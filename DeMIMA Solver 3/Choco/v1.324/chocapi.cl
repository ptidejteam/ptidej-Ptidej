// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: chocapi.cl                                                 *
// *    API: application programmer interface (public methods)        *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: problems                                               *
// *   Part 2: variables                                              *
// *   Part 3: Constraints as tuples of values                        *
// *   Part 4: Arithmetic constraints                                 *
// *   Part 5: Boolean connectors                                     *
// *   Part 6: global constraints                                     *
// *   Part 7: adding information                                     *
// *   Part 8: searching for solutions / (tree search) optimization   *
// *   Part 9: searching for solutions / (assign & repair) local opt. *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 1: problems                                               *
// ********************************************************************

// v0.9907
// v1.010 use setActiveProblem()
[choco/makeProblem(s:string, n:integer) : Problem
 -> let pb := Problem(name = copy(s),
                      ;delayedConstraints = list(nil,nil,nil,nil), //v0.30 renamed globalConstraints
                      // v0.9906 removed above
                      globalSearchSolver = GlobalSearchSolver(),
                      localSearchSolver = LocalSearchSolver() )  in
      (let pe := makeChocEngine(n) in attachPropagationEngine(pb,pe),
       let ie := makeConflictCount() in attachInvariantEngine(pb,ie),
       setActiveProblem(pb),
       pb)]

// v1.0
[choco/getProblem(v:IntVar) : Problem -> v.problem]

// v1.02 <thb> add the case when the constraint c involves only constant variables, considered as belonging to CURRENT_PB
[choco/getProblem(c:AbstractConstraint) : Problem
-> let p := CURRENT_PB, n := getNbVars(c) in
      (when i0 := some(i in (1 .. n) | known?(problem,getVar(c,i) as IntVar)) in
            p := (getVar(c,i0) as IntVar).problem,
       p)]

// v1.010 new API methods
[choco/getActiveProblem() : Problem -> CURRENT_PB]
[choco/setActiveProblem(pb:Problem) : void -> CURRENT_PB := pb]
[choco/discardProblem(pb:Problem) : void
 -> when gs := get(globalSearchSolver, pb)  in
       (backtrack(gs.baseWorld),
        CURRENT_PB := unknown)]
;        CURRENT_PB := DUMMY_PROBLEM)]  // v1.013

// v1.013: accessing the propagation engine
[choco/getPropagationEngine(p:Problem) : PropagationEngine
 -> p.propagationEngine]

// v1.013: a toggle: choosing to delay or no the propagation on linear constraints
[choco/setDelayedLinearConstraintPropagation(pe:ChocEngine,b:boolean) : void
 -> if b pe.delayLinCombThreshold := 0
    else pe.delayLinCombThreshold := 100000]

// ********************************************************************
// *   Part 2: variables                                              *
// ********************************************************************

// public methods for creating variables on the fly and assiging them a name
//
// v0.37 added closeIntVar because of new backtrackable domain bounds
[choco/makeBoundIntVar(p:Problem, s:string, i:integer, j:integer) : IntVar
 -> assert(i <= j),
    let v := IntVar(name = copy(s)) in
       (closeIntVar(v,i,j,1),  // v0.9903: react to removals in one pass
        addIntVar(p,v),
        v)]
[choco/makeBoundIntVar(p:Problem, i:integer, j:integer) : IntVar => makeBoundIntVar(p,"",i,j)]
[choco/makeBoundIntVar(p:Problem, s:string) : IntVar => makeBoundIntVar(p,s,-100,100)]

// v0.37
[choco/makeConstantIntVar(x:integer) : IntVar
 -> let v := IntVar(name = "'" /+ string!(x) /+ "'") in // <thb> v0.97
      (closeIntVar(v,x,x,0), v)]   // v0.9903: there will never be a removal to react to

[choco/makeIntVar(p:Problem, s:string, i:integer, j:integer) : IntVar
 -> assert(i <= j),
;    let v := makeBoundIntVar(p,s,i,j) in
     let v := IntVar(name = copy(s)) in
       (closeIntVar(v,i,j,min(3,j - i - 1)),  // v0.9903: react to (up to 5) removal one by one
        addIntVar(p,v),
        v.bucket := makeLinkedListIntDomain(i,j),
        v)]
