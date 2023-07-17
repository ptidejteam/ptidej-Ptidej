// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: dom.cl                                                     *
// *    encoding variable domains                                     *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: abstract IntVar domains                                *
// *   Part 2: implementing IntVar domains by enumerations of values  *
// *   Part 3: abstract SetVar domains                                *
// *   Part 4: implementing SetVar domains by bitvectors              *
// *   Part 5: implementing SetVar domains by bitvector lists         *
// --------------------------------------------------------------------

// Abstract class for domain implementations, no interface methods
choco/AbstractDomain <: collection() // v1.011: required in order to be able to iterate the object
(ephemeral(choco/AbstractDomain))

// Abstract class for domain implementations of integer variables
choco/AbstractIntDomain <: AbstractDomain()
// Interface of AbstractIntDomain documented in file iprop.cl
choco/containsValInDomain :: property()
choco/remove :: property()
choco/restrict :: property()

choco/AbstractSetDomain <: AbstractDomain

// ********************************************************************
// *   Part 1: abstract IntVar domains                                *
// ********************************************************************

// Interface of AbstractIntDomain:
//   the following methods should be defined on subclasses XXXIntDomain of AbstractIntDomain:
//     getDomainCard(d:XXXIntDomain) : integer
//     restrict(d:XXXIntDomain,s:set[integer]):void
//     restrict(d:XXXIntDomain,x:integer):void
//     removeDomainVal(d:XXXIntDomain,x:integer):void
//     updateDomainInf(d:XXXIntDomain,x:integer):void
//     updateDomainSup(d:XXXIntDomain,x:integer):void
//     getDomainInf(d:XXXIntDomain):integer
//     getDomainSup(d:XXXIntDomain):integer
//     containsValInDomain(d:XXXIntDomain,x:integer):boolean

choco/getDomainCard :: property(range = integer)
choco/getNextValue :: property(range = integer)
choco/getPrevValue :: property(range = integer)
choco/removeDomainVal  :: property(range = boolean)
choco/domainSequence :: property(range = list<integer>)
choco/domainSet :: property(range = set<integer>)

// v1.04
[choco/domainSequence(d:AbstractIntDomain) : list<integer>
-> error("the domainSequence method has not been implemented on ~S",d),
   list<integer>()]
[choco/domainSet(d:AbstractIntDomain) : set<integer>
-> error("the domainSequence method has not been implemented on ~S",d),
   set<integer>()]
[choco/getDomainInf(d:AbstractIntDomain) : integer
 -> error("the getDomainInf method has not been implemented on ~S",d), 0]
[choco/getDomainSup(d:AbstractIntDomain) : integer
 -> error("the getDomainSup method has not been implemented on ~S",d), 0]
[choco/updateDomainInf(d:AbstractIntDomain,x:integer) : integer
 -> error("the updateDomainInf method has not been implemented on ~S",d), 0]
[choco/updateDomainSup(d:AbstractIntDomain,x:integer) : integer
 -> error("the updateDomainSup method has not been implemented on ~S",d), 0]
[choco/containsValInDomain(d:AbstractIntDomain, x:integer) : boolean
 -> error("the containsValInDomain method has not been implemented on ~S",d), true]
[choco/removeDomainVal(d:AbstractIntDomain,x:integer) : boolean
 -> error("the removeDomainVal method has not been implemented on ~S",d), true]
[choco/restrict(d:AbstractIntDomain,x:integer) : void
 -> error("the restrict method has not been implemented on ~S",d)]
[choco/getDomainCard(d:AbstractIntDomain) : integer
 -> error("the getDomainCard method has not been implemented on ~S",d), 1]
[choco/getNextValue(d:AbstractIntDomain, x:integer) : integer
 -> error("the getNextValue method has not been implemented on ~S",d), 0]
// v1.016 from franck:
[choco/getPrevValue(d:AbstractIntDomain, x:integer) : integer
 -> error("the getPrevValue method has not been implemented on ~S",d), 0]
