/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\PaLM\v1.324\p-bool.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:14:42 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <choco.h>
#include <ice.h>
#include <palm.h>

// Implémentation d'un système d'explication en claire/choco
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM : gestion des booléens 
// ** Summary : boolean connectors
// Part 1 : OR : PalmDisjunction
// Part 2 : IFTHEN : PalmGuard 
// Part 3 : IFF : PalmEquiv
// Part 4 : AND : PalmConjunction
// Part 5 : Large AND : PalmLargeConjunction
// Part 6 : Large OR : PalmLargeDisjunction
// Part 7 : Cardinality : PalmCardinality
// General comments on handling boolean constraints within PaLM
// (a) Decisions made when propagating need to be taken within the 
// updateDataStructure mechanisms (working on the Status and 
// TargetStatus) but decisions need to be enforced only when 
// effectively propagating (in the awakeOnXXX).
// Enforced constraints are considered as INDIRECT constraints
// whose activation explanation is computed on the fly. 
// (b) When undoing a decision, it may happen that one or several 
// indirect constraints were dependent on that decision. It is 
// mandatory to undo their effects (which is automatically done
// by the indirect constraint mechanmis) but also to undo any decision 
// made on the Status or TargetStatus related to this constraints
// That is why a slot in PalmInfoConstraint records all the controlling 
// constraint of a given constraint in order to be able to 
// undo all the decisions made on the associated constraints.
// (c) In order to correctly undo the past effects of the removed 
// constraint the idea consists in undoing all decisions that was 
// based on that behavior i.e. the computation of status and/or 
// target status letting the system rechecks all the situations alone. 
// *************************************************
// * Part 1 : OR                                   *
// *************************************************
/* The c++ function for: initHook(c:PalmDisjunction) [] */
void  palm_initHook_PalmDisjunction(PalmDisjunction *c)
{ GC_BIND;
  { AbstractConstraint * g0345; 
    OID  g0346;
    g0345 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0346 = _oid_(_CL_obj);
      } 
    (g0345->hook = g0346);} 
  { OID  g0347UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 1);
      g0347UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const1->hook)),g0347UU);
    } 
  { OID  g0348UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 2);
      g0348UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const2->hook)),g0348UU);
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(d:PalmDisjunction,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmDisjunction(PalmDisjunction *d,int idx)
{ GC_BIND;
  (d->isContradictory = CFALSE);
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const1));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const1));
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const2));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const2));
  if (1 == 1)
   (d->needToAwakeC1 = CFALSE);
  else (d->needToAwakeC2 = CFALSE);
    if (2 == 1)
   (d->needToAwakeC1 = CFALSE);
  else (d->needToAwakeC2 = CFALSE);
    STOREI(d->statusBitVector,0);
  GC_UNBIND;} 


/* The c++ function for: self_print(d:PalmDisjunction) [] */
OID  claire_self_print_PalmDisjunction_palm(PalmDisjunction *d)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(d->const1)));
  princ_string(") e-OR (");
  print_any(GC_OID(_oid_(d->const2)));
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: needToAwake(d:PalmDisjunction,i:integer) [] */
ClaireBoolean * palm_needToAwake_PalmDisjunction(PalmDisjunction *d,int i)
{ { ClaireBoolean *Result ;
    Result = ((i == 1) ?
      d->needToAwakeC1 :
      d->needToAwakeC2 );
    return (Result);} 
  } 


/* The c++ function for: setNeedToAwake(d:PalmDisjunction,i:integer,val:boolean) [] */
void  palm_setNeedToAwake_PalmDisjunction(PalmDisjunction *d,int i,ClaireBoolean *val)
{ if (i == 1)
   (d->needToAwakeC1 = val);
  else (d->needToAwakeC2 = val);
    } 


/* The c++ function for: checkStatusAndReport(d:PalmDisjunction,i:integer) [] */
void  palm_checkStatusAndReport_PalmDisjunction(PalmDisjunction *d,int i)
{ if (!BCONTAIN(d->statusBitVector,(4*(i-1)))) 
  { { OID  btest;
      { { OID  g0353UU;
          { if (i == 1)
             g0353UU = _oid_(d->const1);
            else g0353UU = _oid_(d->const2);
              GC_OID(g0353UU);} 
          btest = (*choco.askIfEntailed)(g0353UU);
          } 
        GC_OID(btest);} 
      if (btest != CNULL)
       { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
        if (BCONTAIN(d->statusBitVector,((4*(i-1))+2)))
         { ClaireBoolean * tgtb = nth_integer(d->statusBitVector,((4*(i-1))+3));
          if (b != tgtb)
           (d->isContradictory = CTRUE);
          } 
        if (d->isContradictory != CTRUE)
         { { int  g0349 = d->statusBitVector;
            int  g0350 = (4*(i-1));
            g0349= (g0349+exp2_integer(g0350));
            if ((b == CTRUE) && 
                (!BCONTAIN(d->statusBitVector,((4*(i-1))+1))))
             g0349= (g0349+exp2_integer((g0350+1)));
            else if ((b != CTRUE) && 
                (BCONTAIN(d->statusBitVector,((4*(i-1))+1))))
             g0349= (g0349-exp2_integer((g0350+1)));
            STOREI(d->statusBitVector,g0349);
            ;} 
          if ((b != CTRUE) && 
              (!BCONTAIN(d->statusBitVector,((4*((3-i)-1))+2))))
           { { int  g0351 = d->statusBitVector;
              int  g0352 = (4*((3-i)-1));
              g0351= (g0351+exp2_integer((g0352+2)));
              if ((CTRUE == CTRUE) && 
                  (!BCONTAIN(d->statusBitVector,((4*((3-i)-1))+3))))
               g0351= (g0351+exp2_integer((g0352+3)));
              else if ((boolean_I_any(Kernel.ctrue) != CTRUE) && 
                  (BCONTAIN(d->statusBitVector,((4*((3-i)-1))+1))))
               g0351= (g0351-exp2_integer((g0352+3)));
              STOREI(d->statusBitVector,g0351);
              ;} 
            if ((3-i) == 1)
             (d->needToAwakeC1 = CTRUE);
            else (d->needToAwakeC2 = CTRUE);
              } 
          } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnConstraint(d:PalmDisjunction,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmDisjunction_palm(PalmDisjunction *d,int idx,int way,int newValue,int oldValue)
{ if (idx <= d->offset) 
  { { (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(d->const1)),
        idx,
        way,
        newValue,
        oldValue);
      if ((!BCONTAIN(d->statusBitVector,(4*(1-1)))) && 
          (!BCONTAIN(d->statusBitVector,((4*(1-1))+2))))
       palm_checkStatusAndReport_PalmDisjunction(d,1);
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(d->const2)),
      (idx-d->offset),
      way,
      newValue,
      oldValue);
    if ((!BCONTAIN(d->statusBitVector,(4*(2-1)))) && 
        (!BCONTAIN(d->statusBitVector,((4*(2-1))+2))))
     palm_checkStatusAndReport_PalmDisjunction(d,2);
    GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(d:PalmDisjunction,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmDisjunction_palm(PalmDisjunction *d,int idx,int way,int newValue,int oldValue)
{ if (idx <= d->offset) 
  { { (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(d->const1)),
        idx,
        way,
        newValue,
        oldValue);
      if (BCONTAIN(d->statusBitVector,(4*(1-1))))
       { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          if (b != nth_integer(d->statusBitVector,((4*(1-1))+1)))
           palm_takeIntoAccountStatusChange_PalmDisjunction(d,1);
          } 
        else palm_takeIntoAccountStatusChange_PalmDisjunction(d,1);
          } 
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(d->const2)),
      (idx-d->offset),
      way,
      newValue,
      oldValue);
    if (BCONTAIN(d->statusBitVector,(4*(1-1))))
     { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))));
      if (btest != CNULL)
       { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
        if (b != nth_integer(d->statusBitVector,((4*(1-1))+1)))
         palm_takeIntoAccountStatusChange_PalmDisjunction(d,1);
        } 
      else palm_takeIntoAccountStatusChange_PalmDisjunction(d,1);
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: checkConstraintState(d:PalmDisjunction) [] */
ClaireBoolean * palm_checkConstraintState_PalmDisjunction(PalmDisjunction *d)
{ if (d->isContradictory == CTRUE) 
  { { ClaireBoolean *Result ;
      { (d->isContradictory = CFALSE);
        { Explanation * expl;
          { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              expl = _CL_obj;
              } 
            GC_OBJECT(Explanation,expl);} 
          palm_self_explain_AbstractConstraint(d,expl);
          { OID gc_local;
            ITERATE(ct);
            bag *ct_support;
            ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))))));
            for (START(ct_support); NEXT(ct);)
            { GC_LOOP;
              GC_OBJECT(set,expl->explanation)->addFast(ct);
              GC_UNLOOP;} 
            } 
          { OID gc_local;
            ITERATE(ct);
            bag *ct_support;
            ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2))))))));
            for (START(ct_support); NEXT(ct);)
            { GC_LOOP;
              GC_OBJECT(set,expl->explanation)->addFast(ct);
              GC_UNLOOP;} 
            } 
          palm_raisePalmFakeContradiction_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) choco_getProblem_AbstractConstraint(d)->propagationEngine)),expl);
          } 
        Result = CFALSE;
        } 
      return (Result);} 
     } 
  else{ if (((1 == 1) ? (d->needToAwakeC1 == CTRUE) : (d->needToAwakeC2 == CTRUE)) || 
        ((2 == 1) ? (d->needToAwakeC1 == CTRUE) : (d->needToAwakeC2 == CTRUE))) 
    { { ClaireBoolean *Result ;
        { if ((1 == 1) ? (d->needToAwakeC1 == CTRUE) : (d->needToAwakeC2 == CTRUE))
           { if (1 == 1)
             (d->needToAwakeC1 = CFALSE);
            else (d->needToAwakeC2 = CFALSE);
              { Explanation * expl;
              { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                  expl = _CL_obj;
                  } 
                GC_OBJECT(Explanation,expl);} 
              palm_self_explain_AbstractConstraint(d,expl);
              { OID gc_local;
                ITERATE(ct);
                bag *ct_support;
                ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2))))))));
                for (START(ct_support); NEXT(ct);)
                { GC_LOOP;
                  GC_OBJECT(set,expl->explanation)->addFast(ct);
                  GC_UNLOOP;} 
                } 
              palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const1),expl);
              palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const1),expl);
              (*choco.awake)(GC_OID(_oid_(d->const1)));
              } 
            } 
          if ((2 == 1) ? (d->needToAwakeC1 == CTRUE) : (d->needToAwakeC2 == CTRUE))
           { if (2 == 1)
             (d->needToAwakeC1 = CFALSE);
            else (d->needToAwakeC2 = CFALSE);
              { Explanation * expl;
              { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                  expl = _CL_obj;
                  } 
                GC_OBJECT(Explanation,expl);} 
              palm_self_explain_AbstractConstraint(d,expl);
              { OID gc_local;
                ITERATE(ct);
                bag *ct_support;
                ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))))));
                for (START(ct_support); NEXT(ct);)
                { GC_LOOP;
                  GC_OBJECT(set,expl->explanation)->addFast(ct);
                  GC_UNLOOP;} 
                } 
              palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const2),expl);
              palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,d->const2),expl);
              (*choco.awake)(GC_OID(_oid_(d->const2)));
              } 
            } 
          Result = CFALSE;
          } 
        return (Result);} 
       } 
    else{ GC_BIND;
      { ClaireBoolean *Result ;
        Result = CTRUE;
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


/* The c++ function for: choco/awakeOnInf(d:PalmDisjunction,i:integer) [] */
void  choco_awakeOnInf_PalmDisjunction(PalmDisjunction *d,int i)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset))));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(d:PalmDisjunction,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmDisjunction(PalmDisjunction *d,int i)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) i)));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(d:PalmDisjunction,i:integer) [] */
void  choco_awakeOnSup_PalmDisjunction(PalmDisjunction *d,int i)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset))));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(d:PalmDisjunction,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmDisjunction(PalmDisjunction *d,int i)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) i)));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(d:PalmDisjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmDisjunction(PalmDisjunction *d,int i,int v)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i),((int) v)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) (i-d->offset)),((int) v)));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(d:PalmDisjunction,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmDisjunction(PalmDisjunction *d,int i,int v)
{ if (palm_checkConstraintState_PalmDisjunction(d) == CTRUE) 
  { if (i <= d->offset)
     { if (!BCONTAIN(d->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(d->statusBitVector,((4*(1-1))+2)))
         { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))),((int) i),((int) v)));
          } 
        } 
      } 
    else if (!BCONTAIN(d->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(d->statusBitVector,((4*(2-1))+2)))
       { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))),((int) i),((int) v)));
        } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/askIfEntailed(d:PalmDisjunction) [] */
OID  choco_askIfEntailed_PalmDisjunction(PalmDisjunction *d)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(d->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(d->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0354 = d->statusBitVector;
              int  g0355 = (4*(1-1));
              g0354= (g0354+exp2_integer(g0355));
              if ((b == CTRUE) && 
                  (!BCONTAIN(d->statusBitVector,((4*(1-1))+1))))
               g0354= (g0354+exp2_integer((g0355+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(d->statusBitVector,((4*(1-1))+1))))
               g0354= (g0354-exp2_integer((g0355+1)));
              STOREI(d->statusBitVector,g0354);
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
            { int  g0356 = d->statusBitVector;
              int  g0357 = (4*(2-1));
              g0356= (g0356+exp2_integer(g0357));
              if ((b == CTRUE) && 
                  (!BCONTAIN(d->statusBitVector,((4*(2-1))+1))))
               g0356= (g0356+exp2_integer((g0357+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(d->statusBitVector,((4*(2-1))+1))))
               g0356= (g0356-exp2_integer((g0357+1)));
              STOREI(d->statusBitVector,g0356);
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
/* The c++ function for: choco/testIfSatisfied(d:PalmDisjunction) [] */
ClaireBoolean * choco_testIfSatisfied_PalmDisjunction(PalmDisjunction *d)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))))))) == CTRUE) ? CTRUE : (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))))))) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
/* The c++ function for: choco/propagate(d:PalmDisjunction) [] */
void  choco_propagate_PalmDisjunction(PalmDisjunction *d)
{ palm_checkStatusAndReport_PalmDisjunction(d,1);
  palm_checkStatusAndReport_PalmDisjunction(d,2);
  palm_checkConstraintState_PalmDisjunction(d);
  } 


