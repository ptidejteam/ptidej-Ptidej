/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\intconst2.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:28 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: const2.cl                                                  *
// *    additional simple constraints                                 *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: accessing the ith element in a list of values          *
// *   Part 2: AllDifferent                                           *
// *   Part 3: occurences of a value in a list of variables           *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: accessing the ith element in a list of values          *
// ********************************************************************
// Element constraint
//   (accessing the ith element in a list of values, where i is a variable)
// the slot v1 represents the index and the slot v2 represents the value
// propagation with complete arc consistency from values to indices (v2 to v1)
// propagation with interval approximation from indices to values (v1 to v2)
/* The c++ function for: self_print(c:Elt) [] */
void  claire_self_print_Elt_choco(Elt *c)
{ GC_BIND;
  princ_string("ith-elt(");
  print_any(GC_OID(_oid_(c->lval)));
  princ_string(",");
  print_any(GC_OID(_oid_(c->v1)));
  princ_string(") == ");
  print_any(GC_OID(_oid_(c->v2)));
  princ_string("");
  GC_UNBIND;} 


// v0.34 uses the cste slot: l[i + cste] = x
// (ex: cste = 1 allows to use and index from 0 to length(l) - 1
// v0.9907: removed the method without the last argument
/* The c++ function for: makeEltConstraint(l:list[integer],i:IntVar,x:IntVar,offset:integer) [] */
Elt * choco_makeEltConstraint_list(list *l,IntVar *i,IntVar *x,int offset)
{ GC_BIND;
  { Elt *Result ;
    { Elt * _CL_obj = ((Elt *) GC_OBJECT(Elt,new_object_class(choco._Elt)));
      (_CL_obj->v1 = i);
      (_CL_obj->v2 = x);
      (_CL_obj->cste = offset);
      (_CL_obj->lval = ((list *) copy_bag(l)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.25 <fl> replaced all removeVal by remVal (in order to propagate induced consequences)
// v0.26 a few casts
/* The c++ function for: updateValueFromIndex(c:Elt) [] */
void  choco_updateValueFromIndex_Elt(Elt *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list * l = c->lval;
    IntVar * indexVar = c->v1;
    IntVar * valueVar = c->v2;
    int  minval = 99999999;
    int  maxval = -99999999;
    { IntVar * g0581 = indexVar;
      AbstractIntDomain * g0582 = g0581->bucket;
      if (g0582 == (NULL))
       { int  feasibleIndex = g0581->inf->latestValue;
        { OID gc_local;
          while ((feasibleIndex <= g0581->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            { int  val = (*(l))[(feasibleIndex+c->cste)];
              minval= ((val <= minval) ?
                val :
                minval );
              maxval= ((val <= maxval) ?
                maxval :
                val );
              } 
            feasibleIndex= ((g0581->inf->latestValue <= (feasibleIndex+1)) ?
              (feasibleIndex+1) :
              g0581->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g0582->isa,choco._LinkedListIntDomain))
       { int  feasibleIndex = g0581->inf->latestValue;
        { OID gc_local;
          while ((feasibleIndex <= g0581->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            { int  val = (*(l))[(feasibleIndex+c->cste)];
              minval= ((val <= minval) ?
                val :
                minval );
              maxval= ((val <= maxval) ?
                maxval :
                val );
              } 
            feasibleIndex= choco.getNextValue->fcall(((int) g0582),((int) feasibleIndex));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(feasibleIndex);
          bag *feasibleIndex_support;
          feasibleIndex_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0582)))));
          for (START(feasibleIndex_support); NEXT(feasibleIndex);)
          { GC_LOOP;
            { int  val = (*(l))[(feasibleIndex+c->cste)];
              minval= ((val <= minval) ?
                val :
                minval );
              maxval= ((val <= maxval) ?
                maxval :
                val );
              } 
            GC_UNLOOP;} 
          } 
        } 
    (*choco.updateInf)(_oid_(valueVar),
      minval,
      c->idx2);
    (*choco.updateSup)(_oid_(valueVar),
      maxval,
      c->idx2);
    if (valueVar->bucket != (NULL))
     { IntVar * g0583 = valueVar;
      AbstractIntDomain * g0584 = g0583->bucket;
      if (g0584 == (NULL))
       { int  v = g0583->inf->latestValue;
        { OID gc_local;
          while ((v <= g0583->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0591I;
              { OID  g0592UU;
                { ClaireBoolean * V_CL0593;{ OID  g0594UU;
                    { IntVar * g0585 = indexVar;
                      AbstractIntDomain * g0586 = g0585->bucket;
                      if (g0586 == (NULL))
                       { int  i = g0585->inf->latestValue;
                        { OID gc_local;
                          g0594UU= _oid_(CFALSE);
                          while ((i <= g0585->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0594UU = Kernel.ctrue;
                              break;} 
                            i= ((g0585->inf->latestValue <= (i+1)) ?
                              (i+1) :
                              g0585->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0586->isa,choco._LinkedListIntDomain))
                       { int  i = g0585->inf->latestValue;
                        { OID gc_local;
                          g0594UU= _oid_(CFALSE);
                          while ((i <= g0585->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0594UU = Kernel.ctrue;
                              break;} 
                            i= choco.getNextValue->fcall(((int) g0586),((int) i));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i);
                          g0594UU= _oid_(CFALSE);
                          bag *i_support;
                          i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0586)))));
                          for (START(i_support); NEXT(i);)
                          { GC_LOOP;
                            if (v == (*(l))[(i+c->cste)])
                             { g0594UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0593 = boolean_I_any(g0594UU);
                    } 
                  
                  g0592UU=_oid_(V_CL0593);} 
                g0591I = not_any(g0592UU);
                } 
              
              if (g0591I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx2);
                } 
            v= ((g0583->inf->latestValue <= (v+1)) ?
              (v+1) :
              g0583->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0584->isa,choco._LinkedListIntDomain))
       { int  v = g0583->inf->latestValue;
        { OID gc_local;
          while ((v <= g0583->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0595I;
              { OID  g0596UU;
                { ClaireBoolean * V_CL0597;{ OID  g0598UU;
                    { IntVar * g0587 = indexVar;
                      AbstractIntDomain * g0588 = g0587->bucket;
                      if (g0588 == (NULL))
                       { int  i = g0587->inf->latestValue;
                        { OID gc_local;
                          g0598UU= _oid_(CFALSE);
                          while ((i <= g0587->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0598UU = Kernel.ctrue;
                              break;} 
                            i= ((g0587->inf->latestValue <= (i+1)) ?
                              (i+1) :
                              g0587->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0588->isa,choco._LinkedListIntDomain))
                       { int  i = g0587->inf->latestValue;
                        { OID gc_local;
                          g0598UU= _oid_(CFALSE);
                          while ((i <= g0587->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0598UU = Kernel.ctrue;
                              break;} 
                            i= choco.getNextValue->fcall(((int) g0588),((int) i));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i);
                          g0598UU= _oid_(CFALSE);
                          bag *i_support;
                          i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0588)))));
                          for (START(i_support); NEXT(i);)
                          { GC_LOOP;
                            if (v == (*(l))[(i+c->cste)])
                             { g0598UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0597 = boolean_I_any(g0598UU);
                    } 
                  
                  g0596UU=_oid_(V_CL0597);} 
                g0595I = not_any(g0596UU);
                } 
              
              if (g0595I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx2);
                } 
            v= choco.getNextValue->fcall(((int) g0584),((int) v));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0584)))));
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            { ClaireBoolean * g0599I;
              { OID  g0600UU;
                { ClaireBoolean * V_CL0601;{ OID  g0602UU;
                    { IntVar * g0589 = indexVar;
                      AbstractIntDomain * g0590 = g0589->bucket;
                      if (g0590 == (NULL))
                       { int  i = g0589->inf->latestValue;
                        { OID gc_local;
                          g0602UU= _oid_(CFALSE);
                          while ((i <= g0589->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0602UU = Kernel.ctrue;
                              break;} 
                            i= ((g0589->inf->latestValue <= (i+1)) ?
                              (i+1) :
                              g0589->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0590->isa,choco._LinkedListIntDomain))
                       { int  i = g0589->inf->latestValue;
                        { OID gc_local;
                          g0602UU= _oid_(CFALSE);
                          while ((i <= g0589->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (v == (*(l))[(i+c->cste)])
                             { g0602UU = Kernel.ctrue;
                              break;} 
                            i= choco.getNextValue->fcall(((int) g0590),((int) i));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i);
                          g0602UU= _oid_(CFALSE);
                          bag *i_support;
                          i_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0590)))));
                          for (START(i_support); NEXT(i);)
                          { GC_LOOP;
                            if (v == (*(l))[(i+c->cste)])
                             { g0602UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0601 = boolean_I_any(g0602UU);
                    } 
                  
                  g0600UU=_oid_(V_CL0601);} 
                g0599I = not_any(g0600UU);
                } 
              
              if (g0599I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
    } 
  GC_UNBIND;} 


// v0.29: The constraint must enforce that valueVar takes a value among l, which may be a sequence of values with holes.
// When valueVar is represented as an enumeration of values (with v.bucket), this is enforced from the start
// (when the constraint is posted).
// otherwise, when valueVar is only reperesented by its domain bounds, each time a value is removed from its domain
// we need not only to restrict indexVar, but also to propagate back the new bounds for valueVar.
// Thus, the consequences of this function on indexVar must be posted and propagated back to the Elt constraint
// therefore, we replace the cause of the event on indexVar (c.idx1) by 0 => the Elt constraint will be re-awaken later
// with a call to updateValueFromIndex which will recompute the min/max for valueVar
/* The c++ function for: updateIndexFromValue(c:Elt) [] */
void  choco_updateIndexFromValue_Elt(Elt *c)
{ GC_BIND;
  { list * l = c->lval;
    int  n = l->length;
    IntVar * indexVar = c->v1;
    IntVar * valueVar = c->v2;
    int  minFeasibleIndex = ((indexVar->inf->latestValue <= (1-c->cste)) ?
      (1-c->cste) :
      indexVar->inf->latestValue );
    int  maxFeasibleIndex = (((n-c->cste) <= indexVar->sup->latestValue) ?
      (n-c->cste) :
      indexVar->sup->latestValue );
    int  cause = ((valueVar->bucket != (NULL)) ?
      c->idx1 :
      0 );
    { OID gc_local;
      while ((((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(indexVar),
          minFeasibleIndex))) == CTRUE) && 
          (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
            (*(l))[(minFeasibleIndex+c->cste)])) == CTRUE)))
      { // HOHO, GC_LOOP not needed !
        ++minFeasibleIndex;
        } 
      } 
    (*choco.updateInf)(_oid_(indexVar),
      minFeasibleIndex,
      cause);
    { OID gc_local;
      while ((((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(indexVar),
          maxFeasibleIndex))) == CTRUE) && 
          (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
            (*(l))[(maxFeasibleIndex+c->cste)])) == CTRUE)))
      { // HOHO, GC_LOOP not needed !
        maxFeasibleIndex= (maxFeasibleIndex-1);
        } 
      } 
    (*choco.updateSup)(_oid_(indexVar),
      maxFeasibleIndex,
      cause);
    if (indexVar->bucket != (NULL))
     { int  i = (minFeasibleIndex+1);
      int  g0603 = (maxFeasibleIndex-1);
      { OID gc_local;
        while ((i <= g0603))
        { // HOHO, GC_LOOP not needed !
          if (((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(indexVar),
              i))) == CTRUE) && 
              (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                (*(l))[(i+c->cste)])) == CTRUE))
           (*choco.removeVal)(_oid_(indexVar),
            i,
            cause);
          ++i;
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// => removeVal(indexVar,i,0) -> updateValueFromIndex
//indeed removing the index implies that not only "not(valueVar canBeInstantiatedTo l[i]))" (what is not an additional info)
// but also valueVar >= new min of the list and valueVar <= new max of the list
// v0.9907: at initialization time, we propagate once for good the fact that:
//   - indices must be in the right range for accessing the integer table 
//   - all values in the domain of valueVar must correspond to a value in l 
/* The c++ function for: awake(c:Elt) [] */
void  choco_awake_Elt_choco(Elt *c)
{ GC_BIND;
  { list * l = GC_OBJECT(list,c->lval);
    int  n = c->lval->length;
    int  offset = c->cste;
    IntVar * indexVar = GC_OBJECT(IntVar,c->v1);
    IntVar * valueVar = GC_OBJECT(IntVar,c->v2);
    (*choco.updateInf)(_oid_(indexVar),
      (1-offset),
      c->idx1);
    (*choco.updateSup)(_oid_(indexVar),
      (n-offset),
      c->idx1);
    choco_propagate_Elt(c);
    } 
  GC_UNBIND;} 


/* The c++ function for: propagate(c:Elt) [] */
void  choco_propagate_Elt(Elt *c)
{ choco_updateIndexFromValue_Elt(c);
  choco_updateValueFromIndex_Elt(c);
  } 


/* The c++ function for: awakeOnInf(c:Elt,idx:integer) [] */
void  choco_awakeOnInf_Elt(Elt *c,int idx)
{ if (idx == 1)
   choco_updateValueFromIndex_Elt(c);
  else choco_updateIndexFromValue_Elt(c);
    } 


/* The c++ function for: awakeOnSup(c:Elt,idx:integer) [] */
void  choco_awakeOnSup_Elt(Elt *c,int idx)
{ if (idx == 1)
   choco_updateValueFromIndex_Elt(c);
  else choco_updateIndexFromValue_Elt(c);
    } 


/* The c++ function for: awakeOnInst(c:Elt,idx:integer) [] */
void  choco_awakeOnInst_Elt(Elt *c,int idx)
{ if (idx == 1) 
  { choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v2),(*(c->lval))[(c->v1->value+c->cste)],c->idx2);
     } 
  else{ GC_BIND;
    choco_updateIndexFromValue_Elt(c);
    GC_UNBIND;} 
  } 


// v0.33 <thb>: in case a value is no longer feasible, we need to upate the index.
// Note that we call updateIndexFrom Value rather than only removing the i such that l[i]=x
// from the domain of the index variable.
// This is due to the case of interval approximation for value Var (in this case,
// one single call to updateIndexFromValue reaches fix point saturation)
/* The c++ function for: awakeOnRem(c:Elt,idx:integer,x:integer) [] */
void  choco_awakeOnRem_Elt(Elt *c,int idx,int x)
{ if (idx == 1)
   choco_updateValueFromIndex_Elt(c);
  else choco_updateIndexFromValue_Elt(c);
    } 


//v0.93: askIfEntailed can be called before any awake (in bool combinations)
//       Therefore we cannot assume that all values of domain(indexVar) lead to "in scope" indexes for the list.
//       -> added tests (idx + c.cste) % (1 .. length(l))
/* The c++ function for: askIfEntailed(c:Elt) [] */
OID  choco_askIfEntailed_Elt(Elt *c)
{ GC_BIND;
  { OID Result = 0;
    { list * l = c->lval;
      IntVar * indexVar = c->v1;
      IntVar * valueVar = c->v2;
      { ClaireBoolean * g0608I;
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) valueVar))));
            if (v_and == CFALSE) g0608I =CFALSE; 
            else { { OID  g0609UU;
                { IntVar * g0604 = indexVar;
                  AbstractIntDomain * g0605 = g0604->bucket;
                  if (g0605 == (NULL))
                   { int  idx = g0604->inf->latestValue;
                    { OID gc_local;
                      g0609UU= _oid_(CFALSE);
                      while ((idx <= g0604->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if ((((idx+c->cste) > l->length) || 
                              (1 > (idx+c->cste))) || 
                            ((*(l))[(idx+c->cste)] != valueVar->value))
                         { g0609UU = Kernel.ctrue;
                          break;} 
                        idx= ((g0604->inf->latestValue <= (idx+1)) ?
                          (idx+1) :
                          g0604->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0605->isa,choco._LinkedListIntDomain))
                   { int  idx = g0604->inf->latestValue;
                    { OID gc_local;
                      g0609UU= _oid_(CFALSE);
                      while ((idx <= g0604->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if ((((idx+c->cste) > l->length) || 
                              (1 > (idx+c->cste))) || 
                            ((*(l))[(idx+c->cste)] != valueVar->value))
                         { g0609UU = Kernel.ctrue;
                          break;} 
                        idx= choco.getNextValue->fcall(((int) g0605),((int) idx));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(idx);
                      g0609UU= _oid_(CFALSE);
                      bag *idx_support;
                      idx_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0605)))));
                      for (START(idx_support); NEXT(idx);)
                      { GC_LOOP;
                        if ((((idx+c->cste) > l->length) || 
                              (1 > (idx+c->cste))) || 
                            ((*(l))[(idx+c->cste)] != valueVar->value))
                         { g0609UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0609UU);
                } 
              if (v_and == CFALSE) g0608I =CFALSE; 
              else g0608I = CTRUE;} 
            } 
          } 
        
        if (g0608I == CTRUE) Result = Kernel.ctrue;
          else { ClaireBoolean * g0610I;
          { OID  g0611UU;
            { IntVar * g0606 = indexVar;
              AbstractIntDomain * g0607 = g0606->bucket;
              if (g0607 == (NULL))
               { int  idx = g0606->inf->latestValue;
                { OID gc_local;
                  g0611UU= _oid_(CFALSE);
                  while ((idx <= g0606->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    if ((((idx+c->cste) <= l->length) && 
                          (1 <= (idx+c->cste))) && 
                        ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          (*(l))[(idx+c->cste)]))) == CTRUE))
                     { g0611UU = Kernel.ctrue;
                      break;} 
                    idx= ((g0606->inf->latestValue <= (idx+1)) ?
                      (idx+1) :
                      g0606->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0607->isa,choco._LinkedListIntDomain))
               { int  idx = g0606->inf->latestValue;
                { OID gc_local;
                  g0611UU= _oid_(CFALSE);
                  while ((idx <= g0606->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    if ((((idx+c->cste) <= l->length) && 
                          (1 <= (idx+c->cste))) && 
                        ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          (*(l))[(idx+c->cste)]))) == CTRUE))
                     { g0611UU = Kernel.ctrue;
                      break;} 
                    idx= choco.getNextValue->fcall(((int) g0607),((int) idx));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(idx);
                  g0611UU= _oid_(CFALSE);
                  bag *idx_support;
                  idx_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0607)))));
                  for (START(idx_support); NEXT(idx);)
                  { GC_LOOP;
                    if ((((idx+c->cste) <= l->length) && 
                          (1 <= (idx+c->cste))) && 
                        ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          (*(l))[(idx+c->cste)]))) == CTRUE))
                     { g0611UU = Kernel.ctrue;
                      break;} 
                    GC_UNLOOP;} 
                  } 
                } 
            g0610I = boolean_I_any(g0611UU);
            } 
          
          if (g0610I == CTRUE) Result = CNULL;
            else Result = Kernel.cfalse;
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:Elt) [] */
ClaireBoolean * choco_testIfSatisfied_Elt(Elt *c)
{ ;return (equal((*(c->lval))[(c->v1->value+c->cste)],c->v2->value));} 


// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// second version: accessing a bidimensional array with two variable indices
// This code could be further optimized if we were caching the min and max values
// still feasible per line and per column
/* The c++ function for: self_print(c:Elt2) [] */
void  claire_self_print_Elt2_choco(Elt2 *c)
{ GC_BIND;
  princ_string("ith-elt(");
  print_any(_oid_(c->tval));
  princ_string(",");
  print_any(GC_OID(_oid_(c->v1)));
  princ_string(",");
  print_any(GC_OID(_oid_(c->v2)));
  princ_string(" == ");
  print_any(GC_OID(_oid_(c->v3)));
  princ_string("");
  GC_UNBIND;} 


/* The c++ function for: makeElt2Constraint(l:table,n:integer,m:integer,i:IntVar,j:IntVar,x:IntVar) [] */
Elt2 * choco_makeElt2Constraint_table(table *l,int n,int m,IntVar *i,IntVar *j,IntVar *x)
{ GC_BIND;
  { Elt2 *Result ;
    { Elt2 * _CL_obj = ((Elt2 *) GC_OBJECT(Elt2,new_object_class(choco._Elt2)));
      (_CL_obj->v1 = i);
      (_CL_obj->v2 = j);
      (_CL_obj->v3 = x);
      (_CL_obj->cste = 0);
      (_CL_obj->tval = l);
      (_CL_obj->dim1 = n);
      (_CL_obj->dim2 = m);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 0.26 a few casts
/* The c++ function for: updateValueFromIndex(c:Elt2) [] */
void  choco_updateValueFromIndex_Elt2(Elt2 *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { table * l = c->tval;
    IntVar * index1Var = c->v1;
    IntVar * index2Var = c->v2;
    IntVar * valueVar = c->v3;
    int  minval = 99999999;
    int  maxval = -99999999;
    { IntVar * g0612 = index1Var;
      AbstractIntDomain * g0613 = g0612->bucket;
      if (g0613 == (NULL))
       { int  feasibleIndex1 = g0612->inf->latestValue;
        { OID gc_local;
          while ((feasibleIndex1 <= g0612->sup->latestValue))
          { GC_LOOP;
            { IntVar * g0614 = index2Var;
              AbstractIntDomain * g0615 = g0614->bucket;
              if (g0615 == (NULL))
               { int  feasibleIndex2 = g0614->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0614->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= ((g0614->inf->latestValue <= (feasibleIndex2+1)) ?
                      (feasibleIndex2+1) :
                      g0614->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0615->isa,choco._LinkedListIntDomain))
               { int  feasibleIndex2 = g0614->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0614->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= choco.getNextValue->fcall(((int) g0615),((int) feasibleIndex2));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(feasibleIndex2);
                  bag *feasibleIndex2_support;
                  feasibleIndex2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0615)))));
                  for (START(feasibleIndex2_support); NEXT(feasibleIndex2);)
                  { GC_LOOP;
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    GC_UNLOOP;} 
                  } 
                } 
            feasibleIndex1= ((g0612->inf->latestValue <= (feasibleIndex1+1)) ?
              (feasibleIndex1+1) :
              g0612->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0613->isa,choco._LinkedListIntDomain))
       { int  feasibleIndex1 = g0612->inf->latestValue;
        { OID gc_local;
          while ((feasibleIndex1 <= g0612->sup->latestValue))
          { GC_LOOP;
            { IntVar * g0616 = index2Var;
              AbstractIntDomain * g0617 = g0616->bucket;
              if (g0617 == (NULL))
               { int  feasibleIndex2 = g0616->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0616->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= ((g0616->inf->latestValue <= (feasibleIndex2+1)) ?
                      (feasibleIndex2+1) :
                      g0616->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0617->isa,choco._LinkedListIntDomain))
               { int  feasibleIndex2 = g0616->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0616->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= choco.getNextValue->fcall(((int) g0617),((int) feasibleIndex2));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(feasibleIndex2);
                  bag *feasibleIndex2_support;
                  feasibleIndex2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0617)))));
                  for (START(feasibleIndex2_support); NEXT(feasibleIndex2);)
                  { GC_LOOP;
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    GC_UNLOOP;} 
                  } 
                } 
            feasibleIndex1= choco.getNextValue->fcall(((int) g0613),((int) feasibleIndex1));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(feasibleIndex1);
          bag *feasibleIndex1_support;
          feasibleIndex1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0613)))));
          for (START(feasibleIndex1_support); NEXT(feasibleIndex1);)
          { GC_LOOP;
            { IntVar * g0618 = index2Var;
              AbstractIntDomain * g0619 = g0618->bucket;
              if (g0619 == (NULL))
               { int  feasibleIndex2 = g0618->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0618->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= ((g0618->inf->latestValue <= (feasibleIndex2+1)) ?
                      (feasibleIndex2+1) :
                      g0618->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0619->isa,choco._LinkedListIntDomain))
               { int  feasibleIndex2 = g0618->inf->latestValue;
                { OID gc_local;
                  while ((feasibleIndex2 <= g0618->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    feasibleIndex2= choco.getNextValue->fcall(((int) g0619),((int) feasibleIndex2));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(feasibleIndex2);
                  bag *feasibleIndex2_support;
                  feasibleIndex2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0619)))));
                  for (START(feasibleIndex2_support); NEXT(feasibleIndex2);)
                  { GC_LOOP;
                    { int  val = nth_table2(l,feasibleIndex1,feasibleIndex2);
                      minval= ((val <= minval) ?
                        val :
                        minval );
                      maxval= ((val <= maxval) ?
                        maxval :
                        val );
                      } 
                    GC_UNLOOP;} 
                  } 
                } 
            GC_UNLOOP;} 
          } 
        } 
    (*choco.updateInf)(_oid_(valueVar),
      minval,
      c->idx3);
    (*choco.updateSup)(_oid_(valueVar),
      maxval,
      c->idx3);
    } 
  GC_UNBIND;} 


