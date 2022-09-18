/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-variables.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:38 2003 *****/

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
// Part 1 : Tools and basic stuff 
// Part 2 : Data Structure maintenance
// Part 3 : Domain restoration
// Part 4 : Domain modification
// *************************************************************
// *   Part 1: Tools and basic stuff                           *
// *************************************************************
// printing ... 
/* The c++ function for: self_print(t:PalmIntVar) [] */
OID  claire_self_print_PalmIntVar_palm(PalmIntVar *t)
{ if (t->bucket != (NULL)) 
  { { OID Result = 0;
      { if (t->name != (NULL))
         { princ_string(t->name);
          princ_string(":");
          } 
        else princ_string("<IntVar>");
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) t))))) == CTRUE)
         { print_any(t->value);
          princ_string("");
          } 
        else { princ_string("[");
            print_any(CLREAD(PalmIntDomain,t->bucket,nbElements));
            princ_string("]");
            print_any(GC_OID(_oid_(t->bucket)));
            princ_string("");
            } 
          } 
      return (Result);} 
     } 
  else{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) t))))) == CTRUE) 
    { { OID Result = 0;
        { princ_string(t->name);
          princ_string(":");
          print_any(t->value);
          princ_string("");
          } 
        return (Result);} 
       } 
    else{ if (t->inf->latestValue > t->sup->latestValue) 
      { { OID Result = 0;
          { princ_string(t->name);
            princ_string(":[]");
            } 
          return (Result);} 
         } 
      else{ GC_BIND;
        princ_string(t->name);
        princ_string(":[");
        print_any(t->inf->latestValue);
        princ_string("..");
        print_any(t->sup->latestValue);
        { OID Result = 0;
          princ_string("]");
          GC_UNBIND; return (Result);} 
        } 
      } 
    } 
  } 


// accessing the lower bound 
/* The c++ function for: choco/getInf(v:PalmIntVar) [] */
int  choco_getInf_PalmIntVar_palm(PalmIntVar *v)
{ GC_BIND;
  if ((v->bucket != (NULL)) && 
      (CLREAD(PalmIntDomain,v->bucket,needInfComputation) == CTRUE))
   { claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket))))));
    (CLREAD(PalmIntDomain,v->bucket,needInfComputation) = CFALSE);
    } 
  { int Result = 0;
    Result = v->inf->latestValue;
    GC_UNBIND; return (Result);} 
  } 


