// {m in property | ((defined(m.name) = palm) & (length(m.restrictions) = 0))}

// This file creates local copies of the useful choco identifiers in the Palm namespace
//  this supports much lighter code writing for Palm (not having to prefix everyting with choco/)

// ---- redefining constants
MININT    :: choco/MININT
MAXINT    :: choco/MAXINT
UNKNOWNINT	:: choco/UNKNOWNINT

// ---- redefining global objects
// <grt> the choco constants do not exist anymore
// <grt> Creating new instances directly in the properties
//DUMMY_BOUND_UPDATE  :: choco/DUMMY_BOUND_UPDATE
//DUMMY_INSTANTIATION :: choco/DUMMY_INSTANTIATION
//DUMMY_VALUE_REMOVAL :: choco/DUMMY_VALUE_REMOVAL
//DUMMY_CONST_AWAKE   :: choco/DUMMY_CONST_AWAKE

// ---- redefining classes
//  (up to now, this works for slot typing, method argument typing, 
//   but not for inheritance declarations, object creation)
Ephemeral                      :: choco/Ephemeral
Problem                        :: choco/Problem
Solution                       :: choco/Solution
AbstractConstraint             :: choco/AbstractConstraint 
IntConstraint                  ::   choco/IntConstraint
UnIntConstraint                ::     choco/UnIntConstraint
BinIntConstraint               ::     choco/BinIntConstraint
Elt                            ::       choco/Elt
TernIntConstraint              ::     choco/TernIntConstraint
LargeIntConstraint             ::     choco/LargeIntConstraint
LinComb                        ::        choco/LinComb
Delayer                        ::     choco/Delayer
CompositeConstraint            ::   choco/CompositeConstraint
BinCompositeConstraint         ::     choco/BinCompositeConstraint
BinBoolConstraint              ::       choco/BinBoolConstraint
BinBoolConstraintWCounterOpp   ::         choco/BinBoolConstraintWCounterOpp
LargeCompositeConstraint       ::     choco/LargeCompositeConstraint
LargeBoolConstraint            ::       choco/LargeBoolConstraint
LargeConjunction               :: choco/LargeConjunction
LargeDisjunction               :: choco/LargeDisjunction
LargeBoolConstraintWCounterOpp ::         choco/LargeBoolConstraintWCounterOpp
AbstractVar                    :: choco/AbstractVar
IntVar                         ::   choco/IntVar           
AbstractDomain                 :: choco/AbstractDomain
AbstractIntDomain              ::   choco/AbstractIntDomain
LinkedListIntDomain            ::     choco/LinkedListIntDomain
PropagationEvent               :: choco/PropagationEvent
ConstAwakeEvent                ::  choco/ConstAwakeEvent
// <grt> not useful anymore...
// IntVarEvent                    ::   choco/IntVarEvent
Instantiation                  ::     choco/Instantiation
ValueRemovals                  ::     choco/ValueRemovals
BoundUpdate                    ::     choco/BoundUpdate    
IncInf                         ::       choco/IncInf
DecSup                         ::       choco/DecSup
LogicEngine                    :: choco/LogicEngine
PropagationEngine              ::   choco/PropagationEngine
ChocEngine                     ::     choco/ChocEngine
Solver                         :: choco/Solver
GlobalSearchSolver             :: choco/GlobalSearchSolver
LocalSearchSolver              :: choco/LocalSearchSolver
EventCollection                :: choco/EventCollection
InstantiationStack             ::   choco/InstantiationStack
EventQueue                     ::   choco/EventQueue
BoundEventQueue                ::     choco/BoundEventQueue
RemovalEventQueue              ::     choco/RemovalEventQueue
ConstAwakeEventQueue           ::     choco/ConstAwakeEventQueue
Util                           :: choco/Util
Matrix                         ::   choco/Matrix
Matrix2D                       ::     choco/Matrix2D
IntMatrix2D                    ::       choco/IntMatrix2D
BoolMatrix2D                   ::       choco/BoolMatrix2D
MatrixND                       ::     choco/MatrixND
BoolMatrixND                   ::       choco/BoolMatrixND
IntMatrixND                    ::       choco/IntMatrixND
Term                           :: choco/Term
OccurTerm                      ::   choco/OccurTerm

