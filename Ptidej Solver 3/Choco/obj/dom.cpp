/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\dom.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:25 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: dom.cl                                                     *
// *    encoding variable domains                                     *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: abstract IntVar domains                                *
// *   Part 2: implementing IntVar domains by enumerations of values  *
// *   Part 3: abstract SetVar domains                                *
// *   Part 4: implementing SetVar domains by bitvectors              *
// *   Part 5: implementing SetVar domains by bitvector lists         *
// --------------------------------------------------------------------
// Abstract class for domain implementations, no interface methods
// v1.011: required in order to be able to iterate the object
// Abstract class for domain implementations of integer variables
// Interface of AbstractIntDomain documented in file iprop.cl
// ********************************************************************
// *   Part 1: abstract IntVar domains                                *
// ********************************************************************
// Interface of AbstractIntDomain:
//   the following methods should be defined on subclasses XXXIntDomain of AbstractIntDomain:
//     getDomainCard(d:XXXIntDomain) : integer
//     restrict(d:XXXIntDomain,s:set[integer]):void
//     restrict(d:XXXIntDomain,x:integer):void
//     removeDomainVal(d:XXXIntDomain,x:integer):void
//     updateDomainInf(d:XXXIntDomain,x:integer):void
//     updateDomainSup(d:XXXIntDomain,x:integer):void
//     getDomainInf(d:XXXIntDomain):integer
//     getDomainSup(d:XXXIntDomain):integer
//     containsValInDomain(d:XXXIntDomain,x:integer):boolean
// v1.04
/* The c++ function for: domainSequence(d:AbstractIntDomain) [] */
list * choco_domainSequence_AbstractIntDomain_choco(AbstractIntDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the domainSequence method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (list::empty(Kernel._integer));} 


/* The c++ function for: domainSet(d:AbstractIntDomain) [] */
set * choco_domainSet_AbstractIntDomain_choco(AbstractIntDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the domainSequence method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (set::empty(Kernel._integer));} 


