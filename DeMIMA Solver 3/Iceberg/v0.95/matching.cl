// ********************************************************************
// * ICE: global constraints for OCRE, version 1.001 03/09/2001       *
// *        requires Claire v2.5, CHOCO v1.08                         *
// * file: match.cl                                                   *
// *    assignment constraints (bipartite matching and flow)          *
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: utils                                                  *
// *    ------ graphs --------                                        *
// *   Part 2: bipartite graph management                             *
// *   Part 3: flow and residual graph management                     *
// *   Part 4: specific matching (vs. flow) graphs                    *
// *   Part 5: computing a reference flow                             *
// *   Part 6: computing the st. conn. comp. of the residual graph    *
// *    ------ propagation --------                                   *
// *   Part 7: achieving generalized arc consistency (Regin 94/96)    *
// *   Part 8: standard alldiff constraint with generalized AC        *
// *   Part 9: dual models for permutation constraints                *
// *   Part 10:global cardinality constraint (gcc, Regin 96)          *
// --------------------------------------------------------------------

claire/ICE_RELEASE :: 0.8
[claire/showIglooLicense()
 -> printf("Iceberg version ~A, Copyright (C) 2000-02 Bouygues e-lab\n",ICE_RELEASE)]
(showIglooLicense())

claire/MVIEW:integer := 4
claire/MTALK:integer := 5
claire/MDEBUG:integer := 6

SCCVIEW:integer := 5
SCCTALK:integer := 6

claire/ICETEST_VIEW:integer := -1
claire/ICEBENCH_VIEW:integer := -1

// ********************************************************************
// *   Part 1: utils                                                  *
// ********************************************************************

private/IntCollection[maxsize] <: Ephemeral(
           maxsize:integer,
           nbElts:integer = 0)
[private/getSize(ic:IntCollection) : integer -> ic.nbElts]

// ----- utils: stack operations --------
private/IntStack[maxsize] <: IntCollection(
           contents:list[integer])

[close(s:IntStack) : IntStack
 -> s.contents := make_list(s.maxsize,integer,0),
    s]

[set!(s:IntStack) -> list{s.contents[i] | i in (1 .. s.nbElts)}]
[iterate(s:IntStack,v:Variable,e:any)
  -> let l := s.contents in
       (for %i in (1 .. s.nbElts)
          let v := l[%i] in e) ]
[private/clear(s:IntStack) : void => s.nbElts := 0]
[private/pop(s:IntStack) : integer => s.nbElts :- 1, s.contents[s.nbElts + 1]]
[private/push(s:IntStack,x:integer) : void => s.nbElts :+ 1, s.contents[s.nbElts] := x]

// ----- utils: queue operations --------
private/IntQueue[maxsize] <: IntCollection(
           last:integer = 0,
           contents:list<integer>,
           onceinqueue:list<boolean> )

[close(q:IntQueue) : IntQueue
 -> q.contents := make_list(q.maxsize,integer,0),
    q.onceinqueue := make_list(q.maxsize,boolean,false),
    q]

[push(q:IntQueue,x:integer) : void
  -> assert(x <= q.maxsize),
     q.onceinqueue[x] := true,
     if (q.contents[x] = 0)
        (if (q.nbElts = 0) q.contents[x] := x
         else (q.contents[x] := q.contents[q.last], q.contents[q.last] := x),
         q.last := x, q.nbElts :+ 1) ]

[pop(q:IntQueue) : integer
  -> let x := q.contents[q.last] in
       (q.nbElts :- 1, q.contents[q.last] := q.contents[x], q.contents[x] := 0,
        x)]

[init(q:IntQueue) : void
 -> q.nbElts := 0,
    for i in (1 .. q.maxsize)
       (q.onceinqueue[i] := false, q.contents[i] := 0)]

// ********************************************************************
// *   Part 2: bipartite graph management                             *
// ********************************************************************

// An abstract class encoding assignment graphs
//  (matching each left vertex with one single right vertex)
AbstractBipartiteGraph <: choco/LargeIntConstraint(
  nbLeftVertices:integer,
  nbRightVertices:integer,
  minValue:integer = MAXINT, // encoding of a value set for the set of right vertices
  maxValue:integer = MININT,
  source:integer,
  nbVertices:integer,        // total number of nodes in the graph (left+right+source)
  refMatch:integer[],       // storing a reference matching: from leftVars onto rightVars
  matchingSize:integer = 0, // and its size = #{i | refMatch[i] != 0}
  left2rightArc:integer[],  // storing the alternating forest (in the search for augmenting paths)
  right2leftArc:integer[],  // storing the alternating forest (in the search for augmenting paths)
  queue:IntQueue,
// data structures for Computing the strongly connected component of the delta graph
 // temporary data structure: markers, iterators, ....
  time:integer = 0,         // a time counter
  finishDate:integer[],     // finishDate[i] : value of time when the expansion of i was completed in DFS
  seen:boolean[],           // seen[i]=true <=> node i has been expanded in DFS
  currentNode:integer = 0,      // the current node in the second DFS exploration
  currentComponent:integer = 0, // a counter used when building the solution
  // the solution
  component:integer[],          // storing the solution: component[i] is the index of strong con. comp. oof i
  componentOrder:BoolMatrix2D)  // componentOrder[i,j]=true <=> there exists an edge in the SCC graph from component i to component j
(store(matchingSize)) // in addition, updates to refMatch are also backtrackably stored

// debug methods
[showRefAssignment(c:AbstractBipartiteGraph) : void
 -> printf("F&F matching:~S ~S\n",c.isa,
           list{list(i,c.refMatch[i]) | i in (1 .. c.nbLeftVertices)})]
[showSCCDecomposition(c:AbstractBipartiteGraph) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices, nbComp := c.currentComponent in
      (printf("Component graph with ~S components",nbComp),
       for comp in (1 .. nbComp)
         (printf("NODE ~S: left:~S, right:~S\n",
              comp,list{i in (1 .. n1) | c.component[i] = comp},
              list{(j + c.minValue - 1) | j in list{j in (1 .. n2) | c.component[j + n1] = comp}}),
          printf("  predecessors: ~S\n",comp,list{compj in (1 .. nbComp) | read(c.componentOrder,compj,comp)}) ))]

// we consider a flow in the graph by adding a source linked to all right vertices
// and a sink linked to all left vertices
//

// accessing the edges of the bipartite graph from the left vertices
//    access from the left vertex set: reading domains of modeling variables
[mayMatch(c:AbstractBipartiteGraph, i:integer) : list[integer]
 => ;assert(1 <= i & i <= c.nbLeftVertices),  // do not uncomment the assert, or bad expansion in iterations
    list<integer>{(j - c.minValue + 1) | j in domain(c.vars[i] as IntVar)} ]

// reverse: accessing the edges of the bipartite graph from the left vertices
//      iterating over the variables (left vertex set) and reading their domains
//  note: this could be improved for Permutation: choco/domain(c.vars[j + c.nbLeftVertices])
//        but we do not want to have multiple versions of this function to choose from 
[mayInverseMatch(c:AbstractBipartiteGraph, j:integer) : list[integer]
 => ;assert(1 <= j & j <= c.nbRightVertices),  // do not uncomment the assert, or bad expansion in iterations
    list<integer>{i in (1 .. c.nbLeftVertices) |
              c.vars[i] choco/canBeInstantiatedTo (j + c.minValue - 1)}]

