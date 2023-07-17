// ********************************************************************
// * file: flowConstraint.cl                                          *
// *    flow constraints                                              *
// *                                                                  *
// * Author: Etienne Gaudin, Thierry Benoist                          * 
// * version: 1.00.02                                                 *
// * Copyright (C) Bouygues, 2001,2002 see readme.txt                 *
// ********************************************************************
// TODO : enrich local propagation (add krichoff law on edge)
//        bound consistency on min flow
//        propose a new API with a graph

claire/FLOWVIEW:integer := 5
claire/FLOWTALK:integer := 5
claire/FLOWDEBUG:integer := 5

// **********************************************************************
// *   Part 1: object model                                             *
// *   Part 2: API functions                                            *
// *   Part 3: linking with choco solver                                *
// *   Part 4: global propagation algorithms based on preflow           *
// *   Part 5: local propagation algorithms based on flow conservation  *
// *   Part 6: initialisation of the graph for the preflow              *
// *   Part 7: print and debug                                          *
// *   Part 8: Store object                                             *
// **********************************************************************


// **********************************************************************
// *   Part 1: object model                                             *
// **********************************************************************

// forward definition
FlowResEdge <: PreFlowResEdge
FlowNode <: PreFlowNode
FlowNodesSet <:ephemeral_object


// global variables, to improve code readability 
// index of source and sink in c.flowGraph
;claire/FLOWREALSOURCE:integer := 2  // v0.64.02 now it could be any node, the source index is memorized in FlowConstraint

// index of the first real node, all real nodes are contiguous in c.flowGraph
// new in v0.64.01
claire/FLOWREALNODE:integer := 2
// index of dummy source and dummy sink in c.flowGraph (dummy nodes are uses for flow with min flow contraint)
claire/FLOWDUMMYSOURCE:integer := 1

