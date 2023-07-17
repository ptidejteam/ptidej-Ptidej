// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: main.cl                                                    *
// *    solving                                                       *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// * A. Global search                                                 *
// *   Part 0: object model                                           *
// *   Part 1: utils and solution management                          *
// *   Part 2: exploring  one level of branching                      *
// *   Part 3: Feeding the engine with a library of branching objects *
// *   Part 4: Using branching classes within globalSearchSolver's    *
// * B. Local search (generating assignments, improving them by LO)   *
// *   Part 5: utils and solution management                          *
// *   Part 6: Feeding the engine with a library of neighborhoods     *
// *   Part 7: Using neighborhood classes within LocalSearchSolver's  *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 0: object model                                           *
// ********************************************************************

;choco/AbstractBranching <: Ephemeral(
;        manager:GlobalSearchSolver,
;        nextBranching:AbstractBranching)

// v1.013 the range of the abstract interface properties needs be specified
// v1.013 add a parameter to getNextBranch and finishedBranching (index of the previous branch),
//        introduce getFirstBranch
choco/selectBranchingObject :: property(range = any)
choco/goDownBranch :: property(range = void)
choco/traceDownBranch :: property(range = void)
choco/getFirstBranch :: property(range = integer)  // v1.013
choco/getNextBranch :: property(range = integer)
choco/goUpBranch :: property(range = void)
choco/traceUpBranch :: property(range = void)
choco/finishedBranching :: property(range = boolean)
choco/branchOn :: property(range = boolean)

// v0.9906
[selectBranchingObject(b:AbstractBranching) : any
 -> error("selectBranchingObject not defined on the abstract class AbstractBranching"), unknown]
[goDownBranch(b:AbstractBranching,x:any,i:integer) : void
 -> error("goDownBranch not defined on the abstract class AbstractBranching (called with ~S,~S)",x,i)]
[traceDownBranch(b:AbstractBranching,x:any,i:integer) : void
 -> error("traceDownBranch not defined on the abstract class AbstractBranching (called with ~S,~S)",x,i)]
// v0.9906: <thb> definitions required in order to have dynamic dispatch of the above interface methods
[choco/getFirstBranch(b:AbstractBranching,x:any) : integer          // v1.013
 -> error("getFirstBranch not defined on the abstract class AbstractBranching (called with ~S)",x), 0]
[choco/getNextBranch(b:AbstractBranching,x:any,i:integer) : integer // v1.013
 -> error("getNextBranch not defined on the abstract class AbstractBranching (called with ~S)",x), 0]
[choco/goUpBranch(b:AbstractBranching,x:any,i:integer) : void
 -> error("goUpBranch not defined on the abstract class AbstractBranching (called with ~S,~S)",x,i)]
[choco/traceUpBranch(b:AbstractBranching,x:any,i:integer) : void
 -> error("traceUpBranch not defined on the abstract class AbstractBranching (called with ~S,~S)",x,i)]
[choco/finishedBranching(b:AbstractBranching,x:any,i:integer) : boolean
 -> error("finishedBranching not defined on the abstract class AbstractBranching (called with ~S)",x), true]
[choco/branchOn(b:AbstractBranching,v:any,n:integer) : boolean
 -> error("branchOn not defined on the abstract class AbstractBranching (called with ~S,~S)",v,n), false]
 
// v0.9907 <thb> must be declared abstract
(abstract(selectBranchingObject),
 abstract(goDownBranch),
 abstract(traceDownBranch),
 abstract(getFirstBranch),
 abstract(getNextBranch),
 abstract(goUpBranch),
 abstract(traceUpBranch),
 abstract(finishedBranching))

// v1.0
(interface(AbstractBranching,
    selectBranchingObject,
    goDownBranch,traceDownBranch,goUpBranch,traceUpBranch,
    getFirstBranch,getNextBranch,finishedBranching,branchOn))
// v1.0
(interface(selectBranchingObject),
 interface(getFirstBranch),
 interface(getNextBranch),
 interface(goDownBranch),
 interface(traceDownBranch),
 interface(goUpBranch),
 interface(traceUpBranch),
 interface(finishedBranching),
 interface(branchOn))
 
choco/LargeBranching <: AbstractBranching()
choco/BinBranching <: AbstractBranching()

// v1.013 using a binary branching object as a large branching one
[choco/goUpBranch(b:LargeBranching,x:any,i:integer) : void -> nil]
[choco/traceUpBranch(b:LargeBranching,x:any,i:integer) : void -> nil]
[choco/getFirstBranch(b:LargeBranching,x:any) : integer -> 1]
[choco/getNextBranch(b:LargeBranching,x:any,i:integer) : integer -> 2]
[choco/finishedBranching(b:LargeBranching,x:any,i:integer) : boolean -> (i = 2)]

// v1.013: a new class for branching objects
// that may alternatively branch from several sub-branching objects
choco/CompositeBranching <: LargeBranching(
     alternatives:AbstractBranching)
// the only method that should be defined
[choco/selectBranching(b:CompositeBranching) : AbstractBranching
 -> error("selectBranching not defined on the abstract class CompositeBranching"), b]
[choco/selectBranchingObject(b:CompositeBranching) : any
 -> let alt := selectBranching(b),
        x   := selectBranchingObject(alt) in tuple(alt,x)]
[choco/getFirstBranch(b:CompositeBranching,xpair:any) : integer
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      getFirstBranch(alt,x)]
[choco/getNextBranch(b:CompositeBranching,xpair:any,i:integer) : integer
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      getNextBranch(alt,x,i)]
[choco/goUpBranch(b:CompositeBranching,xpair:any,i:integer) : void
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      goUpBranch(alt,x,i)]
[choco/traceUpBranch(b:CompositeBranching,xpair:any,i:integer) : void
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      traceUpBranch(alt,x,i)]
[choco/goDownBranch(b:CompositeBranching,xpair:any,i:integer) : void
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      goDownBranch(alt,x,i)]
[choco/traceDownBranch(b:CompositeBranching,xpair:any,i:integer) : void
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      traceDownBranch(alt,x,i)]
[choco/finishedBranching(b:CompositeBranching,xpair:any,i:integer) : boolean
  -> let xp := (xpair as tuple(AbstractBranching,any)),
         alt := (xp[1] as AbstractBranching), x := xp[2] in 
      finishedBranching(alt,x,i)]

// v1.318: introducing heuristics
choco/VarSelector <: Ephemeral(
      branching:AbstractBranching)
[selectVar(vs:VarSelector) : (IntVar U {unknown}) -> error("selectVar is not defined on ~S",vs), unknown]
(interface(selectVar), interface(VarSelector,selectVar))

choco/MinDomain <: VarSelector(problem:Problem, vars:list<IntVar>)
[selectVar(vs:MinDomain) : (IntVar U {unknown})
 -> if (length(vs.vars) > 0) getSmallestDomainUnassignedVar(vs.vars)
    else getSmallestDomainUnassignedVar(getProblem(getSearchManager(vs.branching)))]

choco/MaxDeg <: VarSelector(problem:Problem, vars:list<IntVar>)
[selectVar(vs:MaxDeg) : (IntVar U {unknown})
 -> if (length(vs.vars) > 0) getMostConstrainedUnassignedVar(vs.vars)
    else getMostConstrainedUnassignedVar(getProblem(getSearchManager(vs.branching)))]