// accessing the right vertex matched to i
[match(c:AbstractBipartiteGraph, i:integer) : integer
 => ; assert(1 <= i & i <= c.nbLeftVertices),
    c.refMatch[i]]

CHK:any := nil    
// integrity check: checking that the flow is indeed maximal (yielding an assignment)
[choco/checkFlow(c:AbstractBipartiteGraph) : void
 => let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
      (//[MDEBUG] checkFlow(~S: refM:~S) // c,list{match(c,i) | i in (1 .. n1)},
       CHK := c,
       assert(forall(i1 in (1 .. n1) | match(c,i1) != 0)),
       assert(c.nbLeftVertices = c.matchingSize) )]

// ********************************************************************
// *   Part 3: flow and residual graph management                     *
// ********************************************************************

// a flow is built in the bipartite graph from the reference matching
//          match(i) = j <=> flow(j to i) = 1
//    from a source linked to all right vertices and a sink linked to all left vertices
//    Yes, this IS counter-intuitive, the flow goes from left to right
//    but this makes the job much easier for gcc in order to compute compatible flows
//    (with lower bounds on the edges from the source to the right vertices)

// whether the flow from i (a left vertex) to the sink may be increased
[mayGrowFlowToSink(c:AbstractBipartiteGraph,i:integer) : boolean
 => (match(c,i) = 0)]

// whether the flow from j (a right vertex) to i (a left vertex) may be increased
// (the additional flow is able to arrive to j, we don't care yet whether it will be able to leave i)
[mayGrowFlowBetween(c:AbstractBipartiteGraph,j:integer,i:integer) : boolean
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    match(c,i) != j]

// whether the flow from j (a right vertex) to i (a left vertex) may be increased
[mayDiminishFlowBetween(c:AbstractBipartiteGraph,j:integer,i:integer) : boolean
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    match(c,i) = j]
    
// ********************************************************************
// *   Part 4: specific matching (vs. flow) graphs                    *
// ********************************************************************

// several classes of AbstractBipartiteGraph will be defined:
//   they must all implement:
//     inlined methods:
//        increaseMatchingSize, decreaseMatchingSize,
//        mayDiminishFlowFromSource, mayGrowFlowFromSource, mustGrowFlowFromSource
//        deleteMatch, setMatch, 
//     std methods:
//        setMatch

// a subclass that implements matchings (and not flows)
AbstractBipartiteMatching <: AbstractBipartiteGraph(
     refInverseMatch:integer[]) // the reverse assignment is stored
// updates to refInverseMatch are also backtrackably stored

// accessing the left vertex matched to j
[inverseMatch(c:AbstractBipartiteMatching, j:integer) : integer
 => ; assert(1 <= j & j <= c.nbRightVertices),
    c.refInverseMatch[j] ]

// updates the matching size when one more left vertex is matched with j
[increaseMatchingSize(c:AbstractBipartiteMatching,j:integer) : void
 => c.matchingSize :+ 1]

// updates the matching size when one more left vertex is de-matched with j
[decreaseMatchingSize(c:AbstractBipartiteMatching,j:integer) : void
 => c.matchingSize :- 1]
    
