/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\setconst.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:30 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 1.330 sep. 9th 2002                               *
// * file: sets.cl                                                    *
// *    domains, propagation and constraint for set variables         *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: Generic set constraint classes                         *
// *   Part 2: set cardinality                                        *
// *   Part 3: member/notMember for integer constants                 *
// *   Part 4: member/notMember for IntVars                           *
// *   Part 5: union/intersection constraints                         *
// *   Part 6: bin comparisons (subset/disjoint/equal/overlap)        *
// *   Part 12: partition constraint                                  *
// *   Part 12: scalar constraint                                     *
// *   Part 13: enumeration constructor                               *
// *   Part 14: cons constraint                                       *
// --------------------------------------------------------------------
// verbosity for set variable propagation
// ********************************************************************
// *   Part 1: Generic set constraint classes                         *
// ********************************************************************
// An abstract class for all constraints involving at least one set variable
/* The c++ function for: computeVarIdxInOpposite(c:SetConstraint,i:integer) [] */
int  choco_computeVarIdxInOpposite_SetConstraint(SetConstraint *c,int i)
{ return (i);} 


// An abstract class for constraints involving a set variable and a constant
/* The c++ function for: testIfCompletelyInstantiated(c:UnSetConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_UnSetConstraint(UnSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:UnSetConstraint) [] */
int  choco_getNbVars_UnSetConstraint(UnSetConstraint *c)
{ return (1);} 


/* The c++ function for: getVar(c:UnSetConstraint,i:integer) [] */
AbstractVar * choco_getVar_UnSetConstraint_choco(UnSetConstraint *c,int i)
{ if (i == 1)
   ;else close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
      _oid_(list::alloc(2,i,_oid_(c))))));
    return (c->sv1);} 


/* The c++ function for: connect(c:UnSetConstraint) [] */
void  choco_connect_UnSetConstraint_choco(UnSetConstraint *c)
{ GC_BIND;
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv1),1);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:UnSetConstraint) [] */
void  choco_disconnect_UnSetConstraint_choco(UnSetConstraint *c)
{ GC_BIND;
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:UnSetConstraint) [] */
void  choco_reconnect_UnSetConstraint_choco(UnSetConstraint *c)
{ GC_BIND;
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: assignIndices(c1:UnSetConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_UnSetConstraint(UnSetConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv1),j);
      choco_setConstraintIndex_UnSetConstraint(c1,1,c1->sv1->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:UnSetConstraint,idx:integer) [] */
int  choco_getConstraintIdx_UnSetConstraint(UnSetConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
        _oid_(list::alloc(2,idx,_oid_(c))))));
      return (Result);} 
  } 


/* The c++ function for: setConstraintIndex(c:UnSetConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_UnSetConstraint(UnSetConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: isActive(c:UnSetConstraint) [] */
ClaireBoolean * choco_isActive_UnSetConstraint_choco(UnSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
    GC_UNBIND; return (Result);} 
  } 


// An abstract class for constraints involving a set variable and an integer variable
/* The c++ function for: testIfCompletelyInstantiated(c:BinSetIntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_BinSetIntConstraint(BinSetIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:BinSetIntConstraint) [] */
int  choco_getNbVars_BinSetIntConstraint(BinSetIntConstraint *c)
{ return (2);} 


/* The c++ function for: getVar(c:BinSetIntConstraint,i:integer) [] */
AbstractVar * choco_getVar_BinSetIntConstraint_choco(BinSetIntConstraint *c,int i)
{ { AbstractVar *Result ;
    if (i == 1)
     Result = c->sv1;
    else if (i == 2)
     Result = c->v2;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = c->sv1;
        } 
      return (Result);} 
  } 


/* The c++ function for: connect(c:BinSetIntConstraint) [] */
void  choco_connect_BinSetIntConstraint_choco(BinSetIntConstraint *c)
{ GC_BIND;
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv1),1);
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v2),2);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:BinSetIntConstraint) [] */
void  choco_disconnect_BinSetIntConstraint_choco(BinSetIntConstraint *c)
{ GC_BIND;
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:BinSetIntConstraint) [] */
void  choco_reconnect_BinSetIntConstraint_choco(BinSetIntConstraint *c)
{ GC_BIND;
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: assignIndices(c1:BinSetIntConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_BinSetIntConstraint(BinSetIntConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv1),j);
      choco_setConstraintIndex_BinSetIntConstraint(c1,1,c1->sv1->constraints->length);
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v2),j);
      choco_setConstraintIndex_BinSetIntConstraint(c1,2,c1->v2->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:BinSetIntConstraint,idx:integer) [] */
int  choco_getConstraintIdx_BinSetIntConstraint(BinSetIntConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else if (idx == 2)
     Result = c->idx2;
    else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
        _oid_(list::alloc(2,idx,_oid_(c))))));
      return (Result);} 
  } 


/* The c++ function for: setConstraintIndex(c:BinSetIntConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_BinSetIntConstraint(BinSetIntConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else if (i == 2)
   (c->idx2 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: isActive(c:BinSetIntConstraint) [] */
ClaireBoolean * choco_isActive_BinSetIntConstraint_choco(BinSetIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1) == CTRUE) ? CTRUE : ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


// An abstract class for constraints involving two set variables
/* The c++ function for: testIfCompletelyInstantiated(c:BinSetConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_BinSetConstraint(BinSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv2)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:BinSetConstraint) [] */
int  choco_getNbVars_BinSetConstraint(BinSetConstraint *c)
{ return (2);} 


/* The c++ function for: getVar(c:BinSetConstraint,i:integer) [] */
AbstractVar * choco_getVar_BinSetConstraint_choco(BinSetConstraint *c,int i)
{ { AbstractVar *Result ;
    if (i == 1)
     Result = c->sv1;
    else if (i == 2)
     Result = c->sv2;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = c->sv1;
        } 
      return (Result);} 
  } 


/* The c++ function for: connect(c:BinSetConstraint) [] */
void  choco_connect_BinSetConstraint_choco(BinSetConstraint *c)
{ GC_BIND;
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv1),1);
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv2),2);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:BinSetConstraint) [] */
void  choco_disconnect_BinSetConstraint_choco(BinSetConstraint *c)
{ GC_BIND;
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:BinSetConstraint) [] */
void  choco_reconnect_BinSetConstraint_choco(BinSetConstraint *c)
{ GC_BIND;
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: assignIndices(c1:BinSetConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_BinSetConstraint(BinSetConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv1),j);
      choco_setConstraintIndex_BinSetConstraint(c1,1,c1->sv1->constraints->length);
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv2),j);
      choco_setConstraintIndex_BinSetConstraint(c1,2,c1->sv2->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:BinSetConstraint,idx:integer) [] */
