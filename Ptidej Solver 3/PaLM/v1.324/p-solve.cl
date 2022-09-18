// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 

// ** Summary : solving problems (mechanism and tools)
// * Part 1 : Runtime statistics          
// * Part 2 : palmSolver initialisation
// * Part 3 : Solving Problems                    
// * Part 4 : Extending a solution                 
// * Part 5 : Repairing a solution                 
// * Part 6 : Maintaining the search state          
// * Part 7 : Learning from a contradiction        
// * Part 8 : Choosing a constraint to undo        
// * Part 9 : Default extension                    
// * Part 10: Variable selection tools             
// * Part 11: Solution management tools            
// * Part 12: A generic learner for Path Repair   


// Forward declaration
PalmAssignVar <: PalmBranching

// *************************************************
// * Part 1 : Runtime statistics                   *
// *************************************************

// Different statistics are stored
claire/RLX :: 1 // number of constraint relaxation
claire/EXT :: 2 // number of extensions
claire/CPU :: 3 // cpu time 
RuntimeStatistic :: {RLX, EXT, CPU}

[getGlobalStatistics(pb: PalmSolver, stat: RuntimeStatistic): integer
 -> let total := 0
	in (
		for s in solutions(pb)
			total :+ s.lstat[stat],
		total 
	)]

[getGlobalStatistics(pb: PalmProblem, stat: RuntimeStatistic): integer
 => getGlobalStatistics(pb.palmSolver,stat)]


// accessing the information
[getRuntimeStatistic(pb: PalmSolver, stat: RuntimeStatistic): integer
 => pb.statistics[stat]]

[getRuntimeStatistic(pb: PalmProblem, stat: RuntimeStatistic): integer
 => getRuntimeStatistic(pb.palmSolver,stat)]


// setting a value
[setRuntimeStatistic(pb: PalmSolver, stat: RuntimeStatistic, val: integer): integer
 => pb.statistics[stat] := val]

// value incrementing
[incRuntimeStatistic(pb: PalmSolver, stat: RuntimeStatistic, inc: integer): void
 => pb.statistics[stat] :+ inc]

// value decrementing
[decRuntimeStatistic(pb: PalmSolver, stat: RuntimeStatistic, dec: integer): void
 => pb.statistics[stat] :- dec]

// pretty printing the kept information
[printRuntimeStatistics(pb: PalmProblem) 
 -> for s in RuntimeStatistic 
		printRuntimeStatistics(pb, s)]

// pretty printing one kept information
[printRuntimeStatistics(pb: PalmProblem, stat: RuntimeStatistic )
 -> case stat (
		{RLX} printf("~S repairs ", getRuntimeStatistic(pb.palmSolver, stat)),
		{EXT} printf("~S extensions ", getRuntimeStatistic(pb.palmSolver, stat)),
		{CPU} printf("~S ms ", getRuntimeStatistic(pb.palmSolver, stat))
	)]

[reset(pb: PalmSolver): void
 -> pb.finished := false,
	erase(dummyVariable,pb.problem.propagationEngine), ;<ygg> v1.014
 	for s in RuntimeStatistic
		setRuntimeStatistic(pb, s, 0)]


// *************************************************
// * Part 2 : API                                  *
// *************************************************
[initPalmSolver(pb: PalmProblem) : void
 -> let ps := PalmSolver(statistics = make_list(length(RuntimeStatistic), integer, 0))
	in (
		pb.palmSolver := ps,

		attachPalmState(pb,PalmState(path = Explanation())),
		attachPalmExtend(pb,PalmExtend()),
		attachPalmBranchings(pb, list(PalmAssignVar())),
		attachPalmLearn(pb,PalmLearn()),
		attachPalmRepair(pb,PalmRepair()),

		ps.problem := pb
	)]

