/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-constraints.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:39 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM 
// ** Summary : Basic constraints and handling mechanisms
// Part 1  : Storing specific information within constraints
// Part 2  : Basic tools for AbstractConstraints                    
// Part 3  : (Re|dis)connection to(from) the constraint network
// Part 4  : Unary constraints (general purpose methods)
// Part 5  : Binary constraints (general purpose methods)
// Part 6  : Large constraints (general purpose methods)
// Part 7  : Basic unary constraint (propagation)
//           PalmGreaterOrEqualxc, PalmLessOrEqualxc, PalmNotEqualxc, PalmEqualxc
// Part 8  : Basic binary constraint (propagation)
//		     PalmNotEqualxyc, PalmEqualxyc, PalmGreaterOrEqualxyc, PalmLessOrEqualxyc 
// Part 9  : Linear combination of variables (PalmLinComb)
// Part 10 : Handling delayers within PaLMchoco
// ********************************************************************
// *   Part 1: Storing specific information within constraints        *
// ********************************************************************
// Information to add to constraints in order to implement
// explanation management
/* The c++ function for: self_print(pcc:PalmControlConstraint) [] */
OID  claire_self_print_PalmControlConstraint_palm(PalmControlConstraint *pcc)
{ GC_BIND;
  princ_string("control(");
  print_any(GC_OID(_oid_(pcc->constraint)));
  princ_string(",");
  print_any(pcc->index);
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 2: Basic tools for AbstractConstraints                    *
// ********************************************************************
// updating the dependencyNet (constraint addition)
/* The c++ function for: addDependency(c:choco/AbstractConstraint,p:Explanation) [] */
void  palm_addDependency_AbstractConstraint(AbstractConstraint *c,Explanation *p)
{ GC_BIND;
  add_property(palm.dependencyNet,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->hook)),_oid_(p));
  GC_UNBIND;} 


// updating the dependencyNet (constraint removal)
/* The c++ function for: removeDependency(c:choco/AbstractConstraint,p:Explanation) [] */
void  palm_removeDependency_AbstractConstraint(AbstractConstraint *c,Explanation *p)
{ GC_BIND;
  delete_property(palm.dependencyNet,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->hook)),_oid_(p));
  GC_UNBIND;} 


// undoing past effects of a constraint
/* The c++ function for: undo(c:choco/AbstractConstraint) [] */
void  palm_undo_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { OID gc_local;
    ITERATE(e);
    bag *e_support;
    e_support = GC_OBJECT(set,OBJECT(PalmInfoConstraint,c->hook)->dependencyNet);
    for (START(e_support); NEXT(e);)
    { GC_LOOP;
      (*palm.postUndoRemoval)(e,
        _oid_(c));
      GC_UNLOOP;} 
    } 
  (OBJECT(PalmInfoConstraint,c->hook)->dependencyNet = set::empty(palm._Explanation));
  GC_UNBIND;} 


// testing indirect constraints 
/* The c++ function for: indirect?(c:choco/AbstractConstraint) [] */
ClaireBoolean * palm_indirect_ask_AbstractConstraint(AbstractConstraint *c)
{ return (((c->hook != CNULL) ? ((OBJECT(PalmInfoConstraint,c->hook)->indirect == CTRUE) ? CTRUE: CFALSE): CFALSE));} 


/* The c++ function for: setIndirect(c:choco/AbstractConstraint,e:Explanation) [] */
void  palm_setIndirect_AbstractConstraint(AbstractConstraint *c,Explanation *e)
{ { OID  ch = c->hook;
    (OBJECT(PalmInfoConstraint,ch)->addingExplanation = e->explanation);
    (OBJECT(PalmInfoConstraint,ch)->indirect = CTRUE);
    } 
  } 


/* The c++ function for: setDirect(c:choco/AbstractConstraint) [] */
void  palm_setDirect_AbstractConstraint(AbstractConstraint *c)
{ { OID  ch = c->hook;
    (OBJECT(PalmInfoConstraint,ch)->indirect = CFALSE);
    (OBJECT(PalmInfoConstraint,ch)->addingExplanation = set::empty(Kernel._any));
    } 
  } 


/* The c++ function for: setIndirectDependance(c:choco/AbstractConstraint,e:Explanation) [] */
void  palm_setIndirectDependance_AbstractConstraint(AbstractConstraint *c,Explanation *e)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c_prime);
    bag *c_prime_support;
    c_prime_support = GC_OBJECT(set,e->explanation);
    for (START(c_prime_support); NEXT(c_prime);)
    { GC_LOOP;
      add_property(palm.dependingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,c_prime)->hook)),_oid_(c));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


/* The c++ function for: removeIndirectDependance(c:choco/AbstractConstraint) [] */
void  palm_removeIndirectDependance_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { OID gc_local;
    ITERATE(c_prime);
    bag *c_prime_support;
    c_prime_support = GC_OBJECT(set,OBJECT(PalmInfoConstraint,c->hook)->addingExplanation);
    for (START(c_prime_support); NEXT(c_prime);)
    { GC_LOOP;
      delete_property(palm.dependingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,c_prime)->hook)),_oid_(c));
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


/* The c++ function for: informControllersOfDeactivation(c:choco/AbstractConstraint) [] */
void  palm_informControllersOfDeactivation_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { OID gc_local;
    ITERATE(ctrl);
    for (START(OBJECT(PalmInfoConstraint,c->hook)->controllingConstraints); NEXT(ctrl);)
    { GC_LOOP;
      (*palm.takeIntoAccountStatusChange)(GC_OID(_oid_(OBJECT(PalmControlConstraint,ctrl)->constraint)),
        OBJECT(PalmControlConstraint,ctrl)->index);
      GC_UNLOOP;} 
    } 
  GC_UNBIND;} 


// getting the weight of a constraint
/* The c++ function for: weight(c:choco/AbstractConstraint) [] */
int  palm_weight_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { int Result = 0;
    Result = (*palm.weight)(c->hook);
    GC_UNBIND; return (Result);} 
  } 


// adding explanation information from a constraint
/* The c++ function for: self_explain(c:choco/AbstractConstraint,e:Explanation) [] */
void  palm_self_explain_AbstractConstraint(AbstractConstraint *c,Explanation *e)
{ if ((c->hook != CNULL) && 
      (OBJECT(PalmInfoConstraint,c->hook)->indirect == CTRUE)) 
  { { OID gc_local;
      ITERATE(g0044);
      for (START(OBJECT(PalmInfoConstraint,c->hook)->addingExplanation); NEXT(g0044);)
      { GC_LOOP;
        e->explanation->addFast(g0044);
        GC_UNLOOP;} 
      } 
     } 
  else{ GC_BIND;
    e->explanation->addFast(_oid_(c));
    GC_UNBIND;} 
  } 


// activating a constraint
/* The c++ function for: activate(c:choco/AbstractConstraint) [] */
void  palm_activate_AbstractConstraint(AbstractConstraint *c)
{ { OID  hk = c->hook;
    (OBJECT(PalmInfoConstraint,hk)->timeStamp = palm.PALM_TIME_STAMP->value);
    (palm.PALM_TIME_STAMP->value= ((palm.PALM_TIME_STAMP->value)+1));
    } 
  } 


// deactivating a constraint 
// BEWARE: deactivating != removing (past effects are not handled)
// deactivating only deals with unplugging from the constraint network
/* The c++ function for: deactivate(c:choco/AbstractConstraint) [] */
void  palm_deactivate_AbstractConstraint(AbstractConstraint *c)
{ { set * dc = ((set *) copy_bag(OBJECT(PalmInfoConstraint,c->hook)->dependingConstraints));
    { ITERATE(c_prime);
      for (START(dc); NEXT(c_prime);)
      { palm_removeIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,c_prime));
        palm_informControllersOfDeactivation_AbstractConstraint(OBJECT(AbstractConstraint,c_prime));
        choco_setPassive_AbstractConstraint(OBJECT(AbstractConstraint,c_prime));
        palm_setDirect_AbstractConstraint(OBJECT(AbstractConstraint,c_prime));
        } 
      } 
    } 
  ;if (OBJECT(PalmInfoConstraint,c->hook)->dependingConstraints->length != 0)
   close_exception(((general_error *) (*Core._general_error)(_string_(" ******* In deactivate(c: AbstractConstraint) (file: p-constraints) c.dependingConstraints should be empty !!! \n Report bug and all possible information to jussien@emn.fr \n"),
    _oid_(Kernel.nil))));
  } 


/* The c++ function for: initHook(c:choco/AbstractConstraint) [] */
void  palm_initHook_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { AbstractConstraint * g0045; 
    OID  g0046;
    g0045 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0046 = _oid_(_CL_obj);
      } 
    (g0045->hook = g0046);} 
  GC_UNBIND;} 


/* The c++ function for: addControl(ct:choco/AbstractConstraint,pere:choco/AbstractConstraint,idx:integer) [] */
void  palm_addControl_AbstractConstraint(AbstractConstraint *ct,AbstractConstraint *pere,int idx)
{ GC_BIND;
  { OID  g0047UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = pere);
      (_CL_obj->index = idx);
      g0047UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,ct->hook)),g0047UU);
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 3: (Re | dis)connection to the constraint network         *
// ********************************************************************
// need to override choco connectEvent in order to add more information
// <grt> added "IntVar" in property names (VS Set for the future versions)
/* The c++ function for: choco/connectIntVarEvents(u:PalmIntVar,activeOnInst:boolean,activeOnInf:boolean,activeOnSup:boolean,activeOnRem:boolean) [] */
void  choco_connectIntVarEvents_PalmIntVar_palm(PalmIntVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem)
{ GC_BIND;
  choco_connectEvent_VarEvent(GC_OBJECT(IncInf,u->updtInfEvt),u->nbConstraints,activeOnInf);
  choco_connectEvent_VarEvent(GC_OBJECT(DecInf,u->restInfEvt),u->nbConstraints,((activeOnInf == CTRUE) ? CTRUE : ((activeOnSup == CTRUE) ? CTRUE : CFALSE)));
  choco_connectEvent_VarEvent(GC_OBJECT(DecSup,u->updtSupEvt),u->nbConstraints,activeOnSup);
  choco_connectEvent_VarEvent(GC_OBJECT(IncSup,u->restSupEvt),u->nbConstraints,((activeOnInf == CTRUE) ? CTRUE : ((activeOnSup == CTRUE) ? CTRUE : CFALSE)));
  choco_connectEvent_VarEvent(GC_OBJECT(ValueRemovals,u->remValEvt),u->nbConstraints,activeOnRem);
  choco_connectEvent_VarEvent(GC_OBJECT(ValueRestorations,u->restValEvt),u->nbConstraints,activeOnRem);
  GC_UNBIND;} 


