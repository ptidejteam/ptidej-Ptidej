// ********************************************************************
// * ICEBERG: global constraints for OCRE, version 1.0.1  11/04/2002  *
// *        requires CHOCO, v1.3.18                                   *
// * file: wcsp.cl                                                    *
// *    weighted constraint satisfaction problems                     *
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************

// verbosity levels for the WCSP fragment
claire/WVIEW:integer := 1
claire/WTALK:integer := 2
claire/WDEBUG:integer := 4

// Constraint Description :
// =======================
// This constraint use a set of constraint C, a set of integer W and a domain variable T.
// It maintains an evaluation of T, the agregation of penalties for all violated constraints
// The operator of agregation can be additive
//      additiveWCSP( const:list[AbstractConstraint], penalties:list[integer], T:IntVar)
// or any other kind of operator. For example the Max operator for future implementations
//      maxWCSP( const:list[AbstractConstraint], penlaties:list[integer], T:IntVar)

// ------------------  File Overview  ---------------------------------
// *   Part 1: An abstract class for WCSPs as global constraints      *
// *   Part 2: Specific propagation algorithms for additive WCSPs     *
// *   Part 3: WCSP propagation as a reaction to Choco events         *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 1: An abstract class for WCSPs as global constraints      *
// ********************************************************************

ice/AbstractWCSP <: choco/LargeIntConstraint(
    ice/penalties:integer[],
    ice/subConst:AbstractConstraint[],
    ice/nbSubConst:integer,
    ice/nbSubVars:integer = 0,
    // coding the constraint network
    constIndices:list[list[integer]], // constIndices[varIdx]=indices of all subconst involving var
    varIndices:list[list[integer]])   // varIndices[constIdx]=indices of all vars involved in const

// accessing the variable encoding the total cost of the network (objective value of the weighted CSP)
[ice/getTotalPenaltyVar(c:AbstractWCSP) : IntVar
 => last(c.vars)]

// accessing the ith constraint of variable
[ice/getSubConst(c:AbstractWCSP,subcIdx:integer) : AbstractConstraint
 => assert(subcIdx <= c.nbSubConst),
    c.subConst[subcIdx]]
[ice/getSubConstPenalty(c:AbstractWCSP,subcIdx:integer) : integer
 => assert(subcIdx <= c.nbSubConst),
    c.penalties[subcIdx]]
[ice/getSubVar(c:AbstractWCSP,varIdx:integer) : IntVar
 => assert(varIdx <= c.nbSubVars),
    c.vars[varIdx]]

[ice/getNbSubVars(c:AbstractWCSP) : integer => c.nbSubVars]
[ice/getNbSubConst(c:AbstractWCSP) : integer => c.nbSubConst]

// handling the sub-constraint network
[getNbSubConstLinkedTo(c:AbstractWCSP,varIdx:integer) : integer
 => length(c.constIndices[varIdx])]
[getSubConstIndex(c:AbstractWCSP,varIdx:integer, i:integer) : integer
 => assert(0 <= i & i <= length(c.constIndices[varIdx])),
    c.constIndices[varIdx][i] ]

[getIndex(c:AbstractWCSP,var:IntVar) : integer
 -> when varidx := some(i in (1 .. c.nbSubVars) | getSubVar(c,i) = var) in
         varidx
    else error("~S is not a variable of WCSP ~S\n",var,c)]
[addVar(c:AbstractWCSP,var:IntVar) : void
 -> assert(not(var % c.vars)),
    assert(c.nbSubVars = length(c.vars)),
    assert(c.nbSubVars = length(c.constIndices)),
    c.vars :add var,
    c.constIndices :add list<integer>(),
    c.nbSubVars :+ 1]

// there is only one variable in that constraint for which we haven't reacted
// to an instantiation. Note that this variable may in the mean time have been instantiated
// but its IC  hasn't been taken into account within the backward checking bound yet.
[getUnInstantiatedVarIdx(c:AbstractWCSP,subcIdx:integer) : integer
 -> when vi := some(varIdx in c.varIndices[subcIdx] | not(c.reactedToInst[varIdx])) in
         vi
    else error("all variables linked to subconstraint #~S are instantiated !",subcIdx)]