(interface(domainSequence), interface(domainSet),
 interface(updateDomainInf),interface(updateDomainSup),
 interface(getDomainInf), interface(getDomainSup), interface(containsValInDomain),
 interface(removeDomainVal), interface(restrict),
 interface(getDomainCard), 
 interface(getNextValue),
 interface(getPrevValue)
)
(interface(AbstractIntDomain,
             domainSequence,
             domainSet,
             updateDomainInf,
             updateDomainSup,
             getDomainInf,
             getDomainSup,
             containsValInDomain,
             getNextValue,
             getPrevValue,  // <franck> v1.016
             removeDomainVal,
             restrict,
             getDomainCard
             ))
             
// ********************************************************************
// *   Part 2: implementing IntVar domains by enumerations of values  *
// ********************************************************************

// Interface of domains
claire/DINF :: -1000000
claire/DSUP ::  1000000


// An encoding of enumerated domains as linked lists
// we use an array of pointer indices: contents
//   contents[i] = i      <=> i is a possible value (OK)
//   contents[i] = j > i  <=> i, i+1, ..., j-1 non-OK, j probably OK
//   contents[i]  = MAXINT  <=> i, ..., dim non-OK
//
choco/LinkedListIntDomain <: AbstractIntDomain(
       choco/offset:integer = 0,    // v0.9907 no longer private fields
       choco/bucketSize:integer = 0,
       choco/contents:integer[])
store(bucketSize)
// v1.02 <ebo>, <fxj>, v1.08 <fl> lighter printing
[self_print(x:LinkedListIntDomain) : void
 -> printf("[~S]~I",x.bucketSize,
           (let s := domainSet(x), si := size(s) in
              (if (si <= 4) print(s)
               else printf("{~S,~S...~S,~S}",s[1],s[2],s[si - 1],s[si])))) ]

// automatically called when
[choco/makeLinkedListIntDomain(a:integer, b:integer) : LinkedListIntDomain
 -> assert(a <= b),
    assert(b <= a + 10000),  // we refuse such domains with too large a span (consumes too much memory)
    let n := b - a + 1 in
       LinkedListIntDomain(bucketSize = n,
                           offset = a - 1,
                           contents = array!(list<integer>{i | i in (1 .. n)} add MAXINT)) ]  // v1.0, now an array
// claire3 port use array only on strongly typed lists

// a first utility
[random(d:LinkedListIntDomain) : integer
 -> let l := d.contents, i := 1 + random(length(l)) in  // fix v0.27 length vs. size
      (while (l[i] != i)
          i := 1 + random(length(l)),   // fix v0.27 length vs. size
       i + d.offset) ]

// Implementing the interface from AbstractDomain: primitives for iteration
// v1.04
[choco/domainSet(d:LinkedListIntDomain) : set<integer>
 -> let s := set<integer>(), l := d.contents, i := l[1] in
       (while (i != MAXINT)
         (s :add (i + d.offset), i := l[i + 1]),
        s)]
// this is necessarliy sorted by increasing order of values
// v1.04
[choco/domainSequence(d:LinkedListIntDomain) : list<integer>
 -> let s := list<integer>(), l := d.contents, i := l[1] in
       (while (i != MAXINT)
         (s :add (i + d.offset), i := l[i + 1]),
        s)]

;// used for iteration in compiled code: do not built the intentional set (does NOT allocate)
;[iterate(d:LinkedListIntDomain,v:Variable,e:any)
; => let delta := d.offset, i := d.contents[1] in
;       while (i != MAXINT)
;          (if (d.contents[i] = i)
;             let v := i + delta in e,      // we test that the value is indeed present
;	   i := d.contents[i + 1])]	   // (the bucket may be modified by e !!)