int  choco_getConstraintIdx_BinSetConstraint(BinSetConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else if (idx == 2)
     Result = c->idx2;
    else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
        _oid_(list::alloc(2,idx,_oid_(c))))));
      return (Result);} 
  } 


/* The c++ function for: setConstraintIndex(c:BinSetConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_BinSetConstraint(BinSetConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else if (i == 2)
   (c->idx2 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: isActive(c:BinSetConstraint) [] */
ClaireBoolean * choco_isActive_BinSetConstraint_choco(BinSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1) == CTRUE) ? CTRUE : ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


// An abstract class for constraints involving two set variables
/* The c++ function for: testIfCompletelyInstantiated(c:TernSetConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_TernSetConstraint(TernSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv2)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv3)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:TernSetConstraint) [] */
int  choco_getNbVars_TernSetConstraint(TernSetConstraint *c)
{ return (3);} 


/* The c++ function for: getVar(c:TernSetConstraint,i:integer) [] */
AbstractVar * choco_getVar_TernSetConstraint_choco(TernSetConstraint *c,int i)
{ { AbstractVar *Result ;
    if (i == 1)
     Result = c->sv1;
    else if (i == 2)
     Result = c->sv2;
    else if (i == 2)
     Result = c->sv3;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = c->sv1;
        } 
      return (Result);} 
  } 


/* The c++ function for: connect(c:TernSetConstraint) [] */
void  choco_connect_TernSetConstraint_choco(TernSetConstraint *c)
{ GC_BIND;
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv1),1);
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv2),2);
  choco_connectSetVar_AbstractConstraint1(c,GC_OBJECT(SetVar,c->sv3),3);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:TernSetConstraint) [] */
void  choco_disconnect_TernSetConstraint_choco(TernSetConstraint *c)
{ GC_BIND;
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2);
  choco_disconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv3),c->idx3);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:TernSetConstraint) [] */
void  choco_reconnect_TernSetConstraint_choco(TernSetConstraint *c)
{ GC_BIND;
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1);
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2);
  choco_reconnectSetVar_SetVar(GC_OBJECT(SetVar,c->sv3),c->idx3);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: assignIndices(c1:TernSetConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_TernSetConstraint(TernSetConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv1),j);
      choco_setConstraintIndex_TernSetConstraint(c1,1,c1->sv1->constraints->length);
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv2),j);
      choco_setConstraintIndex_TernSetConstraint(c1,2,c1->sv2->constraints->length);
      ++j;
      choco_connectSetVar_AbstractConstraint1(root,GC_OBJECT(SetVar,c1->sv3),j);
      choco_setConstraintIndex_TernSetConstraint(c1,3,c1->sv3->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:TernSetConstraint,idx:integer) [] */
int  choco_getConstraintIdx_TernSetConstraint(TernSetConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else if (idx == 2)
     Result = c->idx2;
    else if (idx == 3)
     Result = c->idx3;
    else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
        _oid_(list::alloc(2,idx,_oid_(c))))));
      return (Result);} 
  } 


/* The c++ function for: setConstraintIndex(c:TernSetConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_TernSetConstraint(TernSetConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else if (i == 2)
   (c->idx2 = val);
  else if (i == 3)
   (c->idx3 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: isActive(c:TernSetConstraint) [] */
ClaireBoolean * choco_isActive_TernSetConstraint_choco(TernSetConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv1),c->idx1) == CTRUE) ? CTRUE : ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv2),c->idx2) == CTRUE) ? CTRUE : ((choco_isActiveSetVar_SetVar(GC_OBJECT(SetVar,c->sv3),c->idx3) == CTRUE) ? CTRUE : CFALSE)));
    GC_UNBIND; return (Result);} 
  } 


// TODO: lien entre les fonctions doAwake et awake...
// ********************************************************************
// *   Part 2: set cardinality                                        *
// ********************************************************************
/* The c++ function for: awakeOnKer(c:SetCard,idx:integer) [] */
void  choco_awakeOnKer_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  kerSize = choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))));
    (*choco.updateInf)(_oid_(v),
      kerSize,
      c->idx2);
    if (v->sup->latestValue == kerSize)
     { OID gc_local;
      ITERATE(i);
      bag *i_support;
      i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
      for (START(i_support); NEXT(i);)
      { GC_LOOP;
        if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
         choco_remFromEnveloppe_SetVar(sv,i,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:SetCard,idx:integer) [] */
void  choco_awakeOnEnv_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  envSize = choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))));
    (*choco.updateSup)(_oid_(v),
      envSize,
      c->idx2);
    if (v->inf->latestValue == envSize)
     { OID gc_local;
      ITERATE(i);
      bag *i_support;
      i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
      for (START(i_support); NEXT(i);)
      { GC_LOOP;
        if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
         choco_addToKernel_SetVar(sv,i,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:SetCard,idx:integer) [] */
void  choco_awakeOnInstSet_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    int  kerSize = choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))));
    choco_instantiate_IntVar2(v,kerSize,c->idx2);
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(c:SetCard,idx:integer) [] */
void  choco_awakeOnInf_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  envSize = choco.getEnveloppeSize->fcall(((int) svdom));
    if (v->inf->latestValue > envSize)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (v->inf->latestValue == envSize)
     { { OID gc_local;
        ITERATE(i);
        bag *i_support;
        i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
        for (START(i_support); NEXT(i);)
        { GC_LOOP;
          if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
           choco_addToKernel_SetVar(sv,i,c->idx1);
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:SetCard,idx:integer) [] */
void  choco_awakeOnSup_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  kerSize = choco.getKernelSize->fcall(((int) svdom));
    if (v->sup->latestValue < kerSize)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (v->sup->latestValue == kerSize)
     { { OID gc_local;
        ITERATE(i);
        bag *i_support;
        i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
        for (START(i_support); NEXT(i);)
        { GC_LOOP;
          if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
           choco_remFromEnveloppe_SetVar(sv,i,c->idx1);
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:SetCard,idx:integer) [] */
void  choco_awakeOnInst_SetCard(SetCard *c,int idx)
{ choco_awakeOnVar_SetCard(c,idx);
  } 


/* The c++ function for: awakeOnRem(c:SetCard,idx:integer,val:integer) [] */
void  choco_awakeOnRem_SetCard(SetCard *c,int idx,int val)
{ ;} 


/* The c++ function for: awakeOnVar(c:SetCard,idx:integer) [] */
void  choco_awakeOnVar_SetCard(SetCard *c,int idx)
{ GC_BIND;
  ;{ IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  kerSize = choco.getKernelSize->fcall(((int) svdom));
    int  envSize = choco.getEnveloppeSize->fcall(((int) svdom));
    if ((v->sup->latestValue < kerSize) || 
        (v->inf->latestValue > envSize))
     choco_raiseContradiction_AbstractConstraint(c);
    else if (kerSize < envSize)
     { if (v->inf->latestValue == envSize)
       { OID gc_local;
        ITERATE(i);
        bag *i_support;
        i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
        for (START(i_support); NEXT(i);)
        { GC_LOOP;
          if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
           choco_addToKernel_SetVar(sv,i,c->idx1);
          GC_UNLOOP;} 
        } 
      else if (v->sup->latestValue == kerSize)
       { OID gc_local;
        ITERATE(i);
        bag *i_support;
        i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) svdom)))));
        for (START(i_support); NEXT(i);)
        { GC_LOOP;
          if (belong_to(i,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svdom)))) != CTRUE)
           choco_remFromEnveloppe_SetVar(sv,i,c->idx1);
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:SetCard) [] */
OID  choco_askIfEntailed_SetCard(SetCard *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * v = c->v2;
      SetVar * sv = c->sv1;
      if (choco.getInf->fcall(((int) v)) > choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket))))))
       Result = Kernel.cfalse;
      else if (choco.getSup->fcall(((int) v)) < choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket))))))
       Result = Kernel.cfalse;
      else if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v))))) == CTRUE) && 
          ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) sv))))) == CTRUE))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:SetCard) [] */
