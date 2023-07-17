// interface defination for module choco, Thu Feb 13 21:07:24 2003
#ifndef CLAIREH_choco
#define CLAIREH_choco


class Util;
class StoredInt;
class Matrix;
class Matrix2D;
class MatrixND;
class BoolMatrix2D;
class BoolMatrixND;
class IntMatrix2D;
class IntMatrixND;
class IntSetAnnotation;
class IntSetIntAnnotation;
class IntSetBoolAnnotation;
class BipartiteSet;
class Ephemeral;
class Problem;
class Solver;
class LocalSearchSolver;
class GlobalSearchSolver;
class AbstractConstraint;
class IntConstraint;
class AbstractVar;
class IntVar;
class SetVar;
class PropagationEvent;
class ConstAwakeEvent;
class VarEvent;
class Instantiation;
class InstInt;
class InstSet;
class ValueRemovals;
class BoundUpdate;
class IncInf;
class DecSup;
class IncKer;
class DevEnv;
class LogicEngine;
class PropagationEngine;
class InvariantEngine;
class AbstractBranching;
class GlobalSearchLimit;
class ConstructiveHeuristic;
class MoveNeighborhood;
class Solution;
class ConflictCount;
class NodeLimit;
class BacktrackLimit;
class TimeLimit;
class AbstractDomain;
class AbstractIntDomain;
class AbstractSetDomain;
class LinkedListIntDomain;
class BitSetDomain;
class BitListSetDomain;
class DecEnv;
class EventCollection;
class EventQueue;
class BoundEventQueue;
class RemovalEventQueue;
class InstantiationStack;
class ConstAwakeEventQueue;
class ChocEngine;
class UnIntConstraint;
class BinIntConstraint;
class TernIntConstraint;
class LargeIntConstraint;
class Delayer;
class CompositeConstraint;
class BinCompositeConstraint;
class LargeCompositeConstraint;
class BinRelation;
class TruthTest;
class TruthTable2D;
class CSPBinConstraint;
class AC3BinConstraint;
class AC4BinConstraint;
class AC2001BinConstraint;
class CSPLargeConstraint;
class GreaterOrEqualxc;
class LessOrEqualxc;
class Equalxc;
class NotEqualxc;
class Equalxyc;
class NotEqualxyc;
class GreaterOrEqualxyc;
class LinComb;
class Elt;
class Elt2;
class AllDiff;
class Occurrence;
class BinBoolConstraint;
class LargeBoolConstraint;
class BinBoolConstraintWCounterOpp;
class LargeBoolConstraintWCounterOpp;
class Disjunction;
class Guard;
class Equiv;
class Conjunction;
class LargeConjunction;
class LargeDisjunction;
class Cardinality;
class SetConstraint;
class UnSetConstraint;
class BinSetIntConstraint;
class BinSetConstraint;
class TernSetConstraint;
class SetCard;
class MemberC;
class NotMemberC;
class MemberX;
class NotMemberX;
class SetIntersection;
class SetUnion;
class SubSet;
class DisjointSets;
class OverlappingSets;
class LargeBranching;
class BinBranching;
class CompositeBranching;
class VarSelector;
class MinDomain;
class MaxDeg;
class DomDeg;
class StaticVarOrder;
class ValIterator;
class IncreasingDomain;
class DecreasingDomain;
class ValSelector;
class MidValHeuristic;
class MinValHeuristic;
class MaxValHeuristic;
class AssignVar;
class SplitDomain;
class AssignOrForbid;
class SettleBinDisjunction;
class Solve;
class AbstractOptimize;
class BranchAndBound;
class OptimizeWithRestarts;
class AssignmentHeuristic;
class FlipNeighborhood;
class MultipleDescent;
class Term;
class LinTerm;
class UnTerm;
class BinTerm;
class OccurTerm;
class EltTerm;
class Elt2Term;

class Util: public ephemeral_object{ 
  public:} 
;

class StoredInt: public Util{ 
  public:
     int latestValue;
     int latestUpdate;} 
;

class Matrix: public Util{ 
  public:
     ClaireBoolean *backtrackable;} 
;

class Matrix2D: public Matrix{ 
  public:
     int size1;
     int offset1;
     int size2;
     int offset2;} 
;

class MatrixND: public Matrix{ 
  public:
     int dim;
     OID *lsizes;
     OID *offsetArray;} 
;

class BoolMatrix2D: public Matrix2D{ 
  public:
     OID *contents;} 
;

class BoolMatrixND: public MatrixND{ 
  public:
     OID *contents;} 
;

class IntMatrix2D: public Matrix2D{ 
  public:
     OID *contents;} 
;

class IntMatrixND: public MatrixND{ 
  public:
     OID *contents;} 
;

class IntSetAnnotation: public Util{ 
  public:
     int offset;
     int asize;} 
;

class IntSetIntAnnotation: public IntSetAnnotation{ 
  public:
     OID *intValues;} 
;

class IntSetBoolAnnotation: public IntSetAnnotation{ 
  public:
     OID *boolValues;} 
;

class BipartiteSet: public Util{ 
  public:
     OID defaultValue;
     OID *objs;
     int nbLeft;
     table *indices;} 
;

class Ephemeral: public ephemeral_object{ 
  public:} 
;

class Problem: public Ephemeral{ 
  public:
     list *constraints;
     list *vars;
     list *setVars;
     char *name;
     ClaireBoolean *feasible;
     ClaireBoolean *solved;
     ClaireBoolean *feasibleMode;
     PropagationEngine *propagationEngine;
     GlobalSearchSolver *globalSearchSolver;
     InvariantEngine *invariantEngine;
     LocalSearchSolver *localSearchSolver;} 
;

class Solver: public Ephemeral{ 
  public:
     Problem *problem;
     list *solutions;
     list *varsToStore;
     set *varsToShow;} 
;

class LocalSearchSolver: public Solver{ 
  public:
     ConstructiveHeuristic *buildAssignment;
     MoveNeighborhood *changeAssignment;
     int maxNbLocalSearch;
     int maxNbLocalMoves;} 
;

class GlobalSearchSolver: public Solver{ 
  public:
     ClaireBoolean *stopAtFirstSol;
     int nbSol;
     int nbBk;
     int nbNd;
     int maxNbBk;
     int maxPrintDepth;
     int maxSolutionStorage;
     list *branchingComponents;
     int baseWorld;
     list *limits;} 
;

class AbstractConstraint: public Ephemeral{ 
  public:
     OID hook;
     ConstAwakeEvent *constAwakeEvent;
     int fastDispatchIndex;
     ClaireBoolean *violated;
  int  choco_getNbVars();  
  int  choco_assignIndices(AbstractConstraint *root,int i);  
  int  choco_getConstraintIdx(int idx);  
  void  choco_setConstraintIndex(int i,int val);  
  OID  choco_askIfEntailed();  
  ClaireBoolean * choco_testIfSatisfied();  
  ClaireBoolean * choco_testIfCompletelyInstantiated();  
  void  choco_propagate();  
  void  choco_awakeOnInf(int idx);  
  void  choco_awakeOnSup(int idx);  
  void  choco_awakeOnInst(int idx);  
  void  choco_awakeOnRem(int idx,int x);  } 
;

class IntConstraint: public AbstractConstraint{ 
  public:
     int cste;} 
;

class AbstractVar: public Ephemeral{ 
  public:
     char *name;
     OID hook;
     Problem *problem;
     int nbViolatedConstraints;
     int nbConstraints;
     list *constraints;
     list *indices;} 
;

class IntVar: public AbstractVar{ 
  public:
     StoredInt *inf;
     StoredInt *sup;
     int value;
     int savedAssignment;
     AbstractIntDomain *bucket;
     IncInf *updtInfEvt;
     DecSup *updtSupEvt;
     InstInt *instantiateEvt;
     ValueRemovals *remValEvt;
  ClaireBoolean * choco_domainIncludedIn(list *sortedList);  
  ClaireBoolean * choco_hasExactDomain();  
  int  choco_getInf();  
  int  choco_getSup();  } 
;

class SetVar: public AbstractVar{ 
  public:
     AbstractSetDomain *setBucket;
     IncKer *updtKerEvt;
     DecEnv *updtEnvEvt;
     InstSet *instantiateEvt;} 
;

class PropagationEvent: public Ephemeral{ 
  public:} 
;

class ConstAwakeEvent: public PropagationEvent{ 
  public:
     AbstractConstraint *touchedConst;
     ClaireBoolean *initialized;
     int priority;} 
;

class VarEvent: public PropagationEvent{ 
  public:
     AbstractVar *modifiedVar;
     list *nextConst;} 
;

class Instantiation: public VarEvent{ 
  public:
     int cause;} 
;

class InstInt: public Instantiation{ 
  public:} 
;

class InstSet: public Instantiation{ 
  public:} 
;

class ValueRemovals: public VarEvent{ 
  public:
     int maxVals;
     int nbVals;
     ClaireBoolean *many;
     list *valueStack;
     list *causeStack;
     int idxInQueue;} 
;

class BoundUpdate: public VarEvent{ 
  public:
     int cause;
     int idxInQueue;} 
;

class IncInf: public BoundUpdate{ 
  public:} 
;

class DecSup: public BoundUpdate{ 
  public:} 
;

class IncKer: public BoundUpdate{ 
  public:} 
;

class DevEnv: public BoundUpdate{ 
  public:} 
;

class LogicEngine: public Ephemeral{ 
  public:
     Problem *problem;} 
;

class PropagationEngine: public LogicEngine{ 
  public:
     int maxSize;
     ClaireBoolean *propagationOverflow;
     Ephemeral *contradictionCause;
  OID  choco_getNextActiveEventQueue();  } 
;

class InvariantEngine: public LogicEngine{ 
  public:} 
;

class AbstractBranching: public Ephemeral{ 
  public:
     GlobalSearchSolver *manager;
     AbstractBranching *nextBranching;
  OID  choco_selectBranchingObject();  
  void  choco_goDownBranch(OID x,int i);  
  void  choco_traceDownBranch(OID x,int i);  
  void  choco_goUpBranch(OID x,int i);  
  void  choco_traceUpBranch(OID x,int i);  
  int  choco_getFirstBranch(OID x);  
  int  choco_getNextBranch(OID x,int i);  
  ClaireBoolean * choco_finishedBranching(OID x,int i);  
  ClaireBoolean * choco_branchOn(OID v,int n);  } 
;

class GlobalSearchLimit: public Ephemeral{ 
  public:
  ClaireBoolean * choco_mayExpandNewNode(GlobalSearchSolver *algo);  } 
;

class ConstructiveHeuristic: public Ephemeral{ 
  public:
     LocalSearchSolver *manager;} 
;

class MoveNeighborhood: public Ephemeral{ 
  public:
     LocalSearchSolver *manager;} 
;

class Solution: public Ephemeral{ 
  public:
     Solver *algo;
     int time;
     int nbBk;
     int nbNd;
     int objectiveValue;
     list *lval;} 
;

class ConflictCount: public InvariantEngine{ 
  public:
     int nbViolatedConstraints;
     int minNbViolatedConstraints;
     list *assignedThenUnassignedVars;
     int lastAssignedVar;
     list *conflictingVars;} 
;

class NodeLimit: public GlobalSearchLimit{ 
  public:
     int maxNb;} 
;

class BacktrackLimit: public GlobalSearchLimit{ 
  public:
     int maxNb;} 
;

class TimeLimit: public GlobalSearchLimit{ 
  public:
     int maxMsec;} 
;

class AbstractDomain: public ClaireCollection{ 
  public:} 
;

class AbstractIntDomain: public AbstractDomain{ 
  public:
  list * choco_domainSequence();  
  set * choco_domainSet();  
  int  choco_updateDomainInf(int x);  
  int  choco_updateDomainSup(int x);  
  int  choco_getDomainInf();  
  int  choco_getDomainSup();  
  ClaireBoolean * choco_containsValInDomain(int x);  
  int  choco_getNextValue(int x);  
  int  choco_getPrevValue(int x);  
  ClaireBoolean * choco_removeDomainVal(int x);  
  void  choco_restrict(int x);  
  int  choco_getDomainCard();  } 
;

class AbstractSetDomain: public AbstractDomain{ 
  public:
     int minValue;
     int kernelSize;
     int enveloppeSize;
  list * choco_getKernel();  
  list * choco_getEnveloppe();  
  int  choco_getKernelSize();  
  int  choco_getEnveloppeSize();  
  int  choco_getEnveloppeInf();  
  int  choco_getEnveloppeSup();  
  ClaireBoolean * choco_isInKernel(int x);  
  ClaireBoolean * choco_isInEnveloppe(int x);  
  ClaireBoolean * choco_updateKernel(int x);  
  ClaireBoolean * choco_updateEnveloppe(int x);  } 
;

class LinkedListIntDomain: public AbstractIntDomain{ 
  public:
     int offset;
     int bucketSize;
     OID *contents;} 
;

class BitSetDomain: public AbstractSetDomain{ 
  public:
     int kernelBitVector;
     int enveloppeBitVector;} 
;

class BitListSetDomain: public AbstractSetDomain{ 
  public:
     list *kernelBitVectorList;
     list *enveloppeBitVectorList;} 
;

class DecEnv: public BoundUpdate{ 
  public:} 
;

class EventCollection: public Ephemeral{ 
  public:
     PropagationEngine *engine;
     int qsize;
  void  choco_popSomeEvents();  } 
;

class EventQueue: public EventCollection{ 
  public:
     int qLastRead;
     int qLastEnqueued;
     ClaireBoolean *isPopping;} 
;

class BoundEventQueue: public EventQueue{ 
  public:
     list *eventQueue;
     ClaireBoolean *redundantEvent;} 
;

class RemovalEventQueue: public EventQueue{ 
  public:
     list *eventQueue;
     ClaireBoolean *redundantEvent;} 
;

class InstantiationStack: public EventCollection{ 
  public:
     list *eventQueue;
     int sLastRead;
     int sLastPushed;} 
;

class ConstAwakeEventQueue: public EventCollection{ 
  public:
     BipartiteSet *partition;} 
;

class ChocEngine: public PropagationEngine{ 
  public:
     RemovalEventQueue *removalEvtQueue;
     BoundEventQueue *boundEvtQueue;
     InstantiationStack *instEvtStack;
     ConstAwakeEventQueue *delayedConst1;
     ConstAwakeEventQueue *delayedConst2;
     ConstAwakeEventQueue *delayedConst3;
     ConstAwakeEventQueue *delayedConst4;
     int nbPendingInitConstAwakeEvent;
     int nbPendingVarEvent;
     int delayLinCombThreshold;} 
;

class UnIntConstraint: public IntConstraint{ 
  public:
     IntVar *v1;
     int idx1;} 
;

class BinIntConstraint: public IntConstraint{ 
  public:
     IntVar *v1;
     int idx1;
     IntVar *v2;
     int idx2;} 
;

class TernIntConstraint: public IntConstraint{ 
  public:
     IntVar *v1;
     int idx1;
     IntVar *v2;
     int idx2;
     IntVar *v3;
     int idx3;} 
;

class LargeIntConstraint: public IntConstraint{ 
  public:
     list *vars;
     list *indices;
     int nbVars;} 
;

class Delayer: public IntConstraint{ 
  public:
     int priority;
     IntConstraint *target;} 
;

class CompositeConstraint: public AbstractConstraint{ 
  public:} 
;

class BinCompositeConstraint: public CompositeConstraint{ 
  public:
     AbstractConstraint *const1;
     AbstractConstraint *const2;
     int offset;} 
;

class LargeCompositeConstraint: public CompositeConstraint{ 
  public:
     list *lconst;
     list *loffset;
     int nbConst;
     list *additionalVars;
     list *additionalIndices;} 
;

class BinRelation: public Util{ 
  public:
  ClaireBoolean * choco_getTruthValue(int x,int y);  } 
;

class TruthTest: public BinRelation{ 
  public:
     method *test;} 
;

class TruthTable2D: public BinRelation{ 
  public:
     int offset1;
     int offset2;
     int size1;
     int size2;
     BoolMatrix2D *matrix;} 
;

class CSPBinConstraint: public BinIntConstraint{ 
  public:
     BinRelation *feasRelation;
     ClaireBoolean *feasiblePair;} 
;

class AC3BinConstraint: public CSPBinConstraint{ 
  public:} 
;

class AC4BinConstraint: public CSPBinConstraint{ 
  public:
     IntSetIntAnnotation *nbSupport1;
     IntSetIntAnnotation *nbSupport2;
     IntSetBoolAnnotation *validSupport1;
     IntSetBoolAnnotation *validSupport2;} 
;

class AC2001BinConstraint: public CSPBinConstraint{ 
  public:
     IntSetIntAnnotation *currentSupport1;
     IntSetIntAnnotation *currentSupport2;} 
;

class CSPLargeConstraint: public LargeIntConstraint{ 
  public:
     ClaireBoolean *cachedTuples;
     BoolMatrixND *matrix;
     method *feasTest;} 
;

class GreaterOrEqualxc: public UnIntConstraint{ 
  public:} 
;

class LessOrEqualxc: public UnIntConstraint{ 
  public:} 
;

class Equalxc: public UnIntConstraint{ 
  public:} 
;

class NotEqualxc: public UnIntConstraint{ 
  public:} 
;

class Equalxyc: public BinIntConstraint{ 
  public:} 
;

class NotEqualxyc: public BinIntConstraint{ 
  public:} 
;

class GreaterOrEqualxyc: public BinIntConstraint{ 
  public:} 
;

class LinComb: public LargeIntConstraint{ 
  public:
     int nbPosVars;
     OID *coefs;
     int op;
     ClaireBoolean *improvedUpperBound;
     ClaireBoolean *improvedLowerBound;
  void  choco_filter(ClaireBoolean *startWithLB,int minNbRules);  
  ClaireBoolean * choco_filterOnImprovedLowerBound();  
  ClaireBoolean * choco_filterOnImprovedUpperBound();  } 
;

class Elt: public BinIntConstraint{ 
  public:
     list *lval;} 
;

class Elt2: public TernIntConstraint{ 
  public:
     table *tval;
     int dim1;
     int dim2;} 
;

class AllDiff: public LargeIntConstraint{ 
  public:} 
;

class Occurrence: public LargeIntConstraint{ 
  public:
     OID *isPossible;
     OID *isSure;
     int nbPossible;
     int nbSure;
     ClaireBoolean *constrainOnInfNumber;
     ClaireBoolean *constrainOnSupNumber;} 
;

class BinBoolConstraint: public BinCompositeConstraint{ 
  public:
     int statusBitVector;} 
;

class LargeBoolConstraint: public LargeCompositeConstraint{ 
  public:
     list *statusBitVectorList;
     int nbTrueStatus;
     int nbFalseStatus;} 
;

class BinBoolConstraintWCounterOpp: public BinBoolConstraint{ 
  public:
     AbstractConstraint *oppositeConst1;
     AbstractConstraint *oppositeConst2;
     list *indicesInOppConst1;
     list *indicesInOppConst2;} 
;

class LargeBoolConstraintWCounterOpp: public LargeBoolConstraint{ 
  public:
     list *loppositeConst;
     list *indicesInOppConsts;} 
;

class Disjunction: public BinBoolConstraint{ 
  public:} 
;

class Guard: public BinBoolConstraint{ 
  public:} 
;

class Equiv: public BinBoolConstraintWCounterOpp{ 
  public:} 
;

class Conjunction: public BinBoolConstraint{ 
  public:} 
;

class LargeConjunction: public LargeBoolConstraint{ 
  public:} 
;

class LargeDisjunction: public LargeBoolConstraint{ 
  public:} 
;

class Cardinality: public LargeBoolConstraintWCounterOpp{ 
  public:
     ClaireBoolean *constrainOnInfNumber;
     ClaireBoolean *constrainOnSupNumber;} 
;

class SetConstraint: public AbstractConstraint{ 
  public:
     SetVar *sv1;
     int idx1;} 
;

class UnSetConstraint: public SetConstraint{ 
  public:
     int cste;} 
;

class BinSetIntConstraint: public SetConstraint{ 
  public:
     IntVar *v2;
     int idx2;} 
;

class BinSetConstraint: public SetConstraint{ 
  public:
     SetVar *sv2;
     int idx2;} 
;

class TernSetConstraint: public SetConstraint{ 
  public:
     SetVar *sv2;
     SetVar *sv3;
     int idx2;
     int idx3;} 
;

class SetCard: public BinSetIntConstraint{ 
  public:} 
;

class MemberC: public UnSetConstraint{ 
  public:} 
;

class NotMemberC: public UnSetConstraint{ 
  public:} 
;

class MemberX: public BinSetIntConstraint{ 
  public:} 
;

class NotMemberX: public BinSetIntConstraint{ 
  public:} 
;

class SetIntersection: public TernSetConstraint{ 
  public:} 
;

class SetUnion: public TernSetConstraint{ 
  public:} 
;

class SubSet: public BinSetConstraint{ 
  public:} 
;

class DisjointSets: public BinSetConstraint{ 
  public:} 
;

class OverlappingSets: public BinSetConstraint{ 
  public:
     ClaireBoolean *emptyKernelIntersection;} 
;

class LargeBranching: public AbstractBranching{ 
  public:} 
;

class BinBranching: public AbstractBranching{ 
  public:} 
;

class CompositeBranching: public LargeBranching{ 
  public:
     AbstractBranching *alternatives;} 
;

class VarSelector: public Ephemeral{ 
  public:
     AbstractBranching *branching;
  OID  choco_selectVar();  } 
;

class MinDomain: public VarSelector{ 
  public:
     Problem *problem;
     list *vars;} 
;

class MaxDeg: public VarSelector{ 
  public:
     Problem *problem;
     list *vars;} 
;

class DomDeg: public VarSelector{ 
  public:
     Problem *problem;
     list *vars;} 
;

class StaticVarOrder: public VarSelector{ 
  public:
     list *vars;} 
;

class ValIterator: public Ephemeral{ 
  public:
     AbstractBranching *branching;
  ClaireBoolean * choco_isOver(IntVar *x,int i);  
  int  choco_getFirstVal(IntVar *x);  
  int  choco_getNextVal(IntVar *x,int i);  } 
;

class IncreasingDomain: public ValIterator{ 
  public:} 
;

class DecreasingDomain: public ValIterator{ 
  public:} 
;

class ValSelector: public Ephemeral{ 
  public:
     AbstractBranching *branching;
  int  choco_getBestVal(IntVar *x);  } 
;

