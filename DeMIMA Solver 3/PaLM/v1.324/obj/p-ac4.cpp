/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-ac4.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:41 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2000 - Vincent Barichard - EMN 
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM (ici: gestion des domaines en extension)
// ** Summary : Propagating extensive constraints using AC4
// *   Part 1 : Constraint declaration                         
// *   Part 2 : API                                            
// *   Part 3 : Propagation                                    
// *   Part 4 : Data structure management                      
// *************************************************************
// *   Part 1 : Constraint declaration                         *
// *************************************************************
// *************************************************************
// *   Part 2 : API                                            *
// *************************************************************
// pretty printing 
/* The c++ function for: self_print(c:PalmAC4BinConstraint) [] */
OID  claire_self_print_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c)
{ princ_string("<AC4>");
  if (c->cachedTuples != CTRUE)
   { princ_string("<");
    print_any(_oid_(c->feasTest->selector));
    princ_string(">");
    } 
  { OID Result = 0;
    { princ_string("(");
      princ_string(c->v1->name);
      princ_string(",");
      princ_string(c->v2->name);
      princ_string(")");
      } 
    return (Result);} 
  } 


// constraint creation
/* The c++ function for: makeAC4constraint(u:PalmIntVar,v:PalmIntVar,feasible:boolean,mytuples:list<list<integer>>) [] */
PalmAC4BinConstraint * palm_makeAC4constraint_PalmIntVar(PalmIntVar *u,PalmIntVar *v,ClaireBoolean *feasible,list *mytuples)
{ GC_BIND;
  { PalmAC4BinConstraint *Result ;
    { PalmAC4BinConstraint * c = GC_OBJECT(PalmAC4BinConstraint,((PalmAC4BinConstraint *) palm_makePalmBinIntConstraint_class(palm._PalmAC4BinConstraint,u,v,0)));
      int  sizeV1 = CLREAD(PalmIntDomain,u->bucket,bucketSize);
      int  sizeV2 = CLREAD(PalmIntDomain,v->bucket,bucketSize);
      int  offV1 = CLREAD(PalmIntDomain,u->bucket,offset);
      int  offV2 = CLREAD(PalmIntDomain,v->bucket,offset);
      (c->matrix = choco_make2DBoolMatrix_integer(1,
        sizeV1,
        1,
        sizeV2,
        not_any(_oid_(feasible)),
        CFALSE));
      (c->nbSupportsOnV1 = make_list_integer2(sizeV1,Kernel._integer,0));
      (c->nbSupportsOnV2 = make_list_integer2(sizeV2,Kernel._integer,0));
      { PalmAC4BinConstraint * g0215; 
        list * g0216;
        g0215 = c;
        { list * i_bag = list::empty(param_I_class(Kernel._set,Kernel._integer));
          { int  i = 1;
            int  g0209 = sizeV1;
            { OID gc_local;
              while ((i <= g0209))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast(_oid_(set::empty(Kernel._integer)));
                ++i;
                } 
              } 
            } 
          g0216 = GC_OBJECT(list,i_bag);
          } 
        (g0215->supportsOnV1 = g0216);} 
      { PalmAC4BinConstraint * g0217; 
        list * g0218;
        g0217 = c;
        { list * i_bag = list::empty(param_I_class(Kernel._set,Kernel._integer));
          { int  i = 1;
            int  g0210 = sizeV2;
            { OID gc_local;
              while ((i <= g0210))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast(_oid_(set::empty(Kernel._integer)));
                ++i;
                } 
              } 
            } 
          g0218 = GC_OBJECT(list,i_bag);
          } 
        (g0217->supportsOnV2 = g0218);} 
      { int  i = 1;
        int  g0211 = sizeV1;
        { OID gc_local;
          while ((i <= g0211))
          { // HOHO, GC_LOOP not needed !
            ((*(c->supportsOnV1))[i]=_oid_(set::empty(Kernel._integer)));
            ++i;
            } 
          } 
        } 
      { int  i = 1;
        int  g0212 = sizeV2;
        { OID gc_local;
          while ((i <= g0212))
          { // HOHO, GC_LOOP not needed !
            ((*(c->supportsOnV2))[i]=_oid_(set::empty(Kernel._integer)));
            ++i;
            } 
          } 
        } 
      if (feasible != CTRUE)
       { int  i = 1;
        int  g0213 = sizeV1;
        { OID gc_local;
          while ((i <= g0213))
          { // HOHO, GC_LOOP not needed !
            { int  j = 1;
              int  g0214 = sizeV2;
              { OID gc_local;
                while ((j <= g0214))
                { // HOHO, GC_LOOP not needed !
                  ((*(c->supportsOnV1))[i]=_oid_(add_set(OBJECT(set,(*(c->supportsOnV1))[i]),j)));
                  ((*(c->nbSupportsOnV1))[i]=(((*(c->nbSupportsOnV1))[i])+1));
                  ((*(c->supportsOnV2))[j]=_oid_(add_set(OBJECT(set,(*(c->supportsOnV2))[j]),i)));
                  ((*(c->nbSupportsOnV2))[j]=(((*(c->nbSupportsOnV2))[j])+1));
                  ++j;
                  } 
                } 
              } 
            ++i;
            } 
          } 
        } 
      { OID gc_local;
        ITERATE(t);
        for (START(mytuples); NEXT(t);)
        { GC_LOOP;
          { int  i = (((*(OBJECT(bag,t)))[1])-offV1);
            int  j = (((*(OBJECT(bag,t)))[2])-offV2);
            if ((i < 1) || 
                ((i > CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),bucketSize)) || 
                  ((j < 1) || 
                    (j > CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),bucketSize)))))
             close_exception(((general_error *) (*Core._general_error)(_string_("**** PaLM error : Can't add tuple (~S, ~S), the values are not in the domain\n"),
              _oid_(list::alloc(2,(*(OBJECT(bag,t)))[1],(*(OBJECT(bag,t)))[2])))));
            else { claire_store_BoolMatrix2D(GC_OBJECT(BoolMatrix2D,c->matrix),i,j,feasible);
                if (feasible == CTRUE)
                 { ((*(c->supportsOnV1))[i]=_oid_(add_set(OBJECT(set,(*(c->supportsOnV1))[i]),j)));
                  ((*(c->nbSupportsOnV1))[i]=(((*(c->nbSupportsOnV1))[i])+1));
                  ((*(c->supportsOnV2))[j]=_oid_(add_set(OBJECT(set,(*(c->supportsOnV2))[j]),i)));
                  ((*(c->nbSupportsOnV2))[j]=(((*(c->nbSupportsOnV2))[j])+1));
                  } 
                else { ((*(c->supportsOnV1))[i]=_oid_(delete_bag(OBJECT(bag,(*(c->supportsOnV1))[i]),j)));
                    ((*(c->nbSupportsOnV1))[i]=(((*(c->nbSupportsOnV1))[i])-1));
                    ((*(c->supportsOnV2))[j]=_oid_(delete_bag(OBJECT(bag,(*(c->supportsOnV2))[j]),i)));
                    ((*(c->nbSupportsOnV2))[j]=(((*(c->nbSupportsOnV2))[j])-1));
                    } 
                  } 
              } 
          GC_UNLOOP;} 
        } 
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// *************************************************************
// *   Part 3 : Propagation                                    *
// *************************************************************
/* The c++ function for: choco/testIfSatisfied(c:PalmAC4BinConstraint) [] */
ClaireBoolean * choco_testIfSatisfied_PalmAC4BinConstraint(PalmAC4BinConstraint *c)
{ if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) && 
      ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE)) 
  { { ClaireBoolean *Result ;
      Result = ((c->cachedTuples == CTRUE) ?
        claire_read_BoolMatrix2D(GC_OBJECT(BoolMatrix2D,c->matrix),c->v1->value,c->v2->value) :
        OBJECT(ClaireBoolean,funcall_method2(c->feasTest,c->v1->value,c->v2->value)) );
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireBoolean *Result ;
      Result = CFALSE;
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: choco/awake(c:PalmAC4BinConstraint) [] */
void  choco_awake_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    { int  v = 1;
      int  g0219 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),bucketSize);
      { OID gc_local;
        while ((v <= g0219))
        { GC_LOOP;
          ((*(c->nbSupportsOnV1))[v]=0);
          { OID gc_local;
            ITERATE(x);
            for (START(OBJECT(bag,(*(c->supportsOnV1))[v])); NEXT(x);)
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket))))),((int) (x+o2)))))) == CTRUE)
               ((*(c->nbSupportsOnV1))[v]=(((*(c->nbSupportsOnV1))[v])+1));
              GC_UNLOOP;} 
            } 
          ++v;
          GC_UNLOOP;} 
        } 
      } 
    { int  v = 1;
      int  g0220 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),bucketSize);
      { OID gc_local;
        while ((v <= g0220))
        { GC_LOOP;
          ((*(c->nbSupportsOnV2))[v]=0);
          { OID gc_local;
            ITERATE(x);
            for (START(OBJECT(bag,(*(c->supportsOnV2))[v])); NEXT(x);)
            { GC_LOOP;
              if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket))))),((int) (x+o1)))))) == CTRUE)
               ((*(c->nbSupportsOnV2))[v]=(((*(c->nbSupportsOnV2))[v])+1));
              GC_UNLOOP;} 
            } 
          ++v;
          GC_UNLOOP;} 
        } 
      } 
    { Explanation * expl;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          expl = _CL_obj;
          } 
        GC_OBJECT(Explanation,expl);} 
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket)))))))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if ((*(c->nbSupportsOnV1))[(x-o1)] == 0)
           { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
               GC__ANY(expl, 5);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { OID gc_local;
              ITERATE(y);
              for (START(OBJECT(bag,(*(c->supportsOnV1))[(x-o1)])); NEXT(y);)
              { GC_LOOP;
                palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(y+o2),expl);
                GC_UNLOOP;} 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              x,
              c->idx1,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket)))))))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          if ((*(c->nbSupportsOnV2))[(x-o2)] == 0)
           { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
               GC__ANY(expl, 5);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { OID gc_local;
              ITERATE(y);
              for (START(OBJECT(bag,(*(c->supportsOnV2))[(x-o2)])); NEXT(y);)
              { GC_LOOP;
                palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(y+o1),expl);
                GC_UNLOOP;} 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v2)),
              x,
              c->idx2,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      } 
    } 
  ;GC_UNBIND;} 