ClaireBoolean * choco_testIfSatisfied_SetCard(SetCard *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { IntVar * v = c->v2;
      SetVar * sv = c->sv1;
      { OID  g1122UU;
        { int  V_CL1123;{ V_CL1123 = v->value;
            } 
          
          g1122UU=V_CL1123;} 
        Result = ((g1122UU == choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))))) ? CTRUE : CFALSE);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// same method for awake & propagate
/* The c++ function for: propagate(c:SetCard) [] */
void  choco_propagate_SetCard(SetCard *c)
{ GC_BIND;
  { IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    int  envSize = choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))));
    int  kerSize = choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))));
    if (v->inf->latestValue > envSize)
     choco_raiseContradiction_AbstractConstraint(c);
    if (v->sup->latestValue < kerSize)
     choco_raiseContradiction_AbstractConstraint(c);
    if (v->inf->latestValue == envSize)
     { OID gc_local;
      ITERATE(i);
      bag *i_support;
      i_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(sv));
      for (START(i_support); NEXT(i);)
      { GC_LOOP;
        if (choco_getDomainKernel_SetVar(sv)->memq(i) != CTRUE)
         choco_addToKernel_SetVar(sv,i,c->idx1);
        GC_UNLOOP;} 
      } 
    if (v->sup->latestValue == kerSize)
     { OID gc_local;
      ITERATE(i);
      bag *i_support;
      i_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(sv));
      for (START(i_support); NEXT(i);)
      { GC_LOOP;
        if (choco_getDomainKernel_SetVar(sv)->memq(i) != CTRUE)
         choco_remFromEnveloppe_SetVar(sv,i,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:SetCard) [] */
void  choco_awake_SetCard_choco(SetCard *c)
{ GC_BIND;
  { IntVar * v = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    (*choco.updateInf)(_oid_(v),
      0,
      c->idx2);
    (*choco.updateSup)(_oid_(v),
      GC_OID(choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(sv->setBucket)))))),
      c->idx1);
    choco_propagate_SetCard(c);
    } 
  GC_UNBIND;} 


/* The c++ function for: setCard(sv:SetVar,iv:IntVar) [] */
SetCard * choco_setCard_SetVar(SetVar *sv,IntVar *iv)
{ GC_BIND;
  { SetCard *Result ;
    { SetCard * _CL_obj = ((SetCard *) GC_OBJECT(SetCard,new_object_class(choco._SetCard)));
      (_CL_obj->sv1 = sv);
      (_CL_obj->v2 = iv);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 3: member/notMember for integer constants                 *
// ********************************************************************
// A constraint stating that a constant is a member of a set variable
/* The c++ function for: awakeOnKer(c:MemberC,idx:integer) [] */
void  choco_awakeOnKer_MemberC(MemberC *c,int idx)
{ ;} 


/* The c++ function for: awakeOnEnv(c:MemberC,idx:integer) [] */
void  choco_awakeOnEnv_MemberC(MemberC *c,int idx)
{ GC_BIND;
  ;if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste)))) == CTRUE)
   choco_raiseContradiction_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:MemberC,idx:integer) [] */
void  choco_awakeOnInstSet_MemberC(MemberC *c,int idx)
{ if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste)))) == CTRUE) 
  { choco_raiseContradiction_AbstractConstraint(c);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: askIfEntailed(c:MemberC) [] */
OID  choco_askIfEntailed_MemberC(MemberC *c)
{ GC_BIND;
  { OID Result = 0;
    if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste)))) == CTRUE)
     Result = Kernel.cfalse;
    else if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))))) == CTRUE)
     Result = Kernel.ctrue;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:MemberC) [] */
