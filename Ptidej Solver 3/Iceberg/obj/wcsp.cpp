/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Iceberg\v0.95\wcsp.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:08:22 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>
#include <ice.h>

// ********************************************************************
// * ICEBERG: global constraints for OCRE, version 1.0.1  11/04/2002  *
// *        requires CHOCO, v1.3.18                                   *
// * file: wcsp.cl                                                    *
// *    weighted constraint satisfaction problems                     *
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************
// verbosity levels for the WCSP fragment
// Constraint Description :
// =======================
// This constraint use a set of constraint C, a set of integer W and a domain variable T.
// It maintains an evaluation of T, the agregation of penalties for all violated constraints
// The operator of agregation can be additive
//      additiveWCSP( const:list[AbstractConstraint], penalties:list[integer], T:IntVar)
// or any other kind of operator. For example the Max operator for future implementations
//      maxWCSP( const:list[AbstractConstraint], penlaties:list[integer], T:IntVar)
// ------------------  File Overview  ---------------------------------
// *   Part 1: An abstract class for WCSPs as global constraints      *
// *   Part 2: Specific propagation algorithms for additive WCSPs     *
// *   Part 3: WCSP propagation as a reaction to Choco events         *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: An abstract class for WCSPs as global constraints      *
// ********************************************************************
// varIndices[constIdx]=indices of all vars involved in const
// accessing the variable encoding the total cost of the network (objective value of the weighted CSP)
/* The c++ function for: getTotalPenaltyVar(c:AbstractWCSP) [] */
IntVar * ice_getTotalPenaltyVar_AbstractWCSP(AbstractWCSP *c)
{ return (OBJECT(IntVar,last_list(c->vars)));} 


// accessing the ith constraint of variable
/* The c++ function for: getSubConst(c:AbstractWCSP,subcIdx:integer) [] */
AbstractConstraint * ice_getSubConst_AbstractWCSP(AbstractWCSP *c,int subcIdx)
{ ;return (OBJECT(AbstractConstraint,((OID *) c->subConst)[subcIdx]));} 


/* The c++ function for: getSubConstPenalty(c:AbstractWCSP,subcIdx:integer) [] */
int  ice_getSubConstPenalty_AbstractWCSP(AbstractWCSP *c,int subcIdx)
{ ;return (((OID *) c->penalties)[subcIdx]);} 


/* The c++ function for: getSubVar(c:AbstractWCSP,varIdx:integer) [] */
IntVar * ice_getSubVar_AbstractWCSP(AbstractWCSP *c,int varIdx)
{ ;return (OBJECT(IntVar,(*(c->vars))[varIdx]));} 


/* The c++ function for: getNbSubVars(c:AbstractWCSP) [] */
int  ice_getNbSubVars_AbstractWCSP(AbstractWCSP *c)
{ return (c->nbSubVars);} 


/* The c++ function for: getNbSubConst(c:AbstractWCSP) [] */
int  ice_getNbSubConst_AbstractWCSP(AbstractWCSP *c)
{ return (c->nbSubConst);} 


// handling the sub-constraint network
/* The c++ function for: getNbSubConstLinkedTo(c:AbstractWCSP,varIdx:integer) [] */
int  ice_getNbSubConstLinkedTo_AbstractWCSP(AbstractWCSP *c,int varIdx)
{ return (OBJECT(bag,(*(c->constIndices))[varIdx])->length);} 


/* The c++ function for: getSubConstIndex(c:AbstractWCSP,varIdx:integer,i:integer) [] */
int  ice_getSubConstIndex_AbstractWCSP(AbstractWCSP *c,int varIdx,int i)
{ ;return ((*(OBJECT(bag,(*(c->constIndices))[varIdx])))[i]);} 


/* The c++ function for: getIndex(c:AbstractWCSP,var:choco/IntVar) [] */
int  ice_getIndex_AbstractWCSP(AbstractWCSP *c,IntVar *var)
{ { int Result = 0;
    { OID  varidxtest;
      { OID  i_some = CNULL;
        { int  i = 1;
          int  g0000 = c->nbSubVars;
          { while ((i <= g0000))
            { { ClaireBoolean * g0001I;
                { OID  g0002UU;
                  { g0002UU = (*(c->vars))[i];
                    } 
                  g0001I = ((g0002UU == _oid_(var)) ? CTRUE : CFALSE);
                  } 
                
                if (g0001I == CTRUE) { i_some= i;
                    break;} 
                  } 
              ++i;
              } 
            } 
          } 
        varidxtest = i_some;
        } 
      if (varidxtest != CNULL)
       { int  varidx = varidxtest;
        Result = varidx;
        } 
      else close_exception(((general_error *) (*Core._general_error)(_string_("~S is not a variable of WCSP ~S\n"),
          _oid_(list::alloc(2,_oid_(var),_oid_(c))))));
        } 
    return (Result);} 
  } 


/* The c++ function for: addVar(c:AbstractWCSP,var:choco/IntVar) [] */
void  ice_addVar_AbstractWCSP(AbstractWCSP *c,IntVar *var)
{ GC_BIND;
  ;;;GC_OBJECT(list,c->vars)->addFast(_oid_(var));
  GC_OBJECT(list,c->constIndices)->addFast(_oid_(list::empty(Kernel._integer)));
  (c->nbSubVars = (c->nbSubVars+1));
  GC_UNBIND;} 


