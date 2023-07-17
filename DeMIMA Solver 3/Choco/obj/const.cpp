/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\const.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:26 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: const.cl                                                   *
// *    modelling constraints                                         *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: AbstractConstraint                                     *
// ********************************************************************
// Abstract class for constraints, no interface methods
// Such a constraint may have IntVar as well as other types of variables
// abstract classes for small constraints on integer valued variables (IntVar)
//
// constraint c is the (c.idx1)th constraint of its variable c.v1
// i.e.    c.v1.constraints[c.idx1] = c
// c.v2.constraints[c.idx2] = c
// c.v3.constraints[c.idx3] = c
/* The c++ function for: closeLargeIntConstraint(c:LargeIntConstraint) [] */
void  choco_closeLargeIntConstraint_LargeIntConstraint(LargeIntConstraint *c)
{ (c->nbVars = c->vars->length);
  (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
  } 


// This class is an empty shell supporting the delayed propagation of an IntConstraint
// v0.30: this is an undocumented API function (not to be used until debugged...)
/* The c++ function for: delay(c:IntConstraint,p:integer) [] */
Delayer * choco_delay_IntConstraint(IntConstraint *c,int p)
{ GC_BIND;
  { Delayer *Result ;
    { Delayer * _CL_obj = ((Delayer *) GC_OBJECT(Delayer,new_object_class(choco._Delayer)));
      (_CL_obj->target = c);
      (_CL_obj->priority = p);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907
/* The c++ function for: self_print(d:Delayer) [] */
void  claire_self_print_Delayer_choco(Delayer *d)
{ GC_BIND;
  princ_string("delay(");
  print_any(GC_OID(_oid_(d->target)));
  princ_string(",");
  print_any(d->priority);
  princ_string(")");
  GC_UNBIND;} 


// abstract classes for compoosite constraints (constraints made out of
// several (simpler) constraints).
// we provide with a generic mechanism for assigning a unique index to each
// variable in the composite constraint.
// Abstract class for constraint made of several sub-constraints, no interface
// note (0.18): CompositeConstraint could feature a field "indices"
// claire3 port: change slots to strongly typed lists
// v1.02: corresponding constraint indices
// v0.37 public for extensions to CHOCO
/* The c++ function for: closeCompositeConstraint(c:BinCompositeConstraint) [] */
void  choco_closeCompositeConstraint_BinCompositeConstraint(BinCompositeConstraint *c)
{ GC_BIND;
  (c->offset = choco.getNbVars->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
  GC_UNBIND;} 


// v1.02 fill the nbConst slot + take the additionalVars slot into account
// claire3 port: strongly typed lists
/* The c++ function for: closeCompositeConstraint(c:LargeCompositeConstraint) [] */
void  choco_closeCompositeConstraint_LargeCompositeConstraint(LargeCompositeConstraint *c)
{ GC_BIND;
  (c->nbConst = c->lconst->length);
  { int  nbvars = 0;
    list * l = GC_OBJECT(list,make_list_integer2(c->nbConst,Kernel._integer,0));
    { int  i = 1;
      int  g0152 = c->nbConst;
      { OID gc_local;
        while ((i <= g0152))
        { // HOHO, GC_LOOP not needed !
          nbvars= (nbvars+(choco.getNbVars->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i])))));
          ((*(l))[i]=nbvars);
          ++i;
          } 
        } 
      } 
    (c->loffset = l);
    (c->additionalIndices = make_list_integer2(c->additionalVars->length,Kernel._integer,0));
    ;} 
  GC_UNBIND;} 


// v1.02: new access functions: retrieving the number of variables coming from subconstraints
/* The c++ function for: getNbVarsFromSubConst(c:LargeCompositeConstraint) [] */
int  choco_getNbVarsFromSubConst_LargeCompositeConstraint(LargeCompositeConstraint *c)
{ return (last_list(c->loffset));} 


// Methods for connecting constraints
// <fl> 0.36: open-coded method for compatibility with other libraries (eg igloo)
// private lower-level connection methods
// connecting a constraint to one of its variables, activate connections for bound events, or do both
// a constraint is active if it is connected to the network and if it does propagate
// v1.0 for the same guy
/* The c++ function for: connectHook(x:any,c:AbstractConstraint) [] */
OID  choco_connectHook_any_choco(OID x,AbstractConstraint *c)
{ return (Core.nil->value);} 


/* The c++ function for: reconnectHook(x:any,c:AbstractConstraint) [] */
OID  choco_reconnectHook_any_choco(OID x,AbstractConstraint *c)
{ return (Core.nil->value);} 


/* The c++ function for: disconnectHook(x:any,c:AbstractConstraint) [] */
OID  choco_disconnectHook_any_choco(OID x,AbstractConstraint *c)
{ return (Core.nil->value);} 


// v1.03 needs to be abstract (redefined for "global" constraint inheriting from IntConstraint)
// Kind of prefix numbering of all variables in the subconstraints of a composite constraint
//
// In a complex root composite constraint (a syntactic tree where the nodes
// are CompositeConstraint and the leaves are simple AbstractConstraint
// such as UnIntConstraint/BinIntConstraint)
// The assignIndices function associates to each variable involved in a leaf constraint
// a unique index.
// Each internal node of the tree contains an "offset" field which contains the
// largest index among all variables in the left subtree.
//
// v0.30: the second argument of assignIndices can now either be a CompositeConstraint or a Delayer
// v1.02 raise an error + return an integer (for type inference)
/* The c++ function for: assignIndices(c1:AbstractConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *root,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the root definition of assignIndices should not be called (~S,~S,~S)"),
    _oid_(list::alloc(3,_oid_(c1),
      _oid_(root),
      i)))));
  return (1);} 


/* The c++ function for: assignIndices(c1:UnIntConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_UnIntConstraint(UnIntConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v1),j);
      choco_setConstraintIndex_UnIntConstraint(c1,1,c1->v1->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: assignIndices(c1:BinIntConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_BinIntConstraint(BinIntConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v1),j);
      choco_setConstraintIndex_BinIntConstraint(c1,1,c1->v1->constraints->length);
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v2),j);
      choco_setConstraintIndex_BinIntConstraint(c1,2,c1->v2->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.34
/* The c++ function for: assignIndices(c1:TernIntConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_TernIntConstraint(TernIntConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v1),j);
      choco_setConstraintIndex_TernIntConstraint(c1,1,c1->v1->constraints->length);
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v2),j);
      choco_setConstraintIndex_TernIntConstraint(c1,2,c1->v2->constraints->length);
      ++j;
      choco_connectIntVar_AbstractConstraint1(root,GC_OBJECT(IntVar,c1->v3),j);
      choco_setConstraintIndex_TernIntConstraint(c1,3,c1->v3->constraints->length);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.15
/* The c++ function for: assignIndices(c1:LargeIntConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_LargeIntConstraint(LargeIntConstraint *c1,AbstractConstraint *root,int i)
{ { int Result = 0;
    { int  j = i;
      { int  k = 1;
        int  g0153 = c1->nbVars;
        { OID gc_local;
          while ((k <= g0153))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint1(root,OBJECT(IntVar,(*(c1->vars))[k]),j);
            choco_setConstraintIndex_LargeIntConstraint(c1,k,OBJECT(AbstractVar,(*(c1->vars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      Result = j;
      } 
    return (Result);} 
  } 


/* The c++ function for: assignIndices(c1:BinCompositeConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_BinCompositeConstraint(BinCompositeConstraint *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      j= (*choco.assignIndices)(GC_OID(_oid_(c1->const1)),
        _oid_(root),
        j);
      j= (*choco.assignIndices)(GC_OID(_oid_(c1->const2)),
        _oid_(root),
        j);
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: assignIndices(c1:LargeCompositeConstraint,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_LargeCompositeConstraint(LargeCompositeConstraint *c1,AbstractConstraint *root,int i)
{ { int Result = 0;
    { int  j = i;
      { int  constIdx = 1;
        int  g0154 = c1->nbConst;
        { OID gc_local;
          while ((constIdx <= g0154))
          { // HOHO, GC_LOOP not needed !
            { AbstractConstraint * subc = OBJECT(AbstractConstraint,(*(c1->lconst))[constIdx]);
              j= (*choco.assignIndices)(_oid_(subc),
                _oid_(root),
                j);
              ;} 
            ++constIdx;
            } 
          } 
        } 
      { int  k = 1;
        int  g0155 = c1->additionalVars->length;
        { OID gc_local;
          while ((k <= g0155))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint1(root,OBJECT(IntVar,(*(c1->additionalVars))[k]),j);
            ((*(c1->additionalIndices))[k]=OBJECT(AbstractVar,(*(c1->additionalVars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      Result = j;
      } 
    return (Result);} 
  } 


// v0.32 this new function is required since the introduction of indexInOpposite.
// v1.02 raise an error
/* The c++ function for: setConstraintIndex(c:AbstractConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_AbstractConstraint(AbstractConstraint *c,int i,int val)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the root definition of setConstraintIndex (~S,~S,~S) should not be called"),
    _oid_(list::alloc(3,_oid_(c),
      i,
      val)))));
  } 


// let v be the i-th var of c, records that c is the n-th constraint involving v
/* The c++ function for: setConstraintIndex(c:UnIntConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_UnIntConstraint(UnIntConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: setConstraintIndex(c:BinIntConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_BinIntConstraint(BinIntConstraint *c,int i,int val)
{ if (i == 1)
   (c->idx1 = val);
  else if (i == 2)
   (c->idx2 = val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: setConstraintIndex(c:TernIntConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_TernIntConstraint(TernIntConstraint *c,int i,int val)
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


/* The c++ function for: setConstraintIndex(c:LargeIntConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_LargeIntConstraint(LargeIntConstraint *c,int i,int val)
{ if (i <= c->indices->length)
   ((*(c->indices))[i]=val);
  else close_exception(((general_error *) (*Core._general_error)(_string_("impossible to copy index ~S on ~S as ~S"),
      _oid_(list::alloc(3,i,
        _oid_(c),
        val)))));
    } 


/* The c++ function for: setConstraintIndex(c:BinCompositeConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_BinCompositeConstraint(BinCompositeConstraint *c,int i,int val)
{ if (i <= c->offset) 
  { (*choco.setConstraintIndex)(GC_OID(_oid_(c->const1)),
      i,
      val);
     } 
  else{ GC_BIND;
    (*choco.setConstraintIndex)(GC_OID(_oid_(c->const2)),
      (i-c->offset),
      val);
    GC_UNBIND;} 
  } 


// v1.02 raise an error when the index is too large
/* The c++ function for: setConstraintIndex(c:LargeCompositeConstraint,i:integer,val:integer) [] */
void  choco_setConstraintIndex_LargeCompositeConstraint(LargeCompositeConstraint *c,int i,int val)
{ GC_BIND;
  { OID  constIdxtest;
    { { OID  i2_some = CNULL;
        { int  i2 = 1;
          int  g0156 = c->nbConst;
          { OID gc_local;
            while ((i2 <= g0156))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[i2])
               { i2_some= i2;
                break;} 
              ++i2;
              } 
            } 
          } 
        constIdxtest = i2_some;
        } 
      GC_OID(constIdxtest);} 
    if (constIdxtest != CNULL)
     { int  constIdx = constIdxtest;
      int  realIdx = ((constIdx == 1) ?
        i :
        (i-((*(c->loffset))[(constIdx-1)])) );
      (*choco.setConstraintIndex)((*(c->lconst))[constIdx],
        realIdx,
        val);
      } 
    else { int  realIdx = (i-(last_list(c->loffset)));
        if (realIdx <= c->additionalVars->length)
         ((*(c->additionalIndices))[realIdx]=val);
        else close_exception(((general_error *) (*Core._general_error)(_string_("index ~S above largest var-index in constraint ~S"),
            _oid_(list::alloc(2,i,_oid_(c))))));
          } 
      } 
  GC_UNBIND;} 