// a flow object
;FlowConstraint <: GlobalConstraint(   // <ega> v0.3.01 no more available in choco 1.02 
FlowConstraint <: IntConstraint(
  indices:list<integer> = list<integer>(),    // indices of the flow constraint for each variable
  linkVarIdxToEdge:FlowResEdge[], // use to access variables through edges 
  flowVar:IntVar,
  flowGraph:FlowNode[], // for all (i,j) such as j % flowGraph[i.nextEdge] (ie (i,j) flow is constrained by a variable domain)
                        //               then not(i % flowGraph[j.nextEdge]) (the symetrical edge flow (j,i) as a null capa)
                        // first node and last node in this array are dummy node  
  excessFlowNodes:PreFlowNodesSet,   // memorize node with excess in the current preflow, if list = nil the preflow is a flow
                                     // not store because after each constraint fix point, it must be empty!
  sourceIdx:integer,                 // v0.64.02 index of the source in flowGraph
  sinkIdx:integer                    // v0.64.02 index of the sink in flowGraph
)

// a node 
FlowNode <: PreFlowNode(
  dummyNextEdge:FlowResEdge,  // edge (also in nextEdge) link to FLOWDUMMYSINK, create only if necessary for feasible min flow
  dummyPrevEdge:FlowResEdge,  // edge (also in prevEdge) link to FLOWDUMMYSOURCE, idem
  b:integer = 0,              // b = sum(l_(label,i)) - sum(l_(label,j))
  staticCapa:boolean = false)

// edges in the residual graph
// only one object for the two symmetrical edges (i,j) (j,i)
// v0.63 r_ij and r_ji as StoreForFlow object to limit world stack explosion 
FlowResEdge <: PreFlowResEdge(
  l_ij:integer = 0,           // flow lower bound, initialized to avoid pb with store
  u_ij:integer = 0,           // flow upper bound, idem
  varIdx:integer = -1,        // index of the variable for choco
  lu_ij:(IntVar U integer)    // lu for lower (l_ij) and upper (u_ij) bounds of  (i,j) edge (by definition u_ji = 0 and l_ji = 0)
                              // if  lu_ij % integer  -> l_ij = 0, lu_ij = u_ij
                              // if  lu_ij % IntVar   -> l_ij = getInf(lu_ij)
                              //                         u_ij = getSup(lu_ij)
)



store(l_ij,u_ij,staticCapa,b)  // useless to store excess (always 0 outside algo) and distLabel (recompute before each preflow algo),  
                               // !!! b is alway 0 after awake so could manage it in a special way, pb is incremental update

// **********************************************************************
// *   Part 2: API functions                                            *
// **********************************************************************

// flow constraints are always stated as
//   flow(graph,fVar) where:
// graph: a list of list of outgoing edge capacity,
//        graph[i] : list of tuple(capa_ij,j) 
//                   capa_ij is an IntVar : flow through edge (i,j) must be in [capa_ij.min,capa_ij.max]       
//                           or an integer : flow through edge (i,j) must be in [0,capa_ij]
// flow: flow from node 1 to node length(graph) must be in [fVar.min,fVar.max],
;[choco/flow(graph:list[list[tuple((IntVar U integer),integer)]],fVar:IntVar) : FlowConstraint
//@api
[choco/flow(graph:list[list[any]],fVar:IntVar) : FlowConstraint
=> choco/flow(graph,1,length(graph),fVar)]

;[choco/flow(graph:list[list[tuple((IntVar U integer),integer)]],soIdx:integer, siIdx:integer, fVar:IntVar) : FlowConstraint
//@api
[choco/flow(graph:list[list[any]],soIdx:integer, siIdx:integer, fVar:IntVar) : FlowConstraint
-> assert(graph[length(graph)] = nil),
   let returnObj := FlowConstraint(flowVar = fVar,
                                   flowGraph = array!(list<FlowNode>{ makeFlowNode(i + 1) | i in (0 .. (length(graph) + 2))}),
                                   excessFlowNodes = PreFlowNodesSet(),
                                   sourceIdx = soIdx + 1,
                                   sinkIdx = siIdx + 1),
       edgeWithVars := list<FlowResEdge>(),
       myVarIdx := 1 in  // <ega> v0.3    1 instead of 0
   ( //[FLOWTALK] initializing flow constraint on ~S // graph,
     // initialize node and edge
     for cpt in (1 .. length(graph))
     ( let node := returnObj.flowGraph[cpt + 1],
           edgeList := graph[cpt] in
       (assert(node.nextEdge = list<PreFlowResEdge>()),
         if (cpt = siIdx)
         ( if (edgeList != nil)
           ( //[FLOWVIEW] node ~A is the sink, outcoming edges ~S  are ignored // cpt,edgeList,
             edgeList := nil)),
         for j in (1 .. length(edgeList))
         ( if (edgeList[j][2] = soIdx) 
           ( //[FLOWVIEW] edge ~S to the source ~A is ignored // edgeList[j],returnObj.sourceIdx,
             false)
           else node.nextEdge :add makeFlowResEdge(node, returnObj.flowGraph[edgeList[j][2] + 1], edgeList[j][1]) ),
         //[FLOWDEBUG] initializing node: ~S // node,                                                                             
         for edge in node.nextEdge
         ( edge.j.prevEdge :add edge,
           //[FLOWDEBUG] initializing edge: ~S // edge,
           ( case edge.lu_ij (IntVar (edgeWithVars :add edge,
                                      edge.varIdx := myVarIdx,
                                      myVarIdx :+ 1, 
                                     edge.i.b :- choco/getInf(edge.lu_ij),
                                     edge.j.b :+ choco/getInf(edge.lu_ij)),
                             any nil)
                            )))),
     //[FLOWDEBUG] add a node with one edge just after sink node, this node is the real sink in this constraint,
     // flow through the node edge should be in [fVar.min,fVar.max] 
     let realSink := returnObj.flowGraph[length(returnObj.flowGraph) - 1],
         initSink := returnObj.flowGraph[returnObj.sinkIdx],
         edge := makeFlowResEdge(initSink,realSink,fVar) in
     ( initSink.nextEdge :add edge, realSink.prevEdge :add edge,
       edgeWithVars :add edge, 
       edge.varIdx := myVarIdx,
       edge.i.b :- choco/getInf(edge.lu_ij as choco/IntVar), edge.j.b :+ choco/getInf(edge.lu_ij as choco/IntVar)),
     assert(returnObj.flowGraph[returnObj.sourceIdx].prevEdge = nil),
     assert(length(returnObj.flowGraph[returnObj.sinkIdx].nextEdge) = 1),
     returnObj.linkVarIdxToEdge := array!(edgeWithVars),
     returnObj.indices := make_list(choco/getNbVars(returnObj),integer,0),          // <ega> v0.3.02
     // initialize dummy nodes
     returnObj.flowGraph[FLOWDUMMYSOURCE].nextEdge := list<PreFlowResEdge>(),
     returnObj.flowGraph[length(returnObj.flowGraph)].nextEdge := list<PreFlowResEdge>(),
     //[FLOWTALK] flow constraint ~S has been build // returnObj,
     returnObj)
   // !!! should check that there is no symmetrical edge in graph                  
]
                              
//@api output for enumerating
// return x_ij value for edge i 
[choco/getFlow(c:FlowConstraint,i:integer) : integer
-> ( let e := c.linkVarIdxToEdge[i] in
     ( //[FLOWDEBUG] edge ~S lu_ij=~S // e,e.lu_ij,
       case e.lu_ij (IntVar (e.l_ij + max(e.u_ij - e.r_ij - e.l_ij, 0)),  // <ega> v0.30
                     integer ((e.lu_ij as integer) - e.r_ij),
                     any error("type error in getFlow\n"))))]

// return current max flow
[choco/getMaxFlow(c:FlowConstraint) : integer
-> let e := c.linkVarIdxToEdge[choco/getNbVars(c)] in
   ( e.l_ij + max(e.u_ij - e.r_ij - e.l_ij, 0))]

// **********************************************************************
// *   Part 3: linking with choco solver                                *
// **********************************************************************

// Return the number of vars of the constraint
[choco/getNbVars(c:FlowConstraint) : integer => length(c.linkVarIdxToEdge)]

// Return variable with index i
[choco/getVar(c:FlowConstraint, i:integer) : IntVar
-> c.linkVarIdxToEdge[i].lu_ij as IntVar]
       
// set FlowConstraint to the lowest priority (awake only when no more events) 
// value should be in {1,2,3,4}
[choco/getPriority(c:FlowConstraint) : integer => 4]

// Connect the constraint (could be in choco standard)
// <ega> v0.3.02 useless since choco 1.03
;[choco/connect(c:FlowConstraint) : void
;-> c.indices := make_list(getNbVars(c),integer,0),
;   for i in (1 .. getNbVars(c))
;   ( connectVar(c,getVar(c,i),i))]

// <ega> v0.3.02 need since choco 1.03
[choco/setConstraintIndex(c:FlowConstraint, i:integer,idx:integer) : void
-> c.indices[i] := idx]


// Reactions to choco solver events
// first propagation
[choco/awake(c:FlowConstraint) : void
-> //[FLOWTALK] fist propagate on flow constraint,
   for e in c.linkVarIdxToEdge
   ( if (choco/getInf(e.lu_ij as choco/IntVar) < 0) 
       updateInf(e.lu_ij as choco/IntVar,0,c.indices[e.varIdx])),
   awakeInitialFlowConservation(c),
   awakeInitialPreFlow(c)]   
       
// standard propagation
[choco/propagate(c:FlowConstraint) : void
-> //[FLOWTALK] try to propagate on flow constraint ~S // c,
   // v0.51 only awake on sup is needed
   // v0.62 new strategies : awakeFeasible and then maxPreFlow if necessary
   awakeFeasiblePreFlow(c), 
   if (getMaxFlow(c) != choco/getSup(c.flowVar))
     awakeMaxPreFlow(c)]
   
[awakeOnInst(c:FlowConstraint,idx:integer) : void 
-> let v := choco/getVar(c,idx) in
   ( awakePreFlowOnInst(c,v,idx),
     awakeNodeFlowConservationOnInst(c,c.linkVarIdxToEdge[idx]))] 
         
[awakeOnInf(c:FlowConstraint,idx:integer) : void 
-> let v := choco/getVar(c,idx) in
   ( awakePreFlowOnInf(c,v,idx),
     awakeNodeFlowConservationOnInf(c,c.linkVarIdxToEdge[idx]))]
     
[awakeOnSup(c:FlowConstraint,idx:integer) : void 
-> let v := choco/getVar(c,idx) in
   ( awakePreFlowOnSup(c,v,idx),
     awakeNodeFlowConservationOnSup(c,c.linkVarIdxToEdge[idx]))]

   
//update choco solver but also propagate to flow conservation   
[updatePreFlowSup(c:FlowConstraint,v:IntVar,newM:integer,constIdx:integer,e:FlowResEdge) : void
-> updateSup(v,newM,constIdx),
   updateEdgeSup(e,v),
   awakeNodeFlowConservationOnSup(c,e)]

[updatePreFlowInf(c:FlowConstraint,v:IntVar,newM:integer,constIdx:integer,e:FlowResEdge) : void
-> updateInf(v,newM,constIdx),
   updateEdgeInf(e,v),
   awakeNodeFlowConservationOnInf(c,e)]


// update choco solver but also propagate to PreFlow (!!!)
[updateFlowVarInf(c:FlowConstraint,v:IntVar,newV:integer,vIdx:integer,isPreFlowActive?:boolean) : void
-> updateInf(v,newV,c.indices[vIdx]),
   if (isPreFlowActive?)
     awakePreFlowOnInf(c,v,vIdx)]

[updateFlowVarSup(c:FlowConstraint,v:IntVar,newV:integer,vIdx:integer,isPreFlowActive?:boolean) : void
-> updateSup(v,newV,c.indices[vIdx]),
   if (isPreFlowActive?)
     awakePreFlowOnSup(c,v,vIdx)]

// **********************************************************************
// *   Part 4: global propagation algorithms based on preflow           *
// **********************************************************************


// check if flow remains consistent, if yes update the residual edge according to new value
[awakePreFlowOnInst(c:FlowConstraint,v:IntVar,idx:integer) : void
-> if (getFlow(c,idx) != choco/getInf(v)) ;& not(c.needToUpdate)) // v0.5
   ( //[FLOWTALK] awakePreFlowOnInst: current pre flow is no more consistent on edge ~S // c.linkVarIdxToEdge[idx],
     if (getFlow(c,idx) > choco/getInf(v))
     ( 
       if not(incrementalUpdateEdgeOnSup(c,idx))
         constAwake(c,false),
       updateEdgeInf(c.linkVarIdxToEdge[idx],v))
     else
     ( if not(incrementalUpdateEdgeOnInf(c,idx))
         constAwake(c,false),
       updateEdgeSup(c.linkVarIdxToEdge[idx],v)))
   else
   ( // value of v is equal to current flow, only need to update the edge 
     let e := c.linkVarIdxToEdge[idx] in 
     ( updateEdgeInst(e,v)))]
   
// check if flow remains consistent, if yes update the residual edge according to new value
[awakePreFlowOnInf(c:FlowConstraint,v:IntVar,idx:integer) : void
-> if (getFlow(c,idx) < choco/getInf(v))
   ( //[FLOWTALK] awakePreFlowOnInf: current pre flow is no more consistent on edge ~S // c.linkVarIdxToEdge[idx],
     // change the preflow, if this change reduce the flow then call constAwake  
     if not(incrementalUpdateEdgeOnInf(c,idx))
       constAwake(c,false))
   else
   ( // bound change is consistent with current flow, only need to update edge
     updateEdgeInf(c.linkVarIdxToEdge[idx],v))]


// update the residual edge according to new value and change preflow if necessary
[awakePreFlowOnSup(c:FlowConstraint,v:IntVar,idx:integer) : void
-> // if bound change is no consistent with current preflow, change it
   if (getFlow(c,idx) > choco/getSup(v))
   ( //[FLOWTALK] awakePreFlowOnSup: current pre flow is no more consistent on edge ~S // c.linkVarIdxToEdge[idx],
     // change the preflow and then update the edge
     if not(incrementalUpdateEdgeOnSup(c,idx))
       constAwake(c,false)) 
   else
   ( // preflow is consistent, only need to update edge
     updateEdgeSup(c.linkVarIdxToEdge[idx],v))]



// inital awake,
// create dummy edges if there is min flow contraints on edges
[awakeInitialPreFlow(c:FlowConstraint) : void
-> ( let sourceN:FlowNode := c.flowGraph[c.sourceIdx],
         sinkN:FlowNode := c.flowGraph[length(c.flowGraph) - 1] in
     ( // adds a dummy edge from real sink to real source with an infinite capacity (or theorical max flow so getSup(c.flowVar))
       sinkN.nextEdge :add makeFlowDummyResEdge(sinkN,sourceN,0),
       sourceN.prevEdge :add sinkN.nextEdge[length(sinkN.nextEdge)])),
   awakeStaticPreFlow(c,true)]


// basic algorithm, use preflow algorithm and tigger a contradiction if flow is out of flowVar
// 3 stages process :
// - find a feasible flow (usefull only when there is min flow constraint on edges)
// - compute max flow 
// - check consitency 
[awakeStaticPreFlow(c:FlowConstraint) : void 
-> awakeStaticPreFlow(c,false)]
[awakeStaticPreFlow(c:FlowConstraint,firstTime:boolean) : void
-> let sourceN:FlowNode := c.flowGraph[c.sourceIdx],
       sinkN:FlowNode := c.flowGraph[length(c.flowGraph) - 1] in 
   ( // initialize residual edges and node b and excess attributes
     assert(c.excessFlowNodes.flowNodesSet = set<PreFlowNode>()),
     for cpt in (FLOWREALNODE .. (length(c.flowGraph) - 1))
     ( let node := c.flowGraph[cpt] in
       ( node.b := 0, node.excess := 0,
         for edge in node.nextEdge
         ( initFlowResEdge(edge)))),
 
     // compute attribute b (difference between minimum flow coming in and minimum flow coming out) of each node 
     // v0.62 was previously in updateFeasibleMinGraph
     for cpt in (FLOWREALNODE .. (length(c.flowGraph) - 1))
     ( let node := c.flowGraph[cpt] in  
       ( for edge in node.nextEdge
         ( case edge.lu_ij (IntVar (edge.i.b :- choco/getInf(edge.lu_ij),
                                    edge.j.b :+ choco/getInf(edge.lu_ij)),
                            any nil)))), 
                            
     // checks if min flow on edges is feasible
     awakeFeasiblePreFlow(c,firstTime),
     //[FLOWTALK] after testing feasible min Flow of ~S // c,
     awakeMinPreFlow(c),
     awakeMaxPreFlow(c,firstTime)),
   //[FLOWVIEW] flow constraint ~S has been awaked // c
   ]


// compute a max flow from the current pre flow
// use the pre flow algorithm on the residual graph
// trigger a contradiction if max flow is not in c.flowVar domain
// propagate : flow on each edge is lower or equal to max flow 
[awakeMaxPreFlow(c:FlowConstraint) : void
=> awakeMaxPreFlow(c,false)]
[awakeMaxPreFlow(c:FlowConstraint,firstTime:boolean) : void
-> let sourceN:FlowNode := c.flowGraph[c.sourceIdx],
       sinkN:FlowNode := c.flowGraph[length(c.flowGraph) - 1],
       realMaxFlow:integer := 0 in 
   ( assert(sinkN.excess = 0), assert(sourceN.excess = 0),
     if (sourceN.excess != 0) contradiction(),
     initialMaxFlow(c.excessFlowNodes,sourceN,sinkN),
     if (sourceN.excess > 0) // usefull is the current preflow before initialMaxFlow was not a flow
     ( c.excessFlowNodes.flowNodesSet :delete sourceN),
     if (sinkN.excess > 0)
     ( c.excessFlowNodes.flowNodesSet :delete sinkN), 
     sourceN.excess := 0, sinkN.excess := 0,
     assert(c.excessFlowNodes.flowNodesSet = set<PreFlowNode>()),
     realMaxFlow := getMaxFlow(c),
     //[FLOWVIEW] max flow is ~A // realMaxFlow,
     // set edge distance to MAXINT for nodes (usefull for all nodes to see unreachable node in next call to initDistLabel) 
     // but it is not mandatory as unreachable node have no excess, so are not iterated by preflow algorithm 
     reInitDistLabel(c.flowGraph),
     if not(choco/canBeInstantiatedTo(c.flowVar,realMaxFlow))
     ( //[FLOWTALK] obtains max flow = ~A is incompatible with ~S // realMaxFlow,c.flowVar,
       choco/raiseContradiction(c))
     else 
     ( // propagate on sup bound of the global flow
       if (realMaxFlow < choco/getSup(c.flowVar))
       ( updatePreFlowSup(c,c.flowVar,realMaxFlow,c.indices[choco/getNbVars(c)],c.linkVarIdxToEdge[choco/getNbVars(c)]),
         // propagate also u_ij <= realMaxFlow
         if not(firstTime)
         ( for vidx in (1 .. length(c.linkVarIdxToEdge))
             let e := c.linkVarIdxToEdge[vidx] in
             ( if (e.u_ij > realMaxFlow)
               ( updatePreFlowSup(c,e.lu_ij as choco/IntVar,realMaxFlow,c.indices[vidx],e)))))
       else
       ( if firstTime
         ( for vidx in (1 .. length(c.linkVarIdxToEdge))
             let e := c.linkVarIdxToEdge[vidx] in
             ( if (e.u_ij > realMaxFlow)
               ( updatePreFlowSup(c,e.lu_ij as choco/IntVar,realMaxFlow, c.indices[vidx],e)))))))]

// return true if current preflow in c is a flow
[isAFlow(c:FlowConstraint) : boolean
-> //[FLOWDEBUG] isAFlow: c.excessFlowNodes = ~S // c.excessFlowNodes.flowNodesSet,
   (c.excessFlowNodes.flowNodesSet = set<PreFlowNode>())]

// compute a min flow from the current feasible flow (if current feasible flot is null, then supposed that min flot is null)
// use the pre flow algorithm on the inverse residual graph
// propagate lower bound of max flow 
[awakeMinPreFlow(c:FlowConstraint) : void
-> let sourceN:FlowNode := c.flowGraph[c.sourceIdx],
       sinkN:FlowNode := c.flowGraph[length(c.flowGraph) - 1],
       minFlow:integer := getMaxFlow(c) in 
   ( if (minFlow > 0)
     ( assert(sinkN.excess = 0), assert(sourceN.excess = 0),
       initialMinFlow(c.excessFlowNodes,sourceN,sinkN),
       c.excessFlowNodes.flowNodesSet :delete sinkN,assert(c.excessFlowNodes.flowNodesSet = set<PreFlowNode>()),
       minFlow := getMaxFlow(c),
       reInitDistLabel(c.flowGraph),
       // current max flow is empty, 
       sinkN.excess := 0, sourceN.excess := 0,  // v0.5 
       //[FLOWVIEW] min flot of ~S is ~A // c,minFlow,
       // propagate on inf bound of the global flow
       if (minFlow > choco/getInf(c.flowVar))
       ( updatePreFlowInf(c,c.flowVar,minFlow,c.indices[choco/getNbVars(c)],c.linkVarIdxToEdge[choco/getNbVars(c)])))
     else // feasible flow value is 0 so min flot is 0
     ( assert(minFlow = 0),
       if (minFlow > choco/getInf(c.flowVar))
       ( updatePreFlowInf(c,c.flowVar,minFlow,c.indices[choco/getNbVars(c)],c.linkVarIdxToEdge[choco/getNbVars(c)]))))]


// transform the feasible bounded flow problem in a maxflow problem by adding a dummy source and sink and some dummy edge
// trigger a contradiction is flow is unfeasible
[awakeFeasiblePreFlow(c:FlowConstraint) : void
=> awakeFeasiblePreFlow(c,false)]
[awakeFeasiblePreFlow(c:FlowConstraint, firstTime:boolean) : void
-> let sourceN:FlowNode := c.flowGraph[c.sourceIdx],
       sinkN:FlowNode := c.flowGraph[length(c.flowGraph) - 1],
       dummySource:FlowNode := c.flowGraph[FLOWDUMMYSOURCE],
       dummySink:FlowNode := c.flowGraph[length(c.flowGraph)],
       needToCheck:boolean := false in 
   ( assert(list{e in dummySink.prevEdge | (e.r_ij != 0 | e.r_ji != 0)} = nil),
     assert(list{e in dummySource.nextEdge | (e.r_ij != 0 | e.r_ji != 0)} = nil),
     //[FLOWDEBUG] awakeFeasiblePreFlow is awake on ~S // c.flowGraph,
     needToCheck := updateFeasibleMinGraph(c,sourceN,sinkN),
     if (needToCheck)
     ( //[FLOWTALK] must find a feasible flow according to min flow constraint,
       // set to infinite (or theorical max flow so (getSup(c.flowVar) - sinkN.b = previous max flow)) the capacity of the dummy edge linking sinkN to sourceN  
       if (firstTime)
       ( initFlowResEdge(sinkN.nextEdge[1],choco/getSup(c.flowVar)))
       else
       ( initFlowResEdge(sinkN.nextEdge[1],choco/getSup(c.flowVar) - sinkN.b),
         updateResEdge(sinkN.nextEdge[1],getMaxFlow(c) - sinkN.b)),
       // solve max flow on the modified graph
       initialMaxFlow(c.excessFlowNodes,dummySource,dummySink),
       // clean up excessFlowNodes, and for each node distLabel and b
       c.excessFlowNodes.flowNodesSet :delete dummySink, assert(c.excessFlowNodes.flowNodesSet = set<PreFlowNode>()), 
       reInitDistLabel(c.flowGraph),
       let saturatingFlow := maxTheoreticalNextFlow(dummySource)  in
       ( if (saturatingFlow != dummySink.excess)
         ( //[FLOWTALK] min flow on edges are unfeasibles, needed ~A, obtained ~A // saturatingFlow, dummySink.excess,
           // clean up non store value
           dummySink.excess := 0, dummySource.excess := 0,
           for edge in dummySink.prevEdge edge.i.b := 0,
           for edge in dummySource.nextEdge edge.j.b := 0,
           choco/raiseContradiction(c))),   
       // flow in the feasible flow is equal to the flow in edge linking initial sink and real sink
       //[FLOWVIEW] feasible flow is ~A // getMaxFlow(c), 
       // reinitialized graph for standard max flow 
       // disactivated edge linked to dummy nodes and the edge between source and sink
       for edge in dummySink.prevEdge
       ( edge.r_ij := 0, edge.r_ji := 0, edge.u_ij := 0, edge.i.b := 0),
       for edge in dummySource.nextEdge
       ( edge.r_ij := 0, edge.r_ji := 0, edge.u_ij := 0, edge.j.b := 0),
       sinkN.nextEdge[1].r_ij := 0,
       sinkN.nextEdge[1].r_ji := 0,
       dummySink.excess := 0, dummySource.excess := 0))]
     
// return upper bound of flow coming out from node n
[maxTheoreticalNextFlow(n:FlowNode) : integer
-> let val:integer := 0 in
   ( for e in (n.nextEdge as list[FlowResEdge])
     ( val :+ e.u_ij),
     val)]


// update dummy edges and dummy nodes according to current min flow value on edge
// try to minimize GC triggering by minimizing creation of dummy edges
[updateFeasibleMinGraph(c:FlowConstraint,sourceN:FlowNode,sinkN:FlowNode) : boolean
-> let dummySource:FlowNode := c.flowGraph[FLOWDUMMYSOURCE],
       dummySink:FlowNode := c.flowGraph[length(c.flowGraph)],
       needToCheck:boolean := false in 
   ( // update or create dummy edges  
     // for each real node with b != 0 
     //   if b < 0 adds a dummy edge trough the dummySink of -b capacity
     //   if b > 0 adds a dummy edge from the dummySource of b capacity
     for nIndex in (FLOWREALNODE .. (length(c.flowGraph) - 1))
     ( let node:FlowNode := c.flowGraph[nIndex] in
       ( if (node.b < 0)
         ( when dummyEdge := get(dummyNextEdge,node) in
           ( initFlowResEdge(dummyEdge,(node.b * -1)))
           else
           ( node.dummyNextEdge := makeFlowDummyResEdge(node,dummySink,(node.b * -1)),
             node.nextEdge :add node.dummyNextEdge,
             dummySink.prevEdge :add node.dummyNextEdge),
           needToCheck := true)
         else
         ( if (node.b > 0)
           ( when dummyEdge := get(dummyPrevEdge,node) in
             ( initFlowResEdge(dummyEdge,node.b))
             else 
             ( node.dummyPrevEdge := makeFlowDummyResEdge(dummySource,node,node.b),
               dummySource.nextEdge :add node.dummyPrevEdge,
               node.prevEdge :add node.dummyPrevEdge),
             needToCheck := true)))),
     needToCheck)]

                           

// incremental update of residual graph in edge
// new values are consistent with current flow
// (seems to be true even if it is only a preflow and not a flow)
// l_ij and u_ij have changed
[updateEdgeInst(e:FlowResEdge, v:IntVar) : void
-> //[FLOWDEBUG] before updateEdgeInst ~S // e,
   e.r_ij :+ -(e.u_ij) + choco/getSup(v), 			//v0.63
   e.r_ji :+ e.l_ij - choco/getInf(v),    			//v0.63
   assert(e.r_ij >= 0),
   assert(e.r_ji >= 0),
   e.u_ij := choco/getInf(v),
   e.l_ij := e.u_ij,
   //[FLOWDEBUG] after updateEdgeInst ~S // e
   ]

// incremental update of residual graph in edge
// new values are consistent with current flow
// l_ij has changed
[updateEdgeInf(e:FlowResEdge, v:IntVar) : void
-> //[FLOWDEBUG] before updateEdgeInf ~S // e, 
   e.r_ji :+ e.l_ij - choco/getInf(v), 				//v0.63
   assert(e.r_ji >= 0),
   e.l_ij := choco/getInf(v),
   //[FLOWDEBUG] after updateEdgeInf ~S // e
   ]

// incremental update of residual graph in edge
// new values are consistent with current flow
// u_ij has changed
[updateEdgeSup(e:FlowResEdge, v:IntVar) : void
-> //[FLOWDEBUG] before updateEdgeSup ~S // e, 
   e.r_ij :+ -(e.u_ij) + choco/getSup(v), 			//v0.63
   assert(e.r_ij >= 0),
   e.u_ij := choco/getSup(v),
   //[FLOWDEBUG] after updateEdgeSup ~S // e
   ]
   
// incremental update of current preflow when a new flow on an edge is no more consistent with it 
// cancel a part of the flow to become preflow consistent
// return true if this call is enough to maintain fixpoint
[incrementalUpdateEdgeOnSup(c:FlowConstraint,varIdx:integer) : boolean
-> let e:FlowResEdge := c.linkVarIdxToEdge[varIdx],
       gapToAdd:integer :=  choco/getSup(e.lu_ij as choco/IntVar) - getFlow(c,varIdx)  in 
   ( //[FLOWDEBUG] incrementalUpdateEdgeOnSup on edge ~S with gap ~A // e, gapToAdd,
     e.i.b :- gapToAdd, e.j.b :+ gapToAdd,           // v0.62 maintain b
     updateResEdge(e,gapToAdd),
     updateEdgeSup(e,e.lu_ij as choco/IntVar),
     false)]
     ;incrementalUpdateEdge(c,e,gapToAdd)] // v0.62 alway call constAwake 
       
       


// incremental update of feasible flow from current max flow when new min flow on an edge is greater then current flow on it
// return true if this call maintain fix point 
[incrementalUpdateEdgeOnInf(c:FlowConstraint,varIdx:integer) : boolean
-> let e:FlowResEdge := c.linkVarIdxToEdge[varIdx],
       gapToAdd:integer := choco/getInf(e.lu_ij as choco/IntVar) - getFlow(c,varIdx) in
   ( //[FLOWDEBUG] incrementalUpdateEdgeOnInf on edge ~S with gap ~A // e, gapToAdd,
     e.i.b :- gapToAdd, e.j.b :+ gapToAdd,           // v0.62 maintain b
     updateResEdge(e,gapToAdd),
     updateEdgeInf(e,e.lu_ij as choco/IntVar),
     false)]
     ;incrementalUpdateEdge(c,e,gapToAdd))]     // v0.62 alway call constAwake 





