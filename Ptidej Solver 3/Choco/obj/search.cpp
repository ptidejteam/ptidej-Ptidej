/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\search.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:31 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: main.cl                                                    *
// *    solving                                                       *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// * A. Global search                                                 *
// *   Part 0: object model                                           *
// *   Part 1: utils and solution management                          *
// *   Part 2: exploring  one level of branching                      *
// *   Part 3: Feeding the engine with a library of branching objects *
// *   Part 4: Using branching classes within globalSearchSolver's    *
// * B. Local search (generating assignments, improving them by LO)   *
// *   Part 5: utils and solution management                          *
// *   Part 6: Feeding the engine with a library of neighborhoods     *
// *   Part 7: Using neighborhood classes within LocalSearchSolver's  *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 0: object model                                           *
// ********************************************************************
// v1.013 the range of the abstract interface properties needs be specified
// v1.013 add a parameter to getNextBranch and finishedBranching (index of the previous branch),
//        introduce getFirstBranch
// v1.013
// v0.9906
/* The c++ function for: selectBranchingObject(b:AbstractBranching) [] */
OID  choco_selectBranchingObject_AbstractBranching_choco(AbstractBranching *b)
{ close_exception(((general_error *) (*Core._general_error)(_string_("selectBranchingObject not defined on the abstract class AbstractBranching"),
    _oid_(Kernel.nil))));
  return (CNULL);} 


/* The c++ function for: goDownBranch(b:AbstractBranching,x:any,i:integer) [] */
void  choco_goDownBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("goDownBranch not defined on the abstract class AbstractBranching (called with ~S,~S)"),
    _oid_(list::alloc(2,x,i)))));
  } 


/* The c++ function for: traceDownBranch(b:AbstractBranching,x:any,i:integer) [] */
void  choco_traceDownBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("traceDownBranch not defined on the abstract class AbstractBranching (called with ~S,~S)"),
    _oid_(list::alloc(2,x,i)))));
  } 


// v0.9906: <thb> definitions required in order to have dynamic dispatch of the above interface methods
/* The c++ function for: getFirstBranch(b:AbstractBranching,x:any) [] */
int  choco_getFirstBranch_AbstractBranching_choco(AbstractBranching *b,OID x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getFirstBranch not defined on the abstract class AbstractBranching (called with ~S)"),
    _oid_(list::alloc(1,x)))));
  return (0);} 


/* The c++ function for: getNextBranch(b:AbstractBranching,x:any,i:integer) [] */
int  choco_getNextBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getNextBranch not defined on the abstract class AbstractBranching (called with ~S)"),
    _oid_(list::alloc(1,x)))));
  return (0);} 


/* The c++ function for: goUpBranch(b:AbstractBranching,x:any,i:integer) [] */
void  choco_goUpBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("goUpBranch not defined on the abstract class AbstractBranching (called with ~S,~S)"),
    _oid_(list::alloc(2,x,i)))));
  } 


/* The c++ function for: traceUpBranch(b:AbstractBranching,x:any,i:integer) [] */
void  choco_traceUpBranch_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("traceUpBranch not defined on the abstract class AbstractBranching (called with ~S,~S)"),
    _oid_(list::alloc(2,x,i)))));
  } 


/* The c++ function for: finishedBranching(b:AbstractBranching,x:any,i:integer) [] */
ClaireBoolean * choco_finishedBranching_AbstractBranching_choco(AbstractBranching *b,OID x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("finishedBranching not defined on the abstract class AbstractBranching (called with ~S)"),
    _oid_(list::alloc(1,x)))));
  return (CTRUE);} 


/* The c++ function for: branchOn(b:AbstractBranching,v:any,n:integer) [] */
ClaireBoolean * choco_branchOn_AbstractBranching(AbstractBranching *b,OID v,int n)
{ close_exception(((general_error *) (*Core._general_error)(_string_("branchOn not defined on the abstract class AbstractBranching (called with ~S,~S)"),
    _oid_(list::alloc(2,v,n)))));
  return (CFALSE);} 


// v0.9907 <thb> must be declared abstract
// v1.0
// v1.0
// v1.013 using a binary branching object as a large branching one
/* The c++ function for: goUpBranch(b:LargeBranching,x:any,i:integer) [] */
void  choco_goUpBranch_LargeBranching_choco(LargeBranching *b,OID x,int i)
{ ;} 


/* The c++ function for: traceUpBranch(b:LargeBranching,x:any,i:integer) [] */
void  choco_traceUpBranch_LargeBranching_choco(LargeBranching *b,OID x,int i)
{ ;} 


/* The c++ function for: getFirstBranch(b:LargeBranching,x:any) [] */
int  choco_getFirstBranch_LargeBranching_choco(LargeBranching *b,OID x)
{ return (1);} 


/* The c++ function for: getNextBranch(b:LargeBranching,x:any,i:integer) [] */
int  choco_getNextBranch_LargeBranching_choco(LargeBranching *b,OID x,int i)
{ return (2);} 


/* The c++ function for: finishedBranching(b:LargeBranching,x:any,i:integer) [] */
ClaireBoolean * choco_finishedBranching_LargeBranching_choco(LargeBranching *b,OID x,int i)
{ return (equal(i,2));} 


// v1.013: a new class for branching objects
// that may alternatively branch from several sub-branching objects
// the only method that should be defined
/* The c++ function for: selectBranching(b:CompositeBranching) [] */
AbstractBranching * choco_selectBranching_CompositeBranching(CompositeBranching *b)
{ close_exception(((general_error *) (*Core._general_error)(_string_("selectBranching not defined on the abstract class CompositeBranching"),
    _oid_(Kernel.nil))));
  return (b);} 


/* The c++ function for: selectBranchingObject(b:CompositeBranching) [] */
OID  choco_selectBranchingObject_CompositeBranching_choco(CompositeBranching *b)
{ GC_BIND;
  { OID Result = 0;
    { AbstractBranching * alt = choco_selectBranching_CompositeBranching(b);
      OID  x = GC_OID(choco.selectBranchingObject->fcall(((int) alt)));
      Result = _oid_(tuple::alloc(2,_oid_(alt),x));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getFirstBranch(b:CompositeBranching,xpair:any) [] */
int  choco_getFirstBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair)
{ { int Result = 0;
    { tuple * xp = OBJECT(tuple,xpair);
      AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
      OID  x = (*(xp))[2];
      Result = choco.getFirstBranch->fcall(((int) alt),((int) x));
      } 
    return (Result);} 
  } 


/* The c++ function for: getNextBranch(b:CompositeBranching,xpair:any,i:integer) [] */
int  choco_getNextBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { int Result = 0;
    { tuple * xp = OBJECT(tuple,xpair);
      AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
      OID  x = (*(xp))[2];
      Result = choco.getNextBranch->fcall(((int) alt),((int) x),((int) i));
      } 
    return (Result);} 
  } 


/* The c++ function for: goUpBranch(b:CompositeBranching,xpair:any,i:integer) [] */
void  choco_goUpBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { tuple * xp = OBJECT(tuple,xpair);
    AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
    OID  x = (*(xp))[2];
    _void_(choco.goUpBranch->fcall(((int) alt),((int) x),((int) i)));
    } 
  } 


/* The c++ function for: traceUpBranch(b:CompositeBranching,xpair:any,i:integer) [] */
void  choco_traceUpBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { tuple * xp = OBJECT(tuple,xpair);
    AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
    OID  x = (*(xp))[2];
    _void_(choco.traceUpBranch->fcall(((int) alt),((int) x),((int) i)));
    } 
  } 


/* The c++ function for: goDownBranch(b:CompositeBranching,xpair:any,i:integer) [] */
void  choco_goDownBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { tuple * xp = OBJECT(tuple,xpair);
    AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
    OID  x = (*(xp))[2];
    _void_(choco.goDownBranch->fcall(((int) alt),((int) x),((int) i)));
    } 
  } 


/* The c++ function for: traceDownBranch(b:CompositeBranching,xpair:any,i:integer) [] */
void  choco_traceDownBranch_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { tuple * xp = OBJECT(tuple,xpair);
    AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
    OID  x = (*(xp))[2];
    _void_(choco.traceDownBranch->fcall(((int) alt),((int) x),((int) i)));
    } 
  } 


/* The c++ function for: finishedBranching(b:CompositeBranching,xpair:any,i:integer) [] */
ClaireBoolean * choco_finishedBranching_CompositeBranching_choco(CompositeBranching *b,OID xpair,int i)
{ { ClaireBoolean *Result ;
    { tuple * xp = OBJECT(tuple,xpair);
      AbstractBranching * alt = OBJECT(AbstractBranching,(*(xp))[1]);
      OID  x = (*(xp))[2];
      Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.finishedBranching->fcall(((int) alt),((int) x),((int) i))));
      } 
    return (Result);} 
  } 


// v1.318: introducing heuristics
/* The c++ function for: selectVar(vs:VarSelector) [] */
OID  choco_selectVar_VarSelector(VarSelector *vs)
{ close_exception(((general_error *) (*Core._general_error)(_string_("selectVar is not defined on ~S"),
    _oid_(list::alloc(1,_oid_(vs))))));
  return (CNULL);} 


/* The c++ function for: selectVar(vs:MinDomain) [] */
OID  choco_selectVar_MinDomain(MinDomain *vs)
{ if (vs->vars->length > 0) 
  { { OID Result = 0;
      { int  g1136 = 99999999;
        OID  g1137 = CNULL;
        { OID gc_local;
          ITERATE(g1138);
          bag *g1138_support;
          g1138_support = GC_OBJECT(list,vs->vars);
          for (START(g1138_support); NEXT(g1138);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1138))))) == CTRUE)
             { int  g1140 = choco_getDomainSize_IntVar(OBJECT(IntVar,g1138));
              if (g1140 < g1136)
               { g1136= g1140;
                GC__OID(g1137 = g1138, 4);
                } 
              } 
            GC_UNLOOP;} 
          } 
        Result = g1137;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = choco_getSmallestDomainUnassignedVar_Problem(choco_getProblem_Solver(choco_getSearchManager_AbstractBranching(vs->branching)));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: selectVar(vs:MaxDeg) [] */
OID  choco_selectVar_MaxDeg(MaxDeg *vs)
{ if (vs->vars->length > 0) 
  { { OID Result = 0;
      { int  g1141 = 0;
        OID  g1142 = CNULL;
        { OID gc_local;
          ITERATE(g1143);
          bag *g1143_support;
          g1143_support = GC_OBJECT(list,vs->vars);
          for (START(g1143_support); NEXT(g1143);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1143))))) == CTRUE)
             { int  g1145 = choco_getDegree_AbstractVar(OBJECT(AbstractVar,g1143));
              if (g1141 < g1145)
               { g1141= g1145;
                GC__OID(g1142 = g1143, 4);
                } 
              } 
            GC_UNLOOP;} 
          } 
        Result = g1142;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = choco_getMostConstrainedUnassignedVar_Problem(choco_getProblem_Solver(choco_getSearchManager_AbstractBranching(vs->branching)));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: selectVar(vs:DomDeg) [] */
OID  choco_selectVar_DomDeg(DomDeg *vs)
{ if (vs->vars->length > 0) 
  { { OID Result = 0;
      { int  g1146 = 99999999;
        int  g1147 = 0;
        OID  g1148 = CNULL;
        { OID gc_local;
          ITERATE(g1149);
          bag *g1149_support;
          g1149_support = GC_OBJECT(list,vs->vars);
          for (START(g1149_support); NEXT(g1149);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1149))))) == CTRUE)
             { int  g1151 = choco_getDomainSize_IntVar(OBJECT(IntVar,g1149));
              int  g1152 = choco_getDegree_AbstractVar(OBJECT(AbstractVar,g1149));
              if ((g1151*g1147) < (g1146*g1152))
               { g1146= g1151;
                g1147= g1152;
                GC__OID(g1148 = g1149, 5);
                } 
              } 
            GC_UNLOOP;} 
          } 
        Result = g1148;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = choco_getDomOverDegBestUnassignedVar_Problem(choco_getProblem_Solver(choco_getSearchManager_AbstractBranching(vs->branching)));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: selectVar(vs:StaticVarOrder) [] */
OID  choco_selectVar_StaticVarOrder(StaticVarOrder *vs)
{ GC_BIND;
  { OID Result = 0;
    { OID  v_some = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(vs->vars); NEXT(v);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
           { v_some= v;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = v_some;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isOver(vi:ValIterator,x:IntVar,i:integer) [] */
ClaireBoolean * choco_isOver_ValIterator(ValIterator *vi,IntVar *x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("isOver is not defined on ~S"),
    _oid_(list::alloc(1,_oid_(vi))))));
  return (CTRUE);} 


/* The c++ function for: getFirstVal(vi:ValIterator,x:IntVar) [] */
int  choco_getFirstVal_ValIterator(ValIterator *vi,IntVar *x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getFirstVal is not defined on ~S"),
    _oid_(list::alloc(1,_oid_(vi))))));
  return (0);} 