// accessing the upper bound 
/* The c++ function for: choco/getSup(v:PalmIntVar) [] */
int  choco_getSup_PalmIntVar_palm(PalmIntVar *v)
{ GC_BIND;
  if ((v->bucket != (NULL)) && 
      (CLREAD(PalmIntDomain,v->bucket,needSupComputation) == CTRUE))
   { claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket))))));
    (CLREAD(PalmIntDomain,v->bucket,needSupComputation) = CFALSE);
    } 
  { int Result = 0;
    Result = v->sup->latestValue;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: self_explain(v:PalmIntVar,s:{1, 2, 3, 4},e:Explanation) [] */
void  palm_self_explain_PalmIntVar1(PalmIntVar *v,int s,Explanation *e)
{ if (v->bucket != (NULL)) 
  { if (s == 4)
     { int  x = (CLREAD(PalmIntDomain,v->bucket,offset)+1);
      int  g0023 = (CLREAD(PalmIntDomain,v->bucket,offset)+CLREAD(PalmIntDomain,v->bucket,bucketSize));
      { OID gc_local;
        while ((x <= g0023))
        { // HOHO, GC_LOOP not needed !
          palm_self_explain_PalmIntVar2(v,3,x,e);
          ++x;
          } 
        } 
      } 
    else if (s == 1)
     { int  x = (CLREAD(PalmIntDomain,v->bucket,offset)+1);
      int  g0024 = ((choco.getInf->fcall(((int) v)))-1);
      { OID gc_local;
        while ((x <= g0024))
        { // HOHO, GC_LOOP not needed !
          palm_self_explain_PalmIntVar2(v,3,x,e);
          ++x;
          } 
        } 
      } 
    else if (s == 2)
     { int  x = ((choco.getSup->fcall(((int) v)))+1);
      int  g0025 = (CLREAD(PalmIntDomain,v->bucket,offset)+CLREAD(PalmIntDomain,v->bucket,bucketSize));
      { OID gc_local;
        while ((x <= g0025))
        { // HOHO, GC_LOOP not needed !
          palm_self_explain_PalmIntVar2(v,3,x,e);
          ++x;
          } 
        } 
      } 
    else if (s == 3)
     close_exception(((general_error *) (*Core._general_error)(_string_("PaLM : VAL needs another parameter in self_explain (p-variables.cl)"),
      _oid_(Kernel.nil))));
     } 
  else{ GC_BIND;
    if (s == 1)
     { OID gc_local;
      ITERATE(g0026);
      bag *g0026_support;
      g0026_support = GC_OBJECT(set,OBJECT(Explanation,(*(v->explanationOnInf))[v->explanationOnInf->length])->explanation);
      for (START(g0026_support); NEXT(g0026);)
      { GC_LOOP;
        GC_OBJECT(set,e->explanation)->addFast(g0026);
        GC_UNLOOP;} 
      } 
    else if (s == 2)
     { OID gc_local;
      ITERATE(g0027);
      bag *g0027_support;
      g0027_support = GC_OBJECT(set,OBJECT(Explanation,(*(v->explanationOnSup))[v->explanationOnSup->length])->explanation);
      for (START(g0027_support); NEXT(g0027);)
      { GC_LOOP;
        GC_OBJECT(set,e->explanation)->addFast(g0027);
        GC_UNLOOP;} 
      } 
    else if (s == 4)
     { palm_self_explain_PalmIntVar1(v,1,e);
      palm_self_explain_PalmIntVar1(v,2,e);
      } 
    else if (s == 3)
     close_exception(((general_error *) (*Core._general_error)(_string_("PaLM : VAL needs another parameter in self_explain (p-variables.cl)"),
      _oid_(Kernel.nil))));
    GC_UNBIND;} 
  } 


/* The c++ function for: self_explain(v:PalmIntVar,s:{1, 2, 3, 4},x:integer,e:Explanation) [] */
void  palm_self_explain_PalmIntVar2(PalmIntVar *v,int s,int x,Explanation *e)
{ if (s == 3) 
  { if (v->bucket != (NULL))
     { int  realVal = (x-CLREAD(PalmIntDomain,v->bucket,offset));
      OID  explSet;
      if ((realVal > 0) && 
          (realVal <= CLREAD(PalmIntDomain,v->bucket,explanationOnVal)->length))
       explSet = (*(CLREAD(PalmIntDomain,v->bucket,explanationOnVal)))[(x-CLREAD(PalmIntDomain,v->bucket,offset))];
      else explSet = CNULL;
        if (explSet != CNULL)
       { OID gc_local;
        ITERATE(g0028);
        for (START(OBJECT(Explanation,explSet)->explanation); NEXT(g0028);)
        { GC_LOOP;
          e->explanation->addFast(g0028);
          GC_UNLOOP;} 
        } 
      } 
    else if (x < v->inf->latestValue)
     { OID gc_local;
      ITERATE(g0029);
      bag *g0029_support;
      g0029_support = OBJECT(Explanation,(*(v->explanationOnInf))[v->explanationOnInf->length])->explanation;
      for (START(g0029_support); NEXT(g0029);)
      { GC_LOOP;
        e->explanation->addFast(g0029);
        GC_UNLOOP;} 
      } 
    else if (x > v->sup->latestValue)
     { OID gc_local;
      ITERATE(g0030);
      bag *g0030_support;
      g0030_support = OBJECT(Explanation,(*(v->explanationOnSup))[v->explanationOnSup->length])->explanation;
      for (START(g0030_support); NEXT(g0030);)
      { GC_LOOP;
        e->explanation->addFast(g0030);
        GC_UNLOOP;} 
      } 
     } 
  else{ GC_BIND;
    close_exception(((general_error *) (*Core._general_error)(_string_("PaLM : INF, SUP or DOM do not need a supplementary parameter in self_explain (p-variables.cl)"),
      _oid_(Kernel.nil))));
    GC_UNBIND;} 
  } 


// FirstValue in the domain
/* The c++ function for: firstValue(v:PalmIntVar) [] */
int  palm_firstValue_PalmIntVar(PalmIntVar *v)
{ if (v->bucket != (NULL)) 
  { { int Result = 0;
      Result = palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) v->bucket)));
      return (Result);} 
     } 
  else{ GC_BIND;
    { int Result = 0;
      Result = v->inf->latestValue;
      GC_UNBIND; return (Result);} 
    } 
  } 