// incremental update of feasible flow from current max flow when flow on edge e changes
// return true if this call maintain fix point on max flow (ie feasible flow value remains max flow)
// useless since v0.62, if current flow is inconsistent alway call constAwake (which call awakeFeasiblePreFlow)
;[incrementalUpdateEdge(c:FlowConstraint,e:FlowResEdge,gapToAdd:integer) : boolean
;-> let dummySource:FlowNode := c.flowGraph[FLOWDUMMYSOURCE],
;       dummySink:FlowNode := c.flowGraph[length(c.flowGraph)],       
;       realSource:FlowNode := c.flowGraph[FLOWREALSOURCE],
;       realSink:FlowNode := c.flowGraph[length(c.flowGraph) - 1],
;       initialFlow:integer := getMaxFlow(c),
;       gapCapa:integer :=  (if (gapToAdd > 0) gapToAdd else -(gapToAdd)) in
;   ( //[FLOWTALK] incrementalUpdateEdge on edge ~S with gap ~A // e, gapToAdd,
;     assert(realSource.excess = 0), assert(realSink.excess = 0),
;     assert(c.excessFlowNodes.flowNodesSet = {}),
;     //link dummy sink e.i and and dummy source to e.j, capa of the edges is set to gapToAdd
;     when dummyEdge := get(dummyNextEdge,e.i) in
;       ( initFlowResEdge(dummyEdge,gapCapa))
;       else
;       ( e.i.dummyNextEdge := makeFlowDummyResEdge(e.i,dummySink,gapCapa),
;         e.i.nextEdge :add e.i.dummyNextEdge,
;         dummySink.prevEdge :add e.i.dummyNextEdge), 
;     when dummyEdge := get(dummyPrevEdge,e.j) in
;       ( initFlowResEdge(dummyEdge,gapCapa))
;       else
;       ( e.j.dummyPrevEdge := makeFlowDummyResEdge(dummySource,e.j,gapCapa),
;         e.j.prevEdge :add e.j.dummyPrevEdge,
;         dummySource.nextEdge :add e.j.dummyPrevEdge),
;     // activate edge between realSink and realSource
;     initFlowResEdge(realSink.nextEdge[1],choco/getSup(c.flowVar)),
;     updateResEdge(realSink.nextEdge[1],getMaxFlow(c)),
;     // add/remove flow to the edge and modify residual value then change the current feasible flow 
;     if (gapToAdd > 0)
;     ( assert(e.r_ij >= gapToAdd),
;       updateResEdge(e,gapToAdd), 
;       updateEdgeInf(e,e.lu_ij),
;       initialMaxFlow(c.excessFlowNodes,dummySource,dummySink))
;     else
;     ( // remove flow from e and through it to dummySink and dummySource
;       assert(e.r_ji >= gapCapa),
;       addFlow(c.excessFlowNodes,e.j.dummyPrevEdge,gapCapa),
;       addFlow(c.excessFlowNodes,e,-(gapCapa)),
;       addFlow(c.excessFlowNodes,e.i.dummyNextEdge,gapCapa),
;       updateEdgeSup(e,e.lu_ij),
;       assert(e.j.excess = 0), assert(e.i.excess = 0),
;       initialMinFlow(c.excessFlowNodes,dummySource,dummySink)),
;     c.excessFlowNodes.flowNodesSet :delete dummySink,
;     reInitDistLabel(c.flowGraph),
;     assert(c.excessFlowNodes.flowNodesSet = {}),
;     if (((gapToAdd < 0) & (dummySink.excess != 0)) | ((gapToAdd > 0) & (dummySink.excess != gapToAdd)))
;     ( // update non store value,
;       dummySink.excess := 0, dummySource.excess := 0,
;       choco/raiseContradiction())
;     else
;     ( // desactivate dummy edges
;       e.j.dummyPrevEdge.r_ij := 0, e.j.dummyPrevEdge.r_ji := 0, e.j.dummyPrevEdge.u_ij := 0,
;       e.i.dummyNextEdge.r_ij := 0, e.i.dummyNextEdge.r_ji := 0, e.i.dummyNextEdge.u_ij := 0,
;       realSink.nextEdge[1].r_ij := 0, realSink.nextEdge[1].r_ji := 0, realSink.nextEdge[1].u_ij := 0,
;       dummySink.excess := 0, dummySource.excess := 0,
;       (initialFlow = getMaxFlow(c))
;       ))] 