/* The c++ function for: getNextVal(vi:ValIterator,x:IntVar,i:integer) [] */
int  choco_getNextVal_ValIterator(ValIterator *vi,IntVar *x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getNextVal is not defined on ~S"),
    _oid_(list::alloc(1,_oid_(vi))))));
  return (0);} 


/* The c++ function for: isOver(vi:IncreasingDomain,x:IntVar,i:integer) [] */
ClaireBoolean * choco_isOver_IncreasingDomain(IncreasingDomain *vi,IntVar *x,int i)
{ return (_inf_equal_integer(x->sup->latestValue,i));} 


/* The c++ function for: getFirstVal(vi:IncreasingDomain,x:IntVar) [] */
int  choco_getFirstVal_IncreasingDomain(IncreasingDomain *vi,IntVar *x)
{ return (x->inf->latestValue);} 


/* The c++ function for: getNextVal(vi:IncreasingDomain,x:IntVar,i:integer) [] */
int  choco_getNextVal_IncreasingDomain(IncreasingDomain *vi,IntVar *x,int i)
{ return (choco_getNextDomainValue_IntVar(x,i));} 


/* The c++ function for: isOver(vi:DecreasingDomain,x:IntVar,i:integer) [] */
ClaireBoolean * choco_isOver_DecreasingDomain(DecreasingDomain *vi,IntVar *x,int i)
{ return (_inf_equal_integer(i,x->inf->latestValue));} 


/* The c++ function for: getFirstVal(vi:DecreasingDomain,x:IntVar) [] */
int  choco_getFirstVal_DecreasingDomain(DecreasingDomain *vi,IntVar *x)
{ return (x->sup->latestValue);} 


/* The c++ function for: getNextVal(vi:DecreasingDomain,x:IntVar,i:integer) [] */
int  choco_getNextVal_DecreasingDomain(DecreasingDomain *vi,IntVar *x,int i)
{ return (choco_getPrevDomainValue_IntVar(x,i));} 


/* The c++ function for: getBestVal(vh:ValSelector,x:IntVar) [] */
int  choco_getBestVal_ValSelector(ValSelector *vh,IntVar *x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getBestVal is not defined on ~S"),
    _oid_(list::alloc(1,_oid_(vh))))));
  return (0);} 


/* The c++ function for: getBestVal(vh:MidValHeuristic,x:IntVar) [] */
int  choco_getBestVal_MidValHeuristic(MidValHeuristic *vh,IntVar *x)
{ return ((x->inf->latestValue+((x->sup->latestValue-x->inf->latestValue)/2)));} 


/* The c++ function for: getBestVal(vh:MinValHeuristic,x:IntVar) [] */
int  choco_getBestVal_MinValHeuristic(MinValHeuristic *vh,IntVar *x)
{ return (x->inf->latestValue);} 


/* The c++ function for: getBestVal(vh:MaxValHeuristic,x:IntVar) [] */
int  choco_getBestVal_MaxValHeuristic(MaxValHeuristic *vh,IntVar *x)
{ return (x->sup->latestValue);} 


// note: this could be enriched into a LargeBranching, considering LargeDisjunctions
// v1.010: the branching now explicitly stores the list of disjunctions
// v1.0 accessing the objective value of an optimization problem
// (note that the objective value may not be instantiated)
/* The c++ function for: getObjectiveValue(a:AbstractOptimize) [] */
int  choco_getObjectiveValue_AbstractOptimize_choco(AbstractOptimize *a)
{ { int Result = 0;
    Result = ((a->doMaximize == CTRUE) ?
      a->objective->sup->latestValue :
      a->objective->inf->latestValue );
    return (Result);} 
  } 


// v1.0 for PaLM
// v1.05 <thb> getBestObjectiveValue
/* The c++ function for: getBestObjectiveValue(a:AbstractOptimize) [] */
int  choco_getBestObjectiveValue_AbstractOptimize(AbstractOptimize *a)
{ { int Result = 0;
    Result = ((a->doMaximize == CTRUE) ?
      a->lowerBound :
      a->upperBound );
    return (Result);} 
  } 


// v1.08: the target for the objective function: we are searching for a solution
// at least as good as this
/* The c++ function for: getObjectiveTarget(a:AbstractOptimize) [] */
int  choco_getObjectiveTarget_AbstractOptimize(AbstractOptimize *a)
{ { int Result = 0;
    Result = ((a->doMaximize == CTRUE) ?
      a->targetLowerBound :
      a->targetUpperBound );
    return (Result);} 
  } 


// v1.013 initialization of the optimization bound data structure
/* The c++ function for: initBounds(a:AbstractOptimize) [] */
void  choco_initBounds_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  { IntVar * obj = GC_OBJECT(IntVar,a->objective);
    (a->lowerBound = choco.getInf->fcall(((int) obj)));
    (a->upperBound = choco.getSup->fcall(((int) obj)));
    (a->targetLowerBound = choco.getInf->fcall(((int) obj)));
    (a->targetUpperBound = choco.getSup->fcall(((int) obj)));
    } 
  GC_UNBIND;} 


// v1.02: returns a boolean indicating whether the search found solutions or not
/* The c++ function for: getFeasibility(a:GlobalSearchSolver) [] */
ClaireBoolean * choco_getFeasibility_GlobalSearchSolver(GlobalSearchSolver *a)
{ return (a->problem->feasible);} 


// v1.320: may we generate a new node ?
/* The c++ function for: mayExpandNewNode(lim:GlobalSearchLimit,algo:GlobalSearchSolver) [] */
ClaireBoolean * choco_mayExpandNewNode_GlobalSearchLimit(GlobalSearchLimit *lim,GlobalSearchSolver *algo)
{ return (CTRUE);} 


/* The c++ function for: mayExpandNewNode(lim:NodeLimit,algo:GlobalSearchSolver) [] */
ClaireBoolean * choco_mayExpandNewNode_NodeLimit(NodeLimit *lim,GlobalSearchSolver *algo)
{ return (_inf_equal_integer(algo->nbNd,lim->maxNb));} 


/* The c++ function for: mayExpandNewNode(lim:BacktrackLimit,algo:GlobalSearchSolver) [] */
ClaireBoolean * choco_mayExpandNewNode_BacktrackLimit(BacktrackLimit *lim,GlobalSearchSolver *algo)
{ return (_inf_equal_integer(algo->nbBk,lim->maxNb));} 


/* The c++ function for: mayExpandNewNode(lim:TimeLimit,algo:GlobalSearchSolver) [] */
ClaireBoolean * choco_mayExpandNewNode_TimeLimit(TimeLimit *lim,GlobalSearchSolver *algo)
{ return (_inf_equal_integer(time_read_void(),lim->maxMsec));} 


// used for optimization by branch and bound
/* The c++ function for: copyParameters(oldSolver:GlobalSearchSolver,newSolver:GlobalSearchSolver) [] */
void  choco_copyParameters_GlobalSearchSolver(GlobalSearchSolver *oldSolver,GlobalSearchSolver *newSolver)
{ { int  d = oldSolver->maxPrintDepth;
    if (d == (CNULL))
     ;else (newSolver->maxPrintDepth = d);
      } 
  { int  storeBest = oldSolver->maxSolutionStorage;
    if (storeBest == (CNULL))
     ;else (newSolver->maxSolutionStorage = storeBest);
      } 
  { set * varsShow = oldSolver->varsToShow;
    if (varsShow == (NULL))
     ;else (newSolver->varsToShow = varsShow);
      } 
  } 


// v1.08 new name attachGlobalSearchSolver -> attach
/* The c++ function for: attach(newSolver:GlobalSearchSolver,pb:Problem) [] */
void  choco_attach_GlobalSearchSolver(GlobalSearchSolver *newSolver,Problem *pb)
{ (pb->globalSearchSolver = newSolver);
  (newSolver->problem = pb);
  } 


// v1.0, v1.013: more precise return type
/* The c++ function for: composeGlobalSearch(algo:GlobalSearchSolver,l:list[AbstractBranching]) [] */
GlobalSearchSolver * choco_composeGlobalSearch_GlobalSearchSolver(GlobalSearchSolver *algo,list *l)
{ { GlobalSearchSolver *Result ;
    { int  n = l->length;
      list * l2 = l;
      (algo->branchingComponents = l2);
      { int  i = 1;
        int  g1153 = (n-1);
        { while ((i <= g1153))
          { (OBJECT(AbstractBranching,(*(l2))[i])->nextBranching = OBJECT(AbstractBranching,(*(l2))[(i+1)]));
            ++i;
            } 
          } 
        } 
      { ITERATE(comp);
        for (START(l2); NEXT(comp);)
        (OBJECT(AbstractBranching,comp)->manager = algo);
        } 
      Result = algo;
      } 
    return (Result);} 
  } 


/* The c++ function for: choco_composeGlobalSearch_GlobalSearchSolver_type */
ClaireType * choco_composeGlobalSearch_GlobalSearchSolver_type(ClaireType *algo,ClaireType *l)
{ return (algo);} 