[initWCSPNetwork(c:AbstractWCSP, lc:list[AbstractConstraint]) : void
 -> let nbc := length(lc) in
      (c.subConst := array!(list<AbstractConstraint>{c | c in lc}),
       c.nbSubConst := nbc,
       c.varIndices := make_list(nbc,list<integer>,list<integer>() ),
       for subci in (1 .. nbc)
         let subc := lc[subci], nbv := getNbVars(subc) in
           (c.varIndices[subci] := make_list(nbv,integer,0),
            for vidx in (1 .. nbv)
               let v := getVar(subc,vidx) in
                   when varIdx := some(i in (1 .. c.nbSubVars) | getSubVar(c,i) = v) in
                       (c.varIndices[subci][vidx] := varIdx,
                        c.constIndices[varIdx] :add subci)
                   else (addVar(c,v),          // adds v to the end of c.subVars
                         c.varIndices[subci][vidx] := c.nbSubVars,
                         c.constIndices[c.nbSubVars] :add subci)   ))]

// ********************************************************************
// *   Part 2: data structures propagation for additive WCSPs         *
// ********************************************************************

// This implementation is based on Lionel Lobjois' PhD thesis, page 50-56

// This constraint features the following internal data structures
//    MBC: a backward checking lowerbound
//    IC : a matric of inconsistency counts,such that
//         IC[x,v]=sum(penalty(c) | c in QA and (x=v) => (c is violated))
//    NONASS: the set of yet unAssigned variables
//    QA: set of quasi-instantiated constraints
//        this is not stored by iterated on the fly (see NBNI)
//    NBNI[c]: number of unAssigned variables in constraint c
//    VARPEN:  the minimum penalty over all possible values
//         VARPEN[x]=min(IC[x,val] | val in domain(x))
//    a lower bound
//         LB = MBC + sum(v in NONASS |VARPEN(v))

// Constraint semantics:
//    TotalPenalty >= LB                                 (*)
//    LB - VARPEN[x] + IC[x,v] > TotalPenalty => x != v  (**)

// Reaction to events:
//
// upon an instantiation  (x = v)
//      change(MBC) MBC :+ IC[x,v]
//      change(NBNI) NBI[c] :- 1 for all constraints c linked to x
//        if NBNI[c] = 1
//           let x2 be the remaining uninstantiated variable from c
//               add penalty(c) to IC[x2,val] for all impossible values val for x2
//      update VARPEN[x2] for all variables x2 whose IS have changed
//      change LB
//      propagate (*) and (**)

// upon any event removal (x <> v) / incinf (x >= v) / decsup (x <= v)
//      for all constraints c from QA such that x is the remaining uninstantiated var
//          recompute VARPEN[x]=min(IC[x,val] | val in domain(x))
//      change LB
//      propagate (*) and (**)

// Event handling strategy
//   Immediate reaction (called once per domain update):
//       maintain the invariants IC, QA, VARPEN, MBC
//   Delayed reaction (called once per constraint):
//       if the VARPEN/MBC/IC invariants have changed, repropagate (*) and (**)

ice/AdditiveWCSP <: AbstractWCSP(
    // invariants
    ic:integer[][],           // inconsistency count  ic[variableIndex][valueIndex]
    domainOffsets:integer[],  // offsets for access to the IC matric
    // flags:
    reactedToInst:boolean[],  // reactedToInst[varIdx]=true :var is instantiated
                              // and the invariants (backwardchecking) have been updated accordingly
    nbUnInstantiatedVars:integer[],  // one entry per constraint
    leastICValue:integer[],   // the value with least inconst. count -> used for computing varPenalty
    varPenalties:integer[],
    backwardCheckingBound:integer,
    forwardCheckingBound:integer)
(store(backwardCheckingBound,forwardCheckingBound))

[display(c:AdditiveWCSP)
 -> ;//[0] show partially instantiated constraints,
    ;for subcIdx in (1 .. getNbSubConst(c))
    ;    printf("~Sth constraint ~S: features ~S uninst subVars\n",subcIdx,getSubConst(c,subcIdx),getNbUnInstantiatedVars(c,subcIdx)),
    //[0] show inconsistency counts,
    for varIdx in (1 .. getNbSubVars(c))
      let var := getSubVar(c,varIdx) in
        (printf("IC[~S]: ",var),
         let minVal := choco/getInf(var), maxVal := choco/getSup(var) in
             for val in (minVal .. maxVal)
                 printf("~S[~S] ",val,getInconsistencyCount(c,varIdx,val)),
         princ("\n")),
    //[0] show penalties,
    //[WVIEW] bounds bkwd:~S, fwd:~S // c.backwardCheckingBound, c.forwardCheckingBound
    ]

