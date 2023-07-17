/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\var.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:26 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: var.cl                                                     *
// *    modelling domain variables                                    *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: AbstractVar                                            *
// *   Part 2: IntVar                                                 *
// *   Part 3: Generating events from IntVars                         *
// *   Part 4: SetVar                                                 *
// *   Part 5: Generating events from SetVars                         *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: AbstractVar                                            *
// ********************************************************************
// the abstract class for all types of domain variables
// if c=v.constraints[i]
// interface of AbstractVar
//    choco/domain(v:AbstractVar) : set U list U Interval
/* The c++ function for: isInstantiated(v:AbstractVar) [] */
ClaireBoolean * choco_isInstantiated_AbstractVar_choco(AbstractVar *v)
{ return (CFALSE);} 


// v1.0
/* The c++ function for: getConstraint(v:AbstractVar,i:integer) [] */
AbstractConstraint * choco_getConstraint_AbstractVar(AbstractVar *v,int i)
{ return (OBJECT(AbstractConstraint,(*(v->constraints))[i]));} 


// v1.010: accessing the degree of a variable
/* The c++ function for: getDegree(v:AbstractVar) [] */
int  choco_getDegree_AbstractVar(AbstractVar *v)
{ return (v->constraints->length);} 


// ********************************************************************
// *   Part 2: IntVar                                                 *
// ********************************************************************
// the class for all integer variables
// v0.37 <fl> more efficient storage of inf/sup slots
// v1.02 inf and sup are no longer stored since they are StoredInt !!!!!
/* The c++ function for: self_print(v:IntVar) [] */
void  claire_self_print_IntVar_choco(IntVar *v)
{ GC_BIND;
  if (v->name != NULL)
   { princ_string(v->name);
    princ_string(":");
    } 
  else princ_string("<IntVar>");
    if (choco_knownInt_integer(v->value) == CTRUE)
   { print_any(v->value);
    princ_string("");
    } 
  else if (v->bucket != (NULL))
   { print_any(GC_OID(_oid_(v->bucket)));
    princ_string("");
    } 
  else { princ_string("[");
      print_any(v->inf->latestValue);
      princ_string(".");
      print_any(v->sup->latestValue);
      princ_string("]");
      } 
    GC_UNBIND;} 


// v0.9903:add one parameter (number of removal to react to, one by one)
/* The c++ function for: closeIntVar(v:IntVar,i:integer,j:integer,p:integer) [] */
void  choco_closeIntVar_IntVar(IntVar *v,int i,int j,int p)
{ GC_BIND;
  { IntVar * g0125; 
    StoredInt * g0126;
    g0125 = v;
    { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
      (_CL_obj->latestValue = i);
      g0126 = _CL_obj;
      } 
    (g0125->inf = g0126);} 
  { IntVar * g0127; 
    StoredInt * g0128;
    g0127 = v;
    { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
      (_CL_obj->latestValue = j);
      g0128 = _CL_obj;
      } 
    (g0127->sup = g0128);} 
  if (i == j)
   STOREI(v->value,i);
  { IntVar * g0129; 
    IncInf * g0130;
    g0129 = v;
    { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
      (_CL_obj->modifiedVar = v);
      g0130 = _CL_obj;
      } 
    (g0129->updtInfEvt = g0130);} 
  { IntVar * g0131; 
    DecSup * g0132;
    g0131 = v;
    { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
      (_CL_obj->modifiedVar = v);
      g0132 = _CL_obj;
      } 
    (g0131->updtSupEvt = g0132);} 
  { IntVar * g0133; 
    InstInt * g0134;
    g0133 = v;
    { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
      (_CL_obj->modifiedVar = v);
      g0134 = _CL_obj;
      } 
    (g0133->instantiateEvt = g0134);} 
  { ValueRemovals * e;
    { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
        (_CL_obj->modifiedVar = v);
        (_CL_obj->maxVals = p);
        e = _CL_obj;
        } 
      GC_OBJECT(ValueRemovals,e);} 
    (e->valueStack = make_list_integer2(e->maxVals,Kernel._integer,0));
    (e->causeStack = make_list_integer2(e->maxVals,Kernel._integer,0));
    (v->remValEvt = e);
    } 
  GC_UNBIND;} 


