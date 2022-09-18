// A tutorial file for PaLM : PaLM note "Search in PaLM" 
// see http://www.e-constraints.net/palm/manual/palm-note-search.html 


// PART 1: defining a new branching

// a new branching
PathRepairAssignVar <: PalmAssignVar(
   variable: PalmIntVar, // the variable that is currently enumerated
   value: integer = 0 // the current value for iterating decision constraints
) 

[selectBranchingItem(b: PathRepairAssignVar): any 
 -> when v := getMinDomVar(b.extender.manager.problem)
	in (
		b.variable := v,
		b.value := getInf(v), // initializing the iteration process
		v
	) 
	else unknown]

[getNextDecisions(b: PathRepairAssignVar): list[AbstractConstraint]
 -> let ct := assign(b.variable, b.value)
	in (
		b.value :+ 1,
		list(ct)
	)]

[checkAcceptable(b: PathRepairAssignVar, cts: list[AbstractConstraint]): boolean
 -> true] // no need for complicated code 

[learnFromRejection(b: PathRepairAssignVar): void
 -> if (b.value = getSup(b.variable)) (
		raisePalmFakeContradiction(b.extender.manager.problem.propagationEngine, becauseOf(theDom(b.variable)))
	)]



// PART 2: testing the new solver
[demo() : void
 -> let pb := makePalmProblem("demo PR", 16),
		vars := list{ makeIntVar(pb, "x" /+ string!(i), 1, 16) | i in (1 .. 16) }
	in (
		post(pb, e-allDifferent(vars)),
		post(pb, e-sumVars(list{vars[i] | i in (1 .. 4)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in (5 .. 8)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in (9 .. 12)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in (13 .. 16)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in list(1,5,9,13)}) == 34),
		post(pb, e-sumVars(list{vars[i + 1] | i in list(1,5,9,13)}) == 34),
		post(pb, e-sumVars(list{vars[i + 2] | i in list(1,5,9,13)}) == 34),
		post(pb, e-sumVars(list{vars[i + 3] | i in list(1,5,9,13)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in list(1,6,11,16)}) == 34),
		post(pb, e-sumVars(list{vars[i] | i in list(4,7,10,13)}) == 34),
		setSolutionVars(pb, vars),
		attachPalmExtend(pb, PalmUnsureExtend()), 
		attachPalmRepair(pb, PalmUnsureRepair()),
		attachPalmLearn(pb, makePathRepairLearn(PathRepairLearn,10,1000)),
		if solve(pb, list(PathRepairAssignVar())) (
			showSol(solutions(pb)[1].lval)
		) else printf("No solution\n")
	)]


[showInt(i: integer) : void
 -> if (i < 10) printf("  ~S", i)
    else printf(" ~S", i)]


[showSol(vars: list<(integer U {unknown})>): void
 -> printf("\n"), showInt(vars[1]), showInt(vars[2]), showInt(vars[3]), showInt(vars[4]), printf("\n"),
	showInt(vars[5]), showInt(vars[6]), showInt(vars[7]), showInt(vars[8]), printf("\n"),
	showInt(vars[9]), showInt(vars[10]), showInt(vars[11]), showInt(vars[12]), printf("\n"),
	showInt(vars[13]), showInt(vars[14]), showInt(vars[15]), showInt(vars[16]), printf("\n")]