// **********************************************************************
// *   Part 5: local propagation algorithms based on flow conservation  *
// **********************************************************************



// awake method for propagation of node flow conservation :
// Xin1 + Xin2 + ... - Xout1 - Xout2 - ....= 0 with (in1,in2,... % prevEdge) and (out1,out2,... % nextEdge) 
//
// TODO : flow conservation is propagated on nodes, it could be done on edges 

// propagation is implemented with two methods for the two following events : 
// 1) change on a max of incoming edges or a min of outcoming edges : awakeNodeFlowConservationOnMaxOutMinIn
// 2) change on a max of outcoming edges or a min of incoming edges : awakeNodeFlowConservationOnMaxInMinOut
// these two methods generate new events if the call propagates something
//


// standard propagation events
// awake on edge, for each edge extremity awake with the corresponding event
[awakeNodeFlowConservationOnInf(c:FlowConstraint,e:FlowResEdge) : void
-> //[FLOWTALK] awakeNodeFlowConservationOnInf on edge ~A->~A // e.i.label,e.j.label,
   // for node e.j, edge e is an incoming node -> awake on min incoming
   // for node e.i, edge e is an outcoming node -> awake on min outcoming
   awakeFlowConservation(c,
                         awakeNodeFlowConservationOnMaxOutMinIn(c,e.j),
                         awakeNodeFlowConservationOnMaxInMinOut(c,e.i))]
   