/* The c++ function for: choco/awake(d:PalmDisjunction) [] */
void  choco_awake_PalmDisjunction_palm(PalmDisjunction *d)
{ palm_checkStatusAndReport_PalmDisjunction(d,1);
  palm_checkStatusAndReport_PalmDisjunction(d,2);
  palm_checkConstraintState_PalmDisjunction(d);
  ;} 


/* The c++ function for: whyIsTrue(d:PalmDisjunction) [] */
set * palm_whyIsTrue_PalmDisjunction(PalmDisjunction *d)
{ if (boolean_I_any(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))))) == CTRUE) 
  { { set *Result ;
      Result = OBJECT(set,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1)))))));
      return (Result);} 
     } 
  else{ if (boolean_I_any(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))))) == CTRUE) 
    { { set *Result ;
        Result = OBJECT(set,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2)))))));
        return (Result);} 
       } 
    else{ GC_BIND;
      close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - odd state for whyIsTrue for PalmDisjunction"),
        _oid_(Kernel.nil))));
      { set *Result ;
        Result = set::empty(choco._AbstractConstraint);
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


/* The c++ function for: whyIsFalse(d:PalmDisjunction) [] */
set * palm_whyIsFalse_PalmDisjunction(PalmDisjunction *d)
{ GC_BIND;
  ;;{ set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const1))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(d->const2))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/awakeOnInst(c:PalmDisjunction,idx:integer) [] */
OID  choco_awakeOnInst_PalmDisjunction(PalmDisjunction *c,int idx)
{ return (_void_(close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))))));} 


/* The c++ function for: checkPalm(ct:PalmDisjunction) [] */
ClaireBoolean * palm_checkPalm_PalmDisjunction(PalmDisjunction *ct)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const1))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const2))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port register no longer used
// *************************************************
// * Part 2 : IFTHEN : Lazy Guards                 *
// *************************************************
// -------- Lazy guards (if (c1,c2))  -----------------------------------
/* The c++ function for: initHook(c:PalmGuard) [] */
void  palm_initHook_PalmGuard(PalmGuard *c)
{ GC_BIND;
  { AbstractConstraint * g0358; 
    OID  g0359;
    g0358 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0359 = _oid_(_CL_obj);
      } 
    (g0358->hook = g0359);} 
  { OID  g0360UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 2);
      g0360UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const2->hook)),g0360UU);
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(g:PalmGuard,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmGuard(PalmGuard *g,int idx)
{ GC_BIND;
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,g->const2));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,g->const2));
  (g->needToAwakeC2 = CFALSE);
  STOREI(g->statusBitVector,0);
  GC_UNBIND;} 


/* The c++ function for: self_print(g:PalmGuard) [] */
OID  claire_self_print_PalmGuard_palm(PalmGuard *g)
{ GC_BIND;
  princ_string("e-IF (");
  print_any(GC_OID(_oid_(g->const1)));
  princ_string(") e-THEN (");
  print_any(GC_OID(_oid_(g->const2)));
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: needToAwake(g:PalmGuard,i:integer) [] */
ClaireBoolean * palm_needToAwake_PalmGuard(PalmGuard *g,int i)
{ ;return (g->needToAwakeC2);} 


/* The c++ function for: setNeedToAwake(g:PalmGuard,i:integer,val:boolean) [] */
void  palm_setNeedToAwake_PalmGuard(PalmGuard *g,int i,ClaireBoolean *val)
{ ;(g->needToAwakeC2 = val);
  } 


/* The c++ function for: checkStatusAndReport(g:PalmGuard,i:integer) [] */
void  palm_checkStatusAndReport_PalmGuard(PalmGuard *g,int i)
{ GC_BIND;
  ;;if (!BCONTAIN(g->statusBitVector,(4*(1-1))))
   { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      { int  g0361 = g->statusBitVector;
        int  g0362 = (4*(1-1));
        g0361= (g0361+exp2_integer(g0362));
        if ((b == CTRUE) && 
            (!BCONTAIN(g->statusBitVector,((4*(1-1))+1))))
         g0361= (g0361+exp2_integer((g0362+1)));
        else if ((b != CTRUE) && 
            (BCONTAIN(g->statusBitVector,((4*(1-1))+1))))
         g0361= (g0361-exp2_integer((g0362+1)));
        STOREI(g->statusBitVector,g0361);
        ;} 
      if ((b == CTRUE) && 
          (!BCONTAIN(g->statusBitVector,((4*(2-1))+2))))
       { { int  g0363 = g->statusBitVector;
          int  g0364 = (4*(2-1));
          g0363= (g0363+exp2_integer((g0364+2)));
          if ((CTRUE == CTRUE) && 
              (!BCONTAIN(g->statusBitVector,((4*(2-1))+3))))
           g0363= (g0363+exp2_integer((g0364+3)));
          else if ((boolean_I_any(Kernel.ctrue) != CTRUE) && 
              (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
           g0363= (g0363-exp2_integer((g0364+3)));
          STOREI(g->statusBitVector,g0363);
          ;} 
        (g->needToAwakeC2 = CTRUE);
        } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnConstraint(g:PalmGuard,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmGuard_palm(PalmGuard *g,int idx,int way,int newValue,int oldValue)
{ if (idx <= g->offset) 
  { { (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(g->const1)),
        idx,
        way,
        newValue,
        oldValue);
      palm_checkStatusAndReport_PalmGuard(g,1);
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(g->const2)),
      (idx-g->offset),
      way,
      newValue,
      oldValue);
    GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(g:PalmGuard,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmGuard_palm(PalmGuard *g,int idx,int way,int newValue,int oldValue)
{ if (idx <= g->offset) 
  { { (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(g->const1)),
        idx,
        way,
        newValue,
        oldValue);
      if (BCONTAIN(g->statusBitVector,(4*(1-1))))
       { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          if (b != nth_integer(g->statusBitVector,((4*(1-1))+1)))
           palm_takeIntoAccountStatusChange_PalmGuard(g,1);
          } 
        else palm_takeIntoAccountStatusChange_PalmGuard(g,1);
          } 
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(g->const2)),
      (idx-g->offset),
      way,
      newValue,
      oldValue);
    if (BCONTAIN(g->statusBitVector,(4*(2-1))))
     { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2))))));
      if (btest != CNULL)
       { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
        if (b != nth_integer(g->statusBitVector,((4*(2-1))+1)))
         palm_takeIntoAccountStatusChange_PalmGuard(g,2);
        } 
      else palm_takeIntoAccountStatusChange_PalmGuard(g,2);
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: checkConstraintState(g:PalmGuard) [] */
ClaireBoolean * palm_checkConstraintState_PalmGuard(PalmGuard *g)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ClaireBoolean * g0365I;
      { g0365I = g->needToAwakeC2;
        } 
      
      if (g0365I == CTRUE) { (g->needToAwakeC2 = CFALSE);
          { Explanation * expl;
            { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                expl = _CL_obj;
                } 
              GC_OBJECT(Explanation,expl);} 
            palm_self_explain_AbstractConstraint(g,expl);
            { OID gc_local;
              ITERATE(ct);
              bag *ct_support;
              ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))))));
              for (START(ct_support); NEXT(ct);)
              { GC_LOOP;
                GC_OBJECT(set,expl->explanation)->addFast(ct);
                GC_UNLOOP;} 
              } 
            palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,g->const2),expl);
            palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,g->const2),expl);
            (*choco.awake)(GC_OID(_oid_(g->const2)));
            } 
          Result = CFALSE;
          } 
        else Result = CTRUE;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/awakeOnInf(g:PalmGuard,i:integer) [] */
void  choco_awakeOnInf_PalmGuard(PalmGuard *g,int i)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(g:PalmGuard,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmGuard(PalmGuard *g,int i)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(g:PalmGuard,i:integer) [] */
void  choco_awakeOnSup_PalmGuard(PalmGuard *g,int i)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(g:PalmGuard,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmGuard(PalmGuard *g,int i)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset))));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(g:PalmGuard,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmGuard(PalmGuard *g,int i,int v)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset)),((int) v)));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(g:PalmGuard,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmGuard(PalmGuard *g,int i,int v)
{ if (palm_checkConstraintState_PalmGuard(g) == CTRUE) 
  { if (i <= g->offset)
     ;else if (BCONTAIN(g->statusBitVector,((4*(2-1))+2)))
     { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))),((int) (i-g->offset)),((int) v)));
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/propagate(g:PalmGuard) [] */
void  choco_propagate_PalmGuard(PalmGuard *g)
{ palm_checkStatusAndReport_PalmGuard(g,1);
  palm_checkConstraintState_PalmGuard(g);
  } 


/* The c++ function for: choco/awake(g:PalmGuard) [] */
void  choco_awake_PalmGuard_palm(PalmGuard *g)
{ palm_checkStatusAndReport_PalmGuard(g,1);
  palm_checkConstraintState_PalmGuard(g);
  ;} 


/* The c++ function for: choco/askIfEntailed(g:PalmGuard) [] */
OID  choco_askIfEntailed_PalmGuard(PalmGuard *g)
{ if (BCONTAIN(g->statusBitVector,(4*(1-1)))) 
  { { OID Result = 0;
      { ClaireBoolean * b = nth_integer(g->statusBitVector,((4*(1-1))+1));
        if (b == CTRUE)
         { if (BCONTAIN(g->statusBitVector,(4*(2-1))))
           Result = _oid_(nth_integer(g->statusBitVector,((4*(2-1))+1)));
          else { OID  b2test = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2))))));
              if (b2test != CNULL)
               { ClaireBoolean * b2 = OBJECT(ClaireBoolean,b2test);
                { int  g0366 = g->statusBitVector;
                  int  g0367 = (4*(2-1));
                  g0366= (g0366+exp2_integer(g0367));
                  if ((b2 == CTRUE) && 
                      (!BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
                   g0366= (g0366+exp2_integer((g0367+1)));
                  else if ((b2 != CTRUE) && 
                      (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
                   g0366= (g0366-exp2_integer((g0367+1)));
                  STOREI(g->statusBitVector,g0366);
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
            { int  g0368 = g->statusBitVector;
              int  g0369 = (4*(2-1));
              g0368= (g0368+exp2_integer(g0369));
              if ((b2 == CTRUE) && 
                  (!BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
               g0368= (g0368+exp2_integer((g0369+1)));
              else if ((b2 != CTRUE) && 
                  (BCONTAIN(g->statusBitVector,((4*(2-1))+1))))
               g0368= (g0368-exp2_integer((g0369+1)));
              STOREI(g->statusBitVector,g0368);
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


/* The c++ function for: choco/testIfSatisfied(g:PalmGuard) [] */
ClaireBoolean * choco_testIfSatisfied_PalmGuard(PalmGuard *g)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const1))))))) == CTRUE) ? CTRUE : (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(g->const2)))))))) == CTRUE) ? CTRUE : CFALSE));
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
/* The c++ function for: choco/awakeOnInst(c:PalmGuard,idx:integer) [] */
OID  choco_awakeOnInst_PalmGuard(PalmGuard *c,int idx)
{ return (_void_(close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))))));} 


/* The c++ function for: checkPalm(ct:PalmGuard) [] */
ClaireBoolean * palm_checkPalm_PalmGuard(PalmGuard *ct)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const1))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const2))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port register no longer used
// *************************************************
// * Part 3 : IFF                                  *
// *************************************************
// -------- Equivalence (c1 if and only if c2)  -----------------------
/* The c++ function for: initHook(c:PalmEquiv) [] */
void  palm_initHook_PalmEquiv(PalmEquiv *c)
{ GC_BIND;
  { AbstractConstraint * g0370; 
    OID  g0371;
    g0370 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0371 = _oid_(_CL_obj);
      } 
    (g0370->hook = g0371);} 
  { OID  g0372UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 1);
      g0372UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const1->hook)),g0372UU);
    } 
  { OID  g0373UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 2);
      g0373UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const2->hook)),g0373UU);
    } 
  { OID  g0374UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 3);
      g0374UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->oppositeConst1->hook)),g0374UU);
    } 
  { OID  g0375UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 4);
      g0375UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->oppositeConst2->hook)),g0375UU);
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(c:PalmEquiv,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmEquiv(PalmEquiv *c,int idx)
{ GC_BIND;
  (c->isContradictory = CFALSE);
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1));
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->oppositeConst1));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->oppositeConst1));
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2));
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->oppositeConst2));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->oppositeConst2));
  if (1 == 1)
   (c->needToAwakeC1 = CFALSE);
  else if (1 == 2)
   (c->needToAwakeC2 = CFALSE);
  else if (1 == 3)
   (c->needToAwakeNegC1 = CFALSE);
  else if (1 == 4)
   (c->needToAwakeNegC2 = CFALSE);
  if (2 == 1)
   (c->needToAwakeC1 = CFALSE);
  else if (2 == 2)
   (c->needToAwakeC2 = CFALSE);
  else if (2 == 3)
   (c->needToAwakeNegC1 = CFALSE);
  else if (2 == 4)
   (c->needToAwakeNegC2 = CFALSE);
  if (3 == 1)
   (c->needToAwakeC1 = CFALSE);
  else if (3 == 2)
   (c->needToAwakeC2 = CFALSE);
  else if (3 == 3)
   (c->needToAwakeNegC1 = CFALSE);
  else if (3 == 4)
   (c->needToAwakeNegC2 = CFALSE);
  if (4 == 1)
   (c->needToAwakeC1 = CFALSE);
  else if (4 == 2)
   (c->needToAwakeC2 = CFALSE);
  else if (4 == 3)
   (c->needToAwakeNegC1 = CFALSE);
  else if (4 == 4)
   (c->needToAwakeNegC2 = CFALSE);
  STOREI(c->statusBitVector,0);
  GC_UNBIND;} 


/* The c++ function for: self_print(c:PalmEquiv) [] */
OID  claire_self_print_PalmEquiv_palm(PalmEquiv *c)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(c->const1)));
  princ_string(") e-IFF (");
  print_any(GC_OID(_oid_(c->const2)));
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: needToAwake(g:PalmEquiv,i:integer) [] */
ClaireBoolean * palm_needToAwake_PalmEquiv(PalmEquiv *g,int i)
{ { ClaireBoolean *Result ;
    Result = ((i == 1) ?
      g->needToAwakeC1 :
      ((i == 2) ?
        g->needToAwakeC2 :
        ((i == 3) ?
          g->needToAwakeNegC1 :
          ((i == 4) ?
            g->needToAwakeNegC2 :
            CFALSE ) ) ) );
    return (Result);} 
  } 


