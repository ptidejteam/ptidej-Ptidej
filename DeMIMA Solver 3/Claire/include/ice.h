// interface defination for module ice, Thu Feb 13 21:08:22 2003
#ifndef CLAIREH_ice
#define CLAIREH_ice


class AbstractWCSP;
class AdditiveWCSP;
class IntCollection;
class IntStack;
class IntQueue;
class AbstractBipartiteGraph;
class AbstractBipartiteMatching;
class BalancedBipartiteMatching;
class AbstractBipartiteFlow;
class CompleteAllDiff;
class Permutation;
class GlobalCardinality;

class AbstractWCSP: public LargeIntConstraint{ 
  public:
     OID *penalties;
     OID *subConst;
     int nbSubConst;
     int nbSubVars;
     list *constIndices;
     list *varIndices;} 
;

class AdditiveWCSP: public AbstractWCSP{ 
  public:
     OID *ic;
     OID *domainOffsets;
     OID *reactedToInst;
     OID *nbUnInstantiatedVars;
     OID *leastICValue;
     OID *varPenalties;
     int backwardCheckingBound;
     int forwardCheckingBound;} 
;

class IntCollection: public Ephemeral{ 
  public:
     int maxsize;
     int nbElts;} 
;

class IntStack: public IntCollection{ 
  public:
     list *contents;} 
;

class IntQueue: public IntCollection{ 
  public:
     int last;
     list *contents;
     list *onceinqueue;} 
;

class AbstractBipartiteGraph: public LargeIntConstraint{ 
  public:
     int nbLeftVertices;
     int nbRightVertices;
     int minValue;
     int maxValue;
     int source;
     int nbVertices;
     OID *refMatch;
     int matchingSize;
     OID *left2rightArc;
     OID *right2leftArc;
     IntQueue *queue;
     int time;
     OID *finishDate;
     OID *seen;
     int currentNode;
     int currentComponent;
     OID *component;
     BoolMatrix2D *componentOrder;} 
;

class AbstractBipartiteMatching: public AbstractBipartiteGraph{ 
  public:
     OID *refInverseMatch;} 
;

class BalancedBipartiteMatching: public AbstractBipartiteMatching{ 
  public:} 
;

class AbstractBipartiteFlow: public AbstractBipartiteGraph{ 
  public:
     OID *minFlow;
     OID *maxFlow;
     OID *flow;
     ClaireBoolean *compatibleFlow;} 
;

class CompleteAllDiff: public AbstractBipartiteMatching{ 
  public:} 
;

class Permutation: public BalancedBipartiteMatching{ 
  public:} 
;

class GlobalCardinality: public AbstractBipartiteFlow{ 
  public:} 