//@api entry point
[choco/additiveWCSP(lc:list[AbstractConstraint], lp:list[integer], X:IntVar) : AdditiveWCSP
 -> assert(length(lc) = length(lp)),
    let c := AdditiveWCSP(penalties = array!(list<integer>{p | p in lp})) in
      (// initialize the WCSP network encoded in the global constraint
       initWCSPNetwork(c,lc),
       // register the objective cost (total penalty) as the last variable of the constraint
       c.vars :add X,
       // create and initialize all data structures for computing the lower bound of the penalty
       c.domainOffsets := make_array(c.nbSubVars, integer, 0),
       makeInconsistencyCount(c),
       c.nbUnInstantiatedVars := make_array(c.nbSubConst,integer,0),
       c.leastICValue := make_array(c.nbSubVars, integer, 1),  // initialize the argmin to 1
       c.varPenalties := make_array(c.nbSubVars, integer, 0),
       c.reactedToInst := make_array(c.nbSubVars, boolean, false),
       c.backwardCheckingBound := 0,
       c.forwardCheckingBound := 0,
       // initialize internal data structures provided by Choco for LargeIntConstraints
       closeLargeIntConstraint(c),
       c)]

// returns the number of subVars for which we havent reacted to an instantiation yet
[getNbUnInstantiatedVars(c:AdditiveWCSP,subcIdx:integer) : integer
 -> c.nbUnInstantiatedVars[subcIdx] ]

// accessing the inconsistency count
[getInconsistencyCount(c:AdditiveWCSP, varIdx:integer, val:integer) : integer
 => let val2 := val - c.domainOffsets[varIdx] + 1 in
        c.ic[varIdx][val2] ]
// used for initialization
[setInconsistencyCount(c:AdditiveWCSP, varIdx:integer, val:integer, x:integer) : void
 => let val2 := val - c.domainOffsets[varIdx] + 1 in
        store(c.ic[varIdx],val2,x,true)]
// used for incremental backtrackable updates
[increaseInconsistencyCount(c:AdditiveWCSP, varIdx:integer, val:integer, delta:integer) : boolean
 => //[WTALK] add ~S to IC[~S][~S] // delta,varIdx,val,
    let val2 := val - c.domainOffsets[varIdx] + 1,
        newIC := c.ic[varIdx][val2] + delta in
       (store(c.ic[varIdx] as integer[],val2,newIC,true),
        //[WDEBUG] now IC[~S][~S] = ~S and argmin[~S]:~S// varIdx,val,c.ic[varIdx][val2],varIdx,c.leastICValue[varIdx],
        if (c.leastICValue[varIdx] = val)
           recomputeVarPenalty(c,varIdx)
        else 
          (assert(recomputeVarPenalty(c,varIdx) = false),
           false ))]   ; EBO remove warning

// allocation of the required data structures
[makeInconsistencyCount(c:AdditiveWCSP) : void
 -> let dummyArray := make_array(0, integer, 0) in
       (c.ic := make_array(c.nbSubVars, integer[], dummyArray),
        for varIdx in (1 .. c.nbSubVars)
          let var := getSubVar(c,varIdx), minv := choco/getInf(var), maxv := choco/getSup(var) in
             (c.domainOffsets[varIdx] := minv,
              c.ic[varIdx] := make_array(maxv - minv + 1, integer, 0)))]


// ********************************************************************
// *   Part 3: WCSP propagation as a reaction to Choco events         *
// ********************************************************************

// reacting to the instantiation of the varIdx-th variable in the network
[choco/awakeOnInst(c:AdditiveWCSP, varIdx:integer) : void
 -> if (varIdx <= getNbSubVars(c))
      let var := getSubVar(c,varIdx), val := choco/getValue(var),
          anyPenaltyChange := false,
          deltaBackwardCheckingBound := getInconsistencyCount(c,varIdx,val) in
        (store(c.reactedToInst,varIdx,true,true),
         if (deltaBackwardCheckingBound > 0)
            (c.backwardCheckingBound :+ deltaBackwardCheckingBound,
             constAwake(c,false)),
         //[WTALK] backward checking lower bound: ~S // c.backwardCheckingBound,
         let nbc := getNbSubConstLinkedTo(c,varIdx) in
           for i in (1 .. nbc)
             let subcIdx := getSubConstIndex(c,varIdx,i),
                 subc := getSubConst(c,subcIdx) in
                (store(c.nbUnInstantiatedVars,subcIdx,c.nbUnInstantiatedVars[subcIdx] - 1,true),
                 if (c.nbUnInstantiatedVars[subcIdx] = 1)
                    let var2Idx := getUnInstantiatedVarIdx(c,subcIdx) in
                       (if updateIC(c,var2Idx,subcIdx)
                           anyPenaltyChange := true)),
        if anyPenaltyChange constAwake(c,false))    // when new constraint become quasi-instantiated -> need to retrigger the LB computation
   else constAwake(c,false)]