/* The c++ function for: setNeedToAwake(g:PalmEquiv,i:integer,val:boolean) [] */
void  palm_setNeedToAwake_PalmEquiv(PalmEquiv *g,int i,ClaireBoolean *val)
{ if (i == 1)
   (g->needToAwakeC1 = val);
  else if (i == 2)
   (g->needToAwakeC2 = val);
  else if (i == 3)
   (g->needToAwakeNegC1 = val);
  else if (i == 4)
   (g->needToAwakeNegC2 = val);
  } 


/* The c++ function for: checkStatusAndReport(c:PalmEquiv,i:integer) [] */
void  palm_checkStatusAndReport_PalmEquiv(PalmEquiv *c,int i)
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
          { int  g0376 = c->statusBitVector;
            int  g0377 = (4*(i-1));
            g0376= (g0376+exp2_integer(g0377));
            if ((b == CTRUE) && 
                (!BCONTAIN(c->statusBitVector,((4*(i-1))+1))))
             g0376= (g0376+exp2_integer((g0377+1)));
            else if ((b != CTRUE) && 
                (BCONTAIN(c->statusBitVector,((4*(i-1))+1))))
             g0376= (g0376-exp2_integer((g0377+1)));
            STOREI(c->statusBitVector,g0376);
            ;} 
          { int  g0378 = c->statusBitVector;
            int  g0379 = (4*(j-1));
            g0378= (g0378+exp2_integer((g0379+2)));
            if ((b == CTRUE) && 
                (!BCONTAIN(c->statusBitVector,((4*(j-1))+3))))
             g0378= (g0378+exp2_integer((g0379+3)));
            else if ((b != CTRUE) && 
                (BCONTAIN(c->statusBitVector,((4*(j-1))+1))))
             g0378= (g0378-exp2_integer((g0379+3)));
            STOREI(c->statusBitVector,g0378);
            ;} 
          if (BCONTAIN(c->statusBitVector,(4*(j-1))))
           { if (b != nth_integer(c->statusBitVector,((4*(j-1))+1)))
             (c->isContradictory = CTRUE);
            } 
          else if (b == CTRUE)
           { if (j == 1)
             (c->needToAwakeC1 = CTRUE);
            else if (j == 2)
             (c->needToAwakeC2 = CTRUE);
            else if (j == 3)
             (c->needToAwakeNegC1 = CTRUE);
            else if (j == 4)
             (c->needToAwakeNegC2 = CTRUE);
            } 
          else if (b == CFALSE)
           { if ((j+2) == 1)
             (c->needToAwakeC1 = CTRUE);
            else if ((j+2) == 2)
             (c->needToAwakeC2 = CTRUE);
            else if ((j+2) == 3)
             (c->needToAwakeNegC1 = CTRUE);
            else if ((j+2) == 4)
             (c->needToAwakeNegC2 = CTRUE);
            } 
          } 
        else ;} 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnConstraint(c:PalmEquiv,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmEquiv_palm(PalmEquiv *c,int idx,int way,int newValue,int oldValue)
{ if (idx <= c->offset) 
  { { (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->const1)),
        idx,
        way,
        newValue,
        oldValue);
      (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->oppositeConst1)),
        (*(c->indicesInOppConst1))[idx],
        way,
        newValue,
        oldValue);
      if ((!BCONTAIN(c->statusBitVector,(4*(1-1)))) && 
          (!BCONTAIN(c->statusBitVector,((4*(1-1))+2))))
       palm_checkStatusAndReport_PalmEquiv(c,1);
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->const2)),
      (idx-c->offset),
      way,
      newValue,
      oldValue);
    (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->oppositeConst2)),
      (*(c->indicesInOppConst2))[(idx-c->offset)],
      way,
      newValue,
      oldValue);
    if ((!BCONTAIN(c->statusBitVector,(4*(2-1)))) && 
        (!BCONTAIN(c->statusBitVector,((4*(2-1))+2))))
     palm_checkStatusAndReport_PalmEquiv(c,2);
    GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:PalmEquiv,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmEquiv_palm(PalmEquiv *c,int idx,int way,int newValue,int oldValue)
{ if (idx <= c->offset) 
  { { (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->const1)),
        idx,
        way,
        newValue,
        oldValue);
      (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->oppositeConst1)),
        (*(c->indicesInOppConst1))[idx],
        way,
        newValue,
        oldValue);
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          if (b != nth_integer(c->statusBitVector,((4*(1-1))+1)))
           palm_takeIntoAccountStatusChange_PalmEquiv(c,1);
          } 
        else palm_takeIntoAccountStatusChange_PalmEquiv(c,1);
          } 
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->const2)),
      (idx-c->offset),
      way,
      newValue,
      oldValue);
    (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->oppositeConst2)),
      (*(c->indicesInOppConst2))[(idx-c->offset)],
      way,
      newValue,
      oldValue);
    if (BCONTAIN(c->statusBitVector,(4*(2-1))))
     { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
      if (btest != CNULL)
       { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
        if (b != nth_integer(c->statusBitVector,((4*(2-1))+1)))
         palm_takeIntoAccountStatusChange_PalmEquiv(c,2);
        } 
      else palm_takeIntoAccountStatusChange_PalmEquiv(c,2);
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: checkConstraintState(c:PalmEquiv) [] */
ClaireBoolean * palm_checkConstraintState_PalmEquiv(PalmEquiv *c)
{ if (c->isContradictory == CTRUE) 
  { { ClaireBoolean *Result ;
      { (c->isContradictory = CFALSE);
        { Explanation * expl;
          { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              expl = _CL_obj;
              } 
            GC_OBJECT(Explanation,expl);} 
          palm_self_explain_AbstractConstraint(c,expl);
          if ((BCONTAIN(c->statusBitVector,(4*(1-1)))) && 
              (BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
           { { OID gc_local;
              ITERATE(ct);
              bag *ct_support;
              ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))))));
              for (START(ct_support); NEXT(ct);)
              { GC_LOOP;
                GC_OBJECT(set,expl->explanation)->addFast(ct);
                GC_UNLOOP;} 
              } 
            { OID gc_local;
              ITERATE(ct);
              bag *ct_support;
              ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))));
              for (START(ct_support); NEXT(ct);)
              { GC_LOOP;
                GC_OBJECT(set,expl->explanation)->addFast(ct);
                GC_UNLOOP;} 
              } 
            } 
          else { { OID gc_local;
                ITERATE(ct);
                bag *ct_support;
                ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))));
                for (START(ct_support); NEXT(ct);)
                { GC_LOOP;
                  GC_OBJECT(set,expl->explanation)->addFast(ct);
                  GC_UNLOOP;} 
                } 
              { OID gc_local;
                ITERATE(ct);
                bag *ct_support;
                ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))))));
                for (START(ct_support); NEXT(ct);)
                { GC_LOOP;
                  GC_OBJECT(set,expl->explanation)->addFast(ct);
                  GC_UNLOOP;} 
                } 
              } 
            palm_raisePalmFakeContradiction_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) choco_getProblem_AbstractConstraint(c)->propagationEngine)),expl);
          } 
        Result = CFALSE;
        } 
      return (Result);} 
     } 
  else{ if (((1 == 1) ? (c->needToAwakeC1 == CTRUE) : ((1 == 2) ? (c->needToAwakeC2 == CTRUE) : ((1 == 3) ? (c->needToAwakeNegC1 == CTRUE) : ((1 == 4) && (c->needToAwakeNegC2 == CTRUE))))) || 
        (((2 == 1) ? (c->needToAwakeC1 == CTRUE) : ((2 == 2) ? (c->needToAwakeC2 == CTRUE) : ((2 == 3) ? (c->needToAwakeNegC1 == CTRUE) : ((2 == 4) && (c->needToAwakeNegC2 == CTRUE))))) || 
          (((3 == 1) ? (c->needToAwakeC1 == CTRUE) : ((3 == 2) ? (c->needToAwakeC2 == CTRUE) : ((3 == 3) ? (c->needToAwakeNegC1 == CTRUE) : ((3 == 4) && (c->needToAwakeNegC2 == CTRUE))))) || 
            ((4 == 1) ? (c->needToAwakeC1 == CTRUE) : ((4 == 2) ? (c->needToAwakeC2 == CTRUE) : ((4 == 3) ? (c->needToAwakeNegC1 == CTRUE) : ((4 == 4) && (c->needToAwakeNegC2 == CTRUE)))))))) 
    { { ClaireBoolean *Result ;
        { { int  i = 1;
            int  g0380 = 4;
            { OID gc_local;
              while ((i <= g0380))
              { GC_LOOP;
                if ((i == 1) ? (c->needToAwakeC1 == CTRUE) : ((i == 2) ? (c->needToAwakeC2 == CTRUE) : ((i == 3) ? (c->needToAwakeNegC1 == CTRUE) : ((i == 4) && (c->needToAwakeNegC2 == CTRUE)))))
                 { if (i == 1)
                   (c->needToAwakeC1 = CFALSE);
                  else if (i == 2)
                   (c->needToAwakeC2 = CFALSE);
                  else if (i == 3)
                   (c->needToAwakeNegC1 = CFALSE);
                  else if (i == 4)
                   (c->needToAwakeNegC2 = CFALSE);
                  { Explanation * expl;
                    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                        expl = _CL_obj;
                        } 
                      GC_OBJECT(Explanation,expl);} 
                    OID  ci;
                    { if (i == 1)
                       ci = _oid_(c->const1);
                      else if (i == 2)
                       ci = _oid_(c->const2);
                      else if (i == 3)
                       ci = _oid_(c->oppositeConst1);
                      else if (i == 4)
                       ci = _oid_(c->oppositeConst2);
                      else ci = Kernel.cfalse;
                        GC_OID(ci);} 
                    OID  cj;
                    { if (i == 1)
                       cj = _oid_(c->const2);
                      else if (i == 2)
                       cj = _oid_(c->const1);
                      else if (i == 3)
                       cj = _oid_(c->const2);
                      else if (i == 4)
                       cj = _oid_(c->const1);
                      else cj = Kernel.cfalse;
                        GC_OID(cj);} 
                    OID  j;
                    if (i == 1)
                     j = 2;
                    else if (i == 2)
                     j = 1;
                    else if (i == 3)
                     j = 4;
                    else if (i == 4)
                     j = 3;
                    else j = Kernel.cfalse;
                      palm_self_explain_AbstractConstraint(c,expl);
                    if (i <= 2)
                     { { OID gc_local;
                        ITERATE(ct);
                        bag *ct_support;
                        ct_support = GC_OBJECT(set,OBJECT(bag,(*palm.whyIsTrue)(cj)));
                        for (START(ct_support); NEXT(ct);)
                        { GC_LOOP;
                          GC_OBJECT(set,expl->explanation)->addFast(ct);
                          GC_UNLOOP;} 
                        } 
                      } 
                    else { { OID gc_local;
                          ITERATE(ct);
                          bag *ct_support;
                          ct_support = GC_OBJECT(set,OBJECT(bag,(*palm.whyIsFalse)(cj)));
                          for (START(ct_support); NEXT(ct);)
                          { GC_LOOP;
                            GC_OBJECT(set,expl->explanation)->addFast(ct);
                            GC_UNLOOP;} 
                          } 
                        } 
                      palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,ci),expl);
                    palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,ci),expl);
                    (*choco.awake)(ci);
                    } 
                  } 
                ++i;
                GC_UNLOOP;} 
              } 
            } 
          Result = CFALSE;
          } 
        return (Result);} 
       } 
    else{ GC_BIND;
      { ClaireBoolean *Result ;
        Result = CTRUE;
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


/* The c++ function for: choco/awakeOnInf(c:PalmEquiv,i:integer) [] */
void  choco_awakeOnInf_PalmEquiv(PalmEquiv *c,int i)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           ;if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
          else _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         ;if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(c:PalmEquiv,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmEquiv(PalmEquiv *c,int i)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           ;if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
          else _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         ;if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(c:PalmEquiv,i:integer) [] */
void  choco_awakeOnSup_PalmEquiv(PalmEquiv *c,int i)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           ;if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
          else _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         ;if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmEquiv,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmEquiv(PalmEquiv *c,int i)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           ;if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
          else _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i])));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         ;if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
        else _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)])));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(c:PalmEquiv,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmEquiv(PalmEquiv *c,int i,int v)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
          else _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i]),((int) v)));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
        else _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)]),((int) v)));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(c:PalmEquiv,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmEquiv(PalmEquiv *c,int i,int v)
{ if (palm_checkConstraintState_PalmEquiv(c) == CTRUE) 
  { if (i <= c->offset)
     { if (!BCONTAIN(c->statusBitVector,(4*(1-1))))
       { if (BCONTAIN(c->statusBitVector,((4*(1-1))+2)))
         { if (BCONTAIN(c->statusBitVector,((4*(1-1))+3)))
           _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
          else _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst1)))),((int) (*(c->indicesInOppConst1))[i]),((int) v)));
            } 
        } 
      } 
    else if (!BCONTAIN(c->statusBitVector,(4*(2-1))))
     { if (BCONTAIN(c->statusBitVector,((4*(2-1))+2)))
       { if (BCONTAIN(c->statusBitVector,((4*(2-1))+3)))
         _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
        else _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->oppositeConst2)))),((int) (*(c->indicesInOppConst2))[(i-c->offset)]),((int) v)));
          } 
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/propagate(c:PalmEquiv) [] */
void  choco_propagate_PalmEquiv(PalmEquiv *c)
{ palm_checkStatusAndReport_PalmEquiv(c,1);
  palm_checkStatusAndReport_PalmEquiv(c,2);
  palm_checkConstraintState_PalmEquiv(c);
  } 


/* The c++ function for: choco/awake(c:PalmEquiv) [] */
void  choco_awake_PalmEquiv_palm(PalmEquiv *c)
{ palm_checkStatusAndReport_PalmEquiv(c,1);
  palm_checkStatusAndReport_PalmEquiv(c,2);
  palm_checkConstraintState_PalmEquiv(c);
  ;} 