ClaireBoolean * choco_testIfSatisfied_MemberC(MemberC *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:MemberC) [] */
void  choco_propagate_MemberC(MemberC *c)
{ GC_BIND;
  choco_addToKernel_SetVar(GC_OBJECT(SetVar,c->sv1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awake(c:MemberC) [] */
void  choco_awake_MemberC_choco(MemberC *c)
{ GC_BIND;
  choco_addToKernel_SetVar(GC_OBJECT(SetVar,c->sv1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: memberOf(sv:SetVar,a:integer) [] */
MemberC * choco_memberOf_SetVar1(SetVar *sv,int a)
{ GC_BIND;
  { MemberC *Result ;
    { MemberC * _CL_obj = ((MemberC *) GC_OBJECT(MemberC,new_object_class(choco._MemberC)));
      (_CL_obj->sv1 = sv);
      (_CL_obj->cste = a);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A constraint stating that a constant is not a member of a set variable
/* The c++ function for: awakeOnKer(c:NotMemberC,idx:integer) [] */
void  choco_awakeOnKer_NotMemberC(NotMemberC *c,int idx)
{ GC_BIND;
  ;if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))))) == CTRUE)
   choco_raiseContradiction_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:NotMemberC,idx:integer) [] */
void  choco_awakeOnEnv_NotMemberC(NotMemberC *c,int idx)
{ ;} 


/* The c++ function for: awakeOnInstSet(c:NotMemberC,idx:integer) [] */
void  choco_awakeOnInstSet_NotMemberC(NotMemberC *c,int idx)
{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))))) == CTRUE) 
  { choco_raiseContradiction_AbstractConstraint(c);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: askIfEntailed(c:NotMemberC) [] */
OID  choco_askIfEntailed_NotMemberC(NotMemberC *c)
{ GC_BIND;
  { OID Result = 0;
    if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste)))) == CTRUE)
     Result = Kernel.ctrue;
    else if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))))) == CTRUE)
     Result = Kernel.cfalse;
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:NotMemberC) [] */
ClaireBoolean * choco_testIfSatisfied_NotMemberC(NotMemberC *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->cste))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:NotMemberC) [] */
void  choco_propagate_NotMemberC(NotMemberC *c)
{ GC_BIND;
  choco_remFromEnveloppe_SetVar(GC_OBJECT(SetVar,c->sv1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awake(c:NotMemberC) [] */
void  choco_awake_NotMemberC_choco(NotMemberC *c)
{ GC_BIND;
  choco_remFromEnveloppe_SetVar(GC_OBJECT(SetVar,c->sv1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: notMemberOf(sv:SetVar,a:integer) [] */
NotMemberC * choco_notMemberOf_SetVar1(SetVar *sv,int a)
{ GC_BIND;
  { NotMemberC *Result ;
    { NotMemberC * _CL_obj = ((NotMemberC *) GC_OBJECT(NotMemberC,new_object_class(choco._NotMemberC)));
      (_CL_obj->sv1 = sv);
      (_CL_obj->cste = a);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:MemberC) [] */
NotMemberC * choco_opposite_MemberC(MemberC *c)
{ GC_BIND;
  { NotMemberC *Result ;
    { NotMemberC * _CL_obj = ((NotMemberC *) GC_OBJECT(NotMemberC,new_object_class(choco._NotMemberC)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:NotMemberC) [] */
MemberC * choco_opposite_NotMemberC(NotMemberC *c)
{ GC_BIND;
  { MemberC *Result ;
    { MemberC * _CL_obj = ((MemberC *) GC_OBJECT(MemberC,new_object_class(choco._MemberC)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 4: member/notMember for IntVars                           *
// ********************************************************************
// A constraint stating that a variable is a member of a set variable
/* The c++ function for: awakeOnKer(c:MemberX,idx:integer) [] */
void  choco_awakeOnKer_MemberX(MemberX *c,int idx)
{ ;} 


/* The c++ function for: awakeOnEnv(c:MemberX,idx:integer) [] */
void  choco_awakeOnEnv_MemberX(MemberX *c,int idx)
{ GC_BIND;
  ;{ IntVar * iv = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  nb = 0;
    int  theval = 0;
    (*choco.updateInf)(_oid_(iv),
      GC_OID(choco.getEnveloppeInf->fcall(((int) svdom))),
      c->idx2);
    (*choco.updateSup)(_oid_(iv),
      GC_OID(choco.getEnveloppeSup->fcall(((int) svdom))),
      c->idx2);
    { IntVar * g1124 = iv;
      AbstractIntDomain * g1125 = GC_OBJECT(AbstractIntDomain,g1124->bucket);
      if (g1125 == (NULL))
       { int  val = g1124->inf->latestValue;
        { OID gc_local;
          while ((val <= g1124->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            val= ((g1124->inf->latestValue <= (val+1)) ?
              (val+1) :
              g1124->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g1125->isa,choco._LinkedListIntDomain))
       { int  val = g1124->inf->latestValue;
        { OID gc_local;
          while ((val <= g1124->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            val= choco.getNextValue->fcall(((int) g1125),((int) val));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1125)))));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            GC_UNLOOP;} 
          } 
        } 
    if (nb == 0)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (nb == 1)
     { choco_instantiate_IntVar2(iv,theval,c->idx2);
      choco_addToKernel_SetVar(sv,theval,c->idx1);
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:MemberX,idx:integer) [] */
void  choco_awakeOnInstSet_MemberX(MemberX *c,int idx)
{ choco_awakeOnEnv_MemberX(c,idx);
  } 


/* The c++ function for: awakeOnInst(c:MemberX,idx:integer) [] */
void  choco_awakeOnInst_MemberX(MemberX *c,int idx)
{ GC_BIND;
  choco_addToKernel_SetVar(GC_OBJECT(SetVar,c->sv1),c->v2->value,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awakeOnVar(c:MemberX,idx:integer) [] */
void  choco_awakeOnVar_MemberX(MemberX *c,int idx)
{ GC_BIND;
  ;{ IntVar * iv = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  nb = 0;
    int  theval = 0;
    { IntVar * g1126 = iv;
      AbstractIntDomain * g1127 = GC_OBJECT(AbstractIntDomain,g1126->bucket);
      if (g1127 == (NULL))
       { int  val = g1126->inf->latestValue;
        { OID gc_local;
          while ((val <= g1126->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            val= ((g1126->inf->latestValue <= (val+1)) ?
              (val+1) :
              g1126->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g1127->isa,choco._LinkedListIntDomain))
       { int  val = g1126->inf->latestValue;
        { OID gc_local;
          while ((val <= g1126->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            val= choco.getNextValue->fcall(((int) g1127),((int) val));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1127)))));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
             { ++nb;
              theval= val;
              if (nb > 1)
               { ;break;} 
              } 
            GC_UNLOOP;} 
          } 
        } 
    if (nb == 0)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (nb == 1)
     { choco_instantiate_IntVar2(iv,theval,c->idx2);
      choco_addToKernel_SetVar(sv,theval,c->idx1);
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:MemberX,idx:integer) [] */
void  choco_awakeOnSup_MemberX(MemberX *c,int idx)
{ choco_awakeOnVar_MemberX(c,idx);
  } 


/* The c++ function for: awakeOnInf(c:MemberX,idx:integer) [] */
void  choco_awakeOnInf_MemberX(MemberX *c,int idx)
{ choco_awakeOnVar_MemberX(c,idx);
  } 


/* The c++ function for: awakeOnRem(c:MemberX,idx:integer,val:integer) [] */
void  choco_awakeOnRem_MemberX(MemberX *c,int idx,int val)
{ choco_awakeOnVar_MemberX(c,idx);
  } 


/* The c++ function for: askIfEntailed(c:MemberX) [] */
OID  choco_askIfEntailed_MemberX(MemberX *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * iv = c->v2;
      SetVar * sv = c->sv1;
      AbstractSetDomain * svdom = sv->setBucket;
      int  nb = 0;
      int  theval = 0;
      ClaireBoolean * allValuesOutEnv = CTRUE;
      ClaireBoolean * allValuesInKer = CTRUE;
      { IntVar * g1128 = iv;
        AbstractIntDomain * g1129 = g1128->bucket;
        if (g1129 == (NULL))
         { int  val = g1128->inf->latestValue;
          { OID gc_local;
            while ((val <= g1128->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              val= ((g1128->inf->latestValue <= (val+1)) ?
                (val+1) :
                g1128->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g1129->isa,choco._LinkedListIntDomain))
         { int  val = g1128->inf->latestValue;
          { OID gc_local;
            while ((val <= g1128->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              val= choco.getNextValue->fcall(((int) g1129),((int) val));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1129)))));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              GC_UNLOOP;} 
            } 
          } 
      if (allValuesInKer == CTRUE)
       Result = Kernel.ctrue;
      else if (allValuesOutEnv == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:MemberX) [] */
ClaireBoolean * choco_testIfSatisfied_MemberX(MemberX *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->v2->value))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:MemberX) [] */
void  choco_propagate_MemberX(MemberX *c)
{ choco_awakeOnEnv_MemberX(c,1);
  } 


/* The c++ function for: awake(c:MemberX) [] */
void  choco_awake_MemberX_choco(MemberX *c)
{ choco_awakeOnEnv_MemberX(c,1);
  } 


/* The c++ function for: memberOf(sv:SetVar,v:IntVar) [] */
MemberX * choco_memberOf_SetVar2(SetVar *sv,IntVar *v)
{ GC_BIND;
  { MemberX *Result ;
    { MemberX * _CL_obj = ((MemberX *) GC_OBJECT(MemberX,new_object_class(choco._MemberX)));
      (_CL_obj->sv1 = sv);
      (_CL_obj->v2 = v);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A constraint stating that a variable is not member of a set variable
// awake when the kernel of sv is modified:
//   all values from the kernel of sv (the set variable) must be removed 
//   from the domain of iv (the integer variable)
//   if only one value out of the kernel, then this value can be removed from the enveloppe
/* The c++ function for: awakeOnKer(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnKer_NotMemberX(NotMemberX *c,int idx)
{ ;choco_propagate_NotMemberX(c);
  } 


// awake when the enveloppe of sv is modified:
//   nothing to do
/* The c++ function for: awakeOnEnv(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnEnv_NotMemberX(NotMemberX *c,int idx)
{ ;} 


// awake when the set sv is fixed:
//   all values from the kernel of sv (the set variable) must be removed 
//   from the domain of iv (the integer variable)
/* The c++ function for: awakeOnInstSet(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnInstSet_NotMemberX(NotMemberX *c,int idx)
{ GC_BIND;
  ;{ IntVar * iv = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    ClaireBoolean * someValOutOfEnv = CFALSE;
    { IntVar * g1130 = iv;
      AbstractIntDomain * g1131 = GC_OBJECT(AbstractIntDomain,g1130->bucket);
      if (g1131 == (NULL))
       { int  val = g1130->inf->latestValue;
        { OID gc_local;
          while ((val <= g1130->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else someValOutOfEnv= CTRUE;
              val= ((g1130->inf->latestValue <= (val+1)) ?
              (val+1) :
              g1130->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g1131->isa,choco._LinkedListIntDomain))
       { int  val = g1130->inf->latestValue;
        { OID gc_local;
          while ((val <= g1130->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else someValOutOfEnv= CTRUE;
              val= choco.getNextValue->fcall(((int) g1131),((int) val));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1131)))));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else someValOutOfEnv= CTRUE;
              GC_UNLOOP;} 
          } 
        } 
    if (someValOutOfEnv != CTRUE)
     choco_raiseContradiction_AbstractConstraint(c);
    } 
  GC_UNBIND;} 


// awake when iv is modified but not instantiated
//   
/* The c++ function for: awakeOnSup(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnSup_NotMemberX(NotMemberX *c,int idx)
{ choco_awakeOnVar_NotMemberX(c,idx);
  } 


/* The c++ function for: awakeOnInst(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnInst_NotMemberX(NotMemberX *c,int idx)
{ choco_awakeOnVar_NotMemberX(c,idx);
  } 


/* The c++ function for: awakeOnRem(c:NotMemberX,idx:integer,val:integer) [] */
void  choco_awakeOnRem_NotMemberX(NotMemberX *c,int idx,int val)
{ choco_awakeOnVar_NotMemberX(c,idx);
  } 


/* The c++ function for: awakeOnVar(c:NotMemberX,idx:integer) [] */
void  choco_awakeOnVar_NotMemberX(NotMemberX *c,int idx)
{ ;choco_propagate_NotMemberX(c);
  } 


/* The c++ function for: askIfEntailed(c:NotMemberX) [] */
OID  choco_askIfEntailed_NotMemberX(NotMemberX *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * iv = c->v2;
      SetVar * sv = c->sv1;
      AbstractSetDomain * svdom = sv->setBucket;
      int  nb = 0;
      int  theval = 0;
      ClaireBoolean * allValuesOutEnv = CTRUE;
      ClaireBoolean * allValuesInKer = CTRUE;
      { IntVar * g1132 = iv;
        AbstractIntDomain * g1133 = g1132->bucket;
        if (g1133 == (NULL))
         { int  val = g1132->inf->latestValue;
          { OID gc_local;
            while ((val <= g1132->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              val= ((g1132->inf->latestValue <= (val+1)) ?
                (val+1) :
                g1132->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g1133->isa,choco._LinkedListIntDomain))
         { int  val = g1132->inf->latestValue;
          { OID gc_local;
            while ((val <= g1132->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              val= choco.getNextValue->fcall(((int) g1133),((int) val));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1133)))));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svdom),((int) val))))) == CTRUE)
               { allValuesOutEnv= CFALSE;
                if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val)))) == CTRUE)
                 { allValuesInKer= CFALSE;
                  { ;break;} 
                  } 
                } 
              GC_UNLOOP;} 
            } 
          } 
      if (allValuesOutEnv == CTRUE)
       Result = Kernel.ctrue;
      else if (allValuesInKer == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:NotMemberX) [] */
ClaireBoolean * choco_testIfSatisfied_NotMemberX(NotMemberX *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->sv1->setBucket)))),((int) c->v2->value))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:NotMemberX) [] */
void  choco_propagate_NotMemberX(NotMemberX *c)
{ GC_BIND;
  { IntVar * iv = GC_OBJECT(IntVar,c->v2);
    SetVar * sv = GC_OBJECT(SetVar,c->sv1);
    AbstractSetDomain * svdom = GC_OBJECT(AbstractSetDomain,sv->setBucket);
    int  nb = 0;
    int  theval = 0;
    { IntVar * g1134 = iv;
      AbstractIntDomain * g1135 = GC_OBJECT(AbstractIntDomain,g1134->bucket);
      if (g1135 == (NULL))
       { int  val = g1134->inf->latestValue;
        { OID gc_local;
          while ((val <= g1134->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else { ++nb;
                theval= val;
                if (nb > 1)
                 { ;break;} 
                } 
              val= ((g1134->inf->latestValue <= (val+1)) ?
              (val+1) :
              g1134->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g1135->isa,choco._LinkedListIntDomain))
       { int  val = g1134->inf->latestValue;
        { OID gc_local;
          while ((val <= g1134->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else { ++nb;
                theval= val;
                if (nb > 1)
                 { ;break;} 
                } 
              val= choco.getNextValue->fcall(((int) g1135),((int) val));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g1135)))));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) svdom),((int) val))))) == CTRUE)
             (*choco.removeVal)(_oid_(iv),
              val,
              c->idx2);
            else { ++nb;
                theval= val;
                if (nb > 1)
                 { ;break;} 
                } 
              GC_UNLOOP;} 
          } 
        } 
    if (nb == 0)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (nb == 1)
     { choco_remFromEnveloppe_SetVar(sv,theval,c->idx1);
      choco_instantiate_IntVar2(iv,theval,c->idx2);
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:NotMemberX) [] */
void  choco_awake_NotMemberX_choco(NotMemberX *c)
{ choco_propagate_NotMemberX(c);
  } 


/* The c++ function for: opposite(c:MemberX) [] */
NotMemberX * choco_opposite_MemberX(MemberX *c)
{ GC_BIND;
  { NotMemberX *Result ;
    { NotMemberX * _CL_obj = ((NotMemberX *) GC_OBJECT(NotMemberX,new_object_class(choco._NotMemberX)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->v2 = c->v2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:NotMemberX) [] */
MemberX * choco_opposite_NotMemberX(NotMemberX *c)
{ GC_BIND;
  { MemberX *Result ;
    { MemberX * _CL_obj = ((MemberX *) GC_OBJECT(MemberX,new_object_class(choco._MemberX)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->v2 = c->v2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: notMemberOf(sv:SetVar,v:IntVar) [] */
NotMemberX * choco_notMemberOf_SetVar2(SetVar *sv,IntVar *v)
{ GC_BIND;
  { NotMemberX *Result ;
    { NotMemberX * _CL_obj = ((NotMemberX *) GC_OBJECT(NotMemberX,new_object_class(choco._NotMemberX)));
      (_CL_obj->sv1 = sv);
      (_CL_obj->v2 = v);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 5: union/intersection constraints                         *
// ********************************************************************
// A constraint stating that a set is the intersection of two other sets
// There are seven propagation rules for the constraint sv3 = intersection(sv1, sv2)
//    Ker(sv1) contains Ker(sv3)
//    Ker(sv2) contains Ker(sv3)
//    Ker(sv3) contains (Ker(sv1) inter Ker(sv2))
//    Env(v3)  disjoint Complement(Env(v1)) 
//    Env(v3)  disjoint Complement(Env(v2)) 
//    Env(v2)  disjoint Ker(v1) inter Complement(Env(v3))
//    Env(v1)  disjoint Ker(v2) inter Complement(Env(v3))
/* The c++ function for: awakeOnKer(c:SetIntersection,idx:integer) [] */
void  choco_awakeOnKer_SetIntersection(SetIntersection *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    if (idx == 1)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           choco_addToKernel_SetVar(s3,val,c->idx3);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    else if (idx == 2)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE)
           choco_addToKernel_SetVar(s3,val,c->idx3);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s1,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          { if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
             choco_addToKernel_SetVar(s1,val,c->idx1);
            if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
             choco_addToKernel_SetVar(s2,val,c->idx2);
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:SetIntersection,idx:integer) [] */
void  choco_awakeOnEnv_SetIntersection(SetIntersection *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    if (idx == 1)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    else if (idx == 2)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    else { { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
             choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
            GC_UNLOOP;} 
          } 
        { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
             choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
            GC_UNLOOP;} 
          } 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:SetIntersection,idx:integer) [] */
void  choco_awakeOnInstSet_SetIntersection(SetIntersection *c,int idx)
{ choco_awakeOnKer_SetIntersection(c,idx);
  choco_awakeOnEnv_SetIntersection(c,idx);
  } 


// TODO
/* The c++ function for: testIfSatisfied(c:SetIntersection) [] */
ClaireBoolean * choco_testIfSatisfied_SetIntersection(SetIntersection *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
      ClaireBoolean * allIn = CTRUE;
      ClaireBoolean * noneOut = CTRUE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE) || 
              (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE))
           { allIn= CFALSE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      if (allIn != CTRUE)
       Result = CFALSE;
      else { { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              if ((not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE) && 
                  ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE))
               { noneOut= CFALSE;
                { ;break;} 
                } 
              GC_UNLOOP;} 
            } 
          Result = noneOut;
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:SetIntersection) [] */
void  choco_propagate_SetIntersection(SetIntersection *c)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           choco_addToKernel_SetVar(s3,val,c->idx3);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE)
           choco_addToKernel_SetVar(s3,val,c->idx3);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s1,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s1,val,c->idx1);
          if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s2,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
          } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:SetIntersection) [] */
void  choco_awake_SetIntersection_choco(SetIntersection *c)
{ choco_propagate_SetIntersection(c);
  } 


/* The c++ function for: setintersection(s1:SetVar,s2:SetVar,s3:SetVar) [] */
SetIntersection * choco_setintersection_SetVar(SetVar *s1,SetVar *s2,SetVar *s3)
{ GC_BIND;
  { SetIntersection *Result ;
    { SetIntersection * _CL_obj = ((SetIntersection *) GC_OBJECT(SetIntersection,new_object_class(choco._SetIntersection)));
      (_CL_obj->sv1 = s1);
      (_CL_obj->sv2 = s2);
      (_CL_obj->sv3 = s3);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A constraint stating that a set is the intersection of two other sets
// There are seven propagation rules for the constraint sv3 = setunion(sv1, sv2)
//    Ker(v3) contains Ker(v1)
//    Ker(v3) contains Ker(v2)
//    Ker(v1) contains Ker(v3) inter Complement(Env(v2))
//    Ker(v2) contains Ker(v3) inter Complement(Env(v1))
//    Env(v1) disjoint Complement(Env(v3))
//    Env(v2) disjoint Complement(Env(v3))
//    Env(v3) disjoint Complement(Env(v1)) inter Complement(Env(v2))
/* The c++ function for: awakeOnKer(c:SetUnion,idx:integer) [] */
void  choco_awakeOnKer_SetUnion(SetUnion *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    if (idx == 1)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    else if (idx == 2)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          { if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
             choco_addToKernel_SetVar(s1,val,c->idx1);
            if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
             choco_addToKernel_SetVar(s2,val,c->idx2);
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:SetUnion,idx:integer) [] */
void  choco_awakeOnEnv_SetUnion(SetUnion *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    if (idx == 1)
     { { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s2,val,c->idx2);
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE) && 
              (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE))
           choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
          GC_UNLOOP;} 
        } 
      } 
    else if (idx == 2)
     { { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s1,val,c->idx1);
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE) && 
              (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE))
           choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
          GC_UNLOOP;} 
        } 
      } 
    else { { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
             choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
            GC_UNLOOP;} 
          } 
        { OID gc_local;
          ITERATE(val);
          bag *val_support;
          val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s2));
          for (START(val_support); NEXT(val);)
          { GC_LOOP;
            if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
             choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
            GC_UNLOOP;} 
          } 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:SetUnion,idx:integer) [] */
void  choco_awakeOnInstSet_SetUnion(SetUnion *c,int idx)
{ choco_awakeOnKer_SetUnion(c,idx);
  choco_awakeOnEnv_SetUnion(c,idx);
  } 


// TODO
/* The c++ function for: testIfSatisfied(c:SetUnion) [] */
ClaireBoolean * choco_testIfSatisfied_SetUnion(SetUnion *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
      ClaireBoolean * allIn = CTRUE;
      ClaireBoolean * noneOut = CTRUE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE) && 
              (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE))
           { allIn= CFALSE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      if (allIn != CTRUE)
       Result = CFALSE;
      else { { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
               { noneOut= CFALSE;
                { ;break;} 
                } 
              GC_UNLOOP;} 
            } 
          if (noneOut == CTRUE)
           { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
               { noneOut= CFALSE;
                { ;break;} 
                } 
              GC_UNLOOP;} 
            } 
          Result = noneOut;
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:SetUnion) [] */
void  choco_propagate_SetUnion(SetUnion *c)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    SetVar * s3 = GC_OBJECT(SetVar,c->sv3);
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        { if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s1,val,c->idx1);
          if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE)
           choco_addToKernel_SetVar(s2,val,c->idx2);
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s3));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if ((not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val)))) == CTRUE) && 
            (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE))
         choco_remFromEnveloppe_SetVar(s3,val,c->idx3);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s3->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:SetUnion) [] */