[initPalmBranchAndBound(pb: PalmProblem, isMax: boolean, obj: PalmIntVar) : void
 -> let ps := PalmBranchAndBound(
  				objective = obj,
				statistics = make_list(length(RuntimeStatistic), integer, 0),
				maximizing = isMax
			),
		oldSolver := pb.palmSolver
	in (
		pb.palmSolver := ps,
		if ( (length(oldSolver.varsToStore) > 0) & (obj = oldSolver.varsToStore[1]) )
			ps.varsToStore := oldSolver.varsToStore
		else 
			ps.varsToStore := list<IntVar>(obj) /+ oldSolver.varsToStore,
		attachPalmState(pb, oldSolver.state),
		attachPalmExtend(pb, oldSolver.extending),
		attachPalmLearn(pb, oldSolver.learning),
		attachPalmRepair(pb, oldSolver.repairing),
		ps.problem := pb
	)]

[attachPalmExtend(pb: PalmProblem, ext: PalmExtend) : void
 => pb.palmSolver.extending := ext,
	ext.manager := pb.palmSolver]

[attachPalmLearn(pb: PalmProblem, ext: PalmLearn) : void
 => pb.palmSolver.learning := ext,
	ext.manager := pb.palmSolver]

[attachPalmRepair(pb: PalmProblem, ext: PalmRepair) : void
 => pb.palmSolver.repairing := ext,
	ext.manager := pb.palmSolver]

[attachPalmState(pb: PalmProblem, ext: PalmState) : void
 => pb.palmSolver.state := ext,
	ext.manager := pb.palmSolver]

[attachPalmBranchings(pb: PalmProblem, lbr: list[PalmBranching]) : void
 => let lb := list{copy(t) | t in lbr},
		n := length(lb),
		ext := pb.palmSolver.extending
	in (
		for i in (1 .. n - 1) lb[i].nextBranching := lb[i + 1],
		for br in lb br.extender := ext,
		ext.branching := lb[1] // initialiser le processus !!! 
	)]


// *************************************************
// * Part 3 : Solving Problems                     *
// *************************************************


// the general solve method that search for one feasible solution
[searchOneSolution(pb: PalmProblem): boolean
 -> let algo := pb.palmSolver
	in (
		//[PalmDEBUG] PALM: searching the next solution ,
		run(algo),
		storeSolution(algo),
		algo.feasible
	)]


// the general algorithm 
[run(algo: PalmSolver)
 -> let pb := algo.problem
	in (
		time_set(),									// starting time log
		try (
		    algo.finished := false,                 // initializing termination information
			try (
				//[PalmDEBUG] PALM: initial propagation ,
				propagate(pb)						// initial propagation
			)					    
			catch PalmContradiction (
				//[PalmDEBUG] PALM: !!! a problem occurred during initial propagation ,
				repair(algo)
			),
			//[PalmDEBUG] PALM: starting the search loop ,
			while not(algo.finished) (				// while no solution has been found
				
				try (
					//[PalmDEBUG] PALM: extending the current partial solution ,
					extend(algo),					// set up new decisions (constraints)
					//[PalmDEBUG] PALM: propagating the new constraint(s) , 
					propagate(pb)					// propagate the resulting problem
				)
				catch PalmContradiction (
					//[PalmDEBUG] PALM: !!! a contradiction occurred while propagating extensions ,
					repair(algo)					// if a contradiction occurs repair
				)
			),
			algo.feasible := true					// a solution was found
		)
		catch contradiction (						// there is no possible solution
			//[PalmDEBUG] PALM: no solution found ,
			algo.finished := true,					// the search is finished
			algo.feasible := false					// the problem is not feasible
		),
		setRuntimeStatistic(algo, CPU, time_get())	// stop the time log
	)]	




