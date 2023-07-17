// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 


// ** Summary : Basic constraints and handling mechanisms
// Part 1  : Storing specific information within constraints
// Part 2  : Basic tools for AbstractConstraints                    
// Part 3  : (Re|dis)connection to(from) the constraint network
// Part 4  : Unary constraints (general purpose methods)
// Part 5  : Binary constraints (general purpose methods)
// Part 6  : Large constraints (general purpose methods)
// Part 7  : Basic unary constraint (propagation)
//           PalmGreaterOrEqualxc, PalmLessOrEqualxc, PalmNotEqualxc, PalmEqualxc
// Part 8  : Basic binary constraint (propagation)
//		     PalmNotEqualxyc, PalmEqualxyc, PalmGreaterOrEqualxyc, PalmLessOrEqualxyc 
// Part 9  : Linear combination of variables (PalmLinComb)
// Part 10 : Handling delayers within PaLMchoco

// ********************************************************************
// *   Part 1: Storing specific information within constraints        *
// ********************************************************************

// Information to add to constraints in order to implement
// explanation management

PalmControlConstraint <: choco/Ephemeral(
	constraint: AbstractConstraint,
	index: integer
)

PalmInfoConstraint <: PalmTools(
	timeStamp: integer = 0,
	weight: integer = MAXINT, // associated weight
	everConnected: boolean = false, // has the constraint ever been connected
	indirect: boolean = false,  // indirect constraint ?
	addingExplanation: set<AbstractConstraint>, // for indirect constraints
	dependingConstraints: set<AbstractConstraint>, // depending constraints 
	dependencyNet: set<Explanation>, // aka the alpha set
	searchInfo: any = unknown, // open slot for search use
	ufBox: PalmUserFriendlyBox,
	controllingConstraints: list<PalmControlConstraint>, // the father constraints
	info: any = unknown // open slot for future use 
)

[self_print(pcc: PalmControlConstraint) -> printf("control(~S,~S)", pcc.constraint, pcc.index)]

// ********************************************************************
// *   Part 2: Basic tools for AbstractConstraints                    *
// ********************************************************************


// updating the dependencyNet (constraint addition)
[addDependency(c: AbstractConstraint, p: Explanation): void
 -> c.hook.dependencyNet :add p]

// updating the dependencyNet (constraint removal)
[removeDependency(c: AbstractConstraint, p: Explanation): void
 -> c.hook.dependencyNet :delete p]


// undoing past effects of a constraint
[undo(c: AbstractConstraint): void
 ->	
	for e in c.hook.dependencyNet (
		postUndoRemoval(e,c)
	),
	c.hook.dependencyNet := set<Explanation>()]

// testing indirect constraints 
[indirect?(c: AbstractConstraint): boolean
 => known?(hook,c) & c.hook.indirect ]

[setIndirect(c: AbstractConstraint, e: Explanation) : void
 -> let ch := c.hook in (
		ch.addingExplanation := set!(e),
		ch.indirect := true
	)]

[setDirect(c: AbstractConstraint) : void
 -> let ch := c.hook in (
		ch.indirect := false,
		erase(addingExplanation, ch)
	)]

[setIndirectDependance(c: AbstractConstraint, e: Explanation) : void
 ->	for c' in e (
		c'.hook.dependingConstraints :add c
	)]

[removeIndirectDependance(c: AbstractConstraint) : void 
 -> for c' in c.hook.addingExplanation (
		c'.hook.dependingConstraints :delete c
	)]

[informControllersOfDeactivation(c: AbstractConstraint) : void
 ->	for ctrl in c.hook.controllingConstraints 
		takeIntoAccountStatusChange(ctrl.constraint, ctrl.index)]


// getting the weight of a constraint
[weight(c: AbstractConstraint) : integer
 => c.hook.weight ]

// adding explanation information from a constraint
[self_explain(c: AbstractConstraint, e: Explanation): void
 -> if indirect?(c) mergeConstraints(e, c.hook.addingExplanation)
	else addConstraint(e,c)]

// activating a constraint
[activate(c: AbstractConstraint): void
 -> let hk := c.hook 
	in (
		hk.timeStamp := PALM_TIME_STAMP,
		PALM_TIME_STAMP :+ 1
	)]