void  choco_awake_SetUnion_choco(SetUnion *c)
{ choco_propagate_SetUnion(c);
  } 


/* The c++ function for: setunion(s1:SetVar,s2:SetVar,s3:SetVar) [] */
SetUnion * choco_setunion_SetVar(SetVar *s1,SetVar *s2,SetVar *s3)
{ GC_BIND;
  { SetUnion *Result ;
    { SetUnion * _CL_obj = ((SetUnion *) GC_OBJECT(SetUnion,new_object_class(choco._SetUnion)));
      (_CL_obj->sv1 = s1);
      (_CL_obj->sv2 = s2);
      (_CL_obj->sv3 = s3);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 6: bin comparisons (subset/disjoint/overlap)              *
// ********************************************************************
// A constraint stating that a set is included (may be equal) into anotherone
// There are seven propagation rules for the constraint subset(sv1, sv2)
//    Ker(v2) contains Ker(v1)
//    Env(v1) disjoint Complement(Env(v2))
/* The c++ function for: awakeOnKer(c:SubSet,idx:integer) [] */
void  choco_awakeOnKer_SubSet(SubSet *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    if (idx == 1)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s2,val,c->idx2);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:SubSet,idx:integer) [] */
void  choco_awakeOnEnv_SubSet(SubSet *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    if (idx == 2)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:SubSet,idx:integer) [] */
void  choco_awakeOnInstSet_SubSet(SubSet *c,int idx)
{ choco_awakeOnKer_SubSet(c,idx);
  choco_awakeOnEnv_SubSet(c,idx);
  } 


/* The c++ function for: askIfEntailed(c:SubSet) [] */
OID  choco_askIfEntailed_SubSet(SubSet *c)
{ GC_BIND;
  { OID Result = 0;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * allPossibleIn = CTRUE;
      ClaireBoolean * someSureOut = CFALSE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           { if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE) && 
                (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE))
             someSureOut= CTRUE;
            allPossibleIn= CFALSE;
            } 
          GC_UNLOOP;} 
        } 
      if (allPossibleIn == CTRUE)
       Result = Kernel.ctrue;
      else if (someSureOut == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:SubSet) [] */
