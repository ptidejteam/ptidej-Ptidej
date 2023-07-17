/***** CLAIRE Compilation of file d:\claire\v3.2\src\meta\function.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:02:43 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>

//+-------------------------------------------------------------+
//| CLAIRE                                                      |
//| function.cl                                                 |
//| Copyright (C) 1994 - 2000 Yves Caseau. All Rights Reserved  |
//| cf. copyright info in file object.cl: about()               |
//+-------------------------------------------------------------+
// --------------------------------------------------------------------
// This file hold the methods that are defined by an external function
// and those that apply to a primitive type
// --------------------------------------------------------------------
// *********************************************************************
// *  Table of contents                                                *
// *   Part 1: Basics of pretty printing                               *
// *   Part 2: Methods for CLAIRE objects                              *
// *   Part 3: System Methods                                          *
// *   Part 4: Methods for Native entities                             *
// *********************************************************************
// we find here what is necessary for the minimal kernel of CLAIRE
// ==============================================================
// *********************************************************************
// *   Part 1: Basics of pretty printing                               *
// *********************************************************************
// we use a nice object
// buffered print
/* The c++ function for: print_in_string(_CL_obj:void) [SLOT_UPDATE] */
void  print_in_string_void()
{ (Core.pretty->cprevious = use_as_output_port(Core.pretty->cpretty));
  if (equal(ClAlloc->import(Kernel._port,(int *) Core.pretty->cprevious),ClAlloc->import(Kernel._port,(int *) Core.pretty->cpretty)) == CTRUE)
   close_exception(((general_error *) (*Core._general_error)(_string_("[123] YOU ARE USING PRINT_in_string_void RECURSIVELY"),
    _oid_(Kernel.nil))));
  } 


/* The c++ function for: end_of_string(_CL_obj:void) [0] */
char * end_of_print_void()
{ { char *Result ;
    { char * s = string_I_port(Core.pretty->cpretty);
      set_length_port(Core.pretty->cpretty,0);
      use_as_output_port(Core.pretty->cprevious);
      Result = s;
      } 
    return (Result);} 
  } 


/* The c++ function for: mClaire/buffer_length(_CL_obj:void) [0] */
int  buffer_length_void()
{ return (length_port(Core.pretty->cpretty));} 


/* The c++ function for: mClaire/buffer_set_length(i:integer) [0] */
void  buffer_set_length_integer(int i)
{ set_length_port(Core.pretty->cpretty,i);
  } 


// a method for calling the printer without issuing a message
// here we assume that self_print is always defined as a function
/* The c++ function for: apply_self_print(self:any) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  apply_self_print_any(OID self)
{ if (INHERIT(OWNER(self),Kernel._thing))
   princ_symbol(OBJECT(thing,self)->name);
  else if (INHERIT(OWNER(self),Kernel._class))
   princ_symbol(OBJECT(ClaireClass,self)->name);
  else if (INHERIT(OWNER(self),Kernel._integer))
   princ_integer(self);
  else if (Kernel._string == OWNER(self))
   (*Kernel.self_print)(self);
  else { method * _Zprop = ((method *) _at_property1(Kernel.self_print,OWNER(self)));
      if ((boolean_I_any(_oid_(_Zprop)) == CTRUE) && 
          (_Zprop->functional != (NULL)))
       { list * l = _Zprop->srange;
        fcall1(_Zprop->evaluate,OBJECT(ClaireClass,(*(l))[1]),self,OBJECT(ClaireClass,(*(l))[2]));
        } 
      else { ClaireHandler c_handle = ClaireHandler();
          if ERROR_IN 
          { (*Kernel.self_print)(self);
            ClEnv->cHandle--;} 
          else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
          { c_handle.catchIt();{ princ_string("<unprintable:");
              print_any(_oid_(OWNER(self)));
              princ_string(">");
              } 
            } 
          else PREVIOUS_HANDLER;} 
        } 
    } 


// some basic definitions
/* The c++ function for: self_print(self:any) [NEW_ALLOC+SLOT_UPDATE] */
void  self_print_any_Core(OID self)
{ if (self == CNULL)
   princ_string("unknown");
  else { princ_string("<");
      print_any(_oid_(OWNER(self)));
      princ_string(">");
      } 
    } 


/* The c++ function for: self_print(self:boolean) [0] */
void  self_print_boolean_Core(ClaireBoolean *self)
{ if (self == CTRUE)
   princ_string("true");
  else princ_string("false");
    } 


/* The c++ function for: self_print(self:function) [0] */
void  self_print_function_Core(ClaireFunction *self)
{ princ_string("#'");
  princ_string(string_I_function(self));
  princ_string("");
  } 


// prints the name of a restriction. If we have a close property and if a
// short-cut is possible, we use it.
/* The c++ function for: self_print(self:restriction) [NEW_ALLOC] */
void  self_print_restriction_Core(restriction *self)
{ if ((self->selector == (NULL)) || 
      (self->domain == (NULL)))
   { princ_string("<");
    print_any(_oid_(OWNER(_oid_(self))));
    princ_string(">");
    } 
  else { property * p = self->selector;
      int  n = 0;
      ClaireClass * c = domain_I_restriction(self);
      { ITERATE(r);
        for (START(p->restrictions); NEXT(r);)
        if (domain_I_restriction(OBJECT(restriction,r)) == c)
         ++n;
        } 
      princ_symbol(p->name);
      princ_string(" @ ");
      { OID  g0045UU;
        if (n == 1)
         g0045UU = _oid_(c);
        else g0045UU = _oid_(self->domain);
          print_any(g0045UU);
        } 
      princ_string("");
      } 
    } 


