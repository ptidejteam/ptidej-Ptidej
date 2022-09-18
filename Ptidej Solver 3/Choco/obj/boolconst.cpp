/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\boolconst.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:29 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: bool.cl                                                    *
// *    boolean connectors                                            *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: Abstract classes for boolean connectors                *
// *   Part 2: Simple binary compositions (or, and, implies, equiv)   *
// *   Part 3: Large compositions (or, and)                           *
// *   Part 4: Cardinality constraint                                 *
// *   Part 5: Negating a constraint                                  *
// --------------------------------------------------------------------
// v1.02 new encoding for Boolean connectors
//   status_i = unknown <=> no idea about validity of c.const_i
//   status_i = true    <=> c.const_i has been proved true
//   status_i = false   <=> c.const_i has been proved false
//   targetStatus_i = unknown <=> no idea whether c.const_i should be true or not (given the state c.const2, choice points, ...)
//   targetStatus_i = true    <=> c.const_i should be true (and is therefore propagated)
//   targetStatus_i = false   <=> c.const_i should be false (and its opposite is therefore propagated)
// ********************************************************************
// *   Part 1: Abstract classes for boolean connectors                *
// ********************************************************************
// v1.02: a new compact an clean encoding of subconstraint status within Boolean constraints:
// using a bitvector to store four three-valued data (true, false, unknown): status/targetStatus of c.const1/2
// Each data is stored on two bits: 
//   - the first bit indicates whether the data is known or unknown
//   - the second bit indicates whether the data is true or false
//  Therefor the second bit is meaningful only when the first one is true.
// v1.02: extensions: methods for initializing all status information to unknown
/* The c++ function for: closeBoolConstraint(c:BinCompositeConstraint) [] */
void  choco_closeBoolConstraint_BinCompositeConstraint(BinCompositeConstraint *c)
{ choco_closeCompositeConstraint_BinCompositeConstraint(c);
  STOREI(CLREAD(BinBoolConstraint,c,statusBitVector),0);
  } 


/* The c++ function for: knownStatus(bbc:BinBoolConstraint,i:integer) [] */
ClaireBoolean * choco_knownStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i)
{ return (nth_integer(bbc->statusBitVector,(4*(i-1))));} 


/* The c++ function for: getStatus(bbc:BinBoolConstraint,i:integer) [] */
ClaireBoolean * choco_getStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i)
{ return (nth_integer(bbc->statusBitVector,((4*(i-1))+1)));} 


/* The c++ function for: knownTargetStatus(bbc:BinBoolConstraint,i:integer) [] */
ClaireBoolean * choco_knownTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i)
{ return (nth_integer(bbc->statusBitVector,((4*(i-1))+2)));} 


/* The c++ function for: getTargetStatus(bbc:BinBoolConstraint,i:integer) [] */
ClaireBoolean * choco_getTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i)
{ return (nth_integer(bbc->statusBitVector,((4*(i-1))+3)));} 


/* The c++ function for: setStatus(bbc:BinBoolConstraint,i:integer,b:boolean) [] */
void  choco_setStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i,ClaireBoolean *b)
{ ;{ int  bv = bbc->statusBitVector;
    int  j = (4*(i-1));
    bv= (bv+exp2_integer(j));
    if ((b == CTRUE) && 
        (!BCONTAIN(bbc->statusBitVector,((4*(i-1))+1))))
     bv= (bv+exp2_integer((j+1)));
    else if ((b != CTRUE) && 
        (BCONTAIN(bbc->statusBitVector,((4*(i-1))+1))))
     bv= (bv-exp2_integer((j+1)));
    STOREI(bbc->statusBitVector,bv);
    ;} 
  } 


/* The c++ function for: setTargetStatus(bbc:BinBoolConstraint,i:integer,b:boolean) [] */
void  choco_setTargetStatus_BinBoolConstraint(BinBoolConstraint *bbc,int i,ClaireBoolean *b)
{ ;{ int  bv = bbc->statusBitVector;
    int  j = (4*(i-1));
    bv= (bv+exp2_integer((j+2)));
    if ((b == CTRUE) && 
        (!BCONTAIN(bbc->statusBitVector,((4*(i-1))+3))))
     bv= (bv+exp2_integer((j+3)));
    else if ((b != CTRUE) && 
        (BCONTAIN(bbc->statusBitVector,((4*(i-1))+1))))
     bv= (bv-exp2_integer((j+3)));
    STOREI(bbc->statusBitVector,bv);
    ;} 
  } 


// v1.02: a new implementations with new bitvectors
//  4 bits per subconstraint (2 per status, 2 per targetStatus)
//  => store 7 subconstraint per integer
// lstatus[i] = true    <=> const[i] has been proven true
// lstatus[i] = false   <=> const[i] has been proven false
// lstatus[i] = unknown <=> no idea about validity of const[i]
//   c.nbTrueStatus = count({i in (1 .. c.nbConst | c.lstatus[i] = true})
//   c.nbFalseStatus = count({i in (1 .. c.nbConst | c.lstatus[i] = false})
// claire3 port: strongly typed lists
// v1.02: extensions: methods for initializing all status information to unknown
/* The c++ function for: closeBoolConstraint(c:LargeCompositeConstraint) [] */
void  choco_closeBoolConstraint_LargeCompositeConstraint(LargeCompositeConstraint *c)
{ choco_closeCompositeConstraint_LargeCompositeConstraint(c);
  (CLREAD(LargeBoolConstraint,c,statusBitVectorList) = make_list_integer2(((c->nbConst/7)+1),Kernel._integer,0));
  } 


/* The c++ function for: knownStatus(lbc:LargeBoolConstraint,i:integer) [] */
ClaireBoolean * choco_knownStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i)
{ { ClaireBoolean *Result ;
    { int  i1 = ((i/7)+1);
      int  i2 = (4*mod_integer((i-1),7));
      Result = nth_integer((*(lbc->statusBitVectorList))[i1],i2);
      } 
    return (Result);} 
  } 


/* The c++ function for: getStatus(lbc:LargeBoolConstraint,i:integer) [] */
ClaireBoolean * choco_getStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i)
{ { ClaireBoolean *Result ;
    { int  i1 = ((i/7)+1);
      int  i2 = (4*mod_integer((i-1),7));
      Result = nth_integer((*(lbc->statusBitVectorList))[i1],(i2+1));
      } 
    return (Result);} 
  } 


/* The c++ function for: knownTargetStatus(lbc:LargeBoolConstraint,i:integer) [] */
ClaireBoolean * choco_knownTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i)
{ { ClaireBoolean *Result ;
    { int  i1 = ((i/7)+1);
      int  i2 = (4*mod_integer((i-1),7));
      Result = nth_integer((*(lbc->statusBitVectorList))[i1],(i2+2));
      } 
    return (Result);} 
  } 


/* The c++ function for: getTargetStatus(lbc:LargeBoolConstraint,i:integer) [] */
ClaireBoolean * choco_getTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i)
{ { ClaireBoolean *Result ;
    { int  i1 = ((i/7)+1);
      int  i2 = (4*mod_integer((i-1),7));
      Result = nth_integer((*(lbc->statusBitVectorList))[i1],(i2+3));
      } 
    return (Result);} 
  } 


/* The c++ function for: setStatus(lbc:LargeBoolConstraint,i:integer,b:boolean) [] */
void  choco_setStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i,ClaireBoolean *b)
{ ;{ int  i1 = ((i/7)+1);
    int  i2 = (4*mod_integer((i-1),7));
    list * lbv = lbc->statusBitVectorList;
    int  bv = (*(lbv))[i1];
    bv= (bv+exp2_integer(i2));
    { ClaireBoolean * g0784I;
      { ClaireBoolean *v_and;
        { v_and = b;
          if (v_and == CFALSE) g0784I =CFALSE; 
          else { { OID  g0785UU;
              { int  g0780 = ((i/7)+1);
                int  g0781 = (4*mod_integer((i-1),7));
                g0785UU = _oid_(nth_integer((*(lbc->statusBitVectorList))[g0780],(g0781+1)));
                } 
              v_and = not_any(g0785UU);
              } 
            if (v_and == CFALSE) g0784I =CFALSE; 
            else g0784I = CTRUE;} 
          } 
        } 
      
      if (g0784I == CTRUE) bv= (bv+exp2_integer((i2+1)));
        else { ClaireBoolean * g0786I;
        { ClaireBoolean *v_and;
          { v_and = not_any(_oid_(b));
            if (v_and == CFALSE) g0786I =CFALSE; 
            else { { int  g0782 = ((i/7)+1);
                int  g0783 = (4*mod_integer((i-1),7));
                v_and = nth_integer((*(lbc->statusBitVectorList))[g0782],(g0783+1));
                } 
              if (v_and == CFALSE) g0786I =CFALSE; 
              else g0786I = CTRUE;} 
            } 
          } 
        
        if (g0786I == CTRUE) bv= (bv-exp2_integer((i2+1)));
          } 
      } 
    STOREI((*lbc->statusBitVectorList)[i1],bv);
    ;} 
  } 


/* The c++ function for: setTargetStatus(lbc:LargeBoolConstraint,i:integer,b:boolean) [] */
void  choco_setTargetStatus_LargeBoolConstraint(LargeBoolConstraint *lbc,int i,ClaireBoolean *b)
{ ;{ int  i1 = ((i/7)+1);
    int  i2 = (4*mod_integer((i-1),7));
    list * lbv = lbc->statusBitVectorList;
    int  bv = (*(lbv))[i1];
    bv= (bv+exp2_integer((i2+2)));
    { ClaireBoolean * g0791I;
      { ClaireBoolean *v_and;
        { v_and = b;
          if (v_and == CFALSE) g0791I =CFALSE; 
          else { { OID  g0792UU;
              { int  g0787 = ((i/7)+1);
                int  g0788 = (4*mod_integer((i-1),7));
                g0792UU = _oid_(nth_integer((*(lbc->statusBitVectorList))[g0787],(g0788+3)));
                } 
              v_and = not_any(g0792UU);
              } 
            if (v_and == CFALSE) g0791I =CFALSE; 
            else g0791I = CTRUE;} 
          } 
        } 
      
      if (g0791I == CTRUE) bv= (bv+exp2_integer((i2+3)));
        else { ClaireBoolean * g0793I;
        { ClaireBoolean *v_and;
          { v_and = not_any(_oid_(b));
            if (v_and == CFALSE) g0793I =CFALSE; 
            else { { int  g0789 = ((i/7)+1);
                int  g0790 = (4*mod_integer((i-1),7));
                v_and = nth_integer((*(lbc->statusBitVectorList))[g0789],(g0790+1));
                } 
              if (v_and == CFALSE) g0793I =CFALSE; 
              else g0793I = CTRUE;} 
            } 
          } 
        
        if (g0793I == CTRUE) bv= (bv-exp2_integer((i2+3)));
          } 
      } 
    STOREI((*lbc->statusBitVectorList)[i1],bv);
    ;} 
  } 


// -------- BoolConstraintWCounterOpp (v1.02) ---------
// two abstract classes storing the counter opposite of each subconstraint (for binary and large compositions)
// Both classes store all opposite constraints as well as an index correspondance for variables
// (between subconstraints and their counterparts)
// binary compositions with subconstraints counterparts
//        this is useful for using linear constraints within boolean combinations
// v0.18: overriding the variable indexing of BinCompositeConst because oppositeConst1 & oppositeConst2 
// no longer need be assigned the same indices as const1 and const2
// v0.33 <thb> typos (c1. missing)
/* The c++ function for: assignIndices(c1:BinBoolConstraintWCounterOpp,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_BinBoolConstraintWCounterOpp(BinBoolConstraintWCounterOpp *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      j= (*choco.assignIndices)(GC_OID(_oid_(c1->const1)),
        _oid_(root),
        j);
      (c1->offset = (j-i));
      { int  k = 1;
        int  g0794 = c1->offset;
        { OID gc_local;
          while ((k <= g0794))
          { GC_LOOP;
            (*choco.setConstraintIndex)(GC_OID(_oid_(c1->oppositeConst1)),
              (*(c1->indicesInOppConst1))[k],
              GC_OID(choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c1->const1)))),((int) k))));
            ++k;
            GC_UNLOOP;} 
          } 
        } 
      j= (*choco.assignIndices)(GC_OID(_oid_(c1->const2)),
        _oid_(root),
        j);
      { int  k = 1;
        int  g0795 = ((j-i)-c1->offset);
        { OID gc_local;
          while ((k <= g0795))
          { GC_LOOP;
            (*choco.setConstraintIndex)(GC_OID(_oid_(c1->oppositeConst2)),
              (*(c1->indicesInOppConst2))[k],
              GC_OID(choco.getConstraintIdx->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c1->const2)))),((int) k))));
            ++k;
            GC_UNLOOP;} 
          } 
        } 
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 filling the slots for storing the counter opposite constraint
/* The c++ function for: closeBoolConstraintWCounterOpp(c:BinBoolConstraintWCounterOpp) [] */
void  choco_closeBoolConstraintWCounterOpp_BinBoolConstraintWCounterOpp(BinBoolConstraintWCounterOpp *c)
{ GC_BIND;
  choco_closeBoolConstraint_BinCompositeConstraint(c);
  { AbstractConstraint * c1 = GC_OBJECT(AbstractConstraint,c->const1);
    AbstractConstraint * c2 = GC_OBJECT(AbstractConstraint,c->const2);
    OID  oppc1 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))));
    OID  oppc2 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))));
    (c->oppositeConst1 = OBJECT(AbstractConstraint,oppc1));
    (c->oppositeConst2 = OBJECT(AbstractConstraint,oppc2));
    { BinBoolConstraintWCounterOpp * g0798; 
      list * g0799;
      g0798 = c;
      { list * i_bag = list::empty(Kernel._integer);
        { int  i = 1;
          int  g0796 = choco.getNbVars->fcall(((int) c1));
          { OID gc_local;
            while ((i <= g0796))
            { GC_LOOP;
              i_bag->addFast(GC_OID((*choco.computeVarIdxInOpposite)(_oid_(c1),
                i)));
              ++i;
              GC_UNLOOP;} 
            } 
          } 
        g0799 = GC_OBJECT(list,i_bag);
        } 
      (g0798->indicesInOppConst1 = g0799);} 
    { BinBoolConstraintWCounterOpp * g0800; 
      list * g0801;
      g0800 = c;
      { list * i_bag = list::empty(Kernel._integer);
        { int  i = 1;
          int  g0797 = choco.getNbVars->fcall(((int) c2));
          { OID gc_local;
            while ((i <= g0797))
            { GC_LOOP;
              i_bag->addFast(GC_OID((*choco.computeVarIdxInOpposite)(_oid_(c2),
                i)));
              ++i;
              GC_UNLOOP;} 
            } 
          } 
        g0801 = GC_OBJECT(list,i_bag);
        } 
      (g0800->indicesInOppConst2 = g0801);} 
    } 
  GC_UNBIND;} 


