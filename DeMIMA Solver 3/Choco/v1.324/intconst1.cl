// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: const.cl                                                   *
// *    propagation of simple constraints (value tuples + linear)     *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 0: Binary relations                                       *
// *   Part 1: Binary constraints as pairs of feasible values         *
// *   Part 2: Constraints as arbitrary lists feasible tuples         *
// *   Part 3: Comparisons as unary constraints                       *
// *   Part 4: Comparisons as binary constraints                      *
// *   Part 5: General linear combinations as global constraints      *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 0: Binary relations                                       *
// ********************************************************************

// Abstract class for encoding relations between values form two domains
//  (maybe, there could be subclasses for dense and sparse matrices)
BinRelation <: Util()
choco/getTruthValue :: property(range = boolean)
[choco/getTruthValue(tt:BinRelation, x:integer, y:integer) : boolean
 -> error("getTruthValue not defined for ~S",tt.isa), true]  
(interface(getTruthValue),
 interface(BinRelation, getTruthValue))

// class with a test method
TruthTest <: BinRelation(
   test:method[range = boolean])  // the predicate defining the constraint
// get
[choco/getTruthValue(tt:TruthTest, x:integer, y:integer) : boolean
 => funcall(tt.test,x,y) as boolean]  
// pretty printing 
[self_print(tt:TruthTest) : void -> printf("<~S>",tt.test.selector)]

// class with an explicit table of allowed pairs
TruthTable2D <: BinRelation(
   offset1:integer = 0,                 // offsets for reading the nbSupport vectors
   offset2:integer = 0,                 //  (the same as in the 2D matrix when the tuples are cached)
   size1:integer = 0,                   // size of the nbSupport vectors
   size2:integer = 0,                   //  (the same as in the 2D matrix when the tuples are cached)
   matrix:BoolMatrix2D)   
// get/set
[choco/getTruthValue(dtt:TruthTable2D, x:integer, y:integer) : boolean
 => read(dtt.matrix,x,y)]  
[choco/setTruePair(dtt:TruthTable2D, x:integer, y:integer) : void
 => store(dtt.matrix,x,y,true)]  
// pretty printing 
[self_print(tt:TruthTable2D) : void -> printf("mat<~Sx~S>",tt.size1,tt.size2)]
      

// ********************************************************************
// *   Part 1: Binary constraints as pairs of feasible values         *
// ********************************************************************

// The abstract class for user-defined constraints and propagated by one of the arc consistency algorithms
CSPBinConstraint <: BinIntConstraint(
   feasRelation:BinRelation,     // encoding of the explicit constraint feasibility relation
   feasiblePair:boolean = true)   // if true: the relation contains feasible pairs, otherwise infeasible ones

[self_print(c:CSPBinConstraint) : void
 -> printf("~S~A(~S,~S)[~S]",c.isa,(if c.feasiblePair "+" else "-"),c.v1,c.v2,c.feasRelation)]

// checking a constraint
[choco/testIfSatisfied(c:CSPBinConstraint) : boolean
 -> getTruthValue(c.feasRelation,c.v1.value,c.v2.value) = c.feasiblePair]

// taking the opposite while sharing the relation object
[choco/opposite(c:CSPBinConstraint) : CSPBinConstraint
 -> let c2 := copy(c) in 
       (c2.feasiblePair := not(c.feasiblePair),
        c2)]

// forward checking propagation: the same code is used for reacting to an instantiation, no matter what AC algorithm is used
[choco/awakeOnInst(c:CSPBinConstraint, idx:integer) : void
 -> let var1 := c.v1, var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair, val := (if (idx = 1) c.v1.value else c.v2.value) in 
     (if (idx = 1)
        for y in list{y in domain(var2) | getTruthValue(feasRel,val,y) != feas}
            removeVal(var2,y,c.idx2)
      else for x in list{x in domain(var1) | getTruthValue(feasRel,x,val) != feas}
               removeVal(var1,x,c.idx1),
     // v1.0: now, the constraint is definitely satisfied (a property of forward checking propagation)                                          
     setPassive(c) )]

// debug        
[see(c:CSPBinConstraint) : void
 -> printf("(~S,~S) % {",c.v1,c.v2),
    for x in domain(c.v1)
      for y in domain(c.v2)
	    (if (getTruthValue(c.feasRelation,x,y) = c.feasiblePair) 
            printf("(~S,~S), ",x,y)),
     printf("}") ]

// ********************************************************************
// *   Part 1a: Binary constraints propagated by the AC3 algorithm    *
// ********************************************************************

AC3BinConstraint <: CSPBinConstraint()

// internal constructor
[choco/makeAC3BinConstraint(u:IntVar, v:IntVar, feas:boolean, feasRel:BinRelation) : AC3BinConstraint
 -> AC3BinConstraint(v1 = u, v2 = v, cste = 0, feasiblePair = feas, feasRelation = feasRel)]
    
// updates the support for all values in the domain of v2, and remove unsupported values for v2
[choco/reviseV2(c:AC3BinConstraint) : void
 => let var1 := c.v1, var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair in 
      for y in domain(var2)
        (if forall(x in domain(var1) | getTruthValue(feasRel,x,y) != feas)
            removeVal(var2,y,c.idx2))]

// updates the support for all values in the domain of v1, and remove unsupported values for v1
[choco/reviseV1(c:AC3BinConstraint) : void
 => let var1 := c.v1, var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair in 
      for x in domain(var1)
        (if forall(y in domain(var2) | getTruthValue(feasRel,x,y) != feas)
            removeVal(var1,x,c.idx1))]

