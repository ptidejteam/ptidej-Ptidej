// ********************************************************************
// * ICEBERG: global constraints for OCRE v1.0.1                      * 
// *        requires CHOCO, v1.3.18                                   *
// * file: dyn.cl                                                     *
// *    dynamic programming                                           *
// * Author: Thierry Benoist
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 1: Data model                                            *
// *   Part 2: creation                                               *
// *   Part 3: accessors                                              *
// *   Part 4: invariants                                             *
// *   Part 5: Internal propagations                                  *
// *   Part 6: propagations                                           *
// *   Part 7: Interface to the Choco framework                       *
// --------------------------------------------------------------------

ICE_RELEASE :: 1.01

[showIceLicense()
 -> printf("Iceberg version ~A, Copyright (C) 2001 Bouygues e-lab\n",ICE_RELEASE),
    printf("for details read licence.txt\n") ]
(showIceLicense())


//verbosity levels
claire/DPVIEW:integer := 3
claire/DPTALK:integer := 4
claire/DPDEBUG:integer := 5


// ********************************************************************
// *   Part 1: data model                                         *
// ********************************************************************

//forward
DynEdge <: ephemeral_object
DynNode <: ephemeral_object
Layer <: ephemeral_object
DynProg <: LargeIntConstraint


//-- Each node represent a state at a certain date
DynNode <: ephemeral_object(state:integer, //-- state label
                         layer:Layer,   //-- the layer this node belongs to
                         rightEdges:list[DynEdge], //-- edges from this node to the right layer
                         leftEdges:list[DynEdge],  //-- edges from this node to the left layer
                         maxToRight:integer = MININT, //-- max path to the rightmost node (final)
                         maxToLeft:integer = MININT,  //-- max path to the leftmost node (initial)
                         minToRight:integer = MAXINT, //-- min path to the rightmost node (final)
                         minToLeft:integer = MAXINT,  //-- min path to the leftmost node (initial)
                         //flags
                         ntuToRight?:boolean = true, //-- true iff the path lengths to the rightmost node may have changed
                         ntuToLeft?:boolean = true)  //-- true iff the path lengths to the leftmost node may have changed 
                          

//-- An edge is a transition from a node to another node,
//-- characterized by a cost and a command.
//-- The source node is not precised since edges are slots of DynNode (rightEdges and leftEdges slots)
DynEdge <: ephemeral_object(toNode:DynNode,
                         cost:integer,
                         command:integer) 


//-- There is a layer for each date containing the list of nodes associated to possible states at this date                
Layer <: ephemeral_object(date:integer,    //-- the date of the layer
                          dynprog:DynProg, //-- the parent DynProg object 
                          nodes:list[DynNode], //-- the list of nodes, sorted with increasing state labels
                          rightLayer:Layer, //-- the right layer
                          leftLayer:Layer,  //-- the left layer
                          stateVar:IntVar,   //-- the IntVar associated to the state at this date
                          commandVar:IntVar, //-- the command associated to transition from the left layer (previous) to this one.
                          maxCommand:integer = MININT, //-- the max possible command for transition from the left layer (previous) to this one.
                          minCommand:integer = MAXINT, //-- the max possible command for transition from the left layer (previous) to this one.                         

                          minFeasIdx:integer = 1, //-- idx of the first possible node
                          maxFeasIdx:integer, //-- idx of the last possible node
                          
                          //flags
                          ntuToRight?:boolean = true, //-- true iff the path lengths to the rightmost node may have changed
                          ntuToLeft?:boolean = true)  //-- true iff the path lengths to the leftmost node may have changed
                            

//-- Type of the return of the transitions function
//--   (tuple(command,cost) or unknown is the transition is not possible)                          
Transition :: (tuple(integer,integer) U {unknown})


//-- Global constraint associated to a dynamic program: 
//-- It links a list of commands, a list of states and an objective variable.
DynProg <: LargeIntConstraint(;needToUpdate:boolean = true, //-- true iff the constraint needs to be awoken
                            nbDates:integer,  //-- the number of dates in the dynamic program
                            ;nbVars:integer,   //-- the number of variables in the constraints (2*nbDates + 1)
                            totalCost:IntVar, //-- the total cost of the dynamic program
                            //-- transition function
                            transition:method[domain = list(integer,integer,integer),  //-- arguments: state1,state2,t
                                              range = Transition], //-- Returns the cost and command associated to the transition  
                                                                   //-- from state1 at date t to state2 at date t+1
                                                                   //-- (unknown if this transition is impossible)
                            initialLayer:Layer, //-- the initial layer for date 0 (only contains the fictive initial node)
                            layers:Layer[],     //-- the nbDates layers 
                            finalLayer:Layer,   //-- the final layer for date nbDates+1 (only contains the fictive final node)
                            
                            //flags
                            maxNtuToRight:integer, //-- idx of the rightmost layer whose path lengths to the final node might have changed 
                            minNtuToLeft:integer)  //-- idx of the leftmost layer whose path lengths to the initial node might have changed 