/* The c++ function for: setConstraintIndex(c:Delayer,i:integer,val:integer) [] */
void  choco_setConstraintIndex_Delayer(Delayer *c,int i,int val)
{ GC_BIND;
  (*choco.setConstraintIndex)(GC_OID(_oid_(c->target)),
    i,
    val);
  GC_UNBIND;} 


// v0.32 among all constraints linked to the idx-th variable of c,
// find the index of constraint c
// v1.02 raises an error + return an int (for type inference)
/* The c++ function for: getConstraintIdx(c:AbstractConstraint,idx:integer) [] */
int  choco_getConstraintIdx_AbstractConstraint(AbstractConstraint *c,int idx)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the root definition of getConstraintIdx (~S,~S) should not be called"),
    _oid_(list::alloc(2,_oid_(c),idx)))));
  return (0);} 


/* The c++ function for: getConstraintIdx(c:UnIntConstraint,idx:integer) [] */
int  choco_getConstraintIdx_UnIntConstraint(UnIntConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
          _oid_(list::alloc(2,idx,_oid_(c))))));
        Result = 0;
        } 
      return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:BinIntConstraint,idx:integer) [] */
int  choco_getConstraintIdx_BinIntConstraint(BinIntConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else if (idx == 2)
     Result = c->idx2;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
          _oid_(list::alloc(2,idx,_oid_(c))))));
        Result = 0;
        } 
      return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:TernIntConstraint,idx:integer) [] */
int  choco_getConstraintIdx_TernIntConstraint(TernIntConstraint *c,int idx)
{ { int Result = 0;
    if (idx == 1)
     Result = c->idx1;
    else if (idx == 2)
     Result = c->idx2;
    else if (idx == 3)
     Result = c->idx3;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
          _oid_(list::alloc(2,idx,_oid_(c))))));
        Result = 0;
        } 
      return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:LargeIntConstraint,idx:integer) [] */
int  choco_getConstraintIdx_LargeIntConstraint(LargeIntConstraint *c,int idx)
{ { int Result = 0;
    if (idx <= c->indices->length)
     Result = (*(c->indices))[idx];
    else { close_exception(((general_error *) (*Core._general_error)(_string_("impossible to get ~S-th index of ~S"),
          _oid_(list::alloc(2,idx,_oid_(c))))));
        Result = 0;
        } 
      return (Result);} 
  } 


/* The c++ function for: getConstraintIdx(c:BinCompositeConstraint,idx:integer) [] */
int  choco_getConstraintIdx_BinCompositeConstraint(BinCompositeConstraint *c,int idx)
{ if (idx <= c->offset) 
  { { int Result = 0;
      Result = choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) idx));
      return (Result);} 
     } 
  else{ GC_BIND;
    { int Result = 0;
      Result = choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (idx-c->offset)));
      GC_UNBIND; return (Result);} 
    } 
  } 


// v1.02 raise an error when the index is too large
/* The c++ function for: getConstraintIdx(c:LargeCompositeConstraint,idx:integer) [] */
int  choco_getConstraintIdx_LargeCompositeConstraint(LargeCompositeConstraint *c,int idx)
{ GC_BIND;
  { int Result = 0;
    { OID  constIdxtest;
      { { OID  i_some = CNULL;
          { int  i = 1;
            int  g0157 = c->nbConst;
            { OID gc_local;
              while ((i <= g0157))
              { // HOHO, GC_LOOP not needed !
                if (idx <= (*(c->loffset))[i])
                 { i_some= i;
                  break;} 
                ++i;
                } 
              } 
            } 
          constIdxtest = i_some;
          } 
        GC_OID(constIdxtest);} 
      if (constIdxtest != CNULL)
       { int  constIdx = constIdxtest;
        int  realIdx = ((constIdx == 1) ?
          idx :
          (idx-((*(c->loffset))[(constIdx-1)])) );
        Result = choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[constIdx])),((int) realIdx));
        } 
      else { int  realIdx = (idx-(last_list(c->loffset)));
          if (realIdx <= c->additionalVars->length)
           Result = (*(c->additionalIndices))[realIdx];
          else { close_exception(((general_error *) (*Core._general_error)(_string_("index ~S above largest var-index in constraint ~S"),
                _oid_(list::alloc(2,idx,_oid_(c))))));
              Result = 0;
              } 
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 <naren>
/* The c++ function for: getConstraintIdx(c:Delayer,i:integer) [] */
int  choco_getConstraintIdx_Delayer(Delayer *c,int i)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target)))),((int) i));
    GC_UNBIND; return (Result);} 
  } 