// *************************************************************
// *   Part 2 : Data Structure maintenance                     *
// *************************************************************
// The general purpose method 
/* The c++ function for: updateDataStructures(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructures_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue)
{ (*palm.updateDataStructuresOnVariable)(_oid_(v),
    way,
    newValue,
    oldValue);
  palm_updateDataStructuresOnConstraints_PalmIntVar(v,way,newValue,oldValue);
  } 


// Handling Data Structure maintenance
/* The c++ function for: updateDataStructuresOnVariable(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnVariable_PalmIntVar_palm(PalmIntVar *v,int way,int newValue,int oldValue)
{ ;} 


// to be redefined for specific variables 
/* The c++ function for: updateDataStructuresOnConstraints(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraints_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue)
{ GC_BIND;
  { VarEvent * evt;
    { if (way == 1)
       evt = v->updtInfEvt;
      else if (way == 2)
       evt = v->updtSupEvt;
      else evt = v->remValEvt;
        GC_OBJECT(VarEvent,evt);} 
    list * lnext = evt->nextConst;
    list * li = v->indices;
    list * lc = v->constraints;
    int  n = v->nbConstraints;
    int  k = ((n != 0) ?
      (*(lnext))[n] :
      0 );
    int  end = n;
    if (k != 0)
     { ClaireBoolean *v_while;
      v_while = CFALSE;
      
      while (v_while != CTRUE)
      { (*palm.updateDataStructuresOnConstraint)((*(lc))[k],
          (*(li))[k],
          way,
          newValue,
          oldValue);
        k= (*(lnext))[k];
        v_while = ((k == (*(lnext))[end]) ? CTRUE : CFALSE);
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnConstraint(c:choco/AbstractConstraint,idx:integer,way:{1, 2, 3, 4},v:integer,nextValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_AbstractConstraint_palm(AbstractConstraint *c,int idx,int way,int v,int nextValue)
{ ;} 


// The general purpose method 
/* The c++ function for: updateDataStructuresOnRestore(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestore_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue)
{ (*palm.updateDataStructuresOnRestoreVariable)(_oid_(v),
    way,
    newValue,
    oldValue);
  palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(v,way,newValue,oldValue);
  } 


/* The c++ function for: updateDataStructuresOnRestoreVariable(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreVariable_PalmIntVar_palm(PalmIntVar *v,int way,int newValue,int oldValue)
{ ;} 


/* The c++ function for: updateDataStructuresOnRestoreConstraints(v:PalmIntVar,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(PalmIntVar *v,int way,int newValue,int oldValue)
{ GC_BIND;
  { VarEvent * evt;
    { if (way == 1)
       evt = v->restInfEvt;
      else if (way == 2)
       evt = v->restSupEvt;
      else evt = v->restValEvt;
        GC_OBJECT(VarEvent,evt);} 
    list * lnext = evt->nextConst;
    list * li = v->indices;
    list * lc = v->constraints;
    int  n = v->nbConstraints;
    int  k = ((n != 0) ?
      (*(lnext))[n] :
      0 );
    int  end = n;
    if (k != 0)
     { ClaireBoolean *v_while;
      v_while = CFALSE;
      
      while (v_while != CTRUE)
      { (*palm.updateDataStructuresOnRestoreConstraint)((*(lc))[k],
          (*(li))[k],
          way,
          newValue,
          oldValue);
        k= (*(lnext))[k];
        v_while = ((k == (*(lnext))[end]) ? CTRUE : CFALSE);
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:choco/AbstractConstraint,idx:integer,way:{1, 2, 3, 4},v:integer,w:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_AbstractConstraint_palm(AbstractConstraint *c,int idx,int way,int v,int w)
{ ;} 


// *************************************************************
// *   Part 3 : Domain restoration                             *
// *************************************************************
// restoring INF for v 
/* The c++ function for: restoreInf(v:PalmIntVar,newValue:integer) [] */
void  palm_restoreInf_PalmIntVar(PalmIntVar *v,int newValue)
{ if (v->inf->latestValue > newValue) 
  { { int  oldValue = v->inf->latestValue;
      claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),newValue);
      if (v->inf->latestValue != v->sup->latestValue)
       STOREI(v->value,-100000000);
      if (v->inf->latestValue == v->sup->latestValue)
       STOREI(v->value,v->inf->latestValue);
      (*palm.updateDataStructuresOnRestoreVariable)(_oid_(v),
        1,
        newValue,
        oldValue);
      palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(v,1,newValue,oldValue);
      palm_postRestoreEvent_PalmEngine1(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)),GC_OBJECT(DecInf,v->restInfEvt));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// retour sur la borne sup 