;
extern IntVar * ice_getTotalPenaltyVar_AbstractWCSP(AbstractWCSP *c);
extern AbstractConstraint * ice_getSubConst_AbstractWCSP(AbstractWCSP *c,int subcIdx);
extern int  ice_getSubConstPenalty_AbstractWCSP(AbstractWCSP *c,int subcIdx);
extern IntVar * ice_getSubVar_AbstractWCSP(AbstractWCSP *c,int varIdx);
extern int  ice_getNbSubVars_AbstractWCSP(AbstractWCSP *c);
extern int  ice_getNbSubConst_AbstractWCSP(AbstractWCSP *c);
extern int  ice_getNbSubConstLinkedTo_AbstractWCSP(AbstractWCSP *c,int varIdx);
extern int  ice_getSubConstIndex_AbstractWCSP(AbstractWCSP *c,int varIdx,int i);
extern int  ice_getIndex_AbstractWCSP(AbstractWCSP *c,IntVar *var);
extern void  ice_addVar_AbstractWCSP(AbstractWCSP *c,IntVar *var);
extern int  ice_getUnInstantiatedVarIdx_AbstractWCSP(AbstractWCSP *c,int subcIdx);
extern void  ice_initWCSPNetwork_AbstractWCSP(AbstractWCSP *c,list *lc);
extern OID  ice_display_AdditiveWCSP(AdditiveWCSP *c);
extern AdditiveWCSP * choco_additiveWCSP_list(list *lc,list *lp,IntVar *X);
extern int  ice_getNbUnInstantiatedVars_AdditiveWCSP(AdditiveWCSP *c,int subcIdx);
extern int  ice_getInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val);
extern void  ice_setInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val,int x);
extern ClaireBoolean * ice_increaseInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val,int delta);
extern void  ice_makeInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c);
extern void  choco_awakeOnInst_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern ClaireBoolean * ice_updateIC_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int subcIdx);
extern ClaireBoolean * ice_recomputeVarPenalty_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern int  ice_getVarPenalty_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern void  choco_awakeOnRem_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int oldval);
extern void  choco_awakeOnVar_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern void  choco_awakeOnInf_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern void  choco_awakeOnSup_AdditiveWCSP(AdditiveWCSP *c,int varIdx);
extern int  choco_getPriority_AdditiveWCSP_ice(AdditiveWCSP *c);
extern void  choco_propagate_AdditiveWCSP(AdditiveWCSP *c);
extern void  choco_awake_AdditiveWCSP_ice(AdditiveWCSP *c);
extern ClaireBoolean * choco_testIfSatisfied_AdditiveWCSP(AdditiveWCSP *c);
extern OID  claire_showIglooLicense_void();
extern int  ice_getSize_IntCollection(IntCollection *ic);
extern IntStack * claire_close_IntStack(IntStack *s);
extern OID  claire_set_I_IntStack(IntStack *s);
extern void  ice_clear_IntStack(IntStack *s);
extern int  ice_pop_IntStack(IntStack *s);
extern void  ice_push_IntStack(IntStack *s,int x);
extern IntQueue * claire_close_IntQueue(IntQueue *q);
extern void  ice_push_IntQueue(IntQueue *q,int x);
extern int  ice_pop_IntQueue(IntQueue *q);
extern void  ice_init_IntQueue(IntQueue *q);
extern void  ice_showRefAssignment_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern void  ice_showSCCDecomposition_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern list * ice_mayMatch_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i);
extern list * ice_mayInverseMatch_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j);
extern int  ice_match_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i);
extern void  choco_checkFlow_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern ClaireBoolean * ice_mayGrowFlowToSink_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i);
extern ClaireBoolean * ice_mayGrowFlowBetween_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j,int i);
extern ClaireBoolean * ice_mayDiminishFlowBetween_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j,int i);
extern int  ice_inverseMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern void  ice_increaseMatchingSize_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern void  ice_decreaseMatchingSize_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern void  ice_deleteMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j);
extern void  ice_putRefMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j);
extern void  ice_setMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j);
extern ClaireBoolean * ice_mayDiminishFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern ClaireBoolean * ice_mayGrowFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern ClaireBoolean * ice_mustGrowFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j);
extern void  ice_setMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j);
extern void  ice_deleteMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j);
extern void  ice_putRefMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j);
extern ClaireBoolean * ice_mayDiminishFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j);
extern ClaireBoolean * ice_mayGrowFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j);
extern ClaireBoolean * ice_mustGrowFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j);
extern void  ice_increaseMatchingSize_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j);
extern void  ice_decreaseMatchingSize_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j);
extern int  ice_findAlternatingPath_AbstractBipartiteMatching(AbstractBipartiteMatching *c);
extern void  choco_augment_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int x);
extern void  ice_augmentFlow_AbstractBipartiteMatching_ice(AbstractBipartiteMatching *c);
extern int  ice_findAlternatingPath_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  choco_augment_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int x);
extern void  ice_augmentFlow_AbstractBipartiteFlow_ice(AbstractBipartiteFlow *c);
extern void  ice_turnIntoCompatibleFlow_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  ice_circulate_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int y);
extern int  ice_findAlternatingCycle_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  ice_initSCCGraph_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern void  ice_addComponentVertex_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern void  ice_addComponentEdge_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int compi,int compj);
extern void  ice_firstPassDFS_AbstractBipartiteMatching(AbstractBipartiteMatching *c);
extern void  ice_firstDFSearch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i);
extern void  ice_secondPassDFS_AbstractBipartiteMatching(AbstractBipartiteMatching *c);
extern void  ice_secondDFSearch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i);
extern void  ice_firstPassDFS_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  ice_firstDFSearch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i);
extern void  ice_secondPassDFS_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  ice_secondDFSearch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i);
extern void  ice_removeUselessEdges_AbstractBipartiteMatching(AbstractBipartiteMatching *c);
extern void  ice_removeUselessEdges_AbstractBipartiteFlow(AbstractBipartiteFlow *c);
extern void  choco_propagate_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern int  choco_getPriority_AbstractBipartiteGraph_ice(AbstractBipartiteGraph *c);
extern void  choco_closeAssignmentConstraint_AbstractBipartiteGraph(AbstractBipartiteGraph *c);
extern OID  claire_self_print_CompleteAllDiff_ice(CompleteAllDiff *c);
extern CompleteAllDiff * choco_completeAllDiff_list(list *l1);
extern void  ice_setEdgeAndPublish_CompleteAllDiff(CompleteAllDiff *c,int i,int j);
extern void  choco_deleteEdgeAndPublish_CompleteAllDiff(CompleteAllDiff *c,int i,int j);
extern void  choco_awakeOnRem_CompleteAllDiff(CompleteAllDiff *c,int idx,int val);
extern void  choco_awakeOnVar_CompleteAllDiff(CompleteAllDiff *c,int idx);
extern void  choco_awakeOnInf_CompleteAllDiff(CompleteAllDiff *c,int idx);
extern void  choco_awakeOnSup_CompleteAllDiff(CompleteAllDiff *c,int idx);
extern void  choco_awakeOnInst_CompleteAllDiff(CompleteAllDiff *c,int idx);
extern void  choco_awake_CompleteAllDiff_ice(CompleteAllDiff *c);
extern OID  claire_self_print_Permutation_ice(Permutation *c);
extern void  ice_setEdgeAndPublish_Permutation(Permutation *c,int i,int j,ClaireBoolean *fromLeft);
extern void  choco_deleteEdgeAndPublish_Permutation1(Permutation *c,int i,int j,ClaireBoolean *fromLeft);
extern void  choco_deleteEdgeAndPublish_Permutation2(Permutation *c,int i,int j);
extern void  choco_awakeOnRem_Permutation(Permutation *c,int idx,int val);
extern void  choco_awakeOnVar_Permutation(Permutation *c,int idx);
extern void  choco_awakeOnInf_Permutation(Permutation *c,int idx);
extern void  choco_awakeOnSup_Permutation(Permutation *c,int idx);
extern void  choco_awakeOnInst_Permutation(Permutation *c,int idx);
extern void  choco_awake_Permutation_ice(Permutation *c);
extern Permutation * choco_permutation_list(list *l1,list *l2);
extern OID  claire_self_print_GlobalCardinality_ice(GlobalCardinality *c);
extern void  choco_deleteEdgeAndPublish_GlobalCardinality(GlobalCardinality *c,int i,int j);
extern void  ice_setEdgeAndPublish_GlobalCardinality(GlobalCardinality *c,int i,int j);
extern void  choco_awakeOnRem_GlobalCardinality(GlobalCardinality *c,int idx,int val);
extern void  choco_awakeOnVar_GlobalCardinality(GlobalCardinality *c,int idx);
extern void  choco_awakeOnInf_GlobalCardinality(GlobalCardinality *c,int idx);
extern void  choco_awakeOnSup_GlobalCardinality(GlobalCardinality *c,int idx);
extern void  choco_awakeOnInst_GlobalCardinality(GlobalCardinality *c,int idx);
extern void  choco_awake_GlobalCardinality_ice(GlobalCardinality *c);
extern GlobalCardinality * choco_gcc_list1(list *l1,list *l2);
extern GlobalCardinality * choco_gcc_list2(list *l1,int val1,int val2,list *l2);
extern void  ice_checkComponentOrder_AbstractBipartiteMatching(AbstractBipartiteMatching *c);