CompleteAllDiff                :: ice/CompleteAllDiff
Permutation                    :: ice/Permutation
GlobalCardinality              :: ice/GlobalCardinality

// ---- redefining methods
//  works for standard method calls, but not yet for operators

// a few utils (we wish it worked)
div-                    :: choco/div-
div+                    :: choco/div+
make2DBoolMatrix         :: choco/make2DBoolMatrix
qsize                    :: choco/qsize
qLastRead                :: choco/qLastRead
qLastEnqueued            :: choco/qLastEnqueued
sLastRead                :: choco/sLastRead
sLastPushed              :: choco/sLastPushed
isEmpty                  :: choco/isEmpty
makeBipartiteSet         :: choco/makeBipartiteSet
partition                :: choco/partition

// ------- on Problem
propagationEngine       :: choco/propagationEngine
removalEvtQueue          :: choco/removalEvtQueue
boundEvtQueue            :: choco/boundEvtQueue
eventQueue               :: choco/eventQueue
engine                   :: choco/engine
globalSearchSolver       :: choco/globalSearchSolver
localSearchSolver        :: choco/localSearchSolver
attachPropagationEngine  :: choco/attachPropagationEngine
setActiveProblem         :: choco/setActiveProblem
varsToStore              :: choco/varsToStore
constraints              :: choco/constraints
solutions                :: choco/solutions
post                     :: choco/post

// ------- on Solution
;algo                     :: choco/algo

// ------- on PropagationEngine
delayedConst1            :: choco/delayedConst1
delayedConst2            :: choco/delayedConst2
delayedConst3            :: choco/delayedConst3
delayedConst4            :: choco/delayedConst4
instEvtStack             :: choco/instEvtStack
propagationOverflow      :: choco/propagationOverflow
contradictionCause       :: choco/contradictionCause
maxSize                  :: choco/maxSize

getNextActiveEventQueue :: choco/getNextActiveEventQueue
postUpdateInf           :: choco/postUpdateInf 
postUpdateSup           :: choco/postUpdateSup


// ----- on EventQueue
propagateEvent          :: choco/propagateEvent 
redundantEvent           :: choco/redundantEvent
isPopping                :: choco/isPopping
flushEventQueue          :: choco/flushEventQueue
popSomeEvents            :: choco/popSomeEvents

// ----- on PropagationEvent
registerEvent            :: choco/registerEvent

// ------- on AbstractVar
hook                     :: choco/hook

// ------- on IntVar
knownInt		:: choco/knownInt
isInstantiated		:: choco/isInstantiated
isInstantiatedTo        :: choco/isInstantiatedTo
canBeInstantiatedTo     :: choco/canBeInstantiatedTo 
getInf                  :: choco/getInf 
getSup                  :: choco/getSup 
removeVal               :: choco/removeVal
updateInf               :: choco/updateInf
updateSup               :: choco/updateSup
instantiate             :: choco/instantiate
getDomainSize           :: choco/getDomainSize

// <grt> added "IntVar" in property names (VS Set for the future versions)
connectIntVarEvents           :: choco/connectIntVarEvents
reconnectIntVarEvents         :: choco/reconnectIntVarEvents
disconnectIntVarEvents        :: choco/disconnectIntVarEvents
// </grt>
connectEvent             :: choco/connectEvent
disconnectEvent          :: choco/disconnectEvent
reconnectEvent           :: choco/reconnectEvent
connectHook             :: choco/connectHook
reconnectHook           :: choco/reconnectHook
disconnectHook          :: choco/disconnectHook

