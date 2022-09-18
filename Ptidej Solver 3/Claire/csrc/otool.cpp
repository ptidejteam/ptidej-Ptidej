/***** CLAIRE Compilation of file d:\claire\v3.2\src\compile\otool.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:03:03 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <Optimize.h>
void  self_print_to_protect(to_protect *v5264)
{ GC_BIND;
  princ_string("[to_protect ");
  print_any(GC_OID(v5264->arg));
  princ_string("]");
  GC_UNBIND;} 

OID  self_eval_to_protect(to_protect *v5264)
{ GC_BIND;
  { OID Result = 0;
    Result = OPT_EVAL(v5264->arg);
    GC_UNBIND; return (Result);} 
  } 

void  self_print_to_CL(to_CL *v9268)
{ GC_BIND;
  princ_string("CL{");
  print_any(GC_OID(v9268->arg));
  princ_string("}:");
  print_any(_oid_(v9268->set_arg));
  princ_string("");
  GC_UNBIND;} 

ClaireType * c_type_to_CL_Optimize(to_CL *v9268)
{ GC_BIND;
  { ClaireType *Result ;
    Result = sort_abstract_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v9268->arg))));
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * c_gc_ask_to_CL(to_CL *v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((gcsafe_ask_class(v9268->set_arg) != CTRUE) ? ((((OBJECT(ClaireBoolean,(*Optimize.c_gc_ask)(GC_OID(v9268->arg)))) == CTRUE) || 
        (_inf_equalt_class(v9268->set_arg,Kernel._import) == CTRUE)) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 

void  self_print_to_C(to_C *v9268)
{ GC_BIND;
  princ_string("C{");
  print_any(GC_OID(v9268->arg));
  princ_string("}:");
  print_any(_oid_(v9268->set_arg));
  princ_string("");
  GC_UNBIND;} 

ClaireBoolean * c_gc_ask_to_C(to_C *v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((gcsafe_ask_class(v9268->set_arg) != CTRUE) ? (((OBJECT(ClaireBoolean,(*Optimize.c_gc_ask)(GC_OID(v9268->arg)))) == CTRUE) ? ((_inf_equalt_class(v9268->set_arg,Kernel._object) == CTRUE) ? CTRUE: CFALSE): CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_to_C_Optimize(to_C *v9268)
{ GC_BIND;
  { ClaireType *Result ;
    Result = glb_class(v9268->set_arg,GC_OBJECT(ClaireType,ptype_type(OBJECT(ClaireType,(*Optimize.c_type)(GC_OID(v9268->arg))))));
    GC_UNBIND; return (Result);} 
  } 

void  self_print_C_cast(C_cast *v9268)
{ GC_BIND;
  princ_string("<");
  print_any(GC_OID(v9268->arg));
  princ_string(":");
  print_any(_oid_(v9268->set_arg));
  princ_string(">}");
  GC_UNBIND;} 

ClaireBoolean * c_gc_ask_C_cast(C_cast *v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = OBJECT(ClaireBoolean,(*Optimize.c_gc_ask)(v9268->arg));
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_C_cast_Optimize(C_cast *v9268)
{ return (v9268->set_arg);} 

OID  c_code_C_cast_Optimize(C_cast *v9268,ClaireClass *v5259)
{ if (INHERIT(v5259,Kernel._object)) 
  { { OID Result = 0;
      { C_cast * v2072 = ((C_cast *) GC_OBJECT(C_cast,new_object_class(Optimize._C_cast)));
        (v2072->arg = (*Optimize.c_code)(GC_OID(v9268->arg),
          _oid_(v5259)));
        (v2072->set_arg = v9268->set_arg);
        add_I_property(Kernel.instances,Optimize._C_cast,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = (*Optimize.c_code)(GC_OID(v9268->arg),
        _oid_(v5259));
      GC_UNBIND; return (Result);} 
    } 
  } 

void  self_print_Pattern(ClairePattern *v9268)
{ GC_BIND;
  print_any(_oid_(v9268->selector));
  princ_string("[tuple(");
  princ_bag(GC_OBJECT(list,v9268->arg));
  princ_string(")]");
  GC_UNBIND;} 

ClaireBoolean * _Z_any3(OID v5264,ClairePattern *v5265)
{ GC_BIND;
  { ClaireBoolean *Result ;
    if (INHERIT(OWNER(v5264),Language._Call))
     { ClaireBoolean *v_and;
      { v_and = ((OBJECT(Call,v5264)->selector == v5265->selector) ? CTRUE : CFALSE);
        if (v_and == CFALSE) Result =CFALSE; 
        else { { list * v12260;
            { { bag *v_list; OID v_val;
                OID v5266,CLcount;
                v_list = GC_OBJECT(list,OBJECT(Call,v5264)->args);
                 v12260 = v_list->clone();
                for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                { v5266 = (*(v_list))[CLcount];
                  v_val = (*Optimize.c_type)(v5266);
                  
                  (*((list *) v12260))[CLcount] = v_val;} 
                } 
              GC_OBJECT(list,v12260);} 
            v_and = tmatch_ask_list(v12260,GC_OBJECT(list,v5265->arg));
            } 
          if (v_and == CFALSE) Result =CFALSE; 
          else Result = CTRUE;} 
        } 
      } 
    else Result = CFALSE;
      GC_UNBIND; return (Result);} 
  } 

ClaireType * glb_Pattern(ClairePattern *v5264,ClaireType *v5265)
{ return (Kernel.emptySet);} 

ClaireBoolean * less_ask_Pattern(ClairePattern *v5264,OID v5265)
{ { ClaireBoolean *Result ;
    if (INHERIT(OWNER(v5265),Optimize._Pattern))
     { ClaireBoolean *v_and;
      { v_and = ((v5264->selector == OBJECT(ClairePattern,v5265)->selector) ? CTRUE : CFALSE);
        if (v_and == CFALSE) Result =CFALSE; 
        else { v_and = ((v5264->arg->length == OBJECT(ClairePattern,v5265)->arg->length) ? CTRUE : CFALSE);
          if (v_and == CFALSE) Result =CFALSE; 
          else { { OID  v14182;
              { int  v5249 = 1;
                int  v6661 = v5264->arg->length;
                { OID gc_local;
                  v14182= _oid_(CFALSE);
                  while ((v5249 <= v6661))
                  { // HOHO, GC_LOOP not needed !
                    if (_equaltype_ask_any(OBJECT(ClaireType,(*(v5264->arg))[v5249]),OBJECT(ClaireType,(*(OBJECT(ClairePattern,v5265)->arg))[v5249])) != CTRUE)
                     { v14182 = Kernel.ctrue;
                      break;} 
                    ++v5249;
                    } 
                  } 
                } 
              v_and = not_any(v14182);
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    else Result = OBJECT(ClaireBoolean,(*Core._inf_equalt)(_oid_(Language._Call),
        v5265));
      return (Result);} 
  } 

ClaireBoolean * less_ask_any(OID v5264,ClairePattern *v5265)
{ { ClaireBoolean *Result ;
    if (INHERIT(OWNER(v5264),Optimize._Pattern))
     { ClaireBoolean *v_and;
      { v_and = ((OBJECT(ClairePattern,v5264)->selector == v5265->selector) ? CTRUE : CFALSE);
        if (v_and == CFALSE) Result =CFALSE; 
        else { v_and = ((OBJECT(ClairePattern,v5264)->arg->length == v5265->arg->length) ? CTRUE : CFALSE);
          if (v_and == CFALSE) Result =CFALSE; 
          else { { OID  v3517;
              { int  v5249 = 1;
                int  v6663 = OBJECT(ClairePattern,v5264)->arg->length;
                { OID gc_local;
                  v3517= _oid_(CFALSE);
                  while ((v5249 <= v6663))
                  { // HOHO, GC_LOOP not needed !
                    if (_equaltype_ask_any(OBJECT(ClaireType,(*(OBJECT(ClairePattern,v5264)->arg))[v5249]),OBJECT(ClaireType,(*(v5265->arg))[v5249])) != CTRUE)
                     { v3517 = Kernel.ctrue;
                      break;} 
                    ++v5249;
                    } 
                  } 
                } 
              v_and = not_any(v3517);
              } 
            if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    else Result = CFALSE;
      return (Result);} 
  } 

ClairePattern * nth_property(property *v5256,tuple *v5264)
{ GC_BIND;
  { ClairePattern *Result ;
    { ClairePattern * v2072 = ((ClairePattern *) GC_OBJECT(ClairePattern,new_object_class(Optimize._Pattern)));
      (v2072->selector = v5256);
      (v2072->arg = list_I_tuple(v5264));
      add_I_property(Kernel.instances,Optimize._Pattern,11,_oid_(v2072));
      Result = v2072;
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  warn_void()
{ if (Optimize.OPT->in_method != CNULL) 
  { { OID Result = 0;
      Result = tformat_string("---- WARNING[in ~S]: ",2,list::alloc(1,GC_OID(Optimize.OPT->in_method)));
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = tformat_string("---- WARNING: ",2,list::empty());
      GC_UNBIND; return (Result);} 
    } 
  } 

void  Cerror_string(char *v5259,listargs *v5252)
{ GC_BIND;
  princ_string("---- Compiler Error[in ");
  print_any(GC_OID(Optimize.OPT->in_method));
  princ_string("]:\n");
  princ_string("---- file read up to line ");
  princ_integer(Reader.reader->nb_line);
  princ_string("\n");
  close_exception(((general_error *) (*Core._general_error)(_string_(v5259),
    _oid_(v5252))));
  GC_UNBIND;} 

OID  c_warn_Call(Call *v9268,OID v15607)
{ GC_BIND;
  { OID Result = 0;
    { property * v5259 = v9268->selector;
      if (v15607 == _oid_(Kernel._void))
       (*Optimize.Cerror)(_string_("[205] message ~S sent to void object"),
        _oid_(v9268));
      else if ((boolean_I_any(_oid_(v5259->restrictions)) != CTRUE) && 
          (contain_ask_set(Optimize.OPT->ignore,_oid_(v5259)) != CTRUE))
       { warn_void();
        tformat_string("the property ~S is undefined.\n",2,list::alloc(1,_oid_(v5259)));
        } 
      else if ((contain_ask_set(Optimize.OPT->ignore,_oid_(v5259)) != CTRUE) && 
          (((v5259->open <= 1) || 
              (v5259->open == 4)) && 
            ((INHERIT(OWNER(v15607),Kernel._list)) && (class_I_type(OBJECT(ClaireType,(*(OBJECT(bag,v15607)))[1]))->open != 3))))
       { warn_void();
        tformat_string("wrongly typed message ~S [~S]\n",2,list::alloc(2,_oid_(v9268),v15607));
        } 
      Result = _oid_(open_message_property(v9268->selector,GC_OBJECT(list,v9268->args)));
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_warn_Super(Super *v9268,OID v15607)
{ GC_BIND;
  { OID Result = 0;
    { property * v5259 = v9268->selector;
      if (v15607 == _oid_(Kernel._void))
       (*Optimize.Cerror)(_string_("[205] message ~S sent to void object"),
        _oid_(v9268));
      else if (boolean_I_any(_oid_(v5259->restrictions)) != CTRUE)
       { warn_void();
        tformat_string("the property ~S is undefined.\n",2,list::alloc(1,_oid_(v5259)));
        } 
      else if ((contain_ask_set(Optimize.OPT->ignore,_oid_(v5259)) != CTRUE) && 
          (v5259->open <= 1))
       ;{ Call * v5253 = GC_OBJECT(Call,open_message_property(v9268->selector,GC_OBJECT(list,v9268->args)));
        Super * v2072 = ((Super *) GC_OBJECT(Super,new_object_class(Language._Super)));
        (v2072->selector = v5253->selector);
        (v2072->cast_to = v9268->cast_to);
        (v2072->args = v5253->args);
        add_I_property(Kernel.instances,Language._Super,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_warn_property(property *v9268,list *v5252,list *v15607)
{ if ((v9268->open <= 1) && 
      ((contain_ask_set(Optimize.OPT->ignore,_oid_(v9268)) != CTRUE) && 
        (Optimize.compiler->safety > 1)))
   ;return (_oid_(open_message_property(v9268,v5252)));} 

OID  c_warn_Variable(Variable *v9268,OID v5264,ClaireType *v5265)
{ GC_BIND;
  if (boolean_I_any((*Kernel._exp)(_oid_(v5265),
    GC_OID(_oid_(v9268->range)))) != CTRUE)
   { if (Optimize.compiler->safety > 4)
     { warn_void();
      tformat_string("~S of type ~S is put in the variable ~S:~S\n",2,list::alloc(4,v5264,
        _oid_(v5265),
        _oid_(v9268),
        GC_OID(_oid_(v9268->range))));
      } 
    else (*Optimize.Cerror)(_string_("[212] the value ~S of type ~S cannot be placed in the variable ~S:~S"),
        v5264,
        _oid_(v5265),
        _oid_(v9268),
        GC_OID(_oid_(v9268->range)));
      } 
  else if ((Optimize.compiler->safety <= 1) || 
      (boolean_I_any(sort_equal_class(osort_any(GC_OID(_oid_(v9268->range))),osort_any(_oid_(v5265)))) != CTRUE))
   { warn_void();
    tformat_string("~S of type ~S is put in the variable ~S:~S\n",2,list::alloc(4,v5264,
      _oid_(v5265),
      _oid_(v9268),
      GC_OID(_oid_(v9268->range))));
    } 
  { OID Result = 0;
    if ((Optimize.compiler->safety <= 1) && 
        (_inf_equal_type(v5265,GC_OBJECT(ClaireType,v9268->range)) != CTRUE))
     Result = c_check_any(GC_OID((*Optimize.c_code)(v5264,
      _oid_(Kernel._any))),GC_OID((*Optimize.c_code)(GC_OID(_oid_(v9268->range)),
      _oid_(Kernel._type))));
    else Result = v5264;
      GC_UNBIND; return (Result);} 
  } 

OID  sort_equal_class(ClaireClass *v5243,ClaireClass *v15127)
{ { OID Result = 0;
    if (INHERIT(v5243,Kernel._object))
     Result = _oid_(inherit_ask_class(v15127,Kernel._object));
    else Result = _oid_(((v5243 == v15127) ? CTRUE : ((((Optimize.compiler->overflow_ask != CTRUE) && 
            ((v5243 == Kernel._any) && 
                (v15127 == Kernel._integer))) || 
          ((v5243 == Kernel._integer) && 
              (v15127 == Kernel._any))) ? CTRUE : CFALSE)));
      return (Result);} 
  } 

ClaireClass * psort_any(OID v5264)
{ { ClaireClass *Result ;
    { ClaireClass * v5243 = class_I_type(OBJECT(ClaireType,v5264));
      Result = ((INHERIT(v5243,Kernel._object)) ?
        v5243 :
        ((v5243 == Kernel._float) ?
          v5243 :
          sort_I_class(v5243) ) );
      } 
    return (Result);} 
  } 

ClaireClass * osort_any(OID v5264)
{ { ClaireClass *Result ;
    { ClaireClass * v5243 = class_I_type(OBJECT(ClaireType,v5264));
      Result = ((v5243 == Kernel._float) ?
        v5243 :
        sort_I_class(v5243) );
      } 
    return (Result);} 
  } 

ClaireClass * sort_Variable(Variable *v5264)
{ GC_BIND;
  { ClaireClass *Result ;
    { ClaireType * v5258 = v5264->range;
      Result = (((INHERIT(v5258->isa,Core._Union)) && (equal(_oid_(CLREAD(Union,v5258,t1)),_oid_(Kernel.emptySet)) == CTRUE)) ?
        psort_any(GC_OID(_oid_(CLREAD(Union,CLREAD(Union,v5258,t2),t2)))) :
        psort_any(_oid_(v5258)) );
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireClass * stupid_t_any1(OID v9268)
{ GC_BIND;
  { ClaireClass *Result ;
    if (INHERIT(OWNER(v9268),Language._Variable))
     { ClaireType * v5258 = GC_OBJECT(ClaireType,OBJECT(Variable,v9268)->range);
      Result = ((sort_abstract_ask_type(v5258) == CTRUE) ?
        Kernel._any :
        (((INHERIT(v5258->isa,Core._Union)) && (equal(_oid_(CLREAD(Union,v5258,t1)),_oid_(Kernel.emptySet)) == CTRUE)) ?
          OBJECT(ClaireClass,(*Core.t1)(GC_OID(_oid_(CLREAD(Union,v5258,t2))))) :
          class_I_type(v5258) ) );
      } 
    else if (INHERIT(OWNER(v9268),Core._global_variable))
     { ClaireType * v5258 = OBJECT(global_variable,v9268)->range;
      Result = ((boolean_I_any(_oid_(v5258)) == CTRUE) ?
        class_I_type(v5258) :
        OWNER(OBJECT(global_variable,v9268)->value) );
      } 
    else if (INHERIT(OWNER(v9268),Language._And))
     Result = Kernel._boolean;
    else if (INHERIT(OWNER(v9268),Kernel._bag))
     Result = OWNER(v9268);
    else if (INHERIT(OWNER(v9268),Kernel._environment))
     Result = Kernel._environment;
    else if (INHERIT(OWNER(v9268),Kernel._class))
     Result = Kernel._class;
    else if (INHERIT(OWNER(v9268),Language._Call_slot))
     { slot * v5259 = OBJECT(Call_slot,v9268)->selector;
      property * v5256 = v5259->selector;
      { ITERATE(v15623);
        for (START(v5256->definition); NEXT(v15623);)
        if (Kernel._slot == OBJECT(ClaireObject,v15623)->isa)
         { if (_inf_equal_type(domain_I_restriction(v5259),domain_I_restriction(OBJECT(restriction,v15623))) == CTRUE)
           v5259= OBJECT(slot,v15623);
          } 
        } 
      Result = class_I_type(v5259->range);
      } 
    else if (INHERIT(OWNER(v9268),Language._Call_method))
     Result = class_I_type(OBJECT(Call_method,v9268)->arg->range);
    else if (INHERIT(OWNER(v9268),Language._Call))
     Result = selector_psort_Call(OBJECT(Call,v9268));
    else if (INHERIT(OWNER(v9268),Optimize._to_C))
     Result = OBJECT(to_C,v9268)->set_arg;
    else if (INHERIT(OWNER(v9268),Optimize._to_protect))
     Result = stupid_t_any1(GC_OID(OBJECT(to_protect,v9268)->arg));
    else if (INHERIT(OWNER(v9268),Kernel._symbol))
     Result = OWNER(v9268);
    else if (INHERIT(OWNER(v9268),Kernel._char))
     Result = OWNER(v9268);
    else if (Kernel._boolean == OWNER(v9268))
     Result = OWNER(v9268);
    else if (INHERIT(OWNER(v9268),Kernel._primitive))
     Result = OWNER(v9268);
    else if (INHERIT(OWNER(v9268),Language._Assign))
     Result = stupid_t_any1(GC_OID(OBJECT(Assign,v9268)->arg));
    else if (INHERIT(OWNER(v9268),Language._Let))
     Result = stupid_t_any1(GC_OID(OBJECT(Let,v9268)->arg));
    else if (INHERIT(OWNER(v9268),Language._Do))
     Result = stupid_t_any1(GC_OID(last_list(OBJECT(Do,v9268)->args)));
    else if (INHERIT(OWNER(v9268),Language._If))
     Result = meet_class(stupid_t_any1(GC_OID(OBJECT(If,v9268)->arg)),stupid_t_any1(GC_OID(OBJECT(If,v9268)->other)));
    else if (INHERIT(OWNER(v9268),Language._Collect))
     Result = Kernel._list;
    else if (INHERIT(OWNER(v9268),Language._Image))
     Result = Kernel._set;
    else if (INHERIT(OWNER(v9268),Language._Or))
     Result = Kernel._boolean;
    else if (INHERIT(OWNER(v9268),Language._Select))
     Result = Kernel._set;
    else if (INHERIT(OWNER(v9268),Language._Lselect))
     Result = Kernel._list;
    else if (INHERIT(OWNER(v9268),Language._List))
     Result = Kernel._list;
    else if (INHERIT(OWNER(v9268),Language._Set))
     Result = Kernel._set;
    else Result = ((INHERIT(OWNER(v9268),Kernel._thing)) ?
      OWNER(v9268) :
      ((INHERIT(OWNER(v9268),Language._Tuple)) ?
        Kernel._tuple :
        ((INHERIT(OWNER(v9268),Language._Exists)) ?
          ((OBJECT(Exists,v9268)->other == CNULL) ?
            Kernel._any :
            Kernel._boolean ) :
          ((INHERIT(OWNER(v9268),Kernel._integer)) ?
            Kernel._integer :
            Kernel._any ) ) ) );
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * stupid_t_any2(OID v9268,OID v5264)
{ { ClaireBoolean *Result ;
    { ClaireClass * v15126 = stupid_t_any1(v9268);
      ClaireClass * v15127 = stupid_t_any1(v5264);
      Result = ((v15126 != Kernel._any) ? ((v15126 == v15127) ? CTRUE: CFALSE): CFALSE);
      } 
    return (Result);} 
  } 

ClaireBoolean * extended_ask_type(ClaireType *v9268)
{ { ClaireBoolean *Result ;
    Result = ((INHERIT(v9268->isa,Core._Union)) ?
      ((Kernel._set == CLREAD(Union,v9268,t2)->isa) ? ((boolean_I_any(_oid_(CLREAD(Union,v9268,t2))) == CTRUE) ? (((*(((bag *) CLREAD(Union,v9268,t2))))[1] == CNULL) ? CTRUE: CFALSE): CFALSE): CFALSE) :
      CFALSE );
    return (Result);} 
  } 

ClaireType * extends_type(ClaireType *v5264)
{ GC_BIND;
  { ClaireType *Result ;
    { optUnion * v2072 = ((optUnion *) GC_OBJECT(optUnion,new_object_class(Optimize._optUnion)));
      (v2072->t1 = v5264);
      (v2072->t2 = set::alloc(Kernel.emptySet,1,CNULL));
      Result = v2072;
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * sort_abstract_I_type(ClaireType *v5264)
{ if (((_oid_((INHERIT(v5264->isa,Kernel._class) ? (ClaireObject *) sort_I_class((ClaireClass *) OBJECT(ClaireClass,_oid_(v5264))) :  (ClaireObject *)  sort_I_type((ClaireType *) OBJECT(ClaireType,_oid_(v5264))))) != _oid_(Kernel._any)) && 
        ((_oid_((INHERIT(v5264->isa,Kernel._class) ? (ClaireObject *) sort_I_class((ClaireClass *) OBJECT(ClaireClass,_oid_(v5264))) :  (ClaireObject *)  sort_I_type((ClaireType *) OBJECT(ClaireType,_oid_(v5264))))) != _oid_(Kernel._integer)) || 
            (Optimize.compiler->overflow_ask == CTRUE))) || 
      (v5264 == Kernel._float)) 
  { { ClaireType *Result ;
      { Union * v2072 = ((Union *) GC_OBJECT(Union,new_object_class(Core._Union)));
        (v2072->t1 = Kernel._any);
        (v2072->t2 = v5264);
        Result = v2072;
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireType *Result ;
      Result = v5264;
      GC_UNBIND; return (Result);} 
    } 
  } 

ClaireBoolean * sort_abstract_ask_type(ClaireType *v5264)
{ { ClaireBoolean *Result ;
    Result = ((INHERIT(v5264->isa,Core._Union)) ?
      equal(_oid_(CLREAD(Union,v5264,t1)),_oid_(Kernel._any)) :
      CFALSE );
    return (Result);} 
  } 

ClaireType * ptype_type(ClaireType *v5264)
{ { ClaireType *Result ;
    Result = ((INHERIT(v5264->isa,Core._Union)) ?
      ((CLREAD(Union,v5264,t1) == Kernel._any) ?
        CLREAD(Union,v5264,t2) :
        v5264 ) :
      v5264 );
    return (Result);} 
  } 

ClaireType * pmember_type(ClaireType *v5264)
{ return (member_type(ptype_type(v5264)));} 

OID  enumerate_code_any(OID v9268,ClaireType *v13271)
{ if (_inf_equal_type(ptype_type(v13271),Kernel._bag) == CTRUE) 
  { { OID Result = 0;
      Result = c_strict_code_any(v9268,Kernel._bag);
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = c_code_method_method1(GC_OBJECT(method,((method *) _at_property1(Core.enumerate,Kernel._any))),list::alloc(1,v9268),list::alloc(1,_oid_(v13271)));
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  range_infers_for_Variable(Variable *v9268,ClaireType *v5265,ClaireType *v15719)
{ GC_BIND;
  if (v9268->range == (NULL))
   { if (INHERIT(v5265->isa,Core._Interval))
     v5265= Kernel._integer;
    (v9268->range = v5265);
    } 
  else if ((_inf_equal_type(v5265,GC_OBJECT(ClaireType,v9268->range)) != CTRUE) && 
      (Optimize.compiler->safety <= 1))
   { if ((boolean_I_any(_oid_(v5265)) != CTRUE) || 
        (boolean_I_any(_oid_(v9268->range)) != CTRUE))
     { warn_void();
      tformat_string("range of variable in ~S is wrong.\n",2,list::alloc(1,_oid_(v9268)));
      } 
    } 
  { OID Result = 0;
    if ((sort_Variable(v9268) != Kernel._any) && 
        (((sort_Variable(v9268) != Kernel._integer) || 
            (Optimize.compiler->overflow_ask == CTRUE)) && 
          ((_inf_equal_type(v15719,Kernel._array) != CTRUE) || 
              (_inf_equal_type(v5265,Kernel._float) != CTRUE))))
     { Result = _void_((v9268->range = sort_abstract_I_type(GC_OBJECT(ClaireType,v9268->range))));
      } 
    else Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
  } 

OID  range_infers_Variable(Variable *v9268,ClaireType *v5265)
{ gc_register_Variable(v9268);
  { OID Result = 0;
    if ((v9268->range == (NULL)) || 
        ((extended_ask_type(v9268->range) == CTRUE) && 
            (INHERIT(v9268->range->isa,Optimize._optUnion))))
     { if (Kernel._set == v5265->isa)
       Result = _void_((v9268->range = class_I_type(v5265)));
      else Result = _void_((v9268->range = v5265));
        } 
    else Result = Kernel.cfalse;
      return (Result);} 
  } 

void  range_infer_case_any(OID v9268,ClaireType *v5265)
{ GC_BIND;
  if (INHERIT(OWNER(v9268),Language._Variable))
   { if (boolean_I_any(sort_equal_class(osort_any(GC_OID(_oid_(OBJECT(Variable,v9268)->range))),osort_any(_oid_(v5265)))) == CTRUE)
     { ClaireClass * v15126 = psort_any(_oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(Variable,v9268)->range))));
      if (v15126 != psort_any(_oid_(class_I_type(v5265))))
       { Variable * v6685; 
        ClaireType * v6686;
        v6685 = OBJECT(Variable,v9268);
        { Union * v2072 = ((Union *) GC_OBJECT(Union,new_object_class(Core._Union)));
          (v2072->t1 = Kernel.emptySet);
          { Union * v6687; 
            ClaireType * v6688;
            v6687 = v2072;
            { Union * v2072 = ((Union *) GC_OBJECT(Union,new_object_class(Core._Union)));
              (v2072->t1 = v15126);
              (v2072->t2 = v5265);
              v6688 = v2072;
              } 
            (v6687->t2 = v6688);} 
          v6686 = v2072;
          } 
        (v6685->range = v6686);} 
      else (OBJECT(Variable,v9268)->range = v5265);
        } 
    else if (osort_any(GC_OID(_oid_(OBJECT(Variable,v9268)->range))) == Kernel._any)
     (OBJECT(Variable,v9268)->range = sort_abstract_I_type(v5265));
    } 
  GC_UNBIND;} 

OID  c_check_any(OID v5264,OID v5265)
{ GC_BIND;
  { OID Result = 0;
    { method * v5253 = ((method *) _at_property1(Core.check_in,Kernel._any));
      if (Optimize.compiler->safety > 3)
       Result = v5264;
      else { legal_ask_module(v5253->module_I,_oid_(v5253));
          { Call_method2 * v2072 = ((Call_method2 *) GC_OBJECT(Call_method2,new_object_class(Language._Call_method2)));
            (v2072->arg = v5253);
            (v2072->args = list::alloc(2,GC_OID(c_gc_I_any1(v5264)),GC_OID(c_gc_I_any1(v5265))));
            add_I_property(Kernel.instances,Language._Call_method2,11,_oid_(v2072));
            Result = _oid_(v2072);
            } 
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

void  range_sets_any(OID v9268,ClaireType *v5265)
{ GC_BIND;
  if (INHERIT(OWNER(v9268),Language._Variable))
   { if (boolean_I_any(sort_equal_class(osort_any(GC_OID(_oid_(OBJECT(Variable,v9268)->range))),osort_any(_oid_(v5265)))) == CTRUE)
     { ClaireClass * v15126 = psort_any(_oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(Variable,v9268)->range))));
      if (v15126 != psort_any(_oid_(class_I_type(v5265))))
       { Variable * v6689; 
        ClaireType * v6690;
        v6689 = OBJECT(Variable,v9268);
        { Union * v2072 = ((Union *) GC_OBJECT(Union,new_object_class(Core._Union)));
          (v2072->t1 = Kernel.emptySet);
          { Union * v6691; 
            ClaireType * v6693;
            v6691 = v2072;
            { Union * v2072 = ((Union *) GC_OBJECT(Union,new_object_class(Core._Union)));
              (v2072->t1 = v15126);
              (v2072->t2 = v5265);
              v6693 = v2072;
              } 
            (v6691->t2 = v6693);} 
          v6690 = v2072;
          } 
        (v6689->range = v6690);} 
      else (OBJECT(Variable,v9268)->range = v5265);
        } 
    else if (osort_any(GC_OID(_oid_(OBJECT(Variable,v9268)->range))) == Kernel._any)
     (OBJECT(Variable,v9268)->range = sort_abstract_I_type(v5265));
    } 
  GC_UNBIND;} 

ClaireClass * c_srange_method(method *v5253)
{ { ClaireClass *Result ;
    Result = ((v5253->range == Kernel._float) ?
      Kernel._float :
      OBJECT(ClaireClass,last_list(v5253->srange)) );
    return (Result);} 
  } 

OID  c_code_Type_Optimize(Type *v9268,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    Result = (*Optimize.c_code)(GC_OID((*Optimize.self_code)(_oid_(v9268))),
      _oid_(v5259));
    GC_UNBIND; return (Result);} 
  } 

OID  self_code_subtype(subtype *v9268)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Kernel.nth);
      (v2072->args = list::alloc(2,_oid_(v9268->arg),GC_OID((*Optimize.c_code)(GC_OID(_oid_(v9268->t1)),
        _oid_(Kernel._type)))));
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  self_code_Param(Param *v9268)
{ if ((v9268->params->length == 1) && 
      (((*(v9268->params))[1] == _oid_(Kernel.of)) && 
        (Kernel._set == OWNER((*(v9268->args))[1])))) 
  { { OID Result = 0;
      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (v2072->selector = Core.param_I);
        (v2072->args = list::alloc(2,_oid_(v9268->arg),GC_OID((*Optimize.c_code)(GC_OID((*Kernel.nth)((*(v9268->args))[1],
            1)),
          _oid_(Kernel._type)))));
        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (v2072->selector = Kernel.nth);
        { Call * v6694; 
          list * v6715;
          v6694 = v2072;
          { OID v_bag;
            GC_ANY(v6715= list::empty(Kernel.emptySet));
            ((list *) v6715)->addFast(_oid_(v9268->arg));
            ((list *) v6715)->addFast(GC_OID(_oid_(v9268->params)));
            { { list * v8343;{ bag *v_list; OID v_val;
                  OID v5265,CLcount;
                  v_list = GC_OBJECT(list,v9268->args);
                   v8343 = v_list->clone();
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { v5265 = (*(v_list))[CLcount];
                    v_val = (*Optimize.c_code)(v5265,
                      _oid_(Kernel._type));
                    
                    (*((list *) v8343))[CLcount] = v_val;} 
                  } 
                
                v_bag=_oid_(v8343);} 
              GC_OID(v_bag);} 
            ((list *) v6715)->addFast(v_bag);} 
          (v6694->args = v6715);} 
        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  self_code_Union(Union *v9268)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Core.U);
      (v2072->args = list::alloc(2,GC_OID((*Optimize.c_code)(GC_OID(_oid_(v9268->t1)),
        _oid_(Kernel._type))),GC_OID((*Optimize.c_code)(GC_OID(_oid_(v9268->t2)),
        _oid_(Kernel._type)))));
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  self_code_Interval(Interval *v9268)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Kernel._dot_dot);
      (v2072->args = list::alloc(2,v9268->arg1,v9268->arg2));
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  self_code_Reference(Reference *v9268)
{ GC_BIND;
  { OID Result = 0;
    { Definition * v2072 = ((Definition *) GC_OBJECT(Definition,new_object_class(Language._Definition)));
      (v2072->arg = Core._Reference);
      { Definition * v6717; 
        list * v6719;
        v6717 = v2072;
        { OID v_bag;
          GC_ANY(v6719= list::empty(Kernel.emptySet));
          { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (v2072->selector = Kernel._equal);
              (v2072->args = list::alloc(2,_oid_(Core.args),GC_OID(_oid_(v9268->args))));
              add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
              v_bag = _oid_(v2072);
              } 
            GC_OID(v_bag);} 
          ((list *) v6719)->addFast(v_bag);
          { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (v2072->selector = Kernel._equal);
              (v2072->args = list::alloc(2,_oid_(Kernel.index),v9268->index));
              add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
              v_bag = _oid_(v2072);
              } 
            GC_OID(v_bag);} 
          ((list *) v6719)->addFast(v_bag);
          { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (v2072->selector = Kernel._equal);
              (v2072->args = list::alloc(2,_oid_(Kernel.arg),_oid_(v9268->arg)));
              add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
              v_bag = _oid_(v2072);
              } 
            GC_OID(v_bag);} 
          ((list *) v6719)->addFast(v_bag);} 
        (v6717->args = v6719);} 
      add_I_property(Kernel.instances,Language._Definition,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  self_code_Pattern(ClairePattern *v9268)
{ if (Optimize.compiler->inline_ask == CTRUE) 
  { { OID Result = 0;
      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (v2072->selector = Kernel.nth);
        { Call * v6720; 
          list * v6721;
          v6720 = v2072;
          { OID v_bag;
            GC_ANY(v6721= list::empty(Kernel.emptySet));
            ((list *) v6721)->addFast(_oid_(v9268->selector));
            { { Tuple * v2072 = ((Tuple *) GC_OBJECT(Tuple,new_object_class(Language._Tuple)));
                (v2072->args = v9268->arg);
                add_I_property(Kernel.instances,Language._Tuple,11,_oid_(v2072));
                v_bag = _oid_(v2072);
                } 
              GC_OID(v_bag);} 
            ((list *) v6721)->addFast(v_bag);} 
          (v6720->args = v6721);} 
        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = _oid_(Language._Call);
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  member_code_class2(ClaireClass *v9268,OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2041;
      { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = ((_inf_equal_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v5264))),Kernel._object) == CTRUE) ?
            Kernel.isa :
            Core.owner ));
          (v2072->args = list::alloc(1,v5264));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          v2041 = v2072;
          } 
        GC_OBJECT(Call,v2041);} 
      if (((v9268->open <= -1) || 
            (v9268->open == 1)) && 
          (boolean_I_any(_oid_(v9268->subclass)) != CTRUE))
       { OID  v6305;
        { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = Kernel._equal);
          (v2072->args = list::alloc(2,_oid_(v9268),_oid_(v2041)));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          v6305 = _oid_(v2072);
          } 
        Result = (*Optimize.c_code)(v6305);
        } 
      else { OID  v7267;
          { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Core.inherit_ask);
            (v2072->args = list::alloc(2,_oid_(v2041),_oid_(v9268)));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v7267 = _oid_(v2072);
            } 
          Result = (*Optimize.c_code)(v7267);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  member_code_Type(Type *v9268,OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { Call_method2 * v2072 = ((Call_method2 *) GC_OBJECT(Call_method2,new_object_class(Language._Call_method2)));
      update_property(Kernel.arg,
        v2072,
        2,
        Kernel._object,
        GC_OID(_oid_(_at_property2(Kernel._Z,list::alloc(2,_oid_(Kernel._any),_oid_(Kernel._any))))));
      (v2072->args = list::alloc(2,GC_OID((*Optimize.c_code)(v5264,
        _oid_(Kernel._any))),GC_OID((*Optimize.c_code)(_oid_(v9268),
        _oid_(Kernel._any)))));
      add_I_property(Kernel.instances,Language._Call_method2,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  member_code_Union(Union *v9268,OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { Or * v2072 = ((Or *) GC_OBJECT(Or,new_object_class(Language._Or)));
      (v2072->args = list::alloc(2,GC_OID((*Optimize.member_code)(GC_OID(_oid_(v9268->t1)),
        v5264)),GC_OID((*Optimize.member_code)(GC_OID(_oid_(v9268->t2)),
        v5264))));
      add_I_property(Kernel.instances,Language._Or,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  member_code_Interval(Interval *v9268,OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { OID  v8227;
      { And * v2072 = ((And *) GC_OBJECT(And,new_object_class(Language._And)));
        { And * v6725; 
          list * v6746;
          v6725 = v2072;
          { OID v_bag;
            GC_ANY(v6746= list::empty(Kernel.emptySet));
            { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel._sup_equal);
                (v2072->args = list::alloc(2,v5264,v9268->arg1));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v_bag = _oid_(v2072);
                } 
              GC_OID(v_bag);} 
            ((list *) v6746)->addFast(v_bag);
            { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel._inf_equal);
                (v2072->args = list::alloc(2,v5264,v9268->arg2));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v_bag = _oid_(v2072);
                } 
              GC_OID(v_bag);} 
            ((list *) v6746)->addFast(v_bag);} 
          (v6725->args = v6746);} 
        add_I_property(Kernel.instances,Language._And,11,_oid_(v2072));
        v8227 = _oid_(v2072);
        } 
      Result = (*Optimize.c_code)(v8227,
        _oid_(Kernel._any));
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  member_code_Param(Param *v9268,OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { OID  v15868;
      { And * v2072 = ((And *) GC_OBJECT(And,new_object_class(Language._And)));
        { And * v6749; 
          list * v6750;
          v6749 = v2072;
          { list * v2367;
            { OID v_bag;
              GC_ANY(v2367= list::empty(Kernel.emptySet));
              { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel._Z);
                  (v2072->args = list::alloc(2,v5264,_oid_(v9268->arg)));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                GC_OID(v_bag);} 
              ((list *) v2367)->addFast(v_bag);} 
            list * v3328;
            { list * v5962 = list::empty(Kernel.emptySet);
              { int  v5249 = 1;
                int  v6747 = v9268->params->length;
                { OID gc_local;
                  while ((v5249 <= v6747))
                  { GC_LOOP;
                    { OID  v4290;
                      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Kernel._Z);
                        { Call * v6754; 
                          list * v6755;
                          v6754 = v2072;
                          { OID v_bag;
                            GC_ANY(v6755= list::empty(Kernel.emptySet));
                            { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                                update_property(Kernel.selector,
                                  v2072,
                                  2,
                                  Kernel._object,
                                  (*(v9268->params))[v5249]);
                                (v2072->args = list::alloc(1,v5264));
                                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                                v_bag = _oid_(v2072);
                                } 
                              GC_OID(v_bag);} 
                            ((list *) v6755)->addFast(v_bag);
                            ((list *) v6755)->addFast((*(v9268->args))[v5249]);} 
                          (v6754->args = v6755);} 
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v4290 = _oid_(v2072);
                        } 
                      v5962->addFast(v4290);
                      } 
                    ++v5249;
                    GC_UNLOOP;} 
                  } 
                } 
              v3328 = GC_OBJECT(list,v5962);
              } 
            v6750 = append_list(v2367,v3328);
            } 
          (v6749->args = v6750);} 
        add_I_property(Kernel.instances,Language._And,11,_oid_(v2072));
        v15868 = _oid_(v2072);
        } 
      Result = (*Optimize.c_code)(v15868,
        _oid_(Kernel._any));
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  member_code_any(OID v9268,OID v5264)
{ GC_BIND;
  (Language.LDEF->value= Core.nil->value);
  { OID Result = 0;
    { list * v15607 = list::alloc(2,GC_OID((*Optimize.c_type)(v5264)),GC_OID((*Optimize.c_type)(v9268)));
      OID  v5258 = GC_OID(extract_pattern_any(v9268,Kernel.nil));
      { ClaireBoolean * g0060I;
        { ClaireBoolean *v_or;
          { v_or = ((v5258 == CNULL) ? CTRUE : CFALSE);
            if (v_or == CTRUE) g0060I =CTRUE; 
            else { v_or = ((v9268 == _oid_(Kernel._object)) ? CTRUE : CFALSE);
              if (v_or == CTRUE) g0060I =CTRUE; 
              else { { OID  v11930;
                  if (INHERIT(OWNER(v9268),Core._global_variable))
                   v11930 = _oid_(OBJECT(global_variable,v9268)->range);
                  else v11930 = Kernel.cfalse;
                    v_or = boolean_I_any(v11930);
                  } 
                if (v_or == CTRUE) g0060I =CTRUE; 
                else g0060I = CFALSE;} 
              } 
            } 
          } 
        
        if (g0060I == CTRUE) Result = c_code_method_method1(GC_OBJECT(method,((method *) _at_property2(Kernel._Z,v15607))),list::alloc(2,v5264,v9268),v15607);
          else Result = (*Optimize.member_code)(v5258,
          v5264);
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * _Z_any4(OID v5264,OID v5265)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*Kernel._inf_equal)(v5264,
      GC_OID(OPT_EVAL((*(OBJECT(Call,v5265)->args))[2]))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*Kernel._inf_equal)(GC_OID(OPT_EVAL((*(OBJECT(Call,v5265)->args))[1])),
      v5264))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * _Z_any5(OID v5264,OID v5265)
{ return (((belong_to(v5264,OPT_EVAL((*(OBJECT(Call,v5265)->args))[1])) == CTRUE) ? ((equal(v5264,OPT_EVAL((*(OBJECT(Call,v5265)->args))[2])) != CTRUE) ? CTRUE: CFALSE): CFALSE));} 

ClaireBoolean * gcsafe_ask_class(ClaireClass *v9268)
{ return (((v9268 == Kernel._void) ? CTRUE : 
  ((INHERIT(v9268,Kernel._thing)) ? CTRUE : 
  ((INHERIT(v9268,Kernel._class)) ? CTRUE : 
  (((v9268 != Kernel._object) && 
      ((INHERIT(v9268,Kernel._object)) && 
        ((v9268->open < 3) && 
          ((!INHERIT(v9268,Kernel._collection)) && 
            (v9268 != Core._lambda))))) ? CTRUE : 
  ((v9268 == Kernel._integer) ? CTRUE : 
  ((v9268 == Kernel._char) ? CTRUE : 
  ((INHERIT(v9268,Kernel._boolean)) ? CTRUE : 
  ((v9268 == Kernel._function) ? CTRUE : 
  CFALSE)))))))));} 

ClaireBoolean * gcsafe_ask_type(ClaireType *v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((equal(_oid_(v9268),_oid_(Kernel.emptySet)) == CTRUE) ?
      CTRUE :
      ((INHERIT(v9268->isa,Core._Union)) ?
        (((OBJECT(ClaireBoolean,(*Optimize.gcsafe_ask)(GC_OID(_oid_(CLREAD(Union,v9268,t1)))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*Optimize.gcsafe_ask)(GC_OID(_oid_(CLREAD(Union,v9268,t2)))))) == CTRUE) ? CTRUE: CFALSE): CFALSE) :
        gcsafe_ask_class(class_I_type(v9268)) ) );
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * gcsafe_ask_property(property *v9268)
{ { ClaireBoolean *Result ;
    { OID  v12891;
      { ITERATE(v5264);
        v12891= _oid_(CFALSE);
        for (START(v9268->restrictions); NEXT(v5264);)
        if (not_any(_oid_((INHERIT(v9268->range->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(v9268->range))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(v9268->range)))))) == CTRUE)
         { v12891 = Kernel.ctrue;
          break;} 
        } 
      Result = not_any(v12891);
      } 
    return (Result);} 
  } 

OID  c_gc_I_any1(OID v9268)
{ if ((Optimize.OPT->online_ask != CTRUE) && 
      ((OBJECT(ClaireBoolean,(*Optimize.c_gc_ask)(v9268))) == CTRUE)) 
  { { OID Result = 0;
      { (Optimize.OPT->protection = CTRUE);
        { to_protect * v2072 = ((to_protect *) GC_OBJECT(to_protect,new_object_class(Optimize._to_protect)));
          (v2072->arg = v9268);
          add_I_property(Kernel.instances,Optimize._to_protect,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = v9268;
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  c_gc_I_any2(OID v9268,ClaireType *v5260)
{ if ((Optimize.OPT->online_ask != CTRUE) && 
      ((((OBJECT(ClaireBoolean,(*Optimize.c_gc_ask)(v9268))) == CTRUE) || 
          ((INHERIT(OWNER(v9268),Language._Let)) && 
              (not_any((*Optimize.gcsafe_ask)(GC_OID((*Optimize.c_type)(v9268)))) == CTRUE))) && 
        (not_any(_oid_((INHERIT(v5260->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(v5260))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(v5260)))))) == CTRUE))) 
  { { OID Result = 0;
      { (Optimize.OPT->protection = CTRUE);
        { to_protect * v2072 = ((to_protect *) GC_OBJECT(to_protect,new_object_class(Optimize._to_protect)));
          (v2072->arg = v9268);
          add_I_property(Kernel.instances,Optimize._to_protect,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = v9268;
      GC_UNBIND; return (Result);} 
    } 
  } 

ClaireBoolean * need_protect_any(OID v5264)
{ { ClaireBoolean *Result ;
    Result = ((INHERIT(OWNER(v5264),Language._Call_slot)) ?
      Optimize.OPT->use_update :
      ((INHERIT(OWNER(v5264),Language._Call_method2)) ?
        ((Optimize.OPT->use_nth_equal == CTRUE) ? CTRUE : ((OBJECT(Call_method,v5264)->arg->selector != Kernel.nth) ? CTRUE : ((domain_I_restriction(OBJECT(Call_method,v5264)->arg) == Kernel._class) ? CTRUE : CFALSE))) :
        CTRUE ) );
    return (Result);} 
  } 

Variable * Variable_I_symbol(symbol *v5259,int v5254,ClaireType *v5260)
{ GC_BIND;
  { Variable *Result ;
    { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
      (v2072->pname = v5259);
      (v2072->index = v5254);
      (v2072->range = v5260);
      add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
      Result = v2072;
      } 
    GC_UNBIND; return (Result);} 
  } 

list * get_indexed_class_Optimize(ClaireClass *v5243)
{ return (v5243->slots);} 

ClaireBoolean * designated_ask_any(OID v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean *v_or;
      { v_or = inherit_ask_class(OWNER(v9268),Kernel._thing);
        if (v_or == CTRUE) Result =CTRUE; 
        else { v_or = inherit_ask_class(OWNER(v9268),Language._Variable);
          if (v_or == CTRUE) Result =CTRUE; 
          else { v_or = inherit_ask_class(OWNER(v9268),Kernel._integer);
            if (v_or == CTRUE) Result =CTRUE; 
            else { v_or = ((Kernel._boolean == OWNER(v9268)) ? CTRUE : CFALSE);
              if (v_or == CTRUE) Result =CTRUE; 
              else { v_or = ((equal(v9268,Core.nil->value) == CTRUE) ? CTRUE : CFALSE);
                if (v_or == CTRUE) Result =CTRUE; 
                else { v_or = ((equal(v9268,_oid_(Kernel.emptySet)) == CTRUE) ? CTRUE : CFALSE);
                  if (v_or == CTRUE) Result =CTRUE; 
                  else { v_or = ((v9268 == CNULL) ? CTRUE : CFALSE);
                    if (v_or == CTRUE) Result =CTRUE; 
                    else { v_or = ((Kernel._float == OWNER(v9268)) ? CTRUE : CFALSE);
                      if (v_or == CTRUE) Result =CTRUE; 
                      else { if (INHERIT(OWNER(v9268),Language._Call))
                         { OID  v5264 = GC_OID((*Optimize.c_code)(v9268));
                          v_or = (((!INHERIT(OWNER(v5264),Language._Call)) && 
                              (designated_ask_any(v5264) == CTRUE)) ? CTRUE : ((OBJECT(Call,v9268)->selector == Core.get_stack) ? CTRUE : CFALSE));
                          } 
                        else if (INHERIT(OWNER(v9268),Language._Call_slot))
                         v_or = designated_ask_any(GC_OID(OBJECT(Call_slot,v9268)->arg));
                        else if (INHERIT(OWNER(v9268),Language._Call_table))
                         v_or = designated_ask_any(GC_OID(OBJECT(Call_table,v9268)->arg));
                        else if (INHERIT(OWNER(v9268),Optimize._to_protect))
                         v_or = ((need_protect_any(GC_OID(OBJECT(to_protect,v9268)->arg)) != CTRUE) ? ((designated_ask_any(GC_OID(OBJECT(to_protect,v9268)->arg)) == CTRUE) ? CTRUE: CFALSE): CFALSE);
                        else if (INHERIT(OWNER(v9268),Language._Call_method))
                         { ClaireBoolean *v_and;
                          { v_and = ((contain_ask_set(OBJECT(set,Optimize._starsimple_operations_star->value),_oid_(OBJECT(Call_method,v9268)->arg->selector)) == CTRUE) ? CTRUE : ((OBJECT(Call_method,v9268)->arg == _at_property1(Kernel.nth,Kernel._bag)) ? CTRUE : CFALSE));
                            if (v_and == CFALSE) v_or =CFALSE; 
                            else { { OID  v13852;
                                { OID gc_local;
                                  ITERATE(v5265);
                                  v13852= _oid_(CFALSE);
                                  bag *v5265_support;
                                  v5265_support = GC_OBJECT(list,OBJECT(Call_method,v9268)->args);
                                  for (START(v5265_support); NEXT(v5265);)
                                  { GC_LOOP;
                                    if (designated_ask_any(v5265) != CTRUE)
                                     { v13852 = Kernel.ctrue;
                                      break;} 
                                    GC_UNLOOP;} 
                                  } 
                                v_and = not_any(v13852);
                                } 
                              if (v_and == CFALSE) v_or =CFALSE; 
                              else v_or = CTRUE;} 
                            } 
                          } 
                        else v_or = ((INHERIT(OWNER(v9268),Optimize._to_CL)) ?
                          designated_ask_any(GC_OID(OBJECT(to_CL,v9268)->arg)) :
                          ((INHERIT(OWNER(v9268),Optimize._to_C)) ?
                            designated_ask_any(GC_OID(OBJECT(to_C,v9268)->arg)) :
                            CFALSE ) );
                        if (v_or == CTRUE) Result =CTRUE; 
                        else Result = CFALSE;} 
                      } 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  gc_register_Variable(Variable *v9268)
{ if ((0 <= Optimize.OPT->loop_index) && 
      (v9268->index > Optimize.OPT->loop_index))
   (Optimize.OPT->loop_index = v9268->index);
  return (Kernel.ctrue);} 

OID  gc_register_Variable2(Variable *v9268,OID v10542)
{ ;{ OID Result = 0;
    if (inner2outer_ask_any(v10542) == CTRUE)
     Result = gc_register_Variable(v9268);
    else Result = Kernel.cfalse;
      return (Result);} 
  } 

ClaireBoolean * inner2outer_ask_any(OID v5264)
{ GC_BIND;
  { ClaireBoolean *Result ;
    if (INHERIT(OWNER(v5264),Optimize._to_protect))
     Result = CTRUE;
    else Result = ((INHERIT(OWNER(v5264),Language._Variable)) ?
      not_any((*Optimize.gcsafe_ask)(GC_OID(_oid_(OBJECT(Variable,v5264)->range)))) :
      ((INHERIT(OWNER(v5264),Optimize._to_CL)) ?
        inner2outer_ask_any(OBJECT(to_CL,v5264)->arg) :
        ((INHERIT(OWNER(v5264),Optimize._to_C)) ?
          inner2outer_ask_any(OBJECT(to_C,v5264)->arg) :
          ((INHERIT(OWNER(v5264),Language._Let)) ?
            inner2outer_ask_any(GC_OID(_oid_(OBJECT(Instruction_with_var,v5264)->var))) :
            CFALSE ) ) ) );
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * identifiable_ask_any(OID v9268)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean *v_or;
      { v_or = ((v9268 == CNULL) ? CTRUE : CFALSE);
        if (v_or == CTRUE) Result =CTRUE; 
        else { if (INHERIT(OWNER(v9268),Optimize._to_CL))
           v_or = identifiable_ask_any(GC_OID(OBJECT(to_CL,v9268)->arg));
          else { ClaireClass * v5260 = class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v9268))));
              v_or = not_any(_oid_(contain_ask_set(OBJECT(set,Optimize._starnon_identifiable_set_star->value),_oid_(v5260))));
              } 
            if (v_or == CTRUE) Result =CTRUE; 
          else Result = CFALSE;} 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_inline_method1(method *v9268,list *v5252,ClaireClass *v5259)
{ GC_BIND;
  ;{ OID Result = 0;
    Result = (*Optimize.c_code)(GC_OID(c_inline_method2(v9268,v5252)),
      _oid_(v5259));
    GC_UNBIND; return (Result);} 
  } 

OID  c_inline_method2(method *v9268,list *v5252)
{ GC_RESERVE(11);  // v3.0.55 optim !
  { OID Result = 0;
    { lambda * v5246 = GC_OBJECT(lambda,v9268->formula);
      OID  v5264 = GC_OID(v5246->body);
      list * v4056 = GC_OBJECT(list,bound_variables_any(v5264));
      symbol * v8450 = (((v9268->selector == Language.iterate) || 
          (v9268->selector == Language.Iterate)) ?
        OBJECT(Variable,(*(v5246->vars))[2])->pname :
        Kernel._class->name );
      v5264= GC_OID(instruction_copy_any(v5264));
      { OID gc_local;
        ITERATE(v5263);
        for (START(v4056); NEXT(v5263);)
        { GC_LOOP;
          { Variable * v15716;
            { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
                (v2072->pname = ((OBJECT(Variable,v5263)->pname == v8450) ?
                  v8450 :
                  gensym_void() ));
                (v2072->index = 1000);
                add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
                v15716 = v2072;
                } 
              GC_OBJECT(Variable,v15716);} 
            store_object(v15716,
              3,
              Kernel._object,
              get_property(Kernel.range,OBJECT(ClaireObject,v5263)),
              CFALSE);
            GC__OID(v5264 = substitution_any(v5264,OBJECT(Variable,v5263),_oid_(v15716)), 5);
            } 
          GC_UNLOOP;} 
        } 
      (Optimize.OPT->max_vars = (Optimize.OPT->max_vars+v4056->length));
      Result = c_substitution_any(v5264,GC_OBJECT(list,v5246->vars),v5252,CFALSE);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_inline_arg_ask_any(OID v9268)
{ GC_BIND;
  { OID Result = 0;
    if (INHERIT(OWNER(v9268),Language._Call))
     { list * v5252 = GC_OBJECT(list,OBJECT(Call,v9268)->args);
      OID  v5253;
      { { list * v14813;
          { { bag *v_list; OID v_val;
              OID v5264,CLcount;
              v_list = v5252;
               v14813 = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v5264 = (*(v_list))[CLcount];
                v_val = (*Optimize.c_type)(v5264);
                
                (*((list *) v14813))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,v14813);} 
          v5253 = restriction_I_property(OBJECT(Call,v9268)->selector,v14813,CTRUE);
          } 
        GC_OID(v5253);} 
      if (Kernel._method == OWNER(v5253))
       { if ((OBJECT(method,v5253)->inline_ask == CTRUE) && 
            (c_inline_ask_method(OBJECT(method,v5253),v5252) == CTRUE))
         Result = c_inline_method2(OBJECT(method,v5253),v5252);
        else Result = Kernel.cfalse;
          } 
      else Result = Core.nil->value;
        } 
    else { OID  v15774;
        { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = Kernel.set_I);
          (v2072->args = list::alloc(1,v9268));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          v15774 = _oid_(v2072);
          } 
        Result = c_inline_arg_ask_any(v15774);
        } 
      GC_UNBIND; return (Result);} 
  } 

OID  c_substitution_any(OID v9268,list *v15476,list *v13625,ClaireBoolean *v11067)
{ GC_BIND;
  { OID Result = 0;
    if (INHERIT(OWNER(v9268),Language._Variable))
     { OID  v5249;
      { { OID  v6801 = CNULL;
          { int  v5250 = 1;
            int  v6784 = v15476->length;
            { OID gc_local;
              while ((v5250 <= v6784))
              { // HOHO, GC_LOOP not needed !
                if (OBJECT(Variable,v9268)->pname == OBJECT(Variable,(*(v15476))[v5250])->pname)
                 { v6801= v5250;
                  break;} 
                ++v5250;
                } 
              } 
            } 
          v5249 = v6801;
          } 
        GC_OID(v5249);} 
      if (v5249 != CNULL)
       Result = (*(v13625))[v5249];
      else Result = v9268;
        } 
    else if (INHERIT(OWNER(v9268),Kernel._bag))
     { { int  v5249 = 1;
        int  v6785 = OBJECT(bag,v9268)->length;
        { OID gc_local;
          while ((v5249 <= v6785))
          { // HOHO, GC_LOOP not needed !
            ((*(OBJECT(list,v9268)))[v5249]=c_substitution_any((*(OBJECT(bag,v9268)))[v5249],v15476,v13625,v11067));
            ++v5249;
            } 
          } 
        } 
      Result = v9268;
      } 
    else if (INHERIT(OWNER(v9268),Language._Call))
     { if (OBJECT(Call,v9268)->selector == Core.eval)
       Result = c_substitution_any((*(OBJECT(Call,v9268)->args))[1],v15476,v13625,((OBJECT(Call,v9268)->args->length == 1) ? CTRUE : (((OBJECT(Call,v9268)->args->length == 2) && 
          (belong_to((*(OBJECT(Call,v9268)->args))[1],(*(OBJECT(Call,v9268)->args))[2]) == CTRUE)) ? CTRUE : CFALSE)));
      else if (v11067 == CTRUE)
       { ClaireHandler c_handle = ClaireHandler();
        if ERROR_IN 
        { { list * v2273;
            { { bag *v_list; OID v_val;
                OID v5265,CLcount;
                v_list = GC_OBJECT(list,OBJECT(Call,v9268)->args);
                 v2273 = v_list->clone();
                for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                { v5265 = (*(v_list))[CLcount];
                  v_val = c_substitution_any(v5265,v15476,v13625,CTRUE);
                  
                  (*((list *) v2273))[CLcount] = v_val;} 
                } 
              GC_OBJECT(list,v2273);} 
            Result = apply_property(OBJECT(Call,v9268)->selector,v2273);
            } 
          ClEnv->cHandle--;} 
        else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
        { c_handle.catchIt();{ tformat_string("a strange problem happens ~A \n",0,list::alloc(1,ClEnv->verbose));
            warn_void();
            tformat_string("failed substitution: ~S",2,list::alloc(1,GC_OID(_oid_(ClEnv->exception_I))));
            c_substitution_any(GC_OID(_oid_(OBJECT(Call,v9268)->args)),v15476,v13625,CFALSE);
            Result = v9268;
            } 
          } 
        else PREVIOUS_HANDLER;} 
      else { c_substitution_any(GC_OID(_oid_(OBJECT(Call,v9268)->args)),v15476,v13625,CFALSE);
          Result = v9268;
          } 
        } 
    else if (INHERIT(OWNER(v9268),Language._Instruction))
     { { OID gc_local;
        ITERATE(v5259);
        bag *v5259_support;
        v5259_support = OWNER(v9268)->slots;
        for (START(v5259_support); NEXT(v5259);)
        { GC_LOOP;
          { OID  v5265 = get_slot(OBJECT(slot,v5259),OBJECT(ClaireObject,v9268));
            put_slot(OBJECT(slot,v5259),OBJECT(ClaireObject,v9268),c_substitution_any(v5265,v15476,v13625,v11067));
            } 
          GC_UNLOOP;} 
        } 
      Result = v9268;
      } 
    else Result = v9268;
      GC_UNBIND; return (Result);} 
  } 

OID  eval_any2(OID v5264,ClaireClass *v5265)
{ return (OPT_EVAL(v5264));} 

list * bound_variables_any(OID v9268)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list *Result ;
    { list * v5252 = list::empty(Kernel._any);
      if (INHERIT(OWNER(v9268),Language._Instruction_with_var))
       v5252= list::alloc(Kernel._any,1,GC_OID(_oid_(OBJECT(For,v9268)->var)));
      if (INHERIT(OWNER(v9268),Language._Variable))
       ;else if (INHERIT(OWNER(v9268),Language._Instruction))
       { OID gc_local;
        ITERATE(v5259);
        for (START(OBJECT(ClaireObject,v9268)->isa->slots); NEXT(v5259);)
        { GC_LOOP;
          GC__ANY(v5252 = add_star_list(v5252,GC_OBJECT(list,bound_variables_any(get_slot(OBJECT(slot,v5259),OBJECT(ClaireObject,v9268))))), 1);
          GC_UNLOOP;} 
        } 
      else if (INHERIT(OWNER(v9268),Kernel._bag))
       { OID gc_local;
        ITERATE(v5264);
        for (START(OBJECT(bag,v9268)); NEXT(v5264);)
        { GC_LOOP;
          v5252= add_star_list(v5252,GC_OBJECT(list,bound_variables_any(v5264)));
          GC_UNLOOP;} 
        } 
      Result = v5252;
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_boolean_any(OID v5264)
{ GC_BIND;
  { OID Result = 0;
    { ClaireType * v15724 = GC_OBJECT(ClaireType,ptype_type(OBJECT(ClaireType,(*Optimize.c_type)(v5264))));
      if (_inf_equal_type(v15724,Kernel._boolean) == CTRUE)
       { if (INHERIT(OWNER(v5264),Language._Call))
         { if ((OBJECT(Call,v5264)->selector == Core.NOT) && 
              (ptype_type(OBJECT(ClaireType,(*Optimize.c_type)((*(OBJECT(Call,v5264)->args))[1]))) != Kernel._boolean))
           { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (v2072->selector = Core._I_equal);
              { Call * v6787; 
                list * v6808;
                v6787 = v2072;
                { OID v_bag;
                  GC_ANY(v6808= list::empty(Kernel.emptySet));
                  { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                      (v2072->selector = Kernel.boolean_I);
                      (v2072->args = list::alloc(1,(*(OBJECT(Call,v5264)->args))[1]));
                      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                      v_bag = _oid_(v2072);
                      } 
                    GC_OID(v_bag);} 
                  ((list *) v6808)->addFast(v_bag);
                  ((list *) v6808)->addFast(Kernel.ctrue);} 
                (v6787->args = v6808);} 
              add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
              v5264 = _oid_(v2072);
              } 
            GC_OID(v5264);} 
          } 
        Result = c_strict_code_any(v5264,Kernel._boolean);
        } 
      else if (_inf_equal_type(v15724,Kernel._bag) == CTRUE)
       { OID  v8953;
        { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = Core._I_equal);
          { Call * v6810; 
            list * v6811;
            v6810 = v2072;
            { OID v_bag;
              GC_ANY(v6811= list::empty(Kernel.emptySet));
              { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.length);
                  (v2072->args = list::alloc(1,v5264));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                GC_OID(v_bag);} 
              ((list *) v6811)->addFast(v_bag);
              ((list *) v6811)->addFast(0);} 
            (v6810->args = v6811);} 
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          v8953 = _oid_(v2072);
          } 
        Result = (*Optimize.c_code)(v8953);
        } 
      else { OID  v11836;
          { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.boolean_I);
            (v2072->args = list::alloc(1,v5264));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v11836 = _oid_(v2072);
            } 
          Result = (*Optimize.c_code)(v11836);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