// returns true is the penalty of the variable is changed, now that we known
// that the constraint subc is quasi-instantiated
[updateIC(c:AdditiveWCSP,varIdx:integer,subcIdx:integer) : boolean
 -> assert(c.nbUnInstantiatedVars[subcIdx] = 1),
    let var := getSubVar(c,varIdx), subc := getSubConst(c,subcIdx), changedPenalty := false in
      (for val in domain(var)
         let wouldBeFeas := true in
           (world+(),
            var.value := val,
            assert(choco/testIfInstantiated(subc)),
            wouldBeFeas := (choco/testIfSatisfied(subc) as boolean),
            //[WDEBUG] when ~S=~S, ~S is ~S // var,val,subc,wouldBeFeas,
            world-(),
            if not(wouldBeFeas)
              (if increaseInconsistencyCount(c,varIdx,val,c.penalties[subcIdx])
                  changedPenalty := true)),
       changedPenalty)]

// returns true is the penalty of the variable is changed
[recomputeVarPenalty(c:AdditiveWCSP, varIdx:integer) : boolean
 -> assert(not(c.reactedToInst[varIdx])),
    let var := getSubVar(c,varIdx), minIC := MAXINT, argminIC := 0 in
      (for val in domain(var)
         let currentIC := getInconsistencyCount(c,varIdx,val) in
            (if (currentIC < minIC)
                (minIC := currentIC, argminIC := val)),
       let oldVarPenalty := c.varPenalties[varIdx] in
         (if (argminIC != c.leastICValue[varIdx] |
              minIC != c.varPenalties[varIdx]      )
                  // watchout, the test must be on argmin and on min,
                  // since argmin may change while min remains constant and the converse
             (//[WTALK] update varPenalty[~S] to ~S, argmin:~S // varIdx,minIC,argminIC,
              assert(minIC >= c.varPenalties[varIdx]),
              store(c.varPenalties,varIdx,minIC,true),
              store(c.leastICValue,varIdx,argminIC, true),
              ; essai infructueux de maintenance incrementale de forwardCheckingBound
              ; c.forwardCheckingBound :+ (minIC - oldVarPenalty),
              true)
          else (//[WDEBUG] no update, varPenalty[~S]=~S, argmin:~S // varIdx,minIC,argminIC,
                false)))]

// accessing VARPEN[v]
[getVarPenalty(c:AdditiveWCSP, varIdx:integer) : integer
 => c.varPenalties[varIdx] ]

// reacting to the removal of oldval from the domain of the varIdx-th variable in the network
[choco/awakeOnRem(c:AdditiveWCSP, varIdx:integer, oldval:integer) : void
 -> if (varIdx <= getNbSubVars(c))
       (if (oldval = c.leastICValue[varIdx])
           (if recomputeVarPenalty(c,varIdx)  // only in the case when the penalty of a variable is changed
               constAwake(c,false) ))]        // do we need to retrigger LB computations

// reacting to a set of removals from the domain of the varIdx-th variable in the network
[choco/awakeOnVar(c:AdditiveWCSP, varIdx:integer) : void
 -> let var := getSubVar(c,varIdx) in
      (if not(choco/isInstantiated(var))      // only in the case when the penalty of a variable is changed
         (if recomputeVarPenalty(c,varIdx)    // do we need to retrigger LB computations
             constAwake(c,false) ))]

// reacting to an increase of the domain lower bound of the varIdx-th variable in the network
[choco/awakeOnInf(c:AdditiveWCSP, varIdx:integer) : void
 -> if (varIdx <= getNbSubVars(c)) awakeOnVar(c,varIdx)]
// reacting to an increase of the domain upper bound of the varIdx-th variable in the network
[choco/awakeOnSup(c:AdditiveWCSP, varIdx:integer) : void
 -> if (varIdx <= getNbSubVars(c)) awakeOnVar(c,varIdx)
    else constAwake(c,false)]