/* The c++ function for: restoreSup(v:PalmIntVar,newValue:integer) [] */
void  palm_restoreSup_PalmIntVar(PalmIntVar *v,int newValue)
{ if (v->sup->latestValue < newValue) 
  { { int  oldValue = v->sup->latestValue;
      claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),newValue);
      if (v->inf->latestValue != v->sup->latestValue)
       STOREI(v->value,-100000000);
      if (v->inf->latestValue == v->sup->latestValue)
       STOREI(v->value,v->sup->latestValue);
      (*palm.updateDataStructuresOnRestoreVariable)(_oid_(v),
        2,
        newValue,
        oldValue);
      palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(v,2,newValue,oldValue);
      palm_postRestoreEvent_PalmEngine1(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)),GC_OBJECT(IncSup,v->restSupEvt));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: postRestoreInf(pe:PalmEngine,v:PalmIntVar) [] */
void  palm_postRestoreInf_PalmEngine(PalmEngine *pe,PalmIntVar *v)
{ GC_BIND;
  palm_postRestoreEvent_PalmEngine1(pe,GC_OBJECT(DecInf,v->restInfEvt));
  GC_UNBIND;} 


/* The c++ function for: postRestoreSup(pe:PalmEngine,v:PalmIntVar) [] */
void  palm_postRestoreSup_PalmEngine(PalmEngine *pe,PalmIntVar *v)
{ GC_BIND;
  palm_postRestoreEvent_PalmEngine1(pe,GC_OBJECT(IncSup,v->restSupEvt));
  GC_UNBIND;} 


/* The c++ function for: postRestoreEvent(pe:PalmEngine,e:choco/BoundUpdate) [] */
void  palm_postRestoreEvent_PalmEngine1(PalmEngine *pe,BoundUpdate *e)
{ { BoundEventQueue * bq = pe->boundRestEvtQueue;
    int  idxQ = e->idxInQueue;
    if (idxQ <= 0)
     { int  idx;
      { int  g0031 = ((bq->qLastEnqueued == bq->qsize) ?
          1 :
          (bq->qLastEnqueued+1) );
        if (g0031 == bq->qLastRead)
         { close_exception(((general_error *) (*Core._general_error)(_string_("bound event fifo queue is full"),
            _oid_(Kernel.nil))));
          idx = -1;
          } 
        else { (bq->qLastEnqueued = g0031);
            idx = g0031;
            } 
          } 
      ((*(bq->eventQueue))[idx]=_oid_(e));
      (e->idxInQueue = idx);
      } 
    } 
  } 


/* The c++ function for: restoreVariableExplanation(e:DecInf) [] */
void  palm_restoreVariableExplanation_DecInf(DecInf *e)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list * newlist = list::empty(palm._PalmIncInfExplanation);
    ClaireBoolean * keep = CTRUE;
    AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    { OID gc_local;
      ITERATE(exp);
      bag *exp_support;
      exp_support = GC_OBJECT(list,CLREAD(PalmIntVar,v,explanationOnInf));
      for (START(exp_support); NEXT(exp);)
      { GC_LOOP;
        { int  pv = OBJECT(PalmBoundExplanation,exp)->previousValue;
          if (pv < CLREAD(IntVar,v,inf)->latestValue)
           GC__ANY(newlist = newlist->addFast(exp), 1);
          else if ((keep == CTRUE) && 
              (pv == CLREAD(PalmIntVar,v,originalInf)))
           { GC__ANY(newlist = newlist->addFast(exp), 1);
            keep= CFALSE;
            } 
          } 
        GC_UNLOOP;} 
      } 
    (CLREAD(PalmIntVar,v,explanationOnInf) = newlist);
    } 
  GC_UNBIND;} 