// 0.9907: same fixes as in Elt <thb>
/* The c++ function for: updateIndexFromValue(c:Elt2) [] */
void  choco_updateIndexFromValue_Elt2(Elt2 *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { table * l = c->tval;
    int  n = c->dim1;
    int  m = c->dim2;
    IntVar * index1Var = c->v1;
    IntVar * index2Var = c->v2;
    IntVar * valueVar = c->v3;
    int  minFeasibleIndex1 = ((index1Var->inf->latestValue <= 1) ?
      1 :
      index1Var->inf->latestValue );
    int  maxFeasibleIndex1 = ((n <= index1Var->sup->latestValue) ?
      n :
      index1Var->sup->latestValue );
    int  minFeasibleIndex2 = ((index2Var->inf->latestValue <= 1) ?
      1 :
      index2Var->inf->latestValue );
    int  maxFeasibleIndex2 = ((m <= index2Var->sup->latestValue) ?
      m :
      index2Var->sup->latestValue );
    int  cause1 = ((valueVar->bucket != (NULL)) ?
      c->idx1 :
      0 );
    int  cause2 = ((valueVar->bucket != (NULL)) ?
      c->idx2 :
      0 );
    { OID gc_local;
      ClaireBoolean *v_while;
      { ClaireBoolean *v_and;
        { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index1Var),
            minFeasibleIndex1));
          if (v_and == CFALSE) v_while =CFALSE; 
          else { { OID  g0634UU;
              { IntVar * g0620 = index2Var;
                AbstractIntDomain * g0621 = g0620->bucket;
                if (g0621 == (NULL))
                 { int  i2 = g0620->inf->latestValue;
                  { OID gc_local;
                    g0634UU= _oid_(CFALSE);
                    while ((i2 <= g0620->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                       { g0634UU = Kernel.ctrue;
                        break;} 
                      i2= ((g0620->inf->latestValue <= (i2+1)) ?
                        (i2+1) :
                        g0620->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0621->isa,choco._LinkedListIntDomain))
                 { int  i2 = g0620->inf->latestValue;
                  { OID gc_local;
                    g0634UU= _oid_(CFALSE);
                    while ((i2 <= g0620->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                       { g0634UU = Kernel.ctrue;
                        break;} 
                      i2= choco.getNextValue->fcall(((int) g0621),((int) i2));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(i2);
                    g0634UU= _oid_(CFALSE);
                    bag *i2_support;
                    i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0621)))));
                    for (START(i2_support); NEXT(i2);)
                    { GC_LOOP;
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                       { g0634UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              v_and = not_any(g0634UU);
              } 
            if (v_and == CFALSE) v_while =CFALSE; 
            else v_while = CTRUE;} 
          } 
        } 
      
      while (v_while == CTRUE)
      { // HOHO, GC_LOOP not needed !
        ++minFeasibleIndex1;
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index1Var),
              minFeasibleIndex1));
            if (v_and == CFALSE) v_while =CFALSE; 
            else { { OID  g0635UU;
                { IntVar * g0620 = index2Var;
                  AbstractIntDomain * g0621 = g0620->bucket;
                  if (g0621 == (NULL))
                   { int  i2 = g0620->inf->latestValue;
                    { OID gc_local;
                      g0635UU= _oid_(CFALSE);
                      while ((i2 <= g0620->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                         { g0635UU = Kernel.ctrue;
                          break;} 
                        i2= ((g0620->inf->latestValue <= (i2+1)) ?
                          (i2+1) :
                          g0620->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0621->isa,choco._LinkedListIntDomain))
                   { int  i2 = g0620->inf->latestValue;
                    { OID gc_local;
                      g0635UU= _oid_(CFALSE);
                      while ((i2 <= g0620->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                         { g0635UU = Kernel.ctrue;
                          break;} 
                        i2= choco.getNextValue->fcall(((int) g0621),((int) i2));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(i2);
                      g0635UU= _oid_(CFALSE);
                      bag *i2_support;
                      i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0621)))));
                      for (START(i2_support); NEXT(i2);)
                      { GC_LOOP;
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,minFeasibleIndex1,i2))) != CTRUE)
                         { g0635UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0635UU);
                } 
              if (v_and == CFALSE) v_while =CFALSE; 
              else v_while = CTRUE;} 
            } 
          } 
        } 
      } 
    (*choco.updateInf)(_oid_(index1Var),
      minFeasibleIndex1,
      cause1);
    { OID gc_local;
      ClaireBoolean *v_while;
      { ClaireBoolean *v_and;
        { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index1Var),
            maxFeasibleIndex1));
          if (v_and == CFALSE) v_while =CFALSE; 
          else { { OID  g0636UU;
              { IntVar * g0622 = index2Var;
                AbstractIntDomain * g0623 = g0622->bucket;
                if (g0623 == (NULL))
                 { int  i2 = g0622->inf->latestValue;
                  { OID gc_local;
                    g0636UU= _oid_(CFALSE);
                    while ((i2 <= g0622->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                       { g0636UU = Kernel.ctrue;
                        break;} 
                      i2= ((g0622->inf->latestValue <= (i2+1)) ?
                        (i2+1) :
                        g0622->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0623->isa,choco._LinkedListIntDomain))
                 { int  i2 = g0622->inf->latestValue;
                  { OID gc_local;
                    g0636UU= _oid_(CFALSE);
                    while ((i2 <= g0622->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                       { g0636UU = Kernel.ctrue;
                        break;} 
                      i2= choco.getNextValue->fcall(((int) g0623),((int) i2));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(i2);
                    g0636UU= _oid_(CFALSE);
                    bag *i2_support;
                    i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0623)))));
                    for (START(i2_support); NEXT(i2);)
                    { GC_LOOP;
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                       { g0636UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              v_and = not_any(g0636UU);
              } 
            if (v_and == CFALSE) v_while =CFALSE; 
            else v_while = CTRUE;} 
          } 
        } 
      
      while (v_while == CTRUE)
      { // HOHO, GC_LOOP not needed !
        maxFeasibleIndex1= (maxFeasibleIndex1-1);
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index1Var),
              maxFeasibleIndex1));
            if (v_and == CFALSE) v_while =CFALSE; 
            else { { OID  g0637UU;
                { IntVar * g0622 = index2Var;
                  AbstractIntDomain * g0623 = g0622->bucket;
                  if (g0623 == (NULL))
                   { int  i2 = g0622->inf->latestValue;
                    { OID gc_local;
                      g0637UU= _oid_(CFALSE);
                      while ((i2 <= g0622->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                         { g0637UU = Kernel.ctrue;
                          break;} 
                        i2= ((g0622->inf->latestValue <= (i2+1)) ?
                          (i2+1) :
                          g0622->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0623->isa,choco._LinkedListIntDomain))
                   { int  i2 = g0622->inf->latestValue;
                    { OID gc_local;
                      g0637UU= _oid_(CFALSE);
                      while ((i2 <= g0622->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                         { g0637UU = Kernel.ctrue;
                          break;} 
                        i2= choco.getNextValue->fcall(((int) g0623),((int) i2));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(i2);
                      g0637UU= _oid_(CFALSE);
                      bag *i2_support;
                      i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0623)))));
                      for (START(i2_support); NEXT(i2);)
                      { GC_LOOP;
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,maxFeasibleIndex1,i2))) != CTRUE)
                         { g0637UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0637UU);
                } 
              if (v_and == CFALSE) v_while =CFALSE; 
              else v_while = CTRUE;} 
            } 
          } 
        } 
      } 
    (*choco.updateSup)(_oid_(index1Var),
      maxFeasibleIndex1,
      cause1);
    if (index1Var->bucket != (NULL))
     { int  i1 = (minFeasibleIndex1+1);
      int  g0624 = (maxFeasibleIndex1-1);
      { OID gc_local;
        while ((i1 <= g0624))
        { GC_LOOP;
          { ClaireBoolean * g0638I;
            { ClaireBoolean *v_and;
              { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index1Var),
                  i1));
                if (v_and == CFALSE) g0638I =CFALSE; 
                else { { OID  g0639UU;
                    { IntVar * g0625 = index2Var;
                      AbstractIntDomain * g0626 = g0625->bucket;
                      if (g0626 == (NULL))
                       { int  i2 = g0625->inf->latestValue;
                        { OID gc_local;
                          g0639UU= _oid_(CFALSE);
                          while ((i2 <= g0625->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0639UU = Kernel.ctrue;
                              break;} 
                            i2= ((g0625->inf->latestValue <= (i2+1)) ?
                              (i2+1) :
                              g0625->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0626->isa,choco._LinkedListIntDomain))
                       { int  i2 = g0625->inf->latestValue;
                        { OID gc_local;
                          g0639UU= _oid_(CFALSE);
                          while ((i2 <= g0625->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0639UU = Kernel.ctrue;
                              break;} 
                            i2= choco.getNextValue->fcall(((int) g0626),((int) i2));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i2);
                          g0639UU= _oid_(CFALSE);
                          bag *i2_support;
                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0626)))));
                          for (START(i2_support); NEXT(i2);)
                          { GC_LOOP;
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0639UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    v_and = not_any(g0639UU);
                    } 
                  if (v_and == CFALSE) g0638I =CFALSE; 
                  else g0638I = CTRUE;} 
                } 
              } 
            
            if (g0638I == CTRUE) (*choco.removeVal)(_oid_(index1Var),
                i1,
                cause1);
              } 
          ++i1;
          GC_UNLOOP;} 
        } 
      } 
    { OID gc_local;
      ClaireBoolean *v_while;
      { ClaireBoolean *v_and;
        { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index2Var),
            minFeasibleIndex2));
          if (v_and == CFALSE) v_while =CFALSE; 
          else { { OID  g0640UU;
              { IntVar * g0627 = index1Var;
                AbstractIntDomain * g0628 = g0627->bucket;
                if (g0628 == (NULL))
                 { int  i1 = g0627->inf->latestValue;
                  { OID gc_local;
                    g0640UU= _oid_(CFALSE);
                    while ((i1 <= g0627->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                       { g0640UU = Kernel.ctrue;
                        break;} 
                      i1= ((g0627->inf->latestValue <= (i1+1)) ?
                        (i1+1) :
                        g0627->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0628->isa,choco._LinkedListIntDomain))
                 { int  i1 = g0627->inf->latestValue;
                  { OID gc_local;
                    g0640UU= _oid_(CFALSE);
                    while ((i1 <= g0627->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                       { g0640UU = Kernel.ctrue;
                        break;} 
                      i1= choco.getNextValue->fcall(((int) g0628),((int) i1));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(i1);
                    g0640UU= _oid_(CFALSE);
                    bag *i1_support;
                    i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0628)))));
                    for (START(i1_support); NEXT(i1);)
                    { GC_LOOP;
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                       { g0640UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              v_and = not_any(g0640UU);
              } 
            if (v_and == CFALSE) v_while =CFALSE; 
            else v_while = CTRUE;} 
          } 
        } 
      
      while (v_while == CTRUE)
      { // HOHO, GC_LOOP not needed !
        ++minFeasibleIndex2;
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index2Var),
              minFeasibleIndex2));
            if (v_and == CFALSE) v_while =CFALSE; 
            else { { OID  g0641UU;
                { IntVar * g0627 = index1Var;
                  AbstractIntDomain * g0628 = g0627->bucket;
                  if (g0628 == (NULL))
                   { int  i1 = g0627->inf->latestValue;
                    { OID gc_local;
                      g0641UU= _oid_(CFALSE);
                      while ((i1 <= g0627->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                         { g0641UU = Kernel.ctrue;
                          break;} 
                        i1= ((g0627->inf->latestValue <= (i1+1)) ?
                          (i1+1) :
                          g0627->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0628->isa,choco._LinkedListIntDomain))
                   { int  i1 = g0627->inf->latestValue;
                    { OID gc_local;
                      g0641UU= _oid_(CFALSE);
                      while ((i1 <= g0627->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                         { g0641UU = Kernel.ctrue;
                          break;} 
                        i1= choco.getNextValue->fcall(((int) g0628),((int) i1));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(i1);
                      g0641UU= _oid_(CFALSE);
                      bag *i1_support;
                      i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0628)))));
                      for (START(i1_support); NEXT(i1);)
                      { GC_LOOP;
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,minFeasibleIndex2))) != CTRUE)
                         { g0641UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0641UU);
                } 
              if (v_and == CFALSE) v_while =CFALSE; 
              else v_while = CTRUE;} 
            } 
          } 
        } 
      } 
    (*choco.updateInf)(_oid_(index2Var),
      minFeasibleIndex2,
      cause2);
    { OID gc_local;
      ClaireBoolean *v_while;
      { ClaireBoolean *v_and;
        { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index2Var),
            maxFeasibleIndex2));
          if (v_and == CFALSE) v_while =CFALSE; 
          else { { OID  g0642UU;
              { IntVar * g0629 = index1Var;
                AbstractIntDomain * g0630 = g0629->bucket;
                if (g0630 == (NULL))
                 { int  i1 = g0629->inf->latestValue;
                  { OID gc_local;
                    g0642UU= _oid_(CFALSE);
                    while ((i1 <= g0629->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                       { g0642UU = Kernel.ctrue;
                        break;} 
                      i1= ((g0629->inf->latestValue <= (i1+1)) ?
                        (i1+1) :
                        g0629->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0630->isa,choco._LinkedListIntDomain))
                 { int  i1 = g0629->inf->latestValue;
                  { OID gc_local;
                    g0642UU= _oid_(CFALSE);
                    while ((i1 <= g0629->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                       { g0642UU = Kernel.ctrue;
                        break;} 
                      i1= choco.getNextValue->fcall(((int) g0630),((int) i1));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(i1);
                    g0642UU= _oid_(CFALSE);
                    bag *i1_support;
                    i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0630)))));
                    for (START(i1_support); NEXT(i1);)
                    { GC_LOOP;
                      if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                        nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                       { g0642UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              v_and = not_any(g0642UU);
              } 
            if (v_and == CFALSE) v_while =CFALSE; 
            else v_while = CTRUE;} 
          } 
        } 
      
      while (v_while == CTRUE)
      { // HOHO, GC_LOOP not needed !
        maxFeasibleIndex2= (maxFeasibleIndex2-1);
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index2Var),
              maxFeasibleIndex2));
            if (v_and == CFALSE) v_while =CFALSE; 
            else { { OID  g0643UU;
                { IntVar * g0629 = index1Var;
                  AbstractIntDomain * g0630 = g0629->bucket;
                  if (g0630 == (NULL))
                   { int  i1 = g0629->inf->latestValue;
                    { OID gc_local;
                      g0643UU= _oid_(CFALSE);
                      while ((i1 <= g0629->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                         { g0643UU = Kernel.ctrue;
                          break;} 
                        i1= ((g0629->inf->latestValue <= (i1+1)) ?
                          (i1+1) :
                          g0629->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0630->isa,choco._LinkedListIntDomain))
                   { int  i1 = g0629->inf->latestValue;
                    { OID gc_local;
                      g0643UU= _oid_(CFALSE);
                      while ((i1 <= g0629->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                         { g0643UU = Kernel.ctrue;
                          break;} 
                        i1= choco.getNextValue->fcall(((int) g0630),((int) i1));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(i1);
                      g0643UU= _oid_(CFALSE);
                      bag *i1_support;
                      i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0630)))));
                      for (START(i1_support); NEXT(i1);)
                      { GC_LOOP;
                        if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                          nth_table2(l,i1,maxFeasibleIndex2))) != CTRUE)
                         { g0643UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0643UU);
                } 
              if (v_and == CFALSE) v_while =CFALSE; 
              else v_while = CTRUE;} 
            } 
          } 
        } 
      } 
    (*choco.updateSup)(_oid_(index2Var),
      maxFeasibleIndex2,
      cause2);
    if (index2Var->bucket != (NULL))
     { int  i2 = (minFeasibleIndex2+1);
      int  g0631 = (maxFeasibleIndex2-1);
      { OID gc_local;
        while ((i2 <= g0631))
        { GC_LOOP;
          { ClaireBoolean * g0644I;
            { ClaireBoolean *v_and;
              { v_and = OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(index2Var),
                  i2));
                if (v_and == CFALSE) g0644I =CFALSE; 
                else { { OID  g0645UU;
                    { IntVar * g0632 = index1Var;
                      AbstractIntDomain * g0633 = g0632->bucket;
                      if (g0633 == (NULL))
                       { int  i1 = g0632->inf->latestValue;
                        { OID gc_local;
                          g0645UU= _oid_(CFALSE);
                          while ((i1 <= g0632->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0645UU = Kernel.ctrue;
                              break;} 
                            i1= ((g0632->inf->latestValue <= (i1+1)) ?
                              (i1+1) :
                              g0632->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0633->isa,choco._LinkedListIntDomain))
                       { int  i1 = g0632->inf->latestValue;
                        { OID gc_local;
                          g0645UU= _oid_(CFALSE);
                          while ((i1 <= g0632->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0645UU = Kernel.ctrue;
                              break;} 
                            i1= choco.getNextValue->fcall(((int) g0633),((int) i1));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i1);
                          g0645UU= _oid_(CFALSE);
                          bag *i1_support;
                          i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0633)))));
                          for (START(i1_support); NEXT(i1);)
                          { GC_LOOP;
                            if (not_any((*choco.canBeInstantiatedTo)(_oid_(valueVar),
                              nth_table2(l,i1,i2))) != CTRUE)
                             { g0645UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    v_and = not_any(g0645UU);
                    } 
                  if (v_and == CFALSE) g0644I =CFALSE; 
                  else g0644I = CTRUE;} 
                } 
              } 
            
            if (g0644I == CTRUE) (*choco.removeVal)(_oid_(index2Var),
                i2,
                cause2);
              } 
          ++i2;
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


// v0.9907: at initialization time, we propagate once for good the fact that 
//   - indices must be in the right range for accessing the integer table 
//   - all values in the domain of valueVar must correspond to a value in l        
/* The c++ function for: awake(c:Elt2) [] */
void  choco_awake_Elt2_choco(Elt2 *c)
{ GC_RESERVE(16);  // v3.0.55 optim !
  { table * l = c->tval;
    IntVar * index1Var = c->v1;
    IntVar * index2Var = c->v2;
    IntVar * valueVar = c->v3;
    (*choco.updateInf)(_oid_(index1Var),
      1,
      c->idx1);
    (*choco.updateSup)(_oid_(index1Var),
      c->dim1,
      c->idx1);
    (*choco.updateInf)(_oid_(index2Var),
      1,
      c->idx2);
    (*choco.updateSup)(_oid_(index2Var),
      c->dim2,
      c->idx2);
    if (valueVar->bucket != (NULL))
     { IntVar * g0646 = valueVar;
      AbstractIntDomain * g0647 = g0646->bucket;
      if (g0647 == (NULL))
       { int  v = g0646->inf->latestValue;
        { OID gc_local;
          while ((v <= g0646->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0672I;
              { OID  g0673UU;
                { ClaireBoolean * V_CL0674;{ OID  g0675UU;
                    { IntVar * g0648 = index1Var;
                      AbstractIntDomain * g0649 = g0648->bucket;
                      if (g0649 == (NULL))
                       { int  i1 = g0648->inf->latestValue;
                        { OID gc_local;
                          g0675UU= _oid_(CFALSE);
                          while ((i1 <= g0648->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0676I;
                              { OID  g0677UU;
                                { ClaireBoolean * V_CL0678;{ OID  g0679UU;
                                    { IntVar * g0650 = index2Var;
                                      AbstractIntDomain * g0651 = g0650->bucket;
                                      if (g0651 == (NULL))
                                       { int  i2 = g0650->inf->latestValue;
                                        { OID gc_local;
                                          g0679UU= _oid_(CFALSE);
                                          while ((i2 <= g0650->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0679UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0650->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0650->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0651->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0650->inf->latestValue;
                                        { OID gc_local;
                                          g0679UU= _oid_(CFALSE);
                                          while ((i2 <= g0650->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0679UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0651),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0679UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0651)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0679UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0678 = boolean_I_any(g0679UU);
                                    } 
                                  
                                  g0677UU=_oid_(V_CL0678);} 
                                g0676I = not_any(g0677UU);
                                } 
                              
                              if (g0676I == CTRUE) { g0675UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= ((g0648->inf->latestValue <= (i1+1)) ?
                              (i1+1) :
                              g0648->inf->latestValue );
                            GC_UNLOOP;} 
                          } 
                        } 
                      else if (INHERIT(g0649->isa,choco._LinkedListIntDomain))
                       { int  i1 = g0648->inf->latestValue;
                        { OID gc_local;
                          g0675UU= _oid_(CFALSE);
                          while ((i1 <= g0648->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0680I;
                              { OID  g0681UU;
                                { ClaireBoolean * V_CL0682;{ OID  g0683UU;
                                    { IntVar * g0652 = index2Var;
                                      AbstractIntDomain * g0653 = g0652->bucket;
                                      if (g0653 == (NULL))
                                       { int  i2 = g0652->inf->latestValue;
                                        { OID gc_local;
                                          g0683UU= _oid_(CFALSE);
                                          while ((i2 <= g0652->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0683UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0652->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0652->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0653->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0652->inf->latestValue;
                                        { OID gc_local;
                                          g0683UU= _oid_(CFALSE);
                                          while ((i2 <= g0652->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0683UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0653),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0683UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0653)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0683UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0682 = boolean_I_any(g0683UU);
                                    } 
                                  
                                  g0681UU=_oid_(V_CL0682);} 
                                g0680I = not_any(g0681UU);
                                } 
                              
                              if (g0680I == CTRUE) { g0675UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= choco.getNextValue->fcall(((int) g0649),((int) i1));
                            GC_UNLOOP;} 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i1);
                          g0675UU= _oid_(CFALSE);
                          bag *i1_support;
                          i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0649)))));
                          for (START(i1_support); NEXT(i1);)
                          { GC_LOOP;
                            { ClaireBoolean * g0684I;
                              { OID  g0685UU;
                                { ClaireBoolean * V_CL0686;{ OID  g0687UU;
                                    { IntVar * g0654 = index2Var;
                                      AbstractIntDomain * g0655 = g0654->bucket;
                                      if (g0655 == (NULL))
                                       { int  i2 = g0654->inf->latestValue;
                                        { OID gc_local;
                                          g0687UU= _oid_(CFALSE);
                                          while ((i2 <= g0654->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0687UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0654->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0654->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0655->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0654->inf->latestValue;
                                        { OID gc_local;
                                          g0687UU= _oid_(CFALSE);
                                          while ((i2 <= g0654->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0687UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0655),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0687UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0655)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0687UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0686 = boolean_I_any(g0687UU);
                                    } 
                                  
                                  g0685UU=_oid_(V_CL0686);} 
                                g0684I = not_any(g0685UU);
                                } 
                              
                              if (g0684I == CTRUE) { g0675UU = Kernel.ctrue;
                                  break;} 
                                } 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0674 = boolean_I_any(g0675UU);
                    } 
                  
                  g0673UU=_oid_(V_CL0674);} 
                g0672I = not_any(g0673UU);
                } 
              
              if (g0672I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx3);
                } 
            v= ((g0646->inf->latestValue <= (v+1)) ?
              (v+1) :
              g0646->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0647->isa,choco._LinkedListIntDomain))
       { int  v = g0646->inf->latestValue;
        { OID gc_local;
          while ((v <= g0646->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0688I;
              { OID  g0689UU;
                { ClaireBoolean * V_CL0690;{ OID  g0691UU;
                    { IntVar * g0656 = index1Var;
                      AbstractIntDomain * g0657 = g0656->bucket;
                      if (g0657 == (NULL))
                       { int  i1 = g0656->inf->latestValue;
                        { OID gc_local;
                          g0691UU= _oid_(CFALSE);
                          while ((i1 <= g0656->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0692I;
                              { OID  g0693UU;
                                { ClaireBoolean * V_CL0694;{ OID  g0695UU;
                                    { IntVar * g0658 = index2Var;
                                      AbstractIntDomain * g0659 = g0658->bucket;
                                      if (g0659 == (NULL))
                                       { int  i2 = g0658->inf->latestValue;
                                        { OID gc_local;
                                          g0695UU= _oid_(CFALSE);
                                          while ((i2 <= g0658->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0695UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0658->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0658->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0659->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0658->inf->latestValue;
                                        { OID gc_local;
                                          g0695UU= _oid_(CFALSE);
                                          while ((i2 <= g0658->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0695UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0659),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0695UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0659)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0695UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0694 = boolean_I_any(g0695UU);
                                    } 
                                  
                                  g0693UU=_oid_(V_CL0694);} 
                                g0692I = not_any(g0693UU);
                                } 
                              
                              if (g0692I == CTRUE) { g0691UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= ((g0656->inf->latestValue <= (i1+1)) ?
                              (i1+1) :
                              g0656->inf->latestValue );
                            GC_UNLOOP;} 
                          } 
                        } 
                      else if (INHERIT(g0657->isa,choco._LinkedListIntDomain))
                       { int  i1 = g0656->inf->latestValue;
                        { OID gc_local;
                          g0691UU= _oid_(CFALSE);
                          while ((i1 <= g0656->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0696I;
                              { OID  g0697UU;
                                { ClaireBoolean * V_CL0698;{ OID  g0699UU;
                                    { IntVar * g0660 = index2Var;
                                      AbstractIntDomain * g0661 = g0660->bucket;
                                      if (g0661 == (NULL))
                                       { int  i2 = g0660->inf->latestValue;
                                        { OID gc_local;
                                          g0699UU= _oid_(CFALSE);
                                          while ((i2 <= g0660->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0699UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0660->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0660->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0661->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0660->inf->latestValue;
                                        { OID gc_local;
                                          g0699UU= _oid_(CFALSE);
                                          while ((i2 <= g0660->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0699UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0661),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0699UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0661)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0699UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0698 = boolean_I_any(g0699UU);
                                    } 
                                  
                                  g0697UU=_oid_(V_CL0698);} 
                                g0696I = not_any(g0697UU);
                                } 
                              
                              if (g0696I == CTRUE) { g0691UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= choco.getNextValue->fcall(((int) g0657),((int) i1));
                            GC_UNLOOP;} 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i1);
                          g0691UU= _oid_(CFALSE);
                          bag *i1_support;
                          i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0657)))));
                          for (START(i1_support); NEXT(i1);)
                          { GC_LOOP;
                            { ClaireBoolean * g0700I;
                              { OID  g0701UU;
                                { ClaireBoolean * V_CL0702;{ OID  g0703UU;
                                    { IntVar * g0662 = index2Var;
                                      AbstractIntDomain * g0663 = g0662->bucket;
                                      if (g0663 == (NULL))
                                       { int  i2 = g0662->inf->latestValue;
                                        { OID gc_local;
                                          g0703UU= _oid_(CFALSE);
                                          while ((i2 <= g0662->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0703UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0662->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0662->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0663->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0662->inf->latestValue;
                                        { OID gc_local;
                                          g0703UU= _oid_(CFALSE);
                                          while ((i2 <= g0662->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0703UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0663),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0703UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0663)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0703UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0702 = boolean_I_any(g0703UU);
                                    } 
                                  
                                  g0701UU=_oid_(V_CL0702);} 
                                g0700I = not_any(g0701UU);
                                } 
                              
                              if (g0700I == CTRUE) { g0691UU = Kernel.ctrue;
                                  break;} 
                                } 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0690 = boolean_I_any(g0691UU);
                    } 
                  
                  g0689UU=_oid_(V_CL0690);} 
                g0688I = not_any(g0689UU);
                } 
              
              if (g0688I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx3);
                } 
            v= choco.getNextValue->fcall(((int) g0647),((int) v));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(v);
          bag *v_support;
          v_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0647)))));
          for (START(v_support); NEXT(v);)
          { GC_LOOP;
            { ClaireBoolean * g0704I;
              { OID  g0705UU;
                { ClaireBoolean * V_CL0706;{ OID  g0707UU;
                    { IntVar * g0664 = index1Var;
                      AbstractIntDomain * g0665 = g0664->bucket;
                      if (g0665 == (NULL))
                       { int  i1 = g0664->inf->latestValue;
                        { OID gc_local;
                          g0707UU= _oid_(CFALSE);
                          while ((i1 <= g0664->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0708I;
                              { OID  g0709UU;
                                { ClaireBoolean * V_CL0710;{ OID  g0711UU;
                                    { IntVar * g0666 = index2Var;
                                      AbstractIntDomain * g0667 = g0666->bucket;
                                      if (g0667 == (NULL))
                                       { int  i2 = g0666->inf->latestValue;
                                        { OID gc_local;
                                          g0711UU= _oid_(CFALSE);
                                          while ((i2 <= g0666->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0711UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0666->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0666->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0667->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0666->inf->latestValue;
                                        { OID gc_local;
                                          g0711UU= _oid_(CFALSE);
                                          while ((i2 <= g0666->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0711UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0667),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0711UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0667)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0711UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0710 = boolean_I_any(g0711UU);
                                    } 
                                  
                                  g0709UU=_oid_(V_CL0710);} 
                                g0708I = not_any(g0709UU);
                                } 
                              
                              if (g0708I == CTRUE) { g0707UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= ((g0664->inf->latestValue <= (i1+1)) ?
                              (i1+1) :
                              g0664->inf->latestValue );
                            GC_UNLOOP;} 
                          } 
                        } 
                      else if (INHERIT(g0665->isa,choco._LinkedListIntDomain))
                       { int  i1 = g0664->inf->latestValue;
                        { OID gc_local;
                          g0707UU= _oid_(CFALSE);
                          while ((i1 <= g0664->sup->latestValue))
                          { GC_LOOP;
                            { ClaireBoolean * g0712I;
                              { OID  g0713UU;
                                { ClaireBoolean * V_CL0714;{ OID  g0715UU;
                                    { IntVar * g0668 = index2Var;
                                      AbstractIntDomain * g0669 = g0668->bucket;
                                      if (g0669 == (NULL))
                                       { int  i2 = g0668->inf->latestValue;
                                        { OID gc_local;
                                          g0715UU= _oid_(CFALSE);
                                          while ((i2 <= g0668->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0715UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0668->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0668->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0669->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0668->inf->latestValue;
                                        { OID gc_local;
                                          g0715UU= _oid_(CFALSE);
                                          while ((i2 <= g0668->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0715UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0669),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0715UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0669)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0715UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0714 = boolean_I_any(g0715UU);
                                    } 
                                  
                                  g0713UU=_oid_(V_CL0714);} 
                                g0712I = not_any(g0713UU);
                                } 
                              
                              if (g0712I == CTRUE) { g0707UU = Kernel.ctrue;
                                  break;} 
                                } 
                            i1= choco.getNextValue->fcall(((int) g0665),((int) i1));
                            GC_UNLOOP;} 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(i1);
                          g0707UU= _oid_(CFALSE);
                          bag *i1_support;
                          i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0665)))));
                          for (START(i1_support); NEXT(i1);)
                          { GC_LOOP;
                            { ClaireBoolean * g0716I;
                              { OID  g0717UU;
                                { ClaireBoolean * V_CL0718;{ OID  g0719UU;
                                    { IntVar * g0670 = index2Var;
                                      AbstractIntDomain * g0671 = g0670->bucket;
                                      if (g0671 == (NULL))
                                       { int  i2 = g0670->inf->latestValue;
                                        { OID gc_local;
                                          g0719UU= _oid_(CFALSE);
                                          while ((i2 <= g0670->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0719UU = Kernel.ctrue;
                                              break;} 
                                            i2= ((g0670->inf->latestValue <= (i2+1)) ?
                                              (i2+1) :
                                              g0670->inf->latestValue );
                                            } 
                                          } 
                                        } 
                                      else if (INHERIT(g0671->isa,choco._LinkedListIntDomain))
                                       { int  i2 = g0670->inf->latestValue;
                                        { OID gc_local;
                                          g0719UU= _oid_(CFALSE);
                                          while ((i2 <= g0670->sup->latestValue))
                                          { // HOHO, GC_LOOP not needed !
                                            if (v == nth_table2(l,i1,i2))
                                             { g0719UU = Kernel.ctrue;
                                              break;} 
                                            i2= choco.getNextValue->fcall(((int) g0671),((int) i2));
                                            } 
                                          } 
                                        } 
                                      else { OID gc_local;
                                          ITERATE(i2);
                                          g0719UU= _oid_(CFALSE);
                                          bag *i2_support;
                                          i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0671)))));
                                          for (START(i2_support); NEXT(i2);)
                                          { GC_LOOP;
                                            if (v == nth_table2(l,i1,i2))
                                             { g0719UU = Kernel.ctrue;
                                              break;} 
                                            GC_UNLOOP;} 
                                          } 
                                        } 
                                    V_CL0718 = boolean_I_any(g0719UU);
                                    } 
                                  
                                  g0717UU=_oid_(V_CL0718);} 
                                g0716I = not_any(g0717UU);
                                } 
                              
                              if (g0716I == CTRUE) { g0707UU = Kernel.ctrue;
                                  break;} 
                                } 
                            GC_UNLOOP;} 
                          } 
                        } 
                    V_CL0706 = boolean_I_any(g0707UU);
                    } 
                  
                  g0705UU=_oid_(V_CL0706);} 
                g0704I = not_any(g0705UU);
                } 
              
              if (g0704I == CTRUE) (*choco.removeVal)(_oid_(valueVar),
                  v,
                  c->idx3);
                } 
            GC_UNLOOP;} 
          } 
        } 
    } 
  GC_UNBIND;} 


/* The c++ function for: propagate(c:Elt2) [] */
void  choco_propagate_Elt2(Elt2 *c)
{ choco_updateIndexFromValue_Elt2(c);
  choco_updateValueFromIndex_Elt2(c);
  } 


/* The c++ function for: awakeOnInf(c:Elt2,idx:integer) [] */
void  choco_awakeOnInf_Elt2(Elt2 *c,int idx)
{ if (idx <= 2)
   choco_updateValueFromIndex_Elt2(c);
  else choco_updateIndexFromValue_Elt2(c);
    } 


/* The c++ function for: awakeOnSup(c:Elt2,idx:integer) [] */
void  choco_awakeOnSup_Elt2(Elt2 *c,int idx)
{ if (idx <= 2)
   choco_updateValueFromIndex_Elt2(c);
  else choco_updateIndexFromValue_Elt2(c);
    } 


/* The c++ function for: awakeOnInst(c:Elt2,idx:integer) [] */
void  choco_awakeOnInst_Elt2(Elt2 *c,int idx)
{ if (idx <= 2)
   choco_updateValueFromIndex_Elt2(c);
  else choco_updateIndexFromValue_Elt2(c);
    } 


/* The c++ function for: awakeOnRem(c:Elt2,idx:integer,x:integer) [] */
void  choco_awakeOnRem_Elt2(Elt2 *c,int idx,int x)
{ if (idx <= 2) 
  { choco_updateValueFromIndex_Elt2(c);
     } 
  else{ GC_BIND;
    { table * l = c->tval;
      int  n = c->dim1;
      int  m = c->dim2;
      IntVar * index1Var = GC_OBJECT(IntVar,c->v1);
      IntVar * index2Var = GC_OBJECT(IntVar,c->v2);
      IntVar * valueVar = GC_OBJECT(IntVar,c->v3);
      if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) index1Var))))) == CTRUE)
       { int  i0 = index1Var->value;
        int  j = 1;
        int  g0720 = m;
        { OID gc_local;
          while ((j <= g0720))
          { // HOHO, GC_LOOP not needed !
            if (nth_table2(l,i0,j) == x)
             (*choco.removeVal)(_oid_(index2Var),
              j,
              c->idx2);
            ++j;
            } 
          } 
        } 
      else if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) index2Var))))) == CTRUE)
       { int  j0 = index2Var->value;
        int  i = 1;
        int  g0721 = n;
        { OID gc_local;
          while ((i <= g0721))
          { // HOHO, GC_LOOP not needed !
            if (nth_table2(l,i,j0) == x)
             (*choco.removeVal)(_oid_(index1Var),
              i,
              c->idx1);
            ++i;
            } 
          } 
        } 
      } 
    GC_UNBIND;} 
  } 


// v0.9907 <thb> missing methods
/* The c++ function for: askIfEntailed(c:Elt2) [] */
OID  choco_askIfEntailed_Elt2(Elt2 *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { OID Result = 0;
    { table * l = c->tval;
      IntVar * index1Var = c->v1;
      IntVar * index2Var = c->v2;
      IntVar * valueVar = c->v3;
      { ClaireBoolean * g0738I;
        { ClaireBoolean *v_and;
          { v_and = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) valueVar))));
            if (v_and == CFALSE) g0738I =CFALSE; 
            else { { OID  g0739UU;
                { IntVar * g0722 = index1Var;
                  AbstractIntDomain * g0723 = g0722->bucket;
                  if (g0723 == (NULL))
                   { int  i1 = g0722->inf->latestValue;
                    { OID gc_local;
                      g0739UU= _oid_(CFALSE);
                      while ((i1 <= g0722->sup->latestValue))
                      { GC_LOOP;
                        { ClaireBoolean * g0740I;
                          { OID  g0741UU;
                            { ClaireBoolean * V_CL0742;{ OID  g0743UU;
                                { IntVar * g0724 = index2Var;
                                  AbstractIntDomain * g0725 = g0724->bucket;
                                  if (g0725 == (NULL))
                                   { int  i2 = g0724->inf->latestValue;
                                    { OID gc_local;
                                      g0743UU= _oid_(CFALSE);
                                      while ((i2 <= g0724->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0743UU = Kernel.ctrue;
                                          break;} 
                                        i2= ((g0724->inf->latestValue <= (i2+1)) ?
                                          (i2+1) :
                                          g0724->inf->latestValue );
                                        } 
                                      } 
                                    } 
                                  else if (INHERIT(g0725->isa,choco._LinkedListIntDomain))
                                   { int  i2 = g0724->inf->latestValue;
                                    { OID gc_local;
                                      g0743UU= _oid_(CFALSE);
                                      while ((i2 <= g0724->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0743UU = Kernel.ctrue;
                                          break;} 
                                        i2= choco.getNextValue->fcall(((int) g0725),((int) i2));
                                        } 
                                      } 
                                    } 
                                  else { OID gc_local;
                                      ITERATE(i2);
                                      g0743UU= _oid_(CFALSE);
                                      bag *i2_support;
                                      i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0725)))));
                                      for (START(i2_support); NEXT(i2);)
                                      { GC_LOOP;
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0743UU = Kernel.ctrue;
                                          break;} 
                                        GC_UNLOOP;} 
                                      } 
                                    } 
                                V_CL0742 = not_any(g0743UU);
                                } 
                              
                              g0741UU=_oid_(V_CL0742);} 
                            g0740I = not_any(g0741UU);
                            } 
                          
                          if (g0740I == CTRUE) { g0739UU = Kernel.ctrue;
                              break;} 
                            } 
                        i1= ((g0722->inf->latestValue <= (i1+1)) ?
                          (i1+1) :
                          g0722->inf->latestValue );
                        GC_UNLOOP;} 
                      } 
                    } 
                  else if (INHERIT(g0723->isa,choco._LinkedListIntDomain))
                   { int  i1 = g0722->inf->latestValue;
                    { OID gc_local;
                      g0739UU= _oid_(CFALSE);
                      while ((i1 <= g0722->sup->latestValue))
                      { GC_LOOP;
                        { ClaireBoolean * g0744I;
                          { OID  g0745UU;
                            { ClaireBoolean * V_CL0746;{ OID  g0747UU;
                                { IntVar * g0726 = index2Var;
                                  AbstractIntDomain * g0727 = g0726->bucket;
                                  if (g0727 == (NULL))
                                   { int  i2 = g0726->inf->latestValue;
                                    { OID gc_local;
                                      g0747UU= _oid_(CFALSE);
                                      while ((i2 <= g0726->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0747UU = Kernel.ctrue;
                                          break;} 
                                        i2= ((g0726->inf->latestValue <= (i2+1)) ?
                                          (i2+1) :
                                          g0726->inf->latestValue );
                                        } 
                                      } 
                                    } 
                                  else if (INHERIT(g0727->isa,choco._LinkedListIntDomain))
                                   { int  i2 = g0726->inf->latestValue;
                                    { OID gc_local;
                                      g0747UU= _oid_(CFALSE);
                                      while ((i2 <= g0726->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0747UU = Kernel.ctrue;
                                          break;} 
                                        i2= choco.getNextValue->fcall(((int) g0727),((int) i2));
                                        } 
                                      } 
                                    } 
                                  else { OID gc_local;
                                      ITERATE(i2);
                                      g0747UU= _oid_(CFALSE);
                                      bag *i2_support;
                                      i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0727)))));
                                      for (START(i2_support); NEXT(i2);)
                                      { GC_LOOP;
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0747UU = Kernel.ctrue;
                                          break;} 
                                        GC_UNLOOP;} 
                                      } 
                                    } 
                                V_CL0746 = not_any(g0747UU);
                                } 
                              
                              g0745UU=_oid_(V_CL0746);} 
                            g0744I = not_any(g0745UU);
                            } 
                          
                          if (g0744I == CTRUE) { g0739UU = Kernel.ctrue;
                              break;} 
                            } 
                        i1= choco.getNextValue->fcall(((int) g0723),((int) i1));
                        GC_UNLOOP;} 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(i1);
                      g0739UU= _oid_(CFALSE);
                      bag *i1_support;
                      i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0723)))));
                      for (START(i1_support); NEXT(i1);)
                      { GC_LOOP;
                        { ClaireBoolean * g0748I;
                          { OID  g0749UU;
                            { ClaireBoolean * V_CL0750;{ OID  g0751UU;
                                { IntVar * g0728 = index2Var;
                                  AbstractIntDomain * g0729 = g0728->bucket;
                                  if (g0729 == (NULL))
                                   { int  i2 = g0728->inf->latestValue;
                                    { OID gc_local;
                                      g0751UU= _oid_(CFALSE);
                                      while ((i2 <= g0728->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0751UU = Kernel.ctrue;
                                          break;} 
                                        i2= ((g0728->inf->latestValue <= (i2+1)) ?
                                          (i2+1) :
                                          g0728->inf->latestValue );
                                        } 
                                      } 
                                    } 
                                  else if (INHERIT(g0729->isa,choco._LinkedListIntDomain))
                                   { int  i2 = g0728->inf->latestValue;
                                    { OID gc_local;
                                      g0751UU= _oid_(CFALSE);
                                      while ((i2 <= g0728->sup->latestValue))
                                      { // HOHO, GC_LOOP not needed !
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0751UU = Kernel.ctrue;
                                          break;} 
                                        i2= choco.getNextValue->fcall(((int) g0729),((int) i2));
                                        } 
                                      } 
                                    } 
                                  else { OID gc_local;
                                      ITERATE(i2);
                                      g0751UU= _oid_(CFALSE);
                                      bag *i2_support;
                                      i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0729)))));
                                      for (START(i2_support); NEXT(i2);)
                                      { GC_LOOP;
                                        if (nth_table2(l,i1,i2) != valueVar->value)
                                         { g0751UU = Kernel.ctrue;
                                          break;} 
                                        GC_UNLOOP;} 
                                      } 
                                    } 
                                V_CL0750 = not_any(g0751UU);
                                } 
                              
                              g0749UU=_oid_(V_CL0750);} 
                            g0748I = not_any(g0749UU);
                            } 
                          
                          if (g0748I == CTRUE) { g0739UU = Kernel.ctrue;
                              break;} 
                            } 
                        GC_UNLOOP;} 
                      } 
                    } 
                v_and = not_any(g0739UU);
                } 
              if (v_and == CFALSE) g0738I =CFALSE; 
              else g0738I = CTRUE;} 
            } 
          } 
        
        if (g0738I == CTRUE) Result = Kernel.ctrue;
          else { ClaireBoolean * g0752I;
          { OID  g0753UU;
            { IntVar * g0730 = index1Var;
              AbstractIntDomain * g0731 = g0730->bucket;
              if (g0731 == (NULL))
               { int  i1 = g0730->inf->latestValue;
                { OID gc_local;
                  g0753UU= _oid_(CFALSE);
                  while ((i1 <= g0730->sup->latestValue))
                  { GC_LOOP;
                    { ClaireBoolean * g0754I;
                      { OID  g0755UU;
                        { IntVar * g0732 = index2Var;
                          AbstractIntDomain * g0733 = g0732->bucket;
                          if (g0733 == (NULL))
                           { int  i2 = g0732->inf->latestValue;
                            { OID gc_local;
                              g0755UU= _oid_(CFALSE);
                              while ((i2 <= g0732->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0755UU = Kernel.ctrue;
                                  break;} 
                                i2= ((g0732->inf->latestValue <= (i2+1)) ?
                                  (i2+1) :
                                  g0732->inf->latestValue );
                                } 
                              } 
                            } 
                          else if (INHERIT(g0733->isa,choco._LinkedListIntDomain))
                           { int  i2 = g0732->inf->latestValue;
                            { OID gc_local;
                              g0755UU= _oid_(CFALSE);
                              while ((i2 <= g0732->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0755UU = Kernel.ctrue;
                                  break;} 
                                i2= choco.getNextValue->fcall(((int) g0733),((int) i2));
                                } 
                              } 
                            } 
                          else { OID gc_local;
                              ITERATE(i2);
                              g0755UU= _oid_(CFALSE);
                              bag *i2_support;
                              i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0733)))));
                              for (START(i2_support); NEXT(i2);)
                              { GC_LOOP;
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0755UU = Kernel.ctrue;
                                  break;} 
                                GC_UNLOOP;} 
                              } 
                            } 
                        g0754I = boolean_I_any(g0755UU);
                        } 
                      
                      if (g0754I == CTRUE) { g0753UU = Kernel.ctrue;
                          break;} 
                        } 
                    i1= ((g0730->inf->latestValue <= (i1+1)) ?
                      (i1+1) :
                      g0730->inf->latestValue );
                    GC_UNLOOP;} 
                  } 
                } 
              else if (INHERIT(g0731->isa,choco._LinkedListIntDomain))
               { int  i1 = g0730->inf->latestValue;
                { OID gc_local;
                  g0753UU= _oid_(CFALSE);
                  while ((i1 <= g0730->sup->latestValue))
                  { GC_LOOP;
                    { ClaireBoolean * g0756I;
                      { OID  g0757UU;
                        { IntVar * g0734 = index2Var;
                          AbstractIntDomain * g0735 = g0734->bucket;
                          if (g0735 == (NULL))
                           { int  i2 = g0734->inf->latestValue;
                            { OID gc_local;
                              g0757UU= _oid_(CFALSE);
                              while ((i2 <= g0734->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0757UU = Kernel.ctrue;
                                  break;} 
                                i2= ((g0734->inf->latestValue <= (i2+1)) ?
                                  (i2+1) :
                                  g0734->inf->latestValue );
                                } 
                              } 
                            } 
                          else if (INHERIT(g0735->isa,choco._LinkedListIntDomain))
                           { int  i2 = g0734->inf->latestValue;
                            { OID gc_local;
                              g0757UU= _oid_(CFALSE);
                              while ((i2 <= g0734->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0757UU = Kernel.ctrue;
                                  break;} 
                                i2= choco.getNextValue->fcall(((int) g0735),((int) i2));
                                } 
                              } 
                            } 
                          else { OID gc_local;
                              ITERATE(i2);
                              g0757UU= _oid_(CFALSE);
                              bag *i2_support;
                              i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0735)))));
                              for (START(i2_support); NEXT(i2);)
                              { GC_LOOP;
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0757UU = Kernel.ctrue;
                                  break;} 
                                GC_UNLOOP;} 
                              } 
                            } 
                        g0756I = boolean_I_any(g0757UU);
                        } 
                      
                      if (g0756I == CTRUE) { g0753UU = Kernel.ctrue;
                          break;} 
                        } 
                    i1= choco.getNextValue->fcall(((int) g0731),((int) i1));
                    GC_UNLOOP;} 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(i1);
                  g0753UU= _oid_(CFALSE);
                  bag *i1_support;
                  i1_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0731)))));
                  for (START(i1_support); NEXT(i1);)
                  { GC_LOOP;
                    { ClaireBoolean * g0758I;
                      { OID  g0759UU;
                        { IntVar * g0736 = index2Var;
                          AbstractIntDomain * g0737 = g0736->bucket;
                          if (g0737 == (NULL))
                           { int  i2 = g0736->inf->latestValue;
                            { OID gc_local;
                              g0759UU= _oid_(CFALSE);
                              while ((i2 <= g0736->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0759UU = Kernel.ctrue;
                                  break;} 
                                i2= ((g0736->inf->latestValue <= (i2+1)) ?
                                  (i2+1) :
                                  g0736->inf->latestValue );
                                } 
                              } 
                            } 
                          else if (INHERIT(g0737->isa,choco._LinkedListIntDomain))
                           { int  i2 = g0736->inf->latestValue;
                            { OID gc_local;
                              g0759UU= _oid_(CFALSE);
                              while ((i2 <= g0736->sup->latestValue))
                              { // HOHO, GC_LOOP not needed !
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0759UU = Kernel.ctrue;
                                  break;} 
                                i2= choco.getNextValue->fcall(((int) g0737),((int) i2));
                                } 
                              } 
                            } 
                          else { OID gc_local;
                              ITERATE(i2);
                              g0759UU= _oid_(CFALSE);
                              bag *i2_support;
                              i2_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0737)))));
                              for (START(i2_support); NEXT(i2);)
                              { GC_LOOP;
                                if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(valueVar),
                                  nth_table2(l,i1,i2)))) == CTRUE)
                                 { g0759UU = Kernel.ctrue;
                                  break;} 
                                GC_UNLOOP;} 
                              } 
                            } 
                        g0758I = boolean_I_any(g0759UU);
                        } 
                      
                      if (g0758I == CTRUE) { g0753UU = Kernel.ctrue;
                          break;} 
                        } 
                    GC_UNLOOP;} 
                  } 
                } 
            g0752I = boolean_I_any(g0753UU);
            } 
          
          if (g0752I == CTRUE) Result = CNULL;
            else Result = Kernel.cfalse;
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907 <thb> missing methods
/* The c++ function for: testIfSatisfied(c:Elt2) [] */
ClaireBoolean * choco_testIfSatisfied_Elt2(Elt2 *c)
{ ;return (equal(nth_table2(c->tval,c->v1->value,c->v2->value),c->v3->value));} 


// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 2: AllDifferent                                           *
// ********************************************************************
// v0.28: fully rewritten, no longer a global constraint
/* The c++ function for: self_print(self:AllDiff) [] */
void  claire_self_print_AllDiff_choco(AllDiff *self)
{ GC_BIND;
  princ_string("AllDiff(");
  print_any(GC_OID(_oid_(self->vars)));
  princ_string(")");
  GC_UNBIND;} 


// ----- Constraint:  AllDifferent(Xi) ------
// forward checking propagation
/* The c++ function for: awakeOnInf(c:AllDiff,idx:integer) [] */
void  choco_awakeOnInf_AllDiff(AllDiff *c,int idx)
{ ;} 


/* The c++ function for: awakeOnSup(c:AllDiff,idx:integer) [] */
void  choco_awakeOnSup_AllDiff(AllDiff *c,int idx)
{ ;} 


/* The c++ function for: awakeOnRem(c:AllDiff,idx:integer,x:integer) [] */
void  choco_awakeOnRem_AllDiff(AllDiff *c,int idx,int x)
{ ;} 


// Note (v0.28): the conclusion (removeVal) passes the cause of the event as
// a parameter (c.indices[i]).
// This will avoid remakening the AllDiff if l[i] undergoes only a value removal
// However, if this value removal turns out to be an instantiation,
// then, this will be considered as an event strengthening
// and the initial cause (AllDiff) will be repropagated !
/* The c++ function for: awakeOnInst(c:AllDiff,idx:integer) [] */
void  choco_awakeOnInst_AllDiff(AllDiff *c,int idx)
{ GC_BIND;
  { list * l = c->vars;
    int  n = c->nbVars;
    int  val = OBJECT(IntVar,(*(l))[idx])->value;
    { int  i = 1;
      int  g0760 = (idx-1);
      { OID gc_local;
        while ((i <= g0760))
        { // HOHO, GC_LOOP not needed !
          (*choco.removeVal)((*(l))[i],
            val,
            (*(c->indices))[i]);
          ++i;
          } 
        } 
      } 
    { int  i = (idx+1);
      int  g0761 = n;
      { OID gc_local;
        while ((i <= g0761))
        { // HOHO, GC_LOOP not needed !
          (*choco.removeVal)((*(l))[i],
            val,
            (*(c->indices))[i]);
          ++i;
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: propagate(c:AllDiff) [] */
void  choco_propagate_AllDiff(AllDiff *c)
{ GC_BIND;
  { list * l = c->vars;
    int  n = c->nbVars;
    int  i = 1;
    int  g0762 = n;
    { OID gc_local;
      while ((i <= g0762))
      { // HOHO, GC_LOOP not needed !
        { IntVar * v = OBJECT(IntVar,(*(l))[i]);
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v))))) == CTRUE)
           { int  val = v->value;
            { int  j = 1;
              int  g0763 = (i-1);
              { OID gc_local;
                while ((j <= g0763))
                { // HOHO, GC_LOOP not needed !
                  (*choco.removeVal)((*(l))[j],
                    val,
                    (*(c->indices))[j]);
                  ++j;
                  } 
                } 
              } 
            { int  j = (i+1);
              int  g0764 = n;
              { OID gc_local;
                while ((j <= g0764))
                { // HOHO, GC_LOOP not needed !
                  (*choco.removeVal)((*(l))[j],
                    val,
                    (*(c->indices))[j]);
                  ++j;
                  } 
                } 
              } 
            } 
          } 
        ++i;
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: testIfSatisfied(c:AllDiff) [] */
ClaireBoolean * choco_testIfSatisfied_AllDiff(AllDiff *c)
{ { ClaireBoolean *Result ;
    { list * l = c->vars;
      int  n = c->nbVars;
      ClaireBoolean * result = CTRUE;
      { int  i = 1;
        int  g0765 = (n-1);
        { while ((i <= g0765))
          { { int  x = OBJECT(IntVar,(*(l))[i])->value;
              int  j = (i+1);
              int  g0766 = n;
              { while ((j <= g0766))
                { if (x == OBJECT(IntVar,(*(l))[j])->value)
                   result= CFALSE;
                  ++j;
                  } 
                } 
              } 
            ++i;
            } 
          } 
        } 
      Result = result;
      } 
    return (Result);} 
  } 


// v0.93 <thb>
/* The c++ function for: askIfEntailed(c:AllDiff) [] */
OID  choco_askIfEntailed_AllDiff(AllDiff *c)
{ { OID Result = 0;
    { OID  res = CNULL;
      res= Kernel.ctrue;
      { int  i = 1;
        int  g0767 = c->nbVars;
        { OID gc_local;
          while ((i <= g0767))
          { // HOHO, GC_LOOP not needed !
            { int  j = (i+1);
              int  g0768 = c->nbVars;
              { OID gc_local;
                while ((j <= g0768))
                { // HOHO, GC_LOOP not needed !
                  { IntVar * vi = OBJECT(IntVar,(*(c->vars))[i]);
                    IntVar * vj = OBJECT(IntVar,(*(c->vars))[j]);
                    if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) vi))))) == CTRUE) && 
                        (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) vj))))) == CTRUE) && 
                          (vi->value == vj->value)))
                     { res= Kernel.cfalse;
                      { ;break;} 
                      } 
                    else if ((OBJECT(ClaireBoolean,(*choco.canBeEqualTo)(_oid_(vi),
                      _oid_(vj)))) == CTRUE)
                     { res= CNULL;
                      { ;break;} 
                      } 
                    } 
                  ++j;
                  } 
                } 
              } 
            ++i;
            } 
          } 
        } 
      Result = res;
      } 
    return (Result);} 
  } 


// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 3: occurences of a value in a list of variables           *
// ********************************************************************
// v0.28 now inheriting from LargeIntConstraint, and with a "indices" field
// v0.32 <thb>: an isSure list must be maintained.
//        Otherwise, INSTANTIATE events triggering both awake (when the occurence was in a boolean constraint) and
//        awakeOnInst could cause a double increment of nbSure. (see non-regression test "testNbSure1()" for an example)
/* The c++ function for: self_print(self:Occurrence) [] */
void  claire_self_print_Occurrence_choco(Occurrence *self)
{ GC_BIND;
  { int  n = self->nbVars;
    list * l = GC_OBJECT(list,self->vars);
    princ_string("#{x in ");
    { OID  g0770UU;
      { list * V_CL0771;{ list * i_bag = list::empty(Kernel.emptySet);
          { int  i = 1;
            int  g0769 = (n-1);
            { OID gc_local;
              while ((i <= g0769))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast((*(l))[i]);
                ++i;
                } 
              } 
            } 
          V_CL0771 = GC_OBJECT(list,i_bag);
          } 
        
        g0770UU=_oid_(V_CL0771);} 
      print_any(g0770UU);
      } 
    princ_string(" | x = ");
    print_any(self->cste);
    princ_string("} ");
    princ_string(((self->constrainOnInfNumber == CTRUE) ?
      ((self->constrainOnSupNumber == CTRUE) ?
        "=" :
        ">=" ) :
      "<=" ));
    princ_string(" ");
    print_any((*(l))[n]);
    princ_string("");
    } 
  GC_UNBIND;} 


