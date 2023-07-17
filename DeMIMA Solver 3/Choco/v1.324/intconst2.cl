// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: const2.cl                                                  *
// *    additional simple constraints                                 *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: accessing the ith element in a list of values          *
// *   Part 2: AllDifferent                                           *
// *   Part 3: occurences of a value in a list of variables           *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 1: accessing the ith element in a list of values          *
// ********************************************************************

// Element constraint
//   (accessing the ith element in a list of values, where i is a variable)
// the slot v1 represents the index and the slot v2 represents the value
// propagation with complete arc consistency from values to indices (v2 to v1)
// propagation with interval approximation from indices to values (v1 to v2)
choco/Elt <: BinIntConstraint(lval:list[integer])
[self_print(c:Elt) : void -> printf("ith-elt(~S,~S) == ~S",c.lval,c.v1,c.v2)]

// v0.34 uses the cste slot: l[i + cste] = x
// (ex: cste = 1 allows to use and index from 0 to length(l) - 1
// v0.9907: removed the method without the last argument
[private/makeEltConstraint(l:list[integer], i:IntVar, x:IntVar, offset:integer) : Elt
 -> Elt(v1 = i, v2 = x, cste = offset, lval = copy(l))]

// v0.25 <fl> replaced all removeVal by remVal (in order to propagate induced consequences)
// v0.26 a few casts
[private/updateValueFromIndex(c:Elt) : void
 -> let l := c.lval, indexVar := c.v1, valueVar := c.v2, minval := MAXINT, maxval := MININT in
      (for feasibleIndex in domain(indexVar)
         let val := (l[feasibleIndex + c.cste] as integer) in
         (minval :min val,
          maxval :max val),
       updateInf(valueVar, minval, c.idx2),
       updateSup(valueVar, maxval, c.idx2),
       // v1.0: propagate on holes when valueVar has an enumerated domain
       if known?(bucket,valueVar)
          for v in list{v in domain(valueVar) |
                          not(exists(i in domain(indexVar) | v = l[i + c.cste]))}  // v1.02 typo
              removeVal(valueVar,v,c.idx2)
        )]

// v0.29: The constraint must enforce that valueVar takes a value among l, which may be a sequence of values with holes.
// When valueVar is represented as an enumeration of values (with v.bucket), this is enforced from the start
// (when the constraint is posted).
// otherwise, when valueVar is only reperesented by its domain bounds, each time a value is removed from its domain
// we need not only to restrict indexVar, but also to propagate back the new bounds for valueVar.
// Thus, the consequences of this function on indexVar must be posted and propagated back to the Elt constraint
// therefore, we replace the cause of the event on indexVar (c.idx1) by 0 => the Elt constraint will be re-awaken later
// with a call to updateValueFromIndex which will recompute the min/max for valueVar
[private/updateIndexFromValue(c:Elt) : void
 -> let l := c.lval,n := length(l),
        indexVar := c.v1, valueVar := c.v2,
        minFeasibleIndex := max(1 - c.cste,indexVar.inf),
        maxFeasibleIndex := min(indexVar.sup,n - c.cste),
        cause := (if known?(bucket,valueVar) c.idx1 else 0) in // v0.28: size vs. length
     (while ((indexVar canBeInstantiatedTo minFeasibleIndex) &
             not(valueVar canBeInstantiatedTo l[minFeasibleIndex + c.cste])) minFeasibleIndex :+ 1,
      updateInf(indexVar,minFeasibleIndex,cause),
      while ((indexVar canBeInstantiatedTo maxFeasibleIndex) &
             not(valueVar canBeInstantiatedTo l[maxFeasibleIndex + c.cste])) maxFeasibleIndex :- 1,
      updateSup(indexVar,maxFeasibleIndex,cause),
      if (known?(bucket,indexVar))  //those remVal would be ignored for variables using an interval approximation for domain
         for i in (minFeasibleIndex + 1 .. maxFeasibleIndex - 1)
            (if ((indexVar canBeInstantiatedTo i) & not(valueVar canBeInstantiatedTo l[i + c.cste]))
                removeVal(indexVar, i, cause)) )]
         // => removeVal(indexVar,i,0) -> updateValueFromIndex
         //indeed removing the index implies that not only "not(valueVar canBeInstantiatedTo l[i]))" (what is not an additional info)
         // but also valueVar >= new min of the list and valueVar <= new max of the list