/* The c++ function for: choco/askIfEntailed(c:PalmEquiv) [] */
OID  choco_askIfEntailed_PalmEquiv(PalmEquiv *c)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(c->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0381 = c->statusBitVector;
              int  g0382 = (4*(1-1));
              g0381= (g0381+exp2_integer(g0382));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0381= (g0381+exp2_integer((g0382+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0381= (g0381-exp2_integer((g0382+1)));
              STOREI(c->statusBitVector,g0381);
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
            { int  g0383 = c->statusBitVector;
              int  g0384 = (4*(2-1));
              g0383= (g0383+exp2_integer(g0384));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0383= (g0383+exp2_integer((g0384+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0383= (g0383-exp2_integer((g0384+1)));
              STOREI(c->statusBitVector,g0383);
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


/* The c++ function for: choco/testIfSatisfied(c:PalmEquiv) [] */
ClaireBoolean * choco_testIfSatisfied_PalmEquiv(PalmEquiv *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((equal(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))),_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))) == CTRUE) ? CTRUE : CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
/* The c++ function for: choco/awakeOnInst(c:PalmEquiv,i:integer) [] */
void  choco_awakeOnInst_PalmEquiv(PalmEquiv *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: checkPalm(ct:PalmEquiv) [] */
ClaireBoolean * palm_checkPalm_PalmEquiv(PalmEquiv *ct)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const1))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const2))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port register no longer used
// *************************************************
// * Part 4 : AND                                  *
// *************************************************
// -------- Conjunctions (only used in subterms of Boolean formulae) --
// note v1.02: for conjunctions, targetStatus slots are useless -> we only use status fields
/* The c++ function for: initHook(c:PalmConjunction) [] */
void  palm_initHook_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { AbstractConstraint * g0385; 
    OID  g0386;
    g0385 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0386 = _oid_(_CL_obj);
      } 
    (g0385->hook = g0386);} 
  { OID  g0387UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 1);
      g0387UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const1->hook)),g0387UU);
    } 
  { OID  g0388UU;
    { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
      (_CL_obj->constraint = c);
      (_CL_obj->index = 2);
      g0388UU = _oid_(_CL_obj);
      } 
    add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,c->const2->hook)),g0388UU);
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(c:PalmConjunction,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmConjunction(PalmConjunction *c,int idx)
{ GC_BIND;
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1));
  palm_removeIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2));
  palm_setDirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2));
  STOREI(c->statusBitVector,0);
  GC_UNBIND;} 


/* The c++ function for: self_print(c:PalmConjunction) [] */
OID  claire_self_print_PalmConjunction_palm(PalmConjunction *c)
{ GC_BIND;
  princ_string("(");
  print_any(GC_OID(_oid_(c->const1)));
  princ_string(") e-AND (");
  print_any(GC_OID(_oid_(c->const2)));
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: updateDataStructuresOnConstraint(c:PalmConjunction,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmConjunction_palm(PalmConjunction *c,int idx,int way,int newValue,int oldValue)
{ if (idx <= c->offset) 
  { (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->const1)),
      idx,
      way,
      newValue,
      oldValue);
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnConstraint)(GC_OID(_oid_(c->const2)),
      (idx-c->offset),
      way,
      newValue,
      oldValue);
    GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:PalmConjunction,idx:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmConjunction_palm(PalmConjunction *c,int idx,int way,int newValue,int oldValue)
{ if (idx <= c->offset) 
  { { (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->const1)),
        idx,
        way,
        newValue,
        oldValue);
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          if (b != nth_integer(c->statusBitVector,((4*(1-1))+1)))
           palm_takeIntoAccountStatusChange_PalmConjunction(c,1);
          } 
        else palm_takeIntoAccountStatusChange_PalmConjunction(c,1);
          } 
      } 
     } 
  else{ GC_BIND;
    (*palm.updateDataStructuresOnRestoreConstraint)(GC_OID(_oid_(c->const2)),
      (idx-c->offset),
      way,
      newValue,
      oldValue);
    if (BCONTAIN(c->statusBitVector,(4*(2-1))))
     { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
      if (btest != CNULL)
       { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
        if (b != nth_integer(c->statusBitVector,((4*(2-1))+1)))
         palm_takeIntoAccountStatusChange_PalmConjunction(c,2);
        } 
      else palm_takeIntoAccountStatusChange_PalmConjunction(c,2);
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnInf(c:PalmConjunction,i:integer) [] */
void  choco_awakeOnInf_PalmConjunction(PalmConjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(c:PalmConjunction,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmConjunction(PalmConjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(c:PalmConjunction,i:integer) [] */
void  choco_awakeOnSup_PalmConjunction(PalmConjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmConjunction,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmConjunction(PalmConjunction *c,int i)
{ if (i <= c->offset) 
  { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i)));
     } 
  else{ GC_BIND;
    _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset))));
    GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(c:PalmConjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmConjunction(PalmConjunction *c,int i,int v)
{ if (i <= c->offset) 
  { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
     } 
  else{ GC_BIND;
    _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(c:PalmConjunction,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmConjunction(PalmConjunction *c,int i,int v)
{ if (i <= c->offset) 
  { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))),((int) i),((int) v)));
     } 
  else{ GC_BIND;
    _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))),((int) (i-c->offset)),((int) v)));
    GC_UNBIND;} 
  } 


/* The c++ function for: choco/propagate(c:PalmConjunction) [] */
void  choco_propagate_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { Explanation * e;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        e = _CL_obj;
        } 
      GC_OBJECT(Explanation,e);} 
    palm_self_explain_AbstractConstraint(c,e);
    palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
    palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1),e);
    palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
    palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2),e);
    } 
  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
  _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))));
  GC_UNBIND;} 


/* The c++ function for: choco/awake(c:PalmConjunction) [] */
void  choco_awake_PalmConjunction_palm(PalmConjunction *c)
{ GC_BIND;
  { Explanation * e;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        e = _CL_obj;
        } 
      GC_OBJECT(Explanation,e);} 
    palm_self_explain_AbstractConstraint(c,e);
    palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
    palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const1),e);
    palm_setIndirect_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
    palm_setIndirectDependance_AbstractConstraint(GC_OBJECT(AbstractConstraint,c->const2),e);
    } 
  (*choco.awake)(GC_OID(_oid_(c->const1)));
  (*choco.awake)(GC_OID(_oid_(c->const2)));
  ;GC_UNBIND;} 


/* The c++ function for: choco/askIfEntailed(c:PalmConjunction) [] */
OID  choco_askIfEntailed_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { OID Result = 0;
    { OID  leftOK;
      if (BCONTAIN(c->statusBitVector,(4*(1-1))))
       leftOK = _oid_(nth_integer(c->statusBitVector,((4*(1-1))+1)));
      else { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))));
          if (btest != CNULL)
           { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
            { int  g0389 = c->statusBitVector;
              int  g0390 = (4*(1-1));
              g0389= (g0389+exp2_integer(g0390));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0389= (g0389+exp2_integer((g0390+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(1-1))+1))))
               g0389= (g0389-exp2_integer((g0390+1)));
              STOREI(c->statusBitVector,g0389);
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
            { int  g0391 = c->statusBitVector;
              int  g0392 = (4*(2-1));
              g0391= (g0391+exp2_integer(g0392));
              if ((b == CTRUE) && 
                  (!BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0391= (g0391+exp2_integer((g0392+1)));
              else if ((b != CTRUE) && 
                  (BCONTAIN(c->statusBitVector,((4*(2-1))+1))))
               g0391= (g0391-exp2_integer((g0392+1)));
              STOREI(c->statusBitVector,g0391);
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


/* The c++ function for: whyIsTrue(c:PalmConjunction) [] */
set * palm_whyIsTrue_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: whyIsFalse(c:PalmConjunction) [] */
set * palm_whyIsFalse_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { set *Result ;
    { OID  leftOK_ask;
      { if (BCONTAIN(c->statusBitVector,(4*(1-1))))
         leftOK_ask = _oid_(nth_integer(c->statusBitVector,((4*(1-1))+1)));
        else leftOK_ask = choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))));
          GC_OID(leftOK_ask);} 
      OID  rightOK_ask;
      { if (BCONTAIN(c->statusBitVector,(4*(2-1))))
         rightOK_ask = _oid_(nth_integer(c->statusBitVector,((4*(2-1))+1)));
        else rightOK_ask = choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))));
          GC_OID(rightOK_ask);} 
      Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      if (leftOK_ask == Kernel.cfalse)
       { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      else if (rightOK_ask == Kernel.cfalse)
       { OID gc_local;
        ITERATE(ct);
        bag *ct_support;
        ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2))))))));
        for (START(ct_support); NEXT(ct);)
        { GC_LOOP;
          e->explanation->addFast(ct);
          GC_UNLOOP;} 
        } 
      else close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl odd state for whyIsFalse for PalmConjunction"),
          _oid_(Kernel.nil))));
        Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/testIfSatisfied(c:PalmConjunction) [] */
ClaireBoolean * choco_testIfSatisfied_PalmConjunction(PalmConjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const1)))))))) == CTRUE) ? (((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->const2)))))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
// v0.34
/* The c++ function for: choco/awakeOnInst(c:PalmConjunction,i:integer) [] */
void  choco_awakeOnInst_PalmConjunction(PalmConjunction *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: checkPalm(ct:PalmConjunction) [] */
ClaireBoolean * palm_checkPalm_PalmConjunction(PalmConjunction *ct)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const1))))) == CTRUE) ? (((OBJECT(ClaireBoolean,(*palm.checkPalm)(GC_OID(_oid_(ct->const2))))) == CTRUE) ? CTRUE: CFALSE): CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// claire3 port register no longer used
// ********************************************************************
// *   Part 5: Large AND                                              *
// ********************************************************************
// -------- Large Conjunctions (c1 and c2 and c3 ..... and cn) -----------
/* The c++ function for: initHook(c:PalmLargeConjunction) [] */
void  palm_initHook_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  { AbstractConstraint * g0394; 
    OID  g0395;
    g0394 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0395 = _oid_(_CL_obj);
      } 
    (g0394->hook = g0395);} 
  { int  i = 1;
    int  g0393 = c->nbConst;
    { OID gc_local;
      while ((i <= g0393))
      { GC_LOOP;
        { OID  g0396UU;
          { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
            (_CL_obj->constraint = c);
            (_CL_obj->index = i);
            g0396UU = _oid_(_CL_obj);
            } 
          add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,(*(c->lconst))[i])->hook)),g0396UU);
          } 
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(c:PalmLargeConjunction,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmLargeConjunction(PalmLargeConjunction *c,int idx)
{ { int  k = 1;
    int  g0397 = c->nbConst;
    { OID gc_local;
      while ((k <= g0397))
      { // HOHO, GC_LOOP not needed !
        palm_removeIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]));
        palm_setDirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]));
        ++k;
        } 
      } 
    } 
  { int  k = 1;
    int  g0398 = c->statusBitVectorList->length;
    { OID gc_local;
      while ((k <= g0398))
      { // HOHO, GC_LOOP not needed !
        ((*(c->statusBitVectorList))[k]=0);
        ++k;
        } 
      } 
    } 
  } 


/* The c++ function for: self_print(c:PalmLargeConjunction) [] */
OID  claire_self_print_PalmLargeConjunction_palm(PalmLargeConjunction *c)
{ princ_string("(");
  print_any((*(c->lconst))[1]);
  princ_string(")");
  { OID Result = 0;
    { int  i = 2;
      int  g0399 = c->nbConst;
      { OID gc_local;
        Result= _oid_(CFALSE);
        while ((i <= g0399))
        { // HOHO, GC_LOOP not needed !
          princ_string(" e-AND (");
          print_any((*(c->lconst))[i]);
          princ_string(")");
          ++i;
          } 
        } 
      } 
    return (Result);} 
  } 


/* The c++ function for: updateDataStructuresOnConstraint(c:PalmLargeConjunction,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmLargeConjunction_palm(PalmLargeConjunction *c,int i,int way,int newValue,int oldValue)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0400 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0400))
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
      (*palm.updateDataStructuresOnConstraint)((*(c->lconst))[idx],
        reali,
        way,
        newValue,
        oldValue);
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(c:PalmLargeConjunction,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmLargeConjunction_palm(PalmLargeConjunction *c,int i,int way,int newValue,int oldValue)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0401 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0401))
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
      (*palm.updateDataStructuresOnRestoreConstraint)((*(c->lconst))[idx],
        reali,
        way,
        newValue,
        oldValue);
      { ClaireBoolean * g0406I;
        { int  g0402 = ((idx/7)+1);
          int  g0403 = (4*mod_integer((idx-1),7));
          g0406I = nth_integer((*(c->statusBitVectorList))[g0402],g0403);
          } 
        
        if (g0406I == CTRUE) { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx]))));
            if (btest != CNULL)
             { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
              { ClaireBoolean * g0407I;
                { OID  g0408UU;
                  { int  g0404 = ((idx/7)+1);
                    int  g0405 = (4*mod_integer((idx-1),7));
                    g0408UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0404],(g0405+1)));
                    } 
                  g0407I = ((_oid_(b) != g0408UU) ? CTRUE : CFALSE);
                  } 
                
                if (g0407I == CTRUE) palm_takeIntoAccountStatusChange_PalmLargeConjunction(c,idx);
                  } 
              } 
            else palm_takeIntoAccountStatusChange_PalmLargeConjunction(c,idx);
              } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnInf(c:PalmLargeConjunction,i:integer) [] */
void  choco_awakeOnInf_PalmLargeConjunction(PalmLargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0409 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0409))
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


/* The c++ function for: awakeOnRestoreInf(c:PalmLargeConjunction,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmLargeConjunction(PalmLargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0410 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0410))
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
      _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnSup(c:PalmLargeConjunction,i:integer) [] */
void  choco_awakeOnSup_PalmLargeConjunction(PalmLargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0411 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0411))
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


/* The c++ function for: awakeOnRestoreSup(c:PalmLargeConjunction,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmLargeConjunction(PalmLargeConjunction *c,int i)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0412 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0412))
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
      _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnRem(c:PalmLargeConjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmLargeConjunction(PalmLargeConjunction *c,int i,int v)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0413 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0413))
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


/* The c++ function for: awakeOnRestoreVal(c:PalmLargeConjunction,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmLargeConjunction(PalmLargeConjunction *c,int i,int v)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0414 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0414))
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
      _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: choco/propagate(c:PalmLargeConjunction) [] */