/* The c++ function for: choco/propagate(c:PalmAC4BinConstraint) [] */
void  choco_propagate_PalmAC4BinConstraint(PalmAC4BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    { OID gc_local;
      ITERATE(x);
      bag *x_support;
      x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket)))))))));
      for (START(x_support); NEXT(x);)
      { GC_LOOP;
        if ((*(c->nbSupportsOnV1))[(x-o1)] == 0)
         { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              expl = _CL_obj;
              } 
             GC__ANY(expl, 3);} 
          palm_self_explain_AbstractConstraint(c,expl);
          { OID gc_local;
            ITERATE(y);
            for (START(OBJECT(bag,(*(c->supportsOnV1))[(x-o1)])); NEXT(y);)
            { GC_LOOP;
              palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(y+o2),expl);
              GC_UNLOOP;} 
            } 
          (*choco.removeVal)(GC_OID(_oid_(c->v1)),
            x,
            c->idx1,
            _oid_(expl));
          } 
        GC_UNLOOP;} 
      } 
    { OID gc_local;
      ITERATE(x);
      bag *x_support;
      x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket)))))))));
      for (START(x_support); NEXT(x);)
      { GC_LOOP;
        if ((*(c->nbSupportsOnV2))[(x-o2)] == 0)
         { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              expl = _CL_obj;
              } 
             GC__ANY(expl, 3);} 
          palm_self_explain_AbstractConstraint(c,expl);
          { OID gc_local;
            ITERATE(y);
            for (START(OBJECT(bag,(*(c->supportsOnV2))[(x-o2)])); NEXT(y);)
            { GC_LOOP;
              palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(y+o1),expl);
              GC_UNLOOP;} 
            } 
          (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            x,
            c->idx2,
            _oid_(expl));
          } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnRem(c:PalmAC4BinConstraint,idx:integer,v:integer) [] */