// deactivating a constraint 
// BEWARE: deactivating != removing (past effects are not handled)
// deactivating only deals with unplugging from the constraint network
[deactivate(c: AbstractConstraint): void
->  let dc := copy(c.hook.dependingConstraints) in  (
		for c' in dc (
			removeIndirectDependance(c'),
			informControllersOfDeactivation(c'),
			setPassive(c'),
			setDirect(c')
		)
	),
	assert(length(c.hook.dependingConstraints) = 0),
	if (length(c.hook.dependingConstraints) != 0)
		error(" ******* In deactivate(c: AbstractConstraint) (file: p-constraints) c.dependingConstraints should be empty !!! \n Report bug and all possible information to jussien@emn.fr \n")]


[initHook(c: AbstractConstraint): void
 => c.hook := PalmInfoConstraint()]

[addControl(ct: AbstractConstraint, pere: AbstractConstraint, idx: integer) : void
 => ct.hook.controllingConstraints :add PalmControlConstraint( constraint = pere, index = idx)]


// ********************************************************************
// *   Part 3: (Re | dis)connection to the constraint network         *
// ********************************************************************

// need to override choco connectEvent in order to add more information
// <grt> added "IntVar" in property names (VS Set for the future versions)
[connectIntVarEvents(u:PalmIntVar, activeOnInst:boolean, activeOnInf:boolean,
                             activeOnSup:boolean, activeOnRem:boolean) : void
 -> connectEvent(u.updtInfEvt, u.nbConstraints, activeOnInf),
	connectEvent(u.restInfEvt, u.nbConstraints, activeOnInf | activeOnSup), // restore events work the same
    connectEvent(u.updtSupEvt, u.nbConstraints, activeOnSup),
	connectEvent(u.restSupEvt, u.nbConstraints, activeOnInf | activeOnSup), // restore events work the same
//    connectEvent(u.instantiateEvt, u.nbConstraints, activeOnInst),
    connectEvent(u.remValEvt, u.nbConstraints, activeOnRem),
	connectEvent(u.restValEvt, u.nbConstraints, activeOnRem)]


// <grt> added "IntVar" in property names (VS Set for the future versions)
[disconnectIntVarEvents(v: PalmIntVar, i: integer, passiveOnInst: boolean, passiveOnInf: boolean,
								 passiveOnSup: boolean, passiveOnRem: boolean) : void
 -> if passiveOnInf disconnectEvent(v.updtInfEvt, i),
	if passiveOnInf disconnectEvent(v.restInfEvt,i),
	if passiveOnSup disconnectEvent(v.updtSupEvt,i), 
	if passiveOnSup disconnectEvent(v.restSupEvt,i),
	if passiveOnRem disconnectEvent(v.remValEvt,i), 
	if passiveOnRem disconnectEvent(v.restValEvt,i)]

	;if passiveOnInst disconnectEvent(v.instantiateEvt, i)] // not handled in PaLM

// reconnecting PaLM events
// <grt> added "IntVar" in property names (VS Set for the future versions)
[reconnectIntVarEvents(v: PalmIntVar, idx: integer, onInst: boolean, onInf: boolean, onSup: boolean, onRem: boolean): void
 ->	if onInf reconnectEvent(v.updtInfEvt, idx),
	if onInf reconnectEvent(v.restInfEvt, idx),
	if onSup reconnectEvent(v.updtSupEvt, idx),
	if onSup reconnectEvent(v.restSupEvt, idx),
	if onRem reconnectEvent(v.remValEvt, idx),
	if onRem reconnectEvent(v.restValEvt, idx)]

	;if onInst reconnectEvent(v.instantiateEvt, idx)] 


[connectHook(hk: PalmInfoConstraint, c: AbstractConstraint) : void
 -> activate(c), c.hook.everConnected := true]

[disconnectHook(hk: PalmInfoConstraint, c: AbstractConstraint) : void
 -> deactivate(c)]

[reconnectHook(hk: PalmInfoConstraint, c: AbstractConstraint) : void
 -> activate(c)]


// <fla> use fast dispatch of general PaLM methods
//   first, define the range of the methods
whyIsFalse :: property(range = set[AbstractConstraint])
whyIsTrue :: property(range = set[AbstractConstraint])
awakeOnRestoreInf :: property(range = void)
awakeOnRestoreSup :: property(range = void)
awakeOnRestoreVal :: property(range = void)

// <fla> use fast dispatch of general PaLM methods
//   second, provide a root definition
[awakeOnRestoreInf(c: AbstractConstraint, i: integer) : void 
 -> error("!!! awakeOnRestoreInf for ~S not yet implemented", c.isa)]
[awakeOnRestoreSup(c: AbstractConstraint, i: integer) : void 
 -> error("!!! awakeOnRestoreSup for ~S not yet implemented", c.isa)]
[awakeOnRestoreVal(c: AbstractConstraint, idx:integer, v:integer) : void
 -> error("!!! awakeOnRestoreVal for ~S not yet implemented", c.isa)]
[whyIsTrue(c: AbstractConstraint) : set[AbstractConstraint]
 -> error("!!! whyIsTrue for ~S not yet implemented", c.isa)]
[whyIsFalse(c: AbstractConstraint) : set[AbstractConstraint]
 -> error("!!! whyIsFalse for ~S not yet implemented", c.isa)]

// <fla> use fast dispatch of general PaLM methods
//   third, declare the method as interface, to turn on fast dispatch
(interface(whyIsFalse), 
 interface(whyIsTrue),
 interface(awakeOnRestoreInf),
 interface(awakeOnRestoreSup),
 interface(awakeOnRestoreVal))

; forward
PalmUnIntConstraint <: choco/UnIntConstraint
PalmBinIntConstraint <: choco/BinIntConstraint
PalmLargeIntConstraint <: choco/LargeIntConstraint

// <fla> use fast dispatch of general PaLM methods
//   last, declare those methods as interface of the abstract classes so that
//   they appear in the headers of the generated code (palm.h)
// does not work yet
;(interface(PalmUnIntConstraint U PalmBinIntConstraint U PalmLargeIntConstraint,
;      whyIsFalse, whyIsTrue))

// ********************************************************************
// *   Part 4: Unary constraints                                      *
// ********************************************************************

// Handling unary constraints
PalmUnIntConstraint <: choco/UnIntConstraint(
    v1:PalmIntVar)  // restrict slot range (used for type inference by the Claire compiler)

// API for creating unary constraints
// needed for constraint creation because of new assignements
[makePalmUnIntConstraint(c:class, u:PalmIntVar, cc:integer) : PalmUnIntConstraint
 -> let cont:PalmUnIntConstraint := (new(c) as PalmUnIntConstraint) in
      (
       put(v1 @ PalmUnIntConstraint,cont, u),
       put(cste @ PalmUnIntConstraint,cont, cc),
       // [0] initHook(c) 3,
	   initHook(cont), 
       cont) ]

// awakeOnRestoreXXX shared by all unary constraints
// to be specified for each constraint (if possible)
[awakeOnRestoreInf(c: PalmUnIntConstraint, i: integer) : void -> propagate(c)]
[awakeOnRestoreSup(c: PalmUnIntConstraint, i: integer) : void -> propagate(c)]


// ********************************************************************
// *   Part 5: Binary constraints                                     *
// ********************************************************************

// Handling binary constraints
PalmBinIntConstraint <: choco/BinIntConstraint(
	v1:PalmIntVar,  // restrict slot range (used for type inference by the Claire compiler)
	v2:PalmIntVar)  // id
    

// API for creating binary constraints
// needed for constraint creation because of new assignements
[makePalmBinIntConstraint(c:class, u:PalmIntVar, v:PalmIntVar, cc:integer) : PalmBinIntConstraint
 -> let cont:PalmBinIntConstraint := (new(c) as PalmBinIntConstraint) in
      (put(v1 @ PalmBinIntConstraint,cont,u),
       put(v2 @ PalmBinIntConstraint,cont,v),
       put(cste @ PalmBinIntConstraint,cont,cc),
       // [0] initHook(c) 4,
	   initHook(cont),
       cont) ]

// awakeOnRestoreXXX shared by all binary constraints
// to be specified for each constraint (if possible)
[awakeOnRestoreInf(c: PalmBinIntConstraint, i: integer) : void -> propagate(c)]
[awakeOnRestoreSup(c: PalmBinIntConstraint, i: integer) : void -> propagate(c)]


// ********************************************************************
// *   Part 6 : Large constraints                                     *
// ********************************************************************

PalmLargeIntConstraint <: choco/LargeIntConstraint()
   
// API for creating large constraints
// needed for constraint creation because of new assignements
[makePalmLargeIntConstraint(c:class, l:list[PalmIntVar], cc:integer) : PalmLargeIntConstraint
 -> let cont:PalmLargeIntConstraint := (new(c) as PalmLargeIntConstraint) in
      (cont.vars := list<IntVar>{v | v in l},  // <fla> strong typing of the var list
       cont.nbVars := length(l),
       cont.indices := make_list(length(l),integer,0),
       cont.cste := cc,
       // [0] initHook(c) 5,
	   initHook(cont),
       cont) ]



// ********************************************************************
// *   Part 7 : Basic unary constraints                               *
// ********************************************************************

// *******
// *** PalmGreaterOrEqualxc : X >= c

// class definition
PalmGreaterOrEqualxc <: PalmUnIntConstraint()

// pretty printing 
[self_print(c:PalmGreaterOrEqualxc) -> printf("~A >= ~S",c.v1.name,c.cste)]

// awakening of constraint : first propagation 
[propagate(c: PalmGreaterOrEqualxc): void
 -> let expl := Explanation() in (
		self_explain(c, expl),
		updateInf(c.v1, c.cste, c.idx1, expl)
	)]
// reaction to INCINF and DECSUP
[awakeOnInf(c:PalmGreaterOrEqualxc, i:integer) : void -> nil]
[awakeOnSup(c:PalmGreaterOrEqualxc, i:integer) : void -> nil]

// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 

// reaction to RESVAL (restoration)
[awakeOnRestoreVal(c:PalmGreaterOrEqualxc, idx:integer, v:integer) : void
 -> if (v < c.cste) (
		let expl := Explanation() 
		in (
			self_explain(c, expl),
			removeVal(c.v1, v, c.idx1, expl)
		)
	)]

// Unused but necessary for satisfying compilation
[awakeOnInst(c: PalmGreaterOrEqualxc, idx: integer): void -> nil ]
[awakeOnRem(c: PalmGreaterOrEqualxc, idx: integer, x: integer): void -> nil ]
[askIfEntailed(c: PalmGreaterOrEqualxc): (boolean U {unknown}) 
 -> let v := c.v1, x := c.cste in
    (if (getInf(v) >= x) true
     else if (getSup(v) < x) false
     else unknown)]
[testIfSatisfied(c: PalmGreaterOrEqualxc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmGreaterOrEqualxc): boolean -> false]


[whyIsFalse(c: PalmGreaterOrEqualxc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
	    self_explain(c.v1, SUP, expl),
		set!(expl)
	)]

// defined for boolean connectors
[whyIsTrue(c: PalmGreaterOrEqualxc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
	    self_explain(c.v1, INF, expl),
		set!(expl)
	)]

// registering the constraint within choco mechanims
[checkPalm(ct: PalmGreaterOrEqualxc) : boolean -> true]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmGreaterOrEqualxc) )