choco/DomDeg <: VarSelector(problem:Problem, vars:list<IntVar>)
[selectVar(vs:DomDeg) : (IntVar U {unknown})
 -> if (length(vs.vars) > 0) getDomOverDegBestUnassignedVar(vs.vars)
    else getDomOverDegBestUnassignedVar(getProblem(getSearchManager(vs.branching)))]

choco/StaticVarOrder <: VarSelector(vars:list[IntVar])
[selectVar(vs:StaticVarOrder) : (IntVar U {unknown})
 -> some(v in vs.vars | not(isInstantiated(v)))]

choco/ValIterator <: Ephemeral(
      branching:AbstractBranching)
[isOver(vi:ValIterator, x:IntVar, i:integer) : boolean -> error("isOver is not defined on ~S",vi), true]
[getFirstVal(vi:ValIterator, x:IntVar) : integer -> error("getFirstVal is not defined on ~S",vi), 0]
[getNextVal(vi:ValIterator, x:IntVar, i:integer) : integer -> error("getNextVal is not defined on ~S",vi), 0]
( interface(isOver),interface(getFirstVal),interface(getNextVal),
  interface(ValIterator,isOver,getFirstVal,getNextVal) )

choco/IncreasingDomain <: ValIterator()
[isOver(vi:IncreasingDomain, x:IntVar, i:integer) : boolean
 -> (i >= x.sup)]
[getFirstVal(vi:IncreasingDomain, x:IntVar) : integer
 -> x.inf]
[getNextVal(vi:IncreasingDomain, x:IntVar, i:integer) : integer
 -> getNextDomainValue(x,i)]

choco/DecreasingDomain <: ValIterator()
[isOver(vi:DecreasingDomain, x:IntVar, i:integer) : boolean
 -> (i <= x.inf)]
[getFirstVal(vi:DecreasingDomain, x:IntVar) : integer
 -> x.sup]
[getNextVal(vi:DecreasingDomain, x:IntVar, i:integer) : integer
 -> getPrevDomainValue(x,i)]

choco/ValSelector <: Ephemeral(
      branching:AbstractBranching)
[getBestVal(vh:ValSelector, x:IntVar) : integer -> error("getBestVal is not defined on ~S",vh), 0]
( interface(getBestVal),
  interface(ValSelector,getBestVal) )

MidValHeuristic <: ValSelector()
MinValHeuristic <: ValSelector()
MaxValHeuristic <: ValSelector()
[getBestVal(vh:MidValHeuristic, x:IntVar) : integer
 -> x.inf + (x.sup - x.inf) / 2]
[getBestVal(vh:MinValHeuristic, x:IntVar) : integer
 -> x.inf]
[getBestVal(vh:MaxValHeuristic, x:IntVar) : integer
 -> x.sup]

choco/AssignVar <: LargeBranching(
     varHeuristic:VarSelector,
     valHeuristic:ValIterator)
choco/SplitDomain <: BinBranching(
     varHeuristic:VarSelector,
     valHeuristic:ValSelector,
     dichotomyThreshold:integer = 5)
choco/AssignOrForbid <: BinBranching(
     varHeuristic:VarSelector,
     valHeuristic:ValSelector)

// note: this could be enriched into a LargeBranching, considering LargeDisjunctions
// v1.010: the branching now explicitly stores the list of disjunctions
choco/SettleBinDisjunction <: BinBranching(disjunctions:list<Disjunction>)

;choco/GlobalSearchLimit <: Ephemeral()
;
;choco/GlobalSearchSolver <: Solver(
;    // slots for global search
;   private/stopAtFirstSol:boolean = true,
;   choco/nbSol:integer = 0,        // nb of solutions found
;   choco/nbBk:integer = 0,         // nb of backtracks in one tree
;   choco/nbNd:integer = 0,         // nb of nodes (== nb calls to assign) expanded in one tree
;   choco/maxNbBk:integer = 100000,   // limit on the total number of backtracks
;   choco/maxPrintDepth:integer = 5,  // maximal depth of printing
;    // slots for managing the solutions generated during the search
;   private/storeBestSol:boolean = false, // do we store the best solution while optimizing
;   private/branchingComponents:list[AbstractBranching],
;   private/limits:list<GlobalSearchLimit>
;   )

choco/Solve <: GlobalSearchSolver()

choco/AbstractOptimize <: GlobalSearchSolver(
   choco/doMaximize:boolean,
   choco/objective:IntVar,   // v1.0 moved from Problem to AbstractOptimize
   choco/lowerBound:integer = MININT,  // the bounds of the objective value
   choco/upperBound:integer = MAXINT,  // (problem definition + strengthened by the search history -solution found & nogoods-)
   choco/targetLowerBound:integer = MININT, // v1.08: the bounds for the current tentative search
   choco/targetUpperBound:integer = MAXINT  // v1.013: generalized this data structure, originally on OptimizeWithRestart
   )

// v1.0 accessing the objective value of an optimization problem
// (note that the objective value may not be instantiated)
choco/getObjectiveValue :: property(range = integer)
[choco/getObjectiveValue(a:AbstractOptimize) : integer
 -> (if a.doMaximize a.objective.sup else a.objective.inf)]
// v1.0 for PaLM
(abstract(getObjectiveValue))

// v1.05 <thb> getBestObjectiveValue
[choco/getBestObjectiveValue(a:AbstractOptimize) : integer
 -> (if a.doMaximize a.lowerBound else a.upperBound)]

// v1.08: the target for the objective function: we are searching for a solution
// at least as good as this
[choco/getObjectiveTarget(a:AbstractOptimize) : integer
 -> (if a.doMaximize a.targetLowerBound
     else a.targetUpperBound)]

// v1.013 initialization of the optimization bound data structure
[choco/initBounds(a:AbstractOptimize) : void
 -> let obj := a.objective in
     (a.lowerBound := getInf(obj), a.upperBound := getSup(obj),
      a.targetLowerBound := getInf(obj), a.targetUpperBound := getSup(obj))]

// v1.02: returns a boolean indicating whether the search found solutions or not
[choco/getFeasibility(a:GlobalSearchSolver) : boolean
 -> a.problem.feasible]

// v1.320: may we generate a new node ?
[mayExpandNewNode(lim:GlobalSearchLimit, algo:GlobalSearchSolver) : boolean 
-> true] 
[mayExpandNewNode(lim:NodeLimit, algo:GlobalSearchSolver) : boolean 
-> (algo.nbNd <= lim.maxNb)] 
[mayExpandNewNode(lim:BacktrackLimit, algo:GlobalSearchSolver) : boolean 
-> (algo.nbBk <= lim.maxNb)] 
[mayExpandNewNode(lim:TimeLimit, algo:GlobalSearchSolver) : boolean 
-> (time_read() <= lim.maxMsec)] 
(interface(GlobalSearchLimit, mayExpandNewNode))


choco/BranchAndBound <: AbstractOptimize()

// used for optimization by branch and bound
choco/OptimizeWithRestarts <: AbstractOptimize(
   choco/nbIter:integer = 0,
   choco/baseNbSol:integer = 0,
   choco/nbBkTot:integer = 0, // total nb of backtracks (all trees in the optimization process)
   choco/nbNdTot:integer = 0  // total nb of nodes expanded in all trees
   )