void  choco_awakeOnRem_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx,int v)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    if (idx == 1)
     { OID gc_local;
      ITERATE(x);
      for (START(OBJECT(bag,(*(c->supportsOnV1))[(v-o1)])); NEXT(x);)
      { GC_LOOP;
        if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v2,bucket))))),((int) (x+o2)))))) == CTRUE) && 
            ((*(c->nbSupportsOnV2))[x] == 0))
         { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              expl = _CL_obj;
              } 
             GC__ANY(expl, 5);} 
          palm_self_explain_AbstractConstraint(c,expl);
          { OID gc_local;
            ITERATE(y);
            for (START(OBJECT(bag,(*(c->supportsOnV2))[x])); NEXT(y);)
            { GC_LOOP;
              palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(y+o1),expl);
              GC_UNLOOP;} 
            } 
          (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            (x+o2),
            c->idx2,
            _oid_(expl));
          } 
        GC_UNLOOP;} 
      } 
    else { OID gc_local;
        ITERATE(x);
        for (START(OBJECT(bag,(*(c->supportsOnV2))[(v-o2)])); NEXT(x);)
        { GC_LOOP;
          if (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.containsValInDomain->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(CLREAD(PalmIntVar,c->v1,bucket))))),((int) (x+o1)))))) == CTRUE) && 
              ((*(c->nbSupportsOnV1))[x] == 0))
           { { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
               GC__ANY(expl, 5);} 
            palm_self_explain_AbstractConstraint(c,expl);
            { OID gc_local;
              ITERATE(y);
              for (START(OBJECT(bag,(*(c->supportsOnV1))[x])); NEXT(y);)
              { GC_LOOP;
                palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(y+o2),expl);
                GC_UNLOOP;} 
              } 
            (*choco.removeVal)(GC_OID(_oid_(c->v1)),
              (x+o1),
              c->idx1,
              _oid_(expl));
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnRestoreVal(c:PalmAC4BinConstraint,idx:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx,int v)
{ GC_BIND;
  { Explanation * expl;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        expl = _CL_obj;
        } 
      GC_OBJECT(Explanation,expl);} 
    int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    if (idx == 1)
     { if ((*(c->nbSupportsOnV1))[(v-o1)] == 0)
       { palm_self_explain_AbstractConstraint(c,expl);
        { OID gc_local;
          ITERATE(y);
          for (START(OBJECT(bag,(*(c->supportsOnV1))[(v-o2)])); NEXT(y);)
          { GC_LOOP;
            palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v2)),3,(y+o2),expl);
            GC_UNLOOP;} 
          } 
        (*choco.removeVal)(GC_OID(_oid_(c->v1)),
          v,
          c->idx1,
          _oid_(expl));
        } 
      } 
    else if ((*(c->nbSupportsOnV2))[(v-o2)] == 0)
     { palm_self_explain_AbstractConstraint(c,expl);
      { OID gc_local;
        ITERATE(y);
        for (START(OBJECT(bag,(*(c->supportsOnV2))[(v-o2)])); NEXT(y);)
        { GC_LOOP;
          palm_self_explain_PalmIntVar2(GC_OBJECT(PalmIntVar,((PalmIntVar *) c->v1)),3,(y+o2),expl);
          GC_UNLOOP;} 
        } 
      (*choco.removeVal)(GC_OID(_oid_(c->v2)),
        v,
        c->idx2,
        _oid_(expl));
      } 
    } 
  GC_UNBIND;} 