// <grt> added "IntVar" in property names (VS Set for the future versions)
/* The c++ function for: choco/disconnectIntVarEvents(v:PalmIntVar,i:integer,passiveOnInst:boolean,passiveOnInf:boolean,passiveOnSup:boolean,passiveOnRem:boolean) [] */
void  choco_disconnectIntVarEvents_PalmIntVar_palm(PalmIntVar *v,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnInf,ClaireBoolean *passiveOnSup,ClaireBoolean *passiveOnRem)
{ GC_BIND;
  if (passiveOnInf == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(IncInf,v->updtInfEvt),i);
  if (passiveOnInf == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(DecInf,v->restInfEvt),i);
  if (passiveOnSup == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(DecSup,v->updtSupEvt),i);
  if (passiveOnSup == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(IncSup,v->restSupEvt),i);
  if (passiveOnRem == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(ValueRemovals,v->remValEvt),i);
  if (passiveOnRem == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(ValueRestorations,v->restValEvt),i);
  GC_UNBIND;} 


// reconnecting PaLM events
// <grt> added "IntVar" in property names (VS Set for the future versions)
/* The c++ function for: choco/reconnectIntVarEvents(v:PalmIntVar,idx:integer,onInst:boolean,onInf:boolean,onSup:boolean,onRem:boolean) [] */
void  choco_reconnectIntVarEvents_PalmIntVar_palm(PalmIntVar *v,int idx,ClaireBoolean *onInst,ClaireBoolean *onInf,ClaireBoolean *onSup,ClaireBoolean *onRem)
{ GC_BIND;
  if (onInf == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(IncInf,v->updtInfEvt),idx);
  if (onInf == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(DecInf,v->restInfEvt),idx);
  if (onSup == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(DecSup,v->updtSupEvt),idx);
  if (onSup == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(IncSup,v->restSupEvt),idx);
  if (onRem == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(ValueRemovals,v->remValEvt),idx);
  if (onRem == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(ValueRestorations,v->restValEvt),idx);
  GC_UNBIND;} 


/* The c++ function for: choco/connectHook(hk:PalmInfoConstraint,c:choco/AbstractConstraint) [] */
void  choco_connectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c)
{ palm_activate_AbstractConstraint(c);
  (OBJECT(PalmInfoConstraint,c->hook)->everConnected = CTRUE);
  } 


/* The c++ function for: choco/disconnectHook(hk:PalmInfoConstraint,c:choco/AbstractConstraint) [] */
void  choco_disconnectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c)
{ palm_deactivate_AbstractConstraint(c);
  } 


/* The c++ function for: choco/reconnectHook(hk:PalmInfoConstraint,c:choco/AbstractConstraint) [] */
void  choco_reconnectHook_PalmInfoConstraint_palm(PalmInfoConstraint *hk,AbstractConstraint *c)
{ palm_activate_AbstractConstraint(c);
  } 


// <fla> use fast dispatch of general PaLM methods
//   first, define the range of the methods
// <fla> use fast dispatch of general PaLM methods
//   second, provide a root definition
/* The c++ function for: awakeOnRestoreInf(c:choco/AbstractConstraint,i:integer) [] */
void  palm_awakeOnRestoreInf_AbstractConstraint(AbstractConstraint *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("!!! awakeOnRestoreInf for ~S not yet implemented"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: awakeOnRestoreSup(c:choco/AbstractConstraint,i:integer) [] */
void  palm_awakeOnRestoreSup_AbstractConstraint(AbstractConstraint *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("!!! awakeOnRestoreSup for ~S not yet implemented"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: awakeOnRestoreVal(c:choco/AbstractConstraint,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_AbstractConstraint(AbstractConstraint *c,int idx,int v)
{ close_exception(((general_error *) (*Core._general_error)(_string_("!!! awakeOnRestoreVal for ~S not yet implemented"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: whyIsTrue(c:choco/AbstractConstraint) [] */
set * palm_whyIsTrue_AbstractConstraint(AbstractConstraint *c)
{ return (((set *) close_exception(((general_error *) (*Core._general_error)(_string_("!!! whyIsTrue for ~S not yet implemented"),
    _oid_(list::alloc(1,_oid_(c->isa))))))));} 


/* The c++ function for: whyIsFalse(c:choco/AbstractConstraint) [] */
set * palm_whyIsFalse_AbstractConstraint(AbstractConstraint *c)
{ return (((set *) close_exception(((general_error *) (*Core._general_error)(_string_("!!! whyIsFalse for ~S not yet implemented"),
    _oid_(list::alloc(1,_oid_(c->isa))))))));} 


// <fla> use fast dispatch of general PaLM methods
//   third, declare the method as interface, to turn on fast dispatch
// <fla> use fast dispatch of general PaLM methods
//   last, declare those methods as interface of the abstract classes so that
//   they appear in the headers of the generated code (palm.h)
// does not work yet
// ********************************************************************
// *   Part 4: Unary constraints                                      *
// ********************************************************************
// Handling unary constraints
// restrict slot range (used for type inference by the Claire compiler)
// API for creating unary constraints
// needed for constraint creation because of new assignements
/* The c++ function for: makePalmUnIntConstraint(c:class,u:PalmIntVar,cc:integer) [] */
PalmUnIntConstraint * palm_makePalmUnIntConstraint_class(ClaireClass *c,PalmIntVar *u,int cc)
{ GC_BIND;
  { PalmUnIntConstraint *Result ;
    { PalmUnIntConstraint * cont = GC_OBJECT(PalmUnIntConstraint,((PalmUnIntConstraint *) new_class1(c)));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.v1,palm._PalmUnIntConstraint))),
        _oid_(cont),
        _oid_(u));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.cste,palm._PalmUnIntConstraint))),
        _oid_(cont),
        cc);
      { AbstractConstraint * g0048; 
        OID  g0049;
        g0048 = cont;
        { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
          g0049 = _oid_(_CL_obj);
          } 
        (g0048->hook = g0049);} 
      Result = cont;
      } 
    GC_UNBIND; return (Result);} 
  } 


// awakeOnRestoreXXX shared by all unary constraints
// to be specified for each constraint (if possible)
/* The c++ function for: awakeOnRestoreInf(c:PalmUnIntConstraint,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmUnIntConstraint(PalmUnIntConstraint *c,int i)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmUnIntConstraint,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmUnIntConstraint(PalmUnIntConstraint *c,int i)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


// ********************************************************************
// *   Part 5: Binary constraints                                     *
// ********************************************************************
// Handling binary constraints
// id
// API for creating binary constraints
// needed for constraint creation because of new assignements
/* The c++ function for: makePalmBinIntConstraint(c:class,u:PalmIntVar,v:PalmIntVar,cc:integer) [] */
PalmBinIntConstraint * palm_makePalmBinIntConstraint_class(ClaireClass *c,PalmIntVar *u,PalmIntVar *v,int cc)
{ GC_BIND;
  { PalmBinIntConstraint *Result ;
    { PalmBinIntConstraint * cont = GC_OBJECT(PalmBinIntConstraint,((PalmBinIntConstraint *) new_class1(c)));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.v1,palm._PalmBinIntConstraint))),
        _oid_(cont),
        _oid_(u));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.v2,palm._PalmBinIntConstraint))),
        _oid_(cont),
        _oid_(v));
      (*Kernel.put)(GC_OID(_oid_(_at_property1(choco.cste,palm._PalmBinIntConstraint))),
        _oid_(cont),
        cc);
      { AbstractConstraint * g0050; 
        OID  g0051;
        g0050 = cont;
        { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
          g0051 = _oid_(_CL_obj);
          } 
        (g0050->hook = g0051);} 
      Result = cont;
      } 
    GC_UNBIND; return (Result);} 
  } 


// awakeOnRestoreXXX shared by all binary constraints
// to be specified for each constraint (if possible)
/* The c++ function for: awakeOnRestoreInf(c:PalmBinIntConstraint,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmBinIntConstraint(PalmBinIntConstraint *c,int i)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmBinIntConstraint,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmBinIntConstraint(PalmBinIntConstraint *c,int i)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


// ********************************************************************
// *   Part 6 : Large constraints                                     *
// ********************************************************************
// API for creating large constraints
// needed for constraint creation because of new assignements
/* The c++ function for: makePalmLargeIntConstraint(c:class,l:list[PalmIntVar],cc:integer) [] */
PalmLargeIntConstraint * palm_makePalmLargeIntConstraint_class(ClaireClass *c,list *l,int cc)
{ GC_BIND;
  { PalmLargeIntConstraint *Result ;
    { PalmLargeIntConstraint * cont = GC_OBJECT(PalmLargeIntConstraint,((PalmLargeIntConstraint *) new_class1(c)));
      { LargeIntConstraint * g0052; 
        list * g0053;
        g0052 = cont;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g0053 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g0053))[CLcount] = v_val;} 
          } 
        (g0052->vars = g0053);} 
      (cont->nbVars = l->length);
      (cont->indices = make_list_integer2(l->length,Kernel._integer,0));
      (cont->cste = cc);
      { AbstractConstraint * g0054; 
        OID  g0055;
        g0054 = cont;
        { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
          g0055 = _oid_(_CL_obj);
          } 
        (g0054->hook = g0055);} 
      Result = cont;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 7 : Basic unary constraints                               *
