// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM : gestion des booléens 

// ** Summary : boolean connectors
// Part 1 : OR : PalmDisjunction
// Part 2 : IFTHEN : PalmGuard 
// Part 3 : IFF : PalmEquiv
// Part 4 : AND : PalmConjunction
// Part 5 : Large AND : PalmLargeConjunction
// Part 6 : Large OR : PalmLargeDisjunction
// Part 7 : Cardinality : PalmCardinality

// General comments on handling boolean constraints within PaLM

// (a) Decisions made when propagating need to be taken within the 
// updateDataStructure mechanisms (working on the Status and 
// TargetStatus) but decisions need to be enforced only when 
// effectively propagating (in the awakeOnXXX).
// Enforced constraints are considered as INDIRECT constraints
// whose activation explanation is computed on the fly. 
 
// (b) When undoing a decision, it may happen that one or several 
// indirect constraints were dependent on that decision. It is 
// mandatory to undo their effects (which is automatically done
// by the indirect constraint mechanmis) but also to undo any decision 
// made on the Status or TargetStatus related to this constraints
// That is why a slot in PalmInfoConstraint records all the controlling 
// constraint of a given constraint in order to be able to 
// undo all the decisions made on the associated constraints.

// (c) In order to correctly undo the past effects of the removed 
// constraint the idea consists in undoing all decisions that was 
// based on that behavior i.e. the computation of status and/or 
// target status letting the system rechecks all the situations alone. 


// *************************************************
// * Part 1 : OR                                   *
// *************************************************

PalmDisjunction <: choco/Disjunction(
	isContradictory: boolean = false,
	needToAwakeC1: boolean = false,
	needToAwakeC2: boolean = false
)

[initHook(c: PalmDisjunction): void
 -> c.hook := PalmInfoConstraint(),
	addControl(c.const1, c, 1), 
	addControl(c.const2, c, 2)]

[takeIntoAccountStatusChange(d: PalmDisjunction, idx: integer) : void
 -> d.isContradictory := false,
	removeIndirectDependance(d.const1), setDirect(d.const1),
	removeIndirectDependance(d.const2), setDirect(d.const2),
	setNeedToAwake(d, 1, false), setNeedToAwake(d, 2, false), 
	d.statusBitVector := 0]

[self_print(d:PalmDisjunction) -> printf("(~S) e-OR (~S)", d.const1, d.const2)]

[needToAwake(d: PalmDisjunction, i: integer) : boolean
 => if (i = 1) d.needToAwakeC1 else d.needToAwakeC2]
[setNeedToAwake(d: PalmDisjunction, i: integer, val: boolean) : void
 => if (i = 1) d.needToAwakeC1 := val else d.needToAwakeC2 := val]

[checkStatusAndReport(d: PalmDisjunction, i: integer) : void
 -> if not(knownStatus(d,i)) (
		when b := askIfTrue((if (i = 1) d.const1 else d.const2)) 
		in (
			if knownTargetStatus(d,i) (
				let tgtb := getTargetStatus(d,i) 
				in (
					if (b != tgtb) d.isContradictory := true
				)
			),
			if not(d.isContradictory) (
				setStatus(d, i, b), 
				if (not(b) & not(knownTargetStatus(d, 3 - i))) (
					setTargetStatus(d, 3 - i, true),
					setNeedToAwake(d, 3 - i, true)
				)
			)
		)
	)]


[updateDataStructuresOnConstraint(d: PalmDisjunction, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= d.offset) (
 		// [0] updateDataStructuresOnConstraint 1,
		updateDataStructuresOnConstraint(d.const1, idx, way, newValue, oldValue),
		if (not(knownStatus(d,1)) & not(knownTargetStatus(d, 1))) checkStatusAndReport(d,1)
	)
	else (
 		// [0] updateDataStructuresOnConstraint 2,
		updateDataStructuresOnConstraint(d.const2, idx - d.offset, way, newValue, oldValue),
		if (not(knownStatus(d, 2)) & not(knownTargetStatus(d, 2))) checkStatusAndReport(d,2)
	)]


[updateDataStructuresOnRestoreConstraint(d: PalmDisjunction, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= d.offset) (
		updateDataStructuresOnRestoreConstraint(d.const1, idx, way, newValue, oldValue),
		if knownStatus(d, 1) (
			when b := askIfTrue(d.const1) in (
				if (b != getStatus(d, 1)) (takeIntoAccountStatusChange(d, 1))
			)
			else (takeIntoAccountStatusChange(d, 1))
		)
	)
	else (
		updateDataStructuresOnRestoreConstraint(d.const2, idx - d.offset, way, newValue, oldValue),
		if knownStatus(d, 1) (
			when b := askIfTrue(d.const1) in (
				if (b != getStatus(d, 1)) (takeIntoAccountStatusChange(d, 1))
			)
			else (takeIntoAccountStatusChange(d, 1))
		)
	)]


[checkConstraintState(d: PalmDisjunction) : boolean
 -> if (d.isContradictory) (
		d.isContradictory := false,
		let expl := Explanation()
		in (
			self_explain(d, expl),
			for ct in whyIsFalse(d.const1) addConstraint(expl,ct),
			for ct in whyIsFalse(d.const2) addConstraint(expl,ct),
			raisePalmFakeContradiction(getProblem(d).propagationEngine, expl)
		),
		false
	)
	else if (needToAwake(d,1) | needToAwake(d,2)) (
		if needToAwake(d,1) (
			setNeedToAwake(d, 1, false),
			let expl := Explanation()
			in (
				self_explain(d, expl),
				for ct in whyIsFalse(d.const2) addConstraint(expl,ct),
				// see comment (a) above
				setIndirect(d.const1, expl), setIndirectDependance(d.const1, expl),
				doAwake(d.const1)
			)
		),
		if needToAwake(d,2) (
			setNeedToAwake(d, 2, false),
			let expl := Explanation()
			in (
				self_explain(d, expl),
				for ct in whyIsFalse(d.const1) addConstraint(expl,ct),
				// see comment (a) above
				setIndirect(d.const2, expl), setIndirectDependance(d.const2, expl),
				doAwake(d.const2)
			)
		),
		false
	)
	else true]


[awakeOnInf(d:PalmDisjunction, i:integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d,1)) (
				if knownTargetStatus(d,1) (
					assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnInf(d.const1,i)			  // (no storage of opposite(c.const1))
				)
			)
		)
		else if not(knownStatus(d,2)) (
				if knownTargetStatus(d,2) (
					assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnInf(d.const2,i - d.offset)   // (no storage of opposite(c.const1))
				)  
		)
	)]

[awakeOnRestoreInf(d: PalmDisjunction, i: integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d, 1)) (
				if knownTargetStatus(d, 1) (
					assert(getTargetStatus(d, 1) = true),
					awakeOnRestoreInf(d.const1, i)
				)
			)
		)
		else (
			if not(knownStatus(d, 2)) (
				if knownTargetStatus(d, 2) (
					assert(getTargetStatus(d,2) = true),
					awakeOnRestoreInf(d.const2, i)
				)
			)
		)
	)]



[awakeOnSup(d:PalmDisjunction, i:integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d,1)) (
				if knownTargetStatus(d,1) (
					assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnSup(d.const1,i)			  // (no storage of opposite(c.const1))
				)
			)
		)
		else if not(knownStatus(d,2)) (
				if knownTargetStatus(d,2) (
					assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnSup(d.const2,i - d.offset)   // (no storage of opposite(c.const1))
				)  
		)
	)]

