/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-model.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:38 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2000-2002 - Narendra Jussien - EMN
// Système PaLM 
// ** Summary
// Part 1 : Release information (version number, release date, ...)
// Part 2 : Object hierarchy (storing explanation and specific tools)
// Part 3 : Global parameters 
// *************************************************************
// * Part 1 : Release information                              *
// *************************************************************
/* The c++ function for: palmVersion(_CL_obj:void) [] */
char * palm_palmVersion_void()
{ return ("1.324");} 


/* The c++ function for: palmReleaseDate(_CL_obj:void) [] */
char * palm_palmReleaseDate_void()
{ return ("Dec 1, 2002");} 


/* The c++ function for: palmInfo(_CL_obj:void) [] */
char * palm_palmInfo_void()
{ return ("PaLM : Constraint Programming with Explanations");} 


/* The c++ function for: showPalmLicense(_CL_obj:void) [] */
OID  palm_showPalmLicense_void()
{ princ_string("** ");
  princ_string(palm_palmInfo_void());
  princ_string(" \n** PaLM v");
  princ_string(palm_palmVersion_void());
  princ_string(" (");
  princ_string(palm_palmReleaseDate_void());
  return (_void_(princ_string("), Copyright (c) 2000-2002 N. Jussien\n")));} 


// present the information at each execution ! 
// *************************************************************
// * Part 2 : object hierarchy                                 *
// *************************************************************
// Specific tools are PaLMtools 
// forward declaration for PalmVariable ... obviously a special 
// treatment will be reserved to PalmVariable ... 
// Forward declaration for PalmSolver
// Forward declaration for UFboxes
// ** Problem
// ** Propagation engine
// A PaLMengine is a slightly peculiar ChocEngine
// we need to store the explicit list of constraints to be 
// awaken when posting a new one (the index position will
// not be sufficient)
// ** Specific events
// New events need to be created for constraint removal handling
// Storing explanations ... 
// an explanation is a set of constraints ... 
// A contradiction is explained in a different way
// A bound modification is explained that way
// Explanation for INF modification
// Explanation for SUP modification
// a value removal is explained that way
// forward 
// Palm variables are one of a kind ! 
// Palm domains are specific !
// Palm contradiction handling tools 
// solving problems
// a learner for Path Repair techniques ... 
// forward declaration 
// an extender that needs to ask permission
// ** Solver   
// User-Friendly information
// ********************************************************************
// *   Part 3: Global parameters                                      *
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
//               XTALK: (default 2)    trace
//               XDEBUG: (default 3)  debug
//        The DEBUG flag may be shared by several fragments
// Palm Fragment 
// Reporting structure events ... 
// Showing detailed information on the behavior
// debugging
// Showing evolution of optimization during search
// time stamp
// Taille maximum autorisee pour un domaine
// Entier dont les 29 premiers bits sont a un
// UF mode switch
// Global variables 
// The currently defined block