// ********************************************************************
// *******
// *** PalmGreaterOrEqualxc : X >= c
// class definition
// pretty printing 
/* The c++ function for: self_print(c:PalmGreaterOrEqualxc) [] */
OID  claire_self_print_PalmGreaterOrEqualxc_palm(PalmGreaterOrEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" >= ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// awakening of constraint : first propagation 
/* The c++ function for: choco/propagate(c:PalmGreaterOrEqualxc) [] */
void  choco_propagate_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    palm_self_explain_AbstractConstraint(c,expl);
    (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      c->cste,
      c->idx1,
      _oid_(expl));
    } 
  GC_UNBIND;} 


// reaction to INCINF and DECSUP
/* The c++ function for: choco/awakeOnInf(c:PalmGreaterOrEqualxc,i:integer) [] */
void  choco_awakeOnInf_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnSup(c:PalmGreaterOrEqualxc,i:integer) [] */
void  choco_awakeOnSup_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int i)
{ ;} 


// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 
// reaction to RESVAL (restoration)
/* The c++ function for: awakeOnRestoreVal(c:PalmGreaterOrEqualxc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx,int v)
{ if (v < c->cste) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(expl));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// Unused but necessary for satisfying compilation
/* The c++ function for: choco/awakeOnInst(c:PalmGreaterOrEqualxc,idx:integer) [] */
void  choco_awakeOnInst_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx)
{ ;} 


/* The c++ function for: choco/awakeOnRem(c:PalmGreaterOrEqualxc,idx:integer,x:integer) [] */
void  choco_awakeOnRem_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c,int idx,int x)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmGreaterOrEqualxc) [] */
OID  choco_askIfEntailed_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { PalmIntVar * v = GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1));
      int  x = c->cste;
      if (x <= choco.getInf->fcall(((int) v)))
       Result = Kernel.ctrue;
      else if (choco.getSup->fcall(((int) v)) < x)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmGreaterOrEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmGreaterOrEqualxc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ return (CFALSE);} 


/* The c++ function for: whyIsFalse(c:PalmGreaterOrEqualxc) [] */
set * palm_whyIsFalse_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsTrue(c:PalmGreaterOrEqualxc) [] */
set * palm_whyIsTrue_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// registering the constraint within choco mechanims
/* The c++ function for: checkPalm(ct:PalmGreaterOrEqualxc) [] */
ClaireBoolean * palm_checkPalm_PalmGreaterOrEqualxc(PalmGreaterOrEqualxc *ct)
{ return (CTRUE);} 


// claire3 port register no longer used
// *******
// *** PalmLessOrEqualxc : X <= c
// class definition
// pretty printing 
/* The c++ function for: self_print(c:PalmLessOrEqualxc) [] */
OID  claire_self_print_PalmLessOrEqualxc_palm(PalmLessOrEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" <= ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// constraint awakening
/* The c++ function for: choco/propagate(c:PalmLessOrEqualxc) [] */
void  choco_propagate_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    palm_self_explain_AbstractConstraint(c,expl);
    (*choco.updateSup)(GC_OID(_oid_(c->v1)),
      c->cste,
      c->idx1,
      _oid_(expl));
    } 
  GC_UNBIND;} 


// reaction to INCINF and DECSUP
/* The c++ function for: choco/awakeOnInf(c:PalmLessOrEqualxc,i:integer) [] */
void  choco_awakeOnInf_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnSup(c:PalmLessOrEqualxc,i:integer) [] */
void  choco_awakeOnSup_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int i)
{ ;} 


// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 
// reaction to RESVAL (restoration)
/* The c++ function for: awakeOnRestoreVal(c:PalmLessOrEqualxc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx,int v)
{ if (v > c->cste) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(expl));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsFalse(c:PalmLessOrEqualxc) [] */
set * palm_whyIsFalse_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsTrue(c:PalmLessOrEqualxc) [] */
set * palm_whyIsTrue_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// Unused but necessary for satisfying compilation
/* The c++ function for: choco/awakeOnInst(c:PalmLessOrEqualxc,idx:integer) [] */
void  choco_awakeOnInst_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx)
{ ;} 


/* The c++ function for: choco/awakeOnRem(c:PalmLessOrEqualxc,idx:integer,x:integer) [] */
void  choco_awakeOnRem_PalmLessOrEqualxc(PalmLessOrEqualxc *c,int idx,int x)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmLessOrEqualxc) [] */
OID  choco_askIfEntailed_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { PalmIntVar * v = GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1));
      int  x = c->cste;
      if (choco.getSup->fcall(((int) v)) <= x)
       Result = Kernel.ctrue;
      else if (choco.getInf->fcall(((int) v)) > x)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmLessOrEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmLessOrEqualxc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmLessOrEqualxc(PalmLessOrEqualxc *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmLessOrEqualxc) [] */
ClaireBoolean * palm_checkPalm_PalmLessOrEqualxc(PalmLessOrEqualxc *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanims
// claire3 port register no longer used
// *******
// *** PalmNotEqualxc : X !== c
// class definition
// pretty printing
/* The c++ function for: self_print(c:PalmNotEqualxc) [] */
OID  claire_self_print_PalmNotEqualxc_palm(PalmNotEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" !== ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// constraint awakening
/* The c++ function for: choco/propagate(c:PalmNotEqualxc) [] */
void  choco_propagate_PalmNotEqualxc(PalmNotEqualxc *c)
{ if (get_property(choco.bucket,GC_OBJECT(PalmIntVar,c->v1)) != CNULL) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        c->cste,
        c->idx1,
        _oid_(expl));
      } 
     } 
  else{ GC_BIND;
    { Explanation * explinf;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          explinf = _CL_obj;
          } 
        GC_OBJECT(Explanation,explinf);} 
      Explanation * explsup;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          explsup = _CL_obj;
          } 
        GC_OBJECT(Explanation,explsup);} 
      palm_self_explain_AbstractConstraint(c,explinf);
      palm_self_explain_AbstractConstraint(c,explsup);
      if (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == c->cste)
       (*choco.updateInf)(GC_OID(_oid_(c->v1)),
        (c->cste+1),
        c->idx1,
        _oid_(explinf));
      if (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == c->cste)
       (*choco.updateSup)(GC_OID(_oid_(c->v1)),
        (c->cste-1),
        c->idx1,
        _oid_(explsup));
      } 
    GC_UNBIND;} 
  } 


// reaction to INCINF 
/* The c++ function for: choco/awakeOnInf(c:PalmNotEqualxc,idx:integer) [] */
void  choco_awakeOnInf_PalmNotEqualxc(PalmNotEqualxc *c,int idx)
{ GC_BIND;
  { Explanation * explinf;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        explinf = _CL_obj;
        } 
      GC_OBJECT(Explanation,explinf);} 
    palm_self_explain_AbstractConstraint(c,explinf);
    if (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == c->cste)
     (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      (c->cste+1),
      c->idx1,
      _oid_(explinf));
    } 
  GC_UNBIND;} 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmNotEqualxc,idx:integer) [] */
void  choco_awakeOnSup_PalmNotEqualxc(PalmNotEqualxc *c,int idx)
{ GC_BIND;
  { Explanation * explsup;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        explsup = _CL_obj;
        } 
      GC_OBJECT(Explanation,explsup);} 
    palm_self_explain_AbstractConstraint(c,explsup);
    if (c->v1->sup->latestValue == c->cste)
     (*choco.updateSup)(GC_OID(_oid_(c->v1)),
      (c->cste-1),
      c->idx1,
      _oid_(explsup));
    } 
  GC_UNBIND;} 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmNotEqualxc,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmNotEqualxc(PalmNotEqualxc *c,int idx)
{ choco_awakeOnInf_PalmNotEqualxc(c,idx);
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmNotEqualxc,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmNotEqualxc(PalmNotEqualxc *c,int idx)
{ choco_awakeOnSup_PalmNotEqualxc(c,idx);
  } 


// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmNotEqualxc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmNotEqualxc(PalmNotEqualxc *c,int idx,int v)
{ if ((CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),nbElements) == 1) && 
      (v == c->cste)) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(expl));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// unused but necessary for compilation
/* The c++ function for: choco/awakeOnInst(c:PalmNotEqualxc,idx:integer) [] */
void  choco_awakeOnInst_PalmNotEqualxc(PalmNotEqualxc *c,int idx)
{ ;} 


