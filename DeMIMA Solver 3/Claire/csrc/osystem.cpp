/***** CLAIRE Compilation of file d:\claire\v3.2\src\compile\osystem.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:03:03 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <Optimize.h>
char * home_void()
{ return (getenv_string("CLAIRE3_HOME"));} 

OID  safe_any2(OID v5264)
{ return (v5264);} 

ClaireType * safe_any2_type(ClaireType *v5264)
{ return (v5264);} 

ClaireType * c_type_any_Optimize(OID v9268)
{ GC_BIND;
  { ClaireType *Result ;
    { ClaireObject *V_CC ;
      if (INHERIT(OWNER(v9268),Language._Variable))
       { OID  v5258 = GC_OID(get_property(Kernel.range,OBJECT(ClaireObject,v9268)));
        if (v5258 == CNULL)
         V_CC = Kernel._any;
        else V_CC = (((INHERIT(OWNER(v5258),Core._Union)) && (equal(_oid_(OBJECT(Union,v5258)->t1),_oid_(Kernel.emptySet)) == CTRUE)) ?
          CLREAD(Union,OBJECT(Union,v5258)->t2,t2) :
          ptype_type(OBJECT(ClaireType,v5258)) );
        } 
      else if (INHERIT(OWNER(v9268),Core._global_variable))
       { ClaireType * v5258 = OBJECT(global_variable,v9268)->range;
        if (boolean_I_any(_oid_(v5258)) == CTRUE)
         V_CC = v5258;
        else V_CC = set::alloc(1,OBJECT(global_variable,v9268)->value);
          } 
      else if (INHERIT(OWNER(v9268),Kernel._unbound_symbol))
       V_CC = OBJECT(ClaireType,(*Optimize.Cerror)(_string_("[215] the symbol ~A is unbound_symbol"),
        _oid_(OBJECT(unbound_symbol,v9268)->name)));
      else if (INHERIT(OWNER(v9268),Kernel._error))
       V_CC = Kernel.emptySet;
      else if (INHERIT(OWNER(v9268),Language._Update))
       V_CC = OBJECT(ClaireType,(*Optimize.c_type)(OBJECT(Update,v9268)->value));
      else if (INHERIT(OWNER(v9268),Language._Construct))
       { if ((!INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._List)) && 
            (!INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Set)))
         V_CC = Kernel._any;
        else { ClaireType * v8003 = Kernel.emptySet;
            { OID gc_local;
              ITERATE(v13275);
              for (START(OBJECT(Construct,v9268)->args); NEXT(v13275);)
              { GC_LOOP;
                if (boolean_I_any(_oid_(v8003)) == CTRUE)
                 v8003= meet_class(((ClaireClass *) v8003),class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275)))));
                else v8003= class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))));
                  GC_UNLOOP;} 
              } 
            V_CC = nth_class1(((INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Set)) ?
              Kernel._set :
              Kernel._list ),v8003);
            } 
          } 
      else if (INHERIT(OWNER(v9268),Language._Instruction))
       close_exception(((general_error *) (*Core._general_error)(_string_("c_type of ~S is not defined"),
        _oid_(list::alloc(1,_oid_(OWNER(v9268)))))));
      else V_CC = set::alloc(1,v9268);
        Result= (ClaireType *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 

OID  c_strict_code_any(OID v5264,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    Result = (*Optimize.c_strict_check)(GC_OID((*Optimize.c_code)(v5264,
        _oid_(v5259))),
      _oid_(v5259));
    GC_UNBIND; return (Result);} 
  } 

OID  c_strict_check_any_Optimize(OID v5264,ClaireClass *v5259)
{ if ((Optimize.OPT->online_ask != CTRUE) && 
      ((INHERIT(v5259,Kernel._object)) && 
        (!INHERIT(stupid_t_any1(v5264),v5259)))) 
  { { OID Result = 0;
      { if ((*Optimize.c_type)(v5264) == _oid_(Kernel._any))
         (*Optimize.Cerror)(_string_("Need explict cast: ~S is not a ~S"),
          v5264,
          _oid_(v5259));
        { C_cast * v2072 = ((C_cast *) GC_OBJECT(C_cast,new_object_class(Optimize._C_cast)));
          (v2072->arg = v5264);
          (v2072->set_arg = v5259);
          add_I_property(Kernel.instances,Optimize._C_cast,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
      return (Result);} 
     } 
  else{ GC_BIND;
    { OID Result = 0;
      Result = v5264;
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  c_code_any1_Optimize(OID v5264,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    { OID  v5265;
      { if (INHERIT(OWNER(v5264),Language._Call))
         v5265 = c_code_call_Call(OBJECT(Call,v5264),v5259);
        else v5265 = (*Optimize.c_code)(v5264);
          GC_OID(v5265);} 
      ClaireClass * v5266 = OBJECT(ClaireClass,(*Optimize.c_sort)(v5265));
      if ((v5259 == Kernel._void) || 
          ((v5266 == v5259) || 
            (Optimize.OPT->online_ask == CTRUE)))
       Result = v5265;
      else if (v5259 == Kernel._any)
       { if ((v5266 == Kernel._integer) && 
            ((INHERIT(OWNER(v5265),Language._Call_slot)) && 
              ((Optimize.compiler->overflow_ask != CTRUE) || 
                  (Optimize.compiler->class2file_ask != CTRUE))))
         Result = v5265;
        else if (INHERIT(OWNER(v5265),Optimize._to_C))
         Result = OBJECT(to_C,v5265)->arg;
        else { if (v5266 == Kernel._float)
             { (Optimize.OPT->protection = CTRUE);
              (Optimize.OPT->allocation = CTRUE);
              } 
            { to_CL * v2072 = ((to_CL *) GC_OBJECT(to_CL,new_object_class(Optimize._to_CL)));
              (v2072->arg = v5265);
              (v2072->set_arg = psort_any(GC_OID((*Optimize.c_type)(v5265))));
              add_I_property(Kernel.instances,Optimize._to_CL,11,_oid_(v2072));
              Result = _oid_(v2072);
              } 
            } 
          } 
      else if (v5266 == Kernel._any)
       { to_C * v2072 = ((to_C *) GC_OBJECT(to_C,new_object_class(Optimize._to_C)));
        (v2072->arg = v5265);
        (v2072->set_arg = v5259);
        add_I_property(Kernel.instances,Optimize._to_C,11,_oid_(v2072));
        Result = _oid_(v2072);
        } 
      else Result = v5265;
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_code_any2_Optimize(OID v9268)
{ GC_BIND;
  { OID Result = 0;
    if (INHERIT(OWNER(v9268),Kernel._unbound_symbol))
     Result = (*Optimize.Cerror)(_string_("[215] the symbol ~A is unbound_symbol"),
      _oid_(OBJECT(unbound_symbol,v9268)->name));
    else if (INHERIT(OWNER(v9268),Language._Variable))
     Result = v9268;
    else if (INHERIT(OWNER(v9268),Core._global_variable))
     { c_register_object(OBJECT(ClaireObject,v9268));
      Result = v9268;
      } 
    else if (INHERIT(OWNER(v9268),Language._Optimized_instruction))
     Result = v9268;
    else if (INHERIT(OWNER(v9268),Language._Instruction))
     Result = (*Optimize.Cerror)(_string_("[internal] c_code(~S) should have a parameter"),
      v9268);
    else if (Kernel._set == OWNER(v9268))
     { if (OBJECT(bag,v9268)->length != 0)
       { Set * v5264;
        { { Set * v2072 = ((Set *) GC_OBJECT(Set,new_object_class(Language._Set)));
            (v2072->args = list_I_set(OBJECT(set,v9268)));
            add_I_property(Kernel.instances,Language._Set,11,_oid_(v2072));
            v5264 = v2072;
            } 
          GC_OBJECT(Set,v5264);} 
        if (of_bag(OBJECT(bag,v9268)) != Kernel._void)
         (v5264->of = of_bag(OBJECT(bag,v9268)));
        Result = (*Optimize.c_code)(_oid_(v5264));
        } 
      else Result = v9268;
        } 
    else if (INHERIT(OWNER(v9268),Kernel._list))
     { if (OBJECT(bag,v9268)->length != 0)
       { List * v5264;
        { { List * v2072 = ((List *) GC_OBJECT(List,new_object_class(Language._List)));
            (v2072->args = OBJECT(list,v9268));
            add_I_property(Kernel.instances,Language._List,11,_oid_(v2072));
            v5264 = v2072;
            } 
          GC_OBJECT(List,v5264);} 
        if (of_bag(OBJECT(bag,v9268)) != Kernel._void)
         (v5264->of = of_bag(OBJECT(bag,v9268)));
        Result = (*Optimize.c_code)(_oid_(v5264));
        } 
      else Result = v9268;
        } 
    else if (INHERIT(OWNER(v9268),Kernel._tuple))
     { OID  v12447;
      { Tuple * v2072 = ((Tuple *) GC_OBJECT(Tuple,new_object_class(Language._Tuple)));
        (v2072->args = OBJECT(list,v9268));
        add_I_property(Kernel.instances,Language._Tuple,11,_oid_(v2072));
        v12447 = _oid_(v2072);
        } 
      Result = (*Optimize.c_code)(v12447);
      } 
    else { if (INHERIT(OWNER(v9268),Kernel._thing))
         (*Optimize.c_register)(v9268);
        if ((Kernel._float == OWNER(v9268)) || 
            (INHERIT(OWNER(v9268),Kernel._function)))
         (Optimize.OPT->allocation = CTRUE);
        Result = v9268;
        } 
      GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * c_gc_ask_any(OID v9268)
{ { ClaireBoolean *Result ;
    Result = ((INHERIT(OWNER(v9268),Language._Variable)) ?
      CFALSE :
      ((INHERIT(OWNER(v9268),Core._global_variable)) ?
        not_any(_oid_((INHERIT(OBJECT(global_variable,v9268)->range->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(OBJECT(global_variable,v9268)->range))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(OBJECT(global_variable,v9268)->range)))))) :
        ((INHERIT(OWNER(v9268),Language._Construct)) ?
          CFALSE :
          ((INHERIT(OWNER(v9268),Language._Instruction)) ?
            CFALSE :
            CFALSE ) ) ) );
    return (Result);} 
  } 

ClaireClass * c_sort_any_Optimize(OID v9268)
{ GC_BIND;
  { ClaireClass *Result ;
    { ClaireObject *V_CC ;
      if (INHERIT(OWNER(v9268),Core._global_variable))
       V_CC = Kernel._any;
      else if (INHERIT(OWNER(v9268),Language._Instruction))
       { if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Variable))
         V_CC = sort_Variable(OBJECT(Variable,v9268));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Assign))
         V_CC = sort_Variable(GC_OBJECT(Variable,OBJECT(Variable,OBJECT(Assign,v9268)->var)));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call))
         V_CC = osort_any(_oid_(selector_psort_Call(OBJECT(Call,v9268))));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call_method))
         { V_CC = (((OBJECT(Call_method,v9268)->arg->selector == Core.externC) && 
              (OBJECT(Call_method,v9268)->args->length == 2)) ?
            psort_any((*(OBJECT(Call_method,v9268)->args))[2]) :
            c_srange_method(OBJECT(Call_method,v9268)->arg) );
          } 
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call_slot))
         V_CC = OBJECT(Call_slot,v9268)->selector->srange;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call_table))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call_array))
         { V_CC = ((OBJECT(Call_array,v9268)->test == _oid_(Kernel._float)) ?
            Kernel._float :
            Kernel._any );
          } 
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Definition))
         V_CC = Kernel._object;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Optimize._to_C))
         V_CC = OBJECT(to_C,v9268)->set_arg;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Optimize._to_protect))
         V_CC = OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(to_protect,v9268)->arg));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Update))
         V_CC = OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(Update,v9268)->value));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._If))
         V_CC = psort_any(_oid_(meet_class(OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(If,v9268)->arg)),OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(If,v9268)->other)))));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Handle))
         V_CC = psort_any(_oid_(meet_class(OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(ClaireHandle,v9268)->arg)),OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(ClaireHandle,v9268)->other)))));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Let))
         V_CC = OBJECT(ClaireClass,(*Optimize.c_sort)(OBJECT(Let,v9268)->arg));
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Optimize._to_CL))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Return))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._List))
         V_CC = Kernel._object;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Set))
         V_CC = Kernel._object;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Tuple))
         V_CC = Kernel._object;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Construct))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Gassign))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Super))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._For))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Exists))
         { V_CC = ((OBJECT(Exists,v9268)->other == CNULL) ?
            Kernel._any :
            Kernel._object );
          } 
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Iteration))
         V_CC = Kernel._object;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._And))
         V_CC = Kernel._boolean;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Or))
         V_CC = Kernel._boolean;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._While))
         V_CC = Kernel._any;
        else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Do))
         V_CC = OBJECT(ClaireClass,(*Optimize.c_sort)(GC_OID(last_list(OBJECT(Do,v9268)->args))));
        else close_exception(((general_error *) (*Core._general_error)(_string_("[internal] c_sort is not implemented for ~S"),
            _oid_(list::alloc(1,_oid_(OWNER(v9268)))))));
          } 
      else V_CC = ((Kernel._float == OWNER(v9268)) ?
        Kernel._float :
        psort_any(GC_OID((*Optimize.c_type)(v9268))) );
      Result= (ClaireClass *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 

ClaireClass * selector_psort_Call(Call *v9268)
{ { ClaireClass *Result ;
    { property * v5256 = v9268->selector;
      Result = (((v5256 == Core.base_I) || 
          (v5256 == Core.index_I)) ?
        Kernel._integer :
        ((v5256 == Optimize.anyObject_I) ?
          OBJECT(ClaireClass,(*(v9268->args))[1]) :
          ((v5256 == Optimize.object_I) ?
            OBJECT(ClaireClass,(*(v9268->args))[2]) :
            Kernel._any ) ) );
      } 
    return (Result);} 
  } 

int  c_status_any(OID v9268,list *v5252)
{ GC_BIND;
  { int Result = 0;
    if (INHERIT(OWNER(v9268),Language._Instruction))
     { if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Variable))
       { Result = (((OBJECT(Variable,v9268)->range == (NULL)) || 
            (not_any((*Optimize.gcsafe_ask)(GC_OID(_oid_(OBJECT(Variable,v9268)->range)))) == CTRUE)) ?
          ((contain_ask_list(v5252,v9268) == CTRUE) ?
            16 :
            (((OBJECT(Variable,v9268)->range != (NULL)) && 
                (_inf_equal_type(GC_OBJECT(ClaireType,OBJECT(Variable,v9268)->range),Kernel._float) == CTRUE)) ?
              2 :
              0 ) ) :
          32 );
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Error))
       Result = 32;
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Printf))
       { int  v8415;
        { int  v6592 = 0;
          { OID gc_local;
            ITERATE(v6595);
            bag *v6595_support;
            v6595_support = GC_OBJECT(list,OBJECT(Construct,v9268)->args);
            for (START(v6595_support); NEXT(v6595);)
            { GC_LOOP;
              { int  v6593 = c_status_any(v6595,v5252);
                v6592= c_or_integer(v6592,v6593);
                } 
              GC_UNLOOP;} 
            } 
          v8415 = v6592;
          } 
        Result = c_return_integer(0,v8415);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Construct))
       Result = 2;
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Definition))
       Result = 2;
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Call))
       { int  v15591 = c_status_property(OBJECT(Call,v9268)->selector);
        int  v15592;
        { int  v6596 = 0;
          { OID gc_local;
            ITERATE(v6598);
            bag *v6598_support;
            v6598_support = GC_OBJECT(list,OBJECT(Call,v9268)->args);
            for (START(v6598_support); NEXT(v6598);)
            { GC_LOOP;
              { int  v6597 = c_status_any(v6598,v5252);
                v6596= c_or_integer(v6596,v6597);
                } 
              GC_UNLOOP;} 
            } 
          v15592 = v6596;
          } 
        Result = c_return_integer(v15591,v15592);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._If))
       { int  v15591 = c_status_any(GC_OID(OBJECT(If,v9268)->arg),v5252);
        int  v15592 = c_status_any(GC_OID(OBJECT(If,v9268)->other),v5252);
        Result = c_return_integer(c_or_integer(v15591,v15592),c_status_any(GC_OID(OBJECT(If,v9268)->test),v5252));
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Do))
       { OID  v5265 = GC_OID(last_list(OBJECT(Do,v9268)->args));
        int  v15591 = c_status_any(v5265,v5252);
        int  v15592;
        { int  v6599 = 0;
          { OID gc_local;
            ITERATE(v6601);
            bag *v6601_support;
            v6601_support = GC_OBJECT(list,OBJECT(Do,v9268)->args);
            for (START(v6601_support); NEXT(v6601);)
            { GC_LOOP;
              if (equal(v6601,v5265) != CTRUE)
               { int  v6600 = c_status_any(v6601,v5252);
                v6599= c_or_integer(v6599,v6600);
                } 
              GC_UNLOOP;} 
            } 
          v15592 = v6599;
          } 
        Result = c_return_integer(v15591,v15592);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._For))
       Result = c_return_integer(c_status_any(GC_OID(OBJECT(Iteration,v9268)->arg),v5252),c_status_any(GC_OID(OBJECT(Iteration,v9268)->set_arg),v5252));
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Iteration))
       Result = c_return_integer(2,c_status_any(GC_OID(OBJECT(Iteration,v9268)->arg),v5252));
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Let))
       Result = c_return_integer(c_status_any(GC_OID(OBJECT(Let,v9268)->arg),v5252),c_status_any(GC_OID(OBJECT(Let,v9268)->value),v5252));
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Assign))
       Result = c_status_any(GC_OID(OBJECT(Assign,v9268)->arg),v5252);
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Gassign))
       Result = c_status_any(GC_OID(OBJECT(Gassign,v9268)->arg),v5252);
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._And))
       { int  v9377;
        { int  v6622 = 0;
          { OID gc_local;
            ITERATE(v6624);
            bag *v6624_support;
            v6624_support = GC_OBJECT(list,OBJECT(And,v9268)->args);
            for (START(v6624_support); NEXT(v6624);)
            { GC_LOOP;
              { int  v6623 = c_status_any(v6624,v5252);
                v6622= c_or_integer(v6622,v6623);
                } 
              GC_UNLOOP;} 
            } 
          v9377 = v6622;
          } 
        Result = c_return_integer(0,v9377);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Or))
       { int  v10337;
        { int  v6625 = 0;
          { OID gc_local;
            ITERATE(v6627);
            bag *v6627_support;
            v6627_support = GC_OBJECT(list,OBJECT(Or,v9268)->args);
            for (START(v6627_support); NEXT(v6627);)
            { GC_LOOP;
              { int  v6626 = c_status_any(v6627,v5252);
                v6625= c_or_integer(v6625,v6626);
                } 
              GC_UNLOOP;} 
            } 
          v10337 = v6625;
          } 
        Result = c_return_integer(0,v10337);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Cast))
       Result = c_status_any(GC_OID(OBJECT(Cast,v9268)->arg),v5252);
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Super))
       { int  v15591 = c_status_property(OBJECT(Super,v9268)->selector);
        int  v15592;
        { int  v6628 = 0;
          { OID gc_local;
            ITERATE(v6631);
            bag *v6631_support;
            v6631_support = GC_OBJECT(list,OBJECT(Super,v9268)->args);
            for (START(v6631_support); NEXT(v6631);)
            { GC_LOOP;
              { int  v6630 = c_status_any(v6631,v5252);
                v6628= c_or_integer(v6628,v6630);
                } 
              GC_UNLOOP;} 
            } 
          v15592 = v6628;
          } 
        Result = c_return_integer(v15591,v15592);
        } 
      else if (INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Case))
       { int  v6632 = 0;
        { OID gc_local;
          ITERATE(v6655);
          bag *v6655_support;
          v6655_support = GC_OBJECT(list,OBJECT(Case,v9268)->args);
          for (START(v6655_support); NEXT(v6655);)
          { GC_LOOP;
            { int  v6654 = c_status_any(v6655,v5252);
              v6632= c_or_integer(v6632,v6654);
              } 
            GC_UNLOOP;} 
          } 
        Result = v6632;
        } 
      else Result = ((INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._While)) ?
        c_return_integer(c_status_any(GC_OID(OBJECT(While,v9268)->arg),v5252),c_status_any(GC_OID(OBJECT(While,v9268)->test),v5252)) :
        ((INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Return)) ?
          c_status_any(GC_OID(OBJECT(Return,v9268)->arg),v5252) :
          ((INHERIT(OBJECT(ClaireObject,v9268)->isa,Language._Handle)) ?
            c_return_integer(c_or_integer(c_status_any(GC_OID(OBJECT(ClaireHandle,v9268)->arg),v5252),c_status_any(GC_OID(OBJECT(ClaireHandle,v9268)->other),v5252)),c_status_any(GC_OID(OBJECT(ClaireHandle,v9268)->test),v5252)) :
            ((INHERIT(OBJECT(ClaireObject,v9268)->isa,Kernel._unbound_symbol)) ?
              0 :
              (*Optimize.Cerror)(_string_("[internal] oops .. I forgot ~S"),
                _oid_(OWNER(v9268))) ) ) ) );
      } 
    else Result = ((Kernel._float == OWNER(v9268)) ?
      2 :
      ((Kernel._string == OWNER(v9268)) ?
        2 :
        ((INHERIT(OWNER(v9268),Kernel._function)) ?
          2 :
          ((INHERIT(OWNER(v9268),Core._Type)) ?
            2 :
            0 ) ) ) );
    GC_UNBIND; return (Result);} 
  } 

int  c_or_integer(int v5264,int v5265)
{ { int Result = 0;
    { int  v5244 = or_integer(v5264,v5265);
      if ((BCONTAIN(v5244,5)) && 
          ((!BCONTAIN(v5264,5)) || 
              (!BCONTAIN(v5265,5))))
       v5244= (v5244-exp2_integer(5));
      if ((BCONTAIN(v5244,6)) && 
          ((!BCONTAIN(v5264,6)) || 
              (!BCONTAIN(v5265,6))))
       v5244= (v5244-exp2_integer(6));
      Result = v5244;
      } 
    return (Result);} 
  } 

int  c_or_list(list *v5252)
{ { int Result = 0;
    { int  v5244 = 0;
      { ITERATE(v5264);
        for (START(v5252); NEXT(v5264);)
        v5244= c_or_integer(v5244,v5264);
        } 
      Result = v5244;
      } 
    return (Result);} 
  } 

int  status_I_restriction(restriction *v9268)
{ GC_BIND;
  { int Result = 0;
    if (Kernel._method == v9268->isa)
     { if (CLREAD(method,v9268,status) != (CNULL))
       Result = CLREAD(method,v9268,status);
      else if (CLREAD(method,v9268,formula) == (NULL))
       { (CLREAD(method,v9268,status) = 0);
        Result = 0;
        } 
      else { (CLREAD(method,v9268,status) = 0);
          (CLREAD(method,v9268,status) = c_status_any(GC_OID(CLREAD(method,v9268,formula)->body),GC_OBJECT(list,CLREAD(method,v9268,formula)->vars)));
          Result = CLREAD(method,v9268,status);
          } 
        } 
    else Result = 0;
      GC_UNBIND; return (Result);} 
  } 

int  c_return_integer(int v5264,int v5265)
{ { int Result = 0;
    { int  v5244 = or_integer(v5264,v5265);
      if ((!BCONTAIN(v5264,5)) && 
          (BCONTAIN(v5265,5)))
       v5244= (v5244-exp2_integer(5));
      if ((!BCONTAIN(v5264,4)) && 
          (BCONTAIN(v5265,4)))
       v5244= (v5244-exp2_integer(4));
      Result = v5244;
      } 
    return (Result);} 
  } 

int  c_status_property(property *v9268)
{ { int Result = 0;
    { int  v5258 = ((stable_ask_relation(v9268) == CTRUE) ?
        0 :
        (*Language.bit_vector)(1,
          2,
          3) );
      { ITERATE(v5264);
        for (START(v9268->restrictions); NEXT(v5264);)
        { if (((*Kernel.srange)(v5264) != _oid_(Kernel._integer)) && 
              (((*Kernel.srange)(v5264) != _oid_(Kernel._object)) && 
                ((*Kernel.srange)(v5264) != _oid_(Kernel._any))))
           v5258= c_or_integer(v5258,2);
          if (Kernel._method == OBJECT(ClaireObject,v5264)->isa)
           v5258= c_or_integer(v5258,status_I_restriction(OBJECT(restriction,v5264)));
          } 
        } 
      Result = v5258;
      } 
    return (Result);} 
  } 

OID  showstatus_method2(method *v5253)
{ { OID Result = 0;
    { list * v15405 = list::alloc(6,_string_("NEW_ALLOC"),
        _string_("BAG_UPDATE"),
        _string_("SLOT_UPDATE"),
        _string_("RETURN_ARG"),
        _string_("SAFE_RESULT"),
        _string_("SAFE_GC"));
      list * v5252 = list::empty();
      int  v5259 = v5253->status;
      { int  v5249 = 1;
        int  v6659 = 6;
        { OID gc_local;
          while ((v5249 <= v6659))
          { // HOHO, GC_LOOP not needed !
            if (BCONTAIN(v5259,v5249))
             v5252= v5252->addFast((*(v15405))[v5249]);
            ++v5249;
            } 
          } 
        } 
      Result = _oid_(v5252);
      } 
    return (Result);} 
  } 

void  s_test_method2(method *v5253)
{ GC_BIND;
  { lambda * v15453 = GC_OBJECT(lambda,v5253->formula);
    int  v8123 = c_status_any(GC_OID(v15453->body),GC_OBJECT(list,v15453->vars));
    tformat_string("status(~S) := ~S \n",0,list::alloc(2,_oid_(v5253),v8123));
    (v5253->status = v8123);
    showstatus_method2(v5253);
    } 
  GC_UNBIND;} 

ClaireBoolean * legal_ask_module(module *v9268,OID v5264)
{ GC_BIND;
  { ClaireBoolean *Result ;
    if ((v5264 == _oid_(Optimize.object_I)) || 
        (v5264 == _oid_(Optimize.anyObject_I)))
     Result = CTRUE;
    else if (Optimize.OPT->legal_modules->length != 0)
     { if ((contain_ask_set(Optimize.OPT->legal_modules,_oid_(v9268)) != CTRUE) && 
          ((Kernel._method == OWNER(v5264)) && ((OBJECT(method,v5264)->inline_ask == CFALSE) || 
              (Optimize.compiler->inline_ask != CTRUE))))
       { tformat_string("legal_modules = ~S\n",0,list::alloc(1,GC_OID(_oid_(Optimize.OPT->legal_modules))));
        tformat_string("---- ERROR: ~S implies using ~S !\n\n",0,list::alloc(2,v5264,_oid_(v9268)));
        Result = CFALSE;
        } 
      else Result = CTRUE;
        } 
    else { GC_OBJECT(set,Optimize.OPT->need_modules)->addFast(_oid_(v9268));
        Result = CTRUE;
        } 
      GC_UNBIND; return (Result);} 
  } 

OID  legal_ask_environment(ClaireEnvironment *v9268,OID v5264)
{ return (Kernel.ctrue);} 

OID  c_register_object(ClaireObject *v9268)
{ GC_BIND;
  { OID Result = 0;
    { OID  v5264 = GC_OID(get_module_object(v9268));
      if (v5264 != _oid_(ClEnv))
       Result = (*Optimize.legal_ask)(v5264,
        _oid_(v9268));
      else Result = Kernel.ctrue;
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_register_property(property *v9268)
{ GC_BIND;
  { OID Result = 0;
    { module * v5253 = ClEnv->module_I;
      OID  v15437 = GC_OID(get_module_object(v9268));
      if (((v15437 == _oid_(claire.it)) || 
            (v15437 == _oid_(v5253))) && 
          (Optimize.OPT->objects->memq(_oid_(v9268)) != CTRUE))
       GC_OBJECT(set,Optimize.OPT->properties)->addFast(_oid_(v9268));
      Result = c_register_object(v9268);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  selector_register_property(property *v9268)
{ c_register_property(v9268);
  if (allocate_ask_property(v9268) == Kernel.ctrue)
   (Optimize.OPT->allocation = CTRUE);
  return (_oid_(v9268));} 

OID  allocate_ask_property(property *v9268)
{ { OID Result = 0;
    { ITERATE(v5264);
      Result= _oid_(CFALSE);
      for (START(v9268->restrictions); NEXT(v5264);)
      if (Kernel._slot == OBJECT(ClaireObject,v5264)->isa)
       { if ((not_any(_oid_((INHERIT(OBJECT(restriction,v5264)->range->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(OBJECT(restriction,v5264)->range))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(OBJECT(restriction,v5264)->range)))))) == CTRUE) && 
            (OBJECT(slot,v5264)->srange != Kernel._any))
         { Result = Kernel.ctrue;
          break;} 
        else if ((not_any(_oid_((INHERIT(OBJECT(restriction,v5264)->range->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(OBJECT(restriction,v5264)->range))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(OBJECT(restriction,v5264)->range)))))) == CTRUE) && 
            (gcsafe_ask_class(domain_I_restriction(OBJECT(restriction,v5264))) != CTRUE))
         { Result = 0;
          break;} 
        } 
      else if (Kernel._method == OBJECT(ClaireObject,v5264)->isa)
       { if ((BCONTAIN(status_I_restriction(OBJECT(restriction,v5264)),1)) || 
            (((c_srange_method(OBJECT(method,v5264)) != Kernel._integer) && 
                ((c_srange_method(OBJECT(method,v5264)) != Kernel._object) && 
                  (c_srange_method(OBJECT(method,v5264)) != Kernel._any))) || 
              (not_any(_oid_((INHERIT(OBJECT(restriction,v5264)->range->isa,Kernel._class) ? (ClaireObject *) gcsafe_ask_class((ClaireClass *) OBJECT(ClaireClass,_oid_(OBJECT(restriction,v5264)->range))) :  (ClaireObject *)  gcsafe_ask_type((ClaireType *) OBJECT(ClaireType,_oid_(OBJECT(restriction,v5264)->range)))))) == CTRUE)))
         { Result = Kernel.ctrue;
          break;} 
        } 
      } 
    return (Result);} 
  } 

ClaireBoolean * stable_ask_relation(ClaireRelation *v9268)
{ GC_BIND;
  { OID  v5253 = GC_OID(get_module_object(v9268));
    if ((v9268->open == 2) && 
        ((contain_ask_set(Optimize.OPT->legal_modules,v5253) == CTRUE) || 
            (v5253 == _oid_(ClEnv))))
     (v9268->open = 1);
    } 
  { ClaireBoolean *Result ;
    Result = ((v9268->open <= 1) ? CTRUE : ((v9268->open == 4) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 

OID  get_module_object(ClaireObject *v9268)
{ return (_oid_(defined_symbol(OBJECT(symbol,(*Kernel.name)(_oid_(v9268))))));} 

OID  known_I_listargs(listargs *v5252)
{ GC_BIND;
  GC_OBJECT(set,Optimize.OPT->to_remove)->addFast(_oid_(Reader.known_I));
  { OID Result = 0;
    { OID gc_local;
      ITERATE(v5258);
      Result= _oid_(CFALSE);
      for (START(v5252); NEXT(v5258);)
      { GC_LOOP;
        GC_OBJECT(set,Optimize.OPT->knowns)->addFast(v5258);
        GC_UNLOOP;} 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  oload_module(module *v5253)
{ load_module(v5253);
  return (turbo_module(v5253));} 

OID  oload_string(char *v5246)
{ load_string(v5246);
  return (turbo_module(ClEnv->module_I));} 

OID  turbo_module(module *v5253)
{ GC_RESERVE(5);  // v3.0.55 optim !
  { OID Result = 0;
    { int  v15764 = ClEnv->verbose;
      (Optimize.OPT->online_ask = CTRUE);
      (ClEnv->verbose = 0);
      { OID gc_local;
        ITERATE(v5264);
        bag *v5264_support;
        v5264_support = Kernel._method->instances;
        for (START(v5264_support); NEXT(v5264);)
        { GC_LOOP;
          if ((OBJECT(restriction,v5264)->module_I == v5253) && 
              ((OBJECT(method,v5264)->inline_ask != CTRUE) && 
                (OBJECT(method,v5264)->formula != (NULL))))
           { lambda * v5252 = GC_OBJECT(lambda,OBJECT(method,v5264)->formula);
            OID  v5265 = GC_OID(v5252->body);
            { ClaireHandler c_handle = ClaireHandler();
              if ERROR_IN 
              { { if (OBJECT(restriction,v5264)->range == Kernel._any)
                   update_property(Kernel.range,
                    OBJECT(ClaireObject,v5264),
                    5,
                    Kernel._object,
                    GC_OID((*Optimize.c_type)(v5265)));
                  (v5252->body = (*Optimize.c_code)(v5265,
                    _oid_(Kernel._any)));
                  (Language._starvariable_index_star->value= (v5252->vars->length+1));
                  (v5252->body = lexical_build_any(GC_OID(v5252->body),GC_OBJECT(list,v5252->vars),Language._starvariable_index_star->value));
                  (v5252->dimension = Language._starvariable_index_star->value);
                  } 
                ClEnv->cHandle--;} 
              else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
              { c_handle.catchIt();{ (v5252->body = v5265);
                  ;} 
                } 
              else PREVIOUS_HANDLER;} 
            } 
          GC_UNLOOP;} 
        } 
      (Optimize.OPT->online_ask = CFALSE);
      (ClEnv->verbose = v15764);
      Result = Kernel.ctrue;
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  stats_meta_OPT(meta_OPT *v5264)
{ return (Core.nil->value);} 