// there is only one variable in that constraint for which we haven't reacted
// to an instantiation. Note that this variable may in the mean time have been instantiated
// but its IC  hasn't been taken into account within the backward checking bound yet.
/* The c++ function for: getUnInstantiatedVarIdx(c:AbstractWCSP,subcIdx:integer) [] */
int  ice_getUnInstantiatedVarIdx_AbstractWCSP(AbstractWCSP *c,int subcIdx)
{ { int Result = 0;
    { OID  vitest;
      { OID  varIdx_some = CNULL;
        { ITERATE(varIdx);
          for (START(OBJECT(bag,(*(c->varIndices))[subcIdx])); NEXT(varIdx);)
          if (not_any(((OID *) CLREAD(AdditiveWCSP,c,reactedToInst))[varIdx]) == CTRUE)
           { varIdx_some= varIdx;
            break;} 
          } 
        vitest = varIdx_some;
        } 
      if (vitest != CNULL)
       { int  vi = vitest;
        Result = vi;
        } 
      else close_exception(((general_error *) (*Core._general_error)(_string_("all variables linked to subconstraint #~S are instantiated !"),
          _oid_(list::alloc(1,subcIdx)))));
        } 
    return (Result);} 
  } 


/* The c++ function for: initWCSPNetwork(c:AbstractWCSP,lc:list[choco/AbstractConstraint]) [] */
void  ice_initWCSPNetwork_AbstractWCSP(AbstractWCSP *c,list *lc)
{ GC_RESERVE(16);  // v3.0.55 optim !
  { int  nbc = lc->length;
    { AbstractWCSP * g0006; 
      OID * g0007;
      g0006 = c;
      { list * g0008UU;
        { { bag *v_list; OID v_val;
            OID c,CLcount;
            v_list = lc;
             g0008UU = v_list->clone(choco._AbstractConstraint);
            for (CLcount= 1; CLcount <= v_list->length; CLcount++)
            { c = (*(v_list))[CLcount];
              v_val = c;
              
              (*((list *) g0008UU))[CLcount] = v_val;} 
            } 
          GC_OBJECT(list,g0008UU);} 
        g0007 = array_I_list(g0008UU);
        } 
      (g0006->subConst = g0007);} 
    (c->nbSubConst = nbc);
    (c->varIndices = make_list_integer2(nbc,GC_OBJECT(ClaireType,nth_class2(Kernel._list,list::alloc(Kernel._any,1,_oid_(Kernel.of)),list::alloc(1,_oid_(set::alloc(1,_oid_(Kernel._integer)))))),_oid_(list::empty(Kernel._integer))));
    { int  subci = 1;
      int  g0003 = nbc;
      { OID gc_local;
        while ((subci <= g0003))
        { GC_LOOP;
          { AbstractConstraint * subc = OBJECT(AbstractConstraint,(*(lc))[subci]);
            int  nbv = choco.getNbVars->fcall(((int) subc));
            ((*(GC_OBJECT(list,c->varIndices)))[subci]=GC_OID(_oid_(make_list_integer2(nbv,Kernel._integer,0))));
            { int  vidx = 1;
              int  g0004 = nbv;
              { OID gc_local;
                while ((vidx <= g0004))
                { GC_LOOP;
                  { AbstractVar * v = GC_OBJECT(AbstractVar,OBJECT(AbstractVar,(*choco.getVar)(_oid_(subc),
                      vidx)));
                    OID  varIdxtest;
                    { { OID  i_some = CNULL;
                        { int  i = 1;
                          int  g0005 = c->nbSubVars;
                          { OID gc_local;
                            while ((i <= g0005))
                            { // HOHO, GC_LOOP not needed !
                              { ClaireBoolean * g0009I;
                                { OID  g0010UU;
                                  { g0010UU = (*(c->vars))[i];
                                    } 
                                  g0009I = ((g0010UU == _oid_(v)) ? CTRUE : CFALSE);
                                  } 
                                
                                if (g0009I == CTRUE) { i_some= i;
                                    break;} 
                                  } 
                              ++i;
                              } 
                            } 
                          } 
                        varIdxtest = i_some;
                        } 
                      GC_OID(varIdxtest);} 
                    if (varIdxtest != CNULL)
                     { int  varIdx = varIdxtest;
                      ((*(OBJECT(list,(*(c->varIndices))[subci])))[vidx]=varIdx);
                      ((*(c->constIndices))[varIdx]=_oid_(add_list(OBJECT(list,(*(c->constIndices))[varIdx]),subci)));
                      } 
                    else { ice_addVar_AbstractWCSP(c,((IntVar *) v));
                        ((*(OBJECT(list,(*(c->varIndices))[subci])))[vidx]=c->nbSubVars);
                        ((*(c->constIndices))[c->nbSubVars]=_oid_(add_list(OBJECT(list,(*(c->constIndices))[c->nbSubVars]),subci)));
                        } 
                      } 
                  ++vidx;
                  GC_UNLOOP;} 
                } 
              } 
            } 
          ++subci;
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 2: data structures propagation for additive WCSPs         *
// ********************************************************************
// This implementation is based on Lionel Lobjois' PhD thesis, page 50-56
// This constraint features the following internal data structures
//    MBC: a backward checking lowerbound
//    IC : a matric of inconsistency counts,such that
//         IC[x,v]=sum(penalty(c) | c in QA and (x=v) => (c is violated))
//    NONASS: the set of yet unAssigned variables
//    QA: set of quasi-instantiated constraints
//        this is not stored by iterated on the fly (see NBNI)
//    NBNI[c]: number of unAssigned variables in constraint c
//    VARPEN:  the minimum penalty over all possible values
//         VARPEN[x]=min(IC[x,val] | val in domain(x))
//    a lower bound
//         LB = MBC + sum(v in NONASS |VARPEN(v))
// Constraint semantics:
//    TotalPenalty >= LB                                 (*)
//    LB - VARPEN[x] + IC[x,v] > TotalPenalty => x != v  (**)
// Reaction to events:
//
// upon an instantiation  (x = v)
//      change(MBC) MBC :+ IC[x,v]
//      change(NBNI) NBI[c] :- 1 for all constraints c linked to x
//        if NBNI[c] = 1
//           let x2 be the remaining uninstantiated variable from c
//               add penalty(c) to IC[x2,val] for all impossible values val for x2
//      update VARPEN[x2] for all variables x2 whose IS have changed
//      change LB
//      propagate (*) and (**)
// upon any event removal (x <> v) / incinf (x >= v) / decsup (x <= v)
//      for all constraints c from QA such that x is the remaining uninstantiated var
//          recompute VARPEN[x]=min(IC[x,val] | val in domain(x))
//      change LB
//      propagate (*) and (**)
// Event handling strategy
//   Immediate reaction (called once per domain update):
//       maintain the invariants IC, QA, VARPEN, MBC
//   Delayed reaction (called once per constraint):
//       if the VARPEN/MBC/IC invariants have changed, repropagate (*) and (**)
/* The c++ function for: display(c:AdditiveWCSP) [] */
OID  ice_display_AdditiveWCSP(AdditiveWCSP *c)
{ tformat_string("show inconsistency counts\n",0,list::empty());
  { int  varIdx = 1;
    int  g0011 = c->nbSubVars;
    { OID gc_local;
      while ((varIdx <= g0011))
      { // HOHO, GC_LOOP not needed !
        { IntVar * var;
          { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
            } 
          princ_string("IC[");
          print_any(_oid_(var));
          princ_string("]: ");
          { int  minVal = choco.getInf->fcall(((int) var));
            int  maxVal = choco.getSup->fcall(((int) var));
            int  val = minVal;
            int  g0012 = maxVal;
            { OID gc_local;
              while ((val <= g0012))
              { // HOHO, GC_LOOP not needed !
                print_any(val);
                princ_string("[");
                { OID  g0014UU;
                  { int  g0013 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                    g0014UU = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0013];
                    } 
                  print_any(g0014UU);
                  } 
                princ_string("] ");
                ++val;
                } 
              } 
            } 
          princ_string("\n");
          } 
        ++varIdx;
        } 
      } 
    } 
  tformat_string("show penalties\n",0,list::empty());
  { OID Result = 0;
    if (ice.WVIEW->value <= ClEnv->verbose)
     Result = tformat_string("bounds bkwd:~S, fwd:~S \n",ice.WVIEW->value,list::alloc(2,c->backwardCheckingBound,c->forwardCheckingBound));
    else Result = Kernel.cfalse;
      return (Result);} 
  } 


//@api entry point
/* The c++ function for: choco/additiveWCSP(lc:list[choco/AbstractConstraint],lp:list[integer],X:choco/IntVar) [] */
AdditiveWCSP * choco_additiveWCSP_list(list *lc,list *lp,IntVar *X)
{ GC_BIND;
  ;{ AdditiveWCSP *Result ;
    { AdditiveWCSP * c;
      { { AdditiveWCSP * _CL_obj = ((AdditiveWCSP *) GC_OBJECT(AdditiveWCSP,new_object_class(ice._AdditiveWCSP)));
          { AbstractWCSP * g0015; 
            OID * g0016;
            g0015 = _CL_obj;
            { list * g0017UU;
              { { bag *v_list; OID v_val;
                  OID p,CLcount;
                  v_list = lp;
                   g0017UU = v_list->clone(Kernel._integer);
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { p = (*(v_list))[CLcount];
                    v_val = p;
                    
                    (*((list *) g0017UU))[CLcount] = v_val;} 
                  } 
                GC_OBJECT(list,g0017UU);} 
              g0016 = array_I_list(g0017UU);
              } 
            (g0015->penalties = g0016);} 
          c = _CL_obj;
          } 
        GC_OBJECT(AdditiveWCSP,c);} 
      ice_initWCSPNetwork_AbstractWCSP(c,lc);
      GC_OBJECT(list,c->vars)->addFast(_oid_(X));
      (c->domainOffsets = make_array_integer(c->nbSubVars,Kernel._integer,0));
      ice_makeInconsistencyCount_AdditiveWCSP(c);
      (c->nbUnInstantiatedVars = make_array_integer(c->nbSubConst,Kernel._integer,0));
      (c->leastICValue = make_array_integer(c->nbSubVars,Kernel._integer,1));
      (c->varPenalties = make_array_integer(c->nbSubVars,Kernel._integer,0));
      (c->reactedToInst = make_array_integer(c->nbSubVars,Kernel._boolean,Kernel.cfalse));
      STOREI(c->backwardCheckingBound,0);
      STOREI(c->forwardCheckingBound,0);
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// returns the number of subVars for which we havent reacted to an instantiation yet
/* The c++ function for: getNbUnInstantiatedVars(c:AdditiveWCSP,subcIdx:integer) [] */
int  ice_getNbUnInstantiatedVars_AdditiveWCSP(AdditiveWCSP *c,int subcIdx)
{ return (((OID *) c->nbUnInstantiatedVars)[subcIdx]);} 


// accessing the inconsistency count
/* The c++ function for: getInconsistencyCount(c:AdditiveWCSP,varIdx:integer,val:integer) [] */
int  ice_getInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val)
{ { int Result = 0;
    { int  val2 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
      Result = ((OID *) array_v(((OID *) c->ic)[varIdx]))[val2];
      } 
    return (Result);} 
  } 


// used for initialization
/* The c++ function for: setInconsistencyCount(c:AdditiveWCSP,varIdx:integer,val:integer,x:integer) [] */
void  ice_setInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val,int x)
{ { int  val2 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
    STOREI(array_v(((OID *) c->ic)[varIdx])[val2],x);
    } 
  } 


// used for incremental backtrackable updates
/* The c++ function for: increaseInconsistencyCount(c:AdditiveWCSP,varIdx:integer,val:integer,delta:integer) [] */
ClaireBoolean * ice_increaseInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int val,int delta)
{ if (ice.WTALK->value <= ClEnv->verbose)
   tformat_string("add ~S to IC[~S][~S] \n",ice.WTALK->value,list::alloc(3,delta,
    varIdx,
    val));
  else ;{ ClaireBoolean *Result ;
    { int  val2 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
      int  newIC = ((((OID *) array_v(((OID *) c->ic)[varIdx]))[val2])+delta);
      STOREI(array_v(((OID *) c->ic)[varIdx])[val2],newIC);
      if (((OID *) c->leastICValue)[varIdx] == val)
       Result = ice_recomputeVarPenalty_AdditiveWCSP(c,varIdx);
      else { Result = CFALSE;
          } 
        } 
    return (Result);} 
  } 


// allocation of the required data structures
/* The c++ function for: makeInconsistencyCount(c:AdditiveWCSP) [] */
void  ice_makeInconsistencyCount_AdditiveWCSP(AdditiveWCSP *c)
{ GC_BIND;
  { OID * dummyArray = GC_ARRAY(make_array_integer(0,Kernel._integer,0));
    (c->ic = make_array_integer(c->nbSubVars,GC_OBJECT(ClaireType,nth_type(Kernel._integer)),_array_(dummyArray)));
    { int  varIdx = 1;
      int  g0018 = c->nbSubVars;
      { OID gc_local;
        while ((varIdx <= g0018))
        { // HOHO, GC_LOOP not needed !
          { IntVar * var;
            { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
              } 
            int  minv = choco.getInf->fcall(((int) var));
            int  maxv = choco.getSup->fcall(((int) var));
            (((OID *) c->domainOffsets)[varIdx] = minv);
            (((OID *) c->ic)[varIdx] = _array_(make_array_integer(((maxv-minv)+1),Kernel._integer,0)));
            } 
          ++varIdx;
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 3: WCSP propagation as a reaction to Choco events         *
// ********************************************************************
// reacting to the instantiation of the varIdx-th variable in the network
/* The c++ function for: choco/awakeOnInst(c:AdditiveWCSP,varIdx:integer) [] */
void  choco_awakeOnInst_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ if (varIdx <= c->nbSubVars)
   { IntVar * var;
    { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
      } 
    int  val;
    { val = var->value;
      } 
    ClaireBoolean * anyPenaltyChange = CFALSE;
    int  deltaBackwardCheckingBound;
    { int  g0019 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
      deltaBackwardCheckingBound = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0019];
      } 
    STOREI(c->reactedToInst[varIdx],Kernel.ctrue);
    if (deltaBackwardCheckingBound > 0)
     { STOREI(c->backwardCheckingBound,(c->backwardCheckingBound+deltaBackwardCheckingBound));
      choco_constAwake_AbstractConstraint(c,CFALSE);
      } 
    if (ice.WTALK->value <= ClEnv->verbose)
     tformat_string("backward checking lower bound: ~S \n",ice.WTALK->value,list::alloc(1,c->backwardCheckingBound));
    else ;{ int  nbc = OBJECT(bag,(*(c->constIndices))[varIdx])->length;
      int  i = 1;
      int  g0020 = nbc;
      { OID gc_local;
        while ((i <= g0020))
        { // HOHO, GC_LOOP not needed !
          { int  subcIdx;
            { subcIdx = (*(OBJECT(bag,(*(c->constIndices))[varIdx])))[i];
              } 
            AbstractConstraint * subc;
            { subc = OBJECT(AbstractConstraint,((OID *) c->subConst)[subcIdx]);
              } 
            STOREI(c->nbUnInstantiatedVars[subcIdx],((((OID *) c->nbUnInstantiatedVars)[subcIdx])-1));
            if (((OID *) c->nbUnInstantiatedVars)[subcIdx] == 1)
             { int  var2Idx = ice_getUnInstantiatedVarIdx_AbstractWCSP(c,subcIdx);
              if (ice_updateIC_AdditiveWCSP(c,var2Idx,subcIdx) == CTRUE)
               anyPenaltyChange= CTRUE;
              } 
            } 
          ++i;
          } 
        } 
      } 
    if (anyPenaltyChange == CTRUE)
     choco_constAwake_AbstractConstraint(c,CFALSE);
    } 
  else choco_constAwake_AbstractConstraint(c,CFALSE);
    } 


// returns true is the penalty of the variable is changed, now that we known
// that the constraint subc is quasi-instantiated
/* The c++ function for: updateIC(c:AdditiveWCSP,varIdx:integer,subcIdx:integer) [] */
ClaireBoolean * ice_updateIC_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int subcIdx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    { IntVar * var;
      { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
        } 
      AbstractConstraint * subc;
      { subc = OBJECT(AbstractConstraint,((OID *) c->subConst)[subcIdx]);
        } 
      ClaireBoolean * changedPenalty = CFALSE;
      { IntVar * g0021 = var;
        AbstractIntDomain * g0022 = GC_OBJECT(AbstractIntDomain,g0021->bucket);
        if (g0022 == (NULL))
         { int  val = g0021->inf->latestValue;
          { OID gc_local;
            while ((val <= g0021->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { ClaireBoolean * wouldBeFeas = CTRUE;
                world_push();
                STOREI(var->value,val);
                wouldBeFeas= OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) subc))));
                world_pop();
                if (wouldBeFeas != CTRUE)
                 { if (ice_increaseInconsistencyCount_AdditiveWCSP(c,varIdx,val,((OID *) c->penalties)[subcIdx]) == CTRUE)
                   changedPenalty= CTRUE;
                  } 
                } 
              val= ((g0021->inf->latestValue <= (val+1)) ?
                (val+1) :
                g0021->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0022->isa,choco._LinkedListIntDomain))
         { int  val = g0021->inf->latestValue;
          { OID gc_local;
            while ((val <= g0021->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { ClaireBoolean * wouldBeFeas = CTRUE;
                world_push();
                STOREI(var->value,val);
                wouldBeFeas= OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) subc))));
                world_pop();
                if (wouldBeFeas != CTRUE)
                 { if (ice_increaseInconsistencyCount_AdditiveWCSP(c,varIdx,val,((OID *) c->penalties)[subcIdx]) == CTRUE)
                   changedPenalty= CTRUE;
                  } 
                } 
              val= choco.getNextValue->fcall(((int) g0022),((int) val));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0022)))));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              { ClaireBoolean * wouldBeFeas = CTRUE;
                world_push();
                STOREI(var->value,val);
                wouldBeFeas= OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.testIfSatisfied->fcall(((int) subc))));
                world_pop();
                if (wouldBeFeas != CTRUE)
                 { if (ice_increaseInconsistencyCount_AdditiveWCSP(c,varIdx,val,((OID *) c->penalties)[subcIdx]) == CTRUE)
                   changedPenalty= CTRUE;
                  } 
                } 
              GC_UNLOOP;} 
            } 
          } 
      Result = changedPenalty;
      } 
    GC_UNBIND; return (Result);} 
  } 