// solving the feasibility problem 
// same code as in run(PalmSolver) in order to be able
// to store consecutive solutions ... 
[runonce(algo: PalmBranchAndBound) : void
 -> time_set(), 
	try (
		while not(algo.finished) (
			try (
				extend(algo),
				propagate(algo.problem)
			)
			catch PalmContradiction (
				repair(algo)
			)
		), 
		algo.feasible := true
	)
	catch contradiction (
		algo.finished := true,
		algo.feasible := false
	),
	setRuntimeStatistic(algo, CPU, time_get())]

[run(algo: PalmBranchAndBound) : void
 -> try ( 
		post(algo.problem, algo.objective >= algo.lowerBound),
		post(algo.problem, algo.objective <= algo.upperBound),
		propagate(algo.problem)
	)
	catch contradiction (
		//[PalmVIEW] optimality proven !!! // ,
		algo.finished := true,
		algo.feasible := false
	),
	until not(algo.feasible) (
		runonce(algo),
		storeSolution(algo),
		if (algo.feasible) postDynamicCut(algo)			
	)]

[postDynamicCut(algo: PalmBranchAndBound) : void
 -> let pb  := algo.problem,
		obj := algo.objective,
		bv := getObjectiveValue(pb, obj, algo.maximizing) // how to handle heuristic based algorithms
	in (
		reset(algo),
		try (
			if algo.maximizing algo.lowerBound :max bv else algo.upperBound :min bv,
			let cut := (if algo.maximizing (obj > bv) else (obj < bv))
			in (
				algo.dynamicCuts :add cut,
				post(pb, cut)
			),
			try ( propagate(pb) ) catch PalmContradiction ( repair(algo) )
		)
		catch contradiction (
			algo.finished := true,
			algo.feasible := false
		)
	)]
	

// *************************************************
// * Part 4 : Extending a solution                 *
// *************************************************

(abstract(extend)) // <v1.08> <ygg> compilation bug fix

[extend(algo: PalmSolver): void
 => explore(algo.extending, algo.extending.branching)]    // branchings are chained


[explore(ext: PalmExtend, b: PalmBranching) : void
 -> when v := selectBranchingItem(b) in (	
		propagateAllDecisionConstraints(ext.manager.problem, selectDecisions(b, v))
	) 
	else ( 
		when nB := get(nextBranching, b) in explore(ext, nB)
		else b.extender.manager.finished := true
	)]

[explore(ext: PalmUnsureExtend, b: PalmBranching) : void
 -> when v := selectBranchingItem(b) in (	
		propagateAllDecisionConstraints(ext.manager.problem, selectAuthorizedDecisions(b, v))
	) 
	else ( 
		when nB := get(nextBranching, b) in explore(ext, nB)
		else b.extender.manager.finished := true
	)]

[selectAuthorizedDecisions(b: PalmBranching, v: any) : list<AbstractConstraint>
 -> let accepted := false,
		ext := b.extender, 
		pb := ext.manager.problem
	in (
		let decisions := getNextDecisions(b)
		in (
			while not(checkAcceptable(b, decisions) & checkAcceptable(ext.manager.learning, decisions)) (
					learnFromRejection(b),
					decisions := getNextDecisions(b)
			),
			decisions
		)
	)]

// <cl3> bug 
;[selectAuthorizedDecisions(b: PalmBranching, v: any) : list[AbstractConstraint]
; -> let accepted := false,
;		ext := b.extender, 
;		pb := ext.manager.problem
;	in (
;		while not(accepted) (
;			let decisions := getNextDecisions(b)
;			in (
;				if (checkAcceptable(b, decisions) & checkAcceptable(ext.manager.learning, decisions)) (
;					accepted := true,
;					propagateAllDecisionConstraints(pb, decisions)
;				)
;				else (
;					learnFromRejection(b)
;				)
;			)
;		)
;	)]

// generic post mechanism for a set of constraints
[propagateAllDecisionConstraints(pb: PalmProblem, cts: list[AbstractConstraint]): void
 ->	let manager := pb.palmSolver
	in (
		incRuntimeStatistic(manager, EXT, 1), // a bunch of constraint at the same time : only 1 extension
		for ct in cts (
			addDecision(manager.state, ct),
			post(pb, ct, 0), propagate(pb)
		)
	)]


