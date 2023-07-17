// interface defination for module palm, Thu Feb 13 21:14:38 2003
#ifndef CLAIREH_palm
#define CLAIREH_palm


class PalmTools;
class PalmIntVar;
class PalmSolver;
class PalmUserFriendlyBox;
class PalmProblem;
class PalmEngine;
class DecInf;
class IncSup;
class ValueRestorations;
class Explanation;
class PalmContradictionExplanation;
class PalmBoundExplanation;
class PalmIncInfExplanation;
class PalmDecSupExplanation;
class PalmRemovalExplanation;
class PalmIntDomain;
class PalmContradiction;
class PalmSolverTools;
class PalmState;
class PalmLearn;
class PathRepairLearn;
class PalmExtend;
class PalmBranching;
class PalmUnsureExtend;
class PalmRepair;
class PalmUnsureRepair;
class PalmSolution;
class PalmBranchAndBound;
class PalmControlConstraint;
class PalmInfoConstraint;
class PalmUnIntConstraint;
class PalmBinIntConstraint;
class PalmLargeIntConstraint;
class PalmGreaterOrEqualxc;
class PalmLessOrEqualxc;
class PalmNotEqualxc;
class PalmEqualxc;
class AssignmentConstraint;
class PalmNotEqualxyc;
class PalmEqualxyc;
class PalmGreaterOrEqualxyc;
class PalmLessOrEqualxyc;
class PalmLinComb;
class PalmCompleteAllDiff;
class PalmPermutation;
class PalmGlobalCardinality;
class PalmGlobalCardinalityVar;
class PalmOccurrence;
class PalmElt;
class PalmAC4BinConstraint;
class PalmAssignVar;
class PalmAssignMinDomVar;
class PalmAssignMinDomDegVar;
class PalmDisjunction;
class PalmGuard;
class PalmEquiv;
class PalmConjunction;
class PalmLargeConjunction;
class PalmLargeDisjunction;
class PalmCardinality;
class PalmTempTerm;
class PalmUnTerm;
class PalmBinTerm;
class PalmLinTerm;
class PalmOccurTerm;
class PalmEltTerm;

class PalmTools: public Ephemeral{ 
  public:} 
;

class PalmIntVar: public IntVar{ 
  public:
     int originalInf;
     int originalSup;
     list *explanationOnInf;
     list *explanationOnSup;
     table *enumerationConstraints;
     table *negEnumerationConstraints;
     DecInf *restInfEvt;
     IncSup *restSupEvt;
     ValueRestorations *restValEvt;} 
;

class PalmSolver: public Solver{ 
  public:
     list *statistics;
     ClaireBoolean *finished;
     ClaireBoolean *feasible;
     PalmState *state;
     PalmLearn *learning;
     PalmExtend *extending;
     PalmRepair *repairing;} 
;

class PalmUserFriendlyBox: public PalmTools{ 
  public:
     char *shortName;
     char *name;
     list *constraints;
     PalmUserFriendlyBox *fatherBox;
     list *childrenBoxes;} 
;

class PalmProblem: public Problem{ 
  public:
     int maxRelaxLvl;
     PalmSolver *palmSolver;
     PalmUserFriendlyBox *rootUFboxes;
     set *relaxedUFboxes;
     set *allUFboxes;
     set *userRepresentation;} 
;

class PalmEngine: public ChocEngine{ 
  public:
     list *toBeAwakenConstraints;
     BoundEventQueue *boundRestEvtQueue;
     RemovalEventQueue *removalRestEvtQueue;
     PalmIntVar *dummyVariable;
     ClaireBoolean *contradictory;} 
;

class DecInf: public BoundUpdate{ 
  public:} 
;

class IncSup: public BoundUpdate{ 
  public:} 
;

class ValueRestorations: public ValueRemovals{ 
  public:} 
;

class Explanation: public ClaireCollection{ 
  public:
     set *explanation;} 
;

class PalmContradictionExplanation: public Explanation{ 
  public:} 
;

class PalmBoundExplanation: public Explanation{ 
  public:
     int previousValue;
     PalmIntVar *variable;} 
;

class PalmIncInfExplanation: public PalmBoundExplanation{ 
  public:} 
;

class PalmDecSupExplanation: public PalmBoundExplanation{ 
  public:} 
;

class PalmRemovalExplanation: public Explanation{ 
  public:
     int value;
     PalmIntVar *variable;} 
;

class PalmIntDomain: public AbstractIntDomain{ 
  public:
     int offset;
     int bucketSize;
     int nbElements;
     list *booleanVector;
     list *precVector;
     list *succVector;
     int firstSuccPres;
     int firstPrecPres;
     int firstSuccAbs;
     int firstPrecAbs;
     ClaireBoolean *needInfComputation;
     ClaireBoolean *needSupComputation;
     list *explanationOnVal;
     list *instantiationConstraints;
     list *negInstantiationConstraints;} 
;

class PalmContradiction: public ClaireException{ 
  public:} 
;

class PalmSolverTools: public PalmTools{ 
  public:
     PalmSolver *manager;} 
;

class PalmState: public PalmSolverTools{ 
  public:
     Explanation *path;} 
;

class PalmLearn: public PalmSolverTools{ 
  public:} 
;

class PathRepairLearn: public PalmLearn{ 
  public:
     int maxMoves;
     list *explanations;
     int maxSize;
     int lastExplanation;
     ClaireBoolean *isFull;} 
;

class PalmExtend: public PalmSolverTools{ 
  public:
     PalmBranching *branching;} 
;

class PalmBranching: public Ephemeral{ 
  public:
     PalmExtend *extender;
     PalmBranching *nextBranching;} 
;

class PalmUnsureExtend: public PalmExtend{ 
  public:} 
;

class PalmRepair: public PalmSolverTools{ 
  public:} 
;

class PalmUnsureRepair: public PalmRepair{ 
  public:} 
;

class PalmSolution: public Solution{ 
  public:
     ClaireBoolean *feasible;
     list *lstat;} 
;

class PalmBranchAndBound: public PalmSolver{ 
  public:
     ClaireBoolean *maximizing;
     IntVar *objective;
     int lowerBound;
     int upperBound;
     int optimum;
     list *dynamicCuts;} 
;

class PalmControlConstraint: public Ephemeral{ 
  public:
     AbstractConstraint *constraint;
     int index;} 
;

class PalmInfoConstraint: public PalmTools{ 
  public:
     int timeStamp;
     int weight;
     ClaireBoolean *everConnected;
     ClaireBoolean *indirect;
     set *addingExplanation;
     set *dependingConstraints;
     set *dependencyNet;
     OID searchInfo;
     PalmUserFriendlyBox *ufBox;
     list *controllingConstraints;
     OID info;} 
;

class PalmUnIntConstraint: public UnIntConstraint{ 
  public:} 
;

class PalmBinIntConstraint: public BinIntConstraint{ 
  public:} 
;

class PalmLargeIntConstraint: public LargeIntConstraint{ 
  public:} 
;

class PalmGreaterOrEqualxc: public PalmUnIntConstraint{ 
  public:} 
;

class PalmLessOrEqualxc: public PalmUnIntConstraint{ 
  public:} 
;

class PalmNotEqualxc: public PalmUnIntConstraint{ 
  public:} 
;

class PalmEqualxc: public PalmUnIntConstraint{ 
  public:} 
;

class AssignmentConstraint: public PalmEqualxc{ 
  public:} 
;

class PalmNotEqualxyc: public PalmBinIntConstraint{ 
  public:} 
;

class PalmEqualxyc: public PalmBinIntConstraint{ 
  public:} 
;

class PalmGreaterOrEqualxyc: public PalmBinIntConstraint{ 
  public:} 
;

class PalmLessOrEqualxyc: public PalmBinIntConstraint{ 
  public:} 
;

class PalmLinComb: public LinComb{ 
  public:} 
;

class PalmCompleteAllDiff: public CompleteAllDiff{ 
  public:} 
;

class PalmPermutation: public Permutation{ 
  public:} 
;

class PalmGlobalCardinality: public GlobalCardinality{ 
  public:} 
;

class PalmGlobalCardinalityVar: public PalmGlobalCardinality{ 
  public:
     list *intervals;} 
;

class PalmOccurrence: public Occurrence{ 
  public:
     ClaireBoolean *checkPossible;
     ClaireBoolean *checkSure;
     list *checkInf;
     list *checkSup;} 
;

class PalmElt: public Elt{ 
  public:} 
;

class PalmAC4BinConstraint: public PalmBinIntConstraint{ 
  public:
     ClaireBoolean *cachedTuples;
     BoolMatrix2D *matrix;
     method *feasTest;
     list *supportsOnV1;
     list *nbSupportsOnV1;
     list *supportsOnV2;
     list *nbSupportsOnV2;} 
;

class PalmAssignVar: public PalmBranching{ 
  public:} 
;

class PalmAssignMinDomVar: public PalmAssignVar{ 
  public:} 
;

class PalmAssignMinDomDegVar: public PalmAssignVar{ 
  public:} 
;

class PalmDisjunction: public Disjunction{ 
  public:
     ClaireBoolean *isContradictory;
     ClaireBoolean *needToAwakeC1;
     ClaireBoolean *needToAwakeC2;} 
;

class PalmGuard: public Guard{ 
  public:
     ClaireBoolean *needToAwakeC2;} 
;

class PalmEquiv: public Equiv{ 
  public:
     ClaireBoolean *isContradictory;
     ClaireBoolean *needToAwakeC1;
     ClaireBoolean *needToAwakeNegC1;
     ClaireBoolean *needToAwakeC2;
     ClaireBoolean *needToAwakeNegC2;} 
;

class PalmConjunction: public Conjunction{ 
  public:} 
;

class PalmLargeConjunction: public LargeConjunction{ 
  public:} 
;

class PalmLargeDisjunction: public LargeDisjunction{ 
  public:
     list *needToAwake;
     ClaireBoolean *isContradictory;} 
;

class PalmCardinality: public Cardinality{ 
  public:
     list *needToAwake;
     ClaireBoolean *isContradictory;
     ClaireBoolean *needToAwakeTrue;
     ClaireBoolean *needToAwakeFalse;} 
;

class PalmTempTerm: public PalmTools{ 
  public:
     int cste;} 
;

class PalmUnTerm: public PalmTempTerm{ 
  public:
     PalmIntVar *v1;
     ClaireBoolean *sign1;} 
;

class PalmBinTerm: public PalmTempTerm{ 
  public:
     PalmIntVar *v1;
     PalmIntVar *v2;
     ClaireBoolean *sign1;
     ClaireBoolean *sign2;} 
;

class PalmLinTerm: public PalmTempTerm{ 
  public:
     list *lcoeff;
     list *lvars;} 
;

class PalmOccurTerm: public Term{ 
  public:
     int target;
     list *lvars;} 
;

class PalmEltTerm: public EltTerm{ 
  public:} 