[choco/makeIntVar(p:Problem, i:integer, j:integer) : IntVar => makeIntVar(p,"",i,j)]
[choco/makeIntVar(p:Problem, s:string) : IntVar => makeIntVar(p,s,-100,100)]

// v0.22 <fl> documented but not implemented ...
// v0.26 stronger typing of b
[choco/makeIntVar(p:Problem, s:string, b:list[integer]) : IntVar
 -> let minVal := min(b), // v1.02 min vs. Min
        maxVal := max(b), // v1.02 max vs. Max
        v := makeIntVar(p,s,minVal,maxVal) in
      (for val in list{val2 in (minVal .. maxVal) | not(val2 % b)}
           removeDomainVal(v.bucket, val),
       v)]
// v0.26 stronger typing of b
[choco/makeIntVar(p:Problem, b:list[integer]) : IntVar -> makeIntVar(p,"",b)]

// v1.330: introducing set variables
[choco/makeSetVar(p:Problem, s:string, i:integer, j:integer) : SetVar
 -> assert(i <= j),
     let v := SetVar(name = copy(s)) in
       (closeSetVar(v,i,j),
        addSetVar(p,v),
        if (j - i + 1 <= 30) 
           v.setBucket := makeBitSetDomain(i,j)
        else v.setBucket := makeBitListSetDomain(i,j),
        v)]
[choco/makeSetVar(p:Problem, i:integer, j:integer) : SetVar => makeSetVar(p,"",i,j)]

[choco/makeSetVar(p:Problem, s:string, i:integer, j:integer, cardVar:IntVar) : SetVar
 -> let sv := makeSetVar(p,s,i,j) in 
     (post(p, setCard(sv,cardVar)),
      sv)]
[choco/makeSetVar(p:Problem, i:integer, j:integer, cardVar:IntVar) : SetVar => makeSetVar(p,"",i,j,cardVar)]

// ********************************************************************
// *   Part 3: Constraints as tuples of values                        *
// ********************************************************************

// ---- binary constraints -----
 
// introducing relations
// creating a relation from a test method
[choco/makeBinTruthTest(m:method[range = boolean]) : TruthTest 
 -> TruthTest(test = m)]
// creating an empty relation table
[choco/makeBinRelation(min1:integer, max1:integer, min2:integer, max2:integer) : TruthTable2D
 -> let n := max1 - min1 + 1, m := max2 - min2 + 1, mat := make2DBoolMatrix(min1,max1,min2,max2,false,false) in 
        TruthTable2D(matrix = mat, offset1 = min1, offset2 = min2, size1 = n, size2 = m)]
// creating a relation from an enumeration of tuples
[choco/makeBinRelation(min1:integer, max1:integer, min2:integer, max2:integer, mytuples:list[tuple(integer,integer)]) : TruthTable2D
 -> let n := max1 - min1 + 1, m := max2 - min2 + 1, mat := make2DBoolMatrix(min1,max1,min2,max2,false,false),
        tt := TruthTable2D(matrix = mat, offset1 = min1, offset2 = min2, size1 = n, size2 = m) in 
     (for t in list{tt in mytuples | (tt[1] >= min1 & tt[1] <= max1 & tt[2] >= min2 & tt[2] <= max2)}
        let i := t[1] - min1 + 1, j := t[2] - min2 + 1 in
          (assert((1 <= i) & (i <= n)), assert((1 <= j) & (j <= m)),
           store(mat,t[1],t[2],true)),
      tt) ]

// generic API for stating binary constraints
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean,ac:integer) : CSPBinConstraint
 -> if (ac = 3) makeAC3BinConstraint(va, vb, feas, feasRel)
    else if (ac = 4) makeAC4BinConstraint(va, vb, feas, feasRel)
    else if (ac = 2001) makeAC2001BinConstraint(va, vb, feas, feasRel)
    else error("algorithm AC~S not implemented yet for binary constraints",ac)]