// Constraint interface
// The public methods on the constraints are the following
// v1.02
// v1.04
// v1.04
// choco/doAwake(c:AbstractConstraint) : void
//    implements a fast call to awake(c)
// choco/doAwakeOnInf(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnInf(c,idx)
// choco/doAwakeOnSup(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnSup(c,idx)
// choco/doAwakeOnInst(c:AbstractConstraint, idx:integer) : void
//    implements a fast call to awakeOnInst(c,idx)
// choco/doAwakeOnRem(c:AbstractConstraint, idx:integer, x:integer) : void
//    implements a fast call to awakeOnRem(c,idx, x)
// choco/doAwakeOnVar(c:AbstractConstraint,idx) : void
//    implements a fast call to awakeOnVar(c,idx)
// choco/askIfTrue(c:AbstractConstraint) : (boolean U {unknown})
//    implements a fast call to askIfEntailed(c)
// choco/testIfTrue(c:AbstractConstraint) : boolean
//    implements a fast call to testIfSatisfied(c)
// choco/testIfInstantiated(c:AbstractConstraint) : boolean
//    implements a fast call to testIfCompletelyInstantiated(c)
// for each constraint the following method must be defined
// v0.9907: accessing the priority of a constraint
/* The c++ function for: getPriority(c:AbstractConstraint) [] */
int  choco_getPriority_AbstractConstraint_choco(AbstractConstraint *c)
{ return (1);} 


/* The c++ function for: getPriority(c:Delayer) [] */
int  choco_getPriority_Delayer_choco(Delayer *c)
{ return (c->priority);} 


// v1.04
/* The c++ function for: testIfCompletelyInstantiated(c:IntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_IntConstraint(IntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { int  n = choco.getNbVars->fcall(((int) c));
      { OID  g0159UU;
        { int  i = 1;
          int  g0158 = n;
          { OID gc_local;
            g0159UU= _oid_(CFALSE);
            while ((i <= g0158))
            { GC_LOOP;
              if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID((*choco.getVar)(_oid_(c),
                i))))))) == CTRUE)
               { g0159UU = Kernel.ctrue;
                break;} 
              ++i;
              GC_UNLOOP;} 
            } 
          } 
        Result = not_any(g0159UU);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:UnIntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_UnIntConstraint(UnIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:BinIntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_BinIntConstraint(BinIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:TernIntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_TernIntConstraint(TernIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v3)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:LargeIntConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_LargeIntConstraint(LargeIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0160UU;
      { OID gc_local;
        ITERATE(v);
        g0160UU= _oid_(CFALSE);
        for (START(c->vars); NEXT(v);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
           { g0160UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = not_any(g0160UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:BinCompositeConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_BinCompositeConstraint(BinCompositeConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:LargeCompositeConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_LargeCompositeConstraint(LargeCompositeConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean *v_and;
      { { OID  g0161UU;
          { OID gc_local;
            ITERATE(subc);
            g0161UU= _oid_(CFALSE);
            for (START(c->lconst); NEXT(subc);)
            { GC_LOOP;
              if (not_any(_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,subc))))) == CTRUE)
               { g0161UU = Kernel.ctrue;
                break;} 
              GC_UNLOOP;} 
            } 
          v_and = not_any(g0161UU);
          } 
        if (v_and == CFALSE) Result =CFALSE; 
        else { { OID  g0162UU;
            { OID gc_local;
              ITERATE(v);
              g0162UU= _oid_(CFALSE);
              for (START(c->additionalVars); NEXT(v);)
              { GC_LOOP;
                if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,v))))) == CTRUE)
                 { g0162UU = Kernel.ctrue;
                  break;} 
                GC_UNLOOP;} 
              } 
            v_and = not_any(g0162UU);
            } 
          if (v_and == CFALSE) Result =CFALSE; 
          else Result = CTRUE;} 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02
// v0.29: counting the number of variables involved in a constraint
/* The c++ function for: getNbVars(c:AbstractConstraint) [] */
int  choco_getNbVars_AbstractConstraint(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getNbVars has not yet been defined on ~S, it should be !"),
    _oid_(list::alloc(1,_oid_(c))))));
  return (0);} 


/* The c++ function for: getNbVars(c:UnIntConstraint) [] */
int  choco_getNbVars_UnIntConstraint(UnIntConstraint *c)
{ return (1);} 


/* The c++ function for: getNbVars(c:BinIntConstraint) [] */
int  choco_getNbVars_BinIntConstraint(BinIntConstraint *c)
{ return (2);} 


/* The c++ function for: getNbVars(c:TernIntConstraint) [] */
int  choco_getNbVars_TernIntConstraint(TernIntConstraint *c)
{ return (3);} 


/* The c++ function for: getNbVars(c:LargeIntConstraint) [] */
int  choco_getNbVars_LargeIntConstraint(LargeIntConstraint *c)
{ ;return (c->nbVars);} 


/* The c++ function for: getNbVars(c:LargeCompositeConstraint) [] */
int  choco_getNbVars_LargeCompositeConstraint(LargeCompositeConstraint *c)
{ { int Result = 0;
    if (c->nbConst == 0)
     Result = 0;
    else { Result = (((*(c->loffset))[c->nbConst])+c->additionalVars->length);
        } 
      return (Result);} 
  } 


// v1.02
/* The c++ function for: getNbVars(c:BinCompositeConstraint) [] */
int  choco_getNbVars_BinCompositeConstraint(BinCompositeConstraint *c)
{ GC_BIND;
  ;{ int Result = 0;
    Result = (c->offset+(choco.getNbVars->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))));
    GC_UNBIND; return (Result);} 
  } 


// accessing the ith variable of a constraint
/* The c++ function for: getVar(c:AbstractConstraint,i:integer) [] */
AbstractVar * choco_getVar_AbstractConstraint_choco(AbstractConstraint *c,int i)
{ GC_BIND;
  close_exception(((general_error *) (*Core._general_error)(_string_("getVar has not yet been defined on ~S, it should be !"),
    _oid_(list::alloc(1,_oid_(c))))));
  { AbstractVar *Result ;
    { IntVar * _CL_obj = ((IntVar *) GC_OBJECT(IntVar,new_object_class(choco._IntVar)));
      (_CL_obj->name = string_v(CLREAD(slot,_at_property1(Kernel.name,choco._IntVar),DEFAULT)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getVar(c:UnIntConstraint,i:integer) [] */
AbstractVar * choco_getVar_UnIntConstraint_choco(UnIntConstraint *c,int i)
{ if (i == 1)
   ;else close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
      _oid_(list::alloc(2,i,_oid_(c))))));
    return (c->v1);} 


/* The c++ function for: getVar(c:BinIntConstraint,i:integer) [] */
AbstractVar * choco_getVar_BinIntConstraint_choco(BinIntConstraint *c,int i)
{ { AbstractVar *Result ;
    if (i == 1)
     Result = c->v1;
    else if (i == 2)
     Result = c->v2;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = c->v1;
        } 
      return (Result);} 
  } 


/* The c++ function for: getVar(c:TernIntConstraint,i:integer) [] */
AbstractVar * choco_getVar_TernIntConstraint_choco(TernIntConstraint *c,int i)
{ { AbstractVar *Result ;
    if (i == 1)
     Result = c->v1;
    else if (i == 2)
     Result = c->v2;
    else if (i == 3)
     Result = c->v3;
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = c->v1;
        } 
      return (Result);} 
  } 


/* The c++ function for: getVar(c:LargeIntConstraint,i:integer) [] */
AbstractVar * choco_getVar_LargeIntConstraint_choco(LargeIntConstraint *c,int i)
{ { AbstractVar *Result ;
    if ((i <= c->nbVars) && 
        (1 <= i))
     Result = OBJECT(AbstractVar,(*(c->vars))[i]);
    else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        Result = OBJECT(AbstractVar,(*(c->vars))[1]);
        } 
      return (Result);} 
  } 