[choco/copyParameters(oldSolver:GlobalSearchSolver, newSolver:GlobalSearchSolver) : void
 -> when d := get(maxPrintDepth, oldSolver) in newSolver.maxPrintDepth := d,
    when storeBest := get(maxSolutionStorage, oldSolver) in newSolver.maxSolutionStorage := storeBest, // v1.013
    when varsShow := get(varsToShow, oldSolver) in newSolver.varsToShow := varsShow]

// v1.08 new name attachGlobalSearchSolver -> attach
[choco/attach(newSolver:GlobalSearchSolver, pb:Problem) : void
 -> ;when oldSolver := get(globalSearchSolver,pb) in
    ;     copyParameters(oldSolver, newSolver),
    pb.globalSearchSolver := newSolver,
    newSolver.problem := pb]

// v1.0, v1.013: more precise return type
[composeGlobalSearch(algo:GlobalSearchSolver, l:list[AbstractBranching]) : type[algo] // GlobalSearchSolver
 -> let n := length(l), l2 := l in // v1.018: no longer a copy // list{copy(t) | t in l} in
     (algo.branchingComponents := l2,
      for i in (1 .. n - 1) 
         (l2[i] as AbstractBranching).nextBranching := (l2[i + 1] as AbstractBranching),
      for comp in l2 comp.manager := algo,
      algo)]

// ********************************************************************
// *   Part 1: utils and solution management                          *
// ********************************************************************

[choco/getSmallestDomainUnassignedVar(pb:Problem) : (IntVar U {unknown})
 -> getSmallestDomainUnassignedVar(pb.vars)]

// v1.02 inlined <thb>
[choco/getSmallestDomainUnassignedVar(l:list[IntVar]) : (IntVar U {unknown})
 => let minsize := MAXINT, v0:(IntVar U {unknown}) := unknown in
        (for v in list{v in l | not(isInstantiated(v))}
           let dsize := getDomainSize(v) in
              (if (dsize < minsize)
                  (minsize := dsize, v0 := v)),
         v0)]

// v1.010: a new variable selection heuristic
[choco/getDomOverDegBestUnassignedVar(pb:Problem) : (IntVar U {unknown})
 -> getDomOverDegBestUnassignedVar(pb.vars)]
[choco/getDomOverDegBestUnassignedVar(l:list[IntVar]) : (IntVar U {unknown})
 => let minsize := MAXINT, maxdeg := 0, v0:(IntVar U {unknown}) := unknown in
        (for v in list{v in l | not(isInstantiated(v))}
           let dsize := getDomainSize(v), deg := getDegree(v) in
              (if (dsize * maxdeg < minsize * deg)
                  (minsize := dsize, maxdeg := deg,v0 := v)),
         v0)]

// v1.010: a new variable selection heuristic
[choco/getMostConstrainedUnassignedVar(pb:Problem) : (IntVar U {unknown})
 -> getMostConstrainedUnassignedVar(pb.vars)]
[choco/getMostConstrainedUnassignedVar(l:list[IntVar]) : (IntVar U {unknown})
 => let maxdeg := 0, v0:(IntVar U {unknown}) := unknown in
        (for v in list{v in l | not(isInstantiated(v))}
           let deg := getDegree(v) in
              (if (maxdeg < deg)
                  (maxdeg := deg, v0 := v)),
         v0)]

// stores information from the current state in the next solution of the problem
// note: only instantiated variables are recorded in the Solution object
//       either all variables or a user-defined subset of them are recorded
//       this may erase a soolution that was previously stored in the ith position
//       this may also increase the size of the pb.solutions vector.
// v1.013: code has been redesigned
[choco/storeCurrentSolution(a:Solver) : void
 -> let sol := makeSolutionFromCurrentState(a) in
        storeSolution(a,sol)]
[choco/makeSolutionFromCurrentState(a:Solver) : Solution
 -> let lvar := (if a.varsToStore a.varsToStore else a.problem.vars),
        // lvar is a reference list of variables of interest, which value is to be recorded in a solution
        nbv := length(lvar),                // v0.28: size vs. length
        sol := makeSolution(a,nbv) in
     (sol.time := time_read(), // v1.013
      sol.nbBk := a.nbBk,
      sol.nbNd := a.nbNd,
      case a (AbstractOptimize
                sol.objectiveValue := getObjectiveValue(a)),
      for i in (1 .. nbv)
        let v := lvar[i] in
           (if not(isInstantiated(v))
            sol.lval[i] := v.value),
      sol)]
[choco/storeSolution(a:Solver,sol:Solution) : void
 -> //[SVIEW] store solution ~S // sol,
    if (length(a.solutions) = a.maxSolutionStorage)
       a.solutions := (a.solutions << 1),
    a.solutions :add sol] // v1.014

// v1.013 no longer an integer parameter
[choco/existsSolution(a:Solver) : boolean
 -> (length(a.solutions) > 0)]

// v1.013 no longer an integer parameter
[choco/restoreBestSolution(a:Solver) : void
 -> restoreSolution(last(a.solutions))]

[choco/restoreSolution(s:Solution) : void
 -> let a := s.algo,
        lvar := (if a.varsToStore a.varsToStore else a.problem.vars),
        lval := s.lval,
        nbv := length(lvar) in        // v0.28: size vs. length
     (for i in (1 .. nbv)
         (if (lval[i] != unknown)
             setVal(lvar[i],lval[i])),
      propagate(a.problem))]

// printing the current state of domains when a solution has been reached
[showSolution(a:Solver)
 -> if (verbose() >= STALK)
      let S := (if (a.varsToShow) a.varsToShow
                else a.problem.vars) in
        for x in S printf("~S\n",x)]
;        for x in S printf("~I\n",self_print(x))]

// ********************************************************************
// *   Part 2: exploring  one level of branching                      *
// ********************************************************************

// explore is a general method that applies on any new descendent or AbtractBranching
choco/explore :: property()
;(interface(AbstractBranching, explore))

// The heart of the search engine:
// exploring the branches of one choice point
[choco/explore(b:AbstractBranching,n:integer) : boolean
 -> let algo := b.manager, pb := algo.problem in
     (when v := selectBranchingObject(b) in branchOn(b,v,n)
      else (when b2 := get(nextBranching,b) in
                    explore(b2,n)
            else (recordSolution(algo),
                  showSolution(algo),
                  algo.stopAtFirstSol)))]

// v1.010: <thb> new method responsible for expanding a node of the search tree
// once the branching object has been generated
[choco/branchOn(b:LargeBranching,v:any,n:integer) : boolean
 -> let algo := b.manager, pb := algo.problem,
        nodeSuccess := false, nodeFinished := false, i := getFirstBranch(b,v) in
      (newNode(algo),
       try
          (until (nodeSuccess | nodeFinished)
             let branchSuccess := false in
               (try (checkCleanState(pb.propagationEngine),
                     pushWorld(pb),
                     if (n <= algo.maxPrintDepth)
                        traceDownBranch(b,v,i),
                     goDownBranch(b,v,i),
                     if explore(b,n + 1) branchSuccess := true)
                catch contradiction nil,
                if not(branchSuccess)
                  (popWorld(pb),
                   endNode(algo),
                   postDynamicCut(algo),
                   if (n <= algo.maxPrintDepth)
                      traceUpBranch(b,v,i),
                   goUpBranch(b,v,i)),
                if branchSuccess nodeSuccess := true,
                if not(finishedBranching(b,v,i)) i := getNextBranch(b,v,i)
                else nodeFinished := true
                ))
       catch contradiction nodeSuccess := false,
       nodeSuccess)]