ClaireBoolean * choco_testIfSatisfied_SubSet(SubSet *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * allIn = CTRUE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
           { allIn= CFALSE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      Result = allIn;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:SubSet) [] */
void  choco_propagate_SubSet(SubSet *c)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_addToKernel_SetVar(s2,val,c->idx2);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val)))) == CTRUE)
         choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:SubSet) [] */
void  choco_awake_SubSet_choco(SubSet *c)
{ choco_propagate_SubSet(c);
  } 


/* The c++ function for: subset(s1:SetVar,s2:SetVar) [] */
SubSet * choco_subset_SetVar(SetVar *s1,SetVar *s2)
{ GC_BIND;
  { SubSet *Result ;
    { SubSet * _CL_obj = ((SubSet *) GC_OBJECT(SubSet,new_object_class(choco._SubSet)));
      (_CL_obj->sv1 = s1);
      (_CL_obj->sv2 = s2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A constraint stating that two sets have no value in common
// There are seven propagation rules for the constraint subset(sv1, sv2)
//    Env(v1) disjoint Complement(Ker(v2))
//    Env(v2) disjoint Complement(Ker(v1))
/* The c++ function for: awakeOnKer(c:DisjointSets,idx:integer) [] */
void  choco_awakeOnKer_DisjointSets(DisjointSets *c,int idx)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    if (idx == 1)
     { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:DisjointSets,idx:integer) [] */
void  choco_awakeOnEnv_DisjointSets(DisjointSets *c,int idx)
{ ;} 


/* The c++ function for: awakeOnInstSet(c:DisjointSets,idx:integer) [] */
void  choco_awakeOnInstSet_DisjointSets(DisjointSets *c,int idx)
{ choco_awakeOnKer_DisjointSets(c,idx);
  } 


/* The c++ function for: askIfEntailed(c:DisjointSets) [] */
OID  choco_askIfEntailed_DisjointSets(DisjointSets *c)
{ GC_BIND;
  { OID Result = 0;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * somePossibleIn = CFALSE;
      ClaireBoolean * someSureIn = CFALSE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE) && 
                ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE))
             { someSureIn= CTRUE;
              { ;break;} 
              } 
            somePossibleIn= CTRUE;
            } 
          GC_UNLOOP;} 
        } 
      if (someSureIn == CTRUE)
       Result = Kernel.cfalse;
      else if (somePossibleIn != CTRUE)
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:DisjointSets) [] */
ClaireBoolean * choco_testIfSatisfied_DisjointSets(DisjointSets *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * someIn = CFALSE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { someIn= CTRUE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      Result = not_any(_oid_(someIn));
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:DisjointSets) [] */
void  choco_propagate_DisjointSets(DisjointSets *c)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_remFromEnveloppe_SetVar(s2,val,c->idx2);
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s2));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        choco_remFromEnveloppe_SetVar(s1,val,c->idx1);
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:DisjointSets) [] */
void  choco_awake_DisjointSets_choco(DisjointSets *c)
{ choco_propagate_DisjointSets(c);
  } 


