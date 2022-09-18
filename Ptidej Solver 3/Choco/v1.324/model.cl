// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: model.cl                                                   *
// *    object model & basic architecture                             *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
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

CHOCO_RELEASE :: 13.024
[showChocoLicense() : void
 -> printf("Choco version ~A, Copyright (C) 1999-2002 F. Laburthe\n",CHOCO_RELEASE),
    printf("Choco comes with ABSOLUTELY NO WARRANTY; "),
    printf("for details read licence.txt\n"),
    printf("This is free software, and you are welcome to redistribute it\n"),
    printf("under certain conditions; read licence.txt for details.\n") ]
(showChocoLicense())

// ********************************************************************
// *   Part 1: Global parameters                                      *
// ********************************************************************

claire/EXTENDABLE_CHOCO:boolean := true

// VERBOSITY PARAMETERS: we use the ADHOC methodology
//
//    (a) The integer value of verbose() may be
//          0: application mode  = silent  (trace <=> printf)
//          1: execution mode    = silent but for structure comments
//          2: trace mode        = report the execution flow step by step
//          3: debug mode        = report everything !

//    (b) each fragment X of the program may
//          - either use the standard integer values (0,1,2)
//          - introduce the flags: XTALK, XSHOW, XDEBUG
//            The flags support a flexible control of the code fragments,
//            for which tracing can be independently turned on and off
//               XVIEW: (default 1)   execution
//               XTALK: (defalt 2)    trace
//               XDEBUG: (default 3)  debug

// fragment D: coding domains
claire/DDEBUG:integer := 6     // debugging buckets (implementing domains)

// fragment P: propagation
claire/PVIEW:integer := 4      // general propagation info (layered fix-points)
claire/PTALK:integer := 5      // tracing propagation (event queues)
claire/PDEBUG:integer := 6     // debugging propagation (domain updates)

// fragment GP: propagation of global constraints
claire/GPVIEW:integer := 2      // general propagation info (layered fix-points)
claire/GPTALK:integer := 2      // tracing propagation (event queues)
claire/GPDEBUG:integer := 4     // debugging propagation (domain updates)

// fragment S: global search
claire/SVIEW:integer := 1      // general search info (solutions found)
claire/STALK:integer := 2      // tracing decisions in the search tree
claire/SDEBUG:integer := 3     // debugging search tree construction (variable selection heuristics)

// fragment I: invariants (for non-monotonic moves)
claire/IVIEW:integer := 1      // general info about conflict counts
claire/ITALK:integer := 2      // tracing improvement of conflict counts
claire/IDEBUG:integer := 3     // debugging evaluations of invariant maintenance

// fragment L: moves & local search
claire/LVIEW:integer := 1      // general info about iterations (good solutions found)
claire/LTALK:integer := 5      // tracing assignments & flips
claire/LDEBUG:integer := 6     // debugging local optimization (selection heuristics)

// fragment CHOCOBENCH: benchmark suite
claire/CHOCOBENCH_VIEW:integer := -1
// fragment CHOCOTEST: regression testbed
claire/CHOCOTEST_VIEW:integer := -1

// ********************************************************************
// *   Part 3: The object hierarchy                                   *
// ********************************************************************

// root class for all Choco objects
choco/Ephemeral <: ephemeral_object()

// below, the whole hierarchy is reproduced
// (uncommented lines are mandatory forward declarations)
choco/Problem <: Ephemeral

choco/Solver <: Ephemeral
  choco/LocalSearchSolver <: Solver
  choco/GlobalSearchSolver <: Solver

choco/AbstractConstraint <: Ephemeral
  choco/IntConstraint <: AbstractConstraint
;    choco/UnIntConstraint <: IntConstraint
;    choco/BinIntConstraint <: IntConstraint
;    choco/TernIntConstraint <: IntConstraint
;    choco/LargeIntConstraint <: IntConstraint
;    choco/Delayer <: IntConstraint
;  choco/CompositeConstraint <: AbstractConstraint
;    choco/BinCompositeConstraint <: CompositeConstraint
;      choco/BinBoolConstraint <: BinCompositeConstraint
;        choco/BinBoolConstraintWCounterOpp <: BinBoolConstraint  // v1.02 
;    choco/LargeCompositeConstraint <: CompositeConstraint
;      choco/LargeBoolConstraint <: LargeCompositeConstraint
;        choco/LargeBoolConstraintWCounterOpp <: LargeBoolConstraint  // v1.02
;
choco/AbstractVar <: Ephemeral
  choco/IntVar <: AbstractVar
  choco/SetVar <: AbstractVar