// [choco/closeIntVar(v:IntVar) : void => closeIntVar(v,-100,100)]  // 0.9903 never used ?????
// forward
// choco/raiseContradiction :: property()
// Basic interface of IntVar for performing events on variable domains
/* The c++ function for: updateInf(v:IntVar,x:integer) [] */
ClaireBoolean * choco_updateInf_IntVar1_choco(IntVar *v,int x)
{ if (x > v->inf->latestValue) 
  { { ClaireBoolean *Result ;
      { if (x > v->sup->latestValue)
         { if (x > 99999999)
           close_exception(((general_error *) (*Core._general_error)(_string_("Finite domain overflow on var:~S, ~S > ~S (MAXINT)"),
            _oid_(list::alloc(3,_oid_(v),
              x,
              99999999)))));
          else choco_raiseContradiction_AbstractVar(v);
            } 
        else if (v->bucket != (NULL))
         claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),choco.updateDomainInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) x)));
        else claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),x);
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


/* The c++ function for: updateSup(v:IntVar,x:integer) [] */
ClaireBoolean * choco_updateSup_IntVar1_choco(IntVar *v,int x)
{ if (x < v->sup->latestValue) 
  { { ClaireBoolean *Result ;
      { if (x < v->inf->latestValue)
         { if (x < -99999999)
           close_exception(((general_error *) (*Core._general_error)(_string_("Finite domain overflow on var:~S, ~S < ~S (MININT)"),
            _oid_(list::alloc(3,_oid_(v),
              x,
              -99999999)))));
          else choco_raiseContradiction_AbstractVar(v);
            } 
        else if (v->bucket != (NULL))
         claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),choco.updateDomainSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) x)));
        else claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),x);
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


/* The c++ function for: instantiate(v:IntVar,x:integer) [] */
ClaireBoolean * choco_instantiate_IntVar1(IntVar *v,int x)
{ if (choco_knownInt_integer(v->value) == CTRUE) 
  { { ClaireBoolean *Result ;
      if (v->value != x)
       { choco_raiseContradiction_AbstractVar(v);
        Result = CTRUE;
        } 
      else Result = CFALSE;
        return (Result);} 
     } 
  else{ GC_BIND;
    if ((x < v->inf->latestValue) || 
        (x > v->sup->latestValue))
     choco_raiseContradiction_AbstractVar(v);
    else { AbstractIntDomain * dom = GC_OBJECT(AbstractIntDomain,v->bucket);
        if (dom == (NULL))
         ;else if (boolean_I_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) dom),((int) x)))) == CTRUE)
         _void_(choco.restrict->fcall(((int) dom),((int) x)));
        else choco_raiseContradiction_AbstractVar(v);
          } 
      claire_write_StoredInt(GC_OBJECT(StoredInt,v->sup),x);
    claire_write_StoredInt(GC_OBJECT(StoredInt,v->inf),x);
    STOREI(v->value,x);
    { ClaireBoolean *Result ;
      Result = CTRUE;
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: removeVal(v:IntVar,x:integer) [] */
ClaireBoolean * choco_removeVal_IntVar1_choco(IntVar *v,int x)
{ if ((v->inf->latestValue <= x) && 
      (x <= v->sup->latestValue)) 
  { { ClaireBoolean *Result ;
      if (x == v->inf->latestValue)
       { (*choco.updateInf)(_oid_(v),
          (x+1));
        if (v->inf->latestValue == v->sup->latestValue)
         choco_instantiate_IntVar1(v,v->inf->latestValue);
        Result = CTRUE;
        } 
      else if (x == v->sup->latestValue)
       { (*choco.updateSup)(_oid_(v),
          (x-1));
        if (v->inf->latestValue == v->sup->latestValue)
         choco_instantiate_IntVar1(v,v->inf->latestValue);
        Result = CTRUE;
        } 
      else Result = ((v->bucket != (NULL)) ?
        OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.removeDomainVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) x)))) :
        CFALSE );
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireBoolean *Result ;
      Result = CFALSE;
      GC_UNBIND; return (Result);} 
    } 
  } 