[awakeOnRestoreSup(d: PalmDisjunction, i: integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d, 1)) (
				if knownTargetStatus(d, 1) (
					assert(getTargetStatus(d, 1) = true),
					awakeOnRestoreSup(d.const1, i)
				)
			)
		)
		else (
			if not(knownStatus(d, 2)) (
				if knownTargetStatus(d, 2) (
					assert(getTargetStatus(d,2) = true),
					awakeOnRestoreSup(d.const2, i)
				)
			)
		)
	)]

[awakeOnRem(d:PalmDisjunction, i:integer, v: integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d,1)) (
				if knownTargetStatus(d,1) (
					assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnRem(d.const1,i, v)			  // (no storage of opposite(c.const1))
				)
			)
		)
		else if not(knownStatus(d,2)) (
				if knownTargetStatus(d,2) (
					assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
					doAwakeOnRem(d.const2,i - d.offset, v)   // (no storage of opposite(c.const1))
				)  
		)
	)]


[awakeOnRestoreVal(d: PalmDisjunction, i: integer, v: integer) : void
 -> if checkConstraintState(d) (
		if (i <= d.offset) (
			if not(knownStatus(d, 1)) (
				if knownTargetStatus(d, 1) (
					assert(getTargetStatus(d, 1) = true),
					awakeOnRestoreVal(d.const1, i, v)
				)
			)
		)
		else (
			if not(knownStatus(d, 2)) (
				if knownTargetStatus(d, 2) (
					assert(getTargetStatus(d,2) = true),
					awakeOnRestoreVal(d.const2, i, v)
				)
			)
		)
	)]


[askIfEntailed(d:PalmDisjunction) : (boolean U {unknown})
 -> let leftOK := (if knownStatus(d,1) getStatus(d,1)
                   else (when b := askIfTrue(d.const1) in
                             (setStatus(d,1,b), b)
                         else unknown)),                                                   
        rightOK := (if knownStatus(d,2) getStatus(d,2)
                    else (when b := askIfTrue(d.const2) in
                              (setStatus(d,2,b), b)
                          else unknown)) in
     (if (leftOK = true) true
      else if (rightOK = true) true
      else if (leftOK = false & rightOK = false) false
      else unknown)]



// Such a check is used in  a local optimization mode: therefore the status1/2
// invariants may well not be up to date, therefore we do not trust them and
// re-compute whether the disjunction is feasible or not.
[testIfSatisfied(d:PalmDisjunction) : boolean
 -> (testIfTrue(d.const1) | testIfTrue(d.const2))]   // v0.26 wrong interface name (testIfSatisfied)


[propagate(d:PalmDisjunction) : void
 -> checkStatusAndReport(d,1), checkStatusAndReport(d,2),
	checkConstraintState(d)]

[awake(d:PalmDisjunction) : void
 -> // [0] awake(d:PalmDisjunction) called,
 	checkStatusAndReport(d,1), checkStatusAndReport(d,2),
	checkConstraintState(d),
	// [0] awake(d:PalmDisjunction) done,
	nil
]


[whyIsTrue(d: PalmDisjunction) : set[AbstractConstraint] 
 -> if askIfTrue(d.const1) 
		whyIsTrue(d.const1)
    else if askIfTrue(d.const2) 
		whyIsTrue(d.const2)
 	else (
		error("PaLM: p-bool.cl - odd state for whyIsTrue for PalmDisjunction"),
		set<AbstractConstraint>() // nil
	)]

[whyIsFalse(d: PalmDisjunction) : set[AbstractConstraint]
 -> assert(askIfTrue(d.const1) = false),
	assert(askIfTrue(d.const2) = false),
	let e := Explanation() 
	in (
		for ct in whyIsFalse(d.const1) addConstraint(e,ct),
		for ct in whyIsFalse(d.const2) addConstraint(e,ct),
		set!(e)
	)]


[awakeOnInst(c: PalmDisjunction, idx: integer) 
 -> error("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmDisjunction) : boolean -> checkPalm(ct.const1) &  checkPalm(ct.const2)]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmDisjunction))




// *************************************************
// * Part 2 : IFTHEN : Lazy Guards                 *
// *************************************************

// -------- Lazy guards (if (c1,c2))  -----------------------------------
PalmGuard <: choco/Guard( // note: only status1 is used
	needToAwakeC2: boolean = false	 // note: therefore only nTAC2 is used !
)        

[initHook(c: PalmGuard): void
 -> c.hook := PalmInfoConstraint(),
	addControl(c.const2, c, 2)]

[takeIntoAccountStatusChange(g: PalmGuard, idx: integer) : void
 -> ; // [0] undoing choices ~S ~S ~S // g.const1.v1, g.const2.v2, g.const2.v1, 
	removeIndirectDependance(g.const2), setDirect(g.const2),
	setNeedToAwake(g, 2, false), g.statusBitVector := 0]


[self_print(g:PalmGuard) -> printf("e-IF (~S) e-THEN (~S)", g.const1, g.const2)]

[needToAwake(g: PalmGuard, i: integer) : boolean
 => assert(i = 2), g.needToAwakeC2]
[setNeedToAwake(g: PalmGuard, i: integer, val: boolean) : void 
 => assert(i = 2), g.needToAwakeC2 := val]

[checkStatusAndReport(g: PalmGuard, i: integer) : void
 -> assert(i = 1), 
	assert(not(knownTargetStatus(g, 1))),
;	// [0] checking status ~S ~S ~S // g.const1.v1, g.const2.v2, g.const2.v1, 
	if not(knownStatus(g,1)) (
		when b := askIfTrue(g.const1) 
		in (
;			// [0] status of ~S ~S // g.const1, b, 
			setStatus(g, 1, b), 
;			// [0] targetStatus of ~S ~S // g.const2, knownTargetStatus(g, 2), 
			if (b & not(knownTargetStatus(g, 2))) (
				setTargetStatus(g, 2, true),
				setNeedToAwake(g, 2, true)
			)
		)
	) ]


[updateDataStructuresOnConstraint(g: PalmGuard, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= g.offset) (
 		// [0] updateDataStructuresOnConstraint 3,
		updateDataStructuresOnConstraint(g.const1, idx, way, newValue, oldValue),
		checkStatusAndReport(g,1)
	)
	else (
 		// [0] updateDataStructuresOnConstraint 4,
		updateDataStructuresOnConstraint(g.const2, idx - g.offset, way, newValue, oldValue)
	)]


[updateDataStructuresOnRestoreConstraint(g: PalmGuard, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= g.offset) (
		updateDataStructuresOnRestoreConstraint(g.const1, idx, way, newValue, oldValue),
		if knownStatus(g, 1) (
			when b := askIfTrue(g.const1) in (
				if (b != getStatus(g, 1)) (takeIntoAccountStatusChange(g, 1))
			)
			else (takeIntoAccountStatusChange(g, 1))
		)
	)
	else (
		updateDataStructuresOnRestoreConstraint(g.const2, idx - g.offset, way, newValue, oldValue),
		if knownStatus(g, 2) (
			when b := askIfTrue(g.const2) in (
				if (b != getStatus(g, 2)) (takeIntoAccountStatusChange(g, 2))
			)
			else (takeIntoAccountStatusChange(g, 2))
		)
	)]

[checkConstraintState(g: PalmGuard) : boolean
 ->	if needToAwake(g,2) (
		setNeedToAwake(g, 2, false),
		let expl := Explanation()
		in (
			self_explain(g, expl),
			for ct in whyIsTrue(g.const1) addConstraint(expl,ct),
			// see comment (a) above
			setIndirect(g.const2, expl), setIndirectDependance(g.const2, expl),
			doAwake(g.const2)
		), 
		false
	)
	else true]

[awakeOnInf(g:PalmGuard, i:integer) : void
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil
		else if knownTargetStatus(g,2) (
			assert(getTargetStatus(g,2) = true),
            doAwakeOnInf(g.const2, i - g.offset)
		)
	)]

[awakeOnRestoreInf(g: PalmGuard, i: integer) : void 
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil 
		else if knownTargetStatus(g,2) (
				assert(getTargetStatus(g,2) = true),
				awakeOnRestoreInf(g.const2, i - g.offset)
		)
	)]

