/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\palmapi.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:44 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 
// ** Summary : PaLM API
// Part 1  : Problems                
// Part 2  : Variables
// Part 3  : Posting constraints                             
// Part 4  : Removing constraints                             
// Part 5  : Propagating and solving problems
// Part 6  : Assignment constraints
// Part 7  : Negating constraints
// Part 8  : arithmetic constraints                         
// Part 9  : global constraints 
// Part 10 : boolean connectors 
// Part 11 : user-friendly tools                           
// *************************************************************
// *   Part 1 : Problems                                       *
// *************************************************************
// creating a problem
/* The c++ function for: makePalmProblem(s:string,n:integer) [] */
PalmProblem * palm_makePalmProblem_string1(char *s,int n)
{ GC_BIND;
  { PalmProblem *Result ;
    { PalmProblem * pb;
      { { PalmProblem * _CL_obj = ((PalmProblem *) GC_OBJECT(PalmProblem,new_object_class(palm._PalmProblem)));
          (_CL_obj->name = copy_string(s));
          { Problem * g0663; 
            GlobalSearchSolver * g0664;
            g0663 = _CL_obj;
            { GlobalSearchSolver * _CL_obj = ((GlobalSearchSolver *) GC_OBJECT(GlobalSearchSolver,new_object_class(choco._GlobalSearchSolver)));
              g0664 = _CL_obj;
              } 
            (g0663->globalSearchSolver = g0664);} 
          { Problem * g0665; 
            LocalSearchSolver * g0666;
            g0665 = _CL_obj;
            { LocalSearchSolver * _CL_obj = ((LocalSearchSolver *) GC_OBJECT(LocalSearchSolver,new_object_class(choco._LocalSearchSolver)));
              g0666 = _CL_obj;
              } 
            (g0665->localSearchSolver = g0666);} 
          pb = _CL_obj;
          } 
        GC_OBJECT(PalmProblem,pb);} 
      { PalmEngine * pe = GC_OBJECT(PalmEngine,palm_makePalmEngine_integer((n+1)));
        choco_attachPropagationEngine_Problem(pb,pe);
        } 
      palm_initPalmSolver_PalmProblem(pb);
      { PalmProblem * g0667; 
        PalmUserFriendlyBox * g0668;
        g0667 = pb;
        { PalmUserFriendlyBox * _CL_obj = ((PalmUserFriendlyBox *) GC_OBJECT(PalmUserFriendlyBox,new_object_class(palm._PalmUserFriendlyBox)));
          (_CL_obj->shortName = "PB");
          (_CL_obj->name = "The complete problem");
          g0668 = _CL_obj;
          } 
        (g0667->rootUFboxes = g0668);} 
      (palm.UFcurrentBox->value= _oid_(pb->rootUFboxes));
      GC_OBJECT(set,pb->userRepresentation)->addFast(GC_OID(_oid_(pb->rootUFboxes)));
      choco_setActiveProblem_Problem(pb);
      Result = pb;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makePalmProblem(s:string,n:integer,maxRlxLvl:integer) [] */
PalmProblem * palm_makePalmProblem_string2(char *s,int n,int maxRlxLvl)
{ GC_BIND;
  { PalmProblem *Result ;
    { PalmProblem * pb = GC_OBJECT(PalmProblem,palm_makePalmProblem_string1(s,n));
      (pb->maxRelaxLvl = maxRlxLvl);
      Result = pb;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: setObjective(pb:PalmProblem,v:PalmIntVar) [] */
void  palm_setObjective_PalmProblem(PalmProblem *pb,PalmIntVar *v)
{ (CLREAD(PalmBranchAndBound,pb->palmSolver,objective) = v);
  } 


/* The c++ function for: setSolutionVars(pb:PalmProblem,lv:list[PalmIntVar]) [] */
void  palm_setSolutionVars_PalmProblem(PalmProblem *pb,list *lv)
{ { Solver * g0669; 
    list * g0670;
    g0669 = pb->palmSolver;
    { bag *v_list; OID v_val;
      OID v,CLcount;
      v_list = lv;
       g0670 = v_list->clone(choco._IntVar);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { v = (*(v_list))[CLcount];
        v_val = v;
        
        (*((list *) g0670))[CLcount] = v_val;} 
      } 
    (g0669->varsToStore = g0670);} 
  } 


/* The c++ function for: choco/solutions(pb:PalmProblem) [] */
list * choco_solutions_PalmProblem(PalmProblem *pb)
{ return (pb->palmSolver->solutions);} 


// solve for possibly all solutions ...
/* The c++ function for: solve(pb:PalmProblem,lbr:list[PalmBranching],allSolutions:boolean) [] */
void  palm_solve_PalmProblem1(PalmProblem *pb,list *lbr,ClaireBoolean *allSolutions)
{ GC_BIND;
  { list * g0671;
    { { bag *v_list; OID v_val;
        OID g0672,CLcount;
        v_list = lbr;
         g0671 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0672 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0672)));
          
          (*((list *) g0671))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0671);} 
    int  g0673 = g0671->length;
    PalmExtend * g0674 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0675 = 1;
      int  g0677 = (g0673-1);
      { OID gc_local;
        while ((g0675 <= g0677))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0671))[g0675]),
            3,
            Kernel._object,
            (*(g0671))[(g0675+1)]);
          ++g0675;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0676);
      for (START(g0671); NEXT(g0676);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0676)->extender = g0674);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0674,
      3,
      Kernel._object,
      (*(g0671))[1]);
    } 
  if (allSolutions == CTRUE)
   { PalmState * st = GC_OBJECT(PalmState,pb->palmSolver->state);
    ClaireBoolean * soluble = palm_searchOneSolution_PalmProblem(pb);
    { OID gc_local;
      while ((soluble == CTRUE))
      { // HOHO, GC_LOOP not needed !
        (((soluble= palm_discardCurrentSolution_PalmState(st)) == CTRUE) ? ((palm_searchOneSolution_PalmProblem(pb) == CTRUE) ? CTRUE: CFALSE): CFALSE);
        } 
      } 
    } 
  else palm_solve_PalmProblem4(pb);
    GC_UNBIND;} 


/* The c++ function for: solve(pb:PalmProblem,allSolutions:boolean) [] */
void  palm_solve_PalmProblem2(PalmProblem *pb,ClaireBoolean *allSolutions)
{ GC_BIND;
  ;if (allSolutions == CTRUE)
   { PalmState * st = GC_OBJECT(PalmState,pb->palmSolver->state);
    ClaireBoolean * soluble = palm_searchOneSolution_PalmProblem(pb);
    { while ((soluble == CTRUE))
      { (((soluble= palm_discardCurrentSolution_PalmState(st)) == CTRUE) ? ((palm_searchOneSolution_PalmProblem(pb) == CTRUE) ? CTRUE: CFALSE): CFALSE);
        } 
      } 
    } 
  else palm_searchOneSolution_PalmProblem(pb);
    GC_UNBIND;} 


/* The c++ function for: solve(pb:PalmProblem,lbr:list[PalmBranching]) [] */
void  palm_solve_PalmProblem3(PalmProblem *pb,list *lbr)
{ GC_BIND;
  { list * g0678;
    { { bag *v_list; OID v_val;
        OID g0679,CLcount;
        v_list = lbr;
         g0678 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0679 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0679)));
          
          (*((list *) g0678))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0678);} 
    int  g0680 = g0678->length;
    PalmExtend * g0681 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0682 = 1;
      int  g0684 = (g0680-1);
      { OID gc_local;
        while ((g0682 <= g0684))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0678))[g0682]),
            3,
            Kernel._object,
            (*(g0678))[(g0682+1)]);
          ++g0682;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0683);
      for (START(g0678); NEXT(g0683);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0683)->extender = g0681);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0681,
      3,
      Kernel._object,
      (*(g0678))[1]);
    } 
  palm_searchOneSolution_PalmProblem(pb);
  GC_UNBIND;} 


/* The c++ function for: solve(pb:PalmProblem) [] */
void  palm_solve_PalmProblem4(PalmProblem *pb)
{ palm_solve_PalmProblem2(pb,CFALSE);
  } 


/* The c++ function for: minimize(pb:PalmProblem,obj:PalmIntVar,lbr:list[PalmBranching]) [] */
int  palm_minimize_PalmProblem1(PalmProblem *pb,PalmIntVar *obj,list *lbr)
{ GC_BIND;
  { list * g0685;
    { { bag *v_list; OID v_val;
        OID g0686,CLcount;
        v_list = lbr;
         g0685 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0686 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0686)));
          
          (*((list *) g0685))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0685);} 
    int  g0687 = g0685->length;
    PalmExtend * g0688 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0689 = 1;
      int  g0691 = (g0687-1);
      { OID gc_local;
        while ((g0689 <= g0691))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0685))[g0689]),
            3,
            Kernel._object,
            (*(g0685))[(g0689+1)]);
          ++g0689;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0690);
      for (START(g0685); NEXT(g0690);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0690)->extender = g0688);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0688,
      3,
      Kernel._object,
      (*(g0685))[1]);
    } 
  { int Result = 0;
    Result = palm_minimize_PalmProblem2(pb,obj);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: minimize(pb:PalmProblem,obj:PalmIntVar) [] */
int  palm_minimize_PalmProblem2(PalmProblem *pb,PalmIntVar *obj)
{ GC_BIND;
  palm_initPalmBranchAndBound_PalmProblem(pb,CFALSE,obj);
  (*palm.run)(GC_OID(_oid_(pb->palmSolver)));
  { int Result = 0;
    Result = CLREAD(PalmBranchAndBound,pb->palmSolver,upperBound);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: minimize(pb:PalmProblem,obj:PalmIntVar,lb:integer,ub:integer,lbr:list[PalmBranching]) [] */
int  palm_minimize_PalmProblem3(PalmProblem *pb,PalmIntVar *obj,int lb,int ub,list *lbr)
{ GC_BIND;
  { list * g0692;
    { { bag *v_list; OID v_val;
        OID g0693,CLcount;
        v_list = lbr;
         g0692 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0693 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0693)));
          
          (*((list *) g0692))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0692);} 
    int  g0694 = g0692->length;
    PalmExtend * g0695 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0696 = 1;
      int  g0698 = (g0694-1);
      { OID gc_local;
        while ((g0696 <= g0698))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0692))[g0696]),
            3,
            Kernel._object,
            (*(g0692))[(g0696+1)]);
          ++g0696;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0697);
      for (START(g0692); NEXT(g0697);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0697)->extender = g0695);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0695,
      3,
      Kernel._object,
      (*(g0692))[1]);
    } 
  { int Result = 0;
    Result = palm_minimize_PalmProblem4(pb,obj,lb,ub);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: minimize(pb:PalmProblem,obj:PalmIntVar,lb:integer,ub:integer) [] */
int  palm_minimize_PalmProblem4(PalmProblem *pb,PalmIntVar *obj,int lb,int ub)
{ GC_BIND;
  palm_initPalmBranchAndBound_PalmProblem(pb,CFALSE,obj);
  (CLREAD(PalmBranchAndBound,pb->palmSolver,upperBound) = ub);
  (CLREAD(PalmBranchAndBound,pb->palmSolver,lowerBound) = lb);
  (*palm.run)(GC_OID(_oid_(pb->palmSolver)));
  { int Result = 0;
    Result = CLREAD(PalmBranchAndBound,pb->palmSolver,upperBound);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: maximize(pb:PalmProblem,obj:PalmIntVar,lbr:list[PalmBranching]) [] */
int  palm_maximize_PalmProblem1(PalmProblem *pb,PalmIntVar *obj,list *lbr)
{ GC_BIND;
  { list * g0699;
    { { bag *v_list; OID v_val;
        OID g0700,CLcount;
        v_list = lbr;
         g0699 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0700 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0700)));
          
          (*((list *) g0699))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0699);} 
    int  g0701 = g0699->length;
    PalmExtend * g0702 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0703 = 1;
      int  g0705 = (g0701-1);
      { OID gc_local;
        while ((g0703 <= g0705))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0699))[g0703]),
            3,
            Kernel._object,
            (*(g0699))[(g0703+1)]);
          ++g0703;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0704);
      for (START(g0699); NEXT(g0704);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0704)->extender = g0702);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0702,
      3,
      Kernel._object,
      (*(g0699))[1]);
    } 
  { int Result = 0;
    Result = palm_maximize_PalmProblem2(pb,obj);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: maximize(pb:PalmProblem,obj:PalmIntVar) [] */
int  palm_maximize_PalmProblem2(PalmProblem *pb,PalmIntVar *obj)
{ GC_BIND;
  palm_initPalmBranchAndBound_PalmProblem(pb,CTRUE,obj);
  (*palm.run)(GC_OID(_oid_(pb->palmSolver)));
  { int Result = 0;
    Result = CLREAD(PalmBranchAndBound,pb->palmSolver,lowerBound);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: maximize(pb:PalmProblem,obj:PalmIntVar,lb:integer,ub:integer,lbr:list[PalmBranching]) [] */
int  palm_maximize_PalmProblem3(PalmProblem *pb,PalmIntVar *obj,int lb,int ub,list *lbr)
{ GC_BIND;
  { list * g0706;
    { { bag *v_list; OID v_val;
        OID g0707,CLcount;
        v_list = lbr;
         g0706 = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { g0707 = (*(v_list))[CLcount];
          v_val = _oid_(copy_object(OBJECT(ClaireObject,g0707)));
          
          (*((list *) g0706))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,g0706);} 
    int  g0708 = g0706->length;
    PalmExtend * g0709 = GC_OBJECT(PalmExtend,pb->palmSolver->extending);
    { int  g0710 = 1;
      int  g0712 = (g0708-1);
      { OID gc_local;
        while ((g0710 <= g0712))
        { // HOHO, GC_LOOP not needed !
          update_property(palm.nextBranching,
            OBJECT(ClaireObject,(*(g0706))[g0710]),
            3,
            Kernel._object,
            (*(g0706))[(g0710+1)]);
          ++g0710;
          } 
        } 
      } 
    { OID gc_local;
      ITERATE(g0711);
      for (START(g0706); NEXT(g0711);)
      { GC_LOOP;
        (OBJECT(PalmBranching,g0711)->extender = g0709);
        GC_UNLOOP;} 
      } 
    update_property(palm.branching,
      g0709,
      3,
      Kernel._object,
      (*(g0706))[1]);
    } 
  { int Result = 0;
    Result = palm_maximize_PalmProblem4(pb,obj,lb,ub);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: maximize(pb:PalmProblem,obj:PalmIntVar,lb:integer,ub:integer) [] */
int  palm_maximize_PalmProblem4(PalmProblem *pb,PalmIntVar *obj,int lb,int ub)
{ GC_BIND;
  palm_initPalmBranchAndBound_PalmProblem(pb,CTRUE,obj);
  (CLREAD(PalmBranchAndBound,pb->palmSolver,upperBound) = ub);
  (CLREAD(PalmBranchAndBound,pb->palmSolver,lowerBound) = lb);
  (*palm.run)(GC_OID(_oid_(pb->palmSolver)));
  { int Result = 0;
    Result = CLREAD(PalmBranchAndBound,pb->palmSolver,lowerBound);
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 2 : Variables                                      *
// *************************************************************
// creating a variable on the fly (note the same API that choco)
/* The c++ function for: makeBoundIntVar(p:PalmProblem,s:string,i:integer,j:integer) [] */
PalmIntVar * palm_makeBoundIntVar_PalmProblem(PalmProblem *p,char *s,int i,int j)
{ GC_BIND;
  ;{ PalmIntVar *Result ;
    { PalmIntVar * v;
      { { PalmIntVar * _CL_obj = ((PalmIntVar *) GC_OBJECT(PalmIntVar,new_object_class(palm._PalmIntVar)));
          (_CL_obj->name = copy_string(s));
          (_CL_obj->originalInf = i);
          (_CL_obj->originalSup = j);
          v = _CL_obj;
          } 
        GC_OBJECT(PalmIntVar,v);} 
      { IntVar * g0714; 
        StoredInt * g0715;
        g0714 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = i);
          g0715 = _CL_obj;
          } 
        (g0714->inf = g0715);} 
      { IntVar * g0716; 
        StoredInt * g0717;
        g0716 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = j);
          g0717 = _CL_obj;
          } 
        (g0716->sup = g0717);} 
      if (i == j)
       STOREI(v->value,i);
      { IntVar * g0718; 
        IncInf * g0719;
        g0718 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g0719 = _CL_obj;
          } 
        (g0718->updtInfEvt = g0719);} 
      { IntVar * g0720; 
        DecSup * g0721;
        g0720 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g0721 = _CL_obj;
          } 
        (g0720->updtSupEvt = g0721);} 
      { IntVar * g0722; 
        InstInt * g0723;
        g0722 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g0723 = _CL_obj;
          } 
        (g0722->instantiateEvt = g0723);} 
      { ValueRemovals * g0713;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = 1);
            g0713 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g0713);} 
        (g0713->valueStack = make_list_integer2(g0713->maxVals,Kernel._integer,0));
        (g0713->causeStack = make_list_integer2(g0713->maxVals,Kernel._integer,0));
        (v->remValEvt = g0713);
        } 
      choco_addIntVar_Problem(p,v);
      { PalmIntVar * g0724; 
        DecInf * g0725;
        g0724 = v;
        { DecInf * _CL_obj = ((DecInf *) GC_OBJECT(DecInf,new_object_class(palm._DecInf)));
          (_CL_obj->modifiedVar = v);
          g0725 = _CL_obj;
          } 
        (g0724->restInfEvt = g0725);} 
      { PalmIntVar * g0726; 
        IncSup * g0727;
        g0726 = v;
        { IncSup * _CL_obj = ((IncSup *) GC_OBJECT(IncSup,new_object_class(palm._IncSup)));
          (_CL_obj->modifiedVar = v);
          g0727 = _CL_obj;
          } 
        (g0726->restSupEvt = g0727);} 
      { OID  g0728UU;
        { { PalmIncInfExplanation * V_CL0729;{ Explanation * g0730UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0730UU = _CL_obj;
                } 
              V_CL0729 = palm_makeIncInfExplanation_Explanation(g0730UU,i,v);
              } 
            
            g0728UU=_oid_(V_CL0729);} 
          GC_OID(g0728UU);} 
        GC_OBJECT(list,v->explanationOnInf)->addFast(g0728UU);
        } 
      { OID  g0731UU;
        { { PalmDecSupExplanation * V_CL0732;{ Explanation * g0733UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0733UU = _CL_obj;
                } 
              V_CL0732 = palm_makeDecSupExplanation_Explanation(g0733UU,j,v);
              } 
            
            g0731UU=_oid_(V_CL0732);} 
          GC_OID(g0731UU);} 
        GC_OBJECT(list,v->explanationOnSup)->addFast(g0731UU);
        } 
      (v->restValEvt = palm_makeValueRestorations_PalmIntVar(v,0));
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