// v0.9907: at initialization time, we propagate once for good the fact that:
//   - indices must be in the right range for accessing the integer table 
//   - all values in the domain of valueVar must correspond to a value in l 
[choco/awake(c:Elt) : void
 -> let l := c.lval, n := length(c.lval), offset := c.cste, indexVar := c.v1, valueVar := c.v2 in
       (updateInf(indexVar,1 - offset,c.idx1), updateSup(indexVar,n - offset,c.idx1),
        propagate(c))]
        
[choco/propagate(c:Elt) : void
 -> updateIndexFromValue(c), 
    updateValueFromIndex(c)]

[choco/awakeOnInf(c:Elt, idx:integer) : void
 -> if (idx = 1) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

[choco/awakeOnSup(c:Elt, idx:integer) : void
 -> if (idx = 1) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

[choco/awakeOnInst(c:Elt, idx:integer) : void
 -> if (idx = 1) instantiate(c.v2, c.lval[c.v1.value + c.cste],c.idx2)
    else updateIndexFromValue(c)]

// v0.33 <thb>: in case a value is no longer feasible, we need to upate the index.
// Note that we call updateIndexFrom Value rather than only removing the i such that l[i]=x
// from the domain of the index variable.
// This is due to the case of interval approximation for value Var (in this case,
// one single call to updateIndexFromValue reaches fix point saturation)
[choco/awakeOnRem(c:Elt, idx:integer,x:integer) : void
 -> if (idx = 1) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

//v0.93: askIfEntailed can be called before any awake (in bool combinations)
//       Therefore we cannot assume that all values of domain(indexVar) lead to "in scope" indexes for the list.
//       -> added tests (idx + c.cste) % (1 .. length(l))
[choco/askIfEntailed(c:Elt) : (boolean U {unknown})  // v0.26 wrong interface (was askIfTrue)
 -> let l := c.lval, indexVar := c.v1, valueVar := c.v2 in
       (// If the value is known, test whether all possible values of indexVar + cste are 
        // valid indexes pointing to the correct value
        if (isInstantiated(valueVar) & 
            forall(idx in domain(indexVar) | 
                      ((idx + c.cste) % (1 .. length(l))) & (l[idx + c.cste] = valueVar.value)))
          true
        // Otherwise test just test whether indexVar + cste can be a valid index pointing to a possible value  
        else if exists(idx in domain(indexVar) |
                         ((idx + c.cste) % (1 .. length(l))) & (valueVar canBeInstantiatedTo l[idx + c.cste]))
          unknown
        else false)]

[choco/testIfSatisfied(c:Elt) : boolean        // v0.26 wrong interface (was checkIfSatisfied)
 -> assert(isInstantiated(c.v1) & isInstantiated(c.v2)),
    c.lval[c.v1.value + c.cste] = c.v2.value]

// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(Elt))

// second version: accessing a bidimensional array with two variable indices
// This code could be further optimized if we were caching the min and max values
// still feasible per line and per column
choco/Elt2 <: TernIntConstraint(
       tval:table[range = integer],
       dim1:integer = 0,
       dim2:integer = 0)

[self_print(c:Elt2) : void -> printf("ith-elt(~S,~S,~S == ~S",c.tval,c.v1,c.v2,c.v3)]

[private/makeElt2Constraint(l:table, n:integer, m:integer, i:IntVar, j:IntVar, x:IntVar) : Elt2
 -> Elt2(v1 = i, v2 = j, v3 = x, cste = 0, tval = l, dim1 = n, dim2 = m)]

// 0.26 a few casts
[private/updateValueFromIndex(c:Elt2) : void
 -> let l := c.tval, index1Var := c.v1, index2Var := c.v2, valueVar := c.v3,
        minval := MAXINT, maxval := MININT in
      (for feasibleIndex1:integer in domain(index1Var)
         for feasibleIndex2:integer in domain(index2Var)
            let val := (l[feasibleIndex1, feasibleIndex2] as integer) in
           (minval :min val,
            maxval :max val),
       updateInf(valueVar, minval, c.idx3),
       updateSup(valueVar, maxval, c.idx3) )]

// 0.9907: same fixes as in Elt <thb>
[private/updateIndexFromValue(c:Elt2) : void
 -> let l := c.tval, n := c.dim1, m := c.dim2, 
        index1Var := c.v1, index2Var := c.v2, valueVar := c.v3,
        minFeasibleIndex1 := max(1,index1Var.inf),
        maxFeasibleIndex1 := min(index1Var.sup,n),
        minFeasibleIndex2 := max(1,index2Var.inf),
        maxFeasibleIndex2 := min(index2Var.sup,m),
        cause1 := (if known?(bucket,valueVar) c.idx1 else 0),
        cause2 := (if known?(bucket,valueVar) c.idx2 else 0) in
   (// update index 1:      
    while ((index1Var canBeInstantiatedTo minFeasibleIndex1) &
           forall(i2 in domain(index2Var) |
                  not(valueVar canBeInstantiatedTo l[minFeasibleIndex1,i2])) )
       minFeasibleIndex1 :+ 1,       
    updateInf(index1Var,minFeasibleIndex1,cause1),
    while ((index1Var canBeInstantiatedTo maxFeasibleIndex1) &
           forall(i2 in domain(index2Var) |
                  not(valueVar canBeInstantiatedTo l[maxFeasibleIndex1,i2])) )
       maxFeasibleIndex1 :- 1,       
    updateSup(index1Var,maxFeasibleIndex1,cause1),
    if (known?(bucket,index1Var))  //those remVal would be ignored for variables using an interval approximation for domain
       for i1 in (minFeasibleIndex1 + 1 .. maxFeasibleIndex1 - 1)
            (if ((index1Var canBeInstantiatedTo i1) & 
                 forall(i2 in domain(index2Var) | not(valueVar canBeInstantiatedTo l[i1,i2])))
              removeVal(index1Var, i1, cause1)),
    // update index 2:      
    while ((index2Var canBeInstantiatedTo minFeasibleIndex2) &
           forall(i1 in domain(index1Var) |
                  not(valueVar canBeInstantiatedTo l[i1,minFeasibleIndex2])) )
       minFeasibleIndex2 :+ 1,       
    updateInf(index2Var,minFeasibleIndex2,cause2),
    while ((index2Var canBeInstantiatedTo maxFeasibleIndex2) &
           forall(i1 in domain(index1Var) |
                  not(valueVar canBeInstantiatedTo l[i1,maxFeasibleIndex2])) )
       maxFeasibleIndex2 :- 1,       
    updateSup(index2Var,maxFeasibleIndex2,cause2),
    if (known?(bucket,index2Var))  //those remVal would be ignored for variables using an interval approximation for domain
       for i2 in (minFeasibleIndex2 + 1 .. maxFeasibleIndex2 - 1)
            (if ((index2Var canBeInstantiatedTo i2) & 
                 forall(i1 in domain(index1Var) | not(valueVar canBeInstantiatedTo l[i1,i2])))
              removeVal(index2Var, i2, cause2))
   )]
            
// v0.9907: at initialization time, we propagate once for good the fact that 
//   - indices must be in the right range for accessing the integer table 
//   - all values in the domain of valueVar must correspond to a value in l        
[choco/awake(c:Elt2) : void
 -> let l := c.tval, index1Var := c.v1, index2Var := c.v2, valueVar := c.v3 in 
     (updateInf(index1Var,1,c.idx1), updateSup(index1Var,c.dim1,c.idx1),
      updateInf(index2Var,1,c.idx2), updateSup(index2Var,c.dim2,c.idx2),
      if known?(bucket,valueVar)
         for v in list{v in domain(valueVar) | 
                         not(exists(i1 in domain(index1Var) | 
                            not(exists(i2 in domain(index2Var) | 
                               v = l[i1,i2] )))) }
             removeVal(valueVar,v,c.idx3)  )]

[choco/propagate(c:Elt2) : void
 -> updateIndexFromValue(c),
    updateValueFromIndex(c)]

[choco/awakeOnInf(c:Elt2, idx:integer) : void
 -> if (idx <= 2) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

[choco/awakeOnSup(c:Elt2, idx:integer) : void
 -> if (idx <= 2) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

[choco/awakeOnInst(c:Elt2, idx:integer) : void
 -> if (idx <= 2) updateValueFromIndex(c)
    else updateIndexFromValue(c)]

[choco/awakeOnRem(c:Elt2, idx:integer,x:integer) : void
 -> if (idx <= 2) updateValueFromIndex(c)
    else let l := c.tval, n := c.dim1, m := c.dim2, index1Var := c.v1, index2Var := c.v2, valueVar := c.v3 in
            (if isInstantiated(index1Var)
                let i0 := index1Var.value in
                   for j in (1 .. m)
                     (if (l[i0,j] = x)  removeVal(index2Var, j, c.idx2))
             else if isInstantiated(index2Var)
                let j0 := index2Var.value in
                   for i in (1 .. n)
                     (if (l[i,j0] = x)  removeVal(index1Var, i, c.idx1))  )]

// v0.9907 <thb> missing methods
[choco/askIfEntailed(c:Elt2) : (boolean U {unknown})
 -> let l := c.tval, index1Var := c.v1, index2Var := c.v2, valueVar := c.v3 in
      (// If the value is known, test whether all possible pairs of values 
        // for index1Var,index2Var pointing towards the correct value
        if (isInstantiated(valueVar) &
            forall(i1 in domain(index1Var) | 
              forall(i2 in domain(index2Var) |
                l[i1,i2] = valueVar.value)))
          true
        // otherwise test just test whether there exists a pair of valid indices 
        else if exists(i1 in domain(index1Var) |
                  exists(i2 in domain(index2Var) |
                    valueVar canBeInstantiatedTo l[i1,i2]))
          unknown
        else false)]      

// v0.9907 <thb> missing methods
[choco/testIfSatisfied(c:Elt2) : boolean
 -> assert(isInstantiated(c.v1) & isInstantiated(c.v2) & isInstantiated(c.v3)),
    c.tval[c.v1.value, c.v2.value] = c.v3.value]
      
// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(Elt2))

// ********************************************************************
// *   Part 2: AllDifferent                                           *
// ********************************************************************

// v0.28: fully rewritten, no longer a global constraint
choco/AllDiff <: LargeIntConstraint()
[self_print(self:AllDiff) : void -> printf("AllDiff(~S)",self.vars)]

// ----- Constraint:  AllDifferent(Xi) ------
// forward checking propagation

[choco/awakeOnInf(c:AllDiff, idx:integer) : void -> nil]
[choco/awakeOnSup(c:AllDiff, idx:integer) : void -> nil]
[choco/awakeOnRem(c:AllDiff, idx:integer, x:integer) : void -> nil]

// Note (v0.28): the conclusion (removeVal) passes the cause of the event as
// a parameter (c.indices[i]).
// This will avoid remakening the AllDiff if l[i] undergoes only a value removal
// However, if this value removal turns out to be an instantiation,
// then, this will be considered as an event strengthening
// and the initial cause (AllDiff) will be repropagated !
[choco/awakeOnInst(c:AllDiff, idx:integer) : void
 -> let l := c.vars, n := c.nbVars, val := l[idx].value in
       for i:integer in ((1 .. idx - 1) /+ (idx + 1 .. n))
          removeVal(l[i], val, c.indices[i])]

[choco/propagate(c:AllDiff) : void
 -> let l := c.vars, n := c.nbVars in
       for i in (1 .. n)
          let v:IntVar := l[i] in
             (if isInstantiated(v)
                 let val := v.value in
                     for j:integer in ((1 .. i - 1) /+ (i + 1 .. n))
                         removeVal(l[j], val, c.indices[j]))]

[choco/testIfSatisfied(c:AllDiff) : boolean
 -> let l := c.vars, n := c.nbVars, result := true in
      (for i in (1 .. n - 1)
         let x := l[i].value in
            for j in (i + 1 .. n)
              (if (x = l[j].value) result := false),
       result)]

// v0.93 <thb>
[choco/askIfEntailed(c:AllDiff) : (boolean U {unknown})
 -> let res:(boolean U {unknown}) := unknown in
     (res := true,
      for i in (1 .. c.nbVars)
       for j in (i + 1 .. c.nbVars) 
         let vi := c.vars[i], vj := c.vars[j] in
             (if (isInstantiated(vi) & isInstantiated(vj) & vi.value = vj.value)
                 (res := false, break()) //the constraint is violated -> return false
              else if (vi canBeEqualTo vj) 
                 (res := unknown, break())), //the constraint can be violated -> return unknown      
      res)]  
;error("askIfEntailed not yet implemented on AllDiff")]

// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(AllDiff))

// ********************************************************************
// *   Part 3: occurences of a value in a list of variables           *
// ********************************************************************

// v0.28 now inheriting from LargeIntConstraint, and with a "indices" field
// v0.32 <thb>: an isSure list must be maintained.
//        Otherwise, INSTANTIATE events triggering both awake (when the occurence was in a boolean constraint) and
//        awakeOnInst could cause a double increment of nbSure. (see non-regression test "testNbSure1()" for an example)
choco/Occurrence <: LargeIntConstraint(
// claire3 port: strongly typed lists
        choco/isPossible:boolean[], // initialized by makeOccurConstraint@OccurTerm (chocapi.cl)
        choco/isSure:boolean[],     // v1.011 isPossible/isSure are now array slots
        choco/nbPossible:integer,
        choco/nbSure:integer,
        choco/constrainOnInfNumber:boolean,
        choco/constrainOnSupNumber:boolean)
(store(nbPossible, nbSure))
[self_print(self:Occurrence) : void 
  -> let n := self.nbVars, l := self.vars in
       printf("#{x in ~S | x = ~S} ~A ~S",
               list{l[i] | i in (1 .. n - 1)},self.cste,
               (if self.constrainOnInfNumber
                  (if self.constrainOnSupNumber "="
                   else ">=")
                else "<="),
               l[n])]

// ----- Constraint:  Occur(v,(Xi),y) ------

// Two Utils
//
[private/checkNbPossible(c:Occurrence) : void
 -> assert(count(list{p in c.isPossible | (p = true)}) = c.nbPossible),
    let l := c.vars, n := c.nbVars, nbVar := l[n], target := c.cste in
      (if c.constrainOnInfNumber
         (updateSup(nbVar, c.nbPossible, c.indices[n]),
          if (nbVar.inf = c.nbPossible)
             for i in {i in (1 .. n - 1) | c.isPossible[i]}
                 instantiate(l[i], target, c.indices[i])))]

[private/checkNbSure(c:Occurrence) : void
 -> let l := c.vars, n := c.nbVars, nbVar := l[n], target := c.cste in
       (assert(c.nbSure <= count(list{i in (1 .. n - 1) | l[i] isInstantiatedTo target}) ),
       // equality cannot be guaranteed in the assertion above (valid only at propagation fixpoints)
       if c.constrainOnSupNumber
         (updateInf(nbVar, c.nbSure, c.indices[n]),
          if (nbVar.sup = c.nbSure)
             //!! it assumes that if c.isPossible[i] = false, target does not belong to the domain of l[i]
             (assert(forall(i in {i in (1 .. n - 1) | not(c.isPossible[i])}
                              | not(l[i] canBeInstantiatedTo target))), //v0.31:associate an assert to the above assumption
              for i in {i in (1 .. n - 1) | (c.isPossible[i] & not(isInstantiated(l[i])))}
                 removeVal(l[i], target, c.indices[i]))) )]

// back-propagates the bounds of the counter variable on the main variables.
[choco/update(c:Occurrence) : void
 -> checkNbPossible(c),
    assert(c.nbSure <= count(list{i in (1 .. c.nbVars - 1) | c.vars[i] isInstantiatedTo c.cste})),
    checkNbSure(c)]


// propagates the values of the main variables onto the counter variable 
// v0.29: must compute (initialize) nbSure as well as nbPossible
// v0.31: maintains the isSure list
[choco/propagate(c:Occurrence) : void
 -> let l := c.vars, n := c.nbVars, target := c.cste in
      (assert(c.nbSure = count(list{i in (1 .. n - 1) | c.isSure[i]}) ),
       assert(c.nbPossible = count(list{i in (1 .. n - 1) | c.isPossible[i]}) ),
       for j in (1 .. n - 1)
          (if c.isPossible[j]
             let v := (l[j] as IntVar) in
                 (if (not(c.isSure[j]) & (v isInstantiatedTo target))
                        (store(c.isSure,j,true,true),
                         c.nbSure :+ 1)
                  else if not(v canBeInstantiatedTo target)
                        (store(c.isPossible,j,false,true),
                         c.nbPossible :- 1))),
      assert(c.nbSure = count(list{i in (1 .. n - 1) | l[i] isInstantiatedTo target}) ),
      assert(c.nbPossible = count(list{i in (1 .. n - 1) | l[i] canBeInstantiatedTo target}) ),
      update(c))]

[choco/awake(c:Occurrence) : void
 -> // v1.0 two first easy inferences
    let n := c.nbVars, nbVar := c.vars[n] in
      (if c.constrainOnInfNumber updateSup(nbVar, n, c.indices[n]),
       if c.constrainOnSupNumber updateInf(nbVar, 0, c.indices[n])),
    propagate(c)]
       
// <thb> v0.31: There is a smart propagation path, when the variables are BoundIntVar (no bucket):
// When the number of occurrences has already reached its max, then, the additional
// possible occurrences of the value should be forbidden. Therefore whenever the bound
// of a variable reaches the target value, this value can be shaved off and the bound improved by 1.
[choco/awakeOnInf(c:Occurrence, idx:integer) : void
 -> if (idx < c.nbVars)
      (if (c.isPossible[idx])
         (if (c.vars[idx].inf > c.cste)
             (store(c.isPossible,idx,false,true), c.nbPossible :- 1, checkNbPossible(c))
          else if ( (c.vars[idx].inf = c.cste) & // v0.93 <ega> inf instead of sup
                     not(c.isSure[idx]) &      // v0.33 <thb>: if the variable is already instantiated, this call to awakeOnInf should do nothing
                     c.constrainOnSupNumber &        // v0.36 <fl>
                    (c.nbSure = c.vars[c.nbVars].sup) )
              (assert(not(known?(bucket,c.vars[idx]))), // otherwise should have been deduced when nbSure is modified
               updateInf(c.vars[idx],c.cste + 1,c.indices[idx])) )) // 0.93 <ega> + 1 instead of - 1
    else checkNbPossible(c)]

[choco/awakeOnSup(c:Occurrence, idx:integer) : void
 -> if (idx < c.nbVars)
      (if (c.isPossible[idx])
         (if (c.vars[idx].sup < c.cste)
             (store(c.isPossible,idx,false,true), c.nbPossible :- 1, checkNbPossible(c))
          else if ( (c.vars[idx].sup = c.cste) &
                     not(c.isSure[idx]) &      // v0.33 <thb>: if the variable is already instantiated, this call to awakeOnInf should do nothing
                     c.constrainOnSupNumber &        // v0.36 <fl>
                    (c.nbSure = c.vars[c.nbVars].sup) )
              (assert(not(known?(bucket,c.vars[idx]))), // otherwise should have been deduced when nbSure is modified
               updateSup(c.vars[idx],c.cste - 1,c.indices[idx])) ))
    else checkNbSure(c)]

// v0.32: maintain c.isSure
[choco/awakeOnInst(c:Occurrence, idx:integer) : void
 -> if (idx < c.nbVars & c.isPossible[idx] & not(c.isSure[idx])) //v0.31 if c.isSure[idx]=true, then it has already been taken in account
       (if (c.vars[idx].value = c.cste)
           (store(c.isSure,idx,true,true), c.nbSure :+ 1, checkNbSure(c) )
        else (store(c.isPossible,idx,false,true), c.nbPossible :- 1, checkNbPossible(c)))
    else (checkNbSure(c), checkNbPossible(c))]

[choco/awakeOnRem(c:Occurrence, idx:integer, x:integer) : void
 -> if (idx < c.nbVars & x = c.cste & c.isPossible[idx] & known?(bucket,c.vars[idx]))
       (store(c.isPossible,idx,false,true), c.nbPossible :- 1, checkNbPossible(c)) ]

// <thb> v0.93
[choco/testIfSatisfied(c:Occurrence) : boolean
 -> c.vars[c.nbVars] = count(list{i in (1 .. c.nbVars - 1) | (c.vars[i].value = c.cste)})]  // v0.9907 <thb>

// <thb> v0.93
[choco/askIfEntailed(c:Occurrence) : (boolean U {unknown})
 -> let occ := (c.vars[c.nbVars] as IntVar),
;        nbS := count(list{i in (1 .. c.nbVars - 1) | c.vars[i] isInstantiatedTo c.cste}),
;        nbP := count(list{i in (1 .. c.nbVars - 1) | c.vars[i] canBeInstantiatedTo c.cste}) in
         nbS := 0, nbP := 0 in 
        (for i in (1 .. c.nbVars - 1)
            let v := c.vars[i] in 
              (if (v isInstantiatedTo c.cste) nbS :+ 1,
               if (v canBeInstantiatedTo c.cste) nbP :+ 1),
         if (c.constrainOnSupNumber & c.constrainOnInfNumber)
             (if (isInstantiated(occ) & (nbS = nbP) & (occ.value = nbS)) true 
              else if (nbP < occ.inf | nbS > occ.sup) false
              else unknown)         
         else if c.constrainOnInfNumber    
             (if (nbS >= occ.sup) true 
              else if (nbP < occ.inf) false
              else unknown)
         else if c.constrainOnSupNumber 
             (if (nbP <= occ.inf) true 
              else if (nbS > occ.sup) false
              else unknown)
         else error("Stop and debug: constrainOnSupNumber or constrainOnInfNumber must be true"))]

// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
;(#if (compiler.active? & compiler.loading?) register(Occurrence))
