// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 


// ** Summary
// Handling explanations and specific actions on them
// Part 1 : Set operations ... 
// Part 2 : New events (tools and API)                      
// Part 3 : Bound explanations 
// Part 4 : Value Removal explanations
// Part 5 : Initialising the PalmEngine ...
// Part 6 : Flushing and resetting event Queues upon contradiction
// Part 7 : Handling contradictions
// Part 8 : Removing constraints
// Part 9 : Explanation generation API 

// *************************************************************
// *   Part 1: Constraints Set operations                      *
// *************************************************************

[self_print(p: Explanation)
 -> if (PALM_USER_FRIENDLY) (
		when ct := some( c in p.explanation | true)
		in (
			let ufb := set(),
				pb := getProblem(ct)
			in (
				for c in p.explanation (
					ufb :add shortName(project(c, pb))
				),
				printf("~S", ufb)
			)	
		)
		else printf("~S", {x | x  in p.explanation})
	)
	else printf("~S", {x | x in p.explanation})]


[addConstraint(p: Explanation, c: AbstractConstraint) : void
 => add(p.explanation,c)]

[deleteConstraint(p: Explanation, c: AbstractConstraint) : void
 => delete(p.explanation,c)]

[mergeConstraints(p: Explanation, lc: set[AbstractConstraint]) : void
 => for c in lc addConstraint(p,c)]

[merge(p: Explanation,q: Explanation): void
 => for c in q.explanation addConstraint(p,c)]
 
 
[empty?(p: Explanation) : boolean
 => length(p.explanation) = 0]

[empties(p: Explanation): void
 => p.explanation := set<AbstractConstraint>()]

[delete(p: Explanation, c: AbstractConstraint) : Explanation
 => p.explanation :delete c, p]


[set!(p: Explanation): set[AbstractConstraint]
 => p.explanation]

[iterate(p: Explanation, v: Variable, e: any) 
 => (for v in p.explanation e) ]


[%(c: AbstractConstraint, p: Explanation) : boolean
 => c % p.explanation ]


[clone(p: Explanation) : Explanation
 -> let expl := Explanation()
	in (
		for c in p
			addConstraint(expl,c),
		expl
	)]

[valid?(e: Explanation): boolean
 => forall(c in e | isActive(c))]

[contains?(e: Explanation, f: Explanation): boolean // does e contains f ? 
 => forall(c in f | c % e)]

[clean(e: Explanation): void
 -> for c in clone(e) (
		if c.hook.indirect (
			deleteConstraint(e,c)
		)
	)]


// *************************************************************
// *   Part 2: New events (tools and API)                      *
// *************************************************************

// pretty printing 
[self_print(e:DecInf) : void
 -> printf("RINF(~S):~S[c:~S][i:~S]",e.modifiedVar,e.modifiedVar.inf,e.cause,e.idxInQueue)]


// pretty printing
[self_print(e:IncSup) : void
 -> printf("RSUP(~S):~S[c:~S][i:~S]",e.modifiedVar,e.modifiedVar.sup,e.cause,e.idxInQueue)]

// pretty printing 
[self_print(e:ValueRestorations) : void
 -> let lvr := list{e.valueStack[i] | i in (1 .. e.nbVals)},
		lcs := list{e.causeStack[i] | i in (1 .. e.nbVals)}
	in (
		printf("RVALS(~S):~S[c:~S][i:~S]",e.modifiedVar,lvr,lcs,e.idxInQueue)
	)]

// API 
[makeValueRestorations(var: PalmIntVar, mv: integer): ValueRestorations
 -> let e := ValueRestorations(modifiedVar = var, maxVals = mv, many = false) 
	in (
		e.valueStack := make_list(mv,integer,0),
		e.causeStack := make_list(mv,integer,0),
		e
	)]


// *************************************************************
// *   Part 3 : Bound explanations                              *
// *************************************************************

// General ... 

[addDependencies(p: Explanation)
 => addDependencies(set!(p), p) ]

[addDependencies(p: set[AbstractConstraint], e: Explanation)
 => for c in p addDependency(c,e) ]


