// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: bool.cl                                                    *
// *    boolean connectors                                            *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: Abstract classes for boolean connectors                *
// *   Part 2: Simple binary compositions (or, and, implies, equiv)   *
// *   Part 3: Large compositions (or, and)                           *
// *   Part 4: Cardinality constraint                                 *
// *   Part 5: Negating a constraint                                  *
// --------------------------------------------------------------------

// v1.02 new encoding for Boolean connectors
//   status_i = unknown <=> no idea about validity of c.const_i
//   status_i = true    <=> c.const_i has been proved true
//   status_i = false   <=> c.const_i has been proved false
//   targetStatus_i = unknown <=> no idea whether c.const_i should be true or not (given the state c.const2, choice points, ...)
//   targetStatus_i = true    <=> c.const_i should be true (and is therefore propagated)
//   targetStatus_i = false   <=> c.const_i should be false (and its opposite is therefore propagated)

// ********************************************************************
// *   Part 1: Abstract classes for boolean connectors                *
// ********************************************************************

// v1.02: a new compact an clean encoding of subconstraint status within Boolean constraints:
// using a bitvector to store four three-valued data (true, false, unknown): status/targetStatus of c.const1/2
// Each data is stored on two bits: 
//   - the first bit indicates whether the data is known or unknown
//   - the second bit indicates whether the data is true or false
//  Therefor the second bit is meaningful only when the first one is true.
choco/BinBoolConstraint <: BinCompositeConstraint(
   choco/statusBitVector:integer = 0)
(store(statusBitVector))   

// v1.02: extensions: methods for initializing all status information to unknown
[choco/closeBoolConstraint(c:BinCompositeConstraint) : void
 -> closeCompositeConstraint(c),
    c.statusBitVector := 0]

[choco/knownStatus(bbc:BinBoolConstraint,i:integer) : boolean => bbc.statusBitVector[4 * (i - 1)]]
[choco/getStatus(bbc:BinBoolConstraint, i:integer) : boolean => bbc.statusBitVector[4 * (i - 1) + 1]]
[choco/knownTargetStatus(bbc:BinBoolConstraint, i:integer) : boolean => bbc.statusBitVector[4 * (i - 1) + 2]]
[choco/getTargetStatus(bbc:BinBoolConstraint, i:integer) : boolean => bbc.statusBitVector[4 * (i - 1) + 3]]
[choco/setStatus(bbc:BinBoolConstraint, i:integer, b:boolean) : void
 => assert(not(knownStatus(bbc,i))),
    let bv := bbc.statusBitVector, j := 4 * (i - 1) in // for the i-th subconstraint
       (bv :+ ^2(j),                                   // the status is recorded as known
        if (b & not(getStatus(bbc,i))) bv :+ ^2(j + 1) // the status is recorded as having value b
        else if (not(b) & getStatus(bbc,i)) bv :- ^2(j + 1),
        bbc.statusBitVector := bv,
        assert(knownStatus(bbc,i) & (getStatus(bbc,i) = b)))]
[choco/setTargetStatus(bbc:BinBoolConstraint, i:integer, b:boolean) : void
 => assert(not(knownTargetStatus(bbc,i))),
    let bv := bbc.statusBitVector, j := 4 * (i - 1) in // for the i-th subconstraint
       (bv :+ ^2(j + 2),                               // the status is recorded as known
        if (b & not(getTargetStatus(bbc,i))) bv :+ ^2(j + 3) // the status is recorded as having value b
        else if (not(b) & getStatus(bbc,i)) bv :- ^2(j + 3),
        bbc.statusBitVector := bv,
        assert(knownTargetStatus(bbc,i) & (getTargetStatus(bbc,i) = b)))]

// v1.02: a new implementations with new bitvectors
//  4 bits per subconstraint (2 per status, 2 per targetStatus)
//  => store 7 subconstraint per integer
// lstatus[i] = true    <=> const[i] has been proven true
// lstatus[i] = false   <=> const[i] has been proven false
// lstatus[i] = unknown <=> no idea about validity of const[i]
//   c.nbTrueStatus = count({i in (1 .. c.nbConst | c.lstatus[i] = true})
//   c.nbFalseStatus = count({i in (1 .. c.nbConst | c.lstatus[i] = false})
// claire3 port: strongly typed lists
choco/LargeBoolConstraint <: LargeCompositeConstraint(
     choco/statusBitVectorList:list<integer>,   // upates to statusBitVectorList are also backtracked
     choco/nbTrueStatus:integer = 0,
     choco/nbFalseStatus:integer = 0)
(store(nbTrueStatus, nbFalseStatus))

// v1.02: extensions: methods for initializing all status information to unknown
[choco/closeBoolConstraint(c:LargeCompositeConstraint) : void
 -> closeCompositeConstraint(c),
    c.statusBitVectorList := make_list((c.nbConst / 7) + 1, integer,0)]

[choco/knownStatus(lbc:LargeBoolConstraint,i:integer) : boolean
 => let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7) in lbc.statusBitVectorList[i1][i2]]
[choco/getStatus(lbc:LargeBoolConstraint,i:integer) : boolean
 => let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7) in lbc.statusBitVectorList[i1][i2 + 1]]
[choco/knownTargetStatus(lbc:LargeBoolConstraint,i:integer) : boolean
 => let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7) in lbc.statusBitVectorList[i1][i2 + 2]]