// *******
// *** PalmLessOrEqualxc : X <= c

// class definition
PalmLessOrEqualxc <: PalmUnIntConstraint()

// pretty printing 
[self_print(c:PalmLessOrEqualxc) -> printf("~A <= ~S",c.v1.name,c.cste)]

// constraint awakening
[propagate(c: PalmLessOrEqualxc) : void 
 -> let expl := Explanation() in (
		self_explain(c,expl),
		updateSup(c.v1, c.cste, c.idx1, expl)
	)] 

// reaction to INCINF and DECSUP
[awakeOnInf(c:PalmLessOrEqualxc, i:integer) : void -> nil] 
[awakeOnSup(c:PalmLessOrEqualxc, i:integer) : void -> nil]

// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 

// reaction to RESVAL (restoration)
[awakeOnRestoreVal(c:PalmLessOrEqualxc, idx:integer, v:integer) : void
 -> if (v > c.cste) (
		let expl := Explanation() 
		in (
			self_explain(c, expl),
			removeVal(c.v1, v, c.idx1, expl)
		)
	)]

// defined for boolean connectors
[whyIsFalse(c: PalmLessOrEqualxc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
	    self_explain(c.v1, INF, expl),
		set!(expl)
	)]

// defined for boolean connectors
[whyIsTrue(c: PalmLessOrEqualxc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
	    self_explain(c.v1, SUP, expl),
		set!(expl)
	)]



// Unused but necessary for satisfying compilation
[awakeOnInst(c: PalmLessOrEqualxc, idx: integer): void -> nil ]
[awakeOnRem(c: PalmLessOrEqualxc, idx: integer, x: integer): void -> nil ]
[askIfEntailed(c: PalmLessOrEqualxc): (boolean U {unknown}) 
 -> let v := c.v1, x := c.cste in
     (if (getSup(v) <= x) true
      else if (getInf(v) > x) false
      else unknown)]
[testIfSatisfied(c: PalmLessOrEqualxc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmLessOrEqualxc): boolean -> false]

[checkPalm(ct: PalmLessOrEqualxc) : boolean -> true]
// registering the constraint within choco mechanims
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmLessOrEqualxc) )


// *******
// *** PalmNotEqualxc : X !== c

// class definition
PalmNotEqualxc <: PalmUnIntConstraint()

// pretty printing
[self_print(c:PalmNotEqualxc) -> printf("~A !== ~S", c.v1.name, c.cste)]

// constraint awakening
[propagate(c: PalmNotEqualxc) : void 
 -> if (known?(get(bucket,c.v1))) (
		let expl := Explanation() 
		in (
			self_explain(c, expl),
			removeVal(c.v1, c.cste, c.idx1, expl)
		)
	) 
	else (
		let explinf := Explanation(),
			explsup := Explanation()
		in (
			self_explain(c, explinf),
			self_explain(c, explsup),
			if (getInf(c.v1) = c.cste)
				updateInf(c.v1, c.cste + 1, c.idx1, explinf),
			if (getSup(c.v1) = c.cste)
				updateSup(c.v1, c.cste - 1, c.idx1, explsup)
		)
	)]

// reaction to INCINF 
[awakeOnInf(c:PalmNotEqualxc, idx:integer) : void 
 -> let explinf := Explanation()
	in (
		self_explain(c, explinf),
		if (getInf(c.v1) = c.cste)
			updateInf(c.v1, c.cste + 1, c.idx1, explinf)
	)]

// reaction to DECSUP
[awakeOnSup(c:PalmNotEqualxc, idx:integer) : void 
 -> let explsup := Explanation()
	in (
		self_explain(c, explsup),
		if (c.v1.sup = c.cste)
			updateSup(c.v1, c.cste - 1, c.idx1, explsup)
	)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmNotEqualxc, idx: integer) : void
 -> awakeOnInf(c,idx)]

// reaction to INCSUP
[awakeOnRestoreSup(c: PalmNotEqualxc, idx: integer) : void
 -> awakeOnSup(c,idx)]

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmNotEqualxc, idx:integer, v:integer) : void
 -> if (c.v1.bucket.nbElements = 1 & v = c.cste) (
		let expl := Explanation() 
		in (
			self_explain(c, expl),
			removeVal(c.v1, v, c.idx1, expl)
		)
	)]

// unused but necessary for compilation
[awakeOnInst(c:PalmNotEqualxc, idx:integer) : void -> nil]
[awakeOnRem(c: PalmNotEqualxc, idx: integer, x: integer): void -> nil]
[askIfEntailed(c:PalmNotEqualxc) : (boolean U {unknown}) 
 -> let v := c.v1, x := c.cste in
     (if (v choco/isInstantiatedTo x) false
      else if not(v choco/canBeInstantiatedTo x) true
      else unknown)]
[testIfSatisfied(c:PalmNotEqualxc) : boolean -> true]
[testIfCompletelyInstantiated(c:PalmNotEqualxc): boolean -> false]


[whyIsTrue(c: PalmNotEqualxc) : set[AbstractConstraint]
 -> let e := Explanation()
	in (
		self_explain(c.v1, VAL, c.cste, e),
		set!(e)
	)]

[whyIsFalse(c: PalmNotEqualxc) : set[AbstractConstraint]
 -> assert(isInstantiated(c.v1)),
	let e := Explanation()
	in (
		self_explain(c.v1, DOM, e),
		set!(e)
	)]



[checkPalm(ct: PalmNotEqualxc) : boolean -> true]
// registering the constraint within choco mechanims
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmNotEqualxc) )

// *******
// *** PalmEqualxc : X == c

// class definition
PalmEqualxc <: PalmUnIntConstraint()
AssignmentConstraint <: PalmEqualxc()

// pretty printing 
[self_print(c:PalmEqualxc) -> printf("~A = ~S",c.v1.name,c.cste)]

// constraint awakening
[propagate(c: PalmEqualxc) : void 
 -> let explinf := Explanation(),
	    explsup := Explanation() 
	in (
		self_explain(c, explinf),
		self_explain(c, explsup),
		updateInf(c.v1, c.cste, c.idx1, explinf),
		updateSup(c.v1, c.cste, c.idx1, explsup)
	)]

[propagate(c: AssignmentConstraint) : void 
 -> ; // [0] awaking ~S // c,
	propagate@PalmEqualxc(c)]

// reaction to INCINF and DECSUP
[awakeOnInf(c:PalmEqualxc, i:integer) : void -> nil]
[awakeOnSup(c:PalmEqualxc, i:integer) : void -> nil]
 
[awakeOnInf(c:AssignmentConstraint, i:integer) : void -> nil]
[awakeOnSup(c:AssignmentConstraint, i:integer) : void -> nil]

[awakeOnRem(c: PalmEqualxc, idx: integer, x: integer): void -> nil ]
[awakeOnRem(c: AssignmentConstraint, idx: integer, x: integer): void -> nil ]


// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmEqualxc, idx:integer, v:integer) : void
 -> if (v != c.cste) (
		let expl := Explanation() 
		in (
			self_explain(c, expl),
			removeVal(c.v1, v, c.idx1, expl)
		)
	)]

// Unused but necessary for satisfying compilation
[awakeOnInst(c: PalmEqualxc, idx: integer): void -> nil ]
[askIfEntailed(c: PalmEqualxc): (boolean U {unknown}) 
 -> let v := c.v1, x := c.cste in
     (if (v choco/isInstantiatedTo x) true
      else if not(v choco/canBeInstantiatedTo x) false
      else unknown)]
[testIfSatisfied(c: PalmEqualxc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmEqualxc): boolean -> false]

[whyIsTrue(c: PalmEqualxc) : set[AbstractConstraint]
 -> assert(isInstantiated(c.v1)),
	let e := Explanation()
	in (
		self_explain(c.v1, DOM, e),
		set!(e)
	)]

[whyIsFalse(c: PalmEqualxc) : set[AbstractConstraint]
 -> let e := Explanation()
	in (
		self_explain(c.v1, VAL, c.cste, e),
		set!(e)
	)]


// registering the constraint within choco mechanims
[checkPalm(ct: PalmEqualxc) : boolean -> true]
[checkPalm(ct: AssignmentConstraint) : boolean -> true]
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmEqualxc) ) 
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(AssignmentConstraint) ) 

// ********************************************************************
// *   Part 8 : Basic binary constraints                              *
// ********************************************************************

// *******
// *** PalmNotEqualxyc : Y !== V + C

// class definition
PalmNotEqualxyc <:  PalmBinIntConstraint()

// pretty printing
[self_print(c:PalmNotEqualxyc) -> printf("~A !== ~A + ~S",c.v1.name,c.v2.name,c.cste)]

// awakening the constraint
[propagate(c:PalmNotEqualxyc) : void
 -> if (known?(bucket, c.v1)) (
		let expl := Explanation() 
		in (
			if (c.v1.bucket.nbElements = 1) (
				self_explain(c, expl),
				self_explain(c.v1, DOM, expl),
				removeVal(c.v2, firstElement(c.v1.bucket) - c.cste, c.idx2, expl)
			),
			if (c.v2.bucket.nbElements = 1) (
				self_explain(c, expl),
				self_explain(c.v2, DOM, expl),
				removeVal(c.v1, firstElement(c.v2.bucket) + c.cste, c.idx1, expl)
			)
		)
	)
	else (
		let expl := Explanation() 
		in (
			if (getInf(c.v1) = getSup(c.v1)) (
				self_explain(c, expl),
				self_explain(c.v1, DOM, expl),
				if (getInf(c.v2) = getInf(c.v1) - c.cste)
					updateInf(c.v2, c.v1.inf - c.cste + 1, c.idx2, expl),
				if (getSup(c.v2) = getInf(c.v1) - c.cste)
					updateSup(c.v2, c.v1.inf - c.cste - 1, c.idx2, clone(expl))
			) 
			else if (getInf(c.v2) = getSup(c.v2)) (
				self_explain(c, expl),
				self_explain(c.v2, DOM, expl),
				if (getInf(c.v1) = getInf(c.v2) + c.cste)
					updateInf(c.v1, c.v2.inf + c.cste + 1, c.idx1, expl),
				if (getSup(c.v1) = getInf(c.v2) + c.cste)
					updateSup(c.v1, c.v2.inf + c.cste - 1, c.idx1, clone(expl))
			)
		)
	)]

// reaction to INCINF
[awakeOnInf(c:PalmNotEqualxyc, idx:integer) : void
 -> let expl := Explanation() 
	in (
		if ((idx = 1) & (c.v1.inf = c.v1.sup)) (
			self_explain(c, expl),
			self_explain(c.v1, DOM, expl),
			if (c.v2.inf = c.v1.inf - c.cste)
				updateInf(c.v2, c.v1.inf - c.cste + 1, c.idx2, expl),
			if (c.v2.sup = c.v1.inf - c.cste)
				updateSup(c.v2, c.v1.inf - c.cste - 1, c.idx2, clone(expl))
		) 
		else if ((idx = 2) & (c.v2.inf = c.v2.sup)) (
			self_explain(c, expl),
			self_explain(c.v2, DOM, expl),
			if (c.v1.inf = c.v2.inf + c.cste)
				updateInf(c.v1, c.v2.inf + c.cste + 1, c.idx1, expl),
			if (c.v1.sup = c.v2.inf + c.cste)
				updateSup(c.v1, c.v2.inf + c.cste - 1, c.idx1, clone(expl))
		)
	)]

// reaction to DECSUP
[awakeOnSup(c:PalmNotEqualxyc, idx:integer) : void
 -> awakeOnInf(c, idx)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmNotEqualxyc, idx: integer) : void
 -> awakeOnInf(c, idx)]

// reaction to INCSUP
[awakeOnRestoreSup(c: PalmNotEqualxyc, idx: integer) : void
 -> awakeOnInf(c, idx)]

// reaction to REMVAL
[awakeOnRem(c:PalmNotEqualxyc, idx:integer, v:integer) : void
 -> let expl := Explanation() 
	in (
	    if (idx = 1) (
			if (c.v1.bucket.nbElements = 1) (
				self_explain(c, expl),
				self_explain(c.v1, DOM, expl),
				removeVal(c.v2, firstElement(c.v1.bucket) - c.cste, c.idx2, expl)
			)
		) 
	    else if (c.v2.bucket.nbElements = 1) (
			self_explain(c, expl),
			self_explain(c.v2, DOM, expl),
			removeVal(c.v1, firstElement(c.v2.bucket) + c.cste, c.idx1, expl)
		)
	)]

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmNotEqualxyc, idx:integer, v:integer) : void
 -> let expl := Explanation() 
	in (
	    if (idx = 1) (
			if (c.v1.bucket.nbElements = 1) (
				self_explain(c, expl),
				self_explain(c.v1, DOM, expl),
				removeVal(c.v2, firstElement(c.v1.bucket) - c.cste, c.idx2, expl)
			)
		) 
		else if (c.v2.bucket.nbElements = 1) (
			self_explain(c, expl),
			self_explain(c.v2, DOM, expl),
			removeVal(c.v1, firstElement(c.v2.bucket) + c.cste, c.idx1, expl)
		)
	)]


// unused but necessary for compilation
[awakeOnInst(c: PalmNotEqualxyc, idx: integer): void -> nil ]
[askIfEntailed(c: PalmNotEqualxyc): (boolean U {unknown}) 
 -> if ((getSup(c.v1) < getInf(c.v2) + c.cste) | (getSup(c.v2) < getInf(c.v1) - c.cste)) true
    else if (getInf(c.v1) = getSup(c.v1) & getInf(c.v2) = getSup(c.v2) & getInf(c.v1) = getInf(c.v2) + c.cste) false
    else unknown]
[testIfSatisfied(c: PalmNotEqualxyc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmNotEqualxyc): boolean -> false]

[checkPalm(ct: PalmNotEqualxyc) : boolean -> true]
// registering the constraint within the choco mechanims
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmNotEqualxyc) )

// *******
// *** PalmEqualxyc : Y == V + c

// class definition
PalmEqualxyc <: PalmBinIntConstraint()

// pretty printing
[self_print(c:PalmEqualxyc) -> printf("~A = ~A + ~S",c.v1.name,c.v2.name,c.cste)]