/* The c++ function for: choco/awakeOnRem(c:PalmNotEqualxc,idx:integer,x:integer) [] */
void  choco_awakeOnRem_PalmNotEqualxc(PalmNotEqualxc *c,int idx,int x)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmNotEqualxc) [] */
OID  choco_askIfEntailed_PalmNotEqualxc(PalmNotEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { PalmIntVar * v = GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1));
      int  x = c->cste;
      if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
        x))) == CTRUE)
       Result = Kernel.cfalse;
      else if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
        x)) == CTRUE)
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmNotEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmNotEqualxc(PalmNotEqualxc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmNotEqualxc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmNotEqualxc(PalmNotEqualxc *c)
{ return (CFALSE);} 


/* The c++ function for: whyIsTrue(c:PalmNotEqualxc) [] */
set * palm_whyIsTrue_PalmNotEqualxc(PalmNotEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,c->cste,e);
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: whyIsFalse(c:PalmNotEqualxc) [] */
set * palm_whyIsFalse_PalmNotEqualxc(PalmNotEqualxc *c)
{ GC_BIND;
  ;{ set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,e);
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkPalm(ct:PalmNotEqualxc) [] */
ClaireBoolean * palm_checkPalm_PalmNotEqualxc(PalmNotEqualxc *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanims
// claire3 port register no longer used
// *******
// *** PalmEqualxc : X == c
// class definition
// pretty printing 
/* The c++ function for: self_print(c:PalmEqualxc) [] */
OID  claire_self_print_PalmEqualxc_palm(PalmEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" = ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// constraint awakening
/* The c++ function for: choco/propagate(c:PalmEqualxc) [] */
void  choco_propagate_PalmEqualxc(PalmEqualxc *c)
{ GC_BIND;
  { Explanation * explinf;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        explinf = _CL_obj;
        } 
      GC_OBJECT(Explanation,explinf);} 
    Explanation * explsup;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        explsup = _CL_obj;
        } 
      GC_OBJECT(Explanation,explsup);} 
    palm_self_explain_AbstractConstraint(c,explinf);
    palm_self_explain_AbstractConstraint(c,explsup);
    (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      c->cste,
      c->idx1,
      _oid_(explinf));
    (*choco.updateSup)(GC_OID(_oid_(c->v1)),
      c->cste,
      c->idx1,
      _oid_(explsup));
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/propagate(c:AssignmentConstraint) [] */
void  choco_propagate_AssignmentConstraint(AssignmentConstraint *c)
{ choco_propagate_PalmEqualxc(c);
  } 


// reaction to INCINF and DECSUP
/* The c++ function for: choco/awakeOnInf(c:PalmEqualxc,i:integer) [] */
void  choco_awakeOnInf_PalmEqualxc(PalmEqualxc *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnSup(c:PalmEqualxc,i:integer) [] */
void  choco_awakeOnSup_PalmEqualxc(PalmEqualxc *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnInf(c:AssignmentConstraint,i:integer) [] */
void  choco_awakeOnInf_AssignmentConstraint(AssignmentConstraint *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnSup(c:AssignmentConstraint,i:integer) [] */
void  choco_awakeOnSup_AssignmentConstraint(AssignmentConstraint *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnRem(c:PalmEqualxc,idx:integer,x:integer) [] */
void  choco_awakeOnRem_PalmEqualxc(PalmEqualxc *c,int idx,int x)
{ ;} 


/* The c++ function for: choco/awakeOnRem(c:AssignmentConstraint,idx:integer,x:integer) [] */
void  choco_awakeOnRem_AssignmentConstraint(AssignmentConstraint *c,int idx,int x)
{ ;} 


// reaction to DECINF and INCSUP (restorations)
// see PalmUnIntConstraint code 
// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmEqualxc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmEqualxc(PalmEqualxc *c,int idx,int v)
{ if (v != c->cste) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(expl));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// Unused but necessary for satisfying compilation
/* The c++ function for: choco/awakeOnInst(c:PalmEqualxc,idx:integer) [] */
void  choco_awakeOnInst_PalmEqualxc(PalmEqualxc *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmEqualxc) [] */
OID  choco_askIfEntailed_PalmEqualxc(PalmEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { PalmIntVar * v = GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1));
      int  x = c->cste;
      if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
        x))) == CTRUE)
       Result = Kernel.ctrue;
      else if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
        x)) == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmEqualxc(PalmEqualxc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmEqualxc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmEqualxc(PalmEqualxc *c)
{ return (CFALSE);} 


/* The c++ function for: whyIsTrue(c:PalmEqualxc) [] */
set * palm_whyIsTrue_PalmEqualxc(PalmEqualxc *c)
{ GC_BIND;
  ;{ set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,e);
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: whyIsFalse(c:PalmEqualxc) [] */
set * palm_whyIsFalse_PalmEqualxc(PalmEqualxc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,c->cste,e);
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// registering the constraint within choco mechanims
/* The c++ function for: checkPalm(ct:PalmEqualxc) [] */
ClaireBoolean * palm_checkPalm_PalmEqualxc(PalmEqualxc *ct)
{ return (CTRUE);} 


/* The c++ function for: checkPalm(ct:AssignmentConstraint) [] */
ClaireBoolean * palm_checkPalm_AssignmentConstraint(AssignmentConstraint *ct)
{ return (CTRUE);} 


// claire3 port register no longer used
// claire3 port register no longer used
// ********************************************************************
// *   Part 8 : Basic binary constraints                              *
// ********************************************************************
// *******
// *** PalmNotEqualxyc : Y !== V + C
// class definition
// pretty printing
/* The c++ function for: self_print(c:PalmNotEqualxyc) [] */
OID  claire_self_print_PalmNotEqualxyc_palm(PalmNotEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" !== ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// awakening the constraint
/* The c++ function for: choco/propagate(c:PalmNotEqualxyc) [] */
void  choco_propagate_PalmNotEqualxyc(PalmNotEqualxyc *c)
{ if (CLREAD(PalmIntVar,c->v1,bucket) != (NULL)) 
  { { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),nbElements) == 1)
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,expl);
        (*choco.removeVal)(GC_OID(_oid_(c->v2)),
          (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket))))-c->cste),
          c->idx2,
          _oid_(expl));
        } 
      if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),nbElements) == 1)
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),4,expl);
        (*choco.removeVal)(GC_OID(_oid_(c->v1)),
          (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket))))+c->cste),
          c->idx1,
          _oid_(expl));
        } 
      } 
     } 
  else{ GC_BIND;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      if (GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,expl);
        if (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))-c->cste))
         (*choco.updateInf)(GC_OID(_oid_(c->v2)),
          ((c->v1->inf->latestValue-c->cste)+1),
          c->idx2,
          _oid_(expl));
        if (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))-c->cste))
         (*choco.updateSup)(GC_OID(_oid_(c->v2)),
          ((c->v1->inf->latestValue-c->cste)-1),
          c->idx2,
          GC_OID(_oid_(palm_clone_Explanation(expl))));
        } 
      else if (GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),4,expl);
        if (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))
         (*choco.updateInf)(GC_OID(_oid_(c->v1)),
          ((c->v2->inf->latestValue+c->cste)+1),
          c->idx1,
          _oid_(expl));
        if (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))
         (*choco.updateSup)(GC_OID(_oid_(c->v1)),
          ((c->v2->inf->latestValue+c->cste)-1),
          c->idx1,
          GC_OID(_oid_(palm_clone_Explanation(expl))));
        } 
      } 
    GC_UNBIND;} 
  } 


// reaction to INCINF
/* The c++ function for: choco/awakeOnInf(c:PalmNotEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    if ((idx == 1) && 
        (c->v1->inf->latestValue == c->v1->sup->latestValue))
     { palm_self_explain_AbstractConstraint(c,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,expl);
      if (c->v2->inf->latestValue == (c->v1->inf->latestValue-c->cste))
       (*choco.updateInf)(GC_OID(_oid_(c->v2)),
        ((c->v1->inf->latestValue-c->cste)+1),
        c->idx2,
        _oid_(expl));
      if (c->v2->sup->latestValue == (c->v1->inf->latestValue-c->cste))
       (*choco.updateSup)(GC_OID(_oid_(c->v2)),
        ((c->v1->inf->latestValue-c->cste)-1),
        c->idx2,
        GC_OID(_oid_(palm_clone_Explanation(expl))));
      } 
    else if ((idx == 2) && 
        (c->v2->inf->latestValue == c->v2->sup->latestValue))
     { palm_self_explain_AbstractConstraint(c,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),4,expl);
      if (c->v1->inf->latestValue == (c->v2->inf->latestValue+c->cste))
       (*choco.updateInf)(GC_OID(_oid_(c->v1)),
        ((c->v2->inf->latestValue+c->cste)+1),
        c->idx1,
        _oid_(expl));
      if (c->v1->sup->latestValue == (c->v2->inf->latestValue+c->cste))
       (*choco.updateSup)(GC_OID(_oid_(c->v1)),
        ((c->v2->inf->latestValue+c->cste)-1),
        c->idx1,
        GC_OID(_oid_(palm_clone_Explanation(expl))));
      } 
    } 
  GC_UNBIND;} 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmNotEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx)
{ choco_awakeOnInf_PalmNotEqualxyc(c,idx);
  } 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmNotEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx)
{ choco_awakeOnInf_PalmNotEqualxyc(c,idx);
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmNotEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx)
{ choco_awakeOnInf_PalmNotEqualxyc(c,idx);
  } 


// reaction to REMVAL
/* The c++ function for: choco/awakeOnRem(c:PalmNotEqualxyc,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx,int v)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    if (idx == 1)
     { if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),nbElements) == 1)
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,expl);
        (*choco.removeVal)(GC_OID(_oid_(c->v2)),
          (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket))))-c->cste),
          c->idx2,
          _oid_(expl));
        } 
      } 
    else if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),nbElements) == 1)
     { palm_self_explain_AbstractConstraint(c,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),4,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket))))+c->cste),
        c->idx1,
        _oid_(expl));
      } 
    } 
  GC_UNBIND;} 


// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmNotEqualxyc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx,int v)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    if (idx == 1)
     { if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),nbElements) == 1)
       { palm_self_explain_AbstractConstraint(c,expl);
        palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),4,expl);
        (*choco.removeVal)(GC_OID(_oid_(c->v2)),
          (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket))))-c->cste),
          c->idx2,
          _oid_(expl));
        } 
      } 
    else if (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),nbElements) == 1)
     { palm_self_explain_AbstractConstraint(c,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),4,expl);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        (palm_firstElement_PalmIntDomain(GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket))))+c->cste),
        c->idx1,
        _oid_(expl));
      } 
    } 
  GC_UNBIND;} 


// unused but necessary for compilation
/* The c++ function for: choco/awakeOnInst(c:PalmNotEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_PalmNotEqualxyc(PalmNotEqualxyc *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmNotEqualxyc) [] */
OID  choco_askIfEntailed_PalmNotEqualxyc(PalmNotEqualxyc *c)
{ GC_BIND;
  { OID Result = 0;
    if ((choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) < ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste)) || 
        (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))) < ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))-c->cste)))
     Result = Kernel.ctrue;
    else if ((GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))) && 
        ((GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))) && 
          (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))))
     Result = Kernel.cfalse;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmNotEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmNotEqualxyc(PalmNotEqualxyc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmNotEqualxyc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmNotEqualxyc(PalmNotEqualxyc *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmNotEqualxyc) [] */
ClaireBoolean * palm_checkPalm_PalmNotEqualxyc(PalmNotEqualxyc *ct)
{ return (CTRUE);} 


// registering the constraint within the choco mechanims
// claire3 port register no longer used
// *******
// *** PalmEqualxyc : Y == V + c
// class definition
// pretty printing
/* The c++ function for: self_print(c:PalmEqualxyc) [] */
OID  claire_self_print_PalmEqualxyc_palm(PalmEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" = ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// awakening the constraint
/* The c++ function for: choco/propagate(c:PalmEqualxyc) [] */
void  choco_propagate_PalmEqualxyc(PalmEqualxyc *c)
{ if (CLREAD(PalmIntVar,c->v1,bucket) != (NULL)) 
  { { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
      PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b1)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) b2),((int) (x-c->cste))))) == CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(x-c->cste),expl);
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b2)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) b1),((int) (x+c->cste))))) == CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(x+c->cste),expl);
            (*choco.removeVal)(GC_OID(_oid_(c->v2)),
              x,
              c->idx2,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      } 
     } 
  else{ GC_BIND;
    { Explanation * v1inf;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          v1inf = _CL_obj;
          } 
        GC_OBJECT(Explanation,v1inf);} 
      Explanation * v1sup;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          v1sup = _CL_obj;
          } 
        GC_OBJECT(Explanation,v1sup);} 
      Explanation * v2inf;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          v2inf = _CL_obj;
          } 
        GC_OBJECT(Explanation,v2inf);} 
      Explanation * v2sup;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          v2sup = _CL_obj;
          } 
        GC_OBJECT(Explanation,v2sup);} 
      palm_self_explain_AbstractConstraint(c,v1inf);
      palm_self_explain_AbstractConstraint(c,v2inf);
      palm_self_explain_AbstractConstraint(c,v1sup);
      palm_self_explain_AbstractConstraint(c,v2sup);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),1,v1inf);
      (*choco.updateInf)(GC_OID(_oid_(c->v1)),
        (c->v2->inf->latestValue+c->cste),
        c->idx1,
        _oid_(v1inf));
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),2,v1sup);
      (*choco.updateSup)(GC_OID(_oid_(c->v1)),
        (c->v2->sup->latestValue+c->cste),
        c->idx1,
        _oid_(v1sup));
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,v2inf);
      (*choco.updateInf)(GC_OID(_oid_(c->v2)),
        (c->v1->inf->latestValue-c->cste),
        c->idx2,
        _oid_(v2inf));
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,v2sup);
      (*choco.updateSup)(GC_OID(_oid_(c->v2)),
        (c->v1->sup->latestValue-c->cste),
        c->idx2,
        _oid_(v2sup));
      } 
    GC_UNBIND;} 
  } 