/* The c++ function for: getVar(c:LargeCompositeConstraint,i:integer) [] */
AbstractVar * choco_getVar_LargeCompositeConstraint_choco(LargeCompositeConstraint *c,int i)
{ GC_BIND;
  { AbstractVar *Result ;
    { OID  constIdxtest;
      { { OID  i0_some = CNULL;
          { int  i0 = 1;
            int  g0163 = c->nbConst;
            { OID gc_local;
              while ((i0 <= g0163))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(c->loffset))[i0])
                 { i0_some= i0;
                  break;} 
                ++i0;
                } 
              } 
            } 
          constIdxtest = i0_some;
          } 
        GC_OID(constIdxtest);} 
      if (constIdxtest != CNULL)
       { int  constIdx = constIdxtest;
        int  realIdx = ((constIdx == 1) ?
          i :
          (i-((*(c->loffset))[(constIdx-1)])) );
        Result = OBJECT(IntVar,(*choco.getVar)((*(c->lconst))[constIdx],
          realIdx));
        } 
      else { int  realIdx = (i-(last_list(c->loffset)));
          if (realIdx <= c->additionalVars->length)
           Result = OBJECT(IntVar,(*(c->additionalVars))[realIdx]);
          else { close_exception(((general_error *) (*Core._general_error)(_string_("wrong var index (~S) for ~S"),
                _oid_(list::alloc(2,i,_oid_(c))))));
              Result = OBJECT(IntVar,(*choco.getVar)((*(c->lconst))[1],
                1));
              } 
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getVar(c:BinCompositeConstraint,i:integer) [] */
AbstractVar * choco_getVar_BinCompositeConstraint_choco(BinCompositeConstraint *c,int i)
{ if (i <= c->offset) 
  { { AbstractVar *Result ;
      Result = OBJECT(IntVar,(*choco.getVar)(GC_OID(_oid_(c->const1)),
        i));
      return (Result);} 
     } 
  else{ GC_BIND;
    { AbstractVar *Result ;
      Result = OBJECT(IntVar,(*choco.getVar)(GC_OID(_oid_(c->const2)),
        (i-c->offset)));
      GC_UNBIND; return (Result);} 
    } 
  } 


// for each constraint C that involves IntVar (therefore, all IntConstraint,
// some GlobalConstraint and some CompositeConstraint), we implement
// propagation by defining
//   - a method awakeOnInf(C,i) saying how C reacts
//              to an increase of the inf of its ith variable
//   - a method awakeOnSup(C,i) saying how C reacts
//              to a decrease of the sup of its ith variable
//   - a method awakeOnInst(C,i) saying how C reacts
//              when its ith variable is assigned a value
//   - a method awakeOnRem(C,i,x) saying how C reacts
//              when its ith variable has lost the value x
//   - a method askIfEntailed(C) saying whether one can infer
//              that C is true, false or not.
//   - a method testIfSatisfied(C) testing whether C is true or not
//              This is called only when all variables are instantiated
// The constraint may in addition refine the following definitions:
//   - a method testIfCompletelyInstantiated(C) testing
//              whether all variables in C are instantiated
//   - a method getNbVars(C) indicating the number of variables involved in constraint
//   - a method onePropagation(C) performing one pass of propagation on C.
//     However, unlike awake, it may not reach saturation (the fix-point of complete propagation)
//     Therefore, it returns a boolean indicating whether additional calls are needed
//     to reach it.
// a few default definitions
/* The c++ function for: disconnect(c:AbstractConstraint) [] */
void  choco_disconnect_AbstractConstraint_choco(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("disconnect has not yet been defined on ~S, it should be !"),
    _oid_(list::alloc(1,_oid_(c))))));
  } 


/* The c++ function for: connect(c:AbstractConstraint) [] */
void  choco_connect_AbstractConstraint_choco(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("connect has not yet been defined on ~S, it should be !"),
    _oid_(list::alloc(1,_oid_(c))))));
  } 


/* The c++ function for: opposite(c:AbstractConstraint) [] */
AbstractConstraint * choco_opposite_AbstractConstraint(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("opposite has not yet been defined on ~S, it should be !"),
    _oid_(list::alloc(1,_oid_(c))))));
  return (c);} 


/* The c++ function for: askIfEntailed(c:AbstractConstraint) [] */
OID  choco_askIfEntailed_AbstractConstraint(AbstractConstraint *c)
{ { OID Result = 0;
    if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) c))))) == CTRUE)
     Result = _oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) c)));
    else Result = CNULL;
      return (Result);} 
  } 


//v0.93
// v1.04: this is the function that must be implemented on all constraint classes
// TODO: note that this definition is on Ephemeral, while it should be on AbstractConstraint. 
// This is because of the definition of propagate@Problem. This should be fixed (rename propagate/awake into awake/awakeAtFirst)
/* The c++ function for: propagate(c:Ephemeral) [] */
void  choco_propagate_Ephemeral(Ephemeral *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the propagate method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(c))))));
  } 


// v1.05 <brg> <thb> new default definitions:
// forget the variable on which the event (domain update) has occurred and call the basic propagation function
/* The c++ function for: awakeOnInf(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnInf_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnSup(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnSup_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnInst(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnInst_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnRem(c:AbstractConstraint,idx:integer,x:integer) [] */
void  choco_awakeOnRem_AbstractConstraint(AbstractConstraint *c,int idx,int x)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnVar(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnVar_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awake(c:AbstractConstraint) [] */
void  choco_awake_AbstractConstraint_choco(AbstractConstraint *c)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnKer(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnKer_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnEnv(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnEnv_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: awakeOnInstSet(c:AbstractConstraint,idx:integer) [] */
void  choco_awakeOnInstSet_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: testIfSatisfied(c:AbstractConstraint) [] */
ClaireBoolean * choco_testIfSatisfied_AbstractConstraint(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the feasibility test has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(c))))));
  return (CFALSE);} 


/* The c++ function for: testIfCompletelyInstantiated(c:AbstractConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_AbstractConstraint(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the instantiation test has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(c))))));
  return (CFALSE);} 


// claire3 port
// The interface of the AbstractConstraint class
// v0.38: the needToAwake field moves from DelayedConstraint to Delayer
// and is replaced by an interface function noNeedToAwake on rhe DelayedConstraint class
// v0.9906: moved code for Delayer to iprop.cl
// v0.9907: remove the DelayedConstraint class
// Note: the three next definitions are usually not optimal (redundant propagation)
// and should therefore preferably be redefined for subclasses
/* The c++ function for: propagate(c:UnIntConstraint) [] */
void  choco_propagate_UnIntConstraint(UnIntConstraint *c)
{ _void_(choco.awakeOnInf->fcall(((int) c),((int) 1)));
  _void_(choco.awakeOnSup->fcall(((int) c),((int) 1)));
  } 


/* The c++ function for: propagate(c:BinIntConstraint) [] */
void  choco_propagate_BinIntConstraint(BinIntConstraint *c)
{ _void_(choco.awakeOnInf->fcall(((int) c),((int) 1)));
  _void_(choco.awakeOnInf->fcall(((int) c),((int) 2)));
  _void_(choco.awakeOnSup->fcall(((int) c),((int) 1)));
  _void_(choco.awakeOnSup->fcall(((int) c),((int) 2)));
  } 


/* The c++ function for: propagate(c:LargeIntConstraint) [] */
void  choco_propagate_LargeIntConstraint(LargeIntConstraint *c)
{ { int  i = 1;
    int  g0164 = c->nbVars;
    { OID gc_local;
      while ((i <= g0164))
      { // HOHO, GC_LOOP not needed !
        _void_(choco.awakeOnInf->fcall(((int) c),((int) i)));
        _void_(choco.awakeOnSup->fcall(((int) c),((int) i)));
        ++i;
        } 
      } 
    } 
  } 


// Note: Within the layered propagation framework, this function must return a Boolean
// indicating whether some events have been popped for this constraint or not.
//  - Whenever it returns no, a fixpoint has been reached
//  - Otherwise, one propagation pass was performed and the function should be called
//    a second time to check whether some new events have been produced.
// V0.30, 0.33 <thb>
// ********************************************************************
// *   Part 7: compiler optimization                                  *
// ********************************************************************
// Dispatching the virtual methods
//  (trying to improve over the std Claire dispatch)
// claire3 port: remove DispatchIndexValue / function arrays
// claire3 port: remove that ugly ptach for fast dispatch of the main methods
// (no more register)
/* The c++ function for: doAwake(c:AbstractConstraint) [] */
void  choco_doAwake_AbstractConstraint(AbstractConstraint *c)
{ (*choco.awake)(_oid_(c));
  } 