// returns true is the penalty of the variable is changed
/* The c++ function for: recomputeVarPenalty(c:AdditiveWCSP,varIdx:integer) [] */
ClaireBoolean * ice_recomputeVarPenalty_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ GC_BIND;
  ;{ ClaireBoolean *Result ;
    { IntVar * var;
      { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
        } 
      int  minIC = 99999999;
      int  argminIC = 0;
      { IntVar * g0023 = var;
        AbstractIntDomain * g0024 = GC_OBJECT(AbstractIntDomain,g0023->bucket);
        if (g0024 == (NULL))
         { int  val = g0023->inf->latestValue;
          { OID gc_local;
            while ((val <= g0023->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  currentIC;
                { int  g0025 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                  currentIC = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0025];
                  } 
                if (currentIC < minIC)
                 { minIC= currentIC;
                  argminIC= val;
                  } 
                } 
              val= ((g0023->inf->latestValue <= (val+1)) ?
                (val+1) :
                g0023->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0024->isa,choco._LinkedListIntDomain))
         { int  val = g0023->inf->latestValue;
          { OID gc_local;
            while ((val <= g0023->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  currentIC;
                { int  g0026 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                  currentIC = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0026];
                  } 
                if (currentIC < minIC)
                 { minIC= currentIC;
                  argminIC= val;
                  } 
                } 
              val= choco.getNextValue->fcall(((int) g0024),((int) val));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(val);
            bag *val_support;
            val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0024)))));
            for (START(val_support); NEXT(val);)
            { GC_LOOP;
              { int  currentIC;
                { int  g0027 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                  currentIC = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0027];
                  } 
                if (currentIC < minIC)
                 { minIC= currentIC;
                  argminIC= val;
                  } 
                } 
              GC_UNLOOP;} 
            } 
          } 
      { int  oldVarPenalty = ((OID *) c->varPenalties)[varIdx];
        if ((argminIC != ((OID *) c->leastICValue)[varIdx]) || 
            (minIC != ((OID *) c->varPenalties)[varIdx]))
         { if (ice.WTALK->value <= ClEnv->verbose)
           tformat_string("update varPenalty[~S] to ~S, argmin:~S \n",ice.WTALK->value,list::alloc(3,varIdx,
            minIC,
            argminIC));
          else ;STOREI(c->varPenalties[varIdx],minIC);
          STOREI(c->leastICValue[varIdx],argminIC);
          Result = CTRUE;
          } 
        else { Result = CFALSE;
            } 
          } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// accessing VARPEN[v]
