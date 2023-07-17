// Implémentation d'un système d'explication en claire/choco
// (c) 2000-2002 - Narendra Jussien - EMN
// Système PaLM 

// ** Summary
// Part 1 : Release information (version number, release date, ...)
// Part 2 : Object hierarchy (storing explanation and specific tools)
// Part 3 : Global parameters 


// *************************************************************
// * Part 1 : Release information                              *
// *************************************************************

[palmVersion() : string -> "1.324"]
[palmReleaseDate() : string -> "Dec 1, 2002"]
[palmInfo() : string -> "PaLM : Constraint Programming with Explanations"]

[showPalmLicense()
   -> printf("** ~A \n** PaLM v~A (~A), Copyright (c) 2000-2002 N. Jussien\n", 
			 palmInfo(), palmVersion(), palmReleaseDate())]

(showPalmLicense()) // present the information at each execution ! 

// *************************************************************
// * Part 2 : object hierarchy                                 *
// *************************************************************

// Specific tools are PaLMtools 
PalmTools <: choco/Ephemeral()

// forward declaration for PalmVariable ... obviously a special 
// treatment will be reserved to PalmVariable ... 
PalmIntVar <: choco/IntVar

// Forward declaration for PalmSolver
PalmSolver <: choco/Solver

// Forward declaration for UFboxes
PalmUserFriendlyBox <: PalmTools

// ** Problem
PalmProblem <: choco/Problem(
	maxRelaxLvl: integer = 0, // maximum relaxation level acceptable with no user intervention
	palmSolver: PalmSolver, // default value for the solver

	// user-friendly information
	rootUFboxes: PalmUserFriendlyBox, // The root of the hierarchy of boxes
	relaxedUFboxes: set<PalmUserFriendlyBox>,
	allUFboxes: set<PalmUserFriendlyBox>,
	userRepresentation: set<PalmUserFriendlyBox>
) 

// ** Propagation engine
// A PaLMengine is a slightly peculiar ChocEngine
// we need to store the explicit list of constraints to be 
// awaken when posting a new one (the index position will
// not be sufficient)
PalmEngine <: choco/ChocEngine( 
	toBeAwakenConstraints: list<AbstractConstraint>,
	boundRestEvtQueue: BoundEventQueue, // Restore propagation queue
	removalRestEvtQueue:RemovalEventQueue, // a restoration handling queue

	dummyVariable: PalmIntVar = unknown, 
	contradictory: boolean = false
)


// ** Specific events
// New events need to be created for constraint removal handling
DecInf <: choco/BoundUpdate()
IncSup <: choco/BoundUpdate()
ValueRestorations <: choco/ValueRemovals()


// Storing explanations ... 
// an explanation is a set of constraints ... 
Explanation <: collection( // PalmTools( // claire3 port : collection required for iteration
	explanation: set<AbstractConstraint>
)
(ephemeral(Explanation))

// A contradiction is explained in a different way
PalmContradictionExplanation <: Explanation()


// A bound modification is explained that way
PalmBoundExplanation <: Explanation(
	previousValue: integer, // need to know the previous value for proper de-propagation
	variable: PalmIntVar // need to know the relative variable
)

// Explanation for INF modification
PalmIncInfExplanation <: PalmBoundExplanation()

// Explanation for SUP modification
PalmDecSupExplanation <: PalmBoundExplanation()

// a value removal is explained that way
PalmRemovalExplanation <: Explanation(
	value: integer,
	variable: PalmIntVar
)



// forward 
PalmIntDomain <: choco/AbstractIntDomain

// Palm variables are one of a kind ! 
PalmIntVar <: choco/IntVar(
		bucket:PalmIntDomain, // essai ....

 		originalInf: integer = -100, // keeping the first values 
		originalSup: integer = 100,

		explanationOnInf:list<PalmIncInfExplanation> = list<PalmIncInfExplanation>(),
		explanationOnSup:list<PalmDecSupExplanation> = list<PalmDecSupExplanation>(),

		// maintaining the list of assignement/refutation constraints for 
		// enumeration purposes
		enumerationConstraints: table[range = AbstractConstraint],
		negEnumerationConstraints: table[range = AbstractConstraint],

		restInfEvt: DecInf,
		restSupEvt: IncSup,
		restValEvt: ValueRestorations
)