/* The c++ function for: doPropagate(c:AbstractConstraint) [] */
void  choco_doPropagate_AbstractConstraint(AbstractConstraint *c)
{ _void_(choco.propagate->fcall(((int) c)));
  } 


/* The c++ function for: doAwakeOnInf(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnInf_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.awakeOnInf->fcall(((int) c),((int) idx)));
  } 


/* The c++ function for: doAwakeOnSup(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnSup_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.awakeOnSup->fcall(((int) c),((int) idx)));
  } 


/* The c++ function for: doAwakeOnInst(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnInst_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.awakeOnInst->fcall(((int) c),((int) idx)));
  } 


/* The c++ function for: doAwakeOnKer(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnKer_AbstractConstraint(AbstractConstraint *c,int idx)
{ (*choco.awakeOnKer)(_oid_(c),
    idx);
  } 


/* The c++ function for: doAwakeOnEnv(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnEnv_AbstractConstraint(AbstractConstraint *c,int idx)
{ (*choco.awakeOnEnv)(_oid_(c),
    idx);
  } 


/* The c++ function for: doAwakeOnInstSet(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnInstSet_AbstractConstraint(AbstractConstraint *c,int idx)
{ (*choco.awakeOnInstSet)(_oid_(c),
    idx);
  } 


/* The c++ function for: doAwakeOnRem(c:AbstractConstraint,idx:integer,x:integer) [] */
void  choco_doAwakeOnRem_AbstractConstraint(AbstractConstraint *c,int idx,int x)
{ _void_(choco.awakeOnRem->fcall(((int) c),((int) idx),((int) x)));
  } 


/* The c++ function for: doAwakeOnVar(c:AbstractConstraint,idx:integer) [] */
void  choco_doAwakeOnVar_AbstractConstraint(AbstractConstraint *c,int idx)
{ _void_(choco.awakeOnVar->fcall(((int) c),((int) idx)));
  } 


/* The c++ function for: askIfTrue(c:AbstractConstraint) [] */
OID  choco_askIfTrue_AbstractConstraint(AbstractConstraint *c)
{ return (choco.askIfEntailed->fcall(((int) c)));} 


/* The c++ function for: testIfTrue(c:AbstractConstraint) [] */
ClaireBoolean * choco_testIfTrue_AbstractConstraint(AbstractConstraint *c)
{ return (OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) c)))));} 


/* The c++ function for: testIfInstantiated(c:AbstractConstraint) [] */
ClaireBoolean * choco_testIfInstantiated_AbstractConstraint(AbstractConstraint *c)
{ return (OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) c)))));} 


// ********************************************************************
// *   Part 7: delayed constraints layered propagation                *
// ********************************************************************
// v0.30 : abstract events for delayed propagation
// The four following methods make any constraint "delayable".
// Returns true when the event is relevant (ie: the constraint will definitely
// need to be waken to react to that abstract event)
// Constraints considering specific ways of abstracting an event
// (more specific abstract events than the generic "something has changed")
// must refine these methods. This is for instance the case with LinComb
// that implements two flags improvedUpperBound & improvedLowerBound.
/* The c++ function for: abstractIncInf(c:AbstractConstraint,i:integer) [] */
ClaireBoolean * choco_abstractIncInf_AbstractConstraint(AbstractConstraint *c,int i)
{ return (CTRUE);} 


/* The c++ function for: abstractDecSup(c:AbstractConstraint,i:integer) [] */
ClaireBoolean * choco_abstractDecSup_AbstractConstraint(AbstractConstraint *c,int i)
{ return (CTRUE);} 


/* The c++ function for: abstractInstantiate(c:AbstractConstraint,i:integer) [] */
ClaireBoolean * choco_abstractInstantiate_AbstractConstraint(AbstractConstraint *c,int i)
{ return (CTRUE);} 


/* The c++ function for: abstractRemoveVal(c:AbstractConstraint,i:integer,x:integer) [] */
ClaireBoolean * choco_abstractRemoveVal_AbstractConstraint(AbstractConstraint *c,int i,int x)
{ return (CTRUE);} 


// The Delayer class, calls abstractXxx on its target when an event occur
// v0.33 <thb> always call abstractXxx (for side effexts on the target, like setting the
//    improvedUpperBound and improvedLowerBound flags on a LinComb), then update needToAwake
// v0.9907: no more needToAwake flag: replaced by the presence/absence of the ConstAwakeEvent in the queue
/* The c++ function for: awakeOnInf(d:Delayer,idx:integer) [] */
void  choco_awakeOnInf_Delayer(Delayer *d,int idx)
{ if ((OBJECT(ClaireBoolean,(*choco.abstractIncInf)(GC_OID(_oid_(d->target)),
    idx))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnSup(d:Delayer,idx:integer) [] */
void  choco_awakeOnSup_Delayer(Delayer *d,int idx)
{ if ((OBJECT(ClaireBoolean,(*choco.abstractDecSup)(GC_OID(_oid_(d->target)),
    idx))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInst(d:Delayer,idx:integer) [] */
void  choco_awakeOnInst_Delayer(Delayer *d,int idx)
{ if ((OBJECT(ClaireBoolean,(*choco.abstractInstantiate)(GC_OID(_oid_(d->target)),
    idx))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRem(d:Delayer,idx:integer,x:integer) [] */
void  choco_awakeOnRem_Delayer(Delayer *d,int idx,int x)
{ if ((OBJECT(ClaireBoolean,(*choco.abstractRemoveVal)(GC_OID(_oid_(d->target)),
    idx,
    x))) == CTRUE) 
  { choco_constAwake_AbstractConstraint(d,CFALSE);
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// The Delayer class delegates others method to its target constraint (decorator pattern)
/* The c++ function for: testIfSatisfied(c:Delayer) [] */
ClaireBoolean * choco_testIfSatisfied_Delayer(Delayer *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfCompletelyInstantiated(c:Delayer) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_Delayer(Delayer *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfCompletelyInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:Delayer) [] */
int  choco_getNbVars_Delayer(Delayer *c)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getNbVars->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: askIfEntailed(c:Delayer) [] */
OID  choco_askIfEntailed_Delayer(Delayer *c)
{ GC_BIND;
  { OID Result = 0;
    Result = choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target)))));
    GC_UNBIND; return (Result);} 
  } 


// v0.30 Delayed constraint layered propagation  (code adapted from what was part 6 of iprop.cl)
// delegation of the onePropagation method for a Delayer
//
// <thb> v0.31: this function returns a Boolean indicating whether the propagation actually
// did something or not (generated propagation events).
// v0.9906: this function now returns void
/* The c++ function for: propagate(c:Delayer) [] */
void  choco_propagate_Delayer(Delayer *c)
{ GC_BIND;
  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->target))))));
  GC_UNBIND;} 


// v0.9907
/* The c++ function for: awake(c:Delayer) [] */
void  choco_awake_Delayer_choco(Delayer *c)
{ GC_BIND;
  (*choco.awake)(GC_OID(_oid_(c->target)));
  GC_UNBIND;} 


// v1.02 modified range to IntVar (more homogeneous)
/* The c++ function for: getVar(c:Delayer,i:integer) [] */
AbstractVar * choco_getVar_Delayer_choco(Delayer *c,int i)
{ GC_BIND;
  { AbstractVar *Result ;
    Result = OBJECT(AbstractVar,(*choco.getVar)(GC_OID(_oid_(c->target)),
      i));
    GC_UNBIND; return (Result);} 
  } 


// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 10: constraint networks                                   *
// ********************************************************************
// Building the constraint network
// 1. Adding an integer variable
/* The c++ function for: addIntVar(p:Problem,v:IntVar) [] */
void  choco_addIntVar_Problem(Problem *p,IntVar *v)
{ GC_BIND;
  GC_OBJECT(list,p->vars)->addFast(_oid_(v));
  (v->problem = p);
  if (p->vars->length == (p->propagationEngine->maxSize+1))
   princ_string("Watchout: the problem size is too small: risk of event queue overflows");
  GC_UNBIND;} 


// 2. connecting constraints and variables
// *** Part 10a: connecting a constraint ******************************
// This function connects a constraint with its variables in several ways.
// Note that connect and connect may only be called while a constraint
// has been fully created and is being posted to a problem !
// Note that it should be called only once per constraint !
// (Note: these functions are redefined on GlobalConstraint (LinComb, Matching, ...)
// v1.0: inform the hook of the connection (useful architecture for Palm explanation)
// v1.03 definition on the abstract class <ega>
/* The c++ function for: connect(c:IntConstraint) [] */
void  choco_connect_IntConstraint_choco(IntConstraint *c)
{ GC_BIND;
  { int  n = choco.getNbVars->fcall(((int) c));
    { int  i = 1;
      int  g0165 = n;
      { OID gc_local;
        while ((i <= g0165))
        { GC_LOOP;
          choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,OBJECT(IntVar,(*choco.getVar)(_oid_(c),
            i))),i);
          ++i;
          GC_UNLOOP;} 
        } 
      } 
    if (c->hook != CNULL)
     (*choco.connectHook)(GC_OID(c->hook),
      _oid_(c));
    } 
  GC_UNBIND;} 


