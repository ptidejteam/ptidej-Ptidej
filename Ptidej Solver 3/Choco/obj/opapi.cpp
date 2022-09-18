/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\opapi.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:32 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: opapi.cl                                                   *
// *    API (public methods) using operators for stating constraints  *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 4: Arithmetic constraints                                 *
// *   Part 5: Boolean connectors                                     *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 4: Arithmetic constraints                                 *
// ********************************************************************
// v1.04 the return type of all arithmetic methods is now a subtype of IntConstraint
// Full API for arithmetic comparisons
// Simple unary operators
/* The c++ function for: >=(v:IntVar,x:integer) [] */
GreaterOrEqualxc * claire__sup_equal_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { GreaterOrEqualxc *Result ;
    { GreaterOrEqualxc * _CL_obj = ((GreaterOrEqualxc *) GC_OBJECT(GreaterOrEqualxc,new_object_class(choco._GreaterOrEqualxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = x);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(v:IntVar,x:integer) [] */
GreaterOrEqualxc * claire__sup_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { GreaterOrEqualxc *Result ;
    { GreaterOrEqualxc * _CL_obj = ((GreaterOrEqualxc *) GC_OBJECT(GreaterOrEqualxc,new_object_class(choco._GreaterOrEqualxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = (x+1));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <=(v:IntVar,x:integer) [] */
LessOrEqualxc * claire__inf_equal_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { LessOrEqualxc *Result ;
    { LessOrEqualxc * _CL_obj = ((LessOrEqualxc *) GC_OBJECT(LessOrEqualxc,new_object_class(choco._LessOrEqualxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = x);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(v:IntVar,x:integer) [] */
LessOrEqualxc * claire__inf_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { LessOrEqualxc *Result ;
    { LessOrEqualxc * _CL_obj = ((LessOrEqualxc *) GC_OBJECT(LessOrEqualxc,new_object_class(choco._LessOrEqualxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = (x-1));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(v:IntVar,x:integer) [] */
Equalxc * claire__equal_equal_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { Equalxc *Result ;
    { Equalxc * _CL_obj = ((Equalxc *) GC_OBJECT(Equalxc,new_object_class(choco._Equalxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = x);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(v:IntVar,x:integer) [] */
NotEqualxc * claire__I_equal_equal_IntVar1(IntVar *v,int x)
{ GC_BIND;
  { NotEqualxc *Result ;
    { NotEqualxc * _CL_obj = ((NotEqualxc *) GC_OBJECT(NotEqualxc,new_object_class(choco._NotEqualxc)));
      (_CL_obj->v1 = v);
      (_CL_obj->cste = x);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:integer,v:IntVar) [] */
LessOrEqualxc * claire__sup_equal_integer1(int x,IntVar *v)
{ return (claire__inf_equal_IntVar1(v,x));} 


/* The c++ function for: >(x:integer,v:IntVar) [] */
LessOrEqualxc * claire__sup_integer2(int x,IntVar *v)
{ return (claire__inf_IntVar1(v,x));} 


/* The c++ function for: <=(x:integer,v:IntVar) [] */
GreaterOrEqualxc * claire__inf_equal_integer2(int x,IntVar *v)
{ return (claire__sup_equal_IntVar1(v,x));} 


/* The c++ function for: <(x:integer,v:IntVar) [] */
GreaterOrEqualxc * claire__inf_integer2(int x,IntVar *v)
{ return (claire__sup_IntVar1(v,x));} 


/* The c++ function for: ==(x:integer,v:IntVar) [] */
Equalxc * claire__equal_equal_integer1(int x,IntVar *v)
{ return (claire__equal_equal_IntVar1(v,x));} 


/* The c++ function for: !==(x:integer,v:IntVar) [] */
NotEqualxc * claire__I_equal_equal_integer1(int x,IntVar *v)
{ return (claire__I_equal_equal_IntVar1(v,x));} 


// Simple binary operators
/* The c++ function for: ==(u:IntVar,v:IntVar) [] */
Equalxyc * claire__equal_equal_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { Equalxyc *Result ;
    { Equalxyc * _CL_obj = ((Equalxyc *) GC_OBJECT(Equalxyc,new_object_class(choco._Equalxyc)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(u:IntVar,v:IntVar) [] */
NotEqualxyc * claire__I_equal_equal_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { NotEqualxyc *Result ;
    { NotEqualxyc * _CL_obj = ((NotEqualxyc *) GC_OBJECT(NotEqualxyc,new_object_class(choco._NotEqualxyc)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907
/* The c++ function for: <=(u:IntVar,v:IntVar) [] */
GreaterOrEqualxyc * claire__inf_equal_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { GreaterOrEqualxyc *Result ;
    { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
      (_CL_obj->v1 = v);
      (_CL_obj->v2 = u);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(u:IntVar,v:IntVar) [] */
GreaterOrEqualxyc * claire__sup_equal_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { GreaterOrEqualxyc *Result ;
    { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(u:IntVar,v:IntVar) [] */
GreaterOrEqualxyc * claire__sup_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { GreaterOrEqualxyc *Result ;
    { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      (_CL_obj->cste = 1);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907
/* The c++ function for: <(u:IntVar,v:IntVar) [] */
GreaterOrEqualxyc * claire__inf_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { GreaterOrEqualxyc *Result ;
    { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
      (_CL_obj->v1 = v);
      (_CL_obj->v2 = u);
      (_CL_obj->cste = 1);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


//  UnTerm: a temporary object representing +/-x +c
/* The c++ function for: self_print(t:UnTerm) [] */
OID  claire_self_print_UnTerm_choco(UnTerm *t)
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


//  BinTerm: a temporary object representing +/-x +/-y +c
/* The c++ function for: self_print(t:BinTerm) [] */
OID  claire_self_print_BinTerm_choco(BinTerm *t)
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


/* The c++ function for: sumTerms(l:list[Term]) [] */
Term * choco_sumTerms_list(list *l)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { Term *Result ;
    { Term * t = OBJECT(Term,(*(l))[1]);
      { int  i = 2;
        int  g1297 = l->length;
        { OID gc_local;
          while ((i <= g1297))
          { GC_LOOP;
            GC__ANY(t = OBJECT(Term,(*Core._plus)(_oid_(t),
              (*(l))[i])), 1);
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = t;
      } 
    GC_UNBIND; return (Result);} 
  } 


// building linear terms
/* The c++ function for: *(a:integer,x:IntVar) [] */
LinTerm * claire__star_integer2(int a,IntVar *x)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      (_CL_obj->lcoeff = list::alloc(Kernel._integer,1,a));
      (_CL_obj->lvars = list::alloc(choco._IntVar,1,_oid_(x)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: *(x:IntVar,a:integer) [] */
LinTerm * claire__star_IntVar(IntVar *x,int a)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      (_CL_obj->lcoeff = list::alloc(Kernel._integer,1,a));
      (_CL_obj->lvars = list::alloc(choco._IntVar,1,_oid_(x)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.018
/* The c++ function for: *(a:integer,t:LinTerm) [] */
LinTerm * claire__star_integer3(int a,LinTerm *t)
{ { int  i = 1;
    int  g1298 = t->lvars->length;
    { while ((i <= g1298))
      { ((*(t->lcoeff))[i]=(((*(t->lcoeff))[i])*a));
        ++i;
        } 
      } 
    } 
  (t->cste = (t->cste*a));
  return (t);} 


/* The c++ function for: *(t:LinTerm,a:integer) [] */
LinTerm * claire__star_LinTerm(LinTerm *t,int a)
{ { int  i = 1;
    int  g1299 = t->lvars->length;
    { while ((i <= g1299))
      { ((*(t->lcoeff))[i]=(((*(t->lcoeff))[i])*a));
        ++i;
        } 
      } 
    } 
  return (t);} 


// ----- Addition Operator
/* The c++ function for: +(u:IntVar,c:integer) [] */
UnTerm * claire__plus_IntVar1(IntVar *u,int c)
{ GC_BIND;
  { UnTerm *Result ;
    { UnTerm * _CL_obj = ((UnTerm *) GC_OBJECT(UnTerm,new_object_class(choco._UnTerm)));
      (_CL_obj->v1 = u);
      (_CL_obj->cste = c);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(c:integer,u:IntVar) [] */
UnTerm * claire__plus_integer2(int c,IntVar *u)
{ return (claire__plus_IntVar1(u,c));} 


// v0.36 <thb> wrong return type
/* The c++ function for: +(u:IntVar,v:IntVar) [] */
BinTerm * claire__plus_IntVar2(IntVar *u,IntVar *v)
{ GC_BIND;
  { BinTerm *Result ;
    { BinTerm * _CL_obj = ((BinTerm *) GC_OBJECT(BinTerm,new_object_class(choco._BinTerm)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:UnTerm,c:integer) [] */
UnTerm * claire__plus_UnTerm1(UnTerm *t,int c)
{ (t->cste = (t->cste+c));
  return (t);} 


/* The c++ function for: +(c:integer,t:UnTerm) [] */
UnTerm * claire__plus_integer3(int c,UnTerm *t)
{ return (claire__plus_UnTerm1(t,c));} 


/* The c++ function for: +(t:UnTerm,x:IntVar) [] */
BinTerm * claire__plus_UnTerm2(UnTerm *t,IntVar *x)
{ GC_BIND;
  { BinTerm *Result ;
    { BinTerm * _CL_obj = ((BinTerm *) GC_OBJECT(BinTerm,new_object_class(choco._BinTerm)));
      (_CL_obj->v1 = t->v1);
      (_CL_obj->sign1 = t->sign1);
      (_CL_obj->v2 = x);
      (_CL_obj->cste = t->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:IntVar,t:UnTerm) [] */
BinTerm * claire__plus_IntVar3(IntVar *x,UnTerm *t)
{ return (claire__plus_UnTerm2(t,x));} 


/* The c++ function for: +(t1:UnTerm,t2:UnTerm) [] */
BinTerm * claire__plus_UnTerm3(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { BinTerm *Result ;
    { BinTerm * _CL_obj = ((BinTerm *) GC_OBJECT(BinTerm,new_object_class(choco._BinTerm)));
      (_CL_obj->v1 = t1->v1);
      (_CL_obj->sign1 = t1->sign1);
      (_CL_obj->v2 = t2->v1);
      (_CL_obj->sign2 = t2->sign1);
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:BinTerm,c:integer) [] */
BinTerm * claire__plus_BinTerm1(BinTerm *t,int c)
{ (t->cste = (t->cste+c));
  return (t);} 


/* The c++ function for: +(c:integer,t:BinTerm) [] */
BinTerm * claire__plus_integer4(int c,BinTerm *t)
{ return (claire__plus_BinTerm1(t,c));} 


/* The c++ function for: +(t:BinTerm,x:IntVar) [] */
LinTerm * claire__plus_BinTerm2(BinTerm *t,IntVar *x)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      { LinTerm * g1300; 
        list * g1301;
        g1300 = _CL_obj;
        { OID v_bag;
          GC_ANY(g1301= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1301)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1301)->addFast(v_bag);
          ((list *) g1301)->addFast(1);} 
        (g1300->lcoeff = g1301);} 
      (_CL_obj->lvars = list::alloc(choco._IntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)));
      (_CL_obj->cste = t->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:IntVar,t:BinTerm) [] */
LinTerm * claire__plus_IntVar4(IntVar *x,BinTerm *t)
{ return (claire__plus_BinTerm2(t,x));} 


/* The c++ function for: +(t1:BinTerm,t2:UnTerm) [] */
LinTerm * claire__plus_BinTerm3(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      { LinTerm * g1302; 
        list * g1303;
        g1302 = _CL_obj;
        { OID v_bag;
          GC_ANY(g1303= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1303)->addFast(v_bag);
          if (t1->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1303)->addFast(v_bag);
          if (t2->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1303)->addFast(v_bag);} 
        (g1302->lcoeff = g1303);} 
      (_CL_obj->lvars = list::alloc(choco._IntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:UnTerm,t1:BinTerm) [] */
LinTerm * claire__plus_UnTerm4(UnTerm *t2,BinTerm *t1)
{ return (claire__plus_BinTerm3(t1,t2));} 


/* The c++ function for: +(t1:BinTerm,t2:BinTerm) [] */
LinTerm * claire__plus_BinTerm4(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      { LinTerm * g1304; 
        list * g1305;
        g1304 = _CL_obj;
        { OID v_bag;
          GC_ANY(g1305= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1305)->addFast(v_bag);
          if (t1->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1305)->addFast(v_bag);
          if (t2->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1305)->addFast(v_bag);
          if (t2->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1305)->addFast(v_bag);} 
        (g1304->lcoeff = g1305);} 
      (_CL_obj->lvars = list::alloc(choco._IntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t:LinTerm,a:integer) [] */
LinTerm * claire__plus_LinTerm1(LinTerm *t,int a)
{ (t->cste = (t->cste+a));
  return (t);} 


/* The c++ function for: +(a:integer,t:LinTerm) [] */
LinTerm * claire__plus_integer5(int a,LinTerm *t)
{ return (claire__plus_LinTerm1(t,a));} 


/* The c++ function for: +(t:LinTerm,x:IntVar) [] */
LinTerm * claire__plus_LinTerm2(LinTerm *t,IntVar *x)
{ GC_BIND;
  GC_OBJECT(list,t->lcoeff)->addFast(1);
  GC_OBJECT(list,t->lvars)->addFast(_oid_(x));
  { LinTerm *Result ;
    Result = t;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(x:IntVar,t:LinTerm) [] */
LinTerm * claire__plus_IntVar5(IntVar *x,LinTerm *t)
{ return (claire__plus_LinTerm2(t,x));} 


/* The c++ function for: +(t1:LinTerm,t2:UnTerm) [] */
LinTerm * claire__plus_LinTerm3(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { OID  g1306UU;
    if (t2->sign1 == CTRUE)
     g1306UU = 1;
    else g1306UU = -1;
      GC_OBJECT(list,t1->lcoeff)->addFast(g1306UU);
    } 
  GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)));
  (t1->cste = (t1->cste+t2->cste));
  { LinTerm *Result ;
    Result = t1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:UnTerm,t1:LinTerm) [] */
LinTerm * claire__plus_UnTerm5(UnTerm *t2,LinTerm *t1)
{ return (claire__plus_LinTerm3(t1,t2));} 


/* The c++ function for: +(t1:LinTerm,t2:BinTerm) [] */
LinTerm * claire__plus_LinTerm4(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm * g1307; 
    list * g1308;
    g1307 = t1;
    { list * g1309UU;
      { OID v_bag;
        GC_ANY(g1309UU= list::empty(Kernel._integer));
        if (t2->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1309UU)->addFast(v_bag);
        if (t2->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1309UU)->addFast(v_bag);} 
      g1308 = append_list(GC_OBJECT(list,t1->lcoeff),g1309UU);
      } 
    (g1307->lcoeff = g1308);} 
  (t1->lvars = append_list(GC_OBJECT(list,t1->lvars),list::alloc(choco._IntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2)))));
  (t1->cste = (t1->cste+t2->cste));
  { LinTerm *Result ;
    Result = t1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: +(t2:BinTerm,t1:LinTerm) [] */
LinTerm * claire__plus_BinTerm5(BinTerm *t2,LinTerm *t1)
{ return (claire__plus_LinTerm4(t1,t2));} 


/* The c++ function for: +(t1:LinTerm,t2:LinTerm) [] */
LinTerm * claire__plus_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      (_CL_obj->lcoeff = append_list(GC_OBJECT(list,t1->lcoeff),GC_OBJECT(list,t2->lcoeff)));
      (_CL_obj->lvars = append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars)));
      (_CL_obj->cste = (t1->cste+t2->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Opposite Operator
/* The c++ function for: -(x:IntVar) [] */
UnTerm * claire__dash_IntVar1(IntVar *x)
{ GC_BIND;
  { UnTerm *Result ;
    { UnTerm * _CL_obj = ((UnTerm *) GC_OBJECT(UnTerm,new_object_class(choco._UnTerm)));
      (_CL_obj->v1 = x);
      (_CL_obj->sign1 = CFALSE);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t:UnTerm) [] */
UnTerm * claire__dash_UnTerm1(UnTerm *t)
{ (t->sign1 = not_any(_oid_(t->sign1)));
  (t->cste = (-t->cste));
  return (t);} 


/* The c++ function for: -(t:BinTerm) [] */
BinTerm * claire__dash_BinTerm1(BinTerm *t)
{ (t->sign1 = not_any(_oid_(t->sign1)));
  (t->sign2 = not_any(_oid_(t->sign2)));
  (t->cste = (-t->cste));
  return (t);} 


/* The c++ function for: -(t:LinTerm) [] */
LinTerm * claire__dash_LinTerm1(LinTerm *t)
{ GC_BIND;
  { LinTerm * g1310; 
    list * g1311;
    g1310 = t;
    { bag *v_list; OID v_val;
      OID a,CLcount;
      v_list = GC_OBJECT(list,t->lcoeff);
       g1311 = v_list->clone(Kernel._integer);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { a = (*(v_list))[CLcount];
        v_val = (-a);
        
        (*((list *) g1311))[CLcount] = v_val;} 
      } 
    (g1310->lcoeff = g1311);} 
  (t->cste = (-t->cste));
  { LinTerm *Result ;
    Result = t;
    GC_UNBIND; return (Result);} 
  } 


// ----- Substraction Operator
// many lines for
// [-(t1:(Term U IntVar U integer), t2:(Term U IntVar)) : Term => t1 + -(t2)]
// because of a dump compiler
// v0.18 further expand for static typing
/* The c++ function for: -(t1:IntVar,t2:integer) [] */
UnTerm * claire__dash_IntVar2(IntVar *t1,int t2)
{ return (claire__plus_IntVar1(t1,(-t2)));} 


/* The c++ function for: -(t1:UnTerm,t2:integer) [] */
UnTerm * claire__dash_UnTerm2(UnTerm *t1,int t2)
{ return (claire__plus_UnTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:BinTerm,t2:integer) [] */
BinTerm * claire__dash_BinTerm2(BinTerm *t1,int t2)
{ return (claire__plus_BinTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:LinTerm,t2:integer) [] */
LinTerm * claire__dash_LinTerm2(LinTerm *t1,int t2)
{ return (claire__plus_LinTerm1(t1,(-t2)));} 


/* The c++ function for: -(t1:integer,t2:IntVar) [] */
UnTerm * claire__dash_integer4(int t1,IntVar *t2)
{ GC_BIND;
  { UnTerm *Result ;
    Result = claire__plus_UnTerm1(GC_OBJECT(UnTerm,claire__dash_IntVar1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:IntVar,t2:IntVar) [] */
BinTerm * claire__dash_IntVar3(IntVar *t1,IntVar *t2)
{ GC_BIND;
  { BinTerm *Result ;
    Result = claire__plus_UnTerm2(GC_OBJECT(UnTerm,claire__dash_IntVar1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:UnTerm,t2:IntVar) [] */
BinTerm * claire__dash_UnTerm3(UnTerm *t1,IntVar *t2)
{ GC_BIND;
  { BinTerm *Result ;
    Result = claire__plus_UnTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:BinTerm,t2:IntVar) [] */
LinTerm * claire__dash_BinTerm3(BinTerm *t1,IntVar *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_BinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:LinTerm,t2:IntVar) [] */
LinTerm * claire__dash_LinTerm3(LinTerm *t1,IntVar *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:UnTerm) [] */
UnTerm * claire__dash_integer5(int t1,UnTerm *t2)
{ GC_BIND;
  { UnTerm *Result ;
    Result = claire__plus_UnTerm1(GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:IntVar,t2:UnTerm) [] */
BinTerm * claire__dash_IntVar4(IntVar *t1,UnTerm *t2)
{ GC_BIND;
  { BinTerm *Result ;
    Result = claire__plus_UnTerm2(GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:UnTerm,t2:UnTerm) [] */
BinTerm * claire__dash_UnTerm4(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { BinTerm *Result ;
    Result = claire__plus_UnTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:BinTerm,t2:UnTerm) [] */
LinTerm * claire__dash_BinTerm4(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_BinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:LinTerm,t2:UnTerm) [] */
LinTerm * claire__dash_LinTerm4(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:BinTerm) [] */
BinTerm * claire__dash_integer6(int t1,BinTerm *t2)
{ GC_BIND;
  { BinTerm *Result ;
    Result = claire__plus_BinTerm1(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:IntVar,t2:BinTerm) [] */
LinTerm * claire__dash_IntVar5(IntVar *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_BinTerm2(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:UnTerm,t2:BinTerm) [] */
LinTerm * claire__dash_UnTerm5(UnTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_BinTerm3(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:BinTerm,t2:BinTerm) [] */
LinTerm * claire__dash_BinTerm5(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_BinTerm4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:LinTerm,t2:BinTerm) [] */
LinTerm * claire__dash_LinTerm5(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:integer,t2:LinTerm) [] */
LinTerm * claire__dash_integer7(int t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm1(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:IntVar,t2:LinTerm) [] */
LinTerm * claire__dash_IntVar6(IntVar *t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm2(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:UnTerm,t2:LinTerm) [] */
LinTerm * claire__dash_UnTerm6(UnTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm3(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:BinTerm,t2:LinTerm) [] */
LinTerm * claire__dash_BinTerm6(BinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm4(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),t1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: -(t1:LinTerm,t2:LinTerm) [] */
LinTerm * claire__dash_LinTerm6(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinTerm *Result ;
    Result = claire__plus_LinTerm5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


// ------- Using terms within comparisons
// v0.18 rewrite for static typing
// [>=(a:integer, t:Term) : IntConstraint => -(t) >= -(a)]
// [>=(x:IntVar, t:Term) : IntConstraint => -(t) >= -(x)]
// [>=(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => -(t2) >= -(t1)]
/* The c++ function for: >=(a:integer,t:UnTerm) [] */
IntConstraint * claire__sup_equal_integer2(int a,UnTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm3(GC_OBJECT(UnTerm,claire__dash_UnTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(a:integer,t:BinTerm) [] */
IntConstraint * claire__sup_equal_integer3(int a,BinTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm2(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(a:integer,t:LinTerm) [] */
IntConstraint * claire__sup_equal_integer4(int a,LinTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm1(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t)),(-a));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:IntVar,t:UnTerm) [] */
IntConstraint * claire__sup_equal_IntVar3(IntVar *x,UnTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm5(GC_OBJECT(UnTerm,claire__dash_UnTerm1(t)),GC_OBJECT(UnTerm,claire__dash_IntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:IntVar,t:BinTerm) [] */
IntConstraint * claire__sup_equal_IntVar4(IntVar *x,BinTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm4(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t)),GC_OBJECT(UnTerm,claire__dash_IntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(x:IntVar,t:LinTerm) [] */
IntConstraint * claire__sup_equal_IntVar5(IntVar *x,LinTerm *t)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm3(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t)),GC_OBJECT(UnTerm,claire__dash_IntVar1(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:UnTerm,t2:BinTerm) [] */
IntConstraint * claire__sup_equal_UnTerm1(UnTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm4(GC_OBJECT(BinTerm,claire__dash_BinTerm1(t2)),GC_OBJECT(UnTerm,claire__dash_UnTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:UnTerm,t2:LinTerm) [] */
IntConstraint * claire__sup_equal_UnTerm2(UnTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm3(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),GC_OBJECT(UnTerm,claire__dash_UnTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:BinTerm,t2:LinTerm) [] */
IntConstraint * claire__sup_equal_BinTerm1(BinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm4(GC_OBJECT(LinTerm,claire__dash_LinTerm1(t2)),GC_OBJECT(BinTerm,claire__dash_BinTerm1(t1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:UnTerm,c:integer) [] */
UnIntConstraint * claire__sup_equal_UnTerm3(UnTerm *t,int c)
{ if (t->sign1 == CTRUE) 
  { { UnIntConstraint *Result ;
      { GreaterOrEqualxc * _CL_obj = ((GreaterOrEqualxc *) GC_OBJECT(GreaterOrEqualxc,new_object_class(choco._GreaterOrEqualxc)));
        (_CL_obj->v1 = t->v1);
        (_CL_obj->cste = (c-t->cste));
        Result = _CL_obj;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { UnIntConstraint *Result ;
      { LessOrEqualxc * _CL_obj = ((LessOrEqualxc *) GC_OBJECT(LessOrEqualxc,new_object_class(choco._LessOrEqualxc)));
        (_CL_obj->v1 = t->v1);
        (_CL_obj->cste = (t->cste-c));
        Result = _CL_obj;
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:UnTerm,x:IntVar) [] */
IntConstraint * claire__sup_equal_UnTerm4(UnTerm *t,IntVar *x)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm5(GC_OBJECT(UnTerm,claire__dash_IntVar1(x)),GC_OBJECT(UnTerm,claire__dash_UnTerm1(t)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:UnTerm,t2:UnTerm) [] */
IntConstraint * claire__sup_equal_UnTerm5(UnTerm *t1,UnTerm *t2)
{ if (t1->sign1 == t2->sign1) 
  { { IntConstraint *Result ;
      if (t1->sign1 == CTRUE)
       { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
        (_CL_obj->v1 = t1->v1);
        (_CL_obj->v2 = t2->v1);
        (_CL_obj->cste = (t2->cste-t1->cste));
        Result = _CL_obj;
        } 
      else { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
          (_CL_obj->v1 = t2->v1);
          (_CL_obj->v2 = t1->v1);
          (_CL_obj->cste = (t2->cste-t1->cste));
          Result = _CL_obj;
          } 
        return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      { list * g1312UU;
        { OID v_bag;
          GC_ANY(g1312UU= list::empty(Kernel._integer));
          if (t1->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1312UU)->addFast(v_bag);
          ((list *) g1312UU)->addFast((-((t2->sign1 == CTRUE) ?
            1 :
            -1 )));} 
        Result = choco_makeLinComb_list(g1312UU,list::alloc(choco._IntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),2);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:BinTerm,c:integer) [] */
IntConstraint * claire__sup_equal_BinTerm2(BinTerm *t,int c)
{ if (t->sign1 != t->sign2) 
  { { IntConstraint *Result ;
      if (t->sign1 == CTRUE)
       { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
        (_CL_obj->v1 = t->v1);
        (_CL_obj->v2 = t->v2);
        (_CL_obj->cste = (c-t->cste));
        Result = _CL_obj;
        } 
      else { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
          (_CL_obj->v1 = t->v2);
          (_CL_obj->v2 = t->v1);
          (_CL_obj->cste = (c+t->cste));
          Result = _CL_obj;
          } 
        return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      { list * g1313UU;
        { OID v_bag;
          GC_ANY(g1313UU= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1313UU)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1313UU)->addFast(v_bag);} 
        Result = choco_makeLinComb_list(g1313UU,list::alloc(choco._IntVar,2,GC_OID(_oid_(t->v1)),GC_OID(_oid_(t->v2))),(t->cste-c),2);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: >=(t:BinTerm,x:IntVar) [] */
LinComb * claire__sup_equal_BinTerm3(BinTerm *t,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1314UU;
      { OID v_bag;
        GC_ANY(g1314UU= list::empty(Kernel._integer));
        if (t->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1314UU)->addFast(v_bag);
        if (t->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1314UU)->addFast(v_bag);
        ((list *) g1314UU)->addFast(-1);} 
      Result = choco_makeLinComb_list(g1314UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)),t->cste,2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:BinTerm,t2:UnTerm) [] */
LinComb * claire__sup_equal_BinTerm4(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1315UU;
      { OID v_bag;
        GC_ANY(g1315UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1315UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1315UU)->addFast(v_bag);
        ((list *) g1315UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1315UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:BinTerm,t2:BinTerm) [] */
LinComb * claire__sup_equal_BinTerm5(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1316UU;
      { OID v_bag;
        GC_ANY(g1316UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1316UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1316UU)->addFast(v_bag);
        ((list *) g1316UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g1316UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1316UU,list::alloc(choco._IntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:LinTerm,a:integer) [] */
LinComb * claire__sup_equal_LinTerm1(LinTerm *t,int a)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,t->lcoeff),GC_OBJECT(list,t->lvars),(t->cste-a),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:LinTerm,x:IntVar) [] */
LinComb * claire__sup_equal_LinTerm2(LinTerm *t,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(_oid_(x))),t->cste,2);
    GC_UNBIND; return (Result);} 
  } 


//v0.30 add -1 and not +1 !
/* The c++ function for: >=(t:LinTerm,t2:UnTerm) [] */
LinComb * claire__sup_equal_LinTerm3(LinTerm *t,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t->cste-t2->cste),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t:LinTerm,t2:BinTerm) [] */
LinComb * claire__sup_equal_LinTerm4(LinTerm *t,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t->lvars),list::alloc(choco._IntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t->cste-t2->cste),2);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >=(t1:LinTerm,t2:LinTerm) [] */
LinComb * claire__sup_equal_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1317UU;
      { { list * g1318UU;
          { { bag *v_list; OID v_val;
              OID a,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g1318UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { a = (*(v_list))[CLcount];
                v_val = (-a);
                
                (*((list *) g1318UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g1318UU);} 
          g1317UU = append_list(GC_OBJECT(list,t1->lcoeff),g1318UU);
          } 
        GC_OBJECT(list,g1317UU);} 
      Result = choco_makeLinComb_list(g1317UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),2);
      } 
    GC_UNBIND; return (Result);} 
  } 


// All comparisons can be defined from >=
// rewrite t1 > t2 as t1 >= t2 + 1
// v0.18 expanded for static typing
// [>(t1:Term, t2:Term) : IntConstraint => (t1 >= t2 + 1)]
// [>(t1:(IntVar U integer), t2:Term) : IntConstraint => (t1 >= t2 + 1)]
// [>(t1:Term, t2:(IntVar U integer)) : IntConstraint => (t1 >= t2 + 1)]
/* The c++ function for: >(t1:integer,t2:UnTerm) [] */
IntConstraint * claire__sup_integer3(int t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_integer2(t1,GC_OBJECT(UnTerm,claire__plus_UnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:integer,t2:BinTerm) [] */
IntConstraint * claire__sup_integer4(int t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_integer3(t1,GC_OBJECT(BinTerm,claire__plus_BinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:integer,t2:LinTerm) [] */
IntConstraint * claire__sup_integer5(int t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_integer4(t1,GC_OBJECT(LinTerm,claire__plus_LinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:IntVar,t2:UnTerm) [] */
IntConstraint * claire__sup_IntVar3(IntVar *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_IntVar3(t1,GC_OBJECT(UnTerm,claire__plus_UnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:IntVar,t2:BinTerm) [] */
IntConstraint * claire__sup_IntVar4(IntVar *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_IntVar4(t1,GC_OBJECT(BinTerm,claire__plus_BinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:IntVar,t2:LinTerm) [] */
IntConstraint * claire__sup_IntVar5(IntVar *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_IntVar5(t1,GC_OBJECT(LinTerm,claire__plus_LinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:UnTerm,t2:integer) [] */
IntConstraint * claire__sup_UnTerm1(UnTerm *t1,int t2)
{ return (claire__sup_equal_UnTerm3(t1,(t2+1)));} 


/* The c++ function for: >(t1:BinTerm,t2:integer) [] */
IntConstraint * claire__sup_BinTerm1(BinTerm *t1,int t2)
{ return (claire__sup_equal_BinTerm2(t1,(t2+1)));} 


/* The c++ function for: >(t1:LinTerm,t2:integer) [] */
IntConstraint * claire__sup_LinTerm1(LinTerm *t1,int t2)
{ return (claire__sup_equal_LinTerm1(t1,(t2+1)));} 


/* The c++ function for: >(t1:UnTerm,t2:IntVar) [] */
IntConstraint * claire__sup_UnTerm2(UnTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm5(t1,GC_OBJECT(UnTerm,claire__plus_IntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:BinTerm,t2:IntVar) [] */
IntConstraint * claire__sup_BinTerm2(BinTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm4(t1,GC_OBJECT(UnTerm,claire__plus_IntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:LinTerm,t2:IntVar) [] */
IntConstraint * claire__sup_LinTerm2(LinTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm3(t1,GC_OBJECT(UnTerm,claire__plus_IntVar1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:UnTerm,t2:UnTerm) [] */
IntConstraint * claire__sup_UnTerm3(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm5(t1,GC_OBJECT(UnTerm,claire__plus_UnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:BinTerm,t2:UnTerm) [] */
IntConstraint * claire__sup_BinTerm3(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm4(t1,GC_OBJECT(UnTerm,claire__plus_UnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:LinTerm,t2:UnTerm) [] */
IntConstraint * claire__sup_LinTerm3(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm3(t1,GC_OBJECT(UnTerm,claire__plus_UnTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:UnTerm,t2:BinTerm) [] */
IntConstraint * claire__sup_UnTerm4(UnTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm1(t1,GC_OBJECT(BinTerm,claire__plus_BinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:BinTerm,t2:BinTerm) [] */
IntConstraint * claire__sup_BinTerm4(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm5(t1,GC_OBJECT(BinTerm,claire__plus_BinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:LinTerm,t2:BinTerm) [] */
IntConstraint * claire__sup_LinTerm4(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm4(t1,GC_OBJECT(BinTerm,claire__plus_BinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:UnTerm,t2:LinTerm) [] */
IntConstraint * claire__sup_UnTerm5(UnTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm2(t1,GC_OBJECT(LinTerm,claire__plus_LinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:BinTerm,t2:LinTerm) [] */
IntConstraint * claire__sup_BinTerm5(BinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_BinTerm1(t1,GC_OBJECT(LinTerm,claire__plus_LinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: >(t1:LinTerm,t2:LinTerm) [] */
IntConstraint * claire__sup_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_LinTerm5(t1,GC_OBJECT(LinTerm,claire__plus_LinTerm1(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


// rewrite t1 <= t2 as t2 >= t1
// v0.18 expanded for static typing
// [<=(t1:Term, t2:Term) : IntConstraint -> t2 >= t1]
// [<=(t1:(IntVar U integer), t2:Term) : IntConstraint => t2 >= t1]
// [<=(t1:Term, t2:(IntVar U integer)) : IntConstraint => t2 >= t1]
/* The c++ function for: <=(t1:integer,t2:UnTerm) [] */
IntConstraint * claire__inf_equal_integer3(int t1,UnTerm *t2)
{ return (claire__sup_equal_UnTerm3(t2,t1));} 


/* The c++ function for: <=(t1:integer,t2:BinTerm) [] */
IntConstraint * claire__inf_equal_integer4(int t1,BinTerm *t2)
{ return (claire__sup_equal_BinTerm2(t2,t1));} 


/* The c++ function for: <=(t1:integer,t2:LinTerm) [] */
IntConstraint * claire__inf_equal_integer5(int t1,LinTerm *t2)
{ return (claire__sup_equal_LinTerm1(t2,t1));} 


/* The c++ function for: <=(t1:IntVar,t2:UnTerm) [] */
IntConstraint * claire__inf_equal_IntVar3(IntVar *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__sup_equal_UnTerm5(GC_OBJECT(UnTerm,claire__dash_IntVar1(t1)),GC_OBJECT(UnTerm,claire__dash_UnTerm1(t2)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <=(t1:IntVar,t2:BinTerm) [] */
IntConstraint * claire__inf_equal_IntVar4(IntVar *t1,BinTerm *t2)
{ return (claire__sup_equal_BinTerm3(t2,t1));} 


/* The c++ function for: <=(t1:IntVar,t2:LinTerm) [] */
IntConstraint * claire__inf_equal_IntVar5(IntVar *t1,LinTerm *t2)
{ return (claire__sup_equal_LinTerm2(t2,t1));} 


/* The c++ function for: <=(t1:UnTerm,t2:integer) [] */
IntConstraint * claire__inf_equal_UnTerm1(UnTerm *t1,int t2)
{ return (claire__sup_equal_integer2(t2,t1));} 


/* The c++ function for: <=(t1:BinTerm,t2:integer) [] */
IntConstraint * claire__inf_equal_BinTerm1(BinTerm *t1,int t2)
{ return (claire__sup_equal_integer3(t2,t1));} 


/* The c++ function for: <=(t1:LinTerm,t2:integer) [] */
IntConstraint * claire__inf_equal_LinTerm1(LinTerm *t1,int t2)
{ return (claire__sup_equal_integer4(t2,t1));} 


/* The c++ function for: <=(t1:UnTerm,t2:IntVar) [] */
IntConstraint * claire__inf_equal_UnTerm2(UnTerm *t1,IntVar *t2)
{ return (claire__sup_equal_IntVar3(t2,t1));} 


/* The c++ function for: <=(t1:BinTerm,t2:IntVar) [] */
IntConstraint * claire__inf_equal_BinTerm2(BinTerm *t1,IntVar *t2)
{ return (claire__sup_equal_IntVar4(t2,t1));} 


/* The c++ function for: <=(t1:LinTerm,t2:IntVar) [] */
IntConstraint * claire__inf_equal_LinTerm2(LinTerm *t1,IntVar *t2)
{ return (claire__sup_equal_IntVar5(t2,t1));} 


/* The c++ function for: <=(t1:UnTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_equal_UnTerm3(UnTerm *t1,UnTerm *t2)
{ return (claire__sup_equal_UnTerm5(t2,t1));} 


/* The c++ function for: <=(t1:BinTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_equal_BinTerm3(BinTerm *t1,UnTerm *t2)
{ return (claire__sup_equal_UnTerm1(t2,t1));} 


/* The c++ function for: <=(t1:LinTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_equal_LinTerm3(LinTerm *t1,UnTerm *t2)
{ return (claire__sup_equal_UnTerm2(t2,t1));} 


/* The c++ function for: <=(t1:UnTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_equal_UnTerm4(UnTerm *t1,BinTerm *t2)
{ return (claire__sup_equal_BinTerm4(t2,t1));} 


/* The c++ function for: <=(t1:BinTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_equal_BinTerm4(BinTerm *t1,BinTerm *t2)
{ return (claire__sup_equal_BinTerm5(t2,t1));} 


/* The c++ function for: <=(t1:LinTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_equal_LinTerm4(LinTerm *t1,BinTerm *t2)
{ return (claire__sup_equal_BinTerm1(t2,t1));} 


/* The c++ function for: <=(t1:UnTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_equal_UnTerm5(UnTerm *t1,LinTerm *t2)
{ return (claire__sup_equal_LinTerm3(t2,t1));} 


/* The c++ function for: <=(t1:BinTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_equal_BinTerm5(BinTerm *t1,LinTerm *t2)
{ return (claire__sup_equal_LinTerm4(t2,t1));} 


/* The c++ function for: <=(t1:LinTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_equal_LinTerm5(LinTerm *t1,LinTerm *t2)
{ return (claire__sup_equal_LinTerm5(t2,t1));} 


// rewrite t1 < t2 as t1 <= t2 - 1
// v0.18 expanded for static typing
// [<(t1:Term, t2:Term) : IntConstraint => (t1 <= t2 - 1)]
// [<(t1:(IntVar U integer), t2:Term) : IntConstraint => (t1 <= t2 - 1)]
// [<(t1:Term, t2:(IntVar U integer)) : IntConstraint => (t1 <= t2 - 1)]
/* The c++ function for: <(t1:integer,t2:UnTerm) [] */
IntConstraint * claire__inf_integer3(int t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_integer3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:integer,t2:BinTerm) [] */
IntConstraint * claire__inf_integer4(int t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_integer4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:integer,t2:LinTerm) [] */
IntConstraint * claire__inf_integer5(int t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_integer5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:IntVar,t2:UnTerm) [] */
IntConstraint * claire__inf_IntVar3(IntVar *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_IntVar3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:IntVar,t2:BinTerm) [] */
IntConstraint * claire__inf_IntVar4(IntVar *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_IntVar4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:IntVar,t2:LinTerm) [] */
IntConstraint * claire__inf_IntVar5(IntVar *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_IntVar5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:UnTerm,t2:integer) [] */
IntConstraint * claire__inf_UnTerm1(UnTerm *t1,int t2)
{ return (claire__inf_equal_UnTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:BinTerm,t2:integer) [] */
IntConstraint * claire__inf_BinTerm1(BinTerm *t1,int t2)
{ return (claire__inf_equal_BinTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:LinTerm,t2:integer) [] */
IntConstraint * claire__inf_LinTerm1(LinTerm *t1,int t2)
{ return (claire__inf_equal_LinTerm1(t1,(t2-1)));} 


/* The c++ function for: <(t1:UnTerm,t2:IntVar) [] */
IntConstraint * claire__inf_UnTerm2(UnTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_UnTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:BinTerm,t2:IntVar) [] */
IntConstraint * claire__inf_BinTerm2(BinTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_BinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:LinTerm,t2:IntVar) [] */
IntConstraint * claire__inf_LinTerm2(LinTerm *t1,IntVar *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_LinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_IntVar2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:UnTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_UnTerm3(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_UnTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:BinTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_BinTerm3(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_BinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:LinTerm,t2:UnTerm) [] */
IntConstraint * claire__inf_LinTerm3(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_LinTerm3(t1,GC_OBJECT(UnTerm,claire__dash_UnTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:UnTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_UnTerm4(UnTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_UnTerm4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:BinTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_BinTerm4(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_BinTerm4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:LinTerm,t2:BinTerm) [] */
IntConstraint * claire__inf_LinTerm4(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_LinTerm4(t1,GC_OBJECT(BinTerm,claire__dash_BinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:UnTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_UnTerm5(UnTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_UnTerm5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:BinTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_BinTerm5(BinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_BinTerm5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <(t1:LinTerm,t2:LinTerm) [] */
IntConstraint * claire__inf_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    Result = claire__inf_equal_LinTerm5(t1,GC_OBJECT(LinTerm,claire__dash_LinTerm2(t2,1)));
    GC_UNBIND; return (Result);} 
  } 


// Equality
// v0.18 expanded for static typing
// [==(a:integer, t:Term) : IntConstraint => t == a]
// [==(x:IntVar, t:Term) : IntConstraint => t == x]
// [==(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => t2 == t1]
// [==(t1:BinTerm, t2:LinTerm) : IntConstraint => t2 == t1]
/* The c++ function for: ==(a:integer,t:UnTerm) [] */
IntConstraint * claire__equal_equal_integer2(int a,UnTerm *t)
{ return (claire__equal_equal_UnTerm3(t,a));} 


/* The c++ function for: ==(a:integer,t:BinTerm) [] */
IntConstraint * claire__equal_equal_integer3(int a,BinTerm *t)
{ return (claire__equal_equal_BinTerm2(t,a));} 


/* The c++ function for: ==(a:integer,t:LinTerm) [] */
IntConstraint * claire__equal_equal_integer4(int a,LinTerm *t)
{ return (claire__equal_equal_LinTerm1(t,a));} 


/* The c++ function for: ==(x:IntVar,t:UnTerm) [] */
IntConstraint * claire__equal_equal_IntVar3(IntVar *x,UnTerm *t)
{ return (claire__equal_equal_UnTerm4(t,x));} 


/* The c++ function for: ==(x:IntVar,t:BinTerm) [] */
IntConstraint * claire__equal_equal_IntVar4(IntVar *x,BinTerm *t)
{ return (claire__equal_equal_BinTerm3(t,x));} 


/* The c++ function for: ==(x:IntVar,t:LinTerm) [] */
IntConstraint * claire__equal_equal_IntVar5(IntVar *x,LinTerm *t)
{ return (claire__equal_equal_LinTerm2(t,x));} 


/* The c++ function for: ==(t1:UnTerm,t2:BinTerm) [] */
IntConstraint * claire__equal_equal_UnTerm1(UnTerm *t1,BinTerm *t2)
{ return (claire__equal_equal_BinTerm4(t2,t1));} 


/* The c++ function for: ==(t1:UnTerm,t2:LinTerm) [] */
IntConstraint * claire__equal_equal_UnTerm2(UnTerm *t1,LinTerm *t2)
{ return (claire__equal_equal_LinTerm3(t2,t1));} 


/* The c++ function for: ==(t1:BinTerm,t2:LinTerm) [] */
IntConstraint * claire__equal_equal_BinTerm1(BinTerm *t1,LinTerm *t2)
{ return (claire__equal_equal_LinTerm4(t2,t1));} 


/* The c++ function for: ==(t:UnTerm,c:integer) [] */
UnIntConstraint * claire__equal_equal_UnTerm3(UnTerm *t,int c)
{ GC_BIND;
  { UnIntConstraint *Result ;
    { Equalxc * _CL_obj = ((Equalxc *) GC_OBJECT(Equalxc,new_object_class(choco._Equalxc)));
      (_CL_obj->v1 = t->v1);
      (_CL_obj->cste = ((t->sign1 == CTRUE) ?
        (c-t->cste) :
        (t->cste-c) ));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9907
/* The c++ function for: ==(t:UnTerm,x:IntVar) [] */
IntConstraint * claire__equal_equal_UnTerm4(UnTerm *t,IntVar *x)
{ if (t->sign1 == CTRUE) 
  { { IntConstraint *Result ;
      { Equalxyc * _CL_obj = ((Equalxyc *) GC_OBJECT(Equalxyc,new_object_class(choco._Equalxyc)));
        (_CL_obj->v1 = x);
        (_CL_obj->v2 = t->v1);
        (_CL_obj->cste = t->cste);
        Result = _CL_obj;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      Result = choco_makeLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(choco._IntVar,2,GC_OID(_oid_(t->v1)),_oid_(x)),(-t->cste),1);
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: ==(t1:UnTerm,t2:UnTerm) [] */
BinIntConstraint * claire__equal_equal_UnTerm5(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { BinIntConstraint *Result ;
    { Equalxyc * _CL_obj = ((Equalxyc *) GC_OBJECT(Equalxyc,new_object_class(choco._Equalxyc)));
      (_CL_obj->v1 = t1->v1);
      (_CL_obj->v2 = t2->v1);
      (_CL_obj->cste = (t2->cste-t1->cste));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:BinTerm,c:integer) [] */
IntConstraint * claire__equal_equal_BinTerm2(BinTerm *t,int c)
{ if (t->sign1 != t->sign2) 
  { { IntConstraint *Result ;
      { Equalxyc * _CL_obj = ((Equalxyc *) GC_OBJECT(Equalxyc,new_object_class(choco._Equalxyc)));
        (_CL_obj->v1 = t->v1);
        (_CL_obj->v2 = t->v2);
        (_CL_obj->cste = ((t->sign1 == CTRUE) ?
          (c-t->cste) :
          (t->cste-c) ));
        Result = _CL_obj;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      { list * g1319UU;
        { OID v_bag;
          GC_ANY(g1319UU= list::empty(Kernel._integer));
          if (t->sign1 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1319UU)->addFast(v_bag);
          if (t->sign2 == CTRUE)
           v_bag = 1;
          else v_bag = -1;
            ((list *) g1319UU)->addFast(v_bag);} 
        Result = choco_makeLinComb_list(g1319UU,list::alloc(choco._IntVar,2,GC_OID(_oid_(t->v1)),GC_OID(_oid_(t->v2))),(t->cste-c),1);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: ==(t:BinTerm,x:IntVar) [] */
LinComb * claire__equal_equal_BinTerm3(BinTerm *t,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1320UU;
      { OID v_bag;
        GC_ANY(g1320UU= list::empty(Kernel._integer));
        if (t->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1320UU)->addFast(v_bag);
        if (t->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1320UU)->addFast(v_bag);
        ((list *) g1320UU)->addFast(-1);} 
      Result = choco_makeLinComb_list(g1320UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t->v1)),
        GC_OID(_oid_(t->v2)),
        _oid_(x)),t->cste,1);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:BinTerm,t2:UnTerm) [] */
LinComb * claire__equal_equal_BinTerm4(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1321UU;
      { OID v_bag;
        GC_ANY(g1321UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1321UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1321UU)->addFast(v_bag);
        ((list *) g1321UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1321UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


// claire3 port staticly typed lists
/* The c++ function for: ==(t1:BinTerm,t2:BinTerm) [] */
LinComb * claire__equal_equal_BinTerm5(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1322UU;
      { OID v_bag;
        GC_ANY(g1322UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1322UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1322UU)->addFast(v_bag);
        ((list *) g1322UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g1322UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1322UU,list::alloc(choco._IntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:LinTerm,c:integer) [] */
LinComb * claire__equal_equal_LinTerm1(LinTerm *t,int c)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,t->lcoeff),GC_OBJECT(list,t->lvars),(t->cste-c),1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:LinTerm,x:IntVar) [] */
LinComb * claire__equal_equal_LinTerm2(LinTerm *t,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t->lvars)->addFast(_oid_(x))),t->cste,1);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t1:LinTerm,t2:UnTerm) [] */
LinComb * claire__equal_equal_LinTerm3(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t1->cste-t2->cste),1);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port staticly typed lists
/* The c++ function for: ==(t1:LinTerm,t2:BinTerm) [] */
LinComb * claire__equal_equal_LinTerm4(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),list::alloc(choco._IntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t1->cste-t2->cste),1);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port staticly typed lists
/* The c++ function for: ==(t1:LinTerm,t2:LinTerm) [] */
LinComb * claire__equal_equal_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1323UU;
      { { list * g1324UU;
          { { bag *v_list; OID v_val;
              OID a,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g1324UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { a = (*(v_list))[CLcount];
                v_val = (-a);
                
                (*((list *) g1324UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g1324UU);} 
          g1323UU = append_list(GC_OBJECT(list,t1->lcoeff),g1324UU);
          } 
        GC_OBJECT(list,g1323UU);} 
      Result = choco_makeLinComb_list(g1323UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),1);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.18 expanded for static typing
// [!==(a:integer, t:Term) : IntConstraint => t !== a]
// [!==(x:IntVar, t:Term) : IntConstraint => t !== x]
// [!==(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => t2 !== t1]
// [!==(t1:BinTerm, t2:LinTerm) : IntConstraint => t2 !== t1]
/* The c++ function for: !==(a:integer,t:UnTerm) [] */
IntConstraint * claire__I_equal_equal_integer2(int a,UnTerm *t)
{ return (claire__I_equal_equal_UnTerm3(t,a));} 


/* The c++ function for: !==(a:integer,t:BinTerm) [] */
IntConstraint * claire__I_equal_equal_integer3(int a,BinTerm *t)
{ return (claire__I_equal_equal_BinTerm2(t,a));} 


/* The c++ function for: !==(a:integer,t:LinTerm) [] */
OID  claire__I_equal_equal_integer4(int a,LinTerm *t)
{ return (_oid_(claire__I_equal_equal_LinTerm1(t,a)));} 


/* The c++ function for: !==(x:IntVar,t:UnTerm) [] */
IntConstraint * claire__I_equal_equal_IntVar3(IntVar *x,UnTerm *t)
{ return (claire__I_equal_equal_UnTerm4(t,x));} 


/* The c++ function for: !==(x:IntVar,t:BinTerm) [] */
OID  claire__I_equal_equal_IntVar4(IntVar *x,BinTerm *t)
{ return (_oid_(claire__I_equal_equal_BinTerm3(t,x)));} 


/* The c++ function for: !==(x:IntVar,t:LinTerm) [] */
OID  claire__I_equal_equal_IntVar5(IntVar *x,LinTerm *t)
{ return (_oid_(claire__I_equal_equal_LinTerm2(t,x)));} 


/* The c++ function for: !==(t1:UnTerm,t2:BinTerm) [] */
OID  claire__I_equal_equal_UnTerm1(UnTerm *t1,BinTerm *t2)
{ return (_oid_(claire__I_equal_equal_BinTerm4(t2,t1)));} 


/* The c++ function for: !==(t1:UnTerm,t2:LinTerm) [] */
OID  claire__I_equal_equal_UnTerm2(UnTerm *t1,LinTerm *t2)
{ return (_oid_(claire__I_equal_equal_LinTerm3(t2,t1)));} 


/* The c++ function for: !==(t1:BinTerm,t2:LinTerm) [] */
OID  claire__I_equal_equal_BinTerm1(BinTerm *t1,LinTerm *t2)
{ return (_oid_(claire__I_equal_equal_LinTerm4(t2,t1)));} 


// v1.010 introduce general linear disequalities
/* The c++ function for: !==(t:UnTerm,c:integer) [] */
UnIntConstraint * claire__I_equal_equal_UnTerm3(UnTerm *t,int c)
{ GC_BIND;
  { UnIntConstraint *Result ;
    { NotEqualxc * _CL_obj = ((NotEqualxc *) GC_OBJECT(NotEqualxc,new_object_class(choco._NotEqualxc)));
      (_CL_obj->v1 = t->v1);
      (_CL_obj->cste = ((t->sign1 == CTRUE) ?
        (c-t->cste) :
        (t->cste-c) ));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t:UnTerm,x:IntVar) [] */
IntConstraint * claire__I_equal_equal_UnTerm4(UnTerm *t,IntVar *x)
{ if (t->sign1 != CTRUE) 
  { { IntConstraint *Result ;
      Result = choco_makeLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(choco._IntVar,2,_oid_(x),GC_OID(_oid_(t->v1))),(-t->cste),3);
      return (Result);} 
     } 
  else{ GC_BIND;
    { IntConstraint *Result ;
      { NotEqualxyc * _CL_obj = ((NotEqualxyc *) GC_OBJECT(NotEqualxyc,new_object_class(choco._NotEqualxyc)));
        (_CL_obj->v1 = x);
        (_CL_obj->v2 = t->v1);
        (_CL_obj->cste = t->cste);
        Result = _CL_obj;
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: !==(t1:UnTerm,t2:UnTerm) [] */
IntConstraint * claire__I_equal_equal_UnTerm5(UnTerm *t1,UnTerm *t2)
{ GC_BIND;
  { IntConstraint *Result ;
    { int  newcste = ((t1->sign1 == CTRUE) ?
        (t2->cste-t1->cste) :
        (t1->cste-t2->cste) );
      if (t1->sign1 != t2->sign1)
       Result = choco_makeLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(choco._IntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t2->v1))),(-newcste),3);
      else { NotEqualxyc * _CL_obj = ((NotEqualxyc *) GC_OBJECT(NotEqualxyc,new_object_class(choco._NotEqualxyc)));
          (_CL_obj->v1 = t1->v1);
          (_CL_obj->v2 = t2->v1);
          (_CL_obj->cste = newcste);
          Result = _CL_obj;
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:BinTerm,c:integer) [] */
IntConstraint * claire__I_equal_equal_BinTerm2(BinTerm *t1,int c)
{ GC_BIND;
  { IntConstraint *Result ;
    { int  newcste = ((t1->sign1 == CTRUE) ?
        (c-t1->cste) :
        (t1->cste-c) );
      if (t1->sign1 != t1->sign2)
       { NotEqualxyc * _CL_obj = ((NotEqualxyc *) GC_OBJECT(NotEqualxyc,new_object_class(choco._NotEqualxyc)));
        (_CL_obj->v1 = t1->v1);
        (_CL_obj->v2 = t1->v2);
        (_CL_obj->cste = newcste);
        Result = _CL_obj;
        } 
      else Result = choco_makeLinComb_list(list::alloc(Kernel._integer,2,1,1),list::alloc(choco._IntVar,2,GC_OID(_oid_(t1->v1)),GC_OID(_oid_(t1->v2))),(-newcste),3);
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:BinTerm,x:IntVar) [] */
LinComb * claire__I_equal_equal_BinTerm3(BinTerm *t1,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1325UU;
      { OID v_bag;
        GC_ANY(g1325UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1325UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1325UU)->addFast(v_bag);
        ((list *) g1325UU)->addFast(-1);} 
      Result = choco_makeLinComb_list(g1325UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        _oid_(x)),t1->cste,3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:BinTerm,t2:UnTerm) [] */
LinComb * claire__I_equal_equal_BinTerm4(BinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1326UU;
      { OID v_bag;
        GC_ANY(g1326UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1326UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1326UU)->addFast(v_bag);
        ((list *) g1326UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1326UU,list::alloc(choco._IntVar,3,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:BinTerm,t2:BinTerm) [] */
LinComb * claire__I_equal_equal_BinTerm5(BinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1327UU;
      { OID v_bag;
        GC_ANY(g1327UU= list::empty(Kernel._integer));
        if (t1->sign1 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1327UU)->addFast(v_bag);
        if (t1->sign2 == CTRUE)
         v_bag = 1;
        else v_bag = -1;
          ((list *) g1327UU)->addFast(v_bag);
        ((list *) g1327UU)->addFast((-((t2->sign1 == CTRUE) ?
          1 :
          -1 )));
        ((list *) g1327UU)->addFast((-((t2->sign2 == CTRUE) ?
          1 :
          -1 )));} 
      Result = choco_makeLinComb_list(g1327UU,list::alloc(choco._IntVar,4,GC_OID(_oid_(t1->v1)),
        GC_OID(_oid_(t1->v2)),
        GC_OID(_oid_(t2->v1)),
        GC_OID(_oid_(t2->v2))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:LinTerm,c:integer) [] */
LinComb * claire__I_equal_equal_LinTerm1(LinTerm *t1,int c)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,t1->lcoeff),GC_OBJECT(list,t1->lvars),(t1->cste-c),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:LinTerm,x:IntVar) [] */
LinComb * claire__I_equal_equal_LinTerm2(LinTerm *t1,IntVar *x)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast(-1)),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(_oid_(x))),t1->cste,3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:LinTerm,t2:UnTerm) [] */
LinComb * claire__I_equal_equal_LinTerm3(LinTerm *t1,UnTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,GC_OBJECT(list,t1->lcoeff)->addFast((-((t2->sign1 == CTRUE) ?
      1 :
      -1 )))),GC_OBJECT(list,GC_OBJECT(list,t1->lvars)->addFast(GC_OID(_oid_(t2->v1)))),(t1->cste-t2->cste),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:LinTerm,t2:BinTerm) [] */
LinComb * claire__I_equal_equal_LinTerm4(LinTerm *t1,BinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    Result = choco_makeLinComb_list(GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lcoeff),list::alloc(Kernel._integer,2,(-((t2->sign1 == CTRUE) ?
      1 :
      -1 )),(-((t2->sign2 == CTRUE) ?
      1 :
      -1 ))))),GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),list::alloc(choco._IntVar,2,GC_OID(_oid_(t2->v1)),GC_OID(_oid_(t2->v2))))),(t1->cste-t2->cste),3);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: !==(t1:LinTerm,t2:LinTerm) [] */
LinComb * claire__I_equal_equal_LinTerm5(LinTerm *t1,LinTerm *t2)
{ GC_BIND;
  { LinComb *Result ;
    { list * g1328UU;
      { { list * g1329UU;
          { { bag *v_list; OID v_val;
              OID cf,CLcount;
              v_list = GC_OBJECT(list,t2->lcoeff);
               g1329UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { cf = (*(v_list))[CLcount];
                v_val = (-cf);
                
                (*((list *) g1329UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g1329UU);} 
          g1328UU = append_list(GC_OBJECT(list,t1->lcoeff),g1329UU);
          } 
        GC_OBJECT(list,g1328UU);} 
      Result = choco_makeLinComb_list(g1328UU,GC_OBJECT(list,append_list(GC_OBJECT(list,t1->lvars),GC_OBJECT(list,t2->lvars))),(t1->cste-t2->cste),3);
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 5: Boolean connectors                                     *
// ********************************************************************
// ----- Disjunctions -----------
// 1. Associative rules for building disjunctions from two disjunctions
/* The c++ function for: or(c1:LargeDisjunction,c2:LargeDisjunction) [] */
LargeDisjunction * claire_or_LargeDisjunction1(LargeDisjunction *c1,LargeDisjunction *c2)
{ GC_BIND;
  { LargeDisjunction *Result ;
    { LargeDisjunction * c;
      { { LargeDisjunction * _CL_obj = ((LargeDisjunction *) GC_OBJECT(LargeDisjunction,new_object_class(choco._LargeDisjunction)));
          (_CL_obj->lconst = append_list(GC_OBJECT(list,c1->lconst),GC_OBJECT(list,c2->lconst)));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// claire3 port: strongly typed lists
/* The c++ function for: or(c1:Disjunction,c2:Disjunction) [] */
LargeDisjunction * claire_or_Disjunction1(Disjunction *c1,Disjunction *c2)
{ GC_BIND;
  { LargeDisjunction *Result ;
    { LargeDisjunction * c;
      { { LargeDisjunction * _CL_obj = ((LargeDisjunction *) GC_OBJECT(LargeDisjunction,new_object_class(choco._LargeDisjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,4,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            GC_OID(_oid_(c2->const1)),
            GC_OID(_oid_(c2->const2))));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: or(c1:LargeDisjunction,c2:Disjunction) [] */
LargeDisjunction * claire_or_LargeDisjunction2(LargeDisjunction *c1,Disjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const1)));
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  { LargeDisjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: or(c1:Disjunction,c2:LargeDisjunction) [] */
LargeDisjunction * claire_or_Disjunction2(Disjunction *c1,LargeDisjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const1)));
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c2);
  { LargeDisjunction *Result ;
    Result = c2;
    GC_UNBIND; return (Result);} 
  } 


// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v0.21 <fl> initialize lstatus -> v1.02 done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
// claire3 port: strongly typed lists
/* The c++ function for: or(c1:Disjunction,c2:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Conjunction) U LargeConjunction)) [] */
LargeDisjunction * claire_or_Disjunction3(Disjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  { LargeDisjunction *Result ;
    { LargeDisjunction * c;
      { { LargeDisjunction * _CL_obj = ((LargeDisjunction *) GC_OBJECT(LargeDisjunction,new_object_class(choco._LargeDisjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,3,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            _oid_(c2)));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeDisjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: or(c1:LargeDisjunction,c2:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Conjunction) U LargeConjunction)) [] */
LargeDisjunction * claire_or_LargeDisjunction3(LargeDisjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(_oid_(c2));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  { LargeDisjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the "or" operator is commutative 
/* The c++ function for: or(c1:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Conjunction) U LargeConjunction),c2:Disjunction) [] */
LargeDisjunction * claire_or_AbstractConstraint1(AbstractConstraint *c1,Disjunction *c2)
{ return (claire_or_Disjunction3(c2,c1));} 


/* The c++ function for: or(c1:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Conjunction) U LargeConjunction),c2:LargeDisjunction) [] */
LargeDisjunction * claire_or_AbstractConstraint2(AbstractConstraint *c1,LargeDisjunction *c2)
{ return (claire_or_LargeDisjunction3(c2,c1));} 


// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
/* The c++ function for: or(c1:((IntConstraint U SetConstraint) U CompositeConstraint),c2:((IntConstraint U SetConstraint) U CompositeConstraint)) [] */
Disjunction * claire_or_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { Disjunction *Result ;
    { Disjunction * d;
      { { Disjunction * _CL_obj = ((Disjunction *) GC_OBJECT(Disjunction,new_object_class(choco._Disjunction)));
          (_CL_obj->const1 = c1);
          (_CL_obj->const2 = c2);
          d = _CL_obj;
          } 
        GC_OBJECT(Disjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 4. additional (non binary) method definition (new in 0.9901)
/* The c++ function for: or(l:list[AbstractConstraint]) [] */
LargeDisjunction * claire_or_list(list *l)
{ GC_BIND;
  { LargeDisjunction *Result ;
    { ClaireObject *V_CC ;
      if (l->length == 0)
       close_exception(((general_error *) (*Core._general_error)(_string_("empty disjunction is not a valid constraint"),
        _oid_(Kernel.nil))));
      else { LargeDisjunction * c;
          { { LargeDisjunction * _CL_obj = ((LargeDisjunction *) GC_OBJECT(LargeDisjunction,new_object_class(choco._LargeDisjunction)));
              { LargeCompositeConstraint * g1330; 
                list * g1331;
                g1330 = _CL_obj;
                { bag *v_list; OID v_val;
                  OID subc,CLcount;
                  v_list = l;
                   g1331 = v_list->clone(choco._AbstractConstraint);
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { subc = (*(v_list))[CLcount];
                    v_val = subc;
                    
                    (*((list *) g1331))[CLcount] = v_val;} 
                  } 
                (g1330->lconst = g1331);} 
              c = _CL_obj;
              } 
            GC_OBJECT(LargeDisjunction,c);} 
          choco_closeBoolConstraint_LargeCompositeConstraint(c);
          V_CC = c;
          } 
        Result= (LargeDisjunction *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// ----- Conjunctions -----------
// 1. Associative rules for building conjunctions from two conjunctions
// claire3 port: strongly typed lists
/* The c++ function for: and(c1:Conjunction,c2:Conjunction) [] */
LargeConjunction * claire_and_Conjunction1(Conjunction *c1,Conjunction *c2)
{ GC_BIND;
  { LargeConjunction *Result ;
    { LargeConjunction * c;
      { { LargeConjunction * _CL_obj = ((LargeConjunction *) GC_OBJECT(LargeConjunction,new_object_class(choco._LargeConjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,4,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            GC_OID(_oid_(c2->const1)),
            GC_OID(_oid_(c2->const2))));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: and(c1:LargeConjunction,c2:LargeConjunction) [] */
LargeConjunction * claire_and_LargeConjunction1(LargeConjunction *c1,LargeConjunction *c2)
{ GC_BIND;
  { LargeConjunction *Result ;
    { LargeConjunction * c;
      { { LargeConjunction * _CL_obj = ((LargeConjunction *) GC_OBJECT(LargeConjunction,new_object_class(choco._LargeConjunction)));
          (_CL_obj->lconst = append_list(GC_OBJECT(list,c1->lconst),GC_OBJECT(list,c2->lconst)));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: and(c1:LargeConjunction,c2:Conjunction) [] */
LargeConjunction * claire_and_LargeConjunction2(LargeConjunction *c1,Conjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const1)));
  GC_OBJECT(list,c1->lconst)->addFast(GC_OID(_oid_(c2->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  { LargeConjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: and(c1:Conjunction,c2:LargeConjunction) [] */
LargeConjunction * claire_and_Conjunction2(Conjunction *c1,LargeConjunction *c2)
{ GC_BIND;
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const1)));
  GC_OBJECT(list,c2->lconst)->addFast(GC_OID(_oid_(c1->const2)));
  choco_closeBoolConstraint_LargeCompositeConstraint(c2);
  { LargeConjunction *Result ;
    Result = c2;
    GC_UNBIND; return (Result);} 
  } 


// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v1.02 status initialization is done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
// claire3 port: strongly typed lists
/* The c++ function for: and(c1:Conjunction,c2:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Disjunction) U LargeDisjunction)) [] */
LargeConjunction * claire_and_Conjunction3(Conjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  { LargeConjunction *Result ;
    { LargeConjunction * c;
      { { LargeConjunction * _CL_obj = ((LargeConjunction *) GC_OBJECT(LargeConjunction,new_object_class(choco._LargeConjunction)));
          (_CL_obj->lconst = list::alloc(choco._AbstractConstraint,3,GC_OID(_oid_(c1->const1)),
            GC_OID(_oid_(c1->const2)),
            _oid_(c2)));
          c = _CL_obj;
          } 
        GC_OBJECT(LargeConjunction,c);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: and(c1:LargeConjunction,c2:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Disjunction) U LargeDisjunction)) [] */
LargeConjunction * claire_and_LargeConjunction3(LargeConjunction *c1,AbstractConstraint *c2)
{ GC_BIND;
  GC_OBJECT(list,c1->lconst)->addFast(_oid_(c2));
  choco_closeBoolConstraint_LargeCompositeConstraint(c1);
  { LargeConjunction *Result ;
    Result = c1;
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the "and" operator is commutative 
/* The c++ function for: and(c1:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Disjunction) U LargeDisjunction),c2:Conjunction) [] */
LargeConjunction * claire_and_AbstractConstraint1(AbstractConstraint *c1,Conjunction *c2)
{ return (claire_and_Conjunction3(c2,c1));} 


/* The c++ function for: and(c1:((((((IntConstraint U SetConstraint) U Cardinality) U Guard) U Equiv) U Disjunction) U LargeDisjunction),c2:LargeConjunction) [] */
LargeConjunction * claire_and_AbstractConstraint2(AbstractConstraint *c1,LargeConjunction *c2)
{ return (claire_and_LargeConjunction3(c2,c1));} 


// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
/* The c++ function for: and(c1:((IntConstraint U SetConstraint) U CompositeConstraint),c2:((IntConstraint U SetConstraint) U CompositeConstraint)) [] */
Conjunction * claire_and_AbstractConstraint3(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { Conjunction *Result ;
    { Conjunction * c;
      { { Conjunction * _CL_obj = ((Conjunction *) GC_OBJECT(Conjunction,new_object_class(choco._Conjunction)));
          c = _CL_obj;
          } 
        GC_OBJECT(Conjunction,c);} 
      (c->const1 = c1);
      (c->const2 = c2);
      choco_closeBoolConstraint_BinCompositeConstraint(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 4. additional (non binary) method definition (new in 0.9901)
/* The c++ function for: and(l:list[AbstractConstraint]) [] */
LargeConjunction * claire_and_list(list *l)
{ GC_BIND;
  { LargeConjunction *Result ;
    { ClaireObject *V_CC ;
      if (l->length == 0)
       close_exception(((general_error *) (*Core._general_error)(_string_("empty conjunction is not a valid constraint"),
        _oid_(Kernel.nil))));
      else { LargeConjunction * c;
          { { LargeConjunction * _CL_obj = ((LargeConjunction *) GC_OBJECT(LargeConjunction,new_object_class(choco._LargeConjunction)));
              { LargeCompositeConstraint * g1332; 
                list * g1333;
                g1332 = _CL_obj;
                { bag *v_list; OID v_val;
                  OID subc,CLcount;
                  v_list = l;
                   g1333 = v_list->clone(choco._AbstractConstraint);
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { subc = (*(v_list))[CLcount];
                    v_val = subc;
                    
                    (*((list *) g1333))[CLcount] = v_val;} 
                  } 
                (g1332->lconst = g1333);} 
              c = _CL_obj;
              } 
            GC_OBJECT(LargeConjunction,c);} 
          choco_closeBoolConstraint_LargeCompositeConstraint(c);
          V_CC = c;
          } 
        Result= (LargeConjunction *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// ----- Complete and lightweight guards + equivalence -----------
// v0.97: the "implies" guard now achieves both ways consistency (back propagating guard onto the triggerring condition)
/* The c++ function for: implies(c1:((IntConstraint U SetConstraint) U CompositeConstraint),c2:((IntConstraint U SetConstraint) U CompositeConstraint)) [] */
CompositeConstraint * claire_implies_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { CompositeConstraint *Result ;
    { OID  oppc1 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) c1))));
      Result = OBJECT(CompositeConstraint,(*Core.ClaireOr)(oppc1,
        _oid_(c2)));
      } 
    GC_UNBIND; return (Result);} 
  } 


// this is the old implies: a lightweight implementation (incomplete propagation)
/* The c++ function for: ifThen(c1:((IntConstraint U SetConstraint) U CompositeConstraint),c2:((IntConstraint U SetConstraint) U CompositeConstraint)) [] */
Guard * claire_ifThen_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { Guard *Result ;
    { Guard * g;
      { { Guard * _CL_obj = ((Guard *) GC_OBJECT(Guard,new_object_class(choco._Guard)));
          g = _CL_obj;
          } 
        GC_OBJECT(Guard,g);} 
      (g->const1 = c1);
      (g->const2 = c2);
      choco_closeBoolConstraint_BinCompositeConstraint(g);
      Result = g;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.29: there is now an explicit correspondance between
// the indices of variables in e.const1 and in e.oppositeConst1
// most of the time, computeVarIdxInOpposite computes the same index, but not for linear constraint
/* The c++ function for: ifOnlyIf(c1:((IntConstraint U SetConstraint) U CompositeConstraint),c2:((IntConstraint U SetConstraint) U CompositeConstraint)) [] */
Equiv * claire_ifOnlyIf_AbstractConstraint(AbstractConstraint *c1,AbstractConstraint *c2)
{ GC_BIND;
  { Equiv *Result ;
    { Equiv * e;
      { { Equiv * _CL_obj = ((Equiv *) GC_OBJECT(Equiv,new_object_class(choco._Equiv)));
          e = _CL_obj;
          } 
        GC_OBJECT(Equiv,e);} 
      (e->const1 = c1);
      (e->const2 = c2);
      choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp(e);
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ----- Cardinality constraints -----------
// v0.9904 
// claire3 port: strongly typed lists
/* The c++ function for: makeCardinalityConstraint(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:IntVar,atleast:boolean,atmost:boolean) [] */
Cardinality * choco_makeCardinalityConstraint_list(list *l,IntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost)
{ GC_BIND;
  ;{ Cardinality *Result ;
    { int  n = l->length;
      Cardinality * c;
      { { Cardinality * _CL_obj = ((Cardinality *) GC_OBJECT(Cardinality,new_object_class(choco._Cardinality)));
          { LargeCompositeConstraint * g1334; 
            list * g1335;
            g1334 = _CL_obj;
            { bag *v_list; OID v_val;
              OID c,CLcount;
              v_list = l;
               g1335 = v_list->clone(choco._AbstractConstraint);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { c = (*(v_list))[CLcount];
                v_val = c;
                
                (*((list *) g1335))[CLcount] = v_val;} 
              } 
            (g1334->lconst = g1335);} 
          (_CL_obj->nbConst = n);
          (_CL_obj->additionalVars = list::alloc(choco._IntVar,1,_oid_(v)));
          (_CL_obj->constrainOnInfNumber = atleast);
          (_CL_obj->constrainOnSupNumber = atmost);
          c = _CL_obj;
          } 
        GC_OBJECT(Cardinality,c);} 
      choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9904: extended API
/* The c++ function for: card(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:IntVar) [] */
Cardinality * choco_card_list1(list *l,IntVar *v)
{ return (choco_makeCardinalityConstraint_list(l,v,CTRUE,CTRUE));} 


/* The c++ function for: atleast(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:IntVar) [] */
Cardinality * choco_atleast_list1(list *l,IntVar *v)
{ return (choco_makeCardinalityConstraint_list(l,v,CTRUE,CFALSE));} 


/* The c++ function for: atmost(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:IntVar) [] */
Cardinality * choco_atmost_list1(list *l,IntVar *v)
{ return (choco_makeCardinalityConstraint_list(l,v,CFALSE,CTRUE));} 


// <thb> v0.93: allows an integer as second parameter
/* The c++ function for: card(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:integer) [] */
Cardinality * choco_card_list2(list *l,int v)
{ GC_BIND;
  { Cardinality *Result ;
    Result = choco_card_list1(l,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: atleast(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:integer) [] */
Cardinality * choco_atleast_list2(list *l,int v)
{ GC_BIND;
  { Cardinality *Result ;
    Result = choco_atleast_list1(l,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: atmost(l:list[((IntConstraint U SetConstraint) U CompositeConstraint)],v:integer) [] */
Cardinality * choco_atmost_list2(list *l,int v)
{ GC_BIND;
  { Cardinality *Result ;
    Result = choco_atmost_list1(l,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(v)));
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 6: global constraints                                     *
// ********************************************************************
//  OccurTerm: a temporary object representing occur(t,l)
// v0.9907 inherit from Term
// changed v0.28, v0.32
// claire3 port: strongly typed lists
/* The c++ function for: makeOccurConstraint(ot:OccurTerm,v:IntVar,atleast:boolean,atmost:boolean) [] */
Occurrence * choco_makeOccurConstraint_OccurTerm(OccurTerm *ot,IntVar *v,ClaireBoolean *atleast,ClaireBoolean *atmost)
{ GC_BIND;
  { Occurrence *Result ;
    { list * l = GC_OBJECT(list,ot->lvars);
      int  n = l->length;
      int  nb1 = 0;
      int  nb2 = 0;
      OID * isPos = GC_ARRAY(make_array_integer(n,Kernel._boolean,Kernel.ctrue));
      OID * isSur = GC_ARRAY(make_array_integer(n,Kernel._boolean,Kernel.cfalse));
      int  tgt = ot->target;
      Occurrence * c;
      { { Occurrence * _CL_obj = ((Occurrence *) GC_OBJECT(Occurrence,new_object_class(choco._Occurrence)));
          { LargeIntConstraint * g1337; 
            list * g1338;
            g1337 = _CL_obj;
            { list * g1339UU;
              { { bag *v_list; OID v_val;
                  OID vv,CLcount;
                  v_list = l;
                   g1339UU = v_list->clone(choco._IntVar);
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { vv = (*(v_list))[CLcount];
                    v_val = vv;
                    
                    (*((list *) g1339UU))[CLcount] = v_val;} 
                  } 
                GC_OBJECT(list,g1339UU);} 
              g1338 = g1339UU->addFast(_oid_(v));
              } 
            (g1337->vars = g1338);} 
          (_CL_obj->cste = tgt);
          c = _CL_obj;
          } 
        GC_OBJECT(Occurrence,c);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      { int  j = 1;
        int  g1336 = n;
        { OID gc_local;
          while ((j <= g1336))
          { // HOHO, GC_LOOP not needed !
            { IntVar * v = OBJECT(IntVar,(*(l))[j]);
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
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: occur(tgt:integer,l:list[IntVar]) [] */
OccurTerm * choco_occur_integer(int tgt,list *l)
{ GC_BIND;
  { OccurTerm *Result ;
    { OccurTerm * _CL_obj = ((OccurTerm *) GC_OBJECT(OccurTerm,new_object_class(choco._OccurTerm)));
      (_CL_obj->target = tgt);
      (_CL_obj->lvars = ((list *) copy_bag(l)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// Occurrence constraints are always stated as
//   occur(t,l) (==/>=/<=) x/v
// watchout: arithmetic is not complete.
/* The c++ function for: ==(ot:OccurTerm,x:IntVar) [] */
Occurrence * claire__equal_equal_OccurTerm1(OccurTerm *ot,IntVar *x)
{ return (choco_makeOccurConstraint_OccurTerm(ot,x,CTRUE,CTRUE));} 


/* The c++ function for: ==(x:IntVar,ot:OccurTerm) [] */
Occurrence * claire__equal_equal_IntVar6(IntVar *x,OccurTerm *ot)
{ return (claire__equal_equal_OccurTerm1(ot,x));} 


/* The c++ function for: ==(ot:OccurTerm,x:integer) [] */
Occurrence * claire__equal_equal_OccurTerm2(OccurTerm *ot,int x)
{ GC_BIND;
  { Occurrence *Result ;
    Result = choco_makeOccurConstraint_OccurTerm(ot,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)),CTRUE,CTRUE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(x:integer,ot:OccurTerm) [] */
Occurrence * claire__equal_equal_integer5(int x,OccurTerm *ot)
{ return (claire__equal_equal_OccurTerm2(ot,x));} 


/* The c++ function for: >=(ot:OccurTerm,x:IntVar) [] */
Occurrence * claire__sup_equal_OccurTerm1(OccurTerm *ot,IntVar *x)
{ return (choco_makeOccurConstraint_OccurTerm(ot,x,CTRUE,CFALSE));} 


/* The c++ function for: >=(ot:OccurTerm,x:integer) [] */
Occurrence * claire__sup_equal_OccurTerm2(OccurTerm *ot,int x)
{ GC_BIND;
  { Occurrence *Result ;
    Result = choco_makeOccurConstraint_OccurTerm(ot,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)),CTRUE,CFALSE);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: <=(ot:OccurTerm,x:IntVar) [] */
Occurrence * claire__inf_equal_OccurTerm1(OccurTerm *ot,IntVar *x)
{ return (choco_makeOccurConstraint_OccurTerm(ot,x,CFALSE,CTRUE));} 


/* The c++ function for: <=(ot:OccurTerm,x:integer) [] */
Occurrence * claire__inf_equal_OccurTerm2(OccurTerm *ot,int x)
{ GC_BIND;
  { Occurrence *Result ;
    Result = choco_makeOccurConstraint_OccurTerm(ot,GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)),CFALSE,CTRUE);
    GC_UNBIND; return (Result);} 
  } 


// v0.24 Symmetrical API's (a request from Michel Lemaitre)
/* The c++ function for: >=(x:IntVar,ot:OccurTerm) [] */
Occurrence * claire__sup_equal_IntVar6(IntVar *x,OccurTerm *ot)
{ return (claire__inf_equal_OccurTerm1(ot,x));} 


/* The c++ function for: >=(x:integer,ot:OccurTerm) [] */
Occurrence * claire__sup_equal_integer5(int x,OccurTerm *ot)
{ return (claire__inf_equal_OccurTerm2(ot,x));} 


/* The c++ function for: <=(x:IntVar,ot:OccurTerm) [] */
Occurrence * claire__inf_equal_IntVar6(IntVar *x,OccurTerm *ot)
{ return (claire__sup_equal_OccurTerm1(ot,x));} 


/* The c++ function for: <=(x:integer,ot:OccurTerm) [] */
Occurrence * claire__inf_equal_integer6(int x,OccurTerm *ot)
{ return (claire__sup_equal_OccurTerm2(ot,x));} 


//  for Eric: EltTerm: a temporary object representing l[I]
// 1.020: the nth method is replaced by getNth
// v0.34: allows positive UnTerm as indexes (I + c)
/* The c++ function for: getNth(l:list[integer],i:UnTerm) [] */
EltTerm * choco_getNth_list1(list *l,UnTerm *i)
{ GC_BIND;
  if (i->sign1 != CTRUE)
   close_exception(((general_error *) (*Core._general_error)(_string_("Negative indexes (-I + c) are not allowed in an element constraint"),
    _oid_(Kernel.nil))));
  { EltTerm *Result ;
    { EltTerm * _CL_obj = ((EltTerm *) GC_OBJECT(EltTerm,new_object_class(choco._EltTerm)));
      (_CL_obj->lvalues = l);
      (_CL_obj->indexVar = i->v1);
      (_CL_obj->cste = i->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNth(l:list[integer],i:IntVar) [] */
EltTerm * choco_getNth_list2(list *l,IntVar *i)
{ GC_BIND;
  { EltTerm *Result ;
    { EltTerm * _CL_obj = ((EltTerm *) GC_OBJECT(EltTerm,new_object_class(choco._EltTerm)));
      (_CL_obj->lvalues = l);
      (_CL_obj->indexVar = i);
      (_CL_obj->cste = 0);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:EltTerm,x:IntVar) [] */
Elt * claire__equal_equal_EltTerm1(EltTerm *t,IntVar *x)
{ GC_BIND;
  { Elt *Result ;
    Result = choco_makeEltConstraint_list(t->lvalues,t->indexVar,x,t->cste);
    GC_UNBIND; return (Result);} 
  } 


//v0.34 added the offset t.cste
//v0.93: added the reverse syntax
/* The c++ function for: ==(x:IntVar,t:EltTerm) [] */
Elt * claire__equal_equal_IntVar7(IntVar *x,EltTerm *t)
{ GC_BIND;
  { Elt *Result ;
    Result = choco_makeEltConstraint_list(t->lvalues,t->indexVar,x,t->cste);
    GC_UNBIND; return (Result);} 
  } 


//v0.93: allows equality with an integer
/* The c++ function for: ==(t:EltTerm,x:integer) [] */
Elt * claire__equal_equal_EltTerm2(EltTerm *t,int x)
{ GC_BIND;
  { Elt *Result ;
    Result = choco_makeEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(IntVar,t->indexVar),GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)),t->cste);
    GC_UNBIND; return (Result);} 
  } 


//v0.34 added the offset t.cste
/* The c++ function for: ==(x:integer,t:EltTerm) [] */
Elt * claire__equal_equal_integer6(int x,EltTerm *t)
{ GC_BIND;
  { Elt *Result ;
    Result = choco_makeEltConstraint_list(GC_OBJECT(list,t->lvalues),GC_OBJECT(IntVar,t->indexVar),GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)),t->cste);
    GC_UNBIND; return (Result);} 
  } 


// v0.9907 elt with 5 arguments becomes makeElt2Term
/* The c++ function for: makeElt2Term(l:table[range:({integer})],n1:integer,n2:integer,i:IntVar,j:IntVar) [] */
Elt2Term * choco_makeElt2Term_table(table *l,int n1,int n2,IntVar *i,IntVar *j)
{ GC_BIND;
  { Elt2Term *Result ;
    { Elt2Term * _CL_obj = ((Elt2Term *) GC_OBJECT(Elt2Term,new_object_class(choco._Elt2Term)));
      (_CL_obj->valueTable = l);
      (_CL_obj->index1Var = i);
      (_CL_obj->index2Var = j);
      (_CL_obj->cste = 0);
      (_CL_obj->dim1 = n1);
      (_CL_obj->dim2 = n2);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// 1.020 renamed nth into getNth
/* The c++ function for: getNth(l:table[range:({integer})],i:IntVar,j:IntVar) [] */
Elt2Term * choco_getNth_table(table *l,IntVar *i,IntVar *j)
{ return (choco_makeElt2Term_table(l,
    (*Core.size)((*(OBJECT(bag,(*Kernel.arg)(_oid_(l->domain)))))[1]),
    (*Core.size)((*(OBJECT(bag,(*Kernel.arg)(_oid_(l->domain)))))[2]),
    i,
    j));} 


/* The c++ function for: ==(t:Elt2Term,x:IntVar) [] */
Elt2 * claire__equal_equal_Elt2Term1(Elt2Term *t,IntVar *x)
{ GC_BIND;
  { Elt2 *Result ;
    Result = choco_makeElt2Constraint_table(t->valueTable,
      t->dim1,
      t->dim2,
      t->index1Var,
      t->index2Var,
      x);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(x:IntVar,t:Elt2Term) [] */
Elt2 * claire__equal_equal_IntVar8(IntVar *x,Elt2Term *t)
{ GC_BIND;
  { Elt2 *Result ;
    Result = choco_makeElt2Constraint_table(t->valueTable,
      t->dim1,
      t->dim2,
      t->index1Var,
      t->index2Var,
      x);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(t:Elt2Term,x:integer) [] */
Elt2 * claire__equal_equal_Elt2Term2(Elt2Term *t,int x)
{ GC_BIND;
  { Elt2 *Result ;
    Result = choco_makeElt2Constraint_table(t->valueTable,
      t->dim1,
      t->dim2,
      GC_OBJECT(IntVar,t->index1Var),
      GC_OBJECT(IntVar,t->index2Var),
      GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: ==(x:integer,t:Elt2Term) [] */
Elt2 * claire__equal_equal_integer7(int x,Elt2Term *t)
{ GC_BIND;
  { Elt2 *Result ;
    Result = choco_makeElt2Constraint_table(t->valueTable,
      t->dim1,
      t->dim2,
      GC_OBJECT(IntVar,t->index1Var),
      GC_OBJECT(IntVar,t->index2Var),
      GC_OBJECT(IntVar,choco_makeConstantIntVar_integer(x)));
    GC_UNBIND; return (Result);} 
  } 

