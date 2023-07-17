/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-domain.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:38 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2000 - Vincent Barichard - EMN 
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM (gestion des domaines en extension)
// ** Summary : handling domains for PaLM
// *   Part 1 : Tools 
// *   Part 2 : API
// *   Part 3 : Interface for propagation
// ********************************************************************
// *   Part 1 : Tools                                                 *
// ********************************************************************
/* The c++ function for: not!(x:integer) [] */
int  palm_not_I_integer(int x)
{ return (((palm.PALM_BITALLONE->value)-x));} 


/* The c++ function for: sum(l:list[integer]) [] */
int  claire_sum_list(list *l)
{ { int Result = 0;
    { int  n = 0;
      { ITERATE(y);
        for (START(l); NEXT(y);)
        n= (n+y);
        } 
      Result = n;
      } 
    return (Result);} 
  } 


// binary representation => integer
/* The c++ function for: firstCode29bits(ed:PalmIntDomain,x:integer) [] */
int  palm_firstCode29bits_PalmIntDomain(PalmIntDomain *ed,int x)
{ { int Result = 0;
    { int  y = ((x-ed->offset)-1);
      Result = ((y/29)+1);
      } 
    return (Result);} 
  } 


/* The c++ function for: secondCode29bits(ed:PalmIntDomain,x:integer) [] */
int  palm_secondCode29bits_PalmIntDomain(PalmIntDomain *ed,int x)
{ { int Result = 0;
    { int  y = ((x-ed->offset)-1);
      Result = mod_integer(y,29);
      } 
    return (Result);} 
  } 


// integer => binary representation
/* The c++ function for: decode29bits(ed:PalmIntDomain,x:integer,i:(1 .. 29)) [] */
int  palm_decode29bits_PalmIntDomain(PalmIntDomain *ed,int x,int i)
{ return (((((x-1)*29)+i)+ed->offset));} 