[choco/getTargetStatus(lbc:LargeBoolConstraint,i:integer) : boolean
 => let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7) in lbc.statusBitVectorList[i1][i2 + 3]]
[choco/setStatus(lbc:LargeBoolConstraint,i:integer, b:boolean) : void
 => assert(not(knownStatus(lbc,i))),
    let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7),
        lbv := lbc.statusBitVectorList, bv := lbv[i1] in
       (bv :+ ^2(i2),                                   // the status is recorded as known
        if (b & not(getStatus(lbc,i))) bv :+ ^2(i2 + 1) // the status is recorded as having value b
        else if (not(b) & getStatus(lbc,i)) bv :- ^2(i2 + 1),
        store(lbc.statusBitVectorList,i1,bv,true),
        assert(knownStatus(lbc,i) & (getStatus(lbc,i) = b)))]
[choco/setTargetStatus(lbc:LargeBoolConstraint,i:integer, b:boolean) : void
 => assert(not(knownTargetStatus(lbc,i))),
    let i1 := (i / 7) + 1, i2 := 4 * ((i - 1) mod 7),
        lbv := lbc.statusBitVectorList, bv := lbv[i1] in
       (bv :+ ^2(i2 + 2),                                     // the status is recorded as known
        if (b & not(getTargetStatus(lbc,i))) bv :+ ^2(i2 + 3) // the status is recorded as having value b
        else if (not(b) & getStatus(lbc,i)) bv :- ^2(i2 + 3),
        store(lbc.statusBitVectorList,i1,bv,true),
        assert(knownTargetStatus(lbc,i) & (getTargetStatus(lbc,i) = b)))]

// -------- BoolConstraintWCounterOpp (v1.02) ---------
// two abstract classes storing the counter opposite of each subconstraint (for binary and large compositions)
// Both classes store all opposite constraints as well as an index correspondance for variables
// (between subconstraints and their counterparts)

// binary compositions with subconstraints counterparts
choco/BinBoolConstraintWCounterOpp <: BinBoolConstraint(
     choco/oppositeConst1:AbstractConstraint,
     choco/oppositeConst2:AbstractConstraint,
     choco/indicesInOppConst1:list<integer>,  // v0.29: the variable indices may now be different in const1 and oppositeConst1
     choco/indicesInOppConst2:list<integer>)  //        this is useful for using linear constraints within boolean combinations

// v0.18: overriding the variable indexing of BinCompositeConst because oppositeConst1 & oppositeConst2 
// no longer need be assigned the same indices as const1 and const2
// v0.33 <thb> typos (c1. missing)
[choco/assignIndices(c1:BinBoolConstraintWCounterOpp, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j := assignIndices(c1.const1, root, j) as integer,
        c1.offset := j - i,
        for k in (1 .. c1.offset)
            setConstraintIndex(c1.oppositeConst1,c1.indicesInOppConst1[k],getConstraintIdx(c1.const1,k)),  //v0.33  typo ("c1." missing)
        j := assignIndices(c1.const2, root, j) as integer,
        for k in (1 .. j - i - c1.offset) // <franck> v1.016, typo (-i !!)
            setConstraintIndex(c1.oppositeConst2,c1.indicesInOppConst2[k],getConstraintIdx(c1.const2,k)),  //v0.33  typo ("c1." missing)
        j)]

// v1.02 filling the slots for storing the counter opposite constraint
[choco/closeBoolConstraintWCounterOpp(c:BinBoolConstraintWCounterOpp) : void
 -> closeBoolConstraint(c),
    let c1 := c.const1, c2 := c.const2, oppc1 := opposite(c.const1), oppc2 := opposite(c.const2) in 
      (c.oppositeConst1 := oppc1,
       c.oppositeConst2 := oppc2,       
       assert(getNbVars(c1) = getNbVars(c.oppositeConst1)),
       c.indicesInOppConst1 := list<integer>{computeVarIdxInOpposite(c1,i) | i in (1 .. getNbVars(c1))}, // v0.35 (cast)
       assert(getNbVars(c2) = getNbVars(c.oppositeConst2)),
       c.indicesInOppConst2 := list<integer>{computeVarIdxInOpposite(c2,i) | i in (1 .. getNbVars(c2))}
      )]

// large compositions with subconstraints counterparts
choco/LargeBoolConstraintWCounterOpp <: LargeBoolConstraint(
     choco/loppositeConst:list<AbstractConstraint>,
     choco/indicesInOppConsts:list[list[integer]])  // v0.30 same principle as indicesInOppCons1 et 2 in Equiv (since v0.29)

