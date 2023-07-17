// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 

// ** Summary : PaLM API
// Part 1  : Problems                
// Part 2  : Variables
// Part 3  : Posting constraints                             
// Part 4  : Removing constraints                             
// Part 5  : Propagating and solving problems
// Part 6  : Assignment constraints
// Part 7  : Negating constraints
// Part 8  : arithmetic constraints                         
// Part 9  : global constraints 
// Part 10 : boolean connectors 
// Part 11 : user-friendly tools                           

// *************************************************************
// *   Part 1 : Problems                                       *
// *************************************************************

// creating a problem
[makePalmProblem(s: string, n: integer) : PalmProblem
 -> let pb := PalmProblem(name = copy(s),
                      globalSearchSolver = choco/GlobalSearchSolver(),
                      localSearchSolver = choco/LocalSearchSolver() )  in
      (;if (world?() != 0) trace(0,"return to root world ..."),
       ;world=(0),
	   let pe := makePalmEngine(n + 1) in attachPropagationEngine(pb,pe),
       initPalmSolver(pb),

	   pb.rootUFboxes := PalmUserFriendlyBox(shortName = "PB", name = "The complete problem"),
	   UFcurrentBox := pb.rootUFboxes,
	   pb.userRepresentation :add  pb.rootUFboxes,

       setActiveProblem(pb),
       pb)]

[makePalmProblem(s: string, n: integer, maxRlxLvl: integer) : PalmProblem
 -> let pb := makePalmProblem(s, n)
	in (
		pb.maxRelaxLvl := maxRlxLvl,
		pb
	)]

[setObjective(pb: PalmProblem, v: PalmIntVar): void
 => pb.palmSolver.objective := v]

[setSolutionVars(pb: PalmProblem, lv: list[PalmIntVar]): void
 => pb.palmSolver.varsToStore := list<IntVar>{v | v in lv} ]

[solutions(pb: PalmProblem): list<Solution>
 => pb.palmSolver.solutions]


// solve for possibly all solutions ...
[solve(pb: PalmProblem, lbr: list[PalmBranching], allSolutions: boolean) : void
 -> attachPalmBranchings(pb, lbr), 
	if (allSolutions) (
		let st := pb.palmSolver.state,
			soluble := searchOneSolution(pb) // premiere solution
		in (
			while (soluble) (
				soluble := discardCurrentSolution(st) & searchOneSolution(pb)
			)
		)
	) 
	else 
		solve(pb)]

[solve(pb: PalmProblem, allSolutions: boolean) : void
 -> 
	//[PalmDEBUG] PALM: initiating solving ,
	if (allSolutions) (
		let st := pb.palmSolver.state,
			soluble := searchOneSolution(pb) // premiere solution
		in (
			while (soluble) (
				soluble := discardCurrentSolution(st) & searchOneSolution(pb)
			)
		)
	) 
	else 
		searchOneSolution(pb)]

[solve(pb: PalmProblem, lbr: list[PalmBranching]) : void 
 -> attachPalmBranchings(pb, lbr),
	searchOneSolution(pb)]

[solve(pb: PalmProblem) : void 
 -> solve(pb, false)]


[minimize(pb: PalmProblem, obj: PalmIntVar, lbr: list[PalmBranching]) : integer
 -> attachPalmBranchings(pb, lbr),
	minimize(pb, obj)]

[minimize(pb: PalmProblem, obj: PalmIntVar) : integer
 -> initPalmBranchAndBound(pb, false, obj),
	run(pb.palmSolver),
	pb.palmSolver.upperBound]

[minimize(pb: PalmProblem, obj: PalmIntVar, lb: integer, ub: integer, lbr: list[PalmBranching]): integer
 -> attachPalmBranchings(pb, lbr),
	minimize(pb, obj, lb, ub)]

[minimize(pb: PalmProblem, obj: PalmIntVar, lb: integer, ub: integer): integer
 -> initPalmBranchAndBound(pb, false, obj),
	pb.palmSolver.upperBound := ub,
	pb.palmSolver.lowerBound := lb, 
	run(pb.palmSolver),
	pb.palmSolver.upperBound]


[maximize(pb: PalmProblem, obj: PalmIntVar, lbr: list[PalmBranching]) : integer
 -> attachPalmBranchings(pb, lbr),
	maximize(pb, obj)]

[maximize(pb: PalmProblem, obj: PalmIntVar): integer
 -> initPalmBranchAndBound(pb, true, obj),	
	run(pb.palmSolver),
	pb.palmSolver.lowerBound]


[maximize(pb: PalmProblem, obj: PalmIntVar, lb: integer, ub: integer, lbr: list[PalmBranching]): integer
 -> attachPalmBranchings(pb, lbr),
	maximize(pb, obj, lb, ub)]

[maximize(pb: PalmProblem, obj: PalmIntVar, lb: integer, ub: integer): integer
 -> initPalmBranchAndBound(pb, true, obj),	
	pb.palmSolver.upperBound := ub,
	pb.palmSolver.lowerBound := lb, 
	run(pb.palmSolver),
	pb.palmSolver.lowerBound]


// *************************************************************
// *   Part 2 : Variables                                      *
// *************************************************************

// creating a variable on the fly (note the same API that choco)
[makeBoundIntVar(p:PalmProblem, s:string, i:integer, j:integer) : PalmIntVar
 -> assert(i <= j),
    let v := PalmIntVar(name = copy(s), originalInf = i, originalSup = j) 
	in (
		closeIntVar(v,i,j,1),  
        addIntVar(p,v),
		// initialising PaLM specific slots
		v.restInfEvt := DecInf(modifiedVar = v),
		v.restSupEvt := IncSup(modifiedVar = v),
   	    v.explanationOnInf :add makeIncInfExplanation(Explanation(), i, v),
	    v.explanationOnSup :add makeDecSupExplanation(Explanation(), j, v),
	    v.restValEvt := makeValueRestorations(v, 0),
        v
	)]

// creating a variable on the fly (note the same API that choco)
[makeIntVar(p:PalmProblem, s:  string, i: integer, j: integer): PalmIntVar 
 -> assert(i <= j),
	let v := PalmIntVar(name = copy(s), originalInf = i, originalSup = j)
	in (
		closeIntVar(v,i,j,j - i + 2),  // v0.9903: react to (up to 5) removal one by one
        addIntVar(p,v),
        v.bucket := makePalmIntDomain(i,j),
		v.restInfEvt := DecInf(modifiedVar = v),
		v.restSupEvt := IncSup(modifiedVar = v),
   	    v.explanationOnInf :add makeIncInfExplanation(Explanation(), i, v),
	    v.explanationOnSup :add makeDecSupExplanation(Explanation(), j, v),
		v.restValEvt := makeValueRestorations(v,j - i + 2),
        v
	)]

[makeIntVar(p:PalmProblem, s:string, b:(list[integer] U set[integer])) : PalmIntVar
 -> let minVal := min(b),  // v1.02 min vs. Min
        maxVal := max(b),  // v1.02 max vs. Max
        v := makeIntVar(p,s,minVal,maxVal) in
      (for val in list{val2 in (minVal .. maxVal) | not(val2 % b)}
           removeDomainVal(v.bucket, val),
       v)]
// v0.26 stronger typing of b
[makeIntVar(p:PalmProblem, b:(list[integer] U set[integer])) : PalmIntVar -> makeIntVar(p,"<PalmIntVar>",b)]



[makeConstantPalmIntVar(x:integer) : PalmIntVar
 -> let v := PalmIntVar(name = "'" /+ string!(x) /+ "'", originalInf = x, originalSup = x) 
	in (
		closeIntVar(v,x,x,2), 
		v.restInfEvt := DecInf(modifiedVar = v),
		v.restSupEvt := IncSup(modifiedVar = v),
   	    v.explanationOnInf :add makeIncInfExplanation(Explanation(), x, v),
	    v.explanationOnSup :add makeDecSupExplanation(Explanation(), x, v),
	    v.restValEvt := makeValueRestorations(v, 0),
        v		
	)]
		