// reaction to INCINF
/* The c++ function for: choco/awakeOnInf(c:PalmEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_PalmEqualxyc(PalmEqualxyc *c,int idx)
{ if (idx == 1) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,e);
      (*choco.updateInf)(GC_OID(_oid_(c->v2)),
        (c->v1->inf->latestValue-c->cste),
        c->idx2,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),1,e);
      (*choco.updateInf)(GC_OID(_oid_(c->v1)),
        (c->v2->inf->latestValue+c->cste),
        c->idx1,
        _oid_(e));
      } 
    GC_UNBIND;} 
  } 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_PalmEqualxyc(PalmEqualxyc *c,int idx)
{ if (idx == 1) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,e);
      (*choco.updateSup)(GC_OID(_oid_(c->v2)),
        (c->v1->sup->latestValue-c->cste),
        c->idx2,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),2,e);
      (*choco.updateSup)(GC_OID(_oid_(c->v1)),
        (c->v2->sup->latestValue+c->cste),
        c->idx1,
        _oid_(e));
      } 
    GC_UNBIND;} 
  } 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmEqualxyc(PalmEqualxyc *c,int idx)
{ if (idx == 1)
   choco_awakeOnInf_PalmEqualxyc(c,2);
  else choco_awakeOnInf_PalmEqualxyc(c,1);
    } 


// reaction INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmEqualxyc(PalmEqualxyc *c,int idx)
{ if (idx == 1)
   choco_awakeOnSup_PalmEqualxyc(c,2);
  else choco_awakeOnSup_PalmEqualxyc(c,1);
    } 


// reaction to REMVAL  
/* The c++ function for: choco/awakeOnRem(c:PalmEqualxyc,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmEqualxyc(PalmEqualxyc *c,int idx,int v)
{ if (idx == 1) 
  { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket))))),((int) (v-c->cste)))))) == CTRUE)
     { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,v,e);
      (*choco.removeVal)(GC_OID(_oid_(c->v2)),
        (v-c->cste),
        c->idx2,
        _oid_(e));
      } 
     } 
  else{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket))))),((int) (v+c->cste)))))) == CTRUE) 
    { { Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        palm_self_explain_AbstractConstraint(c,e);
        palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,v,e);
        (*choco.removeVal)(GC_OID(_oid_(c->v1)),
          (v+c->cste),
          c->idx1,
          _oid_(e));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmEqualxyc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmEqualxyc(PalmEqualxyc *c,int idx,int v)
{ if (idx == 1) 
  { if (not_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket))))),((int) (v-c->cste))))) == CTRUE)
     { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(v-c->cste),e);
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(e));
      } 
     } 
  else{ if (not_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket))))),((int) (v+c->cste))))) == CTRUE) 
    { { Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        palm_self_explain_AbstractConstraint(c,e);
        palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(v+c->cste),e);
        (*choco.removeVal)(GC_OID(_oid_(c->v2)),
          v,
          c->idx2,
          _oid_(e));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// unused but necessary for compilation
/* The c++ function for: choco/awakeOnInst(c:PalmEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_PalmEqualxyc(PalmEqualxyc *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmEqualxyc) [] */
OID  choco_askIfEntailed_PalmEqualxyc(PalmEqualxyc *c)
{ GC_BIND;
  { OID Result = 0;
    if ((choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) < ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste)) || 
        (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) > ((choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste)))
     Result = Kernel.cfalse;
    else if ((GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))) && 
        ((GC_OID(choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))) == GC_OID(choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))) && 
          (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) == ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))))
     Result = Kernel.ctrue;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmEqualxyc(PalmEqualxyc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmEqualxyc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmEqualxyc(PalmEqualxyc *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmEqualxyc) [] */
ClaireBoolean * palm_checkPalm_PalmEqualxyc(PalmEqualxyc *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanisms
// claire3 port register no longer used
// *******
// *** PalmGreaterOrEqualxyc : U >= V + c
// class definition
// pretty printing 
/* The c++ function for: self_print(c:PalmGreaterOrEqualxyc) [] */
OID  claire_self_print_PalmGreaterOrEqualxyc_palm(PalmGreaterOrEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" >= ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// a propagation tool
// a tool for ordered constraints
/* The c++ function for: hasSupport(c:PalmGreaterOrEqualxyc,idx:integer,x:integer) [] */
ClaireBoolean * palm_hasSupport_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * ret = CFALSE;
      if (idx == 1)
       { OID gc_local;
        ITERATE(v);
        bag *v_support;
        v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket)))))))));
        for (START(v_support); NEXT(v);)
        { GC_LOOP;
          if (v <= (x-c->cste))
           { ret= CTRUE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      else { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket)))))))));
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if ((x+c->cste) <= v)
             { ret= CTRUE;
              { ;break;} 
              } 
            GC_UNLOOP;} 
          } 
        Result = ret;
      } 
    GC_UNBIND; return (Result);} 
  } 


// awakening the constraint
/* The c++ function for: choco/propagate(c:PalmGreaterOrEqualxyc) [] */
void  choco_propagate_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ if (CLREAD(PalmIntVar,c->v1,bucket) != (NULL)) 
  { { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
      PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b1)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmGreaterOrEqualxyc(c,1,x) != CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { int  y = (b2->offset+1);
              int  g0056 = (x-c->cste);
              { OID gc_local;
                while ((y <= g0056))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,y,expl);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b2)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmGreaterOrEqualxyc(c,2,x) != CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { int  y = (x+c->cste);
              int  g0057 = (b1->offset+b1->bucketSize);
              { OID gc_local;
                while ((y <= g0057))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,expl);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,expl);
            (*choco.removeVal)(GC_OID(_oid_(c->v2)),
              x,
              c->idx2,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      } 
     } 
  else{ GC_BIND;
    choco_awakeOnInf_PalmGreaterOrEqualxyc(c,2);
    choco_awakeOnSup_PalmGreaterOrEqualxyc(c,1);
    GC_UNBIND;} 
  } 


// reaction to INCINF
/* The c++ function for: choco/awakeOnInf(c:PalmGreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx)
{ if (idx == 2) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),1,e);
      (*choco.updateInf)(GC_OID(_oid_(c->v1)),
        (c->v2->inf->latestValue+c->cste),
        c->idx1,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmGreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx)
{ if (idx == 1) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,e);
      (*choco.updateSup)(GC_OID(_oid_(c->v2)),
        (c->v1->sup->latestValue-c->cste),
        c->idx2,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmGreaterOrEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx)
{ if (idx == 1)
   choco_awakeOnInf_PalmGreaterOrEqualxyc(c,2);
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmGreaterOrEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx)
{ if (idx == 2)
   choco_awakeOnSup_PalmGreaterOrEqualxyc(c,1);
  } 


// reaction to REMVAL
/* The c++ function for: choco/awakeOnRem(c:PalmGreaterOrEqualxyc,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int v)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
    PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
    if (idx == 1)
     { OID gc_local;
      ITERATE(x);
      bag *x_support;
      x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b2)))));
      for (START(x_support); NEXT(x);)
      { GC_LOOP;
        if (palm_hasSupport_PalmGreaterOrEqualxyc(c,2,x) != CTRUE)
         { Explanation * e;
          { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              e = _CL_obj;
              } 
            GC_OBJECT(Explanation,e);} 
          palm_self_explain_AbstractConstraint(c,e);
          { int  y = (x+c->cste);
            int  g0058 = (b1->offset+b1->bucketSize);
            { OID gc_local;
              while ((y <= g0058))
              { GC_LOOP;
                palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,e);
                ++y;
                GC_UNLOOP;} 
              } 
            } 
          (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            x,
            c->idx2,
            _oid_(e));
          } 
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b1)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmGreaterOrEqualxyc(c,1,x) != CTRUE)
           { Explanation * e;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                e = _CL_obj;
                } 
              GC_OBJECT(Explanation,e);} 
            palm_self_explain_AbstractConstraint(c,e);
            { int  y = (b2->offset+1);
              int  g0059 = (x-c->cste);
              { OID gc_local;
                while ((y <= g0059))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,y,e);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(e));
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmGreaterOrEqualxyc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx,int v)
{ if (idx == 1) 
  { if (palm_hasSupport_PalmGreaterOrEqualxyc(c,1,v) != CTRUE)
     { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      { int  x = (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset)+1);
        int  g0060 = (v-c->cste);
        { OID gc_local;
          while ((x <= g0060))
          { GC_LOOP;
            palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,x,e);
            ++x;
            GC_UNLOOP;} 
          } 
        } 
      (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        v,
        c->idx1,
        _oid_(e));
      } 
     } 
  else{ if (palm_hasSupport_PalmGreaterOrEqualxyc(c,2,v) != CTRUE) 
    { { Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        palm_self_explain_AbstractConstraint(c,e);
        { int  y = (v+c->cste);
          int  g0061 = (CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset)+CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),bucketSize));
          { OID gc_local;
            while ((y <= g0061))
            { GC_LOOP;
              palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,e);
              ++y;
              GC_UNLOOP;} 
            } 
          } 
        (*choco.removeVal)(GC_OID(_oid_(c->v2)),
          v,
          c->idx2,
          _oid_(e));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsFalse(c:PalmGreaterOrEqualxyc) [] */
