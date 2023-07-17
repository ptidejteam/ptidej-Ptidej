// ********************************************************************
// * file: preFlow.cl                                                 *
// *    pre flow algorithm used in the flow constraints               *
// *                                                                  *
// * Author: Etienne Gaudin, Fabrice Buscaylet, Thierry Benoist       * 
// * version: 1.00.02                                                 *
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************
// TODO : 
//        improve data structure of PreFlowNodesSet

claire/PREFLOWVIEW:integer := 5
claire/PREFLOWTALK:integer := 5
claire/PREFLOWDEBUG:integer := 5


// **********************************************************************
// *   Part 1: object model                                             *
// *   Part 2: preflow algorithms for max flow                          *
// *   Part 3: preflow algorithms for min flow (on an existing flow)    *
// *   Part 4: node selection algorithms                                *
// *   Part 5: initialisation and update methods                        *
// *   Part 6: print and debug                                          *
// **********************************************************************


// **********************************************************************
// *   Part 1: object model                                             *
// **********************************************************************

// forward definition
PreFlowResEdge <: ephemeral_object
;PreFlowNode <:ephemeral_object
PreFlowNode <:object              // not ephemeral only  for debugging


// a node 
;PreFlowNode <: ephemeral_object(
PreFlowNode <: object(          // not ephemeral only  for debugging
  label:integer,              // no more useless, use in flow conservation initial propagation  + pretty printing
  nextEdge:list<PreFlowResEdge> = list<PreFlowResEdge>(), // list of edges from the node
  prevEdge:list<PreFlowResEdge> = list<PreFlowResEdge>(), // list of edges to the node
  excess:integer = 0,
  distLabel:integer = MAXINT)

// edges in the residual graph
// only one object for the two symmetrical edges (i,j) (j,i)
// note: SortedInt is a choco utility class storing a backtrackable integer with a time stamp, 
//       so that only one update is recorded per world
//       if you don't need to store the residual graph, r_ij and r_ji could be replaced by integer (then remove reify)
PreFlowResEdge <:ephemeral_object(
  i:PreFlowNode,
  j:PreFlowNode,
  ;r_ij:integer = 0,
  ;r_ji:integer = 0
  r_ij:StoredInt,           // residual capacity of edge (i,j) =  u_ij - l_ji - x_ij + x_ji (with x_ij flow between i and j)
  r_ji:StoredInt            // residual capacity of edge (j,i) =  u_ji - l_ij - x_ji + x_ij
;  //Ahuja and al : u'_ij = u_ij - l_ij, x'_ij = x_ij - l_ij, r_ij = (u_ij - x_ij) + (x_ji - l_ji)
)


PreFlowNodesSet <:ephemeral_object(flowNodesSet:set<PreFlowNode> = set<PreFlowNode>(),
                                   flowDistLabels:set<integer> = set<integer>())




reify(r_ij,r_ji)               // v0.63 use StoredInt of choco instead of default store for r_ij and r_ji -> minimize world stack 
;store(r_ij,r_ji)

// **********************************************************************
// *   Part 2: preflow algorithms for max flow                          *
// **********************************************************************

// default value for needToSature := true
// v0.63 g:PreFlowNode[] is no more a parameter
[initialMaxFlow(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode) : void
=> initialMaxFlow(preFlowNodes,sourceN,sinkN,true)]

// init distance label of each node for the max flow algorithm and  saturated the source node 
// v0.63 g:PreFlowNode[] is no more a parameter
[initialMaxFlow(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode,needToSature:boolean) : void
-> let activeNodes := initDistLabel(sinkN) in             // v0.62.03
   (//[PREFLOWTALK] finding max flow from node ~S to node ~S // sourceN,sinkN,
   if (sourceN.distLabel = MAXINT)
   ( //[PREFLOWVIEW] source distLabel is unknown, no path from source to sink, flow is null 
   )
   else 
   ( if (needToSature)// source saturation
     ( for edge in sourceN.nextEdge
       ( addFlow(preFlowNodes,edge,edge.r_ij)), // inital value in residual graph is equal to edge capacity so adding max flow
       updateDistLabel(preFlowNodes,sourceN),  
       //[PREFLOWTALK] source ~S has been saturated // sourceN,
       if (sourceN.distLabel != MAXINT) contradiction(),
       assert(sourceN.distLabel = MAXINT)),
     preFlowNodes.flowDistLabels :delete MAXINT,
     sourceN.distLabel := activeNodes,                    // v0.62.03
     preFlowPushMax(preFlowNodes,sourceN,sinkN)))]
     
// THE Algorithm for max flow     
// compute Max Flow with preFlow push algorithms
// !!! use heuristics to get the best node, for example maintain sorted list on distLabel
[preFlowPushMax(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode) : void
-> let saturated? := false in 
   ( while not(saturated?)
     ( ;when node := some(node in preFlowNodes.flowNodesSet | (node.excess > 0) & (node != sourceN) & (node != sinkN)) in
       when node := highestDistLabelNodes(preFlowNodes,sourceN,sinkN) in
       ( //[PREFLOWDEBUG] node ~S is not saturated // node,
         when edge := some(edge in node.nextEdge | edge.r_ij > 0 & edge.j.distLabel = node.distLabel - 1) in
         ( //[PREFLOWDEBUG] adding flow ~A on edge ~S // min(edge.r_ij,node.excess),edge,
           addFlow(preFlowNodes,edge, min(edge.r_ij,node.excess)))
         else
         ( when edge := some(edge in node.prevEdge | edge.r_ji > 0 & edge.i.distLabel = node.distLabel - 1) in 
           ( //[PREFLOWDEBUG] adding negative flow ~A on edge ~S // max(-(edge.r_ji),-(node.excess)),edge,
             addFlow(preFlowNodes,edge, max(-(edge.r_ji),-(node.excess))))
           else (updateDistLabel(preFlowNodes,node))))
       else saturated? := true)),
   //[PREFLOWTALK] max flow is ~S // sinkN.excess
] 

// **********************************************************************
// *   Part 3: preflow algorithms for min flow (on an existing flow)    *
// **********************************************************************

// minimum flow algorithm, same principle as max flow
// take a  initial feasible flot (if the flow value of this feasible flot is 0 then there is nothing to do)
// and to remove (instead of adding in max flow) the maximum of flow
// -> so use the graph in reverse order
// init distance label of each node for the max flow algorithm and  saturated the sink node 
// v0.63 g:PreFlowNode[] is no more a parameter
[initialMinFlow(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode) : void
=> initialMinFlow(preFlowNodes,sourceN,sinkN,true)]
[initialMinFlow(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode,needToSature:boolean) : void
-> let activeNodes := initDistLabel(sourceN,true) in // v0.62.03
   (//[PREFLOWTALK] finding min flow from node ~S to node ~S // sourceN,sinkN,
   if (sinkN.distLabel = MAXINT)
   ( //[PREFLOWVIEW] sink distLabel is unknown, no path from sink to source, flow is null 
   )
   else 
   ( if (needToSature)// source saturation
     ( // source saturation
       for edge in sinkN.prevEdge     // v0.5 prev instead of next
       ( addFlow(preFlowNodes,edge,-(edge.r_ji))), // inital value in residual graph is equal to edge capacity so cancelling max - min  flow
       updateDistLabel(preFlowNodes,sinkN),  
       //[PREFLOWTALK] sink ~S has been saturated // sinkN,
       assert(sinkN.distLabel = MAXINT)),
     preFlowNodes.flowDistLabels :delete MAXINT,
     sinkN.distLabel := activeNodes, // v0.62.03
     preFlowPushMin(preFlowNodes,sourceN,sinkN)))]

// THE Algorithm for min flot
// compute Min Flow with preFlow push algorithms
// try to find max flow on reverse graph
// !!! use heuristics to get the best node, for example maintain sorted list on distLabel
[preFlowPushMin(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode) : void
-> let saturated? := false in 
   ( while not(saturated?)
     ( ;when node := some(node in preFlowNodes.flowNodesSet | (node.excess > 0) & (node != sourceN) & (node != sinkN)) in
       when node := highestDistLabelNodes(preFlowNodes,sourceN,sinkN) in
       ( //[PREFLOWDEBUG] node ~S is not saturated // node,
         when edge := some(edge in node.prevEdge | edge.r_ji > 0 & edge.i.distLabel = node.distLabel - 1) in
         ( //[PREFLOWDEBUG] cancelling flow ~A on edge ~S // min(edge.r_ji,node.excess),edge,
           addFlow(preFlowNodes,edge, -(min(edge.r_ji,node.excess))))
         else
         ( when edge := some(edge in node.nextEdge | edge.r_ij > 0 & edge.j.distLabel = node.distLabel - 1) in
           ( //[PREFLOWDEBUG] adding flow ~A on edge ~S // max(-(edge.r_ij),-(node.excess)),edge,
             addFlow(preFlowNodes,edge, min(edge.r_ij,node.excess)))
           else (updateDistLabel(preFlowNodes,node))))
       else saturated? := true)),
   //[PREFLOWTALK] min flow is ~S // sourceN.excess
] 

// **********************************************************************
// *   Part 4: node selection algorithms                                *
// **********************************************************************

// return a node with the highest distance label
[highestDistLabelNodes(preFlowNodes:PreFlowNodesSet,sourceN:PreFlowNode,sinkN:PreFlowNode) : (PreFlowNode U {unknown})
-> let maxDist: integer := preFlowNodes.flowDistLabels[size(preFlowNodes.flowDistLabels)] ,
       cmax: integer := 0 ,
       returnNode := unknown ,
       found:boolean := false in 
   ( if (preFlowNodes.flowNodesSet = set<PreFlowNode>())
       unknown
     else
     ( // search in the set of node with excess if one as maxDist as distLabel
       for node in list{n in randomAccess(preFlowNodes.flowNodesSet) | n != sourceN & n != sinkN}
;       for node in list{n in preFlowNodes.flowNodesSet | n != sourceN & n != sinkN}
       ( if (node.distLabel = maxDist)
         ( returnNode := node,
           found := true,
           break() 
         ) 
         else // compute the max seen distance label 
         ( if (node.distLabel > cmax) 
           ( cmax := node.distLabel ,
             returnNode := node 
           ))),    
       if (found)
       ( returnNode as PreFlowNode) 
       else
       ( preFlowNodes.flowDistLabels :=  set<integer>{ n.distLabel | n in preFlowNodes.flowNodesSet} ,
         ;preFlowNodes.flowDistLabels :=  { v in preFlowNodes.flowDistLabels | v <= cmax } , //v0.64.02
         returnNode as PreFlowNode
       )))]


// FUN: randomAccess
// chooses a random starting point in the list and starts iterating from there (cyclic loop)
// <FLA> , extract from sword
[randomAccess(s:set[PreFlowNode]) : list[PreFlowNode]
 -> let n := size(s), first := random(n) + 1 in
      list{s[i] | i in (first .. n)} /+ list{s[i] | i in (1 .. first - 1)}]

[Iterate(c:randomAccess[tuple(bag)],v:Variable,e:any) : void
 => let s:set[PreFlowNode] := eval(c.args[1]), n := size(s), first := random(n) + 1, i := first in
      (while true
         (let v := s[i] in e,
          i :+ 1, if (i > n) i := 1,
          if (i = first) break() ))]


// **********************************************************************
// *   Part 5: initialisation and update methods                        *
// **********************************************************************



// adds deltaFlow on edgeIdx of node, updates residual graph and excess value 
// if deltaFlow is lower than 0 then adding flow to the reverse edge
// NOTE: be carefull with => instead of ->, not the same result as deltaFlow could be edge.r_ij 
[addFlow(preFlowNodes:PreFlowNodesSet,edge:PreFlowResEdge,deltaFlow:integer) : void
-> updateResEdge(edge,deltaFlow),
   updateExcess(preFlowNodes,edge,deltaFlow)]
  
// update residual graph when adding deltaFlow on edgeIdx of node, 
[updateResEdge(edge:PreFlowResEdge, deltaFlow:integer) : void
->  //[PREFLOWDEBUG] adding ~A to edge ~S // deltaFlow,edge,
    edge.r_ij  :- deltaFlow,
    edge.r_ji :+ deltaFlow]

// update excess list when adding deltaFlow on edgeIdx of node 
[updateExcess(preFlowNodes:PreFlowNodesSet,edge:PreFlowResEdge, deltaFlow:integer) : void
-> //[PREFLOWDEBUG] adding ~A to excess ~A of node ~A, removing it to excess ~A  of node ~S// deltaFlow,edge.j.excess,edge.j.label,edge.i.excess,edge.i.label,
   edge.i.excess :- deltaFlow,
   edge.j.excess :+ deltaFlow,
   if (edge.i.excess = 0) 
   ( preFlowNodes.flowNodesSet :delete edge.i )
   else 
   ( if (edge.i.excess > 0) 
     ( preFlowNodes.flowNodesSet :add edge.i,
       preFlowNodes.flowDistLabels :add edge.i.distLabel)),
   if (edge.j.excess = 0) 
   ( preFlowNodes.flowNodesSet :delete edge.j )
   else 
   ( if (edge.j.excess > 0) 
     ( preFlowNodes.flowNodesSet :add edge.j,
       preFlowNodes.flowDistLabels :add edge.j.distLabel)),
   //[PREFLOWDEBUG] c.excessPreFlowNodes:~S // preFlowNodes.flowNodesSet
   ] 


// put a distLabel to MAXINT
[reInitDistLabel(node:PreFlowNode) : void
=> node.distLabel := MAXINT]


// reverse = false : use the graph
//           true  : use reverse graph
[initDistLabel(lastNode:PreFlowNode) : integer 
=> initDistLabel(lastNode,false)]

// initialisation by a backward breadth-first search
// distLabel is a lower bound of the shortest distance to t in the residual network 
// v0.62.03 return the number of active node (highest distance label)
// v1.01 reverse is useless!!!
[initDistLabel(initNode:PreFlowNode,reverse:boolean) : integer 
-> let currentNodes:list<PreFlowNode> := list<PreFlowNode>(initNode), 
       oldNodes:list<PreFlowNode> := list<PreFlowNode>(), currentDist := 0,
       nbReachableNodes := 1 in
   ( currentNodes[1].distLabel := currentDist,
     while (currentNodes != list<PreFlowNode>())
     ( oldNodes := currentNodes,
       currentNodes := list<PreFlowNode>(),
       currentDist :+ 1,
       for node in oldNodes
;      ( let edgeList:list<PreFlowResEdge> := (if reverse node.nextEdge else node.prevEdge) in
       ( let edgeList:list<PreFlowResEdge> := node.prevEdge in
         ( for edge in list<PreFlowResEdge>{e in edgeList | e.r_ij > 0}
           ( if (edge.i.distLabel > currentDist)  // v0.5 reinit even if distLabel is not MAXINT
             ( edge.i.distLabel := currentDist,
               currentNodes := (currentNodes add edge.i),
               nbReachableNodes :+ 1))),
;         let edgeList:list<PreFlowResEdge> := (if reverse node.prevEdge else node.nextEdge) in
         let edgeList:list<PreFlowResEdge> := node.nextEdge in
         ( for edge in list<PreFlowResEdge>{e in edgeList | e.r_ji > 0}
           ( if (edge.j.distLabel > currentDist)  // v0.5 reinit even if distLabel is not MAXINT
             ( edge.j.distLabel := currentDist,
               currentNodes := (currentNodes add edge.j),
               nbReachableNodes :+ 1))))),
     //[PREFLOWTALK] ~A nodes reach by initDistLabel // nbReachableNodes,
     nbReachableNodes
     )]
                 
             
// new distLabel for i is min{ distLabel(j) | (i,j) is an edge and r_ij > 0 }            
[updateDistLabel(preFlowNodes:PreFlowNodesSet,n:PreFlowNode) : void
-> let minDist := MAXINT in
   ( for edge in n.nextEdge 
     ( if ((edge.r_ij > 0) & (edge.j.distLabel + 1 < minDist)) 
       ( minDist := edge.j.distLabel + 1,
         //[PREFLOWDEBUG] distLabel ~A for node ~A is improved by edge ~S // minDist, n.label, edge
         )),
     for edge in n.prevEdge
     ( if  ((edge.r_ji > 0) & (edge.i.distLabel + 1 < minDist))
       ( minDist := edge.i.distLabel + 1,
         //[PREFLOWDEBUG] distLabel ~A for node ~A is improved by reverse edge ~S // minDist, n.label, edge
         )),                                                  
     n.distLabel := minDist,
     if (n.excess > 0) preFlowNodes.flowDistLabels :add n.distLabel,
     if (minDist = MAXINT)
       //[PREFLOWTALK] node ~S as no neibhourgs and is relabeled, it must be the source // n
     )]  





// **********************************************************************
// *   Part 6: Double chained set                                       *
// **********************************************************************

    
// simple implementation of a double chained list
// add and delete are in o(1)
// labelIdxList, prevIdxList and nextIdxList must be initialized with 0
// length(prevIdxList) =  length(nextIdxList) = length(labelIdxList) = maxSize
// chaining the label and the hole
// labelOffset insure that (label - labelOffset) are always in [1 .. maxSize]
// firstHole is the index of the bottom hole
// top is index of the top label
BitVector <: ephemeral_object
DoubleChainedList <: ephemeral_object(
  prevIdxList:list[integer] = nil,
  nextIdxList:list[integer] = nil,
  labelIdxList:list[integer] = nil,
  usedLabel:BitVector,
  labelOffset:integer = 0,
  maxSize:integer = 0,
  firstHole:integer = 1,
  top:integer = 0)
  

  
[removeIdx(chain:DoubleChainedList, label:integer) : void
=> assert(label <= chain.maxSize),
   if use?(chain.usedLabel,label - chain.labelOffset) // else label is not in
   let pos := chain.labelIdxList[label - chain.labelOffset],
       nextIdx := chain.nextIdxList[pos],
       prevIdx := chain.prevIdxList[pos] in
   ( if (prevIdx != 0)
       chain.nextIdxList[prevIdx] := nextIdx,
     if (nextIdx != 0)
       chain.prevIdxList[nextIdx] := prevIdx,
     if (chain.top = pos)
       chain.top := prevIdx,
     use-(chain.usedLabel, label - chain.labelOffset),
     chain.labelIdxList[pos] := 0,
     chain.nextIdxList[pos] := chain.firstHole,
     chain.prevIdxList[pos] := 0,
     chain.firstHole := pos)]


[addIdx(chain:DoubleChainedList, label:integer) : void
=> assert(label <= chain.maxSize),
   if not(use?(chain.usedLabel,label - chain.labelOffset)) // else label is already in 
   let newPos := chain.firstHole in
   ( assert(newPos <= chain.maxSize),
     assert(chain.labelIdxList[newPos] = 0),
     if (chain.nextIdxList[newPos] = 0)
       chain.firstHole :+ 1
     else
       chain.firstHole := chain.nextIdxList[newPos],
     chain.prevIdxList[chain.firstHole] := 0,
     chain.nextIdxList[newPos] := 0,
     chain.prevIdxList[newPos] := chain.top,
     if (chain.top != 0)
       chain.nextIdxList[chain.top] := newPos,
     chain.top := newPos,
     use(chain.usedLabel,label - chain.labelOffset),
     chain.labelIdxList[newPos] := label)]


// bitVector class form sword 
BitVector <: ephemeral_object(contents:list<integer> = list<integer>())

// returns true if ith pos is equal to 1
[use?(s:BitVector,i:integer) : boolean
 -> let j := 1, l := s.contents in
        (while (i >= 30) (i :- 30, j :+ 1),
         if (j <= length(l)) l[j][i] else false) ]

// true if the schedule contains at least one unit
[used?(s:BitVector) : boolean -> exists(i in s.contents | i != 0) ]

// set "use" the ith half pos is equal to 0
[use(s:BitVector,i:integer) : void
 -> let j := 1, l := s.contents in
        (assert(not(use?(s,i))),
         while (i >= 30) (i :- 30, j :+ 1),
         if (j > length(l)) (for k in (length(l) .. j) l :add 0,
                             s.contents := l),
         l[j] := l[j] + ^2(i))  ]

// set "not used" the ith pos 
[use-(s:BitVector,i:integer) : void
 ->  let j := 1, l := s.contents in
        (assert(use?(s,i)),
         while (i >= 30) (i :- 30, j :+ 1),
         l[j] := l[j] - ^2(i))]

// create a nice Bitvector that contains at least n bits
[makeBitVector(n:integer): BitVector
 -> BitVector(contents = make_list(1 + n / 30, 0)) ]

// **********************************************************************
// *   Part 7: print and debug                                          *
// **********************************************************************


[self_print(x:PreFlowNodesSet) : void
-> printf("{"),
   if (size(x.flowNodesSet) > 0)
   ( for i in (1 .. (size(x.flowNodesSet) - 1)) printf("~A, ",x.flowNodesSet[i].label),
     printf("~A, ",x.flowNodesSet[size(x.flowNodesSet)].label)),
   printf("}")]


[self_print(x:PreFlowNode)
-> printf("~A(d:~A,e:~A)",x.label,x.distLabel,x.excess)]


[self_print(x:PreFlowResEdge)
-> printf("~A->~A(r_ij:~S,r_ji:~S)",x.i.label,x.j.label,x.r_ij,x.r_ji)]