class MidValHeuristic: public ValSelector{ 
  public:} 
;

class MinValHeuristic: public ValSelector{ 
  public:} 
;

class MaxValHeuristic: public ValSelector{ 
  public:} 
;

class AssignVar: public LargeBranching{ 
  public:
     VarSelector *varHeuristic;
     ValIterator *valHeuristic;} 
;

class SplitDomain: public BinBranching{ 
  public:
     VarSelector *varHeuristic;
     ValSelector *valHeuristic;
     int dichotomyThreshold;} 
;

class AssignOrForbid: public BinBranching{ 
  public:
     VarSelector *varHeuristic;
     ValSelector *valHeuristic;} 
;

class SettleBinDisjunction: public BinBranching{ 
  public:
     list *disjunctions;} 
;

class Solve: public GlobalSearchSolver{ 
  public:} 
;

class AbstractOptimize: public GlobalSearchSolver{ 
  public:
     ClaireBoolean *doMaximize;
     IntVar *objective;
     int lowerBound;
     int upperBound;
     int targetLowerBound;
     int targetUpperBound;} 
;

class BranchAndBound: public AbstractOptimize{ 
  public:} 
;

class OptimizeWithRestarts: public AbstractOptimize{ 
  public:
     int nbIter;
     int baseNbSol;
     int nbBkTot;
     int nbNdTot;} 
;

class AssignmentHeuristic: public ConstructiveHeuristic{ 
  public:} 
;

class FlipNeighborhood: public MoveNeighborhood{ 
  public:} 
;

class MultipleDescent: public LocalSearchSolver{ 
  public:
     int nbLocalSearch;
     int nbLocalMoves;} 
;

class Term: public Ephemeral{ 
  public:
     int cste;} 
;

class LinTerm: public Term{ 
  public:
     list *lcoeff;
     list *lvars;} 
;

class UnTerm: public Term{ 
  public:
     IntVar *v1;
     ClaireBoolean *sign1;} 
;

class BinTerm: public Term{ 
  public:
     IntVar *v1;
     IntVar *v2;
     ClaireBoolean *sign1;
     ClaireBoolean *sign2;} 
;

class OccurTerm: public Term{ 
  public:
     int target;
     list *lvars;} 
;

class EltTerm: public Term{ 
  public:
     list *lvalues;
     IntVar *indexVar;} 
;

class Elt2Term: public Term{ 
  public:
     table *valueTable;
     int dim1;
     int dim2;
     IntVar *index1Var;
     IntVar *index2Var;} 