set * palm_whyIsFalse_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),1,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsTrue(c:PalmGreaterOrEqualxyc) [] */
set * palm_whyIsTrue_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),2,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// unused but necessary for compilation
/* The c++ function for: choco/awakeOnInst(c:PalmGreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmGreaterOrEqualxyc) [] */
OID  choco_askIfEntailed_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ GC_BIND;
  { OID Result = 0;
    if (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) < ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))
     Result = Kernel.cfalse;
    else if (((choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste) <= choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))))
     Result = Kernel.ctrue;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmGreaterOrEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmGreaterOrEqualxyc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmGreaterOrEqualxyc) [] */
ClaireBoolean * palm_checkPalm_PalmGreaterOrEqualxyc(PalmGreaterOrEqualxyc *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanisms
// claire3 port register no longer used
// *******
// *** PalmLessOrEqualxyc : U <= V + c
// class definition
// pretty printing
/* The c++ function for: self_print(c:PalmLessOrEqualxyc) [] */
OID  claire_self_print_PalmLessOrEqualxyc_palm(PalmLessOrEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" <= ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  return (_void_(princ_string("")));} 


// tool for propagation
/* The c++ function for: hasSupport(c:PalmLessOrEqualxyc,idx:integer,x:integer) [] */
ClaireBoolean * palm_hasSupport_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * ret = CFALSE;
      if (idx == 1)
       { OID gc_local;
        ITERATE(v);
        bag *v_support;
        v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket)))))))));
        for (START(v_support); NEXT(v);)
        { GC_LOOP;
          if ((x-c->cste) <= v)
           { ret= CTRUE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      else { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket)))))))));
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            if (v <= (x+c->cste))
             { ret= CTRUE;
              { ;break;} 
              } 
            GC_UNLOOP;} 
          } 
        Result = ret;
      } 
    GC_UNBIND; return (Result);} 
  } 


// awakening the constraint
/* The c++ function for: choco/propagate(c:PalmLessOrEqualxyc) [] */
void  choco_propagate_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ if (CLREAD(PalmIntVar,c->v1,bucket) != (NULL)) 
  { { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
      PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b1)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmLessOrEqualxyc(c,1,x) != CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { int  y = (x-c->cste);
              int  g0062 = (b2->offset+b2->bucketSize);
              { OID gc_local;
                while ((y <= g0062))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,y,expl);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b2)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmLessOrEqualxyc(c,2,x) != CTRUE)
           { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { int  y = (b1->offset+1);
              int  g0063 = (x+c->cste);
              { OID gc_local;
                while ((y <= g0063))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,expl);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,expl);
            (*choco.removeVal)(GC_OID(_oid_(c->v2)),
              x,
              c->idx2,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      } 
     } 
  else{ GC_BIND;
    choco_awakeOnInf_PalmLessOrEqualxyc(c,1);
    choco_awakeOnSup_PalmLessOrEqualxyc(c,2);
    GC_UNBIND;} 
  } 


// reaction to INCINF
/* The c++ function for: choco/awakeOnInf(c:PalmLessOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx)
{ if (idx == 1) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,e);
      palm_self_explain_AbstractConstraint(c,e);
      (*choco.updateInf)(GC_OID(_oid_(c->v2)),
        (c->v1->inf->latestValue-c->cste),
        c->idx2,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmLessOrEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx)
{ if (idx == 2) 
  { { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),2,e);
      palm_self_explain_AbstractConstraint(c,e);
      (*choco.updateSup)(GC_OID(_oid_(c->v1)),
        (c->v2->sup->latestValue+c->cste),
        c->idx1,
        _oid_(e));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmLessOrEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx)
{ if (idx == 2)
   choco_awakeOnInf_PalmLessOrEqualxyc(c,1);
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmLessOrEqualxyc,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx)
{ if (idx == 1)
   choco_awakeOnSup_PalmLessOrEqualxyc(c,2);
  } 


// reaction to REMVAL
/* The c++ function for: choco/awakeOnRem(c:PalmLessOrEqualxyc,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int v)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
    PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
    if (idx == 1)
     { OID gc_local;
      ITERATE(x);
      bag *x_support;
      x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b2)))));
      for (START(x_support); NEXT(x);)
      { GC_LOOP;
        if (palm_hasSupport_PalmLessOrEqualxyc(c,2,x) != CTRUE)
         { Explanation * e;
          { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              e = _CL_obj;
              } 
            GC_OBJECT(Explanation,e);} 
          palm_self_explain_AbstractConstraint(c,e);
          { int  y = (b1->offset+1);
            int  g0064 = (x-c->cste);
            { OID gc_local;
              while ((y <= g0064))
              { GC_LOOP;
                palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,e);
                ++y;
                GC_UNLOOP;} 
              } 
            } 
          (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            x,
            c->idx2,
            _oid_(e));
          } 
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) b1)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (palm_hasSupport_PalmLessOrEqualxyc(c,1,x) != CTRUE)
           { Explanation * e;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                e = _CL_obj;
                } 
              GC_OBJECT(Explanation,e);} 
            palm_self_explain_AbstractConstraint(c,e);
            { int  y = (x+c->cste);
              int  g0065 = (b2->offset+b2->bucketSize);
              { OID gc_local;
                while ((y <= g0065))
                { GC_LOOP;
                  palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,y,e);
                  ++y;
                  GC_UNLOOP;} 
                } 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(e));
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// reaction to RESVAL
/* The c++ function for: awakeOnRestoreVal(c:PalmLessOrEqualxyc,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx,int v)
{ GC_BIND;
  { PalmIntDomain * b1 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v1,bucket)));
    PalmIntDomain * b2 = GC_OBJECT(PalmIntDomain,((PalmIntDomain *) CLREAD(PalmIntVar,c->v2,bucket)));
    if (idx == 1)
     { if (palm_hasSupport_PalmLessOrEqualxyc(c,1,v) != CTRUE)
       { Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        palm_self_explain_AbstractConstraint(c,e);
        { int  x = (v-c->cste);
          int  g0066 = (b1->offset+b1->bucketSize);
          { OID gc_local;
            while ((x <= g0066))
            { GC_LOOP;
              palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,x,e);
              ++x;
              GC_UNLOOP;} 
            } 
          } 
        (*choco.removeVal)(GC_OID(_oid_(c->v1)),
          v,
          c->idx1,
          _oid_(e));
        } 
      } 
    else if (palm_hasSupport_PalmLessOrEqualxyc(c,2,v) != CTRUE)
     { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      { int  y = (b1->offset+1);
        int  g0067 = (v+c->cste);
        { OID gc_local;
          while ((y <= g0067))
          { GC_LOOP;
            palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,y,e);
            ++y;
            GC_UNLOOP;} 
          } 
        } 
      (*choco.removeVal)(GC_OID(_oid_(c->v2)),
        v,
        c->idx2,
        _oid_(e));
      } 
    } 
  GC_UNBIND;} 


// defined for boolean connectors
/* The c++ function for: whyIsFalse(c:PalmLessOrEqualxyc) [] */
set * palm_whyIsFalse_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),1,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),2,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// defined for boolean connectors
/* The c++ function for: whyIsTrue(c:PalmLessOrEqualxyc) [] */
set * palm_whyIsTrue_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),2,expl);
      palm_self_explain_PalmIntVar1(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),1,expl);
      Result = expl->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


// unused but necessary for compilation
/* The c++ function for: choco/awakeOnInst(c:PalmLessOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmLessOrEqualxyc) [] */
OID  choco_askIfEntailed_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ GC_BIND;
  { OID Result = 0;
    if (choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) > ((choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))
     Result = Kernel.cfalse;
    else if (choco.getSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1))))) <= ((choco.getInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2))))))+c->cste))
     Result = Kernel.ctrue;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmLessOrEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ return (CTRUE);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmLessOrEqualxyc) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmLessOrEqualxyc(PalmLessOrEqualxyc *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmLessOrEqualxyc) [] */