// Implementing the interface of AbstractVar :
//   1. iterating the values in the current domain of a variable
// claire3 port: Intervals no longer need to be typed, change range to subtype[int]
// v1.04: use the domainSequence new API method
/* The c++ function for: domain(x:IntVar) [] */
ClaireType * claire_domain_IntVar(IntVar *x)
{ if (x->bucket != (NULL)) 
  { { ClaireType *Result ;
      Result = OBJECT(ClaireType,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(x->bucket)))))));
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireType *Result ;
      Result = _dot_dot_integer(x->inf->latestValue,x->sup->latestValue);
      GC_UNBIND; return (Result);} 
    } 
  } 


//   2. iterating the values in the current domain of a variable
// 1.322: reshaped to avoid the definition of iterators on domains.
// Basic interface of IntVar for testing domains
// <thb> v0.93
// <ega> v0.9901
// <thb> v1.02
/* The c++ function for: isInstantiatedTo(v:IntVar,x:integer) [] */
ClaireBoolean * choco_isInstantiatedTo_IntVar_choco(IntVar *v,int x)
{ return (equal(v->value,x));} 


//  knownInt(v.value)
/* The c++ function for: canBeInstantiatedTo(v:IntVar,x:integer) [] */
ClaireBoolean * choco_canBeInstantiatedTo_IntVar_choco(IntVar *v,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((v->inf->latestValue <= x) ? ((x <= v->sup->latestValue) ? (((v->bucket == (NULL)) || 
        (boolean_I_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) x)))) == CTRUE)) ? CTRUE: CFALSE): CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// returns true iff 2 IntVar have intersecting domains
/* The c++ function for: canBeEqualTo(v:IntVar,x:IntVar) [] */
ClaireBoolean * choco_canBeEqualTo_IntVar_choco(IntVar *v,IntVar *x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean *v_and;
      { v_and = ((x->inf->latestValue <= v->sup->latestValue) ? CTRUE : CFALSE);
        if (v_and == CFALSE) Result =CFALSE; 
        else { v_and = ((v->inf->latestValue <= x->sup->latestValue) ? CTRUE : CFALSE);
          if (v_and == CFALSE) Result =CFALSE; 
          else { { ClaireBoolean *v_or;
              { v_or = ((v->bucket == (NULL)) ? ((x->bucket == (NULL)) ? CTRUE: CFALSE): CFALSE);
                if (v_or == CTRUE) v_and =CTRUE; 
                else { { OID  g0137UU;
                    { IntVar * g0135 = x;
                      AbstractIntDomain * g0136 = g0135->bucket;
                      if (g0136 == (NULL))
                       { int  val = g0135->inf->latestValue;
                        { OID gc_local;
                          g0137UU= _oid_(CFALSE);
                          while ((val <= g0135->sup->latestValue))
                          { GC_LOOP;
                            if (boolean_I_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) val)))) == CTRUE)
                             { g0137UU = Kernel.ctrue;
                              break;} 
                            val= ((g0135->inf->latestValue <= (val+1)) ?
                              (val+1) :
                              g0135->inf->latestValue );
                            GC_UNLOOP;} 
                          } 
                        } 
                      else if (INHERIT(g0136->isa,choco._LinkedListIntDomain))
                       { int  val = g0135->inf->latestValue;
                        { OID gc_local;
                          g0137UU= _oid_(CFALSE);
                          while ((val <= g0135->sup->latestValue))
                          { GC_LOOP;
                            if (boolean_I_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) val)))) == CTRUE)
                             { g0137UU = Kernel.ctrue;
                              break;} 
                            val= choco.getNextValue->fcall(((int) g0136),((int) val));
                            GC_UNLOOP;} 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(val);
                          g0137UU= _oid_(CFALSE);
                          bag *val_support;
                          val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0136)))));
                          for (START(val_support); NEXT(val);)
                          { GC_LOOP;
                            if (boolean_I_any(_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) val)))) == CTRUE)
                             { g0137UU = Kernel.ctrue;
                              break;} 
                            GC_UNLOOP;} 
                          } 
                        } 
                    v_or = boolean_I_any(g0137UU);
                    } 
                  if (v_or == CTRUE) v_and =CTRUE; 
                  else v_and = CFALSE;} 
                } 
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


