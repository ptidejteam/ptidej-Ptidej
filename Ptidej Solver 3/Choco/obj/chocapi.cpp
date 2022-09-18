/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\chocapi.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:31 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: chocapi.cl                                                 *
// *    API: application programmer interface (public methods)        *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: problems                                               *
// *   Part 2: variables                                              *
// *   Part 3: Constraints as tuples of values                        *
// *   Part 4: Arithmetic constraints                                 *
// *   Part 5: Boolean connectors                                     *
// *   Part 6: global constraints                                     *
// *   Part 7: adding information                                     *
// *   Part 8: searching for solutions / (tree search) optimization   *
// *   Part 9: searching for solutions / (assign & repair) local opt. *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: problems                                               *
// ********************************************************************
// v0.9907
// v1.010 use setActiveProblem()
/* The c++ function for: makeProblem(s:string,n:integer) [] */
Problem * choco_makeProblem_string(char *s,int n)
{ GC_BIND;
  { Problem *Result ;
    { Problem * pb;
      { { Problem * _CL_obj = ((Problem *) GC_OBJECT(Problem,new_object_class(choco._Problem)));
          (_CL_obj->name = copy_string(s));
          { Problem * g1190; 
            GlobalSearchSolver * g1191;
            g1190 = _CL_obj;
            { GlobalSearchSolver * _CL_obj = ((GlobalSearchSolver *) GC_OBJECT(GlobalSearchSolver,new_object_class(choco._GlobalSearchSolver)));
              g1191 = _CL_obj;
              } 
            (g1190->globalSearchSolver = g1191);} 
          { Problem * g1192; 
            LocalSearchSolver * g1193;
            g1192 = _CL_obj;
            { LocalSearchSolver * _CL_obj = ((LocalSearchSolver *) GC_OBJECT(LocalSearchSolver,new_object_class(choco._LocalSearchSolver)));
              g1193 = _CL_obj;
              } 
            (g1192->localSearchSolver = g1193);} 
          pb = _CL_obj;
          } 
        GC_OBJECT(Problem,pb);} 
      { ChocEngine * pe = GC_OBJECT(ChocEngine,choco_makeChocEngine_integer(n));
        choco_attachPropagationEngine_Problem(pb,pe);
        } 
      { ConflictCount * ie = GC_OBJECT(ConflictCount,choco_makeConflictCount_void());
        choco_attachInvariantEngine_Problem(pb,ie);
        } 
      choco_setActiveProblem_Problem(pb);
      Result = pb;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.0
/* The c++ function for: getProblem(v:IntVar) [] */
Problem * choco_getProblem_IntVar(IntVar *v)
{ return (v->problem);} 


// v1.02 <thb> add the case when the constraint c involves only constant variables, considered as belonging to CURRENT_PB
/* The c++ function for: getProblem(c:AbstractConstraint) [] */
Problem * choco_getProblem_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { Problem *Result ;
    { OID  p = GC_OID(choco.CURRENT_PB->value);
      int  n = choco.getNbVars->fcall(((int) c));
      { OID  i0test;
        { { OID  i_some = CNULL;
            { int  i = 1;
              int  g1194 = n;
              { OID gc_local;
                while ((i <= g1194))
                { // HOHO, GC_LOOP not needed !
                  if (OBJECT(IntVar,(*choco.getVar)(_oid_(c),
                    i))->problem != (NULL))
                   { i_some= i;
                    break;} 
                  ++i;
                  } 
                } 
              } 
            i0test = i_some;
            } 
          GC_OID(i0test);} 
        if (i0test != CNULL)
         { int  i0 = i0test;
          p= GC_OID(_oid_(OBJECT(IntVar,(*choco.getVar)(_oid_(c),
            i0))->problem));
          } 
        else ;} 
      Result = OBJECT(Problem,p);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.010 new API methods
/* The c++ function for: getActiveProblem(_CL_obj:void) [] */
Problem * choco_getActiveProblem_void()
{ return (OBJECT(Problem,choco.CURRENT_PB->value));} 


/* The c++ function for: setActiveProblem(pb:Problem) [] */
void  choco_setActiveProblem_Problem(Problem *pb)
{ (choco.CURRENT_PB->value= _oid_(pb));
  } 


/* The c++ function for: discardProblem(pb:Problem) [] */
void  choco_discardProblem_Problem(Problem *pb)
{ { GlobalSearchSolver * gs = pb->globalSearchSolver;
    if (gs == (NULL))
     ;else { backtrack_integer(gs->baseWorld);
        (choco.CURRENT_PB->value= CNULL);
        } 
      } 
  } 


// v1.013: accessing the propagation engine
/* The c++ function for: getPropagationEngine(p:Problem) [] */
PropagationEngine * choco_getPropagationEngine_Problem(Problem *p)
{ return (p->propagationEngine);} 


// v1.013: a toggle: choosing to delay or no the propagation on linear constraints
/* The c++ function for: setDelayedLinearConstraintPropagation(pe:ChocEngine,b:boolean) [] */
void  choco_setDelayedLinearConstraintPropagation_ChocEngine(ChocEngine *pe,ClaireBoolean *b)
{ if (b == CTRUE)
   (pe->delayLinCombThreshold = 0);
  else (pe->delayLinCombThreshold = 100000);
    } 


// ********************************************************************
// *   Part 2: variables                                              *
// ********************************************************************
// public methods for creating variables on the fly and assiging them a name
//
// v0.37 added closeIntVar because of new backtrackable domain bounds
/* The c++ function for: makeBoundIntVar(p:Problem,s:string,i:integer,j:integer) [] */
IntVar * choco_makeBoundIntVar_Problem1(Problem *p,char *s,int i,int j)
{ GC_BIND;
  ;{ IntVar *Result ;
    { IntVar * v;
      { { IntVar * _CL_obj = ((IntVar *) GC_OBJECT(IntVar,new_object_class(choco._IntVar)));
          (_CL_obj->name = copy_string(s));
          v = _CL_obj;
          } 
        GC_OBJECT(IntVar,v);} 
      { IntVar * g1196; 
        StoredInt * g1197;
        g1196 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = i);
          g1197 = _CL_obj;
          } 
        (g1196->inf = g1197);} 
      { IntVar * g1198; 
        StoredInt * g1199;
        g1198 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = j);
          g1199 = _CL_obj;
          } 
        (g1198->sup = g1199);} 
      if (i == j)
       STOREI(v->value,i);
      { IntVar * g1200; 
        IncInf * g1201;
        g1200 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g1201 = _CL_obj;
          } 
        (g1200->updtInfEvt = g1201);} 
      { IntVar * g1202; 
        DecSup * g1203;
        g1202 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g1203 = _CL_obj;
          } 
        (g1202->updtSupEvt = g1203);} 
      { IntVar * g1204; 
        InstInt * g1205;
        g1204 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g1205 = _CL_obj;
          } 
        (g1204->instantiateEvt = g1205);} 
      { ValueRemovals * g1195;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = 1);
            g1195 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g1195);} 
        (g1195->valueStack = make_list_integer2(g1195->maxVals,Kernel._integer,0));
        (g1195->causeStack = make_list_integer2(g1195->maxVals,Kernel._integer,0));
        (v->remValEvt = g1195);
        } 
      choco_addIntVar_Problem(p,v);
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeBoundIntVar(p:Problem,i:integer,j:integer) [] */
IntVar * choco_makeBoundIntVar_Problem2(Problem *p,int i,int j)
{ return (choco_makeBoundIntVar_Problem1(p,"",i,j));} 


/* The c++ function for: makeBoundIntVar(p:Problem,s:string) [] */
IntVar * choco_makeBoundIntVar_Problem3(Problem *p,char *s)
{ return (choco_makeBoundIntVar_Problem1(p,s,-100,100));} 