// *************************************************************
// *   Part 3 : Posting constraints                            *
// *************************************************************

// posting a constraint into a problem
[post(p: PalmProblem, c: (PalmUnIntConstraint U PalmBinIntConstraint U PalmLargeIntConstraint)): void
 ->	let evCon := c.hook.everConnected,
		pe := p.propagationEngine, 
		prio := getPriority(c),
		e := (if evCon c.constAwakeEvent else choco/ConstAwakeEvent(touchedConst = c, initialized = false, priority = prio))
	in (
		if (PALM_USER_FRIENDLY) (
			c.hook.ufBox := UFcurrentBox,
			UFcurrentBox.constraints :add c
		),
		if not(evCon) p.constraints :add c, 
		c.constAwakeEvent := e,
		if not(evCon) registerEvent(pe,e),
		constAwake(c,true),
		if not(evCon) connect(c) else reconnect(c)
	)]


// posting a list of constraints into a problem
[post(p: PalmProblem, cl: list[AbstractConstraint]) : void
 -> for c in cl post(p,c)]

// posting a constraint with a preference (weigth)
[post(p: PalmProblem, c: AbstractConstraint, w: integer) : void
 -> post(p,c), c.hook.weight := w]

[post(p: PalmProblem, d: Delayer, w: integer) : void
 -> post(p,d), d.target.hook.weight := w]


// posting a list of constraints with a common weight
[post(p: PalmProblem, cl: list[AbstractConstraint], w: integer) : void
 -> for c in cl post(p,c,w)]

// posting an indirect constraint with an explanation
[post(p: PalmProblem, c: AbstractConstraint, e: Explanation): void
 -> setIndirect(c, e),
	setIndirectDependance(c, e),
	post(p,c)]

[post(p: PalmProblem, d: Delayer, e: Explanation): void
 -> setIndirect(d.target, e),
	setIndirectDependance(d.target, e),
	post(p,d)]


// posting a list of indirect constraints with a common explanation
[post(p: PalmProblem, cl: list[AbstractConstraint], e: Explanation) : void
 -> for c in cl post(p,c,copy(e))]

// further constraining a domain: adding information to the current state
// (restricting by hand the domain of a variable)
[setMin(v:PalmIntVar, x:integer) : void => post(getProblem(v), v >= x)]
[setMax(v:PalmIntVar, x:integer) : void => post(getProblem(v), v <= x)]
[setVal(v:PalmIntVar, x:integer) : void => post(getProblem(v), v == x)]
[remVal(v:PalmIntVar, x:integer) : void => post(getProblem(v), v !== x)]


// *************************************************************
// *   Part 4 : Removing constraints                           *
// *************************************************************

// removing a single constraint
[remove(pb: PalmProblem, ct: AbstractConstraint) : void
 -> remove(pb.propagationEngine, ct) ]

// Removing a set of constraints in one single step 
[remove(pb: PalmProblem, cl: set[AbstractConstraint]): void
 -> remove(pb.propagationEngine, list!(cl))]

// Removing a list of constraints in one single step 
[remove(pb: PalmProblem, cl: list[AbstractConstraint]): void
 -> remove(pb.propagationEngine, cl)]


// *************************************************************
// *   Part 5 : Propagating and solving problems               *
// *************************************************************

// full propagation (all levels) for a problem: this is the only public propagation method
[propagate(p:PalmProblem) : void
 -> let pe := p.propagationEngine, someEvents := true 
	in (
		while someEvents (
			when q := getNextActiveEventQueue(pe) 
			in popSomeEvents(q)
            else someEvents := false
		)		
	)]


// ********************************************************************
// *   Part 6 : Instanciation constraints                             *
// ********************************************************************


// util: build a table with:
//       - domain: 'memberType'
//       - range: integer 
//       - defaultValue:0
[private/makeConstraintTable(memberType:type) : table[range = AbstractConstraint]
-> let t := (mClaire/new!(table) as table) in // claire3 port: make_object -> new
     (put(range,t,AbstractConstraint),
      put(domain,t,memberType),
      write(default,t,UNKNOWN_ABS_CT),
	  // claire3 port: module System has been replaced by mClaire
      write(mClaire/graph,t,make_list(29,unknown)),
      t)]



[assign(v: PalmIntVar, x: integer) : AssignmentConstraint
 ->	if known?(bucket, v) (
		when ct := (v.bucket.instantiationConstraints[x - v.bucket.offset] as AssignmentConstraint)
		in (
			ct
		)
		else (
			let ct := (makePalmUnIntConstraint(AssignmentConstraint,v,x) as AssignmentConstraint)
			in (
				v.bucket.instantiationConstraints[x - v.bucket.offset] := ct,
				v.bucket.negInstantiationConstraints[x - v.bucket.offset] := (v !== x),
				ct
			)
		)	
	)
	else let ct := UNKNOWN_ABS_CT 
		 in (
			if not(known?(enumerationConstraints, v)) (
				v.enumerationConstraints := makeConstraintTable(integer),
				v.negEnumerationConstraints := makeConstraintTable(integer)
			) 
			else (
				ct := (v.enumerationConstraints[x] as AssignmentConstraint)
			),
			if (ct != UNKNOWN_ABS_CT)
				(ct as AssignmentConstraint)
			else (
				ct := makePalmUnIntConstraint(AssignmentConstraint, v, x), 
				v.enumerationConstraints[x] := ct,
				v.negEnumerationConstraints[x] := (v !== x),
				(ct as AssignmentConstraint)
			)
		 )]	


// ********************************************************************
// *   Part 7 : Negating constraints                                  *
// ********************************************************************


// negate may return unknown if the constraint is already subsumed
[negate(ct: AbstractConstraint) : (AbstractConstraint U {unknown})
 -> error("PALM error : unable to negate constraint ~S (~S) during search (palmapi.cl)", ct, ct.isa),
	unknown]
(abstract(negate))


[negate(ct: AssignmentConstraint) : (AbstractConstraint U {unknown})
 -> let v := ct.v1 
	in (
		if known?(bucket, v) (
			v.bucket.negInstantiationConstraints[ct.cste - ct.v1.bucket.offset]
		) 
		else (
			v.negEnumerationConstraints[ct.cste]
		)
	)]

[opposite(ct: AssignmentConstraint) : (AbstractConstraint U {unknown})
 -> when opp := negate(ct)
	in opp
	else (ct.v1 !== ct.cste)]

[negate(ct: PalmLessOrEqualxc) : (AbstractConstraint U {unknown})
 -> opposite(ct) ]

[negate(ct: PalmGreaterOrEqualxc) : (AbstractConstraint U {unknown})
 -> opposite(ct) ]


[opposite(c:PalmGreaterOrEqualxc) : PalmLessOrEqualxc -> makePalmUnIntConstraint(PalmLessOrEqualxc, c.v1,c.cste - 1)]
[opposite(c:PalmLessOrEqualxc) : PalmGreaterOrEqualxc -> makePalmUnIntConstraint(PalmGreaterOrEqualxc, c.v1, c.cste + 1)]
[opposite(c:PalmEqualxc) : PalmNotEqualxc -> makePalmUnIntConstraint(PalmNotEqualxc, c.v1, c.cste)]
[opposite(c:PalmNotEqualxc) : PalmEqualxc -> makePalmUnIntConstraint(PalmEqualxc, c.v1, c.cste)]
[opposite(c:PalmEqualxyc) : PalmNotEqualxyc -> makePalmBinIntConstraint(PalmNotEqualxyc, c.v1, c.v2, c.cste)]
[opposite(c:PalmNotEqualxyc) : PalmEqualxyc -> makePalmBinIntConstraint(PalmEqualxyc, c.v1, c.v2, c.cste)]

// v1.0: the constraint LessOrEqual disappears
// variable 1 in (x >= y + c) becomes variable 2 in not(x >= y + c) rewritten (y >= x - c + 1)
[opposite(c:PalmGreaterOrEqualxyc) : PalmGreaterOrEqualxyc
  -> makePalmBinIntConstraint(PalmGreaterOrEqualxyc, c.v2, c.v1, -(c.cste) + 1)]