//  <ega> v0.9901: checks if all values in v are included in l
// v0.9907
/* The c++ function for: domainIncludedIn(v:IntVar,sortedList:list[integer]) [] */
ClaireBoolean * choco_domainIncludedIn_IntVar_choco(IntVar *v,list *sortedList)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((*(sortedList))[1] <= v->inf->latestValue) ? ((v->sup->latestValue <= last_list(sortedList)) ? (((v->bucket == (NULL)) || 
        (choco_isIncludedIn_LinkedListIntDomain(GC_OBJECT(LinkedListIntDomain,((LinkedListIntDomain *) v->bucket)),sortedList) == CTRUE)) ? CTRUE: CFALSE): CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: canBeInstantiatedIn(v:IntVar,sortedList:list[integer]) [] */
ClaireBoolean * choco_canBeInstantiatedIn_IntVar_choco(IntVar *v,list *sortedList)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean *v_and;
      { v_and = ((v->inf->latestValue <= last_list(sortedList)) ? CTRUE : CFALSE);
        if (v_and == CFALSE) Result =CFALSE; 
        else { v_and = (((*(sortedList))[1] <= v->sup->latestValue) ? CTRUE : CFALSE);
          if (v_and == CFALSE) Result =CFALSE; 
          else { { ClaireBoolean *v_or;
              { v_or = ((v->bucket == (NULL)) ? CTRUE : CFALSE);
                if (v_or == CTRUE) v_and =CTRUE; 
                else { { OID  g0138UU;
                    { OID gc_local;
                      ITERATE(val);
                      g0138UU= _oid_(CFALSE);
                      bag *val_support;
                      val_support = GC_OBJECT(bag,enumerate_any(GC_OID(_oid_(v->bucket))));
                      for (START(val_support); NEXT(val);)
                      { GC_LOOP;
                        if (contain_ask_list(sortedList,val) == CTRUE)
                         { g0138UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    v_or = boolean_I_any(g0138UU);
                    } 
                  if (v_or == CTRUE) v_and =CTRUE; 
                  else v_and = CFALSE;} 
                } 
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907 <ega>
/* The c++ function for: hasExactDomain(v:IntVar) [] */
ClaireBoolean * choco_hasExactDomain_IntVar_choco(IntVar *v)
{ return (not_any(_oid_(((v->bucket == (NULL)) ? CTRUE : CFALSE))));} 


/* The c++ function for: random(v:IntVar) [] */
int  claire_random_IntVar(IntVar *v)
{ GC_BIND;
  { int Result = 0;
    { AbstractIntDomain * dom = GC_OBJECT(AbstractIntDomain,v->bucket);
      Result = ((dom == (NULL)) ?
        claire_random_integer2(v->inf->latestValue,v->sup->latestValue) :
        claire_random_LinkedListIntDomain(((LinkedListIntDomain *) dom)) );
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.013: a new method for accessing the domain of an IntVar
// v1.016: <franck> add a test comparison to the domain lower bound
/* The c++ function for: getNextDomainValue(v:IntVar,i:integer) [] */
int  choco_getNextDomainValue_IntVar(IntVar *v,int i)
{ if (i < v->inf->latestValue) 
  { { int Result = 0;
      Result = v->inf->latestValue;
      return (Result);} 
     } 
  else{ GC_BIND;
    ;{ int Result = 0;
      { AbstractIntDomain * d = v->bucket;
        Result = ((d == (NULL)) ?
          (i+1) :
          choco.getNextValue->fcall(((int) d),((int) i)) );
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


// v1.016 <franck>
/* The c++ function for: getPrevDomainValue(v:IntVar,i:integer) [] */
int  choco_getPrevDomainValue_IntVar(IntVar *v,int i)
{ if (i > v->sup->latestValue) 
  { { int Result = 0;
      Result = v->sup->latestValue;
      return (Result);} 
     } 
  else{ GC_BIND;
    ;{ int Result = 0;
      { AbstractIntDomain * d = v->bucket;
        Result = ((d == (NULL)) ?
          (i-1) :
          choco.getPrevValue->fcall(((int) d),((int) i)) );
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


// retrieving bound information on the variables
/* The c++ function for: getInf(v:IntVar) [] */
int  choco_getInf_IntVar_choco(IntVar *v)
{ return (v->inf->latestValue);} 


/* The c++ function for: getSup(v:IntVar) [] */
int  choco_getSup_IntVar_choco(IntVar *v)
{ return (v->sup->latestValue);} 


/* The c++ function for: isInstantiated(v:IntVar) [] */
ClaireBoolean * choco_isInstantiated_IntVar_choco(IntVar *v)
{ return (choco_knownInt_integer(v->value));} 


/* The c++ function for: getValue(v:IntVar) [] */
int  choco_getValue_IntVar(IntVar *v)
{ ;return (v->value);} 


// retrieving bound information on the variables
// V0.9907
/* The c++ function for: getDomainSize(v:IntVar) [] */
int  choco_getDomainSize_IntVar(IntVar *v)
{ { int Result = 0;
    { AbstractIntDomain * dom = v->bucket;
      Result = ((dom == (NULL)) ?
        ((v->sup->latestValue-v->inf->latestValue)+1) :
        choco.getDomainCard->fcall(((int) dom)) );
      } 
    return (Result);} 
  } 


// ********************************************************************
// *   Part 3: Generating events from IntVars                         *
// ********************************************************************
// v0.9907: now returns a Boolean indicating whether the call indeed added new information
/* The c++ function for: updateInf(v:IntVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_updateInf_IntVar2_choco(IntVar *v,int x,int idx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    if ((OBJECT(ClaireBoolean,(*choco.updateInf)(_oid_(v),
      x))) == CTRUE)
     { int  cause = ((v->inf->latestValue == x) ?
        idx :
        0 );
      if (v->sup->latestValue == v->inf->latestValue)
       choco_instantiate_IntVar2(v,v->inf->latestValue,cause);
      else (*choco.postUpdateInf)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          cause);
        Result = CTRUE;
      } 
    else Result = CFALSE;
      GC_UNBIND; return (Result);} 
  } 


// v0.9907: now returns a Boolean indicating whether the call indeed added new information
/* The c++ function for: updateSup(v:IntVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_updateSup_IntVar2_choco(IntVar *v,int x,int idx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    if ((OBJECT(ClaireBoolean,(*choco.updateSup)(_oid_(v),
      x))) == CTRUE)
     { int  cause = ((v->sup->latestValue == x) ?
        idx :
        0 );
      if (v->sup->latestValue == v->inf->latestValue)
       choco_instantiate_IntVar2(v,v->sup->latestValue,cause);
      else (*choco.postUpdateSup)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          cause);
        Result = CTRUE;
      } 
    else Result = CFALSE;
      GC_UNBIND; return (Result);} 
  } 


// v0.9907: now returns a Boolean indicating whether the call indeed added new information
// Note: In case v<>x => v>=x+1, then, maybe we can keep the idx of the
//          event generating constraint (and avoid telling that constraint
//          that the event created not a hole, but improved a bound).
//          Maybe, but we no longer take that risk (v0.90)
//       otherwise, for instance, if v<>x => v>=x+2 (because there was already
//          a hole at x+1 in the domain of v), then, probably, we should
//          forget about the index of the event generating constraint.
//          Indeed the propagated event is stronger than the initial one that
//          was generated; thus the generating constraint should be informed
//          about such a new event.
/* The c++ function for: removeVal(v:IntVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_removeVal_IntVar2_choco(IntVar *v,int x,int idx)
{ if ((OBJECT(ClaireBoolean,(*choco.removeVal)(_oid_(v),
    x))) == CTRUE) 
  { { ClaireBoolean *Result ;
      { if (v->inf->latestValue == v->sup->latestValue)
         (*choco.postInstInt)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          0);
        else if (x < v->inf->latestValue)
         (*choco.postUpdateInf)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          0);
        else if (x > v->sup->latestValue)
         (*choco.postUpdateSup)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          0);
        else (*choco.postRemoveVal)(GC_OID(_oid_(v->problem->propagationEngine)),
            _oid_(v),
            x,
            idx);
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


// v0.9907: now returns a Boolean indicating whether the call indeed added new information
/* The c++ function for: instantiate(v:IntVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_instantiate_IntVar2(IntVar *v,int x,int idx)
{ if (choco_instantiate_IntVar1(v,x) == CTRUE) 
  { { ClaireBoolean *Result ;
      { (*choco.postInstInt)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          idx);
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


// v0.9907: now returns a Boolean indicating whether the call indeed added new information
/* The c++ function for: removeInterval(v:IntVar,a:integer,b:integer,idx:integer) [] */
ClaireBoolean * choco_removeInterval_IntVar(IntVar *v,int a,int b,int idx)
{ ;{ ClaireBoolean *Result ;
    if (a <= v->inf->latestValue)
     Result = OBJECT(ClaireBoolean,(*choco.updateInf)(_oid_(v),
      (b+1),
      idx));
    else if (v->sup->latestValue <= b)
     Result = OBJECT(ClaireBoolean,(*choco.updateSup)(_oid_(v),
      (a-1),
      idx));
    else if (v->bucket != (NULL))
     { ClaireBoolean * anyChange = CFALSE;
      { int  i = a;
        int  g0139 = b;
        { OID gc_local;
          while ((i <= g0139))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.removeVal)(_oid_(v),
              i,
              idx))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            } 
          } 
        } 
      Result = anyChange;
      } 
    else Result = CFALSE;
      return (Result);} 
  } 


// <ega> 0.9901
// incomplete propagation if
// 1- v is not an enumerated variable and
// 2- values in x are not "contiguous" 
/* The c++ function for: restrictTo(v:IntVar,sortedList:list[integer],idx:integer) [] */
void  choco_restrictTo_IntVar(IntVar *v,list *sortedList,int idx)
{ { int  n = sortedList->length;
    if ((*(sortedList))[1] > v->inf->latestValue)
     (*choco.updateInf)(_oid_(v),
      (*(sortedList))[1],
      idx);
    if ((*(sortedList))[n] < v->sup->latestValue)
     (*choco.updateSup)(_oid_(v),
      (*(sortedList))[n],
      idx);
    if (v->bucket != (NULL))
     { int  lastVal = (*(sortedList))[1];
      int  i = 2;
      int  g0140 = n;
      { OID gc_local;
        while ((i <= g0140))
        { // HOHO, GC_LOOP not needed !
          { int  j = (lastVal+1);
            int  g0141 = (((*(sortedList))[i])-1);
            { OID gc_local;
              while ((j <= g0141))
              { // HOHO, GC_LOOP not needed !
                (*choco.removeVal)(_oid_(v),
                  j,
                  idx);
                lastVal= (*(sortedList))[i];
                ++j;
                } 
              } 
            } 
          ++i;
          } 
        } 
      } 
    } 
  } 


// ********************************************************************
// *   Part 4: SetVar                                                 *
// ********************************************************************
// set-valued variables. A variable models a set of integers
/* The c++ function for: self_print(v:SetVar) [] */
void  claire_self_print_SetVar_choco(SetVar *v)
{ GC_BIND;
  if (v->name != NULL)
   { princ_string(v->name);
    princ_string(":");
    } 
  else princ_string("<SetVar>");
    { print_any(GC_OID(_oid_(choco_getDomainKernel_SetVar(v))));
    princ_string(" -- ");
    print_any(GC_OID(_oid_(choco_getDomainEnveloppe_SetVar(v))));
    princ_string("");
    } 
  GC_UNBIND;} 


/* The c++ function for: getDomainKernel(v:SetVar) [] */
list * choco_getDomainKernel_SetVar(SetVar *v)
{ GC_BIND;
  { list *Result ;
    Result = OBJECT(list,_oid_((ClaireObject *) choco.getKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainEnveloppe(v:SetVar) [] */
list * choco_getDomainEnveloppe_SetVar(SetVar *v)
{ GC_BIND;
  { list *Result ;
    Result = OBJECT(list,_oid_((ClaireObject *) choco.getEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainKernelSize(v:SetVar) [] */
int  choco_getDomainKernelSize_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainEnveloppeSize(v:SetVar) [] */
int  choco_getDomainEnveloppeSize_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainEnveloppeInf(v:SetVar) [] */
int  choco_getDomainEnveloppeInf_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getEnveloppeInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainEnveloppeSup(v:SetVar) [] */
int  choco_getDomainEnveloppeSup_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getEnveloppeSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainKernelInf(v:SetVar) [] */
int  choco_getDomainKernelInf_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getKernelInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getDomainKernelSup(v:SetVar) [] */
int  choco_getDomainKernelSup_SetVar(SetVar *v)
{ GC_BIND;
  { int Result = 0;
    Result = choco.getKernelSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isInstantiated(v:SetVar) [] */
ClaireBoolean * choco_isInstantiated_SetVar_choco(SetVar *v)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((GC_OID(choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))))) == GC_OID(choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket))))))) ? CTRUE : CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isInstantiatedTo(v:SetVar,l:list[integer]) [] */
ClaireBoolean * choco_isInstantiatedTo_SetVar_choco(SetVar *v,list *l)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((GC_OID(choco.getEnveloppeSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))))) == GC_OID(choco.getKernelSize->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket))))))) ? ((equal(_oid_(choco_getDomainKernel_SetVar(v)),_oid_(l)) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getValue(v:SetVar) [] */
list * choco_getValue_SetVar(SetVar *v)
{ return (choco_getDomainKernel_SetVar(v));} 


/* The c++ function for: isInDomainEnveloppe(v:SetVar,x:integer) [] */
ClaireBoolean * choco_isInDomainEnveloppe_SetVar(SetVar *v,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))),((int) x))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isInDomainKernel(v:SetVar,x:integer) [] */
ClaireBoolean * choco_isInDomainKernel_SetVar(SetVar *v,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->setBucket)))),((int) x))));
    GC_UNBIND; return (Result);} 
  } 


// v can be instantiated to l iff 
//   - all values from l are in the enveloppe
//   - no value from the kernel is out of l
/* The c++ function for: canBeInstantiatedTo(v:SetVar,l:(set[integer] U list[integer])) [] */
ClaireBoolean * choco_canBeInstantiatedTo_SetVar_choco(SetVar *v,bag *l)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { AbstractSetDomain * svb = v->setBucket;
      { ClaireBoolean *v_and;
        { { OID  g0142UU;
            { OID gc_local;
              ITERATE(x);
              g0142UU= _oid_(CFALSE);
              for (START(l); NEXT(x);)
              { GC_LOOP;
                if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svb),((int) x)))) == CTRUE)
                 { g0142UU = Kernel.ctrue;
                  break;} 
                GC_UNLOOP;} 
              } 
            v_and = not_any(g0142UU);
            } 
          if (v_and == CFALSE) Result =CFALSE; 
          else { { OID  g0143UU;
              { OID gc_local;
                ITERATE(y);
                g0143UU= _oid_(CFALSE);
                bag *y_support;
                y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svb)))));
                for (START(y_support); NEXT(y);)
                { GC_LOOP;
                  if (belong_to(y,_oid_(l)) != CTRUE)
                   { g0143UU = Kernel.ctrue;
                    break;} 
                  GC_UNLOOP;} 
                } 
              v_and = not_any(g0143UU);
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1 can be equal to v2 iff
//    all values from the kernel of v1 are in the enveloppe of v2 and conversely   
/* The c++ function for: canBeEqualTo(v1:SetVar,v2:SetVar) [] */
ClaireBoolean * choco_canBeEqualTo_SetVar_choco(SetVar *v1,SetVar *v2)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { AbstractSetDomain * svb1 = v1->setBucket;
      AbstractSetDomain * svb2 = v2->setBucket;
      { ClaireBoolean *v_and;
        { { OID  g0144UU;
            { OID gc_local;
              ITERATE(x);
              g0144UU= _oid_(CFALSE);
              bag *x_support;
              x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svb1)))));
              for (START(x_support); NEXT(x);)
              { GC_LOOP;
                if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svb2),((int) x)))) == CTRUE)
                 { g0144UU = Kernel.ctrue;
                  break;} 
                GC_UNLOOP;} 
              } 
            v_and = not_any(g0144UU);
            } 
          if (v_and == CFALSE) Result =CFALSE; 
          else { { OID  g0145UU;
              { OID gc_local;
                ITERATE(y);
                g0145UU= _oid_(CFALSE);
                bag *y_support;
                y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.getKernel->fcall(((int) svb2)))));
                for (START(y_support); NEXT(y);)
                { GC_LOOP;
                  if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) svb1),((int) y)))) == CTRUE)
                   { g0145UU = Kernel.ctrue;
                    break;} 
                  GC_UNLOOP;} 
                } 
              v_and = not_any(g0145UU);
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: addSetVar(p:Problem,v:SetVar) [] */
void  choco_addSetVar_Problem(Problem *p,SetVar *v)
{ GC_BIND;
  GC_OBJECT(list,p->setVars)->addFast(_oid_(v));
  (v->problem = p);
  if ((p->vars->length+p->setVars->length) == (p->propagationEngine->maxSize+1))
   princ_string("Watchout: the problem size is too small: risk of event queue overflows");
  GC_UNBIND;} 