// Implementing the interface from AbstractIntDomain
// Accessors
[choco/getDomainInf(d:LinkedListIntDomain) : integer
 => let l := d.contents in
      (assert(l[1] != MAXINT),    // check: non empty domain
       l[1] + d.offset)]

[choco/getDomainSup(d:LinkedListIntDomain) : integer
 => let l := d.contents, i := length(l) - 1 in // v0.28: size vs. length
      (assert(l[1] != MAXINT),    // check: non empty domain
       while (l[i] = MAXINT) i :- 1,
       assert(l[i] = i),        // check that the last pointer is a feasible index
       l[i] + d.offset) ]

// v0.9901: submethod of domainIncludedIn
// supposed that [v.inf,v.sup] is included in [l[1],last(l)]     
// v0.9907 new name
[choco/isIncludedIn(b:LinkedListIntDomain,l:list[integer]) : boolean
 -> forall(x in b | x % l)]

// v0.9907
[choco/getDomainCard(d:LinkedListIntDomain) : integer -> d.bucketSize]

[choco/containsValInDomain(d:LinkedListIntDomain, x:integer) : boolean
 -> let l := d.contents, i := x - d.offset in   // i is the index corresponding to x
      (assert(i >= 1 & i < length(l)),          // safety check: valid index (fix v0.27 length vs. size)
       l[i] = i)]

// v1.013
[choco/getNextValue(d:LinkedListIntDomain, x:integer) : integer
 -> let l := d.contents, o := d.offset, i := x - o in
       (assert(i >= 1 & i < length(l)),          // safety check: valid index
        l[i + 1] + o)]

// v1.016 from franck:
[choco/getPrevValue(d:LinkedListIntDomain, x:integer) : integer
 -> let l := d.contents, o := d.offset, i := x - o in
       (assert(i >= 1 & i < length(l)),          // safety check: valid index
        i :- 1,
        while (l[i] > i) i :- 1,
        assert(l[i] = i),        // check that the last pointer is a feasible index
        l[i] + o)]

// Modifiers (update functions)
[choco/removeDomainVal(d:LinkedListIntDomain,x:integer) : boolean
 -> let l := d.contents, i := x - d.offset in   // i is the index corresponding to x
      (assert(i >= 1 & i < length(l)),          // safety check: valid index (fix v0.27 length vs. size)
       if (l[i] = i)                            // if i was present in the domain
         let k := l[i + 1], j := i - 1 in
           (d.bucketSize :- 1,                        //    decrease cardinal
            store(l,i,k,true),                  //    i points to the next feasible value k
            while (j >= 1 & l[j] = i)           //    all previous infeasible indices
                  (store(l,j,k,true), j :- 1),  //    pointing on i now point on k
            //[DDEBUG] after removal(~S) => bucket:~S // x,d,
            true)
       else false)]

[choco/restrict(d:LinkedListIntDomain,x:integer) : void
 -> let l := d.contents, i := x - d.offset in   // i is the index corresponding to x
      (assert(i >= 1 & i < length(l)),          // safety check: valid index (fix v0.27 length vs. size)
       assert(l[i] = i),                        // i is already present in the domain
       for j in (1 .. i - 1)
           store(l,j,i,true),
       for j in (i + 1 .. length(l) - 1)    // fix v0.27 length vs. size
           store(l,j,MAXINT,true),
       d.bucketSize := 1, // v0.36 <michel>
       //[DDEBUG] after restrict(~S) => bucket:~S // x,d
       )]

// returns the new value of the lower bound (at least x)
[choco/updateDomainInf(d:LinkedListIntDomain,x:integer) : integer
 -> let l := d.contents, i := x - d.offset in   // i is the index corresponding to x
      (assert(i >= 1 & i < length(l)),          // safety check: valid index (fix v0.27 length vs. size)
       assert(l[1] < MAXINT),     // check : d is non empty
       assert(i > l[1]),          // check : x is indeed an improved lower bound
       let i0 := l[i] in
          (assert(i0 != MAXINT),  // check: the new bound does not empty the domain v1.010
           let j := 1 in
              (while (l[j] != i0)               // for all indices j that point to less than i0
                  (if (l[j] = j) d.bucketSize :- 1, // if they were feasible, remove one value
                   store(l,j,i0,true),          // anoyhow, have them point to i0
                   j :+ 1)),
           //[DDEBUG] after setInf(~S) => bucket:~S // x,d,
           i0 + d.offset))]