// large compositions with subconstraints counterparts
// v0.30 same principle as indicesInOppCons1 et 2 in Equiv (since v0.29)
// overriding the indexing of variables provided by the abstract class LargeCompositeConst,
// Indeed, the Cardianlity class adds the oppositeConstraint array (for propagating reverse conditions)
// variables need be assigned their index within the subconstraints as well as within the opposite of the subconstraints
//
/* The c++ function for: assignIndices(c1:LargeBoolConstraintWCounterOpp,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_LargeBoolConstraintWCounterOpp(LargeBoolConstraintWCounterOpp *c1,AbstractConstraint *root,int i)
{ GC_BIND;
  { int Result = 0;
    { int  j = i;
      { int  constIdx = 1;
        int  g0802 = c1->nbConst;
        { OID gc_local;
          while ((constIdx <= g0802))
          { GC_LOOP;
            { int  j0 = j;
              AbstractConstraint * subc = OBJECT(AbstractConstraint,(*(c1->lconst))[constIdx]);
              j= (*choco.assignIndices)(_oid_(subc),
                _oid_(root),
                j);
              { int  ii = 1;
                int  g0803 = (j-j0);
                { OID gc_local;
                  while ((ii <= g0803))
                  { GC_LOOP;
                    (*choco.setConstraintIndex)((*(c1->loppositeConst))[constIdx],
                      (*(OBJECT(bag,(*(c1->indicesInOppConsts))[constIdx])))[ii],
                      GC_OID(choco.getConstraintIdx->fcall(((int) subc),((int) ii))));
                    ++ii;
                    GC_UNLOOP;} 
                  } 
                } 
              } 
            ++constIdx;
            GC_UNLOOP;} 
          } 
        } 
      { int  k = 1;
        int  g0804 = c1->additionalVars->length;
        { OID gc_local;
          while ((k <= g0804))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint1(root,OBJECT(IntVar,(*(c1->additionalVars))[k]),j);
            ((*(c1->additionalIndices))[k]=OBJECT(AbstractVar,(*(c1->additionalVars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      Result = j;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 filling the slots for storing the counter opposite constraint
/* The c++ function for: closeBoolConstraintWCounterOpp(c:LargeBoolConstraintWCounterOpp) [] */
void  choco_closeBoolConstraintWCounterOpp_LargeBoolConstraintWCounterOpp(LargeBoolConstraintWCounterOpp *c)
{ GC_BIND;
  choco_closeBoolConstraint_LargeCompositeConstraint(c);
  { LargeBoolConstraintWCounterOpp * g0806; 
    list * g0807;
    g0806 = c;
    { bag *v_list; OID v_val;
      OID subc,CLcount;
      v_list = GC_OBJECT(list,c->lconst);
       g0807 = v_list->clone(choco._AbstractConstraint);
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { subc = (*(v_list))[CLcount];
        v_val = _oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,subc))));
        
        (*((list *) g0807))[CLcount] = v_val;} 
      } 
    (g0806->loppositeConst = g0807);} 
  { LargeBoolConstraintWCounterOpp * g0808; 
    list * g0809;
    g0808 = c;
    { bag *v_list; OID v_val;
      OID subc,CLcount;
      v_list = GC_OBJECT(list,c->lconst);
       g0809 = v_list->clone();
      for (CLcount= 1; CLcount <= v_list->length; CLcount++)
      { subc = (*(v_list))[CLcount];
        { list * V_CL0810;{ list * i_bag = list::empty(Kernel._integer);
            { int  i = 1;
              int  g0805 = choco.getNbVars->fcall(((int) OBJECT(ClaireObject,subc)));
              { OID gc_local;
                while ((i <= g0805))
                { GC_LOOP;
                  i_bag->addFast(GC_OID((*choco.computeVarIdxInOpposite)(subc,
                    i)));
                  ++i;
                  GC_UNLOOP;} 
                } 
              } 
            V_CL0810 = GC_OBJECT(list,i_bag);
            } 
          
          v_val=_oid_(V_CL0810);} 
        
        (*((list *) g0809))[CLcount] = v_val;} 
      } 
    (g0808->indicesInOppConsts = g0809);} 
  GC_UNBIND;} 


// v0.30, v0.35 (cast)
// ********************************************************************
// *   Part 2: Simple binary compositions (or, and, implies, equiv)   *
// ********************************************************************
// -------- Disjunctions (c1 or c2): look ahead propagation ---------
/* The c++ function for: self_print(d:Disjunction) [] */
void  claire_self_print_Disjunction_choco(Disjunction *d)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(d->const1)));
  princ_string(") OR (");
  print_any(GC_OID(_oid_(d->const2)));
  princ_string(")");
  GC_UNBIND;} 


// this function is always called on a constraint that we want to propagate (either a root constraint
// or a subconstraint that now needs to be propagated, eg such that targetStatus=true)
/* The c++ function for: checkStatus(d:Disjunction,i:integer) [] */
void  choco_checkStatus_Disjunction(Disjunction *d,int i)
{ GC_BIND;
  ;{ AbstractConstraint * c = GC_OBJECT(AbstractConstraint,((i == 1) ?
      d->const1 :
      d->const2 ));
    OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) c)));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      if (BCONTAIN(d->statusBitVector,((4*(i-1))+2)))
       { ClaireBoolean * tgtb = nth_integer(d->statusBitVector,((4*(i-1))+3));
        if (b != tgtb)
         choco_raiseContradiction_AbstractConstraint(d);
        } 
      { int  g0811 = d->statusBitVector;
        int  g0812 = (4*(i-1));
        g0811= (g0811+exp2_integer(g0812));
        if ((b == CTRUE) && 
            (!BCONTAIN(d->statusBitVector,((4*(i-1))+1))))
         g0811= (g0811+exp2_integer((g0812+1)));
        else if ((b != CTRUE) && 
            (BCONTAIN(d->statusBitVector,((4*(i-1))+1))))
         g0811= (g0811-exp2_integer((g0812+1)));
        STOREI(d->statusBitVector,g0811);
        ;} 
      if ((b != CTRUE) && 
          (!BCONTAIN(d->statusBitVector,((4*((3-i)-1))+2))))
       { { int  g0813 = d->statusBitVector;
          int  g0814 = (4*((3-i)-1));
          g0813= (g0813+exp2_integer((g0814+2)));
          if ((CTRUE == CTRUE) && 
              (!BCONTAIN(d->statusBitVector,((4*((3-i)-1))+3))))
           g0813= (g0813+exp2_integer((g0814+3)));
          else if ((boolean_I_any(Kernel.ctrue) != CTRUE) && 
              (BCONTAIN(d->statusBitVector,((4*((3-i)-1))+1))))
           g0813= (g0813-exp2_integer((g0814+3)));
          STOREI(d->statusBitVector,g0813);
          ;} 
        { AbstractConstraint * c2 = GC_OBJECT(AbstractConstraint,((i == 1) ?
            d->const2 :
            d->const1 ));
          (*choco.awake)(_oid_(c2));
          } 
        } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(d:Disjunction,i:integer) [] */
void  choco_awakeOnInf_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset))));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnSup(d:Disjunction,i:integer) [] */
void  choco_awakeOnSup_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset))));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInst(d:Disjunction,i:integer) [] */
void  choco_awakeOnInst_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset))));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnRem(d:Disjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_Disjunction(Disjunction *d,int i,int v)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i),((int) v)));
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset)),((int) v)));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnEnv(d:Disjunction,i:integer) [] */
void  choco_awakeOnEnv_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { (*choco.awakeOnEnv)(GC_OID(_oid_(d->const1)),
          i);
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { (*choco.awakeOnEnv)(GC_OID(_oid_(d->const2)),
          (i-d->offset));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnKer(d:Disjunction,i:integer) [] */
void  choco_awakeOnKer_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { (*choco.awakeOnKer)(GC_OID(_oid_(d->const1)),
          i);
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { (*choco.awakeOnKer)(GC_OID(_oid_(d->const2)),
          (i-d->offset));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInstSet(d:Disjunction,i:integer) [] */
void  choco_awakeOnInstSet_Disjunction(Disjunction *d,int i)
{ if (i <= d->offset) 
  { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
       { (*choco.awakeOnInstSet)(GC_OID(_oid_(d->const1)),
          i);
        } 
      else choco_checkStatus_Disjunction(d,1);
        } 
     } 
  else{ if (!BCONTAIN(d->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { (*choco.awakeOnInstSet)(GC_OID(_oid_(d->const2)),
          (i-d->offset));
        } 
      else choco_checkStatus_Disjunction(d,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: askIfEntailed(d:Disjunction) [] */
OID  choco_askIfEntailed_Disjunction(Disjunction *d)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(d->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(d->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0815 = d->statusBitVector;
              int  g0816 = (4*(1-1));
              g0815= (g0815+exp2_integer(g0816));
              if ((b == CTRUE) && 
                  (!BCONTAIN(d->statusBitVector,((4*(1-1))+1))))
               g0815= (g0815+exp2_integer((g0816+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(d->statusBitVector,((4*(1-1))+1))))
               g0815= (g0815-exp2_integer((g0816+1)));
              STOREI(d->statusBitVector,g0815);
              ;} 
            leftOK = _oid_(b);
            } 
          else leftOK = CNULL;
            } 
        OID  rightOK;
      if (BCONTAIN(d->statusBitVector,(4*(2-1))))
       rightOK = _oid_(nth_integer(d->statusBitVector,((4*(2-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0817 = d->statusBitVector;
              int  g0818 = (4*(2-1));
              g0817= (g0817+exp2_integer(g0818));
              if ((b == CTRUE) && 
                  (!BCONTAIN(d->statusBitVector,((4*(2-1))+1))))
               g0817= (g0817+exp2_integer((g0818+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(d->statusBitVector,((4*(2-1))+1))))
               g0817= (g0817-exp2_integer((g0818+1)));
              STOREI(d->statusBitVector,g0817);
              ;} 
            rightOK = _oid_(b);
            } 
          else rightOK = CNULL;
            } 
        if (leftOK == Kernel.ctrue)
       Result = Kernel.ctrue;
      else if (rightOK == Kernel.ctrue)
       Result = Kernel.ctrue;
      else if ((leftOK == Kernel.cfalse) && 
          (rightOK == Kernel.cfalse))
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


// Such a check is used in  a local optimization mode: therefore the status1/2
// invariants may well not be up to date, therefore we do not trust them and
// re-compute whether the disjunction is feasible or not.
// Note v1.0: is that so sure ?
/* The c++ function for: testIfSatisfied(d:Disjunction) [] */
ClaireBoolean * choco_testIfSatisfied_Disjunction(Disjunction *d)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))))))) == CTRUE) ? CTRUE : (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))))))) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagate(d:Disjunction) [] */
void  choco_propagate_Disjunction(Disjunction *d)
{ if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
   choco_checkStatus_Disjunction(d,1);
  if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
   choco_checkStatus_Disjunction(d,2);
  } 


// -------- Lazy guards (if (c1,c2))  -----------------------------------
// note: only status1 is used
/* The c++ function for: self_print(g:Guard) [] */
void  claire_self_print_Guard_choco(Guard *g)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(g->const1)));
  princ_string(") => (");
  print_any(GC_OID(_oid_(g->const2)));
  princ_string(")");
  GC_UNBIND;} 