// overriding the indexing of variables provided by the abstract class LargeCompositeConst,
// Indeed, the Cardianlity class adds the oppositeConstraint array (for propagating reverse conditions)
// variables need be assigned their index within the subconstraints as well as within the opposite of the subconstraints
//
[choco/assignIndices(c1:LargeBoolConstraintWCounterOpp, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
     (for constIdx in (1 .. c1.nbConst)
        let j0 := j, subc := c1.lconst[constIdx] in
           (j := (assignIndices(subc,root,j) as integer),  // <hha> 0.20
            assert(c1.loffset[constIdx] = j - i), // v0.37 (c1.offset is now filled earlier by closeCompositeConstraint)
            for ii in (1 .. j - j0)
                setConstraintIndex(c1.loppositeConst[constIdx],c1.indicesInOppConsts[constIdx][ii],getConstraintIdx(subc,ii)) ),
      assert(length(c1.additionalVars) = length(c1.additionalIndices)),
      for k in (1 .. length(c1.additionalVars))    // v1.02: handle the new additional* slots
         (j :+ 1, 
          connectIntVar(root,c1.additionalVars[k],j), 
          c1.additionalIndices[k] := length(c1.additionalVars[k].constraints)),
      j)]

// v1.02 filling the slots for storing the counter opposite constraint
[closeBoolConstraintWCounterOpp(c:LargeBoolConstraintWCounterOpp) : void
 -> closeBoolConstraint(c),
    c.loppositeConst := list<AbstractConstraint>{opposite(subc) | subc in c.lconst},  // v1.016: franck
    c.indicesInOppConsts := list{
          list<integer>{computeVarIdxInOpposite(subc,i) | i in (1 .. getNbVars(subc))} |
                              subc in c.lconst}] // v0.30, v0.35 (cast)

// ********************************************************************
// *   Part 2: Simple binary compositions (or, and, implies, equiv)   *
// ********************************************************************

// -------- Disjunctions (c1 or c2): look ahead propagation ---------
choco/Disjunction <: BinBoolConstraint()
[self_print(d:Disjunction) : void -> printf("(~S) OR (~S)", d.const1, d.const2)]

// this function is always called on a constraint that we want to propagate (either a root constraint
// or a subconstraint that now needs to be propagated, eg such that targetStatus=true)
[choco/checkStatus(d:Disjunction, i:integer) : void
 -> assert(not(knownStatus(d,i))),
    let c := (if (i = 1) d.const1 else d.const2) in  // v1.311: introduce typed local var
   (when b := askIfTrue(c) in
        (if knownTargetStatus(d,i)
            let tgtb := getTargetStatus(d,i) in
              (if (b != tgtb) raiseContradiction(d)),
         setStatus(d,i,b),
         // note (v1.02): below, we may already have targetStatus2=true without having status1=false 
         //   (for instance if the disjunction has been settled within a choice point)
         //    therefore we explicitly check that the targetStatus is not already known before setting it to true
         if (not(b) & not(knownTargetStatus(d,3 - i)))
            (setTargetStatus(d,3 - i,true),
             let c2 := (if (i = 1) d.const2 else d.const1) in
                 doAwake(c2) )))]

[choco/awakeOnInf(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnInf(d.const1,i))             // (no storage of opposite(c.const1))
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnInf(d.const2,i - d.offset))  // (no storage of opposite(c.const1))
           else checkStatus(d,2))]

[choco/awakeOnSup(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  
              doAwakeOnSup(d.const1,i))             
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  
              doAwakeOnSup(d.const2,i - d.offset))  
           else checkStatus(d,2))]

[choco/awakeOnInst(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  
              doAwakeOnInst(d.const1,i))            
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  
              doAwakeOnInst(d.const2,i - d.offset)) 
           else checkStatus(d,2))]

[choco/awakeOnRem(d:Disjunction, i:integer, v:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),
              doAwakeOnRem(d.const1,i,v))         
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),
              doAwakeOnRem(d.const2,i - d.offset,v))
           else checkStatus(d,2))]

[choco/awakeOnEnv(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnEnv(d.const1,i))             // (no storage of opposite(c.const1))
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnEnv(d.const2,i - d.offset))  // (no storage of opposite(c.const1))
           else checkStatus(d,2))]

[choco/awakeOnKer(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnKer(d.const1,i))             // (no storage of opposite(c.const1))
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnKer(d.const2,i - d.offset))  // (no storage of opposite(c.const1))
           else checkStatus(d,2))]