/* The c++ function for: connect(c:UnIntConstraint) [] */
void  choco_connect_UnIntConstraint_choco(UnIntConstraint *c)
{ GC_BIND;
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v1),1);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: connect(c:BinIntConstraint) [] */
void  choco_connect_BinIntConstraint_choco(BinIntConstraint *c)
{ GC_BIND;
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v1),1);
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v2),2);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: connect(c:TernIntConstraint) [] */
void  choco_connect_TernIntConstraint_choco(TernIntConstraint *c)
{ GC_BIND;
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v1),1);
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v2),2);
  choco_connectIntVar_AbstractConstraint1(c,GC_OBJECT(IntVar,c->v3),3);
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: connect(c:LargeIntConstraint) [] */
void  choco_connect_LargeIntConstraint_choco(LargeIntConstraint *c)
{ GC_BIND;
  { int  i = 1;
    int  g0166 = c->nbVars;
    { OID gc_local;
      while ((i <= g0166))
      { // HOHO, GC_LOOP not needed !
        choco_connectIntVar_AbstractConstraint1(c,OBJECT(IntVar,(*(c->vars))[i]),i);
        ++i;
        } 
      } 
    } 
  if (c->hook != CNULL)
   (*choco.connectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: connect(c:CompositeConstraint) [] */
void  choco_connect_CompositeConstraint_choco(CompositeConstraint *c)
{ GC_BIND;
  (*choco.assignIndices)(_oid_(c),
    _oid_(c),
    0);
  if (c->hook != CNULL)
   (*choco.connectHook)(c->hook,
    _oid_(c));
  GC_UNBIND;} 


// v0.30: a Delayer is a sort of "UnCompositeConstraint" wrt constraint connection
// v1.02 <naren> call connectHook on target subconstraint
/* The c++ function for: connect(d:Delayer) [] */
void  choco_connect_Delayer_choco(Delayer *d)
{ GC_BIND;
  (*choco.assignIndices)(GC_OID(_oid_(d->target)),
    _oid_(d),
    0);
  if (d->target->hook != CNULL)
   (*choco.connectHook)(d->target->hook,
    GC_OID(_oid_(d->target)));
  GC_UNBIND;} 


// These connections will be used by the event scheduler.
//   1. for all the concerned variables v, the constraint c is stored in the list
//      of v.constraints (the list of constraints involving v). This allows us to
//      inform all such constraints that an event has occurred on v. Thus, we only
//      store a queue of events associated to a variable rather than a pair (v,c) => shorter queue
//   2. In order for such a list to avoid re-informing an event-generating constraint
//      about its own occurrence, we store a redundant index telling how to retrieve
//      a constraint c in all lists v.constraints for its variables v.
//      (ie, if wake(c1) => event(v) => wake(c) for c in (v.constraints BUT c1)
//      This supports a propagation of c on v by cyclic iteration on (v.constraints but c)
//   3. In order to avoid wakening constraints that are fully satisfied, there is
//      a linked list iteration mechanism on the list v.constraints.
//      Note here, that although the
//      "nextConst" list is a backtrackable list of indices (the index next active constraint
//      in this chained list), unbacktrackable updates are performed on it. Indeed, constraint
//      posting is not backtrackable !
/* The c++ function for: connectIntVar(cont:AbstractConstraint,u:IntVar,i:integer) [] */
void  choco_connectIntVar_AbstractConstraint1(AbstractConstraint *cont,IntVar *u,int i)
{ choco_connectIntVar_AbstractConstraint2(cont,
    u,
    i,
    CTRUE,
    CTRUE,
    CTRUE,
    CTRUE);
  } 


/* The c++ function for: connectIntVar(cont:AbstractConstraint,u:IntVar,i:integer,activeOnInst:boolean,activeOnInf:boolean,activeOnSup:boolean,activeOnRem:boolean) [] */
void  choco_connectIntVar_AbstractConstraint2(AbstractConstraint *cont,IntVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem)
{ GC_BIND;
  ;GC_OBJECT(list,u->constraints)->addFast(_oid_(cont));
  (u->indices = GC_OBJECT(list,u->indices)->addFast(i));
  { int  n = u->nbConstraints;
    (u->nbConstraints = (n+1));
    (*choco.setConstraintIndex)(_oid_(cont),
      i,
      (n+1));
    } 
  (*choco.connectIntVarEvents)(_oid_(u),
    _oid_(activeOnInst),
    _oid_(activeOnInf),
    _oid_(activeOnSup),
    _oid_(activeOnRem));
  GC_UNBIND;} 


// v1.0 reorganized
/* The c++ function for: connectIntVarEvents(u:IntVar,activeOnInst:boolean,activeOnInf:boolean,activeOnSup:boolean,activeOnRem:boolean) [] */
void  choco_connectIntVarEvents_IntVar_choco(IntVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem)
{ GC_BIND;
  ;choco_connectEvent_VarEvent(GC_OBJECT(IncInf,u->updtInfEvt),u->nbConstraints,activeOnInf);
  choco_connectEvent_VarEvent(GC_OBJECT(DecSup,u->updtSupEvt),u->nbConstraints,activeOnSup);
  choco_connectEvent_VarEvent(GC_OBJECT(InstInt,u->instantiateEvt),u->nbConstraints,activeOnInst);
  choco_connectEvent_VarEvent(GC_OBJECT(ValueRemovals,u->remValEvt),u->nbConstraints,activeOnRem);
  GC_UNBIND;} 


/* The c++ function for: connectEvent(e:VarEvent,nbconst:integer,active:boolean) [] */
void  choco_connectEvent_VarEvent(VarEvent *e,int nbconst,ClaireBoolean *active)
{ if (nbconst > 1) 
  { { list * lnext = GC_OBJECT(list,e->nextConst);
      int  j = (*(lnext))[(nbconst-1)];
      int  k = (nbconst-1);
      if ((j == 0) && 
          (active == CTRUE))
       lnext= GC_OBJECT(list,lnext->addFast(nbconst));
      else lnext= GC_OBJECT(list,lnext->addFast(j));
        if (active == CTRUE)
       { while (((k > 0) && 
            ((j <= k) && 
              ((*(lnext))[k] == j))))
        { ((*(lnext))[k]=nbconst);
          k= (k-1);
          } 
        } 
      (e->nextConst = lnext);
      } 
     } 
  else{ if (active == CTRUE) 
    { (e->nextConst = list::alloc(Kernel._integer,1,1));
       } 
    else{ GC_BIND;
      (e->nextConst = list::alloc(Kernel._integer,1,0));
      GC_UNBIND;} 
    } 
  } 


// on the variable and it is connected.
// *** Part 10b: disconnecting a constraint ******************************
// v1.0: disconnects the i-th constraint linked to variable u
// v1.01 too many ~S
/* The c++ function for: disconnectIntVar(u:IntVar,i:integer) [] */
void  choco_disconnectIntVar_IntVar(IntVar *u,int i)
{ ;(*choco.disconnectIntVarEvents)(_oid_(u),
    i,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue);
  } 


/* The c++ function for: disconnectIntVarEvents(u:IntVar,i:integer,passiveOnInst:boolean,passiveOnInf:boolean,passiveOnSup:boolean,passiveOnRem:boolean) [] */
void  choco_disconnectIntVarEvents_IntVar_choco(IntVar *u,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnInf,ClaireBoolean *passiveOnSup,ClaireBoolean *passiveOnRem)
{ GC_BIND;
  if (passiveOnInst == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(InstInt,u->instantiateEvt),i);
  if (passiveOnInf == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(IncInf,u->updtInfEvt),i);
  if (passiveOnSup == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(DecSup,u->updtSupEvt),i);
  if (passiveOnRem == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(ValueRemovals,u->remValEvt),i);
  GC_UNBIND;} 


// v1.0: disconnects the i-th constraint linked to variable u for the event e
/* The c++ function for: disconnectEvent(e:VarEvent,i:integer) [] */
void  choco_disconnectEvent_VarEvent(VarEvent *e,int i)
{ { list * lnext = e->nextConst;
    int  n = e->modifiedVar->nbConstraints;
    if ((*(lnext))[i] == 0)
     ;else if ((*(lnext))[i] == i)
     { int  k = 1;
      int  g0167 = n;
      { while ((k <= g0167))
        { STOREI((*lnext)[k],0);
          ++k;
          } 
        } 
      } 
    else { int  j = (*(lnext))[i];
        int  k = ((i == 1) ?
          n :
          (i-1) );
        { while (((*(lnext))[k] == i))
          { STOREI((*lnext)[k],j);
            k= ((k == 1) ?
              n :
              (k-1) );
            } 
          } 
        } 
      } 
  } 


/* The c++ function for: connectSetVar(cont:AbstractConstraint,u:SetVar,i:integer) [] */
void  choco_connectSetVar_AbstractConstraint1(AbstractConstraint *cont,SetVar *u,int i)
{ choco_connectSetVar_AbstractConstraint2(cont,
    u,
    i,
    CTRUE,
    CTRUE,
    CTRUE);
  } 


/* The c++ function for: connectSetVar(cont:AbstractConstraint,u:SetVar,i:integer,activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) [] */
void  choco_connectSetVar_AbstractConstraint2(AbstractConstraint *cont,SetVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv)
{ GC_BIND;
  ;GC_OBJECT(list,u->constraints)->addFast(_oid_(cont));
  (u->indices = GC_OBJECT(list,u->indices)->addFast(i));
  { int  n = u->nbConstraints;
    (u->nbConstraints = (n+1));
    (*choco.setConstraintIndex)(_oid_(cont),
      i,
      (n+1));
    } 
  (*choco.connectSetVarEvents)(_oid_(u),
    _oid_(activeOnInst),
    _oid_(activeOnKer),
    _oid_(activeOnEnv));
  GC_UNBIND;} 


/* The c++ function for: connectSetVarEvents(u:SetVar,activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) [] */
void  choco_connectSetVarEvents_SetVar_choco(SetVar *u,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv)
{ GC_BIND;
  ;choco_connectEvent_VarEvent(GC_OBJECT(IncKer,u->updtKerEvt),u->nbConstraints,activeOnKer);
  choco_connectEvent_VarEvent(GC_OBJECT(DecEnv,u->updtEnvEvt),u->nbConstraints,activeOnEnv);
  choco_connectEvent_VarEvent(GC_OBJECT(InstSet,u->instantiateEvt),u->nbConstraints,activeOnInst);
  GC_UNBIND;} 


/* The c++ function for: disconnectSetVar(u:SetVar,i:integer) [] */
void  choco_disconnectSetVar_SetVar(SetVar *u,int i)
{ (*choco.disconnectSetVarEvents)(_oid_(u),
    i,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue);
  } 


/* The c++ function for: disconnectSetVarEvents(u:SetVar,i:integer,passiveOnInst:boolean,passiveOnKer:boolean,passiveOnEnv:boolean) [] */
void  choco_disconnectSetVarEvents_SetVar_choco(SetVar *u,int i,ClaireBoolean *passiveOnInst,ClaireBoolean *passiveOnKer,ClaireBoolean *passiveOnEnv)
{ GC_BIND;
  ;if (passiveOnKer == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(IncKer,u->updtKerEvt),i);
  if (passiveOnEnv == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(DecEnv,u->updtEnvEvt),i);
  if (passiveOnInst == CTRUE)
   choco_disconnectEvent_VarEvent(GC_OBJECT(InstSet,u->instantiateEvt),i);
  GC_UNBIND;} 


/* The c++ function for: reconnectSetVar(u:SetVar,i:integer) [] */
void  choco_reconnectSetVar_SetVar(SetVar *u,int i)
{ (*choco.reconnectSetVarEvents)(_oid_(u),
    i,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue);
  } 


/* The c++ function for: reconnectSetVarEvents(u:SetVar,i:integer,activeOnInst:boolean,activeOnKer:boolean,activeOnEnv:boolean) [] */
void  choco_reconnectSetVarEvents_SetVar_choco(SetVar *u,int i,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnKer,ClaireBoolean *activeOnEnv)
{ GC_BIND;
  ;if (activeOnKer == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(IncKer,u->updtKerEvt),i);
  if (activeOnEnv == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(DecEnv,u->updtEnvEvt),i);
  if (activeOnInst == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(InstSet,u->instantiateEvt),i);
  GC_UNBIND;} 


// v1.0: inform the hook of the connection (useful architecture for Palm explanation)
/* The c++ function for: disconnect(c:UnIntConstraint) [] */
void  choco_disconnect_UnIntConstraint_choco(UnIntConstraint *c)
{ GC_BIND;
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:BinIntConstraint) [] */
void  choco_disconnect_BinIntConstraint_choco(BinIntConstraint *c)
{ GC_BIND;
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:TernIntConstraint) [] */
void  choco_disconnect_TernIntConstraint_choco(TernIntConstraint *c)
{ GC_BIND;
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  choco_disconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v3),c->idx3);
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:LargeIntConstraint) [] */
void  choco_disconnect_LargeIntConstraint_choco(LargeIntConstraint *c)
{ GC_BIND;
  { int  i = 1;
    int  g0168 = c->nbVars;
    { OID gc_local;
      while ((i <= g0168))
      { // HOHO, GC_LOOP not needed !
        choco_disconnectIntVar_IntVar(OBJECT(IntVar,(*(c->vars))[i]),(*(c->indices))[i]);
        ++i;
        } 
      } 
    } 
  if (c->hook != CNULL)
   (*choco.disconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: disconnect(c:Delayer) [] */
void  choco_disconnect_Delayer_choco(Delayer *c)
{ GC_BIND;
  (*choco.disconnect)(GC_OID(_oid_(c->target)));
  if (c->hook != CNULL)
   (*choco.disconnectHook)(c->hook,
    _oid_(c));
  GC_UNBIND;} 


// *** Part 10c: disconnecting a constraint ******************************
// v1.0: reconnecting a constraint that was temporarily disconnected from the network
/* The c++ function for: reconnect(c:UnIntConstraint) [] */
void  choco_reconnect_UnIntConstraint_choco(UnIntConstraint *c)
{ GC_BIND;
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:BinIntConstraint) [] */
void  choco_reconnect_BinIntConstraint_choco(BinIntConstraint *c)
{ GC_BIND;
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:TernIntConstraint) [] */
void  choco_reconnect_TernIntConstraint_choco(TernIntConstraint *c)
{ GC_BIND;
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2);
  choco_reconnectIntVar_IntVar(GC_OBJECT(IntVar,c->v3),c->idx3);
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:LargeIntConstraint) [] */
void  choco_reconnect_LargeIntConstraint_choco(LargeIntConstraint *c)
{ GC_BIND;
  { int  i = 1;
    int  g0169 = c->nbVars;
    { OID gc_local;
      while ((i <= g0169))
      { // HOHO, GC_LOOP not needed !
        choco_reconnectIntVar_IntVar(OBJECT(IntVar,(*(c->vars))[i]),(*(c->indices))[i]);
        ++i;
        } 
      } 
    } 
  if (c->hook != CNULL)
   (*choco.reconnectHook)(GC_OID(c->hook),
    _oid_(c));
  GC_UNBIND;} 


/* The c++ function for: reconnect(c:AbstractConstraint) [] */
void  choco_reconnect_AbstractConstraint_choco(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("TODO: reconnect still not implemented on ~S"),
    _oid_(list::alloc(1,_oid_(c))))));
  } 