// awakening the constraint
[propagate(c:PalmEqualxyc) : void
 -> if (known?(bucket, c.v1)) (
		let b1 := c.v1.bucket,
			b2 := c.v2.bucket
		in (
			for x in domainSequence(b1) (
				if not(containsValInDomain(b2, (x - c.cste))) (
					let expl := Explanation()
					in (
						self_explain(c, expl),
						self_explain(c.v2, VAL, (x - c.cste), expl),
						removeVal(c.v1, x, c.idx1, expl)
					)
				)
			),
			for x in domainSequence(b2) (
				if not(containsValInDomain(b1, (x + c.cste))) (
					let expl := Explanation()
					in (
						self_explain(c, expl),
						self_explain(c.v1, VAL, (x + c.cste), expl),
						removeVal(c.v2, x, c.idx2, expl)
					)
				)
			)
		)
	)
	else (
		let v1inf := Explanation(),
			v1sup := Explanation(),
			v2inf := Explanation(),
			v2sup := Explanation() 
		in (
				self_explain(c,v1inf),
				self_explain(c,v2inf),
				self_explain(c,v1sup),
				self_explain(c,v2sup),

				self_explain(c.v2,INF, v1inf),
				updateInf(c.v1, c.v2.inf + c.cste, c.idx1, v1inf),

				self_explain(c.v2,SUP, v1sup),
				updateSup(c.v1, c.v2.sup + c.cste, c.idx1, v1sup),

				self_explain(c.v1,INF, v2inf),
				updateInf(c.v2, c.v1.inf - c.cste, c.idx2, v2inf),

				self_explain(c.v1,SUP, v2sup),
				updateSup(c.v2, c.v1.sup - c.cste, c.idx2, v2sup) 
		)
	)]

// reaction to INCINF
[awakeOnInf(c:PalmEqualxyc, idx:integer) : void
 -> if (idx = 1) let e := Explanation() in (
		self_explain(c,e),
		self_explain(c.v1,INF,e),
		updateInf(c.v2, c.v1.inf - c.cste, c.idx2,e)
	)
    else let e := Explanation() in (
		self_explain(c,e),
		self_explain(c.v2,INF,e),	
		updateInf(c.v1, c.v2.inf + c.cste, c.idx1,e)
	)]

// reaction to DECSUP
[awakeOnSup(c:PalmEqualxyc, idx:integer) : void
 -> if (idx = 1) let e := Explanation() in (
		self_explain(c,e),
		self_explain(c.v1,SUP,e),
		updateSup(c.v2, c.v1.sup - c.cste, c.idx2, e)
	)
    else let e := Explanation() in (
		self_explain(c,e),
		self_explain(c.v2,SUP,e),
		updateSup(c.v1, c.v2.sup + c.cste, c.idx1, e) // S. Ouis explanation missing
	)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmEqualxyc, idx: integer) : void 
 -> if (idx = 1) awakeOnInf(c,2) else awakeOnInf(c,1)]

// reaction INCSUP
[awakeOnRestoreSup(c: PalmEqualxyc, idx: integer) : void
 -> if (idx = 1) awakeOnSup(c,2) else awakeOnSup(c,1)]

// reaction to REMVAL  
[awakeOnRem(c:PalmEqualxyc, idx:integer, v:integer) : void
 -> if (idx = 1) (
		if containsValInDomain(c.v2.bucket, (v - c.cste)) (
			let e := Explanation() 
			in (
				self_explain(c, e),
				self_explain(c.v1, VAL, v, e),
				removeVal(c.v2, (v - c.cste), c.idx2, e)
			)
		) 
	)
	else (
		if containsValInDomain(c.v1.bucket, (v + c.cste)) (
			let e := Explanation() 
			in (
				self_explain(c, e),
				self_explain(c.v2, VAL, v, e),
				removeVal(c.v1, (v + c.cste), c.idx1, e)
			)
		)
	)]

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmEqualxyc, idx:integer, v:integer) : void 
 -> if (idx = 1) (
		if not(containsValInDomain(c.v2.bucket, (v - c.cste))) (
			let e := Explanation() 
			in (
				self_explain(c, e),
				self_explain(c.v2, VAL, (v - c.cste), e),
				removeVal(c.v1, v, c.idx1, e)
			)
		)
    ) 
	else (
		if not(containsValInDomain(c.v1.bucket, (v + c.cste))) (
			let e := Explanation() 
			in (
				self_explain(c, e),
				self_explain(c.v1, VAL, (v + c.cste), e),
				removeVal(c.v2, v, c.idx2, e)
			)
		) 
	)]

// unused but necessary for compilation
[awakeOnInst(c: PalmEqualxyc, idx: integer): void -> nil ]
[askIfEntailed(c: PalmEqualxyc): (boolean U {unknown})
 -> if (getSup(c.v1) < getInf(c.v2) + c.cste |
        getInf(c.v1) > getSup(c.v2) + c.cste) false
    else if (getInf(c.v1) = getSup(c.v1) &
             getInf(c.v2) = getSup(c.v2) &
             getInf(c.v1) = getInf(c.v2) + c.cste) true
    else unknown]

[testIfSatisfied(c: PalmEqualxyc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmEqualxyc): boolean -> false]

[checkPalm(ct: PalmEqualxyc) : boolean -> true]
// registering the constraint within choco mechanisms
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmEqualxyc) )


// *******
// *** PalmGreaterOrEqualxyc : U >= V + c

// class definition
PalmGreaterOrEqualxyc <: PalmBinIntConstraint()

// pretty printing 
[self_print(c:PalmGreaterOrEqualxyc) -> printf("~A >= ~A + ~S",c.v1.name,c.v2.name,c.cste)]

// a propagation tool
// a tool for ordered constraints
[hasSupport(c:PalmGreaterOrEqualxyc, idx:integer, x:integer) : boolean
 -> let ret := false
	in (
		if (idx = 1)
	        for v in domainSequence(c.v2.bucket) (if (v <= (x - c.cste)) (ret := true, break()))
		else
			for v in domainSequence(c.v1.bucket) (if (v >= (x + c.cste)) (ret := true, break())),
		ret
	)]


// awakening the constraint
[propagate(c:PalmGreaterOrEqualxyc) : void
 -> if (known?(bucket,c.v1)) (
		let b1 := c.v1.bucket,
			b2 := c.v2.bucket
		in (
			for x in domainSequence(b1) (
				if not(hasSupport(c, 1, x)) (
					let expl := Explanation()
					in (
	 					self_explain(c, expl),
						for y in ((b2.offset + 1) .. (x - c.cste)) 
							self_explain(c.v2,VAL,y,expl),
						removeVal(c.v1, x, c.idx1, expl)
					)
				)
			),
			for x in domainSequence(b2) (
				if not(hasSupport(c, 2, x)) (
					let expl := Explanation()
					in (
						self_explain(c, expl),
						for y in ((x + c.cste) .. (b1.offset + b1.bucketSize)) 
							self_explain(c.v1,VAL,y,expl),
 						self_explain(c.v1, SUP, expl),
						removeVal(c.v2, x, c.idx2, expl)
					)
				)
			)
		)
	)
	else (
		doAwakeOnInf(c,2), 
		doAwakeOnSup(c,1)
	)]

// reaction to INCINF
[awakeOnInf(c:PalmGreaterOrEqualxyc, idx:integer) : void
 ->	if (idx = 2) 
	let e := Explanation() in (
		self_explain(c,e),		
		self_explain(c.v2,INF,e),
		updateInf(c.v1, c.v2.inf + c.cste, c.idx1, e)
	)]