//storage
(store(maxNtuToRight,minNtuToLeft,ntuToRight?,ntuToLeft?,minFeasIdx,maxFeasIdx))

//display
[self_print(n:DynNode) -> printf("~S@~S",n.state,n.layer.date)]
[self_print(e:DynEdge) -> printf("to~S:$~S/#~S",e.toNode,e.cost,e.command)]
[self_print(l:Layer) -> printf("Layer~S",l.date)]

                          
// ********************************************************************
// *   Part 2: creation                                               *
// ********************************************************************


// ----- Constructor:

//-- Creates a DynProg constraint for the dynamic program defined by:
//--      * n state variables
//--      * n command variables
//--      * 1 cost variables
//--      * and a transition function
//@api
[choco/makeDynamicProgram(states:list[IntVar],commands:list[IntVar],totalC:choco/IntVar,
                           tr:method) : DynProg 
;                          tr:method[domain = list(integer,integer,integer),  //claire2.5.60 is unable to detect the signature of the method !
;                                    range = Transition]) : DynProg
-> assert(length(states) = length(commands)),
   let res := DynProg(nbDates = length(states),
                      nbVars = length(states) + length(commands) + 1,
                      totalCost = totalC,
                      transition = tr,
                      initialLayer = Layer(date = 0, 
                                           stateVar = choco/makeConstantIntVar(0),
                                           maxFeasIdx = 1,
                                           ntuToLeft? = false), 
                      layers = array!(list<Layer>{Layer(date = i, stateVar = states[i],commandVar = commands[i]) | i in (1 .. length(states))}),    
                      finalLayer = Layer(date = length(states) + 1,
                                         stateVar = choco/makeConstantIntVar(0),
                                         maxFeasIdx = 1,
                                         ntuToRight? = false),
                      maxNtuToRight = length(states),
                      minNtuToLeft = 1) in  
    (//create the initial and final nodes
     res.initialLayer.nodes := list(DynNode(state = 0,layer = res.initialLayer,
                                         maxToLeft = 0, minToLeft = 0, ntuToLeft? = false)),
     res.finalLayer.nodes := list(DynNode(state = 0,layer = res.finalLayer,
                                       maxToRight = 0, minToRight = 0, ntuToRight? = false)),
     
     //fill the rightLayer and leftLayer slots
     res.initialLayer.rightLayer := res.layers[1],     
     res.finalLayer.leftLayer := res.layers[length(res.layers)],
     for i in (1 .. res.nbDates)
         (res.layers[i].leftLayer := getLayer(res,i - 1),
          res.layers[i].rightLayer := getLayer(res,i + 1)),
     
     //create nodes and edges
     //[DPDEBUG] making connections...,
     makeConnections(res),
     
     //initiallize the maxFeasIdx and dynprog slots
     for l in res.layers 
         (l.maxFeasIdx := length(l.nodes),
          l.dynprog := res),              
     res.initialLayer.dynprog := res,              
     res.finalLayer.dynprog := res,              
     
     //returns the DynProg constraint
    assert(not(res.initialLayer.ntuToLeft?)),
    assert(not(res.finalLayer.ntuToRight?)),
    ;assert(not(getLayer(res,res.nbDates).ntuToRight?)),  
    //[DPDEBUG] constraint created,
    res)]

[getPriority(c:DynProg) : integer -> 4]

// ----- Graph construction:

//-- Accessor to the transition function:
//--   Returns the tuple (cost,command) associated to transition from X[t]=x to X[t+1] = y if this transition is possible
//--   Returns unknown otherwise
[getCostAndCommand(c:DynProg,x:integer,y:integer,t:integer) : (tuple(integer,integer) U {unknown})
=> funcall(c.transition,x,y,t) as (tuple(integer,integer) U {unknown})]


//-- From left to right, create nodes and connect them, and finally sort the nodes list by increasing state labels.
//-- Only nodes that can be reached from the initial state are created
//-- and nodes that cannot lead to final state are removed as soon as discovered,
//-- finally from each created node there exists a path from initial state and a path to final state.
//-- (note that this filtering does only takes in account the feasibility of transitions and not their costs, 
//--  ie the upperBound an lowerbound on the total cost are not taken into account)                                 
[makeConnections(c:DynProg)
-> connectToRight(c.initialLayer.nodes[1],c),
   for l in c.layers
       (//[DPDEBUG] Connecting layer ~S of ~S // l.date,c.nbDates,
        for n in l.nodes 
           connectToRight(n,c)),
   for l in c.layers //sorted lists are easier to manipulate
       sort(<= @ DynNode,l.nodes)]    


//-- Create a node and add it to the specified layer 
[makeNode(s:integer,l:Layer) : DynNode
-> let res := DynNode(state = s, layer = l) in 
    (l.nodes :add res, res)]
     