// we are too far
/* The c++ function for: print(x:any) [NEW_ALLOC+SLOT_UPDATE] */
void  print_any(OID x)
{ if ((Core.pretty->pbreak == CTRUE) && 
      (Core.pretty->pprint == CTRUE))
   { int  b_index = buffer_length_void();
    ClaireBoolean * missed = CFALSE;
    int  _Zl = Core.pretty->index;
    if (short_enough_integer((b_index+10)) != CTRUE)
     { (Core.pretty->pprint = CFALSE);
      (Core.pretty->pbreak = CFALSE);
      print_any(x);
      (Core.pretty->pprint = CTRUE);
      } 
    else { { ClaireHandler c_handle = ClaireHandler();
          if ERROR_IN 
          { { (Core.pretty->pbreak = CFALSE);
              apply_self_print_any(x);
              (Core.pretty->pbreak = CTRUE);
              } 
            ClEnv->cHandle--;} 
          else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Core._much_too_far)) == CTRUE)
          { c_handle.catchIt();missed= CTRUE;
            } 
          else PREVIOUS_HANDLER;} 
        if (missed == CTRUE)
         { (Core.pretty->pprint = CTRUE);
          (Core.pretty->pbreak = CTRUE);
          buffer_set_length_integer(b_index);
          (Core.pretty->index = _Zl);
          apply_self_print_any(x);
          } 
        } 
      } 
  else apply_self_print_any(x);
    ;} 


/* The c++ function for: short_enough(self:integer) [0] */
ClaireBoolean * short_enough_integer(int self)
{ return (_inf_integer(self,Core.pretty->width));} 


// *********************************************************************
// *   Part 2: Methods for CLAIRE objects                              *
// *********************************************************************
// the instantiation body is a sequence of words from which the initialization
// of the object must be built.
//  copied_def = object (for object) + float (for float) + integer (for all)
//               + NULL for objects
// v3.2.12: use a condition that is coherent with ClReflect.cl : a slot defaut value must be placed
// unless it is a copied_def
/* The c++ function for: mClaire/complete!(self:object) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
ClaireObject * complete_I_object(ClaireObject *self)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { OID gc_local;
    ITERATE(s);
    for (START(self->isa->slots); NEXT(s);)
    { GC_LOOP;
      { property * p = OBJECT(restriction,s)->selector;
        ClaireClass * s2 = OBJECT(slot,s)->srange;
        OID  d = GC_OID(OBJECT(slot,s)->DEFAULT);
        if (d != CNULL)
         { OID  v = slot_get_object(self,OBJECT(slot,s)->index,OBJECT(slot,s)->srange);
          if ((v == CNULL) && 
              ((s2 != Kernel._object) && 
                  ((!INHERIT(OWNER(d),Kernel._integer)) && 
                    (s2 != Kernel._float))))
           update_property(p,
            self,
            OBJECT(slot,s)->index,
            OBJECT(slot,s)->srange,
            d);
          else if (equal(d,v) == CTRUE)
           { if (boolean_I_any(_oid_(p->multivalued_ask)) == CTRUE)
             { OID gc_local;
              ITERATE(y);
              bag *y_support;
              y_support = GC_OBJECT(bag,enumerate_any(d));
              for (START(y_support); NEXT(y);)
              { GC_LOOP;
                update_plus_relation(p,_oid_(self),y);
                GC_UNLOOP;} 
              } 
            else update_plus_relation(p,_oid_(self),d);
              } 
          } 
        } 
      GC_UNLOOP;} 
    } 
  { ClaireObject *Result ;
    { OID  m = GC_OID(_oid_(_at_property1(Kernel.close,OWNER(_oid_(self)))));
      Result = ((Kernel._method == OWNER(m)) ?
        OBJECT(ClaireObject,funcall_method1(OBJECT(method,m),_oid_(self))) :
        self );
      } 
    GC_UNBIND; return (Result);} 
  } 


// v3.0.41  obviously
//-------------------------- ENTITY   --------------------------------------
/* The c++ function for: not(self:any) [0] */
ClaireBoolean * not_any(OID self)
{ { ClaireBoolean *Result ;
    Result = ((self == Kernel.ctrue) ?
      CFALSE :
      ((self == Kernel.cfalse) ?
        CTRUE :
        ((boolean_I_any(self) != CTRUE) ?
          CTRUE :
          CFALSE ) ) );
    return (Result);} 
  } 


/* The c++ function for: !=(self:any,x:any) [0] */
ClaireBoolean * _I_equal_any(OID self,OID x)
{ { ClaireBoolean *Result ;
    Result = ((equal(self,x) == CTRUE) ?
      CFALSE :
      CTRUE );
    return (Result);} 
  } 


// gives the type of any object. This is open_coded.
/* The c++ function for: owner(self:any) [0] */
ClaireClass * owner_any(OID self)
{ return (OWNER(self));} 


// some useful methods
/* The c++ function for: known?(self:any) [0] */
ClaireBoolean * known_ask_any(OID self)
{ return (_I_equal_any(CNULL,self));} 


/* The c++ function for: unknown?(self:any) [0] */
ClaireBoolean * unknown_ask_any(OID self)
{ return (equal(CNULL,self));} 


// needed by the compiled code
/* The c++ function for: check_in(self:any,y:type) [RETURN_ARG] */
OID  check_in_any(OID self,ClaireType *y)
{ { OID Result = 0;
    if (belong_to(self,_oid_(y)) == CTRUE)
     Result = self;
    else close_exception(((general_error *) (*Core._general_error)(_string_("[124] the value ~S does not belong to the range ~S"),
        _oid_(list::alloc(2,self,_oid_(y))))));
      return (Result);} 
  } 


