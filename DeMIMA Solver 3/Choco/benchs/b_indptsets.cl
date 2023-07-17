// Studying independent sets in hyper-graphs
//   a study from Pat Prosser, with a few hints from François Laburthe

// tracing (verbosity level) the maximal independent set module
MISTALK:integer := 2

// ------------------------------------------------------------------
// Part 1: data structures for storing the objects that are studied
// ------------------------------------------------------------------

// hypergraphs are represented as unnamed objects
// two forward declarations
Edge <: object
Vertex <: object
// a hyper-graph is a graph with hyper-edges
Graph <: object(
   nbVertices:integer = 0,
   vertices:list<Vertex>,
   nbEdges:integer = 0,
   edges:list<Edge>)
[self_print(g:Graph) : void -> printf("g(~S,~S)",g.nbVertices,g.nbEdges)]

Vertex <: object(
   index:integer,
   edges:list<Edge>)
[self_print(v:Vertex) : void -> printf("v~S",v.index)]

Edge <: object(
   vertices:list<Vertex>,
   arity:integer)
[self_print(e:Edge) : void -> printf("e~S",e.vertices)]

// two I/O utilities: reading an integer or a list of integers from a port
[readInt(p:port) : integer
 -> let x := read(p) in
      (case x (integer x,
               any     error("did not read an integer from the port, but ~S",x)))]

[readIntList(p:port) : list<integer>
 -> let l := list<integer>(),
        n := readInt(p) in
      (for i in (1 .. n)
           l :add readInt(p),
       l)]

// reading a graph from a file
[readGraph(fname:string) : Graph
 -> let p := fopen(fname,"r"),
        g := Graph() in
      (try   g.nbVertices := readInt(p)
       catch any error("the file should start with the number of vertices in the graph"),
       g.vertices := list<Vertex>{Vertex(index = i) | i in (1 .. g.nbVertices)},
       let stop := false in
         (until stop
            let l := readIntList(p) in
               (if (length(l) = 0) stop := true
                else if exists(i in l | ((i < 1) | (i > g.nbVertices)))
                       error("the edge ~S references unvalid vertices (~S vertices in the graph)",l,g.nbVertices)
                else let e := Edge(arity = length(l),
                                   vertices = list<Vertex>{g.vertices[i] | i in l}) in
                        (g.nbEdges :+ 1,
                         g.edges :add e,
                         for v in e.vertices
                             v.edges :add e))),
       fclose(p),
       //[MISTALK] read graph ~S // g,
       g)]

// ------------------------------------------------------------------
// Part 2: stating the problem of finding an independent set in a graph
// ------------------------------------------------------------------

// a data structure collecting the data and the CSP model
IndSetProblem <: object(
      graph:Graph,
      csp:choco/Problem,
      objective:choco/IntVar,
      expetedOptimum:integer = -1,
      binaryVars:list<choco/IntVar>)
[self_print(ispb:IndSetProblem) : void -> printf("indset(~S)",ispb.graph)]

// utility: summing the binary variables for a subset of vertices
// 1. with a sum of 0/1 vars
[sumVerts(ispb:IndSetProblem, vertices:list[Vertex]) : choco/Term
 -> choco/sumVars(list<choco/IntVar>{ispb.binaryVars[v.index] | v in vertices})]
// 2. with an occurrence count of 1 among 0/1 vars
[occurVerts(ispb:IndSetProblem, vertices:list[Vertex]) : choco/Term
 -> choco/occur(1,list<choco/IntVar>{ispb.binaryVars[v.index] | v in vertices})]

// Modeling the search for independent sets (may be non maximal)
// There are twol ways of modeling the problem the objective of the problem
//     (depending on the value of the second parameter)
//   model=1: an count of occurrences of value 1 among all binary variables
//   model=2: a sum of binary variables
//
[createIndSetProblem(g:Graph, model:integer) : IndSetProblem
 -> let ispb := IndSetProblem(),
        pb := choco/makeProblem("max indpt set pb",g.nbVertices + 1) in
     (ispb.csp := pb,
      ispb.graph := g,
      ispb.objective := choco/makeIntVar(pb,"idsetcard",0,g.nbVertices),
      ispb.binaryVars := list<choco/IntVar>{choco/makeIntVar(pb,"v" /+ string!(i),0,1) | i in (1 .. g.nbVertices)},

      let pe := choco/getPropagationEngine(pb) in
        (// with model=2, the solver delays all linear constraints
         if (model = 2)
            choco/setDelayedLinearConstraintPropagation(pe,true)
         // with model=3, the solver delays no linear constraints
         else if (model = 3)
            choco/setDelayedLinearConstraintPropagation(pe,false)),

      // posting the independence constraint for each edge
      for e in g.edges
         (if (model = 1)
             choco/post(pb, occurVerts(ispb,e.vertices) <= e.arity - 1)
          else if ((model = 2) | (model = 3))
             choco/post(pb, sumVerts(ispb,e.vertices) <= e.arity - 1)
         ),
      // defining the objective function
      if (model = 1)
         choco/post(pb,occurVerts(ispb,ispb.graph.vertices) == ispb.objective)
      else if ((model = 2) | (model = 3))
         choco/post(pb,sumVerts(ispb,ispb.graph.vertices) == ispb.objective),

      // return the main object
      //[MISTALK] defined problem ~S // ispb,
      ispb)]
        