// Note: checkStatus is always called on a sub-constraint that we want to propagate
//    checkStatus asks for entailment, updates the status field, and,
//    when necessary, propagates the subconstraint through doAwake
//
/* The c++ function for: checkStatus(g:Guard,i:integer) [] */
void  choco_checkStatus_Guard(Guard *g,int i)
{ GC_BIND;
  ;;if (!BCONTAIN(g->statusBitVector,(4*(1-1))))
   { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      { int  g0819 = g->statusBitVector;
        int  g0820 = (4*(1-1));
        g0819= (g0819+exp2_integer(g0820));
        if ((b == CTRUE) && 
            (!BCONTAIN(g->statusBitVector,((4*(1-1))+1))))
         g0819= (g0819+exp2_integer((g0820+1)));
        else if ((b != CTRUE) && 
            (BCONTAIN(g->statusBitVector,((4*(1-1))+1))))
         g0819= (g0819-exp2_integer((g0820+1)));
        STOREI(g->statusBitVector,g0819);
        ;} 
      if ((b == CTRUE) && 
          (!BCONTAIN(g->statusBitVector,((4*(2-1))+2))))
       { { int  g0821 = g->statusBitVector;
          int  g0822 = (4*(2-1));
          g0821= (g0821+exp2_integer((g0822+2)));
          if ((CTRUE == CTRUE) && 
              (!BCONTAIN(g->statusBitVector,((4*(2-1))+3))))
           g0821= (g0821+exp2_integer((g0822+3)));
          else if ((boolean_I_any(Kernel.ctrue) != CTRUE) && 
              (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
           g0821= (g0821-exp2_integer((g0822+3)));
          STOREI(g->statusBitVector,g0821);
          ;} 
        (*choco.awake)(GC_OID(_oid_(g->const2)));
        } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(g:Guard,i:integer) [] */
void  choco_awakeOnInf_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnSup(g:Guard,i:integer) [] */
void  choco_awakeOnSup_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInst(g:Guard,i:integer) [] */
void  choco_awakeOnInst_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnRem(g:Guard,i:integer,v:integer) [] */
void  choco_awakeOnRem_Guard(Guard *g,int i,int v)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset)),((int) v)));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnKer(g:Guard,i:integer) [] */
void  choco_awakeOnKer_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { (*choco.awakeOnKer)(GC_OID(_oid_(g->const2)),
          (i-g->offset));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnEnv(g:Guard,i:integer) [] */
void  choco_awakeOnEnv_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { (*choco.awakeOnEnv)(GC_OID(_oid_(g->const2)),
          (i-g->offset));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInstSet(g:Guard,i:integer) [] */
void  choco_awakeOnInstSet_Guard(Guard *g,int i)
{ if (i <= g->offset) 
  { choco_checkStatus_Guard(g,1);
     } 
  else{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
    { { (*choco.awakeOnInstSet)(GC_OID(_oid_(g->const2)),
          (i-g->offset));
        } 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: propagate(g:Guard) [] */
void  choco_propagate_Guard(Guard *g)
{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
  { { _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2))))));
      } 
     } 
  else{ GC_BIND;
    choco_checkStatus_Guard(g,1);
    GC_UNBIND;} 
  } 


// v1.05 separate awake & propagate
/* The c++ function for: awake(g:Guard) [] */
void  choco_awake_Guard_choco(Guard *g)
{ if (BCONTAIN(g->statusBitVector,((4*(2-1))+2))) 
  { { (*choco.awake)(GC_OID(_oid_(g->const2)));
      } 
     } 
  else{ GC_BIND;
    choco_checkStatus_Guard(g,1);
    GC_UNBIND;} 
  } 


/* The c++ function for: askIfEntailed(g:Guard) [] */
OID  choco_askIfEntailed_Guard(Guard *g)
{ if (BCONTAIN(g->statusBitVector,(4*(1-1)))) 
  { { OID Result = 0;
      { ClaireBoolean * b = nth_integer(g->statusBitVector,((4*(1-1))+1));
        if (b == CTRUE)
         { if (BCONTAIN(g->statusBitVector,(4*(2-1))))
           Result = _oid_(nth_integer(g->statusBitVector,((4*(2-1))+1)));
          else { OID  b2test = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2))))));
              if (b2test != CNULL)
               { ClaireBoolean * b2 = OBJECT(ClaireBoolean,b2test);
                { int  g0823 = g->statusBitVector;
                  int  g0824 = (4*(2-1));
                  g0823= (g0823+exp2_integer(g0824));
                  if ((b2 == CTRUE) && 
                      (!BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
                   g0823= (g0823+exp2_integer((g0824+1)));
                  else if ((b2 != CTRUE) && 
                      (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
                   g0823= (g0823-exp2_integer((g0824+1)));
                  STOREI(g->statusBitVector,g0823);
                  ;} 
                Result = _oid_(b2);
                } 
              else Result = CNULL;
                } 
            } 
        else Result = Kernel.ctrue;
          } 
      return (Result);} 
     } 
  else{ GC_BIND;
    ;{ OID Result = 0;
      if (BCONTAIN(g->statusBitVector,(4*(2-1))))
       { if (BCONTAIN(g->statusBitVector,((4*(2-1))+1)))
         Result = Kernel.ctrue;
        else Result = CNULL;
          } 
      else { OID  b2test = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2))))));
          if (b2test != CNULL)
           { ClaireBoolean * b2 = OBJECT(ClaireBoolean,b2test);
            { int  g0825 = g->statusBitVector;
              int  g0826 = (4*(2-1));
              g0825= (g0825+exp2_integer(g0826));
              if ((b2 == CTRUE) && 
                  (!BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
               g0825= (g0825+exp2_integer((g0826+1)));
              else if ((b2 != CTRUE) && 
                  (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
               g0825= (g0825-exp2_integer((g0826+1)));
              STOREI(g->statusBitVector,g0825);
              ;} 
            if (b2 == CTRUE)
             Result = Kernel.ctrue;
            else Result = CNULL;
              } 
          else Result = CNULL;
            } 
        GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: testIfSatisfied(g:Guard) [] */
ClaireBoolean * choco_testIfSatisfied_Guard(Guard *g)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))))) == CTRUE) ? CTRUE : (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))))))) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// -------- Equivalence (c1 if and only if c2)  -----------------------
/* The c++ function for: self_print(c:Equiv) [] */
void  claire_self_print_Equiv_choco(Equiv *c)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(c->const1)));
  princ_string(") <=> (");
  print_any(GC_OID(_oid_(c->const2)));
  princ_string(")");
  GC_UNBIND;} 


// if status(i) can be inferred, sets targetStatus(j) and propagate accordingly
/* The c++ function for: checkStatus(c:Equiv,i:integer) [] */
void  choco_checkStatus_Equiv(Equiv *c,int i)
{ if (!BCONTAIN(c->statusBitVector,(4*(i-1)))) 
  { { AbstractConstraint * ci = GC_OBJECT(AbstractConstraint,((i == 1) ?
        c->const1 :
        c->const2 ));
      int  j = (3-i);
      AbstractConstraint * cj = GC_OBJECT(AbstractConstraint,((j == 1) ?
        c->const1 :
        c->const2 ));
      AbstractConstraint * oppcj = GC_OBJECT(AbstractConstraint,((j == 1) ?
        c->oppositeConst1 :
        c->oppositeConst2 ));
      { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) ci)));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          { int  g0827 = c->statusBitVector;
            int  g0828 = (4*(i-1));
            g0827= (g0827+exp2_integer(g0828));
            if ((b == CTRUE) && 
                (!BCONTAIN(c->statusBitVector,((4*(i-1))+1))))
             g0827= (g0827+exp2_integer((g0828+1)));
            else if ((b != CTRUE) && 
                (BCONTAIN(c->statusBitVector,((4*(i-1))+1))))
             g0827= (g0827-exp2_integer((g0828+1)));
            STOREI(c->statusBitVector,g0827);
            ;} 
          { int  g0829 = c->statusBitVector;
            int  g0830 = (4*(j-1));
            g0829= (g0829+exp2_integer((g0830+2)));
            if ((b == CTRUE) && 
                (!BCONTAIN(c->statusBitVector,((4*(j-1))+3))))
             g0829= (g0829+exp2_integer((g0830+3)));
            else if ((b != CTRUE) && 
                (BCONTAIN(c->statusBitVector,((4*(j-1))+1))))
             g0829= (g0829-exp2_integer((g0830+3)));
            STOREI(c->statusBitVector,g0829);
            ;} 
          if (BCONTAIN(c->statusBitVector,(4*(j-1))))
           { if (b != nth_integer(c->statusBitVector,((4*(j-1))+1)))
             choco_raiseContradiction_AbstractConstraint(c);
            } 
          else if (b == CTRUE)
           (*choco.awake)(_oid_(cj));
          else if (b == CFALSE)
           (*choco.awake)(_oid_(oppcj));
          } 
        else ;} 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInf(c:Equiv,i:integer) [] */
void  choco_awakeOnInf_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
        else _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnSup(c:Equiv,i:integer) [] */
void  choco_awakeOnSup_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
        else _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInst(c:Equiv,i:integer) [] */
void  choco_awakeOnInst_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
        else _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnRem(c:Equiv,i:integer,v:integer) [] */
void  choco_awakeOnRem_Equiv(Equiv *c,int i,int v)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
        else _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i]),((int) v)));
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
        else _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)]),((int) v)));
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnKer(c:Equiv,i:integer) [] */
void  choco_awakeOnKer_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         (*choco.awakeOnKer)(GC_OID(_oid_(c->const1)),
          i);
        else (*choco.awakeOnKer)(GC_OID(_oid_(c->oppositeConst1)),
            (*(c->indicesInOppConst1))[i]);
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         (*choco.awakeOnKer)(GC_OID(_oid_(c->const2)),
          (i-c->offset));
        else (*choco.awakeOnKer)(GC_OID(_oid_(c->oppositeConst2)),
            (*(c->indicesInOppConst2))[(i-c->offset)]);
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnEnv(c:Equiv,i:integer) [] */
void  choco_awakeOnEnv_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         (*choco.awakeOnEnv)(GC_OID(_oid_(c->const1)),
          i);
        else (*choco.awakeOnEnv)(GC_OID(_oid_(c->oppositeConst1)),
            (*(c->indicesInOppConst1))[i]);
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         (*choco.awakeOnEnv)(GC_OID(_oid_(c->const2)),
          (i-c->offset));
        else (*choco.awakeOnEnv)(GC_OID(_oid_(c->oppositeConst2)),
            (*(c->indicesInOppConst2))[(i-c->offset)]);
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInstSet(c:Equiv,i:integer) [] */
void  choco_awakeOnInstSet_Equiv(Equiv *c,int i)
{ if (i <= c->offset) 
  { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
         (*choco.awakeOnInstSet)(GC_OID(_oid_(c->const1)),
          i);
        else (*choco.awakeOnInstSet)(GC_OID(_oid_(c->oppositeConst1)),
            (*(c->indicesInOppConst1))[i]);
          } 
      else choco_checkStatus_Equiv(c,1);
        } 
     } 
  else{ if (!BCONTAIN(c->statusBitVector,(4*(2-1)))) 
    { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         (*choco.awakeOnInstSet)(GC_OID(_oid_(c->const2)),
          (i-c->offset));
        else (*choco.awakeOnInstSet)(GC_OID(_oid_(c->oppositeConst2)),
            (*(c->indicesInOppConst2))[(i-c->offset)]);
          } 
      else choco_checkStatus_Equiv(c,2);
         } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// v1.05 when the target status is known, no need to call awake, propagate is enough
// (initial propagation was already done when the target status was settled)
/* The c++ function for: propagate(c:Equiv) [] */
void  choco_propagate_Equiv(Equiv *c)
{ GC_BIND;
  if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
   { ClaireBoolean * b = nth_integer(c->statusBitVector,((4*(1-1))+3));
    if (b == CTRUE)
     _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
    else _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1))))));
      } 
  else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
   choco_checkStatus_Equiv(c,2);
  if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
   { ClaireBoolean * b = nth_integer(c->statusBitVector,((4*(2-1))+3));
    if (b == CTRUE)
     _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
    else _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2))))));
      } 
  else if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
   choco_checkStatus_Equiv(c,1);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:Equiv) [] */
