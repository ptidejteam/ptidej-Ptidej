/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-explain.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:41 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 
// ** Summary
// Handling explanations and specific actions on them
// Part 1 : Set operations ... 
// Part 2 : New events (tools and API)                      
// Part 3 : Bound explanations 
// Part 4 : Value Removal explanations
// Part 5 : Initialising the PalmEngine ...
// Part 6 : Flushing and resetting event Queues upon contradiction
// Part 7 : Handling contradictions
// Part 8 : Removing constraints
// Part 9 : Explanation generation API 
// *************************************************************
// *   Part 1: Constraints Set operations                      *
// *************************************************************
/* The c++ function for: self_print(p:Explanation) [] */
OID  claire_self_print_Explanation_palm(Explanation *p)
{ if ((OBJECT(ClaireBoolean,palm.PALM_USER_FRIENDLY->value)) == CTRUE) 
  { { OID Result = 0;
      { OID  cttest;
        { { OID  c_some = CNULL;
            { OID gc_local;
              ITERATE(c);
              bag *c_support;
              c_support = GC_OBJECT(set,p->explanation);
              for (START(c_support); NEXT(c);)
              { GC_LOOP;
                if (CTRUE == CTRUE)
                 { c_some= c;
                  break;} 
                GC_UNLOOP;} 
              } 
            cttest = c_some;
            } 
          GC_OID(cttest);} 
        if (cttest != CNULL)
         { AbstractConstraint * ct = OBJECT(AbstractConstraint,cttest);
          set * ufb = set::empty();
          Problem * pb = GC_OBJECT(Problem,choco_getProblem_AbstractConstraint(ct));
          { OID gc_local;
            ITERATE(c);
            bag *c_support;
            c_support = GC_OBJECT(set,p->explanation);
            for (START(c_support); NEXT(c);)
            { GC_LOOP;
              ufb= ufb->addFast(GC_OID(_string_(palm_project_AbstractConstraint(OBJECT(AbstractConstraint,c),((PalmProblem *) pb))->shortName)));
              GC_UNLOOP;} 
            } 
          print_any(_oid_(ufb));
          princ_string("");
          } 
        else { { OID  g0221UU;
              { set * V_CL0222;{ set * x_bag = set::empty(Kernel.emptySet);
                  { OID gc_local;
                    ITERATE(x);
                    bag *x_support;
                    x_support = GC_OBJECT(set,p->explanation);
                    for (START(x_support); NEXT(x);)
                    { GC_LOOP;
                      x_bag->addFast(x);
                      GC_UNLOOP;} 
                    } 
                  V_CL0222 = GC_OBJECT(set,x_bag);
                  } 
                
                g0221UU=_oid_(V_CL0222);} 
              print_any(g0221UU);
              } 
            princ_string("");
            } 
          } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID  g0223UU;
      { set * V_CL0224;{ set * x_bag = set::empty(Kernel.emptySet);
          { OID gc_local;
            ITERATE(x);
            bag *x_support;
            x_support = GC_OBJECT(set,p->explanation);
            for (START(x_support); NEXT(x);)
            { GC_LOOP;
              x_bag->addFast(x);
              GC_UNLOOP;} 
            } 
          V_CL0224 = GC_OBJECT(set,x_bag);
          } 
        
        g0223UU=_oid_(V_CL0224);} 
      print_any(g0223UU);
      } 
    { OID Result = 0;
      princ_string("");
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: addConstraint(p:Explanation,c:choco/AbstractConstraint) [] */
void  palm_addConstraint_Explanation(Explanation *p,AbstractConstraint *c)
{ GC_BIND;
  p->explanation->addFast(_oid_(c));
  GC_UNBIND;} 


/* The c++ function for: deleteConstraint(p:Explanation,c:choco/AbstractConstraint) [] */
void  palm_deleteConstraint_Explanation(Explanation *p,AbstractConstraint *c)
{ delete_bag(p->explanation,_oid_(c));
  } 


/* The c++ function for: mergeConstraints(p:Explanation,lc:set[choco/AbstractConstraint]) [] */
void  palm_mergeConstraints_Explanation(Explanation *p,set *lc)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c);
    for (START(lc); NEXT(c);)
    { GC_LOOP;
      p->explanation->addFast(c);
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


/* The c++ function for: merge(p:Explanation,q:Explanation) [] */
void  palm_merge_Explanation(Explanation *p,Explanation *q)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c);
    for (START(q->explanation); NEXT(c);)
    { GC_LOOP;
      p->explanation->addFast(c);
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


/* The c++ function for: empty?(p:Explanation) [] */
ClaireBoolean * palm_empty_ask_Explanation(Explanation *p)
{ return (equal(p->explanation->length,0));} 


/* The c++ function for: empties(p:Explanation) [] */
void  palm_empties_Explanation(Explanation *p)
{ (p->explanation = set::empty(choco._AbstractConstraint));
  } 


/* The c++ function for: delete(p:Explanation,c:choco/AbstractConstraint) [] */
Explanation * claire_delete_Explanation(Explanation *p,AbstractConstraint *c)
{ delete_bag(p->explanation,_oid_(c));
  return (p);} 


/* The c++ function for: set!(p:Explanation) [] */
set * claire_set_I_Explanation(Explanation *p)
{ return (p->explanation);} 


/* The c++ function for: %(c:choco/AbstractConstraint,p:Explanation) [] */
ClaireBoolean * claire__Z_AbstractConstraint(AbstractConstraint *c,Explanation *p)
{ return (contain_ask_set(p->explanation,_oid_(c)));} 


/* The c++ function for: clone(p:Explanation) [] */
Explanation * palm_clone_Explanation(Explanation *p)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      { OID gc_local;
        ITERATE(c);
        for (START(p->explanation); NEXT(c);)
        { GC_LOOP;
          expl->explanation->addFast(c);
          GC_UNLOOP;} 
        } 
      Result = expl;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: valid?(e:Explanation) [] */
ClaireBoolean * palm_valid_ask_Explanation(Explanation *e)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0225UU;
      { OID gc_local;
        ITERATE(c);
        g0225UU= _oid_(CFALSE);
        for (START(e->explanation); NEXT(c);)
        { GC_LOOP;
          if (not_any((*choco.isActive)(c)) == CTRUE)
           { g0225UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = not_any(g0225UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: contains?(e:Explanation,f:Explanation) [] */
ClaireBoolean * palm_contains_ask_Explanation(Explanation *e,Explanation *f)
{ { ClaireBoolean *Result ;
    { OID  g0226UU;
      { ITERATE(c);
        g0226UU= _oid_(CFALSE);
        for (START(f->explanation); NEXT(c);)
        if (belong_to(c,_oid_(e)) != CTRUE)
         { g0226UU = Kernel.ctrue;
          break;} 
        } 
      Result = not_any(g0226UU);
      } 
    return (Result);} 
  } 


/* The c++ function for: clean(e:Explanation) [] */
void  palm_clean_Explanation(Explanation *e)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c);
    bag *c_support;
    c_support = GC_OBJECT(set,palm_clone_Explanation(e)->explanation);
    for (START(c_support); NEXT(c);)
    { GC_LOOP;
      if (OBJECT(PalmInfoConstraint,OBJECT(AbstractConstraint,c)->hook)->indirect == CTRUE)
       delete_bag(e->explanation,c);
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// *************************************************************
// *   Part 2: New events (tools and API)                      *
// *************************************************************
// pretty printing 
/* The c++ function for: self_print(e:DecInf) [] */
void  claire_self_print_DecInf_palm(DecInf *e)
{ GC_BIND;
  princ_string("RINF(");
  print_any(GC_OID(_oid_(e->modifiedVar)));
  princ_string("):");
  print_any(CLREAD(IntVar,e->modifiedVar,inf)->latestValue);
  princ_string("[c:");
  print_any(e->cause);
  princ_string("][i:");
  print_any(e->idxInQueue);
  princ_string("]");
  GC_UNBIND;} 


// pretty printing
/* The c++ function for: self_print(e:IncSup) [] */
void  claire_self_print_IncSup_palm(IncSup *e)
{ GC_BIND;
  princ_string("RSUP(");
  print_any(GC_OID(_oid_(e->modifiedVar)));
  princ_string("):");
  print_any(CLREAD(IntVar,e->modifiedVar,sup)->latestValue);
  princ_string("[c:");
  print_any(e->cause);
  princ_string("][i:");
  print_any(e->idxInQueue);
  princ_string("]");
  GC_UNBIND;} 


// pretty printing 
/* The c++ function for: self_print(e:ValueRestorations) [] */
void  claire_self_print_ValueRestorations_palm(ValueRestorations *e)
{ GC_BIND;
  { list * lvr;
    { { list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0227 = e->nbVals;
          { OID gc_local;
            while ((i <= g0227))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(e->valueStack))[i]);
              ++i;
              } 
            } 
          } 
        lvr = GC_OBJECT(list,i_bag);
        } 
      GC_OBJECT(list,lvr);} 
    list * lcs;
    { { list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0228 = e->nbVals;
          { OID gc_local;
            while ((i <= g0228))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(e->causeStack))[i]);
              ++i;
              } 
            } 
          } 
        lcs = GC_OBJECT(list,i_bag);
        } 
      GC_OBJECT(list,lcs);} 
    princ_string("RVALS(");
    print_any(GC_OID(_oid_(e->modifiedVar)));
    princ_string("):");
    print_any(_oid_(lvr));
    princ_string("[c:");
    print_any(_oid_(lcs));
    princ_string("][i:");
    print_any(e->idxInQueue);
    princ_string("]");
    } 
  GC_UNBIND;} 