void  choco_propagate_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  { Explanation * e;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        e = _CL_obj;
        } 
      GC_OBJECT(Explanation,e);} 
    palm_self_explain_AbstractConstraint(c,e);
    { OID gc_local;
      ITERATE(ct);
      bag *ct_support;
      ct_support = GC_OBJECT(list,c->lconst);
      for (START(ct_support); NEXT(ct);)
      { GC_LOOP;
        { palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,ct),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
          palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,ct),e);
          } 
        GC_UNLOOP;} 
      } 
    } 
  { int  i = 1;
    int  g0415 = c->nbConst;
    { OID gc_local;
      while ((i <= g0415))
      { // HOHO, GC_LOOP not needed !
        _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
        ++i;
        } 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/awake(c:PalmLargeConjunction) [] */
void  choco_awake_PalmLargeConjunction_palm(PalmLargeConjunction *c)
{ GC_BIND;
  { Explanation * e;
    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
        e = _CL_obj;
        } 
      GC_OBJECT(Explanation,e);} 
    palm_self_explain_AbstractConstraint(c,e);
    { OID gc_local;
      ITERATE(ct);
      bag *ct_support;
      ct_support = GC_OBJECT(list,c->lconst);
      for (START(ct_support); NEXT(ct);)
      { GC_LOOP;
        { palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,ct),GC_OBJECT(Explanation,palm_clone_Explanation(e)));
          palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,ct),e);
          } 
        GC_UNLOOP;} 
      } 
    } 
  { int  i = 1;
    int  g0416 = c->nbConst;
    { OID gc_local;
      while ((i <= g0416))
      { // HOHO, GC_LOOP not needed !
        (*choco.awake)((*(c->lconst))[i]);
        ++i;
        } 
      } 
    } 
  ;GC_UNBIND;} 


/* The c++ function for: choco/askIfEntailed(c:PalmLargeConjunction) [] */
OID  choco_askIfEntailed_PalmLargeConjunction(PalmLargeConjunction *c)
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
            { ClaireBoolean * g0429I;
              { int  g0417 = ((i/7)+1);
                int  g0418 = (4*mod_integer((i-1),7));
                g0429I = nth_integer((*(c->statusBitVectorList))[g0417],g0418);
                } 
              
              if (g0429I == CTRUE) { { int  g0419 = ((i/7)+1);
                    int  g0420 = (4*mod_integer((i-1),7));
                    nth_integer((*(c->statusBitVectorList))[g0419],(g0420+1));
                    } 
                  ithStatus = Kernel.cfalse;
                  } 
                else { OID  bitest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
                if (bitest != CNULL)
                 { ClaireBoolean * bi = OBJECT(ClaireBoolean,bitest);
                  { int  g0421 = ((i/7)+1);
                    int  g0422 = (4*mod_integer((i-1),7));
                    list * g0423 = GC_OBJECT(list,c->statusBitVectorList);
                    int  g0424 = (*(g0423))[g0421];
                    g0424= (g0424+exp2_integer(g0422));
                    { ClaireBoolean * g0430I;
                      { ClaireBoolean *v_and;
                        { v_and = bi;
                          if (v_and == CFALSE) g0430I =CFALSE; 
                          else { { OID  g0431UU;
                              { int  g0425 = ((i/7)+1);
                                int  g0426 = (4*mod_integer((i-1),7));
                                g0431UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0425],(g0426+1)));
                                } 
                              v_and = not_any(g0431UU);
                              } 
                            if (v_and == CFALSE) g0430I =CFALSE; 
                            else g0430I = CTRUE;} 
                          } 
                        } 
                      
                      if (g0430I == CTRUE) g0424= (g0424+exp2_integer((g0422+1)));
                        else { ClaireBoolean * g0432I;
                        { ClaireBoolean *v_and;
                          { v_and = not_any(_oid_(bi));
                            if (v_and == CFALSE) g0432I =CFALSE; 
                            else { { int  g0427 = ((i/7)+1);
                                int  g0428 = (4*mod_integer((i-1),7));
                                v_and = nth_integer((*(c->statusBitVectorList))[g0427],(g0428+1));
                                } 
                              if (v_and == CFALSE) g0432I =CFALSE; 
                              else g0432I = CTRUE;} 
                            } 
                          } 
                        
                        if (g0432I == CTRUE) g0424= (g0424-exp2_integer((g0422+1)));
                          } 
                      } 
                    STOREI((*c->statusBitVectorList)[g0421],g0424);
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


/* The c++ function for: choco/testIfSatisfied(c:PalmLargeConjunction) [] */
ClaireBoolean * choco_testIfSatisfied_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0433UU;
      { OID gc_local;
        ITERATE(subc);
        g0433UU= _oid_(CFALSE);
        for (START(c->lconst); NEXT(subc);)
        { GC_LOOP;
          if (not_any(_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,subc))))) == CTRUE)
           { g0433UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = not_any(g0433UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
/* The c++ function for: whyIsTrue(c:PalmLargeConjunction) [] */
set * palm_whyIsTrue_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  ;{ set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      { int  i = 1;
        int  g0434 = c->nbConst;
        { OID gc_local;
          while ((i <= g0434))
          { GC_LOOP;
            { OID gc_local;
              ITERATE(ct);
              bag *ct_support;
              ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))))));
              for (START(ct_support); NEXT(ct);)
              { GC_LOOP;
                e->explanation->addFast(ct);
                GC_UNLOOP;} 
              } 
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: whyIsFalse(c:PalmLargeConjunction) [] */
set * palm_whyIsFalse_PalmLargeConjunction(PalmLargeConjunction *c)
{ GC_BIND;
  { set *Result ;
    { OID  itest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0435 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0435))
              { // HOHO, GC_LOOP not needed !
                { ClaireBoolean * g0440I;
                  { ClaireBoolean *v_or;
                    { { ClaireBoolean *v_and;
                        { { int  g0436 = ((idx/7)+1);
                            int  g0437 = (4*mod_integer((idx-1),7));
                            v_and = nth_integer((*(c->statusBitVectorList))[g0436],g0437);
                            } 
                          if (v_and == CFALSE) v_or =CFALSE; 
                          else { { OID  g0441UU;
                              { int  g0438 = ((idx/7)+1);
                                int  g0439 = (4*mod_integer((idx-1),7));
                                g0441UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0438],(g0439+1)));
                                } 
                              v_and = not_any(g0441UU);
                              } 
                            if (v_and == CFALSE) v_or =CFALSE; 
                            else v_or = CTRUE;} 
                          } 
                        } 
                      if (v_or == CTRUE) g0440I =CTRUE; 
                      else { v_or = ((choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx]))) == Kernel.cfalse) ? CTRUE : CFALSE);
                        if (v_or == CTRUE) g0440I =CTRUE; 
                        else g0440I = CFALSE;} 
                      } 
                    } 
                  
                  if (g0440I == CTRUE) { idx_some= idx;
                      break;} 
                    } 
                ++idx;
                } 
              } 
            } 
          itest = idx_some;
          } 
        GC_OID(itest);} 
      if (itest != CNULL)
       { int  i = itest;
        Explanation * e;
        { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
            e = _CL_obj;
            } 
          GC_OBJECT(Explanation,e);} 
        { OID gc_local;
          ITERATE(ct);
          bag *ct_support;
          ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))))));
          for (START(ct_support); NEXT(ct);)
          { GC_LOOP;
            e->explanation->addFast(ct);
            GC_UNLOOP;} 
          } 
        Result = e->explanation;
        } 
      else { close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - whyIsFalse(c: ~S) should be called with a non true constraint !!!"),
            _oid_(list::alloc(1,_oid_(c->isa))))));
          Result = set::empty(choco._AbstractConstraint);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/awakeOnInst(c:PalmLargeConjunction,i:integer) [] */
void  choco_awakeOnInst_PalmLargeConjunction(PalmLargeConjunction *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should net be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: checkPalm(ct:PalmLargeConjunction) [] */
ClaireBoolean * palm_checkPalm_PalmLargeConjunction(PalmLargeConjunction *ct)
{ { ClaireBoolean *Result ;
    { OID  g0443UU;
      { int  i = 1;
        int  g0442 = ct->nbConst;
        { OID gc_local;
          g0443UU= _oid_(CFALSE);
          while ((i <= g0442))
          { // HOHO, GC_LOOP not needed !
            if (not_any((*palm.checkPalm)((*(ct->lconst))[i])) == CTRUE)
             { g0443UU = Kernel.ctrue;
              break;} 
            ++i;
            } 
          } 
        } 
      Result = not_any(g0443UU);
      } 
    return (Result);} 
  } 


// claire3 port register no longer used
// ********************************************************************
// *   Part 6: Large OR                                               *
// ********************************************************************
// -------- Large Disjunctions (c1 or c2 or c3 ..... or cn) -----------
/* The c++ function for: initHook(c:PalmLargeDisjunction) [] */
void  palm_initHook_PalmLargeDisjunction(PalmLargeDisjunction *c)
{ GC_BIND;
  { AbstractConstraint * g0445; 
    OID  g0446;
    g0445 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0446 = _oid_(_CL_obj);
      } 
    (g0445->hook = g0446);} 
  { int  i = 1;
    int  g0444 = c->nbConst;
    { OID gc_local;
      while ((i <= g0444))
      { GC_LOOP;
        { OID  g0447UU;
          { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
            (_CL_obj->constraint = c);
            (_CL_obj->index = i);
            g0447UU = _oid_(_CL_obj);
            } 
          add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,(*(c->lconst))[i])->hook)),g0447UU);
          } 
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: takeIntoAccountStatusChange(d:PalmLargeDisjunction,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmLargeDisjunction(PalmLargeDisjunction *d,int idx)
{ (d->isContradictory = CFALSE);
  { int  k = 1;
    int  g0448 = d->nbConst;
    { OID gc_local;
      while ((k <= g0448))
      { // HOHO, GC_LOOP not needed !
        ((*(d->needToAwake))[k]=Kernel.cfalse);
        palm_removeIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(d->lconst))[k]));
        palm_setDirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(d->lconst))[k]));
        ++k;
        } 
      } 
    } 
  { int  k = 1;
    int  g0449 = d->statusBitVectorList->length;
    { OID gc_local;
      while ((k <= g0449))
      { // HOHO, GC_LOOP not needed !
        ((*(d->statusBitVectorList))[k]=0);
        ++k;
        } 
      } 
    } 
  STOREI(d->nbTrueStatus,0);
  STOREI(d->nbFalseStatus,0);
  } 


/* The c++ function for: self_print(c:PalmLargeDisjunction) [] */
OID  claire_self_print_PalmLargeDisjunction_palm(PalmLargeDisjunction *c)
{ princ_string("(");
  print_any((*(c->lconst))[1]);
  princ_string(")");
  { OID Result = 0;
    { int  i = 2;
      int  g0450 = c->nbConst;
      { OID gc_local;
        Result= _oid_(CFALSE);
        while ((i <= g0450))
        { // HOHO, GC_LOOP not needed !
          princ_string(" e-OR (");
          print_any((*(c->lconst))[i]);
          princ_string(")");
          ++i;
          } 
        } 
      } 
    return (Result);} 
  } 


/* The c++ function for: needToAwake(d:PalmLargeDisjunction,i:integer) [] */
ClaireBoolean * palm_needToAwake_PalmLargeDisjunction2(PalmLargeDisjunction *d,int i)
{ return (OBJECT(ClaireBoolean,(*(d->needToAwake))[i]));} 


/* The c++ function for: setNeedToAwake(d:PalmLargeDisjunction,i:integer,val:boolean) [] */
void  palm_setNeedToAwake_PalmLargeDisjunction(PalmLargeDisjunction *d,int i,ClaireBoolean *val)
{ ((*(d->needToAwake))[i]=_oid_(val));
  } 