;
extern char * palm_palmVersion_void();
extern char * palm_palmReleaseDate_void();
extern char * palm_palmInfo_void();
extern OID  palm_showPalmLicense_void();
extern int  palm_not_I_integer(int x);
extern int  claire_sum_list(list *l);
extern int  palm_firstCode29bits_PalmIntDomain(PalmIntDomain *ed,int x);
extern int  palm_secondCode29bits_PalmIntDomain(PalmIntDomain *ed,int x);
extern int  palm_decode29bits_PalmIntDomain(PalmIntDomain *ed,int x,int i);
extern PalmIntDomain * palm_makePalmIntDomain_integer(int dinf,int dsup);
extern OID  claire_self_print_PalmIntDomain_palm(PalmIntDomain *ed);
extern list * choco_domainSequence_PalmIntDomain_palm(PalmIntDomain *ed);
extern set * choco_domainSet_PalmIntDomain_palm(PalmIntDomain *ed);
extern list * palm_removedlist_I_PalmIntDomain(PalmIntDomain *ed);
extern int  choco_getDomainCard_PalmIntDomain_palm(PalmIntDomain *ed);
extern ClaireBoolean * choco_containsValInDomain_PalmIntDomain_palm(PalmIntDomain *ed,int x);
extern int  palm_firstElement_PalmIntDomain(PalmIntDomain *ed);
extern int  choco_getInf_PalmIntDomain_palm(PalmIntDomain *ed);
extern int  choco_getSup_PalmIntDomain_palm(PalmIntDomain *ed);
extern void  palm_addDomainVal_PalmIntDomain(PalmIntDomain *ed,int z);
extern ClaireBoolean * choco_removeDomainVal_PalmIntDomain_palm(PalmIntDomain *ed,int z);
extern OID  claire_self_print_PalmIntVar_palm(PalmIntVar *t);
extern int  choco_getInf_PalmIntVar_palm(PalmIntVar *v);
extern int  choco_getSup_PalmIntVar_palm(PalmIntVar *v);
extern void  palm_self_explain_PalmIntVar1(PalmIntVar *v,int s,Explanation *e);
extern void  palm_self_explain_PalmIntVar2(PalmIntVar *v,int s,int x,Explanation *e);
extern int  palm_firstValue_PalmIntVar(PalmIntVar *v);
extern void  palm_updateDataStructures_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnVariable_PalmIntVar_palm(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnConstraints_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnConstraint_AbstractConstraint_palm(AbstractConstraint *c,int idx,int way,int v,int nextValue);
extern void  palm_updateDataStructuresOnRestore_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreVariable_PalmIntVar_palm(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_AbstractConstraint_palm(AbstractConstraint *c,int idx,int way,int v,int w);
extern void  palm_restoreInf_PalmIntVar(PalmIntVar *v,int newValue);
extern void  palm_restoreSup_PalmIntVar(PalmIntVar *v,int newValue);
extern void  palm_postRestoreInf_PalmEngine(PalmEngine *pe,PalmIntVar *v);
extern void  palm_postRestoreSup_PalmEngine(PalmEngine *pe,PalmIntVar *v);
extern void  palm_postRestoreEvent_PalmEngine1(PalmEngine *pe,BoundUpdate *e);
extern void  palm_restoreVariableExplanation_DecInf(DecInf *e);
extern void  palm_restoreVariableExplanation_IncSup(IncSup *e);
extern void  palm_restoreVal_PalmIntVar(PalmIntVar *v,int val);
extern OID  palm_postRestoreVal_PalmEngine(PalmEngine *pe,PalmIntVar *v,int value);
extern OID  palm_postRestoreEvent_PalmEngine2(PalmEngine *pe,ValueRestorations *e,int value);
extern ClaireBoolean * choco_updateInf_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e);
extern ClaireBoolean * choco_updateInf_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e);
extern ClaireBoolean * choco_updateSup_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e);
extern ClaireBoolean * choco_updateSup_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e);
extern ClaireBoolean * choco_removeVal_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e);
extern ClaireBoolean * choco_removeVal_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e);
extern void  palm_postRemoveVal_PalmEngine(PalmEngine *pe,IntVar *v,int x,int i);
extern ClaireBoolean * choco_instantiate_PalmIntVar(PalmIntVar *v,int x,int idx,Explanation *e);
extern OID  claire_self_print_PalmControlConstraint_palm(PalmControlConstraint *pcc);
extern void  palm_addDependency_AbstractConstraint(AbstractConstraint *c,Explanation *p);
extern void  palm_removeDependency_AbstractConstraint(AbstractConstraint *c,Explanation *p);
extern void  palm_undo_AbstractConstraint(AbstractConstraint *c);
extern ClaireBoolean * palm_indirect_ask_AbstractConstraint(AbstractConstraint *c);
extern void  palm_setIndirect_AbstractConstraint(AbstractConstraint *c,Explanation *e);
extern void  palm_setDirect_AbstractConstraint(AbstractConstraint *c);
extern void  palm_setIndirectDependance_AbstractConstraint(AbstractConstraint *c,Explanation *e);
extern void  palm_removeIndirectDependance_AbstractConstraint(AbstractConstraint *c);
extern void  palm_informControllersOfDeactivation_AbstractConstraint(AbstractConstraint *c);
extern int  palm_weight_AbstractConstraint(AbstractConstraint *c);
extern void  palm_self_explain_AbstractConstraint(AbstractConstraint *c,Explanation *e);
extern void  palm_activate_AbstractConstraint(AbstractConstraint *c);
extern void  palm_deactivate_AbstractConstraint(AbstractConstraint *c);
extern void  palm_initHook_AbstractConstraint(AbstractConstraint *c);
extern void  palm_addControl_AbstractConstraint(AbstractConstraint *ct,AbstractConstraint *pere,int idx);
extern void  choco_connectIntVarEvents_PalmIntVar_palm(PalmIntVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem);
extern void  choco_disconnectIntVarEvents_PalmIntVar_palm(PalmIntVar *v,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnInf,ClaireBoolean *passiveOnSup,ClaireBoolean *passiveOnRem);
extern void  choco_reconnectIntVarEvents_PalmIntVar_palm(PalmIntVar *v,int idx,ClaireBoolean *onInst,ClaireBoolean *onInf,ClaireBoolean *onSup,ClaireBoolean *onRem);
extern void  choco_connectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c);
extern void  choco_disconnectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c);
extern void  choco_reconnectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c);
extern void  palm_awakeOnRestoreInf_AbstractConstraint(AbstractConstraint *c,int i);
extern void  palm_awakeOnRestoreSup_AbstractConstraint(AbstractConstraint *c,int i);
extern void  palm_awakeOnRestoreVal_AbstractConstraint(AbstractConstraint *c,int idx,int v);
extern set * palm_whyIsTrue_AbstractConstraint(AbstractConstraint *c);
extern set * palm_whyIsFalse_AbstractConstraint(AbstractConstraint *c);
extern PalmUnIntConstraint * palm_makePalmUnIntConstraint_class(ClaireClass *c,PalmIntVar *u,int cc);
extern void  palm_awakeOnRestoreInf_PalmUnIntConstraint(PalmUnIntConstraint *c,int i);
extern void  palm_awakeOnRestoreSup_PalmUnIntConstraint(PalmUnIntConstraint *c,int i);
extern PalmBinIntConstraint * palm_makePalmBinIntConstraint_class(ClaireClass *c,PalmIntVar *u,PalmIntVar *v,int cc);
extern void  palm_awakeOnRestoreInf_PalmBinIntConstraint(PalmBinIntConstraint *c,int i);
extern void  palm_awakeOnRestoreSup_PalmBinIntConstraint(PalmBinIntConstraint *c,int i);
extern PalmLargeIntConstraint * palm_makePalmLargeIntConstraint_class(ClaireClass *c,list *l,int cc);
extern OID  claire_self_print_PalmGreaterOrEqualxc_palm(PalmGreaterOrEqualxc *c);
extern void  choco_propagate_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern void  choco_awakeOnInf_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int i);
extern void  choco_awakeOnSup_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int i);
extern void  palm_awakeOnRestoreVal_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx,int v);
extern void  choco_awakeOnInst_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx);
extern void  choco_awakeOnRem_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx,int x);
extern OID  choco_askIfEntailed_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern set * palm_whyIsFalse_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern set * palm_whyIsTrue_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern ClaireBoolean * palm_checkPalm_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *ct);
extern OID  claire_self_print_PalmLessOrEqualxc_palm(PalmLessOrEqualxc *c);
extern void  choco_propagate_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern void  choco_awakeOnInf_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int i);
extern void  choco_awakeOnSup_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int i);
extern void  palm_awakeOnRestoreVal_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx,int v);
extern set * palm_whyIsFalse_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern set * palm_whyIsTrue_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern void  choco_awakeOnInst_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx);
extern void  choco_awakeOnRem_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx,int x);
extern OID  choco_askIfEntailed_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern ClaireBoolean * palm_checkPalm_PalmLessOrEqualxc(PalmLessOrEqualxc *ct);
extern OID  claire_self_print_PalmNotEqualxc_palm(PalmNotEqualxc *c);
extern void  choco_propagate_PalmNotEqualxc(PalmNotEqualxc *c);
extern void  choco_awakeOnInf_PalmNotEqualxc(PalmNotEqualxc *c,int idx);
extern void  choco_awakeOnSup_PalmNotEqualxc(PalmNotEqualxc *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmNotEqualxc(PalmNotEqualxc *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmNotEqualxc(PalmNotEqualxc *c,int idx);
extern void  palm_awakeOnRestoreVal_PalmNotEqualxc(PalmNotEqualxc *c,int idx,int v);
extern void  choco_awakeOnInst_PalmNotEqualxc(PalmNotEqualxc *c,int idx);
extern void  choco_awakeOnRem_PalmNotEqualxc(PalmNotEqualxc *c,int idx,int x);
extern OID  choco_askIfEntailed_PalmNotEqualxc(PalmNotEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmNotEqualxc(PalmNotEqualxc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmNotEqualxc(PalmNotEqualxc *c);
extern set * palm_whyIsTrue_PalmNotEqualxc(PalmNotEqualxc *c);
extern set * palm_whyIsFalse_PalmNotEqualxc(PalmNotEqualxc *c);
extern ClaireBoolean * palm_checkPalm_PalmNotEqualxc(PalmNotEqualxc *ct);
extern OID  claire_self_print_PalmEqualxc_palm(PalmEqualxc *c);
extern void  choco_propagate_PalmEqualxc(PalmEqualxc *c);
extern void  choco_propagate_AssignmentConstraint(AssignmentConstraint *c);
extern void  choco_awakeOnInf_PalmEqualxc(PalmEqualxc *c,int i);
extern void  choco_awakeOnSup_PalmEqualxc(PalmEqualxc *c,int i);
extern void  choco_awakeOnInf_AssignmentConstraint(AssignmentConstraint *c,int i);
extern void  choco_awakeOnSup_AssignmentConstraint(AssignmentConstraint *c,int i);
extern void  choco_awakeOnRem_PalmEqualxc(PalmEqualxc *c,int idx,int x);
extern void  choco_awakeOnRem_AssignmentConstraint(AssignmentConstraint *c,int idx,int x);
extern void  palm_awakeOnRestoreVal_PalmEqualxc(PalmEqualxc *c,int idx,int v);
extern void  choco_awakeOnInst_PalmEqualxc(PalmEqualxc *c,int idx);
extern OID  choco_askIfEntailed_PalmEqualxc(PalmEqualxc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmEqualxc(PalmEqualxc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmEqualxc(PalmEqualxc *c);
extern set * palm_whyIsTrue_PalmEqualxc(PalmEqualxc *c);
extern set * palm_whyIsFalse_PalmEqualxc(PalmEqualxc *c);
extern ClaireBoolean * palm_checkPalm_PalmEqualxc(PalmEqualxc *ct);
extern ClaireBoolean * palm_checkPalm_AssignmentConstraint(AssignmentConstraint *ct);
extern OID  claire_self_print_PalmNotEqualxyc_palm(PalmNotEqualxyc *c);
extern void  choco_propagate_PalmNotEqualxyc(PalmNotEqualxyc *c);
extern void  choco_awakeOnInf_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx);
extern void  choco_awakeOnSup_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx);
extern void  choco_awakeOnRem_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx,int v);
extern void  choco_awakeOnInst_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_PalmNotEqualxyc(PalmNotEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmNotEqualxyc(PalmNotEqualxyc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmNotEqualxyc(PalmNotEqualxyc *c);
extern ClaireBoolean * palm_checkPalm_PalmNotEqualxyc(PalmNotEqualxyc *ct);
extern OID  claire_self_print_PalmEqualxyc_palm(PalmEqualxyc *c);
extern void  choco_propagate_PalmEqualxyc(PalmEqualxyc *c);
extern void  choco_awakeOnInf_PalmEqualxyc(PalmEqualxyc *c,int idx);
extern void  choco_awakeOnSup_PalmEqualxyc(PalmEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmEqualxyc(PalmEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmEqualxyc(PalmEqualxyc *c,int idx);
extern void  choco_awakeOnRem_PalmEqualxyc(PalmEqualxyc *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PalmEqualxyc(PalmEqualxyc *c,int idx,int v);
extern void  choco_awakeOnInst_PalmEqualxyc(PalmEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_PalmEqualxyc(PalmEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmEqualxyc(PalmEqualxyc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmEqualxyc(PalmEqualxyc *c);
extern ClaireBoolean * palm_checkPalm_PalmEqualxyc(PalmEqualxyc *ct);
extern OID  claire_self_print_PalmGreaterOrEqualxyc_palm(PalmGreaterOrEqualxyc *c);
extern ClaireBoolean * palm_hasSupport_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int x);
extern void  choco_propagate_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern void  choco_awakeOnInf_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx);
extern void  choco_awakeOnSup_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx);
extern void  choco_awakeOnRem_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int v);
extern set * palm_whyIsFalse_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern set * palm_whyIsTrue_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern void  choco_awakeOnInst_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern ClaireBoolean * palm_checkPalm_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *ct);
extern OID  claire_self_print_PalmLessOrEqualxyc_palm(PalmLessOrEqualxyc *c);
extern ClaireBoolean * palm_hasSupport_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int x);
extern void  choco_propagate_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern void  choco_awakeOnInf_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx);
extern void  choco_awakeOnSup_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx);
extern void  choco_awakeOnRem_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int v);
extern set * palm_whyIsFalse_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern set * palm_whyIsTrue_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern void  choco_awakeOnInst_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx);
extern OID  choco_askIfEntailed_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c);
extern ClaireBoolean * palm_checkPalm_PalmLessOrEqualxyc(PalmLessOrEqualxyc *ct);
extern OID  claire_self_print_PalmLinComb_palm(PalmLinComb *self);
extern int  choco_assignIndices_PalmLinComb(PalmLinComb *c,AbstractConstraint *root,int i);
extern PalmLinComb * palm_makePalmLinComb_list(list *l1,list *l2,int c,int opn);
extern void  palm_updateDataStructuresOnConstraint_PalmLinComb_palm(PalmLinComb *c,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmLinComb_palm(PalmLinComb *c,int idx,int way,int newValue,int oldValue);
extern Explanation * palm_explainVariablesLB_PalmLinComb(PalmLinComb *c);
extern Explanation * palm_explainVariablesUB_PalmLinComb(PalmLinComb *c);
extern int  choco_computeUpperBound_PalmLinComb_palm(PalmLinComb *c);
extern int  choco_computeLowerBound_PalmLinComb_palm(PalmLinComb *c);
extern ClaireBoolean * choco_propagateNewLowerBound_PalmLinComb_palm(PalmLinComb *c,int mylb);
extern ClaireBoolean * choco_propagateNewUpperBound_PalmLinComb_palm(PalmLinComb *c,int myub);
extern void  choco_propagate_PalmLinComb(PalmLinComb *c);
extern void  choco_awake_PalmLinComb_palm(PalmLinComb *c);
extern void  choco_awakeOnInf_PalmLinComb(PalmLinComb *c,int idx);
extern void  choco_awakeOnSup_PalmLinComb(PalmLinComb *c,int idx);
extern void  choco_awakeOnRem_PalmLinComb(PalmLinComb *c,int idx,int v);
extern void  palm_awakeOnRestoreInf_PalmLinComb(PalmLinComb *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmLinComb(PalmLinComb *c,int idx);
extern void  palm_awakeOnRestoreVal_PalmLinComb(PalmLinComb *c,int idx,int v);
extern ClaireBoolean * palm_abstractDecInf_PalmLinComb(PalmLinComb *c,int i);
extern ClaireBoolean * palm_abstractIncSup_PalmLinComb(PalmLinComb *c,int i);
extern ClaireBoolean * palm_abstractRestoreVal_PalmLinComb(PalmLinComb *c,int i,int v);
extern OID  choco_askIfEntailed_PalmLinComb(PalmLinComb *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmLinComb(PalmLinComb *c);
extern void  choco_awakeOnInst_PalmLinComb(PalmLinComb *c,int idx);
extern ClaireBoolean * palm_checkPalm_PalmLinComb(PalmLinComb *ct);
extern void  palm_awakeOnRestoreInf_Delayer(Delayer *d,int idx);
extern void  palm_awakeOnRestoreSup_Delayer(Delayer *d,int idx);
extern void  palm_awakeOnRestoreVal_Delayer(Delayer *d,int idx,int x);
extern ClaireBoolean * palm_abstractDecInf_AbstractConstraint(AbstractConstraint *c,int i);
extern ClaireBoolean * palm_abstractIncSup_AbstractConstraint(AbstractConstraint *c,int i);
extern ClaireBoolean * palm_abstractRestoreVal_AbstractConstraint(AbstractConstraint *c,int i,int x);
extern void  palm_updateDataStructuresOnConstraint_Delayer_palm(Delayer *d,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_Delayer_palm(Delayer *d,int idx,int way,int newValue,int oldValue);
extern OID  claire_self_print_PalmCompleteAllDiff_palm(PalmCompleteAllDiff *c);
extern PalmCompleteAllDiff * claire_e_dashallDifferent_list(list *l1);
extern PalmCompleteAllDiff * claire_e_dashcompleteAllDiff_list(list *l1);
extern void  choco_deleteEdgeAndPublish_PalmCompleteAllDiff(PalmCompleteAllDiff *c,int i,int j);
extern void  palm_updateDataStructuresOnConstraint_PalmCompleteAllDiff_palm(PalmCompleteAllDiff *c,int idx,int way,int val,int unused);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmCompleteAllDiff_palm(PalmCompleteAllDiff *c,int idx,int way,int val,int unused);
extern void  choco_awakeOnRem_PalmCompleteAllDiff(PalmCompleteAllDiff *c,int idx,int val);
extern void  palm_awakeOnRestoreVal_PalmCompleteAllDiff(PalmCompleteAllDiff *c,int idx,int val);
extern void  choco_awake_PalmCompleteAllDiff_palm(PalmCompleteAllDiff *c);
extern ClaireBoolean * palm_checkPalm_PalmCompleteAllDiff(PalmCompleteAllDiff *ct);
extern OID  claire_self_print_PalmPermutation_palm(PalmPermutation *c);
extern PalmPermutation * claire_e_dashpermutation_list(list *l1,list *l2);
extern void  choco_deleteEdgeAndPublish_PalmPermutation(PalmPermutation *c,int i,int j);
extern OID  palm_publishDeletion_PalmPermutation(PalmPermutation *c,int i,int j,ClaireBoolean *fromLeft);
extern void  palm_updateDataStructuresOnConstraint_PalmPermutation_palm(PalmPermutation *c,int idx,int way,int val,int unused);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmPermutation_palm(PalmPermutation *c,int idx,int way,int val,int unused);
extern void  choco_awakeOnRem_PalmPermutation(PalmPermutation *c,int idx,int val);
extern void  palm_awakeOnRestoreVal_PalmPermutation(PalmPermutation *c,int idx,int val);
extern void  choco_awake_PalmPermutation_palm(PalmPermutation *c);
extern ClaireBoolean * palm_checkPalm_PalmPermutation(PalmPermutation *ct);
extern OID  claire_self_print_PalmGlobalCardinality_palm(PalmGlobalCardinality *c);
extern PalmGlobalCardinality * palm_e_dashgcc_list1(list *l1,list *l2);
extern PalmGlobalCardinality * palm_e_dashgcc_list2(list *l1,int val1,int val2,list *l2);
extern void  choco_deleteEdgeAndPublish_PalmGlobalCardinality(PalmGlobalCardinality *c,int i,int j);
extern void  palm_updateDataStructuresOnConstraint_PalmGlobalCardinality_palm(PalmGlobalCardinality *c,int idx,int way,int val,int unused);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinality_palm(PalmGlobalCardinality *c,int idx,int way,int val,int unused);
extern void  choco_awakeOnRem_PalmGlobalCardinality(PalmGlobalCardinality *c,int idx,int val);
extern void  palm_awakeOnRestoreVal_PalmGlobalCardinality(PalmGlobalCardinality *c,int idx,int val);
extern void  choco_awake_PalmGlobalCardinality_palm(PalmGlobalCardinality *c);
extern ClaireBoolean * palm_checkPalm_PalmGlobalCardinality(PalmGlobalCardinality *ct);
extern OID  claire_self_print_PalmGlobalCardinalityVar_palm(PalmGlobalCardinalityVar *c);
extern PalmGlobalCardinalityVar * palm_e_dashgcc_list3(list *l1,list *l2);
extern PalmGlobalCardinalityVar * palm_e_dashgcc_list4(list *l1,int val1,int val2,list *l2);
extern ClaireBoolean * palm_mustDiminishFlowFromSource_PalmGlobalCardinalityVar(PalmGlobalCardinalityVar *c,int j);
extern void  palm_updateDataStructuresOnConstraint_PalmGlobalCardinalityVar_palm(PalmGlobalCardinalityVar *c,int idx,int way,int val,int unused);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinalityVar_palm(PalmGlobalCardinalityVar *c,int idx,int way,int val,int unused);
extern void  choco_awakeOnRem_PalmGlobalCardinalityVar(PalmGlobalCardinalityVar *c,int idx,int val);
extern void  palm_awakeOnRestoreVal_PalmGlobalCardinalityVar(PalmGlobalCardinalityVar *c,int idx,int val);
extern void  choco_awake_PalmGlobalCardinalityVar_palm(PalmGlobalCardinalityVar *c);
extern void  choco_propagate_PalmGlobalCardinalityVar(PalmGlobalCardinalityVar *c);
extern ClaireBoolean * palm_checkPalm_PalmGlobalCardinalityVar(PalmGlobalCardinalityVar *ct);
extern void  ice_augmentFlow_PalmCompleteAllDiff_palm(PalmCompleteAllDiff *c);
extern void  ice_augmentFlow_PalmPermutation_palm(PalmPermutation *c);
extern void  ice_augmentFlow_PalmGlobalCardinality_palm(PalmGlobalCardinality *c);
extern void  palm_removeUselessEdges_PalmCompleteAllDiff(PalmCompleteAllDiff *c);
extern void  palm_removeUselessEdges_PalmPermutation(PalmPermutation *c);
extern void  palm_removeUselessEdges_PalmGlobalCardinality(PalmGlobalCardinality *c);
extern void  palm_checkNbPossible_PalmOccurrence(PalmOccurrence *c);
extern void  palm_checkNbSure_PalmOccurrence(PalmOccurrence *c);
extern void  palm_updateDataStructuresOnConstraint_PalmOccurrence_palm(PalmOccurrence *c,int idx,int way,int newValue,int oldValue);
extern OID  palm_updateDataStructuresOnRestoreConstraint_PalmOccurrence_palm(PalmOccurrence *c,int idx,int way,int newValue,int oldValue);
extern void  choco_awakeOnInf_PalmOccurrence(PalmOccurrence *c,int idx);
extern void  choco_awakeOnSup_PalmOccurrence(PalmOccurrence *c,int idx);
extern void  choco_awakeOnRem_PalmOccurrence(PalmOccurrence *c,int idx,int x);
extern void  palm_awakeOnRestoreInf_PalmOccurrence(PalmOccurrence *c,int i);
extern void  palm_awakeOnRestoreSup_PalmOccurrence(PalmOccurrence *c,int i);
extern void  palm_awakeOnRestoreVal_PalmOccurrence(PalmOccurrence *c,int i,int v);
extern void  choco_awake_PalmOccurrence_palm(PalmOccurrence *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmOccurrence(PalmOccurrence *c);
extern OID  choco_askIfEntailed_PalmOccurrence(PalmOccurrence *c);
extern ClaireBoolean * palm_checkPalm_PalmOccurrence(PalmOccurrence *ct);
extern PalmElt * palm_makePalmEltConstraint_list(list *l,PalmIntVar *i,PalmIntVar *x,int o);
extern void  palm_updateValueFromIndex_PalmElt(PalmElt *c);
extern void  palm_updateIndexFromValue_PalmElt(PalmElt *c);
extern void  choco_awake_PalmElt_palm(PalmElt *c);
extern void  choco_propagate_PalmElt(PalmElt *c);
extern void  choco_awakeOnInf_PalmElt(PalmElt *c,int idx);
extern void  choco_awakeOnSup_PalmElt(PalmElt *c,int idx);
extern void  choco_awakeOnInst_PalmElt(PalmElt *c,int idx);
extern void  choco_awakeOnRem_PalmElt(PalmElt *c,int idx,int x);
extern void  palm_awakeOnRestore_PalmElt(PalmElt *c,int idx);
extern void  palm_awakeOnRestoreInf_PalmElt(PalmElt *c,int idx);
extern void  palm_awakeOnRestoreSup_PalmElt(PalmElt *c,int idx);
extern void  palm_awakeOnRestoreVal_PalmElt(PalmElt *c,int idx,int v);
extern OID  choco_askIfEntailed_PalmElt(PalmElt *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmElt(PalmElt *c);
extern set * palm_whyTrueOrFalse_PalmElt(PalmElt *c);
extern set * palm_whyIsFalse_PalmElt(PalmElt *c);
extern set * palm_whyIsTrue_PalmElt(PalmElt *c);
extern ClaireBoolean * palm_checkPalm_PalmElt(PalmElt *ct);
extern OID  claire_self_print_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c);
extern PalmAC4BinConstraint * palm_makeAC4constraint_PalmIntVar(PalmIntVar *u,PalmIntVar *v,ClaireBoolean *feasible,list *mytuples);
extern ClaireBoolean * choco_testIfSatisfied_PalmAC4BinConstraint(PalmAC4BinConstraint *c);
extern void  choco_awake_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c);
extern void  choco_propagate_PalmAC4BinConstraint(PalmAC4BinConstraint *c);
extern void  choco_awakeOnRem_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx,int v);
extern void  palm_updateDataStructuresOnConstraint_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c,int idx,int way,int v,int unused);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c,int idx,int way,int v,int unused);
extern void  choco_awakeOnInf_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int i);
extern void  choco_awakeOnSup_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int i);
extern void  choco_awakeOnInst_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx);
extern OID  choco_askIfEntailed_PalmAC4BinConstraint(PalmAC4BinConstraint *c);
extern ClaireBoolean * choco_testIfCompletelyInstantiated_PalmAC4BinConstraint(PalmAC4BinConstraint *c);
extern ClaireBoolean * palm_checkPalm_PalmAC4BinConstraint(PalmAC4BinConstraint *ct);
extern OID  claire_self_print_Explanation_palm(Explanation *p);
extern void  palm_addConstraint_Explanation(Explanation *p,AbstractConstraint *c);
extern void  palm_deleteConstraint_Explanation(Explanation *p,AbstractConstraint *c);
extern void  palm_mergeConstraints_Explanation(Explanation *p,set *lc);
extern void  palm_merge_Explanation(Explanation *p,Explanation *q);
extern ClaireBoolean * palm_empty_ask_Explanation(Explanation *p);
extern void  palm_empties_Explanation(Explanation *p);
extern Explanation * claire_delete_Explanation(Explanation *p,AbstractConstraint *c);
extern set * claire_set_I_Explanation(Explanation *p);
extern ClaireBoolean * claire__Z_AbstractConstraint(AbstractConstraint *c,Explanation *p);
extern Explanation * palm_clone_Explanation(Explanation *p);
extern ClaireBoolean * palm_valid_ask_Explanation(Explanation *e);
extern ClaireBoolean * palm_contains_ask_Explanation(Explanation *e,Explanation *f);
extern void  palm_clean_Explanation(Explanation *e);
extern void  claire_self_print_DecInf_palm(DecInf *e);
extern void  claire_self_print_IncSup_palm(IncSup *e);
extern void  claire_self_print_ValueRestorations_palm(ValueRestorations *e);
extern ValueRestorations * palm_makeValueRestorations_PalmIntVar(PalmIntVar *var,int mv);
extern OID  palm_addDependencies_Explanation(Explanation *p);
extern OID  palm_addDependencies_set(set *p,Explanation *e);
extern OID  palm_removeDependencies_Explanation(Explanation *p,AbstractConstraint *removed);
extern PalmIncInfExplanation * palm_makeIncInfExplanation_Explanation(Explanation *e,int oldval,PalmIntVar *v);
extern OID  claire_self_print_PalmIncInfExplanation_palm(PalmIncInfExplanation *e);
extern void  palm_postUndoRemoval_PalmIncInfExplanation_palm(PalmIncInfExplanation *exp,AbstractConstraint *removed);
extern PalmDecSupExplanation * palm_makeDecSupExplanation_Explanation(Explanation *e,int oldval,PalmIntVar *v);
extern OID  claire_self_print_PalmDecSupExplanation_palm(PalmDecSupExplanation *e);
extern void  palm_postUndoRemoval_PalmDecSupExplanation_palm(PalmDecSupExplanation *exp,AbstractConstraint *removed);
extern PalmRemovalExplanation * palm_makeRemovalExplanation_Explanation(Explanation *e,int n,PalmIntVar *v);
extern OID  claire_self_print_PalmRemovalExplanation_palm(PalmRemovalExplanation *e);
extern OID  palm_postUndoRemoval_PalmRemovalExplanation_palm(PalmRemovalExplanation *exp,AbstractConstraint *removed);
extern PalmEngine * palm_makePalmEngine_integer(int n);
extern OID  choco_getNextActiveEventQueue_PalmEngine_palm(PalmEngine *pe);
extern void  palm_resetEvent_BoundUpdate(BoundUpdate *evt);
extern void  palm_resetEvent_ValueRemovals(ValueRemovals *evt);
extern void  palm_resetPoppingQueue_BoundEventQueue(BoundEventQueue *q);
extern void  palm_resetPoppingQueue_RemovalEventQueue(RemovalEventQueue *q);
extern void  palm_resetEventQueue_EventQueue(EventQueue *q);
extern void  palm_resetEvents_PalmEngine(PalmEngine *pb);
extern void  palm_flushEvents_PalmEngine(PalmEngine *pb);
extern void  palm_raisePalmContradiction_PalmEngine(PalmEngine *pe,PalmIntVar *v);
extern void  palm_raisePalmFakeContradiction_PalmEngine(PalmEngine *pe,Explanation *e);
extern void  palm_raiseSystemContradiction_PalmEngine(PalmEngine *pe);
extern void  palm_remove_PalmEngine1(PalmEngine *pe,AbstractConstraint *c);
extern void  palm_remove_PalmEngine2(PalmEngine *pe,list *cl);
extern OID  palm_restoreVariableExplanations_PalmEngine(PalmEngine *pe);
extern OID  palm_checkVariableDomainAgainstRemValEvt_PalmIntVar(PalmIntVar *v);
extern void  choco_propagateEvent_DecInf_palm(DecInf *e,BoundEventQueue *q);
extern void  choco_propagateEvent_IncSup_palm(IncSup *e,BoundEventQueue *q);
extern void  choco_propagateEvent_ValueRestorations_palm(ValueRestorations *e,RemovalEventQueue *q);
extern Explanation * palm_becauseOf_listargs(listargs *causes);
extern tuple * palm_theInf_PalmIntVar(PalmIntVar *v);
extern tuple * palm_theInf_PalmIntVar_(PalmIntVar *g0293);
extern tuple * palm_theSup_PalmIntVar(PalmIntVar *v);
extern tuple * palm_theSup_PalmIntVar_(PalmIntVar *g0294);
extern tuple * palm_theDom_PalmIntVar(PalmIntVar *v);
extern tuple * palm_theDom_PalmIntVar_(PalmIntVar *g0295);
extern tuple * palm_theHole_PalmIntVar(PalmIntVar *v,int x);
extern tuple * palm_theHole_PalmIntVar_(PalmIntVar *g0296,int g0297);
extern tuple * palm_theVars_AbstractConstraint(AbstractConstraint *c);
extern tuple * palm_theVars_AbstractConstraint_(AbstractConstraint *g0298);
extern Explanation * palm_explain_PalmIntVar1(PalmIntVar *v,int s);
extern Explanation * palm_explain_PalmIntVar2(PalmIntVar *v,int s,int val);
extern Explanation * palm_explain_AbstractConstraint(AbstractConstraint *c);
extern Explanation * palm_explain_PalmProblem(PalmProblem *pb);
extern int  palm_getGlobalStatistics_PalmSolver(PalmSolver *pb,int stat);
extern int  palm_getGlobalStatistics_PalmProblem(PalmProblem *pb,int stat);
extern int  palm_getRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat);
extern int  palm_getRuntimeStatistic_PalmProblem(PalmProblem *pb,int stat);
extern int  palm_setRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int val);
extern void  palm_incRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int inc);
extern void  palm_decRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int dec);
extern OID  palm_printRuntimeStatistics_PalmProblem1(PalmProblem *pb);
extern OID  palm_printRuntimeStatistics_PalmProblem2(PalmProblem *pb,int stat);
extern void  palm_reset_PalmSolver(PalmSolver *pb);
extern void  palm_initPalmSolver_PalmProblem(PalmProblem *pb);
extern void  palm_initPalmBranchAndBound_PalmProblem(PalmProblem *pb,ClaireBoolean *isMax,PalmIntVar *obj);
extern void  palm_attachPalmExtend_PalmProblem(PalmProblem *pb,PalmExtend *ext);
extern void  palm_attachPalmLearn_PalmProblem(PalmProblem *pb,PalmLearn *ext);
extern void  palm_attachPalmRepair_PalmProblem(PalmProblem *pb,PalmRepair *ext);
extern void  palm_attachPalmState_PalmProblem(PalmProblem *pb,PalmState *ext);
extern void  palm_attachPalmBranchings_PalmProblem(PalmProblem *pb,list *lbr);
extern ClaireBoolean * palm_searchOneSolution_PalmProblem(PalmProblem *pb);
extern OID  palm_run_PalmSolver(PalmSolver *algo);
extern void  palm_runonce_PalmBranchAndBound(PalmBranchAndBound *algo);
extern void  palm_run_PalmBranchAndBound(PalmBranchAndBound *algo);
extern void  palm_postDynamicCut_PalmBranchAndBound(PalmBranchAndBound *algo);
extern void  palm_extend_PalmSolver_palm(PalmSolver *algo);
extern void  palm_explore_PalmExtend(PalmExtend *ext,PalmBranching *b);
extern void  palm_explore_PalmUnsureExtend(PalmUnsureExtend *ext,PalmBranching *b);
extern list * palm_selectAuthorizedDecisions_PalmBranching(PalmBranching *b,OID v);
extern void  palm_propagateAllDecisionConstraints_PalmProblem(PalmProblem *pb,list *cts);
extern OID  palm_selectBranchingItem_PalmBranching_palm(PalmBranching *b);
extern list * palm_selectDecisions_PalmBranching_palm(PalmBranching *b,OID v);
extern list * palm_getNextDecisions_PalmBranching_palm(PalmBranching *b);
extern ClaireBoolean * palm_checkAcceptable_PalmBranching_palm(PalmBranching *b,list *cts);
extern void  palm_learnFromRejection_PalmBranching_palm(PalmBranching *b);
extern void  palm_repair_PalmProblem_palm(PalmProblem *pb);
extern void  palm_repair_PalmSolver_palm(PalmSolver *algo);
extern void  palm_addDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct);
extern void  palm_reverseDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct);
extern void  palm_removeDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct);
extern void  palm_learnFromContradiction_PalmLearn_palm(PalmLearn *learner,Explanation *e);
extern void  palm_learnFromRemoval_PalmLearn_palm(PalmLearn *learner,AbstractConstraint *ct);
extern ClaireBoolean * palm_checkAcceptable_PalmLearn_palm(PalmLearn *memory,list *cts);
extern list * palm_sortConstraintsToUndo_PalmLearn_palm(PalmLearn *learner,Explanation *e);
extern ClaireBoolean * palm_checkAcceptableRelaxation_PalmLearn_palm(PalmLearn *learner,AbstractConstraint *ct_out);
extern OID  palm_selectDecisionToUndo_PalmRepair_palm(PalmRepair *repairer,Explanation *e);
extern OID  palm_selectDecisionToUndo_PalmUnsureRepair_palm(PalmUnsureRepair *repairer,Explanation *e);
extern ClaireBoolean * palm_standard_better_constraint_AbstractConstraint(AbstractConstraint *x,AbstractConstraint *y);
extern ClaireBoolean * palm_standard_better_ordered_constraint_AbstractConstraint(AbstractConstraint *ct1,AbstractConstraint *ct2);
extern int  palm_getObjectiveValue_PalmProblem(PalmProblem *pb,PalmIntVar *obj,ClaireBoolean *isMax);
extern OID  palm_selectBranchingItem_PalmAssignVar_palm(PalmAssignVar *b);
extern list * palm_selectDecisions_PalmAssignVar_palm(PalmAssignVar *b,PalmIntVar *v);
extern OID  palm_selectBranchingItem_PalmAssignMinDomDegVar_palm(PalmAssignMinDomDegVar *b);
extern OID  palm_getMinDomVar_Problem(Problem *pb);
extern OID  palm_getMinDomVar_list(list *l);
extern OID  palm_getMinDomDegVar_Problem(Problem *pb);
extern OID  palm_getMinDomDegVar_list(list *l);
extern OID  claire_self_print_PalmSolution_palm(PalmSolution *sol);
extern int  palm_getRuntimeStatistic_PalmSolution(PalmSolution *sol,int s);
extern void  palm_storeSolution_PalmSolver(PalmSolver *psolver);
extern void  palm_storeSolution_PalmBranchAndBound(PalmBranchAndBound *psolver);
extern ClaireBoolean * palm_discardCurrentSolution_PalmState(PalmState *ps);
extern PathRepairLearn * palm_makePathRepairLearn_integer(int lSize,int mMoves);
extern PathRepairLearn * palm_makePathRepairLearn_class(ClaireClass *c,int lSize,int mMoves);
extern void  palm_addForbiddenSituation_PathRepairLearn(PathRepairLearn *prl,Explanation *ng);
extern ClaireBoolean * palm_isForbidden_PathRepairLearn(PathRepairLearn *prl,Explanation *ng);
extern void  palm_learnFromContradiction_PathRepairLearn_palm(PathRepairLearn *learner,Explanation *e);
extern void  palm_informConstraintsInExplanation_PathRepairLearn_palm(PathRepairLearn *prl,Explanation *s);
extern void  palm_learnFromRemoval_PathRepairLearn_palm(PathRepairLearn *learner,AbstractConstraint *ct);
extern ClaireBoolean * palm_checkAcceptable_PathRepairLearn_palm(PathRepairLearn *memory,list *cts);
extern ClaireBoolean * palm_checkAcceptableRelaxation_PathRepairLearn_palm(PathRepairLearn *learner,AbstractConstraint *ct_out);
extern void  palm_initHook_PalmDisjunction(PalmDisjunction *c);
extern void  palm_takeIntoAccountStatusChange_PalmDisjunction(PalmDisjunction *d,int idx);
extern OID  claire_self_print_PalmDisjunction_palm(PalmDisjunction *d);
extern ClaireBoolean * palm_needToAwake_PalmDisjunction(PalmDisjunction *d,int i);
extern void  palm_setNeedToAwake_PalmDisjunction(PalmDisjunction *d,int i,ClaireBoolean *val);
extern void  palm_checkStatusAndReport_PalmDisjunction(PalmDisjunction *d,int i);
extern void  palm_updateDataStructuresOnConstraint_PalmDisjunction_palm(PalmDisjunction *d,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmDisjunction_palm(PalmDisjunction *d,int idx,int way,int newValue,int oldValue);
extern ClaireBoolean * palm_checkConstraintState_PalmDisjunction(PalmDisjunction *d);
extern void  choco_awakeOnInf_PalmDisjunction(PalmDisjunction *d,int i);
extern void  palm_awakeOnRestoreInf_PalmDisjunction(PalmDisjunction *d,int i);
extern void  choco_awakeOnSup_PalmDisjunction(PalmDisjunction *d,int i);
extern void  palm_awakeOnRestoreSup_PalmDisjunction(PalmDisjunction *d,int i);
extern void  choco_awakeOnRem_PalmDisjunction(PalmDisjunction *d,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmDisjunction(PalmDisjunction *d,int i,int v);
extern OID  choco_askIfEntailed_PalmDisjunction(PalmDisjunction *d);
extern ClaireBoolean * choco_testIfSatisfied_PalmDisjunction(PalmDisjunction *d);
extern void  choco_propagate_PalmDisjunction(PalmDisjunction *d);
extern void  choco_awake_PalmDisjunction_palm(PalmDisjunction *d);
extern set * palm_whyIsTrue_PalmDisjunction(PalmDisjunction *d);
extern set * palm_whyIsFalse_PalmDisjunction(PalmDisjunction *d);
extern OID  choco_awakeOnInst_PalmDisjunction(PalmDisjunction *c,int idx);
extern ClaireBoolean * palm_checkPalm_PalmDisjunction(PalmDisjunction *ct);
extern void  palm_initHook_PalmGuard(PalmGuard *c);
extern void  palm_takeIntoAccountStatusChange_PalmGuard(PalmGuard *g,int idx);
extern OID  claire_self_print_PalmGuard_palm(PalmGuard *g);
extern ClaireBoolean * palm_needToAwake_PalmGuard(PalmGuard *g,int i);
extern void  palm_setNeedToAwake_PalmGuard(PalmGuard *g,int i,ClaireBoolean *val);
extern void  palm_checkStatusAndReport_PalmGuard(PalmGuard *g,int i);
extern void  palm_updateDataStructuresOnConstraint_PalmGuard_palm(PalmGuard *g,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmGuard_palm(PalmGuard *g,int idx,int way,int newValue,int oldValue);
extern ClaireBoolean * palm_checkConstraintState_PalmGuard(PalmGuard *g);
extern void  choco_awakeOnInf_PalmGuard(PalmGuard *g,int i);
extern void  palm_awakeOnRestoreInf_PalmGuard(PalmGuard *g,int i);
extern void  choco_awakeOnSup_PalmGuard(PalmGuard *g,int i);
extern void  palm_awakeOnRestoreSup_PalmGuard(PalmGuard *g,int i);
extern void  choco_awakeOnRem_PalmGuard(PalmGuard *g,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmGuard(PalmGuard *g,int i,int v);
extern void  choco_propagate_PalmGuard(PalmGuard *g);
extern void  choco_awake_PalmGuard_palm(PalmGuard *g);
extern OID  choco_askIfEntailed_PalmGuard(PalmGuard *g);
extern ClaireBoolean * choco_testIfSatisfied_PalmGuard(PalmGuard *g);
extern OID  choco_awakeOnInst_PalmGuard(PalmGuard *c,int idx);
extern ClaireBoolean * palm_checkPalm_PalmGuard(PalmGuard *ct);
extern void  palm_initHook_PalmEquiv(PalmEquiv *c);
extern void  palm_takeIntoAccountStatusChange_PalmEquiv(PalmEquiv *c,int idx);
extern OID  claire_self_print_PalmEquiv_palm(PalmEquiv *c);
extern ClaireBoolean * palm_needToAwake_PalmEquiv(PalmEquiv *g,int i);
extern void  palm_setNeedToAwake_PalmEquiv(PalmEquiv *g,int i,ClaireBoolean *val);
extern void  palm_checkStatusAndReport_PalmEquiv(PalmEquiv *c,int i);
extern void  palm_updateDataStructuresOnConstraint_PalmEquiv_palm(PalmEquiv *c,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmEquiv_palm(PalmEquiv *c,int idx,int way,int newValue,int oldValue);
extern ClaireBoolean * palm_checkConstraintState_PalmEquiv(PalmEquiv *c);
extern void  choco_awakeOnInf_PalmEquiv(PalmEquiv *c,int i);
extern void  palm_awakeOnRestoreInf_PalmEquiv(PalmEquiv *c,int i);
extern void  choco_awakeOnSup_PalmEquiv(PalmEquiv *c,int i);
extern void  palm_awakeOnRestoreSup_PalmEquiv(PalmEquiv *c,int i);
extern void  choco_awakeOnRem_PalmEquiv(PalmEquiv *c,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmEquiv(PalmEquiv *c,int i,int v);
extern void  choco_propagate_PalmEquiv(PalmEquiv *c);
extern void  choco_awake_PalmEquiv_palm(PalmEquiv *c);
extern OID  choco_askIfEntailed_PalmEquiv(PalmEquiv *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmEquiv(PalmEquiv *c);
extern void  choco_awakeOnInst_PalmEquiv(PalmEquiv *c,int i);
extern ClaireBoolean * palm_checkPalm_PalmEquiv(PalmEquiv *ct);
extern void  palm_initHook_PalmConjunction(PalmConjunction *c);
extern void  palm_takeIntoAccountStatusChange_PalmConjunction(PalmConjunction *c,int idx);
extern OID  claire_self_print_PalmConjunction_palm(PalmConjunction *c);
extern void  palm_updateDataStructuresOnConstraint_PalmConjunction_palm(PalmConjunction *c,int idx,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmConjunction_palm(PalmConjunction *c,int idx,int way,int newValue,int oldValue);
extern void  choco_awakeOnInf_PalmConjunction(PalmConjunction *c,int i);
extern void  palm_awakeOnRestoreInf_PalmConjunction(PalmConjunction *c,int i);
extern void  choco_awakeOnSup_PalmConjunction(PalmConjunction *c,int i);
extern void  palm_awakeOnRestoreSup_PalmConjunction(PalmConjunction *c,int i);
extern void  choco_awakeOnRem_PalmConjunction(PalmConjunction *c,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmConjunction(PalmConjunction *c,int i,int v);
extern void  choco_propagate_PalmConjunction(PalmConjunction *c);
extern void  choco_awake_PalmConjunction_palm(PalmConjunction *c);
extern OID  choco_askIfEntailed_PalmConjunction(PalmConjunction *c);
extern set * palm_whyIsTrue_PalmConjunction(PalmConjunction *c);
extern set * palm_whyIsFalse_PalmConjunction(PalmConjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmConjunction(PalmConjunction *c);
extern void  choco_awakeOnInst_PalmConjunction(PalmConjunction *c,int i);
extern ClaireBoolean * palm_checkPalm_PalmConjunction(PalmConjunction *ct);
extern void  palm_initHook_PalmLargeConjunction(PalmLargeConjunction *c);
extern void  palm_takeIntoAccountStatusChange_PalmLargeConjunction(PalmLargeConjunction *c,int idx);
extern OID  claire_self_print_PalmLargeConjunction_palm(PalmLargeConjunction *c);
extern void  palm_updateDataStructuresOnConstraint_PalmLargeConjunction_palm(PalmLargeConjunction *c,int i,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmLargeConjunction_palm(PalmLargeConjunction *c,int i,int way,int newValue,int oldValue);
extern void  choco_awakeOnInf_PalmLargeConjunction(PalmLargeConjunction *c,int i);
extern void  palm_awakeOnRestoreInf_PalmLargeConjunction(PalmLargeConjunction *c,int i);
extern void  choco_awakeOnSup_PalmLargeConjunction(PalmLargeConjunction *c,int i);
extern void  palm_awakeOnRestoreSup_PalmLargeConjunction(PalmLargeConjunction *c,int i);
extern void  choco_awakeOnRem_PalmLargeConjunction(PalmLargeConjunction *c,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmLargeConjunction(PalmLargeConjunction *c,int i,int v);
extern void  choco_propagate_PalmLargeConjunction(PalmLargeConjunction *c);
extern void  choco_awake_PalmLargeConjunction_palm(PalmLargeConjunction *c);
extern OID  choco_askIfEntailed_PalmLargeConjunction(PalmLargeConjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmLargeConjunction(PalmLargeConjunction *c);
extern set * palm_whyIsTrue_PalmLargeConjunction(PalmLargeConjunction *c);
extern set * palm_whyIsFalse_PalmLargeConjunction(PalmLargeConjunction *c);
extern void  choco_awakeOnInst_PalmLargeConjunction(PalmLargeConjunction *c,int i);
extern ClaireBoolean * palm_checkPalm_PalmLargeConjunction(PalmLargeConjunction *ct);
extern void  palm_initHook_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern void  palm_takeIntoAccountStatusChange_PalmLargeDisjunction(PalmLargeDisjunction *d,int idx);
extern OID  claire_self_print_PalmLargeDisjunction_palm(PalmLargeDisjunction *c);
extern ClaireBoolean * palm_needToAwake_PalmLargeDisjunction2(PalmLargeDisjunction *d,int i);
extern void  palm_setNeedToAwake_PalmLargeDisjunction(PalmLargeDisjunction *d,int i,ClaireBoolean *val);
extern void  palm_checkStatusAndReport_PalmLargeDisjunction(PalmLargeDisjunction *d,int i);
extern void  palm_updateDataStructuresOnConstraint_PalmLargeDisjunction_palm(PalmLargeDisjunction *c,int i,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmLargeDisjunction_palm(PalmLargeDisjunction *d,int i,int way,int newValue,int oldValue);
extern ClaireBoolean * palm_checkConstraintState_PalmLargeDisjunction(PalmLargeDisjunction *d);
extern void  choco_awakeOnInf_PalmLargeDisjunction(PalmLargeDisjunction *c,int i);
extern void  palm_awakeOnRestoreInf_PalmLargeDisjunction(PalmLargeDisjunction *c,int i);
extern void  choco_awakeOnSup_PalmLargeDisjunction(PalmLargeDisjunction *c,int i);
extern void  palm_awakeOnRestoreSup_PalmLargeDisjunction(PalmLargeDisjunction *c,int i);
extern void  choco_awakeOnRem_PalmLargeDisjunction(PalmLargeDisjunction *c,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmLargeDisjunction(PalmLargeDisjunction *c,int i,int v);
extern OID  choco_askIfEntailed_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern ClaireBoolean * choco_testIfSatisfied_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern void  choco_propagate_PalmLargeDisjunction(PalmLargeDisjunction *d);
extern void  choco_awake_PalmLargeDisjunction_palm(PalmLargeDisjunction *d);
extern set * palm_whyIsTrue_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern set * palm_whyIsFalse_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern void  choco_awakeOnInst_PalmLargeDisjunction(PalmLargeDisjunction *c,int i);
extern ClaireBoolean * palm_checkPalm_PalmLargeDisjunction(PalmLargeDisjunction *ct);
extern void  palm_initHook_PalmCardinality(PalmCardinality *c);
extern ClaireBoolean * palm_needToAwake_PalmCardinality2(PalmCardinality *c,int i);
extern void  palm_setNeedToAwake_PalmCardinality(PalmCardinality *c,int i,ClaireBoolean *val);
extern OID  claire_self_print_PalmCardinality_palm(PalmCardinality *c);
extern void  palm_takeIntoAccountStatusChange_PalmCardinality(PalmCardinality *c,int idx);
extern void  palm_checkOnNbTrue_PalmCardinality(PalmCardinality *c);
extern void  palm_checkOnNbFalse_PalmCardinality(PalmCardinality *c);
extern void  palm_checkStatusAndReport_PalmCardinality(PalmCardinality *c,int i);
extern Explanation * palm_explainTrueConstraints_PalmCardinality(PalmCardinality *c);
extern Explanation * palm_explainFalseConstraints_PalmCardinality(PalmCardinality *c);
extern ClaireBoolean * palm_checkConstraintState_PalmCardinality(PalmCardinality *c);
extern void  palm_updateDataStructuresOnConstraint_PalmCardinality_palm(PalmCardinality *c,int i,int way,int newValue,int oldValue);
extern void  palm_updateDataStructuresOnRestoreConstraint_PalmCardinality_palm(PalmCardinality *d,int i,int way,int newValue,int oldValue);
extern void  choco_awakeOnInf_PalmCardinality(PalmCardinality *c,int i);
extern void  palm_awakeOnRestoreInf_PalmCardinality(PalmCardinality *c,int i);
extern void  choco_awakeOnSup_PalmCardinality(PalmCardinality *c,int i);
extern void  palm_awakeOnRestoreSup_PalmCardinality(PalmCardinality *c,int i);
extern void  choco_awakeOnRem_PalmCardinality(PalmCardinality *c,int i,int v);
extern void  palm_awakeOnRestoreVal_PalmCardinality(PalmCardinality *c,int i,int v);
extern ClaireBoolean * palm_checkPalm_PalmCardinality(PalmCardinality *ct);
extern PalmProblem * palm_makePalmProblem_string1(char *s,int n);
extern PalmProblem * palm_makePalmProblem_string2(char *s,int n,int maxRlxLvl);
extern void  palm_setObjective_PalmProblem(PalmProblem *pb,PalmIntVar *v);
extern void  palm_setSolutionVars_PalmProblem(PalmProblem *pb,list *lv);
extern list * choco_solutions_PalmProblem(PalmProblem *pb);
extern void  palm_solve_PalmProblem1(PalmProblem *pb,list *lbr,ClaireBoolean *allSolutions);
extern void  palm_solve_PalmProblem2(PalmProblem *pb,ClaireBoolean *allSolutions);
extern void  palm_solve_PalmProblem3(PalmProblem *pb,list *lbr);
extern void  palm_solve_PalmProblem4(PalmProblem *pb);
extern int  palm_minimize_PalmProblem1(PalmProblem *pb,PalmIntVar *obj,list *lbr);
extern int  palm_minimize_PalmProblem2(PalmProblem *pb,PalmIntVar *obj);
extern int  palm_minimize_PalmProblem3(PalmProblem *pb,PalmIntVar *obj,int lb,int ub,list *lbr);
extern int  palm_minimize_PalmProblem4(PalmProblem *pb,PalmIntVar *obj,int lb,int ub);
extern int  palm_maximize_PalmProblem1(PalmProblem *pb,PalmIntVar *obj,list *lbr);
extern int  palm_maximize_PalmProblem2(PalmProblem *pb,PalmIntVar *obj);
extern int  palm_maximize_PalmProblem3(PalmProblem *pb,PalmIntVar *obj,int lb,int ub,list *lbr);
extern int  palm_maximize_PalmProblem4(PalmProblem *pb,PalmIntVar *obj,int lb,int ub);
extern PalmIntVar * palm_makeBoundIntVar_PalmProblem(PalmProblem *p,char *s,int i,int j);
extern PalmIntVar * palm_makeIntVar_PalmProblem1(PalmProblem *p,char *s,int i,int j);
extern PalmIntVar * palm_makeIntVar_PalmProblem2(PalmProblem *p,char *s,bag *b);
extern PalmIntVar * palm_makeIntVar_PalmProblem3(PalmProblem *p,bag *b);
extern PalmIntVar * palm_makeConstantPalmIntVar_integer(int x);
extern void  choco_post_PalmProblem1(PalmProblem *p,IntConstraint *c);
extern void  choco_post_PalmProblem2(PalmProblem *p,list *cl);
extern void  choco_post_PalmProblem3(PalmProblem *p,AbstractConstraint *c,int w);
extern void  choco_post_PalmProblem4(PalmProblem *p,Delayer *d,int w);
extern void  choco_post_PalmProblem5(PalmProblem *p,list *cl,int w);
extern void  choco_post_PalmProblem6(PalmProblem *p,AbstractConstraint *c,Explanation *e);
extern void  choco_post_PalmProblem7(PalmProblem *p,Delayer *d,Explanation *e);
extern void  choco_post_PalmProblem8(PalmProblem *p,list *cl,Explanation *e);
extern void  palm_setMin_PalmIntVar(PalmIntVar *v,int x);
extern void  palm_setMax_PalmIntVar(PalmIntVar *v,int x);
extern void  palm_setVal_PalmIntVar(PalmIntVar *v,int x);
extern void  palm_remVal_PalmIntVar(PalmIntVar *v,int x);
extern void  palm_remove_PalmProblem1(PalmProblem *pb,AbstractConstraint *ct);
extern void  palm_remove_PalmProblem2(PalmProblem *pb,set *cl);
extern void  palm_remove_PalmProblem3(PalmProblem *pb,list *cl);
extern void  choco_propagate_PalmProblem(PalmProblem *p);
extern table * palm_makeConstraintTable_type(ClaireType *memberType);
extern AssignmentConstraint * palm_assign_PalmIntVar(PalmIntVar *v,int x);
extern OID  palm_negate_AbstractConstraint_palm(AbstractConstraint *ct);
extern OID  palm_negate_AssignmentConstraint_palm(AssignmentConstraint *ct);
extern OID  choco_opposite_AssignmentConstraint(AssignmentConstraint *ct);
extern OID  palm_negate_PalmLessOrEqualxc_palm(PalmLessOrEqualxc *ct);
extern OID  palm_negate_PalmGreaterOrEqualxc_palm(PalmGreaterOrEqualxc *ct);
extern PalmLessOrEqualxc * choco_opposite_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c);
extern PalmGreaterOrEqualxc * choco_opposite_PalmLessOrEqualxc(PalmLessOrEqualxc *c);
extern PalmNotEqualxc * choco_opposite_PalmEqualxc(PalmEqualxc *c);
extern PalmEqualxc * choco_opposite_PalmNotEqualxc(PalmNotEqualxc *c);
extern PalmNotEqualxyc * choco_opposite_PalmEqualxyc(PalmEqualxyc *c);
extern PalmEqualxyc * choco_opposite_PalmNotEqualxyc(PalmNotEqualxyc *c);
extern PalmGreaterOrEqualxyc * choco_opposite_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c);
extern int  choco_computeVarIdxInOpposite_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int i);
extern PalmConjunction * choco_opposite_PalmGuard(PalmGuard *c);
extern PalmDisjunction * choco_opposite_PalmConjunction(PalmConjunction *c);
extern PalmConjunction * choco_opposite_PalmDisjunction(PalmDisjunction *c);
extern PalmLargeConjunction * choco_opposite_PalmLargeDisjunction(PalmLargeDisjunction *c);
extern PalmLargeDisjunction * choco_opposite_PalmLargeConjunction(PalmLargeConjunction *c);
extern PalmCardinality * choco_opposite_PalmEquiv(PalmEquiv *c);
extern PalmLinComb * choco_opposite_PalmLinComb(PalmLinComb *c);
extern PalmGreaterOrEqualxc * claire__sup_equal_PalmIntVar1(PalmIntVar *v,int x);
extern PalmGreaterOrEqualxc * claire__sup_PalmIntVar1(PalmIntVar *v,int x);
extern PalmLessOrEqualxc * claire__inf_equal_PalmIntVar1(PalmIntVar *v,int x);
extern PalmLessOrEqualxc * claire__inf_PalmIntVar1(PalmIntVar *v,int x);
extern PalmEqualxc * claire__equal_equal_PalmIntVar1(PalmIntVar *v,int x);
extern PalmNotEqualxc * claire__I_equal_equal_PalmIntVar1(PalmIntVar *v,int x);
extern PalmLessOrEqualxc * claire__sup_equal_integer6(int x,PalmIntVar *v);
extern PalmLessOrEqualxc * claire__sup_integer6(int x,PalmIntVar *v);
extern PalmGreaterOrEqualxc * claire__inf_equal_integer7(int x,PalmIntVar *v);
extern PalmGreaterOrEqualxc * claire__inf_integer6(int x,PalmIntVar *v);
extern PalmEqualxc * claire__equal_equal_integer8(int x,PalmIntVar *v);
extern PalmNotEqualxc * claire__I_equal_equal_integer5(int x,PalmIntVar *v);
extern PalmEqualxyc * claire__equal_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmNotEqualxyc * claire__I_equal_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmGreaterOrEqualxyc * claire__inf_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmGreaterOrEqualxyc * claire__sup_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmGreaterOrEqualxyc * claire__sup_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmGreaterOrEqualxyc * claire__inf_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern OID  claire_self_print_PalmUnTerm_palm(PalmUnTerm *t);
extern OID  claire_self_print_PalmBinTerm_palm(PalmBinTerm *t);
extern OID  claire_self_print_PalmLinTerm_palm(PalmLinTerm *t);
extern PalmLinTerm * claire_e_dashscalar_list(list *l1,list *l2);
extern PalmLinTerm * palm_e_dashsumVars_list(list *l);
extern PalmLinTerm * claire__star_integer4(int a,PalmIntVar *x);
extern PalmLinTerm * claire__star_PalmIntVar(PalmIntVar *x,int a);
extern PalmUnTerm * claire__plus_PalmIntVar1(PalmIntVar *u,int c);
extern PalmUnTerm * claire__plus_integer6(int c,PalmIntVar *u);
extern PalmBinTerm * claire__plus_PalmIntVar2(PalmIntVar *u,PalmIntVar *v);
extern PalmUnTerm * claire__plus_PalmUnTerm1(PalmUnTerm *t,int c);
extern PalmUnTerm * claire__plus_integer7(int c,PalmUnTerm *t);
extern PalmBinTerm * claire__plus_PalmUnTerm2(PalmUnTerm *t,PalmIntVar *x);
extern PalmBinTerm * claire__plus_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t);
extern PalmBinTerm * claire__plus_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2);
extern PalmBinTerm * claire__plus_PalmBinTerm1(PalmBinTerm *t,int c);
extern PalmBinTerm * claire__plus_integer8(int c,PalmBinTerm *t);
extern PalmLinTerm * claire__plus_PalmBinTerm2(PalmBinTerm *t,PalmIntVar *x);
extern PalmLinTerm * claire__plus_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t);
extern PalmLinTerm * claire__plus_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2);
extern PalmLinTerm * claire__plus_PalmUnTerm4(PalmUnTerm *t2,PalmBinTerm *t1);
extern PalmLinTerm * claire__plus_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__plus_PalmLinTerm1(PalmLinTerm *t,int a);
extern PalmLinTerm * claire__plus_integer9(int a,PalmLinTerm *t);
extern PalmLinTerm * claire__plus_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x);
extern PalmLinTerm * claire__plus_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t);
extern PalmLinTerm * claire__plus_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern PalmLinTerm * claire__plus_PalmUnTerm5(PalmUnTerm *t2,PalmLinTerm *t1);
extern PalmLinTerm * claire__plus_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__plus_PalmBinTerm5(PalmBinTerm *t2,PalmLinTerm *t1);
extern PalmLinTerm * claire__plus_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern PalmUnTerm * claire__dash_PalmIntVar1(PalmIntVar *x);
extern PalmUnTerm * claire__dash_PalmUnTerm1(PalmUnTerm *t);
extern PalmBinTerm * claire__dash_PalmBinTerm1(PalmBinTerm *t);
extern PalmLinTerm * claire__dash_PalmLinTerm1(PalmLinTerm *t);
extern PalmUnTerm * claire__dash_PalmIntVar2(PalmIntVar *t1,int t2);
extern PalmUnTerm * claire__dash_PalmUnTerm2(PalmUnTerm *t1,int t2);
extern PalmBinTerm * claire__dash_PalmBinTerm2(PalmBinTerm *t1,int t2);
extern PalmLinTerm * claire__dash_PalmLinTerm2(PalmLinTerm *t1,int t2);
extern PalmUnTerm * claire__dash_integer8(int t1,PalmIntVar *t2);
extern PalmBinTerm * claire__dash_PalmIntVar3(PalmIntVar *t1,PalmIntVar *t2);
extern PalmBinTerm * claire__dash_PalmUnTerm3(PalmUnTerm *t1,PalmIntVar *t2);
extern PalmLinTerm * claire__dash_PalmBinTerm3(PalmBinTerm *t1,PalmIntVar *t2);
extern PalmLinTerm * claire__dash_PalmLinTerm3(PalmLinTerm *t1,PalmIntVar *t2);
extern PalmUnTerm * claire__dash_integer9(int t1,PalmUnTerm *t2);
extern PalmBinTerm * claire__dash_PalmIntVar4(PalmIntVar *t1,PalmUnTerm *t2);
extern PalmBinTerm * claire__dash_PalmUnTerm4(PalmUnTerm *t1,PalmUnTerm *t2);
extern PalmLinTerm * claire__dash_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2);
extern PalmLinTerm * claire__dash_PalmLinTerm4(PalmLinTerm *t1,PalmUnTerm *t2);
extern PalmBinTerm * claire__dash_integer10(int t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__dash_PalmIntVar5(PalmIntVar *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__dash_PalmUnTerm5(PalmUnTerm *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__dash_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__dash_PalmLinTerm5(PalmLinTerm *t1,PalmBinTerm *t2);
extern PalmLinTerm * claire__dash_integer11(int t1,PalmLinTerm *t2);
extern PalmLinTerm * claire__dash_PalmIntVar6(PalmIntVar *t1,PalmLinTerm *t2);
extern PalmLinTerm * claire__dash_PalmUnTerm6(PalmUnTerm *t1,PalmLinTerm *t2);
extern PalmLinTerm * claire__dash_PalmBinTerm6(PalmBinTerm *t1,PalmLinTerm *t2);
extern PalmLinTerm * claire__dash_PalmLinTerm6(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_equal_integer7(int a,PalmUnTerm *t);
extern AbstractConstraint * claire__sup_equal_integer8(int a,PalmBinTerm *t);
extern AbstractConstraint * claire__sup_equal_integer9(int a,PalmLinTerm *t);
extern AbstractConstraint * claire__sup_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t);
extern AbstractConstraint * claire__sup_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t);
extern AbstractConstraint * claire__sup_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t);
extern AbstractConstraint * claire__sup_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2);
extern PalmUnIntConstraint * claire__sup_equal_PalmUnTerm3(PalmUnTerm *t,int c);
extern AbstractConstraint * claire__sup_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x);
extern AbstractConstraint * claire__sup_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_equal_PalmBinTerm2(PalmBinTerm *t,int c);
extern PalmLinComb * claire__sup_equal_PalmBinTerm3(PalmBinTerm *t,PalmIntVar *x);
extern PalmLinComb * claire__sup_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2);
extern PalmLinComb * claire__sup_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2);
extern PalmLinComb * claire__sup_equal_PalmLinTerm1(PalmLinTerm *t,int a);
extern PalmLinComb * claire__sup_equal_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x);
extern PalmLinComb * claire__sup_equal_PalmLinTerm3(PalmLinTerm *t,PalmUnTerm *t2);
extern PalmLinComb * claire__sup_equal_PalmLinTerm4(PalmLinTerm *t,PalmBinTerm *t2);
extern PalmLinComb * claire__sup_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_integer7(int t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_integer8(int t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_integer9(int t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_PalmUnTerm1(PalmUnTerm *t1,int t2);
extern AbstractConstraint * claire__sup_PalmBinTerm1(PalmBinTerm *t1,int t2);
extern AbstractConstraint * claire__sup_PalmLinTerm1(PalmLinTerm *t1,int t2);
extern AbstractConstraint * claire__sup_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__sup_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__sup_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__sup_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__sup_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__sup_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__sup_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_equal_integer8(int t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_equal_integer9(int t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_equal_integer10(int t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmUnTerm1(PalmUnTerm *t1,int t2);
extern AbstractConstraint * claire__inf_equal_PalmBinTerm1(PalmBinTerm *t1,int t2);
extern AbstractConstraint * claire__inf_equal_PalmLinTerm1(PalmLinTerm *t1,int t2);
extern AbstractConstraint * claire__inf_equal_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_equal_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_equal_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_equal_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_integer7(int t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_integer8(int t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_integer9(int t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_PalmUnTerm1(PalmUnTerm *t1,int t2);
extern AbstractConstraint * claire__inf_PalmBinTerm1(PalmBinTerm *t1,int t2);
extern AbstractConstraint * claire__inf_PalmLinTerm1(PalmLinTerm *t1,int t2);
extern AbstractConstraint * claire__inf_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2);
extern AbstractConstraint * claire__inf_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__inf_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__inf_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__inf_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__equal_equal_integer9(int a,PalmUnTerm *t);
extern AbstractConstraint * claire__equal_equal_integer10(int a,PalmBinTerm *t);
extern AbstractConstraint * claire__equal_equal_integer11(int a,PalmLinTerm *t);
extern AbstractConstraint * claire__equal_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t);
extern AbstractConstraint * claire__equal_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t);
extern AbstractConstraint * claire__equal_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t);
extern AbstractConstraint * claire__equal_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2);
extern AbstractConstraint * claire__equal_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__equal_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2);
extern PalmUnIntConstraint * claire__equal_equal_PalmUnTerm3(PalmUnTerm *t,int c);
extern AbstractConstraint * claire__equal_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x);
extern PalmBinIntConstraint * claire__equal_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2);
extern AbstractConstraint * claire__equal_equal_PalmBinTerm2(PalmBinTerm *t,int c);
extern PalmLinComb * claire__equal_equal_PalmBinTerm3(PalmBinTerm *t,PalmIntVar *x);
extern PalmLinComb * claire__equal_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2);
extern PalmLinComb * claire__equal_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2);
extern PalmLinComb * claire__equal_equal_PalmLinTerm1(PalmLinTerm *t,int c);
extern PalmLinComb * claire__equal_equal_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x);
extern PalmLinComb * claire__equal_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern PalmLinComb * claire__equal_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern PalmLinComb * claire__equal_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern AbstractConstraint * claire__I_equal_equal_integer6(int a,PalmUnTerm *t);
extern AbstractConstraint * claire__I_equal_equal_integer7(int a,PalmBinTerm *t);
extern OID  claire__I_equal_equal_integer8(int a,PalmLinTerm *t);
extern AbstractConstraint * claire__I_equal_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t);
extern OID  claire__I_equal_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t);
extern OID  claire__I_equal_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t);
extern OID  claire__I_equal_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2);
extern OID  claire__I_equal_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2);
extern OID  claire__I_equal_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2);
extern PalmUnIntConstraint * claire__I_equal_equal_PalmUnTerm3(PalmUnTerm *t,int c);
extern IntConstraint * claire__I_equal_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x);
extern IntConstraint * claire__I_equal_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2);
extern IntConstraint * claire__I_equal_equal_PalmBinTerm2(PalmBinTerm *t1,int c);
extern PalmLinComb * claire__I_equal_equal_PalmBinTerm3(PalmBinTerm *t1,PalmIntVar *x);
extern PalmLinComb * claire__I_equal_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2);
extern PalmLinComb * claire__I_equal_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2);
extern PalmLinComb * claire__I_equal_equal_PalmLinTerm1(PalmLinTerm *t1,int c);
extern PalmLinComb * claire__I_equal_equal_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *x);
extern PalmLinComb * claire__I_equal_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2);
extern PalmLinComb * claire__I_equal_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2);
extern PalmLinComb * claire__I_equal_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2);
extern PalmOccurrence * palm_makePalmOccurConstraint_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost);
extern PalmOccurTerm * palm_occur_integer(int tgt,list *l);
extern PalmOccurrence * claire__equal_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x);
extern PalmOccurrence * claire__equal_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot);
extern PalmOccurrence * claire__sup_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x);
extern PalmOccurrence * claire__inf_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x);
extern PalmOccurrence * claire__sup_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot);
extern PalmOccurrence * claire__inf_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot);
extern PalmEltTerm * choco_getNth_list3(list *l,PalmUnTerm *i);
extern PalmEltTerm * choco_getNth_list4(list *l,PalmIntVar *i);
extern PalmElt * claire__equal_equal_PalmEltTerm1(PalmEltTerm *t,PalmIntVar *x);
extern PalmElt * claire__equal_equal_PalmIntVar7(PalmIntVar *x,PalmEltTerm *t);
extern PalmElt * claire__equal_equal_PalmEltTerm2(PalmEltTerm *t,int x);
extern PalmElt * claire__equal_equal_integer12(int x,PalmEltTerm *t);
extern PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction1(PalmLargeDisjunction *c1,PalmLargeDisjunction *c2);
extern PalmLargeDisjunction * claire_e_dashor_PalmDisjunction1(PalmDisjunction *c1,PalmDisjunction *c2);
extern PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction2(PalmLargeDisjunction *c1,PalmDisjunction *c2);
extern PalmLargeDisjunction * claire_e_dashor_PalmDisjunction2(PalmDisjunction *c1,PalmLargeDisjunction *c2);
extern PalmLargeDisjunction * claire_e_dashor_PalmDisjunction3(PalmDisjunction *c1,AbstractConstraint *c2);
extern PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction3(PalmLargeDisjunction *c1,AbstractConstraint *c2);
extern PalmLargeDisjunction * claire_e_dashor_AbstractConstraint1(AbstractConstraint *c1,PalmDisjunction *c2);
extern PalmLargeDisjunction * claire_e_dashor_AbstractConstraint2(AbstractConstraint *c1,PalmLargeDisjunction *c2);
extern PalmDisjunction * claire_e_dashor_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2);
extern PalmLargeDisjunction * claire_e_dashor_list(list *l);
extern PalmLargeConjunction * claire_e_dashand_PalmConjunction1(PalmConjunction *c1,PalmConjunction *c2);
extern PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction1(PalmLargeConjunction *c1,PalmLargeConjunction *c2);
extern PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction2(PalmLargeConjunction *c1,PalmConjunction *c2);
extern PalmLargeConjunction * claire_e_dashand_PalmConjunction2(PalmConjunction *c1,PalmLargeConjunction *c2);
extern PalmLargeConjunction * claire_e_dashand_PalmConjunction3(PalmConjunction *c1,AbstractConstraint *c2);
extern PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction3(PalmLargeConjunction *c1,AbstractConstraint *c2);
extern PalmLargeConjunction * claire_e_dashand_AbstractConstraint1(AbstractConstraint *c1,PalmConjunction *c2);
extern PalmLargeConjunction * claire_e_dashand_AbstractConstraint2(AbstractConstraint *c1,PalmLargeConjunction *c2);
extern PalmConjunction * claire_e_dashand_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2);
extern PalmLargeConjunction * claire_e_dashand_list(list *l);
extern CompositeConstraint * claire_e_dashimplies_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern PalmGuard * palm_e_dashifThen_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern PalmEquiv * claire_e_dashiff_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern PalmCardinality * palm_makePalmCardinalityConstraint_list(list *l,PalmIntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost);
extern PalmCardinality * palm_e_dashcard_list1(list *l,PalmIntVar *v);
extern PalmCardinality * palm_e_dashatleast_list1(list *l,PalmIntVar *v);
extern PalmCardinality * palm_e_dashatmost_list1(list *l,PalmIntVar *v);
extern PalmCardinality * palm_e_dashcard_list2(list *l,int v);
extern PalmCardinality * palm_e_dashatleast_list2(list *l,int v);
extern PalmCardinality * palm_e_dashatmost_list2(list *l,int v);
extern void  palm_startInfoBox_PalmProblem(PalmProblem *pb,char *sname,char *fname);
extern void  palm_endInfoBox_PalmProblem(PalmProblem *pb);
extern OID  claire_self_print_PalmUserFriendlyBox_palm(PalmUserFriendlyBox *b);
extern void  claire_self_print_PalmProblem_palm(PalmProblem *pb);
extern OID  palm_showInfoBox_integer(int t,PalmUserFriendlyBox *b);
extern PalmUserFriendlyBox * palm_project_AbstractConstraint(AbstractConstraint *ct,PalmProblem *pb);
extern set * palm_getConstraints_PalmUserFriendlyBox(PalmUserFriendlyBox *rb);
extern OID  palm_setUserFriendly_boolean(ClaireBoolean *val);
extern void  palm_setUserRepresentation_PalmProblem(PalmProblem *pb,list *uv);
extern void  choco_post_PalmProblem9(PalmProblem *pb,PalmUserFriendlyBox *b);
extern ClaireBoolean * palm_checkPalm_AbstractConstraint(AbstractConstraint *ct);
extern ClaireBoolean * palm_checkFullPalm_PalmProblem(PalmProblem *pb);