OID  choco_askIfEntailed_Equiv(Equiv *c)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(c->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0831 = c->statusBitVector;
              int  g0832 = (4*(1-1));
              g0831= (g0831+exp2_integer(g0832));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0831= (g0831+exp2_integer((g0832+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0831= (g0831-exp2_integer((g0832+1)));
              STOREI(c->statusBitVector,g0831);
              ;} 
            leftOK = _oid_(b);
            } 
          else leftOK = CNULL;
            } 
        OID  rightOK;
      if (BCONTAIN(c->statusBitVector,(4*(2-1))))
       rightOK = _oid_(nth_integer(c->statusBitVector,((4*(2-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0833 = c->statusBitVector;
              int  g0834 = (4*(2-1));
              g0833= (g0833+exp2_integer(g0834));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0833= (g0833+exp2_integer((g0834+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0833= (g0833-exp2_integer((g0834+1)));
              STOREI(c->statusBitVector,g0833);
              ;} 
            rightOK = _oid_(b);
            } 
          else rightOK = CNULL;
            } 
        if (leftOK == Kernel.ctrue)
       Result = rightOK;
      else if (rightOK == Kernel.ctrue)
       Result = leftOK;
      else if ((leftOK == Kernel.cfalse) && 
          (rightOK == Kernel.cfalse))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:Equiv) [] */
ClaireBoolean * choco_testIfSatisfied_Equiv(Equiv *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((equal(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))),_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))) == CTRUE) ? CTRUE : CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// -------- Conjunctions (only used in subterms of Boolean formulae) --
// note v1.02: for conjunctions, targetStatus slots are useless -> we only use status fields
/* The c++ function for: self_print(c:Conjunction) [] */
void  claire_self_print_Conjunction_choco(Conjunction *c)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(c->const1)));
  princ_string(") AND (");
  print_any(GC_OID(_oid_(c->const2)));
  princ_string(")");
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(c:Conjunction,i:integer) [] */
void  choco_awakeOnInf_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnSup(c:Conjunction,i:integer) [] */
void  choco_awakeOnSup_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInst(c:Conjunction,i:integer) [] */
void  choco_awakeOnInst_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRem(c:Conjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_Conjunction(Conjunction *c,int i,int v)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnKer(c:Conjunction,i:integer) [] */
void  choco_awakeOnKer_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { (*choco.awakeOnKer)(GC_OID(_oid_(c->const1)),
      i);
     } 
  else{ GC_BIND;
    (*choco.awakeOnKer)(GC_OID(_oid_(c->const2)),
      (i-c->offset));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnEnv(c:Conjunction,i:integer) [] */
void  choco_awakeOnEnv_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { (*choco.awakeOnEnv)(GC_OID(_oid_(c->const1)),
      i);
     } 
  else{ GC_BIND;
    (*choco.awakeOnEnv)(GC_OID(_oid_(c->const2)),
      (i-c->offset));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInstSet(c:Conjunction,i:integer) [] */
void  choco_awakeOnInstSet_Conjunction(Conjunction *c,int i)
{ if (i <= c->offset) 
  { (*choco.awakeOnInstSet)(GC_OID(_oid_(c->const1)),
      i);
     } 
  else{ GC_BIND;
    (*choco.awakeOnInstSet)(GC_OID(_oid_(c->const2)),
      (i-c->offset));
    GC_UNBIND;} 
  } 


// v1.05 <thb> awake -> propagate
/* The c++ function for: propagate(c:Conjunction) [] */
void  choco_propagate_Conjunction(Conjunction *c)
{ GC_BIND;
  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
  GC_UNBIND;} 


// v1.010
/* The c++ function for: awake(c:Conjunction) [] */
void  choco_awake_Conjunction_choco(Conjunction *c)
{ GC_BIND;
  (*choco.awake)(GC_OID(_oid_(c->const1)));
  (*choco.awake)(GC_OID(_oid_(c->const2)));
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:Conjunction) [] */
OID  choco_askIfEntailed_Conjunction(Conjunction *c)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(c->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0835 = c->statusBitVector;
              int  g0836 = (4*(1-1));
              g0835= (g0835+exp2_integer(g0836));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0835= (g0835+exp2_integer((g0836+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0835= (g0835-exp2_integer((g0836+1)));
              STOREI(c->statusBitVector,g0835);
              ;} 
            leftOK = _oid_(b);
            } 
          else leftOK = CNULL;
            } 
        OID  rightOK;
      if (BCONTAIN(c->statusBitVector,(4*(2-1))))
       rightOK = _oid_(nth_integer(c->statusBitVector,((4*(2-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0837 = c->statusBitVector;
              int  g0838 = (4*(2-1));
              g0837= (g0837+exp2_integer(g0838));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0837= (g0837+exp2_integer((g0838+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0837= (g0837-exp2_integer((g0838+1)));
              STOREI(c->statusBitVector,g0837);
              ;} 
            rightOK = _oid_(b);
            } 
          else rightOK = CNULL;
            } 
        if ((leftOK == Kernel.ctrue) && 
          (rightOK == Kernel.ctrue))
       Result = Kernel.ctrue;
      else if ((leftOK == Kernel.cfalse) || 
          (rightOK == Kernel.cfalse))
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:Conjunction) [] */
ClaireBoolean * choco_testIfSatisfied_Conjunction(Conjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 3: Large compositions (or, and)                           *
// ********************************************************************
// -------- Large Conjunctions (c1 or c2 or c3 ..... or cn) -----------
/* The c++ function for: self_print(c:LargeConjunction) [] */
void  claire_self_print_LargeConjunction_choco(LargeConjunction *c)
{ princ_string("(");
  print_any((*(c->lconst))[1]);
  princ_string(")");
  { int  i = 2;
    int  g0839 = c->nbConst;
    { OID gc_local;
      while ((i <= g0839))
      { // HOHO, GC_LOOP not needed !
        princ_string(" AND (");
        print_any((*(c->lconst))[i]);
        princ_string(")");
        ++i;
        } 
      } 
    } 
  } 


/* The c++ function for: awakeOnInf(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnInf_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0840 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0840))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnSup_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0841 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0841))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnInst_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0842 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0842))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnRem(c:LargeConjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_LargeConjunction(LargeConjunction *c,int i,int v)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0843 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0843))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnKer(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnKer_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0844 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0844))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      (*choco.awakeOnKer)((*(c->lconst))[idx],
        reali);
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnEnv_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0845 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0845))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      (*choco.awakeOnEnv)((*(c->lconst))[idx],
        reali);
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(c:LargeConjunction,i:integer) [] */
void  choco_awakeOnInstSet_LargeConjunction(LargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0846 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0846))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      int  reali = ((idx == 1) ?
        i :
        (i-((*(c->loffset))[(idx-1)])) );
      (*choco.awakeOnInstSet)((*(c->lconst))[idx],
        reali);
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: propagate(c:LargeConjunction) [] */
void  choco_propagate_LargeConjunction(LargeConjunction *c)
{ { int  i = 1;
    int  g0847 = c->nbConst;
    { OID gc_local;
      while ((i <= g0847))
      { // HOHO, GC_LOOP not needed !
        { ClaireBoolean * g0852I;
          { OID  g0853UU;
            { ClaireBoolean * V_CL0854;{ ClaireBoolean *v_and;
                { { int  g0848 = ((i/7)+1);
                    int  g0849 = (4*mod_integer((i-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g0848],g0849);
                    } 
                  if (v_and == CFALSE) V_CL0854 =CFALSE; 
                  else { { OID  g0855UU;
                      { int  g0850 = ((i/7)+1);
                        int  g0851 = (4*mod_integer((i-1),7));
                        g0855UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0850],(g0851+1)));
                        } 
                      v_and = ((g0855UU == Kernel.ctrue) ? CTRUE : CFALSE);
                      } 
                    if (v_and == CFALSE) V_CL0854 =CFALSE; 
                    else V_CL0854 = CTRUE;} 
                  } 
                } 
              
              g0853UU=_oid_(V_CL0854);} 
            g0852I = not_any(g0853UU);
            } 
          
          if (g0852I == CTRUE) _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
            } 
        ++i;
        } 
      } 
    } 
  } 


// v1.05 separate propagate and awake
/* The c++ function for: awake(c:LargeConjunction) [] */
void  choco_awake_LargeConjunction_choco(LargeConjunction *c)
{ { int  i = 1;
    int  g0856 = c->nbConst;
    { OID gc_local;
      while ((i <= g0856))
      { // HOHO, GC_LOOP not needed !
        { ClaireBoolean * g0861I;
          { OID  g0862UU;
            { ClaireBoolean * V_CL0863;{ ClaireBoolean *v_and;
                { { int  g0857 = ((i/7)+1);
                    int  g0858 = (4*mod_integer((i-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g0857],g0858);
                    } 
                  if (v_and == CFALSE) V_CL0863 =CFALSE; 
                  else { { OID  g0864UU;
                      { int  g0859 = ((i/7)+1);
                        int  g0860 = (4*mod_integer((i-1),7));
                        g0864UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0859],(g0860+1)));
                        } 
                      v_and = ((g0864UU == Kernel.ctrue) ? CTRUE : CFALSE);
                      } 
                    if (v_and == CFALSE) V_CL0863 =CFALSE; 
                    else V_CL0863 = CTRUE;} 
                  } 
                } 
              
              g0862UU=_oid_(V_CL0863);} 
            g0861I = not_any(g0862UU);
            } 
          
          if (g0861I == CTRUE) (*choco.awake)((*(c->lconst))[i]);
            } 
        ++i;
        } 
      } 
    } 
  } 


/* The c++ function for: askIfEntailed(c:LargeConjunction) [] */
OID  choco_askIfEntailed_LargeConjunction(LargeConjunction *c)
{ GC_RESERVE(16);  // v3.0.55 optim !
  { OID Result = 0;
    { ClaireBoolean * allTrue = CTRUE;
      ClaireBoolean * oneFalse = CFALSE;
      int  i = 1;
      int  n = c->nbConst;
      { OID gc_local;
        while ((((allTrue == CTRUE) || 
              (oneFalse != CTRUE)) && 
            (i <= n)))
        { GC_LOOP;
          { OID  ithStatus;
            { ClaireBoolean * g0877I;
              { int  g0865 = ((i/7)+1);
                int  g0866 = (4*mod_integer((i-1),7));
                g0877I = nth_integer((*(c->statusBitVectorList))[g0865],g0866);
                } 
              
              if (g0877I == CTRUE) { int  g0867 = ((i/7)+1);
                  int  g0868 = (4*mod_integer((i-1),7));
                  ithStatus = _oid_(nth_integer((*(c->statusBitVectorList))[g0867],(g0868+1)));
                  } 
                else { OID  bitest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
                if (bitest != CNULL)
                 { ClaireBoolean * bi = OBJECT(ClaireBoolean,bitest);
                  { int  g0869 = ((i/7)+1);
                    int  g0870 = (4*mod_integer((i-1),7));
                    list * g0871 = GC_OBJECT(list,c->statusBitVectorList);
                    int  g0872 = (*(g0871))[g0869];
                    g0872= (g0872+exp2_integer(g0870));
                    { ClaireBoolean * g0878I;
                      { ClaireBoolean *v_and;
                        { v_and = bi;
                          if (v_and == CFALSE) g0878I =CFALSE; 
                          else { { OID  g0879UU;
                              { int  g0873 = ((i/7)+1);
                                int  g0874 = (4*mod_integer((i-1),7));
                                g0879UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0873],(g0874+1)));
                                } 
                              v_and = not_any(g0879UU);
                              } 
                            if (v_and == CFALSE) g0878I =CFALSE; 
                            else g0878I = CTRUE;} 
                          } 
                        } 
                      
                      if (g0878I == CTRUE) g0872= (g0872+exp2_integer((g0870+1)));
                        else { ClaireBoolean * g0880I;
                        { ClaireBoolean *v_and;
                          { v_and = not_any(_oid_(bi));
                            if (v_and == CFALSE) g0880I =CFALSE; 
                            else { { int  g0875 = ((i/7)+1);
                                int  g0876 = (4*mod_integer((i-1),7));
                                v_and = nth_integer((*(c->statusBitVectorList))[g0875],(g0876+1));
                                } 
                              if (v_and == CFALSE) g0880I =CFALSE; 
                              else g0880I = CTRUE;} 
                            } 
                          } 
                        
                        if (g0880I == CTRUE) g0872= (g0872-exp2_integer((g0870+1)));
                          } 
                      } 
                    STOREI((*c->statusBitVectorList)[g0869],g0872);
                    ;} 
                  if (bi == CTRUE)
                   STOREI(c->nbTrueStatus,(c->nbTrueStatus+1));
                  else STOREI(c->nbFalseStatus,(c->nbFalseStatus+1));
                    ithStatus = _oid_(bi);
                  } 
                else ithStatus = CNULL;
                  } 
              } 
            if (ithStatus != Kernel.ctrue)
             allTrue= CFALSE;
            if (ithStatus == Kernel.cfalse)
             oneFalse= CTRUE;
            ++i;
            } 
          GC_UNLOOP;} 
        } 
      if (allTrue == CTRUE)
       Result = Kernel.ctrue;
      else if (oneFalse == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:LargeConjunction) [] */
ClaireBoolean * choco_testIfSatisfied_LargeConjunction(LargeConjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0881UU;
      { OID gc_local;
        ITERATE(subc);
        g0881UU= _oid_(CFALSE);
        for (START(c->lconst); NEXT(subc);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,subc))))) == CTRUE)
           { g0881UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = not_any(g0881UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// -------- Large Disjunctions (c1 or c2 or c3 ..... or cn) -----------
/* The c++ function for: self_print(c:LargeDisjunction) [] */
void  claire_self_print_LargeDisjunction_choco(LargeDisjunction *c)
{ princ_string("(");
  print_any((*(c->lconst))[1]);
  princ_string(")");
  { int  i = 2;
    int  g0882 = c->nbConst;
    { OID gc_local;
      while ((i <= g0882))
      { // HOHO, GC_LOOP not needed !
        princ_string(" OR (");
        print_any((*(c->lconst))[i]);
        princ_string(")");
        ++i;
        } 
      } 
    } 
  } 


// v1.010: compares the number of false constraints (subconstraints whose status
// is false) with the overall number of constraints.
/* The c++ function for: checkNbFalseStatus(d:LargeDisjunction) [] */
void  choco_checkNbFalseStatus_LargeDisjunction(LargeDisjunction *d)
{ if (d->nbFalseStatus == d->nbConst) 
  { choco_raiseContradiction_AbstractConstraint(d);
     } 
  else{ if ((d->nbFalseStatus == (d->nbConst-1)) && 
        (d->nbTrueStatus == 0)) 
    { { OID  jtest;
        { { OID  j_some = CNULL;
            { int  j = 1;
              int  g0883 = d->nbConst;
              { OID gc_local;
                while ((j <= g0883))
                { // HOHO, GC_LOOP not needed !
                  { ClaireBoolean * g0894I;
                    { OID  g0895UU;
                      { int  g0884 = ((j/7)+1);
                        int  g0885 = (4*mod_integer((j-1),7));
                        g0895UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0884],g0885));
                        } 
                      g0894I = not_any(g0895UU);
                      } 
                    
                    if (g0894I == CTRUE) { j_some= j;
                        break;} 
                      } 
                  ++j;
                  } 
                } 
              } 
            jtest = j_some;
            } 
          GC_OID(jtest);} 
        if (jtest != CNULL)
         { int  j = jtest;
          { int  g0886 = ((j/7)+1);
            int  g0887 = (4*mod_integer((j-1),7));
            list * g0888 = GC_OBJECT(list,d->statusBitVectorList);
            int  g0889 = (*(g0888))[g0886];
            g0889= (g0889+exp2_integer((g0887+2)));
            { ClaireBoolean * g0896I;
              { ClaireBoolean *v_and;
                { v_and = CTRUE;
                  if (v_and == CFALSE) g0896I =CFALSE; 
                  else { { OID  g0897UU;
                      { int  g0890 = ((j/7)+1);
                        int  g0891 = (4*mod_integer((j-1),7));
                        g0897UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0890],(g0891+3)));
                        } 
                      v_and = not_any(g0897UU);
                      } 
                    if (v_and == CFALSE) g0896I =CFALSE; 
                    else g0896I = CTRUE;} 
                  } 
                } 
              
              if (g0896I == CTRUE) g0889= (g0889+exp2_integer((g0887+3)));
                else { ClaireBoolean * g0898I;
                { ClaireBoolean *v_and;
                  { v_and = ((boolean_I_any(Kernel.ctrue) != CTRUE) ? CTRUE : CFALSE);
                    if (v_and == CFALSE) g0898I =CFALSE; 
                    else { { int  g0892 = ((j/7)+1);
                        int  g0893 = (4*mod_integer((j-1),7));
                        v_and = nth_integer((*(d->statusBitVectorList))[g0892],(g0893+1));
                        } 
                      if (v_and == CFALSE) g0898I =CFALSE; 
                      else g0898I = CTRUE;} 
                    } 
                  } 
                
                if (g0898I == CTRUE) g0889= (g0889-exp2_integer((g0887+3)));
                  } 
              } 
            STOREI((*d->statusBitVectorList)[g0886],g0889);
            ;} 
          (*choco.awake)((*(d->lconst))[j]);
          } 
        else ;} 
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// checks the status of the i-th constraint of the disjunction
/* The c++ function for: checkStatus(d:LargeDisjunction,i:integer) [] */
void  choco_checkStatus_LargeDisjunction(LargeDisjunction *d,int i)
{ GC_BIND;
  ;{ OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[i]))));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      { ClaireBoolean * g0911I;
        { int  g0899 = ((i/7)+1);
          int  g0900 = (4*mod_integer((i-1),7));
          g0911I = nth_integer((*(d->statusBitVectorList))[g0899],(g0900+2));
          } 
        
        if (g0911I == CTRUE) { ClaireBoolean * tgtb;
            { int  g0901 = ((i/7)+1);
              int  g0902 = (4*mod_integer((i-1),7));
              tgtb = nth_integer((*(d->statusBitVectorList))[g0901],(g0902+3));
              } 
            if (b != tgtb)
             choco_raiseContradiction_AbstractConstraint(d);
            } 
          } 
      { int  g0903 = ((i/7)+1);
        int  g0904 = (4*mod_integer((i-1),7));
        list * g0905 = GC_OBJECT(list,d->statusBitVectorList);
        int  g0906 = (*(g0905))[g0903];
        g0906= (g0906+exp2_integer(g0904));
        { ClaireBoolean * g0912I;
          { ClaireBoolean *v_and;
            { v_and = b;
              if (v_and == CFALSE) g0912I =CFALSE; 
              else { { OID  g0913UU;
                  { int  g0907 = ((i/7)+1);
                    int  g0908 = (4*mod_integer((i-1),7));
                    g0913UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0907],(g0908+1)));
                    } 
                  v_and = not_any(g0913UU);
                  } 
                if (v_and == CFALSE) g0912I =CFALSE; 
                else g0912I = CTRUE;} 
              } 
            } 
          
          if (g0912I == CTRUE) g0906= (g0906+exp2_integer((g0904+1)));
            else { ClaireBoolean * g0914I;
            { ClaireBoolean *v_and;
              { v_and = not_any(_oid_(b));
                if (v_and == CFALSE) g0914I =CFALSE; 
                else { { int  g0909 = ((i/7)+1);
                    int  g0910 = (4*mod_integer((i-1),7));
                    v_and = nth_integer((*(d->statusBitVectorList))[g0909],(g0910+1));
                    } 
                  if (v_and == CFALSE) g0914I =CFALSE; 
                  else g0914I = CTRUE;} 
                } 
              } 
            
            if (g0914I == CTRUE) g0906= (g0906-exp2_integer((g0904+1)));
              } 
          } 
        STOREI((*d->statusBitVectorList)[g0903],g0906);
        ;} 
      if (b == CTRUE)
       STOREI(d->nbTrueStatus,(d->nbTrueStatus+1));
      else { STOREI(d->nbFalseStatus,(d->nbFalseStatus+1));
          choco_checkNbFalseStatus_LargeDisjunction(d);
          } 
        } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnInf_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0915 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0915))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0922I;
        { OID  g0923UU;
          { int  g0916 = ((idx/7)+1);
            int  g0917 = (4*mod_integer((idx-1),7));
            g0923UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0916],g0917));
            } 
          g0922I = not_any(g0923UU);
          } 
        
        if (g0922I == CTRUE) { ClaireBoolean * g0924I;
            { int  g0918 = ((idx/7)+1);
              int  g0919 = (4*mod_integer((idx-1),7));
              g0924I = nth_integer((*(ld->statusBitVectorList))[g0918],(g0919+2));
              } 
            
            if (g0924I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0925I;
                  { int  g0920 = ((idx/7)+1);
                    int  g0921 = (4*mod_integer((idx-1),7));
                    g0925I = nth_integer((*(ld->statusBitVectorList))[g0920],(g0921+3));
                    } 
                  
                  if (g0925I == CTRUE) _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(ld->lconst))[idx])),((int) reali)));
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnSup_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0926 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0926))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0933I;
        { OID  g0934UU;
          { int  g0927 = ((idx/7)+1);
            int  g0928 = (4*mod_integer((idx-1),7));
            g0934UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0927],g0928));
            } 
          g0933I = not_any(g0934UU);
          } 
        
        if (g0933I == CTRUE) { ClaireBoolean * g0935I;
            { int  g0929 = ((idx/7)+1);
              int  g0930 = (4*mod_integer((idx-1),7));
              g0935I = nth_integer((*(ld->statusBitVectorList))[g0929],(g0930+2));
              } 
            
            if (g0935I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0936I;
                  { int  g0931 = ((idx/7)+1);
                    int  g0932 = (4*mod_integer((idx-1),7));
                    g0936I = nth_integer((*(ld->statusBitVectorList))[g0931],(g0932+3));
                    } 
                  
                  if (g0936I == CTRUE) _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(ld->lconst))[idx])),((int) reali)));
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnInst_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0937 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0937))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0944I;
        { OID  g0945UU;
          { int  g0938 = ((idx/7)+1);
            int  g0939 = (4*mod_integer((idx-1),7));
            g0945UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0938],g0939));
            } 
          g0944I = not_any(g0945UU);
          } 
        
        if (g0944I == CTRUE) { ClaireBoolean * g0946I;
            { int  g0940 = ((idx/7)+1);
              int  g0941 = (4*mod_integer((idx-1),7));
              g0946I = nth_integer((*(ld->statusBitVectorList))[g0940],(g0941+2));
              } 
            
            if (g0946I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0947I;
                  { int  g0942 = ((idx/7)+1);
                    int  g0943 = (4*mod_integer((idx-1),7));
                    g0947I = nth_integer((*(ld->statusBitVectorList))[g0942],(g0943+3));
                    } 
                  
                  if (g0947I == CTRUE) _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(ld->lconst))[idx])),((int) reali)));
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnRem(ld:LargeDisjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_LargeDisjunction(LargeDisjunction *ld,int i,int v)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0948 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0948))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0955I;
        { OID  g0956UU;
          { int  g0949 = ((idx/7)+1);
            int  g0950 = (4*mod_integer((idx-1),7));
            g0956UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0949],g0950));
            } 
          g0955I = not_any(g0956UU);
          } 
        
        if (g0955I == CTRUE) { ClaireBoolean * g0957I;
            { int  g0951 = ((idx/7)+1);
              int  g0952 = (4*mod_integer((idx-1),7));
              g0957I = nth_integer((*(ld->statusBitVectorList))[g0951],(g0952+2));
              } 
            
            if (g0957I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0958I;
                  { int  g0953 = ((idx/7)+1);
                    int  g0954 = (4*mod_integer((idx-1),7));
                    g0958I = nth_integer((*(ld->statusBitVectorList))[g0953],(g0954+3));
                    } 
                  
                  if (g0958I == CTRUE) _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(ld->lconst))[idx])),((int) reali),((int) v)));
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnKer(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnKer_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0959 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0959))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0966I;
        { OID  g0967UU;
          { int  g0960 = ((idx/7)+1);
            int  g0961 = (4*mod_integer((idx-1),7));
            g0967UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0960],g0961));
            } 
          g0966I = not_any(g0967UU);
          } 
        
        if (g0966I == CTRUE) { ClaireBoolean * g0968I;
            { int  g0962 = ((idx/7)+1);
              int  g0963 = (4*mod_integer((idx-1),7));
              g0968I = nth_integer((*(ld->statusBitVectorList))[g0962],(g0963+2));
              } 
            
            if (g0968I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0969I;
                  { int  g0964 = ((idx/7)+1);
                    int  g0965 = (4*mod_integer((idx-1),7));
                    g0969I = nth_integer((*(ld->statusBitVectorList))[g0964],(g0965+3));
                    } 
                  
                  if (g0969I == CTRUE) (*choco.awakeOnKer)((*(ld->lconst))[idx],
                      reali);
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnEnv(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnEnv_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0970 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0970))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0977I;
        { OID  g0978UU;
          { int  g0971 = ((idx/7)+1);
            int  g0972 = (4*mod_integer((idx-1),7));
            g0978UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0971],g0972));
            } 
          g0977I = not_any(g0978UU);
          } 
        
        if (g0977I == CTRUE) { ClaireBoolean * g0979I;
            { int  g0973 = ((idx/7)+1);
              int  g0974 = (4*mod_integer((idx-1),7));
              g0979I = nth_integer((*(ld->statusBitVectorList))[g0973],(g0974+2));
              } 
            
            if (g0979I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0980I;
                  { int  g0975 = ((idx/7)+1);
                    int  g0976 = (4*mod_integer((idx-1),7));
                    g0980I = nth_integer((*(ld->statusBitVectorList))[g0975],(g0976+3));
                    } 
                  
                  if (g0980I == CTRUE) (*choco.awakeOnEnv)((*(ld->lconst))[idx],
                      reali);
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInstSet(ld:LargeDisjunction,i:integer) [] */
void  choco_awakeOnInstSet_LargeDisjunction(LargeDisjunction *ld,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0981 = ld->nbConst;
          { OID gc_local;
            while ((idx <= g0981))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(ld->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g0988I;
        { OID  g0989UU;
          { int  g0982 = ((idx/7)+1);
            int  g0983 = (4*mod_integer((idx-1),7));
            g0989UU = _oid_(nth_integer((*(ld->statusBitVectorList))[g0982],g0983));
            } 
          g0988I = not_any(g0989UU);
          } 
        
        if (g0988I == CTRUE) { ClaireBoolean * g0990I;
            { int  g0984 = ((idx/7)+1);
              int  g0985 = (4*mod_integer((idx-1),7));
              g0990I = nth_integer((*(ld->statusBitVectorList))[g0984],(g0985+2));
              } 
            
            if (g0990I == CTRUE) { int  reali = ((idx == 1) ?
                  i :
                  (i-((*(ld->loffset))[(idx-1)])) );
                { ClaireBoolean * g0991I;
                  { int  g0986 = ((idx/7)+1);
                    int  g0987 = (4*mod_integer((idx-1),7));
                    g0991I = nth_integer((*(ld->statusBitVectorList))[g0986],(g0987+3));
                    } 
                  
                  if (g0991I == CTRUE) (*choco.awakeOnInstSet)((*(ld->lconst))[idx],
                      reali);
                    } 
                } 
              else choco_checkStatus_LargeDisjunction(ld,idx);
            } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:LargeDisjunction) [] */
OID  choco_askIfEntailed_LargeDisjunction(LargeDisjunction *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { OID Result = 0;
    { ClaireBoolean * allFalse = CTRUE;
      ClaireBoolean * oneTrue = CFALSE;
      int  i = 1;
      int  n = c->nbConst;
      { OID gc_local;
        while ((((allFalse == CTRUE) || 
              (oneTrue != CTRUE)) && 
            (i <= n)))
        { GC_LOOP;
          { OID  ithStatus;
            { { ClaireBoolean * g0996I;
                { int  g0992 = ((i/7)+1);
                  int  g0993 = (4*mod_integer((i-1),7));
                  g0996I = nth_integer((*(c->statusBitVectorList))[g0992],g0993);
                  } 
                
                if (g0996I == CTRUE) { int  g0994 = ((i/7)+1);
                    int  g0995 = (4*mod_integer((i-1),7));
                    ithStatus = _oid_(nth_integer((*(c->statusBitVectorList))[g0994],(g0995+1)));
                    } 
                  else ithStatus = choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i])));
                } 
              GC_OID(ithStatus);} 
            if (ithStatus != Kernel.cfalse)
             allFalse= CFALSE;
            if (ithStatus == Kernel.ctrue)
             oneTrue= CTRUE;
            ++i;
            } 
          GC_UNLOOP;} 
        } 
      if (allFalse == CTRUE)
       Result = Kernel.cfalse;
      else if (oneTrue == CTRUE)
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:LargeDisjunction) [] */
ClaireBoolean * choco_testIfSatisfied_LargeDisjunction(LargeDisjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0997UU;
      { OID gc_local;
        ITERATE(subc);
        g0997UU= _oid_(CFALSE);
        for (START(c->lconst); NEXT(subc);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,subc)))))) == CTRUE)
           { g0997UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = boolean_I_any(g0997UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// v1.05 awake -> propagate , check that status is not already known
/* The c++ function for: propagate(d:LargeDisjunction) [] */
void  choco_propagate_LargeDisjunction(LargeDisjunction *d)
{ { int  i = 1;
    int  g0998 = d->nbConst;
    { OID gc_local;
      while ((i <= g0998))
      { // HOHO, GC_LOOP not needed !
        { ClaireBoolean * g1001I;
          { OID  g1002UU;
            { int  g0999 = ((i/7)+1);
              int  g1000 = (4*mod_integer((i-1),7));
              g1002UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0999],g1000));
              } 
            g1001I = not_any(g1002UU);
            } 
          
          if (g1001I == CTRUE) choco_checkStatus_LargeDisjunction(d,i);
            } 
        ++i;
        } 
      } 
    } 
  } 