;
extern int  choco_div_dash_integer(int a,int b);
extern int  choco_div_plus_integer(int a,int b);
extern int  claire_max_integer2(int x,int y);
extern int  claire_min_integer2(int x,int y);
extern int  claire_max_bag(bag *x);
extern int  claire_min_bag(bag *x);
extern int  claire_sum_bag(bag *x);
extern int  claire_product_bag(bag *x);
extern int  claire_count_any(OID S);
extern int  claire_abs_integer(int v);
extern double  claire_abs_float(double v);
extern OID  claire_abs_float_(OID g0000);
extern char * claire_stringFormat_integer(int n,int k);
extern char * claire_stringFormat_string(char *s,int k);
extern OID  claire_random_list(list *l);
extern int  claire_random_Interval(Interval *I);
extern int  claire_random_integer2(int a,int b);
extern list * claire__7_plus_Interval1(Interval *x,Interval *y);
extern list * claire__7_plus_Interval2(Interval *x,set *y);
extern list * claire__7_plus_set2(set *x,Interval *y);
extern int  claire_integer_I_boolean(ClaireBoolean *b);
extern ClaireBoolean * choco_knownInt_integer(int x);
extern void  claire_self_print_StoredInt_choco(StoredInt *x);
extern void  claire_write_StoredInt(StoredInt *x,int y);
extern int  claire_read_StoredInt(StoredInt *x);
extern BoolMatrix2D * choco_make2DBoolMatrix_integer(int a1,int a2,int b1,int b2,ClaireBoolean *DEFAULT,ClaireBoolean *stored);
extern IntMatrix2D * choco_make2DIntMatrix_integer(int a1,int a2,int b1,int b2,int DEFAULT,ClaireBoolean *stored);
extern BoolMatrixND * choco_makeNDBoolMatrix_list(list *l,ClaireBoolean *DEFAULT,ClaireBoolean *stored);
extern IntMatrixND * choco_makeNDIntMatrix_list(list *l,int DEFAULT,ClaireBoolean *stored);
extern int  choco_flatIndex_Matrix2D(Matrix2D *m,int i,int j);
extern ClaireBoolean * claire_read_BoolMatrix2D(BoolMatrix2D *m,int i,int j);
extern void  claire_store_BoolMatrix2D(BoolMatrix2D *m,int i,int j,ClaireBoolean *x);
extern int  claire_read_IntMatrix2D(IntMatrix2D *m,int i,int j);
extern void  claire_store_IntMatrix2D(IntMatrix2D *m,int i,int j,int x);
extern int  choco_flatIndex_MatrixND(MatrixND *m,list *l);
extern ClaireBoolean * claire_read_BoolMatrixND(BoolMatrixND *m,list *l);
extern void  claire_store_BoolMatrixND(BoolMatrixND *m,list *l,ClaireBoolean *x);
extern int  claire_read_IntMatrixND(IntMatrixND *m,list *l);
extern void  claire_store_IntMatrixND(IntMatrixND *m,list *l,int x);
extern int  choco_getOriginalMin_IntSetAnnotation(IntSetAnnotation *annot);
extern int  choco_getOriginalMax_IntSetAnnotation(IntSetAnnotation *annot);
extern int  choco_getIntAnnotation_IntSetIntAnnotation(IntSetIntAnnotation *ida,int val);
extern void  choco_setIntAnnotation_IntSetIntAnnotation(IntSetIntAnnotation *ida,int val,int annot);
extern IntSetIntAnnotation * choco_makeIntSetIntAnnotation_integer(int min,int max,int def);
extern ClaireBoolean * choco_getBoolAnnotation_IntSetBoolAnnotation(IntSetBoolAnnotation *ida,int val);
extern void  choco_setBoolAnnotation_IntSetBoolAnnotation(IntSetBoolAnnotation *ida,int val,ClaireBoolean *annot);
extern IntSetBoolAnnotation * choco_makeIntSetBoolAnnotation_integer(int min,int max,ClaireBoolean *DEFAULT);
extern table * choco_makeIndexesTable_type(ClaireType *memberType);
extern BipartiteSet * choco_makeBipartiteSet_type(ClaireType *memberType,OID DEFAULT);
extern void  choco_swap_BipartiteSet(BipartiteSet *b,int idx1,int idx2);
extern void  choco_moveLeft_BipartiteSet(BipartiteSet *b,OID obj);
extern void  choco_moveRight_BipartiteSet(BipartiteSet *b,OID obj);
extern void  choco_moveAllLeft_BipartiteSet(BipartiteSet *b);
extern void  choco_moveAllRight_BipartiteSet(BipartiteSet *b);
extern void  choco_addRight_BipartiteSet(BipartiteSet *b,OID obj);
extern void  choco_addLeft_BipartiteSet(BipartiteSet *b,OID obj);
extern ClaireBoolean * choco_isLeft_BipartiteSet(BipartiteSet *b,OID obj);
extern ClaireBoolean * choco_isIn_BipartiteSet(BipartiteSet *b,OID obj);
extern int  choco_getNbLeft_BipartiteSet(BipartiteSet *b);
extern int  choco_getNbRight_BipartiteSet(BipartiteSet *b);
extern int  choco_getNbObjects_BipartiteSet(BipartiteSet *b);
extern OID  choco_getObject_BipartiteSet(BipartiteSet *b,int i);
extern set * choco_leftPart_BipartiteSet(BipartiteSet *b);
extern set * choco_rightPart_BipartiteSet(BipartiteSet *b);
extern int  choco_getBitCount_integer(int bv);
extern int  choco_getMinBitIndex_integer(int bv);
extern int  choco_getMaxBitIndex_integer(int bv);
extern int  choco_makeAllOnesBitVector_integer(int n);
extern list * choco_getBitVectorList_integer(int minv,list *l);
extern ClaireBoolean * choco_isInBitVectorList_integer(int idx,list *l);
extern ClaireBoolean * choco_addToBitVectorList_integer(int idx,list *l);
extern ClaireBoolean * choco_remFromBitVectorList_integer(int idx,list *l);
extern int  choco_getBitVectorListCount_list(list *l);
extern int  choco_getBitVectorInf_list(list *l);
extern int  choco_getBitVectorSup_list(list *l);
extern void  choco_showChocoLicense_void();
extern Solution * choco_makeSolution_Solver(Solver *a,int nbVars);
extern IntVar * choco_getIntVar_Problem(Problem *p,int i);
extern SetVar * choco_getSetVar_Problem(Problem *p,int i);
extern list * choco_domainSequence_AbstractIntDomain_choco(AbstractIntDomain *d);
extern set * choco_domainSet_AbstractIntDomain_choco(AbstractIntDomain *d);
extern int  choco_getDomainInf_AbstractIntDomain_choco(AbstractIntDomain *d);
extern int  choco_getDomainSup_AbstractIntDomain_choco(AbstractIntDomain *d);
extern int  choco_updateDomainInf_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern int  choco_updateDomainSup_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern ClaireBoolean * choco_containsValInDomain_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern ClaireBoolean * choco_removeDomainVal_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern void  choco_restrict_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern int  choco_getDomainCard_AbstractIntDomain_choco(AbstractIntDomain *d);
extern int  choco_getNextValue_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern int  choco_getPrevValue_AbstractIntDomain_choco(AbstractIntDomain *d,int x);
extern void  claire_self_print_LinkedListIntDomain_choco(LinkedListIntDomain *x);
extern LinkedListIntDomain * choco_makeLinkedListIntDomain_integer(int a,int b);
extern int  claire_random_LinkedListIntDomain(LinkedListIntDomain *d);
extern set * choco_domainSet_LinkedListIntDomain_choco(LinkedListIntDomain *d);
extern list * choco_domainSequence_LinkedListIntDomain_choco(LinkedListIntDomain *d);
extern int  choco_getDomainInf_LinkedListIntDomain_choco(LinkedListIntDomain *d);
extern int  choco_getDomainSup_LinkedListIntDomain_choco(LinkedListIntDomain *d);
extern ClaireBoolean * choco_isIncludedIn_LinkedListIntDomain(LinkedListIntDomain *b,list *l);
extern int  choco_getDomainCard_LinkedListIntDomain_choco(LinkedListIntDomain *d);
extern ClaireBoolean * choco_containsValInDomain_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern int  choco_getNextValue_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern int  choco_getPrevValue_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern ClaireBoolean * choco_removeDomainVal_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern void  choco_restrict_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern int  choco_updateDomainInf_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern int  choco_updateDomainSup_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x);
extern list * choco_getKernel_AbstractSetDomain(AbstractSetDomain *d);
extern list * choco_getEnveloppe_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getKernelSize_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getEnveloppeSize_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getKernelInf_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getKernelSup_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getEnveloppeInf_AbstractSetDomain(AbstractSetDomain *d);
extern int  choco_getEnveloppeSup_AbstractSetDomain(AbstractSetDomain *d);
extern ClaireBoolean * choco_isInEnveloppe_AbstractSetDomain(AbstractSetDomain *d,int x);
extern ClaireBoolean * choco_isInKernel_AbstractSetDomain(AbstractSetDomain *d,int x);
extern ClaireBoolean * choco_updateKernel_AbstractSetDomain(AbstractSetDomain *d,int x);
extern ClaireBoolean * choco_updateEnveloppe_AbstractSetDomain(AbstractSetDomain *d,int x);
extern list * choco_getKernel_BitSetDomain(BitSetDomain *d);
extern list * choco_getEnveloppe_BitSetDomain(BitSetDomain *d);
extern int  choco_getKernelSize_BitSetDomain(BitSetDomain *d);
extern int  choco_getEnveloppeSize_BitSetDomain(BitSetDomain *d);
extern int  choco_getKernelInf_BitSetDomain(BitSetDomain *d);
extern int  choco_getKernelSup_BitSetDomain(BitSetDomain *d);
extern int  choco_getEnveloppeInf_BitSetDomain(BitSetDomain *d);
extern int  choco_getEnveloppeSup_BitSetDomain(BitSetDomain *d);
extern ClaireBoolean * choco_isInEnveloppe_BitSetDomain(BitSetDomain *d,int x);
extern ClaireBoolean * choco_isInKernel_BitSetDomain(BitSetDomain *d,int x);
extern BitSetDomain * choco_makeBitSetDomain_integer(int i,int j);
extern ClaireBoolean * choco_updateKernel_BitSetDomain(BitSetDomain *d,int x);
extern ClaireBoolean * choco_updateEnveloppe_BitSetDomain(BitSetDomain *d,int x);
extern BitListSetDomain * choco_makeBitListSetDomain_integer(int i,int j);
extern list * choco_getKernel_BitListSetDomain(BitListSetDomain *d);
extern list * choco_getEnveloppe_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getKernelSize_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getEnveloppeSize_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getKernelInf_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getKernelSup_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getEnveloppeInf_BitListSetDomain(BitListSetDomain *d);
extern int  choco_getEnveloppeSup_BitListSetDomain(BitListSetDomain *d);
extern ClaireBoolean * choco_isInEnveloppe_BitListSetDomain(BitListSetDomain *d,int x);
extern ClaireBoolean * choco_isInKernel_BitListSetDomain(BitListSetDomain *d,int x);
extern ClaireBoolean * choco_updateKernel_BitListSetDomain(BitListSetDomain *d,int x);
extern ClaireBoolean * choco_updateEnveloppe_BitListSetDomain(BitListSetDomain *d,int x);
extern void  claire_self_print_Instantiation_choco(Instantiation *e);
extern void  claire_self_print_InstInt_choco(InstInt *e);
extern void  claire_self_print_InstSet_choco(InstSet *e);
extern void  claire_self_print_BoundUpdate_choco(BoundUpdate *e);
extern void  claire_self_print_IncInf_choco(IncInf *e);
extern void  claire_self_print_DecSup_choco(DecSup *e);
extern void  claire_self_print_IncKer_choco(IncKer *e);
extern void  claire_self_print_DecEnv_choco(DecEnv *e);
extern void  claire_self_print_ValueRemovals_choco(ValueRemovals *e);
extern void  claire_self_print_ConstAwakeEvent_choco(ConstAwakeEvent *e);
extern void  choco_raiseContradiction_PropagationEngine1(PropagationEngine *pe);
extern void  choco_raiseContradiction_PropagationEngine2(PropagationEngine *pe,Ephemeral *x);
extern Ephemeral * choco_getContradictionCause_PropagationEngine(PropagationEngine *pe);
extern void  choco_raiseContradiction_AbstractVar(AbstractVar *v);
extern void  choco_raiseContradiction_AbstractConstraint(AbstractConstraint *c);
extern void  choco_raiseContradiction_Problem(Problem *pb);
extern ChocEngine * choco_makeChocEngine_integer(int n);
extern void  choco_attachPropagationEngine_Problem(Problem *pb,PropagationEngine *pe);
extern ClaireBoolean * choco_isEmpty_EventQueue(EventQueue *q);
extern ClaireBoolean * choco_isEmpty_InstantiationStack(InstantiationStack *s);
extern BoundUpdate * choco_popNextEvent_BoundEventQueue(BoundEventQueue *q);
extern ValueRemovals * choco_popNextEvent_RemovalEventQueue(RemovalEventQueue *q);
extern Instantiation * choco_popNextEvent_InstantiationStack(InstantiationStack *q);
extern int  choco_nextEventPostIndex_BoundEventQueue(BoundEventQueue *q);
extern int  choco_nextEventPostIndex_RemovalEventQueue(RemovalEventQueue *q);
extern void  choco_raiseOverflowWarning_PropagationEngine(PropagationEngine *pe);
extern int  choco_nextEventPostIndex_InstantiationStack(InstantiationStack *s);
extern void  choco_flushEventQueue_BoundEventQueue(BoundEventQueue *q);
extern void  choco_flushEventQueue_RemovalEventQueue(RemovalEventQueue *q);
extern void  choco_flushEventQueue_ConstAwakeEventQueue(ConstAwakeEventQueue *q);
extern OID  choco_checkCleanState_ChocEngine(ChocEngine *pe);
extern void  choco_pushWorld_Problem(Problem *pb);
extern void  choco_popWorld_Problem(Problem *pb);
extern void  choco_setWorld_Problem(Problem *pb,int n);
extern void  choco_commitWorld_Problem(Problem *pb);
extern OID  choco_flushCurrentOpenEvents_ChocEngine(ChocEngine *pe);
extern void  choco_postInstInt_PropagationEngine(PropagationEngine *pe,IntVar *v,int i);
extern void  choco_postRemoveVal_PropagationEngine(PropagationEngine *pe,IntVar *v,int x,int i);
extern void  choco_postUpdateInf_PropagationEngine(PropagationEngine *pe,IntVar *v,int i);
extern void  choco_postUpdateSup_PropagationEngine(PropagationEngine *pe,IntVar *v,int i);
extern void  choco_postUpdateKer_PropagationEngine(PropagationEngine *pe,SetVar *v,int i);
extern void  choco_postUpdateEnv_PropagationEngine(PropagationEngine *pe,SetVar *v,int i);
extern void  choco_postInstSet_PropagationEngine(PropagationEngine *pe,SetVar *v,int i);
extern void  choco_postConstAwake_PropagationEngine(PropagationEngine *pe,AbstractConstraint *c,ClaireBoolean *init);
extern void  choco_postInstantiateEvt_ChocEngine(ChocEngine *pe,Instantiation *e,int i);
extern void  choco_postInstInt_ChocEngine(ChocEngine *pe,IntVar *v,int i);
extern void  choco_postInstSet_ChocEngine(ChocEngine *pe,SetVar *v,int i);
extern void  choco_postRemoveVal_ChocEngine(ChocEngine *pe,IntVar *v,int x,int i);
extern void  choco_postBoundEvent_ChocEngine(ChocEngine *pe,BoundUpdate *e,int i);
extern void  choco_postUpdateInf_ChocEngine(ChocEngine *pe,IntVar *v,int i);
extern void  choco_postUpdateSup_ChocEngine(ChocEngine *pe,IntVar *v,int i);
extern void  choco_postUpdateKer_ChocEngine(ChocEngine *pe,SetVar *v,int i);
extern void  choco_postUpdateEnv_ChocEngine(ChocEngine *pe,SetVar *v,int i);
extern ConstAwakeEventQueue * choco_getQueue_ChocEngine(ChocEngine *pe,ConstAwakeEvent *evt);
extern void  choco_registerEvent_ChocEngine(ChocEngine *pe,ConstAwakeEvent *evt);
extern ClaireBoolean * choco_postConstAwake_ChocEngine(ChocEngine *pe,AbstractConstraint *c,ClaireBoolean *init);
extern void  choco_remove_ConstAwakeEventQueue(ConstAwakeEventQueue *q,ConstAwakeEvent *evt);
extern void  choco_constAwake_AbstractConstraint(AbstractConstraint *c,ClaireBoolean *init);
extern void  choco_popSomeEvents_InstantiationStack(InstantiationStack *q);
extern void  choco_propagateEvent_Instantiation_choco(Instantiation *e);
extern void  choco_popSomeEvents_RemovalEventQueue(RemovalEventQueue *q);
extern void  choco_propagateEvent_ValueRemovals_choco(ValueRemovals *e,RemovalEventQueue *q);
extern void  choco_popSomeEvents_BoundEventQueue(BoundEventQueue *q);
extern void  choco_propagateEvent_BoundUpdate_choco(BoundUpdate *e,BoundEventQueue *q);
extern ClaireBoolean * choco_isEmpty_ConstAwakeEventQueue(ConstAwakeEventQueue *q);
extern void  choco_popSomeEvents_ConstAwakeEventQueue(ConstAwakeEventQueue *q);
extern ConstAwakeEvent * choco_popNextEvent_ConstAwakeEventQueue(ConstAwakeEventQueue *q);
extern void  choco_propagateEvent_ConstAwakeEvent_choco(ConstAwakeEvent *e);
extern void  choco_popSomeEvents_EventCollection(EventCollection *q);
extern OID  choco_getNextActiveEventQueue_PropagationEngine_choco(PropagationEngine *pe);
extern OID  choco_getNextActiveConstraintEventQueue_ChocEngine(ChocEngine *pe);
extern OID  choco_getNextActiveVariableEventQueue_ChocEngine(ChocEngine *pe);
extern OID  choco_getNextActiveEventQueue_ChocEngine_choco(ChocEngine *pe);
extern ClaireBoolean * choco_isInstantiated_AbstractVar_choco(AbstractVar *v);
extern AbstractConstraint * choco_getConstraint_AbstractVar(AbstractVar *v,int i);
extern int  choco_getDegree_AbstractVar(AbstractVar *v);
extern void  claire_self_print_IntVar_choco(IntVar *v);
extern void  choco_closeIntVar_IntVar(IntVar *v,int i,int j,int p);
extern ClaireBoolean * choco_updateInf_IntVar1_choco(IntVar *v,int x);
extern ClaireBoolean * choco_updateSup_IntVar1_choco(IntVar *v,int x);
extern ClaireBoolean * choco_instantiate_IntVar1(IntVar *v,int x);
extern ClaireBoolean * choco_removeVal_IntVar1_choco(IntVar *v,int x);
extern ClaireType * claire_domain_IntVar(IntVar *x);
extern ClaireBoolean * choco_isInstantiatedTo_IntVar_choco(IntVar *v,int x);
extern ClaireBoolean * choco_canBeInstantiatedTo_IntVar_choco(IntVar *v,int x);
extern ClaireBoolean * choco_canBeEqualTo_IntVar_choco(IntVar *v,IntVar *x);
extern ClaireBoolean * choco_domainIncludedIn_IntVar_choco(IntVar *v,list *sortedList);
extern ClaireBoolean * choco_canBeInstantiatedIn_IntVar_choco(IntVar *v,list *sortedList);
extern ClaireBoolean * choco_hasExactDomain_IntVar_choco(IntVar *v);
extern int  claire_random_IntVar(IntVar *v);
extern int  choco_getNextDomainValue_IntVar(IntVar *v,int i);
extern int  choco_getPrevDomainValue_IntVar(IntVar *v,int i);
extern int  choco_getInf_IntVar_choco(IntVar *v);
extern int  choco_getSup_IntVar_choco(IntVar *v);
extern ClaireBoolean * choco_isInstantiated_IntVar_choco(IntVar *v);
extern int  choco_getValue_IntVar(IntVar *v);
extern int  choco_getDomainSize_IntVar(IntVar *v);
extern ClaireBoolean * choco_updateInf_IntVar2_choco(IntVar *v,int x,int idx);
extern ClaireBoolean * choco_updateSup_IntVar2_choco(IntVar *v,int x,int idx);
extern ClaireBoolean * choco_removeVal_IntVar2_choco(IntVar *v,int x,int idx);
extern ClaireBoolean * choco_instantiate_IntVar2(IntVar *v,int x,int idx);
extern ClaireBoolean * choco_removeInterval_IntVar(IntVar *v,int a,int b,int idx);
extern void  choco_restrictTo_IntVar(IntVar *v,list *sortedList,int idx);
extern void  claire_self_print_SetVar_choco(SetVar *v);
extern list * choco_getDomainKernel_SetVar(SetVar *v);
extern list * choco_getDomainEnveloppe_SetVar(SetVar *v);
extern int  choco_getDomainKernelSize_SetVar(SetVar *v);
extern int  choco_getDomainEnveloppeSize_SetVar(SetVar *v);
extern int  choco_getDomainEnveloppeInf_SetVar(SetVar *v);
extern int  choco_getDomainEnveloppeSup_SetVar(SetVar *v);
extern int  choco_getDomainKernelInf_SetVar(SetVar *v);
extern int  choco_getDomainKernelSup_SetVar(SetVar *v);
extern ClaireBoolean * choco_isInstantiated_SetVar_choco(SetVar *v);
extern ClaireBoolean * choco_isInstantiatedTo_SetVar_choco(SetVar *v,list *l);
extern list * choco_getValue_SetVar(SetVar *v);
extern ClaireBoolean * choco_isInDomainEnveloppe_SetVar(SetVar *v,int x);
extern ClaireBoolean * choco_isInDomainKernel_SetVar(SetVar *v,int x);
extern ClaireBoolean * choco_canBeInstantiatedTo_SetVar_choco(SetVar *v,bag *l);
extern ClaireBoolean * choco_canBeEqualTo_SetVar_choco(SetVar *v1,SetVar *v2);
extern void  choco_addSetVar_Problem(Problem *p,SetVar *v);
extern void  choco_closeSetVar_SetVar(SetVar *v,int i,int j);
extern ClaireBoolean * choco_addToKernel_SetVar(SetVar *v,int x,int idx);
extern ClaireBoolean * choco_remFromEnveloppe_SetVar(SetVar *v,int x,int idx);
extern void  choco_setIn_SetVar(SetVar *v,int x);
extern void  choco_setOut_SetVar(SetVar *v,int x);
extern void  choco_closeLargeIntConstraint_LargeIntConstraint(LargeIntConstraint *c);
extern Delayer * choco_delay_IntConstraint(IntConstraint *c,int p);
extern void  claire_self_print_Delayer_choco(Delayer *d);
extern void  choco_closeCompositeConstraint_BinCompositeConstraint(BinCompositeConstraint *c);
extern void  choco_closeCompositeConstraint_LargeCompositeConstraint(LargeCompositeConstraint *c);
extern int  choco_getNbVarsFromSubConst_LargeCompositeConstraint(LargeCompositeConstraint *c);
extern OID  choco_connectHook_any_choco(OID x,AbstractConstraint *c);
extern OID  choco_reconnectHook_any_choco(OID x,AbstractConstraint *c);
extern OID  choco_disconnectHook_any_choco(OID x,AbstractConstraint *c);
extern int  choco_assignIndices_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_UnIntConstraint(UnIntConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_BinIntConstraint(BinIntConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_TernIntConstraint(TernIntConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_LargeIntConstraint(LargeIntConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_BinCompositeConstraint(BinCompositeConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_assignIndices_LargeCompositeConstraint(LargeCompositeConstraint *c1,AbstractConstraint *root,int i);
extern void  choco_setConstraintIndex_AbstractConstraint(AbstractConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_UnIntConstraint(UnIntConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_BinIntConstraint(BinIntConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_TernIntConstraint(TernIntConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_LargeIntConstraint(LargeIntConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_BinCompositeConstraint(BinCompositeConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_LargeCompositeConstraint(LargeCompositeConstraint *c,int i,int val);
extern void  choco_setConstraintIndex_Delayer(Delayer *c,int i,int val);
extern int  choco_getConstraintIdx_AbstractConstraint(AbstractConstraint *c,int idx);
extern int  choco_getConstraintIdx_UnIntConstraint(UnIntConstraint *c,int idx);
extern int  choco_getConstraintIdx_BinIntConstraint(BinIntConstraint *c,int idx);
extern int  choco_getConstraintIdx_TernIntConstraint(TernIntConstraint *c,int idx);
extern int  choco_getConstraintIdx_LargeIntConstraint(LargeIntConstraint *c,int idx);
extern int  choco_getConstraintIdx_BinCompositeConstraint(BinCompositeConstraint *c,int idx);
extern int  choco_getConstraintIdx_LargeCompositeConstraint(LargeCompositeConstraint *c,int idx);
extern int  choco_getConstraintIdx_Delayer(Delayer *c,int i);
extern int  choco_getPriority_AbstractConstraint_choco(AbstractConstraint *c);
extern int  choco_getPriority_Delayer_choco(Delayer *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_IntConstraint(IntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_UnIntConstraint(UnIntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_BinIntConstraint(BinIntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_TernIntConstraint(TernIntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_LargeIntConstraint(LargeIntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_BinCompositeConstraint(BinCompositeConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_LargeCompositeConstraint(LargeCompositeConstraint *c);
extern int  choco_getNbVars_AbstractConstraint(AbstractConstraint *c);
extern int  choco_getNbVars_UnIntConstraint(UnIntConstraint *c);
extern int  choco_getNbVars_BinIntConstraint(BinIntConstraint *c);
extern int  choco_getNbVars_TernIntConstraint(TernIntConstraint *c);
extern int  choco_getNbVars_LargeIntConstraint(LargeIntConstraint *c);
extern int  choco_getNbVars_LargeCompositeConstraint(LargeCompositeConstraint *c);
extern int  choco_getNbVars_BinCompositeConstraint(BinCompositeConstraint *c);
extern AbstractVar * choco_getVar_AbstractConstraint_choco(AbstractConstraint *c,int i);
extern AbstractVar * choco_getVar_UnIntConstraint_choco(UnIntConstraint *c,int i);
extern AbstractVar * choco_getVar_BinIntConstraint_choco(BinIntConstraint *c,int i);
extern AbstractVar * choco_getVar_TernIntConstraint_choco(TernIntConstraint *c,int i);
extern AbstractVar * choco_getVar_LargeIntConstraint_choco(LargeIntConstraint *c,int i);
extern AbstractVar * choco_getVar_LargeCompositeConstraint_choco(LargeCompositeConstraint *c,int i);
extern AbstractVar * choco_getVar_BinCompositeConstraint_choco(BinCompositeConstraint *c,int i);
extern void  choco_disconnect_AbstractConstraint_choco(AbstractConstraint *c);
extern void  choco_connect_AbstractConstraint_choco(AbstractConstraint *c);
extern AbstractConstraint * choco_opposite_AbstractConstraint(AbstractConstraint *c);
extern OID  choco_askIfEntailed_AbstractConstraint(AbstractConstraint *c);
extern void  choco_propagate_Ephemeral(Ephemeral *c);
extern void  choco_awakeOnInf_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awakeOnSup_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awakeOnInst_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awakeOnRem_AbstractConstraint(AbstractConstraint *c,int idx,int x);
extern void  choco_awakeOnVar_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awake_AbstractConstraint_choco(AbstractConstraint *c);
extern void  choco_awakeOnKer_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awakeOnEnv_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_awakeOnInstSet_AbstractConstraint(AbstractConstraint *c,int idx);
extern ClaireBoolean * choco_testIfSatisfied_AbstractConstraint(AbstractConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_AbstractConstraint(AbstractConstraint *c);
extern void  choco_propagate_UnIntConstraint(UnIntConstraint *c);
extern void  choco_propagate_BinIntConstraint(BinIntConstraint *c);
extern void  choco_propagate_LargeIntConstraint(LargeIntConstraint *c);
extern void  choco_doAwake_AbstractConstraint(AbstractConstraint *c);
extern void  choco_doPropagate_AbstractConstraint(AbstractConstraint *c);
extern void  choco_doAwakeOnInf_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnSup_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnInst_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnKer_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnEnv_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnInstSet_AbstractConstraint(AbstractConstraint *c,int idx);
extern void  choco_doAwakeOnRem_AbstractConstraint(AbstractConstraint *c,int idx,int x);
extern void  choco_doAwakeOnVar_AbstractConstraint(AbstractConstraint *c,int idx);
extern OID  choco_askIfTrue_AbstractConstraint(AbstractConstraint *c);
extern ClaireBoolean * choco_testIfTrue_AbstractConstraint(AbstractConstraint *c);
extern ClaireBoolean * choco_testIfInstantiated_AbstractConstraint(AbstractConstraint *c);
extern ClaireBoolean * choco_abstractIncInf_AbstractConstraint(AbstractConstraint *c,int i);
extern ClaireBoolean * choco_abstractDecSup_AbstractConstraint(AbstractConstraint *c,int i);
extern ClaireBoolean * choco_abstractInstantiate_AbstractConstraint(AbstractConstraint *c,int i);
extern ClaireBoolean * choco_abstractRemoveVal_AbstractConstraint(AbstractConstraint *c,int i,int x);
extern void  choco_awakeOnInf_Delayer(Delayer *d,int idx);
extern void  choco_awakeOnSup_Delayer(Delayer *d,int idx);
extern void  choco_awakeOnInst_Delayer(Delayer *d,int idx);
extern void  choco_awakeOnRem_Delayer(Delayer *d,int idx,int x);
extern ClaireBoolean * choco_testIfSatisfied_Delayer(Delayer *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_Delayer(Delayer *c);
extern int  choco_getNbVars_Delayer(Delayer *c);
extern OID  choco_askIfEntailed_Delayer(Delayer *c);
extern void  choco_propagate_Delayer(Delayer *c);
extern void  choco_awake_Delayer_choco(Delayer *c);
extern AbstractVar * choco_getVar_Delayer_choco(Delayer *c,int i);
extern void  choco_addIntVar_Problem(Problem *p,IntVar *v);
extern void  choco_connect_IntConstraint_choco(IntConstraint *c);
extern void  choco_connect_UnIntConstraint_choco(UnIntConstraint *c);
extern void  choco_connect_BinIntConstraint_choco(BinIntConstraint *c);
extern void  choco_connect_TernIntConstraint_choco(TernIntConstraint *c);
extern void  choco_connect_LargeIntConstraint_choco(LargeIntConstraint *c);
extern void  choco_connect_CompositeConstraint_choco(CompositeConstraint *c);
extern void  choco_connect_Delayer_choco(Delayer *d);
extern void  choco_connectIntVar_AbstractConstraint1(AbstractConstraint *cont,IntVar *u,int i);
extern void  choco_connectIntVar_AbstractConstraint2(AbstractConstraint *cont,IntVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem);
extern void  choco_connectIntVarEvents_IntVar_choco(IntVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem);
extern void  choco_connectEvent_VarEvent(VarEvent *e,int nbconst,ClaireBoolean *active);
extern void  choco_disconnectIntVar_IntVar(IntVar *u,int i);
extern void  choco_disconnectIntVarEvents_IntVar_choco(IntVar *u,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnInf,ClaireBoolean *passiveOnSup,ClaireBoolean *passiveOnRem);
extern void  choco_disconnectEvent_VarEvent(VarEvent *e,int i);
extern void  choco_connectSetVar_AbstractConstraint1(AbstractConstraint *cont,SetVar *u,int i);
extern void  choco_connectSetVar_AbstractConstraint2(AbstractConstraint *cont,SetVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv);
extern void  choco_connectSetVarEvents_SetVar_choco(SetVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv);
extern void  choco_disconnectSetVar_SetVar(SetVar *u,int i);
extern void  choco_disconnectSetVarEvents_SetVar_choco(SetVar *u,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnKer,ClaireBoolean *passiveOnEnv);
extern void  choco_reconnectSetVar_SetVar(SetVar *u,int i);
extern void  choco_reconnectSetVarEvents_SetVar_choco(SetVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv);
extern void  choco_disconnect_UnIntConstraint_choco(UnIntConstraint *c);
extern void  choco_disconnect_BinIntConstraint_choco(BinIntConstraint *c);
extern void  choco_disconnect_TernIntConstraint_choco(TernIntConstraint *c);
extern void  choco_disconnect_LargeIntConstraint_choco(LargeIntConstraint *c);
extern void  choco_disconnect_Delayer_choco(Delayer *c);
extern void  choco_reconnect_UnIntConstraint_choco(UnIntConstraint *c);
extern void  choco_reconnect_BinIntConstraint_choco(BinIntConstraint *c);
extern void  choco_reconnect_TernIntConstraint_choco(TernIntConstraint *c);
extern void  choco_reconnect_LargeIntConstraint_choco(LargeIntConstraint *c);
extern void  choco_reconnect_AbstractConstraint_choco(AbstractConstraint *c);
extern void  choco_reconnectIntVar_IntVar(IntVar *v,int idx);
extern void  choco_reconnectIntVarEvents_IntVar_choco(IntVar *u,int idx,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem);
extern void  choco_reconnectEvent_VarEvent(VarEvent *e,int idx);
extern ClaireBoolean * choco_isActive_AbstractConstraint_choco(AbstractConstraint *c);
extern ClaireBoolean * choco_isActive_UnIntConstraint_choco(UnIntConstraint *c);
extern ClaireBoolean * choco_isActive_BinIntConstraint_choco(BinIntConstraint *c);
extern ClaireBoolean * choco_isActive_TernIntConstraint_choco(TernIntConstraint *c);
extern ClaireBoolean * choco_isActive_LargeIntConstraint_choco(LargeIntConstraint *c);
extern ClaireBoolean * choco_isActive_Delayer_choco(Delayer *d);
extern ClaireBoolean * choco_isActive_BinCompositeConstraint_choco(BinCompositeConstraint *c);
extern ClaireBoolean * choco_isActive_LargeCompositeConstraint_choco(LargeCompositeConstraint *c);
extern ClaireBoolean * choco_isActiveIntVar_IntVar(IntVar *u,int i);
extern ClaireBoolean * choco_isActiveSetVar_SetVar(SetVar *u,int i);
extern ClaireBoolean * choco_isActiveEvent_VarEvent(VarEvent *e,int i);
extern ClaireBoolean * choco_getTruthValue_BinRelation(BinRelation *tt,int x,int y);
extern ClaireBoolean * choco_getTruthValue_TruthTest(TruthTest *tt,int x,int y);
extern void  claire_self_print_TruthTest_choco(TruthTest *tt);
extern ClaireBoolean * choco_getTruthValue_TruthTable2D(TruthTable2D *dtt,int x,int y);
extern void  choco_setTruePair_TruthTable2D(TruthTable2D *dtt,int x,int y);
extern void  claire_self_print_TruthTable2D_choco(TruthTable2D *tt);
extern void  claire_self_print_CSPBinConstraint_choco(CSPBinConstraint *c);
extern ClaireBoolean * choco_testIfSatisfied_CSPBinConstraint(CSPBinConstraint *c);
extern CSPBinConstraint * choco_opposite_CSPBinConstraint(CSPBinConstraint *c);
extern void  choco_awakeOnInst_CSPBinConstraint(CSPBinConstraint *c,int idx);
extern void  choco_see_CSPBinConstraint(CSPBinConstraint *c);
extern AC3BinConstraint * choco_makeAC3BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel);
extern void  choco_reviseV2_AC3BinConstraint(AC3BinConstraint *c);
extern void  choco_reviseV1_AC3BinConstraint(AC3BinConstraint *c);
extern void  choco_awakeOnInf_AC3BinConstraint(AC3BinConstraint *c,int idx);
extern void  choco_awakeOnSup_AC3BinConstraint(AC3BinConstraint *c,int idx);
extern void  choco_awakeOnRem_AC3BinConstraint(AC3BinConstraint *c,int idx,int oldval);
extern void  choco_awakeOnVar_AC3BinConstraint(AC3BinConstraint *c,int idx);
extern void  choco_propagate_AC3BinConstraint(AC3BinConstraint *c);
extern AC4BinConstraint * choco_makeAC4BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel);
extern void  choco_decrementNbSupportV1_AC4BinConstraint(AC4BinConstraint *c,int x);
extern void  choco_decrementNbSupportV2_AC4BinConstraint(AC4BinConstraint *c,int y);
extern void  choco_resetNbSupportV1_AC4BinConstraint(AC4BinConstraint *c,int x);
extern void  choco_resetNbSupportV2_AC4BinConstraint(AC4BinConstraint *c,int y);
extern void  choco_propagate_AC4BinConstraint(AC4BinConstraint *c);
extern void  choco_awakeOnRem_AC4BinConstraint(AC4BinConstraint *c,int idx,int oldval);
extern void  choco_awakeOnInst_AC4BinConstraint(AC4BinConstraint *c,int idx);
extern void  choco_awakeOnInf_AC4BinConstraint(AC4BinConstraint *c,int idx);
extern void  choco_awakeOnSup_AC4BinConstraint(AC4BinConstraint *c,int idx);
extern void  choco_awakeOnVar_AC4BinConstraint(AC4BinConstraint *c,int idx);
extern AC2001BinConstraint * choco_makeAC2001BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel);
extern void  choco_reviseV2_AC2001BinConstraint(AC2001BinConstraint *c);
extern void  choco_reviseV1_AC2001BinConstraint(AC2001BinConstraint *c);
extern void  choco_updateSupportVal1_AC2001BinConstraint(AC2001BinConstraint *c,int x);
extern void  choco_updateSupportVal2_AC2001BinConstraint(AC2001BinConstraint *c,int y);
extern void  choco_awakeOnInf_AC2001BinConstraint(AC2001BinConstraint *c,int idx);
extern void  choco_awakeOnSup_AC2001BinConstraint(AC2001BinConstraint *c,int idx);
extern void  choco_awakeOnInst_AC2001BinConstraint(AC2001BinConstraint *c,int idx);
extern void  choco_awakeOnRem_AC2001BinConstraint(AC2001BinConstraint *c,int idx,int oldval);
extern void  choco_awakeOnVar_AC2001BinConstraint(AC2001BinConstraint *c,int idx);
extern void  choco_propagate_AC2001BinConstraint(AC2001BinConstraint *c);
extern void  choco_awake_AC2001BinConstraint_choco(AC2001BinConstraint *c);
extern CSPLargeConstraint * choco_tupleConstraint_list(list *lvars,ClaireBoolean *feasible,list *mytuples);
extern void  choco_awakeOnInf_CSPLargeConstraint(CSPLargeConstraint *c,int idx);
extern void  choco_awakeOnSup_CSPLargeConstraint(CSPLargeConstraint *c,int idx);
extern void  choco_awakeOnRem_CSPLargeConstraint(CSPLargeConstraint *c,int idx,int oldval);
extern void  choco_awakeOnInst_CSPLargeConstraint(CSPLargeConstraint *c,int idx);
extern void  choco_propagate_CSPLargeConstraint(CSPLargeConstraint *c);
extern void  claire_self_print_GreaterOrEqualxc_choco(GreaterOrEqualxc *c);
extern void  claire_self_print_LessOrEqualxc_choco(LessOrEqualxc *c);
extern void  claire_self_print_Equalxc_choco(Equalxc *c);
extern void  claire_self_print_NotEqualxc_choco(NotEqualxc *c);
extern void  choco_awakeOnInf_Equalxc(Equalxc *c,int i);
extern void  choco_awakeOnSup_Equalxc(Equalxc *c,int i);
extern void  choco_awakeOnInst_Equalxc(Equalxc *c,int i);
extern void  choco_propagate_Equalxc(Equalxc *c);
extern OID  choco_askIfEntailed_Equalxc(Equalxc *c);
extern ClaireBoolean * choco_testIfSatisfied_Equalxc(Equalxc *c);
extern void  choco_awakeOnInf_GreaterOrEqualxc(GreaterOrEqualxc *c,int i);
extern void  choco_awakeOnSup_GreaterOrEqualxc(GreaterOrEqualxc *c,int i);
extern void  choco_awakeOnInst_GreaterOrEqualxc(GreaterOrEqualxc *c,int i);
extern void  choco_propagate_GreaterOrEqualxc(GreaterOrEqualxc *c);
extern OID  choco_askIfEntailed_GreaterOrEqualxc(GreaterOrEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_GreaterOrEqualxc(GreaterOrEqualxc *c);
extern void  choco_awakeOnInf_LessOrEqualxc(LessOrEqualxc *c,int i);
extern void  choco_awakeOnSup_LessOrEqualxc(LessOrEqualxc *c,int i);
extern void  choco_awakeOnInst_LessOrEqualxc(LessOrEqualxc *c,int i);
extern void  choco_propagate_LessOrEqualxc(LessOrEqualxc *c);
extern OID  choco_askIfEntailed_LessOrEqualxc(LessOrEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_LessOrEqualxc(LessOrEqualxc *c);
extern void  choco_awakeOnInf_NotEqualxc(NotEqualxc *c,int idx);
extern void  choco_awakeOnSup_NotEqualxc(NotEqualxc *c,int idx);
extern void  choco_awakeOnInst_NotEqualxc(NotEqualxc *c,int idx);
extern void  choco_propagate_NotEqualxc(NotEqualxc *c);
extern OID  choco_askIfEntailed_NotEqualxc(NotEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_NotEqualxc(NotEqualxc *c);
extern void  claire_self_print_Equalxyc_choco(Equalxyc *c);
extern void  claire_self_print_NotEqualxyc_choco(NotEqualxyc *c);
extern void  claire_self_print_GreaterOrEqualxyc_choco(GreaterOrEqualxyc *c);
extern void  choco_propagate_Equalxyc(Equalxyc *c);
extern OID  choco_askIfEntailed_Equalxyc(Equalxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_Equalxyc(Equalxyc *c);
extern void  choco_awakeOnInf_Equalxyc(Equalxyc *c,int idx);
extern void  choco_awakeOnSup_Equalxyc(Equalxyc *c,int idx);
extern void  choco_awakeOnInst_Equalxyc(Equalxyc *c,int idx);
extern void  choco_awakeOnRem_Equalxyc(Equalxyc *c,int idx,int x);
extern void  choco_propagate_NotEqualxyc(NotEqualxyc *cont);
extern void  choco_awakeOnInf_NotEqualxyc(NotEqualxyc *c,int idx);
extern void  choco_awakeOnSup_NotEqualxyc(NotEqualxyc *c,int idx);
extern void  choco_awakeOnInst_NotEqualxyc(NotEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_NotEqualxyc(NotEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_NotEqualxyc(NotEqualxyc *c);
extern void  choco_propagate_GreaterOrEqualxyc(GreaterOrEqualxyc *cont);
extern void  choco_awakeOnInf_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx);
extern void  choco_awakeOnSup_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx);
extern void  choco_awakeOnInst_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_GreaterOrEqualxyc(GreaterOrEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_GreaterOrEqualxyc(GreaterOrEqualxyc *c);
extern void  claire_self_print_LinComb_choco(LinComb *self);
extern LinComb * choco_makeLinComb_list(list *l1,list *l2,int c,int opn);
extern int  choco_getNbVars_LinComb(LinComb *c);
extern int  choco_assignIndices_LinComb(LinComb *c,AbstractConstraint *root,int i);
extern LinComb * choco_opposite_LinComb(LinComb *c);
extern int  choco_computeVarIdxInOpposite_LinComb(LinComb *c,int i);
extern int  choco_computeUpperBound_LinComb_choco(LinComb *c);
extern int  choco_computeLowerBound_LinComb_choco(LinComb *c);
extern ClaireBoolean * choco_propagateNewLowerBound_LinComb_choco(LinComb *c,int mylb);
extern ClaireBoolean * choco_propagateNewUpperBound_LinComb_choco(LinComb *c,int myub);
extern ClaireBoolean * choco_filterOnImprovedLowerBound_LinComb(LinComb *c);
extern ClaireBoolean * choco_filterOnImprovedUpperBound_LinComb(LinComb *c);
extern void  choco_filter_LinComb(LinComb *c,ClaireBoolean *startWithLB,int minNbRules);
extern void  choco_propagate_LinComb(LinComb *c);
extern void  choco_awake_LinComb_choco(LinComb *c);
extern OID  choco_askIfEntailed_LinComb(LinComb *c);
extern ClaireBoolean * choco_testIfSatisfied_LinComb(LinComb *c);
extern ClaireBoolean * choco_abstractIncInf_LinComb(LinComb *c,int i);
extern ClaireBoolean * choco_abstractDecSup_LinComb(LinComb *c,int i);
extern ClaireBoolean * choco_abstractInstantiate_LinComb(LinComb *c,int i);
extern ClaireBoolean * choco_abstractRemoveVal_LinComb(LinComb *c,int i,int v);
extern void  choco_awakeOnInf_LinComb(LinComb *c,int idx);
extern void  choco_awakeOnSup_LinComb(LinComb *c,int idx);
extern void  choco_awakeOnInst_LinComb(LinComb *c,int idx);
extern void  claire_self_print_Elt_choco(Elt *c);
extern Elt * choco_makeEltConstraint_list(list *l,IntVar *i,IntVar *x,int offset);
extern void  choco_updateValueFromIndex_Elt(Elt *c);
extern void  choco_updateIndexFromValue_Elt(Elt *c);
extern void  choco_awake_Elt_choco(Elt *c);
extern void  choco_propagate_Elt(Elt *c);
extern void  choco_awakeOnInf_Elt(Elt *c,int idx);
extern void  choco_awakeOnSup_Elt(Elt *c,int idx);
extern void  choco_awakeOnInst_Elt(Elt *c,int idx);
extern void  choco_awakeOnRem_Elt(Elt *c,int idx,int x);
extern OID  choco_askIfEntailed_Elt(Elt *c);
extern ClaireBoolean * choco_testIfSatisfied_Elt(Elt *c);
extern void  claire_self_print_Elt2_choco(Elt2 *c);
extern Elt2 * choco_makeElt2Constraint_table(table *l,int n,int m,IntVar *i,IntVar *j,IntVar *x);
extern void  choco_updateValueFromIndex_Elt2(Elt2 *c);
extern void  choco_updateIndexFromValue_Elt2(Elt2 *c);
extern void  choco_awake_Elt2_choco(Elt2 *c);
extern void  choco_propagate_Elt2(Elt2 *c);
extern void  choco_awakeOnInf_Elt2(Elt2 *c,int idx);
extern void  choco_awakeOnSup_Elt2(Elt2 *c,int idx);
extern void  choco_awakeOnInst_Elt2(Elt2 *c,int idx);
extern void  choco_awakeOnRem_Elt2(Elt2 *c,int idx,int x);
extern OID  choco_askIfEntailed_Elt2(Elt2 *c);
extern ClaireBoolean * choco_testIfSatisfied_Elt2(Elt2 *c);
extern void  claire_self_print_AllDiff_choco(AllDiff *self);
extern void  choco_awakeOnInf_AllDiff(AllDiff *c,int idx);
extern void  choco_awakeOnSup_AllDiff(AllDiff *c,int idx);
extern void  choco_awakeOnRem_AllDiff(AllDiff *c,int idx,int x);
extern void  choco_awakeOnInst_AllDiff(AllDiff *c,int idx);
extern void  choco_propagate_AllDiff(AllDiff *c);
extern ClaireBoolean * choco_testIfSatisfied_AllDiff(AllDiff *c);
extern OID  choco_askIfEntailed_AllDiff(AllDiff *c);
extern void  claire_self_print_Occurrence_choco(Occurrence *self);
extern void  choco_checkNbPossible_Occurrence_choco(Occurrence *c);
extern void  choco_checkNbSure_Occurrence_choco(Occurrence *c);
extern void  choco_update_Occurrence(Occurrence *c);
extern void  choco_propagate_Occurrence(Occurrence *c);
extern void  choco_awake_Occurrence_choco(Occurrence *c);
extern void  choco_awakeOnInf_Occurrence(Occurrence *c,int idx);
extern void  choco_awakeOnSup_Occurrence(Occurrence *c,int idx);
extern void  choco_awakeOnInst_Occurrence(Occurrence *c,int idx);
extern void  choco_awakeOnRem_Occurrence(Occurrence *c,int idx,int x);
extern ClaireBoolean * choco_testIfSatisfied_Occurrence(Occurrence *c);
extern OID  choco_askIfEntailed_Occurrence(Occurrence *c);
extern void  choco_closeBoolConstraint_BinCompositeConstraint(BinCompositeConstraint *c);
extern ClaireBoolean * choco_knownStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i);
extern ClaireBoolean * choco_getStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i);
extern ClaireBoolean * choco_knownTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i);
extern ClaireBoolean * choco_getTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i);
extern void  choco_setStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i,ClaireBoolean *b);
extern void  choco_setTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i,ClaireBoolean *b);
extern void  choco_closeBoolConstraint_LargeCompositeConstraint(LargeCompositeConstraint *c);
extern ClaireBoolean * choco_knownStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i);
extern ClaireBoolean * choco_getStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i);
extern ClaireBoolean * choco_knownTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i);
extern ClaireBoolean * choco_getTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i);
extern void  choco_setStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i,ClaireBoolean *b);
extern void  choco_setTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i,ClaireBoolean *b);
extern int  choco_assignIndices_BinBoolConstraintWCounterOpp(BinBoolConstraintWCounterOpp *c1,AbstractConstraint *root,int i);
extern void  choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp(BinBoolConstraintWCounterOpp *c);
extern int  choco_assignIndices_LargeBoolConstraintWCounterOpp(LargeBoolConstraintWCounterOpp *c1,AbstractConstraint *root,int i);
extern void  choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp(LargeBoolConstraintWCounterOpp *c);
extern void  claire_self_print_Disjunction_choco(Disjunction *d);
extern void  choco_checkStatus_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnInf_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnSup_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnInst_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnRem_Disjunction(Disjunction *d,int i,int v);
extern void  choco_awakeOnEnv_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnKer_Disjunction(Disjunction *d,int i);
extern void  choco_awakeOnInstSet_Disjunction(Disjunction *d,int i);
extern OID  choco_askIfEntailed_Disjunction(Disjunction *d);
extern ClaireBoolean * choco_testIfSatisfied_Disjunction(Disjunction *d);
extern void  choco_propagate_Disjunction(Disjunction *d);
extern void  claire_self_print_Guard_choco(Guard *g);
extern void  choco_checkStatus_Guard(Guard *g,int i);
extern void  choco_awakeOnInf_Guard(Guard *g,int i);
extern void  choco_awakeOnSup_Guard(Guard *g,int i);
extern void  choco_awakeOnInst_Guard(Guard *g,int i);
extern void  choco_awakeOnRem_Guard(Guard *g,int i,int v);
extern void  choco_awakeOnKer_Guard(Guard *g,int i);
extern void  choco_awakeOnEnv_Guard(Guard *g,int i);
extern void  choco_awakeOnInstSet_Guard(Guard *g,int i);
extern void  choco_propagate_Guard(Guard *g);
extern void  choco_awake_Guard_choco(Guard *g);
extern OID  choco_askIfEntailed_Guard(Guard *g);
extern ClaireBoolean * choco_testIfSatisfied_Guard(Guard *g);
extern void  claire_self_print_Equiv_choco(Equiv *c);
extern void  choco_checkStatus_Equiv(Equiv *c,int i);
extern void  choco_awakeOnInf_Equiv(Equiv *c,int i);
extern void  choco_awakeOnSup_Equiv(Equiv *c,int i);
extern void  choco_awakeOnInst_Equiv(Equiv *c,int i);
extern void  choco_awakeOnRem_Equiv(Equiv *c,int i,int v);
extern void  choco_awakeOnKer_Equiv(Equiv *c,int i);
extern void  choco_awakeOnEnv_Equiv(Equiv *c,int i);
extern void  choco_awakeOnInstSet_Equiv(Equiv *c,int i);
extern void  choco_propagate_Equiv(Equiv *c);
extern OID  choco_askIfEntailed_Equiv(Equiv *c);
extern ClaireBoolean * choco_testIfSatisfied_Equiv(Equiv *c);
extern void  claire_self_print_Conjunction_choco(Conjunction *c);
extern void  choco_awakeOnInf_Conjunction(Conjunction *c,int i);
extern void  choco_awakeOnSup_Conjunction(Conjunction *c,int i);
extern void  choco_awakeOnInst_Conjunction(Conjunction *c,int i);
extern void  choco_awakeOnRem_Conjunction(Conjunction *c,int i,int v);
extern void  choco_awakeOnKer_Conjunction(Conjunction *c,int i);
extern void  choco_awakeOnEnv_Conjunction(Conjunction *c,int i);
extern void  choco_awakeOnInstSet_Conjunction(Conjunction *c,int i);
extern void  choco_propagate_Conjunction(Conjunction *c);
extern void  choco_awake_Conjunction_choco(Conjunction *c);
extern OID  choco_askIfEntailed_Conjunction(Conjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_Conjunction(Conjunction *c);
extern void  claire_self_print_LargeConjunction_choco(LargeConjunction *c);
extern void  choco_awakeOnInf_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_awakeOnSup_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_awakeOnInst_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_awakeOnRem_LargeConjunction(LargeConjunction *c,int i,int v);
extern void  choco_awakeOnKer_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_awakeOnEnv_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_awakeOnInstSet_LargeConjunction(LargeConjunction *c,int i);
extern void  choco_propagate_LargeConjunction(LargeConjunction *c);
extern void  choco_awake_LargeConjunction_choco(LargeConjunction *c);
extern OID  choco_askIfEntailed_LargeConjunction(LargeConjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_LargeConjunction(LargeConjunction *c);
extern void  claire_self_print_LargeDisjunction_choco(LargeDisjunction *c);
extern void  choco_checkNbFalseStatus_LargeDisjunction(LargeDisjunction *d);
extern void  choco_checkStatus_LargeDisjunction(LargeDisjunction *d,int i);
extern void  choco_awakeOnInf_LargeDisjunction(LargeDisjunction *ld,int i);
extern void  choco_awakeOnSup_LargeDisjunction(LargeDisjunction *ld,int i);
extern void  choco_awakeOnInst_LargeDisjunction(LargeDisjunction *ld,int i);
extern void  choco_awakeOnRem_LargeDisjunction(LargeDisjunction *ld,int i,int v);
extern void  choco_awakeOnKer_LargeDisjunction(LargeDisjunction *ld,int i);
extern void  choco_awakeOnEnv_LargeDisjunction(LargeDisjunction *ld,int i);
extern void  choco_awakeOnInstSet_LargeDisjunction(LargeDisjunction *ld,int i);
extern OID  choco_askIfEntailed_LargeDisjunction(LargeDisjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_LargeDisjunction(LargeDisjunction *c);
extern void  choco_propagate_LargeDisjunction(LargeDisjunction *d);
extern void  choco_awake_LargeDisjunction_choco(LargeDisjunction *d);
extern IntVar * choco_getCardVar_Cardinality(Cardinality *c);
extern int  choco_getCardVarIdx_Cardinality(Cardinality *c);
extern void  claire_self_print_Cardinality_choco(Cardinality *c);
extern void  choco_awakeOnNbTrue_Cardinality(Cardinality *c);
extern void  choco_awakeOnNbFalse_Cardinality(Cardinality *c);
extern void  choco_checkStatus_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnInf_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnSup_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnInst_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnRem_Cardinality(Cardinality *c,int i,int v);
extern void  choco_awakeOnKer_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnEnv_Cardinality(Cardinality *c,int i);
extern void  choco_awakeOnInstSet_Cardinality(Cardinality *c,int i);
extern void  choco_awake_Cardinality_choco(Cardinality *c);
extern void  choco_propagate_Cardinality(Cardinality *c);
extern ClaireBoolean * choco_testIfSatisfied_Cardinality(Cardinality *c);
extern OID  choco_askIfEntailed_Cardinality(Cardinality *c);
extern Conjunction * choco_opposite_Guard(Guard *c);
extern Disjunction * choco_opposite_Conjunction(Conjunction *c);
extern Conjunction * choco_opposite_Disjunction(Disjunction *c);
extern LargeConjunction * choco_opposite_LargeDisjunction(LargeDisjunction *c);
extern LargeDisjunction * choco_opposite_LargeConjunction(LargeConjunction *c);
extern LessOrEqualxc * choco_opposite_GreaterOrEqualxc(GreaterOrEqualxc *c);
extern GreaterOrEqualxc * choco_opposite_LessOrEqualxc(LessOrEqualxc *c);
extern NotEqualxc * choco_opposite_Equalxc(Equalxc *c);
extern Equalxc * choco_opposite_NotEqualxc(NotEqualxc *c);
extern NotEqualxyc * choco_opposite_Equalxyc(Equalxyc *c);
extern Equalxyc * choco_opposite_NotEqualxyc(NotEqualxyc *c);
extern GreaterOrEqualxyc * choco_opposite_GreaterOrEqualxyc(GreaterOrEqualxyc *c);
extern int  choco_computeVarIdxInOpposite_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int i);
extern int  choco_computeVarIdxInOpposite_AbstractConstraint(AbstractConstraint *c,int i);
extern int  choco_computeVarIdxInOpposite_IntConstraint(IntConstraint *c,int i);
extern int  choco_computeVarIdxInOpposite_BinCompositeConstraint(BinCompositeConstraint *c,int i);
extern int  choco_computeVarIdxInOpposite_LargeCompositeConstraint(LargeCompositeConstraint *c,int i);
extern int  choco_computeVarIdxInOpposite_SetConstraint(SetConstraint *c,int i);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_UnSetConstraint(UnSetConstraint *c);
extern int  choco_getNbVars_UnSetConstraint(UnSetConstraint *c);
extern AbstractVar * choco_getVar_UnSetConstraint_choco(UnSetConstraint *c,int i);
extern void  choco_connect_UnSetConstraint_choco(UnSetConstraint *c);
extern void  choco_disconnect_UnSetConstraint_choco(UnSetConstraint *c);
extern void  choco_reconnect_UnSetConstraint_choco(UnSetConstraint *c);
extern int  choco_assignIndices_UnSetConstraint(UnSetConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_getConstraintIdx_UnSetConstraint(UnSetConstraint *c,int idx);
extern void  choco_setConstraintIndex_UnSetConstraint(UnSetConstraint *c,int i,int val);
extern ClaireBoolean * choco_isActive_UnSetConstraint_choco(UnSetConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_BinSetIntConstraint(BinSetIntConstraint *c);
extern int  choco_getNbVars_BinSetIntConstraint(BinSetIntConstraint *c);
extern AbstractVar * choco_getVar_BinSetIntConstraint_choco(BinSetIntConstraint *c,int i);
extern void  choco_connect_BinSetIntConstraint_choco(BinSetIntConstraint *c);
extern void  choco_disconnect_BinSetIntConstraint_choco(BinSetIntConstraint *c);
extern void  choco_reconnect_BinSetIntConstraint_choco(BinSetIntConstraint *c);
extern int  choco_assignIndices_BinSetIntConstraint(BinSetIntConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_getConstraintIdx_BinSetIntConstraint(BinSetIntConstraint *c,int idx);
extern void  choco_setConstraintIndex_BinSetIntConstraint(BinSetIntConstraint *c,int i,int val);
extern ClaireBoolean * choco_isActive_BinSetIntConstraint_choco(BinSetIntConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_BinSetConstraint(BinSetConstraint *c);
extern int  choco_getNbVars_BinSetConstraint(BinSetConstraint *c);
extern AbstractVar * choco_getVar_BinSetConstraint_choco(BinSetConstraint *c,int i);
extern void  choco_connect_BinSetConstraint_choco(BinSetConstraint *c);
extern void  choco_disconnect_BinSetConstraint_choco(BinSetConstraint *c);
extern void  choco_reconnect_BinSetConstraint_choco(BinSetConstraint *c);
extern int  choco_assignIndices_BinSetConstraint(BinSetConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_getConstraintIdx_BinSetConstraint(BinSetConstraint *c,int idx);
extern void  choco_setConstraintIndex_BinSetConstraint(BinSetConstraint *c,int i,int val);
extern ClaireBoolean * choco_isActive_BinSetConstraint_choco(BinSetConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_TernSetConstraint(TernSetConstraint *c);
extern int  choco_getNbVars_TernSetConstraint(TernSetConstraint *c);
extern AbstractVar * choco_getVar_TernSetConstraint_choco(TernSetConstraint *c,int i);
extern void  choco_connect_TernSetConstraint_choco(TernSetConstraint *c);
extern void  choco_disconnect_TernSetConstraint_choco(TernSetConstraint *c);
extern void  choco_reconnect_TernSetConstraint_choco(TernSetConstraint *c);
extern int  choco_assignIndices_TernSetConstraint(TernSetConstraint *c1,AbstractConstraint *root,int i);
extern int  choco_getConstraintIdx_TernSetConstraint(TernSetConstraint *c,int idx);
extern void  choco_setConstraintIndex_TernSetConstraint(TernSetConstraint *c,int i,int val);
extern ClaireBoolean * choco_isActive_TernSetConstraint_choco(TernSetConstraint *c);
extern void  choco_awakeOnKer_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnEnv_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnInstSet_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnInf_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnSup_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnInst_SetCard(SetCard *c,int idx);
extern void  choco_awakeOnRem_SetCard(SetCard *c,int idx,int val);
extern void  choco_awakeOnVar_SetCard(SetCard *c,int idx);
extern OID  choco_askIfEntailed_SetCard(SetCard *c);
extern ClaireBoolean * choco_testIfSatisfied_SetCard(SetCard *c);
extern void  choco_propagate_SetCard(SetCard *c);
extern void  choco_awake_SetCard_choco(SetCard *c);
extern SetCard * choco_setCard_SetVar(SetVar *sv,IntVar *iv);
extern void  choco_awakeOnKer_MemberC(MemberC *c,int idx);
extern void  choco_awakeOnEnv_MemberC(MemberC *c,int idx);
extern void  choco_awakeOnInstSet_MemberC(MemberC *c,int idx);
extern OID  choco_askIfEntailed_MemberC(MemberC *c);
extern ClaireBoolean * choco_testIfSatisfied_MemberC(MemberC *c);
extern void  choco_propagate_MemberC(MemberC *c);
extern void  choco_awake_MemberC_choco(MemberC *c);
extern MemberC * choco_memberOf_SetVar1(SetVar *sv,int a);
extern void  choco_awakeOnKer_NotMemberC(NotMemberC *c,int idx);
extern void  choco_awakeOnEnv_NotMemberC(NotMemberC *c,int idx);
extern void  choco_awakeOnInstSet_NotMemberC(NotMemberC *c,int idx);
extern OID  choco_askIfEntailed_NotMemberC(NotMemberC *c);
extern ClaireBoolean * choco_testIfSatisfied_NotMemberC(NotMemberC *c);
extern void  choco_propagate_NotMemberC(NotMemberC *c);
extern void  choco_awake_NotMemberC_choco(NotMemberC *c);
extern NotMemberC * choco_notMemberOf_SetVar1(SetVar *sv,int a);
extern NotMemberC * choco_opposite_MemberC(MemberC *c);
extern MemberC * choco_opposite_NotMemberC(NotMemberC *c);
extern void  choco_awakeOnKer_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnEnv_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnInstSet_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnInst_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnVar_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnSup_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnInf_MemberX(MemberX *c,int idx);
extern void  choco_awakeOnRem_MemberX(MemberX *c,int idx,int val);
extern OID  choco_askIfEntailed_MemberX(MemberX *c);
extern ClaireBoolean * choco_testIfSatisfied_MemberX(MemberX *c);
extern void  choco_propagate_MemberX(MemberX *c);
extern void  choco_awake_MemberX_choco(MemberX *c);
extern MemberX * choco_memberOf_SetVar2(SetVar *sv,IntVar *v);
extern void  choco_awakeOnKer_NotMemberX(NotMemberX *c,int idx);
extern void  choco_awakeOnEnv_NotMemberX(NotMemberX *c,int idx);
extern void  choco_awakeOnInstSet_NotMemberX(NotMemberX *c,int idx);
extern void  choco_awakeOnSup_NotMemberX(NotMemberX *c,int idx);
extern void  choco_awakeOnInst_NotMemberX(NotMemberX *c,int idx);
extern void  choco_awakeOnRem_NotMemberX(NotMemberX *c,int idx,int val);
extern void  choco_awakeOnVar_NotMemberX(NotMemberX *c,int idx);
extern OID  choco_askIfEntailed_NotMemberX(NotMemberX *c);
extern ClaireBoolean * choco_testIfSatisfied_NotMemberX(NotMemberX *c);
extern void  choco_propagate_NotMemberX(NotMemberX *c);
extern void  choco_awake_NotMemberX_choco(NotMemberX *c);
extern NotMemberX * choco_opposite_MemberX(MemberX *c);
extern MemberX * choco_opposite_NotMemberX(NotMemberX *c);
extern NotMemberX * choco_notMemberOf_SetVar2(SetVar *sv,IntVar *v);
extern void  choco_awakeOnKer_SetIntersection(SetIntersection *c,int idx);
extern void  choco_awakeOnEnv_SetIntersection(SetIntersection *c,int idx);
extern void  choco_awakeOnInstSet_SetIntersection(SetIntersection *c,int idx);
extern ClaireBoolean * choco_testIfSatisfied_SetIntersection(SetIntersection *c);
extern void  choco_propagate_SetIntersection(SetIntersection *c);
extern void  choco_awake_SetIntersection_choco(SetIntersection *c);
extern SetIntersection * choco_setintersection_SetVar(SetVar *s1,SetVar *s2,SetVar *s3);
extern void  choco_awakeOnKer_SetUnion(SetUnion *c,int idx);
extern void  choco_awakeOnEnv_SetUnion(SetUnion *c,int idx);
extern void  choco_awakeOnInstSet_SetUnion(SetUnion *c,int idx);
extern ClaireBoolean * choco_testIfSatisfied_SetUnion(SetUnion *c);
extern void  choco_propagate_SetUnion(SetUnion *c);
extern void  choco_awake_SetUnion_choco(SetUnion *c);
extern SetUnion * choco_setunion_SetVar(SetVar *s1,SetVar *s2,SetVar *s3);
extern void  choco_awakeOnKer_SubSet(SubSet *c,int idx);
extern void  choco_awakeOnEnv_SubSet(SubSet *c,int idx);
extern void  choco_awakeOnInstSet_SubSet(SubSet *c,int idx);
extern OID  choco_askIfEntailed_SubSet(SubSet *c);
extern ClaireBoolean * choco_testIfSatisfied_SubSet(SubSet *c);
extern void  choco_propagate_SubSet(SubSet *c);
extern void  choco_awake_SubSet_choco(SubSet *c);
extern SubSet * choco_subset_SetVar(SetVar *s1,SetVar *s2);
extern void  choco_awakeOnKer_DisjointSets(DisjointSets *c,int idx);
extern void  choco_awakeOnEnv_DisjointSets(DisjointSets *c,int idx);
extern void  choco_awakeOnInstSet_DisjointSets(DisjointSets *c,int idx);
extern OID  choco_askIfEntailed_DisjointSets(DisjointSets *c);
extern ClaireBoolean * choco_testIfSatisfied_DisjointSets(DisjointSets *c);
extern void  choco_propagate_DisjointSets(DisjointSets *c);
extern void  choco_awake_DisjointSets_choco(DisjointSets *c);
extern DisjointSets * choco_disjoint_SetVar(SetVar *s1,SetVar *s2);
extern void  choco_awakeOnKer_OverlappingSets(OverlappingSets *c,int idx);
extern void  choco_awakeOnEnv_OverlappingSets(OverlappingSets *c,int idx);
extern void  choco_awakeOnInstSet_OverlappingSets(OverlappingSets *c,int idx);
extern OID  choco_askIfEntailed_OverlappingSets(OverlappingSets *c);
extern ClaireBoolean * choco_testIfSatisfied_OverlappingSets(OverlappingSets *c);
extern void  choco_propagate_OverlappingSets(OverlappingSets *c);
extern void  choco_awake_OverlappingSets_choco(OverlappingSets *c);
extern OverlappingSets * choco_overlap_SetVar(SetVar *s1,SetVar *s2);
extern OverlappingSets * choco_opposite_DisjointSets(DisjointSets *c);
extern DisjointSets * choco_opposite_OverlappingSets(OverlappingSets *c);
extern OID  choco_selectBranchingObject_AbstractBranching_choco(AbstractBranching *b);
extern void  choco_goDownBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern void  choco_traceDownBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern int  choco_getFirstBranch_AbstractBranching_choco(AbstractBranching *b,OID x);
extern int  choco_getNextBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern void  choco_goUpBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern void  choco_traceUpBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern ClaireBoolean * choco_finishedBranching_AbstractBranching_choco(AbstractBranching *b,OID x,int i);
extern ClaireBoolean * choco_branchOn_AbstractBranching(AbstractBranching *b,OID v,int n);
extern void  choco_goUpBranch_LargeBranching_choco(LargeBranching *b,OID x,int i);
extern void  choco_traceUpBranch_LargeBranching_choco(LargeBranching *b,OID x,int i);
extern int  choco_getFirstBranch_LargeBranching_choco(LargeBranching *b,OID x);
extern int  choco_getNextBranch_LargeBranching_choco(LargeBranching *b,OID x,int i);
extern ClaireBoolean * choco_finishedBranching_LargeBranching_choco(LargeBranching *b,OID x,int i);
extern AbstractBranching * choco_selectBranching_CompositeBranching(CompositeBranching *b);
extern OID  choco_selectBranchingObject_CompositeBranching_choco(CompositeBranching *b);
extern int  choco_getFirstBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair);
extern int  choco_getNextBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern void  choco_goUpBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern void  choco_traceUpBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern void  choco_goDownBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern void  choco_traceDownBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern ClaireBoolean * choco_finishedBranching_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i);
extern OID  choco_selectVar_VarSelector(VarSelector *vs);
extern OID  choco_selectVar_MinDomain(MinDomain *vs);
extern OID  choco_selectVar_MaxDeg(MaxDeg *vs);
extern OID  choco_selectVar_DomDeg(DomDeg *vs);
extern OID  choco_selectVar_StaticVarOrder(StaticVarOrder *vs);
extern ClaireBoolean * choco_isOver_ValIterator(ValIterator *vi,IntVar *x,int i);
extern int  choco_getFirstVal_ValIterator(ValIterator *vi,IntVar *x);
extern int  choco_getNextVal_ValIterator(ValIterator *vi,IntVar *x,int i);
extern ClaireBoolean * choco_isOver_IncreasingDomain(IncreasingDomain *vi,IntVar *x,int i);
extern int  choco_getFirstVal_IncreasingDomain(IncreasingDomain *vi,IntVar *x);
extern int  choco_getNextVal_IncreasingDomain(IncreasingDomain *vi,IntVar *x,int i);
extern ClaireBoolean * choco_isOver_DecreasingDomain(DecreasingDomain *vi,IntVar *x,int i);
extern int  choco_getFirstVal_DecreasingDomain(DecreasingDomain *vi,IntVar *x);
extern int  choco_getNextVal_DecreasingDomain(DecreasingDomain *vi,IntVar *x,int i);
extern int  choco_getBestVal_ValSelector(ValSelector *vh,IntVar *x);
extern int  choco_getBestVal_MidValHeuristic(MidValHeuristic *vh,IntVar *x);
extern int  choco_getBestVal_MinValHeuristic(MinValHeuristic *vh,IntVar *x);
extern int  choco_getBestVal_MaxValHeuristic(MaxValHeuristic *vh,IntVar *x);
extern int  choco_getObjectiveValue_AbstractOptimize_choco(AbstractOptimize *a);
extern int  choco_getBestObjectiveValue_AbstractOptimize(AbstractOptimize *a);
extern int  choco_getObjectiveTarget_AbstractOptimize(AbstractOptimize *a);
extern void  choco_initBounds_AbstractOptimize(AbstractOptimize *a);
extern ClaireBoolean * choco_getFeasibility_GlobalSearchSolver(GlobalSearchSolver *a);
extern ClaireBoolean * choco_mayExpandNewNode_GlobalSearchLimit(GlobalSearchLimit *lim,GlobalSearchSolver *algo);
extern ClaireBoolean * choco_mayExpandNewNode_NodeLimit(NodeLimit *lim,GlobalSearchSolver *algo);
extern ClaireBoolean * choco_mayExpandNewNode_BacktrackLimit(BacktrackLimit *lim,GlobalSearchSolver *algo);
extern ClaireBoolean * choco_mayExpandNewNode_TimeLimit(TimeLimit *lim,GlobalSearchSolver *algo);
extern void  choco_copyParameters_GlobalSearchSolver(GlobalSearchSolver *oldSolver,GlobalSearchSolver *newSolver);
extern void  choco_attach_GlobalSearchSolver(GlobalSearchSolver *newSolver,Problem *pb);
extern GlobalSearchSolver * choco_composeGlobalSearch_GlobalSearchSolver(GlobalSearchSolver *algo,list *l);
extern ClaireType * choco_composeGlobalSearch_GlobalSearchSolver_type(ClaireType *algo,ClaireType *l);
extern OID  choco_getSmallestDomainUnassignedVar_Problem(Problem *pb);
extern OID  choco_getSmallestDomainUnassignedVar_list(list *l);
extern OID  choco_getDomOverDegBestUnassignedVar_Problem(Problem *pb);
extern OID  choco_getDomOverDegBestUnassignedVar_list(list *l);
extern OID  choco_getMostConstrainedUnassignedVar_Problem(Problem *pb);
extern OID  choco_getMostConstrainedUnassignedVar_list(list *l);
extern void  choco_storeCurrentSolution_Solver(Solver *a);
extern Solution * choco_makeSolutionFromCurrentState_Solver(Solver *a);
extern void  choco_storeSolution_Solver(Solver *a,Solution *sol);
extern ClaireBoolean * choco_existsSolution_Solver(Solver *a);
extern void  choco_restoreBestSolution_Solver(Solver *a);
extern void  choco_restoreSolution_Solution(Solution *s);
extern OID  choco_showSolution_Solver(Solver *a);
extern ClaireBoolean * choco_explore_AbstractBranching(AbstractBranching *b,int n);
extern ClaireBoolean * choco_branchOn_LargeBranching(LargeBranching *b,OID v,int n);
extern ClaireBoolean * choco_branchOn_BinBranching(BinBranching *b,OID v,int n);
extern OID  choco_selectBranchingObject_SplitDomain_choco(SplitDomain *b);
extern void  choco_goDownBranch_SplitDomain_choco(SplitDomain *b,OID x,int first);
extern void  choco_traceDownBranch_SplitDomain_choco(SplitDomain *b,OID x,int first);
extern OID  choco_selectBranchingObject_AssignOrForbid_choco(AssignOrForbid *b);
extern void  choco_goDownBranch_AssignOrForbid_choco(AssignOrForbid *b,OID x,int first);
extern void  choco_traceDownBranch_AssignOrForbid_choco(AssignOrForbid *b,OID x,int first);
extern OID  choco_selectBranchingObject_AssignVar_choco(AssignVar *b);
extern ClaireBoolean * choco_finishedBranching_AssignVar_choco(AssignVar *b,OID x,int i);
extern int  choco_getFirstBranch_AssignVar_choco(AssignVar *b,OID x);
extern int  choco_getNextBranch_AssignVar_choco(AssignVar *b,OID x,int i);
extern void  choco_goDownBranch_AssignVar_choco(AssignVar *b,OID x,int i);
extern void  choco_goUpBranch_AssignVar_choco(AssignVar *b,OID x,int i);
extern void  choco_traceDownBranch_AssignVar_choco(AssignVar *b,OID x,int i);
extern void  choco_traceUpBranch_AssignVar_choco(AssignVar *b,OID x,int i);
extern OID  choco_selectBranchingObject_SettleBinDisjunction_choco(SettleBinDisjunction *b);
extern void  choco_goDownBranch_SettleBinDisjunction_choco(SettleBinDisjunction *b,OID d,int first);
extern void  choco_traceDownBranch_SettleBinDisjunction_choco(SettleBinDisjunction *b,OID d,int first);
extern void  choco_newTreeSearch_GlobalSearchSolver(GlobalSearchSolver *a);
extern void  choco_endTreeSearch_GlobalSearchSolver(GlobalSearchSolver *algo);
extern void  choco_newNode_GlobalSearchSolver(GlobalSearchSolver *a);
extern void  choco_endNode_GlobalSearchSolver(GlobalSearchSolver *a);
extern void  choco_recordSolution_GlobalSearchSolver(GlobalSearchSolver *a);
extern void  choco_postDynamicCut_GlobalSearchSolver(GlobalSearchSolver *a);
extern void  choco_recordSolution_AbstractOptimize(AbstractOptimize *a);
extern void  choco_setBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_setTargetBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_setTargetLowerBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_setTargetUpperBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_postTargetBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_postTargetLowerBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_postTargetUpperBound_AbstractOptimize(AbstractOptimize *a);
extern void  choco_newTreeSearch_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_endTreeSearch_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_postDynamicCut_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_run_GlobalSearchSolver(GlobalSearchSolver *algo);
extern void  choco_run_Solve(Solve *algo);
extern void  choco_run_BranchAndBound(BranchAndBound *algo);
extern void  choco_postDynamicCut_BranchAndBound(BranchAndBound *a);
extern void  choco_newTreeSearch_BranchAndBound(BranchAndBound *a);
extern void  choco_endTreeSearch_BranchAndBound(BranchAndBound *a);
extern void  choco_newLoop_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_endLoop_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_recordNoSolution_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern ClaireBoolean * choco_oneMoreLoop_OptimizeWithRestarts(OptimizeWithRestarts *a);
extern void  choco_run_OptimizeWithRestarts(OptimizeWithRestarts *algo);
extern ConflictCount * choco_makeConflictCount_void();
extern void  choco_attachInvariantEngine_Problem(Problem *pb,InvariantEngine *ie);
extern int  choco_unitaryDeltaConflict_IntVar(IntVar *v,int a,int b,AbstractConstraint *c);
extern int  choco_deltaNbConflicts_IntVar(IntVar *v,int a,int b);
extern int  choco_getMinConflictValue_IntVar(IntVar *v);
extern void  choco_computeConflictAccount_ConflictCount(ConflictCount *ie);
extern void  choco_resetInvariants_InvariantEngine(InvariantEngine *ie);
extern void  choco_resetInvariants_ConflictCount(ConflictCount *ie);
extern int  choco_getLocalSearchObjectiveValue_InvariantEngine(InvariantEngine *ie);
extern int  choco_getLocalSearchObjectiveValue_ConflictCount(ConflictCount *ie);
extern void  choco_restoreBestAssignment_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_saveAssignment_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_setFeasibleMode_Problem(Problem *pb,ClaireBoolean *b);
extern OID  choco_selectMoveVar_AssignmentHeuristic(AssignmentHeuristic *a);
extern int  choco_selectValue_AssignmentHeuristic(AssignmentHeuristic *a,IntVar *v);
extern ClaireBoolean * choco_assignOneVar_AssignmentHeuristic(AssignmentHeuristic *a);
extern void  choco_build_AssignmentHeuristic(AssignmentHeuristic *a);
extern void  choco_eraseAssignment_ConflictCount(ConflictCount *ie);
extern OID  choco_checkCleanState_ConflictCount(ConflictCount *ie);
extern OID  choco_selectMoveVar_FlipNeighborhood(FlipNeighborhood *m);
extern int  choco_newValue_FlipNeighborhood(FlipNeighborhood *m,IntVar *v);
extern ClaireBoolean * choco_move_FlipNeighborhood(FlipNeighborhood *m);
extern ClaireBoolean * choco_finishedLoop_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_initLoop_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_endLoop_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_copyParameters_LocalSearchSolver(LocalSearchSolver *oldSolver,LocalSearchSolver *newSolver);
extern void  choco_attachLocalSearchSolver_Problem(Problem *pb,LocalSearchSolver *newSolver);
extern void  choco_runLocalSearch_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_initIterations_MultipleDescent(MultipleDescent *algo);
extern ClaireBoolean * choco_finishedIterations_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_oneMoreMove_LocalSearchSolver(LocalSearchSolver *algo);
extern void  choco_becomesAConflict_AbstractConstraint(AbstractConstraint *c,ConflictCount *ie);
extern void  choco_becomesAConflict_UnIntConstraint(UnIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesAConflict_BinIntConstraint(BinIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesAConflict_TernIntConstraint(TernIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesAConflict_LargeIntConstraint(LargeIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesSatisfied_AbstractConstraint(AbstractConstraint *c,ConflictCount *ie);
extern void  choco_becomesSatisfied_UnIntConstraint(UnIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesSatisfied_BinIntConstraint(BinIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesSatisfied_TernIntConstraint(TernIntConstraint *c,ConflictCount *ie);
extern void  choco_becomesSatisfied_LargeIntConstraint(LargeIntConstraint *c,ConflictCount *ie);
extern void  choco_addOneConflict_ConflictCount(ConflictCount *ie,IntVar *v);
extern void  choco_removeOneConflict_ConflictCount(ConflictCount *ie,IntVar *v);
extern void  choco_postAssignVal_ConflictCount(ConflictCount *ie,IntVar *v,int a);
extern void  choco_postUnAssignVal_ConflictCount(ConflictCount *ie,IntVar *v);
extern void  choco_postChangeVal_ConflictCount(ConflictCount *ie,IntVar *v,int a,int b);
extern Problem * choco_makeProblem_string(char *s,int n);
extern Problem * choco_getProblem_IntVar(IntVar *v);
extern Problem * choco_getProblem_AbstractConstraint(AbstractConstraint *c);
extern Problem * choco_getActiveProblem_void();
extern void  choco_setActiveProblem_Problem(Problem *pb);
extern void  choco_discardProblem_Problem(Problem *pb);
extern PropagationEngine * choco_getPropagationEngine_Problem(Problem *p);
extern void  choco_setDelayedLinearConstraintPropagation_ChocEngine(ChocEngine *pe,ClaireBoolean *b);
extern IntVar * choco_makeBoundIntVar_Problem1(Problem *p,char *s,int i,int j);
extern IntVar * choco_makeBoundIntVar_Problem2(Problem *p,int i,int j);
extern IntVar * choco_makeBoundIntVar_Problem3(Problem *p,char *s);
extern IntVar * choco_makeConstantIntVar_integer(int x);
extern IntVar * choco_makeIntVar_Problem1(Problem *p,char *s,int i,int j);
extern IntVar * choco_makeIntVar_Problem2(Problem *p,int i,int j);
extern IntVar * choco_makeIntVar_Problem3(Problem *p,char *s);
extern IntVar * choco_makeIntVar_Problem4(Problem *p,char *s,list *b);
extern IntVar * choco_makeIntVar_Problem5(Problem *p,list *b);
extern SetVar * choco_makeSetVar_Problem1(Problem *p,char *s,int i,int j);
extern SetVar * choco_makeSetVar_Problem2(Problem *p,int i,int j);
extern SetVar * choco_makeSetVar_Problem3(Problem *p,char *s,int i,int j,IntVar *cardVar);
extern SetVar * choco_makeSetVar_Problem4(Problem *p,int i,int j,IntVar *cardVar);
extern TruthTest * choco_makeBinTruthTest_method(method *m);
extern TruthTable2D * choco_makeBinRelation_integer1(int min1,int max1,int min2,int max2);
extern TruthTable2D * choco_makeBinRelation_integer2(int min1,int max1,int min2,int max2,list *mytuples);
extern CSPBinConstraint * choco_binConstraint_IntVar1(IntVar *va,IntVar *vb,BinRelation *feasRel,ClaireBoolean *feas,int ac);
extern CSPBinConstraint * choco_binConstraint_IntVar2(IntVar *va,IntVar *vb,BinRelation *feasRel,ClaireBoolean *feas);
extern CSPBinConstraint * choco_binConstraint_IntVar3(IntVar *va,IntVar *vb,BinRelation *feasRel,int ac);
extern CSPBinConstraint * choco_binConstraint_IntVar4(IntVar *va,IntVar *vb,BinRelation *feasRel);
extern CSPBinConstraint * choco_feasPairConstraint_IntVar1(IntVar *va,IntVar *vb,list *goodPairs,int ac);
extern CSPBinConstraint * choco_infeasPairConstraint_IntVar1(IntVar *va,IntVar *vb,list *badPairs,int ac);
extern CSPBinConstraint * choco_feasBinTestConstraint_IntVar1(IntVar *va,IntVar *vb,method *m,int ac);
extern CSPBinConstraint * choco_infeasBinTestConstraint_IntVar1(IntVar *va,IntVar *vb,method *m,int ac);
extern CSPBinConstraint * choco_feasPairConstraint_IntVar2(IntVar *va,IntVar *vb,list *goodPairs);
extern CSPBinConstraint * choco_infeasPairConstraint_IntVar2(IntVar *va,IntVar *vb,list *badPairs);
extern CSPBinConstraint * choco_feasBinTestConstraint_IntVar2(IntVar *va,IntVar *vb,method *m);
extern CSPBinConstraint * choco_infeasBinTestConstraint_IntVar2(IntVar *va,IntVar *vb,method *m);
extern CSPLargeConstraint * choco_feasTupleConstraint_list(list *lvars,list *goodTuples);
extern CSPLargeConstraint * choco_infeasTupleConstraint_list(list *lvars,list *badTuples);
extern CSPLargeConstraint * choco_feasTestConstraint_list(list *lvars,method *m);
extern void  claire_self_print_LinTerm_choco(LinTerm *t);
extern LinTerm * claire_scalar_list(list *l1,list *l2);
extern LinTerm * choco_sumVars_list(list *l);
extern AllDiff * choco_allDifferent_list(list *l);
extern void  choco_post_Problem(Problem *p,AbstractConstraint *c0);
extern void  choco_setMin_IntVar(IntVar *v,int x);
extern void  choco_setMax_IntVar(IntVar *v,int x);
extern void  choco_setVal_IntVar(IntVar *v,int x);
extern void  choco_remVal_IntVar(IntVar *v,int x);
extern void  choco_setBranch_Disjunction(Disjunction *d,int i,ClaireBoolean *b);
extern void  choco_setBranch_LargeDisjunction(LargeDisjunction *d,int i,ClaireBoolean *b);
extern void  choco_setBranch_Cardinality(Cardinality *c,int i,ClaireBoolean *b);
extern void  choco_propagate_Problem(Problem *p);
extern void  choco_setActive_AbstractConstraint(AbstractConstraint *c);
extern void  choco_setPassive_AbstractConstraint(AbstractConstraint *c);
extern Problem * choco_getProblem_Solver(Solver *algo);
extern GlobalSearchSolver * choco_getSearchManager_AbstractBranching(AbstractBranching *b);
extern OID  choco_makeMinDomVarHeuristic_void();
extern OID  choco_makeDomDegVarHeuristic_void();
extern OID  choco_makeMaxDegVarHeuristic_void();
extern OID  choco_makeStaticVarHeuristic_list(list *l);
extern MinDomain * choco_makeMinDomVarHeuristic_list(list *l);
extern DomDeg * choco_makeDomDegVarHeuristic_list(list *l);
extern MaxDeg * choco_makeMaxDegVarHeuristic_list(list *l);
extern OID  choco_makeIncValIterator_void();
extern OID  choco_makeDecValIterator_void();
extern OID  choco_makeMinValSelector_void();
extern OID  choco_makeMaxValSelector_void();
extern OID  choco_makeMidValSelector_void();
extern AssignVar * choco_makeAssignVarBranching_VarSelector1(VarSelector *varh,ValIterator *valh);
extern AssignVar * choco_makeAssignVarBranching_VarSelector2(VarSelector *varh);
extern AssignVar * choco_makeAssignVarBranching_void();
extern SplitDomain * choco_makeSplitDomBranching_VarSelector1(VarSelector *varh,ValSelector *valh,int threshold);
extern SplitDomain * choco_makeSplitDomBranching_VarSelector2(VarSelector *varh,int threshold);
extern SplitDomain * choco_makeSplitDomBranching_VarSelector3(VarSelector *varh);
extern SplitDomain * choco_makeSplitDomBranching_void();
extern AssignOrForbid * choco_makeAssignOrForbidBranching_VarSelector(VarSelector *varh,ValSelector *valh);
extern SettleBinDisjunction * choco_makeDisjunctionBranching_Problem(Problem *pb);
extern list * choco_makeDefaultBranchingList_Problem(Problem *pb);
extern Solve * choco_makeGlobalSearchSolver_boolean1(ClaireBoolean *allSolutions,list *l);
extern AbstractOptimize * choco_makeGlobalSearchMaximizer_IntVar1(IntVar *obj,ClaireBoolean *restartSearch,list *l);
extern AbstractOptimize * choco_makeGlobalSearchMinimizer_IntVar1(IntVar *obj,ClaireBoolean *restartSearch,list *l);
extern Solve * choco_makeGlobalSearchSolver_boolean2(ClaireBoolean *allSolutions);
extern AbstractOptimize * choco_makeGlobalSearchMaximizer_IntVar2(IntVar *obj,ClaireBoolean *restartSearch);
extern AbstractOptimize * choco_makeGlobalSearchMinimizer_IntVar2(IntVar *obj,ClaireBoolean *restartSearch);
extern NodeLimit * choco_makeBacktrackLimit_integer(int nb);
extern BacktrackLimit * choco_makeNodeLimit_integer(int nb);
extern void  choco_setSearchLimit_GlobalSearchSolver(GlobalSearchSolver *algo,GlobalSearchLimit *lim);
extern ClaireBoolean * choco_solve_Problem1(Problem *pb,ClaireBoolean *all);
extern int  choco_minimize_Problem1(Problem *pb,IntVar *obj,ClaireBoolean *restart);
extern int  choco_maximize_Problem1(Problem *pb,IntVar *obj,ClaireBoolean *restart);
extern ClaireBoolean * choco_solve_Problem2(Problem *pb,list *l,ClaireBoolean *all);
extern int  choco_minimize_Problem2(Problem *pb,IntVar *obj,list *l,ClaireBoolean *restart);
extern int  choco_maximize_Problem2(Problem *pb,IntVar *obj,list *l,ClaireBoolean *restart);
extern void  choco_setMaxPrintDepth_GlobalSearchSolver(GlobalSearchSolver *algo,int n);
extern void  choco_setMaxSolutionStorage_GlobalSearchSolver(GlobalSearchSolver *algo,int nb);
extern void  choco_setVarsToShow_GlobalSearchSolver(GlobalSearchSolver *algo,list *l);
extern int  choco_getNbSol_GlobalSearchSolver(GlobalSearchSolver *algo);
extern Problem * choco_getProblem_AbstractBranching(AbstractBranching *b);
extern GlobalSearchSolver * choco_getGlobalSearchSolver_Problem(Problem *p);
extern void  choco_setNodeLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n);
extern void  choco_setTimeLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n);
extern void  choco_setBacktrackLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n);
extern void  choco_setMaxNbBk_GlobalSearchSolver(GlobalSearchSolver *algo,int n);
extern void  choco_assignVal_IntVar(IntVar *v,int a);
extern void  choco_unassignVal_IntVar(IntVar *v);
extern void  choco_changeVal_IntVar(IntVar *v,int b);
extern void  choco_setMaxNbLocalSearch_Problem(Problem *pb,int n);
extern void  choco_setMaxNbLocalMoves_Problem(Problem *pb,int n);
extern int  choco_move_Problem1(Problem *pb);
extern int  choco_move_Problem2(Problem *pb,ConstructiveHeuristic *b,MoveNeighborhood *m);
extern GreaterOrEqualxc * claire__sup_equal_IntVar1(IntVar *v,int x);
extern GreaterOrEqualxc * claire__sup_IntVar1(IntVar *v,int x);
extern LessOrEqualxc * claire__inf_equal_IntVar1(IntVar *v,int x);
extern LessOrEqualxc * claire__inf_IntVar1(IntVar *v,int x);
extern Equalxc * claire__equal_equal_IntVar1(IntVar *v,int x);
extern NotEqualxc * claire__I_equal_equal_IntVar1(IntVar *v,int x);
extern LessOrEqualxc * claire__sup_equal_integer1(int x,IntVar *v);
extern LessOrEqualxc * claire__sup_integer2(int x,IntVar *v);
extern GreaterOrEqualxc * claire__inf_equal_integer2(int x,IntVar *v);
extern GreaterOrEqualxc * claire__inf_integer2(int x,IntVar *v);
extern Equalxc * claire__equal_equal_integer1(int x,IntVar *v);
extern NotEqualxc * claire__I_equal_equal_integer1(int x,IntVar *v);
extern Equalxyc * claire__equal_equal_IntVar2(IntVar *u,IntVar *v);
extern NotEqualxyc * claire__I_equal_equal_IntVar2(IntVar *u,IntVar *v);
extern GreaterOrEqualxyc * claire__inf_equal_IntVar2(IntVar *u,IntVar *v);
extern GreaterOrEqualxyc * claire__sup_equal_IntVar2(IntVar *u,IntVar *v);
extern GreaterOrEqualxyc * claire__sup_IntVar2(IntVar *u,IntVar *v);
extern GreaterOrEqualxyc * claire__inf_IntVar2(IntVar *u,IntVar *v);
extern OID  claire_self_print_UnTerm_choco(UnTerm *t);
extern OID  claire_self_print_BinTerm_choco(BinTerm *t);
extern Term * choco_sumTerms_list(list *l);
extern LinTerm * claire__star_integer2(int a,IntVar *x);
extern LinTerm * claire__star_IntVar(IntVar *x,int a);
extern LinTerm * claire__star_integer3(int a,LinTerm *t);
extern LinTerm * claire__star_LinTerm(LinTerm *t,int a);
extern UnTerm * claire__plus_IntVar1(IntVar *u,int c);
extern UnTerm * claire__plus_integer2(int c,IntVar *u);
extern BinTerm * claire__plus_IntVar2(IntVar *u,IntVar *v);
extern UnTerm * claire__plus_UnTerm1(UnTerm *t,int c);
extern UnTerm * claire__plus_integer3(int c,UnTerm *t);
extern BinTerm * claire__plus_UnTerm2(UnTerm *t,IntVar *x);
extern BinTerm * claire__plus_IntVar3(IntVar *x,UnTerm *t);
extern BinTerm * claire__plus_UnTerm3(UnTerm *t1,UnTerm *t2);
extern BinTerm * claire__plus_BinTerm1(BinTerm *t,int c);
extern BinTerm * claire__plus_integer4(int c,BinTerm *t);
extern LinTerm * claire__plus_BinTerm2(BinTerm *t,IntVar *x);
extern LinTerm * claire__plus_IntVar4(IntVar *x,BinTerm *t);
extern LinTerm * claire__plus_BinTerm3(BinTerm *t1,UnTerm *t2);
extern LinTerm * claire__plus_UnTerm4(UnTerm *t2,BinTerm *t1);
extern LinTerm * claire__plus_BinTerm4(BinTerm *t1,BinTerm *t2);
extern LinTerm * claire__plus_LinTerm1(LinTerm *t,int a);
extern LinTerm * claire__plus_integer5(int a,LinTerm *t);
extern LinTerm * claire__plus_LinTerm2(LinTerm *t,IntVar *x);
extern LinTerm * claire__plus_IntVar5(IntVar *x,LinTerm *t);
extern LinTerm * claire__plus_LinTerm3(LinTerm *t1,UnTerm *t2);
extern LinTerm * claire__plus_UnTerm5(UnTerm *t2,LinTerm *t1);
extern LinTerm * claire__plus_LinTerm4(LinTerm *t1,BinTerm *t2);
extern LinTerm * claire__plus_BinTerm5(BinTerm *t2,LinTerm *t1);
extern LinTerm * claire__plus_LinTerm5(LinTerm *t1,LinTerm *t2);
extern UnTerm * claire__dash_IntVar1(IntVar *x);
extern UnTerm * claire__dash_UnTerm1(UnTerm *t);
extern BinTerm * claire__dash_BinTerm1(BinTerm *t);
extern LinTerm * claire__dash_LinTerm1(LinTerm *t);
extern UnTerm * claire__dash_IntVar2(IntVar *t1,int t2);
extern UnTerm * claire__dash_UnTerm2(UnTerm *t1,int t2);
extern BinTerm * claire__dash_BinTerm2(BinTerm *t1,int t2);
extern LinTerm * claire__dash_LinTerm2(LinTerm *t1,int t2);
extern UnTerm * claire__dash_integer4(int t1,IntVar *t2);
extern BinTerm * claire__dash_IntVar3(IntVar *t1,IntVar *t2);
extern BinTerm * claire__dash_UnTerm3(UnTerm *t1,IntVar *t2);
extern LinTerm * claire__dash_BinTerm3(BinTerm *t1,IntVar *t2);
extern LinTerm * claire__dash_LinTerm3(LinTerm *t1,IntVar *t2);
extern UnTerm * claire__dash_integer5(int t1,UnTerm *t2);
extern BinTerm * claire__dash_IntVar4(IntVar *t1,UnTerm *t2);
extern BinTerm * claire__dash_UnTerm4(UnTerm *t1,UnTerm *t2);
extern LinTerm * claire__dash_BinTerm4(BinTerm *t1,UnTerm *t2);
extern LinTerm * claire__dash_LinTerm4(LinTerm *t1,UnTerm *t2);
extern BinTerm * claire__dash_integer6(int t1,BinTerm *t2);
extern LinTerm * claire__dash_IntVar5(IntVar *t1,BinTerm *t2);
extern LinTerm * claire__dash_UnTerm5(UnTerm *t1,BinTerm *t2);
extern LinTerm * claire__dash_BinTerm5(BinTerm *t1,BinTerm *t2);
extern LinTerm * claire__dash_LinTerm5(LinTerm *t1,BinTerm *t2);
extern LinTerm * claire__dash_integer7(int t1,LinTerm *t2);
extern LinTerm * claire__dash_IntVar6(IntVar *t1,LinTerm *t2);
extern LinTerm * claire__dash_UnTerm6(UnTerm *t1,LinTerm *t2);
extern LinTerm * claire__dash_BinTerm6(BinTerm *t1,LinTerm *t2);
extern LinTerm * claire__dash_LinTerm6(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__sup_equal_integer2(int a,UnTerm *t);
extern IntConstraint * claire__sup_equal_integer3(int a,BinTerm *t);
extern IntConstraint * claire__sup_equal_integer4(int a,LinTerm *t);
extern IntConstraint * claire__sup_equal_IntVar3(IntVar *x,UnTerm *t);
extern IntConstraint * claire__sup_equal_IntVar4(IntVar *x,BinTerm *t);
extern IntConstraint * claire__sup_equal_IntVar5(IntVar *x,LinTerm *t);
extern IntConstraint * claire__sup_equal_UnTerm1(UnTerm *t1,BinTerm *t2);
extern IntConstraint * claire__sup_equal_UnTerm2(UnTerm *t1,LinTerm *t2);
extern IntConstraint * claire__sup_equal_BinTerm1(BinTerm *t1,LinTerm *t2);
extern UnIntConstraint * claire__sup_equal_UnTerm3(UnTerm *t,int c);
extern IntConstraint * claire__sup_equal_UnTerm4(UnTerm *t,IntVar *x);
extern IntConstraint * claire__sup_equal_UnTerm5(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__sup_equal_BinTerm2(BinTerm *t,int c);
extern LinComb * claire__sup_equal_BinTerm3(BinTerm *t,IntVar *x);
extern LinComb * claire__sup_equal_BinTerm4(BinTerm *t1,UnTerm *t2);
extern LinComb * claire__sup_equal_BinTerm5(BinTerm *t1,BinTerm *t2);
extern LinComb * claire__sup_equal_LinTerm1(LinTerm *t,int a);
extern LinComb * claire__sup_equal_LinTerm2(LinTerm *t,IntVar *x);
extern LinComb * claire__sup_equal_LinTerm3(LinTerm *t,UnTerm *t2);
extern LinComb * claire__sup_equal_LinTerm4(LinTerm *t,BinTerm *t2);
extern LinComb * claire__sup_equal_LinTerm5(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__sup_integer3(int t1,UnTerm *t2);
extern IntConstraint * claire__sup_integer4(int t1,BinTerm *t2);
extern IntConstraint * claire__sup_integer5(int t1,LinTerm *t2);
extern IntConstraint * claire__sup_IntVar3(IntVar *t1,UnTerm *t2);
extern IntConstraint * claire__sup_IntVar4(IntVar *t1,BinTerm *t2);
extern IntConstraint * claire__sup_IntVar5(IntVar *t1,LinTerm *t2);
extern IntConstraint * claire__sup_UnTerm1(UnTerm *t1,int t2);
extern IntConstraint * claire__sup_BinTerm1(BinTerm *t1,int t2);
extern IntConstraint * claire__sup_LinTerm1(LinTerm *t1,int t2);
extern IntConstraint * claire__sup_UnTerm2(UnTerm *t1,IntVar *t2);
extern IntConstraint * claire__sup_BinTerm2(BinTerm *t1,IntVar *t2);
extern IntConstraint * claire__sup_LinTerm2(LinTerm *t1,IntVar *t2);
extern IntConstraint * claire__sup_UnTerm3(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__sup_BinTerm3(BinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__sup_LinTerm3(LinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__sup_UnTerm4(UnTerm *t1,BinTerm *t2);
extern IntConstraint * claire__sup_BinTerm4(BinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__sup_LinTerm4(LinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__sup_UnTerm5(UnTerm *t1,LinTerm *t2);
extern IntConstraint * claire__sup_BinTerm5(BinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__sup_LinTerm5(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_equal_integer3(int t1,UnTerm *t2);
extern IntConstraint * claire__inf_equal_integer4(int t1,BinTerm *t2);
extern IntConstraint * claire__inf_equal_integer5(int t1,LinTerm *t2);
extern IntConstraint * claire__inf_equal_IntVar3(IntVar *t1,UnTerm *t2);
extern IntConstraint * claire__inf_equal_IntVar4(IntVar *t1,BinTerm *t2);
extern IntConstraint * claire__inf_equal_IntVar5(IntVar *t1,LinTerm *t2);
extern IntConstraint * claire__inf_equal_UnTerm1(UnTerm *t1,int t2);
extern IntConstraint * claire__inf_equal_BinTerm1(BinTerm *t1,int t2);
extern IntConstraint * claire__inf_equal_LinTerm1(LinTerm *t1,int t2);
extern IntConstraint * claire__inf_equal_UnTerm2(UnTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_equal_BinTerm2(BinTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_equal_LinTerm2(LinTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_equal_UnTerm3(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_equal_BinTerm3(BinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_equal_LinTerm3(LinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_equal_UnTerm4(UnTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_equal_BinTerm4(BinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_equal_LinTerm4(LinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_equal_UnTerm5(UnTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_equal_BinTerm5(BinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_equal_LinTerm5(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_integer3(int t1,UnTerm *t2);
extern IntConstraint * claire__inf_integer4(int t1,BinTerm *t2);
extern IntConstraint * claire__inf_integer5(int t1,LinTerm *t2);
extern IntConstraint * claire__inf_IntVar3(IntVar *t1,UnTerm *t2);
extern IntConstraint * claire__inf_IntVar4(IntVar *t1,BinTerm *t2);
extern IntConstraint * claire__inf_IntVar5(IntVar *t1,LinTerm *t2);
extern IntConstraint * claire__inf_UnTerm1(UnTerm *t1,int t2);
extern IntConstraint * claire__inf_BinTerm1(BinTerm *t1,int t2);
extern IntConstraint * claire__inf_LinTerm1(LinTerm *t1,int t2);
extern IntConstraint * claire__inf_UnTerm2(UnTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_BinTerm2(BinTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_LinTerm2(LinTerm *t1,IntVar *t2);
extern IntConstraint * claire__inf_UnTerm3(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_BinTerm3(BinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_LinTerm3(LinTerm *t1,UnTerm *t2);
extern IntConstraint * claire__inf_UnTerm4(UnTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_BinTerm4(BinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_LinTerm4(LinTerm *t1,BinTerm *t2);
extern IntConstraint * claire__inf_UnTerm5(UnTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_BinTerm5(BinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__inf_LinTerm5(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__equal_equal_integer2(int a,UnTerm *t);
extern IntConstraint * claire__equal_equal_integer3(int a,BinTerm *t);
extern IntConstraint * claire__equal_equal_integer4(int a,LinTerm *t);
extern IntConstraint * claire__equal_equal_IntVar3(IntVar *x,UnTerm *t);
extern IntConstraint * claire__equal_equal_IntVar4(IntVar *x,BinTerm *t);
extern IntConstraint * claire__equal_equal_IntVar5(IntVar *x,LinTerm *t);
extern IntConstraint * claire__equal_equal_UnTerm1(UnTerm *t1,BinTerm *t2);
extern IntConstraint * claire__equal_equal_UnTerm2(UnTerm *t1,LinTerm *t2);
extern IntConstraint * claire__equal_equal_BinTerm1(BinTerm *t1,LinTerm *t2);
extern UnIntConstraint * claire__equal_equal_UnTerm3(UnTerm *t,int c);
extern IntConstraint * claire__equal_equal_UnTerm4(UnTerm *t,IntVar *x);
extern BinIntConstraint * claire__equal_equal_UnTerm5(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__equal_equal_BinTerm2(BinTerm *t,int c);
extern LinComb * claire__equal_equal_BinTerm3(BinTerm *t,IntVar *x);
extern LinComb * claire__equal_equal_BinTerm4(BinTerm *t1,UnTerm *t2);
extern LinComb * claire__equal_equal_BinTerm5(BinTerm *t1,BinTerm *t2);
extern LinComb * claire__equal_equal_LinTerm1(LinTerm *t,int c);
extern LinComb * claire__equal_equal_LinTerm2(LinTerm *t,IntVar *x);
extern LinComb * claire__equal_equal_LinTerm3(LinTerm *t1,UnTerm *t2);
extern LinComb * claire__equal_equal_LinTerm4(LinTerm *t1,BinTerm *t2);
extern LinComb * claire__equal_equal_LinTerm5(LinTerm *t1,LinTerm *t2);
extern IntConstraint * claire__I_equal_equal_integer2(int a,UnTerm *t);
extern IntConstraint * claire__I_equal_equal_integer3(int a,BinTerm *t);
extern OID  claire__I_equal_equal_integer4(int a,LinTerm *t);
extern IntConstraint * claire__I_equal_equal_IntVar3(IntVar *x,UnTerm *t);
extern OID  claire__I_equal_equal_IntVar4(IntVar *x,BinTerm *t);
extern OID  claire__I_equal_equal_IntVar5(IntVar *x,LinTerm *t);
extern OID  claire__I_equal_equal_UnTerm1(UnTerm *t1,BinTerm *t2);
extern OID  claire__I_equal_equal_UnTerm2(UnTerm *t1,LinTerm *t2);
extern OID  claire__I_equal_equal_BinTerm1(BinTerm *t1,LinTerm *t2);
extern UnIntConstraint * claire__I_equal_equal_UnTerm3(UnTerm *t,int c);
extern IntConstraint * claire__I_equal_equal_UnTerm4(UnTerm *t,IntVar *x);
extern IntConstraint * claire__I_equal_equal_UnTerm5(UnTerm *t1,UnTerm *t2);
extern IntConstraint * claire__I_equal_equal_BinTerm2(BinTerm *t1,int c);
extern LinComb * claire__I_equal_equal_BinTerm3(BinTerm *t1,IntVar *x);
extern LinComb * claire__I_equal_equal_BinTerm4(BinTerm *t1,UnTerm *t2);
extern LinComb * claire__I_equal_equal_BinTerm5(BinTerm *t1,BinTerm *t2);
extern LinComb * claire__I_equal_equal_LinTerm1(LinTerm *t1,int c);
extern LinComb * claire__I_equal_equal_LinTerm2(LinTerm *t1,IntVar *x);
extern LinComb * claire__I_equal_equal_LinTerm3(LinTerm *t1,UnTerm *t2);
extern LinComb * claire__I_equal_equal_LinTerm4(LinTerm *t1,BinTerm *t2);
extern LinComb * claire__I_equal_equal_LinTerm5(LinTerm *t1,LinTerm *t2);
extern LargeDisjunction * claire_or_LargeDisjunction1(LargeDisjunction *c1,LargeDisjunction *c2);
extern LargeDisjunction * claire_or_Disjunction1(Disjunction *c1,Disjunction *c2);
extern LargeDisjunction * claire_or_LargeDisjunction2(LargeDisjunction *c1,Disjunction *c2);
extern LargeDisjunction * claire_or_Disjunction2(Disjunction *c1,LargeDisjunction *c2);
extern LargeDisjunction * claire_or_Disjunction3(Disjunction *c1,AbstractConstraint *c2);
extern LargeDisjunction * claire_or_LargeDisjunction3(LargeDisjunction *c1,AbstractConstraint *c2);
extern LargeDisjunction * claire_or_AbstractConstraint1(AbstractConstraint *c1,Disjunction *c2);
extern LargeDisjunction * claire_or_AbstractConstraint2(AbstractConstraint *c1,LargeDisjunction *c2);
extern Disjunction * claire_or_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2);
extern LargeDisjunction * claire_or_list(list *l);
extern LargeConjunction * claire_and_Conjunction1(Conjunction *c1,Conjunction *c2);
extern LargeConjunction * claire_and_LargeConjunction1(LargeConjunction *c1,LargeConjunction *c2);
extern LargeConjunction * claire_and_LargeConjunction2(LargeConjunction *c1,Conjunction *c2);
extern LargeConjunction * claire_and_Conjunction2(Conjunction *c1,LargeConjunction *c2);
extern LargeConjunction * claire_and_Conjunction3(Conjunction *c1,AbstractConstraint *c2);
extern LargeConjunction * claire_and_LargeConjunction3(LargeConjunction *c1,AbstractConstraint *c2);
extern LargeConjunction * claire_and_AbstractConstraint1(AbstractConstraint *c1,Conjunction *c2);
extern LargeConjunction * claire_and_AbstractConstraint2(AbstractConstraint *c1,LargeConjunction *c2);
extern Conjunction * claire_and_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2);
extern LargeConjunction * claire_and_list(list *l);
extern CompositeConstraint * claire_implies_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern Guard * claire_ifThen_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern Equiv * claire_ifOnlyIf_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern Cardinality * choco_makeCardinalityConstraint_list(list *l,IntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost);
extern Cardinality * choco_card_list1(list *l,IntVar *v);
extern Cardinality * choco_atleast_list1(list *l,IntVar *v);
extern Cardinality * choco_atmost_list1(list *l,IntVar *v);
extern Cardinality * choco_card_list2(list *l,int v);
extern Cardinality * choco_atleast_list2(list *l,int v);
extern Cardinality * choco_atmost_list2(list *l,int v);
extern Occurrence * choco_makeOccurConstraint_OccurTerm(OccurTerm *ot,IntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost);
extern OccurTerm * choco_occur_integer(int tgt,list *l);
extern Occurrence * claire__equal_equal_OccurTerm1(OccurTerm *ot,IntVar *x);
extern Occurrence * claire__equal_equal_IntVar6(IntVar *x,OccurTerm *ot);
extern Occurrence * claire__equal_equal_OccurTerm2(OccurTerm *ot,int x);
extern Occurrence * claire__equal_equal_integer5(int x,OccurTerm *ot);
extern Occurrence * claire__sup_equal_OccurTerm1(OccurTerm *ot,IntVar *x);
extern Occurrence * claire__sup_equal_OccurTerm2(OccurTerm *ot,int x);
extern Occurrence * claire__inf_equal_OccurTerm1(OccurTerm *ot,IntVar *x);
extern Occurrence * claire__inf_equal_OccurTerm2(OccurTerm *ot,int x);
extern Occurrence * claire__sup_equal_IntVar6(IntVar *x,OccurTerm *ot);
extern Occurrence * claire__sup_equal_integer5(int x,OccurTerm *ot);
extern Occurrence * claire__inf_equal_IntVar6(IntVar *x,OccurTerm *ot);
extern Occurrence * claire__inf_equal_integer6(int x,OccurTerm *ot);
extern EltTerm * choco_getNth_list1(list *l,UnTerm *i);
extern EltTerm * choco_getNth_list2(list *l,IntVar *i);
extern Elt * claire__equal_equal_EltTerm1(EltTerm *t,IntVar *x);
extern Elt * claire__equal_equal_IntVar7(IntVar *x,EltTerm *t);
extern Elt * claire__equal_equal_EltTerm2(EltTerm *t,int x);
extern Elt * claire__equal_equal_integer6(int x,EltTerm *t);
extern Elt2Term * choco_makeElt2Term_table(table *l,int n1,int n2,IntVar *i,IntVar *j);
extern Elt2Term * choco_getNth_table(table *l,IntVar *i,IntVar *j);
extern Elt2 * claire__equal_equal_Elt2Term1(Elt2Term *t,IntVar *x);
extern Elt2 * claire__equal_equal_IntVar8(IntVar *x,Elt2Term *t);
extern Elt2 * claire__equal_equal_Elt2Term2(Elt2Term *t,int x);
extern Elt2 * claire__equal_equal_integer7(int x,Elt2Term *t);

// namespace class for choco 
class chocoClass: public NameSpace {
public:

operation * div_dash;
operation * div_plus;
global_variable * MAXINT;
global_variable * MININT;
global_variable * UNKNOWNINT;
ClaireClass * _Util;
ClaireClass * _StoredInt;
ClaireClass * _Matrix;
ClaireClass * _Matrix2D;
ClaireClass * _MatrixND;
ClaireClass * _BoolMatrix2D;
ClaireClass * _BoolMatrixND;
ClaireClass * _IntMatrix2D;
ClaireClass * _IntMatrixND;
ClaireClass * _IntSetAnnotation;
ClaireClass * _IntSetIntAnnotation;
ClaireClass * _IntSetBoolAnnotation;
ClaireClass * _BipartiteSet;
global_variable * CHOCO_RELEASE;
global_variable * EXTENDABLE_CHOCO;
global_variable * DDEBUG;
global_variable * PVIEW;
global_variable * PTALK;
global_variable * PDEBUG;
global_variable * GPVIEW;
global_variable * GPTALK;
global_variable * GPDEBUG;
global_variable * SVIEW;
global_variable * STALK;
global_variable * SDEBUG;
global_variable * IVIEW;
global_variable * ITALK;
global_variable * IDEBUG;
global_variable * LVIEW;
global_variable * LTALK;
global_variable * LDEBUG;
global_variable * CHOCOBENCH_VIEW;
global_variable * CHOCOTEST_VIEW;
ClaireClass * _Ephemeral;
ClaireClass * _Problem;
ClaireClass * _Solver;
ClaireClass * _LocalSearchSolver;
ClaireClass * _GlobalSearchSolver;
ClaireClass * _AbstractConstraint;
ClaireClass * _IntConstraint;
ClaireClass * _AbstractVar;
ClaireClass * _IntVar;
ClaireClass * _SetVar;
ClaireClass * _PropagationEvent;
ClaireClass * _ConstAwakeEvent;
ClaireClass * _VarEvent;
ClaireClass * _Instantiation;
ClaireClass * _InstInt;
ClaireClass * _InstSet;
ClaireClass * _ValueRemovals;
ClaireClass * _BoundUpdate;
ClaireClass * _IncInf;
ClaireClass * _DecSup;
ClaireClass * _IncKer;
ClaireClass * _DevEnv;
ClaireClass * _LogicEngine;
ClaireClass * _PropagationEngine;
ClaireClass * _InvariantEngine;
ClaireClass * _AbstractBranching;
ClaireClass * _GlobalSearchLimit;
ClaireClass * _ConstructiveHeuristic;
ClaireClass * _MoveNeighborhood;
ClaireClass * _Solution;
global_variable * CURRENT_PB;
property * setActiveProblem;
property * getActiveProblem;
ClaireClass * _ConflictCount;
ClaireClass * _NodeLimit;
ClaireClass * _BacktrackLimit;
ClaireClass * _TimeLimit;
ClaireClass * _AbstractDomain;
ClaireClass * _AbstractIntDomain;
property * containsValInDomain;
property * remove;
property * restrict;
ClaireClass * _AbstractSetDomain;
property * getDomainCard;
property * getNextValue;
property * getPrevValue;
property * removeDomainVal;
property * domainSequence;
property * domainSet;
global_variable * DINF;
global_variable * DSUP;
ClaireClass * _LinkedListIntDomain;
ClaireClass * _BitSetDomain;
ClaireClass * _BitListSetDomain;
ClaireClass * _DecEnv;
ClaireClass * _EventCollection;
ClaireClass * _EventQueue;
ClaireClass * _BoundEventQueue;
ClaireClass * _RemovalEventQueue;
ClaireClass * _InstantiationStack;
ClaireClass * _ConstAwakeEventQueue;
ClaireClass * _ChocEngine;
property * raiseOverflowWarning;
property * getNextActiveEventQueue;
property * propagateEvent;
property * isInstantiated;
property * getInf;
property * getSup;
operation * isInstantiatedTo;
operation * canBeInstantiatedTo;
operation * canBeEqualTo;
operation * domainIncludedIn;
operation * canBeInstantiatedIn;
ClaireClass * _UnIntConstraint;
ClaireClass * _BinIntConstraint;
ClaireClass * _TernIntConstraint;
ClaireClass * _LargeIntConstraint;
ClaireClass * _Delayer;
ClaireClass * _CompositeConstraint;
ClaireClass * _BinCompositeConstraint;
ClaireClass * _LargeCompositeConstraint;
property * getNbVars;
property * connect;
property * disconnect;
property * reconnect;
property * connectIntVar;
property * connectIntVarEvents;
property * disconnectIntVar;
property * disconnectEvent;
property * disconnectIntVarEvents;
property * reconnectEvent;
property * reconnectIntVarEvents;
property * isActive;
property * setConstraintIndex;
property * doPropagate;
property * doAwake;
property * doAwakeOnVar;
property * askIfTrue;
property * doAwakeOnInf;
property * doAwakeOnSup;
property * doAwakeOnInst;
property * doAwakeOnRem;
property * testIfTrue;
property * testIfInstantiated;
property * getPriority;
property * getVar;
property * propagate;
property * awake;
property * awakeOnInf;
property * awakeOnSup;
property * awakeOnInst;
property * awakeOnRem;
property * awakeOnVar;
property * awakeOnKer;
property * awakeOnEnv;
property * awakeOnInstSet;
property * askIfEntailed;
property * testIfSatisfied;
property * testIfCompletelyInstantiated;
property * connectEvent;
ClaireClass * _BinRelation;
property * getTruthValue;
ClaireClass * _TruthTest;
ClaireClass * _TruthTable2D;
ClaireClass * _CSPBinConstraint;
ClaireClass * _AC3BinConstraint;
ClaireClass * _AC4BinConstraint;
ClaireClass * _AC2001BinConstraint;
ClaireClass * _CSPLargeConstraint;
ClaireClass * _GreaterOrEqualxc;
ClaireClass * _LessOrEqualxc;
ClaireClass * _Equalxc;
ClaireClass * _NotEqualxc;
ClaireClass * _Equalxyc;
ClaireClass * _NotEqualxyc;
ClaireClass * _GreaterOrEqualxyc;
global_variable * EQ;
global_variable * GEQ;
global_variable * NEQ;
ClaireClass * _LinComb;
property * propagateNewLowerBound;
property * propagateNewUpperBound;
property * filter;
ClaireClass * _Elt;
ClaireClass * _Elt2;
ClaireClass * _AllDiff;
ClaireClass * _Occurrence;
ClaireClass * _BinBoolConstraint;
ClaireClass * _LargeBoolConstraint;
ClaireClass * _BinBoolConstraintWCounterOpp;
ClaireClass * _LargeBoolConstraintWCounterOpp;
ClaireClass * _Disjunction;
ClaireClass * _Guard;
ClaireClass * _Equiv;
ClaireClass * _Conjunction;
ClaireClass * _LargeConjunction;
ClaireClass * _LargeDisjunction;
ClaireClass * _Cardinality;
global_variable * SPVIEW;
global_variable * SPTALK;
ClaireClass * _SetConstraint;
ClaireClass * _UnSetConstraint;
ClaireClass * _BinSetIntConstraint;
ClaireClass * _BinSetConstraint;
ClaireClass * _TernSetConstraint;
ClaireClass * _SetCard;
ClaireClass * _MemberC;
ClaireClass * _NotMemberC;
ClaireClass * _MemberX;
ClaireClass * _NotMemberX;
ClaireClass * _SetIntersection;
ClaireClass * _SetUnion;
ClaireClass * _SubSet;
ClaireClass * _DisjointSets;
ClaireClass * _OverlappingSets;
property * selectBranchingObject;
property * goDownBranch;
property * traceDownBranch;
property * getFirstBranch;
property * getNextBranch;
property * goUpBranch;
property * traceUpBranch;
property * finishedBranching;
property * branchOn;
ClaireClass * _LargeBranching;
ClaireClass * _BinBranching;
ClaireClass * _CompositeBranching;
ClaireClass * _VarSelector;
ClaireClass * _MinDomain;
ClaireClass * _MaxDeg;
ClaireClass * _DomDeg;
ClaireClass * _StaticVarOrder;
ClaireClass * _ValIterator;
ClaireClass * _IncreasingDomain;
ClaireClass * _DecreasingDomain;
ClaireClass * _ValSelector;
ClaireClass * _MidValHeuristic;
ClaireClass * _MinValHeuristic;
ClaireClass * _MaxValHeuristic;
ClaireClass * _AssignVar;
ClaireClass * _SplitDomain;
ClaireClass * _AssignOrForbid;
ClaireClass * _SettleBinDisjunction;
ClaireClass * _Solve;
ClaireClass * _AbstractOptimize;
property * getObjectiveValue;
ClaireClass * _BranchAndBound;
ClaireClass * _OptimizeWithRestarts;
property * explore;
ClaireClass * _AssignmentHeuristic;
ClaireClass * _FlipNeighborhood;
ClaireClass * _MultipleDescent;
ClaireClass * _Term;
ClaireClass * _LinTerm;
operation * scalar;
operation * _equal_equal;
operation * _I_equal_equal;
ClaireClass * _UnTerm;
ClaireClass * _BinTerm;
operation * implies;
operation * ifOnlyIf;
ClaireClass * _OccurTerm;
ClaireClass * _EltTerm;
ClaireClass * _Elt2Term;
property * sum;// claire/"sum"
property * product;// claire/"product"
property * count;// claire/"count"
property * abs;// claire/"abs"
property * stringFormat;// claire/"stringFormat"
property * knownInt;// choco/"knownInt"
property * latestValue;// claire/"latestValue"
property * latestUpdate;// claire/"latestUpdate"
property * backtrackable;// choco/"backtrackable"
property * size1;// choco/"size1"
property * offset1;// choco/"offset1"
property * size2;// choco/"size2"
property * offset2;// choco/"offset2"
property * dim;// choco/"dim"
property * lsizes;// choco/"lsizes"
property * offsetArray;// choco/"offsetArray"
property * contents;// choco/"contents"
property * make2DBoolMatrix;// choco/"make2DBoolMatrix"
property * make2DIntMatrix;// choco/"make2DIntMatrix"
property * makeNDBoolMatrix;// choco/"makeNDBoolMatrix"
property * makeNDIntMatrix;// choco/"makeNDIntMatrix"
property * flatIndex;// choco/"flatIndex"
property * offset;// choco/"offset"
property * asize;// choco/"asize"
property * getOriginalMin;// choco/"getOriginalMin"
property * getOriginalMax;// choco/"getOriginalMax"
property * intValues;// choco/"intValues"
property * getIntAnnotation;// choco/"getIntAnnotation"
property * setIntAnnotation;// choco/"setIntAnnotation"
property * makeIntSetIntAnnotation;// choco/"makeIntSetIntAnnotation"
property * boolValues;// choco/"boolValues"
property * getBoolAnnotation;// choco/"getBoolAnnotation"
property * setBoolAnnotation;// choco/"setBoolAnnotation"
property * makeIntSetBoolAnnotation;// choco/"makeIntSetBoolAnnotation"
property * defaultValue;// choco/"defaultValue"
property * objs;// choco/"objs"
property * nbLeft;// choco/"nbLeft"
property * indices;// choco/"indices"
property * makeIndexesTable;// choco/"makeIndexesTable"
property * makeBipartiteSet;// choco/"makeBipartiteSet"
property * swap;// choco/"swap"
property * moveLeft;// choco/"moveLeft"
property * moveRight;// choco/"moveRight"
property * moveAllLeft;// choco/"moveAllLeft"
property * moveAllRight;// choco/"moveAllRight"
property * addRight;// choco/"addRight"
property * addLeft;// choco/"addLeft"
property * isLeft;// choco/"isLeft"
property * isIn;// choco/"isIn"
property * getNbLeft;// choco/"getNbLeft"
property * getNbRight;// choco/"getNbRight"
property * getNbObjects;// choco/"getNbObjects"
property * getObject;// choco/"getObject"
property * leftPart;// choco/"leftPart"
property * rightPart;// choco/"rightPart"
property * getBitCount;// choco/"getBitCount"
property * getMinBitIndex;// choco/"getMinBitIndex"
property * getMaxBitIndex;// choco/"getMaxBitIndex"
property * makeAllOnesBitVector;// choco/"makeAllOnesBitVector"
property * getBitVectorList;// choco/"getBitVectorList"
property * isInBitVectorList;// choco/"isInBitVectorList"
property * addToBitVectorList;// choco/"addToBitVectorList"
property * remFromBitVectorList;// choco/"remFromBitVectorList"
property * getBitVectorListCount;// choco/"getBitVectorListCount"
property * getBitVectorInf;// choco/"getBitVectorInf"
property * getBitVectorSup;// choco/"getBitVectorSup"
property * showChocoLicense;// choco/"showChocoLicense"
property * algo;// choco/"algo"
property * time;// choco/"time"
property * nbBk;// choco/"nbBk"
property * nbNd;// choco/"nbNd"
property * objectiveValue;// choco/"objectiveValue"
property * lval;// choco/"lval"
property * makeSolution;// choco/"makeSolution"
property * constraints;// choco/"constraints"
property * setVars;// choco/"setVars"
property * feasible;// choco/"feasible"
property * solved;// choco/"solved"
property * feasibleMode;// choco/"feasibleMode"
property * propagationEngine;// choco/"propagationEngine"
property * globalSearchSolver;// choco/"globalSearchSolver"
property * invariantEngine;// choco/"invariantEngine"
property * localSearchSolver;// choco/"localSearchSolver"
property * getIntVar;// choco/"getIntVar"
property * getSetVar;// choco/"getSetVar"
property * problem;// choco/"problem"
property * maxSize;// choco/"maxSize"
property * propagationOverflow;// choco/"propagationOverflow"
property * contradictionCause;// choco/"contradictionCause"
property * nbViolatedConstraints;// choco/"nbViolatedConstraints"
property * minNbViolatedConstraints;// choco/"minNbViolatedConstraints"
property * assignedThenUnassignedVars;// choco/"assignedThenUnassignedVars"
property * lastAssignedVar;// choco/"lastAssignedVar"
property * conflictingVars;// choco/"conflictingVars"
property * solutions;// choco/"solutions"
property * varsToStore;// choco/"varsToStore"
property * varsToShow;// choco/"varsToShow"
property * maxNb;// choco/"maxNb"
property * maxMsec;// choco/"maxMsec"
property * stopAtFirstSol;// choco/"stopAtFirstSol"
property * nbSol;// choco/"nbSol"
property * maxNbBk;// choco/"maxNbBk"
property * maxPrintDepth;// choco/"maxPrintDepth"
property * maxSolutionStorage;// choco/"maxSolutionStorage"
property * branchingComponents;// choco/"branchingComponents"
property * baseWorld;// choco/"baseWorld"
property * limits;// choco/"limits"
property * manager;// choco/"manager"
property * nextBranching;// choco/"nextBranching"
property * buildAssignment;// choco/"buildAssignment"
property * changeAssignment;// choco/"changeAssignment"
property * maxNbLocalSearch;// choco/"maxNbLocalSearch"
property * maxNbLocalMoves;// choco/"maxNbLocalMoves"
property * getDomainInf;// choco/"getDomainInf"
property * getDomainSup;// choco/"getDomainSup"
property * updateDomainInf;// choco/"updateDomainInf"
property * updateDomainSup;// choco/"updateDomainSup"
property * bucketSize;// choco/"bucketSize"
property * makeLinkedListIntDomain;// choco/"makeLinkedListIntDomain"
property * isIncludedIn;// choco/"isIncludedIn"
property * minValue;// choco/"minValue"
property * kernelSize;// choco/"kernelSize"
property * enveloppeSize;// choco/"enveloppeSize"
property * getKernel;// choco/"getKernel"
property * getEnveloppe;// choco/"getEnveloppe"
property * getKernelSize;// choco/"getKernelSize"
property * getEnveloppeSize;// choco/"getEnveloppeSize"
property * getKernelInf;// choco/"getKernelInf"
property * getKernelSup;// choco/"getKernelSup"
property * getEnveloppeInf;// choco/"getEnveloppeInf"
property * getEnveloppeSup;// choco/"getEnveloppeSup"
property * isInEnveloppe;// choco/"isInEnveloppe"
property * isInKernel;// choco/"isInKernel"
property * updateKernel;// choco/"updateKernel"
property * updateEnveloppe;// choco/"updateEnveloppe"
property * kernelBitVector;// choco/"kernelBitVector"
property * enveloppeBitVector;// choco/"enveloppeBitVector"
property * makeBitSetDomain;// choco/"makeBitSetDomain"
property * kernelBitVectorList;// choco/"kernelBitVectorList"
property * enveloppeBitVectorList;// choco/"enveloppeBitVectorList"
property * makeBitListSetDomain;// choco/"makeBitListSetDomain"
property * modifiedVar;// choco/"modifiedVar"
property * nextConst;// choco/"nextConst"
property * cause;// choco/"cause"
property * getDomainKernel;// choco/"getDomainKernel"
property * idxInQueue;// choco/"idxInQueue"
property * inf;// choco/"inf"
property * sup;// choco/"sup"
property * getDomainEnveloppe;// choco/"getDomainEnveloppe"
property * maxVals;// choco/"maxVals"
property * nbVals;// choco/"nbVals"
property * many;// choco/"many"
property * valueStack;// choco/"valueStack"
property * causeStack;// choco/"causeStack"
property * touchedConst;// choco/"touchedConst"
property * initialized;// choco/"initialized"
property * priority;// choco/"priority"
property * engine;// choco/"engine"
property * qsize;// choco/"qsize"
property * qLastRead;// choco/"qLastRead"
property * qLastEnqueued;// choco/"qLastEnqueued"
property * isPopping;// choco/"isPopping"
property * eventQueue;// choco/"eventQueue"
property * redundantEvent;// choco/"redundantEvent"
property * sLastRead;// choco/"sLastRead"
property * sLastPushed;// choco/"sLastPushed"
property * partition;// choco/"partition"
property * removalEvtQueue;// choco/"removalEvtQueue"
property * boundEvtQueue;// choco/"boundEvtQueue"
property * instEvtStack;// choco/"instEvtStack"
property * delayedConst1;// choco/"delayedConst1"
property * delayedConst2;// choco/"delayedConst2"
property * delayedConst3;// choco/"delayedConst3"
property * delayedConst4;// choco/"delayedConst4"
property * nbPendingInitConstAwakeEvent;// choco/"nbPendingInitConstAwakeEvent"
property * nbPendingVarEvent;// choco/"nbPendingVarEvent"
property * delayLinCombThreshold;// choco/"delayLinCombThreshold"
property * raiseContradiction;// choco/"raiseContradiction"
property * flushCurrentOpenEvents;// choco/"flushCurrentOpenEvents"
property * getContradictionCause;// choco/"getContradictionCause"
property * getProblem;// choco/"getProblem"
property * makeChocEngine;// choco/"makeChocEngine"
property * attachPropagationEngine;// choco/"attachPropagationEngine"
property * isEmpty;// choco/"isEmpty"
property * popNextEvent;// choco/"popNextEvent"
property * nextEventPostIndex;// choco/"nextEventPostIndex"
property * flushEventQueue;// choco/"flushEventQueue"
property * updtInfEvt;// choco/"updtInfEvt"
property * updtSupEvt;// choco/"updtSupEvt"
property * remValEvt;// choco/"remValEvt"
property * checkCleanState;// choco/"checkCleanState"
property * updtKerEvt;// choco/"updtKerEvt"
property * updtEnvEvt;// choco/"updtEnvEvt"
property * pushWorld;// choco/"pushWorld"
property * popWorld;// choco/"popWorld"
property * setWorld;// choco/"setWorld"
property * commitWorld;// choco/"commitWorld"
property * postInstInt;// choco/"postInstInt"
property * postRemoveVal;// choco/"postRemoveVal"
property * postUpdateInf;// choco/"postUpdateInf"
property * postUpdateSup;// choco/"postUpdateSup"
property * postUpdateKer;// choco/"postUpdateKer"
property * postUpdateEnv;// choco/"postUpdateEnv"
property * postInstSet;// choco/"postInstSet"
property * postConstAwake;// choco/"postConstAwake"
property * postInstantiateEvt;// choco/"postInstantiateEvt"
property * instantiateEvt;// choco/"instantiateEvt"
property * postBoundEvent;// choco/"postBoundEvent"
property * getQueue;// choco/"getQueue"
property * registerEvent;// choco/"registerEvent"
property * constAwakeEvent;// choco/"constAwakeEvent"
property * constAwake;// choco/"constAwake"
property * popSomeEvents;// choco/"popSomeEvents"
property * nbConstraints;// choco/"nbConstraints"
property * doAwakeOnInstSet;// choco/"doAwakeOnInstSet"
property * doAwakeOnKer;// choco/"doAwakeOnKer"
property * doAwakeOnEnv;// choco/"doAwakeOnEnv"
property * getNextActiveConstraintEventQueue;// choco/"getNextActiveConstraintEventQueue"
property * getNextActiveVariableEventQueue;// choco/"getNextActiveVariableEventQueue"
property * hook;// choco/"hook"
property * getConstraint;// choco/"getConstraint"
property * getDegree;// choco/"getDegree"
property * savedAssignment;// choco/"savedAssignment"
property * bucket;// choco/"bucket"
property * closeIntVar;// choco/"closeIntVar"
property * updateInf;// choco/"updateInf"
property * updateSup;// choco/"updateSup"
property * instantiate;// choco/"instantiate"
property * removeVal;// choco/"removeVal"
property * hasExactDomain;// choco/"hasExactDomain"
property * getNextDomainValue;// choco/"getNextDomainValue"
property * getPrevDomainValue;// choco/"getPrevDomainValue"
property * getValue;// choco/"getValue"
property * getDomainSize;// choco/"getDomainSize"
property * removeInterval;// choco/"removeInterval"
property * restrictTo;// choco/"restrictTo"
property * setBucket;// choco/"setBucket"
property * getDomainKernelSize;// choco/"getDomainKernelSize"
property * getDomainEnveloppeSize;// choco/"getDomainEnveloppeSize"
property * getDomainEnveloppeInf;// choco/"getDomainEnveloppeInf"
property * getDomainEnveloppeSup;// choco/"getDomainEnveloppeSup"
property * getDomainKernelInf;// choco/"getDomainKernelInf"
property * getDomainKernelSup;// choco/"getDomainKernelSup"
property * isInDomainEnveloppe;// choco/"isInDomainEnveloppe"
property * isInDomainKernel;// choco/"isInDomainKernel"
property * addSetVar;// choco/"addSetVar"
property * closeSetVar;// choco/"closeSetVar"
property * addToKernel;// choco/"addToKernel"
property * remFromEnveloppe;// choco/"remFromEnveloppe"
property * setIn;// choco/"setIn"
property * setOut;// choco/"setOut"
property * fastDispatchIndex;// choco/"fastDispatchIndex"
property * violated;// choco/"violated"
property * cste;// choco/"cste"
property * v1;// choco/"v1"
property * idx1;// choco/"idx1"
property * v2;// choco/"v2"
property * idx2;// choco/"idx2"
property * v3;// choco/"v3"
property * idx3;// choco/"idx3"
property * nbVars;// choco/"nbVars"
property * closeLargeIntConstraint;// choco/"closeLargeIntConstraint"
property * target;// choco/"target"
property * delay;// choco/"delay"
property * const1;// choco/"const1"
property * const2;// choco/"const2"
property * lconst;// choco/"lconst"
property * loffset;// choco/"loffset"
property * nbConst;// choco/"nbConst"
property * additionalVars;// choco/"additionalVars"
property * additionalIndices;// choco/"additionalIndices"
property * closeCompositeConstraint;// choco/"closeCompositeConstraint"
property * getNbVarsFromSubConst;// choco/"getNbVarsFromSubConst"
property * connectHook;// choco/"connectHook"
property * reconnectHook;// choco/"reconnectHook"
property * disconnectHook;// choco/"disconnectHook"
property * assignIndices;// choco/"assignIndices"
property * getConstraintIdx;// choco/"getConstraintIdx"
property * opposite;// choco/"opposite"
property * abstractIncInf;// choco/"abstractIncInf"
property * abstractDecSup;// choco/"abstractDecSup"
property * abstractInstantiate;// choco/"abstractInstantiate"
property * abstractRemoveVal;// choco/"abstractRemoveVal"
property * addIntVar;// choco/"addIntVar"
property * connectSetVar;// choco/"connectSetVar"
property * connectSetVarEvents;// choco/"connectSetVarEvents"
property * disconnectSetVar;// choco/"disconnectSetVar"
property * disconnectSetVarEvents;// choco/"disconnectSetVarEvents"
property * reconnectSetVar;// choco/"reconnectSetVar"
property * reconnectSetVarEvents;// choco/"reconnectSetVarEvents"
property * reconnectIntVar;// choco/"reconnectIntVar"
property * isActiveIntVar;// choco/"isActiveIntVar"
property * isActiveEvent;// choco/"isActiveEvent"
property * isActiveSetVar;// choco/"isActiveSetVar"
property * test;// choco/"test"
property * matrix;// choco/"matrix"
property * setTruePair;// choco/"setTruePair"
property * feasRelation;// choco/"feasRelation"
property * feasiblePair;// choco/"feasiblePair"
property * setPassive;// choco/"setPassive"
property * see;// choco/"see"
property * makeAC3BinConstraint;// choco/"makeAC3BinConstraint"
property * reviseV2;// choco/"reviseV2"
property * reviseV1;// choco/"reviseV1"
property * nbSupport1;// choco/"nbSupport1"
property * nbSupport2;// choco/"nbSupport2"
property * validSupport1;// choco/"validSupport1"
property * validSupport2;// choco/"validSupport2"
property * makeAC4BinConstraint;// choco/"makeAC4BinConstraint"
property * decrementNbSupportV1;// choco/"decrementNbSupportV1"
property * decrementNbSupportV2;// choco/"decrementNbSupportV2"
property * resetNbSupportV1;// choco/"resetNbSupportV1"
property * resetNbSupportV2;// choco/"resetNbSupportV2"
property * currentSupport1;// choco/"currentSupport1"
property * currentSupport2;// choco/"currentSupport2"
property * makeAC2001BinConstraint;// choco/"makeAC2001BinConstraint"
property * updateSupportVal2;// choco/"updateSupportVal2"
property * updateSupportVal1;// choco/"updateSupportVal1"
property * cachedTuples;// choco/"cachedTuples"
property * feasTest;// choco/"feasTest"
property * tupleConstraint;// choco/"tupleConstraint"
property * nbPosVars;// choco/"nbPosVars"
property * coefs;// choco/"coefs"
property * op;// choco/"op"
property * improvedUpperBound;// choco/"improvedUpperBound"
property * improvedLowerBound;// choco/"improvedLowerBound"
property * makeLinComb;// choco/"makeLinComb"
property * computeVarIdxInOpposite;// choco/"computeVarIdxInOpposite"
property * computeUpperBound;// choco/"computeUpperBound"
property * computeLowerBound;// choco/"computeLowerBound"
property * filterOnImprovedLowerBound;// choco/"filterOnImprovedLowerBound"
property * filterOnImprovedUpperBound;// choco/"filterOnImprovedUpperBound"
property * makeEltConstraint;// choco/"makeEltConstraint"
property * updateValueFromIndex;// choco/"updateValueFromIndex"
property * updateIndexFromValue;// choco/"updateIndexFromValue"
property * tval;// choco/"tval"
property * dim1;// choco/"dim1"
property * dim2;// choco/"dim2"
property * makeElt2Constraint;// choco/"makeElt2Constraint"
property * isPossible;// choco/"isPossible"
property * isSure;// choco/"isSure"
property * nbPossible;// choco/"nbPossible"
property * nbSure;// choco/"nbSure"
property * constrainOnInfNumber;// choco/"constrainOnInfNumber"
property * constrainOnSupNumber;// choco/"constrainOnSupNumber"
property * checkNbPossible;// choco/"checkNbPossible"
property * checkNbSure;// choco/"checkNbSure"
property * update;// choco/"update"
property * statusBitVector;// choco/"statusBitVector"
property * closeBoolConstraint;// choco/"closeBoolConstraint"
property * knownStatus;// choco/"knownStatus"
property * getStatus;// choco/"getStatus"
property * knownTargetStatus;// choco/"knownTargetStatus"
property * getTargetStatus;// choco/"getTargetStatus"
property * setStatus;// choco/"setStatus"
property * setTargetStatus;// choco/"setTargetStatus"
property * statusBitVectorList;// choco/"statusBitVectorList"
property * nbTrueStatus;// choco/"nbTrueStatus"
property * nbFalseStatus;// choco/"nbFalseStatus"
property * oppositeConst1;// choco/"oppositeConst1"
property * oppositeConst2;// choco/"oppositeConst2"
property * indicesInOppConst1;// choco/"indicesInOppConst1"
property * indicesInOppConst2;// choco/"indicesInOppConst2"
property * closeBoolConstraintWCounterOpp;// choco/"closeBoolConstraintWCounterOpp"
property * loppositeConst;// choco/"loppositeConst"
property * indicesInOppConsts;// choco/"indicesInOppConsts"
property * checkStatus;// choco/"checkStatus"
property * checkNbFalseStatus;// choco/"checkNbFalseStatus"
property * getCardVar;// choco/"getCardVar"
property * getCardVarIdx;// choco/"getCardVarIdx"
property * awakeOnNbTrue;// choco/"awakeOnNbTrue"
property * awakeOnNbFalse;// choco/"awakeOnNbFalse"
property * sv1;// choco/"sv1"
property * sv2;// choco/"sv2"
property * sv3;// choco/"sv3"
property * setCard;// choco/"setCard"
property * memberOf;// choco/"memberOf"
property * notMemberOf;// choco/"notMemberOf"
property * setintersection;// choco/"setintersection"
property * setunion;// choco/"setunion"
property * subset;// choco/"subset"
property * disjoint;// choco/"disjoint"
property * emptyKernelIntersection;// choco/"emptyKernelIntersection"
property * overlap;// choco/"overlap"
property * alternatives;// choco/"alternatives"
property * selectBranching;// choco/"selectBranching"
property * branching;// choco/"branching"
property * selectVar;// choco/"selectVar"
property * getSmallestDomainUnassignedVar;// choco/"getSmallestDomainUnassignedVar"
property * getSearchManager;// choco/"getSearchManager"
property * getMostConstrainedUnassignedVar;// choco/"getMostConstrainedUnassignedVar"
property * getDomOverDegBestUnassignedVar;// choco/"getDomOverDegBestUnassignedVar"
property * isOver;// choco/"isOver"
property * getFirstVal;// choco/"getFirstVal"
property * getNextVal;// choco/"getNextVal"
property * getBestVal;// choco/"getBestVal"
property * varHeuristic;// choco/"varHeuristic"
property * valHeuristic;// choco/"valHeuristic"
property * dichotomyThreshold;// choco/"dichotomyThreshold"
property * disjunctions;// choco/"disjunctions"
property * doMaximize;// choco/"doMaximize"
property * objective;// choco/"objective"
property * lowerBound;// choco/"lowerBound"
property * upperBound;// choco/"upperBound"
property * targetLowerBound;// choco/"targetLowerBound"
property * targetUpperBound;// choco/"targetUpperBound"
property * getBestObjectiveValue;// choco/"getBestObjectiveValue"
property * getObjectiveTarget;// choco/"getObjectiveTarget"
property * initBounds;// choco/"initBounds"
property * getFeasibility;// choco/"getFeasibility"
property * mayExpandNewNode;// choco/"mayExpandNewNode"
property * nbIter;// choco/"nbIter"
property * baseNbSol;// choco/"baseNbSol"
property * nbBkTot;// choco/"nbBkTot"
property * nbNdTot;// choco/"nbNdTot"
property * copyParameters;// choco/"copyParameters"
property * attach;// choco/"attach"
property * composeGlobalSearch;// choco/"composeGlobalSearch"
property * storeCurrentSolution;// choco/"storeCurrentSolution"
property * makeSolutionFromCurrentState;// choco/"makeSolutionFromCurrentState"
property * storeSolution;// choco/"storeSolution"
property * existsSolution;// choco/"existsSolution"
property * restoreBestSolution;// choco/"restoreBestSolution"
property * restoreSolution;// choco/"restoreSolution"
property * setVal;// choco/"setVal"
property * showSolution;// choco/"showSolution"
property * recordSolution;// choco/"recordSolution"
property * newNode;// choco/"newNode"
property * endNode;// choco/"endNode"
property * postDynamicCut;// choco/"postDynamicCut"
property * setMax;// choco/"setMax"
property * setMin;// choco/"setMin"
property * remVal;// choco/"remVal"
property * setBranch;// choco/"setBranch"
property * newTreeSearch;// choco/"newTreeSearch"
property * endTreeSearch;// choco/"endTreeSearch"
property * setBound;// choco/"setBound"
property * setTargetBound;// choco/"setTargetBound"
property * setTargetLowerBound;// choco/"setTargetLowerBound"
property * setTargetUpperBound;// choco/"setTargetUpperBound"
property * postTargetBound;// choco/"postTargetBound"
property * postTargetLowerBound;// choco/"postTargetLowerBound"
property * postTargetUpperBound;// choco/"postTargetUpperBound"
property * run;// choco/"run"
property * newLoop;// choco/"newLoop"
property * endLoop;// choco/"endLoop"
property * recordNoSolution;// choco/"recordNoSolution"
property * oneMoreLoop;// choco/"oneMoreLoop"
property * makeConflictCount;// choco/"makeConflictCount"
property * attachInvariantEngine;// choco/"attachInvariantEngine"
property * unitaryDeltaConflict;// choco/"unitaryDeltaConflict"
property * deltaNbConflicts;// choco/"deltaNbConflicts"
property * getMinConflictValue;// choco/"getMinConflictValue"
property * computeConflictAccount;// choco/"computeConflictAccount"
property * resetInvariants;// choco/"resetInvariants"
property * getLocalSearchObjectiveValue;// choco/"getLocalSearchObjectiveValue"
property * restoreBestAssignment;// choco/"restoreBestAssignment"
property * saveAssignment;// choco/"saveAssignment"
property * setFeasibleMode;// choco/"setFeasibleMode"
property * nbLocalSearch;// choco/"nbLocalSearch"
property * nbLocalMoves;// choco/"nbLocalMoves"
property * selectMoveVar;// choco/"selectMoveVar"
property * selectValue;// choco/"selectValue"
property * assignOneVar;// choco/"assignOneVar"
property * assignVal;// choco/"assignVal"
property * build;// choco/"build"
property * eraseAssignment;// choco/"eraseAssignment"
property * newValue;// choco/"newValue"
property * move;// choco/"move"
property * changeVal;// choco/"changeVal"
property * finishedLoop;// choco/"finishedLoop"
property * initLoop;// choco/"initLoop"
property * attachLocalSearchSolver;// choco/"attachLocalSearchSolver"
property * runLocalSearch;// choco/"runLocalSearch"
property * initIterations;// choco/"initIterations"
property * finishedIterations;// choco/"finishedIterations"
property * oneMoreMove;// choco/"oneMoreMove"
property * becomesAConflict;// choco/"becomesAConflict"
property * addOneConflict;// choco/"addOneConflict"
property * becomesSatisfied;// choco/"becomesSatisfied"
property * removeOneConflict;// choco/"removeOneConflict"
property * postAssignVal;// choco/"postAssignVal"
property * postUnAssignVal;// choco/"postUnAssignVal"
property * postChangeVal;// choco/"postChangeVal"
property * makeProblem;// choco/"makeProblem"
property * discardProblem;// choco/"discardProblem"
property * getPropagationEngine;// choco/"getPropagationEngine"
property * setDelayedLinearConstraintPropagation;// choco/"setDelayedLinearConstraintPropagation"
property * makeBoundIntVar;// choco/"makeBoundIntVar"
property * makeConstantIntVar;// choco/"makeConstantIntVar"
property * makeIntVar;// choco/"makeIntVar"
property * makeSetVar;// choco/"makeSetVar"
property * post;// choco/"post"
property * makeBinTruthTest;// choco/"makeBinTruthTest"
property * makeBinRelation;// choco/"makeBinRelation"
property * binConstraint;// choco/"binConstraint"
property * feasPairConstraint;// choco/"feasPairConstraint"
property * infeasPairConstraint;// choco/"infeasPairConstraint"
property * feasBinTestConstraint;// choco/"feasBinTestConstraint"
property * infeasBinTestConstraint;// choco/"infeasBinTestConstraint"
property * feasTupleConstraint;// choco/"feasTupleConstraint"
property * infeasTupleConstraint;// choco/"infeasTupleConstraint"
property * feasTestConstraint;// choco/"feasTestConstraint"
property * lcoeff;// choco/"lcoeff"
property * lvars;// choco/"lvars"
property * sumVars;// choco/"sumVars"
property * allDifferent;// choco/"allDifferent"
property * setActive;// choco/"setActive"
property * makeMinDomVarHeuristic;// choco/"makeMinDomVarHeuristic"
property * makeDomDegVarHeuristic;// choco/"makeDomDegVarHeuristic"
property * makeMaxDegVarHeuristic;// choco/"makeMaxDegVarHeuristic"
property * makeStaticVarHeuristic;// choco/"makeStaticVarHeuristic"
property * makeIncValIterator;// choco/"makeIncValIterator"
property * makeDecValIterator;// choco/"makeDecValIterator"
property * makeMinValSelector;// choco/"makeMinValSelector"
property * makeMaxValSelector;// choco/"makeMaxValSelector"
property * makeMidValSelector;// choco/"makeMidValSelector"
property * makeAssignVarBranching;// choco/"makeAssignVarBranching"
property * makeSplitDomBranching;// choco/"makeSplitDomBranching"
property * makeAssignOrForbidBranching;// choco/"makeAssignOrForbidBranching"
property * makeDisjunctionBranching;// choco/"makeDisjunctionBranching"
property * makeDefaultBranchingList;// choco/"makeDefaultBranchingList"
property * makeGlobalSearchSolver;// choco/"makeGlobalSearchSolver"
property * makeGlobalSearchMaximizer;// choco/"makeGlobalSearchMaximizer"
property * makeGlobalSearchMinimizer;// choco/"makeGlobalSearchMinimizer"
property * makeBacktrackLimit;// choco/"makeBacktrackLimit"
property * makeNodeLimit;// choco/"makeNodeLimit"
property * setSearchLimit;// choco/"setSearchLimit"
property * solve;// choco/"solve"
property * minimize;// choco/"minimize"
property * maximize;// choco/"maximize"
property * setMaxPrintDepth;// choco/"setMaxPrintDepth"
property * setMaxSolutionStorage;// choco/"setMaxSolutionStorage"
property * setVarsToShow;// choco/"setVarsToShow"
property * getNbSol;// choco/"getNbSol"
property * getGlobalSearchSolver;// choco/"getGlobalSearchSolver"
property * setNodeLimit;// choco/"setNodeLimit"
property * setTimeLimit;// choco/"setTimeLimit"
property * setBacktrackLimit;// choco/"setBacktrackLimit"
property * setMaxNbBk;// choco/"setMaxNbBk"
property * unassignVal;// choco/"unassignVal"
property * setMaxNbLocalSearch;// choco/"setMaxNbLocalSearch"
property * setMaxNbLocalMoves;// choco/"setMaxNbLocalMoves"
property * sign1;// choco/"sign1"
property * sign2;// choco/"sign2"
property * sumTerms;// choco/"sumTerms"
property * ifThen;// claire/"ifThen"
property * makeCardinalityConstraint;// choco/"makeCardinalityConstraint"
property * card;// choco/"card"
property * atleast;// choco/"atleast"
property * atmost;// choco/"atmost"
property * makeOccurConstraint;// choco/"makeOccurConstraint"
property * occur;// choco/"occur"
property * lvalues;// choco/"lvalues"
property * indexVar;// choco/"indexVar"
property * getNth;// choco/"getNth"
property * valueTable;// choco/"valueTable"
property * index1Var;// choco/"index1Var"
property * index2Var;// choco/"index2Var"
property * makeElt2Term;// choco/"makeElt2Term"

// module definition 
 void metaLoad();};

extern chocoClass choco;

#endif