/* The c++ function for: disjoint(s1:SetVar,s2:SetVar) [] */
DisjointSets * choco_disjoint_SetVar(SetVar *s1,SetVar *s2)
{ GC_BIND;
  { DisjointSets *Result ;
    { DisjointSets * _CL_obj = ((DisjointSets *) GC_OBJECT(DisjointSets,new_object_class(choco._DisjointSets)));
      (_CL_obj->sv1 = s1);
      (_CL_obj->sv2 = s2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// A constraint stating that two sets have some values in common
// There are seven propagation rules for the constraint overlap(sv1, sv2)
//    kernelIntersectionSize = empty(Ker(v1) inter Ker(v2))
//    kernelIntersectionSize > 0 => satisfied
//    Enveloppe(v1) inter Enveloppe(v2) = {a} => a in Ker(v1) inter Ker(v2)
/* The c++ function for: awakeOnKer(c:OverlappingSets,idx:integer) [] */
void  choco_awakeOnKer_OverlappingSets(OverlappingSets *c,int idx)
{ if (c->emptyKernelIntersection == CTRUE) 
  { { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      int  nb = 0;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { nb= 1;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      if (nb > 0)
       { STOREO(c->emptyKernelIntersection,CFALSE);
        choco_setPassive_AbstractConstraint(c);
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnEnv(c:OverlappingSets,idx:integer) [] */
void  choco_awakeOnEnv_OverlappingSets(OverlappingSets *c,int idx)
{ if (c->emptyKernelIntersection == CTRUE) 
  { { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      int  theval = -100000000;
      int  nbCandidates = 0;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { ++nbCandidates;
            theval= val;
            if (nbCandidates > 1)
             { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      if (nbCandidates == 1)
       { STOREO(c->emptyKernelIntersection,CFALSE);
        choco_addToKernel_SetVar(s1,theval,c->idx1);
        choco_addToKernel_SetVar(s2,theval,c->idx2);
        choco_setPassive_AbstractConstraint(c);
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInstSet(c:OverlappingSets,idx:integer) [] */
void  choco_awakeOnInstSet_OverlappingSets(OverlappingSets *c,int idx)
{ choco_awakeOnEnv_OverlappingSets(c,idx);
  } 


/* The c++ function for: askIfEntailed(c:OverlappingSets) [] */
OID  choco_askIfEntailed_OverlappingSets(OverlappingSets *c)
{ GC_BIND;
  { OID Result = 0;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * somePossibleIn = CFALSE;
      ClaireBoolean * someSureIn = CFALSE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainEnveloppe_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s1->setBucket)))),((int) val))))) == CTRUE) && 
                ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE))
             { someSureIn= CTRUE;
              { ;break;} 
              } 
            somePossibleIn= CTRUE;
            } 
          GC_UNLOOP;} 
        } 
      if (someSureIn == CTRUE)
       Result = Kernel.ctrue;
      else if (somePossibleIn != CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:OverlappingSets) [] */
ClaireBoolean * choco_testIfSatisfied_OverlappingSets(OverlappingSets *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
      SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
      ClaireBoolean * someIn = CFALSE;
      { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
           { someIn= CTRUE;
            { ;break;} 
            } 
          GC_UNLOOP;} 
        } 
      Result = someIn;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(c:OverlappingSets) [] */
void  choco_propagate_OverlappingSets(OverlappingSets *c)
{ choco_awakeOnEnv_OverlappingSets(c,1);
  } 


/* The c++ function for: awake(c:OverlappingSets) [] */
void  choco_awake_OverlappingSets_choco(OverlappingSets *c)
{ GC_BIND;
  { SetVar * s1 = GC_OBJECT(SetVar,c->sv1);
    SetVar * s2 = GC_OBJECT(SetVar,c->sv2);
    int  nb = 0;
    { OID gc_local;
      ITERATE(val);
      bag *val_support;
      val_support = GC_OBJECT(list,choco_getDomainKernel_SetVar(s1));
      for (START(val_support); NEXT(val);)
      { GC_LOOP;
        if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(s2->setBucket)))),((int) val))))) == CTRUE)
         { nb= 1;
          { ;break;} 
          } 
        GC_UNLOOP;} 
      } 
    if (nb > 0)
     { STOREO(c->emptyKernelIntersection,CFALSE);
      choco_setPassive_AbstractConstraint(c);
      } 
    else choco_awakeOnEnv_OverlappingSets(c,1);
      } 
  GC_UNBIND;} 


