/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\compil.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:32 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// the interface of abstract classes must be extensible
// in those cases where the abstact class has only on single concrete class
//  (such as IntVar, IntDomain, ...), 
// there is only one single implementation of the interface and the compiler 
// optimizes the code accordignly. 
//  Here, we tell the compiler not to be smart, so that Palm can extend those interfaces !!
// ---- IntVar
// In addition, open-up the low-level primitives of IntVar for the Palm library
// ---- AbstractConstraint
// ---- AbstractIntDomain,
// a few higher level primitives that are open for re-definition
// --- PropagationEngine
// strange: don't understand why ???
// for naren
// --- LinComb
// v1.0 specific patch for PaLM
// --- Occurrence
// v1.0 for Palm