// namespace class for ice 
class iceClass: public NameSpace {
public:

global_variable * WVIEW;
global_variable * WTALK;
global_variable * WDEBUG;
ClaireClass * _AbstractWCSP;
ClaireClass * _AdditiveWCSP;
global_variable * ICE_RELEASE;
global_variable * MVIEW;
global_variable * MTALK;
global_variable * MDEBUG;
global_variable * SCCVIEW;
global_variable * SCCTALK;
global_variable * ICETEST_VIEW;
global_variable * ICEBENCH_VIEW;
ClaireClass * _IntCollection;
ClaireClass * _IntStack;
ClaireClass * _IntQueue;
ClaireClass * _AbstractBipartiteGraph;
global_variable * CHK;
ClaireClass * _AbstractBipartiteMatching;
ClaireClass * _BalancedBipartiteMatching;
ClaireClass * _AbstractBipartiteFlow;
global_variable * DBC;
property * deleteEdgeAndPublish;
ClaireClass * _CompleteAllDiff;
ClaireClass * _Permutation;
ClaireClass * _GlobalCardinality;
property * penalties;// ice/"penalties"
property * subConst;// ice/"subConst"
property * nbSubConst;// ice/"nbSubConst"
property * nbSubVars;// ice/"nbSubVars"
property * constIndices;// ice/"constIndices"
property * varIndices;// ice/"varIndices"
property * getTotalPenaltyVar;// ice/"getTotalPenaltyVar"
property * getSubConst;// ice/"getSubConst"
property * getSubConstPenalty;// ice/"getSubConstPenalty"
property * getSubVar;// ice/"getSubVar"
property * getNbSubVars;// ice/"getNbSubVars"
property * getNbSubConst;// ice/"getNbSubConst"
property * getNbSubConstLinkedTo;// ice/"getNbSubConstLinkedTo"
property * getSubConstIndex;// ice/"getSubConstIndex"
property * getIndex;// ice/"getIndex"
property * addVar;// ice/"addVar"
property * getUnInstantiatedVarIdx;// ice/"getUnInstantiatedVarIdx"
property * reactedToInst;// ice/"reactedToInst"
property * initWCSPNetwork;// ice/"initWCSPNetwork"
property * ic;// ice/"ic"
property * domainOffsets;// ice/"domainOffsets"
property * nbUnInstantiatedVars;// ice/"nbUnInstantiatedVars"
property * leastICValue;// ice/"leastICValue"
property * varPenalties;// ice/"varPenalties"
property * backwardCheckingBound;// ice/"backwardCheckingBound"
property * forwardCheckingBound;// ice/"forwardCheckingBound"
property * display;// ice/"display"
property * getInconsistencyCount;// ice/"getInconsistencyCount"
property * additiveWCSP;// choco/"additiveWCSP"
property * makeInconsistencyCount;// ice/"makeInconsistencyCount"
property * getNbUnInstantiatedVars;// ice/"getNbUnInstantiatedVars"
property * setInconsistencyCount;// ice/"setInconsistencyCount"
property * increaseInconsistencyCount;// ice/"increaseInconsistencyCount"
property * recomputeVarPenalty;// ice/"recomputeVarPenalty"
property * updateIC;// ice/"updateIC"
property * getVarPenalty;// ice/"getVarPenalty"
property * showIglooLicense;// claire/"showIglooLicense"
property * maxsize;// ice/"maxsize"
property * nbElts;// ice/"nbElts"
property * getSize;// ice/"getSize"
property * clear;// ice/"clear"
property * pop;// ice/"pop"
property * push;// ice/"push"
property * onceinqueue;// ice/"onceinqueue"
property * init;// ice/"init"
property * nbLeftVertices;// ice/"nbLeftVertices"
property * nbRightVertices;// ice/"nbRightVertices"
property * maxValue;// ice/"maxValue"
property * nbVertices;// ice/"nbVertices"
property * refMatch;// ice/"refMatch"
property * matchingSize;// ice/"matchingSize"
property * left2rightArc;// ice/"left2rightArc"
property * right2leftArc;// ice/"right2leftArc"
property * queue;// ice/"queue"
property * finishDate;// ice/"finishDate"
property * seen;// ice/"seen"
property * currentNode;// ice/"currentNode"
property * currentComponent;// ice/"currentComponent"
property * component;// ice/"component"
property * componentOrder;// ice/"componentOrder"
property * showRefAssignment;// ice/"showRefAssignment"
property * showSCCDecomposition;// ice/"showSCCDecomposition"
property * mayMatch;// ice/"mayMatch"
property * mayInverseMatch;// ice/"mayInverseMatch"
property * match;// ice/"match"
property * checkFlow;// choco/"checkFlow"
property * mayGrowFlowToSink;// ice/"mayGrowFlowToSink"
property * mayGrowFlowBetween;// ice/"mayGrowFlowBetween"
property * mayDiminishFlowBetween;// ice/"mayDiminishFlowBetween"
property * refInverseMatch;// ice/"refInverseMatch"
property * inverseMatch;// ice/"inverseMatch"
property * increaseMatchingSize;// ice/"increaseMatchingSize"
property * decreaseMatchingSize;// ice/"decreaseMatchingSize"
property * deleteMatch;// ice/"deleteMatch"
property * putRefMatch;// ice/"putRefMatch"
property * setMatch;// ice/"setMatch"
property * mayDiminishFlowFromSource;// ice/"mayDiminishFlowFromSource"
property * mayGrowFlowFromSource;// ice/"mayGrowFlowFromSource"
property * mustGrowFlowFromSource;// ice/"mustGrowFlowFromSource"
property * minFlow;// ice/"minFlow"
property * maxFlow;// ice/"maxFlow"
property * flow;// ice/"flow"
property * compatibleFlow;// ice/"compatibleFlow"
property * findAlternatingPath;// ice/"findAlternatingPath"
property * augment;// choco/"augment"
property * augmentFlow;// ice/"augmentFlow"
property * turnIntoCompatibleFlow;// ice/"turnIntoCompatibleFlow"
property * findAlternatingCycle;// ice/"findAlternatingCycle"
property * circulate;// ice/"circulate"
property * mayDimishFlowFromSource;// ice/"mayDimishFlowFromSource"
property * initSCCGraph;// ice/"initSCCGraph"
property * addComponentVertex;// ice/"addComponentVertex"
property * addComponentEdge;// ice/"addComponentEdge"
property * firstPassDFS;// ice/"firstPassDFS"
property * firstDFSearch;// ice/"firstDFSearch"
property * secondPassDFS;// ice/"secondPassDFS"
property * secondDFSearch;// ice/"secondDFSearch"
property * removeUselessEdges;// ice/"removeUselessEdges"
property * closeAssignmentConstraint;// choco/"closeAssignmentConstraint"
property * completeAllDiff;// choco/"completeAllDiff"
property * setEdgeAndPublish;// ice/"setEdgeAndPublish"
property * permutation;// choco/"permutation"
property * gcc;// choco/"gcc"
property * checkComponentOrder;// ice/"checkComponentOrder"

// module definition 
 void metaLoad();};

extern iceClass ice;

#endif