/* The c++ function for: getDomainInf(d:AbstractIntDomain) [] */
int  choco_getDomainInf_AbstractIntDomain_choco(AbstractIntDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the getDomainInf method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: getDomainSup(d:AbstractIntDomain) [] */
int  choco_getDomainSup_AbstractIntDomain_choco(AbstractIntDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the getDomainSup method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: updateDomainInf(d:AbstractIntDomain,x:integer) [] */
int  choco_updateDomainInf_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the updateDomainInf method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: updateDomainSup(d:AbstractIntDomain,x:integer) [] */
int  choco_updateDomainSup_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the updateDomainSup method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: containsValInDomain(d:AbstractIntDomain,x:integer) [] */
ClaireBoolean * choco_containsValInDomain_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the containsValInDomain method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


/* The c++ function for: removeDomainVal(d:AbstractIntDomain,x:integer) [] */
ClaireBoolean * choco_removeDomainVal_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the removeDomainVal method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


/* The c++ function for: restrict(d:AbstractIntDomain,x:integer) [] */
void  choco_restrict_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the restrict method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  } 


/* The c++ function for: getDomainCard(d:AbstractIntDomain) [] */
int  choco_getDomainCard_AbstractIntDomain_choco(AbstractIntDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the getDomainCard method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (1);} 


/* The c++ function for: getNextValue(d:AbstractIntDomain,x:integer) [] */
int  choco_getNextValue_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the getNextValue method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


// v1.016 from franck:
/* The c++ function for: getPrevValue(d:AbstractIntDomain,x:integer) [] */
int  choco_getPrevValue_AbstractIntDomain_choco(AbstractIntDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("the getPrevValue method has not been implemented on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


// ********************************************************************
// *   Part 2: implementing IntVar domains by enumerations of values  *
// ********************************************************************
// Interface of domains
// An encoding of enumerated domains as linked lists
// we use an array of pointer indices: contents
//   contents[i] = i      <=> i is a possible value (OK)
//   contents[i] = j > i  <=> i, i+1, ..., j-1 non-OK, j probably OK
//   contents[i]  = MAXINT  <=> i, ..., dim non-OK
//
// v1.02 <ebo>, <fxj>, v1.08 <fl> lighter printing
/* The c++ function for: self_print(x:LinkedListIntDomain) [] */
void  claire_self_print_LinkedListIntDomain_choco(LinkedListIntDomain *x)
{ GC_BIND;
  princ_string("[");
  print_any(x->bucketSize);
  princ_string("]");
  { set * s = GC_OBJECT(set,OBJECT(set,_oid_((ClaireObject *) choco.domainSet->fcall(((int) x)))));
    int  si = size_set(s);
    if (si <= 4)
     print_any(_oid_(s));
    else { princ_string("{");
        print_any((*(s))[1]);
        princ_string(",");
        print_any((*(s))[2]);
        princ_string("...");
        print_any((*(s))[(si-1)]);
        princ_string(",");
        print_any((*(s))[si]);
        princ_string("}");
        } 
      } 
  princ_string("");
  GC_UNBIND;} 


// automatically called when
/* The c++ function for: makeLinkedListIntDomain(a:integer,b:integer) [] */
LinkedListIntDomain * choco_makeLinkedListIntDomain_integer(int a,int b)
{ GC_BIND;
  ;;{ LinkedListIntDomain *Result ;
    { int  n = ((b-a)+1);
      LinkedListIntDomain * _CL_obj = ((LinkedListIntDomain *) GC_OBJECT(LinkedListIntDomain,new_object_class(choco._LinkedListIntDomain)));
      STOREI(_CL_obj->bucketSize,n);
      (_CL_obj->offset = (a-1));
      { LinkedListIntDomain * g0027; 
        OID * g0028;
        g0027 = _CL_obj;
        { list * g0029UU;
          { list * g0030UU;
            { list * i_bag = list::empty(Kernel._integer);
              { int  i = 1;
                int  g0026 = n;
                { OID gc_local;
                  while ((i <= g0026))
                  { // HOHO, GC_LOOP not needed !
                    i_bag->addFast(i);
                    ++i;
                    } 
                  } 
                } 
              g0030UU = GC_OBJECT(list,i_bag);
              } 
            g0029UU = g0030UU->addFast(99999999);
            } 
          g0028 = array_I_list(g0029UU);
          } 
        (g0027->contents = g0028);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.0, now an array
// claire3 port use array only on strongly typed lists
// a first utility
/* The c++ function for: random(d:LinkedListIntDomain) [] */
int  claire_random_LinkedListIntDomain(LinkedListIntDomain *d)
{ { int Result = 0;
    { OID * l = d->contents;
      int  i = (1+random_integer(l[0]));
      { while ((((OID *) l)[i] != i))
        { i= (1+random_integer(l[0]));
          } 
        } 
      Result = (i+d->offset);
      } 
    return (Result);} 
  } 


// Implementing the interface from AbstractDomain: primitives for iteration
// v1.04
/* The c++ function for: domainSet(d:LinkedListIntDomain) [] */
set * choco_domainSet_LinkedListIntDomain_choco(LinkedListIntDomain *d)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { set *Result ;
    { set * s = set::empty(Kernel._integer);
      OID * l = d->contents;
      int  i = ((OID *) l)[1];
      { OID gc_local;
        while ((i != 99999999))
        { GC_LOOP;
          GC__ANY(s = s->addFast((i+d->offset)), 1);
          i= ((OID *) l)[(i+1)];
          GC_UNLOOP;} 
        } 
      Result = s;
      } 
    GC_UNBIND; return (Result);} 
  } 


// this is necessarliy sorted by increasing order of values
// v1.04
/* The c++ function for: domainSequence(d:LinkedListIntDomain) [] */
list * choco_domainSequence_LinkedListIntDomain_choco(LinkedListIntDomain *d)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list *Result ;
    { list * s = list::empty(Kernel._integer);
      OID * l = d->contents;
      int  i = ((OID *) l)[1];
      { OID gc_local;
        while ((i != 99999999))
        { GC_LOOP;
          GC__ANY(s = s->addFast((i+d->offset)), 1);
          i= ((OID *) l)[(i+1)];
          GC_UNLOOP;} 
        } 
      Result = s;
      } 
    GC_UNBIND; return (Result);} 
  } 


// Implementing the interface from AbstractIntDomain
// Accessors
/* The c++ function for: getDomainInf(d:LinkedListIntDomain) [] */
int  choco_getDomainInf_LinkedListIntDomain_choco(LinkedListIntDomain *d)
{ { int Result = 0;
    { OID * l = d->contents;
      Result = ((((OID *) l)[1])+d->offset);
      } 
    return (Result);} 
  } 


/* The c++ function for: getDomainSup(d:LinkedListIntDomain) [] */
int  choco_getDomainSup_LinkedListIntDomain_choco(LinkedListIntDomain *d)
{ { int Result = 0;
    { OID * l = d->contents;
      int  i = (l[0]-1);
      { while ((((OID *) l)[i] == 99999999))
        { i= (i-1);
          } 
        } 
      Result = ((((OID *) l)[i])+d->offset);
      } 
    return (Result);} 
  } 


// v0.9901: submethod of domainIncludedIn
// supposed that [v.inf,v.sup] is included in [l[1],last(l)]     
// v0.9907 new name
/* The c++ function for: isIncludedIn(b:LinkedListIntDomain,l:list[integer]) [] */
ClaireBoolean * choco_isIncludedIn_LinkedListIntDomain(LinkedListIntDomain *b,list *l)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0031UU;
      { OID gc_local;
        ITERATE(x);
        g0031UU= _oid_(CFALSE);
        bag *x_support;
        x_support = GC_OBJECT(bag,enumerate_any(_oid_(b)));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if (contain_ask_list(l,x) != CTRUE)
           { g0031UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = not_any(g0031UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907
/* The c++ function for: getDomainCard(d:LinkedListIntDomain) [] */
int  choco_getDomainCard_LinkedListIntDomain_choco(LinkedListIntDomain *d)
{ return (d->bucketSize);} 


/* The c++ function for: containsValInDomain(d:LinkedListIntDomain,x:integer) [] */
ClaireBoolean * choco_containsValInDomain_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { ClaireBoolean *Result ;
    { OID * l = d->contents;
      int  i = (x-d->offset);
      Result = ((((OID *) l)[i] == i) ? CTRUE : CFALSE);
      } 
    return (Result);} 
  } 


// v1.013
/* The c++ function for: getNextValue(d:LinkedListIntDomain,x:integer) [] */
int  choco_getNextValue_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { int Result = 0;
    { OID * l = d->contents;
      int  o = d->offset;
      int  i = (x-o);
      Result = ((((OID *) l)[(i+1)])+o);
      } 
    return (Result);} 
  } 


// v1.016 from franck:
/* The c++ function for: getPrevValue(d:LinkedListIntDomain,x:integer) [] */
int  choco_getPrevValue_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { int Result = 0;
    { OID * l = d->contents;
      int  o = d->offset;
      int  i = (x-o);
      i= (i-1);
      { while ((((OID *) l)[i] > i))
        { i= (i-1);
          } 
        } 
      Result = ((((OID *) l)[i])+o);
      } 
    return (Result);} 
  } 


// Modifiers (update functions)
/* The c++ function for: removeDomainVal(d:LinkedListIntDomain,x:integer) [] */
ClaireBoolean * choco_removeDomainVal_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { ClaireBoolean *Result ;
    { OID * l = d->contents;
      int  i = (x-d->offset);
      if (((OID *) l)[i] == i)
       { int  k = ((OID *) l)[(i+1)];
        int  j = (i-1);
        STOREI(d->bucketSize,(d->bucketSize-1));
        STOREI(l[i],k);
        { while (((1 <= j) && 
              (((OID *) l)[j] == i)))
          { STOREI(l[j],k);
            j= (j-1);
            } 
          } 
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 


/* The c++ function for: restrict(d:LinkedListIntDomain,x:integer) [] */
void  choco_restrict_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { OID * l = d->contents;
    int  i = (x-d->offset);
    { int  j = 1;
      int  g0032 = (i-1);
      { while ((j <= g0032))
        { STOREI(l[j],i);
          ++j;
          } 
        } 
      } 
    { int  j = (i+1);
      int  g0033 = (l[0]-1);
      { while ((j <= g0033))
        { STOREI(l[j],99999999);
          ++j;
          } 
        } 
      } 
    STOREI(d->bucketSize,1);
    ;} 
  } 


// returns the new value of the lower bound (at least x)
/* The c++ function for: updateDomainInf(d:LinkedListIntDomain,x:integer) [] */
int  choco_updateDomainInf_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { int Result = 0;
    { OID * l = d->contents;
      int  i = (x-d->offset);
      { int  i0 = ((OID *) l)[i];
        { int  j = 1;
          { while ((((OID *) l)[j] != i0))
            { if (((OID *) l)[j] == j)
               STOREI(d->bucketSize,(d->bucketSize-1));
              STOREI(l[j],i0);
              ++j;
              } 
            } 
          } 
        Result = (i0+d->offset);
        } 
      } 
    return (Result);} 
  } 


// returns the new value of the upper bound (at most x)
/* The c++ function for: updateDomainSup(d:LinkedListIntDomain,x:integer) [] */
int  choco_updateDomainSup_LinkedListIntDomain_choco(LinkedListIntDomain *d,int x)
{ { int Result = 0;
    { OID * l = d->contents;
      int  i = (x-d->offset);
      { int  j = (i+1);
        { while ((((OID *) l)[j] != 99999999))
          { if (((OID *) l)[j] == j)
             STOREI(d->bucketSize,(d->bucketSize-1));
            STOREI(l[j],99999999);
            ++j;
            } 
          } 
        } 
      { int  j = i;
        { while ((((OID *) l)[j] != j))
          { STOREI(l[j],99999999);
            j= (j-1);
            } 
          } 
        Result = (j+d->offset);
        } 
      } 
    return (Result);} 
  } 


// ********************************************************************
// *   Part 3: abstract SetVar domains                                *
// ********************************************************************
// Domains for set variables have two bounds:
//   a lower bound called the kernel: set of values that are for sure in the set variable
//                                      (intersection of all possible sets)
//   an upper bound called the enveloppe: set of values that may be in the set variable
//                                      (union of all possible sets)
/* The c++ function for: getKernel(d:AbstractSetDomain) [] */
list * choco_getKernel_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getKernel not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (list::empty(Kernel._integer));} 


/* The c++ function for: getEnveloppe(d:AbstractSetDomain) [] */
list * choco_getEnveloppe_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getEnveloppe not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (list::empty(Kernel._integer));} 


/* The c++ function for: getKernelSize(d:AbstractSetDomain) [] */
int  choco_getKernelSize_AbstractSetDomain(AbstractSetDomain *d)
{ return (d->kernelSize);} 


/* The c++ function for: getEnveloppeSize(d:AbstractSetDomain) [] */
int  choco_getEnveloppeSize_AbstractSetDomain(AbstractSetDomain *d)
{ return (d->enveloppeSize);} 


/* The c++ function for: getKernelInf(d:AbstractSetDomain) [] */
int  choco_getKernelInf_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getKernelInf not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: getKernelSup(d:AbstractSetDomain) [] */
int  choco_getKernelSup_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getKernelSup not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: getEnveloppeInf(d:AbstractSetDomain) [] */
int  choco_getEnveloppeInf_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getEnveloppeInf not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: getEnveloppeSup(d:AbstractSetDomain) [] */
int  choco_getEnveloppeSup_AbstractSetDomain(AbstractSetDomain *d)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getEnveloppeSup not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (0);} 


/* The c++ function for: isInEnveloppe(d:AbstractSetDomain,x:integer) [] */
ClaireBoolean * choco_isInEnveloppe_AbstractSetDomain(AbstractSetDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("isInEnveloppe not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


/* The c++ function for: isInKernel(d:AbstractSetDomain,x:integer) [] */
ClaireBoolean * choco_isInKernel_AbstractSetDomain(AbstractSetDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("isInKernel not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


/* The c++ function for: updateKernel(d:AbstractSetDomain,x:integer) [] */
ClaireBoolean * choco_updateKernel_AbstractSetDomain(AbstractSetDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("updateKernel not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


/* The c++ function for: updateEnveloppe(d:AbstractSetDomain,x:integer) [] */
ClaireBoolean * choco_updateEnveloppe_AbstractSetDomain(AbstractSetDomain *d,int x)
{ close_exception(((general_error *) (*Core._general_error)(_string_("updateKernel not defined on ~S"),
    _oid_(list::alloc(1,_oid_(d))))));
  return (CTRUE);} 


// ********************************************************************
// *   Part 4: implementing SetVar domains by bitvectors              *
// ********************************************************************
// An encoding with integers taken as bitvectors 
// bitvetor[idx] tells if idx + minValue is in the domain
//               this is valid for 0<=idx<=29
/* The c++ function for: getKernel(d:BitSetDomain) [] */
list * choco_getKernel_BitSetDomain(BitSetDomain *d)
{ GC_BIND;
  { list *Result ;
    { bag *v_list; OID v_val;
      OID i,CLcount;
      { list * i_out = list::empty(Kernel._integer);
        { int  i = 0;
          int  g0034 = 29;
          { OID gc_local;
            while ((i <= g0034))
            { // HOHO, GC_LOOP not needed !
              if (BCONTAIN(d->kernelBitVector,i))
               i_out->addFast(i);
              ++i;
              } 
            } 
          } 
        v_list = GC_OBJECT(list,i_out);
        } 
       Result = v_list->clone(Kernel._integer);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { i = (*(v_list))[CLcount];
        v_val = (i+d->minValue);
        
        (*((list *) Result))[CLcount] = v_val;} 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getEnveloppe(d:BitSetDomain) [] */
list * choco_getEnveloppe_BitSetDomain(BitSetDomain *d)
{ GC_BIND;
  { list *Result ;
    { bag *v_list; OID v_val;
      OID i,CLcount;
      { list * i_out = list::empty(Kernel._integer);
        { int  i = 0;
          int  g0035 = 29;
          { OID gc_local;
            while ((i <= g0035))
            { // HOHO, GC_LOOP not needed !
              if (BCONTAIN(d->enveloppeBitVector,i))
               i_out->addFast(i);
              ++i;
              } 
            } 
          } 
        v_list = GC_OBJECT(list,i_out);
        } 
       Result = v_list->clone(Kernel._integer);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { i = (*(v_list))[CLcount];
        v_val = (i+d->minValue);
        
        (*((list *) Result))[CLcount] = v_val;} 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getKernelSize(d:BitSetDomain) [] */
int  choco_getKernelSize_BitSetDomain(BitSetDomain *d)
{ ;return (d->kernelSize);} 


/* The c++ function for: getEnveloppeSize(d:BitSetDomain) [] */
int  choco_getEnveloppeSize_BitSetDomain(BitSetDomain *d)
{ ;return (d->enveloppeSize);} 


/* The c++ function for: getKernelInf(d:BitSetDomain) [] */
int  choco_getKernelInf_BitSetDomain(BitSetDomain *d)
{ { int Result = 0;
    { int  g0039UU;
      { int  g0036 = 99999999;
        { int  g0037 = 0;
          int  g0038 = 29;
          { while ((g0037 <= g0038))
            { if (BCONTAIN(d->kernelBitVector,g0037))
               g0036= ((g0037 <= g0036) ?
                g0037 :
                g0036 );
              ++g0037;
              } 
            } 
          } 
        g0039UU = g0036;
        } 
      Result = (d->minValue+g0039UU);
      } 
    return (Result);} 
  } 


/* The c++ function for: getKernelSup(d:BitSetDomain) [] */
int  choco_getKernelSup_BitSetDomain(BitSetDomain *d)
{ { int Result = 0;
    { int  g0043UU;
      { int  g0040 = -99999999;
        { int  g0041 = 0;
          int  g0042 = 29;
          { while ((g0041 <= g0042))
            { if (BCONTAIN(d->kernelBitVector,g0041))
               g0040= ((g0041 <= g0040) ?
                g0040 :
                g0041 );
              ++g0041;
              } 
            } 
          } 
        g0043UU = g0040;
        } 
      Result = (d->minValue+g0043UU);
      } 
    return (Result);} 
  } 


/* The c++ function for: getEnveloppeInf(d:BitSetDomain) [] */
int  choco_getEnveloppeInf_BitSetDomain(BitSetDomain *d)
{ { int Result = 0;
    { int  g0047UU;
      { int  g0044 = 99999999;
        { int  g0045 = 0;
          int  g0046 = 29;
          { while ((g0045 <= g0046))
            { if (BCONTAIN(d->enveloppeBitVector,g0045))
               g0044= ((g0045 <= g0044) ?
                g0045 :
                g0044 );
              ++g0045;
              } 
            } 
          } 
        g0047UU = g0044;
        } 
      Result = (d->minValue+g0047UU);
      } 
    return (Result);} 
  } 


/* The c++ function for: getEnveloppeSup(d:BitSetDomain) [] */
int  choco_getEnveloppeSup_BitSetDomain(BitSetDomain *d)
{ { int Result = 0;
    { int  g0051UU;
      { int  g0048 = -99999999;
        { int  g0049 = 0;
          int  g0050 = 29;
          { while ((g0049 <= g0050))
            { if (BCONTAIN(d->enveloppeBitVector,g0049))
               g0048= ((g0049 <= g0048) ?
                g0048 :
                g0049 );
              ++g0049;
              } 
            } 
          } 
        g0051UU = g0048;
        } 
      Result = (d->minValue+g0051UU);
      } 
    return (Result);} 
  } 


/* The c++ function for: isInEnveloppe(d:BitSetDomain,x:integer) [] */
ClaireBoolean * choco_isInEnveloppe_BitSetDomain(BitSetDomain *d,int x)
{ return (nth_integer(d->enveloppeBitVector,(x-d->minValue)));} 


/* The c++ function for: isInKernel(d:BitSetDomain,x:integer) [] */
ClaireBoolean * choco_isInKernel_BitSetDomain(BitSetDomain *d,int x)
{ return (nth_integer(d->kernelBitVector,(x-d->minValue)));} 


/* The c++ function for: makeBitSetDomain(i:integer,j:integer) [] */
BitSetDomain * choco_makeBitSetDomain_integer(int i,int j)
{ GC_BIND;
  { BitSetDomain *Result ;
    { int  nbvals = ((j-i)+1);
      int  allOne = ((exp2_integer((nbvals-1))-1)+exp2_integer((nbvals-1)));
      BitSetDomain * _CL_obj = ((BitSetDomain *) GC_OBJECT(BitSetDomain,new_object_class(choco._BitSetDomain)));
      (_CL_obj->minValue = i);
      STOREI(_CL_obj->kernelSize,0);
      STOREI(_CL_obj->enveloppeSize,nbvals);
      STOREI(_CL_obj->kernelBitVector,0);
      STOREI(_CL_obj->enveloppeBitVector,allOne);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: updateKernel(d:BitSetDomain,x:integer) [] */
ClaireBoolean * choco_updateKernel_BitSetDomain(BitSetDomain *d,int x)
{ { ClaireBoolean *Result ;
    { int  idx = (x-d->minValue);
      int  deltaBitVector = exp2_integer(idx);
      if (!BCONTAIN(d->kernelBitVector,idx))
       { STOREI(d->kernelSize,(d->kernelSize+1));
        STOREI(d->kernelBitVector,(d->kernelBitVector+deltaBitVector));
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 


/* The c++ function for: updateEnveloppe(d:BitSetDomain,x:integer) [] */
ClaireBoolean * choco_updateEnveloppe_BitSetDomain(BitSetDomain *d,int x)
{ { ClaireBoolean *Result ;
    { int  idx = (x-d->minValue);
      int  deltaBitVector = exp2_integer(idx);
      if (BCONTAIN(d->enveloppeBitVector,idx))
       { STOREI(d->enveloppeSize,(d->enveloppeSize-1));
        STOREI(d->enveloppeBitVector,(d->enveloppeBitVector-deltaBitVector));
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 


// ********************************************************************
// *   Part 5: implementing SetVar domains by bitvector lists         *
// ********************************************************************
// An encoding with a list of integers taken as bitvectors 
// bitvetor[word][idx] tells if (minValue + 30(word-1) + idx) is in the domain
//   this is valid for 0<=word<=length(bitvector), 0<=idx<=29
//        x = minv + 30(w-1) + idx
//        idx = (x - minv) mod 30
//        w   = (x - minv) / 30 + 1 
// updates to kernelBitVectorList and enveloppeBitVectorList are backtrackable.
/* The c++ function for: makeBitListSetDomain(i:integer,j:integer) [] */
BitListSetDomain * choco_makeBitListSetDomain_integer(int i,int j)
{ GC_BIND;
  { BitListSetDomain *Result ;
    { int  nbvals = ((j-i)+1);
      int  nbwords = choco_div_plus_integer(nbvals,30);
      int  all1 = choco_makeAllOnesBitVector_integer(30);
      int  nbAdditionalBits = mod_integer(nbvals,30);
      BitListSetDomain * _CL_obj = ((BitListSetDomain *) GC_OBJECT(BitListSetDomain,new_object_class(choco._BitListSetDomain)));
      (_CL_obj->minValue = i);
      (_CL_obj->kernelBitVectorList = make_list_integer2(nbwords,Kernel._integer,0));
      STOREI(_CL_obj->kernelSize,0);
      STOREI(_CL_obj->enveloppeSize,nbvals);
      (_CL_obj->enveloppeBitVectorList = ((nbAdditionalBits > 0) ?
        GC_OBJECT(list,make_list_integer2((nbwords-1),Kernel._integer,all1))->addFast(choco_makeAllOnesBitVector_integer(nbAdditionalBits)) :
        make_list_integer2(nbwords,Kernel._integer,all1) ));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getKernel(d:BitListSetDomain) [] */
list * choco_getKernel_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { list *Result ;
    Result = choco_getBitVectorList_integer(d->minValue,GC_OBJECT(list,d->kernelBitVectorList));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getEnveloppe(d:BitListSetDomain) [] */
list * choco_getEnveloppe_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { list *Result ;
    Result = choco_getBitVectorList_integer(d->minValue,GC_OBJECT(list,d->enveloppeBitVectorList));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getKernelSize(d:BitListSetDomain) [] */
int  choco_getKernelSize_BitListSetDomain(BitListSetDomain *d)
{ ;return (d->kernelSize);} 


/* The c++ function for: getEnveloppeSize(d:BitListSetDomain) [] */
int  choco_getEnveloppeSize_BitListSetDomain(BitListSetDomain *d)
{ ;return (d->enveloppeSize);} 


/* The c++ function for: getKernelInf(d:BitListSetDomain) [] */
int  choco_getKernelInf_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { int Result = 0;
    Result = (d->minValue+choco_getBitVectorInf_list(GC_OBJECT(list,d->kernelBitVectorList)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getKernelSup(d:BitListSetDomain) [] */
int  choco_getKernelSup_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { int Result = 0;
    Result = (d->minValue+choco_getBitVectorSup_list(GC_OBJECT(list,d->kernelBitVectorList)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getEnveloppeInf(d:BitListSetDomain) [] */
int  choco_getEnveloppeInf_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { int Result = 0;
    Result = (d->minValue+choco_getBitVectorInf_list(GC_OBJECT(list,d->enveloppeBitVectorList)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getEnveloppeSup(d:BitListSetDomain) [] */
int  choco_getEnveloppeSup_BitListSetDomain(BitListSetDomain *d)
{ GC_BIND;
  { int Result = 0;
    Result = (d->minValue+choco_getBitVectorSup_list(GC_OBJECT(list,d->enveloppeBitVectorList)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isInEnveloppe(d:BitListSetDomain,x:integer) [] */
ClaireBoolean * choco_isInEnveloppe_BitListSetDomain(BitListSetDomain *d,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = choco_isInBitVectorList_integer((x-d->minValue),GC_OBJECT(list,d->enveloppeBitVectorList));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: isInKernel(d:BitListSetDomain,x:integer) [] */
ClaireBoolean * choco_isInKernel_BitListSetDomain(BitListSetDomain *d,int x)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = choco_isInBitVectorList_integer((x-d->minValue),GC_OBJECT(list,d->kernelBitVectorList));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: updateKernel(d:BitListSetDomain,x:integer) [] */
ClaireBoolean * choco_updateKernel_BitListSetDomain(BitListSetDomain *d,int x)
{ if (choco_addToBitVectorList_integer((x-d->minValue),GC_OBJECT(list,d->kernelBitVectorList)) == CTRUE) 
  { { ClaireBoolean *Result ;
      { STOREI(d->kernelSize,(d->kernelSize+1));
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


/* The c++ function for: updateEnveloppe(d:BitListSetDomain,x:integer) [] */
ClaireBoolean * choco_updateEnveloppe_BitListSetDomain(BitListSetDomain *d,int x)
{ if (choco_remFromBitVectorList_integer((x-d->minValue),GC_OBJECT(list,d->enveloppeBitVectorList)) == CTRUE) 
  { { ClaireBoolean *Result ;
      { STOREI(d->enveloppeSize,(d->enveloppeSize-1));
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


// TODO: définir des iterateurs sur getEnveloppe et getKernel pour les deux implementations de domaines