[removeDependencies(p: Explanation, removed: AbstractConstraint)
 => for c in (p but removed) removeDependency(c, p)]

// INCINF explanation
[makeIncInfExplanation(e: Explanation, oldval: integer, v: PalmIntVar): PalmIncInfExplanation
 -> let iie := PalmIncInfExplanation(explanation = set!(e), previousValue = oldval, variable = v)
	in (
		addDependencies(iie), iie
	)]

[self_print(e: PalmIncInfExplanation)
 -> printf("~A.inf > ~S because ~S",e.variable.name, e.previousValue, set!(e))]

[postUndoRemoval(exp: PalmIncInfExplanation, removed: AbstractConstraint): void
 -> removeDependencies(exp, removed),
	restoreInf(exp.variable, exp.previousValue)]


// DECSUP explanation
[makeDecSupExplanation(e: Explanation, oldval: integer, v: PalmIntVar): PalmDecSupExplanation
 -> let iie := PalmDecSupExplanation(explanation = set!(e), previousValue = oldval, variable = v)
	in (
		addDependencies(iie), iie
	)]

[self_print(e: PalmDecSupExplanation)
 -> printf("~A.sup < ~S because ~S",e.variable.name, e.previousValue, set!(e))]

[postUndoRemoval(exp: PalmDecSupExplanation, removed: AbstractConstraint): void
 -> removeDependencies(exp, removed),
	restoreSup(exp.variable, exp.previousValue)]


(abstract(postUndoRemoval))

// *************************************************************
// *   Part 4: Value removal explanations                      *
// *************************************************************

// REMVAL explanation
[makeRemovalExplanation(e: Explanation, n: integer, v: PalmIntVar): PalmRemovalExplanation
 -> let be := PalmRemovalExplanation(explanation = set!(e), value = n, variable = v) 
	in (
	  addDependencies(be), be 
    )]

[self_print(e: PalmRemovalExplanation)
 -> printf("~A != ~S because ~S", e.variable.name, e.value, set!(e))]

[postUndoRemoval(exp: PalmRemovalExplanation, removed: AbstractConstraint)
 -> //[PalmDEBUG] PALM: Undoing removal - Val back to ~S for variable ~A // exp.value, exp.variable.name, 
	removeDependencies(exp, removed),
	restoreVal(exp.variable, exp.value),
	exp.variable.bucket.explanationOnVal[(exp.value - exp.variable.bucket.offset)] := unknown // resetting the explanation
]


// *************************************************************
// *   Part 5 : Handling value restorations ... Queue          *
// *************************************************************

[makePalmEngine(n:integer) : PalmEngine
 -> let m := 2 * n + 2,
        pe := PalmEngine(maxSize = n) in
      (pe.boundEvtQueue := choco/BoundEventQueue(
              qsize = m,
              engine = pe,
              eventQueue = make_list(m, BoundUpdate, choco/BoundUpdate()), // <grt> no DUMMY constants anymore
              qLastRead = m,     // this is the same as 0 (cyclic iteration), but with a test
              qLastEnqueued = m),// against overwhelming
		pe.boundRestEvtQueue := choco/BoundEventQueue(
			qsize = m,
			engine = pe,
			eventQueue = make_list(m, BoundUpdate, choco/BoundUpdate()), // <grt> no DUMMY constants anymore
			qLastRead = m,
			qLastEnqueued = m),
       pe.removalEvtQueue := choco/RemovalEventQueue(
              qsize = n + 1, // v0.9903
              engine = pe,
              eventQueue = make_list(m, ValueRemovals, choco/ValueRemovals()), // <grt> no DUMMY constants anymore
              qLastRead = n + 1,        // v0.9907 <naren>: must be the last spot
              qLastEnqueued = n + 1),   // v0.9907 <naren>: must be the last spot
	   pe.removalRestEvtQueue := choco/RemovalEventQueue(
              qsize = n + 1, // v0.9903
              engine = pe,
              eventQueue = make_list(m, ValueRemovals, choco/ValueRemovals()), // <grt> no DUMMY constants anymore
              qLastRead = n + 1,
              qLastEnqueued = n + 1),
       pe.instEvtStack := choco/InstantiationStack(
              qsize = n,
              engine = pe,
              eventQueue = make_list(m, Instantiation, choco/Instantiation()), // <grt> no DUMMY constants anymore
              sLastRead = 0,
              sLastPushed = 0),

   // v1.07 (choco compatibility)

       pe.delayedConst1 := choco/ConstAwakeEventQueue(engine = pe, partition = makeBipartiteSet(ConstAwakeEvent, choco/ConstAwakeEvent())), // <grt> no DUMMY constants anymore
       pe.delayedConst2 := choco/ConstAwakeEventQueue(engine = pe, partition = makeBipartiteSet(ConstAwakeEvent, choco/ConstAwakeEvent())), // <grt> no DUMMY constants anymore
       pe.delayedConst3 := choco/ConstAwakeEventQueue(engine = pe, partition = makeBipartiteSet(ConstAwakeEvent, choco/ConstAwakeEvent())), // <grt> no DUMMY constants anymore
       pe.delayedConst4 := choco/ConstAwakeEventQueue(engine = pe, partition = makeBipartiteSet(ConstAwakeEvent, choco/ConstAwakeEvent())), // <grt> no DUMMY constants anymore

       pe
      )]
		




