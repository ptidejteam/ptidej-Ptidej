/***** CLAIRE Compilation of file d:\claire\v3.2\src\meta\define.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:02:52 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>

//+-------------------------------------------------------------+
//| CLAIRE                                                      |
//| define.cl                                                   |
//| Copyright (C) 1994 - 2000 Yves Caseau. All Rights Reserved  |
//| cf. copyright info in file object.cl: about()               |
//+-------------------------------------------------------------+
// ---------------------------------------------------------------------
// Contents:
//   Part 2: the instantiation macros
//   Part 3: the useful stuff
//   Part 4: the other macros
// ---------------------------------------------------------------------
// *********************************************************************
// *     Part 1: Definition                                            *
// *********************************************************************
// this is the basic class instantiation
//
/* The c++ function for: self_print(self:Definition) [NEW_ALLOC+SLOT_UPDATE] */
OID  self_print_Definition_Language(Definition *self)
{ GC_BIND;
  print_any(_oid_(self->arg));
  princ_string("(");
  printbox_bag2(GC_OBJECT(list,self->args));
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


// ------------- named object definition ------------------------------
//
/* The c++ function for: self_print(self:Defobj) [NEW_ALLOC+SLOT_UPDATE] */
void  self_print_Defobj_Language(Defobj *self)
{ if (self->arg == Core._global_variable) 
  { { OID  r = _oid_(Kernel._any);
      OID  v = CNULL;
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,self->args);
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if ((*(OBJECT(Call,x)->args))[1] == _oid_(Kernel.value))
           v= (*(OBJECT(Call,x)->args))[2];
          else if ((*(OBJECT(Call,x)->args))[1] == _oid_(Kernel.range))
           r= (*(OBJECT(Call,x)->args))[2];
          GC_UNLOOP;} 
        } 
      if (boolean_I_any(r) == CTRUE)
       { princ_symbol(self->ident);
        princ_string(":");
        print_any(r);
        princ_string(" := ");
        printexp_any(v,CFALSE);
        princ_string("");
        } 
      else { princ_symbol(self->ident);
          princ_string(" :: ");
          printexp_any(v,CFALSE);
          princ_string("");
          } 
        } 
     } 
  else{ GC_BIND;
    princ_symbol(self->ident);
    princ_string(" :: ");
    print_any(_oid_(self->arg));
    princ_string("(");
    printbox_bag2(GC_OBJECT(list,self->args));
    princ_string(")");
    GC_UNBIND;} 
  } 


// ------------- class definition ------------------------------------
//
/* The c++ function for: self_print(self:Defclass) [NEW_ALLOC+SLOT_UPDATE] */
void  self_print_Defclass_Language(Defclass *self)
{ GC_BIND;
  princ_symbol(self->ident);
  if (self->params->length != 0)
   { princ_string("[");
    princ_bag(GC_OBJECT(list,self->params));
    princ_string("]");
    } 
  princ_string(" <: ");
  print_any(_oid_(self->arg));
  princ_string("(");
  { int  _Zl = Core.pretty->index;
    list * l = GC_OBJECT(list,self->args);
    int  n = l->length;
    int  i = 1;
    int  g0031 = n;
    { OID gc_local;
      while ((i <= g0031))
      { // HOHO, GC_LOOP not needed !
        if (i == 1)
         set_level_void();
        else lbreak_void();
          if (INHERIT(OWNER((*(l))[i]),Language._Vardef))
         (*Language.ppvariable)((*(l))[i]);
        else { (*Language.ppvariable)((*(OBJECT(bag,(*Core.args)((*(l))[i]))))[1]);
            princ_string(" = ");
            print_any((*(OBJECT(bag,(*Core.args)((*(l))[i]))))[2]);
            princ_string("");
            } 
          if (i < n)
         princ_string(",");
        ++i;
        } 
      } 
    } 
  princ_string(")");
  GC_UNBIND;} 


// -------------- method definition ----------------------------------
//
/* The c++ function for: self_print(self:Defmethod) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  self_print_Defmethod_Language(Defmethod *self)
{ GC_BIND;
  print_any(_oid_(self->arg->selector));
  princ_string("(");
  ppvariable_list(GC_OBJECT(list,self->arg->args));
  princ_string(") : ");
  printexp_any(GC_OID(self->set_arg),CFALSE);
  lbreak_void();
  (Core.pretty->index = (Core.pretty->index+4));
  princ_string(" ");
  princ_string(((boolean_I_any(self->inline_ask) == CTRUE) ?
    "=>" :
    "->" ));
  princ_string(" ");
  printexp_any(GC_OID(self->body),CFALSE);
  princ_string(" ");
  (Core.pretty->index = (Core.pretty->index-4));
  GC_UNBIND;} 


// -------------- array definition -----------------------------------
/* The c++ function for: self_print(self:Defarray) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  self_print_Defarray_Language(Defarray *self)
{ GC_BIND;
  print_any((*(self->arg->args))[1]);
  princ_string("[");
  ppvariable_list(GC_OBJECT(list,cdr_list(GC_OBJECT(list,self->arg->args))));
  princ_string("] : ");
  print_any(GC_OID(self->set_arg));
  lbreak_void();
  (Core.pretty->index = (Core.pretty->index+4));
  princ_string(" := ");
  printexp_any(GC_OID(self->body),CFALSE);
  princ_string(" ");
  (Core.pretty->index = (Core.pretty->index-4));
  GC_UNBIND;} 


// -------------- rule definition ------------------------------------
/* The c++ function for: self_print(self:Defrule) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  self_print_Defrule_Language(Defrule *self)
{ GC_BIND;
  princ_symbol(self->ident);
  princ_string("(");
  ppvariable_list(GC_OBJECT(list,self->args));
  princ_string(") :: rule(");
  lbreak_integer(4);
  princ_string(" ");
  print_any(GC_OID(self->arg));
  princ_string(" ");
  lbreak_integer(4);
  princ_string("=> ");
  print_any(GC_OID(self->body));
  princ_string(")");
  (Core.pretty->index = (Core.pretty->index-4));
  GC_UNBIND;} 


/* The c++ function for: self_print(self:Defvar) [NEW_ALLOC+SLOT_UPDATE] */
void  self_print_Defvar_Language(Defvar *self)
{ GC_BIND;
  ppvariable_Variable(GC_OBJECT(Variable,self->ident));
  princ_string(" := ");
  printexp_any(GC_OID(self->arg),CFALSE);
  princ_string("");
  GC_UNBIND;} 