// v1.010: the initial account of all statuses catches the case of disjunctions
// with no or one single disjunct
/* The c++ function for: awake(d:LargeDisjunction) [] */
void  choco_awake_LargeDisjunction_choco(LargeDisjunction *d)
{ choco_checkNbFalseStatus_LargeDisjunction(d);
  choco_propagate_LargeDisjunction(d);
  } 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 4: Cardinality constraint                                 *
// ********************************************************************
// the cardinality constraint: denoting by a domain variable the number of subconstraints
// that are true among a list
// v1.02: accessing the only additional variable: 
// denotes the number of constraints that will hold and the corresponding constraint inde
/* The c++ function for: getCardVar(c:Cardinality) [] */
IntVar * choco_getCardVar_Cardinality(Cardinality *c)
{ return (OBJECT(IntVar,(*(c->additionalVars))[1]));} 


/* The c++ function for: getCardVarIdx(c:Cardinality) [] */
int  choco_getCardVarIdx_Cardinality(Cardinality *c)
{ return ((*(c->additionalIndices))[1]);} 


/* The c++ function for: self_print(c:Cardinality) [] */
void  claire_self_print_Cardinality_choco(Cardinality *c)
{ GC_BIND;
  princ_string("#");
  print_any(GC_OID(_oid_(c->lconst)));
  princ_string(" ");
  princ_string((((c->constrainOnInfNumber == CTRUE) && 
      (c->constrainOnSupNumber == CTRUE)) ?
    "=" :
    ((c->constrainOnInfNumber == CTRUE) ?
      ">=" :
      "<=" ) ));
  princ_string(" ");
  print_any((*(c->additionalVars))[1]);
  princ_string("");
  GC_UNBIND;} 


