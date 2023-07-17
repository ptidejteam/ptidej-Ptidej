/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-solve.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:42 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 
// ** Summary : solving problems (mechanism and tools)
// * Part 1 : Runtime statistics          
// * Part 2 : palmSolver initialisation
// * Part 3 : Solving Problems                    
// * Part 4 : Extending a solution                 
// * Part 5 : Repairing a solution                 
// * Part 6 : Maintaining the search state          
// * Part 7 : Learning from a contradiction        
// * Part 8 : Choosing a constraint to undo        
// * Part 9 : Default extension                    
// * Part 10: Variable selection tools             
// * Part 11: Solution management tools            
// * Part 12: A generic learner for Path Repair   
// Forward declaration
// *************************************************
// * Part 1 : Runtime statistics                   *
// *************************************************
// Different statistics are stored
// number of constraint relaxation
// number of extensions
// cpu time 
/* The c++ function for: getGlobalStatistics(pb:PalmSolver,stat:{1, 2, 3}) [] */
int  palm_getGlobalStatistics_PalmSolver(PalmSolver *pb,int stat)
{ { int Result = 0;
    { int  total = 0;
      { ITERATE(s);
        for (START(pb->solutions); NEXT(s);)
        total= (total+((*(OBJECT(PalmSolution,s)->lstat))[stat]));
        } 
      Result = total;
      } 
    return (Result);} 
  } 


/* The c++ function for: getGlobalStatistics(pb:PalmProblem,stat:{1, 2, 3}) [] */
int  palm_getGlobalStatistics_PalmProblem(PalmProblem *pb,int stat)
{ GC_BIND;
  { int Result = 0;
    Result = palm_getGlobalStatistics_PalmSolver(GC_OBJECT(PalmSolver,pb->palmSolver),stat);
    GC_UNBIND; return (Result);} 
  } 


// accessing the information
/* The c++ function for: getRuntimeStatistic(pb:PalmSolver,stat:{1, 2, 3}) [] */
int  palm_getRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat)
{ return ((*(pb->statistics))[stat]);} 


/* The c++ function for: getRuntimeStatistic(pb:PalmProblem,stat:{1, 2, 3}) [] */
int  palm_getRuntimeStatistic_PalmProblem(PalmProblem *pb,int stat)
{ return ((*(pb->palmSolver->statistics))[stat]);} 


// setting a value
/* The c++ function for: setRuntimeStatistic(pb:PalmSolver,stat:{1, 2, 3},val:integer) [] */
int  palm_setRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int val)
{ return (((*(pb->statistics))[stat]=val));} 


// value incrementing
/* The c++ function for: incRuntimeStatistic(pb:PalmSolver,stat:{1, 2, 3},inc:integer) [] */
void  palm_incRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int inc)
{ ((*(pb->statistics))[stat]=(((*(pb->statistics))[stat])+inc));
  } 


// value decrementing
/* The c++ function for: decRuntimeStatistic(pb:PalmSolver,stat:{1, 2, 3},dec:integer) [] */
void  palm_decRuntimeStatistic_PalmSolver(PalmSolver *pb,int stat,int dec)
{ ((*(pb->statistics))[stat]=(((*(pb->statistics))[stat])-dec));
  } 


// pretty printing the kept information
/* The c++ function for: printRuntimeStatistics(pb:PalmProblem) [] */
OID  palm_printRuntimeStatistics_PalmProblem1(PalmProblem *pb)
{ { OID Result = 0;
    { ITERATE(s);
      Result= _oid_(CFALSE);
      bag *s_support;
      s_support = set::alloc(Kernel.emptySet,3,1,
        2,
        3);
      for (START(s_support); NEXT(s);)
      palm_printRuntimeStatistics_PalmProblem2(pb,s);
      } 
    return (Result);} 
  } 


// pretty printing one kept information
/* The c++ function for: printRuntimeStatistics(pb:PalmProblem,stat:{1, 2, 3}) [] */
OID  palm_printRuntimeStatistics_PalmProblem2(PalmProblem *pb,int stat)
{ { OID Result = 0;
    if (stat == 1)
     { print_any((*(pb->palmSolver->statistics))[stat]);
      princ_string(" repairs ");
      } 
    else if (stat == 2)
     { print_any((*(pb->palmSolver->statistics))[stat]);
      princ_string(" extensions ");
      } 
    else if (stat == 3)
     { print_any((*(pb->palmSolver->statistics))[stat]);
      princ_string(" ms ");
      } 
    else Result = Kernel.cfalse;
      return (Result);} 
  } 


/* The c++ function for: reset(pb:PalmSolver) [] */
void  palm_reset_PalmSolver(PalmSolver *pb)
{ GC_BIND;
  (pb->finished = CFALSE);
  erase_property(palm.dummyVariable,GC_OBJECT(PropagationEngine,pb->problem->propagationEngine));
  { ITERATE(s);
    bag *s_support;
    s_support = set::alloc(Kernel.emptySet,3,1,
      2,
      3);
    for (START(s_support); NEXT(s);)
    ((*(pb->statistics))[s]=0);
    } 
  GC_UNBIND;} 