[awakeOnSup(g:PalmGuard, i:integer) : void
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil
		else if knownTargetStatus(g,2) (
				assert(getTargetStatus(g,2) = true),
				doAwakeOnSup(g.const2, i - g.offset)
		)
	)]

[awakeOnRestoreSup(g: PalmGuard, i: integer) : void 
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil 
		else if knownTargetStatus(g,2) (
				assert(getTargetStatus(g,2) = true),
				awakeOnRestoreSup(g.const2, i - g.offset)
		)
	)]

[awakeOnRem(g:PalmGuard, i:integer, v:integer) : void
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil
		else if knownTargetStatus(g,2) (
			    assert(getTargetStatus(g,2) = true),
				doAwakeOnRem(g.const2, i - g.offset,v)
		)
	)]

[awakeOnRestoreVal(g: PalmGuard, i: integer, v: integer) : void 
 -> if checkConstraintState(g) (
		if (i <= g.offset) nil 
		else if knownTargetStatus(g,2) (
				assert(getTargetStatus(g,2) = true),
				awakeOnRestoreVal(g.const2, i - g.offset, v)
		)
	)]


[propagate(g:PalmGuard) : void
 -> checkStatusAndReport(g,1),
	checkConstraintState(g)]

[awake(g:PalmGuard) : void
 -> // [0] awake(g:PalmGuard) called,
 	checkStatusAndReport(g,1),
	checkConstraintState(g),
	// [0] awake(g:PalmGuard) done,
	nil
]


[askIfEntailed(g:PalmGuard) : (boolean U {unknown})
 -> if knownStatus(g,1)
       let b := getStatus(g,1) in
          (if b 
             (assert(getTargetStatus(g,2) = true),
              if knownStatus(g,2) getStatus(g,2)
              else (when b2 := askIfTrue(g.const2) in 
                       (setStatus(g,2,b2), b2)
                    else unknown))
           else true)
    else (assert(not(knownTargetStatus(g,2))),              
          if knownStatus(g,2) 
            (if getStatus(g,2) true else unknown)
          else (when b2 := askIfTrue(g.const2) in
                  (setStatus(g,2,b2), 
                   if b2 true else unknown)) )]

[testIfSatisfied(g:PalmGuard) : boolean
 -> (not(testIfTrue(g.const1)) | testIfTrue(g.const2))]  // v0.26 wrong interface name (testIfSatisfied)



[awakeOnInst(c: PalmGuard, idx: integer) 
 -> error("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmGuard) : boolean -> checkPalm(ct.const1) &  checkPalm(ct.const2)]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmGuard))


// *************************************************
// * Part 3 : IFF                                  *
// *************************************************

// -------- Equivalence (c1 if and only if c2)  -----------------------
PalmEquiv <: choco/Equiv(
	isContradictory: boolean = false, 
	needToAwakeC1: boolean = false, needToAwakeNegC1: boolean = false,
	needToAwakeC2: boolean = false, needToAwakeNegC2: boolean = false
)

[initHook(c: PalmEquiv): void
 -> c.hook := PalmInfoConstraint(),
	addControl(c.const1, c, 1), 
	addControl(c.const2, c, 2),
	addControl(c.oppositeConst1, c, 3),
	addControl(c.oppositeConst2, c, 4)]

[takeIntoAccountStatusChange(c: PalmEquiv, idx: integer) : void
 -> c.isContradictory := false,
	removeIndirectDependance(c.const1), setDirect(c.const1),
	removeIndirectDependance(c.oppositeConst1), setDirect(c.oppositeConst1),
	removeIndirectDependance(c.const2), setDirect(c.const2),
	removeIndirectDependance(c.oppositeConst2), setDirect(c.oppositeConst2),
	setNeedToAwake(c, 1, false), setNeedToAwake(c, 2, false),
	setNeedToAwake(c, 3, false), setNeedToAwake(c, 4, false),
	c.statusBitVector := 0]

[self_print(c:PalmEquiv) -> printf("(~S) e-IFF (~S)", c.const1, c.const2)]
[needToAwake(g: PalmEquiv, i: integer) : boolean
 => case i (
		{1} g.needToAwakeC1,
		{2} g.needToAwakeC2,
		{3} g.needToAwakeNegC1,
		{4} g.needToAwakeNegC2
	)]

[setNeedToAwake(g: PalmEquiv, i: integer, val: boolean) : void
 => case i (
		{1} g.needToAwakeC1 := val,
		{2} g.needToAwakeC2 := val,
		{3} g.needToAwakeNegC1 := val,
		{4} g.needToAwakeNegC2 := val
	)]

[checkStatusAndReport(c: PalmEquiv, i: integer) : void
 -> if not(knownStatus(c,i)) (
		let ci := ((if (i = 1) c.const1 else c.const2)),
           j := 3 - i,
           cj := ((if (j = 1) c.const1 else c.const2)),
           oppcj := ((if (j = 1) c.oppositeConst1 else c.oppositeConst2)) 
		in (
			assert(not(knownTargetStatus(c,j))),
			when b := askIfTrue(ci) 
			in (
				setStatus(c,i,b),
				setTargetStatus(c,j,b),
;				// [0] setting status for ~S (~S) in ~S // i, b, c, 
				if knownStatus(c,j) (
					if (b != getStatus(c,j)) c.isContradictory := true
				)
				else if (b = true) setNeedToAwake(c, j, true)
				else if (b = false) setNeedToAwake(c, j + 2, true)  
			)
		)
	)]

[updateDataStructuresOnConstraint(c: PalmEquiv, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= c.offset) (
 		// [0] updateDataStructuresOnConstraint 5,
		updateDataStructuresOnConstraint(c.const1, idx, way, newValue, oldValue),
 		// [0] updateDataStructuresOnConstraint 6,
		updateDataStructuresOnConstraint(c.oppositeConst1, c.indicesInOppConst1[idx], way, newValue, oldValue),
		if (not(knownStatus(c,1)) & not(knownTargetStatus(c, 1))) checkStatusAndReport(c,1)
	)
	else (
 		// [0] updateDataStructuresOnConstraint 7,
		updateDataStructuresOnConstraint(c.const2, idx - c.offset, way, newValue, oldValue),
 		// [0] updateDataStructuresOnConstraint 8,
		updateDataStructuresOnConstraint(c.oppositeConst2, c.indicesInOppConst2[idx - c.offset], way, newValue, oldValue),
		if (not(knownStatus(c, 2)) & not(knownTargetStatus(c, 2))) checkStatusAndReport(c,2)
	)]


[updateDataStructuresOnRestoreConstraint(c: PalmEquiv, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= c.offset) (
		updateDataStructuresOnRestoreConstraint(c.const1, idx, way, newValue, oldValue),
		updateDataStructuresOnRestoreConstraint(c.oppositeConst1, c.indicesInOppConst1[idx], way, newValue, oldValue),
		if knownStatus(c, 1) (
			when b := askIfTrue(c.const1) in (
				if (b != getStatus(c, 1)) (takeIntoAccountStatusChange(c, 1))
			)
			else (takeIntoAccountStatusChange(c, 1))
		)
	)
	else (
		updateDataStructuresOnRestoreConstraint(c.const2, idx - c.offset, way, newValue, oldValue),
		updateDataStructuresOnRestoreConstraint(c.oppositeConst2, c.indicesInOppConst2[idx - c.offset], way, newValue, oldValue),
		if knownStatus(c, 2) (
			when b := askIfTrue(c.const2) in (
				if (b != getStatus(c, 2)) (takeIntoAccountStatusChange(c, 2))
			)
			else (takeIntoAccountStatusChange(c, 2))
		)
	)]

[checkConstraintState(c: PalmEquiv) : boolean
 -> if (c.isContradictory) (
		c.isContradictory := false,
		let expl := Explanation()
		in (
			self_explain(c, expl),
			if (knownStatus(c, 1) & getStatus(c, 1)) (
				assert(askIfTrue(c.const1) = true),
				for ct in whyIsTrue(c.const1) addConstraint(expl,ct),
				for ct in whyIsFalse(c.const2) addConstraint(expl,ct)
			)
			else (
				assert(askIfTrue(c.const2) = true),
				for ct in whyIsTrue(c.const2) addConstraint(expl,ct),
				for ct in whyIsFalse(c.const1) addConstraint(expl,ct)
			),
			raisePalmFakeContradiction(getProblem(c).propagationEngine, expl)
		),
		false
	)
	else if (needToAwake(c,1) | needToAwake(c,2) | needToAwake(c, 3) | needToAwake(c, 4)) (
		for i in (1 .. 4) (
			if needToAwake(c, i) (
				setNeedToAwake(c, i, false),
				let expl := Explanation(),
					ci := (case i ({1} c.const1, {2} c.const2, {3} c.oppositeConst1, {4} c.oppositeConst2)),
					cj := (case i ({1} c.const2, {2} c.const1, {3} c.const2, {4} c.const1)),
					j := (case i  ({1} 2, {2} 1, {3} 4, {4} 3))
				in (
					self_explain(c, expl),
					if (i <= 2) (
						assert(askIfTrue(cj) = true),
						for ct in whyIsTrue(cj) addConstraint(expl,ct)
					)
					else (
						assert(askIfTrue(cj) = false),
						for ct in whyIsFalse(cj) addConstraint(expl,ct)
					),
					
					
					// see comment (a) above
					setIndirect(ci, expl), setIndirectDependance(ci, expl),
					doAwake(ci)
				)
			)
		),
		false
	)
	else true]
         
		 
		    
[awakeOnInf(c:PalmEquiv, i:integer) : void
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
					if getTargetStatus(c, 1) assert(indirect?(c.const1))
					else assert(indirect?(c.oppositeConst1)), 
					if getTargetStatus(c,1) doAwakeOnInf(c.const1, i)
					else doAwakeOnInf(c.oppositeConst1, c.indicesInOppConst1[i])
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
					if getTargetStatus(c, 2) assert(indirect?(c.const2))
					else assert(indirect?(c.oppositeConst2)), 

					if getTargetStatus(c,2) doAwakeOnInf(c.const2, i - c.offset)
					else doAwakeOnInf(c.oppositeConst2, c.indicesInOppConst2[i - c.offset])
				)
			)
		)
	)]