// v1.010: recoded these functions so that they work also on atleast/atmax constraint
// back-propagates the upper bound of the counter variable on the status fo the subconstraints.
/* The c++ function for: awakeOnNbTrue(c:Cardinality) [] */
void  choco_awakeOnNbTrue_Cardinality(Cardinality *c)
{ if (c->constrainOnSupNumber == CTRUE)
   { IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
    (*choco.updateInf)(_oid_(nbVar),
      c->nbTrueStatus,
      0);
    if (c->nbTrueStatus > nbVar->sup->latestValue)
     choco_raiseContradiction_AbstractConstraint(c);
    else if (c->nbTrueStatus == nbVar->sup->latestValue)
     { int  j = 1;
      int  g1003 = c->nbConst;
      { OID gc_local;
        while ((j <= g1003))
        { // HOHO, GC_LOOP not needed !
          { ClaireBoolean * g1006I;
            { OID  g1007UU;
              { int  g1004 = ((j/7)+1);
                int  g1005 = (4*mod_integer((j-1),7));
                g1007UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1004],g1005));
                } 
              g1006I = not_any(g1007UU);
              } 
            
            if (g1006I == CTRUE) (*choco.awake)((*(c->loppositeConst))[j]);
              } 
          ++j;
          } 
        } 
      } 
    } 
  } 


// back-propagates the lower bound of the counter variable on the status fo the subconstraints.
/* The c++ function for: awakeOnNbFalse(c:Cardinality) [] */
void  choco_awakeOnNbFalse_Cardinality(Cardinality *c)
{ if (c->constrainOnInfNumber == CTRUE)
   { IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
    (*choco.updateSup)(_oid_(nbVar),
      (c->nbConst-c->nbFalseStatus),
      0);
    if ((c->nbConst-c->nbFalseStatus) < nbVar->inf->latestValue)
     ;else if ((c->nbConst-c->nbFalseStatus) == nbVar->inf->latestValue)
     { int  j = 1;
      int  g1008 = c->nbConst;
      { OID gc_local;
        while ((j <= g1008))
        { // HOHO, GC_LOOP not needed !
          { ClaireBoolean * g1011I;
            { OID  g1012UU;
              { int  g1009 = ((j/7)+1);
                int  g1010 = (4*mod_integer((j-1),7));
                g1012UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1009],g1010));
                } 
              g1011I = not_any(g1012UU);
              } 
            
            if (g1011I == CTRUE) (*choco.awake)((*(c->lconst))[j]);
              } 
          ++j;
          } 
        } 
      } 
    } 
  } 


/* The c++ function for: checkStatus(c:Cardinality,i:integer) [] */
void  choco_checkStatus_Cardinality(Cardinality *c,int i)
{ GC_BIND;
  ;;{ OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      { int  g1013 = ((i/7)+1);
        int  g1014 = (4*mod_integer((i-1),7));
        list * g1015 = GC_OBJECT(list,c->statusBitVectorList);
        int  g1016 = (*(g1015))[g1013];
        g1016= (g1016+exp2_integer(g1014));
        { ClaireBoolean * g1021I;
          { ClaireBoolean *v_and;
            { v_and = b;
              if (v_and == CFALSE) g1021I =CFALSE; 
              else { { OID  g1022UU;
                  { int  g1017 = ((i/7)+1);
                    int  g1018 = (4*mod_integer((i-1),7));
                    g1022UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1017],(g1018+1)));
                    } 
                  v_and = not_any(g1022UU);
                  } 
                if (v_and == CFALSE) g1021I =CFALSE; 
                else g1021I = CTRUE;} 
              } 
            } 
          
          if (g1021I == CTRUE) g1016= (g1016+exp2_integer((g1014+1)));
            else { ClaireBoolean * g1023I;
            { ClaireBoolean *v_and;
              { v_and = not_any(_oid_(b));
                if (v_and == CFALSE) g1023I =CFALSE; 
                else { { int  g1019 = ((i/7)+1);
                    int  g1020 = (4*mod_integer((i-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g1019],(g1020+1));
                    } 
                  if (v_and == CFALSE) g1023I =CFALSE; 
                  else g1023I = CTRUE;} 
                } 
              } 
            
            if (g1023I == CTRUE) g1016= (g1016-exp2_integer((g1014+1)));
              } 
          } 
        STOREI((*c->statusBitVectorList)[g1013],g1016);
        ;} 
      if (b == CTRUE)
       { STOREI(c->nbTrueStatus,(c->nbTrueStatus+1));
        choco_awakeOnNbTrue_Cardinality(c);
        } 
      else { STOREI(c->nbFalseStatus,(c->nbFalseStatus+1));
          choco_awakeOnNbFalse_Cardinality(c);
          } 
        } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(c:Cardinality,i:integer) [] */
void  choco_awakeOnInf_Cardinality(Cardinality *c,int i)
{ if (i == choco_getNbVars_LargeCompositeConstraint(c)) 
  { choco_awakeOnNbFalse_Cardinality(c);
     } 
  else{ GC_BIND;
    ;{ OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g1024 = c->nbConst;
            { OID gc_local;
              while ((idx <= g1024))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(c->loffset))[idx])
                 { idx_some= idx;
                  break;} 
                ++idx;
                } 
              } 
            } 
          idxtest = idx_some;
          } 
        GC_OID(idxtest);} 
      if (idxtest != CNULL)
       { int  idx = idxtest;
        { ClaireBoolean * g1029I;
          { OID  g1030UU;
            { int  g1025 = ((idx/7)+1);
              int  g1026 = (4*mod_integer((idx-1),7));
              g1030UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1025],g1026));
              } 
            g1029I = not_any(g1030UU);
            } 
          
          if (g1029I == CTRUE) choco_checkStatus_Cardinality(c,idx);
            else { int  reali = ((idx == 1) ?
              i :
              (i-((*(c->loffset))[(idx-1)])) );
            { ClaireBoolean * g1031I;
              { int  g1027 = ((idx/7)+1);
                int  g1028 = (4*mod_integer((idx-1),7));
                g1031I = nth_integer((*(c->statusBitVectorList))[g1027],(g1028+1));
                } 
              
              if (g1031I == CTRUE) _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                else _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(c->loppositeConst))[idx])),((int) (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali])));
              } 
            } 
          } 
        } 
      else ;} 
    GC_UNBIND;} 
  } 


//v0.30
/* The c++ function for: awakeOnSup(c:Cardinality,i:integer) [] */
void  choco_awakeOnSup_Cardinality(Cardinality *c,int i)
{ if (i == choco_getNbVars_LargeCompositeConstraint(c)) 
  { choco_awakeOnNbTrue_Cardinality(c);
     } 
  else{ GC_BIND;
    ;{ OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g1032 = c->nbConst;
            { OID gc_local;
              while ((idx <= g1032))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(c->loffset))[idx])
                 { idx_some= idx;
                  break;} 
                ++idx;
                } 
              } 
            } 
          idxtest = idx_some;
          } 
        GC_OID(idxtest);} 
      if (idxtest != CNULL)
       { int  idx = idxtest;
        { ClaireBoolean * g1037I;
          { OID  g1038UU;
            { int  g1033 = ((idx/7)+1);
              int  g1034 = (4*mod_integer((idx-1),7));
              g1038UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1033],g1034));
              } 
            g1037I = not_any(g1038UU);
            } 
          
          if (g1037I == CTRUE) choco_checkStatus_Cardinality(c,idx);
            else { int  reali = ((idx == 1) ?
              i :
              (i-((*(c->loffset))[(idx-1)])) );
            { ClaireBoolean * g1039I;
              { int  g1035 = ((idx/7)+1);
                int  g1036 = (4*mod_integer((idx-1),7));
                g1039I = nth_integer((*(c->statusBitVectorList))[g1035],(g1036+1));
                } 
              
              if (g1039I == CTRUE) _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                else _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(c->loppositeConst))[idx])),((int) (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali])));
              } 
            } 
          } 
        } 
      else ;} 
    GC_UNBIND;} 
  } 