// v1.103: the branches of a BinBranching are no longer labelled with a boolean
// but with an integer (1/2) like LargeBranching
[choco/branchOn(b:BinBranching,v:any,n:integer) : boolean
 -> let algo := b.manager, pb := algo.problem, success := false in
      (newNode(algo),
       try (checkCleanState(pb.propagationEngine),
            pushWorld(pb),
            if (n <= algo.maxPrintDepth)
               traceDownBranch(b,v,1),
            goDownBranch(b,v,1),
            if explore(b,n + 1) success := true)
       catch contradiction nil,
       if not(success)
         (popWorld(pb),
          endNode(algo),
          try (postDynamicCut(algo),
               if (n <= algo.maxPrintDepth)
                  traceDownBranch(b,v,2),
               goDownBranch(b,v,2),
               if explore(b,n + 1) success := true)
          catch contradiction success := false),
       success)]

// ********************************************************************
// *   Part 3: Feeding the engine with a library of branching objects *
// ********************************************************************

// library of choice points (Branching classes)
// v1.010: now caching the middle of the domain (and not recomputing it)
;choco/SplitDomain <: BinBranching(dichotomyThreshold:integer = 5)
[selectBranchingObject(b:SplitDomain) : any
 -> when x := selectVar(b.varHeuristic) in
       (if (getDomainSize(x) >= b.dichotomyThreshold)  // <ega> v0.9906: typo
           tuple(x, getBestVal(b.valHeuristic, x))
        else unknown)
    else unknown]

[goDownBranch(b:SplitDomain,x:any,first:integer) : void
 -> let xp := (x as tuple(IntVar, integer)),
        v := (xp[1] as IntVar), mid := (xp[2] as integer) in
     (if (first = 1) setMax(v,mid)
      else setMin(v,mid + 1),
      propagate(b.manager.problem) )]
[traceDownBranch(b:SplitDomain,x:any,first:integer) : void
 -> let xp := (x as tuple(IntVar, integer)),
        v := (xp[1] as IntVar), mid := (xp[2] as integer) in
     (if (first = 1) //[STALK] !!! branch ~S: ~S <= ~S // world?(),v,mid
      else //[STALK] !!! branch ~S: ~S > ~S // world?(),v,mid
     )]  // <ega> v0.9907 removed ugly call to propagate !!

;choco/AssignOrForbid <: VarBinBranching()
// v1.010: now caching the best value in the domain (and not recomputing it)
[selectBranchingObject(b:AssignOrForbid) : any
 -> when x := selectVar(b.varHeuristic) in
         tuple(x, getBestVal(b.valHeuristic, x))
    else unknown]

[goDownBranch(b:AssignOrForbid,x:any,first:integer) : void
 -> let xp := (x as tuple(IntVar, integer)),
        v := (xp[1] as IntVar), best := (xp[2] as integer) in
     (if (first = 1) setVal(v,best)
      else remVal(v,best),
      propagate(b.manager.problem)) ]
[traceDownBranch(b:AssignOrForbid,x:any,first:integer) : void
 -> let xp := (x as tuple(IntVar, integer)),
        v := (xp[1] as IntVar), best := (xp[2] as integer) in
     (if (first = 1) //[STALK]  !! branch ~S: ~S == ~S // world?(),v,best
      else //[STALK]  !! branch ~S: ~S !== ~S // world?(),v,best
     )]

;choco/AssignVar <: LargeBranching()
[selectBranchingObject(b:AssignVar) : any
 -> selectVar(b.varHeuristic)]

// v1.013
[finishedBranching(b:AssignVar,x:any,i:integer) : boolean
 -> let y := (x as IntVar) in 
      isOver(b.valHeuristic, y, i)]
[getFirstBranch(b:AssignVar,x:any) : integer
 -> let y := (x as IntVar) in 
      getFirstVal(b.valHeuristic, y)]
[getNextBranch(b:AssignVar,x:any,i:integer) : integer
 -> let y := (x as IntVar) in 
      getNextVal(b.valHeuristic, y, i)]
[goDownBranch(b:AssignVar,x:any,i:integer) : void
 -> let y := (x as IntVar) in 
      (setVal(y,i), propagate(b.manager.problem))]
[goUpBranch(b:AssignVar,x:any,i:integer) : void
 -> let y := (x as IntVar) in 
      (remVal(y,i), propagate(b.manager.problem))]
[traceDownBranch(b:AssignVar,x:any,i:integer) : void
 -> let y := (x as IntVar) in 
      //[STALK]  !! branch ~S: ~S == ~S // world?(),y,i
]      
[traceUpBranch(b:AssignVar,x:any,i:integer) : void
 -> let y := (x as IntVar) in 
      //[STALK]  !! branch ~S: ~S !== ~S // world?(),y,i
]      

// note: this could be enriched into a LargeBranching, considering LargeDisjunctions
// v1.010: store the disjunction list into the branching object
[selectBranchingObject(b:SettleBinDisjunction) : any
 -> some(c in b.disjunctions |
           not(knownStatus(c,1)) & not(knownTargetStatus(c,1))     // v1.02 using the new status methods
           & not(knownStatus(c,2)) & not(knownTargetStatus(c,2)) )]  // v1.05 typo <franck>
[goDownBranch(b:SettleBinDisjunction,d:any,first:integer) : void
 -> case d
     (Disjunction (setBranch(d,first,true),
                   propagate(b.manager.problem)),
      any error("Typing error: should be a Disjunction"))]
[traceDownBranch(b:SettleBinDisjunction,d:any,first:integer) : void
 -> case d
     (Disjunction
        (if (first = 1) //[STALK] !! branch ~S[1]: try ~S // world?(),d.const1
         else //[STALK] !! branch ~S[2]: try ~S // world?(),d.const2
        ),
      any error("Typing error: should be a Disjunction"))]

// ********************************************************************
// *   Part 4: Using branching classes within globalSearchSolver's    *
// ********************************************************************

// The main method is run
//   it calls a few general submethods

;(interface(GlobalSearchSolver, run,
;                               newTreeSearch, endTreeSearch,
;                               newNode,       endNode,
;                               recordSolution,showSolution,
;                               postDynamicCut))))

// general definitions
[newTreeSearch(a:GlobalSearchSolver) : void
 -> assert(a.problem.globalSearchSolver = a), // v1.011 <thb> consistency check
    a.nbBk := 0, a.nbNd := 0, a.nbSol := 0, a.baseWorld := world?(), time_set()]
[endTreeSearch(algo:GlobalSearchSolver) : void
 -> let t := time_get() in
        trace(SVIEW,"solve => ~A solution [~Snd., ~Sbk.] in ~S ms.\n",
                (if not(algo.problem.feasible) "no" else string!(algo.nbSol)),algo.nbNd,algo.nbBk, t)]
[newNode(a:GlobalSearchSolver) : void
 -> a.nbNd :+ 1, 
    for lim in a.limits 
       (if not(mayExpandNewNode(lim,a)) 
           raiseContradiction(a.problem.propagationEngine,lim))]