/* The c++ function for: checkStatusAndReport(d:PalmLargeDisjunction,i:integer) [] */
void  palm_checkStatusAndReport_PalmLargeDisjunction(PalmLargeDisjunction *d,int i)
{ GC_BIND;
  { ClaireBoolean * g0476I;
    { OID  g0477UU;
      { int  g0451 = ((i/7)+1);
        int  g0452 = (4*mod_integer((i-1),7));
        g0477UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0451],g0452));
        } 
      g0476I = not_any(g0477UU);
      } 
    
    if (g0476I == CTRUE) { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[i]))));
        if (btest != CNULL)
         { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
          { ClaireBoolean * g0478I;
            { int  g0453 = ((i/7)+1);
              int  g0454 = (4*mod_integer((i-1),7));
              g0478I = nth_integer((*(d->statusBitVectorList))[g0453],(g0454+2));
              } 
            
            if (g0478I == CTRUE) { ClaireBoolean * tgtb;
                { int  g0455 = ((i/7)+1);
                  int  g0456 = (4*mod_integer((i-1),7));
                  tgtb = nth_integer((*(d->statusBitVectorList))[g0455],(g0456+3));
                  } 
                if (b != tgtb)
                 (d->isContradictory = CTRUE);
                } 
              } 
          if (d->isContradictory != CTRUE)
           { { int  g0457 = ((i/7)+1);
              int  g0458 = (4*mod_integer((i-1),7));
              list * g0459 = GC_OBJECT(list,d->statusBitVectorList);
              int  g0460 = (*(g0459))[g0457];
              g0460= (g0460+exp2_integer(g0458));
              { ClaireBoolean * g0479I;
                { ClaireBoolean *v_and;
                  { v_and = b;
                    if (v_and == CFALSE) g0479I =CFALSE; 
                    else { { OID  g0480UU;
                        { int  g0461 = ((i/7)+1);
                          int  g0462 = (4*mod_integer((i-1),7));
                          g0480UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0461],(g0462+1)));
                          } 
                        v_and = not_any(g0480UU);
                        } 
                      if (v_and == CFALSE) g0479I =CFALSE; 
                      else g0479I = CTRUE;} 
                    } 
                  } 
                
                if (g0479I == CTRUE) g0460= (g0460+exp2_integer((g0458+1)));
                  else { ClaireBoolean * g0481I;
                  { ClaireBoolean *v_and;
                    { v_and = not_any(_oid_(b));
                      if (v_and == CFALSE) g0481I =CFALSE; 
                      else { { int  g0463 = ((i/7)+1);
                          int  g0464 = (4*mod_integer((i-1),7));
                          v_and = nth_integer((*(d->statusBitVectorList))[g0463],(g0464+1));
                          } 
                        if (v_and == CFALSE) g0481I =CFALSE; 
                        else g0481I = CTRUE;} 
                      } 
                    } 
                  
                  if (g0481I == CTRUE) g0460= (g0460-exp2_integer((g0458+1)));
                    } 
                } 
              STOREI((*d->statusBitVectorList)[g0457],g0460);
              ;} 
            if (b == CTRUE)
             STOREI(d->nbTrueStatus,(d->nbTrueStatus+1));
            else { STOREI(d->nbFalseStatus,(d->nbFalseStatus+1));
                if ((d->nbFalseStatus == (d->nbConst-1)) && 
                    (d->nbTrueStatus == 0))
                 { OID  jtest;
                  { { OID  j_some = CNULL;
                      { int  j = 1;
                        int  g0465 = d->nbConst;
                        { OID gc_local;
                          while ((j <= g0465))
                          { // HOHO, GC_LOOP not needed !
                            { ClaireBoolean * g0482I;
                              { OID  g0483UU;
                                { int  g0466 = ((j/7)+1);
                                  int  g0467 = (4*mod_integer((j-1),7));
                                  g0483UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0466],g0467));
                                  } 
                                g0482I = not_any(g0483UU);
                                } 
                              
                              if (g0482I == CTRUE) { j_some= j;
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
                    { int  g0468 = ((j/7)+1);
                      int  g0469 = (4*mod_integer((j-1),7));
                      list * g0470 = GC_OBJECT(list,d->statusBitVectorList);
                      int  g0471 = (*(g0470))[g0468];
                      g0471= (g0471+exp2_integer((g0469+2)));
                      { ClaireBoolean * g0484I;
                        { ClaireBoolean *v_and;
                          { v_and = CTRUE;
                            if (v_and == CFALSE) g0484I =CFALSE; 
                            else { { OID  g0485UU;
                                { int  g0472 = ((j/7)+1);
                                  int  g0473 = (4*mod_integer((j-1),7));
                                  g0485UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0472],(g0473+3)));
                                  } 
                                v_and = not_any(g0485UU);
                                } 
                              if (v_and == CFALSE) g0484I =CFALSE; 
                              else g0484I = CTRUE;} 
                            } 
                          } 
                        
                        if (g0484I == CTRUE) g0471= (g0471+exp2_integer((g0469+3)));
                          else { ClaireBoolean * g0486I;
                          { ClaireBoolean *v_and;
                            { v_and = ((boolean_I_any(Kernel.ctrue) != CTRUE) ? CTRUE : CFALSE);
                              if (v_and == CFALSE) g0486I =CFALSE; 
                              else { { int  g0474 = ((j/7)+1);
                                  int  g0475 = (4*mod_integer((j-1),7));
                                  v_and = nth_integer((*(d->statusBitVectorList))[g0474],(g0475+1));
                                  } 
                                if (v_and == CFALSE) g0486I =CFALSE; 
                                else g0486I = CTRUE;} 
                              } 
                            } 
                          
                          if (g0486I == CTRUE) g0471= (g0471-exp2_integer((g0469+3)));
                            } 
                        } 
                      STOREI((*d->statusBitVectorList)[g0468],g0471);
                      ;} 
                    ((*(d->needToAwake))[j]=Kernel.ctrue);
                    } 
                  else ;} 
                } 
              } 
          } 
        else ;} 
      } 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnConstraint(c:PalmLargeDisjunction,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmLargeDisjunction_palm(PalmLargeDisjunction *c,int i,int way,int newValue,int oldValue)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0487 = c->nbConst;
          { OID gc_local;
            while ((idx <= g0487))
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
      (*palm.updateDataStructuresOnConstraint)((*(c->lconst))[idx],
        reali,
        way,
        newValue,
        oldValue);
      { ClaireBoolean * g0490I;
        { OID  g0491UU;
          { int  g0488 = ((idx/7)+1);
            int  g0489 = (4*mod_integer((idx-1),7));
            g0491UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0488],(g0489+2)));
            } 
          g0490I = not_any(g0491UU);
          } 
        
        if (g0490I == CTRUE) palm_checkStatusAndReport_PalmLargeDisjunction(c,idx);
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(d:PalmLargeDisjunction,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmLargeDisjunction_palm(PalmLargeDisjunction *d,int i,int way,int newValue,int oldValue)
{ GC_BIND;
  { OID  idxtest;
    { { OID  idx_some = CNULL;
        { int  idx = 1;
          int  g0492 = d->nbConst;
          { OID gc_local;
            while ((idx <= g0492))
            { // HOHO, GC_LOOP not needed !
              if (i <= (*(d->loffset))[idx])
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
        (i-((*(d->loffset))[(idx-1)])) );
      (*palm.updateDataStructuresOnRestoreConstraint)((*(d->lconst))[idx],
        reali,
        way,
        newValue,
        oldValue);
      { ClaireBoolean * g0497I;
        { int  g0493 = ((idx/7)+1);
          int  g0494 = (4*mod_integer((idx-1),7));
          g0497I = nth_integer((*(d->statusBitVectorList))[g0493],g0494);
          } 
        
        if (g0497I == CTRUE) { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[idx]))));
            if (btest != CNULL)
             { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
              { ClaireBoolean * g0498I;
                { OID  g0499UU;
                  { int  g0495 = ((idx/7)+1);
                    int  g0496 = (4*mod_integer((idx-1),7));
                    g0499UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0495],(g0496+1)));
                    } 
                  g0498I = ((_oid_(b) != g0499UU) ? CTRUE : CFALSE);
                  } 
                
                if (g0498I == CTRUE) palm_takeIntoAccountStatusChange_PalmLargeDisjunction(d,idx);
                  } 
              } 
            else palm_takeIntoAccountStatusChange_PalmLargeDisjunction(d,idx);
              } 
          } 
      } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: checkConstraintState(d:PalmLargeDisjunction) [] */
ClaireBoolean * palm_checkConstraintState_PalmLargeDisjunction(PalmLargeDisjunction *d)
{ if (d->isContradictory == CTRUE) 
  { { ClaireBoolean *Result ;
      { { Explanation * e;
          { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
              e = _CL_obj;
              } 
            GC_OBJECT(Explanation,e);} 
          palm_self_explain_AbstractConstraint(d,e);
          { int  k = 1;
            int  g0500 = d->nbConst;
            { OID gc_local;
              while ((k <= g0500))
              { GC_LOOP;
                { OID gc_local;
                  ITERATE(ct);
                  bag *ct_support;
                  ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[k]))))));
                  for (START(ct_support); NEXT(ct);)
                  { GC_LOOP;
                    GC_OBJECT(set,e->explanation)->addFast(ct);
                    GC_UNLOOP;} 
                  } 
                ++k;
                GC_UNLOOP;} 
              } 
            } 
          palm_raisePalmFakeContradiction_PalmEngine(GC_OBJECT(PalmEngine,((PalmEngine *) choco_getProblem_AbstractConstraint(d)->propagationEngine)),e);
          } 
        Result = CFALSE;
        } 
      return (Result);} 
     } 
  else{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
    { ClaireBoolean *Result ;
      { ClaireBoolean * g0504I;
        { OID  g0505UU;
          { int  k = 1;
            int  g0501 = d->nbConst;
            { OID gc_local;
              g0505UU= _oid_(CFALSE);
              while ((k <= g0501))
              { // HOHO, GC_LOOP not needed !
                if ((OBJECT(ClaireBoolean,(*(d->needToAwake))[k])) == CTRUE)
                 { g0505UU = Kernel.ctrue;
                  break;} 
                ++k;
                } 
              } 
            } 
          g0504I = boolean_I_any(g0505UU);
          } 
        
        if (g0504I == CTRUE) { { int  k = 1;
              int  g0502 = d->nbConst;
              { OID gc_local;
                while ((k <= g0502))
                { GC_LOOP;
                  if ((OBJECT(ClaireBoolean,(*(d->needToAwake))[k])) == CTRUE)
                   { ((*(d->needToAwake))[k]=Kernel.cfalse);
                    { Explanation * e;
                      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                          e = _CL_obj;
                          } 
                        GC_OBJECT(Explanation,e);} 
                      palm_self_explain_AbstractConstraint(d,e);
                      { int  id = 1;
                        int  g0503 = d->nbConst;
                        { OID gc_local;
                          while ((id <= g0503))
                          { GC_LOOP;
                            if (id != k)
                             { OID gc_local;
                              ITERATE(ct);
                              bag *ct_support;
                              ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[id]))))));
                              for (START(ct_support); NEXT(ct);)
                              { GC_LOOP;
                                GC_OBJECT(set,e->explanation)->addFast(ct);
                                GC_UNLOOP;} 
                              } 
                            ++id;
                            GC_UNLOOP;} 
                          } 
                        } 
                      palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(d->lconst))[k]),e);
                      palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(d->lconst))[k]),e);
                      (*choco.awake)((*(d->lconst))[k]);
                      } 
                    } 
                  ++k;
                  GC_UNLOOP;} 
                } 
              } 
            Result = CFALSE;
            } 
          else Result = CTRUE;
        } 
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: choco/awakeOnInf(c:PalmLargeDisjunction,i:integer) [] */
void  choco_awakeOnInf_PalmLargeDisjunction(PalmLargeDisjunction *c,int i)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0506 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0506))
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
        { ClaireBoolean * g0511I;
          { int  g0507 = ((idx/7)+1);
            int  g0508 = (4*mod_integer((idx-1),7));
            g0511I = nth_integer((*(c->statusBitVectorList))[g0507],g0508);
            } 
          
          if (g0511I == CTRUE) { ClaireBoolean * g0512I;
              { int  g0509 = ((idx/7)+1);
                int  g0510 = (4*mod_integer((idx-1),7));
                g0512I = nth_integer((*(c->statusBitVectorList))[g0509],(g0510+2));
                } 
              
              if (g0512I == CTRUE) { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(c:PalmLargeDisjunction,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmLargeDisjunction(PalmLargeDisjunction *c,int i)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0513 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0513))
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
        { ClaireBoolean * g0518I;
          { int  g0514 = ((idx/7)+1);
            int  g0515 = (4*mod_integer((idx-1),7));
            g0518I = nth_integer((*(c->statusBitVectorList))[g0514],g0515);
            } 
          
          if (g0518I == CTRUE) { ClaireBoolean * g0519I;
              { int  g0516 = ((idx/7)+1);
                int  g0517 = (4*mod_integer((idx-1),7));
                g0519I = nth_integer((*(c->statusBitVectorList))[g0516],(g0517+2));
                } 
              
              if (g0519I == CTRUE) { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(c:PalmLargeDisjunction,i:integer) [] */
void  choco_awakeOnSup_PalmLargeDisjunction(PalmLargeDisjunction *c,int i)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0520 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0520))
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
        { ClaireBoolean * g0525I;
          { int  g0521 = ((idx/7)+1);
            int  g0522 = (4*mod_integer((idx-1),7));
            g0525I = nth_integer((*(c->statusBitVectorList))[g0521],g0522);
            } 
          
          if (g0525I == CTRUE) { ClaireBoolean * g0526I;
              { int  g0523 = ((idx/7)+1);
                int  g0524 = (4*mod_integer((idx-1),7));
                g0526I = nth_integer((*(c->statusBitVectorList))[g0523],(g0524+2));
                } 
              
              if (g0526I == CTRUE) { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmLargeDisjunction,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmLargeDisjunction(PalmLargeDisjunction *c,int i)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0527 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0527))
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
        { ClaireBoolean * g0532I;
          { int  g0528 = ((idx/7)+1);
            int  g0529 = (4*mod_integer((idx-1),7));
            g0532I = nth_integer((*(c->statusBitVectorList))[g0528],g0529);
            } 
          
          if (g0532I == CTRUE) { ClaireBoolean * g0533I;
              { int  g0530 = ((idx/7)+1);
                int  g0531 = (4*mod_integer((idx-1),7));
                g0533I = nth_integer((*(c->statusBitVectorList))[g0530],(g0531+2));
                } 
              
              if (g0533I == CTRUE) { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(c:PalmLargeDisjunction,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmLargeDisjunction(PalmLargeDisjunction *c,int i,int v)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0534 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0534))
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
        { ClaireBoolean * g0539I;
          { int  g0535 = ((idx/7)+1);
            int  g0536 = (4*mod_integer((idx-1),7));
            g0539I = nth_integer((*(c->statusBitVectorList))[g0535],g0536);
            } 
          
          if (g0539I == CTRUE) { ClaireBoolean * g0540I;
              { int  g0537 = ((idx/7)+1);
                int  g0538 = (4*mod_integer((idx-1),7));
                g0540I = nth_integer((*(c->statusBitVectorList))[g0537],(g0538+2));
                } 
              
              if (g0540I == CTRUE) { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(c:PalmLargeDisjunction,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmLargeDisjunction(PalmLargeDisjunction *c,int i,int v)
{ if (palm_checkConstraintState_PalmLargeDisjunction(c) == CTRUE) 
  { { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0541 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0541))
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
        { ClaireBoolean * g0546I;
          { int  g0542 = ((idx/7)+1);
            int  g0543 = (4*mod_integer((idx-1),7));
            g0546I = nth_integer((*(c->statusBitVectorList))[g0542],g0543);
            } 
          
          if (g0546I == CTRUE) { ClaireBoolean * g0547I;
              { int  g0544 = ((idx/7)+1);
                int  g0545 = (4*mod_integer((idx-1),7));
                g0547I = nth_integer((*(c->statusBitVectorList))[g0544],(g0545+2));
                } 
              
              if (g0547I == CTRUE) { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/askIfEntailed(c:PalmLargeDisjunction) [] */
OID  choco_askIfEntailed_PalmLargeDisjunction(PalmLargeDisjunction *c)
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
            { { ClaireBoolean * g0552I;
                { int  g0548 = ((i/7)+1);
                  int  g0549 = (4*mod_integer((i-1),7));
                  g0552I = nth_integer((*(c->statusBitVectorList))[g0548],g0549);
                  } 
                
                if (g0552I == CTRUE) { int  g0550 = ((i/7)+1);
                    int  g0551 = (4*mod_integer((i-1),7));
                    ithStatus = _oid_(nth_integer((*(c->statusBitVectorList))[g0550],(g0551+1)));
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


/* The c++ function for: choco/testIfSatisfied(c:PalmLargeDisjunction) [] */
ClaireBoolean * choco_testIfSatisfied_PalmLargeDisjunction(PalmLargeDisjunction *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { OID  g0553UU;
      { OID gc_local;
        ITERATE(subc);
        g0553UU= _oid_(CFALSE);
        for (START(c->lconst); NEXT(subc);)
        { GC_LOOP;
          if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) OBJECT(ClaireObject,subc)))))) == CTRUE)
           { g0553UU = Kernel.ctrue;
            break;} 
          GC_UNLOOP;} 
        } 
      Result = boolean_I_any(g0553UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 wrong interface name (testIfSatisfied)
/* The c++ function for: choco/propagate(d:PalmLargeDisjunction) [] */
void  choco_propagate_PalmLargeDisjunction(PalmLargeDisjunction *d)
{ { int  i = 1;
    int  g0554 = d->nbConst;
    { OID gc_local;
      while ((i <= g0554))
      { // HOHO, GC_LOOP not needed !
        palm_checkStatusAndReport_PalmLargeDisjunction(d,i);
        ++i;
        } 
      } 
    } 
  palm_checkConstraintState_PalmLargeDisjunction(d);
  } 


/* The c++ function for: choco/awake(d:PalmLargeDisjunction) [] */
void  choco_awake_PalmLargeDisjunction_palm(PalmLargeDisjunction *d)
{ { int  i = 1;
    int  g0555 = d->nbConst;
    { OID gc_local;
      while ((i <= g0555))
      { // HOHO, GC_LOOP not needed !
        palm_checkStatusAndReport_PalmLargeDisjunction(d,i);
        ++i;
        } 
      } 
    } 
  palm_checkConstraintState_PalmLargeDisjunction(d);
  ;} 


/* The c++ function for: whyIsTrue(c:PalmLargeDisjunction) [] */
set * palm_whyIsTrue_PalmLargeDisjunction(PalmLargeDisjunction *c)
{ GC_BIND;
  { set *Result ;
    { OID  cttest;
      { { OID  ctx_some = CNULL;
          { OID gc_local;
            ITERATE(ctx);
            for (START(c->lconst); NEXT(ctx);)
            { GC_LOOP;
              if (choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,ctx))) == Kernel.ctrue)
               { ctx_some= ctx;
                break;} 
              GC_UNLOOP;} 
            } 
          cttest = ctx_some;
          } 
        GC_OID(cttest);} 
      if (cttest != CNULL)
       { AbstractConstraint * ct = OBJECT(AbstractConstraint,cttest);
        Result = OBJECT(set,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) ct))));
        } 
      else { close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - odd state for whyIsTrue for PalmLargeDisjunction"),
            _oid_(Kernel.nil))));
          Result = set::empty(choco._AbstractConstraint);
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: whyIsFalse(c:PalmLargeDisjunction) [] */
set * palm_whyIsFalse_PalmLargeDisjunction(PalmLargeDisjunction *c)
{ GC_BIND;
  ;{ set *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      { OID gc_local;
        ITERATE(ct);
        for (START(c->lconst); NEXT(ct);)
        { GC_LOOP;
          { OID gc_local;
            ITERATE(cc);
            bag *cc_support;
            cc_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,ct))))));
            for (START(cc_support); NEXT(cc);)
            { GC_LOOP;
              e->explanation->addFast(cc);
              GC_UNLOOP;} 
            } 
          GC_UNLOOP;} 
        } 
      Result = e->explanation;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/awakeOnInst(c:PalmLargeDisjunction,i:integer) [] */
void  choco_awakeOnInst_PalmLargeDisjunction(PalmLargeDisjunction *c,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("PaLM: p-bool.cl - awakeOnInst should not be called for ~S within PaLM !!! (file: p-bool.cl)"),
    _oid_(list::alloc(1,_oid_(c->isa))))));
  } 