//v0.30
/* The c++ function for: awakeOnInst(c:Cardinality,i:integer) [] */
void  choco_awakeOnInst_Cardinality(Cardinality *c,int i)
{ if (i == choco_getNbVars_LargeCompositeConstraint(c)) 
  { { choco_awakeOnNbFalse_Cardinality(c);
      choco_awakeOnNbTrue_Cardinality(c);
      } 
     } 
  else{ GC_BIND;
    ;{ OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g1040 = c->nbConst;
            { OID gc_local;
              while ((idx <= g1040))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(c->loffset))[idx])
                 { idx_some= idx;
                  break;} 
                ++idx;
                } 
              } 
            } 
          idxtest = idx_some;
          } 
        GC_OID(idxtest);} 
      if (idxtest != CNULL)
       { int  idx = idxtest;
        { ClaireBoolean * g1045I;
          { OID  g1046UU;
            { int  g1041 = ((idx/7)+1);
              int  g1042 = (4*mod_integer((idx-1),7));
              g1046UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1041],g1042));
              } 
            g1045I = not_any(g1046UU);
            } 
          
          if (g1045I == CTRUE) choco_checkStatus_Cardinality(c,idx);
            else { int  reali = ((idx == 1) ?
              i :
              (i-((*(c->loffset))[(idx-1)])) );
            { ClaireBoolean * g1047I;
              { int  g1043 = ((idx/7)+1);
                int  g1044 = (4*mod_integer((idx-1),7));
                g1047I = nth_integer((*(c->statusBitVectorList))[g1043],(g1044+1));
                } 
              
              if (g1047I == CTRUE) _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                else _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(c->loppositeConst))[idx])),((int) (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali])));
              } 
            } 
          } 
        } 
      else ;} 
    GC_UNBIND;} 
  } 


//v0.30
/* The c++ function for: awakeOnRem(c:Cardinality,i:integer,v:integer) [] */
void  choco_awakeOnRem_Cardinality(Cardinality *c,int i,int v)
{ GC_BIND;
  if (i == choco_getNbVars_LargeCompositeConstraint(c))
   ;else { { OID  idxtest;
        { { OID  idx_some = CNULL;
            { int  idx = 1;
              int  g1048 = c->nbConst;
              { OID gc_local;
                while ((idx <= g1048))
                { // HOHO, GC_LOOP not needed !
                  if (i <= (*(c->loffset))[idx])
                   { idx_some= idx;
                    break;} 
                  ++idx;
                  } 
                } 
              } 
            idxtest = idx_some;
            } 
          GC_OID(idxtest);} 
        if (idxtest != CNULL)
         { int  idx = idxtest;
          { ClaireBoolean * g1053I;
            { OID  g1054UU;
              { int  g1049 = ((idx/7)+1);
                int  g1050 = (4*mod_integer((idx-1),7));
                g1054UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1049],g1050));
                } 
              g1053I = not_any(g1054UU);
              } 
            
            if (g1053I == CTRUE) choco_checkStatus_Cardinality(c,idx);
              else { int  reali = ((idx == 1) ?
                i :
                (i-((*(c->loffset))[(idx-1)])) );
              { ClaireBoolean * g1055I;
                { int  g1051 = ((idx/7)+1);
                  int  g1052 = (4*mod_integer((idx-1),7));
                  g1055I = nth_integer((*(c->statusBitVectorList))[g1051],(g1052+1));
                  } 
                
                if (g1055I == CTRUE) _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
                  else _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(c->loppositeConst))[idx])),((int) (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali]),((int) v)));
                } 
              } 
            } 
          } 
        else ;} 
      } 
    GC_UNBIND;} 


//v0.30
/* The c++ function for: awakeOnKer(c:Cardinality,i:integer) [] */
void  choco_awakeOnKer_Cardinality(Cardinality *c,int i)
{ GC_BIND;
  ;{ OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g1056 = c->nbConst;
          { OID gc_local;
            while ((idx <= g1056))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g1061I;
        { OID  g1062UU;
          { int  g1057 = ((idx/7)+1);
            int  g1058 = (4*mod_integer((idx-1),7));
            g1062UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1057],g1058));
            } 
          g1061I = not_any(g1062UU);
          } 
        
        if (g1061I == CTRUE) choco_checkStatus_Cardinality(c,idx);
          else { int  reali = ((idx == 1) ?
            i :
            (i-((*(c->loffset))[(idx-1)])) );
          { ClaireBoolean * g1063I;
            { int  g1059 = ((idx/7)+1);
              int  g1060 = (4*mod_integer((idx-1),7));
              g1063I = nth_integer((*(c->statusBitVectorList))[g1059],(g1060+1));
              } 
            
            if (g1063I == CTRUE) (*choco.awakeOnKer)((*(c->lconst))[idx],
                reali);
              else (*choco.awakeOnKer)((*(c->loppositeConst))[idx],
              (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali]);
            } 
          } 
        } 
      } 
    else ;} 
  GC_UNBIND;} 


//v0.30
/* The c++ function for: awakeOnEnv(c:Cardinality,i:integer) [] */
void  choco_awakeOnEnv_Cardinality(Cardinality *c,int i)
{ GC_BIND;
  ;{ OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g1064 = c->nbConst;
          { OID gc_local;
            while ((idx <= g1064))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g1069I;
        { OID  g1070UU;
          { int  g1065 = ((idx/7)+1);
            int  g1066 = (4*mod_integer((idx-1),7));
            g1070UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1065],g1066));
            } 
          g1069I = not_any(g1070UU);
          } 
        
        if (g1069I == CTRUE) choco_checkStatus_Cardinality(c,idx);
          else { int  reali = ((idx == 1) ?
            i :
            (i-((*(c->loffset))[(idx-1)])) );
          { ClaireBoolean * g1071I;
            { int  g1067 = ((idx/7)+1);
              int  g1068 = (4*mod_integer((idx-1),7));
              g1071I = nth_integer((*(c->statusBitVectorList))[g1067],(g1068+1));
              } 
            
            if (g1071I == CTRUE) (*choco.awakeOnEnv)((*(c->lconst))[idx],
                reali);
              else (*choco.awakeOnEnv)((*(c->loppositeConst))[idx],
              (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali]);
            } 
          } 
        } 
      } 
    else ;} 
  GC_UNBIND;} 


//v0.30
/* The c++ function for: awakeOnInstSet(c:Cardinality,i:integer) [] */
void  choco_awakeOnInstSet_Cardinality(Cardinality *c,int i)
{ GC_BIND;
  ;{ OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g1072 = c->nbConst;
          { OID gc_local;
            while ((idx <= g1072))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(c->loffset))[idx])
               { idx_some= idx;
                break;} 
              ++idx;
              } 
            } 
          } 
        idxtest = idx_some;
        } 
      GC_OID(idxtest);} 
    if (idxtest != CNULL)
     { int  idx = idxtest;
      { ClaireBoolean * g1077I;
        { OID  g1078UU;
          { int  g1073 = ((idx/7)+1);
            int  g1074 = (4*mod_integer((idx-1),7));
            g1078UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1073],g1074));
            } 
          g1077I = not_any(g1078UU);
          } 
        
        if (g1077I == CTRUE) choco_checkStatus_Cardinality(c,idx);
          else { int  reali = ((idx == 1) ?
            i :
            (i-((*(c->loffset))[(idx-1)])) );
          { ClaireBoolean * g1079I;
            { int  g1075 = ((idx/7)+1);
              int  g1076 = (4*mod_integer((idx-1),7));
              g1079I = nth_integer((*(c->statusBitVectorList))[g1075],(g1076+1));
              } 
            
            if (g1079I == CTRUE) (*choco.awakeOnInstSet)((*(c->lconst))[idx],
                reali);
              else (*choco.awakeOnInstSet)((*(c->loppositeConst))[idx],
              (*(OBJECT(bag,(*(c->indicesInOppConsts))[idx])))[reali]);
            } 
          } 
        } 
      } 
    else ;} 
  GC_UNBIND;} 


//v0.30
/* The c++ function for: awake(c:Cardinality) [] */
void  choco_awake_Cardinality_choco(Cardinality *c)
{ { IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
    int  nbVarIdx = (*(c->additionalIndices))[1];
    if (c->constrainOnInfNumber == CTRUE)
     (*choco.updateSup)(_oid_(nbVar),
      c->nbConst,
      nbVarIdx);
    if (c->constrainOnSupNumber == CTRUE)
     (*choco.updateInf)(_oid_(nbVar),
      0,
      nbVarIdx);
    } 
  choco_propagate_Cardinality(c);
  } 


// propagates the status of the subconstraints onto the counter variable 
/* The c++ function for: propagate(c:Cardinality) [] */
void  choco_propagate_Cardinality(Cardinality *c)
{ { int  i = 1;
    int  g1080 = c->nbConst;
    { OID gc_local;
      while ((i <= g1080))
      { // HOHO, GC_LOOP not needed !
        { ClaireBoolean * g1083I;
          { OID  g1084UU;
            { int  g1081 = ((i/7)+1);
              int  g1082 = (4*mod_integer((i-1),7));
              g1084UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1081],g1082));
              } 
            g1083I = not_any(g1084UU);
            } 
          
          if (g1083I == CTRUE) choco_checkStatus_Cardinality(c,i);
            } 
        ++i;
        } 
      } 
    } 
  choco_awakeOnNbTrue_Cardinality(c);
  choco_awakeOnNbFalse_Cardinality(c);
  } 