// symmetrical   
[awakeNodeFlowConservationOnSup(c:FlowConstraint,e:FlowResEdge) : void
-> //[FLOWTALK] awakeNodeFlowConservationOnSup on edge ~A->~A // e.i.label,e.j.label,
   // for node e.i, edge e is an outcoming node -> awake on max outcoming
   // for node e.j, edge e is an incoming node -> awake on max incoming
   awakeFlowConservation(c,
                         awakeNodeFlowConservationOnMaxOutMinIn(c,e.i),
                         awakeNodeFlowConservationOnMaxInMinOut(c,e.j))]

// need to call both awaking methods for each node
[awakeNodeFlowConservationOnInst(c:FlowConstraint,e:FlowResEdge) : void
-> //[FLOWTALK] awakeNodeFlowConservationOnInst on edge ~A->~A // e.i.label,e.j.label,
   awakeFlowConservation(c,
          (awakeNodeFlowConservationOnMaxOutMinIn(c,e.j) U awakeNodeFlowConservationOnMaxOutMinIn(c,e.i)),
          (awakeNodeFlowConservationOnMaxInMinOut(c,(e.i.staticCapa := false,e.i)) U 
           awakeNodeFlowConservationOnMaxInMinOut(c,(e.j.staticCapa := false,e.j))))]


