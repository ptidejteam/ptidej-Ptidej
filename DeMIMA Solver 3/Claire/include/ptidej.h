// interface defination for module ptidej, Wed Oct 19 15:35:17 2005
#ifndef CLAIREH_ptidej
#define CLAIREH_ptidej


class Entity;
class PtidejProblem;
class PtidejBinConstraint;
class PtidejLargeConstraint;
class PtidejAC4Constraint;
class PtidejVar;
class PtidejBoundVar;
class GeneralPalmRepair;
class PtidejAssignVar;
class PtidejBranching;
class PtidejLearn;
class PtidejRepair;
class PtidejSolver;
class MinimumPtidejRepair;
class PtidejInteractiveBranching;
class PtidejAutomaticBranching;
class PropertyTypeConstraint;
class ListPropertyTypeConstraint;
class PropertyDistanceConstraint;
class EqualConstraint;
class NotEqualConstraint;
class GreaterOrEqualPtidejConstraint;
class LessOrEqualPtidejConstraint;
class AggregationConstraint;
class AssociationConstraint;
class CompositionConstraint;
class ContainerAggregationConstraint;
class ContainerCompositionConstraint;
class UseConstraint;
class IgnoranceConstraint;
class CreationConstraint;
class AssociationDistanceConstraint;
class UseDistanceConstraint;
class InheritanceTreeDepthConstraint;
class InheritancePathConstraint;
class StrictInheritancePathConstraint;
class InheritanceConstraint;
class StrictInheritanceConstraint;
class PtidejSaveAllSolutions;
class PtidejSimpleAutomaticRepair;
class PtidejSimpleInteractiveRepair;
class PtidejAutomaticRepair;
class PtidejInteractiveRepair;
class BitString;
class PtidejCombinatorialAutomaticRepair;

class Entity: public ephemeral_object{ 
  public:
     char *name;
     list *superEntities;
     list *cachedReachableSuperEntities;
     list *aggregatedEntities;
     list *cachedReachableAggregatedEntities;
     list *associatedEntities;
     list *cachedReachableAssociatedEntities;
     list *componentsType;
     list *composedEntities;
     list *containerAggregatedEntities;
     list *cachedReachableContainerAggregatedEntities;
     list *containerComponentsType;
     list *containerComposedEntities;
     list *cachedReachableContainerComposedEntities;
     list *createdEntities;
     list *cachedReachableCreatedEntities;
     list *knownEntities;
     list *cachedReachableKnownEntities;
     list *unknownEntities;
     list *cachedReachableUnknownEntities;} 
;

class PtidejProblem: public PalmProblem{ 
  public:
     int globalWeight;} 
;

