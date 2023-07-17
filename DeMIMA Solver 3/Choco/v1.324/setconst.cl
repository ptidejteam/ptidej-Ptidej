// ********************************************************************
// * CHOCO, version 1.330 sep. 9th 2002                               *
// * file: sets.cl                                                    *
// *    domains, propagation and constraint for set variables         *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: Generic set constraint classes                         *
// *   Part 2: set cardinality                                        *
// *   Part 3: member/notMember for integer constants                 *
// *   Part 4: member/notMember for IntVars                           *
// *   Part 5: union/intersection constraints                         *
// *   Part 6: bin comparisons (subset/disjoint/equal/overlap)        *
// *   Part 12: partition constraint                                  *
// *   Part 12: scalar constraint                                     *
// *   Part 13: enumeration constructor                               *
// *   Part 14: cons constraint                                       *
// --------------------------------------------------------------------

// verbosity for set variable propagation
SPVIEW:integer := 3
SPTALK:integer := 4

// ********************************************************************
// *   Part 1: Generic set constraint classes                         *
// ********************************************************************

// An abstract class for all constraints involving at least one set variable
choco/SetConstraint <: AbstractConstraint(
  choco/sv1:SetVar,
  choco/idx1:integer = 0)
[computeVarIdxInOpposite(c:SetConstraint,i:integer) : integer -> i]

// An abstract class for constraints involving a set variable and a constant
choco/UnSetConstraint <: SetConstraint(
  choco/cste:integer = 0)
  
[testIfCompletelyInstantiated(c:UnSetConstraint) : boolean
 -> isInstantiated(c.sv1)]