bucket                  :: choco/bucket
nbConstraints           :: choco/nbConstraints
problem                 :: choco/problem 

closeIntVar              :: choco/closeIntVar
addIntVar                :: choco/addIntVar

// ------- on IntVarEvent
nextConst                :: choco/nextConst
propagationEngine        :: choco/propagationEngine
idxInQueue               :: choco/idxInQueue
nextEventPostIndex       :: choco/nextEventPostIndex
cause                    :: choco/cause

// ------- on ValueRemovals
modifiedVar              :: choco/modifiedVar
many                     :: choco/many
valueStack               :: choco/valueStack
nbVals                   :: choco/nbVals
causeStack               :: choco/causeStack
maxVals                  :: choco/maxVals

// ------- on AbstractConstraint
connect                 :: choco/connect 
disconnect              :: choco/disconnect 
reconnect               :: choco/reconnect
// <grt> added "Int" in the property name (VS Set)
connectIntVar              :: choco/connectIntVar
constAwakeEvent         :: choco/constAwakeEvent
delay                   :: choco/delay 

isActive                :: choco/isActive
getPriority             :: choco/getPriority
getVar                  :: choco/getVar
getNbVars               :: choco/getNbVars
assignIndices           :: choco/assignIndices
getConstraintIdx        :: choco/getConstraintIdx
setConstraintIndex      :: choco/setConstraintIndex
askIfEntailed           :: choco/askIfEntailed
testIfSatisfied         :: choco/testIfSatisfied
testIfCompletelyInstantiated :: choco/testIfCompletelyInstantiated
propagate               :: choco/propagate
awake                   :: choco/awake
opposite                :: choco/opposite
getProblem              :: choco/getProblem
setPassive              :: choco/setPassive

// ------- on ConstAwakeEvent
touchedConst             :: choco/touchedConst
initialized              :: choco/initialized
priority                 :: choco/priority

// ------- on IntConstraint
awakeOnVar              :: choco/awakeOnVar
awakeOnInf              :: choco/awakeOnInf
awakeOnSup              :: choco/awakeOnSup
awakeOnInst             :: choco/awakeOnInst
awakeOnRem              :: choco/awakeOnRem
constAwake              :: choco/constAwake
doAwakeOnInf             :: choco/doAwakeOnInf
doAwakeOnSup             :: choco/doAwakeOnSup
askIfTrue                :: choco/askIfTrue
doAwake                  :: choco/doAwake
doAwakeOnRem             :: choco/doAwakeOnRem
testIfTrue               :: choco/testIfTrue
doPropagate              :: choco/doPropagate

closeLargeIntConstraint  :: choco/closeLargeIntConstraint

inf                     :: choco/inf
sup                     :: choco/sup
v1                      :: choco/v1
v2                      :: choco/v2
idx1                    :: choco/idx1
idx2                    :: choco/idx2
cste                    :: choco/cste
indices                 :: choco/indices
nbVars                  :: choco/nbVars
offset                  :: choco/offset
updtInfEvt              :: choco/updtInfEvt
updtSupEvt              :: choco/updtSupEvt
remValEvt               :: choco/remValEvt

// ------ on AbstractIntDomain,
domainSequence          :: choco/domainSequence
domainSet               :: choco/domainSet
updateDomainInf         :: choco/updateDomainInf
updateDomainSup         :: choco/updateDomainSup
getDomainInf            :: choco/getDomainInf
getDomainSup            :: choco/getDomainSup 
containsValInDomain     :: choco/containsValInDomain
getNextValue            :: choco/getNextValue
getPrevValue            :: choco/getPrevValue
removeDomainVal         :: choco/removeDomainVal
restrict                :: choco/restrict 
getDomainCard           :: choco/getDomainCard

// ------- on LinkedListIntDomain
bucketSize              :: choco/bucketSize

// ----- on  LargeCompositeConstraint
loffset                  :: choco/loffset

