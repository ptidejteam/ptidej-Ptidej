/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\model.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:25 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: model.cl                                                   *
// *    object model & basic architecture                             *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: Global parameters                                      *
// *   Part 2: simple utilities (min,max,etc.)                        *
// *   Part 3: data structure utilities                               *
// *   Part 3: The generic object hierarchy                           *
// *   Part 4: Variables                                              *
// *   Part 5: Propagation events                                     *
// *   Part 6: Constraints (objects, creation, generic methods)       *
// *   Part 7: compiler optimization                                  *
// *   Part 8: Problems & constraint networks                         *
// --------------------------------------------------------------------
/* The c++ function for: showChocoLicense(_CL_obj:void) [] */
void  choco_showChocoLicense_void()
{ princ_string("Choco version ");
  princ_float(13.024);
  princ_string(", Copyright (C) 1999-2002 F. Laburthe\n");
  princ_string("Choco comes with ABSOLUTELY NO WARRANTY; ");
  princ_string("for details read licence.txt\n");
  princ_string("This is free software, and you are welcome to redistribute it\n");
  princ_string("under certain conditions; read licence.txt for details.\n");
  } 


// ********************************************************************
// *   Part 1: Global parameters                                      *
// ********************************************************************
// VERBOSITY PARAMETERS: we use the ADHOC methodology
//
//    (a) The integer value of verbose() may be
//          0: application mode  = silent  (trace <=> printf)
//          1: execution mode    = silent but for structure comments
//          2: trace mode        = report the execution flow step by step
//          3: debug mode        = report everything !
//    (b) each fragment X of the program may
//          - either use the standard integer values (0,1,2)
//          - introduce the flags: XTALK, XSHOW, XDEBUG
//            The flags support a flexible control of the code fragments,
//            for which tracing can be independently turned on and off
//               XVIEW: (default 1)   execution
//               XTALK: (defalt 2)    trace
//               XDEBUG: (default 3)  debug
// fragment D: coding domains
// debugging buckets (implementing domains)
// fragment P: propagation
// general propagation info (layered fix-points)
// tracing propagation (event queues)
// debugging propagation (domain updates)
// fragment GP: propagation of global constraints
// general propagation info (layered fix-points)
// tracing propagation (event queues)
// debugging propagation (domain updates)
// fragment S: global search
// general search info (solutions found)
// tracing decisions in the search tree
// debugging search tree construction (variable selection heuristics)
// fragment I: invariants (for non-monotonic moves)
// general info about conflict counts
// tracing improvement of conflict counts
// debugging evaluations of invariant maintenance
// fragment L: moves & local search
// general info about iterations (good solutions found)
// tracing assignments & flips
// debugging local optimization (selection heuristics)
// fragment CHOCOBENCH: benchmark suite
// fragment CHOCOTEST: regression testbed
// ********************************************************************
// *   Part 3: The object hierarchy                                   *
// ********************************************************************
// root class for all Choco objects
// below, the whole hierarchy is reproduced
// (uncommented lines are mandatory forward declarations)
// v0.9906
// ********************************************************************
// *   Part 8: Problems                                               *
// ********************************************************************
// v0.25: a solution contains the list of values for a reference list of variables
// claire3 port: strongly typed lists
/* The c++ function for: makeSolution(a:Solver,nbVars:integer) [] */
Solution * choco_makeSolution_Solver(Solver *a,int nbVars)
{ GC_BIND;
  { Solution *Result ;
    { Solution * _CL_obj = ((Solution *) GC_OBJECT(Solution,new_object_class(choco._Solution)));
      (_CL_obj->algo = a);
      (_CL_obj->lval = make_list_integer2(nbVars,GC_OBJECT(ClaireType,U_type(Kernel._integer,set::alloc(Kernel.emptySet,1,CNULL))),CNULL));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A problem is a global structure containing variables bound by constraints
// as well as solutions or solver parameters
/* The c++ function for: getIntVar(p:Problem,i:integer) [] */
IntVar * choco_getIntVar_Problem(Problem *p,int i)
{ ;return (OBJECT(IntVar,(*(p->vars))[i]));} 


/* The c++ function for: getSetVar(p:Problem,i:integer) [] */
SetVar * choco_getSetVar_Problem(Problem *p,int i)
{ ;return (OBJECT(SetVar,(*(p->setVars))[i]));} 


// forward declarations // v1.010
// ********************************************************************
// *   Part 9: local and global solvers                               *
// ********************************************************************
// -----------Logic-------------------------------------
// v1.0 <fl>
// -----------Control-------------------------------------
// claire3 port: strongly typed lists
// v1.020: new objects for search limits