// ** need to specify the following methods
[selectBranchingItem(b: PalmBranching) : any
 -> error("*** PaLM error : need to specify selectBranchingItem for a ~S", b.isa),
	unknown]
(abstract(selectBranchingItem))

[selectDecisions(b: PalmBranching, v: any) : list<AbstractConstraint>
 -> error("*** PaLM error : need to specify selectDecisions for a ~S", b.isa),
	list<AbstractConstraint>()]
(abstract(selectDecisions))

[getNextDecisions(b: PalmBranching) : list<AbstractConstraint>
 -> error("*** PaLM error : need to specify getNextDecisions for a ~S", b.isa),
	list<AbstractConstraint>()]
(abstract(getNextDecisions))

// this method checks if the set of decisions is acceptable by the branching component
[checkAcceptable(b: PalmBranching, cts: list[AbstractConstraint]) : boolean
 -> error("*** PaLM error : need to specify checkAcceptable for a ~S", b.isa),
	false]

// this method learns from the rejected decisions (if possible)
[learnFromRejection(b: PalmBranching) : void
 => error("*** PaLM error : need to specify learnFromRejection for an ~S", b.isa)]
(abstract(learnFromRejection))



// *************************************************
// * Part 5 : Repairing a solution                 *
// *************************************************

(abstract(repair)) // <v1.08> <ygg> compilation bug fix

[repair(pb: PalmProblem): void
 => repair(pb.palmSolver)]

// The generic repair technique 
[repair(algo: PalmSolver) : void
 -> let pb := algo.problem,
		pe := pb.propagationEngine,
		state := algo.state, 
		learner := algo.learning,
		repairer := algo.repairing
	in (
		if (pe.contradictory) (
			let e  := Explanation(),	
				fv := pe.contradictionCause
			in (
				self_explain(fv, DOM, e),
				assert(valid?(e)),
;				// [0] failing variable ~S // fv,
;				// [0] contradiction explanation ~S // e, 
; 				if not(valid?(e)) error("impossible to get an invalid explanation"), 
				pe.contradictory := false,
				learnFromContradiction(learner, e),
				if (empty?(e)) (
					raiseSystemContradiction(pe)
				)
				else (	
					when ct := selectDecisionToUndo(repairer, e)
					in (
;						// [0] willing to remove ~S // ct,
						if (weight(ct) <= pb.maxRelaxLvl) ( // the constraint is relaxable
							incRuntimeStatistic(algo, RLX, 1),
							if (weight(ct) > 0) ( // not an enumeration constraint
								//[PalmVIEW] PALM: Removing constraint ~S (w:~S) // ct, weight(ct)
							) 
							else ( // need to maintain the current search state 
								removeDecision(state, ct)
							),
							try (
;								// [0] removing ~S // ct,
								remove(pb,ct),  
								propagate(pb)
							)
							catch PalmContradiction (
								repair(algo)								
							),
							if (weight(ct) = 0) (// an enumeration constraint
;								// [0] will try to negate the constraint // ,
								deleteConstraint(e,ct),
								
								when negct := negate(ct)
								in (
									if valid?(e) (
										clean(e), 
										try (
;										// [0] posting ~S // negct, 
											post(pb, negct, e), propagate(pb)
										)
										catch PalmContradiction (
											repair(algo)
										)
									)
								)
							)
						)
						else (
							raiseSystemContradiction(pe) // inadequate constraint
						)
					)
					else (
						raiseSystemContradiction(pe) // no available constraint for relaxation
					)
				)
			)
		)
	)]
		
// *************************************************
// * Part 6 : Maintaining the search state         *
// *************************************************



[addDecision(state: PalmState, ct: AbstractConstraint): void
 => addConstraint(state.path,ct)]
(abstract(addDecision))


