/***** CLAIRE Compilation of file palm.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:45 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>


palmClass palm;

// definition of the meta-model for palm 

void palmClass::metaLoad() { 
  GC_BIND;
  ClEnv->module_I = it;
// definition of the properties 
  palm.palmVersion = property::make("palmVersion",palm.it);
  palm.palmReleaseDate = property::make("palmReleaseDate",palm.it);
  palm.palmInfo = property::make("palmInfo",palm.it);
  palm.showPalmLicense = property::make("showPalmLicense",palm.it);
  palm.maxRelaxLvl = property::make("maxRelaxLvl",palm.it);
  palm.palmSolver = property::make("palmSolver",palm.it);
  palm.rootUFboxes = property::make("rootUFboxes",palm.it);
  palm.relaxedUFboxes = property::make("relaxedUFboxes",2,palm.it);
  palm.allUFboxes = property::make("allUFboxes",2,palm.it);
  palm.userRepresentation = property::make("userRepresentation",palm.it);
  palm.toBeAwakenConstraints = property::make("toBeAwakenConstraints",2,palm.it);
  palm.boundRestEvtQueue = property::make("boundRestEvtQueue",palm.it);
  palm.removalRestEvtQueue = property::make("removalRestEvtQueue",palm.it);
  palm.dummyVariable = property::make("dummyVariable",palm.it);
  palm.contradictory = property::make("contradictory",palm.it);
  palm.explanation = property::make("explanation",palm.it);
  palm.previousValue = property::make("previousValue",palm.it);
  palm.variable = property::make("variable",palm.it);
  palm.originalInf = property::make("originalInf",palm.it);
  palm.originalSup = property::make("originalSup",palm.it);
  palm.explanationOnInf = property::make("explanationOnInf",palm.it);
  palm.explanationOnSup = property::make("explanationOnSup",palm.it);
  palm.enumerationConstraints = property::make("enumerationConstraints",palm.it);
  palm.negEnumerationConstraints = property::make("negEnumerationConstraints",palm.it);
  palm.restInfEvt = property::make("restInfEvt",palm.it);
  palm.restSupEvt = property::make("restSupEvt",palm.it);
  palm.restValEvt = property::make("restValEvt",palm.it);
  palm.nbElements = property::make("nbElements",palm.it);
  palm.booleanVector = property::make("booleanVector",palm.it);
  palm.precVector = property::make("precVector",palm.it);
  palm.succVector = property::make("succVector",palm.it);
  palm.firstSuccPres = property::make("firstSuccPres",palm.it);
  palm.firstPrecPres = property::make("firstPrecPres",palm.it);
  palm.firstSuccAbs = property::make("firstSuccAbs",palm.it);
  palm.firstPrecAbs = property::make("firstPrecAbs",palm.it);
  palm.needInfComputation = property::make("needInfComputation",palm.it);
  palm.needSupComputation = property::make("needSupComputation",palm.it);
  palm.explanationOnVal = property::make("explanationOnVal",palm.it);
  palm.instantiationConstraints = property::make("instantiationConstraints",palm.it);
  palm.negInstantiationConstraints = property::make("negInstantiationConstraints",palm.it);
  palm.manager = property::make("manager",palm.it);
  palm.path = property::make("path",palm.it);
  palm.maxMoves = property::make("maxMoves",palm.it);
  palm.explanations = property::make("explanations",palm.it);
  palm.lastExplanation = property::make("lastExplanation",palm.it);
  palm.isFull = property::make("isFull",palm.it);
  palm.extender = property::make("extender",palm.it);
  palm.nextBranching = property::make("nextBranching",2,palm.it);
  palm.branching = property::make("branching",palm.it);
  palm.feasible = property::make("feasible",palm.it);
  palm.lstat = property::make("lstat",palm.it);
  palm.statistics = property::make("statistics",palm.it);
  palm.finished = property::make("finished",palm.it);
  palm.state = property::make("state",palm.it);
  palm.learning = property::make("learning",palm.it);
  palm.extending = property::make("extending",palm.it);
  palm.repairing = property::make("repairing",palm.it);
  palm.maximizing = property::make("maximizing",palm.it);
  palm.objective = property::make("objective",palm.it);
  palm.lowerBound = property::make("lowerBound",palm.it);
  palm.upperBound = property::make("upperBound",palm.it);
  palm.optimum = property::make("optimum",2,palm.it);
  palm.dynamicCuts = property::make("dynamicCuts",2,palm.it);
  palm.shortName = property::make("shortName",palm.it);
  palm.fatherBox = property::make("fatherBox",palm.it);
  palm.childrenBoxes = property::make("childrenBoxes",palm.it);
  palm.not_I = property::make("not!",palm.it);
  palm.firstCode29bits = property::make("firstCode29bits",palm.it);
  palm.secondCode29bits = property::make("secondCode29bits",palm.it);
  palm.decode29bits = property::make("decode29bits",palm.it);
  palm.makePalmIntDomain = property::make("makePalmIntDomain",palm.it);
  palm.removedlist_I = property::make("removedlist!",palm.it);
  palm.firstElement = property::make("firstElement",palm.it);
  palm.addDomainVal = property::make("addDomainVal",palm.it);
  palm.self_explain = property::make("self_explain",palm.it);
  palm.merge = property::make("merge",palm.it);
  palm.firstValue = property::make("firstValue",palm.it);
  palm.updateDataStructures = property::make("updateDataStructures",palm.it);
  palm.updateDataStructuresOnVariable = property::make("updateDataStructuresOnVariable",3,palm.it);
  palm.updateDataStructuresOnConstraints = property::make("updateDataStructuresOnConstraints",palm.it);
  palm.updateDataStructuresOnConstraint = property::make("updateDataStructuresOnConstraint",3,palm.it);
  palm.updateDataStructuresOnRestore = property::make("updateDataStructuresOnRestore",palm.it);
  palm.updateDataStructuresOnRestoreVariable = property::make("updateDataStructuresOnRestoreVariable",3,palm.it);
  palm.updateDataStructuresOnRestoreConstraints = property::make("updateDataStructuresOnRestoreConstraints",palm.it);
  palm.updateDataStructuresOnRestoreConstraint = property::make("updateDataStructuresOnRestoreConstraint",3,palm.it);
  palm.restoreInf = property::make("restoreInf",palm.it);
  palm.postRestoreInf = property::make("postRestoreInf",palm.it);
  palm.restoreSup = property::make("restoreSup",palm.it);
  palm.postRestoreSup = property::make("postRestoreSup",palm.it);
  palm.postRestoreEvent = property::make("postRestoreEvent",palm.it);
  palm.restoreVariableExplanation = property::make("restoreVariableExplanation",palm.it);
  palm.restoreVal = property::make("restoreVal",palm.it);
  palm.postRestoreVal = property::make("postRestoreVal",palm.it);
  palm.valid_ask = property::make("valid?",palm.it);
  palm.clone = property::make("clone",palm.it);
  palm.makeIncInfExplanation = property::make("makeIncInfExplanation",palm.it);
  palm.makeDecSupExplanation = property::make("makeDecSupExplanation",palm.it);
  palm.postRemoveVal = property::make("postRemoveVal",palm.it);
  palm.makeRemovalExplanation = property::make("makeRemovalExplanation",palm.it);
  palm.constraint = property::make("constraint",palm.it);
  palm.index = property::make("index",palm.it);
  palm.timeStamp = property::make("timeStamp",palm.it);
  palm.weight = property::make("weight",palm.it);
  palm.everConnected = property::make("everConnected",palm.it);
  palm.indirect = property::make("indirect",palm.it);
  palm.addingExplanation = property::make("addingExplanation",palm.it);
  palm.dependingConstraints = property::make("dependingConstraints",palm.it);
  palm.dependencyNet = property::make("dependencyNet",palm.it);
  palm.searchInfo = property::make("searchInfo",palm.it);
  palm.ufBox = property::make("ufBox",palm.it);
  palm.controllingConstraints = property::make("controllingConstraints",palm.it);
  palm.info = property::make("info",2,palm.it);
  palm.addDependency = property::make("addDependency",palm.it);
  palm.removeDependency = property::make("removeDependency",palm.it);
  palm.undo = property::make("undo",palm.it);
  palm.postUndoRemoval = property::make("postUndoRemoval",3,palm.it);
  palm.indirect_ask = property::make("indirect?",palm.it);
  palm.setIndirect = property::make("setIndirect",palm.it);
  palm.setDirect = property::make("setDirect",palm.it);
  palm.setIndirectDependance = property::make("setIndirectDependance",palm.it);
  palm.removeIndirectDependance = property::make("removeIndirectDependance",palm.it);
  palm.informControllersOfDeactivation = property::make("informControllersOfDeactivation",palm.it);
  palm.takeIntoAccountStatusChange = property::make("takeIntoAccountStatusChange",palm.it);
  palm.mergeConstraints = property::make("mergeConstraints",palm.it);
  palm.addConstraint = property::make("addConstraint",palm.it);
  palm.activate = property::make("activate",palm.it);
  palm.deactivate = property::make("deactivate",palm.it);
  palm.initHook = property::make("initHook",palm.it);
  palm.addControl = property::make("addControl",palm.it);
  palm.makePalmUnIntConstraint = property::make("makePalmUnIntConstraint",palm.it);
  palm.makePalmBinIntConstraint = property::make("makePalmBinIntConstraint",palm.it);
  palm.makePalmLargeIntConstraint = property::make("makePalmLargeIntConstraint",palm.it);
  palm.checkPalm = property::make("checkPalm",palm.it);
  palm.hasSupport = property::make("hasSupport",palm.it);
  palm.makePalmLinComb = property::make("makePalmLinComb",palm.it);
  palm.explainVariablesLB = property::make("explainVariablesLB",palm.it);
  palm.explainVariablesUB = property::make("explainVariablesUB",palm.it);
  palm.raisePalmFakeContradiction = property::make("raisePalmFakeContradiction",palm.it);
  palm.abstractDecInf = property::make("abstractDecInf",palm.it);
  palm.abstractIncSup = property::make("abstractIncSup",palm.it);
  palm.abstractRestoreVal = property::make("abstractRestoreVal",palm.it);
  palm.e_dashallDifferent = property::make("e-allDifferent",claire.it);
  palm.e_dashcompleteAllDiff = property::make("e-completeAllDiff",claire.it);
  palm.e_dashpermutation = property::make("e-permutation",claire.it);
  palm.publishDeletion = property::make("publishDeletion",palm.it);
  palm.theHole = property::make("theHole",palm.it);
  palm.becauseOf = property::make("becauseOf",palm.it);
  palm.e_dashgcc = property::make("e-gcc",palm.it);
  palm.intervals = property::make("intervals",palm.it);
  palm.mustDiminishFlowFromSource = property::make("mustDiminishFlowFromSource",palm.it);
  palm.removeUselessEdges = property::make("removeUselessEdges",palm.it);
  palm.checkPossible = property::make("checkPossible",palm.it);
  palm.checkSure = property::make("checkSure",palm.it);
  palm.checkInf = property::make("checkInf",palm.it);
  palm.checkSup = property::make("checkSup",palm.it);
  palm.checkNbPossible = property::make("checkNbPossible",palm.it);
  palm.checkNbSure = property::make("checkNbSure",palm.it);
  palm.makePalmEltConstraint = property::make("makePalmEltConstraint",palm.it);
  palm.updateValueFromIndex = property::make("updateValueFromIndex",palm.it);
  palm.updateIndexFromValue = property::make("updateIndexFromValue",palm.it);
  palm.awakeOnRestore = property::make("awakeOnRestore",palm.it);
  palm.whyTrueOrFalse = property::make("whyTrueOrFalse",palm.it);
  palm.cachedTuples = property::make("cachedTuples",palm.it);
  palm.matrix = property::make("matrix",palm.it);
  palm.feasTest = property::make("feasTest",palm.it);
  palm.supportsOnV1 = property::make("supportsOnV1",palm.it);
  palm.nbSupportsOnV1 = property::make("nbSupportsOnV1",palm.it);
  palm.supportsOnV2 = property::make("supportsOnV2",palm.it);
  palm.nbSupportsOnV2 = property::make("nbSupportsOnV2",palm.it);
  palm.makeAC4constraint = property::make("makeAC4constraint",palm.it);
  palm.project = property::make("project",palm.it);
  palm.deleteConstraint = property::make("deleteConstraint",palm.it);
  palm.empty_ask = property::make("empty?",palm.it);
  palm.empties = property::make("empties",palm.it);
  palm.contains_ask = property::make("contains?",palm.it);
  palm.clean = property::make("clean",palm.it);
  palm.makeValueRestorations = property::make("makeValueRestorations",palm.it);
  palm.addDependencies = property::make("addDependencies",palm.it);
  palm.removeDependencies = property::make("removeDependencies",palm.it);
  palm.makePalmEngine = property::make("makePalmEngine",palm.it);
  palm.resetEvent = property::make("resetEvent",palm.it);
  palm.resetPoppingQueue = property::make("resetPoppingQueue",palm.it);
  palm.resetEventQueue = property::make("resetEventQueue",palm.it);
  palm.resetEvents = property::make("resetEvents",palm.it);
  palm.flushEvents = property::make("flushEvents",palm.it);
  palm.makeBoundIntVar = property::make("makeBoundIntVar",palm.it);
  palm.raiseSystemContradiction = property::make("raiseSystemContradiction",palm.it);
  palm.remove = property::make("remove",palm.it);
  palm.restoreVariableExplanations = property::make("restoreVariableExplanations",palm.it);
  palm.checkVariableDomainAgainstRemValEvt = property::make("checkVariableDomainAgainstRemValEvt",palm.it);
  palm.theInf = property::make("theInf",palm.it);
  palm.theSup = property::make("theSup",palm.it);
  palm.theDom = property::make("theDom",palm.it);
  palm.theVars = property::make("theVars",palm.it);
  palm.explain = property::make("explain",palm.it);
  palm.getGlobalStatistics = property::make("getGlobalStatistics",palm.it);
  palm.getRuntimeStatistic = property::make("getRuntimeStatistic",palm.it);
  palm.setRuntimeStatistic = property::make("setRuntimeStatistic",palm.it);
  palm.incRuntimeStatistic = property::make("incRuntimeStatistic",palm.it);
  palm.decRuntimeStatistic = property::make("decRuntimeStatistic",palm.it);
  palm.printRuntimeStatistics = property::make("printRuntimeStatistics",palm.it);
  palm.reset = property::make("reset",palm.it);
  palm.initPalmSolver = property::make("initPalmSolver",palm.it);
  palm.attachPalmState = property::make("attachPalmState",palm.it);
  palm.attachPalmExtend = property::make("attachPalmExtend",palm.it);
  palm.attachPalmBranchings = property::make("attachPalmBranchings",palm.it);
  palm.attachPalmLearn = property::make("attachPalmLearn",palm.it);
  palm.attachPalmRepair = property::make("attachPalmRepair",palm.it);
  palm.initPalmBranchAndBound = property::make("initPalmBranchAndBound",palm.it);
  palm.searchOneSolution = property::make("searchOneSolution",palm.it);
  palm.run = property::make("run",palm.it);
  palm.storeSolution = property::make("storeSolution",palm.it);
  palm.repair = property::make("repair",3,palm.it);
  palm.extend = property::make("extend",3,palm.it);
  palm.runonce = property::make("runonce",palm.it);
  palm.postDynamicCut = property::make("postDynamicCut",palm.it);
  palm.getObjectiveValue = property::make("getObjectiveValue",palm.it);
  palm.explore = property::make("explore",palm.it);
  palm.selectBranchingItem = property::make("selectBranchingItem",3,palm.it);
  palm.selectDecisions = property::make("selectDecisions",3,palm.it);
  palm.propagateAllDecisionConstraints = property::make("propagateAllDecisionConstraints",palm.it);
  palm.selectAuthorizedDecisions = property::make("selectAuthorizedDecisions",palm.it);
  palm.getNextDecisions = property::make("getNextDecisions",3,palm.it);
  palm.checkAcceptable = property::make("checkAcceptable",3,palm.it);
  palm.learnFromRejection = property::make("learnFromRejection",3,palm.it);
  palm.addDecision = property::make("addDecision",3,palm.it);
  palm.learnFromContradiction = property::make("learnFromContradiction",3,palm.it);
  palm.selectDecisionToUndo = property::make("selectDecisionToUndo",3,palm.it);
  palm.removeDecision = property::make("removeDecision",3,palm.it);
  palm.negate = property::make("negate",3,palm.it);
  palm.reverseDecision = property::make("reverseDecision",3,palm.it);
  palm.learnFromRemoval = property::make("learnFromRemoval",3,palm.it);
  palm.sortConstraintsToUndo = property::make("sortConstraintsToUndo",3,palm.it);
  palm.checkAcceptableRelaxation = property::make("checkAcceptableRelaxation",3,palm.it);
  palm.standard_better_constraint = property::make("standard_better_constraint",palm.it);
  palm.standard_better_ordered_constraint = property::make("standard_better_ordered_constraint",palm.it);
  palm.getMinDomVar = property::make("getMinDomVar",palm.it);
  palm.assign = property::make("assign",palm.it);
  palm.getMinDomDegVar = property::make("getMinDomDegVar",palm.it);
  palm.discardCurrentSolution = property::make("discardCurrentSolution",palm.it);
  palm.makePathRepairLearn = property::make("makePathRepairLearn",palm.it);
  palm.addForbiddenSituation = property::make("addForbiddenSituation",palm.it);
  palm.isForbidden = property::make("isForbidden",palm.it);
  palm.informConstraintsInExplanation = property::make("informConstraintsInExplanation",3,palm.it);
  palm.isContradictory = property::make("isContradictory",palm.it);
  palm.needToAwakeC1 = property::make("needToAwakeC1",palm.it);
  palm.needToAwakeC2 = property::make("needToAwakeC2",palm.it);
  palm.setNeedToAwake = property::make("setNeedToAwake",palm.it);
  palm.needToAwake = property::make("needToAwake",palm.it);
  palm.checkStatusAndReport = property::make("checkStatusAndReport",palm.it);
  palm.checkConstraintState = property::make("checkConstraintState",palm.it);
  palm.needToAwakeNegC1 = property::make("needToAwakeNegC1",palm.it);
  palm.needToAwakeNegC2 = property::make("needToAwakeNegC2",palm.it);
  palm.needToAwakeTrue = property::make("needToAwakeTrue",palm.it);
  palm.needToAwakeFalse = property::make("needToAwakeFalse",palm.it);
  palm.checkOnNbTrue = property::make("checkOnNbTrue",palm.it);
  palm.checkOnNbFalse = property::make("checkOnNbFalse",palm.it);
  palm.explainTrueConstraints = property::make("explainTrueConstraints",palm.it);
  palm.explainFalseConstraints = property::make("explainFalseConstraints",palm.it);
  palm.makePalmProblem = property::make("makePalmProblem",palm.it);
  palm.setObjective = property::make("setObjective",palm.it);
  palm.setSolutionVars = property::make("setSolutionVars",palm.it);
  palm.solve = property::make("solve",palm.it);
  palm.minimize = property::make("minimize",palm.it);
  palm.maximize = property::make("maximize",palm.it);
  palm.makeIntVar = property::make("makeIntVar",palm.it);
  palm.makeConstantPalmIntVar = property::make("makeConstantPalmIntVar",palm.it);
  palm.setMin = property::make("setMin",palm.it);
  palm.setMax = property::make("setMax",palm.it);
  palm.setVal = property::make("setVal",palm.it);
  palm.remVal = property::make("remVal",palm.it);
  palm.makeConstraintTable = property::make("makeConstraintTable",palm.it);
  palm.e_dashcard = property::make("e-card",palm.it);
  palm.sign1 = property::make("sign1",palm.it);
  palm.sign2 = property::make("sign2",palm.it);
  palm.lcoeff = property::make("lcoeff",palm.it);
  palm.lvars = property::make("lvars",palm.it);
  palm.e_dashsumVars = property::make("e-sumVars",palm.it);
  palm.makePalmOccurConstraint = property::make("makePalmOccurConstraint",palm.it);
  palm.occur = property::make("occur",palm.it);
  palm.e_dashifThen = property::make("e-ifThen",palm.it);
  palm.makePalmCardinalityConstraint = property::make("makePalmCardinalityConstraint",palm.it);
  palm.e_dashatleast = property::make("e-atleast",palm.it);
  palm.e_dashatmost = property::make("e-atmost",palm.it);
  palm.startInfoBox = property::make("startInfoBox",palm.it);
  palm.endInfoBox = property::make("endInfoBox",palm.it);
  palm.showInfoBox = property::make("showInfoBox",palm.it);
  palm.getConstraints = property::make("getConstraints",palm.it);
  palm.setUserFriendly = property::make("setUserFriendly",palm.it);
  palm.setUserRepresentation = property::make("setUserRepresentation",palm.it);
  palm.checkFullPalm = property::make("checkFullPalm",palm.it);
  
  // instructions from module sources
  { global_variable * _CL_obj = (palm.MININT = (global_variable *) Core._global_variable->instantiate("MININT",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,-99999999);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.MAXINT = (global_variable *) Core._global_variable->instantiate("MAXINT",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,99999999);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.UNKNOWNINT = (global_variable *) Core._global_variable->instantiate("UNKNOWNINT",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,-100000000);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Ephemeral = (global_variable *) Core._global_variable->instantiate("Ephemeral",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Ephemeral));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Problem = (global_variable *) Core._global_variable->instantiate("Problem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Problem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Solution = (global_variable *) Core._global_variable->instantiate("Solution",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Solution));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.AbstractConstraint = (global_variable *) Core._global_variable->instantiate("AbstractConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._AbstractConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.IntConstraint = (global_variable *) Core._global_variable->instantiate("IntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._IntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.UnIntConstraint = (global_variable *) Core._global_variable->instantiate("UnIntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._UnIntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BinIntConstraint = (global_variable *) Core._global_variable->instantiate("BinIntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BinIntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Elt = (global_variable *) Core._global_variable->instantiate("Elt",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Elt));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.TernIntConstraint = (global_variable *) Core._global_variable->instantiate("TernIntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._TernIntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeIntConstraint = (global_variable *) Core._global_variable->instantiate("LargeIntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeIntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LinComb = (global_variable *) Core._global_variable->instantiate("LinComb",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LinComb));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Delayer = (global_variable *) Core._global_variable->instantiate("Delayer",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Delayer));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.CompositeConstraint = (global_variable *) Core._global_variable->instantiate("CompositeConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._CompositeConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BinCompositeConstraint = (global_variable *) Core._global_variable->instantiate("BinCompositeConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BinCompositeConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BinBoolConstraint = (global_variable *) Core._global_variable->instantiate("BinBoolConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BinBoolConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BinBoolConstraintWCounterOpp = (global_variable *) Core._global_variable->instantiate("BinBoolConstraintWCounterOpp",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BinBoolConstraintWCounterOpp));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeCompositeConstraint = (global_variable *) Core._global_variable->instantiate("LargeCompositeConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeCompositeConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeBoolConstraint = (global_variable *) Core._global_variable->instantiate("LargeBoolConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeBoolConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeConjunction = (global_variable *) Core._global_variable->instantiate("LargeConjunction",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeConjunction));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeDisjunction = (global_variable *) Core._global_variable->instantiate("LargeDisjunction",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeDisjunction));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LargeBoolConstraintWCounterOpp = (global_variable *) Core._global_variable->instantiate("LargeBoolConstraintWCounterOpp",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LargeBoolConstraintWCounterOpp));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.AbstractVar = (global_variable *) Core._global_variable->instantiate("AbstractVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._AbstractVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.IntVar = (global_variable *) Core._global_variable->instantiate("IntVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._IntVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.AbstractDomain = (global_variable *) Core._global_variable->instantiate("AbstractDomain",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._AbstractDomain));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.AbstractIntDomain = (global_variable *) Core._global_variable->instantiate("AbstractIntDomain",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._AbstractIntDomain));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LinkedListIntDomain = (global_variable *) Core._global_variable->instantiate("LinkedListIntDomain",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LinkedListIntDomain));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PropagationEvent = (global_variable *) Core._global_variable->instantiate("PropagationEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._PropagationEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.ConstAwakeEvent = (global_variable *) Core._global_variable->instantiate("ConstAwakeEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._ConstAwakeEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Instantiation = (global_variable *) Core._global_variable->instantiate("Instantiation",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Instantiation));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.ValueRemovals = (global_variable *) Core._global_variable->instantiate("ValueRemovals",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._ValueRemovals));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BoundUpdate = (global_variable *) Core._global_variable->instantiate("BoundUpdate",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BoundUpdate));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.IncInf = (global_variable *) Core._global_variable->instantiate("IncInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._IncInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.DecSup = (global_variable *) Core._global_variable->instantiate("DecSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._DecSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LogicEngine = (global_variable *) Core._global_variable->instantiate("LogicEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LogicEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PropagationEngine = (global_variable *) Core._global_variable->instantiate("PropagationEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._PropagationEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.ChocEngine = (global_variable *) Core._global_variable->instantiate("ChocEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._ChocEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Solver = (global_variable *) Core._global_variable->instantiate("Solver",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Solver));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.GlobalSearchSolver = (global_variable *) Core._global_variable->instantiate("GlobalSearchSolver",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._GlobalSearchSolver));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.LocalSearchSolver = (global_variable *) Core._global_variable->instantiate("LocalSearchSolver",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._LocalSearchSolver));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.EventCollection = (global_variable *) Core._global_variable->instantiate("EventCollection",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._EventCollection));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.InstantiationStack = (global_variable *) Core._global_variable->instantiate("InstantiationStack",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._InstantiationStack));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.EventQueue = (global_variable *) Core._global_variable->instantiate("EventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._EventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BoundEventQueue = (global_variable *) Core._global_variable->instantiate("BoundEventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BoundEventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.RemovalEventQueue = (global_variable *) Core._global_variable->instantiate("RemovalEventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._RemovalEventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.ConstAwakeEventQueue = (global_variable *) Core._global_variable->instantiate("ConstAwakeEventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._ConstAwakeEventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Util = (global_variable *) Core._global_variable->instantiate("Util",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Util));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Matrix = (global_variable *) Core._global_variable->instantiate("Matrix",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Matrix));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Matrix2D = (global_variable *) Core._global_variable->instantiate("Matrix2D",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Matrix2D));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.IntMatrix2D = (global_variable *) Core._global_variable->instantiate("IntMatrix2D",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._IntMatrix2D));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BoolMatrix2D = (global_variable *) Core._global_variable->instantiate("BoolMatrix2D",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BoolMatrix2D));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.MatrixND = (global_variable *) Core._global_variable->instantiate("MatrixND",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._MatrixND));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.BoolMatrixND = (global_variable *) Core._global_variable->instantiate("BoolMatrixND",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._BoolMatrixND));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.IntMatrixND = (global_variable *) Core._global_variable->instantiate("IntMatrixND",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._IntMatrixND));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Term = (global_variable *) Core._global_variable->instantiate("Term",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._Term));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.OccurTerm = (global_variable *) Core._global_variable->instantiate("OccurTerm",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(choco._OccurTerm));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.CompleteAllDiff = (global_variable *) Core._global_variable->instantiate("CompleteAllDiff",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(ice._CompleteAllDiff));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.Permutation = (global_variable *) Core._global_variable->instantiate("Permutation",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(ice._Permutation));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.GlobalCardinality = (global_variable *) Core._global_variable->instantiate("GlobalCardinality",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(ice._GlobalCardinality));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.div_dash = (global_variable *) Core._global_variable->instantiate("div-",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.div_dash));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.div_plus = (global_variable *) Core._global_variable->instantiate("div+",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.div_plus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.make2DBoolMatrix = (global_variable *) Core._global_variable->instantiate("make2DBoolMatrix",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.make2DBoolMatrix));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.qsize = (global_variable *) Core._global_variable->instantiate("qsize",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.qsize));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.qLastRead = (global_variable *) Core._global_variable->instantiate("qLastRead",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.qLastRead));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.qLastEnqueued = (global_variable *) Core._global_variable->instantiate("qLastEnqueued",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.qLastEnqueued));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.sLastRead = (global_variable *) Core._global_variable->instantiate("sLastRead",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.sLastRead));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.sLastPushed = (global_variable *) Core._global_variable->instantiate("sLastPushed",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.sLastPushed));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isEmpty = (global_variable *) Core._global_variable->instantiate("isEmpty",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isEmpty));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.makeBipartiteSet = (global_variable *) Core._global_variable->instantiate("makeBipartiteSet",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.makeBipartiteSet));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.partition = (global_variable *) Core._global_variable->instantiate("partition",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.partition));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagationEngine = (global_variable *) Core._global_variable->instantiate("propagationEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagationEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.removalEvtQueue = (global_variable *) Core._global_variable->instantiate("removalEvtQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.removalEvtQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.boundEvtQueue = (global_variable *) Core._global_variable->instantiate("boundEvtQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.boundEvtQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.eventQueue = (global_variable *) Core._global_variable->instantiate("eventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.eventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.engine = (global_variable *) Core._global_variable->instantiate("engine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.engine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.globalSearchSolver = (global_variable *) Core._global_variable->instantiate("globalSearchSolver",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.globalSearchSolver));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.localSearchSolver = (global_variable *) Core._global_variable->instantiate("localSearchSolver",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.localSearchSolver));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.attachPropagationEngine = (global_variable *) Core._global_variable->instantiate("attachPropagationEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.attachPropagationEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.setActiveProblem = (global_variable *) Core._global_variable->instantiate("setActiveProblem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.setActiveProblem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.varsToStore = (global_variable *) Core._global_variable->instantiate("varsToStore",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.varsToStore));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.constraints = (global_variable *) Core._global_variable->instantiate("constraints",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.constraints));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.solutions = (global_variable *) Core._global_variable->instantiate("solutions",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.solutions));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.post = (global_variable *) Core._global_variable->instantiate("post",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.post));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.delayedConst1 = (global_variable *) Core._global_variable->instantiate("delayedConst1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.delayedConst1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.delayedConst2 = (global_variable *) Core._global_variable->instantiate("delayedConst2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.delayedConst2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.delayedConst3 = (global_variable *) Core._global_variable->instantiate("delayedConst3",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.delayedConst3));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.delayedConst4 = (global_variable *) Core._global_variable->instantiate("delayedConst4",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.delayedConst4));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.instEvtStack = (global_variable *) Core._global_variable->instantiate("instEvtStack",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.instEvtStack));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagationOverflow = (global_variable *) Core._global_variable->instantiate("propagationOverflow",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagationOverflow));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.contradictionCause = (global_variable *) Core._global_variable->instantiate("contradictionCause",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.contradictionCause));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.maxSize = (global_variable *) Core._global_variable->instantiate("maxSize",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.maxSize));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getNextActiveEventQueue = (global_variable *) Core._global_variable->instantiate("getNextActiveEventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getNextActiveEventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.postUpdateInf = (global_variable *) Core._global_variable->instantiate("postUpdateInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.postUpdateInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.postUpdateSup = (global_variable *) Core._global_variable->instantiate("postUpdateSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.postUpdateSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagateEvent = (global_variable *) Core._global_variable->instantiate("propagateEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagateEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.redundantEvent = (global_variable *) Core._global_variable->instantiate("redundantEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.redundantEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isPopping = (global_variable *) Core._global_variable->instantiate("isPopping",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isPopping));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.flushEventQueue = (global_variable *) Core._global_variable->instantiate("flushEventQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.flushEventQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.popSomeEvents = (global_variable *) Core._global_variable->instantiate("popSomeEvents",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.popSomeEvents));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.registerEvent = (global_variable *) Core._global_variable->instantiate("registerEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.registerEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.hook = (global_variable *) Core._global_variable->instantiate("hook",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.hook));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.knownInt = (global_variable *) Core._global_variable->instantiate("knownInt",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.knownInt));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isInstantiated = (global_variable *) Core._global_variable->instantiate("isInstantiated",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isInstantiated));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isInstantiatedTo = (global_variable *) Core._global_variable->instantiate("isInstantiatedTo",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isInstantiatedTo));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.canBeInstantiatedTo = (global_variable *) Core._global_variable->instantiate("canBeInstantiatedTo",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.canBeInstantiatedTo));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getInf = (global_variable *) Core._global_variable->instantiate("getInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getSup = (global_variable *) Core._global_variable->instantiate("getSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.removeVal = (global_variable *) Core._global_variable->instantiate("removeVal",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.removeVal));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updateInf = (global_variable *) Core._global_variable->instantiate("updateInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updateInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updateSup = (global_variable *) Core._global_variable->instantiate("updateSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updateSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.instantiate = (global_variable *) Core._global_variable->instantiate("instantiate",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.instantiate));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getDomainSize = (global_variable *) Core._global_variable->instantiate("getDomainSize",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getDomainSize));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.connectIntVarEvents = (global_variable *) Core._global_variable->instantiate("connectIntVarEvents",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.connectIntVarEvents));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.reconnectIntVarEvents = (global_variable *) Core._global_variable->instantiate("reconnectIntVarEvents",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.reconnectIntVarEvents));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.disconnectIntVarEvents = (global_variable *) Core._global_variable->instantiate("disconnectIntVarEvents",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.disconnectIntVarEvents));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.connectEvent = (global_variable *) Core._global_variable->instantiate("connectEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.connectEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.disconnectEvent = (global_variable *) Core._global_variable->instantiate("disconnectEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.disconnectEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.reconnectEvent = (global_variable *) Core._global_variable->instantiate("reconnectEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.reconnectEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.connectHook = (global_variable *) Core._global_variable->instantiate("connectHook",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.connectHook));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.reconnectHook = (global_variable *) Core._global_variable->instantiate("reconnectHook",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.reconnectHook));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.disconnectHook = (global_variable *) Core._global_variable->instantiate("disconnectHook",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.disconnectHook));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.bucket = (global_variable *) Core._global_variable->instantiate("bucket",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.bucket));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbConstraints = (global_variable *) Core._global_variable->instantiate("nbConstraints",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbConstraints));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.problem = (global_variable *) Core._global_variable->instantiate("problem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.problem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.closeIntVar = (global_variable *) Core._global_variable->instantiate("closeIntVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.closeIntVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.addIntVar = (global_variable *) Core._global_variable->instantiate("addIntVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.addIntVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nextConst = (global_variable *) Core._global_variable->instantiate("nextConst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nextConst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagationEngine = (global_variable *) Core._global_variable->instantiate("propagationEngine",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagationEngine));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.idxInQueue = (global_variable *) Core._global_variable->instantiate("idxInQueue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.idxInQueue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nextEventPostIndex = (global_variable *) Core._global_variable->instantiate("nextEventPostIndex",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nextEventPostIndex));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.cause = (global_variable *) Core._global_variable->instantiate("cause",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.cause));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.modifiedVar = (global_variable *) Core._global_variable->instantiate("modifiedVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.modifiedVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.many = (global_variable *) Core._global_variable->instantiate("many",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.many));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.valueStack = (global_variable *) Core._global_variable->instantiate("valueStack",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.valueStack));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbVals = (global_variable *) Core._global_variable->instantiate("nbVals",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbVals));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.causeStack = (global_variable *) Core._global_variable->instantiate("causeStack",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.causeStack));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.maxVals = (global_variable *) Core._global_variable->instantiate("maxVals",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.maxVals));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.connect = (global_variable *) Core._global_variable->instantiate("connect",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.connect));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.disconnect = (global_variable *) Core._global_variable->instantiate("disconnect",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.disconnect));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.reconnect = (global_variable *) Core._global_variable->instantiate("reconnect",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.reconnect));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.connectIntVar = (global_variable *) Core._global_variable->instantiate("connectIntVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.connectIntVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.constAwakeEvent = (global_variable *) Core._global_variable->instantiate("constAwakeEvent",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.constAwakeEvent));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.delay = (global_variable *) Core._global_variable->instantiate("delay",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.delay));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isActive = (global_variable *) Core._global_variable->instantiate("isActive",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isActive));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getPriority = (global_variable *) Core._global_variable->instantiate("getPriority",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getPriority));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getVar = (global_variable *) Core._global_variable->instantiate("getVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getNbVars = (global_variable *) Core._global_variable->instantiate("getNbVars",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getNbVars));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.assignIndices = (global_variable *) Core._global_variable->instantiate("assignIndices",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.assignIndices));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getConstraintIdx = (global_variable *) Core._global_variable->instantiate("getConstraintIdx",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getConstraintIdx));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.setConstraintIndex = (global_variable *) Core._global_variable->instantiate("setConstraintIndex",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.setConstraintIndex));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.askIfEntailed = (global_variable *) Core._global_variable->instantiate("askIfEntailed",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.askIfEntailed));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.testIfSatisfied = (global_variable *) Core._global_variable->instantiate("testIfSatisfied",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.testIfSatisfied));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.testIfCompletelyInstantiated = (global_variable *) Core._global_variable->instantiate("testIfCompletelyInstantiated",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.testIfCompletelyInstantiated));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagate = (global_variable *) Core._global_variable->instantiate("propagate",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagate));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awake = (global_variable *) Core._global_variable->instantiate("awake",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awake));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.opposite = (global_variable *) Core._global_variable->instantiate("opposite",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.opposite));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getProblem = (global_variable *) Core._global_variable->instantiate("getProblem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getProblem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.setPassive = (global_variable *) Core._global_variable->instantiate("setPassive",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.setPassive));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.touchedConst = (global_variable *) Core._global_variable->instantiate("touchedConst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.touchedConst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.initialized = (global_variable *) Core._global_variable->instantiate("initialized",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.initialized));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.priority = (global_variable *) Core._global_variable->instantiate("priority",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.priority));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awakeOnVar = (global_variable *) Core._global_variable->instantiate("awakeOnVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awakeOnVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awakeOnInf = (global_variable *) Core._global_variable->instantiate("awakeOnInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awakeOnInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awakeOnSup = (global_variable *) Core._global_variable->instantiate("awakeOnSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awakeOnSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awakeOnInst = (global_variable *) Core._global_variable->instantiate("awakeOnInst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awakeOnInst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.awakeOnRem = (global_variable *) Core._global_variable->instantiate("awakeOnRem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.awakeOnRem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.constAwake = (global_variable *) Core._global_variable->instantiate("constAwake",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.constAwake));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.doAwakeOnInf = (global_variable *) Core._global_variable->instantiate("doAwakeOnInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.doAwakeOnInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.doAwakeOnSup = (global_variable *) Core._global_variable->instantiate("doAwakeOnSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.doAwakeOnSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.askIfTrue = (global_variable *) Core._global_variable->instantiate("askIfTrue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.askIfTrue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.doAwake = (global_variable *) Core._global_variable->instantiate("doAwake",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.doAwake));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.doAwakeOnRem = (global_variable *) Core._global_variable->instantiate("doAwakeOnRem",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.doAwakeOnRem));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.testIfTrue = (global_variable *) Core._global_variable->instantiate("testIfTrue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.testIfTrue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.doPropagate = (global_variable *) Core._global_variable->instantiate("doPropagate",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.doPropagate));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.closeLargeIntConstraint = (global_variable *) Core._global_variable->instantiate("closeLargeIntConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.closeLargeIntConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.inf = (global_variable *) Core._global_variable->instantiate("inf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.inf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.sup = (global_variable *) Core._global_variable->instantiate("sup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.sup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.v1 = (global_variable *) Core._global_variable->instantiate("v1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.v1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.v2 = (global_variable *) Core._global_variable->instantiate("v2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.v2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.idx1 = (global_variable *) Core._global_variable->instantiate("idx1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.idx1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.idx2 = (global_variable *) Core._global_variable->instantiate("idx2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.idx2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.cste = (global_variable *) Core._global_variable->instantiate("cste",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.cste));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.indices = (global_variable *) Core._global_variable->instantiate("indices",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.indices));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbVars = (global_variable *) Core._global_variable->instantiate("nbVars",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbVars));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.offset = (global_variable *) Core._global_variable->instantiate("offset",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.offset));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updtInfEvt = (global_variable *) Core._global_variable->instantiate("updtInfEvt",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updtInfEvt));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updtSupEvt = (global_variable *) Core._global_variable->instantiate("updtSupEvt",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updtSupEvt));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.remValEvt = (global_variable *) Core._global_variable->instantiate("remValEvt",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.remValEvt));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.domainSequence = (global_variable *) Core._global_variable->instantiate("domainSequence",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.domainSequence));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.domainSet = (global_variable *) Core._global_variable->instantiate("domainSet",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.domainSet));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updateDomainInf = (global_variable *) Core._global_variable->instantiate("updateDomainInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updateDomainInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.updateDomainSup = (global_variable *) Core._global_variable->instantiate("updateDomainSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.updateDomainSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getDomainInf = (global_variable *) Core._global_variable->instantiate("getDomainInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getDomainInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getDomainSup = (global_variable *) Core._global_variable->instantiate("getDomainSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getDomainSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.containsValInDomain = (global_variable *) Core._global_variable->instantiate("containsValInDomain",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.containsValInDomain));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getNextValue = (global_variable *) Core._global_variable->instantiate("getNextValue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getNextValue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getPrevValue = (global_variable *) Core._global_variable->instantiate("getPrevValue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getPrevValue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.removeDomainVal = (global_variable *) Core._global_variable->instantiate("removeDomainVal",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.removeDomainVal));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.restrict = (global_variable *) Core._global_variable->instantiate("restrict",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.restrict));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getDomainCard = (global_variable *) Core._global_variable->instantiate("getDomainCard",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getDomainCard));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.bucketSize = (global_variable *) Core._global_variable->instantiate("bucketSize",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.bucketSize));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.loffset = (global_variable *) Core._global_variable->instantiate("loffset",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.loffset));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagateNewLowerBound = (global_variable *) Core._global_variable->instantiate("propagateNewLowerBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagateNewLowerBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.propagateNewUpperBound = (global_variable *) Core._global_variable->instantiate("propagateNewUpperBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.propagateNewUpperBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.computeLowerBound = (global_variable *) Core._global_variable->instantiate("computeLowerBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.computeLowerBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.computeUpperBound = (global_variable *) Core._global_variable->instantiate("computeUpperBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.computeUpperBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbPosVars = (global_variable *) Core._global_variable->instantiate("nbPosVars",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbPosVars));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.coefs = (global_variable *) Core._global_variable->instantiate("coefs",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.coefs));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.op = (global_variable *) Core._global_variable->instantiate("op",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.op));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.GEQ = (global_variable *) Core._global_variable->instantiate("GEQ",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.EQ = (global_variable *) Core._global_variable->instantiate("EQ",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.NEQ = (global_variable *) Core._global_variable->instantiate("NEQ",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.improvedLowerBound = (global_variable *) Core._global_variable->instantiate("improvedLowerBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.improvedLowerBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.improvedUpperBound = (global_variable *) Core._global_variable->instantiate("improvedUpperBound",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.improvedUpperBound));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.filter = (global_variable *) Core._global_variable->instantiate("filter",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.filter));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isPossible = (global_variable *) Core._global_variable->instantiate("isPossible",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isPossible));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbPossible = (global_variable *) Core._global_variable->instantiate("nbPossible",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbPossible));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.isSure = (global_variable *) Core._global_variable->instantiate("isSure",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.isSure));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbSure = (global_variable *) Core._global_variable->instantiate("nbSure",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbSure));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.constrainOnInfNumber = (global_variable *) Core._global_variable->instantiate("constrainOnInfNumber",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.constrainOnInfNumber));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.constrainOnSupNumber = (global_variable *) Core._global_variable->instantiate("constrainOnSupNumber",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.constrainOnSupNumber));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.update = (global_variable *) Core._global_variable->instantiate("update",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.update));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.abstractIncInf = (global_variable *) Core._global_variable->instantiate("abstractIncInf",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.abstractIncInf));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.abstractDecSup = (global_variable *) Core._global_variable->instantiate("abstractDecSup",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.abstractDecSup));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.abstractRemoveVal = (global_variable *) Core._global_variable->instantiate("abstractRemoveVal",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.abstractRemoveVal));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.target = (global_variable *) Core._global_variable->instantiate("target",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.target));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getNth = (global_variable *) Core._global_variable->instantiate("getNth",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getNth));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.lval = (global_variable *) Core._global_variable->instantiate("lval",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.lval));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.lvalues = (global_variable *) Core._global_variable->instantiate("lvalues",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.lvalues));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.const1 = (global_variable *) Core._global_variable->instantiate("const1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.const1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.const2 = (global_variable *) Core._global_variable->instantiate("const2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.const2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbConst = (global_variable *) Core._global_variable->instantiate("nbConst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbConst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.lconst = (global_variable *) Core._global_variable->instantiate("lconst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.lconst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.loppositeConst = (global_variable *) Core._global_variable->instantiate("loppositeConst",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.loppositeConst));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.oppositeConst1 = (global_variable *) Core._global_variable->instantiate("oppositeConst1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.oppositeConst1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.oppositeConst2 = (global_variable *) Core._global_variable->instantiate("oppositeConst2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.oppositeConst2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.indicesInOppConst1 = (global_variable *) Core._global_variable->instantiate("indicesInOppConst1",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.indicesInOppConst1));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.indicesInOppConst2 = (global_variable *) Core._global_variable->instantiate("indicesInOppConst2",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.indicesInOppConst2));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.additionalVars = (global_variable *) Core._global_variable->instantiate("additionalVars",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.additionalVars));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.statusBitVectorList = (global_variable *) Core._global_variable->instantiate("statusBitVectorList",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.statusBitVectorList));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.statusBitVector = (global_variable *) Core._global_variable->instantiate("statusBitVector",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.statusBitVector));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.closeBoolConstraint = (global_variable *) Core._global_variable->instantiate("closeBoolConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.closeBoolConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.closeBoolConstraintWCounterOpp = (global_variable *) Core._global_variable->instantiate("closeBoolConstraintWCounterOpp",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.closeBoolConstraintWCounterOpp));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.knownStatus = (global_variable *) Core._global_variable->instantiate("knownStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.knownStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.knownTargetStatus = (global_variable *) Core._global_variable->instantiate("knownTargetStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.knownTargetStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getTargetStatus = (global_variable *) Core._global_variable->instantiate("getTargetStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getTargetStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.setStatus = (global_variable *) Core._global_variable->instantiate("setStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.setStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.setTargetStatus = (global_variable *) Core._global_variable->instantiate("setTargetStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.setTargetStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getStatus = (global_variable *) Core._global_variable->instantiate("getStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbTrueStatus = (global_variable *) Core._global_variable->instantiate("nbTrueStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbTrueStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbFalseStatus = (global_variable *) Core._global_variable->instantiate("nbFalseStatus",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.nbFalseStatus));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.getCardVar = (global_variable *) Core._global_variable->instantiate("getCardVar",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.getCardVar));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.minValue = (global_variable *) Core._global_variable->instantiate("minValue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(choco.minValue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.maxValue = (global_variable *) Core._global_variable->instantiate("maxValue",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.maxValue));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbLeftVertices = (global_variable *) Core._global_variable->instantiate("nbLeftVertices",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.nbLeftVertices));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbRightVertices = (global_variable *) Core._global_variable->instantiate("nbRightVertices",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.nbRightVertices));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.closeAssignmentConstraint = (global_variable *) Core._global_variable->instantiate("closeAssignmentConstraint",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.closeAssignmentConstraint));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.deleteEdgeAndPublish = (global_variable *) Core._global_variable->instantiate("deleteEdgeAndPublish",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.deleteEdgeAndPublish));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.deleteMatch = (global_variable *) Core._global_variable->instantiate("deleteMatch",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.deleteMatch));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.component = (global_variable *) Core._global_variable->instantiate("component",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.component));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.componentOrder = (global_variable *) Core._global_variable->instantiate("componentOrder",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.componentOrder));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.minFlow = (global_variable *) Core._global_variable->instantiate("minFlow",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.minFlow));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.maxFlow = (global_variable *) Core._global_variable->instantiate("maxFlow",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.maxFlow));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.flow = (global_variable *) Core._global_variable->instantiate("flow",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.flow));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.matchingSize = (global_variable *) Core._global_variable->instantiate("matchingSize",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.matchingSize));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.mustGrowFlowFromSource = (global_variable *) Core._global_variable->instantiate("mustGrowFlowFromSource",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.mustGrowFlowFromSource));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.nbVertices = (global_variable *) Core._global_variable->instantiate("nbVertices",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.nbVertices));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.findAlternatingPath = (global_variable *) Core._global_variable->instantiate("findAlternatingPath",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.findAlternatingPath));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.match = (global_variable *) Core._global_variable->instantiate("match",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.match));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.inverseMatch = (global_variable *) Core._global_variable->instantiate("inverseMatch",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.inverseMatch));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.augment = (global_variable *) Core._global_variable->instantiate("augment",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.augment));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.checkFlow = (global_variable *) Core._global_variable->instantiate("checkFlow",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.checkFlow));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.firstPassDFS = (global_variable *) Core._global_variable->instantiate("firstPassDFS",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.firstPassDFS));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.secondPassDFS = (global_variable *) Core._global_variable->instantiate("secondPassDFS",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.secondPassDFS));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.mayMatch = (global_variable *) Core._global_variable->instantiate("mayMatch",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,_oid_(ice.mayMatch));
    close_global_variable(_CL_obj);
    } 
  
  palm.palmVersion->addMethod(list::domain(1,Kernel._void),Kernel._string,
  	0,_function_(palm_palmVersion_void,"palm_palmVersion_void"));
  
  palm.palmReleaseDate->addMethod(list::domain(1,Kernel._void),Kernel._string,
  	0,_function_(palm_palmReleaseDate_void,"palm_palmReleaseDate_void"));
  
  palm.palmInfo->addMethod(list::domain(1,Kernel._void),Kernel._string,
  	0,_function_(palm_palmInfo_void,"palm_palmInfo_void"));
  
  palm.showPalmLicense->addMethod(list::domain(1,Kernel._void),Kernel._any,
  	NEW_ALLOC,_function_(palm_showPalmLicense_void,"palm_showPalmLicense_void"));
  
  palm_showPalmLicense_void();
  
  (palm._PalmTools = ClaireClass::make("PalmTools",choco._Ephemeral,palm.it));
  
  (palm._PalmIntVar = ClaireClass::make("PalmIntVar",choco._IntVar,palm.it));
  
  (palm._PalmSolver = ClaireClass::make("PalmSolver",choco._Solver,palm.it));
  
  (palm._PalmUserFriendlyBox = ClaireClass::make("PalmUserFriendlyBox",palm._PalmTools,palm.it));
  
  { (palm._PalmProblem = ClaireClass::make("PalmProblem",choco._Problem,palm.it));
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.maxRelaxLvl,maxRelaxLvl,Kernel._integer,0);
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.palmSolver,palmSolver,palm._PalmSolver,CNULL);
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.rootUFboxes,rootUFboxes,palm._PalmUserFriendlyBox,CNULL);
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.relaxedUFboxes,relaxedUFboxes,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmUserFriendlyBox))))),CNULL);
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.allUFboxes,allUFboxes,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmUserFriendlyBox))))),CNULL);
    CL_ADD_SLOT(palm._PalmProblem,PalmProblem,palm.userRepresentation,userRepresentation,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmUserFriendlyBox))))),CNULL);
    } 
  
  { (palm._PalmEngine = ClaireClass::make("PalmEngine",choco._ChocEngine,palm.it));
    CL_ADD_SLOT(palm._PalmEngine,PalmEngine,palm.toBeAwakenConstraints,toBeAwakenConstraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmEngine,PalmEngine,palm.boundRestEvtQueue,boundRestEvtQueue,OBJECT(ClaireType,palm.BoundEventQueue->value),CNULL);
    CL_ADD_SLOT(palm._PalmEngine,PalmEngine,palm.removalRestEvtQueue,removalRestEvtQueue,OBJECT(ClaireType,palm.RemovalEventQueue->value),CNULL);
    CL_ADD_SLOT(palm._PalmEngine,PalmEngine,palm.dummyVariable,dummyVariable,palm._PalmIntVar,CNULL);
    CL_ADD_SLOT(palm._PalmEngine,PalmEngine,palm.contradictory,contradictory,Kernel._boolean,Kernel.cfalse);
    } 
  
  (palm._DecInf = ClaireClass::make("DecInf",choco._BoundUpdate,palm.it));
  
  (palm._IncSup = ClaireClass::make("IncSup",choco._BoundUpdate,palm.it));
  
  (palm._ValueRestorations = ClaireClass::make("ValueRestorations",choco._ValueRemovals,palm.it));
  
  { (palm._Explanation = ClaireClass::make("Explanation",Kernel._collection,palm.it));
    CL_ADD_SLOT(palm._Explanation,Explanation,palm.explanation,explanation,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    } 
  
  ephemeral_class(palm._Explanation);
  
  (palm._PalmContradictionExplanation = ClaireClass::make("PalmContradictionExplanation",palm._Explanation,palm.it));
  
  { (palm._PalmBoundExplanation = ClaireClass::make("PalmBoundExplanation",palm._Explanation,palm.it));
    CL_ADD_SLOT(palm._PalmBoundExplanation,PalmBoundExplanation,palm.previousValue,previousValue,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmBoundExplanation,PalmBoundExplanation,palm.variable,variable,palm._PalmIntVar,CNULL);
    } 
  
  (palm._PalmIncInfExplanation = ClaireClass::make("PalmIncInfExplanation",palm._PalmBoundExplanation,palm.it));
  
  (palm._PalmDecSupExplanation = ClaireClass::make("PalmDecSupExplanation",palm._PalmBoundExplanation,palm.it));
  
  { (palm._PalmRemovalExplanation = ClaireClass::make("PalmRemovalExplanation",palm._Explanation,palm.it));
    CL_ADD_SLOT(palm._PalmRemovalExplanation,PalmRemovalExplanation,Kernel.value,value,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmRemovalExplanation,PalmRemovalExplanation,palm.variable,variable,palm._PalmIntVar,CNULL);
    } 
  
  (palm._PalmIntDomain = ClaireClass::make("PalmIntDomain",choco._AbstractIntDomain,palm.it));
  
  { (palm._PalmIntVar = ClaireClass::make("PalmIntVar",choco._IntVar,palm.it));
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,choco.bucket,bucket,palm._PalmIntDomain,CNULL);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.originalInf,originalInf,Kernel._integer,-100);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.originalSup,originalSup,Kernel._integer,100);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.explanationOnInf,explanationOnInf,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmIncInfExplanation))))),_oid_(list::empty(palm._PalmIncInfExplanation)));
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.explanationOnSup,explanationOnSup,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmDecSupExplanation))))),_oid_(list::empty(palm._PalmDecSupExplanation)));
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.enumerationConstraints,enumerationConstraints,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.negEnumerationConstraints,negEnumerationConstraints,nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.restInfEvt,restInfEvt,palm._DecInf,CNULL);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.restSupEvt,restSupEvt,palm._IncSup,CNULL);
    CL_ADD_SLOT(palm._PalmIntVar,PalmIntVar,palm.restValEvt,restValEvt,palm._ValueRestorations,CNULL);
    } 
  
  { (palm._PalmIntDomain = ClaireClass::make("PalmIntDomain",choco._AbstractIntDomain,palm.it));
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,choco.offset,offset,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,choco.bucketSize,bucketSize,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.nbElements,nbElements,Kernel._integer,0);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.booleanVector,booleanVector,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.precVector,precVector,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.succVector,succVector,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.firstSuccPres,firstSuccPres,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.firstPrecPres,firstPrecPres,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.firstSuccAbs,firstSuccAbs,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.firstPrecAbs,firstPrecAbs,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.needInfComputation,needInfComputation,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.needSupComputation,needSupComputation,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.explanationOnVal,explanationOnVal,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,GC_OID(_oid_(U_type(palm._PalmRemovalExplanation,set::alloc(Kernel.emptySet,1,CNULL)))))))),CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.instantiationConstraints,instantiationConstraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmIntDomain,PalmIntDomain,palm.negInstantiationConstraints,negInstantiationConstraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    } 
  
  { (palm.raisePalmContradiction = property::make("raisePalmContradiction",1,palm.it,Kernel._any,0));
    ;} 
  
  (palm._PalmContradiction = ClaireClass::make("PalmContradiction",Kernel._exception,palm.it));
  
  { (palm._PalmSolverTools = ClaireClass::make("PalmSolverTools",palm._PalmTools,palm.it));
    CL_ADD_SLOT(palm._PalmSolverTools,PalmSolverTools,palm.manager,manager,palm._PalmSolver,CNULL);
    } 
  
  { (palm._PalmState = ClaireClass::make("PalmState",palm._PalmSolverTools,palm.it));
    CL_ADD_SLOT(palm._PalmState,PalmState,palm.path,path,palm._Explanation,CNULL);
    } 
  
  (palm._PalmLearn = ClaireClass::make("PalmLearn",palm._PalmSolverTools,palm.it));
  
  { (palm._PathRepairLearn = ClaireClass::make("PathRepairLearn",palm._PalmLearn,palm.it));
    CL_ADD_SLOT(palm._PathRepairLearn,PathRepairLearn,palm.maxMoves,maxMoves,Kernel._integer,1500);
    CL_ADD_SLOT(palm._PathRepairLearn,PathRepairLearn,palm.explanations,explanations,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._Explanation))))),_oid_(list::empty(palm._Explanation)));
    CL_ADD_SLOT(palm._PathRepairLearn,PathRepairLearn,choco.maxSize,maxSize,Kernel._integer,7);
    CL_ADD_SLOT(palm._PathRepairLearn,PathRepairLearn,palm.lastExplanation,lastExplanation,Kernel._integer,0);
    CL_ADD_SLOT(palm._PathRepairLearn,PathRepairLearn,palm.isFull,isFull,Kernel._boolean,Kernel.cfalse);
    } 
  
  (palm._PalmExtend = ClaireClass::make("PalmExtend",palm._PalmSolverTools,palm.it));
  
  (palm._PalmBranching = ClaireClass::make("PalmBranching",choco._Ephemeral,palm.it));
  
  { (palm._PalmBranching = ClaireClass::make("PalmBranching",choco._Ephemeral,palm.it));
    CL_ADD_SLOT(palm._PalmBranching,PalmBranching,palm.extender,extender,palm._PalmExtend,CNULL);
    CL_ADD_SLOT(palm._PalmBranching,PalmBranching,palm.nextBranching,nextBranching,palm._PalmBranching,CNULL);
    } 
  
  { (palm._PalmExtend = ClaireClass::make("PalmExtend",palm._PalmSolverTools,palm.it));
    CL_ADD_SLOT(palm._PalmExtend,PalmExtend,palm.branching,branching,palm._PalmBranching,CNULL);
    } 
  
  (palm._PalmUnsureExtend = ClaireClass::make("PalmUnsureExtend",palm._PalmExtend,palm.it));
  
  (palm._PalmRepair = ClaireClass::make("PalmRepair",palm._PalmSolverTools,palm.it));
  
  (palm._PalmUnsureRepair = ClaireClass::make("PalmUnsureRepair",palm._PalmRepair,palm.it));
  
  { (palm._PalmSolution = ClaireClass::make("PalmSolution",choco._Solution,palm.it));
    CL_ADD_SLOT(palm._PalmSolution,PalmSolution,palm.feasible,feasible,Kernel._boolean,CNULL);
    CL_ADD_SLOT(palm._PalmSolution,PalmSolution,palm.lstat,lstat,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  { (palm._PalmSolver = ClaireClass::make("PalmSolver",choco._Solver,palm.it));
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.statistics,statistics,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),_oid_(list::empty(Kernel._integer)));
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.finished,finished,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.feasible,feasible,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.state,state,palm._PalmState,CNULL);
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.learning,learning,palm._PalmLearn,CNULL);
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.extending,extending,palm._PalmExtend,CNULL);
    CL_ADD_SLOT(palm._PalmSolver,PalmSolver,palm.repairing,repairing,palm._PalmRepair,CNULL);
    } 
  
  { (palm._PalmBranchAndBound = ClaireClass::make("PalmBranchAndBound",palm._PalmSolver,palm.it));
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.maximizing,maximizing,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.objective,objective,OBJECT(ClaireType,palm.IntVar->value),CNULL);
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.lowerBound,lowerBound,Kernel._integer,-99999999);
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.upperBound,upperBound,Kernel._integer,99999999);
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.optimum,optimum,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmBranchAndBound,PalmBranchAndBound,palm.dynamicCuts,dynamicCuts,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    } 
  
  { (palm._PalmUserFriendlyBox = ClaireClass::make("PalmUserFriendlyBox",palm._PalmTools,palm.it));
    CL_ADD_SLOT(palm._PalmUserFriendlyBox,PalmUserFriendlyBox,palm.shortName,shortName,Kernel._string,CNULL);
    CL_ADD_SLOT(palm._PalmUserFriendlyBox,PalmUserFriendlyBox,Kernel.name,name,Kernel._string,CNULL);
    CL_ADD_SLOT(palm._PalmUserFriendlyBox,PalmUserFriendlyBox,choco.constraints,constraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmUserFriendlyBox,PalmUserFriendlyBox,palm.fatherBox,fatherBox,palm._PalmUserFriendlyBox,CNULL);
    CL_ADD_SLOT(palm._PalmUserFriendlyBox,PalmUserFriendlyBox,palm.childrenBoxes,childrenBoxes,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmUserFriendlyBox))))),CNULL);
    } 
  
  { global_variable * _CL_obj = (palm.PalmVIEW = (global_variable *) Core._global_variable->instantiate("PalmVIEW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PalmTALK = (global_variable *) Core._global_variable->instantiate("PalmTALK",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PalmDEBUG = (global_variable *) Core._global_variable->instantiate("PalmDEBUG",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PalmOPTSHOW = (global_variable *) Core._global_variable->instantiate("PalmOPTSHOW",claire.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,0);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PALM_TIME_STAMP = (global_variable *) Core._global_variable->instantiate("PALM_TIME_STAMP",palm.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,0);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PALM_MAXSIZE = (global_variable *) Core._global_variable->instantiate("PALM_MAXSIZE",palm.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,10000);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PALM_BITALLONE = (global_variable *) Core._global_variable->instantiate("PALM_BITALLONE",palm.it));
    (_CL_obj->range = Kernel._integer);
    STOREI(_CL_obj->value,536870911);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.PALM_USER_FRIENDLY = (global_variable *) Core._global_variable->instantiate("PALM_USER_FRIENDLY",palm.it));
    (_CL_obj->range = Kernel._boolean);
    STOREI(_CL_obj->value,Kernel.cfalse);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.UNKNOWN_ABS_CT = (global_variable *) Core._global_variable->instantiate("UNKNOWN_ABS_CT",palm.it));
    (_CL_obj->range = choco._AbstractConstraint);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      GC_OID(_oid_(new_class1(choco._AbstractConstraint))));
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.UFcurrentBox = (global_variable *) Core._global_variable->instantiate("UFcurrentBox",palm.it));
    (_CL_obj->range = palm._PalmUserFriendlyBox);
    { OID  g0840UU;
      { PalmUserFriendlyBox * _CL_obj = ((PalmUserFriendlyBox *) GC_OBJECT(PalmUserFriendlyBox,new_object_class(palm._PalmUserFriendlyBox)));
        g0840UU = _oid_(_CL_obj);
        } 
      update_property(Kernel.value,
        _CL_obj,
        3,
        Kernel._any,
        g0840UU);
      } 
    close_global_variable(_CL_obj);
    } 
  
  palm.not_I->addMethod(list::domain(1,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(palm_not_I_integer,"palm_not!_integer"));
  
  choco.sum->addMethod(list::domain(1,nth_class1(Kernel._list,Kernel._integer)),Kernel._integer,
  	0,_function_(claire_sum_list,"claire_sum_list"));
  
  palm.firstCode29bits->addMethod(list::domain(2,palm._PalmIntDomain,Kernel._integer),Kernel._integer,
  	RETURN_ARG,_function_(palm_firstCode29bits_PalmIntDomain,"palm_firstCode29bits_PalmIntDomain"));
  
  palm.secondCode29bits->addMethod(list::domain(2,palm._PalmIntDomain,Kernel._integer),Kernel._integer,
  	0,_function_(palm_secondCode29bits_PalmIntDomain,"palm_secondCode29bits_PalmIntDomain"));
  
  palm.decode29bits->addMethod(list::domain(3,palm._PalmIntDomain,Kernel._integer,_dot_dot_integer(1,29)),Kernel._integer,
  	RETURN_ARG,_function_(palm_decode29bits_PalmIntDomain,"palm_decode29bits_PalmIntDomain"));
  
  palm.makePalmIntDomain->addMethod(list::domain(2,Kernel._integer,Kernel._integer),palm._PalmIntDomain,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmIntDomain_integer,"palm_makePalmIntDomain_integer"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmIntDomain),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmIntDomain_palm,"claire_self_print_PalmIntDomain_palm"));
  
  choco.domainSequence->addMethod(list::domain(1,palm._PalmIntDomain),param_I_class(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSequence_PalmIntDomain_palm,"choco_domainSequence_PalmIntDomain_palm"));
  
  choco.domainSet->addMethod(list::domain(1,palm._PalmIntDomain),param_I_class(Kernel._set,Kernel._integer),
  	NEW_ALLOC,_function_(choco_domainSet_PalmIntDomain_palm,"choco_domainSet_PalmIntDomain_palm"));
  
  ;
  palm.removedlist_I->addMethod(list::domain(1,palm._PalmIntDomain),nth_class1(Kernel._list,Kernel._integer),
  	NEW_ALLOC,_function_(palm_removedlist_I_PalmIntDomain,"palm_removedlist!_PalmIntDomain"));
  
  choco.getDomainCard->addMethod(list::domain(1,palm._PalmIntDomain),Kernel._integer,
  	0,_function_(choco_getDomainCard_PalmIntDomain_palm,"choco_getDomainCard_PalmIntDomain_palm"));
  
  choco.containsValInDomain->addMethod(list::domain(2,palm._PalmIntDomain,Kernel._integer),Kernel._boolean,
  	0,_function_(choco_containsValInDomain_PalmIntDomain_palm,"choco_containsValInDomain_PalmIntDomain_palm"));
  
  palm.firstElement->addMethod(list::domain(1,palm._PalmIntDomain),Kernel._integer,
  	RETURN_ARG,_function_(palm_firstElement_PalmIntDomain,"palm_firstElement_PalmIntDomain"));
  
  choco.getInf->addMethod(list::domain(1,palm._PalmIntDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getInf_PalmIntDomain_palm,"choco_getInf_PalmIntDomain_palm"));
  
  choco.getSup->addMethod(list::domain(1,palm._PalmIntDomain),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getSup_PalmIntDomain_palm,"choco_getSup_PalmIntDomain_palm"));
  
  palm.addDomainVal->addMethod(list::domain(2,palm._PalmIntDomain,Kernel._integer),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addDomainVal_PalmIntDomain,"palm_addDomainVal_PalmIntDomain"));
  
  choco.removeDomainVal->addMethod(list::domain(2,palm._PalmIntDomain,Kernel._integer),Kernel._boolean,
  	BAG_UPDATE+SLOT_UPDATE,_function_(choco_removeDomainVal_PalmIntDomain_palm,"choco_removeDomainVal_PalmIntDomain_palm"));
  
  { global_variable * _CL_obj = (palm.INF = (global_variable *) Core._global_variable->instantiate("INF",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.SUP = (global_variable *) Core._global_variable->instantiate("SUP",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.VAL = (global_variable *) Core._global_variable->instantiate("VAL",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.DOM = (global_variable *) Core._global_variable->instantiate("DOM",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,4);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.SELECT = (global_variable *) Core._global_variable->instantiate("SELECT",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(set::alloc(Kernel.emptySet,4,1,
        2,
        3,
        4)));
    close_global_variable(_CL_obj);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmIntVar),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmIntVar_palm,"claire_self_print_PalmIntVar_palm"));
  
  choco.getInf->addMethod(list::domain(1,palm._PalmIntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getInf_PalmIntVar_palm,"choco_getInf_PalmIntVar_palm"));
  
  choco.getSup->addMethod(list::domain(1,palm._PalmIntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_getSup_PalmIntVar_palm,"choco_getSup_PalmIntVar_palm"));
  
  palm.self_explain->addMethod(list::domain(3,palm._PalmIntVar,OBJECT(ClaireType,palm.SELECT->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_self_explain_PalmIntVar1,"palm_self_explain_PalmIntVar1"));
  
  palm.self_explain->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    palm._Explanation),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_self_explain_PalmIntVar2,"palm_self_explain_PalmIntVar2"));
  
  palm.firstValue->addMethod(list::domain(1,palm._PalmIntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_firstValue_PalmIntVar,"palm_firstValue_PalmIntVar"));
  
  palm.updateDataStructures->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructures_PalmIntVar,"palm_updateDataStructures_PalmIntVar"));
  
  palm.updateDataStructuresOnVariable->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	0,_function_(palm_updateDataStructuresOnVariable_PalmIntVar_palm,"palm_updateDataStructuresOnVariable_PalmIntVar_palm"));
  
  abstract_property(palm.updateDataStructuresOnVariable);
  
  palm.updateDataStructuresOnConstraints->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraints_PalmIntVar,"palm_updateDataStructuresOnConstraints_PalmIntVar"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,OBJECT(ClaireType,palm.AbstractConstraint->value),
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	0,_function_(palm_updateDataStructuresOnConstraint_AbstractConstraint_palm,"palm_updateDataStructuresOnConstraint_AbstractConstraint_palm"));
  
  abstract_property(palm.updateDataStructuresOnConstraint);
  
  palm.updateDataStructuresOnRestore->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestore_PalmIntVar,"palm_updateDataStructuresOnRestore_PalmIntVar"));
  
  palm.updateDataStructuresOnRestoreVariable->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	0,_function_(palm_updateDataStructuresOnRestoreVariable_PalmIntVar_palm,"palm_updateDataStructuresOnRestoreVariable_PalmIntVar_palm"));
  
  abstract_property(palm.updateDataStructuresOnRestoreVariable);
  
  palm.updateDataStructuresOnRestoreConstraints->addMethod(list::domain(4,palm._PalmIntVar,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraints_PalmIntVar,"palm_updateDataStructuresOnRestoreConstraints_PalmIntVar"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,OBJECT(ClaireType,palm.AbstractConstraint->value),
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	0,_function_(palm_updateDataStructuresOnRestoreConstraint_AbstractConstraint_palm,"palm_updateDataStructuresOnRestoreConstraint_AbstractConstraint_palm"));
  
  abstract_property(palm.updateDataStructuresOnRestoreConstraint);
  
  palm.restoreInf->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreInf_PalmIntVar,"palm_restoreInf_PalmIntVar"));
  
  palm.restoreSup->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreSup_PalmIntVar,"palm_restoreSup_PalmIntVar"));
  
  palm.postRestoreInf->addMethod(list::domain(2,palm._PalmEngine,palm._PalmIntVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRestoreInf_PalmEngine,"palm_postRestoreInf_PalmEngine"));
  
  palm.postRestoreSup->addMethod(list::domain(2,palm._PalmEngine,palm._PalmIntVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRestoreSup_PalmEngine,"palm_postRestoreSup_PalmEngine"));
  
  palm.postRestoreEvent->addMethod(list::domain(2,palm._PalmEngine,OBJECT(ClaireType,palm.BoundUpdate->value)),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRestoreEvent_PalmEngine1,"palm_postRestoreEvent_PalmEngine1"));
  
  palm.restoreVariableExplanation->addMethod(list::domain(1,palm._DecInf),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreVariableExplanation_DecInf,"palm_restoreVariableExplanation_DecInf"));
  
  palm.restoreVariableExplanation->addMethod(list::domain(1,palm._IncSup),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreVariableExplanation_IncSup,"palm_restoreVariableExplanation_IncSup"));
  
  palm.restoreVal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreVal_PalmIntVar,"palm_restoreVal_PalmIntVar"));
  
  palm.postRestoreVal->addMethod(list::domain(3,palm._PalmEngine,palm._PalmIntVar,Kernel._integer),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRestoreVal_PalmEngine,"palm_postRestoreVal_PalmEngine"));
  
  palm.postRestoreEvent->addMethod(list::domain(3,palm._PalmEngine,palm._ValueRestorations,Kernel._integer),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRestoreEvent_PalmEngine2,"palm_postRestoreEvent_PalmEngine2"));
  
  choco.updateInf->addMethod(list::domain(4,palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateInf_PalmIntVar1_palm,"choco_updateInf_PalmIntVar1_palm"));
  
  choco.updateInf->addMethod(list::domain(3,palm._PalmIntVar,Kernel._integer,palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateInf_PalmIntVar2_palm,"choco_updateInf_PalmIntVar2_palm"));
  
  choco.updateSup->addMethod(list::domain(4,palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateSup_PalmIntVar1_palm,"choco_updateSup_PalmIntVar1_palm"));
  
  choco.updateSup->addMethod(list::domain(3,palm._PalmIntVar,Kernel._integer,palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_updateSup_PalmIntVar2_palm,"choco_updateSup_PalmIntVar2_palm"));
  
  choco.removeVal->addMethod(list::domain(4,palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_removeVal_PalmIntVar1_palm,"choco_removeVal_PalmIntVar1_palm"));
  
  choco.removeVal->addMethod(list::domain(3,palm._PalmIntVar,Kernel._integer,palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_removeVal_PalmIntVar2_palm,"choco_removeVal_PalmIntVar2_palm"));
  
  palm.postRemoveVal->addMethod(list::domain(4,palm._PalmEngine,
    OBJECT(ClaireType,palm.IntVar->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postRemoveVal_PalmEngine,"palm_postRemoveVal_PalmEngine"));
  
  choco.instantiate->addMethod(list::domain(4,palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    palm._Explanation),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_instantiate_PalmIntVar,"choco_instantiate_PalmIntVar"));
  
  { (palm._PalmControlConstraint = ClaireClass::make("PalmControlConstraint",choco._Ephemeral,palm.it));
    CL_ADD_SLOT(palm._PalmControlConstraint,PalmControlConstraint,palm.constraint,constraint,OBJECT(ClaireType,palm.AbstractConstraint->value),CNULL);
    CL_ADD_SLOT(palm._PalmControlConstraint,PalmControlConstraint,palm.index,index,Kernel._integer,CNULL);
    } 
  
  { (palm._PalmInfoConstraint = ClaireClass::make("PalmInfoConstraint",palm._PalmTools,palm.it));
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.timeStamp,timeStamp,Kernel._integer,0);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.weight,weight,Kernel._integer,99999999);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.everConnected,everConnected,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.indirect,indirect,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.addingExplanation,addingExplanation,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.dependingConstraints,dependingConstraints,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,palm.AbstractConstraint->value)))),CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.dependencyNet,dependencyNet,nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._Explanation))))),CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.searchInfo,searchInfo,Kernel._any,CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.ufBox,ufBox,palm._PalmUserFriendlyBox,CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.controllingConstraints,controllingConstraints,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmControlConstraint))))),CNULL);
    CL_ADD_SLOT(palm._PalmInfoConstraint,PalmInfoConstraint,palm.info,info,Kernel._any,CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmControlConstraint),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmControlConstraint_palm,"claire_self_print_PalmControlConstraint_palm"));
  
  palm.addDependency->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addDependency_AbstractConstraint,"palm_addDependency_AbstractConstraint"));
  
  palm.removeDependency->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_removeDependency_AbstractConstraint,"palm_removeDependency_AbstractConstraint"));
  
  palm.undo->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_undo_AbstractConstraint,"palm_undo_AbstractConstraint"));
  
  palm.indirect_ask->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	0,_function_(palm_indirect_ask_AbstractConstraint,"palm_indirect?_AbstractConstraint"));
  
  palm.setIndirect->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setIndirect_AbstractConstraint,"palm_setIndirect_AbstractConstraint"));
  
  palm.setDirect->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setDirect_AbstractConstraint,"palm_setDirect_AbstractConstraint"));
  
  palm.setIndirectDependance->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_setIndirectDependance_AbstractConstraint,"palm_setIndirectDependance_AbstractConstraint"));
  
  palm.removeIndirectDependance->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_removeIndirectDependance_AbstractConstraint,"palm_removeIndirectDependance_AbstractConstraint"));
  
  palm.informControllersOfDeactivation->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_informControllersOfDeactivation_AbstractConstraint,"palm_informControllersOfDeactivation_AbstractConstraint"));
  
  palm.weight->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._integer,
  	NEW_ALLOC,_function_(palm_weight_AbstractConstraint,"palm_weight_AbstractConstraint"));
  
  palm.self_explain->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_self_explain_AbstractConstraint,"palm_self_explain_AbstractConstraint"));
  
  palm.activate->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_activate_AbstractConstraint,"palm_activate_AbstractConstraint"));
  
  palm.deactivate->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_deactivate_AbstractConstraint,"palm_deactivate_AbstractConstraint"));
  
  palm.initHook->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_AbstractConstraint,"palm_initHook_AbstractConstraint"));
  
  palm.addControl->addMethod(list::domain(3,OBJECT(ClaireType,palm.AbstractConstraint->value),OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addControl_AbstractConstraint,"palm_addControl_AbstractConstraint"));
  
  choco.connectIntVarEvents->addMethod(list::domain(5,palm._PalmIntVar,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectIntVarEvents_PalmIntVar_palm,"choco_connectIntVarEvents_PalmIntVar_palm"));
  
  choco.disconnectIntVarEvents->addMethod(list::domain(6,palm._PalmIntVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_disconnectIntVarEvents_PalmIntVar_palm,"choco_disconnectIntVarEvents_PalmIntVar_palm"));
  
  choco.reconnectIntVarEvents->addMethod(list::domain(6,palm._PalmIntVar,
    Kernel._integer,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean,
    Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reconnectIntVarEvents_PalmIntVar_palm,"choco_reconnectIntVarEvents_PalmIntVar_palm"));
  
  choco.connectHook->addMethod(list::domain(2,palm._PalmInfoConstraint,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_connectHook_PalmInfoConstraint_palm,"choco_connectHook_PalmInfoConstraint_palm"));
  
  choco.disconnectHook->addMethod(list::domain(2,palm._PalmInfoConstraint,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_disconnectHook_PalmInfoConstraint_palm,"choco_disconnectHook_PalmInfoConstraint_palm"));
  
  choco.reconnectHook->addMethod(list::domain(2,palm._PalmInfoConstraint,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_reconnectHook_PalmInfoConstraint_palm,"choco_reconnectHook_PalmInfoConstraint_palm"));
  
  { (palm.whyIsFalse = property::make("whyIsFalse",1,palm.it,choco._AbstractConstraint,58));
    (palm.whyIsFalse->range = nth_class1(Kernel._set,OBJECT(ClaireType,palm.AbstractConstraint->value)));
    ;} 
  
  { (palm.whyIsTrue = property::make("whyIsTrue",1,palm.it,choco._AbstractConstraint,59));
    (palm.whyIsTrue->range = nth_class1(Kernel._set,OBJECT(ClaireType,palm.AbstractConstraint->value)));
    ;} 
  
  { (palm.awakeOnRestoreInf = property::make("awakeOnRestoreInf",1,palm.it,choco._AbstractConstraint,60));
    (palm.awakeOnRestoreInf->range = Kernel._void);
    ;} 
  
  { (palm.awakeOnRestoreSup = property::make("awakeOnRestoreSup",1,palm.it,choco._AbstractConstraint,61));
    (palm.awakeOnRestoreSup->range = Kernel._void);
    ;} 
  
  { (palm.awakeOnRestoreVal = property::make("awakeOnRestoreVal",1,palm.it,choco._AbstractConstraint,62));
    (palm.awakeOnRestoreVal->range = Kernel._void);
    ;} 
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(palm_awakeOnRestoreInf_AbstractConstraint,"palm_awakeOnRestoreInf_AbstractConstraint"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(palm_awakeOnRestoreSup_AbstractConstraint,"palm_awakeOnRestoreSup_AbstractConstraint"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(palm_awakeOnRestoreVal_AbstractConstraint,"palm_awakeOnRestoreVal_AbstractConstraint"));
  
  palm.whyIsTrue->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),nth_class1(Kernel._set,choco._AbstractConstraint),
  	SAFE_RESULT,_function_(palm_whyIsTrue_AbstractConstraint,"palm_whyIsTrue_AbstractConstraint"));
  
  palm.whyIsFalse->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),nth_class1(Kernel._set,choco._AbstractConstraint),
  	SAFE_RESULT,_function_(palm_whyIsFalse_AbstractConstraint,"palm_whyIsFalse_AbstractConstraint"));
  
  { ;} 
  
  (palm._PalmUnIntConstraint = ClaireClass::make("PalmUnIntConstraint",choco._UnIntConstraint,palm.it));
  
  (palm._PalmBinIntConstraint = ClaireClass::make("PalmBinIntConstraint",choco._BinIntConstraint,palm.it));
  
  (palm._PalmLargeIntConstraint = ClaireClass::make("PalmLargeIntConstraint",choco._LargeIntConstraint,palm.it));
  
  { (palm._PalmUnIntConstraint = ClaireClass::make("PalmUnIntConstraint",choco._UnIntConstraint,palm.it));
    CL_ADD_SLOT(palm._PalmUnIntConstraint,PalmUnIntConstraint,choco.v1,v1,palm._PalmIntVar,CNULL);
    } 
  
  palm.makePalmUnIntConstraint->addMethod(list::domain(3,Kernel._class,palm._PalmIntVar,Kernel._integer),palm._PalmUnIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmUnIntConstraint_class,"palm_makePalmUnIntConstraint_class"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmUnIntConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmUnIntConstraint,"palm_awakeOnRestoreInf_PalmUnIntConstraint"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmUnIntConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmUnIntConstraint,"palm_awakeOnRestoreSup_PalmUnIntConstraint"));
  
  { (palm._PalmBinIntConstraint = ClaireClass::make("PalmBinIntConstraint",choco._BinIntConstraint,palm.it));
    CL_ADD_SLOT(palm._PalmBinIntConstraint,PalmBinIntConstraint,choco.v1,v1,palm._PalmIntVar,CNULL);
    CL_ADD_SLOT(palm._PalmBinIntConstraint,PalmBinIntConstraint,choco.v2,v2,palm._PalmIntVar,CNULL);
    } 
  
  palm.makePalmBinIntConstraint->addMethod(list::domain(4,Kernel._class,
    palm._PalmIntVar,
    palm._PalmIntVar,
    Kernel._integer),palm._PalmBinIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmBinIntConstraint_class,"palm_makePalmBinIntConstraint_class"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmBinIntConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmBinIntConstraint,"palm_awakeOnRestoreInf_PalmBinIntConstraint"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmBinIntConstraint,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmBinIntConstraint,"palm_awakeOnRestoreSup_PalmBinIntConstraint"));
  
  (palm._PalmLargeIntConstraint = ClaireClass::make("PalmLargeIntConstraint",choco._LargeIntConstraint,palm.it));
  
  palm.makePalmLargeIntConstraint->addMethod(list::domain(3,Kernel._class,nth_class1(Kernel._list,palm._PalmIntVar),Kernel._integer),palm._PalmLargeIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmLargeIntConstraint_class,"palm_makePalmLargeIntConstraint_class"));
  
  (palm._PalmGreaterOrEqualxc = ClaireClass::make("PalmGreaterOrEqualxc",palm._PalmUnIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmGreaterOrEqualxc_palm,"claire_self_print_PalmGreaterOrEqualxc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmGreaterOrEqualxc,"choco_propagate_PalmGreaterOrEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmGreaterOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_PalmGreaterOrEqualxc,"choco_awakeOnInf_PalmGreaterOrEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmGreaterOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_PalmGreaterOrEqualxc,"choco_awakeOnSup_PalmGreaterOrEqualxc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmGreaterOrEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmGreaterOrEqualxc,"palm_awakeOnRestoreVal_PalmGreaterOrEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmGreaterOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmGreaterOrEqualxc,"choco_awakeOnInst_PalmGreaterOrEqualxc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmGreaterOrEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmGreaterOrEqualxc,"choco_awakeOnRem_PalmGreaterOrEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmGreaterOrEqualxc,"choco_askIfEntailed_PalmGreaterOrEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmGreaterOrEqualxc,"choco_testIfSatisfied_PalmGreaterOrEqualxc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxc,"choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmGreaterOrEqualxc,"palm_whyIsFalse_PalmGreaterOrEqualxc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmGreaterOrEqualxc,"palm_whyIsTrue_PalmGreaterOrEqualxc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmGreaterOrEqualxc,"palm_checkPalm_PalmGreaterOrEqualxc"));
  
  (palm._PalmLessOrEqualxc = ClaireClass::make("PalmLessOrEqualxc",palm._PalmUnIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLessOrEqualxc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmLessOrEqualxc_palm,"claire_self_print_PalmLessOrEqualxc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmLessOrEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmLessOrEqualxc,"choco_propagate_PalmLessOrEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmLessOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_PalmLessOrEqualxc,"choco_awakeOnInf_PalmLessOrEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmLessOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_PalmLessOrEqualxc,"choco_awakeOnSup_PalmLessOrEqualxc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmLessOrEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmLessOrEqualxc,"palm_awakeOnRestoreVal_PalmLessOrEqualxc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmLessOrEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmLessOrEqualxc,"palm_whyIsFalse_PalmLessOrEqualxc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmLessOrEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmLessOrEqualxc,"palm_whyIsTrue_PalmLessOrEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmLessOrEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmLessOrEqualxc,"choco_awakeOnInst_PalmLessOrEqualxc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmLessOrEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmLessOrEqualxc,"choco_awakeOnRem_PalmLessOrEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmLessOrEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmLessOrEqualxc,"choco_askIfEntailed_PalmLessOrEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmLessOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmLessOrEqualxc,"choco_testIfSatisfied_PalmLessOrEqualxc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmLessOrEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmLessOrEqualxc,"choco_testIfCompletelyInstantiated_PalmLessOrEqualxc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmLessOrEqualxc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmLessOrEqualxc,"palm_checkPalm_PalmLessOrEqualxc"));
  
  (palm._PalmNotEqualxc = ClaireClass::make("PalmNotEqualxc",palm._PalmUnIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmNotEqualxc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmNotEqualxc_palm,"claire_self_print_PalmNotEqualxc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmNotEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmNotEqualxc,"choco_propagate_PalmNotEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmNotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmNotEqualxc,"choco_awakeOnInf_PalmNotEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmNotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_PalmNotEqualxc,"choco_awakeOnSup_PalmNotEqualxc"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmNotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmNotEqualxc,"palm_awakeOnRestoreInf_PalmNotEqualxc"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmNotEqualxc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmNotEqualxc,"palm_awakeOnRestoreSup_PalmNotEqualxc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmNotEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmNotEqualxc,"palm_awakeOnRestoreVal_PalmNotEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmNotEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmNotEqualxc,"choco_awakeOnInst_PalmNotEqualxc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmNotEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmNotEqualxc,"choco_awakeOnRem_PalmNotEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmNotEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmNotEqualxc,"choco_askIfEntailed_PalmNotEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmNotEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmNotEqualxc,"choco_testIfSatisfied_PalmNotEqualxc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmNotEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmNotEqualxc,"choco_testIfCompletelyInstantiated_PalmNotEqualxc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmNotEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmNotEqualxc,"palm_whyIsTrue_PalmNotEqualxc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmNotEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmNotEqualxc,"palm_whyIsFalse_PalmNotEqualxc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmNotEqualxc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmNotEqualxc,"palm_checkPalm_PalmNotEqualxc"));
  
  (palm._PalmEqualxc = ClaireClass::make("PalmEqualxc",palm._PalmUnIntConstraint,palm.it));
  
  (palm._AssignmentConstraint = ClaireClass::make("AssignmentConstraint",palm._PalmEqualxc,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmEqualxc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmEqualxc_palm,"claire_self_print_PalmEqualxc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmEqualxc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmEqualxc,"choco_propagate_PalmEqualxc"));
  
  choco.propagate->addMethod(list::domain(1,palm._AssignmentConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_AssignmentConstraint,"choco_propagate_AssignmentConstraint"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_PalmEqualxc,"choco_awakeOnInf_PalmEqualxc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_PalmEqualxc,"choco_awakeOnSup_PalmEqualxc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._AssignmentConstraint,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_AssignmentConstraint,"choco_awakeOnInf_AssignmentConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._AssignmentConstraint,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_AssignmentConstraint,"choco_awakeOnSup_AssignmentConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmEqualxc,"choco_awakeOnRem_PalmEqualxc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._AssignmentConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_AssignmentConstraint,"choco_awakeOnRem_AssignmentConstraint"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmEqualxc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmEqualxc,"palm_awakeOnRestoreVal_PalmEqualxc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmEqualxc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmEqualxc,"choco_awakeOnInst_PalmEqualxc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmEqualxc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmEqualxc,"choco_askIfEntailed_PalmEqualxc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmEqualxc,"choco_testIfSatisfied_PalmEqualxc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmEqualxc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmEqualxc,"choco_testIfCompletelyInstantiated_PalmEqualxc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmEqualxc,"palm_whyIsTrue_PalmEqualxc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmEqualxc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmEqualxc,"palm_whyIsFalse_PalmEqualxc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmEqualxc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmEqualxc,"palm_checkPalm_PalmEqualxc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._AssignmentConstraint),Kernel._boolean,
  	0,_function_(palm_checkPalm_AssignmentConstraint,"palm_checkPalm_AssignmentConstraint"));
  
  (palm._PalmNotEqualxyc = ClaireClass::make("PalmNotEqualxyc",palm._PalmBinIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmNotEqualxyc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmNotEqualxyc_palm,"claire_self_print_PalmNotEqualxyc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmNotEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmNotEqualxyc,"choco_propagate_PalmNotEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmNotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmNotEqualxyc,"choco_awakeOnInf_PalmNotEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmNotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmNotEqualxyc,"choco_awakeOnSup_PalmNotEqualxyc"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmNotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmNotEqualxyc,"palm_awakeOnRestoreInf_PalmNotEqualxyc"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmNotEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmNotEqualxyc,"palm_awakeOnRestoreSup_PalmNotEqualxyc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmNotEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmNotEqualxyc,"choco_awakeOnRem_PalmNotEqualxyc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmNotEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmNotEqualxyc,"palm_awakeOnRestoreVal_PalmNotEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmNotEqualxyc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmNotEqualxyc,"choco_awakeOnInst_PalmNotEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmNotEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmNotEqualxyc,"choco_askIfEntailed_PalmNotEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmNotEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmNotEqualxyc,"choco_testIfSatisfied_PalmNotEqualxyc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmNotEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmNotEqualxyc,"choco_testIfCompletelyInstantiated_PalmNotEqualxyc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmNotEqualxyc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmNotEqualxyc,"palm_checkPalm_PalmNotEqualxyc"));
  
  (palm._PalmEqualxyc = ClaireClass::make("PalmEqualxyc",palm._PalmBinIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmEqualxyc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmEqualxyc_palm,"claire_self_print_PalmEqualxyc_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmEqualxyc,"choco_propagate_PalmEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmEqualxyc,"choco_awakeOnInf_PalmEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_PalmEqualxyc,"choco_awakeOnSup_PalmEqualxyc"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmEqualxyc,"palm_awakeOnRestoreInf_PalmEqualxyc"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmEqualxyc,"palm_awakeOnRestoreSup_PalmEqualxyc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmEqualxyc,"choco_awakeOnRem_PalmEqualxyc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmEqualxyc,"palm_awakeOnRestoreVal_PalmEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmEqualxyc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmEqualxyc,"choco_awakeOnInst_PalmEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmEqualxyc,"choco_askIfEntailed_PalmEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmEqualxyc,"choco_testIfSatisfied_PalmEqualxyc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmEqualxyc,"choco_testIfCompletelyInstantiated_PalmEqualxyc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmEqualxyc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmEqualxyc,"palm_checkPalm_PalmEqualxyc"));
  
  (palm._PalmGreaterOrEqualxyc = ClaireClass::make("PalmGreaterOrEqualxyc",palm._PalmBinIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmGreaterOrEqualxyc_palm,"claire_self_print_PalmGreaterOrEqualxyc_palm"));
  
  palm.hasSupport->addMethod(list::domain(3,palm._PalmGreaterOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_hasSupport_PalmGreaterOrEqualxyc,"palm_hasSupport_PalmGreaterOrEqualxyc"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_PalmGreaterOrEqualxyc,"choco_propagate_PalmGreaterOrEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmGreaterOrEqualxyc,"choco_awakeOnInf_PalmGreaterOrEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_PalmGreaterOrEqualxyc,"choco_awakeOnSup_PalmGreaterOrEqualxyc"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmGreaterOrEqualxyc,"palm_awakeOnRestoreInf_PalmGreaterOrEqualxyc"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmGreaterOrEqualxyc,"palm_awakeOnRestoreSup_PalmGreaterOrEqualxyc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmGreaterOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmGreaterOrEqualxyc,"choco_awakeOnRem_PalmGreaterOrEqualxyc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmGreaterOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmGreaterOrEqualxyc,"palm_awakeOnRestoreVal_PalmGreaterOrEqualxyc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmGreaterOrEqualxyc,"palm_whyIsFalse_PalmGreaterOrEqualxyc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmGreaterOrEqualxyc,"palm_whyIsTrue_PalmGreaterOrEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmGreaterOrEqualxyc,"choco_awakeOnInst_PalmGreaterOrEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmGreaterOrEqualxyc,"choco_askIfEntailed_PalmGreaterOrEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmGreaterOrEqualxyc,"choco_testIfSatisfied_PalmGreaterOrEqualxyc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxyc,"choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxyc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmGreaterOrEqualxyc,"palm_checkPalm_PalmGreaterOrEqualxyc"));
  
  (palm._PalmLessOrEqualxyc = ClaireClass::make("PalmLessOrEqualxyc",palm._PalmBinIntConstraint,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmLessOrEqualxyc_palm,"claire_self_print_PalmLessOrEqualxyc_palm"));
  
  palm.hasSupport->addMethod(list::domain(3,palm._PalmLessOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_hasSupport_PalmLessOrEqualxyc,"palm_hasSupport_PalmLessOrEqualxyc"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_PalmLessOrEqualxyc,"choco_propagate_PalmLessOrEqualxyc"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmLessOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmLessOrEqualxyc,"choco_awakeOnInf_PalmLessOrEqualxyc"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmLessOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_PalmLessOrEqualxyc,"choco_awakeOnSup_PalmLessOrEqualxyc"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmLessOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmLessOrEqualxyc,"palm_awakeOnRestoreInf_PalmLessOrEqualxyc"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmLessOrEqualxyc,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmLessOrEqualxyc,"palm_awakeOnRestoreSup_PalmLessOrEqualxyc"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmLessOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmLessOrEqualxyc,"choco_awakeOnRem_PalmLessOrEqualxyc"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmLessOrEqualxyc,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmLessOrEqualxyc,"palm_awakeOnRestoreVal_PalmLessOrEqualxyc"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmLessOrEqualxyc,"palm_whyIsFalse_PalmLessOrEqualxyc"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmLessOrEqualxyc,"palm_whyIsTrue_PalmLessOrEqualxyc"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmLessOrEqualxyc,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmLessOrEqualxyc,"choco_awakeOnInst_PalmLessOrEqualxyc"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmLessOrEqualxyc,"choco_askIfEntailed_PalmLessOrEqualxyc"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmLessOrEqualxyc,"choco_testIfSatisfied_PalmLessOrEqualxyc"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmLessOrEqualxyc,"choco_testIfCompletelyInstantiated_PalmLessOrEqualxyc"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmLessOrEqualxyc),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmLessOrEqualxyc,"palm_checkPalm_PalmLessOrEqualxyc"));
  
  (palm._PalmLinComb = ClaireClass::make("PalmLinComb",choco._LinComb,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLinComb),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmLinComb_palm,"claire_self_print_PalmLinComb_palm"));
  
  choco.assignIndices->addMethod(list::domain(3,palm._PalmLinComb,U_type(choco._CompositeConstraint,choco._Delayer),Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_assignIndices_PalmLinComb,"choco_assignIndices_PalmLinComb"));
  
  palm.makePalmLinComb->addMethod(list::domain(4,nth_class1(Kernel._list,Kernel._integer),
    nth_class1(Kernel._list,choco._IntVar),
    Kernel._integer,
    Kernel._integer),palm._PalmLinComb,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makePalmLinComb_list,"palm_makePalmLinComb_list"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmLinComb,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmLinComb_palm,"palm_updateDataStructuresOnConstraint_PalmLinComb_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmLinComb,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmLinComb_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmLinComb_palm"));
  
  palm.explainVariablesLB->addMethod(list::domain(1,palm._PalmLinComb),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explainVariablesLB_PalmLinComb,"palm_explainVariablesLB_PalmLinComb"));
  
  palm.explainVariablesUB->addMethod(list::domain(1,palm._PalmLinComb),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explainVariablesUB_PalmLinComb,"palm_explainVariablesUB_PalmLinComb"));
  
  choco.computeUpperBound->addMethod(list::domain(1,palm._PalmLinComb),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_computeUpperBound_PalmLinComb_palm,"choco_computeUpperBound_PalmLinComb_palm"));
  
  choco.computeLowerBound->addMethod(list::domain(1,palm._PalmLinComb),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_computeLowerBound_PalmLinComb_palm,"choco_computeLowerBound_PalmLinComb_palm"));
  
  choco.propagateNewLowerBound->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagateNewLowerBound_PalmLinComb_palm,"choco_propagateNewLowerBound_PalmLinComb_palm"));
  
  choco.propagateNewUpperBound->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagateNewUpperBound_PalmLinComb_palm,"choco_propagateNewUpperBound_PalmLinComb_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmLinComb),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_PalmLinComb,"choco_propagate_PalmLinComb"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmLinComb),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmLinComb_palm,"choco_awake_PalmLinComb_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmLinComb,"choco_awakeOnInf_PalmLinComb"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmLinComb,"choco_awakeOnSup_PalmLinComb"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmLinComb,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmLinComb,"choco_awakeOnRem_PalmLinComb"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmLinComb,"palm_awakeOnRestoreInf_PalmLinComb"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmLinComb,"palm_awakeOnRestoreSup_PalmLinComb"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmLinComb,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmLinComb,"palm_awakeOnRestoreVal_PalmLinComb"));
  
  palm.abstractDecInf->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(palm_abstractDecInf_PalmLinComb,"palm_abstractDecInf_PalmLinComb"));
  
  palm.abstractIncSup->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(palm_abstractIncSup_PalmLinComb,"palm_abstractIncSup_PalmLinComb"));
  
  palm.abstractRestoreVal->addMethod(list::domain(3,palm._PalmLinComb,Kernel._integer,Kernel._integer),Kernel._boolean,
  	SLOT_UPDATE,_function_(palm_abstractRestoreVal_PalmLinComb,"palm_abstractRestoreVal_PalmLinComb"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmLinComb),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmLinComb,"choco_askIfEntailed_PalmLinComb"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmLinComb),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmLinComb,"choco_testIfSatisfied_PalmLinComb"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmLinComb,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmLinComb,"choco_awakeOnInst_PalmLinComb"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmLinComb),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmLinComb,"palm_checkPalm_PalmLinComb"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,OBJECT(ClaireType,palm.Delayer->value),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreInf_Delayer,"palm_awakeOnRestoreInf_Delayer"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,OBJECT(ClaireType,palm.Delayer->value),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreSup_Delayer,"palm_awakeOnRestoreSup_Delayer"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,OBJECT(ClaireType,palm.Delayer->value),Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_Delayer,"palm_awakeOnRestoreVal_Delayer"));
  
  palm.abstractDecInf->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._boolean,
  	0,_function_(palm_abstractDecInf_AbstractConstraint,"palm_abstractDecInf_AbstractConstraint"));
  
  palm.abstractIncSup->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._boolean,
  	0,_function_(palm_abstractIncSup_AbstractConstraint,"palm_abstractIncSup_AbstractConstraint"));
  
  palm.abstractRestoreVal->addMethod(list::domain(3,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer,Kernel._integer),Kernel._boolean,
  	0,_function_(palm_abstractRestoreVal_AbstractConstraint,"palm_abstractRestoreVal_AbstractConstraint"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,OBJECT(ClaireType,palm.Delayer->value),
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_Delayer_palm,"palm_updateDataStructuresOnConstraint_Delayer_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,OBJECT(ClaireType,palm.Delayer->value),
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_Delayer_palm,"palm_updateDataStructuresOnRestoreConstraint_Delayer_palm"));
  
  { global_variable * _CL_obj = (palm.ALLDIFF_PRECISE_EXPLANATIONS = (global_variable *) Core._global_variable->instantiate("ALLDIFF_PRECISE_EXPLANATIONS",claire.it));
    (_CL_obj->range = Kernel._boolean);
    STOREI(_CL_obj->value,Kernel.ctrue);
    close_global_variable(_CL_obj);
    } 
  
  (palm._PalmCompleteAllDiff = ClaireClass::make("PalmCompleteAllDiff",ice._CompleteAllDiff,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmCompleteAllDiff),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmCompleteAllDiff_palm,"claire_self_print_PalmCompleteAllDiff_palm"));
  
  palm.e_dashallDifferent->addMethod(list::domain(1,nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmCompleteAllDiff,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashallDifferent_list,"claire_e-allDifferent_list"));
  
  palm.e_dashcompleteAllDiff->addMethod(list::domain(1,nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmCompleteAllDiff,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashcompleteAllDiff_list,"claire_e-completeAllDiff_list"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,palm._PalmCompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_PalmCompleteAllDiff,"choco_deleteEdgeAndPublish_PalmCompleteAllDiff"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmCompleteAllDiff,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnConstraint_PalmCompleteAllDiff_palm,"palm_updateDataStructuresOnConstraint_PalmCompleteAllDiff_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmCompleteAllDiff,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmCompleteAllDiff_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmCompleteAllDiff_palm"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmCompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmCompleteAllDiff,"choco_awakeOnRem_PalmCompleteAllDiff"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmCompleteAllDiff,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(palm_awakeOnRestoreVal_PalmCompleteAllDiff,"palm_awakeOnRestoreVal_PalmCompleteAllDiff"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmCompleteAllDiff),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmCompleteAllDiff_palm,"choco_awake_PalmCompleteAllDiff_palm"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmCompleteAllDiff),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmCompleteAllDiff,"palm_checkPalm_PalmCompleteAllDiff"));
  
  (palm._PalmPermutation = ClaireClass::make("PalmPermutation",ice._Permutation,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmPermutation),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmPermutation_palm,"claire_self_print_PalmPermutation_palm"));
  
  palm.e_dashpermutation->addMethod(list::domain(2,nth_class1(Kernel._list,palm._PalmIntVar),nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmPermutation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashpermutation_list,"claire_e-permutation_list"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,palm._PalmPermutation,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_PalmPermutation,"choco_deleteEdgeAndPublish_PalmPermutation"));
  
  palm.publishDeletion->addMethod(list::domain(4,palm._PalmPermutation,
    Kernel._integer,
    Kernel._integer,
    Kernel._boolean),Kernel._any,
  	NEW_ALLOC,_function_(palm_publishDeletion_PalmPermutation,"palm_publishDeletion_PalmPermutation"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmPermutation,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnConstraint_PalmPermutation_palm,"palm_updateDataStructuresOnConstraint_PalmPermutation_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmPermutation,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmPermutation_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmPermutation_palm"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmPermutation,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmPermutation,"choco_awakeOnRem_PalmPermutation"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmPermutation,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(palm_awakeOnRestoreVal_PalmPermutation,"palm_awakeOnRestoreVal_PalmPermutation"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmPermutation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmPermutation_palm,"choco_awake_PalmPermutation_palm"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmPermutation),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmPermutation,"palm_checkPalm_PalmPermutation"));
  
  (palm._PalmGlobalCardinality = ClaireClass::make("PalmGlobalCardinality",ice._GlobalCardinality,palm.it));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmGlobalCardinality),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmGlobalCardinality_palm,"claire_self_print_PalmGlobalCardinality_palm"));
  
  palm.e_dashgcc->addMethod(list::domain(2,nth_class1(Kernel._list,palm._PalmIntVar),nth_class1(Kernel._list,Core._Interval)),palm._PalmGlobalCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashgcc_list1,"palm_e-gcc_list1"));
  
  palm.e_dashgcc->addMethod(list::domain(4,nth_class1(Kernel._list,palm._PalmIntVar),
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,Core._Interval)),palm._PalmGlobalCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashgcc_list2,"palm_e-gcc_list2"));
  
  ice.deleteEdgeAndPublish->addMethod(list::domain(3,palm._PalmGlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_deleteEdgeAndPublish_PalmGlobalCardinality,"choco_deleteEdgeAndPublish_PalmGlobalCardinality"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmGlobalCardinality,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnConstraint_PalmGlobalCardinality_palm,"palm_updateDataStructuresOnConstraint_PalmGlobalCardinality_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmGlobalCardinality,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinality_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinality_palm"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmGlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmGlobalCardinality,"choco_awakeOnRem_PalmGlobalCardinality"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmGlobalCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(palm_awakeOnRestoreVal_PalmGlobalCardinality,"palm_awakeOnRestoreVal_PalmGlobalCardinality"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmGlobalCardinality),Kernel._void,
  	NEW_ALLOC,_function_(choco_awake_PalmGlobalCardinality_palm,"choco_awake_PalmGlobalCardinality_palm"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmGlobalCardinality),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmGlobalCardinality,"palm_checkPalm_PalmGlobalCardinality"));
  
  { (palm._PalmGlobalCardinalityVar = ClaireClass::make("PalmGlobalCardinalityVar",palm._PalmGlobalCardinality,palm.it));
    CL_ADD_SLOT(palm._PalmGlobalCardinalityVar,PalmGlobalCardinalityVar,palm.intervals,intervals,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmIntVar))))),CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmGlobalCardinalityVar),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmGlobalCardinalityVar_palm,"claire_self_print_PalmGlobalCardinalityVar_palm"));
  
  palm.e_dashgcc->addMethod(list::domain(2,nth_class1(Kernel._list,palm._PalmIntVar),nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmGlobalCardinalityVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashgcc_list3,"palm_e-gcc_list3"));
  
  palm.e_dashgcc->addMethod(list::domain(4,nth_class1(Kernel._list,palm._PalmIntVar),
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmGlobalCardinalityVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashgcc_list4,"palm_e-gcc_list4"));
  
  palm.mustDiminishFlowFromSource->addMethod(list::domain(2,palm._PalmGlobalCardinalityVar,Kernel._integer),Kernel._boolean,
  	0,_function_(palm_mustDiminishFlowFromSource_PalmGlobalCardinalityVar,"palm_mustDiminishFlowFromSource_PalmGlobalCardinalityVar"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmGlobalCardinalityVar,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnConstraint_PalmGlobalCardinalityVar_palm,"palm_updateDataStructuresOnConstraint_PalmGlobalCardinalityVar_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmGlobalCardinalityVar,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinalityVar_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmGlobalCardinalityVar_palm"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmGlobalCardinalityVar,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnRem_PalmGlobalCardinalityVar,"choco_awakeOnRem_PalmGlobalCardinalityVar"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmGlobalCardinalityVar,Kernel._integer,Kernel._integer),Kernel._void,
  	0,_function_(palm_awakeOnRestoreVal_PalmGlobalCardinalityVar,"palm_awakeOnRestoreVal_PalmGlobalCardinalityVar"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmGlobalCardinalityVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmGlobalCardinalityVar_palm,"choco_awake_PalmGlobalCardinalityVar_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmGlobalCardinalityVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmGlobalCardinalityVar,"choco_propagate_PalmGlobalCardinalityVar"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmGlobalCardinalityVar),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmGlobalCardinalityVar,"palm_checkPalm_PalmGlobalCardinalityVar"));
  
  ice.augmentFlow->addMethod(list::domain(1,palm._PalmCompleteAllDiff),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_augmentFlow_PalmCompleteAllDiff_palm,"ice_augmentFlow_PalmCompleteAllDiff_palm"));
  
  ice.augmentFlow->addMethod(list::domain(1,palm._PalmPermutation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_augmentFlow_PalmPermutation_palm,"ice_augmentFlow_PalmPermutation_palm"));
  
  ice.augmentFlow->addMethod(list::domain(1,palm._PalmGlobalCardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(ice_augmentFlow_PalmGlobalCardinality_palm,"ice_augmentFlow_PalmGlobalCardinality_palm"));
  
  palm.removeUselessEdges->addMethod(list::domain(1,palm._PalmCompleteAllDiff),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_removeUselessEdges_PalmCompleteAllDiff,"palm_removeUselessEdges_PalmCompleteAllDiff"));
  
  palm.removeUselessEdges->addMethod(list::domain(1,palm._PalmPermutation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_removeUselessEdges_PalmPermutation,"palm_removeUselessEdges_PalmPermutation"));
  
  palm.removeUselessEdges->addMethod(list::domain(1,palm._PalmGlobalCardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_removeUselessEdges_PalmGlobalCardinality,"palm_removeUselessEdges_PalmGlobalCardinality"));
  
  { (palm._PalmOccurrence = ClaireClass::make("PalmOccurrence",choco._Occurrence,palm.it));
    CL_ADD_SLOT(palm._PalmOccurrence,PalmOccurrence,palm.checkPossible,checkPossible,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmOccurrence,PalmOccurrence,palm.checkSure,checkSure,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmOccurrence,PalmOccurrence,palm.checkInf,checkInf,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    CL_ADD_SLOT(palm._PalmOccurrence,PalmOccurrence,palm.checkSup,checkSup,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    } 
  
  palm.checkNbPossible->addMethod(list::domain(1,palm._PalmOccurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkNbPossible_PalmOccurrence,"palm_checkNbPossible_PalmOccurrence"));
  
  palm.checkNbSure->addMethod(list::domain(1,palm._PalmOccurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkNbSure_PalmOccurrence,"palm_checkNbSure_PalmOccurrence"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmOccurrence,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmOccurrence_palm,"palm_updateDataStructuresOnConstraint_PalmOccurrence_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmOccurrence,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmOccurrence_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmOccurrence_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmOccurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmOccurrence,"choco_awakeOnInf_PalmOccurrence"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmOccurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmOccurrence,"choco_awakeOnSup_PalmOccurrence"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmOccurrence,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmOccurrence,"choco_awakeOnRem_PalmOccurrence"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmOccurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreInf_PalmOccurrence,"palm_awakeOnRestoreInf_PalmOccurrence"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmOccurrence,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreSup_PalmOccurrence,"palm_awakeOnRestoreSup_PalmOccurrence"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmOccurrence,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmOccurrence,"palm_awakeOnRestoreVal_PalmOccurrence"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmOccurrence),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmOccurrence_palm,"choco_awake_PalmOccurrence_palm"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmOccurrence),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmOccurrence,"choco_testIfSatisfied_PalmOccurrence"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmOccurrence),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmOccurrence,"choco_askIfEntailed_PalmOccurrence"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmOccurrence),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmOccurrence,"palm_checkPalm_PalmOccurrence"));
  
  (palm._PalmElt = ClaireClass::make("PalmElt",choco._Elt,palm.it));
  
  palm.makePalmEltConstraint->addMethod(list::domain(4,nth_class1(Kernel._list,Kernel._integer),
    palm._PalmIntVar,
    palm._PalmIntVar,
    Kernel._integer),palm._PalmElt,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makePalmEltConstraint_list,"palm_makePalmEltConstraint_list"));
  
  palm.updateValueFromIndex->addMethod(list::domain(1,palm._PalmElt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateValueFromIndex_PalmElt,"palm_updateValueFromIndex_PalmElt"));
  
  palm.updateIndexFromValue->addMethod(list::domain(1,palm._PalmElt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_updateIndexFromValue_PalmElt,"palm_updateIndexFromValue_PalmElt"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmElt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmElt_palm,"choco_awake_PalmElt_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmElt),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmElt,"choco_propagate_PalmElt"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInf_PalmElt,"choco_awakeOnInf_PalmElt"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnSup_PalmElt,"choco_awakeOnSup_PalmElt"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnInst_PalmElt,"choco_awakeOnInst_PalmElt"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmElt,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmElt,"choco_awakeOnRem_PalmElt"));
  
  palm.awakeOnRestore->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestore_PalmElt,"palm_awakeOnRestore_PalmElt"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreInf_PalmElt,"palm_awakeOnRestoreInf_PalmElt"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmElt,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreSup_PalmElt,"palm_awakeOnRestoreSup_PalmElt"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmElt,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmElt,"palm_awakeOnRestoreVal_PalmElt"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmElt),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmElt,"choco_askIfEntailed_PalmElt"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmElt),Kernel._boolean,
  	0,_function_(choco_testIfSatisfied_PalmElt,"choco_testIfSatisfied_PalmElt"));
  
  palm.whyTrueOrFalse->addMethod(list::domain(1,palm._PalmElt),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyTrueOrFalse_PalmElt,"palm_whyTrueOrFalse_PalmElt"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmElt),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsFalse_PalmElt,"palm_whyIsFalse_PalmElt"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmElt),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_whyIsTrue_PalmElt,"palm_whyIsTrue_PalmElt"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmElt),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmElt,"palm_checkPalm_PalmElt"));
  
  { (palm._PalmAC4BinConstraint = ClaireClass::make("PalmAC4BinConstraint",palm._PalmBinIntConstraint,palm.it));
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.cachedTuples,cachedTuples,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.matrix,matrix,OBJECT(ClaireType,palm.BoolMatrix2D->value),CNULL);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.feasTest,feasTest,nth_class2(Kernel._method,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.supportsOnV1,supportsOnV1,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,GC_OID(_oid_(nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))))))))),CNULL);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.nbSupportsOnV1,nbSupportsOnV1,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.supportsOnV2,supportsOnV2,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,GC_OID(_oid_(nth_class2(Kernel._set,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))))))))),CNULL);
    CL_ADD_SLOT(palm._PalmAC4BinConstraint,PalmAC4BinConstraint,palm.nbSupportsOnV2,nbSupportsOnV2,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmAC4BinConstraint_palm,"claire_self_print_PalmAC4BinConstraint_palm"));
  
  palm.makeAC4constraint->addMethod(list::domain(4,palm._PalmIntVar,
    palm._PalmIntVar,
    Kernel._boolean,
    param_I_class(Kernel._list,GC_OBJECT(ClaireType,param_I_class(Kernel._list,Kernel._integer)))),palm._PalmAC4BinConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeAC4constraint_PalmIntVar,"palm_makeAC4constraint_PalmIntVar"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_testIfSatisfied_PalmAC4BinConstraint,"choco_testIfSatisfied_PalmAC4BinConstraint"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmAC4BinConstraint_palm,"choco_awake_PalmAC4BinConstraint_palm"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmAC4BinConstraint,"choco_propagate_PalmAC4BinConstraint"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmAC4BinConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awakeOnRem_PalmAC4BinConstraint,"choco_awakeOnRem_PalmAC4BinConstraint"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmAC4BinConstraint,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_awakeOnRestoreVal_PalmAC4BinConstraint,"palm_awakeOnRestoreVal_PalmAC4BinConstraint"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmAC4BinConstraint,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmAC4BinConstraint_palm,"palm_updateDataStructuresOnConstraint_PalmAC4BinConstraint_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmAC4BinConstraint,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmAC4BinConstraint_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmAC4BinConstraint_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmAC4BinConstraint,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInf_PalmAC4BinConstraint,"choco_awakeOnInf_PalmAC4BinConstraint"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmAC4BinConstraint,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnSup_PalmAC4BinConstraint,"choco_awakeOnSup_PalmAC4BinConstraint"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmAC4BinConstraint,Kernel._integer),Kernel._void,
  	0,_function_(choco_awakeOnInst_PalmAC4BinConstraint,"choco_awakeOnInst_PalmAC4BinConstraint"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmAC4BinConstraint),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(choco_askIfEntailed_PalmAC4BinConstraint,"choco_askIfEntailed_PalmAC4BinConstraint"));
  
  choco.testIfCompletelyInstantiated->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._boolean,
  	0,_function_(choco_testIfCompletelyInstantiated_PalmAC4BinConstraint,"choco_testIfCompletelyInstantiated_PalmAC4BinConstraint"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmAC4BinConstraint),Kernel._boolean,
  	0,_function_(palm_checkPalm_PalmAC4BinConstraint,"palm_checkPalm_PalmAC4BinConstraint"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._Explanation),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_Explanation_palm,"claire_self_print_Explanation_palm"));
  
  palm.addConstraint->addMethod(list::domain(2,palm._Explanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_addConstraint_Explanation,"palm_addConstraint_Explanation"));
  
  palm.deleteConstraint->addMethod(list::domain(2,palm._Explanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	RETURN_ARG,_function_(palm_deleteConstraint_Explanation,"palm_deleteConstraint_Explanation"));
  
  palm.mergeConstraints->addMethod(list::domain(2,palm._Explanation,nth_class1(Kernel._set,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_mergeConstraints_Explanation,"palm_mergeConstraints_Explanation"));
  
  palm.merge->addMethod(list::domain(2,palm._Explanation,palm._Explanation),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_merge_Explanation,"palm_merge_Explanation"));
  
  palm.empty_ask->addMethod(list::domain(1,palm._Explanation),Kernel._boolean,
  	0,_function_(palm_empty_ask_Explanation,"palm_empty?_Explanation"));
  
  palm.empties->addMethod(list::domain(1,palm._Explanation),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_empties_Explanation,"palm_empties_Explanation"));
  
  Kernel._delete->addMethod(list::domain(2,palm._Explanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),palm._Explanation,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire_delete_Explanation,"claire_delete_Explanation"));
  
  Kernel.set_I->addMethod(list::domain(1,palm._Explanation),nth_class1(Kernel._set,choco._AbstractConstraint),
  	0,_function_(claire_set_I_Explanation,"claire_set!_Explanation"));
  
  ;
  Kernel._Z->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._boolean,
  	0,_function_(claire__Z_AbstractConstraint,"claire_%_AbstractConstraint"));
  
  palm.clone->addMethod(list::domain(1,palm._Explanation),palm._Explanation,
  	NEW_ALLOC,_function_(palm_clone_Explanation,"palm_clone_Explanation"));
  
  palm.valid_ask->addMethod(list::domain(1,palm._Explanation),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_valid_ask_Explanation,"palm_valid?_Explanation"));
  
  palm.contains_ask->addMethod(list::domain(2,palm._Explanation,palm._Explanation),Kernel._boolean,
  	0,_function_(palm_contains_ask_Explanation,"palm_contains?_Explanation"));
  
  palm.clean->addMethod(list::domain(1,palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_clean_Explanation,"palm_clean_Explanation"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._DecInf),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_DecInf_palm,"claire_self_print_DecInf_palm"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._IncSup),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_IncSup_palm,"claire_self_print_IncSup_palm"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._ValueRestorations),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_ValueRestorations_palm,"claire_self_print_ValueRestorations_palm"));
  
  palm.makeValueRestorations->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._ValueRestorations,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makeValueRestorations_PalmIntVar,"palm_makeValueRestorations_PalmIntVar"));
  
  palm.addDependencies->addMethod(list::domain(1,palm._Explanation),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addDependencies_Explanation,"palm_addDependencies_Explanation"));
  
  palm.addDependencies->addMethod(list::domain(2,nth_class1(Kernel._set,choco._AbstractConstraint),palm._Explanation),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addDependencies_set,"palm_addDependencies_set"));
  
  palm.removeDependencies->addMethod(list::domain(2,palm._Explanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_removeDependencies_Explanation,"palm_removeDependencies_Explanation"));
  
  palm.makeIncInfExplanation->addMethod(list::domain(3,palm._Explanation,Kernel._integer,palm._PalmIntVar),palm._PalmIncInfExplanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeIncInfExplanation_Explanation,"palm_makeIncInfExplanation_Explanation"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmIncInfExplanation),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmIncInfExplanation_palm,"claire_self_print_PalmIncInfExplanation_palm"));
  
  palm.postUndoRemoval->addMethod(list::domain(2,palm._PalmIncInfExplanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postUndoRemoval_PalmIncInfExplanation_palm,"palm_postUndoRemoval_PalmIncInfExplanation_palm"));
  
  palm.makeDecSupExplanation->addMethod(list::domain(3,palm._Explanation,Kernel._integer,palm._PalmIntVar),palm._PalmDecSupExplanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeDecSupExplanation_Explanation,"palm_makeDecSupExplanation_Explanation"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmDecSupExplanation),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmDecSupExplanation_palm,"claire_self_print_PalmDecSupExplanation_palm"));
  
  palm.postUndoRemoval->addMethod(list::domain(2,palm._PalmDecSupExplanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postUndoRemoval_PalmDecSupExplanation_palm,"palm_postUndoRemoval_PalmDecSupExplanation_palm"));
  
  abstract_property(palm.postUndoRemoval);
  
  palm.makeRemovalExplanation->addMethod(list::domain(3,palm._Explanation,Kernel._integer,palm._PalmIntVar),palm._PalmRemovalExplanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeRemovalExplanation_Explanation,"palm_makeRemovalExplanation_Explanation"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmRemovalExplanation),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmRemovalExplanation_palm,"claire_self_print_PalmRemovalExplanation_palm"));
  
  palm.postUndoRemoval->addMethod(list::domain(2,palm._PalmRemovalExplanation,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postUndoRemoval_PalmRemovalExplanation_palm,"palm_postUndoRemoval_PalmRemovalExplanation_palm"));
  
  palm.makePalmEngine->addMethod(list::domain(1,Kernel._integer),palm._PalmEngine,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makePalmEngine_integer,"palm_makePalmEngine_integer"));
  
  choco.getNextActiveEventQueue->addMethod(list::domain(1,palm._PalmEngine),U_type(choco._EventCollection,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_getNextActiveEventQueue_PalmEngine_palm,"choco_getNextActiveEventQueue_PalmEngine_palm"));
  
  palm.resetEvent->addMethod(list::domain(1,OBJECT(ClaireType,palm.BoundUpdate->value)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_resetEvent_BoundUpdate,"palm_resetEvent_BoundUpdate"));
  
  palm.resetEvent->addMethod(list::domain(1,OBJECT(ClaireType,palm.ValueRemovals->value)),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_resetEvent_ValueRemovals,"palm_resetEvent_ValueRemovals"));
  
  palm.resetPoppingQueue->addMethod(list::domain(1,OBJECT(ClaireType,palm.BoundEventQueue->value)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_resetPoppingQueue_BoundEventQueue,"palm_resetPoppingQueue_BoundEventQueue"));
  
  palm.resetPoppingQueue->addMethod(list::domain(1,OBJECT(ClaireType,palm.RemovalEventQueue->value)),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_resetPoppingQueue_RemovalEventQueue,"palm_resetPoppingQueue_RemovalEventQueue"));
  
  palm.resetEventQueue->addMethod(list::domain(1,OBJECT(ClaireType,palm.EventQueue->value)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_resetEventQueue_EventQueue,"palm_resetEventQueue_EventQueue"));
  
  palm.resetEvents->addMethod(list::domain(1,palm._PalmEngine),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_resetEvents_PalmEngine,"palm_resetEvents_PalmEngine"));
  
  palm.flushEvents->addMethod(list::domain(1,palm._PalmEngine),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_flushEvents_PalmEngine,"palm_flushEvents_PalmEngine"));
  
  palm.raisePalmContradiction->addMethod(list::domain(2,palm._PalmEngine,palm._PalmIntVar),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_raisePalmContradiction_PalmEngine,"palm_raisePalmContradiction_PalmEngine"));
  
  palm.raisePalmFakeContradiction->addMethod(list::domain(2,palm._PalmEngine,palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_raisePalmFakeContradiction_PalmEngine,"palm_raisePalmFakeContradiction_PalmEngine"));
  
  palm.raiseSystemContradiction->addMethod(list::domain(1,palm._PalmEngine),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_raiseSystemContradiction_PalmEngine,"palm_raiseSystemContradiction_PalmEngine"));
  
  palm.remove->addMethod(list::domain(2,palm._PalmEngine,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remove_PalmEngine1,"palm_remove_PalmEngine1"));
  
  palm.remove->addMethod(list::domain(2,palm._PalmEngine,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remove_PalmEngine2,"palm_remove_PalmEngine2"));
  
  palm.restoreVariableExplanations->addMethod(list::domain(1,palm._PalmEngine),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_restoreVariableExplanations_PalmEngine,"palm_restoreVariableExplanations_PalmEngine"));
  
  palm.checkVariableDomainAgainstRemValEvt->addMethod(list::domain(1,palm._PalmIntVar),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkVariableDomainAgainstRemValEvt_PalmIntVar,"palm_checkVariableDomainAgainstRemValEvt_PalmIntVar"));
  
  choco.propagateEvent->addMethod(list::domain(2,palm._DecInf,OBJECT(ClaireType,palm.BoundEventQueue->value)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagateEvent_DecInf_palm,"choco_propagateEvent_DecInf_palm"));
  
  choco.propagateEvent->addMethod(list::domain(2,palm._IncSup,OBJECT(ClaireType,palm.BoundEventQueue->value)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagateEvent_IncSup_palm,"choco_propagateEvent_IncSup_palm"));
  
  choco.propagateEvent->addMethod(list::domain(2,palm._ValueRestorations,OBJECT(ClaireType,palm.RemovalEventQueue->value)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagateEvent_ValueRestorations_palm,"choco_propagateEvent_ValueRestorations_palm"));
  
  palm.becauseOf->addMethod(list::domain(1,Kernel._listargs),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_becauseOf_listargs,"palm_becauseOf_listargs"));
  
  palm.theInf->addFloatMethod(list::domain(1,palm._PalmIntVar),tuple::alloc(2,_oid_(palm._PalmIntVar),_oid_(Kernel._integer)),
  	NEW_ALLOC,_function_(palm_theInf_PalmIntVar,"palm_theInf_PalmIntVar"),_function_(palm_theInf_PalmIntVar_,"palm_theInf_PalmIntVar_"));
  
  palm.theSup->addFloatMethod(list::domain(1,palm._PalmIntVar),tuple::alloc(2,_oid_(palm._PalmIntVar),_oid_(Kernel._integer)),
  	NEW_ALLOC,_function_(palm_theSup_PalmIntVar,"palm_theSup_PalmIntVar"),_function_(palm_theSup_PalmIntVar_,"palm_theSup_PalmIntVar_"));
  
  palm.theDom->addFloatMethod(list::domain(1,palm._PalmIntVar),tuple::alloc(2,_oid_(palm._PalmIntVar),_oid_(Kernel._integer)),
  	NEW_ALLOC,_function_(palm_theDom_PalmIntVar,"palm_theDom_PalmIntVar"),_function_(palm_theDom_PalmIntVar_,"palm_theDom_PalmIntVar_"));
  
  palm.theHole->addFloatMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),tuple::alloc(3,_oid_(palm._PalmIntVar),
    _oid_(Kernel._integer),
    _oid_(Kernel._integer)),
  	NEW_ALLOC,_function_(palm_theHole_PalmIntVar,"palm_theHole_PalmIntVar"),_function_(palm_theHole_PalmIntVar_,"palm_theHole_PalmIntVar_"));
  
  palm.theVars->addFloatMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),tuple::alloc(1,_oid_(choco._AbstractConstraint)),
  	NEW_ALLOC,_function_(palm_theVars_AbstractConstraint,"palm_theVars_AbstractConstraint"),_function_(palm_theVars_AbstractConstraint_,"palm_theVars_AbstractConstraint_"));
  
  palm.explain->addMethod(list::domain(2,palm._PalmIntVar,OBJECT(ClaireType,palm.SELECT->value)),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explain_PalmIntVar1,"palm_explain_PalmIntVar1"));
  
  palm.explain->addMethod(list::domain(3,palm._PalmIntVar,OBJECT(ClaireType,palm.SELECT->value),Kernel._integer),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explain_PalmIntVar2,"palm_explain_PalmIntVar2"));
  
  palm.explain->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explain_AbstractConstraint,"palm_explain_AbstractConstraint"));
  
  palm.explain->addMethod(list::domain(1,palm._PalmProblem),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explain_PalmProblem,"palm_explain_PalmProblem"));
  
  (palm._PalmAssignVar = ClaireClass::make("PalmAssignVar",palm._PalmBranching,palm.it));
  
  { global_variable * _CL_obj = (palm.RLX = (global_variable *) Core._global_variable->instantiate("RLX",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,1);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.EXT = (global_variable *) Core._global_variable->instantiate("EXT",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,2);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.CPU = (global_variable *) Core._global_variable->instantiate("CPU",claire.it));
    (_CL_obj->range = Kernel.emptySet);
    STOREI(_CL_obj->value,3);
    close_global_variable(_CL_obj);
    } 
  
  { global_variable * _CL_obj = (palm.RuntimeStatistic = (global_variable *) Core._global_variable->instantiate("RuntimeStatistic",palm.it));
    (_CL_obj->range = Kernel.emptySet);
    update_property(Kernel.value,
      _CL_obj,
      3,
      Kernel._any,
      _oid_(set::alloc(Kernel.emptySet,3,1,
        2,
        3)));
    close_global_variable(_CL_obj);
    } 
  
  palm.getGlobalStatistics->addMethod(list::domain(2,palm._PalmSolver,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._integer,
  	0,_function_(palm_getGlobalStatistics_PalmSolver,"palm_getGlobalStatistics_PalmSolver"));
  
  palm.getGlobalStatistics->addMethod(list::domain(2,palm._PalmProblem,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getGlobalStatistics_PalmProblem,"palm_getGlobalStatistics_PalmProblem"));
  
  palm.getRuntimeStatistic->addMethod(list::domain(2,palm._PalmSolver,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._integer,
  	RETURN_ARG,_function_(palm_getRuntimeStatistic_PalmSolver,"palm_getRuntimeStatistic_PalmSolver"));
  
  palm.getRuntimeStatistic->addMethod(list::domain(2,palm._PalmProblem,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._integer,
  	RETURN_ARG,_function_(palm_getRuntimeStatistic_PalmProblem,"palm_getRuntimeStatistic_PalmProblem"));
  
  palm.setRuntimeStatistic->addMethod(list::domain(3,palm._PalmSolver,OBJECT(ClaireType,palm.RuntimeStatistic->value),Kernel._integer),Kernel._integer,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_setRuntimeStatistic_PalmSolver,"palm_setRuntimeStatistic_PalmSolver"));
  
  palm.incRuntimeStatistic->addMethod(list::domain(3,palm._PalmSolver,OBJECT(ClaireType,palm.RuntimeStatistic->value),Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_incRuntimeStatistic_PalmSolver,"palm_incRuntimeStatistic_PalmSolver"));
  
  palm.decRuntimeStatistic->addMethod(list::domain(3,palm._PalmSolver,OBJECT(ClaireType,palm.RuntimeStatistic->value),Kernel._integer),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_decRuntimeStatistic_PalmSolver,"palm_decRuntimeStatistic_PalmSolver"));
  
  palm.printRuntimeStatistics->addMethod(list::domain(1,palm._PalmProblem),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_printRuntimeStatistics_PalmProblem1,"palm_printRuntimeStatistics_PalmProblem1"));
  
  palm.printRuntimeStatistics->addMethod(list::domain(2,palm._PalmProblem,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_printRuntimeStatistics_PalmProblem2,"palm_printRuntimeStatistics_PalmProblem2"));
  
  palm.reset->addMethod(list::domain(1,palm._PalmSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_reset_PalmSolver,"palm_reset_PalmSolver"));
  
  palm.initPalmSolver->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initPalmSolver_PalmProblem,"palm_initPalmSolver_PalmProblem"));
  
  palm.initPalmBranchAndBound->addMethod(list::domain(3,palm._PalmProblem,Kernel._boolean,palm._PalmIntVar),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_initPalmBranchAndBound_PalmProblem,"palm_initPalmBranchAndBound_PalmProblem"));
  
  palm.attachPalmExtend->addMethod(list::domain(2,palm._PalmProblem,palm._PalmExtend),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_attachPalmExtend_PalmProblem,"palm_attachPalmExtend_PalmProblem"));
  
  palm.attachPalmLearn->addMethod(list::domain(2,palm._PalmProblem,palm._PalmLearn),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_attachPalmLearn_PalmProblem,"palm_attachPalmLearn_PalmProblem"));
  
  palm.attachPalmRepair->addMethod(list::domain(2,palm._PalmProblem,palm._PalmRepair),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_attachPalmRepair_PalmProblem,"palm_attachPalmRepair_PalmProblem"));
  
  palm.attachPalmState->addMethod(list::domain(2,palm._PalmProblem,palm._PalmState),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_attachPalmState_PalmProblem,"palm_attachPalmState_PalmProblem"));
  
  palm.attachPalmBranchings->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,palm._PalmBranching)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_attachPalmBranchings_PalmProblem,"palm_attachPalmBranchings_PalmProblem"));
  
  palm.searchOneSolution->addMethod(list::domain(1,palm._PalmProblem),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_searchOneSolution_PalmProblem,"palm_searchOneSolution_PalmProblem"));
  
  palm.run->addMethod(list::domain(1,palm._PalmSolver),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_run_PalmSolver,"palm_run_PalmSolver"));
  
  palm.runonce->addMethod(list::domain(1,palm._PalmBranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_runonce_PalmBranchAndBound,"palm_runonce_PalmBranchAndBound"));
  
  palm.run->addMethod(list::domain(1,palm._PalmBranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_run_PalmBranchAndBound,"palm_run_PalmBranchAndBound"));
  
  palm.postDynamicCut->addMethod(list::domain(1,palm._PalmBranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_postDynamicCut_PalmBranchAndBound,"palm_postDynamicCut_PalmBranchAndBound"));
  
  abstract_property(palm.extend);
  
  palm.extend->addMethod(list::domain(1,palm._PalmSolver),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_extend_PalmSolver_palm,"palm_extend_PalmSolver_palm"));
  
  palm.explore->addMethod(list::domain(2,palm._PalmExtend,palm._PalmBranching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_explore_PalmExtend,"palm_explore_PalmExtend"));
  
  palm.explore->addMethod(list::domain(2,palm._PalmUnsureExtend,palm._PalmBranching),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_explore_PalmUnsureExtend,"palm_explore_PalmUnsureExtend"));
  
  palm.selectAuthorizedDecisions->addMethod(list::domain(2,palm._PalmBranching,Kernel._any),param_I_class(Kernel._list,choco._AbstractConstraint),
  	NEW_ALLOC,_function_(palm_selectAuthorizedDecisions_PalmBranching,"palm_selectAuthorizedDecisions_PalmBranching"));
  
  palm.propagateAllDecisionConstraints->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_propagateAllDecisionConstraints_PalmProblem,"palm_propagateAllDecisionConstraints_PalmProblem"));
  
  palm.selectBranchingItem->addMethod(list::domain(1,palm._PalmBranching),Kernel._any,
  	0,_function_(palm_selectBranchingItem_PalmBranching_palm,"palm_selectBranchingItem_PalmBranching_palm"));
  
  abstract_property(palm.selectBranchingItem);
  
  palm.selectDecisions->addMethod(list::domain(2,palm._PalmBranching,Kernel._any),param_I_class(Kernel._list,choco._AbstractConstraint),
  	NEW_ALLOC,_function_(palm_selectDecisions_PalmBranching_palm,"palm_selectDecisions_PalmBranching_palm"));
  
  abstract_property(palm.selectDecisions);
  
  palm.getNextDecisions->addMethod(list::domain(1,palm._PalmBranching),param_I_class(Kernel._list,choco._AbstractConstraint),
  	NEW_ALLOC,_function_(palm_getNextDecisions_PalmBranching_palm,"palm_getNextDecisions_PalmBranching_palm"));
  
  abstract_property(palm.getNextDecisions);
  
  palm.checkAcceptable->addMethod(list::domain(2,palm._PalmBranching,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._boolean,
  	0,_function_(palm_checkAcceptable_PalmBranching_palm,"palm_checkAcceptable_PalmBranching_palm"));
  
  palm.learnFromRejection->addMethod(list::domain(1,palm._PalmBranching),Kernel._void,
  	SAFE_RESULT,_function_(palm_learnFromRejection_PalmBranching_palm,"palm_learnFromRejection_PalmBranching_palm"));
  
  abstract_property(palm.learnFromRejection);
  
  abstract_property(palm.repair);
  
  palm.repair->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_repair_PalmProblem_palm,"palm_repair_PalmProblem_palm"));
  
  palm.repair->addMethod(list::domain(1,palm._PalmSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_repair_PalmSolver_palm,"palm_repair_PalmSolver_palm"));
  
  palm.addDecision->addMethod(list::domain(2,palm._PalmState,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_addDecision_PalmState_palm,"palm_addDecision_PalmState_palm"));
  
  abstract_property(palm.addDecision);
  
  palm.reverseDecision->addMethod(list::domain(2,palm._PalmState,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	RETURN_ARG,_function_(palm_reverseDecision_PalmState_palm,"palm_reverseDecision_PalmState_palm"));
  
  abstract_property(palm.reverseDecision);
  
  palm.removeDecision->addMethod(list::domain(2,palm._PalmState,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_removeDecision_PalmState_palm,"palm_removeDecision_PalmState_palm"));
  
  abstract_property(palm.removeDecision);
  
  palm.learnFromContradiction->addMethod(list::domain(2,palm._PalmLearn,palm._Explanation),Kernel._void,
  	0,_function_(palm_learnFromContradiction_PalmLearn_palm,"palm_learnFromContradiction_PalmLearn_palm"));
  
  abstract_property(palm.learnFromContradiction);
  
  palm.learnFromRemoval->addMethod(list::domain(2,palm._PalmLearn,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	0,_function_(palm_learnFromRemoval_PalmLearn_palm,"palm_learnFromRemoval_PalmLearn_palm"));
  
  abstract_property(palm.learnFromRemoval);
  
  palm.checkAcceptable->addMethod(list::domain(2,palm._PalmLearn,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._boolean,
  	0,_function_(palm_checkAcceptable_PalmLearn_palm,"palm_checkAcceptable_PalmLearn_palm"));
  
  abstract_property(palm.checkAcceptable);
  
  palm.sortConstraintsToUndo->addMethod(list::domain(2,palm._PalmLearn,palm._Explanation),param_I_class(Kernel._list,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_sortConstraintsToUndo_PalmLearn_palm,"palm_sortConstraintsToUndo_PalmLearn_palm"));
  
  abstract_property(palm.sortConstraintsToUndo);
  
  palm.checkAcceptableRelaxation->addMethod(list::domain(2,palm._PalmLearn,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	0,_function_(palm_checkAcceptableRelaxation_PalmLearn_palm,"palm_checkAcceptableRelaxation_PalmLearn_palm"));
  
  abstract_property(palm.checkAcceptableRelaxation);
  
  palm.selectDecisionToUndo->addMethod(list::domain(2,palm._PalmRepair,palm._Explanation),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_selectDecisionToUndo_PalmRepair_palm,"palm_selectDecisionToUndo_PalmRepair_palm"));
  
  abstract_property(palm.selectDecisionToUndo);
  
  palm.selectDecisionToUndo->addMethod(list::domain(2,palm._PalmUnsureRepair,palm._Explanation),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(palm_selectDecisionToUndo_PalmUnsureRepair_palm,"palm_selectDecisionToUndo_PalmUnsureRepair_palm"));
  
  abstract_property(palm.selectDecisionToUndo);
  
  palm.standard_better_constraint->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_standard_better_constraint_AbstractConstraint,"palm_standard_better_constraint_AbstractConstraint"));
  
  palm.standard_better_ordered_constraint->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_standard_better_ordered_constraint_AbstractConstraint,"palm_standard_better_ordered_constraint_AbstractConstraint"));
  
  palm.getObjectiveValue->addMethod(list::domain(3,palm._PalmProblem,palm._PalmIntVar,Kernel._boolean),Kernel._integer,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_getObjectiveValue_PalmProblem,"palm_getObjectiveValue_PalmProblem"));
  
  (palm._PalmAssignVar = ClaireClass::make("PalmAssignVar",palm._PalmBranching,palm.it));
  
  palm.selectBranchingItem->addMethod(list::domain(1,palm._PalmAssignVar),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_selectBranchingItem_PalmAssignVar_palm,"palm_selectBranchingItem_PalmAssignVar_palm"));
  
  palm.selectDecisions->addMethod(list::domain(2,palm._PalmAssignVar,palm._PalmIntVar),param_I_class(Kernel._list,choco._AbstractConstraint),
  	NEW_ALLOC,_function_(palm_selectDecisions_PalmAssignVar_palm,"palm_selectDecisions_PalmAssignVar_palm"));
  
  (palm._PalmAssignMinDomVar = ClaireClass::make("PalmAssignMinDomVar",palm._PalmAssignVar,palm.it));
  
  (palm._PalmAssignMinDomDegVar = ClaireClass::make("PalmAssignMinDomDegVar",palm._PalmAssignVar,palm.it));
  
  palm.selectBranchingItem->addMethod(list::domain(1,palm._PalmAssignMinDomDegVar),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_selectBranchingItem_PalmAssignMinDomDegVar_palm,"palm_selectBranchingItem_PalmAssignMinDomDegVar_palm"));
  
  palm.getMinDomVar->addMethod(list::domain(1,OBJECT(ClaireType,palm.Problem->value)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getMinDomVar_Problem,"palm_getMinDomVar_Problem"));
  
  palm.getMinDomVar->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getMinDomVar_list,"palm_getMinDomVar_list"));
  
  palm.getMinDomDegVar->addMethod(list::domain(1,OBJECT(ClaireType,palm.Problem->value)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getMinDomDegVar_Problem,"palm_getMinDomDegVar_Problem"));
  
  palm.getMinDomDegVar->addMethod(list::domain(1,nth_class1(Kernel._list,choco._IntVar)),U_type(choco._IntVar,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getMinDomDegVar_list,"palm_getMinDomDegVar_list"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmSolution),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmSolution_palm,"claire_self_print_PalmSolution_palm"));
  
  palm.getRuntimeStatistic->addMethod(list::domain(2,palm._PalmSolution,OBJECT(ClaireType,palm.RuntimeStatistic->value)),Kernel._integer,
  	RETURN_ARG,_function_(palm_getRuntimeStatistic_PalmSolution,"palm_getRuntimeStatistic_PalmSolution"));
  
  palm.storeSolution->addMethod(list::domain(1,palm._PalmSolver),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_storeSolution_PalmSolver,"palm_storeSolution_PalmSolver"));
  
  palm.storeSolution->addMethod(list::domain(1,palm._PalmBranchAndBound),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_storeSolution_PalmBranchAndBound,"palm_storeSolution_PalmBranchAndBound"));
  
  palm.discardCurrentSolution->addMethod(list::domain(1,palm._PalmState),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_discardCurrentSolution_PalmState,"palm_discardCurrentSolution_PalmState"));
  
  palm.makePathRepairLearn->addMethod(list::domain(2,Kernel._integer,Kernel._integer),palm._PathRepairLearn,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makePathRepairLearn_integer,"palm_makePathRepairLearn_integer"));
  
  palm.makePathRepairLearn->addMethod(list::domain(3,Kernel._class,Kernel._integer,Kernel._integer),palm._PathRepairLearn,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePathRepairLearn_class,"palm_makePathRepairLearn_class"));
  
  palm.addForbiddenSituation->addMethod(list::domain(2,palm._PathRepairLearn,palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_addForbiddenSituation_PathRepairLearn,"palm_addForbiddenSituation_PathRepairLearn"));
  
  palm.isForbidden->addMethod(list::domain(2,palm._PathRepairLearn,palm._Explanation),Kernel._boolean,
  	0,_function_(palm_isForbidden_PathRepairLearn,"palm_isForbidden_PathRepairLearn"));
  
  palm.learnFromContradiction->addMethod(list::domain(2,palm._PathRepairLearn,palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_learnFromContradiction_PathRepairLearn_palm,"palm_learnFromContradiction_PathRepairLearn_palm"));
  
  palm.informConstraintsInExplanation->addMethod(list::domain(2,palm._PathRepairLearn,palm._Explanation),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_informConstraintsInExplanation_PathRepairLearn_palm,"palm_informConstraintsInExplanation_PathRepairLearn_palm"));
  
  abstract_property(palm.informConstraintsInExplanation);
  
  palm.learnFromRemoval->addMethod(list::domain(2,palm._PathRepairLearn,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_learnFromRemoval_PathRepairLearn_palm,"palm_learnFromRemoval_PathRepairLearn_palm"));
  
  palm.checkAcceptable->addMethod(list::domain(2,palm._PathRepairLearn,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkAcceptable_PathRepairLearn_palm,"palm_checkAcceptable_PathRepairLearn_palm"));
  
  palm.checkAcceptableRelaxation->addMethod(list::domain(2,palm._PathRepairLearn,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkAcceptableRelaxation_PathRepairLearn_palm,"palm_checkAcceptableRelaxation_PathRepairLearn_palm"));
  
  { (palm._PalmDisjunction = ClaireClass::make("PalmDisjunction",choco._Disjunction,palm.it));
    CL_ADD_SLOT(palm._PalmDisjunction,PalmDisjunction,palm.isContradictory,isContradictory,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmDisjunction,PalmDisjunction,palm.needToAwakeC1,needToAwakeC1,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmDisjunction,PalmDisjunction,palm.needToAwakeC2,needToAwakeC2,Kernel._boolean,Kernel.cfalse);
    } 
  
  palm.initHook->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmDisjunction,"palm_initHook_PalmDisjunction"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmDisjunction,"palm_takeIntoAccountStatusChange_PalmDisjunction"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmDisjunction_palm,"claire_self_print_PalmDisjunction_palm"));
  
  palm.needToAwake->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._boolean,
  	0,_function_(palm_needToAwake_PalmDisjunction,"palm_needToAwake_PalmDisjunction"));
  
  palm.setNeedToAwake->addMethod(list::domain(3,palm._PalmDisjunction,Kernel._integer,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setNeedToAwake_PalmDisjunction,"palm_setNeedToAwake_PalmDisjunction"));
  
  palm.checkStatusAndReport->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkStatusAndReport_PalmDisjunction,"palm_checkStatusAndReport_PalmDisjunction"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmDisjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmDisjunction_palm,"palm_updateDataStructuresOnConstraint_PalmDisjunction_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmDisjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmDisjunction_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmDisjunction_palm"));
  
  palm.checkConstraintState->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_checkConstraintState_PalmDisjunction,"palm_checkConstraintState_PalmDisjunction"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmDisjunction,"choco_awakeOnInf_PalmDisjunction"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmDisjunction,"palm_awakeOnRestoreInf_PalmDisjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmDisjunction,"choco_awakeOnSup_PalmDisjunction"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmDisjunction,"palm_awakeOnRestoreSup_PalmDisjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmDisjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmDisjunction,"choco_awakeOnRem_PalmDisjunction"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmDisjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmDisjunction,"palm_awakeOnRestoreVal_PalmDisjunction"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmDisjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_PalmDisjunction,"choco_askIfEntailed_PalmDisjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmDisjunction,"choco_testIfSatisfied_PalmDisjunction"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmDisjunction,"choco_propagate_PalmDisjunction"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmDisjunction_palm,"choco_awake_PalmDisjunction_palm"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmDisjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsTrue_PalmDisjunction,"palm_whyIsTrue_PalmDisjunction"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmDisjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsFalse_PalmDisjunction,"palm_whyIsFalse_PalmDisjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmDisjunction,Kernel._integer),Kernel._any,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmDisjunction,"choco_awakeOnInst_PalmDisjunction"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmDisjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmDisjunction,"palm_checkPalm_PalmDisjunction"));
  
  { (palm._PalmGuard = ClaireClass::make("PalmGuard",choco._Guard,palm.it));
    CL_ADD_SLOT(palm._PalmGuard,PalmGuard,palm.needToAwakeC2,needToAwakeC2,Kernel._boolean,Kernel.cfalse);
    } 
  
  palm.initHook->addMethod(list::domain(1,palm._PalmGuard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmGuard,"palm_initHook_PalmGuard"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmGuard,"palm_takeIntoAccountStatusChange_PalmGuard"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmGuard),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmGuard_palm,"claire_self_print_PalmGuard_palm"));
  
  palm.needToAwake->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._boolean,
  	0,_function_(palm_needToAwake_PalmGuard,"palm_needToAwake_PalmGuard"));
  
  palm.setNeedToAwake->addMethod(list::domain(3,palm._PalmGuard,Kernel._integer,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setNeedToAwake_PalmGuard,"palm_setNeedToAwake_PalmGuard"));
  
  palm.checkStatusAndReport->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkStatusAndReport_PalmGuard,"palm_checkStatusAndReport_PalmGuard"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmGuard,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmGuard_palm,"palm_updateDataStructuresOnConstraint_PalmGuard_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmGuard,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmGuard_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmGuard_palm"));
  
  palm.checkConstraintState->addMethod(list::domain(1,palm._PalmGuard),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_checkConstraintState_PalmGuard,"palm_checkConstraintState_PalmGuard"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmGuard,"choco_awakeOnInf_PalmGuard"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmGuard,"palm_awakeOnRestoreInf_PalmGuard"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmGuard,"choco_awakeOnSup_PalmGuard"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmGuard,"palm_awakeOnRestoreSup_PalmGuard"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmGuard,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmGuard,"choco_awakeOnRem_PalmGuard"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmGuard,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmGuard,"palm_awakeOnRestoreVal_PalmGuard"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmGuard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmGuard,"choco_propagate_PalmGuard"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmGuard),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmGuard_palm,"choco_awake_PalmGuard_palm"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmGuard),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(choco_askIfEntailed_PalmGuard,"choco_askIfEntailed_PalmGuard"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmGuard),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmGuard,"choco_testIfSatisfied_PalmGuard"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmGuard,Kernel._integer),Kernel._any,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmGuard,"choco_awakeOnInst_PalmGuard"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmGuard),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmGuard,"palm_checkPalm_PalmGuard"));
  
  { (palm._PalmEquiv = ClaireClass::make("PalmEquiv",choco._Equiv,palm.it));
    CL_ADD_SLOT(palm._PalmEquiv,PalmEquiv,palm.isContradictory,isContradictory,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmEquiv,PalmEquiv,palm.needToAwakeC1,needToAwakeC1,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmEquiv,PalmEquiv,palm.needToAwakeNegC1,needToAwakeNegC1,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmEquiv,PalmEquiv,palm.needToAwakeC2,needToAwakeC2,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmEquiv,PalmEquiv,palm.needToAwakeNegC2,needToAwakeNegC2,Kernel._boolean,Kernel.cfalse);
    } 
  
  palm.initHook->addMethod(list::domain(1,palm._PalmEquiv),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmEquiv,"palm_initHook_PalmEquiv"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmEquiv,"palm_takeIntoAccountStatusChange_PalmEquiv"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmEquiv),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmEquiv_palm,"claire_self_print_PalmEquiv_palm"));
  
  palm.needToAwake->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._boolean,
  	0,_function_(palm_needToAwake_PalmEquiv,"palm_needToAwake_PalmEquiv"));
  
  palm.setNeedToAwake->addMethod(list::domain(3,palm._PalmEquiv,Kernel._integer,Kernel._boolean),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setNeedToAwake_PalmEquiv,"palm_setNeedToAwake_PalmEquiv"));
  
  palm.checkStatusAndReport->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkStatusAndReport_PalmEquiv,"palm_checkStatusAndReport_PalmEquiv"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmEquiv,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmEquiv_palm,"palm_updateDataStructuresOnConstraint_PalmEquiv_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmEquiv,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmEquiv_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmEquiv_palm"));
  
  palm.checkConstraintState->addMethod(list::domain(1,palm._PalmEquiv),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_checkConstraintState_PalmEquiv,"palm_checkConstraintState_PalmEquiv"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmEquiv,"choco_awakeOnInf_PalmEquiv"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmEquiv,"palm_awakeOnRestoreInf_PalmEquiv"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmEquiv,"choco_awakeOnSup_PalmEquiv"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmEquiv,"palm_awakeOnRestoreSup_PalmEquiv"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmEquiv,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmEquiv,"choco_awakeOnRem_PalmEquiv"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmEquiv,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmEquiv,"palm_awakeOnRestoreVal_PalmEquiv"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmEquiv),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmEquiv,"choco_propagate_PalmEquiv"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmEquiv),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmEquiv_palm,"choco_awake_PalmEquiv_palm"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmEquiv),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_PalmEquiv,"choco_askIfEntailed_PalmEquiv"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmEquiv),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmEquiv,"choco_testIfSatisfied_PalmEquiv"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmEquiv,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmEquiv,"choco_awakeOnInst_PalmEquiv"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmEquiv),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmEquiv,"palm_checkPalm_PalmEquiv"));
  
  (palm._PalmConjunction = ClaireClass::make("PalmConjunction",choco._Conjunction,palm.it));
  
  palm.initHook->addMethod(list::domain(1,palm._PalmConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmConjunction,"palm_initHook_PalmConjunction"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmConjunction,"palm_takeIntoAccountStatusChange_PalmConjunction"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmConjunction),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmConjunction_palm,"claire_self_print_PalmConjunction_palm"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmConjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmConjunction_palm,"palm_updateDataStructuresOnConstraint_PalmConjunction_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmConjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmConjunction_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmConjunction_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_PalmConjunction,"choco_awakeOnInf_PalmConjunction"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmConjunction,"palm_awakeOnRestoreInf_PalmConjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_PalmConjunction,"choco_awakeOnSup_PalmConjunction"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmConjunction,"palm_awakeOnRestoreSup_PalmConjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmConjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_PalmConjunction,"choco_awakeOnRem_PalmConjunction"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmConjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmConjunction,"palm_awakeOnRestoreVal_PalmConjunction"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_PalmConjunction,"choco_propagate_PalmConjunction"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmConjunction_palm,"choco_awake_PalmConjunction_palm"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmConjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+SLOT_UPDATE,_function_(choco_askIfEntailed_PalmConjunction,"choco_askIfEntailed_PalmConjunction"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmConjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsTrue_PalmConjunction,"palm_whyIsTrue_PalmConjunction"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmConjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsFalse_PalmConjunction,"palm_whyIsFalse_PalmConjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmConjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmConjunction,"choco_testIfSatisfied_PalmConjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmConjunction,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmConjunction,"choco_awakeOnInst_PalmConjunction"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmConjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmConjunction,"palm_checkPalm_PalmConjunction"));
  
  (palm._PalmLargeConjunction = ClaireClass::make("PalmLargeConjunction",choco._LargeConjunction,palm.it));
  
  palm.initHook->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmLargeConjunction,"palm_initHook_PalmLargeConjunction"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmLargeConjunction,"palm_takeIntoAccountStatusChange_PalmLargeConjunction"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmLargeConjunction_palm,"claire_self_print_PalmLargeConjunction_palm"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmLargeConjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmLargeConjunction_palm,"palm_updateDataStructuresOnConstraint_PalmLargeConjunction_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmLargeConjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmLargeConjunction_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmLargeConjunction_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnInf_PalmLargeConjunction,"choco_awakeOnInf_PalmLargeConjunction"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmLargeConjunction,"palm_awakeOnRestoreInf_PalmLargeConjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnSup_PalmLargeConjunction,"choco_awakeOnSup_PalmLargeConjunction"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmLargeConjunction,"palm_awakeOnRestoreSup_PalmLargeConjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmLargeConjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_awakeOnRem_PalmLargeConjunction,"choco_awakeOnRem_PalmLargeConjunction"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmLargeConjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmLargeConjunction,"palm_awakeOnRestoreVal_PalmLargeConjunction"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_propagate_PalmLargeConjunction,"choco_propagate_PalmLargeConjunction"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmLargeConjunction_palm,"choco_awake_PalmLargeConjunction_palm"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmLargeConjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_askIfEntailed_PalmLargeConjunction,"choco_askIfEntailed_PalmLargeConjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmLargeConjunction,"choco_testIfSatisfied_PalmLargeConjunction"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmLargeConjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsTrue_PalmLargeConjunction,"palm_whyIsTrue_PalmLargeConjunction"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmLargeConjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsFalse_PalmLargeConjunction,"palm_whyIsFalse_PalmLargeConjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmLargeConjunction,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmLargeConjunction,"choco_awakeOnInst_PalmLargeConjunction"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmLargeConjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmLargeConjunction,"palm_checkPalm_PalmLargeConjunction"));
  
  { (palm._PalmLargeDisjunction = ClaireClass::make("PalmLargeDisjunction",choco._LargeDisjunction,palm.it));
    CL_ADD_SLOT(palm._PalmLargeDisjunction,PalmLargeDisjunction,palm.needToAwake,needToAwake,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    CL_ADD_SLOT(palm._PalmLargeDisjunction,PalmLargeDisjunction,palm.isContradictory,isContradictory,Kernel._boolean,Kernel.cfalse);
    } 
  
  palm.initHook->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmLargeDisjunction,"palm_initHook_PalmLargeDisjunction"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmLargeDisjunction,"palm_takeIntoAccountStatusChange_PalmLargeDisjunction"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmLargeDisjunction_palm,"claire_self_print_PalmLargeDisjunction_palm"));
  
  palm.needToAwake->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(palm_needToAwake_PalmLargeDisjunction2,"palm_needToAwake_PalmLargeDisjunction2"));
  
  palm.setNeedToAwake->addMethod(list::domain(3,palm._PalmLargeDisjunction,Kernel._integer,Kernel._boolean),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_setNeedToAwake_PalmLargeDisjunction,"palm_setNeedToAwake_PalmLargeDisjunction"));
  
  palm.checkStatusAndReport->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkStatusAndReport_PalmLargeDisjunction,"palm_checkStatusAndReport_PalmLargeDisjunction"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmLargeDisjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmLargeDisjunction_palm,"palm_updateDataStructuresOnConstraint_PalmLargeDisjunction_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmLargeDisjunction,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmLargeDisjunction_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmLargeDisjunction_palm"));
  
  palm.checkConstraintState->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_checkConstraintState_PalmLargeDisjunction,"palm_checkConstraintState_PalmLargeDisjunction"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmLargeDisjunction,"choco_awakeOnInf_PalmLargeDisjunction"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmLargeDisjunction,"palm_awakeOnRestoreInf_PalmLargeDisjunction"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmLargeDisjunction,"choco_awakeOnSup_PalmLargeDisjunction"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmLargeDisjunction,"palm_awakeOnRestoreSup_PalmLargeDisjunction"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmLargeDisjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmLargeDisjunction,"choco_awakeOnRem_PalmLargeDisjunction"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmLargeDisjunction,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmLargeDisjunction,"palm_awakeOnRestoreVal_PalmLargeDisjunction"));
  
  choco.askIfEntailed->addMethod(list::domain(1,palm._PalmLargeDisjunction),U_type(Kernel._boolean,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC,_function_(choco_askIfEntailed_PalmLargeDisjunction,"choco_askIfEntailed_PalmLargeDisjunction"));
  
  choco.testIfSatisfied->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(choco_testIfSatisfied_PalmLargeDisjunction,"choco_testIfSatisfied_PalmLargeDisjunction"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_propagate_PalmLargeDisjunction,"choco_propagate_PalmLargeDisjunction"));
  
  choco.awake->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_awake_PalmLargeDisjunction_palm,"choco_awake_PalmLargeDisjunction_palm"));
  
  palm.whyIsTrue->addMethod(list::domain(1,palm._PalmLargeDisjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsTrue_PalmLargeDisjunction,"palm_whyIsTrue_PalmLargeDisjunction"));
  
  palm.whyIsFalse->addMethod(list::domain(1,palm._PalmLargeDisjunction),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+RETURN_ARG,_function_(palm_whyIsFalse_PalmLargeDisjunction,"palm_whyIsFalse_PalmLargeDisjunction"));
  
  choco.awakeOnInst->addMethod(list::domain(2,palm._PalmLargeDisjunction,Kernel._integer),Kernel._void,
  	SAFE_RESULT,_function_(choco_awakeOnInst_PalmLargeDisjunction,"choco_awakeOnInst_PalmLargeDisjunction"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmLargeDisjunction),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmLargeDisjunction,"palm_checkPalm_PalmLargeDisjunction"));
  
  { (palm._PalmCardinality = ClaireClass::make("PalmCardinality",choco._Cardinality,palm.it));
    CL_ADD_SLOT(palm._PalmCardinality,PalmCardinality,palm.needToAwake,needToAwake,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._boolean))))),CNULL);
    CL_ADD_SLOT(palm._PalmCardinality,PalmCardinality,palm.isContradictory,isContradictory,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmCardinality,PalmCardinality,palm.needToAwakeTrue,needToAwakeTrue,Kernel._boolean,Kernel.cfalse);
    CL_ADD_SLOT(palm._PalmCardinality,PalmCardinality,palm.needToAwakeFalse,needToAwakeFalse,Kernel._boolean,Kernel.cfalse);
    } 
  
  palm.initHook->addMethod(list::domain(1,palm._PalmCardinality),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_initHook_PalmCardinality,"palm_initHook_PalmCardinality"));
  
  palm.needToAwake->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._boolean,
  	RETURN_ARG,_function_(palm_needToAwake_PalmCardinality2,"palm_needToAwake_PalmCardinality2"));
  
  palm.setNeedToAwake->addMethod(list::domain(3,palm._PalmCardinality,Kernel._integer,Kernel._boolean),Kernel._void,
  	BAG_UPDATE+RETURN_ARG,_function_(palm_setNeedToAwake_PalmCardinality,"palm_setNeedToAwake_PalmCardinality"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmCardinality),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmCardinality_palm,"claire_self_print_PalmCardinality_palm"));
  
  palm.takeIntoAccountStatusChange->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_takeIntoAccountStatusChange_PalmCardinality,"palm_takeIntoAccountStatusChange_PalmCardinality"));
  
  palm.checkOnNbTrue->addMethod(list::domain(1,palm._PalmCardinality),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkOnNbTrue_PalmCardinality,"palm_checkOnNbTrue_PalmCardinality"));
  
  palm.checkOnNbFalse->addMethod(list::domain(1,palm._PalmCardinality),Kernel._void,
  	BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkOnNbFalse_PalmCardinality,"palm_checkOnNbFalse_PalmCardinality"));
  
  palm.checkStatusAndReport->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_checkStatusAndReport_PalmCardinality,"palm_checkStatusAndReport_PalmCardinality"));
  
  palm.explainTrueConstraints->addMethod(list::domain(1,palm._PalmCardinality),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explainTrueConstraints_PalmCardinality,"palm_explainTrueConstraints_PalmCardinality"));
  
  palm.explainFalseConstraints->addMethod(list::domain(1,palm._PalmCardinality),palm._Explanation,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_explainFalseConstraints_PalmCardinality,"palm_explainFalseConstraints_PalmCardinality"));
  
  palm.checkConstraintState->addMethod(list::domain(1,palm._PalmCardinality),Kernel._boolean,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_checkConstraintState_PalmCardinality,"palm_checkConstraintState_PalmCardinality"));
  
  palm.updateDataStructuresOnConstraint->addMethod(list::domain(5,palm._PalmCardinality,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnConstraint_PalmCardinality_palm,"palm_updateDataStructuresOnConstraint_PalmCardinality_palm"));
  
  palm.updateDataStructuresOnRestoreConstraint->addMethod(list::domain(5,palm._PalmCardinality,
    Kernel._integer,
    OBJECT(ClaireType,palm.SELECT->value),
    Kernel._integer,
    Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_updateDataStructuresOnRestoreConstraint_PalmCardinality_palm,"palm_updateDataStructuresOnRestoreConstraint_PalmCardinality_palm"));
  
  choco.awakeOnInf->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnInf_PalmCardinality,"choco_awakeOnInf_PalmCardinality"));
  
  palm.awakeOnRestoreInf->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreInf_PalmCardinality,"palm_awakeOnRestoreInf_PalmCardinality"));
  
  choco.awakeOnSup->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnSup_PalmCardinality,"choco_awakeOnSup_PalmCardinality"));
  
  palm.awakeOnRestoreSup->addMethod(list::domain(2,palm._PalmCardinality,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreSup_PalmCardinality,"palm_awakeOnRestoreSup_PalmCardinality"));
  
  choco.awakeOnRem->addMethod(list::domain(3,palm._PalmCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_awakeOnRem_PalmCardinality,"choco_awakeOnRem_PalmCardinality"));
  
  palm.awakeOnRestoreVal->addMethod(list::domain(3,palm._PalmCardinality,Kernel._integer,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_awakeOnRestoreVal_PalmCardinality,"palm_awakeOnRestoreVal_PalmCardinality"));
  
  palm.checkPalm->addMethod(list::domain(1,palm._PalmCardinality),Kernel._boolean,
  	NEW_ALLOC,_function_(palm_checkPalm_PalmCardinality,"palm_checkPalm_PalmCardinality"));
  
  palm.makePalmProblem->addMethod(list::domain(2,Kernel._string,Kernel._integer),palm._PalmProblem,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmProblem_string1,"palm_makePalmProblem_string1"));
  
  palm.makePalmProblem->addMethod(list::domain(3,Kernel._string,Kernel._integer,Kernel._integer),palm._PalmProblem,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmProblem_string2,"palm_makePalmProblem_string2"));
  
  palm.setObjective->addMethod(list::domain(2,palm._PalmProblem,palm._PalmIntVar),Kernel._void,
  	SLOT_UPDATE+RETURN_ARG,_function_(palm_setObjective_PalmProblem,"palm_setObjective_PalmProblem"));
  
  palm.setSolutionVars->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,palm._PalmIntVar)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_setSolutionVars_PalmProblem,"palm_setSolutionVars_PalmProblem"));
  
  choco.solutions->addMethod(list::domain(1,palm._PalmProblem),param_I_class(Kernel._list,choco._Solution),
  	0,_function_(choco_solutions_PalmProblem,"choco_solutions_PalmProblem"));
  
  palm.solve->addMethod(list::domain(3,palm._PalmProblem,nth_class1(Kernel._list,palm._PalmBranching),Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_solve_PalmProblem1,"palm_solve_PalmProblem1"));
  
  palm.solve->addMethod(list::domain(2,palm._PalmProblem,Kernel._boolean),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_solve_PalmProblem2,"palm_solve_PalmProblem2"));
  
  palm.solve->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,palm._PalmBranching)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_solve_PalmProblem3,"palm_solve_PalmProblem3"));
  
  palm.solve->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_solve_PalmProblem4,"palm_solve_PalmProblem4"));
  
  palm.minimize->addMethod(list::domain(3,palm._PalmProblem,palm._PalmIntVar,nth_class1(Kernel._list,palm._PalmBranching)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_minimize_PalmProblem1,"palm_minimize_PalmProblem1"));
  
  palm.minimize->addMethod(list::domain(2,palm._PalmProblem,palm._PalmIntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_minimize_PalmProblem2,"palm_minimize_PalmProblem2"));
  
  palm.minimize->addMethod(list::domain(5,palm._PalmProblem,
    palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,palm._PalmBranching)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_minimize_PalmProblem3,"palm_minimize_PalmProblem3"));
  
  palm.minimize->addMethod(list::domain(4,palm._PalmProblem,
    palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_minimize_PalmProblem4,"palm_minimize_PalmProblem4"));
  
  palm.maximize->addMethod(list::domain(3,palm._PalmProblem,palm._PalmIntVar,nth_class1(Kernel._list,palm._PalmBranching)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_maximize_PalmProblem1,"palm_maximize_PalmProblem1"));
  
  palm.maximize->addMethod(list::domain(2,palm._PalmProblem,palm._PalmIntVar),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_maximize_PalmProblem2,"palm_maximize_PalmProblem2"));
  
  palm.maximize->addMethod(list::domain(5,palm._PalmProblem,
    palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer,
    nth_class1(Kernel._list,palm._PalmBranching)),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_maximize_PalmProblem3,"palm_maximize_PalmProblem3"));
  
  palm.maximize->addMethod(list::domain(4,palm._PalmProblem,
    palm._PalmIntVar,
    Kernel._integer,
    Kernel._integer),Kernel._integer,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_maximize_PalmProblem4,"palm_maximize_PalmProblem4"));
  
  palm.makeBoundIntVar->addMethod(list::domain(4,palm._PalmProblem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer),palm._PalmIntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeBoundIntVar_PalmProblem,"palm_makeBoundIntVar_PalmProblem"));
  
  palm.makeIntVar->addMethod(list::domain(4,palm._PalmProblem,
    Kernel._string,
    Kernel._integer,
    Kernel._integer),palm._PalmIntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeIntVar_PalmProblem1,"palm_makeIntVar_PalmProblem1"));
  
  palm.makeIntVar->addMethod(list::domain(3,palm._PalmProblem,Kernel._string,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),palm._PalmIntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeIntVar_PalmProblem2,"palm_makeIntVar_PalmProblem2"));
  
  palm.makeIntVar->addMethod(list::domain(2,palm._PalmProblem,U_type(GC_OBJECT(ClaireType,nth_class1(Kernel._list,Kernel._integer)),GC_OBJECT(ClaireType,nth_class1(Kernel._set,Kernel._integer)))),palm._PalmIntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeIntVar_PalmProblem3,"palm_makeIntVar_PalmProblem3"));
  
  palm.makeConstantPalmIntVar->addMethod(list::domain(1,Kernel._integer),palm._PalmIntVar,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makeConstantPalmIntVar_integer,"palm_makeConstantPalmIntVar_integer"));
  
  choco.post->addMethod(list::domain(2,palm._PalmProblem,U_type(GC_OBJECT(ClaireType,U_type(palm._PalmUnIntConstraint,palm._PalmBinIntConstraint)),palm._PalmLargeIntConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_post_PalmProblem1,"choco_post_PalmProblem1"));
  
  choco.post->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_post_PalmProblem2,"choco_post_PalmProblem2"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,OBJECT(ClaireType,palm.AbstractConstraint->value),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_post_PalmProblem3,"choco_post_PalmProblem3"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,OBJECT(ClaireType,palm.Delayer->value),Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_post_PalmProblem4,"choco_post_PalmProblem4"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,nth_class1(Kernel._list,choco._AbstractConstraint),Kernel._integer),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_post_PalmProblem5,"choco_post_PalmProblem5"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_post_PalmProblem6,"choco_post_PalmProblem6"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,OBJECT(ClaireType,palm.Delayer->value),palm._Explanation),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(choco_post_PalmProblem7,"choco_post_PalmProblem7"));
  
  choco.post->addMethod(list::domain(3,palm._PalmProblem,nth_class1(Kernel._list,choco._AbstractConstraint),palm._Explanation),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_post_PalmProblem8,"choco_post_PalmProblem8"));
  
  palm.setMin->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_setMin_PalmIntVar,"palm_setMin_PalmIntVar"));
  
  palm.setMax->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_setMax_PalmIntVar,"palm_setMax_PalmIntVar"));
  
  palm.setVal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_setVal_PalmIntVar,"palm_setVal_PalmIntVar"));
  
  palm.remVal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remVal_PalmIntVar,"palm_remVal_PalmIntVar"));
  
  palm.remove->addMethod(list::domain(2,palm._PalmProblem,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remove_PalmProblem1,"palm_remove_PalmProblem1"));
  
  palm.remove->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._set,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remove_PalmProblem2,"palm_remove_PalmProblem2"));
  
  palm.remove->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,choco._AbstractConstraint)),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_remove_PalmProblem3,"palm_remove_PalmProblem3"));
  
  choco.propagate->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	NEW_ALLOC+RETURN_ARG,_function_(choco_propagate_PalmProblem,"choco_propagate_PalmProblem"));
  
  palm.makeConstraintTable->addMethod(list::domain(1,Kernel._type),nth_class2(Kernel._table,list::alloc(Kernel._any,1,_oid_(Kernel.range)),list::alloc(Kernel.emptySet,1,_oid_(set::alloc(Kernel.emptySet,1,_oid_(choco._AbstractConstraint))))),
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makeConstraintTable_type,"palm_makeConstraintTable_type"));
  
  palm.assign->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._AssignmentConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_assign_PalmIntVar,"palm_assign_PalmIntVar"));
  
  palm.negate->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	0,_function_(palm_negate_AbstractConstraint_palm,"palm_negate_AbstractConstraint_palm"));
  
  abstract_property(palm.negate);
  
  palm.negate->addMethod(list::domain(1,palm._AssignmentConstraint),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	RETURN_ARG,_function_(palm_negate_AssignmentConstraint_palm,"palm_negate_AssignmentConstraint_palm"));
  
  choco.opposite->addMethod(list::domain(1,palm._AssignmentConstraint),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_AssignmentConstraint,"choco_opposite_AssignmentConstraint"));
  
  palm.negate->addMethod(list::domain(1,palm._PalmLessOrEqualxc),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_negate_PalmLessOrEqualxc_palm,"palm_negate_PalmLessOrEqualxc_palm"));
  
  palm.negate->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),U_type(choco._AbstractConstraint,set::alloc(Kernel.emptySet,1,CNULL)),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(palm_negate_PalmGreaterOrEqualxc_palm,"palm_negate_PalmGreaterOrEqualxc_palm"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmGreaterOrEqualxc),palm._PalmLessOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmGreaterOrEqualxc,"choco_opposite_PalmGreaterOrEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmLessOrEqualxc),palm._PalmGreaterOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmLessOrEqualxc,"choco_opposite_PalmLessOrEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmEqualxc),palm._PalmNotEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmEqualxc,"choco_opposite_PalmEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmNotEqualxc),palm._PalmEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmNotEqualxc,"choco_opposite_PalmNotEqualxc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmEqualxyc),palm._PalmNotEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmEqualxyc,"choco_opposite_PalmEqualxyc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmNotEqualxyc),palm._PalmEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmNotEqualxyc,"choco_opposite_PalmNotEqualxyc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmGreaterOrEqualxyc),palm._PalmGreaterOrEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmGreaterOrEqualxyc,"choco_opposite_PalmGreaterOrEqualxyc"));
  
  choco.computeVarIdxInOpposite->addMethod(list::domain(2,palm._PalmGreaterOrEqualxyc,Kernel._integer),Kernel._integer,
  	0,_function_(choco_computeVarIdxInOpposite_PalmGreaterOrEqualxyc,"choco_computeVarIdxInOpposite_PalmGreaterOrEqualxyc"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmGuard),palm._PalmConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmGuard,"choco_opposite_PalmGuard"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmConjunction),palm._PalmDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmConjunction,"choco_opposite_PalmConjunction"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmDisjunction),palm._PalmConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmDisjunction,"choco_opposite_PalmDisjunction"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmLargeDisjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmLargeDisjunction,"choco_opposite_PalmLargeDisjunction"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmLargeConjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmLargeConjunction,"choco_opposite_PalmLargeConjunction"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmEquiv),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmEquiv,"choco_opposite_PalmEquiv"));
  
  choco.opposite->addMethod(list::domain(1,palm._PalmLinComb),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_opposite_PalmLinComb,"choco_opposite_PalmLinComb"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmGreaterOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar1,"claire_>=_PalmIntVar1"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmGreaterOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmIntVar1,"claire_>_PalmIntVar1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmLessOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmIntVar1,"claire_<=_PalmIntVar1"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmLessOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmIntVar1,"claire_<_PalmIntVar1"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar1,"claire_==_PalmIntVar1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmNotEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmIntVar1,"claire_!==_PalmIntVar1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmLessOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer6,"claire_>=_integer6"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmLessOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer6,"claire_>_integer6"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmGreaterOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer7,"claire_<=_integer7"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmGreaterOrEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer6,"claire_<_integer6"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer8,"claire_==_integer8"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmNotEqualxc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer5,"claire_!==_integer5"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar2,"claire_==_PalmIntVar2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmNotEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmIntVar2,"claire_!==_PalmIntVar2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmGreaterOrEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmIntVar2,"claire_<=_PalmIntVar2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmGreaterOrEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar2,"claire_>=_PalmIntVar2"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmGreaterOrEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmIntVar2,"claire_>_PalmIntVar2"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmGreaterOrEqualxyc,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmIntVar2,"claire_<_PalmIntVar2"));
  
  { (palm._PalmTempTerm = ClaireClass::make("PalmTempTerm",palm._PalmTools,palm.it));
    CL_ADD_SLOT(palm._PalmTempTerm,PalmTempTerm,choco.cste,cste,Kernel._integer,0);
    } 
  
  { (palm._PalmUnTerm = ClaireClass::make("PalmUnTerm",palm._PalmTempTerm,palm.it));
    CL_ADD_SLOT(palm._PalmUnTerm,PalmUnTerm,choco.v1,v1,palm._PalmIntVar,CNULL);
    CL_ADD_SLOT(palm._PalmUnTerm,PalmUnTerm,palm.sign1,sign1,Kernel._boolean,Kernel.ctrue);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmUnTerm),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmUnTerm_palm,"claire_self_print_PalmUnTerm_palm"));
  
  { (palm._PalmBinTerm = ClaireClass::make("PalmBinTerm",palm._PalmTempTerm,palm.it));
    CL_ADD_SLOT(palm._PalmBinTerm,PalmBinTerm,choco.v1,v1,palm._PalmIntVar,CNULL);
    CL_ADD_SLOT(palm._PalmBinTerm,PalmBinTerm,choco.v2,v2,palm._PalmIntVar,CNULL);
    CL_ADD_SLOT(palm._PalmBinTerm,PalmBinTerm,palm.sign1,sign1,Kernel._boolean,Kernel.ctrue);
    CL_ADD_SLOT(palm._PalmBinTerm,PalmBinTerm,palm.sign2,sign2,Kernel._boolean,Kernel.ctrue);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmBinTerm),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmBinTerm_palm,"claire_self_print_PalmBinTerm_palm"));
  
  { (palm._PalmLinTerm = ClaireClass::make("PalmLinTerm",palm._PalmTempTerm,palm.it));
    CL_ADD_SLOT(palm._PalmLinTerm,PalmLinTerm,palm.lcoeff,lcoeff,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer))))),CNULL);
    CL_ADD_SLOT(palm._PalmLinTerm,PalmLinTerm,palm.lvars,lvars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmIntVar))))),CNULL);
    } 
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmLinTerm),Kernel._any,
  	NEW_ALLOC+SLOT_UPDATE,_function_(claire_self_print_PalmLinTerm_palm,"claire_self_print_PalmLinTerm_palm"));
  
  { (palm.e_dashscalar = (operation *) Kernel._operation->instantiate("e-scalar",claire.it));
    (palm.e_dashscalar->precedence = Kernel._star->precedence);
    ;} 
  
  palm.e_dashscalar->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire_e_dashscalar_list,"claire_e-scalar_list"));
  
  palm.e_dashsumVars->addMethod(list::domain(1,nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(palm_e_dashsumVars_list,"palm_e-sumVars_list"));
  
  Kernel._star->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__star_integer4,"claire_*_integer4"));
  
  Kernel._star->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__star_PalmIntVar,"claire_*_PalmIntVar"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmUnTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmIntVar1,"claire_+_PalmIntVar1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmUnTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_integer6,"claire_+_integer6"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmBinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmIntVar2,"claire_+_PalmIntVar2"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),palm._PalmUnTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmUnTerm1,"claire_+_PalmUnTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),palm._PalmUnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer7,"claire_+_integer7"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),palm._PalmBinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmUnTerm2,"claire_+_PalmUnTerm2"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),palm._PalmBinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_PalmIntVar3,"claire_+_PalmIntVar3"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),palm._PalmBinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmUnTerm3,"claire_+_PalmUnTerm3"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),palm._PalmBinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmBinTerm1,"claire_+_PalmBinTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),palm._PalmBinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer8,"claire_+_integer8"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmBinTerm2,"claire_+_PalmBinTerm2"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_PalmIntVar4,"claire_+_PalmIntVar4"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmBinTerm3,"claire_+_PalmBinTerm3"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__plus_PalmUnTerm4,"claire_+_PalmUnTerm4"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmBinTerm4,"claire_+_PalmBinTerm4"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),palm._PalmLinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmLinTerm1,"claire_+_PalmLinTerm1"));
  
  Core._plus->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_integer9,"claire_+_integer9"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),palm._PalmLinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmLinTerm2,"claire_+_PalmLinTerm2"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmIntVar5,"claire_+_PalmIntVar5"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),palm._PalmLinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmLinTerm3,"claire_+_PalmLinTerm3"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmUnTerm5,"claire_+_PalmUnTerm5"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmLinTerm4,"claire_+_PalmLinTerm4"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__plus_PalmBinTerm5,"claire_+_PalmBinTerm5"));
  
  Core._plus->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC,_function_(claire__plus_PalmLinTerm5,"claire_+_PalmLinTerm5"));
  
  Kernel._dash->addMethod(list::domain(1,palm._PalmIntVar),palm._PalmUnTerm,
  	NEW_ALLOC,_function_(claire__dash_PalmIntVar1,"claire_-_PalmIntVar1"));
  
  Kernel._dash->addMethod(list::domain(1,palm._PalmUnTerm),palm._PalmUnTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmUnTerm1,"claire_-_PalmUnTerm1"));
  
  Kernel._dash->addMethod(list::domain(1,palm._PalmBinTerm),palm._PalmBinTerm,
  	SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmBinTerm1,"claire_-_PalmBinTerm1"));
  
  Kernel._dash->addMethod(list::domain(1,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm1,"claire_-_PalmLinTerm1"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmIntVar,Kernel._integer),palm._PalmUnTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_PalmIntVar2,"claire_-_PalmIntVar2"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),palm._PalmUnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmUnTerm2,"claire_-_PalmUnTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),palm._PalmBinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmBinTerm2,"claire_-_PalmBinTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm2,"claire_-_PalmLinTerm2"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,palm._PalmIntVar),palm._PalmUnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer8,"claire_-_integer8"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmIntVar),palm._PalmBinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_PalmIntVar3,"claire_-_PalmIntVar3"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),palm._PalmBinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_PalmUnTerm3,"claire_-_PalmUnTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),palm._PalmLinTerm,
  	NEW_ALLOC+RETURN_ARG,_function_(claire__dash_PalmBinTerm3,"claire_-_PalmBinTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm3,"claire_-_PalmLinTerm3"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),palm._PalmUnTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer9,"claire_-_integer9"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),palm._PalmBinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmIntVar4,"claire_-_PalmIntVar4"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),palm._PalmBinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmUnTerm4,"claire_-_PalmUnTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmBinTerm4,"claire_-_PalmBinTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm4,"claire_-_PalmLinTerm4"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),palm._PalmBinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer10,"claire_-_integer10"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmIntVar5,"claire_-_PalmIntVar5"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmUnTerm5,"claire_-_PalmUnTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmBinTerm5,"claire_-_PalmBinTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm5,"claire_-_PalmLinTerm5"));
  
  Kernel._dash->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_integer11,"claire_-_integer11"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmIntVar6,"claire_-_PalmIntVar6"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmUnTerm6,"claire_-_PalmUnTerm6"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmBinTerm6,"claire_-_PalmBinTerm6"));
  
  Kernel._dash->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),palm._PalmLinTerm,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire__dash_PalmLinTerm6,"claire_-_PalmLinTerm6"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer7,"claire_>=_integer7"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer8,"claire_>=_integer8"));
  
  Kernel._sup_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_integer9,"claire_>=_integer9"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar3,"claire_>=_PalmIntVar3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar4,"claire_>=_PalmIntVar4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar5,"claire_>=_PalmIntVar5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmUnTerm1,"claire_>=_PalmUnTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmUnTerm2,"claire_>=_PalmUnTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmBinTerm1,"claire_>=_PalmBinTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),palm._PalmUnIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmUnTerm3,"claire_>=_PalmUnTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmUnTerm4,"claire_>=_PalmUnTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmUnTerm5,"claire_>=_PalmUnTerm5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmBinTerm2,"claire_>=_PalmBinTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmBinTerm3,"claire_>=_PalmBinTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmBinTerm4,"claire_>=_PalmBinTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmBinTerm5,"claire_>=_PalmBinTerm5"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmLinTerm1,"claire_>=_PalmLinTerm1"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmLinTerm2,"claire_>=_PalmLinTerm2"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmLinTerm3,"claire_>=_PalmLinTerm3"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmLinTerm4,"claire_>=_PalmLinTerm4"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmLinTerm5,"claire_>=_PalmLinTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer7,"claire_>_integer7"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer8,"claire_>_integer8"));
  
  Kernel._sup->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_integer9,"claire_>_integer9"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmIntVar3,"claire_>_PalmIntVar3"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmIntVar4,"claire_>_PalmIntVar4"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmIntVar5,"claire_>_PalmIntVar5"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmUnTerm1,"claire_>_PalmUnTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmBinTerm1,"claire_>_PalmBinTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmLinTerm1,"claire_>_PalmLinTerm1"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmUnTerm2,"claire_>_PalmUnTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmBinTerm2,"claire_>_PalmBinTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmLinTerm2,"claire_>_PalmLinTerm2"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmUnTerm3,"claire_>_PalmUnTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmBinTerm3,"claire_>_PalmBinTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmLinTerm3,"claire_>_PalmLinTerm3"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmUnTerm4,"claire_>_PalmUnTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmBinTerm4,"claire_>_PalmBinTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmLinTerm4,"claire_>_PalmLinTerm4"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmUnTerm5,"claire_>_PalmUnTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmBinTerm5,"claire_>_PalmBinTerm5"));
  
  Kernel._sup->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_PalmLinTerm5,"claire_>_PalmLinTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer8,"claire_<=_integer8"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer9,"claire_<=_integer9"));
  
  Kernel._inf_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_integer10,"claire_<=_integer10"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmIntVar3,"claire_<=_PalmIntVar3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC,_function_(claire__inf_equal_PalmIntVar4,"claire_<=_PalmIntVar4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmIntVar5,"claire_<=_PalmIntVar5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmUnTerm1,"claire_<=_PalmUnTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmBinTerm1,"claire_<=_PalmBinTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmLinTerm1,"claire_<=_PalmLinTerm1"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmUnTerm2,"claire_<=_PalmUnTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmBinTerm2,"claire_<=_PalmBinTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmLinTerm2,"claire_<=_PalmLinTerm2"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmUnTerm3,"claire_<=_PalmUnTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmBinTerm3,"claire_<=_PalmBinTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmLinTerm3,"claire_<=_PalmLinTerm3"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmUnTerm4,"claire_<=_PalmUnTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmBinTerm4,"claire_<=_PalmBinTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmLinTerm4,"claire_<=_PalmLinTerm4"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmUnTerm5,"claire_<=_PalmUnTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmBinTerm5,"claire_<=_PalmBinTerm5"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmLinTerm5,"claire_<=_PalmLinTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer7,"claire_<_integer7"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer8,"claire_<_integer8"));
  
  Kernel._inf->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_integer9,"claire_<_integer9"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmIntVar3,"claire_<_PalmIntVar3"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmIntVar4,"claire_<_PalmIntVar4"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmIntVar5,"claire_<_PalmIntVar5"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmUnTerm1,"claire_<_PalmUnTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmBinTerm1,"claire_<_PalmBinTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmLinTerm1,"claire_<_PalmLinTerm1"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmUnTerm2,"claire_<_PalmUnTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmBinTerm2,"claire_<_PalmBinTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmLinTerm2,"claire_<_PalmLinTerm2"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmUnTerm3,"claire_<_PalmUnTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmBinTerm3,"claire_<_PalmBinTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmLinTerm3,"claire_<_PalmLinTerm3"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmUnTerm4,"claire_<_PalmUnTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmBinTerm4,"claire_<_PalmBinTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmLinTerm4,"claire_<_PalmLinTerm4"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmUnTerm5,"claire_<_PalmUnTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmBinTerm5,"claire_<_PalmBinTerm5"));
  
  Kernel._inf->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_PalmLinTerm5,"claire_<_PalmLinTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer9,"claire_==_integer9"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer10,"claire_==_integer10"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer11,"claire_==_integer11"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar3,"claire_==_PalmIntVar3"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar4,"claire_==_PalmIntVar4"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar5,"claire_==_PalmIntVar5"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmUnTerm1,"claire_==_PalmUnTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmUnTerm2,"claire_==_PalmUnTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmBinTerm1,"claire_==_PalmBinTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),palm._PalmUnIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmUnTerm3,"claire_==_PalmUnTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmUnTerm4,"claire_==_PalmUnTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),palm._PalmBinIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmUnTerm5,"claire_==_PalmUnTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmBinTerm2,"claire_==_PalmBinTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmBinTerm3,"claire_==_PalmBinTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmBinTerm4,"claire_==_PalmBinTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmBinTerm5,"claire_==_PalmBinTerm5"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmLinTerm1,"claire_==_PalmLinTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmLinTerm2,"claire_==_PalmLinTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmLinTerm3,"claire_==_PalmLinTerm3"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmLinTerm4,"claire_==_PalmLinTerm4"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmLinTerm5,"claire_==_PalmLinTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer6,"claire_!==_integer6"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmBinTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer7,"claire_!==_integer7"));
  
  choco._I_equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmLinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_integer8,"claire_!==_integer8"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmUnTerm),OBJECT(ClaireType,palm.AbstractConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmIntVar3,"claire_!==_PalmIntVar3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmBinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmIntVar4,"claire_!==_PalmIntVar4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmLinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmIntVar5,"claire_!==_PalmIntVar5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmBinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmUnTerm1,"claire_!==_PalmUnTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmLinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmUnTerm2,"claire_!==_PalmUnTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmLinTerm),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmBinTerm1,"claire_!==_PalmBinTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,Kernel._integer),palm._PalmUnIntConstraint,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmUnTerm3,"claire_!==_PalmUnTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmIntVar),OBJECT(ClaireType,palm.IntConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmUnTerm4,"claire_!==_PalmUnTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmUnTerm,palm._PalmUnTerm),OBJECT(ClaireType,palm.IntConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmUnTerm5,"claire_!==_PalmUnTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,Kernel._integer),OBJECT(ClaireType,palm.IntConstraint->value),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmBinTerm2,"claire_!==_PalmBinTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmBinTerm3,"claire_!==_PalmBinTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmBinTerm4,"claire_!==_PalmBinTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmBinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmBinTerm5,"claire_!==_PalmBinTerm5"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,Kernel._integer),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmLinTerm1,"claire_!==_PalmLinTerm1"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmIntVar),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmLinTerm2,"claire_!==_PalmLinTerm2"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmUnTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmLinTerm3,"claire_!==_PalmLinTerm3"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmBinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmLinTerm4,"claire_!==_PalmLinTerm4"));
  
  choco._I_equal_equal->addMethod(list::domain(2,palm._PalmLinTerm,palm._PalmLinTerm),palm._PalmLinComb,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__I_equal_equal_PalmLinTerm5,"claire_!==_PalmLinTerm5"));
  
  { (palm._PalmOccurTerm = ClaireClass::make("PalmOccurTerm",choco._Term,palm.it));
    CL_ADD_SLOT(palm._PalmOccurTerm,PalmOccurTerm,choco.target,target,Kernel._integer,CNULL);
    CL_ADD_SLOT(palm._PalmOccurTerm,PalmOccurTerm,palm.lvars,lvars,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(palm._PalmIntVar))))),CNULL);
    } 
  
  palm.makePalmOccurConstraint->addMethod(list::domain(4,palm._PalmOccurTerm,
    palm._PalmIntVar,
    Kernel._boolean,
    Kernel._boolean),palm._PalmOccurrence,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_makePalmOccurConstraint_PalmOccurTerm,"palm_makePalmOccurConstraint_PalmOccurTerm"));
  
  palm.occur->addMethod(list::domain(2,Kernel._integer,nth_class1(Kernel._list,palm._PalmIntVar)),palm._PalmOccurTerm,
  	NEW_ALLOC,_function_(palm_occur_integer,"palm_occur_integer"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmOccurTerm,palm._PalmIntVar),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmOccurTerm,"claire_==_PalmOccurTerm"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmOccurTerm),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar6,"claire_==_PalmIntVar6"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmOccurTerm,palm._PalmIntVar),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmOccurTerm,"claire_>=_PalmOccurTerm"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmOccurTerm,palm._PalmIntVar),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmOccurTerm,"claire_<=_PalmOccurTerm"));
  
  Kernel._sup_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmOccurTerm),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__sup_equal_PalmIntVar6,"claire_>=_PalmIntVar6"));
  
  Kernel._inf_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmOccurTerm),palm._PalmOccurrence,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__inf_equal_PalmIntVar6,"claire_<=_PalmIntVar6"));
  
  (palm._PalmEltTerm = ClaireClass::make("PalmEltTerm",choco._EltTerm,palm.it));
  
  choco.getNth->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),palm._PalmUnTerm),palm._PalmEltTerm,
  	NEW_ALLOC,_function_(choco_getNth_list3,"choco_getNth_list3"));
  
  choco.getNth->addMethod(list::domain(2,nth_class1(Kernel._list,Kernel._integer),palm._PalmIntVar),palm._PalmEltTerm,
  	NEW_ALLOC,_function_(choco_getNth_list4,"choco_getNth_list4"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmEltTerm,palm._PalmIntVar),palm._PalmElt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmEltTerm1,"claire_==_PalmEltTerm1"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmIntVar,palm._PalmEltTerm),palm._PalmElt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmIntVar7,"claire_==_PalmIntVar7"));
  
  choco._equal_equal->addMethod(list::domain(2,palm._PalmEltTerm,Kernel._integer),palm._PalmElt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_PalmEltTerm2,"claire_==_PalmEltTerm2"));
  
  choco._equal_equal->addMethod(list::domain(2,Kernel._integer,palm._PalmEltTerm),palm._PalmElt,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire__equal_equal_integer12,"claire_==_integer12"));
  
  { (palm.e_dashor = (operation *) Kernel._operation->instantiate("e-or",claire.it));
    (palm.e_dashor->precedence = Core.ClaireOr->precedence);
    ;} 
  
  { (palm.e_dashimplies = (operation *) Kernel._operation->instantiate("e-implies",claire.it));
    ;} 
  
  { (palm.e_dashiff = (operation *) Kernel._operation->instantiate("e-iff",claire.it));
    (palm.e_dashiff->precedence = Core._and->precedence);
    ;} 
  
  { (palm.e_dashand = (operation *) Kernel._operation->instantiate("e-and",claire.it));
    (palm.e_dashand->precedence = Core._and->precedence);
    ;} 
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmLargeDisjunction,palm._PalmLargeDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashor_PalmLargeDisjunction1,"claire_e-or_PalmLargeDisjunction1"));
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmDisjunction,palm._PalmDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashor_PalmDisjunction1,"claire_e-or_PalmDisjunction1"));
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmLargeDisjunction,palm._PalmDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashor_PalmLargeDisjunction2,"claire_e-or_PalmLargeDisjunction2"));
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmDisjunction,palm._PalmLargeDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashor_PalmDisjunction2,"claire_e-or_PalmDisjunction2"));
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmDisjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmConjunction)),palm._PalmLargeConjunction)),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashor_PalmDisjunction3,"claire_e-or_PalmDisjunction3"));
  
  palm.e_dashor->addMethod(list::domain(2,palm._PalmLargeDisjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmConjunction)),palm._PalmLargeConjunction)),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashor_PalmLargeDisjunction3,"claire_e-or_PalmLargeDisjunction3"));
  
  palm.e_dashor->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmConjunction)),palm._PalmLargeConjunction),palm._PalmDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashor_AbstractConstraint1,"claire_e-or_AbstractConstraint1"));
  
  palm.e_dashor->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmConjunction)),palm._PalmLargeConjunction),palm._PalmLargeDisjunction),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashor_AbstractConstraint2,"claire_e-or_AbstractConstraint2"));
  
  palm.e_dashor->addMethod(list::domain(2,U_type(choco._IntConstraint,choco._CompositeConstraint),U_type(choco._IntConstraint,choco._CompositeConstraint)),palm._PalmDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashor_AbstractConstraint3,"claire_e-or_AbstractConstraint3"));
  
  palm.e_dashor->addMethod(list::domain(1,nth_class1(Kernel._list,choco._AbstractConstraint)),palm._PalmLargeDisjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashor_list,"claire_e-or_list"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmConjunction,palm._PalmConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashand_PalmConjunction1,"claire_e-and_PalmConjunction1"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmLargeConjunction,palm._PalmLargeConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashand_PalmLargeConjunction1,"claire_e-and_PalmLargeConjunction1"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmLargeConjunction,palm._PalmConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashand_PalmLargeConjunction2,"claire_e-and_PalmLargeConjunction2"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmConjunction,palm._PalmLargeConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashand_PalmConjunction2,"claire_e-and_PalmConjunction2"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmConjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmDisjunction)),palm._PalmLargeDisjunction)),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashand_PalmConjunction3,"claire_e-and_PalmConjunction3"));
  
  palm.e_dashand->addMethod(list::domain(2,palm._PalmLargeConjunction,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmDisjunction)),palm._PalmLargeDisjunction)),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashand_PalmLargeConjunction3,"claire_e-and_PalmLargeConjunction3"));
  
  palm.e_dashand->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmDisjunction)),palm._PalmLargeDisjunction),palm._PalmConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashand_AbstractConstraint1,"claire_e-and_AbstractConstraint1"));
  
  palm.e_dashand->addMethod(list::domain(2,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(GC_OBJECT(ClaireType,U_type(choco._IntConstraint,palm._PalmCardinality)),palm._PalmGuard)),palm._PalmEquiv)),palm._PalmDisjunction)),palm._PalmLargeDisjunction),palm._PalmLargeConjunction),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG,_function_(claire_e_dashand_AbstractConstraint2,"claire_e-and_AbstractConstraint2"));
  
  palm.e_dashand->addMethod(list::domain(2,U_type(choco._IntConstraint,choco._CompositeConstraint),U_type(choco._IntConstraint,choco._CompositeConstraint)),palm._PalmConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashand_AbstractConstraint3,"claire_e-and_AbstractConstraint3"));
  
  palm.e_dashand->addMethod(list::domain(1,nth_class1(Kernel._list,choco._AbstractConstraint)),palm._PalmLargeConjunction,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashand_list,"claire_e-and_list"));
  
  palm.e_dashimplies->addMethod(list::domain(2,U_type(choco._IntConstraint,choco._CompositeConstraint),U_type(choco._IntConstraint,choco._CompositeConstraint)),U_type(palm._PalmDisjunction,palm._PalmLargeDisjunction),
  	NEW_ALLOC+RETURN_ARG,_function_(claire_e_dashimplies_AbstractConstraint,"claire_e-implies_AbstractConstraint"));
  
  palm.e_dashifThen->addMethod(list::domain(2,U_type(choco._IntConstraint,choco._CompositeConstraint),U_type(choco._IntConstraint,choco._CompositeConstraint)),palm._PalmGuard,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashifThen_AbstractConstraint,"palm_e-ifThen_AbstractConstraint"));
  
  palm.e_dashiff->addMethod(list::domain(2,U_type(choco._IntConstraint,choco._CompositeConstraint),U_type(choco._IntConstraint,choco._CompositeConstraint)),palm._PalmEquiv,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_e_dashiff_AbstractConstraint,"claire_e-iff_AbstractConstraint"));
  
  palm.makePalmCardinalityConstraint->addMethod(list::domain(4,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),
    palm._PalmIntVar,
    Kernel._boolean,
    Kernel._boolean),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_makePalmCardinalityConstraint_list,"palm_makePalmCardinalityConstraint_list"));
  
  palm.e_dashcard->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),palm._PalmIntVar),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashcard_list1,"palm_e-card_list1"));
  
  palm.e_dashatleast->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),palm._PalmIntVar),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashatleast_list1,"palm_e-atleast_list1"));
  
  palm.e_dashatmost->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),palm._PalmIntVar),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashatmost_list1,"palm_e-atmost_list1"));
  
  palm.e_dashcard->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),Kernel._integer),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashcard_list2,"palm_e-card_list2"));
  
  palm.e_dashatleast->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),Kernel._integer),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashatleast_list2,"palm_e-atleast_list2"));
  
  palm.e_dashatmost->addMethod(list::domain(2,nth_class1(Kernel._list,GC_OBJECT(ClaireType,U_type(choco._IntConstraint,choco._CompositeConstraint))),Kernel._integer),palm._PalmCardinality,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_e_dashatmost_list2,"palm_e-atmost_list2"));
  
  palm.startInfoBox->addMethod(list::domain(3,palm._PalmProblem,Kernel._string,Kernel._string),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_startInfoBox_PalmProblem,"palm_startInfoBox_PalmProblem"));
  
  palm.endInfoBox->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	0,_function_(palm_endInfoBox_PalmProblem,"palm_endInfoBox_PalmProblem"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmUserFriendlyBox),Kernel._any,
  	NEW_ALLOC,_function_(claire_self_print_PalmUserFriendlyBox_palm,"claire_self_print_PalmUserFriendlyBox_palm"));
  
  Kernel.self_print->addMethod(list::domain(1,palm._PalmProblem),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(claire_self_print_PalmProblem_palm,"claire_self_print_PalmProblem_palm"));
  
  palm.showInfoBox->addMethod(list::domain(2,Kernel._integer,palm._PalmUserFriendlyBox),Kernel._any,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_showInfoBox_integer,"palm_showInfoBox_integer"));
  
  palm.project->addMethod(list::domain(2,OBJECT(ClaireType,palm.AbstractConstraint->value),palm._PalmProblem),palm._PalmUserFriendlyBox,
  	0,_function_(palm_project_AbstractConstraint,"palm_project_AbstractConstraint"));
  
  palm.getConstraints->addMethod(list::domain(1,palm._PalmUserFriendlyBox),nth_class1(Kernel._set,choco._AbstractConstraint),
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(palm_getConstraints_PalmUserFriendlyBox,"palm_getConstraints_PalmUserFriendlyBox"));
  
  palm.setUserFriendly->addMethod(list::domain(1,Kernel._boolean),Kernel._any,
  	SAFE_RESULT,_function_(palm_setUserFriendly_boolean,"palm_setUserFriendly_boolean"));
  
  palm.setUserRepresentation->addMethod(list::domain(2,palm._PalmProblem,nth_class1(Kernel._list,Kernel._string)),Kernel._void,
  	NEW_ALLOC+SLOT_UPDATE+RETURN_ARG,_function_(palm_setUserRepresentation_PalmProblem,"palm_setUserRepresentation_PalmProblem"));
  
  choco.post->addMethod(list::domain(2,palm._PalmProblem,palm._PalmUserFriendlyBox),Kernel._void,
  	NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE,_function_(choco_post_PalmProblem9,"choco_post_PalmProblem9"));
  
  palm.checkPalm->addMethod(list::domain(1,OBJECT(ClaireType,palm.AbstractConstraint->value)),Kernel._boolean,
  	0,_function_(palm_checkPalm_AbstractConstraint,"palm_checkPalm_AbstractConstraint"));
  
  palm.checkFullPalm->addMethod(list::domain(1,palm._PalmProblem),Kernel._boolean,
  	NEW_ALLOC+SLOT_UPDATE,_function_(palm_checkFullPalm_PalmProblem,"palm_checkFullPalm_PalmProblem"));
  
  GC_UNBIND;} 