// v0.37
/* The c++ function for: makeConstantIntVar(x:integer) [] */
IntVar * choco_makeConstantIntVar_integer(int x)
{ GC_BIND;
  { IntVar *Result ;
    { IntVar * v;
      { { IntVar * _CL_obj = ((IntVar *) GC_OBJECT(IntVar,new_object_class(choco._IntVar)));
          (_CL_obj->name = append_string(GC_STRING(append_string("'",GC_STRING(string_I_integer (x)))),"'"));
          v = _CL_obj;
          } 
        GC_OBJECT(IntVar,v);} 
      { IntVar * g1207; 
        StoredInt * g1208;
        g1207 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = x);
          g1208 = _CL_obj;
          } 
        (g1207->inf = g1208);} 
      { IntVar * g1209; 
        StoredInt * g1210;
        g1209 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = x);
          g1210 = _CL_obj;
          } 
        (g1209->sup = g1210);} 
      if (x == x)
       STOREI(v->value,x);
      { IntVar * g1211; 
        IncInf * g1212;
        g1211 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g1212 = _CL_obj;
          } 
        (g1211->updtInfEvt = g1212);} 
      { IntVar * g1213; 
        DecSup * g1214;
        g1213 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g1214 = _CL_obj;
          } 
        (g1213->updtSupEvt = g1214);} 
      { IntVar * g1215; 
        InstInt * g1216;
        g1215 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g1216 = _CL_obj;
          } 
        (g1215->instantiateEvt = g1216);} 
      { ValueRemovals * g1206;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = 0);
            g1206 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g1206);} 
        (g1206->valueStack = make_list_integer2(g1206->maxVals,Kernel._integer,0));
        (g1206->causeStack = make_list_integer2(g1206->maxVals,Kernel._integer,0));
        (v->remValEvt = g1206);
        } 
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.9903: there will never be a removal to react to
/* The c++ function for: makeIntVar(p:Problem,s:string,i:integer,j:integer) [] */
IntVar * choco_makeIntVar_Problem1(Problem *p,char *s,int i,int j)
{ GC_BIND;
  ;{ IntVar *Result ;
    { IntVar * v;
      { { IntVar * _CL_obj = ((IntVar *) GC_OBJECT(IntVar,new_object_class(choco._IntVar)));
          (_CL_obj->name = copy_string(s));
          v = _CL_obj;
          } 
        GC_OBJECT(IntVar,v);} 
      { IntVar * g1218; 
        StoredInt * g1219;
        g1218 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = i);
          g1219 = _CL_obj;
          } 
        (g1218->inf = g1219);} 
      { IntVar * g1220; 
        StoredInt * g1221;
        g1220 = v;
        { StoredInt * _CL_obj = ((StoredInt *) GC_OBJECT(StoredInt,new_object_class(choco._StoredInt)));
          (_CL_obj->latestValue = j);
          g1221 = _CL_obj;
          } 
        (g1220->sup = g1221);} 
      if (i == j)
       STOREI(v->value,i);
      { IntVar * g1222; 
        IncInf * g1223;
        g1222 = v;
        { IncInf * _CL_obj = ((IncInf *) GC_OBJECT(IncInf,new_object_class(choco._IncInf)));
          (_CL_obj->modifiedVar = v);
          g1223 = _CL_obj;
          } 
        (g1222->updtInfEvt = g1223);} 
      { IntVar * g1224; 
        DecSup * g1225;
        g1224 = v;
        { DecSup * _CL_obj = ((DecSup *) GC_OBJECT(DecSup,new_object_class(choco._DecSup)));
          (_CL_obj->modifiedVar = v);
          g1225 = _CL_obj;
          } 
        (g1224->updtSupEvt = g1225);} 
      { IntVar * g1226; 
        InstInt * g1227;
        g1226 = v;
        { InstInt * _CL_obj = ((InstInt *) GC_OBJECT(InstInt,new_object_class(choco._InstInt)));
          (_CL_obj->modifiedVar = v);
          g1227 = _CL_obj;
          } 
        (g1226->instantiateEvt = g1227);} 
      { ValueRemovals * g1217;
        { { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
            (_CL_obj->modifiedVar = v);
            (_CL_obj->maxVals = ((((j-i)-1) <= 3) ?
              ((j-i)-1) :
              3 ));
            g1217 = _CL_obj;
            } 
          GC_OBJECT(ValueRemovals,g1217);} 
        (g1217->valueStack = make_list_integer2(g1217->maxVals,Kernel._integer,0));
        (g1217->causeStack = make_list_integer2(g1217->maxVals,Kernel._integer,0));
        (v->remValEvt = g1217);
        } 
      choco_addIntVar_Problem(p,v);
      (v->bucket = choco_makeLinkedListIntDomain_integer(i,j));
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeIntVar(p:Problem,i:integer,j:integer) [] */
IntVar * choco_makeIntVar_Problem2(Problem *p,int i,int j)
{ return (choco_makeIntVar_Problem1(p,"",i,j));} 


/* The c++ function for: makeIntVar(p:Problem,s:string) [] */
IntVar * choco_makeIntVar_Problem3(Problem *p,char *s)
{ return (choco_makeIntVar_Problem1(p,s,-100,100));} 


// v0.22 <fl> documented but not implemented ...
// v0.26 stronger typing of b
/* The c++ function for: makeIntVar(p:Problem,s:string,b:list[integer]) [] */
IntVar * choco_makeIntVar_Problem4(Problem *p,char *s,list *b)
{ GC_BIND;
  { IntVar *Result ;
    { int  minVal;
      { int  g1228 = 99999999;
        { OID gc_local;
          ITERATE(g1229);
          for (START(b); NEXT(g1229);)
          { GC_LOOP;
            g1228= ((g1229 <= g1228) ?
              g1229 :
              g1228 );
            GC_UNLOOP;} 
          } 
        minVal = g1228;
        } 
      int  maxVal;
      { int  g1230 = -99999999;
        { OID gc_local;
          ITERATE(g1231);
          for (START(b); NEXT(g1231);)
          { GC_LOOP;
            g1230= ((g1231 <= g1230) ?
              g1230 :
              g1231 );
            GC_UNLOOP;} 
          } 
        maxVal = g1230;
        } 
      IntVar * v = GC_OBJECT(IntVar,choco_makeIntVar_Problem1(p,s,minVal,maxVal));
      { int  val = minVal;
        int  g1232 = maxVal;
        { OID gc_local;
          while ((val <= g1232))
          { GC_LOOP;
            if (b->memq(val) != CTRUE)
             _oid_((ClaireObject *) choco.removeDomainVal->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(v->bucket)))),((int) val)));
            ++val;
            GC_UNLOOP;} 
          } 
        } 
      Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v0.26 stronger typing of b
/* The c++ function for: makeIntVar(p:Problem,b:list[integer]) [] */
IntVar * choco_makeIntVar_Problem5(Problem *p,list *b)
{ return (choco_makeIntVar_Problem4(p,"",b));} 