// Modeling the search for maximal independent sets (may be non maximal)
[createMaxIndSetProblem(g:Graph, model:integer) : IndSetProblem
 -> let ispb := IndSetProblem(),
        pb := choco/makeProblem("max indpt set pb",g.nbVertices + 1) in
     (ispb.csp := pb,
      ispb.graph := g,
      ispb.objective := choco/makeIntVar(pb,0,g.nbVertices),
      ispb.binaryVars := list<choco/IntVar>{choco/makeIntVar(pb,0,1) | i in (1 .. g.nbVertices)},

      for vindex in (1 .. g.nbVertices)
         let v := g.vertices[vindex],
             binv := ispb.binaryVars[vindex] in
          (choco/post(pb, choco/implies(binv == 1,
                                   and(list{(sumVerts(ispb,(e.vertices but v)) < e.arity - 1) |
                                              e in v.edges}))),

           choco/post(pb, choco/implies(binv == 0,
                                   and(list{(sumVerts(ispb,(e.vertices but v)) == e.arity - 1) |
                                              e in v.edges}))),

           // defining the objective function
           choco/post(pb,choco/occur(1,ispb.binaryVars) == ispb.objective) ),

      // return the main object
      //[MISTALK] defined problem ~S // ispb,
      ispb)]

;[solveIndSetProblem(ispb:IndSetProblem, restart:boolean)
; -> choco/maximize(ispb.csp, ispb.objective, restart)]

[solveIndSetProblem(ispb:IndSetProblem)
 -> let searchStrategy := choco/makeDefaultBranchingList(ispb.csp),
        algo := choco/makeGlobalSearchMaximizer(ispb.objective, false,searchStrategy) in
     (choco/attach(algo,ispb.csp),
      choco/run(algo),
      
      let bestval := choco/getBestObjectiveValue(algo) in
         (if ((ispb.expetedOptimum > 0) & (ispb.expetedOptimum != bestval))
             error("found wrong optimum value ~S instead of ~S",bestval,ispb.expetedOptimum))
     )]

// ------------------------------------------------------------------
// Part 3: tests and experiments
// ------------------------------------------------------------------

PATDATADIR:string := ("." / "benchs" / "Data" / "IndptSets")

// generic parametrized search
[indptSets(fname:string, model:integer) : void
 -> trace(CHOCOBENCH_VIEW,"indpt sets (~A)-~A: ",
         stringFormat(fname,6),
         (if (model = 1) "occur" 
          else if (model = 2) "delay" 
          else "sum  ")),
    time_set(),
    let g := readGraph(PATDATADIR / fname /+ ".txt"),
        ispb := createIndSetProblem(g,model) in
     (if (fname = "gtest") ispb.expetedOptimum := 9
      else if (fname = "g25-01") ispb.expetedOptimum := 14
      else if (fname = "g25-02") ispb.expetedOptimum := 15,
      solveIndSetProblem(ispb),
      let t := time_get() in
          trace(CHOCOBENCH_VIEW,"~S ms.\n",t),
      choco/discardProblem(ispb.csp))]

[maxIndptSets(fname:string, model:integer) : void
 -> trace(CHOCOBENCH_VIEW,"max indpt sets (~A)-~A: ",
         stringFormat(fname,5),
         (if (model = 1) "occur" 
          else if (model = 2) "delay" 
          else "sum")),
    time_set(),
    let g := readGraph(PATDATADIR / fname /+ ".txt"),
        ispb := createMaxIndSetProblem(g,model) in
     (solveIndSetProblem(ispb),
      let t := time_get() in
          trace(CHOCOBENCH_VIEW,"~S ms.\n",t),
      choco/discardProblem(ispb.csp))]
        
// ----------------------------------------------------------------
// Summary of experiments (run times on the 300MHz laptop,
//                         choco v1.3.12, claire 3.2.18 -s 4 4)
// ----------------------------------------------------------------

;INSTANCE             gtest    g25-01   g25-02
;#vert, |edge|        13,4     25,4     25,4
;optimum              9        14       15
;-----------------------------------------------------------------
;MODEL       SEARCH                               TOPLEVEL COMMAND
;-----------------------------------------------------------------
;occur       B&B      97nd     51445nd  24172nd   go(fname,1,false,false)
;                     20ms     12878ms  6199ms
;
;            restart  181nd    51802nd  24439nd   go(fname,1,true,false)
;                     30ms     12699ms  6149ms
;
;sum         B&B      97nd     51445nd  24172nd   go(fname,2,false,false)
;                     90ms     92383ms  45035ms
;
;            restart  181nd    51802nd  24439nd   go(fname,2,true,false)
;                     110ms    92523ms  45025ms
;
;delay(sum,0)B&B      97nd     51445nd  24172nd   go(fname,3,false,false)
;                     20ms     13970ms  6520ms
;
;            restart  181nd    51802nd  24439nd   go(fname,3,true,false)
;                     40ms     13799ms  6419ms
;
;WITH CHOCO 1.313
;
;sum         B&B      97nd     51445nd  24172nd   go(fname,2,false,false)
;                     90ms     92383ms  45004ms (interface(getNextActiveEventQ)
;                     90ms     90900ms  44885ms (nbPendingIntVarEvent)
;                                       44400   (full use of nbPendintIntVarEvent)
;                                       42800   (lifo instead of random access for constawakeEvt queues)

// Conclusions:
//   1. surprisingly, occur is a bit faster than non delayed sums (by around 8%)
//   2. on this benchmark, it is an awful idea to delay linear constraints that involve so few variables !!!!!
//        the penalty is around a factor 7 in overall run-time !
//   3. we don't know yet how to use the fast dispatch feature of Claire 3
//   4. the factor 7 is hard to diminish w/o the fast dispatch