// *************************************************
// * Part 2 : API                                  *
// *************************************************
/* The c++ function for: initPalmSolver(pb:PalmProblem) [] */
void  palm_initPalmSolver_PalmProblem(PalmProblem *pb)
{ GC_BIND;
  { PalmSolver * ps;
    { { PalmSolver * _CL_obj = ((PalmSolver *) GC_OBJECT(PalmSolver,new_object_class(palm._PalmSolver)));
        (_CL_obj->statistics = make_list_integer2(OBJECT(bag,palm.RuntimeStatistic->value)->length,Kernel._integer,0));
        ps = _CL_obj;
        } 
      GC_OBJECT(PalmSolver,ps);} 
    (pb->palmSolver = ps);
    { PalmState * g0306UU;
      { PalmState * _CL_obj = ((PalmState *) GC_OBJECT(PalmState,new_object_class(palm._PalmState)));
        { PalmState * g0307; 
          Explanation * g0308;
          g0307 = _CL_obj;
          { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            g0308 = _CL_obj;
            } 
          (g0307->path = g0308);} 
        g0306UU = _CL_obj;
        } 
      palm_attachPalmState_PalmProblem(pb,g0306UU);
      } 
    { PalmExtend * g0309UU;
      { PalmExtend * _CL_obj = ((PalmExtend *) GC_OBJECT(PalmExtend,new_object_class(palm._PalmExtend)));
        g0309UU = _CL_obj;
        } 
      palm_attachPalmExtend_PalmProblem(pb,g0309UU);
      } 
    { list * g0299;
      { { bag *v_list; OID v_val;
          OID g0300,CLcount;
          { OID v_bag;
            GC_ANY(v_list= list::empty(Kernel.emptySet));
            { { PalmAssignVar * _CL_obj = ((PalmAssignVar *) GC_OBJECT(PalmAssignVar,new_object_class(palm._PalmAssignVar)));
                v_bag = _oid_(_CL_obj);
                } 
              GC_OID(v_bag);} 
            ((list *) v_list)->addFast(v_bag);} 
           g0299 = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { g0300 = (*(v_list))[CLcount];
            v_val = _oid_(copy_object(OBJECT(ClaireObject,g0300)));
            
            (*((list *) g0299))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,g0299);} 
      int  g0301 = g0299->length;
      PalmExtend * g0302 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
      { int  g0303 = 1;
        int  g0305 = (g0301-1);
        { OID gc_local;
          while ((g0303 <= g0305))
          { // HOHO, GC_LOOP not needed !
            update_property(palm.nextBranching,
              OBJECT(ClaireObject,(*(g0299))[g0303]),
              3,
              Kernel._object,
              (*(g0299))[(g0303+1)]);
            ++g0303;
            } 
          } 
        } 
      { OID gc_local;
        ITERATE(g0304);
        for (START(g0299); NEXT(g0304);)
        { GC_LOOP;
          (OBJECT(PalmBranching,g0304)->extender = g0302);
          GC_UNLOOP;} 
        } 
      update_property(palm.branching,
        g0302,
        3,
        Kernel._object,
        (*(g0299))[1]);
      } 
    { PalmLearn * g0310UU;
      { PalmLearn * _CL_obj = ((PalmLearn *) GC_OBJECT(PalmLearn,new_object_class(palm._PalmLearn)));
        g0310UU = _CL_obj;
        } 
      palm_attachPalmLearn_PalmProblem(pb,g0310UU);
      } 
    { PalmRepair * g0311UU;
      { PalmRepair * _CL_obj = ((PalmRepair *) GC_OBJECT(PalmRepair,new_object_class(palm._PalmRepair)));
        g0311UU = _CL_obj;
        } 
      palm_attachPalmRepair_PalmProblem(pb,g0311UU);
      } 
    (ps->problem = pb);
    } 
  GC_UNBIND;} 


/* The c++ function for: initPalmBranchAndBound(pb:PalmProblem,isMax:boolean,obj:PalmIntVar) [] */
void  palm_initPalmBranchAndBound_PalmProblem(PalmProblem *pb,ClaireBoolean *isMax,PalmIntVar *obj)
{ GC_BIND;
  { PalmBranchAndBound * ps;
    { { PalmBranchAndBound * _CL_obj = ((PalmBranchAndBound *) GC_OBJECT(PalmBranchAndBound,new_object_class(palm._PalmBranchAndBound)));
        (_CL_obj->objective = obj);
        (_CL_obj->statistics = make_list_integer2(OBJECT(bag,palm.RuntimeStatistic->value)->length,Kernel._integer,0));
        (_CL_obj->maximizing = isMax);
        ps = _CL_obj;
        } 
      GC_OBJECT(PalmBranchAndBound,ps);} 
    PalmSolver * oldSolver = GC_OBJECT(PalmSolver,pb->palmSolver);
    (pb->palmSolver = ps);
    if ((oldSolver->varsToStore->length > 0) && 
        (_oid_(obj) == (*(oldSolver->varsToStore))[1]))
     (ps->varsToStore = oldSolver->varsToStore);
    else (ps->varsToStore = append_list(list::alloc(choco._IntVar,1,_oid_(obj)),GC_OBJECT(list,oldSolver->varsToStore)));
      (pb->palmSolver->state = oldSolver->state);
    (oldSolver->state->manager = pb->palmSolver);
    (pb->palmSolver->extending = oldSolver->extending);
    (oldSolver->extending->manager = pb->palmSolver);
    (pb->palmSolver->learning = oldSolver->learning);
    (oldSolver->learning->manager = pb->palmSolver);
    (pb->palmSolver->repairing = oldSolver->repairing);
    (oldSolver->repairing->manager = pb->palmSolver);
    (ps->problem = pb);
    } 
  GC_UNBIND;} 


/* The c++ function for: attachPalmExtend(pb:PalmProblem,ext:PalmExtend) [] */
void  palm_attachPalmExtend_PalmProblem(PalmProblem *pb,PalmExtend *ext)
{ (pb->palmSolver->extending = ext);
  (ext->manager = pb->palmSolver);
  } 


/* The c++ function for: attachPalmLearn(pb:PalmProblem,ext:PalmLearn) [] */
void  palm_attachPalmLearn_PalmProblem(PalmProblem *pb,PalmLearn *ext)
{ (pb->palmSolver->learning = ext);
  (ext->manager = pb->palmSolver);
  } 


/* The c++ function for: attachPalmRepair(pb:PalmProblem,ext:PalmRepair) [] */
void  palm_attachPalmRepair_PalmProblem(PalmProblem *pb,PalmRepair *ext)
{ (pb->palmSolver->repairing = ext);
  (ext->manager = pb->palmSolver);
  } 


/* The c++ function for: attachPalmState(pb:PalmProblem,ext:PalmState) [] */
void  palm_attachPalmState_PalmProblem(PalmProblem *pb,PalmState *ext)
{ (pb->palmSolver->state = ext);
  (ext->manager = pb->palmSolver);
  } 


/* The c++ function for: attachPalmBranchings(pb:PalmProblem,lbr:list[PalmBranching]) [] */
void  palm_attachPalmBranchings_PalmProblem(PalmProblem *pb,list *lbr)
{ GC_BIND;
  { list * lb;
    { { bag *v_list; OID v_val;
        OID t,CLcount;
        v_list = lbr;
         lb = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { t = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,t)));
          
          (*((list *) lb))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,lb);} 
    int  n = lb->length;
    PalmExtend * ext = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  i = 1;
      int  g0312 = (n-1);
      { OID gc_local;
        while ((i <= g0312))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(lb))[i]),
            3,
            Kernel._object,
            (*(lb))[(i+1)]);
          ++i;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(br);
      for (START(lb); NEXT(br);)
      { GC_LOOP;
        (OBJECT(PalmBranching,br)->extender = ext);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      ext,
      3,
      Kernel._object,
      (*(lb))[1]);
    } 
  GC_UNBIND;} 


// *************************************************
// * Part 3 : Solving Problems                     *
// *************************************************
// the general solve method that search for one feasible solution
/* The c++ function for: searchOneSolution(pb:PalmProblem) [] */
ClaireBoolean * palm_searchOneSolution_PalmProblem(PalmProblem *pb)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { PalmSolver * algo = pb->palmSolver;
      (*palm.run)(_oid_(algo));
      _void_((INHERIT(algo->isa,palm._PalmBranchAndBound) ?  palm_storeSolution_PalmBranchAndBound((PalmBranchAndBound *) OBJECT(PalmBranchAndBound,_oid_(algo))) :   palm_storeSolution_PalmSolver((PalmSolver *) OBJECT(PalmSolver,_oid_(algo)))));
      Result = algo->feasible;
      } 
    GC_UNBIND; return (Result);} 
  } 


// the general algorithm 
/* The c++ function for: run(algo:PalmSolver) [] */
OID  palm_run_PalmSolver(PalmSolver *algo)
{ GC_BIND;
  { OID Result = 0;
    { Problem * pb = GC_OBJECT(Problem,algo->problem);
      time_set_void();
      { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { (algo->finished = CFALSE);
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { _void_(choco.propagate->fcall(((int) pb)));
                  } 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
              { c_handle.catchIt();{ (*palm.repair)(_oid_(algo));
                  } 
                } 
              else PREVIOUS_HANDLER;} 
            { while ((algo->finished != CTRUE))
              { { ClaireHandler c_handle = ClaireHandler();
                  if ERROR_IN 
                  { { (*palm.extend)(_oid_(algo));
                      _void_(choco.propagate->fcall(((int) pb)));
                      } 
                    ClEnv->cHandle--;} 
                  else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
                  { c_handle.catchIt();{ (*palm.repair)(_oid_(algo));
                      } 
                    } 
                  else PREVIOUS_HANDLER;} 
                } 
              } 
            (algo->feasible = CTRUE);
            } 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();{ (algo->finished = CTRUE);
            (algo->feasible = CFALSE);
            } 
          } 
        else PREVIOUS_HANDLER;} 
      Result = ((*(algo->statistics))[3]=time_get_void());
      } 
    GC_UNBIND; return (Result);} 
  } 