// main algorithm for managing propagation of flow conservation on all the network
// no particular heuristics (could use the degree of each node, low degree first?)
// ping pong between each event until fix point is reached
[awakeFlowConservation(c:FlowConstraint, toUpdateOnMaxInMinOut:set[integer],toUpdateOnMaxOutMinIn:set[integer]) : void
=> awakeFlowConservation(c, toUpdateOnMaxInMinOut,toUpdateOnMaxOutMinIn,true)]

[awakeFlowConservation(c:FlowConstraint, toUpdateOnMaxInMinOut:set[integer],toUpdateOnMaxOutMinIn:set[integer], 
                       isPreFlowActive?:boolean) : void
-> // check if there still something to awake
   while (toUpdateOnMaxInMinOut != {} | toUpdateOnMaxOutMinIn != {})
   ( while (toUpdateOnMaxInMinOut != {})
     ( //  propagate improvement of  max edge flow for an incoming node or for min edge flow for an outcoming node 
       let crtNodeIdx := toUpdateOnMaxInMinOut[size(toUpdateOnMaxInMinOut)] in
       ( toUpdateOnMaxInMinOut := (toUpdateOnMaxInMinOut delete crtNodeIdx) as set[integer],   // !!! check with François to see if it is an efficient claire implementation
         toUpdateOnMaxOutMinIn := (toUpdateOnMaxOutMinIn 
                                   U awakeNodeFlowConservationOnMaxInMinOut(c,c.flowGraph[crtNodeIdx],isPreFlowActive?)
                                   ) as set[integer])), 
     while (toUpdateOnMaxOutMinIn != {})
     ( //  propagate improvement of max edge flow for an outcoming node or for min edge flow for an incoming node 
       let crtNodeIdx := toUpdateOnMaxOutMinIn[size(toUpdateOnMaxOutMinIn)] in
       ( toUpdateOnMaxOutMinIn := (toUpdateOnMaxOutMinIn delete crtNodeIdx) as set[integer],
         toUpdateOnMaxInMinOut := (toUpdateOnMaxInMinOut
                                   U awakeNodeFlowConservationOnMaxOutMinIn(c,c.flowGraph[crtNodeIdx],isPreFlowActive?)
                                   ) as set[integer])))]


// special case for initial propagation
// - can filter "need to awake" event as we first awake all node on both events
// - no propagation to PreFlow (last to be initialized)
//
// then awake the main method (awakeFlowConservation) 
[awakeInitialFlowConservation(c:FlowConstraint) : void
-> let toUpdateNodesOnMaxInMinOut:set[integer] := {},
       toUpdateNodesOnMaxOutMinIn:set[integer] := {} in
   ( for nIndex in (FLOWREALNODE .. (length(c.flowGraph) - 1))
     ( toUpdateNodesOnMaxInMinOut := (toUpdateNodesOnMaxInMinOut 
                                      U {n in awakeNodeFlowConservationOnMaxOutMinIn(c,c.flowGraph[nIndex],false) | n < nIndex}
                                      ) as set[integer], // need only to awake nodes already awaked
       toUpdateNodesOnMaxOutMinIn := (toUpdateNodesOnMaxOutMinIn 
                                      U {n in awakeNodeFlowConservationOnMaxInMinOut(c,c.flowGraph[nIndex],false) | n <= nIndex}
                                      ) as set[integer]),// nIndex should be awaked again if this call propagates something 
     awakeFlowConservation(c,toUpdateNodesOnMaxInMinOut,toUpdateNodesOnMaxOutMinIn,false),
     //[FLOWVIEW] flot is ~S after flow conservation // c.flowVar
     )]
     
     
// wake up on a change of a max of an incoming edge or a min of an outcoming edge
// update min of incoming edges and max of outcoming edges of node n and return the "need to awake" set of nodes
//
// propagation rules for e in n.prevEdge (an incoming edge) 
// 1)  min(e.lu_ij) >= sum_min(out in nextEdge) - sum_max(in in prevEdge | in != e)  
//
// propagation rules for e in n.nextEdge (an outcoming edge) 
// 2)  max(e.lu_ij) <= sum_max(in in prevEdge) - sum_min(out in nextEdge | out != e)
//
// compute first maxInMinOut value = sum_max(in in prevEdge) - sum_min(out in nextEdge)
// so rule 1) min(e.lu_ij) >= max(e.lu_ij) - maxInMinOut
//         2) max(e.lu_ij) <= min(e.lu_ij) + maxInMinOut
//
// when a new bound is deducted, recursively call this method to propagate this new event to the other edge extremity
// IMPORTANT NOTE : if events are generated, need to generate a new event on node n 
//                  of type "change of a max of an outcoming edge or a min of an incoming edge"
//                  these calls must be done by the calling method 