// returns the new value of the upper bound (at most x)
[choco/updateDomainSup(d:LinkedListIntDomain,x:integer) : integer
 -> let l := d.contents, i := x - d.offset in   // i is the index corresponding to x
      (assert(i >= 1 & i < length(l)),          // safety check: valid index (fix v0.27 length vs. size)
       assert(l[1] < MAXINT),   // check : d is non empty  v1.010
       assert(l[i] < MAXINT),   // check : x is indeed an improved upper bound
       assert(l[1] <= i),       // check: the new bound does not empty the domain v1.010
       let j := i + 1 in
          (while (l[j] != MAXINT)                // for all indices j after i pointing to some value
             (if (l[j] = j) d.bucketSize :- 1,   // if they were feasible, remove one value
              store(l,j,MAXINT,true),            // anyhow, have them point to END
              j :+ 1)),
       let j := i in
          (while (l[j] != j)                    // for all infeasible indices j around i
             (store(l,j,MAXINT,true),             // have them point to END
              j :- 1),
           //[DDEBUG] after setSup(~S) => bucket:~S // x,d,
           j + d.offset))]


// ********************************************************************
// *   Part 3: abstract SetVar domains                                *
// ********************************************************************

// Domains for set variables have two bounds:
//   a lower bound called the kernel: set of values that are for sure in the set variable
//                                      (intersection of all possible sets)
//   an upper bound called the enveloppe: set of values that may be in the set variable
//                                      (union of all possible sets)
choco/AbstractSetDomain <: AbstractDomain(
       choco/minValue:integer = 0,
       choco/kernelSize:integer = 0,
       choco/enveloppeSize:integer = 0)
(store(kernelSize, enveloppeSize))
[choco/getKernel(d:AbstractSetDomain) : list<integer>
 -> error("getKernel not defined on ~S",d), list<integer>()]
[choco/getEnveloppe(d:AbstractSetDomain) : list<integer>
 -> error("getEnveloppe not defined on ~S",d), list<integer>()]
[choco/getKernelSize(d:AbstractSetDomain) : integer
 -> d.kernelSize]
[choco/getEnveloppeSize(d:AbstractSetDomain) : integer
 -> d.enveloppeSize]
[choco/getKernelInf(d:AbstractSetDomain) : integer
 -> error("getKernelInf not defined on ~S",d), 0]
[choco/getKernelSup(d:AbstractSetDomain) : integer
 -> error("getKernelSup not defined on ~S",d), 0]
[choco/getEnveloppeInf(d:AbstractSetDomain) : integer
 -> error("getEnveloppeInf not defined on ~S",d), 0]
[choco/getEnveloppeSup(d:AbstractSetDomain) : integer
 -> error("getEnveloppeSup not defined on ~S",d), 0]
[choco/isInEnveloppe(d:AbstractSetDomain, x:integer) : boolean
 -> error("isInEnveloppe not defined on ~S",d), true]
[choco/isInKernel(d:AbstractSetDomain, x:integer) : boolean
 -> error("isInKernel not defined on ~S",d), true]
[choco/updateKernel(d:AbstractSetDomain,x:integer) : boolean
 -> error("updateKernel not defined on ~S",d), true]
[choco/updateEnveloppe(d:AbstractSetDomain,x:integer) : boolean
 -> error("updateKernel not defined on ~S",d), true]