// ----- Constraint:  Occur(v,(Xi),y) ------
// Two Utils
//
/* The c++ function for: checkNbPossible(c:Occurrence) [] */
void  choco_checkNbPossible_Occurrence_choco(Occurrence *c)
{ GC_BIND;
  ;{ list * l = GC_OBJECT(list,c->vars);
    int  n = c->nbVars;
    OID  nbVar = (*(l))[n];
    int  target = c->cste;
    if (c->constrainOnInfNumber == CTRUE)
     { (*choco.updateSup)(nbVar,
        c->nbPossible,
        (*(c->indices))[n]);
      if (OBJECT(IntVar,nbVar)->inf->latestValue == c->nbPossible)
       { int  i = 1;
        int  g0772 = (n-1);
        { OID gc_local;
          while ((i <= g0772))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,((OID *) c->isPossible)[i])) == CTRUE)
             choco_instantiate_IntVar2(OBJECT(IntVar,(*(l))[i]),target,(*(c->indices))[i]);
            ++i;
            } 
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: checkNbSure(c:Occurrence) [] */
void  choco_checkNbSure_Occurrence_choco(Occurrence *c)
{ GC_BIND;
  { list * l = c->vars;
    int  n = c->nbVars;
    OID  nbVar = (*(l))[n];
    int  target = c->cste;
    if (c->constrainOnSupNumber == CTRUE)
     { (*choco.updateInf)(nbVar,
        c->nbSure,
        (*(c->indices))[n]);
      if (OBJECT(IntVar,nbVar)->sup->latestValue == c->nbSure)
       { { int  i = 1;
          int  g0773 = (n-1);
          { OID gc_local;
            while ((i <= g0773))
            { // HOHO, GC_LOOP not needed !
              if (((OBJECT(ClaireBoolean,((OID *) c->isPossible)[i])) == CTRUE) && 
                  (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,(*(l))[i]))))) == CTRUE))
               (*choco.removeVal)((*(l))[i],
                target,
                (*(c->indices))[i]);
              ++i;
              } 
            } 
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// back-propagates the bounds of the counter variable on the main variables.
/* The c++ function for: update(c:Occurrence) [] */
void  choco_update_Occurrence(Occurrence *c)
{ (*choco.checkNbPossible)(_oid_(c));
  ;(*choco.checkNbSure)(_oid_(c));
  } 


// propagates the values of the main variables onto the counter variable 
// v0.29: must compute (initialize) nbSure as well as nbPossible
// v0.31: maintains the isSure list
/* The c++ function for: propagate(c:Occurrence) [] */
void  choco_propagate_Occurrence(Occurrence *c)
{ GC_BIND;
  { list * l = GC_OBJECT(list,c->vars);
    int  n = c->nbVars;
    int  target = c->cste;
    { int  j = 1;
      int  g0774 = (n-1);
      { OID gc_local;
        while ((j <= g0774))
        { // HOHO, GC_LOOP not needed !
          if ((OBJECT(ClaireBoolean,((OID *) c->isPossible)[j])) == CTRUE)
           { IntVar * v = OBJECT(IntVar,(*(l))[j]);
            if ((not_any(((OID *) c->isSure)[j]) == CTRUE) && 
                ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
                  target))) == CTRUE))
             { STOREI(c->isSure[j],Kernel.ctrue);
              STOREI(c->nbSure,(c->nbSure+1));
              } 
            else if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
              target)) == CTRUE)
             { STOREI(c->isPossible[j],Kernel.cfalse);
              STOREI(c->nbPossible,(c->nbPossible-1));
              } 
            } 
          ++j;
          } 
        } 
      } 
    choco_update_Occurrence(c);
    } 
  GC_UNBIND;} 