// Palm domains are specific !
PalmIntDomain <: choco/AbstractIntDomain(
  offset: integer,
  bucketSize: integer,
  nbElements: integer = 0,
  booleanVector: list<integer>,

  // Slots pour le codage des listes chainees
  precVector: list<integer>,
  succVector: list<integer>,

  firstSuccPres: integer,
  firstPrecPres: integer,
  firstSuccAbs:  integer,
  firstPrecAbs:  integer,

  needInfComputation: boolean = false,
  needSupComputation: boolean = false,

  explanationOnVal: list<(PalmRemovalExplanation U {unknown})>, // corresponding list of explanations
  instantiationConstraints: list<AbstractConstraint>, // corresponding list of instantiation constraints
  negInstantiationConstraints: list<AbstractConstraint> // corresponding list of negations of those same constraints
)


// Palm contradiction handling tools 
raisePalmContradiction :: property()
PalmContradiction <: exception()

// solving problems

PalmSolverTools <: PalmTools(
	manager: PalmSolver // corresponding manager
)

PalmState <: PalmSolverTools(
	path: Explanation // a set of constraints that represent the current path in the search tree
)
PalmLearn <: PalmSolverTools()
// a learner for Path Repair techniques ... 
PathRepairLearn <: PalmLearn(
	maxMoves: integer = 1500,					// Maximum number of moves
	explanations: list<Explanation> = list<Explanation>(),		// The tabu list of explanations
	maxSize: integer = 7,						// The maximum size of the list
	lastExplanation: integer = 0,				// Where is the last explanation in the list
	isFull: boolean = false						// Is the list full ? 
)


// forward declaration 
PalmExtend <: PalmSolverTools
PalmBranching <: choco/Ephemeral

PalmBranching <: choco/Ephemeral(
	extender: PalmExtend,
	nextBranching: PalmBranching
)

PalmExtend <: PalmSolverTools(
	branching: PalmBranching
)

PalmUnsureExtend <: PalmExtend() // an extender that needs to ask permission

PalmRepair <: PalmSolverTools()
PalmUnsureRepair <: PalmRepair()

PalmSolution <: choco/Solution(
	feasible: boolean,
	lstat: list<integer>                 // statistiques d'exécution    
)

// ** Solver   
PalmSolver <: choco/Solver(
	statistics: list<integer> = list<integer>(), // a bunch of runtime statistics updated during the search
	finished: boolean = false,
	feasible: boolean = false,

	state: PalmState, // a representation of the current state of the problem (some info)
	learning: PalmLearn, // a learning component that specifies how to keep nogoods ...
	extending: PalmExtend, // a forward moving component that specifies how to add information
	repairing: PalmRepair // a repairing component that specifies how to handle contradictions
)

PalmBranchAndBound <: PalmSolver(
	maximizing: boolean = false,
	objective:IntVar,   // v1.0 moved from Problem to PalmBranchAndBound
	lowerBound:integer = MININT,
	upperBound:integer = MAXINT,
	optimum:integer,
	dynamicCuts: list<AbstractConstraint>
)

// User-Friendly information
PalmUserFriendlyBox <: PalmTools(
	shortName: string, // A 3 letter identifier for the block
	name: string,      // The full name of the block
	constraints: list<AbstractConstraint>, // The list of constraints defining the block
	fatherBox: PalmUserFriendlyBox, // The direct ancestor in the hierarchy
	childrenBoxes: list<PalmUserFriendlyBox> // The descendants in the hierarchy
)





// ********************************************************************
// *   Part 3: Global parameters                                      *
// ********************************************************************

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
//               XTALK: (default 2)    trace
//               XDEBUG: (default 3)  debug
//        The DEBUG flag may be shared by several fragments

// Palm Fragment 
claire/PalmVIEW: integer := 1 // Reporting structure events ... 
claire/PalmTALK: integer := 2 // Showing detailed information on the behavior
claire/PalmDEBUG:integer := 3 // debugging

claire/PalmOPTSHOW: integer := 0 // Showing evolution of optimization during search

PALM_TIME_STAMP: integer := 0 // time stamp

PALM_MAXSIZE: integer := 10000  // Taille maximum autorisee pour un domaine
PALM_BITALLONE: integer := 536870911 // Entier dont les 29 premiers bits sont a un


PALM_USER_FRIENDLY: boolean := false // UF mode switch

UNKNOWN_ABS_CT:choco/AbstractConstraint :: new(choco/AbstractConstraint)

// Global variables 
UFcurrentBox: PalmUserFriendlyBox := PalmUserFriendlyBox() // The currently defined block