// API 
/* The c++ function for: makeValueRestorations(var:PalmIntVar,mv:integer) [] */
ValueRestorations * palm_makeValueRestorations_PalmIntVar(PalmIntVar *var,int mv)
{ GC_BIND;
  { ValueRestorations *Result ;
    { ValueRestorations * e;
      { { ValueRestorations * _CL_obj = ((ValueRestorations *) GC_OBJECT(ValueRestorations,new_object_class(palm._ValueRestorations)));
          (_CL_obj->modifiedVar = var);
          (_CL_obj->maxVals = mv);
          (_CL_obj->many = CFALSE);
          e = _CL_obj;
          } 
        GC_OBJECT(ValueRestorations,e);} 
      (e->valueStack = make_list_integer2(mv,Kernel._integer,0));
      (e->causeStack = make_list_integer2(mv,Kernel._integer,0));
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 3 : Bound explanations                              *
// *************************************************************
// General ... 
/* The c++ function for: addDependencies(p:Explanation) [] */
OID  palm_addDependencies_Explanation(Explanation *p)
{ GC_BIND;
  { OID Result = 0;
    { OID gc_local;
      ITERATE(g0229);
      Result= _oid_(CFALSE);
      bag *g0229_support;
      g0229_support = GC_OBJECT(set,p->explanation);
      for (START(g0229_support); NEXT(g0229);)
      { GC_LOOP;
        palm_addDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0229),p);
        GC_UNLOOP;} 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: addDependencies(p:set[choco/AbstractConstraint],e:Explanation) [] */
OID  palm_addDependencies_set(set *p,Explanation *e)
{ { OID Result = 0;
    { ITERATE(c);
      Result= _oid_(CFALSE);
      for (START(p); NEXT(c);)
      palm_addDependency_AbstractConstraint(OBJECT(AbstractConstraint,c),e);
      } 
    return (Result);} 
  } 


/* The c++ function for: removeDependencies(p:Explanation,removed:choco/AbstractConstraint) [] */
OID  palm_removeDependencies_Explanation(Explanation *p,AbstractConstraint *removed)
{ GC_BIND;
  { OID Result = 0;
    { OID gc_local;
      ITERATE(c);
      Result= _oid_(CFALSE);
      bag *c_support;
      c_support = GC_OBJECT(set,p->explanation);
      for (START(c_support); NEXT(c);)
      { GC_LOOP;
        if (c != _oid_(removed))
         palm_removeDependency_AbstractConstraint(OBJECT(AbstractConstraint,c),p);
        GC_UNLOOP;} 
      } 
    GC_UNBIND; return (Result);} 
  } 


// INCINF explanation
/* The c++ function for: makeIncInfExplanation(e:Explanation,oldval:integer,v:PalmIntVar) [] */
PalmIncInfExplanation * palm_makeIncInfExplanation_Explanation(Explanation *e,int oldval,PalmIntVar *v)
{ GC_BIND;
  { PalmIncInfExplanation *Result ;
    { PalmIncInfExplanation * iie;
      { { PalmIncInfExplanation * _CL_obj = ((PalmIncInfExplanation *) GC_OBJECT(PalmIncInfExplanation,new_object_class(palm._PalmIncInfExplanation)));
          (_CL_obj->explanation = e->explanation);
          (_CL_obj->previousValue = oldval);
          (_CL_obj->variable = v);
          iie = _CL_obj;
          } 
        GC_OBJECT(PalmIncInfExplanation,iie);} 
      { OID gc_local;
        ITERATE(g0230);
        bag *g0230_support;
        g0230_support = GC_OBJECT(set,iie->explanation);
        for (START(g0230_support); NEXT(g0230);)
        { GC_LOOP;
          palm_addDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0230),iie);
          GC_UNLOOP;} 
        } 
      Result = iie;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: self_print(e:PalmIncInfExplanation) [] */
OID  claire_self_print_PalmIncInfExplanation_palm(PalmIncInfExplanation *e)
{ GC_BIND;
  princ_string(e->variable->name);
  princ_string(".inf > ");
  print_any(e->previousValue);
  princ_string(" because ");
  print_any(GC_OID(_oid_(e->explanation)));
  { OID Result = 0;
    princ_string("");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: postUndoRemoval(exp:PalmIncInfExplanation,removed:choco/AbstractConstraint) [] */
void  palm_postUndoRemoval_PalmIncInfExplanation_palm(PalmIncInfExplanation *exp,AbstractConstraint *removed)
{ GC_BIND;
  { OID gc_local;
    ITERATE(g0231);
    bag *g0231_support;
    g0231_support = GC_OBJECT(set,exp->explanation);
    for (START(g0231_support); NEXT(g0231);)
    { GC_LOOP;
      if (g0231 != _oid_(removed))
       palm_removeDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0231),exp);
      GC_UNLOOP;} 
    } 
  palm_restoreInf_PalmIntVar(GC_OBJECT(PalmIntVar,exp->variable),exp->previousValue);
  GC_UNBIND;} 


// DECSUP explanation
/* The c++ function for: makeDecSupExplanation(e:Explanation,oldval:integer,v:PalmIntVar) [] */
PalmDecSupExplanation * palm_makeDecSupExplanation_Explanation(Explanation *e,int oldval,PalmIntVar *v)
{ GC_BIND;
  { PalmDecSupExplanation *Result ;
    { PalmDecSupExplanation * iie;
      { { PalmDecSupExplanation * _CL_obj = ((PalmDecSupExplanation *) GC_OBJECT(PalmDecSupExplanation,new_object_class(palm._PalmDecSupExplanation)));
          (_CL_obj->explanation = e->explanation);
          (_CL_obj->previousValue = oldval);
          (_CL_obj->variable = v);
          iie = _CL_obj;
          } 
        GC_OBJECT(PalmDecSupExplanation,iie);} 
      { OID gc_local;
        ITERATE(g0232);
        bag *g0232_support;
        g0232_support = GC_OBJECT(set,iie->explanation);
        for (START(g0232_support); NEXT(g0232);)
        { GC_LOOP;
          palm_addDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0232),iie);
          GC_UNLOOP;} 
        } 
      Result = iie;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: self_print(e:PalmDecSupExplanation) [] */
OID  claire_self_print_PalmDecSupExplanation_palm(PalmDecSupExplanation *e)
{ GC_BIND;
  princ_string(e->variable->name);
  princ_string(".sup < ");
  print_any(e->previousValue);
  princ_string(" because ");
  print_any(GC_OID(_oid_(e->explanation)));
  { OID Result = 0;
    princ_string("");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: postUndoRemoval(exp:PalmDecSupExplanation,removed:choco/AbstractConstraint) [] */
void  palm_postUndoRemoval_PalmDecSupExplanation_palm(PalmDecSupExplanation *exp,AbstractConstraint *removed)
{ GC_BIND;
  { OID gc_local;
    ITERATE(g0233);
    bag *g0233_support;
    g0233_support = GC_OBJECT(set,exp->explanation);
    for (START(g0233_support); NEXT(g0233);)
    { GC_LOOP;
      if (g0233 != _oid_(removed))
       palm_removeDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0233),exp);
      GC_UNLOOP;} 
    } 
  palm_restoreSup_PalmIntVar(GC_OBJECT(PalmIntVar,exp->variable),exp->previousValue);
  GC_UNBIND;} 


// *************************************************************
// *   Part 4: Value removal explanations                      *
// *************************************************************
// REMVAL explanation
/* The c++ function for: makeRemovalExplanation(e:Explanation,n:integer,v:PalmIntVar) [] */
PalmRemovalExplanation * palm_makeRemovalExplanation_Explanation(Explanation *e,int n,PalmIntVar *v)
{ GC_BIND;
  { PalmRemovalExplanation *Result ;
    { PalmRemovalExplanation * be;
      { { PalmRemovalExplanation * _CL_obj = ((PalmRemovalExplanation *) GC_OBJECT(PalmRemovalExplanation,new_object_class(palm._PalmRemovalExplanation)));
          (_CL_obj->explanation = e->explanation);
          STOREI(_CL_obj->value,n);
          (_CL_obj->variable = v);
          be = _CL_obj;
          } 
        GC_OBJECT(PalmRemovalExplanation,be);} 
      { OID gc_local;
        ITERATE(g0234);
        bag *g0234_support;
        g0234_support = GC_OBJECT(set,be->explanation);
        for (START(g0234_support); NEXT(g0234);)
        { GC_LOOP;
          palm_addDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0234),be);
          GC_UNLOOP;} 
        } 
      Result = be;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: self_print(e:PalmRemovalExplanation) [] */
OID  claire_self_print_PalmRemovalExplanation_palm(PalmRemovalExplanation *e)
{ GC_BIND;
  princ_string(e->variable->name);
  princ_string(" != ");
  print_any(e->value);
  princ_string(" because ");
  print_any(GC_OID(_oid_(e->explanation)));
  { OID Result = 0;
    princ_string("");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: postUndoRemoval(exp:PalmRemovalExplanation,removed:choco/AbstractConstraint) [] */
OID  palm_postUndoRemoval_PalmRemovalExplanation_palm(PalmRemovalExplanation *exp,AbstractConstraint *removed)
{ GC_BIND;
  ;{ OID gc_local;
    ITERATE(g0235);
    bag *g0235_support;
    g0235_support = GC_OBJECT(set,exp->explanation);
    for (START(g0235_support); NEXT(g0235);)
    { GC_LOOP;
      if (g0235 != _oid_(removed))
       palm_removeDependency_AbstractConstraint(OBJECT(AbstractConstraint,g0235),exp);
      GC_UNLOOP;} 
    } 
  palm_restoreVal_PalmIntVar(GC_OBJECT(PalmIntVar,exp->variable),exp->value);
  { OID Result = 0;
    Result = ((*(CLREAD(PalmIntDomain,exp->variable->bucket,explanationOnVal)))[(exp->value-CLREAD(PalmIntDomain,exp->variable->bucket,offset))]=CNULL);
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 5 : Handling value restorations ... Queue          *
// *************************************************************
/* The c++ function for: makePalmEngine(n:integer) [] */
PalmEngine * palm_makePalmEngine_integer(int n)
{ GC_BIND;
  { PalmEngine *Result ;
    { int  m = ((2*n)+2);
      PalmEngine * pe;
      { { PalmEngine * _CL_obj = ((PalmEngine *) GC_OBJECT(PalmEngine,new_object_class(palm._PalmEngine)));
          (_CL_obj->maxSize = n);
          pe = _CL_obj;
          } 
        GC_OBJECT(PalmEngine,pe);} 
      { ChocEngine * g0236; 
        BoundEventQueue * g0237;
        g0236 = pe;
        { BoundEventQueue * _CL_obj = ((BoundEventQueue *) GC_OBJECT(BoundEventQueue,new_object_class(choco._BoundEventQueue)));
          (_CL_obj->qsize = m);
          (_CL_obj->engine = pe);
          { BoundEventQueue * g0238; 
            list * g0239;
            g0238 = _CL_obj;
            { OID  g0240UU;
              { BoundUpdate * _CL_obj = ((BoundUpdate *) GC_OBJECT(BoundUpdate,new_object_class(choco._BoundUpdate)));
                g0240UU = _oid_(_CL_obj);
                } 
              g0239 = make_list_integer2(m,OBJECT(ClaireType,palm.BoundUpdate->value),g0240UU);
              } 
            (g0238->eventQueue = g0239);} 
          (_CL_obj->qLastRead = m);
          (_CL_obj->qLastEnqueued = m);
          g0237 = _CL_obj;
          } 
        (g0236->boundEvtQueue = g0237);} 
      { PalmEngine * g0241; 
        BoundEventQueue * g0242;
        g0241 = pe;
        { BoundEventQueue * _CL_obj = ((BoundEventQueue *) GC_OBJECT(BoundEventQueue,new_object_class(choco._BoundEventQueue)));
          (_CL_obj->qsize = m);
          (_CL_obj->engine = pe);
          { BoundEventQueue * g0243; 
            list * g0244;
            g0243 = _CL_obj;
            { OID  g0245UU;
              { BoundUpdate * _CL_obj = ((BoundUpdate *) GC_OBJECT(BoundUpdate,new_object_class(choco._BoundUpdate)));
                g0245UU = _oid_(_CL_obj);
                } 
              g0244 = make_list_integer2(m,OBJECT(ClaireType,palm.BoundUpdate->value),g0245UU);
              } 
            (g0243->eventQueue = g0244);} 
          (_CL_obj->qLastRead = m);
          (_CL_obj->qLastEnqueued = m);
          g0242 = _CL_obj;
          } 
        (g0241->boundRestEvtQueue = g0242);} 
      { ChocEngine * g0246; 
        RemovalEventQueue * g0247;
        g0246 = pe;
        { RemovalEventQueue * _CL_obj = ((RemovalEventQueue *) GC_OBJECT(RemovalEventQueue,new_object_class(choco._RemovalEventQueue)));
          (_CL_obj->qsize = (n+1));
          (_CL_obj->engine = pe);
          { RemovalEventQueue * g0248; 
            list * g0249;
            g0248 = _CL_obj;
            { OID  g0250UU;
              { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
                g0250UU = _oid_(_CL_obj);
                } 
              g0249 = make_list_integer2(m,OBJECT(ClaireType,palm.ValueRemovals->value),g0250UU);
              } 
            (g0248->eventQueue = g0249);} 
          (_CL_obj->qLastRead = (n+1));
          (_CL_obj->qLastEnqueued = (n+1));
          g0247 = _CL_obj;
          } 
        (g0246->removalEvtQueue = g0247);} 
      { PalmEngine * g0251; 
        RemovalEventQueue * g0252;
        g0251 = pe;
        { RemovalEventQueue * _CL_obj = ((RemovalEventQueue *) GC_OBJECT(RemovalEventQueue,new_object_class(choco._RemovalEventQueue)));
          (_CL_obj->qsize = (n+1));
          (_CL_obj->engine = pe);
          { RemovalEventQueue * g0253; 
            list * g0254;
            g0253 = _CL_obj;
            { OID  g0255UU;
              { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
                g0255UU = _oid_(_CL_obj);
                } 
              g0254 = make_list_integer2(m,OBJECT(ClaireType,palm.ValueRemovals->value),g0255UU);
              } 
            (g0253->eventQueue = g0254);} 
          (_CL_obj->qLastRead = (n+1));
          (_CL_obj->qLastEnqueued = (n+1));
          g0252 = _CL_obj;
          } 
        (g0251->removalRestEvtQueue = g0252);} 
      { ChocEngine * g0256; 
        InstantiationStack * g0257;
        g0256 = pe;
        { InstantiationStack * _CL_obj = ((InstantiationStack *) GC_OBJECT(InstantiationStack,new_object_class(choco._InstantiationStack)));
          (_CL_obj->qsize = n);
          (_CL_obj->engine = pe);
          { InstantiationStack * g0258; 
            list * g0259;
            g0258 = _CL_obj;
            { OID  g0260UU;
              { Instantiation * _CL_obj = ((Instantiation *) GC_OBJECT(Instantiation,new_object_class(choco._Instantiation)));
                g0260UU = _oid_(_CL_obj);
                } 
              g0259 = make_list_integer2(m,OBJECT(ClaireType,palm.Instantiation->value),g0260UU);
              } 
            (g0258->eventQueue = g0259);} 
          STOREI(_CL_obj->sLastRead,0);
          STOREI(_CL_obj->sLastPushed,0);
          g0257 = _CL_obj;
          } 
        (g0256->instEvtStack = g0257);} 
      { ChocEngine * g0261; 
        ConstAwakeEventQueue * g0262;
        g0261 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0263; 
            BipartiteSet * g0264;
            g0263 = _CL_obj;
            { OID  g0265UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0265UU = _oid_(_CL_obj);
                } 
              g0264 = choco_makeBipartiteSet_type(OBJECT(ClaireType,palm.ConstAwakeEvent->value),g0265UU);
              } 
            (g0263->partition = g0264);} 
          g0262 = _CL_obj;
          } 
        (g0261->delayedConst1 = g0262);} 
      { ChocEngine * g0266; 
        ConstAwakeEventQueue * g0267;
        g0266 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0268; 
            BipartiteSet * g0269;
            g0268 = _CL_obj;
            { OID  g0270UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0270UU = _oid_(_CL_obj);
                } 
              g0269 = choco_makeBipartiteSet_type(OBJECT(ClaireType,palm.ConstAwakeEvent->value),g0270UU);
              } 
            (g0268->partition = g0269);} 
          g0267 = _CL_obj;
          } 
        (g0266->delayedConst2 = g0267);} 
      { ChocEngine * g0271; 
        ConstAwakeEventQueue * g0272;
        g0271 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0273; 
            BipartiteSet * g0274;
            g0273 = _CL_obj;
            { OID  g0275UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0275UU = _oid_(_CL_obj);
                } 
              g0274 = choco_makeBipartiteSet_type(OBJECT(ClaireType,palm.ConstAwakeEvent->value),g0275UU);
              } 
            (g0273->partition = g0274);} 
          g0272 = _CL_obj;
          } 
        (g0271->delayedConst3 = g0272);} 
      { ChocEngine * g0276; 
        ConstAwakeEventQueue * g0277;
        g0276 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0278; 
            BipartiteSet * g0279;
            g0278 = _CL_obj;
            { OID  g0280UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0280UU = _oid_(_CL_obj);
                } 
              g0279 = choco_makeBipartiteSet_type(OBJECT(ClaireType,palm.ConstAwakeEvent->value),g0280UU);
              } 
            (g0278->partition = g0279);} 
          g0277 = _CL_obj;
          } 
        (g0276->delayedConst4 = g0277);} 
      Result = pe;
      } 
    GC_UNBIND; return (Result);} 
  } 