// AC2001 is the default algorithm
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,feas,2001)]
// by default the positive relation is provided
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,ac:integer) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,true,ac)]
// Simplest API: by default the positive relation is provided and AC2001 is the algorithm
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,true,2001)]

// ----- older deprecated API (kept for backward compatibility)
[choco/feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple(integer,integer)], ac:integer) : CSPBinConstraint // port to claire 3 tuple(integer,integer) -> tuple
 -> let feasRel := makeBinRelation(va.inf, va.sup, vb.inf, vb.sup, goodPairs) in binConstraint(va,vb,feasRel,true,ac)]
[choco/infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple(integer,integer)], ac:integer) : CSPBinConstraint // port to claire 3 tuple(integer,integer) -> tuple
 -> let feasRel := makeBinRelation(va.inf, va.sup, vb.inf, vb.sup, badPairs) in binConstraint(va,vb,feasRel,false,ac)]
[choco/feasBinTestConstraint(va:IntVar,vb:IntVar, m:method, ac:integer) : CSPBinConstraint
 -> let feasRel := makeBinTruthTest(m) in binConstraint(va,vb,feasRel,true,ac)]
[choco/infeasBinTestConstraint(va:IntVar,vb:IntVar, m:method, ac:integer) : CSPBinConstraint // new since v0.9907
 -> let feasRel := makeBinTruthTest(m) in binConstraint(va,vb,feasRel,false,ac)]
  
[choco/feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple]) : CSPBinConstraint => feasPairConstraint(va,vb,goodPairs,2001)]
[choco/infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple]) : CSPBinConstraint => infeasPairConstraint(va,vb,badPairs,2001)]
[choco/feasBinTestConstraint(va:IntVar,vb:IntVar, m:method) : CSPBinConstraint => feasBinTestConstraint(va,vb,m,2001)]
[choco/infeasBinTestConstraint(va:IntVar,vb:IntVar, m:method) : CSPBinConstraint => infeasBinTestConstraint(va,vb,m,2001)]
// ---------------------

// n-ary constraints 
[choco/feasTupleConstraint(lvars:list[IntVar],
                           goodTuples:list[list[integer]]) : CSPLargeConstraint
 -> tupleConstraint(lvars, true, goodTuples)]

[choco/infeasTupleConstraint(lvars:list[IntVar],
                             badTuples:list[list[integer]]) : CSPLargeConstraint
 -> tupleConstraint(lvars, false, badTuples)]

// claire3 port: building a staticly typed vars lisit instead of a simple copy
[choco/feasTestConstraint(lvars:list[IntVar],m:method) : CSPLargeConstraint
 -> let c := CSPLargeConstraint(vars = list<IntVar>{v | v in lvars}, cste = 0) in
     (closeLargeIntConstraint(c),  // v0.34
      c.cachedTuples := false,
      c.feasTest := m,
      c) ]

// ********************************************************************
// *   Part 4: Arithmetic constraints                                 *
// ********************************************************************

// General linear combinations
//  we store linear expressions in temporary data structures: terms
// v1.0 renamed Term (was Term)
choco/Term <: Ephemeral(
    cste:integer = 0)

//  LinTerm: a temporary object representing a linear term
choco/LinTerm <: Term(
    lcoeff:list<integer>, // v1.011 <thb> strongly typed lists
    lvars:list<IntVar>)

// v1.02 <franck>
[self_print(t:LinTerm) : void
 -> let tLength := length(t.lcoeff) in
      (if (tLength > 0) printf("~S.~S",t.lcoeff[1],t.lvars[1]),
       for i in (2 .. tLength) // v0.28: size vs. length
           printf(" + ~S.~S",t.lcoeff[i],t.lvars[i]),
       if (t.cste < 0) printf(" ~S",t.cste)
       else if (t.cste > 0) printf(" +~S",t.cste) )]

