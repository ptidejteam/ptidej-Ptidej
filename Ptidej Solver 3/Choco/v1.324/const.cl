// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: const.cl                                                   *
// *    modelling constraints                                         *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 1: AbstractConstraint                                     *
// ********************************************************************


// Abstract class for constraints, no interface methods
// Such a constraint may have IntVar as well as other types of variables
choco/AbstractConstraint <: Ephemeral(
   choco/hook:any,   // v0.35 add a hook for attaching objects to constraints
   // v0.30: abstract class for constraints whose propagation is delayed (events are not handled immediately)
   choco/constAwakeEvent:ConstAwakeEvent,  // v0.9906, used to be on DelayedConstraint
   private/fastDispatchIndex:integer = -1,
   choco/violated:boolean = false)

// abstract classes for small constraints on integer valued variables (IntVar)
//
choco/IntConstraint <: AbstractConstraint(
   cste:integer = 0)

choco/UnIntConstraint <: IntConstraint(
   v1:IntVar,        // the only integer variable involved in the constraint
   idx1:integer = 0)     // constraint c is the (c.idx1)th constraint of its variable c.v1
                     // i.e.    c.v1.constraints[c.idx1] = c
choco/BinIntConstraint <: IntConstraint(
   v1:IntVar, idx1:integer = 0,  // c.v1.constraints[c.idx1] = c
   v2:IntVar, idx2:integer = 0)  // c.v2.constraints[c.idx2] = c

choco/TernIntConstraint <: IntConstraint(
   v1:IntVar, idx1:integer = 0,  // c.v1.constraints[c.idx1] = c
   v2:IntVar, idx2:integer = 0,  // c.v2.constraints[c.idx2] = c
   v3:IntVar, idx3:integer = 0)  // c.v3.constraints[c.idx3] = c
(known!(v1,v2,v3))

choco/LargeIntConstraint <: IntConstraint(
   vars:list<IntVar>,
   indices:list<integer>,
   nbVars:integer = 0)

[choco/closeLargeIntConstraint(c:LargeIntConstraint) : void
 => c.nbVars := length(c.vars),
    c.indices := make_list(c.nbVars,integer,0)]

// This class is an empty shell supporting the delayed propagation of an IntConstraint
choco/Delayer <: IntConstraint(  // v0.9907 the DelayedConstraint class is removed
    priority:integer = 2, // by default, delay constraints to level 2 (ie just above level 1, the default level for all constraints)
    target:IntConstraint)

// v0.30: this is an undocumented API function (not to be used until debugged...)
[choco/delay(c:IntConstraint,p:integer) : Delayer -> Delayer(target = c, priority = p)]
// v0.9907
[choco/self_print(d:Delayer) : void -> printf("delay(~S,~S)",d.target,d.priority)]

// abstract classes for compoosite constraints (constraints made out of
// several (simpler) constraints).
// we provide with a generic mechanism for assigning a unique index to each
// variable in the composite constraint.

// Abstract class for constraint made of several sub-constraints, no interface
// note (0.18): CompositeConstraint could feature a field "indices"
choco/CompositeConstraint <: AbstractConstraint()

choco/BinCompositeConstraint <: CompositeConstraint(
     choco/const1:AbstractConstraint,
     choco/const2:AbstractConstraint,
     choco/offset:integer = 0)
known!(const1,const2)

// claire3 port: change slots to strongly typed lists
choco/LargeCompositeConstraint <: CompositeConstraint(
     choco/lconst:list<AbstractConstraint>,
     choco/loffset:list<integer>,
     choco/nbConst:integer = 0,
     choco/additionalVars:list<IntVar>,      // v1.02: variables that are not in the subconstraints
     choco/additionalIndices:list<integer>)  // v1.02: corresponding constraint indices

choco/getNbVars :: property(range = integer)

// v0.37 public for extensions to CHOCO
[choco/closeCompositeConstraint(c:BinCompositeConstraint) : void
 -> c.offset := getNbVars(c.const1)]
// v1.02 fill the nbConst slot + take the additionalVars slot into account
// claire3 port: strongly typed lists
[choco/closeCompositeConstraint(c:LargeCompositeConstraint) : void
 -> c.nbConst := length(c.lconst), // v1.02
    let nbvars := 0, 
        l := make_list(c.nbConst,integer,0) in
      (for i in (1 .. c.nbConst)
           (nbvars :+ (getNbVars(c.lconst[i]) as integer),
            l[i] := nbvars),
       c.loffset := l,
       c.additionalIndices := make_list(length(c.additionalVars),integer,0),
       assert(nbvars + length(c.additionalVars) = getNbVars(c)))]
// v1.02: new access functions: retrieving the number of variables coming from subconstraints
[choco/getNbVarsFromSubConst(c:LargeCompositeConstraint) : integer
 => last(c.loffset)]

// Methods for connecting constraints

// <fl> 0.36: open-coded method for compatibility with other libraries (eg igloo)
choco/connect :: property(range = void)
choco/disconnect :: property(range = void)
choco/reconnect :: property(range = void)

// private lower-level connection methods
// connecting a constraint to one of its variables, activate connections for bound events, or do both
choco/connectIntVar :: property(range = void)
choco/connectIntVarEvents :: property(range = void)
choco/disconnectIntVar :: property(range = void)
choco/disconnectEvent :: property(range = void)
choco/disconnectIntVarEvents :: property(range = void)
choco/reconnectEvent :: property(range = void)
choco/reconnectIntVarEvents :: property(range = void)
// a constraint is active if it is connected to the network and if it does propagate
choco/isActive :: property(range = boolean)

// v1.0 for the same guy
[choco/connectHook(x:any, c:AbstractConstraint) -> nil]
[choco/reconnectHook(x:any, c:AbstractConstraint) -> nil]
[choco/disconnectHook(x:any, c:AbstractConstraint) -> nil]

// v1.03 needs to be abstract (redefined for "global" constraint inheriting from IntConstraint)
choco/setConstraintIndex :: property(range = void)