// solving the feasibility problem 
// same code as in run(PalmSolver) in order to be able
// to store consecutive solutions ... 
/* The c++ function for: runonce(algo:PalmBranchAndBound) [] */
void  palm_runonce_PalmBranchAndBound(PalmBranchAndBound *algo)
{ GC_BIND;
  time_set_void();
  { ClaireHandler c_handle = ClaireHandler();
    if ERROR_IN 
    { { { OID gc_local;
          while ((algo->finished != CTRUE))
          { GC_LOOP;
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { (*palm.extend)(_oid_(algo));
                  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(algo->problem))))));
                  } 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
              { c_handle.catchIt();(*palm.repair)(_oid_(algo));
                } 
              else PREVIOUS_HANDLER;} 
            GC_UNLOOP;} 
          } 
        (algo->feasible = CTRUE);
        } 
      ClEnv->cHandle--;} 
    else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
    { c_handle.catchIt();{ (algo->finished = CTRUE);
        (algo->feasible = CFALSE);
        } 
      } 
    else PREVIOUS_HANDLER;} 
  ((*(algo->statistics))[3]=time_get_void());
  GC_UNBIND;} 


/* The c++ function for: run(algo:PalmBranchAndBound) [] */
void  palm_run_PalmBranchAndBound(PalmBranchAndBound *algo)
{ GC_BIND;
  { ClaireHandler c_handle = ClaireHandler();
    if ERROR_IN 
    { { (*choco.post)(GC_OID(_oid_(algo->problem)),
          GC_OID((*Kernel._sup_equal)(GC_OID(_oid_(algo->objective)),
            algo->lowerBound)));
        (*choco.post)(GC_OID(_oid_(algo->problem)),
          GC_OID((*Kernel._inf_equal)(GC_OID(_oid_(algo->objective)),
            algo->upperBound)));
        _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(algo->problem))))));
        } 
      ClEnv->cHandle--;} 
    else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
    { c_handle.catchIt();{ if (palm.PalmVIEW->value <= ClEnv->verbose)
         tformat_string("optimality proven !!! \n",palm.PalmVIEW->value,list::empty());
        else ;(algo->finished = CTRUE);
        (algo->feasible = CFALSE);
        } 
      } 
    else PREVIOUS_HANDLER;} 
  { ClaireBoolean *v_while;
    v_while = CFALSE;
    
    while (v_while != CTRUE)
    { palm_runonce_PalmBranchAndBound(algo);
      palm_storeSolution_PalmBranchAndBound(algo);
      if (algo->feasible == CTRUE)
       palm_postDynamicCut_PalmBranchAndBound(algo);
      v_while = not_any(_oid_(algo->feasible));
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: postDynamicCut(algo:PalmBranchAndBound) [] */
void  palm_postDynamicCut_PalmBranchAndBound(PalmBranchAndBound *algo)
{ GC_BIND;
  { Problem * pb = GC_OBJECT(Problem,algo->problem);
    IntVar * obj = GC_OBJECT(IntVar,algo->objective);
    int  bv = ((algo->maximizing == CTRUE) ?
      choco.getSup->fcall(((int) obj)) :
      choco.getInf->fcall(((int) obj)) );
    palm_reset_PalmSolver(algo);
    { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { { if (algo->maximizing == CTRUE)
           (algo->lowerBound = ((bv <= algo->lowerBound) ?
            algo->lowerBound :
            bv ));
          else (algo->upperBound = ((bv <= algo->upperBound) ?
              bv :
              algo->upperBound ));
            { ClaireObject * cut = GC_OBJECT(ClaireObject,((algo->maximizing == CTRUE) ?
              OBJECT(ClaireObject,(*Kernel._sup)(_oid_(obj),
                bv)) :
              OBJECT(ClaireObject,(*Kernel._inf)(_oid_(obj),
                bv)) ));
            GC_OBJECT(list,algo->dynamicCuts)->addFast(_oid_(cut));
            (*choco.post)(_oid_(pb),
              _oid_(cut));
            } 
          { ClaireHandler c_handle = ClaireHandler();
            if ERROR_IN 
            { _void_(choco.propagate->fcall(((int) pb)));
              ClEnv->cHandle--;} 
            else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
            { c_handle.catchIt();(*palm.repair)(_oid_(algo));
              } 
            else PREVIOUS_HANDLER;} 
          } 
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();{ (algo->finished = CTRUE);
          (algo->feasible = CFALSE);
          } 
        } 
      else PREVIOUS_HANDLER;} 
    } 
  GC_UNBIND;} 


// *************************************************
// * Part 4 : Extending a solution                 *
// *************************************************
// <v1.08> <ygg> compilation bug fix
/* The c++ function for: extend(algo:PalmSolver) [] */
void  palm_extend_PalmSolver_palm(PalmSolver *algo)
{ GC_BIND;
  (*palm.explore)(GC_OID(_oid_(algo->extending)),
    GC_OID(_oid_(algo->extending->branching)));
  GC_UNBIND;} 


// branchings are chained
/* The c++ function for: explore(ext:PalmExtend,b:PalmBranching) [] */
void  palm_explore_PalmExtend(PalmExtend *ext,PalmBranching *b)
{ GC_BIND;
  { OID  v = GC_OID((*palm.selectBranchingItem)(_oid_(b)));
    if (v != CNULL)
     palm_propagateAllDecisionConstraints_PalmProblem(GC_OBJECT(PalmProblem,((PalmProblem *) ext->manager->problem)),GC_OBJECT(list,OBJECT(list,(*palm.selectDecisions)(_oid_(b),
      v))));
    else { PalmBranching * nB = GC_OBJECT(PalmBranching,b->nextBranching);
        if (nB == (NULL))
         (b->extender->manager->finished = CTRUE);
        else _void_((INHERIT(ext->isa,palm._PalmUnsureExtend) ?  palm_explore_PalmUnsureExtend((PalmUnsureExtend *) OBJECT(PalmUnsureExtend,_oid_(ext)),OBJECT(PalmBranching,_oid_(nB))) :   palm_explore_PalmExtend((PalmExtend *) OBJECT(PalmExtend,_oid_(ext)),OBJECT(PalmBranching,_oid_(nB)))));
          } 
      } 
  GC_UNBIND;} 


/* The c++ function for: explore(ext:PalmUnsureExtend,b:PalmBranching) [] */
void  palm_explore_PalmUnsureExtend(PalmUnsureExtend *ext,PalmBranching *b)
{ GC_BIND;
  { OID  v = GC_OID((*palm.selectBranchingItem)(_oid_(b)));
    if (v != CNULL)
     palm_propagateAllDecisionConstraints_PalmProblem(GC_OBJECT(PalmProblem,((PalmProblem *) ext->manager->problem)),GC_OBJECT(list,palm_selectAuthorizedDecisions_PalmBranching(b,v)));
    else { PalmBranching * nB = GC_OBJECT(PalmBranching,b->nextBranching);
        if (nB == (NULL))
         (b->extender->manager->finished = CTRUE);
        else palm_explore_PalmUnsureExtend(ext,nB);
          } 
      } 
  GC_UNBIND;} 


/* The c++ function for: selectAuthorizedDecisions(b:PalmBranching,v:any) [] */
list * palm_selectAuthorizedDecisions_PalmBranching(PalmBranching *b,OID v)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list *Result ;
    { ClaireBoolean * accepted = CFALSE;
      PalmExtend * ext = b->extender;
      Problem * pb = ext->manager->problem;
      list * decisions = GC_OBJECT(list,OBJECT(list,(*palm.getNextDecisions)(_oid_(b))));
      { OID gc_local;
        ClaireBoolean *v_while;
        v_while = not_any(_oid_((((OBJECT(ClaireBoolean,(*palm.checkAcceptable)(_oid_(b),
          _oid_(decisions)))) == CTRUE) ? ((boolean_I_any((*palm.checkAcceptable)(GC_OID(_oid_(ext->manager->learning)),
          _oid_(decisions))) == CTRUE) ? CTRUE: CFALSE): CFALSE)));
        
        while (v_while == CTRUE)
        { GC_LOOP;
          (*palm.learnFromRejection)(_oid_(b));
          GC__ANY(decisions = OBJECT(list,(*palm.getNextDecisions)(_oid_(b))), 1);
          v_while = not_any(_oid_((((OBJECT(ClaireBoolean,(*palm.checkAcceptable)(_oid_(b),
            _oid_(decisions)))) == CTRUE) ? ((boolean_I_any((*palm.checkAcceptable)(GC_OID(_oid_(ext->manager->learning)),
            _oid_(decisions))) == CTRUE) ? CTRUE: CFALSE): CFALSE)));
          GC_UNLOOP;} 
        } 
      Result = decisions;
      } 
    GC_UNBIND; return (Result);} 
  } 