[choco/computeVarIdxInOpposite(c:PalmGreaterOrEqualxyc,i:integer) : integer
 -> (if (i = 1) 2 else 1)]




[opposite(c:PalmGuard) : PalmConjunction
 -> let d := PalmConjunction(const1 = c.const1, const2 = opposite(c.const2)) in
      (closeBoolConstraint(d), initHook(d), d)]
[opposite(c:PalmConjunction) : PalmDisjunction
 -> let d := PalmDisjunction(const1 = opposite(c.const1), const2 = opposite(c.const2)) in
      (closeBoolConstraint(d), initHook(d), d)]
[opposite(c:PalmDisjunction) : PalmConjunction
 -> let d := PalmConjunction(const1 = opposite(c.const1), const2 = opposite(c.const2)) in
      (closeBoolConstraint(d), initHook(d), d)]
// v0.9903 <ebo>, <fxj>
// v1.02: fix the range
[opposite(c:PalmLargeDisjunction) : PalmLargeConjunction
 -> let d := PalmLargeConjunction(lconst = list<AbstractConstraint>{opposite(subc) | subc in c.lconst}) in
      (closeBoolConstraint(d), initHook(d), d)]

[opposite(c:PalmLargeConjunction) : PalmLargeDisjunction
 -> let d := PalmLargeDisjunction(lconst = list<AbstractConstraint>{opposite(subc) | subc in c.lconst},
								  needToAwake = make_list(c.nbConst, boolean, false)) in
      (closeBoolConstraint(d), initHook(d), d)]

[opposite(c:PalmEquiv) : PalmCardinality
  -> e-card(list<(IntConstraint U CompositeConstraint)>(c.const1,c.const2), makeConstantPalmIntVar(1))]

// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[opposite(c:PalmLinComb) : PalmLinComb
 -> if (c.op = EQ)  makePalmLinComb(list<integer>{i | i in c.coefs}, c.vars,c.cste, NEQ)  // v1.011 coefs is an array
    else if (c.op = GEQ) makePalmLinComb(list<integer>{-(i) | i in c.coefs}, c.vars, -(c.cste + 1), GEQ)
    else (assert(c.op = NEQ),
          makePalmLinComb(list<integer>{i | i in c.coefs}, c.vars,c.cste, EQ))]           // v1.011 coefs is an array



// *************************************************************
// *   Part 8 : arithmetic constraints                         *
// *************************************************************

// Simple unary operators
[claire/>=(v:PalmIntVar, x:integer) : PalmGreaterOrEqualxc
 -> makePalmUnIntConstraint(PalmGreaterOrEqualxc,v,x)]
[claire/>(v:PalmIntVar, x:integer) : PalmGreaterOrEqualxc
 -> makePalmUnIntConstraint(PalmGreaterOrEqualxc,v,x + 1)]
[claire/<=(v:PalmIntVar, x:integer) : PalmLessOrEqualxc
 -> makePalmUnIntConstraint(PalmLessOrEqualxc,v,x)]
[claire/<(v:PalmIntVar, x:integer) : PalmLessOrEqualxc
 -> makePalmUnIntConstraint(PalmLessOrEqualxc,v,x - 1)]
[claire/==(v:PalmIntVar, x:integer) : PalmEqualxc
 -> makePalmUnIntConstraint(PalmEqualxc,v,x)]
[claire/!==(v:PalmIntVar, x:integer) : PalmNotEqualxc
 -> makePalmUnIntConstraint(PalmNotEqualxc,v,x)]

[claire/>=(x:integer, v:PalmIntVar) : PalmLessOrEqualxc => (v <= x)]
[claire/>(x:integer, v:PalmIntVar) : PalmLessOrEqualxc => (v < x)]
[claire/<=(x:integer, v:PalmIntVar) : PalmGreaterOrEqualxc => (v >= x)]
[claire/<(x:integer, v:PalmIntVar) : PalmGreaterOrEqualxc => (v > x)]
[claire/==(x:integer, v:PalmIntVar) : PalmEqualxc => (v == x)]
[claire/!==(x:integer, v:PalmIntVar) : PalmNotEqualxc => (v !== x)]

// Simple binary operators
[claire/==(u:PalmIntVar, v:PalmIntVar) : PalmEqualxyc
 -> makePalmBinIntConstraint(PalmEqualxyc,u,v,0)]
[claire/!==(u:PalmIntVar, v:PalmIntVar) : PalmNotEqualxyc
 -> makePalmBinIntConstraint(PalmNotEqualxyc,u,v,0)]
[claire/<=(u:PalmIntVar, v:PalmIntVar) : PalmGreaterOrEqualxyc => (v >= u)]
[claire/>=(u:PalmIntVar, v:PalmIntVar) : PalmGreaterOrEqualxyc
 -> makePalmBinIntConstraint(PalmGreaterOrEqualxyc,u,v,0)]
[claire/>(u:PalmIntVar, v:PalmIntVar) : PalmGreaterOrEqualxyc
 -> makePalmBinIntConstraint(PalmGreaterOrEqualxyc,u,v,1)]
[claire/<(u:PalmIntVar, v:PalmIntVar) : PalmGreaterOrEqualxyc => (v > u)]

// General linear combinations
//  we store linear expressions in temporary data structures: terms
palm/PalmTempTerm <: PalmTools(
    palm/cste:integer = 0)

//  PalmUnTerm: a temporary object representing +/-x +c
palm/PalmUnTerm <: PalmTempTerm(
    palm/v1:PalmIntVar,
    palm/sign1:boolean = true)

[self_print(t:PalmUnTerm)
 -> if not(t.sign1) printf("-"),
    printf("~S ",t.v1),
    if (t.cste < 0) printf("~S",t.cste)
    else if (t.cste > 0) printf("+~S",t.cste)]

//  PalmBinTerm: a temporary object representing +/-x +/-y +c
palm/PalmBinTerm <: PalmTempTerm(
    palm/v1:PalmIntVar,
    palm/v2:PalmIntVar,
    palm/sign1:boolean = true,
    palm/sign2:boolean = true)

[self_print(t:PalmBinTerm)
 -> if (t.sign1) printf("+") else printf("-"),
    printf("~S ",t.v1),
    if (t.sign2) printf("+") else printf("-"),
    printf("~S",t.v2),
    if (t.cste < 0) printf("~S",t.cste)
    else if (t.cste > 0) printf("+~S",t.cste)]

//  PalmLinTerm: a temporary object representing a linear term
palm/PalmLinTerm <: PalmTempTerm(
    palm/lcoeff:list<integer>,
    palm/lvars:list<PalmIntVar>)

[self_print(t:PalmLinTerm)
 -> for i in (1 .. length(t.lcoeff))
      printf("~S.~S + ",t.lcoeff[i],t.lvars[i]),
    if (t.cste < 0) printf("~S",t.cste)
    else if (t.cste > 0) printf("+~S",t.cste)]

// constructing a linear term from a list of variables
claire/e-scalar :: operation(precedence = precedence(*))
[e-scalar(l1:list[integer], l2:list[PalmIntVar]) : PalmLinTerm
 -> PalmLinTerm(lcoeff = list<integer>{i | i in l1}, lvars = list<PalmIntVar>{v | v in l2})] 


//v0.36:Constructing a linear term from a list of variables
[e-sumVars(l:list[PalmIntVar]) : PalmLinTerm
-> PalmLinTerm(lcoeff = make_list(length(l), integer, 1), lvars = list<PalmIntVar>{v | v in l})] //v0.94: ensure a non-destructive behavior


// building linear terms
[*(a:integer, x:PalmIntVar) : PalmLinTerm -> PalmLinTerm(lcoeff = list<integer>(a), lvars = list<PalmIntVar>(x))]
[*(x:PalmIntVar, a:integer) : PalmLinTerm -> PalmLinTerm(lcoeff = list<integer>(a), lvars = list<PalmIntVar>(x))]