// *********************************************************************
// *     Part 2: the general instantiation macro                       *
// *********************************************************************
// creation of a new object
//
/* The c++ function for: self_eval(self:Definition) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
OID  self_eval_Definition(Definition *self)
{ GC_BIND;
  { OID Result = 0;
    { ClaireClass * _Zc = self->arg;
      ClaireObject * _Zo;
      { { if (_Zc->open <= 0)
           close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
            _oid_(list::alloc(1,_oid_(_Zc))))));
          _Zo = new_object_class(_Zc);
          } 
        GC_OBJECT(ClaireObject,_Zo);} 
      if (_Zc->open != ClEnv->ephemeral)
       (_Zc->instances = _Zc->instances->addFast(_oid_(_Zo)));
      Result = complete_object(_Zo,GC_OBJECT(list,self->args));
      } 
    GC_UNBIND; return (Result);} 
  } 


// the instantiation body is a sequence of words from which the initialization
// of the object must be built.
//
/* The c++ function for: complete(self:object,%l:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
OID  complete_object(ClaireObject *self,list *_Zl)
{ GC_RESERVE(6);  // v3.0.55 optim !
  { OID gc_local;
    ITERATE(x);
    for (START(_Zl); NEXT(x);)
    { GC_LOOP;
      { property * p = make_a_property_any((*(OBJECT(Call,x)->args))[1]);
        OID  y = GC_OID(OPT_EVAL((*(OBJECT(Call,x)->args))[2]));
        ClaireObject * s = GC_OBJECT(ClaireObject,_at_property1(p,self->isa));
        if (Kernel._slot == s->isa)
         { if (belong_to(y,_oid_(CLREAD(restriction,s,range))) != CTRUE)
           range_is_wrong_slot(((slot *) s),y);
          else update_property(p,
              self,
              CLREAD(slot,s,index),
              CLREAD(slot,s,srange),
              y);
            } 
        else close_exception(((general_error *) (*Core._general_error)(_string_("[106] the object ~S does not understand ~S"),
            _oid_(list::alloc(2,_oid_(self),_oid_(p))))));
          } 
      GC_UNLOOP;} 
    } 
  { OID Result = 0;
    Result = _oid_(complete_I_object(self));
    GC_UNBIND; return (Result);} 
  } 


// creation of a new named object
//
/* The c++ function for: self_eval(self:Defobj) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
OID  self_eval_Defobj(Defobj *self)
{ GC_BIND;
  { OID Result = 0;
    { ClaireClass * _Zc = self->arg;
      if (_Zc->open <= 0)
       close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
        _oid_(list::alloc(1,_oid_(_Zc))))));
      if (INHERIT(_Zc,Kernel._thing))
       { thing * _Zo = new_thing_class(_Zc,self->ident);
        Result = complete_object(_Zo,GC_OBJECT(list,self->args));
        } 
      else { ClaireObject * ob = GC_OBJECT(ClaireObject,new_object_class(_Zc));
          if (_Zc->open != ClEnv->ephemeral)
           _Zc->instances->addFast(_oid_(ob));
          Result = put_symbol(self->ident,complete_object(ob,GC_OBJECT(list,self->args)));
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// creation of a new named object
/* The c++ function for: self_eval(self:Defclass) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
OID  self_eval_Defclass(Defclass *self)
{ GC_RESERVE(8);  // v3.0.55 optim !
  { OID Result = 0;
    if ((INHERIT(owner_any(get_symbol(self->ident)),Kernel._class)) && 
        ((OBJECT(ClaireClass,get_symbol(self->ident))->open != -2) || 
            (self->arg != OBJECT(ClaireClass,get_symbol(self->ident))->superclass)))
     close_exception(((general_error *) (*Core._general_error)(_string_("[107] class re-definition is not valid: ~S"),
      _oid_(list::alloc(1,_oid_(self))))));
    else if ((self->arg->open == 1) || 
        (self->arg->open == -1))
     close_exception(((general_error *) (*Core._general_error)(_string_("[109] the parent class ~S of ~S is closed"),
      _oid_(list::alloc(2,_oid_(self->arg),_oid_(self))))));
    else { ClaireClass * _Zo = class_I_symbol(self->ident,self->arg);
        { OID gc_local;
          ITERATE(x);
          bag *x_support;
          x_support = GC_OBJECT(list,self->args);
          for (START(x_support); NEXT(x);)
          { GC_LOOP;
            { OID  v = CNULL;
              if (INHERIT(OWNER(x),Language._Call))
               { GC__OID(v = OPT_EVAL((*(OBJECT(Call,x)->args))[2]), 4);
                x= (*(OBJECT(Call,x)->args))[1];
                } 
              { ClaireType * rt = GC_OBJECT(ClaireType,extract_type_any(GC_OID((*Kernel.range)(x))));
                property * p = make_a_property_any(_oid_(OBJECT(Variable,x)->pname));
                slot * ps = OBJECT(slot,last_list(_Zo->slots));
                int  ix = ps->index;
                if ((v != CNULL) && 
                    (belong_to(v,_oid_(rt)) != CTRUE))
                 close_exception(((general_error *) (*Core._general_error)(_string_("[108] default(~S) = ~S does not belong to ~S"),
                  _oid_(list::alloc(3,x,
                    v,
                    _oid_(rt))))));
                { ClaireBoolean * g0032I;
                  { ClaireBoolean *v_and;
                    { v_and = ((p->open <= 0) ? CTRUE : CFALSE);
                      if (v_and == CFALSE) g0032I =CFALSE; 
                      else { { OID  g0033UU;
                          { OID gc_local;
                            ITERATE(sx);
                            g0033UU= _oid_(CFALSE);
                            for (START(self->arg->slots); NEXT(sx);)
                            { GC_LOOP;
                              if (OBJECT(restriction,sx)->selector == p)
                               { g0033UU = Kernel.ctrue;
                                break;} 
                              GC_UNLOOP;} 
                            } 
                          v_and = boolean_I_any(g0033UU);
                          } 
                        if (v_and == CFALSE) g0032I =CFALSE; 
                        else g0032I = CTRUE;} 
                      } 
                    } 
                  
                  if (g0032I == CTRUE) close_exception(((general_error *) (*Core._general_error)(_string_("[181] cannot overide a slot for a closed property ~S"),
                      _oid_(list::alloc(1,_oid_(p))))));
                    } 
                if (ps->range == Kernel._float)
                 ++ix;
                add_slot_class(_Zo,
                  p,
                  rt,
                  v,
                  (ix+1));
                } 
              } 
            GC_UNLOOP;} 
          } 
        close_class(_Zo);
        if (self->forward_ask == CTRUE)
         (_Zo->open = -2);
        else if (_Zo->open == -2)
         (_Zo->open = 2);
        if (_inf_equal_type(_Zo,Kernel._import) == CTRUE)
         (_Zo->open = -1);
        (_Zo->params = self->params);
        { OID gc_local;
          ITERATE(p);
          bag *p_support;
          p_support = GC_OBJECT(list,self->params);
          for (START(p_support); NEXT(p);)
          { GC_LOOP;
            (OBJECT(property,p)->open = 0);
            GC_UNLOOP;} 
          } 
        attach_comment_any(_oid_(_Zo));
        Result = _oid_(_Zo);
        } 
      GC_UNBIND; return (Result);} 
  } 


// method definition
// v0.01
/* The c++ function for: self_eval(self:Defmethod) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+SAFE_RESULT] */
OID  self_eval_Defmethod(Defmethod *self)
{ GC_BIND;
  if (!INHERIT(self->arg->isa,Language._Call))
   close_exception(((general_error *) (*Core._general_error)(_string_("[110] wrong signature definition ~S"),
    _oid_(list::alloc(1,GC_OID(_oid_(self->arg)))))));
  { OID Result = 0;
    { property * p = make_a_property_any(_oid_(self->arg->selector));
      list * l = GC_OBJECT(list,self->arg->args);
      list * lv;
      if ((l->length == 1) && 
          ((*(l))[1] == _oid_(ClEnv)))
       { OID v_bag;
        GC_ANY(lv= list::empty(Kernel.emptySet));
        { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
            (_CL_obj->pname = symbol_I_string2("XfakeParameter"));
            (_CL_obj->range = Kernel._void);
            add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
            v_bag = _oid_(_CL_obj);
            } 
          GC_OID(v_bag);} 
        ((list *) lv)->addFast(v_bag);} 
      else lv = l;
        list * lp = GC_OBJECT(list,extract_signature_list(lv));
      list * lrange = GC_OBJECT(list,extract_range_any(GC_OID(self->set_arg),lv,GC_OBJECT(list,OBJECT(list,Language.LDEF->value))));
      list * lbody = GC_OBJECT(list,extract_status_any(GC_OID(self->body)));
      method * m = add_method_property(p,
        lp,
        OBJECT(ClaireType,(*(lrange))[1]),
        (*(lbody))[1],
        (*(lbody))[2]);
      if ((p->open > 0) && 
          ((p->open <= 1) && 
            (p->dispatcher == 0)))
       { OID  rtest;
        { { OID  r_some = CNULL;
            { OID gc_local;
              ITERATE(r);
              for (START(p->restrictions); NEXT(r);)
              { GC_LOOP;
                if (r != _oid_(m))
                 { if (length_bag(_exp_list(OBJECT(restriction,r)->domain,m->domain)) != 0)
                   { r_some= r;
                    break;} 
                  } 
                GC_UNLOOP;} 
              } 
            rtest = r_some;
            } 
          GC_OID(rtest);} 
        if (rtest != CNULL)
         { restriction * r = OBJECT(restriction,rtest);
          tformat_string("--- WARNING ! [186] conflict between ~S and ~S is dangerous since ~S is closed\n",1,list::alloc(3,_oid_(m),
            _oid_(r),
            _oid_(p)));
          } 
        else ;} 
      (Language.LDEF->value= _oid_(list::empty(Kernel._any)));
      if ((*(lbody))[3] != _oid_(Kernel.body))
       (m->formula = lambda_I_list(lv,(*(lbody))[3]));
      if (lrange->length > 1)
       (m->typing = (*(lrange))[2]);
      update_property(Kernel.inline_ask,
        m,
        13,
        Kernel._object,
        GC_OID(self->inline_ask));
      attach_comment_any(_oid_(m));
      if ((p == Kernel.close) && 
          (_inf_equal_type(m->range,domain_I_restriction(m)) != CTRUE))
       close_exception(((general_error *) (*Core._general_error)(_string_("[184] the close method ~S has a wrong range"),
        _oid_(list::alloc(1,_oid_(m))))));
      Result = _oid_(m);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v3.2.24
// attach a cute comment if needed ... to a defclass or a defmethod
/* The c++ function for: attach_comment(x:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
void  attach_comment_any(OID x)
{ if (((OBJECT(ClaireBoolean,Language.NeedComment->value)) == CTRUE) && 
      (Language.LastComment->value != CNULL)) 
  { write_property(Kernel.comment,OBJECT(ClaireObject,x),GC_OID(Language.LastComment->value));
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// returns the list of types AND modifies LDEF
/* The c++ function for: iClaire/extract_signature(l:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
list * extract_signature_list(list *l)
{ GC_BIND;
  (Language.LDEF->value= _oid_(list::empty(Kernel._any)));
  { list *Result ;
    { int  n = 0;
      { bag *v_list; OID v_val;
        OID v,CLcount;
        v_list = l;
         Result = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { v = (*(v_list))[CLcount];
          if (!INHERIT(OBJECT(ClaireObject,v)->isa,Language._Variable))
           close_exception(((general_error *) (*Core._general_error)(_string_("[111] wrong typed argument ~S"),
            _oid_(list::alloc(1,v)))));
          else { OID  p = GC_OID(extract_pattern_any(GC_OID(_oid_(OBJECT(Variable,v)->range)),list::alloc(1,n)));
              ++n;
              if (p == CNULL)
               close_exception(((general_error *) (*Core._general_error)(_string_("[111] wrong typed argument ~S (~S)"),
                _oid_(list::alloc(2,v,GC_OID(_oid_(OBJECT(Variable,v)->range)))))));
              (OBJECT(Variable,v)->range = type_I_any(p));
              v_val = p;
              } 
            
          (*((list *) Result))[CLcount] = v_val;} 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// takes an <exp> that must belong to <type> and returns the CLAIRE type
// if LDEF is non-empty, it is used as a list of type variable and patterns
// may be returned. In addition, if the path list is non empty, new type
// variables may be defined. a syntax error will produce the unknown value
//
/* The c++ function for: iClaire/extract_pattern(x:any,path:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
OID  extract_pattern_any(OID x,list *path)
{ GC_BIND;
  { OID Result = 0;
    if (INHERIT(OWNER(x),Kernel._class))
     Result = x;
    else if (Kernel._set == OWNER(x))
     { OID  z;
      { if (OBJECT(bag,x)->length == 1)
         z = extract_pattern_any((*(OBJECT(bag,x)))[1],Kernel.nil);
        else z = Kernel.cfalse;
          GC_OID(z);} 
      if (INHERIT(OWNER(z),Core._Reference))
       { Reference * w = GC_OBJECT(Reference,((Reference *) copy_object(OBJECT(ClaireObject,z))));
        (w->arg = CTRUE);
        Result = _oid_(w);
        } 
      else Result = x;
        } 
    else if (INHERIT(OWNER(x),Language._Tuple))
     { list * ltp;
      { { bag *v_list; OID v_val;
          OID z,CLcount;
          v_list = GC_OBJECT(list,OBJECT(Construct,x)->args);
           ltp = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { z = (*(v_list))[CLcount];
            v_val = extract_pattern_any(z,path);
            
            (*((list *) ltp))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,ltp);} 
      { ClaireBoolean * g0034I;
        { OID  g0035UU;
          { OID gc_local;
            ITERATE(y);
            g0035UU= _oid_(CFALSE);
            for (START(ltp); NEXT(y);)
            { GC_LOOP;
              if (y == CNULL)
               { g0035UU = Kernel.ctrue;
                break;} 
              GC_UNLOOP;} 
            } 
          g0034I = boolean_I_any(g0035UU);
          } 
        
        if (g0034I == CTRUE) Result = CNULL;
          else Result = _oid_(tuple_I_list(ltp));
        } 
      } 
    else if (INHERIT(OWNER(x),Core._global_variable))
     Result = extract_pattern_any(GC_OID(OBJECT(global_variable,x)->value),path);
    else if (INHERIT(OWNER(x),Language._Call))
     { property * p = OBJECT(Call,x)->selector;
      if (p == Core.U)
       { OID  x1 = GC_OID(extract_pattern_any((*(OBJECT(Call,x)->args))[1],Kernel.nil));
        OID  x2 = GC_OID(extract_pattern_any((*(OBJECT(Call,x)->args))[2],Kernel.nil));
        if ((x1 == CNULL) || 
            (x2 == CNULL))
         Result = CNULL;
        else Result = _oid_(U_type(OBJECT(ClaireType,x1),OBJECT(ClaireType,x2)));
          } 
      else if (p == Kernel._exp)
       Result = (*Kernel._exp)(GC_OID(extract_pattern_any((*(OBJECT(Call,x)->args))[1],Kernel.nil)),
        GC_OID(extract_pattern_any((*(OBJECT(Call,x)->args))[2],Kernel.nil)));
      else if (p == Kernel._dot_dot)
       { OID  v1 = GC_OID(extract_item_any((*(OBJECT(Call,x)->args))[1],Core.nil->value));
        OID  v2 = GC_OID(extract_item_any((*(OBJECT(Call,x)->args))[2],Core.nil->value));
        if ((INHERIT(OWNER(v1),Kernel._integer)) && 
            (INHERIT(OWNER(v2),Kernel._integer)))
         Result = _oid_(_dot_dot_integer(v1,v2));
        else Result = CNULL;
          } 
      else if (p == Kernel.nth)
       Result = extract_pattern_nth_list(GC_OBJECT(list,OBJECT(Call,x)->args),path);
      else if (p == Kernel._star)
       { OID  z = GC_OID(extract_pattern_any((*(OBJECT(Call,x)->args))[1],path));
        if (z != CNULL)
         Result = _oid_(U_type(OBJECT(ClaireType,z),set::alloc(Kernel.emptySet,1,CNULL)));
        else Result = CNULL;
          } 
      else Result = CNULL;
        } 
    else if (INHERIT(OWNER(x),Kernel._type))
     Result = x;
    else if (INHERIT(OWNER(x),Kernel._unbound_symbol))
     { symbol * s = extract_symbol_any(x);
      OID  v;
      { { OID  z_some = CNULL;
          { OID gc_local;
            ITERATE(z);
            bag *z_support;
            z_support = GC_OBJECT(bag,enumerate_any(GC_OID(Language.LDEF->value)));
            for (START(z_support); NEXT(z);)
            { GC_LOOP;
              if (OBJECT(Variable,z)->pname == s)
               { z_some= z;
                break;} 
              GC_UNLOOP;} 
            } 
          v = z_some;
          } 
        GC_OID(v);} 
      if (v != CNULL)
       Result = (*Kernel.range)(v);
      else if ((INHERIT(path->isa,Kernel._list)) && (path->length > 1))
       { Reference * y;
        { { Reference * _CL_obj = ((Reference *) GC_OBJECT(Reference,new_object_class(Core._Reference)));
            (_CL_obj->index = (*(path))[1]);
            (_CL_obj->args = cdr_list(path));
            add_I_property(Kernel.instances,Core._Reference,11,_oid_(_CL_obj));
            y = _CL_obj;
            } 
          GC_OBJECT(Reference,y);} 
        Variable * v;
        { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
            (_CL_obj->pname = s);
            (_CL_obj->range = y);
            add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
            v = _CL_obj;
            } 
          GC_OBJECT(Variable,v);} 
        (Language.LDEF->value= (*Kernel.add)(GC_OID(Language.LDEF->value),
          _oid_(v)));
        Result = _oid_(Kernel._void);
        } 
      else Result = CNULL;
        } 
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


// takes an <exp> that must belong to <type> and returns the CLAIRE type
//
/* The c++ function for: iClaire/extract_type(x:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
ClaireType * extract_type_any(OID x)
{ GC_BIND;
  { ClaireType *Result ;
    { ClaireObject *V_CC ;
      { (Language.LDEF->value= _oid_(list::empty(Kernel._any)));
        { OID  r = GC_OID(extract_pattern_any(x,Kernel.nil));
          if (r == CNULL)
           close_exception(((general_error *) (*Core._general_error)(_string_("[112] wrong type expression ~S"),
            _oid_(list::alloc(1,x)))));
          else V_CC = OBJECT(ClaireType,r);
            } 
        } 
      Result= (ClaireType *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// an item is an integer, a float, a symbol, a string or a type
//
/* The c++ function for: extract_item(x:any,y:any) [NEW_ALLOC+RETURN_ARG] */
OID  extract_item_any(OID x,OID y)
{ GC_BIND;
  { OID Result = 0;
    if (((((INHERIT(OWNER(x),Kernel._integer)) || 
              (Kernel._float == OWNER(x))) || 
            (INHERIT(OWNER(x),Kernel._symbol))) || 
          (Kernel._string == OWNER(x))) || 
        (INHERIT(OWNER(x),Kernel._type)))
     Result = x;
    else if (INHERIT(OWNER(x),Core._global_variable))
     Result = extract_item_any(GC_OID((*Kernel.value)(x)),y);
    else Result = CNULL;
      GC_UNBIND; return (Result);} 
  } 


// version for X[...] which is the most complex case - note the extensibility
// patch.
/* The c++ function for: extract_pattern_nth(l:list,path:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
OID  extract_pattern_nth_list(list *l,list *path)
{ GC_BIND;
  { OID Result = 0;
    { int  m = l->length;
      OID  x = (*(l))[1];
      if (m == 1)
       { OID  y = GC_OID(extract_pattern_any((*(l))[1],Kernel.nil));
        if (y == CNULL)
         Result = CNULL;
        else { Param * _CL_obj = ((Param *) GC_OBJECT(Param,new_object_class(Core._Param)));
            (_CL_obj->arg = Kernel._array);
            (_CL_obj->params = list::alloc(1,_oid_(Kernel.of)));
            (_CL_obj->args = list::alloc(1,_oid_(set::alloc(1,y))));
            Result = _oid_(_CL_obj);
            } 
          } 
      else if (m == 2)
       { if (((x == _oid_(Core._subtype)) || 
              ((x == _oid_(Kernel._set)) || 
                (x == _oid_(Kernel._list)))) || 
            (!INHERIT(OWNER(x),Kernel._class)))
         { OID  y = GC_OID(extract_pattern_any((*(l))[2],Kernel.nil));
          { ClaireHandler c_handle = ClaireHandler();
            if ERROR_IN 
            { if (y != CNULL)
               Result = (*Kernel.nth)((*(l))[1],
                y);
              else Result = CNULL;
                ClEnv->cHandle--;} 
            else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
            { c_handle.catchIt();Result = CNULL;
              } 
            else PREVIOUS_HANDLER;} 
          } 
        else Result = CNULL;
          } 
      else { OID  l1 = (*(l))[2];
          OID  l2 = GC_OID((*Core.args)((*(l))[3]));
          list * l3 = list::empty(Kernel._any);
          { int  n = 1;
            int  g0036 = (*Kernel.length)(l1);
            { OID gc_local;
              while ((n <= g0036))
              { GC_LOOP;
                { OID  y = (*(OBJECT(bag,l2)))[n];
                  { OID  g0037UU;
                    { if (INHERIT(OWNER(y),Language._Set))
                       { OID  v = GC_OID(extract_pattern_any((*(OBJECT(Construct,y)->args))[1],((list *) copy_bag(path))->addFast(GC_OID((*Kernel.nth)(l1,
                          n)))));
                        if (v == _oid_(Kernel._void))
                         g0037UU = _oid_(Kernel._any);
                        else if (INHERIT(OWNER(v),Core._Reference))
                         { Reference * z = GC_OBJECT(Reference,((Reference *) copy_object(OBJECT(ClaireObject,v))));
                          (z->arg = CTRUE);
                          g0037UU = _oid_(z);
                          } 
                        else { set * V_CL0038;{ OID v_bag;
                              GC_ANY(V_CL0038= set::empty(Kernel.emptySet));
                              { if (v != CNULL)
                                 v_bag = v;
                                else v_bag = OPT_EVAL((*(OBJECT(Construct,y)->args))[1]);
                                  GC_OID(v_bag);} 
                              ((set *) V_CL0038)->addFast(v_bag);} 
                            
                            g0037UU=_oid_(V_CL0038);} 
                          } 
                      else { list * g0039UU;
                          { ClaireObject *V_CC ;
                            if (path->length != 0)
                             V_CC = path->addFast(GC_OID((*Kernel.nth)(l1,
                              n)));
                            else V_CC = CFALSE;
                              g0039UU= (list *) V_CC;} 
                          g0037UU = extract_pattern_any(y,g0039UU);
                          } 
                        GC_OID(g0037UU);} 
                    l3 = l3->addFast(g0037UU);
                    } 
                  } 
                ++n;
                GC_UNLOOP;} 
              } 
            } 
          if (l3->memq(CNULL) == CTRUE)
           Result = CNULL;
          else { Param * _CL_obj = ((Param *) GC_OBJECT(Param,new_object_class(Core._Param)));
              update_property(Kernel.arg,
                _CL_obj,
                2,
                Kernel._object,
                x);
              update_property(Kernel.params,
                _CL_obj,
                3,
                Kernel._object,
                l1);
              (_CL_obj->args = l3);
              Result = _oid_(_CL_obj);
              } 
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// we perform some pre-processing on x[l] at reading time to make evaluation easier
/* The c++ function for: iClaire/extract_class_call(self:class,l:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
ClaireObject * extract_class_call_class(ClaireClass *self,list *l)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { ClaireObject *Result ;
    { ClaireObject *V_CC ;
      { ClaireBoolean * g0041I;
        { ClaireBoolean *v_and;
          { v_and = ((self == Core._subtype) ? CTRUE : ((self == Kernel._set) ? CTRUE : ((self == Kernel._list) ? CTRUE : CFALSE)));
            if (v_and == CFALSE) g0041I =CFALSE; 
            else { v_and = ((l->length == 1) ? CTRUE : CFALSE);
              if (v_and == CFALSE) g0041I =CFALSE; 
              else { { OID  y = (*(l))[1];
                  OID  z = GC_OID(extract_pattern_any(y,Kernel.nil));
                  if (INHERIT(OWNER(y),Core._global_variable))
                   y= GC_OID((*Kernel.value)((*(l))[1]));
                  v_and = ((INHERIT(OWNER(z),Kernel._type)) ? CTRUE : ((self == Core._subtype) ? CTRUE : (((INHERIT(OWNER(y),Language._Call)) ? ((OBJECT(Call,y)->selector != Kernel._equal) || 
                      (OBJECT(Call,y)->args->length != 2)) : ((INHERIT(OWNER(y),Language._Tuple)) && (CTRUE == CTRUE))) ? CTRUE : CFALSE)));
                  } 
                if (v_and == CFALSE) g0041I =CFALSE; 
                else g0041I = CTRUE;} 
              } 
            } 
          } 
        
        if (g0041I == CTRUE) { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (_CL_obj->selector = Kernel.nth);
            (_CL_obj->args = cons_any(_oid_(self),l));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
            V_CC = _CL_obj;
            } 
          else if (self == Core._lambda)
         { if ((l->length == 2) && 
              ((INHERIT(OWNER((*(l))[1]),Language._Do)) || 
                  (INHERIT(OWNER((*(l))[1]),Language._Variable))))
           { list * lv;
            if (INHERIT(OWNER((*(l))[1]),Language._Do))
             { list * v_out = list::empty(Kernel.emptySet);
              { OID gc_local;
                ITERATE(v);
                bag *v_support;
                v_support = GC_OBJECT(list,OBJECT(bag,(*Core.args)((*(l))[1])));
                for (START(v_support); NEXT(v);)
                { GC_LOOP;
                  if (INHERIT(OWNER(v),Language._Variable))
                   v_out->addFast(v);
                  GC_UNLOOP;} 
                } 
              lv = GC_OBJECT(list,v_out);
              } 
            else lv = list::alloc(1,(*(l))[1]);
              extract_signature_list(lv);
            V_CC = lambda_I_list(lv,(*(l))[2]);
            } 
          else close_exception(((general_error *) (*Core._general_error)(_string_("[113] Wrong lambda definition lambda[~S]"),
              _oid_(list::alloc(1,_oid_(l))))));
            } 
        else { list * l1 = list::empty(Kernel._any);
            list * l2 = list::empty(Kernel._any);
            int  m = l->length;
            { int  n = 1;
              int  g0040 = m;
              { OID gc_local;
                while ((n <= g0040))
                { GC_LOOP;
                  { OID  y = (*(l))[n];
                    OID  p = CNULL;
                    OID  v = CNULL;
                    if (INHERIT(OWNER(y),Language._Call))
                     { if ((OBJECT(Call,y)->selector != Kernel._equal) || 
                          (OBJECT(Call,y)->args->length != 2))
                       close_exception(((general_error *) (*Core._general_error)(_string_("[114] Wrong parametrization ~S"),
                        _oid_(list::alloc(1,y)))));
                      p= _oid_(make_a_property_any((*(OBJECT(Call,y)->args))[1]));
                      { { Set * _CL_obj = ((Set *) GC_OBJECT(Set,new_object_class(Language._Set)));
                          (_CL_obj->args = list::alloc(1,(*(OBJECT(Call,y)->args))[2]));
                          add_I_property(Kernel.instances,Language._Set,11,_oid_(_CL_obj));
                          v = _oid_(_CL_obj);
                          } 
                         GC__OID(v, 1);} 
                      } 
                    else if (INHERIT(OWNER(y),Language._Vardef))
                     { p= _oid_(make_a_property_any(_oid_(OBJECT(Variable,y)->pname)));
                      GC__OID(v = _oid_(OBJECT(Variable,y)->range), 1);
                      } 
                    else { p= _oid_(make_a_property_any(y));
                        v= _oid_(Kernel.emptySet);
                        } 
                      l1= l1->addFast(p);
                    l2= l2->addFast(v);
                    } 
                  ++n;
                  GC_UNLOOP;} 
                } 
              } 
            { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (_CL_obj->selector = Kernel.nth);
              { Call * g0042; 
                list * g0043;
                g0042 = _CL_obj;
                { list * g0044UU;
                  { OID v_bag;
                    GC_ANY(g0044UU= list::empty(Kernel.emptySet));
                    ((list *) g0044UU)->addFast(_oid_(l1));
                    { { List * _CL_obj = ((List *) GC_OBJECT(List,new_object_class(Language._List)));
                        (_CL_obj->args = l2);
                        add_I_property(Kernel.instances,Language._List,11,_oid_(_CL_obj));
                        v_bag = _oid_(_CL_obj);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) g0044UU)->addFast(v_bag);} 
                  g0043 = cons_any(_oid_(self),g0044UU);
                  } 
                (g0042->args = g0043);} 
              add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
              V_CC = _CL_obj;
              } 
            } 
          } 
      Result= (ClaireObject *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// extract the range (type and/or second-order function)
// lvar is the list of arguments that will serve as second-o. args
// ldef is the list of extra type variables that are defined in the sig.
/* The c++ function for: iClaire/extract_range(x:any,lvar:list,ldef:list) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
list * extract_range_any(OID x,list *lvar,list *ldef)
{ GC_RESERVE(13);  // v3.0.55 optim !
  { list *Result ;
    { ClaireBoolean * g0046I;
      { OID  g0047UU;
        if (INHERIT(OWNER(x),Language._Call))
         g0047UU = _oid_(((OBJECT(Call,x)->selector == Kernel.nth) ? (((*(OBJECT(Call,x)->args))[1] == _oid_(Kernel._type)) ? CTRUE: CFALSE): CFALSE));
        else g0047UU = Kernel.cfalse;
          g0046I = not_any(g0047UU);
        } 
      
      if (g0046I == CTRUE) Result = list::alloc(2,GC_OID(_oid_(extract_type_any(x))),_oid_(Kernel.emptySet));
        else { { OID gc_local;
          ITERATE(v);
          for (START(ldef); NEXT(v);)
          { GC_LOOP;
            { Reference * r = GC_OBJECT(Reference,OBJECT(Reference,(*Kernel.range)(v)));
              list * path = GC_OBJECT(list,r->args);
              int  n = path->length;
              OID  y = (*(lvar))[(r->index+1)];
              { int  i = 1;
                int  g0045 = path->length;
                { OID gc_local;
                  while ((i <= g0045))
                  { GC_LOOP;
                    { { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (_CL_obj->selector = Core._at);
                        (_CL_obj->args = list::alloc(2,y,(*(path))[i]));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                        y = _oid_(_CL_obj);
                        } 
                       GC__OID(y, 9);} 
                    ++i;
                    GC_UNLOOP;} 
                  } 
                } 
              { { OID  g0048UU;
                  { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                    (_CL_obj->selector = Core.member);
                    (_CL_obj->args = list::alloc(1,y));
                    add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                    g0048UU = _oid_(_CL_obj);
                    } 
                  x = substitution_any(x,OBJECT(Variable,v),g0048UU);
                  } 
                 GC__OID(x, 1);} 
              } 
            GC_UNLOOP;} 
          } 
        { list * lv2 = list::empty(Kernel._any);
          { OID gc_local;
            ITERATE(v);
            for (START(lvar); NEXT(v);)
            { GC_LOOP;
              { Variable * v2;
                { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                    (_CL_obj->pname = OBJECT(Variable,v)->pname);
                    (_CL_obj->range = Kernel._type);
                    add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
                    v2 = _CL_obj;
                    } 
                  GC_OBJECT(Variable,v2);} 
                lv2= lv2->addFast(_oid_(v2));
                GC__OID(x = substitution_any(x,OBJECT(Variable,v),_oid_(v2)), 1);
                } 
              GC_UNLOOP;} 
            } 
          { lambda * lb = GC_OBJECT(lambda,lambda_I_list(lv2,(*(OBJECT(bag,(*Core.args)(x))))[2]));
            OID  ur = CNULL;
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { { list * g0049UU;
                    { { bag *v_list; OID v_val;
                        OID v,CLcount;
                        v_list = lvar;
                         g0049UU = v_list->clone();
                        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                        { v = (*(v_list))[CLcount];
                          v_val = (*Kernel.range)(v);
                          
                          (*((list *) g0049UU))[CLcount] = v_val;} 
                        } 
                      GC_OBJECT(list,g0049UU);} 
                    ur = apply_lambda(lb,g0049UU);
                    } 
                  GC_OID(ur);} 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
              { c_handle.catchIt();{ princ_string("The type expression ");
                  print_any(x);
                  princ_string(" is not a valid because\n");
                  princ_string("lambda = ");
                  print_any(_oid_(lb));
                  princ_string(", l = ");
                  { OID  g0050UU;
                    { { list * V_CL0051;{ bag *v_list; OID v_val;
                          OID v,CLcount;
                          v_list = lvar;
                           V_CL0051 = v_list->clone();
                          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                          { v = (*(v_list))[CLcount];
                            v_val = (*Kernel.range)(v);
                            
                            (*((list *) V_CL0051))[CLcount] = v_val;} 
                          } 
                        
                        g0050UU=_oid_(V_CL0051);} 
                      GC_OID(g0050UU);} 
                    print_any(g0050UU);
                    } 
                  princ_string("\n");
                  close_exception(ClEnv->exception_I);
                  } 
                } 
              else PREVIOUS_HANDLER;} 
            if (!INHERIT(OWNER(ur),Kernel._type))
             close_exception(((general_error *) (*Core._general_error)(_string_("[115] the (resulting) range ~S is not a type"),
              _oid_(list::alloc(1,ur)))));
            Result = list::alloc(2,ur,_oid_(lb));
            } 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// define the different components of status(m), which is a bit vector
// a new allocation may be done by running the method
// a list is updated whose content is not gcsafe
// an slot is updated whose content is not gcsafe
// the method returns one of its args
// the result (not gcsafe) does not need protection
// the result (not gcsafe) does not need protection
// create a bitvector from a list of flags
/* The c++ function for: bit_vector(l:listargs) [0] */
int  bit_vector_listargs2(listargs *l)
{ { int Result = 0;
    { int  d = 0;
      { ITERATE(x);
        for (START(l); NEXT(x);)
        d= (d+exp2_integer(x));
        } 
      Result = d;
      } 
    return (Result);} 
  } 


// parse the body and return (status, functional, body)
// the input is  body | (function!(f) | function!(f,s)) < | body> opt
//
/* The c++ function for: iClaire/extract_status(x:any) [NEW_ALLOC] */
list * extract_status_any(OID x)
{ GC_BIND;
  { list *Result ;
    { OID  s = CNULL;
      OID  f;
      if ((INHERIT(OWNER(x),Language._Call)) && (OBJECT(Call,x)->selector == Language.function_I))
       f = x;
      else f = CNULL;
        if (INHERIT(OWNER(x),Language._And))
       { OID  y = (*(OBJECT(And,x)->args))[1];
        if ((INHERIT(OWNER(y),Language._Call)) && (OBJECT(Call,y)->selector == Language.function_I))
         { f= y;
          x= (*(OBJECT(And,x)->args))[2];
          } 
        } 
      else if (INHERIT(OWNER(x),Language._Call))
       { if (OBJECT(Call,x)->selector == Language.function_I)
         x= _oid_(Kernel.body);
        } 
      else ;if (f != CNULL)
       { x= _oid_(Kernel.body);
        if (length_bag(OBJECT(bag,(*Core.args)(f))) > 1)
         { int  V_CL0052;{ set * g0053UU;
            { set * u_bag = set::empty(Kernel.emptySet);
              { OID gc_local;
                ITERATE(u);
                bag *u_support;
                u_support = GC_OBJECT(list,cdr_list(GC_OBJECT(list,OBJECT(list,(*Core.args)(f)))));
                for (START(u_support); NEXT(u);)
                { GC_LOOP;
                  { OID  g0054UU;
                    { if (INHERIT(OWNER(u),Kernel._integer))
                       g0054UU = u;
                      else if (INHERIT(OWNER(u),Core._global_variable))
                       g0054UU = OBJECT(global_variable,u)->value;
                      else close_exception(((general_error *) (*Core._general_error)(_string_("[116] ~S not allowed in function!"),
                          _oid_(list::alloc(1,u)))));
                        GC_OID(g0054UU);} 
                    u_bag->addFast(g0054UU);
                    } 
                  GC_UNLOOP;} 
                } 
              g0053UU = GC_OBJECT(set,u_bag);
              } 
            V_CL0052 = integer_I_set(g0053UU);
            } 
          
          s=V_CL0052;} 
        else s= 0;
          f= _oid_(make_function_string(string_I_symbol(extract_symbol_any((*(OBJECT(bag,(*Core.args)(f))))[1]))));
        } 
      Result = list::alloc(3,s,
        f,
        x);
      } 
    GC_UNBIND; return (Result);} 
  } 


// cleans a pattern into a type
//
/* The c++ function for: iClaire/type!(x:any) [NEW_ALLOC+RETURN_ARG] */
ClaireType * type_I_any(OID x)
{ GC_BIND;
  { ClaireType *Result ;
    if (INHERIT(OWNER(x),Kernel._list))
     { bag *v_list; OID v_val;
      OID y,CLcount;
      v_list = OBJECT(bag,x);
       Result = v_list->clone();
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { y = (*(v_list))[CLcount];
        v_val = _oid_(type_I_any(y));
        
        (*((list *) Result))[CLcount] = v_val;} 
      } 
    else if (INHERIT(OWNER(x),Core._Param))
     { Param * _CL_obj = ((Param *) GC_OBJECT(Param,new_object_class(Core._Param)));
      (_CL_obj->arg = OBJECT(Param,x)->arg);
      (_CL_obj->params = OBJECT(Param,x)->params);
      { Param * g0055; 
        list * g0056;
        g0055 = _CL_obj;
        { bag *v_list; OID v_val;
          OID y,CLcount;
          v_list = GC_OBJECT(list,OBJECT(Param,x)->args);
           g0056 = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { y = (*(v_list))[CLcount];
            v_val = _oid_(type_I_any(y));
            
            (*((list *) g0056))[CLcount] = v_val;} 
          } 
        (g0055->args = g0056);} 
      Result = _CL_obj;
      } 
    else if (INHERIT(OWNER(x),Core._Reference))
     Result = Kernel._any;
    else Result = OBJECT(ClaireType,x);
      GC_UNBIND; return (Result);} 
  } 


// creates a table
// to do in later versions: use an array if direct indexed access
// in the meanwhile, arrays of float should be used with care (indexed arrays)
//
/* The c++ function for: self_eval(self:Defarray) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+SAFE_RESULT] */
OID  self_eval_Defarray(Defarray *self)
{ GC_BIND;
  { OID Result = 0;
    { list * a = GC_OBJECT(list,self->arg->args);
      table * ar = ((table *) new_thing_class(Kernel._table,extract_symbol_any((*(a))[1])));
      Variable * v = OBJECT(Variable,(*(a))[2]);
      ClaireType * s = GC_OBJECT(ClaireType,extract_type_any(GC_OID(_oid_(v->range))));
      OID  e;
      { { list * l = GC_OBJECT(list,cdr_list(a));
          OID  b = GC_OID(lexical_build_any(GC_OID(self->body),l,0));
          { ClaireBoolean * g0057I;
            { OID  g0058UU;
              { int  V_CL0059;{ set * g0060UU;
                  { set * va_out = set::empty(Kernel.emptySet);
                    { OID gc_local;
                      ITERATE(va);
                      for (START(l); NEXT(va);)
                      { GC_LOOP;
                        if (occurrence_any(b,OBJECT(Variable,va)) > 0)
                         va_out->addFast(va);
                        GC_UNLOOP;} 
                      } 
                    g0060UU = GC_OBJECT(set,va_out);
                    } 
                  V_CL0059 = g0060UU->length;
                  } 
                
                g0058UU=V_CL0059;} 
              g0057I = ((g0058UU != 0) ? CTRUE : CFALSE);
              } 
            
            if (g0057I == CTRUE) e = _oid_(lambda_I_list(l,b));
              else e = self->body;
            } 
          } 
        GC_OID(e);} 
      OID  d;
      { if (INHERIT(OWNER(e),Core._lambda))
         d = CNULL;
        else d = eval_any(GC_OID(self->body));
          GC_OID(d);} 
      update_property(Kernel.range,
        ar,
        5,
        Kernel._object,
        GC_OID(extract_pattern_any(GC_OID(self->set_arg),Kernel.nil)));
      if ((d != CNULL) && 
          (belong_to(d,_oid_(ar->range)) != CTRUE))
       close_exception(((range_error *) (*Core._range_error)(_oid_(ar),
        d,
        _oid_(ar->range))));
      (v->range = s);
      attach_comment_any(_oid_(ar));
      if (INHERIT(class_I_type(ar->range),Kernel._set))
       (ar->multivalued_ask = CTRUE);
      else if ((INHERIT(class_I_type(ar->range),Kernel._list)) && 
          (!INHERIT(ar->range->isa,Language._Tuple)))
       (ar->multivalued_ask = Kernel._list);
      if (a->length == 2)
       { (ar->domain = s);
        if (INHERIT(s->isa,Core._Interval))
         { (ar->params = (CLREAD(Interval,s,arg1)-1));
          (ar->graph = make_copy_list_integer(size_Interval(((Interval *) s)),d));
          } 
        else { (ar->params = _oid_(Kernel._any));
            (ar->graph = make_list_integer(29,CNULL));
            } 
          if (INHERIT(OWNER(e),Core._lambda))
         { OID gc_local;
          ITERATE(y);
          bag *y_support;
          y_support = GC_OBJECT(bag,enumerate_any(_oid_(ar->domain)));
          for (START(y_support); NEXT(y);)
          { GC_LOOP;
            nth_equal_table1(ar,y,GC_OID(funcall_lambda1(OBJECT(lambda,e),y)));
            GC_UNLOOP;} 
          } 
        else (ar->DEFAULT = d);
          } 
      else { ClaireType * s2 = GC_OBJECT(ClaireType,extract_type_any(GC_OID(_oid_(OBJECT(Variable,(*(a))[3])->range))));
          (ar->domain = tuple_I_list(list::alloc(2,_oid_(s),_oid_(s2))));
          (OBJECT(Variable,(*(a))[3])->range = s2);
          if ((INHERIT(s->isa,Core._Interval)) && 
              (INHERIT(s2->isa,Core._Interval)))
           { (ar->params = _oid_(list::alloc(2,Core.size->fcall(((int) s2)),(((CLREAD(Interval,s,arg1)*(Core.size->fcall(((int) s2))))+CLREAD(Interval,s2,arg1))-1))));
            (ar->graph = make_copy_list_integer(((Core.size->fcall(((int) s)))*(Core.size->fcall(((int) s2)))),d));
            } 
          else { (ar->params = _oid_(Kernel._any));
              (ar->graph = make_list_integer(29,CNULL));
              } 
            if (INHERIT(OWNER(e),Core._lambda))
           { OID gc_local;
            ITERATE(y1);
            bag *y1_support;
            y1_support = GC_OBJECT(bag,enumerate_any(_oid_(s)));
            for (START(y1_support); NEXT(y1);)
            { GC_LOOP;
              { OID gc_local;
                ITERATE(y2);
                bag *y2_support;
                y2_support = GC_OBJECT(bag,enumerate_any(_oid_(s2)));
                for (START(y2_support); NEXT(y2);)
                { GC_LOOP;
                  nth_equal_table2(ar,y1,y2,GC_OID((*Kernel.funcall)(e,
                    y1,
                    y2)));
                  GC_UNLOOP;} 
                } 
              GC_UNLOOP;} 
            } 
          else (ar->DEFAULT = d);
            } 
        Result = _oid_(ar);
      } 
    GC_UNBIND; return (Result);} 
  } 


// ------------------ NEW in v3.2 : definition of rules -----------------------
//
// a demon is a lambda with a name and a priority
/* The c++ function for: self_print(self:demon) [0] */
void  self_print_demon(demon *self)
{ princ_symbol(self->pname);
  } 


/* The c++ function for: funcall(self:demon,x:any,y:any) [NEW_ALLOC] */
OID  funcall_demon1(demon *self,OID x,OID y)
{ GC_BIND;
  { OID Result = 0;
    Result = (*Kernel.funcall)(GC_OID(_oid_(self->formula)),
      x,
      y);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: funcall(self:demon,x:any,y:any,z:any) [NEW_ALLOC] */
OID  funcall_demon2(demon *self,OID x,OID y,OID z)
{ GC_BIND;
  { OID Result = 0;
    Result = (*Kernel.funcall)(GC_OID(_oid_(self->formula)),
      x,
      y,
      z);
    GC_UNBIND; return (Result);} 
  } 


// in the interpreted mode we store the list of demons using a table
// list of relevant demons
// the last rule/axiom that was defined on each relation
// this is used to find when the relation may be compiled
// list of involved relations
// compile(ru) => may compile(r)
// evaluate a rule definition: create a new demon and, if needed, the if_write 
// function
/* The c++ function for: self_eval(self:Defrule) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
OID  self_eval_Defrule(Defrule *self)
{ if ((*(self->args))[1] != _oid_(ClEnv)) 
  { { OID Result = 0;
      Result = (*Language.eval_rule)(_oid_(self));
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      { OID  _Zcondition = GC_OID(self->arg);
        OID  ru = get_symbol(self->ident);
        (OBJECT(ClaireObject,ru)->isa = Language._rule_object);
        add_I_property(Kernel.instances,Language._rule_object,11,ru);
        { tuple * g0061 = make_filter_any(_Zcondition);
          OID  R = (*(g0061))[1];
          OID  lvar = (*(g0061))[2];
          demon * d = make_demon_relation(OBJECT(ClaireRelation,R),
            OBJECT(symbol,(*Kernel.name)(ru)),
            OBJECT(list,lvar),
            _Zcondition,
            GC_OID(lexical_build_any(GC_OID(self->body),OBJECT(list,lvar),0)));
          if (INHERIT(OWNER(OBJECT(ClaireRelation,R)->if_write),Kernel._function))
           close_exception(((general_error *) (*Core._general_error)(_string_("cannot define a new rule on ~S which is closed"),
            _oid_(list::alloc(1,R)))));
          add_table(Language.demons,R,_oid_(d));
          nth_put_table(Language.last_rule,R,ru);
          if (length_bag(OBJECT(bag,nth_table1(Language.demons,R))) == 1)
           eval_if_write_relation(OBJECT(ClaireRelation,R));
          if ((INHERIT(OBJECT(ClaireObject,R)->isa,Kernel._property)) && (OBJECT(property,R)->restrictions->length == 0))
           eventMethod_property(OBJECT(property,R));
          Result = ru;
          } 
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


// an eventMethod is a property whose unique (?) restriction is a method
/* The c++ function for: eventMethod?(r:relation) [0] */
ClaireBoolean * eventMethod_ask_relation2(ClaireRelation *r)
{ { ClaireBoolean *Result ;
    if (INHERIT(r->isa,Kernel._property))
     { OID  g0062UU;
      { ITERATE(x);
        g0062UU= _oid_(CFALSE);
        for (START(CLREAD(property,r,restrictions)); NEXT(x);)
        if (Kernel._slot == OBJECT(ClaireObject,x)->isa)
         { g0062UU = Kernel.ctrue;
          break;} 
        } 
      Result = not_any(g0062UU);
      } 
    else Result = CFALSE;
      return (Result);} 
  } 


// check that condition is either a filter or the conjunction of a filter and a 
// condition
// a filter is R(x) := y | R(x) := (y <- z) | R(x) :add y | P(x,y)
// R(x) is x.r or A[x]
// the list of variable is of length 3 if R is mono-valued
/* The c++ function for: make_filter(g0063:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
tuple * make_filter_any_(OID g0063)
{ return make_filter_any(g0063)->copyIfNeeded();} 


/* The c++ function for: make_filter(cond:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
tuple * make_filter_any(OID cond)
{ GC_BIND;
  { tuple *Result ;
    { ClaireObject *V_CC ;
      { OID  c;
        if (INHERIT(OWNER(cond),Language._And))
         c = (*(OBJECT(And,cond)->args))[1];
        else c = cond;
          tformat_string("make_filter : ~S (~S) \n",0,list::alloc(2,c,_oid_(OBJECT(ClaireObject,c)->isa)));
        if ((INHERIT(OWNER(c),Language._Call)) && (((OBJECT(Call,c)->selector == Core.write) || 
              (OBJECT(Call,c)->selector == Kernel.nth_equal)) && 
            (INHERIT(OWNER((*(OBJECT(Call,c)->args))[1]),Kernel._relation))))
         { ClaireRelation * R = OBJECT(ClaireRelation,(*(OBJECT(bag,(*Core.args)(c))))[1]);
          Variable * x;
          { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
              (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(c))))[2]));
              (_CL_obj->range = R->domain);
              add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
              x = _CL_obj;
              } 
            GC_OBJECT(Variable,x);} 
          OID  y1 = (*(OBJECT(bag,(*Core.args)(c))))[3];
          if (multi_ask_any(_oid_(R)) == CTRUE)
           close_exception(((general_error *) (*Core._general_error)(_string_("[188] wrong event filter ~S for multi-valued relation"),
            _oid_(list::alloc(2,c,_oid_(R))))));
          if ((INHERIT(OWNER(y1),Language._Call)) && (OBJECT(Call,y1)->selector == Language._inf_dash))
           { OID v_bag;
            GC_ANY(V_CC= tuple::empty());
            ((tuple *) V_CC)->addFast(_oid_(R));
            { list * V_CL0064;{ OID v_bag;
                GC_ANY(V_CL0064= list::empty(Kernel.emptySet));
                ((list *) V_CL0064)->addFast(_oid_(x));
                { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                    (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(y1))))[1]));
                    (_CL_obj->range = R->range);
                    add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
                    v_bag = _oid_(_CL_obj);
                    } 
                  GC_OID(v_bag);} 
                ((list *) V_CL0064)->addFast(v_bag);
                { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                    (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(y1))))[2]));
                    (_CL_obj->range = R->range);
                    add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
                    v_bag = _oid_(_CL_obj);
                    } 
                  GC_OID(v_bag);} 
                ((list *) V_CL0064)->addFast(v_bag);} 
              
              v_bag=_oid_(V_CL0064);} 
            ((tuple *) V_CC)->addFast(v_bag);} 
          else { OID v_bag;
              GC_ANY(V_CC= tuple::empty());
              ((tuple *) V_CC)->addFast(_oid_(R));
              { list * V_CL0065;{ OID v_bag;
                  GC_ANY(V_CL0065= list::empty(Kernel.emptySet));
                  ((list *) V_CL0065)->addFast(_oid_(x));
                  { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                      (_CL_obj->pname = extract_symbol_any(y1));
                      (_CL_obj->range = R->range);
                      add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
                      v_bag = _oid_(_CL_obj);
                      } 
                    GC_OID(v_bag);} 
                  ((list *) V_CL0065)->addFast(v_bag);
                  { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                      (_CL_obj->pname = gensym_void());
                      (_CL_obj->range = R->range);
                      add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
                      v_bag = _oid_(_CL_obj);
                      } 
                    GC_OID(v_bag);} 
                  ((list *) V_CL0065)->addFast(v_bag);} 
                
                v_bag=_oid_(V_CL0065);} 
              ((tuple *) V_CC)->addFast(v_bag);} 
            } 
        else if ((INHERIT(OWNER(c),Language._Call)) && ((OBJECT(Call,c)->selector == Kernel.add) && 
            (INHERIT(OWNER((*(OBJECT(Call,c)->args))[1]),Kernel._relation))))
         { ClaireRelation * R = OBJECT(ClaireRelation,(*(OBJECT(bag,(*Core.args)(c))))[1]);
          Variable * x;
          { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
              (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(c))))[2]));
              (_CL_obj->range = R->domain);
              add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
              x = _CL_obj;
              } 
            GC_OBJECT(Variable,x);} 
          Variable * y;
          { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
              (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(c))))[3]));
              (_CL_obj->range = R->range);
              add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
              y = _CL_obj;
              } 
            GC_OBJECT(Variable,y);} 
          V_CC = tuple::alloc(2,_oid_(R),_oid_(list::alloc(2,_oid_(x),_oid_(y))));
          } 
        else if ((INHERIT(OWNER(c),Language._Call)) && (OBJECT(Call,c)->args->length == 2))
         { property * R = OBJECT(Call,c)->selector;
          Variable * x;
          { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
              (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(c))))[1]));
              (_CL_obj->range = R->domain);
              add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
              x = _CL_obj;
              } 
            GC_OBJECT(Variable,x);} 
          Variable * y;
          { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
              (_CL_obj->pname = extract_symbol_any((*(OBJECT(bag,(*Core.args)(c))))[2]));
              (_CL_obj->range = R->range);
              add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
              y = _CL_obj;
              } 
            GC_OBJECT(Variable,y);} 
          V_CC = tuple::alloc(2,_oid_(R),_oid_(list::alloc(2,_oid_(x),_oid_(y))));
          } 
        else close_exception(((general_error *) (*Core._general_error)(_string_("[188] wrong event filter: ~S"),
            _oid_(list::alloc(1,c)))));
          } 
      Result= (tuple *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// create a demon
// notice that a demon has 3 args if R is monovalued 
/* The c++ function for: make_demon(R:relation,n:symbol,lvar:list[Variable],cond:any,conc:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
demon * make_demon_relation(ClaireRelation *R,symbol *n,list *lvar,OID cond,OID conc)
{ GC_BIND;
  { demon *Result ;
    { OID  x = (*(lvar))[1];
      OID  y = (*(lvar))[2];
      OID  _Ztest;
      { { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (_CL_obj->selector = ((multi_ask_any(_oid_(R)) == CTRUE) ?
            Kernel._Z :
            Kernel._equal ));
          (_CL_obj->args = list::alloc(2,y,GC_OID(_oid_(readCall_relation(R,x)))));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
          _Ztest = _oid_(_CL_obj);
          } 
        GC_OID(_Ztest);} 
      OID  _Zbody = conc;
      if (Kernel.if_write->trace_I > ClEnv->verbose)
       { { Do * _CL_obj = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
          { Do * g0066; 
            list * g0067;
            g0066 = _CL_obj;
            { OID v_bag;
              GC_ANY(g0067= list::empty(Kernel.emptySet));
              { { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (_CL_obj->selector = Core.format);
                  { Call * g0068; 
                    list * g0069;
                    g0068 = _CL_obj;
                    { OID v_bag;
                      GC_ANY(g0069= list::empty(Kernel.emptySet));
                      ((list *) g0069)->addFast(_string_("--- trigger ~A(~S,~S)\n"));
                      { { List * _CL_obj = ((List *) GC_OBJECT(List,new_object_class(Language._List)));
                          (_CL_obj->args = list::alloc(3,_string_(string_I_symbol(n)),
                            x,
                            y));
                          add_I_property(Kernel.instances,Language._List,11,_oid_(_CL_obj));
                          v_bag = _oid_(_CL_obj);
                          } 
                        GC_OID(v_bag);} 
                      ((list *) g0069)->addFast(v_bag);} 
                    (g0068->args = g0069);} 
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                  v_bag = _oid_(_CL_obj);
                  } 
                GC_OID(v_bag);} 
              ((list *) g0067)->addFast(v_bag);
              ((list *) g0067)->addFast(conc);} 
            (g0066->args = g0067);} 
          add_I_property(Kernel.instances,Language._Do,11,_oid_(_CL_obj));
          conc = _oid_(_CL_obj);
          } 
        GC_OID(conc);} 
      { { If * _CL_obj = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
          (_CL_obj->arg = conc);
          add_I_property(Kernel.instances,Language._If,11,_oid_(_CL_obj));
          (_CL_obj->other = Kernel.cfalse);
          _Zbody = _oid_(_CL_obj);
          } 
        GC_OID(_Zbody);} 
      if (eventMethod_ask_relation2(R) == CTRUE)
       { if (INHERIT(OWNER(cond),Language._And))
         if (OBJECT(And,cond)->args->length > 2)
         { And * _CL_obj = ((And *) GC_OBJECT(And,new_object_class(Language._And)));
          (_CL_obj->args = cdr_list(GC_OBJECT(list,OBJECT(And,cond)->args)));
          add_I_property(Kernel.instances,Language._And,11,_oid_(_CL_obj));
          _Ztest = _oid_(_CL_obj);
          } 
        else _Ztest = (*(OBJECT(And,cond)->args))[2];
          else _Zbody= conc;
          } 
      else if (INHERIT(OWNER(cond),Language._And))
       { { And * _CL_obj = ((And *) GC_OBJECT(And,new_object_class(Language._And)));
          (_CL_obj->args = append_list(list::alloc(1,_Ztest),GC_OBJECT(list,cdr_list(GC_OBJECT(list,OBJECT(And,cond)->args)))));
          add_I_property(Kernel.instances,Language._And,11,_oid_(_CL_obj));
          _Ztest = _oid_(_CL_obj);
          } 
        GC_OID(_Ztest);} 
      if (INHERIT(OWNER(_Zbody),Language._If))
       (OBJECT(If,_Zbody)->test = _Ztest);
      { demon * _CL_obj = ((demon *) GC_OBJECT(demon,new_object_class(Language._demon)));
        (_CL_obj->pname = n);
        (_CL_obj->formula = lambda_I_list(lvar,_Zbody));
        add_I_property(Kernel.instances,Language._demon,11,_oid_(_CL_obj));
        Result = _CL_obj;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// cute litle guy
/* The c++ function for: readCall(R:relation,x:any) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE] */
Call * readCall_relation(ClaireRelation *R,OID x)
{ if (INHERIT(R->isa,Kernel._table)) 
  { { Call *Result ;
      { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (_CL_obj->selector = Kernel.nth);
        (_CL_obj->args = list::alloc(2,_oid_(R),x));
        add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
        Result = _CL_obj;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { Call *Result ;
      { Call_plus * _CL_obj = ((Call_plus *) GC_OBJECT(Call_plus,new_object_class(Language._Call_plus)));
        (_CL_obj->selector = ((property *) R));
        (_CL_obj->args = list::alloc(1,x));
        add_I_property(Kernel.instances,Language._Call_plus,11,_oid_(_CL_obj));
        Result = _CL_obj;
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


// a small brother
/* The c++ function for: putCall(R:relation,x:any,y:any) [NEW_ALLOC] */
Call * putCall_relation2(ClaireRelation *R,OID x,OID y)
{ if (multi_ask_any(_oid_(R)) == CTRUE) 
  { { Call *Result ;
      { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (_CL_obj->selector = Core.add_value);
        (_CL_obj->args = list::alloc(3,_oid_(R),
          x,
          y));
        add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
        Result = _CL_obj;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { Call *Result ;
      { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (_CL_obj->selector = Kernel.put);
        (_CL_obj->args = list::alloc(3,_oid_(R),
          x,
          y));
        add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
        Result = _CL_obj;
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


// generate an if_write "daemon", only the first time, which uses
// the list in demons[R]
// the first step is to make the update (with inverse management)
/* The c++ function for: eval_if_write(R:relation) [NEW_ALLOC+BAG_UPDATE+SLOT_UPDATE+RETURN_ARG] */
void  eval_if_write_relation(ClaireRelation *R)
{ GC_BIND;
  { OID  l = nth_table1(Language.demons,_oid_(R));
    list * lvar = GC_OBJECT(list,OBJECT(demon,(*(OBJECT(bag,l)))[1])->formula->vars);
    Variable * dv;
    { { Variable * _CL_obj = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
        (_CL_obj->pname = gensym_void());
        (_CL_obj->range = Language._demon);
        add_I_property(Kernel.instances,Language._Variable,11,_oid_(_CL_obj));
        dv = _CL_obj;
        } 
      GC_OBJECT(Variable,dv);} 
    list * l1 = list::alloc(Kernel._any,1,GC_OID(_oid_(putCall_relation2(R,(*(lvar))[1],(*(lvar))[2]))));
    list * l2;
    { OID v_bag;
      GC_ANY(l2= list::empty(Kernel._any));
      { { For * _CL_obj = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
          (_CL_obj->var = dv);
          { Iteration * g0070; 
            OID  g0071;
            g0070 = _CL_obj;
            { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (_CL_obj->selector = Kernel.nth);
              (_CL_obj->args = list::alloc(2,_oid_(Language.demons),_oid_(R)));
              add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
              g0071 = _oid_(_CL_obj);
              } 
            (g0070->set_arg = g0071);} 
          { Iteration * g0072; 
            OID  g0073;
            g0072 = _CL_obj;
            { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (_CL_obj->selector = Kernel.funcall);
              (_CL_obj->args = append_list(list::alloc(1,_oid_(dv)),lvar));
              add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
              g0073 = _oid_(_CL_obj);
              } 
            (g0072->arg = g0073);} 
          add_I_property(Kernel.instances,Language._For,11,_oid_(_CL_obj));
          v_bag = _oid_(_CL_obj);
          } 
        GC_OID(v_bag);} 
      ((list *) l2)->addFast(v_bag);} 
    { OID gc_local;
      ITERATE(v);
      for (START(lvar); NEXT(v);)
      { GC_LOOP;
        put_property2(Kernel.range,OBJECT(ClaireObject,v),_oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Kernel.range)(v))))));
        GC_UNLOOP;} 
      } 
    if (R->inverse != (NULL))
     { if (multi_ask_any(_oid_(R)) != CTRUE)
       { OID  g0074UU;
        { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (_CL_obj->selector = Core.update_dash);
          (_CL_obj->args = list::alloc(3,_oid_(R->inverse),
            (*(lvar))[3],
            (*(lvar))[1]));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
          g0074UU = _oid_(_CL_obj);
          } 
        l1 = l1->addFast(g0074UU);
        } 
      l1= l1->addFast(GC_OID(_oid_(putCall_relation2(R->inverse,(*(lvar))[2],(*(lvar))[1]))));
      } 
    { ClaireRelation * g0075; 
      OID  g0076;
      g0075 = R;
      { lambda * V_CL0077;{ OID  g0078UU;
          if (eventMethod_ask_relation2(R) == CTRUE)
           { Do * _CL_obj = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
            (_CL_obj->args = l2);
            add_I_property(Kernel.instances,Language._Do,11,_oid_(_CL_obj));
            g0078UU = _oid_(_CL_obj);
            } 
          else if (multi_ask_any(_oid_(R)) == CTRUE)
           { If * _CL_obj = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
            { If * g0079; 
              OID  g0080;
              g0079 = _CL_obj;
              { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (_CL_obj->selector = Core.NOT);
                { Call * g0081; 
                  list * g0082;
                  g0081 = _CL_obj;
                  { OID v_bag;
                    GC_ANY(g0082= list::empty(Kernel.emptySet));
                    { { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (_CL_obj->selector = Kernel._Z);
                        (_CL_obj->args = list::alloc(2,(*(lvar))[2],GC_OID(_oid_(readCall_relation(R,(*(lvar))[1])))));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                        v_bag = _oid_(_CL_obj);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) g0082)->addFast(v_bag);} 
                  (g0081->args = g0082);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                g0080 = _oid_(_CL_obj);
                } 
              (g0079->test = g0080);} 
            { If * g0083; 
              OID  g0084;
              g0083 = _CL_obj;
              { Do * _CL_obj = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                (_CL_obj->args = append_list(l1,l2));
                add_I_property(Kernel.instances,Language._Do,11,_oid_(_CL_obj));
                g0084 = _oid_(_CL_obj);
                } 
              (g0083->arg = g0084);} 
            add_I_property(Kernel.instances,Language._If,11,_oid_(_CL_obj));
            (_CL_obj->other = Kernel.cfalse);
            g0078UU = _oid_(_CL_obj);
            } 
          else { Let * _CL_obj = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
              store_object(_CL_obj,
                2,
                Kernel._object,
                (*(lvar))[3],
                CFALSE);
              (_CL_obj->value = _oid_(readCall_relation(R,(*(lvar))[1])));
              { Let * g0085; 
                OID  g0086;
                g0085 = _CL_obj;
                { If * _CL_obj = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
                  { If * g0087; 
                    OID  g0088;
                    g0087 = _CL_obj;
                    { Call * _CL_obj = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                      (_CL_obj->selector = Core._I_equal);
                      (_CL_obj->args = list::alloc(2,(*(lvar))[2],(*(lvar))[3]));
                      add_I_property(Kernel.instances,Language._Call,11,_oid_(_CL_obj));
                      g0088 = _oid_(_CL_obj);
                      } 
                    (g0087->test = g0088);} 
                  { If * g0089; 
                    OID  g0090;
                    g0089 = _CL_obj;
                    { Do * _CL_obj = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                      (_CL_obj->args = append_list(l1,l2));
                      add_I_property(Kernel.instances,Language._Do,11,_oid_(_CL_obj));
                      g0090 = _oid_(_CL_obj);
                      } 
                    (g0089->arg = g0090);} 
                  add_I_property(Kernel.instances,Language._If,11,_oid_(_CL_obj));
                  (_CL_obj->other = Kernel.cfalse);
                  g0086 = _oid_(_CL_obj);
                  } 
                (g0085->arg = g0086);} 
              add_I_property(Kernel.instances,Language._Let,11,_oid_(_CL_obj));
              g0078UU = _oid_(_CL_obj);
              } 
            V_CL0077 = lambda_I_list(list::alloc(2,(*(lvar))[1],(*(lvar))[2]),g0078UU);
          } 
        
        g0076=_oid_(V_CL0077);} 
      (g0075->if_write = g0076);} 
    } 
  GC_UNBIND;} 


// create a restriction (method) that will trigger an event
/* The c++ function for: eventMethod(p:property) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  eventMethod_property(property *p)
{ { method * m = add_method_property(p,
      list::alloc(2,_oid_(p->domain),_oid_(p->range)),
      Kernel._void,
      0,
      CNULL);
    store_object(m,
      8,
      Kernel._object,
      p->if_write,
      CFALSE);
    (m->functional = make_function_string(append_string(string_I_symbol(p->name),"_write")));
    } 
  } 


// new in v3.1: the inter face pragma ******************************
// this array is used to store the declarations
// define a property as an interface
/* The c++ function for: interface(p:property) [NEW_ALLOC+SLOT_UPDATE+RETURN_ARG] */
void  interface_property(property *p)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  if (boolean_I_any(_oid_(p->restrictions)) != CTRUE)
   close_exception(((general_error *) (*Core._general_error)(_string_("[185] cannot define an empty property ~S as an interface"),
    _oid_(list::alloc(1,_oid_(p))))));
  if ((uniform_property(p) != CTRUE) || 
      (OBJECT(restriction,(*(p->restrictions))[1])->domain->memq(_oid_(Kernel._float)) == CTRUE))
   close_exception(((general_error *) (*Core._general_error)(_string_("[185] cannot define an non-uniform property ~S as an interface"),
    _oid_(list::alloc(1,_oid_(p))))));
  { ClaireClass * d = domain_I_restriction(OBJECT(restriction,(*(p->restrictions))[1]));
    list * ls = list::empty(Kernel._any);
    { OID gc_local;
      ITERATE(g0091);
      bag *g0091_support;
      g0091_support = Kernel._property->descendents;
      for (START(g0091_support); NEXT(g0091);)
      { GC_LOOP;
        { OID  g0092;
          { { OID gc_local;
              ITERATE(p2);
              g0092= _oid_(CFALSE);
              for (START(OBJECT(ClaireClass,g0091)->instances); NEXT(p2);)
              { GC_LOOP;
                if ((OBJECT(property,p2)->dispatcher > 0) && 
                    (boolean_I_any((*Core.glb)(_oid_(OBJECT(ClaireRelation,p2)->domain),
                      _oid_(p->domain))) == CTRUE))
                 ls= ls->addFast(OBJECT(property,p2)->dispatcher);
                GC_UNLOOP;} 
              } 
            GC_OID(g0092);} 
          if (boolean_I_any(g0092) == CTRUE)
           { ;break;} 
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(x);
      for (START(p->restrictions); NEXT(x);)
      { GC_LOOP;
        d= meet_class(d,domain_I_restriction(OBJECT(restriction,x)));
        GC_UNLOOP;} 
      } 
    (p->domain = d);
    { property * g0094; 
      int  g0095;
      g0094 = p;
      { OID  i_some = CNULL;
        { int  i = 1;
          int  g0093 = (ls->length+1);
          { OID gc_local;
            while ((i <= g0093))
            { // HOHO, GC_LOOP not needed !
              if (ls->memq(i) != CTRUE)
               { i_some= i;
                break;} 
              ++i;
              } 
            } 
          } 
        g0095 = i_some;
        } 
      (g0094->dispatcher = g0095);} 
    } 
  GC_UNBIND;} 


/* The c++ function for: interface(c:class,l:listargs) [NEW_ALLOC+BAG_UPDATE] */
void  interface_class(ClaireClass *c,listargs *l)
{ GC_BIND;
  { OID  g0096UU;
    { list * V_CL0097;{ list * x_out = list::empty(Kernel._property);
        { ITERATE(x);
          for (START(l); NEXT(x);)
          if (INHERIT(OWNER(x),Kernel._property))
           x_out->addFast(x);
          } 
        V_CL0097 = GC_OBJECT(list,x_out);
        } 
      
      g0096UU=_oid_(V_CL0097);} 
    put_table(Language.InterfaceList,_oid_(c),g0096UU);
    } 
  { property * px = Language.ClaireInterface;
    { ITERATE(p);
      bag *p_support;
      p_support = OBJECT(bag,nth_table1(Language.InterfaceList,_oid_(c)));
      for (START(p_support); NEXT(p);)
      if ((*Kernel.open)(p) == 3)
       (*Core.call)(_oid_(px),
        p);
      } 
    } 
  GC_UNBIND;} 


// only implied for open properties !!!!
// ****************** Construction *********************************
// filling the evaluation form