[reverseDecision(state: PalmState, ct: AbstractConstraint): void
 => deleteConstraint(state.path,ct)]
(abstract(reverseDecision))

[removeDecision(state: PalmState, ct: AbstractConstraint): void
 => learnFromRemoval(state.manager.learning, ct),
	deleteConstraint(state.path,ct)]
(abstract(removeDecision))

// *************************************************
// * Part 7 : Learning from contradictions         *
// *************************************************

// learning something from the contradiction ? 
[learnFromContradiction(learner: PalmLearn, e: Explanation): void
 => nil]
(abstract(learnFromContradiction))

[learnFromRemoval(learner: PalmLearn, ct: AbstractConstraint): void
 => nil]
(abstract(learnFromRemoval))

// this method checks if the set of decisions is acceptable by the solver
[checkAcceptable(memory: PalmLearn, cts: list[AbstractConstraint]) : boolean
 => true]
(abstract(checkAcceptable))

// this method orders the set of enumeration constraints (w=0) from an explanation 
[sortConstraintsToUndo(learner: PalmLearn, e: Explanation): list<AbstractConstraint>
 => sort(standard_better_ordered_constraint @ AbstractConstraint, 
	     list<AbstractConstraint>{c in e | weight(c) = 0})]
(abstract(sortConstraintsToUndo))

[checkAcceptableRelaxation(learner: PalmLearn, ct_out: AbstractConstraint): boolean
 => true]
(abstract(checkAcceptableRelaxation))

// *************************************************
// * Part 8 : Choosing a constraint to undo        *
// *************************************************
// chosing a constraint to relax (here is the standard technique ... complete search ensured)
[selectDecisionToUndo(repairer: PalmRepair, e: Explanation) : (AbstractConstraint U {unknown})
 => min(standard_better_constraint @ AbstractConstraint, set!(e))]
(abstract(selectDecisionToUndo))

// chosing a constraint to relax (here need to ask permission)
[selectDecisionToUndo(repairer: PalmUnsureRepair, e: Explanation) : (AbstractConstraint U {unknown})
 => let solver := repairer.manager, 
		learner := solver.learning,
		state := solver.state,
		cts := sortConstraintsToUndo(learner, e),
		idx_ct_out := 0,
		nbCandidates := length(cts),
		ct_out:(AbstractConstraint U {unknown}) := unknown,
		foundCandidate := false
	in (
		while (not(foundCandidate) & (idx_ct_out < nbCandidates))(
			idx_ct_out :+ 1,
			ct_out := cts[idx_ct_out],
			foundCandidate := (checkAcceptableRelaxation(learner, ct_out) as boolean)
		),
		if foundCandidate (
			ct_out
		)
		else 
			unknown
	)]
(abstract(selectDecisionToUndo))

// Standard constraint comparison
[standard_better_constraint(x: AbstractConstraint, y: AbstractConstraint): boolean 
 -> let xh := x.hook,
		yh := y.hook,
		wx := xh.weight,
		wy := yh.weight,
		tx := xh.timeStamp,
		ty := yh.timeStamp
	in (
		(wx < wy) | ((wx = wy) & (tx > ty))
	)]


[standard_better_ordered_constraint(ct1: AbstractConstraint, ct2: AbstractConstraint): boolean 
 -> let h1 := ct1.hook,
		h2 := ct2.hook,
		i1 := h1.searchInfo,
		i2 := h2.searchInfo,
		t1 := h1.timeStamp,
		t2 := h2.timeStamp
	in (
		(i1 > i2) | ( (i1 = i2) & (t1 > t2))
	)]



// *************************************************
// * Part 9 : Default extension                    *
// *************************************************


[getObjectiveValue(pb: PalmProblem, obj: PalmIntVar, isMax: boolean): integer
 => if isMax getSup(obj) else getInf(obj)]

PalmAssignVar <: PalmBranching()

