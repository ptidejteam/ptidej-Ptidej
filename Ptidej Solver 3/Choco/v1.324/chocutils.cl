// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: utils.cl                                                   *
// *    common utilities & data structures                            *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: Global parameters                                      *
// *   Part 2: simple utilities (min,max,etc.)                        *
// *   Part 3: data structure utilities                               *
// *   Part 3: The generic object hierarchy                           *
// *   Part 4: Variables                                              *
// *   Part 5: Propagation events                                     *
// *   Part 6: Constraints (objects, creation, generic methods)       *
// *   Part 7: compiler optimization                                  *
// *   Part 8: Problems & constraint networks                         *
// --------------------------------------------------------------------

// ********************************************************************
// *   Part 1: simple utilities (min,max,etc.)                        *
// ********************************************************************

// utils: integer divisions (rounded down for div- and up for div+)
// we suppose that b is always > 0
// (too bad: the integer division of C (and so of Claire) is rounded
//  up for negative integers and down for positive ones ....)
//
div- :: operation(precedence = precedence(/))
div+ :: operation(precedence = precedence(/))
[div-(a:integer, b:integer) : integer
 -> assert(b != 0),
    if (b < 0) div-(-(a),-(b))  // <thb> v0.97
    else let r := a / b in
           (if (a >= 0 | r * b = a) r else r - 1)]

[div+(a:integer, b:integer) : integer
 -> assert(b != 0),
    if (b < 0) div+(-(a),-(b))  // <thb> v0.97
    else let r := a / b in
           (if (a <= 0 | r * b = a) r else r + 1)]

// the largest bound for integers are [-1073741823, 1073741823],
// but we use a slightly restricted range, with easier to recognize values
// 0.34: MAXINT - MININT no longer produces an overflow
MAXINT :: 99999999
MININT :: -99999999

// utils specific for claire3 port
[claire/max(x:integer, y:integer) : integer
 => if (x >= y) x else y]
[claire/min(x:integer, y:integer) : integer
 => if (x >= y) y else x]

// Utils : maximum and minimum of a collection of integers
// v1.02 lowercase those function names
[claire/max(x:(list[integer] U set[integer])) : integer
 => let s := MININT in (for y in x s :max y, s)]

[claire/min(x:(list[integer] U set[integer])) : integer
 => let s := MAXINT in (for y in x s :min y, s)]

[claire/sum(x:(list[integer] U set[integer])) : integer
 => let s := 0 in (for y in x s :+ y, s)]

[claire/product(x:(list[integer] U set[integer])) : integer
 => let p := 1 in (for y in x p :* y, p)]

[claire/count(S:any) : integer
 => let s := 0 in
      (for %x in S s :+ 1, s)]

[claire/abs(v:integer) : integer => if (v < 0) -(v) else v]
[claire/abs(v:float) : float -> if (v < 0.0) -(v) else v]

[claire/stringFormat(n:integer,k:integer) : string
 -> if (k <= 0) ""
    else if (n < 0) ("-" /+ stringFormat(-(n), k - 1))
    else if (n = 0) make_string(max(k,0),' ')
    else if (n <= 9) string!(n) /+ stringFormat(0,k - 1)
    else if (n <= 99) string!(n) /+ stringFormat(0,k - 2)
    else if (n <= 999) string!(n) /+ stringFormat(0,k - 3)
    else string!(n) /+ stringFormat(0,k - 4)]

[claire/stringFormat(s:string,k:integer) : string
 -> if (length(s) >= k) substring(s,1,k)
    else (s /+ make_string(k - length(s), ' '))]

[claire/random(l:list) : any
 -> let n := length(l) in l[1 + random(n)] ]

// 0.26 casts for improved compilation
// claire3 port: module System has disappeared
[claire/random(I:Interval) : integer
 -> I.arg1 + random(1 + I.arg2 - I.arg1) ]

[claire/random(a:integer, b:integer) : integer
 -> assert(a <= b),
    a + random(b - a + 1) ]