// ********************************************************************
// *   Part 1: utils and solution management                          *
// ********************************************************************
/* The c++ function for: getSmallestDomainUnassignedVar(pb:Problem) [] */
OID  choco_getSmallestDomainUnassignedVar_Problem(Problem *pb)
{ GC_BIND;
  { OID Result = 0;
    { int  g1154 = 99999999;
      OID  g1155 = CNULL;
      { OID gc_local;
        ITERATE(g1156);
        bag *g1156_support;
        g1156_support = GC_OBJECT(list,pb->vars);
        for (START(g1156_support); NEXT(g1156);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1156))))) == CTRUE)
           { int  g1158 = choco_getDomainSize_IntVar(OBJECT(IntVar,g1156));
            if (g1158 < g1154)
             { g1154= g1158;
              GC__OID(g1155 = g1156, 4);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = g1155;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 inlined <thb>
/* The c++ function for: getSmallestDomainUnassignedVar(l:list[IntVar]) [] */
OID  choco_getSmallestDomainUnassignedVar_list(list *l)
{ { OID Result = 0;
    { int  minsize = 99999999;
      OID  v0 = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(l); NEXT(v);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
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


// v1.010: a new variable selection heuristic
/* The c++ function for: getDomOverDegBestUnassignedVar(pb:Problem) [] */
OID  choco_getDomOverDegBestUnassignedVar_Problem(Problem *pb)
{ GC_BIND;
  { OID Result = 0;
    { int  g1159 = 99999999;
      int  g1160 = 0;
      OID  g1161 = CNULL;
      { OID gc_local;
        ITERATE(g1162);
        bag *g1162_support;
        g1162_support = GC_OBJECT(list,pb->vars);
        for (START(g1162_support); NEXT(g1162);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1162))))) == CTRUE)
           { int  g1164 = choco_getDomainSize_IntVar(OBJECT(IntVar,g1162));
            int  g1165 = choco_getDegree_AbstractVar(OBJECT(AbstractVar,g1162));
            if ((g1164*g1160) < (g1159*g1165))
             { g1159= g1164;
              g1160= g1165;
              GC__OID(g1161 = g1162, 5);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = g1161;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomOverDegBestUnassignedVar(l:list[IntVar]) [] */
OID  choco_getDomOverDegBestUnassignedVar_list(list *l)
{ { OID Result = 0;
    { int  minsize = 99999999;
      int  maxdeg = 0;
      OID  v0 = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(l); NEXT(v);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
           { int  dsize = choco_getDomainSize_IntVar(OBJECT(IntVar,v));
            int  deg = choco_getDegree_AbstractVar(OBJECT(AbstractVar,v));
            if ((dsize*maxdeg) < (minsize*deg))
             { minsize= dsize;
              maxdeg= deg;
              GC__OID(v0 = v, 5);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = v0;
      } 
    return (Result);} 
  } 


// v1.010: a new variable selection heuristic
/* The c++ function for: getMostConstrainedUnassignedVar(pb:Problem) [] */
OID  choco_getMostConstrainedUnassignedVar_Problem(Problem *pb)
{ GC_BIND;
  { OID Result = 0;
    { int  g1166 = 0;
      OID  g1167 = CNULL;
      { OID gc_local;
        ITERATE(g1168);
        for (START(pb->vars); NEXT(g1168);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,g1168))))) == CTRUE)
           { int  g1170 = choco_getDegree_AbstractVar(OBJECT(AbstractVar,g1168));
            if (g1166 < g1170)
             { g1166= g1170;
              GC__OID(g1167 = g1168, 4);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = g1167;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getMostConstrainedUnassignedVar(l:list[IntVar]) [] */
OID  choco_getMostConstrainedUnassignedVar_list(list *l)
{ { OID Result = 0;
    { int  maxdeg = 0;
      OID  v0 = CNULL;
      { OID gc_local;
        ITERATE(v);
        for (START(l); NEXT(v);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
           { int  deg = choco_getDegree_AbstractVar(OBJECT(AbstractVar,v));
            if (maxdeg < deg)
             { maxdeg= deg;
              GC__OID(v0 = v, 4);
              } 
            } 
          GC_UNLOOP;} 
        } 
      Result = v0;
      } 
    return (Result);} 
  } 


// stores information from the current state in the next solution of the problem
// note: only instantiated variables are recorded in the Solution object
//       either all variables or a user-defined subset of them are recorded
//       this may erase a soolution that was previously stored in the ith position
//       this may also increase the size of the pb.solutions vector.
// v1.013: code has been redesigned
/* The c++ function for: storeCurrentSolution(a:Solver) [] */
void  choco_storeCurrentSolution_Solver(Solver *a)
{ GC_BIND;
  { Solution * sol = GC_OBJECT(Solution,choco_makeSolutionFromCurrentState_Solver(a));
    choco_storeSolution_Solver(a,sol);
    } 
  GC_UNBIND;} 


/* The c++ function for: makeSolutionFromCurrentState(a:Solver) [] */
Solution * choco_makeSolutionFromCurrentState_Solver(Solver *a)
{ GC_BIND;
  { Solution *Result ;
    { list * lvar = GC_OBJECT(list,((a->varsToStore->length != 0) ?
        a->varsToStore :
        a->problem->vars ));
      int  nbv = lvar->length;
      Solution * sol = GC_OBJECT(Solution,choco_makeSolution_Solver(a,nbv));
      (sol->time = time_read_void());
      (sol->nbBk = CLREAD(GlobalSearchSolver,a,nbBk));
      (sol->nbNd = CLREAD(GlobalSearchSolver,a,nbNd));
      if (INHERIT(a->isa,choco._AbstractOptimize))
       (sol->objectiveValue = (*choco.getObjectiveValue)(_oid_(a)));
      { int  i = 1;
        int  g1171 = nbv;
        { OID gc_local;
          while ((i <= g1171))
          { // HOHO, GC_LOOP not needed !
            { IntVar * v = OBJECT(IntVar,(*(lvar))[i]);
              if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v)))) == CTRUE)
               ((*(sol->lval))[i]=v->value);
              } 
            ++i;
            } 
          } 
        } 
      Result = sol;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: storeSolution(a:Solver,sol:Solution) [] */
void  choco_storeSolution_Solver(Solver *a,Solution *sol)
{ GC_BIND;
  if (choco.SVIEW->value <= ClEnv->verbose)
   tformat_string("store solution ~S \n",choco.SVIEW->value,list::alloc(1,_oid_(sol)));
  else ;if (a->solutions->length == CLREAD(GlobalSearchSolver,a,maxSolutionStorage))
   (a->solutions = skip_list(a->solutions,1));
  GC_OBJECT(list,a->solutions)->addFast(_oid_(sol));
  GC_UNBIND;} 


// v1.014
// v1.013 no longer an integer parameter
/* The c++ function for: existsSolution(a:Solver) [] */
ClaireBoolean * choco_existsSolution_Solver(Solver *a)
{ return (_sup_integer(a->solutions->length,0));} 


// v1.013 no longer an integer parameter
/* The c++ function for: restoreBestSolution(a:Solver) [] */
void  choco_restoreBestSolution_Solver(Solver *a)
{ GC_BIND;
  choco_restoreSolution_Solution(GC_OBJECT(Solution,OBJECT(Solution,last_list(a->solutions))));
  GC_UNBIND;} 


/* The c++ function for: restoreSolution(s:Solution) [] */
void  choco_restoreSolution_Solution(Solution *s)
{ GC_BIND;
  { Solver * a = GC_OBJECT(Solver,s->algo);
    list * lvar = GC_OBJECT(list,((a->varsToStore->length != 0) ?
      a->varsToStore :
      a->problem->vars ));
    list * lval = GC_OBJECT(list,s->lval);
    int  nbv = lvar->length;
    { int  i = 1;
      int  g1172 = nbv;
      { OID gc_local;
        while ((i <= g1172))
        { // HOHO, GC_LOOP not needed !
          if ((*(lval))[i] != CNULL)
           choco_instantiate_IntVar2(OBJECT(IntVar,(*(lvar))[i]),(*(lval))[i],0);
          ++i;
          } 
        } 
      } 
    choco_propagate_Problem(GC_OBJECT(Problem,a->problem));
    } 
  GC_UNBIND;} 


// printing the current state of domains when a solution has been reached
/* The c++ function for: showSolution(a:Solver) [] */
OID  choco_showSolution_Solver(Solver *a)
{ if (choco.STALK->value <= ClEnv->verbose) 
  { { OID Result = 0;
      { bag * S;
        { if (a->varsToShow->length != 0)
           S = a->varsToShow;
          else S = a->problem->vars;
            GC_OBJECT(bag,S);} 
        { ITERATE(x);
          Result= _oid_(CFALSE);
          for (START(S); NEXT(x);)
          { print_any(x);
            princ_string("\n");
            } 
          } 
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
    } 
  } 


// ********************************************************************
// *   Part 2: exploring  one level of branching                      *
// ********************************************************************
// explore is a general method that applies on any new descendent or AbtractBranching
// The heart of the search engine:
// exploring the branches of one choice point
/* The c++ function for: explore(b:AbstractBranching,n:integer) [] */
ClaireBoolean * choco_explore_AbstractBranching(AbstractBranching *b,int n)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { GlobalSearchSolver * algo = GC_OBJECT(GlobalSearchSolver,b->manager);
      Problem * pb = GC_OBJECT(Problem,algo->problem);
      OID  v = GC_OID(choco.selectBranchingObject->fcall(((int) b)));
      if (v != CNULL)
       Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.branchOn->fcall(((int) b),((int) v),((int) n))));
      else { AbstractBranching * b2 = GC_OBJECT(AbstractBranching,b->nextBranching);
          if (b2 == (NULL))
           { _void_((INHERIT(algo->isa,choco._AbstractOptimize) ?  choco_recordSolution_AbstractOptimize((AbstractOptimize *) OBJECT(AbstractOptimize,_oid_(algo))) :   choco_recordSolution_GlobalSearchSolver((GlobalSearchSolver *) OBJECT(GlobalSearchSolver,_oid_(algo)))));
            choco_showSolution_Solver(algo);
            Result = algo->stopAtFirstSol;
            } 
          else Result = choco_explore_AbstractBranching(b2,n);
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// v1.010: <thb> new method responsible for expanding a node of the search tree
// once the branching object has been generated
/* The c++ function for: branchOn(b:LargeBranching,v:any,n:integer) [] */
ClaireBoolean * choco_branchOn_LargeBranching(LargeBranching *b,OID v,int n)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { ClaireBoolean *Result ;
    { GlobalSearchSolver * algo = GC_OBJECT(GlobalSearchSolver,b->manager);
      Problem * pb = GC_OBJECT(Problem,algo->problem);
      ClaireBoolean * nodeSuccess = CFALSE;
      ClaireBoolean * nodeFinished = CFALSE;
      int  i = choco.getFirstBranch->fcall(((int) b),((int) v));
      choco_newNode_GlobalSearchSolver(algo);
      { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { OID gc_local;
            ClaireBoolean *v_while;
            v_while = CFALSE;
            
            while (v_while != CTRUE)
            { GC_LOOP;
              { ClaireBoolean * branchSuccess = CFALSE;
                { ClaireHandler c_handle = ClaireHandler();
                  if ERROR_IN 
                  { { ;{ OID  g1173test = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
                        if (g1173test != CNULL)
                         { EventCollection * g1173 = OBJECT(EventCollection,g1173test);
                          close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
                            _oid_(list::alloc(2,world_number(),_oid_(g1173))))));
                          } 
                        else { world_push();
                            ;} 
                          } 
                      if (n <= algo->maxPrintDepth)
                       _void_(choco.traceDownBranch->fcall(((int) b),((int) v),((int) i)));
                      _void_(choco.goDownBranch->fcall(((int) b),((int) v),((int) i)));
                      if (choco_explore_AbstractBranching(b,(n+1)) == CTRUE)
                       branchSuccess= CTRUE;
                      } 
                    ClEnv->cHandle--;} 
                  else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
                  { c_handle.catchIt();;} 
                  else PREVIOUS_HANDLER;} 
                if (branchSuccess != CTRUE)
                 { world_pop();
                  ;;choco_endNode_GlobalSearchSolver(algo);
                  (*choco.postDynamicCut)(_oid_(algo));
                  if (n <= algo->maxPrintDepth)
                   _void_(choco.traceUpBranch->fcall(((int) b),((int) v),((int) i)));
                  _void_(choco.goUpBranch->fcall(((int) b),((int) v),((int) i)));
                  } 
                if (branchSuccess == CTRUE)
                 nodeSuccess= CTRUE;
                if (not_any(_oid_((ClaireObject *) choco.finishedBranching->fcall(((int) b),((int) v),((int) i)))) == CTRUE)
                 i= choco.getNextBranch->fcall(((int) b),((int) v),((int) i));
                else nodeFinished= CTRUE;
                  } 
              v_while = ((nodeSuccess == CTRUE) ? CTRUE : ((nodeFinished == CTRUE) ? CTRUE : CFALSE));
              GC_UNLOOP;} 
            } 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();nodeSuccess= CFALSE;
          } 
        else PREVIOUS_HANDLER;} 
      Result = nodeSuccess;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.103: the branches of a BinBranching are no longer labelled with a boolean
// but with an integer (1/2) like LargeBranching
/* The c++ function for: branchOn(b:BinBranching,v:any,n:integer) [] */
ClaireBoolean * choco_branchOn_BinBranching(BinBranching *b,OID v,int n)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { GlobalSearchSolver * algo = GC_OBJECT(GlobalSearchSolver,b->manager);
      Problem * pb = GC_OBJECT(Problem,algo->problem);
      ClaireBoolean * success = CFALSE;
      choco_newNode_GlobalSearchSolver(algo);
      { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { ;{ OID  g1174test = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
              if (g1174test != CNULL)
               { EventCollection * g1174 = OBJECT(EventCollection,g1174test);
                close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
                  _oid_(list::alloc(2,world_number(),_oid_(g1174))))));
                } 
              else { world_push();
                  ;} 
                } 
            if (n <= algo->maxPrintDepth)
             _void_(choco.traceDownBranch->fcall(((int) b),((int) v),((int) 1)));
            _void_(choco.goDownBranch->fcall(((int) b),((int) v),((int) 1)));
            if (choco_explore_AbstractBranching(b,(n+1)) == CTRUE)
             success= CTRUE;
            } 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();;} 
        else PREVIOUS_HANDLER;} 
      if (success != CTRUE)
       { world_pop();
        ;;choco_endNode_GlobalSearchSolver(algo);
        { ClaireHandler c_handle = ClaireHandler();
          if ERROR_IN 
          { { (*choco.postDynamicCut)(_oid_(algo));
              if (n <= algo->maxPrintDepth)
               _void_(choco.traceDownBranch->fcall(((int) b),((int) v),((int) 2)));
              _void_(choco.goDownBranch->fcall(((int) b),((int) v),((int) 2)));
              if (choco_explore_AbstractBranching(b,(n+1)) == CTRUE)
               success= CTRUE;
              } 
            ClEnv->cHandle--;} 
          else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
          { c_handle.catchIt();success= CFALSE;
            } 
          else PREVIOUS_HANDLER;} 
        } 
      Result = success;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 3: Feeding the engine with a library of branching objects *
// ********************************************************************
// library of choice points (Branching classes)
// v1.010: now caching the middle of the domain (and not recomputing it)
/* The c++ function for: selectBranchingObject(b:SplitDomain) [] */
OID  choco_selectBranchingObject_SplitDomain_choco(SplitDomain *b)
{ GC_BIND;
  { OID Result = 0;
    { OID  xtest = GC_OID(choco.selectVar->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->varHeuristic))))));
      if (xtest != CNULL)
       { IntVar * x = OBJECT(IntVar,xtest);
        if (b->dichotomyThreshold <= choco_getDomainSize_IntVar(x))
         Result = _oid_(tuple::alloc(2,_oid_(x),choco.getBestVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->valHeuristic)))),((int) x))));
        else Result = CNULL;
          } 
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: goDownBranch(b:SplitDomain,x:any,first:integer) [] */
void  choco_goDownBranch_SplitDomain_choco(SplitDomain *b,OID x,int first)
{ GC_BIND;
  { tuple * xp = OBJECT(tuple,x);
    IntVar * v = OBJECT(IntVar,(*(xp))[1]);
    int  mid = (*(xp))[2];
    if (first == 1)
     (*choco.updateSup)(_oid_(v),
      mid,
      0);
    else (*choco.updateInf)(_oid_(v),
        (mid+1),
        0);
      choco_propagate_Problem(GC_OBJECT(Problem,b->manager->problem));
    } 
  GC_UNBIND;} 


