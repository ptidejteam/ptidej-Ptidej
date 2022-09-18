/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-namespace.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:38 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// {m in property | ((defined(m.name) = palm) & (length(m.restrictions) = 0))}
// This file creates local copies of the useful choco identifiers in the Palm namespace
//  this supports much lighter code writing for Palm (not having to prefix everyting with choco/)
// ---- redefining constants
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
// <grt> not useful anymore...
// IntVarEvent                    ::   choco/IntVarEvent
// ---- redefining methods
//  works for standard method calls, but not yet for operators
// a few utils (we wish it worked)
// ------- on Problem
// ------- on Solution
// ------- on PropagationEngine
// ----- on EventQueue
// ----- on PropagationEvent
// ------- on AbstractVar
// ------- on IntVar
// <grt> added "IntVar" in property names (VS Set for the future versions)
// </grt>
// ------- on IntVarEvent
// ------- on ValueRemovals
// ------- on AbstractConstraint
// <grt> added "Int" in the property name (VS Set)
// ------- on ConstAwakeEvent
// ------- on IntConstraint
// ------ on AbstractIntDomain,
// ------- on LinkedListIntDomain
// ----- on  LargeCompositeConstraint
// ----- on  LinComb
// ----- on Occurrence
// ----- on Delayer
// ----- on Elt
// ----- on EltTerm
// ----- on BoolConstraint
// ----- on Cardinality
// -----  on Matching constraints