// **********************************************************************
// *   Part 6: Flushing and resetting event Queues upon contradiction   *
// **********************************************************************
/* The c++ function for: choco/getNextActiveEventQueue(pe:PalmEngine) [] */
OID  choco_getNextActiveEventQueue_PalmEngine_palm(PalmEngine *pe)
{ GC_BIND;
  { OID Result = 0;
    { BoundEventQueue * brq = pe->boundRestEvtQueue;
      if (brq->qLastRead != brq->qLastEnqueued)
       Result = _oid_(brq);
      else { RemovalEventQueue * rrq = pe->removalRestEvtQueue;
          if (rrq->qLastRead != rrq->qLastEnqueued)
           Result = _oid_(rrq);
          else { InstantiationStack * iq = pe->instEvtStack;
              if (iq->sLastRead != iq->sLastPushed)
               Result = _oid_(iq);
              else { BoundEventQueue * bq = pe->boundEvtQueue;
                  if (bq->qLastRead != bq->qLastEnqueued)
                   Result = _oid_(bq);
                  else { RemovalEventQueue * rq = pe->removalEvtQueue;
                      if (rq->qLastRead != rq->qLastEnqueued)
                       Result = _oid_(rq);
                      else { ConstAwakeEventQueue * cq1 = pe->delayedConst1;
                          if (choco_isEmpty_ConstAwakeEventQueue(cq1) != CTRUE)
                           Result = _oid_(cq1);
                          else { ConstAwakeEventQueue * cq2 = pe->delayedConst2;
                              if (choco_isEmpty_ConstAwakeEventQueue(cq2) != CTRUE)
                               Result = _oid_(cq2);
                              else { ConstAwakeEventQueue * cq3 = pe->delayedConst3;
                                  if (choco_isEmpty_ConstAwakeEventQueue(cq3) != CTRUE)
                                   Result = _oid_(cq3);
                                  else { ConstAwakeEventQueue * cq4 = pe->delayedConst4;
                                      if (choco_isEmpty_ConstAwakeEventQueue(cq4) != CTRUE)
                                       Result = _oid_(cq4);
                                      else Result = CNULL;
                                        } 
                                    } 
                                } 
                            } 
                        } 
                    } 
                } 
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// Il y a eu une contradiction, il me reste des choses à propager, il faut
// absolument le faire. Comme je ne sais plus trop qui est en place ou pas,
// on remet toutes les causes des évènements qui restent à zéro
// et c'est tout ce que l'on fait !
/* The c++ function for: resetEvent(evt:choco/BoundUpdate) [] */
void  palm_resetEvent_BoundUpdate(BoundUpdate *evt)
{ (evt->cause = 0);
  } 


/* The c++ function for: resetEvent(evt:choco/ValueRemovals) [] */
void  palm_resetEvent_ValueRemovals(ValueRemovals *evt)
{ { int  k = 1;
    int  g0281 = evt->nbVals;
    { while ((k <= g0281))
      { ((*(evt->causeStack))[k]=0);
        ++k;
        } 
      } 
    } 
  } 


/* The c++ function for: resetPoppingQueue(q:choco/BoundEventQueue) [] */
void  palm_resetPoppingQueue_BoundEventQueue(BoundEventQueue *q)
{ { list * eq = q->eventQueue;
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    (q->redundantEvent = CFALSE);
    if (q->isPopping == CTRUE)
     { OID  evt = (*(eq))[i];
      if (OBJECT(BoundUpdate,evt)->idxInQueue < 0)
       { (OBJECT(BoundUpdate,evt)->cause = 0);
        (OBJECT(BoundUpdate,evt)->idxInQueue = i);
        (q->qLastRead = ((q->qLastRead == 1) ?
          q->qsize :
          (q->qLastRead-1) ));
        } 
      (q->isPopping = CFALSE);
      } 
    } 
  } 


/* The c++ function for: resetPoppingQueue(q:choco/RemovalEventQueue) [] */
void  palm_resetPoppingQueue_RemovalEventQueue(RemovalEventQueue *q)
{ { list * eq = q->eventQueue;
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    if (q->isPopping == CTRUE)
     { OID  evt = (*(eq))[i];
      (OBJECT(ValueRemovals,evt)->nbVals = (OBJECT(ValueRemovals,evt)->nbVals+1));
      { int  k = 1;
        int  g0282 = OBJECT(ValueRemovals,evt)->nbVals;
        { while ((k <= g0282))
          { ((*(OBJECT(ValueRemovals,evt)->causeStack))[k]=0);
            ++k;
            } 
          } 
        } 
      (OBJECT(ValueRemovals,evt)->idxInQueue = i);
      (q->qLastRead = ((q->qLastRead == 1) ?
        q->qsize :
        (q->qLastRead-1) ));
      (q->isPopping = CFALSE);
      } 
    } 
  } 


/* The c++ function for: resetEventQueue(q:choco/EventQueue) [] */
void  palm_resetEventQueue_EventQueue(EventQueue *q)
{ GC_BIND;
  { OID  eq = GC_OID((*choco.eventQueue)(_oid_(q)));
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    (*palm.resetPoppingQueue)(_oid_(q));
    if (i != j)
     { ++i;
      if (i > q->qsize)
       i= 1;
      if (i <= j)
       { int  k = i;
        int  g0283 = j;
        { OID gc_local;
          while ((k <= g0283))
          { // HOHO, GC_LOOP not needed !
            { VarEvent * evt = OBJECT(VarEvent,(*(OBJECT(bag,eq)))[k]);
              _void_((INHERIT(evt->isa,choco._BoundUpdate) ?  palm_resetEvent_BoundUpdate((BoundUpdate *) OBJECT(BoundUpdate,_oid_(evt))) :   palm_resetEvent_ValueRemovals((ValueRemovals *) OBJECT(ValueRemovals,_oid_(evt)))));
              } 
            ++k;
            } 
          } 
        } 
      else { { int  k = i;
            int  g0284 = q->qsize;
            { OID gc_local;
              while ((k <= g0284))
              { // HOHO, GC_LOOP not needed !
                { VarEvent * evt = OBJECT(VarEvent,(*(OBJECT(bag,eq)))[k]);
                  _void_((INHERIT(evt->isa,choco._BoundUpdate) ?  palm_resetEvent_BoundUpdate((BoundUpdate *) OBJECT(BoundUpdate,_oid_(evt))) :   palm_resetEvent_ValueRemovals((ValueRemovals *) OBJECT(ValueRemovals,_oid_(evt)))));
                  } 
                ++k;
                } 
              } 
            } 
          { int  k = 1;
            int  g0285 = j;
            { OID gc_local;
              while ((k <= g0285))
              { // HOHO, GC_LOOP not needed !
                { VarEvent * evt = OBJECT(VarEvent,(*(OBJECT(bag,eq)))[k]);
                  _void_((INHERIT(evt->isa,choco._BoundUpdate) ?  palm_resetEvent_BoundUpdate((BoundUpdate *) OBJECT(BoundUpdate,_oid_(evt))) :   palm_resetEvent_ValueRemovals((ValueRemovals *) OBJECT(ValueRemovals,_oid_(evt)))));
                  } 
                ++k;
                } 
              } 
            } 
          } 
        } 
    } 
  GC_UNBIND;} 


// resetting a stable state for the current problem 
/* The c++ function for: resetEvents(pb:PalmEngine) [] */
void  palm_resetEvents_PalmEngine(PalmEngine *pb)
{ GC_BIND;
  palm_resetEventQueue_EventQueue(GC_OBJECT(BoundEventQueue,pb->boundEvtQueue));
  palm_resetEventQueue_EventQueue(GC_OBJECT(BoundEventQueue,pb->boundRestEvtQueue));
  palm_resetEventQueue_EventQueue(GC_OBJECT(RemovalEventQueue,pb->removalEvtQueue));
  palm_resetEventQueue_EventQueue(GC_OBJECT(RemovalEventQueue,pb->removalRestEvtQueue));
  GC_UNBIND;} 


/* The c++ function for: flushEvents(pb:PalmEngine) [] */
void  palm_flushEvents_PalmEngine(PalmEngine *pb)
{ GC_BIND;
  choco_flushEventQueue_BoundEventQueue(GC_OBJECT(BoundEventQueue,pb->boundEvtQueue));
  choco_flushEventQueue_BoundEventQueue(GC_OBJECT(BoundEventQueue,pb->boundRestEvtQueue));
  choco_flushEventQueue_RemovalEventQueue(GC_OBJECT(RemovalEventQueue,pb->removalEvtQueue));
  choco_flushEventQueue_RemovalEventQueue(GC_OBJECT(RemovalEventQueue,pb->removalRestEvtQueue));
  GC_UNBIND;} 


// **********************************************************************
// *   Part 7: Handling contradictions                                  *
// **********************************************************************
// Handling localized contradictions ... 
/* The c++ function for: raisePalmContradiction(pe:PalmEngine,v:PalmIntVar) [] */
void  palm_raisePalmContradiction_PalmEngine(PalmEngine *pe,PalmIntVar *v)
{ (pe->contradictionCause = v);
  (pe->contradictory = CTRUE);
  palm_resetEvents_PalmEngine(pe);
  close_exception((ClaireException *) new_object_class(palm._PalmContradiction));
  } 


// Raising a Palm contradiction (to be handled automatically)
// Handling non really localized contradiction ...
/* The c++ function for: raisePalmFakeContradiction(pe:PalmEngine,e:Explanation) [] */
void  palm_raisePalmFakeContradiction_PalmEngine(PalmEngine *pe,Explanation *e)
{ GC_BIND;
  if (pe->dummyVariable == (NULL))
   { (pe->dummyVariable = palm_makeBoundIntVar_PalmProblem(GC_OBJECT(PalmProblem,((PalmProblem *) pe->problem)),"*dummy*",0,1));
    delete_bag(pe->problem->vars,_oid_(pe->dummyVariable));
    } 
  (*choco.updateInf)(GC_OID(_oid_(pe->dummyVariable)),
    2,
    0,
    _oid_(e));
  GC_UNBIND;} 


// No possible solution => pass the contradiction to choco
/* The c++ function for: raiseSystemContradiction(pe:PalmEngine) [] */
void  palm_raiseSystemContradiction_PalmEngine(PalmEngine *pe)
{ (pe->contradictory = CFALSE);
  palm_flushEvents_PalmEngine(pe);
  contradiction_I_void();
  } 


// *************************************************************
// *   Part 8: Removing constraints                            *
// *************************************************************
// Removing a single constraint
/* The c++ function for: remove(pe:PalmEngine,c:choco/AbstractConstraint) [] */
void  palm_remove_PalmEngine1(PalmEngine *pe,AbstractConstraint *c)
{ choco_setPassive_AbstractConstraint(c);
  palm_undo_AbstractConstraint(c);
  palm_restoreVariableExplanations_PalmEngine(pe);
  } 


// Removing a list of constraints in one single step 
/* The c++ function for: remove(pe:PalmEngine,cl:list[choco/AbstractConstraint]) [] */
void  palm_remove_PalmEngine2(PalmEngine *pe,list *cl)
{ { ITERATE(c);
    for (START(cl); NEXT(c);)
    { choco_setPassive_AbstractConstraint(OBJECT(AbstractConstraint,c));
      palm_undo_AbstractConstraint(OBJECT(AbstractConstraint,c));
      } 
    } 
  palm_restoreVariableExplanations_PalmEngine(pe);
  } 


// Updating variable explanations in order to reflect the current situation
/* The c++ function for: restoreVariableExplanations(pe:PalmEngine) [] */
OID  palm_restoreVariableExplanations_PalmEngine(PalmEngine *pe)
{ GC_BIND;
  { BoundEventQueue * q = GC_OBJECT(BoundEventQueue,pe->boundRestEvtQueue);
    list * eq = GC_OBJECT(list,q->eventQueue);
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    if (i != j)
     { ++i;
      if (i > q->qsize)
       i= 1;
      if (i <= j)
       { int  k = i;
        int  g0286 = j;
        { OID gc_local;
          while ((k <= g0286))
          { // HOHO, GC_LOOP not needed !
            { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
              (*palm.restoreVariableExplanation)(_oid_(evt));
              } 
            ++k;
            } 
          } 
        } 
      else { { int  k = i;
            int  g0287 = q->qsize;
            { OID gc_local;
              while ((k <= g0287))
              { // HOHO, GC_LOOP not needed !
                { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
                  (*palm.restoreVariableExplanation)(_oid_(evt));
                  } 
                ++k;
                } 
              } 
            } 
          { int  k = 1;
            int  g0288 = j;
            { OID gc_local;
              while ((k <= g0288))
              { // HOHO, GC_LOOP not needed !
                { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
                  (*palm.restoreVariableExplanation)(_oid_(evt));
                  } 
                ++k;
                } 
              } 
            } 
          } 
        } 
    } 
  { OID Result = 0;
    { RemovalEventQueue * q = GC_OBJECT(RemovalEventQueue,pe->removalRestEvtQueue);
      list * eq = GC_OBJECT(list,q->eventQueue);
      int  i = q->qLastRead;
      int  j = q->qLastEnqueued;
      if (i != j)
       { ++i;
        if (i > q->qsize)
         i= 1;
        if (i <= j)
         { int  k = i;
          int  g0289 = j;
          { OID gc_local;
            Result= _oid_(CFALSE);
            while ((k <= g0289))
            { GC_LOOP;
              palm_checkVariableDomainAgainstRemValEvt_PalmIntVar(GC_OBJECT(PalmIntVar,((PalmIntVar *) OBJECT(VarEvent,(*(eq))[k])->modifiedVar)));
              ++k;
              GC_UNLOOP;} 
            } 
          } 
        else { { int  k = i;
              int  g0290 = q->qsize;
              { OID gc_local;
                while ((k <= g0290))
                { GC_LOOP;
                  palm_checkVariableDomainAgainstRemValEvt_PalmIntVar(GC_OBJECT(PalmIntVar,((PalmIntVar *) OBJECT(VarEvent,(*(eq))[k])->modifiedVar)));
                  ++k;
                  GC_UNLOOP;} 
                } 
              } 
            { int  k = 1;
              int  g0291 = j;
              { OID gc_local;
                Result= _oid_(CFALSE);
                while ((k <= g0291))
                { GC_LOOP;
                  palm_checkVariableDomainAgainstRemValEvt_PalmIntVar(GC_OBJECT(PalmIntVar,((PalmIntVar *) OBJECT(VarEvent,(*(eq))[k])->modifiedVar)));
                  ++k;
                  GC_UNLOOP;} 
                } 
              } 
            } 
          } 
      else Result = Kernel.cfalse;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkVariableDomainAgainstRemValEvt(v:PalmIntVar) [] */
OID  palm_checkVariableDomainAgainstRemValEvt_PalmIntVar(PalmIntVar *v)
{ GC_BIND;
  ;{ OID Result = 0;
    { ValueRemovals * ev = GC_OBJECT(ValueRemovals,v->remValEvt);
      int  idx = 1;
      PalmIntDomain * buck = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) v->bucket));
      { Result= _oid_(CFALSE);
        while ((idx <= ev->nbVals))
        { { int  val = (*(ev->valueStack))[idx];
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) buck),((int) val))))) == CTRUE)
             { ((*(ev->valueStack))[idx]=(*(ev->valueStack))[ev->nbVals]);
              ((*(ev->causeStack))[idx]=(*(ev->causeStack))[ev->nbVals]);
              (ev->nbVals = (ev->nbVals-1));
              } 
            else ++idx;
              } 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/propagateEvent(e:DecInf,q:choco/BoundEventQueue) [] */
void  choco_propagateEvent_DecInf_palm(DecInf *e,BoundEventQueue *q)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    int  n = v->nbConstraints;
    list * lc = GC_OBJECT(list,v->constraints);
    list * li = GC_OBJECT(list,v->indices);
    (q->isPopping = CTRUE);
    if (n == 0)
     (e->idxInQueue = 0);
    else { list * lnext = GC_OBJECT(list,e->nextConst);
        int  i1 = e->cause;
        int  prevk = i1;
        int  k = ((i1 == 0) ?
          (*(lnext))[n] :
          (*(lnext))[i1] );
        int  i0 = ((i1 <= 1) ?
          n :
          (i1-1) );
        (e->idxInQueue = 0);
        { while ((k > prevk))
          { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
            prevk= k;
            k= (*(lnext))[k];
            } 
          } 
        { while ((k != (*(lnext))[i0]))
          { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
            k= (*(lnext))[k];
            } 
          } 
        } 
      (q->isPopping = CFALSE);
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/propagateEvent(e:IncSup,q:choco/BoundEventQueue) [] */
void  choco_propagateEvent_IncSup_palm(IncSup *e,BoundEventQueue *q)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    int  n = v->nbConstraints;
    list * lc = GC_OBJECT(list,v->constraints);
    list * li = GC_OBJECT(list,v->indices);
    (q->isPopping = CTRUE);
    if (n == 0)
     (e->idxInQueue = 0);
    else { list * lnext = GC_OBJECT(list,e->nextConst);
        int  i1 = e->cause;
        int  prevk = i1;
        int  k = ((i1 == 0) ?
          (*(lnext))[n] :
          (*(lnext))[i1] );
        int  i0 = ((i1 <= 1) ?
          n :
          (i1-1) );
        (e->idxInQueue = 0);
        { while ((k > prevk))
          { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
            prevk= k;
            k= (*(lnext))[k];
            } 
          } 
        { while ((k != (*(lnext))[i0]))
          { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
            k= (*(lnext))[k];
            } 
          } 
        } 
      (q->isPopping = CFALSE);
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/propagateEvent(e:ValueRestorations,q:choco/RemovalEventQueue) [] */
void  choco_propagateEvent_ValueRestorations_palm(ValueRestorations *e,RemovalEventQueue *q)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    int  n = v->nbConstraints;
    list * lc = GC_OBJECT(list,v->constraints);
    list * li = GC_OBJECT(list,v->indices);
    (e->idxInQueue = 0);
    if (n > 0)
     { list * lnext = GC_OBJECT(list,e->nextConst);
      { while ((e->nbVals > 0))
        { { int  nbv = e->nbVals;
            int  i1 = (*(e->causeStack))[nbv];
            int  prevk = i1;
            int  k = ((i1 == 0) ?
              (*(lnext))[n] :
              (*(lnext))[i1] );
            int  i0 = ((i1 <= 1) ?
              n :
              (i1-1) );
            (e->nbVals = (e->nbVals-1));
            if (k > 0)
             { (q->isPopping = CTRUE);
              if (e->many == CTRUE)
               { { while ((k > prevk))
                  { close_exception(((general_error *) (*Core._general_error)(_string_("PALM multiple value restorations not handled in p-explain.cl"),
                      _oid_(Kernel.nil))));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                { while ((k != (*(lnext))[i0]))
                  { close_exception(((general_error *) (*Core._general_error)(_string_("PALM multiple value restorations not handled in p-explain.cl"),
                      _oid_(Kernel.nil))));
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              else { int  x = (*(e->valueStack))[nbv];
                  { while ((k > prevk))
                    { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k]),((int) x)));
                      prevk= k;
                      k= (*(lnext))[k];
                      } 
                    } 
                  { while ((k != (*(lnext))[i0]))
                    { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k]),((int) x)));
                      k= (*(lnext))[k];
                      } 
                    } 
                  } 
                (q->isPopping = CFALSE);
              } 
            } 
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// *************************************************************
// *   Part 9: Explanation Generation API                      *
// *************************************************************
// This part was asked by THB 
/* The c++ function for: becauseOf(causes:listargs) [] */
Explanation * palm_becauseOf_listargs(listargs *causes)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      { OID gc_local;
        ITERATE(c);
        for (START(causes); NEXT(c);)
        { GC_LOOP;
          if (INHERIT(OWNER(c),palm._Explanation))
           { OID gc_local;
            ITERATE(g0292);
            bag *g0292_support;
            g0292_support = GC_OBJECT(set,OBJECT(Explanation,c)->explanation);
            for (START(g0292_support); NEXT(g0292);)
            { GC_LOOP;
              GC_OBJECT(set,e->explanation)->addFast(g0292);
              GC_UNLOOP;} 
            } 
          else if (INHERIT(OWNER(c),choco._AbstractConstraint))
           palm_self_explain_AbstractConstraint(OBJECT(AbstractConstraint,c),e);
          else if (INHERIT(OWNER(c),palm._PalmIntVar))
           palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,c),4,e);
          else if (INHERIT(OWNER(c),Kernel._tuple))
           { if (INHERIT(OWNER((*(OBJECT(bag,c)))[1]),choco._AbstractConstraint))
             { OID gc_local;
              ITERATE(v);
              bag *v_support;
              v_support = GC_OBJECT(list,OBJECT(bag,(*Core.vars)((*(OBJECT(bag,c)))[1])));
              for (START(v_support); NEXT(v);)
              { GC_LOOP;
                palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,v),4,e);
                GC_UNLOOP;} 
              } 
            else if (INHERIT(OWNER((*(OBJECT(bag,c)))[1]),palm._PalmIntVar))
             { if (OBJECT(bag,c)->length == 2)
               palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,(*(OBJECT(bag,c)))[1]),(*(OBJECT(bag,c)))[2],e);
              else palm_self_explain_PalmIntVar2(OBJECT(PalmIntVar,(*(OBJECT(bag,c)))[1]),3,(*(OBJECT(bag,c)))[3],e);
                } 
            else close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: typing error in becauseOf (p-explain.cl)"),
                _oid_(Kernel.nil))));
              } 
          else close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: typing error in becauseOf (p-explain.cl)"),
              _oid_(Kernel.nil))));
            GC_UNLOOP;} 
        } 
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: theInf(g0293:PalmIntVar) [] */
tuple * palm_theInf_PalmIntVar_(PalmIntVar *g0293)
{ return palm_theInf_PalmIntVar(g0293)->copyIfNeeded();} 