/* The c++ function for: traceDownBranch(b:SplitDomain,x:any,first:integer) [] */
void  choco_traceDownBranch_SplitDomain_choco(SplitDomain *b,OID x,int first)
{ { tuple * xp = OBJECT(tuple,x);
    IntVar * v = OBJECT(IntVar,(*(xp))[1]);
    int  mid = (*(xp))[2];
    if (first == 1)
     { if (choco.STALK->value <= ClEnv->verbose)
       tformat_string("!!! branch ~S: ~S <= ~S \n",choco.STALK->value,list::alloc(3,world_number(),
        _oid_(v),
        mid));
      else ;} 
    else if (choco.STALK->value <= ClEnv->verbose)
     tformat_string("!!! branch ~S: ~S > ~S \n",choco.STALK->value,list::alloc(3,world_number(),
      _oid_(v),
      mid));
    else ;} 
  } 


// <ega> v0.9907 removed ugly call to propagate !!
// v1.010: now caching the best value in the domain (and not recomputing it)
/* The c++ function for: selectBranchingObject(b:AssignOrForbid) [] */
OID  choco_selectBranchingObject_AssignOrForbid_choco(AssignOrForbid *b)
{ GC_BIND;
  { OID Result = 0;
    { OID  xtest = GC_OID(choco.selectVar->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->varHeuristic))))));
      if (xtest != CNULL)
       { IntVar * x = OBJECT(IntVar,xtest);
        Result = _oid_(tuple::alloc(2,_oid_(x),choco.getBestVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->valHeuristic)))),((int) x))));
        } 
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: goDownBranch(b:AssignOrForbid,x:any,first:integer) [] */
void  choco_goDownBranch_AssignOrForbid_choco(AssignOrForbid *b,OID x,int first)
{ GC_BIND;
  { tuple * xp = OBJECT(tuple,x);
    IntVar * v = OBJECT(IntVar,(*(xp))[1]);
    int  best = (*(xp))[2];
    if (first == 1)
     choco_instantiate_IntVar2(v,best,0);
    else (*choco.removeVal)(_oid_(v),
        best,
        0);
      choco_propagate_Problem(GC_OBJECT(Problem,b->manager->problem));
    } 
  GC_UNBIND;} 


/* The c++ function for: traceDownBranch(b:AssignOrForbid,x:any,first:integer) [] */
void  choco_traceDownBranch_AssignOrForbid_choco(AssignOrForbid *b,OID x,int first)
{ { tuple * xp = OBJECT(tuple,x);
    IntVar * v = OBJECT(IntVar,(*(xp))[1]);
    int  best = (*(xp))[2];
    if (first == 1)
     { if (choco.STALK->value <= ClEnv->verbose)
       tformat_string(" !! branch ~S: ~S == ~S \n",choco.STALK->value,list::alloc(3,world_number(),
        _oid_(v),
        best));
      else ;} 
    else if (choco.STALK->value <= ClEnv->verbose)
     tformat_string(" !! branch ~S: ~S !== ~S \n",choco.STALK->value,list::alloc(3,world_number(),
      _oid_(v),
      best));
    else ;} 
  } 


/* The c++ function for: selectBranchingObject(b:AssignVar) [] */
OID  choco_selectBranchingObject_AssignVar_choco(AssignVar *b)
{ GC_BIND;
  { OID Result = 0;
    Result = choco.selectVar->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->varHeuristic)))));
    GC_UNBIND; return (Result);} 
  } 


// v1.013
/* The c++ function for: finishedBranching(b:AssignVar,x:any,i:integer) [] */
ClaireBoolean * choco_finishedBranching_AssignVar_choco(AssignVar *b,OID x,int i)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { IntVar * y = OBJECT(IntVar,x);
      Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isOver->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->valHeuristic)))),((int) y),((int) i))));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getFirstBranch(b:AssignVar,x:any) [] */
int  choco_getFirstBranch_AssignVar_choco(AssignVar *b,OID x)
{ GC_BIND;
  { int Result = 0;
    { IntVar * y = OBJECT(IntVar,x);
      Result = choco.getFirstVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->valHeuristic)))),((int) y));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNextBranch(b:AssignVar,x:any,i:integer) [] */
int  choco_getNextBranch_AssignVar_choco(AssignVar *b,OID x,int i)
{ GC_BIND;
  { int Result = 0;
    { IntVar * y = OBJECT(IntVar,x);
      Result = choco.getNextVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(b->valHeuristic)))),((int) y),((int) i));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: goDownBranch(b:AssignVar,x:any,i:integer) [] */
void  choco_goDownBranch_AssignVar_choco(AssignVar *b,OID x,int i)
{ GC_BIND;
  { IntVar * y = OBJECT(IntVar,x);
    choco_instantiate_IntVar2(y,i,0);
    choco_propagate_Problem(GC_OBJECT(Problem,b->manager->problem));
    } 
  GC_UNBIND;} 


/* The c++ function for: goUpBranch(b:AssignVar,x:any,i:integer) [] */
void  choco_goUpBranch_AssignVar_choco(AssignVar *b,OID x,int i)
{ GC_BIND;
  { IntVar * y = OBJECT(IntVar,x);
    (*choco.removeVal)(_oid_(y),
      i,
      0);
    choco_propagate_Problem(GC_OBJECT(Problem,b->manager->problem));
    } 
  GC_UNBIND;} 


/* The c++ function for: traceDownBranch(b:AssignVar,x:any,i:integer) [] */
void  choco_traceDownBranch_AssignVar_choco(AssignVar *b,OID x,int i)
{ { IntVar * y = OBJECT(IntVar,x);
    if (choco.STALK->value <= ClEnv->verbose)
     tformat_string(" !! branch ~S: ~S == ~S \n",choco.STALK->value,list::alloc(3,world_number(),
      _oid_(y),
      i));
    else ;} 
  } 


/* The c++ function for: traceUpBranch(b:AssignVar,x:any,i:integer) [] */
void  choco_traceUpBranch_AssignVar_choco(AssignVar *b,OID x,int i)
{ { IntVar * y = OBJECT(IntVar,x);
    if (choco.STALK->value <= ClEnv->verbose)
     tformat_string(" !! branch ~S: ~S !== ~S \n",choco.STALK->value,list::alloc(3,world_number(),
      _oid_(y),
      i));
    else ;} 
  } 


// note: this could be enriched into a LargeBranching, considering LargeDisjunctions
// v1.010: store the disjunction list into the branching object
/* The c++ function for: selectBranchingObject(b:SettleBinDisjunction) [] */
OID  choco_selectBranchingObject_SettleBinDisjunction_choco(SettleBinDisjunction *b)
{ { OID Result = 0;
    { OID  c_some = CNULL;
      { ITERATE(c);
        for (START(b->disjunctions); NEXT(c);)
        if ((!BCONTAIN(OBJECT(BinBoolConstraint,c)->statusBitVector,(4*(1-1)))) && 
            ((!BCONTAIN(OBJECT(BinBoolConstraint,c)->statusBitVector,((4*(1-1))+2))) && 
              ((!BCONTAIN(OBJECT(BinBoolConstraint,c)->statusBitVector,(4*(2-1)))) && 
                (!BCONTAIN(OBJECT(BinBoolConstraint,c)->statusBitVector,((4*(2-1))+2))))))
         { c_some= c;
          break;} 
        } 
      Result = c_some;
      } 
    return (Result);} 
  } 


// v1.05 typo <franck>
/* The c++ function for: goDownBranch(b:SettleBinDisjunction,d:any,first:integer) [] */
void  choco_goDownBranch_SettleBinDisjunction_choco(SettleBinDisjunction *b,OID d,int first)
{ GC_BIND;
  if (INHERIT(OWNER(d),choco._Disjunction))
   { choco_setBranch_Disjunction(OBJECT(Disjunction,d),first,CTRUE);
    choco_propagate_Problem(GC_OBJECT(Problem,b->manager->problem));
    } 
  else close_exception(((general_error *) (*Core._general_error)(_string_("Typing error: should be a Disjunction"),
      _oid_(Kernel.nil))));
    GC_UNBIND;} 


/* The c++ function for: traceDownBranch(b:SettleBinDisjunction,d:any,first:integer) [] */
void  choco_traceDownBranch_SettleBinDisjunction_choco(SettleBinDisjunction *b,OID d,int first)
{ GC_BIND;
  if (INHERIT(OWNER(d),choco._Disjunction))
   { if (first == 1)
     { if (choco.STALK->value <= ClEnv->verbose)
       tformat_string("!! branch ~S[1]: try ~S \n",choco.STALK->value,list::alloc(2,world_number(),GC_OID(_oid_(OBJECT(BinCompositeConstraint,d)->const1))));
      else ;} 
    else if (choco.STALK->value <= ClEnv->verbose)
     tformat_string("!! branch ~S[2]: try ~S \n",choco.STALK->value,list::alloc(2,world_number(),GC_OID(_oid_(OBJECT(BinCompositeConstraint,d)->const2))));
    else ;} 
  else close_exception(((general_error *) (*Core._general_error)(_string_("Typing error: should be a Disjunction"),
      _oid_(Kernel.nil))));
    GC_UNBIND;} 


// ********************************************************************
// *   Part 4: Using branching classes within globalSearchSolver's    *
// ********************************************************************
// The main method is run
//   it calls a few general submethods
// general definitions
/* The c++ function for: newTreeSearch(a:GlobalSearchSolver) [] */
void  choco_newTreeSearch_GlobalSearchSolver(GlobalSearchSolver *a)
{ ;(a->nbBk = 0);
  (a->nbNd = 0);
  (a->nbSol = 0);
  (a->baseWorld = world_number());
  time_set_void();
  } 


