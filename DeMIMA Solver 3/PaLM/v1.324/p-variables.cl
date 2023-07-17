// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 

// ** Summary
// Part 1 : Tools and basic stuff 
// Part 2 : Data Structure maintenance
// Part 3 : Domain restoration
// Part 4 : Domain modification


// *************************************************************
// *   Part 1: Tools and basic stuff                           *
// *************************************************************

INF :: 1
SUP :: 2
VAL :: 3
DOM :: 4

SELECT :: {INF, SUP, VAL, DOM}

// printing ... 
[self_print(t: PalmIntVar) 
 -> if (known?(bucket, t)) (
		if known?(name,t) printf("~A:",t.name) else printf("<IntVar>"),
		if isInstantiated(t) 
			printf("~S",t.value)
		else
			printf("[~S]~S",t.bucket.nbElements, t.bucket)
	)
	else  (
		if isInstantiated(t) 
			printf("~A:~S",t.name, t.value)
		else (
			if (t.inf > t.sup) printf("~A:[]", t.name)
			else printf("~A:[~S..~S]", t.name, t.inf, t.sup)
		)
	)]

// accessing the lower bound 
[getInf(v: PalmIntVar) : integer
 -> if (known?(bucket, v) & v.bucket.needInfComputation) (
         v.inf := getInf(v.bucket),
         v.bucket.needInfComputation := false
	),
	v.inf]

// accessing the upper bound 
[getSup(v: PalmIntVar) : integer
 -> if (known?(bucket, v) & v.bucket.needSupComputation) (
         v.sup := getSup(v.bucket),
         v.bucket.needSupComputation := false
	), 
	v.sup] 


[self_explain(v: PalmIntVar, s: SELECT, e: Explanation) : void
 -> if (known?(bucket, v)) 
		case s 
		(
			{DOM} for x in ((v.bucket.offset + 1) .. (v.bucket.offset + v.bucket.bucketSize)) self_explain(v,VAL,x,e),
			{INF} for x in ((v.bucket.offset + 1) .. (getInf(v) - 1)) self_explain(v, VAL, x, e),
			{SUP} for x in ((getSup(v) + 1) .. (v.bucket.offset + v.bucket.bucketSize)) self_explain(v, VAL, x, e),
			{VAL} error("PaLM : VAL needs another parameter in self_explain (p-variables.cl)")
		)
	else
		case s 
		(
			{INF} merge(e,v.explanationOnInf[length(v.explanationOnInf)]), 
			{SUP} merge(e,v.explanationOnSup[length(v.explanationOnSup)]), 
			{DOM} (self_explain(v,INF,e), self_explain(v,SUP,e)),
			{VAL} error("PaLM : VAL needs another parameter in self_explain (p-variables.cl)")
		)]

[self_explain(v: PalmIntVar, s: SELECT, x: integer, e: Explanation) : void
 -> if (s = VAL) (
		if (known?(bucket, v)) (	
			let realVal := x - v.bucket.offset,
				explSet := (if (realVal > 0 & realVal <= length(v.bucket.explanationOnVal)) v.bucket.explanationOnVal[(x - v.bucket.offset)] else unknown) 
			in ( 
				if known?(explSet) merge(e,explSet)
			)
		) 
		else (
			if (x < v.inf)
				merge(e,v.explanationOnInf[length(v.explanationOnInf)]) // self_explain(v, INF, e)
			else if (x > v.sup)
				merge(e,v.explanationOnSup[length(v.explanationOnSup)]) // self_explain(v, SUP, e)
		)
	) else (
		error("PaLM : INF, SUP or DOM do not need a supplementary parameter in self_explain (p-variables.cl)")
	)]


// FirstValue in the domain
[firstValue(v: PalmIntVar): integer
 -> if known?(bucket, v)
		firstElement(v.bucket)
	else	
		v.inf]


// *************************************************************
// *   Part 2 : Data Structure maintenance                     *
// *************************************************************

