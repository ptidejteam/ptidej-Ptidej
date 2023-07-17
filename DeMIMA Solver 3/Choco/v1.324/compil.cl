
// the interface of abstract classes must be extensible

// in those cases where the abstact class has only on single concrete class
//  (such as IntVar, IntDomain, ...), 
// there is only one single implementation of the interface and the compiler 
// optimizes the code accordignly. 
//  Here, we tell the compiler not to be smart, so that Palm can extend those interfaces !!

// ---- IntVar
(#if EXTENDABLE_CHOCO
	(abstract(updateInf),
	 abstract(updateSup),
	 abstract(removeVal),
	 abstract(isInstantiated),
	 abstract(isInstantiatedTo),
	 abstract(canBeInstantiatedTo),
	 abstract(canBeEqualTo),
	 abstract(canBeInstantiatedIn),
	 abstract(domainIncludedIn),
	 abstract(hasExactDomain),
	 abstract(getInf),
	 abstract(getSup))
 else final(IntVar))
 ;// v1.0 for Palm
;(abstract(isInstantiatedTo), abstract(canBeInstantiatedTo), abstract(getInf), abstract(getSup))

// In addition, open-up the low-level primitives of IntVar for the Palm library
(#if EXTENDABLE_CHOCO
	(abstract(connectIntVarEvents),abstract(reconnectIntVarEvents), abstract(disconnectIntVarEvents),                                 
	 abstract(connectSetVarEvents),abstract(reconnectSetVarEvents), abstract(disconnectSetVarEvents),                                 
	 abstract(connectHook),abstract(reconnectHook),abstract(disconnectHook)) )
 
// ---- AbstractConstraint
(#if EXTENDABLE_CHOCO
	(abstract(connect),abstract(disconnect),abstract(reconnect)) )

// ---- AbstractIntDomain,
(#if EXTENDABLE_CHOCO
	(abstract(domainSequence),
	 abstract(domainSet),
	 abstract(updateDomainInf),
	 abstract(updateDomainSup),
	 abstract(getDomainInf),
	 abstract(getDomainSup),
	 abstract(containsValInDomain),
	 abstract(getNextValue),
	 abstract(getPrevValue),  // <franck> v1.016
	 abstract(removeDomainVal),
	 abstract(restrict),
	 abstract(getDomainCard)) )
; (abstract(getDomainCard))  // for Palm (abstractIntDomain)
  
// a few higher level primitives that are open for re-definition
(#if EXTENDABLE_CHOCO
	(abstract(isActive),
	 abstract(getPriority),abstract(getVar),
	 abstract(awake)) )

// --- PropagationEngine
(#if EXTENDABLE_CHOCO
	(abstract(getNextActiveEventQueue)) )

// strange: don't understand why ???
(#if EXTENDABLE_CHOCO
	(abstract(propagateEvent)))  // for naren

// --- LinComb
// v1.0 specific patch for PaLM
(#if EXTENDABLE_CHOCO
	(abstract(propagateNewLowerBound), abstract(propagateNewUpperBound), 
	 abstract(computeLowerBound), abstract(computeUpperBound)) 
 else final(LinComb))

// --- Occurrence
// v1.0 for Palm
(#if EXTENDABLE_CHOCO
	(abstract(checkNbPossible), abstract(checkNbSure))
 else final(Occurrence))