// constructing a linear term from a list of variables
claire/scalar :: operation(precedence = precedence(*))
// claire3 port: building a staticly typed vars list instead of a simple copy
// v1.011 strongly typed lists
[scalar(l1:list[integer], l2:list[IntVar]) : LinTerm
 -> LinTerm(lcoeff = list<integer>{i | i in l1}, lvars = list<IntVar>{v | v in l2})] //v0.94: ensure a non-destructive behavior

//v0.36:Constructing a linear term from a list of variables
// claire3 port: avoid make_list, v1.011 strongly typed lists
[choco/sumVars(l:list[IntVar]) : LinTerm
-> LinTerm(lcoeff = make_list(length(l),integer,1),// make_list(length(l),integer,1), // list<integer>{1 | i in (1 .. length(l))}, ;make_list(length(l),1),
           lvars = list<IntVar>{v | v in l})] //v0.94: ensure a non-destructive behavior
      

// ********************************************************************
// *   Part 6: global constraints                                     *
// ********************************************************************

// changed v0.28
// claire3 port: building a staticly typed vars lisit instead of a simple copy
[choco/allDifferent(l:list[IntVar]) : AllDiff
 -> let c := AllDiff(vars = list<IntVar>{v | v in l}, cste = 0) in
       (closeLargeIntConstraint(c),
        c)]


// ********************************************************************
// *   Part 7: adding information                                     *
// ********************************************************************

// v0.9906: rewritten
// v0.9907: merged the definitions from IntConstraint and DelayedConstraint into AbstractConstraint
// v1.010: one single restriction for post@Problem
[choco/post(p:Problem, c0:AbstractConstraint) : void //v0.30 replaced GlobalConstraint by DelayedConstraint
 -> let c := c0 in
      (case c0 (LinComb
                  (if (getNbVars(c0) >= p.propagationEngine.delayLinCombThreshold)
                      c := delay(c0,2)), // 0.30: delay propagation for large linear constraints
                Delayer (if (c0.priority <= 0)
                            c := c0.target)),// v1.012 undelaying constraints that are posted as delay(c,0/-1)
       p.constraints :add c,
       connect(c),
       let pe := p.propagationEngine, prio := getPriority(c),
           e := ConstAwakeEvent(touchedConst = c, initialized = false, priority = prio) in
        (c.constAwakeEvent := e,
         registerEvent(pe,e),
         postConstAwake(pe,c,true)))]

// further constraining a domain: adding information to the current state
// (restricting by hand the domain of a variable)
[choco/setMin(v:IntVar, x:integer) : void => updateInf(v,x,0)]
[choco/setMax(v:IntVar, x:integer) : void => updateSup(v,x,0)]
[choco/setVal(v:IntVar, x:integer) : void => instantiate(v,x,0)]
[choco/remVal(v:IntVar, x:integer) : void => removeVal(v,x,0)]

// v1.02 using the targetStatus slots
// v1.103: same signatures for setBranch
[choco/setBranch(d:Disjunction, i:integer, b:boolean) : void
 -> setTargetStatus(d,i,b),
    if b
      (if (i = 1) doAwake(d.const1)
       else doAwake(d.const2))]
[choco/setBranch(d:LargeDisjunction, i:integer, b:boolean) : void
-> setTargetStatus(d,i,b),
   if b doAwake(d.lconst[i]) ]  // v1.02: no propagation of counterpart for largedisjunctions (see cardinality for this)

[choco/setBranch(c:Cardinality, i:integer, b:boolean) : void
 -> if not(knownStatus(c,i))
       (setStatus(c, i, b),
        if b (c.nbTrueStatus :+ 1, doAwake(c.lconst[i]), awakeOnNbTrue(c))
        else (c.nbFalseStatus :+ 1, doAwake(c.loppositeConst[i]), awakeOnNbFalse(c)))
    else if (getStatus(c,i) != b) raiseContradiction(c) ]

// v0.9906
[choco/propagate(p:Problem) : void
 -> let pe := p.propagationEngine, someEvents := true in
       (while someEvents
           (when q := getNextActiveEventQueue(pe) in 
                 popSomeEvents(q)
            else someEvents := false))]