// The general purpose method 
[updateDataStructures(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 => updateDataStructuresOnVariable(v, way, newValue, oldValue), // updating the DS maintained on the variable
	// [0] updateDataStructuresOnConstraint 17,
	updateDataStructuresOnConstraints(v, way, newValue, oldValue)]

// Handling Data Structure maintenance
[updateDataStructuresOnVariable(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 =>	nil]
// to be redefined for specific variables 
(abstract(updateDataStructuresOnVariable))


[updateDataStructuresOnConstraints(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 -> let evt := (if (way = INF) v.updtInfEvt else if (way = SUP) v.updtSupEvt else v.remValEvt),
		lnext := evt.nextConst,
		li := v.indices,
		lc := v.constraints,
		n := v.nbConstraints,
		k := (if (n != 0) lnext[n] else 0),
		end := n
	in ( 
		if (k != 0) (
			until (k = lnext[end]) (
		 		// [0] updateDataStructuresOnConstraint 18,
		 		// [0] lc[k].isa = ~S // lc[k].isa, 
		 		// [0] li[k] = ~S // li[k], 
		 		// [0] way = ~S // way, 
		 		// [0] newValue = ~S // newValue, 
		 		// [0] oldValue = ~S // oldValue, 
				updateDataStructuresOnConstraint(lc[k], li[k], way, newValue, oldValue),
				k := lnext[k]
			)
		)
	)]

[updateDataStructuresOnConstraint(c: AbstractConstraint, idx: integer, way: SELECT, v: integer, nextValue: integer) : void
 => nil]
(abstract(updateDataStructuresOnConstraint))



// The general purpose method 
[updateDataStructuresOnRestore(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 => updateDataStructuresOnRestoreVariable(v, way, newValue, oldValue), // updating the DS maintained on the variable
	updateDataStructuresOnRestoreConstraints(v, way, newValue, oldValue)]

[updateDataStructuresOnRestoreVariable(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 => nil]
(abstract(updateDataStructuresOnRestoreVariable))


[updateDataStructuresOnRestoreConstraints(v: PalmIntVar, way: SELECT, newValue: integer, oldValue: integer) : void
 -> let evt := (if (way = INF) v.restInfEvt else if (way = SUP) v.restSupEvt else v.restValEvt),
		lnext := evt.nextConst,
		li := v.indices,
		lc := v.constraints,
		n := v.nbConstraints,
		k := (if (n != 0) lnext[n] else 0),
		end := n
	in ( 
		if (k != 0) (
			until (k = lnext[end]) (
				updateDataStructuresOnRestoreConstraint(lc[k], li[k], way, newValue, oldValue),
				k := lnext[k]
			)
		)
	)]

[updateDataStructuresOnRestoreConstraint(c: AbstractConstraint, idx: integer, way: SELECT, v: integer, w: integer) : void
 => nil]
(abstract(updateDataStructuresOnRestoreConstraint))



// *************************************************************
// *   Part 3 : Domain restoration                             *
// *************************************************************

// restoring INF for v 
[restoreInf(v: PalmIntVar, newValue: integer): void
-> if (v.inf > newValue) (
      let oldValue := v.inf in (
		v.inf := newValue, 
		if (v.inf != v.sup)
			v.value := UNKNOWNINT,
		if (v.inf = v.sup)
			v.value := v.inf, 
		//[PalmDEBUG] restoring INF ~S from ~S to ~S // v, oldValue, newValue, 
;		// [0] will call restore on ~S // v.constraints, 
		updateDataStructuresOnRestore(v, INF, newValue, oldValue),
		postRestoreInf(v.problem.propagationEngine, v)	
	  )					
	)]

// retour sur la borne sup 
[restoreSup(v: PalmIntVar, newValue: integer): void
-> if (v.sup < newValue) (
      let oldValue := v.sup in (
		v.sup := newValue,
		if (v.inf != v.sup)
			v.value := UNKNOWNINT,
		if (v.inf = v.sup)
			v.value := v.sup, 
		//[PalmDEBUG] restoring SUP ~S from ~S to ~S // v, oldValue, newValue, 
;		// [0] will call restore on ~S // v.constraints, 
		updateDataStructuresOnRestore(v, SUP, newValue, oldValue),
		postRestoreSup(v.problem.propagationEngine,v)
	  )
	)]

[postRestoreInf(pe: PalmEngine, v: PalmIntVar): void
 => postRestoreEvent(pe, v.restInfEvt)]

[postRestoreSup(pe: PalmEngine, v: PalmIntVar): void
 => postRestoreEvent(pe, v.restSupEvt)]


[postRestoreEvent(pe: PalmEngine, e: BoundUpdate) : void 
 -> let bq := pe.boundRestEvtQueue,
		idxQ := e.idxInQueue
	in (
		if (idxQ <= 0) // no event in the queue 
			let idx := nextEventPostIndex(bq) 
			in (
				bq.eventQueue[idx] := e,
				e.idxInQueue := idx
			)
	)]

[restoreVariableExplanation(e: DecInf): void
 -> let newlist := list<PalmIncInfExplanation>(),
		keep: boolean := true,
		v := e.modifiedVar
	in (
		for exp in v.explanationOnInf (
			let pv := exp.previousValue 
			in (
				if (pv < v.inf)
					newlist :add exp
				else if (keep & (pv = v.originalInf))
					(
						newlist :add exp,
						keep := false
					)
				)
			),
		v.explanationOnInf := newlist 
	)]

[restoreVariableExplanation(e: IncSup): void
 -> let newlist := list<PalmDecSupExplanation>(),
		keep: boolean := true,
		v := e.modifiedVar
	in (
		for exp in v.explanationOnSup (
			let pv := exp.previousValue 
			in (
				if (pv > v.sup)
					newlist :add exp
				else if (keep & (pv = v.originalSup))
					(
						newlist :add exp,
						keep := false
					)
				)
			),
		v.explanationOnSup := newlist 
	)]

[restoreVal(v: PalmIntVar, val: integer): void
 -> if (v.bucket.nbElements = 1) v.value := UNKNOWNINT,
	addDomainVal(v.bucket, val),
    if (val < v.inf)
       v.inf := val,
    if (val > v.sup)
       v.sup := val,
	if (v.bucket.nbElements = 1) 
		v.value := firstElement(v.bucket)
	else 
		v.value := UNKNOWNINT, 
   updateDataStructuresOnRestore(v, VAL, val, 0),
   postRestoreVal(v.problem.propagationEngine, v, val) ]


[postRestoreVal(pe: PalmEngine, v: PalmIntVar, value: integer)
 => postRestoreEvent(pe, v.restValEvt, value) ] 

[postRestoreEvent(pe: PalmEngine, e: ValueRestorations, value: integer) 
 -> let bq := pe.removalRestEvtQueue,
		idxQ := e.idxInQueue
	in (
		if (idxQ <= 0) // no event in the queue 
			let idx := nextEventPostIndex(bq) 
			in (
				bq.eventQueue[idx] := e,
				e.idxInQueue := idx,
				e.many := false, 
				e.valueStack[1] := value,
				e.nbVals := 1,
				e.causeStack[1] := 0 // unused here
			)
		else (
			if ((e.many != true) & not(value % list{e.valueStack[i] | i in (1 .. e.nbVals)})) (
				let nbRems := e.nbVals + 1
				in (	
					if (nbRems <= e.maxVals) (
						e.nbVals :+ 1,
						e.valueStack[nbRems] := value
					)
					else (
						error("PaLM error: PaLM does not handle multiple value restorations (p-variables.cl)"),
						e.many := true,
						e.nbVals := 1,
						e.valueStack[1] := value
					)
				)
			)
		)
	)]

// *************************************************************
// *   Part 4 : Domain modification                            *
// *************************************************************

				
[updateInf(v: PalmIntVar, x:integer, idx:integer, e: Explanation) : boolean
 -> assert(valid?(e)),
	if known?(bucket, v) (
		let rep := false 
		in (
			
			for i in (getInf(v) .. (x - 1)) 
				rep := removeVal(v, i, idx, clone(e)) | rep,
			rep
		)
	)
	else (
		if updateInf(v,x,e) (
			postUpdateInf(v.problem.propagationEngine, v, idx),
			if (x > v.sup) (
				v.value := UNKNOWNINT,
				raisePalmContradiction(v.problem.propagationEngine, v)
			),
			true
		) 
		else
			false
	)]

[updateInf(v: PalmIntVar, x:integer, e: Explanation) : boolean
 -> if (x > v.inf) ( 
		let oldValue := v.inf, 
			newValue := x 
		in (
			//[PalmDEBUG] PALM: ~S.inf: ~S -> ~S because of ~S// v,v.inf,x, e, 
			self_explain(v,INF,e),
			v.explanationOnInf :add makeIncInfExplanation(e,v.inf,v),
			v.inf := x,
			if (v.inf = v.sup) v.value := v.inf, 
		    updateDataStructures(v, INF, newValue, oldValue)
		),
		true
	)
    else 
		false ]



[updateSup(v: PalmIntVar, x:integer, idx:integer, e: Explanation) : boolean
 -> assert(valid?(e)),
	if known?(bucket, v) (
		let rep := false
		in (
			for i in ((x + 1) .. getSup(v)) 
				rep := removeVal(v, i, idx, clone(e)) | rep,
			rep
		)
	)
	else (
		if updateSup(v,x,e) (
			postUpdateSup(v.problem.propagationEngine, v, idx),
			if (x < v.inf) (
				v.value := UNKNOWNINT, 
				raisePalmContradiction(v.problem.propagationEngine, v)
			),
			true
		)
		else
			false
	)]

[updateSup(v: PalmIntVar, x: integer, e: Explanation) : boolean
 -> if (x < v.sup) ( 
		let oldValue := v.sup,
			newValue := x 
		in (
			//[PalmDEBUG] PALM: ~S.sup: ~S -> ~S because of ~S// v,v.sup,x, e,
			self_explain(v,SUP,e),
			v.explanationOnSup :add makeDecSupExplanation(e,v.sup,v),
			v.sup := x,
			if (v.inf = v.sup) v.value := v.inf, 
	       	updateDataStructures(v, SUP, newValue, oldValue)
		),
		true
     )
     else 
		false ]

[removeVal(v: PalmIntVar, x: integer, idx: integer, e: Explanation): boolean
 -> if known?(bucket, v) (
		if removeVal(v,x,e) (
		  postRemoveVal(v.problem.propagationEngine, v, x, idx),
		  if (v.bucket.nbElements = 0) (
			raisePalmContradiction(v.problem.propagationEngine, v)
		  ),
		  true
		)
		else
			false
	)
	else (
		if (x = getInf(v)) updateInf(v, x + 1, idx, e)
		else if (x = getSup(v)) updateSup(v, x - 1, idx, e)
		else false
	)]

[removeVal(v: PalmIntVar, x: integer, e: Explanation) : boolean
 -> let buck := v.bucket
	in (
		if containsValInDomain(buck, x) (
			//[PalmDEBUG] PALM: ~S.val != ~S because of ~S// v,x, e,
			buck.explanationOnVal[(x - v.bucket.offset)] := makeRemovalExplanation(e,x,v),
			let nextValX := buck.succVector[x - buck.offset] + buck.offset
			in (
				removeDomainVal(buck, x),
				if (x = v.inf)
				   buck.needInfComputation := true,
				if (x = v.sup)
				   buck.needSupComputation := true,
				if (buck.nbElements = 1)
					v.value := firstElement(buck),
				if (buck.nbElements = 0)
					v.value := UNKNOWNINT,
				updateDataStructures(v, VAL, x, 0)
			),
			true
		 )
		 else
			false 
	)]

// need a specific postRemoveVal for PaLM ... storing information about propagation 
// for enumerated variables ...
[postRemoveVal(pe:PalmEngine,v:IntVar, x:integer, i:integer) : void
 -> //[PDEBUG] try to post REMOVAL ~S <> ~S [cause:~S] // v,x,i,
    let rq := pe.removalEvtQueue, e := v.remValEvt, idxQ := e.idxInQueue in
      (if (idxQ = 0)   // there are no event (REMVAL or REMVALS v) pending in the queue
         let idx := nextEventPostIndex(rq) in
            (//[PTALK]   ++ posted ~S:~S // idx,e,
             e.idxInQueue := idx,
             e.many := false, // this event removes only one value and not an set of them,
             e.valueStack[1] := x,
             e.nbVals := 1,
             e.causeStack[1] := i,
             rq.eventQueue[idx] := e)
       else (// there is already such a similar event in the queue
              if (idxQ != -1) ; <naren 24/5> handling value removals in x while propagating x
	            assert(rq.eventQueue[idxQ] = e)
			 else ( // Il faut conserver l'information actuelle qqpart en cas de problème !!! 
				e.valueStack[e.nbVals + 2] := e.valueStack[e.nbVals + 1],
				e.causeStack[e.nbVals + 2] := e.causeStack[e.nbVals + 1]
			 ),
            if (e.many = true)       // when abstract REMOVALS event present,
                (assert(e.nbVals = 1),
                 if (e.causeStack[1] != i)
                     e.causeStack[1] := 0)
             else let nbRems := e.nbVals + 1 in
                (if (nbRems <= e.maxVals) // this is a simple REMOVAL event
                    (assert(nbRems > 0),
                     e.nbVals :+ 1,
                     e.valueStack[nbRems] := x,
                     e.causeStack[nbRems] := i)
                 else (assert(nbRems = e.maxVals + 1),
                       //[PTALK] event abstraction REMVAL(~S) ~S and ~S  -> REMVALS // v,e.valueStack,x,
                       e.many := true,
                       e.nbVals := 1,
                       e.valueStack[1] := x,
                       e.causeStack[1] := i,
                       rq.engine.propagationOverflow := true,
                       if exists(j in (2 .. nbRems - 1) | e.causeStack[j] != i)
                          e.causeStack[1] := 0,
                       #if not(compiler.active?)
                          (e.valueStack[1] := 0,
                           for i in (2 .. nbRems - 1)
                             (e.causeStack[i] := 0,
                              e.valueStack[i] := 0))
                       )))) ]

[instantiate(v: PalmIntVar, x: integer, idx: integer, e: Explanation): boolean
 -> let change := false
	in (
		change := updateInf(v, x, idx, clone(e)),
		change := updateSup(v, x, idx, clone(e)) | change,
		change 
	)]