/* The c++ function for: restoreVariableExplanation(e:IncSup) [] */
void  palm_restoreVariableExplanation_IncSup(IncSup *e)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list * newlist = list::empty(palm._PalmDecSupExplanation);
    ClaireBoolean * keep = CTRUE;
    AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    { OID gc_local;
      ITERATE(exp);
      bag *exp_support;
      exp_support = GC_OBJECT(list,CLREAD(PalmIntVar,v,explanationOnSup));
      for (START(exp_support); NEXT(exp);)
      { GC_LOOP;
        { int  pv = OBJECT(PalmBoundExplanation,exp)->previousValue;
          if (pv > CLREAD(IntVar,v,sup)->latestValue)
           GC__ANY(newlist = newlist->addFast(exp), 1);
          else if ((keep == CTRUE) && 
              (pv == CLREAD(PalmIntVar,v,originalSup)))
           { GC__ANY(newlist = newlist->addFast(exp), 1);
            keep= CFALSE;
            } 
          } 
        GC_UNLOOP;} 
      } 
    (CLREAD(PalmIntVar,v,explanationOnSup) = newlist);
    } 
  GC_UNBIND;} 


/* The c++ function for: restoreVal(v:PalmIntVar,val:integer) [] */
void  palm_restoreVal_PalmIntVar(PalmIntVar *v,int val)
{ GC_BIND;
  if (CLREAD(PalmIntDomain,v->bucket,nbElements) == 1)
   STOREI(v->value,-100000000);
  palm_addDomainVal_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) v->bucket)),val);
  if (val < v->inf->latestValue)
   claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),val);
  if (val > v->sup->latestValue)
   claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),val);
  if (CLREAD(PalmIntDomain,v->bucket,nbElements) == 1)
   update_property(Kernel.value,
    v,
    11,
    Kernel._integer,
    palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) v->bucket))));
  else STOREI(v->value,-100000000);
    (*palm.updateDataStructuresOnRestoreVariable)(_oid_(v),
    3,
    val,
    0);
  palm_updateDataStructuresOnRestoreConstraints_PalmIntVar(v,3,val,0);
  palm_postRestoreEvent_PalmEngine2(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)),GC_OBJECT(ValueRestorations,v->restValEvt),val);
  GC_UNBIND;} 