/* The c++ function for: getVarPenalty(c:AdditiveWCSP,varIdx:integer) [] */
int  ice_getVarPenalty_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ return (((OID *) c->varPenalties)[varIdx]);} 


// reacting to the removal of oldval from the domain of the varIdx-th variable in the network
/* The c++ function for: choco/awakeOnRem(c:AdditiveWCSP,varIdx:integer,oldval:integer) [] */
void  choco_awakeOnRem_AdditiveWCSP(AdditiveWCSP *c,int varIdx,int oldval)
{ if (varIdx <= c->nbSubVars)
   { if (oldval == ((OID *) c->leastICValue)[varIdx])
     { if (ice_recomputeVarPenalty_AdditiveWCSP(c,varIdx) == CTRUE)
       choco_constAwake_AbstractConstraint(c,CFALSE);
      } 
    } 
  } 


// do we need to retrigger LB computations
// reacting to a set of removals from the domain of the varIdx-th variable in the network
/* The c++ function for: choco/awakeOnVar(c:AdditiveWCSP,varIdx:integer) [] */
void  choco_awakeOnVar_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ { IntVar * var;
    { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
      } 
    if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) var)))) == CTRUE)
     { if (ice_recomputeVarPenalty_AdditiveWCSP(c,varIdx) == CTRUE)
       choco_constAwake_AbstractConstraint(c,CFALSE);
      } 
    } 
  } 