// **********************************************************************
// *   Part 6: Flushing and resetting event Queues upon contradiction   *
// **********************************************************************


[getNextActiveEventQueue(pe:PalmEngine) : (EventCollection U {unknown})
 -> let brq := pe.boundRestEvtQueue in
	 (if not(isEmpty(brq)) brq 
	  else let rrq := pe.removalRestEvtQueue in
	   (if not(isEmpty(rrq)) rrq
	    else let iq := pe.instEvtStack in 
		  (if not(isEmpty(iq)) iq
		   else let bq := pe.boundEvtQueue in 
			(if not(isEmpty(bq)) bq
			 else let rq := pe.removalEvtQueue in
			  (if not(isEmpty(rq)) rq
			   else let cq1 := pe.delayedConst1 in
				(if not(isEmpty(cq1)) cq1
				 else let cq2 := pe.delayedConst2 in
				  (if not(isEmpty(cq2)) cq2
				   else let cq3 := pe.delayedConst3 in
					(if not(isEmpty(cq3)) cq3
					 else let cq4 := pe.delayedConst4 in
					  (if not(isEmpty(cq4)) cq4
					   else unknown)))))))))]


// Il y a eu une contradiction, il me reste des choses à propager, il faut
// absolument le faire. Comme je ne sais plus trop qui est en place ou pas,
// on remet toutes les causes des évènements qui restent à zéro
// et c'est tout ce que l'on fait !

[resetEvent(evt: BoundUpdate) : void
 => evt.cause := 0]

[resetEvent(evt: ValueRemovals) : void
 => for k: integer in (1 .. evt.nbVals)
		evt.causeStack[k] := 0]

[resetPoppingQueue(q: BoundEventQueue): void
 -> let eq := q.eventQueue, i := q.qLastRead, j := q.qLastEnqueued 
	in (
		q.redundantEvent := false, // no more propagation cycle
		if q.isPopping           // there was an open event (code -1)
			let evt := eq[i] 
			in (
				if (evt.idxInQueue < 0) (
					evt.cause := 0,
					evt.idxInQueue := i,
					q.qLastRead := (if (q.qLastRead = 1) q.qsize else q.qLastRead - 1)
				),
				q.isPopping := false
			)
	)]

[resetPoppingQueue(q: RemovalEventQueue): void
 -> let eq := q.eventQueue, i := q.qLastRead, j := q.qLastEnqueued 
	in (
		if q.isPopping       (    // there was an open event 
			let evt := eq[i] 
			in (
				evt.nbVals :+ 1, // step back to the removed value 
				for k: integer in (1 .. evt.nbVals)
					evt.causeStack[k] := 0,
				evt.idxInQueue := i, 
				q.qLastRead := (if (q.qLastRead = 1) q.qsize else q.qLastRead - 1),
				q.isPopping := false
			)
		)
	)]