//-- Create an edge from leftNode to rightNode 
//--  (in fact creates 2 oriented edges and add them in the appropriated rightEdges and leftEdges lists)     
[makeEdge(leftNode:DynNode,rightNode:DynNode,cst:integer,cmd:integer) : void
-> leftNode.rightEdges :add DynEdge(toNode = rightNode, cost = cst, command = cmd),
   rightNode.leftEdges :add DynEdge(toNode = leftNode, cost = cst, command = cmd)]
 
//-- Remove node n from data structures (it can recusively remove some other nodes)   
[removeNode(n:DynNode)
-> n.layer.nodes :delete n,
   for e in n.leftEdges
       (e.toNode.rightEdges :delete some(ed in e.toNode.rightEdges | ed.toNode = n),
        if not(e.toNode.rightEdges) removeNode(e.toNode))]
       
//-- Connect a node to its right layer (the leftEdges lists are build simultaneously via makeEdge)  
[connectToRight(n:DynNode,dp:DynProg)
-> let x := n.state,rl := n.layer.rightLayer, t := n.layer.date in 
     for y in  domain(rl.stateVar)
        when tr := getCostAndCommand(dp,x,y,t) in //returns a tuple(integer,integer)
            let cst := tr[1], cmd := tr[2] in
               (if possibleCommand?(rl,cmd) 
                    let toN := (when node := some(node in rl.nodes | node.state = y) in node
                                else makeNode(y,rl)) in 
                       makeEdge(n,toN,cst,cmd)),
   //if the node is an impossible one: remove it
   if not(n.rightEdges) removeNode(n)]

//-- Comparator to sort the nodes lists of each layer.
[<=(n1:DynNode,n2:DynNode) : boolean -> n1.state <= n2.state]


// ********************************************************************
// *   Part 3: Propagation utils                                      *
// ********************************************************************

// RULES:
//
//  Layer[0].nodes[1].minToRight <= totalCost <= Layer[0].nodes[1].maxToRight
//  Layer[nbDates+1].nodes[1].minToLeft <= totalCost <= Layer[nbDates+1].nodes[1].maxToLeft //equivalent to the previous one
//
//  x % domain(state[t]) <=> known?(maxToRight,n(x,t)) &  //n(x,t) denoting getNode(x,t)
//                           known?(minToRight,n(x,t)) &
//                           known?(maxToLeft,n(x,t)) &
//                           known?(minToLeft,n(x,t)) &
//                           n(x,t).maxToRight + n(x,t).maxToLeft >= totalCost.inf &
//                           n(x,t).minToRight + n(x,t).minToLeft <= totalCost.sup) 
//                           
//  c % domain(cost[t]) <=> exists(n in layers[t].nodes | exists(edge in possibleLeftEdges(n) | edge.cost = c))
//
//  u % domain(command[t]) <=> exists(n in layers[t].nodes | exists(edge in possibleLeftEdges(n) | edge.command = u))
//


// ------ mere accessors

//-- Returns layer number n (if 0 returns initial layer, and if nbDates+1 returns final layer)
[getLayer(c:DynProg,n:integer) : Layer
=> if (n = 0) c.initialLayer
   else if (n = c.nbDates + 1) c.finalLayer
   else c.layers[n]] 

//-- Returns the list of layers from date=t downto 0
[layersBefore(c:DynProg,t:integer) : list[Layer]
=> list{getLayer(c,t - x) | x in (0 .. t)}]

//-- Returns the list of layers from date=t downto 0
[layersAfter(c:DynProg,t:integer) : list[Layer]
=> list{getLayer(c,x) | x in (t .. c.nbDates + 1)}]


// ------ filters

//-- Returns the length of the longest path from initial node to final node, using this node
[getMaxCost(n:DynNode) : integer =>  n.maxToRight + n.maxToLeft]
//-- Returns the length of the shortest path from initial node to final node, using this node
[getMinCost(n:DynNode) : integer =>  n.minToRight + n.minToLeft]

//-- Returns the length of the longest path from initial node to final node, using edge (leftNode->rightNode at cost)  
[getMaxCost(leftNode:DynNode,rightNode:DynNode,cost:integer) : integer =>  leftNode.maxToLeft + cost + rightNode.maxToRight]
//-- Returns the length of the shortest path from initial node to final node, using edge (leftNode->rightNode at cost) 
[getMinCost(leftNode:DynNode,rightNode:DynNode,cost:integer) : integer =>  leftNode.minToLeft + cost + rightNode.minToRight]


//-- Returns true iff the cost of the node is compatible with the bounds of the objective var
[costOk?(n:DynNode) : boolean
-> let dp := n.layer.dynprog in 
    (getMaxCost(n) >= dp.totalCost.inf &
     getMinCost(n) <= dp.totalCost.sup) ]