ClaireBoolean * palm_checkPalm_PalmLessOrEqualxyc(PalmLessOrEqualxyc *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanisms
// claire3 port register no longer used
// ********************************************************************
// *   Part 9 : Linear combinations of variables                      *
// ********************************************************************
// * 1: definition, API and constraint network tools 
// class definition : inheritance from the correspoding constraint in choco
// pretty printing
/* The c++ function for: self_print(self:PalmLinComb) [] */
OID  claire_self_print_PalmLinComb_palm(PalmLinComb *self)
{ GC_BIND;
  { OID Result = 0;
    { OID * ac = GC_ARRAY(self->coefs);
      list * av = GC_OBJECT(list,self->vars);
      int  npv = self->nbPosVars;
      int  nnv = (self->nbVars-npv);
      if (npv > 0)
       { { int  coef = ((OID *) ac)[1];
          IntVar * var = OBJECT(IntVar,(*(av))[1]);
          if (coef != 0)
           { if (coef != 1)
             { print_any(coef);
              princ_string("*");
              princ_string(var->name);
              princ_string("");
              } 
            else { princ_string(var->name);
                princ_string("");
                } 
              } 
          } 
        { int  i = 2;
          int  g0068 = npv;
          { OID gc_local;
            while ((i <= g0068))
            { // HOHO, GC_LOOP not needed !
              { int  coef = ((OID *) ac)[i];
                IntVar * var = OBJECT(IntVar,(*(av))[i]);
                if (coef != 1)
                 { princ_string(" + ");
                  print_any(coef);
                  princ_string("*");
                  princ_string(var->name);
                  princ_string("");
                  } 
                else { princ_string(" + ");
                    princ_string(var->name);
                    princ_string("");
                    } 
                  } 
              ++i;
              } 
            } 
          } 
        if (self->cste > 0)
         { princ_string(" + ");
          print_any(self->cste);
          princ_string("");
          } 
        } 
      else if (self->cste > 0)
       { print_any(self->cste);
        princ_string("");
        } 
      else princ_string("0");
        if (self->op == 1)
       princ_string(" == ");
      else if (self->op == 3)
       princ_string(" <> ");
      else princ_string(" >= ");
        if (nnv > 0)
       { { int  coef = ((OID *) ac)[(npv+1)];
          IntVar * var = OBJECT(IntVar,(*(av))[(npv+1)]);
          if (coef != 0)
           { if ((0-coef) != 1)
             { print_any((0-coef));
              princ_string("*");
              princ_string(var->name);
              princ_string("");
              } 
            else { princ_string(var->name);
                princ_string("");
                } 
              } 
          } 
        { int  i = (npv+2);
          int  g0069 = self->nbVars;
          { OID gc_local;
            while ((i <= g0069))
            { // HOHO, GC_LOOP not needed !
              { int  coef = ((OID *) ac)[i];
                IntVar * var = OBJECT(IntVar,(*(av))[i]);
                if ((0-coef) != 1)
                 { princ_string(" + ");
                  print_any((0-coef));
                  princ_string("*");
                  princ_string(var->name);
                  princ_string("");
                  } 
                else { princ_string(" + ");
                    princ_string(var->name);
                    princ_string("");
                    } 
                  } 
              ++i;
              } 
            } 
          } 
        if (self->cste < 0)
         { princ_string(" + ");
          print_any((0-self->cste));
          princ_string("");
          } 
        else Result = Kernel.cfalse;
          } 
      else if (self->cste < 0)
       { print_any((0-self->cste));
        princ_string("");
        } 
      else princ_string("0");
        } 
    GC_UNBIND; return (Result);} 
  } 


// PalmLinComb need to awake on RemoveVal ... 
/* The c++ function for: choco/assignIndices(c:PalmLinComb,root:(choco/CompositeConstraint U choco/Delayer),i:integer) [] */
int  choco_assignIndices_PalmLinComb(PalmLinComb *c,AbstractConstraint *root,int i)
{ { int Result = 0;
    { int  j = i;
      { int  k = 1;
        int  g0070 = c->nbPosVars;
        { OID gc_local;
          while ((k <= g0070))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint2(root,
              OBJECT(IntVar,(*(c->vars))[k]),
              j,
              CTRUE,
              _I_equal_any(c->op,2),
              CTRUE,
              CTRUE);
            ((*(c->indices))[k]=OBJECT(AbstractVar,(*(c->vars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      { int  k = (c->nbPosVars+1);
        int  g0071 = c->nbVars;
        { OID gc_local;
          while ((k <= g0071))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint2(root,
              OBJECT(IntVar,(*(c->vars))[k]),
              j,
              CTRUE,
              CTRUE,
              _I_equal_any(c->op,2),
              CTRUE);
            ((*(c->indices))[k]=OBJECT(AbstractVar,(*(c->vars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      Result = j;
      } 
    return (Result);} 
  } 


// API for creating such constraints
/* The c++ function for: makePalmLinComb(l1:list[integer],l2:list[choco/IntVar],c:integer,opn:integer) [] */
PalmLinComb * palm_makePalmLinComb_list(list *l1,list *l2,int c,int opn)
{ GC_BIND;
  ;{ PalmLinComb *Result ;
    { list * posCoefs = list::empty(Kernel._integer);
      list * negCoefs = list::empty(Kernel._integer);
      list * posVars = list::empty(choco._IntVar);
      list * negVars = list::empty(choco._IntVar);
      { int  j = 1;
        int  g0072 = l2->length;
        { OID gc_local;
          while ((j <= g0072))
          { // HOHO, GC_LOOP not needed !
            if ((*(l1))[j] > 0)
             { posCoefs= posCoefs->addFast((*(l1))[j]);
              posVars= posVars->addFast((*(l2))[j]);
              } 
            else if ((*(l1))[j] < 0)
             { negCoefs= negCoefs->addFast((*(l1))[j]);
              negVars= negVars->addFast((*(l2))[j]);
              } 
            ++j;
            } 
          } 
        } 
      { PalmLinComb * cont;
        { { PalmLinComb * _CL_obj = ((PalmLinComb *) GC_OBJECT(PalmLinComb,new_object_class(palm._PalmLinComb)));
            (_CL_obj->vars = append_list(posVars,negVars));
            (_CL_obj->cste = c);
            cont = _CL_obj;
            } 
          GC_OBJECT(PalmLinComb,cont);} 
        (cont->nbVars = cont->vars->length);
        (cont->indices = make_list_integer2(cont->nbVars,Kernel._integer,0));
        { AbstractConstraint * g0073; 
          OID  g0074;
          g0073 = cont;
          { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
            g0074 = _oid_(_CL_obj);
            } 
          (g0073->hook = g0074);} 
        (cont->op = opn);
        (cont->nbPosVars = posCoefs->length);
        (cont->coefs = array_I_list(append_list(posCoefs,negCoefs)));
        Result = cont;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// * 2: Propagation mechanisms                          
/* The c++ function for: updateDataStructuresOnConstraint(c:PalmLinComb,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmLinComb_palm(PalmLinComb *c,int idx,int way,int newValue,int oldValue)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:PalmLinComb,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmLinComb_palm(PalmLinComb *c,int idx,int way,int newValue,int oldValue)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  } 


// The lincomb constraint is a delayed constraint and therefore
// need specific propagation mechanisms
// Explaining the current lower bound 
/* The c++ function for: explainVariablesLB(c:PalmLinComb) [] */
Explanation * palm_explainVariablesLB_PalmLinComb(PalmLinComb *c)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      { int  i = 1;
        int  g0075 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0075))
          { // HOHO, GC_LOOP not needed !
            palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,(*(c->vars))[i]),1,expl);
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0076 = c->nbVars;
        { OID gc_local;
          while ((i <= g0076))
          { // HOHO, GC_LOOP not needed !
            palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,(*(c->vars))[i]),2,expl);
            ++i;
            } 
          } 
        } 
      Result = expl;
      } 
    GC_UNBIND; return (Result);} 
  } 


// explaining the current upper bound
/* The c++ function for: explainVariablesUB(c:PalmLinComb) [] */
Explanation * palm_explainVariablesUB_PalmLinComb(PalmLinComb *c)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      palm_self_explain_AbstractConstraint(c,expl);
      { int  i = 1;
        int  g0077 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0077))
          { // HOHO, GC_LOOP not needed !
            palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,(*(c->vars))[i]),2,expl);
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0078 = c->nbVars;
        { OID gc_local;
          while ((i <= g0078))
          { // HOHO, GC_LOOP not needed !
            palm_self_explain_PalmIntVar1(OBJECT(PalmIntVar,(*(c->vars))[i]),1,expl);
            ++i;
            } 
          } 
        } 
      Result = expl;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.05 added a few casts
/* The c++ function for: choco/computeUpperBound(c:PalmLinComb) [] */
int  choco_computeUpperBound_PalmLinComb_palm(PalmLinComb *c)
{ { int Result = 0;
    { int  s = 0;
      { int  i = 1;
        int  g0079 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0079))
          { // HOHO, GC_LOOP not needed !
            s= (s+((choco.getSup->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0080 = c->nbVars;
        { OID gc_local;
          while ((i <= g0080))
          { // HOHO, GC_LOOP not needed !
            s= (s+((choco.getInf->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      Result = (s+c->cste);
      } 
    return (Result);} 
  } 


// lower bound estimate of a linear combination of variables
//
// v1.05 added a few casts
/* The c++ function for: choco/computeLowerBound(c:PalmLinComb) [] */
int  choco_computeLowerBound_PalmLinComb_palm(PalmLinComb *c)
{ { int Result = 0;
    { int  s = 0;
      { int  i = 1;
        int  g0081 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0081))
          { // HOHO, GC_LOOP not needed !
            s= (s+((choco.getInf->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0082 = c->nbVars;
        { OID gc_local;
          while ((i <= g0082))
          { // HOHO, GC_LOOP not needed !
            s= (s+((choco.getSup->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      Result = (s+c->cste);
      } 
    return (Result);} 
  } 


// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
/* The c++ function for: choco/propagateNewLowerBound(c:PalmLinComb,mylb:integer) [] */
ClaireBoolean * choco_propagateNewLowerBound_PalmLinComb_palm(PalmLinComb *c,int mylb)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * anyChange = CFALSE;
      if (mylb > 0)
       palm_raisePalmFakeContradiction_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) choco_getProblem_AbstractConstraint(c)->propagationEngine)),GC_OBJECT(Explanation,palm_explainVariablesLB_PalmLinComb(c)));
      { Explanation * expl = GC_OBJECT(Explanation,palm_explainVariablesLB_PalmLinComb(c));
        { int  i = 1;
          int  g0083 = c->nbPosVars;
          { OID gc_local;
            while ((i <= g0083))
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,(*choco.updateSup)((*(c->vars))[i],
                (choco_div_dash_integer((-mylb),((OID *) c->coefs)[i])+(choco.getInf->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))),
                (*(c->indices))[i],
                GC_OID(_oid_(palm_clone_Explanation(expl)))))) == CTRUE)
               anyChange= CTRUE;
              ++i;
              GC_UNLOOP;} 
            } 
          } 
        { int  i = (c->nbPosVars+1);
          int  g0084 = c->nbVars;
          { OID gc_local;
            while ((i <= g0084))
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,(*choco.updateInf)((*(c->vars))[i],
                (choco_div_plus_integer(mylb,(-((OID *) c->coefs)[i]))+(choco.getSup->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))),
                (*(c->indices))[i],
                GC_OID(_oid_(palm_clone_Explanation(expl)))))) == CTRUE)
               anyChange= CTRUE;
              ++i;
              GC_UNLOOP;} 
            } 
          } 
        Result = anyChange;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
/* The c++ function for: choco/propagateNewUpperBound(c:PalmLinComb,myub:integer) [] */
ClaireBoolean * choco_propagateNewUpperBound_PalmLinComb_palm(PalmLinComb *c,int myub)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * anyChange = CFALSE;
      Explanation * expl = GC_OBJECT(Explanation,palm_explainVariablesUB_PalmLinComb(c));
      if (myub < 0)
       palm_raisePalmFakeContradiction_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) choco_getProblem_AbstractConstraint(c)->propagationEngine)),GC_OBJECT(Explanation,palm_explainVariablesUB_PalmLinComb(c)));
      { int  i = 1;
        int  g0085 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0085))
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,(*choco.updateInf)((*(c->vars))[i],
              (choco_div_plus_integer((-myub),((OID *) c->coefs)[i])+(choco.getSup->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))),
              (*(c->indices))[i],
              GC_OID(_oid_(palm_clone_Explanation(expl)))))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0086 = c->nbVars;
        { OID gc_local;
          while ((i <= g0086))
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,(*choco.updateSup)((*(c->vars))[i],
              (choco_div_dash_integer(myub,(-((OID *) c->coefs)[i]))+(choco.getInf->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))),
              (*(c->indices))[i],
              GC_OID(_oid_(palm_clone_Explanation(expl)))))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = anyChange;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.30: may only be waken by Boolean combinations of linear constraints:
// Therefore, propagate without delay (don't set flags, ...)
// Delayed linear constraints are stored in pb.delayedConstraint and appear within a Delayer object
// the delayer handles the needToAwake flag, delegates the event abstraction job and wakes up
// the delayed constraint through calls to onePropagation
// v0.33 <thb>: this same function is used for propagating immediately or delayed
/* The c++ function for: choco/propagate(c:PalmLinComb) [] */
void  choco_propagate_PalmLinComb(PalmLinComb *c)
{ if ((c->improvedLowerBound == CTRUE) && 
      (c->improvedUpperBound == CTRUE))
   choco_filter_LinComb(c,CTRUE,2);
  else if (c->improvedLowerBound == CTRUE)
   choco_filter_LinComb(c,CTRUE,1);
  else if (c->improvedUpperBound == CTRUE)
   choco_filter_LinComb(c,CFALSE,1);
  } 


// apply UB rule at least once and ocsillate if this produces new inferences
// awakening the constraint
/* The c++ function for: choco/awake(c:PalmLinComb) [] */
void  choco_awake_PalmLinComb_palm(PalmLinComb *c)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  choco_filter_LinComb(c,CTRUE,2);
  ;} 