// used in interpreted mode (the optimized iterator is already in Claire)
; [/+(x:(Interval U set),y:(Interval U set)) : list -> list!(set!(x)) /+ list!(set!(y))]
[/+(x:Interval,y:Interval) : list -> list!(set!(x)) /+ list!(set!(y))] // v0.35
[/+(x:Interval,y:set) : list -> list!(set!(x)) /+ list!(y)]
[/+(x:set,y:Interval) : list -> list!(x) /+ list!(set!(y))]

// mapping booleans to -1/+1 coefficients
[integer!(b:boolean) : integer => (if b 1 else -1)]  // 0.26 type integer result

UNKNOWNINT :: -100000000
[knownInt(x:integer) : boolean -> (x != UNKNOWNINT)]

// ********************************************************************
// *   Part 2: backtrackable integers                                 *
// ********************************************************************

// root class for all generic data structure utilities
choco/Util <: ephemeral_object()

// utility: storing a backtrackable integer with a time stamp, so that
// only one update is recorded per world
// (cf Claire documentation, end of Part 2, page 15)
// new in v0.37
claire/StoredInt <: Util(
    claire/latestValue:integer = 0,   // current (latest) value
    claire/latestUpdate:integer = 0)  // index of the latest world in which the StoredInt was modified
[self_print(x:StoredInt) : void -> princ(x.latestValue)]

[write(x:StoredInt, y:integer) : void
 -> if (y != x.latestValue)      // v1.010 <ega>
      let currentWorld := world?() in
       (if (currentWorld > x.latestUpdate)
           (put_store(latestValue,x,y,true),
            put_store(latestUpdate,x,currentWorld,true))
        else x.latestValue := y)]

[read(x:StoredInt) : integer => x.latestValue]

// ********************************************************************
// *   Part 3: Matrices                                               *
// ********************************************************************

// creating unnamed arrays
choco/Matrix <: Util(
          backtrackable:boolean)

choco/Matrix2D <: Matrix(
          size1:integer = -1,
          offset1:integer = 0,
          size2:integer = -1,
          offset2:integer = 0)

choco/MatrixND <: Matrix(
          dim:integer = 0,
          lsizes:integer[],
          offsetArray:integer[])

choco/BoolMatrix2D <: Matrix2D(contents:boolean[])
choco/BoolMatrixND <: MatrixND(contents:boolean[])
choco/IntMatrix2D <: Matrix2D(contents:integer[])
choco/IntMatrixND <: MatrixND(contents:integer[])

// creates a bi-dimensional matrix storing values
// for pairs of indices in (a1 .. a2, b1 .. b2)
[choco/make2DBoolMatrix(a1:integer,a2:integer,
                  b1:integer, b2:integer, default:boolean, stored:boolean) : BoolMatrix2D
 -> let s1 := a2 - a1 + 1, s2 := b2 - b1 + 1 in
      (assert(s1 > 0), assert(s2 > 0),
       BoolMatrix2D(contents = make_array(s1 * s2, boolean, default),
                   backtrackable = stored,
                   size1 = s1,  size2 = s2,
                   offset1 = a1,offset2 = b1))]
[choco/make2DIntMatrix(a1:integer,a2:integer,
                  b1:integer, b2:integer, default:integer, stored:boolean) : IntMatrix2D
 -> let s1 := a2 - a1 + 1, s2 := b2 - b1 + 1 in
      (assert(s1 > 0), assert(s2 > 0),
       IntMatrix2D(contents = make_array(s1 * s2, integer, default),
                   backtrackable = stored,
                   size1 = s1,  size2 = s2,
                   offset1 = a1,offset2 = a2))]

// claire3 port: module System has disappeared, type arrays
[choco/makeNDBoolMatrix(l:list[Interval], default:boolean, stored:boolean) : BoolMatrixND
 -> let n := length(l),
        ls := list<integer>{(itv.arg2 - itv.arg1 + 1) | itv in l} in
      (BoolMatrixND(contents = make_array(product(ls),boolean, default), // v1.02 product vs. Product
                   backtrackable = stored,
                   dim = n, // v1.008
                   lsizes = array!(ls),
                   offsetArray = array!(list<integer>{itv.arg1 | itv in l})))]