/* The c++ function for: overlap(s1:SetVar,s2:SetVar) [] */
OverlappingSets * choco_overlap_SetVar(SetVar *s1,SetVar *s2)
{ GC_BIND;
  { OverlappingSets *Result ;
    { OverlappingSets * _CL_obj = ((OverlappingSets *) GC_OBJECT(OverlappingSets,new_object_class(choco._OverlappingSets)));
      (_CL_obj->sv1 = s1);
      (_CL_obj->sv2 = s2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:DisjointSets) [] */
OverlappingSets * choco_opposite_DisjointSets(DisjointSets *c)
{ GC_BIND;
  { OverlappingSets *Result ;
    { OverlappingSets * _CL_obj = ((OverlappingSets *) GC_OBJECT(OverlappingSets,new_object_class(choco._OverlappingSets)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->sv2 = c->sv2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:OverlappingSets) [] */
DisjointSets * choco_opposite_OverlappingSets(OverlappingSets *c)
{ GC_BIND;
  { DisjointSets *Result ;
    { DisjointSets * _CL_obj = ((DisjointSets *) GC_OBJECT(DisjointSets,new_object_class(choco._DisjointSets)));
      (_CL_obj->sv1 = c->sv1);
      (_CL_obj->sv2 = c->sv2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// TODO: NotSubset, StrictSubset, NotStrictSubset, EqualSets, DifferentSets
// ********************************************************************
// *   Part 7: larger operators                                       *
// ********************************************************************
// TODO: partition, covering (largeunion), largeintersection ???
//       scalar (constant weights) & scalar (variable weights)