(interface(getKernel), interface(getEnveloppe), 
 interface(getKernelSize), interface(getEnveloppeSize), 
 interface(getKernelInf), interface(getKernelSup), 
 interface(getEnveloppeInf), interface(getEnveloppeSup), 
 interface(isInKernel), interface(isInEnveloppe),
 interface(updateKernel), interface(updateEnveloppe))
(interface(AbstractSetDomain, 
             getKernel, getEnveloppe, 
             getKernelSize, getEnveloppeSize, 
             getEnveloppeInf, getEnveloppeSup,
             isInKernel, isInEnveloppe,
             updateKernel, updateEnveloppe))

// ********************************************************************
// *   Part 4: implementing SetVar domains by bitvectors              *
// ********************************************************************

// An encoding with integers taken as bitvectors 
// bitvetor[idx] tells if idx + minValue is in the domain
//               this is valid for 0<=idx<=29
choco/BitSetDomain <: AbstractSetDomain(
       choco/kernelBitVector:integer,
       choco/enveloppeBitVector:integer)
(store(kernelBitVector, enveloppeBitVector))
[choco/getKernel(d:BitSetDomain) : list<integer>
 -> list<integer>{(i + d.minValue) | i in list<integer>{i in (0 .. 29) | d.kernelBitVector[i]}}]
[choco/getEnveloppe(d:BitSetDomain) : list<integer>
 -> list<integer>{(i + d.minValue) | i in list<integer>{i in (0 .. 29) | d.enveloppeBitVector[i]}}]
[choco/getKernelSize(d:BitSetDomain) : integer
 -> assert(count(list<integer>{i in (0 .. 29) | d.kernelBitVector[i]}) =  d.kernelSize),
    d.kernelSize]
[choco/getEnveloppeSize(d:BitSetDomain) : integer
 -> assert(count(list<integer>{i in (0 .. 29) | d.enveloppeBitVector[i]}) = d.enveloppeSize),
    d.enveloppeSize]
[choco/getKernelInf(d:BitSetDomain) : integer
 -> d.minValue + min(list<integer>{i in (0 .. 29) | d.kernelBitVector[i]})]
[choco/getKernelSup(d:BitSetDomain) : integer
 -> d.minValue + max(list<integer>{i in (0 .. 29) | d.kernelBitVector[i]})]
[choco/getEnveloppeInf(d:BitSetDomain) : integer
 -> d.minValue + min(list<integer>{i in (0 .. 29) | d.enveloppeBitVector[i]})]
[choco/getEnveloppeSup(d:BitSetDomain) : integer
 -> d.minValue + max(list<integer>{i in (0 .. 29) | d.enveloppeBitVector[i]})]
[choco/isInEnveloppe(d:BitSetDomain, x:integer) : boolean
 -> d.enveloppeBitVector[x - d.minValue]]
[choco/isInKernel(d:BitSetDomain, x:integer) : boolean
 -> d.kernelBitVector[x - d.minValue]]
 
[choco/makeBitSetDomain(i:integer,j:integer) : BitSetDomain
 -> let nbvals := j - i + 1,
        allOne := ^2(nbvals - 1) - 1 + ^2(nbvals - 1) in 
       BitSetDomain(minValue = i,
                    kernelSize = 0,
                    enveloppeSize = nbvals,                          
                    kernelBitVector = 0,
                    enveloppeBitVector = allOne)]
[choco/updateKernel(d:BitSetDomain,x:integer) : boolean
 -> let idx := x - d.minValue, deltaBitVector := ^2(idx) in 
      (assert(idx >= 0 & idx <= 29),
       if not(d.kernelBitVector[idx])
         (d.kernelSize :+ 1,
          d.kernelBitVector :+ deltaBitVector, 
          true)
       else false)]