// the priority of the constraint (among the set of all global constraints)
[choco/getPriority(c:AdditiveWCSP) : integer -> 2]

// Note (<fl>, july 10th): the algorithm below has the nice property that one run is enough in order to reach
// propagation fixpoint: as long as the consequences of a bound computation are only value removals
// and not variable fixing, then a second pass would not modify the VARPEN (min of penalties over all values
// in the domain), nor the backwardcheckingbound, thus not the fwdchk bound.
// Thus, no loop until fixpoint is required.
// Thus: - the events generated by the method (updateInf(totalPenalty,...) and removal(...))
//         use as third parameter the WCSPConstraint index (this prevents from re-awakening the constraint
//         on the events that it did generate
//       - the code never recursively calls itself (propagate) nor calls the constAwake method (the clean way to
//         cause a second call)
[choco/propagate(c:AdditiveWCSP) : void
 -> assert(forall(varIdx in (1 .. getNbSubVars(c)) |    // we assert that once all pending events have been treated, reactedToInst
            (c.reactedToInst[varIdx] = choco/isInstantiated(getSubVar(c,varIdx))) )), // exactly marks the instantiated variables
;   essai infructueux de maintenance incrementale de forwardCheckingBound
;   assert(c.forwardCheckingBound = c.backwardCheckingBound +   // we assert that the forwardCheckingBound has been properly
;               sum(list{getVarPenalty(c,vi) | vi in            // maintained through incremental computations
;                         list{i in (1 .. getNbSubVars(c)) | not(c.reactedToInst[i])} })),
    c.forwardCheckingBound := c.backwardCheckingBound +
          sum(list{getVarPenalty(c,vi) | vi in
                     list{i in (1 .. getNbSubVars(c)) | not(c.reactedToInst[i])} }),
    //[WTALK] forward checking lower bound: ~S // c.forwardCheckingBound,
    let tpv := getTotalPenaltyVar(c) in
      (if (c.forwardCheckingBound > choco/getInf(tpv))
          updateInf(tpv, c.forwardCheckingBound, c.indices[getNbSubVars(c) + 1]),
       for varIdx in list{i in (1 .. getNbSubVars(c)) | not(c.reactedToInst[i])}
         let var := getSubVar(c,varIdx) in
           (assert(not(choco/isInstantiated(var))),
            for val in domain(var)
              (if (c.forwardCheckingBound - getVarPenalty(c,varIdx) + getInconsistencyCount(c,varIdx,val) > choco/getSup(tpv))
                  (//[WVIEW] remove ~S-~S (fwd:~S, varpen:~S, ic:~S // var,val,c.forwardCheckingBound,getVarPenalty(c,varIdx),getInconsistencyCount(c,varIdx,val),
                   removeVal(var,val,c.indices[varIdx]))))) ]

// the method for the initial propagation of the global constraint
[choco/awake(c:AdditiveWCSP) : void
 -> for varIdx in (1 .. getNbSubVars(c))
       (if choco/isInstantiated(getSubVar(c,varIdx))
           store(c.reactedToInst,varIdx,true,true)),
    for subcIdx in (1 .. getNbSubConst(c))
       (c.nbUnInstantiatedVars[subcIdx] :=
           choco/getNbVars(getSubConst(c,subcIdx))
              - count(list{varI in c.varIndices[subcIdx] | c.reactedToInst[varI]}),
        //[WTALK] initialize nbUnInst[~S]=~S // subcIdx,c.nbUnInstantiatedVars[subcIdx],
        if (c.nbUnInstantiatedVars[subcIdx] = 0)
           (if not(choco/testIfSatisfied(getSubConst(c,subcIdx)))
               c.backwardCheckingBound :+ getSubConstPenalty(c,subcIdx))
        else if (c.nbUnInstantiatedVars[subcIdx] = 1)
                for varIdx in c.varIndices[subcIdx]
                    updateIC(c,varIdx,subcIdx) ),
    for varIdx in (1 .. getNbSubVars(c))
        recomputeVarPenalty(c,varIdx),
    propagate(c) ]

// checking whether the constraint is satisfied or not, once all variables are instantiated
[choco/testIfSatisfied(c:AdditiveWCSP) : boolean
 -> let nbtrue := 0 in
      (for i in (1 .. getNbSubConst(c))
          (if testIfTrue(getSubConst(c,i)) nbtrue :+ 1),
       choco/isInstantiatedTo(getTotalPenaltyVar(c),nbtrue) )]