// ----- Addition Operator
[+(u:PalmIntVar, c:integer) : PalmUnTerm -> PalmUnTerm(v1 = u, cste = c)]
[+(c:integer, u:PalmIntVar) : PalmUnTerm => u + c]
[+(u:PalmIntVar, v:PalmIntVar) : PalmBinTerm -> PalmBinTerm(v1 = u, v2 = v)]
[+(t:PalmUnTerm, c:integer) : PalmUnTerm -> t.cste :+ c, t]
[+(c:integer, t:PalmUnTerm) : PalmUnTerm => t + c]

[+(t:PalmUnTerm, x:PalmIntVar) : PalmBinTerm -> PalmBinTerm(v1 = t.v1, sign1 = t.sign1, v2 = x, cste = t.cste)]
[+(x:PalmIntVar, t:PalmUnTerm) : PalmBinTerm => t + x]
[+(t1:PalmUnTerm, t2:PalmUnTerm) : PalmBinTerm -> PalmBinTerm(v1 = t1.v1, sign1 = t1.sign1, v2 = t2.v1, sign2 = t2.sign1, cste = t1.cste + t2.cste)]
[+(t:PalmBinTerm, c:integer) : PalmBinTerm -> t.cste :+ c, t]
[+(c:integer, t:PalmBinTerm) : PalmBinTerm => t + c]

[+(t:PalmBinTerm, x:PalmIntVar) : PalmLinTerm
 -> PalmLinTerm(lcoeff = list<integer>(integer!(t.sign1),integer!(t.sign2),1),
            lvars = list<PalmIntVar>(t.v1,t.v2,x),
            cste = t.cste)]
[+(x:PalmIntVar, t:PalmBinTerm) : PalmLinTerm => t + x]
[+(t1:PalmBinTerm, t2:PalmUnTerm) : PalmLinTerm
 -> PalmLinTerm(lcoeff = list<integer>(integer!(t1.sign1),integer!(t1.sign2),integer!(t2.sign1)),
            lvars = list<PalmIntVar>(t1.v1,t1.v2,t2.v1),
            cste = t1.cste + t2.cste)]
[+(t2:PalmUnTerm, t1:PalmBinTerm) : PalmLinTerm => t1 + t2]

[+(t1:PalmBinTerm, t2:PalmBinTerm) : PalmLinTerm
 -> PalmLinTerm(lcoeff = list<integer>(integer!(t1.sign1),integer!(t1.sign2),integer!(t2.sign1),integer!(t2.sign2)),
            lvars = list<PalmIntVar>(t1.v1,t1.v2,t2.v1,t2.v2),
            cste = t1.cste + t2.cste)]

[+(t:PalmLinTerm, a:integer) : PalmLinTerm -> t.cste :+ a, t]
[+(a:integer, t:PalmLinTerm) : PalmLinTerm => t + a]

[+(t:PalmLinTerm, x:PalmIntVar) : PalmLinTerm -> t.lcoeff :add 1, t.lvars :add x, t]
[+(x:PalmIntVar, t:PalmLinTerm) : PalmLinTerm => t + x]

[+(t1:PalmLinTerm, t2:PalmUnTerm) : PalmLinTerm
 -> t1.lcoeff :add integer!(t2.sign1),
    t1.lvars :add t2.v1,
    t1.cste :+ t2.cste,
    t1]
[+(t2:PalmUnTerm, t1:PalmLinTerm) : PalmLinTerm => (t1 + t2)]

[+(t1:PalmLinTerm, t2:PalmBinTerm) : PalmLinTerm
 -> t1.lcoeff :/+ list<integer>(integer!(t2.sign1),integer!(t2.sign2)),
    t1.lvars :/+ list<PalmIntVar>(t2.v1,t2.v2),
    t1.cste :+ t2.cste,
    t1]
[+(t2:PalmBinTerm, t1:PalmLinTerm) : PalmLinTerm => t1 + t2]

[+(t1:PalmLinTerm, t2:PalmLinTerm) : PalmLinTerm
 -> PalmLinTerm(lcoeff = t1.lcoeff /+ t2.lcoeff,
            lvars = t1.lvars /+ t2.lvars,
            cste = t1.cste + t2.cste)]

// ----- Opposite Operator
[-(x:PalmIntVar) : PalmUnTerm
 -> PalmUnTerm(v1 = x, sign1 = false)]
[-(t:PalmUnTerm) : PalmUnTerm
 -> t.sign1 := not(t.sign1), t.cste := -(t.cste), t]
[-(t:PalmBinTerm) : PalmBinTerm
 -> t.sign1 := not(t.sign1), t.sign2 := not(t.sign2), t.cste := -(t.cste), t]
[-(t:PalmLinTerm) : PalmLinTerm
 -> t.lcoeff := list<integer>{-(a) | a in t.lcoeff}, t.cste := -(t.cste), t]

// ----- Substraction Operator
// many lines for
// [-(t1:(PalmTempTerm U PalmIntVar U integer), t2:(PalmTempTerm U PalmIntVar)) : PalmTempTerm => t1 + -(t2)]
// because of a dump compiler
// v0.18 further expand for static typing
[-(t1:PalmIntVar, t2:integer) : PalmUnTerm -> t1 + -(t2)]
[-(t1:PalmUnTerm, t2:integer) : PalmUnTerm -> t1 + -(t2)]
[-(t1:PalmBinTerm, t2:integer) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmLinTerm, t2:integer) : PalmLinTerm -> t1 + -(t2)]
[-(t1:integer, t2:PalmIntVar) : PalmUnTerm -> t1 + -(t2)]
[-(t1:PalmIntVar, t2:PalmIntVar) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmUnTerm, t2:PalmIntVar) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmBinTerm, t2:PalmIntVar) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmLinTerm, t2:PalmIntVar) : PalmLinTerm -> t1 + -(t2)]
[-(t1:integer, t2:PalmUnTerm) : PalmUnTerm -> t1 + -(t2)]
[-(t1:PalmIntVar, t2:PalmUnTerm) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmUnTerm, t2:PalmUnTerm) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmBinTerm, t2:PalmUnTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmLinTerm, t2:PalmUnTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:integer, t2:PalmBinTerm) : PalmBinTerm -> t1 + -(t2)]
[-(t1:PalmIntVar, t2:PalmBinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmUnTerm, t2:PalmBinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmBinTerm, t2:PalmBinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmLinTerm, t2:PalmBinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:integer, t2:PalmLinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmIntVar, t2:PalmLinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmUnTerm, t2:PalmLinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmBinTerm, t2:PalmLinTerm) : PalmLinTerm -> t1 + -(t2)]
[-(t1:PalmLinTerm, t2:PalmLinTerm) : PalmLinTerm -> t1 + -(t2)]