// reaction to INCINF
/* The c++ function for: choco/awakeOnInf(c:PalmLinComb,idx:integer) [] */
void  choco_awakeOnInf_PalmLinComb(PalmLinComb *c,int idx)
{ if (idx <= c->nbPosVars)
   choco_filter_LinComb(c,CTRUE,1);
  else choco_filter_LinComb(c,CFALSE,1);
    } 


// reaction to DECSUP
/* The c++ function for: choco/awakeOnSup(c:PalmLinComb,idx:integer) [] */
void  choco_awakeOnSup_PalmLinComb(PalmLinComb *c,int idx)
{ if (idx <= c->nbPosVars)
   choco_filter_LinComb(c,CFALSE,1);
  else choco_filter_LinComb(c,CTRUE,1);
    } 


/* The c++ function for: choco/awakeOnRem(c:PalmLinComb,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmLinComb(PalmLinComb *c,int idx,int v)
{ choco_filter_LinComb(c,CTRUE,2);
  } 


// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(c:PalmLinComb,idx:integer) [] */
void  palm_awakeOnRestoreInf_PalmLinComb(PalmLinComb *c,int idx)
{ choco_filter_LinComb(c,CTRUE,2);
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(c:PalmLinComb,idx:integer) [] */
void  palm_awakeOnRestoreSup_PalmLinComb(PalmLinComb *c,int idx)
{ choco_filter_LinComb(c,CTRUE,2);
  } 


/* The c++ function for: awakeOnRestoreVal(c:PalmLinComb,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmLinComb(PalmLinComb *c,int idx,int v)
{ choco_filter_LinComb(c,CTRUE,2);
  } 


// abstracting reaction to DECINF
/* The c++ function for: abstractDecInf(c:PalmLinComb,i:integer) [] */
ClaireBoolean * palm_abstractDecInf_PalmLinComb(PalmLinComb *c,int i)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  return (CTRUE);} 


// abstracting reaction to INCSUP
/* The c++ function for: abstractIncSup(c:PalmLinComb,i:integer) [] */
ClaireBoolean * palm_abstractIncSup_PalmLinComb(PalmLinComb *c,int i)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  return (CTRUE);} 


/* The c++ function for: abstractRestoreVal(c:PalmLinComb,i:integer,v:integer) [] */
ClaireBoolean * palm_abstractRestoreVal_PalmLinComb(PalmLinComb *c,int i,int v)
{ STOREO(c->improvedUpperBound,CTRUE);
  STOREO(c->improvedLowerBound,CTRUE);
  return (CTRUE);} 


// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: choco/askIfEntailed(c:PalmLinComb) [] */
OID  choco_askIfEntailed_PalmLinComb(PalmLinComb *c)
{ { OID Result = 0;
    if (c->op == 1)
     { int  a = (*choco.computeLowerBound)(_oid_(c));
      int  b = (*choco.computeUpperBound)(_oid_(c));
      if ((b < 0) || 
          (a > 0))
       Result = Kernel.cfalse;
      else if ((a == 0) && 
          (b == 0))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    else if (c->op == 2)
     { if ((*choco.computeUpperBound)(_oid_(c)) < 0)
       Result = Kernel.cfalse;
      else if (0 <= (*choco.computeLowerBound)(_oid_(c)))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    else { { int  a = (*choco.computeLowerBound)(_oid_(c));
          if (a > 0)
           Result = Kernel.ctrue;
          else { int  b = (*choco.computeUpperBound)(_oid_(c));
              if (b < 0)
               Result = Kernel.ctrue;
              else if ((b == 0) && 
                  (a == 0))
               Result = Kernel.cfalse;
              else Result = Kernel.ctrue;
                } 
            } 
        } 
      return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmLinComb) [] */
ClaireBoolean * choco_testIfSatisfied_PalmLinComb(PalmLinComb *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,(*Kernel.funcall)(GC_OID(_oid_(_at_property1(choco.testIfSatisfied,OBJECT(ClaireClass,palm.LinComb->value)))),
      _oid_(c)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/awakeOnInst(c:PalmLinComb,idx:integer) [] */
void  choco_awakeOnInst_PalmLinComb(PalmLinComb *c,int idx)
{ ;} 


/* The c++ function for: checkPalm(ct:PalmLinComb) [] */
ClaireBoolean * palm_checkPalm_PalmLinComb(PalmLinComb *ct)
{ return (CTRUE);} 


// registering the constraint within chcco mechanismss
// claire3 port register no longer used
// ********************************************************************
// *   Part 10 : Handling delayers within PaLMchoco                   *
// ********************************************************************
// reaction to DECINF
/* The c++ function for: awakeOnRestoreInf(d:choco/Delayer,idx:integer) [] */
void  palm_awakeOnRestoreInf_Delayer(Delayer *d,int idx)
{ if ((OBJECT(ClaireBoolean,(*palm.abstractDecInf)(GC_OID(_oid_(d->target)),
    idx))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// reaction to INCSUP
/* The c++ function for: awakeOnRestoreSup(d:choco/Delayer,idx:integer) [] */
void  palm_awakeOnRestoreSup_Delayer(Delayer *d,int idx)
{ if ((OBJECT(ClaireBoolean,(*palm.abstractIncSup)(GC_OID(_oid_(d->target)),
    idx))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(d:choco/Delayer,idx:integer,x:integer) [] */
void  palm_awakeOnRestoreVal_Delayer(Delayer *d,int idx,int x)
{ if ((OBJECT(ClaireBoolean,(*palm.abstractRestoreVal)(GC_OID(_oid_(d->target)),
    idx,
    x))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// making any constraint delayable ...
/* The c++ function for: abstractDecInf(c:choco/AbstractConstraint,i:integer) [] */
ClaireBoolean * palm_abstractDecInf_AbstractConstraint(AbstractConstraint *c,int i)
{ return (CTRUE);} 


/* The c++ function for: abstractIncSup(c:choco/AbstractConstraint,i:integer) [] */
ClaireBoolean * palm_abstractIncSup_AbstractConstraint(AbstractConstraint *c,int i)
{ return (CTRUE);} 


/* The c++ function for: abstractRestoreVal(c:choco/AbstractConstraint,i:integer,x:integer) [] */
ClaireBoolean * palm_abstractRestoreVal_AbstractConstraint(AbstractConstraint *c,int i,int x)
{ return (CTRUE);} 


// relaying DS maintenance through the target constraint ... 
/* The c++ function for: updateDataStructuresOnConstraint(d:choco/Delayer,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_Delayer_palm(Delayer *d,int idx,int way,int newValue,int oldValue)
{ GC_BIND;
  (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(d->target)),
    idx,
    way,
    newValue,
    oldValue);
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(d:choco/Delayer,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_Delayer_palm(Delayer *d,int idx,int way,int newValue,int oldValue)
{ GC_BIND;
  (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(d->target)),
    idx,
    way,
    newValue,
    oldValue);
  GC_UNBIND;} 