/* The c++ function for: endTreeSearch(algo:GlobalSearchSolver) [] */
void  choco_endTreeSearch_GlobalSearchSolver(GlobalSearchSolver *algo)
{ GC_BIND;
  { int  t = time_get_void();
    if (choco.SVIEW->value <= ClEnv->verbose)
     { list * g1175UU;
      { OID v_bag;
        GC_ANY(g1175UU= list::empty(Kernel.emptySet));
        { if (algo->problem->feasible != CTRUE)
           v_bag = _string_("no");
          else v_bag = _string_(string_I_integer (algo->nbSol));
            GC_OID(v_bag);} 
        ((list *) g1175UU)->addFast(v_bag);
        ((list *) g1175UU)->addFast(algo->nbNd);
        ((list *) g1175UU)->addFast(algo->nbBk);
        ((list *) g1175UU)->addFast(t);} 
      tformat_string("solve => ~A solution [~Snd., ~Sbk.] in ~S ms.\n",choco.SVIEW->value,g1175UU);
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: newNode(a:GlobalSearchSolver) [] */
void  choco_newNode_GlobalSearchSolver(GlobalSearchSolver *a)
{ GC_BIND;
  (a->nbNd = (a->nbNd+1));
  { OID gc_local;
    ITERATE(lim);
    bag *lim_support;
    lim_support = GC_OBJECT(list,a->limits);
    for (START(lim_support); NEXT(lim);)
    { GC_LOOP;
      if (not_any((*choco.mayExpandNewNode)(lim,
        _oid_(a))) == CTRUE)
       choco_raiseContradiction_PropagationEngine2(GC_OBJECT(PropagationEngine,a->problem->propagationEngine),OBJECT(Ephemeral,lim));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


/* The c++ function for: endNode(a:GlobalSearchSolver) [] */
void  choco_endNode_GlobalSearchSolver(GlobalSearchSolver *a)
{ (a->nbBk = (a->nbBk+1));
  } 


/* The c++ function for: recordSolution(a:GlobalSearchSolver) [] */
void  choco_recordSolution_GlobalSearchSolver(GlobalSearchSolver *a)
{ (a->problem->feasible = CTRUE);
  (a->nbSol = (a->nbSol+1));
  if (choco.SVIEW->value <= ClEnv->verbose)
   tformat_string("... solution [~Snd., ~Sbk., ~Sms.]\n",choco.SVIEW->value,list::alloc(3,a->nbNd,
    a->nbBk,
    time_read_void()));
  else ;if (a->maxSolutionStorage > 0)
   choco_storeCurrentSolution_Solver(a);
  } 


// v1.013
/* The c++ function for: postDynamicCut(a:GlobalSearchSolver) [] */
void  choco_postDynamicCut_GlobalSearchSolver(GlobalSearchSolver *a)
{ ;} 


/* The c++ function for: recordSolution(a:AbstractOptimize) [] */
void  choco_recordSolution_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  { IntVar * obj = GC_OBJECT(IntVar,a->objective);
    int  objval = (*choco.getObjectiveValue)(_oid_(a));
    (a->problem->feasible = CTRUE);
    (a->nbSol = (a->nbSol+1));
    if (choco.SVIEW->value <= ClEnv->verbose)
     tformat_string("... solution with ~A:~S [~Snd., ~Sbk., ~Sms.]\n",choco.SVIEW->value,list::alloc(5,GC_OID(_string_(obj->name)),
      objval,
      a->nbNd,
      a->nbBk,
      time_read_void()));
    else ;choco_setBound_AbstractOptimize(a);
    choco_setTargetBound_AbstractOptimize(a);
    if (a->maxSolutionStorage > 0)
     choco_storeCurrentSolution_Solver(a);
    } 
  GC_UNBIND;} 


// v1.013
// v1.013 resetting the optimization bounds
/* The c++ function for: setBound(a:AbstractOptimize) [] */
void  choco_setBound_AbstractOptimize(AbstractOptimize *a)
{ { int  objval = (*choco.getObjectiveValue)(_oid_(a));
    if (a->doMaximize == CTRUE)
     (a->lowerBound = ((objval <= a->lowerBound) ?
      a->lowerBound :
      objval ));
    else (a->upperBound = ((objval <= a->upperBound) ?
        objval :
        a->upperBound ));
      } 
  } 


// v1.08: resetting the values of the target bounds (bounds for the remaining search)
/* The c++ function for: setTargetBound(a:AbstractOptimize) [] */
void  choco_setTargetBound_AbstractOptimize(AbstractOptimize *a)
{ if (a->doMaximize == CTRUE)
   choco_setTargetLowerBound_AbstractOptimize(a);
  else choco_setTargetUpperBound_AbstractOptimize(a);
    } 


/* The c++ function for: setTargetLowerBound(a:AbstractOptimize) [] */
void  choco_setTargetLowerBound_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  { int  newBound = (a->lowerBound+1);
    if (a->problem->feasible != CTRUE)
     { if (choco.STALK->value <= ClEnv->verbose)
       tformat_string("search first sol ...",choco.STALK->value,list::empty());
      else ;} 
    else { (a->targetLowerBound = newBound);
        if (choco.STALK->value <= ClEnv->verbose)
         tformat_string("search target: ~A >= ~S ... ",choco.STALK->value,list::alloc(2,GC_OID(_string_(a->objective->name)),newBound));
        else ;} 
      } 
  GC_UNBIND;} 


/* The c++ function for: setTargetUpperBound(a:AbstractOptimize) [] */
void  choco_setTargetUpperBound_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  { int  newBound = (a->upperBound-1);
    if (a->problem->feasible != CTRUE)
     { if (choco.STALK->value <= ClEnv->verbose)
       tformat_string("search first sol ...",choco.STALK->value,list::empty());
      else ;} 
    else { (a->targetUpperBound = newBound);
        if (choco.STALK->value <= ClEnv->verbose)
         tformat_string("search target: ~A <= ~S ... ",choco.STALK->value,list::alloc(2,GC_OID(_string_(a->objective->name)),newBound));
        else ;} 
      } 
  GC_UNBIND;} 


// v1.013: propagating the optimization cuts from the new target bounds
/* The c++ function for: postTargetBound(a:AbstractOptimize) [] */
void  choco_postTargetBound_AbstractOptimize(AbstractOptimize *a)
{ if (a->doMaximize == CTRUE)
   choco_postTargetLowerBound_AbstractOptimize(a);
  else choco_postTargetUpperBound_AbstractOptimize(a);
    } 


/* The c++ function for: postTargetLowerBound(a:AbstractOptimize) [] */
void  choco_postTargetLowerBound_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(a->objective)),
    a->targetLowerBound,
    0);
  GC_UNBIND;} 


/* The c++ function for: postTargetUpperBound(a:AbstractOptimize) [] */
void  choco_postTargetUpperBound_AbstractOptimize(AbstractOptimize *a)
{ GC_BIND;
  (*choco.updateSup)(GC_OID(_oid_(a->objective)),
    a->targetUpperBound,
    0);
  GC_UNBIND;} 


/* The c++ function for: newTreeSearch(a:OptimizeWithRestarts) [] */
void  choco_newTreeSearch_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ GC_BIND;
  (a->nbBk = 0);
  (a->nbNd = 0);
  (a->nbIter = (a->nbIter+1));
  (a->baseWorld = world_number());
  (a->baseNbSol = a->nbSol);
  time_set_void();
  choco_postTargetBound_AbstractOptimize(a);
  choco_propagate_Problem(GC_OBJECT(Problem,a->problem));
  GC_UNBIND;} 


// claire3 port world= -> backtrack
/* The c++ function for: endTreeSearch(a:OptimizeWithRestarts) [] */
void  choco_endTreeSearch_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ (a->nbBkTot = (a->nbBkTot+a->nbBk));
  (a->nbNdTot = (a->nbNdTot+a->nbNd));
  (a->nbBk = 0);
  (a->nbNd = 0);
  backtrack_integer(a->baseWorld);
  time_get_void();
  } 


// v1.012: no need to print solution information twice
/* The c++ function for: postDynamicCut(a:OptimizeWithRestarts) [] */
void  choco_postDynamicCut_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ ;} 


// generic method
/* The c++ function for: run(algo:GlobalSearchSolver) [] */
void  choco_run_GlobalSearchSolver(GlobalSearchSolver *algo)
{ close_exception(((general_error *) (*Core._general_error)(_string_("run is not implemented on ~S"),
    _oid_(list::alloc(1,_oid_(algo))))));
  } 


// searching for one solution
// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
/* The c++ function for: run(algo:Solve) [] */
void  choco_run_Solve(Solve *algo)
{ GC_BIND;
  choco_newTreeSearch_GlobalSearchSolver(algo);
  { Problem * pb = GC_OBJECT(Problem,algo->problem);
    ClaireBoolean * feasibleRootState = CTRUE;
    { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { choco_propagate_Problem(pb);
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();feasibleRootState= CFALSE;
        } 
      else PREVIOUS_HANDLER;} 
    if (feasibleRootState == CTRUE)
     { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { { { OID  g1176test = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
            if (g1176test != CNULL)
             { EventCollection * g1176 = OBJECT(EventCollection,g1176test);
              close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
                _oid_(list::alloc(2,world_number(),_oid_(g1176))))));
              } 
            else { world_push();
                ;} 
              } 
          choco_explore_AbstractBranching(OBJECT(AbstractBranching,(*(algo->branchingComponents))[1]),1);
          } 
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();{ world_pop();
          ;;} 
        } 
      else PREVIOUS_HANDLER;} 
    { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { if ((algo->maxSolutionStorage > 0) && 
            ((algo->stopAtFirstSol != CTRUE) && 
              (choco_existsSolution_Solver(algo) == CTRUE)))
         choco_restoreBestSolution_Solver(algo);
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();close_exception(((general_error *) (*Core._general_error)(_string_("contradiction while restoring the best solution found: BUG !!"),
          _oid_(Kernel.nil))));
        } 
      else PREVIOUS_HANDLER;} 
    } 
  choco_endTreeSearch_GlobalSearchSolver(algo);
  GC_UNBIND;} 


// v1.02: returns void
// v0.25 adding a world level for avoiding conflicts between the proof of optimality
// and the restoring of best sol
// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
/* The c++ function for: run(algo:BranchAndBound) [] */
void  choco_run_BranchAndBound(BranchAndBound *algo)
{ GC_BIND;
  choco_newTreeSearch_BranchAndBound(algo);
  { Problem * pb = GC_OBJECT(Problem,algo->problem);
    ClaireBoolean * feasibleRootState = CTRUE;
    { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { choco_propagate_Problem(pb);
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();feasibleRootState= CFALSE;
        } 
      else PREVIOUS_HANDLER;} 
    if (feasibleRootState == CTRUE)
     { { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { { OID  g1177test = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
              if (g1177test != CNULL)
               { EventCollection * g1177 = OBJECT(EventCollection,g1177test);
                close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
                  _oid_(list::alloc(2,world_number(),_oid_(g1177))))));
                } 
              else { world_push();
                  ;} 
                } 
            choco_explore_AbstractBranching(OBJECT(AbstractBranching,(*(algo->branchingComponents))[1]),1);
            world_pop();
            ;;} 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();{ world_pop();
            ;;} 
          } 
        else PREVIOUS_HANDLER;} 
      { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { if ((algo->maxSolutionStorage > 0) && 
              (choco_existsSolution_Solver(algo) == CTRUE))
           choco_restoreBestSolution_Solver(algo);
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
        { c_handle.catchIt();close_exception(((general_error *) (*Core._general_error)(_string_("contradiction while restoring the best solution found: BUG !!"),
            _oid_(Kernel.nil))));
          } 
        else PREVIOUS_HANDLER;} 
      } 
    choco_endTreeSearch_BranchAndBound(algo);
    } 
  GC_UNBIND;} 


// v0.9907 <thb>  // v1.02 returns void
// v1.013: we use  targetBound data structures for the optimization cuts
/* The c++ function for: postDynamicCut(a:BranchAndBound) [] */
void  choco_postDynamicCut_BranchAndBound(BranchAndBound *a)
{ GC_BIND;
  choco_postTargetBound_AbstractOptimize(a);
  choco_propagate_Problem(GC_OBJECT(Problem,a->problem));
  GC_UNBIND;} 


/* The c++ function for: newTreeSearch(a:BranchAndBound) [] */
void  choco_newTreeSearch_BranchAndBound(BranchAndBound *a)
{ choco_initBounds_AbstractOptimize(a);
  time_set_void();
  } 


// v0.9907, v1.013
// v1.02: removed reference to baseWorld
/* The c++ function for: endTreeSearch(a:BranchAndBound) [] */
void  choco_endTreeSearch_BranchAndBound(BranchAndBound *a)
{ { int  t = time_get_void();
    if (a->problem->feasible == CTRUE)
     { if (choco.SVIEW->value <= ClEnv->verbose)
       tformat_string("solve => ~S sol, best:~S [~Snd, ~Sbk], ~S ms.\n",choco.SVIEW->value,list::alloc(5,a->nbSol,
        ((a->doMaximize == CTRUE) ?
          a->lowerBound :
          a->upperBound ),
        a->nbNd,
        a->nbBk,
        t));
      else ;} 
    else if (choco.SVIEW->value <= ClEnv->verbose)
     tformat_string("solve => no sol [~Snd, ~Sbk], ~S ms.\n",choco.SVIEW->value,list::alloc(3,a->nbNd,
      a->nbBk,
      t));
    else ;} 
  } 


/* The c++ function for: newLoop(a:OptimizeWithRestarts) [] */
void  choco_newLoop_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ (a->nbSol = 0);
  (a->nbBkTot = 0);
  (a->nbNdTot = 0);
  (a->nbIter = 0);
  choco_initBounds_AbstractOptimize(a);
  time_set_void();
  } 


// v1.013
/* The c++ function for: endLoop(a:OptimizeWithRestarts) [] */
void  choco_endLoop_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ GC_BIND;
  { int  t = time_get_void();
    if (choco.SVIEW->value <= ClEnv->verbose)
     { list * g1178UU;
      { OID v_bag;
        GC_ANY(g1178UU= list::empty(Kernel.emptySet));
        if (a->doMaximize == CTRUE)
         v_bag = _string_("max");
        else v_bag = _string_("min");
          ((list *) g1178UU)->addFast(v_bag);
        ((list *) g1178UU)->addFast(GC_OID(_string_(a->objective->name)));
        ((list *) g1178UU)->addFast(choco_getBestObjectiveValue_AbstractOptimize(a));
        ((list *) g1178UU)->addFast(a->nbIter);
        ((list *) g1178UU)->addFast(a->nbNdTot);
        ((list *) g1178UU)->addFast(a->nbBkTot);
        ((list *) g1178UU)->addFast(t);} 
      tformat_string("Optimisation over => ~A(~A) = ~S found in ~S iterations, ~S nodes, ~S backtracks and ~S ms.\n",choco.SVIEW->value,g1178UU);
      } 
    else ;} 
  GC_UNBIND;} 