[resetEventQueue(q: EventQueue) : void
 -> let eq := q.eventQueue, i := q.qLastRead, j := q.qLastEnqueued 
	in (
	 resetPoppingQueue(q), 
     if (i != j) (// the case (i=j) corresponds either to "no event" (when popping=false)
                 // or to "one single (currently open) event, no further pending events" (popping = true)
        i :+ 1, if (i > q.qsize) i := 1,  // skip it and go to next pending event
        if (i <= j) (            // we did not circle while posting events: straight iteration
          for k in (i .. j)  (   // all other events in the queue are pending // NAREN
             let evt := eq[k] 
			 in (
                assert(evt.idxInQueue = k),
				resetEvent(evt)
			 )
		  )
		)
        else (
			for k:integer in ((i .. q.qsize) /+ (1 .. j))  ( // we did circle while posting events :
                let evt := eq[k] 
				in (
                   assert(evt.idxInQueue = k),
				   resetEvent(evt)
				)
			)
		)
      ) 
	 )]



// resetting a stable state for the current problem 
[resetEvents(pb: PalmEngine): void
 -> resetEventQueue(pb.boundEvtQueue), // nettoyage de la file de propagation en cours
	resetEventQueue(pb.boundRestEvtQueue),// nettoyage de la file de restauration en cours
	resetEventQueue(pb.removalEvtQueue),	
	resetEventQueue(pb.removalRestEvtQueue)] ; ,
;	resetEventQueue(pb.delayedConst1),
;	resetEventQueue(pb.delayedConst2),
;	resetEventQueue(pb.delayedConst3),
;	resetEventQueue(pb.delayedConst4)] 
 
[flushEvents(pb: PalmEngine): void
 -> flushEventQueue(pb.boundEvtQueue), // nettoyage de la file de propagation en cours
	flushEventQueue(pb.boundRestEvtQueue),// nettoyage de la file de restauration en cours
	flushEventQueue(pb.removalEvtQueue),	
	flushEventQueue(pb.removalRestEvtQueue)] 

// **********************************************************************
// *   Part 7: Handling contradictions                                  *
// **********************************************************************

// Handling localized contradictions ... 
[raisePalmContradiction(pe: PalmEngine, v: PalmIntVar): void => 
	pe.contradictionCause := v,
	pe.contradictory := true, 
	resetEvents(pe), 
	PalmContradiction() ] // Raising a Palm contradiction (to be handled automatically)

// Handling non really localized contradiction ...
[raisePalmFakeContradiction(pe: PalmEngine, e: Explanation) : void =>
	if not(known?(dummyVariable, pe)) (
		pe.dummyVariable := makeBoundIntVar(pe.problem, "*dummy*", 0, 1),
		pe.problem.vars :delete pe.dummyVariable
	),
	updateInf(pe.dummyVariable, 2, 0, e)]

// No possible solution => pass the contradiction to choco
[raiseSystemContradiction(pe: PalmEngine) : void
 => pe.contradictory := false,
	flushEvents(pe),
	contradiction!()]


// *************************************************************
// *   Part 8: Removing constraints                            *
// *************************************************************


// Removing a single constraint
[remove(pe: PalmEngine, c: AbstractConstraint): void
 -> // step 1: unplug the constraint from the constraint network
	setPassive(c),

	// step 2: put back dependent values into the domains 
	undo(c), 

	// step 3 : restore explanations in their acceptable state 
	restoreVariableExplanations(pe)]


// Removing a list of constraints in one single step 
[remove(pe: PalmEngine, cl: list[AbstractConstraint]): void
 -> for c in cl (
		setPassive(c),
		undo(c)
	),
	restoreVariableExplanations(pe)]