;
;choco/AbstractDomain <: collection
;  choco/AbstractIntDomain <: AbstractDomain
;    choco/LinkedListIntDomain <: AbstractIntDomain
;  choco/AbstractSetDomain <: AbstractDomain
;    choco/BitSetDomain <: AbstractSetDomain
;    choco/BitListSetDomain <: AbstractSetDomain
;
choco/PropagationEvent <: Ephemeral
  choco/ConstAwakeEvent <: PropagationEvent  // v0.9906
  choco/VarEvent <: PropagationEvent
    choco/Instantiation <: VarEvent
      choco/InstInt <: Instantiation
      choco/InstSet <: Instantiation
    choco/ValueRemovals <: VarEvent
    choco/BoundUpdate <: VarEvent
      choco/IncInf <: BoundUpdate
      choco/DecSup <: BoundUpdate
      choco/IncKer <: BoundUpdate
      choco/DevEnv <: BoundUpdate
;
;choco/EventCollection <: Ephemeral
;  choco/InstantiationStack <: EventCollection
;  choco/EventQueue <: EventCollection
;    choco/BoundEventQueue <: EventQueue
;    choco/RemovalEventQueue <: EventQueue
;    choco/ConstAwakeEventQueue <: EventQueue  // v0.9906

choco/LogicEngine <: Ephemeral
  choco/PropagationEngine <: LogicEngine
;    choco/ChocEngine <: PropagationEngine
  choco/InvariantEngine <: LogicEngine
;    choco/ConflictCount <: InvariantEngine
;
;choco/Solver <: Ephemeral
;  choco/GlobalSearchSolver <: Solver
;    choco/Solve <: GlobalSearchSolver
;    choco/AbstractOptimize <: GlobalSearchSolver
;      choco/BranchAndBound <: AbstractOptimize
;      choco/OptimizeWithRestarts <: AbstractOptimize
;  choco/LocalSearchSolver <: Solver
;    choco/MultipleDescent <: LocalSearchSolver
;
choco/AbstractBranching <: Ephemeral
;  choco/BinBranching <: AbstractBranching
;    choco/SplitDomain <: BinBranching
;    choco/AssignOrForbid <: BinBranching
;    choco/SettleBinDisjunction <: BinBranching
;  choco/LargeBranching <: AbstractBranching
;    choco/CompositeBranching <: LargeBranching
;    choco/AssignVar <: LargeBranching
choco/GlobalSearchLimit <: Ephemeral
;
choco/ConstructiveHeuristic <: Ephemeral
;  choco/AssignmentHeuristic <: ConstructiveHeuristic
choco/MoveNeighborhood <: Ephemeral
;  choco/FlipNeighborhood <: MoveNeighborhood
;
;choco/Solution <: Ephemeral

// ********************************************************************
// *   Part 8: Problems                                               *
// ********************************************************************

// v0.25: a solution contains the list of values for a reference list of variables
// claire3 port: strongly typed lists
Solution <: Ephemeral(
   algo:Solver,
   choco/time:integer = 0,
   choco/nbBk:integer = 0,
   choco/nbNd:integer = 0,
   choco/objectiveValue:integer = MAXINT,
   lval:list<(integer U {unknown})>)

[makeSolution(a:Solver,nbVars:integer) : Solution
 -> Solution(algo = a, 
             lval = make_list(nbVars,(integer U {unknown}),unknown))]

// A problem is a global structure containing variables bound by constraints
// as well as solutions or solver parameters
choco/Problem <: Ephemeral(
     // public slots
   choco/constraints:list<AbstractConstraint>,  // v0.9907 becomes public  v1.0 -> AbstractConstraint
   choco/vars:list<IntVar>,
   choco/setVars:list<SetVar>,          // v1.330
   choco/name:string = "",
   choco/feasible:boolean = false,
   choco/solved:boolean = false,
   // there are two reasoning modes: feasible/infeasible (depends whether constraints may be violated or not)
   choco/feasibleMode:boolean = true,
   // tools for feasible mode: a propagation engine and a solver (controlling global search)
   choco/propagationEngine:PropagationEngine,
   choco/globalSearchSolver:GlobalSearchSolver,
   // tools for infeasible mode: an invariant engine (counting inconsistencies) and a solver (controlling local search)
   choco/invariantEngine:InvariantEngine,
   choco/localSearchSolver:LocalSearchSolver
   )