[choco/awakeOnInstSet(d:Disjunction, i:integer) : void
 -> if (i <= d.offset)
       (if not(knownStatus(d,1))
          (if knownTargetStatus(d,1)
             (assert(getTargetStatus(d,1) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnInstSet(d.const1,i))             // (no storage of opposite(c.const1))
           else checkStatus(d,1)))
    else if not(knownStatus(d,2))
          (if knownTargetStatus(d,2)
             (assert(getTargetStatus(d,2) = true),  // we never have targetStatus=false for a disjunction 
              doAwakeOnInstSet(d.const2,i - d.offset))  // (no storage of opposite(c.const1))
           else checkStatus(d,2))]
           
[choco/askIfEntailed(d:Disjunction) : (boolean U {unknown})
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
// Note v1.0: is that so sure ?
[choco/testIfSatisfied(d:Disjunction) : boolean
 -> (testIfTrue(d.const1) | testIfTrue(d.const2))] 

[choco/propagate(d:Disjunction) : void
 -> if not(knownStatus(d,1)) checkStatus(d,1), 
    if not(knownStatus(d,2)) checkStatus(d,2)]

// -------- Lazy guards (if (c1,c2))  -----------------------------------
choco/Guard <: BinBoolConstraint()        // note: only status1 is used
[self_print(g:Guard) : void -> printf("(~S) => (~S)", g.const1, g.const2)]

// Note: checkStatus is always called on a sub-constraint that we want to propagate
//    checkStatus asks for entailment, updates the status field, and,
//    when necessary, propagates the subconstraint through doAwake
//
[choco/checkStatus(g:Guard, i:integer)  : void
 -> assert(i = 1),
    assert(not(knownTargetStatus(g,1))),
    if not(knownStatus(g,1))
       (when b := askIfTrue(g.const1) in
           (setStatus(g,1,b),
            if (b & not(knownTargetStatus(g,2)))
               (setTargetStatus(g,2,true),                
                doAwake(g.const2))))]

[choco/awakeOnInf(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnInf(g.const2, i - g.offset))]

[choco/awakeOnSup(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnSup(g.const2, i - g.offset))]

[choco/awakeOnInst(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnInst(g.const2, i - g.offset))]

[choco/awakeOnRem(g:Guard, i:integer, v:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnRem(g.const2, i - g.offset,v))]

[choco/awakeOnKer(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnKer(g.const2, i - g.offset))]

[choco/awakeOnEnv(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnEnv(g.const2, i - g.offset))]

[choco/awakeOnInstSet(g:Guard, i:integer) : void
 -> if (i <= g.offset) checkStatus(g,1)
    else if knownTargetStatus(g,2) 
           (assert(getTargetStatus(g,2) = true),
            doAwakeOnInstSet(g.const2, i - g.offset))]

[choco/propagate(g:Guard) : void
 -> if knownTargetStatus(g,2)
     (assert(getTargetStatus(g,2) = true),
      doPropagate(g.const2))
    else checkStatus(g,1)]

// v1.05 separate awake & propagate
[choco/awake(g:Guard) : void
 -> if knownTargetStatus(g,2)
     (assert(getTargetStatus(g,2) = true),
      doAwake(g.const2))
    else checkStatus(g,1)]

[choco/askIfEntailed(g:Guard) : (boolean U {unknown})
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

[choco/testIfSatisfied(g:Guard) : boolean
 -> (not(testIfTrue(g.const1)) | testIfTrue(g.const2))]  // v0.26 wrong interface name (testIfSatisfied)

// -------- Equivalence (c1 if and only if c2)  -----------------------
choco/Equiv <: BinBoolConstraintWCounterOpp()
[self_print(c:Equiv) : void -> printf("(~S) <=> (~S)", c.const1, c.const2)]

// if status(i) can be inferred, sets targetStatus(j) and propagate accordingly
[choco/checkStatus(c:Equiv,i:integer) : void
 -> if not(knownStatus(c,i))
       let ci := ((if (i = 1) c.const1 else c.const2)),
           j := 3 - i,
           cj := ((if (j = 1) c.const1 else c.const2)),
           oppcj := ((if (j = 1) c.oppositeConst1 else c.oppositeConst2)) in
       (assert(not(knownTargetStatus(c,j))),
        when b := askIfTrue(ci) in
           (setStatus(c,i,b),
            setTargetStatus(c,j,b),
            if knownStatus(c,j) 
              (if (b != getStatus(c,j)) raiseContradiction(c))
            else if (b = true) doAwake(cj)
            else if (b = false) doAwake(oppcj) ))]
            
[choco/awakeOnInf(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnInf(c.const1, i)
              else doAwakeOnInf(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnInf(c.const2, i - c.offset)
              else doAwakeOnInf(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]

[choco/awakeOnSup(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnSup(c.const1, i)
              else doAwakeOnSup(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnSup(c.const2, i - c.offset)
              else doAwakeOnSup(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]

[choco/awakeOnInst(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnInst(c.const1, i)
              else doAwakeOnInst(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnInst(c.const2, i - c.offset)
              else doAwakeOnInst(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]

[choco/awakeOnRem(c:Equiv, i:integer, v:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnRem(c.const1, i, v)
              else doAwakeOnRem(c.oppositeConst1, c.indicesInOppConst1[i],v))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnRem(c.const2, i - c.offset,v)
              else doAwakeOnRem(c.oppositeConst2, c.indicesInOppConst2[i - c.offset], v))
           else checkStatus(c,2)) ]

[choco/awakeOnKer(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnKer(c.const1, i)
              else doAwakeOnKer(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnKer(c.const2, i - c.offset)
              else doAwakeOnKer(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]

[choco/awakeOnEnv(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnEnv(c.const1, i)
              else doAwakeOnEnv(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnEnv(c.const2, i - c.offset)
              else doAwakeOnEnv(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]

[choco/awakeOnInstSet(c:Equiv, i:integer) : void
 -> if (i <= c.offset)
       (if not(knownStatus(c,1))
          (if knownTargetStatus(c,1)
             (if getTargetStatus(c,1) doAwakeOnInstSet(c.const1, i)
              else doAwakeOnInstSet(c.oppositeConst1, c.indicesInOppConst1[i]))
           else checkStatus(c,1)))
    else if not(knownStatus(c,2))
          (if knownTargetStatus(c,2)
             (if getTargetStatus(c,2) doAwakeOnInstSet(c.const2, i - c.offset)
              else doAwakeOnInstSet(c.oppositeConst2, c.indicesInOppConst2[i - c.offset]))
           else checkStatus(c,2)) ]
           
// v1.05 when the target status is known, no need to call awake, propagate is enough
// (initial propagation was already done when the target status was settled)
[choco/propagate(c:Equiv) : void
 -> if knownTargetStatus(c,1)
      let b := getTargetStatus(c,1) in 
         (if b doPropagate(c.const1)
          else doPropagate(c.oppositeConst1))
    else if not(knownStatus(c,2)) checkStatus(c,2),
    if knownTargetStatus(c,2)
      let b := getTargetStatus(c,2) in 
         (if b doPropagate(c.const2)
          else doPropagate(c.oppositeConst2))
    else if not(knownStatus(c,1)) checkStatus(c,1)]

[choco/askIfEntailed(c:Equiv) : (boolean U {unknown})
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

[choco/testIfSatisfied(c:Equiv) : boolean
 -> (testIfTrue(c.const1) = testIfTrue(c.const2))]  // v0.26 wrong interface name (testIfSatisfied)

// -------- Conjunctions (only used in subterms of Boolean formulae) --
// note v1.02: for conjunctions, targetStatus slots are useless -> we only use status fields
choco/Conjunction <: BinBoolConstraint()
[self_print(c:Conjunction) : void -> printf("(~S) AND (~S)", c.const1, c.const2)]

[choco/awakeOnInf(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnInf(c.const1,i)
    else doAwakeOnInf(c.const2,i - c.offset)]

[choco/awakeOnSup(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnSup(c.const1,i)
    else doAwakeOnSup(c.const2,i - c.offset)]

[choco/awakeOnInst(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnInst(c.const1,i)
    else doAwakeOnInst(c.const2,i - c.offset)]

[choco/awakeOnRem(c:Conjunction, i:integer, v:integer) : void
 -> if (i <= c.offset) doAwakeOnRem(c.const1,i,v)
    else doAwakeOnRem(c.const2,i - c.offset,v)]

[choco/awakeOnKer(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnKer(c.const1,i)
    else doAwakeOnKer(c.const2,i - c.offset)]

[choco/awakeOnEnv(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnEnv(c.const1,i)
    else doAwakeOnEnv(c.const2,i - c.offset)]

[choco/awakeOnInstSet(c:Conjunction, i:integer) : void
 -> if (i <= c.offset) doAwakeOnInstSet(c.const1,i)
    else doAwakeOnInstSet(c.const2,i - c.offset)]

// v1.05 <thb> awake -> propagate
[choco/propagate(c:Conjunction) : void
 -> doPropagate(c.const1),
    doPropagate(c.const2)]

// v1.010
[choco/awake(c:Conjunction) : void
 -> doAwake(c.const1),
    doAwake(c.const2)]

[choco/askIfEntailed(c:Conjunction) : (boolean U {unknown})
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

[choco/testIfSatisfied(c:Conjunction) : boolean
 -> (testIfTrue(c.const1) & testIfTrue(c.const2))]  // v0.26 wrong interface name (testIfSatisfied)

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?)
;     (register(Disjunction), register(Guard), register(Equiv), register(Conjunction)))

// ********************************************************************
// *   Part 3: Large compositions (or, and)                           *
// ********************************************************************

// -------- Large Conjunctions (c1 or c2 or c3 ..... or cn) -----------
choco/LargeConjunction <: LargeBoolConstraint()
[self_print(c:LargeConjunction) : void 
 -> printf("(~S)",c.lconst[1]),
    for i in (2 .. c.nbConst)
      printf(" AND (~S)",c.lconst[i])]

[choco/awakeOnInf(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnInf(c.lconst[idx],reali)]

[choco/awakeOnSup(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnSup(c.lconst[idx],reali)]

[choco/awakeOnInst(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnInst(c.lconst[idx],reali)]

[choco/awakeOnRem(c:LargeConjunction, i:integer, v:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnRem(c.lconst[idx],reali,v)]

[choco/awakeOnKer(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnKer(c.lconst[idx],reali)]

[choco/awakeOnEnv(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnEnv(c.lconst[idx],reali)]

[choco/awakeOnInstSet(c:LargeConjunction, i:integer) : void
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
      let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
         doAwakeOnInstSet(c.lconst[idx],reali)]

[choco/propagate(c:LargeConjunction) : void
 -> for i in (1 .. c.nbConst)
       (if not( knownStatus(c,i) & getStatus(c,i) = true)
           doPropagate(c.lconst[i])) ]

// v1.05 separate propagate and awake
[choco/awake(c:LargeConjunction) : void
 -> for i in (1 .. c.nbConst)
       (if not( knownStatus(c,i) & getStatus(c,i) = true)
           doAwake(c.lconst[i])) ]

[choco/askIfEntailed(c:LargeConjunction) : (boolean U {unknown})
 -> let allTrue := true, oneFalse := false, i := 1, n := c.nbConst in
        (while ((allTrue | not(oneFalse)) & i <= n)
           let ithStatus := (if knownStatus(c,i) getStatus(c,i)
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

[choco/testIfSatisfied(c:LargeConjunction) : boolean
 -> forall(subc in c.lconst | testIfTrue(subc))]  // v0.26 wrong interface name (testIfSatisfied)

// -------- Large Disjunctions (c1 or c2 or c3 ..... or cn) -----------
choco/LargeDisjunction <: LargeBoolConstraint()
[self_print(c:LargeDisjunction) : void 
 -> printf("(~S)",c.lconst[1]),
    for i in (2 .. c.nbConst)
      printf(" OR (~S)",c.lconst[i])]

// v1.010: compares the number of false constraints (subconstraints whose status
// is false) with the overall number of constraints.
[private/checkNbFalseStatus(d:LargeDisjunction) : void
 -> if (d.nbFalseStatus = d.nbConst) raiseContradiction(d) // v1.010
    else if (d.nbFalseStatus = d.nbConst - 1 & d.nbTrueStatus = 0)    // v0.21
          (when j := some(j in (1 .. d.nbConst) | not(knownStatus(d,j))) in
              (setTargetStatus(d,j,true),
               doAwake(d.lconst[j])) )]

// checks the status of the i-th constraint of the disjunction
[choco/checkStatus(d:LargeDisjunction,i:integer) : void
 -> assert(not(knownStatus(d,i))),
    when b := askIfTrue(d.lconst[i]) in
        (if knownTargetStatus(d,i)
            let tgtb := getTargetStatus(d,i) in
               (if (b != tgtb) raiseContradiction(d)),
         setStatus(d,i,b),
         if b d.nbTrueStatus :+ 1
         else (d.nbFalseStatus :+ 1,
               checkNbFalseStatus(d)) )]

[choco/awakeOnInf(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnInf(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/awakeOnSup(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnSup(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/awakeOnInst(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnInst(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/awakeOnRem(ld:LargeDisjunction, i:integer, v:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnRem(ld.lconst[idx],reali,v))
           else checkStatus(ld,idx)))]

[choco/awakeOnKer(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnKer(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/awakeOnEnv(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnEnv(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/awakeOnInstSet(ld:LargeDisjunction, i:integer) : void
 -> when idx := some(idx in (1 .. ld.nbConst) | ld.loffset[idx] >= i) in
       (if not(knownStatus(ld,idx))
          (if knownTargetStatus(ld,idx)
              let reali := (if (idx = 1) i else i - ld.loffset[idx - 1]) in
                 (if getTargetStatus(ld,idx)
                     doAwakeOnInstSet(ld.lconst[idx],reali))
           else checkStatus(ld,idx)))]

[choco/askIfEntailed(c:LargeDisjunction) : (boolean U {unknown})
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

[choco/testIfSatisfied(c:LargeDisjunction) : boolean
 -> exists(subc in c.lconst | testIfTrue(subc))]  // v0.26 wrong interface name (testIfSatisfied)

// v1.05 awake -> propagate , check that status is not already known
[choco/propagate(d:LargeDisjunction) : void
 -> for i in (1 .. d.nbConst)
       (if not(knownStatus(d,i))
           checkStatus(d,i)) ]

// v1.010: the initial account of all statuses catches the case of disjunctions
// with no or one single disjunct
[choco/awake(d:LargeDisjunction) : void
 -> checkNbFalseStatus(d),
    doPropagate(d)]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?)
;     (register(LargeDisjunction), register(LargeConjunction)))

// ********************************************************************
// *   Part 4: Cardinality constraint                                 *
// ********************************************************************

// the cardinality constraint: denoting by a domain variable the number of subconstraints
// that are true among a list
choco/Cardinality <: LargeBoolConstraintWCounterOpp(
     choco/constrainOnInfNumber:boolean = true,
     choco/constrainOnSupNumber:boolean = true)

// v1.02: accessing the only additional variable: 
// denotes the number of constraints that will hold and the corresponding constraint inde
[choco/getCardVar(c:Cardinality) : IntVar => c.additionalVars[1]]
[choco/getCardVarIdx(c:Cardinality) : integer => c.additionalIndices[1]]

[self_print(c:Cardinality) : void 
 -> printf("#~S ~A ~S",
           c.lconst,
           ( if (c.constrainOnInfNumber & c.constrainOnSupNumber) "="
             else if c.constrainOnInfNumber ">="
             else "<="),
           getCardVar(c))]

// v1.010: recoded these functions so that they work also on atleast/atmax constraint
// back-propagates the upper bound of the counter variable on the status fo the subconstraints.
[private/awakeOnNbTrue(c:Cardinality) : void
-> if c.constrainOnSupNumber  // v1.010
     let nbVar := getCardVar(c) in   // v1.02 using the new accessor function
       (updateInf(nbVar,c.nbTrueStatus,0),
        if (c.nbTrueStatus > nbVar.sup) raiseContradiction(c)  // v1.010
        else if (c.nbTrueStatus = nbVar.sup)                   // v0.9904
          for j in {j in (1 .. c.nbConst) | not(knownStatus(c,j))} // v1.02 (bitvector access)
            doAwake(c.loppositeConst[j])) ]

// back-propagates the lower bound of the counter variable on the status fo the subconstraints.
[private/awakeOnNbFalse(c:Cardinality) : void
 -> if c.constrainOnInfNumber
      let nbVar := getCardVar(c) in   // v1.02 using the new accessor function
        (updateSup(nbVar, c.nbConst - c.nbFalseStatus,0),
         if (c.nbConst - c.nbFalseStatus < nbVar.inf)  // v1.010
         else if (c.nbConst - c.nbFalseStatus = nbVar.inf)  // v0.9904
           for j in {j in (1 .. c.nbConst) | not(knownStatus(c,j))} // v1.02 (bitvector access)
             doAwake(c.lconst[j])) ]

[choco/checkStatus(c:Cardinality,i:integer) : void
 -> assert(0 < i & i <= c.nbConst),
    assert(not(knownStatus(c,i))), // v1.02 (bitvector access) 
    when b := askIfTrue(c.lconst[i]) in
       (setStatus(c,i,b), // v1.02 (bitvector access)
        if b (c.nbTrueStatus :+ 1, awakeOnNbTrue(c))
        else (c.nbFalseStatus :+ 1, awakeOnNbFalse(c)) )]


[choco/awakeOnInf(c:Cardinality, i:integer) : void
 -> if (i = getNbVars(c)) awakeOnNbFalse(c)
    else (assert(i < getNbVars(c)),
          when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
             (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
                 checkStatus(c,idx)
              else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                      (if getStatus(c,idx) // v1.02 (bitvector access)
                          doAwakeOnInf(c.lconst[idx],reali)
                       else doAwakeOnInf(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) ))) ] //v0.30

[choco/awakeOnSup(c:Cardinality, i:integer) : void
 -> if (i = getNbVars(c)) awakeOnNbTrue(c)
    else (assert(i < getNbVars(c)),
          when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
             (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
                 checkStatus(c,idx)
              else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                      (if getStatus(c,idx) // v1.02 (bitvector access)
                          doAwakeOnSup(c.lconst[idx],reali)
                       else doAwakeOnSup(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) ))) ] //v0.30

[choco/awakeOnInst(c:Cardinality, i:integer) : void
 -> if (i = getNbVars(c)) (awakeOnNbFalse(c), awakeOnNbTrue(c))
    else (assert(i < getNbVars(c)),
          when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
             (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
                 checkStatus(c,idx)
              else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                      (if getStatus(c,idx) // v1.02 (bitvector access)
                          doAwakeOnInst(c.lconst[idx],reali)
                       else doAwakeOnInst(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) ))) ] //v0.30

[choco/awakeOnRem(c:Cardinality, i:integer, v:integer) : void
 -> if (i = getNbVars(c)) nil
    else (assert(i < getNbVars(c)),
          when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
             (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
                 checkStatus(c,idx)
              else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                      (if getStatus(c,idx) // v1.02 (bitvector access)
                          doAwakeOnRem(c.lconst[idx],reali,v)
                       else doAwakeOnRem(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali],v) ))) ] //v0.30

[choco/awakeOnKer(c:Cardinality, i:integer) : void
 -> assert(i < getNbVars(c)),
    when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
       (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
           checkStatus(c,idx)
        else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                (if getStatus(c,idx) // v1.02 (bitvector access)
                    doAwakeOnKer(c.lconst[idx],reali)
                 else doAwakeOnKer(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) )) ] //v0.30

[choco/awakeOnEnv(c:Cardinality, i:integer) : void
 -> assert(i < getNbVars(c)),
    when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
       (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
           checkStatus(c,idx)
        else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                (if getStatus(c,idx) // v1.02 (bitvector access)
                    doAwakeOnEnv(c.lconst[idx],reali)
                 else doAwakeOnEnv(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) )) ] //v0.30
                       
[choco/awakeOnInstSet(c:Cardinality, i:integer) : void
 -> assert(i < getNbVars(c)),
    when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
       (if not(knownStatus(c,idx)) // v1.02 (bitvector access)
           checkStatus(c,idx)
        else let reali := (if (idx = 1) i else i - c.loffset[idx - 1]) in
                (if getStatus(c,idx) // v1.02 (bitvector access)
                    doAwakeOnInstSet(c.lconst[idx],reali)
                 else doAwakeOnInstSet(c.loppositeConst[idx],c.indicesInOppConsts[idx][reali]) )) ] //v0.30

[choco/awake(c:Cardinality) : void
 -> // v1.0 two first easy inferences (same as Occurrence)
    let nbVar := getCardVar(c), nbVarIdx := getCardVarIdx(c) in   // v1.02 using the new accessor function
     (if c.constrainOnInfNumber updateSup(nbVar,c.nbConst,nbVarIdx),
      if c.constrainOnSupNumber updateInf(nbVar,0,nbVarIdx)),
    doPropagate(c)]

// propagates the status of the subconstraints onto the counter variable 
[choco/propagate(c:Cardinality) : void
 -> for i in (1 .. c.nbConst) 
       (if not(knownStatus(c,i)) checkStatus(c,i)),
    awakeOnNbTrue(c), awakeOnNbFalse(c)]

// v0.9904
[choco/testIfSatisfied(c:Cardinality) : boolean
 -> let nbVal := getCardVar(c).value in
    ( (if c.constrainOnInfNumber
          nbVal <= count(list{subc in c.lconst | testIfSatisfied(subc)})
       else true) &
      (if c.constrainOnSupNumber
          nbVal >= count(list{subc in c.lconst | testIfSatisfied(subc)})
       else true) )]

// v0.9904
[choco/askIfEntailed(c:Cardinality) : (boolean U {unknown})
 -> let i := 1, n := c.nbConst, nbVar := getCardVar(c) in
        (if (c.nbTrueStatus > nbVar.sup | n - c.nbFalseStatus < nbVar.inf) false
         else (while (i <= n) // note: this could be optimized
                 (if not(knownStatus(c,i))
                     let ithStatus := askIfTrue(c.lconst[i]) in
                       (if (ithStatus = false) 
                           (setStatus(c,i,false), c.nbFalseStatus :+ 1),
                        if (ithStatus = true) 
                           (setStatus(c,i,true), c.nbTrueStatus :+ 1)),
                   i :+ 1),
               if ((c.constrainOnSupNumber & c.nbTrueStatus > nbVar.sup) |
                   (c.constrainOnInfNumber & n - c.nbFalseStatus < nbVar.inf)) false
               else if ((not(c.constrainOnInfNumber) | c.nbTrueStatus >= nbVar.inf) &
                        (not(c.constrainOnSupNumber) | n - c.nbFalseStatus <= nbVar.sup)) true
               else unknown)) ]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(Cardinality))

// ********************************************************************
// *   Part 5: Negating a constraint                                  *
// ********************************************************************

// generic utility: transforming a constraint into its opposite
// v0.37: call closeCompositeConstraint
// v1.05 closeCompositeConstraint ->  closeBoolConstraint
[choco/opposite(c:Guard) : Conjunction
; -> let d := Conjunction(const1 = c.const1, const2 = opposite(c.const2)) in
 -> let oppc2 := opposite(c.const2), d := Conjunction(const1 = c.const1, const2 = oppc2) in
      (closeBoolConstraint(d), d)]
[choco/opposite(c:Conjunction) : Disjunction
; -> let d := Disjunction(const1 = opposite(c.const1), const2 = opposite(c.const2)) in
 -> let oppc1 := opposite(c.const1), oppc2 := opposite(c.const2), d := Disjunction(const1 = oppc1, const2 = oppc2) in
      (closeBoolConstraint(d), d)]
[choco/opposite(c:Disjunction) : Conjunction
; -> let d := Conjunction(const1 = opposite(c.const1), const2 = opposite(c.const2)) in
 -> let oppc1 := opposite(c.const1), oppc2 := opposite(c.const2), d := Conjunction(const1 = oppc1, const2 = oppc2) in
      (closeBoolConstraint(d), d)]
// v0.9903 <ebo>, <fxj>
// v1.05 <thb> remove all the status stuff by proper call to closeBoolConstraint
[choco/opposite(c:LargeDisjunction) : LargeConjunction
 -> let d := LargeConjunction(lconst = list<AbstractConstraint>{opposite(subc) | subc in c.lconst}) in // v1.011 strongly types lists
      (closeBoolConstraint(d), d)]
[choco/opposite(c:LargeConjunction) : LargeDisjunction
 -> let d := LargeDisjunction(lconst = list<AbstractConstraint>{opposite(subc) | subc in c.lconst}) in
      (closeBoolConstraint(d), d)]

// v0.37: TODO
// [choco/opposite(c:Equiv) : Cardinality
//  -> card(list(c.const1,c.const2), makeConst(1))]

// <hha> 0.17 wrong field names (v instead of v1)
[choco/opposite(c:GreaterOrEqualxc) : LessOrEqualxc -> LessOrEqualxc(v1 = c.v1, cste = c.cste - 1)]
[choco/opposite(c:LessOrEqualxc) : GreaterOrEqualxc -> GreaterOrEqualxc(v1 = c.v1, cste = c.cste + 1)]
[choco/opposite(c:Equalxc) : NotEqualxc -> NotEqualxc(v1 = c.v1, cste = c.cste)]
[choco/opposite(c:NotEqualxc) : Equalxc -> Equalxc(v1 = c.v1, cste = c.cste)]
[choco/opposite(c:Equalxyc) : NotEqualxyc -> NotEqualxyc(v1 = c.v1, v2 = c.v2, cste = c.cste)]
[choco/opposite(c:NotEqualxyc) : Equalxyc -> Equalxyc(v1 = c.v1, v2 = c.v2, cste = c.cste)]

// v1.0: the constraint LessOrEqual disappears
// variable 1 in (x >= y + c) becomes variable 2 in not(x >= y + c) rewritten (y >= x - c + 1)
[choco/opposite(c:GreaterOrEqualxyc) : GreaterOrEqualxyc
  -> GreaterOrEqualxyc(v1 = c.v2, v2 = c.v1, cste = -(c.cste) + 1)]
[choco/computeVarIdxInOpposite(c:GreaterOrEqualxyc,i:integer) : integer
 -> (if (i = 1) 2 else 1)]

// v0.29: correspondance between the indices of variables in a constraint c and its converse not(c)
[choco/computeVarIdxInOpposite(c:AbstractConstraint,i:integer) : integer
 -> error("~S not usable in a boolean constraint (don't know how to propagate its negation)",c),
    0]
[choco/computeVarIdxInOpposite(c:IntConstraint,i:integer) : integer -> i]
[choco/computeVarIdxInOpposite(c:BinCompositeConstraint,i:integer) : integer
 -> if (i <= c.offset) computeVarIdxInOpposite(c.const1,i)
    else computeVarIdxInOpposite(c.const2,i - c.offset) + c.offset]
[choco/computeVarIdxInOpposite(c:LargeCompositeConstraint,i:integer) : integer
 -> when idx := some(idx in (1 .. c.nbConst) | c.loffset[idx] >= i) in
        (if (idx = 1) computeVarIdxInOpposite(c.lconst[1],i)
         else computeVarIdxInOpposite(c.lconst[idx],i - c.loffset[idx - 1]) + c.loffset[idx - 1])
    else error("this should never happen, if ~S is a valid var idx for ~S",i,c)]