// claire3 port: module System has disappeared, type arrays
[choco/makeNDIntMatrix(l:list[Interval], default:integer, stored:boolean) : IntMatrixND
 -> let n := length(l),
        ls := list<integer>{(itv.arg2 - itv.arg1 + 1) | itv in l} in
      (IntMatrixND(contents = make_array(product(ls),integer, default), // v1.02 product vs. Product
                   backtrackable = stored,
                   dim = n, // v1.008
                   lsizes = array!(ls),
                   offsetArray = array!(list<integer>{itv.arg1 | itv in l})))]

// matrices of booleans or integer in dimension 2
[private/flatIndex(m:Matrix2D,i:integer,j:integer) : integer
 -> //[6] flatIndex o1:~S, s1:~S, i:~S // m.offset1, m.size1, i,
    assert(m.offset1 <= i & i <= m.offset1 + m.size1 - 1),
    //[6] flatIndex o2:~S, s2:~S, j:~S // m.offset2, m.size2, j,
    assert(m.offset2 <= j & j <= m.offset2 + m.size2 - 1),
    m.size1 * (j - m.offset2) + (i - m.offset1) + 1]

[choco/read(m:BoolMatrix2D,i:integer,j:integer) : boolean
 -> m.contents[flatIndex(m,i,j)] ]
[choco/store(m:BoolMatrix2D,i:integer,j:integer,x:boolean) : void
 -> store(m.contents,flatIndex(m,i,j),x,m.backtrackable) ]
[choco/read(m:IntMatrix2D,i:integer,j:integer) : integer
 -> m.contents[flatIndex(m,i,j)] ]
[choco/store(m:IntMatrix2D,i:integer,j:integer,x:integer) : void
 -> store(m.contents,flatIndex(m,i,j),x,m.backtrackable) ]

// matrices of booleans or integer in arbitrary dimensions
[private/flatIndex(m:MatrixND,l:list[integer]) : integer
 -> assert(length(l) = m.dim),
    assert(forall(i in (1 .. m.dim) | m.offsetArray[i] <= l[i] &
                                      l[i] <= m.offsetArray[i] + m.lsizes[i] - 1)),
    let n := length(l), loff := m.offsetArray, s := (l[n] - loff[n]) in  // v1.008
      (for i in (1 .. n - 1)
           let ithCoordinate := l[n - i] - loff[n - i] in
               s := s * m.lsizes[n - i] + ithCoordinate,   // v1.008
       s + 1)]
[choco/read(m:BoolMatrixND,l:list[integer]) : boolean
 -> m.contents[flatIndex(m,l)]]
[choco/store(m:BoolMatrixND,l:list[integer],x:boolean) : void
 -> store(m.contents,flatIndex(m,l),x,m.backtrackable)]
[choco/read(m:IntMatrixND,l:list[integer]) : integer
 -> m.contents[flatIndex(m,l)]]
[choco/store(m:IntMatrixND,l:list[integer],x:integer) : void
 -> store(m.contents,flatIndex(m,l),x,m.backtrackable)]

// ********************************************************************
// *   Part 4: associations of keys to values from a set              *
// ********************************************************************

// utilities for associating annotations to each value for a set
//  an abstract class for associating a (backtrackable) annotation to each value from a set of integers
IntSetAnnotation <: Util(
   offset:integer = 0,
   asize:integer = 0)                    // size of the nbSupport vectors
[choco/getOriginalMin(annot:IntSetAnnotation) : integer -> annot.offset]
[choco/getOriginalMax(annot:IntSetAnnotation) : integer -> annot.offset + annot.asize - 1]

// subclass for integer annotations   
IntSetIntAnnotation <: IntSetAnnotation(
   intValues:integer[])
;(store(intValues)) // updates are stored, but it is useless to declare it (updates with store/4)
// get/set methods
[getIntAnnotation(ida:IntSetIntAnnotation, val:integer) : integer
 => ida.intValues[val - ida.offset]]