(store(feasibleMode))
;DUMMY_PROBLEM :: Problem(name = "dummy empty problem")
;choco/CURRENT_PB:Problem := DUMMY_PROBLEM
choco/CURRENT_PB:any := unknown

[choco/getIntVar(p:Problem, i:integer) : IntVar
 -> assert(0 < i & i <= length(p.vars)),
    p.vars[i] ]

[choco/getSetVar(p:Problem, i:integer) : SetVar
 -> assert(0 < i & i <= length(p.setVars)),
    p.setVars[i] ]
    
// forward declarations // v1.010
choco/setActiveProblem :: property()
choco/getActiveProblem :: property()

// ********************************************************************
// *   Part 9: local and global solvers                               *
// ********************************************************************

// -----------Logic-------------------------------------
choco/LogicEngine <: Ephemeral(
   choco/problem:Problem)

choco/PropagationEngine <: LogicEngine(
   choco/maxSize:integer = 100,
   choco/propagationOverflow:boolean = false,
   choco/contradictionCause:Ephemeral)  // v1.0 <fl>
    
InvariantEngine <: LogicEngine()

ConflictCount <: InvariantEngine(
    // slots for handling infeasible assignments (no propagation)
   choco/nbViolatedConstraints:integer = 0,
   choco/minNbViolatedConstraints:integer = MAXINT,
   choco/assignedThenUnassignedVars:list<IntVar>,     // v0.34 contains assigned variables up to index
   choco/lastAssignedVar:integer = 0,                 //       lastAssignedVar, unassigned afterwards.
   choco/conflictingVars:list<IntVar>)

// -----------Control-------------------------------------
// claire3 port: strongly typed lists
choco/Solver <: Ephemeral(
   choco/problem:Problem,
   choco/solutions:list<Solution>,     // v1.013 no more unknowns
   choco/varsToStore:list<IntVar>,     // those variables that need be stored when finding a solution
   choco/varsToShow:set<IntVar>
   )

// v1.020: new objects for search limits
choco/GlobalSearchLimit  <: Ephemeral()
NodeLimit <: GlobalSearchLimit(
   choco/maxNb:integer = 100000)
   
BacktrackLimit <: GlobalSearchLimit(
   choco/maxNb:integer = 100000)

TimeLimit <: GlobalSearchLimit(
   choco/maxMsec:integer = 3600000)

choco/GlobalSearchSolver <: Solver(
    // slots for global search
   private/stopAtFirstSol:boolean = true,
   choco/nbSol:integer = 0,        // nb of solutions found
   choco/nbBk:integer = 0,         // nb of backtracks in one tree
   choco/nbNd:integer = 0,         // nb of nodes (== nb calls to assign) expanded in one tree
   choco/maxNbBk:integer = 100000,   // limit on the total number of backtracks
   choco/maxPrintDepth:integer = 5,  // maximal depth of printing
    // slots for managing the solutions generated during the search
   choco/maxSolutionStorage:integer = 0, // v1.013 maximal number of solutions that are stored
   choco/branchingComponents:list[AbstractBranching],
   choco/baseWorld:integer = 0,
   choco/limits:list<GlobalSearchLimit>  // 1.320: new mechanism: replaces maxNbBk
   )

choco/AbstractBranching <: Ephemeral(
   choco/manager:GlobalSearchSolver,
   choco/nextBranching:AbstractBranching)

choco/LocalSearchSolver <: Solver(
    // slots for generating an assignment
   choco/buildAssignment:ConstructiveHeuristic,
   choco/changeAssignment:MoveNeighborhood,
   choco/maxNbLocalSearch:integer = 20,
   choco/maxNbLocalMoves:integer = 5000)
(store(assignedThenUnassignedVars,lastAssignedVar)) 

choco/ConstructiveHeuristic <: Ephemeral(
   choco/manager:LocalSearchSolver)

choco/MoveNeighborhood <: Ephemeral(
   choco/manager:LocalSearchSolver)