// ------- Using terms within comparisons
// v0.18 rewrite for static typing
// [>=(a:integer, t:PalmTempTerm) : AbstractConstraint => -(t) >= -(a)]
// [>=(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => -(t) >= -(x)]
// [>=(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => -(t2) >= -(t1)]
[>=(a:integer, t:PalmUnTerm) : AbstractConstraint -> -(t) >= -(a)]
[>=(a:integer, t:PalmBinTerm) : AbstractConstraint -> -(t) >= -(a)]
[>=(a:integer, t:PalmLinTerm) : AbstractConstraint -> -(t) >= -(a)]
[>=(x:PalmIntVar, t:PalmUnTerm) : AbstractConstraint -> -(t) >= -(x)]
[>=(x:PalmIntVar, t:PalmBinTerm) : AbstractConstraint -> -(t) >= -(x)]
[>=(x:PalmIntVar, t:PalmLinTerm) : AbstractConstraint -> -(t) >= -(x)]
[>=(t1:PalmUnTerm, t2:PalmBinTerm) : AbstractConstraint -> -(t2) >= -(t1)]
[>=(t1:PalmUnTerm, t2:PalmLinTerm) : AbstractConstraint -> -(t2) >= -(t1)]
[>=(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint -> -(t2) >= -(t1)]

[>=(t:PalmUnTerm, c:integer) : PalmUnIntConstraint
 -> if t.sign1
       makePalmUnIntConstraint(PalmGreaterOrEqualxc,t.v1, c - t.cste)
    else makePalmUnIntConstraint(PalmLessOrEqualxc,t.v1, t.cste - c)]

[>=(t:PalmUnTerm, x:PalmIntVar) : AbstractConstraint => -(x) >= -(t)]
[>=(t1:PalmUnTerm, t2:PalmUnTerm) : AbstractConstraint
 -> if (t1.sign1 = t2.sign1)
       (if t1.sign1
           makePalmBinIntConstraint(PalmGreaterOrEqualxyc,t1.v1,t2.v1,t2.cste - t1.cste)
        else
           makePalmBinIntConstraint(PalmGreaterOrEqualxyc,t2.v1,t1.v1,t2.cste - t1.cste))
    else makePalmLinComb(list<integer>(integer!(t1.sign1),-(integer!(t2.sign1))), list<PalmIntVar>(t1.v1,t2.v1), t1.cste - t2.cste, GEQ)]

[>=(t:PalmBinTerm, c:integer) : AbstractConstraint
 -> if (t.sign1 != t.sign2)
       (if t.sign1
           makePalmBinIntConstraint(PalmGreaterOrEqualxyc,t.v1,t.v2,c - t.cste)
        else
           makePalmBinIntConstraint(PalmGreaterOrEqualxyc,t.v2,t.v1,c + t.cste))
    else makePalmLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2)), list<PalmIntVar>(t.v1,t.v2), t.cste - c, GEQ) ]

[>=(t:PalmBinTerm, x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2),-1), list<PalmIntVar>(t.v1,t.v2,x),t.cste,GEQ)]

[>=(t1:PalmBinTerm, t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1))),
            list<PalmIntVar>(t1.v1,t1.v2,t2.v1),t1.cste - t2.cste,GEQ)]

[>=(t1:PalmBinTerm, t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
            list<PalmIntVar>(t1.v1,t1.v2,t2.v1,t2.v2),t1.cste - t2.cste,GEQ)]

[>=(t:PalmLinTerm,a:integer) : PalmLinComb
 -> makePalmLinComb(t.lcoeff, t.lvars, t.cste - a, GEQ)]

[>=(t:PalmLinTerm,x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb(t.lcoeff add -1, t.lvars add x, t.cste, GEQ)]

[>=(t:PalmLinTerm,t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb(t.lcoeff add -(integer!(t2.sign1)),t.lvars add t2.v1, t.cste - t2.cste, GEQ)]

[>=(t:PalmLinTerm,t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb(t.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                t.lvars /+ list<PalmIntVar>(t2.v1,t2.v2), t.cste - t2.cste, GEQ)]

[>=(t1:PalmLinTerm,t2:PalmLinTerm) : PalmLinComb
 -> makePalmLinComb(t1.lcoeff /+ list<integer>{-(a) | a in t2.lcoeff},
                t1.lvars /+ t2.lvars, t1.cste - t2.cste, GEQ)]

// All comparisons can be defined from >=
// rewrite t1 > t2 as t1 >= t2 + 1
// v0.18 expanded for static typing
// [>(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint => (t1 >= t2 + 1)]
// [>(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => (t1 >= t2 + 1)]
// [>(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => (t1 >= t2 + 1)]
[>(t1:integer, t2:PalmUnTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:integer, t2:PalmBinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:integer, t2:PalmLinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmIntVar, t2:PalmUnTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmIntVar, t2:PalmBinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmIntVar, t2:PalmLinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmUnTerm, t2:integer) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmBinTerm, t2:integer) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmLinTerm, t2:integer) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmUnTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmBinTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmLinTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmUnTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmBinTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmLinTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmUnTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmBinTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmLinTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmUnTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]
[>(t1:PalmLinTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 >= t2 + 1)]

// rewrite t1 <= t2 as t2 >= t1

// v0.18 expanded for static typing
// [<=(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint -> t2 >= t1]
// [<=(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => t2 >= t1]
// [<=(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => t2 >= t1]
[<=(t1:integer, t2:PalmUnTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:integer, t2:PalmBinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:integer, t2:PalmLinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmIntVar, t2:PalmUnTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmIntVar, t2:PalmBinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmIntVar, t2:PalmLinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmUnTerm, t2:integer) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmBinTerm, t2:integer) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmLinTerm, t2:integer) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmUnTerm, t2:PalmIntVar) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmBinTerm, t2:PalmIntVar) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmLinTerm, t2:PalmIntVar) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmUnTerm, t2:PalmUnTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmBinTerm, t2:PalmUnTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmLinTerm, t2:PalmUnTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmUnTerm, t2:PalmBinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmBinTerm, t2:PalmBinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmLinTerm, t2:PalmBinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmUnTerm, t2:PalmLinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint -> t2 >= t1]
[<=(t1:PalmLinTerm, t2:PalmLinTerm) : AbstractConstraint -> t2 >= t1]


// rewrite t1 < t2 as t1 <= t2 - 1
// v0.18 expanded for static typing
// [<(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint => (t1 <= t2 - 1)]
// [<(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => (t1 <= t2 - 1)]
// [<(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => (t1 <= t2 - 1)]
[<(t1:integer, t2:PalmUnTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:integer, t2:PalmBinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:integer, t2:PalmLinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmIntVar, t2:PalmUnTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmIntVar, t2:PalmBinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmIntVar, t2:PalmLinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmUnTerm, t2:integer) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmBinTerm, t2:integer) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmLinTerm, t2:integer) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmUnTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmBinTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmLinTerm, t2:PalmIntVar) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmUnTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmBinTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmLinTerm, t2:PalmUnTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmUnTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmBinTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmLinTerm, t2:PalmBinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmUnTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]
[<(t1:PalmLinTerm, t2:PalmLinTerm) : AbstractConstraint -> (t1 <= t2 - 1)]

// Equality
// v0.18 expanded for static typing
// [==(a:integer, t:PalmTempTerm) : AbstractConstraint => t == a]
// [==(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => t == x]
// [==(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => t2 == t1]
// [==(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint => t2 == t1]
[==(a:integer, t:PalmUnTerm) : AbstractConstraint => t == a]
[==(a:integer, t:PalmBinTerm) : AbstractConstraint => t == a]
[==(a:integer, t:PalmLinTerm) : AbstractConstraint => t == a]
[==(x:PalmIntVar, t:PalmUnTerm) : AbstractConstraint => t == x]
[==(x:PalmIntVar, t:PalmBinTerm) : AbstractConstraint => t == x]
[==(x:PalmIntVar, t:PalmLinTerm) : AbstractConstraint => t == x]
[==(t1:PalmUnTerm, t2:PalmBinTerm) : AbstractConstraint => t2 == t1]
[==(t1:PalmUnTerm, t2:PalmLinTerm) : AbstractConstraint => t2 == t1]
[==(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint => t2 == t1]



[==(t:PalmUnTerm, c:integer) : PalmUnIntConstraint
 -> makePalmUnIntConstraint(PalmEqualxc,t.v1, c - t.cste)]
[==(t:PalmUnTerm, x:PalmIntVar) : AbstractConstraint
  -> if t.sign1
       makePalmBinIntConstraint(PalmEqualxyc,x,t.v1,t.cste)
     else makePalmLinComb(list<integer>(1,1),list<PalmIntVar>(t.v1,x),-(t.cste),EQ)]
[==(t1:PalmUnTerm, t2:PalmUnTerm) : PalmBinIntConstraint
 -> makePalmBinIntConstraint(PalmEqualxyc,t1.v1,t2.v1,t2.cste - t1.cste)]

[==(t:PalmBinTerm, c:integer) : AbstractConstraint
 -> if (t.sign1 != t.sign2)
       makePalmBinIntConstraint(PalmEqualxyc,t.v1,t.v2,(if t.sign1 c - t.cste else t.cste - c))
    else makePalmLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2)),list<PalmIntVar>(t.v1,t.v2),t.cste - c,EQ)]