// v1.08
/* The c++ function for: recordNoSolution(a:OptimizeWithRestarts) [] */
void  choco_recordNoSolution_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ GC_BIND;
  { IntVar * obj = GC_OBJECT(IntVar,a->objective);
    int  objtgt = choco_getObjectiveTarget_AbstractOptimize(a);
    if (choco.SVIEW->value <= ClEnv->verbose)
     tformat_string("... no solution with ~A:~S [~Snd., ~Sbk.]\n",choco.SVIEW->value,list::alloc(4,GC_OID(_string_(obj->name)),
      objtgt,
      a->nbNd,
      a->nbBk));
    else ;if (a->doMaximize == CTRUE)
     (a->upperBound = (((objtgt-1) <= a->upperBound) ?
      (objtgt-1) :
      a->upperBound ));
    else (a->lowerBound = (((objtgt+1) <= a->lowerBound) ?
        a->lowerBound :
        (objtgt+1) ));
      } 
  GC_UNBIND;} 


// v1.08: loop until the lower bound equals the upper bound
/* The c++ function for: oneMoreLoop(a:OptimizeWithRestarts) [] */
ClaireBoolean * choco_oneMoreLoop_OptimizeWithRestarts(OptimizeWithRestarts *a)
{ return (_inf_integer(a->lowerBound,a->upperBound));} 


// v1.02: note: the initial propagation must be done before pushing any world level. It is therefore kept before restoring a solution
// v1.08: <ega> requires a more careful management of lower/upperBounds
/* The c++ function for: run(algo:OptimizeWithRestarts) [] */
void  choco_run_OptimizeWithRestarts(OptimizeWithRestarts *algo)
{ GC_BIND;
  { int  w = (world_number()+1);
    Problem * pb = GC_OBJECT(Problem,algo->problem);
    ClaireBoolean * finished = CFALSE;
    choco_newLoop_OptimizeWithRestarts(algo);
    { ClaireHandler c_handle = ClaireHandler();
      if ERROR_IN 
      { choco_propagate_Problem(pb);
        ClEnv->cHandle--;} 
      else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
      { c_handle.catchIt();{ finished= CTRUE;
          choco_recordNoSolution_OptimizeWithRestarts(algo);
          } 
        } 
      else PREVIOUS_HANDLER;} 
    if (finished != CTRUE)
     { { OID  g1179test = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
        if (g1179test != CNULL)
         { EventCollection * g1179 = OBJECT(EventCollection,g1179test);
          close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
            _oid_(list::alloc(2,world_number(),_oid_(g1179))))));
          } 
        else { world_push();
            ;} 
          } 
      { while ((choco_oneMoreLoop_OptimizeWithRestarts(algo) == CTRUE))
        { { ClaireBoolean * foundSolution = CFALSE;
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { choco_newTreeSearch_OptimizeWithRestarts(algo);
                  if (choco_explore_AbstractBranching(OBJECT(AbstractBranching,(*(algo->branchingComponents))[1]),1) == CTRUE)
                   foundSolution= CTRUE;
                  } 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._contradiction)) == CTRUE)
              { c_handle.catchIt();;} 
              else PREVIOUS_HANDLER;} 
            choco_endTreeSearch_OptimizeWithRestarts(algo);
            if (foundSolution != CTRUE)
             choco_recordNoSolution_OptimizeWithRestarts(algo);
            } 
          } 
        } 
      world_pop();
      ;;} 
    choco_endLoop_OptimizeWithRestarts(algo);
    if ((algo->maxSolutionStorage > 0) && 
        (choco_existsSolution_Solver(algo) == CTRUE))
     choco_restoreBestSolution_Solver(algo);
    } 
  GC_UNBIND;} 


// v1.02 returns void
// ********************************************************************
// *   Part 5: utils and solution management                          *
// ********************************************************************
// V0.9907
/* The c++ function for: makeConflictCount(_CL_obj:void) [] */
ConflictCount * choco_makeConflictCount_void()
{ GC_BIND;
  { ConflictCount *Result ;
    { ConflictCount * _CL_obj = ((ConflictCount *) GC_OBJECT(ConflictCount,new_object_class(choco._ConflictCount)));
      (_CL_obj->nbViolatedConstraints = 0);
      STOREI(_CL_obj->lastAssignedVar,0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: attachInvariantEngine(pb:Problem,ie:InvariantEngine) [] */
void  choco_attachInvariantEngine_Problem(Problem *pb,InvariantEngine *ie)
{ (pb->invariantEngine = ie);
  (ie->problem = pb);
  } 


// returns +1 when the status of c changes from true to false when v goes from a to b
//         -1                                   false to true
//         0  when c does not change (remains satisfied or violated)
/* The c++ function for: unitaryDeltaConflict(v:IntVar,a:integer,b:integer,c:AbstractConstraint) [] */
int  choco_unitaryDeltaConflict_IntVar(IntVar *v,int a,int b,AbstractConstraint *c)
{ ;;{ int Result = 0;
    { ClaireBoolean * OKbefore = not_any(_oid_(c->violated));
      ClaireBoolean * OKafter = OKbefore;
      STOREI(v->value,b);
      OKafter= OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) c))));
      STOREI(v->value,a);
      Result = ((OKafter == OKbefore) ?
        0 :
        ((OKbefore == CTRUE) ?
          1 :
          -1 ) );
      } 
    return (Result);} 
  } 


// the conflicting constraint was repaired
// computes the change in the number of conflicting constraints
// when v is flipped from value a to b
/* The c++ function for: deltaNbConflicts(v:IntVar,a:integer,b:integer) [] */
int  choco_deltaNbConflicts_IntVar(IntVar *v,int a,int b)
{ GC_BIND;
  { int Result = 0;
    { int  delta = 0;
      { OID gc_local;
        ITERATE(c);
        bag *c_support;
        c_support = GC_OBJECT(list,v->constraints);
        for (START(c_support); NEXT(c);)
        { GC_LOOP;
          delta= (delta+choco_unitaryDeltaConflict_IntVar(v,a,b,OBJECT(AbstractConstraint,c)));
          GC_UNLOOP;} 
        } 
      Result = delta;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getMinConflictValue(v:IntVar) [] */
int  choco_getMinConflictValue_IntVar(IntVar *v)
{ GC_BIND;
  { int Result = 0;
    { int  a = v->value;
      int  minDelta = 99999999;
      int  bestvalue = a;
      { IntVar * g1180 = v;
        AbstractIntDomain * g1181 = GC_OBJECT(AbstractIntDomain,g1180->bucket);
        if (g1181 == (NULL))
         { int  b = g1180->inf->latestValue;
          { OID gc_local;
            while ((b <= g1180->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (b != a)
               { int  delta = choco_deltaNbConflicts_IntVar(v,a,b);
                if (delta < minDelta)
                 { minDelta= delta;
                  bestvalue= b;
                  } 
                } 
              b= ((g1180->inf->latestValue <= (b+1)) ?
                (b+1) :
                g1180->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g1181->isa,choco._LinkedListIntDomain))
         { int  b = g1180->inf->latestValue;
          { OID gc_local;
            while ((b <= g1180->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (b != a)
               { int  delta = choco_deltaNbConflicts_IntVar(v,a,b);
                if (delta < minDelta)
                 { minDelta= delta;
                  bestvalue= b;
                  } 
                } 
              b= choco.getNextValue->fcall(((int) g1181),((int) b));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(b);
            bag *b_support;
            b_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1181)))));
            for (START(b_support); NEXT(b);)
            { GC_LOOP;
              if (b != a)
               { int  delta = choco_deltaNbConflicts_IntVar(v,a,b);
                if (delta < minDelta)
                 { minDelta= delta;
                  bestvalue= b;
                  } 
                } 
              GC_UNLOOP;} 
            } 
          } 
      Result = bestvalue;
      } 
    GC_UNBIND; return (Result);} 
  } 


// saving and retrieving assignments
/* The c++ function for: computeConflictAccount(ie:ConflictCount) [] */
void  choco_computeConflictAccount_ConflictCount(ConflictCount *ie)
{ GC_BIND;
  { Problem * p = GC_OBJECT(Problem,ie->problem);
    { OID gc_local;
      ITERATE(v);
      bag *v_support;
      v_support = GC_OBJECT(list,p->vars);
      for (START(v_support); NEXT(v);)
      { GC_LOOP;
        (OBJECT(AbstractVar,v)->nbViolatedConstraints = 0);
        GC_UNLOOP;} 
      } 
    (ie->nbViolatedConstraints = 0);
    { OID gc_local;
      ITERATE(c);
      bag *c_support;
      c_support = GC_OBJECT(list,p->constraints);
      for (START(c_support); NEXT(c);)
      { GC_LOOP;
        if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE)
         { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE)
           (OBJECT(AbstractConstraint,c)->violated = CFALSE);
          else { (OBJECT(AbstractConstraint,c)->violated = CTRUE);
              (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
              if (INHERIT(OBJECT(ClaireObject,c)->isa,choco._UnIntConstraint))
               (OBJECT(UnIntConstraint,c)->v1->nbViolatedConstraints = (OBJECT(UnIntConstraint,c)->v1->nbViolatedConstraints+1));
              else if (INHERIT(OBJECT(ClaireObject,c)->isa,choco._BinIntConstraint))
               { (OBJECT(BinIntConstraint,c)->v1->nbViolatedConstraints = (OBJECT(BinIntConstraint,c)->v1->nbViolatedConstraints+1));
                (OBJECT(BinIntConstraint,c)->v2->nbViolatedConstraints = (OBJECT(BinIntConstraint,c)->v2->nbViolatedConstraints+1));
                } 
              else if (INHERIT(OBJECT(ClaireObject,c)->isa,choco._LargeIntConstraint))
               { OID gc_local;
                ITERATE(v);
                bag *v_support;
                v_support = GC_OBJECT(list,OBJECT(LargeIntConstraint,c)->vars);
                for (START(v_support); NEXT(v);)
                { GC_LOOP;
                  (OBJECT(AbstractVar,v)->nbViolatedConstraints = (OBJECT(AbstractVar,v)->nbViolatedConstraints+1));
                  GC_UNLOOP;} 
                } 
              else close_exception(((general_error *) (*Core._general_error)(_string_("Cannot build assignment with such constraint ~S"),
                  _oid_(list::alloc(1,c)))));
                } 
            } 
        GC_UNLOOP;} 
      } 
    { ConflictCount * g1182; 
      list * g1183;
      g1182 = ie;
      { list * v_out = list::empty(choco._IntVar);
        { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,p->vars);
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if ((OBJECT(IntVar,v)->inf->latestValue < OBJECT(IntVar,v)->sup->latestValue) && 
                (OBJECT(AbstractVar,v)->nbViolatedConstraints > 0))
             v_out->addFast(v);
            GC_UNLOOP;} 
          } 
        g1183 = GC_OBJECT(list,v_out);
        } 
      (g1182->conflictingVars = g1183);} 
    } 
  GC_UNBIND;} 


// this function is called when the local search mode is started after a global search
// (feasible mode)
/* The c++ function for: resetInvariants(ie:InvariantEngine) [] */
void  choco_resetInvariants_InvariantEngine(InvariantEngine *ie)
{ close_exception(((general_error *) (*Core._general_error)(_string_("resetInvariants should be defined for ~S"),
    _oid_(list::alloc(1,_oid_(ie))))));
  } 


/* The c++ function for: resetInvariants(ie:ConflictCount) [] */
void  choco_resetInvariants_ConflictCount(ConflictCount *ie)
{ { Problem * p = ie->problem;
    { ITERATE(c);
      bag *c_support;
      c_support = p->constraints;
      for (START(c_support); NEXT(c);)
      (OBJECT(AbstractConstraint,c)->violated = CFALSE);
      } 
    { ITERATE(v);
      bag *v_support;
      v_support = p->vars;
      for (START(v_support); NEXT(v);)
      (OBJECT(AbstractVar,v)->nbViolatedConstraints = 0);
      } 
    (ie->nbViolatedConstraints = 0);
    } 
  } 


// interface
/* The c++ function for: getLocalSearchObjectiveValue(ie:InvariantEngine) [] */
int  choco_getLocalSearchObjectiveValue_InvariantEngine(InvariantEngine *ie)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getObjective should be defined for ~S"),
    _oid_(list::alloc(1,_oid_(ie))))));
  return (0);} 


/* The c++ function for: getLocalSearchObjectiveValue(ie:ConflictCount) [] */
int  choco_getLocalSearchObjectiveValue_ConflictCount(ConflictCount *ie)
{ return (ie->nbViolatedConstraints);} 


