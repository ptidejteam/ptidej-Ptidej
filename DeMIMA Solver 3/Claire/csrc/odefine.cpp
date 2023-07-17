/***** CLAIRE Compilation of file d:\claire\v3.2\src\compile\odefine.cl 
         [version 3.2.52 / safety 5] Sat Sep 14 18:03:05 2002 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <Optimize.h>
ClaireType * c_type_List_Optimize(List *v9268)
{ GC_BIND;
  ;{ ClaireType *Result ;
    if (v9268->of != (NULL))
     Result = param_I_class(Kernel._list,v9268->of);
    else { OID  v8003 = _oid_(Kernel.emptySet);
        { OID gc_local;
          ITERATE(v13275);
          for (START(v9268->args); NEXT(v13275);)
          { GC_LOOP;
            if (boolean_I_any(v8003) == CTRUE)
             v8003= _oid_(meet_class(OBJECT(ClaireClass,v8003),class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))))));
            else v8003= _oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275)))));
              GC_UNLOOP;} 
          } 
        Result = nth_class1(Kernel._list,OBJECT(ClaireType,v8003));
        } 
      GC_UNBIND; return (Result);} 
  } 

OID  c_code_List_Optimize(List *v9268)
{ GC_BIND;
  (Optimize.OPT->allocation = CTRUE);
  { OID Result = 0;
    { List * v5264;
      { { List * v2072 = ((List *) GC_OBJECT(List,new_object_class(Language._List)));
          { Construct * v8765; 
            list * v8766;
            v8765 = v2072;
            { bag *v_list; OID v_val;
              OID v13275,CLcount;
              v_list = GC_OBJECT(list,v9268->args);
               v8766 = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v13275 = (*(v_list))[CLcount];
                v_val = c_gc_I_any2(GC_OID((*Optimize.c_code)(v13275,
                  _oid_(Kernel._any))),GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))));
                
                (*((list *) v8766))[CLcount] = v_val;} 
              } 
            (v8765->args = v8766);} 
          add_I_property(Kernel.instances,Language._List,11,_oid_(v2072));
          v5264 = v2072;
          } 
        GC_OBJECT(List,v5264);} 
      if (v9268->of != (NULL))
       { { ClaireBoolean * g0285I;
          { ClaireBoolean *v_or;
            { v_or = ((Optimize.compiler->safety > 4) ? CTRUE : CFALSE);
              if (v_or == CTRUE) g0285I =CTRUE; 
              else { v_or = ((equal(_oid_(v9268->of),_oid_(Kernel.emptySet)) == CTRUE) ? CTRUE : CFALSE);
                if (v_or == CTRUE) g0285I =CTRUE; 
                else { { OID  v6431;
                    { OID gc_local;
                      ITERATE(v13275);
                      v6431= _oid_(CFALSE);
                      bag *v13275_support;
                      v13275_support = GC_OBJECT(list,v9268->args);
                      for (START(v13275_support); NEXT(v13275);)
                      { GC_LOOP;
                        if (_inf_equal_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))),GC_OBJECT(ClaireType,v9268->of)) != CTRUE)
                         { v6431 = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    v_or = not_any(v6431);
                    } 
                  if (v_or == CTRUE) g0285I =CTRUE; 
                  else g0285I = CFALSE;} 
                } 
              } 
            } 
          
          if (g0285I == CTRUE) { (v5264->of = v9268->of);
              Result = _oid_(v5264);
              } 
            else { warn_void();
            { list * v7392;
              { OID v_bag;
                GC_ANY(v7392= list::empty(Kernel.emptySet));
                { { list * v10396;{ bag *v_list; OID v_val;
                      OID v13275,CLcount;
                      v_list = GC_OBJECT(list,v9268->args);
                       v10396 = v_list->clone();
                      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                      { v13275 = (*(v_list))[CLcount];
                        v_val = (*Optimize.c_type)(v13275);
                        
                        (*((list *) v10396))[CLcount] = v_val;} 
                      } 
                    
                    v_bag=_oid_(v10396);} 
                  GC_OID(v_bag);} 
                ((list *) v7392)->addFast(v_bag);
                ((list *) v7392)->addFast(GC_OID(_oid_(v9268->of)));} 
              tformat_string("unsafe typed list: ~S not in ~S\n",2,v7392);
              } 
            { OID  v9314;
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Core.check_in);
                (v2072->args = list::alloc(3,_oid_(v5264),
                  _oid_(Kernel._list),
                  GC_OID(_oid_(v9268->of))));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v9314 = _oid_(v2072);
                } 
              Result = (*Optimize.c_code)(v9314,
                _oid_(Kernel._list));
              } 
            } 
          } 
        } 
      else Result = _oid_(v5264);
        } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_Set_Optimize(Set *v9268)
{ GC_BIND;
  ;{ ClaireType *Result ;
    if (v9268->of != (NULL))
     Result = param_I_class(Kernel._set,v9268->of);
    else { OID  v8003 = _oid_(Kernel.emptySet);
        { OID gc_local;
          ITERATE(v13275);
          for (START(v9268->args); NEXT(v13275);)
          { GC_LOOP;
            if (boolean_I_any(v8003) == CTRUE)
             v8003= _oid_(meet_class(OBJECT(ClaireClass,v8003),class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))))));
            else v8003= _oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275)))));
              GC_UNLOOP;} 
          } 
        Result = nth_class1(Kernel._set,OBJECT(ClaireType,v8003));
        } 
      GC_UNBIND; return (Result);} 
  } 

OID  c_code_Set_Optimize(Set *v9268)
{ GC_BIND;
  (Optimize.OPT->allocation = CTRUE);
  { OID Result = 0;
    { Set * v5264;
      { { Set * v2072 = ((Set *) GC_OBJECT(Set,new_object_class(Language._Set)));
          { Construct * v8792; 
            list * v8793;
            v8792 = v2072;
            { bag *v_list; OID v_val;
              OID v13275,CLcount;
              v_list = GC_OBJECT(list,v9268->args);
               v8793 = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v13275 = (*(v_list))[CLcount];
                v_val = c_gc_I_any2(GC_OID((*Optimize.c_code)(v13275,
                  _oid_(Kernel._any))),GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))));
                
                (*((list *) v8793))[CLcount] = v_val;} 
              } 
            (v8792->args = v8793);} 
          add_I_property(Kernel.instances,Language._Set,11,_oid_(v2072));
          v5264 = v2072;
          } 
        GC_OBJECT(Set,v5264);} 
      if (v9268->of != (NULL))
       { { ClaireBoolean * g0292I;
          { ClaireBoolean *v_or;
            { v_or = ((Optimize.compiler->safety > 4) ? CTRUE : CFALSE);
              if (v_or == CTRUE) g0292I =CTRUE; 
              else { v_or = ((equal(_oid_(v9268->of),_oid_(Kernel.emptySet)) == CTRUE) ? CTRUE : CFALSE);
                if (v_or == CTRUE) g0292I =CTRUE; 
                else { { OID  v571;
                    { OID gc_local;
                      ITERATE(v13275);
                      v571= _oid_(CFALSE);
                      bag *v13275_support;
                      v13275_support = GC_OBJECT(list,v9268->args);
                      for (START(v13275_support); NEXT(v13275);)
                      { GC_LOOP;
                        if (_inf_equal_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))),GC_OBJECT(ClaireType,v9268->of)) != CTRUE)
                         { v571 = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    v_or = not_any(v571);
                    } 
                  if (v_or == CTRUE) g0292I =CTRUE; 
                  else g0292I = CFALSE;} 
                } 
              } 
            } 
          
          if (g0292I == CTRUE) { (v5264->of = v9268->of);
              Result = _oid_(v5264);
              } 
            else { warn_void();
            { list * v1532;
              { OID v_bag;
                GC_ANY(v1532= list::empty(Kernel.emptySet));
                { { list * v10424;{ bag *v_list; OID v_val;
                      OID v13275,CLcount;
                      v_list = GC_OBJECT(list,v9268->args);
                       v10424 = v_list->clone();
                      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                      { v13275 = (*(v_list))[CLcount];
                        v_val = (*Optimize.c_type)(v13275);
                        
                        (*((list *) v10424))[CLcount] = v_val;} 
                      } 
                    
                    v_bag=_oid_(v10424);} 
                  GC_OID(v_bag);} 
                ((list *) v1532)->addFast(v_bag);
                ((list *) v1532)->addFast(GC_OID(_oid_(v9268->of)));} 
              tformat_string("unsafe typed set: ~S not in ~S\n",2,v1532);
              } 
            { OID  v3454;
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Core.check_in);
                (v2072->args = list::alloc(3,_oid_(v5264),
                  _oid_(Kernel._set),
                  GC_OID(_oid_(v9268->of))));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v3454 = _oid_(v2072);
                } 
              Result = (*Optimize.c_code)(v3454,
                _oid_(Kernel._set));
              } 
            } 
          } 
        } 
      else Result = _oid_(v5264);
        } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_Tuple_Optimize(Tuple *v9268)
{ GC_BIND;
  { ClaireType *Result ;
    { list * v4415;
      { { bag *v_list; OID v_val;
          OID v5264,CLcount;
          v_list = v9268->args;
           v4415 = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v5264 = (*(v_list))[CLcount];
            v_val = (*Optimize.c_type)(v5264);
            
            (*((list *) v4415))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,v4415);} 
      Result = tuple_I_list(v4415);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_code_Tuple_Optimize(Tuple *v9268)
{ GC_BIND;
  (Optimize.OPT->allocation = CTRUE);
  { OID Result = 0;
    { Tuple * v2072 = ((Tuple *) GC_OBJECT(Tuple,new_object_class(Language._Tuple)));
      { Construct * v8802; 
        list * v8803;
        v8802 = v2072;
        { bag *v_list; OID v_val;
          OID v13275,CLcount;
          v_list = GC_OBJECT(list,v9268->args);
           v8803 = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v13275 = (*(v_list))[CLcount];
            v_val = c_gc_I_any2(GC_OID((*Optimize.c_code)(v13275,
              _oid_(Kernel._any))),GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v13275))));
            
            (*((list *) v8803))[CLcount] = v_val;} 
          } 
        (v8802->args = v8803);} 
      add_I_property(Kernel.instances,Language._Tuple,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_Definition_Optimize(Definition *v9268)
{ { ClaireType *Result ;
    if (_inf_equalt_class(v9268->arg,Kernel._exception) == CTRUE)
     Result = Kernel.emptySet;
    else Result = v9268->arg;
      return (Result);} 
  } 

OID  c_code_Definition_Optimize(Definition *v9268,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    { ClaireClass * v13254 = v9268->arg;
      Variable * v13274;
      { { int  v14114;
          { (Optimize.OPT->max_vars = (Optimize.OPT->max_vars+1));
            v14114 = 0;
            } 
          v13274 = Variable_I_symbol(OBJECT(symbol,Optimize._starname_star->value),v14114,v13254);
          } 
        GC_OBJECT(Variable,v13274);} 
      OID  v13275 = GC_OID(total_ask_class(v13254,GC_OBJECT(list,v9268->args)));
      if (v13254->open <= 0)
       close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
        _oid_(list::alloc(1,_oid_(v13254))))));
      if (boolean_I_any(v13275) == CTRUE)
       Result = (*Optimize.c_code)(v13275,
        _oid_(v5259));
      else { OID  v15075;
          { Let * v2072 = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
            (v2072->var = v13274);
            { Let * v9476; 
              OID  v9477;
              v9476 = v2072;
              { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                { Cast * v9478; 
                  OID  v9479;
                  v9478 = v2072;
                  { OID  v3496;
                    { { OID  v4457;
                        { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                          (v2072->selector = Core.new_I);
                          (v2072->args = list::alloc(1,_oid_(v13254)));
                          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                          v4457 = _oid_(v2072);
                          } 
                        v3496 = (*Optimize.c_code)(v4457,
                          _oid_(Kernel._object));
                        } 
                      GC_OID(v3496);} 
                    v9479 = c_gc_I_any1(v3496);
                    } 
                  (v9478->arg = v9479);} 
                (v2072->set_arg = v13254);
                add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                v9477 = _oid_(v2072);
                } 
              (v9476->value = v9477);} 
            { Let * v9484; 
              OID  v9485;
              v9484 = v2072;
              { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                store_object(v2072,
                  2,
                  Kernel._object,
                  analyze_I_class(v13254,_oid_(v13274),GC_OBJECT(list,v9268->args),list::empty()),
                  CFALSE);
                add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                v9485 = _oid_(v2072);
                } 
              (v9484->arg = v9485);} 
            add_I_property(Kernel.instances,Language._Let,11,_oid_(v2072));
            v15075 = _oid_(v2072);
            } 
          Result = (*Optimize.c_code)(v15075,
            _oid_(v5259));
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  total_ask_class(ClaireClass *v9268,list *v5252)
{ GC_BIND;
  { OID Result = 0;
    { list * v15468 = GC_OBJECT(list,OBJECT(list,(*Optimize.get_indexed)(_oid_(v9268))));
      int  v5254 = v15468->length;
      { ClaireBoolean * g0311I;
        { ClaireBoolean *v_and;
          { v_and = not_any(_oid_(Optimize.compiler->diet_ask));
            if (v_and == CFALSE) g0311I =CFALSE; 
            else { v_and = ((v5252->length == (v5254-1)) ? CTRUE : CFALSE);
              if (v_and == CFALSE) g0311I =CFALSE; 
              else { v_and = ((v9268->open == ClEnv->ephemeral) ? CTRUE : ((_inf_equalt_class(v9268,Kernel._exception) == CTRUE) ? CTRUE : CFALSE));
                if (v_and == CFALSE) g0311I =CFALSE; 
                else { v_and = ((v5254 <= 4) ? CTRUE : CFALSE);
                  if (v_and == CFALSE) g0311I =CFALSE; 
                  else { { OID  v13060;
                      { int  v5249 = 2;
                        int  v9505 = v5254;
                        { OID gc_local;
                          v13060= _oid_(CFALSE);
                          while ((v5249 <= v9505))
                          { // HOHO, GC_LOOP not needed !
                            if (((*Kernel.srange)((*(v15468))[v5249]) != _oid_(Kernel._integer)) && 
                                ((*Kernel.srange)((*(v15468))[v5249]) != _oid_(Kernel._any)))
                             { v13060 = Kernel.ctrue;
                              break;} 
                            ++v5249;
                            } 
                          } 
                        } 
                      v_and = not_any(v13060);
                      } 
                    if (v_and == CFALSE) g0311I =CFALSE; 
                    else g0311I = CTRUE;} 
                  } 
                } 
              } 
            } 
          } 
        
        if (g0311I == CTRUE) { OID  v13254;
            { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = ((v5252->length == 0) ?
                  Core.new_I :
                  Optimize.anyObject_I ));
                { Call * v9508; 
                  list * v9509;
                  v9508 = v2072;
                  { list * v15942;
                    { { bag *v_list; OID v_val;
                        OID v5264,CLcount;
                        v_list = v5252;
                         v15942 = v_list->clone();
                        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                        { v5264 = (*(v_list))[CLcount];
                          v_val = c_gc_I_any1(GC_OID((*Optimize.c_code)((*(OBJECT(bag,(*Core.args)(v5264))))[2],
                            _oid_(Kernel._any))));
                          
                          (*((list *) v15942))[CLcount] = v_val;} 
                        } 
                      GC_OBJECT(list,v15942);} 
                    v9509 = cons_any(_oid_(v9268),v15942);
                    } 
                  (v9508->args = v9509);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v13254 = _oid_(v2072);
                } 
              GC_OID(v13254);} 
            OID  v5253 = GC_OID(_oid_(_at_property1(Kernel.close,v9268)));
            if (_inf_equal_type(v9268,Kernel._exception) != CTRUE)
             (Optimize.OPT->allocation = CTRUE);
            if (v5252->length == 0)
             v13254= GC_OID((*Optimize.c_code)(v13254));
            if (boolean_I_any(v5253) == CTRUE)
             { Call_method1 * v2072 = ((Call_method1 *) GC_OBJECT(Call_method1,new_object_class(Language._Call_method1)));
              update_property(Kernel.arg,
                v2072,
                2,
                Kernel._object,
                v5253);
              (v2072->args = list::alloc(1,v13254));
              add_I_property(Kernel.instances,Language._Call_method1,11,_oid_(v2072));
              Result = _oid_(v2072);
              } 
            else Result = v13254;
              } 
          else Result = Kernel.cfalse;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  analyze_I_class(ClaireClass *v5243,OID v9268,list *v13263,list *v15468)
{ GC_RESERVE(15);  // v3.0.55 optim !
  { OID Result = 0;
    { ClaireBoolean * v15097 = ((v5243->open != 4) ? ((boolean_I_any(_oid_(v15468)) != CTRUE) ? ((Optimize.compiler->class2file_ask != CTRUE) ? CTRUE: CFALSE): CFALSE): CFALSE);
      list * v5258;
      { { bag *v_list; OID v_val;
          OID v5264,CLcount;
          v_list = v13263;
           v5258 = v_list->clone();
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v5264 = (*(v_list))[CLcount];
            { OID  v5256 = (*(OBJECT(Call,v5264)->args))[1];
              OID  v5265 = (*(OBJECT(Call,v5264)->args))[2];
              ClaireObject * v5259 = GC_OBJECT(ClaireObject,_at_property1(OBJECT(property,v5256),v5243));
              ClaireBoolean * v14222 = (((*Kernel.open)(v5256) == 0) ? ((Kernel._slot == v5259->isa) ? CTRUE: CFALSE): CFALSE);
              GC__ANY(v15468 = v15468->addFast(v5256), 4);
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = ((v14222 == CTRUE) ?
                  Kernel.put :
                  Core.write ));
                { Call * v9511; 
                  list * v9512;
                  v9511 = v2072;
                  { OID v_bag;
                    GC_ANY(v9512= list::empty(Kernel.emptySet));
                    ((list *) v9512)->addFast(v5256);
                    ((list *) v9512)->addFast(v9268);
                    { if ((v14222 != CTRUE) || 
                          (_inf_equal_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Optimize.c_type)(v5265))),GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Kernel.range)(_oid_(v5259))))) == CTRUE))
                       v_bag = v5265;
                      else v_bag = c_check_any(GC_OID((*Optimize.c_code)(v5265,
                          _oid_(Kernel._any))),GC_OID((*Optimize.c_code)(GC_OID((*Kernel.range)(_oid_(v5259))),
                          _oid_(Kernel._type))));
                        GC_OID(v_bag);} 
                    ((list *) v9512)->addFast(v_bag);} 
                  (v9511->args = v9512);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v_val = _oid_(v2072);
                } 
              } 
            
            (*((list *) v5258))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,v5258);} 
      if (v15097 == CTRUE)
       { { OID  v2441;
          { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.add);
            (v2072->args = list::alloc(3,_oid_(Kernel.instances),
              _oid_(v5243),
              v9268));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v2441 = _oid_(v2072);
            } 
          v5258 = v5258->addFast(v2441);
          } 
        GC_OBJECT(list,v5258);} 
      if (Optimize.compiler->class2file_ask != CTRUE)
       { OID gc_local;
        ITERATE(v5259);
        bag *v5259_support;
        v5259_support = GC_OBJECT(list,OBJECT(bag,(*Optimize.get_indexed)(_oid_(v5243))));
        for (START(v5259_support); NEXT(v5259);)
        { GC_LOOP;
          { property * v5256 = OBJECT(restriction,v5259)->selector;
            OID  v5263 = GC_OID(OBJECT(slot,v5259)->DEFAULT);
            { ClaireBoolean * g0319I;
              { ClaireBoolean *v_and;
                { v_and = known_ask_any(v5263);
                  if (v_and == CFALSE) g0319I =CFALSE; 
                  else { { OID  v8160;
                      if (multi_ask_any(_oid_(v5256)) == CTRUE)
                       v8160 = v5263;
                      else v8160 = Kernel.ctrue;
                        v_and = boolean_I_any(v8160);
                      } 
                    if (v_and == CFALSE) g0319I =CFALSE; 
                    else { v_and = not_any(_oid_(v15468->memq(_oid_(v5256))));
                      if (v_and == CFALSE) g0319I =CFALSE; 
                      else { v_and = ((v5256->inverse != (NULL)) ? CTRUE : ((v5256->if_write != CNULL) ? CTRUE : (((OBJECT(slot,v5259)->srange != Kernel._object) && 
                            ((OBJECT(slot,v5259)->srange != Kernel._float) && 
                              (!INHERIT(OWNER(v5263),Kernel._integer)))) ? CTRUE : CFALSE)));
                        if (v_and == CFALSE) g0319I =CFALSE; 
                        else g0319I = CTRUE;} 
                      } 
                    } 
                  } 
                } 
              
              if (g0319I == CTRUE) { OID  v3776;
                  if (designated_ask_any(v5263) == CTRUE)
                   v3776 = v5263;
                  else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                      (v2072->selector = Kernel.DEFAULT);
                      { Call * v9537; 
                        list * v9538;
                        v9537 = v2072;
                        { OID v_bag;
                          GC_ANY(v9538= list::empty(Kernel.emptySet));
                          { { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                              { Cast * v9539; 
                                OID  v9540;
                                v9539 = v2072;
                                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                                  (v2072->selector = Core._at);
                                  (v2072->args = list::alloc(2,_oid_(v5256),_oid_(v5243)));
                                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                                  v9540 = _oid_(v2072);
                                  } 
                                (v9539->arg = v9540);} 
                              (v2072->set_arg = Kernel._slot);
                              add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                              v_bag = _oid_(v2072);
                              } 
                            GC_OID(v_bag);} 
                          ((list *) v9538)->addFast(v_bag);} 
                        (v9537->args = v9538);} 
                      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                      v3776 = _oid_(v2072);
                      } 
                    { { OID  v12965;
                      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Core.write);
                        (v2072->args = list::alloc(3,_oid_(v5256),
                          v9268,
                          v3776));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v12965 = _oid_(v2072);
                        } 
                      v5258 = v5258->addFast(v12965);
                      } 
                     GC__ANY(v5258, 7);} 
                  } 
                } 
            } 
          GC_UNLOOP;} 
        } 
      { OID  v5253 = GC_OID(_oid_(_at_property1(Kernel.close,v5243)));
        { OID  v13926;
          if (boolean_I_any(v5253) == CTRUE)
           { Call_method1 * v2072 = ((Call_method1 *) GC_OBJECT(Call_method1,new_object_class(Language._Call_method1)));
            update_property(Kernel.arg,
              v2072,
              2,
              Kernel._object,
              v5253);
            (v2072->args = list::alloc(1,v9268));
            add_I_property(Kernel.instances,Language._Call_method1,11,_oid_(v2072));
            v13926 = _oid_(v2072);
            } 
          else v13926 = v9268;
            v5258 = v5258->addFast(v13926);
          } 
        } 
      Result = _oid_(v5258);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_code_Defobj_Optimize(Defobj *v9268,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    { ClaireBoolean * v13253 = Optimize.OPT->allocation;
      ClaireClass * v13254 = v9268->arg;
      OID  v5255 = get_symbol(v9268->ident);
      OID  v13274;
      { if ((v5255 != CNULL) && 
            (!INHERIT(OWNER(v5255),Core._global_variable)))
         v13274 = v5255;
        else { Variable * v11170;{ int  v15848;
              { (Optimize.OPT->max_vars = (Optimize.OPT->max_vars+1));
                v15848 = 0;
                } 
              v11170 = Variable_I_symbol(OBJECT(symbol,Optimize._starname_star->value),v15848,v13254);
              } 
            
            v13274=_oid_(v11170);} 
          GC_OID(v13274);} 
      Call * v2005;
      { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = Optimize.object_I);
          (v2072->args = list::alloc(2,_oid_(v9268->ident),_oid_(v13254)));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          v2005 = v2072;
          } 
        GC_OBJECT(Call,v2005);} 
      OID  v2006 = GC_OID(analyze_I_class(v13254,v13274,GC_OBJECT(list,v9268->args),list::alloc(1,_oid_(Kernel.name))));
      OID  v13275;
      if (!INHERIT(OWNER(v13274),Language._Variable))
       { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
        store_object(v2072,
          2,
          Kernel._object,
          (*Kernel.cons)(_oid_(v2005),
            v2006),
          CFALSE);
        add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
        v13275 = _oid_(v2072);
        } 
      else { Let * v2072 = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
          store_object(v2072,
            2,
            Kernel._object,
            v13274,
            CFALSE);
          (v2072->value = _oid_(v2005));
          { Let * v9545; 
            OID  v9567;
            v9545 = v2072;
            { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
              store_object(v2072,
                2,
                Kernel._object,
                v2006,
                CFALSE);
              add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
              v9567 = _oid_(v2072);
              } 
            (v9545->arg = v9567);} 
          add_I_property(Kernel.instances,Language._Let,11,_oid_(v2072));
          v13275 = _oid_(v2072);
          } 
        if (v13254->open <= 0)
       close_exception(((general_error *) (*Core._general_error)(_string_("[105] cannot instantiate ~S"),
        _oid_(list::alloc(1,_oid_(v13254))))));
      if (v5255 != CNULL)
       { if (contain_ask_list(Optimize.OPT->objects,v5255) != CTRUE)
         { GC_OBJECT(list,Optimize.OPT->objects)->addFast(v5255);
          (*Optimize.c_register)(v5255);
          } 
        } 
      else { warn_void();
          tformat_string("~S is unknown\n",2,list::alloc(1,_oid_(v9268->ident)));
          } 
        v13275= GC_OID((*Optimize.c_code)(v13275,
        _oid_(v5259)));
      if (_inf_equal_type(v9268->arg,Kernel._exception) == CTRUE)
       (Optimize.OPT->allocation = v13253);
      Result = v13275;
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  c_code_Defclass_Optimize(Defclass *v9268,ClaireClass *v5259)
{ GC_BIND;
  { OID Result = 0;
    { symbol * v10312 = v9268->ident;
      OID  v5255 = get_symbol(v10312);
      Call * v4985;
      { ClaireObject *V_CC ;
        if (v5255 != CNULL)
         { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
          (v2072->selector = Core.class_I);
          (v2072->args = list::alloc(2,_oid_(v10312),_oid_(v9268->arg)));
          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
          V_CC = v2072;
          } 
        else close_exception(((general_error *) (*Core._general_error)(_string_("[internal] cannot compile unknown class ~S"),
            _oid_(list::alloc(1,_oid_(v10312))))));
          v4985= (Call *) V_CC;} 
      Do * v13275;
      { { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
          { Do * v9568; 
            list * v9569;
            v9568 = v2072;
            { list * v8066;
              { { list * v9027;
                  { { bag *v_list; OID v_val;
                      OID v5264,CLcount;
                      v_list = GC_OBJECT(list,v9268->args);
                       v9027 = v_list->clone();
                      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                      { v5264 = (*(v_list))[CLcount];
                        { OID  v5263 = CNULL;
                          if (INHERIT(OWNER(v5264),Language._Call))
                           { v5263= (*(OBJECT(Call,v5264)->args))[2];
                            v5264= (*(OBJECT(Call,v5264)->args))[1];
                            } 
                          { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                            (v2072->selector = Kernel.add_slot);
                            (v2072->args = list::alloc(5,v5255,
                              _oid_(make_a_property_any(_oid_(OBJECT(Variable,v5264)->pname))),
                              GC_OID((*Kernel.range)(v5264)),
                              v5263,
                              0));
                            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                            v_val = _oid_(v2072);
                            } 
                          } 
                        
                        (*((list *) v9027))[CLcount] = v_val;} 
                      } 
                    GC_OBJECT(list,v9027);} 
                  list * v9988;
                  if (v9268->params->length != 0)
                   { OID v_bag;
                    GC_ANY(v9988= list::empty(Kernel.emptySet));
                    { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Kernel.put);
                        (v2072->args = list::alloc(3,_oid_(Kernel.params),
                          v5255,
                          GC_OID(_oid_(v9268->params))));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v_bag = _oid_(v2072);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) v9988)->addFast(v_bag);} 
                  else v9988 = list::empty();
                    v8066 = append_list(v9027,v9988);
                  } 
                GC_OBJECT(list,v8066);} 
              v9569 = cons_any(_oid_(v4985),v8066);
              } 
            (v9568->args = v9569);} 
          add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
          v13275 = v2072;
          } 
        GC_OBJECT(Do,v13275);} 
      if (contain_ask_list(Optimize.OPT->objects,v5255) != CTRUE)
       { GC_OBJECT(list,Optimize.OPT->objects)->addFast(v5255);
        (*Optimize.c_register)(v5255);
        } 
      Result = (*Optimize.c_code)(_oid_(v13275),
        _oid_(v5259));
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireType * c_type_Defmethod_Optimize(Defmethod *v9268)
{ return (Kernel._any);} 

OID  c_code_Defmethod_Optimize(Defmethod *v9268)
{ GC_BIND;
  { OID Result = 0;
    { property * v15600 = v9268->arg->selector;
      list * v5252 = GC_OBJECT(list,v9268->arg->args);
      list * v15474 = (((v5252->length == 1) && 
          ((*(v5252))[1] == _oid_(ClEnv))) ?
        list::alloc(1,GC_OID(_oid_(Variable_I_symbol(OBJECT(symbol,Optimize._starname_star->value),0,Kernel._void)))) :
        v5252 );
      list * v15471 = GC_OBJECT(list,extract_signature_I_list(v15474));
      list * v5945 = GC_OBJECT(list,extract_range_any(GC_OID(v9268->set_arg),v15474,GC_OBJECT(list,OBJECT(list,Language.LDEF->value))));
      OID  v8090;
      if ((boolean_I_any(v9268->inline_ask) == CTRUE) && 
          (Optimize.compiler->inline_ask == CTRUE))
       { print_in_string_void();
        princ_string("lambda[(");
        ppvariable_list(v15474);
        princ_string("),");
        print_any(GC_OID(v9268->body));
        princ_string("]");
        v8090 = _string_(end_of_print_void());
        } 
      else v8090 = Kernel.cfalse;
        list * v11302 = GC_OBJECT(list,extract_status_new_any(GC_OID(v9268->body)));
      OID  v12479 = GC_OID((*Core._at)(_oid_(v15600),
        (*(v15471))[2]));
      method * v5253;
      { ClaireObject *V_CC ;
        if (Kernel._method == OBJECT(ClaireObject,v12479)->isa)
         V_CC = OBJECT(method,v12479);
        else close_exception(((general_error *) (*Core._general_error)(_string_("[internal] the method ~S @ ~S is not known"),
            _oid_(list::alloc(2,_oid_(v15600),(*(v15471))[2])))));
          v5253= (method *) V_CC;} 
      OID  v11284 = v5253->status;
      ((*(v11302))[2]=get_property(Kernel.functional,v5253));
      if ((Optimize.compiler->inline_ask != CTRUE) && 
          ((v15600 == Language.Iterate) || 
              (v15600 == Language.iterate)))
       Result = Core.nil->value;
      else { if ((*(v11302))[3] != _oid_(Kernel.body))
           { char * v15515 = string_v((*Optimize.function_name)(_oid_(v15600),
              (*(v15471))[2],
              (*(v11302))[2]));
            lambda * v15453 = GC_OBJECT(lambda,lambda_I_list(v15474,(*(v11302))[3]));
            int  v8123 = ((Optimize.OPT->recompute == CTRUE) ?
              c_status_any(GC_OID(v15453->body),GC_OBJECT(list,v15453->vars)) :
              status_I_restriction(v5253) );
            compile_lambda_string(v15515,v15453,_oid_(v5253));
            if (((*(v11302))[1] == CNULL) || 
                (Optimize.OPT->recompute == CTRUE))
             { if ((Optimize.OPT->use_nth_equal != CTRUE) && 
                  (BCONTAIN(v8123,2)))
               v8123= (v8123-exp2_integer(2));
              if ((Optimize.OPT->use_update != CTRUE) && 
                  (BCONTAIN(v8123,3)))
               v8123= (v8123-exp2_integer(3));
              if ((Optimize.OPT->allocation != CTRUE) && 
                  (BCONTAIN(v8123,1)))
               v8123= (v8123-exp2_integer(1));
              ((*(v11302))[1]=v8123);
              } 
            ((*(v11302))[2]=_oid_(make_function_string(v15515)));
            } 
          if (INHERIT(OWNER(v9268->set_arg),Core._global_variable))
           ((*(v5945))[1]=v9268->set_arg);
          else if ((INHERIT(v5253->range->isa,Kernel._class)) && 
              (!INHERIT(OWNER((*(v5945))[1]),Kernel._class)))
           ((*(v5945))[1]=_oid_(v5253->range));
          { OID  v13264 = GC_OID(add_method_I_method(v5253,
              OBJECT(list,(*(v15471))[1]),
              (*(v5945))[1],
              (*(v11302))[1],
              OBJECT(ClaireFunction,(*(v11302))[2])));
            { OID  v10949;
              if ((boolean_I_any(v9268->inline_ask) == CTRUE) && 
                  ((Optimize.compiler->inline_ask == CTRUE) && 
                    (Optimize.compiler->diet_ask != CTRUE)))
               { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Core.inlineok_ask);
                (v2072->args = list::alloc(2,v13264,v8090));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v10949 = _oid_(v2072);
                } 
              else if ((boolean_I_any((*(v5945))[2]) == CTRUE) && 
                  (Optimize.compiler->diet_ask != CTRUE))
               { char * v15515 = GC_STRING(append_string(string_v((*Optimize.function_name)(_oid_(v15600),
                  (*(v15471))[2],
                  (*(v11302))[2])),"_type"));
                compile_lambda_string(v15515,OBJECT(lambda,(*(v5945))[2]),_oid_(Kernel._type));
                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Core.write);
                  (v2072->args = list::alloc(3,Language.typing->value,
                    v13264,
                    _oid_(make_function_string(v15515))));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v10949 = _oid_(v2072);
                  } 
                } 
              else v10949 = v13264;
                Result = (*Optimize.c_code)(v10949);
              } 
            } 
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

method * add_method_property2(property *v5256,list *v15471,ClaireType *v15645,int v15689,ClaireFunction *v15219,ClaireFunction *v15220)
{ return (add_method_property(v5256,
    v15471,
    v15645,
    v15689,
    _oid_(v15219)));} 

OID  add_method_I_method(method *v5253,list *v15471,OID v15645,OID v12924,ClaireFunction *v15287)
{ GC_BIND;
  { OID Result = 0;
    { Call_method * v13254;
      { { Call_method * v2072 = ((Call_method *) GC_OBJECT(Call_method,new_object_class(Language._Call_method)));
          (v2072->arg = ((method *) _at_property1(Kernel.add_method,Kernel._property)));
          (v2072->args = list::alloc(5,GC_OID((*Optimize.c_code)(_oid_(v5253->selector),
              _oid_(Kernel._property))),
            GC_OID((*Optimize.c_code)(_oid_(v15471),
              _oid_(Kernel._list))),
            GC_OID((*Optimize.c_code)(v15645,
              _oid_(Kernel._type))),
            v12924,
            _oid_(v15287)));
          add_I_property(Kernel.instances,Language._Call_method,11,_oid_(v2072));
          v13254 = v2072;
          } 
        GC_OBJECT(Call_method,v13254);} 
      if ((v5253->range == Kernel._float) || 
          ((v5253->domain->memq(_oid_(Kernel._float)) == CTRUE) || 
            (INHERIT(v5253->range->isa,Kernel._tuple))))
       GC_OBJECT(list,v13254->args)->addFast(_oid_(make_function_string(append_string(string_I_function(v15287),"_"))));
      Result = _oid_(v13254);
      } 
    GC_UNBIND; return (Result);} 
  } 

list * extract_status_new_any(OID v5264)
{ GC_BIND;
  { list *Result ;
    { OID  v5259 = CNULL;
      OID  v5246;
      if ((INHERIT(OWNER(v5264),Language._Call)) && (OBJECT(Call,v5264)->selector == Language.function_I))
       v5246 = v5264;
      else v5246 = CNULL;
        if (INHERIT(OWNER(v5264),Language._And))
       { OID  v5265 = (*(OBJECT(And,v5264)->args))[1];
        if ((INHERIT(OWNER(v5265),Language._Call)) && (OBJECT(Call,v5265)->selector == Language.function_I))
         { v5246= v5265;
          v5264= (*(OBJECT(And,v5264)->args))[2];
          } 
        } 
      else if (INHERIT(OWNER(v5264),Language._Call))
       { if (OBJECT(Call,v5264)->selector == Language.function_I)
         v5264= _oid_(Kernel.body);
        } 
      if (v5246 != CNULL)
       { v5264= _oid_(Kernel.body);
        if (length_bag(OBJECT(bag,(*Core.args)(v5246))) > 1)
         { { ClaireHandler c_handle = ClaireHandler();
            if ERROR_IN 
            { { int  v11201;{ set * v12871;
                  { bag *V_CC ;
                    { set * v13832;
                      { set * v12630 = set::empty(Kernel.emptySet);
                        { OID gc_local;
                          ITERATE(v5261);
                          bag *v5261_support;
                          v5261_support = GC_OBJECT(list,cdr_list(GC_OBJECT(list,OBJECT(list,(*Core.args)(v5246)))));
                          for (START(v5261_support); NEXT(v5261);)
                          { GC_LOOP;
                            v12630->addFast(GC_OID(OPT_EVAL(v5261)));
                            GC_UNLOOP;} 
                          } 
                        v13832 = GC_OBJECT(set,v12630);
                        } 
                      V_CC = check_in_bag(v13832,Kernel._set,Kernel._integer);
                      } 
                    v12871= (set *) V_CC;} 
                  v11201 = integer_I_set(v12871);
                  } 
                
                v5259=v11201;} 
              ClEnv->cHandle--;} 
            else if (belong_to(_oid_(ClEnv->exception_I),_oid_(Kernel._any)) == CTRUE)
            { c_handle.catchIt();{ warn_void();
                (Optimize.SHIT->value= _oid_(cdr_list(GC_OBJECT(list,OBJECT(list,(*Core.args)(v5246))))));
                tformat_string("wrong status ~S -> ~S\n",2,list::alloc(2,v5246,_oid_(set_I_bag(cdr_list(GC_OBJECT(list,OBJECT(list,(*Core.args)(v5246))))))));
                v5259= 0;
                } 
              } 
            else PREVIOUS_HANDLER;} 
          if ((OBJECT(ClaireBoolean,(*Kernel._sup)(v5259,
            63))) == CTRUE)
           { princ_string("AHAHA ");
            print_any(v5246);
            princ_string("\n");
            } 
          } 
        else v5259= 0;
          v5246= _oid_(make_function_string(string_I_symbol(extract_symbol_any((*(OBJECT(bag,(*Core.args)(v5246))))[1]))));
        } 
      Result = list::alloc(3,v5259,
        v5246,
        v5264);
      } 
    GC_UNBIND; return (Result);} 
  } 

list * extract_signature_I_list(list *v5252)
{ GC_BIND;
  (Language.LDEF->value= _oid_(list::empty(Kernel._any)));
  { list *Result ;
    { int  v5254 = 0;
      list * v15405 = list::empty(Kernel._type);
      list * v15406;
      { { bag *v_list; OID v_val;
          OID v5263,CLcount;
          v_list = v5252;
           v15406 = v_list->clone(Kernel._any);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v5263 = (*(v_list))[CLcount];
            { OID  v5256 = GC_OID(extract_pattern_any(GC_OID(_oid_(OBJECT(Variable,v5263)->range)),list::alloc(1,v5254)));
              ++v5254;
              { { OID  v2206;
                  { if (INHERIT(OBJECT(Variable,v5263)->range->isa,Core._global_variable))
                     v2206 = _oid_(OBJECT(Variable,v5263)->range);
                    else v2206 = v5256;
                      GC_OID(v2206);} 
                  v15405 = v15405->addFast(v2206);
                  } 
                GC_OBJECT(list,v15405);} 
              (OBJECT(Variable,v5263)->range = type_I_any(v5256));
              v_val = v5256;
              } 
            
            (*((list *) v15406))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,v15406);} 
      Result = list::alloc(2,_oid_(v15405),_oid_(v15406));
      } 
    GC_UNBIND; return (Result);} 
  } 

ClaireBoolean * _equalsig_ask_list(list *v5264,list *v5265)
{ return (((tmatch_ask_list(v5264,v5265) == CTRUE) ? ((tmatch_ask_list(v5265,v5264) == CTRUE) ? CTRUE: CFALSE): CFALSE));} 

char * function_name_property_Optimize(property *v5256,list *v5252,OID v5264)
{ if (INHERIT(OWNER(v5264),Kernel._function)) 
  { { char *Result ;
      Result = string_I_function(OBJECT(ClaireFunction,v5264));
      return (Result);} 
     } 
  else{ GC_BIND;
    { char *Result ;
      { int  v5254 = 0;
        int  v5253 = 0;
        module * v15487 = v5256->name->module_I;
        ClaireClass * v5243 = class_I_type(OBJECT(ClaireType,(*(v5252))[1]));
        char * v5258 = GC_STRING(append_string(GC_STRING(append_string(string_I_symbol(v5256->name),"_")),string_I_symbol(v5243->name)));
        if ((Optimize.compiler->naming == 0) && 
            (v5256 != Core.main))
         v5258= GC_STRING(append_string(GC_STRING(append_string(string_I_symbol(v15487->name),"_")),v5258));
        { ITERATE(v5258);
          for (START(v5256->restrictions); NEXT(v5258);)
          { if (v5243 == domain_I_restriction(OBJECT(restriction,v5258)))
             ++v5254;
            if (_equalsig_ask_list(v5252,OBJECT(restriction,v5258)->domain) == CTRUE)
             v5253= v5254;
            } 
          } 
        v5258= GC_STRING(((v5254 <= 1) ?
          v5258 :
          append_string(v5258,GC_STRING(string_I_integer (v5253))) ));
        Result = (((stable_ask_relation(v5256) == CTRUE) || 
            (v5256 == Core.main)) ?
          v5258 :
          append_string(GC_STRING(append_string(v5258,"_")),string_I_symbol(ClEnv->module_I->name)) );
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 

OID  compile_lambda_string(char *v9268,lambda *v5252,OID v5253)
{ { OID Result = 0;
    { int  v5264 = Optimize.compiler->safety;
      lambda * v5265 = v5252;
      if (Kernel._method == OWNER(v5253))
       (Optimize.OPT->in_method = v5253);
      (Optimize.OPT->protection = CFALSE);
      (Optimize.OPT->allocation = CFALSE);
      if (Optimize.OPT->loop_index > 0)
       (Optimize.OPT->loop_index = 0);
      (Optimize.OPT->loop_gc = CFALSE);
      (Optimize.OPT->use_update = CFALSE);
      (Optimize.OPT->use_nth_equal = CFALSE);
      (Optimize.OPT->max_vars = 0);
      if (contain_ask_list(Optimize.OPT->unsure,v5253) == CTRUE)
       (Optimize.compiler->safety = 1);
      (*Optimize.make_c_function)(_oid_(v5252),
        _string_(v9268),
        v5253);
      (Optimize.OPT->in_method = CNULL);
      (Optimize.compiler->safety = v5264);
      Result = Kernel.ctrue;
      } 
    return (Result);} 
  } 

OID  c_code_Defarray_Optimize(Defarray *v9268)
{ GC_BIND;
  { OID Result = 0;
    { list * v5241 = GC_OBJECT(list,v9268->arg->args);
      OID  v13253 = get_symbol(extract_symbol_any((*(v5241))[1]));
      table * v13274;
      { ClaireObject *V_CC ;
        if (INHERIT(OWNER(v13253),Kernel._table))
         V_CC = OBJECT(table,v13253);
        else close_exception(((general_error *) (*Core._general_error)(_string_("[internal] the table ~S is unknown"),
            _oid_(list::alloc(1,(*(v5241))[1])))));
          v13274= (table *) V_CC;} 
      OID  v5259 = GC_OID((*Kernel.domain)(v13253));
      OID  v5245;
      { { list * v5252 = GC_OBJECT(list,cdr_list(v5241));
          OID  v5242 = GC_OID(lexical_build_any(GC_OID(v9268->body),v5252,0));
          { ClaireBoolean * g0341I;
            { OID  v4128;
              { ITERATE(v15763);
                v4128= _oid_(CFALSE);
                for (START(v5252); NEXT(v15763);)
                if (occurrence_any(v5242,OBJECT(Variable,v15763)) > 0)
                 { v4128 = Kernel.ctrue;
                  break;} 
                } 
              g0341I = boolean_I_any(v4128);
              } 
            
            if (g0341I == CTRUE) v5245 = _oid_(lambda_I_list(v5252,v5242));
              else v5245 = v9268->body;
            } 
          } 
        GC_OID(v5245);} 
      OID  v5244;
      { if (INHERIT(OWNER(v5245),Core._lambda))
         v5244 = CNULL;
        else v5244 = v9268->body;
          GC_OID(v5244);} 
      list * v1602;
      if (boolean_I_any(_oid_(OBJECT(ClaireRelation,v13253)->multivalued_ask)) == CTRUE)
       { OID v_bag;
        GC_ANY(v1602= list::empty(Kernel._any));
        { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.put);
            (v2072->args = list::alloc(3,_oid_(Kernel.multivalued_ask),
              _oid_(v13274),
              GC_OID(_oid_(OBJECT(ClaireRelation,v13253)->multivalued_ask))));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          GC_OID(v_bag);} 
        ((list *) v1602)->addFast(v_bag);} 
      else v1602 = list::empty(Kernel._any);
        list * v1603;
      { OID v_bag;
        GC_ANY(v1603= list::empty(Kernel._any));
        { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.put);
            (v2072->args = list::alloc(3,_oid_(Kernel.range),
              _oid_(v13274),
              GC_OID((*Kernel.range)(v13253))));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          GC_OID(v_bag);} 
        ((list *) v1603)->addFast(v_bag);
        { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.put);
            (v2072->args = list::alloc(3,_oid_(Kernel.params),
              _oid_(v13274),
              GC_OID((*Kernel.params)(v13253))));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          GC_OID(v_bag);} 
        ((list *) v1603)->addFast(v_bag);
        { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.put);
            (v2072->args = list::alloc(3,_oid_(Kernel.domain),
              _oid_(v13274),
              v5259));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          GC_OID(v_bag);} 
        ((list *) v1603)->addFast(v_bag);} 
      (OBJECT(Variable,(*(v5241))[2])->range = extract_type_any(GC_OID(_oid_(OBJECT(Variable,(*(v5241))[2])->range))));
      if (v5241->length == 2)
       { { { OID  v5089;
            { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
              (v2072->selector = Kernel.put);
              { Call * v9603; 
                list * v9604;
                v9603 = v2072;
                { OID v_bag;
                  GC_ANY(v9604= list::empty(Kernel.emptySet));
                  ((list *) v9604)->addFast(_oid_(Kernel.graph));
                  ((list *) v9604)->addFast(_oid_(v13274));
                  if (INHERIT(OBJECT(ClaireObject,v5259)->isa,Core._Interval))
                   { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                    (v2072->selector = Core.make_copy_list);
                    (v2072->args = list::alloc(2,size_Interval(OBJECT(Interval,v5259)),v5244));
                    add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                    v_bag = _oid_(v2072);
                    } 
                  else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                      (v2072->selector = Kernel.make_list);
                      (v2072->args = list::alloc(2,29,CNULL));
                      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                      v_bag = _oid_(v2072);
                      } 
                    ((list *) v9604)->addFast(v_bag);} 
                (v9603->args = v9604);} 
              add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
              v5089 = _oid_(v2072);
              } 
            v1603 = v1603->addFast(v5089);
            } 
          GC_OBJECT(list,v1603);} 
        { { OID  v7972;
            if (INHERIT(OWNER(v5245),Core._lambda))
             { For * v2072 = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
              store_object(v2072,
                2,
                Kernel._object,
                (*(v5241))[2],
                CFALSE);
              (v2072->set_arg = v5259);
              { Iteration * v9606; 
                OID  v9609;
                v9606 = v2072;
                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.nth_equal);
                  (v2072->args = list::alloc(3,_oid_(v13274),
                    (*(v5241))[2],
                    GC_OID(OBJECT(lambda,v5245)->body)));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v9609 = _oid_(v2072);
                  } 
                (v9606->arg = v9609);} 
              add_I_property(Kernel.instances,Language._For,11,_oid_(v2072));
              v7972 = _oid_(v2072);
              } 
            else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel.put);
                (v2072->args = list::alloc(3,_oid_(Kernel.DEFAULT),
                  _oid_(v13274),
                  v5244));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v7972 = _oid_(v2072);
                } 
              v1603 = v1603->addFast(v7972);
            } 
          GC_OBJECT(list,v1603);} 
        } 
      else { ClaireType * v15623 = GC_OBJECT(ClaireType,extract_type_any(GC_OID(_oid_(OBJECT(Variable,(*(v5241))[3])->range))));
          (OBJECT(Variable,(*(v5241))[3])->range = v15623);
          { { OID  v10855;
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel.put);
                { Call * v9630; 
                  list * v9631;
                  v9630 = v2072;
                  { OID v_bag;
                    GC_ANY(v9631= list::empty(Kernel.emptySet));
                    ((list *) v9631)->addFast(_oid_(Kernel.graph));
                    ((list *) v9631)->addFast(_oid_(v13274));
                    { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Core.make_copy_list);
                        { Call * v9632; 
                          list * v9633;
                          v9632 = v2072;
                          { OID v_bag;
                            GC_ANY(v9633= list::empty(Kernel.emptySet));
                            ((list *) v9633)->addFast(OBJECT(table,v13253)->graph->length);
                            { if ((*Kernel.params)(v13253) == _oid_(Kernel._any))
                               v_bag = CNULL;
                              else v_bag = (*Kernel.DEFAULT)(v13253);
                                GC_OID(v_bag);} 
                            ((list *) v9633)->addFast(v_bag);} 
                          (v9632->args = v9633);} 
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v_bag = _oid_(v2072);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) v9631)->addFast(v_bag);} 
                  (v9630->args = v9631);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v10855 = _oid_(v2072);
                } 
              v1603 = v1603->addFast(v10855);
              } 
            GC_OBJECT(list,v1603);} 
          { { OID  v3073;
              if (INHERIT(OWNER(v5245),Core._lambda))
               { For * v2072 = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
                store_object(v2072,
                  2,
                  Kernel._object,
                  (*(v5241))[2],
                  CFALSE);
                (v2072->set_arg = (*(OBJECT(bag,v5259)))[1]);
                { Iteration * v9635; 
                  OID  v9636;
                  v9635 = v2072;
                  { For * v2072 = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
                    store_object(v2072,
                      2,
                      Kernel._object,
                      (*(v5241))[3],
                      CFALSE);
                    (v2072->set_arg = _oid_(v15623));
                    { Iteration * v9637; 
                      OID  v9639;
                      v9637 = v2072;
                      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Kernel.nth_equal);
                        (v2072->args = list::alloc(4,_oid_(v13274),
                          (*(v5241))[2],
                          (*(v5241))[3],
                          GC_OID(OBJECT(lambda,v5245)->body)));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v9639 = _oid_(v2072);
                        } 
                      (v9637->arg = v9639);} 
                    add_I_property(Kernel.instances,Language._For,11,_oid_(v2072));
                    v9636 = _oid_(v2072);
                    } 
                  (v9635->arg = v9636);} 
                add_I_property(Kernel.instances,Language._For,11,_oid_(v2072));
                v3073 = _oid_(v2072);
                } 
              else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.put);
                  (v2072->args = list::alloc(3,_oid_(Kernel.DEFAULT),
                    _oid_(v13274),
                    v5244));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v3073 = _oid_(v2072);
                  } 
                v1603 = v1603->addFast(v3073);
              } 
            GC_OBJECT(list,v1603);} 
          } 
        GC_OBJECT(list,Optimize.OPT->objects)->addFast(v13253);
      (*Optimize.c_register)(v13253);
      { OID  v7878;
        { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
          { Do * v9661; 
            list * v9662;
            v9661 = v2072;
            { OID  v14558;
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Optimize.object_I);
                (v2072->args = list::alloc(2,GC_OID((*Kernel.name)(v13253)),_oid_(Kernel._table)));
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v14558 = _oid_(v2072);
                } 
              v9662 = cons_any(v14558,GC_OBJECT(list,add_star_list(v1602,v1603)));
              } 
            (v9661->args = v9662);} 
          add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
          v7878 = _oid_(v2072);
          } 
        Result = (*Optimize.c_code)(v7878,
          _oid_(Kernel._any));
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

void  compute_if_write_inverse_relation2(ClaireRelation *v5226)
{ GC_BIND;
  { Variable * v5264;
    { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
        (v2072->pname = symbol_I_string2("XX"));
        (v2072->range = v5226->domain);
        add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
        v5264 = v2072;
        } 
      GC_OBJECT(Variable,v5264);} 
    Variable * v5265;
    { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
        (v2072->pname = symbol_I_string2("YY"));
        (v2072->range = ((multi_ask_any(_oid_(v5226)) == CTRUE) ?
          member_type(v5226->range) :
          v5226->range ));
        add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
        v5265 = v2072;
        } 
      GC_OBJECT(Variable,v5265);} 
    Variable * v5266;
    { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
        (v2072->pname = symbol_I_string2("ZZ"));
        (v2072->range = v5226->range);
        add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
        v5266 = v2072;
        } 
      GC_OBJECT(Variable,v5266);} 
    list * v15405 = list::empty(Kernel._any);
    if (multi_ask_any(_oid_(v5226)) == CTRUE)
     { v15405= list::alloc(Kernel._any,1,GC_OID((INHERIT(v5226->isa,Kernel._property) ?  Produce_put_property2((property *) OBJECT(property,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5265)) :   Produce_put_table2((table *) OBJECT(table,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5265)))));
      if (v5226->inverse != (NULL))
       v15405= GC_OBJECT(list,v15405->addFast(GC_OID((INHERIT(v5226->inverse->isa,Kernel._property) ?  Produce_put_property2((property *) OBJECT(property,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5265)),_oid_(v5264)) :   Produce_put_table2((table *) OBJECT(table,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5265)),_oid_(v5264))))));
      { ClaireRelation * v9664; 
        OID  v9665;
        v9664 = v5226;
        { lambda * v11292;{ OID  v2018;
            { If * v2072 = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
              { If * v9667; 
                OID  v9668;
                v9667 = v2072;
                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Core.NOT);
                  { Call * v9669; 
                    list * v9691;
                    v9669 = v2072;
                    { OID v_bag;
                      GC_ANY(v9691= list::empty(Kernel.emptySet));
                      { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                          (v2072->selector = Kernel._Z);
                          (v2072->args = list::alloc(2,_oid_(v5265),GC_OID(Produce_get_relation2(v5226,v5264))));
                          add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                          v_bag = _oid_(v2072);
                          } 
                        GC_OID(v_bag);} 
                      ((list *) v9691)->addFast(v_bag);} 
                    (v9669->args = v9691);} 
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v9668 = _oid_(v2072);
                  } 
                (v9667->test = v9668);} 
              { If * v9692; 
                OID  v9693;
                v9692 = v2072;
                { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                  (v2072->args = v15405);
                  add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                  v9693 = _oid_(v2072);
                  } 
                (v9692->arg = v9693);} 
              add_I_property(Kernel.instances,Language._If,11,_oid_(v2072));
              (v2072->other = Kernel.cfalse);
              v2018 = _oid_(v2072);
              } 
            v11292 = lambda_I_list(list::alloc(2,_oid_(v5264),_oid_(v5265)),v2018);
            } 
          
          v9665=_oid_(v11292);} 
        (v9664->if_write = v9665);} 
      } 
    else { v15405= list::alloc(Kernel._any,1,GC_OID((INHERIT(v5226->isa,Kernel._property) ?  Produce_put_property2((property *) OBJECT(property,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5265)) :   Produce_put_table2((table *) OBJECT(table,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5265)))));
        if (v5226->inverse != (NULL))
         { { { OID  v12542;
              { If * v2072 = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
                { If * v9696; 
                  OID  v9697;
                  v9696 = v2072;
                  { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                    (v2072->selector = Core.known_ask);
                    (v2072->args = list::alloc(1,_oid_(v5266)));
                    add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                    v9697 = _oid_(v2072);
                    } 
                  (v9696->test = v9697);} 
                (v2072->arg = (INHERIT(v5226->inverse->isa,Kernel._property) ?  Produce_remove_property2((property *) OBJECT(property,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5266)),_oid_(v5264)) :   Produce_remove_table2((table *) OBJECT(table,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5266)),_oid_(v5264))));
                add_I_property(Kernel.instances,Language._If,11,_oid_(v2072));
                (v2072->other = Kernel.cfalse);
                v12542 = _oid_(v2072);
                } 
              v15405 = v15405->addFast(v12542);
              } 
            GC_OBJECT(list,v15405);} 
          v15405= GC_OBJECT(list,v15405->addFast(GC_OID((INHERIT(v5226->inverse->isa,Kernel._property) ?  Produce_put_property2((property *) OBJECT(property,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5265)),_oid_(v5264)) :   Produce_put_table2((table *) OBJECT(table,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5265)),_oid_(v5264))))));
          } 
        { ClaireRelation * v9698; 
          OID  v9699;
          v9698 = v5226;
          { lambda * v11326;{ OID  v1924;
              { Let * v2072 = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
                (v2072->var = v5266);
                (v2072->value = Produce_get_relation2(v5226,v5264));
                { Let * v9722; 
                  OID  v9723;
                  v9722 = v2072;
                  { If * v2072 = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
                    { If * v9724; 
                      OID  v9725;
                      v9724 = v2072;
                      { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Core._I_equal);
                        (v2072->args = list::alloc(2,_oid_(v5265),_oid_(v5266)));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v9725 = _oid_(v2072);
                        } 
                      (v9724->test = v9725);} 
                    { If * v9727; 
                      OID  v9728;
                      v9727 = v2072;
                      { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                        (v2072->args = v15405);
                        add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                        v9728 = _oid_(v2072);
                        } 
                      (v9727->arg = v9728);} 
                    add_I_property(Kernel.instances,Language._If,11,_oid_(v2072));
                    (v2072->other = Kernel.cfalse);
                    v9723 = _oid_(v2072);
                    } 
                  (v9722->arg = v9723);} 
                add_I_property(Kernel.instances,Language._Let,11,_oid_(v2072));
                v1924 = _oid_(v2072);
                } 
              v11326 = lambda_I_list(list::alloc(2,_oid_(v5264),_oid_(v5265)),v1924);
              } 
            
            v9699=_oid_(v11326);} 
          (v9698->if_write = v9699);} 
        } 
      { char * v8882 = GC_STRING(append_string(string_I_symbol(v5226->name),"_write"));
      compile_lambda_string(v8882,GC_OBJECT(lambda,OBJECT(lambda,v5226->if_write)),_oid_(Kernel._void));
      } 
    } 
  GC_UNBIND;} 

OID  compute_set_write_relation2(ClaireRelation *v5226)
{ GC_BIND;
  { OID Result = 0;
    { Variable * v5264;
      { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
          (v2072->pname = symbol_I_string2("XX"));
          (v2072->range = v5226->domain);
          add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
          v5264 = v2072;
          } 
        GC_OBJECT(Variable,v5264);} 
      Variable * v5265;
      { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
          (v2072->pname = symbol_I_string2("YY"));
          (v2072->range = Kernel._bag);
          add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
          v5265 = v2072;
          } 
        GC_OBJECT(Variable,v5265);} 
      Variable * v5266;
      { { Variable * v2072 = ((Variable *) GC_OBJECT(Variable,new_object_class(Language._Variable)));
          (v2072->pname = symbol_I_string2("ZZ"));
          (v2072->range = member_type(v5226->range));
          add_I_property(Kernel.instances,Language._Variable,11,_oid_(v2072));
          v5266 = v2072;
          } 
        GC_OBJECT(Variable,v5266);} 
      list * v15405 = list::empty(Kernel._any);
      tformat_string("compute set_write for ~S \n",0,list::alloc(1,_oid_(v5226)));
      if (v5226->inverse != (NULL))
       { { OID  v12448;
          { For * v2072 = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
            (v2072->var = v5266);
            (v2072->set_arg = Produce_get_relation2(v5226,v5264));
            (v2072->arg = (INHERIT(v5226->inverse->isa,Kernel._property) ?  Produce_remove_property2((property *) OBJECT(property,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5266)),_oid_(v5264)) :   Produce_remove_table2((table *) OBJECT(table,_oid_(v5226->inverse)),OBJECT(Variable,_oid_(v5266)),_oid_(v5264))));
            add_I_property(Kernel.instances,Language._For,11,_oid_(v2072));
            v12448 = _oid_(v2072);
            } 
          v15405 = v15405->addFast(v12448);
          } 
        GC_OBJECT(list,v15405);} 
      v15405= GC_OBJECT(list,v15405->addFast(GC_OID(Produce_erase_property2(((property *) v5226),v5264))));
      { { OID  v13409;
          { For * v2072 = ((For *) GC_OBJECT(For,new_object_class(Language._For)));
            (v2072->var = v5266);
            (v2072->set_arg = _oid_(v5265));
            (v2072->arg = (INHERIT(v5226->isa,Kernel._property) ?  Produce_put_property2((property *) OBJECT(property,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5266)) :   Produce_put_table2((table *) OBJECT(table,_oid_(v5226)),OBJECT(Variable,_oid_(v5264)),_oid_(v5266))));
            add_I_property(Kernel.instances,Language._For,11,_oid_(v2072));
            v13409 = _oid_(v2072);
            } 
          v15405 = v15405->addFast(v13409);
          } 
        GC_OBJECT(list,v15405);} 
      { char * v8882 = GC_STRING(append_string(string_I_symbol(v5226->name),"_set_write"));
        { lambda * v14370;
          { { OID  v15331;
              { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                (v2072->args = v15405);
                add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                v15331 = _oid_(v2072);
                } 
              v14370 = lambda_I_list(list::alloc(2,_oid_(v5264),_oid_(v5265)),v15331);
              } 
            GC_OBJECT(lambda,v14370);} 
          Result = compile_lambda_string(v8882,v14370,_oid_(Kernel._void));
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  Produce_put_property2(property *v5258,Variable *v5264,OID v5265)
{ GC_BIND;
  { OID Result = 0;
    { list * v5252 = list::empty(Kernel._any);
      { OID gc_local;
        ITERATE(v15843);
        for (START(v5258->restrictions); NEXT(v15843);)
        { GC_LOOP;
          if ((Kernel._slot == OBJECT(ClaireObject,v15843)->isa) && 
              (boolean_I_any(_oid_(_exp_type(GC_OBJECT(ClaireType,ptype_type(v5264->range)),domain_I_restriction(OBJECT(restriction,v15843))))) == CTRUE))
           { list * v3705;
            { OID v_bag;
              GC_ANY(v3705= list::empty(Kernel.emptySet));
              ((list *) v3705)->addFast(_oid_(domain_I_restriction(OBJECT(restriction,v15843))));
              if (boolean_I_any(_oid_(v5258->multivalued_ask)) == CTRUE)
               { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel.add_I);
                { Call * v9755; 
                  list * v9756;
                  v9755 = v2072;
                  { OID v_bag;
                    GC_ANY(v9756= list::empty(Kernel.emptySet));
                    { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = v5258);
                        { Call * v9757; 
                          list * v9758;
                          v9757 = v2072;
                          { OID v_bag;
                            GC_ANY(v9758= list::empty(Kernel.emptySet));
                            { { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                                (v2072->arg = _oid_(v5264));
                                (v2072->set_arg = domain_I_restriction(OBJECT(restriction,v15843)));
                                add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                                v_bag = _oid_(v2072);
                                } 
                              GC_OID(v_bag);} 
                            ((list *) v9758)->addFast(v_bag);} 
                          (v9757->args = v9758);} 
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v_bag = _oid_(v2072);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) v9756)->addFast(v_bag);
                    ((list *) v9756)->addFast(v5265);} 
                  (v9755->args = v9756);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v_bag = _oid_(v2072);
                } 
              else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.put);
                  { Call * v9759; 
                    list * v9760;
                    v9759 = v2072;
                    { OID v_bag;
                      GC_ANY(v9760= list::empty(Kernel.emptySet));
                      ((list *) v9760)->addFast(_oid_(v5258));
                      { { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                          (v2072->arg = _oid_(v5264));
                          (v2072->set_arg = domain_I_restriction(OBJECT(restriction,v15843)));
                          add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                          v_bag = _oid_(v2072);
                          } 
                        GC_OID(v_bag);} 
                      ((list *) v9760)->addFast(v_bag);
                      ((list *) v9760)->addFast(v5265);} 
                    (v9759->args = v9760);} 
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                ((list *) v3705)->addFast(v_bag);} 
            v5252 = add_star_list(v5252,v3705);
            } 
          GC_UNLOOP;} 
        } 
      if (v5252->length == 2)
       Result = (*(v5252))[2];
      else { Case * v2072 = ((Case *) GC_OBJECT(Case,new_object_class(Language._Case)));
          (v2072->var = _oid_(v5264));
          (v2072->args = v5252);
          add_I_property(Kernel.instances,Language._Case,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  Produce_erase_property2(property *v5258,Variable *v5264)
{ GC_BIND;
  { OID Result = 0;
    { list * v5252 = list::empty(Kernel._any);
      bag * v13625;
      if (v5258->multivalued_ask == Kernel._list)
       v13625 = list::empty(Kernel._any);
      else v13625 = set::empty(Kernel._any);
        cast_I_bag(v13625,member_type(v5258->range));
      { OID gc_local;
        ITERATE(v15843);
        for (START(v5258->restrictions); NEXT(v15843);)
        { GC_LOOP;
          if ((Kernel._slot == OBJECT(ClaireObject,v15843)->isa) && 
              (boolean_I_any(_oid_(_exp_type(GC_OBJECT(ClaireType,ptype_type(v5264->range)),domain_I_restriction(OBJECT(restriction,v15843))))) == CTRUE))
           { list * v10432;
            { OID v_bag;
              GC_ANY(v10432= list::empty(Kernel.emptySet));
              ((list *) v10432)->addFast(_oid_(domain_I_restriction(OBJECT(restriction,v15843))));
              { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.put);
                  { Call * v9762; 
                    list * v9763;
                    v9762 = v2072;
                    { OID v_bag;
                      GC_ANY(v9763= list::empty(Kernel.emptySet));
                      ((list *) v9763)->addFast(_oid_(v5258));
                      { { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                          (v2072->arg = _oid_(v5264));
                          (v2072->set_arg = domain_I_restriction(OBJECT(restriction,v15843)));
                          add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                          v_bag = _oid_(v2072);
                          } 
                        GC_OID(v_bag);} 
                      ((list *) v9763)->addFast(v_bag);
                      { if (boolean_I_any(_oid_(v5258->multivalued_ask)) == CTRUE)
                         v_bag = _oid_(v13625);
                        else v_bag = OBJECT(slot,v15843)->DEFAULT;
                          GC_OID(v_bag);} 
                      ((list *) v9763)->addFast(v_bag);} 
                    (v9762->args = v9763);} 
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                GC_OID(v_bag);} 
              ((list *) v10432)->addFast(v_bag);} 
            v5252 = add_star_list(v5252,v10432);
            } 
          GC_UNLOOP;} 
        } 
      if (v5252->length == 2)
       Result = (*(v5252))[2];
      else { Case * v2072 = ((Case *) GC_OBJECT(Case,new_object_class(Language._Case)));
          (v2072->var = _oid_(v5264));
          (v2072->args = v5252);
          add_I_property(Kernel.instances,Language._Case,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  Produce_put_table2(table *v5258,Variable *v5264,OID v5265)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Kernel.put);
      { Call * v10438; 
        list * v10439;
        v10438 = v2072;
        { OID v_bag;
          GC_ANY(v10439= list::empty(Kernel.emptySet));
          ((list *) v10439)->addFast(_oid_(v5258));
          ((list *) v10439)->addFast(_oid_(v5264));
          if (boolean_I_any(_oid_(v5258->multivalued_ask)) == CTRUE)
           { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel.add);
            (v2072->args = list::alloc(2,_oid_(list::alloc(2,_oid_(Kernel.nth),_oid_(list::alloc(2,_oid_(v5258),_oid_(v5264))))),v5265));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          else v_bag = v5265;
            ((list *) v10439)->addFast(v_bag);} 
        (v10438->args = v10439);} 
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  Produce_get_relation2(ClaireRelation *v5258,Variable *v5264)
{ GC_BIND;
  { OID Result = 0;
    if (INHERIT(v5258->isa,Kernel._table))
     { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Kernel.nth);
      (v2072->args = list::alloc(2,_oid_(v5258),_oid_(v5264)));
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    else if (INHERIT(v5258->isa,Kernel._property))
     { list * v5252 = list::empty(Kernel._any);
      { OID gc_local;
        ITERATE(v15843);
        for (START(CLREAD(property,v5258,restrictions)); NEXT(v15843);)
        { GC_LOOP;
          if ((Kernel._slot == OBJECT(ClaireObject,v15843)->isa) && 
              (boolean_I_any(_oid_(_exp_type(GC_OBJECT(ClaireType,ptype_type(v5264->range)),domain_I_restriction(OBJECT(restriction,v15843))))) == CTRUE))
           { list * v5669;
            { OID v_bag;
              GC_ANY(v5669= list::empty(Kernel.emptySet));
              ((list *) v5669)->addFast(_oid_(domain_I_restriction(OBJECT(restriction,v15843))));
              { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = ((property *) v5258));
                  { Call * v10441; 
                    list * v10442;
                    v10441 = v2072;
                    { OID v_bag;
                      GC_ANY(v10442= list::empty(Kernel.emptySet));
                      { { Cast * v2072 = ((Cast *) GC_OBJECT(Cast,new_object_class(Language._Cast)));
                          (v2072->arg = _oid_(v5264));
                          (v2072->set_arg = domain_I_restriction(OBJECT(restriction,v15843)));
                          add_I_property(Kernel.instances,Language._Cast,11,_oid_(v2072));
                          v_bag = _oid_(v2072);
                          } 
                        GC_OID(v_bag);} 
                      ((list *) v10442)->addFast(v_bag);} 
                    (v10441->args = v10442);} 
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                GC_OID(v_bag);} 
              ((list *) v5669)->addFast(v_bag);} 
            v5252 = add_star_list(v5252,v5669);
            } 
          GC_UNLOOP;} 
        } 
      if (v5252->length == 2)
       Result = (*(v5252))[2];
      else { Case * v2072 = ((Case *) GC_OBJECT(Case,new_object_class(Language._Case)));
          (v2072->var = _oid_(v5264));
          (v2072->args = v5252);
          add_I_property(Kernel.instances,Language._Case,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
    else Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
  } 

OID  Produce_remove_property2(property *v5258,Variable *v5264,OID v5265)
{ GC_BIND;
  { OID Result = 0;
    { list * v5252 = list::empty(Kernel._any);
      { OID gc_local;
        ITERATE(v15843);
        for (START(v5258->restrictions); NEXT(v15843);)
        { GC_LOOP;
          if (Kernel._slot == OBJECT(ClaireObject,v15843)->isa)
           { list * v8557;
            { OID v_bag;
              GC_ANY(v8557= list::empty(Kernel.emptySet));
              ((list *) v8557)->addFast(_oid_(domain_I_restriction(OBJECT(restriction,v15843))));
              if (boolean_I_any(_oid_(v5258->multivalued_ask)) == CTRUE)
               { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Kernel._delete);
                { Call * v10444; 
                  list * v10445;
                  v10444 = v2072;
                  { OID v_bag;
                    GC_ANY(v10445= list::empty(Kernel.emptySet));
                    { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = v5258);
                        (v2072->args = list::alloc(1,_oid_(v5264)));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v_bag = _oid_(v2072);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) v10445)->addFast(v_bag);
                    ((list *) v10445)->addFast(v5265);} 
                  (v10444->args = v10445);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v_bag = _oid_(v2072);
                } 
              else { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.put);
                  (v2072->args = list::alloc(3,_oid_(v5258),
                    _oid_(v5264),
                    CNULL));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v_bag = _oid_(v2072);
                  } 
                ((list *) v8557)->addFast(v_bag);} 
            v5252 = add_star_list(v5252,v8557);
            } 
          GC_UNLOOP;} 
        } 
      if (v5252->length == 2)
       Result = (*(v5252))[2];
      else { Case * v2072 = ((Case *) GC_OBJECT(Case,new_object_class(Language._Case)));
          (v2072->var = _oid_(v5264));
          (v2072->args = v5252);
          add_I_property(Kernel.instances,Language._Case,11,_oid_(v2072));
          Result = _oid_(v2072);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 

OID  Produce_remove_table2(table *v5258,Variable *v5264,OID v5265)
{ GC_BIND;
  { OID Result = 0;
    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
      (v2072->selector = Kernel.put);
      { Call * v10446; 
        list * v10447;
        v10446 = v2072;
        { OID v_bag;
          GC_ANY(v10447= list::empty(Kernel.emptySet));
          ((list *) v10447)->addFast(_oid_(v5258));
          ((list *) v10447)->addFast(_oid_(v5264));
          if (boolean_I_any(_oid_(v5258->multivalued_ask)) == CTRUE)
           { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
            (v2072->selector = Kernel._delete);
            (v2072->args = list::alloc(2,_oid_(list::alloc(2,_oid_(Kernel.nth),_oid_(list::alloc(2,_oid_(v5258),_oid_(v5264))))),v5265));
            add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
            v_bag = _oid_(v2072);
            } 
          else v_bag = CNULL;
            ((list *) v10447)->addFast(v_bag);} 
        (v10446->args = v10447);} 
      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
      Result = _oid_(v2072);
      } 
    GC_UNBIND; return (Result);} 
  } 

OID  Tighten_relation2(ClaireRelation *v5258)
{ GC_RESERVE(6);  // v3.0.55 optim !
  { OID Result = 0;
    if (INHERIT(v5258->isa,Kernel._property))
     { ClaireType * v15115 = set::empty();
      ClaireType * v15129 = set::empty();
      { OID gc_local;
        ITERATE(v5259);
        for (START(CLREAD(property,v5258,restrictions)); NEXT(v5259);)
        { GC_LOOP;
          if (Kernel._slot == OBJECT(ClaireObject,v5259)->isa)
           { GC__ANY(v15115 = U_type(v15115,domain_I_restriction(OBJECT(restriction,v5259))), 3);
            GC__ANY(v15129 = U_type(v15129,GC_OBJECT(ClaireType,((multi_ask_any(_oid_(v5258)) == CTRUE) ?
              member_type(OBJECT(restriction,v5259)->range) :
              OBJECT(restriction,v5259)->range ))), 4);
            } 
          GC_UNLOOP;} 
        } 
      (v5258->open = 1);
      (v5258->domain = class_I_type(v15115));
      (v5258->range = ((v5258->multivalued_ask == Kernel._list) ?
        param_I_class(Kernel._list,class_I_type(v15129)) :
        ((v5258->multivalued_ask == Kernel._set) ?
          param_I_class(Kernel._set,class_I_type(v15129)) :
          v15129 ) ));
      Result = Kernel.cfalse;
      } 
    else Result = Kernel.cfalse;
      GC_UNBIND; return (Result);} 
  } 

void  lexical_num_any2(OID v9268,int v5254)
{ GC_BIND;
  if (INHERIT(OWNER(v9268),Language._Call))
   lexical_num_any2(GC_OID(_oid_(OBJECT(Call,v9268)->args)),v5254);
  else if (INHERIT(OWNER(v9268),Language._Instruction))
   { ClaireClass * v15607 = OBJECT(ClaireObject,v9268)->isa;
    if (contain_ask_set(Language._Instruction_with_var->descendents,_oid_(v15607)) == CTRUE)
     { put_property2(Kernel.index,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,(*Language.var)(v9268))),v5254);
      ++v5254;
      if (v5254 > Language._starvariable_index_star->value)
       (Language._starvariable_index_star->value= v5254);
      } 
    { ITERATE(v5259);
      for (START(v15607->slots); NEXT(v5259);)
      lexical_num_any2(get_slot(OBJECT(slot,v5259),OBJECT(ClaireObject,v9268)),v5254);
      } 
    } 
  else if (INHERIT(OWNER(v9268),Kernel._bag))
   { ITERATE(v5264);
    for (START(OBJECT(bag,v9268)); NEXT(v5264);)
    lexical_num_any2(v5264,v5254);
    } 
  else ;GC_UNBIND;} 

ClaireType * c_type_Defrule2_Optimize(Defrule *v9268)
{ return (Kernel._any);} 

OID  c_code_Defrule_Optimize(Defrule *v9268,ClaireClass *v5259)
{ GC_RESERVE(11);  // v3.0.55 optim !
  { OID Result = 0;
    { OID  v15659 = get_symbol(v9268->ident);
      list * v5252 = list::empty(Kernel._any);
      tformat_string("compile a rule ~S \n",0,list::alloc(1,v15659));
      { OID gc_local;
        ITERATE(v5258);
        bag *v5258_support;
        v5258_support = OBJECT(bag,nth_table1(Language.relations,v15659));
        for (START(v5258_support); NEXT(v5258);)
        { GC_LOOP;
          if (eventMethod_ask_relation2(OBJECT(ClaireRelation,v5258)) != CTRUE)
           Tighten_relation2(OBJECT(ClaireRelation,v5258));
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(v5258);
        bag *v5258_support;
        v5258_support = OBJECT(bag,nth_table1(Language.relations,v15659));
        for (START(v5258_support); NEXT(v5258);)
        { GC_LOOP;
          { if ((*Kernel.open)(v5258) < 2)
             { { OID  v770;
                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.FINAL);
                  (v2072->args = list::alloc(1,v5258));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v770 = _oid_(v2072);
                  } 
                v5252 = v5252->addFast(v770);
                } 
               GC__ANY(v5252, 5);} 
            compile_if_write_relation(OBJECT(ClaireRelation,v5258));
            { OID  v8882 = GC_OID((*Kernel._7_plus)(GC_OID((*Kernel.name)(v5258)),
                _string_("_write")));
              char * v5259 = string_I_symbol(OBJECT(symbol,v8882));
              OID  v15454 = GC_OID((*Kernel.if_write)(v5258));
              compile_lambda_string(v5259,OBJECT(lambda,v15454),_oid_(Kernel._void));
              { OID  v1731;
                { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                  (v2072->selector = Kernel.put);
                  (v2072->args = list::alloc(3,Optimize.if_write->value,
                    v5258,
                    _oid_(make_function_string(v5259))));
                  add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                  v1731 = _oid_(v2072);
                  } 
                v5252->addFast(v1731);
                } 
              } 
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(v5258);
        bag *v5258_support;
        v5258_support = OBJECT(bag,nth_table1(Language.relations,v15659));
        for (START(v5258_support); NEXT(v5258);)
        { GC_LOOP;
          if (eventMethod_ask_relation2(OBJECT(ClaireRelation,v5258)) == CTRUE)
           v5252= v5252->addFast(_void_(compileEventMethod_property(OBJECT(property,v5258))));
          GC_UNLOOP;} 
        } 
      { OID  v2692;
        { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
          (v2072->args = v5252);
          add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
          v2692 = _oid_(v2072);
          } 
        Result = (*Optimize.c_code)(v2692,
          _oid_(v5259));
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 

void  compile_if_write_relation(ClaireRelation *v5226)
{ GC_BIND;
  { OID  v5252 = nth_table1(Language.demons,_oid_(v5226));
    list * v13347 = GC_OBJECT(list,OBJECT(demon,(*(OBJECT(bag,v5252)))[1])->formula->vars);
    list * v15405 = list::alloc(Kernel._any,1,GC_OID((*Optimize.Produce_put)(_oid_(v5226),
      (*(v13347))[1],
      (*(v13347))[2])));
    list * v15406;
    { { bag *v_list; OID v_val;
        OID v5264,CLcount;
        v_list = OBJECT(bag,v5252);
         v15406 = v_list->clone(Kernel._any);
        for (CLcount= 1; CLcount <= v_list->length; CLcount++)
        { v5264 = (*(v_list))[CLcount];
          v_val = substitution_any(GC_OID(substitution_any(GC_OID(substitution_any(GC_OID(OBJECT(demon,v5264)->formula->body),OBJECT(Variable,(*(OBJECT(demon,v5264)->formula->vars))[3]),(*(v13347))[3])),OBJECT(Variable,(*(OBJECT(demon,v5264)->formula->vars))[1]),(*(v13347))[1])),OBJECT(Variable,(*(OBJECT(demon,v5264)->formula->vars))[2]),(*(v13347))[2]);
          
          (*((list *) v15406))[CLcount] = v_val;} 
        } 
      GC_OBJECT(list,v15406);} 
    put_property2(Kernel.range,OBJECT(ClaireObject,(*(v13347))[1]),_oid_(v5226->domain));
    put_property2(Kernel.range,OBJECT(ClaireObject,(*(v13347))[2]),_oid_(v5226->range));
    { OID gc_local;
      ITERATE(v5263);
      for (START(v13347); NEXT(v5263);)
      { GC_LOOP;
        put_property2(Kernel.range,OBJECT(ClaireObject,v5263),_oid_(class_I_type(GC_OBJECT(ClaireType,OBJECT(ClaireType,(*Kernel.range)(v5263))))));
        GC_UNLOOP;} 
      } 
    if ((INHERIT(OWNER((*(v15406))[1]),Language._If)) && 
        (eventMethod_ask_relation2(v5226) != CTRUE))
     { if (INHERIT(OWNER(OBJECT(If,(*(v15406))[1])->test),Language._And))
       { If * v10469; 
        OID  v10470;
        v10469 = OBJECT(If,(*(v15406))[1]);
        { And * v2072 = ((And *) GC_OBJECT(And,new_object_class(Language._And)));
          (v2072->args = cdr_list(GC_OBJECT(list,OBJECT(list,(*Core.args)(GC_OID(OBJECT(If,(*(v15406))[1])->test))))));
          add_I_property(Kernel.instances,Language._And,11,_oid_(v2072));
          v10470 = _oid_(v2072);
          } 
        (v10469->test = v10470);} 
      else ((*(v15406))[1]=OBJECT(If,(*(v15406))[1])->arg);
        } 
    if (v5226->inverse != (NULL))
     { if (multi_ask_any(_oid_(v5226)) != CTRUE)
       v15405= v15405->addFast(GC_OID((*Optimize.Produce_remove)(_oid_(v5226->inverse),
        (*(v13347))[3],
        (*(v13347))[1])));
      v15405= v15405->addFast(GC_OID((*Optimize.Produce_put)(_oid_(v5226->inverse),
        (*(v13347))[2],
        (*(v13347))[1])));
      } 
    { ClaireRelation * v10471; 
      OID  v10472;
      v10471 = v5226;
      { lambda * v12100;{ OID  v8458;
          if (eventMethod_ask_relation2(v5226) == CTRUE)
           { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
            (v2072->args = v15406);
            add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
            v8458 = _oid_(v2072);
            } 
          else if (multi_ask_any(_oid_(v5226)) == CTRUE)
           { If * v2072 = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
            { If * v10475; 
              OID  v10497;
              v10475 = v2072;
              { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                (v2072->selector = Core.NOT);
                { Call * v10498; 
                  list * v10499;
                  v10498 = v2072;
                  { OID v_bag;
                    GC_ANY(v10499= list::empty(Kernel.emptySet));
                    { { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                        (v2072->selector = Kernel._Z);
                        (v2072->args = list::alloc(2,(*(v13347))[2],GC_OID(_oid_(readCall_relation(v5226,(*(v13347))[1])))));
                        add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                        v_bag = _oid_(v2072);
                        } 
                      GC_OID(v_bag);} 
                    ((list *) v10499)->addFast(v_bag);} 
                  (v10498->args = v10499);} 
                add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                v10497 = _oid_(v2072);
                } 
              (v10475->test = v10497);} 
            { If * v10500; 
              OID  v10501;
              v10500 = v2072;
              { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                (v2072->args = append_list(v15405,v15406));
                add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                v10501 = _oid_(v2072);
                } 
              (v10500->arg = v10501);} 
            add_I_property(Kernel.instances,Language._If,11,_oid_(v2072));
            (v2072->other = Kernel.cfalse);
            v8458 = _oid_(v2072);
            } 
          else { Let * v2072 = ((Let *) GC_OBJECT(Let,new_object_class(Language._Let)));
              store_object(v2072,
                2,
                Kernel._object,
                (*(v13347))[3],
                CFALSE);
              (v2072->value = _oid_(readCall_relation(v5226,(*(v13347))[1])));
              { Let * v10502; 
                OID  v10504;
                v10502 = v2072;
                { If * v2072 = ((If *) GC_OBJECT(If,new_object_class(Language._If)));
                  { If * v10505; 
                    OID  v10506;
                    v10505 = v2072;
                    { Call * v2072 = ((Call *) GC_OBJECT(Call,new_object_class(Language._Call)));
                      (v2072->selector = Core._I_equal);
                      (v2072->args = list::alloc(2,(*(v13347))[2],(*(v13347))[3]));
                      add_I_property(Kernel.instances,Language._Call,11,_oid_(v2072));
                      v10506 = _oid_(v2072);
                      } 
                    (v10505->test = v10506);} 
                  { If * v10507; 
                    OID  v10528;
                    v10507 = v2072;
                    { Do * v2072 = ((Do *) GC_OBJECT(Do,new_object_class(Language._Do)));
                      (v2072->args = append_list(v15405,v15406));
                      add_I_property(Kernel.instances,Language._Do,11,_oid_(v2072));
                      v10528 = _oid_(v2072);
                      } 
                    (v10507->arg = v10528);} 
                  add_I_property(Kernel.instances,Language._If,11,_oid_(v2072));
                  (v2072->other = Kernel.cfalse);
                  v10504 = _oid_(v2072);
                  } 
                (v10502->arg = v10504);} 
              add_I_property(Kernel.instances,Language._Let,11,_oid_(v2072));
              v8458 = _oid_(v2072);
              } 
            v12100 = lambda_I_list(list::alloc(2,(*(v13347))[1],(*(v13347))[2]),v8458);
          } 
        
        v10472=_oid_(v12100);} 
      (v10471->if_write = v10472);} 
    } 
  GC_UNBIND;} 

void  compileEventMethod_property(property *v5256)
{ GC_BIND;
  { method * v5253 = OBJECT(method,(*(v5256->restrictions))[1]);
    char * v15515 = GC_STRING(append_string(string_I_symbol(v5256->name),"_write"));
    add_method_I_method(v5253,
      list::alloc(2,_oid_(v5256->domain),_oid_(v5256->range)),
      _oid_(Kernel._void),
      0,
      make_function_string(v15515));
    } 
  GC_UNBIND;} 