/* The c++ function for: checkPalm(ct:PalmLargeDisjunction) [] */
ClaireBoolean * palm_checkPalm_PalmLargeDisjunction(PalmLargeDisjunction *ct)
{ { ClaireBoolean *Result ;
    { OID  g0557UU;
      { int  i = 1;
        int  g0556 = ct->nbConst;
        { OID gc_local;
          g0557UU= _oid_(CFALSE);
          while ((i <= g0556))
          { // HOHO, GC_LOOP not needed !
            if (not_any((*palm.checkPalm)((*(ct->lconst))[i])) == CTRUE)
             { g0557UU = Kernel.ctrue;
              break;} 
            ++i;
            } 
          } 
        } 
      Result = not_any(g0557UU);
      } 
    return (Result);} 
  } 


// v0.34
// claire3 port register no longer used
// ********************************************************************
// *   Part 7: Cardinality constraint                                 *
// ********************************************************************
/* The c++ function for: initHook(c:PalmCardinality) [] */
void  palm_initHook_PalmCardinality(PalmCardinality *c)
{ GC_BIND;
  { AbstractConstraint * g0559; 
    OID  g0560;
    g0559 = c;
    { PalmInfoConstraint * _CL_obj = ((PalmInfoConstraint *) GC_OBJECT(PalmInfoConstraint,new_object_class(palm._PalmInfoConstraint)));
      g0560 = _oid_(_CL_obj);
      } 
    (g0559->hook = g0560);} 
  { int  i = 1;
    int  g0558 = c->nbConst;
    { OID gc_local;
      while ((i <= g0558))
      { GC_LOOP;
        { OID  g0561UU;
          { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
            (_CL_obj->constraint = c);
            (_CL_obj->index = i);
            g0561UU = _oid_(_CL_obj);
            } 
          add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,(*(c->lconst))[i])->hook)),g0561UU);
          } 
        { OID  g0562UU;
          { PalmControlConstraint * _CL_obj = ((PalmControlConstraint *) GC_OBJECT(PalmControlConstraint,new_object_class(palm._PalmControlConstraint)));
            (_CL_obj->constraint = c);
            (_CL_obj->index = (i+c->nbConst));
            g0562UU = _oid_(_CL_obj);
            } 
          add_property(palm.controllingConstraints,GC_OBJECT(ClaireObject,OBJECT(ClaireObject,OBJECT(AbstractConstraint,(*(c->loppositeConst))[i])->hook)),g0562UU);
          } 
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


/* The c++ function for: needToAwake(c:PalmCardinality,i:integer) [] */
ClaireBoolean * palm_needToAwake_PalmCardinality2(PalmCardinality *c,int i)
{ return (OBJECT(ClaireBoolean,(*(c->needToAwake))[i]));} 


/* The c++ function for: setNeedToAwake(c:PalmCardinality,i:integer,val:boolean) [] */
void  palm_setNeedToAwake_PalmCardinality(PalmCardinality *c,int i,ClaireBoolean *val)
{ ((*(c->needToAwake))[i]=_oid_(val));
  } 


/* The c++ function for: self_print(c:PalmCardinality) [] */
OID  claire_self_print_PalmCardinality_palm(PalmCardinality *c)
{ GC_BIND;
  princ_string("e-#");
  print_any(GC_OID(_oid_(c->lconst)));
  princ_string(" ");
  princ_string((((boolean_I_any(_oid_(choco.constrainOnInfNumber)) == CTRUE) && 
      (boolean_I_any(_oid_(choco.constrainOnSupNumber)) == CTRUE)) ?
    "=" :
    ((boolean_I_any(_oid_(choco.constrainOnInfNumber)) == CTRUE) ?
      ">=" :
      "<=" ) ));
  princ_string(" ");
  print_any((*(c->additionalVars))[1]);
  { OID Result = 0;
    princ_string("");
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: takeIntoAccountStatusChange(c:PalmCardinality,idx:integer) [] */
void  palm_takeIntoAccountStatusChange_PalmCardinality(PalmCardinality *c,int idx)
{ { int  k = 1;
    int  g0563 = c->nbConst;
    { OID gc_local;
      while ((k <= g0563))
      { // HOHO, GC_LOOP not needed !
        ((*(c->needToAwake))[k]=Kernel.cfalse);
        palm_removeIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]));
        palm_setDirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]));
        ((*(c->needToAwake))[(k+c->nbConst)]=Kernel.cfalse);
        palm_removeIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->loppositeConst))[k]));
        palm_setDirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->loppositeConst))[k]));
        ++k;
        } 
      } 
    } 
  { int  k = 1;
    int  g0564 = c->statusBitVectorList->length;
    { OID gc_local;
      while ((k <= g0564))
      { // HOHO, GC_LOOP not needed !
        ((*(c->statusBitVectorList))[k]=0);
        ++k;
        } 
      } 
    } 
  (c->needToAwakeTrue = CTRUE);
  (c->needToAwakeFalse = CTRUE);
  STOREI(c->nbTrueStatus,0);
  STOREI(c->nbFalseStatus,0);
  } 


// back-propagates the upper bound of the counter variable on the status fo the subconstraints.
/* The c++ function for: checkOnNbTrue(c:PalmCardinality) [] */
void  palm_checkOnNbTrue_PalmCardinality(PalmCardinality *c)
{ { IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
    (c->needToAwakeTrue = CTRUE);
    if ((c->nbTrueStatus == nbVar->sup->latestValue) && 
        (c->constrainOnSupNumber == CTRUE))
     { int  j = 1;
      int  g0565 = c->nbConst;
      { while ((j <= g0565))
        { { ClaireBoolean * g0568I;
            { OID  g0569UU;
              { int  g0566 = ((j/7)+1);
                int  g0567 = (4*mod_integer((j-1),7));
                g0569UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0566],g0567));
                } 
              g0568I = not_any(g0569UU);
              } 
            
            if (g0568I == CTRUE) ((*(c->needToAwake))[(c->nbConst+j)]=Kernel.ctrue);
              } 
          ++j;
          } 
        } 
      } 
    } 
  } 


// back-propagates the lower bound of the counter variable on the status fo the subconstraints.
/* The c++ function for: checkOnNbFalse(c:PalmCardinality) [] */
void  palm_checkOnNbFalse_PalmCardinality(PalmCardinality *c)
{ { IntVar * nbVar = OBJECT(IntVar,(*(c->additionalVars))[1]);
    (c->needToAwakeFalse = CTRUE);
    if (((c->nbConst-c->nbFalseStatus) == nbVar->inf->latestValue) && 
        (c->constrainOnInfNumber == CTRUE))
     { int  j = 1;
      int  g0570 = c->nbConst;
      { while ((j <= g0570))
        { { ClaireBoolean * g0573I;
            { OID  g0574UU;
              { int  g0571 = ((j/7)+1);
                int  g0572 = (4*mod_integer((j-1),7));
                g0574UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0571],g0572));
                } 
              g0573I = not_any(g0574UU);
              } 
            
            if (g0573I == CTRUE) ((*(c->needToAwake))[j]=Kernel.ctrue);
              } 
          ++j;
          } 
        } 
      } 
    } 
  } 


/* The c++ function for: checkStatusAndReport(c:PalmCardinality,i:integer) [] */
void  palm_checkStatusAndReport_PalmCardinality(PalmCardinality *c,int i)
{ GC_BIND;
  ;;{ OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[i]))));
    if (btest != CNULL)
     { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
      { int  g0575 = ((i/7)+1);
        int  g0576 = (4*mod_integer((i-1),7));
        list * g0577 = GC_OBJECT(list,c->statusBitVectorList);
        int  g0578 = (*(g0577))[g0575];
        g0578= (g0578+exp2_integer(g0576));
        { ClaireBoolean * g0583I;
          { ClaireBoolean *v_and;
            { v_and = b;
              if (v_and == CFALSE) g0583I =CFALSE; 
              else { { OID  g0584UU;
                  { int  g0579 = ((i/7)+1);
                    int  g0580 = (4*mod_integer((i-1),7));
                    g0584UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0579],(g0580+1)));
                    } 
                  v_and = not_any(g0584UU);
                  } 
                if (v_and == CFALSE) g0583I =CFALSE; 
                else g0583I = CTRUE;} 
              } 
            } 
          
          if (g0583I == CTRUE) g0578= (g0578+exp2_integer((g0576+1)));
            else { ClaireBoolean * g0585I;
            { ClaireBoolean *v_and;
              { v_and = not_any(_oid_(b));
                if (v_and == CFALSE) g0585I =CFALSE; 
                else { { int  g0581 = ((i/7)+1);
                    int  g0582 = (4*mod_integer((i-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g0581],(g0582+1));
                    } 
                  if (v_and == CFALSE) g0585I =CFALSE; 
                  else g0585I = CTRUE;} 
                } 
              } 
            
            if (g0585I == CTRUE) g0578= (g0578-exp2_integer((g0576+1)));
              } 
          } 
        STOREI((*c->statusBitVectorList)[g0575],g0578);
        ;} 
      if (b == CTRUE)
       { STOREI(c->nbTrueStatus,(c->nbTrueStatus+1));
        palm_checkOnNbTrue_PalmCardinality(c);
        } 
      else { STOREI(c->nbFalseStatus,(c->nbFalseStatus+1));
          palm_checkOnNbFalse_PalmCardinality(c);
          } 
        } 
    else ;} 
  GC_UNBIND;} 