[endNode(a:GlobalSearchSolver) : void -> a.nbBk :+ 1]
[recordSolution(a:GlobalSearchSolver) : void
 -> a.problem.feasible := true, a.nbSol :+ 1,
    trace(SVIEW,"... solution [~Snd., ~Sbk., ~Sms.]\n",a.nbNd,a.nbBk,time_read()),  // v1.011 <thb>
    if (a.maxSolutionStorage > 0) storeCurrentSolution(a)]  // v1.013
[postDynamicCut(a:GlobalSearchSolver) : void                // v1.019
 -> nil]
[recordSolution(a:AbstractOptimize) : void
 -> let obj := a.objective, objval := getObjectiveValue(a) in
       (a.problem.feasible := true, a.nbSol :+ 1,
        trace(SVIEW,"... solution with ~A:~S [~Snd., ~Sbk., ~Sms.]\n",obj.name,objval,a.nbNd,a.nbBk,time_read()),  // v1.011 <thb>
        setBound(a),  // v1.013
        setTargetBound(a),
        if (a.maxSolutionStorage > 0) storeCurrentSolution(a))] // v1.013

// v1.013 resetting the optimization bounds
[setBound(a:AbstractOptimize) : void
 -> let objval := getObjectiveValue(a) in
       (if a.doMaximize a.lowerBound :max objval
        else a.upperBound :min objval)]
// v1.08: resetting the values of the target bounds (bounds for the remaining search)
[setTargetBound(a:AbstractOptimize) : void
 -> if a.doMaximize setTargetLowerBound(a)
    else setTargetUpperBound(a)]
[setTargetLowerBound(a:AbstractOptimize) : void
 -> let newBound := a.lowerBound + 1 in
     (if not(a.problem.feasible) trace(STALK,"search first sol ...")
      else (a.targetLowerBound := newBound,
            trace(STALK,"search target: ~A >= ~S ... ",a.objective.name,newBound)) )]
[setTargetUpperBound(a:AbstractOptimize) : void
 -> let newBound := a.upperBound - 1 in
     (if not(a.problem.feasible) trace(STALK,"search first sol ...")
      else (a.targetUpperBound := newBound,
            trace(STALK,"search target: ~A <= ~S ... ",a.objective.name,newBound)) )]
// v1.013: propagating the optimization cuts from the new target bounds
[postTargetBound(a:AbstractOptimize) : void
 -> if a.doMaximize postTargetLowerBound(a)
    else postTargetUpperBound(a)]
[postTargetLowerBound(a:AbstractOptimize) : void
 -> setMin(a.objective, a.targetLowerBound)]
[postTargetUpperBound(a:AbstractOptimize) : void
 -> setMax(a.objective, a.targetUpperBound)]

[newTreeSearch(a:OptimizeWithRestarts) : void
 -> a.nbBk := 0, a.nbNd := 0, a.nbIter :+ 1,
    a.baseWorld := world?(), a.baseNbSol := a.nbSol, time_set(),
    postTargetBound(a),  // v1.013
    propagate(a.problem)]
// claire3 port world= -> backtrack
[endTreeSearch(a:OptimizeWithRestarts) : void
 -> a.nbBkTot :+ a.nbBk, a.nbNdTot :+ a.nbNd,
    a.nbBk := 0, a.nbNd := 0,
    backtrack(a.baseWorld),
    time_get() ]  // v1.012: no need to print solution information twice
[postDynamicCut(a:OptimizeWithRestarts) : void
 -> nil]


// generic method
[choco/run(algo:GlobalSearchSolver) : void
 -> error("run is not implemented on ~S",algo)]

// searching for one solution
// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
[choco/run(algo:Solve) : void
 -> newTreeSearch(algo),
    let pb := algo.problem, feasibleRootState := true in
       (try propagate(pb)
        catch contradiction feasibleRootState := false,
        if feasibleRootState
          (try (pushWorld(pb),
                explore(algo.branchingComponents[1],1))
           catch contradiction popWorld(pb)),
           try (if (algo.maxSolutionStorage > 0 & // v1.011 <thb> restoring best solution
                    not(algo.stopAtFirstSol) &
                    existsSolution(algo))
                  restoreBestSolution(algo) )
           catch contradiction error("contradiction while restoring the best solution found: BUG !!")),
    endTreeSearch(algo) ]  // v1.02: returns void

// v0.25 adding a world level for avoiding conflicts between the proof of optimality
// and the restoring of best sol
// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
[choco/run(algo:BranchAndBound) : void
 -> newTreeSearch(algo),
    let pb := algo.problem, feasibleRootState := true in
       (try propagate(pb)
        catch contradiction feasibleRootState := false,
        if feasibleRootState
          (try (pushWorld(pb),
                explore(algo.branchingComponents[1],1),
                popWorld(pb))
           catch contradiction popWorld(pb),  // v1.02 if there was a contradiction, it happened during explore
           try (if (algo.maxSolutionStorage > 0 & existsSolution(algo)) // v0.9907 <thb> checking that one solution has indeed been found
                   restoreBestSolution(algo) )  // 1.011: restore the best solution out of the above try...catch
           catch contradiction error("contradiction while restoring the best solution found: BUG !!")),
        endTreeSearch(algo) )] // v0.9907 <thb>  // v1.02 returns void

// v1.013: we use  targetBound data structures for the optimization cuts
[postDynamicCut(a:BranchAndBound) : void
 -> postTargetBound(a),
    propagate(a.problem)]
[newTreeSearch(a:BranchAndBound) : void
 -> initBounds(a), time_set()]  // v0.9907, v1.013

// v1.02: removed reference to baseWorld
[endTreeSearch(a:BranchAndBound) : void
 -> let t := time_get() in
       (if a.problem.feasible
          //[SVIEW] solve => ~S sol, best:~S [~Snd, ~Sbk], ~S ms.// a.nbSol,(if a.doMaximize a.lowerBound else a.upperBound),a.nbNd,a.nbBk,t
        else //[SVIEW] solve => no sol [~Snd, ~Sbk], ~S ms.// a.nbNd,a.nbBk,t
       )]

[newLoop(a:OptimizeWithRestarts) : void
 -> a.nbSol := 0, a.nbBkTot := 0, a.nbNdTot := 0, a.nbIter := 0,
    initBounds(a), time_set()]  // v1.013
[endLoop(a:OptimizeWithRestarts) : void
 -> let t := time_get() in
    trace(SVIEW,"Optimisation over => ~A(~A) = ~S found in ~S iterations, ~S nodes, ~S backtracks and ~S ms.\n",
           (if a.doMaximize "max" else "min"),a.objective.name,
           getBestObjectiveValue(a),  // v1.013 using the accessor
           a.nbIter,a.nbNdTot,a.nbBkTot,t)]
// v1.08
[recordNoSolution(a:OptimizeWithRestarts) : void
  -> let obj := a.objective, objtgt := getObjectiveTarget(a) in
       (trace(SVIEW,"... no solution with ~A:~S [~Snd., ~Sbk.]\n",obj.name,objtgt,a.nbNd,a.nbBk),
        if a.doMaximize  // v1.014
           a.upperBound :min objtgt - 1
        else a.lowerBound :max objtgt + 1)]

// v1.08: loop until the lower bound equals the upper bound
[oneMoreLoop(a:OptimizeWithRestarts) : boolean
 -> (a.lowerBound < a.upperBound)]

// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
// v1.08: <ega> requires a more careful management of lower/upperBounds
[choco/run(algo:OptimizeWithRestarts) : void // v1.02 returns void
 -> let w := world?() + 1, pb := algo.problem, finished := false in
      (newLoop(algo),
       try propagate(pb)
       catch contradiction (finished := true, recordNoSolution(algo)),
       if not(finished)   // v1.014
         (pushWorld(pb),
          while oneMoreLoop(algo)
            let foundSolution := false in
             (try (newTreeSearch(algo),
                if explore(algo.branchingComponents[1],1)
                   foundSolution := true)
              catch contradiction nil,
              endTreeSearch(algo),
              if not(foundSolution) recordNoSolution(algo)
             ),
          assert(world?() = w),
          popWorld(pb)),
       endLoop(algo),
       if (algo.maxSolutionStorage > 0 & existsSolution(algo)) // v0.9907 <thb> checking that one solution has indeed been found
          restoreBestSolution(algo))]  // v1.02 returns void
// ********************************************************************
// *   Part 5: utils and solution management                          *
// ********************************************************************

// V0.9907
[choco/makeConflictCount() : ConflictCount
 -> ConflictCount(nbViolatedConstraints = 0,
;                  conflictingVars = list<IntVar>(),
;                  assignedThenUnassignedVars = list<IntVar>(),
                  lastAssignedVar = 0) ]

[choco/attachInvariantEngine(pb:Problem, ie:InvariantEngine) : void
 -> pb.invariantEngine := ie,
    ie.problem := pb]

// returns +1 when the status of c changes from true to false when v goes from a to b
//         -1                                   false to true
//         0  when c does not change (remains satisfied or violated)
[private/unitaryDeltaConflict(v:IntVar,a:integer,b:integer,c:AbstractConstraint) : integer
 -> assert(v.value = a),
    assert(testIfInstantiated(c)),
    let OKbefore := not(c.violated),
        OKafter := OKbefore in 
      (v.value := b, OKafter := testIfTrue(c), v.value := a,
       //[LDEBUG]:  -- flipping ~S from ~S to ~S: ~S goes from ~S to ~S // v,a,b,c,OKbefore,OKafter,
       if (OKafter = OKbefore) 0  // no change for the constraint
       else if (OKbefore) 1       // the constraint became a conflict
       else -1) ]                 // the conflicting constraint was repaired

// computes the change in the number of conflicting constraints
// when v is flipped from value a to b
[choco/deltaNbConflicts(v:IntVar,a:integer,b:integer) : integer
 -> let delta := 0 in
      (for c in v.constraints
          delta :+ unitaryDeltaConflict(v,a,b,c),
       //[LDEBUG] flipping ~S from ~S to ~S yields delta = ~S // v,a,b,delta,
       delta)]

[choco/getMinConflictValue(v:IntVar) : integer
 -> let a := v.value, minDelta := MAXINT, bestvalue := a in
       (for b in (domain(v) but a)
            let delta := deltaNbConflicts(v,a,b) in
               (//[LDEBUG] for ~S(~S), value ~S adds ~S conflicts // v,a,b,delta,
                if (delta < minDelta) (minDelta := delta, bestvalue := b)),
        bestvalue)]

// saving and retrieving assignments
[choco/computeConflictAccount(ie:ConflictCount) : void
 -> let p := ie.problem in
       (for v in p.vars
            v.nbViolatedConstraints := 0,
        ie.nbViolatedConstraints := 0,
        for c in {c2 in p.constraints | testIfInstantiated(c2)}  // should be extended for handling global constraints
           (if testIfTrue(c)  // v0.26 wrong interface call: testIfTrue instead of testIfSatisfied
               c.violated := false
            else (c.violated := true,
                  ie.nbViolatedConstraints :+ 1,
                  case c (UnIntConstraint c.v1.nbViolatedConstraints :+ 1,
                          BinIntConstraint (c.v1.nbViolatedConstraints :+ 1,
                                            c.v2.nbViolatedConstraints :+ 1),
                          LargeIntConstraint                // v0.15
                            for v in c.vars
                                v.nbViolatedConstraints :+ 1,
                          any error("Cannot build assignment with such constraint ~S",c)))),
        ie.conflictingVars := list<IntVar>{v in p.vars | v.inf < v.sup & v.nbViolatedConstraints > 0}) ]

// this function is called when the local search mode is started after a global search
// (feasible mode)
[choco/resetInvariants(ie:InvariantEngine) : void -> error("resetInvariants should be defined for ~S",ie)]
[choco/resetInvariants(ie:ConflictCount) : void
 -> let p := ie.problem in
       (// all constraints are either yet uninstantiated, or trivially satisfied
        assert(forall(c in p.constraints |
                        (not(testIfInstantiated(c)) | testIfTrue(c)))),
        for c in p.constraints c.violated := false,
        for v in p.vars v.nbViolatedConstraints := 0,
        ie.nbViolatedConstraints := 0)]

// interface
[choco/getLocalSearchObjectiveValue(ie:InvariantEngine) : integer -> error("getObjective should be defined for ~S",ie), 0]
[choco/getLocalSearchObjectiveValue(ie:ConflictCount) : integer -> ie.nbViolatedConstraints]

[choco/restoreBestAssignment(algo:LocalSearchSolver) : void
 -> //[LTALK] restoring the best assignment found so far //,
    let p := algo.problem in
       (for v in {v1 in p.vars | v1.inf < v1.sup}
            v.value := v.savedAssignment,
        computeConflictAccount(p.invariantEngine))]

[choco/saveAssignment(algo:LocalSearchSolver) : void
 -> for v in algo.problem.vars v.savedAssignment := v.value]

// v0.9907
;[choco/setFeasibleMode(pb:Problem, b:boolean) : void
; -> if (b != pb.feasibleMode)
;       (if (b = false)
;           let ie := pb.invariantEngine,
;               l1 := list<IntVar>{v in pb.vars | v.inf = v.sup},
;               l2 := list<IntVar>{v in pb.vars | v.inf != v.sup} in
;            (ie.assignedThenUnassignedVars := (l1 /+ l2),
;             ie.lastAssignedVar := length(l1),
;             resetInvariants(ie)),
;         pb.feasibleMode := false)]
[choco/setFeasibleMode(pb:Problem, b:boolean) : void
 -> if (b != pb.feasibleMode)
       (if (b = false)
           let ie := (pb.invariantEngine as ConflictCount), 
               l := ie.assignedThenUnassignedVars in
            (shrink(l, 0),
             for v in pb.vars 
                (if (v.inf = v.sup) add(l,v)),
             let n := length(l) in 
               ie.lastAssignedVar := n,
             for v in pb.vars 
                (if (v.inf != v.sup) add(l,v)),
             resetInvariants(ie)),
         pb.feasibleMode := false)]

; TODO: watchout: commenting out the 2 next lines may prevent from doing more than one move of local opt
;    for v in a.problem.unassignedVars
;       erase(value,v),

;choco/ConstructiveHeuristic <: Ephemeral(
;   private/manager:LocalSearchSolver)
choco/AssignmentHeuristic <: ConstructiveHeuristic()

;choco/MoveNeighborhood <: Ephemeral(
;    private/manager:LocalSearchSolver)

choco/FlipNeighborhood <: MoveNeighborhood()