// v1.0: controlling the amount of propagation (useful for dynamic constraint posts...)
[choco/setActive(c:AbstractConstraint) : void
 -> let rootConstraint := getConstraint(getVar(c,1),getConstraintIdx(c,1)) in
       (if (rootConstraint = c) 
           (if not(isActive(c))
              (reconnect(c),
               constAwake(c,true)))
        else case rootConstraint 
           (Delayer (if (rootConstraint.target = c) setActive(rootConstraint))) )]

[choco/setPassive(c:AbstractConstraint) : void
 -> let rootConstraint := getConstraint(getVar(c,1),getConstraintIdx(c,1)) in
       (if (rootConstraint = c) 
           (if  isActive(c)
              (disconnect(c),
               let pb := getProblem(c),
                   evt := c.constAwakeEvent,
                   q := getQueue(pb.propagationEngine, evt) in
                 remove(q,evt)))
        else case rootConstraint 
           (Delayer (if (rootConstraint.target = c) setPassive(rootConstraint))) )]

// ********************************************************************
// *   Part 8: searching for solutions / optimization                 *
// ********************************************************************

[choco/getProblem(algo:Solver) : Problem -> algo.problem]
[choco/getSearchManager(b:AbstractBranching) : GlobalSearchSolver -> b.manager]

// v1.016: new branching heuristic objects
[choco/makeMinDomVarHeuristic() -> MinDomain()]
[choco/makeDomDegVarHeuristic() -> DomDeg()]
[choco/makeMaxDegVarHeuristic() -> MaxDeg()]
[choco/makeStaticVarHeuristic(l:list[IntVar])
 -> StaticVarOrder(vars = list<IntVar>{v | v in l})]

// new API methods
[choco/makeMinDomVarHeuristic(l:list[IntVar]) : MinDomain
 -> MinDomain(vars = list<IntVar>{v | v in l})]
[choco/makeDomDegVarHeuristic(l:list[IntVar]) : DomDeg
 -> DomDeg(vars = list<IntVar>{v | v in l})]
[choco/makeMaxDegVarHeuristic(l:list[IntVar]) : MaxDeg
 -> MaxDeg(vars = list<IntVar>{v | v in l})]

[choco/makeIncValIterator() -> IncreasingDomain()]
[choco/makeDecValIterator() -> DecreasingDomain()]

[choco/makeMinValSelector() -> MinValHeuristic()]
[choco/makeMaxValSelector() -> MaxValHeuristic()]
[choco/makeMidValSelector() -> MidValHeuristic()]

[choco/makeAssignVarBranching(varh:VarSelector, valh:ValIterator) : AssignVar
 -> let av := AssignVar(varHeuristic = varh, valHeuristic = valh) in
      (varh.branching := av,
       valh.branching := av,
       av)]
[choco/makeAssignVarBranching(varh:VarSelector) : AssignVar
 -> makeAssignVarBranching(varh, makeIncValIterator())]
[choco/makeAssignVarBranching() : AssignVar
 -> makeAssignVarBranching(makeMinDomVarHeuristic(), makeIncValIterator())]

[choco/makeSplitDomBranching(varh:VarSelector, valh:ValSelector, threshold:integer) : SplitDomain
 -> let sd := SplitDomain(varHeuristic = varh, valHeuristic = valh, dichotomyThreshold = threshold) in
      (varh.branching := sd,
       valh.branching := sd,
       sd)]
[choco/makeSplitDomBranching(varh:VarSelector, threshold:integer) : SplitDomain
 -> makeSplitDomBranching(varh, makeMidValSelector(), threshold)]
[choco/makeSplitDomBranching(varh:VarSelector) : SplitDomain
 -> makeSplitDomBranching(varh, makeMidValSelector(), 5)]
[choco/makeSplitDomBranching() : SplitDomain
 -> makeSplitDomBranching(makeMinDomVarHeuristic(), makeMidValSelector(), 5)]

[choco/makeAssignOrForbidBranching(varh:VarSelector, valh:ValSelector) : AssignOrForbid
 -> let af := AssignOrForbid(varHeuristic = varh, valHeuristic = valh) in
      (varh.branching := af,
       valh.branching := af,
       af)]