/* The c++ function for: postRestoreVal(pe:PalmEngine,v:PalmIntVar,value:integer) [] */
OID  palm_postRestoreVal_PalmEngine(PalmEngine *pe,PalmIntVar *v,int value)
{ GC_BIND;
  { OID Result = 0;
    Result = palm_postRestoreEvent_PalmEngine2(pe,GC_OBJECT(ValueRestorations,v->restValEvt),value);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: postRestoreEvent(pe:PalmEngine,e:ValueRestorations,value:integer) [] */
OID  palm_postRestoreEvent_PalmEngine2(PalmEngine *pe,ValueRestorations *e,int value)
{ GC_BIND;
  { OID Result = 0;
    { RemovalEventQueue * bq = GC_OBJECT(RemovalEventQueue,pe->removalRestEvtQueue);
      int  idxQ = e->idxInQueue;
      if (idxQ <= 0)
       { int  idx;
        { int  g0032 = ((bq->qLastEnqueued == bq->qsize) ?
            1 :
            (bq->qLastEnqueued+1) );
          if (g0032 == bq->qLastRead)
           { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,bq->engine));
            idx = -1;
            } 
          else { (bq->qLastEnqueued = g0032);
              idx = g0032;
              } 
            } 
        ((*(bq->eventQueue))[idx]=_oid_(e));
        (e->idxInQueue = idx);
        (e->many = CFALSE);
        ((*(e->valueStack))[1]=value);
        (e->nbVals = 1);
        Result = ((*(e->causeStack))[1]=0);
        } 
      else { ClaireBoolean * g0034I;
        { ClaireBoolean *v_and;
          { v_and = ((e->many != CTRUE) ? CTRUE : CFALSE);
            if (v_and == CFALSE) g0034I =CFALSE; 
            else { { OID  g0035UU;
                { ClaireBoolean * V_CL0036;{ list * g0037UU;
                    { list * i_bag = list::empty(Kernel.emptySet);
                      { int  i = 1;
                        int  g0033 = e->nbVals;
                        { OID gc_local;
                          while ((i <= g0033))
                          { // HOHO, GC_LOOP not needed !
                            i_bag->addFast((*(e->valueStack))[i]);
                            ++i;
                            } 
                          } 
                        } 
                      g0037UU = GC_OBJECT(list,i_bag);
                      } 
                    V_CL0036 = g0037UU->memq(value);
                    } 
                  
                  g0035UU=_oid_(V_CL0036);} 
                v_and = not_any(g0035UU);
                } 
              if (v_and == CFALSE) g0034I =CFALSE; 
              else g0034I = CTRUE;} 
            } 
          } 
        
        if (g0034I == CTRUE) { int  nbRems = (e->nbVals+1);
            if (nbRems <= e->maxVals)
             { (e->nbVals = (e->nbVals+1));
              Result = ((*(e->valueStack))[nbRems]=value);
              } 
            else { close_exception(((general_error *) (*Core._general_error)(_string_("PaLM error: PaLM does not handle multiple value restorations (p-variables.cl)"),
                  _oid_(Kernel.nil))));
                (e->many = CTRUE);
                (e->nbVals = 1);
                Result = ((*(e->valueStack))[1]=value);
                } 
              } 
          else Result = Kernel.cfalse;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 4 : Domain modification                            *
// *************************************************************
/* The c++ function for: choco/updateInf(v:PalmIntVar,x:integer,idx:integer,e:Explanation) [] */
ClaireBoolean * choco_updateInf_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    if (v->bucket != (NULL))
     { ClaireBoolean * rep = CFALSE;
      { int  i = choco.getInf->fcall(((int) v));
        int  g0038 = (x-1);
        { OID gc_local;
          while ((i <= g0038))
          { GC_LOOP;
            (((rep= OBJECT(ClaireBoolean,(*choco.removeVal)(_oid_(v),
              i,
              idx,
              GC_OID(_oid_(palm_clone_Explanation(e)))))) == CTRUE) ? CTRUE : ((rep == CTRUE) ? CTRUE : CFALSE));
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = rep;
      } 
    else if ((OBJECT(ClaireBoolean,(*choco.updateInf)(_oid_(v),
      x,
      _oid_(e)))) == CTRUE)
     { (*choco.postUpdateInf)(GC_OID(_oid_(v->problem->propagationEngine)),
        _oid_(v),
        idx);
      if (x > v->sup->latestValue)
       { STOREI(v->value,-100000000);
        (v->problem->propagationEngine->contradictionCause = v);
        (CLREAD(PalmEngine,v->problem->propagationEngine,contradictory) = CTRUE);
        palm_resetEvents_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)));
        close_exception((ClaireException *) new_object_class(palm._PalmContradiction));
        } 
      Result = CTRUE;
      } 
    else Result = CFALSE;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/updateInf(v:PalmIntVar,x:integer,e:Explanation) [] */
ClaireBoolean * choco_updateInf_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e)
{ if (x > v->inf->latestValue) 
  { { ClaireBoolean *Result ;
      { { int  oldValue = v->inf->latestValue;
          int  newValue = x;
          palm_self_explain_PalmIntVar1(v,1,e);
          GC_OBJECT(list,v->explanationOnInf)->addFast(GC_OID(_oid_(palm_makeIncInfExplanation_Explanation(e,v->inf->latestValue,v))));
          claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),x);
          if (v->inf->latestValue == v->sup->latestValue)
           STOREI(v->value,v->inf->latestValue);
          (*palm.updateDataStructuresOnVariable)(_oid_(v),
            1,
            newValue,
            oldValue);
          palm_updateDataStructuresOnConstraints_PalmIntVar(v,1,newValue,oldValue);
          } 
        Result = CTRUE;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireBoolean *Result ;
      Result = CFALSE;
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: choco/updateSup(v:PalmIntVar,x:integer,idx:integer,e:Explanation) [] */
ClaireBoolean * choco_updateSup_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    if (v->bucket != (NULL))
     { ClaireBoolean * rep = CFALSE;
      { int  i = (x+1);
        int  g0039 = choco.getSup->fcall(((int) v));
        { OID gc_local;
          while ((i <= g0039))
          { GC_LOOP;
            (((rep= OBJECT(ClaireBoolean,(*choco.removeVal)(_oid_(v),
              i,
              idx,
              GC_OID(_oid_(palm_clone_Explanation(e)))))) == CTRUE) ? CTRUE : ((rep == CTRUE) ? CTRUE : CFALSE));
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = rep;
      } 
    else if ((OBJECT(ClaireBoolean,(*choco.updateSup)(_oid_(v),
      x,
      _oid_(e)))) == CTRUE)
     { (*choco.postUpdateSup)(GC_OID(_oid_(v->problem->propagationEngine)),
        _oid_(v),
        idx);
      if (x < v->inf->latestValue)
       { STOREI(v->value,-100000000);
        (v->problem->propagationEngine->contradictionCause = v);
        (CLREAD(PalmEngine,v->problem->propagationEngine,contradictory) = CTRUE);
        palm_resetEvents_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)));
        close_exception((ClaireException *) new_object_class(palm._PalmContradiction));
        } 
      Result = CTRUE;
      } 
    else Result = CFALSE;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/updateSup(v:PalmIntVar,x:integer,e:Explanation) [] */
ClaireBoolean * choco_updateSup_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e)
{ if (x < v->sup->latestValue) 
  { { ClaireBoolean *Result ;
      { { int  oldValue = v->sup->latestValue;
          int  newValue = x;
          palm_self_explain_PalmIntVar1(v,2,e);
          GC_OBJECT(list,v->explanationOnSup)->addFast(GC_OID(_oid_(palm_makeDecSupExplanation_Explanation(e,v->sup->latestValue,v))));
          claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),x);
          if (v->inf->latestValue == v->sup->latestValue)
           STOREI(v->value,v->inf->latestValue);
          (*palm.updateDataStructuresOnVariable)(_oid_(v),
            2,
            newValue,
            oldValue);
          palm_updateDataStructuresOnConstraints_PalmIntVar(v,2,newValue,oldValue);
          } 
        Result = CTRUE;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireBoolean *Result ;
      Result = CFALSE;
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: choco/removeVal(v:PalmIntVar,x:integer,idx:integer,e:Explanation) [] */
ClaireBoolean * choco_removeVal_PalmIntVar1_palm(PalmIntVar *v,int x,int idx,Explanation *e)
{ if (v->bucket != (NULL)) 
  { { ClaireBoolean *Result ;
      if ((OBJECT(ClaireBoolean,(*choco.removeVal)(_oid_(v),
        x,
        _oid_(e)))) == CTRUE)
       { palm_postRemoveVal_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)),v,x,idx);
        if (CLREAD(PalmIntDomain,v->bucket,nbElements) == 0)
         { (v->problem->propagationEngine->contradictionCause = v);
          (CLREAD(PalmEngine,v->problem->propagationEngine,contradictory) = CTRUE);
          palm_resetEvents_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) v->problem->propagationEngine)));
          close_exception((ClaireException *) new_object_class(palm._PalmContradiction));
          } 
        Result = CTRUE;
        } 
      else Result = CFALSE;
        return (Result);} 
     } 
  else{ if (x == choco.getInf->fcall(((int) v))) 
    { { ClaireBoolean *Result ;
        Result = OBJECT(ClaireBoolean,(*choco.updateInf)(_oid_(v),
          (x+1),
          idx,
          _oid_(e)));
        return (Result);} 
       } 
    else{ if (x == choco.getSup->fcall(((int) v))) 
      { { ClaireBoolean *Result ;
          Result = OBJECT(ClaireBoolean,(*choco.updateSup)(_oid_(v),
            (x-1),
            idx,
            _oid_(e)));
          return (Result);} 
         } 
      else{ GC_BIND;
        { ClaireBoolean *Result ;
          Result = CFALSE;
          GC_UNBIND; return (Result);} 
        } 
      } 
    } 
  } 