/* The c++ function for: theInf(v:PalmIntVar) [] */
tuple * palm_theInf_PalmIntVar(PalmIntVar *v)
{ { tuple *Result ;
    Result = tuple::allocStack(2,_oid_(v),1);
    return (Result);} 
  } 


/* The c++ function for: theSup(g0294:PalmIntVar) [] */
tuple * palm_theSup_PalmIntVar_(PalmIntVar *g0294)
{ return palm_theSup_PalmIntVar(g0294)->copyIfNeeded();} 


/* The c++ function for: theSup(v:PalmIntVar) [] */
tuple * palm_theSup_PalmIntVar(PalmIntVar *v)
{ { tuple *Result ;
    Result = tuple::allocStack(2,_oid_(v),2);
    return (Result);} 
  } 


/* The c++ function for: theDom(g0295:PalmIntVar) [] */
tuple * palm_theDom_PalmIntVar_(PalmIntVar *g0295)
{ return palm_theDom_PalmIntVar(g0295)->copyIfNeeded();} 


/* The c++ function for: theDom(v:PalmIntVar) [] */
tuple * palm_theDom_PalmIntVar(PalmIntVar *v)
{ { tuple *Result ;
    Result = tuple::allocStack(2,_oid_(v),4);
    return (Result);} 
  } 