//-- Returns true iff the cost of the edge from-to is compatible with the bounds of the objective var
[costOk?(from:DynNode,to:DynNode,cost:integer) : boolean
-> let dp := from.layer.dynprog in 
    (getMaxCost(from,to,cost) >= dp.totalCost.inf &
     getMinCost(from,to,cost) <= dp.totalCost.sup) ]


[discarded?(n:DynNode) : boolean
=> not(n.ntuToLeft?) & not(n.ntuToRight?) & 
   n.minToRight = MAXINT & n.minToLeft = MAXINT & n.maxToRight = MININT & n.maxToLeft = MININT]  
 
//-- Returns true iff the node is possible (ie the corresponding state belongs to domain(stateVar))
//-- Caution: does not test the cost of the node !
[possibleNode?(n:DynNode) : boolean
=> n.layer.stateVar choco/canBeInstantiatedTo n.state]
   
//-- Returns true iff the specified command is possible for transition from l.date-1 to l.date
[possibleCommand?(l:Layer,cmd:integer) : boolean
=>  (unknown?(commandVar,l) | l.commandVar choco/canBeInstantiatedTo cmd)]  //returns true if unknown so that intial & final states are reachable      


//-- Returns the list of possible right edges for node n 
[possibleRightEdges(n:DynNode) : list[DynEdge]
=> list{edge in n.rightEdges | possibleNode?(edge.toNode) & possibleCommand?(n.layer.rightLayer,edge.command)}]

//-- Returns the list of possible left edges for node n                         
[possibleLeftEdges(n:DynNode) : list[DynEdge]
=> list{edge in n.leftEdges | possibleNode?(edge.toNode) & possibleCommand?(n.layer,edge.command) }]

                        
//-- Returns the list of possible (wrt the state variable) nodes for layer l
[possibleNodes(l:Layer) : list[DynNode]
=> list{n in list{l.nodes[i] | i in (l.minFeasIdx .. l.maxFeasIdx)} | possibleNode?(n)}] 


// ----- Marking methods to maintain the max command seen in one pass:

//-- Initialize slots minCommand and maxCommand
[prepareMarkers(l:Layer) : void
-> //[DPDEBUG] Prepare markers for layer ~S // l,
   l.minCommand := MAXINT, 
   l.maxCommand := MININT] 

//-- Notify that the specified command is possible for transition to layer l.   
[markPossibleCommand(l:Layer,cmd:integer)  : void
=> //[DPDEBUG] Command ~S is possible for  ~S (commandVar ~S) // cmd,l,get(commandVar,l),
   l.minCommand :min cmd,
   l.maxCommand :max cmd]


//-- Update minCommand and maxCommand 
//--  (only useful to cross inter commands gaps)
[updateMarkers(l:Layer)  : void
-> //[DPDEBUG] Update markers,
   prepareMarkers(l),
   try
     (for n in list{n in (possibleNodes(l) as list[DynNode]) | costOk?(n)}
        for e in list{e in possibleLeftEdges(n) | costOk?(e.toNode,n,e.cost)}
          (markPossibleCommand(n.layer,e.command),
           if not(ntuCommand?(l)) exception())) //stop if we detect that previous bounds of commandVar are still valid      
   catch exception nil] 



// ----- Update the min/max feasible indices of a layer

//-- Update the minFeasIdx
[updateMinFeasIdx(l:Layer)  : void
-> let dp := l.dynprog in 
    while (l.minFeasIdx <= l.maxFeasIdx &  
          not(possibleNode?(l.nodes[l.minFeasIdx]) & costOk?(l.nodes[l.minFeasIdx])))
        (discardNode(l.nodes[l.minFeasIdx]),
         l.minFeasIdx :+ 1)]
//-- Update the maxFeasIdx
[updateMaxFeasIdx(l:Layer) : void
-> let dp := l.dynprog in 
    while (l.maxFeasIdx >= l.minFeasIdx &  
          not(possibleNode?(l.nodes[l.maxFeasIdx]) & costOk?(l.nodes[l.maxFeasIdx])))
        (discardNode(l.nodes[l.maxFeasIdx]),  
         l.maxFeasIdx :- 1)]


               
// ********************************************************************
// *   Part 4: invariants                                             *
// ********************************************************************

// Computation of Bellman values