class PtidejBinConstraint: public PalmBinIntConstraint{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class PtidejLargeConstraint: public PalmLargeIntConstraint{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class PtidejAC4Constraint: public PalmAC4BinConstraint{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class PtidejVar: public PalmIntVar{ 
  public:
     ClaireBoolean *toBeEnumerated;} 
;

class PtidejBoundVar: public PtidejVar{ 
  public:} 
;

class GeneralPalmRepair: public PalmRepair{ 
  public:} 
;

class PtidejAssignVar: public PalmAssignVar{ 
  public:} 
;

class PtidejBranching: public PalmBranching{ 
  public:
     int solutionNumber;
     char *message;
     int percentage;
     char *xCommand;} 
;

class PtidejLearn: public PalmLearn{ 
  public:} 
;

class PtidejRepair: public GeneralPalmRepair{ 
  public:
     PtidejProblem *problem;
     list *userRelaxedConstraints;} 
;

class PtidejSolver: public PalmSolver{ 
  public:} 
;

class MinimumPtidejRepair: public PtidejRepair{ 
  public:} 
;

class PtidejInteractiveBranching: public PtidejBranching{ 
  public:} 
;

class PtidejAutomaticBranching: public PtidejBranching{ 
  public:} 
;

class PropertyTypeConstraint: public PtidejBinConstraint{ 
  public:
     property *field;} 
;

class ListPropertyTypeConstraint: public PtidejBinConstraint{ 
  public:
     property *field;} 
;

class PropertyDistanceConstraint: public PtidejLargeConstraint{ 
  public:
     property *field;} 
;

class EqualConstraint: public PalmEqualxyc{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class NotEqualConstraint: public PalmNotEqualxyc{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class GreaterOrEqualPtidejConstraint: public PalmGreaterOrEqualxc{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class LessOrEqualPtidejConstraint: public PalmLessOrEqualxc{ 
  public:
     char *name;
     char *command;
     OID thisConstraint;
     OID nextConstraint;} 
;

class AggregationConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class AssociationConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class CompositionConstraint: public ListPropertyTypeConstraint{ 
  public:} 
;

class ContainerAggregationConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class ContainerCompositionConstraint: public ListPropertyTypeConstraint{ 
  public:} 
;

class UseConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class IgnoranceConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class CreationConstraint: public PropertyTypeConstraint{ 
  public:} 
;

class AssociationDistanceConstraint: public PropertyDistanceConstraint{ 
  public:} 
;

class UseDistanceConstraint: public PropertyDistanceConstraint{ 
  public:} 
;

class InheritanceTreeDepthConstraint: public PropertyDistanceConstraint{ 
  public:} 
;

class InheritancePathConstraint: public PtidejBinConstraint{ 
  public:} 
;

class StrictInheritancePathConstraint: public PtidejBinConstraint{ 
  public:} 
;

class InheritanceConstraint: public PtidejBinConstraint{ 
  public:} 
;

class StrictInheritanceConstraint: public PtidejBinConstraint{ 
  public:} 
;

class PtidejSaveAllSolutions: public PtidejBranching{ 
  public:} 
;

class PtidejSimpleAutomaticRepair: public PtidejRepair{ 
  public:} 
;

class PtidejSimpleInteractiveRepair: public PtidejRepair{ 
  public:} 
;

class PtidejAutomaticRepair: public PtidejRepair{ 
  public:
     PtidejBranching *ptidejBranching;
     list *removableConstraints;
     ClaireBoolean *areAllConstraintsRelaxed;} 
;

class PtidejInteractiveRepair: public PtidejRepair{ 
  public:} 
;

class BitString: public ephemeral_object{ 
  public:
     int numberOfBits;
     int value;} 
;

class PtidejCombinatorialAutomaticRepair: public PtidejRepair{ 
  public:} 
;
extern char * ptidej_ptidejVersion_void();
extern char * ptidej_ptidejReleaseDate_void();
extern char * ptidej_ptidejInfo_void();
extern void  ptidej_showPtidejLicense_void();
extern void  claire_self_print_Entity_ptidej(Entity *e);
extern property * ptidej_getCachedReachableRelationship_property(property *relationship);
extern void  claire_self_print_list2_ptidej(list *l);
extern int  ptidej_getGlobalWeight_PtidejProblem(PtidejProblem *p);
extern void  ptidej_setGlobalWeight_PtidejProblem(PtidejProblem *p,int gw);
extern char * ptidej_getName_PtidejBinConstraint(PtidejBinConstraint *c);
extern char * ptidej_getXCommand_PtidejBinConstraint(PtidejBinConstraint *c);
extern OID  ptidej_getThisConstraint_PtidejBinConstraint(PtidejBinConstraint *c);
extern OID  ptidej_getNextConstraint_PtidejBinConstraint(PtidejBinConstraint *c);
extern int  palm_weight_PtidejBinConstraint(PtidejBinConstraint *c);
extern char * ptidej_getName_PtidejLargeConstraint(PtidejLargeConstraint *c);
extern char * ptidej_getXCommand_PtidejLargeConstraint(PtidejLargeConstraint *c);
extern OID  ptidej_getThisConstraint_PtidejLargeConstraint(PtidejLargeConstraint *c);
extern OID  ptidej_getNextConstraint_PtidejLargeConstraint(PtidejLargeConstraint *c);
extern int  palm_weight_PtidejLargeConstraint(PtidejLargeConstraint *c);
extern char * ptidej_getName_PtidejAC4Constraint(PtidejAC4Constraint *c);
extern char * ptidej_getXCommand_PtidejAC4Constraint(PtidejAC4Constraint *c);
extern OID  ptidej_getThisConstraint_PtidejAC4Constraint(PtidejAC4Constraint *c);
extern OID  ptidej_getNextConstraint_PtidejAC4Constraint(PtidejAC4Constraint *c);
extern int  palm_weight_PtidejAC4Constraint(PtidejAC4Constraint *c);
extern void  ptidej_setVarsToShow_GlobalSearchSolver1(GlobalSearchSolver *globalSearchSolver,list *vars);
extern void  ptidej_setVarsToShow_GlobalSearchSolver2(GlobalSearchSolver *globalSearchSolver,list *vars);
extern void  choco_attach_LocalSearchSolver(LocalSearchSolver *newSolver,PalmProblem *pb);
extern void  palm_remove_GeneralPalmRepair(GeneralPalmRepair *repairer,AbstractConstraint *constraint);
extern list * ptidej_getRemovedConstraints_GeneralPalmRepair(GeneralPalmRepair *repairer);
extern tuple * palm_selectDecisionToUndo_GeneralPalmRepair_ptidej(GeneralPalmRepair *repairer,Explanation *e);
extern tuple * palm_selectDecisionToUndo_GeneralPalmRepair_ptidej_(GeneralPalmRepair *g0005,Explanation *g0006);
extern tuple * palm_selectDecisionToUndo_MinimumPtidejRepair_ptidej(MinimumPtidejRepair *repairer,Explanation *e);
extern tuple * palm_selectDecisionToUndo_MinimumPtidejRepair_ptidej_(MinimumPtidejRepair *g0007,Explanation *g0008);
extern PtidejProblem * ptidej_makePtidejProblem_string(char *s,int sizeOfTheDomain,int maxRelaxationLevel);
extern OID  palm_selectBranchingItem_PtidejAssignVar_ptidej(PtidejAssignVar *b);
extern void  palm_remove_PtidejRepair(PtidejRepair *repairer,AbstractConstraint *constraint);
extern list * ptidej_getRemovedConstraints_PtidejRepair(PtidejRepair *repairer);
extern void  ptidej_setRemovedConstraints_PtidejRepair(PtidejRepair *repairer,list *l);
extern int  ptidej_getProblemRelaxationLevel_PtidejRepair(PtidejRepair *repairer);
extern PtidejProblem * choco_getProblem_PtidejRepair(PtidejRepair *repairer);
extern void  ptidej_setProblem_PtidejRepair(PtidejRepair *repairer,PtidejProblem *pb);
extern void  ptidej_setSolutionNumber_PtidejBranching(PtidejBranching *b,int n);
extern int  ptidej_getSolutionNumber_PtidejBranching(PtidejBranching *b);
extern void  ptidej_setMessage_PtidejBranching(PtidejBranching *b,char *m);
extern char * ptidej_getMessage_PtidejBranching(PtidejBranching *b);
extern void  ptidej_setPercentage_PtidejBranching(PtidejBranching *b,int p);
extern int  ptidej_getPercentage_PtidejBranching(PtidejBranching *b);
extern void  ptidej_setXCommand_PtidejBranching(PtidejBranching *b,char *c);
extern char * ptidej_getXCommand_PtidejBranching(PtidejBranching *b);
extern PtidejVar * ptidej_makePtidejVar_PalmProblem1(PalmProblem *p,char *s,int i,int j);
extern PtidejVar * ptidej_makePtidejVar_PalmProblem2(PalmProblem *p,char *s,int i,int j,ClaireBoolean *enumerate);
extern PtidejBoundVar * ptidej_makePtidejBoundVar_PalmProblem1(PalmProblem *p,char *s,int i,int j);
extern PtidejBoundVar * ptidej_makePtidejBoundVar_PalmProblem2(PalmProblem *p,char *s,int i,int j,ClaireBoolean *enumerate);
extern void  claire_self_print_PtidejVar_ptidej(PtidejVar *v);
extern int  ptidej_userAssignSelectVar_list(list *l);
extern ClaireBoolean * ptidej_isBetterConstraint_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2);
extern void  ptidej_getExplanations_list(list *vs,Explanation *expl);
extern OID  ptidej_clearResultFile_void();
extern void  ptidej_printOutSolution_PtidejProblem(PtidejProblem *pb,char *solutionMessage,int solutionNumber,int solutionPercentage,char *xCommand);
extern void  ptidej_printOutTime_integer(int t);
extern void  ptidej_printHeader_void();
extern void  ptidej_printFooter_void();
extern void  ptidej_printStatus_void();
extern OID  palm_selectBranchingItem_PtidejInteractiveBranching_ptidej(PtidejInteractiveBranching *b);
extern OID  palm_selectBranchingItem_PtidejAutomaticBranching_ptidej(PtidejAutomaticBranching *b);
extern void  ptidej_searchConcretePatterns_method(method *solver,PtidejProblem *problem);
extern void  palm_repair_PtidejProblem_ptidej(PtidejProblem *pb);
extern void  palm_repair_PtidejSolver_ptidej(PtidejSolver *algo);
extern PtidejAC4Constraint * ptidej_makePtidejAC4Constraint_string(char *nm,char *co,PtidejVar *u,PtidejVar *v,ClaireBoolean *feasible,list *mytuples,OID thisMet,OID nextMet);
extern void  claire_self_print_PtidejAC4Constraint_ptidej(PtidejAC4Constraint *c);
extern PtidejAC4Constraint * ptidej_makeAggregationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeAssociationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeAwarenessAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeCompositionAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeContainerAggregationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeContainerCompositionAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeCreationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeIgnoranceAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeInheritanceAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeInheritancePathAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeUseAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeNoAssociationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeNoAggregationAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeNoCompositionAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeStrictInheritanceAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern PtidejAC4Constraint * ptidej_makeStrictInheritancePathAC4Constraint_string_ptidej(char *n,char *co,PtidejVar *c1,PtidejVar *c2);
extern list * ptidej_getAllReachableEntities_Entity(Entity *entity,list *listOfAllEntities,property *f);
extern PtidejBinConstraint * ptidej_makePropertyTypeConstraint_class(ClaireClass *ct,char *na,char *co,PtidejVar *v1,PtidejVar *v2,property *fd);
extern void  claire_self_print_PropertyTypeConstraint_ptidej(PropertyTypeConstraint *c);
extern void  choco_awake_PropertyTypeConstraint_ptidej(PropertyTypeConstraint *c);
extern void  choco_awakeOnRem_PropertyTypeConstraint(PropertyTypeConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_PropertyTypeConstraint(PropertyTypeConstraint *c,int idx,int v);
extern void  choco_doAwake_PropertyTypeConstraint(PropertyTypeConstraint *c);
extern PtidejBinConstraint * ptidej_makeListPropertyTypeConstraint_class(ClaireClass *cst,char *n,char *co,PtidejVar *v1,PtidejVar *v2,property *f);
extern void  claire_self_print_ListPropertyTypeConstraint_ptidej(ListPropertyTypeConstraint *c);
extern void  choco_awake_ListPropertyTypeConstraint_ptidej(ListPropertyTypeConstraint *c);
extern void  choco_awakeOnRem_ListPropertyTypeConstraint(ListPropertyTypeConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_ListPropertyTypeConstraint(ListPropertyTypeConstraint *c,int idx,int v);
extern void  choco_doAwake_ListPropertyTypeConstraint(ListPropertyTypeConstraint *c);
extern PropertyDistanceConstraint * ptidej_makePropertyDistanceConstraint_class(ClaireClass *cst,char *n,char *co,PtidejVar *v1,PtidejVar *v2,PalmIntVar *counter,property *f);
extern void  claire_self_print_PropertyDistanceConstraint_ptidej(PropertyDistanceConstraint *c);
extern void  choco_awake_PropertyDistanceConstraint_ptidej(PropertyDistanceConstraint *c);
extern void  palm_awakeOnRestoreInf_PropertyDistanceConstraint(PropertyDistanceConstraint *c,int idx);
extern void  choco_awakeOnRem_PropertyDistanceConstraint(PropertyDistanceConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreSup_PropertyDistanceConstraint(PropertyDistanceConstraint *c,int idx);
extern void  palm_awakeOnRestoreVal_PropertyDistanceConstraint(PropertyDistanceConstraint *c,int idx,int v);
extern void  choco_doAwake_PropertyDistanceConstraint(PropertyDistanceConstraint *c);
extern void  choco_propagate_PropertyDistanceConstraint(PropertyDistanceConstraint *c);
extern EqualConstraint * ptidej_makeEqualConstraint_string(char *na,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_EqualConstraint_ptidej(EqualConstraint *c);
extern void  choco_awake_EqualConstraint_ptidej(EqualConstraint *c);
extern void  choco_awakeOnRem_EqualConstraint(EqualConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_EqualConstraint(EqualConstraint *c,int idx,int v);
extern void  choco_doAwake_EqualConstraint(EqualConstraint *c);
extern NotEqualConstraint * ptidej_makeNotEqualConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_NotEqualConstraint_ptidej(NotEqualConstraint *c);
extern void  choco_awake_NotEqualConstraint_ptidej(NotEqualConstraint *c);
extern void  choco_awakeOnRem_NotEqualConstraint(NotEqualConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_NotEqualConstraint(NotEqualConstraint *c,int idx,int v);
extern void  choco_doAwake_NotEqualConstraint(NotEqualConstraint *c);
extern GreaterOrEqualPtidejConstraint * ptidej_makeGreaterOrEqualPtidejConstraint_string(char *n,char *co,PtidejBoundVar *v,int i);
extern void  claire_self_print_GreaterOrEqualPtidejConstraint_ptidej(GreaterOrEqualPtidejConstraint *c);
extern int  ptidej_getConstant_GreaterOrEqualPtidejConstraint(GreaterOrEqualPtidejConstraint *c);
extern LessOrEqualPtidejConstraint * ptidej_makeLessOrEqualPtidejConstraint_string(char *n,char *co,PtidejBoundVar *v,int i);
extern void  claire_self_print_LessOrEqualPtidejConstraint_ptidej(LessOrEqualPtidejConstraint *c);
extern int  ptidej_getConstant_LessOrEqualPtidejConstraint(LessOrEqualPtidejConstraint *c);
extern AggregationConstraint * ptidej_makeAggregationConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern AssociationConstraint * ptidej_makeAssociationConstraint_string(char *na,char *co,PtidejVar *v1,PtidejVar *v2);
extern CompositionConstraint * ptidej_makeCompositionConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern ContainerAggregationConstraint * ptidej_makeContainerAggregationConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern ContainerCompositionConstraint * ptidej_makeContainerCompositionConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern UseConstraint * ptidej_makeUseConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern IgnoranceConstraint * ptidej_makeIgnoranceConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern CreationConstraint * ptidej_makeCreationConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern AssociationDistanceConstraint * ptidej_makeAssociationDistanceConstraint_string(char *na,char *co,PtidejVar *v1,PtidejVar *v2,PalmIntVar *distance);
extern UseDistanceConstraint * ptidej_makeUseDistanceConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2,PalmIntVar *counter);
extern InheritanceTreeDepthConstraint * ptidej_makeInheritanceTreeDepthConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2,PalmIntVar *counter);
extern InheritancePathConstraint * ptidej_makeInheritancePathConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_InheritancePathConstraint_ptidej(InheritancePathConstraint *c);
extern void  choco_awake_InheritancePathConstraint_ptidej(InheritancePathConstraint *c);
extern void  choco_awakeOnRem_InheritancePathConstraint(InheritancePathConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_InheritancePathConstraint(InheritancePathConstraint *c,int idx,int v);
extern void  choco_doAwake_InheritancePathConstraint(InheritancePathConstraint *c);
extern StrictInheritancePathConstraint * ptidej_makeStrictInheritancePathConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_StrictInheritancePathConstraint_ptidej(StrictInheritancePathConstraint *c);
extern void  choco_awake_StrictInheritancePathConstraint_ptidej(StrictInheritancePathConstraint *c);
extern void  choco_awakeOnRem_StrictInheritancePathConstraint(StrictInheritancePathConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_StrictInheritancePathConstraint(StrictInheritancePathConstraint *c,int idx,int v);
extern void  choco_doAwake_StrictInheritancePathConstraint(StrictInheritancePathConstraint *c);
extern InheritanceConstraint * ptidej_makeInheritanceConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_InheritanceConstraint_ptidej(InheritanceConstraint *c);
extern void  choco_awake_InheritanceConstraint_ptidej(InheritanceConstraint *c);
extern void  choco_awakeOnRem_InheritanceConstraint(InheritanceConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_InheritanceConstraint(InheritanceConstraint *c,int idx,int v);
extern void  choco_doAwake_InheritanceConstraint(InheritanceConstraint *c);
extern StrictInheritanceConstraint * ptidej_makeStrictInheritanceConstraint_string(char *n,char *co,PtidejVar *v1,PtidejVar *v2);
extern void  claire_self_print_StrictInheritanceConstraint_ptidej(StrictInheritanceConstraint *c);
extern void  choco_awake_StrictInheritanceConstraint_ptidej(StrictInheritanceConstraint *c);
extern void  choco_awakeOnRem_StrictInheritanceConstraint(StrictInheritanceConstraint *c,int idx,int v);
extern void  palm_awakeOnRestoreVal_StrictInheritanceConstraint(StrictInheritanceConstraint *c,int idx,int v);
extern void  choco_doAwake_StrictInheritanceConstraint(StrictInheritanceConstraint *c);
extern OID  ptidej_selectBranchingObject_PtidejSaveAllSolutions(PtidejSaveAllSolutions *b);
extern tuple * palm_selectDecisionToUndo_PtidejSimpleAutomaticRepair_ptidej(PtidejSimpleAutomaticRepair *rep,Explanation *e);
extern tuple * palm_selectDecisionToUndo_PtidejSimpleAutomaticRepair_ptidej_(PtidejSimpleAutomaticRepair *g0121,Explanation *g0122);
extern void  ptidej_initSimpleAutomaticSolver_PtidejProblem(PtidejProblem *currentProblem);
extern void  ptidej_simpleAutomaticSolve_PtidejProblem(PtidejProblem *currentProblem);
extern char * ptidej_getSymbol_method(method *m);
extern tuple * palm_selectDecisionToUndo_PtidejSimpleInteractiveRepair_ptidej(PtidejSimpleInteractiveRepair *rep,Explanation *e);
extern tuple * palm_selectDecisionToUndo_PtidejSimpleInteractiveRepair_ptidej_(PtidejSimpleInteractiveRepair *g0136,Explanation *g0137);
extern void  ptidej_initSimpleInteractiveSolver_PtidejProblem(PtidejProblem *pb);
extern void  ptidej_simpleInteractiveSolve_PtidejProblem(PtidejProblem *pb);
extern tuple * palm_selectDecisionToUndo_PtidejAutomaticRepair_ptidej(PtidejAutomaticRepair *repairer,Explanation *e);
extern tuple * palm_selectDecisionToUndo_PtidejAutomaticRepair_ptidej_(PtidejAutomaticRepair *g0152,Explanation *g0153);
extern void  ptidej_initAutomaticSolver_PtidejProblem(PtidejProblem *pb);
extern void  ptidej_automaticSolve_PtidejProblem(PtidejProblem *pb);
extern tuple * palm_selectDecisionToUndo_PtidejInteractiveRepair_ptidej(PtidejInteractiveRepair *repairer,Explanation *e);
extern tuple * palm_selectDecisionToUndo_PtidejInteractiveRepair_ptidej_(PtidejInteractiveRepair *g0170,Explanation *g0171);
extern void  ptidej_initInteractiveSolver_PtidejProblem(PtidejProblem *currentProblem);
extern void  ptidej_interactiveSolve_PtidejProblem(PtidejProblem *pb);
extern BitString * ptidej_makeBitString_integer(int n);
extern void  claire_self_print_BitString_ptidej(BitString *bitString);
extern char * ptidej_getString_BitString(BitString *bitString);
extern int  ptidej_compose_array(OID *bits);
extern OID * ptidej_decompose_BitString(BitString *bitString);
extern void  ptidej_inc_BitString(BitString *bitString);
extern int  ptidej_factorial_integer(int n);
extern int  ptidej_getNumberOfOnes_BitString(BitString *bitString);
extern list * ptidej_getIndexesOfOnes_BitString(BitString *bitString);
extern list * ptidej_C_integer(int n,int p);
extern tuple * palm_selectDecisionToUndo_PtidejCombinatorialAutomaticRepair_ptidej(PtidejCombinatorialAutomaticRepair *repairer,Explanation *e);
extern tuple * palm_selectDecisionToUndo_PtidejCombinatorialAutomaticRepair_ptidej_(PtidejCombinatorialAutomaticRepair *g0191,Explanation *g0192);
extern void  ptidej_initCombinatorialAutomaticSolver_PtidejProblem(PtidejProblem *currentProblem);
extern void  ptidej_combinatorialAutomaticSolve_PtidejProblem(PtidejProblem *currentProblem);
extern void  ptidej_dynamicPost_PtidejProblem(PtidejProblem *problem,AbstractConstraint *constraint);
extern IntVar * ptidej_findNewVariable_PtidejProblem(PtidejProblem *problem,IntVar *variable);
extern void  ptidej_classicalPtidejProblem_void();
extern void  ptidej_inconsistentPtidejProblem_void();
extern void  ptidej_overConstrainedPtidejProblem_void();
extern PtidejProblem * ptidej_customProblemForAssociationDistanceTestPattern_void();
extern PtidejProblem * ptidej_customProblemForCompositionTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForCompositionTestPattern_void();
extern PtidejProblem * ptidej_customProblemForCreationTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForCreationTestPattern_void();
extern PtidejProblem * ptidej_customProblemForIgnoranceTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForIgnoranceTestPattern_void();
extern PtidejProblem * ptidej_customProblemForInheritancePathTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForInheritancePathTestPattern_void();
extern PtidejProblem * ptidej_customProblemForInheritanceTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForInheritanceTestPattern_void();
extern PtidejProblem * ptidej_customProblemForInheritanceTreeDepthTestPattern_void();
extern PtidejProblem * ptidej_customProblemForUseDistanceTestPattern_void();
extern PtidejProblem * ptidej_customProblemForUseTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForUseTestPattern_void();
extern PtidejProblem * ptidej_customProblemForStrictInheritanceTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForStrictInheritanceTestPattern_void();
extern PtidejProblem * ptidej_customProblemForAbstractFactoryPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForAbstractFactoryPattern_void();
extern PtidejProblem * ptidej_customProblemForChainOfResponsibilityPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForChainOfResponsibilityPattern_void();
extern PalmProblem * ptidej_customProblemForCompositePattern_void();
extern PalmProblem * ptidej_ac4ProblemForCompositePattern_void();
extern PtidejProblem * ptidej_customProblemForExtendedCompositePattern_void();
extern PtidejProblem * ptidej_ac4ProblemForExtendedCompositePattern_void();
extern PtidejProblem * ptidej_customProblemForFacadePattern_void();
extern PtidejProblem * ptidej_ac4ProblemForFacadePattern_void();
extern PtidejProblem * ptidej_customProblemForFactoryMethodPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForFactoryMethodPattern_void();
extern PtidejProblem * ptidej_customProblemForIteratorPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForIteratorPattern_void();
extern PtidejProblem * ptidej_customProblemForMediatorPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForMediatorPattern_void();
extern PtidejProblem * ptidej_customProblemForMementoPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForMementoPattern_void();
extern PtidejProblem * ptidej_customProblemForObserverPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForObserverPattern_void();
extern PtidejProblem * ptidej_customProblemForProxyPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForProxyPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForVisitorPattern_void();
extern PtidejProblem * ptidej_customProblemForVisitorPattern_void();
extern PtidejProblem * ptidej_customProblemForBadCompositionTestPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForBadCompositionTestPattern_void();
extern PtidejProblem * ptidej_customProblemForGoodInheritancePattern_void();
extern PtidejProblem * ptidej_ac4ProblemForGoodInheritancePattern_void();
extern PtidejProblem * ptidej_customProblemForRedundantTransitivityPattern_void();
extern PtidejProblem * ptidej_ac4ProblemForRedundantTransitivityPattern_void();

// namespace class for ptidej 
class ptidejClass: public NameSpace {
public:

ClaireClass * _Entity;
global_variable * peNil;
global_variable * minBoundValue;
global_variable * maxBoundValue;
global_variable * listOfEntities;
ClaireClass * _PtidejProblem;
ClaireClass * _PtidejBinConstraint;
ClaireClass * _PtidejLargeConstraint;
ClaireClass * _PtidejAC4Constraint;
ClaireClass * _PtidejVar;
ClaireClass * _PtidejBoundVar;
global_variable * PtidejResultFile;
ClaireClass * _GeneralPalmRepair;
ClaireClass * _PtidejAssignVar;
ClaireClass * _PtidejBranching;
ClaireClass * _PtidejLearn;
ClaireClass * _PtidejRepair;
ClaireClass * _PtidejSolver;
ClaireClass * _MinimumPtidejRepair;
global_variable * StatusCounter;
ClaireClass * _PtidejInteractiveBranching;
ClaireClass * _PtidejAutomaticBranching;
ClaireClass * _PropertyTypeConstraint;
ClaireClass * _ListPropertyTypeConstraint;
ClaireClass * _PropertyDistanceConstraint;
ClaireClass * _EqualConstraint;
ClaireClass * _NotEqualConstraint;
ClaireClass * _GreaterOrEqualPtidejConstraint;
ClaireClass * _LessOrEqualPtidejConstraint;
ClaireClass * _AggregationConstraint;
ClaireClass * _AssociationConstraint;
ClaireClass * _CompositionConstraint;
ClaireClass * _ContainerAggregationConstraint;
ClaireClass * _ContainerCompositionConstraint;
ClaireClass * _UseConstraint;
ClaireClass * _IgnoranceConstraint;
ClaireClass * _CreationConstraint;
ClaireClass * _AssociationDistanceConstraint;
ClaireClass * _UseDistanceConstraint;
ClaireClass * _InheritanceTreeDepthConstraint;
ClaireClass * _InheritancePathConstraint;
ClaireClass * _StrictInheritancePathConstraint;
ClaireClass * _InheritanceConstraint;
ClaireClass * _StrictInheritanceConstraint;
global_variable * SolutionNumber;
global_variable * MaximumNumberOfSolutions;
global_variable * SolutionMessage;
global_variable * Percentage;
global_variable * XCommand;
ClaireClass * _PtidejSaveAllSolutions;
ClaireClass * _PtidejSimpleAutomaticRepair;
ClaireClass * _PtidejSimpleInteractiveRepair;
ClaireClass * _PtidejAutomaticRepair;
ClaireClass * _PtidejInteractiveRepair;
ClaireClass * _BitString;
ClaireClass * _PtidejCombinatorialAutomaticRepair;
property * ptidejVersion;// ptidej/"ptidejVersion"
property * ptidejReleaseDate;// ptidej/"ptidejReleaseDate"
property * ptidejInfo;// ptidej/"ptidejInfo"
property * showPtidejLicense;// ptidej/"showPtidejLicense"
property * superEntities;// ptidej/"superEntities"
property * cachedReachableSuperEntities;// ptidej/"cachedReachableSuperEntities"
property * aggregatedEntities;// ptidej/"aggregatedEntities"
property * cachedReachableAggregatedEntities;// ptidej/"cachedReachableAggregatedEntities"
property * associatedEntities;// ptidej/"associatedEntities"
property * cachedReachableAssociatedEntities;// ptidej/"cachedReachableAssociatedEntities"
property * componentsType;// ptidej/"componentsType"
property * composedEntities;// ptidej/"composedEntities"
property * containerAggregatedEntities;// ptidej/"containerAggregatedEntities"
property * cachedReachableContainerAggregatedEntities;// ptidej/"cachedReachableContainerAggregatedEntities"
property * containerComponentsType;// ptidej/"containerComponentsType"
property * containerComposedEntities;// ptidej/"containerComposedEntities"
property * cachedReachableContainerComposedEntities;// ptidej/"cachedReachableContainerComposedEntities"
property * createdEntities;// ptidej/"createdEntities"
property * cachedReachableCreatedEntities;// ptidej/"cachedReachableCreatedEntities"
property * knownEntities;// ptidej/"knownEntities"
property * cachedReachableKnownEntities;// ptidej/"cachedReachableKnownEntities"
property * unknownEntities;// ptidej/"unknownEntities"
property * cachedReachableUnknownEntities;// ptidej/"cachedReachableUnknownEntities"
property * getCachedReachableRelationship;// ptidej/"getCachedReachableRelationship"
property * globalWeight;// ptidej/"globalWeight"
property * getGlobalWeight;// ptidej/"getGlobalWeight"
property * setGlobalWeight;// ptidej/"setGlobalWeight"
property * command;// ptidej/"command"
property * thisConstraint;// ptidej/"thisConstraint"
property * nextConstraint;// ptidej/"nextConstraint"
property * getName;// ptidej/"getName"
property * getXCommand;// ptidej/"getXCommand"
property * getThisConstraint;// ptidej/"getThisConstraint"
property * getNextConstraint;// ptidej/"getNextConstraint"
property * toBeEnumerated;// ptidej/"toBeEnumerated"
property * setVarsToShow;// ptidej/"setVarsToShow"
property * getRemovedConstraints;// ptidej/"getRemovedConstraints"
property * solutionNumber;// ptidej/"solutionNumber"
property * message;// ptidej/"message"
property * percentage;// ptidej/"percentage"
property * xCommand;// ptidej/"xCommand"
property * userRelaxedConstraints;// ptidej/"userRelaxedConstraints"
property * makePtidejProblem;// ptidej/"makePtidejProblem"
property * setRemovedConstraints;// ptidej/"setRemovedConstraints"
property * getProblemRelaxationLevel;// ptidej/"getProblemRelaxationLevel"
property * setProblem;// ptidej/"setProblem"
property * setSolutionNumber;// ptidej/"setSolutionNumber"
property * getSolutionNumber;// ptidej/"getSolutionNumber"
property * setMessage;// ptidej/"setMessage"
property * getMessage;// ptidej/"getMessage"
property * setPercentage;// ptidej/"setPercentage"
property * getPercentage;// ptidej/"getPercentage"
property * setXCommand;// ptidej/"setXCommand"
property * makePtidejVar;// ptidej/"makePtidejVar"
property * makePtidejBoundVar;// ptidej/"makePtidejBoundVar"
property * userAssignSelectVar;// ptidej/"userAssignSelectVar"
property * isBetterConstraint;// ptidej/"isBetterConstraint"
property * getExplanations;// ptidej/"getExplanations"
property * clearResultFile;// ptidej/"clearResultFile"
property * printOutSolution;// ptidej/"printOutSolution"
property * printOutTime;// ptidej/"printOutTime"
property * printHeader;// ptidej/"printHeader"
property * printFooter;// ptidej/"printFooter"
property * printStatus;// ptidej/"printStatus"
property * searchConcretePatterns;// ptidej/"searchConcretePatterns"
property * makePtidejAC4Constraint;// ptidej/"makePtidejAC4Constraint"
property * makeAggregationAC4Constraint;// ptidej/"makeAggregationAC4Constraint"
property * makeAssociationAC4Constraint;// ptidej/"makeAssociationAC4Constraint"
property * makeAwarenessAC4Constraint;// ptidej/"makeAwarenessAC4Constraint"
property * makeCompositionAC4Constraint;// ptidej/"makeCompositionAC4Constraint"
property * makeContainerAggregationAC4Constraint;// ptidej/"makeContainerAggregationAC4Constraint"
property * makeContainerCompositionAC4Constraint;// ptidej/"makeContainerCompositionAC4Constraint"
property * makeCreationAC4Constraint;// ptidej/"makeCreationAC4Constraint"
property * makeIgnoranceAC4Constraint;// ptidej/"makeIgnoranceAC4Constraint"
property * makeInheritanceAC4Constraint;// ptidej/"makeInheritanceAC4Constraint"
property * makeInheritancePathAC4Constraint;// ptidej/"makeInheritancePathAC4Constraint"
property * makeUseAC4Constraint;// ptidej/"makeUseAC4Constraint"
property * makeNoAssociationAC4Constraint;// ptidej/"makeNoAssociationAC4Constraint"
property * makeNoAggregationAC4Constraint;// ptidej/"makeNoAggregationAC4Constraint"
property * makeNoCompositionAC4Constraint;// ptidej/"makeNoCompositionAC4Constraint"
property * makeStrictInheritanceAC4Constraint;// ptidej/"makeStrictInheritanceAC4Constraint"
property * makeStrictInheritancePathAC4Constraint;// ptidej/"makeStrictInheritancePathAC4Constraint"
property * getAllReachableEntities;// ptidej/"getAllReachableEntities"
property * field;// ptidej/"field"
property * makePropertyTypeConstraint;// ptidej/"makePropertyTypeConstraint"
property * makeListPropertyTypeConstraint;// ptidej/"makeListPropertyTypeConstraint"
property * makePropertyDistanceConstraint;// ptidej/"makePropertyDistanceConstraint"
property * makeEqualConstraint;// ptidej/"makeEqualConstraint"
property * makeNotEqualConstraint;// ptidej/"makeNotEqualConstraint"
property * makeGreaterOrEqualPtidejConstraint;// ptidej/"makeGreaterOrEqualPtidejConstraint"
property * getConstant;// ptidej/"getConstant"
property * makeLessOrEqualPtidejConstraint;// ptidej/"makeLessOrEqualPtidejConstraint"
property * makeAggregationConstraint;// ptidej/"makeAggregationConstraint"
property * makeAssociationConstraint;// ptidej/"makeAssociationConstraint"
property * makeCompositionConstraint;// ptidej/"makeCompositionConstraint"
property * makeContainerAggregationConstraint;// ptidej/"makeContainerAggregationConstraint"
property * makeContainerCompositionConstraint;// ptidej/"makeContainerCompositionConstraint"
property * makeUseConstraint;// ptidej/"makeUseConstraint"
property * makeIgnoranceConstraint;// ptidej/"makeIgnoranceConstraint"
property * makeCreationConstraint;// ptidej/"makeCreationConstraint"
property * makeAssociationDistanceConstraint;// ptidej/"makeAssociationDistanceConstraint"
property * makeUseDistanceConstraint;// ptidej/"makeUseDistanceConstraint"
property * makeInheritanceTreeDepthConstraint;// ptidej/"makeInheritanceTreeDepthConstraint"
property * makeInheritancePathConstraint;// ptidej/"makeInheritancePathConstraint"
property * makeStrictInheritancePathConstraint;// ptidej/"makeStrictInheritancePathConstraint"
property * makeInheritanceConstraint;// ptidej/"makeInheritanceConstraint"
property * makeStrictInheritanceConstraint;// ptidej/"makeStrictInheritanceConstraint"
property * selectBranchingObject;// ptidej/"selectBranchingObject"
property * initSimpleAutomaticSolver;// ptidej/"initSimpleAutomaticSolver"
property * simpleAutomaticSolve;// ptidej/"simpleAutomaticSolve"
property * getSymbol;// ptidej/"getSymbol"
property * initSimpleInteractiveSolver;// ptidej/"initSimpleInteractiveSolver"
property * simpleInteractiveSolve;// ptidej/"simpleInteractiveSolve"
property * ptidejBranching;// ptidej/"ptidejBranching"
property * removableConstraints;// ptidej/"removableConstraints"
property * areAllConstraintsRelaxed;// ptidej/"areAllConstraintsRelaxed"
property * initAutomaticSolver;// ptidej/"initAutomaticSolver"
property * automaticSolve;// ptidej/"automaticSolve"
property * initInteractiveSolver;// ptidej/"initInteractiveSolver"
property * interactiveSolve;// ptidej/"interactiveSolve"
property * numberOfBits;// ptidej/"numberOfBits"
property * makeBitString;// ptidej/"makeBitString"
property * getString;// ptidej/"getString"
property * decompose;// ptidej/"decompose"
property * compose;// ptidej/"compose"
property * inc;// ptidej/"inc"
property * factorial;// ptidej/"factorial"
property * getNumberOfOnes;// ptidej/"getNumberOfOnes"
property * getIndexesOfOnes;// ptidej/"getIndexesOfOnes"
property * C;// ptidej/"C"
property * initCombinatorialAutomaticSolver;// ptidej/"initCombinatorialAutomaticSolver"
property * combinatorialAutomaticSolve;// ptidej/"combinatorialAutomaticSolve"
property * dynamicPost;// ptidej/"dynamicPost"
property * findNewVariable;// ptidej/"findNewVariable"
property * classicalPtidejProblem;// ptidej/"classicalPtidejProblem"
property * inconsistentPtidejProblem;// ptidej/"inconsistentPtidejProblem"
property * overConstrainedPtidejProblem;// ptidej/"overConstrainedPtidejProblem"
property * customProblemForAssociationDistanceTestPattern;// ptidej/"customProblemForAssociationDistanceTestPattern"
property * customProblemForCompositionTestPattern;// ptidej/"customProblemForCompositionTestPattern"
property * ac4ProblemForCompositionTestPattern;// ptidej/"ac4ProblemForCompositionTestPattern"
property * customProblemForCreationTestPattern;// ptidej/"customProblemForCreationTestPattern"
property * ac4ProblemForCreationTestPattern;// ptidej/"ac4ProblemForCreationTestPattern"
property * customProblemForIgnoranceTestPattern;// ptidej/"customProblemForIgnoranceTestPattern"
property * ac4ProblemForIgnoranceTestPattern;// ptidej/"ac4ProblemForIgnoranceTestPattern"
property * customProblemForInheritancePathTestPattern;// ptidej/"customProblemForInheritancePathTestPattern"
property * ac4ProblemForInheritancePathTestPattern;// ptidej/"ac4ProblemForInheritancePathTestPattern"
property * customProblemForInheritanceTestPattern;// ptidej/"customProblemForInheritanceTestPattern"
property * ac4ProblemForInheritanceTestPattern;// ptidej/"ac4ProblemForInheritanceTestPattern"
property * customProblemForInheritanceTreeDepthTestPattern;// ptidej/"customProblemForInheritanceTreeDepthTestPattern"
property * customProblemForUseDistanceTestPattern;// ptidej/"customProblemForUseDistanceTestPattern"
property * customProblemForUseTestPattern;// ptidej/"customProblemForUseTestPattern"
property * ac4ProblemForUseTestPattern;// ptidej/"ac4ProblemForUseTestPattern"
property * customProblemForStrictInheritanceTestPattern;// ptidej/"customProblemForStrictInheritanceTestPattern"
property * ac4ProblemForStrictInheritanceTestPattern;// ptidej/"ac4ProblemForStrictInheritanceTestPattern"
property * customProblemForAbstractFactoryPattern;// ptidej/"customProblemForAbstractFactoryPattern"
property * ac4ProblemForAbstractFactoryPattern;// ptidej/"ac4ProblemForAbstractFactoryPattern"
property * customProblemForChainOfResponsibilityPattern;// ptidej/"customProblemForChainOfResponsibilityPattern"
property * ac4ProblemForChainOfResponsibilityPattern;// ptidej/"ac4ProblemForChainOfResponsibilityPattern"
property * customProblemForCompositePattern;// ptidej/"customProblemForCompositePattern"
property * ac4ProblemForCompositePattern;// ptidej/"ac4ProblemForCompositePattern"
property * customProblemForExtendedCompositePattern;// ptidej/"customProblemForExtendedCompositePattern"
property * ac4ProblemForExtendedCompositePattern;// ptidej/"ac4ProblemForExtendedCompositePattern"
property * customProblemForFacadePattern;// ptidej/"customProblemForFacadePattern"
property * ac4ProblemForFacadePattern;// ptidej/"ac4ProblemForFacadePattern"
property * customProblemForFactoryMethodPattern;// ptidej/"customProblemForFactoryMethodPattern"
property * ac4ProblemForFactoryMethodPattern;// ptidej/"ac4ProblemForFactoryMethodPattern"
property * customProblemForIteratorPattern;// ptidej/"customProblemForIteratorPattern"
property * ac4ProblemForIteratorPattern;// ptidej/"ac4ProblemForIteratorPattern"
property * customProblemForMediatorPattern;// ptidej/"customProblemForMediatorPattern"
property * ac4ProblemForMediatorPattern;// ptidej/"ac4ProblemForMediatorPattern"
property * customProblemForMementoPattern;// ptidej/"customProblemForMementoPattern"
property * ac4ProblemForMementoPattern;// ptidej/"ac4ProblemForMementoPattern"
property * customProblemForObserverPattern;// ptidej/"customProblemForObserverPattern"
property * ac4ProblemForObserverPattern;// ptidej/"ac4ProblemForObserverPattern"
property * customProblemForProxyPattern;// ptidej/"customProblemForProxyPattern"
property * ac4ProblemForProxyPattern;// ptidej/"ac4ProblemForProxyPattern"
property * ac4ProblemForVisitorPattern;// ptidej/"ac4ProblemForVisitorPattern"
property * customProblemForVisitorPattern;// ptidej/"customProblemForVisitorPattern"
property * customProblemForBadCompositionTestPattern;// ptidej/"customProblemForBadCompositionTestPattern"
property * ac4ProblemForBadCompositionTestPattern;// ptidej/"ac4ProblemForBadCompositionTestPattern"
property * customProblemForGoodInheritancePattern;// ptidej/"customProblemForGoodInheritancePattern"
property * ac4ProblemForGoodInheritancePattern;// ptidej/"ac4ProblemForGoodInheritancePattern"
property * customProblemForRedundantTransitivityPattern;// ptidej/"customProblemForRedundantTransitivityPattern"
property * ac4ProblemForRedundantTransitivityPattern;// ptidej/"ac4ProblemForRedundantTransitivityPattern"

// module definition 
 void metaLoad();};

extern ptidejClass ptidej;

#endif