[awakeNodeFlowConservationOnMaxInMinOut(c:FlowConstraint, n:FlowNode) : set<integer>
=> awakeNodeFlowConservationOnMaxInMinOut(c,n,true)]
[awakeNodeFlowConservationOnMaxInMinOut(c:FlowConstraint, n:FlowNode,isPreFlowActive?:boolean) : set<integer>
-> if not(n.staticCapa)
   ( let toUpdateNodes:set<integer> := set<integer>(), 
         maxInMinOut := 0,
         becomeStaticCapa? := true,
         change := false,
         // special case from source or sink (next edges of sink are next edges of source and reciprocally for prev edges)
         prevList:list<PreFlowResEdge> := (if (n.label = c.sourceIdx) 
                                          ( (c.flowGraph[length(c.flowGraph) - 1].prevEdge) as list<PreFlowResEdge>) 
                                        else 
                                          ((n.prevEdge) as list<PreFlowResEdge>)),
         nextList:list<PreFlowResEdge> := (if (n.label = length(c.flowGraph) - 1) 
                                          ((c.flowGraph[c.sourceIdx].nextEdge) as list<PreFlowResEdge>)
                                        else 
                                          ((n.nextEdge) as list<PreFlowResEdge>)) in
     ( for e in prevList                                        // initialize maxInMinOut and maxOutMinIn
       ( if (e.lu_ij % integer) maxInMinOut :+ (e.lu_ij as integer)
         else maxInMinOut :+ choco/getSup(e.lu_ij as choco/IntVar)),
       for e in nextList
       ( if not(e.lu_ij % integer) maxInMinOut :- choco/getInf(e.lu_ij as choco/IntVar)),
       //[FLOWDEBUG] call awakeNodeFlowConservationOnMaxInMinOut(~A) on node ~S // maxInMinOut,n,     
       for e in list{ee in prevList | not(ee.lu_ij % integer)}
       ( if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) // checking if edge flow is in an uninstantiated variable
         ( let newMin := choco/getSup(e.lu_ij as choco/IntVar) - maxInMinOut in // compute new min bound
           ( if (choco/getInf(e.lu_ij as choco/IntVar) < newMin)
             ( change := true,
               updateFlowVarInf(c,e.lu_ij as choco/IntVar,newMin,e.varIdx,isPreFlowActive?),   // post to choco the new bound
               //[FLOWDEBUG] new min ~S = ~A deducted by flow conservation on prevEdge // e.lu_ij as choco/IntVar, newMin,
               if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) becomeStaticCapa? := false,
               toUpdateNodes := (toUpdateNodes
                                 U awakeNodeFlowConservationOnMaxInMinOut(c,e.i,isPreFlowActive?)
                                ) as set<integer>)    // should wake up node i to propagate new bound
             else becomeStaticCapa? := false))),                 
       for e in list{ee in nextList | not(ee.lu_ij % integer)}
       ( if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) // checking if edge flow is in an uninstantiated variable
         ( let newMax := choco/getInf(e.lu_ij as choco/IntVar) + maxInMinOut in // compute new max bound
           ( if (choco/getSup(e.lu_ij as choco/IntVar) > newMax)
             ( change := true,
               updateFlowVarSup(c,e.lu_ij as choco/IntVar,newMax,e.varIdx,isPreFlowActive?),   // post to choco the new bound
               //[FLOWDEBUG] new max ~S = ~A deducted by flow conservation on nextEdge // e.lu_ij as choco/IntVar, newMax, 
               if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) becomeStaticCapa? := false,
               toUpdateNodes := (toUpdateNodes 
                                 U awakeNodeFlowConservationOnMaxInMinOut(c,e.j,isPreFlowActive?)
                                 ) as set<integer>)    // should wake up node j to propagate new bound
             else becomeStaticCapa? := false))),
       if (change & not(becomeStaticCapa?)) toUpdateNodes := (toUpdateNodes add n.label),
       if (becomeStaticCapa?)
         n.staticCapa := true,
       //[FLOWDEBUG] awakeNodeFlowConservationOnMaxInMinOut returns ~S // set!(toUpdateNodes),
       toUpdateNodes))
   else    
   ( //[FLOWDEBUG] node ~A is static for awakeNodeFlowConservationOnMaxInMinOut // n.label,
     set<integer>())] 
        

// wake up on a change of a max of an outcoming edge or a min of an incoming edge
// wake up to update max of incoming edges and min of outcoming edges of node n and return the "need to awake" set of nodes
//        
// propagation rules for e in n.prevEdge (an incoming edge) 
// 3) max(e.lu_ij) <= sum_max(out in nextEdge) - sum_min(in in prevEdge | in != e)
//
// propagation rules for e in n.nextEdge (an outcoming edge) 
// 4) min(e.lu_ij) >= sum_min(in in prevEdge) - sum_max(out in nextEdge | out != e)
//
// compute first maxOutMinIn value = sum_max(out in nextEdge) - sum_min(int in prevEdge)
// so rule 3) max(e.lu_ij) <= min(e.lu_ij) + maxOutMinIn
//         4) min(e.lu_ij) >= max(e.lu_ij) - maxOutMinIn
//
// when a new bound is deducted, recursively call this method to propagate this new event to the other edge extremity
// IMPORTANT NOTE : if events are generated, need to generate a new event on node n 
//                  of type "change of a max of an incoming edge or a min of an outcoming edge"
//                  these called must be done by the calling method 
[awakeNodeFlowConservationOnMaxOutMinIn(c:FlowConstraint, n:FlowNode) : set<integer>
-> awakeNodeFlowConservationOnMaxOutMinIn(c,n,true)]
[awakeNodeFlowConservationOnMaxOutMinIn(c:FlowConstraint, n:FlowNode, isPreFlowActive?:boolean) : set<integer>
-> if not(n.staticCapa)
   ( let toUpdateNodes:set<integer> := set<integer>(), 
         maxOutMinIn := 0,
         becomeStaticCapa? := true,
         change := false,
         // special case from source or sink (next edges of sink are next edges of source and reciprocally for prev edges)
         prevList:list<PreFlowResEdge> := (if (n.label = c.sourceIdx) 
                                         ((c.flowGraph[length(c.flowGraph) - 1].prevEdge) as list<PreFlowResEdge>)
                                        else 
                                         ((n.prevEdge) as list<PreFlowResEdge>)),
         nextList:list<PreFlowResEdge> := (if (n.label = length(c.flowGraph) - 1) 
                                         ((c.flowGraph[c.sourceIdx].nextEdge) as list<PreFlowResEdge>)
                                        else 
                                         ((n.nextEdge) as list<PreFlowResEdge>)) in
     ( for e in prevList                                        // initialize maxInMinOut and maxOutMinIn
       ( if not(e.lu_ij % integer) maxOutMinIn :- choco/getInf(e.lu_ij as choco/IntVar)),
       for e in nextList
       ( if (e.lu_ij % integer) maxOutMinIn :+ (e.lu_ij as integer)
         else maxOutMinIn :+ choco/getSup(e.lu_ij as choco/IntVar)),
       //[FLOWDEBUG] call awakeNodeFlowConservationOnMaxOutMinIn(~A) on node ~S // maxOutMinIn,n,    
       for e in list{ee in prevList | not(ee.lu_ij % integer)}
       ( if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) // checking if edge flow is in an uninstantiated variable
         ( let newMax := choco/getInf(e.lu_ij as choco/IntVar) + maxOutMinIn in // compute new max bound
           ( if (choco/getSup(e.lu_ij as choco/IntVar) > newMax)
             ( change := true,
               updateFlowVarSup(c,e.lu_ij as choco/IntVar,newMax,e.varIdx,isPreFlowActive?),   // post to choco the new bound
               //[FLOWDEBUG] new max ~S = ~A deducted by flow conservation on prevEdge // e.lu_ij as choco/IntVar, newMax,
               if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) becomeStaticCapa? := false,
               toUpdateNodes := (toUpdateNodes 
                                 U awakeNodeFlowConservationOnMaxOutMinIn(c,e.i,isPreFlowActive?)
                                 ) as set<integer>)    // should wake up node i to propagate new bound
             else becomeStaticCapa? := false))),
       for e in list{ee in nextList | not(ee.lu_ij % integer)}
       ( if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) // checking if edge flow is in an uninstantiated variable
         ( let newMin := choco/getSup(e.lu_ij as choco/IntVar) - maxOutMinIn in // compute new min bound
           ( if (choco/getInf(e.lu_ij as choco/IntVar) < newMin)
             ( change := true,
               updateFlowVarInf(c,e.lu_ij as choco/IntVar,newMin,e.varIdx,isPreFlowActive?),   // post to choco the new bound
               //[FLOWDEBUG] new min ~S = ~A deducted by flow conservation on nextEdge // e.lu_ij as choco/IntVar, newMin,
               if not(choco/isInstantiated(e.lu_ij as choco/IntVar)) becomeStaticCapa? := false,
               toUpdateNodes := (toUpdateNodes 
                                 U awakeNodeFlowConservationOnMaxOutMinIn(c,e.j,isPreFlowActive?)
                                 ) as set<integer>)    // should wake up node j to propagate new bound
             else becomeStaticCapa? := false))),
       if (change & not(becomeStaticCapa?)) toUpdateNodes := (toUpdateNodes add n.label),
       if (becomeStaticCapa?)
         n.staticCapa := true,
       //[FLOWDEBUG] awakeNodeFlowConservationOnMaxOutMinin returns ~S // set!(toUpdateNodes),
       toUpdateNodes)) 
   else 
   ( //[FLOWDEBUG] node ~A is static for awakeNodeFlowConservationOnMaxOutMinIn // n.label,
     set<integer>()) ] // et ben, je ferai pas ça tous les jours!!! et c'est pas fini...