// reaction to DECSUP
[awakeOnSup(c:PalmGreaterOrEqualxyc, idx:integer) : void
 ->	if (idx = 1) 
	let e := Explanation() in (
		self_explain(c,e),
		self_explain(c.v1,SUP,e),
		updateSup(c.v2, c.v1.sup - c.cste, c.idx2, e)
	)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmGreaterOrEqualxyc, idx: integer) : void
 -> if (idx = 1) awakeOnInf(c, 2)] 

// reaction to INCSUP
[awakeOnRestoreSup(c: PalmGreaterOrEqualxyc, idx: integer) : void
 -> if (idx = 2) awakeOnSup(c, 1)] 

// reaction to REMVAL
[awakeOnRem(c:PalmGreaterOrEqualxyc, idx:integer, v:integer) : void
 -> let b1 := c.v1.bucket,
		b2 := c.v2.bucket
	in (
		if (idx = 1) 
			for x in domainSequence(b2)  (
				if not(hasSupport(c, 2, x)) (
					let e := Explanation()
					in (
						self_explain(c, e),
						for y in ((x + c.cste) .. (b1.offset + b1.bucketSize)) 
							self_explain(c.v1, VAL, y, e),
						removeVal(c.v2, x, c.idx2, e)
					)
				)
			)
		else
			for x in domainSequence(b1) (
				if not(hasSupport(c, 1, x)) (
					let e := Explanation()
					in (
						self_explain(c, e),
						for y in ((b2.offset + 1) .. (x - c.cste)) 
							self_explain(c.v2, VAL, y, e),
						removeVal(c.v1, x, c.idx1, e)
					)
				)
			) 
	)]

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmGreaterOrEqualxyc, idx:integer, v:integer) : void
 ->	if (idx = 1) (
		if not(hasSupport(c, 1, v)) (
			let e := Explanation() 
			in (
				self_explain(c, e),
				for x in ((c.v2.bucket.offset + 1) .. (v - c.cste)) 
					self_explain(c.v2, VAL, x, e),
				removeVal(c.v1, v, c.idx1, e)
			)
		)
	)
	else (	
		if not(hasSupport(c, 2, v)) (
			let e := Explanation()
			in (
				self_explain(c, e),
				for y in ((v + c.cste) .. (c.v1.bucket.offset + c.v1.bucket.bucketSize)) 
					self_explain(c.v1, VAL, y, e),
				removeVal(c.v2, v, c.idx2, e)
			)
		)
	)]

// defined for boolean connectors
[whyIsFalse(c: PalmGreaterOrEqualxyc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
		self_explain(c.v1, SUP, expl),
		self_explain(c.v2, INF, expl),		
		set!(expl)
	)]

// defined for boolean connectors
[whyIsTrue(c: PalmGreaterOrEqualxyc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
		self_explain(c.v1, INF, expl),
		self_explain(c.v2, SUP, expl),		
		set!(expl)
	)]

// unused but necessary for compilation
[awakeOnInst(c: PalmGreaterOrEqualxyc, idx: integer): void -> nil ]
[askIfEntailed(c: PalmGreaterOrEqualxyc): (boolean U {unknown})
 ->	if (getSup(c.v1) < getInf(c.v2) + c.cste) false
    else if (getInf(c.v1) >= getSup(c.v2) + c.cste) true
    else unknown]
[testIfSatisfied(c: PalmGreaterOrEqualxyc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmGreaterOrEqualxyc): boolean -> false]

[checkPalm(ct: PalmGreaterOrEqualxyc) : boolean -> true]
// registering the constraint within choco mechanisms
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmGreaterOrEqualxyc) )

// *******
// *** PalmLessOrEqualxyc : U <= V + c

// class definition
PalmLessOrEqualxyc <: PalmBinIntConstraint()

// pretty printing
[self_print(c:PalmLessOrEqualxyc) -> printf("~A <= ~A + ~S",c.v1.name,c.v2.name,c.cste)]

// tool for propagation
[hasSupport(c:PalmLessOrEqualxyc, idx:integer, x:integer) : boolean
 -> let ret := false
    in (
       if (idx = 1)
			for v in domainSequence(c.v2.bucket) (if (v >= (x - c.cste)) (ret := true, break()))
       else
			for v in domainSequence(c.v1.bucket) (if (v <= (x + c.cste)) (ret := true, break())),
       ret
	)]

// awakening the constraint
[propagate(c:PalmLessOrEqualxyc) : void
 -> if (known?(bucket, c.v1)) (
		let b1 := c.v1.bucket,
			b2 := c.v2.bucket
		in (
			for x in domainSequence(b1) (
				if not(hasSupport(c, 1, x)) (
					let expl := Explanation()
					in (
						self_explain(c, expl),
						for y in ((x - c.cste ) .. (b2.offset + b2.bucketSize)) 
							self_explain(c.v2,VAL,y,expl),
						removeVal(c.v1, x, c.idx1, expl)
					)
				)
			),
			for x in domainSequence(b2) (
				if not(hasSupport(c, 2, x)) (
					let expl := Explanation()
					in (
						self_explain(c, expl),
						for y in ((b1.offset + 1) .. (x + c.cste)) 
							self_explain(c.v1,VAL,y,expl),
						self_explain(c.v1, INF, expl),
						removeVal(c.v2, x, c.idx2, expl)
					)
				)
			)
		)	
	) 
	else (
		doAwakeOnInf(c,1), 
		doAwakeOnSup(c,2)
	)]

// reaction to INCINF
[awakeOnInf(c:PalmLessOrEqualxyc, idx:integer) : void
 -> if (idx = 1) 
	let e := Explanation() in (
		self_explain(c.v1,INF,e),
		self_explain(c,e),
		updateInf(c.v2, c.v1.inf - c.cste, c.idx2, e)
	)]

// reaction to DECSUP
[awakeOnSup(c:PalmLessOrEqualxyc, idx:integer) : void
 -> if (idx = 2) 
	let e := Explanation() in (
		self_explain(c.v2,SUP,e),
		self_explain(c,e),
		updateSup(c.v1, c.v2.sup + c.cste, c.idx1, e)
	)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmLessOrEqualxyc, idx: integer) : void
 -> if (idx = 2) awakeOnInf(c, 1)]

// reaction to INCSUP
[awakeOnRestoreSup(c: PalmLessOrEqualxyc, idx: integer) : void
 -> if (idx = 1) awakeOnSup(c, 2)]

// reaction to REMVAL
[awakeOnRem(c:PalmLessOrEqualxyc, idx:integer, v:integer) : void
 -> let b1 := c.v1.bucket,
		b2 := c.v2.bucket
	in (
		if (idx = 1)
			for x in domainSequence(b2) (
				if not(hasSupport(c, 2, x)) (
					let e := Explanation()
					in (
						self_explain(c, e),
						for y in ((b1.offset + 1) .. (x - c.cste)) 
							self_explain(c.v1, VAL, y, e),
						removeVal(c.v2, x, c.idx2, e)
					)
				)
			)
		else
			for x in domainSequence(b1) (
				if not(hasSupport(c, 1, x)) (
					let e := Explanation()
					in (
						self_explain(c, e),
						for y in ((x + c.cste) .. (b2.offset + b2.bucketSize)) 
							self_explain(c.v2, VAL, y, e),
						removeVal(c.v1, x, c.idx1, e)
					)
				)
			)
	)]