[awakeOnRestoreInf(c:PalmEquiv, i:integer) : void
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
						if getTargetStatus(c, 1) assert(indirect?(c.const1))
						else assert(indirect?(c.oppositeConst1)), 

					if getTargetStatus(c,1) awakeOnRestoreInf(c.const1, i)
					else awakeOnRestoreInf(c.oppositeConst1, c.indicesInOppConst1[i])
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
						if getTargetStatus(c, 2) assert(indirect?(c.const2))
						else assert(indirect?(c.oppositeConst2)), 

					if getTargetStatus(c,2) awakeOnRestoreInf(c.const2, i - c.offset)
					else awakeOnRestoreInf(c.oppositeConst2, c.indicesInOppConst2[i - c.offset])
				)
			)
		)
	)]

[awakeOnSup(c:PalmEquiv, i:integer) : void
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
					if getTargetStatus(c, 1) assert(indirect?(c.const1))
					else assert(indirect?(c.oppositeConst1)), 

					if getTargetStatus(c,1) doAwakeOnSup(c.const1, i)
					else doAwakeOnSup(c.oppositeConst1, c.indicesInOppConst1[i])
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
					if getTargetStatus(c, 2) assert(indirect?(c.const2))
					else assert(indirect?(c.oppositeConst2)), 

					if getTargetStatus(c,2) doAwakeOnSup(c.const2, i - c.offset)
					else doAwakeOnSup(c.oppositeConst2, c.indicesInOppConst2[i - c.offset])
				)
			)
		)
	)]

[awakeOnRestoreSup(c:PalmEquiv, i:integer) : void 
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
						if getTargetStatus(c, 1) assert(indirect?(c.const1))
						else assert(indirect?(c.oppositeConst1)), 

					if getTargetStatus(c,1) awakeOnRestoreSup(c.const1, i)
					else awakeOnRestoreSup(c.oppositeConst1, c.indicesInOppConst1[i])
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
						if getTargetStatus(c, 2) assert(indirect?(c.const2))
						else assert(indirect?(c.oppositeConst2)), 

					if getTargetStatus(c,2) awakeOnRestoreSup(c.const2, i - c.offset)
					else awakeOnRestoreSup(c.oppositeConst2, c.indicesInOppConst2[i - c.offset])
				)
			)
		)
	)]

[awakeOnRem(c:PalmEquiv, i:integer, v: integer) : void
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
					if getTargetStatus(c,1) doAwakeOnRem(c.const1, i, v)
					else doAwakeOnRem(c.oppositeConst1, c.indicesInOppConst1[i], v)
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
					if getTargetStatus(c,2) doAwakeOnRem(c.const2, i - c.offset, v)
					else doAwakeOnRem(c.oppositeConst2, c.indicesInOppConst2[i - c.offset], v)
				)
			)
		)
	)]

[awakeOnRestoreVal(c:PalmEquiv, i:integer, v: integer) : void
 -> if checkConstraintState(c) (
		if (i <= c.offset) (
			if not(knownStatus(c,1)) (
				if knownTargetStatus(c,1) (
					if getTargetStatus(c,1) awakeOnRestoreVal(c.const1, i, v)
					else awakeOnRestoreVal(c.oppositeConst1, c.indicesInOppConst1[i], v)
				)
			)
		)
		else ( 
			if not(knownStatus(c,2)) (
				if knownTargetStatus(c,2) (
					if getTargetStatus(c,2) awakeOnRestoreVal(c.const2, i - c.offset, v)
					else awakeOnRestoreVal(c.oppositeConst2, c.indicesInOppConst2[i - c.offset], v) 
				)
			)
		)
	)]


[propagate(c:PalmEquiv) : void
 -> checkStatusAndReport(c, 1), checkStatusAndReport(c, 2), 
	checkConstraintState(c)]