// reacting to an increase of the domain lower bound of the varIdx-th variable in the network
/* The c++ function for: choco/awakeOnInf(c:AdditiveWCSP,varIdx:integer) [] */
void  choco_awakeOnInf_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ if (varIdx <= c->nbSubVars)
   choco_awakeOnVar_AdditiveWCSP(c,varIdx);
  } 


// reacting to an increase of the domain upper bound of the varIdx-th variable in the network
/* The c++ function for: choco/awakeOnSup(c:AdditiveWCSP,varIdx:integer) [] */
void  choco_awakeOnSup_AdditiveWCSP(AdditiveWCSP *c,int varIdx)
{ if (varIdx <= c->nbSubVars)
   choco_awakeOnVar_AdditiveWCSP(c,varIdx);
  else choco_constAwake_AbstractConstraint(c,CFALSE);
    } 


// the priority of the constraint (among the set of all global constraints)
/* The c++ function for: choco/getPriority(c:AdditiveWCSP) [] */
int  choco_getPriority_AdditiveWCSP_ice(AdditiveWCSP *c)
{ return (2);} 


// Note (<fl>, july 10th): the algorithm below has the nice property that one run is enough in order to reach
// propagation fixpoint: as long as the consequences of a bound computation are only value removals
// and not variable fixing, then a second pass would not modify the VARPEN (min of penalties over all values
// in the domain), nor the backwardcheckingbound, thus not the fwdchk bound.
// Thus, no loop until fixpoint is required.
// Thus: - the events generated by the method (updateInf(totalPenalty,...) and removal(...))
//         use as third parameter the WCSPConstraint index (this prevents from re-awakening the constraint
//         on the events that it did generate
//       - the code never recursively calls itself (propagate) nor calls the constAwake method (the clean way to
//         cause a second call)
/* The c++ function for: choco/propagate(c:AdditiveWCSP) [] */
void  choco_propagate_AdditiveWCSP(AdditiveWCSP *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  ;{ OID  g0045UU;
    { int  V_CL0046;{ int  g0047UU;
        { int  g0032 = 0;
          { int  g0034 = 1;
            int  g0035 = c->nbSubVars;
            { OID gc_local;
              while ((g0034 <= g0035))
              { // HOHO, GC_LOOP not needed !
                if (not_any(((OID *) c->reactedToInst)[g0034]) == CTRUE)
                 { int  g0033 = ((OID *) c->varPenalties)[g0034];
                  g0032= (g0032+g0033);
                  } 
                ++g0034;
                } 
              } 
            } 
          g0047UU = g0032;
          } 
        V_CL0046 = (c->backwardCheckingBound+g0047UU);
        } 
      
      g0045UU=V_CL0046;} 
    update_property(ice.forwardCheckingBound,
      c,
      23,
      Kernel._integer,
      g0045UU);
    } 
  if (ice.WTALK->value <= ClEnv->verbose)
   tformat_string("forward checking lower bound: ~S \n",ice.WTALK->value,list::alloc(1,c->forwardCheckingBound));
  else ;{ IntVar * tpv = GC_OBJECT(IntVar,OBJECT(IntVar,last_list(c->vars)));
    if (c->forwardCheckingBound > choco.getInf->fcall(((int) tpv)))
     (*choco.updateInf)(_oid_(tpv),
      c->forwardCheckingBound,
      (*(c->indices))[(c->nbSubVars+1)]);
    { int  varIdx = 1;
      int  g0036 = c->nbSubVars;
      { OID gc_local;
        while ((varIdx <= g0036))
        { GC_LOOP;
          if (not_any(((OID *) c->reactedToInst)[varIdx]) == CTRUE)
           { IntVar * var;
            { var = OBJECT(IntVar,(*(c->vars))[varIdx]);
              } 
            { IntVar * g0037 = var;
              AbstractIntDomain * g0038 = GC_OBJECT(AbstractIntDomain,g0037->bucket);
              if (g0038 == (NULL))
               { int  val = g0037->inf->latestValue;
                { OID gc_local;
                  while ((val <= g0037->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { ClaireBoolean * g0048I;
                      { int  g0049UU;
                        { int  g0050UU;
                          { int  g0039 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                            g0050UU = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0039];
                            } 
                          g0049UU = ((c->forwardCheckingBound-(((OID *) c->varPenalties)[varIdx]))+g0050UU);
                          } 
                        g0048I = ((g0049UU > choco.getSup->fcall(((int) tpv))) ? CTRUE : CFALSE);
                        } 
                      
                      if (g0048I == CTRUE) { if (ice.WVIEW->value <= ClEnv->verbose)
                           { list * g0051UU;
                            { OID v_bag;
                              GC_ANY(g0051UU= list::empty(Kernel.emptySet));
                              ((list *) g0051UU)->addFast(_oid_(var));
                              ((list *) g0051UU)->addFast(val);
                              ((list *) g0051UU)->addFast(c->forwardCheckingBound);
                              ((list *) g0051UU)->addFast(((OID *) c->varPenalties)[varIdx]);
                              { int  g0040 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                                v_bag = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0040];
                                } 
                              ((list *) g0051UU)->addFast(v_bag);} 
                            tformat_string("remove ~S-~S (fwd:~S, varpen:~S, ic:~S \n",ice.WVIEW->value,g0051UU);
                            } 
                          else ;(*choco.removeVal)(_oid_(var),
                            val,
                            (*(c->indices))[varIdx]);
                          } 
                        } 
                    val= ((g0037->inf->latestValue <= (val+1)) ?
                      (val+1) :
                      g0037->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0038->isa,choco._LinkedListIntDomain))
               { int  val = g0037->inf->latestValue;
                { OID gc_local;
                  while ((val <= g0037->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { ClaireBoolean * g0052I;
                      { int  g0053UU;
                        { int  g0054UU;
                          { int  g0041 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                            g0054UU = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0041];
                            } 
                          g0053UU = ((c->forwardCheckingBound-(((OID *) c->varPenalties)[varIdx]))+g0054UU);
                          } 
                        g0052I = ((g0053UU > choco.getSup->fcall(((int) tpv))) ? CTRUE : CFALSE);
                        } 
                      
                      if (g0052I == CTRUE) { if (ice.WVIEW->value <= ClEnv->verbose)
                           { list * g0055UU;
                            { OID v_bag;
                              GC_ANY(g0055UU= list::empty(Kernel.emptySet));
                              ((list *) g0055UU)->addFast(_oid_(var));
                              ((list *) g0055UU)->addFast(val);
                              ((list *) g0055UU)->addFast(c->forwardCheckingBound);
                              ((list *) g0055UU)->addFast(((OID *) c->varPenalties)[varIdx]);
                              { int  g0042 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                                v_bag = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0042];
                                } 
                              ((list *) g0055UU)->addFast(v_bag);} 
                            tformat_string("remove ~S-~S (fwd:~S, varpen:~S, ic:~S \n",ice.WVIEW->value,g0055UU);
                            } 
                          else ;(*choco.removeVal)(_oid_(var),
                            val,
                            (*(c->indices))[varIdx]);
                          } 
                        } 
                    val= choco.getNextValue->fcall(((int) g0038),((int) val));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(val);
                  bag *val_support;
                  val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0038)))));
                  for (START(val_support); NEXT(val);)
                  { GC_LOOP;
                    { ClaireBoolean * g0056I;
                      { int  g0057UU;
                        { int  g0058UU;
                          { int  g0043 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                            g0058UU = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0043];
                            } 
                          g0057UU = ((c->forwardCheckingBound-(((OID *) c->varPenalties)[varIdx]))+g0058UU);
                          } 
                        g0056I = ((g0057UU > choco.getSup->fcall(((int) tpv))) ? CTRUE : CFALSE);
                        } 
                      
                      if (g0056I == CTRUE) { if (ice.WVIEW->value <= ClEnv->verbose)
                           { list * g0059UU;
                            { OID v_bag;
                              GC_ANY(g0059UU= list::empty(Kernel.emptySet));
                              ((list *) g0059UU)->addFast(_oid_(var));
                              ((list *) g0059UU)->addFast(val);
                              ((list *) g0059UU)->addFast(c->forwardCheckingBound);
                              ((list *) g0059UU)->addFast(((OID *) c->varPenalties)[varIdx]);
                              { int  g0044 = ((val-(((OID *) c->domainOffsets)[varIdx]))+1);
                                v_bag = ((OID *) array_v(((OID *) c->ic)[varIdx]))[g0044];
                                } 
                              ((list *) g0059UU)->addFast(v_bag);} 
                            tformat_string("remove ~S-~S (fwd:~S, varpen:~S, ic:~S \n",ice.WVIEW->value,g0059UU);
                            } 
                          else ;(*choco.removeVal)(_oid_(var),
                            val,
                            (*(c->indices))[varIdx]);
                          } 
                        } 
                    GC_UNLOOP;} 
                  } 
                } 
            } 
          ++varIdx;
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


// the method for the initial propagation of the global constraint
/* The c++ function for: choco/awake(c:AdditiveWCSP) [] */
void  choco_awake_AdditiveWCSP_ice(AdditiveWCSP *c)
{ { int  varIdx = 1;
    int  g0060 = c->nbSubVars;
    { OID gc_local;
      while ((varIdx <= g0060))
      { // HOHO, GC_LOOP not needed !
        { ClaireBoolean * g0065I;
          { OID V_C;{ OID  g0066UU;
              { g0066UU = (*(c->vars))[varIdx];
                } 
              V_C = (*choco.isInstantiated)(g0066UU);
              } 
            
            g0065I=OBJECT(ClaireBoolean,V_C);} 
          
          if (g0065I == CTRUE) STOREI(c->reactedToInst[varIdx],Kernel.ctrue);
            } 
        ++varIdx;
        } 
      } 
    } 
  { int  subcIdx = 1;
    int  g0061 = c->nbSubConst;
    { OID gc_local;
      while ((subcIdx <= g0061))
      { // HOHO, GC_LOOP not needed !
        { int  g0067; 
          OID  g0068;
          g0067 = subcIdx;
          { int  V_CL0069;{ int  g0070UU;
              { OID V_C;{ OID  g0072UU;
                  { g0072UU = ((OID *) c->subConst)[subcIdx];
                    } 
                  V_C = (*choco.getNbVars)(g0072UU);
                  } 
                
                g0070UU=V_C;} 
              int  g0071UU;
              { int  g0062 = 0;
                { OID gc_local;
                  ITERATE(g0063);
                  for (START(OBJECT(bag,(*(c->varIndices))[subcIdx])); NEXT(g0063);)
                  { GC_LOOP;
                    if ((OBJECT(ClaireBoolean,((OID *) c->reactedToInst)[g0063])) == CTRUE)
                     ++g0062;
                    GC_UNLOOP;} 
                  } 
                g0071UU = g0062;
                } 
              V_CL0069 = (g0070UU-g0071UU);
              } 
            
            g0068=V_CL0069;} 
          (((OID *) c->nbUnInstantiatedVars)[g0067] = g0068);} 
        if (ice.WTALK->value <= ClEnv->verbose)
         tformat_string("initialize nbUnInst[~S]=~S \n",ice.WTALK->value,list::alloc(2,subcIdx,((OID *) c->nbUnInstantiatedVars)[subcIdx]));
        else ;if (((OID *) c->nbUnInstantiatedVars)[subcIdx] == 0)
         { { ClaireBoolean * g0073I;
            { OID  g0074UU;
              { OID  g0075UU;
                { g0075UU = ((OID *) c->subConst)[subcIdx];
                  } 
                g0074UU = (*choco.testIfSatisfied)(g0075UU);
                } 
              g0073I = not_any(g0074UU);
              } 
            
            if (g0073I == CTRUE) { OID  g0076UU;
                { int  V_CL0077;{ int  g0078UU;
                    { g0078UU = ((OID *) c->penalties)[subcIdx];
                      } 
                    V_CL0077 = (c->backwardCheckingBound+g0078UU);
                    } 
                  
                  g0076UU=V_CL0077;} 
                update_property(ice.backwardCheckingBound,
                  c,
                  22,
                  Kernel._integer,
                  g0076UU);
                } 
              } 
          } 
        else if (((OID *) c->nbUnInstantiatedVars)[subcIdx] == 1)
         { OID gc_local;
          ITERATE(varIdx);
          for (START(OBJECT(bag,(*(c->varIndices))[subcIdx])); NEXT(varIdx);)
          { GC_LOOP;
            ice_updateIC_AdditiveWCSP(c,varIdx,subcIdx);
            GC_UNLOOP;} 
          } 
        ++subcIdx;
        } 
      } 
    } 
  { int  varIdx = 1;
    int  g0064 = c->nbSubVars;
    { OID gc_local;
      while ((varIdx <= g0064))
      { // HOHO, GC_LOOP not needed !
        ice_recomputeVarPenalty_AdditiveWCSP(c,varIdx);
        ++varIdx;
        } 
      } 
    } 
  choco_propagate_AdditiveWCSP(c);
  } 


// checking whether the constraint is satisfied or not, once all variables are instantiated
/* The c++ function for: choco/testIfSatisfied(c:AdditiveWCSP) [] */
ClaireBoolean * choco_testIfSatisfied_AdditiveWCSP(AdditiveWCSP *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { int  nbtrue = 0;
      { int  i = 1;
        int  g0079 = c->nbSubConst;
        { OID gc_local;
          while ((i <= g0079))
          { // HOHO, GC_LOOP not needed !
            { ClaireBoolean * g0080I;
              { OID V_C;{ OID  g0081UU;
                  { g0081UU = ((OID *) c->subConst)[i];
                    } 
                  V_C = (*choco.testIfSatisfied)(g0081UU);
                  } 
                
                g0080I=OBJECT(ClaireBoolean,V_C);} 
              
              if (g0080I == CTRUE) ++nbtrue;
                } 
            ++i;
            } 
          } 
        } 
      Result = OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(GC_OID(last_list(c->vars)),
        nbtrue));
      } 
    GC_UNBIND; return (Result);} 
  } 