[==(t:PalmBinTerm, x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2),-1),list<PalmIntVar>(t.v1,t.v2,x),t.cste,EQ)]
[==(t1:PalmBinTerm, t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1))),
            list<PalmIntVar>(t1.v1,t1.v2,t2.v1),t1.cste - t2.cste,EQ)]

[==(t1:PalmBinTerm, t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
            list<PalmIntVar>(t1.v1,t1.v2,t2.v1,t2.v2),t1.cste - t2.cste,EQ)]

[==(t:PalmLinTerm, c:integer) : PalmLinComb
 -> makePalmLinComb(t.lcoeff, t.lvars, t.cste - c, EQ)]
[==(t:PalmLinTerm, x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb(t.lcoeff add -1, t.lvars add x, t.cste, EQ)]
[==(t1:PalmLinTerm, t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb(t1.lcoeff add -(integer!(t2.sign1)), t1.lvars add t2.v1, t1.cste - t2.cste, EQ)]

[==(t1:PalmLinTerm, t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb(t1.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                t1.lvars /+ list<PalmIntVar>(t2.v1,t2.v2),t1.cste - t2.cste, EQ)]
[==(t1:PalmLinTerm, t2:PalmLinTerm) : PalmLinComb
 -> makePalmLinComb(t1.lcoeff /+ list<integer>{-(a) | a in t2.lcoeff},
            t1.lvars /+ t2.lvars,
            t1.cste - t2.cste, EQ)]

// v0.18 expanded for static typing
// [!==(a:integer, t:PalmTempTerm) : AbstractConstraint => t !== a]
// [!==(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => t !== x]
// [!==(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => t2 !== t1]
// [!==(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint => t2 !== t1]

[!==(a:integer, t:PalmUnTerm) : AbstractConstraint -> t !== a]
[!==(a:integer, t:PalmBinTerm) : AbstractConstraint -> t !== a]
[!==(a:integer, t:PalmLinTerm) -> t !== a]
[!==(x:PalmIntVar, t:PalmUnTerm) : AbstractConstraint -> t !== x]
[!==(x:PalmIntVar, t:PalmBinTerm) -> t !== x]
[!==(x:PalmIntVar, t:PalmLinTerm) -> t !== x]
[!==(t1:PalmUnTerm, t2:PalmBinTerm) -> t2 !== t1]
[!==(t1:PalmUnTerm, t2:PalmLinTerm) -> t2 !== t1]
[!==(t1:PalmBinTerm, t2:PalmLinTerm) -> t2 !== t1]

[!==(t:PalmUnTerm, c:integer) : PalmUnIntConstraint
 -> makePalmUnIntConstraint(PalmNotEqualxc,t.v1,(if t.sign1 c - t.cste else t.cste - c))]
[!==(t:PalmUnTerm, x:PalmIntVar) : IntConstraint
 -> if not(t.sign1) makePalmLinComb(list<integer>(1,1), list<PalmIntVar>(x, t.v1), -(t.cste), NEQ)
    else makePalmBinIntConstraint(PalmNotEqualxyc,x,t.v1,t.cste)]
[!==(t1:PalmUnTerm, t2:PalmUnTerm) : IntConstraint
 -> let newcste := (if (t1.sign1) t2.cste - t1.cste else t1.cste - t2.cste) in 
      (if (t1.sign1 != t2.sign1) 
          makePalmLinComb(list<integer>(1,1), list<PalmIntVar>(t1.v1, t2.v1), -(newcste), NEQ)
       else makePalmBinIntConstraint(PalmNotEqualxyc,t1.v1,t2.v1,newcste))]
[!==(t1:PalmBinTerm, c:integer) : IntConstraint
 -> let newcste := (if (t1.sign1) c - t1.cste else t1.cste - c) in 
      (if (t1.sign1 != t1.sign2)
          makePalmBinIntConstraint(PalmNotEqualxyc,t1.v1,t1.v2,newcste)
       else makePalmLinComb(list<integer>(1,1), list<PalmIntVar>(t1.v1, t1.v2), -(newcste), NEQ))]

[!==(t1:PalmBinTerm, x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-1),
                list<PalmIntVar>(t1.v1, t1.v2, x), t1.cste, NEQ)]
[!==(t1:PalmBinTerm, t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-(integer!(t2.sign1))),
                list<PalmIntVar>(t1.v1, t1.v2, t2.v1), t1.cste - t2.cste, NEQ)]
[!==(t1:PalmBinTerm, t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                list<PalmIntVar>(t1.v1, t1.v2, t2.v1, t2.v2), t1.cste - t2.cste, NEQ)]
[!==(t1:PalmLinTerm, c:integer) : PalmLinComb
 -> makePalmLinComb(t1.lcoeff, t1.lvars, t1.cste - c, NEQ)]
[!==(t1:PalmLinTerm, x:PalmIntVar) : PalmLinComb
 -> makePalmLinComb((t1.lcoeff add -1) as list[integer],
                (t1.lvars add x) as list[PalmIntVar], t1.cste, NEQ)]
[!==(t1:PalmLinTerm, t2:PalmUnTerm) : PalmLinComb
 -> makePalmLinComb((t1.lcoeff add -(integer!(t2.sign1))) as list[integer],
                (t1.lvars add t2.v1) as list[PalmIntVar], t1.cste - t2.cste, NEQ)]
[!==(t1:PalmLinTerm, t2:PalmBinTerm) : PalmLinComb
 -> makePalmLinComb((t1.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2)))) as list[integer],
                (t1.lvars /+ list<PalmIntVar>(t2.v1, t2.v2)) as list[PalmIntVar], t1.cste - t2.cste, NEQ)]
[!==(t1:PalmLinTerm, t2:PalmLinTerm) : PalmLinComb
 -> makePalmLinComb((t1.lcoeff /+ list<integer>{-(cf) | cf in t2.lcoeff}) as list[integer],
                (t1.lvars /+ t2.lvars) as list[PalmIntVar], t1.cste - t2.cste, NEQ)]



// *************************************************************
// *   Part 9 : symbolic constraints                           *
// *************************************************************


//  PalmOccurTerm: a temporary object representing occur(t,l)
PalmOccurTerm <: choco/Term(
    target:integer,
    lvars:list<PalmIntVar>)

 
[makePalmOccurConstraint(ot:PalmOccurTerm, v:PalmIntVar,
                             atleast:boolean, atmost:boolean) : PalmOccurrence
 -> let l := list<IntVar>{v | v in ot.lvars}, n := length(l), nb1 := 0, nb2 := 0,
        isPos := make_array(n, boolean, true), 
		isSur := make_array(n, boolean, false),
        tgt := ot.target, c := PalmOccurrence(vars = copy(l) add v, cste = tgt) in
     (closeLargeIntConstraint(c),   // v0.34
      // [0] initHook(c) 1,
	  initHook(c),
      for j in (1 .. n)
         let v := (l[j] as PalmIntVar) in
            (if (v choco/canBeInstantiatedTo tgt)
                (nb1 :+ 1,
                 if (v choco/isInstantiatedTo tgt)
                    (isSur[j] := true, nb2 :+ 1))
             else isPos[j] := false),
      c.isPossible := isPos,
      c.isSure := isSur,
      c.nbPossible := nb1,
      c.nbSure := nb2,
      c.constrainOnInfNumber := atleast,      // v0.9907
      c.constrainOnSupNumber := atmost,
	  c.checkInf := make_list(n + 1, boolean, false),
	  c.checkSup := make_list(n + 1, boolean, false),
      c)]

[palm/occur(tgt:integer, l:list[PalmIntVar]) : PalmOccurTerm
 -> PalmOccurTerm(target = tgt, lvars = list<PalmIntVar>{v | v in l}) ]


// Occurrence constraints are always stated as
//   occur(t,l) (==/>=/<=) x/v
// watchout: arithmetic is not complete.
[==(ot:PalmOccurTerm,x:PalmIntVar) : PalmOccurrence
 -> makePalmOccurConstraint(ot,x,true,true)]