[choco/updateEnveloppe(d:BitSetDomain,x:integer) : boolean
 -> let idx := x - d.minValue, deltaBitVector := ^2(idx) in 
      (assert(idx >= 0 & idx <= 29),
       if d.enveloppeBitVector[idx]
         (d.enveloppeSize :- 1,
          d.enveloppeBitVector :- deltaBitVector, 
          true)
       else false)]       

// ********************************************************************
// *   Part 5: implementing SetVar domains by bitvector lists         *
// ********************************************************************

// An encoding with a list of integers taken as bitvectors 
// bitvetor[word][idx] tells if (minValue + 30(word-1) + idx) is in the domain
//   this is valid for 0<=word<=length(bitvector), 0<=idx<=29
//        x = minv + 30(w-1) + idx
//        idx = (x - minv) mod 30
//        w   = (x - minv) / 30 + 1 
choco/BitListSetDomain <: AbstractSetDomain(
       choco/kernelBitVectorList:list<integer>,
       choco/enveloppeBitVectorList:list<integer>)
// updates to kernelBitVectorList and enveloppeBitVectorList are backtrackable.
[choco/makeBitListSetDomain(i:integer,j:integer) : BitListSetDomain
 -> let nbvals := j - i + 1, nbwords := (nbvals div+ 30), 
        all1 := makeAllOnesBitVector(30), nbAdditionalBits := (nbvals mod 30) in 
       BitListSetDomain(minValue = i,
                    kernelBitVectorList = make_list(nbwords,integer,0),
                    kernelSize = 0,
                    enveloppeSize = nbvals,
                    enveloppeBitVectorList = 
                       (if (nbAdditionalBits > 0)
                           make_list(nbwords - 1,integer,all1) add makeAllOnesBitVector(nbAdditionalBits)
                        else make_list(nbwords,integer,all1)))]

[choco/getKernel(d:BitListSetDomain) : list<integer>
 -> getBitVectorList(d.minValue, d.kernelBitVectorList)]
[choco/getEnveloppe(d:BitListSetDomain) : list<integer>
 -> getBitVectorList(d.minValue, d.enveloppeBitVectorList)]
[choco/getKernelSize(d:BitListSetDomain) : integer
 -> assert(getBitVectorListCount(d.kernelBitVectorList) = d.kernelSize),
    d.kernelSize]
[choco/getEnveloppeSize(d:BitListSetDomain) : integer
 -> assert(getBitVectorListCount(d.enveloppeBitVectorList) = d.enveloppeSize),
    d.enveloppeSize]
[choco/getKernelInf(d:BitListSetDomain) : integer
 -> d.minValue + getBitVectorInf(d.kernelBitVectorList)]
[choco/getKernelSup(d:BitListSetDomain) : integer
 -> d.minValue + getBitVectorSup(d.kernelBitVectorList)]
[choco/getEnveloppeInf(d:BitListSetDomain) : integer
 -> d.minValue + getBitVectorInf(d.enveloppeBitVectorList)]
[choco/getEnveloppeSup(d:BitListSetDomain) : integer
 -> d.minValue + getBitVectorSup(d.enveloppeBitVectorList)]

[choco/isInEnveloppe(d:BitListSetDomain, x:integer) : boolean
 -> isInBitVectorList(x - d.minValue, d.enveloppeBitVectorList)]
[choco/isInKernel(d:BitListSetDomain, x:integer) : boolean
 -> isInBitVectorList(x - d.minValue, d.kernelBitVectorList)]

[choco/updateKernel(d:BitListSetDomain,x:integer) : boolean
 -> if addToBitVectorList(x - d.minValue, d.kernelBitVectorList)
       (d.kernelSize :+ 1, true)
    else false]
[choco/updateEnveloppe(d:BitListSetDomain,x:integer) : boolean
 -> if remFromBitVectorList(x - d.minValue, d.enveloppeBitVectorList)
       (d.enveloppeSize :- 1, true)
    else false]
    

// TODO: définir des iterateurs sur getEnveloppe et getKernel pour les deux implementations de domaines
                        