/* The c++ function for: theHole(g0296:PalmIntVar,g0297:integer) [] */
tuple * palm_theHole_PalmIntVar_(PalmIntVar *g0296,int g0297)
{ return palm_theHole_PalmIntVar(g0296,g0297)->copyIfNeeded();} 


/* The c++ function for: theHole(v:PalmIntVar,x:integer) [] */
tuple * palm_theHole_PalmIntVar(PalmIntVar *v,int x)
{ { tuple *Result ;
    Result = tuple::allocStack(3,_oid_(v),
      3,
      x);
    return (Result);} 
  } 


/* The c++ function for: theVars(g0298:choco/AbstractConstraint) [] */
tuple * palm_theVars_AbstractConstraint_(AbstractConstraint *g0298)
{ return palm_theVars_AbstractConstraint(g0298)->copyIfNeeded();} 


/* The c++ function for: theVars(c:choco/AbstractConstraint) [] */
tuple * palm_theVars_AbstractConstraint(AbstractConstraint *c)
{ { tuple *Result ;
    Result = tuple::allocStack(1,_oid_(c));
    return (Result);} 
  } 


// explaining a current state 
/* The c++ function for: explain(v:PalmIntVar,s:{1, 2, 3, 4}) [] */
Explanation * palm_explain_PalmIntVar1(PalmIntVar *v,int s)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar1(v,s,e);
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: explain(v:PalmIntVar,s:{1, 2, 3, 4},val:integer) [] */
Explanation * palm_explain_PalmIntVar2(PalmIntVar *v,int s,int val)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar2(v,s,val,e);
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: explain(c:choco/AbstractConstraint) [] */
Explanation * palm_explain_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: explain(pb:PalmProblem) [] */
Explanation * palm_explain_PalmProblem(PalmProblem *pb)
{ GC_BIND;
  { Explanation *Result ;
    { PropagationEngine * pe = GC_OBJECT(PropagationEngine,pb->propagationEngine);
      if (CLREAD(PalmEngine,pe,contradictory) == CTRUE)
       { Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        Ephemeral * fv = GC_OBJECT(Ephemeral,pe->contradictionCause);
        palm_self_explain_PalmIntVar1(((PalmIntVar *) fv),4,e);
        Result = e;
        } 
      else { close_exception(((general_error *) (*Core._general_error)(_string_("explain@PalmProblem should only be used for over-constrained problems"),
            _oid_(Kernel.nil))));
          { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            Result = _CL_obj;
            } 
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