[awake(c:PalmEquiv) : void
 -> // [0] awake(c:PalmEquiv) called,
 	checkStatusAndReport(c, 1), checkStatusAndReport(c, 2), 
	checkConstraintState(c),
	// [0] awake(c:PalmEquiv) done,
	nil
]

    
[askIfEntailed(c:PalmEquiv) : (boolean U {unknown})
 -> let leftOK := (if knownStatus(c,1) getStatus(c,1)
                    else (when b := askIfTrue(c.const1) in 
                              (setStatus(c,1,b), b)
                          else unknown)),
        rightOK := (if knownStatus(c,2) getStatus(c,2)
                    else (when b := askIfTrue(c.const2) in 
                              (setStatus(c,2,b), b)
                          else unknown)) in
     (if (leftOK = true) rightOK
      else if (rightOK = true) leftOK
      else if (leftOK = false & rightOK = false) true
      else unknown)]

[testIfSatisfied(c:PalmEquiv) : boolean
 -> (testIfTrue(c.const1) = testIfTrue(c.const2))]  // v0.26 wrong interface name (testIfSatisfied)


[awakeOnInst(c:PalmEquiv, i:integer) : void
 => error("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmEquiv) : boolean -> checkPalm(ct.const1) &  checkPalm(ct.const2)]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmEquiv))


// *************************************************
// * Part 4 : AND                                  *
// *************************************************

// -------- Conjunctions (only used in subterms of Boolean formulae) --
// note v1.02: for conjunctions, targetStatus slots are useless -> we only use status fields

PalmConjunction <: choco/Conjunction()

[initHook(c: PalmConjunction): void
 -> c.hook := PalmInfoConstraint(),
	addControl(c.const1, c, 1), 
	addControl(c.const2, c, 2)]

[takeIntoAccountStatusChange(c: PalmConjunction, idx: integer) : void
 -> removeIndirectDependance(c.const1), setDirect(c.const1),
	removeIndirectDependance(c.const2), setDirect(c.const2),
	c.statusBitVector := 0]

[self_print(c:PalmConjunction) -> printf("(~S) e-AND (~S)", c.const1, c.const2)]

[updateDataStructuresOnConstraint(c: PalmConjunction, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= c.offset) (
 		// [0] updateDataStructuresOnConstraint 9,
		updateDataStructuresOnConstraint(c.const1, idx, way, newValue, oldValue)
	)
	else (
 		// [0] updateDataStructuresOnConstraint 10,
		updateDataStructuresOnConstraint(c.const2, idx - c.offset, way, newValue, oldValue)
	)]


[updateDataStructuresOnRestoreConstraint(c: PalmConjunction, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (idx <= c.offset) (
		updateDataStructuresOnRestoreConstraint(c.const1, idx, way, newValue, oldValue),
		if knownStatus(c, 1) (
			when b := askIfTrue(c.const1) in (
				if (b != getStatus(c, 1)) (takeIntoAccountStatusChange(c, 1))
			)
			else (takeIntoAccountStatusChange(c, 1))
		)
	)
	else (
		updateDataStructuresOnRestoreConstraint(c.const2, idx - c.offset, way, newValue, oldValue),
		if knownStatus(c, 2) (
			when b := askIfTrue(c.const2) in (
				if (b != getStatus(c, 2)) (takeIntoAccountStatusChange(c, 2))
			)
			else (takeIntoAccountStatusChange(c, 2))
		)

	)]


[awakeOnInf(c:PalmConjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnInf(c.const1,i)
    else doAwakeOnInf(c.const2,i - c.offset)]

[awakeOnRestoreInf(c: PalmConjunction, i: integer) : void
 -> if (i <= c.offset) awakeOnRestoreInf(c.const1, i)
	else awakeOnRestoreInf(c.const2, i - c.offset)]


[awakeOnSup(c:PalmConjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnSup(c.const1,i)
    else doAwakeOnSup(c.const2,i - c.offset)]

[awakeOnRestoreSup(c: PalmConjunction, i: integer) : void
 -> if (i <= c.offset) awakeOnRestoreInf(c.const1, i)
	else awakeOnRestoreSup(c.const2, i - c.offset)]


[awakeOnRem(c:PalmConjunction, i:integer, v:integer) : void
 -> if (i <= c.offset) doAwakeOnRem(c.const1,i,v)
    else doAwakeOnRem(c.const2,i - c.offset,v)]

[awakeOnRestoreVal(c: PalmConjunction, i: integer, v: integer) : void
 -> if (i <= c.offset) awakeOnRestoreVal(c.const1, i, v)
	else awakeOnRestoreVal(c.const2, i - c.offset, v)]

[propagate(c:PalmConjunction) : void
 -> let e := Explanation() in (
		self_explain(c, e),
		// see comment (a) above
		setIndirect(c.const1, clone(e)), setIndirectDependance(c.const1, e),
		setIndirect(c.const2, clone(e)), setIndirectDependance(c.const2, e)
	),
	doPropagate(c.const1),
    doPropagate(c.const2)]


[awake(c:PalmConjunction) : void
 -> // [0] awake(c:PalmConjunction) called,
 	let e := Explanation() in (
		self_explain(c, e),
		// see comment (a) above
		setIndirect(c.const1, clone(e)), setIndirectDependance(c.const1, e),
		setIndirect(c.const2, clone(e)), setIndirectDependance(c.const2, e)
	),
	doAwake(c.const1),
    doAwake(c.const2),
    // [0] awake(c:PalmConjunction) done,
	nil
]


[askIfEntailed(c:PalmConjunction) : (boolean U {unknown})
 -> let leftOK := (if knownStatus(c,1) getStatus(c,1)
                    else (when b := askIfTrue(c.const1) in 
                              (setStatus(c,1,b), b)
                          else unknown)),
        rightOK := (if knownStatus(c,2) getStatus(c,2)
                    else (when b := askIfTrue(c.const2) in 
                              (setStatus(c,2,b), b)
                          else unknown)) in
     (if (leftOK = true & rightOK = true) true
      else if (leftOK = false | rightOK = false) false
      else unknown)]


[whyIsTrue(c: PalmConjunction) : set[AbstractConstraint]
 -> let e := Explanation() 
	in (
		assert((knownStatus(c, 1) & getStatus(c, 1)) | (askIfTrue(c.const1) = true)),
		assert((knownStatus(c, 2) & getStatus(c, 2)) | (askIfTrue(c.const2) = true)),
		for ct in whyIsTrue(c.const1) addConstraint(e,ct),
		for ct in whyIsTrue(c.const2) addConstraint(e,ct), 
		set!(e)
	)]


[whyIsFalse(c: PalmConjunction) : set[AbstractConstraint]
 -> let leftOK? := (if knownStatus(c, 1) getStatus(c, 1)
                    else askIfTrue(c.const1)),
        rightOK? := (if knownStatus(c, 2) getStatus(c, 2)
                    else askIfTrue(c.const2)),
		e := Explanation() 
	in (
		if (leftOK? = false) (for ct in whyIsFalse(c.const1) addConstraint(e,ct))
		else if (rightOK? = false) (for ct in whyIsFalse(c.const2) addConstraint(e,ct)) 
		else error("PaLM: p-bool.cl odd state for whyIsFalse for PalmConjunction"),
		set!(e)
	)]

[testIfSatisfied(c:PalmConjunction) : boolean
 -> (testIfTrue(c.const1) & testIfTrue(c.const2))]  // v0.26 wrong interface name (testIfSatisfied)

// v0.34

[awakeOnInst(c:PalmConjunction, i:integer) : void
 => error("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmConjunction) : boolean -> checkPalm(ct.const1) &  checkPalm(ct.const2)]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmConjunction))


// ********************************************************************
// *   Part 5: Large AND                                              *
// ********************************************************************


// -------- Large Conjunctions (c1 and c2 and c3 ..... and cn) -----------
PalmLargeConjunction <: choco/LargeConjunction()

[initHook(c: PalmLargeConjunction): void
 -> c.hook := PalmInfoConstraint(),
	for i in (1 .. c.nbConst) addControl(c.lconst[i], c, i)]

[takeIntoAccountStatusChange(c: PalmLargeConjunction, idx: integer) : void
 -> for k in (1 .. c.nbConst) (
		removeIndirectDependance(c.lconst[k]), 
		setDirect(c.lconst[k])
	),
	for k in (1 .. length(c.statusBitVectorList)) c.statusBitVectorList[k] := 0]


[self_print(c:PalmLargeConjunction)
 -> printf("(~S)",c.lconst[1]),
    for i in (2 .. c.nbConst)
      printf(" e-AND (~S)",c.lconst[i])]


[updateDataStructuresOnConstraint(c: PalmLargeConjunction, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in (
 		// [0] updateDataStructuresOnConstraint 12,
		updateDataStructuresOnConstraint(c.lconst[idx], reali, way, newValue, oldValue))]


[updateDataStructuresOnRestoreConstraint(c: PalmLargeConjunction, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in (
		updateDataStructuresOnRestoreConstraint(c.lconst[idx], reali, way, newValue, oldValue),
		if knownStatus(c, idx) (
			when b := askIfTrue(c.lconst[idx]) in (
				if (b != getStatus(c, idx)) (takeIntoAccountStatusChange(c, idx))
			)
			else (takeIntoAccountStatusChange(c, idx))
		)
	)]

[awakeOnInf(c:PalmLargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnInf(c.lconst[idx],reali)]

[awakeOnRestoreInf(c: PalmLargeConjunction, i: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         awakeOnRestoreInf(c.lconst[idx],reali)]


[awakeOnSup(c:PalmLargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnSup(c.lconst[idx],reali)]

[awakeOnRestoreSup(c: PalmLargeConjunction, i: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         awakeOnRestoreSup(c.lconst[idx],reali)]

[awakeOnRem(c:PalmLargeConjunction, i:integer, v:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnRem(c.lconst[idx],reali,v)]

[awakeOnRestoreVal(c: PalmLargeConjunction, i: integer, v: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         awakeOnRestoreVal(c.lconst[idx],reali, v)]



[propagate(c:PalmLargeConjunction) : void
 -> let e := Explanation() in (
		self_explain(c, e),
		for ct in c.lconst ( // see comment (a) above
			setIndirect(ct, clone(e)), setIndirectDependance(ct, e)
		)
	),
	for i in (1 .. c.nbConst) (
		assert(indirect?(c.lconst[i])),
		doPropagate(c.lconst[i])
	)]

[awake(c:PalmLargeConjunction) : void
 -> // [0] awake(c:PalmLargeConjunction) called,
 	let e := Explanation() in (
		self_explain(c, e),
		for ct in c.lconst ( // see comment (a) above
			setIndirect(ct, clone(e)), setIndirectDependance(ct, e)
		)
	),
	for i in (1 .. c.nbConst) (
		assert(indirect?(c.lconst[i])),
		doAwake(c.lconst[i])
	),
	// [0] awake(c:PalmLargeConjunction) done,
	nil
]


[askIfEntailed(c:PalmLargeConjunction) : (boolean U {unknown})
 -> let allTrue := true, oneFalse := false, i := 1, n := c.nbConst in
        (while ((allTrue | not(oneFalse)) & i <= n)
           let ithStatus := (if knownStatus(c,i) (getStatus(c,i), assert(askIfTrue(c.lconst[i]) = getStatus(c, i)))
                             else when bi := askIfTrue(c.lconst[i]) in
                                (setStatus(c,i,bi), 
                                 if bi c.nbTrueStatus :+ 1
                                 else c.nbFalseStatus :+ 1,
                                 bi)) in
             (if (ithStatus != true) allTrue := false,
              if (ithStatus = false) oneFalse := true,
              i :+ 1),
         if allTrue true
         else if oneFalse false
         else unknown) ]

[testIfSatisfied(c:PalmLargeConjunction) : boolean
 -> forall(subc in c.lconst | testIfTrue(subc))]  // v0.26 wrong interface name (testIfSatisfied)

[whyIsTrue(c: PalmLargeConjunction) : set[AbstractConstraint]
 -> assert(forall(k in (1 .. c.nbConst) | askIfTrue(c.lconst[k]) = true)),
	let e := Explanation() 
	in (
		for i in (1 .. c.nbConst)  for ct in whyIsTrue(c.lconst[i]) addConstraint(e,ct),
		set!(e)
	)]



[whyIsFalse(c: PalmLargeConjunction) : set[AbstractConstraint]
 -> when i := some(idx in (1 .. c.nbConst) | ((knownStatus(c, idx) & not(getStatus(c, idx))) | (askIfTrue(c.lconst[idx]) = false)))
	in (
		let e := Explanation()
		in (
			for ct in whyIsFalse(c.lconst[i]) addConstraint(e,ct),
			set!(e)
		)		
	) 
	else (
		error("PaLM: p-bool.cl - whyIsFalse(c: ~S) should be called with a non true constraint !!!", c.isa),
		set<AbstractConstraint>() // nil
	)]


[awakeOnInst(c:PalmLargeConjunction, i:integer) : void
 -> error("PaLM: p-bool.cl - awakeOnInst should net be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmLargeConjunction) : boolean -> forall(i in (1 .. ct.nbConst) | checkPalm(ct.lconst[i]))]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmLargeConjunction))


// ********************************************************************
// *   Part 6: Large OR                                               *
// ********************************************************************

// -------- Large Disjunctions (c1 or c2 or c3 ..... or cn) -----------
PalmLargeDisjunction <: choco/LargeDisjunction(
	needToAwake: list<boolean>,
	isContradictory: boolean = false
)

[initHook(c: PalmLargeDisjunction): void
 -> c.hook := PalmInfoConstraint(),
	for i in (1 .. c.nbConst) addControl(c.lconst[i], c, i)]

[takeIntoAccountStatusChange(d: PalmLargeDisjunction, idx: integer) : void
 -> d.isContradictory := false,
	for k in (1 .. d.nbConst) (
		setNeedToAwake(d, k, false), 
		removeIndirectDependance(d.lconst[k]), 
		setDirect(d.lconst[k])
	),
	for k in (1 .. length(d.statusBitVectorList)) d.statusBitVectorList[k] := 0,
	d.nbTrueStatus := 0, d.nbFalseStatus := 0]

[self_print(c:PalmLargeDisjunction)
 -> printf("(~S)",c.lconst[1]),
    for i in (2 .. c.nbConst)
      printf(" e-OR (~S)",c.lconst[i])]

[needToAwake(d: PalmLargeDisjunction, i: integer) : boolean
 => d.needToAwake[i] ]
[setNeedToAwake(d: PalmLargeDisjunction, i: integer, val: boolean) : void
 => d.needToAwake[i] := val ]


[checkStatusAndReport(d:PalmLargeDisjunction,i:integer) : void
 -> if not(knownStatus(d,i)) (
		when b := askIfTrue(d.lconst[i]) 
		in (
			if knownTargetStatus(d, i) (
				let tgtb := getTargetStatus(d, i) 
				in (
					if (b != tgtb) d.isContradictory := true
				)	
			),
			if not(d.isContradictory) (
				setStatus(d, i, b),
				if b d.nbTrueStatus :+ 1
				else (
					d.nbFalseStatus :+ 1,
					if (d.nbFalseStatus = d.nbConst - 1 & d.nbTrueStatus = 0) (
						when j := some(j in (1 .. d.nbConst) | not(knownStatus(d, j)))
						in (
							setTargetStatus(d, j, true),
							setNeedToAwake(d, j, true)
						)
					)
				)
			)
		)
	)]

[updateDataStructuresOnConstraint(c: PalmLargeDisjunction, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i)
	in (
		let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in (
	 		// [0] updateDataStructuresOnConstraint 13,
			updateDataStructuresOnConstraint(c.lconst[idx], reali, way, newValue, oldValue),
			if not(knownTargetStatus(c, idx)) checkStatusAndReport(c, idx)
		)
	)]

[updateDataStructuresOnRestoreConstraint(d: PalmLargeDisjunction, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> when idx := some(idx in (1 .. d.nbConst) | d.loffset[idx] >= i)
	in (
		let reali := (if (idx = 1) i else i - d.loffset[idx - 1]) 
		in (
			updateDataStructuresOnRestoreConstraint(d.lconst[idx], reali, way, newValue, oldValue),
			if knownStatus(d, idx) (
				when b := askIfTrue(d.lconst[idx]) in (
					if (b != getStatus(d, idx)) (takeIntoAccountStatusChange(d, idx))
				)
				else (takeIntoAccountStatusChange(d, idx))
			)
		)
	)]

[checkConstraintState(d: PalmLargeDisjunction) : boolean
 -> if (d.isContradictory) (
		// Une contradiction car tout le monde est faux !!! 
		let e := Explanation() 
		in (
			self_explain(d, e),
			for k in (1 .. d.nbConst) 
				for ct in whyIsFalse(d.lconst[k]) addConstraint(e,ct),
			raisePalmFakeContradiction(getProblem(d).propagationEngine, e)
		),
		false
	) 
	else if exists(k in (1 .. d.nbConst) | needToAwake(d, k)) (
		for k in (1 .. d.nbConst) (
			if needToAwake(d, k) (
				setNeedToAwake(d, k, false),
				let e := Explanation() 
				in (
					self_explain(d, e),
					for id in ((1 .. d.nbConst) but k)
						for ct in whyIsFalse(d.lconst[id]) addConstraint(e,ct),
					// see comment (a) above
					setIndirect(d.lconst[k], e), setIndirectDependance(d.lconst[k], e),
					doAwake(d.lconst[k])
				)
			)
		),
		false
	)
	else true]
		



[awakeOnInf(c:PalmLargeDisjunction, i:integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						doAwakeOnInf(c.lconst[idx], reali) 
					)
				)
			)
		)
	)]

[awakeOnRestoreInf(c: PalmLargeDisjunction, i: integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						awakeOnRestoreInf(c.lconst[idx], reali) 
					)
				)
			)
		)
	)]

[awakeOnSup(c:PalmLargeDisjunction, i:integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						doAwakeOnSup(c.lconst[idx], reali) 
					)
				)
			)
		)
	)]

[awakeOnRestoreSup(c: PalmLargeDisjunction, i: integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						awakeOnRestoreSup(c.lconst[idx], reali) 
					)
				)
			)
		)
	)]

[awakeOnRem(c:PalmLargeDisjunction, i:integer, v: integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						doAwakeOnRem(c.lconst[idx], reali, v) 
					)
				)
			)
		)
	)]