// reconnects the i-th constraint involving variable v
/* The c++ function for: reconnectIntVar(v:IntVar,idx:integer) [] */
void  choco_reconnectIntVar_IntVar(IntVar *v,int idx)
{ (*choco.reconnectIntVarEvents)(_oid_(v),
    idx,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue,
    Kernel.ctrue);
  } 


/* The c++ function for: reconnectIntVarEvents(u:IntVar,idx:integer,activeOnInst:boolean,activeOnInf:boolean,activeOnSup:boolean,activeOnRem:boolean) [] */
void  choco_reconnectIntVarEvents_IntVar_choco(IntVar *u,int idx,ClaireBoolean *activeOnInst,ClaireBoolean *activeOnInf,ClaireBoolean *activeOnSup,ClaireBoolean *activeOnRem)
{ GC_BIND;
  ;if (activeOnInst == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(InstInt,u->instantiateEvt),idx);
  if (activeOnInf == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(IncInf,u->updtInfEvt),idx);
  if (activeOnSup == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(DecSup,u->updtSupEvt),idx);
  if (activeOnRem == CTRUE)
   choco_reconnectEvent_VarEvent(GC_OBJECT(ValueRemovals,u->remValEvt),idx);
  GC_UNBIND;} 


/* The c++ function for: reconnectEvent(e:VarEvent,idx:integer) [] */
void  choco_reconnectEvent_VarEvent(VarEvent *e,int idx)
{ { list * lnext = e->nextConst;
    int  nbconst = lnext->length;
    int  nextCI = (*(lnext))[idx];
    if (nextCI == 0)
     { int  i = 1;
      int  g0170 = nbconst;
      { while ((i <= g0170))
        { ((*(lnext))[i]=idx);
          ++i;
          } 
        } 
      } 
    else { int  k1 = ((idx > 1) ?
          (idx-1) :
          nbconst );
        ClaireBoolean * needToContinue = CTRUE;
        { while ((needToContinue == CTRUE))
          { if ((*(lnext))[k1] == nextCI)
             { ((*(lnext))[k1]=idx);
              needToContinue= _I_equal_any(k1,nextCI);
              } 
            else needToContinue= CFALSE;
              k1= ((k1 == 1) ?
              nbconst :
              (k1-1) );
            } 
          } 
        } 
      } 
  } 