// reaction to RESVAL
[awakeOnRestoreVal(c:PalmLessOrEqualxyc, idx:integer, v:integer) : void
 -> let b1 := c.v1.bucket,
		b2 := c.v2.bucket 
	in (
		if (idx = 1) (
			if not(hasSupport(c, 1, v)) (
				let e := Explanation()
				in (
					self_explain(c, e),
					for x in ((v - c.cste) .. (b1.offset + b1.bucketSize)) 
						self_explain(c.v2, VAL, x, e),
					removeVal(c.v1, v, c.idx1, e)
				)
			)
		)
		else (
			if not(hasSupport(c, 2, v)) (
				let e := Explanation()
				in (
					self_explain(c, e),
					for y in ((b1.offset + 1) .. (v + c.cste)) 
						self_explain(c.v1, VAL, y, e),
					removeVal(c.v2, v, c.idx2, e)
				)
			)
		) 
	)]

// defined for boolean connectors
[whyIsFalse(c: PalmLessOrEqualxyc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
		self_explain(c.v1, INF, expl),
		self_explain(c.v2, SUP, expl),		
		set!(expl)
	)]

// defined for boolean connectors
[whyIsTrue(c: PalmLessOrEqualxyc) : set[AbstractConstraint]
 -> let expl := Explanation()
	in (
		self_explain(c.v1, SUP, expl),
		self_explain(c.v2, INF, expl),		
		set!(expl)
	)]


// unused but necessary for compilation
[awakeOnInst(c: PalmLessOrEqualxyc, idx: integer): void -> nil ]
[askIfEntailed(c: PalmLessOrEqualxyc): (boolean U {unknown})
 -> if (getInf(c.v1) > getSup(c.v2) + c.cste) false
    else if (getSup(c.v1) <= getInf(c.v2) + c.cste) true  // 0.9904 typo (v1 vs v2)
    else unknown]
[testIfSatisfied(c: PalmLessOrEqualxyc): boolean -> true]
[testIfCompletelyInstantiated(c: PalmLessOrEqualxyc): boolean -> false]

[checkPalm(ct: PalmLessOrEqualxyc) : boolean -> true]
// registering the constraint within choco mechanisms
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmLessOrEqualxyc) )



// ********************************************************************
// *   Part 9 : Linear combinations of variables                      *
// ********************************************************************

// * 1: definition, API and constraint network tools 

// class definition : inheritance from the correspoding constraint in choco
PalmLinComb <: choco/LinComb()

// pretty printing
[self_print(self:PalmLinComb)
 -> let ac := self.coefs,
		av := self.vars,
	    npv := self.nbPosVars,
		nnv := self.nbVars - npv
	in (
		if (npv > 0) (
			let coef := ac[1], var := av[1]
			in ( if (coef != 0) (
					if (coef != 1) printf("~S*~A", coef, var.name)
					else printf("~A", var.name)
			)),
			for i in (2 .. npv) (
				let coef := ac[i], var := av[i]
				in ( if (coef != 1)	printf(" + ~S*~A", coef, var.name)
					else printf(" + ~A", var.name)
				)
			),
			if (self.cste > 0) printf(" + ~S", self.cste)
		) 
		else (
			if (self.cste > 0) printf("~S", self.cste) 
			else printf("0")
		),
		if (self.op = EQ) printf(" == ") 
		else if (self.op = NEQ) printf(" <> ")
		else printf(" >= "),
		if (nnv > 0) (
			let coef := ac[npv + 1], var := av[npv + 1]
			in ( if (coef != 0) (
					if (0 - coef != 1) printf("~S*~A", 0 - coef, var.name)
					else printf("~A", var.name)
			)),
			for i in (npv + 2 .. self.nbVars) (
				let coef := ac[i], var := av[i]
				in ( 
					if (0 - coef != 1) printf(" + ~S*~A", 0 - coef, var.name)
					else printf(" + ~A", var.name)
				)
			),
			if (self.cste < 0) printf(" + ~S", 0 - self.cste)
		)
		else (
			if (self.cste < 0) printf("~S", 0 - self.cste) 
			else printf("0")
		)
	 )]