claire/NBR:integer := 0
// -------- Bellman values for nodes:
//-- Compute the Bellman values at node n
//-- initial is true iff it is the initial propagation
//-- Returns true iff the min or the max changed
[computeToRight(n:DynNode,initial:boolean) : boolean 
-> NBR :+ 1,
   assert(n.ntuToRight? | ntuCommand?(n.layer.rightLayer)), 
   let oldMax := n.maxToRight, oldMin := n.minToRight, changed? := true in 
      (//[DPDEBUG] \nComputing node ~S to right (st=~S)// n,n.layer.stateVar,
       n.maxToRight := MININT,n.minToRight := MAXINT, //reset to default values
       
       for edge in list{e in possibleRightEdges(n) | (initial | costOk?(n,e.toNode,e.cost))} //cost cannot be used in initial computation to right
           (//[DPDEBUG] right edge ~S is possible // edge,
            //note that the command of the edge is possible
            markPossibleCommand(n.layer.rightLayer,edge.command),
            
            //update Bellman values
            n.maxToRight :max edge.cost + edge.toNode.maxToRight,
            n.minToRight :min edge.cost + edge.toNode.minToRight,
            
            //stop if we detect that previous min and max are still valid and if the command is up to date
            if (n.maxToRight = oldMax & n.minToRight = oldMin) 
                (changed? := false, 
                 if not(ntuCommand?(n.layer.rightLayer)) break())), 
                
       //note that the update has been performed
       n.ntuToRight? := false,
        
       //[DPDEBUG] Computed node ~S to right -> min=~S max=~S // n,n.minToRight,n.maxToRight,

       //detect whether the node has become impossible
       if ( not(initial | costOk?(n)) | n.maxToRight = MININT ) 
           discardNodeAndState(n) 
       //at least if its bellman values changed, notify it:
       else if changed?
           changedToRight(n),
       changed?)]    
               
claire/NBL:integer := 0       
//-- Same function in the reverse direction:
[computeToLeft(n:DynNode,initial:boolean) : boolean
-> NBL :+ 1,
   assert(n.ntuToLeft? | ntuCommand?(n.layer)), 
   let oldMax := n.maxToLeft, oldMin := n.minToLeft, changed? := true  in 
      (//[DPDEBUG] \nComputing node ~S to left (st=~S)// n,n.layer.stateVar,
       n.maxToLeft := MININT, n.minToLeft := MAXINT, //reset to default values     
       for edge in list{e in possibleLeftEdges(n) |  costOk?(e.toNode,n,e.cost)}  //cost can be used even in initial computation to left 
           (//[DPDEBUG] left edge ~S is possible // edge,            
            //note that the command of the edge is possible
            markPossibleCommand(n.layer,edge.command),            
            
            //update Bellman values
            n.maxToLeft :max edge.cost + edge.toNode.maxToLeft,
            n.minToLeft :min edge.cost + edge.toNode.minToLeft,

            //stop if we detect that previous min and max are still valid            
            if (n.maxToLeft = oldMax & n.minToLeft = oldMin) 
                (changed? := false,
                 if not(ntuCommand?(n.layer)) break())), //if the command is not ok -> go on
       
       //note that the update has been performed
       n.ntuToLeft? := false,
       
       //[DPDEBUG] Computed node ~S to left -> min=~S max=~S // n,get(minToLeft,n),get(maxToLeft,n),
       
       //detect whether the node has become impossible
       if not(costOk?(n)) // even if it is the initial propagation, xxxToRight values have already been computed
           discardNodeAndState(n) 
       
       //at least if its bellman values changed, notify it:
       else if changed?
           changedToLeft(n),
       changed?)]    
               
       


// -------- Computation of Bellman values for layers:

//-- Compute Bellman right values for nodes of layer l
[computeToRight(l:Layer,initial:boolean) : void
-> assert(l.ntuToRight?),

   //if not initial: update the minFeas and maxFeas slots 
   //ie discard nodes that need to ? (but this should have been done when this slots have been updated 

   //[DPDEBUG] Going to recompute nodes of layer ~S to right // l, 
   for n in (possibleNodes(l) as list[DynNode])
       (if (ntuCommand?(l.rightLayer) | n.ntuToRight?) 
           (computeToRight(n,initial),
            assert(not(n.ntuToRight?)))),
   
   //update the command:
   updateCommand(l.rightLayer),
;   assert(not(ntuCommand?(l.rightLayer))),
    if ntuCommand?(l.rightLayer) error("command of ~S should have been updated",l.rightLayer),
              
   //note that the update has been performed:          
   for n in l.nodes
       (if n.ntuToRight? error("~S should not be ntuToRight?=true",n)),
   l.ntuToRight? := false]


//-- Same function in the reverse direction:
[computeToLeft(l:Layer,initial:boolean) : void
-> assert(l.ntuToLeft?),   
   //[DPDEBUG] Going to recompute nodes of layer ~S to left // l,
   for n in (possibleNodes(l) as list[DynNode])
       (if (ntuCommand?(l) | n.ntuToLeft?) 
           (computeToLeft(n,initial),
            assert(not(n.ntuToLeft?)))),
   
   //[DPDEBUG] update the command for layer ~S // l,
   updateCommand(l),
   assert(not(ntuCommand?(l))),
   
   //[DPDEBUG] command update done (~S) // l,   
   //note that the update has been performed:          
   assert(forall(n in l.nodes | not(n.ntuToLeft?))),    
   l.ntuToLeft? := false]
   


// -------- update of all Bellman values:

[bellmanComputation(c:DynProg,initial:boolean) : void
-> while needToUpdate?(c) oneBellmanComputation(c,initial)] //after the second one, initial should be false ?

//-- Updates all Bellman values that need to be recomputed 
[oneBellmanComputation(c:DynProg,initial:boolean) : void
-> assert(c.maxNtuToRight < 0 | getLayer(c,c.maxNtuToRight).ntuToRight?), 
   assert(c.minNtuToLeft >= c.nbDates + 1 | getLayer(c,c.minNtuToLeft).ntuToLeft?),   
      
   //recompute to the right layers that need to 
   //[DPDEBUG] Recomputing to right from ~S downto 0 // c.maxNtuToRight,
   //[DPDEBUG]   ntuToRight?= ~S, ~S, ~S // c.initialLayer.ntuToRight?, list{l.ntuToRight? | l in list!(c.layers)}, c.finalLayer.ntuToRight?,
   for l in layersBefore(c,c.maxNtuToRight) //from t:=maxNtuToRight downto 0
       (if (l.ntuToRight?) computeToRight(l,initial)),
   c.maxNtuToRight := -1, //note that the updates have been performed   
   
   //recompute to the left layers that need to  
   //[DPDEBUG] Recomputing to left from ~S to n+1 // c.minNtuToLeft,
   //[DPDEBUG]   ntuToLeft?= ~S, ~S, ~S // c.initialLayer.ntuToLeft?, list{l.ntuToLeft? | l in list!(c.layers)}, c.finalLayer.ntuToLeft?,
   for l in layersAfter(c,c.minNtuToLeft) //from t:=minNtuToLeft to c.nbDates+1
       (if (l.ntuToLeft?) computeToLeft(l,initial)),
   c.minNtuToLeft := c.nbDates + 2] //note that the updates have been performed 
      
   

// ********************************************************************
// *   Part 5: Internal propagations                                  *
// ********************************************************************

// propagation of the ntu flags ("Need To Update")


// ----- Node flags

//-- Notify a node that some its xxxToRight values have changed
//-- therefore xxxToRight values of some nodes of the left layer must be recomputed
[changedToRight(n:DynNode) : void
-> for e in possibleLeftEdges(n) 
        (e.toNode.ntuToRight? := true, 
         ntuToRight(e.toNode.layer))]    
            
//-- Notify a node that some its xxxToLeft values have changed
//-- therefore xxxToLeft values of some nodes of the right layer must be recomputed
[changedToLeft(n:DynNode) : void
-> for e in possibleRightEdges(n) 
        (e.toNode.ntuToLeft? := true, 
         ntuToLeft(e.toNode.layer))]       

[discardNode(n:DynNode) : void
->  if not(discarded?(n)) 
       (//[DPDEBUG] Discarding node ~S // n,
        //modify costs to set them to +-MAXINT
        n.minToRight := MAXINT,
        n.minToLeft := MAXINT,
        n.maxToRight := MININT,
        n.maxToLeft := MININT,     
        
        //after this the node is up to date:
        n.ntuToLeft? := false,
        n.ntuToRight? := false,
        
        // inform both sides
        changedToLeft(n),changedToRight(n))]
        


// ----- Layer flags

//-- Notify a layer that some its nodes need to be recomputed (xxxToRightValues)
//-- If allNodes? is true, then all nodes of the layer will be marked as ntuToRight?=true
[ntuToRight(l:Layer,allNodes?:boolean)  : void
-> let c := l.dynprog in 
      (l.ntuToRight? := true,
       prepareMarkers(l.rightLayer),
       c.maxNtuToRight :max l.date,
       if allNodes? 
           (for n in list{n in (possibleNodes(l) as list[DynNode]) | costOk?(n)} n.ntuToRight? := true,
            assert(forall(n in list{n in l.nodes | not(n % possibleNodes(l))} |
                            (n.minToRight = MAXINT & n.minToLeft = MAXINT &
                             n.maxToRight = MININT & n.maxToLeft = MININT) ))))]  //impossible nodes have already been updated
       
//-- Shortcut: 
[ntuToRight(l:Layer)  : void => ntuToRight(l,false)]


//-- Notify a layer that some its nodes need to be recomputed (xxxToLeftValues)
//-- If allNodes? is true, then all nodes of the layer will be marked as ntuToLeft?=true
[ntuToLeft(l:Layer,allNodes?:boolean) : void
-> let c := l.dynprog in 
      (l.ntuToLeft? := true,
       c.minNtuToLeft :min l.date,
       prepareMarkers(l),       
       if allNodes? 
           (for n in list{n in (possibleNodes(l) as list[DynNode]) | costOk?(n)} n.ntuToLeft? := true,
            assert(forall(n in list{n in l.nodes | not(n % possibleNodes(l))} |
                            (n.minToRight = MAXINT & n.minToLeft = MAXINT &
                             n.maxToRight = MININT & n.maxToLeft = MININT) ))))]  //impossible nodes have already been updated
       