;choco/LocalSearchSolver <: Solver(
;    // slots for generating an assignment
;   choco/buildAssignment:ConstructiveHeuristic,
;   choco/changeAssignment:MoveNeighborhood,
;   choco/maxNbLocalSearch:integer = 20,
;   choco/maxNbLocalMoves:integer = 5000)
;(store(assignedThenUnassignedVars,lastAssignedVar)) // v0.37: haveAwakenConstraints need to be stored

choco/MultipleDescent <: LocalSearchSolver(
   choco/nbLocalSearch:integer = 0,
   choco/nbLocalMoves:integer = 0)

// ********************************************************************
// *   Part 6: Feeding the engine with a library of neighborhoods     *
// ********************************************************************

[selectMoveVar(a:AssignmentHeuristic) : (IntVar U {unknown})
 -> let ie := a.manager.problem.invariantEngine, l := ie.assignedThenUnassignedVars,
        n := length(l), j := ie.lastAssignedVar in
     (if (j = n) unknown
      else l[j + 1 + random(n - j)])]
[selectValue(a:AssignmentHeuristic, v:IntVar) : integer
 -> if unknown?(bucket,v) random(v.inf,v.sup)
    else random(v.bucket)]
[assignOneVar(a:AssignmentHeuristic) : boolean
 -> when v := selectMoveVar(a) in
         let val := selectValue(a,v) in
            (assignVal(v,val),
             true)
    else false]
[build(a:AssignmentHeuristic) : void
 -> eraseAssignment(a.manager.problem.invariantEngine),
    while assignOneVar(a) nil,
  ;  maintainConflictAccount(a),
    //[LTALK]   ++ initial solution built with ~S violated const. // a.manager.problem.invariantEngine.nbViolatedConstraints,
    checkCleanState(a.manager.problem.invariantEngine)]