// PalmLinComb need to awake on RemoveVal ... 
[assignIndices(c:PalmLinComb, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (for k in (1 .. c.nbPosVars)
           // <grt> added "Int" in property names (VS Set for the future versions)
           (j :+ 1, connectIntVar(root,c.vars[k],j,true,(c.op != GEQ),true,true),
              // the constraint should be waken only upon updates to the upper bounds
              // of the positive variables (except in case of a equality constraint)
            c.indices[k] := length(c.vars[k].constraints)), // 0.32
        for k in (c.nbPosVars + 1 .. c.nbVars)
           // <grt> added "Int" in property names (VS Set for the future versions)
           (j :+ 1, connectIntVar(root,c.vars[k],j,true,true,(c.op != GEQ),true),
              // the constraint should be waken only upon updates to the lower bounds
              // of the negative variables (except in case of a equality constraint)
           c.indices[k] := length(c.vars[k].constraints)),
        j)]



// API for creating such constraints
[makePalmLinComb(l1:list[integer], l2:list[IntVar], c:integer, opn:integer) : PalmLinComb
 -> assert(length(l1) = length(l2)),                                          
    let posCoefs := list<integer>(), negCoefs := list<integer>(),
        posVars := list<IntVar>(), negVars := list<IntVar>() 
	in
      (for j in (1 .. length(l2))
         (if (l1[j] > 0)
             (posCoefs :add l1[j], posVars :add l2[j])
          else if (l1[j] < 0)
             (negCoefs :add l1[j], negVars :add l2[j])),
       assert(length(posVars) = length(posCoefs)),  // v0.28: size vs. length
       assert(length(negVars) = length(negCoefs)),
       let cont := PalmLinComb(vars = posVars /+ negVars, cste = c) in
          (closeLargeIntConstraint(cont),  
           // [0] initHook(c) 6,
		   initHook(cont),
           cont.op := opn,
           cont.nbPosVars := length(posCoefs),
           cont.coefs := array!(posCoefs /+ negCoefs), 
           cont) )]







// * 2: Propagation mechanisms                          


[updateDataStructuresOnConstraint(c: PalmLinComb, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> c.improvedUpperBound := true,
    c.improvedLowerBound := true]

[updateDataStructuresOnRestoreConstraint(c: PalmLinComb, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> c.improvedUpperBound := true,
    c.improvedLowerBound := true]


// The lincomb constraint is a delayed constraint and therefore
// need specific propagation mechanisms

// Explaining the current lower bound 
[explainVariablesLB(c: PalmLinComb) : Explanation
 -> let expl := Explanation() in 
	(
		self_explain(c, expl),
		for i:integer in (1 .. c.nbPosVars)
			self_explain(c.vars[i],INF,expl),
		for i:integer in (c.nbPosVars + 1 .. c.nbVars)
			self_explain(c.vars[i],SUP,expl),
		expl
	)
]

// explaining the current upper bound
[explainVariablesUB(c: PalmLinComb) : Explanation
 -> let expl := Explanation() in 
	(
		self_explain(c, expl),
		for i:integer in (1 .. c.nbPosVars)
			self_explain(c.vars[i],SUP,expl), 
		for i:integer in (c.nbPosVars + 1 .. c.nbVars)
			self_explain(c.vars[i],INF,expl), 
		expl
	)]

// v1.05 added a few casts
[computeUpperBound(c:PalmLinComb) : integer
 -> let s := 0 in
     (for i in (1 .. c.nbPosVars) s :+ (getSup(c.vars[i]) * (c.coefs[i] as integer)), // v1.05 casts
      for i in (c.nbPosVars + 1 .. c.nbVars) s :+ (getInf(c.vars[i]) * (c.coefs[i] as integer)), // v1.05 casts
      s + c.cste)]

// lower bound estimate of a linear combination of variables
//
// v1.05 added a few casts
[computeLowerBound(c:PalmLinComb) : integer
 -> let s := 0 in
     (for i in (1 .. c.nbPosVars) s :+ (getInf(c.vars[i]) * (c.coefs[i] as integer)), // v1.05 casts
      for i in (c.nbPosVars + 1 .. c.nbVars) s :+ (getSup(c.vars[i]) * (c.coefs[i] as integer)), // v1.05 casts
      s + c.cste)]



// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
[propagateNewLowerBound(c:PalmLinComb, mylb:integer) : boolean
 -> let anyChange := false 
	in (
		if (mylb > 0) raisePalmFakeContradiction(getProblem(c).propagationEngine,explainVariablesLB(c)),
		let expl := explainVariablesLB(c)
		in (
			for i in (1 .. c.nbPosVars) (
			if updateSup((c.vars[i] as PalmIntVar), -(mylb) choco/div- (c.coefs[i] as integer) + getInf(c.vars[i] as PalmIntVar), c.indices[i], clone(expl)) // v1.05 casts
				anyChange := true
			),             
			for i in (c.nbPosVars + 1 .. c.nbVars) (
			if updateInf((c.vars[i] as PalmIntVar), mylb choco/div+ -((c.coefs[i] as integer)) + getSup(c.vars[i] as PalmIntVar), c.indices[i], clone(expl)) // v1.05 casts
				anyChange := true
			),
			anyChange
		)
	  )]

// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
[propagateNewUpperBound(c:PalmLinComb, myub:integer) : boolean               
 -> let anyChange := false 
	in (
		let expl := explainVariablesUB(c)
		in (
			if (myub < 0) raisePalmFakeContradiction(getProblem(c).propagationEngine, explainVariablesUB(c)),
			for i in (1 .. c.nbPosVars) (
				if updateInf((c.vars[i] as PalmIntVar), -(myub) choco/div+ (c.coefs[i] as integer) + getSup(c.vars[i] as PalmIntVar), c.indices[i], clone(expl)) // v1.05 casts
				 anyChange := true
			),
			for i in (c.nbPosVars + 1 .. c.nbVars) (
				if updateSup((c.vars[i] as PalmIntVar), myub choco/div- -((c.coefs[i] as integer)) + getInf(c.vars[i] as PalmIntVar), c.indices[i], clone(expl)) // v1.05 casts
				 anyChange := true
			),
			anyChange
		)
	)]

       
// v0.30: may only be waken by Boolean combinations of linear constraints:
// Therefore, propagate without delay (don't set flags, ...)
// Delayed linear constraints are stored in pb.delayedConstraint and appear within a Delayer object
// the delayer handles the needToAwake flag, delegates the event abstraction job and wakes up
// the delayed constraint through calls to onePropagation
// v0.33 <thb>: this same function is used for propagating immediately or delayed
[propagate(c:PalmLinComb) : void
 -> if (c.improvedLowerBound & c.improvedUpperBound) filter(c, true, 2)
    else if c.improvedLowerBound filter(c, true, 1)   // apply LB rule at least once and ocsillate if this produces new inferences
    else if c.improvedUpperBound filter(c, false, 1)] // apply UB rule at least once and ocsillate if this produces new inferences


// awakening the constraint
[awake(c:PalmLinComb) : void
 -> // [0] awake(c:PalmLinComb) called,
 	c.improvedUpperBound := true,
    c.improvedLowerBound := true,
    filter(c, true, 2),
    // [0] awake(c:PalmLinComb) done,
    nil
]

// reaction to INCINF
[awakeOnInf(c:PalmLinComb, idx:integer) : void
 -> if (idx <= c.nbPosVars) filter(c, true, 1)
    else filter(c, false, 1)]

// reaction to DECSUP
[awakeOnSup(c:PalmLinComb, idx:integer) : void
 -> if (idx <= c.nbPosVars) filter(c, false, 1)
    else filter(c, true, 1)]

[awakeOnRem(c:PalmLinComb, idx:integer, v: integer): void
 -> filter(c, true, 2)]

// reaction to DECINF
[awakeOnRestoreInf(c: PalmLinComb, idx: integer) : void
 -> filter(c, true, 2)]

// reaction to INCSUP
[awakeOnRestoreSup(c: PalmLinComb, idx: integer) : void
 -> filter(c, true, 2)]

[awakeOnRestoreVal(c: PalmLinComb, idx: integer, v: integer) : void
 -> filter(c, true, 2)]



// abstracting reaction to DECINF
[abstractDecInf(c:PalmLinComb,i:integer) : boolean
-> c.improvedUpperBound := true,
   c.improvedLowerBound := true,
   true]

// abstracting reaction to INCSUP
[abstractIncSup(c:PalmLinComb,i:integer) : boolean
-> c.improvedUpperBound := true,
   c.improvedLowerBound := true,
   true]

[abstractRestoreVal(c: PalmLinComb, i: integer, v: integer) : boolean
 -> c.improvedUpperBound := true,
	c.improvedLowerBound := true,
	true]

// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/askIfEntailed(c:PalmLinComb) : (boolean U {unknown})
 -> if (c.op = EQ)
       let a := computeLowerBound(c), b := computeUpperBound(c) in
         (if (b < 0 | a > 0) false
          else if (a = 0 & b = 0) true
          else unknown)
    else if (c.op = GEQ)
         (if (computeUpperBound(c) < 0) false
          else if (computeLowerBound(c) >= 0) true
          else unknown)
    else (assert(c.op = NEQ),
          let a := computeLowerBound(c) in       
            (if (a > 0) true
             else let b := computeUpperBound(c) in
               (if (b < 0) true
                else if (b = 0 & a = 0) false
                else true))) ]



[testIfSatisfied(c:PalmLinComb) : boolean  -> funcall(testIfSatisfied @ LinComb, c)]
[awakeOnInst(c:PalmLinComb, idx:integer) : void  -> nil]

[checkPalm(ct: PalmLinComb) : boolean -> true]
// registering the constraint within chcco mechanismss
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmLinComb))



// ********************************************************************
// *   Part 10 : Handling delayers within PaLMchoco                   *
// ********************************************************************

// reaction to DECINF
[awakeOnRestoreInf(d:Delayer, idx:integer) : void
 -> if abstractDecInf(d.target,idx) constAwake(d, false)]

// reaction to INCSUP
[awakeOnRestoreSup(d:Delayer, idx:integer) : void
 -> if abstractIncSup(d.target,idx) constAwake(d, false)]

[awakeOnRestoreVal(d:Delayer, idx:integer, x:integer) : void
 -> if abstractRestoreVal(d.target,idx,x) constAwake(d,false)]


// making any constraint delayable ...
[abstractDecInf(c:AbstractConstraint,i:integer) : boolean -> true]
[abstractIncSup(c:AbstractConstraint,i:integer) : boolean -> true]
[abstractRestoreVal(c:AbstractConstraint,i:integer,x:integer) : boolean -> true]


// relaying DS maintenance through the target constraint ... 
[updateDataStructuresOnConstraint(d: Delayer, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> // [0] updateDataStructuresOnConstraint 15,
	updateDataStructuresOnConstraint(d.target, idx, way, newValue, oldValue)]

[updateDataStructuresOnRestoreConstraint(d: Delayer, idx: integer, way: SELECT, newValue: integer, oldValue: integer) : void
 -> updateDataStructuresOnRestoreConstraint(d.target, idx, way, newValue, oldValue)]
 