/* The c++ function for: restoreBestAssignment(algo:LocalSearchSolver) [] */
void  choco_restoreBestAssignment_LocalSearchSolver(LocalSearchSolver *algo)
{ GC_BIND;
  ;{ Problem * p = GC_OBJECT(Problem,algo->problem);
    { OID gc_local;
      ITERATE(v);
      bag *v_support;
      v_support = GC_OBJECT(list,p->vars);
      for (START(v_support); NEXT(v);)
      { GC_LOOP;
        if (OBJECT(IntVar,v)->inf->latestValue < OBJECT(IntVar,v)->sup->latestValue)
         STOREI(OBJECT(IntVar,v)->value,OBJECT(IntVar,v)->savedAssignment);
        GC_UNLOOP;} 
      } 
    choco_computeConflictAccount_ConflictCount(GC_OBJECT(ConflictCount,((ConflictCount *) p->invariantEngine)));
    } 
  GC_UNBIND;} 


/* The c++ function for: saveAssignment(algo:LocalSearchSolver) [] */
void  choco_saveAssignment_LocalSearchSolver(LocalSearchSolver *algo)
{ { ITERATE(v);
    bag *v_support;
    v_support = algo->problem->vars;
    for (START(v_support); NEXT(v);)
    (OBJECT(IntVar,v)->savedAssignment = OBJECT(IntVar,v)->value);
    } 
  } 


// v0.9907
/* The c++ function for: setFeasibleMode(pb:Problem,b:boolean) [] */
void  choco_setFeasibleMode_Problem(Problem *pb,ClaireBoolean *b)
{ if (b != pb->feasibleMode) 
  { { if (b == CFALSE)
       { ConflictCount * ie = GC_OBJECT(ConflictCount,((ConflictCount *) pb->invariantEngine));
        list * l = GC_OBJECT(list,ie->assignedThenUnassignedVars);
        shrink_list(l,0);
        { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,pb->vars);
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if (OBJECT(IntVar,v)->inf->latestValue == OBJECT(IntVar,v)->sup->latestValue)
             l->addFast(v);
            GC_UNLOOP;} 
          } 
        { int  n = l->length;
          STOREI(ie->lastAssignedVar,n);
          } 
        { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,pb->vars);
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if (OBJECT(IntVar,v)->inf->latestValue != OBJECT(IntVar,v)->sup->latestValue)
             l->addFast(v);
            GC_UNLOOP;} 
          } 
        choco_resetInvariants_ConflictCount(ie);
        } 
      STOREO(pb->feasibleMode,CFALSE);
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// ********************************************************************
// *   Part 6: Feeding the engine with a library of neighborhoods     *
// ********************************************************************
/* The c++ function for: selectMoveVar(a:AssignmentHeuristic) [] */
OID  choco_selectMoveVar_AssignmentHeuristic(AssignmentHeuristic *a)
{ { OID Result = 0;
    { InvariantEngine * ie = a->manager->problem->invariantEngine;
      list * l = CLREAD(ConflictCount,ie,assignedThenUnassignedVars);
      int  n = l->length;
      int  j = CLREAD(ConflictCount,ie,lastAssignedVar);
      if (j == n)
       Result = CNULL;
      else Result = (*(l))[((j+1)+random_integer((n-j)))];
        } 
    return (Result);} 
  } 


/* The c++ function for: selectValue(a:AssignmentHeuristic,v:IntVar) [] */
int  choco_selectValue_AssignmentHeuristic(AssignmentHeuristic *a,IntVar *v)
{ if (v->bucket == (NULL)) 
  { { int Result = 0;
      Result = claire_random_integer2(v->inf->latestValue,v->sup->latestValue);
      return (Result);} 
     } 
  else{ GC_BIND;
    { int Result = 0;
      Result = claire_random_LinkedListIntDomain(GC_OBJECT(LinkedListIntDomain,((LinkedListIntDomain *) v->bucket)));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: assignOneVar(a:AssignmentHeuristic) [] */
ClaireBoolean * choco_assignOneVar_AssignmentHeuristic(AssignmentHeuristic *a)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  vtest = GC_OID(choco_selectMoveVar_AssignmentHeuristic(a));
      if (vtest != CNULL)
       { IntVar * v = OBJECT(IntVar,vtest);
        int  val = choco_selectValue_AssignmentHeuristic(a,v);
        choco_assignVal_IntVar(v,val);
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: build(a:AssignmentHeuristic) [] */
void  choco_build_AssignmentHeuristic(AssignmentHeuristic *a)
{ GC_BIND;
  choco_eraseAssignment_ConflictCount(GC_OBJECT(ConflictCount,((ConflictCount *) a->manager->problem->invariantEngine)));
  { while ((choco_assignOneVar_AssignmentHeuristic(a) == CTRUE))
    { ;} 
    } 
  ;;GC_UNBIND;} 


// v1.0 erasing the previous assignment before constructing a new one.
/* The c++ function for: eraseAssignment(ie:ConflictCount) [] */
void  choco_eraseAssignment_ConflictCount(ConflictCount *ie)
{ GC_BIND;
  { Problem * pb = GC_OBJECT(Problem,ie->problem);
    { OID gc_local;
      ITERATE(v);
      bag *v_support;
      v_support = GC_OBJECT(list,pb->vars);
      for (START(v_support); NEXT(v);)
      { GC_LOOP;
        if (OBJECT(IntVar,v)->inf->latestValue != OBJECT(IntVar,v)->sup->latestValue)
         { STOREI(OBJECT(IntVar,v)->value,-100000000);
          (OBJECT(AbstractVar,v)->nbViolatedConstraints = 0);
          } 
        GC_UNLOOP;} 
      } 
    { ConflictCount * g1184; 
      list * g1185;
      g1184 = ie;
      { list * v_out = list::empty(choco._IntVar);
        { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,pb->vars);
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if (OBJECT(AbstractVar,v)->nbViolatedConstraints > 0)
             v_out->addFast(v);
            GC_UNLOOP;} 
          } 
        g1185 = GC_OBJECT(list,v_out);
        } 
      (g1184->conflictingVars = g1185);} 
    { int  nbconflicts = 0;
      { OID gc_local;
        ITERATE(c);
        bag *c_support;
        c_support = GC_OBJECT(list,pb->constraints);
        for (START(c_support); NEXT(c);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE)
           { if (not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,c))))) == CTRUE)
             ++nbconflicts;
            } 
          else (OBJECT(AbstractConstraint,c)->violated = CFALSE);
            GC_UNLOOP;} 
        } 
      (ie->nbViolatedConstraints = nbconflicts);
      } 
    { list * l = GC_OBJECT(list,ie->assignedThenUnassignedVars);
      shrink_list(l,0);
      { OID gc_local;
        ITERATE(v);
        bag *v_support;
        v_support = GC_OBJECT(list,pb->vars);
        for (START(v_support); NEXT(v);)
        { GC_LOOP;
          if (OBJECT(IntVar,v)->inf->latestValue == OBJECT(IntVar,v)->sup->latestValue)
           l->addFast(v);
          GC_UNLOOP;} 
        } 
      { int  n = l->length;
        STOREI(ie->lastAssignedVar,n);
        } 
      { OID gc_local;
        ITERATE(v);
        bag *v_support;
        v_support = GC_OBJECT(list,pb->vars);
        for (START(v_support); NEXT(v);)
        { GC_LOOP;
          if (OBJECT(IntVar,v)->inf->latestValue != OBJECT(IntVar,v)->sup->latestValue)
           l->addFast(v);
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


// checks that the invariant engine is in a proper state
/* The c++ function for: checkCleanState(ie:ConflictCount) [] */
OID  choco_checkCleanState_ConflictCount(ConflictCount *ie)
{ return (Core.nil->value);} 


// TODO: there should be another move scheme than FlipNeighborhood
// select a constraint rather than a variable and repair it
//       incremental structure => keep the sublist of violated constraints
/* The c++ function for: selectMoveVar(m:FlipNeighborhood) [] */
OID  choco_selectMoveVar_FlipNeighborhood(FlipNeighborhood *m)
{ GC_BIND;
  { OID Result = 0;
    { list * l = GC_OBJECT(list,CLREAD(ConflictCount,m->manager->problem->invariantEngine,conflictingVars));
      if (l->length != 0)
       Result = claire_random_list(l);
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: newValue(m:FlipNeighborhood,v:IntVar) [] */
int  choco_newValue_FlipNeighborhood(FlipNeighborhood *m,IntVar *v)
{ return (choco_getMinConflictValue_IntVar(v));} 


/* The c++ function for: move(m:FlipNeighborhood) [] */
ClaireBoolean * choco_move_FlipNeighborhood(FlipNeighborhood *m)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  vtest = GC_OID(choco_selectMoveVar_FlipNeighborhood(m));
      if (vtest != CNULL)
       { IntVar * v = OBJECT(IntVar,vtest);
        int  b = choco_newValue_FlipNeighborhood(m,v);
        choco_changeVal_IntVar(v,b);
        Result = CTRUE;
        } 
      else { Result = CFALSE;
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: finishedLoop(algo:LocalSearchSolver) [] */
ClaireBoolean * choco_finishedLoop_LocalSearchSolver(LocalSearchSolver *algo)
{ { ClaireBoolean *Result ;
    { InvariantEngine * ie = algo->problem->invariantEngine;
      if (CLREAD(ConflictCount,ie,nbViolatedConstraints) == 0)
       { Result = CTRUE;
        } 
      else { Result = ((algo->maxNbLocalMoves <= CLREAD(MultipleDescent,algo,nbLocalMoves)) ?
            CTRUE :
            CFALSE );
          } 
        } 
    return (Result);} 
  } 


/* The c++ function for: initLoop(algo:LocalSearchSolver) [] */
void  choco_initLoop_LocalSearchSolver(LocalSearchSolver *algo)
{ (CLREAD(MultipleDescent,algo,nbLocalSearch) = (CLREAD(MultipleDescent,algo,nbLocalSearch)+1));
  (CLREAD(MultipleDescent,algo,nbLocalMoves) = 0);
  } 


/* The c++ function for: endLoop(algo:LocalSearchSolver) [] */
void  choco_endLoop_LocalSearchSolver(LocalSearchSolver *algo)
{ GC_BIND;
  { InvariantEngine * ie = GC_OBJECT(InvariantEngine,algo->problem->invariantEngine);
    if (CLREAD(ConflictCount,ie,nbViolatedConstraints) < CLREAD(ConflictCount,ie,minNbViolatedConstraints))
     { if (choco.LVIEW->value <= ClEnv->verbose)
       tformat_string("found a solution with ~S violated constraints \n",choco.LVIEW->value,list::alloc(1,CLREAD(ConflictCount,ie,nbViolatedConstraints)));
      else ;(CLREAD(ConflictCount,ie,minNbViolatedConstraints) = CLREAD(ConflictCount,ie,nbViolatedConstraints));
      choco_saveAssignment_LocalSearchSolver(algo);
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: copyParameters(oldSolver:LocalSearchSolver,newSolver:LocalSearchSolver) [] */
void  choco_copyParameters_LocalSearchSolver(LocalSearchSolver *oldSolver,LocalSearchSolver *newSolver)
{ { int  nbs = oldSolver->maxNbLocalSearch;
    if (nbs == (CNULL))
     ;else (newSolver->maxNbLocalSearch = nbs);
      } 
  { int  nbm = oldSolver->maxNbLocalMoves;
    if (nbm == (CNULL))
     ;else (newSolver->maxNbLocalMoves = nbm);
      } 
  } 


/* The c++ function for: attachLocalSearchSolver(pb:Problem,newSolver:LocalSearchSolver) [] */
void  choco_attachLocalSearchSolver_Problem(Problem *pb,LocalSearchSolver *newSolver)
{ GC_BIND;
  { LocalSearchSolver * oldSolver = GC_OBJECT(LocalSearchSolver,pb->localSearchSolver);
    if (oldSolver == (NULL))
     ;else choco_copyParameters_LocalSearchSolver(oldSolver,newSolver);
      } 
  (pb->localSearchSolver = newSolver);
  (newSolver->problem = pb);
  GC_UNBIND;} 


// ********************************************************************
// *   Part 7: Using neighborhood classes within LocalSearchSolver's  *
// ********************************************************************
/* The c++ function for: runLocalSearch(algo:LocalSearchSolver) [] */
void  choco_runLocalSearch_LocalSearchSolver(LocalSearchSolver *algo)
{ GC_BIND;
  choco_setFeasibleMode_Problem(GC_OBJECT(Problem,algo->problem),CFALSE);
  choco_initIterations_MultipleDescent(((MultipleDescent *) algo));
  { OID gc_local;
    while ((choco_finishedIterations_LocalSearchSolver(algo) != CTRUE))
    { GC_LOOP;
      choco_build_AssignmentHeuristic(GC_OBJECT(AssignmentHeuristic,((AssignmentHeuristic *) algo->buildAssignment)));
      choco_initLoop_LocalSearchSolver(algo);
      { OID gc_local;
        while ((choco_finishedLoop_LocalSearchSolver(algo) != CTRUE))
        { GC_LOOP;
          choco_move_FlipNeighborhood(GC_OBJECT(FlipNeighborhood,((FlipNeighborhood *) algo->changeAssignment)));
          choco_oneMoreMove_LocalSearchSolver(algo);
          GC_UNLOOP;} 
        } 
      choco_endLoop_LocalSearchSolver(algo);
      GC_UNLOOP;} 
    } 
  choco_restoreBestAssignment_LocalSearchSolver(algo);
  GC_UNBIND;} 


// v0.9906
/* The c++ function for: initIterations(algo:MultipleDescent) [] */
void  choco_initIterations_MultipleDescent(MultipleDescent *algo)
{ { Problem * pb = algo->problem;
    PropagationEngine * pe = pb->propagationEngine;
    if ((CLREAD(ChocEngine,pe,delayedConst2)->partition->objs[0] > 0) || 
        ((CLREAD(ChocEngine,pe,delayedConst3)->partition->objs[0] > 0) || 
          (CLREAD(ChocEngine,pe,delayedConst4)->partition->objs[0] > 0)))
     close_exception(((general_error *) (*Core._general_error)(_string_("local moves not implemented on problems with global constraints"),
      _oid_(Kernel.nil))));
    { ClaireBoolean * g1186I;
      { OID  g1187UU;
        { ITERATE(c);
          g1187UU= _oid_(CFALSE);
          bag *c_support;
          c_support = pb->constraints;
          for (START(c_support); NEXT(c);)
          if (INHERIT(OBJECT(ClaireObject,c)->isa,choco._CompositeConstraint))
           { g1187UU = Kernel.ctrue;
            break;} 
          } 
        g1186I = boolean_I_any(g1187UU);
        } 
      
      if (g1186I == CTRUE) close_exception(((general_error *) (*Core._general_error)(_string_("local moves not implemented on problems with Boolean combinations of constraints"),
          _oid_(Kernel.nil))));
        } 
    (algo->nbLocalSearch = 0);
    } 
  } 


// V0.9907
/* The c++ function for: finishedIterations(algo:LocalSearchSolver) [] */
ClaireBoolean * choco_finishedIterations_LocalSearchSolver(LocalSearchSolver *algo)
{ return (((algo->maxNbLocalSearch <= CLREAD(MultipleDescent,algo,nbLocalSearch)) ? CTRUE : ((CLREAD(ConflictCount,algo->problem->invariantEngine,minNbViolatedConstraints) == 0) ? CTRUE : CFALSE)));} 


// V0.9907
/* The c++ function for: oneMoreMove(algo:LocalSearchSolver) [] */
void  choco_oneMoreMove_LocalSearchSolver(LocalSearchSolver *algo)
{ (CLREAD(MultipleDescent,algo,nbLocalMoves) = (CLREAD(MultipleDescent,algo,nbLocalMoves)+1));
  } 


// v1.0 generic event: a constraint becomes conflicting
/* The c++ function for: becomesAConflict(c:AbstractConstraint,ie:ConflictCount) [] */
void  choco_becomesAConflict_AbstractConstraint(AbstractConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CTRUE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
  { int  n = choco.getNbVars->fcall(((int) c));
    int  i = 1;
    int  g1188 = n;
    { OID gc_local;
      while ((i <= g1188))
      { GC_LOOP;
        choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,OBJECT(IntVar,(*choco.getVar)(_oid_(c),
          i))));
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: becomesAConflict(c:UnIntConstraint,ie:ConflictCount) [] */
void  choco_becomesAConflict_UnIntConstraint(UnIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CTRUE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  GC_UNBIND;} 


/* The c++ function for: becomesAConflict(c:BinIntConstraint,ie:ConflictCount) [] */
void  choco_becomesAConflict_BinIntConstraint(BinIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CTRUE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v2));
  GC_UNBIND;} 


/* The c++ function for: becomesAConflict(c:TernIntConstraint,ie:ConflictCount) [] */
void  choco_becomesAConflict_TernIntConstraint(TernIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CTRUE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v2));
  choco_addOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v3));
  GC_UNBIND;} 


/* The c++ function for: becomesAConflict(c:LargeIntConstraint,ie:ConflictCount) [] */
void  choco_becomesAConflict_LargeIntConstraint(LargeIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CTRUE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints+1));
  { OID gc_local;
    ITERATE(v);
    bag *v_support;
    v_support = GC_OBJECT(list,c->vars);
    for (START(v_support); NEXT(v);)
    { GC_LOOP;
      choco_addOneConflict_ConflictCount(ie,OBJECT(IntVar,v));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// v1.0 generic event: a constraint becomes un-conflicting
/* The c++ function for: becomesSatisfied(c:AbstractConstraint,ie:ConflictCount) [] */
void  choco_becomesSatisfied_AbstractConstraint(AbstractConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CFALSE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints-1));
  { int  n = choco.getNbVars->fcall(((int) c));
    int  i = 1;
    int  g1189 = n;
    { OID gc_local;
      while ((i <= g1189))
      { GC_LOOP;
        choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,OBJECT(IntVar,(*choco.getVar)(_oid_(c),
          i))));
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: becomesSatisfied(c:UnIntConstraint,ie:ConflictCount) [] */
void  choco_becomesSatisfied_UnIntConstraint(UnIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CFALSE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints-1));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  GC_UNBIND;} 


/* The c++ function for: becomesSatisfied(c:BinIntConstraint,ie:ConflictCount) [] */
void  choco_becomesSatisfied_BinIntConstraint(BinIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CFALSE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints-1));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v2));
  GC_UNBIND;} 


/* The c++ function for: becomesSatisfied(c:TernIntConstraint,ie:ConflictCount) [] */
void  choco_becomesSatisfied_TernIntConstraint(TernIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CFALSE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints-1));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v1));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v2));
  choco_removeOneConflict_ConflictCount(ie,GC_OBJECT(IntVar,c->v3));
  GC_UNBIND;} 