// Maintaining arc consistency
[choco/awakeOnInf(c:AC3BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

[choco/awakeOnSup(c:AC3BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

[choco/awakeOnRem(c:AC3BinConstraint, idx:integer, oldval:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

[choco/awakeOnVar(c:AC3BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

[choco/propagate(c:AC3BinConstraint) : void
 -> reviseV2(c), reviseV1(c)]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(AC3BinConstraint))

// ********************************************************************
// *   Part 1b: Binary constraints propagated by the AC4 algorithm    *
// ********************************************************************


// the AC4 algorithm uses annotation, caching the current number of supports for each value of the domains     
AC4BinConstraint <: CSPBinConstraint(
   nbSupport1:IntSetIntAnnotation,        // nbOfSupport for each value of v1
   nbSupport2:IntSetIntAnnotation,        // nbOfSupport for each value of v2
   validSupport1:IntSetBoolAnnotation,    // v0.9907: "desynchronized" copies of the domains of v1 of v2
   validSupport2:IntSetBoolAnnotation)    //   a value is removed from this copy only once the constraint did react 
                                          //   to an event removing that value

// internal constructor
//    Note: 0.9907 it is important that after their creation, the support counts of the constraints
//    are meaningful.
//    Indeed, they may be resynchronized with awake (the initial propagation)
//    But some awakeOn... methods may be called before awake.
[choco/makeAC4BinConstraint(u:IntVar, v:IntVar, feas:boolean, feasRel:BinRelation) : AC4BinConstraint
 -> let c := AC4BinConstraint(v1 = u, v2 = v, cste = 0, feasiblePair = feas, feasRelation = feasRel) in 
       (c.nbSupport1 := makeIntSetIntAnnotation(c.v1.inf,c.v1.sup,(if feas 0 else v.sup - v.inf + 1)),
        c.nbSupport2 := makeIntSetIntAnnotation(c.v2.inf,c.v2.sup,(if feas 0 else u.sup - u.inf + 1)),
        c.validSupport1 := makeIntSetBoolAnnotation(c.v1.inf,c.v1.sup,true),
        c.validSupport2 := makeIntSetBoolAnnotation(c.v2.inf,c.v2.sup,true),
        for x in (u.inf .. u.sup) 
           let nbs := count(list{y in domain(c.v2) | getTruthValue(feasRel,x,y) = feas}) in
               setIntAnnotation(c.nbSupport1,x,nbs),
        for y in (v.inf .. v.sup) 
           let nbs := count(list{x in domain(c.v1) | getTruthValue(feasRel,x,y) = feas}) in
               setIntAnnotation(c.nbSupport2,y,nbs),
        c)]

// -- four small utilities --

// decrement the number of values b that support the assignment v1=x
// ie: b such that v1=x, v2=b is a feasible pair
// When this count reaches 0, discard the value x from the domain of v1
[private/decrementNbSupportV1(c:AC4BinConstraint, x:integer) : void
 -> let nbs := getIntAnnotation(c.nbSupport1,x) in 
      (//[5] decrement supportv1 ~S, ~S[~S] // c,x,nbs,
       assert(nbs >= 1),
       nbs :- 1,
       setIntAnnotation(c.nbSupport1,x,nbs),
       if (nbs = 0)
          removeVal(c.v1,x,c.idx1))]
       
// decrement the number of values a that support the assignment v2=y
// ie: a such that v1=a, v2=y is a feasible pair
// When this count reaches 0, discard the value y from the domain of v2
[private/decrementNbSupportV2(c:AC4BinConstraint, y:integer) : void
 -> let nbs := getIntAnnotation(c.nbSupport2,y) in 
      (//[5] decrement supportv2 ~S, ~S[~S] // c,y,nbs,
       assert(nbs >= 1),
       nbs :- 1,
       setIntAnnotation(c.nbSupport2,y,nbs),
       if (nbs = 0)
          removeVal(c.v2,y,c.idx2))]

// recompute the number of values b that support the assignment v1=x
// ie: b such that v1=x, v2=b is a feasible pair
// When this count equals 0, discard the value x from the domain of v1
[private/resetNbSupportV1(c:AC4BinConstraint, x:integer) : void
 -> let feasRel := c.feasRelation, feas := c.feasiblePair,
        nbs := count(list{y in domain(c.v2) | getTruthValue(feasRel,x,y) = feas}) in
       (setIntAnnotation(c.nbSupport1,x,nbs),
        if (nbs = 0) removeVal(c.v1,x,c.idx1))]
 
// recompute the number of values a that support the assignment v2=y
// ie: a such that v1=a, v2=y is a feasible pair
// When this count equals 0, discard the value y from the domain of v2
[private/resetNbSupportV2(c:AC4BinConstraint, y:integer) : void
 -> let feasRel := c.feasRelation, feas := c.feasiblePair,
        nbs := count(list{x in domain(c.v1) | getTruthValue(feasRel,x,y) = feas}) in
       (setIntAnnotation(c.nbSupport2,y,nbs),
        if (nbs = 0) removeVal(c.v2,y,c.idx2))]

// -- main propagation methods --

// standard filtering algorithm initializing all support counts
[choco/propagate(c:AC4BinConstraint) : void
 -> for x in domain(c.v1) resetNbSupportV1(c,x),
    for y in domain(c.v2) resetNbSupportV2(c,y) ]

// Maintaining arc consistency with the standard AC4 procedure:
// upon the removal of (v1=a) the number of support of (v2=b) is decremented for all values b
// such that (a,b) is a feasible pair
// v0.9903: introduced c.inversedFeasTest flag
// v0.9907: introduced the c.validSupport1/2 slots
// v1.321: rewrite
[choco/awakeOnRem(c:AC4BinConstraint, idx:integer, oldval:integer) : void
 -> let newEvt := true in 
  (if (idx = 1) 
      (if getBoolAnnotation(c.validSupport1, oldval)
          setBoolAnnotation(c.validSupport1, oldval, false)
       else newEvt := false)
   else (if getBoolAnnotation(c.validSupport2, oldval)
            setBoolAnnotation(c.validSupport2, oldval, false)
         else newEvt := false),
   if newEvt
     let feasRel := c.feasRelation, feas := c.feasiblePair in 
      (if (idx = 1)
         let min2 := getOriginalMin(c.validSupport2), max2 := getOriginalMax(c.validSupport2), oldx := oldval in // original domain bounds
            for y in list{y in (min2 .. max2) | 
                          (getBoolAnnotation(c.validSupport2, y) & getTruthValue(feasRel,oldx,y) = feas)}
                decrementNbSupportV2(c,y)
       else let min1 := getOriginalMin(c.validSupport1), max1 := getOriginalMax(c.validSupport1), oldy := oldval in // original domain bounds
              for x in list{x in (min1 .. max1) | 
                            (getBoolAnnotation(c.validSupport1, x) & getTruthValue(feasRel,x,oldy) = feas)}
                  decrementNbSupportV1(c,x) ))]

// forward checking propagation
[choco/awakeOnInst(c:AC4BinConstraint, idx:integer) : void
 -> let var1 := c.v1, var2 := c.v2, val := (if (idx = 1) var1.value else var2.value),
        min1 := getOriginalMin(c.validSupport1), max1 := getOriginalMax(c.validSupport1), // original domain bounds   
        min2 := getOriginalMin(c.validSupport2), max2 := getOriginalMax(c.validSupport2), // original domain bounds  
        feasRel := c.feasRelation, feas := c.feasiblePair in
  (if (idx = 1) 
      (// first, update the validSupport slots 
       for x in ((min1 .. max1) but val) 
           setBoolAnnotation(c.validSupport1, x, false),
       // then perform forward checking propagation
       for y in list{y in (min2 .. max2) | (getBoolAnnotation(c.validSupport2,y) & getTruthValue(feasRel,val,y) != feas)}
           removeVal(var2,y,c.idx2))
   else (// first, update the validSupport slots 
         for y in ((min2 .. max2) but val) 
             setBoolAnnotation(c.validSupport2, y, false),
         // then perform forward checking propagation
         for x in list{x in (min1 .. max1) | (getBoolAnnotation(c.validSupport1,x) & getTruthValue(feasRel,x,val) != feas)}
             removeVal(var1,x,c.idx1)),
   // v1.0: now, the constraint is definitely satisfied (a property of forward checking propagation)                                          
   setPassive(c))]

// Note: these methods could be improved by considering for each value, the minimal and maximal support considered into the count
[choco/awakeOnInf(c:AC4BinConstraint, idx:integer) : void
 -> let vinf := (if (idx = 1) c.v1.inf - 1 else c.v2.inf - 1),
        min1 := getOriginalMin(c.validSupport1), max1 := getOriginalMax(c.validSupport1), // original domain bounds   
        min2 := getOriginalMin(c.validSupport2), max2 := getOriginalMax(c.validSupport2), // original domain bounds  
        feasRel := c.feasRelation, feas := c.feasiblePair in
  (if (idx = 1) 
      (// first, update the validSupport slots    
       for x in (min1 .. vinf)  setBoolAnnotation(c.validSupport1, x, false),
       // then maintain arc consistency (update supportCounts)
       for y in list{y in (min2 .. max2) | getBoolAnnotation(c.validSupport2,y)}
                       (if exists(x in (min1 .. vinf) | getTruthValue(feasRel,x,y) = feas)
                           resetNbSupportV2(c,y)) )
   else (// first, update the validSupport slots    
         for y in (min2 .. vinf) setBoolAnnotation(c.validSupport2, y, false),   
         // then maintain arc consistency (update supportCounts)
         for x in list{x in (min1 .. max1) | getBoolAnnotation(c.validSupport1,x)}
                       (if exists(y in (min2 .. vinf) | getTruthValue(feasRel,x,y) = feas)
                           resetNbSupportV1(c,x)) ) )]

[choco/awakeOnSup(c:AC4BinConstraint, idx:integer) : void
 -> let vsup := (if (idx = 1) c.v1.sup + 1 else c.v2.sup + 1),
        min1 := getOriginalMin(c.validSupport1), max1 := getOriginalMax(c.validSupport1), // original domain bounds   
        min2 := getOriginalMin(c.validSupport2), max2 := getOriginalMax(c.validSupport2), // original domain bounds  
        feasRel := c.feasRelation, feas := c.feasiblePair in
  (if (idx = 1) 
      (// first, update the validSupport slots    
       for x in (vsup .. max1)  setBoolAnnotation(c.validSupport1, x, false),
       // then maintain arc consistency (update supportCounts)
       for y in list{y in (min2 .. max2) | getBoolAnnotation(c.validSupport2,y)}
                       (if exists(x in (vsup .. max1) | getTruthValue(feasRel,x,y) = feas)
                           resetNbSupportV2(c,y)) )
   else (// first, update the validSupport slots    
         for y in (vsup .. max2) setBoolAnnotation(c.validSupport2, y, false),   
         // then maintain arc consistency (update supportCounts)
         for x in list{x in (min1 .. max1) | getBoolAnnotation(c.validSupport1,x)}
                       (if exists(y in (vsup .. max2) | getTruthValue(feasRel,x,y) = feas)
                           resetNbSupportV1(c,x)) ) )]

[choco/awakeOnVar(c:AC4BinConstraint, idx:integer) : void
 -> let vinf := (if (idx = 1) c.v1.inf - 1 else c.v2.inf - 1),
        min1 := getOriginalMin(c.validSupport1), max1 := getOriginalMax(c.validSupport1), // original domain bounds   
        min2 := getOriginalMin(c.validSupport2), max2 := getOriginalMax(c.validSupport2), // original domain bounds  
        feasRel := c.feasRelation, feas := c.feasiblePair in
  (if (idx = 1) 
      (// first, update the validSupport slots    
       for x in (min1 .. max1)  
          (if not(canBeInstantiatedTo(c.v1,x))
              setBoolAnnotation(c.validSupport1, x, false)),
       // then maintain arc consistency (update supportCounts)
       for y in list{y in (min2 .. max2) | getBoolAnnotation(c.validSupport2,y)}
                           resetNbSupportV2(c,y) )
   else (// first, update the validSupport slots    
         for y in (min2 .. max2)  
          (if not(canBeInstantiatedTo(c.v2,y))
              setBoolAnnotation(c.validSupport2, y, false)),
         // then maintain arc consistency (update supportCounts)
         for x in list{x in (min1 .. max1) | getBoolAnnotation(c.validSupport1,x)}
                            resetNbSupportV1(c,x) )  )]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(AC4BinConstraint))

// ********************************************************************
// *   Part 1c: Binary constraints propagated by the AC2001 algorithm *
// ********************************************************************

// the AC2001 algorithm uses annotation, caching a current support for each value of the domains     
AC2001BinConstraint <: CSPBinConstraint(
   currentSupport1:IntSetIntAnnotation,   // current support for each value of v1 (each entry is a value for v2)
   currentSupport2:IntSetIntAnnotation)   // current support for each value of v2 (each entry is a value for v1)
                                   
// internal constructor
[choco/makeAC2001BinConstraint(u:IntVar, v:IntVar, feas:boolean, feasRel:BinRelation) : AC2001BinConstraint
 -> let c := AC2001BinConstraint(v1 = u, v2 = v, cste = 0, feasiblePair = feas, feasRelation = feasRel) in 
       (c.currentSupport1 := makeIntSetIntAnnotation(c.v1.inf,c.v1.sup,-99999),
        c.currentSupport2 := makeIntSetIntAnnotation(c.v2.inf,c.v2.sup,-99999),
        c)]

// updates the support for all values in the domain of v2, and remove unsupported values for v2
[choco/reviseV2(c:AC2001BinConstraint) : void
 => for y in domain(c.v2)
      let x0 := getIntAnnotation(c.currentSupport2,y) in 
        (if not(canBeInstantiatedTo(c.v1,x0))
            updateSupportVal2(c,y)) ]

// updates the support for all values in the domain of v1, and remove unsupported values for v1
[choco/reviseV1(c:AC2001BinConstraint) : void
 => for x in domain(c.v1)
      let y0 := getIntAnnotation(c.currentSupport1,x) in 
        (if not(canBeInstantiatedTo(c.v2,y0))
            updateSupportVal1(c,x)) ]

// the smart trick of AC2001:
//  the current support for value x of v1 is no longer valid:
//  look for a new one, without restarting the iteration form scratch
[choco/updateSupportVal1(c:AC2001BinConstraint, x:integer) : void
 -> let var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair, 
        max2 := getSup(var2), y := getIntAnnotation(c.currentSupport1,x), found := false in 
       (while (not(found) & (y < max2))
              (y := getNextDomainValue(var2,y),
               if (getTruthValue(feasRel,x,y) = feas)
                  found := true),
        if found setIntAnnotation(c.currentSupport1,x,y)
        else removeVal(c.v1, x, c.idx1) )]

//  the current support for value y of v2 is no longer valid:
//  look for a new one, without restarting the iteration form scratch
[choco/updateSupportVal2(c:AC2001BinConstraint, y:integer) : void
 -> let var1 := c.v1, feasRel := c.feasRelation, feas := c.feasiblePair, 
        max1 := getSup(var1), x := getIntAnnotation(c.currentSupport2,y), found := false in 
       (while (not(found) & (x < max1))
              (x := getNextDomainValue(var1,x),
               if (getTruthValue(feasRel,x,y) = feas)
                  found := true),
        if found setIntAnnotation(c.currentSupport2,y,x)
        else removeVal(c.v2, y, c.idx2) )]

[choco/awakeOnInf(c:AC2001BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

[choco/awakeOnSup(c:AC2001BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

// forward checking propagation
[choco/awakeOnInst(c:AC2001BinConstraint, idx:integer) : void
 -> let var1 := c.v1, var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair,
        val := (if (idx = 1) var1.value else var2.value)  in 
    (if (idx = 1)
        for y in list{y in domain(var2) | getTruthValue(feasRel,val,y) != feas}
            removeVal(var2,y,c.idx2)
    else for x in list{x in domain(var1) | getTruthValue(feasRel,x,val) != feas}
             removeVal(var1,x,c.idx1),
    // v1.0: now, the constraint is definitely satisfied (a property of forward checking propagation)                                          
    setPassive(c))]

// Maintaining arc consistency
[choco/awakeOnRem(c:AC2001BinConstraint, idx:integer, oldval:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

// v1.322
[choco/awakeOnVar(c:AC2001BinConstraint, idx:integer) : void
 -> if (idx = 1) reviseV2(c) else reviseV1(c)]

// propagation: incrementally maintain the supports that are no longer valid
[choco/propagate(c:AC2001BinConstraint) : void
 -> reviseV2(c), reviseV1(c)]

// initialization: initialize supports
[choco/awake(c:AC2001BinConstraint) : void
 -> let var1 := c.v1, var2 := c.v2, feasRel := c.feasRelation, feas := c.feasiblePair in
      (for x in domain(var1)
         (when y0 := some(y in domain(var2) |  getTruthValue(feasRel,x,y) = feas) in
               setIntAnnotation(c.currentSupport1,x,y0)
          else removeVal(var1,x,c.idx1)),
       for y in domain(var2)
         (when x0 := some(x in domain(var1) |  getTruthValue(feasRel,x,y) = feas) in
               setIntAnnotation(c.currentSupport2,y,x0)
          else removeVal(var2,y,c.idx2)) )]
              
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(AC2001BinConstraint))

// ********************************************************************
// *   Part 2: Constraints as arbitrary lists feasible tuples         *
// ********************************************************************

// v0.15
CSPLargeConstraint <: LargeIntConstraint(
   cachedTuples:boolean = true, // do we cache the tuples in a matrix
   matrix:BoolMatrixND,         // if yes: the n dimensional boolean array (feasible/infeasible pairs)
   feasTest:method)             // otherwise: the test method

;[self_print(c:CSPLargeConstraint) : void
; -> ...]

[choco/tupleConstraint(lvars:list[IntVar], feasible:boolean,
                         mytuples:list[list[integer]]) : CSPLargeConstraint
 -> let c := CSPLargeConstraint(vars = copy(lvars)),
        // v0.9906 cast below
        // claire3 port: Intervals no longer need to be typed        
        mat := makeNDBoolMatrix(list{((v.inf .. v.sup) as Interval) | v in lvars},
                                not(feasible),false) in
     (c.matrix := mat, // v1.008
      for t in mytuples
          store(mat,t,feasible),
      closeLargeIntConstraint(c), // v1.008
      c) ]

[choco/awakeOnInf(c:CSPLargeConstraint, idx:integer) : void
 -> constAwake(c,false)]  // v1.008 delay the propagation to the constraint-based loop

[choco/awakeOnSup(c:CSPLargeConstraint, idx:integer) : void
 -> constAwake(c,false)]  // v1.008

[choco/awakeOnRem(c:CSPLargeConstraint, idx:integer, oldval:integer) : void
 -> constAwake(c,false)]  // v1.008

[choco/awakeOnInst(c:CSPLargeConstraint, idx:integer) : void
 -> constAwake(c,false)]  // v1.008

// forward checking propagation
[choco/propagate(c:CSPLargeConstraint) : void
 -> let n := length(c.vars), nbUnAssigned := 0, i0 := 0 in   // v0.28: size vs. length
      (for i in (1 .. n)
          (if not(isInstantiated(c.vars[i]))
              (nbUnAssigned :+ 1, i0 := i)),
       if (nbUnAssigned = 1)
          let l1 := list{c.vars[i].value | i in (1 .. i0 - 1)},
              l2 := list{c.vars[i].value | i in (i0 + 1 .. n)} in
          (if c.cachedTuples
              for y in list{y in domain(c.vars[i0] as IntVar) | not(read(c.matrix, l1 /+ list(y) /+ l2)) }  // v0.39: bug <michel>: use of add with side effects on l1
                  removeVal(c.vars[i0],y,0)
           else for y in list{y in domain(c.vars[i0] as IntVar) | not(funcall(c.feasTest, l1 /+ list(y) /+ l2)) }
                    removeVal(c.vars[i0],y,0) )
       else if (nbUnAssigned = 0)
               (if ( (c.cachedTuples &
                      not(read(c.matrix,list{c.vars[i].value | i in (1 .. n)}))) |
                     (not(c.cachedTuples) &
                      not(funcall(c.feasTest,list{c.vars[i].value | i in (1 .. n)}))) )
                   raiseContradiction(c) ) )]  // v1.008

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(CSPLargeConstraint))

// ********************************************************************
// *   Part 3: Comparisons as unary constraints                       *
// ********************************************************************

// Comparisons to a constant (unary Constraints)
//
choco/GreaterOrEqualxc <: UnIntConstraint()
choco/LessOrEqualxc <: UnIntConstraint()
choco/Equalxc <: UnIntConstraint()
choco/NotEqualxc <: UnIntConstraint()

[self_print(c:GreaterOrEqualxc) : void -> printf("~A >= ~S",c.v1.name,c.cste)]
[self_print(c:LessOrEqualxc) : void -> printf("~A <= ~S",c.v1.name,c.cste)]
[self_print(c:Equalxc) : void -> printf("~A = ~S",c.v1.name,c.cste)]
[self_print(c:NotEqualxc) : void -> printf("~A <> ~S",c.v1.name,c.cste)]

// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
[choco/awakeOnInf(c:Equalxc, i:integer) : void
 -> instantiate(c.v1, c.cste, c.idx1)] ;, setPassive(c)]  v1.05 seems to heavy
[choco/awakeOnSup(c:Equalxc, i:integer) : void
 -> instantiate(c.v1, c.cste, c.idx1)] ; , setPassive(c)] v1.05 seems to heavy
[choco/awakeOnInst(c:Equalxc, i:integer) : void
 -> instantiate(c.v1, c.cste, c.idx1)]
[choco/propagate(c:Equalxc) : void     ; v1.010 signature
 -> instantiate(c.v1, c.cste, c.idx1)] ; , setPassive(c)] v1.05 seems to heavy
[choco/askIfEntailed(c:Equalxc) : (boolean U {unknown})
 -> let v := c.v1, x := c.cste in
     (if (v isInstantiatedTo x) true
      else if not(v canBeInstantiatedTo x) false
      else unknown)]
[choco/testIfSatisfied(c:Equalxc) : boolean
 -> assert(isInstantiated(c.v1)),
    (c.v1.value = c.cste) ]

// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
[choco/awakeOnInf(c:GreaterOrEqualxc, i:integer) : void
 -> updateInf(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/awakeOnSup(c:GreaterOrEqualxc, i:integer) : void
 -> updateInf(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/awakeOnInst(c:GreaterOrEqualxc, i:integer) : void
 -> updateInf(c.v1, c.cste, c.idx1)]
[choco/propagate(c:GreaterOrEqualxc) : void     ; v1.010 signature
 -> updateInf(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/askIfEntailed(c:GreaterOrEqualxc) : (boolean U {unknown})
 -> let v := c.v1, x := c.cste in
     (if (getInf(v) >= x) true
      else if (getSup(v) < x) false
      else unknown)]
[choco/testIfSatisfied(c:GreaterOrEqualxc) : boolean
 -> assert(isInstantiated(c.v1)),
    (c.v1.value >= c.cste) ]

// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
[choco/awakeOnInf(c:LessOrEqualxc, i:integer) : void
 -> updateSup(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/awakeOnSup(c:LessOrEqualxc, i:integer) : void
 -> updateSup(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/awakeOnInst(c:LessOrEqualxc, i:integer) : void
 -> updateSup(c.v1, c.cste, c.idx1)]
[choco/propagate(c:LessOrEqualxc) : void      ; v1.010 signature
 -> updateSup(c.v1, c.cste, c.idx1), setPassive(c)]
[choco/askIfEntailed(c:LessOrEqualxc) : (boolean U {unknown})
 -> let v := c.v1, x := c.cste in
     (if (getSup(v) <= x) true
      else if (getInf(v) > x) false
      else unknown)]
[choco/testIfSatisfied(c:LessOrEqualxc) : boolean
 -> assert(isInstantiated(c.v1)),
    (c.v1.value <= c.cste) ]

// v1.05 the calls to setPassive are valid only when c.v1 is not a BoundIntVar
// Moreover, they seem to be a bit heavy (too long to do perform given the computation that they later save)
[choco/awakeOnInf(c:NotEqualxc, idx:integer) : void
 -> removeVal(c.v1, c.cste, c.idx1)] ; , setPassive(c)] // v1.05 seems to heavy 
[choco/awakeOnSup(c:NotEqualxc, idx:integer) : void
 -> removeVal(c.v1, c.cste, c.idx1)] ; , setPassive(c)] // v1.05 seems to heavy 
[choco/awakeOnInst(c:NotEqualxc, idx:integer) : void
 -> if (c.v1.inf = c.cste) raiseContradiction(c)]  // v1.010
[choco/propagate(c:NotEqualxc) : void     ; v1.010 signature
 -> removeVal(c.v1, c.cste, c.idx1)]
[choco/askIfEntailed(c:NotEqualxc) : (boolean U {unknown})
 -> let v := c.v1, x := c.cste in
     (if (v isInstantiatedTo x) false
      else if not(v canBeInstantiatedTo x) true
      else unknown)]
[choco/testIfSatisfied(c:NotEqualxc) : boolean
 -> assert(isInstantiated(c.v1)),
    (c.v1.value != c.cste) ]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?)
;     (register(GreaterOrEqualxc), register(LessOrEqualxc), register(Equalxc), register(NotEqualxc)))

// ********************************************************************
// *   Part 4: Comparisons as binary constraints                      *
// ********************************************************************

choco/Equalxyc <: BinIntConstraint()
choco/NotEqualxyc <:  BinIntConstraint()
choco/GreaterOrEqualxyc <: BinIntConstraint()

[self_print(c:Equalxyc) : void -> printf("~A = ~A + ~S",c.v1.name,c.v2.name,c.cste)]
[self_print(c:NotEqualxyc) : void -> printf("~A <> ~A + ~S",c.v1.name,c.v2.name,c.cste)]
[self_print(c:GreaterOrEqualxyc) : void -> printf("~A >= ~A + ~S",c.v1.name,c.v2.name,c.cste)]

// ----- Constraint:  U == V + c ------
[choco/propagate(c:Equalxyc) : void
 -> updateInf(c.v1, c.v2.inf + c.cste, c.idx1),
    updateSup(c.v1, c.v2.sup + c.cste, c.idx1),
    updateInf(c.v2, c.v1.inf - c.cste, c.idx2),
    updateSup(c.v2, c.v1.sup - c.cste, c.idx2),
//v0.34 <thb>: For enumerated domains, ensure domain(v1) = domain(v2) + c (including holes)
    if (known?(bucket,c.v2))
       for val in list{val in domain(c.v1) | not(c.v2 canBeInstantiatedTo (val - c.cste))}
           removeVal(c.v1,val,c.idx1),
    if (known?(bucket,c.v1))
       for val in list{val in domain(c.v2) | not(c.v1 canBeInstantiatedTo (val + c.cste))}
           removeVal(c.v2,val,c.idx2)  ]

[choco/askIfEntailed(c:Equalxyc) : (boolean U {unknown})
 -> if (c.v1.sup < c.v2.inf + c.cste |
        c.v1.inf > c.v2.sup + c.cste) false
    else if (c.v1.inf = c.v1.sup &
             c.v2.inf = c.v2.sup &
             c.v1.inf = c.v2.inf + c.cste) true
    else unknown]

[choco/testIfSatisfied(c:Equalxyc) : boolean
 -> assert(isInstantiated(c.v1)),
    assert(isInstantiated(c.v2)),
    (c.v1.value = c.v2.value + c.cste) ]

[choco/awakeOnInf(c:Equalxyc, idx:integer) : void
 -> if (idx = 1) updateInf(c.v2, c.v1.inf - c.cste, c.idx2)
    else updateInf(c.v1, c.v2.inf + c.cste, c.idx1)]

[choco/awakeOnSup(c:Equalxyc, idx:integer) : void
 -> if (idx = 1) updateSup(c.v2, c.v1.sup - c.cste, c.idx2)
    else updateSup(c.v1, c.v2.sup + c.cste, c.idx1)]

[choco/awakeOnInst(c:Equalxyc, idx:integer) : void
 -> if (idx = 1) instantiate(c.v2, c.v1.value - c.cste, c.idx2)
    else instantiate(c.v1, c.v2.value + c.cste, c.idx1)]

[choco/awakeOnRem(c:Equalxyc, idx:integer, x:integer) : void
 -> if (idx = 1) removeVal(c.v2, x - c.cste, c.idx2)
    else removeVal(c.v1, x + c.cste, c.idx1)]

// ----- Constraint:  U <> V + c ------
[choco/propagate(cont:NotEqualxyc) : void
 -> doAwakeOnInf(cont,1), doAwakeOnInf(cont,2)]

[choco/awakeOnInf(c:NotEqualxyc, idx:integer) : void
 -> if isInstantiated(c.v1) removeVal(c.v2, c.v1.value - c.cste, c.idx2)
    else if isInstantiated(c.v2) removeVal(c.v1, c.v2.value + c.cste, c.idx1)]

[choco/awakeOnSup(c:NotEqualxyc, idx:integer) : void
 -> if isInstantiated(c.v1) removeVal(c.v2, c.v1.value - c.cste, c.idx2)
    else if isInstantiated(c.v2) removeVal(c.v1, c.v2.value + c.cste, c.idx1)]

// v1.0: after one instantiation, the constraint is necessarily always satisfied
[choco/awakeOnInst(c:NotEqualxyc, idx:integer) : void
 -> if isInstantiated(c.v1) removeVal(c.v2, c.v1.value - c.cste, c.idx2)
    else if isInstantiated(c.v2) removeVal(c.v1, c.v2.value + c.cste, c.idx1)]
    ; setPassive(c)]  // seems to heavy

[choco/askIfEntailed(c:NotEqualxyc) : (boolean U {unknown})
 -> if ((c.v1.sup < c.v2.inf + c.cste) | (c.v2.sup < c.v1.inf - c.cste)) true
    else if (c.v1.inf = c.v1.sup & c.v2.inf = c.v2.sup & c.v1.inf = c.v2.inf + c.cste) false
    else unknown]

[choco/testIfSatisfied(c:NotEqualxyc) : boolean
 -> assert(isInstantiated(c.v1)),
    assert(isInstantiated(c.v2)),
    (c.v1.value != c.v2.value + c.cste) ]

// ----- Constraint:  U >= V + c ------
[choco/propagate(cont:GreaterOrEqualxyc) : void
 -> doAwakeOnInf(cont,2), doAwakeOnSup(cont,1)]

[choco/awakeOnInf(c:GreaterOrEqualxyc, idx:integer) : void
 -> if (idx = 2) updateInf(c.v1, c.v2.inf + c.cste, c.idx1)
    else if (c.v1.inf >= c.v2.sup + c.cste) setPassive(c)]

[choco/awakeOnSup(c:GreaterOrEqualxyc, idx:integer) : void
 -> if (idx = 1) updateSup(c.v2, c.v1.sup - c.cste, c.idx2)
    else if (c.v1.inf >= c.v2.sup + c.cste) setPassive(c)]

[choco/awakeOnInst(c:GreaterOrEqualxyc, idx:integer) : void
 -> if (idx = 1) updateSup(c.v2, c.v1.value - c.cste, c.idx2)
    else updateInf(c.v1, c.v2.value + c.cste, c.idx1),
    if (c.v1.inf >= c.v2.sup + c.cste) setPassive(c)]

[choco/askIfEntailed(c:GreaterOrEqualxyc) : (boolean U {unknown})
 -> if (c.v1.sup < c.v2.inf + c.cste) false
    else if (c.v1.inf >= c.v2.sup + c.cste) true
    else unknown]

[choco/testIfSatisfied(c:GreaterOrEqualxyc) : boolean
 -> assert(isInstantiated(c.v1)),
    assert(isInstantiated(c.v2)),
    (c.v1.value >= c.v2.value + c.cste) ]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?)
;     (register(GreaterOrEqualxyc), 
;      register(Equalxyc), register(NotEqualxyc)))

// ********************************************************************
// *   Part 5: General linear combinations as global constraints      *
// ********************************************************************

// ----- Constraint:  sum(ai Xi) + c >= 0 (or == 0) ------
// v0.32: This constraint has the following data structures
//   coefs[i] is the coefficient of vars[i]
//   nbPosVars is the number of >0 coefficient.
//   vars and coeffs are stored such that all positive coefficients come first
//   (from position 1 to nbPosVars) and negative coefficients last
//   (from position nbPosVars + 1 to nbVars)
// v1.010: introduce op: 1: eqality constrain (== 0)
//                       2: inequalitity constraint (>= 0)
//                       3: disequality constraint (<> 0)
EQ :: 1
GEQ :: 2
NEQ :: 3
choco/LinComb <: LargeIntConstraint(
         nbPosVars:integer = 0,
         coefs:integer[],  // v1.013: now a an array                   // v1.011: now an array
         op:integer = EQ,                    // v1.010
         improvedUpperBound:boolean = true,  // set the propagation flags to true in order to
         improvedLowerBound:boolean = true)  // make the initial propagation

// TODO: changed to StoredBool
(store(improvedUpperBound,improvedLowerBound)) //v0.37: must be stored in order to remain true in worlds
                                               //       were the constraint has not been awoken yet (when in a bool. const.)
// v1.02
[self_print(self:LinComb) : void
  -> let n := length(self.vars) in
       for i in (1 .. min(5,n))
         (if ((i != 1) & (self.coefs[i] > 0)) princ("+"),
          printf("~S.~A ",self.coefs[i],self.vars[i].name)),
    ; printf("~S scalar ~S",self.coefs,self.vars),
     if (self.cste > 0) printf("+~S", self.cste)
     else if (self.cste < 0) printf("~S", self.cste),
     if (self.op = EQ) princ("== 0")
     else if (self.op = GEQ) princ(">= 0")
     else princ("<> 0")]

// 0.32: new data structures
[choco/makeLinComb(l1:list[integer], l2:list[IntVar], c:integer, opn:integer) : LinComb // v1.010
 -> assert(length(l1) = length(l2)),                                          // v0.28: size vs. length
    //[5] makeLinComb ~S ~S ~S ~S // l1,l2,c,opn,
    // claire3 port: typed empty list
    let posCoefs := list<integer>(), negCoefs := list<integer>(),
        posVars := list<IntVar>(), negVars := list<IntVar>() in
      (for j in (1 .. length(l2))
         (if (l1[j] > 0)
             (posCoefs :add l1[j], posVars :add l2[j])
          else if (l1[j] < 0)
             (negCoefs :add l1[j], negVars :add l2[j])),
       assert(length(posVars) = length(posCoefs)),  // v0.28: size vs. length
       assert(length(negVars) = length(negCoefs)),
       let cont := LinComb(vars = posVars /+ negVars, cste = c) in
          (closeLargeIntConstraint(cont),  // v0.34
           cont.op := opn,                 // v1.010
           cont.nbPosVars := length(posCoefs),
           cont.coefs := array!(posCoefs /+ negCoefs), // v1.011 coefs is an array
           cont) )]

[choco/getNbVars(c:LinComb) : integer -> c.nbVars] // v0.29

// v0.30: linear combinations can now be used within Boolean constraints or within a Delayer ("UnCompositeConstraint")
[choco/assignIndices(c:LinComb, root:(CompositeConstraint U Delayer), i:integer) : integer
 -> let j := i in
       (for k in (1 .. c.nbPosVars)
           (j :+ 1, connectIntVar(root,c.vars[k],j,true,(c.op != GEQ),true,false),
              // the constraint should be waken only upon updates to the upper bounds
              // of the positive variables (except in case of a equality constraint)
            c.indices[k] := length(c.vars[k].constraints)), // 0.32
        for k in (c.nbPosVars + 1 .. c.nbVars)
           (j :+ 1, connectIntVar(root,c.vars[k],j,true,true,(c.op != GEQ),false),
              // the constraint should be waken only upon updates to the lower bounds
              // of the negative variables (except in case of a equality constraint)
           c.indices[k] := length(c.vars[k].constraints)),
        j)]

// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/opposite(c:LinComb) : LinComb
 -> if (c.op = EQ)  makeLinComb(list{i | i in c.coefs}, c.vars,c.cste, NEQ)  // v1.011 coefs is an array
    else if (c.op = GEQ) makeLinComb(list{-(i) | i in c.coefs}, c.vars, -(c.cste + 1), GEQ)
    else (assert(c.op = NEQ),
          makeLinComb(list{i | i in c.coefs}, c.vars,c.cste, EQ))]           // v1.011 coefs is an array

// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/computeVarIdxInOpposite(c:LinComb,i:integer) : integer
 -> if (c.op != GEQ) i
    else (if (i <= c.nbPosVars) i + c.nbVars - c.nbPosVars
          else i - c.nbPosVars)]

// Upper bound estimate of a linear combination of variables
//
// v1.05 added a few casts
[choco/computeUpperBound(c:LinComb) : integer
 -> let s := 0 in
     (for i in (1 .. c.nbPosVars) s :+ ((c.vars[i] as IntVar).sup * (c.coefs[i] as integer)), // v1.05 casts
      for i in (c.nbPosVars + 1 .. c.nbVars) s :+ ((c.vars[i] as IntVar).inf * (c.coefs[i] as integer)), // v1.05 casts
      s + c.cste)]

// lower bound estimate of a linear combination of variables
//
// v1.05 added a few casts
[choco/computeLowerBound(c:LinComb) : integer
 -> let s := 0 in
     (for i in (1 .. c.nbPosVars) s :+ ((c.vars[i] as IntVar).inf * (c.coefs[i] as integer)), // v1.05 casts
      for i in (c.nbPosVars + 1 .. c.nbVars) s :+ ((c.vars[i] as IntVar).sup * (c.coefs[i] as integer)), // v1.05 casts
      s + c.cste)]

choco/propagateNewLowerBound :: property(range = boolean)
choco/propagateNewUpperBound :: property(range = boolean)
choco/filter :: property(range = void)

// v1.010: new internal methods
// v0.9907: this does not reach saturation (fix point), but returns a Boolean indicating 
// whether it infered new information or not.
// v1.05 added a few casts
// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
[choco/propagateNewLowerBound(c:LinComb, mylb:integer) : boolean
 -> let anyChange := false in 
     (if (mylb > 0) raiseContradiction(c),
      for i in (1 .. c.nbPosVars)
        (if updateSup(c.vars[i], -(mylb) div- (c.coefs[i] as integer) + (c.vars[i] as IntVar).inf, c.indices[i])
            anyChange := true),             
      for i in (c.nbPosVars + 1 .. c.nbVars)
        (if updateInf(c.vars[i], mylb div+ -((c.coefs[i] as integer)) + (c.vars[i] as IntVar).sup, c.indices[i])
            anyChange := true),
      anyChange)]
// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
[choco/propagateNewUpperBound(c:LinComb, myub:integer) : boolean
-> let anyChange := false in
     (if (myub < 0) raiseContradiction(c), // v1.010
      for i in (1 .. c.nbPosVars)
         (if updateInf(c.vars[i], -(myub) div+ (c.coefs[i] as integer) + (c.vars[i] as IntVar).sup, c.indices[i])
             anyChange := true),
      for i in (c.nbPosVars + 1 .. c.nbVars)
         (if updateSup(c.vars[i], myub div- -((c.coefs[i] as integer)) + (c.vars[i] as IntVar).inf, c.indices[i])
             anyChange := true),
      anyChange)]

// when the lower bound changes
// Note: this must be called as a constraint check everytime the lower bound is improved
//       but there is some propagation to perform only in case the linear constraint
//       is a linear equality
// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/filterOnImprovedLowerBound(c:LinComb) : boolean
 -> if (c.op = EQ) // <hha> v0.17 constraint check only for equality constraints (otherwise passive constraint)
       propagateNewLowerBound(c,computeLowerBound(c))
   else if (c.op = GEQ) false   // v1.0 (nothing to propagate => nothing was inferred, so return false)
   else (assert(c.op = NEQ),
         let mylb := computeLowerBound(c), anyChange := false in
           (if (mylb = 0) // v1.010 propagate the constraint sigma(ai) + c >= 1
               propagateNewUpperBound(c,computeUpperBound(c) - 1)))]
                    
// when the upper bound changes
// v1.05 added a few casts
[choco/filterOnImprovedUpperBound(c:LinComb) : boolean
 -> let myub := computeUpperBound(c) in
      (if (c.op = EQ) propagateNewUpperBound(c,myub)
       else if (c.op = GEQ) propagateNewUpperBound(c,myub)
       else (assert(c.op = NEQ),
             if (myub = 0) // v1.010 propagate the constraint sigma(ai) + c <= -1
                propagateNewLowerBound(c,computeLowerBound(c) + 1)))]

// v1.0
// Note: additional propagation pass are sometimes useful:
// For instance : 3*X[0.3] + Y[1.10] = 10
//                Y >= 2 causes X < 3 -> updateSup(X,2)
//                and this very event (the new sup of X) generates causes (Y >= 4).
//                this induced event (Y>=4) could not be infered at first (with only Y>=2)
//
// a strategy for chaotic iteration with two rules (LB and UB propagation)
// the fix point is reached individually for each rule in one function call
// but this call may break the stability condition for the other rule (in which case
// the second rule infers new information from the fresh inferences made by the first rule).
// The algorithm oscilates between both rules until a global fix point is reached.
// Conjecture: the maximum number of oscillations is two.
// parameters: startWithLB -> whether LB must be the first rule applied
//             MinNbRules  -> minimum number of rules required to reach fiux point.
[choco/filter(c:LinComb, startWithLB:boolean, minNbRules:integer) : void
 -> let lastRuleEffective := true,     // whether the last rule indeed perform some reductions
        nbr := 0,                      // number of rules applied
        nextRuleIsLB := startWithLB in // whether the next rule that should be filtered is LB (or UB)
      (while (lastRuleEffective | nbr < minNbRules)
          (if nextRuleIsLB 
             (//[GPTALK]   -- LB propagation for ~S // c,
              lastRuleEffective := filterOnImprovedLowerBound(c))
           else (//[GPTALK]   -- UB propagation for ~S // c,
                 lastRuleEffective := filterOnImprovedUpperBound(c)),
           nextRuleIsLB := not(nextRuleIsLB),
           nbr :+ 1),
       c.improvedLowerBound := false,
       c.improvedUpperBound := false)]

// v1.015: all right these methods are abstract for Palm, but they are now fast dispatched
(interface(filterOnImprovedLowerBound), interface(filterOnImprovedUpperBound), interface(filter))
(interface(LinComb, filter, filterOnImprovedLowerBound, filterOnImprovedUpperBound))

// v0.33 <thb>: this same function is used for propagating immediately or delayed
[choco/propagate(c:LinComb) : void
 -> if (c.improvedLowerBound & c.improvedUpperBound) filter(c, true, 2)  // apply both rules at least once, starting with LB rule
    else if c.improvedLowerBound filter(c, true, 1)   // apply LB rule at least once and ocsillate if this produces new inferences
    else if c.improvedUpperBound filter(c, false, 1)] // apply UB rule at least once and ocsillate if this produces new inferences

// start with the LB rule, continue with UB and oscillate until no more information can be drawn
[choco/awake(c:LinComb) : void
 -> filter(c, true, 2)]   
  
// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/askIfEntailed(c:LinComb) : (boolean U {unknown})
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

// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
[choco/testIfSatisfied(c:LinComb) : boolean
 -> assert(forall(v in c.vars | isInstantiated(v))),
    let s := 0 in
      (for i in (1 .. c.nbVars) s :+ (c.vars[i].value * c.coefs[i]),
       s :+ c.cste,
       if (c.op = EQ) (s = 0)
       else if (c.op = GEQ) (s >= 0)
       else (assert(c.op = NEQ), (s != 0)) )]

// TODO: when the lists nextConstOnInf & nextConstOnSUp will be merged into a single nextConstOnBound,
// the optimization from assignIndices (wake linear Inequalities only on half the bound events)
// should be moved here, on abstractXXXX and awakeOnXXXX methods

// v0.30 explicit event abstractions required by the Delayer object
// v0.39 <thb> explicitly returns a boolean (a := b no longer returns b)
[choco/abstractIncInf(c:LinComb,i:integer) : boolean
-> if (i <= c.nbPosVars) c.improvedLowerBound := true
   else c.improvedUpperBound := true,
   true]
[choco/abstractDecSup(c:LinComb,i:integer) : boolean
-> if (i <= c.nbPosVars) c.improvedUpperBound := true   //V0.33 <thb> typo
   else c.improvedLowerBound := true,
   true]
[choco/abstractInstantiate(c:LinComb,i:integer) : boolean
-> c.improvedLowerBound := true,
   c.improvedUpperBound := true,
   true]
[choco/abstractRemoveVal(c:LinComb,i:integer,v:integer) : boolean  // v1.0 <naren> missing parameter
-> if (i <= c.nbPosVars) c.improvedLowerBound := true
   else c.improvedUpperBound := true,
   true]

[choco/awakeOnInf(c:LinComb, idx:integer) : void
 -> if (idx <= c.nbPosVars) filter(c,true,1)
    else filter(c,false,1)]
[choco/awakeOnSup(c:LinComb, idx:integer) : void
 -> if (idx <= c.nbPosVars) filter(c,false,1)
    else filter(c,true,1)]
[choco/awakeOnInst(c:LinComb, idx:integer) : void
 -> awake(c)]

// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(LinComb))