[setIntAnnotation(ida:IntSetIntAnnotation, val:integer, annot:integer) : void
 => store(ida.intValues, val - ida.offset, annot, true)] 
// constructor 
[makeIntSetIntAnnotation(min:integer, max:integer, def:integer) : IntSetIntAnnotation
 -> let n := max - min + 1 in
        IntSetIntAnnotation(offset = min - 1, 
                            asize = n,
                            intValues = make_array(n,integer,def)) ]

// subclass for boolean annotations   
IntSetBoolAnnotation <: IntSetAnnotation(
   boolValues:boolean[])
;(store(boolValues)) // updates are stored, but it is useless to declare it (updates with store/4)
// get/set methods
[getBoolAnnotation(ida:IntSetBoolAnnotation, val:integer) : boolean
 => ida.boolValues[val - ida.offset]]
[setBoolAnnotation(ida:IntSetBoolAnnotation, val:integer, annot:boolean) : void
 => store(ida.boolValues, val - ida.offset, annot, true)] 
// constructor 
[makeIntSetBoolAnnotation(min:integer, max:integer, default:boolean) : IntSetBoolAnnotation
 -> let n := max - min + 1 in 
        IntSetBoolAnnotation(offset = min - 1, 
                             asize = n,
                             boolValues = make_array(n,boolean,default)) ]

// ********************************************************************
// *   Part 5: Bipartite sets                                         *
// ********************************************************************
//  <thierry Benoist> v0.9906

// This data structure contains a set of values divided into two parts (left and right).
// A loop over one part takes a time proportionnal to the size of this part (an not to the total size of the set).
// Moving one element from one part to the other takes the time of getting this element in the set through a hastable.
BipartiteSet <: Util(
;      of:type,                  // type of the elements of the set                                                      
      choco/defaultValue:any,   // v1.06 default value to be used when creating the arrays
      choco/objs:array,         // internal array containing the elements
      choco/nbLeft:integer = 0, // nb of element in the left part
      choco/indices:table[range = integer]) //hashtable: indices[objs[i]] = i 

// util: build a table with:
//       - domain: 'memberType'
//       - range: integer 
//       - defaultValue:0
[private/makeIndexesTable(memberType:type) : table[range = integer]
-> let t := (mClaire/new!(table) as table) in // claire3 port: make_object -> new
     (put(range,t,integer),
      put(domain,t,memberType),
      write(default,t,0),
      // claire3 port: module System has been replaced by mClaire
      write(mClaire/graph,t,make_list(29,unknown)),
      t)]
[makeBipartiteSet(memberType:type, default:any) : BipartiteSet
-> BipartiteSet(defaultValue = default,   // v1.06
                objs = make_array(0,memberType,default), 
                nbLeft = 0,
                indices = makeIndexesTable(memberType))]

// Create an empty typed BipartiteSet
// v1.06: add a parameter
;[makeBipartiteSet(default:any) : BipartiteSet
;-> BipartiteSet(defaultValue = default,
;                objs = make_array(0,object,default), 
;                nbLeft = 0,
;                indices = make_table(object,integer,0))]

// Create a BipartiteSet from a left part and a right part
// v1.06: add a parameter
;[makeBipartiteSet(memberType:type,default:any,left:set,right:set) : BipartiteSet
;-> let b := BipartiteSet(of = memberType,
;                         defaultValue = default,  // v1.06
;                         objs = make_array(size(left) + size(right),memberType,default),
;                          // claire3 port: stop using array! on untyped lists
;                         nbLeft = size(left),
;                         indices = make_table(memberType,integer,0)) in
;;                         indices = makeIndexesTable(memberType)) in 
;      (for i in (1 .. size(left)) b.objs[i] := left[i],
;       for i in (1 .. size(right)) b.objs[i] := right[i],      
;       for i in (1 .. length(b.objs)) b.indices[b.objs[i]] := i,
;       b)]                           