// Updating variable explanations in order to reflect the current situation
[restoreVariableExplanations(pe: PalmEngine) 
 -> let q := pe.boundRestEvtQueue,	
		eq := q.eventQueue,
		i := q.qLastRead,
		j := q.qLastEnqueued
	in (
		if (i != j) ( // some  values got back into their respective domains
	       i :+ 1, if (i > q.qsize) i := 1,  // skip it and go to next pending event
	       if (i <= j) (            // we did not circle while posting events: straight iteration
		     for k:integer in (i .. j)  (   // all other events in the queue are pending // NAREN
			     let evt := eq[k] 
				 in (
					restoreVariableExplanation(evt)
				 )
			 )
		   )
		   else (
			 for k:integer in ((i .. q.qsize) /+ (1 .. j))  ( // we did circle while posting events :
                 let evt := eq[k] 
				 in (
                    restoreVariableExplanation(evt)
				 )
			 )
		   )
		)
	),
	let q := pe.removalRestEvtQueue,
		eq := q.eventQueue,
		i := q.qLastRead,
		j := q.qLastEnqueued
	in (
		if (i != j) ( // some  values got back into their respective domains
	       i :+ 1, if (i > q.qsize) i := 1,  // skip it and go to next pending event
	       if (i <= j) (            // we did not circle while posting events: straight iteration
		     for k in (i .. j)     // all other events in the queue are pending // NAREN
				 checkVariableDomainAgainstRemValEvt(eq[k].modifiedVar)				
		   )
		   else (
			 for k:integer in ((i .. q.qsize) /+ (1 .. j))   // we did circle while posting events :
			 	 checkVariableDomainAgainstRemValEvt(eq[k].modifiedVar)				
		   )
		)
	)]
	

[checkVariableDomainAgainstRemValEvt(v: PalmIntVar) 
 -> assert(known?(v.bucket)),
	let ev := v.remValEvt,
		idx := 1,
		buck := v.bucket
	in (
		while (idx <= ev.nbVals) (	// on va passer en revue tous les retraits de valeurs
			let val := ev.valueStack[idx] 
			in (
				if containsValInDomain(buck, val) (	// il y a un problème 
					ev.valueStack[idx] := ev.valueStack[ev.nbVals],
					ev.causeStack[idx] := ev.causeStack[ev.nbVals],
					ev.nbVals :- 1
				)
				else ( // c'est bon on peut avancer
					idx :+ 1
				)
			)
		)		
	)]

[propagateEvent(e: DecInf, q: BoundEventQueue) : void
 -> let v := e.modifiedVar,
		n := v.nbConstraints,
		lc := v.constraints,
		li := v.indices 
 	in (
		q.isPopping := true,
		if (n = 0) (
			e.idxInQueue := 0
		)
		else (
			let lnext := e.nextConst,
				i1 := e.cause,
				prevk := i1,
				k := (if (i1 = 0) lnext[n] else lnext[i1]),
				i0 := (if (i1 <= 1) n else (i1 - 1))
			in (
				e.idxInQueue := 0, // being propagated
				while (k > prevk) ( // first part of the iteration
					awakeOnRestoreInf(lc[k], li[k]),
					prevk := k, k := lnext[k]
				),
				while (k != lnext[i0]) ( // second part 
					awakeOnRestoreInf(lc[k], li[k]),
					k := lnext[k]
				)
			)
		),
		q.isPopping := false
 	)]

[propagateEvent(e: IncSup, q: BoundEventQueue) : void 
 ->	let v := e.modifiedVar,
		n := v.nbConstraints,
		lc := v.constraints,
		li := v.indices 
 	in (
		q.isPopping := true,
		if (n = 0) (
			e.idxInQueue := 0
		)
		else (
			let lnext := e.nextConst,
				i1 := e.cause,
				prevk := i1,
				k := (if (i1 = 0) lnext[n] else lnext[i1]),
				i0 := (if (i1 <= 1) n else (i1 - 1))
			in (
				e.idxInQueue := 0, // being propagated
				while (k > prevk) ( // first part of the iteration
					awakeOnRestoreSup(lc[k], li[k]),
					prevk := k, k := lnext[k]
				),
				while (k != lnext[i0]) ( // second part 
					awakeOnRestoreSup(lc[k], li[k]),
					k := lnext[k]
				)
			)
		),
		q.isPopping := false
 	)]