//-- Shortcut:        
[ntuToLeft(l:Layer)  : void => ntuToLeft(l,false)]



// ----- Others:

//-- All layers need to be recomputed:
[ntuAll(c:DynProg)
-> for t in (0 .. c.nbDates - 1)
     ntuToRight(getLayer(c,t),true),
   for t in (1 .. c.nbDates + 1)
     ntuToLeft(getLayer(c,t),true),    
   assert(not(getLayer(c,c.nbDates).ntuToRight?)),  //why not ? 
   assert(not(c.initialLayer.ntuToLeft?))]


//-- Returns true iff the minCommand and maxCommand markers are different from commandVar bounds.
[ntuCommand?(l:Layer) : boolean
=> known?(commandVar,l) &  //intial and final state have no commandVar
   (l.minCommand > l.commandVar.inf | l.maxCommand < l.commandVar.sup)]
            

//-- Asserts that all needToUpdate flags are false:
[assertNoNeedToUpdate(c:DynProg)
//TODO: should also assert that maxNtuToRight=0 and minNtuToLeft=n+1
=> assert(forall(t in (0 .. c.nbDates + 1) |
              let l := getLayer(c,t) in 
                    (not(l.ntuToLeft?) & 
                     not(l.ntuToRight?) &
                     forall(n in l.nodes | (not(n.ntuToRight?) & not(n.ntuToLeft?))) &
                     not(ntuCommand?(l)))))]    
//same assertion in detailed format:
;=> assert(c.minNtuToLeft = c.nbDates + 1),
;   assert(c.maxNtuToRight = 0),
;   for t in (0 .. c.nbDates + 1) 
;              let l := getLayer(c,t) in 
;                    (assert(not(l.ntuToLeft?)), 
;                     assert(not(l.ntuToRight?),
;                     assert(forall(n in l.nodes | (not(n.ntuToRight?) & not(n.ntuToLeft?)))),
;                     assert(not(ntuCommand?(l)))))]    
 

// ********************************************************************
// *   Part 6: Propagations methods                                   *
// ********************************************************************

// ----- Discarding a node

[discardNodeAndState(n:DynNode) : void
->  discardNode(n),discardState(n)]

//-- Discarding state of node n
[discardState(n:DynNode) : void
->  //[DPDEBUG] Discarding state for ~S // n,
    removeStateVal(n.layer,n.state)] 


// ----- Updates to a state variable  & reaction to any event on a state variable
    
//-- Remove a value from a state var
[removeStateVal(l:Layer,x:integer) : void
->   if known?(bucket,l.stateVar)  removeVal(l.stateVar, x, l.dynprog.indices[l.date])
     else if (x = l.nodes[l.minFeasIdx].state)   updateInfState(l)         
     else if (x = l.nodes[l.maxFeasIdx].state)   updateSupState(l)]          


//-- Update the inf of a state var         
[updateInfState(l:Layer) : void 
-> updateMinFeasIdx(l),
   updateInf(l.stateVar,l.nodes[l.minFeasIdx].state,l.dynprog.indices[l.date])]     

//-- Update the sup of a state var         
[updateSupState(l:Layer) : void
-> updateMaxFeasIdx(l),
   updateSup(l.stateVar,l.nodes[l.maxFeasIdx].state,l.dynprog.indices[l.date])]     
               

//-- Initilize the domain of a state variable, according to the data structure:
//--  Since only useful nodes are created, impossible nodes will not be encountered, 
//--  and therefore will not be discarded ...
//--    -> we do it manually (propagate the basic bounds and holes on the stateVar)   
[initState(l:Layer) : void
->  //update bounds
    updateInf(l.stateVar,l.nodes[1].state,l.dynprog.indices[l.date]),
    updateSup(l.stateVar,l.nodes[length(l.nodes)].state,l.dynprog.indices[l.date]),
    //update holes (enumerated domains only)
    if known?(bucket,l.stateVar) let nodeIdx := l.minFeasIdx in 
      //NB:why not looping on nodes and making removeInterval when not consecutives ?
      for x in domain(l.stateVar)
          (while (x > l.nodes[nodeIdx].state) //we use the fact that nodes are sorted by increasing state
              nodeIdx :+ 1, 
           if (x < l.nodes[nodeIdx].state)    
               removeVal(l.stateVar,x,l.dynprog.indices[l.date]))]              
    
    
   
//-- When something happen on a state variable,
//--    * crosses inter-states gaps if the variable is a BoundIntVar
//--    * discard removed nodes
[awakeOnStateVar(c:DynProg,idx:integer) : void  
->  let l := getLayer(c,idx) in 
               (//crosses inter-states gaps (immediate propagation) 
                updateInfState(l),updateSupState(l),
                
                //discard internal removed nodes (side ones have been discarded by updateInfState)
                for j in (l.minFeasIdx + 1 .. l.maxFeasIdx - 1) 
                    (if not(possibleNode?(l.nodes[j])) discardNode(l.nodes[j])))]