/* The c++ function for: awake(c:Occurrence) [] */
void  choco_awake_Occurrence_choco(Occurrence *c)
{ { int  n = c->nbVars;
    IntVar * nbVar = OBJECT(IntVar,(*(c->vars))[n]);
    if (c->constrainOnInfNumber == CTRUE)
     (*choco.updateSup)(_oid_(nbVar),
      n,
      (*(c->indices))[n]);
    if (c->constrainOnSupNumber == CTRUE)
     (*choco.updateInf)(_oid_(nbVar),
      0,
      (*(c->indices))[n]);
    } 
  choco_propagate_Occurrence(c);
  } 


// <thb> v0.31: There is a smart propagation path, when the variables are BoundIntVar (no bucket):
// When the number of occurrences has already reached its max, then, the additional
// possible occurrences of the value should be forbidden. Therefore whenever the bound
// of a variable reaches the target value, this value can be shaved off and the bound improved by 1.
/* The c++ function for: awakeOnInf(c:Occurrence,idx:integer) [] */
void  choco_awakeOnInf_Occurrence(Occurrence *c,int idx)
{ if (idx < c->nbVars)
   { if ((OBJECT(ClaireBoolean,((OID *) c->isPossible)[idx])) == CTRUE)
     { if (OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue > c->cste)
       { STOREI(c->isPossible[idx],Kernel.cfalse);
        STOREI(c->nbPossible,(c->nbPossible-1));
        (*choco.checkNbPossible)(_oid_(c));
        } 
      else if ((OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue == c->cste) && 
          ((not_any(((OID *) c->isSure)[idx]) == CTRUE) && 
            ((c->constrainOnSupNumber == CTRUE) && 
              (c->nbSure == OBJECT(IntVar,(*(c->vars))[c->nbVars])->sup->latestValue))))
       { (*choco.updateInf)((*(c->vars))[idx],
          (c->cste+1),
          (*(c->indices))[idx]);
        } 
      } 
    } 
  else (*choco.checkNbPossible)(_oid_(c));
    } 