// removing the arc i-j from the reference matching & update matchingSize
// note (v0.6): this function may be called twice without damage
[deleteMatch(c:AbstractBipartiteMatching,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    if (j = c.refMatch[i])
       (store(c.refMatch,i,0,true),
        store(c.refInverseMatch,j,0,true),
        decreaseMatchingSize(c,j) )]

// adding the arc i-j in the reference matching without any updates
[putRefMatch(c:AbstractBipartiteMatching,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    store(c.refMatch,i,j,true),
    store(c.refInverseMatch,j,i,true)]

// adding the arc i-j in the reference matching & update matchingSize
[setMatch(c:AbstractBipartiteMatching,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    let j0 := c.refMatch[i], i0 := c.refInverseMatch[j] in
       (if (j0 != j)
           (assert(i0 != i),
            if (j0 > 0) 
               (store(c.refInverseMatch,j0,0,true),
	        decreaseMatchingSize(c,j0)),
            if (i0 > 0) 
	       (store(c.refMatch,i0,0,true),
	        decreaseMatchingSize(c,j)),
            store(c.refMatch,i,j,true),
            store(c.refInverseMatch,j,i,true),
            increaseMatchingSize(c,j) ))]
            
// whether the flow from the source to j (a right vertex) may be decreased
[mayDiminishFlowFromSource(c:AbstractBipartiteMatching,j:integer) : boolean
 => (inverseMatch(c,j) != 0)]

// whether the flow from the source to j (a right vertex) may be increased
[mayGrowFlowFromSource(c:AbstractBipartiteMatching,j:integer) : boolean
 => ; assert(1 <= j & j <= c.nbRightVertices),
    inverseMatch(c,j) = 0]

// whether the flow from the source to j (a right vertex) must be increased in order
// to get a maximal (sink/left vertex set saturating) flow
[mustGrowFlowFromSource(c:AbstractBipartiteMatching,j:integer) : boolean
 => ; assert(1 <= j & j <= c.nbRightVertices),
    false]


// safety check: the matching is indeed a perfect matching
;[checkFlow(c:AbstractBipartiteMatching) : void
; -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
;      (assert(forall(i1 in (1 .. n1) | match(c,i1) != 0)),
;       assert(forall(i1 in (1 .. n1) |
;                forall(i2 in ((1 .. n1) but i1) | (match(c,i1) != match(c,i2))))),
;       assert((n2 > n1) |
;              forall(j1 in (1 .. n2) |
;                forall(j2 in ((1 .. n2) but j1) | (inverseMatch(c,j1) != inverseMatch(c,j2))))) )]


// yet another even more specific kind of graph: balanced bipartite matching
//   (such that nbLeftVertices = nbRightVertices).
BalancedBipartiteMatching <: AbstractBipartiteMatching()
;[checkFlow(c:BalancedBipartiteMatching) : void
; -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
;      (assert(forall(i1 in (1 .. n1) | match(c,i1) != 0)),
;       assert(forall(i2 in (1 .. n2) | inverseMatch(c,i2) != 0)),
;       assert(forall(i1 in (1 .. n1) |
;                forall(i2 in ((1 .. n1) but i1) | (match(c,i1) != match(c,i2))))),
;       assert((n2 > n1) |
;              forall(j1 in (1 .. n2) |
;                forall(j2 in ((1 .. n2) but j1) | (inverseMatch(c,j1) != inverseMatch(c,j2))))) )]

// a general assignment constraint with constraints on the flow bounds
AbstractBipartiteFlow <: AbstractBipartiteGraph(
// additional slots: flow on the edges from v2 to the sink
   minFlow:integer[],
   maxFlow:integer[],
   flow:integer[],
   compatibleFlow:boolean)  // a flag used when searching for augmenting paths
// updates to flow are backtrackably stored

// adding the arc i-j in the reference matching & update matchingSize
// noet: in case this method makes the flow incompatible (going below minflow), we do not
// set the edge in the matching, so that the flow may be repaired into a compatible one while
// being augmented
[setMatch(c:AbstractBipartiteFlow,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    let j0 := c.refMatch[i] in
       (if (j0 != j)
           (if (j0 > 0) 
               (store(c.refMatch,i,0,true),
                decreaseMatchingSize(c,j0)),
            if ((c.flow[j] < c.maxFlow[j]) &
                ((j0 = 0) | (c.flow[j0] >= c.minFlow[j0])))
               (store(c.refMatch,i,j,true),
                increaseMatchingSize(c,j)) ))]

// removing the arc i-j from the reference matching & update matchingSize
// note (v0.6): this function may be called twice without damage
[deleteMatch(c:AbstractBipartiteFlow,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    if (j = c.refMatch[i])
       (store(c.refMatch,i,0,true),
        decreaseMatchingSize(c,j) )]

// adding the arc i-j in the reference matching without any updates
[putRefMatch(c:AbstractBipartiteFlow,i:integer,j:integer) : void
 => ; assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    store(c.refMatch,i,j,true)]
        
[mayDiminishFlowFromSource(c:AbstractBipartiteFlow,j:integer) : boolean
 => (c.flow[j] > c.minFlow[j])]

[mayGrowFlowFromSource(c:AbstractBipartiteFlow,j:integer) : boolean
 => (c.flow[j] < c.maxFlow[j])]

[mustGrowFlowFromSource(c:AbstractBipartiteFlow,j:integer) : boolean
 => (c.flow[j] < c.minFlow[j])]

// updates the matching size when one more left vertex is matched with j
[increaseMatchingSize(c:AbstractBipartiteFlow,j:integer) : void
 => c.matchingSize :+ 1,
    store(c.flow,j,c.flow[j] + 1,true)]

[decreaseMatchingSize(c:AbstractBipartiteFlow,j:integer) : void
 => c.matchingSize :- 1,
    store(c.flow,j,c.flow[j] - 1,true)]

// ********************************************************************
// *   Part 5: computing a reference flow                             *
// ********************************************************************

// The exact same code is copied twice (for flows and matchings), but will be specialized differently thanks
// to inline expansions.
DBC:any := nil
// First pass: use Ford & Fulkerson algorithm to compute a reference flow (assignment)
//    finds an augmenting path using a fifo queue (returns 0 if none found, otherwise the end of the path)
[ice/findAlternatingPath(c:AbstractBipartiteMatching) : integer
  -> //[MTALK] search for an augmenting path to grow matching above ~S nodes // c.matchingSize,
     let eopath := 0, q := c.queue, n := c.nbLeftVertices in
       (init(q),
        for j in list{j in (1 .. c.nbRightVertices) | mustGrowFlowFromSource(c,j)}
           push(q,j + n),   // enqueue vertives of V2 whose lower bounds haven't been reached
        if (getSize(q) = 0)
           for j in list{j in (1 .. c.nbRightVertices) | mayGrowFlowFromSource(c,j)}
               push(q,j + n),  // otherwise enqueue vertives of V2 whose upper bounds haven't been reached
        while (getSize(q) > 0)
          let x := pop(q) in
            (//[MDEBUG] fifo: pop ~S // x,
             if (x > n)   // if the dequeued vertex is in V1
                (x :- n,
                 if (;//[0] COUCOU ~S ~S// x,c,
                     ;DBC := c,
                     ;error("stop"),
                     ;//[0] mayInverseMatch(~S)=~S // x,mayInverseMatch(c,x),
                     for y:integer in mayInverseMatch(c,x)
                       (;//[0] consider y=~S // y,
                        if (mayGrowFlowBetween(c,x,y) & not(q.onceinqueue[y]))
                           (trace(MTALK,"~S.~S[vs.~S] ",y,x,match(c,y)),
                            c.left2rightArc[y] := x,
                            if mayGrowFlowToSink(c,y)
                              (eopath := y, break(true))
                            else push(q,y)),
                        false))
                    break())
             else (assert(not(mayGrowFlowToSink(c,x))),
                   let y := match(c,x) in
                      (assert(y > 0),
                       assert(mayDiminishFlowBetween(c,y,x)),
                       if not(q.onceinqueue[y + n])
                         (trace(MTALK,"~S#~S ",x,y,),
                          c.right2leftArc[y] := x,
                          push(q,y + n))))),
        //[MTALK] found an alternating path ending in ~S (0 if none)// eopath,
        eopath)]

// augment the matching along one alternating path
// note: throughout the following code, we assume
//    (1 <= x <= c.nbLeftVertices), (1 <= y <= c.nbRightVertices)
[choco/augment(c:AbstractBipartiteMatching,x:integer) : void
  -> let y := c.left2rightArc[x] in
        (while not(mayGrowFlowFromSource(c,y))
               (//[MDEBUG] add ~S.~S // x,y,
                putRefMatch(c,x,y),
                x := c.right2leftArc[y],
                //[MDEBUG] rem ~S.~S // x,y,
                assert(match(c,x) = y),
                y := c.left2rightArc[x],
                assert(y > 0)
                ),
         //[MDEBUG] add ~S.~S // x,y,
         putRefMatch(c,x,y),
         increaseMatchingSize(c,y))]


// keeps augmenting the flow until a maximal flow is reached
[ice/augmentFlow(c:AbstractBipartiteMatching) : void
 -> let eopath := findAlternatingPath(c), n1 := c.nbLeftVertices in
      (if (c.matchingSize < n1)
         //[MTALK] current flow of size:~S: ~S // c.matchingSize, list{list(i,match(c,i)) | i in list{i in (1 .. n1) | match(c,i) > 0}},
       while (eopath > 0)
             (augment(c,eopath), eopath := findAlternatingPath(c)),
       if (c.matchingSize < n1)
          (assert(exists(i in (1 .. n1) | match(c,i) = 0)),
           //[MTALK] there exists no perfect matching // ,
           choco/raiseContradiction(c))
       else (//[MTALK] found a perfect matching (size:~S) // c.matchingSize,
             for i in (1 .. c.nbLeftVertices) 
                 //[MTALK] match ~S with ~S // i,match(c,i),
             checkFlow(c)) )]

(abstract(ice/augmentFlow))

// --------------------------------------------------------------------------
//    EXACT SAME CODE FOR FLOWS (GCC)
// --------------------------------------------------------------------------
[ice/findAlternatingPath(c:AbstractBipartiteFlow) : integer
  -> //[MTALK] search for an augmenting path to grow matching above ~S nodes // c.matchingSize,
     let eopath := 0, q := c.queue, n := c.nbLeftVertices in
       (init(q),
        for j in list{j in (1 .. c.nbRightVertices) | mustGrowFlowFromSource(c,j)}
           push(q,j + n),   // enqueue vertives of V2 whose lower bounds haven't been reached
        if (getSize(q) = 0)
           (c.compatibleFlow := true,
            for j in list{j in (1 .. c.nbRightVertices) | mayGrowFlowFromSource(c,j)}
                push(q,j + n))  // otherwise enqueue vertives of V2 whose upper bounds haven't been reached
        else c.compatibleFlow := false,
        while (getSize(q) > 0)
          let x := pop(q) in
            (//[MDEBUG] fifo: pop ~S // x,
             if (x > n)   // if the dequeued vertex is in V1
                (x :- n,
                 if (for y:integer in mayInverseMatch(c,x)
                       (if (mayGrowFlowBetween(c,x,y) & not(q.onceinqueue[y]))
                           (trace(MTALK,"~S.~S[vs.~S] ",y,x,match(c,y)),
                            c.left2rightArc[y] := x,
                            if mayGrowFlowToSink(c,y)
                              (eopath := y, break(true))
                            else push(q,y)),
                        false))
                    break())
             else (assert(not(mayGrowFlowToSink(c,x))),
                   let y := match(c,x) in
                      (assert(y > 0),
                       assert(mayDiminishFlowBetween(c,y,x)),
                       if not(q.onceinqueue[y + n])
                         (trace(MTALK,"~S#~S ",x,y,),
                          c.right2leftArc[y] := x,
                          push(q,y + n))))),
        //[MTALK] found an alternating path ending in ~S (0 if none)// eopath,
        eopath)]

[choco/augment(c:AbstractBipartiteFlow,x:integer) : void
  -> let y := c.left2rightArc[x] in
        (if c.compatibleFlow // standard case
          (while not(mayGrowFlowFromSource(c,y))
               (//[MDEBUG] add ~S.~S // x,y,
                putRefMatch(c,x,y),
                x := c.right2leftArc[y],
                //[MDEBUG] rem ~S.~S // x,y,
                assert(match(c,x) = y),
                y := c.left2rightArc[x],
                assert(y > 0)))
         else // in case of incompatible flows, different stopping criterion
          (while not(mustGrowFlowFromSource(c,y))
               (//[MDEBUG] add ~S.~S // x,y,
                putRefMatch(c,x,y),
                x := c.right2leftArc[y],
                //[MDEBUG] rem ~S.~S // x,y,
                assert(match(c,x) = y),
                y := c.left2rightArc[x],
                assert(y > 0))),
         //[MDEBUG] add ~S.~S // x,y,
         putRefMatch(c,x,y),
         increaseMatchingSize(c,y))]
         
         
// keeps augmenting the flow until a maximal flow is reached
[private/augmentFlow(c:AbstractBipartiteFlow) : void
 -> let eopath := findAlternatingPath(c), n1 := c.nbLeftVertices in
      (if (c.matchingSize < n1)
         //[MTALK] current flow of size:~S: ~S // c.matchingSize, list{list(i,match(c,i)) | i in list{i in (1 .. n1) | match(c,i) > 0}},
       while (eopath > 0)
             (augment(c,eopath), eopath := findAlternatingPath(c)),
       if (c.matchingSize < n1)
          (assert(exists(i in (1 .. n1) | match(c,i) = 0)),
           //[MTALK] there exists no perfect matching // ,
           choco/raiseContradiction(c))
       else (//[MTALK] found a perfect matching (size:~S) // c.matchingSize,
             for i in (1 .. c.nbLeftVertices) 
                 //[MTALK] match ~S with ~S // i,match(c,i),
             checkFlow(c)) )]

[turnIntoCompatibleFlow(c:AbstractBipartiteFlow) : void
 -> let eopath := findAlternatingCycle(c) in
      (while (eopath > 0)
             (circulate(c,eopath), 
              eopath := findAlternatingCycle(c)),
       if exists(j in (1 .. c.nbRightVertices) | mustGrowFlowFromSource(c,j)) 
          (//[MTALK] there exists no maximal compatible flow // ,
           choco/raiseContradiction(c))
       else (//[MTALK] found a compatible flow,
             checkFlow(c)) )]

// circulates the flow back along the alternate cycle 
//    this does not increase the matching size, but turns an incompatible flow 
//     -ie violating minflow requirements- into a compatible one
[private/circulate(c:AbstractBipartiteFlow,y:integer) : void
  -> decreaseMatchingSize(c,y),
     while not(mayGrowFlowFromSource(c,y))
       let x := c.right2leftArc[y] in
        (//[MDEBUG] rem ~S.~S // x,y,
         y := c.left2rightArc[x],
         //[MDEBUG] add ~S.~S // x,y,
         putRefMatch(c,x,y),
         assert(match(c,x) = y),
         assert(y > 0) ),
     increaseMatchingSize(c,y)]

[private/findAlternatingCycle(c:AbstractBipartiteFlow) : integer
  -> //[MTALK] search for an alternating cycle to make flow compatible,
     let eopath := 0, q := c.queue, n := c.nbLeftVertices in
       (init(q),
        for j in list{j in (1 .. c.nbRightVertices) | mustGrowFlowFromSource(c,j)}
           push(q,j + n),   // enqueue vertives of V2 whose lower bounds haven't been reached
        while (getSize(q) > 0)
          let x := pop(q) in
            (//[MDEBUG] fifo: pop ~S // x,
             if (x > n)   // if the dequeued vertex is in V1
                (x :- n,
                 for y:integer in mayInverseMatch(c,x)
                   (if (mayGrowFlowBetween(c,x,y) & not(q.onceinqueue[y]))
                       (trace(MTALK,"~S.~S[vs.~S] ",y,x,match(c,y)),
                        c.left2rightArc[y] := x,
                        push(q,y))))
             else (assert(not(mayGrowFlowToSink(c,x))),
                   let y := match(c,x) in
                      (assert(y > 0),
                       assert(mayDiminishFlowBetween(c,y,x)),
                       if not(q.onceinqueue[y + n])
                         (trace(MTALK,"~S#~S ",x,y,),
                          c.right2leftArc[y] := x,
                          if mayDimishFlowFromSource(c,y)
                            (eopath := y, break())
                          else push(q,y + n))))),
        //[MTALK] found an alternating cycle ending in ~S (0 if none)// eopath,
        eopath)]
     
// ********************************************************************
// *   Part 6: computing the st. conn. comp. of the residual graph    *
// ********************************************************************

// computing the strongly connected components of the residual graph,
// then remove arcs connecting two different strongly connected components
//
// Computing the strongly connected components is done by an algorithm
// of Aho, Hopcroft, Ullman using depth first search (Cormen, Leiserson, p. 478, p. 489)

// initialize the graph data structure storing the SCC decomposition
[initSCCGraph(c:AbstractBipartiteGraph) : void
 -> // erase the component partial order graph
    let compOrder := c.componentOrder in 
       for cci in (1 .. c.currentComponent)
          for ccj in ((1 .. c.currentComponent) but cci)
             store(compOrder,cci,ccj,false),             
    // erase the component graph
    for i in (1 .. c.nbVertices) c.component[i] := 0,
    c.currentComponent := 0]
    
// adds a new vertex to the component graph (= a component = a set of s. connected vertices in the original graph) 
[addComponentVertex(c:AbstractBipartiteGraph) : void
 => c.currentComponent :+ 1]

// add an edge in the component graph between compi nd compj: 
//   componentOrder stores the transitive closure of that graph
[addComponentEdge(c:AbstractBipartiteGraph, compi:integer, compj:integer) : void
 -> let compOrder := c.componentOrder in 
      (if not(read(compOrder,compi,compj))
         (store(compOrder,compi,compj,true),
          for compj2 in (1 .. (compj - 1)) 
             (if read(compOrder,compj,compj2)
                 store(compOrder,compi,compj2,true)) 
          ))] 

// seen[i] = false <=> color[i] = white (in book)
//         = true               % {gray, black}
[firstPassDFS(c:AbstractBipartiteMatching) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
       (//[SCCVIEW] start first DFS to mark node finishTimes,
        for i in (1 .. c.nbVertices)
           (c.finishDate[i] := 0, c.seen[i] := false),
        c.time := 0,
        for i in (1 .. c.nbVertices)
            firstDFSearch(c,i) )]

// the first search explores (DFS) the reduced graph
[firstDFSearch(c:AbstractBipartiteMatching,i:integer) : void
 -> if not(c.seen[i])
      let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
         (c.time :+ 1, c.seen[i] := true,
          //[SCCTALK] open node ~S, time:~S // i,c.time,
          if (i <= n1)              // (i % c.leftVertices)
             (assert(match(c,i) != 0),
              firstDFSearch(c, match(c,i) + n1))
          else if (i < c.source)   // (i % c.rightVertices)
             (for j in mayInverseMatch(c,i - n1)
                 (if (match(c,j) != i - n1) firstDFSearch(c,j)),
              if mayDiminishFlowFromSource(c,i - n1)
                 firstDFSearch(c,c.source))
          else
             for j in (1 .. n2)    // (i = c.source)
                (if mayGrowFlowFromSource(c,j) firstDFSearch(c,j + n1)),
          c.time :+ 1,
          //[SCCTALK] close node ~S, time:~S // i,c.time,
          c.finishDate[i] := c.time)]

[secondPassDFS(c:AbstractBipartiteMatching) : void
 -> //[SCCVIEW] init SCC graph,
    initSCCGraph(c),
    while true
       let maxf := 0, rootOfComp := 0 in
         (for i in (1 .. c.nbVertices)
            (if (c.component[i] = 0 & c.finishDate[i] > maxf)
                (maxf := c.finishDate[i], rootOfComp := i)),
          if (maxf > 0)
             (addComponentVertex(c),
              //[SCCVIEW] start second DFS from ~S // rootOfComp,    
              secondDFSearch(c,rootOfComp))
          else break()) ]

// the second search explores (DFS) the inverse of the reduced graph
[private/secondDFSearch(c:AbstractBipartiteMatching,i:integer) : void
 -> let compi := c.component[i], curComp := c.currentComponent in
     (if (compi = 0)
        let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
         (c.component[i] := curComp,  c.currentNode := i,
          //[MDEBUG] open node ~S, for currentComponent:~S // i,curComp,
          if (i <= n1)              // (i % c.leftVertices)
             for j in mayMatch(c,i)
                (if (match(c,i) != j) secondDFSearch(c,j + n1))
          else if (i < c.source)   // (i % c.rightVertices)
            (for j in mayInverseMatch(c,i - n1)
                (if (match(c,j) = i - n1)
                    secondDFSearch(c,j)),
             if mayGrowFlowFromSource(c,i - n1) secondDFSearch(c,c.source))
          else                      // (i = c.source)
             for j in (1 .. n2)
                (if mayDiminishFlowFromSource(c,j)
                    secondDFSearch(c,j + n1)),
          //[MDEBUG] close node ~S, for currentComponent:~S // i,curComp
          )
      else if (compi < curComp)
               // ajouter à la composante du sommet "père" une arête vers la composante du sommet i
              addComponentEdge(c,curComp,compi)
           else if (compi > curComp)
              error("unexpected strong connection component of higher index ~S[cur:~S]",compi,curComp)
      )]

// --------------------------------------------------------------------------
//    EXACT SAME CODE FOR FLOWS (GCC)
// --------------------------------------------------------------------------

[firstPassDFS(c:AbstractBipartiteFlow) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
       (//[SCCVIEW] start first DFS to mark node finishTimes,
        for i in (1 .. c.nbVertices)
           (c.finishDate[i] := 0, c.seen[i] := false),
        c.time := 0,
        for i in (1 .. c.nbVertices)
            firstDFSearch(c,i) )]
[firstDFSearch(c:AbstractBipartiteFlow,i:integer) : void
 -> if not(c.seen[i])
      let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
         (c.time :+ 1, c.seen[i] := true,
          //[SCCTALK] open node ~S, time:~S // i,c.time,
          if (i <= n1)              // (i % c.leftVertices)
             (assert(match(c,i) != 0),
              firstDFSearch(c, match(c,i) + n1))
          else if (i < c.source)   // (i % c.rightVertices)
             (for j in mayInverseMatch(c,i - n1)
                 (if (match(c,j) != i - n1) firstDFSearch(c,j)),
              if mayDiminishFlowFromSource(c,i - n1)
                 firstDFSearch(c,c.source))
          else
             for j in (1 .. n2)    // (i = c.source)
                (if mayGrowFlowFromSource(c,j) firstDFSearch(c,j + n1)),
          c.time :+ 1,
          //[SCCTALK] close node ~S, time:~S // i,c.time,
          c.finishDate[i] := c.time)]

[secondPassDFS(c:AbstractBipartiteFlow) : void
 -> //[SCCVIEW] init SCC graph,
    initSCCGraph(c),
    while true
       let maxf := 0, rootOfComp := 0 in
         (for i in (1 .. c.nbVertices)
            (if (c.component[i] = 0 & c.finishDate[i] > maxf)
                (maxf := c.finishDate[i], rootOfComp := i)),
          if (maxf > 0)
             (addComponentVertex(c),
              //[SCCVIEW] start second DFS from ~S // rootOfComp,    
              secondDFSearch(c,rootOfComp))
          else break()) ]
[private/secondDFSearch(c:AbstractBipartiteFlow,i:integer) : void
 -> let compi := c.component[i], curComp := c.currentComponent in
     (if (compi = 0)
        let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
         (c.component[i] := curComp,  c.currentNode := i,
          //[MDEBUG] open node ~S, for currentComponent:~S // i,curComp,
          if (i <= n1)              // (i % c.leftVertices)
             for j in mayMatch(c,i)
                (if (match(c,i) != j) secondDFSearch(c,j + n1))
          else if (i < c.source)   // (i % c.rightVertices)
            (for j in mayInverseMatch(c,i - n1)
                (if (match(c,j) = i - n1)
                    secondDFSearch(c,j)),
             if mayGrowFlowFromSource(c,i - n1) secondDFSearch(c,c.source))
          else                      // (i = c.source)
             for j in (1 .. n2)
                (if mayDiminishFlowFromSource(c,j)
                    secondDFSearch(c,j + n1)),
          //[MDEBUG] close node ~S, for currentComponent:~S // i,curComp
          )
      else if (compi < curComp)
               // ajouter à la composante du sommet "père" une arête vers la composante du sommet i
              addComponentEdge(c,curComp,compi)
           else if (compi > curComp)
              error("unexpected strong connection component of higher index ~S[cur:~S]",compi,curComp)
      )]
          
// ********************************************************************
// *   Part 7: achieving generalized arc consistency (Regin 94/96)    *
// ********************************************************************

// two methods used for detecting that an edge should be removed
// from the bipartite assignment graph
//    deleteMatch          -> removes it from the graph data strutures
//    deleteEdgeAndPublish -> same + publishes the information outside the constraint

// the event generated by the flow algorithm:
// discovering that an edge is no longer valid, and posting this event
// to the constraint solver: since we are already achieving GAC consistency
// in one single loop, there is no need to post a constAwake
choco/deleteEdgeAndPublish :: property()

// remove arcs connecting two different strongly connected components
[private/removeUselessEdges(c:AbstractBipartiteMatching) : void
 -> assert(c.matchingSize = count(list{i in (1 .. c.nbLeftVertices) | match(c,i) != 0})),
    if (c.matchingSize < c.nbLeftVertices)
       (//[MVIEW] complete reference matching [~S/~S] into a perfect matching // c.matchingSize,c.nbLeftVertices,
        augmentFlow(c))
    else //[MVIEW] reference matching already a perfect matching //,
    let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
      (//[MVIEW] compute strongly connected components decomposition //,
       firstPassDFS(c), secondPassDFS(c),

;	   checkComponentOrder(c),

       //[MVIEW] remove edges connecting different SCC //,
       for i in (1 .. n1) 
          //[MTALK] left vertex ~S: scc:~S // i,c.component[i],
       for j in (1 .. n2)
          //[MTALK] right vertex ~S: scc:~S // j,c.component[j + n1],
       let nkept := 0, ndiscard := 0 in
          (for i in (1 .. c.nbLeftVertices)
              for j in (mayMatch(c,i) but match(c,i))
                 (if (c.component[i] != c.component[j + n1])
                     (//[MTALK] discard edge ~S ~S // i,j,
                      ndiscard :+ 1,
                      deleteEdgeAndPublish(c,i,j))
                  else nkept :+ 1),
           //[MVIEW] alldiff SCC decomposition: ~S edges kept, ~S discarded // nkept,ndiscard
           ) )]

// remove arcs connecting two different strongly connected components
[private/removeUselessEdges(c:AbstractBipartiteFlow) : void
 -> assert(c.matchingSize = count(list{i in (1 .. c.nbLeftVertices) | match(c,i) != 0})),
    if (c.matchingSize < c.nbLeftVertices)
       (//[MVIEW] complete reference matching [~S/~S] into a perfect matching // c.matchingSize,c.nbLeftVertices,
        augmentFlow(c))
    else //[MVIEW] reference matching already a perfect matching ~S// list{match(c,i) | i in (1 .. c.nbLeftVertices)},
    ;turnIntoCompatibleFlow(c),
    checkFlow(c),
    let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
      (//[MVIEW] compute strongly connected components decomposition //,
       firstPassDFS(c), secondPassDFS(c),

;   	   checkComponentOrder(c),

       //[MVIEW] remove edges connecting different SCC //,
       for i in (1 .. n1) 
          //[MTALK] left vertex ~S: scc:~S // i,c.component[i],
       for j in (1 .. n2)
          //[MTALK] right vertex ~S: scc:~S // j,c.component[j + n1],
       let nkept := 0, ndiscard := 0 in
          (for i in (1 .. c.nbLeftVertices)
              for j in (mayMatch(c,i) but match(c,i))
                 (if (c.component[i] != c.component[j + n1])
                     (//[MTALK] discard edge ~S ~S // i,j,
                      ndiscard :+ 1,
                      deleteEdgeAndPublish(c,i,j))
                  else nkept :+ 1),
           //[MVIEW] alldiff SCC decomposition: ~S edges kept, ~S discarded // nkept,ndiscard
           ) )]
                      
// Achieves generalized arc consistency in one call
[choco/propagate(c:AbstractBipartiteGraph) : void 
-> //[MVIEW] propagate ~S // c,
   removeUselessEdges(c)]
[choco/getPriority(c:AbstractBipartiteGraph) : integer -> 2]

// generic constraint initialization
//    assumes that the slots nbLeftVertices/nbRightVertices are filled properly
// initialize the structure for strong connection decomposition (with an order matrix filled with false, but on the diagonal)
[choco/closeAssignmentConstraint(c:AbstractBipartiteGraph) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices, nb := n1 + n2 in
      (c.refMatch := make_array(n1,integer,0),
       c.matchingSize := 0,  // <naren>
       c.queue := IntQueue(maxsize = nb),
       c.left2rightArc := make_array(n1,integer,0),
       c.right2leftArc := make_array(n2,integer,0),
       case c (AbstractBipartiteMatching
                 c.refInverseMatch := make_array(n2,integer,0)),                        
       c.source := nb + 1,
       c.nbVertices := nb + 1,
       c.finishDate := make_array(nb + 1,integer,0),
       c.seen := make_array(nb + 1,boolean,false),
       c.component := make_array(nb + 1,integer,0),
       let ord := make2DBoolMatrix(1,nb + 1,1,nb + 1,false,false) in 
         (for i in (1 .. nb + 1)
              store(ord,i,i,true) ,
          c.componentOrder := ord)
      )]

// ********************************************************************
// *   Part 8: standard alldiff constraint with generalized AC        *
// ********************************************************************

// integer valued variables are used only for the left vertex set
// no explicit variables are used for the right vertex set
//   the right vertex set is the interval (minValue .. maxValue)
CompleteAllDiff <: AbstractBipartiteMatching()
[self_print(c:CompleteAllDiff)
 -> printf("CompleteAllDiff(~S)", list{c.vars[i] | i in (1 .. c.nbLeftVertices)})]

// API entry point: creating the constraint (before posting it)
[choco/completeAllDiff(l1:list[IntVar]) : CompleteAllDiff
 -> let n := length(l1), c := CompleteAllDiff() in
      (for v in l1
         (c.minValue :min getInf(v), c.maxValue :max getSup(v)),
       c.vars := list<IntVar>{c | c in l1},
       choco/closeLargeIntConstraint(c),
       c.nbLeftVertices := n, c.nbRightVertices := c.maxValue - c.minValue + 1,
       closeAssignmentConstraint(c),
       c)]

// The next two functions implement the main two events:
//   1. when an edge is definitely chosen in the bipartite assignment graph.
[setEdgeAndPublish(c:CompleteAllDiff, i:integer,j:integer) : void
 -> //[MDEBUG] set edge ~S,~S in the ref. matching // i,j,
    setMatch(c,i,j),
    //[MDEBUG] FC propagation of binary difference constraints incident to edge (~S,~S) // i,j,
    for i2 in ((1 .. c.nbLeftVertices) but i)
        choco/removeVal(c.vars[i2],j + c.minValue - 1,c.indices[i2]) ]

//   2. when an edge is definitely removed from the bipartite assignment graph.
[deleteEdgeAndPublish(c:CompleteAllDiff,i:integer,j:integer) : void
 -> assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    deleteMatch(c,i,j),
    choco/removeVal(c.vars[i], j + c.minValue - 1, c.indices[i])]

// propagation functions: reacting to events
[choco/awakeOnRem(c:CompleteAllDiff,idx:integer,val:integer) : void
 -> //[MVIEW] awake ~S on rem ~S ~S // c,idx,val,
    deleteMatch(c,idx,val - c.minValue + 1),
    choco/constAwake(c,false) ]

// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
[choco/awakeOnVar(c:CompleteAllDiff,idx:integer) : void
 ->  //[MVIEW] awake ~S on var ~S // c,idx,
     let v := getVar(c,idx) in 
        for val in (c.minValue .. c.maxValue)
          (if not(choco/canBeInstantiatedTo(v,val))
             (//[MVIEW] awakeOnVar: delete from matching ~S-~S // idx,val - c.minValue + 1,     
              deleteMatch(c,idx,val - c.minValue + 1))),
     //[MVIEW] awakeOnVar: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]
    
[choco/awakeOnInf(c:CompleteAllDiff,idx:integer) : void
 -> for j in (c.minValue .. (c.vars[idx].inf - 1))
        deleteMatch(c,idx,j - c.minValue + 1),
    choco/constAwake(c,false) ]

[choco/awakeOnSup(c:CompleteAllDiff,idx:integer) : void
 -> for j in ((c.vars[idx].sup + 1) .. c.maxValue)
        deleteMatch(c,idx,j - c.minValue + 1),
    choco/constAwake(c,false) ]

[choco/awakeOnInst(c:CompleteAllDiff,idx:integer) : void
  -> setEdgeAndPublish(c,idx,c.vars[idx].value - c.minValue + 1),
     choco/constAwake(c,false) ]

// no specific initial propagation (awake does the same job as propagate)
[choco/awake(c:CompleteAllDiff) : void
 -> //[MVIEW] awake ~S // c,
    propagate(c)]

// ********************************************************************
// *   Part 9: dual models for permutation constraints                *
// ********************************************************************

// the constraint uses two lists of integer valued variables and models
// a permutation (and the inverse permutation) over (1 .. n)
Permutation <: BalancedBipartiteMatching()
[self_print(c:Permutation)
 -> printf("Permutation(~S,~S)",
           list{c.vars[i] | i in (1 .. c.nbLeftVertices)},
           list{c.vars[i + c.nbLeftVertices] | i in (1 .. c.nbRightVertices)})]
           
// main two events: when an edge is removed or selected in the assignment graph.
//  1. the internal state of the constraint (ref. match.) is update,
//  2. Propagate the integrity constraint of the symmetrical model
//         (v1[i] == j <=> V2[j] == i)
//  3. Propagate the binary difference constraints
//         (V1[i1] <> V1[i2], V2[j1] <> V2[j2])
//      if fromLeft = true,  inst(V1[i],j) is the caller, thus inst(V2[j],i) is called
//      if fromLeft = false, inst(V2[j],i) is the caller, thus inst(V1[i],j) is called
//  4. If the ref. matching is no longer maximal (of size n), it will be completed afterwards
//     (const event loop) and the strong connection component analysis will be performed

// An edge is definitely chosen in the assignement.
[setEdgeAndPublish(c:Permutation, i:integer,j:integer,fromLeft:boolean) : void
 -> let n := c.nbLeftVertices in
      (//[MDEBUG] set edge ~S,~S in the ref. matching// i,j,
       setMatch(c,i,j),
       if fromLeft choco/instantiate(c.vars[j + n],i,c.indices[j + n])
       else choco/instantiate(c.vars[i],j,c.indices[i]),
       //[MDEBUG] propagate the binary difference constraints incident to edge (~S,~S) // i,j,
       for j2 in ((1 .. n) but j)
           choco/removeVal(c.vars[j2 + n],i,c.indices[j2 + n]),
       for i2 in ((1 .. n) but i)
           choco/removeVal(c.vars[i2],j,c.indices[i2]) )]

// the last parameter, fromLeft, indicates whether the event comes from the leftToRight model
// (c.leftVars, also denoted v1) or the roghtToLeft model (c.rightVars, also denoted v2)
[deleteEdgeAndPublish(c:Permutation,i:integer,j:integer,fromLeft:boolean) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
      (assert(1 <= i & i <= n1 & 1 <= j & j <= n2),
       //[MDEBUG] remove edge ~S,~S from ref. matching if present// i,j,
       deleteMatch(c,i,j),
       //[MDEBUG] integrity constraint of sym. model, fix vars for edge ~S,~S// i,j,
       if fromLeft choco/removeVal(c.vars[j + n1],i,c.indices[j + n1])
       else choco/removeVal(c.vars[i],j,c.indices[i]) )]

// the event generated by the flow algorithm
[deleteEdgeAndPublish(c:Permutation,i:integer,j:integer) : void
 -> let n1 := c.nbLeftVertices, n2 := c.nbRightVertices in
      (assert(1 <= i & i <= n1 & 1 <= j & j <= n2),
       //[MDEBUG] remove edge ~S,~S from ref. matching if present // i,j,
       deleteMatch(c,i,j),
       //[MDEBUG] integrity constraint of sym. model, fix vars for edge ~S,~S// i,j,
       choco/removeVal(c.vars[i],j,c.indices[i]),
       choco/removeVal(c.vars[n1 + j],i,c.indices[n1 + j])) ]

// propagation functions: reacting to events
[awakeOnRem(c:Permutation,idx:integer,val:integer) : void
  -> let n := c.nbLeftVertices in
        (if (idx <= c.nbLeftVertices)
            deleteEdgeAndPublish(c,idx,val,true)
         else deleteEdgeAndPublish(c,val,idx - n,false)),
     choco/constAwake(c,false) ]

// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
[choco/awakeOnVar(c:Permutation,idx:integer) : void
  -> let n := c.nbLeftVertices, v := getVar(c,idx) in 
        (if (idx <= n)
            for j in (1 .. n)
               (if not(choco/canBeInstantiatedTo(v,j))
                   deleteEdgeAndPublish(c,idx,j,true))
         else 
            for j in (1 .. n)
               (if not(choco/canBeInstantiatedTo(v,j))
                   deleteEdgeAndPublish(c,j,idx - n,false)),
         choco/constAwake(c,false)) ]
     
[awakeOnInf(c:Permutation,idx:integer) : void
  -> let n := c.nbLeftVertices in
        (if (idx <= n)
            for j in (1 .. (c.vars[idx].inf - 1))
                deleteEdgeAndPublish(c,idx,j,true)
         else for j in (1 .. (c.vars[idx].inf - 1))
                  deleteEdgeAndPublish(c,j,idx - n,false)),
     choco/constAwake(c,false) ]

[awakeOnSup(c:Permutation,idx:integer) : void
  -> let n := c.nbLeftVertices in
        (if (idx <= n)
            for j in ((c.vars[idx].sup + 1) .. n)
                deleteEdgeAndPublish(c,idx,j,true)
         else for j in ((c.vars[idx].sup + 1) .. n)
                  deleteEdgeAndPublish(c,j,idx - n,false)),
     choco/constAwake(c,false) ]

// Note: this works even if the assigned value is not an edge of the reference matching
[awakeOnInst(c:Permutation,idx:integer) : void
  -> let n := c.nbLeftVertices in
        (if (idx <= n) setEdgeAndPublish(c,idx,c.vars[idx].value,true)
         else setEdgeAndPublish(c,c.vars[idx].value,idx - n,false)),
     choco/constAwake(c,false) ]

// performing the initial propagation
[awake(c:Permutation) : void
  -> let n := c.nbLeftVertices in
      (for i in (1 .. n)
          (choco/updateInf(c.vars[i],1,c.indices[i]),
           choco/updateSup(c.vars[i],n,c.indices[i])),
       for j in (1 .. n)
          (choco/updateInf(c.vars[j + n],1,c.indices[j + n]),
           choco/updateSup(c.vars[j + n],n,c.indices[j + n])),
       for i in (1 .. n)
         for j in (1 .. n)
           (if not(choco/canBeInstantiatedTo(c.vars[i],j))
               choco/removeVal(c.vars[j + n],i,c.indices[j + n]),
            if not(choco/canBeInstantiatedTo(c.vars[j + n],i))
               choco/removeVal(c.vars[i],j,c.indices[i])),
       removeUselessEdges(c))]

// API entry point: creating the constraint (before posting it)
[choco/permutation(l1:list[IntVar], l2:list[IntVar]) : Permutation
 -> let c := Permutation(), n := length(l1) in
      (c.vars := (list<IntVar>{v | v in l1} /+ list<IntVar>{v | v in l2}),
       closeLargeIntConstraint(c),
       c.nbLeftVertices := n, c.nbRightVertices := n, c.minValue := 1, c.maxValue := n,
       closeAssignmentConstraint(c),
       c)]

// ********************************************************************
// *   Part 10: global cardinality constraint (gcc, Regin 96)          *
// ********************************************************************

// a very simple version of the cardinality constraint where the values
//   the set of values whose occurrences are counted is the interval (minValue .. maxValue)
GlobalCardinality <: AbstractBipartiteFlow()
[self_print(c:GlobalCardinality)
 -> printf("GCC(~S)", list{c.vars[i] | i in (1 .. c.nbLeftVertices)})]

// The next two functions implement the main event:
// when an edge is definitely removed from the bipartite assignment graph.
[deleteEdgeAndPublish(c:GlobalCardinality,i:integer,j:integer) : void
 -> assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    deleteMatch(c,i,j),
    choco/removeVal(c.vars[i], j + c.minValue - 1, c.indices[i])]

[setEdgeAndPublish(c:GlobalCardinality,i:integer,j:integer) : void
 -> assert(1 <= i & i <= c.nbLeftVertices & 1 <= j & j <= c.nbRightVertices),
    setMatch(c,i,j),
    choco/instantiate(c.vars[i], j + c.minValue - 1, c.indices[i])]

// propagation functions: reacting to events
[awakeOnRem(c:GlobalCardinality,idx:integer,val:integer) : void
  -> //[MVIEW] awake ~S on rem ~S ~S // c,idx,val,
     deleteEdgeAndPublish(c,idx,val - c.minValue + 1),
     //[MVIEW] awakeOnRem: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]

// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
[choco/awakeOnVar(c:GlobalCardinality,idx:integer) : void
 ->  //[MVIEW] awake ~S on var ~S // c,idx,
     let v := getVar(c,idx) in 
        for val in (c.minValue .. c.maxValue)
          (if not(choco/canBeInstantiatedTo(v,val))
              (//[MVIEW] awakeOnVar: delete from matching ~S-~S // idx,val - c.minValue + 1,     
               deleteMatch(c,idx,val - c.minValue + 1))),
     //[MVIEW] awakeOnVar: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]
     
[awakeOnInf(c:GlobalCardinality,idx:integer) : void
  -> //[MVIEW] awakeOnInf ~S on var ~S // c,idx,
     for j in (1 .. (c.vars[idx].inf - 1))
         deleteMatch(c,idx,j - c.minValue + 1),
     //[MVIEW] awakeOnInf: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]

[awakeOnSup(c:GlobalCardinality,idx:integer) : void
  -> //[MVIEW] awakeOnSup ~S on var ~S // c,idx,
     for j in ((c.vars[idx].sup + 1) .. c.maxValue)
         deleteMatch(c,idx,j - c.minValue + 1),
     //[MVIEW] awakeOnSup: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]

// Note: this works even if the assigned value is not an edge of the reference matching
[awakeOnInst(c:GlobalCardinality,idx:integer) : void
  -> //[MVIEW] awakeOnInst ~S on var ~S // c,idx,
     setMatch(c,idx, c.vars[idx].value - c.minValue + 1),
     //[MVIEW] awakeOnInst: call to delayed propagation ~S // c,               
     choco/constAwake(c,false) ]

// performing the initial propagation
[awake(c:GlobalCardinality) : void
  -> for i in (1 .. c.nbLeftVertices)
        (choco/updateInf(c.vars[i],c.minValue,c.indices[i]),
         choco/updateSup(c.vars[i],c.maxValue,c.indices[i])),
     choco/propagate(c) ]

// API entry point: creating the constraint (before posting it)
[choco/gcc(l1:list[IntVar], l2:list[Interval]) : GlobalCardinality -> gcc(l1,1,length(l2),l2)]
[choco/gcc(l1:list[IntVar], val1:integer, val2:integer, l2:list[Interval]) : GlobalCardinality
 -> let c := GlobalCardinality() in
      (c.vars := list<IntVar>{v | v in l1},
       closeLargeIntConstraint(c),
       c.minValue := val1,
       c.maxValue := val2,
       c.nbRightVertices := val2 - val1 + 1,
       assert(length(l2) = c.nbRightVertices),
       c.nbLeftVertices := length(l1),
       c.minFlow := make_array(length(l2), integer, 0),
       c.maxFlow := make_array(length(l2), integer, 0),
       c.flow := make_array(length(l2), integer, 0),
       for i in (1 .. length(l2))
          (c.minFlow[i] := l2[i].arg1, c.maxFlow[i] := l2[i].arg2),
       closeAssignmentConstraint(c),
       c)]


[checkComponentOrder(c: AbstractBipartiteMatching): void 
 -> for i in (1 .. c.currentComponent) (
		let l := set<integer>(i),
			l2 := set<integer>{j in ((1 .. c.currentComponent)) | 
								read(c.componentOrder,i,j)},
			n1 := c.nbLeftVertices,
			n2 := c.nbRightVertices,
			stable := false,
			ok := true
		in (
			while not(stable) (
				stable := true,

				for vtx in (1 .. n1)
					for vtv in (mayMatch(c,vtx) but match(c,vtx)) (
						if ( (c.component[vtx] % l) & not(c.component[vtv + n1] % l)) (
							stable := false,
							l :add c.component[vtv + n1]
						)
					),
				for vtv in (1 .. n2) (
					for vtx in mayInverseMatch(c, vtv) (
						if (match(c,vtx) = vtv) (
							if ( (c.component[vtv + n1] % l) & not(c.component[vtx] % l)) (
								stable := false,
								l :add c.component[vtx]
							)
						)
					),
					if mayGrowFlowFromSource(c, vtv) (
						if ( (c.component[vtv + n1] % l) & not(c.component[c.source] % l) ) (
							stable := false,
							l :add c.component[c.source]
						)
					),
					if mayDiminishFlowFromSource(c, vtv) (
						if ( (c.component[c.source] % l) & not(c.component[vtv + n1] % l) ) (
							stable := false,
							l :add c.component[vtv + n1]
					)
				),
				
			),		
	

	
				
			if (l != l2) (
				printf("Wrong successors of ~S: ~S instead of ~S \n",i, l, l2),
				CHK := c,
				ok := false
;			)
;			else (
;				printf("Good successors of ~S: ~S \n",i, l)
			),
			if not(ok) error("STOP")
		)
	)]