/* The c++ function for: explainTrueConstraints(c:PalmCardinality) [] */
Explanation * palm_explainTrueConstraints_PalmCardinality(PalmCardinality *c)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      { int  ict = 1;
        int  g0586 = c->nbConst;
        { OID gc_local;
          while ((ict <= g0586))
          { GC_LOOP;
            { ClaireBoolean * g0591I;
              { ClaireBoolean *v_and;
                { { int  g0587 = ((ict/7)+1);
                    int  g0588 = (4*mod_integer((ict-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g0587],g0588);
                    } 
                  if (v_and == CFALSE) g0591I =CFALSE; 
                  else { { int  g0589 = ((ict/7)+1);
                      int  g0590 = (4*mod_integer((ict-1),7));
                      v_and = nth_integer((*(c->statusBitVectorList))[g0589],(g0590+1));
                      } 
                    if (v_and == CFALSE) g0591I =CFALSE; 
                    else g0591I = CTRUE;} 
                  } 
                } 
              
              if (g0591I == CTRUE) { OID gc_local;
                  ITERATE(ct);
                  bag *ct_support;
                  ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsTrue->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[ict]))))));
                  for (START(ct_support); NEXT(ct);)
                  { GC_LOOP;
                    GC_OBJECT(set,e->explanation)->addFast(ct);
                    GC_UNLOOP;} 
                  } 
                } 
            ++ict;
            GC_UNLOOP;} 
          } 
        } 
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: explainFalseConstraints(c:PalmCardinality) [] */
Explanation * palm_explainFalseConstraints_PalmCardinality(PalmCardinality *c)
{ GC_BIND;
  { Explanation *Result ;
    { Explanation * e;
      { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
          e = _CL_obj;
          } 
        GC_OBJECT(Explanation,e);} 
      palm_self_explain_AbstractConstraint(c,e);
      { int  ict = 1;
        int  g0592 = c->nbConst;
        { OID gc_local;
          while ((ict <= g0592))
          { GC_LOOP;
            { ClaireBoolean * g0597I;
              { ClaireBoolean *v_and;
                { { int  g0593 = ((ict/7)+1);
                    int  g0594 = (4*mod_integer((ict-1),7));
                    v_and = nth_integer((*(c->statusBitVectorList))[g0593],g0594);
                    } 
                  if (v_and == CFALSE) g0597I =CFALSE; 
                  else { { OID  g0598UU;
                      { int  g0595 = ((ict/7)+1);
                        int  g0596 = (4*mod_integer((ict-1),7));
                        g0598UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0595],(g0596+1)));
                        } 
                      v_and = not_any(g0598UU);
                      } 
                    if (v_and == CFALSE) g0597I =CFALSE; 
                    else g0597I = CTRUE;} 
                  } 
                } 
              
              if (g0597I == CTRUE) { OID gc_local;
                  ITERATE(ct);
                  bag *ct_support;
                  ct_support = GC_OBJECT(set,OBJECT(bag,_oid_((ClaireObject *) palm.whyIsFalse->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[ict]))))));
                  for (START(ct_support); NEXT(ct);)
                  { GC_LOOP;
                    GC_OBJECT(set,e->explanation)->addFast(ct);
                    GC_UNLOOP;} 
                  } 
                } 
            ++ict;
            GC_UNLOOP;} 
          } 
        } 
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkConstraintState(c:PalmCardinality) [] */
ClaireBoolean * palm_checkConstraintState_PalmCardinality(PalmCardinality *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  if (c->needToAwakeTrue == CTRUE)
   (*choco.updateInf)((*(c->additionalVars))[1],
    c->nbTrueStatus,
    0,
    GC_OID(_oid_(palm_explainTrueConstraints_PalmCardinality(c))));
  if (c->needToAwakeFalse == CTRUE)
   (*choco.updateSup)((*(c->additionalVars))[1],
    (c->nbConst-c->nbFalseStatus),
    0,
    GC_OID(_oid_(palm_explainFalseConstraints_PalmCardinality(c))));
  { ClaireBoolean *Result ;
    { ClaireBoolean * g0601I;
      { OID  g0602UU;
        { int  k = 1;
          int  g0599 = (2*c->nbConst);
          { OID gc_local;
            g0602UU= _oid_(CFALSE);
            while ((k <= g0599))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,(*(c->needToAwake))[k])) == CTRUE)
               { g0602UU = Kernel.ctrue;
                break;} 
              ++k;
              } 
            } 
          } 
        g0601I = boolean_I_any(g0602UU);
        } 
      
      if (g0601I == CTRUE) { { int  k = 1;
            int  g0600 = c->nbConst;
            { OID gc_local;
              while ((k <= g0600))
              { GC_LOOP;
                if ((OBJECT(ClaireBoolean,(*(c->needToAwake))[k])) == CTRUE)
                 { ((*(c->needToAwake))[k]=Kernel.cfalse);
                  { Explanation * e;
                    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                        e = _CL_obj;
                        } 
                      GC_OBJECT(Explanation,e);} 
                    palm_self_explain_AbstractConstraint(c,e);
                    palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]),e);
                    palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->lconst))[k]),e);
                    (*choco.awake)((*(c->lconst))[k]);
                    } 
                  } 
                if ((OBJECT(ClaireBoolean,(*(c->needToAwake))[(c->nbConst+k)])) == CTRUE)
                 { ((*(c->needToAwake))[(c->nbConst+k)]=Kernel.cfalse);
                  { Explanation * e;
                    { { Explanation * _CL_obj = ((Explanation *) GC_OBJECT(Explanation,new_object_class(palm._Explanation)));
                        e = _CL_obj;
                        } 
                      GC_OBJECT(Explanation,e);} 
                    palm_self_explain_AbstractConstraint(c,e);
                    palm_setIndirect_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->loppositeConst))[k]),e);
                    palm_setIndirectDependance_AbstractConstraint(OBJECT(AbstractConstraint,(*(c->loppositeConst))[k]),e);
                    (*choco.awake)((*(c->loppositeConst))[k]);
                    } 
                  } 
                ++k;
                GC_UNLOOP;} 
              } 
            } 
          Result = CFALSE;
          } 
        else Result = CTRUE;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: updateDataStructuresOnConstraint(c:PalmCardinality,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnConstraint_PalmCardinality_palm(PalmCardinality *c,int i,int way,int newValue,int oldValue)
{ if (i == choco_getNbVars_LargeCompositeConstraint(c)) 
  { if (way == 1)
     palm_checkOnNbFalse_PalmCardinality(c);
    else if (way == 2)
     palm_checkOnNbTrue_PalmCardinality(c);
    else { palm_checkOnNbFalse_PalmCardinality(c);
        palm_checkOnNbTrue_PalmCardinality(c);
        } 
       } 
  else{ GC_BIND;
    { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0603 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0603))
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
        (*palm.updateDataStructuresOnConstraint)((*(c->lconst))[idx],
          reali,
          way,
          newValue,
          oldValue);
        { ClaireBoolean * g0608I;
          { ClaireBoolean *v_and;
            { { OID  g0609UU;
                { int  g0604 = ((idx/7)+1);
                  int  g0605 = (4*mod_integer((idx-1),7));
                  g0609UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0604],g0605));
                  } 
                v_and = not_any(g0609UU);
                } 
              if (v_and == CFALSE) g0608I =CFALSE; 
              else { { OID  g0610UU;
                  { int  g0606 = ((idx/7)+1);
                    int  g0607 = (4*mod_integer((idx-1),7));
                    g0610UU = _oid_(nth_integer((*(c->statusBitVectorList))[g0606],(g0607+2)));
                    } 
                  v_and = not_any(g0610UU);
                  } 
                if (v_and == CFALSE) g0608I =CFALSE; 
                else g0608I = CTRUE;} 
              } 
            } 
          
          if (g0608I == CTRUE) palm_checkStatusAndReport_PalmCardinality(c,idx);
            } 
        } 
      else ;} 
    GC_UNBIND;} 
  } 


/* The c++ function for: updateDataStructuresOnRestoreConstraint(d:PalmCardinality,i:integer,way:{1, 2, 3, 4},newValue:integer,oldValue:integer) [] */
void  palm_updateDataStructuresOnRestoreConstraint_PalmCardinality_palm(PalmCardinality *d,int i,int way,int newValue,int oldValue)
{ if (i == choco_getNbVars_LargeCompositeConstraint(d)) 
  { { (d->needToAwakeFalse = CTRUE);
      (d->needToAwakeTrue = CTRUE);
      } 
     } 
  else{ GC_BIND;
    { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0611 = d->nbConst;
            { OID gc_local;
              while ((idx <= g0611))
              { // HOHO, GC_LOOP not needed !
                if (i <= (*(d->loffset))[idx])
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
          (i-((*(d->loffset))[(idx-1)])) );
        (*palm.updateDataStructuresOnRestoreConstraint)((*(d->lconst))[idx],
          reali,
          way,
          newValue,
          oldValue);
        { ClaireBoolean * g0616I;
          { int  g0612 = ((idx/7)+1);
            int  g0613 = (4*mod_integer((idx-1),7));
            g0616I = nth_integer((*(d->statusBitVectorList))[g0612],g0613);
            } 
          
          if (g0616I == CTRUE) { OID  btest = GC_OID(choco.askIfEntailed->fcall(((int) OBJECT(ClaireObject,(*(d->lconst))[idx]))));
              if (btest != CNULL)
               { ClaireBoolean * b = OBJECT(ClaireBoolean,btest);
                { ClaireBoolean * g0617I;
                  { OID  g0618UU;
                    { int  g0614 = ((idx/7)+1);
                      int  g0615 = (4*mod_integer((idx-1),7));
                      g0618UU = _oid_(nth_integer((*(d->statusBitVectorList))[g0614],(g0615+1)));
                      } 
                    g0617I = ((_oid_(b) != g0618UU) ? CTRUE : CFALSE);
                    } 
                  
                  if (g0617I == CTRUE) palm_takeIntoAccountStatusChange_PalmCardinality(d,idx);
                    } 
                } 
              else palm_takeIntoAccountStatusChange_PalmCardinality(d,idx);
                } 
            } 
        } 
      else ;} 
    GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnInf(c:PalmCardinality,i:integer) [] */
void  choco_awakeOnInf_PalmCardinality(PalmCardinality *c,int i)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0619 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0619))
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
        { ClaireBoolean * g0624I;
          { int  g0620 = ((idx/7)+1);
            int  g0621 = (4*mod_integer((idx-1),7));
            g0624I = nth_integer((*(c->statusBitVectorList))[g0620],g0621);
            } 
          
          if (g0624I == CTRUE) { ClaireBoolean * g0625I;
              { int  g0622 = ((idx/7)+1);
                int  g0623 = (4*mod_integer((idx-1),7));
                g0625I = nth_integer((*(c->statusBitVectorList))[g0622],(g0623+2));
                } 
              
              if (g0625I == CTRUE) { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreInf(c:PalmCardinality,i:integer) [] */
void  palm_awakeOnRestoreInf_PalmCardinality(PalmCardinality *c,int i)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0626 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0626))
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
        { ClaireBoolean * g0631I;
          { int  g0627 = ((idx/7)+1);
            int  g0628 = (4*mod_integer((idx-1),7));
            g0631I = nth_integer((*(c->statusBitVectorList))[g0627],g0628);
            } 
          
          if (g0631I == CTRUE) { ClaireBoolean * g0632I;
              { int  g0629 = ((idx/7)+1);
                int  g0630 = (4*mod_integer((idx-1),7));
                g0632I = nth_integer((*(c->statusBitVectorList))[g0629],(g0630+2));
                } 
              
              if (g0632I == CTRUE) { _void_(palm.awakeOnRestoreInf->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnSup(c:PalmCardinality,i:integer) [] */
void  choco_awakeOnSup_PalmCardinality(PalmCardinality *c,int i)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0633 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0633))
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
        { ClaireBoolean * g0638I;
          { int  g0634 = ((idx/7)+1);
            int  g0635 = (4*mod_integer((idx-1),7));
            g0638I = nth_integer((*(c->statusBitVectorList))[g0634],g0635);
            } 
          
          if (g0638I == CTRUE) { ClaireBoolean * g0639I;
              { int  g0636 = ((idx/7)+1);
                int  g0637 = (4*mod_integer((idx-1),7));
                g0639I = nth_integer((*(c->statusBitVectorList))[g0636],(g0637+2));
                } 
              
              if (g0639I == CTRUE) { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreSup(c:PalmCardinality,i:integer) [] */
void  palm_awakeOnRestoreSup_PalmCardinality(PalmCardinality *c,int i)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0640 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0640))
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
        { ClaireBoolean * g0645I;
          { int  g0641 = ((idx/7)+1);
            int  g0642 = (4*mod_integer((idx-1),7));
            g0645I = nth_integer((*(c->statusBitVectorList))[g0641],g0642);
            } 
          
          if (g0645I == CTRUE) { ClaireBoolean * g0646I;
              { int  g0643 = ((idx/7)+1);
                int  g0644 = (4*mod_integer((idx-1),7));
                g0646I = nth_integer((*(c->statusBitVectorList))[g0643],(g0644+2));
                } 
              
              if (g0646I == CTRUE) { _void_(palm.awakeOnRestoreSup->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: choco/awakeOnRem(c:PalmCardinality,i:integer,v:integer) [] */
void  choco_awakeOnRem_PalmCardinality(PalmCardinality *c,int i,int v)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0647 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0647))
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
        { ClaireBoolean * g0652I;
          { int  g0648 = ((idx/7)+1);
            int  g0649 = (4*mod_integer((idx-1),7));
            g0652I = nth_integer((*(c->statusBitVectorList))[g0648],g0649);
            } 
          
          if (g0652I == CTRUE) { ClaireBoolean * g0653I;
              { int  g0650 = ((idx/7)+1);
                int  g0651 = (4*mod_integer((idx-1),7));
                g0653I = nth_integer((*(c->statusBitVectorList))[g0650],(g0651+2));
                } 
              
              if (g0653I == CTRUE) { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRestoreVal(c:PalmCardinality,i:integer,v:integer) [] */
void  palm_awakeOnRestoreVal_PalmCardinality(PalmCardinality *c,int i,int v)
{ if (palm_checkConstraintState_PalmCardinality(c) == CTRUE) 
  { if (i < choco_getNbVars_LargeCompositeConstraint(c))
     { OID  idxtest;
      { { OID  idx_some = CNULL;
          { int  idx = 1;
            int  g0654 = c->nbConst;
            { OID gc_local;
              while ((idx <= g0654))
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
        { ClaireBoolean * g0659I;
          { int  g0655 = ((idx/7)+1);
            int  g0656 = (4*mod_integer((idx-1),7));
            g0659I = nth_integer((*(c->statusBitVectorList))[g0655],g0656);
            } 
          
          if (g0659I == CTRUE) { ClaireBoolean * g0660I;
              { int  g0657 = ((idx/7)+1);
                int  g0658 = (4*mod_integer((idx-1),7));
                g0660I = nth_integer((*(c->statusBitVectorList))[g0657],(g0658+2));
                } 
              
              if (g0660I == CTRUE) { _void_(palm.awakeOnRestoreVal->fcall(((int) OBJECT(ClaireObject,(*(c->lconst))[idx])),((int) reali),((int) v)));
                  } 
                } 
            } 
        } 
      else ;} 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


/* The c++ function for: checkPalm(ct:PalmCardinality) [] */
ClaireBoolean * palm_checkPalm_PalmCardinality(PalmCardinality *ct)
{ { ClaireBoolean *Result ;
    { OID  g0662UU;
      { int  i = 1;
        int  g0661 = ct->nbConst;
        { OID gc_local;
          g0662UU= _oid_(CFALSE);
          while ((i <= g0661))
          { // HOHO, GC_LOOP not needed !
            if (not_any((*palm.checkPalm)((*(ct->lconst))[i])) == CTRUE)
             { g0662UU = Kernel.ctrue;
              break;} 
            ++i;
            } 
          } 
        } 
      Result = not_any(g0662UU);
      } 
    return (Result);} 
  } 


// v0.34
// claire3 port register no longer used