// *************************************************************
// *   Part 4 : Data structure management                      *
// *************************************************************
/* The c++ function for: updateDataStructuresOnConstraint(c:PalmAC4BinConstraint,idx:integer,way:{1, 2, 3, 4},v:integer,unused:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c,int idx,int way,int v,int unused)
{ { int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    if (idx == 1)
     { ITERATE(x);
      for (START(OBJECT(bag,(*(c->supportsOnV1))[(v-o1)])); NEXT(x);)
      ((*(c->nbSupportsOnV2))[x]=(((*(c->nbSupportsOnV2))[x])-1));
      } 
    else { ITERATE(x);
        for (START(OBJECT(bag,(*(c->supportsOnV2))[(v-o2)])); NEXT(x);)
        ((*(c->nbSupportsOnV1))[x]=(((*(c->nbSupportsOnV1))[x])-1));
        } 
      } 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:PalmAC4BinConstraint,idx:integer,way:{1, 2, 3, 4},v:integer,unused:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmAC4BinConstraint_palm(PalmAC4BinConstraint *c,int idx,int way,int v,int unused)
{ { int  o1 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v1,bucket),offset);
    int  o2 = CLREAD(PalmIntDomain,CLREAD(PalmIntVar,c->v2,bucket),offset);
    if (idx == 1)
     { ITERATE(x);
      for (START(OBJECT(bag,(*(c->supportsOnV1))[(v-o1)])); NEXT(x);)
      ((*(c->nbSupportsOnV2))[x]=(((*(c->nbSupportsOnV2))[x])+1));
      } 
    else { ITERATE(x);
        for (START(OBJECT(bag,(*(c->supportsOnV2))[(v-o2)])); NEXT(x);)
        ((*(c->nbSupportsOnV1))[x]=(((*(c->nbSupportsOnV1))[x])+1));
        } 
      } 
  } 


/* The c++ function for: choco/awakeOnInf(c:PalmAC4BinConstraint,i:integer) [] */
void  choco_awakeOnInf_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnSup(c:PalmAC4BinConstraint,i:integer) [] */
void  choco_awakeOnSup_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int i)
{ ;} 


/* The c++ function for: choco/awakeOnInst(c:PalmAC4BinConstraint,idx:integer) [] */
void  choco_awakeOnInst_PalmAC4BinConstraint(PalmAC4BinConstraint *c,int idx)
{ ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmAC4BinConstraint) [] */
OID  choco_askIfEntailed_PalmAC4BinConstraint(PalmAC4BinConstraint *c)
{ return (CNULL);} 


/* The c++ function for: choco/testIfCompletelyInstantiated(c:PalmAC4BinConstraint) [] */
ClaireBoolean * choco_testIfCompletelyInstantiated_PalmAC4BinConstraint(PalmAC4BinConstraint *c)
{ return (CFALSE);} 


/* The c++ function for: checkPalm(ct:PalmAC4BinConstraint) [] */
ClaireBoolean * palm_checkPalm_PalmAC4BinConstraint(PalmAC4BinConstraint *ct)
{ return (CTRUE);} 


// registering the constraint within choco mechanims
// claire3 port register no longer used