[awakeOnRestoreVal(c: PalmLargeDisjunction, i: integer, v: integer) : void
 -> if checkConstraintState(c) (
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
		in	(
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
			in (
				if knownStatus(c, idx) (
					if knownTargetStatus(c, idx) (
						assert(getTargetStatus(c, idx) = true),
						awakeOnRestoreVal(c.lconst[idx], reali, v) 
					)
				)
			)
		)
	)]

[askIfEntailed(c:PalmLargeDisjunction) : (boolean U {unknown})
 -> let allFalse := true, oneTrue := false, i := 1, n := c.nbConst in
        (while ((allFalse | not(oneTrue)) & i <= n)
           let ithStatus := (if knownStatus(c,i) getStatus(c,i)
                             else askIfTrue(c.lconst[i])) in
             (if (ithStatus != false) allFalse := false,
              if (ithStatus = true) oneTrue := true,
              i :+ 1),
         if allFalse false
         else if oneTrue true
         else unknown) ]

[testIfSatisfied(c:PalmLargeDisjunction) : boolean
 -> exists(subc in c.lconst | testIfTrue(subc))]  // v0.26 wrong interface name (testIfSatisfied)

[propagate(d:PalmLargeDisjunction) : void
 -> for i in (1 .. d.nbConst) checkStatusAndReport(d,i),
	checkConstraintState(d)	 ]