// v1.0 erasing the previous assignment before constructing a new one.
[choco/eraseAssignment(ie:ConflictCount) : void // v1.02 return void
 -> let pb := ie.problem in 
     (for v:IntVar in pb.vars // claire3 port: type annotation for v
         (if (v.inf != v.sup)
;             (erase(value,v as IntVar), // claire3 port: type annotation for v
             (v.value := UNKNOWNINT, 
              v.nbViolatedConstraints := 0)),
      ie.conflictingVars := list<IntVar>{v in pb.vars | v.nbViolatedConstraints > 0}, 
      let nbconflicts := 0 in
        (for c in pb.constraints 
           (if testIfInstantiated(c)
              (if not(testIfTrue(c)) nbconflicts :+ 1)
            else c.violated := false),
         ie.nbViolatedConstraints := nbconflicts),
;      let l1 := list{v in pb.vars | v.inf = v.sup},
;          l2 := list{v in pb.vars | v.inf != v.sup} in
;        (ie.assignedThenUnassignedVars := (l1 /+ l2),
;         ie.lastAssignedVar := length(l1))  
      let l := ie.assignedThenUnassignedVars in
        (shrink(l, 0),
         for v in pb.vars 
            (if (v.inf = v.sup) add(l,v)),
         let n := length(l) in 
             ie.lastAssignedVar := n,
         for v in pb.vars 
            (if (v.inf != v.sup) add(l,v)) )
      )]
 
// checks that the invariant engine is in a proper state
[choco/checkCleanState(ie:ConflictCount)
 => #if (compiler.active? & compiler.safety > 2) nil
    else let pb := ie.problem, bizarre := false in
      (for c in list{c in pb.constraints | testIfInstantiated(c)}
          (if (c.violated = testIfTrue(c))
              (printf("~S registered as violated:~S, while testIfTrue:~S\n",c,c.violated,testIfTrue(c)),
               bizarre := true)),      
       if (ie.nbViolatedConstraints != count(list{c in pb.constraints | c.violated = true}))
          (printf("wrong violated constraint count:~S instead of ~S",
                  ie.nbViolatedConstraints,count(list{c in pb.constraints | c.violated = true})),
           bizarre := true),
       for v in pb.vars 
          (if (v % ie.conflictingVars)
              (if forall(c in v.constraints | c.violated = false)
                 (printf("~S registered as conflicting with all constraints OK\n",v),
                  bizarre := true))
           else (when c := some(c in v.constraints | c.violated = true) in 
                    (printf("~S not registered as conflicting, while ~S is violated\n",v,c),
                     bizarre := true))),
      if bizarre error("corrupted state for ~S, stop and look why",ie) )]

// TODO: there should be another move scheme than FlipNeighborhood
// select a constraint rather than a variable and repair it
//       incremental structure => keep the sublist of violated constraints

[selectMoveVar(m:FlipNeighborhood) : (IntVar U {unknown})
 -> let l := m.manager.problem.invariantEngine.conflictingVars in
       (if l (random(l) as IntVar)
        else unknown)]
[newValue(m:FlipNeighborhood, v:IntVar) : integer
 -> getMinConflictValue(v)]

[move(m:FlipNeighborhood) : boolean
 -> when v := selectMoveVar(m) in
      let b := newValue(m,v) in
         (changeVal(v,b), true)
    else (assert(length(m.manager.problem.invariantEngine.conflictingVars) = 0),
          false)]
[finishedLoop(algo:LocalSearchSolver) : boolean
 -> let ie := algo.problem.invariantEngine in
       (if (ie.nbViolatedConstraints = 0)
           (assert(length(ie.conflictingVars) = 0),
            true)
        else (assert(length(ie.conflictingVars) > 0),
              if (algo.nbLocalMoves >= algo.maxNbLocalMoves) true
              else false))]
[initLoop(algo:LocalSearchSolver) : void
 -> algo.nbLocalSearch :+ 1,
    algo.nbLocalMoves := 0]
[endLoop(algo:LocalSearchSolver) : void
 -> let ie := algo.problem.invariantEngine in
       (if (ie.nbViolatedConstraints < ie.minNbViolatedConstraints)
           (//[LVIEW] found a solution with ~S violated constraints // ie.nbViolatedConstraints,
            ie.minNbViolatedConstraints := ie.nbViolatedConstraints,
            saveAssignment(algo)))]

[choco/copyParameters(oldSolver:LocalSearchSolver, newSolver:LocalSearchSolver) : void
 -> when nbs := get(maxNbLocalSearch, oldSolver) in newSolver.maxNbLocalSearch := nbs,
    when nbm := get(maxNbLocalMoves, oldSolver) in newSolver.maxNbLocalMoves := nbm]

[choco/attachLocalSearchSolver(pb:Problem, newSolver:LocalSearchSolver) : void
 -> when oldSolver := get(localSearchSolver,pb) in
         copyParameters(oldSolver, newSolver),
    pb.localSearchSolver := newSolver,
    newSolver.problem := pb]

// ********************************************************************
// *   Part 7: Using neighborhood classes within LocalSearchSolver's  *
// ********************************************************************

[runLocalSearch(algo:LocalSearchSolver) : void
 -> setFeasibleMode(algo.problem,false),  // v0.9907
    initIterations(algo),
    while not(finishedIterations(algo))
       (build(algo.buildAssignment),
        initLoop(algo),
        while not(finishedLoop(algo))
             (move(algo.changeAssignment),
              oneMoreMove(algo)),
        endLoop(algo)),
    restoreBestAssignment(algo)]

// v0.9906
[initIterations(algo:MultipleDescent) : void
 -> let pb := algo.problem, pe := pb.propagationEngine in 
       (if (; getNbObjects(pe.delayedConst1.partition) > 0 |
            getNbObjects(pe.delayedConst2.partition) > 0 |
            getNbObjects(pe.delayedConst3.partition) > 0 |
            getNbObjects(pe.delayedConst4.partition) > 0)
          error("local moves not implemented on problems with global constraints"),
        if exists(c in pb.constraints | c % CompositeConstraint)  // v1.010
           error("local moves not implemented on problems with Boolean combinations of constraints"),
        algo.nbLocalSearch := 0)]

// V0.9907
[choco/finishedIterations(algo:LocalSearchSolver) : boolean
 -> (algo.nbLocalSearch >= algo.maxNbLocalSearch |
     algo.problem.invariantEngine.minNbViolatedConstraints = 0)]
// V0.9907
[choco/oneMoreMove(algo:LocalSearchSolver) : void
 -> algo.nbLocalMoves :+ 1]

// v1.0 generic event: a constraint becomes conflicting
[choco/becomesAConflict(c:AbstractConstraint,ie:ConflictCount) : void
 -> c.violated := true, ie.nbViolatedConstraints :+ 1,
    let n := getNbVars(c) in 
     (for i in (1 .. n) addOneConflict(ie,getVar(c,i) as IntVar))]
[choco/becomesAConflict(c:UnIntConstraint,ie:ConflictCount) : void
 -> c.violated := true, ie.nbViolatedConstraints :+ 1,
    addOneConflict(ie,c.v1)]
[choco/becomesAConflict(c:BinIntConstraint,ie:ConflictCount) : void
 -> c.violated := true, ie.nbViolatedConstraints :+ 1,
    addOneConflict(ie,c.v1), addOneConflict(ie,c.v2)]
[choco/becomesAConflict(c:TernIntConstraint,ie:ConflictCount) : void
 -> c.violated := true, ie.nbViolatedConstraints :+ 1,
    addOneConflict(ie,c.v1), addOneConflict(ie,c.v2), addOneConflict(ie,c.v3)]
[choco/becomesAConflict(c:LargeIntConstraint,ie:ConflictCount) : void
 -> c.violated := true, ie.nbViolatedConstraints :+ 1,
    for v in c.vars addOneConflict(ie, v)] 

// v1.0 generic event: a constraint becomes un-conflicting
[choco/becomesSatisfied(c:AbstractConstraint,ie:ConflictCount) : void
 -> c.violated := false, ie.nbViolatedConstraints :- 1,
    let n := getNbVars(c) in 
     (for i in (1 .. n) removeOneConflict(ie,getVar(c,i) as IntVar))]
[choco/becomesSatisfied(c:UnIntConstraint,ie:ConflictCount) : void
 -> c.violated := false, ie.nbViolatedConstraints :- 1,
    removeOneConflict(ie,c.v1)]
[choco/becomesSatisfied(c:BinIntConstraint,ie:ConflictCount) : void
 -> c.violated := false, ie.nbViolatedConstraints :- 1,
    removeOneConflict(ie,c.v1), removeOneConflict(ie,c.v2)]
[choco/becomesSatisfied(c:TernIntConstraint,ie:ConflictCount) : void
 -> c.violated := false, ie.nbViolatedConstraints :- 1,
    removeOneConflict(ie,c.v1), removeOneConflict(ie,c.v2), removeOneConflict(ie,c.v3)]
[choco/becomesSatisfied(c:LargeIntConstraint,ie:ConflictCount) : void
 -> c.violated := false, ie.nbViolatedConstraints :- 1,
    for v in c.vars removeOneConflict(ie, v)] 

// v1.0 two simple uitilities
// addOneConflict registers that variable v appears in one more conflicting constraints
[choco/addOneConflict(ie:ConflictCount,v:IntVar) : void
 -> v.nbViolatedConstraints :+ 1,
    if (v.nbViolatedConstraints = 1) ie.conflictingVars :add v
    else assert(v % ie.conflictingVars)]

// register that variable v appears in one conflicting constraint less
[choco/removeOneConflict(ie:ConflictCount,v:IntVar) : void
 -> v.nbViolatedConstraints :- 1,
    if (v.nbViolatedConstraints = 0) ie.conflictingVars :delete v
    else assert(v % ie.conflictingVars)]

// Note: the events are not stored: immediate reaction
// we maintain incrementally:
//   - the sub-list of instantiated variables
//   - for each constraint, its status (violated vs. satisfied)
//   - a count of all conflicts (total #of constraints, #of constraints per var)
//   - a sub-list of variables occurring in violated constraints
[choco/postAssignVal(ie:ConflictCount,v:IntVar,a:integer) : void
 -> let l := ie.assignedThenUnassignedVars, n := length(l),
        i := get(l,v), j := ie.lastAssignedVar in
   (if (i = 0) error("~S is not present in algo.unassignedVars",v)
    else (assert(i > j & i <= n),  // <fl> 0.36 i may point to the last variable from l
          //[IDEBUG] assign ~S to ~Sth unassigned var (~S), cursor:~S // a,i,v,j,
          store(l,i,l[j + 1],true),
          store(l,j + 1,v,true),
          ie.lastAssignedVar := j + 1,
          // <fx> 0.34 incremental conflict counts
          for c in v.constraints
             (if testIfInstantiated(c)
                (if testIfTrue(c) c.violated := false
                 else becomesAConflict(c,ie)))))]

// The reaction to the unassignment is performed while the value is still in place
// the value is erased only after the reaction
[choco/postUnAssignVal(ie:ConflictCount,v:IntVar) : void
 -> let l := ie.assignedThenUnassignedVars, n := length(l),
        i := get(l,v), j := ie.lastAssignedVar in
     (if (i = 0) error("~S is not present in pb.unassignedVars",v),
      assert(i <= j & j <= n),
      assert(isInstantiated(v)),
      // <fx> 0.34 decremental conflict counts
      for c in v.constraints
         (if (testIfInstantiated(c) & not(testIfTrue(c)))
              becomesSatisfied(c,ie),
          store(l,i,l[j],true),
          store(l,j,v,true),
          ie.lastAssignedVar := j - 1))]

[choco/postChangeVal(ie:ConflictCount,v:IntVar,a:integer,b:integer) : void
 -> assert(v.value = a),
    let nbConflictsBefore := v.nbViolatedConstraints in
      (//[LTALK] change val ~S [~S]:~S -> ~S // v,nbConflictsBefore,a,b,       
       if (nbConflictsBefore = 0 & get(ie.conflictingVars,v) != 0)
          error("~S with no conflicts and conflictingVars:~S",v,ie.conflictingVars),
       assert((if (nbConflictsBefore = 0) (get(ie.conflictingVars,v) = 0) else true)),
       for c in v.constraints // should be extended for handling global constraints
         let OKBefore := not(c.violated),
             OKNow := OKBefore in 
          (v.value := b, OKNow := testIfTrue(c),
           //[LDEBUG] ---- ~S: ~S -> ~S // c,OKBefore, OKNow,
           if (OKNow != OKBefore)
              (if OKNow                            // TODO: shrink list of violated constraints
                 (if not(c.violated) error("~S should be violated and c.viol=~S",c,c.violated),
                  assert(c.violated = true),
                  becomesSatisfied(c,ie))
               else
                 (if c.violated error("~S should not be violated and c.viol=~S",c,c.violated),
                  assert(c.violated = false),
                  becomesAConflict(c,ie))),
           v.value := a) )]