/* The c++ function for: check_in(self:bag,c:class,y:type) [0] */
bag * check_in_bag(bag *self,ClaireClass *c,ClaireType *y)
{ { bag *Result ;
    { ClaireObject *V_CC ;
      { ClaireBoolean * g0046I;
        { OID  g0047UU;
          { ITERATE(z);
            g0047UU= _oid_(CFALSE);
            for (START(self); NEXT(z);)
            if (belong_to(z,_oid_(y)) != CTRUE)
             { g0047UU = Kernel.ctrue;
              break;} 
            } 
          g0046I = not_any(g0047UU);
          } 
        
        if (g0046I == CTRUE) V_CC = cast_I_bag(self,y);
          else close_exception(((general_error *) (*Core._general_error)(_string_("[124] the value ~S does not belong to subtype[~S²]"),
          _oid_(list::alloc(2,_oid_(self),_oid_(y))))));
        } 
      Result= (bag *) V_CC;} 
    return (Result);} 
  } 


// new in v3.00.48
/* The c++ function for: <(self:any,x:any) [NEW_ALLOC] */
ClaireBoolean * _inf_any(OID self,OID x)
{ { ClaireBoolean *Result ;
    Result = ((equal(self,x) == CTRUE) ?
      CFALSE :
      OBJECT(ClaireBoolean,(*Kernel._inf_equal)(self,
        x)) );
    return (Result);} 
  } 


/* The c++ function for: >(self:any,x:any) [NEW_ALLOC] */
ClaireBoolean * _sup_any(OID self,OID x)
{ { ClaireBoolean *Result ;
    Result = ((equal(self,x) == CTRUE) ?
      CFALSE :
      OBJECT(ClaireBoolean,(*Kernel._inf_equal)(x,
        self)) );
    return (Result);} 
  } 


// >= is defined as a macro in file.cl
// ----------------------- CLASS ---------------------------------------------
// declares a class as ephemeral: the member set is not maintained
// v3.2.14 recusively applies to subclasses
/* The c++ function for: ephemeral(self:class) [SLOT_UPDATE+RETURN_ARG] */
OID  ephemeral_class(ClaireClass *self)
{ { OID Result = 0;
    { ITERATE(c);
      Result= _oid_(CFALSE);
      for (START(self->descendents); NEXT(c);)
      if ((OBJECT(ClaireClass,c)->instances->length != 0) || 
          (OBJECT(ClaireClass,c)->open <= 1))
       close_exception(((general_error *) (*Core._general_error)(_string_("[187] cannot declare ~S as ephemeral because of ~S"),
        _oid_(list::alloc(2,_oid_(self),c)))));
      else (OBJECT(ClaireClass,c)->open = ClEnv->ephemeral);
        } 
    return (Result);} 
  } 


// declares a class as an abtract class (without instances)
/* The c++ function for: abstract(c:class) [NEW_ALLOC+SLOT_UPDATE+SAFE_RESULT] */
OID  abstract_class(ClaireClass *c)
{ { OID Result = 0;
    { int  n = c->open;
      if (n == ClEnv->ephemeral)
       close_exception(((general_error *) (*Core._general_error)(_string_("[125] ephemeral classes cannot be abstract"),
        _oid_(Kernel.nil))));
      else { if (c->instances->length != 0)
           ;if (n == 2)
           (c->open = 0);
          else if (n == 1)
           (c->open = -1);
          } 
        Result = _oid_(c);
      } 
    return (Result);} 
  } 


// declares a class as totally defined in the hierarchy: no new subclasses can be added.
/* The c++ function for: final(c:class) [SLOT_UPDATE+SAFE_RESULT] */
OID  final_class(ClaireClass *c)
{ { OID Result = 0;
    { int  n = c->open;
      if (n == 3)
       close_exception(((general_error *) (*Core._general_error)(_string_("[126] ephemeral classes cannot be set as final"),
        _oid_(Kernel.nil))));
      else if (n == 2)
       (c->open = 1);
      else if (n == 0)
       (c->open = -1);
      Result = _oid_(c);
      } 
    return (Result);} 
  } 


//instantiation with and without a name
/* The c++ function for: new(self:class) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
ClaireObject * new_class1(ClaireClass *self)
{ GC_BIND;
  { ClaireObject *Result ;
    { ClaireObject * o;
      { { if (self->open <= 0)
           close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
            _oid_(list::alloc(1,_oid_(self))))));
          o = new_object_class(self);
          } 
        GC_OBJECT(ClaireObject,o);} 
      if (self->open != ClEnv->ephemeral)
       self->instances->addFast(_oid_(o));
      Result = complete_I_object(o);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: new_class1_type */
ClaireType * new_class1_type(ClaireType *self)
{ GC_BIND;
  { ClaireType *Result ;
    Result = glb_class(Kernel._object,GC_OBJECT(ClaireType,member_type(self)));
    GC_UNBIND; return (Result);} 
  } 


// v3.2.26
/* The c++ function for: new(self:class,%nom:symbol) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
thing * new_class2(ClaireClass *self,symbol *_Znom)
{ { thing *Result ;
    { ClaireObject *V_CC ;
      { thing * o;
        { if (self->open <= 0)
           close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
            _oid_(list::alloc(1,_oid_(self))))));
          o = new_thing_class(self,_Znom);
          } 
        V_CC = complete_I_object(o);
        } 
      Result= (thing *) V_CC;} 
    return (Result);} 
  } 


/* The c++ function for: new_class2_type */
ClaireType * new_class2_type(ClaireType *self,ClaireType *_Znom)
{ GC_BIND;
  { ClaireType *Result ;
    Result = glb_class(Kernel._thing,GC_OBJECT(ClaireType,member_type(self)));
    GC_UNBIND; return (Result);} 
  } 