// ********************************************************************
// *   Part 2 : API                                                   *
// ********************************************************************
// Building an enumerated domain (input: inf and sup)
/* The c++ function for: makePalmIntDomain(dinf:integer,dsup:integer) [] */
PalmIntDomain * palm_makePalmIntDomain_integer(int dinf,int dsup)
{ GC_BIND;
  ;;{ PalmIntDomain *Result ;
    { int  n = ((dsup-dinf)+1);
      PalmIntDomain * _CL_obj = ((PalmIntDomain *) GC_OBJECT(PalmIntDomain,new_object_class(palm._PalmIntDomain)));
      (_CL_obj->offset = (dinf-1));
      STOREI(_CL_obj->bucketSize,n);
      (_CL_obj->nbElements = n);
      (_CL_obj->firstSuccPres = 1);
      (_CL_obj->firstPrecPres = n);
      (_CL_obj->firstSuccAbs = 0);
      (_CL_obj->firstPrecAbs = 0);
      { PalmIntDomain * g0004; 
        list * g0005;
        g0004 = _CL_obj;
        { list * g0006UU;
          { list * i_bag = list::empty(Kernel._integer);
            { int  i = 1;
              int  g0000 = (n/29);
              { OID gc_local;
                while ((i <= g0000))
                { // HOHO, GC_LOOP not needed !
                  i_bag->addFast(palm.PALM_BITALLONE->value);
                  ++i;
                  } 
                } 
              } 
            g0006UU = GC_OBJECT(list,i_bag);
            } 
          OID  g0007UU;
          { int  V_CL0008;{ list * g0009UU;
              { list * j_bag = list::empty(Kernel._integer);
                { int  j = 0;
                  int  g0001 = (mod_integer(n,29)-1);
                  { OID gc_local;
                    while ((j <= g0001))
                    { // HOHO, GC_LOOP not needed !
                      j_bag->addFast(exp2_integer(j));
                      ++j;
                      } 
                    } 
                  } 
                g0009UU = GC_OBJECT(list,j_bag);
                } 
              V_CL0008 = claire_sum_list(g0009UU);
              } 
            
            g0007UU=V_CL0008;} 
          g0005 = g0006UU->addFast(g0007UU);
          } 
        (g0004->booleanVector = g0005);} 
      { PalmIntDomain * g0010; 
        list * g0011;
        g0010 = _CL_obj;
        { list * g0012UU;
          { list * i_bag = list::empty(Kernel._integer);
            { int  i = 2;
              int  g0002 = n;
              { OID gc_local;
                while ((i <= g0002))
                { // HOHO, GC_LOOP not needed !
                  i_bag->addFast(i);
                  ++i;
                  } 
                } 
              } 
            g0012UU = GC_OBJECT(list,i_bag);
            } 
          g0011 = g0012UU->addFast(0);
          } 
        (g0010->succVector = g0011);} 
      { PalmIntDomain * g0013; 
        list * g0014;
        g0013 = _CL_obj;
        { list * i_bag = list::empty(Kernel._integer);
          { int  i = 0;
            int  g0003 = (n-1);
            { OID gc_local;
              while ((i <= g0003))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast(i);
                ++i;
                } 
              } 
            } 
          g0014 = GC_OBJECT(list,i_bag);
          } 
        (g0013->precVector = g0014);} 
      (_CL_obj->explanationOnVal = make_list_integer2(n,GC_OBJECT(ClaireType,U_type(palm._PalmRemovalExplanation,set::alloc(Kernel.emptySet,1,CNULL))),CNULL));
      (_CL_obj->instantiationConstraints = make_list_integer2(n,OBJECT(ClaireType,palm.AbstractConstraint->value),CNULL));
      (_CL_obj->negInstantiationConstraints = make_list_integer2(n,OBJECT(ClaireType,palm.AbstractConstraint->value),CNULL));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// pretty printing 
/* The c++ function for: self_print(ed:PalmIntDomain) [] */
OID  claire_self_print_PalmIntDomain_palm(PalmIntDomain *ed)
{ GC_BIND;
  { OID Result = 0;
    { set * s = GC_OBJECT(set,OBJECT(set,_oid_((ClaireObject *) choco.domainSet->fcall(((int) ed)))));
      int  si = size_set(s);
      if (si <= 4)
       { { OID  g0015UU;
          { { list * V_CL0016;{ bag *v_list; OID v_val;
                OID x,CLcount;
                v_list = s;
                 V_CL0016 = v_list->clone();
                for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                { x = (*(v_list))[CLcount];
                  v_val = x;
                  
                  (*((list *) V_CL0016))[CLcount] = v_val;} 
                } 
              
              g0015UU=_oid_(V_CL0016);} 
            GC_OID(g0015UU);} 
          print_any(g0015UU);
          } 
        princ_string("");
        } 
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
    GC_UNBIND; return (Result);} 
  } 


// for compatbilility with 1.07
// PalmIntDomain => list[integer]
/* The c++ function for: choco/domainSequence(ed:PalmIntDomain) [] */
list * choco_domainSequence_PalmIntDomain_palm(PalmIntDomain *ed)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { list *Result ;
    { list * s = list::empty(Kernel._integer);
      int  z = ed->firstSuccPres;
      { OID gc_local;
        while ((z != 0))
        { GC_LOOP;
          GC__ANY(s = s->addFast((z+ed->offset)), 1);
          z= (*(ed->succVector))[z];
          ;GC_UNLOOP;} 
        } 
      Result = s;
      } 
    GC_UNBIND; return (Result);} 
  } 


// PalmIntDomain => set[integer]
/* The c++ function for: choco/domainSet(ed:PalmIntDomain) [] */
set * choco_domainSet_PalmIntDomain_palm(PalmIntDomain *ed)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { set *Result ;
    { set * s = set::empty(Kernel._integer);
      int  z = ed->firstSuccPres;
      { OID gc_local;
        while ((z != 0))
        { GC_LOOP;
          GC__ANY(s = s->addFast((z+ed->offset)), 1);
          z= (*(ed->succVector))[z];
          ;GC_UNLOOP;} 
        } 
      Result = s;
      } 
    GC_UNBIND; return (Result);} 
  } 


// complement(PalmIntDomain) => list[integer]
/* The c++ function for: removedlist!(ed:PalmIntDomain) [] */
list * palm_removedlist_I_PalmIntDomain(PalmIntDomain *ed)
{ { list *Result ;
    { list * s = list::empty();
      int  z = ed->firstSuccAbs;
      { while ((z != 0))
        { s= s->addFast((z+ed->offset));
          z= (*(ed->succVector))[z];
          ;} 
        } 
      Result = s;
      } 
    return (Result);} 
  } 


// size of the domain
/* The c++ function for: choco/getDomainCard(ed:PalmIntDomain) [] */
int  choco_getDomainCard_PalmIntDomain_palm(PalmIntDomain *ed)
{ return (ed->nbElements);} 


// Testing membership of a given x
/* The c++ function for: choco/containsValInDomain(ed:PalmIntDomain,x:integer) [] */
ClaireBoolean * choco_containsValInDomain_PalmIntDomain_palm(PalmIntDomain *ed,int x)
{ { ClaireBoolean *Result ;
    { int  w;
      { int  g0017 = ((x-ed->offset)-1);
        w = ((g0017/29)+1);
        } 
      int  i;
      { int  g0018 = ((x-ed->offset)-1);
        i = mod_integer(g0018,29);
        } 
      Result = ((1 <= (x-ed->offset)) ? (((x-ed->offset) <= ed->bucketSize) ? ((BCONTAIN((*(ed->booleanVector))[w],i)) ? CTRUE: CFALSE): CFALSE): CFALSE);
      } 
    return (Result);} 
  } 


// The first present element (MAXINT if empty domain)
/* The c++ function for: firstElement(ed:PalmIntDomain) [] */
int  palm_firstElement_PalmIntDomain(PalmIntDomain *ed)
{ { int Result = 0;
    Result = ((ed->firstSuccPres == 0) ?
      99999999 :
      (ed->firstSuccPres+ed->offset) );
    return (Result);} 
  } 


// What is the smallest element present in the domain ?
/* The c++ function for: choco/getInf(ed:PalmIntDomain) [] */
int  choco_getInf_PalmIntDomain_palm(PalmIntDomain *ed)
{ GC_BIND;
  { int Result = 0;
    { list * l = GC_OBJECT(list,ed->booleanVector);
      int  w = 1;
      int  i = 0;
      { while (((w <= l->length) && 
            ((*(l))[w] == 0)))
        { ++w;
          } 
        } 
      if (w <= l->length)
       { { while ((!BCONTAIN((*(l))[w],i)))
          { ++i;
            } 
          } 
        Result = palm_decode29bits_PalmIntDomain(ed,w,(i+1));
        } 
      else Result = 99999999;
        } 
    GC_UNBIND; return (Result);} 
  } 


// What is the greatest element present in the domain ?
/* The c++ function for: choco/getSup(ed:PalmIntDomain) [] */
int  choco_getSup_PalmIntDomain_palm(PalmIntDomain *ed)
{ GC_BIND;
  { int Result = 0;
    { list * l = GC_OBJECT(list,ed->booleanVector);
      int  w = l->length;
      int  i = 28;
      { while (((w > 0) && 
            ((*(l))[w] == 0)))
        { w= (w-1);
          } 
        } 
      if (w > 0)
       { { while ((!BCONTAIN((*(l))[w],i)))
          { i= (i-1);
            } 
          } 
        Result = palm_decode29bits_PalmIntDomain(ed,w,(i+1));
        } 
      else Result = -99999999;
        } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 3 : Interface for propagation                             *
// ********************************************************************
// adding a given value (z) to the domain
/* The c++ function for: addDomainVal(ed:PalmIntDomain,z:integer) [] */
void  palm_addDomainVal_PalmIntDomain(PalmIntDomain *ed,int z)
{ { int  x = (z-ed->offset);
    int  w;
    { int  g0019 = ((z-ed->offset)-1);
      w = ((g0019/29)+1);
      } 
    int  i;
    { int  g0020 = ((z-ed->offset)-1);
      i = mod_integer(g0020,29);
      } 
    if ((1 <= x) && 
        ((x <= ed->bucketSize) && 
          (!BCONTAIN((*(ed->booleanVector))[w],i))))
     { STOREI((*ed->booleanVector)[w],(((*(ed->booleanVector))[w])+exp2_integer(i)));
      { int  succAbsX = (*(ed->succVector))[x];
        int  precAbsX = (*(ed->precVector))[x];
        if (precAbsX == 0)
         { (ed->firstSuccAbs = succAbsX);
          if (succAbsX == 0)
           (ed->firstPrecAbs = 0);
          else STOREI((*ed->precVector)[succAbsX],0);
            } 
        else { STOREI((*ed->succVector)[precAbsX],succAbsX);
            if (succAbsX == 0)
             (ed->firstPrecAbs = precAbsX);
            else STOREI((*ed->precVector)[succAbsX],precAbsX);
              } 
          } 
      } 
    if (ed->firstSuccPres == 0)
     (ed->firstSuccPres = x);
    else STOREI((*ed->succVector)[ed->firstPrecPres],x);
      STOREI((*ed->succVector)[x],0);
    STOREI((*ed->precVector)[x],ed->firstPrecPres);
    (ed->firstPrecPres = x);
    (ed->nbElements = (ed->nbElements+1));
    } 
  } 


// removing a given value (z) from the domain
/* The c++ function for: choco/removeDomainVal(ed:PalmIntDomain,z:integer) [] */
ClaireBoolean * choco_removeDomainVal_PalmIntDomain_palm(PalmIntDomain *ed,int z)
{ { ClaireBoolean *Result ;
    { int  x = (z-ed->offset);
      int  w;
      { int  g0021 = ((z-ed->offset)-1);
        w = ((g0021/29)+1);
        } 
      int  i;
      { int  g0022 = ((z-ed->offset)-1);
        i = mod_integer(g0022,29);
        } 
      if ((1 <= x) && 
          ((x <= ed->bucketSize) && 
            (BCONTAIN((*(ed->booleanVector))[w],i))))
       { STOREI((*ed->booleanVector)[w],(((*(ed->booleanVector))[w])-exp2_integer(i)));
        { int  succX = (*(ed->succVector))[x];
          int  precX = (*(ed->precVector))[x];
          if (precX == 0)
           { (ed->firstSuccPres = succX);
            if (succX == 0)
             (ed->firstPrecPres = 0);
            else STOREI((*ed->precVector)[succX],0);
              } 
          else { STOREI((*ed->succVector)[precX],succX);
              if (succX == 0)
               (ed->firstPrecPres = precX);
              else STOREI((*ed->precVector)[succX],precX);
                } 
            if (ed->firstSuccAbs == 0)
           (ed->firstSuccAbs = x);
          else STOREI((*ed->succVector)[ed->firstPrecAbs],x);
            STOREI((*ed->succVector)[x],0);
          STOREI((*ed->precVector)[x],ed->firstPrecAbs);
          (ed->firstPrecAbs = x);
          (ed->nbElements = (ed->nbElements-1));
          Result = CTRUE;
          } 
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 