/* The c++ function for: choco/removeVal(v:PalmIntVar,x:integer,e:Explanation) [] */
ClaireBoolean * choco_removeVal_PalmIntVar2_palm(PalmIntVar *v,int x,Explanation *e)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { PalmIntDomain * buck = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) v->bucket));
      if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) buck),((int) x))))) == CTRUE)
       { ((*(GC_OBJECT(list,buck->explanationOnVal)))[(x-CLREAD(PalmIntDomain,v->bucket,offset))]=GC_OID(_oid_(palm_makeRemovalExplanation_Explanation(e,x,v))));
        { int  nextValX = (((*(buck->succVector))[(x-buck->offset)])+buck->offset);
          _oid_((ClaireObject *) choco.removeDomainVal->fcall(((int) buck),((int) x)));
          if (x == v->inf->latestValue)
           (buck->needInfComputation = CTRUE);
          if (x == v->sup->latestValue)
           (buck->needSupComputation = CTRUE);
          if (buck->nbElements == 1)
           update_property(Kernel.value,
            v,
            11,
            Kernel._integer,
            palm_firstElement_PalmIntDomain(buck));
          if (buck->nbElements == 0)
           STOREI(v->value,-100000000);
          (*palm.updateDataStructuresOnVariable)(_oid_(v),
            3,
            x,
            0);
          palm_updateDataStructuresOnConstraints_PalmIntVar(v,3,x,0);
          } 
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    GC_UNBIND; return (Result);} 
  } 