// v1.330: introducing set variables
/* The c++ function for: makeSetVar(p:Problem,s:string,i:integer,j:integer) [] */
SetVar * choco_makeSetVar_Problem1(Problem *p,char *s,int i,int j)
{ GC_BIND;
  ;{ SetVar *Result ;
    { SetVar * v;
      { { SetVar * _CL_obj = ((SetVar *) GC_OBJECT(SetVar,new_object_class(choco._SetVar)));
          (_CL_obj->name = copy_string(s));
          v = _CL_obj;
          } 
        GC_OBJECT(SetVar,v);} 
      { SetVar * g1233; 
        IncKer * g1234;
        g1233 = v;
        { IncKer * _CL_obj = ((IncKer *) GC_OBJECT(IncKer,new_object_class(choco._IncKer)));
          (_CL_obj->modifiedVar = v);
          g1234 = _CL_obj;
          } 
        (g1233->updtKerEvt = g1234);} 
      { SetVar * g1235; 
        DecEnv * g1236;
        g1235 = v;
        { DecEnv * _CL_obj = ((DecEnv *) GC_OBJECT(DecEnv,new_object_class(choco._DecEnv)));
          (_CL_obj->modifiedVar = v);
          g1236 = _CL_obj;
          } 
        (g1235->updtEnvEvt = g1236);} 
      { SetVar * g1237; 
        InstSet * g1238;
        g1237 = v;
        { InstSet * _CL_obj = ((InstSet *) GC_OBJECT(InstSet,new_object_class(choco._InstSet)));
          (_CL_obj->modifiedVar = v);
          g1238 = _CL_obj;
          } 
        (g1237->instantiateEvt = g1238);} 
      choco_addSetVar_Problem(p,v);
      if (((j-i)+1) <= 30)
       (v->setBucket = choco_makeBitSetDomain_integer(i,j));
      else (v->setBucket = choco_makeBitListSetDomain_integer(i,j));
        Result = v;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSetVar(p:Problem,i:integer,j:integer) [] */
SetVar * choco_makeSetVar_Problem2(Problem *p,int i,int j)
{ return (choco_makeSetVar_Problem1(p,"",i,j));} 


/* The c++ function for: makeSetVar(p:Problem,s:string,i:integer,j:integer,cardVar:IntVar) [] */
SetVar * choco_makeSetVar_Problem3(Problem *p,char *s,int i,int j,IntVar *cardVar)
{ GC_BIND;
  { SetVar *Result ;
    { SetVar * sv = GC_OBJECT(SetVar,choco_makeSetVar_Problem1(p,s,i,j));
      choco_post_Problem(p,GC_OBJECT(SetCard,choco_setCard_SetVar(sv,cardVar)));
      Result = sv;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSetVar(p:Problem,i:integer,j:integer,cardVar:IntVar) [] */
SetVar * choco_makeSetVar_Problem4(Problem *p,int i,int j,IntVar *cardVar)
{ return (choco_makeSetVar_Problem3(p,
    "",
    i,
    j,
    cardVar));} 


// ********************************************************************
// *   Part 3: Constraints as tuples of values                        *
// ********************************************************************
// ---- binary constraints -----
// introducing relations
// creating a relation from a test method
/* The c++ function for: makeBinTruthTest(m:method[range:({boolean})]) [] */
TruthTest * choco_makeBinTruthTest_method(method *m)
{ GC_BIND;
  { TruthTest *Result ;
    { TruthTest * _CL_obj = ((TruthTest *) GC_OBJECT(TruthTest,new_object_class(choco._TruthTest)));
      (_CL_obj->test = m);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// creating an empty relation table
/* The c++ function for: makeBinRelation(min1:integer,max1:integer,min2:integer,max2:integer) [] */
TruthTable2D * choco_makeBinRelation_integer1(int min1,int max1,int min2,int max2)
{ GC_BIND;
  { TruthTable2D *Result ;
    { int  n = ((max1-min1)+1);
      int  m = ((max2-min2)+1);
      BoolMatrix2D * mat = GC_OBJECT(BoolMatrix2D,choco_make2DBoolMatrix_integer(min1,
        max1,
        min2,
        max2,
        CFALSE,
        CFALSE));
      TruthTable2D * _CL_obj = ((TruthTable2D *) GC_OBJECT(TruthTable2D,new_object_class(choco._TruthTable2D)));
      (_CL_obj->matrix = mat);
      (_CL_obj->offset1 = min1);
      (_CL_obj->offset2 = min2);
      (_CL_obj->size1 = n);
      (_CL_obj->size2 = m);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// creating a relation from an enumeration of tuples
/* The c++ function for: makeBinRelation(min1:integer,max1:integer,min2:integer,max2:integer,mytuples:list[tuple(integer, integer)]) [] */
TruthTable2D * choco_makeBinRelation_integer2(int min1,int max1,int min2,int max2,list *mytuples)
{ GC_BIND;
  { TruthTable2D *Result ;
    { int  n = ((max1-min1)+1);
      int  m = ((max2-min2)+1);
      BoolMatrix2D * mat = GC_OBJECT(BoolMatrix2D,choco_make2DBoolMatrix_integer(min1,
        max1,
        min2,
        max2,
        CFALSE,
        CFALSE));
      TruthTable2D * tt;
      { { TruthTable2D * _CL_obj = ((TruthTable2D *) GC_OBJECT(TruthTable2D,new_object_class(choco._TruthTable2D)));
          (_CL_obj->matrix = mat);
          (_CL_obj->offset1 = min1);
          (_CL_obj->offset2 = min2);
          (_CL_obj->size1 = n);
          (_CL_obj->size2 = m);
          tt = _CL_obj;
          } 
        GC_OBJECT(TruthTable2D,tt);} 
      { OID gc_local;
        ITERATE(t);
        for (START(mytuples); NEXT(t);)
        { GC_LOOP;
          if ((min1 <= (*(OBJECT(bag,t)))[1]) && 
              (((*(OBJECT(bag,t)))[1] <= max1) && 
                ((min2 <= (*(OBJECT(bag,t)))[2]) && 
                  ((*(OBJECT(bag,t)))[2] <= max2))))
           { int  i = ((((*(OBJECT(bag,t)))[1])-min1)+1);
            int  j = ((((*(OBJECT(bag,t)))[2])-min2)+1);
            claire_store_BoolMatrix2D(mat,(*(OBJECT(bag,t)))[1],(*(OBJECT(bag,t)))[2],CTRUE);
            } 
          GC_UNLOOP;} 
        } 
      Result = tt;
      } 
    GC_UNBIND; return (Result);} 
  } 


// generic API for stating binary constraints
/* The c++ function for: binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean,ac:integer) [] */
CSPBinConstraint * choco_binConstraint_IntVar1(IntVar *va,IntVar *vb,BinRelation *feasRel,ClaireBoolean *feas,int ac)
{ { CSPBinConstraint *Result ;
    { ClaireObject *V_CC ;
      if (ac == 3)
       V_CC = choco_makeAC3BinConstraint_IntVar(va,vb,feas,feasRel);
      else if (ac == 4)
       V_CC = choco_makeAC4BinConstraint_IntVar(va,vb,feas,feasRel);
      else if (ac == 2001)
       V_CC = choco_makeAC2001BinConstraint_IntVar(va,vb,feas,feasRel);
      else close_exception(((general_error *) (*Core._general_error)(_string_("algorithm AC~S not implemented yet for binary constraints"),
          _oid_(list::alloc(1,ac)))));
        Result= (CSPBinConstraint *) V_CC;} 
    return (Result);} 
  } 


// AC2001 is the default algorithm
/* The c++ function for: binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean) [] */
CSPBinConstraint * choco_binConstraint_IntVar2(IntVar *va,IntVar *vb,BinRelation *feasRel,ClaireBoolean *feas)
{ return (choco_binConstraint_IntVar1(va,
    vb,
    feasRel,
    feas,
    2001));} 


// by default the positive relation is provided
/* The c++ function for: binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,ac:integer) [] */
CSPBinConstraint * choco_binConstraint_IntVar3(IntVar *va,IntVar *vb,BinRelation *feasRel,int ac)
{ return (choco_binConstraint_IntVar1(va,
    vb,
    feasRel,
    CTRUE,
    ac));} 


// Simplest API: by default the positive relation is provided and AC2001 is the algorithm
/* The c++ function for: binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation) [] */
CSPBinConstraint * choco_binConstraint_IntVar4(IntVar *va,IntVar *vb,BinRelation *feasRel)
{ return (choco_binConstraint_IntVar1(va,
    vb,
    feasRel,
    CTRUE,
    2001));} 


// ----- older deprecated API (kept for backward compatibility)
/* The c++ function for: feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple(integer, integer)],ac:integer) [] */
CSPBinConstraint * choco_feasPairConstraint_IntVar1(IntVar *va,IntVar *vb,list *goodPairs,int ac)
{ GC_BIND;
  { CSPBinConstraint *Result ;
    { TruthTable2D * feasRel = GC_OBJECT(TruthTable2D,choco_makeBinRelation_integer2(va->inf->latestValue,
        va->sup->latestValue,
        vb->inf->latestValue,
        vb->sup->latestValue,
        goodPairs));
      Result = choco_binConstraint_IntVar1(va,
        vb,
        feasRel,
        CTRUE,
        ac);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple(integer, integer)],ac:integer) [] */
CSPBinConstraint * choco_infeasPairConstraint_IntVar1(IntVar *va,IntVar *vb,list *badPairs,int ac)
{ GC_BIND;
  { CSPBinConstraint *Result ;
    { TruthTable2D * feasRel = GC_OBJECT(TruthTable2D,choco_makeBinRelation_integer2(va->inf->latestValue,
        va->sup->latestValue,
        vb->inf->latestValue,
        vb->sup->latestValue,
        badPairs));
      Result = choco_binConstraint_IntVar1(va,
        vb,
        feasRel,
        CFALSE,
        ac);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: feasBinTestConstraint(va:IntVar,vb:IntVar,m:method,ac:integer) [] */
CSPBinConstraint * choco_feasBinTestConstraint_IntVar1(IntVar *va,IntVar *vb,method *m,int ac)
{ GC_BIND;
  { CSPBinConstraint *Result ;
    { TruthTest * feasRel = GC_OBJECT(TruthTest,choco_makeBinTruthTest_method(m));
      Result = choco_binConstraint_IntVar1(va,
        vb,
        feasRel,
        CTRUE,
        ac);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: infeasBinTestConstraint(va:IntVar,vb:IntVar,m:method,ac:integer) [] */
CSPBinConstraint * choco_infeasBinTestConstraint_IntVar1(IntVar *va,IntVar *vb,method *m,int ac)
{ GC_BIND;
  { CSPBinConstraint *Result ;
    { TruthTest * feasRel = GC_OBJECT(TruthTest,choco_makeBinTruthTest_method(m));
      Result = choco_binConstraint_IntVar1(va,
        vb,
        feasRel,
        CFALSE,
        ac);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple]) [] */
CSPBinConstraint * choco_feasPairConstraint_IntVar2(IntVar *va,IntVar *vb,list *goodPairs)
{ return (choco_feasPairConstraint_IntVar1(va,vb,goodPairs,2001));} 


/* The c++ function for: infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple]) [] */
CSPBinConstraint * choco_infeasPairConstraint_IntVar2(IntVar *va,IntVar *vb,list *badPairs)
{ return (choco_infeasPairConstraint_IntVar1(va,vb,badPairs,2001));} 


/* The c++ function for: feasBinTestConstraint(va:IntVar,vb:IntVar,m:method) [] */
CSPBinConstraint * choco_feasBinTestConstraint_IntVar2(IntVar *va,IntVar *vb,method *m)
{ return (choco_feasBinTestConstraint_IntVar1(va,vb,m,2001));} 


/* The c++ function for: infeasBinTestConstraint(va:IntVar,vb:IntVar,m:method) [] */
CSPBinConstraint * choco_infeasBinTestConstraint_IntVar2(IntVar *va,IntVar *vb,method *m)
{ return (choco_infeasBinTestConstraint_IntVar1(va,vb,m,2001));} 


// ---------------------
// n-ary constraints 
/* The c++ function for: feasTupleConstraint(lvars:list[IntVar],goodTuples:list[list[integer]]) [] */
CSPLargeConstraint * choco_feasTupleConstraint_list(list *lvars,list *goodTuples)
{ return (choco_tupleConstraint_list(lvars,CTRUE,goodTuples));} 


/* The c++ function for: infeasTupleConstraint(lvars:list[IntVar],badTuples:list[list[integer]]) [] */
CSPLargeConstraint * choco_infeasTupleConstraint_list(list *lvars,list *badTuples)
{ return (choco_tupleConstraint_list(lvars,CFALSE,badTuples));} 


// claire3 port: building a staticly typed vars lisit instead of a simple copy
/* The c++ function for: feasTestConstraint(lvars:list[IntVar],m:method) [] */
CSPLargeConstraint * choco_feasTestConstraint_list(list *lvars,method *m)
{ GC_BIND;
  { CSPLargeConstraint *Result ;
    { CSPLargeConstraint * c;
      { { CSPLargeConstraint * _CL_obj = ((CSPLargeConstraint *) GC_OBJECT(CSPLargeConstraint,new_object_class(choco._CSPLargeConstraint)));
          { LargeIntConstraint * g1239; 
            list * g1240;
            g1239 = _CL_obj;
            { bag *v_list; OID v_val;
              OID v,CLcount;
              v_list = lvars;
               g1240 = v_list->clone(choco._IntVar);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v = (*(v_list))[CLcount];
                v_val = v;
                
                (*((list *) g1240))[CLcount] = v_val;} 
              } 
            (g1239->vars = g1240);} 
          (_CL_obj->cste = 0);
          c = _CL_obj;
          } 
        GC_OBJECT(CSPLargeConstraint,c);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      (c->cachedTuples = CFALSE);
      (c->feasTest = m);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 4: Arithmetic constraints                                 *
// ********************************************************************
// General linear combinations
//  we store linear expressions in temporary data structures: terms
// v1.0 renamed Term (was Term)
//  LinTerm: a temporary object representing a linear term
// v1.02 <franck>
/* The c++ function for: self_print(t:LinTerm) [] */
void  claire_self_print_LinTerm_choco(LinTerm *t)
{ { int  tLength = t->lcoeff->length;
    if (tLength > 0)
     { print_any((*(t->lcoeff))[1]);
      princ_string(".");
      print_any((*(t->lvars))[1]);
      princ_string("");
      } 
    { int  i = 2;
      int  g1241 = tLength;
      { OID gc_local;
        while ((i <= g1241))
        { // HOHO, GC_LOOP not needed !
          princ_string(" + ");
          print_any((*(t->lcoeff))[i]);
          princ_string(".");
          print_any((*(t->lvars))[i]);
          princ_string("");
          ++i;
          } 
        } 
      } 
    if (t->cste < 0)
     { princ_string(" ");
      print_any(t->cste);
      princ_string("");
      } 
    else if (t->cste > 0)
     { princ_string(" +");
      print_any(t->cste);
      princ_string("");
      } 
    } 
  } 


// constructing a linear term from a list of variables
// claire3 port: building a staticly typed vars list instead of a simple copy
// v1.011 strongly typed lists
/* The c++ function for: scalar(l1:list[integer],l2:list[IntVar]) [] */
LinTerm * claire_scalar_list(list *l1,list *l2)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      { LinTerm * g1242; 
        list * g1243;
        g1242 = _CL_obj;
        { bag *v_list; OID v_val;
          OID i,CLcount;
          v_list = l1;
           g1243 = v_list->clone(Kernel._integer);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { i = (*(v_list))[CLcount];
            v_val = i;
            
            (*((list *) g1243))[CLcount] = v_val;} 
          } 
        (g1242->lcoeff = g1243);} 
      { LinTerm * g1244; 
        list * g1245;
        g1244 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l2;
           g1245 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1245))[CLcount] = v_val;} 
          } 
        (g1244->lvars = g1245);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


//v0.94: ensure a non-destructive behavior
//v0.36:Constructing a linear term from a list of variables
// claire3 port: avoid make_list, v1.011 strongly typed lists
/* The c++ function for: sumVars(l:list[IntVar]) [] */
LinTerm * choco_sumVars_list(list *l)
{ GC_BIND;
  { LinTerm *Result ;
    { LinTerm * _CL_obj = ((LinTerm *) GC_OBJECT(LinTerm,new_object_class(choco._LinTerm)));
      (_CL_obj->lcoeff = make_list_integer2(l->length,Kernel._integer,1));
      { LinTerm * g1246; 
        list * g1247;
        g1246 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g1247 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1247))[CLcount] = v_val;} 
          } 
        (g1246->lvars = g1247);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


//v0.94: ensure a non-destructive behavior
// ********************************************************************
// *   Part 6: global constraints                                     *
// ********************************************************************
// changed v0.28
// claire3 port: building a staticly typed vars lisit instead of a simple copy
/* The c++ function for: allDifferent(l:list[IntVar]) [] */
AllDiff * choco_allDifferent_list(list *l)
{ GC_BIND;
  { AllDiff *Result ;
    { AllDiff * c;
      { { AllDiff * _CL_obj = ((AllDiff *) GC_OBJECT(AllDiff,new_object_class(choco._AllDiff)));
          { LargeIntConstraint * g1248; 
            list * g1249;
            g1248 = _CL_obj;
            { bag *v_list; OID v_val;
              OID v,CLcount;
              v_list = l;
               g1249 = v_list->clone(choco._IntVar);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v = (*(v_list))[CLcount];
                v_val = v;
                
                (*((list *) g1249))[CLcount] = v_val;} 
              } 
            (g1248->vars = g1249);} 
          (_CL_obj->cste = 0);
          c = _CL_obj;
          } 
        GC_OBJECT(AllDiff,c);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 7: adding information                                     *
// ********************************************************************
// v0.9906: rewritten
// v0.9907: merged the definitions from IntConstraint and DelayedConstraint into AbstractConstraint
// v1.010: one single restriction for post@Problem
/* The c++ function for: post(p:Problem,c0:AbstractConstraint) [] */
void  choco_post_Problem(Problem *p,AbstractConstraint *c0)
{ GC_BIND;
  { AbstractConstraint * c = c0;
    if (INHERIT(c0->isa,choco._LinComb))
     { if (CLREAD(ChocEngine,p->propagationEngine,delayLinCombThreshold) <= choco_getNbVars_LinComb(((LinComb *) c0)))
       c= GC_OBJECT(Delayer,choco_delay_IntConstraint(((IntConstraint *) c0),2));
      } 
    else if (INHERIT(c0->isa,choco._Delayer))
     { if (CLREAD(Delayer,c0,priority) <= 0)
       c= GC_OBJECT(IntConstraint,CLREAD(Delayer,c0,target));
      } 
    GC_OBJECT(list,p->constraints)->addFast(_oid_(c));
    (*choco.connect)(_oid_(c));
    { PropagationEngine * pe = GC_OBJECT(PropagationEngine,p->propagationEngine);
      int  prio = (*choco.getPriority)(_oid_(c));
      ConstAwakeEvent * e;
      { { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
          (_CL_obj->touchedConst = c);
          (_CL_obj->initialized = CFALSE);
          (_CL_obj->priority = prio);
          e = _CL_obj;
          } 
        GC_OBJECT(ConstAwakeEvent,e);} 
      (c->constAwakeEvent = e);
      choco_registerEvent_ChocEngine(((ChocEngine *) pe),e);
      (*choco.postConstAwake)(_oid_(pe),
        _oid_(c),
        Kernel.ctrue);
      } 
    } 
  GC_UNBIND;} 


// further constraining a domain: adding information to the current state
// (restricting by hand the domain of a variable)
/* The c++ function for: setMin(v:IntVar,x:integer) [] */
void  choco_setMin_IntVar(IntVar *v,int x)
{ (*choco.updateInf)(_oid_(v),
    x,
    0);
  } 


/* The c++ function for: setMax(v:IntVar,x:integer) [] */
void  choco_setMax_IntVar(IntVar *v,int x)
{ (*choco.updateSup)(_oid_(v),
    x,
    0);
  } 


/* The c++ function for: setVal(v:IntVar,x:integer) [] */
void  choco_setVal_IntVar(IntVar *v,int x)
{ choco_instantiate_IntVar2(v,x,0);
  } 


/* The c++ function for: remVal(v:IntVar,x:integer) [] */
void  choco_remVal_IntVar(IntVar *v,int x)
{ (*choco.removeVal)(_oid_(v),
    x,
    0);
  } 


// v1.02 using the targetStatus slots
// v1.103: same signatures for setBranch
/* The c++ function for: setBranch(d:Disjunction,i:integer,b:boolean) [] */
void  choco_setBranch_Disjunction(Disjunction *d,int i,ClaireBoolean *b)
{ GC_BIND;
  { int  g1250 = d->statusBitVector;
    int  g1251 = (4*(i-1));
    g1250= (g1250+exp2_integer((g1251+2)));
    if ((b == CTRUE) && 
        (!BCONTAIN(d->statusBitVector,((4*(i-1))+3))))
     g1250= (g1250+exp2_integer((g1251+3)));
    else if ((b != CTRUE) && 
        (BCONTAIN(d->statusBitVector,((4*(i-1))+1))))
     g1250= (g1250-exp2_integer((g1251+3)));
    STOREI(d->statusBitVector,g1250);
    ;} 
  if (b == CTRUE)
   { if (i == 1)
     (*choco.awake)(GC_OID(_oid_(d->const1)));
    else (*choco.awake)(GC_OID(_oid_(d->const2)));
      } 
  GC_UNBIND;} 


/* The c++ function for: setBranch(d:LargeDisjunction,i:integer,b:boolean) [] */
void  choco_setBranch_LargeDisjunction(LargeDisjunction *d,int i,ClaireBoolean *b)
{ GC_BIND;
  { int  g1252 = ((i/7)+1);
    int  g1253 = (4*mod_integer((i-1),7));
    list * g1254 = d->statusBitVectorList;
    int  g1255 = (*(g1254))[g1252];
    g1255= (g1255+exp2_integer((g1253+2)));
    { ClaireBoolean * g1260I;
      { ClaireBoolean *v_and;
        { v_and = b;
          if (v_and == CFALSE) g1260I =CFALSE; 
          else { { OID  g1261UU;
              { int  g1256 = ((i/7)+1);
                int  g1257 = (4*mod_integer((i-1),7));
                g1261UU = _oid_(nth_integer((*(d->statusBitVectorList))[g1256],(g1257+3)));
                } 
              v_and = not_any(g1261UU);
              } 
            if (v_and == CFALSE) g1260I =CFALSE; 
            else g1260I = CTRUE;} 
          } 
        } 
      
      if (g1260I == CTRUE) g1255= (g1255+exp2_integer((g1253+3)));
        else { ClaireBoolean * g1262I;
        { ClaireBoolean *v_and;
          { v_and = not_any(_oid_(b));
            if (v_and == CFALSE) g1262I =CFALSE; 
            else { { int  g1258 = ((i/7)+1);
                int  g1259 = (4*mod_integer((i-1),7));
                v_and = nth_integer((*(d->statusBitVectorList))[g1258],(g1259+1));
                } 
              if (v_and == CFALSE) g1262I =CFALSE; 
              else g1262I = CTRUE;} 
            } 
          } 
        
        if (g1262I == CTRUE) g1255= (g1255-exp2_integer((g1253+3)));
          } 
      } 
    STOREI((*d->statusBitVectorList)[g1252],g1255);
    ;} 
  if (b == CTRUE)
   (*choco.awake)((*(d->lconst))[i]);
  GC_UNBIND;} 


// v1.02: no propagation of counterpart for largedisjunctions (see cardinality for this)
/* The c++ function for: setBranch(c:Cardinality,i:integer,b:boolean) [] */
void  choco_setBranch_Cardinality(Cardinality *c,int i,ClaireBoolean *b)
{ GC_BIND;
  { ClaireBoolean * g1275I;
    { OID  g1276UU;
      { int  g1263 = ((i/7)+1);
        int  g1264 = (4*mod_integer((i-1),7));
        g1276UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1263],g1264));
        } 
      g1275I = not_any(g1276UU);
      } 
    
    if (g1275I == CTRUE) { { int  g1265 = ((i/7)+1);
          int  g1266 = (4*mod_integer((i-1),7));
          list * g1267 = GC_OBJECT(list,c->statusBitVectorList);
          int  g1268 = (*(g1267))[g1265];
          g1268= (g1268+exp2_integer(g1266));
          { ClaireBoolean * g1277I;
            { ClaireBoolean *v_and;
              { v_and = b;
                if (v_and == CFALSE) g1277I =CFALSE; 
                else { { OID  g1278UU;
                    { int  g1269 = ((i/7)+1);
                      int  g1270 = (4*mod_integer((i-1),7));
                      g1278UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1269],(g1270+1)));
                      } 
                    v_and = not_any(g1278UU);
                    } 
                  if (v_and == CFALSE) g1277I =CFALSE; 
                  else g1277I = CTRUE;} 
                } 
              } 
            
            if (g1277I == CTRUE) g1268= (g1268+exp2_integer((g1266+1)));
              else { ClaireBoolean * g1279I;
              { ClaireBoolean *v_and;
                { v_and = not_any(_oid_(b));
                  if (v_and == CFALSE) g1279I =CFALSE; 
                  else { { int  g1271 = ((i/7)+1);
                      int  g1272 = (4*mod_integer((i-1),7));
                      v_and = nth_integer((*(c->statusBitVectorList))[g1271],(g1272+1));
                      } 
                    if (v_and == CFALSE) g1279I =CFALSE; 
                    else g1279I = CTRUE;} 
                  } 
                } 
              
              if (g1279I == CTRUE) g1268= (g1268-exp2_integer((g1266+1)));
                } 
            } 
          STOREI((*c->statusBitVectorList)[g1265],g1268);
          ;} 
        if (b == CTRUE)
         { STOREI(c->nbTrueStatus,(c->nbTrueStatus+1));
          (*choco.awake)((*(c->lconst))[i]);
          choco_awakeOnNbTrue_Cardinality(c);
          } 
        else { STOREI(c->nbFalseStatus,(c->nbFalseStatus+1));
            (*choco.awake)((*(c->loppositeConst))[i]);
            choco_awakeOnNbFalse_Cardinality(c);
            } 
          } 
      else { ClaireBoolean * g1280I;
      { OID  g1281UU;
        { int  g1273 = ((i/7)+1);
          int  g1274 = (4*mod_integer((i-1),7));
          g1281UU = _oid_(nth_integer((*(c->statusBitVectorList))[g1273],(g1274+1)));
          } 
        g1280I = ((g1281UU != _oid_(b)) ? CTRUE : CFALSE);
        } 
      
      if (g1280I == CTRUE) choco_raiseContradiction_AbstractConstraint(c);
        } 
    } 
  GC_UNBIND;} 


// v0.9906
/* The c++ function for: propagate(p:Problem) [] */
void  choco_propagate_Problem(Problem *p)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { PropagationEngine * pe = p->propagationEngine;
    ClaireBoolean * someEvents = CTRUE;
    { OID gc_local;
      while ((someEvents == CTRUE))
      { GC_LOOP;
        { OID  qtest = GC_OID(choco.getNextActiveEventQueue->fcall(((int) pe)));
          if (qtest != CNULL)
           { EventCollection * q = OBJECT(EventCollection,qtest);
            _void_(choco.popSomeEvents->fcall(((int) q)));
            } 
          else someEvents= CFALSE;
            } 
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 


// v1.0: controlling the amount of propagation (useful for dynamic constraint posts...)
/* The c++ function for: setActive(c:AbstractConstraint) [] */
void  choco_setActive_AbstractConstraint(AbstractConstraint *c)
{ { AbstractConstraint * rootConstraint = OBJECT(AbstractConstraint,(*(OBJECT(AbstractVar,(*choco.getVar)(_oid_(c),
      1))->constraints))[choco.getConstraintIdx->fcall(((int) c),((int) 1))]);
    if (rootConstraint == c)
     { if (not_any((*choco.isActive)(_oid_(c))) == CTRUE)
       { (*choco.reconnect)(_oid_(c));
        choco_constAwake_AbstractConstraint(c,CTRUE);
        } 
      } 
    else if (INHERIT(rootConstraint->isa,choco._Delayer))
     { if (CLREAD(Delayer,rootConstraint,target) == c)
       choco_setActive_AbstractConstraint(rootConstraint);
      } 
    } 
  } 


/* The c++ function for: setPassive(c:AbstractConstraint) [] */
void  choco_setPassive_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  { AbstractConstraint * rootConstraint = OBJECT(AbstractConstraint,(*(OBJECT(AbstractVar,(*choco.getVar)(_oid_(c),
      1))->constraints))[choco.getConstraintIdx->fcall(((int) c),((int) 1))]);
    if (rootConstraint == c)
     { if ((OBJECT(ClaireBoolean,(*choco.isActive)(_oid_(c)))) == CTRUE)
       { (*choco.disconnect)(_oid_(c));
        { Problem * pb = GC_OBJECT(Problem,choco_getProblem_AbstractConstraint(c));
          ConstAwakeEvent * evt = GC_OBJECT(ConstAwakeEvent,c->constAwakeEvent);
          ConstAwakeEventQueue * q = GC_OBJECT(ConstAwakeEventQueue,choco_getQueue_ChocEngine(GC_OBJECT(ChocEngine,((ChocEngine *) pb->propagationEngine)),evt));
          choco_remove_ConstAwakeEventQueue(q,evt);
          } 
        } 
      } 
    else if (INHERIT(rootConstraint->isa,choco._Delayer))
     { if (CLREAD(Delayer,rootConstraint,target) == c)
       choco_setPassive_AbstractConstraint(rootConstraint);
      } 
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 8: searching for solutions / optimization                 *
// ********************************************************************
/* The c++ function for: getProblem(algo:Solver) [] */
Problem * choco_getProblem_Solver(Solver *algo)
{ return (algo->problem);} 


/* The c++ function for: getSearchManager(b:AbstractBranching) [] */
GlobalSearchSolver * choco_getSearchManager_AbstractBranching(AbstractBranching *b)
{ return (b->manager);} 


// v1.016: new branching heuristic objects
/* The c++ function for: makeMinDomVarHeuristic(_CL_obj:void) [] */
OID  choco_makeMinDomVarHeuristic_void()
{ GC_BIND;
  { OID Result = 0;
    { MinDomain * _CL_obj = ((MinDomain *) GC_OBJECT(MinDomain,new_object_class(choco._MinDomain)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeDomDegVarHeuristic(_CL_obj:void) [] */
OID  choco_makeDomDegVarHeuristic_void()
{ GC_BIND;
  { OID Result = 0;
    { DomDeg * _CL_obj = ((DomDeg *) GC_OBJECT(DomDeg,new_object_class(choco._DomDeg)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeMaxDegVarHeuristic(_CL_obj:void) [] */
OID  choco_makeMaxDegVarHeuristic_void()
{ GC_BIND;
  { OID Result = 0;
    { MaxDeg * _CL_obj = ((MaxDeg *) GC_OBJECT(MaxDeg,new_object_class(choco._MaxDeg)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeStaticVarHeuristic(l:list[IntVar]) [] */
OID  choco_makeStaticVarHeuristic_list(list *l)
{ GC_BIND;
  { OID Result = 0;
    { StaticVarOrder * _CL_obj = ((StaticVarOrder *) GC_OBJECT(StaticVarOrder,new_object_class(choco._StaticVarOrder)));
      { StaticVarOrder * g1282; 
        list * g1283;
        g1282 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g1283 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1283))[CLcount] = v_val;} 
          } 
        (g1282->vars = g1283);} 
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


// new API methods
/* The c++ function for: makeMinDomVarHeuristic(l:list[IntVar]) [] */
MinDomain * choco_makeMinDomVarHeuristic_list(list *l)
{ GC_BIND;
  { MinDomain *Result ;
    { MinDomain * _CL_obj = ((MinDomain *) GC_OBJECT(MinDomain,new_object_class(choco._MinDomain)));
      { MinDomain * g1284; 
        list * g1285;
        g1284 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g1285 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1285))[CLcount] = v_val;} 
          } 
        (g1284->vars = g1285);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeDomDegVarHeuristic(l:list[IntVar]) [] */
DomDeg * choco_makeDomDegVarHeuristic_list(list *l)
{ GC_BIND;
  { DomDeg *Result ;
    { DomDeg * _CL_obj = ((DomDeg *) GC_OBJECT(DomDeg,new_object_class(choco._DomDeg)));
      { DomDeg * g1286; 
        list * g1287;
        g1286 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g1287 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1287))[CLcount] = v_val;} 
          } 
        (g1286->vars = g1287);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeMaxDegVarHeuristic(l:list[IntVar]) [] */
MaxDeg * choco_makeMaxDegVarHeuristic_list(list *l)
{ GC_BIND;
  { MaxDeg *Result ;
    { MaxDeg * _CL_obj = ((MaxDeg *) GC_OBJECT(MaxDeg,new_object_class(choco._MaxDeg)));
      { MaxDeg * g1288; 
        list * g1289;
        g1288 = _CL_obj;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l;
           g1289 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g1289))[CLcount] = v_val;} 
          } 
        (g1288->vars = g1289);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeIncValIterator(_CL_obj:void) [] */
OID  choco_makeIncValIterator_void()
{ GC_BIND;
  { OID Result = 0;
    { IncreasingDomain * _CL_obj = ((IncreasingDomain *) GC_OBJECT(IncreasingDomain,new_object_class(choco._IncreasingDomain)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeDecValIterator(_CL_obj:void) [] */
OID  choco_makeDecValIterator_void()
{ GC_BIND;
  { OID Result = 0;
    { DecreasingDomain * _CL_obj = ((DecreasingDomain *) GC_OBJECT(DecreasingDomain,new_object_class(choco._DecreasingDomain)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeMinValSelector(_CL_obj:void) [] */
OID  choco_makeMinValSelector_void()
{ GC_BIND;
  { OID Result = 0;
    { MinValHeuristic * _CL_obj = ((MinValHeuristic *) GC_OBJECT(MinValHeuristic,new_object_class(choco._MinValHeuristic)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeMaxValSelector(_CL_obj:void) [] */
OID  choco_makeMaxValSelector_void()
{ GC_BIND;
  { OID Result = 0;
    { MaxValHeuristic * _CL_obj = ((MaxValHeuristic *) GC_OBJECT(MaxValHeuristic,new_object_class(choco._MaxValHeuristic)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeMidValSelector(_CL_obj:void) [] */
OID  choco_makeMidValSelector_void()
{ GC_BIND;
  { OID Result = 0;
    { MidValHeuristic * _CL_obj = ((MidValHeuristic *) GC_OBJECT(MidValHeuristic,new_object_class(choco._MidValHeuristic)));
      Result = _oid_(_CL_obj);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeAssignVarBranching(varh:VarSelector,valh:ValIterator) [] */
AssignVar * choco_makeAssignVarBranching_VarSelector1(VarSelector *varh,ValIterator *valh)
{ GC_BIND;
  { AssignVar *Result ;
    { AssignVar * av;
      { { AssignVar * _CL_obj = ((AssignVar *) GC_OBJECT(AssignVar,new_object_class(choco._AssignVar)));
          (_CL_obj->varHeuristic = varh);
          (_CL_obj->valHeuristic = valh);
          av = _CL_obj;
          } 
        GC_OBJECT(AssignVar,av);} 
      (varh->branching = av);
      (valh->branching = av);
      Result = av;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeAssignVarBranching(varh:VarSelector) [] */
AssignVar * choco_makeAssignVarBranching_VarSelector2(VarSelector *varh)
{ GC_BIND;
  { AssignVar *Result ;
    Result = choco_makeAssignVarBranching_VarSelector1(varh,GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeAssignVarBranching(_CL_obj:void) [] */
AssignVar * choco_makeAssignVarBranching_void()
{ GC_BIND;
  { AssignVar *Result ;
    Result = choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSplitDomBranching(varh:VarSelector,valh:ValSelector,threshold:integer) [] */
SplitDomain * choco_makeSplitDomBranching_VarSelector1(VarSelector *varh,ValSelector *valh,int threshold)
{ GC_BIND;
  { SplitDomain *Result ;
    { SplitDomain * sd;
      { { SplitDomain * _CL_obj = ((SplitDomain *) GC_OBJECT(SplitDomain,new_object_class(choco._SplitDomain)));
          (_CL_obj->varHeuristic = varh);
          (_CL_obj->valHeuristic = valh);
          (_CL_obj->dichotomyThreshold = threshold);
          sd = _CL_obj;
          } 
        GC_OBJECT(SplitDomain,sd);} 
      (varh->branching = sd);
      (valh->branching = sd);
      Result = sd;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSplitDomBranching(varh:VarSelector,threshold:integer) [] */
SplitDomain * choco_makeSplitDomBranching_VarSelector2(VarSelector *varh,int threshold)
{ GC_BIND;
  { SplitDomain *Result ;
    Result = choco_makeSplitDomBranching_VarSelector1(varh,GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),threshold);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSplitDomBranching(varh:VarSelector) [] */
SplitDomain * choco_makeSplitDomBranching_VarSelector3(VarSelector *varh)
{ GC_BIND;
  { SplitDomain *Result ;
    Result = choco_makeSplitDomBranching_VarSelector1(varh,GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeSplitDomBranching(_CL_obj:void) [] */
SplitDomain * choco_makeSplitDomBranching_void()
{ GC_BIND;
  { SplitDomain *Result ;
    Result = choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeAssignOrForbidBranching(varh:VarSelector,valh:ValSelector) [] */
AssignOrForbid * choco_makeAssignOrForbidBranching_VarSelector(VarSelector *varh,ValSelector *valh)
{ GC_BIND;
  { AssignOrForbid *Result ;
    { AssignOrForbid * af;
      { { AssignOrForbid * _CL_obj = ((AssignOrForbid *) GC_OBJECT(AssignOrForbid,new_object_class(choco._AssignOrForbid)));
          (_CL_obj->varHeuristic = varh);
          (_CL_obj->valHeuristic = valh);
          af = _CL_obj;
          } 
        GC_OBJECT(AssignOrForbid,af);} 
      (varh->branching = af);
      (valh->branching = af);
      Result = af;
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.010 building branching objects
/* The c++ function for: makeDisjunctionBranching(pb:Problem) [] */
SettleBinDisjunction * choco_makeDisjunctionBranching_Problem(Problem *pb)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { SettleBinDisjunction *Result ;
    { list * ldisj = list::empty(choco._Disjunction);
      { OID gc_local;
        ITERATE(c);
        bag *c_support;
        c_support = GC_OBJECT(list,pb->constraints);
        for (START(c_support); NEXT(c);)
        { GC_LOOP;
          if (INHERIT(OBJECT(ClaireObject,c)->isa,choco._Disjunction))
           GC__ANY(ldisj = ldisj->addFast(c), 1);
          GC_UNLOOP;} 
        } 
      { SettleBinDisjunction * _CL_obj = ((SettleBinDisjunction *) GC_OBJECT(SettleBinDisjunction,new_object_class(choco._SettleBinDisjunction)));
        (_CL_obj->disjunctions = ldisj);
        Result = _CL_obj;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


//  the default standard procedure in Choco:
//  first settle suspended disjunctions, then split domains, then instantiate
/* The c++ function for: makeDefaultBranchingList(pb:Problem) [] */
list * choco_makeDefaultBranchingList_Problem(Problem *pb)
{ GC_BIND;
  { list *Result ;
    Result = list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(pb))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void()))))));
    GC_UNBIND; return (Result);} 
  } 


// v1.0 new API methods
/* The c++ function for: makeGlobalSearchSolver(allSolutions:boolean,l:list[AbstractBranching]) [] */
Solve * choco_makeGlobalSearchSolver_boolean1(ClaireBoolean *allSolutions,list *l)
{ GC_BIND;
  { Solve *Result ;
    { GlobalSearchSolver *V_CC ;
      { Solve * g1290UU;
        { Solve * _CL_obj = ((Solve *) GC_OBJECT(Solve,new_object_class(choco._Solve)));
          (_CL_obj->stopAtFirstSol = not_any(_oid_(allSolutions)));
          g1290UU = _CL_obj;
          } 
        V_CC = choco_composeGlobalSearch_GlobalSearchSolver(g1290UU,l);
        } 
      Result= (Solve *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 initialize the stopAtFirstSol slot
/* The c++ function for: makeGlobalSearchMaximizer(obj:IntVar,restartSearch:boolean,l:list[AbstractBranching]) [] */
AbstractOptimize * choco_makeGlobalSearchMaximizer_IntVar1(IntVar *obj,ClaireBoolean *restartSearch,list *l)
{ GC_BIND;
  { AbstractOptimize *Result ;
    { GlobalSearchSolver *V_CC ;
      if (restartSearch == CTRUE)
       { OptimizeWithRestarts * g1291UU;
        { OptimizeWithRestarts * _CL_obj = ((OptimizeWithRestarts *) GC_OBJECT(OptimizeWithRestarts,new_object_class(choco._OptimizeWithRestarts)));
          (_CL_obj->objective = obj);
          (_CL_obj->doMaximize = CTRUE);
          (_CL_obj->stopAtFirstSol = CTRUE);
          g1291UU = _CL_obj;
          } 
        V_CC = choco_composeGlobalSearch_GlobalSearchSolver(g1291UU,l);
        } 
      else { BranchAndBound * g1292UU;
          { BranchAndBound * _CL_obj = ((BranchAndBound *) GC_OBJECT(BranchAndBound,new_object_class(choco._BranchAndBound)));
            (_CL_obj->objective = obj);
            (_CL_obj->doMaximize = CTRUE);
            (_CL_obj->stopAtFirstSol = CFALSE);
            g1292UU = _CL_obj;
            } 
          V_CC = choco_composeGlobalSearch_GlobalSearchSolver(g1292UU,l);
          } 
        Result= (AbstractOptimize *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// v1.02 initialize the stopAtFirstSol slot
/* The c++ function for: makeGlobalSearchMinimizer(obj:IntVar,restartSearch:boolean,l:list[AbstractBranching]) [] */
AbstractOptimize * choco_makeGlobalSearchMinimizer_IntVar1(IntVar *obj,ClaireBoolean *restartSearch,list *l)
{ GC_BIND;
  { AbstractOptimize *Result ;
    { GlobalSearchSolver *V_CC ;
      if (restartSearch == CTRUE)
       { OptimizeWithRestarts * g1293UU;
        { OptimizeWithRestarts * _CL_obj = ((OptimizeWithRestarts *) GC_OBJECT(OptimizeWithRestarts,new_object_class(choco._OptimizeWithRestarts)));
          (_CL_obj->objective = obj);
          (_CL_obj->doMaximize = CFALSE);
          (_CL_obj->stopAtFirstSol = CTRUE);
          g1293UU = _CL_obj;
          } 
        V_CC = choco_composeGlobalSearch_GlobalSearchSolver(g1293UU,l);
        } 
      else { BranchAndBound * g1294UU;
          { BranchAndBound * _CL_obj = ((BranchAndBound *) GC_OBJECT(BranchAndBound,new_object_class(choco._BranchAndBound)));
            (_CL_obj->objective = obj);
            (_CL_obj->doMaximize = CFALSE);
            (_CL_obj->stopAtFirstSol = CFALSE);
            g1294UU = _CL_obj;
            } 
          V_CC = choco_composeGlobalSearch_GlobalSearchSolver(g1294UU,l);
          } 
        Result= (AbstractOptimize *) V_CC;} 
    GC_UNBIND; return (Result);} 
  } 


// default methods
// v1.010: add the Problem argument
/* The c++ function for: makeGlobalSearchSolver(allSolutions:boolean) [] */
Solve * choco_makeGlobalSearchSolver_boolean2(ClaireBoolean *allSolutions)
{ GC_BIND;
  { Solve *Result ;
    Result = choco_makeGlobalSearchSolver_boolean1(allSolutions,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(choco_getActiveProblem_void()))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeGlobalSearchMaximizer(obj:IntVar,restartSearch:boolean) [] */
AbstractOptimize * choco_makeGlobalSearchMaximizer_IntVar2(IntVar *obj,ClaireBoolean *restartSearch)
{ GC_BIND;
  { AbstractOptimize *Result ;
    Result = choco_makeGlobalSearchMaximizer_IntVar1(obj,restartSearch,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(choco_getActiveProblem_void()))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeGlobalSearchMinimizer(obj:IntVar,restartSearch:boolean) [] */
AbstractOptimize * choco_makeGlobalSearchMinimizer_IntVar2(IntVar *obj,ClaireBoolean *restartSearch)
{ GC_BIND;
  { AbstractOptimize *Result ;
    Result = choco_makeGlobalSearchMinimizer_IntVar1(obj,restartSearch,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(choco_getActiveProblem_void()))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: makeBacktrackLimit(nb:integer) [] */
NodeLimit * choco_makeBacktrackLimit_integer(int nb)
{ return (((NodeLimit *) (*choco._NodeLimit)(nb)));} 


/* The c++ function for: makeNodeLimit(nb:integer) [] */
BacktrackLimit * choco_makeNodeLimit_integer(int nb)
{ return (((BacktrackLimit *) (*choco._BacktrackLimit)(nb)));} 


/* The c++ function for: setSearchLimit(algo:GlobalSearchSolver,lim:GlobalSearchLimit) [] */
void  choco_setSearchLimit_GlobalSearchSolver(GlobalSearchSolver *algo,GlobalSearchLimit *lim)
{ GC_BIND;
  GC_OBJECT(list,algo->limits)->addFast(_oid_(lim));
  GC_UNBIND;} 


// -- start: deprecated API functions
// API functions for the default search procedure
/* The c++ function for: solve(pb:Problem,all:boolean) [] */
ClaireBoolean * choco_solve_Problem1(Problem *pb,ClaireBoolean *all)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = choco_solve_Problem2(pb,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(pb))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))),all);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: minimize(pb:Problem,obj:IntVar,restart:boolean) [] */
int  choco_minimize_Problem1(Problem *pb,IntVar *obj,ClaireBoolean *restart)
{ GC_BIND;
  { int Result = 0;
    Result = choco_minimize_Problem2(pb,obj,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(pb))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))),restart);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: maximize(pb:Problem,obj:IntVar,restart:boolean) [] */
int  choco_maximize_Problem1(Problem *pb,IntVar *obj,ClaireBoolean *restart)
{ GC_BIND;
  { int Result = 0;
    Result = choco_maximize_Problem2(pb,obj,list::alloc(choco._AbstractBranching,3,GC_OID(_oid_(choco_makeDisjunctionBranching_Problem(pb))),
      GC_OID(_oid_(choco_makeSplitDomBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValSelector,OBJECT(ValSelector,choco_makeMidValSelector_void())),5))),
      GC_OID(_oid_(choco_makeAssignVarBranching_VarSelector1(GC_OBJECT(VarSelector,OBJECT(VarSelector,choco_makeMinDomVarHeuristic_void())),GC_OBJECT(ValIterator,OBJECT(ValIterator,choco_makeIncValIterator_void())))))),restart);
    GC_UNBIND; return (Result);} 
  } 


// New API functions
// v1.08 attachGlobalSearchSolver -> attach
/* The c++ function for: solve(pb:Problem,l:list[AbstractBranching],all:boolean) [] */
ClaireBoolean * choco_solve_Problem2(Problem *pb,list *l,ClaireBoolean *all)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { Solve * algo = GC_OBJECT(Solve,choco_makeGlobalSearchSolver_boolean1(all,l));
      choco_attach_GlobalSearchSolver(algo,pb);
      choco_run_Solve(algo);
      Result = choco_getFeasibility_GlobalSearchSolver(algo);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02: explicit access to feasibility
// v1.05 <thb> getBestObjectiveValue
/* The c++ function for: minimize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) [] */
int  choco_minimize_Problem2(Problem *pb,IntVar *obj,list *l,ClaireBoolean *restart)
{ GC_BIND;
  { int Result = 0;
    { AbstractOptimize * algo = GC_OBJECT(AbstractOptimize,choco_makeGlobalSearchMinimizer_IntVar1(obj,restart,l));
      choco_attach_GlobalSearchSolver(algo,pb);
      (*choco.run)(_oid_(algo));
      Result = choco_getBestObjectiveValue_AbstractOptimize(algo);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02: explicit access to obj. value
/* The c++ function for: maximize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) [] */
int  choco_maximize_Problem2(Problem *pb,IntVar *obj,list *l,ClaireBoolean *restart)
{ GC_BIND;
  { int Result = 0;
    { AbstractOptimize * algo = GC_OBJECT(AbstractOptimize,choco_makeGlobalSearchMaximizer_IntVar1(obj,restart,l));
      choco_attach_GlobalSearchSolver(algo,pb);
      (*choco.run)(_oid_(algo));
      Result = choco_getBestObjectiveValue_AbstractOptimize(algo);
      } 
    GC_UNBIND; return (Result);} 
  } 


// v1.02: explicit access to obj. value
// -- end: deprecated API functions
// controlling the search
// v1.08 these functions no longer apply to Problem, but to GlobalSearchSolver
/* The c++ function for: setMaxPrintDepth(algo:GlobalSearchSolver,n:integer) [] */
void  choco_setMaxPrintDepth_GlobalSearchSolver(GlobalSearchSolver *algo,int n)
{ (algo->maxPrintDepth = n);
  } 


// v1.013 new API function
/* The c++ function for: setMaxSolutionStorage(algo:GlobalSearchSolver,nb:integer) [] */
void  choco_setMaxSolutionStorage_GlobalSearchSolver(GlobalSearchSolver *algo,int nb)
{ (algo->maxSolutionStorage = nb);
  } 


/* The c++ function for: setVarsToShow(algo:GlobalSearchSolver,l:list[IntVar]) [] */
void  choco_setVarsToShow_GlobalSearchSolver(GlobalSearchSolver *algo,list *l)
{ (algo->varsToShow = ((set *) copy_bag(l)));
  } 


/* The c++ function for: getNbSol(algo:GlobalSearchSolver) [] */
int  choco_getNbSol_GlobalSearchSolver(GlobalSearchSolver *algo)
{ return (algo->nbSol);} 


/* The c++ function for: getProblem(b:AbstractBranching) [] */
Problem * choco_getProblem_AbstractBranching(AbstractBranching *b)
{ return (b->manager->problem);} 


// v1.322 new API method
/* The c++ function for: getGlobalSearchSolver(p:Problem) [] */
GlobalSearchSolver * choco_getGlobalSearchSolver_Problem(Problem *p)
{ return (p->globalSearchSolver);} 


// -------- new from v1.020: introducing limit objects
/* The c++ function for: setNodeLimit(algo:GlobalSearchSolver,n:integer) [] */
void  choco_setNodeLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n)
{ GC_BIND;
  GC_OBJECT(list,algo->limits)->addFast(GC_OID(_oid_(((NodeLimit *) (*choco._NodeLimit)(n)))));
  GC_UNBIND;} 


/* The c++ function for: setTimeLimit(algo:GlobalSearchSolver,n:integer) [] */
void  choco_setTimeLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n)
{ GC_BIND;
  GC_OBJECT(list,algo->limits)->addFast(GC_OID(_oid_(((TimeLimit *) (*choco._TimeLimit)(n)))));
  GC_UNBIND;} 


/* The c++ function for: setBacktrackLimit(algo:GlobalSearchSolver,n:integer) [] */
void  choco_setBacktrackLimit_GlobalSearchSolver(GlobalSearchSolver *algo,int n)
{ GC_BIND;
  GC_OBJECT(list,algo->limits)->addFast(GC_OID(_oid_(((BacktrackLimit *) (*choco._BacktrackLimit)(n)))));
  GC_UNBIND;} 


// deprecated API: mentionned for upward compatibility 
/* The c++ function for: setMaxNbBk(algo:GlobalSearchSolver,n:integer) [] */
void  choco_setMaxNbBk_GlobalSearchSolver(GlobalSearchSolver *algo,int n)
{ choco_setBacktrackLimit_GlobalSearchSolver(algo,n);
  } 


// ********************************************************************
// *   Part 9: searching for solutions / (assign & repair) local opt. *
// ********************************************************************
// API functions -> delegate work to the "invariant maintainer"
// Three new events:
/* The c++ function for: assignVal(v:IntVar,a:integer) [] */
void  choco_assignVal_IntVar(IntVar *v,int a)
{ GC_BIND;
  ;if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v))))) == CTRUE)
   { int  val = v->value;
    if (val != a)
     close_exception(((general_error *) (*Core._general_error)(_string_("wrong event -assign- for a change of value assignment on ~S"),
      _oid_(list::alloc(1,_oid_(v))))));
    } 
  else { STOREI(v->value,a);
      choco_postAssignVal_ConflictCount(GC_OBJECT(ConflictCount,((ConflictCount *) v->problem->invariantEngine)),v,a);
      } 
    GC_UNBIND;} 


/* The c++ function for: unassignVal(v:IntVar) [] */
void  choco_unassignVal_IntVar(IntVar *v)
{ GC_BIND;
  ;if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v))))) == CTRUE)
   { choco_postUnAssignVal_ConflictCount(GC_OBJECT(ConflictCount,((ConflictCount *) v->problem->invariantEngine)),v);
    STOREI(v->value,-100000000);
    } 
  GC_UNBIND;} 


/* The c++ function for: changeVal(v:IntVar,b:integer) [] */
void  choco_changeVal_IntVar(IntVar *v,int b)
{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) v))))) == CTRUE) 
  { { int  a = v->value;
      if (b != a)
       { choco_postChangeVal_ConflictCount(GC_OBJECT(ConflictCount,((ConflictCount *) v->problem->invariantEngine)),v,a,b);
        STOREI(v->value,b);
        } 
      } 
     } 
  else{ GC_BIND;
    close_exception(((general_error *) (*Core._general_error)(_string_("can not change the value of an unassigned variable :~S"),
      _oid_(list::alloc(1,_oid_(v))))));
    GC_UNBIND;} 
  } 


// controlling the search
/* The c++ function for: setMaxNbLocalSearch(pb:Problem,n:integer) [] */
void  choco_setMaxNbLocalSearch_Problem(Problem *pb,int n)
{ (pb->localSearchSolver->maxNbLocalSearch = n);
  } 


/* The c++ function for: setMaxNbLocalMoves(pb:Problem,n:integer) [] */
void  choco_setMaxNbLocalMoves_Problem(Problem *pb,int n)
{ (pb->localSearchSolver->maxNbLocalMoves = n);
  } 


/* The c++ function for: move(pb:Problem) [] */
int  choco_move_Problem1(Problem *pb)
{ GC_BIND;
  { int Result = 0;
    { AssignmentHeuristic * g1295UU;
      { AssignmentHeuristic * _CL_obj = ((AssignmentHeuristic *) GC_OBJECT(AssignmentHeuristic,new_object_class(choco._AssignmentHeuristic)));
        g1295UU = _CL_obj;
        } 
      FlipNeighborhood * g1296UU;
      { FlipNeighborhood * _CL_obj = ((FlipNeighborhood *) GC_OBJECT(FlipNeighborhood,new_object_class(choco._FlipNeighborhood)));
        g1296UU = _CL_obj;
        } 
      Result = choco_move_Problem2(pb,g1295UU,g1296UU);
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: move(pb:Problem,b:ConstructiveHeuristic,m:MoveNeighborhood) [] */
int  choco_move_Problem2(Problem *pb,ConstructiveHeuristic *b,MoveNeighborhood *m)
{ GC_BIND;
  { int Result = 0;
    { MultipleDescent * algo;
      { { MultipleDescent * _CL_obj = ((MultipleDescent *) GC_OBJECT(MultipleDescent,new_object_class(choco._MultipleDescent)));
          (_CL_obj->buildAssignment = b);
          (_CL_obj->changeAssignment = m);
          algo = _CL_obj;
          } 
        GC_OBJECT(MultipleDescent,algo);} 
      (m->manager = algo);
      (b->manager = algo);
      choco_attachLocalSearchSolver_Problem(pb,algo);
      choco_runLocalSearch_LocalSearchSolver(algo);
      Result = (*choco.getLocalSearchObjectiveValue)(GC_OID(_oid_(pb->invariantEngine)));
      } 
    GC_UNBIND; return (Result);} 
  } 