// <cl3> bug 
// generic post mechanism for a set of constraints
/* The c++ function for: propagateAllDecisionConstraints(pb:PalmProblem,cts:list[choco/AbstractConstraint]) [] */
void  palm_propagateAllDecisionConstraints_PalmProblem(PalmProblem *pb,list *cts)
{ GC_BIND;
  { PalmSolver * manager = GC_OBJECT(PalmSolver,pb->palmSolver);
    ((*(manager->statistics))[2]=(((*(manager->statistics))[2])+1));
    { OID gc_local;
      ITERATE(ct);
      for (START(cts); NEXT(ct);)
      { GC_LOOP;
        { (*palm.addDecision)(GC_OID(_oid_(manager->state)),
            ct);
          (*choco.post)(_oid_(pb),
            ct,
            0);
          choco_propagate_PalmProblem(pb);
          } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


// ** need to specify the following methods
/* The c++ function for: selectBranchingItem(b:PalmBranching) [] */
OID  palm_selectBranchingItem_PalmBranching_palm(PalmBranching *b)
{ close_exception(((general_error *) (*Core._general_error)(_string_("*** PaLM error : need to specify selectBranchingItem for a ~S"),
    _oid_(list::alloc(1,_oid_(b->isa))))));
  return (CNULL);} 


/* The c++ function for: selectDecisions(b:PalmBranching,v:any) [] */
list * palm_selectDecisions_PalmBranching_palm(PalmBranching *b,OID v)
{ close_exception(((general_error *) (*Core._general_error)(_string_("*** PaLM error : need to specify selectDecisions for a ~S"),
    _oid_(list::alloc(1,_oid_(b->isa))))));
  return (list::empty(choco._AbstractConstraint));} 


/* The c++ function for: getNextDecisions(b:PalmBranching) [] */
list * palm_getNextDecisions_PalmBranching_palm(PalmBranching *b)
{ close_exception(((general_error *) (*Core._general_error)(_string_("*** PaLM error : need to specify getNextDecisions for a ~S"),
    _oid_(list::alloc(1,_oid_(b->isa))))));
  return (list::empty(choco._AbstractConstraint));} 


// this method checks if the set of decisions is acceptable by the branching component
/* The c++ function for: checkAcceptable(b:PalmBranching,cts:list[choco/AbstractConstraint]) [] */
ClaireBoolean * palm_checkAcceptable_PalmBranching_palm(PalmBranching *b,list *cts)
{ close_exception(((general_error *) (*Core._general_error)(_string_("*** PaLM error : need to specify checkAcceptable for a ~S"),
    _oid_(list::alloc(1,_oid_(b->isa))))));
  return (CFALSE);} 


// this method learns from the rejected decisions (if possible)
/* The c++ function for: learnFromRejection(b:PalmBranching) [] */
void  palm_learnFromRejection_PalmBranching_palm(PalmBranching *b)
{ close_exception(((general_error *) (*Core._general_error)(_string_("*** PaLM error : need to specify learnFromRejection for an ~S"),
    _oid_(list::alloc(1,_oid_(b->isa))))));
  } 


// *************************************************
// * Part 5 : Repairing a solution                 *
// *************************************************
// <v1.08> <ygg> compilation bug fix
/* The c++ function for: repair(pb:PalmProblem) [] */
void  palm_repair_PalmProblem_palm(PalmProblem *pb)
{ GC_BIND;
  (*palm.repair)(GC_OID(_oid_(pb->palmSolver)));
  GC_UNBIND;} 


// The generic repair technique 
/* The c++ function for: repair(algo:PalmSolver) [] */
void  palm_repair_PalmSolver_palm(PalmSolver *algo)
{ GC_BIND;
  { Problem * pb = GC_OBJECT(Problem,algo->problem);
    PropagationEngine * pe = GC_OBJECT(PropagationEngine,pb->propagationEngine);
    PalmState * state = GC_OBJECT(PalmState,algo->state);
    PalmLearn * learner = GC_OBJECT(PalmLearn,algo->learning);
    PalmRepair * repairer = GC_OBJECT(PalmRepair,algo->repairing);
    if (CLREAD(PalmEngine,pe,contradictory) == CTRUE)
     { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      Ephemeral * fv = GC_OBJECT(Ephemeral,pe->contradictionCause);
      palm_self_explain_PalmIntVar1(((PalmIntVar *) fv),4,e);
      (CLREAD(PalmEngine,pe,contradictory) = CFALSE);
      (*palm.learnFromContradiction)(_oid_(learner),
        _oid_(e));
      if (e->explanation->length == 0)
       { (CLREAD(PalmEngine,pe,contradictory) = CFALSE);
        palm_flushEvents_PalmEngine(((PalmEngine *) pe));
        contradiction_I_void();
        } 
      else { OID  ct = GC_OID((*palm.selectDecisionToUndo)(_oid_(repairer),
            _oid_(e)));
          if (ct != CNULL)
           { if ((*palm.weight)(ct) <= CLREAD(PalmProblem,pb,maxRelaxLvl))
             { ((*(algo->statistics))[1]=(((*(algo->statistics))[1])+1));
              if ((*palm.weight)(ct) > 0)
               { if (palm.PalmVIEW->value <= ClEnv->verbose)
                 tformat_string("PALM: Removing constraint ~S (w:~S) \n",palm.PalmVIEW->value,list::alloc(2,ct,(*palm.weight)(ct)));
                else ;} 
              else (*palm.removeDecision)(_oid_(state),
                  ct);
                { ClaireHandler c_handle = ClaireHandler();
                if ERROR_IN 
                { { (*palm.remove)(_oid_(pb),
                      ct);
                    _void_(choco.propagate->fcall(((int) pb)));
                    } 
                  ClEnv->cHandle--;} 
                else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
                { c_handle.catchIt();(*palm.repair)(_oid_(algo));
                  } 
                else PREVIOUS_HANDLER;} 
              if ((*palm.weight)(ct) == 0)
               { delete_bag(e->explanation,ct);
                { OID  negct = GC_OID((*palm.negate)(ct));
                  if (negct != CNULL)
                   { { ClaireBoolean * g0314I;
                      { OID  g0315UU;
                        { OID gc_local;
                          ITERATE(g0313);
                          g0315UU= _oid_(CFALSE);
                          bag *g0313_support;
                          g0313_support = GC_OBJECT(set,e->explanation);
                          for (START(g0313_support); NEXT(g0313);)
                          { GC_LOOP;
                            if (not_any((*choco.isActive)(g0313)) == CTRUE)
                             { g0315UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        g0314I = not_any(g0315UU);
                        } 
                      
                      if (g0314I == CTRUE) { palm_clean_Explanation(e);
                          { ClaireHandler c_handle = ClaireHandler();
                            if ERROR_IN 
                            { { (*choco.post)(_oid_(pb),
                                  negct,
                                  _oid_(e));
                                _void_(choco.propagate->fcall(((int) pb)));
                                } 
                              ClEnv->cHandle--;} 
                            else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
                            { c_handle.catchIt();(*palm.repair)(_oid_(algo));
                              } 
                            else PREVIOUS_HANDLER;} 
                          } 
                        } 
                    } 
                  else ;} 
                } 
              } 
            else { (CLREAD(PalmEngine,pe,contradictory) = CFALSE);
                palm_flushEvents_PalmEngine(((PalmEngine *) pe));
                contradiction_I_void();
                } 
              } 
          else { (CLREAD(PalmEngine,pe,contradictory) = CFALSE);
              palm_flushEvents_PalmEngine(((PalmEngine *) pe));
              contradiction_I_void();
              } 
            } 
        } 
    } 
  GC_UNBIND;} 


// *************************************************
// * Part 6 : Maintaining the search state         *
// *************************************************
/* The c++ function for: addDecision(state:PalmState,ct:choco/AbstractConstraint) [] */
void  palm_addDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct)
{ GC_BIND;
  state->path->explanation->addFast(_oid_(ct));
  GC_UNBIND;} 


/* The c++ function for: reverseDecision(state:PalmState,ct:choco/AbstractConstraint) [] */
void  palm_reverseDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct)
{ delete_bag(state->path->explanation,_oid_(ct));
  } 


/* The c++ function for: removeDecision(state:PalmState,ct:choco/AbstractConstraint) [] */
void  palm_removeDecision_PalmState_palm(PalmState *state,AbstractConstraint *ct)
{ GC_BIND;
  (*palm.learnFromRemoval)(GC_OID(_oid_(state->manager->learning)),
    _oid_(ct));
  delete_bag(state->path->explanation,_oid_(ct));
  GC_UNBIND;} 


// *************************************************
// * Part 7 : Learning from contradictions         *
// *************************************************
// learning something from the contradiction ? 
/* The c++ function for: learnFromContradiction(learner:PalmLearn,e:Explanation) [] */
void  palm_learnFromContradiction_PalmLearn_palm(PalmLearn *learner,Explanation *e)
{ ;} 


/* The c++ function for: learnFromRemoval(learner:PalmLearn,ct:choco/AbstractConstraint) [] */
void  palm_learnFromRemoval_PalmLearn_palm(PalmLearn *learner,AbstractConstraint *ct)
{ ;} 


// this method checks if the set of decisions is acceptable by the solver
/* The c++ function for: checkAcceptable(memory:PalmLearn,cts:list[choco/AbstractConstraint]) [] */
ClaireBoolean * palm_checkAcceptable_PalmLearn_palm(PalmLearn *memory,list *cts)
{ return (CTRUE);} 


// this method orders the set of enumeration constraints (w=0) from an explanation 
/* The c++ function for: sortConstraintsToUndo(learner:PalmLearn,e:Explanation) [] */
list * palm_sortConstraintsToUndo_PalmLearn_palm(PalmLearn *learner,Explanation *e)
{ GC_BIND;
  { list *Result ;
    { list * g0316UU;
      { list * c_out = list::empty(choco._AbstractConstraint);
        { OID gc_local;
          ITERATE(c);
          bag *c_support;
          c_support = GC_OBJECT(set,e->explanation);
          for (START(c_support); NEXT(c);)
          { GC_LOOP;
            if ((*palm.weight)(c) == 0)
             c_out->addFast(c);
            GC_UNLOOP;} 
          } 
        g0316UU = GC_OBJECT(list,c_out);
        } 
      Result = sort_method(GC_OBJECT(method,((method *) _at_property1(palm.standard_better_ordered_constraint,OBJECT(ClaireClass,palm.AbstractConstraint->value)))),g0316UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkAcceptableRelaxation(learner:PalmLearn,ct_out:choco/AbstractConstraint) [] */
ClaireBoolean * palm_checkAcceptableRelaxation_PalmLearn_palm(PalmLearn *learner,AbstractConstraint *ct_out)
{ return (CTRUE);} 


// *************************************************
// * Part 8 : Choosing a constraint to undo        *
// *************************************************
// chosing a constraint to relax (here is the standard technique ... complete search ensured)
/* The c++ function for: selectDecisionToUndo(repairer:PalmRepair,e:Explanation) [] */
OID  palm_selectDecisionToUndo_PalmRepair_palm(PalmRepair *repairer,Explanation *e)
{ GC_BIND;
  { OID Result = 0;
    Result = (*Core.min)(GC_OID(_oid_(_at_property1(palm.standard_better_constraint,OBJECT(ClaireClass,palm.AbstractConstraint->value)))),
      GC_OID(_oid_(e->explanation)));
    GC_UNBIND; return (Result);} 
  } 


// chosing a constraint to relax (here need to ask permission)
/* The c++ function for: selectDecisionToUndo(repairer:PalmUnsureRepair,e:Explanation) [] */
OID  palm_selectDecisionToUndo_PalmUnsureRepair_palm(PalmUnsureRepair *repairer,Explanation *e)
{ GC_BIND;
  { OID Result = 0;
    { PalmSolver * solver = repairer->manager;
      PalmLearn * learner = solver->learning;
      PalmState * state = solver->state;
      list * cts = GC_OBJECT(list,OBJECT(list,(*palm.sortConstraintsToUndo)(_oid_(learner),
        _oid_(e))));
      int  idx_ct_out = 0;
      int  nbCandidates = cts->length;
      OID  ct_out = CNULL;
      ClaireBoolean * foundCandidate = CFALSE;
      { while (((foundCandidate != CTRUE) && 
            (idx_ct_out < nbCandidates)))
        { ++idx_ct_out;
          ct_out= (*(cts))[idx_ct_out];
          foundCandidate= OBJECT(ClaireBoolean,(*palm.checkAcceptableRelaxation)(_oid_(learner),
            ct_out));
          } 
        } 
      if (foundCandidate == CTRUE)
       Result = ct_out;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


// Standard constraint comparison
/* The c++ function for: standard_better_constraint(x:choco/AbstractConstraint,y:choco/AbstractConstraint) [] */
ClaireBoolean * palm_standard_better_constraint_AbstractConstraint(AbstractConstraint *x,AbstractConstraint *y)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  xh = x->hook;
      OID  yh = y->hook;
      int  wx = (*palm.weight)(xh);
      int  wy = (*palm.weight)(yh);
      int  tx = OBJECT(PalmInfoConstraint,xh)->timeStamp;
      int  ty = OBJECT(PalmInfoConstraint,yh)->timeStamp;
      Result = ((wx < wy) ? CTRUE : (((wx == wy) && 
          (tx > ty)) ? CTRUE : CFALSE));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: standard_better_ordered_constraint(ct1:choco/AbstractConstraint,ct2:choco/AbstractConstraint) [] */
ClaireBoolean * palm_standard_better_ordered_constraint_AbstractConstraint(AbstractConstraint *ct1,AbstractConstraint *ct2)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  h1 = ct1->hook;
      OID  h2 = ct2->hook;
      OID  i1 = OBJECT(PalmInfoConstraint,h1)->searchInfo;
      OID  i2 = OBJECT(PalmInfoConstraint,h2)->searchInfo;
      int  t1 = OBJECT(PalmInfoConstraint,h1)->timeStamp;
      int  t2 = OBJECT(PalmInfoConstraint,h2)->timeStamp;
      Result = ((boolean_I_any((*Kernel._sup)(i1,
        i2)) == CTRUE) ? CTRUE : (((equal(i1,i2) == CTRUE) && 
          (t1 > t2)) ? CTRUE : CFALSE));
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************
// * Part 9 : Default extension                    *
// *************************************************
/* The c++ function for: getObjectiveValue(pb:PalmProblem,obj:PalmIntVar,isMax:boolean) [] */
int  palm_getObjectiveValue_PalmProblem(PalmProblem *pb,PalmIntVar *obj,ClaireBoolean *isMax)
{ { int Result = 0;
    Result = ((isMax == CTRUE) ?
      choco.getSup->fcall(((int) obj)) :
      choco.getInf->fcall(((int) obj)) );
    return (Result);} 
  } 


/* The c++ function for: selectBranchingItem(b:PalmAssignVar) [] */
OID  palm_selectBranchingItem_PalmAssignVar_palm(PalmAssignVar *b)
{ GC_BIND;
  { OID Result = 0;
    Result = palm_getMinDomVar_list(GC_OBJECT(list,b->extender->manager->problem->vars));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: selectDecisions(b:PalmAssignVar,v:PalmIntVar) [] */
list * palm_selectDecisions_PalmAssignVar_palm(PalmAssignVar *b,PalmIntVar *v)
{ GC_BIND;
  { list *Result ;
    Result = list::alloc(choco._AbstractConstraint,1,GC_OID(_oid_(palm_assign_PalmIntVar(v,choco.getInf->fcall(((int) v))))));
    GC_UNBIND; return (Result);} 
  } 


// the default AssignVar 
/* The c++ function for: selectBranchingItem(b:PalmAssignMinDomDegVar) [] */
OID  palm_selectBranchingItem_PalmAssignMinDomDegVar_palm(PalmAssignMinDomDegVar *b)
{ GC_BIND;
  { OID Result = 0;
    Result = palm_getMinDomDegVar_list(GC_OBJECT(list,b->extender->manager->problem->vars));
    GC_UNBIND; return (Result);} 
  } 


// *************************************************
// * Part 10: Variable selection tools             *
// *************************************************
// selecting a variable to assign 
/* The c++ function for: getMinDomVar(pb:choco/Problem) [] */
OID  palm_getMinDomVar_Problem(Problem *pb)
{ GC_BIND;
  { OID Result = 0;
    Result = palm_getMinDomVar_list(GC_OBJECT(list,pb->vars));
    GC_UNBIND; return (Result);} 
  } 


// selecting a variable to assign
/* The c++ function for: getMinDomVar(l:list[choco/IntVar]) [] */
OID  palm_getMinDomVar_list(list *l)
{ { OID Result = 0;
    { int  minsize = 99999999;
      OID  v0 = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(l); NEXT(v);)
        { GC_LOOP;
          if (choco_getDomainSize_IntVar(OBJECT(IntVar,v)) > 1)
           { int  dsize = choco_getDomainSize_IntVar(OBJECT(IntVar,v));
            if (dsize < minsize)
             { minsize= dsize;
              GC__OID(v0 = v, 4);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = v0;
      } 
    return (Result);} 
  } 


// selecting a variable to assign 
/* The c++ function for: getMinDomDegVar(pb:choco/Problem) [] */
OID  palm_getMinDomDegVar_Problem(Problem *pb)
{ GC_BIND;
  { OID Result = 0;
    Result = palm_getMinDomDegVar_list(GC_OBJECT(list,pb->vars));
    GC_UNBIND; return (Result);} 
  } 


// selecting a variable to assign
/* The c++ function for: getMinDomDegVar(l:list[choco/IntVar]) [] */
OID  palm_getMinDomDegVar_list(list *l)
{ { OID Result = 0;
    { int  minsize = 99999999;
      OID  v0 = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(l); NEXT(v);)
        { GC_LOOP;
          if (choco_getDomainSize_IntVar(OBJECT(IntVar,v)) > 1)
           { int  dsize = (choco_getDomainSize_IntVar(OBJECT(IntVar,v))/(OBJECT(AbstractVar,v)->nbConstraints+1));
            if (dsize < minsize)
             { minsize= dsize;
              GC__OID(v0 = v, 4);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = v0;
      } 
    return (Result);} 
  } 


// *************************************************
// * Part 11: Solution management tools            *
// *************************************************
// pretty printing
/* The c++ function for: self_print(sol:PalmSolution) [] */
OID  claire_self_print_PalmSolution_palm(PalmSolution *sol)
{ if (sol->feasible == CTRUE)
   { princ_string("SOL ");
    { int  i = 1;
      int  g0317 = sol->algo->varsToStore->length;
      { OID gc_local;
        while ((i <= g0317))
        { // HOHO, GC_LOOP not needed !
          princ_string(OBJECT(AbstractVar,(*(sol->algo->varsToStore))[i])->name);
          princ_string(":");
          print_any((*(sol->lval))[i]);
          princ_string(" ");
          ++i;
          } 
        } 
      } 
    } 
  else princ_string("NO SOL ");
    { OID Result = 0;
    { princ_string("in ");
      print_any((*(sol->lstat))[1]);
      princ_string(" repairs ");
      print_any((*(sol->lstat))[2]);
      princ_string(" extensions ");
      print_any((*(sol->lstat))[3]);
      princ_string(" ms");
      } 
    return (Result);} 
  } 


/* The c++ function for: getRuntimeStatistic(sol:PalmSolution,s:{1, 2, 3}) [] */
int  palm_getRuntimeStatistic_PalmSolution(PalmSolution *sol,int s)
{ return ((*(sol->lstat))[s]);} 


/* The c++ function for: storeSolution(psolver:PalmSolver) [] */
void  palm_storeSolution_PalmSolver(PalmSolver *psolver)
{ GC_BIND;
  { PalmSolution * sol;
    { { PalmSolution * _CL_obj = ((PalmSolution *) GC_OBJECT(PalmSolution,new_object_class(palm._PalmSolution)));
        (_CL_obj->algo = psolver);
        (_CL_obj->lval = make_list_integer2(psolver->varsToStore->length,GC_OBJECT(ClaireType,U_type(Kernel._integer,set::alloc(Kernel.emptySet,1,CNULL))),-1));
        (_CL_obj->feasible = psolver->feasible);
        (_CL_obj->lstat = ((list *) copy_bag(psolver->statistics)));
        sol = _CL_obj;
        } 
      GC_OBJECT(PalmSolution,sol);} 
    { int  i = 1;
      int  g0318 = sol->lval->length;
      { OID gc_local;
        while ((i <= g0318))
        { // HOHO, GC_LOOP not needed !
          ((*(sol->lval))[i]=palm_firstValue_PalmIntVar(OBJECT(PalmIntVar,(*(psolver->varsToStore))[i])));
          ++i;
          } 
        } 
      } 
    GC_OBJECT(list,psolver->solutions)->addFast(_oid_(sol));
    } 
  GC_UNBIND;} 


/* The c++ function for: storeSolution(psolver:PalmBranchAndBound) [] */
void  palm_storeSolution_PalmBranchAndBound(PalmBranchAndBound *psolver)
{ GC_BIND;
  { PalmSolution * sol;
    { { PalmSolution * _CL_obj = ((PalmSolution *) GC_OBJECT(PalmSolution,new_object_class(palm._PalmSolution)));
        (_CL_obj->algo = psolver);
        (_CL_obj->lval = make_list_integer2(psolver->varsToStore->length,GC_OBJECT(ClaireType,U_type(Kernel._integer,set::alloc(Kernel.emptySet,1,CNULL))),-1));
        (_CL_obj->feasible = psolver->feasible);
        (_CL_obj->lstat = ((list *) copy_bag(psolver->statistics)));
        sol = _CL_obj;
        } 
      GC_OBJECT(PalmSolution,sol);} 
    int  bv = ((psolver->maximizing == CTRUE) ?
      choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(psolver->objective))))) :
      choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(psolver->objective))))) );
    if (sol->feasible == CTRUE)
     { if (palm.PalmOPTSHOW->value <= ClEnv->verbose)
       tformat_string("a new solution found ~S for ~A \n",palm.PalmOPTSHOW->value,list::alloc(2,bv,GC_OID(_string_(psolver->problem->name))));
      else ;} 
    { int  i = 1;
      int  g0319 = sol->lval->length;
      { OID gc_local;
        while ((i <= g0319))
        { // HOHO, GC_LOOP not needed !
          ((*(sol->lval))[i]=palm_firstValue_PalmIntVar(OBJECT(PalmIntVar,(*(psolver->varsToStore))[i])));
          ++i;
          } 
        } 
      } 
    ((*(sol->lval))[1]=bv);
    GC_OBJECT(list,psolver->solutions)->addFast(_oid_(sol));
    } 
  GC_UNBIND;} 


/* The c++ function for: discardCurrentSolution(ps:PalmState) [] */
ClaireBoolean * palm_discardCurrentSolution_PalmState(PalmState *ps)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireObject *V_CC ;
      { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { palm_reset_PalmSolver(GC_OBJECT(PalmSolver,ps->manager));
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { if (CLREAD(PalmEngine,ps->manager->problem->propagationEngine,dummyVariable) == (NULL))
                   { (CLREAD(PalmEngine,ps->manager->problem->propagationEngine,dummyVariable) = palm_makeBoundIntVar_PalmProblem(GC_OBJECT(PalmProblem,((PalmProblem *) ps->manager->problem->propagationEngine->problem)),"*dummy*",0,1));
                    delete_bag(ps->manager->problem->propagationEngine->problem->vars,_oid_(CLREAD(PalmEngine,ps->manager->problem->propagationEngine,dummyVariable)));
                    } 
                  (*choco.updateInf)(GC_OID(_oid_(CLREAD(PalmEngine,ps->manager->problem->propagationEngine,dummyVariable))),
                    2,
                    0,
                    GC_OID(_oid_(palm_clone_Explanation(GC_OBJECT(Explanation,ps->path)))));
                  } 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(palm._PalmContradiction)) == CTRUE)
              { c_handle.catchIt();(*palm.repair)(GC_OID(_oid_(ps->manager)));
                } 
              else PREVIOUS_HANDLER;} 
            V_CC = CTRUE;
            } 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();V_CC = CFALSE;
          } 
        else PREVIOUS_HANDLER;} 
      Result= (ClaireBoolean *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************
// * Part 12: A generic learner for Path Repair    *
// *************************************************
/* The c++ function for: makePathRepairLearn(lSize:integer,mMoves:integer) [] */
PathRepairLearn * palm_makePathRepairLearn_integer(int lSize,int mMoves)
{ GC_BIND;
  { PathRepairLearn *Result ;
    { PathRepairLearn * prl;
      { { PathRepairLearn * _CL_obj = ((PathRepairLearn *) GC_OBJECT(PathRepairLearn,new_object_class(palm._PathRepairLearn)));
          (_CL_obj->maxMoves = mMoves);
          (_CL_obj->maxSize = lSize);
          prl = _CL_obj;
          } 
        GC_OBJECT(PathRepairLearn,prl);} 
      { int  i = 1;
        int  g0320 = prl->maxSize;
        { OID gc_local;
          while ((i <= g0320))
          { GC_LOOP;
            { OID  g0321UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0321UU = _oid_(_CL_obj);
                } 
              GC_OBJECT(list,prl->explanations)->addFast(g0321UU);
              } 
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = prl;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makePathRepairLearn(c:class,lSize:integer,mMoves:integer) [] */
PathRepairLearn * palm_makePathRepairLearn_class(ClaireClass *c,int lSize,int mMoves)
{ GC_BIND;
  { PathRepairLearn *Result ;
    { PathRepairLearn * pr = GC_OBJECT(PathRepairLearn,((PathRepairLearn *) new_class1(c)));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(palm.maxMoves,palm._PathRepairLearn))),
        _oid_(pr),
        mMoves);
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.maxSize,palm._PathRepairLearn))),
        _oid_(pr),
        lSize);
      { int  i = 1;
        int  g0322 = pr->maxSize;
        { OID gc_local;
          while ((i <= g0322))
          { GC_LOOP;
            { OID  g0323UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0323UU = _oid_(_CL_obj);
                } 
              GC_OBJECT(list,pr->explanations)->addFast(g0323UU);
              } 
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = pr;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: addForbiddenSituation(prl:PathRepairLearn,ng:Explanation) [] */
void  palm_addForbiddenSituation_PathRepairLearn(PathRepairLearn *prl,Explanation *ng)
{ if (prl->isFull != CTRUE) 
  { { (prl->lastExplanation = (prl->lastExplanation+1));
      ((*(prl->explanations))[prl->lastExplanation]=_oid_(ng));
      if (prl->lastExplanation == prl->maxSize)
       { (prl->isFull = CTRUE);
        (prl->lastExplanation = 1);
        } 
      } 
     } 
  else{ GC_BIND;
    ((*(GC_OBJECT(list,prl->explanations)))[prl->lastExplanation]=GC_OID(_oid_(palm_clone_Explanation(ng))));
    (prl->lastExplanation = ((prl->lastExplanation == prl->maxSize) ?
      1 :
      (prl->lastExplanation+1) ));
    GC_UNBIND;} 
  } 


/* The c++ function for: isForbidden(prl:PathRepairLearn,ng:Explanation) [] */
ClaireBoolean * palm_isForbidden_PathRepairLearn(PathRepairLearn *prl,Explanation *ng)
{ { ClaireBoolean *Result ;
    { OID  g0326UU;
      { int  i = 1;
        int  g0324 = ((prl->isFull == CTRUE) ?
          prl->maxSize :
          prl->lastExplanation );
        { g0326UU= _oid_(CFALSE);
          while ((i <= g0324))
          { { ClaireBoolean * g0327I;
              { OID  g0328UU;
                { ITERATE(g0325);
                  g0328UU= _oid_(CFALSE);
                  for (START(OBJECT(Explanation,(*(prl->explanations))[i])->explanation); NEXT(g0325);)
                  if (belong_to(g0325,_oid_(ng)) != CTRUE)
                   { g0328UU = Kernel.ctrue;
                    break;} 
                  } 
                g0327I = not_any(g0328UU);
                } 
              
              if (g0327I == CTRUE) { g0326UU = Kernel.ctrue;
                  break;} 
                } 
            ++i;
            } 
          } 
        } 
      Result = boolean_I_any(g0326UU);
      } 
    return (Result);} 
  } 


// specification of interface functions 
/* The c++ function for: learnFromContradiction(learner:PathRepairLearn,e:Explanation) [] */
void  palm_learnFromContradiction_PathRepairLearn_palm(PathRepairLearn *learner,Explanation *e)
{ GC_BIND;
  { Explanation * ng;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        ng = _CL_obj;
        } 
      GC_OBJECT(Explanation,ng);} 
    PalmSolver * ps = GC_OBJECT(PalmSolver,learner->manager);
    int  mM = learner->maxMoves;
    int  gRS = (*(ps->statistics))[1];
    if (mM <= (*(ps->statistics))[1])
     { (CLREAD(PalmEngine,learner->manager->problem->propagationEngine,contradictory) = CFALSE);
      palm_flushEvents_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) learner->manager->problem->propagationEngine)));
      contradiction_I_void();
      } 
    { OID gc_local;
      ITERATE(c);
      bag *c_support;
      c_support = GC_OBJECT(set,e->explanation);
      for (START(c_support); NEXT(c);)
      { GC_LOOP;
        if ((*palm.weight)(c) == 0)
         GC_OBJECT(set,ng->explanation)->addFast(c);
        GC_UNLOOP;} 
      } 
    palm_addForbiddenSituation_PathRepairLearn(learner,ng);
    (*palm.informConstraintsInExplanation)(_oid_(learner),
      _oid_(ng));
    } 
  GC_UNBIND;} 


// informing constraints 
/* The c++ function for: informConstraintsInExplanation(prl:PathRepairLearn,s:Explanation) [] */
void  palm_informConstraintsInExplanation_PathRepairLearn_palm(PathRepairLearn *prl,Explanation *s)
{ if (s->explanation->length != 0) 
  { { double  sS = (1.0/to_float (size_set(s->explanation)));
      { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,s->explanation);
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          { OID  cth = GC_OID(OBJECT(PalmInfoConstraint,OBJECT(AbstractConstraint,ct)->hook)->searchInfo);
            if (cth != CNULL)
             (OBJECT(PalmInfoConstraint,OBJECT(AbstractConstraint,ct)->hook)->searchInfo = _float_(((float_v(OBJECT(PalmInfoConstraint,OBJECT(AbstractConstraint,ct)->hook)->searchInfo))+sS)));
            else (OBJECT(PalmInfoConstraint,OBJECT(AbstractConstraint,ct)->hook)->searchInfo = _float_(sS));
              } 
          GC_UNLOOP;} 
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: learnFromRemoval(learner:PathRepairLearn,ct:choco/AbstractConstraint) [] */
void  palm_learnFromRemoval_PathRepairLearn_palm(PathRepairLearn *learner,AbstractConstraint *ct)
{ (OBJECT(PalmInfoConstraint,ct->hook)->searchInfo = CNULL);
  } 


// this method checks if the set of decisions is acceptable by the solver
/* The c++ function for: checkAcceptable(memory:PathRepairLearn,cts:list[choco/AbstractConstraint]) [] */
ClaireBoolean * palm_checkAcceptable_PathRepairLearn_palm(PathRepairLearn *memory,list *cts)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { Explanation * testing;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          testing = _CL_obj;
          } 
        GC_OBJECT(Explanation,testing);} 
      PalmState * st = memory->manager->state;
      { OID gc_local;
        ITERATE(c);
        for (START(st->path->explanation); NEXT(c);)
        { GC_LOOP;
          testing->explanation->addFast(c);
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(c);
        for (START(cts); NEXT(c);)
        { GC_LOOP;
          testing->explanation->addFast(c);
          GC_UNLOOP;} 
        } 
      { OID  g0332UU;
        { ClaireBoolean * V_CL0333;{ OID  g0334UU;
            { int  g0329 = 1;
              int  g0330 = ((memory->isFull == CTRUE) ?
                memory->maxSize :
                memory->lastExplanation );
              { OID gc_local;
                g0334UU= _oid_(CFALSE);
                while ((g0329 <= g0330))
                { GC_LOOP;
                  { ClaireBoolean * g0335I;
                    { OID  g0336UU;
                      { OID gc_local;
                        ITERATE(g0331);
                        g0336UU= _oid_(CFALSE);
                        for (START(OBJECT(Explanation,(*(memory->explanations))[g0329])->explanation); NEXT(g0331);)
                        { GC_LOOP;
                          if (belong_to(g0331,_oid_(testing)) != CTRUE)
                           { g0336UU = Kernel.ctrue;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      g0335I = not_any(g0336UU);
                      } 
                    
                    if (g0335I == CTRUE) { g0334UU = Kernel.ctrue;
                        break;} 
                      } 
                  ++g0329;
                  GC_UNLOOP;} 
                } 
              } 
            V_CL0333 = boolean_I_any(g0334UU);
            } 
          
          g0332UU=_oid_(V_CL0333);} 
        Result = not_any(g0332UU);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkAcceptableRelaxation(learner:PathRepairLearn,ct_out:choco/AbstractConstraint) [] */
ClaireBoolean * palm_checkAcceptableRelaxation_PathRepairLearn_palm(PathRepairLearn *learner,AbstractConstraint *ct_out)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { Explanation * testing;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          testing = _CL_obj;
          } 
        GC_OBJECT(Explanation,testing);} 
      PalmState * st = learner->manager->state;
      OID  ct_in = GC_OID((*choco.opposite)(_oid_(ct_out)));
      { OID gc_local;
        ITERATE(c);
        for (START(st->path->explanation); NEXT(c);)
        { GC_LOOP;
          if (c != _oid_(ct_out))
           testing->explanation->addFast(c);
          GC_UNLOOP;} 
        } 
      testing->explanation->addFast(ct_in);
      { OID  g0340UU;
        { ClaireBoolean * V_CL0341;{ OID  g0342UU;
            { int  g0337 = 1;
              int  g0338 = ((learner->isFull == CTRUE) ?
                learner->maxSize :
                learner->lastExplanation );
              { OID gc_local;
                g0342UU= _oid_(CFALSE);
                while ((g0337 <= g0338))
                { GC_LOOP;
                  { ClaireBoolean * g0343I;
                    { OID  g0344UU;
                      { OID gc_local;
                        ITERATE(g0339);
                        g0344UU= _oid_(CFALSE);
                        for (START(OBJECT(Explanation,(*(learner->explanations))[g0337])->explanation); NEXT(g0339);)
                        { GC_LOOP;
                          if (belong_to(g0339,_oid_(testing)) != CTRUE)
                           { g0344UU = Kernel.ctrue;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      g0343I = not_any(g0344UU);
                      } 
                    
                    if (g0343I == CTRUE) { g0342UU = Kernel.ctrue;
                        break;} 
                      } 
                  ++g0337;
                  GC_UNLOOP;} 
                } 
              } 
            V_CL0341 = boolean_I_any(g0342UU);
            } 
          
          g0340UU=_oid_(V_CL0341);} 
        Result = not_any(g0340UU);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