// ----- Updates to a command variable  & reaction to any event on a command variable

//-- Update the command var of the layer FROM markers minCommand and maxCommand
[updateCommand(l:Layer) : void
-> if ntuCommand?(l) when cmdV := get(commandVar,l) in 
     (updateInf(cmdV,l.minCommand,l.dynprog.indices[l.date + l.dynprog.nbDates]),
      updateSup(cmdV,l.maxCommand,l.dynprog.indices[l.date + l.dynprog.nbDates]))]


//-- When something happen on a command variable,
//--    * crosses inter-commands gaps (since hole are not been propagated)
//--    * notify neighbour layers
[awakeOnCommandVar(c:DynProg,idx:integer)  : void   
->  let l := getLayer(c,idx - c.nbDates), oldMin := 1, oldMax := length(l.nodes) in  
       (//crosses inter-states gaps (immediate propagation) 
        updateMarkers(l),
        updateCommand(l), 
                                                           
        //notify layer and leftlayer that they need to be updated
        ntuToRight(l.leftLayer,true), //true means allNodes
        ntuToLeft(l,true))]

     
 
// ----- Update on the total cost

//-- Update the total cost variable
[updateTotalCost(dp:DynProg) : void
-> updateInf(dp.totalCost,dp.initialLayer.nodes[1].minToRight,0),
   updateSup(dp.totalCost,dp.initialLayer.nodes[1].maxToRight,0)]
   


// ----- Main propagation methods

//-- Propagation:
[bellmanPropagate(c:DynProg) : void
-> assert(not(c.layers[c.nbDates].ntuToRight?)), // after the initial propagation nothing can happen on the right of the last layer 
                                                 // (since the final state is just a fictive one (no command and no cost)!)
   bellmanComputation(c,false),
   updateTotalCost(c),
   assertNoNeedToUpdate(c)]   
     
//-- Reaction to any event of variable of index idx:
[awakeOnVar(c:DynProg,idx:integer)  : void 
-> //[DPDEBUG] Something happenned on idx ~S // idx,
   let n := c.nbDates in
      (if (idx <= n)  awakeOnStateVar(c,idx)               
       else if (idx <= 2 * n) awakeOnCommandVar(c,idx)
       else ntuAll(c), //it is the objective var  (this global awake could be detailed?)         
       //[DPDEBUG] -> c.maxNtuToRight = ~S, c.minNtuToLeft = ~S // c.maxNtuToRight,c.minNtuToLeft,
       assert(not(c.layers[c.nbDates].ntuToRight?))),
   if needToUpdate?(c) constAwake(c,false)]

[needToUpdate?(c:DynProg) : boolean => (c.maxNtuToRight >= 0 | c.minNtuToLeft <= c.nbDates + 1)]   

// ********************************************************************
// *   Part 7: Interface to the Choco framework                       *
// ********************************************************************

// ----- connection stuff                                                            

//-- Return the number of vars of the constraint
[choco/getNbVars(c:DynProg) : integer -> c.nbVars]

//-- Return variable with index i
[choco/getVar(c:DynProg, i:integer) : IntVar
-> let n := c.nbDates in
      (if (i = getNbVars(c)) c.totalCost
       else if (i <= n) c.layers[i].stateVar
       else if (i <= 2 * n ) c.layers[i - n].commandVar
       else error("invalid idx"))]
       
//-- Connect the constraint       
[choco/connect(c:DynProg) : void
 -> let n := c.nbDates in
      (c.indices := make_list(getNbVars(c),integer,0),
       connectVar(c,c.totalCost,getNbVars(c)),
       for i in (1 .. n) connectVar(c,c.layers[i].stateVar,i),
       for i in (1 .. n) connectVar(c,c.layers[i].commandVar,n + i))] //TODO. costs


// ----- Propagation methods


//-- Propagation of the constraint if needToUpdate
[propagate(c:DynProg) : void
-> assert(c.maxNtuToRight >= 0 | c.minNtuToLeft <= c.nbDates + 1),   
   bellmanPropagate(c),
   assert(c.maxNtuToRight = -1  &  c.minNtuToLeft = c.nbDates + 2)]

//-- Initial propagation of the constraint
[awake(c:DynProg) : void
-> for l in c.layers initState(l),//initialize state variables   
   ;ntuAll(c),
   bellmanComputation(c,true),// Compute Bellman values          
   updateTotalCost(c)]// Deduce the total cost 
   

//-- Reactions to events
[awakeOnInst(c:DynProg,idx:integer) : void -> awakeOnVar(c,idx)] 
[awakeOnInf(c:DynProg,idx:integer) : void  -> awakeOnVar(c,idx)]   
[awakeOnSup(c:DynProg,idx:integer) : void  -> awakeOnVar(c,idx)]
   