// namespace class for palm 
class palmClass: public NameSpace {
public:

global_variable * MININT;
global_variable * MAXINT;
global_variable * UNKNOWNINT;
global_variable * Ephemeral;
global_variable * Problem;
global_variable * Solution;
global_variable * AbstractConstraint;
global_variable * IntConstraint;
global_variable * UnIntConstraint;
global_variable * BinIntConstraint;
global_variable * Elt;
global_variable * TernIntConstraint;
global_variable * LargeIntConstraint;
global_variable * LinComb;
global_variable * Delayer;
global_variable * CompositeConstraint;
global_variable * BinCompositeConstraint;
global_variable * BinBoolConstraint;
global_variable * BinBoolConstraintWCounterOpp;
global_variable * LargeCompositeConstraint;
global_variable * LargeBoolConstraint;
global_variable * LargeConjunction;
global_variable * LargeDisjunction;
global_variable * LargeBoolConstraintWCounterOpp;
global_variable * AbstractVar;
global_variable * IntVar;
global_variable * AbstractDomain;
global_variable * AbstractIntDomain;
global_variable * LinkedListIntDomain;
global_variable * PropagationEvent;
global_variable * ConstAwakeEvent;
global_variable * Instantiation;
global_variable * ValueRemovals;
global_variable * BoundUpdate;
global_variable * IncInf;
global_variable * DecSup;
global_variable * LogicEngine;
global_variable * PropagationEngine;
global_variable * ChocEngine;
global_variable * Solver;
global_variable * GlobalSearchSolver;
global_variable * LocalSearchSolver;
global_variable * EventCollection;
global_variable * InstantiationStack;
global_variable * EventQueue;
global_variable * BoundEventQueue;
global_variable * RemovalEventQueue;
global_variable * ConstAwakeEventQueue;
global_variable * Util;
global_variable * Matrix;
global_variable * Matrix2D;
global_variable * IntMatrix2D;
global_variable * BoolMatrix2D;
global_variable * MatrixND;
global_variable * BoolMatrixND;
global_variable * IntMatrixND;
global_variable * Term;
global_variable * OccurTerm;
global_variable * CompleteAllDiff;
global_variable * Permutation;
global_variable * GlobalCardinality;
global_variable * div_dash;
global_variable * div_plus;
global_variable * make2DBoolMatrix;
global_variable * qsize;
global_variable * qLastRead;
global_variable * qLastEnqueued;
global_variable * sLastRead;
global_variable * sLastPushed;
global_variable * isEmpty;
global_variable * makeBipartiteSet;
global_variable * partition;
global_variable * propagationEngine;
global_variable * removalEvtQueue;
global_variable * boundEvtQueue;
global_variable * eventQueue;
global_variable * engine;
global_variable * globalSearchSolver;
global_variable * localSearchSolver;
global_variable * attachPropagationEngine;
global_variable * setActiveProblem;
global_variable * varsToStore;
global_variable * constraints;
global_variable * solutions;
global_variable * post;
global_variable * delayedConst1;
global_variable * delayedConst2;
global_variable * delayedConst3;
global_variable * delayedConst4;
global_variable * instEvtStack;
global_variable * propagationOverflow;
global_variable * contradictionCause;
global_variable * maxSize;
global_variable * getNextActiveEventQueue;
global_variable * postUpdateInf;
global_variable * postUpdateSup;
global_variable * propagateEvent;
global_variable * redundantEvent;
global_variable * isPopping;
global_variable * flushEventQueue;
global_variable * popSomeEvents;
global_variable * registerEvent;
global_variable * hook;
global_variable * knownInt;
global_variable * isInstantiated;
global_variable * isInstantiatedTo;
global_variable * canBeInstantiatedTo;
global_variable * getInf;
global_variable * getSup;
global_variable * removeVal;
global_variable * updateInf;
global_variable * updateSup;
global_variable * instantiate;
global_variable * getDomainSize;
global_variable * connectIntVarEvents;
global_variable * reconnectIntVarEvents;
global_variable * disconnectIntVarEvents;
global_variable * connectEvent;
global_variable * disconnectEvent;
global_variable * reconnectEvent;
global_variable * connectHook;
global_variable * reconnectHook;
global_variable * disconnectHook;
global_variable * bucket;
global_variable * nbConstraints;
global_variable * problem;
global_variable * closeIntVar;
global_variable * addIntVar;
global_variable * nextConst;
global_variable * idxInQueue;
global_variable * nextEventPostIndex;
global_variable * cause;
global_variable * modifiedVar;
global_variable * many;
global_variable * valueStack;
global_variable * nbVals;
global_variable * causeStack;
global_variable * maxVals;
global_variable * connect;
global_variable * disconnect;
global_variable * reconnect;
global_variable * connectIntVar;
global_variable * constAwakeEvent;
global_variable * delay;
global_variable * isActive;
global_variable * getPriority;
global_variable * getVar;
global_variable * getNbVars;
global_variable * assignIndices;
global_variable * getConstraintIdx;
global_variable * setConstraintIndex;
global_variable * askIfEntailed;
global_variable * testIfSatisfied;
global_variable * testIfCompletelyInstantiated;
global_variable * propagate;
global_variable * awake;
global_variable * opposite;
global_variable * getProblem;
global_variable * setPassive;
global_variable * touchedConst;
global_variable * initialized;
global_variable * priority;
global_variable * awakeOnVar;
global_variable * awakeOnInf;
global_variable * awakeOnSup;
global_variable * awakeOnInst;
global_variable * awakeOnRem;
global_variable * constAwake;
global_variable * doAwakeOnInf;
global_variable * doAwakeOnSup;
global_variable * askIfTrue;
global_variable * doAwake;
global_variable * doAwakeOnRem;
global_variable * testIfTrue;
global_variable * doPropagate;
global_variable * closeLargeIntConstraint;
global_variable * inf;
global_variable * sup;
global_variable * v1;
global_variable * v2;
global_variable * idx1;
global_variable * idx2;
global_variable * cste;
global_variable * indices;
global_variable * nbVars;
global_variable * offset;
global_variable * updtInfEvt;
global_variable * updtSupEvt;
global_variable * remValEvt;
global_variable * domainSequence;
global_variable * domainSet;
global_variable * updateDomainInf;
global_variable * updateDomainSup;
global_variable * getDomainInf;
global_variable * getDomainSup;
global_variable * containsValInDomain;
global_variable * getNextValue;
global_variable * getPrevValue;
global_variable * removeDomainVal;
global_variable * restrict;
global_variable * getDomainCard;
global_variable * bucketSize;
global_variable * loffset;
global_variable * propagateNewLowerBound;
global_variable * propagateNewUpperBound;
global_variable * computeLowerBound;
global_variable * computeUpperBound;
global_variable * nbPosVars;
global_variable * coefs;
global_variable * op;
global_variable * GEQ;
global_variable * EQ;
global_variable * NEQ;
global_variable * improvedLowerBound;
global_variable * improvedUpperBound;
global_variable * filter;
global_variable * isPossible;
global_variable * nbPossible;
global_variable * isSure;
global_variable * nbSure;
global_variable * constrainOnInfNumber;
global_variable * constrainOnSupNumber;
global_variable * update;
global_variable * abstractIncInf;
global_variable * abstractDecSup;
global_variable * abstractRemoveVal;
global_variable * target;
global_variable * getNth;
global_variable * lval;
global_variable * lvalues;
global_variable * const1;
global_variable * const2;
global_variable * nbConst;
global_variable * lconst;
global_variable * loppositeConst;
global_variable * oppositeConst1;
global_variable * oppositeConst2;
global_variable * indicesInOppConst1;
global_variable * indicesInOppConst2;
global_variable * additionalVars;
global_variable * statusBitVectorList;
global_variable * statusBitVector;
global_variable * closeBoolConstraint;
global_variable * closeBoolConstraintWCounterOpp;
global_variable * knownStatus;
global_variable * knownTargetStatus;
global_variable * getTargetStatus;
global_variable * setStatus;
global_variable * setTargetStatus;
global_variable * getStatus;
global_variable * nbTrueStatus;
global_variable * nbFalseStatus;
global_variable * getCardVar;
global_variable * minValue;
global_variable * maxValue;
global_variable * nbLeftVertices;
global_variable * nbRightVertices;
global_variable * closeAssignmentConstraint;
global_variable * deleteEdgeAndPublish;
global_variable * deleteMatch;
global_variable * component;
global_variable * componentOrder;
global_variable * minFlow;
global_variable * maxFlow;
global_variable * flow;
global_variable * matchingSize;
global_variable * mustGrowFlowFromSource;
global_variable * nbVertices;
global_variable * findAlternatingPath;
global_variable * match;
global_variable * inverseMatch;
global_variable * augment;
global_variable * checkFlow;
global_variable * firstPassDFS;
global_variable * secondPassDFS;
global_variable * mayMatch;
ClaireClass * _PalmTools;
ClaireClass * _PalmIntVar;
ClaireClass * _PalmSolver;
ClaireClass * _PalmUserFriendlyBox;
ClaireClass * _PalmProblem;
ClaireClass * _PalmEngine;
ClaireClass * _DecInf;
ClaireClass * _IncSup;
ClaireClass * _ValueRestorations;
ClaireClass * _Explanation;
ClaireClass * _PalmContradictionExplanation;
ClaireClass * _PalmBoundExplanation;
ClaireClass * _PalmIncInfExplanation;
ClaireClass * _PalmDecSupExplanation;
ClaireClass * _PalmRemovalExplanation;
ClaireClass * _PalmIntDomain;
property * raisePalmContradiction;
ClaireClass * _PalmContradiction;
ClaireClass * _PalmSolverTools;
ClaireClass * _PalmState;
ClaireClass * _PalmLearn;
ClaireClass * _PathRepairLearn;
ClaireClass * _PalmExtend;
ClaireClass * _PalmBranching;
ClaireClass * _PalmUnsureExtend;
ClaireClass * _PalmRepair;
ClaireClass * _PalmUnsureRepair;
ClaireClass * _PalmSolution;
ClaireClass * _PalmBranchAndBound;
global_variable * PalmVIEW;
global_variable * PalmTALK;
global_variable * PalmDEBUG;
global_variable * PalmOPTSHOW;
global_variable * PALM_TIME_STAMP;
global_variable * PALM_MAXSIZE;
global_variable * PALM_BITALLONE;
global_variable * PALM_USER_FRIENDLY;
global_variable * UNKNOWN_ABS_CT;
global_variable * UFcurrentBox;
global_variable * INF;
global_variable * SUP;
global_variable * VAL;
global_variable * DOM;
global_variable * SELECT;
ClaireClass * _PalmControlConstraint;
ClaireClass * _PalmInfoConstraint;
property * whyIsFalse;
property * whyIsTrue;
property * awakeOnRestoreInf;
property * awakeOnRestoreSup;
property * awakeOnRestoreVal;
ClaireClass * _PalmUnIntConstraint;
ClaireClass * _PalmBinIntConstraint;
ClaireClass * _PalmLargeIntConstraint;
ClaireClass * _PalmGreaterOrEqualxc;
ClaireClass * _PalmLessOrEqualxc;
ClaireClass * _PalmNotEqualxc;
ClaireClass * _PalmEqualxc;
ClaireClass * _AssignmentConstraint;
ClaireClass * _PalmNotEqualxyc;
ClaireClass * _PalmEqualxyc;
ClaireClass * _PalmGreaterOrEqualxyc;
ClaireClass * _PalmLessOrEqualxyc;
ClaireClass * _PalmLinComb;
global_variable * ALLDIFF_PRECISE_EXPLANATIONS;
ClaireClass * _PalmCompleteAllDiff;
ClaireClass * _PalmPermutation;
ClaireClass * _PalmGlobalCardinality;
ClaireClass * _PalmGlobalCardinalityVar;
ClaireClass * _PalmOccurrence;
ClaireClass * _PalmElt;
ClaireClass * _PalmAC4BinConstraint;
ClaireClass * _PalmAssignVar;
global_variable * RLX;
global_variable * EXT;
global_variable * CPU;
global_variable * RuntimeStatistic;
ClaireClass * _PalmAssignMinDomVar;
ClaireClass * _PalmAssignMinDomDegVar;
ClaireClass * _PalmDisjunction;
ClaireClass * _PalmGuard;
ClaireClass * _PalmEquiv;
ClaireClass * _PalmConjunction;
ClaireClass * _PalmLargeConjunction;
ClaireClass * _PalmLargeDisjunction;
ClaireClass * _PalmCardinality;
ClaireClass * _PalmTempTerm;
ClaireClass * _PalmUnTerm;
ClaireClass * _PalmBinTerm;
ClaireClass * _PalmLinTerm;
operation * e_dashscalar;
ClaireClass * _PalmOccurTerm;
ClaireClass * _PalmEltTerm;
operation * e_dashor;
operation * e_dashimplies;
operation * e_dashiff;
operation * e_dashand;
property * palmVersion;// palm/"palmVersion"
property * palmReleaseDate;// palm/"palmReleaseDate"
property * palmInfo;// palm/"palmInfo"
property * showPalmLicense;// palm/"showPalmLicense"
property * maxRelaxLvl;// palm/"maxRelaxLvl"
property * palmSolver;// palm/"palmSolver"
property * rootUFboxes;// palm/"rootUFboxes"
property * relaxedUFboxes;// palm/"relaxedUFboxes"
property * allUFboxes;// palm/"allUFboxes"
property * userRepresentation;// palm/"userRepresentation"
property * toBeAwakenConstraints;// palm/"toBeAwakenConstraints"
property * boundRestEvtQueue;// palm/"boundRestEvtQueue"
property * removalRestEvtQueue;// palm/"removalRestEvtQueue"
property * dummyVariable;// palm/"dummyVariable"
property * contradictory;// palm/"contradictory"
property * explanation;// palm/"explanation"
property * previousValue;// palm/"previousValue"
property * variable;// palm/"variable"
property * originalInf;// palm/"originalInf"
property * originalSup;// palm/"originalSup"
property * explanationOnInf;// palm/"explanationOnInf"
property * explanationOnSup;// palm/"explanationOnSup"
property * enumerationConstraints;// palm/"enumerationConstraints"
property * negEnumerationConstraints;// palm/"negEnumerationConstraints"
property * restInfEvt;// palm/"restInfEvt"
property * restSupEvt;// palm/"restSupEvt"
property * restValEvt;// palm/"restValEvt"
property * nbElements;// palm/"nbElements"
property * booleanVector;// palm/"booleanVector"
property * precVector;// palm/"precVector"
property * succVector;// palm/"succVector"
property * firstSuccPres;// palm/"firstSuccPres"
property * firstPrecPres;// palm/"firstPrecPres"
property * firstSuccAbs;// palm/"firstSuccAbs"
property * firstPrecAbs;// palm/"firstPrecAbs"
property * needInfComputation;// palm/"needInfComputation"
property * needSupComputation;// palm/"needSupComputation"
property * explanationOnVal;// palm/"explanationOnVal"
property * instantiationConstraints;// palm/"instantiationConstraints"
property * negInstantiationConstraints;// palm/"negInstantiationConstraints"
property * manager;// palm/"manager"
property * path;// palm/"path"
property * maxMoves;// palm/"maxMoves"
property * explanations;// palm/"explanations"
property * lastExplanation;// palm/"lastExplanation"
property * isFull;// palm/"isFull"
property * extender;// palm/"extender"
property * nextBranching;// palm/"nextBranching"
property * branching;// palm/"branching"
property * feasible;// palm/"feasible"
property * lstat;// palm/"lstat"
property * statistics;// palm/"statistics"
property * finished;// palm/"finished"
property * state;// palm/"state"
property * learning;// palm/"learning"
property * extending;// palm/"extending"
property * repairing;// palm/"repairing"
property * maximizing;// palm/"maximizing"
property * objective;// palm/"objective"
property * lowerBound;// palm/"lowerBound"
property * upperBound;// palm/"upperBound"
property * optimum;// palm/"optimum"
property * dynamicCuts;// palm/"dynamicCuts"
property * shortName;// palm/"shortName"
property * fatherBox;// palm/"fatherBox"
property * childrenBoxes;// palm/"childrenBoxes"
property * not_I;// palm/"not!"
property * firstCode29bits;// palm/"firstCode29bits"
property * secondCode29bits;// palm/"secondCode29bits"
property * decode29bits;// palm/"decode29bits"
property * makePalmIntDomain;// palm/"makePalmIntDomain"
property * removedlist_I;// palm/"removedlist!"
property * firstElement;// palm/"firstElement"
property * addDomainVal;// palm/"addDomainVal"
property * self_explain;// palm/"self_explain"
property * merge;// palm/"merge"
property * firstValue;// palm/"firstValue"
property * updateDataStructures;// palm/"updateDataStructures"
property * updateDataStructuresOnVariable;// palm/"updateDataStructuresOnVariable"
property * updateDataStructuresOnConstraints;// palm/"updateDataStructuresOnConstraints"
property * updateDataStructuresOnConstraint;// palm/"updateDataStructuresOnConstraint"
property * updateDataStructuresOnRestore;// palm/"updateDataStructuresOnRestore"
property * updateDataStructuresOnRestoreVariable;// palm/"updateDataStructuresOnRestoreVariable"
property * updateDataStructuresOnRestoreConstraints;// palm/"updateDataStructuresOnRestoreConstraints"
property * updateDataStructuresOnRestoreConstraint;// palm/"updateDataStructuresOnRestoreConstraint"
property * restoreInf;// palm/"restoreInf"
property * postRestoreInf;// palm/"postRestoreInf"
property * restoreSup;// palm/"restoreSup"
property * postRestoreSup;// palm/"postRestoreSup"
property * postRestoreEvent;// palm/"postRestoreEvent"
property * restoreVariableExplanation;// palm/"restoreVariableExplanation"
property * restoreVal;// palm/"restoreVal"
property * postRestoreVal;// palm/"postRestoreVal"
property * valid_ask;// palm/"valid?"
property * clone;// palm/"clone"
property * makeIncInfExplanation;// palm/"makeIncInfExplanation"
property * makeDecSupExplanation;// palm/"makeDecSupExplanation"
property * postRemoveVal;// palm/"postRemoveVal"
property * makeRemovalExplanation;// palm/"makeRemovalExplanation"
property * constraint;// palm/"constraint"
property * index;// palm/"index"
property * timeStamp;// palm/"timeStamp"
property * weight;// palm/"weight"
property * everConnected;// palm/"everConnected"
property * indirect;// palm/"indirect"
property * addingExplanation;// palm/"addingExplanation"
property * dependingConstraints;// palm/"dependingConstraints"
property * dependencyNet;// palm/"dependencyNet"
property * searchInfo;// palm/"searchInfo"
property * ufBox;// palm/"ufBox"
property * controllingConstraints;// palm/"controllingConstraints"
property * info;// palm/"info"
property * addDependency;// palm/"addDependency"
property * removeDependency;// palm/"removeDependency"
property * undo;// palm/"undo"
property * postUndoRemoval;// palm/"postUndoRemoval"
property * indirect_ask;// palm/"indirect?"
property * setIndirect;// palm/"setIndirect"
property * setDirect;// palm/"setDirect"
property * setIndirectDependance;// palm/"setIndirectDependance"
property * removeIndirectDependance;// palm/"removeIndirectDependance"
property * informControllersOfDeactivation;// palm/"informControllersOfDeactivation"
property * takeIntoAccountStatusChange;// palm/"takeIntoAccountStatusChange"
property * mergeConstraints;// palm/"mergeConstraints"
property * addConstraint;// palm/"addConstraint"
property * activate;// palm/"activate"
property * deactivate;// palm/"deactivate"
property * initHook;// palm/"initHook"
property * addControl;// palm/"addControl"
property * makePalmUnIntConstraint;// palm/"makePalmUnIntConstraint"
property * makePalmBinIntConstraint;// palm/"makePalmBinIntConstraint"
property * makePalmLargeIntConstraint;// palm/"makePalmLargeIntConstraint"
property * checkPalm;// palm/"checkPalm"
property * hasSupport;// palm/"hasSupport"
property * makePalmLinComb;// palm/"makePalmLinComb"
property * explainVariablesLB;// palm/"explainVariablesLB"
property * explainVariablesUB;// palm/"explainVariablesUB"
property * raisePalmFakeContradiction;// palm/"raisePalmFakeContradiction"
property * abstractDecInf;// palm/"abstractDecInf"
property * abstractIncSup;// palm/"abstractIncSup"
property * abstractRestoreVal;// palm/"abstractRestoreVal"
property * e_dashallDifferent;// claire/"e-allDifferent"
property * e_dashcompleteAllDiff;// claire/"e-completeAllDiff"
property * e_dashpermutation;// claire/"e-permutation"
property * publishDeletion;// palm/"publishDeletion"
property * theHole;// palm/"theHole"
property * becauseOf;// palm/"becauseOf"
property * e_dashgcc;// palm/"e-gcc"
property * intervals;// palm/"intervals"
property * mustDiminishFlowFromSource;// palm/"mustDiminishFlowFromSource"
property * removeUselessEdges;// palm/"removeUselessEdges"
property * checkPossible;// palm/"checkPossible"
property * checkSure;// palm/"checkSure"
property * checkInf;// palm/"checkInf"
property * checkSup;// palm/"checkSup"
property * checkNbPossible;// palm/"checkNbPossible"
property * checkNbSure;// palm/"checkNbSure"
property * makePalmEltConstraint;// palm/"makePalmEltConstraint"
property * updateValueFromIndex;// palm/"updateValueFromIndex"
property * updateIndexFromValue;// palm/"updateIndexFromValue"
property * awakeOnRestore;// palm/"awakeOnRestore"
property * whyTrueOrFalse;// palm/"whyTrueOrFalse"
property * cachedTuples;// palm/"cachedTuples"
property * matrix;// palm/"matrix"
property * feasTest;// palm/"feasTest"
property * supportsOnV1;// palm/"supportsOnV1"
property * nbSupportsOnV1;// palm/"nbSupportsOnV1"
property * supportsOnV2;// palm/"supportsOnV2"
property * nbSupportsOnV2;// palm/"nbSupportsOnV2"
property * makeAC4constraint;// palm/"makeAC4constraint"
property * project;// palm/"project"
property * deleteConstraint;// palm/"deleteConstraint"
property * empty_ask;// palm/"empty?"
property * empties;// palm/"empties"
property * contains_ask;// palm/"contains?"
property * clean;// palm/"clean"
property * makeValueRestorations;// palm/"makeValueRestorations"
property * addDependencies;// palm/"addDependencies"
property * removeDependencies;// palm/"removeDependencies"
property * makePalmEngine;// palm/"makePalmEngine"
property * resetEvent;// palm/"resetEvent"
property * resetPoppingQueue;// palm/"resetPoppingQueue"
property * resetEventQueue;// palm/"resetEventQueue"
property * resetEvents;// palm/"resetEvents"
property * flushEvents;// palm/"flushEvents"
property * makeBoundIntVar;// palm/"makeBoundIntVar"
property * raiseSystemContradiction;// palm/"raiseSystemContradiction"
property * remove;// palm/"remove"
property * restoreVariableExplanations;// palm/"restoreVariableExplanations"
property * checkVariableDomainAgainstRemValEvt;// palm/"checkVariableDomainAgainstRemValEvt"
property * theInf;// palm/"theInf"
property * theSup;// palm/"theSup"
property * theDom;// palm/"theDom"
property * theVars;// palm/"theVars"
property * explain;// palm/"explain"
property * getGlobalStatistics;// palm/"getGlobalStatistics"
property * getRuntimeStatistic;// palm/"getRuntimeStatistic"
property * setRuntimeStatistic;// palm/"setRuntimeStatistic"
property * incRuntimeStatistic;// palm/"incRuntimeStatistic"
property * decRuntimeStatistic;// palm/"decRuntimeStatistic"
property * printRuntimeStatistics;// palm/"printRuntimeStatistics"
property * reset;// palm/"reset"
property * initPalmSolver;// palm/"initPalmSolver"
property * attachPalmState;// palm/"attachPalmState"
property * attachPalmExtend;// palm/"attachPalmExtend"
property * attachPalmBranchings;// palm/"attachPalmBranchings"
property * attachPalmLearn;// palm/"attachPalmLearn"
property * attachPalmRepair;// palm/"attachPalmRepair"
property * initPalmBranchAndBound;// palm/"initPalmBranchAndBound"
property * searchOneSolution;// palm/"searchOneSolution"
property * run;// palm/"run"
property * storeSolution;// palm/"storeSolution"
property * repair;// palm/"repair"
property * extend;// palm/"extend"
property * runonce;// palm/"runonce"
property * postDynamicCut;// palm/"postDynamicCut"
property * getObjectiveValue;// palm/"getObjectiveValue"
property * explore;// palm/"explore"
property * selectBranchingItem;// palm/"selectBranchingItem"
property * selectDecisions;// palm/"selectDecisions"
property * propagateAllDecisionConstraints;// palm/"propagateAllDecisionConstraints"
property * selectAuthorizedDecisions;// palm/"selectAuthorizedDecisions"
property * getNextDecisions;// palm/"getNextDecisions"
property * checkAcceptable;// palm/"checkAcceptable"
property * learnFromRejection;// palm/"learnFromRejection"
property * addDecision;// palm/"addDecision"
property * learnFromContradiction;// palm/"learnFromContradiction"
property * selectDecisionToUndo;// palm/"selectDecisionToUndo"
property * removeDecision;// palm/"removeDecision"
property * negate;// palm/"negate"
property * reverseDecision;// palm/"reverseDecision"
property * learnFromRemoval;// palm/"learnFromRemoval"
property * sortConstraintsToUndo;// palm/"sortConstraintsToUndo"
property * checkAcceptableRelaxation;// palm/"checkAcceptableRelaxation"
property * standard_better_constraint;// palm/"standard_better_constraint"
property * standard_better_ordered_constraint;// palm/"standard_better_ordered_constraint"
property * getMinDomVar;// palm/"getMinDomVar"
property * assign;// palm/"assign"
property * getMinDomDegVar;// palm/"getMinDomDegVar"
property * discardCurrentSolution;// palm/"discardCurrentSolution"
property * makePathRepairLearn;// palm/"makePathRepairLearn"
property * addForbiddenSituation;// palm/"addForbiddenSituation"
property * isForbidden;// palm/"isForbidden"
property * informConstraintsInExplanation;// palm/"informConstraintsInExplanation"
property * isContradictory;// palm/"isContradictory"
property * needToAwakeC1;// palm/"needToAwakeC1"
property * needToAwakeC2;// palm/"needToAwakeC2"
property * setNeedToAwake;// palm/"setNeedToAwake"
property * needToAwake;// palm/"needToAwake"
property * checkStatusAndReport;// palm/"checkStatusAndReport"
property * checkConstraintState;// palm/"checkConstraintState"
property * needToAwakeNegC1;// palm/"needToAwakeNegC1"
property * needToAwakeNegC2;// palm/"needToAwakeNegC2"
property * needToAwakeTrue;// palm/"needToAwakeTrue"
property * needToAwakeFalse;// palm/"needToAwakeFalse"
property * checkOnNbTrue;// palm/"checkOnNbTrue"
property * checkOnNbFalse;// palm/"checkOnNbFalse"
property * explainTrueConstraints;// palm/"explainTrueConstraints"
property * explainFalseConstraints;// palm/"explainFalseConstraints"
property * makePalmProblem;// palm/"makePalmProblem"
property * setObjective;// palm/"setObjective"
property * setSolutionVars;// palm/"setSolutionVars"
property * solve;// palm/"solve"
property * minimize;// palm/"minimize"
property * maximize;// palm/"maximize"
property * makeIntVar;// palm/"makeIntVar"
property * makeConstantPalmIntVar;// palm/"makeConstantPalmIntVar"
property * setMin;// palm/"setMin"
property * setMax;// palm/"setMax"
property * setVal;// palm/"setVal"
property * remVal;// palm/"remVal"
property * makeConstraintTable;// palm/"makeConstraintTable"
property * e_dashcard;// palm/"e-card"
property * sign1;// palm/"sign1"
property * sign2;// palm/"sign2"
property * lcoeff;// palm/"lcoeff"
property * lvars;// palm/"lvars"
property * e_dashsumVars;// palm/"e-sumVars"
property * makePalmOccurConstraint;// palm/"makePalmOccurConstraint"
property * occur;// palm/"occur"
property * e_dashifThen;// palm/"e-ifThen"
property * makePalmCardinalityConstraint;// palm/"makePalmCardinalityConstraint"
property * e_dashatleast;// palm/"e-atleast"
property * e_dashatmost;// palm/"e-atmost"
property * startInfoBox;// palm/"startInfoBox"
property * endInfoBox;// palm/"endInfoBox"
property * showInfoBox;// palm/"showInfoBox"
property * getConstraints;// palm/"getConstraints"
property * setUserFriendly;// palm/"setUserFriendly"
property * setUserRepresentation;// palm/"setUserRepresentation"
property * checkFullPalm;// palm/"checkFullPalm"

// module definition 
 void metaLoad();};

extern palmClass palm;

#endif