// Kind of prefix numbering of all variables in the subconstraints of a composite constraint
//
// In a complex root composite constraint (a syntactic tree where the nodes
// are CompositeConstraint and the leaves are simple AbstractConstraint
// such as UnIntConstraint/BinIntConstraint)
// The assignIndices function associates to each variable involved in a leaf constraint
// a unique index.
// Each internal node of the tree contains an "offset" field which contains the
// largest index among all variables in the left subtree.
//
// v0.30: the second argument of assignIndices can now either be a CompositeConstraint or a Delayer
// v1.02 raise an error + return an integer (for type inference)
[choco/assignIndices(c1:AbstractConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> error("the root definition of assignIndices should not be called (~S,~S,~S)",c1,root,i),1]

[choco/assignIndices(c1:UnIntConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectIntVar(root,c1.v1,j),
        setConstraintIndex(c1,1,length(c1.v1.constraints)), // v1.03
        j)]

[choco/assignIndices(c1:BinIntConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectIntVar(root,c1.v1,j),
        setConstraintIndex(c1,1,length(c1.v1.constraints)), // v1.03
        j :+ 1, connectIntVar(root,c1.v2,j),
        setConstraintIndex(c1,2,length(c1.v2.constraints)), // v1.03
        j)]

// v0.34
[choco/assignIndices(c1:TernIntConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j :+ 1, connectIntVar(root,c1.v1,j),
        setConstraintIndex(c1,1,length(c1.v1.constraints)), // v1.03
        j :+ 1, connectIntVar(root,c1.v2,j),
        setConstraintIndex(c1,2,length(c1.v2.constraints)), // v1.03
        j :+ 1, connectIntVar(root,c1.v3,j),
        setConstraintIndex(c1,3,length(c1.v3.constraints)), // v1.03
        j)]

// v0.15
[choco/assignIndices(c1:LargeIntConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (for k in (1 .. c1.nbVars)
           (j :+ 1, connectIntVar(root,c1.vars[k],j),
            setConstraintIndex(c1,k,length(c1.vars[k].constraints))), // v1.03
        j)]

[choco/assignIndices(c1:BinCompositeConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (j := assignIndices(c1.const1, root, j) as integer,
        assert(c1.offset = j - i), // v0.37 (c1.offset is now filled earlier by closeCompositeConstraint)
        j := assignIndices(c1.const2, root, j) as integer,
        j)]

[choco/assignIndices(c1:LargeCompositeConstraint, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (for constIdx in (1 .. c1.nbConst)
          let subc := c1.lconst[constIdx] in
             (j := (assignIndices(subc,root,j) as integer),  // <hha> 0.20
              assert(c1.loffset[constIdx] = j - i)), // v0.37 (c1.offset is now filled earlier by closeCompositeConstraint)
        assert(length(c1.additionalVars) = length(c1.additionalIndices)),
        for k in (1 .. length(c1.additionalVars))    // v1.02: handle the new additional* slots
          (j :+ 1, 
           connectIntVar(root,c1.additionalVars[k],j), 
           c1.additionalIndices[k] := length(c1.additionalVars[k].constraints)),
        j)]

// v0.32 this new function is required since the introduction of indexInOpposite.
// v1.02 raise an error
[choco/setConstraintIndex(c:AbstractConstraint, i:integer, val:integer) : void
 -> error("the root definition of setConstraintIndex (~S,~S,~S) should not be called",c,i,val)]
// let v be the i-th var of c, records that c is the n-th constraint involving v
[choco/setConstraintIndex(c:UnIntConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[choco/setConstraintIndex(c:BinIntConstraint, i:integer, val:integer) : void
 -> if (i = 1) c.idx1 := val
    else if (i = 2) c.idx2 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[choco/setConstraintIndex(c:TernIntConstraint, i:integer, val:integer) : void // v0.34
 -> if (i = 1) c.idx1 := val
    else if (i = 2) c.idx2 := val
    else if (i = 3) c.idx3 := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[choco/setConstraintIndex(c:LargeIntConstraint, i:integer, val:integer) : void
 -> if (i <= length(c.indices)) c.indices[i] := val
    else error("impossible to copy index ~S on ~S as ~S",i,c,val)]
[choco/setConstraintIndex(c:BinCompositeConstraint, i:integer, val:integer) : void
 -> if (i <= c.offset) setConstraintIndex(c.const1,i,val)
    else setConstraintIndex(c.const2,i - c.offset,val)]
// v1.02 raise an error when the index is too large
[choco/setConstraintIndex(c:LargeCompositeConstraint, i:integer, val:integer) : void
 -> when constIdx := some(i2 in (1 .. c.nbConst) | c.loffset[i2] >= i) in
      let realIdx := (if (constIdx = 1) i else i - c.loffset[constIdx - 1]) in
          setConstraintIndex(c.lconst[constIdx],realIdx,val)
    else let realIdx := i - last(c.loffset) in // v1.02 handle the new additional* slots
      (if (realIdx <= length(c.additionalVars))
          c.additionalIndices[realIdx] := val
       else error("index ~S above largest var-index in constraint ~S",i,c))]
[choco/setConstraintIndex(c:Delayer, i:integer, val:integer) : void // v1.03
 -> setConstraintIndex(c.target,i,val)]

// v0.32 among all constraints linked to the idx-th variable of c,
// find the index of constraint c
// v1.02 raises an error + return an int (for type inference)
[choco/getConstraintIdx(c:AbstractConstraint, idx:integer) : integer
 -> error("the root definition of getConstraintIdx (~S,~S) should not be called",c,idx),0]
[choco/getConstraintIdx(c:UnIntConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else (error("impossible to get ~S-th index of ~S",idx,c), 0)]
[choco/getConstraintIdx(c:BinIntConstraint, idx:integer) : integer
 -> if (idx = 1) c.idx1
    else if (idx = 2) c.idx2
    else (error("impossible to get ~S-th index of ~S",idx,c), 0)]
[choco/getConstraintIdx(c:TernIntConstraint, idx:integer) : integer // v0.34
 -> if (idx = 1) c.idx1
    else if (idx = 2) c.idx2
    else if (idx = 3) c.idx3
    else (error("impossible to get ~S-th index of ~S",idx,c), 0)]
[choco/getConstraintIdx(c:LargeIntConstraint, idx:integer) : integer
 -> if (idx <= length(c.indices)) c.indices[idx]
    else (error("impossible to get ~S-th index of ~S",idx,c), 0)]
[choco/getConstraintIdx(c:BinCompositeConstraint, idx:integer) : integer
 -> if (idx <= c.offset) getConstraintIdx(c.const1,idx)
    else getConstraintIdx(c.const2,idx - c.offset)]
// v1.02 raise an error when the index is too large
[choco/getConstraintIdx(c:LargeCompositeConstraint, idx:integer) : integer
 -> when constIdx := some(i in (1 .. c.nbConst) | c.loffset[i] >= idx) in
      let realIdx := (if (constIdx = 1) idx else idx - c.loffset[constIdx - 1]) in
          getConstraintIdx(c.lconst[constIdx],realIdx)
    else let realIdx := idx - last(c.loffset) in // v1.02 handle the new additional* slots
      (if (realIdx <= length(c.additionalVars))
          c.additionalIndices[realIdx]
       else (error("index ~S above largest var-index in constraint ~S",idx,c), 0))]
// v1.02 <naren>
[choco/getConstraintIdx(c:Delayer,i:integer) : integer
 -> getConstraintIdx(c.target,i)]   

// Constraint interface

// The public methods on the constraints are the following
choco/doPropagate :: property(range = void) // v1.02
choco/doAwake :: property(range = void)
choco/doAwakeOnVar :: property(range = void)
choco/askIfTrue :: property(range = (boolean U {unknown}))
choco/doAwakeOnInf :: property(range = void)
choco/doAwakeOnSup :: property(range = void)
choco/doAwakeOnInst :: property(range = void)
choco/doAwakeOnRem :: property(range = void)
choco/testIfTrue :: property(range = boolean)
choco/testIfInstantiated :: property(range = boolean)
choco/getPriority :: property(range = integer)
choco/getVar :: property(range = AbstractVar) // v1.04

choco/propagate :: property(range = void) // v1.04
choco/awake :: property(range = void)
choco/awakeOnInf :: property(range = void)
choco/awakeOnSup :: property(range = void)
choco/awakeOnInst :: property(range = void)
choco/awakeOnRem :: property(range = void)
choco/awakeOnVar :: property(range = void)
choco/awakeOnKer :: property(range = void)
choco/awakeOnEnv :: property(range = void)
choco/awakeOnInstSet :: property(range = void)
choco/askIfEntailed :: property(range = (boolean U {unknown}))
choco/testIfSatisfied :: property(range = boolean)
choco/testIfCompletelyInstantiated :: property(range = boolean)

// choco/doAwake(c:AbstractConstraint) : void
//    implements a fast call to awake(c)
// choco/doAwakeOnInf(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnInf(c,idx)
// choco/doAwakeOnSup(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnSup(c,idx)
// choco/doAwakeOnInst(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnInst(c,idx)
// choco/doAwakeOnRem(c:AbstractConstraint, idx:integer, x:integer) : void
//    implements a fast call to awakeOnRem(c,idx, x)
// choco/doAwakeOnVar(c:AbstractConstraint,idx) : void
//    implements a fast call to awakeOnVar(c,idx)
// choco/askIfTrue(c:AbstractConstraint) : (boolean U {unknown})
//    implements a fast call to askIfEntailed(c)
// choco/testIfTrue(c:AbstractConstraint) : boolean
//    implements a fast call to testIfSatisfied(c)
// choco/testIfInstantiated(c:AbstractConstraint) : boolean
//    implements a fast call to testIfCompletelyInstantiated(c)

// for each constraint the following method must be defined
// v0.9907: accessing the priority of a constraint
[choco/getPriority(c:AbstractConstraint) : integer -> 1]
[choco/getPriority(c:Delayer) : integer => c.priority]

// v1.04
[choco/testIfCompletelyInstantiated(c:IntConstraint) : boolean
 -> let n := getNbVars(c) in
       forall(i in (1 .. n) | isInstantiated(getVar(c,i)))]
[choco/testIfCompletelyInstantiated(c:UnIntConstraint) : boolean
 -> isInstantiated(c.v1)]
[choco/testIfCompletelyInstantiated(c:BinIntConstraint) : boolean
 -> (isInstantiated(c.v1) & isInstantiated(c.v2))]
[choco/testIfCompletelyInstantiated(c:TernIntConstraint) : boolean  // v0.34
 -> (isInstantiated(c.v1) & isInstantiated(c.v2) & isInstantiated(c.v3))]
[choco/testIfCompletelyInstantiated(c:LargeIntConstraint) : boolean  // v0.15
 -> forall(v in c.vars | isInstantiated(v))]
[choco/testIfCompletelyInstantiated(c:BinCompositeConstraint) : boolean
 -> (testIfInstantiated(c.const1) & testIfInstantiated(c.const2) )]
[choco/testIfCompletelyInstantiated(c:LargeCompositeConstraint) : boolean
 -> (forall(subc in c.lconst | testIfInstantiated(subc)) &
     forall(v in c.additionalVars | isInstantiated(v)) )]  // v1.02

// v0.29: counting the number of variables involved in a constraint
[choco/getNbVars(c:AbstractConstraint) : integer -> error("getNbVars has not yet been defined on ~S, it should be !",c),0]
[choco/getNbVars(c:UnIntConstraint) : integer -> 1]
[choco/getNbVars(c:BinIntConstraint) : integer -> 2]
[choco/getNbVars(c:TernIntConstraint) : integer -> 3]
[choco/getNbVars(c:LargeIntConstraint) : integer
 -> assert(c.nbVars = length(c.vars)), // <thb> v0.36
    c.nbVars]
[choco/getNbVars(c:LargeCompositeConstraint) : integer
 -> if (c.nbConst = 0) 0 // v1.011 <thb>
    else (assert(c.loffset[c.nbConst] = sum(list{getNbVars(subc) | subc in c.lconst})),   // v1.02 sum vs. Sum
          c.loffset[c.nbConst] + length(c.additionalVars))] // v1.02
[choco/getNbVars(c:BinCompositeConstraint) : integer
 -> assert(c.offset + getNbVars(c.const2) = getNbVars(c.const1) + getNbVars(c.const2)),
    c.offset + getNbVars(c.const2)]

// accessing the ith variable of a constraint
[choco/getVar(c:AbstractConstraint, i:integer) : AbstractVar
 -> error("getVar has not yet been defined on ~S, it should be !",c), IntVar()]
[choco/getVar(c:UnIntConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.v1 else error("wrong var index (~S) for ~S",i,c), c.v1]
[choco/getVar(c:BinIntConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.v1
    else if (i = 2) c.v2
    else (error("wrong var index (~S) for ~S",i,c), c.v1)]
[choco/getVar(c:TernIntConstraint, i:integer) : AbstractVar
 -> if (i = 1) c.v1
    else if (i = 2) c.v2
    else if (i = 3) c.v3
    else (error("wrong var index (~S) for ~S",i,c), c.v1)]
[choco/getVar(c:LargeIntConstraint, i:integer) : AbstractVar
 -> if (i % (1 .. c.nbVars)) c.vars[i]
    else (error("wrong var index (~S) for ~S",i,c), c.vars[1])]
[choco/getVar(c:LargeCompositeConstraint, i:integer) : AbstractVar
 -> when constIdx := some(i0 in (1 .. c.nbConst) | c.loffset[i0] >= i) in
      let realIdx := (if (constIdx = 1) i else i - c.loffset[constIdx - 1]) in
         (getVar(c.lconst[constIdx],realIdx) as IntVar)
    else let realIdx := i - last(c.loffset) in // v1.02 handle the new additional* slots
      (if (realIdx <= length(c.additionalVars))
          (c.additionalVars[realIdx] as IntVar)
       else (error("wrong var index (~S) for ~S",i,c), getVar(c.lconst[1],1) as IntVar))]
[choco/getVar(c:BinCompositeConstraint, i:integer) : AbstractVar
 -> if (i <= c.offset) (getVar(c.const1,i) as IntVar)
    else (getVar(c.const2, i - c.offset) as IntVar)]

// for each constraint C that involves IntVar (therefore, all IntConstraint,
// some GlobalConstraint and some CompositeConstraint), we implement
// propagation by defining
//   - a method awakeOnInf(C,i) saying how C reacts
//              to an increase of the inf of its ith variable
//   - a method awakeOnSup(C,i) saying how C reacts
//              to a decrease of the sup of its ith variable
//   - a method awakeOnInst(C,i) saying how C reacts
//              when its ith variable is assigned a value
//   - a method awakeOnRem(C,i,x) saying how C reacts
//              when its ith variable has lost the value x
//   - a method askIfEntailed(C) saying whether one can infer
//              that C is true, false or not.
//   - a method testIfSatisfied(C) testing whether C is true or not
//              This is called only when all variables are instantiated
// The constraint may in addition refine the following definitions:
//   - a method testIfCompletelyInstantiated(C) testing
//              whether all variables in C are instantiated
//   - a method getNbVars(C) indicating the number of variables involved in constraint
//   - a method onePropagation(C) performing one pass of propagation on C.
//     However, unlike awake, it may not reach saturation (the fix-point of complete propagation)
//     Therefore, it returns a boolean indicating whether additional calls are needed
//     to reach it.

// a few default definitions
[choco/disconnect(c:AbstractConstraint) : void -> error("disconnect has not yet been defined on ~S, it should be !",c)]
[choco/connect(c:AbstractConstraint) : void -> error("connect has not yet been defined on ~S, it should be !",c)]

[choco/opposite(c:AbstractConstraint) : AbstractConstraint
 -> error("opposite has not yet been defined on ~S, it should be !",c), c]
[choco/askIfEntailed(c:AbstractConstraint) : (boolean U {unknown})
-> (if testIfCompletelyInstantiated(c) testIfTrue(c) else unknown)] //v0.93
// v1.04: this is the function that must be implemented on all constraint classes
// TODO: note that this definition is on Ephemeral, while it should be on AbstractConstraint. 
// This is because of the definition of propagate@Problem. This should be fixed (rename propagate/awake into awake/awakeAtFirst)
[choco/propagate(c:Ephemeral) : void
 -> error("the propagate method has not been implemented on ~S",c)]
// v1.05 <brg> <thb> new default definitions:
// forget the variable on which the event (domain update) has occurred and call the basic propagation function
[choco/awakeOnInf(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]
[choco/awakeOnSup(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]
[choco/awakeOnInst(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]
[choco/awakeOnRem(c:AbstractConstraint, idx:integer, x:integer) : void -> doPropagate(c)]
[choco/awakeOnVar(c:AbstractConstraint, idx:integer) : void -> doPropagate(c)]
[choco/awake(c:AbstractConstraint) : void -> doPropagate(c)]
[choco/awakeOnKer(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]
[choco/awakeOnEnv(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]
[choco/awakeOnInstSet(c:AbstractConstraint,idx:integer) : void -> doPropagate(c)]

[choco/testIfSatisfied(c:AbstractConstraint) : boolean
 -> error("the feasibility test has not been implemented on ~S",c), false]

[choco/testIfCompletelyInstantiated(c:AbstractConstraint) : boolean
 -> error("the instantiation test has not been implemented on ~S",c), false]

(interface(getNbVars),
; interface(assignIndices), BUG (because of a union as second argument ?)
 interface(getConstraintIdx),
 interface(askIfEntailed),
 interface(testIfSatisfied), interface(testIfCompletelyInstantiated),
 interface(propagate),
 interface(opposite),
 interface(awakeOnVar), interface(awakeOnInf),
 interface(awakeOnSup), interface(awakeOnInst), interface(awakeOnRem))
// claire3 port
// The interface of the AbstractConstraint class
(interface(AbstractConstraint,
// Interface methods for handling the constraint network
                getNbVars,
;                getVar,
                assignIndices,
                getConstraintIdx,
                setConstraintIndex,
;                connect,           // bug to have connect in interface & abstract at the same time
;                disconnect,        // idem
;                reconnect,         // idem
;                isActive,          // idem
// Propagation related interfaces that are positionned on AbstractConstraint
                askIfEntailed,
                testIfSatisfied,
                testIfCompletelyInstantiated,
;                awake,              // idem
                propagate,
                opposite,
                awakeOnVar,
// Note:such an interface should rather be placed on
//       INTCONST :: (IntConstraint U BinCompositeIntConst U LargeCompositeIntConst U DelayedConstraint)
                awakeOnInf,
                awakeOnSup,
                awakeOnInst,
                awakeOnRem
                ))

// v0.38: the needToAwake field moves from DelayedConstraint to Delayer
// and is replaced by an interface function noNeedToAwake on rhe DelayedConstraint class
// v0.9906: moved code for Delayer to iprop.cl
// v0.9907: remove the DelayedConstraint class

// Note: the three next definitions are usually not optimal (redundant propagation)
// and should therefore preferably be redefined for subclasses
[choco/propagate(c:UnIntConstraint) : void
 -> doAwakeOnInf(c,1), doAwakeOnSup(c,1)]

[choco/propagate(c:BinIntConstraint) : void
 -> doAwakeOnInf(c,1), doAwakeOnInf(c,2),
    doAwakeOnSup(c,1), doAwakeOnSup(c,2)]

[choco/propagate(c:LargeIntConstraint) : void  // v0.15
 -> for i in (1 .. c.nbVars)
     (doAwakeOnInf(c,i), doAwakeOnSup(c,i))]

// Note: Within the layered propagation framework, this function must return a Boolean
// indicating whether some events have been popped for this constraint or not.
//  - Whenever it returns no, a fixpoint has been reached
//  - Otherwise, one propagation pass was performed and the function should be called
//    a second time to check whether some new events have been produced.
// V0.30, 0.33 <thb>

// ********************************************************************
// *   Part 7: compiler optimization                                  *
// ********************************************************************

// Dispatching the virtual methods
//  (trying to improve over the std Claire dispatch)

// claire3 port: remove DispatchIndexValue / function arrays
// claire3 port: remove that ugly ptach for fast dispatch of the main methods
// (no more register)
;doPropagate :: propagate
;doAwake :: awake
;doAwakeOnInf :: awakeOnInf
;doAwakeOnSup :: awakeOnSup
;doAwakeOnInst :: awakeOnInst
;doAwakeOnRem :: awakeOnRem
;doAwakeOnVar :: awakeOnVar

[choco/doAwake(c:AbstractConstraint) : void
 => awake(c)]
[choco/doPropagate(c:AbstractConstraint) : void
 => propagate(c)]
[choco/doAwakeOnInf(c:AbstractConstraint, idx:integer) : void
 => awakeOnInf(c,idx) ]
[choco/doAwakeOnSup(c:AbstractConstraint, idx:integer) : void
 => awakeOnSup(c,idx) ]
[choco/doAwakeOnInst(c:AbstractConstraint, idx:integer) : void
 => awakeOnInst(c,idx) ]
[choco/doAwakeOnKer(c:AbstractConstraint, idx:integer) : void
 => awakeOnKer(c,idx) ]
[choco/doAwakeOnEnv(c:AbstractConstraint, idx:integer) : void
 => awakeOnEnv(c,idx) ]
[choco/doAwakeOnInstSet(c:AbstractConstraint, idx:integer) : void
 => awakeOnInstSet(c,idx) ]
[choco/doAwakeOnRem(c:AbstractConstraint, idx:integer, x:integer) : void
 => awakeOnRem(c,idx, x) ]
[choco/doAwakeOnVar(c:AbstractConstraint, idx:integer) : void
 => awakeOnVar(c,idx) ]
[choco/askIfTrue(c:AbstractConstraint) : (boolean U {unknown})
 => askIfEntailed(c) ]
[choco/testIfTrue(c:AbstractConstraint) : boolean
 => testIfSatisfied(c) ]
[choco/testIfInstantiated(c:AbstractConstraint) : boolean
 => testIfCompletelyInstantiated(c) ]

// ********************************************************************
// *   Part 7: delayed constraints layered propagation                *
// ********************************************************************

// v0.30 : abstract events for delayed propagation

// The four following methods make any constraint "delayable".
// Returns true when the event is relevant (ie: the constraint will definitely
// need to be waken to react to that abstract event)
// Constraints considering specific ways of abstracting an event
// (more specific abstract events than the generic "something has changed")
// must refine these methods. This is for instance the case with LinComb
// that implements two flags improvedUpperBound & improvedLowerBound.
[choco/abstractIncInf(c:AbstractConstraint,i:integer) : boolean -> true]
[choco/abstractDecSup(c:AbstractConstraint,i:integer) : boolean -> true]
[choco/abstractInstantiate(c:AbstractConstraint,i:integer) : boolean -> true]
[choco/abstractRemoveVal(c:AbstractConstraint,i:integer,x:integer) : boolean -> true]

// The Delayer class, calls abstractXxx on its target when an event occur
// v0.33 <thb> always call abstractXxx (for side effexts on the target, like setting the
//    improvedUpperBound and improvedLowerBound flags on a LinComb), then update needToAwake
// v0.9907: no more needToAwake flag: replaced by the presence/absence of the ConstAwakeEvent in the queue
[choco/awakeOnInf(d:Delayer, idx:integer) : void
 -> (if abstractIncInf(d.target,idx) constAwake(d,false))]
[choco/awakeOnSup(d:Delayer, idx:integer) : void
 -> (if abstractDecSup(d.target,idx) constAwake(d,false))]
[choco/awakeOnInst(d:Delayer, idx:integer) : void
 -> (if abstractInstantiate(d.target,idx) constAwake(d,false))]
[choco/awakeOnRem(d:Delayer, idx:integer, x:integer) : void
 -> (if abstractRemoveVal(d.target,idx,x) constAwake(d,false))]

// The Delayer class delegates others method to its target constraint (decorator pattern)
[choco/testIfSatisfied(c:Delayer) : boolean -> testIfTrue(c.target)]
[choco/testIfCompletelyInstantiated(c:Delayer) : boolean -> testIfInstantiated(c.target)]
[choco/getNbVars(c:Delayer) : integer -> getNbVars(c.target)]
[choco/askIfEntailed(c:Delayer) : (boolean U {unknown}) -> askIfTrue(c.target)]

// v0.30 Delayed constraint layered propagation  (code adapted from what was part 6 of iprop.cl)
// delegation of the onePropagation method for a Delayer
//
// <thb> v0.31: this function returns a Boolean indicating whether the propagation actually
// did something or not (generated propagation events).
// v0.9906: this function now returns void
[choco/propagate(c:Delayer) : void -> propagate(c.target)]  // v0.9907
[choco/awake(c:Delayer) : void -> doAwake(c.target)]
// v1.02 modified range to IntVar (more homogeneous)
[choco/getVar(c:Delayer,i:integer) : AbstractVar -> getVar(c.target,i)]   

// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(Delayer))
// ********************************************************************
// *   Part 10: constraint networks                                   *
// ********************************************************************

// Building the constraint network
// 1. Adding an integer variable
[choco/addIntVar(p:Problem,v:IntVar) : void
 -> p.vars :add v,
    v.problem := p,   // v0.93
    if (length(p.vars) = (p.propagationEngine.maxSize + 1))  // v0.28: size vs. length
       printf("Watchout: the problem size is too small: risk of event queue overflows") ]

// 2. connecting constraints and variables

// *** Part 10a: connecting a constraint ******************************

// This function connects a constraint with its variables in several ways.
// Note that connect and connect may only be called while a constraint
// has been fully created and is being posted to a problem !
// Note that it should be called only once per constraint !
// (Note: these functions are redefined on GlobalConstraint (LinComb, Matching, ...)
// v1.0: inform the hook of the connection (useful architecture for Palm explanation)
// v1.03 definition on the abstract class <ega>
[choco/connect(c:IntConstraint) : void
 -> let n := getNbVars(c) in
      (for i in (1 .. n)
         connectIntVar(c,getVar(c,i) as IntVar,i), if known?(hook,c) connectHook(c.hook, c))]
[choco/connect(c:UnIntConstraint) : void
 -> connectIntVar(c,c.v1,1), if known?(hook,c) connectHook(c.hook, c)]
[choco/connect(c:BinIntConstraint) : void
 -> connectIntVar(c,c.v1,1), connectIntVar(c,c.v2,2), if known?(hook,c) connectHook(c.hook, c)]
[choco/connect(c:TernIntConstraint) : void
 -> connectIntVar(c,c.v1,1), connectIntVar(c,c.v2,2), connectIntVar(c,c.v3,3),
    if known?(hook,c) connectHook(c.hook, c)]
[choco/connect(c:LargeIntConstraint) : void
 -> for i in (1 .. c.nbVars) connectIntVar(c,c.vars[i],i),
    if known?(hook,c) connectHook(c.hook, c)]
[choco/connect(c:CompositeConstraint) : void
 -> assignIndices(c,c,0), if known?(hook,c) connectHook(c.hook, c)]
// v0.30: a Delayer is a sort of "UnCompositeConstraint" wrt constraint connection
// v1.02 <naren> call connectHook on target subconstraint
[choco/connect(d:Delayer) : void
 -> assignIndices(d.target,d,0), 
    if known?(hook,d.target) connectHook(d.target.hook, d.target)]

// These connections will be used by the event scheduler.
//   1. for all the concerned variables v, the constraint c is stored in the list
//      of v.constraints (the list of constraints involving v). This allows us to
//      inform all such constraints that an event has occurred on v. Thus, we only
//      store a queue of events associated to a variable rather than a pair (v,c) => shorter queue
//   2. In order for such a list to avoid re-informing an event-generating constraint
//      about its own occurrence, we store a redundant index telling how to retrieve
//      a constraint c in all lists v.constraints for its variables v.
//      (ie, if wake(c1) => event(v) => wake(c) for c in (v.constraints BUT c1)
//      This supports a propagation of c on v by cyclic iteration on (v.constraints but c)
//   3. In order to avoid wakening constraints that are fully satisfied, there is
//      a linked list iteration mechanism on the list v.constraints.
//      Note here, that although the
//      "nextConst" list is a backtrackable list of indices (the index next active constraint
//      in this chained list), unbacktrackable updates are performed on it. Indeed, constraint
//      posting is not backtrackable !
[choco/connectIntVar(cont:AbstractConstraint, u:IntVar, i:integer) : void
 -> connectIntVar(cont,u,i,true,true,true,true)]

[choco/connectIntVar(cont:AbstractConstraint, u:IntVar, i:integer,
                  activeOnInst:boolean,activeOnInf:boolean,
                  activeOnSup:boolean,activeOnRem:boolean) : void
 -> //[5] connectIntVar ~S ~S ~S // cont,u,i,
    u.constraints :add cont, u.indices :add i,
    let n := u.nbConstraints in
       (u.nbConstraints := n + 1,
        setConstraintIndex(cont,i,n + 1)),
    connectIntVarEvents(u,activeOnInst,activeOnInf,activeOnSup,activeOnRem)]

// v1.0 reorganized
choco/connectEvent :: property()
choco/connectIntVarEvents :: property()

[choco/connectIntVarEvents(u:IntVar,
                     activeOnInst:boolean,activeOnInf:boolean,
                     activeOnSup:boolean,activeOnRem:boolean) : void
 -> //[5] connectIntVarEvents for last constraint of ~S: INST:~S, INF:~S, SUP:~S, REM:~S // u,activeOnInst,activeOnInf,activeOnSup,activeOnRem,
    connectEvent(u.updtInfEvt, u.nbConstraints, activeOnInf),
    connectEvent(u.updtSupEvt, u.nbConstraints, activeOnSup),
    connectEvent(u.instantiateEvt, u.nbConstraints, activeOnInst),
    connectEvent(u.remValEvt, u.nbConstraints, activeOnRem) ]

[choco/connectEvent(e:VarEvent, nbconst:integer, active:boolean) : void
 -> if (nbconst > 1)
      let lnext := e.nextConst, j:integer := lnext[nbconst - 1], k := nbconst - 1 in
         (if (j = 0 & active)   // <naren> v0.25: no constraint on u is connected to the constraint network
             lnext :add nbconst //               it will be the only one
          else lnext :add j,    // standard case
          if active
            (while (k > 0 & k >= j & lnext[k] = j) // <naren> v0.25 don't loop with k<j1
                   (lnext[k] := nbconst,           //   (was the case when j1 was the only active constraint)
                    k :- 1) ),
          e.nextConst := lnext)
    // claire3 port typed lists
    else (if active e.nextConst := list<integer>(1)   // simple case: there is only one constraint, v0.26:typo
          else e.nextConst := list<integer>(0))]      // on the variable and it is connected.


// *** Part 10b: disconnecting a constraint ******************************

// v1.0: disconnects the i-th constraint linked to variable u
// v1.01 too many ~S
[choco/disconnectIntVar(u:IntVar, i:integer) : void
 -> //[5] disconnectIntVar ~S // u,
    disconnectIntVarEvents(u,i,true,true,true,true)]

[choco/disconnectIntVarEvents(u:IntVar, i:integer,
                       passiveOnInst:boolean,passiveOnInf:boolean,
                       passiveOnSup:boolean,passiveOnRem:boolean) : void
 -> if passiveOnInst disconnectEvent(u.instantiateEvt,i),
    if passiveOnInf disconnectEvent(u.updtInfEvt,i),
    if passiveOnSup disconnectEvent(u.updtSupEvt,i),
    if passiveOnRem disconnectEvent(u.remValEvt,i)]

// v1.0: disconnects the i-th constraint linked to variable u for the event e
[choco/disconnectEvent(e:VarEvent, i:integer) : void
 -> let lnext := e.nextConst, n := e.modifiedVar.nbConstraints in
       (if (lnext[i] = 0) nil                                           // : the constraint was already entailed
        else if (lnext[i] = i) for k in (1 .. n) store(lnext,k,0,true)  // : it was the only active constraint
        else let j := lnext[i], k := (if (i = 1) n else (i - 1)) in
                (while (lnext[k] = i)
                    (store(lnext,k,j,true),
                     k := (if (k = 1) n else (k - 1)) )))]

[choco/connectSetVar(cont:AbstractConstraint, u:SetVar, i:integer) : void
 -> connectSetVar(cont,u,i,true,true,true)]
[choco/connectSetVar(cont:AbstractConstraint, u:SetVar, i:integer,
                     activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) : void
 -> //[5] connectSetVar ~S ~S ~S // cont,u,i,
    u.constraints :add cont, u.indices :add i,
    let n := u.nbConstraints in
       (u.nbConstraints := n + 1,
        setConstraintIndex(cont,i,n + 1)),
    connectSetVarEvents(u,activeOnInst,activeOnKer,activeOnEnv)]
[choco/connectSetVarEvents(u:SetVar,
                     activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) : void
 -> //[5] connectSetVarEvents for last constraint of ~S: INST:~S, KER:~S, ENV:~S// u,activeOnInst,activeOnKer,activeOnEnv,
    connectEvent(u.updtKerEvt, u.nbConstraints, activeOnKer),
    connectEvent(u.updtEnvEvt, u.nbConstraints, activeOnEnv),
    connectEvent(u.instantiateEvt, u.nbConstraints, activeOnInst)]

[choco/disconnectSetVar(u:SetVar, i:integer) : void
 -> disconnectSetVarEvents(u,i,true,true,true)]
[choco/disconnectSetVarEvents(u:SetVar,i:integer,passiveOnInst:boolean,passiveOnKer:boolean,passiveOnEnv:boolean) : void
 -> //[5] disconnectSetVarEvents for last constraint of ~S: INST:~S, KER:~S, ENV:~S// u,passiveOnInst,passiveOnKer,passiveOnEnv,
    if passiveOnKer  disconnectEvent(u.updtKerEvt, i),
    if passiveOnEnv  disconnectEvent(u.updtEnvEvt, i),
    if passiveOnInst disconnectEvent(u.instantiateEvt, i)]

[choco/reconnectSetVar(u:SetVar, i:integer) : void
 -> reconnectSetVarEvents(u,i,true,true,true)]
[choco/reconnectSetVarEvents(u:SetVar,i:integer,activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) : void
 -> //[5] reconnectSetVarEvents for last constraint of ~S: INST:~S, KER:~S, ENV:~S// u,activeOnInst,activeOnKer,activeOnEnv,
    if activeOnKer  reconnectEvent(u.updtKerEvt, i),
    if activeOnEnv  reconnectEvent(u.updtEnvEvt, i),
    if activeOnInst reconnectEvent(u.instantiateEvt, i)]
                     
// v1.0: inform the hook of the connection (useful architecture for Palm explanation)
[choco/disconnect(c:UnIntConstraint) : void
 -> disconnectIntVar(c.v1, c.idx1), if known?(hook,c) disconnectHook(c.hook, c)]
[choco/disconnect(c:BinIntConstraint) : void
 -> disconnectIntVar(c.v1, c.idx1), disconnectIntVar(c.v2, c.idx2),
    if known?(hook,c) disconnectHook(c.hook, c)]
[choco/disconnect(c:TernIntConstraint) : void
 -> disconnectIntVar(c.v1, c.idx1), disconnectIntVar(c.v2, c.idx2), disconnectIntVar(c.v3, c.idx3),
    if known?(hook,c) disconnectHook(c.hook, c)]
[choco/disconnect(c:LargeIntConstraint) : void
 -> for i in (1 .. c.nbVars) disconnectIntVar(c.vars[i], c.indices[i]),
    if known?(hook,c) disconnectHook(c.hook, c)]
[choco/disconnect(c:Delayer) : void
 -> disconnect(c.target),
    if known?(hook,c) disconnectHook(c.hook, c)]

// *** Part 10c: disconnecting a constraint ******************************

// v1.0: reconnecting a constraint that was temporarily disconnected from the network
[choco/reconnect(c:UnIntConstraint) : void
 -> reconnectIntVar(c.v1,c.idx1), if known?(hook,c) reconnectHook(c.hook, c)]
[choco/reconnect(c:BinIntConstraint) : void
 -> reconnectIntVar(c.v1,c.idx1), reconnectIntVar(c.v2,c.idx2), if known?(hook,c) reconnectHook(c.hook, c)]
[choco/reconnect(c:TernIntConstraint) : void
 -> reconnectIntVar(c.v1,c.idx1), reconnectIntVar(c.v2,c.idx2), reconnectIntVar(c.v3,c.idx3),
    if known?(hook,c) reconnectHook(c.hook, c)]
[choco/reconnect(c:LargeIntConstraint) : void
 -> for i in (1 .. c.nbVars) reconnectIntVar(c.vars[i],c.indices[i]),
    if known?(hook,c) reconnectHook(c.hook, c)]
[choco/reconnect(c:AbstractConstraint) : void
 -> error("TODO: reconnect still not implemented on ~S",c)]


// reconnects the i-th constraint involving variable v
[choco/reconnectIntVar(v:IntVar, idx:integer): void
 -> reconnectIntVarEvents(v,idx,true,true,true,true)]

[choco/reconnectIntVarEvents(u:IntVar,idx: integer,
                     activeOnInst:boolean,activeOnInf:boolean,
                     activeOnSup:boolean,activeOnRem:boolean) : void
 -> //[5] reconnectIntVarEvents for ~S-th constraint of ~S: INST:~S, INF:~S, SUP:~S, REM:~S // idx,u,activeOnInst,activeOnInf,activeOnSup,activeOnRem,
    if activeOnInst reconnectEvent(u.instantiateEvt, idx),
    if activeOnInf reconnectEvent(u.updtInfEvt, idx),
    if activeOnSup reconnectEvent(u.updtSupEvt, idx),
    if activeOnRem reconnectEvent(u.remValEvt, idx) ]

[reconnectEvent(e:VarEvent, idx:integer): void
 -> let lnext := e.nextConst, nbconst := length(lnext),
        nextCI:integer := lnext[idx] in
     (if (nextCI = 0)
         // no connected constraint
	 for i:integer in (1 .. nbconst) // idx is the only connected constraint
             lnext[i] := idx
      else let k1 := (if (idx > 1) idx - 1 else nbconst),
               needToContinue: boolean := true in
           (while (needToContinue)
                  (if (lnext[k1] = nextCI)
                      (lnext[k1] := idx,
                       needToContinue := (k1 != nextCI)) // don't pass accross nextCI while circling backwards
                   else needToContinue := false,
                   k1 := (if (k1 = 1) nbconst else k1 - 1))) )]

// a constraint is active if it is connected to the network and if it does propagate
[choco/isActive(c:AbstractConstraint) : boolean
 -> error("TODO: isActive@AbstractConstraint still not implemented"), true]
[choco/isActive(c:UnIntConstraint) : boolean
 -> isActiveIntVar(c.v1, c.idx1)]
[choco/isActive(c:BinIntConstraint) : boolean
 -> (isActiveIntVar(c.v1, c.idx1) | isActiveIntVar(c.v2, c.idx2))]
[choco/isActive(c:TernIntConstraint) : boolean
 -> (isActiveIntVar(c.v1, c.idx1) | isActiveIntVar(c.v2, c.idx2) | isActiveIntVar(c.v3, c.idx3))]
[choco/isActive(c:LargeIntConstraint) : boolean
 -> exists(i in (1 .. c.nbVars) | isActiveIntVar(c.vars[i], c.indices[i]))]
// v1.02 <naren>
[choco/isActive(d:Delayer) : boolean
 -> isActive(d.target)]
// v1.02 <naren>
[choco/isActive(c: BinCompositeConstraint) : boolean
 -> isActive(c.const1) | isActive(c.const2)]
[choco/isActive(c: LargeCompositeConstraint) : boolean
 -> ( exists(i in (1 .. c.nbConst) | isActive(c.lconst[i])) |
      exists(j in (1 .. length(c.additionalVars)) |   // v1.02
              isActiveIntVar(c.additionalVars[j], c.additionalIndices[j])) )]

[choco/isActiveIntVar(u:IntVar, i:integer) : boolean
 -> (isActiveEvent(u.instantiateEvt,i) |
     isActiveEvent(u.updtInfEvt,i) |
     isActiveEvent(u.updtSupEvt,i) |
     isActiveEvent(u.remValEvt,i))]

[choco/isActiveSetVar(u:SetVar, i:integer) : boolean
 -> (isActiveEvent(u.instantiateEvt,i) |
     isActiveEvent(u.updtKerEvt,i) |
     isActiveEvent(u.updtEnvEvt,i))]

[choco/isActiveEvent(e:VarEvent, i:integer) : boolean
 -> let lnext := e.nextConst, n := length(lnext),
        k := (if (i = 1) n else (i - 1)) in
     (n != 0 & lnext[k] = i)]  // beware, the list may be empty