// v3.2.26
// internal version
/* The c++ function for: new_object_class_type */
ClaireType * new_object_class_type(ClaireType *self)
{ GC_BIND;
  { ClaireType *Result ;
    Result = glb_class(Kernel._object,GC_OBJECT(ClaireType,member_type(self)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: new_thing_class_type */
ClaireType * new_thing_class_type(ClaireType *self,ClaireType *_Znom)
{ GC_BIND;
  { ClaireType *Result ;
    Result = glb_class(Kernel._thing,GC_OBJECT(ClaireType,member_type(self)));
    GC_UNBIND; return (Result);} 
  } 


// the smallest super_set of two sets
// there is always any, so it always returns a class
/* The c++ function for: meet(self:class,ens:class) [RETURN_ARG] */
ClaireClass * meet_class(ClaireClass *self,ClaireClass *ens)
{ { ClaireClass *Result ;
    { list * l1 = self->ancestors;
      list * l2 = ens->ancestors;
      int  m = ((l1->length < l2->length) ?
        l1->length :
        l2->length );
      { while ((equal((*(l1))[m],(*(l2))[m]) != CTRUE))
        { m= (m-1);
          } 
        } 
      Result = OBJECT(ClaireClass,(*(l1))[m]);
      } 
    return (Result);} 
  } 


// fast inclusion method for lattice_sets (lattice order). The argument is
// either a lattice_set or {}
/* The c++ function for: inherit?(self:class,ens:class) [0] */
ClaireBoolean * inherit_ask_class(ClaireClass *self,ClaireClass *ens)
{ { ClaireBoolean *Result ;
    { list * l = self->ancestors;
      int  n = ens->ancestors->length;
      Result = ((n <= l->length) ? (((*(l))[n] == _oid_(ens)) ? CTRUE: CFALSE): CFALSE);
      } 
    return (Result);} 
  } 


//------------- PROPERTY ---------------------------------------------------
// the two methods to access open(r)
// an abstract property is extensible and can receive new restrictions
/* The c++ function for: abstract(p:property) [SLOT_UPDATE+SAFE_RESULT] */
OID  abstract_property(property *p)
{ { OID Result = 0;
    { int  n = p->open;
      if (n < 2)
       close_exception(((general_error *) (*Core._general_error)(_string_("[127] ~S can no longer become abstract"),
        _oid_(list::alloc(1,_oid_(p))))));
      else (p->open = 3);
        Result = _oid_(p);
      } 
    return (Result);} 
  } 


// a final property is completely defined and cannot receive a new restriction
// v3.2.04: the new value 4 will be used to represent (compiled but open)
/* The c++ function for: final(r:relation) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
void  final_relation(ClaireRelation *r)
{ GC_BIND;
  if (INHERIT(r->isa,Kernel._property))
   { if (r->open <= 2)
     { (r->open = 1);
      { ClaireRelation * g0048; 
        ClaireType * g0049;
        g0048 = r;
        { list * g0050UU;
          { { bag *v_list; OID v_val;
              OID x,CLcount;
              v_list = CLREAD(property,r,restrictions);
               g0050UU = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { x = (*(v_list))[CLcount];
                v_val = (*(OBJECT(restriction,x)->domain))[1];
                
                (*((list *) g0050UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0050UU);} 
          g0049 = Uall_list(g0050UU);
          } 
        (g0048->domain = g0049);} 
      { ClaireRelation * g0051; 
        ClaireType * g0052;
        g0051 = r;
        { list * g0053UU;
          { { bag *v_list; OID v_val;
              OID x,CLcount;
              v_list = CLREAD(property,r,restrictions);
               g0053UU = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { x = (*(v_list))[CLcount];
                v_val = _oid_(OBJECT(restriction,x)->range);
                
                (*((list *) g0053UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0053UU);} 
          g0052 = Uall_list(g0053UU);
          } 
        (g0051->range = g0052);} 
      } 
    } 
  GC_UNBIND;} 


//------------- MODULES   --------------------------------------------------
// the close function gives its right value to the *internal* slot that
// is the order of the module in the system. The name is supposed to be
// read in the system module.
/* The c++ function for: close(self:module) [NEW_ALLOC+SLOT_UPDATE+SAFE_RESULT] */
module * close_module(module *self)
{ GC_BIND;
  if (self != claire.it)
   { if (self->part_of != (NULL))
     { module * sup = self->part_of;
      GC_OBJECT(list,sup->parts)->addFast(_oid_(self));
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,sup->uses);
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if ((contain_ask_list(self->uses,x) != CTRUE) && 
              (INHERIT(OWNER(x),Kernel._module)))
           GC_OBJECT(list,self->uses)->addFast(x);
          GC_UNLOOP;} 
        } 
      } 
    } 
  { module *Result ;
    Result = self;
    GC_UNBIND; return (Result);} 
  } 


// note: dynamic modules are no longer supported
/* The c++ function for: get_symbol(self:string) [0] */
OID  get_symbol_string(char *self)
{ return (get_symbol_module(claire.it,self));} 


// *********************************************************************
// *   Part 3: System Methods                                          *
// *********************************************************************
// all these methods will be open-coded by the compiler
//get_stack(self:integer) : any -> get_stack(self)
//put_stack(self:integer,x:any) : any -> put_stack(self, x)
//push!(self:meta_system,x:any) : void -> push!(self, x)
/* The c++ function for: gensym(self:void) [NEW_ALLOC] */
symbol * gensym_void()
{ return (gensym_string("g"));} 


// world management
/* The c++ function for: store(l:list,n:integer,y:any) [BAG_UPDATE+RETURN_ARG] */
OID  store_list4(list *l,int n,OID y)
{ return (STOREI((*l)[n],y));} 


/* The c++ function for: store(l:array,n:integer,y:any) [RETURN_ARG] */
OID  store_array1(OID *l,int n,OID y)
{ return (store_array(l,n,y,CTRUE));} 


/* The c++ function for: commit(n:integer) [0] */
void  commit_integer(int n)
{ { while ((n < world_number()))
    { world_remove();
      } 
    } 
  } 


/* The c++ function for: backtrack(n:integer) [0] */
void  backtrack_integer(int n)
{ { while ((n < world_number()))
    { world_pop();
      } 
    } 
  } 


// allows to change the storage class
/* The c++ function for: store(l:listargs) [SLOT_UPDATE+RETURN_ARG] */
OID  store_listargs(listargs *l)
{ { OID Result = 0;
    { ITERATE(r);
      Result= _oid_(CFALSE);
      for (START(l); NEXT(r);)
      if (INHERIT(OWNER(r),Kernel._relation))
       (OBJECT(ClaireRelation,r)->store_ask = CTRUE);
      else if (Kernel._string == OWNER(r))
       { OID  v = value_string(string_v(r));
        if (INHERIT(OWNER(v),Core._global_variable))
         (OBJECT(global_variable,v)->store_ask = CTRUE);
        } 
      } 
    return (Result);} 
  } 


// *********************************************************************
// *   Part 4: Methods for Native entities                             *
// *********************************************************************
//------------------- STRING -----------------------------------------------
/* The c++ function for: symbol!(self:string) [0] */
symbol * symbol_I_string2(char *self)
{ return (symbol_I_string(self,claire.it));} 


/* The c++ function for: externC(s:string) [SAFE_RESULT] */
void  externC_string(char *s)
{ close_exception(((general_error *) (*Core._general_error)(_string_("cannot execute C code: ~A"),
    _oid_(list::alloc(1,_string_(s))))));
  } 


/* The c++ function for: externC(s:string,c:class) [SAFE_RESULT] */
OID  externC_string2(char *s,ClaireClass *c)
{ return (_void_(close_exception(((general_error *) (*Core._general_error)(_string_("cannot execute ~A"),
    _oid_(list::alloc(1,_string_(s))))))));} 


/* The c++ function for: externC_string2_type */
ClaireType * externC_string2_type(ClaireType *s,ClaireType *c)
{ return (member_type(c));} 


/* The c++ function for: nth_get(s:string,n:integer,max:integer) [RETURN_ARG] */
ClaireChar * nth_get_string(char *s,int n,int max)
{ { ClaireChar *Result ;
    { ClaireObject *V_CC ;
      if (n <= max)
       V_CC = _char_(s[n - 1]);
      else close_exception(((general_error *) (*Core._general_error)(_string_("Buffer string access"),
          _oid_(Kernel.nil))));
        Result= (ClaireChar *) V_CC;} 
    return (Result);} 
  } 


/* The c++ function for: nth_put(s:string,n:integer,c:char,max:integer) [BAG_UPDATE+RETURN_ARG] */
void  nth_put_string(char *s,int n,ClaireChar *c,int max)
{ if (n <= max)
   (s[n - 1] = (char) c->ascii);
  else close_exception(((general_error *) (*Core._general_error)(_string_("Buffer string access"),
      _oid_(Kernel.nil))));
    } 


//  v3.2.14
//------------------- SYMBOL -----------------------------------------------
/* The c++ function for: make_string(self:symbol) [SLOT_UPDATE] */
char * make_string_symbol(symbol *self)
{ print_in_string_void();
  princ_symbol(self);
  return (end_of_print_void());} 


//princ(self:symbol) : any -> function!(princ_symbol)
/* The c++ function for: self_print(self:symbol) [NEW_ALLOC+SLOT_UPDATE] */
OID  self_print_symbol_Core(symbol *self)
{ princ_symbol(self->module_I->name);
  princ_string("/");
  print_any(_string_(string_I_symbol(self)));
  return (_void_(princ_string("")));} 


//--------------------- INTEGER -----------------------------------------
/* The c++ function for: +(self:integer,x:integer) [0] */
int  _plus_integer(int self,int x)
{ return (_integer_(self+x));} 


/* The c++ function for: +_integer_type */
ClaireType * _plus_integer_type(ClaireType *self,ClaireType *x)
{ return (abstract_type_operation(Core._plus,self,x));} 


/* The c++ function for: -(self:integer,x:integer) [0] */
int  _dash_integer1(int self,int x)
{ return ((self-x));} 


/* The c++ function for: -_integer1_type */
ClaireType * _dash_integer1_type(ClaireType *self,ClaireType *x)
{ return (abstract_type_operation(Kernel._dash,self,x));} 


//float!(self:integer) : float -> function!(to_float)
//less_code(n:integer,i:integer) : boolean -> function!(less_code_integer)
/* The c++ function for: <<(x:integer,y:integer) [0] */
int  _inf_inf_integer(int x,int y)
{ return ((x << y));} 


/* The c++ function for: >>(x:integer,y:integer) [0] */
int  _sup_sup_integer(int x,int y)
{ return ((x >> y));} 


/* The c++ function for: and(x:integer,y:integer) [0] */
int  and_integer(int x,int y)
{ return ((x & y));} 


/* The c++ function for: or(x:integer,y:integer) [0] */
int  or_integer(int x,int y)
{ return ((x | y));} 


// open-coded
/* The c++ function for: <(self:integer,x:integer) [0] */
ClaireBoolean * _inf_integer(int self,int x)
{ { ClaireBoolean *Result ;
    Result = ((self < x) ?
      CTRUE :
      CFALSE );
    return (Result);} 
  } 


/* The c++ function for: <=(self:integer,x:integer) [0] */
ClaireBoolean * _inf_equal_integer(int self,int x)
{ { ClaireBoolean *Result ;
    Result = ((self <= x) ?
      CTRUE :
      CFALSE );
    return (Result);} 
  } 


/* The c++ function for: >(self:integer,x:integer) [0] */
ClaireBoolean * _sup_integer(int self,int x)
{ { ClaireBoolean *Result ;
    Result = ((self > x) ?
      CTRUE :
      CFALSE );
    return (Result);} 
  } 


/* The c++ function for: nth(self:integer,y:integer) [0] */
ClaireBoolean * nth_integer(int self,int y)
{ { ClaireBoolean *Result ;
    Result = ((BCONTAIN(self,y)) ?
      CTRUE :
      CFALSE );
    return (Result);} 
  } 


// used by the logic
/* The c++ function for: factor?(x:integer,y:integer) [0] */
ClaireBoolean * factor_ask_integer(int x,int y)
{ return (equal(mod_integer(x,y),0));} 


/* The c++ function for: divide?(x:integer,y:integer) [0] */
ClaireBoolean * divide_ask_integer(int x,int y)
{ return (equal(mod_integer(y,x),0));} 


/* The c++ function for: Id(x:any) [RETURN_ARG] */
OID  Id_any(OID x)
{ return (x);} 


/* The c++ function for: Id_any_type */
ClaireType * Id_any_type(ClaireType *x)
{ return (x);} 


/* The c++ function for: pair(x:any,y:any) [NEW_ALLOC] */
list * pair_any(OID x,OID y)
{ return (list::alloc(2,x,y));} 


/* The c++ function for: pair_1(x:list) [RETURN_ARG] */
OID  pair_1_list(list *x)
{ return ((*(x))[1]);} 


/* The c++ function for: pair_1_list_type */
ClaireType * pair_1_list_type(ClaireType *x)
{ return (member_type(x));} 


/* The c++ function for: pair_2(x:list) [RETURN_ARG] */
OID  pair_2_list(list *x)
{ return ((*(x))[2]);} 


/* The c++ function for: pair_2_list_type */
ClaireType * pair_2_list_type(ClaireType *x)
{ return (member_type(x));} 


//------------------------ FLOAT ---------------------------------------------
/* The c++ function for: +(g0054:any,g0055:any) [0] */
OID  _plus_float_(OID g0054,OID g0055)
{ return _float_(_plus_float(float_v(g0054),float_v(g0055)));} 


/* The c++ function for: +(self:float,x:float) [0] */
double  _plus_float(double self,double x)
{ { double Result =0.0;
    { double  y = (self+x);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: -(g0056:any,g0057:any) [0] */
OID  _dash_float_(OID g0056,OID g0057)
{ return _float_(_dash_float(float_v(g0056),float_v(g0057)));} 


/* The c++ function for: -(self:float,x:float) [0] */
double  _dash_float(double self,double x)
{ { double Result =0.0;
    { double  y = (self-x);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: *(g0058:any,g0059:any) [0] */
OID  _star_float_(OID g0058,OID g0059)
{ return _float_(_star_float(float_v(g0058),float_v(g0059)));} 


/* The c++ function for: *(self:float,x:float) [0] */
double  _star_float(double self,double x)
{ { double Result =0.0;
    { double  y = (self*x);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: /(g0060:any,g0061:any) [0] */
OID  _7_float_(OID g0060,OID g0061)
{ return _float_(_7_float(float_v(g0060),float_v(g0061)));} 


/* The c++ function for: /(self:float,x:float) [0] */
double  _7_float(double self,double x)
{ { double Result =0.0;
    { double  y = (self/x);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: -(g0062:any) [NEW_ALLOC] */
OID  _dash_float2_(OID g0062)
{ return _float_(_dash_float2(float_v(g0062)));} 


/* The c++ function for: -(self:float) [NEW_ALLOC] */
double  _dash_float2(double self)
{ return (((-1.0)*self));} 


/* The c++ function for: sqrt(g0063:any) [0] */
OID  sqrt_float_(OID g0063)
{ return _float_(sqrt_float(float_v(g0063)));} 


/* The c++ function for: sqrt(self:float) [0] */
double  sqrt_float(double self)
{ { double Result =0.0;
    { double  y = sqrt(self);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: ^(g0064:any,g0065:any) [NEW_ALLOC] */
OID  _exp_float_(OID g0064,OID g0065)
{ return _float_(_exp_float(float_v(g0064),float_v(g0065)));} 


/* The c++ function for: ^(self:float,x:float) [NEW_ALLOC] */
double  _exp_float(double self,double x)
{ { double Result =0.0;
    { double  y = 0.0;
      y = pow(self,x);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: log(g0066:any) [NEW_ALLOC] */
OID  log_float_(OID g0066)
{ return _float_(log_float(float_v(g0066)));} 


/* The c++ function for: log(self:float) [NEW_ALLOC] */
double  log_float(double self)
{ { double Result =0.0;
    { double  y = 0.0;
      y = log(self);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: mClaire/atan(g0067:any) [NEW_ALLOC] */
OID  atan_float_(OID g0067)
{ return _float_(atan_float(float_v(g0067)));} 


/* The c++ function for: mClaire/atan(self:float) [NEW_ALLOC] */
double  atan_float(double self)
{ { double Result =0.0;
    { double  y = 0.0;
      y = atan(self);
      Result = y;
      } 
    return (Result);} 
  } 


/* The c++ function for: string!(g0068:any) [SLOT_UPDATE] */
char * string_I_float_(OID g0068)
{ return string_I_float(float_v(g0068));} 


/* The c++ function for: string!(self:float) [SLOT_UPDATE] */
char * string_I_float(double self)
{ print_in_string_void();
  princ_float(self);
  return (end_of_print_void());} 


//--------- BAG --------------------------------------------------------
/* The c++ function for: length(self:bag) [0] */
int  length_bag(bag *self)
{ return (self->length);} 


/* The c++ function for: nth(self:bag,x:integer) [0] */
OID  nth_bag(bag *self,int x)
{ { OID Result = 0;
    if ((x > 0) && 
        (x <= self->length))
     Result = (*(self))[x];
    else close_exception(((general_error *) (*Core._general_error)(_string_("[41] nth[~S] out of scope for ~S"),
        _oid_(list::alloc(2,x,_oid_(self))))));
      return (Result);} 
  } 


/* The c++ function for: nth_bag_type */
ClaireType * nth_bag_type(ClaireType *self,ClaireType *x)
{ return (member_type(self));} 


/* The c++ function for: nth_get(self:bag,x:integer) [RETURN_ARG] */
OID  nth_get_bag(bag *self,int x)
{ return ((*(self))[x]);} 


/* The c++ function for: min(f:method,self:bag) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
OID  min_method(method *f,bag *self)
{ { OID Result = 0;
    if (self->length != 0)
     { OID  x = (*(self))[1];
      { ITERATE(y);
        for (START(self); NEXT(y);)
        if (boolean_I_any(funcall_method2(f,y,x)) == CTRUE)
         x= y;
        } 
      Result = x;
      } 
    else close_exception(((general_error *) (*Core._general_error)(_string_("[183] min of empty set is undefined"),
        _oid_(Kernel.nil))));
      return (Result);} 
  } 


/* The c++ function for: min_method_type */
ClaireType * min_method_type(ClaireType *f,ClaireType *self)
{ return (member_type(self));} 


/* The c++ function for: max(f:method,self:bag) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
OID  max_method(method *f,bag *self)
{ { OID Result = 0;
    if (self->length != 0)
     { OID  x = (*(self))[1];
      { ITERATE(y);
        for (START(self); NEXT(y);)
        if (boolean_I_any(funcall_method2(f,y,x)) != CTRUE)
         x= y;
        } 
      Result = x;
      } 
    else close_exception(((general_error *) (*Core._general_error)(_string_("[183] max of empty set is undefined"),
        _oid_(Kernel.nil))));
      return (Result);} 
  } 


/* The c++ function for: max_method_type */
ClaireType * max_method_type(ClaireType *f,ClaireType *self)
{ return (member_type(self));} 


// there seems to be a difficulty with providing this method with the proper type ..
/* The c++ function for: /+(x:bag,y:bag) [NEW_ALLOC] */
list * _7_plus_bag(bag *x,bag *y)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list *Result ;
    { list * l;
      { { ClaireObject *V_CC ;
          if (INHERIT(x->isa,Kernel._list))
           V_CC = copy_bag(x);
          else if (Kernel._set == x->isa)
           V_CC = list_I_set(((set *) x));
          else if (INHERIT(x->isa,Kernel._tuple))
           V_CC = list_I_tuple(((tuple *) x));
          else V_CC = CFALSE;
            l= (list *) V_CC;} 
        GC_OBJECT(list,l);} 
      if (INHERIT(y->isa,Kernel._list))
       l= GC_OBJECT(list,append_list(l,((list *) y)));
      else { OID gc_local;
          ITERATE(z);
          for (START(y); NEXT(z);)
          { GC_LOOP;
            GC__ANY(l = l->addFast(z), 1);
            GC_UNLOOP;} 
          } 
        Result = l;
      } 
    GC_UNBIND; return (Result);} 
  } 


//--------- LIST --------------------------------------------------------
// last element of a list
/* The c++ function for: last(self:list) [RETURN_ARG] */
OID  last_list(list *self)
{ { OID Result = 0;
    if (self->length > 0)
     Result = (*(self))[self->length];
    else close_exception(((general_error *) (*Core._general_error)(_string_("[41] car(nil) is undefined"),
        _oid_(Kernel.nil))));
      return (Result);} 
  } 


/* The c++ function for: last_list_type */
ClaireType * last_list_type(ClaireType *self)
{ return (member_type(self));} 


// remove the last element
/* The c++ function for: rmlast(self:list) [RETURN_ARG] */
list * rmlast_list(list *self)
{ delete_at_list(self,self->length);
  return (self);} 


/* The c++ function for: nth=(self:list,x:integer,y:any) [BAG_UPDATE+RETURN_ARG] */
OID  nth_set_list(list *self,int x,OID y)
{ { OID Result = 0;
    if ((x < 0) || 
        (x > self->length))
     close_exception(((general_error *) (*Core._general_error)(_string_("[41] nth[~S] out of scope for ~S"),
      _oid_(list::alloc(2,x,_oid_(self))))));
    else if (belong_to(y,_oid_(of_bag(self))) == CTRUE)
     Result = ((*(self))[x]=y);
    else close_exception(((system_error *) (*Kernel._system_error)(17,
        y,
        _oid_(self))));
      return (Result);} 
  } 


// v3.2.00
// the old LISP method
/* The c++ function for: car(self:list) [RETURN_ARG] */
OID  car_list(list *self)
{ { OID Result = 0;
    if (self->length > 0)
     Result = (*(self))[1];
    else close_exception(((general_error *) (*Core._general_error)(_string_("[41] car(nil) is undefined"),
        _oid_(Kernel.nil))));
      return (Result);} 
  } 


// hashtable basics
/* The c++ function for: hashlist(n:integer) [NEW_ALLOC] */
list * hashlist_integer(int n)
{ GC_BIND;
  { list *Result ;
    { list * l = GC_OBJECT(list,make_list_integer(n,CNULL));
      int  u = (((*(l))[0])-3);
      { int  i = (n+1);
        int  g0069 = u;
        { OID gc_local;
          while ((i <= g0069))
          { // HOHO, GC_LOOP not needed !
            l->addFast(CNULL);
            ++i;
            } 
          } 
        } 
      Result = l;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: hashsize(l:list) [0] */
int  hashsize_list(list *l)
{ { int Result = 0;
    { int  x = 0;
      { ITERATE(y);
        for (START(l); NEXT(y);)
        if (y != CNULL)
         ++x;
        } 
      Result = x;
      } 
    return (Result);} 
  } 


// this method sorts a list according to an order
/* The c++ function for: sort(f:method,self:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
list * sort_method(method *f,list *self)
{ quicksort_list(self,f,1,self->length);
  return (self);} 


// v3.0.38: upgrade the quicksort algorithm with a better pivot selection cf.bag.cpp
/* The c++ function for: quicksort(self:list,f:method,n:integer,m:integer) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
void  quicksort_list(list *self,method *f,int n,int m)
{ if (m > n)
   { OID  x = (*(self))[n];
    if (m == (n+1))
     { if (boolean_I_any(funcall_method2(f,(*(self))[m],x)) == CTRUE)
       { ((*(self))[n]=(*(self))[m]);
        ((*(self))[m]=x);
        } 
      } 
    else { int  p = _sup_sup_integer((m+n),1);
        int  q = n;
        x= (*(self))[p];
        if (p != n)
         ((*(self))[p]=(*(self))[n]);
        { int  p = (n+1);
          int  g0070 = m;
          { OID gc_local;
            while ((p <= g0070))
            { // HOHO, GC_LOOP not needed !
              if (boolean_I_any(funcall_method2(f,(*(self))[p],x)) == CTRUE)
               { ((*(self))[n]=(*(self))[p]);
                ++n;
                if (p > n)
                 ((*(self))[p]=(*(self))[n]);
                } 
              ++p;
              } 
            } 
          } 
        ((*(self))[n]=x);
        quicksort_list(self,f,q,(n-1));
        quicksort_list(self,f,(n+1),m);
        } 
      } 
  } 


// destructive method that build the powerset
/* The c++ function for: build_powerset(self:list) [NEW_ALLOC+BAG_UPDATE] */
set * build_powerset_list(list *self)
{ if (self->length != 0) 
  { { set *Result ;
      { OID  x = (*(self))[1];
        set * l1 = GC_OBJECT(set,build_powerset_list(skip_list(self,1)));
        set * l2 = l1;
        { OID gc_local;
          ITERATE(y);
          for (START(l1); NEXT(y);)
          { GC_LOOP;
            GC__ANY(l2 = l2->addFast(GC_OID(_oid_(append_set(set::alloc(1,x),OBJECT(set,y))))), 4);
            GC_UNLOOP;} 
          } 
        Result = l2;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { set *Result ;
      Result = set::alloc(Kernel.emptySet,1,_oid_(Kernel.emptySet));
      GC_UNBIND; return (Result);} 
    } 
  } 


// new and useful (v3.1.06)
/* The c++ function for: make_copy_list(n:integer,d:any) [NEW_ALLOC+BAG_UPDATE] */
list * make_copy_list_integer(int n,OID d)
{ GC_BIND;
  { list *Result ;
    { list * l = GC_OBJECT(list,make_list_integer(n,d));
      if (INHERIT(OWNER(d),Kernel._bag))
       { int  i = 1;
        int  g0071 = n;
        { OID gc_local;
          while ((i <= g0071))
          { // HOHO, GC_LOOP not needed !
            ((*(l))[i]=_oid_(copy_bag(OBJECT(bag,d))));
            ++i;
            } 
          } 
        } 
      Result = l;
      } 
    GC_UNBIND; return (Result);} 
  } 


//----------------------  SET  ---------------------------------------------
/* The c++ function for: difference(self:set,x:set) [NEW_ALLOC] */
set * difference_set(set *self,set *x)
{ GC_BIND;
  { set *Result ;
    { bag * y_in = self;
      set * y_out = ((set *) empty_bag(y_in));
      { ITERATE(y);
        for (START(y_in); NEXT(y);)
        if (contain_ask_set(x,y) != CTRUE)
         y_out->addFast(y);
        } 
      Result = GC_OBJECT(set,y_out);
      } 
    GC_UNBIND; return (Result);} 
  } 


//--------- ARRAY --------------------------------------------------------
/* The c++ function for: nth=(self:array,x:integer,y:any) [RETURN_ARG] */
void  nth_equal_array(OID *self,int x,OID y)
{ if (belong_to(y,_oid_(of_array(self))) != CTRUE)
   close_exception(((general_error *) (*Core._general_error)(_string_("type mismatch for array update ~S, ~S"),
    _oid_(list::alloc(2,y,_array_(self))))));
  else if ((x > 0) && 
      (x <= self[0]))
   nth_put_array(self,x,y);
  else close_exception(((general_error *) (*Core._general_error)(_string_("nth[~S] out of scope for ~S"),
      _oid_(list::alloc(2,x,_array_(self))))));
    } 


/* The c++ function for: self_print(self:array) [NEW_ALLOC] */
OID  self_print_array_Core(OID *self)
{ princ_string("array<");
  print_any(_oid_(of_array(self)));
  princ_string(">[");
  princ_integer(self[0]);
  return (_void_(princ_string("]")));} 


//---------------------- CHAR --------------------------------------------
/* The c++ function for: self_print(self:char) [0] */
OID  self_print_char_Core(ClaireChar *self)
{ princ_string("'");
  princ_char(self);
  return (_void_(princ_string("'")));} 


/* The c++ function for: <=(c1:char,c2:char) [0] */
ClaireBoolean * _inf_equal_char(ClaireChar *c1,ClaireChar *c2)
{ return (_inf_equal_integer(c1->ascii,c2->ascii));} 


// three methods that are useful for debugging !