// creating a variable on the fly (note the same API that choco)
/* The c++ function for: makeIntVar(p:PalmProblem,s:string,i:integer,j:integer) [] */
PalmIntVar * palm_makeIntVar_PalmProblem1(PalmProblem *p,char *s,int i,int j)
{ GC_BIND;
  ;{ PalmIntVar *Result ;
    { PalmIntVar * v;
      { { PalmIntVar * _CL_obj = ((PalmIntVar *) GC_OBJECT(PalmIntVar,new_object_class(palm._PalmIntVar)));
          (_CL_obj->name = copy_string(s));
          (_CL_obj->originalInf = i);
          (_CL_obj->originalSup = j);
          v = _CL_obj;
          } 
        GC_OBJECT(PalmIntVar,v);} 
      { IntVar * g0735; 
        StoredInt * g0736;
        g0735 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = i);
          g0736 = _CL_obj;
          } 
        (g0735->inf = g0736);} 
      { IntVar * g0737; 
        StoredInt * g0738;
        g0737 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = j);
          g0738 = _CL_obj;
          } 
        (g0737->sup = g0738);} 
      if (i == j)
       STOREI(v->value,i);
      { IntVar * g0739; 
        IncInf * g0740;
        g0739 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g0740 = _CL_obj;
          } 
        (g0739->updtInfEvt = g0740);} 
      { IntVar * g0741; 
        DecSup * g0742;
        g0741 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g0742 = _CL_obj;
          } 
        (g0741->updtSupEvt = g0742);} 
      { IntVar * g0743; 
        InstInt * g0744;
        g0743 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g0744 = _CL_obj;
          } 
        (g0743->instantiateEvt = g0744);} 
      { ValueRemovals * g0734;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = ((j-i)+2));
            g0734 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g0734);} 
        (g0734->valueStack = make_list_integer2(g0734->maxVals,Kernel._integer,0));
        (g0734->causeStack = make_list_integer2(g0734->maxVals,Kernel._integer,0));
        (v->remValEvt = g0734);
        } 
      choco_addIntVar_Problem(p,v);
      (v->bucket = palm_makePalmIntDomain_integer(i,j));
      { PalmIntVar * g0745; 
        DecInf * g0746;
        g0745 = v;
        { DecInf * _CL_obj = ((DecInf *) GC_OBJECT(DecInf,new_object_class(palm._DecInf)));
          (_CL_obj->modifiedVar = v);
          g0746 = _CL_obj;
          } 
        (g0745->restInfEvt = g0746);} 
      { PalmIntVar * g0747; 
        IncSup * g0748;
        g0747 = v;
        { IncSup * _CL_obj = ((IncSup *) GC_OBJECT(IncSup,new_object_class(palm._IncSup)));
          (_CL_obj->modifiedVar = v);
          g0748 = _CL_obj;
          } 
        (g0747->restSupEvt = g0748);} 
      { OID  g0749UU;
        { { PalmIncInfExplanation * V_CL0750;{ Explanation * g0751UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0751UU = _CL_obj;
                } 
              V_CL0750 = palm_makeIncInfExplanation_Explanation(g0751UU,i,v);
              } 
            
            g0749UU=_oid_(V_CL0750);} 
          GC_OID(g0749UU);} 
        GC_OBJECT(list,v->explanationOnInf)->addFast(g0749UU);
        } 
      { OID  g0752UU;
        { { PalmDecSupExplanation * V_CL0753;{ Explanation * g0754UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0754UU = _CL_obj;
                } 
              V_CL0753 = palm_makeDecSupExplanation_Explanation(g0754UU,j,v);
              } 
            
            g0752UU=_oid_(V_CL0753);} 
          GC_OID(g0752UU);} 
        GC_OBJECT(list,v->explanationOnSup)->addFast(g0752UU);
        } 
      (v->restValEvt = palm_makeValueRestorations_PalmIntVar(v,((j-i)+2)));
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeIntVar(p:PalmProblem,s:string,b:(list[integer] U set[integer])) [] */
PalmIntVar * palm_makeIntVar_PalmProblem2(PalmProblem *p,char *s,bag *b)
{ GC_BIND;
  { PalmIntVar *Result ;
    { int  minVal;
      { int  g0755 = 99999999;
        { OID gc_local;
          ITERATE(g0756);
          for (START(b); NEXT(g0756);)
          { GC_LOOP;
            g0755= ((g0756 <= g0755) ?
              g0756 :
              g0755 );
            GC_UNLOOP;} 
          } 
        minVal = g0755;
        } 
      int  maxVal;
      { int  g0757 = -99999999;
        { OID gc_local;
          ITERATE(g0758);
          for (START(b); NEXT(g0758);)
          { GC_LOOP;
            g0757= ((g0758 <= g0757) ?
              g0757 :
              g0758 );
            GC_UNLOOP;} 
          } 
        maxVal = g0757;
        } 
      PalmIntVar * v = GC_OBJECT(PalmIntVar,palm_makeIntVar_PalmProblem1(p,s,minVal,maxVal));
      { int  val = minVal;
        int  g0759 = maxVal;
        { OID gc_local;
          while ((val <= g0759))
          { GC_LOOP;
            if (belong_to(val,_oid_(b)) != CTRUE)
             _oid_((ClaireObject *) choco.removeDomainVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) val)));
            ++val;
            GC_UNLOOP;} 
          } 
        } 
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 stronger typing of b
/* The c++ function for: makeIntVar(p:PalmProblem,b:(list[integer] U set[integer])) [] */
PalmIntVar * palm_makeIntVar_PalmProblem3(PalmProblem *p,bag *b)
{ return (palm_makeIntVar_PalmProblem2(p,"<PalmIntVar>",b));} 


/* The c++ function for: makeConstantPalmIntVar(x:integer) [] */
PalmIntVar * palm_makeConstantPalmIntVar_integer(int x)
{ GC_BIND;
  { PalmIntVar *Result ;
    { PalmIntVar * v;
      { { PalmIntVar * _CL_obj = ((PalmIntVar *) GC_OBJECT(PalmIntVar,new_object_class(palm._PalmIntVar)));
          (_CL_obj->name = append_string(GC_STRING(append_string("'",GC_STRING(string_I_integer (x)))),"'"));
          (_CL_obj->originalInf = x);
          (_CL_obj->originalSup = x);
          v = _CL_obj;
          } 
        GC_OBJECT(PalmIntVar,v);} 
      { IntVar * g0761; 
        StoredInt * g0762;
        g0761 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = x);
          g0762 = _CL_obj;
          } 
        (g0761->inf = g0762);} 
      { IntVar * g0763; 
        StoredInt * g0764;
        g0763 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = x);
          g0764 = _CL_obj;
          } 
        (g0763->sup = g0764);} 
      if (x == x)
       STOREI(v->value,x);
      { IntVar * g0765; 
        IncInf * g0766;
        g0765 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g0766 = _CL_obj;
          } 
        (g0765->updtInfEvt = g0766);} 
      { IntVar * g0767; 
        DecSup * g0768;
        g0767 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g0768 = _CL_obj;
          } 
        (g0767->updtSupEvt = g0768);} 
      { IntVar * g0769; 
        InstInt * g0770;
        g0769 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g0770 = _CL_obj;
          } 
        (g0769->instantiateEvt = g0770);} 
      { ValueRemovals * g0760;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = 2);
            g0760 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g0760);} 
        (g0760->valueStack = make_list_integer2(g0760->maxVals,Kernel._integer,0));
        (g0760->causeStack = make_list_integer2(g0760->maxVals,Kernel._integer,0));
        (v->remValEvt = g0760);
        } 
      { PalmIntVar * g0771; 
        DecInf * g0772;
        g0771 = v;
        { DecInf * _CL_obj = ((DecInf *) GC_OBJECT(DecInf,new_object_class(palm._DecInf)));
          (_CL_obj->modifiedVar = v);
          g0772 = _CL_obj;
          } 
        (g0771->restInfEvt = g0772);} 
      { PalmIntVar * g0773; 
        IncSup * g0774;
        g0773 = v;
        { IncSup * _CL_obj = ((IncSup *) GC_OBJECT(IncSup,new_object_class(palm._IncSup)));
          (_CL_obj->modifiedVar = v);
          g0774 = _CL_obj;
          } 
        (g0773->restSupEvt = g0774);} 
      { OID  g0775UU;
        { { PalmIncInfExplanation * V_CL0776;{ Explanation * g0777UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0777UU = _CL_obj;
                } 
              V_CL0776 = palm_makeIncInfExplanation_Explanation(g0777UU,x,v);
              } 
            
            g0775UU=_oid_(V_CL0776);} 
          GC_OID(g0775UU);} 
        GC_OBJECT(list,v->explanationOnInf)->addFast(g0775UU);
        } 
      { OID  g0778UU;
        { { PalmDecSupExplanation * V_CL0779;{ Explanation * g0780UU;
              { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                g0780UU = _CL_obj;
                } 
              V_CL0779 = palm_makeDecSupExplanation_Explanation(g0780UU,x,v);
              } 
            
            g0778UU=_oid_(V_CL0779);} 
          GC_OID(g0778UU);} 
        GC_OBJECT(list,v->explanationOnSup)->addFast(g0778UU);
        } 
      (v->restValEvt = palm_makeValueRestorations_PalmIntVar(v,0));
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 3 : Posting constraints                            *
// *************************************************************
// posting a constraint into a problem
/* The c++ function for: choco/post(p:PalmProblem,c:((PalmUnIntConstraint U PalmBinIntConstraint) U PalmLargeIntConstraint)) [] */
void  choco_post_PalmProblem1(PalmProblem *p,IntConstraint *c)
{ GC_BIND;
  { ClaireBoolean * evCon = OBJECT(PalmInfoConstraint,c->hook)->everConnected;
    PropagationEngine * pe = GC_OBJECT(PropagationEngine,p->propagationEngine);
    int  prio = (*choco.getPriority)(_oid_(c));
    ConstAwakeEvent * e;
    { if (evCon == CTRUE)
       e = c->constAwakeEvent;
      else { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
          (_CL_obj->touchedConst = c);
          (_CL_obj->initialized = CFALSE);
          (_CL_obj->priority = prio);
          e = _CL_obj;
          } 
        GC_OBJECT(ConstAwakeEvent,e);} 
    if ((OBJECT(ClaireBoolean,palm.PALM_USER_FRIENDLY->value)) == CTRUE)
     { (OBJECT(PalmInfoConstraint,c->hook)->ufBox = OBJECT(PalmUserFriendlyBox,palm.UFcurrentBox->value));
      GC_OBJECT(list,OBJECT(PalmUserFriendlyBox,palm.UFcurrentBox->value)->constraints)->addFast(_oid_(c));
      } 
    if (evCon != CTRUE)
     GC_OBJECT(list,p->constraints)->addFast(_oid_(c));
    (c->constAwakeEvent = e);
    if (evCon != CTRUE)
     choco_registerEvent_ChocEngine(((ChocEngine *) pe),e);
    choco_constAwake_AbstractConstraint(c,CTRUE);
    if (evCon != CTRUE)
     (*choco.connect)(_oid_(c));
    else (*choco.reconnect)(_oid_(c));
      } 
  GC_UNBIND;} 


// posting a list of constraints into a problem
/* The c++ function for: choco/post(p:PalmProblem,cl:list[choco/AbstractConstraint]) [] */
void  choco_post_PalmProblem2(PalmProblem *p,list *cl)
{ { ITERATE(c);
    for (START(cl); NEXT(c);)
    (*choco.post)(_oid_(p),
      c);
    } 
  } 


// posting a constraint with a preference (weigth)
/* The c++ function for: choco/post(p:PalmProblem,c:choco/AbstractConstraint,w:integer) [] */
void  choco_post_PalmProblem3(PalmProblem *p,AbstractConstraint *c,int w)
{ GC_BIND;
  (*choco.post)(_oid_(p),
    _oid_(c));
  write_property(palm.weight,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->hook)),w);
  GC_UNBIND;} 


/* The c++ function for: choco/post(p:PalmProblem,d:choco/Delayer,w:integer) [] */
void  choco_post_PalmProblem4(PalmProblem *p,Delayer *d,int w)
{ GC_BIND;
  choco_post_Problem(p,d);
  write_property(palm.weight,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,d->target->hook)),w);
  GC_UNBIND;} 


// posting a list of constraints with a common weight
/* The c++ function for: choco/post(p:PalmProblem,cl:list[choco/AbstractConstraint],w:integer) [] */
void  choco_post_PalmProblem5(PalmProblem *p,list *cl,int w)
{ { ITERATE(c);
    for (START(cl); NEXT(c);)
    (*choco.post)(_oid_(p),
      c,
      w);
    } 
  } 


// posting an indirect constraint with an explanation
/* The c++ function for: choco/post(p:PalmProblem,c:choco/AbstractConstraint,e:Explanation) [] */
void  choco_post_PalmProblem6(PalmProblem *p,AbstractConstraint *c,Explanation *e)
{ palm_setIndirect_AbstractConstraint(c,e);
  palm_setIndirectDependance_AbstractConstraint(c,e);
  (*choco.post)(_oid_(p),
    _oid_(c));
  } 


/* The c++ function for: choco/post(p:PalmProblem,d:choco/Delayer,e:Explanation) [] */
void  choco_post_PalmProblem7(PalmProblem *p,Delayer *d,Explanation *e)
{ GC_BIND;
  palm_setIndirect_AbstractConstraint(GC_OBJECT(IntConstraint,d->target),e);
  palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(IntConstraint,d->target),e);
  choco_post_Problem(p,d);
  GC_UNBIND;} 