[getNbVars(c:UnSetConstraint) : integer -> 1]
[getVar(c:UnSetConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.sv1 else error("wrong var index (~S) for ~S",i,c), c.sv1]
[connect(c:UnSetConstraint) : void
 -> connectSetVar(c,c.sv1,1), if known?(hook,c) connectHook(c.hook, c)]
[disconnect(c:UnSetConstraint) : void
 -> disconnectSetVar(c.sv1,c.idx1), if known?(hook,c) disconnectHook(c.hook, c)]
[reconnect(c:UnSetConstraint) : void
 -> reconnectSetVar(c.sv1,c.idx1), if known?(hook,c) reconnectHook(c.hook, c)]
[assignIndices(c1:UnSetConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectSetVar(root,c1.sv1,j),
        setConstraintIndex(c1,1,length(c1.sv1.constraints)), // v1.03
        j)]
[getConstraintIdx(c:UnSetConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else error("impossible to get ~S-th index of ~S",idx,c)]
[setConstraintIndex(c:UnSetConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[isActive(c:UnSetConstraint) : boolean
 -> isActiveSetVar(c.sv1, c.idx1)]

// An abstract class for constraints involving a set variable and an integer variable
choco/BinSetIntConstraint <: SetConstraint(
  choco/v2:IntVar,
  choco/idx2:integer = 0)
[testIfCompletelyInstantiated(c:BinSetIntConstraint) : boolean
 -> (isInstantiated(c.v2) & isInstantiated(c.sv1))]
[getNbVars(c:BinSetIntConstraint) : integer -> 2]
[getVar(c:BinSetIntConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.sv1
    else if (i = 2) c.v2
    else (error("wrong var index (~S) for ~S",i,c), c.sv1)]
[connect(c:BinSetIntConstraint) : void
 -> connectSetVar(c,c.sv1,1), connectIntVar(c,c.v2,2), if known?(hook,c) connectHook(c.hook, c)]
[disconnect(c:BinSetIntConstraint) : void
 -> disconnectSetVar(c.sv1,c.idx1), disconnectIntVar(c.v2,c.idx2), if known?(hook,c) disconnectHook(c.hook, c)]
[reconnect(c:BinSetIntConstraint) : void
 -> reconnectSetVar(c.sv1,c.idx1), reconnectIntVar(c.v2,c.idx2), if known?(hook,c) reconnectHook(c.hook, c)]
[assignIndices(c1:BinSetIntConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectSetVar(root,c1.sv1,j),
        setConstraintIndex(c1,1,length(c1.sv1.constraints)), // v1.03
        j :+ 1, connectIntVar(root,c1.v2,j),
        setConstraintIndex(c1,2,length(c1.v2.constraints)), // v1.03
        j)]
[getConstraintIdx(c:BinSetIntConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else if (idx = 2) c.idx2
    else error("impossible to get ~S-th index of ~S",idx,c)]
[setConstraintIndex(c:BinSetIntConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else if (i = 2) c.idx2 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[isActive(c:BinSetIntConstraint) : boolean
 -> (isActiveSetVar(c.sv1, c.idx1) | isActiveIntVar(c.v2, c.idx2))]
      
// An abstract class for constraints involving two set variables
choco/BinSetConstraint <: SetConstraint(
  choco/sv2:SetVar,
  choco/idx2:integer = 0)
[testIfCompletelyInstantiated(c:BinSetConstraint) : boolean
 -> (isInstantiated(c.sv1) & isInstantiated(c.sv2))]
[getNbVars(c:BinSetConstraint) : integer -> 2]
[getVar(c:BinSetConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.sv1
    else if (i = 2) c.sv2
    else (error("wrong var index (~S) for ~S",i,c), c.sv1)]
[connect(c:BinSetConstraint) : void
 -> connectSetVar(c,c.sv1,1), connectSetVar(c,c.sv2,2), if known?(hook,c) connectHook(c.hook, c)]
[disconnect(c:BinSetConstraint) : void
 -> disconnectSetVar(c.sv1,c.idx1), disconnectSetVar(c.sv2,c.idx2), if known?(hook,c) disconnectHook(c.hook, c)]
[reconnect(c:BinSetConstraint) : void
 -> reconnectSetVar(c.sv1,c.idx1), reconnectSetVar(c.sv2,c.idx2), if known?(hook,c) reconnectHook(c.hook, c)]
[assignIndices(c1:BinSetConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectSetVar(root,c1.sv1,j),
        setConstraintIndex(c1,1,length(c1.sv1.constraints)), // v1.03
        j :+ 1, connectSetVar(root,c1.sv2,j),
        setConstraintIndex(c1,2,length(c1.sv2.constraints)), // v1.03
        j)]
[getConstraintIdx(c:BinSetConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else if (idx = 2) c.idx2
    else error("impossible to get ~S-th index of ~S",idx,c)]
[setConstraintIndex(c:BinSetConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else if (i = 2) c.idx2 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[isActive(c:BinSetConstraint) : boolean
 -> (isActiveSetVar(c.sv1, c.idx1) | isActiveSetVar(c.sv2, c.idx2))]

// An abstract class for constraints involving two set variables
choco/TernSetConstraint <: SetConstraint(
  choco/sv2:SetVar,
  choco/sv3:SetVar,
  choco/idx2:integer = 0,
  choco/idx3:integer = 0)
[testIfCompletelyInstantiated(c:TernSetConstraint) : boolean
 -> (isInstantiated(c.sv1) & isInstantiated(c.sv2) & isInstantiated(c.sv3))]
[getNbVars(c:TernSetConstraint) : integer -> 3]
[getVar(c:TernSetConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.sv1
    else if (i = 2) c.sv2
    else if (i = 2) c.sv3
    else (error("wrong var index (~S) for ~S",i,c), c.sv1)]
[connect(c:TernSetConstraint) : void
 -> connectSetVar(c,c.sv1,1), connectSetVar(c,c.sv2,2), connectSetVar(c,c.sv3,3), if known?(hook,c) connectHook(c.hook, c)]
[disconnect(c:TernSetConstraint) : void
 -> disconnectSetVar(c.sv1,c.idx1), disconnectSetVar(c.sv2,c.idx2), disconnectSetVar(c.sv3,c.idx3), if known?(hook,c) disconnectHook(c.hook, c)]
[reconnect(c:TernSetConstraint) : void
 -> reconnectSetVar(c.sv1,c.idx1), reconnectSetVar(c.sv2,c.idx2), reconnectSetVar(c.sv3,c.idx3), if known?(hook,c) reconnectHook(c.hook, c)]
[assignIndices(c1:TernSetConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectSetVar(root,c1.sv1,j),
        setConstraintIndex(c1,1,length(c1.sv1.constraints)),
        j :+ 1, connectSetVar(root,c1.sv2,j),
        setConstraintIndex(c1,2,length(c1.sv2.constraints)),
        j :+ 1, connectSetVar(root,c1.sv3,j),
        setConstraintIndex(c1,3,length(c1.sv3.constraints)),
        j)]
[getConstraintIdx(c:TernSetConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else if (idx = 2) c.idx2
    else if (idx = 3) c.idx3
    else error("impossible to get ~S-th index of ~S",idx,c)]
[setConstraintIndex(c:TernSetConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else if (i = 2) c.idx2 := val
    else if (i = 3) c.idx3 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[isActive(c:TernSetConstraint) : boolean
 -> (isActiveSetVar(c.sv1, c.idx1) | isActiveSetVar(c.sv2, c.idx2) | isActiveSetVar(c.sv3, c.idx3))]
        
// TODO: lien entre les fonctions doAwake et awake...
    
// ********************************************************************
// *   Part 2: set cardinality                                        *
// ********************************************************************

choco/SetCard <: BinSetIntConstraint()

[awakeOnKer(c:SetCard, idx:integer) : void
 -> assert(idx = 1),
    let v := c.v2, sv := c.sv1, svdom := sv.setBucket, kerSize := getDomainKernelSize(sv) in
      (//[SPTALK] awakeonKer: ~S >= ~S // v,kerSize,
       updateInf(v,kerSize,c.idx2),
       if (v.sup = kerSize)
         for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
             remFromEnveloppe(sv,i,c.idx1))]

[awakeOnEnv(c:SetCard, idx:integer) : void
 -> assert(idx = 1),
    let v := c.v2, sv := c.sv1, svdom := sv.setBucket, envSize := getDomainEnveloppeSize(sv) in
      (//[SPTALK] awakeonEnv: ~S <= ~S // v,envSize,
       updateSup(v,envSize,c.idx2),
       if (v.inf = envSize)
          for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
              addToKernel(sv,i,c.idx1))]

[awakeOnInstSet(c:SetCard, idx:integer) : void
 -> assert(idx = 1),
    let v := c.v2, sv := c.sv1, kerSize := getDomainKernelSize(sv) in
        instantiate(v,kerSize,c.idx2)]

[awakeOnInf(c:SetCard, idx:integer) : void
 -> assert(idx = 2),
    let v := c.v2, sv := c.sv1, svdom := sv.setBucket, envSize := getEnveloppeSize(svdom) in
       (if (v.inf > envSize) raiseContradiction(c)
        else if (v.inf = envSize)
          (//[SPTALK] awakeonInf: card >= ~S => all enveloppe in Kernel // v.inf, 
           for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
              addToKernel(sv,i,c.idx1)))]

[awakeOnSup(c:SetCard, idx:integer) : void
 -> assert(idx = 2),
    let v := c.v2, sv := c.sv1, svdom := sv.setBucket, kerSize := getKernelSize(svdom) in
       (if (v.sup < kerSize) raiseContradiction(c)
        else if (v.sup = kerSize)
          (//[SPTALK] awakeonInf: card <= ~S => all enveloppe out of Kernel // v.sup,
           for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
              remFromEnveloppe(sv,i,c.idx1)))]

[awakeOnInst(c:SetCard, idx:integer) : void
 -> awakeOnVar(c,idx)]

[awakeOnRem(c:SetCard, idx:integer, val:integer) : void
 -> assert(idx = 2)]

[awakeOnVar(c:SetCard, idx:integer) : void
 -> assert(idx = 2),
    let v := c.v2, sv := c.sv1, svdom := sv.setBucket,
        kerSize := getKernelSize(svdom), envSize := getEnveloppeSize(svdom) in
       (if (v.sup < kerSize | v.inf > envSize) raiseContradiction(c)
        else if (kerSize < envSize)
           (if (v.inf = envSize)
               for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
                   addToKernel(sv,i,c.idx1)
            else if (v.sup = kerSize)
               for i in {i in getEnveloppe(svdom) | not(i % getKernel(svdom))}
                   remFromEnveloppe(sv,i,c.idx1)))]              
                                
[askIfEntailed(c:SetCard) : (boolean U {unknown})
 -> let v := c.v2, sv := c.sv1 in
      (if      (getInf(v) > getDomainEnveloppeSize(sv)) false
       else if (getSup(v) < getDomainKernelSize(sv)) false
       else if (isInstantiated(v) & isInstantiated(sv)) true
       else unknown)]

[testIfSatisfied(c:SetCard) : boolean
 -> let v := c.v2, sv := c.sv1 in 
      (assert(isInstantiated(v) & isInstantiated(sv)),
       getValue(v) = getDomainEnveloppeSize(sv))]
       
// same method for awake & propagate
[propagate(c:SetCard) : void
 -> let v := c.v2, sv := c.sv1, 
        envSize := getDomainEnveloppeSize(sv), kerSize := getDomainKernelSize(sv) in 
     (if (v.inf > envSize) raiseContradiction(c),
      if (v.sup < kerSize) raiseContradiction(c),
      if (v.inf = envSize)
         for i in {i in getDomainEnveloppe(sv) | not(i % getDomainKernel(sv))}
             addToKernel(sv,i,c.idx1),
      if (v.sup = kerSize)
         for i in {i in getDomainEnveloppe(sv) | not(i % getDomainKernel(sv))}
             remFromEnveloppe(sv,i,c.idx1))]

[awake(c:SetCard) : void
 -> let v := c.v2, sv := c.sv1 in 
      (updateInf(v,0,c.idx2),
       updateSup(v,getDomainEnveloppeSize(sv),c.idx1),
       propagate(c))]
[choco/setCard(sv:SetVar, iv:IntVar) : SetCard
 -> SetCard(sv1 = sv, v2 = iv)]
      
// ********************************************************************
// *   Part 3: member/notMember for integer constants                 *
// ********************************************************************

// A constraint stating that a constant is a member of a set variable
choco/MemberC <: UnSetConstraint()

[awakeOnKer(c:MemberC, idx:integer) : void
 -> assert(idx = 1)]
[awakeOnEnv(c:MemberC, idx:integer) : void
 -> assert(idx = 1),
    if not(isInDomainEnveloppe(c.sv1,c.cste)) raiseContradiction(c)]
[awakeOnInstSet(c:MemberC, idx:integer) : void
 -> if not(isInDomainKernel(c.sv1,c.cste)) raiseContradiction(c)]
[askIfEntailed(c:MemberC) : (boolean U {unknown})
 -> if not(isInDomainEnveloppe(c.sv1,c.cste)) false
    else if isInDomainKernel(c.sv1,c.cste) true
    else unknown]
[testIfSatisfied(c:MemberC) : boolean -> isInDomainKernel(c.sv1,c.cste)]
[propagate(c:MemberC) : void -> addToKernel(c.sv1,c.cste,c.idx1)]
[awake(c:MemberC) : void -> addToKernel(c.sv1,c.cste,c.idx1)]
[choco/memberOf(sv:SetVar, a:integer) : MemberC
 -> MemberC(sv1 = sv, cste = a)]

 // A constraint stating that a constant is not a member of a set variable
choco/NotMemberC <: UnSetConstraint()

[awakeOnKer(c:NotMemberC, idx:integer) : void
 -> assert(idx = 1),
    if isInDomainKernel(c.sv1,c.cste) raiseContradiction(c)]
[awakeOnEnv(c:NotMemberC, idx:integer) : void
 -> assert(idx = 1)]
[awakeOnInstSet(c:NotMemberC, idx:integer) : void
 -> if isInDomainKernel(c.sv1,c.cste) raiseContradiction(c)]
[askIfEntailed(c:NotMemberC) : (boolean U {unknown})
 -> if not(isInDomainEnveloppe(c.sv1,c.cste)) true
    else if isInDomainKernel(c.sv1,c.cste) false
    else unknown]
[testIfSatisfied(c:NotMemberC) : boolean -> not(isInDomainKernel(c.sv1,c.cste))]
[propagate(c:NotMemberC) : void -> remFromEnveloppe(c.sv1,c.cste,c.idx1)]
[awake(c:NotMemberC) : void -> remFromEnveloppe(c.sv1,c.cste,c.idx1)]
[choco/notMemberOf(sv:SetVar, a:integer) : NotMemberC
 -> NotMemberC(sv1 = sv, cste = a)]
[opposite(c:MemberC) : NotMemberC -> NotMemberC(sv1 = c.sv1, cste = c.cste)]
[opposite(c:NotMemberC) : MemberC -> MemberC(sv1 = c.sv1, cste = c.cste)]

// ********************************************************************
// *   Part 4: member/notMember for IntVars                           *
// ********************************************************************

// A constraint stating that a variable is a member of a set variable
choco/MemberX <: BinSetIntConstraint()

[awakeOnKer(c:MemberX, idx:integer) : void
 -> assert(idx = 1)]
[awakeOnEnv(c:MemberX, idx:integer) : void
 -> assert(idx = 1),
    let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, nb := 0, theval := 0 in 
     (updateInf(iv,getEnveloppeInf(svdom),c.idx2),
      updateSup(iv,getEnveloppeSup(svdom),c.idx2),
      for val in domain(iv) 
         (if isInEnveloppe(svdom,val)
            (nb :+ 1, theval := val,
             if (nb > 1) break())),
      if (nb = 0) raiseContradiction(c)
      else if (nb = 1) 
         (instantiate(iv,theval,c.idx2),
          addToKernel(sv,theval,c.idx1)) )]
[awakeOnInstSet(c:MemberX, idx:integer) : void
 -> awakeOnEnv(c,idx)]
[awakeOnInst(c:MemberX, idx:integer) : void
 -> addToKernel(c.sv1,c.v2.value,c.idx1)]
[awakeOnVar(c:MemberX, idx:integer) : void
 -> assert(idx = 2),
    let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, nb := 0, theval := 0 in 
     (for val in domain(iv) 
         (if isInEnveloppe(svdom,val)
            (nb :+ 1, theval := val,
             if (nb > 1) break())),
      if (nb = 0) raiseContradiction(c)
      else if (nb = 1) 
         (instantiate(iv,theval,c.idx2),
          addToKernel(sv,theval,c.idx1)) )]
[awakeOnSup(c:MemberX, idx:integer) : void
 -> awakeOnVar(c,idx)]
[awakeOnInf(c:MemberX, idx:integer) : void
 -> awakeOnVar(c,idx)]
[awakeOnRem(c:MemberX, idx:integer, val:integer) : void
 -> awakeOnVar(c,idx)]
[askIfEntailed(c:MemberX) : (boolean U {unknown})
 -> let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, nb := 0, theval := 0, 
        allValuesOutEnv := true, allValuesInKer := true in 
     (for val in domain(iv) 
         (if isInEnveloppe(svdom,val)
             (allValuesOutEnv := false,
              if not(isInKernel(svdom,val))
                (allValuesInKer := false,
                 break()))),
      if allValuesInKer true
      else if allValuesOutEnv false
      else unknown)]
[testIfSatisfied(c:MemberX) : boolean
 -> isInDomainKernel(c.sv1,c.v2.value)]
[propagate(c:MemberX) : void -> awakeOnEnv(c,1)]
[awake(c:MemberX) : void -> awakeOnEnv(c,1)]
[choco/memberOf(sv:SetVar, v:IntVar) : MemberX
 -> MemberX(sv1 = sv, v2 = v)]

// A constraint stating that a variable is not member of a set variable
choco/NotMemberX <: BinSetIntConstraint()

// awake when the kernel of sv is modified:
//   all values from the kernel of sv (the set variable) must be removed 
//   from the domain of iv (the integer variable)
//   if only one value out of the kernel, then this value can be removed from the enveloppe
[awakeOnKer(c:NotMemberX, idx:integer) : void
 -> assert(idx = 1),
    propagate(c)]
// awake when the enveloppe of sv is modified:
//   nothing to do
[awakeOnEnv(c:NotMemberX, idx:integer) : void
 -> nil]
// awake when the set sv is fixed:
//   all values from the kernel of sv (the set variable) must be removed 
//   from the domain of iv (the integer variable)
[awakeOnInstSet(c:NotMemberX, idx:integer) : void
 -> assert(idx = 1),
    let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, someValOutOfEnv := false in 
      (for val in domain(iv) 
         (if isInKernel(svdom,val)
             removeVal(iv,val,c.idx2)
          else someValOutOfEnv := true),
       if not(someValOutOfEnv)
          raiseContradiction(c)
       )]
// awake when iv is modified but not instantiated
//   
[awakeOnSup(c:NotMemberX, idx:integer) : void -> awakeOnVar(c,idx)]
[awakeOnInst(c:NotMemberX, idx:integer) : void -> awakeOnVar(c,idx)]
[awakeOnRem(c:NotMemberX, idx:integer, val:integer) : void -> awakeOnVar(c,idx)]
[awakeOnVar(c:NotMemberX, idx:integer) : void
 -> assert(idx = 2),
    propagate(c)]
[askIfEntailed(c:NotMemberX) : (boolean U {unknown})
 -> let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, nb := 0, theval := 0, 
        allValuesOutEnv := true, allValuesInKer := true in 
     (for val in domain(iv) 
         (if isInEnveloppe(svdom,val)
             (allValuesOutEnv := false,
              if not(isInKernel(svdom,val))
                (allValuesInKer := false,
                 break()))),
      if allValuesOutEnv     true
      else if allValuesInKer false
      else unknown)]
[testIfSatisfied(c:NotMemberX) : boolean -> not(isInKernel(c.sv1.setBucket,c.v2.value))]
[propagate(c:NotMemberX) : void
 -> let iv := c.v2, sv := c.sv1, svdom := sv.setBucket, nb := 0, theval := 0 in 
      (for val in domain(iv) 
         (if isInKernel(svdom,val)
             removeVal(iv,val,c.idx2)
          else (nb :+ 1, theval := val,
                if (nb > 1) break())),
       if (nb = 0) raiseContradiction(c)
       else if (nb = 1) 
          (remFromEnveloppe(sv,theval, c.idx1),
           instantiate(iv,theval, c.idx2)) )]                     
[awake(c:NotMemberX) : void -> propagate(c)]
[opposite(c:MemberX) : NotMemberX -> NotMemberX(sv1 = c.sv1, v2 = c.v2)]
[opposite(c:NotMemberX) : MemberX -> MemberX(sv1 = c.sv1, v2 = c.v2)]
[choco/notMemberOf(sv:SetVar, v:IntVar) : NotMemberX
 -> NotMemberX(sv1 = sv, v2 = v)]

// ********************************************************************
// *   Part 5: union/intersection constraints                         *
// ********************************************************************

// A constraint stating that a set is the intersection of two other sets
choco/SetIntersection <: TernSetConstraint()

// There are seven propagation rules for the constraint sv3 = intersection(sv1, sv2)
//    Ker(sv1) contains Ker(sv3)
//    Ker(sv2) contains Ker(sv3)
//    Ker(sv3) contains (Ker(sv1) inter Ker(sv2))
//    Env(v3)  disjoint Complement(Env(v1)) 
//    Env(v3)  disjoint Complement(Env(v2)) 
//    Env(v2)  disjoint Ker(v1) inter Complement(Env(v3))
//    Env(v1)  disjoint Ker(v2) inter Complement(Env(v3))

[awakeOnKer(c:SetIntersection, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in 
      (if (idx = 1)
          for val in getDomainKernel(s1) 
             (if isInDomainKernel(s2,val) 
                 addToKernel(s3,val,c.idx3),
              if not(isInDomainEnveloppe(s3,val))
                 remFromEnveloppe(s2,val,c.idx2))
       else if (idx = 2)
          for val in getDomainKernel(s2) 
             (if isInDomainKernel(s1,val) 
                 addToKernel(s3,val,c.idx3),
              if not(isInDomainEnveloppe(s3,val))
                 remFromEnveloppe(s1,val,c.idx2))     
       else // if (idx = 3)
          for val in getDomainKernel(s3) 
              (if not(isInDomainKernel(s1,val)) addToKernel(s1,val,c.idx1),
               if not(isInDomainKernel(s2,val)) addToKernel(s2,val,c.idx2))
       )]

[awakeOnEnv(c:SetIntersection, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in 
      (if (idx = 1)
          for val in getDomainEnveloppe(s3) 
             (if not(isInDomainEnveloppe(s1,val))
                 remFromEnveloppe(s3,val,c.idx3))
       else if (idx = 2)                 
          for val in getDomainEnveloppe(s3) 
             (if not(isInDomainEnveloppe(s2,val))
                 remFromEnveloppe(s3,val,c.idx3))
       else // if (idx = 3)
         (for val in getDomainKernel(s1) 
              (if not(isInDomainEnveloppe(s3,val)) remFromEnveloppe(s2,val,c.idx2)),
          for val in getDomainKernel(s2) 
              (if not(isInDomainEnveloppe(s3,val)) remFromEnveloppe(s1,val,c.idx1)) )
       )]
       
[awakeOnInstSet(c:SetIntersection, idx:integer) : void
 -> awakeOnKer(c,idx),
    awakeOnEnv(c,idx)]
 
// TODO
;[askIfEntailed(c:SetIntersection) : (boolean U {unknown}) -> unknown] 

[testIfSatisfied(c:SetIntersection) :  boolean
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3, allIn := true, noneOut := true in 
       (for val in getDomainKernel(s3)
           (if (not(isInDomainKernel(s2,val)) | not(isInDomainKernel(s1,val)))
               (allIn := false, break())),
        if not(allIn) false
        else (for val in getDomainKernel(s2)
                 (if (not(isInDomainKernel(s3,val)) & isInDomainKernel(s1,val))
                     (noneOut := false, break())),
              noneOut) )] 
[propagate(c:SetIntersection) : void  
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in
   (for val in getDomainKernel(s1) 
      (if isInDomainKernel(s2,val) 
          addToKernel(s3,val,c.idx3),
       if not(isInDomainEnveloppe(s3,val))
          remFromEnveloppe(s2,val,c.idx2)),
    for val in getDomainKernel(s2) 
      (if isInDomainKernel(s1,val) 
          addToKernel(s3,val,c.idx3),
       if not(isInDomainEnveloppe(s3,val))
          remFromEnveloppe(s1,val,c.idx2)),     
    for val in getDomainKernel(s3) 
      (if not(isInDomainKernel(s1,val)) addToKernel(s1,val,c.idx1),
       if not(isInDomainKernel(s2,val)) addToKernel(s2,val,c.idx2)),
    for val in getDomainEnveloppe(s3) 
      (if not(isInDomainEnveloppe(s1,val))
          remFromEnveloppe(s3,val,c.idx3),
       if not(isInDomainEnveloppe(s2,val))
          remFromEnveloppe(s3,val,c.idx3)) )]     
[awake(c:SetIntersection) : void -> propagate(c)]
[choco/setintersection(s1:SetVar, s2:SetVar, s3:SetVar) : SetIntersection
  -> SetIntersection(sv1 = s1, sv2 = s2, sv3 = s3)]

// A constraint stating that a set is the intersection of two other sets
choco/SetUnion <: TernSetConstraint()

// There are seven propagation rules for the constraint sv3 = setunion(sv1, sv2)
//    Ker(v3) contains Ker(v1)
//    Ker(v3) contains Ker(v2)
//    Ker(v1) contains Ker(v3) inter Complement(Env(v2))
//    Ker(v2) contains Ker(v3) inter Complement(Env(v1))
//    Env(v1) disjoint Complement(Env(v3))
//    Env(v2) disjoint Complement(Env(v3))
//    Env(v3) disjoint Complement(Env(v1)) inter Complement(Env(v2))

[awakeOnKer(c:SetUnion, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in 
      (if (idx = 1)
          for val in getDomainKernel(s1) 
              addToKernel(s3,val,c.idx3)
       else if (idx = 2)
          for val in getDomainKernel(s2) 
              addToKernel(s3,val,c.idx3)
       else // if (idx = 3)
          for val in getDomainKernel(s3) 
              (if not(isInDomainEnveloppe(s2,val)) addToKernel(s1,val,c.idx1),
               if not(isInDomainEnveloppe(s1,val)) addToKernel(s2,val,c.idx2)) )]

[awakeOnEnv(c:SetUnion, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in 
      (if (idx = 1)
          (for val in getDomainKernel(s3) 
              (if not(isInDomainEnveloppe(s1,val)) 
                  addToKernel(s2,val,c.idx2)),
           for val in getDomainEnveloppe(s3) 
              (if (not(isInDomainEnveloppe(s1,val)) & not(isInDomainEnveloppe(s2,val)))
                  remFromEnveloppe(s3,val,c.idx3)) )
       else if (idx = 2)                 
          (for val in getDomainKernel(s3) 
              (if not(isInDomainEnveloppe(s2,val)) 
                  addToKernel(s1,val,c.idx1)),
           for val in getDomainEnveloppe(s3) 
              (if (not(isInDomainEnveloppe(s1,val)) & not(isInDomainEnveloppe(s2,val)))
                  remFromEnveloppe(s3,val,c.idx3)) )
       else // if (idx = 3)
          (for val in getDomainEnveloppe(s1) 
              (if not(isInDomainEnveloppe(s3,val))
                 remFromEnveloppe(s1,val,c.idx1)),
           for val in getDomainEnveloppe(s2) 
              (if not(isInDomainEnveloppe(s3,val))
                 remFromEnveloppe(s2,val,c.idx2)) ) )]                                                                      
[awakeOnInstSet(c:SetUnion, idx:integer) : void
 -> awakeOnKer(c,idx),
    awakeOnEnv(c,idx)]
 
// TODO
;[askIfEntailed(c:SetUnion) : (boolean U {unknown}) -> unknown] 

[testIfSatisfied(c:SetUnion) : boolean
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3, allIn := true, noneOut := true in 
       (for val in getDomainKernel(s3)
           (if (not(isInDomainKernel(s2,val)) & not(isInDomainKernel(s1,val)))
               (allIn := false, break())),
        if not(allIn) false
        else (for val in getDomainKernel(s1)
                 (if not(isInDomainKernel(s3,val))
                    (noneOut := false, break())),
              if noneOut
                for val in getDomainKernel(s1)
                 (if not(isInDomainKernel(s3,val))
                    (noneOut := false, break())),
              noneOut) )] 
[propagate(c:SetUnion) : void  
 -> let s1 := c.sv1, s2 := c.sv2, s3 := c.sv3 in 
      (for val in getDomainKernel(s1) 
           addToKernel(s3,val,c.idx3),
       for val in getDomainKernel(s2) 
           addToKernel(s3,val,c.idx3),
       for val in getDomainKernel(s3) 
          (if not(isInDomainEnveloppe(s2,val)) addToKernel(s1,val,c.idx1),
           if not(isInDomainEnveloppe(s1,val)) addToKernel(s2,val,c.idx2)),
       for val in getDomainEnveloppe(s3) 
          (if (not(isInDomainEnveloppe(s1,val)) & not(isInDomainEnveloppe(s2,val)))
              remFromEnveloppe(s3,val,c.idx3)),
       for val in getDomainEnveloppe(s1) 
          (if not(isInDomainEnveloppe(s3,val))
              remFromEnveloppe(s1,val,c.idx1)),
      for val in getDomainEnveloppe(s2) 
         (if not(isInDomainEnveloppe(s3,val))
             remFromEnveloppe(s2,val,c.idx2)) )]
[awake(c:SetUnion) : void -> propagate(c)]
[choco/setunion(s1:SetVar, s2:SetVar, s3:SetVar) : SetUnion
  -> SetUnion(sv1 = s1, sv2 = s2, sv3 = s3)]

// ********************************************************************
// *   Part 6: bin comparisons (subset/disjoint/overlap)              *
// ********************************************************************

// A constraint stating that a set is included (may be equal) into anotherone
choco/SubSet <: BinSetConstraint()

// There are seven propagation rules for the constraint subset(sv1, sv2)
//    Ker(v2) contains Ker(v1)
//    Env(v1) disjoint Complement(Env(v2))
[awakeOnKer(c:SubSet, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2 in 
      (if (idx = 1)
          for val in getDomainKernel(s1) 
              addToKernel(s2,val,c.idx2) )]
[awakeOnEnv(c:SubSet, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2 in 
      (if (idx = 2)
          for val in getDomainEnveloppe(s1) 
             (if not(isInDomainEnveloppe(s2,val))
                 remFromEnveloppe(s1,val,c.idx1)) )]
[awakeOnInstSet(c:SubSet, idx:integer) : void
 -> awakeOnKer(c,idx),
    awakeOnEnv(c,idx)]
 
[askIfEntailed(c:SubSet) : (boolean U {unknown})
 -> let s1 := c.sv1, s2 := c.sv2, allPossibleIn := true, someSureOut  := false in 
       (for val in getDomainEnveloppe(s1)
           (if not(isInDomainKernel(s2,val))
              (if (isInDomainKernel(s1,val) & not(isInDomainEnveloppe(s2,val)))
                  someSureOut := true,
               allPossibleIn := false)),
        if allPossibleIn    true
        else if someSureOut false
        else unknown)] 

[testIfSatisfied(c:SubSet) : boolean
 -> let s1 := c.sv1, s2 := c.sv2, allIn := true in 
       (for val in getDomainKernel(s1)
           (if not(isInDomainKernel(s2,val))
               (allIn := false, break())),
        allIn)] 

[propagate(c:SubSet) : void  
 -> let s1 := c.sv1, s2 := c.sv2 in
       (for val in getDomainKernel(s1) 
            addToKernel(s2,val,c.idx2),
        for val in getDomainEnveloppe(s1) 
           (if not(isInDomainEnveloppe(s2,val))
               remFromEnveloppe(s1,val,c.idx1)) )]
[awake(c:SubSet) : void -> propagate(c)]
[choco/subset(s1:SetVar, s2:SetVar) : SubSet
  -> SubSet(sv1 = s1, sv2 = s2)]                 
    
// A constraint stating that two sets have no value in common
choco/DisjointSets <: BinSetConstraint()

// There are seven propagation rules for the constraint subset(sv1, sv2)
//    Env(v1) disjoint Complement(Ker(v2))
//    Env(v2) disjoint Complement(Ker(v1))
[awakeOnKer(c:DisjointSets, idx:integer) : void
 -> let s1 := c.sv1, s2 := c.sv2 in 
      (if (idx = 1)
          for val in getDomainKernel(s1) 
              remFromEnveloppe(s2,val,c.idx2)
       else // if (idx = 2)
          for val in getDomainKernel(s2)
              remFromEnveloppe(s1,val,c.idx1) )]
[awakeOnEnv(c:DisjointSets, idx:integer) : void
 -> nil]
[awakeOnInstSet(c:DisjointSets, idx:integer) : void
 -> awakeOnKer(c,idx)]

[askIfEntailed(c:DisjointSets) : (boolean U {unknown})
 -> let s1 := c.sv1, s2 := c.sv2, somePossibleIn := false, someSureIn := false in 
       (for val in getDomainEnveloppe(s1)
           (if isInDomainEnveloppe(s2,val)
               (if (isInDomainKernel(s1,val) & isInDomainKernel(s2,val))
                   (someSureIn := true, break()),
                somePossibleIn := true)),
        if someSureIn false
        else if not(somePossibleIn) true
        else unknown)]       

[testIfSatisfied(c:DisjointSets) : boolean
 -> let s1 := c.sv1, s2 := c.sv2, someIn := false in 
       (for val in getDomainKernel(s1)
           (if isInDomainKernel(s2,val)
               (someIn := true, break())),
        not(someIn))] 
[propagate(c:DisjointSets) : void  
 -> let s1 := c.sv1, s2 := c.sv2 in
       (for val in getDomainKernel(s1) 
            remFromEnveloppe(s2,val,c.idx2),
        for val in getDomainKernel(s2)
            remFromEnveloppe(s1,val,c.idx1) )]
[awake(c:DisjointSets) : void -> propagate(c)]
[choco/disjoint(s1:SetVar, s2:SetVar) : DisjointSets
  -> DisjointSets(sv1 = s1, sv2 = s2)]                    
  
// A constraint stating that two sets have some values in common
choco/OverlappingSets <: BinSetConstraint(
   emptyKernelIntersection:boolean = true)
(store(emptyKernelIntersection))
// There are seven propagation rules for the constraint overlap(sv1, sv2)
//    kernelIntersectionSize = empty(Ker(v1) inter Ker(v2))
//    kernelIntersectionSize > 0 => satisfied
//    Enveloppe(v1) inter Enveloppe(v2) = {a} => a in Ker(v1) inter Ker(v2)
[awakeOnKer(c:OverlappingSets, idx:integer) : void
 -> if c.emptyKernelIntersection
      let s1 := c.sv1, s2 := c.sv2, nb := 0 in 
       (for val in getDomainKernel(s1) 
          (if isInDomainKernel(s2,val)                
             (nb := 1, break())),
        if (nb > 0)
           (c.emptyKernelIntersection := false, setPassive(c)) )]              
              
[awakeOnEnv(c:OverlappingSets, idx:integer) : void
 -> if c.emptyKernelIntersection 
      let s1 := c.sv1, s2 := c.sv2, theval := UNKNOWNINT, nbCandidates := 0 in 
         (for val in getDomainEnveloppe(s1) 
             (if isInDomainEnveloppe(s2,val)
                (nbCandidates :+ 1, theval := val, 
                 if (nbCandidates > 1) break())),
          if (nbCandidates = 1) 
             (c.emptyKernelIntersection := false,
              addToKernel(s1,theval,c.idx1),
              addToKernel(s2,theval,c.idx2),                
              setPassive(c))) ]
[awakeOnInstSet(c:OverlappingSets, idx:integer) : void
 -> awakeOnEnv(c,idx)]

[askIfEntailed(c:OverlappingSets) : (boolean U {unknown})
 -> let s1 := c.sv1, s2 := c.sv2, somePossibleIn := false, someSureIn := false in 
       (for val in getDomainEnveloppe(s1)
           (if isInDomainEnveloppe(s2,val)
               (if (isInDomainKernel(s1,val) & isInDomainKernel(s2,val))
                   (someSureIn := true, break()),
                somePossibleIn := true)),
        if someSureIn true
        else if not(somePossibleIn) false
        else unknown)]       

[testIfSatisfied(c:OverlappingSets) : boolean
 -> let s1 := c.sv1, s2 := c.sv2, someIn := false in 
       (for val in getDomainKernel(s1)
           (if isInDomainKernel(s2,val)
               (someIn := true, break())),
        someIn)] 
[propagate(c:OverlappingSets) : void -> awakeOnEnv(c,1)]
[awake(c:OverlappingSets) : void
 -> let s1 := c.sv1, s2 := c.sv2, nb := 0 in 
       (for val in getDomainKernel(s1) 
          (if isInDomainKernel(s2,val)                
             (nb := 1, break())),
        if (nb > 0)
           (c.emptyKernelIntersection := false, setPassive(c))
        else awakeOnEnv(c,1))]
[choco/overlap(s1:SetVar, s2:SetVar) : OverlappingSets
  -> OverlappingSets(sv1 = s1, sv2 = s2)]                      
  
[choco/opposite(c:DisjointSets) : OverlappingSets -> OverlappingSets(sv1 = c.sv1, sv2 = c.sv2)]
[choco/opposite(c:OverlappingSets) : DisjointSets -> DisjointSets(sv1 = c.sv1, sv2 = c.sv2)]

// TODO: NotSubset, StrictSubset, NotStrictSubset, EqualSets, DifferentSets

// ********************************************************************
// *   Part 7: larger operators                                       *
// ********************************************************************

// TODO: partition, covering (largeunion), largeintersection ???
//       scalar (constant weights) & scalar (variable weights)
  