// v1.010 building branching objects
[choco/makeDisjunctionBranching(pb:Problem) : SettleBinDisjunction
 -> let ldisj := list<Disjunction>() in
     (for c in pb.constraints
        (case c (Disjunction ldisj :add c)),
      SettleBinDisjunction(disjunctions = ldisj))]

//  the default standard procedure in Choco:
//  first settle suspended disjunctions, then split domains, then instantiate
[choco/makeDefaultBranchingList(pb:Problem) : list[AbstractBranching]
 => list<AbstractBranching>(
             makeDisjunctionBranching(pb),
             makeSplitDomBranching(makeMinDomVarHeuristic(), makeMidValSelector(), 5),
             makeAssignVarBranching(makeMinDomVarHeuristic(), makeIncValIterator())
             )]

// v1.0 new API methods
[choco/makeGlobalSearchSolver(allSolutions:boolean, l:list[AbstractBranching]) : Solve
 -> composeGlobalSearch(Solve(stopAtFirstSol = not(allSolutions)),l)]

// v1.02 initialize the stopAtFirstSol slot
[choco/makeGlobalSearchMaximizer(obj:IntVar, restartSearch:boolean, l:list[AbstractBranching]) : AbstractOptimize
 -> if restartSearch 
       composeGlobalSearch(OptimizeWithRestarts(objective = obj, doMaximize = true, stopAtFirstSol = true),l)
    else composeGlobalSearch(BranchAndBound(objective = obj, doMaximize = true, stopAtFirstSol = false),l)]

// v1.02 initialize the stopAtFirstSol slot
[choco/makeGlobalSearchMinimizer(obj:IntVar, restartSearch:boolean, l:list[AbstractBranching]) : AbstractOptimize
 -> if restartSearch 
       composeGlobalSearch(OptimizeWithRestarts(objective = obj, doMaximize = false, stopAtFirstSol = true),l)
    else composeGlobalSearch(BranchAndBound(objective = obj, doMaximize = false, stopAtFirstSol = false),l)]

// default methods
// v1.010: add the Problem argument
[choco/makeGlobalSearchSolver(allSolutions:boolean) : Solve
 -> makeGlobalSearchSolver(allSolutions,makeDefaultBranchingList(getActiveProblem()))]

[choco/makeGlobalSearchMaximizer(obj:IntVar, restartSearch:boolean) : AbstractOptimize
 -> makeGlobalSearchMaximizer(obj, restartSearch,makeDefaultBranchingList(getActiveProblem()))]

[choco/makeGlobalSearchMinimizer(obj:IntVar, restartSearch:boolean) : AbstractOptimize
 -> makeGlobalSearchMinimizer(obj, restartSearch, makeDefaultBranchingList(getActiveProblem()))]

[choco/makeBacktrackLimit(nb:integer) : NodeLimit -> NodeLimit(maxNb = nb)]
[choco/makeNodeLimit(nb:integer) : BacktrackLimit -> BacktrackLimit(maxNb =  nb)] 
[choco/setSearchLimit(algo:GlobalSearchSolver, lim:GlobalSearchLimit) : void 
 -> algo.limits :add lim]
 
// -- start: deprecated API functions
// API functions for the default search procedure
[choco/solve(pb:Problem,all:boolean) : boolean
 -> solve(pb, makeDefaultBranchingList(pb), all)]
[choco/minimize(pb:Problem,obj:IntVar,restart:boolean) : integer
 -> minimize(pb, obj, makeDefaultBranchingList(pb), restart)]
[choco/maximize(pb:Problem,obj:IntVar,restart:boolean) : integer
 -> maximize(pb, obj, makeDefaultBranchingList(pb), restart)]

// New API functions
// v1.08 attachGlobalSearchSolver -> attach
[choco/solve(pb:Problem,l:list[AbstractBranching],all:boolean) : boolean
 -> let algo := makeGlobalSearchSolver(all, l) in
       (attach(algo,pb), run(algo), getFeasibility(algo))]  // v1.02: explicit access to feasibility