[==(x:PalmIntVar,ot:PalmOccurTerm) : PalmOccurrence -> (ot == x)]
;[==(ot:PalmOccurTerm,x:integer) : PalmOccurrence
; -> makePalmOccurConstraint(ot,makeConstantPalmIntVar(x),true,true)]
;[==(x:integer,ot:PalmPalmOccurTerm) : PalmOccurrence -> (ot == x)]
[>=(ot:PalmOccurTerm,x:PalmIntVar) : PalmOccurrence
 -> makePalmOccurConstraint(ot,x,true,false)]
;[>=(ot:PalmOccurTerm,x:integer) : PalmOccurrence
; -> makePalmOccurConstraint(ot,makeConstantPalmIntVar(x),true,false)]
[<=(ot:PalmOccurTerm,x:PalmIntVar) : PalmOccurrence
 -> makePalmOccurConstraint(ot,x,false,true)]
;[<=(ot:PalmOccurTerm,x:integer) : PalmOccurrence
; -> makePalmOccurConstraint(ot,makeConstantPalmIntVar(x),false,true)]

// v0.24 Symmetrical API's (a request from Michel Lemaitre)
[>=(x:PalmIntVar,ot:PalmOccurTerm) : PalmOccurrence -> (ot <= x)]
;[>=(x:integer,ot:PalmOccurTerm) : PalmOccurrence -> (ot <= x)]
[<=(x:PalmIntVar,ot:PalmOccurTerm) : PalmOccurrence -> (ot >= x)]
;[<=(x:integer,ot:PalmOccurTerm) : PalmOccurrence -> (ot >= x)]


PalmEltTerm <: choco/EltTerm()
[getNth(l:list[integer], i:PalmUnTerm) : PalmEltTerm  // <fla> nth -> getNth
 -> if not(i.sign1) error("Negative indexes (-I + c) are not allowed in an element constraint"),
    PalmEltTerm(lvalues = l, choco/indexVar = i.v1, cste = i.cste)]
[getNth(l:list[integer], i:PalmIntVar) : PalmEltTerm
 -> PalmEltTerm(lvalues = l, choco/indexVar = i, cste = 0)]
[claire/==(t:PalmEltTerm,x:PalmIntVar) : PalmElt -> makePalmEltConstraint(t.lvalues,t.choco/indexVar,x,t.cste)] 
[claire/==(x:PalmIntVar,t:PalmEltTerm) : PalmElt -> makePalmEltConstraint(t.lvalues,t.choco/indexVar,x,t.cste)]
[claire/==(t:PalmEltTerm,x:integer) : PalmElt -> makePalmEltConstraint(t.lvalues,t.choco/indexVar,makeConstantPalmIntVar(x),t.cste)]
[claire/==(x:integer,t:PalmEltTerm) : PalmElt -> makePalmEltConstraint(t.lvalues,t.choco/indexVar,makeConstantPalmIntVar(x),t.cste)]


// *************************************************************
// *   Part 10 : boolean connectors                            *
// *************************************************************

claire/e-or :: operation(precedence = precedence(or))
claire/e-implies :: operation()
claire/e-iff :: operation(precedence = precedence(&))
claire/e-and :: operation(precedence = precedence(&))


// ----- Disjunctions -----------

// 1. Associative rules for building disjunctions from two disjunctions
[e-or(c1:PalmLargeDisjunction, c2:PalmLargeDisjunction) : PalmLargeDisjunction
 -> let c := PalmLargeDisjunction(lconst = c1.lconst /+ c2.lconst,
								  needToAwake = c1.needToAwake /+ c2.needToAwake) in  // v0.93 typo
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
	    initHook(c),
        c)]
[e-or(c1:PalmDisjunction, c2: PalmDisjunction) : PalmLargeDisjunction // v1.0: missing API functions, v1.02 typos
 -> let c := PalmLargeDisjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2.const1,c2.const2),
								  needToAwake = list<boolean>(false, false, false, false)) in
       (closeBoolConstraint(c),
	    initHook(c),
        c)]
[e-or(c1:PalmLargeDisjunction, c2:PalmDisjunction) : PalmLargeDisjunction
 => c1.lconst :add c2.const1, c1.lconst :add c2.const2, c1.needToAwake :add false, c1.needToAwake :add false, 
    closeBoolConstraint(c1), // fill the offset, nbConst, status slots
	initHook(c1),
    c1]
[e-or(c1:PalmDisjunction, c2:PalmLargeDisjunction) : PalmLargeDisjunction => c2 e-or c1] 

// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v0.21 <fl> initialize lstatus -> v1.02 done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
[e-or(c1:PalmDisjunction, 
    c2:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmConjunction U PalmLargeConjunction)) : PalmLargeDisjunction
 -> let c := PalmLargeDisjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2), needToAwake = list<boolean>(false, false, false)) in
      (closeBoolConstraint(c), // v0.37 fill the internal offset slot + status slot (v1.02)
	   initHook(c),
       c)]
[e-or(c1:PalmLargeDisjunction, 
    c2:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmConjunction U PalmLargeConjunction)) : PalmLargeDisjunction // v0.30 more precise signature
 -> c1.lconst :add c2, c1.needToAwake :add false,
    closeBoolConstraint(c1), // v0.37 fill the internal offset slot
	initHook(c1),
    c1]
// v1.0: the "or" operator is commutative 
[e-or(c1:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmConjunction U PalmLargeConjunction),
    c2:PalmDisjunction) : PalmLargeDisjunction => c2 e-or c1] 
[e-or(c1:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmConjunction U PalmLargeConjunction),
    c2:PalmLargeDisjunction) : PalmLargeDisjunction => c2 e-or c1] 

// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
[e-or(c1:(IntConstraint U CompositeConstraint), c2:(IntConstraint U CompositeConstraint)) : PalmDisjunction // v0.30 more precise signature
 -> let d := PalmDisjunction(const1 = c1, const2 = c2) in
       (closeBoolConstraint(d),  // v0.37 fill the internal offset slot
	    initHook(d),
        d)]

// 4. additional (non binary) method definition (new in 0.9901)
[e-or(l:list[AbstractConstraint]) : PalmLargeDisjunction
 -> let n := length(l),
        c := PalmLargeDisjunction(lconst = copy(l), needToAwake = make_list(n, boolean, false)) in  // <thb> v1.0 typo (lstatus)
     (closeBoolConstraint(c),
	  initHook(c),
      c)]


// ----- Conjunctions -----------

// 1. Associative rules for building conjunctions from two conjunctions
[e-and(c1:PalmConjunction, c2: PalmConjunction) : PalmLargeConjunction
 -> let c := PalmLargeConjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2.const1,c2.const2)) in  // v1.02  <fla>
       (closeBoolConstraint(c),
	    initHook(c),
        c)]
[e-and(c1:PalmLargeConjunction, c2:PalmLargeConjunction) : PalmLargeConjunction
 -> let c := PalmLargeConjunction(lconst = c1.lconst /+ c2.lconst) in
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
	    initHook(c),
        c)]
[e-and(c1:PalmLargeConjunction, c2:PalmConjunction) : PalmLargeConjunction
 => c1.lconst :add c2.const1, c1.lconst :add c2.const2,
    closeBoolConstraint(c1), // fill the internal offset slot
	initHook(c1),
    c1]
[e-and(c1:PalmConjunction, c2:PalmLargeConjunction) : PalmLargeConjunction => c2 e-and c1] 

// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v1.02 status initialization is done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
[e-and(c1:PalmConjunction, 
     c2:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmDisjunction U PalmLargeDisjunction)) : PalmLargeConjunction
 -> let c := PalmLargeConjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2)) in
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
	    initHook(c),
        c)]
[e-and(c1:PalmLargeConjunction, 
     c2:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmDisjunction U PalmLargeDisjunction)) : PalmLargeConjunction
 -> c1.lconst :add c2,
    closeBoolConstraint(c1), // v0.37 fill the internal offset slot
	initHook(c1),
    c1]
// v1.0: the "and" operator is commutative 
[e-and(c1:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmDisjunction U PalmLargeDisjunction), 
     c2:PalmConjunction) : PalmLargeConjunction => c2 e-and c1] 