// posting a list of indirect constraints with a common explanation
/* The c++ function for: choco/post(p:PalmProblem,cl:list[choco/AbstractConstraint],e:Explanation) [] */
void  choco_post_PalmProblem8(PalmProblem *p,list *cl,Explanation *e)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c);
    for (START(cl); NEXT(c);)
    { GC_LOOP;
      (*choco.post)(_oid_(p),
        c,
        GC_OID(_oid_(copy_object(e))));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// further constraining a domain: adding information to the current state
// (restricting by hand the domain of a variable)
/* The c++ function for: setMin(v:PalmIntVar,x:integer) [] */
void  palm_setMin_PalmIntVar(PalmIntVar *v,int x)
{ GC_BIND;
  (*choco.post)(_oid_(choco_getProblem_IntVar(v)),
    GC_OID(_oid_(claire__sup_equal_PalmIntVar1(v,x))));
  GC_UNBIND;} 


/* The c++ function for: setMax(v:PalmIntVar,x:integer) [] */
void  palm_setMax_PalmIntVar(PalmIntVar *v,int x)
{ GC_BIND;
  (*choco.post)(_oid_(choco_getProblem_IntVar(v)),
    GC_OID(_oid_(claire__inf_equal_PalmIntVar1(v,x))));
  GC_UNBIND;} 


/* The c++ function for: setVal(v:PalmIntVar,x:integer) [] */
void  palm_setVal_PalmIntVar(PalmIntVar *v,int x)
{ GC_BIND;
  (*choco.post)(_oid_(choco_getProblem_IntVar(v)),
    GC_OID(_oid_(claire__equal_equal_PalmIntVar1(v,x))));
  GC_UNBIND;} 


/* The c++ function for: remVal(v:PalmIntVar,x:integer) [] */
void  palm_remVal_PalmIntVar(PalmIntVar *v,int x)
{ GC_BIND;
  (*choco.post)(_oid_(choco_getProblem_IntVar(v)),
    GC_OID(_oid_(claire__I_equal_equal_PalmIntVar1(v,x))));
  GC_UNBIND;} 


// *************************************************************
// *   Part 4 : Removing constraints                           *
// *************************************************************
// removing a single constraint
/* The c++ function for: remove(pb:PalmProblem,ct:choco/AbstractConstraint) [] */
void  palm_remove_PalmProblem1(PalmProblem *pb,AbstractConstraint *ct)
{ GC_BIND;
  palm_remove_PalmEngine1(GC_OBJECT(PalmEngine,((PalmEngine *) pb->propagationEngine)),ct);
  GC_UNBIND;} 


// Removing a set of constraints in one single step 
/* The c++ function for: remove(pb:PalmProblem,cl:set[choco/AbstractConstraint]) [] */
void  palm_remove_PalmProblem2(PalmProblem *pb,set *cl)
{ GC_BIND;
  palm_remove_PalmEngine2(GC_OBJECT(PalmEngine,((PalmEngine *) pb->propagationEngine)),GC_OBJECT(list,list_I_set(cl)));
  GC_UNBIND;} 


// Removing a list of constraints in one single step 
/* The c++ function for: remove(pb:PalmProblem,cl:list[choco/AbstractConstraint]) [] */
void  palm_remove_PalmProblem3(PalmProblem *pb,list *cl)
{ GC_BIND;
  palm_remove_PalmEngine2(GC_OBJECT(PalmEngine,((PalmEngine *) pb->propagationEngine)),cl);
  GC_UNBIND;} 


// *************************************************************
// *   Part 5 : Propagating and solving problems               *
// *************************************************************
// full propagation (all levels) for a problem: this is the only public propagation method
/* The c++ function for: choco/propagate(p:PalmProblem) [] */
void  choco_propagate_PalmProblem(PalmProblem *p)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { PropagationEngine * pe = p->propagationEngine;
    ClaireBoolean * someEvents = CTRUE;
    { OID gc_local;
      while ((someEvents == CTRUE))
      { GC_LOOP;
        { OID  qtest = GC_OID(choco.getNextActiveEventQueue->fcall(((int) pe)));
          if (qtest != CNULL)
           { EventCollection * q = OBJECT(EventCollection,qtest);
            _void_(choco.popSomeEvents->fcall(((int) q)));
            } 
          else someEvents= CFALSE;
            } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 6 : Instanciation constraints                             *
// ********************************************************************
// util: build a table with:
//       - domain: 'memberType'
//       - range: integer 
//       - defaultValue:0
/* The c++ function for: makeConstraintTable(memberType:type) [] */
table * palm_makeConstraintTable_type(ClaireType *memberType)
{ { table *Result ;
    { table * t = ((table *) new_object_class(Kernel._table));
      (t->range = OBJECT(ClaireType,palm.AbstractConstraint->value));
      (t->domain = memberType);
      (t->DEFAULT = palm.UNKNOWN_ABS_CT->value);
      (t->graph = make_list_integer(29,CNULL));
      Result = t;
      } 
    return (Result);} 
  } 


/* The c++ function for: assign(v:PalmIntVar,x:integer) [] */
AssignmentConstraint * palm_assign_PalmIntVar(PalmIntVar *v,int x)
{ GC_BIND;
  { AssignmentConstraint *Result ;
    { AbstractConstraint *V_CC ;
      if (v->bucket != (NULL))
       { OID  cttest = (*(CLREAD(PalmIntDomain,v->bucket,instantiationConstraints)))[(x-CLREAD(PalmIntDomain,v->bucket,offset))];
        if (cttest != CNULL)
         { AssignmentConstraint * ct = OBJECT(AssignmentConstraint,cttest);
          V_CC = ct;
          } 
        else { AssignmentConstraint * ct = GC_OBJECT(AssignmentConstraint,((AssignmentConstraint *) palm_makePalmUnIntConstraint_class(palm._AssignmentConstraint,v,x)));
            ((*(CLREAD(PalmIntDomain,v->bucket,instantiationConstraints)))[(x-CLREAD(PalmIntDomain,v->bucket,offset))]=_oid_(ct));
            ((*(GC_OBJECT(list,CLREAD(PalmIntDomain,v->bucket,negInstantiationConstraints))))[(x-CLREAD(PalmIntDomain,v->bucket,offset))]=GC_OID(_oid_(claire__I_equal_equal_PalmIntVar1(v,x))));
            V_CC = ct;
            } 
          } 
      else { AbstractConstraint * ct = GC_OBJECT(AbstractConstraint,OBJECT(AbstractConstraint,palm.UNKNOWN_ABS_CT->value));
          if (v->enumerationConstraints == (NULL))
           { (v->enumerationConstraints = palm_makeConstraintTable_type(Kernel._integer));
            (v->negEnumerationConstraints = palm_makeConstraintTable_type(Kernel._integer));
            } 
          else ct= OBJECT(AssignmentConstraint,nth_table1(v->enumerationConstraints,x));
            if (_oid_(ct) != palm.UNKNOWN_ABS_CT->value)
           V_CC = ct;
          else { ct= GC_OBJECT(PalmUnIntConstraint,palm_makePalmUnIntConstraint_class(palm._AssignmentConstraint,v,x));
              nth_equal_table1(v->enumerationConstraints,x,_oid_(ct));
              nth_equal_table1(v->negEnumerationConstraints,x,GC_OID(_oid_(claire__I_equal_equal_PalmIntVar1(v,x))));
              V_CC = ct;
              } 
            } 
        Result= (AssignmentConstraint *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 7 : Negating constraints                                  *
// ********************************************************************
// negate may return unknown if the constraint is already subsumed
/* The c++ function for: negate(ct:choco/AbstractConstraint) [] */
OID  palm_negate_AbstractConstraint_palm(AbstractConstraint *ct)
{ close_exception(((general_error *) (*Core._general_error)(_string_("PALM error : unable to negate constraint ~S (~S) during search (palmapi.cl)"),
    _oid_(list::alloc(2,_oid_(ct),_oid_(ct->isa))))));
  return (CNULL);} 


/* The c++ function for: negate(ct:AssignmentConstraint) [] */
OID  palm_negate_AssignmentConstraint_palm(AssignmentConstraint *ct)
{ { OID Result = 0;
    { PalmIntVar * v = ((PalmIntVar *) ct->v1);
      if (v->bucket != (NULL))
       Result = (*(CLREAD(PalmIntDomain,v->bucket,negInstantiationConstraints)))[(ct->cste-CLREAD(PalmIntDomain,CLREAD(PalmIntVar,ct->v1,bucket),offset))];
      else Result = nth_table1(v->negEnumerationConstraints,ct->cste);
        } 
    return (Result);} 
  } 


/* The c++ function for: choco/opposite(ct:AssignmentConstraint) [] */
OID  choco_opposite_AssignmentConstraint(AssignmentConstraint *ct)
{ GC_BIND;
  { OID Result = 0;
    { OID  opptest = GC_OID((*palm.negate)(_oid_(ct)));
      if (opptest != CNULL)
       { AbstractConstraint * opp = OBJECT(AbstractConstraint,opptest);
        Result = _oid_(opp);
        } 
      else Result = _oid_(claire__I_equal_equal_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) ct->v1)),ct->cste));
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: negate(ct:PalmLessOrEqualxc) [] */
OID  palm_negate_PalmLessOrEqualxc_palm(PalmLessOrEqualxc *ct)
{ return (_oid_(choco_opposite_PalmLessOrEqualxc(ct)));} 


/* The c++ function for: negate(ct:PalmGreaterOrEqualxc) [] */
OID  palm_negate_PalmGreaterOrEqualxc_palm(PalmGreaterOrEqualxc *ct)
{ return (_oid_(choco_opposite_PalmGreaterOrEqualxc(ct)));} 


/* The c++ function for: choco/opposite(c:PalmGreaterOrEqualxc) [] */
PalmLessOrEqualxc * choco_opposite_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ GC_BIND;
  { PalmLessOrEqualxc *Result ;
    Result = ((PalmLessOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmLessOrEqualxc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),(c->cste-1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmLessOrEqualxc) [] */
PalmGreaterOrEqualxc * choco_opposite_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ GC_BIND;
  { PalmGreaterOrEqualxc *Result ;
    Result = ((PalmGreaterOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmGreaterOrEqualxc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),(c->cste+1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmEqualxc) [] */
PalmNotEqualxc * choco_opposite_PalmEqualxc(PalmEqualxc *c)
{ GC_BIND;
  { PalmNotEqualxc *Result ;
    Result = ((PalmNotEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmNotEqualxc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),c->cste));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmNotEqualxc) [] */
PalmEqualxc * choco_opposite_PalmNotEqualxc(PalmNotEqualxc *c)
{ GC_BIND;
  { PalmEqualxc *Result ;
    Result = ((PalmEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmEqualxc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),c->cste));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmEqualxyc) [] */
PalmNotEqualxyc * choco_opposite_PalmEqualxyc(PalmEqualxyc *c)
{ GC_BIND;
  { PalmNotEqualxyc *Result ;
    Result = ((PalmNotEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmNotEqualxyc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),c->cste));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmNotEqualxyc) [] */
PalmEqualxyc * choco_opposite_PalmNotEqualxyc(PalmNotEqualxyc *c)
{ GC_BIND;
  { PalmEqualxyc *Result ;
    Result = ((PalmEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmEqualxyc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),c->cste));
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the constraint LessOrEqual disappears
// variable 1 in (x >= y + c) becomes variable 2 in not(x >= y + c) rewritten (y >= x - c + 1)
/* The c++ function for: choco/opposite(c:PalmGreaterOrEqualxyc) [] */
PalmGreaterOrEqualxyc * choco_opposite_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ GC_BIND;
  { PalmGreaterOrEqualxyc *Result ;
    Result = ((PalmGreaterOrEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),((-c->cste)+1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/computeVarIdxInOpposite(c:PalmGreaterOrEqualxyc,i:integer) [] */
int  choco_computeVarIdxInOpposite_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int i)
{ { int Result = 0;
    Result = ((i == 1) ?
      2 :
      1 );
    return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmGuard) [] */
PalmConjunction * choco_opposite_PalmGuard(PalmGuard *c)
{ GC_BIND;
  { PalmConjunction *Result ;
    { PalmConjunction * d;
      { { PalmConjunction * _CL_obj = ((PalmConjunction *) GC_OBJECT(PalmConjunction,new_object_class(palm._PalmConjunction)));
          (_CL_obj->const1 = c->const1);
          update_property(choco.const2,
            _CL_obj,
            7,
            Kernel._object,
            GC_OID((*choco.opposite)(GC_OID(_oid_(c->const2)))));
          d = _CL_obj;
          } 
        GC_OBJECT(PalmConjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      palm_initHook_PalmConjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmConjunction) [] */
PalmDisjunction * choco_opposite_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { PalmDisjunction *Result ;
    { PalmDisjunction * d;
      { { PalmDisjunction * _CL_obj = ((PalmDisjunction *) GC_OBJECT(PalmDisjunction,new_object_class(palm._PalmDisjunction)));
          update_property(choco.const1,
            _CL_obj,
            6,
            Kernel._object,
            GC_OID((*choco.opposite)(GC_OID(_oid_(c->const1)))));
          update_property(choco.const2,
            _CL_obj,
            7,
            Kernel._object,
            GC_OID((*choco.opposite)(GC_OID(_oid_(c->const2)))));
          d = _CL_obj;
          } 
        GC_OBJECT(PalmDisjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      palm_initHook_PalmDisjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmDisjunction) [] */
PalmConjunction * choco_opposite_PalmDisjunction(PalmDisjunction *c)
{ GC_BIND;
  { PalmConjunction *Result ;
    { PalmConjunction * d;
      { { PalmConjunction * _CL_obj = ((PalmConjunction *) GC_OBJECT(PalmConjunction,new_object_class(palm._PalmConjunction)));
          update_property(choco.const1,
            _CL_obj,
            6,
            Kernel._object,
            GC_OID((*choco.opposite)(GC_OID(_oid_(c->const1)))));
          update_property(choco.const2,
            _CL_obj,
            7,
            Kernel._object,
            GC_OID((*choco.opposite)(GC_OID(_oid_(c->const2)))));
          d = _CL_obj;
          } 
        GC_OBJECT(PalmConjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      palm_initHook_PalmConjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9903 <ebo>, <fxj>
// v1.02: fix the range
/* The c++ function for: choco/opposite(c:PalmLargeDisjunction) [] */
PalmLargeConjunction * choco_opposite_PalmLargeDisjunction(PalmLargeDisjunction *c)
{ GC_BIND;
  { PalmLargeConjunction *Result ;
    { PalmLargeConjunction * d;
      { { PalmLargeConjunction * _CL_obj = ((PalmLargeConjunction *) GC_OBJECT(PalmLargeConjunction,new_object_class(palm._PalmLargeConjunction)));
          { LargeCompositeConstraint * g0781; 
            list * g0782;
            g0781 = _CL_obj;
            { bag *v_list; OID v_val;
              OID subc,CLcount;
              v_list = GC_OBJECT(list,c->lconst);
               g0782 = v_list->clone(choco._AbstractConstraint);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { subc = (*(v_list))[CLcount];
                v_val = (*choco.opposite)(subc);
                
                (*((list *) g0782))[CLcount] = v_val;} 
              } 
            (g0781->lconst = g0782);} 
          d = _CL_obj;
          } 
        GC_OBJECT(PalmLargeConjunction,d);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(d);
      palm_initHook_PalmLargeConjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmLargeConjunction) [] */
PalmLargeDisjunction * choco_opposite_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  { PalmLargeDisjunction *Result ;
    { PalmLargeDisjunction * d;
      { { PalmLargeDisjunction * _CL_obj = ((PalmLargeDisjunction *) GC_OBJECT(PalmLargeDisjunction,new_object_class(palm._PalmLargeDisjunction)));
          { LargeCompositeConstraint * g0783; 
            list * g0784;
            g0783 = _CL_obj;
            { bag *v_list; OID v_val;
              OID subc,CLcount;
              v_list = GC_OBJECT(list,c->lconst);
               g0784 = v_list->clone(choco._AbstractConstraint);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { subc = (*(v_list))[CLcount];
                v_val = (*choco.opposite)(subc);
                
                (*((list *) g0784))[CLcount] = v_val;} 
              } 
            (g0783->lconst = g0784);} 
          (_CL_obj->needToAwake = make_list_integer2(c->nbConst,Kernel._boolean,Kernel.cfalse));
          d = _CL_obj;
          } 
        GC_OBJECT(PalmLargeDisjunction,d);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(d);
      palm_initHook_PalmLargeDisjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/opposite(c:PalmEquiv) [] */
PalmCardinality * choco_opposite_PalmEquiv(PalmEquiv *c)
{ GC_BIND;
  { PalmCardinality *Result ;
    Result = palm_e_dashcard_list1(list::alloc(U_type(choco._IntConstraint,choco._CompositeConstraint),2,GC_OID(_oid_(c->const1)),GC_OID(_oid_(c->const2))),GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(1)));
    GC_UNBIND; return (Result);} 
  } 


// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: choco/opposite(c:PalmLinComb) [] */
PalmLinComb * choco_opposite_PalmLinComb(PalmLinComb *c)
{ if (c->op == 1) 
  { { PalmLinComb *Result ;
      { list * g0794UU;
        { list * i_bag = list::empty(Kernel._integer);
          { int  g0785 = 1;
            OID * g0786 = GC_ARRAY(c->coefs);
            int  g0787 = g0786[0];
            { OID gc_local;
              while ((g0785 <= g0787))
              { // HOHO, GC_LOOP not needed !
                { int  i = ((OID *) g0786)[g0785];
                  i_bag->addFast(i);
                  ++g0785;
                  } 
                } 
              } 
            } 
          g0794UU = GC_OBJECT(list,i_bag);
          } 
        Result = palm_makePalmLinComb_list(g0794UU,GC_OBJECT(list,c->vars),c->cste,3);
        } 
      return (Result);} 
     } 
  else{ if (c->op == 2) 
    { { PalmLinComb *Result ;
        { list * g0795UU;
          { list * i_bag = list::empty(Kernel._integer);
            { int  g0788 = 1;
              OID * g0789 = GC_ARRAY(c->coefs);
              int  g0790 = g0789[0];
              { OID gc_local;
                while ((g0788 <= g0790))
                { // HOHO, GC_LOOP not needed !
                  { int  i = ((OID *) g0789)[g0788];
                    i_bag->addFast((-i));
                    ++g0788;
                    } 
                  } 
                } 
              } 
            g0795UU = GC_OBJECT(list,i_bag);
            } 
          Result = palm_makePalmLinComb_list(g0795UU,GC_OBJECT(list,c->vars),(-(c->cste+1)),2);
          } 
        return (Result);} 
       } 
    else{ GC_BIND;
      ;{ PalmLinComb *Result ;
        { list * g0796UU;
          { list * i_bag = list::empty(Kernel._integer);
            { int  g0791 = 1;
              OID * g0792 = GC_ARRAY(c->coefs);
              int  g0793 = g0792[0];
              { OID gc_local;
                while ((g0791 <= g0793))
                { // HOHO, GC_LOOP not needed !
                  { int  i = ((OID *) g0792)[g0791];
                    i_bag->addFast(i);
                    ++g0791;
                    } 
                  } 
                } 
              } 
            g0796UU = GC_OBJECT(list,i_bag);
            } 
          Result = palm_makePalmLinComb_list(g0796UU,GC_OBJECT(list,c->vars),c->cste,1);
          } 
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


// v1.011 coefs is an array
// *************************************************************
// *   Part 8 : arithmetic constraints                         *
// *************************************************************
// Simple unary operators
/* The c++ function for: >=(v:PalmIntVar,x:integer) [] */
PalmGreaterOrEqualxc * claire__sup_equal_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmGreaterOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmGreaterOrEqualxc,v,x)));} 


/* The c++ function for: >(v:PalmIntVar,x:integer) [] */
PalmGreaterOrEqualxc * claire__sup_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmGreaterOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmGreaterOrEqualxc,v,(x+1))));} 


/* The c++ function for: <=(v:PalmIntVar,x:integer) [] */
PalmLessOrEqualxc * claire__inf_equal_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmLessOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmLessOrEqualxc,v,x)));} 


/* The c++ function for: <(v:PalmIntVar,x:integer) [] */
PalmLessOrEqualxc * claire__inf_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmLessOrEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmLessOrEqualxc,v,(x-1))));} 


/* The c++ function for: ==(v:PalmIntVar,x:integer) [] */
PalmEqualxc * claire__equal_equal_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmEqualxc,v,x)));} 


/* The c++ function for: !==(v:PalmIntVar,x:integer) [] */
PalmNotEqualxc * claire__I_equal_equal_PalmIntVar1(PalmIntVar *v,int x)
{ return (((PalmNotEqualxc *) palm_makePalmUnIntConstraint_class(palm._PalmNotEqualxc,v,x)));} 


/* The c++ function for: >=(x:integer,v:PalmIntVar) [] */
PalmLessOrEqualxc * claire__sup_equal_integer6(int x,PalmIntVar *v)
{ return (claire__inf_equal_PalmIntVar1(v,x));} 


/* The c++ function for: >(x:integer,v:PalmIntVar) [] */
PalmLessOrEqualxc * claire__sup_integer6(int x,PalmIntVar *v)
{ return (claire__inf_PalmIntVar1(v,x));} 


/* The c++ function for: <=(x:integer,v:PalmIntVar) [] */
PalmGreaterOrEqualxc * claire__inf_equal_integer7(int x,PalmIntVar *v)
{ return (claire__sup_equal_PalmIntVar1(v,x));} 


/* The c++ function for: <(x:integer,v:PalmIntVar) [] */
PalmGreaterOrEqualxc * claire__inf_integer6(int x,PalmIntVar *v)
{ return (claire__sup_PalmIntVar1(v,x));} 


/* The c++ function for: ==(x:integer,v:PalmIntVar) [] */
PalmEqualxc * claire__equal_equal_integer8(int x,PalmIntVar *v)
{ return (claire__equal_equal_PalmIntVar1(v,x));} 


/* The c++ function for: !==(x:integer,v:PalmIntVar) [] */
PalmNotEqualxc * claire__I_equal_equal_integer5(int x,PalmIntVar *v)
{ return (claire__I_equal_equal_PalmIntVar1(v,x));} 


// Simple binary operators
/* The c++ function for: ==(u:PalmIntVar,v:PalmIntVar) [] */
PalmEqualxyc * claire__equal_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (((PalmEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmEqualxyc,u,v,0)));} 


/* The c++ function for: !==(u:PalmIntVar,v:PalmIntVar) [] */
PalmNotEqualxyc * claire__I_equal_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (((PalmNotEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmNotEqualxyc,u,v,0)));} 


/* The c++ function for: <=(u:PalmIntVar,v:PalmIntVar) [] */
PalmGreaterOrEqualxyc * claire__inf_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (claire__sup_equal_PalmIntVar2(v,u));} 


/* The c++ function for: >=(u:PalmIntVar,v:PalmIntVar) [] */
PalmGreaterOrEqualxyc * claire__sup_equal_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (((PalmGreaterOrEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,u,v,0)));} 


/* The c++ function for: >(u:PalmIntVar,v:PalmIntVar) [] */
PalmGreaterOrEqualxyc * claire__sup_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (((PalmGreaterOrEqualxyc *) palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,u,v,1)));} 


/* The c++ function for: <(u:PalmIntVar,v:PalmIntVar) [] */
PalmGreaterOrEqualxyc * claire__inf_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ return (claire__sup_PalmIntVar2(v,u));} 


// General linear combinations
//  we store linear expressions in temporary data structures: terms
//  PalmUnTerm: a temporary object representing +/-x +c
/* The c++ function for: self_print(t:PalmUnTerm) [] */
OID  claire_self_print_PalmUnTerm_palm(PalmUnTerm *t)
{ GC_BIND;
  if (t->sign1 != CTRUE)
   princ_string("-");
  print_any(GC_OID(_oid_(t->v1)));
  princ_string(" ");
  { OID Result = 0;
    if (t->cste < 0)
     { print_any(t->cste);
      princ_string("");
      } 
    else if (t->cste > 0)
     { princ_string("+");
      print_any(t->cste);
      princ_string("");
      } 
    else Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
  } 


//  PalmBinTerm: a temporary object representing +/-x +/-y +c
/* The c++ function for: self_print(t:PalmBinTerm) [] */
OID  claire_self_print_PalmBinTerm_palm(PalmBinTerm *t)
{ GC_BIND;
  if (t->sign1 == CTRUE)
   princ_string("+");
  else princ_string("-");
    print_any(GC_OID(_oid_(t->v1)));
  princ_string(" ");
  if (t->sign2 == CTRUE)
   princ_string("+");
  else princ_string("-");
    print_any(GC_OID(_oid_(t->v2)));
  princ_string("");
  { OID Result = 0;
    if (t->cste < 0)
     { print_any(t->cste);
      princ_string("");
      } 
    else if (t->cste > 0)
     { princ_string("+");
      print_any(t->cste);
      princ_string("");
      } 
    else Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
  } 


//  PalmLinTerm: a temporary object representing a linear term
/* The c++ function for: self_print(t:PalmLinTerm) [] */
OID  claire_self_print_PalmLinTerm_palm(PalmLinTerm *t)
{ { int  i = 1;
    int  g0797 = t->lcoeff->length;
    { OID gc_local;
      while ((i <= g0797))
      { // HOHO, GC_LOOP not needed !
        print_any((*(t->lcoeff))[i]);
        princ_string(".");
        print_any((*(t->lvars))[i]);
        princ_string(" + ");
        ++i;
        } 
      } 
    } 
  { OID Result = 0;
    if (t->cste < 0)
     { print_any(t->cste);
      princ_string("");
      } 
    else if (t->cste > 0)
     { princ_string("+");
      print_any(t->cste);
      princ_string("");
      } 
    else Result = Kernel.cfalse;
      return (Result);} 
  } 


// constructing a linear term from a list of variables
/* The c++ function for: e-scalar(l1:list[integer],l2:list[PalmIntVar]) [] */
PalmLinTerm * claire_e_dashscalar_list(list *l1,list *l2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      { PalmLinTerm * g0798; 
        list * g0799;
        g0798 = _CL_obj;
        { bag *v_list; OID v_val;
          OID i,CLcount;
          v_list = l1;
           g0799 = v_list->clone(Kernel._integer);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { i = (*(v_list))[CLcount];
            v_val = i;
            
            (*((list *) g0799))[CLcount] = v_val;} 
          } 
        (g0798->lcoeff = g0799);} 
      { PalmLinTerm * g0800; 
        list * g0801;
        g0800 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l2;
           g0801 = v_list->clone(palm._PalmIntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g0801))[CLcount] = v_val;} 
          } 
        (g0800->lvars = g0801);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


//v0.36:Constructing a linear term from a list of variables
/* The c++ function for: e-sumVars(l:list[PalmIntVar]) [] */
PalmLinTerm * palm_e_dashsumVars_list(list *l)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      (_CL_obj->lcoeff = make_list_integer2(l->length,Kernel._integer,1));
      { PalmLinTerm * g0802; 
        list * g0803;
        g0802 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g0803 = v_list->clone(palm._PalmIntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g0803))[CLcount] = v_val;} 
          } 
        (g0802->lvars = g0803);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


//v0.94: ensure a non-destructive behavior
// building linear terms
/* The c++ function for: *(a:integer,x:PalmIntVar) [] */
PalmLinTerm * claire__star_integer4(int a,PalmIntVar *x)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      (_CL_obj->lcoeff = list::alloc(Kernel._integer,1,a));
      (_CL_obj->lvars = list::alloc(palm._PalmIntVar,1,_oid_(x)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: *(x:PalmIntVar,a:integer) [] */
PalmLinTerm * claire__star_PalmIntVar(PalmIntVar *x,int a)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      (_CL_obj->lcoeff = list::alloc(Kernel._integer,1,a));
      (_CL_obj->lvars = list::alloc(palm._PalmIntVar,1,_oid_(x)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Addition Operator
/* The c++ function for: +(u:PalmIntVar,c:integer) [] */
PalmUnTerm * claire__plus_PalmIntVar1(PalmIntVar *u,int c)
{ GC_BIND;
  { PalmUnTerm *Result ;
    { PalmUnTerm * _CL_obj = ((PalmUnTerm *) GC_OBJECT(PalmUnTerm,new_object_class(palm._PalmUnTerm)));
      (_CL_obj->v1 = u);
      (_CL_obj->cste = c);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(c:integer,u:PalmIntVar) [] */
PalmUnTerm * claire__plus_integer6(int c,PalmIntVar *u)
{ return (claire__plus_PalmIntVar1(u,c));} 


/* The c++ function for: +(u:PalmIntVar,v:PalmIntVar) [] */
PalmBinTerm * claire__plus_PalmIntVar2(PalmIntVar *u,PalmIntVar *v)
{ GC_BIND;
  { PalmBinTerm *Result ;
    { PalmBinTerm * _CL_obj = ((PalmBinTerm *) GC_OBJECT(PalmBinTerm,new_object_class(palm._PalmBinTerm)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:PalmUnTerm,c:integer) [] */
PalmUnTerm * claire__plus_PalmUnTerm1(PalmUnTerm *t,int c)
{ (t->cste = (t->cste+c));
  return (t);} 


/* The c++ function for: +(c:integer,t:PalmUnTerm) [] */
PalmUnTerm * claire__plus_integer7(int c,PalmUnTerm *t)
{ return (claire__plus_PalmUnTerm1(t,c));} 


/* The c++ function for: +(t:PalmUnTerm,x:PalmIntVar) [] */
PalmBinTerm * claire__plus_PalmUnTerm2(PalmUnTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmBinTerm *Result ;
    { PalmBinTerm * _CL_obj = ((PalmBinTerm *) GC_OBJECT(PalmBinTerm,new_object_class(palm._PalmBinTerm)));
      (_CL_obj->v1 = t->v1);
      (_CL_obj->sign1 = t->sign1);
      (_CL_obj->v2 = x);
      (_CL_obj->cste = t->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:PalmIntVar,t:PalmUnTerm) [] */
PalmBinTerm * claire__plus_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t)
{ return (claire__plus_PalmUnTerm2(t,x));} 


/* The c++ function for: +(t1:PalmUnTerm,t2:PalmUnTerm) [] */
PalmBinTerm * claire__plus_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    { PalmBinTerm * _CL_obj = ((PalmBinTerm *) GC_OBJECT(PalmBinTerm,new_object_class(palm._PalmBinTerm)));
      (_CL_obj->v1 = t1->v1);
      (_CL_obj->sign1 = t1->sign1);
      (_CL_obj->v2 = t2->v1);
      (_CL_obj->sign2 = t2->sign1);
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:PalmBinTerm,c:integer) [] */
PalmBinTerm * claire__plus_PalmBinTerm1(PalmBinTerm *t,int c)
{ (t->cste = (t->cste+c));
  return (t);} 


/* The c++ function for: +(c:integer,t:PalmBinTerm) [] */
PalmBinTerm * claire__plus_integer8(int c,PalmBinTerm *t)
{ return (claire__plus_PalmBinTerm1(t,c));} 


/* The c++ function for: +(t:PalmBinTerm,x:PalmIntVar) [] */
PalmLinTerm * claire__plus_PalmBinTerm2(PalmBinTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      { PalmLinTerm * g0804; 
        list * g0805;
        g0804 = _CL_obj;
        { OID v_bag;
          GC_ANY(g0805= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0805)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0805)->addFast(v_bag);
          ((list *) g0805)->addFast(1);} 
        (g0804->lcoeff = g0805);} 
      (_CL_obj->lvars = list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)));
      (_CL_obj->cste = t->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:PalmIntVar,t:PalmBinTerm) [] */
PalmLinTerm * claire__plus_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t)
{ return (claire__plus_PalmBinTerm2(t,x));} 


/* The c++ function for: +(t1:PalmBinTerm,t2:PalmUnTerm) [] */
PalmLinTerm * claire__plus_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      { PalmLinTerm * g0806; 
        list * g0807;
        g0806 = _CL_obj;
        { OID v_bag;
          GC_ANY(g0807= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0807)->addFast(v_bag);
          if (t1->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0807)->addFast(v_bag);
          if (t2->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0807)->addFast(v_bag);} 
        (g0806->lcoeff = g0807);} 
      (_CL_obj->lvars = list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:PalmUnTerm,t1:PalmBinTerm) [] */
PalmLinTerm * claire__plus_PalmUnTerm4(PalmUnTerm *t2,PalmBinTerm *t1)
{ return (claire__plus_PalmBinTerm3(t1,t2));} 


/* The c++ function for: +(t1:PalmBinTerm,t2:PalmBinTerm) [] */
PalmLinTerm * claire__plus_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      { PalmLinTerm * g0808; 
        list * g0809;
        g0808 = _CL_obj;
        { OID v_bag;
          GC_ANY(g0809= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0809)->addFast(v_bag);
          if (t1->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0809)->addFast(v_bag);
          if (t2->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0809)->addFast(v_bag);
          if (t2->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0809)->addFast(v_bag);} 
        (g0808->lcoeff = g0809);} 
      (_CL_obj->lvars = list::alloc(palm._PalmIntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:PalmLinTerm,a:integer) [] */
PalmLinTerm * claire__plus_PalmLinTerm1(PalmLinTerm *t,int a)
{ (t->cste = (t->cste+a));
  return (t);} 


/* The c++ function for: +(a:integer,t:PalmLinTerm) [] */
PalmLinTerm * claire__plus_integer9(int a,PalmLinTerm *t)
{ return (claire__plus_PalmLinTerm1(t,a));} 


/* The c++ function for: +(t:PalmLinTerm,x:PalmIntVar) [] */
PalmLinTerm * claire__plus_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x)
{ GC_BIND;
  GC_OBJECT(list,t->lcoeff)->addFast(1);
  GC_OBJECT(list,t->lvars)->addFast(_oid_(x));
  { PalmLinTerm *Result ;
    Result = t;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:PalmIntVar,t:PalmLinTerm) [] */
PalmLinTerm * claire__plus_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t)
{ return (claire__plus_PalmLinTerm2(t,x));} 


/* The c++ function for: +(t1:PalmLinTerm,t2:PalmUnTerm) [] */
PalmLinTerm * claire__plus_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { OID  g0810UU;
    if (t2->sign1 == CTRUE)
     g0810UU = 1;
    else g0810UU = -1;
      GC_OBJECT(list,t1->lcoeff)->addFast(g0810UU);
    } 
  GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)));
  (t1->cste = (t1->cste+t2->cste));
  { PalmLinTerm *Result ;
    Result = t1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:PalmUnTerm,t1:PalmLinTerm) [] */
PalmLinTerm * claire__plus_PalmUnTerm5(PalmUnTerm *t2,PalmLinTerm *t1)
{ return (claire__plus_PalmLinTerm3(t1,t2));} 


/* The c++ function for: +(t1:PalmLinTerm,t2:PalmBinTerm) [] */
PalmLinTerm * claire__plus_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm * g0811; 
    list * g0812;
    g0811 = t1;
    { list * g0813UU;
      { OID v_bag;
        GC_ANY(g0813UU= list::empty(Kernel._integer));
        if (t2->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0813UU)->addFast(v_bag);
        if (t2->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0813UU)->addFast(v_bag);} 
      g0812 = append_list(GC_OBJECT(list,t1->lcoeff),g0813UU);
      } 
    (g0811->lcoeff = g0812);} 
  (t1->lvars = append_list(GC_OBJECT(list,t1->lvars),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2)))));
  (t1->cste = (t1->cste+t2->cste));
  { PalmLinTerm *Result ;
    Result = t1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:PalmBinTerm,t1:PalmLinTerm) [] */
PalmLinTerm * claire__plus_PalmBinTerm5(PalmBinTerm *t2,PalmLinTerm *t1)
{ return (claire__plus_PalmLinTerm4(t1,t2));} 


/* The c++ function for: +(t1:PalmLinTerm,t2:PalmLinTerm) [] */
PalmLinTerm * claire__plus_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    { PalmLinTerm * _CL_obj = ((PalmLinTerm *) GC_OBJECT(PalmLinTerm,new_object_class(palm._PalmLinTerm)));
      (_CL_obj->lcoeff = append_list(GC_OBJECT(list,t1->lcoeff),GC_OBJECT(list,t2->lcoeff)));
      (_CL_obj->lvars = append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars)));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Opposite Operator
/* The c++ function for: -(x:PalmIntVar) [] */
PalmUnTerm * claire__dash_PalmIntVar1(PalmIntVar *x)
{ GC_BIND;
  { PalmUnTerm *Result ;
    { PalmUnTerm * _CL_obj = ((PalmUnTerm *) GC_OBJECT(PalmUnTerm,new_object_class(palm._PalmUnTerm)));
      (_CL_obj->v1 = x);
      (_CL_obj->sign1 = CFALSE);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t:PalmUnTerm) [] */
PalmUnTerm * claire__dash_PalmUnTerm1(PalmUnTerm *t)
{ (t->sign1 = not_any(_oid_(t->sign1)));
  (t->cste = (-t->cste));
  return (t);} 


/* The c++ function for: -(t:PalmBinTerm) [] */
PalmBinTerm * claire__dash_PalmBinTerm1(PalmBinTerm *t)
{ (t->sign1 = not_any(_oid_(t->sign1)));
  (t->sign2 = not_any(_oid_(t->sign2)));
  (t->cste = (-t->cste));
  return (t);} 


/* The c++ function for: -(t:PalmLinTerm) [] */
PalmLinTerm * claire__dash_PalmLinTerm1(PalmLinTerm *t)
{ GC_BIND;
  { PalmLinTerm * g0814; 
    list * g0815;
    g0814 = t;
    { bag *v_list; OID v_val;
      OID a,CLcount;
      v_list = GC_OBJECT(list,t->lcoeff);
       g0815 = v_list->clone(Kernel._integer);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { a = (*(v_list))[CLcount];
        v_val = (-a);
        
        (*((list *) g0815))[CLcount] = v_val;} 
      } 
    (g0814->lcoeff = g0815);} 
  (t->cste = (-t->cste));
  { PalmLinTerm *Result ;
    Result = t;
    GC_UNBIND; return (Result);} 
  } 


// ----- Substraction Operator
// many lines for
// [-(t1:(PalmTempTerm U PalmIntVar U integer), t2:(PalmTempTerm U PalmIntVar)) : PalmTempTerm => t1 + -(t2)]
// because of a dump compiler
// v0.18 further expand for static typing
/* The c++ function for: -(t1:PalmIntVar,t2:integer) [] */
PalmUnTerm * claire__dash_PalmIntVar2(PalmIntVar *t1,int t2)
{ return (claire__plus_PalmIntVar1(t1,(-t2)));} 


/* The c++ function for: -(t1:PalmUnTerm,t2:integer) [] */
PalmUnTerm * claire__dash_PalmUnTerm2(PalmUnTerm *t1,int t2)
{ return (claire__plus_PalmUnTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:PalmBinTerm,t2:integer) [] */
PalmBinTerm * claire__dash_PalmBinTerm2(PalmBinTerm *t1,int t2)
{ return (claire__plus_PalmBinTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:PalmLinTerm,t2:integer) [] */
PalmLinTerm * claire__dash_PalmLinTerm2(PalmLinTerm *t1,int t2)
{ return (claire__plus_PalmLinTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:integer,t2:PalmIntVar) [] */
PalmUnTerm * claire__dash_integer8(int t1,PalmIntVar *t2)
{ GC_BIND;
  { PalmUnTerm *Result ;
    Result = claire__plus_PalmUnTerm1(GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmIntVar,t2:PalmIntVar) [] */
PalmBinTerm * claire__dash_PalmIntVar3(PalmIntVar *t1,PalmIntVar *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    Result = claire__plus_PalmUnTerm2(GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmUnTerm,t2:PalmIntVar) [] */
PalmBinTerm * claire__dash_PalmUnTerm3(PalmUnTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    Result = claire__plus_PalmUnTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmBinTerm,t2:PalmIntVar) [] */
PalmLinTerm * claire__dash_PalmBinTerm3(PalmBinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmBinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmLinTerm,t2:PalmIntVar) [] */
PalmLinTerm * claire__dash_PalmLinTerm3(PalmLinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:PalmUnTerm) [] */
PalmUnTerm * claire__dash_integer9(int t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmUnTerm *Result ;
    Result = claire__plus_PalmUnTerm1(GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmIntVar,t2:PalmUnTerm) [] */
PalmBinTerm * claire__dash_PalmIntVar4(PalmIntVar *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    Result = claire__plus_PalmUnTerm2(GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmUnTerm,t2:PalmUnTerm) [] */
PalmBinTerm * claire__dash_PalmUnTerm4(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    Result = claire__plus_PalmUnTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmBinTerm,t2:PalmUnTerm) [] */
PalmLinTerm * claire__dash_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmBinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmLinTerm,t2:PalmUnTerm) [] */
PalmLinTerm * claire__dash_PalmLinTerm4(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:PalmBinTerm) [] */
PalmBinTerm * claire__dash_integer10(int t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmBinTerm *Result ;
    Result = claire__plus_PalmBinTerm1(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmIntVar,t2:PalmBinTerm) [] */
PalmLinTerm * claire__dash_PalmIntVar5(PalmIntVar *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmBinTerm2(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmUnTerm,t2:PalmBinTerm) [] */
PalmLinTerm * claire__dash_PalmUnTerm5(PalmUnTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmBinTerm3(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmBinTerm,t2:PalmBinTerm) [] */
PalmLinTerm * claire__dash_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmBinTerm4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmLinTerm,t2:PalmBinTerm) [] */
PalmLinTerm * claire__dash_PalmLinTerm5(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:PalmLinTerm) [] */
PalmLinTerm * claire__dash_integer11(int t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm1(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmIntVar,t2:PalmLinTerm) [] */
PalmLinTerm * claire__dash_PalmIntVar6(PalmIntVar *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm2(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmUnTerm,t2:PalmLinTerm) [] */
PalmLinTerm * claire__dash_PalmUnTerm6(PalmUnTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm3(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmBinTerm,t2:PalmLinTerm) [] */
PalmLinTerm * claire__dash_PalmBinTerm6(PalmBinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm4(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:PalmLinTerm,t2:PalmLinTerm) [] */
PalmLinTerm * claire__dash_PalmLinTerm6(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinTerm *Result ;
    Result = claire__plus_PalmLinTerm5(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


// ------- Using terms within comparisons
// v0.18 rewrite for static typing
// [>=(a:integer, t:PalmTempTerm) : AbstractConstraint => -(t) >= -(a)]
// [>=(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => -(t) >= -(x)]
// [>=(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => -(t2) >= -(t1)]
/* The c++ function for: >=(a:integer,t:PalmUnTerm) [] */
AbstractConstraint * claire__sup_equal_integer7(int a,PalmUnTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm3(GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(a:integer,t:PalmBinTerm) [] */
AbstractConstraint * claire__sup_equal_integer8(int a,PalmBinTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm2(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(a:integer,t:PalmLinTerm) [] */
AbstractConstraint * claire__sup_equal_integer9(int a,PalmLinTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm1(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:PalmIntVar,t:PalmUnTerm) [] */
AbstractConstraint * claire__sup_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm5(GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t)),GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:PalmIntVar,t:PalmBinTerm) [] */
AbstractConstraint * claire__sup_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm4(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t)),GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:PalmIntVar,t:PalmLinTerm) [] */
AbstractConstraint * claire__sup_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm3(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t)),GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmUnTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm4(GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t2)),GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmUnTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm3(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmBinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm4(GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm1(t2)),GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:PalmUnTerm,c:integer) [] */
PalmUnIntConstraint * claire__sup_equal_PalmUnTerm3(PalmUnTerm *t,int c)
{ if (t->sign1 == CTRUE) 
  { { PalmUnIntConstraint *Result ;
      Result = palm_makePalmUnIntConstraint_class(palm._PalmGreaterOrEqualxc,GC_OBJECT(PalmIntVar,t->v1),(c-t->cste));
      return (Result);} 
     } 
  else{ GC_BIND;
    { PalmUnIntConstraint *Result ;
      Result = palm_makePalmUnIntConstraint_class(palm._PalmLessOrEqualxc,GC_OBJECT(PalmIntVar,t->v1),(t->cste-c));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:PalmUnTerm,x:PalmIntVar) [] */
AbstractConstraint * claire__sup_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm5(GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(x)),GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmUnTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2)
{ if (t1->sign1 == t2->sign1) 
  { { AbstractConstraint *Result ;
      Result = ((t1->sign1 == CTRUE) ?
        palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,GC_OBJECT(PalmIntVar,t1->v1),GC_OBJECT(PalmIntVar,t2->v1),(t2->cste-t1->cste)) :
        palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,GC_OBJECT(PalmIntVar,t2->v1),GC_OBJECT(PalmIntVar,t1->v1),(t2->cste-t1->cste)) );
      return (Result);} 
     } 
  else{ GC_BIND;
    { AbstractConstraint *Result ;
      { list * g0816UU;
        { OID v_bag;
          GC_ANY(g0816UU= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0816UU)->addFast(v_bag);
          ((list *) g0816UU)->addFast((-((t2->sign1 == CTRUE) ?
            1 :
            -1 )));} 
        Result = palm_makePalmLinComb_list(g0816UU,list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),2);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:PalmBinTerm,c:integer) [] */
AbstractConstraint * claire__sup_equal_PalmBinTerm2(PalmBinTerm *t,int c)
{ if (t->sign1 != t->sign2) 
  { { AbstractConstraint *Result ;
      Result = ((t->sign1 == CTRUE) ?
        palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,GC_OBJECT(PalmIntVar,t->v1),GC_OBJECT(PalmIntVar,t->v2),(c-t->cste)) :
        palm_makePalmBinIntConstraint_class(palm._PalmGreaterOrEqualxyc,GC_OBJECT(PalmIntVar,t->v2),GC_OBJECT(PalmIntVar,t->v1),(c+t->cste)) );
      return (Result);} 
     } 
  else{ GC_BIND;
    { AbstractConstraint *Result ;
      { list * g0817UU;
        { OID v_bag;
          GC_ANY(g0817UU= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0817UU)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0817UU)->addFast(v_bag);} 
        Result = palm_makePalmLinComb_list(g0817UU,list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t->v1)),GC_OID(_oid_(t->v2))),(t->cste-c),2);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:PalmBinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__sup_equal_PalmBinTerm3(PalmBinTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0818UU;
      { OID v_bag;
        GC_ANY(g0818UU= list::empty(Kernel._integer));
        if (t->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0818UU)->addFast(v_bag);
        if (t->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0818UU)->addFast(v_bag);
        ((list *) g0818UU)->addFast(-1);} 
      Result = palm_makePalmLinComb_list(g0818UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)),t->cste,2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmBinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__sup_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0819UU;
      { OID v_bag;
        GC_ANY(g0819UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0819UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0819UU)->addFast(v_bag);
        ((list *) g0819UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0819UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmBinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__sup_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0820UU;
      { OID v_bag;
        GC_ANY(g0820UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0820UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0820UU)->addFast(v_bag);
        ((list *) g0820UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g0820UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0820UU,list::alloc(palm._PalmIntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:PalmLinTerm,a:integer) [] */
PalmLinComb * claire__sup_equal_PalmLinTerm1(PalmLinTerm *t,int a)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,t->lcoeff),GC_OBJECT(list,t->lvars),(t->cste-a),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:PalmLinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__sup_equal_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(_oid_(x))),t->cste,2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:PalmLinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__sup_equal_PalmLinTerm3(PalmLinTerm *t,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t->cste-t2->cste),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:PalmLinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__sup_equal_PalmLinTerm4(PalmLinTerm *t,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t->lvars),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t->cste-t2->cste),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:PalmLinTerm,t2:PalmLinTerm) [] */
PalmLinComb * claire__sup_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0821UU;
      { { list * g0822UU;
          { { bag *v_list; OID v_val;
              OID a,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g0822UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { a = (*(v_list))[CLcount];
                v_val = (-a);
                
                (*((list *) g0822UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0822UU);} 
          g0821UU = append_list(GC_OBJECT(list,t1->lcoeff),g0822UU);
          } 
        GC_OBJECT(list,g0821UU);} 
      Result = palm_makePalmLinComb_list(g0821UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


// All comparisons can be defined from >=
// rewrite t1 > t2 as t1 >= t2 + 1
// v0.18 expanded for static typing
// [>(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint => (t1 >= t2 + 1)]
// [>(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => (t1 >= t2 + 1)]
// [>(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => (t1 >= t2 + 1)]
/* The c++ function for: >(t1:integer,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_integer7(int t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_integer7(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmUnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:integer,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_integer8(int t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_integer8(t1,GC_OBJECT(PalmBinTerm,claire__plus_PalmBinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:integer,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_integer9(int t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_integer9(t1,GC_OBJECT(PalmLinTerm,claire__plus_PalmLinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmIntVar,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmIntVar3(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmUnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmIntVar,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmIntVar4(t1,GC_OBJECT(PalmBinTerm,claire__plus_PalmBinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmIntVar,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmIntVar5(t1,GC_OBJECT(PalmLinTerm,claire__plus_PalmLinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmUnTerm,t2:integer) [] */
AbstractConstraint * claire__sup_PalmUnTerm1(PalmUnTerm *t1,int t2)
{ return (claire__sup_equal_PalmUnTerm3(t1,(t2+1)));} 


/* The c++ function for: >(t1:PalmBinTerm,t2:integer) [] */
AbstractConstraint * claire__sup_PalmBinTerm1(PalmBinTerm *t1,int t2)
{ return (claire__sup_equal_PalmBinTerm2(t1,(t2+1)));} 


/* The c++ function for: >(t1:PalmLinTerm,t2:integer) [] */
AbstractConstraint * claire__sup_PalmLinTerm1(PalmLinTerm *t1,int t2)
{ return (claire__sup_equal_PalmLinTerm1(t1,(t2+1)));} 


/* The c++ function for: >(t1:PalmUnTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__sup_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm5(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmIntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmBinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__sup_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm4(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmIntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmLinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__sup_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmIntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmUnTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm5(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmUnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmBinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm4(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmUnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmLinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__sup_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__plus_PalmUnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmUnTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm1(t1,GC_OBJECT(PalmBinTerm,claire__plus_PalmBinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmBinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm5(t1,GC_OBJECT(PalmBinTerm,claire__plus_PalmBinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmLinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__sup_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm4(t1,GC_OBJECT(PalmBinTerm,claire__plus_PalmBinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmUnTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm2(t1,GC_OBJECT(PalmLinTerm,claire__plus_PalmLinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmBinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmBinTerm1(t1,GC_OBJECT(PalmLinTerm,claire__plus_PalmLinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:PalmLinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__sup_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmLinTerm5(t1,GC_OBJECT(PalmLinTerm,claire__plus_PalmLinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


// rewrite t1 <= t2 as t2 >= t1
// v0.18 expanded for static typing
// [<=(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint -> t2 >= t1]
// [<=(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => t2 >= t1]
// [<=(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => t2 >= t1]
/* The c++ function for: <=(t1:integer,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_equal_integer8(int t1,PalmUnTerm *t2)
{ return (claire__sup_equal_PalmUnTerm3(t2,t1));} 


/* The c++ function for: <=(t1:integer,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_equal_integer9(int t1,PalmBinTerm *t2)
{ return (claire__sup_equal_PalmBinTerm2(t2,t1));} 


/* The c++ function for: <=(t1:integer,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_equal_integer10(int t1,PalmLinTerm *t2)
{ return (claire__sup_equal_PalmLinTerm1(t2,t1));} 


/* The c++ function for: <=(t1:PalmIntVar,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_equal_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__sup_equal_PalmUnTerm5(GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar1(t1)),GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <=(t1:PalmIntVar,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2)
{ return (claire__sup_equal_PalmBinTerm3(t2,t1));} 


/* The c++ function for: <=(t1:PalmIntVar,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2)
{ return (claire__sup_equal_PalmLinTerm2(t2,t1));} 


/* The c++ function for: <=(t1:PalmUnTerm,t2:integer) [] */
AbstractConstraint * claire__inf_equal_PalmUnTerm1(PalmUnTerm *t1,int t2)
{ return (claire__sup_equal_integer7(t2,t1));} 


/* The c++ function for: <=(t1:PalmBinTerm,t2:integer) [] */
AbstractConstraint * claire__inf_equal_PalmBinTerm1(PalmBinTerm *t1,int t2)
{ return (claire__sup_equal_integer8(t2,t1));} 


/* The c++ function for: <=(t1:PalmLinTerm,t2:integer) [] */
AbstractConstraint * claire__inf_equal_PalmLinTerm1(PalmLinTerm *t1,int t2)
{ return (claire__sup_equal_integer9(t2,t1));} 


/* The c++ function for: <=(t1:PalmUnTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_equal_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2)
{ return (claire__sup_equal_PalmIntVar3(t2,t1));} 


/* The c++ function for: <=(t1:PalmBinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_equal_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2)
{ return (claire__sup_equal_PalmIntVar4(t2,t1));} 


/* The c++ function for: <=(t1:PalmLinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_equal_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2)
{ return (claire__sup_equal_PalmIntVar5(t2,t1));} 


/* The c++ function for: <=(t1:PalmUnTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_equal_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2)
{ return (claire__sup_equal_PalmUnTerm5(t2,t1));} 


/* The c++ function for: <=(t1:PalmBinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_equal_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2)
{ return (claire__sup_equal_PalmUnTerm1(t2,t1));} 


/* The c++ function for: <=(t1:PalmLinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ return (claire__sup_equal_PalmUnTerm2(t2,t1));} 


/* The c++ function for: <=(t1:PalmUnTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2)
{ return (claire__sup_equal_PalmBinTerm4(t2,t1));} 


/* The c++ function for: <=(t1:PalmBinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2)
{ return (claire__sup_equal_PalmBinTerm5(t2,t1));} 


/* The c++ function for: <=(t1:PalmLinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ return (claire__sup_equal_PalmBinTerm1(t2,t1));} 


/* The c++ function for: <=(t1:PalmUnTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2)
{ return (claire__sup_equal_PalmLinTerm3(t2,t1));} 


/* The c++ function for: <=(t1:PalmBinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2)
{ return (claire__sup_equal_PalmLinTerm4(t2,t1));} 


/* The c++ function for: <=(t1:PalmLinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ return (claire__sup_equal_PalmLinTerm5(t2,t1));} 


// rewrite t1 < t2 as t1 <= t2 - 1
// v0.18 expanded for static typing
// [<(t1:PalmTempTerm, t2:PalmTempTerm) : AbstractConstraint => (t1 <= t2 - 1)]
// [<(t1:(PalmIntVar U integer), t2:PalmTempTerm) : AbstractConstraint => (t1 <= t2 - 1)]
// [<(t1:PalmTempTerm, t2:(PalmIntVar U integer)) : AbstractConstraint => (t1 <= t2 - 1)]
/* The c++ function for: <(t1:integer,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_integer7(int t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_integer8(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:integer,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_integer8(int t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_integer9(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:integer,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_integer9(int t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_integer10(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmIntVar,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_PalmIntVar3(PalmIntVar *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmIntVar3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmIntVar,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_PalmIntVar4(PalmIntVar *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmIntVar4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmIntVar,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_PalmIntVar5(PalmIntVar *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmIntVar5(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmUnTerm,t2:integer) [] */
AbstractConstraint * claire__inf_PalmUnTerm1(PalmUnTerm *t1,int t2)
{ return (claire__inf_equal_PalmUnTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:PalmBinTerm,t2:integer) [] */
AbstractConstraint * claire__inf_PalmBinTerm1(PalmBinTerm *t1,int t2)
{ return (claire__inf_equal_PalmBinTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:PalmLinTerm,t2:integer) [] */
AbstractConstraint * claire__inf_PalmLinTerm1(PalmLinTerm *t1,int t2)
{ return (claire__inf_equal_PalmLinTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:PalmUnTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_PalmUnTerm2(PalmUnTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmUnTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmBinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_PalmBinTerm2(PalmBinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmBinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmLinTerm,t2:PalmIntVar) [] */
AbstractConstraint * claire__inf_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmIntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmUnTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_PalmUnTerm3(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmUnTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmBinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_PalmBinTerm3(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmBinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmLinTerm,t2:PalmUnTerm) [] */
AbstractConstraint * claire__inf_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmLinTerm3(t1,GC_OBJECT(PalmUnTerm,claire__dash_PalmUnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmUnTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_PalmUnTerm4(PalmUnTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmUnTerm4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmBinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_PalmBinTerm4(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmBinTerm4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmLinTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__inf_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmLinTerm4(t1,GC_OBJECT(PalmBinTerm,claire__dash_PalmBinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmUnTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_PalmUnTerm5(PalmUnTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmUnTerm5(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmBinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_PalmBinTerm5(PalmBinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmBinTerm5(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:PalmLinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__inf_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { AbstractConstraint *Result ;
    Result = claire__inf_equal_PalmLinTerm5(t1,GC_OBJECT(PalmLinTerm,claire__dash_PalmLinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


// Equality
// v0.18 expanded for static typing
// [==(a:integer, t:PalmTempTerm) : AbstractConstraint => t == a]
// [==(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => t == x]
// [==(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => t2 == t1]
// [==(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint => t2 == t1]
/* The c++ function for: ==(a:integer,t:PalmUnTerm) [] */
AbstractConstraint * claire__equal_equal_integer9(int a,PalmUnTerm *t)
{ return (claire__equal_equal_PalmUnTerm3(t,a));} 


/* The c++ function for: ==(a:integer,t:PalmBinTerm) [] */
AbstractConstraint * claire__equal_equal_integer10(int a,PalmBinTerm *t)
{ return (claire__equal_equal_PalmBinTerm2(t,a));} 


/* The c++ function for: ==(a:integer,t:PalmLinTerm) [] */
AbstractConstraint * claire__equal_equal_integer11(int a,PalmLinTerm *t)
{ return (claire__equal_equal_PalmLinTerm1(t,a));} 


/* The c++ function for: ==(x:PalmIntVar,t:PalmUnTerm) [] */
AbstractConstraint * claire__equal_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t)
{ return (claire__equal_equal_PalmUnTerm4(t,x));} 


/* The c++ function for: ==(x:PalmIntVar,t:PalmBinTerm) [] */
AbstractConstraint * claire__equal_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t)
{ return (claire__equal_equal_PalmBinTerm3(t,x));} 


/* The c++ function for: ==(x:PalmIntVar,t:PalmLinTerm) [] */
AbstractConstraint * claire__equal_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t)
{ return (claire__equal_equal_PalmLinTerm2(t,x));} 


/* The c++ function for: ==(t1:PalmUnTerm,t2:PalmBinTerm) [] */
AbstractConstraint * claire__equal_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2)
{ return (claire__equal_equal_PalmBinTerm4(t2,t1));} 


/* The c++ function for: ==(t1:PalmUnTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__equal_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2)
{ return (claire__equal_equal_PalmLinTerm3(t2,t1));} 


/* The c++ function for: ==(t1:PalmBinTerm,t2:PalmLinTerm) [] */
AbstractConstraint * claire__equal_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2)
{ return (claire__equal_equal_PalmLinTerm4(t2,t1));} 


/* The c++ function for: ==(t:PalmUnTerm,c:integer) [] */
PalmUnIntConstraint * claire__equal_equal_PalmUnTerm3(PalmUnTerm *t,int c)
{ GC_BIND;
  { PalmUnIntConstraint *Result ;
    Result = palm_makePalmUnIntConstraint_class(palm._PalmEqualxc,GC_OBJECT(PalmIntVar,t->v1),(c-t->cste));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmUnTerm,x:PalmIntVar) [] */
AbstractConstraint * claire__equal_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x)
{ if (t->sign1 == CTRUE) 
  { { AbstractConstraint *Result ;
      Result = palm_makePalmBinIntConstraint_class(palm._PalmEqualxyc,x,GC_OBJECT(PalmIntVar,t->v1),t->cste);
      return (Result);} 
     } 
  else{ GC_BIND;
    { AbstractConstraint *Result ;
      Result = palm_makePalmLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t->v1)),_oid_(x)),(-t->cste),1);
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: ==(t1:PalmUnTerm,t2:PalmUnTerm) [] */
PalmBinIntConstraint * claire__equal_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmBinIntConstraint *Result ;
    Result = palm_makePalmBinIntConstraint_class(palm._PalmEqualxyc,GC_OBJECT(PalmIntVar,t1->v1),GC_OBJECT(PalmIntVar,t2->v1),(t2->cste-t1->cste));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmBinTerm,c:integer) [] */
AbstractConstraint * claire__equal_equal_PalmBinTerm2(PalmBinTerm *t,int c)
{ if (t->sign1 != t->sign2) 
  { { AbstractConstraint *Result ;
      Result = palm_makePalmBinIntConstraint_class(palm._PalmEqualxyc,GC_OBJECT(PalmIntVar,t->v1),GC_OBJECT(PalmIntVar,t->v2),((t->sign1 == CTRUE) ?
        (c-t->cste) :
        (t->cste-c) ));
      return (Result);} 
     } 
  else{ GC_BIND;
    { AbstractConstraint *Result ;
      { list * g0823UU;
        { OID v_bag;
          GC_ANY(g0823UU= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0823UU)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g0823UU)->addFast(v_bag);} 
        Result = palm_makePalmLinComb_list(g0823UU,list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t->v1)),GC_OID(_oid_(t->v2))),(t->cste-c),1);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: ==(t:PalmBinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__equal_equal_PalmBinTerm3(PalmBinTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0824UU;
      { OID v_bag;
        GC_ANY(g0824UU= list::empty(Kernel._integer));
        if (t->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0824UU)->addFast(v_bag);
        if (t->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0824UU)->addFast(v_bag);
        ((list *) g0824UU)->addFast(-1);} 
      Result = palm_makePalmLinComb_list(g0824UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)),t->cste,1);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:PalmBinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__equal_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0825UU;
      { OID v_bag;
        GC_ANY(g0825UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0825UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0825UU)->addFast(v_bag);
        ((list *) g0825UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0825UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:PalmBinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__equal_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0826UU;
      { OID v_bag;
        GC_ANY(g0826UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0826UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0826UU)->addFast(v_bag);
        ((list *) g0826UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g0826UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0826UU,list::alloc(palm._PalmIntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmLinTerm,c:integer) [] */
PalmLinComb * claire__equal_equal_PalmLinTerm1(PalmLinTerm *t,int c)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,t->lcoeff),GC_OBJECT(list,t->lvars),(t->cste-c),1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmLinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__equal_equal_PalmLinTerm2(PalmLinTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(_oid_(x))),t->cste,1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:PalmLinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__equal_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t1->cste-t2->cste),1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:PalmLinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__equal_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t1->cste-t2->cste),1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:PalmLinTerm,t2:PalmLinTerm) [] */
PalmLinComb * claire__equal_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0827UU;
      { { list * g0828UU;
          { { bag *v_list; OID v_val;
              OID a,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g0828UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { a = (*(v_list))[CLcount];
                v_val = (-a);
                
                (*((list *) g0828UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0828UU);} 
          g0827UU = append_list(GC_OBJECT(list,t1->lcoeff),g0828UU);
          } 
        GC_OBJECT(list,g0827UU);} 
      Result = palm_makePalmLinComb_list(g0827UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.18 expanded for static typing
// [!==(a:integer, t:PalmTempTerm) : AbstractConstraint => t !== a]
// [!==(x:PalmIntVar, t:PalmTempTerm) : AbstractConstraint => t !== x]
// [!==(t1:PalmUnTerm, t2:(PalmBinTerm U PalmLinTerm)) : AbstractConstraint => t2 !== t1]
// [!==(t1:PalmBinTerm, t2:PalmLinTerm) : AbstractConstraint => t2 !== t1]
/* The c++ function for: !==(a:integer,t:PalmUnTerm) [] */
AbstractConstraint * claire__I_equal_equal_integer6(int a,PalmUnTerm *t)
{ return (claire__I_equal_equal_PalmUnTerm3(t,a));} 


/* The c++ function for: !==(a:integer,t:PalmBinTerm) [] */
AbstractConstraint * claire__I_equal_equal_integer7(int a,PalmBinTerm *t)
{ return (claire__I_equal_equal_PalmBinTerm2(t,a));} 


/* The c++ function for: !==(a:integer,t:PalmLinTerm) [] */
OID  claire__I_equal_equal_integer8(int a,PalmLinTerm *t)
{ return (_oid_(claire__I_equal_equal_PalmLinTerm1(t,a)));} 


/* The c++ function for: !==(x:PalmIntVar,t:PalmUnTerm) [] */
AbstractConstraint * claire__I_equal_equal_PalmIntVar3(PalmIntVar *x,PalmUnTerm *t)
{ return (claire__I_equal_equal_PalmUnTerm4(t,x));} 


/* The c++ function for: !==(x:PalmIntVar,t:PalmBinTerm) [] */
OID  claire__I_equal_equal_PalmIntVar4(PalmIntVar *x,PalmBinTerm *t)
{ return (_oid_(claire__I_equal_equal_PalmBinTerm3(t,x)));} 


/* The c++ function for: !==(x:PalmIntVar,t:PalmLinTerm) [] */
OID  claire__I_equal_equal_PalmIntVar5(PalmIntVar *x,PalmLinTerm *t)
{ return (_oid_(claire__I_equal_equal_PalmLinTerm2(t,x)));} 


/* The c++ function for: !==(t1:PalmUnTerm,t2:PalmBinTerm) [] */
OID  claire__I_equal_equal_PalmUnTerm1(PalmUnTerm *t1,PalmBinTerm *t2)
{ return (_oid_(claire__I_equal_equal_PalmBinTerm4(t2,t1)));} 


/* The c++ function for: !==(t1:PalmUnTerm,t2:PalmLinTerm) [] */
OID  claire__I_equal_equal_PalmUnTerm2(PalmUnTerm *t1,PalmLinTerm *t2)
{ return (_oid_(claire__I_equal_equal_PalmLinTerm3(t2,t1)));} 


/* The c++ function for: !==(t1:PalmBinTerm,t2:PalmLinTerm) [] */
OID  claire__I_equal_equal_PalmBinTerm1(PalmBinTerm *t1,PalmLinTerm *t2)
{ return (_oid_(claire__I_equal_equal_PalmLinTerm4(t2,t1)));} 


/* The c++ function for: !==(t:PalmUnTerm,c:integer) [] */
PalmUnIntConstraint * claire__I_equal_equal_PalmUnTerm3(PalmUnTerm *t,int c)
{ GC_BIND;
  { PalmUnIntConstraint *Result ;
    Result = palm_makePalmUnIntConstraint_class(palm._PalmNotEqualxc,GC_OBJECT(PalmIntVar,t->v1),((t->sign1 == CTRUE) ?
      (c-t->cste) :
      (t->cste-c) ));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t:PalmUnTerm,x:PalmIntVar) [] */
IntConstraint * claire__I_equal_equal_PalmUnTerm4(PalmUnTerm *t,PalmIntVar *x)
{ if (t->sign1 != CTRUE) 
  { { IntConstraint *Result ;
      Result = palm_makePalmLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(palm._PalmIntVar,2,_oid_(x),GC_OID(_oid_(t->v1))),(-t->cste),3);
      return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      Result = palm_makePalmBinIntConstraint_class(palm._PalmNotEqualxyc,x,GC_OBJECT(PalmIntVar,t->v1),t->cste);
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: !==(t1:PalmUnTerm,t2:PalmUnTerm) [] */
IntConstraint * claire__I_equal_equal_PalmUnTerm5(PalmUnTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    { int  newcste = ((t1->sign1 == CTRUE) ?
        (t2->cste-t1->cste) :
        (t1->cste-t2->cste) );
      if (t1->sign1 != t2->sign1)
       Result = palm_makePalmLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t2->v1))),(-newcste),3);
      else Result = palm_makePalmBinIntConstraint_class(palm._PalmNotEqualxyc,GC_OBJECT(PalmIntVar,t1->v1),GC_OBJECT(PalmIntVar,t2->v1),newcste);
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmBinTerm,c:integer) [] */
IntConstraint * claire__I_equal_equal_PalmBinTerm2(PalmBinTerm *t1,int c)
{ GC_BIND;
  { IntConstraint *Result ;
    { int  newcste = ((t1->sign1 == CTRUE) ?
        (c-t1->cste) :
        (t1->cste-c) );
      if (t1->sign1 != t1->sign2)
       Result = palm_makePalmBinIntConstraint_class(palm._PalmNotEqualxyc,GC_OBJECT(PalmIntVar,t1->v1),GC_OBJECT(PalmIntVar,t1->v2),newcste);
      else Result = palm_makePalmLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t1->v2))),(-newcste),3);
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmBinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__I_equal_equal_PalmBinTerm3(PalmBinTerm *t1,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0829UU;
      { OID v_bag;
        GC_ANY(g0829UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0829UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0829UU)->addFast(v_bag);
        ((list *) g0829UU)->addFast(-1);} 
      Result = palm_makePalmLinComb_list(g0829UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        _oid_(x)),t1->cste,3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmBinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__I_equal_equal_PalmBinTerm4(PalmBinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0830UU;
      { OID v_bag;
        GC_ANY(g0830UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0830UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0830UU)->addFast(v_bag);
        ((list *) g0830UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0830UU,list::alloc(palm._PalmIntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmBinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__I_equal_equal_PalmBinTerm5(PalmBinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0831UU;
      { OID v_bag;
        GC_ANY(g0831UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0831UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g0831UU)->addFast(v_bag);
        ((list *) g0831UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g0831UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = palm_makePalmLinComb_list(g0831UU,list::alloc(palm._PalmIntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmLinTerm,c:integer) [] */
PalmLinComb * claire__I_equal_equal_PalmLinTerm1(PalmLinTerm *t1,int c)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,t1->lcoeff),GC_OBJECT(list,t1->lvars),(t1->cste-c),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmLinTerm,x:PalmIntVar) [] */
PalmLinComb * claire__I_equal_equal_PalmLinTerm2(PalmLinTerm *t1,PalmIntVar *x)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(_oid_(x))),t1->cste,3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmLinTerm,t2:PalmUnTerm) [] */
PalmLinComb * claire__I_equal_equal_PalmLinTerm3(PalmLinTerm *t1,PalmUnTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t1->cste-t2->cste),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmLinTerm,t2:PalmBinTerm) [] */
PalmLinComb * claire__I_equal_equal_PalmLinTerm4(PalmLinTerm *t1,PalmBinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    Result = palm_makePalmLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),list::alloc(palm._PalmIntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t1->cste-t2->cste),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:PalmLinTerm,t2:PalmLinTerm) [] */
PalmLinComb * claire__I_equal_equal_PalmLinTerm5(PalmLinTerm *t1,PalmLinTerm *t2)
{ GC_BIND;
  { PalmLinComb *Result ;
    { list * g0832UU;
      { { list * g0833UU;
          { { bag *v_list; OID v_val;
              OID cf,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g0833UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { cf = (*(v_list))[CLcount];
                v_val = (-cf);
                
                (*((list *) g0833UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0833UU);} 
          g0832UU = append_list(GC_OBJECT(list,t1->lcoeff),g0833UU);
          } 
        GC_OBJECT(list,g0832UU);} 
      Result = palm_makePalmLinComb_list(g0832UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 9 : symbolic constraints                           *
// *************************************************************
//  PalmOccurTerm: a temporary object representing occur(t,l)
/* The c++ function for: makePalmOccurConstraint(ot:PalmOccurTerm,v:PalmIntVar,atleast:boolean,atmost:boolean) [] */
PalmOccurrence * palm_makePalmOccurConstraint_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost)
{ GC_BIND;
  { PalmOccurrence *Result ;
    { list * l;
      { { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = GC_OBJECT(list,ot->lvars);
           l = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) l))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,l);} 
      int  n = l->length;
      int  nb1 = 0;
      int  nb2 = 0;
      OID * isPos = GC_ARRAY(make_array_integer(n,Kernel._boolean,Kernel.ctrue));
      OID * isSur = GC_ARRAY(make_array_integer(n,Kernel._boolean,Kernel.cfalse));
      int  tgt = ot->target;
      PalmOccurrence * c;
      { { PalmOccurrence * _CL_obj = ((PalmOccurrence *) GC_OBJECT(PalmOccurrence,new_object_class(palm._PalmOccurrence)));
          (_CL_obj->vars = ((list *) copy_bag(l))->addFast(_oid_(v)));
          (_CL_obj->cste = tgt);
          c = _CL_obj;
          } 
        GC_OBJECT(PalmOccurrence,c);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      { AbstractConstraint * g0835; 
        OID  g0836;
        g0835 = c;
        { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
          g0836 = _oid_(_CL_obj);
          } 
        (g0835->hook = g0836);} 
      { int  j = 1;
        int  g0834 = n;
        { OID gc_local;
          while ((j <= g0834))
          { // HOHO, GC_LOOP not needed !
            { PalmIntVar * v = OBJECT(PalmIntVar,(*(l))[j]);
              if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(v),
                tgt))) == CTRUE)
               { ++nb1;
                if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
                  tgt))) == CTRUE)
                 { (((OID *) isSur)[j] = Kernel.ctrue);
                  ++nb2;
                  } 
                } 
              else (((OID *) isPos)[j] = Kernel.cfalse);
                } 
            ++j;
            } 
          } 
        } 
      (c->isPossible = isPos);
      (c->isSure = isSur);
      STOREI(c->nbPossible,nb1);
      STOREI(c->nbSure,nb2);
      (c->constrainOnInfNumber = atleast);
      (c->constrainOnSupNumber = atmost);
      (c->checkInf = make_list_integer2((n+1),Kernel._boolean,Kernel.cfalse));
      (c->checkSup = make_list_integer2((n+1),Kernel._boolean,Kernel.cfalse));
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: occur(tgt:integer,l:list[PalmIntVar]) [] */
PalmOccurTerm * palm_occur_integer(int tgt,list *l)
{ GC_BIND;
  { PalmOccurTerm *Result ;
    { PalmOccurTerm * _CL_obj = ((PalmOccurTerm *) GC_OBJECT(PalmOccurTerm,new_object_class(palm._PalmOccurTerm)));
      (_CL_obj->target = tgt);
      { PalmOccurTerm * g0837; 
        list * g0838;
        g0837 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g0838 = v_list->clone(palm._PalmIntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g0838))[CLcount] = v_val;} 
          } 
        (g0837->lvars = g0838);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// Occurrence constraints are always stated as
//   occur(t,l) (==/>=/<=) x/v
// watchout: arithmetic is not complete.
/* The c++ function for: ==(ot:PalmOccurTerm,x:PalmIntVar) [] */
PalmOccurrence * claire__equal_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x)
{ return (palm_makePalmOccurConstraint_PalmOccurTerm(ot,x,CTRUE,CTRUE));} 


/* The c++ function for: ==(x:PalmIntVar,ot:PalmOccurTerm) [] */
PalmOccurrence * claire__equal_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot)
{ return (claire__equal_equal_PalmOccurTerm(ot,x));} 


/* The c++ function for: >=(ot:PalmOccurTerm,x:PalmIntVar) [] */
PalmOccurrence * claire__sup_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x)
{ return (palm_makePalmOccurConstraint_PalmOccurTerm(ot,x,CTRUE,CFALSE));} 


/* The c++ function for: <=(ot:PalmOccurTerm,x:PalmIntVar) [] */
PalmOccurrence * claire__inf_equal_PalmOccurTerm(PalmOccurTerm *ot,PalmIntVar *x)
{ return (palm_makePalmOccurConstraint_PalmOccurTerm(ot,x,CFALSE,CTRUE));} 


// v0.24 Symmetrical API's (a request from Michel Lemaitre)
/* The c++ function for: >=(x:PalmIntVar,ot:PalmOccurTerm) [] */
PalmOccurrence * claire__sup_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot)
{ return (claire__inf_equal_PalmOccurTerm(ot,x));} 


/* The c++ function for: <=(x:PalmIntVar,ot:PalmOccurTerm) [] */
PalmOccurrence * claire__inf_equal_PalmIntVar6(PalmIntVar *x,PalmOccurTerm *ot)
{ return (claire__sup_equal_PalmOccurTerm(ot,x));} 


/* The c++ function for: choco/getNth(l:list[integer],i:PalmUnTerm) [] */
PalmEltTerm * choco_getNth_list3(list *l,PalmUnTerm *i)
{ GC_BIND;
  if (i->sign1 != CTRUE)
   close_exception(((general_error *) (*Core._general_error)(_string_("Negative indexes (-I + c) are not allowed in an element constraint"),
    _oid_(Kernel.nil))));
  { PalmEltTerm *Result ;
    { PalmEltTerm * _CL_obj = ((PalmEltTerm *) GC_OBJECT(PalmEltTerm,new_object_class(palm._PalmEltTerm)));
      (_CL_obj->lvalues = l);
      (_CL_obj->indexVar = i->v1);
      (_CL_obj->cste = i->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/getNth(l:list[integer],i:PalmIntVar) [] */
PalmEltTerm * choco_getNth_list4(list *l,PalmIntVar *i)
{ GC_BIND;
  { PalmEltTerm *Result ;
    { PalmEltTerm * _CL_obj = ((PalmEltTerm *) GC_OBJECT(PalmEltTerm,new_object_class(palm._PalmEltTerm)));
      (_CL_obj->lvalues = l);
      (_CL_obj->indexVar = i);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmEltTerm,x:PalmIntVar) [] */
PalmElt * claire__equal_equal_PalmEltTerm1(PalmEltTerm *t,PalmIntVar *x)
{ GC_BIND;
  { PalmElt *Result ;
    Result = palm_makePalmEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(PalmIntVar,((PalmIntVar *) t->indexVar)),x,t->cste);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(x:PalmIntVar,t:PalmEltTerm) [] */
PalmElt * claire__equal_equal_PalmIntVar7(PalmIntVar *x,PalmEltTerm *t)
{ GC_BIND;
  { PalmElt *Result ;
    Result = palm_makePalmEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(PalmIntVar,((PalmIntVar *) t->indexVar)),x,t->cste);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:PalmEltTerm,x:integer) [] */
PalmElt * claire__equal_equal_PalmEltTerm2(PalmEltTerm *t,int x)
{ GC_BIND;
  { PalmElt *Result ;
    Result = palm_makePalmEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(PalmIntVar,((PalmIntVar *) t->indexVar)),GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(x)),t->cste);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(x:integer,t:PalmEltTerm) [] */
PalmElt * claire__equal_equal_integer12(int x,PalmEltTerm *t)
{ GC_BIND;
  { PalmElt *Result ;
    Result = palm_makePalmEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(PalmIntVar,((PalmIntVar *) t->indexVar)),GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(x)),t->cste);
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 10 : boolean connectors                            *
// *************************************************************
// ----- Disjunctions -----------
// 1. Associative rules for building disjunctions from two disjunctions
/* The c++ function for: e-or(c1:PalmLargeDisjunction,c2:PalmLargeDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction1(PalmLargeDisjunction *c1,PalmLargeDisjunction *c2)
{ GC_BIND;
  { PalmLargeDisjunction *Result ;
    { PalmLargeDisjunction * c;
      { { PalmLargeDisjunction * _CL_obj = ((PalmLargeDisjunction *) GC_OBJECT(PalmLargeDisjunction,new_object_class(palm._PalmLargeDisjunction)));
          (_CL_obj->lconst = append_list(GC_OBJECT(list,c1->lconst),GC_OBJECT(list,c2->lconst)));
          (_CL_obj->needToAwake = append_list(GC_OBJECT(list,c1->needToAwake),GC_OBJECT(list,c2->needToAwake)));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeDisjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-or(c1:PalmDisjunction,c2:PalmDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_PalmDisjunction1(PalmDisjunction *c1,PalmDisjunction *c2)
{ GC_BIND;
  { PalmLargeDisjunction *Result ;
    { PalmLargeDisjunction * c;
      { { PalmLargeDisjunction * _CL_obj = ((PalmLargeDisjunction *) GC_OBJECT(PalmLargeDisjunction,new_object_class(palm._PalmLargeDisjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,4,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            GC_OID(_oid_(c2->const1)),
            GC_OID(_oid_(c2->const2))));
          (_CL_obj->needToAwake = list::alloc(Kernel._boolean,4,Kernel.cfalse,
            Kernel.cfalse,
            Kernel.cfalse,
            Kernel.cfalse));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeDisjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-or(c1:PalmLargeDisjunction,c2:PalmDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction2(PalmLargeDisjunction *c1,PalmDisjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const1)));
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const2)));
  (c1->needToAwake = GC_OBJECT(list,c1->needToAwake)->addFast(Kernel.cfalse));
  (c1->needToAwake = GC_OBJECT(list,c1->needToAwake)->addFast(Kernel.cfalse));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  palm_initHook_PalmLargeDisjunction(c1);
  { PalmLargeDisjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-or(c1:PalmDisjunction,c2:PalmLargeDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_PalmDisjunction2(PalmDisjunction *c1,PalmLargeDisjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const1)));
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const2)));
  (c2->needToAwake = GC_OBJECT(list,c2->needToAwake)->addFast(Kernel.cfalse));
  (c2->needToAwake = GC_OBJECT(list,c2->needToAwake)->addFast(Kernel.cfalse));
  choco_closeBoolConstraint_LargeCompositeConstraint(c2);
  palm_initHook_PalmLargeDisjunction(c2);
  { PalmLargeDisjunction *Result ;
    Result = c2;
    GC_UNBIND; return (Result);} 
  } 


// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v0.21 <fl> initialize lstatus -> v1.02 done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
/* The c++ function for: e-or(c1:PalmDisjunction,c2:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmConjunction) U PalmLargeConjunction)) [] */
PalmLargeDisjunction * claire_e_dashor_PalmDisjunction3(PalmDisjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmLargeDisjunction *Result ;
    { PalmLargeDisjunction * c;
      { { PalmLargeDisjunction * _CL_obj = ((PalmLargeDisjunction *) GC_OBJECT(PalmLargeDisjunction,new_object_class(palm._PalmLargeDisjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,3,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            _oid_(c2)));
          (_CL_obj->needToAwake = list::alloc(Kernel._boolean,3,Kernel.cfalse,
            Kernel.cfalse,
            Kernel.cfalse));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeDisjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-or(c1:PalmLargeDisjunction,c2:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmConjunction) U PalmLargeConjunction)) [] */
PalmLargeDisjunction * claire_e_dashor_PalmLargeDisjunction3(PalmLargeDisjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(_oid_(c2));
  (c1->needToAwake = GC_OBJECT(list,c1->needToAwake)->addFast(Kernel.cfalse));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  palm_initHook_PalmLargeDisjunction(c1);
  { PalmLargeDisjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the "or" operator is commutative 
/* The c++ function for: e-or(c1:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmConjunction) U PalmLargeConjunction),c2:PalmDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_AbstractConstraint1(AbstractConstraint *c1,PalmDisjunction *c2)
{ return (claire_e_dashor_PalmDisjunction3(c2,c1));} 


/* The c++ function for: e-or(c1:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmConjunction) U PalmLargeConjunction),c2:PalmLargeDisjunction) [] */
PalmLargeDisjunction * claire_e_dashor_AbstractConstraint2(AbstractConstraint *c1,PalmLargeDisjunction *c2)
{ return (claire_e_dashor_PalmLargeDisjunction3(c2,c1));} 


// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
/* The c++ function for: e-or(c1:(choco/IntConstraint U choco/CompositeConstraint),c2:(choco/IntConstraint U choco/CompositeConstraint)) [] */
PalmDisjunction * claire_e_dashor_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmDisjunction *Result ;
    { PalmDisjunction * d;
      { { PalmDisjunction * _CL_obj = ((PalmDisjunction *) GC_OBJECT(PalmDisjunction,new_object_class(palm._PalmDisjunction)));
          (_CL_obj->const1 = c1);
          (_CL_obj->const2 = c2);
          d = _CL_obj;
          } 
        GC_OBJECT(PalmDisjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      palm_initHook_PalmDisjunction(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 4. additional (non binary) method definition (new in 0.9901)
/* The c++ function for: e-or(l:list[choco/AbstractConstraint]) [] */
PalmLargeDisjunction * claire_e_dashor_list(list *l)
{ GC_BIND;
  { PalmLargeDisjunction *Result ;
    { int  n = l->length;
      PalmLargeDisjunction * c;
      { { PalmLargeDisjunction * _CL_obj = ((PalmLargeDisjunction *) GC_OBJECT(PalmLargeDisjunction,new_object_class(palm._PalmLargeDisjunction)));
          (_CL_obj->lconst = ((list *) copy_bag(l)));
          (_CL_obj->needToAwake = make_list_integer2(n,Kernel._boolean,Kernel.cfalse));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeDisjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Conjunctions -----------
// 1. Associative rules for building conjunctions from two conjunctions
/* The c++ function for: e-and(c1:PalmConjunction,c2:PalmConjunction) [] */
PalmLargeConjunction * claire_e_dashand_PalmConjunction1(PalmConjunction *c1,PalmConjunction *c2)
{ GC_BIND;
  { PalmLargeConjunction *Result ;
    { PalmLargeConjunction * c;
      { { PalmLargeConjunction * _CL_obj = ((PalmLargeConjunction *) GC_OBJECT(PalmLargeConjunction,new_object_class(palm._PalmLargeConjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,4,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            GC_OID(_oid_(c2->const1)),
            GC_OID(_oid_(c2->const2))));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeConjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-and(c1:PalmLargeConjunction,c2:PalmLargeConjunction) [] */
PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction1(PalmLargeConjunction *c1,PalmLargeConjunction *c2)
{ GC_BIND;
  { PalmLargeConjunction *Result ;
    { PalmLargeConjunction * c;
      { { PalmLargeConjunction * _CL_obj = ((PalmLargeConjunction *) GC_OBJECT(PalmLargeConjunction,new_object_class(palm._PalmLargeConjunction)));
          (_CL_obj->lconst = append_list(GC_OBJECT(list,c1->lconst),GC_OBJECT(list,c2->lconst)));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeConjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-and(c1:PalmLargeConjunction,c2:PalmConjunction) [] */
PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction2(PalmLargeConjunction *c1,PalmConjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const1)));
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  palm_initHook_PalmLargeConjunction(c1);
  { PalmLargeConjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-and(c1:PalmConjunction,c2:PalmLargeConjunction) [] */
PalmLargeConjunction * claire_e_dashand_PalmConjunction2(PalmConjunction *c1,PalmLargeConjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const1)));
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c2);
  palm_initHook_PalmLargeConjunction(c2);
  { PalmLargeConjunction *Result ;
    Result = c2;
    GC_UNBIND; return (Result);} 
  } 


// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v1.02 status initialization is done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
/* The c++ function for: e-and(c1:PalmConjunction,c2:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmDisjunction) U PalmLargeDisjunction)) [] */
PalmLargeConjunction * claire_e_dashand_PalmConjunction3(PalmConjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmLargeConjunction *Result ;
    { PalmLargeConjunction * c;
      { { PalmLargeConjunction * _CL_obj = ((PalmLargeConjunction *) GC_OBJECT(PalmLargeConjunction,new_object_class(palm._PalmLargeConjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,3,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            _oid_(c2)));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeConjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-and(c1:PalmLargeConjunction,c2:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmDisjunction) U PalmLargeDisjunction)) [] */
PalmLargeConjunction * claire_e_dashand_PalmLargeConjunction3(PalmLargeConjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(_oid_(c2));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  palm_initHook_PalmLargeConjunction(c1);
  { PalmLargeConjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the "and" operator is commutative 
/* The c++ function for: e-and(c1:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmDisjunction) U PalmLargeDisjunction),c2:PalmConjunction) [] */
PalmLargeConjunction * claire_e_dashand_AbstractConstraint1(AbstractConstraint *c1,PalmConjunction *c2)
{ return (claire_e_dashand_PalmConjunction3(c2,c1));} 


/* The c++ function for: e-and(c1:(((((choco/IntConstraint U PalmCardinality) U PalmGuard) U PalmEquiv) U PalmDisjunction) U PalmLargeDisjunction),c2:PalmLargeConjunction) [] */
PalmLargeConjunction * claire_e_dashand_AbstractConstraint2(AbstractConstraint *c1,PalmLargeConjunction *c2)
{ return (claire_e_dashand_PalmLargeConjunction3(c2,c1));} 


// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
/* The c++ function for: e-and(c1:(choco/IntConstraint U choco/CompositeConstraint),c2:(choco/IntConstraint U choco/CompositeConstraint)) [] */
PalmConjunction * claire_e_dashand_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmConjunction *Result ;
    { PalmConjunction * c;
      { { PalmConjunction * _CL_obj = ((PalmConjunction *) GC_OBJECT(PalmConjunction,new_object_class(palm._PalmConjunction)));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmConjunction,c);} 
      (c->const1 = c1);
      (c->const2 = c2);
      choco_closeBoolConstraint_BinCompositeConstraint(c);
      palm_initHook_PalmConjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 4. additional (non binary) method definition (new in 0.9901)
/* The c++ function for: e-and(l:list[choco/AbstractConstraint]) [] */
PalmLargeConjunction * claire_e_dashand_list(list *l)
{ GC_BIND;
  { PalmLargeConjunction *Result ;
    { int  n = l->length;
      PalmLargeConjunction * c;
      { { PalmLargeConjunction * _CL_obj = ((PalmLargeConjunction *) GC_OBJECT(PalmLargeConjunction,new_object_class(palm._PalmLargeConjunction)));
          (_CL_obj->lconst = ((list *) copy_bag(l)));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmLargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      palm_initHook_PalmLargeConjunction(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Complete and lightweight guards + equivalence -----------
// v0.97: the "implies" guard now achieves both ways consistency (back propagating guard onto the triggerring condition)
/* The c++ function for: e-implies(c1:(choco/IntConstraint U choco/CompositeConstraint),c2:(choco/IntConstraint U choco/CompositeConstraint)) [] */
CompositeConstraint * claire_e_dashimplies_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { CompositeConstraint *Result ;
    Result = OBJECT(CompositeConstraint,(*palm.e_dashor)(GC_OID((*choco.opposite)(_oid_(c1))),
      _oid_(c2)));
    GC_UNBIND; return (Result);} 
  } 


// this is the old implies: a lightweight implementation (incomplete propagation)
/* The c++ function for: e-ifThen(c1:(choco/IntConstraint U choco/CompositeConstraint),c2:(choco/IntConstraint U choco/CompositeConstraint)) [] */
PalmGuard * palm_e_dashifThen_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmGuard *Result ;
    { PalmGuard * g;
      { { PalmGuard * _CL_obj = ((PalmGuard *) GC_OBJECT(PalmGuard,new_object_class(palm._PalmGuard)));
          g = _CL_obj;
          } 
        GC_OBJECT(PalmGuard,g);} 
      (g->const1 = c1);
      (g->const2 = c2);
      choco_closeBoolConstraint_BinCompositeConstraint(g);
      palm_initHook_PalmGuard(g);
      Result = g;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.29: there is now an explicit correspondance between
// the indices of variables in e.const1 and in e.oppositeConst1
// most of the time, computeVarIdxInOpposite computes the same index, but not for linear constraint
/* The c++ function for: e-iff(c1:(choco/IntConstraint U choco/CompositeConstraint),c2:(choco/IntConstraint U choco/CompositeConstraint)) [] */
PalmEquiv * claire_e_dashiff_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { PalmEquiv *Result ;
    { PalmEquiv * e;
      { { PalmEquiv * _CL_obj = ((PalmEquiv *) GC_OBJECT(PalmEquiv,new_object_class(palm._PalmEquiv)));
          e = _CL_obj;
          } 
        GC_OBJECT(PalmEquiv,e);} 
      (e->const1 = c1);
      (e->const2 = c2);
      choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp(e);
      palm_initHook_PalmEquiv(e);
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Cardinality constraints -----------
/* The c++ function for: makePalmCardinalityConstraint(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:PalmIntVar,atleast:boolean,atmost:boolean) [] */
PalmCardinality * palm_makePalmCardinalityConstraint_list(list *l,PalmIntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost)
{ GC_BIND;
  ;{ PalmCardinality *Result ;
    { int  n = l->length;
      PalmCardinality * c;
      { { PalmCardinality * _CL_obj = ((PalmCardinality *) GC_OBJECT(PalmCardinality,new_object_class(palm._PalmCardinality)));
          (_CL_obj->lconst = l);
          (_CL_obj->nbConst = n);
          (_CL_obj->additionalVars = list::alloc(choco._IntVar,1,_oid_(v)));
          (_CL_obj->constrainOnInfNumber = atleast);
          (_CL_obj->constrainOnSupNumber = atmost);
          (_CL_obj->needToAwake = make_list_integer2((2*n),Kernel._boolean,Kernel.cfalse));
          c = _CL_obj;
          } 
        GC_OBJECT(PalmCardinality,c);} 
      choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp(c);
      palm_initHook_PalmCardinality(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9904: extended API
/* The c++ function for: e-card(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:PalmIntVar) [] */
PalmCardinality * palm_e_dashcard_list1(list *l,PalmIntVar *v)
{ return (palm_makePalmCardinalityConstraint_list(l,v,CTRUE,CTRUE));} 


/* The c++ function for: e-atleast(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:PalmIntVar) [] */
PalmCardinality * palm_e_dashatleast_list1(list *l,PalmIntVar *v)
{ return (palm_makePalmCardinalityConstraint_list(l,v,CTRUE,CFALSE));} 


/* The c++ function for: e-atmost(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:PalmIntVar) [] */
PalmCardinality * palm_e_dashatmost_list1(list *l,PalmIntVar *v)
{ return (palm_makePalmCardinalityConstraint_list(l,v,CFALSE,CTRUE));} 


// <thb> v0.93: allows an integer as second parameter
/* The c++ function for: e-card(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:integer) [] */
PalmCardinality * palm_e_dashcard_list2(list *l,int v)
{ GC_BIND;
  { PalmCardinality *Result ;
    Result = palm_e_dashcard_list1(l,GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-atleast(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:integer) [] */
PalmCardinality * palm_e_dashatleast_list2(list *l,int v)
{ GC_BIND;
  { PalmCardinality *Result ;
    Result = palm_e_dashatleast_list1(l,GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: e-atmost(l:list[(choco/IntConstraint U choco/CompositeConstraint)],v:integer) [] */
PalmCardinality * palm_e_dashatmost_list2(list *l,int v)
{ GC_BIND;
  { PalmCardinality *Result ;
    Result = palm_e_dashatmost_list1(l,GC_OBJECT(PalmIntVar,palm_makeConstantPalmIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 11 : user-friendly tools                           *
// *************************************************************
// Defining a User-Friendly box 
/* The c++ function for: startInfoBox(pb:PalmProblem,sname:string,fname:string) [] */
void  palm_startInfoBox_PalmProblem(PalmProblem *pb,char *sname,char *fname)
{ GC_BIND;
  ;{ PalmUserFriendlyBox * nb;
    { { PalmUserFriendlyBox * _CL_obj = ((PalmUserFriendlyBox *) GC_OBJECT(PalmUserFriendlyBox,new_object_class(palm._PalmUserFriendlyBox)));
        (_CL_obj->shortName = copy_string(sname));
        (_CL_obj->name = copy_string(fname));
        nb = _CL_obj;
        } 
      GC_OBJECT(PalmUserFriendlyBox,nb);} 
    GC_OBJECT(list,OBJECT(PalmUserFriendlyBox,palm.UFcurrentBox->value)->childrenBoxes)->addFast(_oid_(nb));
    (nb->fatherBox = OBJECT(PalmUserFriendlyBox,palm.UFcurrentBox->value));
    GC_OBJECT(set,pb->allUFboxes)->addFast(_oid_(nb));
    (palm.UFcurrentBox->value= _oid_(nb));
    } 
  GC_UNBIND;} 


// ending the definition of a user-friendly box
/* The c++ function for: endInfoBox(pb:PalmProblem) [] */
void  palm_endInfoBox_PalmProblem(PalmProblem *pb)
{ (palm.UFcurrentBox->value= _oid_(OBJECT(PalmUserFriendlyBox,palm.UFcurrentBox->value)->fatherBox));
  } 


// pretty printing a user-friendly box
/* The c++ function for: self_print(b:PalmUserFriendlyBox) [] */
OID  claire_self_print_PalmUserFriendlyBox_palm(PalmUserFriendlyBox *b)
{ princ_string("[");
  princ_string(b->shortName);
  princ_string(" - ");
  print_any(b->constraints->length);
  princ_string(" cts] ");
  princ_string(b->name);
  return (_void_(princ_string("")));} 


// pretty printing problems
/* The c++ function for: self_print(pb:PalmProblem) [] */
void  claire_self_print_PalmProblem_palm(PalmProblem *pb)
{ if ((OBJECT(ClaireBoolean,palm.PALM_USER_FRIENDLY->value)) == CTRUE) 
  { { princ_string("=== ");
      princ_string(pb->name);
      princ_string(" : description \n");
      palm_showInfoBox_integer(2,GC_OBJECT(PalmUserFriendlyBox,pb->rootUFboxes));
      } 
     } 
  else{ GC_BIND;
    princ_string("<PB>: ");
    princ_string(pb->name);
    princ_string("");
    GC_UNBIND;} 
  } 


/* The c++ function for: showInfoBox(t:integer,b:PalmUserFriendlyBox) [] */
OID  palm_showInfoBox_integer(int t,PalmUserFriendlyBox *b)
{ GC_BIND;
  princ_string("+");
  { int  i = 1;
    int  g0839 = t;
    { OID gc_local;
      while ((i <= g0839))
      { // HOHO, GC_LOOP not needed !
        princ_string("..");
        ++i;
        } 
      } 
    } 
  print_any(_oid_(b));
  princ_string(" \n");
  { OID Result = 0;
    { OID gc_local;
      ITERATE(bc);
      Result= _oid_(CFALSE);
      bag *bc_support;
      bc_support = GC_OBJECT(list,b->childrenBoxes);
      for (START(bc_support); NEXT(bc);)
      { GC_LOOP;
        palm_showInfoBox_integer((t+1),OBJECT(PalmUserFriendlyBox,bc));
        GC_UNLOOP;} 
      } 
    GC_UNBIND; return (Result);} 
  } 


// projecting a constraint on a user view
/* The c++ function for: project(ct:choco/AbstractConstraint,pb:PalmProblem) [] */
PalmUserFriendlyBox * palm_project_AbstractConstraint(AbstractConstraint *ct,PalmProblem *pb)
{ { PalmUserFriendlyBox *Result ;
    { OID  bltest = _oid_(OBJECT(PalmInfoConstraint,ct->hook)->ufBox);
      if (bltest != CNULL)
       { PalmUserFriendlyBox * bl = OBJECT(PalmUserFriendlyBox,bltest);
        set * us = pb->userRepresentation;
        { while ((contain_ask_set(us,_oid_(bl)) != CTRUE))
          { bl= bl->fatherBox;
            } 
          } 
        Result = bl;
        } 
      else Result = pb->rootUFboxes;
        } 
    return (Result);} 
  } 


// projecting back
/* The c++ function for: getConstraints(rb:PalmUserFriendlyBox) [] */
set * palm_getConstraints_PalmUserFriendlyBox(PalmUserFriendlyBox *rb)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { set *Result ;
    { set * cts = GC_OBJECT(set,set_I_bag(GC_OBJECT(list,rb->constraints)));
      { OID gc_local;
        ITERATE(b);
        bag *b_support;
        b_support = GC_OBJECT(list,rb->childrenBoxes);
        for (START(b_support); NEXT(b);)
        { GC_LOOP;
          GC__ANY(cts = ((set *) U_type(cts,GC_OBJECT(set,palm_getConstraints_PalmUserFriendlyBox(OBJECT(PalmUserFriendlyBox,b))))), 1);
          GC_UNLOOP;} 
        } 
      Result = cts;
      } 
    GC_UNBIND; return (Result);} 
  } 


// setting on/off the user-friendly mode
/* The c++ function for: setUserFriendly(val:boolean) [] */
OID  palm_setUserFriendly_boolean(ClaireBoolean *val)
{ return ((palm.PALM_USER_FRIENDLY->value= _oid_(val)));} 


// defining the user representation 
/* The c++ function for: setUserRepresentation(pb:PalmProblem,uv:list[string]) [] */
void  palm_setUserRepresentation_PalmProblem(PalmProblem *pb,list *uv)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { OID gc_local;
    ITERATE(b);
    for (START(uv); NEXT(b);)
    { GC_LOOP;
      { OID  rbtest;
        { { OID  x_some = CNULL;
            { OID gc_local;
              ITERATE(x);
              bag *x_support;
              x_support = GC_OBJECT(set,pb->allUFboxes);
              for (START(x_support); NEXT(x);)
              { GC_LOOP;
                if (equal(_string_(OBJECT(PalmUserFriendlyBox,x)->shortName),b) == CTRUE)
                 { x_some= x;
                  break;} 
                GC_UNLOOP;} 
              } 
            rbtest = x_some;
            } 
          GC_OID(rbtest);} 
        if (rbtest != CNULL)
         { PalmUserFriendlyBox * rb = OBJECT(PalmUserFriendlyBox,rbtest);
          GC_OBJECT(set,pb->userRepresentation)->addFast(_oid_(rb));
          } 
        else { princ_string("PaLM: ");
            princ_string(string_v(b));
            princ_string(" unknown and therefore ignored ... (setUserRepresentation in palmapi.cl)\n");
            } 
          } 
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// setting back a box into the system 
/* The c++ function for: choco/post(pb:PalmProblem,b:PalmUserFriendlyBox) [] */
void  choco_post_PalmProblem9(PalmProblem *pb,PalmUserFriendlyBox *b)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c);
    bag *c_support;
    c_support = GC_OBJECT(set,palm_getConstraints_PalmUserFriendlyBox(b));
    for (START(c_support); NEXT(c);)
    { GC_LOOP;
      { write_property(palm.weight,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,c)->hook)),99999999);
        palm_setUserFriendly_boolean(CFALSE);
        if (not_any((*choco.isActive)(c)) == CTRUE)
         (*choco.post)(_oid_(pb),
          c);
        palm_setUserFriendly_boolean(CTRUE);
        } 
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// default behavior for checking palmitude
/* The c++ function for: checkPalm(ct:choco/AbstractConstraint) [] */
ClaireBoolean * palm_checkPalm_AbstractConstraint(AbstractConstraint *ct)
{ return (CFALSE);} 


/* The c++ function for: checkFullPalm(pb:PalmProblem) [] */
ClaireBoolean * palm_checkFullPalm_PalmProblem(PalmProblem *pb)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * isPalmCompatible = CTRUE;
      { OID gc_local;
        ITERATE(v);
        bag *v_support;
        v_support = GC_OBJECT(list,pb->vars);
        for (START(v_support); NEXT(v);)
        { GC_LOOP;
          if (!INHERIT(OBJECT(ClaireObject,v)->isa,palm._PalmIntVar))
           { isPalmCompatible= CFALSE;
            tformat_string("Beware !!! ~S should inherit from PalmIntVar \n",0,list::alloc(1,v));
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(c);
        bag *c_support;
        c_support = GC_OBJECT(list,pb->constraints);
        for (START(c_support); NEXT(c);)
        { GC_LOOP;
          if (not_any((*palm.checkPalm)(c)) == CTRUE)
           { isPalmCompatible= CFALSE;
            tformat_string("Beware !!! ~S is not a PaLM constraint -- check your code \n",0,list::alloc(1,c));
            } 
          GC_UNLOOP;} 
        } 
      Result = isPalmCompatible;
      } 
    GC_UNBIND; return (Result);} 
  } 