/* The c++ function for: closeSetVar(v:SetVar,i:integer,j:integer) [] */
void  choco_closeSetVar_SetVar(SetVar *v,int i,int j)
{ GC_BIND;
  { SetVar * g0146; 
    IncKer * g0147;
    g0146 = v;
    { IncKer * _CL_obj = ((IncKer *) GC_OBJECT(IncKer,new_object_class(choco._IncKer)));
      (_CL_obj->modifiedVar = v);
      g0147 = _CL_obj;
      } 
    (g0146->updtKerEvt = g0147);} 
  { SetVar * g0148; 
    DecEnv * g0149;
    g0148 = v;
    { DecEnv * _CL_obj = ((DecEnv *) GC_OBJECT(DecEnv,new_object_class(choco._DecEnv)));
      (_CL_obj->modifiedVar = v);
      g0149 = _CL_obj;
      } 
    (g0148->updtEnvEvt = g0149);} 
  { SetVar * g0150; 
    InstSet * g0151;
    g0150 = v;
    { InstSet * _CL_obj = ((InstSet *) GC_OBJECT(InstSet,new_object_class(choco._InstSet)));
      (_CL_obj->modifiedVar = v);
      g0151 = _CL_obj;
      } 
    (g0150->instantiateEvt = g0151);} 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 5: Generating events from SetVars                         *
// ********************************************************************
/* The c++ function for: addToKernel(v:SetVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_addToKernel_SetVar(SetVar *v,int x,int idx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    { AbstractSetDomain * sbu = GC_OBJECT(AbstractSetDomain,v->setBucket);
      if (not_any(_oid_((ClaireObject *) choco.isInEnveloppe->fcall(((int) sbu),((int) x)))) == CTRUE)
       { choco_raiseContradiction_AbstractVar(v);
        Result = CTRUE;
        } 
      else if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.updateKernel->fcall(((int) sbu),((int) x))))) == CTRUE)
       { if (GC_OID(choco.getKernelSize->fcall(((int) sbu))) == GC_OID(choco.getEnveloppeSize->fcall(((int) sbu))))
         (*choco.postInstSet)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          0);
        else (*choco.postUpdateKer)(GC_OID(_oid_(v->problem->propagationEngine)),
            _oid_(v),
            idx);
          Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: remFromEnveloppe(v:SetVar,x:integer,idx:integer) [] */
ClaireBoolean * choco_remFromEnveloppe_SetVar(SetVar *v,int x,int idx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    { AbstractSetDomain * sbu = GC_OBJECT(AbstractSetDomain,v->setBucket);
      if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInKernel->fcall(((int) sbu),((int) x))))) == CTRUE)
       { choco_raiseContradiction_AbstractVar(v);
        Result = CTRUE;
        } 
      else if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.updateEnveloppe->fcall(((int) sbu),((int) x))))) == CTRUE)
       { if (GC_OID(choco.getKernelSize->fcall(((int) sbu))) == GC_OID(choco.getEnveloppeSize->fcall(((int) sbu))))
         (*choco.postInstSet)(GC_OID(_oid_(v->problem->propagationEngine)),
          _oid_(v),
          0);
        else (*choco.postUpdateEnv)(GC_OID(_oid_(v->problem->propagationEngine)),
            _oid_(v),
            idx);
          Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    GC_UNBIND; return (Result);} 
  } 


// TODO add the event calling postInstSet                  
/* The c++ function for: setIn(v:SetVar,x:integer) [] */
void  choco_setIn_SetVar(SetVar *v,int x)
{ choco_addToKernel_SetVar(v,x,0);
  } 


/* The c++ function for: setOut(v:SetVar,x:integer) [] */
void  choco_setOut_SetVar(SetVar *v,int x)
{ choco_remFromEnveloppe_SetVar(v,x,0);
  } 