/* The c++ function for: becomesSatisfied(c:LargeIntConstraint,ie:ConflictCount) [] */
void  choco_becomesSatisfied_LargeIntConstraint(LargeIntConstraint *c,ConflictCount *ie)
{ GC_BIND;
  (c->violated = CFALSE);
  (ie->nbViolatedConstraints = (ie->nbViolatedConstraints-1));
  { OID gc_local;
    ITERATE(v);
    bag *v_support;
    v_support = GC_OBJECT(list,c->vars);
    for (START(v_support); NEXT(v);)
    { GC_LOOP;
      choco_removeOneConflict_ConflictCount(ie,OBJECT(IntVar,v));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// v1.0 two simple uitilities
// addOneConflict registers that variable v appears in one more conflicting constraints
/* The c++ function for: addOneConflict(ie:ConflictCount,v:IntVar) [] */
void  choco_addOneConflict_ConflictCount(ConflictCount *ie,IntVar *v)
{ GC_BIND;
  (v->nbViolatedConstraints = (v->nbViolatedConstraints+1));
  if (v->nbViolatedConstraints == 1)
   GC_OBJECT(list,ie->conflictingVars)->addFast(_oid_(v));
  GC_UNBIND;} 


// register that variable v appears in one conflicting constraint less
/* The c++ function for: removeOneConflict(ie:ConflictCount,v:IntVar) [] */
void  choco_removeOneConflict_ConflictCount(ConflictCount *ie,IntVar *v)
{ (v->nbViolatedConstraints = (v->nbViolatedConstraints-1));
  if (v->nbViolatedConstraints == 0)
   delete_bag(ie->conflictingVars,_oid_(v));
  } 


// Note: the events are not stored: immediate reaction
// we maintain incrementally:
//   - the sub-list of instantiated variables
//   - for each constraint, its status (violated vs. satisfied)
//   - a count of all conflicts (total #of constraints, #of constraints per var)
//   - a sub-list of variables occurring in violated constraints
/* The c++ function for: postAssignVal(ie:ConflictCount,v:IntVar,a:integer) [] */
void  choco_postAssignVal_ConflictCount(ConflictCount *ie,IntVar *v,int a)
{ GC_BIND;
  { list * l = GC_OBJECT(list,ie->assignedThenUnassignedVars);
    int  n = l->length;
    int  i = index_list(l,_oid_(v));
    int  j = ie->lastAssignedVar;
    if (i == 0)
     close_exception(((general_error *) (*Core._general_error)(_string_("~S is not present in algo.unassignedVars"),
      _oid_(list::alloc(1,_oid_(v))))));
    else { STOREI((*l)[i],(*(l))[(j+1)]);
        STOREI((*l)[(j+1)],_oid_(v));
        STOREI(ie->lastAssignedVar,(j+1));
        { OID gc_local;
          ITERATE(c);
          bag *c_support;
          c_support = GC_OBJECT(list,v->constraints);
          for (START(c_support); NEXT(c);)
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE)
             { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE)
               (OBJECT(AbstractConstraint,c)->violated = CFALSE);
              else (*choco.becomesAConflict)(c,
                  _oid_(ie));
                } 
            GC_UNLOOP;} 
          } 
        } 
      } 
  GC_UNBIND;} 


// The reaction to the unassignment is performed while the value is still in place
// the value is erased only after the reaction
/* The c++ function for: postUnAssignVal(ie:ConflictCount,v:IntVar) [] */
void  choco_postUnAssignVal_ConflictCount(ConflictCount *ie,IntVar *v)
{ GC_BIND;
  { list * l = GC_OBJECT(list,ie->assignedThenUnassignedVars);
    int  n = l->length;
    int  i = index_list(l,_oid_(v));
    int  j = ie->lastAssignedVar;
    if (i == 0)
     close_exception(((general_error *) (*Core._general_error)(_string_("~S is not present in pb.unassignedVars"),
      _oid_(list::alloc(1,_oid_(v))))));
    { OID gc_local;
      ITERATE(c);
      bag *c_support;
      c_support = GC_OBJECT(list,v->constraints);
      for (START(c_support); NEXT(c);)
      { GC_LOOP;
        { if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,c)))))) == CTRUE) && 
              (not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,c))))) == CTRUE))
           (*choco.becomesSatisfied)(c,
            _oid_(ie));
          STOREI((*l)[i],(*(l))[j]);
          STOREI((*l)[j],_oid_(v));
          STOREI(ie->lastAssignedVar,(j-1));
          } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: postChangeVal(ie:ConflictCount,v:IntVar,a:integer,b:integer) [] */
void  choco_postChangeVal_ConflictCount(ConflictCount *ie,IntVar *v,int a,int b)
{ GC_BIND;
  ;{ int  nbConflictsBefore = v->nbViolatedConstraints;
    if ((nbConflictsBefore == 0) && 
        (index_list(ie->conflictingVars,_oid_(v)) != 0))
     close_exception(((general_error *) (*Core._general_error)(_string_("~S with no conflicts and conflictingVars:~S"),
      _oid_(list::alloc(2,_oid_(v),GC_OID(_oid_(ie->conflictingVars)))))));
    { OID gc_local;
      ITERATE(c);
      bag *c_support;
      c_support = GC_OBJECT(list,v->constraints);
      for (START(c_support); NEXT(c);)
      { GC_LOOP;
        { ClaireBoolean * OKBefore = not_any(_oid_(OBJECT(AbstractConstraint,c)->violated));
          ClaireBoolean * OKNow = OKBefore;
          STOREI(v->value,b);
          OKNow= OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,c)))));
          if (OKNow != OKBefore)
           { if (OKNow == CTRUE)
             { if (OBJECT(AbstractConstraint,c)->violated != CTRUE)
               close_exception(((general_error *) (*Core._general_error)(_string_("~S should be violated and c.viol=~S"),
                _oid_(list::alloc(2,c,_oid_(OBJECT(AbstractConstraint,c)->violated))))));
              (*choco.becomesSatisfied)(c,
                _oid_(ie));
              } 
            else { if (OBJECT(AbstractConstraint,c)->violated == CTRUE)
                 close_exception(((general_error *) (*Core._general_error)(_string_("~S should not be violated and c.viol=~S"),
                  _oid_(list::alloc(2,c,_oid_(OBJECT(AbstractConstraint,c)->violated))))));
                (*choco.becomesAConflict)(c,
                  _oid_(ie));
                } 
              } 
          STOREI(v->value,a);
          } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 