// need a specific postRemoveVal for PaLM ... storing information about propagation 
// for enumerated variables ...
/* The c++ function for: postRemoveVal(pe:PalmEngine,v:choco/IntVar,x:integer,i:integer) [] */
void  palm_postRemoveVal_PalmEngine(PalmEngine *pe,IntVar *v,int x,int i)
{ GC_BIND;
  ;{ RemovalEventQueue * rq = GC_OBJECT(RemovalEventQueue,pe->removalEvtQueue);
    ValueRemovals * e = GC_OBJECT(ValueRemovals,v->remValEvt);
    int  idxQ = e->idxInQueue;
    if (idxQ == 0)
     { int  idx;
      { int  g0040 = ((rq->qLastEnqueued == rq->qsize) ?
          1 :
          (rq->qLastEnqueued+1) );
        if (g0040 == rq->qLastRead)
         { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,rq->engine));
          idx = -1;
          } 
        else { (rq->qLastEnqueued = g0040);
            idx = g0040;
            } 
          } 
      (e->idxInQueue = idx);
      (e->many = CFALSE);
      ((*(e->valueStack))[1]=x);
      (e->nbVals = 1);
      ((*(e->causeStack))[1]=i);
      ((*(rq->eventQueue))[idx]=_oid_(e));
      } 
    else { if (idxQ != (-1))
         ;else { ((*(e->valueStack))[(e->nbVals+2)]=(*(e->valueStack))[(e->nbVals+1)]);
            ((*(e->causeStack))[(e->nbVals+2)]=(*(e->causeStack))[(e->nbVals+1)]);
            } 
          if (e->many == CTRUE)
         { if ((*(e->causeStack))[1] != i)
           ((*(e->causeStack))[1]=0);
          } 
        else { int  nbRems = (e->nbVals+1);
            if (nbRems <= e->maxVals)
             { (e->nbVals = (e->nbVals+1));
              ((*(e->valueStack))[nbRems]=x);
              ((*(e->causeStack))[nbRems]=i);
              } 
            else { (e->many = CTRUE);
                (e->nbVals = 1);
                ((*(e->valueStack))[1]=x);
                ((*(e->causeStack))[1]=i);
                (rq->engine->propagationOverflow = CTRUE);
                { ClaireBoolean * g0042I;
                  { OID  g0043UU;
                    { int  j = 2;
                      int  g0041 = (nbRems-1);
                      { OID gc_local;
                        g0043UU= _oid_(CFALSE);
                        while ((j <= g0041))
                        { // HOHO, GC_LOOP not needed !
                          if ((*(e->causeStack))[j] != i)
                           { g0043UU = Kernel.ctrue;
                            break;} 
                          ++j;
                          } 
                        } 
                      } 
                    g0042I = boolean_I_any(g0043UU);
                    } 
                  
                  if (g0042I == CTRUE) ((*(e->causeStack))[1]=0);
                    } 
                ;} 
              } 
          } 
      } 
  GC_UNBIND;} 


/* The c++ function for: choco/instantiate(v:PalmIntVar,x:integer,idx:integer,e:Explanation) [] */
ClaireBoolean * choco_instantiate_PalmIntVar(PalmIntVar *v,int x,int idx,Explanation *e)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * change = CFALSE;
      change= OBJECT(ClaireBoolean,(*choco.updateInf)(_oid_(v),
        x,
        idx,
        GC_OID(_oid_(palm_clone_Explanation(e)))));
      (((change= OBJECT(ClaireBoolean,(*choco.updateSup)(_oid_(v),
        x,
        idx,
        GC_OID(_oid_(palm_clone_Explanation(e)))))) == CTRUE) ? CTRUE : ((change == CTRUE) ? CTRUE : CFALSE));
      Result = change;
      } 
    GC_UNBIND; return (Result);} 
  } 

