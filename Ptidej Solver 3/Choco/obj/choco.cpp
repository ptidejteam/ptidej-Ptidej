/***** CLAIRE Compilation of file choco.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:33 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>


chocoClass choco;


// interface method 

int  AbstractConstraint::choco_getNbVars()
  	{ return (int ) choco.getNbVars->fcall((int) this);}


// interface method 

int  AbstractConstraint::choco_assignIndices(AbstractConstraint *root,int i)
  	{ return (int ) choco_assignIndices_AbstractConstraint(this,root,i);}


// interface method 

int  AbstractConstraint::choco_getConstraintIdx(int idx)
  	{ return (int ) choco.getConstraintIdx->fcall((int) this,(int) idx);}


// interface method 

void  AbstractConstraint::choco_setConstraintIndex(int i,int val)
  	{ choco_setConstraintIndex_AbstractConstraint(this,i,val);}


// interface method 

OID  AbstractConstraint::choco_askIfEntailed()
  	{ return (OID ) choco.askIfEntailed->fcall((int) this);}


// interface method 

ClaireBoolean * AbstractConstraint::choco_testIfSatisfied()
  	{ return (ClaireBoolean *) choco.testIfSatisfied->fcall((int) this);}


// interface method 

ClaireBoolean * AbstractConstraint::choco_testIfCompletelyInstantiated()
  	{ return (ClaireBoolean *) choco.testIfCompletelyInstantiated->fcall((int) this);}


// interface method 

void  AbstractConstraint::choco_propagate()
  	{ choco.propagate->fcall((int) this);}


// interface method 

void  AbstractConstraint::choco_awakeOnInf(int idx)
  	{ choco.awakeOnInf->fcall((int) this,(int) idx);}


// interface method 

void  AbstractConstraint::choco_awakeOnSup(int idx)
  	{ choco.awakeOnSup->fcall((int) this,(int) idx);}


// interface method 

void  AbstractConstraint::choco_awakeOnInst(int idx)
  	{ choco.awakeOnInst->fcall((int) this,(int) idx);}


// interface method 

void  AbstractConstraint::choco_awakeOnRem(int idx,int x)
  	{ choco.awakeOnRem->fcall((int) this,(int) idx,(int) x);}


// interface method 

ClaireBoolean * IntVar::choco_domainIncludedIn(list *sortedList)
  	{ return (ClaireBoolean *) choco.domainIncludedIn->fcall((int) this,(int) sortedList);}


// interface method 

ClaireBoolean * IntVar::choco_hasExactDomain()
  	{ return (ClaireBoolean *) choco.hasExactDomain->fcall((int) this);}


// interface method 

int  IntVar::choco_getInf()
  	{ return (int ) choco.getInf->fcall((int) this);}


// interface method 

int  IntVar::choco_getSup()
  	{ return (int ) choco.getSup->fcall((int) this);}


// interface method 

OID  PropagationEngine::choco_getNextActiveEventQueue()
  	{ return (OID ) choco.getNextActiveEventQueue->fcall((int) this);}


// interface method 

OID  AbstractBranching::choco_selectBranchingObject()
  	{ return (OID ) choco.selectBranchingObject->fcall((int) this);}


// interface method 

void  AbstractBranching::choco_goDownBranch(OID x,int i)
  	{ choco.goDownBranch->fcall((int) this,(int) x,(int) i);}


// interface method 

void  AbstractBranching::choco_traceDownBranch(OID x,int i)
  	{ choco.traceDownBranch->fcall((int) this,(int) x,(int) i);}


// interface method 

void  AbstractBranching::choco_goUpBranch(OID x,int i)
  	{ choco.goUpBranch->fcall((int) this,(int) x,(int) i);}


// interface method 

void  AbstractBranching::choco_traceUpBranch(OID x,int i)
  	{ choco.traceUpBranch->fcall((int) this,(int) x,(int) i);}


// interface method 

int  AbstractBranching::choco_getFirstBranch(OID x)
  	{ return (int ) choco.getFirstBranch->fcall((int) this,(int) x);}


// interface method 

int  AbstractBranching::choco_getNextBranch(OID x,int i)
  	{ return (int ) choco.getNextBranch->fcall((int) this,(int) x,(int) i);}


// interface method 

ClaireBoolean * AbstractBranching::choco_finishedBranching(OID x,int i)
  	{ return (ClaireBoolean *) choco.finishedBranching->fcall((int) this,(int) x,(int) i);}


// interface method 

ClaireBoolean * AbstractBranching::choco_branchOn(OID v,int n)
  	{ return (ClaireBoolean *) choco.branchOn->fcall((int) this,(int) v,(int) n);}


// interface method 

ClaireBoolean * GlobalSearchLimit::choco_mayExpandNewNode(GlobalSearchSolver *algo)
  	{ return (ClaireBoolean *) choco_mayExpandNewNode_GlobalSearchLimit(this,algo);}


// interface method 

list * AbstractIntDomain::choco_domainSequence()
  	{ return (list *) choco.domainSequence->fcall((int) this);}


// interface method 

set * AbstractIntDomain::choco_domainSet()
  	{ return (set *) choco.domainSet->fcall((int) this);}


// interface method 

int  AbstractIntDomain::choco_updateDomainInf(int x)
  	{ return (int ) choco.updateDomainInf->fcall((int) this,(int) x);}


// interface method 

int  AbstractIntDomain::choco_updateDomainSup(int x)
  	{ return (int ) choco.updateDomainSup->fcall((int) this,(int) x);}


// interface method 

int  AbstractIntDomain::choco_getDomainInf()
  	{ return (int ) choco.getDomainInf->fcall((int) this);}


// interface method 

int  AbstractIntDomain::choco_getDomainSup()
  	{ return (int ) choco.getDomainSup->fcall((int) this);}


// interface method 

ClaireBoolean * AbstractIntDomain::choco_containsValInDomain(int x)
  	{ return (ClaireBoolean *) choco.containsValInDomain->fcall((int) this,(int) x);}


// interface method 

int  AbstractIntDomain::choco_getNextValue(int x)
  	{ return (int ) choco.getNextValue->fcall((int) this,(int) x);}


// interface method 

int  AbstractIntDomain::choco_getPrevValue(int x)
  	{ return (int ) choco.getPrevValue->fcall((int) this,(int) x);}


// interface method 

ClaireBoolean * AbstractIntDomain::choco_removeDomainVal(int x)
  	{ return (ClaireBoolean *) choco.removeDomainVal->fcall((int) this,(int) x);}


// interface method 

void  AbstractIntDomain::choco_restrict(int x)
  	{ choco.restrict->fcall((int) this,(int) x);}


// interface method 

int  AbstractIntDomain::choco_getDomainCard()
  	{ return (int ) choco.getDomainCard->fcall((int) this);}


// interface method 

list * AbstractSetDomain::choco_getKernel()
  	{ return (list *) choco.getKernel->fcall((int) this);}


// interface method 

list * AbstractSetDomain::choco_getEnveloppe()
  	{ return (list *) choco.getEnveloppe->fcall((int) this);}


// interface method 

int  AbstractSetDomain::choco_getKernelSize()
  	{ return (int ) choco.getKernelSize->fcall((int) this);}


// interface method 

int  AbstractSetDomain::choco_getEnveloppeSize()
  	{ return (int ) choco.getEnveloppeSize->fcall((int) this);}


// interface method 

int  AbstractSetDomain::choco_getEnveloppeInf()
  	{ return (int ) choco.getEnveloppeInf->fcall((int) this);}


// interface method 

int  AbstractSetDomain::choco_getEnveloppeSup()
  	{ return (int ) choco.getEnveloppeSup->fcall((int) this);}


// interface method 

ClaireBoolean * AbstractSetDomain::choco_isInKernel(int x)
  	{ return (ClaireBoolean *) choco.isInKernel->fcall((int) this,(int) x);}


// interface method 

ClaireBoolean * AbstractSetDomain::choco_isInEnveloppe(int x)
  	{ return (ClaireBoolean *) choco.isInEnveloppe->fcall((int) this,(int) x);}


// interface method 

ClaireBoolean * AbstractSetDomain::choco_updateKernel(int x)
  	{ return (ClaireBoolean *) choco.updateKernel->fcall((int) this,(int) x);}


// interface method 

ClaireBoolean * AbstractSetDomain::choco_updateEnveloppe(int x)
  	{ return (ClaireBoolean *) choco.updateEnveloppe->fcall((int) this,(int) x);}


// interface method 

void  EventCollection::choco_popSomeEvents()
  	{ choco.popSomeEvents->fcall((int) this);}


// interface method 

ClaireBoolean * BinRelation::choco_getTruthValue(int x,int y)
  	{ return (ClaireBoolean *) choco.getTruthValue->fcall((int) this,(int) x,(int) y);}


// interface method 

void  LinComb::choco_filter(ClaireBoolean *startWithLB,int minNbRules)
  	{ choco.filter->fcall((int) this,(int) startWithLB,(int) minNbRules);}


// interface method 

ClaireBoolean * LinComb::choco_filterOnImprovedLowerBound()
  	{ return (ClaireBoolean *) choco.filterOnImprovedLowerBound->fcall((int) this);}


// interface method 

ClaireBoolean * LinComb::choco_filterOnImprovedUpperBound()
  	{ return (ClaireBoolean *) choco.filterOnImprovedUpperBound->fcall((int) this);}


// interface method 

OID  VarSelector::choco_selectVar()
  	{ return (OID ) choco.selectVar->fcall((int) this);}


// interface method 

ClaireBoolean * ValIterator::choco_isOver(IntVar *x,int i)
  	{ return (ClaireBoolean *) choco.isOver->fcall((int) this,(int) x,(int) i);}


// interface method 

int  ValIterator::choco_getFirstVal(IntVar *x)
  	{ return (int ) choco.getFirstVal->fcall((int) this,(int) x);}


// interface method 

int  ValIterator::choco_getNextVal(IntVar *x,int i)
  	{ return (int ) choco.getNextVal->fcall((int) this,(int) x,(int) i);}


// interface method 

int  ValSelector::choco_getBestVal(IntVar *x)
  	{ return (int ) choco.getBestVal->fcall((int) this,(int) x);}

// definition of the meta-model for choco 

void chocoClass::metaLoad() { 
  GC_BIND;
  ClEnv->module_I = it;
// definition of the properties 
  choco.sum = property::make("sum",claire.it);
  choco.product = property::make("product",claire.it);
  choco.count = property::make("count",claire.it);
  choco.abs = property::make("abs",claire.it);
  choco.stringFormat = property::make("stringFormat",claire.it);
  choco.knownInt = property::make("knownInt",choco.it);
  choco.latestValue = property::make("latestValue",claire.it);
  choco.latestUpdate = property::make("latestUpdate",claire.it);
  choco.backtrackable = property::make("backtrackable",choco.it);
  choco.size1 = property::make("size1",choco.it);
  choco.offset1 = property::make("offset1",choco.it);
  choco.size2 = property::make("size2",choco.it);
  choco.offset2 = property::make("offset2",choco.it);
  choco.dim = property::make("dim",2,choco.it);
  choco.lsizes = property::make("lsizes",choco.it);
  choco.offsetArray = property::make("offsetArray",choco.it);
  choco.contents = property::make("contents",choco.it);
  choco.make2DBoolMatrix = property::make("make2DBoolMatrix",choco.it);
  choco.make2DIntMatrix = property::make("make2DIntMatrix",choco.it);
  choco.makeNDBoolMatrix = property::make("makeNDBoolMatrix",choco.it);
  choco.makeNDIntMatrix = property::make("makeNDIntMatrix",choco.it);
  choco.flatIndex = property::make("flatIndex",choco.it);
  choco.offset = property::make("offset",choco.it);
  choco.asize = property::make("asize",choco.it);
  choco.getOriginalMin = property::make("getOriginalMin",choco.it);
  choco.getOriginalMax = property::make("getOriginalMax",choco.it);
  choco.intValues = property::make("intValues",choco.it);
  choco.getIntAnnotation = property::make("getIntAnnotation",choco.it);
  choco.setIntAnnotation = property::make("setIntAnnotation",choco.it);
  choco.makeIntSetIntAnnotation = property::make("makeIntSetIntAnnotation",choco.it);
  choco.boolValues = property::make("boolValues",choco.it);
  choco.getBoolAnnotation = property::make("getBoolAnnotation",choco.it);
  choco.setBoolAnnotation = property::make("setBoolAnnotation",choco.it);
  choco.makeIntSetBoolAnnotation = property::make("makeIntSetBoolAnnotation",choco.it);
  choco.defaultValue = property::make("defaultValue",choco.it);
  choco.objs = property::make("objs",choco.it);
  choco.nbLeft = property::make("nbLeft",choco.it);
  choco.indices = property::make("indices",choco.it);
  choco.makeIndexesTable = property::make("makeIndexesTable",choco.it);
  choco.makeBipartiteSet = property::make("makeBipartiteSet",choco.it);
  choco.swap = property::make("swap",choco.it);
  choco.moveLeft = property::make("moveLeft",choco.it);
  choco.moveRight = property::make("moveRight",choco.it);
  choco.moveAllLeft = property::make("moveAllLeft",choco.it);
  choco.moveAllRight = property::make("moveAllRight",choco.it);
  choco.addRight = property::make("addRight",choco.it);
  choco.addLeft = property::make("addLeft",choco.it);
  choco.isLeft = property::make("isLeft",choco.it);
  choco.isIn = property::make("isIn",choco.it);
  choco.getNbLeft = property::make("getNbLeft",choco.it);
  choco.getNbRight = property::make("getNbRight",choco.it);
  choco.getNbObjects = property::make("getNbObjects",choco.it);
  choco.getObject = property::make("getObject",choco.it);
  choco.leftPart = property::make("leftPart",choco.it);
  choco.rightPart = property::make("rightPart",choco.it);
  choco.getBitCount = property::make("getBitCount",choco.it);
  choco.getMinBitIndex = property::make("getMinBitIndex",choco.it);
  choco.getMaxBitIndex = property::make("getMaxBitIndex",choco.it);
  choco.makeAllOnesBitVector = property::make("makeAllOnesBitVector",choco.it);
  choco.getBitVectorList = property::make("getBitVectorList",choco.it);
  choco.isInBitVectorList = property::make("isInBitVectorList",choco.it);
  choco.addToBitVectorList = property::make("addToBitVectorList",choco.it);
  choco.remFromBitVectorList = property::make("remFromBitVectorList",choco.it);
  choco.getBitVectorListCount = property::make("getBitVectorListCount",choco.it);
  choco.getBitVectorInf = property::make("getBitVectorInf",choco.it);
  choco.getBitVectorSup = property::make("getBitVectorSup",choco.it);
  choco.showChocoLicense = property::make("showChocoLicense",choco.it);
  choco.algo = property::make("algo",choco.it);
  choco.time = property::make("time",2,choco.it);
  choco.nbBk = property::make("nbBk",choco.it);
  choco.nbNd = property::make("nbNd",choco.it);
  choco.objectiveValue = property::make("objectiveValue",2,choco.it);
  choco.lval = property::make("lval",choco.it);
  choco.makeSolution = property::make("makeSolution",choco.it);
  choco.constraints = property::make("constraints",choco.it);
  choco.setVars = property::make("setVars",choco.it);
  choco.feasible = property::make("feasible",choco.it);
  choco.solved = property::make("solved",2,choco.it);
  choco.feasibleMode = property::make("feasibleMode",choco.it);
  choco.propagationEngine = property::make("propagationEngine",choco.it);
  choco.globalSearchSolver = property::make("globalSearchSolver",choco.it);
  choco.invariantEngine = property::make("invariantEngine",choco.it);
  choco.localSearchSolver = property::make("localSearchSolver",choco.it);
  choco.getIntVar = property::make("getIntVar",choco.it);
  choco.getSetVar = property::make("getSetVar",choco.it);
  choco.problem = property::make("problem",choco.it);
  choco.maxSize = property::make("maxSize",choco.it);
  choco.propagationOverflow = property::make("propagationOverflow",choco.it);
  choco.contradictionCause = property::make("contradictionCause",choco.it);
  choco.nbViolatedConstraints = property::make("nbViolatedConstraints",choco.it);
  choco.minNbViolatedConstraints = property::make("minNbViolatedConstraints",choco.it);
  choco.assignedThenUnassignedVars = property::make("assignedThenUnassignedVars",choco.it);
  choco.lastAssignedVar = property::make("lastAssignedVar",choco.it);
  choco.conflictingVars = property::make("conflictingVars",choco.it);
  choco.solutions = property::make("solutions",choco.it);
  choco.varsToStore = property::make("varsToStore",choco.it);
  choco.varsToShow = property::make("varsToShow",choco.it);
  choco.maxNb = property::make("maxNb",choco.it);
  choco.maxMsec = property::make("maxMsec",choco.it);
  choco.stopAtFirstSol = property::make("stopAtFirstSol",choco.it);
  choco.nbSol = property::make("nbSol",choco.it);
  choco.maxNbBk = property::make("maxNbBk",2,choco.it);
  choco.maxPrintDepth = property::make("maxPrintDepth",choco.it);
  choco.maxSolutionStorage = property::make("maxSolutionStorage",choco.it);
  choco.branchingComponents = property::make("branchingComponents",choco.it);
  choco.baseWorld = property::make("baseWorld",choco.it);
  choco.limits = property::make("limits",choco.it);
  choco.manager = property::make("manager",choco.it);
  choco.nextBranching = property::make("nextBranching",2,choco.it);
  choco.buildAssignment = property::make("buildAssignment",choco.it);
  choco.changeAssignment = property::make("changeAssignment",choco.it);
  choco.maxNbLocalSearch = property::make("maxNbLocalSearch",choco.it);
  choco.maxNbLocalMoves = property::make("maxNbLocalMoves",choco.it);
  choco.getDomainInf = property::make("getDomainInf",3,choco.it,choco._AbstractIntDomain,9);
  choco.getDomainSup = property::make("getDomainSup",3,choco.it,choco._AbstractIntDomain,10);
  choco.updateDomainInf = property::make("updateDomainInf",3,choco.it,choco._AbstractIntDomain,7);
  choco.updateDomainSup = property::make("updateDomainSup",3,choco.it,choco._AbstractIntDomain,8);
  choco.bucketSize = property::make("bucketSize",choco.it);
  choco.makeLinkedListIntDomain = property::make("makeLinkedListIntDomain",choco.it);
  choco.isIncludedIn = property::make("isIncludedIn",choco.it);
  choco.minValue = property::make("minValue",choco.it);
  choco.kernelSize = property::make("kernelSize",choco.it);
  choco.enveloppeSize = property::make("enveloppeSize",choco.it);
  choco.getKernel = property::make("getKernel",1,choco.it,choco._AbstractSetDomain,17);
  choco.getEnveloppe = property::make("getEnveloppe",1,choco.it,choco._AbstractSetDomain,18);
  choco.getKernelSize = property::make("getKernelSize",1,choco.it,choco._AbstractSetDomain,19);
  choco.getEnveloppeSize = property::make("getEnveloppeSize",1,choco.it,choco._AbstractSetDomain,20);
  choco.getKernelInf = property::make("getKernelInf",1,choco.it,choco._AbstractSetDomain,21);
  choco.getKernelSup = property::make("getKernelSup",1,choco.it,choco._AbstractSetDomain,22);
  choco.getEnveloppeInf = property::make("getEnveloppeInf",1,choco.it,choco._AbstractSetDomain,23);
  choco.getEnveloppeSup = property::make("getEnveloppeSup",1,choco.it,choco._AbstractSetDomain,24);
  choco.isInEnveloppe = property::make("isInEnveloppe",1,choco.it,choco._AbstractSetDomain,26);
  choco.isInKernel = property::make("isInKernel",1,choco.it,choco._AbstractSetDomain,25);
  choco.updateKernel = property::make("updateKernel",1,choco.it,choco._AbstractSetDomain,27);
  choco.updateEnveloppe = property::make("updateEnveloppe",1,choco.it,choco._AbstractSetDomain,28);
  choco.kernelBitVector = property::make("kernelBitVector",choco.it);
  choco.enveloppeBitVector = property::make("enveloppeBitVector",choco.it);
  choco.makeBitSetDomain = property::make("makeBitSetDomain",choco.it);
  choco.kernelBitVectorList = property::make("kernelBitVectorList",choco.it);
  choco.enveloppeBitVectorList = property::make("enveloppeBitVectorList",choco.it);
  choco.makeBitListSetDomain = property::make("makeBitListSetDomain",choco.it);
  choco.modifiedVar = property::make("modifiedVar",choco.it);
  choco.nextConst = property::make("nextConst",choco.it);
  choco.cause = property::make("cause",choco.it);
  choco.getDomainKernel = property::make("getDomainKernel",choco.it);
  choco.idxInQueue = property::make("idxInQueue",choco.it);
  choco.inf = property::make("inf",choco.it);
  choco.sup = property::make("sup",choco.it);
  choco.getDomainEnveloppe = property::make("getDomainEnveloppe",choco.it);
  choco.maxVals = property::make("maxVals",choco.it);
  choco.nbVals = property::make("nbVals",choco.it);
  choco.many = property::make("many",choco.it);
  choco.valueStack = property::make("valueStack",choco.it);
  choco.causeStack = property::make("causeStack",choco.it);
  choco.touchedConst = property::make("touchedConst",choco.it);
  choco.initialized = property::make("initialized",choco.it);
  choco.priority = property::make("priority",choco.it);
  choco.engine = property::make("engine",choco.it);
  choco.qsize = property::make("qsize",choco.it);
  choco.qLastRead = property::make("qLastRead",choco.it);
  choco.qLastEnqueued = property::make("qLastEnqueued",choco.it);
  choco.isPopping = property::make("isPopping",choco.it);
  choco.eventQueue = property::make("eventQueue",choco.it);
  choco.redundantEvent = property::make("redundantEvent",choco.it);
  choco.sLastRead = property::make("sLastRead",choco.it);
  choco.sLastPushed = property::make("sLastPushed",choco.it);
  choco.partition = property::make("partition",choco.it);
  choco.removalEvtQueue = property::make("removalEvtQueue",choco.it);
  choco.boundEvtQueue = property::make("boundEvtQueue",choco.it);
  choco.instEvtStack = property::make("instEvtStack",choco.it);
  choco.delayedConst1 = property::make("delayedConst1",choco.it);
  choco.delayedConst2 = property::make("delayedConst2",choco.it);
  choco.delayedConst3 = property::make("delayedConst3",choco.it);
  choco.delayedConst4 = property::make("delayedConst4",choco.it);
  choco.nbPendingInitConstAwakeEvent = property::make("nbPendingInitConstAwakeEvent",choco.it);
  choco.nbPendingVarEvent = property::make("nbPendingVarEvent",choco.it);
  choco.delayLinCombThreshold = property::make("delayLinCombThreshold",choco.it);
  choco.raiseContradiction = property::make("raiseContradiction",choco.it);
  choco.flushCurrentOpenEvents = property::make("flushCurrentOpenEvents",choco.it);
  choco.getContradictionCause = property::make("getContradictionCause",choco.it);
  choco.getProblem = property::make("getProblem",choco.it);
  choco.makeChocEngine = property::make("makeChocEngine",choco.it);
  choco.attachPropagationEngine = property::make("attachPropagationEngine",choco.it);
  choco.isEmpty = property::make("isEmpty",choco.it);
  choco.popNextEvent = property::make("popNextEvent",choco.it);
  choco.nextEventPostIndex = property::make("nextEventPostIndex",choco.it);
  choco.flushEventQueue = property::make("flushEventQueue",choco.it);
  choco.updtInfEvt = property::make("updtInfEvt",choco.it);
  choco.updtSupEvt = property::make("updtSupEvt",choco.it);
  choco.remValEvt = property::make("remValEvt",choco.it);
  choco.checkCleanState = property::make("checkCleanState",choco.it);
  choco.updtKerEvt = property::make("updtKerEvt",choco.it);
  choco.updtEnvEvt = property::make("updtEnvEvt",choco.it);
  choco.pushWorld = property::make("pushWorld",choco.it);
  choco.popWorld = property::make("popWorld",choco.it);
  choco.setWorld = property::make("setWorld",choco.it);
  choco.commitWorld = property::make("commitWorld",choco.it);
  choco.postInstInt = property::make("postInstInt",choco.it);
  choco.postRemoveVal = property::make("postRemoveVal",choco.it);
  choco.postUpdateInf = property::make("postUpdateInf",choco.it);
  choco.postUpdateSup = property::make("postUpdateSup",choco.it);
  choco.postUpdateKer = property::make("postUpdateKer",choco.it);
  choco.postUpdateEnv = property::make("postUpdateEnv",choco.it);
  choco.postInstSet = property::make("postInstSet",choco.it);
  choco.postConstAwake = property::make("postConstAwake",choco.it);
  choco.postInstantiateEvt = property::make("postInstantiateEvt",choco.it);
  choco.instantiateEvt = property::make("instantiateEvt",choco.it);
  choco.postBoundEvent = property::make("postBoundEvent",choco.it);
  choco.getQueue = property::make("getQueue",choco.it);
  choco.registerEvent = property::make("registerEvent",choco.it);
  choco.constAwakeEvent = property::make("constAwakeEvent",choco.it);
  choco.constAwake = property::make("constAwake",choco.it);
  choco.popSomeEvents = property::make("popSomeEvents",1,choco.it,choco._EventCollection,29);
  choco.nbConstraints = property::make("nbConstraints",choco.it);
  choco.doAwakeOnInstSet = property::make("doAwakeOnInstSet",choco.it);
  choco.doAwakeOnKer = property::make("doAwakeOnKer",choco.it);
  choco.doAwakeOnEnv = property::make("doAwakeOnEnv",choco.it);
  choco.getNextActiveConstraintEventQueue = property::make("getNextActiveConstraintEventQueue",choco.it);
  choco.getNextActiveVariableEventQueue = property::make("getNextActiveVariableEventQueue",choco.it);
  choco.hook = property::make("hook",choco.it);
  choco.getConstraint = property::make("getConstraint",choco.it);
  choco.getDegree = property::make("getDegree",choco.it);
  choco.savedAssignment = property::make("savedAssignment",choco.it);
  choco.bucket = property::make("bucket",choco.it);
  choco.closeIntVar = property::make("closeIntVar",choco.it);
  choco.updateInf = property::make("updateInf",3,choco.it);
  choco.updateSup = property::make("updateSup",3,choco.it);
  choco.instantiate = property::make("instantiate",choco.it);
  choco.removeVal = property::make("removeVal",3,choco.it);
  choco.hasExactDomain = property::make("hasExactDomain",3,choco.it,choco._IntVar,33);
  choco.getNextDomainValue = property::make("getNextDomainValue",choco.it);
  choco.getPrevDomainValue = property::make("getPrevDomainValue",choco.it);
  choco.getValue = property::make("getValue",choco.it);
  choco.getDomainSize = property::make("getDomainSize",choco.it);
  choco.removeInterval = property::make("removeInterval",choco.it);
  choco.restrictTo = property::make("restrictTo",choco.it);
  choco.setBucket = property::make("setBucket",choco.it);
  choco.getDomainKernelSize = property::make("getDomainKernelSize",choco.it);
  choco.getDomainEnveloppeSize = property::make("getDomainEnveloppeSize",choco.it);
  choco.getDomainEnveloppeInf = property::make("getDomainEnveloppeInf",choco.it);
  choco.getDomainEnveloppeSup = property::make("getDomainEnveloppeSup",choco.it);
  choco.getDomainKernelInf = property::make("getDomainKernelInf",choco.it);
  choco.getDomainKernelSup = property::make("getDomainKernelSup",choco.it);
  choco.isInDomainEnveloppe = property::make("isInDomainEnveloppe",choco.it);
  choco.isInDomainKernel = property::make("isInDomainKernel",choco.it);
  choco.addSetVar = property::make("addSetVar",choco.it);
  choco.closeSetVar = property::make("closeSetVar",choco.it);
  choco.addToKernel = property::make("addToKernel",choco.it);
  choco.remFromEnveloppe = property::make("remFromEnveloppe",choco.it);
  choco.setIn = property::make("setIn",choco.it);
  choco.setOut = property::make("setOut",choco.it);
  choco.fastDispatchIndex = property::make("fastDispatchIndex",2,choco.it);
  choco.violated = property::make("violated",choco.it);
  choco.cste = property::make("cste",choco.it);
  choco.v1 = property::make("v1",choco.it);
  choco.idx1 = property::make("idx1",choco.it);
  choco.v2 = property::make("v2",choco.it);
  choco.idx2 = property::make("idx2",choco.it);
  choco.v3 = property::make("v3",choco.it);
  choco.idx3 = property::make("idx3",choco.it);
  choco.nbVars = property::make("nbVars",choco.it);
  choco.closeLargeIntConstraint = property::make("closeLargeIntConstraint",choco.it);
  choco.target = property::make("target",choco.it);
  choco.delay = property::make("delay",choco.it);
  choco.const1 = property::make("const1",choco.it);
  choco.const2 = property::make("const2",choco.it);
  choco.lconst = property::make("lconst",choco.it);
  choco.loffset = property::make("loffset",choco.it);
  choco.nbConst = property::make("nbConst",choco.it);
  choco.additionalVars = property::make("additionalVars",choco.it);
  choco.additionalIndices = property::make("additionalIndices",choco.it);
  choco.closeCompositeConstraint = property::make("closeCompositeConstraint",choco.it);
  choco.getNbVarsFromSubConst = property::make("getNbVarsFromSubConst",choco.it);
  choco.connectHook = property::make("connectHook",3,choco.it);
  choco.reconnectHook = property::make("reconnectHook",3,choco.it);
  choco.disconnectHook = property::make("disconnectHook",3,choco.it);
  choco.assignIndices = property::make("assignIndices",choco.it);
  choco.getConstraintIdx = property::make("getConstraintIdx",1,choco.it,choco._AbstractConstraint,37);
  choco.opposite = property::make("opposite",1,choco.it,choco._AbstractConstraint,42);
  choco.abstractIncInf = property::make("abstractIncInf",choco.it);
  choco.abstractDecSup = property::make("abstractDecSup",choco.it);
  choco.abstractInstantiate = property::make("abstractInstantiate",choco.it);
  choco.abstractRemoveVal = property::make("abstractRemoveVal",choco.it);
  choco.addIntVar = property::make("addIntVar",choco.it);
  choco.connectSetVar = property::make("connectSetVar",choco.it);
  choco.connectSetVarEvents = property::make("connectSetVarEvents",3,choco.it);
  choco.disconnectSetVar = property::make("disconnectSetVar",choco.it);
  choco.disconnectSetVarEvents = property::make("disconnectSetVarEvents",3,choco.it);
  choco.reconnectSetVar = property::make("reconnectSetVar",choco.it);
  choco.reconnectSetVarEvents = property::make("reconnectSetVarEvents",3,choco.it);
  choco.reconnectIntVar = property::make("reconnectIntVar",choco.it);
  choco.isActiveIntVar = property::make("isActiveIntVar",choco.it);
  choco.isActiveEvent = property::make("isActiveEvent",choco.it);
  choco.isActiveSetVar = property::make("isActiveSetVar",choco.it);
  choco.test = property::make("test",choco.it);
  choco.matrix = property::make("matrix",choco.it);
  choco.setTruePair = property::make("setTruePair",choco.it);
  choco.feasRelation = property::make("feasRelation",choco.it);
  choco.feasiblePair = property::make("feasiblePair",choco.it);
  choco.setPassive = property::make("setPassive",choco.it);
  choco.see = property::make("see",choco.it);
  choco.makeAC3BinConstraint = property::make("makeAC3BinConstraint",choco.it);
  choco.reviseV2 = property::make("reviseV2",choco.it);
  choco.reviseV1 = property::make("reviseV1",choco.it);
  choco.nbSupport1 = property::make("nbSupport1",choco.it);
  choco.nbSupport2 = property::make("nbSupport2",choco.it);
  choco.validSupport1 = property::make("validSupport1",choco.it);
  choco.validSupport2 = property::make("validSupport2",choco.it);
  choco.makeAC4BinConstraint = property::make("makeAC4BinConstraint",choco.it);
  choco.decrementNbSupportV1 = property::make("decrementNbSupportV1",choco.it);
  choco.decrementNbSupportV2 = property::make("decrementNbSupportV2",choco.it);
  choco.resetNbSupportV1 = property::make("resetNbSupportV1",choco.it);
  choco.resetNbSupportV2 = property::make("resetNbSupportV2",choco.it);
  choco.currentSupport1 = property::make("currentSupport1",choco.it);
  choco.currentSupport2 = property::make("currentSupport2",choco.it);
  choco.makeAC2001BinConstraint = property::make("makeAC2001BinConstraint",choco.it);
  choco.updateSupportVal2 = property::make("updateSupportVal2",choco.it);
  choco.updateSupportVal1 = property::make("updateSupportVal1",choco.it);
  choco.cachedTuples = property::make("cachedTuples",choco.it);
  choco.feasTest = property::make("feasTest",choco.it);
  choco.tupleConstraint = property::make("tupleConstraint",choco.it);
  choco.nbPosVars = property::make("nbPosVars",choco.it);
  choco.coefs = property::make("coefs",choco.it);
  choco.op = property::make("op",choco.it);
  choco.improvedUpperBound = property::make("improvedUpperBound",choco.it);
  choco.improvedLowerBound = property::make("improvedLowerBound",choco.it);
  choco.makeLinComb = property::make("makeLinComb",choco.it);
  choco.computeVarIdxInOpposite = property::make("computeVarIdxInOpposite",choco.it);
  choco.computeUpperBound = property::make("computeUpperBound",3,choco.it);
  choco.computeLowerBound = property::make("computeLowerBound",3,choco.it);
  choco.filterOnImprovedLowerBound = property::make("filterOnImprovedLowerBound",1,choco.it,choco._LinComb,49);
  choco.filterOnImprovedUpperBound = property::make("filterOnImprovedUpperBound",1,choco.it,choco._LinComb,50);
  choco.makeEltConstraint = property::make("makeEltConstraint",choco.it);
  choco.updateValueFromIndex = property::make("updateValueFromIndex",choco.it);
  choco.updateIndexFromValue = property::make("updateIndexFromValue",choco.it);
  choco.tval = property::make("tval",choco.it);
  choco.dim1 = property::make("dim1",choco.it);
  choco.dim2 = property::make("dim2",choco.it);
  choco.makeElt2Constraint = property::make("makeElt2Constraint",choco.it);
  choco.isPossible = property::make("isPossible",choco.it);
  choco.isSure = property::make("isSure",choco.it);
  choco.nbPossible = property::make("nbPossible",choco.it);
  choco.nbSure = property::make("nbSure",choco.it);
  choco.constrainOnInfNumber = property::make("constrainOnInfNumber",choco.it);
  choco.constrainOnSupNumber = property::make("constrainOnSupNumber",choco.it);
  choco.checkNbPossible = property::make("checkNbPossible",3,choco.it);
  choco.checkNbSure = property::make("checkNbSure",3,choco.it);
  choco.update = property::make("update",choco.it);
  choco.statusBitVector = property::make("statusBitVector",choco.it);
  choco.closeBoolConstraint = property::make("closeBoolConstraint",choco.it);
  choco.knownStatus = property::make("knownStatus",choco.it);
  choco.getStatus = property::make("getStatus",choco.it);
  choco.knownTargetStatus = property::make("knownTargetStatus",choco.it);
  choco.getTargetStatus = property::make("getTargetStatus",choco.it);
  choco.setStatus = property::make("setStatus",choco.it);
  choco.setTargetStatus = property::make("setTargetStatus",choco.it);
  choco.statusBitVectorList = property::make("statusBitVectorList",choco.it);
  choco.nbTrueStatus = property::make("nbTrueStatus",choco.it);
  choco.nbFalseStatus = property::make("nbFalseStatus",choco.it);
  choco.oppositeConst1 = property::make("oppositeConst1",choco.it);
  choco.oppositeConst2 = property::make("oppositeConst2",choco.it);
  choco.indicesInOppConst1 = property::make("indicesInOppConst1",choco.it);
  choco.indicesInOppConst2 = property::make("indicesInOppConst2",choco.it);
  choco.closeBoolConstraintWCounterOpp = property::make("closeBoolConstraintWCounterOpp",choco.it);
  choco.loppositeConst = property::make("loppositeConst",choco.it);
  choco.indicesInOppConsts = property::make("indicesInOppConsts",choco.it);
  choco.checkStatus = property::make("checkStatus",choco.it);
  choco.checkNbFalseStatus = property::make("checkNbFalseStatus",choco.it);
  choco.getCardVar = property::make("getCardVar",choco.it);
  choco.getCardVarIdx = property::make("getCardVarIdx",choco.it);
  choco.awakeOnNbTrue = property::make("awakeOnNbTrue",choco.it);
  choco.awakeOnNbFalse = property::make("awakeOnNbFalse",choco.it);
  choco.sv1 = property::make("sv1",choco.it);
  choco.sv2 = property::make("sv2",choco.it);
  choco.sv3 = property::make("sv3",choco.it);
  choco.setCard = property::make("setCard",choco.it);
  choco.memberOf = property::make("memberOf",choco.it);
  choco.notMemberOf = property::make("notMemberOf",choco.it);
  choco.setintersection = property::make("setintersection",choco.it);
  choco.setunion = property::make("setunion",choco.it);
  choco.subset = property::make("subset",choco.it);
  choco.disjoint = property::make("disjoint",choco.it);
  choco.emptyKernelIntersection = property::make("emptyKernelIntersection",choco.it);
  choco.overlap = property::make("overlap",choco.it);
  choco.alternatives = property::make("alternatives",2,choco.it);
  choco.selectBranching = property::make("selectBranching",choco.it);
  choco.branching = property::make("branching",choco.it);
  choco.selectVar = property::make("selectVar",1,choco.it,choco._VarSelector,53);
  choco.getSmallestDomainUnassignedVar = property::make("getSmallestDomainUnassignedVar",choco.it);
  choco.getSearchManager = property::make("getSearchManager",choco.it);
  choco.getMostConstrainedUnassignedVar = property::make("getMostConstrainedUnassignedVar",choco.it);
  choco.getDomOverDegBestUnassignedVar = property::make("getDomOverDegBestUnassignedVar",choco.it);
  choco.isOver = property::make("isOver",1,choco.it,choco._ValIterator,54);
  choco.getFirstVal = property::make("getFirstVal",1,choco.it,choco._ValIterator,55);
  choco.getNextVal = property::make("getNextVal",1,choco.it,choco._ValIterator,56);
  choco.getBestVal = property::make("getBestVal",1,choco.it,choco._ValSelector,57);
  choco.varHeuristic = property::make("varHeuristic",choco.it);
  choco.valHeuristic = property::make("valHeuristic",choco.it);
  choco.dichotomyThreshold = property::make("dichotomyThreshold",choco.it);
  choco.disjunctions = property::make("disjunctions",2,choco.it);
  choco.doMaximize = property::make("doMaximize",choco.it);
  choco.objective = property::make("objective",choco.it);
  choco.lowerBound = property::make("lowerBound",choco.it);
  choco.upperBound = property::make("upperBound",choco.it);
  choco.targetLowerBound = property::make("targetLowerBound",choco.it);
  choco.targetUpperBound = property::make("targetUpperBound",choco.it);
  choco.getBestObjectiveValue = property::make("getBestObjectiveValue",choco.it);
  choco.getObjectiveTarget = property::make("getObjectiveTarget",choco.it);
  choco.initBounds = property::make("initBounds",choco.it);
  choco.getFeasibility = property::make("getFeasibility",choco.it);
  choco.mayExpandNewNode = property::make("mayExpandNewNode",choco.it);
  choco.nbIter = property::make("nbIter",choco.it);
  choco.baseNbSol = property::make("baseNbSol",2,choco.it);
  choco.nbBkTot = property::make("nbBkTot",choco.it);
  choco.nbNdTot = property::make("nbNdTot",choco.it);
  choco.copyParameters = property::make("copyParameters",choco.it);
  choco.attach = property::make("attach",choco.it);
  choco.composeGlobalSearch = property::make("composeGlobalSearch",choco.it);
  choco.storeCurrentSolution = property::make("storeCurrentSolution",choco.it);
  choco.makeSolutionFromCurrentState = property::make("makeSolutionFromCurrentState",choco.it);
  choco.storeSolution = property::make("storeSolution",choco.it);
  choco.existsSolution = property::make("existsSolution",choco.it);
  choco.restoreBestSolution = property::make("restoreBestSolution",choco.it);
  choco.restoreSolution = property::make("restoreSolution",choco.it);
  choco.setVal = property::make("setVal",choco.it);
  choco.showSolution = property::make("showSolution",choco.it);
  choco.recordSolution = property::make("recordSolution",choco.it);
  choco.newNode = property::make("newNode",choco.it);
  choco.endNode = property::make("endNode",choco.it);
  choco.postDynamicCut = property::make("postDynamicCut",choco.it);
  choco.setMax = property::make("setMax",choco.it);
  choco.setMin = property::make("setMin",choco.it);
  choco.remVal = property::make("remVal",choco.it);
  choco.setBranch = property::make("setBranch",choco.it);
  choco.newTreeSearch = property::make("newTreeSearch",choco.it);
  choco.endTreeSearch = property::make("endTreeSearch",choco.it);
  choco.setBound = property::make("setBound",choco.it);
  choco.setTargetBound = property::make("setTargetBound",choco.it);
  choco.setTargetLowerBound = property::make("setTargetLowerBound",choco.it);
  choco.setTargetUpperBound = property::make("setTargetUpperBound",choco.it);
  choco.postTargetBound = property::make("postTargetBound",choco.it);
  choco.postTargetLowerBound = property::make("postTargetLowerBound",choco.it);
  choco.postTargetUpperBound = property::make("postTargetUpperBound",choco.it);
  choco.run = property::make("run",choco.it);
  choco.newLoop = property::make("newLoop",choco.it);
  choco.endLoop = property::make("endLoop",choco.it);
  choco.recordNoSolution = property::make("recordNoSolution",choco.it);
  choco.oneMoreLoop = property::make("oneMoreLoop",choco.it);
  choco.makeConflictCount = property::make("makeConflictCount",choco.it);
  choco.attachInvariantEngine = property::make("attachInvariantEngine",choco.it);
  choco.unitaryDeltaConflict = property::make("unitaryDeltaConflict",choco.it);
  choco.deltaNbConflicts = property::make("deltaNbConflicts",choco.it);
  choco.getMinConflictValue = property::make("getMinConflictValue",choco.it);
  choco.computeConflictAccount = property::make("computeConflictAccount",choco.it);
  choco.resetInvariants = property::make("resetInvariants",choco.it);
  choco.getLocalSearchObjectiveValue = property::make("getLocalSearchObjectiveValue",choco.it);
  choco.restoreBestAssignment = property::make("restoreBestAssignment",choco.it);
  choco.saveAssignment = property::make("saveAssignment",choco.it);
  choco.setFeasibleMode = property::make("setFeasibleMode",choco.it);
  choco.nbLocalSearch = property::make("nbLocalSearch",choco.it);
  choco.nbLocalMoves = property::make("nbLocalMoves",choco.it);
  choco.selectMoveVar = property::make("selectMoveVar",choco.it);
  choco.selectValue = property::make("selectValue",choco.it);
  choco.assignOneVar = property::make("assignOneVar",choco.it);
  choco.assignVal = property::make("assignVal",choco.it);
  choco.build = property::make("build",choco.it);
  choco.eraseAssignment = property::make("eraseAssignment",choco.it);
  choco.newValue = property::make("newValue",choco.it);
  choco.move = property::make("move",choco.it);
  choco.changeVal = property::make("changeVal",choco.it);
  choco.finishedLoop = property::make("finishedLoop",choco.it);
  choco.initLoop = property::make("initLoop",choco.it);
  choco.attachLocalSearchSolver = property::make("attachLocalSearchSolver",choco.it);
  choco.runLocalSearch = property::make("runLocalSearch",choco.it);
  choco.initIterations = property::make("initIterations",choco.it);
  choco.finishedIterations = property::make("finishedIterations",choco.it);
  choco.oneMoreMove = property::make("oneMoreMove",choco.it);
  choco.becomesAConflict = property::make("becomesAConflict",choco.it);
  choco.addOneConflict = property::make("addOneConflict",choco.it);
  choco.becomesSatisfied = property::make("becomesSatisfied",choco.it);
  choco.removeOneConflict = property::make("removeOneConflict",choco.it);
  choco.postAssignVal = property::make("postAssignVal",choco.it);
  choco.postUnAssignVal = property::make("postUnAssignVal",choco.it);
  choco.postChangeVal = property::make("postChangeVal",choco.it);
  choco.makeProblem = property::make("makeProblem",choco.it);
  choco.discardProblem = property::make("discardProblem",choco.it);
  choco.getPropagationEngine = property::make("getPropagationEngine",choco.it);
  choco.setDelayedLinearConstraintPropagation = property::make("setDelayedLinearConstraintPropagation",choco.it);
  choco.makeBoundIntVar = property::make("makeBoundIntVar",choco.it);
  choco.makeConstantIntVar = property::make("makeConstantIntVar",choco.it);
  choco.makeIntVar = property::make("makeIntVar",choco.it);
  choco.makeSetVar = property::make("makeSetVar",choco.it);
  choco.post = property::make("post",choco.it);
  choco.makeBinTruthTest = property::make("makeBinTruthTest",choco.it);
  choco.makeBinRelation = property::make("makeBinRelation",choco.it);
  choco.binConstraint = property::make("binConstraint",choco.it);
  choco.feasPairConstraint = property::make("feasPairConstraint",choco.it);
  choco.infeasPairConstraint = property::make("infeasPairConstraint",choco.it);
  choco.feasBinTestConstraint = property::make("feasBinTestConstraint",choco.it);
  choco.infeasBinTestConstraint = property::make("infeasBinTestConstraint",choco.it);
  choco.feasTupleConstraint = property::make("feasTupleConstraint",choco.it);
  choco.infeasTupleConstraint = property::make("infeasTupleConstraint",choco.it);
  choco.feasTestConstraint = property::make("feasTestConstraint",choco.it);
  choco.lcoeff = property::make("lcoeff",choco.it);
  choco.lvars = property::make("lvars",choco.it);
  choco.sumVars = property::make("sumVars",choco.it);
  choco.allDifferent = property::make("allDifferent",choco.it);
  choco.setActive = property::make("setActive",choco.it);
  choco.makeMinDomVarHeuristic = property::make("makeMinDomVarHeuristic",choco.it);
  choco.makeDomDegVarHeuristic = property::make("makeDomDegVarHeuristic",choco.it);
  choco.makeMaxDegVarHeuristic = property::make("makeMaxDegVarHeuristic",choco.it);
  choco.makeStaticVarHeuristic = property::make("makeStaticVarHeuristic",choco.it);
  choco.makeIncValIterator = property::make("makeIncValIterator",choco.it);
  choco.makeDecValIterator = property::make("makeDecValIterator",choco.it);
  choco.makeMinValSelector = property::make("makeMinValSelector",choco.it);
  choco.makeMaxValSelector = property::make("makeMaxValSelector",choco.it);
  choco.makeMidValSelector = property::make("makeMidValSelector",choco.it);
  choco.makeAssignVarBranching = property::make("makeAssignVarBranching",choco.it);
  choco.makeSplitDomBranching = property::make("makeSplitDomBranching",choco.it);
  choco.makeAssignOrForbidBranching = property::make("makeAssignOrForbidBranching",choco.it);
  choco.makeDisjunctionBranching = property::make("makeDisjunctionBranching",choco.it);
  choco.makeDefaultBranchingList = property::make("makeDefaultBranchingList",choco.it);
  choco.makeGlobalSearchSolver = property::make("makeGlobalSearchSolver",choco.it);
  choco.makeGlobalSearchMaximizer = property::make("makeGlobalSearchMaximizer",choco.it);
  choco.makeGlobalSearchMinimizer = property::make("makeGlobalSearchMinimizer",choco.it);
  choco.makeBacktrackLimit = property::make("makeBacktrackLimit",choco.it);
  choco.makeNodeLimit = property::make("makeNodeLimit",choco.it);
  choco.setSearchLimit = property::make("setSearchLimit",choco.it);
  choco.solve = property::make("solve",choco.it);
  choco.minimize = property::make("minimize",choco.it);
  choco.maximize = property::make("maximize",choco.it);
  choco.setMaxPrintDepth = property::make("setMaxPrintDepth",choco.it);
  choco.setMaxSolutionStorage = property::make("setMaxSolutionStorage",choco.it);
  choco.setVarsToShow = property::make("setVarsToShow",choco.it);
  choco.getNbSol = property::make("getNbSol",choco.it);
  choco.getGlobalSearchSolver = property::make("getGlobalSearchSolver",choco.it);
  choco.setNodeLimit = property::make("setNodeLimit",choco.it);
  choco.setTimeLimit = property::make("setTimeLimit",choco.it);
  choco.setBacktrackLimit = property::make("setBacktrackLimit",choco.it);
  choco.setMaxNbBk = property::make("setMaxNbBk",choco.it);
  choco.unassignVal = property::make("unassignVal",choco.it);
  choco.setMaxNbLocalSearch = property::make("setMaxNbLocalSearch",choco.it);
  choco.setMaxNbLocalMoves = property::make("setMaxNbLocalMoves",choco.it);
  choco.sign1 = property::make("sign1",choco.it);
  choco.sign2 = property::make("sign2",choco.it);
  choco.sumTerms = property::make("sumTerms",choco.it);
  choco.ifThen = property::make("ifThen",claire.it);
  choco.makeCardinalityConstraint = property::make("makeCardinalityConstraint",choco.it);
  choco.card = property::make("card",choco.it);
  choco.atleast = property::make("atleast",choco.it);
  choco.atmost = property::make("atmost",choco.it);
  choco.makeOccurConstraint = property::make("makeOccurConstraint",choco.it);
  choco.occur = property::make("occur",choco.it);
  choco.lvalues = property::make("lvalues",choco.it);
  choco.indexVar = property::make("indexVar",choco.it);
  choco.getNth = property::make("getNth",choco.it);
  choco.valueTable = property::make("valueTable",choco.it);
  choco.index1Var = property::make("index1Var",choco.it);
  choco.index2Var = property::make("index2Var",choco.it);
  choco.makeElt2Term = property::make("makeElt2Term",choco.it);
  
  // instructions from module sources
  { (choco.div_dash = (operation *) Kernel._operation->instantiate("div-",choco.it));
    (choco.div_dash->precedence = Kernel._7->precedence);
    ;} 
  
  { (choco.div_plus = (operation *) Kernel._operation->instantiate("div+",choco.it));
    (choco.div_plus->precedence = Kernel._7->precedence);
    ;} 
  
  choco.div_dash->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_div_dash_integer,"choco_div-_integer"));
  
  choco.div_plus->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_div_plus_integer,"choco_div+_integer"));
  
  { global_variable * _CL_obj = (choco.MAXINT = (global_variable *) Core._global_variable->instantiate("MAXINT",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,99999999);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.MININT = (global_variable *) Core._global_variable->instantiate("MININT",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,-99999999);
    close_global_variable(_CL_obj);
    } 
  
  Core.max->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._integer,
  	SAFE_RESULT,_function_(claire_max_integer2,"claire_max_integer2"));
  
  Core.min->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._integer,
  	SAFE_RESULT,_function_(claire_min_integer2,"claire_min_integer2"));
  
  Core.max->addMethod(list::domain(1,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),Kernel._integer,
  	0,_function_(claire_max_bag,"claire_max_bag"));
  
  Core.min->addMethod(list::domain(1,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),Kernel._integer,
  	0,_function_(claire_min_bag,"claire_min_bag"));
  
  choco.sum->addMethod(list::domain(1,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),Kernel._integer,
  	0,_function_(claire_sum_bag,"claire_sum_bag"));
  
  choco.product->addMethod(list::domain(1,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),Kernel._integer,
  	0,_function_(claire_product_bag,"claire_product_bag"));
  
  choco.count->addMethod(list::domain(1,Kernel._any),Kernel._integer,
  	NEW_ALLOC,_function_(claire_count_any,"claire_count_any"));
  
  choco.abs->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(claire_abs_integer,"claire_abs_integer"));
  
  choco.abs->addFloatMethod(list::domain(1,Kernel._float),Kernel._float,
  	NEW_ALLOC+RETURN_ARG,_function_(claire_abs_float,"claire_abs_float"),_function_(claire_abs_float_,"claire_abs_float_"));
  
  choco.stringFormat->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._string,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_stringFormat_integer,"claire_stringFormat_integer"));
  
  choco.stringFormat->addMethod(list::domain(2,Kernel._string,Kernel._integer),Kernel._string,
  	NEW_ALLOC,_function_(claire_stringFormat_string,"claire_stringFormat_string"));
  
  Kernel.random->addMethod(list::domain(1,Kernel._list),Kernel._any,
  	RETURN_ARG,_function_(claire_random_list,"claire_random_list"));
  
  Kernel.random->addMethod(list::domain(1,Core._Interval),Kernel._integer,
  	RETURN_ARG,_function_(claire_random_Interval,"claire_random_Interval"));
  
  Kernel.random->addMethod(list::domain(2,Kernel._integer,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(claire_random_integer2,"claire_random_integer2"));
  
  Kernel._7_plus->addMethod(list::domain(2,Core._Interval,Core._Interval),Kernel._list,
  	NEW_ALLOC,_function_(claire__7_plus_Interval1,"claire_/+_Interval1"));
  
  Kernel._7_plus->addMethod(list::domain(2,Core._Interval,Kernel._set),Kernel._list,
  	NEW_ALLOC,_function_(claire__7_plus_Interval2,"claire_/+_Interval2"));
  
  Kernel._7_plus->addMethod(list::domain(2,Kernel._set,Core._Interval),Kernel._list,
  	NEW_ALLOC,_function_(claire__7_plus_set2,"claire_/+_set2"));
  
  Kernel.integer_I->addMethod(list::domain(1,Kernel._boolean),Kernel._integer,
  	0,_function_(claire_integer_I_boolean,"claire_integer!_boolean"));
  
  { global_variable * _CL_obj = (choco.UNKNOWNINT = (global_variable *) Core._global_variable->instantiate("UNKNOWNINT",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,-100000000);
    close_global_variable(_CL_obj);
    } 
  
  choco.knownInt->addMethod(list::domain(1,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_knownInt_integer,"choco_knownInt_integer"));
  
  (choco._Util = ClaireClass::make("Util",Core._ephemeral_object,choco.it));
  
  { (choco._StoredInt = ClaireClass::make("StoredInt",choco._Util,claire.it));
    CL_ADD_SLOT(choco._StoredInt,StoredInt,choco.latestValue,latestValue,Kernel._integer,0);
    CL_ADD_SLOT(choco._StoredInt,StoredInt,choco.latestUpdate,latestUpdate,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._StoredInt),Kernel._void,
  	0,_function_(claire_self_print_StoredInt_choco,"claire_self_print_StoredInt_choco"));
  
  Core.write->addMethod(list::domain(2,choco._StoredInt,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire_write_StoredInt,"claire_write_StoredInt"));
  
  Core.read->addMethod(list::domain(1,choco._StoredInt),Kernel._integer,
  	0,_function_(claire_read_StoredInt,"claire_read_StoredInt"));
  
  { (choco._Matrix = ClaireClass::make("Matrix",choco._Util,choco.it));
    CL_ADD_SLOT(choco._Matrix,Matrix,choco.backtrackable,backtrackable,Kernel._boolean,CNULL);
    } 
  
  { (choco._Matrix2D = ClaireClass::make("Matrix2D",choco._Matrix,choco.it));
    CL_ADD_SLOT(choco._Matrix2D,Matrix2D,choco.size1,size1,Kernel._integer,-1);
    CL_ADD_SLOT(choco._Matrix2D,Matrix2D,choco.offset1,offset1,Kernel._integer,0);
    CL_ADD_SLOT(choco._Matrix2D,Matrix2D,choco.size2,size2,Kernel._integer,-1);
    CL_ADD_SLOT(choco._Matrix2D,Matrix2D,choco.offset2,offset2,Kernel._integer,0);
    } 
  
  { (choco._MatrixND = ClaireClass::make("MatrixND",choco._Matrix,choco.it));
    CL_ADD_SLOT(choco._MatrixND,MatrixND,choco.dim,dim,Kernel._integer,0);
    CL_ADD_SLOT(choco._MatrixND,MatrixND,choco.lsizes,lsizes,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(choco._MatrixND,MatrixND,choco.offsetArray,offsetArray,nth_type(Kernel._integer),CNULL);
    } 
  
  { (choco._BoolMatrix2D = ClaireClass::make("BoolMatrix2D",choco._Matrix2D,choco.it));
    CL_ADD_SLOT(choco._BoolMatrix2D,BoolMatrix2D,choco.contents,contents,nth_type(Kernel._boolean),CNULL);
    } 
  
  { (choco._BoolMatrixND = ClaireClass::make("BoolMatrixND",choco._MatrixND,choco.it));
    CL_ADD_SLOT(choco._BoolMatrixND,BoolMatrixND,choco.contents,contents,nth_type(Kernel._boolean),CNULL);
    } 
  
  { (choco._IntMatrix2D = ClaireClass::make("IntMatrix2D",choco._Matrix2D,choco.it));
    CL_ADD_SLOT(choco._IntMatrix2D,IntMatrix2D,choco.contents,contents,nth_type(Kernel._integer),CNULL);
    } 
  
  { (choco._IntMatrixND = ClaireClass::make("IntMatrixND",choco._MatrixND,choco.it));
    CL_ADD_SLOT(choco._IntMatrixND,IntMatrixND,choco.contents,contents,nth_type(Kernel._integer),CNULL);
    } 
  
  choco.make2DBoolMatrix->addMethod(list::domain(6,Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean),choco._BoolMatrix2D,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_make2DBoolMatrix_integer,"choco_make2DBoolMatrix_integer"));
  
  choco.make2DIntMatrix->addMethod(list::domain(6,Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean),choco._IntMatrix2D,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_make2DIntMatrix_integer,"choco_make2DIntMatrix_integer"));
  
  choco.makeNDBoolMatrix->addMethod(list::domain(3,nth_class1(Kernel._list,Core._Interval),Kernel._boolean,Kernel._boolean),choco._BoolMatrixND,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeNDBoolMatrix_list,"choco_makeNDBoolMatrix_list"));
  
  choco.makeNDIntMatrix->addMethod(list::domain(3,nth_class1(Kernel._list,Core._Interval),Kernel._integer,Kernel._boolean),choco._IntMatrixND,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeNDIntMatrix_list,"choco_makeNDIntMatrix_list"));
  
  choco.flatIndex->addMethod(list::domain(3,choco._Matrix2D,Kernel._integer,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_flatIndex_Matrix2D,"choco_flatIndex_Matrix2D"));
  
  Core.read->addMethod(list::domain(3,choco._BoolMatrix2D,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_read_BoolMatrix2D,"claire_read_BoolMatrix2D"));
  
  Kernel.store->addMethod(list::domain(4,choco._BoolMatrix2D,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_store_BoolMatrix2D,"claire_store_BoolMatrix2D"));
  
  Core.read->addMethod(list::domain(3,choco._IntMatrix2D,Kernel._integer,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_read_IntMatrix2D,"claire_read_IntMatrix2D"));
  
  Kernel.store->addMethod(list::domain(4,choco._IntMatrix2D,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_store_IntMatrix2D,"claire_store_IntMatrix2D"));
  
  choco.flatIndex->addMethod(list::domain(2,choco._MatrixND,nth_class1(Kernel._list,Kernel._integer)),Kernel._integer,
  	RETURN_ARG,_function_(choco_flatIndex_MatrixND,"choco_flatIndex_MatrixND"));
  
  Core.read->addMethod(list::domain(2,choco._BoolMatrixND,nth_class1(Kernel._list,Kernel._integer)),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_read_BoolMatrixND,"claire_read_BoolMatrixND"));
  
  Kernel.store->addMethod(list::domain(3,choco._BoolMatrixND,nth_class1(Kernel._list,Kernel._integer),Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_store_BoolMatrixND,"claire_store_BoolMatrixND"));
  
  Core.read->addMethod(list::domain(2,choco._IntMatrixND,nth_class1(Kernel._list,Kernel._integer)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_read_IntMatrixND,"claire_read_IntMatrixND"));
  
  Kernel.store->addMethod(list::domain(3,choco._IntMatrixND,nth_class1(Kernel._list,Kernel._integer),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_store_IntMatrixND,"claire_store_IntMatrixND"));
  
  { (choco._IntSetAnnotation = ClaireClass::make("IntSetAnnotation",choco._Util,choco.it));
    CL_ADD_SLOT(choco._IntSetAnnotation,IntSetAnnotation,choco.offset,offset,Kernel._integer,0);
    CL_ADD_SLOT(choco._IntSetAnnotation,IntSetAnnotation,choco.asize,asize,Kernel._integer,0);
    } 
  
  choco.getOriginalMin->addMethod(list::domain(1,choco._IntSetAnnotation),Kernel._integer,
  	0,_function_(choco_getOriginalMin_IntSetAnnotation,"choco_getOriginalMin_IntSetAnnotation"));
  
  choco.getOriginalMax->addMethod(list::domain(1,choco._IntSetAnnotation),Kernel._integer,
  	RETURN_ARG,_function_(choco_getOriginalMax_IntSetAnnotation,"choco_getOriginalMax_IntSetAnnotation"));
  
  { (choco._IntSetIntAnnotation = ClaireClass::make("IntSetIntAnnotation",choco._IntSetAnnotation,choco.it));
    CL_ADD_SLOT(choco._IntSetIntAnnotation,IntSetIntAnnotation,choco.intValues,intValues,nth_type(Kernel._integer),CNULL);
    } 
  
  choco.getIntAnnotation->addMethod(list::domain(2,choco._IntSetIntAnnotation,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_getIntAnnotation_IntSetIntAnnotation,"choco_getIntAnnotation_IntSetIntAnnotation"));
  
  choco.setIntAnnotation->addMethod(list::domain(3,choco._IntSetIntAnnotation,Kernel._integer,Kernel._integer),Kernel._void,
  	RETURN_ARG,_function_(choco_setIntAnnotation_IntSetIntAnnotation,"choco_setIntAnnotation_IntSetIntAnnotation"));
  
  choco.makeIntSetIntAnnotation->addMethod(list::domain(3,Kernel._integer,Kernel._integer,Kernel._integer),choco._IntSetIntAnnotation,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeIntSetIntAnnotation_integer,"choco_makeIntSetIntAnnotation_integer"));
  
  { (choco._IntSetBoolAnnotation = ClaireClass::make("IntSetBoolAnnotation",choco._IntSetAnnotation,choco.it));
    CL_ADD_SLOT(choco._IntSetBoolAnnotation,IntSetBoolAnnotation,choco.boolValues,boolValues,nth_type(Kernel._boolean),CNULL);
    } 
  
  choco.getBoolAnnotation->addMethod(list::domain(2,choco._IntSetBoolAnnotation,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_getBoolAnnotation_IntSetBoolAnnotation,"choco_getBoolAnnotation_IntSetBoolAnnotation"));
  
  choco.setBoolAnnotation->addMethod(list::domain(3,choco._IntSetBoolAnnotation,Kernel._integer,Kernel._boolean),Kernel._void,
  	RETURN_ARG,_function_(choco_setBoolAnnotation_IntSetBoolAnnotation,"choco_setBoolAnnotation_IntSetBoolAnnotation"));
  
  choco.makeIntSetBoolAnnotation->addMethod(list::domain(3,Kernel._integer,Kernel._integer,Kernel._boolean),choco._IntSetBoolAnnotation,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeIntSetBoolAnnotation_integer,"choco_makeIntSetBoolAnnotation_integer"));
  
  { (choco._BipartiteSet = ClaireClass::make("BipartiteSet",choco._Util,choco.it));
    CL_ADD_SLOT(choco._BipartiteSet,BipartiteSet,choco.defaultValue,defaultValue,Kernel._any,CNULL);
    CL_ADD_SLOT(choco._BipartiteSet,BipartiteSet,choco.objs,objs,Kernel._array,CNULL);
    CL_ADD_SLOT(choco._BipartiteSet,BipartiteSet,choco.nbLeft,nbLeft,Kernel._integer,0);
    CL_ADD_SLOT(choco._BipartiteSet,BipartiteSet,choco.indices,indices,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  choco.makeIndexesTable->addMethod(list::domain(1,Kernel._type),nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(Kernel.emptySet,1,_oid_(set::alloc(Kernel.emptySet,1,_oid_(Kernel._integer))))),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeIndexesTable_type,"choco_makeIndexesTable_type"));
  
  choco.makeBipartiteSet->addMethod(list::domain(2,Kernel._type,Kernel._any),choco._BipartiteSet,
  	NEW_ALLOC,_function_(choco_makeBipartiteSet_type,"choco_makeBipartiteSet_type"));
  
  choco.swap->addMethod(list::domain(3,choco._BipartiteSet,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_swap_BipartiteSet,"choco_swap_BipartiteSet"));
  
  choco.moveLeft->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_moveLeft_BipartiteSet,"choco_moveLeft_BipartiteSet"));
  
  choco.moveRight->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_moveRight_BipartiteSet,"choco_moveRight_BipartiteSet"));
  
  choco.moveAllLeft->addMethod(list::domain(1,choco._BipartiteSet),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_moveAllLeft_BipartiteSet,"choco_moveAllLeft_BipartiteSet"));
  
  choco.moveAllRight->addMethod(list::domain(1,choco._BipartiteSet),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_moveAllRight_BipartiteSet,"choco_moveAllRight_BipartiteSet"));
  
  choco.addRight->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_addRight_BipartiteSet,"choco_addRight_BipartiteSet"));
  
  choco.addLeft->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_addLeft_BipartiteSet,"choco_addLeft_BipartiteSet"));
  
  choco.isLeft->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._boolean,
  	0,_function_(choco_isLeft_BipartiteSet,"choco_isLeft_BipartiteSet"));
  
  choco.isIn->addMethod(list::domain(2,choco._BipartiteSet,Kernel._any),Kernel._boolean,
  	0,_function_(choco_isIn_BipartiteSet,"choco_isIn_BipartiteSet"));
  
  choco.getNbLeft->addMethod(list::domain(1,choco._BipartiteSet),Kernel._integer,
  	0,_function_(choco_getNbLeft_BipartiteSet,"choco_getNbLeft_BipartiteSet"));
  
  choco.getNbRight->addMethod(list::domain(1,choco._BipartiteSet),Kernel._integer,
  	RETURN_ARG,_function_(choco_getNbRight_BipartiteSet,"choco_getNbRight_BipartiteSet"));
  
  choco.getNbObjects->addMethod(list::domain(1,choco._BipartiteSet),Kernel._integer,
  	0,_function_(choco_getNbObjects_BipartiteSet,"choco_getNbObjects_BipartiteSet"));
  
  choco.getObject->addMethod(list::domain(2,choco._BipartiteSet,Kernel._integer),Kernel._any,
  	RETURN_ARG,_function_(choco_getObject_BipartiteSet,"choco_getObject_BipartiteSet"));
  
  choco.leftPart->addMethod(list::domain(1,choco._BipartiteSet),Kernel._set,
  	NEW_ALLOC,_function_(choco_leftPart_BipartiteSet,"choco_leftPart_BipartiteSet"));
  
  choco.rightPart->addMethod(list::domain(1,choco._BipartiteSet),Kernel._set,
  	NEW_ALLOC,_function_(choco_rightPart_BipartiteSet,"choco_rightPart_BipartiteSet"));
  
  ;
  ;
  choco.getBitCount->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getBitCount_integer,"choco_getBitCount_integer"));
  
  choco.getMinBitIndex->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getMinBitIndex_integer,"choco_getMinBitIndex_integer"));
  
  choco.getMaxBitIndex->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getMaxBitIndex_integer,"choco_getMaxBitIndex_integer"));
  
  choco.makeAllOnesBitVector->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_makeAllOnesBitVector_integer,"choco_makeAllOnesBitVector_integer"));
  
  choco.getBitVectorList->addMethod(list::domain(2,Kernel._integer,param_I_class(Kernel._list,Kernel._integer)),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getBitVectorList_integer,"choco_getBitVectorList_integer"));
  
  choco.isInBitVectorList->addMethod(list::domain(2,Kernel._integer,param_I_class(Kernel._list,Kernel._integer)),Kernel._boolean,
  	RETURN_ARG,_function_(choco_isInBitVectorList_integer,"choco_isInBitVectorList_integer"));
  
  choco.addToBitVectorList->addMethod(list::domain(2,Kernel._integer,param_I_class(Kernel._list,Kernel._integer)),Kernel._boolean,
  	BAG_UPDATE,_function_(choco_addToBitVectorList_integer,"choco_addToBitVectorList_integer"));
  
  choco.remFromBitVectorList->addMethod(list::domain(2,Kernel._integer,param_I_class(Kernel._list,Kernel._integer)),Kernel._boolean,
  	BAG_UPDATE,_function_(choco_remFromBitVectorList_integer,"choco_remFromBitVectorList_integer"));
  
  choco.getBitVectorListCount->addMethod(list::domain(1,param_I_class(Kernel._list,Kernel._integer)),Kernel._integer,
  	0,_function_(choco_getBitVectorListCount_list,"choco_getBitVectorListCount_list"));
  
  choco.getBitVectorInf->addMethod(list::domain(1,param_I_class(Kernel._list,Kernel._integer)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getBitVectorInf_list,"choco_getBitVectorInf_list"));
  
  choco.getBitVectorSup->addMethod(list::domain(1,param_I_class(Kernel._list,Kernel._integer)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getBitVectorSup_list,"choco_getBitVectorSup_list"));
  
  { global_variable * _CL_obj = (choco.CHOCO_RELEASE = (global_variable *) Core._global_variable->instantiate("CHOCO_RELEASE",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _float_(13.024));
    close_global_variable(_CL_obj);
    } 
  
  choco.showChocoLicense->addMethod(list::domain(1,Kernel._void),Kernel._void,
  	NEW_ALLOC,_function_(choco_showChocoLicense_void,"choco_showChocoLicense_void"));
  
  choco_showChocoLicense_void();
  
  { global_variable * _CL_obj = (choco.EXTENDABLE_CHOCO = (global_variable *) Core._global_variable->instantiate("EXTENDABLE_CHOCO",claire.it));
    (_CL_obj->range = Kernel._boolean);
    STOREI(_CL_obj->value,Kernel.ctrue);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.DDEBUG = (global_variable *) Core._global_variable->instantiate("DDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,6);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.PVIEW = (global_variable *) Core._global_variable->instantiate("PVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.PTALK = (global_variable *) Core._global_variable->instantiate("PTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,5);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.PDEBUG = (global_variable *) Core._global_variable->instantiate("PDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,6);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.GPVIEW = (global_variable *) Core._global_variable->instantiate("GPVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.GPTALK = (global_variable *) Core._global_variable->instantiate("GPTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.GPDEBUG = (global_variable *) Core._global_variable->instantiate("GPDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.SVIEW = (global_variable *) Core._global_variable->instantiate("SVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.STALK = (global_variable *) Core._global_variable->instantiate("STALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.SDEBUG = (global_variable *) Core._global_variable->instantiate("SDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.IVIEW = (global_variable *) Core._global_variable->instantiate("IVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.ITALK = (global_variable *) Core._global_variable->instantiate("ITALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.IDEBUG = (global_variable *) Core._global_variable->instantiate("IDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.LVIEW = (global_variable *) Core._global_variable->instantiate("LVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.LTALK = (global_variable *) Core._global_variable->instantiate("LTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,5);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.LDEBUG = (global_variable *) Core._global_variable->instantiate("LDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,6);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.CHOCOBENCH_VIEW = (global_variable *) Core._global_variable->instantiate("CHOCOBENCH_VIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,-1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.CHOCOTEST_VIEW = (global_variable *) Core._global_variable->instantiate("CHOCOTEST_VIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,-1);
    close_global_variable(_CL_obj);
    } 
  
  (choco._Ephemeral = ClaireClass::make("Ephemeral",Core._ephemeral_object,choco.it));
  
  (choco._Problem = ClaireClass::make("Problem",choco._Ephemeral,choco.it));
  
  (choco._Solver = ClaireClass::make("Solver",choco._Ephemeral,choco.it));
  
  (choco._LocalSearchSolver = ClaireClass::make("LocalSearchSolver",choco._Solver,choco.it));
  
  (choco._GlobalSearchSolver = ClaireClass::make("GlobalSearchSolver",choco._Solver,choco.it));
  
  (choco._AbstractConstraint = ClaireClass::make("AbstractConstraint",choco._Ephemeral,choco.it));
  
  (choco._IntConstraint = ClaireClass::make("IntConstraint",choco._AbstractConstraint,choco.it));
  
  (choco._AbstractVar = ClaireClass::make("AbstractVar",choco._Ephemeral,choco.it));
  
  (choco._IntVar = ClaireClass::make("IntVar",choco._AbstractVar,choco.it));
  
  (choco._SetVar = ClaireClass::make("SetVar",choco._AbstractVar,choco.it));
  
  (choco._PropagationEvent = ClaireClass::make("PropagationEvent",choco._Ephemeral,choco.it));
  
  (choco._ConstAwakeEvent = ClaireClass::make("ConstAwakeEvent",choco._PropagationEvent,choco.it));
  
  (choco._VarEvent = ClaireClass::make("VarEvent",choco._PropagationEvent,choco.it));
  
  (choco._Instantiation = ClaireClass::make("Instantiation",choco._VarEvent,choco.it));
  
  (choco._InstInt = ClaireClass::make("InstInt",choco._Instantiation,choco.it));
  
  (choco._InstSet = ClaireClass::make("InstSet",choco._Instantiation,choco.it));
  
  (choco._ValueRemovals = ClaireClass::make("ValueRemovals",choco._VarEvent,choco.it));
  
  (choco._BoundUpdate = ClaireClass::make("BoundUpdate",choco._VarEvent,choco.it));
  
  (choco._IncInf = ClaireClass::make("IncInf",choco._BoundUpdate,choco.it));
  
  (choco._DecSup = ClaireClass::make("DecSup",choco._BoundUpdate,choco.it));
  
  (choco._IncKer = ClaireClass::make("IncKer",choco._BoundUpdate,choco.it));
  
  (choco._DevEnv = ClaireClass::make("DevEnv",choco._BoundUpdate,choco.it));
  
  (choco._LogicEngine = ClaireClass::make("LogicEngine",choco._Ephemeral,choco.it));
  
  (choco._PropagationEngine = ClaireClass::make("PropagationEngine",choco._LogicEngine,choco.it));
  
  (choco._InvariantEngine = ClaireClass::make("InvariantEngine",choco._LogicEngine,choco.it));
  
  (choco._AbstractBranching = ClaireClass::make("AbstractBranching",choco._Ephemeral,choco.it));
  
  (choco._GlobalSearchLimit = ClaireClass::make("GlobalSearchLimit",choco._Ephemeral,choco.it));
  
  (choco._ConstructiveHeuristic = ClaireClass::make("ConstructiveHeuristic",choco._Ephemeral,choco.it));
  
  (choco._MoveNeighborhood = ClaireClass::make("MoveNeighborhood",choco._Ephemeral,choco.it));
  
  { (choco._Solution = ClaireClass::make("Solution",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._Solution,Solution,choco.algo,algo,choco._Solver,CNULL);
    CL_ADD_SLOT(choco._Solution,Solution,choco.time,time,Kernel._integer,0);
    CL_ADD_SLOT(choco._Solution,Solution,choco.nbBk,nbBk,Kernel._integer,0);
    CL_ADD_SLOT(choco._Solution,Solution,choco.nbNd,nbNd,Kernel._integer,0);
    CL_ADD_SLOT(choco._Solution,Solution,choco.objectiveValue,objectiveValue,Kernel._integer,99999999);
    CL_ADD_SLOT(choco._Solution,Solution,choco.lval,lval,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,GC_OID(_oid_(U_type(Kernel._integer,set::alloc(Kernel.emptySet,1,CNULL)))))))),CNULL);
    } 
  
  choco.makeSolution->addMethod(list::domain(2,choco._Solver,Kernel._integer),choco._Solution,
  	NEW_ALLOC,_function_(choco_makeSolution_Solver,"choco_makeSolution_Solver"));
  
  { (choco._Problem = ClaireClass::make("Problem",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._Problem,Problem,choco.constraints,constraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._AbstractConstraint))))),CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,Core.vars,vars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,choco.setVars,setVars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._SetVar))))),CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,Kernel.name,name,Kernel._string,_string_(""));
    CL_ADD_SLOT(choco._Problem,Problem,choco.feasible,feasible,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(choco._Problem,Problem,choco.solved,solved,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(choco._Problem,Problem,choco.feasibleMode,feasibleMode,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._Problem,Problem,choco.propagationEngine,propagationEngine,choco._PropagationEngine,CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,choco.globalSearchSolver,globalSearchSolver,choco._GlobalSearchSolver,CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,choco.invariantEngine,invariantEngine,choco._InvariantEngine,CNULL);
    CL_ADD_SLOT(choco._Problem,Problem,choco.localSearchSolver,localSearchSolver,choco._LocalSearchSolver,CNULL);
    } 
  
  (*Kernel.store)(_oid_(choco.feasibleMode));
  
  { global_variable * _CL_obj = (choco.CURRENT_PB = (global_variable *) Core._global_variable->instantiate("CURRENT_PB",choco.it));
    (_CL_obj->range = Kernel._any);
    STOREI(_CL_obj->value,CNULL);
    close_global_variable(_CL_obj);
    } 
  
  choco.getIntVar->addMethod(list::domain(2,choco._Problem,Kernel._integer),choco._IntVar,
  	RETURN_ARG,_function_(choco_getIntVar_Problem,"choco_getIntVar_Problem"));
  
  choco.getSetVar->addMethod(list::domain(2,choco._Problem,Kernel._integer),choco._SetVar,
  	RETURN_ARG,_function_(choco_getSetVar_Problem,"choco_getSetVar_Problem"));
  
  { (choco.setActiveProblem = property::make("setActiveProblem",1,choco.it,Kernel._any,0));
    ;} 
  
  { (choco.getActiveProblem = property::make("getActiveProblem",1,choco.it,Kernel._any,0));
    ;} 
  
  { (choco._LogicEngine = ClaireClass::make("LogicEngine",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._LogicEngine,LogicEngine,choco.problem,problem,choco._Problem,CNULL);
    } 
  
  { (choco._PropagationEngine = ClaireClass::make("PropagationEngine",choco._LogicEngine,choco.it));
    CL_ADD_SLOT(choco._PropagationEngine,PropagationEngine,choco.maxSize,maxSize,Kernel._integer,100);
    CL_ADD_SLOT(choco._PropagationEngine,PropagationEngine,choco.propagationOverflow,propagationOverflow,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(choco._PropagationEngine,PropagationEngine,choco.contradictionCause,contradictionCause,choco._Ephemeral,CNULL);
    } 
  
  (choco._InvariantEngine = ClaireClass::make("InvariantEngine",choco._LogicEngine,choco.it));
  
  { (choco._ConflictCount = ClaireClass::make("ConflictCount",choco._InvariantEngine,choco.it));
    CL_ADD_SLOT(choco._ConflictCount,ConflictCount,choco.nbViolatedConstraints,nbViolatedConstraints,Kernel._integer,0);
    CL_ADD_SLOT(choco._ConflictCount,ConflictCount,choco.minNbViolatedConstraints,minNbViolatedConstraints,Kernel._integer,99999999);
    CL_ADD_SLOT(choco._ConflictCount,ConflictCount,choco.assignedThenUnassignedVars,assignedThenUnassignedVars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    CL_ADD_SLOT(choco._ConflictCount,ConflictCount,choco.lastAssignedVar,lastAssignedVar,Kernel._integer,0);
    CL_ADD_SLOT(choco._ConflictCount,ConflictCount,choco.conflictingVars,conflictingVars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  { (choco._Solver = ClaireClass::make("Solver",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._Solver,Solver,choco.problem,problem,choco._Problem,CNULL);
    CL_ADD_SLOT(choco._Solver,Solver,choco.solutions,solutions,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._Solution))))),CNULL);
    CL_ADD_SLOT(choco._Solver,Solver,choco.varsToStore,varsToStore,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    CL_ADD_SLOT(choco._Solver,Solver,choco.varsToShow,varsToShow,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  (choco._GlobalSearchLimit = ClaireClass::make("GlobalSearchLimit",choco._Ephemeral,choco.it));
  
  { (choco._NodeLimit = ClaireClass::make("NodeLimit",choco._GlobalSearchLimit,choco.it));
    CL_ADD_SLOT(choco._NodeLimit,NodeLimit,choco.maxNb,maxNb,Kernel._integer,100000);
    } 
  
  { (choco._BacktrackLimit = ClaireClass::make("BacktrackLimit",choco._GlobalSearchLimit,choco.it));
    CL_ADD_SLOT(choco._BacktrackLimit,BacktrackLimit,choco.maxNb,maxNb,Kernel._integer,100000);
    } 
  
  { (choco._TimeLimit = ClaireClass::make("TimeLimit",choco._GlobalSearchLimit,choco.it));
    CL_ADD_SLOT(choco._TimeLimit,TimeLimit,choco.maxMsec,maxMsec,Kernel._integer,3600000);
    } 
  
  { (choco._GlobalSearchSolver = ClaireClass::make("GlobalSearchSolver",choco._Solver,choco.it));
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.stopAtFirstSol,stopAtFirstSol,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.nbSol,nbSol,Kernel._integer,0);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.nbBk,nbBk,Kernel._integer,0);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.nbNd,nbNd,Kernel._integer,0);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.maxNbBk,maxNbBk,Kernel._integer,100000);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.maxPrintDepth,maxPrintDepth,Kernel._integer,5);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.maxSolutionStorage,maxSolutionStorage,Kernel._integer,0);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.branchingComponents,branchingComponents,nth_class1(Kernel._list,choco._AbstractBranching),CNULL);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.baseWorld,baseWorld,Kernel._integer,0);
    CL_ADD_SLOT(choco._GlobalSearchSolver,GlobalSearchSolver,choco.limits,limits,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._GlobalSearchLimit))))),CNULL);
    } 
  
  { (choco._AbstractBranching = ClaireClass::make("AbstractBranching",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._AbstractBranching,AbstractBranching,choco.manager,manager,choco._GlobalSearchSolver,CNULL);
    CL_ADD_SLOT(choco._AbstractBranching,AbstractBranching,choco.nextBranching,nextBranching,choco._AbstractBranching,CNULL);
    } 
  
  { (choco._LocalSearchSolver = ClaireClass::make("LocalSearchSolver",choco._Solver,choco.it));
    CL_ADD_SLOT(choco._LocalSearchSolver,LocalSearchSolver,choco.buildAssignment,buildAssignment,choco._ConstructiveHeuristic,CNULL);
    CL_ADD_SLOT(choco._LocalSearchSolver,LocalSearchSolver,choco.changeAssignment,changeAssignment,choco._MoveNeighborhood,CNULL);
    CL_ADD_SLOT(choco._LocalSearchSolver,LocalSearchSolver,choco.maxNbLocalSearch,maxNbLocalSearch,Kernel._integer,20);
    CL_ADD_SLOT(choco._LocalSearchSolver,LocalSearchSolver,choco.maxNbLocalMoves,maxNbLocalMoves,Kernel._integer,5000);
    } 
  
  (*Kernel.store)(_oid_(choco.assignedThenUnassignedVars),
    _oid_(choco.lastAssignedVar));
  
  { (choco._ConstructiveHeuristic = ClaireClass::make("ConstructiveHeuristic",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._ConstructiveHeuristic,ConstructiveHeuristic,choco.manager,manager,choco._LocalSearchSolver,CNULL);
    } 
  
  { (choco._MoveNeighborhood = ClaireClass::make("MoveNeighborhood",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._MoveNeighborhood,MoveNeighborhood,choco.manager,manager,choco._LocalSearchSolver,CNULL);
    } 
  
  (choco._AbstractDomain = ClaireClass::make("AbstractDomain",Kernel._collection,choco.it));
  
  ephemeral_class(choco._AbstractDomain);
  
  (choco._AbstractIntDomain = ClaireClass::make("AbstractIntDomain",choco._AbstractDomain,choco.it));
  
  { (choco.containsValInDomain = property::make("containsValInDomain",3,choco.it,choco._AbstractIntDomain,11));
    ;} 
  
  { (choco.remove = property::make("remove",1,choco.it,Kernel._any,0));
    ;} 
  
  { (choco.restrict = property::make("restrict",3,choco.it,choco._AbstractIntDomain,13));
    ;} 
  
  (choco._AbstractSetDomain = ClaireClass::make("AbstractSetDomain",choco._AbstractDomain,choco.it));
  
  { (choco.getDomainCard = property::make("getDomainCard",3,choco.it,choco._AbstractIntDomain,14));
    (choco.getDomainCard->range = Kernel._integer);
    ;} 
  
  { (choco.getNextValue = property::make("getNextValue",3,choco.it,choco._AbstractIntDomain,15));
    (choco.getNextValue->range = Kernel._integer);
    ;} 
  
  { (choco.getPrevValue = property::make("getPrevValue",3,choco.it,choco._AbstractIntDomain,16));
    (choco.getPrevValue->range = Kernel._integer);
    ;} 
  
  { (choco.removeDomainVal = property::make("removeDomainVal",3,choco.it,choco._AbstractIntDomain,12));
    (choco.removeDomainVal->range = Kernel._boolean);
    ;} 
  
  { (choco.domainSequence = property::make("domainSequence",3,choco.it,choco._AbstractIntDomain,3));
    (choco.domainSequence->range = nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))));
    ;} 
  
  { (choco.domainSet = property::make("domainSet",3,choco.it,choco._AbstractIntDomain,6));
    (choco.domainSet->range = nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))));
    ;} 
  
  choco.domainSequence->addMethod(list::domain(1,choco._AbstractIntDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSequence_AbstractIntDomain_choco,"choco_domainSequence_AbstractIntDomain_choco"));
  
  choco.domainSet->addMethod(list::domain(1,choco._AbstractIntDomain),param_I_class(Kernel._set,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSet_AbstractIntDomain_choco,"choco_domainSet_AbstractIntDomain_choco"));
  
  choco.getDomainInf->addMethod(list::domain(1,choco._AbstractIntDomain),Kernel._integer,
  	0,_function_(choco_getDomainInf_AbstractIntDomain_choco,"choco_getDomainInf_AbstractIntDomain_choco"));
  
  choco.getDomainSup->addMethod(list::domain(1,choco._AbstractIntDomain),Kernel._integer,
  	0,_function_(choco_getDomainSup_AbstractIntDomain_choco,"choco_getDomainSup_AbstractIntDomain_choco"));
  
  choco.updateDomainInf->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._integer,
  	0,_function_(choco_updateDomainInf_AbstractIntDomain_choco,"choco_updateDomainInf_AbstractIntDomain_choco"));
  
  choco.updateDomainSup->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._integer,
  	0,_function_(choco_updateDomainSup_AbstractIntDomain_choco,"choco_updateDomainSup_AbstractIntDomain_choco"));
  
  choco.containsValInDomain->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_containsValInDomain_AbstractIntDomain_choco,"choco_containsValInDomain_AbstractIntDomain_choco"));
  
  choco.removeDomainVal->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_removeDomainVal_AbstractIntDomain_choco,"choco_removeDomainVal_AbstractIntDomain_choco"));
  
  choco.restrict->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_restrict_AbstractIntDomain_choco,"choco_restrict_AbstractIntDomain_choco"));
  
  choco.getDomainCard->addMethod(list::domain(1,choco._AbstractIntDomain),Kernel._integer,
  	0,_function_(choco_getDomainCard_AbstractIntDomain_choco,"choco_getDomainCard_AbstractIntDomain_choco"));
  
  choco.getNextValue->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getNextValue_AbstractIntDomain_choco,"choco_getNextValue_AbstractIntDomain_choco"));
  
  choco.getPrevValue->addMethod(list::domain(2,choco._AbstractIntDomain,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getPrevValue_AbstractIntDomain_choco,"choco_getPrevValue_AbstractIntDomain_choco"));
  
  { ;} 
  
  ;
  { global_variable * _CL_obj = (choco.DINF = (global_variable *) Core._global_variable->instantiate("DINF",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,-1000000);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.DSUP = (global_variable *) Core._global_variable->instantiate("DSUP",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,1000000);
    close_global_variable(_CL_obj);
    } 
  
  { (choco._LinkedListIntDomain = ClaireClass::make("LinkedListIntDomain",choco._AbstractIntDomain,choco.it));
    CL_ADD_SLOT(choco._LinkedListIntDomain,LinkedListIntDomain,choco.offset,offset,Kernel._integer,0);
    CL_ADD_SLOT(choco._LinkedListIntDomain,LinkedListIntDomain,choco.bucketSize,bucketSize,Kernel._integer,0);
    CL_ADD_SLOT(choco._LinkedListIntDomain,LinkedListIntDomain,choco.contents,contents,nth_type(Kernel._integer),CNULL);
    } 
  
  (*Kernel.store)(_oid_(choco.bucketSize));
  
  Kernel.self_print->addMethod(list::domain(1,choco._LinkedListIntDomain),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_LinkedListIntDomain_choco,"claire_self_print_LinkedListIntDomain_choco"));
  
  choco.makeLinkedListIntDomain->addMethod(list::domain(2,Kernel._integer,Kernel._integer),choco._LinkedListIntDomain,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeLinkedListIntDomain_integer,"choco_makeLinkedListIntDomain_integer"));
  
  Kernel.random->addMethod(list::domain(1,choco._LinkedListIntDomain),Kernel._integer,
  	RETURN_ARG,_function_(claire_random_LinkedListIntDomain,"claire_random_LinkedListIntDomain"));
  
  choco.domainSet->addMethod(list::domain(1,choco._LinkedListIntDomain),param_I_class(Kernel._set,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSet_LinkedListIntDomain_choco,"choco_domainSet_LinkedListIntDomain_choco"));
  
  choco.domainSequence->addMethod(list::domain(1,choco._LinkedListIntDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSequence_LinkedListIntDomain_choco,"choco_domainSequence_LinkedListIntDomain_choco"));
  
  choco.getDomainInf->addMethod(list::domain(1,choco._LinkedListIntDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getDomainInf_LinkedListIntDomain_choco,"choco_getDomainInf_LinkedListIntDomain_choco"));
  
  choco.getDomainSup->addMethod(list::domain(1,choco._LinkedListIntDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getDomainSup_LinkedListIntDomain_choco,"choco_getDomainSup_LinkedListIntDomain_choco"));
  
  choco.isIncludedIn->addMethod(list::domain(2,choco._LinkedListIntDomain,nth_class1(Kernel._list,Kernel._integer)),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_isIncludedIn_LinkedListIntDomain,"choco_isIncludedIn_LinkedListIntDomain"));
  
  choco.getDomainCard->addMethod(list::domain(1,choco._LinkedListIntDomain),Kernel._integer,
  	0,_function_(choco_getDomainCard_LinkedListIntDomain_choco,"choco_getDomainCard_LinkedListIntDomain_choco"));
  
  choco.containsValInDomain->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_containsValInDomain_LinkedListIntDomain_choco,"choco_containsValInDomain_LinkedListIntDomain_choco"));
  
  choco.getNextValue->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_getNextValue_LinkedListIntDomain_choco,"choco_getNextValue_LinkedListIntDomain_choco"));
  
  choco.getPrevValue->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_getPrevValue_LinkedListIntDomain_choco,"choco_getPrevValue_LinkedListIntDomain_choco"));
  
  choco.removeDomainVal->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_removeDomainVal_LinkedListIntDomain_choco,"choco_removeDomainVal_LinkedListIntDomain_choco"));
  
  choco.restrict->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._void,
  	SLOT_UPDATE,_function_(choco_restrict_LinkedListIntDomain_choco,"choco_restrict_LinkedListIntDomain_choco"));
  
  choco.updateDomainInf->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._integer,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_updateDomainInf_LinkedListIntDomain_choco,"choco_updateDomainInf_LinkedListIntDomain_choco"));
  
  choco.updateDomainSup->addMethod(list::domain(2,choco._LinkedListIntDomain,Kernel._integer),Kernel._integer,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_updateDomainSup_LinkedListIntDomain_choco,"choco_updateDomainSup_LinkedListIntDomain_choco"));
  
  { (choco._AbstractSetDomain = ClaireClass::make("AbstractSetDomain",choco._AbstractDomain,choco.it));
    CL_ADD_SLOT(choco._AbstractSetDomain,AbstractSetDomain,choco.minValue,minValue,Kernel._integer,0);
    CL_ADD_SLOT(choco._AbstractSetDomain,AbstractSetDomain,choco.kernelSize,kernelSize,Kernel._integer,0);
    CL_ADD_SLOT(choco._AbstractSetDomain,AbstractSetDomain,choco.enveloppeSize,enveloppeSize,Kernel._integer,0);
    } 
  
  (*Kernel.store)(_oid_(choco.kernelSize),
    _oid_(choco.enveloppeSize));
  
  choco.getKernel->addMethod(list::domain(1,choco._AbstractSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getKernel_AbstractSetDomain,"choco_getKernel_AbstractSetDomain"));
  
  choco.getEnveloppe->addMethod(list::domain(1,choco._AbstractSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getEnveloppe_AbstractSetDomain,"choco_getEnveloppe_AbstractSetDomain"));
  
  choco.getKernelSize->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getKernelSize_AbstractSetDomain,"choco_getKernelSize_AbstractSetDomain"));
  
  choco.getEnveloppeSize->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getEnveloppeSize_AbstractSetDomain,"choco_getEnveloppeSize_AbstractSetDomain"));
  
  choco.getKernelInf->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getKernelInf_AbstractSetDomain,"choco_getKernelInf_AbstractSetDomain"));
  
  choco.getKernelSup->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getKernelSup_AbstractSetDomain,"choco_getKernelSup_AbstractSetDomain"));
  
  choco.getEnveloppeInf->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getEnveloppeInf_AbstractSetDomain,"choco_getEnveloppeInf_AbstractSetDomain"));
  
  choco.getEnveloppeSup->addMethod(list::domain(1,choco._AbstractSetDomain),Kernel._integer,
  	0,_function_(choco_getEnveloppeSup_AbstractSetDomain,"choco_getEnveloppeSup_AbstractSetDomain"));
  
  choco.isInEnveloppe->addMethod(list::domain(2,choco._AbstractSetDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isInEnveloppe_AbstractSetDomain,"choco_isInEnveloppe_AbstractSetDomain"));
  
  choco.isInKernel->addMethod(list::domain(2,choco._AbstractSetDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isInKernel_AbstractSetDomain,"choco_isInKernel_AbstractSetDomain"));
  
  choco.updateKernel->addMethod(list::domain(2,choco._AbstractSetDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_updateKernel_AbstractSetDomain,"choco_updateKernel_AbstractSetDomain"));
  
  choco.updateEnveloppe->addMethod(list::domain(2,choco._AbstractSetDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_updateEnveloppe_AbstractSetDomain,"choco_updateEnveloppe_AbstractSetDomain"));
  
  { ;} 
  
  ;
  { (choco._BitSetDomain = ClaireClass::make("BitSetDomain",choco._AbstractSetDomain,choco.it));
    CL_ADD_SLOT(choco._BitSetDomain,BitSetDomain,choco.kernelBitVector,kernelBitVector,Kernel._integer,CNULL);
    CL_ADD_SLOT(choco._BitSetDomain,BitSetDomain,choco.enveloppeBitVector,enveloppeBitVector,Kernel._integer,CNULL);
    } 
  
  (*Kernel.store)(_oid_(choco.kernelBitVector),
    _oid_(choco.enveloppeBitVector));
  
  choco.getKernel->addMethod(list::domain(1,choco._BitSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getKernel_BitSetDomain,"choco_getKernel_BitSetDomain"));
  
  choco.getEnveloppe->addMethod(list::domain(1,choco._BitSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getEnveloppe_BitSetDomain,"choco_getEnveloppe_BitSetDomain"));
  
  choco.getKernelSize->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	0,_function_(choco_getKernelSize_BitSetDomain,"choco_getKernelSize_BitSetDomain"));
  
  choco.getEnveloppeSize->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	0,_function_(choco_getEnveloppeSize_BitSetDomain,"choco_getEnveloppeSize_BitSetDomain"));
  
  choco.getKernelInf->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getKernelInf_BitSetDomain,"choco_getKernelInf_BitSetDomain"));
  
  choco.getKernelSup->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getKernelSup_BitSetDomain,"choco_getKernelSup_BitSetDomain"));
  
  choco.getEnveloppeInf->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getEnveloppeInf_BitSetDomain,"choco_getEnveloppeInf_BitSetDomain"));
  
  choco.getEnveloppeSup->addMethod(list::domain(1,choco._BitSetDomain),Kernel._integer,
  	RETURN_ARG,_function_(choco_getEnveloppeSup_BitSetDomain,"choco_getEnveloppeSup_BitSetDomain"));
  
  choco.isInEnveloppe->addMethod(list::domain(2,choco._BitSetDomain,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_isInEnveloppe_BitSetDomain,"choco_isInEnveloppe_BitSetDomain"));
  
  choco.isInKernel->addMethod(list::domain(2,choco._BitSetDomain,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_isInKernel_BitSetDomain,"choco_isInKernel_BitSetDomain"));
  
  choco.makeBitSetDomain->addMethod(list::domain(2,Kernel._integer,Kernel._integer),choco._BitSetDomain,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeBitSetDomain_integer,"choco_makeBitSetDomain_integer"));
  
  choco.updateKernel->addMethod(list::domain(2,choco._BitSetDomain,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_updateKernel_BitSetDomain,"choco_updateKernel_BitSetDomain"));
  
  choco.updateEnveloppe->addMethod(list::domain(2,choco._BitSetDomain,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_updateEnveloppe_BitSetDomain,"choco_updateEnveloppe_BitSetDomain"));
  
  { (choco._BitListSetDomain = ClaireClass::make("BitListSetDomain",choco._AbstractSetDomain,choco.it));
    CL_ADD_SLOT(choco._BitListSetDomain,BitListSetDomain,choco.kernelBitVectorList,kernelBitVectorList,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._BitListSetDomain,BitListSetDomain,choco.enveloppeBitVectorList,enveloppeBitVectorList,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  choco.makeBitListSetDomain->addMethod(list::domain(2,Kernel._integer,Kernel._integer),choco._BitListSetDomain,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBitListSetDomain_integer,"choco_makeBitListSetDomain_integer"));
  
  choco.getKernel->addMethod(list::domain(1,choco._BitListSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getKernel_BitListSetDomain,"choco_getKernel_BitListSetDomain"));
  
  choco.getEnveloppe->addMethod(list::domain(1,choco._BitListSetDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getEnveloppe_BitListSetDomain,"choco_getEnveloppe_BitListSetDomain"));
  
  choco.getKernelSize->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	0,_function_(choco_getKernelSize_BitListSetDomain,"choco_getKernelSize_BitListSetDomain"));
  
  choco.getEnveloppeSize->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	0,_function_(choco_getEnveloppeSize_BitListSetDomain,"choco_getEnveloppeSize_BitListSetDomain"));
  
  choco.getKernelInf->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getKernelInf_BitListSetDomain,"choco_getKernelInf_BitListSetDomain"));
  
  choco.getKernelSup->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getKernelSup_BitListSetDomain,"choco_getKernelSup_BitListSetDomain"));
  
  choco.getEnveloppeInf->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getEnveloppeInf_BitListSetDomain,"choco_getEnveloppeInf_BitListSetDomain"));
  
  choco.getEnveloppeSup->addMethod(list::domain(1,choco._BitListSetDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getEnveloppeSup_BitListSetDomain,"choco_getEnveloppeSup_BitListSetDomain"));
  
  choco.isInEnveloppe->addMethod(list::domain(2,choco._BitListSetDomain,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_isInEnveloppe_BitListSetDomain,"choco_isInEnveloppe_BitListSetDomain"));
  
  choco.isInKernel->addMethod(list::domain(2,choco._BitListSetDomain,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_isInKernel_BitListSetDomain,"choco_isInKernel_BitListSetDomain"));
  
  choco.updateKernel->addMethod(list::domain(2,choco._BitListSetDomain,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateKernel_BitListSetDomain,"choco_updateKernel_BitListSetDomain"));
  
  choco.updateEnveloppe->addMethod(list::domain(2,choco._BitListSetDomain,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateEnveloppe_BitListSetDomain,"choco_updateEnveloppe_BitListSetDomain"));
  
  (choco._PropagationEvent = ClaireClass::make("PropagationEvent",choco._Ephemeral,choco.it));
  
  { (choco._VarEvent = ClaireClass::make("VarEvent",choco._PropagationEvent,choco.it));
    CL_ADD_SLOT(choco._VarEvent,VarEvent,choco.modifiedVar,modifiedVar,choco._AbstractVar,CNULL);
    CL_ADD_SLOT(choco._VarEvent,VarEvent,choco.nextConst,nextConst,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),_oid_(list::empty(Kernel._integer)));
    } 
  
  { (choco._Instantiation = ClaireClass::make("Instantiation",choco._VarEvent,choco.it));
    CL_ADD_SLOT(choco._Instantiation,Instantiation,choco.cause,cause,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._Instantiation),Kernel._void,
  	0,_function_(claire_self_print_Instantiation_choco,"claire_self_print_Instantiation_choco"));
  
  (choco._InstInt = ClaireClass::make("InstInt",choco._Instantiation,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._InstInt),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_InstInt_choco,"claire_self_print_InstInt_choco"));
  
  (choco._InstSet = ClaireClass::make("InstSet",choco._Instantiation,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._InstSet),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_InstSet_choco,"claire_self_print_InstSet_choco"));
  
  { (choco._BoundUpdate = ClaireClass::make("BoundUpdate",choco._VarEvent,choco.it));
    CL_ADD_SLOT(choco._BoundUpdate,BoundUpdate,choco.cause,cause,Kernel._integer,0);
    CL_ADD_SLOT(choco._BoundUpdate,BoundUpdate,choco.idxInQueue,idxInQueue,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._BoundUpdate),Kernel._void,
  	0,_function_(claire_self_print_BoundUpdate_choco,"claire_self_print_BoundUpdate_choco"));
  
  (choco._IncInf = ClaireClass::make("IncInf",choco._BoundUpdate,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._IncInf),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_IncInf_choco,"claire_self_print_IncInf_choco"));
  
  (choco._DecSup = ClaireClass::make("DecSup",choco._BoundUpdate,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._DecSup),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_DecSup_choco,"claire_self_print_DecSup_choco"));
  
  (choco._IncKer = ClaireClass::make("IncKer",choco._BoundUpdate,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._IncKer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_IncKer_choco,"claire_self_print_IncKer_choco"));
  
  (choco._DecEnv = ClaireClass::make("DecEnv",choco._BoundUpdate,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._DecEnv),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_DecEnv_choco,"claire_self_print_DecEnv_choco"));
  
  { (choco._ValueRemovals = ClaireClass::make("ValueRemovals",choco._VarEvent,choco.it));
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.maxVals,maxVals,Kernel._integer,3);
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.nbVals,nbVals,Kernel._integer,0);
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.many,many,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.valueStack,valueStack,nth_class1(Kernel._list,Kernel._integer),CNULL);
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.causeStack,causeStack,nth_class1(Kernel._list,Kernel._integer),CNULL);
    CL_ADD_SLOT(choco._ValueRemovals,ValueRemovals,choco.idxInQueue,idxInQueue,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._ValueRemovals),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_ValueRemovals_choco,"claire_self_print_ValueRemovals_choco"));
  
  { (choco._ConstAwakeEvent = ClaireClass::make("ConstAwakeEvent",choco._PropagationEvent,choco.it));
    CL_ADD_SLOT(choco._ConstAwakeEvent,ConstAwakeEvent,choco.touchedConst,touchedConst,choco._AbstractConstraint,CNULL);
    CL_ADD_SLOT(choco._ConstAwakeEvent,ConstAwakeEvent,choco.initialized,initialized,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(choco._ConstAwakeEvent,ConstAwakeEvent,choco.priority,priority,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._ConstAwakeEvent),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_ConstAwakeEvent_choco,"claire_self_print_ConstAwakeEvent_choco"));
  
  { (choco._EventCollection = ClaireClass::make("EventCollection",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._EventCollection,EventCollection,choco.engine,engine,choco._PropagationEngine,CNULL);
    CL_ADD_SLOT(choco._EventCollection,EventCollection,choco.qsize,qsize,Kernel._integer,0);
    } 
  
  { (choco._EventQueue = ClaireClass::make("EventQueue",choco._EventCollection,choco.it));
    CL_ADD_SLOT(choco._EventQueue,EventQueue,choco.qLastRead,qLastRead,Kernel._integer,0);
    CL_ADD_SLOT(choco._EventQueue,EventQueue,choco.qLastEnqueued,qLastEnqueued,Kernel._integer,0);
    CL_ADD_SLOT(choco._EventQueue,EventQueue,choco.isPopping,isPopping,Kernel._boolean,Kernel.cfalse);
    } 
  
  { (choco._BoundEventQueue = ClaireClass::make("BoundEventQueue",choco._EventQueue,choco.it));
    CL_ADD_SLOT(choco._BoundEventQueue,BoundEventQueue,choco.eventQueue,eventQueue,nth_class1(Kernel._list,choco._BoundUpdate),CNULL);
    CL_ADD_SLOT(choco._BoundEventQueue,BoundEventQueue,choco.redundantEvent,redundantEvent,Kernel._boolean,Kernel.cfalse);
    } 
  
  { (choco._RemovalEventQueue = ClaireClass::make("RemovalEventQueue",choco._EventQueue,choco.it));
    CL_ADD_SLOT(choco._RemovalEventQueue,RemovalEventQueue,choco.eventQueue,eventQueue,nth_class1(Kernel._list,choco._ValueRemovals),CNULL);
    CL_ADD_SLOT(choco._RemovalEventQueue,RemovalEventQueue,choco.redundantEvent,redundantEvent,Kernel._boolean,Kernel.cfalse);
    } 
  
  { (choco._InstantiationStack = ClaireClass::make("InstantiationStack",choco._EventCollection,choco.it));
    CL_ADD_SLOT(choco._InstantiationStack,InstantiationStack,choco.eventQueue,eventQueue,nth_class1(Kernel._list,choco._Instantiation),CNULL);
    CL_ADD_SLOT(choco._InstantiationStack,InstantiationStack,choco.sLastRead,sLastRead,Kernel._integer,0);
    CL_ADD_SLOT(choco._InstantiationStack,InstantiationStack,choco.sLastPushed,sLastPushed,Kernel._integer,0);
    } 
  
  (*Kernel.store)(_oid_(choco.sLastRead),
    _oid_(choco.sLastPushed));
  
  { (choco._ConstAwakeEventQueue = ClaireClass::make("ConstAwakeEventQueue",choco._EventCollection,choco.it));
    CL_ADD_SLOT(choco._ConstAwakeEventQueue,ConstAwakeEventQueue,choco.partition,partition,choco._BipartiteSet,CNULL);
    } 
  
  { (choco._ChocEngine = ClaireClass::make("ChocEngine",choco._PropagationEngine,choco.it));
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.removalEvtQueue,removalEvtQueue,choco._RemovalEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.boundEvtQueue,boundEvtQueue,choco._BoundEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.instEvtStack,instEvtStack,choco._InstantiationStack,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.delayedConst1,delayedConst1,choco._ConstAwakeEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.delayedConst2,delayedConst2,choco._ConstAwakeEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.delayedConst3,delayedConst3,choco._ConstAwakeEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.delayedConst4,delayedConst4,choco._ConstAwakeEventQueue,CNULL);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.nbPendingInitConstAwakeEvent,nbPendingInitConstAwakeEvent,Kernel._integer,0);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.nbPendingVarEvent,nbPendingVarEvent,Kernel._integer,0);
    CL_ADD_SLOT(choco._ChocEngine,ChocEngine,choco.delayLinCombThreshold,delayLinCombThreshold,Kernel._integer,8);
    } 
  
  choco.raiseContradiction->addMethod(list::domain(1,choco._PropagationEngine),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_raiseContradiction_PropagationEngine1,"choco_raiseContradiction_PropagationEngine1"));
  
  choco.raiseContradiction->addMethod(list::domain(2,choco._PropagationEngine,choco._Ephemeral),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_raiseContradiction_PropagationEngine2,"choco_raiseContradiction_PropagationEngine2"));
  
  choco.getContradictionCause->addMethod(list::domain(1,choco._PropagationEngine),choco._Ephemeral,
  	0,_function_(choco_getContradictionCause_PropagationEngine,"choco_getContradictionCause_PropagationEngine"));
  
  choco.raiseContradiction->addMethod(list::domain(1,choco._AbstractVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_raiseContradiction_AbstractVar,"choco_raiseContradiction_AbstractVar"));
  
  choco.raiseContradiction->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_raiseContradiction_AbstractConstraint,"choco_raiseContradiction_AbstractConstraint"));
  
  choco.raiseContradiction->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_raiseContradiction_Problem,"choco_raiseContradiction_Problem"));
  
  choco.makeChocEngine->addMethod(list::domain(1,Kernel._integer),choco._ChocEngine,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeChocEngine_integer,"choco_makeChocEngine_integer"));
  
  choco.attachPropagationEngine->addMethod(list::domain(2,choco._Problem,choco._PropagationEngine),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_attachPropagationEngine_Problem,"choco_attachPropagationEngine_Problem"));
  
  choco.isEmpty->addMethod(list::domain(1,choco._EventQueue),Kernel._boolean,
  	0,_function_(choco_isEmpty_EventQueue,"choco_isEmpty_EventQueue"));
  
  choco.isEmpty->addMethod(list::domain(1,choco._InstantiationStack),Kernel._boolean,
  	0,_function_(choco_isEmpty_InstantiationStack,"choco_isEmpty_InstantiationStack"));
  
  choco.popNextEvent->addMethod(list::domain(1,choco._BoundEventQueue),choco._BoundUpdate,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_popNextEvent_BoundEventQueue,"choco_popNextEvent_BoundEventQueue"));
  
  choco.popNextEvent->addMethod(list::domain(1,choco._RemovalEventQueue),choco._ValueRemovals,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_popNextEvent_RemovalEventQueue,"choco_popNextEvent_RemovalEventQueue"));
  
  choco.popNextEvent->addMethod(list::domain(1,choco._InstantiationStack),choco._Instantiation,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_popNextEvent_InstantiationStack,"choco_popNextEvent_InstantiationStack"));
  
  choco.nextEventPostIndex->addMethod(list::domain(1,choco._BoundEventQueue),Kernel._integer,
  	SLOT_UPDATE,_function_(choco_nextEventPostIndex_BoundEventQueue,"choco_nextEventPostIndex_BoundEventQueue"));
  
  { (choco.raiseOverflowWarning = property::make("raiseOverflowWarning",1,choco.it,Kernel._any,0));
    ;} 
  
  choco.nextEventPostIndex->addMethod(list::domain(1,choco._RemovalEventQueue),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_nextEventPostIndex_RemovalEventQueue,"choco_nextEventPostIndex_RemovalEventQueue"));
  
  choco.raiseOverflowWarning->addMethod(list::domain(1,choco._PropagationEngine),Kernel._void,
  	SLOT_UPDATE,_function_(choco_raiseOverflowWarning_PropagationEngine,"choco_raiseOverflowWarning_PropagationEngine"));
  
  choco.nextEventPostIndex->addMethod(list::domain(1,choco._InstantiationStack),Kernel._integer,
  	SLOT_UPDATE,_function_(choco_nextEventPostIndex_InstantiationStack,"choco_nextEventPostIndex_InstantiationStack"));
  
  choco.flushEventQueue->addMethod(list::domain(1,choco._BoundEventQueue),Kernel._void,
  	SLOT_UPDATE,_function_(choco_flushEventQueue_BoundEventQueue,"choco_flushEventQueue_BoundEventQueue"));
  
  choco.flushEventQueue->addMethod(list::domain(1,choco._RemovalEventQueue),Kernel._void,
  	SLOT_UPDATE,_function_(choco_flushEventQueue_RemovalEventQueue,"choco_flushEventQueue_RemovalEventQueue"));
  
  choco.flushEventQueue->addMethod(list::domain(1,choco._ConstAwakeEventQueue),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_flushEventQueue_ConstAwakeEventQueue,"choco_flushEventQueue_ConstAwakeEventQueue"));
  
  choco.checkCleanState->addMethod(list::domain(1,choco._ChocEngine),Kernel._any,
  	0,_function_(choco_checkCleanState_ChocEngine,"choco_checkCleanState_ChocEngine"));
  
  { (choco.getNextActiveEventQueue = property::make("getNextActiveEventQueue",3,choco.it,choco._PropagationEngine,30));
    (choco.getNextActiveEventQueue->range = U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)));
    ;} 
  
  choco.pushWorld->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	NEW_ALLOC+SAFE_RESULT,_function_(choco_pushWorld_Problem,"choco_pushWorld_Problem"));
  
  choco.popWorld->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	0,_function_(choco_popWorld_Problem,"choco_popWorld_Problem"));
  
  choco.setWorld->addMethod(list::domain(2,choco._Problem,Kernel._integer),Kernel._void,
  	0,_function_(choco_setWorld_Problem,"choco_setWorld_Problem"));
  
  choco.commitWorld->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	0,_function_(choco_commitWorld_Problem,"choco_commitWorld_Problem"));
  
  choco.flushCurrentOpenEvents->addMethod(list::domain(1,choco._ChocEngine),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_flushCurrentOpenEvents_ChocEngine,"choco_flushCurrentOpenEvents_ChocEngine"));
  
  choco.postInstInt->addMethod(list::domain(3,choco._PropagationEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postInstInt_PropagationEngine,"choco_postInstInt_PropagationEngine"));
  
  choco.postRemoveVal->addMethod(list::domain(4,choco._PropagationEngine,
    choco._IntVar,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postRemoveVal_PropagationEngine,"choco_postRemoveVal_PropagationEngine"));
  
  choco.postUpdateInf->addMethod(list::domain(3,choco._PropagationEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postUpdateInf_PropagationEngine,"choco_postUpdateInf_PropagationEngine"));
  
  choco.postUpdateSup->addMethod(list::domain(3,choco._PropagationEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postUpdateSup_PropagationEngine,"choco_postUpdateSup_PropagationEngine"));
  
  choco.postUpdateKer->addMethod(list::domain(3,choco._PropagationEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postUpdateKer_PropagationEngine,"choco_postUpdateKer_PropagationEngine"));
  
  choco.postUpdateEnv->addMethod(list::domain(3,choco._PropagationEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postUpdateEnv_PropagationEngine,"choco_postUpdateEnv_PropagationEngine"));
  
  choco.postInstSet->addMethod(list::domain(3,choco._PropagationEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_postInstSet_PropagationEngine,"choco_postInstSet_PropagationEngine"));
  
  choco.postConstAwake->addMethod(list::domain(3,choco._PropagationEngine,choco._AbstractConstraint,Kernel._boolean),Kernel._void,
  	SAFE_RESULT,_function_(choco_postConstAwake_PropagationEngine,"choco_postConstAwake_PropagationEngine"));
  
  choco.postInstantiateEvt->addMethod(list::domain(3,choco._ChocEngine,choco._Instantiation,Kernel._integer),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postInstantiateEvt_ChocEngine,"choco_postInstantiateEvt_ChocEngine"));
  
  choco.postInstInt->addMethod(list::domain(3,choco._ChocEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postInstInt_ChocEngine,"choco_postInstInt_ChocEngine"));
  
  choco.postInstSet->addMethod(list::domain(3,choco._ChocEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postInstSet_ChocEngine,"choco_postInstSet_ChocEngine"));
  
  choco.postRemoveVal->addMethod(list::domain(4,choco._ChocEngine,
    choco._IntVar,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postRemoveVal_ChocEngine,"choco_postRemoveVal_ChocEngine"));
  
  choco.postBoundEvent->addMethod(list::domain(3,choco._ChocEngine,choco._BoundUpdate,Kernel._integer),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postBoundEvent_ChocEngine,"choco_postBoundEvent_ChocEngine"));
  
  choco.postUpdateInf->addMethod(list::domain(3,choco._ChocEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postUpdateInf_ChocEngine,"choco_postUpdateInf_ChocEngine"));
  
  choco.postUpdateSup->addMethod(list::domain(3,choco._ChocEngine,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postUpdateSup_ChocEngine,"choco_postUpdateSup_ChocEngine"));
  
  choco.postUpdateKer->addMethod(list::domain(3,choco._ChocEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postUpdateKer_ChocEngine,"choco_postUpdateKer_ChocEngine"));
  
  choco.postUpdateEnv->addMethod(list::domain(3,choco._ChocEngine,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postUpdateEnv_ChocEngine,"choco_postUpdateEnv_ChocEngine"));
  
  choco.getQueue->addMethod(list::domain(2,choco._ChocEngine,choco._ConstAwakeEvent),choco._ConstAwakeEventQueue,
  	0,_function_(choco_getQueue_ChocEngine,"choco_getQueue_ChocEngine"));
  
  choco.registerEvent->addMethod(list::domain(2,choco._ChocEngine,choco._ConstAwakeEvent),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_registerEvent_ChocEngine,"choco_registerEvent_ChocEngine"));
  
  choco.postConstAwake->addMethod(list::domain(3,choco._ChocEngine,choco._AbstractConstraint,Kernel._boolean),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_postConstAwake_ChocEngine,"choco_postConstAwake_ChocEngine"));
  
  choco.remove->addMethod(list::domain(2,choco._ConstAwakeEventQueue,choco._ConstAwakeEvent),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_remove_ConstAwakeEventQueue,"choco_remove_ConstAwakeEventQueue"));
  
  choco.constAwake->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_constAwake_AbstractConstraint,"choco_constAwake_AbstractConstraint"));
  
  { (choco.propagateEvent = property::make("propagateEvent",3,choco.it,Kernel._any,0));
    ;} 
  
  choco.popSomeEvents->addMethod(list::domain(1,choco._InstantiationStack),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_popSomeEvents_InstantiationStack,"choco_popSomeEvents_InstantiationStack"));
  
  choco.propagateEvent->addMethod(list::domain(1,choco._Instantiation),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagateEvent_Instantiation_choco,"choco_propagateEvent_Instantiation_choco"));
  
  choco.popSomeEvents->addMethod(list::domain(1,choco._RemovalEventQueue),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_popSomeEvents_RemovalEventQueue,"choco_popSomeEvents_RemovalEventQueue"));
  
  choco.propagateEvent->addMethod(list::domain(2,choco._ValueRemovals,choco._RemovalEventQueue),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagateEvent_ValueRemovals_choco,"choco_propagateEvent_ValueRemovals_choco"));
  
  choco.popSomeEvents->addMethod(list::domain(1,choco._BoundEventQueue),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_popSomeEvents_BoundEventQueue,"choco_popSomeEvents_BoundEventQueue"));
  
  choco.propagateEvent->addMethod(list::domain(2,choco._BoundUpdate,choco._BoundEventQueue),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagateEvent_BoundUpdate_choco,"choco_propagateEvent_BoundUpdate_choco"));
  
  choco.isEmpty->addMethod(list::domain(1,choco._ConstAwakeEventQueue),Kernel._boolean,
  	0,_function_(choco_isEmpty_ConstAwakeEventQueue,"choco_isEmpty_ConstAwakeEventQueue"));
  
  choco.popSomeEvents->addMethod(list::domain(1,choco._ConstAwakeEventQueue),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_popSomeEvents_ConstAwakeEventQueue,"choco_popSomeEvents_ConstAwakeEventQueue"));
  
  choco.popNextEvent->addMethod(list::domain(1,choco._ConstAwakeEventQueue),choco._ConstAwakeEvent,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_popNextEvent_ConstAwakeEventQueue,"choco_popNextEvent_ConstAwakeEventQueue"));
  
  choco.propagateEvent->addMethod(list::domain(1,choco._ConstAwakeEvent),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagateEvent_ConstAwakeEvent_choco,"choco_propagateEvent_ConstAwakeEvent_choco"));
  
  ;
  ;
  choco.popSomeEvents->addMethod(list::domain(1,choco._EventCollection),Kernel._void,
  	SAFE_RESULT,_function_(choco_popSomeEvents_EventCollection,"choco_popSomeEvents_EventCollection"));
  
  choco.getNextActiveEventQueue->addMethod(list::domain(1,choco._PropagationEngine),U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_getNextActiveEventQueue_PropagationEngine_choco,"choco_getNextActiveEventQueue_PropagationEngine_choco"));
  
  choco.getNextActiveConstraintEventQueue->addMethod(list::domain(1,choco._ChocEngine),U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_getNextActiveConstraintEventQueue_ChocEngine,"choco_getNextActiveConstraintEventQueue_ChocEngine"));
  
  choco.getNextActiveVariableEventQueue->addMethod(list::domain(1,choco._ChocEngine),U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_getNextActiveVariableEventQueue_ChocEngine,"choco_getNextActiveVariableEventQueue_ChocEngine"));
  
  choco.getNextActiveEventQueue->addMethod(list::domain(1,choco._ChocEngine),U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_getNextActiveEventQueue_ChocEngine_choco,"choco_getNextActiveEventQueue_ChocEngine_choco"));
  
  ;
  ;
  { (choco._AbstractVar = ClaireClass::make("AbstractVar",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,Kernel.name,name,Kernel._string,_string_(""));
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.hook,hook,Kernel._any,CNULL);
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.problem,problem,choco._Problem,CNULL);
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.nbViolatedConstraints,nbViolatedConstraints,Kernel._integer,0);
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.nbConstraints,nbConstraints,Kernel._integer,0);
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.constraints,constraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._AbstractConstraint))))),CNULL);
    CL_ADD_SLOT(choco._AbstractVar,AbstractVar,choco.indices,indices,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  { (choco.isInstantiated = property::make("isInstantiated",3,choco.it,choco._AbstractVar,31));
    (choco.isInstantiated->range = Kernel._boolean);
    ;} 
  
  choco.isInstantiated->addMethod(list::domain(1,choco._AbstractVar),Kernel._boolean,
  	0,_function_(choco_isInstantiated_AbstractVar_choco,"choco_isInstantiated_AbstractVar_choco"));
  
  ;
  ;
  choco.getConstraint->addMethod(list::domain(2,choco._AbstractVar,Kernel._integer),choco._AbstractConstraint,
  	RETURN_ARG,_function_(choco_getConstraint_AbstractVar,"choco_getConstraint_AbstractVar"));
  
  choco.getDegree->addMethod(list::domain(1,choco._AbstractVar),Kernel._integer,
  	0,_function_(choco_getDegree_AbstractVar,"choco_getDegree_AbstractVar"));
  
  { (choco._IntVar = ClaireClass::make("IntVar",choco._AbstractVar,choco.it));
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.inf,inf,choco._StoredInt,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.sup,sup,choco._StoredInt,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,Kernel.value,value,Kernel._integer,-100000000);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.savedAssignment,savedAssignment,Kernel._integer,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.bucket,bucket,choco._AbstractIntDomain,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.updtInfEvt,updtInfEvt,choco._IncInf,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.updtSupEvt,updtSupEvt,choco._DecSup,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.instantiateEvt,instantiateEvt,choco._InstInt,CNULL);
    CL_ADD_SLOT(choco._IntVar,IntVar,choco.remValEvt,remValEvt,choco._ValueRemovals,CNULL);
    } 
  
  (*Core.reify)(_oid_(choco.inf),
    _oid_(choco.sup));
  
  (*Kernel.store)(_oid_(Kernel.value));
  
  Kernel.self_print->addMethod(list::domain(1,choco._IntVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_IntVar_choco,"claire_self_print_IntVar_choco"));
  
  choco.closeIntVar->addMethod(list::domain(4,choco._IntVar,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeIntVar_IntVar,"choco_closeIntVar_IntVar"));
  
  choco.updateInf->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateInf_IntVar1_choco,"choco_updateInf_IntVar1_choco"));
  
  choco.updateSup->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateSup_IntVar1_choco,"choco_updateSup_IntVar1_choco"));
  
  choco.instantiate->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_instantiate_IntVar1,"choco_instantiate_IntVar1"));
  
  choco.removeVal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_removeVal_IntVar1_choco,"choco_removeVal_IntVar1_choco"));
  
  Kernel.domain->addMethod(list::domain(1,choco._IntVar),nth_class1(Kernel._type,Kernel._integer),
  	NEW_ALLOC,_function_(claire_domain_IntVar,"claire_domain_IntVar"));
  
  ;
  { (choco.getInf = property::make("getInf",3,choco.it,choco._IntVar,34));
    (choco.getInf->range = Kernel._integer);
    ;} 
  
  { (choco.getSup = property::make("getSup",3,choco.it,choco._IntVar,35));
    (choco.getSup->range = Kernel._integer);
    ;} 
  
  { (choco.isInstantiatedTo = (operation *) Kernel._operation->instantiate("isInstantiatedTo",choco.it));
    (choco.isInstantiatedTo->precedence = Kernel._Z->precedence);
    (choco.isInstantiatedTo->range = Kernel._boolean);
    ;} 
  
  { (choco.canBeInstantiatedTo = (operation *) Kernel._operation->instantiate("canBeInstantiatedTo",choco.it));
    (choco.canBeInstantiatedTo->precedence = Kernel._Z->precedence);
    (choco.canBeInstantiatedTo->range = Kernel._boolean);
    ;} 
  
  { (choco.canBeEqualTo = (operation *) Kernel._operation->instantiate("canBeEqualTo",choco.it));
    (choco.canBeEqualTo->precedence = Kernel._Z->precedence);
    (choco.canBeEqualTo->range = Kernel._boolean);
    ;} 
  
  { (choco.domainIncludedIn = (operation *) Kernel._operation->instantiate("domainIncludedIn",choco.it));
    (choco.domainIncludedIn->precedence = Kernel._Z->precedence);
    (choco.domainIncludedIn->range = Kernel._boolean);
    ;} 
  
  { (choco.canBeInstantiatedIn = (operation *) Kernel._operation->instantiate("canBeInstantiatedIn",choco.it));
    (choco.canBeInstantiatedIn->precedence = Kernel._Z->precedence);
    (choco.canBeInstantiatedIn->range = Kernel._boolean);
    ;} 
  
  choco.isInstantiatedTo->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isInstantiatedTo_IntVar_choco,"choco_isInstantiatedTo_IntVar_choco"));
  
  choco.canBeInstantiatedTo->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_canBeInstantiatedTo_IntVar_choco,"choco_canBeInstantiatedTo_IntVar_choco"));
  
  choco.canBeEqualTo->addMethod(list::domain(2,choco._IntVar,choco._IntVar),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_canBeEqualTo_IntVar_choco,"choco_canBeEqualTo_IntVar_choco"));
  
  choco.domainIncludedIn->addMethod(list::domain(2,choco._IntVar,nth_class1(Kernel._list,Kernel._integer)),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_domainIncludedIn_IntVar_choco,"choco_domainIncludedIn_IntVar_choco"));
  
  choco.canBeInstantiatedIn->addMethod(list::domain(2,choco._IntVar,nth_class1(Kernel._list,Kernel._integer)),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_canBeInstantiatedIn_IntVar_choco,"choco_canBeInstantiatedIn_IntVar_choco"));
  
  choco.hasExactDomain->addMethod(list::domain(1,choco._IntVar),Kernel._boolean,
  	0,_function_(choco_hasExactDomain_IntVar_choco,"choco_hasExactDomain_IntVar_choco"));
  
  Kernel.random->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_random_IntVar,"claire_random_IntVar"));
  
  choco.getNextDomainValue->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getNextDomainValue_IntVar,"choco_getNextDomainValue_IntVar"));
  
  choco.getPrevDomainValue->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getPrevDomainValue_IntVar,"choco_getPrevDomainValue_IntVar"));
  
  choco.getInf->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getInf_IntVar_choco,"choco_getInf_IntVar_choco"));
  
  choco.getSup->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getSup_IntVar_choco,"choco_getSup_IntVar_choco"));
  
  choco.isInstantiated->addMethod(list::domain(1,choco._IntVar),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_isInstantiated_IntVar_choco,"choco_isInstantiated_IntVar_choco"));
  
  choco.getValue->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	0,_function_(choco_getValue_IntVar,"choco_getValue_IntVar"));
  
  choco.getDomainSize->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	0,_function_(choco_getDomainSize_IntVar,"choco_getDomainSize_IntVar"));
  
  { ;} 
  
  ;
  choco.updateInf->addMethod(list::domain(3,choco._IntVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateInf_IntVar2_choco,"choco_updateInf_IntVar2_choco"));
  
  choco.updateSup->addMethod(list::domain(3,choco._IntVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateSup_IntVar2_choco,"choco_updateSup_IntVar2_choco"));
  
  choco.removeVal->addMethod(list::domain(3,choco._IntVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_removeVal_IntVar2_choco,"choco_removeVal_IntVar2_choco"));
  
  choco.instantiate->addMethod(list::domain(3,choco._IntVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_instantiate_IntVar2,"choco_instantiate_IntVar2"));
  
  choco.removeInterval->addMethod(list::domain(4,choco._IntVar,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_removeInterval_IntVar,"choco_removeInterval_IntVar"));
  
  choco.restrictTo->addMethod(list::domain(3,choco._IntVar,nth_class1(Kernel._list,Kernel._integer),Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_restrictTo_IntVar,"choco_restrictTo_IntVar"));
  
  { (choco._SetVar = ClaireClass::make("SetVar",choco._AbstractVar,choco.it));
    CL_ADD_SLOT(choco._SetVar,SetVar,choco.setBucket,setBucket,choco._AbstractSetDomain,CNULL);
    CL_ADD_SLOT(choco._SetVar,SetVar,choco.updtKerEvt,updtKerEvt,choco._IncKer,CNULL);
    CL_ADD_SLOT(choco._SetVar,SetVar,choco.updtEnvEvt,updtEnvEvt,choco._DecEnv,CNULL);
    CL_ADD_SLOT(choco._SetVar,SetVar,choco.instantiateEvt,instantiateEvt,choco._InstSet,CNULL);
    } 
  
  ;
  Kernel.self_print->addMethod(list::domain(1,choco._SetVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_SetVar_choco,"claire_self_print_SetVar_choco"));
  
  choco.getDomainKernel->addMethod(list::domain(1,choco._SetVar),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getDomainKernel_SetVar,"choco_getDomainKernel_SetVar"));
  
  choco.getDomainEnveloppe->addMethod(list::domain(1,choco._SetVar),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_getDomainEnveloppe_SetVar,"choco_getDomainEnveloppe_SetVar"));
  
  choco.getDomainKernelSize->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC,_function_(choco_getDomainKernelSize_SetVar,"choco_getDomainKernelSize_SetVar"));
  
  choco.getDomainEnveloppeSize->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC,_function_(choco_getDomainEnveloppeSize_SetVar,"choco_getDomainEnveloppeSize_SetVar"));
  
  choco.getDomainEnveloppeInf->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getDomainEnveloppeInf_SetVar,"choco_getDomainEnveloppeInf_SetVar"));
  
  choco.getDomainEnveloppeSup->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getDomainEnveloppeSup_SetVar,"choco_getDomainEnveloppeSup_SetVar"));
  
  choco.getDomainKernelInf->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getDomainKernelInf_SetVar,"choco_getDomainKernelInf_SetVar"));
  
  choco.getDomainKernelSup->addMethod(list::domain(1,choco._SetVar),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getDomainKernelSup_SetVar,"choco_getDomainKernelSup_SetVar"));
  
  choco.isInstantiated->addMethod(list::domain(1,choco._SetVar),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_isInstantiated_SetVar_choco,"choco_isInstantiated_SetVar_choco"));
  
  choco.isInstantiatedTo->addMethod(list::domain(2,choco._SetVar,nth_class1(Kernel._list,Kernel._integer)),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isInstantiatedTo_SetVar_choco,"choco_isInstantiatedTo_SetVar_choco"));
  
  choco.getValue->addMethod(list::domain(1,choco._SetVar),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getValue_SetVar,"choco_getValue_SetVar"));
  
  choco.isInDomainEnveloppe->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_isInDomainEnveloppe_SetVar,"choco_isInDomainEnveloppe_SetVar"));
  
  choco.isInDomainKernel->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_isInDomainKernel_SetVar,"choco_isInDomainKernel_SetVar"));
  
  choco.canBeInstantiatedTo->addMethod(list::domain(2,choco._SetVar,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)))),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_canBeInstantiatedTo_SetVar_choco,"choco_canBeInstantiatedTo_SetVar_choco"));
  
  choco.canBeEqualTo->addMethod(list::domain(2,choco._SetVar,choco._SetVar),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_canBeEqualTo_SetVar_choco,"choco_canBeEqualTo_SetVar_choco"));
  
  choco.addSetVar->addMethod(list::domain(2,choco._Problem,choco._SetVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_addSetVar_Problem,"choco_addSetVar_Problem"));
  
  choco.closeSetVar->addMethod(list::domain(3,choco._SetVar,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeSetVar_SetVar,"choco_closeSetVar_SetVar"));
  
  choco.addToKernel->addMethod(list::domain(3,choco._SetVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_addToKernel_SetVar,"choco_addToKernel_SetVar"));
  
  choco.remFromEnveloppe->addMethod(list::domain(3,choco._SetVar,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_remFromEnveloppe_SetVar,"choco_remFromEnveloppe_SetVar"));
  
  choco.setIn->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setIn_SetVar,"choco_setIn_SetVar"));
  
  choco.setOut->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setOut_SetVar,"choco_setOut_SetVar"));
  
  { (choco._AbstractConstraint = ClaireClass::make("AbstractConstraint",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._AbstractConstraint,AbstractConstraint,choco.hook,hook,Kernel._any,CNULL);
    CL_ADD_SLOT(choco._AbstractConstraint,AbstractConstraint,choco.constAwakeEvent,constAwakeEvent,choco._ConstAwakeEvent,CNULL);
    CL_ADD_SLOT(choco._AbstractConstraint,AbstractConstraint,choco.fastDispatchIndex,fastDispatchIndex,Kernel._integer,-1);
    CL_ADD_SLOT(choco._AbstractConstraint,AbstractConstraint,choco.violated,violated,Kernel._boolean,Kernel.cfalse);
    } 
  
  { (choco._IntConstraint = ClaireClass::make("IntConstraint",choco._AbstractConstraint,choco.it));
    CL_ADD_SLOT(choco._IntConstraint,IntConstraint,choco.cste,cste,Kernel._integer,0);
    } 
  
  { (choco._UnIntConstraint = ClaireClass::make("UnIntConstraint",choco._IntConstraint,choco.it));
    CL_ADD_SLOT(choco._UnIntConstraint,UnIntConstraint,choco.v1,v1,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._UnIntConstraint,UnIntConstraint,choco.idx1,idx1,Kernel._integer,0);
    } 
  
  { (choco._BinIntConstraint = ClaireClass::make("BinIntConstraint",choco._IntConstraint,choco.it));
    CL_ADD_SLOT(choco._BinIntConstraint,BinIntConstraint,choco.v1,v1,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._BinIntConstraint,BinIntConstraint,choco.idx1,idx1,Kernel._integer,0);
    CL_ADD_SLOT(choco._BinIntConstraint,BinIntConstraint,choco.v2,v2,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._BinIntConstraint,BinIntConstraint,choco.idx2,idx2,Kernel._integer,0);
    } 
  
  { (choco._TernIntConstraint = ClaireClass::make("TernIntConstraint",choco._IntConstraint,choco.it));
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.v1,v1,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.idx1,idx1,Kernel._integer,0);
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.v2,v2,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.idx2,idx2,Kernel._integer,0);
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.v3,v3,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._TernIntConstraint,TernIntConstraint,choco.idx3,idx3,Kernel._integer,0);
    } 
  
  ;
  { (choco._LargeIntConstraint = ClaireClass::make("LargeIntConstraint",choco._IntConstraint,choco.it));
    CL_ADD_SLOT(choco._LargeIntConstraint,LargeIntConstraint,Core.vars,vars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    CL_ADD_SLOT(choco._LargeIntConstraint,LargeIntConstraint,choco.indices,indices,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._LargeIntConstraint,LargeIntConstraint,choco.nbVars,nbVars,Kernel._integer,0);
    } 
  
  choco.closeLargeIntConstraint->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeLargeIntConstraint_LargeIntConstraint,"choco_closeLargeIntConstraint_LargeIntConstraint"));
  
  { (choco._Delayer = ClaireClass::make("Delayer",choco._IntConstraint,choco.it));
    CL_ADD_SLOT(choco._Delayer,Delayer,choco.priority,priority,Kernel._integer,2);
    CL_ADD_SLOT(choco._Delayer,Delayer,choco.target,target,choco._IntConstraint,CNULL);
    } 
  
  choco.delay->addMethod(list::domain(2,choco._IntConstraint,Kernel._integer),choco._Delayer,
  	NEW_ALLOC,_function_(choco_delay_IntConstraint,"choco_delay_IntConstraint"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Delayer),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Delayer_choco,"claire_self_print_Delayer_choco"));
  
  (choco._CompositeConstraint = ClaireClass::make("CompositeConstraint",choco._AbstractConstraint,choco.it));
  
  { (choco._BinCompositeConstraint = ClaireClass::make("BinCompositeConstraint",choco._CompositeConstraint,choco.it));
    CL_ADD_SLOT(choco._BinCompositeConstraint,BinCompositeConstraint,choco.const1,const1,choco._AbstractConstraint,CNULL);
    CL_ADD_SLOT(choco._BinCompositeConstraint,BinCompositeConstraint,choco.const2,const2,choco._AbstractConstraint,CNULL);
    CL_ADD_SLOT(choco._BinCompositeConstraint,BinCompositeConstraint,choco.offset,offset,Kernel._integer,0);
    } 
  
  ;
  { (choco._LargeCompositeConstraint = ClaireClass::make("LargeCompositeConstraint",choco._CompositeConstraint,choco.it));
    CL_ADD_SLOT(choco._LargeCompositeConstraint,LargeCompositeConstraint,choco.lconst,lconst,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._AbstractConstraint))))),CNULL);
    CL_ADD_SLOT(choco._LargeCompositeConstraint,LargeCompositeConstraint,choco.loffset,loffset,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._LargeCompositeConstraint,LargeCompositeConstraint,choco.nbConst,nbConst,Kernel._integer,0);
    CL_ADD_SLOT(choco._LargeCompositeConstraint,LargeCompositeConstraint,choco.additionalVars,additionalVars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    CL_ADD_SLOT(choco._LargeCompositeConstraint,LargeCompositeConstraint,choco.additionalIndices,additionalIndices,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  { (choco.getNbVars = property::make("getNbVars",1,choco.it,choco._AbstractConstraint,36));
    (choco.getNbVars->range = Kernel._integer);
    ;} 
  
  choco.closeCompositeConstraint->addMethod(list::domain(1,choco._BinCompositeConstraint),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeCompositeConstraint_BinCompositeConstraint,"choco_closeCompositeConstraint_BinCompositeConstraint"));
  
  choco.closeCompositeConstraint->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_closeCompositeConstraint_LargeCompositeConstraint,"choco_closeCompositeConstraint_LargeCompositeConstraint"));
  
  choco.getNbVarsFromSubConst->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._integer,
  	RETURN_ARG,_function_(choco_getNbVarsFromSubConst_LargeCompositeConstraint,"choco_getNbVarsFromSubConst_LargeCompositeConstraint"));
  
  { (choco.connect = property::make("connect",3,choco.it,Kernel._any,0));
    (choco.connect->range = Kernel._void);
    ;} 
  
  { (choco.disconnect = property::make("disconnect",3,choco.it,Kernel._any,0));
    (choco.disconnect->range = Kernel._void);
    ;} 
  
  { (choco.reconnect = property::make("reconnect",3,choco.it,Kernel._any,0));
    (choco.reconnect->range = Kernel._void);
    ;} 
  
  { (choco.connectIntVar = property::make("connectIntVar",1,choco.it,Kernel._any,0));
    (choco.connectIntVar->range = Kernel._void);
    ;} 
  
  { (choco.connectIntVarEvents = property::make("connectIntVarEvents",3,choco.it,Kernel._any,0));
    (choco.connectIntVarEvents->range = Kernel._void);
    ;} 
  
  { (choco.disconnectIntVar = property::make("disconnectIntVar",1,choco.it,Kernel._any,0));
    (choco.disconnectIntVar->range = Kernel._void);
    ;} 
  
  { (choco.disconnectEvent = property::make("disconnectEvent",1,choco.it,Kernel._any,0));
    (choco.disconnectEvent->range = Kernel._void);
    ;} 
  
  { (choco.disconnectIntVarEvents = property::make("disconnectIntVarEvents",3,choco.it,Kernel._any,0));
    (choco.disconnectIntVarEvents->range = Kernel._void);
    ;} 
  
  { (choco.reconnectEvent = property::make("reconnectEvent",1,choco.it,Kernel._any,0));
    (choco.reconnectEvent->range = Kernel._void);
    ;} 
  
  { (choco.reconnectIntVarEvents = property::make("reconnectIntVarEvents",3,choco.it,Kernel._any,0));
    (choco.reconnectIntVarEvents->range = Kernel._void);
    ;} 
  
  { (choco.isActive = property::make("isActive",3,choco.it,Kernel._any,0));
    (choco.isActive->range = Kernel._boolean);
    ;} 
  
  choco.connectHook->addMethod(list::domain(2,Kernel._any,choco._AbstractConstraint),Kernel._any,
  	0,_function_(choco_connectHook_any_choco,"choco_connectHook_any_choco"));
  
  choco.reconnectHook->addMethod(list::domain(2,Kernel._any,choco._AbstractConstraint),Kernel._any,
  	0,_function_(choco_reconnectHook_any_choco,"choco_reconnectHook_any_choco"));
  
  choco.disconnectHook->addMethod(list::domain(2,Kernel._any,choco._AbstractConstraint),Kernel._any,
  	0,_function_(choco_disconnectHook_any_choco,"choco_disconnectHook_any_choco"));
  
  { (choco.setConstraintIndex = property::make("setConstraintIndex",1,choco.it,Kernel._any,0));
    (choco.setConstraintIndex->range = Kernel._void);
    ;} 
  
  choco.assignIndices->addMethod(list::domain(3,choco._AbstractConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	0,_function_(choco_assignIndices_AbstractConstraint,"choco_assignIndices_AbstractConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._UnIntConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_UnIntConstraint,"choco_assignIndices_UnIntConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._BinIntConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_BinIntConstraint,"choco_assignIndices_BinIntConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._TernIntConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_TernIntConstraint,"choco_assignIndices_TernIntConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._LargeIntConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_LargeIntConstraint,"choco_assignIndices_LargeIntConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._BinCompositeConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC,_function_(choco_assignIndices_BinCompositeConstraint,"choco_assignIndices_BinCompositeConstraint"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._LargeCompositeConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_LargeCompositeConstraint,"choco_assignIndices_LargeCompositeConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._AbstractConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_setConstraintIndex_AbstractConstraint,"choco_setConstraintIndex_AbstractConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._UnIntConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_UnIntConstraint,"choco_setConstraintIndex_UnIntConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._BinIntConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_BinIntConstraint,"choco_setConstraintIndex_BinIntConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._TernIntConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_TernIntConstraint,"choco_setConstraintIndex_TernIntConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._LargeIntConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_LargeIntConstraint,"choco_setConstraintIndex_LargeIntConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._BinCompositeConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_setConstraintIndex_BinCompositeConstraint,"choco_setConstraintIndex_BinCompositeConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._LargeCompositeConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_LargeCompositeConstraint,"choco_setConstraintIndex_LargeCompositeConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._Delayer,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_setConstraintIndex_Delayer,"choco_setConstraintIndex_Delayer"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_AbstractConstraint,"choco_getConstraintIdx_AbstractConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._UnIntConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_UnIntConstraint,"choco_getConstraintIdx_UnIntConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._BinIntConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_BinIntConstraint,"choco_getConstraintIdx_BinIntConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._TernIntConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_TernIntConstraint,"choco_getConstraintIdx_TernIntConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._LargeIntConstraint,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_getConstraintIdx_LargeIntConstraint,"choco_getConstraintIdx_LargeIntConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._BinCompositeConstraint,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getConstraintIdx_BinCompositeConstraint,"choco_getConstraintIdx_BinCompositeConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._LargeCompositeConstraint,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getConstraintIdx_LargeCompositeConstraint,"choco_getConstraintIdx_LargeCompositeConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._Delayer,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getConstraintIdx_Delayer,"choco_getConstraintIdx_Delayer"));
  
  { (choco.doPropagate = property::make("doPropagate",1,choco.it,Kernel._any,0));
    (choco.doPropagate->range = Kernel._void);
    ;} 
  
  { (choco.doAwake = property::make("doAwake",1,choco.it,Kernel._any,0));
    (choco.doAwake->range = Kernel._void);
    ;} 
  
  { (choco.doAwakeOnVar = property::make("doAwakeOnVar",1,choco.it,Kernel._any,0));
    (choco.doAwakeOnVar->range = Kernel._void);
    ;} 
  
  { (choco.askIfTrue = property::make("askIfTrue",1,choco.it,Kernel._any,0));
    (choco.askIfTrue->range = U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)));
    ;} 
  
  { (choco.doAwakeOnInf = property::make("doAwakeOnInf",1,choco.it,Kernel._any,0));
    (choco.doAwakeOnInf->range = Kernel._void);
    ;} 
  
  { (choco.doAwakeOnSup = property::make("doAwakeOnSup",1,choco.it,Kernel._any,0));
    (choco.doAwakeOnSup->range = Kernel._void);
    ;} 
  
  { (choco.doAwakeOnInst = property::make("doAwakeOnInst",1,choco.it,Kernel._any,0));
    (choco.doAwakeOnInst->range = Kernel._void);
    ;} 
  
  { (choco.doAwakeOnRem = property::make("doAwakeOnRem",1,choco.it,Kernel._any,0));
    (choco.doAwakeOnRem->range = Kernel._void);
    ;} 
  
  { (choco.testIfTrue = property::make("testIfTrue",1,choco.it,Kernel._any,0));
    (choco.testIfTrue->range = Kernel._boolean);
    ;} 
  
  { (choco.testIfInstantiated = property::make("testIfInstantiated",1,choco.it,Kernel._any,0));
    (choco.testIfInstantiated->range = Kernel._boolean);
    ;} 
  
  { (choco.getPriority = property::make("getPriority",3,choco.it,Kernel._any,0));
    (choco.getPriority->range = Kernel._integer);
    ;} 
  
  { (choco.getVar = property::make("getVar",3,choco.it,Kernel._any,0));
    (choco.getVar->range = choco._AbstractVar);
    ;} 
  
  { (choco.propagate = property::make("propagate",1,choco.it,choco._Ephemeral,41));
    (choco.propagate->range = Kernel._void);
    ;} 
  
  { (choco.awake = property::make("awake",3,choco.it,Kernel._any,0));
    (choco.awake->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnInf = property::make("awakeOnInf",1,choco.it,choco._AbstractConstraint,44));
    (choco.awakeOnInf->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnSup = property::make("awakeOnSup",1,choco.it,choco._AbstractConstraint,45));
    (choco.awakeOnSup->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnInst = property::make("awakeOnInst",1,choco.it,choco._AbstractConstraint,46));
    (choco.awakeOnInst->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnRem = property::make("awakeOnRem",1,choco.it,choco._AbstractConstraint,47));
    (choco.awakeOnRem->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnVar = property::make("awakeOnVar",1,choco.it,choco._AbstractConstraint,43));
    (choco.awakeOnVar->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnKer = property::make("awakeOnKer",1,choco.it,Kernel._any,0));
    (choco.awakeOnKer->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnEnv = property::make("awakeOnEnv",1,choco.it,Kernel._any,0));
    (choco.awakeOnEnv->range = Kernel._void);
    ;} 
  
  { (choco.awakeOnInstSet = property::make("awakeOnInstSet",1,choco.it,Kernel._any,0));
    (choco.awakeOnInstSet->range = Kernel._void);
    ;} 
  
  { (choco.askIfEntailed = property::make("askIfEntailed",1,choco.it,choco._AbstractConstraint,38));
    (choco.askIfEntailed->range = U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)));
    ;} 
  
  { (choco.testIfSatisfied = property::make("testIfSatisfied",1,choco.it,choco._AbstractConstraint,39));
    (choco.testIfSatisfied->range = Kernel._boolean);
    ;} 
  
  { (choco.testIfCompletelyInstantiated = property::make("testIfCompletelyInstantiated",1,choco.it,choco._AbstractConstraint,40));
    (choco.testIfCompletelyInstantiated->range = Kernel._boolean);
    ;} 
  
  choco.getPriority->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._integer,
  	0,_function_(choco_getPriority_AbstractConstraint_choco,"choco_getPriority_AbstractConstraint_choco"));
  
  choco.getPriority->addMethod(list::domain(1,choco._Delayer),Kernel._integer,
  	0,_function_(choco_getPriority_Delayer_choco,"choco_getPriority_Delayer_choco"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._IntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_IntConstraint,"choco_testIfCompletelyInstantiated_IntConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_UnIntConstraint,"choco_testIfCompletelyInstantiated_UnIntConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_BinIntConstraint,"choco_testIfCompletelyInstantiated_BinIntConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_TernIntConstraint,"choco_testIfCompletelyInstantiated_TernIntConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_LargeIntConstraint,"choco_testIfCompletelyInstantiated_LargeIntConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._BinCompositeConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_BinCompositeConstraint,"choco_testIfCompletelyInstantiated_BinCompositeConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_LargeCompositeConstraint,"choco_testIfCompletelyInstantiated_LargeCompositeConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_AbstractConstraint,"choco_getNbVars_AbstractConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_UnIntConstraint,"choco_getNbVars_UnIntConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_BinIntConstraint,"choco_getNbVars_BinIntConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_TernIntConstraint,"choco_getNbVars_TernIntConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_LargeIntConstraint,"choco_getNbVars_LargeIntConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._integer,
  	RETURN_ARG,_function_(choco_getNbVars_LargeCompositeConstraint,"choco_getNbVars_LargeCompositeConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._BinCompositeConstraint),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getNbVars_BinCompositeConstraint,"choco_getNbVars_BinCompositeConstraint"));
  
  choco.getVar->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),choco._AbstractVar,
  	NEW_ALLOC,_function_(choco_getVar_AbstractConstraint_choco,"choco_getVar_AbstractConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._UnIntConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_UnIntConstraint_choco,"choco_getVar_UnIntConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._BinIntConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_BinIntConstraint_choco,"choco_getVar_BinIntConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._TernIntConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_TernIntConstraint_choco,"choco_getVar_TernIntConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._LargeIntConstraint,Kernel._integer),choco._AbstractVar,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getVar_LargeIntConstraint_choco,"choco_getVar_LargeIntConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._LargeCompositeConstraint,Kernel._integer),choco._AbstractVar,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getVar_LargeCompositeConstraint_choco,"choco_getVar_LargeCompositeConstraint_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._BinCompositeConstraint,Kernel._integer),choco._AbstractVar,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getVar_BinCompositeConstraint_choco,"choco_getVar_BinCompositeConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	SAFE_RESULT,_function_(choco_disconnect_AbstractConstraint_choco,"choco_disconnect_AbstractConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	SAFE_RESULT,_function_(choco_connect_AbstractConstraint_choco,"choco_connect_AbstractConstraint_choco"));
  
  choco.opposite->addMethod(list::domain(1,choco._AbstractConstraint),choco._AbstractConstraint,
  	RETURN_ARG,_function_(choco_opposite_AbstractConstraint,"choco_opposite_AbstractConstraint"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._AbstractConstraint),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+RETURN_ARG,_function_(choco_askIfEntailed_AbstractConstraint,"choco_askIfEntailed_AbstractConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._Ephemeral),Kernel._void,
  	SAFE_RESULT,_function_(choco_propagate_Ephemeral,"choco_propagate_Ephemeral"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_AbstractConstraint,"choco_awakeOnInf_AbstractConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_AbstractConstraint,"choco_awakeOnSup_AbstractConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInst_AbstractConstraint,"choco_awakeOnInst_AbstractConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._AbstractConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_AbstractConstraint,"choco_awakeOnRem_AbstractConstraint"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnVar_AbstractConstraint,"choco_awakeOnVar_AbstractConstraint"));
  
  choco.awake->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awake_AbstractConstraint_choco,"choco_awake_AbstractConstraint_choco"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnKer_AbstractConstraint,"choco_awakeOnKer_AbstractConstraint"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnEnv_AbstractConstraint,"choco_awakeOnEnv_AbstractConstraint"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInstSet_AbstractConstraint,"choco_awakeOnInstSet_AbstractConstraint"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_AbstractConstraint,"choco_testIfSatisfied_AbstractConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_AbstractConstraint,"choco_testIfCompletelyInstantiated_AbstractConstraint"));
  
  { ;} 
  
  ;
  choco.propagate->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_UnIntConstraint,"choco_propagate_UnIntConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_BinIntConstraint,"choco_propagate_BinIntConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_LargeIntConstraint,"choco_propagate_LargeIntConstraint"));
  
  choco.doAwake->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwake_AbstractConstraint,"choco_doAwake_AbstractConstraint"));
  
  choco.doPropagate->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doPropagate_AbstractConstraint,"choco_doPropagate_AbstractConstraint"));
  
  choco.doAwakeOnInf->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnInf_AbstractConstraint,"choco_doAwakeOnInf_AbstractConstraint"));
  
  choco.doAwakeOnSup->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnSup_AbstractConstraint,"choco_doAwakeOnSup_AbstractConstraint"));
  
  choco.doAwakeOnInst->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnInst_AbstractConstraint,"choco_doAwakeOnInst_AbstractConstraint"));
  
  choco.doAwakeOnKer->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnKer_AbstractConstraint,"choco_doAwakeOnKer_AbstractConstraint"));
  
  choco.doAwakeOnEnv->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnEnv_AbstractConstraint,"choco_doAwakeOnEnv_AbstractConstraint"));
  
  choco.doAwakeOnInstSet->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnInstSet_AbstractConstraint,"choco_doAwakeOnInstSet_AbstractConstraint"));
  
  choco.doAwakeOnRem->addMethod(list::domain(3,choco._AbstractConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnRem_AbstractConstraint,"choco_doAwakeOnRem_AbstractConstraint"));
  
  choco.doAwakeOnVar->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_doAwakeOnVar_AbstractConstraint,"choco_doAwakeOnVar_AbstractConstraint"));
  
  choco.askIfTrue->addMethod(list::domain(1,choco._AbstractConstraint),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+RETURN_ARG,_function_(choco_askIfTrue_AbstractConstraint,"choco_askIfTrue_AbstractConstraint"));
  
  choco.testIfTrue->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_testIfTrue_AbstractConstraint,"choco_testIfTrue_AbstractConstraint"));
  
  choco.testIfInstantiated->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfInstantiated_AbstractConstraint,"choco_testIfInstantiated_AbstractConstraint"));
  
  choco.abstractIncInf->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_abstractIncInf_AbstractConstraint,"choco_abstractIncInf_AbstractConstraint"));
  
  choco.abstractDecSup->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_abstractDecSup_AbstractConstraint,"choco_abstractDecSup_AbstractConstraint"));
  
  choco.abstractInstantiate->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_abstractInstantiate_AbstractConstraint,"choco_abstractInstantiate_AbstractConstraint"));
  
  choco.abstractRemoveVal->addMethod(list::domain(3,choco._AbstractConstraint,Kernel._integer,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_abstractRemoveVal_AbstractConstraint,"choco_abstractRemoveVal_AbstractConstraint"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Delayer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_Delayer,"choco_awakeOnInf_Delayer"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Delayer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_Delayer,"choco_awakeOnSup_Delayer"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Delayer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Delayer,"choco_awakeOnInst_Delayer"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Delayer,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_Delayer,"choco_awakeOnRem_Delayer"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Delayer),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_testIfSatisfied_Delayer,"choco_testIfSatisfied_Delayer"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._Delayer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_Delayer,"choco_testIfCompletelyInstantiated_Delayer"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._Delayer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getNbVars_Delayer,"choco_getNbVars_Delayer"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Delayer),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+RETURN_ARG,_function_(choco_askIfEntailed_Delayer,"choco_askIfEntailed_Delayer"));
  
  choco.propagate->addMethod(list::domain(1,choco._Delayer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_Delayer,"choco_propagate_Delayer"));
  
  choco.awake->addMethod(list::domain(1,choco._Delayer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awake_Delayer_choco,"choco_awake_Delayer_choco"));
  
  choco.getVar->addMethod(list::domain(2,choco._Delayer,Kernel._integer),choco._AbstractVar,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getVar_Delayer_choco,"choco_getVar_Delayer_choco"));
  
  choco.addIntVar->addMethod(list::domain(2,choco._Problem,choco._IntVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_addIntVar_Problem,"choco_addIntVar_Problem"));
  
  choco.connect->addMethod(list::domain(1,choco._IntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_IntConstraint_choco,"choco_connect_IntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_UnIntConstraint_choco,"choco_connect_UnIntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_BinIntConstraint_choco,"choco_connect_BinIntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_TernIntConstraint_choco,"choco_connect_TernIntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_LargeIntConstraint_choco,"choco_connect_LargeIntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._CompositeConstraint),Kernel._void,
  	NEW_ALLOC,_function_(choco_connect_CompositeConstraint_choco,"choco_connect_CompositeConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._Delayer),Kernel._void,
  	NEW_ALLOC,_function_(choco_connect_Delayer_choco,"choco_connect_Delayer_choco"));
  
  choco.connectIntVar->addMethod(list::domain(3,choco._AbstractConstraint,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectIntVar_AbstractConstraint1,"choco_connectIntVar_AbstractConstraint1"));
  
  choco.connectIntVar->addMethod(list::domain(7,choco._AbstractConstraint,
    choco._IntVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectIntVar_AbstractConstraint2,"choco_connectIntVar_AbstractConstraint2"));
  
  { (choco.connectEvent = property::make("connectEvent",1,choco.it,Kernel._any,0));
    ;} 
  
  { (choco.connectIntVarEvents = property::make("connectIntVarEvents",3,choco.it,Kernel._any,0));
    ;} 
  
  choco.connectIntVarEvents->addMethod(list::domain(5,choco._IntVar,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectIntVarEvents_IntVar_choco,"choco_connectIntVarEvents_IntVar_choco"));
  
  choco.connectEvent->addMethod(list::domain(3,choco._VarEvent,Kernel._integer,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectEvent_VarEvent,"choco_connectEvent_VarEvent"));
  
  choco.disconnectIntVar->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_disconnectIntVar_IntVar,"choco_disconnectIntVar_IntVar"));
  
  choco.disconnectIntVarEvents->addMethod(list::domain(6,choco._IntVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_disconnectIntVarEvents_IntVar_choco,"choco_disconnectIntVarEvents_IntVar_choco"));
  
  choco.disconnectEvent->addMethod(list::domain(2,choco._VarEvent,Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(choco_disconnectEvent_VarEvent,"choco_disconnectEvent_VarEvent"));
  
  choco.connectSetVar->addMethod(list::domain(3,choco._AbstractConstraint,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectSetVar_AbstractConstraint1,"choco_connectSetVar_AbstractConstraint1"));
  
  choco.connectSetVar->addMethod(list::domain(6,choco._AbstractConstraint,
    choco._SetVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectSetVar_AbstractConstraint2,"choco_connectSetVar_AbstractConstraint2"));
  
  choco.connectSetVarEvents->addMethod(list::domain(4,choco._SetVar,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectSetVarEvents_SetVar_choco,"choco_connectSetVarEvents_SetVar_choco"));
  
  choco.disconnectSetVar->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_disconnectSetVar_SetVar,"choco_disconnectSetVar_SetVar"));
  
  choco.disconnectSetVarEvents->addMethod(list::domain(5,choco._SetVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_disconnectSetVarEvents_SetVar_choco,"choco_disconnectSetVarEvents_SetVar_choco"));
  
  choco.reconnectSetVar->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_reconnectSetVar_SetVar,"choco_reconnectSetVar_SetVar"));
  
  choco.reconnectSetVarEvents->addMethod(list::domain(5,choco._SetVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reconnectSetVarEvents_SetVar_choco,"choco_reconnectSetVarEvents_SetVar_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_UnIntConstraint_choco,"choco_disconnect_UnIntConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_BinIntConstraint_choco,"choco_disconnect_BinIntConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_TernIntConstraint_choco,"choco_disconnect_TernIntConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_LargeIntConstraint_choco,"choco_disconnect_LargeIntConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._Delayer),Kernel._void,
  	NEW_ALLOC,_function_(choco_disconnect_Delayer_choco,"choco_disconnect_Delayer_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_UnIntConstraint_choco,"choco_reconnect_UnIntConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_BinIntConstraint_choco,"choco_reconnect_BinIntConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_TernIntConstraint_choco,"choco_reconnect_TernIntConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_LargeIntConstraint_choco,"choco_reconnect_LargeIntConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	SAFE_RESULT,_function_(choco_reconnect_AbstractConstraint_choco,"choco_reconnect_AbstractConstraint_choco"));
  
  choco.reconnectIntVar->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_reconnectIntVar_IntVar,"choco_reconnectIntVar_IntVar"));
  
  choco.reconnectIntVarEvents->addMethod(list::domain(6,choco._IntVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reconnectIntVarEvents_IntVar_choco,"choco_reconnectIntVarEvents_IntVar_choco"));
  
  choco.reconnectEvent->addMethod(list::domain(2,choco._VarEvent,Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(choco_reconnectEvent_VarEvent,"choco_reconnectEvent_VarEvent"));
  
  choco.isActive->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._boolean,
  	0,_function_(choco_isActive_AbstractConstraint_choco,"choco_isActive_AbstractConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._UnIntConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_UnIntConstraint_choco,"choco_isActive_UnIntConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._BinIntConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_BinIntConstraint_choco,"choco_isActive_BinIntConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._TernIntConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_TernIntConstraint_choco,"choco_isActive_TernIntConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._LargeIntConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_LargeIntConstraint_choco,"choco_isActive_LargeIntConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._Delayer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_isActive_Delayer_choco,"choco_isActive_Delayer_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._BinCompositeConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_isActive_BinCompositeConstraint_choco,"choco_isActive_BinCompositeConstraint_choco"));
  
  choco.isActive->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_LargeCompositeConstraint_choco,"choco_isActive_LargeCompositeConstraint_choco"));
  
  choco.isActiveIntVar->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActiveIntVar_IntVar,"choco_isActiveIntVar_IntVar"));
  
  choco.isActiveSetVar->addMethod(list::domain(2,choco._SetVar,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActiveSetVar_SetVar,"choco_isActiveSetVar_SetVar"));
  
  choco.isActiveEvent->addMethod(list::domain(2,choco._VarEvent,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isActiveEvent_VarEvent,"choco_isActiveEvent_VarEvent"));
  
  (choco._BinRelation = ClaireClass::make("BinRelation",choco._Util,choco.it));
  
  { (choco.getTruthValue = property::make("getTruthValue",1,choco.it,choco._BinRelation,48));
    (choco.getTruthValue->range = Kernel._boolean);
    ;} 
  
  choco.getTruthValue->addMethod(list::domain(3,choco._BinRelation,Kernel._integer,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_getTruthValue_BinRelation,"choco_getTruthValue_BinRelation"));
  
  { ;} 
  
  { (choco._TruthTest = ClaireClass::make("TruthTest",choco._BinRelation,choco.it));
    CL_ADD_SLOT(choco._TruthTest,TruthTest,choco.test,test,nth_class2(Kernel._method,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    } 
  
  choco.getTruthValue->addMethod(list::domain(3,choco._TruthTest,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getTruthValue_TruthTest,"choco_getTruthValue_TruthTest"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._TruthTest),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_TruthTest_choco,"claire_self_print_TruthTest_choco"));
  
  { (choco._TruthTable2D = ClaireClass::make("TruthTable2D",choco._BinRelation,choco.it));
    CL_ADD_SLOT(choco._TruthTable2D,TruthTable2D,choco.offset1,offset1,Kernel._integer,0);
    CL_ADD_SLOT(choco._TruthTable2D,TruthTable2D,choco.offset2,offset2,Kernel._integer,0);
    CL_ADD_SLOT(choco._TruthTable2D,TruthTable2D,choco.size1,size1,Kernel._integer,0);
    CL_ADD_SLOT(choco._TruthTable2D,TruthTable2D,choco.size2,size2,Kernel._integer,0);
    CL_ADD_SLOT(choco._TruthTable2D,TruthTable2D,choco.matrix,matrix,choco._BoolMatrix2D,CNULL);
    } 
  
  choco.getTruthValue->addMethod(list::domain(3,choco._TruthTable2D,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getTruthValue_TruthTable2D,"choco_getTruthValue_TruthTable2D"));
  
  choco.setTruePair->addMethod(list::domain(3,choco._TruthTable2D,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_setTruePair_TruthTable2D,"choco_setTruePair_TruthTable2D"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._TruthTable2D),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_TruthTable2D_choco,"claire_self_print_TruthTable2D_choco"));
  
  { (choco._CSPBinConstraint = ClaireClass::make("CSPBinConstraint",choco._BinIntConstraint,choco.it));
    CL_ADD_SLOT(choco._CSPBinConstraint,CSPBinConstraint,choco.feasRelation,feasRelation,choco._BinRelation,CNULL);
    CL_ADD_SLOT(choco._CSPBinConstraint,CSPBinConstraint,choco.feasiblePair,feasiblePair,Kernel._boolean,Kernel.ctrue);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._CSPBinConstraint),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_CSPBinConstraint_choco,"claire_self_print_CSPBinConstraint_choco"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._CSPBinConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_CSPBinConstraint,"choco_testIfSatisfied_CSPBinConstraint"));
  
  choco.opposite->addMethod(list::domain(1,choco._CSPBinConstraint),choco._CSPBinConstraint,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_opposite_CSPBinConstraint,"choco_opposite_CSPBinConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._CSPBinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_CSPBinConstraint,"choco_awakeOnInst_CSPBinConstraint"));
  
  choco.see->addMethod(list::domain(1,choco._CSPBinConstraint),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_see_CSPBinConstraint,"choco_see_CSPBinConstraint"));
  
  (choco._AC3BinConstraint = ClaireClass::make("AC3BinConstraint",choco._CSPBinConstraint,choco.it));
  
  choco.makeAC3BinConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    Kernel._boolean,
    choco._BinRelation),choco._AC3BinConstraint,
  	NEW_ALLOC,_function_(choco_makeAC3BinConstraint_IntVar,"choco_makeAC3BinConstraint_IntVar"));
  
  choco.reviseV2->addMethod(list::domain(1,choco._AC3BinConstraint),Kernel._void,
  	NEW_ALLOC,_function_(choco_reviseV2_AC3BinConstraint,"choco_reviseV2_AC3BinConstraint"));
  
  choco.reviseV1->addMethod(list::domain(1,choco._AC3BinConstraint),Kernel._void,
  	NEW_ALLOC,_function_(choco_reviseV1_AC3BinConstraint,"choco_reviseV1_AC3BinConstraint"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._AC3BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_AC3BinConstraint,"choco_awakeOnInf_AC3BinConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._AC3BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_AC3BinConstraint,"choco_awakeOnSup_AC3BinConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._AC3BinConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_AC3BinConstraint,"choco_awakeOnRem_AC3BinConstraint"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._AC3BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnVar_AC3BinConstraint,"choco_awakeOnVar_AC3BinConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._AC3BinConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_AC3BinConstraint,"choco_propagate_AC3BinConstraint"));
  
  { (choco._AC4BinConstraint = ClaireClass::make("AC4BinConstraint",choco._CSPBinConstraint,choco.it));
    CL_ADD_SLOT(choco._AC4BinConstraint,AC4BinConstraint,choco.nbSupport1,nbSupport1,choco._IntSetIntAnnotation,CNULL);
    CL_ADD_SLOT(choco._AC4BinConstraint,AC4BinConstraint,choco.nbSupport2,nbSupport2,choco._IntSetIntAnnotation,CNULL);
    CL_ADD_SLOT(choco._AC4BinConstraint,AC4BinConstraint,choco.validSupport1,validSupport1,choco._IntSetBoolAnnotation,CNULL);
    CL_ADD_SLOT(choco._AC4BinConstraint,AC4BinConstraint,choco.validSupport2,validSupport2,choco._IntSetBoolAnnotation,CNULL);
    } 
  
  choco.makeAC4BinConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    Kernel._boolean,
    choco._BinRelation),choco._AC4BinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeAC4BinConstraint_IntVar,"choco_makeAC4BinConstraint_IntVar"));
  
  choco.decrementNbSupportV1->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_decrementNbSupportV1_AC4BinConstraint,"choco_decrementNbSupportV1_AC4BinConstraint"));
  
  choco.decrementNbSupportV2->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_decrementNbSupportV2_AC4BinConstraint,"choco_decrementNbSupportV2_AC4BinConstraint"));
  
  choco.resetNbSupportV1->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_resetNbSupportV1_AC4BinConstraint,"choco_resetNbSupportV1_AC4BinConstraint"));
  
  choco.resetNbSupportV2->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_resetNbSupportV2_AC4BinConstraint,"choco_resetNbSupportV2_AC4BinConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._AC4BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_AC4BinConstraint,"choco_propagate_AC4BinConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._AC4BinConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_AC4BinConstraint,"choco_awakeOnRem_AC4BinConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_AC4BinConstraint,"choco_awakeOnInst_AC4BinConstraint"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_AC4BinConstraint,"choco_awakeOnInf_AC4BinConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_AC4BinConstraint,"choco_awakeOnSup_AC4BinConstraint"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._AC4BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_AC4BinConstraint,"choco_awakeOnVar_AC4BinConstraint"));
  
  { (choco._AC2001BinConstraint = ClaireClass::make("AC2001BinConstraint",choco._CSPBinConstraint,choco.it));
    CL_ADD_SLOT(choco._AC2001BinConstraint,AC2001BinConstraint,choco.currentSupport1,currentSupport1,choco._IntSetIntAnnotation,CNULL);
    CL_ADD_SLOT(choco._AC2001BinConstraint,AC2001BinConstraint,choco.currentSupport2,currentSupport2,choco._IntSetIntAnnotation,CNULL);
    } 
  
  choco.makeAC2001BinConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    Kernel._boolean,
    choco._BinRelation),choco._AC2001BinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeAC2001BinConstraint_IntVar,"choco_makeAC2001BinConstraint_IntVar"));
  
  choco.reviseV2->addMethod(list::domain(1,choco._AC2001BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reviseV2_AC2001BinConstraint,"choco_reviseV2_AC2001BinConstraint"));
  
  choco.reviseV1->addMethod(list::domain(1,choco._AC2001BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reviseV1_AC2001BinConstraint,"choco_reviseV1_AC2001BinConstraint"));
  
  choco.updateSupportVal1->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_updateSupportVal1_AC2001BinConstraint,"choco_updateSupportVal1_AC2001BinConstraint"));
  
  choco.updateSupportVal2->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_updateSupportVal2_AC2001BinConstraint,"choco_updateSupportVal2_AC2001BinConstraint"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_AC2001BinConstraint,"choco_awakeOnInf_AC2001BinConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_AC2001BinConstraint,"choco_awakeOnSup_AC2001BinConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_AC2001BinConstraint,"choco_awakeOnInst_AC2001BinConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._AC2001BinConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_AC2001BinConstraint,"choco_awakeOnRem_AC2001BinConstraint"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._AC2001BinConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnVar_AC2001BinConstraint,"choco_awakeOnVar_AC2001BinConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._AC2001BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_AC2001BinConstraint,"choco_propagate_AC2001BinConstraint"));
  
  choco.awake->addMethod(list::domain(1,choco._AC2001BinConstraint),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awake_AC2001BinConstraint_choco,"choco_awake_AC2001BinConstraint_choco"));
  
  { (choco._CSPLargeConstraint = ClaireClass::make("CSPLargeConstraint",choco._LargeIntConstraint,choco.it));
    CL_ADD_SLOT(choco._CSPLargeConstraint,CSPLargeConstraint,choco.cachedTuples,cachedTuples,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._CSPLargeConstraint,CSPLargeConstraint,choco.matrix,matrix,choco._BoolMatrixND,CNULL);
    CL_ADD_SLOT(choco._CSPLargeConstraint,CSPLargeConstraint,choco.feasTest,feasTest,Kernel._method,CNULL);
    } 
  
  choco.tupleConstraint->addMethod(list::domain(3,nth_class1(Kernel._list,choco._IntVar),Kernel._boolean,nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)))),choco._CSPLargeConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_tupleConstraint_list,"choco_tupleConstraint_list"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._CSPLargeConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_CSPLargeConstraint,"choco_awakeOnInf_CSPLargeConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._CSPLargeConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_CSPLargeConstraint,"choco_awakeOnSup_CSPLargeConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._CSPLargeConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_CSPLargeConstraint,"choco_awakeOnRem_CSPLargeConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._CSPLargeConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_CSPLargeConstraint,"choco_awakeOnInst_CSPLargeConstraint"));
  
  choco.propagate->addMethod(list::domain(1,choco._CSPLargeConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_CSPLargeConstraint,"choco_propagate_CSPLargeConstraint"));
  
  (choco._GreaterOrEqualxc = ClaireClass::make("GreaterOrEqualxc",choco._UnIntConstraint,choco.it));
  
  (choco._LessOrEqualxc = ClaireClass::make("LessOrEqualxc",choco._UnIntConstraint,choco.it));
  
  (choco._Equalxc = ClaireClass::make("Equalxc",choco._UnIntConstraint,choco.it));
  
  (choco._NotEqualxc = ClaireClass::make("NotEqualxc",choco._UnIntConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._GreaterOrEqualxc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_GreaterOrEqualxc_choco,"claire_self_print_GreaterOrEqualxc_choco"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._LessOrEqualxc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_LessOrEqualxc_choco,"claire_self_print_LessOrEqualxc_choco"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Equalxc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Equalxc_choco,"claire_self_print_Equalxc_choco"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._NotEqualxc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_NotEqualxc_choco,"claire_self_print_NotEqualxc_choco"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Equalxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_Equalxc,"choco_awakeOnInf_Equalxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Equalxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_Equalxc,"choco_awakeOnSup_Equalxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Equalxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Equalxc,"choco_awakeOnInst_Equalxc"));
  
  choco.propagate->addMethod(list::domain(1,choco._Equalxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_Equalxc,"choco_propagate_Equalxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Equalxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_Equalxc,"choco_askIfEntailed_Equalxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Equalxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_Equalxc,"choco_testIfSatisfied_Equalxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._GreaterOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_GreaterOrEqualxc,"choco_awakeOnInf_GreaterOrEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._GreaterOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_GreaterOrEqualxc,"choco_awakeOnSup_GreaterOrEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._GreaterOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInst_GreaterOrEqualxc,"choco_awakeOnInst_GreaterOrEqualxc"));
  
  choco.propagate->addMethod(list::domain(1,choco._GreaterOrEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_GreaterOrEqualxc,"choco_propagate_GreaterOrEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._GreaterOrEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_GreaterOrEqualxc,"choco_askIfEntailed_GreaterOrEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._GreaterOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_GreaterOrEqualxc,"choco_testIfSatisfied_GreaterOrEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._LessOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_LessOrEqualxc,"choco_awakeOnInf_LessOrEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._LessOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_LessOrEqualxc,"choco_awakeOnSup_LessOrEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._LessOrEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInst_LessOrEqualxc,"choco_awakeOnInst_LessOrEqualxc"));
  
  choco.propagate->addMethod(list::domain(1,choco._LessOrEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_LessOrEqualxc,"choco_propagate_LessOrEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._LessOrEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_LessOrEqualxc,"choco_askIfEntailed_LessOrEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._LessOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_LessOrEqualxc,"choco_testIfSatisfied_LessOrEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._NotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInf_NotEqualxc,"choco_awakeOnInf_NotEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._NotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnSup_NotEqualxc,"choco_awakeOnSup_NotEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._NotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_NotEqualxc,"choco_awakeOnInst_NotEqualxc"));
  
  choco.propagate->addMethod(list::domain(1,choco._NotEqualxc),Kernel._void,
  	NEW_ALLOC,_function_(choco_propagate_NotEqualxc,"choco_propagate_NotEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._NotEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_NotEqualxc,"choco_askIfEntailed_NotEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._NotEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_NotEqualxc,"choco_testIfSatisfied_NotEqualxc"));
  
  (choco._Equalxyc = ClaireClass::make("Equalxyc",choco._BinIntConstraint,choco.it));
  
  (choco._NotEqualxyc = ClaireClass::make("NotEqualxyc",choco._BinIntConstraint,choco.it));
  
  (choco._GreaterOrEqualxyc = ClaireClass::make("GreaterOrEqualxyc",choco._BinIntConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Equalxyc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Equalxyc_choco,"claire_self_print_Equalxyc_choco"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._NotEqualxyc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_NotEqualxyc_choco,"claire_self_print_NotEqualxyc_choco"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._GreaterOrEqualxyc),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_GreaterOrEqualxyc_choco,"claire_self_print_GreaterOrEqualxyc_choco"));
  
  choco.propagate->addMethod(list::domain(1,choco._Equalxyc),Kernel._void,
  	NEW_ALLOC,_function_(choco_propagate_Equalxyc,"choco_propagate_Equalxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Equalxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_askIfEntailed_Equalxyc,"choco_askIfEntailed_Equalxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Equalxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_Equalxyc,"choco_testIfSatisfied_Equalxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Equalxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInf_Equalxyc,"choco_awakeOnInf_Equalxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Equalxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnSup_Equalxyc,"choco_awakeOnSup_Equalxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Equalxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Equalxyc,"choco_awakeOnInst_Equalxyc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Equalxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnRem_Equalxyc,"choco_awakeOnRem_Equalxyc"));
  
  choco.propagate->addMethod(list::domain(1,choco._NotEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_NotEqualxyc,"choco_propagate_NotEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._NotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInf_NotEqualxyc,"choco_awakeOnInf_NotEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._NotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnSup_NotEqualxyc,"choco_awakeOnSup_NotEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._NotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInst_NotEqualxyc,"choco_awakeOnInst_NotEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._NotEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_askIfEntailed_NotEqualxyc,"choco_askIfEntailed_NotEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._NotEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_NotEqualxyc,"choco_testIfSatisfied_NotEqualxyc"));
  
  choco.propagate->addMethod(list::domain(1,choco._GreaterOrEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_GreaterOrEqualxyc,"choco_propagate_GreaterOrEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._GreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_GreaterOrEqualxyc,"choco_awakeOnInf_GreaterOrEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._GreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_GreaterOrEqualxyc,"choco_awakeOnSup_GreaterOrEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._GreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_GreaterOrEqualxyc,"choco_awakeOnInst_GreaterOrEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._GreaterOrEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_askIfEntailed_GreaterOrEqualxyc,"choco_askIfEntailed_GreaterOrEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._GreaterOrEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_GreaterOrEqualxyc,"choco_testIfSatisfied_GreaterOrEqualxyc"));
  
  { global_variable * _CL_obj = (choco.EQ = (global_variable *) Core._global_variable->instantiate("EQ",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.GEQ = (global_variable *) Core._global_variable->instantiate("GEQ",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.NEQ = (global_variable *) Core._global_variable->instantiate("NEQ",choco.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { (choco._LinComb = ClaireClass::make("LinComb",choco._LargeIntConstraint,choco.it));
    CL_ADD_SLOT(choco._LinComb,LinComb,choco.nbPosVars,nbPosVars,Kernel._integer,0);
    CL_ADD_SLOT(choco._LinComb,LinComb,choco.coefs,coefs,nth_type(Kernel._integer),CNULL);
    CL_ADD_SLOT(choco._LinComb,LinComb,choco.op,op,Kernel._integer,1);
    CL_ADD_SLOT(choco._LinComb,LinComb,choco.improvedUpperBound,improvedUpperBound,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._LinComb,LinComb,choco.improvedLowerBound,improvedLowerBound,Kernel._boolean,Kernel.ctrue);
    } 
  
  (*Kernel.store)(_oid_(choco.improvedUpperBound),
    _oid_(choco.improvedLowerBound));
  
  Kernel.self_print->addMethod(list::domain(1,choco._LinComb),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_LinComb_choco,"claire_self_print_LinComb_choco"));
  
  choco.makeLinComb->addMethod(list::domain(4,nth_class1(Kernel._list,Kernel._integer),
    nth_class1(Kernel._list,choco._IntVar),
    Kernel._integer,
    Kernel._integer),choco._LinComb,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeLinComb_list,"choco_makeLinComb_list"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._LinComb),Kernel._integer,
  	0,_function_(choco_getNbVars_LinComb,"choco_getNbVars_LinComb"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._LinComb,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_LinComb,"choco_assignIndices_LinComb"));
  
  choco.opposite->addMethod(list::domain(1,choco._LinComb),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_LinComb,"choco_opposite_LinComb"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(choco_computeVarIdxInOpposite_LinComb,"choco_computeVarIdxInOpposite_LinComb"));
  
  choco.computeUpperBound->addMethod(list::domain(1,choco._LinComb),Kernel._integer,
  	RETURN_ARG,_function_(choco_computeUpperBound_LinComb_choco,"choco_computeUpperBound_LinComb_choco"));
  
  choco.computeLowerBound->addMethod(list::domain(1,choco._LinComb),Kernel._integer,
  	RETURN_ARG,_function_(choco_computeLowerBound_LinComb_choco,"choco_computeLowerBound_LinComb_choco"));
  
  { (choco.propagateNewLowerBound = property::make("propagateNewLowerBound",3,choco.it,Kernel._any,0));
    (choco.propagateNewLowerBound->range = Kernel._boolean);
    ;} 
  
  { (choco.propagateNewUpperBound = property::make("propagateNewUpperBound",3,choco.it,Kernel._any,0));
    (choco.propagateNewUpperBound->range = Kernel._boolean);
    ;} 
  
  { (choco.filter = property::make("filter",1,choco.it,choco._LinComb,51));
    (choco.filter->range = Kernel._void);
    ;} 
  
  choco.propagateNewLowerBound->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagateNewLowerBound_LinComb_choco,"choco_propagateNewLowerBound_LinComb_choco"));
  
  choco.propagateNewUpperBound->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagateNewUpperBound_LinComb_choco,"choco_propagateNewUpperBound_LinComb_choco"));
  
  choco.filterOnImprovedLowerBound->addMethod(list::domain(1,choco._LinComb),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_filterOnImprovedLowerBound_LinComb,"choco_filterOnImprovedLowerBound_LinComb"));
  
  choco.filterOnImprovedUpperBound->addMethod(list::domain(1,choco._LinComb),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_filterOnImprovedUpperBound_LinComb,"choco_filterOnImprovedUpperBound_LinComb"));
  
  choco.filter->addMethod(list::domain(3,choco._LinComb,Kernel._boolean,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_filter_LinComb,"choco_filter_LinComb"));
  
  { ;} 
  
  ;
  choco.propagate->addMethod(list::domain(1,choco._LinComb),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_LinComb,"choco_propagate_LinComb"));
  
  choco.awake->addMethod(list::domain(1,choco._LinComb),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_LinComb_choco,"choco_awake_LinComb_choco"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._LinComb),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_LinComb,"choco_askIfEntailed_LinComb"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._LinComb),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_LinComb,"choco_testIfSatisfied_LinComb"));
  
  choco.abstractIncInf->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_abstractIncInf_LinComb,"choco_abstractIncInf_LinComb"));
  
  choco.abstractDecSup->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_abstractDecSup_LinComb,"choco_abstractDecSup_LinComb"));
  
  choco.abstractInstantiate->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_abstractInstantiate_LinComb,"choco_abstractInstantiate_LinComb"));
  
  choco.abstractRemoveVal->addMethod(list::domain(3,choco._LinComb,Kernel._integer,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(choco_abstractRemoveVal_LinComb,"choco_abstractRemoveVal_LinComb"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_LinComb,"choco_awakeOnInf_LinComb"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_LinComb,"choco_awakeOnSup_LinComb"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._LinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInst_LinComb,"choco_awakeOnInst_LinComb"));
  
  { (choco._Elt = ClaireClass::make("Elt",choco._BinIntConstraint,choco.it));
    CL_ADD_SLOT(choco._Elt,Elt,choco.lval,lval,nth_class1(Kernel._list,Kernel._integer),CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._Elt),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Elt_choco,"claire_self_print_Elt_choco"));
  
  choco.makeEltConstraint->addMethod(list::domain(4,nth_class1(Kernel._list,Kernel._integer),
    choco._IntVar,
    choco._IntVar,
    Kernel._integer),choco._Elt,
  	NEW_ALLOC,_function_(choco_makeEltConstraint_list,"choco_makeEltConstraint_list"));
  
  choco.updateValueFromIndex->addMethod(list::domain(1,choco._Elt),Kernel._void,
  	NEW_ALLOC,_function_(choco_updateValueFromIndex_Elt,"choco_updateValueFromIndex_Elt"));
  
  choco.updateIndexFromValue->addMethod(list::domain(1,choco._Elt),Kernel._void,
  	NEW_ALLOC,_function_(choco_updateIndexFromValue_Elt,"choco_updateIndexFromValue_Elt"));
  
  choco.awake->addMethod(list::domain(1,choco._Elt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_Elt_choco,"choco_awake_Elt_choco"));
  
  choco.propagate->addMethod(list::domain(1,choco._Elt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_Elt,"choco_propagate_Elt"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Elt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_Elt,"choco_awakeOnInf_Elt"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Elt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_Elt,"choco_awakeOnSup_Elt"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Elt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Elt,"choco_awakeOnInst_Elt"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Elt,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_Elt,"choco_awakeOnRem_Elt"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Elt),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_Elt,"choco_askIfEntailed_Elt"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Elt),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_Elt,"choco_testIfSatisfied_Elt"));
  
  { (choco._Elt2 = ClaireClass::make("Elt2",choco._TernIntConstraint,choco.it));
    CL_ADD_SLOT(choco._Elt2,Elt2,choco.tval,tval,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._Elt2,Elt2,choco.dim1,dim1,Kernel._integer,0);
    CL_ADD_SLOT(choco._Elt2,Elt2,choco.dim2,dim2,Kernel._integer,0);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._Elt2),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Elt2_choco,"claire_self_print_Elt2_choco"));
  
  choco.makeElt2Constraint->addMethod(list::domain(6,Kernel._table,
    Kernel._integer,
    Kernel._integer,
    choco._IntVar,
    choco._IntVar,
    choco._IntVar),choco._Elt2,
  	NEW_ALLOC,_function_(choco_makeElt2Constraint_table,"choco_makeElt2Constraint_table"));
  
  choco.updateValueFromIndex->addMethod(list::domain(1,choco._Elt2),Kernel._void,
  	NEW_ALLOC,_function_(choco_updateValueFromIndex_Elt2,"choco_updateValueFromIndex_Elt2"));
  
  choco.updateIndexFromValue->addMethod(list::domain(1,choco._Elt2),Kernel._void,
  	NEW_ALLOC,_function_(choco_updateIndexFromValue_Elt2,"choco_updateIndexFromValue_Elt2"));
  
  choco.awake->addMethod(list::domain(1,choco._Elt2),Kernel._void,
  	NEW_ALLOC,_function_(choco_awake_Elt2_choco,"choco_awake_Elt2_choco"));
  
  choco.propagate->addMethod(list::domain(1,choco._Elt2),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_Elt2,"choco_propagate_Elt2"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Elt2,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_Elt2,"choco_awakeOnInf_Elt2"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Elt2,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_Elt2,"choco_awakeOnSup_Elt2"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Elt2,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_Elt2,"choco_awakeOnInst_Elt2"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Elt2,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_Elt2,"choco_awakeOnRem_Elt2"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Elt2),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_Elt2,"choco_askIfEntailed_Elt2"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Elt2),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_Elt2,"choco_testIfSatisfied_Elt2"));
  
  (choco._AllDiff = ClaireClass::make("AllDiff",choco._LargeIntConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._AllDiff),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_AllDiff_choco,"claire_self_print_AllDiff_choco"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._AllDiff,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_AllDiff,"choco_awakeOnInf_AllDiff"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._AllDiff,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_AllDiff,"choco_awakeOnSup_AllDiff"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._AllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_AllDiff,"choco_awakeOnRem_AllDiff"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._AllDiff,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_awakeOnInst_AllDiff,"choco_awakeOnInst_AllDiff"));
  
  choco.propagate->addMethod(list::domain(1,choco._AllDiff),Kernel._void,
  	NEW_ALLOC,_function_(choco_propagate_AllDiff,"choco_propagate_AllDiff"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._AllDiff),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_AllDiff,"choco_testIfSatisfied_AllDiff"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._AllDiff),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_AllDiff,"choco_askIfEntailed_AllDiff"));
  
  { (choco._Occurrence = ClaireClass::make("Occurrence",choco._LargeIntConstraint,choco.it));
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.isPossible,isPossible,nth_type(Kernel._boolean),CNULL);
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.isSure,isSure,nth_type(Kernel._boolean),CNULL);
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.nbPossible,nbPossible,Kernel._integer,CNULL);
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.nbSure,nbSure,Kernel._integer,CNULL);
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.constrainOnInfNumber,constrainOnInfNumber,Kernel._boolean,CNULL);
    CL_ADD_SLOT(choco._Occurrence,Occurrence,choco.constrainOnSupNumber,constrainOnSupNumber,Kernel._boolean,CNULL);
    } 
  
  (*Kernel.store)(_oid_(choco.nbPossible),
    _oid_(choco.nbSure));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_Occurrence_choco,"claire_self_print_Occurrence_choco"));
  
  choco.checkNbPossible->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_checkNbPossible_Occurrence_choco,"choco_checkNbPossible_Occurrence_choco"));
  
  choco.checkNbSure->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC,_function_(choco_checkNbSure_Occurrence_choco,"choco_checkNbSure_Occurrence_choco"));
  
  choco.update->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC,_function_(choco_update_Occurrence,"choco_update_Occurrence"));
  
  choco.propagate->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_Occurrence,"choco_propagate_Occurrence"));
  
  choco.awake->addMethod(list::domain(1,choco._Occurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_Occurrence_choco,"choco_awake_Occurrence_choco"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Occurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_awakeOnInf_Occurrence,"choco_awakeOnInf_Occurrence"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Occurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_awakeOnSup_Occurrence,"choco_awakeOnSup_Occurrence"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Occurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_awakeOnInst_Occurrence,"choco_awakeOnInst_Occurrence"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Occurrence,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_awakeOnRem_Occurrence,"choco_awakeOnRem_Occurrence"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Occurrence),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_Occurrence,"choco_testIfSatisfied_Occurrence"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Occurrence),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_Occurrence,"choco_askIfEntailed_Occurrence"));
  
  { (choco._BinBoolConstraint = ClaireClass::make("BinBoolConstraint",choco._BinCompositeConstraint,choco.it));
    CL_ADD_SLOT(choco._BinBoolConstraint,BinBoolConstraint,choco.statusBitVector,statusBitVector,Kernel._integer,0);
    } 
  
  (*Kernel.store)(_oid_(choco.statusBitVector));
  
  choco.closeBoolConstraint->addMethod(list::domain(1,choco._BinCompositeConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeBoolConstraint_BinCompositeConstraint,"choco_closeBoolConstraint_BinCompositeConstraint"));
  
  choco.knownStatus->addMethod(list::domain(2,choco._BinBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_knownStatus_BinBoolConstraint,"choco_knownStatus_BinBoolConstraint"));
  
  choco.getStatus->addMethod(list::domain(2,choco._BinBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_getStatus_BinBoolConstraint,"choco_getStatus_BinBoolConstraint"));
  
  choco.knownTargetStatus->addMethod(list::domain(2,choco._BinBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_knownTargetStatus_BinBoolConstraint,"choco_knownTargetStatus_BinBoolConstraint"));
  
  choco.getTargetStatus->addMethod(list::domain(2,choco._BinBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_getTargetStatus_BinBoolConstraint,"choco_getTargetStatus_BinBoolConstraint"));
  
  choco.setStatus->addMethod(list::domain(3,choco._BinBoolConstraint,Kernel._integer,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE,_function_(choco_setStatus_BinBoolConstraint,"choco_setStatus_BinBoolConstraint"));
  
  choco.setTargetStatus->addMethod(list::domain(3,choco._BinBoolConstraint,Kernel._integer,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE,_function_(choco_setTargetStatus_BinBoolConstraint,"choco_setTargetStatus_BinBoolConstraint"));
  
  { (choco._LargeBoolConstraint = ClaireClass::make("LargeBoolConstraint",choco._LargeCompositeConstraint,choco.it));
    CL_ADD_SLOT(choco._LargeBoolConstraint,LargeBoolConstraint,choco.statusBitVectorList,statusBitVectorList,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._LargeBoolConstraint,LargeBoolConstraint,choco.nbTrueStatus,nbTrueStatus,Kernel._integer,0);
    CL_ADD_SLOT(choco._LargeBoolConstraint,LargeBoolConstraint,choco.nbFalseStatus,nbFalseStatus,Kernel._integer,0);
    } 
  
  (*Kernel.store)(_oid_(choco.nbTrueStatus),
    _oid_(choco.nbFalseStatus));
  
  choco.closeBoolConstraint->addMethod(list::domain(1,choco._LargeCompositeConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeBoolConstraint_LargeCompositeConstraint,"choco_closeBoolConstraint_LargeCompositeConstraint"));
  
  choco.knownStatus->addMethod(list::domain(2,choco._LargeBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_knownStatus_LargeBoolConstraint,"choco_knownStatus_LargeBoolConstraint"));
  
  choco.getStatus->addMethod(list::domain(2,choco._LargeBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_getStatus_LargeBoolConstraint,"choco_getStatus_LargeBoolConstraint"));
  
  choco.knownTargetStatus->addMethod(list::domain(2,choco._LargeBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_knownTargetStatus_LargeBoolConstraint,"choco_knownTargetStatus_LargeBoolConstraint"));
  
  choco.getTargetStatus->addMethod(list::domain(2,choco._LargeBoolConstraint,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(choco_getTargetStatus_LargeBoolConstraint,"choco_getTargetStatus_LargeBoolConstraint"));
  
  choco.setStatus->addMethod(list::domain(3,choco._LargeBoolConstraint,Kernel._integer,Kernel._boolean),Kernel._void,
  	BAG_UPDATE,_function_(choco_setStatus_LargeBoolConstraint,"choco_setStatus_LargeBoolConstraint"));
  
  choco.setTargetStatus->addMethod(list::domain(3,choco._LargeBoolConstraint,Kernel._integer,Kernel._boolean),Kernel._void,
  	BAG_UPDATE,_function_(choco_setTargetStatus_LargeBoolConstraint,"choco_setTargetStatus_LargeBoolConstraint"));
  
  { (choco._BinBoolConstraintWCounterOpp = ClaireClass::make("BinBoolConstraintWCounterOpp",choco._BinBoolConstraint,choco.it));
    CL_ADD_SLOT(choco._BinBoolConstraintWCounterOpp,BinBoolConstraintWCounterOpp,choco.oppositeConst1,oppositeConst1,choco._AbstractConstraint,CNULL);
    CL_ADD_SLOT(choco._BinBoolConstraintWCounterOpp,BinBoolConstraintWCounterOpp,choco.oppositeConst2,oppositeConst2,choco._AbstractConstraint,CNULL);
    CL_ADD_SLOT(choco._BinBoolConstraintWCounterOpp,BinBoolConstraintWCounterOpp,choco.indicesInOppConst1,indicesInOppConst1,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._BinBoolConstraintWCounterOpp,BinBoolConstraintWCounterOpp,choco.indicesInOppConst2,indicesInOppConst2,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  choco.assignIndices->addMethod(list::domain(3,choco._BinBoolConstraintWCounterOpp,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_assignIndices_BinBoolConstraintWCounterOpp,"choco_assignIndices_BinBoolConstraintWCounterOpp"));
  
  choco.closeBoolConstraintWCounterOpp->addMethod(list::domain(1,choco._BinBoolConstraintWCounterOpp),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp,"choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp"));
  
  { (choco._LargeBoolConstraintWCounterOpp = ClaireClass::make("LargeBoolConstraintWCounterOpp",choco._LargeBoolConstraint,choco.it));
    CL_ADD_SLOT(choco._LargeBoolConstraintWCounterOpp,LargeBoolConstraintWCounterOpp,choco.loppositeConst,loppositeConst,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._AbstractConstraint))))),CNULL);
    CL_ADD_SLOT(choco._LargeBoolConstraintWCounterOpp,LargeBoolConstraintWCounterOpp,choco.indicesInOppConsts,indicesInOppConsts,nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer))),CNULL);
    } 
  
  choco.assignIndices->addMethod(list::domain(3,choco._LargeBoolConstraintWCounterOpp,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_LargeBoolConstraintWCounterOpp,"choco_assignIndices_LargeBoolConstraintWCounterOpp"));
  
  choco.closeBoolConstraintWCounterOpp->addMethod(list::domain(1,choco._LargeBoolConstraintWCounterOpp),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp,"choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp"));
  
  (choco._Disjunction = ClaireClass::make("Disjunction",choco._BinBoolConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Disjunction),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Disjunction_choco,"claire_self_print_Disjunction_choco"));
  
  choco.checkStatus->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_checkStatus_Disjunction,"choco_checkStatus_Disjunction"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_Disjunction,"choco_awakeOnInf_Disjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_Disjunction,"choco_awakeOnSup_Disjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_Disjunction,"choco_awakeOnInst_Disjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Disjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_Disjunction,"choco_awakeOnRem_Disjunction"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_Disjunction,"choco_awakeOnEnv_Disjunction"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_Disjunction,"choco_awakeOnKer_Disjunction"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._Disjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_Disjunction,"choco_awakeOnInstSet_Disjunction"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Disjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_Disjunction,"choco_askIfEntailed_Disjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Disjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_Disjunction,"choco_testIfSatisfied_Disjunction"));
  
  choco.propagate->addMethod(list::domain(1,choco._Disjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_Disjunction,"choco_propagate_Disjunction"));
  
  (choco._Guard = ClaireClass::make("Guard",choco._BinBoolConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Guard),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Guard_choco,"claire_self_print_Guard_choco"));
  
  choco.checkStatus->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_checkStatus_Guard,"choco_checkStatus_Guard"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_Guard,"choco_awakeOnInf_Guard"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_Guard,"choco_awakeOnSup_Guard"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_Guard,"choco_awakeOnInst_Guard"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Guard,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_Guard,"choco_awakeOnRem_Guard"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_Guard,"choco_awakeOnKer_Guard"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_Guard,"choco_awakeOnEnv_Guard"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._Guard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_Guard,"choco_awakeOnInstSet_Guard"));
  
  choco.propagate->addMethod(list::domain(1,choco._Guard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_Guard,"choco_propagate_Guard"));
  
  choco.awake->addMethod(list::domain(1,choco._Guard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_Guard_choco,"choco_awake_Guard_choco"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Guard),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_askIfEntailed_Guard,"choco_askIfEntailed_Guard"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Guard),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_Guard,"choco_testIfSatisfied_Guard"));
  
  (choco._Equiv = ClaireClass::make("Equiv",choco._BinBoolConstraintWCounterOpp,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Equiv),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Equiv_choco,"claire_self_print_Equiv_choco"));
  
  choco.checkStatus->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_checkStatus_Equiv,"choco_checkStatus_Equiv"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_Equiv,"choco_awakeOnInf_Equiv"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_Equiv,"choco_awakeOnSup_Equiv"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_Equiv,"choco_awakeOnInst_Equiv"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Equiv,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_Equiv,"choco_awakeOnRem_Equiv"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_Equiv,"choco_awakeOnKer_Equiv"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_Equiv,"choco_awakeOnEnv_Equiv"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._Equiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_Equiv,"choco_awakeOnInstSet_Equiv"));
  
  choco.propagate->addMethod(list::domain(1,choco._Equiv),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_Equiv,"choco_propagate_Equiv"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Equiv),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_Equiv,"choco_askIfEntailed_Equiv"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Equiv),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_Equiv,"choco_testIfSatisfied_Equiv"));
  
  (choco._Conjunction = ClaireClass::make("Conjunction",choco._BinBoolConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Conjunction),Kernel._void,
  	NEW_ALLOC,_function_(claire_self_print_Conjunction_choco,"claire_self_print_Conjunction_choco"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_Conjunction,"choco_awakeOnInf_Conjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_Conjunction,"choco_awakeOnSup_Conjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInst_Conjunction,"choco_awakeOnInst_Conjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Conjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_Conjunction,"choco_awakeOnRem_Conjunction"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnKer_Conjunction,"choco_awakeOnKer_Conjunction"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnEnv_Conjunction,"choco_awakeOnEnv_Conjunction"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._Conjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInstSet_Conjunction,"choco_awakeOnInstSet_Conjunction"));
  
  choco.propagate->addMethod(list::domain(1,choco._Conjunction),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_Conjunction,"choco_propagate_Conjunction"));
  
  choco.awake->addMethod(list::domain(1,choco._Conjunction),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awake_Conjunction_choco,"choco_awake_Conjunction_choco"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Conjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_Conjunction,"choco_askIfEntailed_Conjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Conjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_Conjunction,"choco_testIfSatisfied_Conjunction"));
  
  (choco._LargeConjunction = ClaireClass::make("LargeConjunction",choco._LargeBoolConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._LargeConjunction),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_LargeConjunction_choco,"claire_self_print_LargeConjunction_choco"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_LargeConjunction,"choco_awakeOnInf_LargeConjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_LargeConjunction,"choco_awakeOnSup_LargeConjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInst_LargeConjunction,"choco_awakeOnInst_LargeConjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._LargeConjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_LargeConjunction,"choco_awakeOnRem_LargeConjunction"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnKer_LargeConjunction,"choco_awakeOnKer_LargeConjunction"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnEnv_LargeConjunction,"choco_awakeOnEnv_LargeConjunction"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._LargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInstSet_LargeConjunction,"choco_awakeOnInstSet_LargeConjunction"));
  
  choco.propagate->addMethod(list::domain(1,choco._LargeConjunction),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_LargeConjunction,"choco_propagate_LargeConjunction"));
  
  choco.awake->addMethod(list::domain(1,choco._LargeConjunction),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awake_LargeConjunction_choco,"choco_awake_LargeConjunction_choco"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._LargeConjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_LargeConjunction,"choco_askIfEntailed_LargeConjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._LargeConjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_LargeConjunction,"choco_testIfSatisfied_LargeConjunction"));
  
  (choco._LargeDisjunction = ClaireClass::make("LargeDisjunction",choco._LargeBoolConstraint,choco.it));
  
  Kernel.self_print->addMethod(list::domain(1,choco._LargeDisjunction),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_LargeDisjunction_choco,"claire_self_print_LargeDisjunction_choco"));
  
  choco.checkNbFalseStatus->addMethod(list::domain(1,choco._LargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_checkNbFalseStatus_LargeDisjunction,"choco_checkNbFalseStatus_LargeDisjunction"));
  
  choco.checkStatus->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_checkStatus_LargeDisjunction,"choco_checkStatus_LargeDisjunction"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_LargeDisjunction,"choco_awakeOnInf_LargeDisjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_LargeDisjunction,"choco_awakeOnSup_LargeDisjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_LargeDisjunction,"choco_awakeOnInst_LargeDisjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._LargeDisjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_LargeDisjunction,"choco_awakeOnRem_LargeDisjunction"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_LargeDisjunction,"choco_awakeOnKer_LargeDisjunction"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_LargeDisjunction,"choco_awakeOnEnv_LargeDisjunction"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._LargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_LargeDisjunction,"choco_awakeOnInstSet_LargeDisjunction"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._LargeDisjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_LargeDisjunction,"choco_askIfEntailed_LargeDisjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._LargeDisjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_LargeDisjunction,"choco_testIfSatisfied_LargeDisjunction"));
  
  choco.propagate->addMethod(list::domain(1,choco._LargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_LargeDisjunction,"choco_propagate_LargeDisjunction"));
  
  choco.awake->addMethod(list::domain(1,choco._LargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_LargeDisjunction_choco,"choco_awake_LargeDisjunction_choco"));
  
  { (choco._Cardinality = ClaireClass::make("Cardinality",choco._LargeBoolConstraintWCounterOpp,choco.it));
    CL_ADD_SLOT(choco._Cardinality,Cardinality,choco.constrainOnInfNumber,constrainOnInfNumber,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._Cardinality,Cardinality,choco.constrainOnSupNumber,constrainOnSupNumber,Kernel._boolean,Kernel.ctrue);
    } 
  
  choco.getCardVar->addMethod(list::domain(1,choco._Cardinality),choco._IntVar,
  	RETURN_ARG,_function_(choco_getCardVar_Cardinality,"choco_getCardVar_Cardinality"));
  
  choco.getCardVarIdx->addMethod(list::domain(1,choco._Cardinality),Kernel._integer,
  	RETURN_ARG,_function_(choco_getCardVarIdx_Cardinality,"choco_getCardVarIdx_Cardinality"));
  
  Kernel.self_print->addMethod(list::domain(1,choco._Cardinality),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_Cardinality_choco,"claire_self_print_Cardinality_choco"));
  
  choco.awakeOnNbTrue->addMethod(list::domain(1,choco._Cardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnNbTrue_Cardinality,"choco_awakeOnNbTrue_Cardinality"));
  
  choco.awakeOnNbFalse->addMethod(list::domain(1,choco._Cardinality),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnNbFalse_Cardinality,"choco_awakeOnNbFalse_Cardinality"));
  
  choco.checkStatus->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_checkStatus_Cardinality,"choco_checkStatus_Cardinality"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_Cardinality,"choco_awakeOnInf_Cardinality"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_Cardinality,"choco_awakeOnSup_Cardinality"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_Cardinality,"choco_awakeOnInst_Cardinality"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._Cardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_Cardinality,"choco_awakeOnRem_Cardinality"));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_Cardinality,"choco_awakeOnKer_Cardinality"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_Cardinality,"choco_awakeOnEnv_Cardinality"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._Cardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_Cardinality,"choco_awakeOnInstSet_Cardinality"));
  
  choco.awake->addMethod(list::domain(1,choco._Cardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_Cardinality_choco,"choco_awake_Cardinality_choco"));
  
  choco.propagate->addMethod(list::domain(1,choco._Cardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_Cardinality,"choco_propagate_Cardinality"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._Cardinality),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_Cardinality,"choco_testIfSatisfied_Cardinality"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._Cardinality),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_Cardinality,"choco_askIfEntailed_Cardinality"));
  
  choco.opposite->addMethod(list::domain(1,choco._Guard),choco._Conjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_Guard,"choco_opposite_Guard"));
  
  choco.opposite->addMethod(list::domain(1,choco._Conjunction),choco._Disjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_Conjunction,"choco_opposite_Conjunction"));
  
  choco.opposite->addMethod(list::domain(1,choco._Disjunction),choco._Conjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_Disjunction,"choco_opposite_Disjunction"));
  
  choco.opposite->addMethod(list::domain(1,choco._LargeDisjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_LargeDisjunction,"choco_opposite_LargeDisjunction"));
  
  choco.opposite->addMethod(list::domain(1,choco._LargeConjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_LargeConjunction,"choco_opposite_LargeConjunction"));
  
  choco.opposite->addMethod(list::domain(1,choco._GreaterOrEqualxc),choco._LessOrEqualxc,
  	NEW_ALLOC,_function_(choco_opposite_GreaterOrEqualxc,"choco_opposite_GreaterOrEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,choco._LessOrEqualxc),choco._GreaterOrEqualxc,
  	NEW_ALLOC,_function_(choco_opposite_LessOrEqualxc,"choco_opposite_LessOrEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,choco._Equalxc),choco._NotEqualxc,
  	NEW_ALLOC,_function_(choco_opposite_Equalxc,"choco_opposite_Equalxc"));
  
  choco.opposite->addMethod(list::domain(1,choco._NotEqualxc),choco._Equalxc,
  	NEW_ALLOC,_function_(choco_opposite_NotEqualxc,"choco_opposite_NotEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,choco._Equalxyc),choco._NotEqualxyc,
  	NEW_ALLOC,_function_(choco_opposite_Equalxyc,"choco_opposite_Equalxyc"));
  
  choco.opposite->addMethod(list::domain(1,choco._NotEqualxyc),choco._Equalxyc,
  	NEW_ALLOC,_function_(choco_opposite_NotEqualxyc,"choco_opposite_NotEqualxyc"));
  
  choco.opposite->addMethod(list::domain(1,choco._GreaterOrEqualxyc),choco._GreaterOrEqualxyc,
  	NEW_ALLOC,_function_(choco_opposite_GreaterOrEqualxyc,"choco_opposite_GreaterOrEqualxyc"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._GreaterOrEqualxyc,Kernel._integer),Kernel._integer,
  	0,_function_(choco_computeVarIdxInOpposite_GreaterOrEqualxyc,"choco_computeVarIdxInOpposite_GreaterOrEqualxyc"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._AbstractConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_computeVarIdxInOpposite_AbstractConstraint,"choco_computeVarIdxInOpposite_AbstractConstraint"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._IntConstraint,Kernel._integer),Kernel._integer,
  	SAFE_RESULT,_function_(choco_computeVarIdxInOpposite_IntConstraint,"choco_computeVarIdxInOpposite_IntConstraint"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._BinCompositeConstraint,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_computeVarIdxInOpposite_BinCompositeConstraint,"choco_computeVarIdxInOpposite_BinCompositeConstraint"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._LargeCompositeConstraint,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_computeVarIdxInOpposite_LargeCompositeConstraint,"choco_computeVarIdxInOpposite_LargeCompositeConstraint"));
  
  { global_variable * _CL_obj = (choco.SPVIEW = (global_variable *) Core._global_variable->instantiate("SPVIEW",choco.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (choco.SPTALK = (global_variable *) Core._global_variable->instantiate("SPTALK",choco.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { (choco._SetConstraint = ClaireClass::make("SetConstraint",choco._AbstractConstraint,choco.it));
    CL_ADD_SLOT(choco._SetConstraint,SetConstraint,choco.sv1,sv1,choco._SetVar,CNULL);
    CL_ADD_SLOT(choco._SetConstraint,SetConstraint,choco.idx1,idx1,Kernel._integer,0);
    } 
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,choco._SetConstraint,Kernel._integer),Kernel._integer,
  	SAFE_RESULT,_function_(choco_computeVarIdxInOpposite_SetConstraint,"choco_computeVarIdxInOpposite_SetConstraint"));
  
  { (choco._UnSetConstraint = ClaireClass::make("UnSetConstraint",choco._SetConstraint,choco.it));
    CL_ADD_SLOT(choco._UnSetConstraint,UnSetConstraint,choco.cste,cste,Kernel._integer,0);
    } 
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_UnSetConstraint,"choco_testIfCompletelyInstantiated_UnSetConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_UnSetConstraint,"choco_getNbVars_UnSetConstraint"));
  
  choco.getVar->addMethod(list::domain(2,choco._UnSetConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_UnSetConstraint_choco,"choco_getVar_UnSetConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_UnSetConstraint_choco,"choco_connect_UnSetConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_UnSetConstraint_choco,"choco_disconnect_UnSetConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_UnSetConstraint_choco,"choco_reconnect_UnSetConstraint_choco"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._UnSetConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_UnSetConstraint,"choco_assignIndices_UnSetConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._UnSetConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_UnSetConstraint,"choco_getConstraintIdx_UnSetConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._UnSetConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_UnSetConstraint,"choco_setConstraintIndex_UnSetConstraint"));
  
  choco.isActive->addMethod(list::domain(1,choco._UnSetConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_UnSetConstraint_choco,"choco_isActive_UnSetConstraint_choco"));
  
  { (choco._BinSetIntConstraint = ClaireClass::make("BinSetIntConstraint",choco._SetConstraint,choco.it));
    CL_ADD_SLOT(choco._BinSetIntConstraint,BinSetIntConstraint,choco.v2,v2,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._BinSetIntConstraint,BinSetIntConstraint,choco.idx2,idx2,Kernel._integer,0);
    } 
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_BinSetIntConstraint,"choco_testIfCompletelyInstantiated_BinSetIntConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_BinSetIntConstraint,"choco_getNbVars_BinSetIntConstraint"));
  
  choco.getVar->addMethod(list::domain(2,choco._BinSetIntConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_BinSetIntConstraint_choco,"choco_getVar_BinSetIntConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_BinSetIntConstraint_choco,"choco_connect_BinSetIntConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_BinSetIntConstraint_choco,"choco_disconnect_BinSetIntConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_BinSetIntConstraint_choco,"choco_reconnect_BinSetIntConstraint_choco"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._BinSetIntConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_BinSetIntConstraint,"choco_assignIndices_BinSetIntConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._BinSetIntConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_BinSetIntConstraint,"choco_getConstraintIdx_BinSetIntConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._BinSetIntConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_BinSetIntConstraint,"choco_setConstraintIndex_BinSetIntConstraint"));
  
  choco.isActive->addMethod(list::domain(1,choco._BinSetIntConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_BinSetIntConstraint_choco,"choco_isActive_BinSetIntConstraint_choco"));
  
  { (choco._BinSetConstraint = ClaireClass::make("BinSetConstraint",choco._SetConstraint,choco.it));
    CL_ADD_SLOT(choco._BinSetConstraint,BinSetConstraint,choco.sv2,sv2,choco._SetVar,CNULL);
    CL_ADD_SLOT(choco._BinSetConstraint,BinSetConstraint,choco.idx2,idx2,Kernel._integer,0);
    } 
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_BinSetConstraint,"choco_testIfCompletelyInstantiated_BinSetConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_BinSetConstraint,"choco_getNbVars_BinSetConstraint"));
  
  choco.getVar->addMethod(list::domain(2,choco._BinSetConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_BinSetConstraint_choco,"choco_getVar_BinSetConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_BinSetConstraint_choco,"choco_connect_BinSetConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_BinSetConstraint_choco,"choco_disconnect_BinSetConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_BinSetConstraint_choco,"choco_reconnect_BinSetConstraint_choco"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._BinSetConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_BinSetConstraint,"choco_assignIndices_BinSetConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._BinSetConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_BinSetConstraint,"choco_getConstraintIdx_BinSetConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._BinSetConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_BinSetConstraint,"choco_setConstraintIndex_BinSetConstraint"));
  
  choco.isActive->addMethod(list::domain(1,choco._BinSetConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_BinSetConstraint_choco,"choco_isActive_BinSetConstraint_choco"));
  
  { (choco._TernSetConstraint = ClaireClass::make("TernSetConstraint",choco._SetConstraint,choco.it));
    CL_ADD_SLOT(choco._TernSetConstraint,TernSetConstraint,choco.sv2,sv2,choco._SetVar,CNULL);
    CL_ADD_SLOT(choco._TernSetConstraint,TernSetConstraint,choco.sv3,sv3,choco._SetVar,CNULL);
    CL_ADD_SLOT(choco._TernSetConstraint,TernSetConstraint,choco.idx2,idx2,Kernel._integer,0);
    CL_ADD_SLOT(choco._TernSetConstraint,TernSetConstraint,choco.idx3,idx3,Kernel._integer,0);
    } 
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfCompletelyInstantiated_TernSetConstraint,"choco_testIfCompletelyInstantiated_TernSetConstraint"));
  
  choco.getNbVars->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._integer,
  	0,_function_(choco_getNbVars_TernSetConstraint,"choco_getNbVars_TernSetConstraint"));
  
  choco.getVar->addMethod(list::domain(2,choco._TernSetConstraint,Kernel._integer),choco._AbstractVar,
  	0,_function_(choco_getVar_TernSetConstraint_choco,"choco_getVar_TernSetConstraint_choco"));
  
  choco.connect->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_connect_TernSetConstraint_choco,"choco_connect_TernSetConstraint_choco"));
  
  choco.disconnect->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnect_TernSetConstraint_choco,"choco_disconnect_TernSetConstraint_choco"));
  
  choco.reconnect->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_reconnect_TernSetConstraint_choco,"choco_reconnect_TernSetConstraint_choco"));
  
  choco.assignIndices->addMethod(list::domain(3,choco._TernSetConstraint,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_TernSetConstraint,"choco_assignIndices_TernSetConstraint"));
  
  choco.getConstraintIdx->addMethod(list::domain(2,choco._TernSetConstraint,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getConstraintIdx_TernSetConstraint,"choco_getConstraintIdx_TernSetConstraint"));
  
  choco.setConstraintIndex->addMethod(list::domain(3,choco._TernSetConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setConstraintIndex_TernSetConstraint,"choco_setConstraintIndex_TernSetConstraint"));
  
  choco.isActive->addMethod(list::domain(1,choco._TernSetConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_isActive_TernSetConstraint_choco,"choco_isActive_TernSetConstraint_choco"));
  
  (choco._SetCard = ClaireClass::make("SetCard",choco._BinSetIntConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_SetCard,"choco_awakeOnKer_SetCard"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_SetCard,"choco_awakeOnEnv_SetCard"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInstSet_SetCard,"choco_awakeOnInstSet_SetCard"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_SetCard,"choco_awakeOnInf_SetCard"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_SetCard,"choco_awakeOnSup_SetCard"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_SetCard,"choco_awakeOnInst_SetCard"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._SetCard,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_SetCard,"choco_awakeOnRem_SetCard"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._SetCard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_SetCard,"choco_awakeOnVar_SetCard"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._SetCard),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_SetCard,"choco_askIfEntailed_SetCard"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._SetCard),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_SetCard,"choco_testIfSatisfied_SetCard"));
  
  choco.propagate->addMethod(list::domain(1,choco._SetCard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_SetCard,"choco_propagate_SetCard"));
  
  choco.awake->addMethod(list::domain(1,choco._SetCard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_SetCard_choco,"choco_awake_SetCard_choco"));
  
  choco.setCard->addMethod(list::domain(2,choco._SetVar,choco._IntVar),choco._SetCard,
  	NEW_ALLOC,_function_(choco_setCard_SetVar,"choco_setCard_SetVar"));
  
  (choco._MemberC = ClaireClass::make("MemberC",choco._UnSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._MemberC,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnKer_MemberC,"choco_awakeOnKer_MemberC"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._MemberC,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_MemberC,"choco_awakeOnEnv_MemberC"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._MemberC,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInstSet_MemberC,"choco_awakeOnInstSet_MemberC"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._MemberC),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_MemberC,"choco_askIfEntailed_MemberC"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._MemberC),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_testIfSatisfied_MemberC,"choco_testIfSatisfied_MemberC"));
  
  choco.propagate->addMethod(list::domain(1,choco._MemberC),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_MemberC,"choco_propagate_MemberC"));
  
  choco.awake->addMethod(list::domain(1,choco._MemberC),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_MemberC_choco,"choco_awake_MemberC_choco"));
  
  choco.memberOf->addMethod(list::domain(2,choco._SetVar,Kernel._integer),choco._MemberC,
  	NEW_ALLOC,_function_(choco_memberOf_SetVar1,"choco_memberOf_SetVar1"));
  
  (choco._NotMemberC = ClaireClass::make("NotMemberC",choco._UnSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._NotMemberC,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_NotMemberC,"choco_awakeOnKer_NotMemberC"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._NotMemberC,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnEnv_NotMemberC,"choco_awakeOnEnv_NotMemberC"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._NotMemberC,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInstSet_NotMemberC,"choco_awakeOnInstSet_NotMemberC"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._NotMemberC),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_NotMemberC,"choco_askIfEntailed_NotMemberC"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._NotMemberC),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_NotMemberC,"choco_testIfSatisfied_NotMemberC"));
  
  choco.propagate->addMethod(list::domain(1,choco._NotMemberC),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_NotMemberC,"choco_propagate_NotMemberC"));
  
  choco.awake->addMethod(list::domain(1,choco._NotMemberC),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_NotMemberC_choco,"choco_awake_NotMemberC_choco"));
  
  choco.notMemberOf->addMethod(list::domain(2,choco._SetVar,Kernel._integer),choco._NotMemberC,
  	NEW_ALLOC,_function_(choco_notMemberOf_SetVar1,"choco_notMemberOf_SetVar1"));
  
  choco.opposite->addMethod(list::domain(1,choco._MemberC),choco._NotMemberC,
  	NEW_ALLOC,_function_(choco_opposite_MemberC,"choco_opposite_MemberC"));
  
  choco.opposite->addMethod(list::domain(1,choco._NotMemberC),choco._MemberC,
  	NEW_ALLOC,_function_(choco_opposite_NotMemberC,"choco_opposite_NotMemberC"));
  
  (choco._MemberX = ClaireClass::make("MemberX",choco._BinSetIntConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnKer_MemberX,"choco_awakeOnKer_MemberX"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_MemberX,"choco_awakeOnEnv_MemberX"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_MemberX,"choco_awakeOnInstSet_MemberX"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_MemberX,"choco_awakeOnInst_MemberX"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnVar_MemberX,"choco_awakeOnVar_MemberX"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_MemberX,"choco_awakeOnSup_MemberX"));
  
  choco.awakeOnInf->addMethod(list::domain(2,choco._MemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_MemberX,"choco_awakeOnInf_MemberX"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._MemberX,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_MemberX,"choco_awakeOnRem_MemberX"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._MemberX),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_MemberX,"choco_askIfEntailed_MemberX"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._MemberX),Kernel._boolean,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_testIfSatisfied_MemberX,"choco_testIfSatisfied_MemberX"));
  
  choco.propagate->addMethod(list::domain(1,choco._MemberX),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_MemberX,"choco_propagate_MemberX"));
  
  choco.awake->addMethod(list::domain(1,choco._MemberX),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_MemberX_choco,"choco_awake_MemberX_choco"));
  
  choco.memberOf->addMethod(list::domain(2,choco._SetVar,choco._IntVar),choco._MemberX,
  	NEW_ALLOC,_function_(choco_memberOf_SetVar2,"choco_memberOf_SetVar2"));
  
  (choco._NotMemberX = ClaireClass::make("NotMemberX",choco._BinSetIntConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_NotMemberX,"choco_awakeOnKer_NotMemberX"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnEnv_NotMemberX,"choco_awakeOnEnv_NotMemberX"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInstSet_NotMemberX,"choco_awakeOnInstSet_NotMemberX"));
  
  choco.awakeOnSup->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_NotMemberX,"choco_awakeOnSup_NotMemberX"));
  
  choco.awakeOnInst->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInst_NotMemberX,"choco_awakeOnInst_NotMemberX"));
  
  choco.awakeOnRem->addMethod(list::domain(3,choco._NotMemberX,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_NotMemberX,"choco_awakeOnRem_NotMemberX"));
  
  choco.awakeOnVar->addMethod(list::domain(2,choco._NotMemberX,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnVar_NotMemberX,"choco_awakeOnVar_NotMemberX"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._NotMemberX),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_NotMemberX,"choco_askIfEntailed_NotMemberX"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._NotMemberX),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_NotMemberX,"choco_testIfSatisfied_NotMemberX"));
  
  choco.propagate->addMethod(list::domain(1,choco._NotMemberX),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_NotMemberX,"choco_propagate_NotMemberX"));
  
  choco.awake->addMethod(list::domain(1,choco._NotMemberX),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_NotMemberX_choco,"choco_awake_NotMemberX_choco"));
  
  choco.opposite->addMethod(list::domain(1,choco._MemberX),choco._NotMemberX,
  	NEW_ALLOC,_function_(choco_opposite_MemberX,"choco_opposite_MemberX"));
  
  choco.opposite->addMethod(list::domain(1,choco._NotMemberX),choco._MemberX,
  	NEW_ALLOC,_function_(choco_opposite_NotMemberX,"choco_opposite_NotMemberX"));
  
  choco.notMemberOf->addMethod(list::domain(2,choco._SetVar,choco._IntVar),choco._NotMemberX,
  	NEW_ALLOC,_function_(choco_notMemberOf_SetVar2,"choco_notMemberOf_SetVar2"));
  
  (choco._SetIntersection = ClaireClass::make("SetIntersection",choco._TernSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._SetIntersection,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_SetIntersection,"choco_awakeOnKer_SetIntersection"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._SetIntersection,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_SetIntersection,"choco_awakeOnEnv_SetIntersection"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._SetIntersection,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_SetIntersection,"choco_awakeOnInstSet_SetIntersection"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._SetIntersection),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_testIfSatisfied_SetIntersection,"choco_testIfSatisfied_SetIntersection"));
  
  choco.propagate->addMethod(list::domain(1,choco._SetIntersection),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_SetIntersection,"choco_propagate_SetIntersection"));
  
  choco.awake->addMethod(list::domain(1,choco._SetIntersection),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_SetIntersection_choco,"choco_awake_SetIntersection_choco"));
  
  choco.setintersection->addMethod(list::domain(3,choco._SetVar,choco._SetVar,choco._SetVar),choco._SetIntersection,
  	NEW_ALLOC,_function_(choco_setintersection_SetVar,"choco_setintersection_SetVar"));
  
  (choco._SetUnion = ClaireClass::make("SetUnion",choco._TernSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._SetUnion,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_SetUnion,"choco_awakeOnKer_SetUnion"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._SetUnion,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_SetUnion,"choco_awakeOnEnv_SetUnion"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._SetUnion,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_SetUnion,"choco_awakeOnInstSet_SetUnion"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._SetUnion),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_testIfSatisfied_SetUnion,"choco_testIfSatisfied_SetUnion"));
  
  choco.propagate->addMethod(list::domain(1,choco._SetUnion),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_SetUnion,"choco_propagate_SetUnion"));
  
  choco.awake->addMethod(list::domain(1,choco._SetUnion),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_SetUnion_choco,"choco_awake_SetUnion_choco"));
  
  choco.setunion->addMethod(list::domain(3,choco._SetVar,choco._SetVar,choco._SetVar),choco._SetUnion,
  	NEW_ALLOC,_function_(choco_setunion_SetVar,"choco_setunion_SetVar"));
  
  (choco._SubSet = ClaireClass::make("SubSet",choco._BinSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._SubSet,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_SubSet,"choco_awakeOnKer_SubSet"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._SubSet,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnEnv_SubSet,"choco_awakeOnEnv_SubSet"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._SubSet,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_SubSet,"choco_awakeOnInstSet_SubSet"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._SubSet),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_SubSet,"choco_askIfEntailed_SubSet"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._SubSet),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_testIfSatisfied_SubSet,"choco_testIfSatisfied_SubSet"));
  
  choco.propagate->addMethod(list::domain(1,choco._SubSet),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_SubSet,"choco_propagate_SubSet"));
  
  choco.awake->addMethod(list::domain(1,choco._SubSet),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_SubSet_choco,"choco_awake_SubSet_choco"));
  
  choco.subset->addMethod(list::domain(2,choco._SetVar,choco._SetVar),choco._SubSet,
  	NEW_ALLOC,_function_(choco_subset_SetVar,"choco_subset_SetVar"));
  
  (choco._DisjointSets = ClaireClass::make("DisjointSets",choco._BinSetConstraint,choco.it));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._DisjointSets,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnKer_DisjointSets,"choco_awakeOnKer_DisjointSets"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._DisjointSets,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnEnv_DisjointSets,"choco_awakeOnEnv_DisjointSets"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._DisjointSets,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_DisjointSets,"choco_awakeOnInstSet_DisjointSets"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._DisjointSets),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_DisjointSets,"choco_askIfEntailed_DisjointSets"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._DisjointSets),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_testIfSatisfied_DisjointSets,"choco_testIfSatisfied_DisjointSets"));
  
  choco.propagate->addMethod(list::domain(1,choco._DisjointSets),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_DisjointSets,"choco_propagate_DisjointSets"));
  
  choco.awake->addMethod(list::domain(1,choco._DisjointSets),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_DisjointSets_choco,"choco_awake_DisjointSets_choco"));
  
  choco.disjoint->addMethod(list::domain(2,choco._SetVar,choco._SetVar),choco._DisjointSets,
  	NEW_ALLOC,_function_(choco_disjoint_SetVar,"choco_disjoint_SetVar"));
  
  { (choco._OverlappingSets = ClaireClass::make("OverlappingSets",choco._BinSetConstraint,choco.it));
    CL_ADD_SLOT(choco._OverlappingSets,OverlappingSets,choco.emptyKernelIntersection,emptyKernelIntersection,Kernel._boolean,Kernel.ctrue);
    } 
  
  (*Kernel.store)(_oid_(choco.emptyKernelIntersection));
  
  choco.awakeOnKer->addMethod(list::domain(2,choco._OverlappingSets,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnKer_OverlappingSets,"choco_awakeOnKer_OverlappingSets"));
  
  choco.awakeOnEnv->addMethod(list::domain(2,choco._OverlappingSets,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnEnv_OverlappingSets,"choco_awakeOnEnv_OverlappingSets"));
  
  choco.awakeOnInstSet->addMethod(list::domain(2,choco._OverlappingSets,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInstSet_OverlappingSets,"choco_awakeOnInstSet_OverlappingSets"));
  
  choco.askIfEntailed->addMethod(list::domain(1,choco._OverlappingSets),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_OverlappingSets,"choco_askIfEntailed_OverlappingSets"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,choco._OverlappingSets),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_testIfSatisfied_OverlappingSets,"choco_testIfSatisfied_OverlappingSets"));
  
  choco.propagate->addMethod(list::domain(1,choco._OverlappingSets),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_OverlappingSets,"choco_propagate_OverlappingSets"));
  
  choco.awake->addMethod(list::domain(1,choco._OverlappingSets),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awake_OverlappingSets_choco,"choco_awake_OverlappingSets_choco"));
  
  choco.overlap->addMethod(list::domain(2,choco._SetVar,choco._SetVar),choco._OverlappingSets,
  	NEW_ALLOC,_function_(choco_overlap_SetVar,"choco_overlap_SetVar"));
  
  choco.opposite->addMethod(list::domain(1,choco._DisjointSets),choco._OverlappingSets,
  	NEW_ALLOC,_function_(choco_opposite_DisjointSets,"choco_opposite_DisjointSets"));
  
  choco.opposite->addMethod(list::domain(1,choco._OverlappingSets),choco._DisjointSets,
  	NEW_ALLOC,_function_(choco_opposite_OverlappingSets,"choco_opposite_OverlappingSets"));
  
  { (choco.selectBranchingObject = property::make("selectBranchingObject",3,choco.it,choco._AbstractBranching,3));
    (choco.selectBranchingObject->range = Kernel._any);
    ;} 
  
  { (choco.goDownBranch = property::make("goDownBranch",3,choco.it,choco._AbstractBranching,6));
    (choco.goDownBranch->range = Kernel._void);
    ;} 
  
  { (choco.traceDownBranch = property::make("traceDownBranch",3,choco.it,choco._AbstractBranching,7));
    (choco.traceDownBranch->range = Kernel._void);
    ;} 
  
  { (choco.getFirstBranch = property::make("getFirstBranch",3,choco.it,choco._AbstractBranching,4));
    (choco.getFirstBranch->range = Kernel._integer);
    ;} 
  
  { (choco.getNextBranch = property::make("getNextBranch",3,choco.it,choco._AbstractBranching,5));
    (choco.getNextBranch->range = Kernel._integer);
    ;} 
  
  { (choco.goUpBranch = property::make("goUpBranch",3,choco.it,choco._AbstractBranching,8));
    (choco.goUpBranch->range = Kernel._void);
    ;} 
  
  { (choco.traceUpBranch = property::make("traceUpBranch",3,choco.it,choco._AbstractBranching,9));
    (choco.traceUpBranch->range = Kernel._void);
    ;} 
  
  { (choco.finishedBranching = property::make("finishedBranching",3,choco.it,choco._AbstractBranching,10));
    (choco.finishedBranching->range = Kernel._boolean);
    ;} 
  
  { (choco.branchOn = property::make("branchOn",1,choco.it,choco._AbstractBranching,52));
    (choco.branchOn->range = Kernel._boolean);
    ;} 
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._AbstractBranching),Kernel._any,
  	0,_function_(choco_selectBranchingObject_AbstractBranching_choco,"choco_selectBranchingObject_AbstractBranching_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_goDownBranch_AbstractBranching_choco,"choco_goDownBranch_AbstractBranching_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_traceDownBranch_AbstractBranching_choco,"choco_traceDownBranch_AbstractBranching_choco"));
  
  choco.getFirstBranch->addMethod(list::domain(2,choco._AbstractBranching,Kernel._any),Kernel._integer,
  	0,_function_(choco_getFirstBranch_AbstractBranching_choco,"choco_getFirstBranch_AbstractBranching_choco"));
  
  choco.getNextBranch->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getNextBranch_AbstractBranching_choco,"choco_getNextBranch_AbstractBranching_choco"));
  
  choco.goUpBranch->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_goUpBranch_AbstractBranching_choco,"choco_goUpBranch_AbstractBranching_choco"));
  
  choco.traceUpBranch->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_traceUpBranch_AbstractBranching_choco,"choco_traceUpBranch_AbstractBranching_choco"));
  
  choco.finishedBranching->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_finishedBranching_AbstractBranching_choco,"choco_finishedBranching_AbstractBranching_choco"));
  
  choco.branchOn->addMethod(list::domain(3,choco._AbstractBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_branchOn_AbstractBranching,"choco_branchOn_AbstractBranching"));
  
  { abstract_property(choco.selectBranchingObject);
    abstract_property(choco.goDownBranch);
    abstract_property(choco.traceDownBranch);
    abstract_property(choco.getFirstBranch);
    abstract_property(choco.getNextBranch);
    abstract_property(choco.goUpBranch);
    abstract_property(choco.traceUpBranch);
    abstract_property(choco.finishedBranching);
    } 
  
  ;
  { ;} 
  
  (choco._LargeBranching = ClaireClass::make("LargeBranching",choco._AbstractBranching,choco.it));
  
  (choco._BinBranching = ClaireClass::make("BinBranching",choco._AbstractBranching,choco.it));
  
  choco.goUpBranch->addMethod(list::domain(3,choco._LargeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	0,_function_(choco_goUpBranch_LargeBranching_choco,"choco_goUpBranch_LargeBranching_choco"));
  
  choco.traceUpBranch->addMethod(list::domain(3,choco._LargeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	0,_function_(choco_traceUpBranch_LargeBranching_choco,"choco_traceUpBranch_LargeBranching_choco"));
  
  choco.getFirstBranch->addMethod(list::domain(2,choco._LargeBranching,Kernel._any),Kernel._integer,
  	0,_function_(choco_getFirstBranch_LargeBranching_choco,"choco_getFirstBranch_LargeBranching_choco"));
  
  choco.getNextBranch->addMethod(list::domain(3,choco._LargeBranching,Kernel._any,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getNextBranch_LargeBranching_choco,"choco_getNextBranch_LargeBranching_choco"));
  
  choco.finishedBranching->addMethod(list::domain(3,choco._LargeBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_finishedBranching_LargeBranching_choco,"choco_finishedBranching_LargeBranching_choco"));
  
  { (choco._CompositeBranching = ClaireClass::make("CompositeBranching",choco._LargeBranching,choco.it));
    CL_ADD_SLOT(choco._CompositeBranching,CompositeBranching,choco.alternatives,alternatives,choco._AbstractBranching,CNULL);
    } 
  
  choco.selectBranching->addMethod(list::domain(1,choco._CompositeBranching),choco._AbstractBranching,
  	RETURN_ARG,_function_(choco_selectBranching_CompositeBranching,"choco_selectBranching_CompositeBranching"));
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._CompositeBranching),Kernel._any,
  	NEW_ALLOC,_function_(choco_selectBranchingObject_CompositeBranching_choco,"choco_selectBranchingObject_CompositeBranching_choco"));
  
  choco.getFirstBranch->addMethod(list::domain(2,choco._CompositeBranching,Kernel._any),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getFirstBranch_CompositeBranching_choco,"choco_getFirstBranch_CompositeBranching_choco"));
  
  choco.getNextBranch->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getNextBranch_CompositeBranching_choco,"choco_getNextBranch_CompositeBranching_choco"));
  
  choco.goUpBranch->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_goUpBranch_CompositeBranching_choco,"choco_goUpBranch_CompositeBranching_choco"));
  
  choco.traceUpBranch->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_traceUpBranch_CompositeBranching_choco,"choco_traceUpBranch_CompositeBranching_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_goDownBranch_CompositeBranching_choco,"choco_goDownBranch_CompositeBranching_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_traceDownBranch_CompositeBranching_choco,"choco_traceDownBranch_CompositeBranching_choco"));
  
  choco.finishedBranching->addMethod(list::domain(3,choco._CompositeBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_finishedBranching_CompositeBranching_choco,"choco_finishedBranching_CompositeBranching_choco"));
  
  { (choco._VarSelector = ClaireClass::make("VarSelector",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._VarSelector,VarSelector,choco.branching,branching,choco._AbstractBranching,CNULL);
    } 
  
  choco.selectVar->addMethod(list::domain(1,choco._VarSelector),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_selectVar_VarSelector,"choco_selectVar_VarSelector"));
  
  { ;} 
  
  { (choco._MinDomain = ClaireClass::make("MinDomain",choco._VarSelector,choco.it));
    CL_ADD_SLOT(choco._MinDomain,MinDomain,choco.problem,problem,choco._Problem,CNULL);
    CL_ADD_SLOT(choco._MinDomain,MinDomain,Core.vars,vars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  choco.selectVar->addMethod(list::domain(1,choco._MinDomain),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_selectVar_MinDomain,"choco_selectVar_MinDomain"));
  
  { (choco._MaxDeg = ClaireClass::make("MaxDeg",choco._VarSelector,choco.it));
    CL_ADD_SLOT(choco._MaxDeg,MaxDeg,choco.problem,problem,choco._Problem,CNULL);
    CL_ADD_SLOT(choco._MaxDeg,MaxDeg,Core.vars,vars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  choco.selectVar->addMethod(list::domain(1,choco._MaxDeg),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_selectVar_MaxDeg,"choco_selectVar_MaxDeg"));
  
  { (choco._DomDeg = ClaireClass::make("DomDeg",choco._VarSelector,choco.it));
    CL_ADD_SLOT(choco._DomDeg,DomDeg,choco.problem,problem,choco._Problem,CNULL);
    CL_ADD_SLOT(choco._DomDeg,DomDeg,Core.vars,vars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  choco.selectVar->addMethod(list::domain(1,choco._DomDeg),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_selectVar_DomDeg,"choco_selectVar_DomDeg"));
  
  { (choco._StaticVarOrder = ClaireClass::make("StaticVarOrder",choco._VarSelector,choco.it));
    CL_ADD_SLOT(choco._StaticVarOrder,StaticVarOrder,Core.vars,vars,nth_class1(Kernel._list,choco._IntVar),CNULL);
    } 
  
  choco.selectVar->addMethod(list::domain(1,choco._StaticVarOrder),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_selectVar_StaticVarOrder,"choco_selectVar_StaticVarOrder"));
  
  { (choco._ValIterator = ClaireClass::make("ValIterator",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._ValIterator,ValIterator,choco.branching,branching,choco._AbstractBranching,CNULL);
    } 
  
  choco.isOver->addMethod(list::domain(3,choco._ValIterator,choco._IntVar,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isOver_ValIterator,"choco_isOver_ValIterator"));
  
  choco.getFirstVal->addMethod(list::domain(2,choco._ValIterator,choco._IntVar),Kernel._integer,
  	0,_function_(choco_getFirstVal_ValIterator,"choco_getFirstVal_ValIterator"));
  
  choco.getNextVal->addMethod(list::domain(3,choco._ValIterator,choco._IntVar,Kernel._integer),Kernel._integer,
  	0,_function_(choco_getNextVal_ValIterator,"choco_getNextVal_ValIterator"));
  
  { ;} 
  
  (choco._IncreasingDomain = ClaireClass::make("IncreasingDomain",choco._ValIterator,choco.it));
  
  choco.isOver->addMethod(list::domain(3,choco._IncreasingDomain,choco._IntVar,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isOver_IncreasingDomain,"choco_isOver_IncreasingDomain"));
  
  choco.getFirstVal->addMethod(list::domain(2,choco._IncreasingDomain,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getFirstVal_IncreasingDomain,"choco_getFirstVal_IncreasingDomain"));
  
  choco.getNextVal->addMethod(list::domain(3,choco._IncreasingDomain,choco._IntVar,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getNextVal_IncreasingDomain,"choco_getNextVal_IncreasingDomain"));
  
  (choco._DecreasingDomain = ClaireClass::make("DecreasingDomain",choco._ValIterator,choco.it));
  
  choco.isOver->addMethod(list::domain(3,choco._DecreasingDomain,choco._IntVar,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_isOver_DecreasingDomain,"choco_isOver_DecreasingDomain"));
  
  choco.getFirstVal->addMethod(list::domain(2,choco._DecreasingDomain,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getFirstVal_DecreasingDomain,"choco_getFirstVal_DecreasingDomain"));
  
  choco.getNextVal->addMethod(list::domain(3,choco._DecreasingDomain,choco._IntVar,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getNextVal_DecreasingDomain,"choco_getNextVal_DecreasingDomain"));
  
  { (choco._ValSelector = ClaireClass::make("ValSelector",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._ValSelector,ValSelector,choco.branching,branching,choco._AbstractBranching,CNULL);
    } 
  
  choco.getBestVal->addMethod(list::domain(2,choco._ValSelector,choco._IntVar),Kernel._integer,
  	0,_function_(choco_getBestVal_ValSelector,"choco_getBestVal_ValSelector"));
  
  { ;} 
  
  (choco._MidValHeuristic = ClaireClass::make("MidValHeuristic",choco._ValSelector,choco.it));
  
  (choco._MinValHeuristic = ClaireClass::make("MinValHeuristic",choco._ValSelector,choco.it));
  
  (choco._MaxValHeuristic = ClaireClass::make("MaxValHeuristic",choco._ValSelector,choco.it));
  
  choco.getBestVal->addMethod(list::domain(2,choco._MidValHeuristic,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getBestVal_MidValHeuristic,"choco_getBestVal_MidValHeuristic"));
  
  choco.getBestVal->addMethod(list::domain(2,choco._MinValHeuristic,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getBestVal_MinValHeuristic,"choco_getBestVal_MinValHeuristic"));
  
  choco.getBestVal->addMethod(list::domain(2,choco._MaxValHeuristic,choco._IntVar),Kernel._integer,
  	RETURN_ARG,_function_(choco_getBestVal_MaxValHeuristic,"choco_getBestVal_MaxValHeuristic"));
  
  { (choco._AssignVar = ClaireClass::make("AssignVar",choco._LargeBranching,choco.it));
    CL_ADD_SLOT(choco._AssignVar,AssignVar,choco.varHeuristic,varHeuristic,choco._VarSelector,CNULL);
    CL_ADD_SLOT(choco._AssignVar,AssignVar,choco.valHeuristic,valHeuristic,choco._ValIterator,CNULL);
    } 
  
  { (choco._SplitDomain = ClaireClass::make("SplitDomain",choco._BinBranching,choco.it));
    CL_ADD_SLOT(choco._SplitDomain,SplitDomain,choco.varHeuristic,varHeuristic,choco._VarSelector,CNULL);
    CL_ADD_SLOT(choco._SplitDomain,SplitDomain,choco.valHeuristic,valHeuristic,choco._ValSelector,CNULL);
    CL_ADD_SLOT(choco._SplitDomain,SplitDomain,choco.dichotomyThreshold,dichotomyThreshold,Kernel._integer,5);
    } 
  
  { (choco._AssignOrForbid = ClaireClass::make("AssignOrForbid",choco._BinBranching,choco.it));
    CL_ADD_SLOT(choco._AssignOrForbid,AssignOrForbid,choco.varHeuristic,varHeuristic,choco._VarSelector,CNULL);
    CL_ADD_SLOT(choco._AssignOrForbid,AssignOrForbid,choco.valHeuristic,valHeuristic,choco._ValSelector,CNULL);
    } 
  
  { (choco._SettleBinDisjunction = ClaireClass::make("SettleBinDisjunction",choco._BinBranching,choco.it));
    CL_ADD_SLOT(choco._SettleBinDisjunction,SettleBinDisjunction,choco.disjunctions,disjunctions,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._Disjunction))))),CNULL);
    } 
  
  (choco._Solve = ClaireClass::make("Solve",choco._GlobalSearchSolver,choco.it));
  
  { (choco._AbstractOptimize = ClaireClass::make("AbstractOptimize",choco._GlobalSearchSolver,choco.it));
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.doMaximize,doMaximize,Kernel._boolean,CNULL);
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.objective,objective,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.lowerBound,lowerBound,Kernel._integer,-99999999);
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.upperBound,upperBound,Kernel._integer,99999999);
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.targetLowerBound,targetLowerBound,Kernel._integer,-99999999);
    CL_ADD_SLOT(choco._AbstractOptimize,AbstractOptimize,choco.targetUpperBound,targetUpperBound,Kernel._integer,99999999);
    } 
  
  { (choco.getObjectiveValue = property::make("getObjectiveValue",3,choco.it,Kernel._any,0));
    (choco.getObjectiveValue->range = Kernel._integer);
    ;} 
  
  choco.getObjectiveValue->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._integer,
  	RETURN_ARG,_function_(choco_getObjectiveValue_AbstractOptimize_choco,"choco_getObjectiveValue_AbstractOptimize_choco"));
  
  abstract_property(choco.getObjectiveValue);
  
  choco.getBestObjectiveValue->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._integer,
  	0,_function_(choco_getBestObjectiveValue_AbstractOptimize,"choco_getBestObjectiveValue_AbstractOptimize"));
  
  choco.getObjectiveTarget->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._integer,
  	0,_function_(choco_getObjectiveTarget_AbstractOptimize,"choco_getObjectiveTarget_AbstractOptimize"));
  
  choco.initBounds->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_initBounds_AbstractOptimize,"choco_initBounds_AbstractOptimize"));
  
  choco.getFeasibility->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._boolean,
  	0,_function_(choco_getFeasibility_GlobalSearchSolver,"choco_getFeasibility_GlobalSearchSolver"));
  
  choco.mayExpandNewNode->addMethod(list::domain(2,choco._GlobalSearchLimit,choco._GlobalSearchSolver),Kernel._boolean,
  	0,_function_(choco_mayExpandNewNode_GlobalSearchLimit,"choco_mayExpandNewNode_GlobalSearchLimit"));
  
  choco.mayExpandNewNode->addMethod(list::domain(2,choco._NodeLimit,choco._GlobalSearchSolver),Kernel._boolean,
  	0,_function_(choco_mayExpandNewNode_NodeLimit,"choco_mayExpandNewNode_NodeLimit"));
  
  choco.mayExpandNewNode->addMethod(list::domain(2,choco._BacktrackLimit,choco._GlobalSearchSolver),Kernel._boolean,
  	0,_function_(choco_mayExpandNewNode_BacktrackLimit,"choco_mayExpandNewNode_BacktrackLimit"));
  
  choco.mayExpandNewNode->addMethod(list::domain(2,choco._TimeLimit,choco._GlobalSearchSolver),Kernel._boolean,
  	0,_function_(choco_mayExpandNewNode_TimeLimit,"choco_mayExpandNewNode_TimeLimit"));
  
  ;
  (choco._BranchAndBound = ClaireClass::make("BranchAndBound",choco._AbstractOptimize,choco.it));
  
  { (choco._OptimizeWithRestarts = ClaireClass::make("OptimizeWithRestarts",choco._AbstractOptimize,choco.it));
    CL_ADD_SLOT(choco._OptimizeWithRestarts,OptimizeWithRestarts,choco.nbIter,nbIter,Kernel._integer,0);
    CL_ADD_SLOT(choco._OptimizeWithRestarts,OptimizeWithRestarts,choco.baseNbSol,baseNbSol,Kernel._integer,0);
    CL_ADD_SLOT(choco._OptimizeWithRestarts,OptimizeWithRestarts,choco.nbBkTot,nbBkTot,Kernel._integer,0);
    CL_ADD_SLOT(choco._OptimizeWithRestarts,OptimizeWithRestarts,choco.nbNdTot,nbNdTot,Kernel._integer,0);
    } 
  
  choco.copyParameters->addMethod(list::domain(2,choco._GlobalSearchSolver,choco._GlobalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_copyParameters_GlobalSearchSolver,"choco_copyParameters_GlobalSearchSolver"));
  
  choco.attach->addMethod(list::domain(2,choco._GlobalSearchSolver,choco._Problem),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_attach_GlobalSearchSolver,"choco_attach_GlobalSearchSolver"));
  
  (choco.composeGlobalSearch->addMethod(list::domain(2,choco._GlobalSearchSolver,nth_class1(Kernel._list,choco._AbstractBranching)),choco._GlobalSearchSolver,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_composeGlobalSearch_GlobalSearchSolver,"choco_composeGlobalSearch_GlobalSearchSolver"))->typing = _oid_(_function_(choco_composeGlobalSearch_GlobalSearchSolver_type,"choco_composeGlobalSearch_GlobalSearchSolver_type")));
  
  choco.getSmallestDomainUnassignedVar->addMethod(list::domain(1,choco._Problem),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getSmallestDomainUnassignedVar_Problem,"choco_getSmallestDomainUnassignedVar_Problem"));
  
  choco.getSmallestDomainUnassignedVar->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getSmallestDomainUnassignedVar_list,"choco_getSmallestDomainUnassignedVar_list"));
  
  choco.getDomOverDegBestUnassignedVar->addMethod(list::domain(1,choco._Problem),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getDomOverDegBestUnassignedVar_Problem,"choco_getDomOverDegBestUnassignedVar_Problem"));
  
  choco.getDomOverDegBestUnassignedVar->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getDomOverDegBestUnassignedVar_list,"choco_getDomOverDegBestUnassignedVar_list"));
  
  choco.getMostConstrainedUnassignedVar->addMethod(list::domain(1,choco._Problem),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_getMostConstrainedUnassignedVar_Problem,"choco_getMostConstrainedUnassignedVar_Problem"));
  
  choco.getMostConstrainedUnassignedVar->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_getMostConstrainedUnassignedVar_list,"choco_getMostConstrainedUnassignedVar_list"));
  
  choco.storeCurrentSolution->addMethod(list::domain(1,choco._Solver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_storeCurrentSolution_Solver,"choco_storeCurrentSolution_Solver"));
  
  choco.makeSolutionFromCurrentState->addMethod(list::domain(1,choco._Solver),choco._Solution,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSolutionFromCurrentState_Solver,"choco_makeSolutionFromCurrentState_Solver"));
  
  choco.storeSolution->addMethod(list::domain(2,choco._Solver,choco._Solution),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_storeSolution_Solver,"choco_storeSolution_Solver"));
  
  choco.existsSolution->addMethod(list::domain(1,choco._Solver),Kernel._boolean,
  	0,_function_(choco_existsSolution_Solver,"choco_existsSolution_Solver"));
  
  choco.restoreBestSolution->addMethod(list::domain(1,choco._Solver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_restoreBestSolution_Solver,"choco_restoreBestSolution_Solver"));
  
  choco.restoreSolution->addMethod(list::domain(1,choco._Solution),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_restoreSolution_Solution,"choco_restoreSolution_Solution"));
  
  choco.showSolution->addMethod(list::domain(1,choco._Solver),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_showSolution_Solver,"choco_showSolution_Solver"));
  
  { (choco.explore = property::make("explore",1,choco.it,Kernel._any,0));
    ;} 
  
  choco.explore->addMethod(list::domain(2,choco._AbstractBranching,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_explore_AbstractBranching,"choco_explore_AbstractBranching"));
  
  choco.branchOn->addMethod(list::domain(3,choco._LargeBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_branchOn_LargeBranching,"choco_branchOn_LargeBranching"));
  
  choco.branchOn->addMethod(list::domain(3,choco._BinBranching,Kernel._any,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_branchOn_BinBranching,"choco_branchOn_BinBranching"));
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._SplitDomain),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_selectBranchingObject_SplitDomain_choco,"choco_selectBranchingObject_SplitDomain_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._SplitDomain,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_goDownBranch_SplitDomain_choco,"choco_goDownBranch_SplitDomain_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._SplitDomain,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_traceDownBranch_SplitDomain_choco,"choco_traceDownBranch_SplitDomain_choco"));
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._AssignOrForbid),Kernel._any,
  	NEW_ALLOC,_function_(choco_selectBranchingObject_AssignOrForbid_choco,"choco_selectBranchingObject_AssignOrForbid_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._AssignOrForbid,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_goDownBranch_AssignOrForbid_choco,"choco_goDownBranch_AssignOrForbid_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._AssignOrForbid,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_traceDownBranch_AssignOrForbid_choco,"choco_traceDownBranch_AssignOrForbid_choco"));
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._AssignVar),Kernel._any,
  	NEW_ALLOC,_function_(choco_selectBranchingObject_AssignVar_choco,"choco_selectBranchingObject_AssignVar_choco"));
  
  choco.finishedBranching->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_finishedBranching_AssignVar_choco,"choco_finishedBranching_AssignVar_choco"));
  
  choco.getFirstBranch->addMethod(list::domain(2,choco._AssignVar,Kernel._any),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getFirstBranch_AssignVar_choco,"choco_getFirstBranch_AssignVar_choco"));
  
  choco.getNextBranch->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_getNextBranch_AssignVar_choco,"choco_getNextBranch_AssignVar_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_goDownBranch_AssignVar_choco,"choco_goDownBranch_AssignVar_choco"));
  
  choco.goUpBranch->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_goUpBranch_AssignVar_choco,"choco_goUpBranch_AssignVar_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_traceDownBranch_AssignVar_choco,"choco_traceDownBranch_AssignVar_choco"));
  
  choco.traceUpBranch->addMethod(list::domain(3,choco._AssignVar,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_traceUpBranch_AssignVar_choco,"choco_traceUpBranch_AssignVar_choco"));
  
  choco.selectBranchingObject->addMethod(list::domain(1,choco._SettleBinDisjunction),Kernel._any,
  	0,_function_(choco_selectBranchingObject_SettleBinDisjunction_choco,"choco_selectBranchingObject_SettleBinDisjunction_choco"));
  
  choco.goDownBranch->addMethod(list::domain(3,choco._SettleBinDisjunction,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_goDownBranch_SettleBinDisjunction_choco,"choco_goDownBranch_SettleBinDisjunction_choco"));
  
  choco.traceDownBranch->addMethod(list::domain(3,choco._SettleBinDisjunction,Kernel._any,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_traceDownBranch_SettleBinDisjunction_choco,"choco_traceDownBranch_SettleBinDisjunction_choco"));
  
  choco.newTreeSearch->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	SLOT_UPDATE,_function_(choco_newTreeSearch_GlobalSearchSolver,"choco_newTreeSearch_GlobalSearchSolver"));
  
  choco.endTreeSearch->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	NEW_ALLOC,_function_(choco_endTreeSearch_GlobalSearchSolver,"choco_endTreeSearch_GlobalSearchSolver"));
  
  choco.newNode->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_newNode_GlobalSearchSolver,"choco_newNode_GlobalSearchSolver"));
  
  choco.endNode->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_endNode_GlobalSearchSolver,"choco_endNode_GlobalSearchSolver"));
  
  choco.recordSolution->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_recordSolution_GlobalSearchSolver,"choco_recordSolution_GlobalSearchSolver"));
  
  choco.postDynamicCut->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	0,_function_(choco_postDynamicCut_GlobalSearchSolver,"choco_postDynamicCut_GlobalSearchSolver"));
  
  choco.recordSolution->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_recordSolution_AbstractOptimize,"choco_recordSolution_AbstractOptimize"));
  
  choco.setBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setBound_AbstractOptimize,"choco_setBound_AbstractOptimize"));
  
  choco.setTargetBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setTargetBound_AbstractOptimize,"choco_setTargetBound_AbstractOptimize"));
  
  choco.setTargetLowerBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_setTargetLowerBound_AbstractOptimize,"choco_setTargetLowerBound_AbstractOptimize"));
  
  choco.setTargetUpperBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_setTargetUpperBound_AbstractOptimize,"choco_setTargetUpperBound_AbstractOptimize"));
  
  choco.postTargetBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_postTargetBound_AbstractOptimize,"choco_postTargetBound_AbstractOptimize"));
  
  choco.postTargetLowerBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC,_function_(choco_postTargetLowerBound_AbstractOptimize,"choco_postTargetLowerBound_AbstractOptimize"));
  
  choco.postTargetUpperBound->addMethod(list::domain(1,choco._AbstractOptimize),Kernel._void,
  	NEW_ALLOC,_function_(choco_postTargetUpperBound_AbstractOptimize,"choco_postTargetUpperBound_AbstractOptimize"));
  
  choco.newTreeSearch->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_newTreeSearch_OptimizeWithRestarts,"choco_newTreeSearch_OptimizeWithRestarts"));
  
  choco.endTreeSearch->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	SLOT_UPDATE,_function_(choco_endTreeSearch_OptimizeWithRestarts,"choco_endTreeSearch_OptimizeWithRestarts"));
  
  choco.postDynamicCut->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	0,_function_(choco_postDynamicCut_OptimizeWithRestarts,"choco_postDynamicCut_OptimizeWithRestarts"));
  
  choco.run->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._void,
  	SAFE_RESULT,_function_(choco_run_GlobalSearchSolver,"choco_run_GlobalSearchSolver"));
  
  choco.run->addMethod(list::domain(1,choco._Solve),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_run_Solve,"choco_run_Solve"));
  
  choco.run->addMethod(list::domain(1,choco._BranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_run_BranchAndBound,"choco_run_BranchAndBound"));
  
  choco.postDynamicCut->addMethod(list::domain(1,choco._BranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postDynamicCut_BranchAndBound,"choco_postDynamicCut_BranchAndBound"));
  
  choco.newTreeSearch->addMethod(list::domain(1,choco._BranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_newTreeSearch_BranchAndBound,"choco_newTreeSearch_BranchAndBound"));
  
  choco.endTreeSearch->addMethod(list::domain(1,choco._BranchAndBound),Kernel._void,
  	NEW_ALLOC,_function_(choco_endTreeSearch_BranchAndBound,"choco_endTreeSearch_BranchAndBound"));
  
  choco.newLoop->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_newLoop_OptimizeWithRestarts,"choco_newLoop_OptimizeWithRestarts"));
  
  choco.endLoop->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	NEW_ALLOC,_function_(choco_endLoop_OptimizeWithRestarts,"choco_endLoop_OptimizeWithRestarts"));
  
  choco.recordNoSolution->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_recordNoSolution_OptimizeWithRestarts,"choco_recordNoSolution_OptimizeWithRestarts"));
  
  choco.oneMoreLoop->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._boolean,
  	0,_function_(choco_oneMoreLoop_OptimizeWithRestarts,"choco_oneMoreLoop_OptimizeWithRestarts"));
  
  choco.run->addMethod(list::domain(1,choco._OptimizeWithRestarts),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_run_OptimizeWithRestarts,"choco_run_OptimizeWithRestarts"));
  
  choco.makeConflictCount->addMethod(list::domain(1,Kernel._void),choco._ConflictCount,
  	NEW_ALLOC,_function_(choco_makeConflictCount_void,"choco_makeConflictCount_void"));
  
  choco.attachInvariantEngine->addMethod(list::domain(2,choco._Problem,choco._InvariantEngine),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_attachInvariantEngine_Problem,"choco_attachInvariantEngine_Problem"));
  
  choco.unitaryDeltaConflict->addMethod(list::domain(4,choco._IntVar,
    Kernel._integer,
    Kernel._integer,
    choco._AbstractConstraint),Kernel._integer,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_unitaryDeltaConflict_IntVar,"choco_unitaryDeltaConflict_IntVar"));
  
  choco.deltaNbConflicts->addMethod(list::domain(3,choco._IntVar,Kernel._integer,Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_deltaNbConflicts_IntVar,"choco_deltaNbConflicts_IntVar"));
  
  choco.getMinConflictValue->addMethod(list::domain(1,choco._IntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_getMinConflictValue_IntVar,"choco_getMinConflictValue_IntVar"));
  
  choco.computeConflictAccount->addMethod(list::domain(1,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_computeConflictAccount_ConflictCount,"choco_computeConflictAccount_ConflictCount"));
  
  choco.resetInvariants->addMethod(list::domain(1,choco._InvariantEngine),Kernel._void,
  	SAFE_RESULT,_function_(choco_resetInvariants_InvariantEngine,"choco_resetInvariants_InvariantEngine"));
  
  choco.resetInvariants->addMethod(list::domain(1,choco._ConflictCount),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_resetInvariants_ConflictCount,"choco_resetInvariants_ConflictCount"));
  
  choco.getLocalSearchObjectiveValue->addMethod(list::domain(1,choco._InvariantEngine),Kernel._integer,
  	0,_function_(choco_getLocalSearchObjectiveValue_InvariantEngine,"choco_getLocalSearchObjectiveValue_InvariantEngine"));
  
  choco.getLocalSearchObjectiveValue->addMethod(list::domain(1,choco._ConflictCount),Kernel._integer,
  	0,_function_(choco_getLocalSearchObjectiveValue_ConflictCount,"choco_getLocalSearchObjectiveValue_ConflictCount"));
  
  choco.restoreBestAssignment->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_restoreBestAssignment_LocalSearchSolver,"choco_restoreBestAssignment_LocalSearchSolver"));
  
  choco.saveAssignment->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_saveAssignment_LocalSearchSolver,"choco_saveAssignment_LocalSearchSolver"));
  
  choco.setFeasibleMode->addMethod(list::domain(2,choco._Problem,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_setFeasibleMode_Problem,"choco_setFeasibleMode_Problem"));
  
  (choco._AssignmentHeuristic = ClaireClass::make("AssignmentHeuristic",choco._ConstructiveHeuristic,choco.it));
  
  (choco._FlipNeighborhood = ClaireClass::make("FlipNeighborhood",choco._MoveNeighborhood,choco.it));
  
  { (choco._MultipleDescent = ClaireClass::make("MultipleDescent",choco._LocalSearchSolver,choco.it));
    CL_ADD_SLOT(choco._MultipleDescent,MultipleDescent,choco.nbLocalSearch,nbLocalSearch,Kernel._integer,0);
    CL_ADD_SLOT(choco._MultipleDescent,MultipleDescent,choco.nbLocalMoves,nbLocalMoves,Kernel._integer,0);
    } 
  
  choco.selectMoveVar->addMethod(list::domain(1,choco._AssignmentHeuristic),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	RETURN_ARG,_function_(choco_selectMoveVar_AssignmentHeuristic,"choco_selectMoveVar_AssignmentHeuristic"));
  
  choco.selectValue->addMethod(list::domain(2,choco._AssignmentHeuristic,choco._IntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_selectValue_AssignmentHeuristic,"choco_selectValue_AssignmentHeuristic"));
  
  choco.assignOneVar->addMethod(list::domain(1,choco._AssignmentHeuristic),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignOneVar_AssignmentHeuristic,"choco_assignOneVar_AssignmentHeuristic"));
  
  choco.build->addMethod(list::domain(1,choco._AssignmentHeuristic),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_build_AssignmentHeuristic,"choco_build_AssignmentHeuristic"));
  
  choco.eraseAssignment->addMethod(list::domain(1,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_eraseAssignment_ConflictCount,"choco_eraseAssignment_ConflictCount"));
  
  choco.checkCleanState->addMethod(list::domain(1,choco._ConflictCount),Kernel._any,
  	0,_function_(choco_checkCleanState_ConflictCount,"choco_checkCleanState_ConflictCount"));
  
  choco.selectMoveVar->addMethod(list::domain(1,choco._FlipNeighborhood),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_selectMoveVar_FlipNeighborhood,"choco_selectMoveVar_FlipNeighborhood"));
  
  choco.newValue->addMethod(list::domain(2,choco._FlipNeighborhood,choco._IntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_newValue_FlipNeighborhood,"choco_newValue_FlipNeighborhood"));
  
  choco.move->addMethod(list::domain(1,choco._FlipNeighborhood),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_move_FlipNeighborhood,"choco_move_FlipNeighborhood"));
  
  choco.finishedLoop->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._boolean,
  	0,_function_(choco_finishedLoop_LocalSearchSolver,"choco_finishedLoop_LocalSearchSolver"));
  
  choco.initLoop->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_initLoop_LocalSearchSolver,"choco_initLoop_LocalSearchSolver"));
  
  choco.endLoop->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_endLoop_LocalSearchSolver,"choco_endLoop_LocalSearchSolver"));
  
  choco.copyParameters->addMethod(list::domain(2,choco._LocalSearchSolver,choco._LocalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_copyParameters_LocalSearchSolver,"choco_copyParameters_LocalSearchSolver"));
  
  choco.attachLocalSearchSolver->addMethod(list::domain(2,choco._Problem,choco._LocalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_attachLocalSearchSolver_Problem,"choco_attachLocalSearchSolver_Problem"));
  
  choco.runLocalSearch->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_runLocalSearch_LocalSearchSolver,"choco_runLocalSearch_LocalSearchSolver"));
  
  choco.initIterations->addMethod(list::domain(1,choco._MultipleDescent),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_initIterations_MultipleDescent,"choco_initIterations_MultipleDescent"));
  
  choco.finishedIterations->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._boolean,
  	0,_function_(choco_finishedIterations_LocalSearchSolver,"choco_finishedIterations_LocalSearchSolver"));
  
  choco.oneMoreMove->addMethod(list::domain(1,choco._LocalSearchSolver),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_oneMoreMove_LocalSearchSolver,"choco_oneMoreMove_LocalSearchSolver"));
  
  choco.becomesAConflict->addMethod(list::domain(2,choco._AbstractConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_becomesAConflict_AbstractConstraint,"choco_becomesAConflict_AbstractConstraint"));
  
  choco.becomesAConflict->addMethod(list::domain(2,choco._UnIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_becomesAConflict_UnIntConstraint,"choco_becomesAConflict_UnIntConstraint"));
  
  choco.becomesAConflict->addMethod(list::domain(2,choco._BinIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_becomesAConflict_BinIntConstraint,"choco_becomesAConflict_BinIntConstraint"));
  
  choco.becomesAConflict->addMethod(list::domain(2,choco._TernIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_becomesAConflict_TernIntConstraint,"choco_becomesAConflict_TernIntConstraint"));
  
  choco.becomesAConflict->addMethod(list::domain(2,choco._LargeIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_becomesAConflict_LargeIntConstraint,"choco_becomesAConflict_LargeIntConstraint"));
  
  choco.becomesSatisfied->addMethod(list::domain(2,choco._AbstractConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_becomesSatisfied_AbstractConstraint,"choco_becomesSatisfied_AbstractConstraint"));
  
  choco.becomesSatisfied->addMethod(list::domain(2,choco._UnIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_becomesSatisfied_UnIntConstraint,"choco_becomesSatisfied_UnIntConstraint"));
  
  choco.becomesSatisfied->addMethod(list::domain(2,choco._BinIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_becomesSatisfied_BinIntConstraint,"choco_becomesSatisfied_BinIntConstraint"));
  
  choco.becomesSatisfied->addMethod(list::domain(2,choco._TernIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_becomesSatisfied_TernIntConstraint,"choco_becomesSatisfied_TernIntConstraint"));
  
  choco.becomesSatisfied->addMethod(list::domain(2,choco._LargeIntConstraint,choco._ConflictCount),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_becomesSatisfied_LargeIntConstraint,"choco_becomesSatisfied_LargeIntConstraint"));
  
  choco.addOneConflict->addMethod(list::domain(2,choco._ConflictCount,choco._IntVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_addOneConflict_ConflictCount,"choco_addOneConflict_ConflictCount"));
  
  choco.removeOneConflict->addMethod(list::domain(2,choco._ConflictCount,choco._IntVar),Kernel._void,
  	SLOT_UPDATE,_function_(choco_removeOneConflict_ConflictCount,"choco_removeOneConflict_ConflictCount"));
  
  choco.postAssignVal->addMethod(list::domain(3,choco._ConflictCount,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postAssignVal_ConflictCount,"choco_postAssignVal_ConflictCount"));
  
  choco.postUnAssignVal->addMethod(list::domain(2,choco._ConflictCount,choco._IntVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_postUnAssignVal_ConflictCount,"choco_postUnAssignVal_ConflictCount"));
  
  choco.postChangeVal->addMethod(list::domain(4,choco._ConflictCount,
    choco._IntVar,
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_postChangeVal_ConflictCount,"choco_postChangeVal_ConflictCount"));
  
  choco.makeProblem->addMethod(list::domain(2,Kernel._string,Kernel._integer),choco._Problem,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeProblem_string,"choco_makeProblem_string"));
  
  choco.getProblem->addMethod(list::domain(1,choco._IntVar),choco._Problem,
  	0,_function_(choco_getProblem_IntVar,"choco_getProblem_IntVar"));
  
  choco.getProblem->addMethod(list::domain(1,choco._AbstractConstraint),choco._Problem,
  	NEW_ALLOC,_function_(choco_getProblem_AbstractConstraint,"choco_getProblem_AbstractConstraint"));
  
  choco.getActiveProblem->addMethod(list::domain(1,Kernel._void),choco._Problem,
  	0,_function_(choco_getActiveProblem_void,"choco_getActiveProblem_void"));
  
  choco.setActiveProblem->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	RETURN_ARG,_function_(choco_setActiveProblem_Problem,"choco_setActiveProblem_Problem"));
  
  choco.discardProblem->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	0,_function_(choco_discardProblem_Problem,"choco_discardProblem_Problem"));
  
  choco.getPropagationEngine->addMethod(list::domain(1,choco._Problem),choco._PropagationEngine,
  	0,_function_(choco_getPropagationEngine_Problem,"choco_getPropagationEngine_Problem"));
  
  choco.setDelayedLinearConstraintPropagation->addMethod(list::domain(2,choco._ChocEngine,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setDelayedLinearConstraintPropagation_ChocEngine,"choco_setDelayedLinearConstraintPropagation_ChocEngine"));
  
  choco.makeBoundIntVar->addMethod(list::domain(4,choco._Problem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBoundIntVar_Problem1,"choco_makeBoundIntVar_Problem1"));
  
  choco.makeBoundIntVar->addMethod(list::domain(3,choco._Problem,Kernel._integer,Kernel._integer),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBoundIntVar_Problem2,"choco_makeBoundIntVar_Problem2"));
  
  choco.makeBoundIntVar->addMethod(list::domain(2,choco._Problem,Kernel._string),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBoundIntVar_Problem3,"choco_makeBoundIntVar_Problem3"));
  
  choco.makeConstantIntVar->addMethod(list::domain(1,Kernel._integer),choco._IntVar,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeConstantIntVar_integer,"choco_makeConstantIntVar_integer"));
  
  choco.makeIntVar->addMethod(list::domain(4,choco._Problem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeIntVar_Problem1,"choco_makeIntVar_Problem1"));
  
  choco.makeIntVar->addMethod(list::domain(3,choco._Problem,Kernel._integer,Kernel._integer),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeIntVar_Problem2,"choco_makeIntVar_Problem2"));
  
  choco.makeIntVar->addMethod(list::domain(2,choco._Problem,Kernel._string),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeIntVar_Problem3,"choco_makeIntVar_Problem3"));
  
  choco.makeIntVar->addMethod(list::domain(3,choco._Problem,Kernel._string,nth_class1(Kernel._list,Kernel._integer)),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeIntVar_Problem4,"choco_makeIntVar_Problem4"));
  
  choco.makeIntVar->addMethod(list::domain(2,choco._Problem,nth_class1(Kernel._list,Kernel._integer)),choco._IntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeIntVar_Problem5,"choco_makeIntVar_Problem5"));
  
  choco.makeSetVar->addMethod(list::domain(4,choco._Problem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer),choco._SetVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSetVar_Problem1,"choco_makeSetVar_Problem1"));
  
  choco.makeSetVar->addMethod(list::domain(3,choco._Problem,Kernel._integer,Kernel._integer),choco._SetVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSetVar_Problem2,"choco_makeSetVar_Problem2"));
  
  choco.makeSetVar->addMethod(list::domain(5,choco._Problem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer,
    choco._IntVar),choco._SetVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSetVar_Problem3,"choco_makeSetVar_Problem3"));
  
  choco.makeSetVar->addMethod(list::domain(4,choco._Problem,
    Kernel._integer,
    Kernel._integer,
    choco._IntVar),choco._SetVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSetVar_Problem4,"choco_makeSetVar_Problem4"));
  
  choco.makeBinTruthTest->addMethod(list::domain(1,nth_class2(Kernel._method,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(Kernel.emptySet,1,_oid_(set::alloc(Kernel.emptySet,1,_oid_(Kernel._boolean)))))),choco._TruthTest,
  	NEW_ALLOC,_function_(choco_makeBinTruthTest_method,"choco_makeBinTruthTest_method"));
  
  choco.makeBinRelation->addMethod(list::domain(4,Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer),choco._TruthTable2D,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBinRelation_integer1,"choco_makeBinRelation_integer1"));
  
  choco.makeBinRelation->addMethod(list::domain(5,Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,tuple::alloc(2,_oid_(Kernel._integer),_oid_(Kernel._integer)))),choco._TruthTable2D,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeBinRelation_integer2,"choco_makeBinRelation_integer2"));
  
  choco.binConstraint->addMethod(list::domain(5,choco._IntVar,
    choco._IntVar,
    choco._BinRelation,
    Kernel._boolean,
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_binConstraint_IntVar1,"choco_binConstraint_IntVar1"));
  
  choco.binConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    choco._BinRelation,
    Kernel._boolean),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_binConstraint_IntVar2,"choco_binConstraint_IntVar2"));
  
  choco.binConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    choco._BinRelation,
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_binConstraint_IntVar3,"choco_binConstraint_IntVar3"));
  
  choco.binConstraint->addMethod(list::domain(3,choco._IntVar,choco._IntVar,choco._BinRelation),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_binConstraint_IntVar4,"choco_binConstraint_IntVar4"));
  
  choco.feasPairConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    nth_class1(Kernel._list,tuple::alloc(2,_oid_(Kernel._integer),_oid_(Kernel._integer))),
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_feasPairConstraint_IntVar1,"choco_feasPairConstraint_IntVar1"));
  
  choco.infeasPairConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    nth_class1(Kernel._list,tuple::alloc(2,_oid_(Kernel._integer),_oid_(Kernel._integer))),
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_infeasPairConstraint_IntVar1,"choco_infeasPairConstraint_IntVar1"));
  
  choco.feasBinTestConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    Kernel._method,
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_feasBinTestConstraint_IntVar1,"choco_feasBinTestConstraint_IntVar1"));
  
  choco.infeasBinTestConstraint->addMethod(list::domain(4,choco._IntVar,
    choco._IntVar,
    Kernel._method,
    Kernel._integer),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_infeasBinTestConstraint_IntVar1,"choco_infeasBinTestConstraint_IntVar1"));
  
  choco.feasPairConstraint->addMethod(list::domain(3,choco._IntVar,choco._IntVar,nth_class1(Kernel._list,Kernel._tuple)),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_feasPairConstraint_IntVar2,"choco_feasPairConstraint_IntVar2"));
  
  choco.infeasPairConstraint->addMethod(list::domain(3,choco._IntVar,choco._IntVar,nth_class1(Kernel._list,Kernel._tuple)),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_infeasPairConstraint_IntVar2,"choco_infeasPairConstraint_IntVar2"));
  
  choco.feasBinTestConstraint->addMethod(list::domain(3,choco._IntVar,choco._IntVar,Kernel._method),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_feasBinTestConstraint_IntVar2,"choco_feasBinTestConstraint_IntVar2"));
  
  choco.infeasBinTestConstraint->addMethod(list::domain(3,choco._IntVar,choco._IntVar,Kernel._method),choco._CSPBinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_infeasBinTestConstraint_IntVar2,"choco_infeasBinTestConstraint_IntVar2"));
  
  choco.feasTupleConstraint->addMethod(list::domain(2,nth_class1(Kernel._list,choco._IntVar),nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)))),choco._CSPLargeConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_feasTupleConstraint_list,"choco_feasTupleConstraint_list"));
  
  choco.infeasTupleConstraint->addMethod(list::domain(2,nth_class1(Kernel._list,choco._IntVar),nth_class1(Kernel._list,GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)))),choco._CSPLargeConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_infeasTupleConstraint_list,"choco_infeasTupleConstraint_list"));
  
  choco.feasTestConstraint->addMethod(list::domain(2,nth_class1(Kernel._list,choco._IntVar),Kernel._method),choco._CSPLargeConstraint,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_feasTestConstraint_list,"choco_feasTestConstraint_list"));
  
  { (choco._Term = ClaireClass::make("Term",choco._Ephemeral,choco.it));
    CL_ADD_SLOT(choco._Term,Term,choco.cste,cste,Kernel._integer,0);
    } 
  
  { (choco._LinTerm = ClaireClass::make("LinTerm",choco._Term,choco.it));
    CL_ADD_SLOT(choco._LinTerm,LinTerm,choco.lcoeff,lcoeff,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._LinTerm,LinTerm,choco.lvars,lvars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(choco._IntVar))))),CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._LinTerm),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_LinTerm_choco,"claire_self_print_LinTerm_choco"));
  
  { (choco.scalar = (operation *) Kernel._operation->instantiate("scalar",claire.it));
    (choco.scalar->precedence = Kernel._star->precedence);
    ;} 
  
  choco.scalar->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),nth_class1(Kernel._list,choco._IntVar)),choco._LinTerm,
  	NEW_ALLOC,_function_(claire_scalar_list,"claire_scalar_list"));
  
  choco.sumVars->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),choco._LinTerm,
  	NEW_ALLOC,_function_(choco_sumVars_list,"choco_sumVars_list"));
  
  choco.allDifferent->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),choco._AllDiff,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_allDifferent_list,"choco_allDifferent_list"));
  
  choco.post->addMethod(list::domain(2,choco._Problem,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_post_Problem,"choco_post_Problem"));
  
  choco.setMin->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_setMin_IntVar,"choco_setMin_IntVar"));
  
  choco.setMax->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_setMax_IntVar,"choco_setMax_IntVar"));
  
  choco.setVal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setVal_IntVar,"choco_setVal_IntVar"));
  
  choco.remVal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC,_function_(choco_remVal_IntVar,"choco_remVal_IntVar"));
  
  choco.setBranch->addMethod(list::domain(3,choco._Disjunction,Kernel._integer,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setBranch_Disjunction,"choco_setBranch_Disjunction"));
  
  choco.setBranch->addMethod(list::domain(3,choco._LargeDisjunction,Kernel._integer,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+RETURN_ARG,_function_(choco_setBranch_LargeDisjunction,"choco_setBranch_LargeDisjunction"));
  
  choco.setBranch->addMethod(list::domain(3,choco._Cardinality,Kernel._integer,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setBranch_Cardinality,"choco_setBranch_Cardinality"));
  
  choco.propagate->addMethod(list::domain(1,choco._Problem),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_Problem,"choco_propagate_Problem"));
  
  choco.setActive->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_setActive_AbstractConstraint,"choco_setActive_AbstractConstraint"));
  
  choco.setPassive->addMethod(list::domain(1,choco._AbstractConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_setPassive_AbstractConstraint,"choco_setPassive_AbstractConstraint"));
  
  choco.getProblem->addMethod(list::domain(1,choco._Solver),choco._Problem,
  	0,_function_(choco_getProblem_Solver,"choco_getProblem_Solver"));
  
  choco.getSearchManager->addMethod(list::domain(1,choco._AbstractBranching),choco._GlobalSearchSolver,
  	0,_function_(choco_getSearchManager_AbstractBranching,"choco_getSearchManager_AbstractBranching"));
  
  choco.makeMinDomVarHeuristic->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeMinDomVarHeuristic_void,"choco_makeMinDomVarHeuristic_void"));
  
  choco.makeDomDegVarHeuristic->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeDomDegVarHeuristic_void,"choco_makeDomDegVarHeuristic_void"));
  
  choco.makeMaxDegVarHeuristic->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeMaxDegVarHeuristic_void,"choco_makeMaxDegVarHeuristic_void"));
  
  choco.makeStaticVarHeuristic->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeStaticVarHeuristic_list,"choco_makeStaticVarHeuristic_list"));
  
  choco.makeMinDomVarHeuristic->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),choco._MinDomain,
  	NEW_ALLOC,_function_(choco_makeMinDomVarHeuristic_list,"choco_makeMinDomVarHeuristic_list"));
  
  choco.makeDomDegVarHeuristic->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),choco._DomDeg,
  	NEW_ALLOC,_function_(choco_makeDomDegVarHeuristic_list,"choco_makeDomDegVarHeuristic_list"));
  
  choco.makeMaxDegVarHeuristic->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),choco._MaxDeg,
  	NEW_ALLOC,_function_(choco_makeMaxDegVarHeuristic_list,"choco_makeMaxDegVarHeuristic_list"));
  
  choco.makeIncValIterator->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeIncValIterator_void,"choco_makeIncValIterator_void"));
  
  choco.makeDecValIterator->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeDecValIterator_void,"choco_makeDecValIterator_void"));
  
  choco.makeMinValSelector->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeMinValSelector_void,"choco_makeMinValSelector_void"));
  
  choco.makeMaxValSelector->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeMaxValSelector_void,"choco_makeMaxValSelector_void"));
  
  choco.makeMidValSelector->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(choco_makeMidValSelector_void,"choco_makeMidValSelector_void"));
  
  choco.makeAssignVarBranching->addMethod(list::domain(2,choco._VarSelector,choco._ValIterator),choco._AssignVar,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeAssignVarBranching_VarSelector1,"choco_makeAssignVarBranching_VarSelector1"));
  
  choco.makeAssignVarBranching->addMethod(list::domain(1,choco._VarSelector),choco._AssignVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeAssignVarBranching_VarSelector2,"choco_makeAssignVarBranching_VarSelector2"));
  
  choco.makeAssignVarBranching->addMethod(list::domain(1,Kernel._void),choco._AssignVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeAssignVarBranching_void,"choco_makeAssignVarBranching_void"));
  
  choco.makeSplitDomBranching->addMethod(list::domain(3,choco._VarSelector,choco._ValSelector,Kernel._integer),choco._SplitDomain,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeSplitDomBranching_VarSelector1,"choco_makeSplitDomBranching_VarSelector1"));
  
  choco.makeSplitDomBranching->addMethod(list::domain(2,choco._VarSelector,Kernel._integer),choco._SplitDomain,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSplitDomBranching_VarSelector2,"choco_makeSplitDomBranching_VarSelector2"));
  
  choco.makeSplitDomBranching->addMethod(list::domain(1,choco._VarSelector),choco._SplitDomain,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSplitDomBranching_VarSelector3,"choco_makeSplitDomBranching_VarSelector3"));
  
  choco.makeSplitDomBranching->addMethod(list::domain(1,Kernel._void),choco._SplitDomain,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeSplitDomBranching_void,"choco_makeSplitDomBranching_void"));
  
  choco.makeAssignOrForbidBranching->addMethod(list::domain(2,choco._VarSelector,choco._ValSelector),choco._AssignOrForbid,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeAssignOrForbidBranching_VarSelector,"choco_makeAssignOrForbidBranching_VarSelector"));
  
  choco.makeDisjunctionBranching->addMethod(list::domain(1,choco._Problem),choco._SettleBinDisjunction,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeDisjunctionBranching_Problem,"choco_makeDisjunctionBranching_Problem"));
  
  choco.makeDefaultBranchingList->addMethod(list::domain(1,choco._Problem),nth_class1(Kernel._list,choco._AbstractBranching),
  	NEW_ALLOC,_function_(choco_makeDefaultBranchingList_Problem,"choco_makeDefaultBranchingList_Problem"));
  
  choco.makeGlobalSearchSolver->addMethod(list::domain(2,Kernel._boolean,nth_class1(Kernel._list,choco._AbstractBranching)),choco._Solve,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchSolver_boolean1,"choco_makeGlobalSearchSolver_boolean1"));
  
  choco.makeGlobalSearchMaximizer->addMethod(list::domain(3,choco._IntVar,Kernel._boolean,nth_class1(Kernel._list,choco._AbstractBranching)),choco._AbstractOptimize,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchMaximizer_IntVar1,"choco_makeGlobalSearchMaximizer_IntVar1"));
  
  choco.makeGlobalSearchMinimizer->addMethod(list::domain(3,choco._IntVar,Kernel._boolean,nth_class1(Kernel._list,choco._AbstractBranching)),choco._AbstractOptimize,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchMinimizer_IntVar1,"choco_makeGlobalSearchMinimizer_IntVar1"));
  
  choco.makeGlobalSearchSolver->addMethod(list::domain(1,Kernel._boolean),choco._Solve,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchSolver_boolean2,"choco_makeGlobalSearchSolver_boolean2"));
  
  choco.makeGlobalSearchMaximizer->addMethod(list::domain(2,choco._IntVar,Kernel._boolean),choco._AbstractOptimize,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchMaximizer_IntVar2,"choco_makeGlobalSearchMaximizer_IntVar2"));
  
  choco.makeGlobalSearchMinimizer->addMethod(list::domain(2,choco._IntVar,Kernel._boolean),choco._AbstractOptimize,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_makeGlobalSearchMinimizer_IntVar2,"choco_makeGlobalSearchMinimizer_IntVar2"));
  
  choco.makeBacktrackLimit->addMethod(list::domain(1,Kernel._integer),choco._NodeLimit,
  	NEW_ALLOC,_function_(choco_makeBacktrackLimit_integer,"choco_makeBacktrackLimit_integer"));
  
  choco.makeNodeLimit->addMethod(list::domain(1,Kernel._integer),choco._BacktrackLimit,
  	NEW_ALLOC,_function_(choco_makeNodeLimit_integer,"choco_makeNodeLimit_integer"));
  
  choco.setSearchLimit->addMethod(list::domain(2,choco._GlobalSearchSolver,choco._GlobalSearchLimit),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setSearchLimit_GlobalSearchSolver,"choco_setSearchLimit_GlobalSearchSolver"));
  
  choco.solve->addMethod(list::domain(2,choco._Problem,Kernel._boolean),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_solve_Problem1,"choco_solve_Problem1"));
  
  choco.minimize->addMethod(list::domain(3,choco._Problem,choco._IntVar,Kernel._boolean),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_minimize_Problem1,"choco_minimize_Problem1"));
  
  choco.maximize->addMethod(list::domain(3,choco._Problem,choco._IntVar,Kernel._boolean),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_maximize_Problem1,"choco_maximize_Problem1"));
  
  choco.solve->addMethod(list::domain(3,choco._Problem,nth_class1(Kernel._list,choco._AbstractBranching),Kernel._boolean),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_solve_Problem2,"choco_solve_Problem2"));
  
  choco.minimize->addMethod(list::domain(4,choco._Problem,
    choco._IntVar,
    nth_class1(Kernel._list,choco._AbstractBranching),
    Kernel._boolean),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_minimize_Problem2,"choco_minimize_Problem2"));
  
  choco.maximize->addMethod(list::domain(4,choco._Problem,
    choco._IntVar,
    nth_class1(Kernel._list,choco._AbstractBranching),
    Kernel._boolean),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_maximize_Problem2,"choco_maximize_Problem2"));
  
  choco.setMaxPrintDepth->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setMaxPrintDepth_GlobalSearchSolver,"choco_setMaxPrintDepth_GlobalSearchSolver"));
  
  choco.setMaxSolutionStorage->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setMaxSolutionStorage_GlobalSearchSolver,"choco_setMaxSolutionStorage_GlobalSearchSolver"));
  
  choco.setVarsToShow->addMethod(list::domain(2,choco._GlobalSearchSolver,nth_class1(Kernel._list,choco._IntVar)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setVarsToShow_GlobalSearchSolver,"choco_setVarsToShow_GlobalSearchSolver"));
  
  choco.getNbSol->addMethod(list::domain(1,choco._GlobalSearchSolver),Kernel._integer,
  	0,_function_(choco_getNbSol_GlobalSearchSolver,"choco_getNbSol_GlobalSearchSolver"));
  
  choco.getProblem->addMethod(list::domain(1,choco._AbstractBranching),choco._Problem,
  	0,_function_(choco_getProblem_AbstractBranching,"choco_getProblem_AbstractBranching"));
  
  choco.getGlobalSearchSolver->addMethod(list::domain(1,choco._Problem),choco._GlobalSearchSolver,
  	0,_function_(choco_getGlobalSearchSolver_Problem,"choco_getGlobalSearchSolver_Problem"));
  
  choco.setNodeLimit->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setNodeLimit_GlobalSearchSolver,"choco_setNodeLimit_GlobalSearchSolver"));
  
  choco.setTimeLimit->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setTimeLimit_GlobalSearchSolver,"choco_setTimeLimit_GlobalSearchSolver"));
  
  choco.setBacktrackLimit->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_setBacktrackLimit_GlobalSearchSolver,"choco_setBacktrackLimit_GlobalSearchSolver"));
  
  choco.setMaxNbBk->addMethod(list::domain(2,choco._GlobalSearchSolver,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_setMaxNbBk_GlobalSearchSolver,"choco_setMaxNbBk_GlobalSearchSolver"));
  
  choco.assignVal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_assignVal_IntVar,"choco_assignVal_IntVar"));
  
  choco.unassignVal->addMethod(list::domain(1,choco._IntVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_unassignVal_IntVar,"choco_unassignVal_IntVar"));
  
  choco.changeVal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_changeVal_IntVar,"choco_changeVal_IntVar"));
  
  choco.setMaxNbLocalSearch->addMethod(list::domain(2,choco._Problem,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setMaxNbLocalSearch_Problem,"choco_setMaxNbLocalSearch_Problem"));
  
  choco.setMaxNbLocalMoves->addMethod(list::domain(2,choco._Problem,Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(choco_setMaxNbLocalMoves_Problem,"choco_setMaxNbLocalMoves_Problem"));
  
  choco.move->addMethod(list::domain(1,choco._Problem),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_move_Problem1,"choco_move_Problem1"));
  
  choco.move->addMethod(list::domain(3,choco._Problem,choco._ConstructiveHeuristic,choco._MoveNeighborhood),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_move_Problem2,"choco_move_Problem2"));
  
  { (choco._equal_equal = (operation *) Kernel._operation->instantiate("==",claire.it));
    (choco._equal_equal->precedence = Kernel._equal->precedence);
    ;} 
  
  { (choco._I_equal_equal = (operation *) Kernel._operation->instantiate("!==",claire.it));
    (choco._I_equal_equal->precedence = Core._I_equal->precedence);
    ;} 
  
  { ;;} 
  
  { ;;} 
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._GreaterOrEqualxc,
  	NEW_ALLOC,_function_(claire__sup_equal_IntVar1,"claire_>=_IntVar1"));
  
  Kernel._sup->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._GreaterOrEqualxc,
  	NEW_ALLOC,_function_(claire__sup_IntVar1,"claire_>_IntVar1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._LessOrEqualxc,
  	NEW_ALLOC,_function_(claire__inf_equal_IntVar1,"claire_<=_IntVar1"));
  
  Kernel._inf->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._LessOrEqualxc,
  	NEW_ALLOC,_function_(claire__inf_IntVar1,"claire_<_IntVar1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._Equalxc,
  	NEW_ALLOC,_function_(claire__equal_equal_IntVar1,"claire_==_IntVar1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._NotEqualxc,
  	NEW_ALLOC,_function_(claire__I_equal_equal_IntVar1,"claire_!==_IntVar1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._LessOrEqualxc,
  	NEW_ALLOC,_function_(claire__sup_equal_integer1,"claire_>=_integer1"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._LessOrEqualxc,
  	NEW_ALLOC,_function_(claire__sup_integer2,"claire_>_integer2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._GreaterOrEqualxc,
  	NEW_ALLOC,_function_(claire__inf_equal_integer2,"claire_<=_integer2"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._GreaterOrEqualxc,
  	NEW_ALLOC,_function_(claire__inf_integer2,"claire_<_integer2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._Equalxc,
  	NEW_ALLOC,_function_(claire__equal_equal_integer1,"claire_==_integer1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._NotEqualxc,
  	NEW_ALLOC,_function_(claire__I_equal_equal_integer1,"claire_!==_integer1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._Equalxyc,
  	NEW_ALLOC,_function_(claire__equal_equal_IntVar2,"claire_==_IntVar2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._NotEqualxyc,
  	NEW_ALLOC,_function_(claire__I_equal_equal_IntVar2,"claire_!==_IntVar2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._GreaterOrEqualxyc,
  	NEW_ALLOC,_function_(claire__inf_equal_IntVar2,"claire_<=_IntVar2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._GreaterOrEqualxyc,
  	NEW_ALLOC,_function_(claire__sup_equal_IntVar2,"claire_>=_IntVar2"));
  
  Kernel._sup->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._GreaterOrEqualxyc,
  	NEW_ALLOC,_function_(claire__sup_IntVar2,"claire_>_IntVar2"));
  
  Kernel._inf->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._GreaterOrEqualxyc,
  	NEW_ALLOC,_function_(claire__inf_IntVar2,"claire_<_IntVar2"));
  
  { (choco._UnTerm = ClaireClass::make("UnTerm",choco._Term,choco.it));
    CL_ADD_SLOT(choco._UnTerm,UnTerm,choco.v1,v1,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._UnTerm,UnTerm,choco.sign1,sign1,Kernel._boolean,Kernel.ctrue);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._UnTerm),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_UnTerm_choco,"claire_self_print_UnTerm_choco"));
  
  { (choco._BinTerm = ClaireClass::make("BinTerm",choco._Term,choco.it));
    CL_ADD_SLOT(choco._BinTerm,BinTerm,choco.v1,v1,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._BinTerm,BinTerm,choco.v2,v2,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._BinTerm,BinTerm,choco.sign1,sign1,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(choco._BinTerm,BinTerm,choco.sign2,sign2,Kernel._boolean,Kernel.ctrue);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,choco._BinTerm),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_BinTerm_choco,"claire_self_print_BinTerm_choco"));
  
  choco.sumTerms->addMethod(list::domain(1,nth_class1(Kernel._list,choco._Term)),choco._Term,
  	NEW_ALLOC,_function_(choco_sumTerms_list,"choco_sumTerms_list"));
  
  Kernel._star->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__star_integer2,"claire_*_integer2"));
  
  Kernel._star->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__star_IntVar,"claire_*_IntVar"));
  
  Kernel._star->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._LinTerm,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__star_integer3,"claire_*_integer3"));
  
  Kernel._star->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinTerm,
  	BAG_UPDATE+RETURN_ARG,_function_(claire__star_LinTerm,"claire_*_LinTerm"));
  
  Core._plus->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._UnTerm,
  	NEW_ALLOC,_function_(claire__plus_IntVar1,"claire_+_IntVar1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._UnTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_integer2,"claire_+_integer2"));
  
  Core._plus->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._BinTerm,
  	NEW_ALLOC,_function_(claire__plus_IntVar2,"claire_+_IntVar2"));
  
  Core._plus->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._UnTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_UnTerm1,"claire_+_UnTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._UnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer3,"claire_+_integer3"));
  
  Core._plus->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._BinTerm,
  	NEW_ALLOC,_function_(claire__plus_UnTerm2,"claire_+_UnTerm2"));
  
  Core._plus->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._BinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_IntVar3,"claire_+_IntVar3"));
  
  Core._plus->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._BinTerm,
  	NEW_ALLOC,_function_(claire__plus_UnTerm3,"claire_+_UnTerm3"));
  
  Core._plus->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._BinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_BinTerm1,"claire_+_BinTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._BinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer4,"claire_+_integer4"));
  
  Core._plus->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__plus_BinTerm2,"claire_+_BinTerm2"));
  
  Core._plus->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_IntVar4,"claire_+_IntVar4"));
  
  Core._plus->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__plus_BinTerm3,"claire_+_BinTerm3"));
  
  Core._plus->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_UnTerm4,"claire_+_UnTerm4"));
  
  Core._plus->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__plus_BinTerm4,"claire_+_BinTerm4"));
  
  Core._plus->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_LinTerm1,"claire_+_LinTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer5,"claire_+_integer5"));
  
  Core._plus->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._LinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_LinTerm2,"claire_+_LinTerm2"));
  
  Core._plus->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_IntVar5,"claire_+_IntVar5"));
  
  Core._plus->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._LinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_LinTerm3,"claire_+_LinTerm3"));
  
  Core._plus->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_UnTerm5,"claire_+_UnTerm5"));
  
  Core._plus->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_LinTerm4,"claire_+_LinTerm4"));
  
  Core._plus->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_BinTerm5,"claire_+_BinTerm5"));
  
  Core._plus->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC,_function_(claire__plus_LinTerm5,"claire_+_LinTerm5"));
  
  Kernel._dash->addMethod(list::domain(1,choco._IntVar),choco._UnTerm,
  	NEW_ALLOC,_function_(claire__dash_IntVar1,"claire_-_IntVar1"));
  
  Kernel._dash->addMethod(list::domain(1,choco._UnTerm),choco._UnTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_UnTerm1,"claire_-_UnTerm1"));
  
  Kernel._dash->addMethod(list::domain(1,choco._BinTerm),choco._BinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_BinTerm1,"claire_-_BinTerm1"));
  
  Kernel._dash->addMethod(list::domain(1,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm1,"claire_-_LinTerm1"));
  
  Kernel._dash->addMethod(list::domain(2,choco._IntVar,Kernel._integer),choco._UnTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_IntVar2,"claire_-_IntVar2"));
  
  Kernel._dash->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._UnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_UnTerm2,"claire_-_UnTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._BinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_BinTerm2,"claire_-_BinTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm2,"claire_-_LinTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,choco._IntVar),choco._UnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer4,"claire_-_integer4"));
  
  Kernel._dash->addMethod(list::domain(2,choco._IntVar,choco._IntVar),choco._BinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_IntVar3,"claire_-_IntVar3"));
  
  Kernel._dash->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._BinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_UnTerm3,"claire_-_UnTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._LinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_BinTerm3,"claire_-_BinTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm3,"claire_-_LinTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._UnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer5,"claire_-_integer5"));
  
  Kernel._dash->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._BinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_IntVar4,"claire_-_IntVar4"));
  
  Kernel._dash->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._BinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_UnTerm4,"claire_-_UnTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_BinTerm4,"claire_-_BinTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm4,"claire_-_LinTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._BinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer6,"claire_-_integer6"));
  
  Kernel._dash->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_IntVar5,"claire_-_IntVar5"));
  
  Kernel._dash->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_UnTerm5,"claire_-_UnTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_BinTerm5,"claire_-_BinTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm5,"claire_-_LinTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer7,"claire_-_integer7"));
  
  Kernel._dash->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_IntVar6,"claire_-_IntVar6"));
  
  Kernel._dash->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_UnTerm6,"claire_-_UnTerm6"));
  
  Kernel._dash->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_BinTerm6,"claire_-_BinTerm6"));
  
  Kernel._dash->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._LinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_LinTerm6,"claire_-_LinTerm6"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer2,"claire_>=_integer2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer3,"claire_>=_integer3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer4,"claire_>=_integer4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_IntVar3,"claire_>=_IntVar3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_IntVar4,"claire_>=_IntVar4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_IntVar5,"claire_>=_IntVar5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_UnTerm1,"claire_>=_UnTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_UnTerm2,"claire_>=_UnTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_BinTerm1,"claire_>=_BinTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._UnIntConstraint,
  	NEW_ALLOC,_function_(claire__sup_equal_UnTerm3,"claire_>=_UnTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_UnTerm4,"claire_>=_UnTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_UnTerm5,"claire_>=_UnTerm5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_BinTerm2,"claire_>=_BinTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_BinTerm3,"claire_>=_BinTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_BinTerm4,"claire_>=_BinTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_BinTerm5,"claire_>=_BinTerm5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_LinTerm1,"claire_>=_LinTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_LinTerm2,"claire_>=_LinTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_LinTerm3,"claire_>=_LinTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_LinTerm4,"claire_>=_LinTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_LinTerm5,"claire_>=_LinTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer3,"claire_>_integer3"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer4,"claire_>_integer4"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer5,"claire_>_integer5"));
  
  Kernel._sup->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_IntVar3,"claire_>_IntVar3"));
  
  Kernel._sup->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_IntVar4,"claire_>_IntVar4"));
  
  Kernel._sup->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_IntVar5,"claire_>_IntVar5"));
  
  Kernel._sup->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC,_function_(claire__sup_UnTerm1,"claire_>_UnTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_BinTerm1,"claire_>_BinTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_LinTerm1,"claire_>_LinTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_UnTerm2,"claire_>_UnTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_BinTerm2,"claire_>_BinTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_LinTerm2,"claire_>_LinTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_UnTerm3,"claire_>_UnTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_BinTerm3,"claire_>_BinTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_LinTerm3,"claire_>_LinTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_UnTerm4,"claire_>_UnTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_BinTerm4,"claire_>_BinTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_LinTerm4,"claire_>_LinTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_UnTerm5,"claire_>_UnTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_BinTerm5,"claire_>_BinTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_LinTerm5,"claire_>_LinTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC,_function_(claire__inf_equal_integer3,"claire_<=_integer3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer4,"claire_<=_integer4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer5,"claire_<=_integer5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_IntVar3,"claire_<=_IntVar3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC,_function_(claire__inf_equal_IntVar4,"claire_<=_IntVar4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_IntVar5,"claire_<=_IntVar5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_UnTerm1,"claire_<=_UnTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_BinTerm1,"claire_<=_BinTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_LinTerm1,"claire_<=_LinTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_UnTerm2,"claire_<=_UnTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_BinTerm2,"claire_<=_BinTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_LinTerm2,"claire_<=_LinTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_UnTerm3,"claire_<=_UnTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_BinTerm3,"claire_<=_BinTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_LinTerm3,"claire_<=_LinTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_UnTerm4,"claire_<=_UnTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_BinTerm4,"claire_<=_BinTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_LinTerm4,"claire_<=_LinTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_UnTerm5,"claire_<=_UnTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_BinTerm5,"claire_<=_BinTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_LinTerm5,"claire_<=_LinTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer3,"claire_<_integer3"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer4,"claire_<_integer4"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer5,"claire_<_integer5"));
  
  Kernel._inf->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_IntVar3,"claire_<_IntVar3"));
  
  Kernel._inf->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_IntVar4,"claire_<_IntVar4"));
  
  Kernel._inf->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_IntVar5,"claire_<_IntVar5"));
  
  Kernel._inf->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_UnTerm1,"claire_<_UnTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_BinTerm1,"claire_<_BinTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_LinTerm1,"claire_<_LinTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_UnTerm2,"claire_<_UnTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_BinTerm2,"claire_<_BinTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_LinTerm2,"claire_<_LinTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_UnTerm3,"claire_<_UnTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_BinTerm3,"claire_<_BinTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_LinTerm3,"claire_<_LinTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_UnTerm4,"claire_<_UnTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_BinTerm4,"claire_<_BinTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_LinTerm4,"claire_<_LinTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_UnTerm5,"claire_<_UnTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_BinTerm5,"claire_<_BinTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_LinTerm5,"claire_<_LinTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC,_function_(claire__equal_equal_integer2,"claire_==_integer2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer3,"claire_==_integer3"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer4,"claire_==_integer4"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_IntVar3,"claire_==_IntVar3"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_IntVar4,"claire_==_IntVar4"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_IntVar5,"claire_==_IntVar5"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_UnTerm1,"claire_==_UnTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_UnTerm2,"claire_==_UnTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_BinTerm1,"claire_==_BinTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._UnIntConstraint,
  	NEW_ALLOC,_function_(claire__equal_equal_UnTerm3,"claire_==_UnTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_UnTerm4,"claire_==_UnTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._BinIntConstraint,
  	NEW_ALLOC,_function_(claire__equal_equal_UnTerm5,"claire_==_UnTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_BinTerm2,"claire_==_BinTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_BinTerm3,"claire_==_BinTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_BinTerm4,"claire_==_BinTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_BinTerm5,"claire_==_BinTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_LinTerm1,"claire_==_LinTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_LinTerm2,"claire_==_LinTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_LinTerm3,"claire_==_LinTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_LinTerm4,"claire_==_LinTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_LinTerm5,"claire_==_LinTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC,_function_(claire__I_equal_equal_integer2,"claire_!==_integer2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,choco._BinTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer3,"claire_!==_integer3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,choco._LinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer4,"claire_!==_integer4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._IntVar,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_IntVar3,"claire_!==_IntVar3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._IntVar,choco._BinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_IntVar4,"claire_!==_IntVar4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._IntVar,choco._LinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_IntVar5,"claire_!==_IntVar5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._BinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_UnTerm1,"claire_!==_UnTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._LinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_UnTerm2,"claire_!==_UnTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._LinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_BinTerm1,"claire_!==_BinTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._UnTerm,Kernel._integer),choco._UnIntConstraint,
  	NEW_ALLOC,_function_(claire__I_equal_equal_UnTerm3,"claire_!==_UnTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._IntVar),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_UnTerm4,"claire_!==_UnTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._UnTerm,choco._UnTerm),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_UnTerm5,"claire_!==_UnTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._BinTerm,Kernel._integer),choco._IntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_BinTerm2,"claire_!==_BinTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_BinTerm3,"claire_!==_BinTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_BinTerm4,"claire_!==_BinTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._BinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_BinTerm5,"claire_!==_BinTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._LinTerm,Kernel._integer),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_LinTerm1,"claire_!==_LinTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._IntVar),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_LinTerm2,"claire_!==_LinTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._UnTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_LinTerm3,"claire_!==_LinTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._BinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_LinTerm4,"claire_!==_LinTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,choco._LinTerm,choco._LinTerm),choco._LinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_LinTerm5,"claire_!==_LinTerm5"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._LargeDisjunction,choco._LargeDisjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_or_LargeDisjunction1,"claire_or_LargeDisjunction1"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._Disjunction,choco._Disjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_or_Disjunction1,"claire_or_Disjunction1"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._LargeDisjunction,choco._Disjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_or_LargeDisjunction2,"claire_or_LargeDisjunction2"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._Disjunction,choco._LargeDisjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_or_Disjunction2,"claire_or_Disjunction2"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._Disjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Conjunction)),choco._LargeConjunction)),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_or_Disjunction3,"claire_or_Disjunction3"));
  
  Core.ClaireOr->addMethod(list::domain(2,choco._LargeDisjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Conjunction)),choco._LargeConjunction)),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_or_LargeDisjunction3,"claire_or_LargeDisjunction3"));
  
  Core.ClaireOr->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Conjunction)),choco._LargeConjunction),choco._Disjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_or_AbstractConstraint1,"claire_or_AbstractConstraint1"));
  
  Core.ClaireOr->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Conjunction)),choco._LargeConjunction),choco._LargeDisjunction),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_or_AbstractConstraint2,"claire_or_AbstractConstraint2"));
  
  Core.ClaireOr->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint),U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint)),choco._Disjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_or_AbstractConstraint3,"claire_or_AbstractConstraint3"));
  
  Core.ClaireOr->addMethod(list::domain(1,nth_class1(Kernel._list,choco._AbstractConstraint)),choco._LargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_or_list,"claire_or_list"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._Conjunction,choco._Conjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_and_Conjunction1,"claire_and_Conjunction1"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._LargeConjunction,choco._LargeConjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_and_LargeConjunction1,"claire_and_LargeConjunction1"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._LargeConjunction,choco._Conjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_and_LargeConjunction2,"claire_and_LargeConjunction2"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._Conjunction,choco._LargeConjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_and_Conjunction2,"claire_and_Conjunction2"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._Conjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Disjunction)),choco._LargeDisjunction)),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_and_Conjunction3,"claire_and_Conjunction3"));
  
  Core.ClaireAnd->addMethod(list::domain(2,choco._LargeConjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Disjunction)),choco._LargeDisjunction)),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_and_LargeConjunction3,"claire_and_LargeConjunction3"));
  
  Core.ClaireAnd->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Disjunction)),choco._LargeDisjunction),choco._Conjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_and_AbstractConstraint1,"claire_and_AbstractConstraint1"));
  
  Core.ClaireAnd->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._Cardinality)),choco._Guard)),choco._Equiv)),choco._Disjunction)),choco._LargeDisjunction),choco._LargeConjunction),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_and_AbstractConstraint2,"claire_and_AbstractConstraint2"));
  
  Core.ClaireAnd->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint),U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint)),choco._Conjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_and_AbstractConstraint3,"claire_and_AbstractConstraint3"));
  
  Core.ClaireAnd->addMethod(list::domain(1,nth_class1(Kernel._list,choco._AbstractConstraint)),choco._LargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_and_list,"claire_and_list"));
  
  { (choco.implies = (operation *) Kernel._operation->instantiate("implies",claire.it));
    ;} 
  
  choco.implies->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint),U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint)),U_type(choco._Disjunction,choco._LargeDisjunction),
  	NEW_ALLOC+RETURN_ARG,_function_(claire_implies_AbstractConstraint,"claire_implies_AbstractConstraint"));
  
  choco.ifThen->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint),U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint)),choco._Guard,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_ifThen_AbstractConstraint,"claire_ifThen_AbstractConstraint"));
  
  { (choco.ifOnlyIf = (operation *) Kernel._operation->instantiate("ifOnlyIf",claire.it));
    (choco.ifOnlyIf->precedence = Core._and->precedence);
    ;} 
  
  choco.ifOnlyIf->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint),U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint)),choco._Equiv,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_ifOnlyIf_AbstractConstraint,"claire_ifOnlyIf_AbstractConstraint"));
  
  choco.makeCardinalityConstraint->addMethod(list::domain(4,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),
    choco._IntVar,
    Kernel._boolean,
    Kernel._boolean),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_makeCardinalityConstraint_list,"choco_makeCardinalityConstraint_list"));
  
  choco.card->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),choco._IntVar),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_card_list1,"choco_card_list1"));
  
  choco.atleast->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),choco._IntVar),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_atleast_list1,"choco_atleast_list1"));
  
  choco.atmost->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),choco._IntVar),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_atmost_list1,"choco_atmost_list1"));
  
  choco.card->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),Kernel._integer),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_card_list2,"choco_card_list2"));
  
  choco.atleast->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),Kernel._integer),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_atleast_list2,"choco_atleast_list2"));
  
  choco.atmost->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._SetConstraint)),choco._CompositeConstraint))),Kernel._integer),choco._Cardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_atmost_list2,"choco_atmost_list2"));
  
  { (choco._OccurTerm = ClaireClass::make("OccurTerm",choco._Term,choco.it));
    CL_ADD_SLOT(choco._OccurTerm,OccurTerm,choco.target,target,Kernel._integer,CNULL);
    CL_ADD_SLOT(choco._OccurTerm,OccurTerm,choco.lvars,lvars,nth_class1(Kernel._list,choco._IntVar),CNULL);
    } 
  
  choco.makeOccurConstraint->addMethod(list::domain(4,choco._OccurTerm,
    choco._IntVar,
    Kernel._boolean,
    Kernel._boolean),choco._Occurrence,
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_makeOccurConstraint_OccurTerm,"choco_makeOccurConstraint_OccurTerm"));
  
  choco.occur->addMethod(list::domain(2,Kernel._integer,nth_class1(Kernel._list,choco._IntVar)),choco._OccurTerm,
  	NEW_ALLOC,_function_(choco_occur_integer,"choco_occur_integer"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._OccurTerm,choco._IntVar),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_OccurTerm1,"claire_==_OccurTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_IntVar6,"claire_==_IntVar6"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._OccurTerm,Kernel._integer),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_OccurTerm2,"claire_==_OccurTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer5,"claire_==_integer5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._OccurTerm,choco._IntVar),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_OccurTerm1,"claire_>=_OccurTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._OccurTerm,Kernel._integer),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_OccurTerm2,"claire_>=_OccurTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._OccurTerm,choco._IntVar),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_OccurTerm1,"claire_<=_OccurTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._OccurTerm,Kernel._integer),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_OccurTerm2,"claire_<=_OccurTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,choco._IntVar,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC,_function_(claire__sup_equal_IntVar6,"claire_>=_IntVar6"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer5,"claire_>=_integer5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,choco._IntVar,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_IntVar6,"claire_<=_IntVar6"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,choco._OccurTerm),choco._Occurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer6,"claire_<=_integer6"));
  
  { (choco._EltTerm = ClaireClass::make("EltTerm",choco._Term,choco.it));
    CL_ADD_SLOT(choco._EltTerm,EltTerm,choco.lvalues,lvalues,nth_class1(Kernel._list,Kernel._integer),CNULL);
    CL_ADD_SLOT(choco._EltTerm,EltTerm,choco.indexVar,indexVar,choco._IntVar,CNULL);
    } 
  
  choco.getNth->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),choco._UnTerm),choco._EltTerm,
  	NEW_ALLOC,_function_(choco_getNth_list1,"choco_getNth_list1"));
  
  choco.getNth->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),choco._IntVar),choco._EltTerm,
  	NEW_ALLOC,_function_(choco_getNth_list2,"choco_getNth_list2"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._EltTerm,choco._IntVar),choco._Elt,
  	NEW_ALLOC,_function_(claire__equal_equal_EltTerm1,"claire_==_EltTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._EltTerm),choco._Elt,
  	NEW_ALLOC,_function_(claire__equal_equal_IntVar7,"claire_==_IntVar7"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._EltTerm,Kernel._integer),choco._Elt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_EltTerm2,"claire_==_EltTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._EltTerm),choco._Elt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer6,"claire_==_integer6"));
  
  { (choco._Elt2Term = ClaireClass::make("Elt2Term",choco._Term,choco.it));
    CL_ADD_SLOT(choco._Elt2Term,Elt2Term,choco.valueTable,valueTable,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(choco._Elt2Term,Elt2Term,choco.dim1,dim1,Kernel._integer,0);
    CL_ADD_SLOT(choco._Elt2Term,Elt2Term,choco.dim2,dim2,Kernel._integer,0);
    CL_ADD_SLOT(choco._Elt2Term,Elt2Term,choco.index1Var,index1Var,choco._IntVar,CNULL);
    CL_ADD_SLOT(choco._Elt2Term,Elt2Term,choco.index2Var,index2Var,choco._IntVar,CNULL);
    } 
  
  choco.makeElt2Term->addMethod(list::domain(5,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(Kernel.emptySet,1,_oid_(set::alloc(Kernel.emptySet,1,_oid_(Kernel._integer))))),
    Kernel._integer,
    Kernel._integer,
    choco._IntVar,
    choco._IntVar),choco._Elt2Term,
  	NEW_ALLOC,_function_(choco_makeElt2Term_table,"choco_makeElt2Term_table"));
  
  choco.getNth->addMethod(list::domain(3,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(Kernel.emptySet,1,_oid_(set::alloc(Kernel.emptySet,1,_oid_(Kernel._integer))))),choco._IntVar,choco._IntVar),choco._Elt2Term,
  	NEW_ALLOC,_function_(choco_getNth_table,"choco_getNth_table"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._Elt2Term,choco._IntVar),choco._Elt2,
  	NEW_ALLOC,_function_(claire__equal_equal_Elt2Term1,"claire_==_Elt2Term1"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._IntVar,choco._Elt2Term),choco._Elt2,
  	NEW_ALLOC,_function_(claire__equal_equal_IntVar8,"claire_==_IntVar8"));
  
  choco._equal_equal->addMethod(list::domain(2,choco._Elt2Term,Kernel._integer),choco._Elt2,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_Elt2Term2,"claire_==_Elt2Term2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,choco._Elt2Term),choco._Elt2,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer7,"claire_==_integer7"));
  
  { abstract_property(choco.updateInf);
    abstract_property(choco.updateSup);
    abstract_property(choco.removeVal);
    abstract_property(choco.isInstantiated);
    abstract_property(choco.isInstantiatedTo);
    abstract_property(choco.canBeInstantiatedTo);
    abstract_property(choco.canBeEqualTo);
    abstract_property(choco.canBeInstantiatedIn);
    abstract_property(choco.domainIncludedIn);
    abstract_property(choco.hasExactDomain);
    abstract_property(choco.getInf);
    abstract_property(choco.getSup);
    } 
  
  { abstract_property(choco.connectIntVarEvents);
    abstract_property(choco.reconnectIntVarEvents);
    abstract_property(choco.disconnectIntVarEvents);
    abstract_property(choco.connectSetVarEvents);
    abstract_property(choco.reconnectSetVarEvents);
    abstract_property(choco.disconnectSetVarEvents);
    abstract_property(choco.connectHook);
    abstract_property(choco.reconnectHook);
    abstract_property(choco.disconnectHook);
    } 
  
  { abstract_property(choco.connect);
    abstract_property(choco.disconnect);
    abstract_property(choco.reconnect);
    } 
  
  { abstract_property(choco.domainSequence);
    abstract_property(choco.domainSet);
    abstract_property(choco.updateDomainInf);
    abstract_property(choco.updateDomainSup);
    abstract_property(choco.getDomainInf);
    abstract_property(choco.getDomainSup);
    abstract_property(choco.containsValInDomain);
    abstract_property(choco.getNextValue);
    abstract_property(choco.getPrevValue);
    abstract_property(choco.removeDomainVal);
    abstract_property(choco.restrict);
    abstract_property(choco.getDomainCard);
    } 
  
  { abstract_property(choco.isActive);
    abstract_property(choco.getPriority);
    abstract_property(choco.getVar);
    abstract_property(choco.awake);
    } 
  
  abstract_property(choco.getNextActiveEventQueue);
  
  abstract_property(choco.propagateEvent);
  
  { abstract_property(choco.propagateNewLowerBound);
    abstract_property(choco.propagateNewUpperBound);
    abstract_property(choco.computeLowerBound);
    abstract_property(choco.computeUpperBound);
    } 
  
  { abstract_property(choco.checkNbPossible);
    abstract_property(choco.checkNbSure);
    } 
  
  GC_UNBIND;} 