[e-and(c1:(IntConstraint U PalmCardinality U PalmGuard U PalmEquiv U PalmDisjunction U PalmLargeDisjunction), 
     c2:PalmLargeConjunction) : PalmLargeConjunction => c2 e-and c1] 

// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
[e-and(c1:(IntConstraint U CompositeConstraint), c2:(IntConstraint U CompositeConstraint)) : PalmConjunction // v0.30 more precise signature
 -> let c := PalmConjunction() in
      (write(const1,c,c1),
       write(const2,c,c2),
       closeBoolConstraint(c), // v0.37 fill the internal offset slot
	   initHook(c),
       c)]

// 4. additional (non binary) method definition (new in 0.9901)
[e-and(l:list[AbstractConstraint]) : PalmLargeConjunction
 -> let n := length(l),
        c := PalmLargeConjunction(lconst = copy(l)) in
     (closeBoolConstraint(c),
	  initHook(c),
      c)]


// ----- Complete and lightweight guards + equivalence -----------

// v0.97: the "implies" guard now achieves both ways consistency (back propagating guard onto the triggerring condition)
[e-implies(c1:(IntConstraint U CompositeConstraint), c2:(IntConstraint U CompositeConstraint)) : (PalmDisjunction U PalmLargeDisjunction)
 -> opposite(c1) e-or c2]

// this is the old implies: a lightweight implementation (incomplete propagation)
[e-ifThen(c1:(IntConstraint U CompositeConstraint), c2:(IntConstraint U CompositeConstraint)) : PalmGuard // v0.30 more precise signature
 -> let g := PalmGuard() in
      (write(const1,g,c1),
       write(const2,g,c2),
       closeBoolConstraint(g), // v0.37 fill the internal offset slot
	   initHook(g),
       g)]

// v0.29: there is now an explicit correspondance between
// the indices of variables in e.const1 and in e.oppositeConst1
// most of the time, computeVarIdxInOpposite computes the same index, but not for linear constraint
[e-iff(c1:(IntConstraint U CompositeConstraint), c2:(IntConstraint U CompositeConstraint)) : PalmEquiv // v0.30 more precise signature
 -> let e := PalmEquiv() in
      (write(const1,e,c1),
       write(const2,e,c2),
       closeBoolConstraintWCounterOpp(e), // v0.37 fill the internal offset slot
	   initHook(e),
       e)]


// ----- Cardinality constraints -----------

[makePalmCardinalityConstraint(l:list[(IntConstraint U CompositeConstraint)], v:PalmIntVar,
      atleast:boolean, atmost:boolean) : PalmCardinality // v0.30 more precise signature
 -> assert(forall(c in l | getNbVars(opposite(c)) = getNbVars(c))),
    let n := length(l),
        c := PalmCardinality(lconst = l, nbConst = n,
                         additionalVars = list<IntVar>(v),  // v1.02 new data structure
                         constrainOnInfNumber = atleast,
                         constrainOnSupNumber = atmost,
						 needToAwake = make_list(2 * n, boolean, false)
                         ) in
      (closeBoolConstraintWCounterOpp(c),  // v1.02
       // [0] initHook(c) 2,
	   initHook(c),
       c)]

// v0.9904: extended API
[e-card(l:list[(IntConstraint U CompositeConstraint)], v:PalmIntVar) : PalmCardinality // v0.30 more precise signature
 -> makePalmCardinalityConstraint(l,v,true,true)] 
[e-atleast(l:list[(IntConstraint U CompositeConstraint)], v:PalmIntVar) : PalmCardinality // v0.30 more precise signature
 -> makePalmCardinalityConstraint(l,v,true,false)]
[e-atmost(l:list[(IntConstraint U CompositeConstraint)], v:PalmIntVar) : PalmCardinality // v0.30 more precise signature
 -> makePalmCardinalityConstraint(l,v,false,true)]

// <thb> v0.93: allows an integer as second parameter
[e-card(l:list[(IntConstraint U CompositeConstraint)], v:integer) : PalmCardinality
 -> e-card(l,makeConstantPalmIntVar(v))]
[e-atleast(l:list[(IntConstraint U CompositeConstraint)], v:integer) : PalmCardinality
 -> e-atleast(l,makeConstantPalmIntVar(v))]
[e-atmost(l:list[(IntConstraint U CompositeConstraint)], v:integer) : PalmCardinality
 -> e-atmost(l,makeConstantPalmIntVar(v))]


// *************************************************************
// *   Part 11 : user-friendly tools                           *
// *************************************************************

// Defining a User-Friendly box 
[startInfoBox(pb: PalmProblem, sname: string, fname: string): void 
 -> assert(PALM_USER_FRIENDLY),
	let nb := PalmUserFriendlyBox(shortName = copy(sname), name = copy(fname))
	in (
		UFcurrentBox.childrenBoxes :add nb,
		nb.fatherBox := UFcurrentBox,
		pb.allUFboxes :add nb,
		UFcurrentBox := nb
	)]

// ending the definition of a user-friendly box
[endInfoBox(pb: PalmProblem) : void
 -> UFcurrentBox := UFcurrentBox.fatherBox ]


// pretty printing a user-friendly box
[self_print(b: PalmUserFriendlyBox)
 -> printf("[~A - ~S cts] ~A",  b.shortName, length(b.constraints), b.name)]

// pretty printing problems
[self_print(pb: PalmProblem) : void
 -> if (PALM_USER_FRIENDLY) (
		printf("=== ~A : description \n", pb.name),
		showInfoBox(2, pb.rootUFboxes)
	) else (
		printf("<PB>: ~A", pb.name)
	)]

[showInfoBox(t: integer, b: PalmUserFriendlyBox)
 -> printf("+"),
	for i in (1 .. t) printf(".."),
	printf("~S \n", b),
	for bc in b.childrenBoxes showInfoBox(t + 1, bc)]


// projecting a constraint on a user view
[project(ct: AbstractConstraint, pb: PalmProblem) : PalmUserFriendlyBox
 -> when bl := ct.hook.ufBox
	in (	
		let us := pb.userRepresentation
		in (
			while not(bl % us) bl := bl.fatherBox,
			bl
		)
	) 
	else pb.rootUFboxes ]

// projecting back
[getConstraints(rb: PalmUserFriendlyBox) : set[AbstractConstraint]
 -> let cts := set!(rb.constraints)
	in (
		for b in rb.childrenBoxes cts :U getConstraints(b),
		cts
	)]


// setting on/off the user-friendly mode
[setUserFriendly(val: boolean)
 -> PALM_USER_FRIENDLY := val]


// defining the user representation 
[setUserRepresentation(pb: PalmProblem, uv: list[string]) : void
 -> for b in uv (
		when rb := some(x in pb.allUFboxes | x.shortName = b)
		in (
			pb.userRepresentation :add rb
		)
		else printf("PaLM: ~A unknown and therefore ignored ... (setUserRepresentation in palmapi.cl)\n", b)
	)]

// setting back a box into the system 
[post(pb: PalmProblem, b: PalmUserFriendlyBox): void
 -> for c in getConstraints(b) (
		c.hook.weight := MAXINT,
		setUserFriendly(false), // disconnecting the information manager !!! 
		if not(isActive(c)) post(pb, c), // apply(post @ PalmProblem, list(pb, c)),
		setUserFriendly(true)
	)]


// default behavior for checking palmitude
[checkPalm(ct: AbstractConstraint) : boolean 
 -> false]


[checkFullPalm(pb: PalmProblem) : boolean // testing the full problem for PalmCompatibility
 -> let isPalmCompatible := true 
	in (
		for v in pb.vars (  // test des variables
			if not(v % PalmIntVar)  (	
				isPalmCompatible := false,
				//[0] Beware !!! ~S should inherit from PalmIntVar // v
			)
		), 
		for c in pb.constraints ( // test des contraintes
			if (not(checkPalm(c))) (
				isPalmCompatible := false,
				//[0] Beware !!! ~S is not a PaLM constraint -- check your code // c
			)
		),
		isPalmCompatible
	)]
	 