[selectBranchingItem(b: PalmAssignVar) : any
 => getMinDomVar(b.extender.manager.problem)]

[selectDecisions(b: PalmAssignVar, v: PalmIntVar) : list<AbstractConstraint>
 => list<AbstractConstraint>(assign(v, getInf(v)))]

PalmAssignMinDomVar <: PalmAssignVar() // the default AssignVar 
PalmAssignMinDomDegVar <: PalmAssignVar()

[selectBranchingItem(b: PalmAssignMinDomDegVar) : any
 => getMinDomDegVar(b.extender.manager.problem)]

// *************************************************
// * Part 10: Variable selection tools             *
// *************************************************

// selecting a variable to assign 
[getMinDomVar(pb:Problem) : (IntVar U {unknown})
 => getMinDomVar(pb.vars)]

// selecting a variable to assign
[getMinDomVar(l:list[IntVar]) : (IntVar U {unknown})
 -> let minsize := MAXINT, v0: (IntVar U {unknown}) := unknown in
        (for v in list{v in l | getDomainSize(v) > 1} // v0.26: avoid allocation with set construction
           let dsize := getDomainSize(v) in
              (if (dsize < minsize)
                  (minsize := dsize, v0 := v)),
         v0)]

// selecting a variable to assign 
[getMinDomDegVar(pb:Problem) : (IntVar U {unknown})
 => getMinDomDegVar(pb.vars)]

// selecting a variable to assign
[getMinDomDegVar(l:list[IntVar]) : (IntVar U {unknown})
 -> let minsize := MAXINT, v0: (IntVar U {unknown}) := unknown in
        (for v in list{v in l | getDomainSize(v) > 1} // v0.26: avoid allocation with set construction
           let dsize := getDomainSize(v) / (v.nbConstraints + 1) in // taking into account unconstrained variables
              (if (dsize < minsize)
                  (minsize := dsize, v0 := v)),
         v0)]


// *************************************************
// * Part 11: Solution management tools            *
// *************************************************

// pretty printing
[self_print(sol: PalmSolution) 
 -> if (sol.feasible) (
		printf("SOL "),
		for i in (1 .. length(sol.choco/algo.varsToStore))	
			printf("~A:~S ", sol.choco/algo.varsToStore[i].name, sol.lval[i])
	) 
	else (
		printf("NO SOL ")
	),
	printf("in ~S repairs ~S extensions ~S ms", sol.lstat[1], sol.lstat[2], sol.lstat[3])]

[getRuntimeStatistic(sol: PalmSolution, s: RuntimeStatistic): integer
 => sol.lstat[s]]

[storeSolution(psolver: PalmSolver): void
 -> let sol := PalmSolution( choco/algo = psolver, 
						     lval = make_list(length(psolver.varsToStore), (integer U {unknown}), -1),
							 feasible = psolver.feasible,
							 lstat = copy(psolver.statistics)
				   )
	in (
		for i in (1 .. length(sol.lval))
			sol.lval[i] := firstValue(psolver.varsToStore[i]),
		psolver.solutions :add sol
	)]

[storeSolution(psolver: PalmBranchAndBound): void
 -> let sol := PalmSolution( choco/algo = psolver,     // <grt> Claire 3 Port missing : "choco" added ! 
						     lval = make_list(length(psolver.varsToStore), (integer U {unknown}), -1),
							 feasible = psolver.feasible,
							 lstat = copy(psolver.statistics)
				   ),
		bv := getObjectiveValue(psolver.problem, psolver.objective, psolver.maximizing) 
	in (
		if sol.feasible (
			//[PalmOPTSHOW] a new solution found ~S for ~A // bv, psolver.problem.name
		), 
		for i in (1 .. length(sol.lval))
			sol.lval[i] := firstValue(psolver.varsToStore[i]),
		sol.lval[1] := bv,
		psolver.solutions :add sol
	)]