// ----- on  LinComb
propagateNewLowerBound  :: choco/propagateNewLowerBound
propagateNewUpperBound  :: choco/propagateNewUpperBound
computeLowerBound       :: choco/computeLowerBound
computeUpperBound       :: choco/computeUpperBound
nbPosVars               :: choco/nbPosVars
coefs                   :: choco/coefs
op                      :: choco/op
GEQ                     :: choco/GEQ 
EQ                      :: choco/EQ 
NEQ                     :: choco/NEQ 
improvedLowerBound      :: choco/improvedLowerBound
improvedUpperBound      :: choco/improvedUpperBound
filter                  :: choco/filter

// ----- on Occurrence
;checkNbPossible         :: choco/checkNbPossible   // private symbols from choco
;checkNbSure             :: choco/checkNbSure 
isPossible              :: choco/isPossible 
nbPossible              :: choco/nbPossible
isSure                  :: choco/isSure 
nbSure                  :: choco/nbSure
constrainOnInfNumber    :: choco/constrainOnInfNumber
constrainOnSupNumber    :: choco/constrainOnSupNumber
update                  :: choco/update

// ----- on Delayer
abstractIncInf           :: choco/abstractIncInf
abstractDecSup           :: choco/abstractDecSup
abstractRemoveVal        :: choco/abstractRemoveVal
target                   :: choco/target


// ----- on Elt
getNth                   :: choco/getNth
lval                     :: choco/lval

// ----- on EltTerm
;indexVar                 :: choco/indexVar
lvalues                  :: choco/lvalues

// ----- on BoolConstraint
const1                   :: choco/const1
const2                   :: choco/const2
nbConst                  :: choco/nbConst
lconst                   :: choco/lconst
loppositeConst           :: choco/loppositeConst
oppositeConst1           :: choco/oppositeConst1
oppositeConst2           :: choco/oppositeConst2
indicesInOppConst1       :: choco/indicesInOppConst1
indicesInOppConst2       :: choco/indicesInOppConst2
additionalVars           :: choco/additionalVars
statusBitVectorList      :: choco/statusBitVectorList
statusBitVector          :: choco/statusBitVector

closeBoolConstraint      :: choco/closeBoolConstraint
closeBoolConstraintWCounterOpp :: choco/closeBoolConstraintWCounterOpp

knownStatus              :: choco/knownStatus
knownTargetStatus        :: choco/knownTargetStatus
getTargetStatus          :: choco/getTargetStatus
setStatus                :: choco/setStatus
setTargetStatus          :: choco/setTargetStatus
getStatus                :: choco/getStatus

// ----- on Cardinality
nbTrueStatus             :: choco/nbTrueStatus
nbFalseStatus            :: choco/nbFalseStatus
getCardVar               :: choco/getCardVar

// -----  on Matching constraints
minValue                 :: ice/minValue
maxValue                 :: ice/maxValue
nbLeftVertices           :: ice/nbLeftVertices        
nbRightVertices          :: ice/nbRightVertices        
closeAssignmentConstraint :: ice/closeAssignmentConstraint 
deleteEdgeAndPublish     :: ice/deleteEdgeAndPublish
deleteMatch              :: ice/deleteMatch
component                :: ice/component
componentOrder           :: ice/componentOrder
minFlow                  :: ice/minFlow 
maxFlow                  :: ice/maxFlow 
flow                     :: ice/flow
matchingSize             :: ice/matchingSize
mustGrowFlowFromSource   :: ice/mustGrowFlowFromSource
nbVertices               :: ice/nbVertices
findAlternatingPath      :: ice/findAlternatingPath
match                    :: ice/match
inverseMatch             :: ice/inverseMatch
augment                  :: ice/augment
checkFlow                :: ice/checkFlow
firstPassDFS             :: ice/firstPassDFS
secondPassDFS            :: ice/secondPassDFS
mayMatch                 :: ice/mayMatch