/* The c++ function for: awakeOnSup(c:Occurrence,idx:integer) [] */
void  choco_awakeOnSup_Occurrence(Occurrence *c,int idx)
{ if (idx < c->nbVars)
   { if ((OBJECT(ClaireBoolean,((OID *) c->isPossible)[idx])) == CTRUE)
     { if (OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue < c->cste)
       { STOREI(c->isPossible[idx],Kernel.cfalse);
        STOREI(c->nbPossible,(c->nbPossible-1));
        (*choco.checkNbPossible)(_oid_(c));
        } 
      else if ((OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue == c->cste) && 
          ((not_any(((OID *) c->isSure)[idx]) == CTRUE) && 
            ((c->constrainOnSupNumber == CTRUE) && 
              (c->nbSure == OBJECT(IntVar,(*(c->vars))[c->nbVars])->sup->latestValue))))
       { (*choco.updateSup)((*(c->vars))[idx],
          (c->cste-1),
          (*(c->indices))[idx]);
        } 
      } 
    } 
  else (*choco.checkNbSure)(_oid_(c));
    } 


// v0.32: maintain c.isSure
/* The c++ function for: awakeOnInst(c:Occurrence,idx:integer) [] */
void  choco_awakeOnInst_Occurrence(Occurrence *c,int idx)
{ if ((idx < c->nbVars) && 
      (((OBJECT(ClaireBoolean,((OID *) c->isPossible)[idx])) == CTRUE) && 
        (not_any(((OID *) c->isSure)[idx]) == CTRUE)))
   { if (OBJECT(IntVar,(*(c->vars))[idx])->value == c->cste)
     { STOREI(c->isSure[idx],Kernel.ctrue);
      STOREI(c->nbSure,(c->nbSure+1));
      (*choco.checkNbSure)(_oid_(c));
      } 
    else { STOREI(c->isPossible[idx],Kernel.cfalse);
        STOREI(c->nbPossible,(c->nbPossible-1));
        (*choco.checkNbPossible)(_oid_(c));
        } 
      } 
  else { (*choco.checkNbSure)(_oid_(c));
      (*choco.checkNbPossible)(_oid_(c));
      } 
    } 


/* The c++ function for: awakeOnRem(c:Occurrence,idx:integer,x:integer) [] */
void  choco_awakeOnRem_Occurrence(Occurrence *c,int idx,int x)
{ if ((idx < c->nbVars) && 
      ((x == c->cste) && 
        (((OBJECT(ClaireBoolean,((OID *) c->isPossible)[idx])) == CTRUE) && 
          (OBJECT(IntVar,(*(c->vars))[idx])->bucket != (NULL)))))
   { STOREI(c->isPossible[idx],Kernel.cfalse);
    STOREI(c->nbPossible,(c->nbPossible-1));
    (*choco.checkNbPossible)(_oid_(c));
    } 
  } 


// <thb> v0.93
/* The c++ function for: testIfSatisfied(c:Occurrence) [] */
ClaireBoolean * choco_testIfSatisfied_Occurrence(Occurrence *c)
{ { ClaireBoolean *Result ;
    { OID  g0778UU;
      { int  g0775 = 0;
        { int  g0776 = 1;
          int  g0777 = (c->nbVars-1);
          { while ((g0776 <= g0777))
            { if (OBJECT(IntVar,(*(c->vars))[g0776])->value == c->cste)
               ++g0775;
              ++g0776;
              } 
            } 
          } 
        g0778UU = g0775;
        } 
      Result = ((equal((*(c->vars))[c->nbVars],g0778UU) == CTRUE) ? CTRUE : CFALSE);
      } 
    return (Result);} 
  } 


// v0.9907 <thb>
// <thb> v0.93
/* The c++ function for: askIfEntailed(c:Occurrence) [] */
OID  choco_askIfEntailed_Occurrence(Occurrence *c)
{ { OID Result = 0;
    { IntVar * occ = OBJECT(IntVar,(*(c->vars))[c->nbVars]);
      int  nbS = 0;
      int  nbP = 0;
      { int  i = 1;
        int  g0779 = (c->nbVars-1);
        { OID gc_local;
          while ((i <= g0779))
          { // HOHO, GC_LOOP not needed !
            { IntVar * v = OBJECT(IntVar,(*(c->vars))[i]);
              if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
                c->cste))) == CTRUE)
               ++nbS;
              if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)(_oid_(v),
                c->cste))) == CTRUE)
               ++nbP;
              } 
            ++i;
            } 
          } 
        } 
      if ((c->constrainOnSupNumber == CTRUE) && 
          (c->constrainOnInfNumber == CTRUE))
       { if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) occ))))) == CTRUE) && 
            ((nbS == nbP) && 
              (occ->value == nbS)))
         Result = Kernel.ctrue;
        else if ((nbP < occ->inf->latestValue) || 
            (nbS > occ->sup->latestValue))
         Result = Kernel.cfalse;
        else Result = CNULL;
          } 
      else if (c->constrainOnInfNumber == CTRUE)
       { if (occ->sup->latestValue <= nbS)
         Result = Kernel.ctrue;
        else if (nbP < occ->inf->latestValue)
         Result = Kernel.cfalse;
        else Result = CNULL;
          } 
      else if (c->constrainOnSupNumber == CTRUE)
       { if (nbP <= occ->inf->latestValue)
         Result = Kernel.ctrue;
        else if (nbS > occ->sup->latestValue)
         Result = Kernel.cfalse;
        else Result = CNULL;
          } 
      else close_exception(((general_error *) (*Core._general_error)(_string_("Stop and debug: constrainOnSupNumber or constrainOnInfNumber must be true"),
          _oid_(Kernel.nil))));
        } 
    return (Result);} 
  } 


// v0.34
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)