[awake(d:PalmLargeDisjunction) : void
 -> // [0] awake(d:PalmLargeDisjunction) called,
 	for i in (1 .. d.nbConst) checkStatusAndReport(d,i),
	checkConstraintState(d),
	// [0] awake(d:PalmLargeDisjunction) done,
	nil
]


[whyIsTrue(c: PalmLargeDisjunction) : set[AbstractConstraint]
 -> when ct := some(ctx in c.lconst | askIfTrue(ctx) = true) 
	in (
		whyIsTrue(ct)
	)
	else (
		error("PaLM: p-bool.cl - odd state for whyIsTrue for PalmLargeDisjunction"),
		set<AbstractConstraint>() // nil
	)]

[whyIsFalse(c: PalmLargeDisjunction) : set[AbstractConstraint]
 -> assert(forall(ct in c.lconst | (askIfTrue(ct) = false))),
	let e := Explanation() 
	in (
		for ct in c.lconst (for cc in whyIsFalse(ct) addConstraint(e,cc)),
		set!(e)
	)]


[awakeOnInst(c:PalmLargeDisjunction, i:integer) : void
 -> error("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)", c.isa)]

[checkPalm(ct: PalmLargeDisjunction) : boolean -> forall(i in (1 .. ct.nbConst) | checkPalm(ct.lconst[i]))]
// v0.34
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmLargeDisjunction))


// ********************************************************************
// *   Part 7: Cardinality constraint                                 *
// ********************************************************************

PalmCardinality <: choco/Cardinality(
	needToAwake: list<boolean>,
	isContradictory: boolean = false,
	needToAwakeTrue: boolean = false,
	needToAwakeFalse: boolean = false
)

[initHook(c: PalmCardinality): void
 -> c.hook := PalmInfoConstraint(),
	for i in (1 .. c.nbConst) (
		addControl(c.lconst[i], c, i),
		addControl(c.loppositeConst[i], c, i + c.nbConst)
	)]

[needToAwake(c: PalmCardinality, i: integer) : boolean
 => c.needToAwake[i] ]

[setNeedToAwake(c: PalmCardinality, i: integer, val: boolean) : void
 => c.needToAwake[i] := val ]

[self_print(c:PalmCardinality)
 -> printf("e-#~S ~A ~S", c.lconst,
           ( if (constrainOnInfNumber & constrainOnSupNumber) "="
             else if constrainOnInfNumber ">="
             else "<="), getCardVar(c))]

[takeIntoAccountStatusChange(c: PalmCardinality, idx: integer) : void
 ->	for k in (1 .. c.nbConst) (
		setNeedToAwake(c, k, false), 
		removeIndirectDependance(c.lconst[k]), 
		setDirect(c.lconst[k]),
		setNeedToAwake(c, k + c.nbConst, false), 
		removeIndirectDependance(c.loppositeConst[k]), 
		setDirect(c.loppositeConst[k])

	),
	for k in (1 .. length(c.statusBitVectorList)) c.statusBitVectorList[k] := 0,
	c.needToAwakeTrue := true, c.needToAwakeFalse := true, 
	c.nbTrueStatus := 0, c.nbFalseStatus := 0]