// **********************************************************************
// *   Part 6: initialisation of the graph for the preflow              *
// **********************************************************************

// build flow node
[makeFlowNode(nodeLabel:integer) : FlowNode
-> //[FLOWDEBUG] building node ~A // nodeLabel,
   FlowNode(label = nodeLabel)]

// build the residual graph edge
// at the beginning, flow in each arc x_ij is null 
// so residual graph is equal to the capacity graph
// v0.63 c:FlowConstraint is no more a parameter
[makeFlowResEdge(nodei:FlowNode, nodej:FlowNode, edgeCost:(IntVar U integer)) : FlowResEdge
-> //[FLOWDEBUG] building edge ~A->~A with cost=~S in world ~A // nodei.label, nodej.label, edgeCost, world?(),
   let varu_ij := 0,
       varl_ij := 0 in
   ( case edgeCost (IntVar (varu_ij := choco/getSup(edgeCost), 
                            varl_ij := choco/getInf(edgeCost)),
                    integer varu_ij := edgeCost),
     // v0.62 as r_ij, r_ji, u_ij and l_ij are stored, initialized first with default value
     // then assign to relevant value 
     // (when backtracking in a world were the edge was not defined, attributes are equal to initialisation values) 
     let newEdge := FlowResEdge(i = nodei, j = nodej, r_ij = makeStoreForFlow(0), r_ji = makeStoreForFlow(0), lu_ij = edgeCost) in
     ;let newEdge := FlowResEdge(i = nodei, j = nodej, lu_ij = edgeCost) in
     ( newEdge.r_ij := (varu_ij - varl_ij),         // r_ij = (u_ij - l_ij) - x_ij + x_ji, as (i,j) is in the graph (j,i) is not 
                                                    // so u_ji=l_ji=0 finally r_ij = u_ij - l_ij
       newEdge.l_ij := varl_ij,                     // r_ji = u_ji - l_ji - x_ji + x_ij = 0 
       newEdge.u_ij := varu_ij,
       newEdge.lu_ij := edgeCost,
       newEdge))]
               
// v0.62.01 special initialization for edge link to dummy sink or dummy source                
// v0.63 c:FlowConstraint is no more a parameter
[makeFlowDummyResEdge(nodei:FlowNode, nodej:FlowNode, edgeCost:(IntVar U integer)) : FlowResEdge
-> //[FLOWDEBUG] building dummy edge ~A->~A with cost=~S in world ~A // nodei.label, nodej.label, edgeCost, world?(),
   let returnObj := makeFlowResEdge(nodei,nodej,edgeCost) in
   ( returnObj.lu_ij := 0,
     returnObj)]

// reinitialize residual graph egde (same value as in makeFlowResEdge)
[initFlowResEdge(edge:FlowResEdge) : void
-> initFlowResEdge(edge, edge.lu_ij)]

[initFlowResEdge(edge:FlowResEdge, edgeCost:(IntVar U integer)) : void
-> //[FLOWDEBUG] before init ~S (r_ij=~A,r_ji=~A) with edgeCost ~S in world ~A // edge,Core/Address(get(r_ij,edge)),Core/Address(get(r_ji,edge)),edgeCost, world?(),
   if (edgeCost % integer) 
   ( edge.u_ij := edgeCost) // !!! ugly assignment, usefull for dummy edge
   else
   ( // so it is IntVar
     edge.u_ij := choco/getSup(edgeCost),
     edge.l_ij := choco/getInf(edgeCost)),
   edge.r_ij := (edge.u_ij - edge.l_ij),
   edge.r_ji := 0,
   //[FLOWDEBUG] init edge ~S (r_ij=~A,r_ji=~A) with edgeCost ~S in world ~A // edge,Core/Address(get(r_ij,edge)),Core/Address(get(r_ji,edge)),edgeCost, world?()
]

// put all distLabel to MAXINT
[reInitDistLabel(g:FlowNode[]) : void
-> for node in g
     reInitDistLabel(node)]


// give a non backtrakable default value for StoredInt
[makeStoreForFlow(defaultVal:integer) : StoredInt
-> let returnObj := StoredInt() in
   ( put_store(latestValue,returnObj,defaultVal,false),
     returnObj)]


// **********************************************************************
// *   Part 7: print and debug                                          *
// **********************************************************************

// printing level per level (using distance label)
[self_print(x:FlowConstraint)
-> printf("N:~SC:~S",x.flowGraph,x.flowVar)]


;[self_print(x:FlowNode)
;-> printf("\n~A(d:~A,e:~A,b:~A)\n[~S]\n[~S]",x.label,x.distLabel,x.excess,x.b,x.nextEdge,x.prevEdge)]
[self_print(x:FlowNode)
-> printf("~A(d:~A,e:~A,b:~A)",x.label,x.distLabel,x.excess,x.b)]


[self_print(x:any[]) : void
;[self_print(x:FlowNode[]) : void
-> printf("["),
   if (length(x) > 0)
   ( for i in (1 .. length(x) - 1) printf("~S, ",x[i]),
     printf("~S",x[length(x)])),
   printf("]")] 

[self_print(x:FlowResEdge)
-> printf("~A->~A(r_ij:~A,r_ji:~A,lu_ij:~S)",x.i.label,x.j.label,x.r_ij,x.r_ji,x.lu_ij)]

;[sum(l:list[integer]) : integer
;-> let val := 0 in
;   ( for i in l
;       val :+ i,
;     val)]
;     
;[sum(l:bag) : integer
;-> let val := 0 in
;   ( for i in l
;       val :+ i,
;     val)]