// v0.9904
/* The c++ function for: testIfSatisfied(c:Cardinality) [] */
ClaireBoolean * choco_testIfSatisfied_Cardinality(Cardinality *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { int  nbVal = OBJECT(IntVar,(*(c->additionalVars))[1])->value;
      { ClaireBoolean *v_and;
        { if (c->constrainOnInfNumber == CTRUE)
           { int  g1089UU;
            { int  g1085 = 0;
              { OID gc_local;
                ITERATE(g1086);
                for (START(c->lconst); NEXT(g1086);)
                { GC_LOOP;
                  if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,g1086)))))) == CTRUE)
                   ++g1085;
                  GC_UNLOOP;} 
                } 
              g1089UU = g1085;
              } 
            v_and = ((nbVal <= g1089UU) ? CTRUE : CFALSE);
            } 
          else v_and = CTRUE;
            if (v_and == CFALSE) Result =CFALSE; 
          else { if (c->constrainOnSupNumber == CTRUE)
             { int  g1090UU;
              { int  g1087 = 0;
                { OID gc_local;
                  ITERATE(g1088);
                  for (START(c->lconst); NEXT(g1088);)
                  { GC_LOOP;
                    if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,g1088)))))) == CTRUE)
                     ++g1087;
                    GC_UNLOOP;} 
                  } 
                g1090UU = g1087;
                } 
              v_and = ((g1090UU <= nbVal) ? CTRUE : CFALSE);
              } 
            else v_and = CTRUE;
              if (v_and == CFALSE) Result =CFALSE; 
            else Result = CTRUE;} 
          } 
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9904
/* The c++ function for: askIfEntailed(c:Cardinality) [] */
OID  choco_askIfEntailed_Cardinality(Cardinality *c)
{ GC_RESERVE(13);  // v3.0.55 optim !
  { OID Result = 0;
    { int  i = 1;
      int  n = c->nbConst;
      IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
      if ((c->nbTrueStatus > nbVar->sup->latestValue) || 
          ((n-c->nbFalseStatus) < nbVar->inf->latestValue))
       Result = Kernel.cfalse;
      else { { OID gc_local;
            while ((i <= n))
            { GC_LOOP;
              { ClaireBoolean * g1109I;
                { OID  g1110UU;
                  { int  g1091 = ((i/7)+1);
                    int  g1092 = (4*mod_integer((i-1),7));
                    g1110UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1091],g1092));
                    } 
                  g1109I = not_any(g1110UU);
                  } 
                
                if (g1109I == CTRUE) { OID  ithStatus = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
                    if (ithStatus == Kernel.cfalse)
                     { { int  g1093 = ((i/7)+1);
                        int  g1094 = (4*mod_integer((i-1),7));
                        list * g1095 = GC_OBJECT(list,c->statusBitVectorList);
                        int  g1096 = (*(g1095))[g1093];
                        g1096= (g1096+exp2_integer(g1094));
                        { ClaireBoolean * g1111I;
                          { ClaireBoolean *v_and;
                            { v_and = CFALSE;
                              if (v_and == CFALSE) g1111I =CFALSE; 
                              else { { OID  g1112UU;
                                  { int  g1097 = ((i/7)+1);
                                    int  g1098 = (4*mod_integer((i-1),7));
                                    g1112UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1097],(g1098+1)));
                                    } 
                                  v_and = not_any(g1112UU);
                                  } 
                                if (v_and == CFALSE) g1111I =CFALSE; 
                                else g1111I = CTRUE;} 
                              } 
                            } 
                          
                          if (g1111I == CTRUE) g1096= (g1096+exp2_integer((g1094+1)));
                            else { ClaireBoolean * g1113I;
                            { ClaireBoolean *v_and;
                              { v_and = ((boolean_I_any(Kernel.cfalse) != CTRUE) ? CTRUE : CFALSE);
                                if (v_and == CFALSE) g1113I =CFALSE; 
                                else { { int  g1099 = ((i/7)+1);
                                    int  g1100 = (4*mod_integer((i-1),7));
                                    v_and = nth_integer((*(c->statusBitVectorList))[g1099],(g1100+1));
                                    } 
                                  if (v_and == CFALSE) g1113I =CFALSE; 
                                  else g1113I = CTRUE;} 
                                } 
                              } 
                            
                            if (g1113I == CTRUE) g1096= (g1096-exp2_integer((g1094+1)));
                              } 
                          } 
                        STOREI((*c->statusBitVectorList)[g1093],g1096);
                        ;} 
                      STOREI(c->nbFalseStatus,(c->nbFalseStatus+1));
                      } 
                    if (ithStatus == Kernel.ctrue)
                     { { int  g1101 = ((i/7)+1);
                        int  g1102 = (4*mod_integer((i-1),7));
                        list * g1103 = GC_OBJECT(list,c->statusBitVectorList);
                        int  g1104 = (*(g1103))[g1101];
                        g1104= (g1104+exp2_integer(g1102));
                        { ClaireBoolean * g1114I;
                          { ClaireBoolean *v_and;
                            { v_and = CTRUE;
                              if (v_and == CFALSE) g1114I =CFALSE; 
                              else { { OID  g1115UU;
                                  { int  g1105 = ((i/7)+1);
                                    int  g1106 = (4*mod_integer((i-1),7));
                                    g1115UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1105],(g1106+1)));
                                    } 
                                  v_and = not_any(g1115UU);
                                  } 
                                if (v_and == CFALSE) g1114I =CFALSE; 
                                else g1114I = CTRUE;} 
                              } 
                            } 
                          
                          if (g1114I == CTRUE) g1104= (g1104+exp2_integer((g1102+1)));
                            else { ClaireBoolean * g1116I;
                            { ClaireBoolean *v_and;
                              { v_and = ((boolean_I_any(Kernel.ctrue) != CTRUE) ? CTRUE : CFALSE);
                                if (v_and == CFALSE) g1116I =CFALSE; 
                                else { { int  g1107 = ((i/7)+1);
                                    int  g1108 = (4*mod_integer((i-1),7));
                                    v_and = nth_integer((*(c->statusBitVectorList))[g1107],(g1108+1));
                                    } 
                                  if (v_and == CFALSE) g1116I =CFALSE; 
                                  else g1116I = CTRUE;} 
                                } 
                              } 
                            
                            if (g1116I == CTRUE) g1104= (g1104-exp2_integer((g1102+1)));
                              } 
                          } 
                        STOREI((*c->statusBitVectorList)[g1101],g1104);
                        ;} 
                      STOREI(c->nbTrueStatus,(c->nbTrueStatus+1));
                      } 
                    } 
                  } 
              ++i;
              GC_UNLOOP;} 
            } 
          if (((c->constrainOnSupNumber == CTRUE) && 
                (c->nbTrueStatus > nbVar->sup->latestValue)) || 
              ((c->constrainOnInfNumber == CTRUE) && 
                  ((n-c->nbFalseStatus) < nbVar->inf->latestValue)))
           Result = Kernel.cfalse;
          else if (((c->constrainOnInfNumber != CTRUE) || 
                (nbVar->inf->latestValue <= c->nbTrueStatus)) && 
              ((c->constrainOnSupNumber != CTRUE) || 
                  ((n-c->nbFalseStatus) <= nbVar->sup->latestValue)))
           Result = Kernel.ctrue;
          else Result = CNULL;
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 5: Negating a constraint                                  *
// ********************************************************************
// generic utility: transforming a constraint into its opposite
// v0.37: call closeCompositeConstraint
// v1.05 closeCompositeConstraint ->  closeBoolConstraint
/* The c++ function for: opposite(c:Guard) [] */
Conjunction * choco_opposite_Guard(Guard *c)
{ GC_BIND;
  { Conjunction *Result ;
    { OID  oppc2 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))));
      Conjunction * d;
      { { Conjunction * _CL_obj = ((Conjunction *) GC_OBJECT(Conjunction,new_object_class(choco._Conjunction)));
          (_CL_obj->const1 = c->const1);
          (_CL_obj->const2 = OBJECT(AbstractConstraint,oppc2));
          d = _CL_obj;
          } 
        GC_OBJECT(Conjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:Conjunction) [] */
Disjunction * choco_opposite_Conjunction(Conjunction *c)
{ GC_BIND;
  { Disjunction *Result ;
    { OID  oppc1 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))));
      OID  oppc2 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))));
      Disjunction * d;
      { { Disjunction * _CL_obj = ((Disjunction *) GC_OBJECT(Disjunction,new_object_class(choco._Disjunction)));
          (_CL_obj->const1 = OBJECT(AbstractConstraint,oppc1));
          (_CL_obj->const2 = OBJECT(AbstractConstraint,oppc2));
          d = _CL_obj;
          } 
        GC_OBJECT(Disjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:Disjunction) [] */
Conjunction * choco_opposite_Disjunction(Disjunction *c)
{ GC_BIND;
  { Conjunction *Result ;
    { OID  oppc1 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))));
      OID  oppc2 = GC_OID(_oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))));
      Conjunction * d;
      { { Conjunction * _CL_obj = ((Conjunction *) GC_OBJECT(Conjunction,new_object_class(choco._Conjunction)));
          (_CL_obj->const1 = OBJECT(AbstractConstraint,oppc1));
          (_CL_obj->const2 = OBJECT(AbstractConstraint,oppc2));
          d = _CL_obj;
          } 
        GC_OBJECT(Conjunction,d);} 
      choco_closeBoolConstraint_BinCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9903 <ebo>, <fxj>
// v1.05 <thb> remove all the status stuff by proper call to closeBoolConstraint
/* The c++ function for: opposite(c:LargeDisjunction) [] */
LargeConjunction * choco_opposite_LargeDisjunction(LargeDisjunction *c)
{ GC_BIND;
  { LargeConjunction *Result ;
    { LargeConjunction * d;
      { { LargeConjunction * _CL_obj = ((LargeConjunction *) GC_OBJECT(LargeConjunction,new_object_class(choco._LargeConjunction)));
          { LargeCompositeConstraint * g1117; 
            list * g1118;
            g1117 = _CL_obj;
            { bag *v_list; OID v_val;
              OID subc,CLcount;
              v_list = GC_OBJECT(list,c->lconst);
               g1118 = v_list->clone(choco._AbstractConstraint);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { subc = (*(v_list))[CLcount];
                v_val = _oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,subc))));
                
                (*((list *) g1118))[CLcount] = v_val;} 
              } 
            (g1117->lconst = g1118);} 
          d = _CL_obj;
          } 
        GC_OBJECT(LargeConjunction,d);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:LargeConjunction) [] */
LargeDisjunction * choco_opposite_LargeConjunction(LargeConjunction *c)
{ GC_BIND;
  { LargeDisjunction *Result ;
    { LargeDisjunction * d;
      { { LargeDisjunction * _CL_obj = ((LargeDisjunction *) GC_OBJECT(LargeDisjunction,new_object_class(choco._LargeDisjunction)));
          { LargeCompositeConstraint * g1119; 
            list * g1120;
            g1119 = _CL_obj;
            { bag *v_list; OID v_val;
              OID subc,CLcount;
              v_list = GC_OBJECT(list,c->lconst);
               g1120 = v_list->clone(choco._AbstractConstraint);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { subc = (*(v_list))[CLcount];
                v_val = _oid_((ClaireObject *) choco.opposite->fcall(((int) OBJECT(ClaireObject,subc))));
                
                (*((list *) g1120))[CLcount] = v_val;} 
              } 
            (g1119->lconst = g1120);} 
          d = _CL_obj;
          } 
        GC_OBJECT(LargeDisjunction,d);} 
      choco_closeBoolConstraint_LargeCompositeConstraint(d);
      Result = d;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.37: TODO
// [choco/opposite(c:Equiv) : Cardinality
//  -> card(list(c.const1,c.const2), makeConst(1))]
// <hha> 0.17 wrong field names (v instead of v1)
/* The c++ function for: opposite(c:GreaterOrEqualxc) [] */
LessOrEqualxc * choco_opposite_GreaterOrEqualxc(GreaterOrEqualxc *c)
{ GC_BIND;
  { LessOrEqualxc *Result ;
    { LessOrEqualxc * _CL_obj = ((LessOrEqualxc *) GC_OBJECT(LessOrEqualxc,new_object_class(choco._LessOrEqualxc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->cste = (c->cste-1));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:LessOrEqualxc) [] */
GreaterOrEqualxc * choco_opposite_LessOrEqualxc(LessOrEqualxc *c)
{ GC_BIND;
  { GreaterOrEqualxc *Result ;
    { GreaterOrEqualxc * _CL_obj = ((GreaterOrEqualxc *) GC_OBJECT(GreaterOrEqualxc,new_object_class(choco._GreaterOrEqualxc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->cste = (c->cste+1));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:Equalxc) [] */
NotEqualxc * choco_opposite_Equalxc(Equalxc *c)
{ GC_BIND;
  { NotEqualxc *Result ;
    { NotEqualxc * _CL_obj = ((NotEqualxc *) GC_OBJECT(NotEqualxc,new_object_class(choco._NotEqualxc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:NotEqualxc) [] */
Equalxc * choco_opposite_NotEqualxc(NotEqualxc *c)
{ GC_BIND;
  { Equalxc *Result ;
    { Equalxc * _CL_obj = ((Equalxc *) GC_OBJECT(Equalxc,new_object_class(choco._Equalxc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:Equalxyc) [] */
NotEqualxyc * choco_opposite_Equalxyc(Equalxyc *c)
{ GC_BIND;
  { NotEqualxyc *Result ;
    { NotEqualxyc * _CL_obj = ((NotEqualxyc *) GC_OBJECT(NotEqualxyc,new_object_class(choco._NotEqualxyc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->v2 = c->v2);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: opposite(c:NotEqualxyc) [] */
Equalxyc * choco_opposite_NotEqualxyc(NotEqualxyc *c)
{ GC_BIND;
  { Equalxyc *Result ;
    { Equalxyc * _CL_obj = ((Equalxyc *) GC_OBJECT(Equalxyc,new_object_class(choco._Equalxyc)));
      (_CL_obj->v1 = c->v1);
      (_CL_obj->v2 = c->v2);
      (_CL_obj->cste = c->cste);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.0: the constraint LessOrEqual disappears
// variable 1 in (x >= y + c) becomes variable 2 in not(x >= y + c) rewritten (y >= x - c + 1)
/* The c++ function for: opposite(c:GreaterOrEqualxyc) [] */
GreaterOrEqualxyc * choco_opposite_GreaterOrEqualxyc(GreaterOrEqualxyc *c)
{ GC_BIND;
  { GreaterOrEqualxyc *Result ;
    { GreaterOrEqualxyc * _CL_obj = ((GreaterOrEqualxyc *) GC_OBJECT(GreaterOrEqualxyc,new_object_class(choco._GreaterOrEqualxyc)));
      (_CL_obj->v1 = c->v2);
      (_CL_obj->v2 = c->v1);
      (_CL_obj->cste = ((-c->cste)+1));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: computeVarIdxInOpposite(c:GreaterOrEqualxyc,i:integer) [] */
int  choco_computeVarIdxInOpposite_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int i)
{ { int Result = 0;
    Result = ((i == 1) ?
      2 :
      1 );
    return (Result);} 
  } 


// v0.29: correspondance between the indices of variables in a constraint c and its converse not(c)
/* The c++ function for: computeVarIdxInOpposite(c:AbstractConstraint,i:integer) [] */
int  choco_computeVarIdxInOpposite_AbstractConstraint(AbstractConstraint *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("~S not usable in a boolean constraint (don't know how to propagate its negation)"),
    _oid_(list::alloc(1,_oid_(c))))));
  return (0);} 


/* The c++ function for: computeVarIdxInOpposite(c:IntConstraint,i:integer) [] */
int  choco_computeVarIdxInOpposite_IntConstraint(IntConstraint *c,int i)
{ return (i);} 


/* The c++ function for: computeVarIdxInOpposite(c:BinCompositeConstraint,i:integer) [] */
int  choco_computeVarIdxInOpposite_BinCompositeConstraint(BinCompositeConstraint *c,int i)
{ if (i <= c->offset) 
  { { int Result = 0;
      Result = (*choco.computeVarIdxInOpposite)(GC_OID(_oid_(c->const1)),
        i);
      return (Result);} 
     } 
  else{ GC_BIND;
    { int Result = 0;
      Result = (((*choco.computeVarIdxInOpposite)(GC_OID(_oid_(c->const2)),
        (i-c->offset)))+c->offset);
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: computeVarIdxInOpposite(c:LargeCompositeConstraint,i:integer) [] */
int  choco_computeVarIdxInOpposite_LargeCompositeConstraint(LargeCompositeConstraint *c,int i)
{ GC_BIND;
  { int Result = 0;
    { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g1121 = c->nbConst;
            { OID gc_local;
              while ((idx <= g1121))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(c->loffset))[idx])
                 { idx_some= idx;
                  break;} 
                ++idx;
                } 
              } 
            } 
          idxtest = idx_some;
          } 
        GC_OID(idxtest);} 
      if (idxtest != CNULL)
       { int  idx = idxtest;
        Result = ((idx == 1) ?
          (*choco.computeVarIdxInOpposite)((*(c->lconst))[1],
            i) :
          (((*choco.computeVarIdxInOpposite)((*(c->lconst))[idx],
            (i-((*(c->loffset))[(idx-1)]))))+((*(c->loffset))[(idx-1)])) );
        } 
      else close_exception(((general_error *) (*Core._general_error)(_string_("this should never happen, if ~S is a valid var idx for ~S"),
          _oid_(list::alloc(2,i,_oid_(c))))));
        } 
    GC_UNBIND; return (Result);} 
  } 