[propagateEvent(e: ValueRestorations, q: RemovalEventQueue) : void 
 ->	let v := e.modifiedVar,
		n := v.nbConstraints,
		lc := v.constraints,
		li := v.indices 
 	in (
		e.idxInQueue := 0,
		if (n > 0) (
			let lnext := e.nextConst
			in (
				while (e.nbVals > 0) (
					let nbv := e.nbVals,
						i1 := e.causeStack[nbv],
						prevk := i1,
	                    k := (if (i1 = 0) lnext[n] else lnext[i1]),
		                i0 := (if (i1 <= 1) n else (i1 - 1)) 
					in (
						e.nbVals :- 1,
						if (k > 0) (
							q.isPopping := true,
							if e.many ( // many value removals at once 
								while (k > prevk) ( // first part of the iteration
									error("PALM multiple value restorations not handled in p-explain.cl"),
									// awakeOnRestoreVar(lc[k], li[k]),
									prevk := k, k := lnext[k]
								),
								while (k != lnext[i0]) ( // second part 
									error("PALM multiple value restorations not handled in p-explain.cl"),
									// awakeOnRestoreVar(lc[k], li[k]),
									k := lnext[k]
								)
							)
							else (
								let x := e.valueStack[nbv]
								in (
									while (k > prevk) ( // first part of the iteration
										awakeOnRestoreVal(lc[k], li[k], x),
										prevk := k, k := lnext[k]
									),
									while (k != lnext[i0]) ( // second part 
										awakeOnRestoreVal(lc[k], li[k], x),
										k := lnext[k]
									)								
								)
							),
							q.isPopping := false
						)
					)
				)
			)
		)
 	)]


// *************************************************************
// *   Part 9: Explanation Generation API                      *
// *************************************************************
// This part was asked by THB 

[becauseOf(causes:listargs) : Explanation
 -> let e := Explanation() in (
		for c in causes (
			case c (
				Explanation merge(e,c), // useful for factorizing common explanations
				AbstractConstraint self_explain(c,e),
				PalmIntVar self_explain(c, DOM, e),
				tuple (case c[1] (
						AbstractConstraint (for v in c[1].vars self_explain(v, DOM, e)),
						PalmIntVar (if (length(c) = 2) self_explain(c[1],c[2],e)
								   else self_explain(c[1],VAL,c[3],e)),
						any error("PaLM: typing error in becauseOf (p-explain.cl)") 
					  )
				),
				any error("PaLM: typing error in becauseOf (p-explain.cl)")
			)
		),
		e
	)]
 
 [theInf(v: PalmIntVar) : tuple(PalmIntVar, integer) => tuple(v, INF)]
 [theSup(v: PalmIntVar) : tuple(PalmIntVar, integer) => tuple(v, SUP)]
 [theDom(v: PalmIntVar) : tuple(PalmIntVar, integer) => tuple(v, DOM)]
 [theHole(v: PalmIntVar, x: integer) : tuple(PalmIntVar, integer, integer) => tuple(v, VAL, x)]
 [theVars(c: AbstractConstraint) : tuple(AbstractConstraint) => tuple(c)]


// explaining a current state 
[explain(v: PalmIntVar, s: SELECT) : Explanation 
 -> let e := Explanation() in (self_explain(v,s,e), e)]

[explain(v: PalmIntVar, s: SELECT, val: integer) : Explanation 
 -> let e := Explanation() in (self_explain(v,s,val,e), e)]

[explain(c: AbstractConstraint) : Explanation 
 -> let e := Explanation() in (self_explain(c,e), e)]

[explain(pb: PalmProblem) : Explanation 
 -> let pe := pb.propagationEngine
	in (
		if (pe.contradictory) (
			let e := Explanation(),
				fv := pe.contradictionCause
			in (
				self_explain(fv, DOM, e),
				e
			)
		)
		else (
			error("explain@PalmProblem should only be used for over-constrained problems"),
			Explanation() // fooling the compiler
		)
	)]
 
 