[discardCurrentSolution(ps: PalmState): boolean 
 -> try (
		reset(ps.manager),
		try ( 
			raisePalmFakeContradiction(ps.manager.problem.propagationEngine, clone(ps.path))
		)
		catch PalmContradiction (
			repair(ps.manager)
		),
		true
	)
	catch contradiction (
		false
	)]


// *************************************************
// * Part 12: A generic learner for Path Repair    *
// *************************************************

[makePathRepairLearn(lSize: integer, mMoves: integer) : PathRepairLearn
 -> let prl := PathRepairLearn(maxMoves = mMoves,	
							   maxSize  = lSize)
	in (
		for i:integer in (1 .. prl.maxSize)
			prl.explanations :add Explanation(),
		prl
	)]

[makePathRepairLearn(c:class, lSize: integer, mMoves: integer) : PathRepairLearn
 -> let pr:PathRepairLearn := (new(c) as PathRepairLearn) 
	in (
		put(maxMoves @ PathRepairLearn, pr, mMoves),
		put(maxSize @ PathRepairLearn, pr, lSize),
		for i:integer in (1 .. pr.maxSize)
			pr.explanations :add Explanation(),
		pr
	)]


[addForbiddenSituation(prl: PathRepairLearn, ng: Explanation): void
 -> if not(prl.isFull) (
		prl.lastExplanation :+ 1,
		prl.explanations[prl.lastExplanation] := ng,
		if (prl.lastExplanation = prl.maxSize) (
			prl.isFull := true,
			prl.lastExplanation := 1
		)
	) 
	else (
		prl.explanations[prl.lastExplanation] := clone(ng),
		prl.lastExplanation := (if (prl.lastExplanation = prl.maxSize) 1 else prl.lastExplanation + 1)
	)]	

[isForbidden(prl: PathRepairLearn, ng: Explanation) : boolean
 => exists(i in (1 .. (if prl.isFull prl.maxSize else prl.lastExplanation)) | contains?(ng, prl.explanations[i]))]

// specification of interface functions 
[learnFromContradiction(learner: PathRepairLearn, e: Explanation): void
 -> let ng := Explanation(),
		ps := learner.manager,
		mM := learner.maxMoves,
		gRS := getRuntimeStatistic(ps, RLX)
	in (
		if (getRuntimeStatistic(ps, RLX) >= mM) 
			raiseSystemContradiction(learner.manager.problem.propagationEngine),
		for c in list{ c in e | weight(c) = 0}
			addConstraint(ng,c),
		addForbiddenSituation(learner, ng),
		informConstraintsInExplanation(learner, ng)
	)]

// informing constraints 
[informConstraintsInExplanation(prl: PathRepairLearn, s: Explanation): void
 -> if not(empty?(s))
		let sS: float := 1.0 / float!(size(s.explanation))
		in (
			for ct in s (
				when cth := ct.hook.searchInfo
				in ct.hook.searchInfo :+ sS
				else ct.hook.searchInfo := sS
			)
		)]
(abstract(informConstraintsInExplanation))

[learnFromRemoval(learner: PathRepairLearn, ct: AbstractConstraint): void
 => ct.hook.searchInfo := unknown]

// this method checks if the set of decisions is acceptable by the solver
[checkAcceptable(memory: PathRepairLearn, cts: list[AbstractConstraint]) : boolean
 -> let testing := Explanation(),
		st := memory.manager.state
	in (
		for c in st.path 
			addConstraint(testing,c),
		for c in cts
			addConstraint(testing,c),
		not(isForbidden(memory, testing))
	)]

[checkAcceptableRelaxation(learner: PathRepairLearn, ct_out: AbstractConstraint) : boolean
 -> let testing := Explanation(),
		st := learner.manager.state,
		ct_in := opposite(ct_out)
	in (
		for c in (st.path but ct_out)
			addConstraint(testing,c),
		addConstraint(testing,ct_in),
		not(isForbidden(learner, testing))
	)]