// v1.05 <thb> getBestObjectiveValue
[choco/minimize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) : integer
 -> let algo := makeGlobalSearchMinimizer(obj, restart, l) in 
       (attach(algo,pb), run(algo), getBestObjectiveValue(algo))]  // v1.02: explicit access to obj. value

[choco/maximize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) : integer
 -> let algo := makeGlobalSearchMaximizer(obj, restart, l) in 
       (attach(algo,pb), run(algo), getBestObjectiveValue(algo))]  // v1.02: explicit access to obj. value
// -- end: deprecated API functions

// controlling the search
// v1.08 these functions no longer apply to Problem, but to GlobalSearchSolver
[choco/setMaxPrintDepth(algo:GlobalSearchSolver, n:integer) : void
 -> algo.maxPrintDepth := n]

// v1.013 new API function
[choco/setMaxSolutionStorage(algo:GlobalSearchSolver, nb:integer) : void
 -> algo.maxSolutionStorage := nb]

[choco/setVarsToShow(algo:GlobalSearchSolver, l:list[IntVar]) : void
 -> algo.varsToShow := copy(l)]

[choco/getNbSol(algo:GlobalSearchSolver) : integer
 -> algo.nbSol]

[choco/getProblem(b:AbstractBranching) : Problem
 -> b.manager.problem]

// v1.322 new API method
[choco/getGlobalSearchSolver(p:Problem) : GlobalSearchSolver
 -> p.globalSearchSolver]
  
// -------- new from v1.020: introducing limit objects

[choco/setNodeLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add NodeLimit(maxNb = n)]
[choco/setTimeLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add TimeLimit(maxMsec = n)]
[choco/setBacktrackLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add BacktrackLimit(maxNb = n)]
// deprecated API: mentionned for upward compatibility 
[choco/setMaxNbBk(algo:GlobalSearchSolver, n:integer) : void
 -> setBacktrackLimit(algo,n)]
 
// ********************************************************************
// *   Part 9: searching for solutions / (assign & repair) local opt. *
// ********************************************************************

// API functions -> delegate work to the "invariant maintainer"
// Three new events:
[choco/assignVal(v:IntVar, a:integer) : void
 -> //[IDEBUG] assign ~S: ~S // v,a,
    if isInstantiated(v)
      let val := v.value in
        (if (val != a) error("wrong event -assign- for a change of value assignment on ~S",v)
         // otherwise do nothing (no change)
        )
    else (v.value := a,
          postAssignVal(v.problem.invariantEngine,v,a))]

[choco/unassignVal(v:IntVar) : void
 -> //[IDEBUG] unassign ~S // v,
    if isInstantiated(v)
      (postUnAssignVal(v.problem.invariantEngine,v),
       v.value := UNKNOWNINT)]

[choco/changeVal(v:IntVar,b:integer) : void
 -> if isInstantiated(v)
     let a := v.value in
      (if (b != a)
          (postChangeVal(v.problem.invariantEngine,v,a,b),
           v.value := b)
       // otherwise, not a real change, do nothing
       )
    else error("can not change the value of an unassigned variable :~S",v)]

;private/DEFAULT_BUILD :: AssignmentHeuristic()
;private/DEFAULT_MOVE :: FlipNeighborhood()

// controlling the search
[choco/setMaxNbLocalSearch(pb:Problem, n:integer) : void
 -> pb.localSearchSolver.maxNbLocalSearch := n]

[choco/setMaxNbLocalMoves(pb:Problem, n:integer) : void
 -> pb.localSearchSolver.maxNbLocalMoves := n]

[choco/move(pb:Problem) : integer
; -> move(pb, DEFAULT_BUILD, DEFAULT_MOVE)]
 -> move(pb, AssignmentHeuristic(), FlipNeighborhood())]

[choco/move(pb:Problem, b:ConstructiveHeuristic, m:MoveNeighborhood) : integer
 -> let algo := MultipleDescent(buildAssignment = b,changeAssignment = m) in
     (m.manager := algo,
      b.manager := algo,
      attachLocalSearchSolver(pb,algo),
      runLocalSearch(algo),
      getLocalSearchObjectiveValue(pb.invariantEngine))]

