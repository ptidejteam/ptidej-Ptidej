/***** CLAIRE Compilation of file d:\claire\v3.2\src\compile\gstat.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:03:10 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <Optimize.h>
#include <Generate.h>
list * unfold_args_list(list *v7236)
{ GC_BIND;
  { list *Result ;
    { list * v2369;
      { { list * v12880 = list::empty(Kernel.emptySet);
          { int  v7233 = 1;
            int  v6627 = v7236->length;
            { OID gc_local;
              while ((v7233 <= v6627))
              { // HOHO, GC_LOOP not needed !
                if (c_func_any((*(v7236))[v7233]) != CTRUE)
                 v12880->addFast(v7233);
                ++v7233;
                } 
              } 
            } 
          v2369 = GC_OBJECT(list,v12880);
          } 
        GC_OBJECT(list,v2369);} 
      { bag *v_list; OID v_val;
        OID v7233,CLcount;
        v_list = v2369;
         Result = v_list->clone();
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { v7233 = (*(v_list))[CLcount];
          { Let * v2072 = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
            (v2072->var = Variable_I_symbol(append_symbol(gensym_void(),_string_("UU")),0,GC_OBJECT(ClaireType,c_type_sort_any((*(v7236))[v7233]))));
            (v2072->value = (*(v7236))[v7233]);
            add_I_property(Kernel.instances,Language._Let,11,_oid_(v2072));
            v_val = _oid_(v2072);
            } 
          
          (*((list *) Result))[CLcount] = v_val;} 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_sort_any(OID v7248)
{ GC_BIND;
  { ClaireType *Result ;
    { ClaireClass * v7243 = OBJECT(ClaireClass,(*Optimize.c_sort)(v7248));
      ClaireClass * v7244 = stupid_t_any1(v7248);
      Result = ((v7243 == Kernel._void) ?
        osort_any(GC_OID((*Optimize.c_type)(v7248))) :
        ((boolean_I_any(sort_equal_class(osort_any(_oid_(v7244)),v7243)) == CTRUE) ?
          v7244 :
          v7243 ) );
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  unfold_arg_list(list *v7236,list *v11424,OID v7248)
{ { OID Result = 0;
    { int  v7233 = 1;
      int  v7234 = 0;
      int  v7237 = v7236->length;
      { ClaireBoolean * g0016I;
        { OID  v16197;
          { v16197= _oid_(CFALSE);
            while ((v7233 <= v7237))
            { if (c_func_any((*(v7236))[v7233]) != CTRUE)
               { ++v7234;
                if (equal((*(v7236))[v7233],v7248) == CTRUE)
                 { v16197 = Kernel.ctrue;
                  break;} 
                } 
              else if (equal((*(v7236))[v7233],v7248) == CTRUE)
               { v16197 = Kernel.cfalse;
                break;} 
              ++v7233;
              } 
            } 
          g0016I = boolean_I_any(v16197);
          } 
        
        if (g0016I == CTRUE) Result = (*Language.var)((*(v11424))[v7234]);
          else Result = (*(v7236))[v7233];
        } 
      } 
    return (Result);} 
  } 

void  unfold_use_list(list *v4417,OID v7248,OID v7243,OID v15308)
{ { int  v7239 = v4417->length;
    int  v7247 = ClEnv->verbose;
    (ClEnv->verbose = 0);
    if (boolean_I_any(_oid_(v4417)) != CTRUE)
     close_exception(((general_error *) (*Core._general_error)(_string_("[internal] design bug c_func(~S) should be true"),
      _oid_(list::alloc(1,v7248)))));
    if (equal((*Optimize.c_type)(v7248),_oid_(Kernel.emptySet)) == CTRUE)
     v7243= _oid_(Kernel._void);
    { int  v7233 = 1;
      int  v6631 = (v7239-1);
      { OID gc_local;
        while ((v7233 <= v6631))
        { // HOHO, GC_LOOP not needed !
          write_property(Kernel.arg,OBJECT(ClaireObject,(*(v4417))[v7233]),(*(v4417))[(v7233+1)]);
          ++v7233;
          } 
        } 
      } 
    (OBJECT(Let,(*(v4417))[v7239])->arg = v7248);
    (ClEnv->verbose = v7247);
    self_statement_Let(OBJECT(Let,(*(v4417))[1]),v7243,v15308);
    } 
  } 

void  statement_any(OID v1140,OID v7243,OID v15308)
{ GC_BIND;
  { ClaireBoolean * v7226 = Optimize.OPT->alloc_stack;
    if (((!INHERIT(OWNER(v1140),Language._Tuple)) && 
          (!INHERIT(OWNER(v1140),Language._Do))) && 
        (!INHERIT(OWNER(v1140),Language._Let)))
     (Optimize.OPT->alloc_stack = CFALSE);
    if (c_func_any(v1140) == CTRUE)
     { { ClaireBoolean * g0019I;
        { ClaireBoolean *v_and;
          { v_and = ((Kernel._string == OWNER(v7243)) ? CTRUE : CFALSE);
            if (v_and == CFALSE) g0019I =CFALSE; 
            else { { OID  v6493;
                { OID  v7454;
                  { if (INHERIT(OWNER(v1140),Optimize._to_CL))
                     v7454 = OBJECT(to_CL,v1140)->arg;
                    else v7454 = v1140;
                      GC_OID(v7454);} 
                  v6493 = (*Optimize.c_sort)(v7454);
                  } 
                v_and = ((v6493 != _oid_(Kernel._void)) ? CTRUE : CFALSE);
                } 
              if (v_and == CFALSE) g0019I =CFALSE; 
              else g0019I = CTRUE;} 
            } 
          } 
        
        if (g0019I == CTRUE) { (*Kernel.c_princ)(v7243);
            princ_string(" = ");
            if (bool_exp_ask_any(v1140) == CTRUE)
             (*Generate.bool_exp_I)(Generate.PRODUCER->value,
              v1140,
              v15308);
            else (*Generate.expression)(v1140,
                v15308);
              princ_string(";");
            breakline_void();
            princ_string("");
            } 
          else if (INHERIT(OWNER(v1140),Language._If))
         (*Generate.self_statement)(v1140,
          v7243,
          v15308);
        else if (INHERIT(OWNER(v1140),Reader._delimiter))
         close_exception(((general_error *) (*Core._general_error)(_string_("[201] Loose delimiter in program: ~S"),
          _oid_(list::alloc(1,v1140)))));
        else (*Generate.stat_exp)(Generate.PRODUCER->value,
            v1140,
            v15308);
          } 
      } 
    else (*Generate.self_statement)(v1140,
        v7243,
        v15308);
      (Optimize.OPT->alloc_stack = v7226);
    } 
  GC_UNBIND;} 

void  self_statement_Construct(Construct *v1140,OID v7243,OID v15308)
{ (*Generate.stat_construct)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_If(If *v1140,OID v7243,OID v15308)
{ if (c_func_any(GC_OID(v1140->test)) == CTRUE) 
  { { princ_string("if ");
      (*Optimize.bool_exp)(GC_OID(v1140->test),
        Kernel.ctrue,
        v15308);
      princ_string("");
      breakline_void();
      princ_string(" ");
      if (INHERIT(OWNER(v1140->arg),Language._If))
       new_block_void();
      statement_any(GC_OID(v1140->arg),v7243,v15308);
      if (INHERIT(OWNER(v1140->arg),Language._If))
       close_block_void();
      if ((Kernel._string == OWNER(v7243)) || 
          (boolean_I_any(v1140->other) == CTRUE))
       { OID  v11797 = GC_OID(v1140->other);
        if (!INHERIT(OWNER(v11797),Language._If))
         (Optimize.OPT->level = (Optimize.OPT->level+1));
        princ_string("else ");
        statement_any(v11797,v7243,v15308);
        princ_string("");
        if (!INHERIT(OWNER(v11797),Language._If))
         (Optimize.OPT->level = (Optimize.OPT->level-1));
        } 
      } 
     } 
  else{ GC_BIND;
    { OID  v7247 = GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
        _oid_(append_symbol(gensym_void(),_string_("I")))));
      new_block_void();
      interface_I_class(Kernel._boolean);
      princ_string(" ");
      princ_string(string_v(v7247));
      princ_string(";");
      breakline_void();
      statement_any(GC_OID(v1140->test),v7247,v15308);
      princ_string("");
      breakline_void();
      (Optimize.OPT->level = (Optimize.OPT->level+1));
      princ_string("if (");
      princ_string(string_v(v7247));
      princ_string(" == ");
      (*Generate.produce)(Generate.PRODUCER->value,
        Kernel.ctrue);
      princ_string(") ");
      statement_any(GC_OID(v1140->arg),v7243,v15308);
      princ_string("");
      (Optimize.OPT->level = (Optimize.OPT->level-1));
      if ((Kernel._string == OWNER(v7243)) || 
          (boolean_I_any(v1140->other) == CTRUE))
       { princ_string("else ");
        statement_any(GC_OID(v1140->other),v7243,v15308);
        princ_string("");
        } 
      close_block_void();
      } 
    GC_UNBIND;} 
  } 

void  self_statement_Do(Do *v1140,OID v7243,OID v15308)
{ if (v1140->args->length == 1) 
  { statement_any((*(v1140->args))[1],v7243,v15308);
     } 
  else{ GC_BIND;
    { list * v7236 = GC_OBJECT(list,v1140->args);
      int  v7237 = v7236->length;
      ClaireBoolean * v7226 = Optimize.OPT->alloc_stack;
      int  v7239 = 0;
      (Optimize.OPT->alloc_stack = CFALSE);
      new_block_void();
      inner_statement_any(_oid_(v1140),v7243,v15308);
      close_block_void();
      } 
    GC_UNBIND;} 
  } 

void  inner_statement_any(OID v1140,OID v7243,OID v15308)
{ GC_BIND;
  if (INHERIT(OWNER(v1140),Language._Do))
   { list * v7236 = GC_OBJECT(list,OBJECT(Do,v1140)->args);
    int  v7237 = v7236->length;
    ClaireBoolean * v7226 = Optimize.OPT->alloc_stack;
    int  v7239 = 0;
    (Optimize.OPT->alloc_stack = CFALSE);
    { ITERATE(v7248);
      for (START(v7236); NEXT(v7248);)
      { ++v7239;
        if (v7239 == v7237)
         { (Optimize.OPT->alloc_stack = v7226);
          inner_statement_any(v7248,v7243,v15308);
          } 
        else if (boolean_I_any(v7248) == CTRUE)
         inner_statement_any(v7248,_oid_(Kernel.emptySet),v15308);
        } 
      } 
    } 
  else statement_any(v1140,v7243,v15308);
    GC_UNBIND;} 

void  self_statement_Let(Let *v1140,OID v7243,OID v15308)
{ (*Generate.stat_let)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_And(And *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { char * v7247 = GC_STRING(check_var_string("v_and",v7243,v15308));
    new_block_void();
    interface_I_class(Kernel._boolean);
    princ_string(v7247);
    princ_string(";");
    breakline_void();
    { OID gc_local;
      ITERATE(v7248);
      bag *v7248_support;
      v7248_support = GC_OBJECT(list,v1140->args);
      for (START(v7248_support); NEXT(v7248);)
      { GC_LOOP;
        { new_block_void();
          statement_any(v7248,_string_(v7247),v15308);
          princ_string("if (");
          princ_string(v7247);
          princ_string(" == ");
          (*Generate.produce)(Generate.PRODUCER->value,
            Kernel.cfalse);
          princ_string(") ");
          if (Kernel._string == OWNER(v7243))
           { c_princ_string(string_v(v7243));
            princ_string(" =");
            } 
          expression_boolean(CFALSE,v15308);
          princ_string("; ");
          breakline_void();
          princ_string("else ");
          } 
        GC_UNLOOP;} 
      } 
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      expression_boolean(CTRUE,v15308);
      princ_string(";");
      } 
    else { expression_boolean(CTRUE,v15308);
        princ_string(";");
        } 
      { OID gc_local;
      ITERATE(v7248);
      bag *v7248_support;
      v7248_support = GC_OBJECT(list,v1140->args);
      for (START(v7248_support); NEXT(v7248);)
      { GC_LOOP;
        close_block_void();
        GC_UNLOOP;} 
      } 
    close_block_void();
    } 
  GC_UNBIND;} 

void  self_statement_Or(Or *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { char * v7247 = GC_STRING(check_var_string("v_or",v7243,v15308));
    new_block_void();
    interface_I_class(Kernel._boolean);
    princ_string(v7247);
    princ_string(";");
    breakline_void();
    { OID gc_local;
      ITERATE(v7248);
      bag *v7248_support;
      v7248_support = GC_OBJECT(list,v1140->args);
      for (START(v7248_support); NEXT(v7248);)
      { GC_LOOP;
        { new_block_void();
          statement_any(v7248,_string_(v7247),v15308);
          princ_string("if (");
          princ_string(v7247);
          princ_string(" == ");
          (*Generate.produce)(Generate.PRODUCER->value,
            Kernel.ctrue);
          princ_string(") ");
          if (Kernel._string == OWNER(v7243))
           { c_princ_string(string_v(v7243));
            princ_string(" =");
            } 
          expression_boolean(CTRUE,v15308);
          princ_string("; ");
          breakline_void();
          princ_string("else ");
          } 
        GC_UNLOOP;} 
      } 
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      expression_boolean(CFALSE,v15308);
      princ_string(";");
      } 
    else { expression_boolean(CFALSE,v15308);
        princ_string(";");
        } 
      { OID gc_local;
      ITERATE(v7248);
      bag *v7248_support;
      v7248_support = GC_OBJECT(list,v1140->args);
      for (START(v7248_support); NEXT(v7248);)
      { GC_LOOP;
        close_block_void();
        GC_UNLOOP;} 
      } 
    close_block_void();
    } 
  GC_UNBIND;} 

void  self_statement_While(While *v1140,OID v7243,OID v15308)
{ (*Generate.stat_while)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_Assign(Assign *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { OID  v7247 = GC_OID(v1140->var);
    OID  v7248 = GC_OID(v1140->arg);
    ClaireBoolean * v9238 = ((boolean_I_any(v15308) == CTRUE) ? ((Optimize.OPT->loop_gc == CTRUE) ? ((inner2outer_ask_any(v7248) == CTRUE) ? CTRUE: CFALSE): CFALSE): CFALSE);
    OID  v7249;
    { if (v9238 == CTRUE)
       v7249 = (*Kernel.arg)(v7248);
      else v7249 = v7248;
        GC_OID(v7249);} 
    if (Kernel._string == OWNER(v7243))
     { new_block_void();
      statement_any(v7249,v7243,v15308);
      princ_string(" ");
      if (v9238 == CTRUE)
       (*Generate.gc_protection_exp)(Generate.PRODUCER->value,
        v7247,
        Kernel.cfalse,
        v7243,
        v15308);
      else { ident_symbol(OBJECT(Variable,v7247)->pname);
          princ_string(" = ");
          (*Kernel.c_princ)(v7243);
          princ_string("");
          } 
        princ_string(";");
      close_block_void();
      princ_string("");
      } 
    else if (v9238 == CTRUE)
     { new_block_void();
      statement_any(v7249,GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
        v7247)),v15308);
      princ_string(" ");
      (*Generate.gc_protection_exp)(Generate.PRODUCER->value,
        v7247,
        Kernel.ctrue,
        v7247,
        v15308);
      princ_string(";");
      close_block_void();
      princ_string("");
      } 
    else statement_any(v7248,GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
        v7247)),v15308);
      } 
  GC_UNBIND;} 

void  self_statement_Gassign(Gassign *v1140,OID v7243,OID v15308)
{ (*Generate.stat_gassign)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_to_protect(to_protect *v1140,OID v7243,OID v15308)
{ if ((Optimize.OPT->protection == CTRUE) && 
      (Kernel._string == OWNER(v7243))) 
  { { ClaireClass * v7227 = OBJECT(ClaireClass,(*Optimize.c_sort)(GC_OID(v1140->arg)));
      new_block_void();
      statement_any(GC_OID(v1140->arg),v7243,v15308);
      princ_string(gc_protect_class(v7227));
      princ_string("(");
      if (INHERIT(v7227,Kernel._object))
       { ident_class(psort_any(GC_OID((*Optimize.c_type)(GC_OID(v1140->arg)))));
        princ_string(",");
        } 
      (*Kernel.c_princ)(v7243);
      princ_string(");");
      close_block_void();
      } 
     } 
  else{ GC_BIND;
    statement_any(GC_OID(v1140->arg),v7243,v15308);
    GC_UNBIND;} 
  } 

void  self_statement_For(For *v1140,OID v7243,OID v15308)
{ (*Generate.stat_for)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_Iteration(Iteration *v1140,OID v7243,OID v15308)
{ (*Generate.stat_iteration)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_Return(Return *v1140,OID v7243,OID v15308)
{ if (v15308 == CNULL) 
  { { new_block_void();
      statement_any(GC_OID(v1140->arg),_oid_(Kernel.emptySet),_oid_(Kernel.emptySet));
      princ_string("break;");
      close_block_void();
      } 
     } 
  else{ if (Kernel._string == OWNER(v15308)) 
    { { new_block_void();
        statement_any(GC_OID(v1140->arg),v15308,_oid_(Kernel.emptySet));
        princ_string("break;");
        close_block_void();
        } 
       } 
    else{ GC_BIND;
      close_exception(((general_error *) (*Core._general_error)(_string_("[204] break not inside a For or While:~S"),
        _oid_(list::alloc(1,_oid_(v1140))))));
      GC_UNBIND;} 
    } 
  } 

void  self_statement_Call(Call *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { list * v7236 = GC_OBJECT(list,v1140->args);
    list * v11424 = GC_OBJECT(list,unfold_args_list(v7236));
    { OID  v8415;
      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
        (v2072->selector = v1140->selector);
        { Call * v6657; 
          list * v6658;
          v6657 = v2072;
          { bag *v_list; OID v_val;
            OID v7250,CLcount;
            v_list = v7236;
             v6658 = v_list->clone();
            for (CLcount= 1; CLcount <= v_list->length; CLcount++)
            { v7250 = (*(v_list))[CLcount];
              v_val = unfold_arg_list(v7236,v11424,v7250);
              
              (*((list *) v6658))[CLcount] = v_val;} 
            } 
          (v6657->args = v6658);} 
        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
        v8415 = _oid_(v2072);
        } 
      unfold_use_list(v11424,v8415,v7243,v15308);
      } 
    } 
  GC_UNBIND;} 

void  self_statement_Call_method(Call_method *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { list * v7236 = GC_OBJECT(list,v1140->args);
    list * v11424 = GC_OBJECT(list,unfold_args_list(v7236));
    { OID  v11298;
      { Call_method * v2072 = ((Call_method *) GC_OBJECT(Call_method,new_object_class(Language._Call_method)));
        (v2072->arg = v1140->arg);
        { Call_method * v6660; 
          list * v6661;
          v6660 = v2072;
          { bag *v_list; OID v_val;
            OID v7250,CLcount;
            v_list = v7236;
             v6661 = v_list->clone();
            for (CLcount= 1; CLcount <= v_list->length; CLcount++)
            { v7250 = (*(v_list))[CLcount];
              v_val = unfold_arg_list(v7236,v11424,v7250);
              
              (*((list *) v6661))[CLcount] = v_val;} 
            } 
          (v6660->args = v6661);} 
        add_I_property(Kernel.instances,Language._Call_method,11,_oid_(v2072));
        v11298 = _oid_(v2072);
        } 
      unfold_use_list(v11424,v11298,v7243,v15308);
      } 
    } 
  GC_UNBIND;} 

void  self_statement_Call_method1(Call_method1 *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { list * v7236 = GC_OBJECT(list,v1140->args);
    list * v11424 = GC_OBJECT(list,unfold_args_list(v7236));
    { OID  v14182;
      { Call_method1 * v2072 = ((Call_method1 *) GC_OBJECT(Call_method1,new_object_class(Language._Call_method1)));
        (v2072->arg = v1140->arg);
        { Call_method * v6663; 
          list * v6684;
          v6663 = v2072;
          { bag *v_list; OID v_val;
            OID v7250,CLcount;
            v_list = v7236;
             v6684 = v_list->clone();
            for (CLcount= 1; CLcount <= v_list->length; CLcount++)
            { v7250 = (*(v_list))[CLcount];
              v_val = unfold_arg_list(v7236,v11424,v7250);
              
              (*((list *) v6684))[CLcount] = v_val;} 
            } 
          (v6663->args = v6684);} 
        add_I_property(Kernel.instances,Language._Call_method1,11,_oid_(v2072));
        v14182 = _oid_(v2072);
        } 
      unfold_use_list(v11424,v14182,v7243,v15308);
      } 
    } 
  GC_UNBIND;} 

void  self_statement_Call_method2(Call_method2 *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { list * v7236 = GC_OBJECT(list,v1140->args);
    list * v11424 = GC_OBJECT(list,unfold_args_list(v7236));
    { OID  v4477;
      { Call_method2 * v2072 = ((Call_method2 *) GC_OBJECT(Call_method2,new_object_class(Language._Call_method2)));
        (v2072->arg = v1140->arg);
        { Call_method * v6686; 
          list * v6687;
          v6686 = v2072;
          { bag *v_list; OID v_val;
            OID v7250,CLcount;
            v_list = v7236;
             v6687 = v_list->clone();
            for (CLcount= 1; CLcount <= v_list->length; CLcount++)
            { v7250 = (*(v_list))[CLcount];
              v_val = unfold_arg_list(v7236,v11424,v7250);
              
              (*((list *) v6687))[CLcount] = v_val;} 
            } 
          (v6686->args = v6687);} 
        add_I_property(Kernel.instances,Language._Call_method2,11,_oid_(v2072));
        v4477 = _oid_(v2072);
        } 
      unfold_use_list(v11424,v4477,v7243,v15308);
      } 
    } 
  GC_UNBIND;} 

void  self_statement_Super(Super *v1140,OID v7243,OID v15308)
{ (*Generate.stat_super)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_Cast(Cast *v1140,OID v7243,OID v15308)
{ GC_BIND;
  statement_any(GC_OID(v1140->arg),v7243,v15308);
  GC_UNBIND;} 

void  self_statement_Handle(ClaireHandle *v1140,OID v7243,OID v15308)
{ (*Generate.stat_handle)(Generate.PRODUCER->value,
    _oid_(v1140),
    v7243,
    v15308);
  } 

void  self_statement_to_CL(to_CL *v1140,OID v7243,OID v15308)
{ GC_BIND;
  if (Kernel._string == OWNER(v7243))
   { Variable * v4936 = GC_OBJECT(Variable,build_Variable_string(string_I_symbol(gensym_string("V_CL")),_oid_(v1140->set_arg)));
    ClaireClass * v11592 = ((v1140->set_arg == Kernel._void) ?
      Kernel._any :
      v1140->set_arg );
    new_block_void();
    interface_I_class(v11592);
    princ_string(" ");
    expression_Variable(v4936,v15308);
    princ_string(";");
    statement_any(GC_OID(v1140->arg),GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
      _oid_(v4936))),v15308);
    breakline_void();
    princ_string(string_v(v7243));
    princ_string("=");
    (*Generate.to_cl)(Generate.PRODUCER->value,
      _oid_(v4936),
      _oid_(v1140->set_arg),
      v15308);
    princ_string(";");
    close_block_void();
    } 
  else statement_any(GC_OID(v1140->arg),v7243,v15308);
    GC_UNBIND;} 

void  self_statement_to_C(to_C *v1140,OID v7243,OID v15308)
{ GC_BIND;
  if (Kernel._string == OWNER(v7243))
   { Variable * v4936 = GC_OBJECT(Variable,build_Variable_string("V_C",_oid_(Kernel._any)));
    new_block_void();
    (*Generate.any_interface)(Generate.PRODUCER->value);
    princ_string(" ");
    (*Language.ident)(Generate.PRODUCER->value,
      _oid_(v4936));
    princ_string(";");
    statement_any(GC_OID(v1140->arg),GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
      _oid_(v4936))),v15308);
    breakline_void();
    princ_string(string_v(v7243));
    princ_string("=");
    (*Generate.to_c)(Generate.PRODUCER->value,
      _oid_(v4936),
      _oid_(v1140->set_arg),
      v15308);
    princ_string(";");
    close_block_void();
    } 
  else statement_any(GC_OID(v1140->arg),v7243,v15308);
    GC_UNBIND;} 

void  self_statement_C_cast(C_cast *v1140,OID v7243,OID v15308)
{ GC_BIND;
  if (Kernel._string == OWNER(v7243))
   { char * v4936 = GC_STRING(check_var_string("V_CC",v7243,v15308));
    ClaireClass * v11592 = v1140->set_arg;
    ClaireClass * v11593 = stupid_t_any1(GC_OID(v1140->arg));
    ClaireType * v11622 = join_class(sort_I_class(v11592),v11593);
    new_block_void();
    (*Generate.define_variable)(Generate.PRODUCER->value,
      _oid_(v11622),
      _string_(v4936));
    breakline_void();
    statement_any(GC_OID(v1140->arg),_string_(v4936),v15308);
    princ_string(string_v(v7243));
    princ_string("= ");
    (*Generate.pointer_cast)(Generate.PRODUCER->value,
      _oid_(v11592));
    princ_string(" ");
    princ_string(v4936);
    princ_string(";");
    close_block_void();
    } 
  else statement_any(GC_OID(v1140->arg),v7243,v15308);
    GC_UNBIND;} 

void  self_statement_Call_slot(Call_slot *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { char * v6108 = string_I_symbol(gensym_void());
    Variable * v4936 = GC_OBJECT(Variable,build_Variable_string(v6108,GC_OID((*Optimize.c_type)(GC_OID(v1140->arg)))));
    new_block_void();
    interface_I_class(sort_Variable(v4936));
    princ_string(" ");
    expression_Variable(v4936,_oid_(Kernel.emptySet));
    princ_string(";");
    breakline_void();
    statement_any(GC_OID(v1140->arg),_string_(v6108),v15308);
    breakline_void();
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      } 
    { Call_slot * v7360;
      { Call_slot * v2072 = ((Call_slot *) GC_OBJECT(Call_slot,new_object_class(Language._Call_slot)));
        (v2072->selector = v1140->selector);
        (v2072->arg = _oid_(v4936));
        add_I_property(Kernel.instances,Language._Call_slot,11,_oid_(v2072));
        v7360 = v2072;
        } 
      expression_Call_slot(v7360,v15308);
      } 
    princ_string(";");
    close_block_void();
    } 
  GC_UNBIND;} 

void  self_statement_Call_table(Call_table *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { char * v6108 = string_I_symbol(gensym_void());
    Variable * v4936 = GC_OBJECT(Variable,build_Variable_string(v6108,GC_OID((*Optimize.c_type)(GC_OID(v1140->arg)))));
    new_block_void();
    interface_I_class(sort_Variable(v4936));
    princ_string(" ");
    expression_Variable(v4936,_oid_(Kernel.emptySet));
    princ_string(";");
    breakline_void();
    statement_any(GC_OID(v1140->arg),_string_(v6108),v15308);
    breakline_void();
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      } 
    { Call_table * v8321;
      { Call_table * v2072 = ((Call_table *) GC_OBJECT(Call_table,new_object_class(Language._Call_table)));
        (v2072->selector = v1140->selector);
        (v2072->arg = _oid_(v4936));
        add_I_property(Kernel.instances,Language._Call_table,11,_oid_(v2072));
        v8321 = v2072;
        } 
      expression_Call_table(v8321,v15308);
      } 
    princ_string(";");
    close_block_void();
    } 
  GC_UNBIND;} 

void  self_statement_Call_array(Call_array *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { Variable * v5609 = GC_OBJECT(Variable,build_Variable_string("va_arg1",_oid_(Kernel._array)));
    Variable * v5610 = GC_OBJECT(Variable,build_Variable_string("va_arg2",_oid_(Kernel._integer)));
    new_block_void();
    interface_I_class(Kernel._array);
    princ_string(" ");
    expression_Variable(v5609,_oid_(Kernel.emptySet));
    princ_string(";");
    breakline_void();
    interface_I_class(Kernel._integer);
    princ_string(" ");
    expression_Variable(v5610,_oid_(Kernel.emptySet));
    princ_string(";");
    breakline_void();
    statement_any(GC_OID(v1140->selector),_string_("va_arg1"),v15308);
    statement_any(GC_OID(v1140->arg),_string_("va_arg2"),v15308);
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      } 
    { Call_array * v9282;
      { Call_array * v2072 = ((Call_array *) GC_OBJECT(Call_array,new_object_class(Language._Call_array)));
        (v2072->selector = _oid_(v5609));
        (v2072->arg = _oid_(v5610));
        (v2072->test = v1140->test);
        add_I_property(Kernel.instances,Language._Call_array,11,_oid_(v2072));
        v9282 = v2072;
        } 
      expression_Call_array(v9282,v15308);
      } 
    princ_string(";");
    close_block_void();
    } 
  GC_UNBIND;} 

void  self_statement_Update(Update *v1140,OID v7243,OID v15308)
{ GC_BIND;
  { OID  v7216 = GC_OID(v1140->var);
    OID  v7240 = GC_OID(v1140->selector);
    ClaireType * v11590;
    { if (INHERIT(OWNER(v7216),Language._Call_slot))
       v11590 = domain_I_restriction(OBJECT(Call_slot,v7216)->selector);
      else if (INHERIT(OWNER(v7216),Language._Call_array))
       v11590 = Kernel._integer;
      else v11590 = U_type(Kernel._any,GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Kernel.domain)(v7240))));
        GC_OBJECT(ClaireType,v11590);} 
    ClaireType * v11592;
    { if (INHERIT(OWNER(v7216),Language._Call_slot))
       { v11592 = ((v1140->arg == _oid_(Kernel.add)) ?
          member_type(OBJECT(Call_slot,v7216)->selector->range) :
          OBJECT(Call_slot,v7216)->selector->range );
        } 
      else if (INHERIT(OWNER(v7216),Language._Call_array))
       { v11592 = (((OBJECT(ClaireBoolean,(*Core._inf_equalt)(GC_OID(OBJECT(Call_array,v7216)->test),
          _oid_(Kernel._float)))) == CTRUE) ?
          Kernel._float :
          Kernel._any );
        } 
      else v11592 = U_type(Kernel._any,GC_OBJECT(ClaireType,((v1140->arg == _oid_(Kernel.add)) ?
          member_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Kernel.range)(v7240)))) :
          OBJECT(ClaireType,(*Kernel.range)(v7240)) )));
        GC_OBJECT(ClaireType,v11592);} 
    ClaireClass * v937 = stupid_t_any1(GC_OID((*Kernel.arg)(v1140->var)));
    ClaireClass * v938 = stupid_t_any1(GC_OID(v1140->value));
    OID  v313;
    { if ((_inf_equalt_class(v937,psort_any(_oid_(v11590))) != CTRUE) && 
          (_inf_equalt_class(v937,Kernel._object) == CTRUE))
       { C_cast * v2072 = ((C_cast *) GC_OBJECT(C_cast,new_object_class(Optimize._C_cast)));
        (v2072->arg = (*Kernel.arg)(GC_OID(v1140->var)));
        (v2072->set_arg = psort_any(_oid_(v11590)));
        add_I_property(Kernel.instances,Optimize._C_cast,11,_oid_(v2072));
        v313 = _oid_(v2072);
        } 
      else v313 = (*Kernel.arg)(GC_OID(v1140->var));
        GC_OID(v313);} 
    OID  v11156;
    { if ((_inf_equalt_class(v938,psort_any(_oid_(v11592))) != CTRUE) && 
          (_inf_equalt_class(v938,Kernel._object) == CTRUE))
       { C_cast * v2072 = ((C_cast *) GC_OBJECT(C_cast,new_object_class(Optimize._C_cast)));
        (v2072->arg = v1140->value);
        (v2072->set_arg = psort_any(_oid_(v11592)));
        add_I_property(Kernel.instances,Optimize._C_cast,11,_oid_(v2072));
        v11156 = _oid_(v2072);
        } 
      else v11156 = v1140->value;
        GC_OID(v11156);} 
    Variable * v5609 = GC_OBJECT(Variable,Variable_I_symbol(gensym_void(),0,v11590));
    Variable * v5610 = GC_OBJECT(Variable,Variable_I_symbol(gensym_void(),0,v11592));
    new_block_void();
    interface_I_class(psort_any(_oid_(v11590)));
    princ_string(" ");
    expression_Variable(v5609,_oid_(Kernel.emptySet));
    princ_string("; ");
    breakline_void();
    interface_I_class(psort_any(_oid_(v11592)));
    princ_string(" ");
    expression_Variable(v5610,_oid_(Kernel.emptySet));
    princ_string(";");
    breakline_void();
    statement_any(v313,GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
      _oid_(v5609))),v15308);
    statement_any(v11156,GC_OID((*Kernel.string_I)(Generate.PRODUCER->value,
      _oid_(v5610))),v15308);
    if (Kernel._string == OWNER(v7243))
     { c_princ_string(string_v(v7243));
      princ_string(" = ");
      } 
    { Update * v10243;
      { Update * v2072 = ((Update *) GC_OBJECT(Update,new_object_class(Language._Update)));
        (v2072->selector = v7240);
        { Update * v6693; 
          OID  v6694;
          v6693 = v2072;
          if (v1140->arg == _oid_(Kernel.add))
           v6694 = _oid_(Kernel.add);
          else if (sort_Variable(v5609) == Kernel._any)
           v6694 = _oid_(v5609);
          else { to_CL * v2072 = ((to_CL *) GC_OBJECT(to_CL,new_object_class(Optimize._to_CL)));
              (v2072->arg = _oid_(v5609));
              (v2072->set_arg = sort_Variable(v5609));
              add_I_property(Kernel.instances,Optimize._to_CL,11,_oid_(v2072));
              v6694 = _oid_(v2072);
              } 
            (v6693->arg = v6694);} 
        (v2072->value = _oid_(v5610));
        { Update * v6715; 
          OID  v6716;
          v6715 = v2072;
          { OID  v11816 = GC_OID((*Kernel.copy)(v7216));
            put_property2(Kernel.arg,OBJECT(ClaireObject,v11816),_oid_(v5609));
            v6716 = v11816;
            } 
          (v6715->var = v6716);} 
        add_I_property(Kernel.instances,Language._Update,11,_oid_(v2072));
        v10243 = v2072;
        } 
      expression_Update(v10243,v15308);
      } 
    princ_string(";");
    close_block_void();
    } 
  GC_UNBIND;} 

