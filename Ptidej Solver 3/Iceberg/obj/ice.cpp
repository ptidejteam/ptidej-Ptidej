/***** CLAIRE Compilation of file ice.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:08:23 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>
#include <ice.h>


iceClass ice;

// definition of the meta-model for ice 

void iceClass::metaLoad() { 
  GC_BIND;
  ClEnv->module_I = it;
// definition of the properties 
  ice.penalties = property::make("penalties",ice.it);
  ice.subConst = property::make("subConst",ice.it);
  ice.nbSubConst = property::make("nbSubConst",ice.it);
  ice.nbSubVars = property::make("nbSubVars",ice.it);
  ice.constIndices = property::make("constIndices",ice.it);
  ice.varIndices = property::make("varIndices",ice.it);
  ice.getTotalPenaltyVar = property::make("getTotalPenaltyVar",ice.it);
  ice.getSubConst = property::make("getSubConst",ice.it);
  ice.getSubConstPenalty = property::make("getSubConstPenalty",ice.it);
  ice.getSubVar = property::make("getSubVar",ice.it);
  ice.getNbSubVars = property::make("getNbSubVars",ice.it);
  ice.getNbSubConst = property::make("getNbSubConst",ice.it);
  ice.getNbSubConstLinkedTo = property::make("getNbSubConstLinkedTo",ice.it);
  ice.getSubConstIndex = property::make("getSubConstIndex",ice.it);
  ice.getIndex = property::make("getIndex",ice.it);
  ice.addVar = property::make("addVar",ice.it);
  ice.getUnInstantiatedVarIdx = property::make("getUnInstantiatedVarIdx",ice.it);
  ice.reactedToInst = property::make("reactedToInst",ice.it);
  ice.initWCSPNetwork = property::make("initWCSPNetwork",ice.it);
  ice.ic = property::make("ic",ice.it);
  ice.domainOffsets = property::make("domainOffsets",ice.it);
  ice.nbUnInstantiatedVars = property::make("nbUnInstantiatedVars",ice.it);
  ice.leastICValue = property::make("leastICValue",ice.it);
  ice.varPenalties = property::make("varPenalties",ice.it);
  ice.backwardCheckingBound = property::make("backwardCheckingBound",ice.it);
  ice.forwardCheckingBound = property::make("forwardCheckingBound",ice.it);
  ice.display = property::make("display",ice.it);
  ice.getInconsistencyCount = property::make("getInconsistencyCount",ice.it);
  ice.additiveWCSP = property::make("additiveWCSP",choco.it);
  ice.makeInconsistencyCount = property::make("makeInconsistencyCount",ice.it);
  ice.getNbUnInstantiatedVars = property::make("getNbUnInstantiatedVars",ice.it);
  ice.setInconsistencyCount = property::make("setInconsistencyCount",ice.it);
  ice.increaseInconsistencyCount = property::make("increaseInconsistencyCount",ice.it);
  ice.recomputeVarPenalty = property::make("recomputeVarPenalty",ice.it);
  ice.updateIC = property::make("updateIC",ice.it);
  ice.getVarPenalty = property::make("getVarPenalty",ice.it);
  ice.showIglooLicense = property::make("showIglooLicense",claire.it);
  ice.maxsize = property::make("maxsize",0,ice.it);
  ice.nbElts = property::make("nbElts",ice.it);
  ice.getSize = property::make("getSize",ice.it);
  ice.clear = property::make("clear",ice.it);
  ice.pop = property::make("pop",ice.it);
  ice.push = property::make("push",ice.it);
  ice.onceinqueue = property::make("onceinqueue",ice.it);
  ice.init = property::make("init",ice.it);
  ice.nbLeftVertices = property::make("nbLeftVertices",ice.it);
  ice.nbRightVertices = property::make("nbRightVertices",ice.it);
  ice.maxValue = property::make("maxValue",ice.it);
  ice.nbVertices = property::make("nbVertices",ice.it);
  ice.refMatch = property::make("refMatch",ice.it);
  ice.matchingSize = property::make("matchingSize",ice.it);
  ice.left2rightArc = property::make("left2rightArc",ice.it);
  ice.right2leftArc = property::make("right2leftArc",ice.it);
  ice.queue = property::make("queue",ice.it);
  ice.finishDate = property::make("finishDate",ice.it);
  ice.seen = property::make("seen",ice.it);
  ice.currentNode = property::make("currentNode",2,ice.it);
  ice.currentComponent = property::make("currentComponent",ice.it);
  ice.component = property::make("component",ice.it);
  ice.componentOrder = property::make("componentOrder",ice.it);
  ice.showRefAssignment = property::make("showRefAssignment",ice.it);
  ice.showSCCDecomposition = property::make("showSCCDecomposition",ice.it);
  ice.mayMatch = property::make("mayMatch",ice.it);
  ice.mayInverseMatch = property::make("mayInverseMatch",ice.it);
  ice.match = property::make("match",ice.it);
  ice.checkFlow = property::make("checkFlow",choco.it);
  ice.mayGrowFlowToSink = property::make("mayGrowFlowToSink",ice.it);
  ice.mayGrowFlowBetween = property::make("mayGrowFlowBetween",ice.it);
  ice.mayDiminishFlowBetween = property::make("mayDiminishFlowBetween",ice.it);
  ice.refInverseMatch = property::make("refInverseMatch",ice.it);
  ice.inverseMatch = property::make("inverseMatch",ice.it);
  ice.increaseMatchingSize = property::make("increaseMatchingSize",ice.it);
  ice.decreaseMatchingSize = property::make("decreaseMatchingSize",ice.it);
  ice.deleteMatch = property::make("deleteMatch",ice.it);
  ice.putRefMatch = property::make("putRefMatch",ice.it);
  ice.setMatch = property::make("setMatch",ice.it);
  ice.mayDiminishFlowFromSource = property::make("mayDiminishFlowFromSource",ice.it);
  ice.mayGrowFlowFromSource = property::make("mayGrowFlowFromSource",ice.it);
  ice.mustGrowFlowFromSource = property::make("mustGrowFlowFromSource",ice.it);
  ice.minFlow = property::make("minFlow",ice.it);
  ice.maxFlow = property::make("maxFlow",ice.it);
  ice.flow = property::make("flow",ice.it);
  ice.compatibleFlow = property::make("compatibleFlow",ice.it);
  ice.findAlternatingPath = property::make("findAlternatingPath",ice.it);
  ice.augment = property::make("augment",choco.it);
  ice.augmentFlow = property::make("augmentFlow",3,ice.it);
  ice.turnIntoCompatibleFlow = property::make("turnIntoCompatibleFlow",ice.it);
  ice.findAlternatingCycle = property::make("findAlternatingCycle",ice.it);
  ice.circulate = property::make("circulate",ice.it);
  ice.mayDimishFlowFromSource = property::make("mayDimishFlowFromSource",ice.it);
  ice.initSCCGraph = property::make("initSCCGraph",ice.it);
  ice.addComponentVertex = property::make("addComponentVertex",ice.it);
  ice.addComponentEdge = property::make("addComponentEdge",ice.it);
  ice.firstPassDFS = property::make("firstPassDFS",ice.it);
  ice.firstDFSearch = property::make("firstDFSearch",ice.it);
  ice.secondPassDFS = property::make("secondPassDFS",ice.it);
  ice.secondDFSearch = property::make("secondDFSearch",ice.it);
  ice.removeUselessEdges = property::make("removeUselessEdges",ice.it);
  ice.closeAssignmentConstraint = property::make("closeAssignmentConstraint",choco.it);
  ice.completeAllDiff = property::make("completeAllDiff",choco.it);
  ice.setEdgeAndPublish = property::make("setEdgeAndPublish",ice.it);
  ice.permutation = property::make("permutation",choco.it);
  ice.gcc = property::make("gcc",choco.it);
  ice.checkComponentOrder = property::make("checkComponentOrder",ice.it);
  
  // instructions from module sources
  { global_variable * _CL_obj = (ice.WVIEW = (global_variable *) Core._global_variable->instantiate("WVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.WTALK = (global_variable *) Core._global_variable->instantiate("WTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.WDEBUG = (global_variable *) Core._global_variable->instantiate("WDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { (ice._AbstractWCSP = ClaireClass::make("AbstractWCSP",choco._LargeIntConstraint,ice.it));
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.penalties,penalties,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.subConst,subConst,nth_type(choco._AbstractConstraint),CNULL);
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.nbSubConst,nbSubConst,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.nbSubVars,nbSubVars,Kernel._integer,0);
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.constIndices,constIndices,nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer))),CNULL);
    CL_ADD_SLOT(ice._AbstractWCSP,AbstractWCSP,ice.varIndices,varIndices,nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer))),CNULL);
    } 
  
  ice.getTotalPenaltyVar->addMethod(list::domain(1,ice._AbstractWCSP),choco._IntVar,
  	RETURN_ARG,_function_(ice_getTotalPenaltyVar_AbstractWCSP,"ice_getTotalPenaltyVar_AbstractWCSP"));
  
  ice.getSubConst->addMethod(list::domain(2,ice._AbstractWCSP,Kernel._integer),choco._AbstractConstraint,
  	RETURN_ARG,_function_(ice_getSubConst_AbstractWCSP,"ice_getSubConst_AbstractWCSP"));
  
  ice.getSubConstPenalty->addMethod(list::domain(2,ice._AbstractWCSP,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_getSubConstPenalty_AbstractWCSP,"ice_getSubConstPenalty_AbstractWCSP"));
  
  ice.getSubVar->addMethod(list::domain(2,ice._AbstractWCSP,Kernel._integer),choco._IntVar,
  	RETURN_ARG,_function_(ice_getSubVar_AbstractWCSP,"ice_getSubVar_AbstractWCSP"));
  
  ice.getNbSubVars->addMethod(list::domain(1,ice._AbstractWCSP),Kernel._integer,
  	0,_function_(ice_getNbSubVars_AbstractWCSP,"ice_getNbSubVars_AbstractWCSP"));
  
  ice.getNbSubConst->addMethod(list::domain(1,ice._AbstractWCSP),Kernel._integer,
  	0,_function_(ice_getNbSubConst_AbstractWCSP,"ice_getNbSubConst_AbstractWCSP"));
  
  ice.getNbSubConstLinkedTo->addMethod(list::domain(2,ice._AbstractWCSP,Kernel._integer),Kernel._integer,
  	0,_function_(ice_getNbSubConstLinkedTo_AbstractWCSP,"ice_getNbSubConstLinkedTo_AbstractWCSP"));
  
  ice.getSubConstIndex->addMethod(list::domain(3,ice._AbstractWCSP,Kernel._integer,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_getSubConstIndex_AbstractWCSP,"ice_getSubConstIndex_AbstractWCSP"));
  
  ice.getIndex->addMethod(list::domain(2,ice._AbstractWCSP,choco._IntVar),Kernel._integer,
  	0,_function_(ice_getIndex_AbstractWCSP,"ice_getIndex_AbstractWCSP"));
  
  ice.addVar->addMethod(list::domain(2,ice._AbstractWCSP,choco._IntVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(ice_addVar_AbstractWCSP,"ice_addVar_AbstractWCSP"));
  
  ice.getUnInstantiatedVarIdx->addMethod(list::domain(2,ice._AbstractWCSP,Kernel._integer),Kernel._integer,
  	0,_function_(ice_getUnInstantiatedVarIdx_AbstractWCSP,"ice_getUnInstantiatedVarIdx_AbstractWCSP"));
  
  ice.initWCSPNetwork->addMethod(list::domain(2,ice._AbstractWCSP,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_initWCSPNetwork_AbstractWCSP,"ice_initWCSPNetwork_AbstractWCSP"));
  
  { (ice._AdditiveWCSP = ClaireClass::make("AdditiveWCSP",ice._AbstractWCSP,ice.it));
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.ic,ic,nth_type(GC_OBJECT(ClaireType,nth_type(Kernel._integer))),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.domainOffsets,domainOffsets,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.reactedToInst,reactedToInst,nth_type(Kernel._boolean),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.nbUnInstantiatedVars,nbUnInstantiatedVars,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.leastICValue,leastICValue,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.varPenalties,varPenalties,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.backwardCheckingBound,backwardCheckingBound,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AdditiveWCSP,AdditiveWCSP,ice.forwardCheckingBound,forwardCheckingBound,Kernel._integer,CNULL);
    } 
  
  (*Kernel.store)(_oid_(ice.backwardCheckingBound),
    _oid_(ice.forwardCheckingBound));
  
  ice.display->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(ice_display_AdditiveWCSP,"ice_display_AdditiveWCSP"));
  
  ice.additiveWCSP->addMethod(list::domain(3,nth_class1(Kernel._list,choco._AbstractConstraint),nth_class1(Kernel._list,Kernel._integer),choco._IntVar),ice._AdditiveWCSP,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_additiveWCSP_list,"choco_additiveWCSP_list"));
  
  ice.getNbUnInstantiatedVars->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_getNbUnInstantiatedVars_AdditiveWCSP,"ice_getNbUnInstantiatedVars_AdditiveWCSP"));
  
  ice.getInconsistencyCount->addMethod(list::domain(3,ice._AdditiveWCSP,Kernel._integer,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_getInconsistencyCount_AdditiveWCSP,"ice_getInconsistencyCount_AdditiveWCSP"));
  
  ice.setInconsistencyCount->addMethod(list::domain(4,ice._AdditiveWCSP,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	RETURN_ARG,_function_(ice_setInconsistencyCount_AdditiveWCSP,"ice_setInconsistencyCount_AdditiveWCSP"));
  
  ice.increaseInconsistencyCount->addMethod(list::domain(4,ice._AdditiveWCSP,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_increaseInconsistencyCount_AdditiveWCSP,"ice_increaseInconsistencyCount_AdditiveWCSP"));
  
  ice.makeInconsistencyCount->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(ice_makeInconsistencyCount_AdditiveWCSP,"ice_makeInconsistencyCount_AdditiveWCSP"));
  
  choco.awakeOnInst->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_AdditiveWCSP,"choco_awakeOnInst_AdditiveWCSP"));
  
  ice.updateIC->addMethod(list::domain(3,ice._AdditiveWCSP,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_updateIC_AdditiveWCSP,"ice_updateIC_AdditiveWCSP"));
  
  ice.recomputeVarPenalty->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+SLOT_UPDATE,_function_(ice_recomputeVarPenalty_AdditiveWCSP,"ice_recomputeVarPenalty_AdditiveWCSP"));
  
  ice.getVarPenalty->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_getVarPenalty_AdditiveWCSP,"ice_getVarPenalty_AdditiveWCSP"));
  
  choco.awakeOnRem->addMethod(list::domain(3,ice._AdditiveWCSP,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_AdditiveWCSP,"choco_awakeOnRem_AdditiveWCSP"));
  
  choco.awakeOnVar->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_AdditiveWCSP,"choco_awakeOnVar_AdditiveWCSP"));
  
  choco.awakeOnInf->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_AdditiveWCSP,"choco_awakeOnInf_AdditiveWCSP"));
  
  choco.awakeOnSup->addMethod(list::domain(2,ice._AdditiveWCSP,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_AdditiveWCSP,"choco_awakeOnSup_AdditiveWCSP"));
  
  choco.getPriority->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._integer,
  	0,_function_(choco_getPriority_AdditiveWCSP_ice,"choco_getPriority_AdditiveWCSP_ice"));
  
  choco.propagate->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_AdditiveWCSP,"choco_propagate_AdditiveWCSP"));
  
  choco.awake->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_AdditiveWCSP_ice,"choco_awake_AdditiveWCSP_ice"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,ice._AdditiveWCSP),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_AdditiveWCSP,"choco_testIfSatisfied_AdditiveWCSP"));
  
  { global_variable * _CL_obj = (ice.ICE_RELEASE = (global_variable *) Core._global_variable->instantiate("ICE_RELEASE",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _float_(0.8));
    close_global_variable(_CL_obj);
    } 
  
  ice.showIglooLicense->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(claire_showIglooLicense_void,"claire_showIglooLicense_void"));
  
  claire_showIglooLicense_void();
  
  { global_variable * _CL_obj = (ice.MVIEW = (global_variable *) Core._global_variable->instantiate("MVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.MTALK = (global_variable *) Core._global_variable->instantiate("MTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,5);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.MDEBUG = (global_variable *) Core._global_variable->instantiate("MDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,6);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.SCCVIEW = (global_variable *) Core._global_variable->instantiate("SCCVIEW",ice.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,5);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.SCCTALK = (global_variable *) Core._global_variable->instantiate("SCCTALK",ice.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,6);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.ICETEST_VIEW = (global_variable *) Core._global_variable->instantiate("ICETEST_VIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,-1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (ice.ICEBENCH_VIEW = (global_variable *) Core._global_variable->instantiate("ICEBENCH_VIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,-1);
    close_global_variable(_CL_obj);
    } 
  
  { (ice._IntCollection = ClaireClass::make("IntCollection",choco._Ephemeral,ice.it));
    CL_ADD_SLOT(ice._IntCollection,IntCollection,ice.maxsize,maxsize,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._IntCollection,IntCollection,ice.nbElts,nbElts,Kernel._integer,0);
    (ice._IntCollection->params = list::alloc(Kernel._any,1,_oid_(ice.maxsize)));
    } 
  
  ice.getSize->addMethod(list::domain(1,ice._IntCollection),Kernel._integer,
  	0,_function_(ice_getSize_IntCollection,"ice_getSize_IntCollection"));
  
  { (ice._IntStack = ClaireClass::make("IntStack",ice._IntCollection,ice.it));
    CL_ADD_SLOT(ice._IntStack,IntStack,choco.contents,contents,nth_class1(Kernel._list,Kernel._integer),CNULL);
    (ice._IntStack->params = list::alloc(Kernel._any,1,_oid_(ice.maxsize)));
    } 
  
  Kernel.close->addMethod(list::domain(1,ice._IntStack),ice._IntStack,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire_close_IntStack,"claire_close_IntStack"));
  
  Kernel.set_I->addMethod(list::domain(1,ice._IntStack),Kernel._any,
  	NEW_ALLOC,_function_(claire_set_I_IntStack,"claire_set!_IntStack"));
  
  ;
  ice.clear->addMethod(list::domain(1,ice._IntStack),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_clear_IntStack,"ice_clear_IntStack"));
  
  ice.pop->addMethod(list::domain(1,ice._IntStack),Kernel._integer,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_pop_IntStack,"ice_pop_IntStack"));
  
  ice.push->addMethod(list::domain(2,ice._IntStack,Kernel._integer),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_push_IntStack,"ice_push_IntStack"));
  
  { (ice._IntQueue = ClaireClass::make("IntQueue",ice._IntCollection,ice.it));
    CL_ADD_SLOT(ice._IntQueue,IntQueue,Core.last,last,Kernel._integer,0);
    CL_ADD_SLOT(ice._IntQueue,IntQueue,choco.contents,contents,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(ice._IntQueue,IntQueue,ice.onceinqueue,onceinqueue,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    (ice._IntQueue->params = list::alloc(Kernel._any,1,_oid_(ice.maxsize)));
    } 
  
  Kernel.close->addMethod(list::domain(1,ice._IntQueue),ice._IntQueue,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire_close_IntQueue,"claire_close_IntQueue"));
  
  ice.push->addMethod(list::domain(2,ice._IntQueue,Kernel._integer),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_push_IntQueue,"ice_push_IntQueue"));
  
  ice.pop->addMethod(list::domain(1,ice._IntQueue),Kernel._integer,
  	BAG_UPDATE+SLOT_UPDATE,_function_(ice_pop_IntQueue,"ice_pop_IntQueue"));
  
  ice.init->addMethod(list::domain(1,ice._IntQueue),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_init_IntQueue,"ice_init_IntQueue"));
  
  { (ice._AbstractBipartiteGraph = ClaireClass::make("AbstractBipartiteGraph",choco._LargeIntConstraint,ice.it));
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.nbLeftVertices,nbLeftVertices,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.nbRightVertices,nbRightVertices,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,choco.minValue,minValue,Kernel._integer,99999999);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.maxValue,maxValue,Kernel._integer,-99999999);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,Kernel.source,source,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.nbVertices,nbVertices,Kernel._integer,CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.refMatch,refMatch,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.matchingSize,matchingSize,Kernel._integer,0);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.left2rightArc,left2rightArc,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.right2leftArc,right2leftArc,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.queue,queue,ice._IntQueue,CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,choco.time,time,Kernel._integer,0);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.finishDate,finishDate,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.seen,seen,nth_type(Kernel._boolean),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.currentNode,currentNode,Kernel._integer,0);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.currentComponent,currentComponent,Kernel._integer,0);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.component,component,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteGraph,AbstractBipartiteGraph,ice.componentOrder,componentOrder,choco._BoolMatrix2D,CNULL);
    } 
  
  (*Kernel.store)(_oid_(ice.matchingSize));
  
  ice.showRefAssignment->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	NEW_ALLOC,_function_(ice_showRefAssignment_AbstractBipartiteGraph,"ice_showRefAssignment_AbstractBipartiteGraph"));
  
  ice.showSCCDecomposition->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(ice_showSCCDecomposition_AbstractBipartiteGraph,"ice_showSCCDecomposition_AbstractBipartiteGraph"));
  
  ice.mayMatch->addMethod(list::domain(2,ice._AbstractBipartiteGraph,Kernel._integer),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(ice_mayMatch_AbstractBipartiteGraph,"ice_mayMatch_AbstractBipartiteGraph"));
  
  ice.mayInverseMatch->addMethod(list::domain(2,ice._AbstractBipartiteGraph,Kernel._integer),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(ice_mayInverseMatch_AbstractBipartiteGraph,"ice_mayInverseMatch_AbstractBipartiteGraph"));
  
  ice.match->addMethod(list::domain(2,ice._AbstractBipartiteGraph,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_match_AbstractBipartiteGraph,"ice_match_AbstractBipartiteGraph"));
  
  { global_variable * _CL_obj = (ice.CHK = (global_variable *) Core._global_variable->instantiate("CHK",ice.it));
    (_CL_obj->range = Kernel._any);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      Core.nil->value);
    close_global_variable(_CL_obj);
    } 
  
  ice.checkFlow->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	0,_function_(choco_checkFlow_AbstractBipartiteGraph,"choco_checkFlow_AbstractBipartiteGraph"));
  
  ice.mayGrowFlowToSink->addMethod(list::domain(2,ice._AbstractBipartiteGraph,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayGrowFlowToSink_AbstractBipartiteGraph,"ice_mayGrowFlowToSink_AbstractBipartiteGraph"));
  
  ice.mayGrowFlowBetween->addMethod(list::domain(3,ice._AbstractBipartiteGraph,Kernel._integer,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayGrowFlowBetween_AbstractBipartiteGraph,"ice_mayGrowFlowBetween_AbstractBipartiteGraph"));
  
  ice.mayDiminishFlowBetween->addMethod(list::domain(3,ice._AbstractBipartiteGraph,Kernel._integer,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayDiminishFlowBetween_AbstractBipartiteGraph,"ice_mayDiminishFlowBetween_AbstractBipartiteGraph"));
  
  { (ice._AbstractBipartiteMatching = ClaireClass::make("AbstractBipartiteMatching",ice._AbstractBipartiteGraph,ice.it));
    CL_ADD_SLOT(ice._AbstractBipartiteMatching,AbstractBipartiteMatching,ice.refInverseMatch,refInverseMatch,nth_type(Kernel._integer),CNULL);
    } 
  
  ice.inverseMatch->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(ice_inverseMatch_AbstractBipartiteMatching,"ice_inverseMatch_AbstractBipartiteMatching"));
  
  ice.increaseMatchingSize->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_increaseMatchingSize_AbstractBipartiteMatching,"ice_increaseMatchingSize_AbstractBipartiteMatching"));
  
  ice.decreaseMatchingSize->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_decreaseMatchingSize_AbstractBipartiteMatching,"ice_decreaseMatchingSize_AbstractBipartiteMatching"));
  
  ice.deleteMatch->addMethod(list::domain(3,ice._AbstractBipartiteMatching,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_deleteMatch_AbstractBipartiteMatching,"ice_deleteMatch_AbstractBipartiteMatching"));
  
  ice.putRefMatch->addMethod(list::domain(3,ice._AbstractBipartiteMatching,Kernel._integer,Kernel._integer),Kernel._void,
  	RETURN_ARG,_function_(ice_putRefMatch_AbstractBipartiteMatching,"ice_putRefMatch_AbstractBipartiteMatching"));
  
  ice.setMatch->addMethod(list::domain(3,ice._AbstractBipartiteMatching,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_setMatch_AbstractBipartiteMatching,"ice_setMatch_AbstractBipartiteMatching"));
  
  ice.mayDiminishFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayDiminishFlowFromSource_AbstractBipartiteMatching,"ice_mayDiminishFlowFromSource_AbstractBipartiteMatching"));
  
  ice.mayGrowFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayGrowFlowFromSource_AbstractBipartiteMatching,"ice_mayGrowFlowFromSource_AbstractBipartiteMatching"));
  
  ice.mustGrowFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mustGrowFlowFromSource_AbstractBipartiteMatching,"ice_mustGrowFlowFromSource_AbstractBipartiteMatching"));
  
  (ice._BalancedBipartiteMatching = ClaireClass::make("BalancedBipartiteMatching",ice._AbstractBipartiteMatching,ice.it));
  
  { (ice._AbstractBipartiteFlow = ClaireClass::make("AbstractBipartiteFlow",ice._AbstractBipartiteGraph,ice.it));
    CL_ADD_SLOT(ice._AbstractBipartiteFlow,AbstractBipartiteFlow,ice.minFlow,minFlow,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteFlow,AbstractBipartiteFlow,ice.maxFlow,maxFlow,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteFlow,AbstractBipartiteFlow,ice.flow,flow,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(ice._AbstractBipartiteFlow,AbstractBipartiteFlow,ice.compatibleFlow,compatibleFlow,Kernel._boolean,CNULL);
    } 
  
  ice.setMatch->addMethod(list::domain(3,ice._AbstractBipartiteFlow,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_setMatch_AbstractBipartiteFlow,"ice_setMatch_AbstractBipartiteFlow"));
  
  ice.deleteMatch->addMethod(list::domain(3,ice._AbstractBipartiteFlow,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_deleteMatch_AbstractBipartiteFlow,"ice_deleteMatch_AbstractBipartiteFlow"));
  
  ice.putRefMatch->addMethod(list::domain(3,ice._AbstractBipartiteFlow,Kernel._integer,Kernel._integer),Kernel._void,
  	RETURN_ARG,_function_(ice_putRefMatch_AbstractBipartiteFlow,"ice_putRefMatch_AbstractBipartiteFlow"));
  
  ice.mayDiminishFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayDiminishFlowFromSource_AbstractBipartiteFlow,"ice_mayDiminishFlowFromSource_AbstractBipartiteFlow"));
  
  ice.mayGrowFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mayGrowFlowFromSource_AbstractBipartiteFlow,"ice_mayGrowFlowFromSource_AbstractBipartiteFlow"));
  
  ice.mustGrowFlowFromSource->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._boolean,
  	0,_function_(ice_mustGrowFlowFromSource_AbstractBipartiteFlow,"ice_mustGrowFlowFromSource_AbstractBipartiteFlow"));
  
  ice.increaseMatchingSize->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_increaseMatchingSize_AbstractBipartiteFlow,"ice_increaseMatchingSize_AbstractBipartiteFlow"));
  
  ice.decreaseMatchingSize->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_decreaseMatchingSize_AbstractBipartiteFlow,"ice_decreaseMatchingSize_AbstractBipartiteFlow"));
  
  { global_variable * _CL_obj = (ice.DBC = (global_variable *) Core._global_variable->instantiate("DBC",ice.it));
    (_CL_obj->range = Kernel._any);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      Core.nil->value);
    close_global_variable(_CL_obj);
    } 
  
  ice.findAlternatingPath->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_findAlternatingPath_AbstractBipartiteMatching,"ice_findAlternatingPath_AbstractBipartiteMatching"));
  
  ice.augment->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_augment_AbstractBipartiteMatching,"choco_augment_AbstractBipartiteMatching"));
  
  ice.augmentFlow->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_augmentFlow_AbstractBipartiteMatching_ice,"ice_augmentFlow_AbstractBipartiteMatching_ice"));
  
  abstract_property(ice.augmentFlow);
  
  ice.findAlternatingPath->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_findAlternatingPath_AbstractBipartiteFlow,"ice_findAlternatingPath_AbstractBipartiteFlow"));
  
  ice.augment->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_augment_AbstractBipartiteFlow,"choco_augment_AbstractBipartiteFlow"));
  
  ice.augmentFlow->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_augmentFlow_AbstractBipartiteFlow_ice,"ice_augmentFlow_AbstractBipartiteFlow_ice"));
  
  ice.turnIntoCompatibleFlow->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_turnIntoCompatibleFlow_AbstractBipartiteFlow,"ice_turnIntoCompatibleFlow_AbstractBipartiteFlow"));
  
  ice.circulate->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_circulate_AbstractBipartiteFlow,"ice_circulate_AbstractBipartiteFlow"));
  
  ice.findAlternatingCycle->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_findAlternatingCycle_AbstractBipartiteFlow,"ice_findAlternatingCycle_AbstractBipartiteFlow"));
  
  ice.initSCCGraph->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_initSCCGraph_AbstractBipartiteGraph,"ice_initSCCGraph_AbstractBipartiteGraph"));
  
  ice.addComponentVertex->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(ice_addComponentVertex_AbstractBipartiteGraph,"ice_addComponentVertex_AbstractBipartiteGraph"));
  
  ice.addComponentEdge->addMethod(list::domain(3,ice._AbstractBipartiteGraph,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_addComponentEdge_AbstractBipartiteGraph,"ice_addComponentEdge_AbstractBipartiteGraph"));
  
  ice.firstPassDFS->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_firstPassDFS_AbstractBipartiteMatching,"ice_firstPassDFS_AbstractBipartiteMatching"));
  
  ice.firstDFSearch->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_firstDFSearch_AbstractBipartiteMatching,"ice_firstDFSearch_AbstractBipartiteMatching"));
  
  ice.secondPassDFS->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_secondPassDFS_AbstractBipartiteMatching,"ice_secondPassDFS_AbstractBipartiteMatching"));
  
  ice.secondDFSearch->addMethod(list::domain(2,ice._AbstractBipartiteMatching,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_secondDFSearch_AbstractBipartiteMatching,"ice_secondDFSearch_AbstractBipartiteMatching"));
  
  ice.firstPassDFS->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_firstPassDFS_AbstractBipartiteFlow,"ice_firstPassDFS_AbstractBipartiteFlow"));
  
  ice.firstDFSearch->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_firstDFSearch_AbstractBipartiteFlow,"ice_firstDFSearch_AbstractBipartiteFlow"));
  
  ice.secondPassDFS->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_secondPassDFS_AbstractBipartiteFlow,"ice_secondPassDFS_AbstractBipartiteFlow"));
  
  ice.secondDFSearch->addMethod(list::domain(2,ice._AbstractBipartiteFlow,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(ice_secondDFSearch_AbstractBipartiteFlow,"ice_secondDFSearch_AbstractBipartiteFlow"));
  
  { (ice.deleteEdgeAndPublish = property::make("deleteEdgeAndPublish",1,choco.it,Kernel._any,0));
    ;} 
  
  ice.removeUselessEdges->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_removeUselessEdges_AbstractBipartiteMatching,"ice_removeUselessEdges_AbstractBipartiteMatching"));
  
  ice.removeUselessEdges->addMethod(list::domain(1,ice._AbstractBipartiteFlow),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_removeUselessEdges_AbstractBipartiteFlow,"ice_removeUselessEdges_AbstractBipartiteFlow"));
  
  choco.propagate->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	NEW_ALLOC,_function_(choco_propagate_AbstractBipartiteGraph,"choco_propagate_AbstractBipartiteGraph"));
  
  choco.getPriority->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._integer,
  	0,_function_(choco_getPriority_AbstractBipartiteGraph_ice,"choco_getPriority_AbstractBipartiteGraph_ice"));
  
  ice.closeAssignmentConstraint->addMethod(list::domain(1,ice._AbstractBipartiteGraph),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeAssignmentConstraint_AbstractBipartiteGraph,"choco_closeAssignmentConstraint_AbstractBipartiteGraph"));
  
  (ice._CompleteAllDiff = ClaireClass::make("CompleteAllDiff",ice._AbstractBipartiteMatching,ice.it));
  
  Kernel.self_print->addMethod(list::domain(1,ice._CompleteAllDiff),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_CompleteAllDiff_ice,"claire_self_print_CompleteAllDiff_ice"));
  
  ice.completeAllDiff->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),ice._CompleteAllDiff,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_completeAllDiff_list,"choco_completeAllDiff_list"));
  
  ice.setEdgeAndPublish->addMethod(list::domain(3,ice._CompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(ice_setEdgeAndPublish_CompleteAllDiff,"ice_setEdgeAndPublish_CompleteAllDiff"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,ice._CompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_CompleteAllDiff,"choco_deleteEdgeAndPublish_CompleteAllDiff"));
  
  choco.awakeOnRem->addMethod(list::domain(3,ice._CompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_CompleteAllDiff,"choco_awakeOnRem_CompleteAllDiff"));
  
  choco.awakeOnVar->addMethod(list::domain(2,ice._CompleteAllDiff,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_CompleteAllDiff,"choco_awakeOnVar_CompleteAllDiff"));
  
  choco.awakeOnInf->addMethod(list::domain(2,ice._CompleteAllDiff,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_CompleteAllDiff,"choco_awakeOnInf_CompleteAllDiff"));
  
  choco.awakeOnSup->addMethod(list::domain(2,ice._CompleteAllDiff,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_CompleteAllDiff,"choco_awakeOnSup_CompleteAllDiff"));
  
  choco.awakeOnInst->addMethod(list::domain(2,ice._CompleteAllDiff,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_CompleteAllDiff,"choco_awakeOnInst_CompleteAllDiff"));
  
  choco.awake->addMethod(list::domain(1,ice._CompleteAllDiff),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_CompleteAllDiff_ice,"choco_awake_CompleteAllDiff_ice"));
  
  (ice._Permutation = ClaireClass::make("Permutation",ice._BalancedBipartiteMatching,ice.it));
  
  Kernel.self_print->addMethod(list::domain(1,ice._Permutation),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_Permutation_ice,"claire_self_print_Permutation_ice"));
  
  ice.setEdgeAndPublish->addMethod(list::domain(4,ice._Permutation,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_setEdgeAndPublish_Permutation,"ice_setEdgeAndPublish_Permutation"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(4,ice._Permutation,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_Permutation1,"choco_deleteEdgeAndPublish_Permutation1"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,ice._Permutation,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_Permutation2,"choco_deleteEdgeAndPublish_Permutation2"));
  
  choco.awakeOnRem->addMethod(list::domain(3,ice._Permutation,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_Permutation,"choco_awakeOnRem_Permutation"));
  
  choco.awakeOnVar->addMethod(list::domain(2,ice._Permutation,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_Permutation,"choco_awakeOnVar_Permutation"));
  
  choco.awakeOnInf->addMethod(list::domain(2,ice._Permutation,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_Permutation,"choco_awakeOnInf_Permutation"));
  
  choco.awakeOnSup->addMethod(list::domain(2,ice._Permutation,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_Permutation,"choco_awakeOnSup_Permutation"));
  
  choco.awakeOnInst->addMethod(list::domain(2,ice._Permutation,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Permutation,"choco_awakeOnInst_Permutation"));
  
  choco.awake->addMethod(list::domain(1,ice._Permutation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_Permutation_ice,"choco_awake_Permutation_ice"));
  
  ice.permutation->addMethod(list::domain(2,nth_class1(Kernel._list,choco._IntVar),nth_class1(Kernel._list,choco._IntVar)),ice._Permutation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_permutation_list,"choco_permutation_list"));
  
  (ice._GlobalCardinality = ClaireClass::make("GlobalCardinality",ice._AbstractBipartiteFlow,ice.it));
  
  Kernel.self_print->addMethod(list::domain(1,ice._GlobalCardinality),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_GlobalCardinality_ice,"claire_self_print_GlobalCardinality_ice"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,ice._GlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_GlobalCardinality,"choco_deleteEdgeAndPublish_GlobalCardinality"));
  
  ice.setEdgeAndPublish->addMethod(list::domain(3,ice._GlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_setEdgeAndPublish_GlobalCardinality,"ice_setEdgeAndPublish_GlobalCardinality"));
  
  choco.awakeOnRem->addMethod(list::domain(3,ice._GlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_GlobalCardinality,"choco_awakeOnRem_GlobalCardinality"));
  
  choco.awakeOnVar->addMethod(list::domain(2,ice._GlobalCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_GlobalCardinality,"choco_awakeOnVar_GlobalCardinality"));
  
  choco.awakeOnInf->addMethod(list::domain(2,ice._GlobalCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_GlobalCardinality,"choco_awakeOnInf_GlobalCardinality"));
  
  choco.awakeOnSup->addMethod(list::domain(2,ice._GlobalCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_GlobalCardinality,"choco_awakeOnSup_GlobalCardinality"));
  
  choco.awakeOnInst->addMethod(list::domain(2,ice._GlobalCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_GlobalCardinality,"choco_awakeOnInst_GlobalCardinality"));
  
  choco.awake->addMethod(list::domain(1,ice._GlobalCardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_GlobalCardinality_ice,"choco_awake_GlobalCardinality_ice"));
  
  ice.gcc->addMethod(list::domain(2,nth_class1(Kernel._list,choco._IntVar),nth_class1(Kernel._list,Core._Interval)),ice._GlobalCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_gcc_list1,"choco_gcc_list1"));
  
  ice.gcc->addMethod(list::domain(4,nth_class1(Kernel._list,choco._IntVar),
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,Core._Interval)),ice._GlobalCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_gcc_list2,"choco_gcc_list2"));
  
  ice.checkComponentOrder->addMethod(list::domain(1,ice._AbstractBipartiteMatching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_checkComponentOrder_AbstractBipartiteMatching,"ice_checkComponentOrder_AbstractBipartiteMatching"));
  
  GC_UNBIND;} 