// Exchange elements at indices idx1 and idx2.
[private/swap(b:BipartiteSet,idx1:integer,idx2:integer) : void
-> if (idx1 != idx2) let obj1 := b.objs[idx1], obj2 := b.objs[idx2] in
    (b.objs[idx1] := obj2,
     b.objs[idx2] := obj1,
     b.indices[obj1] := idx2,
     b.indices[obj2] := idx1)]

// Move an object to the left part of the set.
// Throws an error if the bipartite set does not contain this object.
// claire 3 port: there seems to a bug with second order types
;[moveLeft(b:BipartiteSet[of = X],obj:X) : void 
[moveLeft(b:BipartiteSet,obj:any) : void
-> let idx := (b.indices[obj] as integer) in 
        (if (idx = 0) error("~S does not contain ~S",b,obj)
         else if (idx > b.nbLeft)  //not already in the left part
              (swap(b,idx,b.nbLeft + 1), b.nbLeft :+ 1))]


// Move an object to the right part of the set.
// Throws an error if the bipartite set does not contain this object.   
[moveRight(b:BipartiteSet,obj:any) : void 
-> let idx := (b.indices[obj] as integer) in 
        (if (idx = 0) error("~S does not contain ~S",b,obj)
         else if (idx <= b.nbLeft) //not already in the right part
             (swap(b,idx,b.nbLeft), b.nbLeft :- 1))]

// Move all element to the left part of the set.   
[moveAllLeft(b:BipartiteSet) : void  -> b.nbLeft := length(b.objs)]      
// Move all element to the right part of the set.   
[moveAllRight(b:BipartiteSet) : void -> b.nbLeft := 0]

// adding a new object to the set
[addRight(b:BipartiteSet,obj:any) : void 
-> let oldobjs := b.objs in 
     (b.objs := make_array(length(oldobjs) + 1,object,b.defaultValue),
      for i in (1 .. length(oldobjs)) b.objs[i] := oldobjs[i]),  // v1.06
      // claire3 port: stop using array! on untyped lists
   b.objs[length(b.objs)] := obj,
   b.indices[obj] := length(b.objs)]
;[addLeft(b:BipartiteSet[of = X],obj:X) : void 
[addLeft(b:BipartiteSet,obj:any) : void 
-> addRight(b,obj), moveLeft(b,obj)]

// Returns true if the specified object belongs to the left part of the set.
// Returns false if the specified object belongs to the right part of the set.
// Throws an error if the bipartite set does not contain this object.   
[isLeft(b:BipartiteSet,obj:any) : boolean
-> let idx := (b.indices[obj] as integer) in
        (if (idx = 0) error("~S does not contain ~S",b,obj)
         else (idx <= b.nbLeft))]

// v0.9907
// returns true if the object is present in the bipartition
[isIn(b:BipartiteSet,obj:any) : boolean
-> (b.indices[obj] != 0)]

// Returns the number of elements in the left part of the set
[choco/getNbLeft(b:BipartiteSet)  : integer => b.nbLeft]
// Returns the number of elements in the right part of the set
[choco/getNbRight(b:BipartiteSet) : integer => length(b.objs) - b.nbLeft]
// Returns the overall number of elements in the bipartite set
[choco/getNbObjects(b:BipartiteSet)  : integer => length(b.objs)]
// indexed access to an object
;[choco/getObject(b:BipartiteSet[of = X],i:integer) : type[X]
[choco/getObject(b:BipartiteSet,i:integer) : any
 => assert(i >= 0 & i <= length(b.objs)), b.objs[i]]


// Returns the left part of the set
[leftPart(b:BipartiteSet) : set  -> {b.objs[i] | i in (1 .. b.nbLeft)}]

// Returns the right part of the set
[rightPart(b:BipartiteSet) : set -> {b.objs[i] | i in (b.nbLeft + 1 .. length(b.objs))}]


// Optimized iteration on the left part of the set (without allocation)
[Iterate(x:leftPart[tuple(BipartiteSet)],v:Variable,e:any)
=> let b:BipartiteSet := (eval(nth(args(x),1)) as BipartiteSet) in
      (for i in (1 .. b.nbLeft) 
          let v := b.objs[i] in e)]

// Optimized iteration on the left part of the set (without allocation)
[Iterate(x:rightPart[tuple(BipartiteSet)],v:Variable,e:any)
=> let b:BipartiteSet := (eval(nth(args(x),1)) as BipartiteSet) in
      (for i in (b.nbLeft + 1 .. length(b.objs)) 
          let v := b.objs[i] in e)]                             

// ********************************************************************
// *   Part 6: Integers as bitvectors                                 *
// ********************************************************************

[getBitCount(bv:integer) : integer
 -> let cnt := 0 in 
     (for i in (0 .. 29)
         (if bv[i] cnt :+ 1),
      cnt)]
// those two methods return integers between 0 and 29
[getMinBitIndex(bv:integer) : integer
 -> assert(bv != 0),
    let i := 0 in 
     (while not(bv[i]) i :+ 1,
      i)]
[getMaxBitIndex(bv:integer) : integer
 -> assert(bv != 0),
    let i := 29 in 
     (while not(bv[i]) i :- 1,
      i)]

// ********************************************************************
// *   Part 6: Bitvectors                                             *
// ********************************************************************
          
// An encoding of sets with a list of integers taken as bitvectors 
// bitvetor[word][idx] tells if (minValue + 30(word-1) + idx) is in the set
//   this is valid for 0<=word<=length(bitvector), 0<=idx<=29
//        x = minv + 30(w-1) + idx
//        idx = (x - minv) mod 30
//        w   = (x - minv) / 30 + 1 

// utility: creates a bitvector with n "ones": 11111... (from position 0 to n - 1)
[makeAllOnesBitVector(n:integer) : integer
 -> assert(n >= 1 & n <= 30),
    ^2(n - 1) - 1 + ^2(n - 1)]
[choco/getBitVectorList(minv:integer, l:list<integer>) : list<integer>
 -> let res := list<integer>() in 
       (for w in (1 .. length(l))
          let bv := l[w] in 
            for i in (0 .. 29)
              (if bv[i] 
                  add(res, minv + (w - 1) * 30 + i)),
        res)]
[choco/isInBitVectorList(idx:integer, l:list<integer>) : boolean
 -> let w := idx / 30 + 1, i := idx mod 30 in 
        l[w][i]]
// backtrackable addition to a bit vector. Returns a boolean indicating whether the addition was really performed
// or whether the element was already present
[choco/addToBitVectorList(idx:integer, l:list<integer>) : boolean
 -> let w := idx / 30 + 1, i := idx mod 30, bv := l[w] in 
       (if not(bv[i])
          (store(l,w,bv + ^2(i),true), true)
        else false)]
[choco/remFromBitVectorList(idx:integer, l:list<integer>) : boolean
 -> let w := idx / 30 + 1, i := idx mod 30, bv := l[w] in 
       (if bv[i]
          (store(l,w,bv - ^2(i),true), true)
        else false)]

[choco/getBitVectorListCount(l:list<integer>) : integer
 -> let n := 0 in 
       (for w in (1 .. length(l))
          let bv := l[w] in 
            for i in (0 .. 29)
              (if bv[i] n :+ 1),
        n)]
        
[choco/getBitVectorInf(l:list<integer>) : integer
 -> let bvi := 1, n := length(l), res := 0, found := false in 
      (while (not(found) & bvi <= n)
         let bv := l[bvi] in 
           (if (bv = 0) res :+ 30
            else (found := true,
                  res :+ getMinBitIndex(bv)),
            bvi :+ 1),
        if found res
        else MAXINT)]
                                           
[choco/getBitVectorSup(l:list<integer>) : integer
 -> let n := length(l), bvi := n, res := 30 * (n - 1), found := false in 
      (while (not(found) & bvi >= 0)
         let bv := l[bvi] in 
           (if (bv = 0) res :- 30
            else (found := true,
                  res :+ getMaxBitIndex(bv)),
            bvi :- 1),
        if found res
        else MININT)]         