// back-propagates the upper bound of the counter variable on the status fo the subconstraints.
[checkOnNbTrue(c:PalmCardinality) : void
 -> let nbVar := getCardVar(c) 
	in (  // v1.02 using the new accessor function
;		// [0] checking true // ,
		c.needToAwakeTrue := true, 
		// updateInf(nbVar,c.nbTrueStatus,0),
		if (c.nbTrueStatus = nbVar.sup & c.constrainOnSupNumber)   // v0.9904
			for j in {j in (1 .. c.nbConst) | not(knownStatus(c,j))} // v1.02 (bitvector access)
				setNeedToAwake(c,c.nbConst + j,true)
	)]

// back-propagates the lower bound of the counter variable on the status fo the subconstraints.
[checkOnNbFalse(c:PalmCardinality) : void
 -> let nbVar := getCardVar(c) 
	in ( // v1.02 using the new accessor function
;	// [0] checking false // ,
		c.needToAwakeFalse := true,
		// updateSup(nbVar, c.nbConst - c.nbFalseStatus,0),
		if (c.nbConst - c.nbFalseStatus = nbVar.inf & c.constrainOnInfNumber)  (// v0.9904 
			for j in {j in (1 .. c.nbConst) | not(knownStatus(c,j))} ( // v1.02 (bitvector access) 
;				// [0] need to awake ~S // j,
				setNeedToAwake(c, j, true)
			)
		)
	)]


[checkStatusAndReport(c: PalmCardinality, i:integer) : void
 -> assert(0 < i & i <= c.nbConst),
    assert(not(knownStatus(c,i))), // v1.02 (bitvector access) 
    when b := askIfTrue(c.lconst[i]) 
	in (
		setStatus(c,i,b), // v1.02 (bitvector access)
        if b (c.nbTrueStatus :+ 1, checkOnNbTrue(c))
        else (c.nbFalseStatus :+ 1, checkOnNbFalse(c)) 
	)]


[explainTrueConstraints(c: PalmCardinality) : Explanation 
 -> let e := Explanation()
	in (
		self_explain(c, e),
		for ict in list{i in (1 .. c.nbConst) | knownStatus(c, i) & getStatus(c, i)}
			for ct in whyIsTrue(c.lconst[ict]) addConstraint(e,ct),
		e
	)]

[explainFalseConstraints(c: PalmCardinality) : Explanation 
 -> let e := Explanation()
	in (
		self_explain(c, e),
		for ict in list{i in (1 .. c.nbConst) | knownStatus(c, i) & not(getStatus(c, i))}
			for ct in whyIsFalse(c.lconst[ict]) addConstraint(e,ct),
		e
	)]

[checkConstraintState(c: PalmCardinality) : boolean
 -> // Première étape : propager sur la variable de cardinalité 
 ; // [0] checking state // ,
	if (c.needToAwakeTrue) (
		updateInf(getCardVar(c), c.nbTrueStatus, 0, explainTrueConstraints(c))
	),
	if (c.needToAwakeFalse) (
		updateSup(getCardVar(c), c.nbConst - c.nbFalseStatus, 0, explainFalseConstraints(c))
	),
	// Deuxième étape : réveillons les contraintes devant être réveillées
	if exists(k in (1 .. 2 * c.nbConst) | needToAwake(c, k)) (
		for k in (1 .. c.nbConst) (
			if needToAwake(c, k) (
				setNeedToAwake(c, k, false),
				let e := Explanation() 
				in (
;					// [0] awaking ~S // c.lconst[k], 
					self_explain(c, e), // vérifier l'explication 
					setIndirect(c.lconst[k], e), setIndirectDependance(c.lconst[k], e),
					doAwake(c.lconst[k])
				)
			),
			if needToAwake(c, c.nbConst + k) (
				setNeedToAwake(c, c.nbConst + k, false),
				let e := Explanation() 
				in (
;					// [0] awaking ~S // c.loppositeconst[k], 
					self_explain(c, e), // vérifier l'explication 
					setIndirect(c.loppositeConst[k], e), setIndirectDependance(c.loppositeConst[k], e),
					doAwake(c.loppositeConst[k])
				)
			)
		),
		false
	)
	else true ]

[updateDataStructuresOnConstraint(c: PalmCardinality, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (i = getNbVars(c)) (
		if (way = INF) checkOnNbFalse(c)
		else if (way = SUP) checkOnNbTrue(c)
		else (
			checkOnNbFalse(c), 
			checkOnNbTrue(c)
		)
	)
	else 
		when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i)
		in (
			let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in (
		 		// [0] updateDataStructuresOnConstraint 14,
				updateDataStructuresOnConstraint(c.lconst[idx], reali, way, newValue, oldValue),
				if (not(knownStatus(c, idx)) & not(knownTargetStatus(c, idx))) checkStatusAndReport(c, idx)
			)
		)]

[updateDataStructuresOnRestoreConstraint(d: PalmCardinality, i: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> if (i = getNbVars(d)) (
		d.needToAwakeFalse := true,
		d.needToAwakeTrue := true
	)
	else 
		when idx := some(idx in (1 .. d.nbConst) | d.loffset[idx] >= i)
		in (
			let reali := (if (idx = 1) i else i - d.loffset[idx - 1]) 
			in (
				updateDataStructuresOnRestoreConstraint(d.lconst[idx], reali, way, newValue, oldValue),
				if knownStatus(d, idx) (
					when b := askIfTrue(d.lconst[idx]) in (
						if (b != getStatus(d, idx)) (takeIntoAccountStatusChange(d, idx))
					)
					else (takeIntoAccountStatusChange(d, idx))
				)
			)
		)]


[awakeOnInf(c:PalmCardinality, i:integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							doAwakeOnInf(c.lconst[idx], reali) 
						)
					)
				)
			)
		)
	)]

[awakeOnRestoreInf(c: PalmCardinality, i: integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							awakeOnRestoreInf(c.lconst[idx], reali) 
						)
					)
				)
			)
		)
	)]

[awakeOnSup(c:PalmCardinality, i:integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							doAwakeOnSup(c.lconst[idx], reali) 
						)
					)
				)
			)
		)
	)]

[awakeOnRestoreSup(c: PalmCardinality, i: integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							awakeOnRestoreSup(c.lconst[idx], reali) 
						)
					)
				)
			)
		)
	)]

[awakeOnRem(c:PalmCardinality, i:integer, v: integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							doAwakeOnRem(c.lconst[idx], reali, v) 
						)
					)
				)
			)
		)
	)]

[awakeOnRestoreVal(c: PalmCardinality, i: integer, v: integer) : void
 -> if checkConstraintState(c) (
		if (i < getNbVars(c)) (
			when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) 
			in	(
				let reali := (if (idx = 1) i else i - c.loffset[idx - 1])
				in (
					if knownStatus(c, idx) (
						if knownTargetStatus(c, idx) (
							assert(getTargetStatus(c, idx) = true),
							awakeOnRestoreVal(c.lconst[idx], reali, v) 
						)
					)
				)
			)
		)
	)]

[checkPalm(ct: PalmCardinality) : boolean -> forall(i in (1 .. ct.nbConst) | checkPalm(ct.lconst[i]))]
// v0.34
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmCardinality))