// a constraint is active if it is connected to the network and if it does propagate
/* The c++ function for: isActive(c:AbstractConstraint) [] */
ClaireBoolean * choco_isActive_AbstractConstraint_choco(AbstractConstraint *c)
{ close_exception(((general_error *) (*Core._general_error)(_string_("TODO: isActive@AbstractConstraint still not implemented"),
    _oid_(Kernel.nil))));
  return (CTRUE);} 


/* The c++ function for: isActive(c:UnIntConstraint) [] */
ClaireBoolean * choco_isActive_UnIntConstraint_choco(UnIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActive(c:BinIntConstraint) [] */
ClaireBoolean * choco_isActive_BinIntConstraint_choco(BinIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1) == CTRUE) ? CTRUE : ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActive(c:TernIntConstraint) [] */
ClaireBoolean * choco_isActive_TernIntConstraint_choco(TernIntConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v1),c->idx1) == CTRUE) ? CTRUE : ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v2),c->idx2) == CTRUE) ? CTRUE : ((choco_isActiveIntVar_IntVar(GC_OBJECT(IntVar,c->v3),c->idx3) == CTRUE) ? CTRUE : CFALSE)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActive(c:LargeIntConstraint) [] */
ClaireBoolean * choco_isActive_LargeIntConstraint_choco(LargeIntConstraint *c)
{ { ClaireBoolean *Result ;
    { OID  g0172UU;
      { int  i = 1;
        int  g0171 = c->nbVars;
        { OID gc_local;
          g0172UU= _oid_(CFALSE);
          while ((i <= g0171))
          { // HOHO, GC_LOOP not needed !
            if (choco_isActiveIntVar_IntVar(OBJECT(IntVar,(*(c->vars))[i]),(*(c->indices))[i]) == CTRUE)
             { g0172UU = Kernel.ctrue;
              break;} 
            ++i;
            } 
          } 
        } 
      Result = boolean_I_any(g0172UU);
      } 
    return (Result);} 
  } 


// v1.02 <naren>
/* The c++ function for: isActive(d:Delayer) [] */
ClaireBoolean * choco_isActive_Delayer_choco(Delayer *d)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,(*choco.isActive)(GC_OID(_oid_(d->target))));
    GC_UNBIND; return (Result);} 
  } 


// v1.02 <naren>
/* The c++ function for: isActive(c:BinCompositeConstraint) [] */
ClaireBoolean * choco_isActive_BinCompositeConstraint_choco(BinCompositeConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*choco.isActive)(GC_OID(_oid_(c->const1))))) == CTRUE) ? CTRUE : (((OBJECT(ClaireBoolean,(*choco.isActive)(GC_OID(_oid_(c->const2))))) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActive(c:LargeCompositeConstraint) [] */
ClaireBoolean * choco_isActive_LargeCompositeConstraint_choco(LargeCompositeConstraint *c)
{ { ClaireBoolean *Result ;
    { ClaireBoolean *v_or;
      { { OID  g0175UU;
          { int  i = 1;
            int  g0173 = c->nbConst;
            { OID gc_local;
              g0175UU= _oid_(CFALSE);
              while ((i <= g0173))
              { // HOHO, GC_LOOP not needed !
                if ((OBJECT(ClaireBoolean,(*choco.isActive)((*(c->lconst))[i]))) == CTRUE)
                 { g0175UU = Kernel.ctrue;
                  break;} 
                ++i;
                } 
              } 
            } 
          v_or = boolean_I_any(g0175UU);
          } 
        if (v_or == CTRUE) Result =CTRUE; 
        else { { OID  g0176UU;
            { int  j = 1;
              int  g0174 = c->additionalVars->length;
              { OID gc_local;
                g0176UU= _oid_(CFALSE);
                while ((j <= g0174))
                { // HOHO, GC_LOOP not needed !
                  if (choco_isActiveIntVar_IntVar(OBJECT(IntVar,(*(c->additionalVars))[j]),(*(c->additionalIndices))[j]) == CTRUE)
                   { g0176UU = Kernel.ctrue;
                    break;} 
                  ++j;
                  } 
                } 
              } 
            v_or = boolean_I_any(g0176UU);
            } 
          if (v_or == CTRUE) Result =CTRUE; 
          else Result = CFALSE;} 
        } 
      } 
    return (Result);} 
  } 


/* The c++ function for: isActiveIntVar(u:IntVar,i:integer) [] */
ClaireBoolean * choco_isActiveIntVar_IntVar(IntVar *u,int i)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveEvent_VarEvent(GC_OBJECT(InstInt,u->instantiateEvt),i) == CTRUE) ? CTRUE : ((choco_isActiveEvent_VarEvent(GC_OBJECT(IncInf,u->updtInfEvt),i) == CTRUE) ? CTRUE : ((choco_isActiveEvent_VarEvent(GC_OBJECT(DecSup,u->updtSupEvt),i) == CTRUE) ? CTRUE : ((choco_isActiveEvent_VarEvent(GC_OBJECT(ValueRemovals,u->remValEvt),i) == CTRUE) ? CTRUE : CFALSE))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActiveSetVar(u:SetVar,i:integer) [] */
ClaireBoolean * choco_isActiveSetVar_SetVar(SetVar *u,int i)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((choco_isActiveEvent_VarEvent(GC_OBJECT(InstSet,u->instantiateEvt),i) == CTRUE) ? CTRUE : ((choco_isActiveEvent_VarEvent(GC_OBJECT(IncKer,u->updtKerEvt),i) == CTRUE) ? CTRUE : ((choco_isActiveEvent_VarEvent(GC_OBJECT(DecEnv,u->updtEnvEvt),i) == CTRUE) ? CTRUE : CFALSE)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isActiveEvent(e:VarEvent,i:integer) [] */
ClaireBoolean * choco_isActiveEvent_VarEvent(VarEvent *e,int i)
{ { ClaireBoolean *Result ;
    { list * lnext = e->nextConst;
      int  n = lnext->length;
      int  k = ((i == 1) ?
        n :
        (i-1) );
      Result = ((n != 0) ? (((*(lnext))[k] == i) ? CTRUE: CFALSE): CFALSE);
      } 
    return (Result);} 
  } 


// beware, the list may be empty