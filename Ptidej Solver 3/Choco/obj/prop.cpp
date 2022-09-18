/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\prop.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:25 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: iprop.cl                                                   *
// *    propagation of events of integer valued variables             *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: Propagation events                                     *
// *   Part 2: Propagation event queues                               *
// *   Part 3: from event generation to event post (to a prop. engine)*
// *   Part 4: from event generation to event post (SetVars)          *
// *   Part 4: utils for the queue storing INCINF/DECSUP events       *
// *   Part 5: posting an event to the queue                          *
// *   Part 6: retrieving an event & wakening the constraint          *
// *   Part 7: global constraints layered propagation                 *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: Propagation events                                     *
// ********************************************************************
// v0.91 propagation events
// abstract class for propagation events on either constraints or variables 
// An abstract class for all events related to a domain variable
// index pointers coding a chained sublist of v.constraints
// those constraints that should be waken upon handling the event
// index of the constraint that caused the event
/* The c++ function for: self_print(e:Instantiation) [] */
void  claire_self_print_Instantiation_choco(Instantiation *e)
{ princ_string("DUMMY_INST");
  } 


// instantiating an integer variable
/* The c++ function for: self_print(e:InstInt) [] */
void  claire_self_print_InstInt_choco(InstInt *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("INST(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(CLREAD(IntVar,v,value));
    princ_string("[c:");
    print_any(e->cause);
    princ_string("]");
    } 
  GC_UNBIND;} 


// instantiating a set variable
/* The c++ function for: self_print(e:InstSet) [] */
void  claire_self_print_InstSet_choco(InstSet *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("INST SET(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(GC_OID(_oid_(choco_getDomainKernel_SetVar(((SetVar *) v)))));
    princ_string("[c:");
    print_any(e->cause);
    princ_string("]");
    } 
  GC_UNBIND;} 


// updates to domains bouns of a variabl (inf/sup for integers; kernel/enveloppe for sets)
// index in the queue of all pending events
/* The c++ function for: self_print(e:BoundUpdate) [] */
void  claire_self_print_BoundUpdate_choco(BoundUpdate *e)
{ princ_string("DUMMY-BOUND");
  } 


// --- Bound updates for integer variables
// increasing the integer lower bound
/* The c++ function for: self_print(e:IncInf) [] */
void  claire_self_print_IncInf_choco(IncInf *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("INF(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(CLREAD(IntVar,v,inf)->latestValue);
    princ_string("[c:");
    print_any(e->cause);
    princ_string("][i:");
    print_any(e->idxInQueue);
    princ_string("]");
    } 
  GC_UNBIND;} 


// decreasing the integer upper bound
/* The c++ function for: self_print(e:DecSup) [] */
void  claire_self_print_DecSup_choco(DecSup *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("SUP(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(CLREAD(IntVar,v,sup)->latestValue);
    princ_string("[c:");
    print_any(e->cause);
    princ_string("][i:");
    print_any(e->idxInQueue);
    princ_string("]");
    } 
  GC_UNBIND;} 


// --- Bound updates for set variables
// increasing the set lower bound
/* The c++ function for: self_print(e:IncKer) [] */
void  claire_self_print_IncKer_choco(IncKer *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("KER(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(GC_OID(_oid_(choco_getDomainKernel_SetVar(((SetVar *) v)))));
    princ_string("[c:");
    print_any(e->cause);
    princ_string("][i:");
    print_any(e->idxInQueue);
    princ_string("]");
    } 
  GC_UNBIND;} 


// decreasing the set upper bound
/* The c++ function for: self_print(e:DecEnv) [] */
void  claire_self_print_DecEnv_choco(DecEnv *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    princ_string("ENV(");
    print_any(_oid_(v));
    princ_string("):");
    print_any(GC_OID(_oid_(choco_getDomainEnveloppe_SetVar(((SetVar *) v)))));
    princ_string("[c:");
    print_any(e->cause);
    princ_string("][i:");
    print_any(e->idxInQueue);
    princ_string("]");
    } 
  GC_UNBIND;} 


// --- Removing values from the domain of integer variables
// index in the queue of all pending ValueRemoval events
//    (0 in case the event is absent from the queue)
/* The c++ function for: self_print(e:ValueRemovals) [] */
void  claire_self_print_ValueRemovals_choco(ValueRemovals *e)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    if (v == (NULL))
     princ_string("DUMMY-REM");
    else if (e->many == CTRUE)
     { princ_string("MANYREMS(");
      print_any(_oid_(v));
      princ_string(")[c:");
      print_any((*(e->causeStack))[1]);
      princ_string("][i:");
      print_any(e->idxInQueue);
      princ_string("]");
      } 
    else { princ_string("REM(");
        print_any(_oid_(v));
        princ_string(")[");
        { int  i = 1;
          int  g0052 = e->nbVals;
          { OID gc_local;
            while ((i <= g0052))
            { // HOHO, GC_LOOP not needed !
              if (i > 1)
               princ_string(", ");
              print_any((*(e->valueStack))[i]);
              princ_string("[");
              print_any((*(e->causeStack))[i]);
              princ_string("]");
              ++i;
              } 
            } 
          } 
        princ_string("][i:");
        print_any(e->idxInQueue);
        princ_string("]");
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: self_print(e:ConstAwakeEvent) [] */
void  claire_self_print_ConstAwakeEvent_choco(ConstAwakeEvent *e)
{ GC_BIND;
  { AbstractConstraint * c = GC_OBJECT(AbstractConstraint,e->touchedConst);
    if (c == (NULL))
     princ_string("DUMMY-AWAKE");
    else if (e->initialized == CTRUE)
     { princ_string("AWAKE(");
      print_any(_oid_(c));
      princ_string(")");
      } 
    else { princ_string("INIT(");
        print_any(_oid_(c));
        princ_string(")");
        } 
      } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 2: Propagation event queues                               *
// ********************************************************************
// the generic abstract class for storing sets of events
// an abstract subclass for storing events in a (cyclic) fifo queue
// instances q of subclasses from this class have the following invariant
//   (q.qLastRead != q.qLastEnqueued)
//        ==> forall i in (q.qLastRead .. q.qLastEnqueued) q.variableQueue != unknown
// 0.9907 <naren> are we currently popping events from the queue ?
// contains triples (awakeOnInf/awakeOnSup, v, idx1)
// handling a triple (awakeOnInf, v, i) leads to calling
//   awakeOnIncInf(v, x) for all x in ((1 .. v.nbConstraints) but i)
// v0.9906: the redundantEvent flag replaced the propagationCycle exception:
// it is set to true in case the event being popped generated a new event similar to itself
// instances q of this class have the following invariants
//   forall i in (1 .. q.qsize)
//      q.eventQueue[i] = REMVALS ==> (q.nbVals[i] = 1 & q.valuesStack[i] = 0)
//      q.eventQueue[i] = REMVAL
//           ==> forall j1,j2 in (1 .. q.nbVals[i]), j1 != j2,
//                      q.valuesStack[i][j1] != q.valuesStack[i][j2]
// <thb> v1.02: like BoundEvents, RemovalEvent can sometimes be redundant
//   (the event being popped may generated a new event similar to itself)
// unlike the two previous queues, instantiation will be trailed (backtracked)
// TODO: could be storedInt instead of integers ?.....
// v0.9906
// v1.013: minimal number of variables involved in a constraint in order
// for linear constraints to be delayed (propagated in a constraint event loop)
// ********************************************************************
// *   Part 4: utils for the queue storing INCINF/DECSUP events       *
// ********************************************************************
// may be enriched for debugging purposes
// v1.0: internal methods of the propagation engine for handling contradictions
// port to claire 3 add the void range for all three methods
/* The c++ function for: raiseContradiction(pe:PropagationEngine) [] */
void  choco_raiseContradiction_PropagationEngine1(PropagationEngine *pe)
{ GC_BIND;
  (pe->contradictionCause = NULL);
  choco_flushEventQueue_BoundEventQueue(GC_OBJECT(BoundEventQueue,CLREAD(ChocEngine,pe,boundEvtQueue)));
  choco_flushEventQueue_RemovalEventQueue(GC_OBJECT(RemovalEventQueue,CLREAD(ChocEngine,pe,removalEvtQueue)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst1)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst2)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst3)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst4)));
  (CLREAD(ChocEngine,pe,nbPendingInitConstAwakeEvent) = 0);
  (CLREAD(ChocEngine,pe,nbPendingVarEvent) = 0);
  ;contradiction_I_void();
  GC_UNBIND;} 


/* The c++ function for: raiseContradiction(pe:PropagationEngine,x:Ephemeral) [] */
void  choco_raiseContradiction_PropagationEngine2(PropagationEngine *pe,Ephemeral *x)
{ GC_BIND;
  (pe->contradictionCause = x);
  choco_flushEventQueue_BoundEventQueue(GC_OBJECT(BoundEventQueue,CLREAD(ChocEngine,pe,boundEvtQueue)));
  choco_flushEventQueue_RemovalEventQueue(GC_OBJECT(RemovalEventQueue,CLREAD(ChocEngine,pe,removalEvtQueue)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst1)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst2)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst3)));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,CLREAD(ChocEngine,pe,delayedConst4)));
  (CLREAD(ChocEngine,pe,nbPendingInitConstAwakeEvent) = 0);
  (CLREAD(ChocEngine,pe,nbPendingVarEvent) = 0);
  ;contradiction_I_void();
  GC_UNBIND;} 


// retrieving the cause of the last contradiction
/* The c++ function for: getContradictionCause(pe:PropagationEngine) [] */
Ephemeral * choco_getContradictionCause_PropagationEngine(PropagationEngine *pe)
{ return (pe->contradictionCause);} 


// v1.0 public methods for generating a contradiction
// port to claire 3 add the void range for all three methods
// v1.010: use getActiveProblem()
/* The c++ function for: raiseContradiction(v:AbstractVar) [] */
void  choco_raiseContradiction_AbstractVar(AbstractVar *v)
{ GC_BIND;
  choco_raiseContradiction_PropagationEngine2(GC_OBJECT(PropagationEngine,choco_getActiveProblem_void()->propagationEngine),v);
  GC_UNBIND;} 


/* The c++ function for: raiseContradiction(c:AbstractConstraint) [] */
void  choco_raiseContradiction_AbstractConstraint(AbstractConstraint *c)
{ GC_BIND;
  choco_raiseContradiction_PropagationEngine2(GC_OBJECT(PropagationEngine,choco_getProblem_AbstractConstraint(c)->propagationEngine),c);
  GC_UNBIND;} 


/* The c++ function for: raiseContradiction(pb:Problem) [] */
void  choco_raiseContradiction_Problem(Problem *pb)
{ GC_BIND;
  choco_raiseContradiction_PropagationEngine1(GC_OBJECT(PropagationEngine,pb->propagationEngine));
  GC_UNBIND;} 


// v0.9907
// claire3 port: strongly typed lists
/* The c++ function for: makeChocEngine(n:integer) [] */
ChocEngine * choco_makeChocEngine_integer(int n)
{ GC_BIND;
  { ChocEngine *Result ;
    { int  m = ((2*n)+2);
      ChocEngine * pe;
      { { ChocEngine * _CL_obj = ((ChocEngine *) GC_OBJECT(ChocEngine,new_object_class(choco._ChocEngine)));
          (_CL_obj->maxSize = n);
          pe = _CL_obj;
          } 
        GC_OBJECT(ChocEngine,pe);} 
      { ChocEngine * g0056; 
        BoundEventQueue * g0057;
        g0056 = pe;
        { BoundEventQueue * _CL_obj = ((BoundEventQueue *) GC_OBJECT(BoundEventQueue,new_object_class(choco._BoundEventQueue)));
          (_CL_obj->qsize = m);
          (_CL_obj->engine = pe);
          { BoundEventQueue * g0058; 
            list * g0059;
            g0058 = _CL_obj;
            { list * i_bag = list::empty(choco._BoundUpdate);
              { int  i = 1;
                int  g0053 = m;
                { OID gc_local;
                  while ((i <= g0053))
                  { GC_LOOP;
                    { OID  g0060UU;
                      { BoundUpdate * _CL_obj = ((BoundUpdate *) GC_OBJECT(BoundUpdate,new_object_class(choco._BoundUpdate)));
                        g0060UU = _oid_(_CL_obj);
                        } 
                      i_bag->addFast(g0060UU);
                      } 
                    ++i;
                    GC_UNLOOP;} 
                  } 
                } 
              g0059 = GC_OBJECT(list,i_bag);
              } 
            (g0058->eventQueue = g0059);} 
          (_CL_obj->qLastRead = m);
          (_CL_obj->qLastEnqueued = m);
          g0057 = _CL_obj;
          } 
        (g0056->boundEvtQueue = g0057);} 
      { ChocEngine * g0061; 
        RemovalEventQueue * g0062;
        g0061 = pe;
        { RemovalEventQueue * _CL_obj = ((RemovalEventQueue *) GC_OBJECT(RemovalEventQueue,new_object_class(choco._RemovalEventQueue)));
          (_CL_obj->qsize = (n+1));
          (_CL_obj->engine = pe);
          { RemovalEventQueue * g0063; 
            list * g0064;
            g0063 = _CL_obj;
            { list * i_bag = list::empty(choco._ValueRemovals);
              { int  i = 1;
                int  g0054 = m;
                { OID gc_local;
                  while ((i <= g0054))
                  { GC_LOOP;
                    { OID  g0065UU;
                      { ValueRemovals * _CL_obj = ((ValueRemovals *) GC_OBJECT(ValueRemovals,new_object_class(choco._ValueRemovals)));
                        g0065UU = _oid_(_CL_obj);
                        } 
                      i_bag->addFast(g0065UU);
                      } 
                    ++i;
                    GC_UNLOOP;} 
                  } 
                } 
              g0064 = GC_OBJECT(list,i_bag);
              } 
            (g0063->eventQueue = g0064);} 
          (_CL_obj->qLastRead = (n+1));
          (_CL_obj->qLastEnqueued = (n+1));
          g0062 = _CL_obj;
          } 
        (g0061->removalEvtQueue = g0062);} 
      { ChocEngine * g0066; 
        InstantiationStack * g0067;
        g0066 = pe;
        { InstantiationStack * _CL_obj = ((InstantiationStack *) GC_OBJECT(InstantiationStack,new_object_class(choco._InstantiationStack)));
          (_CL_obj->qsize = n);
          (_CL_obj->engine = pe);
          { InstantiationStack * g0068; 
            list * g0069;
            g0068 = _CL_obj;
            { list * i_bag = list::empty(choco._Instantiation);
              { int  i = 1;
                int  g0055 = m;
                { OID gc_local;
                  while ((i <= g0055))
                  { GC_LOOP;
                    { OID  g0070UU;
                      { Instantiation * _CL_obj = ((Instantiation *) GC_OBJECT(Instantiation,new_object_class(choco._Instantiation)));
                        g0070UU = _oid_(_CL_obj);
                        } 
                      i_bag->addFast(g0070UU);
                      } 
                    ++i;
                    GC_UNLOOP;} 
                  } 
                } 
              g0069 = GC_OBJECT(list,i_bag);
              } 
            (g0068->eventQueue = g0069);} 
          STOREI(_CL_obj->sLastRead,0);
          STOREI(_CL_obj->sLastPushed,0);
          g0067 = _CL_obj;
          } 
        (g0066->instEvtStack = g0067);} 
      { ChocEngine * g0071; 
        ConstAwakeEventQueue * g0072;
        g0071 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0073; 
            BipartiteSet * g0074;
            g0073 = _CL_obj;
            { OID  g0075UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0075UU = _oid_(_CL_obj);
                } 
              g0074 = choco_makeBipartiteSet_type(choco._ConstAwakeEvent,g0075UU);
              } 
            (g0073->partition = g0074);} 
          g0072 = _CL_obj;
          } 
        (g0071->delayedConst1 = g0072);} 
      { ChocEngine * g0076; 
        ConstAwakeEventQueue * g0077;
        g0076 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0078; 
            BipartiteSet * g0079;
            g0078 = _CL_obj;
            { OID  g0080UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0080UU = _oid_(_CL_obj);
                } 
              g0079 = choco_makeBipartiteSet_type(choco._ConstAwakeEvent,g0080UU);
              } 
            (g0078->partition = g0079);} 
          g0077 = _CL_obj;
          } 
        (g0076->delayedConst2 = g0077);} 
      { ChocEngine * g0081; 
        ConstAwakeEventQueue * g0082;
        g0081 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0083; 
            BipartiteSet * g0084;
            g0083 = _CL_obj;
            { OID  g0085UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0085UU = _oid_(_CL_obj);
                } 
              g0084 = choco_makeBipartiteSet_type(choco._ConstAwakeEvent,g0085UU);
              } 
            (g0083->partition = g0084);} 
          g0082 = _CL_obj;
          } 
        (g0081->delayedConst3 = g0082);} 
      { ChocEngine * g0086; 
        ConstAwakeEventQueue * g0087;
        g0086 = pe;
        { ConstAwakeEventQueue * _CL_obj = ((ConstAwakeEventQueue *) GC_OBJECT(ConstAwakeEventQueue,new_object_class(choco._ConstAwakeEventQueue)));
          (_CL_obj->engine = pe);
          { ConstAwakeEventQueue * g0088; 
            BipartiteSet * g0089;
            g0088 = _CL_obj;
            { OID  g0090UU;
              { ConstAwakeEvent * _CL_obj = ((ConstAwakeEvent *) GC_OBJECT(ConstAwakeEvent,new_object_class(choco._ConstAwakeEvent)));
                g0090UU = _oid_(_CL_obj);
                } 
              g0089 = choco_makeBipartiteSet_type(choco._ConstAwakeEvent,g0090UU);
              } 
            (g0088->partition = g0089);} 
          g0087 = _CL_obj;
          } 
        (g0086->delayedConst4 = g0087);} 
      Result = pe;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: attachPropagationEngine(pb:Problem,pe:PropagationEngine) [] */
void  choco_attachPropagationEngine_Problem(Problem *pb,PropagationEngine *pe)
{ (pb->propagationEngine = pe);
  (pe->problem = pb);
  } 


/* The c++ function for: isEmpty(q:EventQueue) [] */
ClaireBoolean * choco_isEmpty_EventQueue(EventQueue *q)
{ return (equal(q->qLastRead,q->qLastEnqueued));} 


/* The c++ function for: isEmpty(s:InstantiationStack) [] */
ClaireBoolean * choco_isEmpty_InstantiationStack(InstantiationStack *s)
{ return (equal(s->sLastRead,s->sLastPushed));} 


// v0.9907
/* The c++ function for: popNextEvent(q:BoundEventQueue) [] */
BoundUpdate * choco_popNextEvent_BoundEventQueue(BoundEventQueue *q)
{ { BoundUpdate *Result ;
    { int  i = ((q->qsize <= q->qLastRead) ?
        1 :
        (q->qLastRead+1) );
      (q->qLastRead = i);
      (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
      Result = OBJECT(BoundUpdate,(*(q->eventQueue))[i]);
      } 
    return (Result);} 
  } 


/* The c++ function for: popNextEvent(q:RemovalEventQueue) [] */
ValueRemovals * choco_popNextEvent_RemovalEventQueue(RemovalEventQueue *q)
{ { ValueRemovals *Result ;
    { int  i = ((q->qsize <= q->qLastRead) ?
        1 :
        (q->qLastRead+1) );
      (q->qLastRead = i);
      (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
      Result = OBJECT(ValueRemovals,(*(q->eventQueue))[i]);
      } 
    return (Result);} 
  } 


/* The c++ function for: popNextEvent(q:InstantiationStack) [] */
Instantiation * choco_popNextEvent_InstantiationStack(InstantiationStack *q)
{ { Instantiation *Result ;
    { int  i = (q->sLastRead+1);
      STOREI(q->sLastRead,i);
      (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
      Result = OBJECT(Instantiation,(*(q->eventQueue))[i]);
      } 
    return (Result);} 
  } 


// the size of this queue can be bounded by 2*length(pb.vars)+1 => overflows are errors
/* The c++ function for: nextEventPostIndex(q:BoundEventQueue) [] */
int  choco_nextEventPostIndex_BoundEventQueue(BoundEventQueue *q)
{ { int Result = 0;
    { int  i = ((q->qLastEnqueued == q->qsize) ?
        1 :
        (q->qLastEnqueued+1) );
      if (i == q->qLastRead)
       { close_exception(((general_error *) (*Core._general_error)(_string_("bound event fifo queue is full"),
          _oid_(Kernel.nil))));
        Result = -1;
        } 
      else { (q->qLastEnqueued = i);
          Result = i;
          } 
        } 
    return (Result);} 
  } 


// the size of this queue can be bounded by length(pb.vars)+1 => overflows are errors
/* The c++ function for: nextEventPostIndex(q:RemovalEventQueue) [] */
int  choco_nextEventPostIndex_RemovalEventQueue(RemovalEventQueue *q)
{ GC_BIND;
  { int Result = 0;
    { int  i = ((q->qLastEnqueued == q->qsize) ?
        1 :
        (q->qLastEnqueued+1) );
      if (i == q->qLastRead)
       { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,q->engine));
        Result = -1;
        } 
      else { (q->qLastEnqueued = i);
          Result = i;
          } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: raiseOverflowWarning(pe:PropagationEngine) [] */
void  choco_raiseOverflowWarning_PropagationEngine(PropagationEngine *pe)
{ if (pe->propagationOverflow != CTRUE)
   { (pe->propagationOverflow = CTRUE);
    close_exception(((general_error *) (*Core._general_error)(_string_("an overflow of value removals happened: this is very strange"),
      _oid_(Kernel.nil))));
    } 
  } 


// the size of this queue can be bounded by length(pb.vars) => overflows are errors
/* The c++ function for: nextEventPostIndex(s:InstantiationStack) [] */
int  choco_nextEventPostIndex_InstantiationStack(InstantiationStack *s)
{ { int Result = 0;
    if (s->qsize <= s->sLastPushed)
     { close_exception(((general_error *) (*Core._general_error)(_string_("instantiation event stack is full (top:~S, size:~S)"),
        _oid_(list::alloc(2,s->sLastPushed,s->qsize)))));
      Result = -1;
      } 
    else { STOREI(s->sLastPushed,(s->sLastPushed+1));
        Result = s->sLastPushed;
        } 
      return (Result);} 
  } 


// removes all the data on vars related to event pending in the queue.
// All this needs to be cleaned upon backtracking
// Note: only the information stored in vars (idxDecSupInQueue, idxIncInfInQueue) is cleaned
//       the queue itself is not cleaned (the events are not removed)
//       only the qLastRead pointer is reset
// In interpreted mode, a bunch of assertions are checked
// 1. cleaning the queue of INCINF/DECSUP
/* The c++ function for: flushEventQueue(q:BoundEventQueue) [] */
void  choco_flushEventQueue_BoundEventQueue(BoundEventQueue *q)
{ { list * eq = q->eventQueue;
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    if (q->isPopping == CTRUE)
     { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[i]);
      (evt->idxInQueue = 0);
      (q->isPopping = CFALSE);
      (q->redundantEvent = CFALSE);
      } 
    if (i != j)
     { ++i;
      if (i > q->qsize)
       i= 1;
      if (i <= j)
       { int  k = i;
        int  g0091 = j;
        { while ((k <= g0091))
          { { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
              (evt->idxInQueue = 0);
              } 
            ++k;
            } 
          } 
        } 
      else { { int  k = i;
            int  g0092 = q->qsize;
            { while ((k <= g0092))
              { { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
                  (evt->idxInQueue = 0);
                  } 
                ++k;
                } 
              } 
            } 
          { int  k = 1;
            int  g0093 = j;
            { while ((k <= g0093))
              { { BoundUpdate * evt = OBJECT(BoundUpdate,(*(eq))[k]);
                  (evt->idxInQueue = 0);
                  } 
                ++k;
                } 
              } 
            } 
          } 
        (q->qLastRead = q->qLastEnqueued);
      ;} 
    } 
  } 


// 2. cleaning the queue of REMVAL/REMVALS
/* The c++ function for: flushEventQueue(q:RemovalEventQueue) [] */
void  choco_flushEventQueue_RemovalEventQueue(RemovalEventQueue *q)
{ { list * eq = q->eventQueue;
    int  i = q->qLastRead;
    int  j = q->qLastEnqueued;
    if (q->isPopping == CTRUE)
     { ValueRemovals * evt = OBJECT(ValueRemovals,(*(eq))[i]);
      (evt->idxInQueue = 0);
      (q->isPopping = CFALSE);
      } 
    if (i != j)
     { ++i;
      if (i > q->qsize)
       i= 1;
      if (i <= j)
       { int  k = i;
        int  g0094 = j;
        { while ((k <= g0094))
          { { ValueRemovals * evt = OBJECT(ValueRemovals,(*(eq))[k]);
              (evt->idxInQueue = 0);
              ;} 
            ++k;
            } 
          } 
        } 
      else { { int  k = i;
            int  g0095 = q->qsize;
            { while ((k <= g0095))
              { { ValueRemovals * evt = OBJECT(ValueRemovals,(*(eq))[k]);
                  (evt->idxInQueue = 0);
                  ;} 
                ++k;
                } 
              } 
            } 
          { int  k = 1;
            int  g0096 = j;
            { while ((k <= g0096))
              { { ValueRemovals * evt = OBJECT(ValueRemovals,(*(eq))[k]);
                  (evt->idxInQueue = 0);
                  ;} 
                ++k;
                } 
              } 
            } 
          } 
        (q->qLastRead = q->qLastEnqueued);
      } 
    ;} 
  } 


// 3. cleaning the queue of INSTANTIATE
// [choco/flushEventQueue(q:InstantiationStack) : void
// There is nothing to flush in the stack of instantiations since the two cursors (sLastRead, sLastEnqueued) are stored
// as backtrackable values.
// v1.05 <thb> flush constraint event queues
/* The c++ function for: flushEventQueue(q:ConstAwakeEventQueue) [] */
void  choco_flushEventQueue_ConstAwakeEventQueue(ConstAwakeEventQueue *q)
{ GC_BIND;
  choco_moveAllRight_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition));
  GC_UNBIND;} 


// A useful tool for debugging in interpreted mode
// checking that all links from variables to events are cleaned.
// v1.07 check pe.nbPendingInitConstAwakeEvent
/* The c++ function for: checkCleanState(pe:ChocEngine) [] */
OID  choco_checkCleanState_ChocEngine(ChocEngine *pe)
{ return (Core.nil->value);} 


// v0.9906
// v0.36 <fl> two new API functions
// v1.02 check that there are no pending events before pushing a world.
/* The c++ function for: pushWorld(pb:Problem) [] */
void  choco_pushWorld_Problem(Problem *pb)
{ GC_BIND;
  { OID  qtest = GC_OID(choco.getNextActiveEventQueue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(pb->propagationEngine))))));
    if (qtest != CNULL)
     { EventCollection * q = OBJECT(EventCollection,qtest);
      close_exception(((general_error *) (*Core._general_error)(_string_("it is forbidden to push a new world (~S) while there are pending events in queue:~S"),
        _oid_(list::alloc(2,world_number(),_oid_(q))))));
      } 
    else { world_push();
        ;} 
      } 
  GC_UNBIND;} 


/* The c++ function for: popWorld(pb:Problem) [] */
void  choco_popWorld_Problem(Problem *pb)
{ world_pop();
  ;;} 


// claire3 port world= -> backtrack
/* The c++ function for: setWorld(pb:Problem,n:integer) [] */
void  choco_setWorld_Problem(Problem *pb,int n)
{ backtrack_integer(n);
  ;} 


/* The c++ function for: commitWorld(pb:Problem) [] */
void  choco_commitWorld_Problem(Problem *pb)
{ world_remove();
  ;} 


// v1.0 now called by the raiseContradiction methods
// v1.05 <thb> flush also constraint based events
// v1.07 <fl> reset pe.nbPendingInitConstAwakeEvent
/* The c++ function for: flushCurrentOpenEvents(pe:ChocEngine) [] */
OID  choco_flushCurrentOpenEvents_ChocEngine(ChocEngine *pe)
{ GC_BIND;
  choco_flushEventQueue_BoundEventQueue(GC_OBJECT(BoundEventQueue,pe->boundEvtQueue));
  choco_flushEventQueue_RemovalEventQueue(GC_OBJECT(RemovalEventQueue,pe->removalEvtQueue));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,pe->delayedConst1));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,pe->delayedConst2));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,pe->delayedConst3));
  choco_flushEventQueue_ConstAwakeEventQueue(GC_OBJECT(ConstAwakeEventQueue,pe->delayedConst4));
  (pe->nbPendingInitConstAwakeEvent = 0);
  (pe->nbPendingVarEvent = 0);
  { OID Result = 0;
    Result = Core.nil->value;
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 5: posting an event to the propagation engine             *
// *     ChocEngine: <=/>= events are posted to a fifo queue          *
// *                 =/!= events are posted to a stack                *
// ********************************************************************
// interface functions
/* The c++ function for: postInstInt(pe:PropagationEngine,v:IntVar,i:integer) [] */
void  choco_postInstInt_PropagationEngine(PropagationEngine *pe,IntVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post INSTINT(~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postRemoveVal(pe:PropagationEngine,v:IntVar,x:integer,i:integer) [] */
void  choco_postRemoveVal_PropagationEngine(PropagationEngine *pe,IntVar *v,int x,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post REM(~S,~S,~S) to ~S"),
    _oid_(list::alloc(4,_oid_(v),
      x,
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postUpdateInf(pe:PropagationEngine,v:IntVar,i:integer) [] */
void  choco_postUpdateInf_PropagationEngine(PropagationEngine *pe,IntVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post INCINF(~S,~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postUpdateSup(pe:PropagationEngine,v:IntVar,i:integer) [] */
void  choco_postUpdateSup_PropagationEngine(PropagationEngine *pe,IntVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post DECSUP(~S,~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postUpdateKer(pe:PropagationEngine,v:SetVar,i:integer) [] */
void  choco_postUpdateKer_PropagationEngine(PropagationEngine *pe,SetVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post INCKER(~S,~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postUpdateEnv(pe:PropagationEngine,v:SetVar,i:integer) [] */
void  choco_postUpdateEnv_PropagationEngine(PropagationEngine *pe,SetVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post DECENV(~S,~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postInstSet(pe:PropagationEngine,v:SetVar,i:integer) [] */
void  choco_postInstSet_PropagationEngine(PropagationEngine *pe,SetVar *v,int i)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post INSTSET(~S,~S) to ~S"),
    _oid_(list::alloc(3,_oid_(v),
      i,
      _oid_(pe))))));
  } 


/* The c++ function for: postConstAwake(pe:PropagationEngine,c:AbstractConstraint,init:boolean) [] */
void  choco_postConstAwake_PropagationEngine(PropagationEngine *pe,AbstractConstraint *c,ClaireBoolean *init)
{ close_exception(((general_error *) (*Core._general_error)(_string_("impossible to post AWAKE(~S,init:~S) to ~S"),
    _oid_(list::alloc(3,_oid_(c),
      _oid_(init),
      _oid_(pe))))));
  } 


// generic framwork of the four following methods:
//   get the right queue q for the event
//   get an index idx in the queue by calling nextEventPostIndex(q)
//   write all components of the event at q[idx]:
//      - the variable
//      - if needed, the kind of event (INCINF/DECSUP or REMVAL/REMVALS)
//      - the cause (index of hte constraint causing the event)
//      - if needed, the removed value (for REMVAL)
//   store the location of the event in the queue (idx) in the variable
//   + some additional optimization for removing doubles
/* The c++ function for: postInstantiateEvt(pe:ChocEngine,e:Instantiation,i:integer) [] */
void  choco_postInstantiateEvt_ChocEngine(ChocEngine *pe,Instantiation *e,int i)
{ ;{ InstantiationStack * iq = pe->instEvtStack;
    int  idx;
    if (iq->qsize <= iq->sLastPushed)
     { close_exception(((general_error *) (*Core._general_error)(_string_("instantiation event stack is full (top:~S, size:~S)"),
        _oid_(list::alloc(2,iq->sLastPushed,iq->qsize)))));
      idx = -1;
      } 
    else { STOREI(iq->sLastPushed,(iq->sLastPushed+1));
        idx = iq->sLastPushed;
        } 
      (e->cause = i);
    (pe->nbPendingVarEvent = (pe->nbPendingVarEvent+1));
    STOREI((*iq->eventQueue)[idx],_oid_(e));
    } 
  } 


/* The c++ function for: postInstInt(pe:ChocEngine,v:IntVar,i:integer) [] */
void  choco_postInstInt_ChocEngine(ChocEngine *pe,IntVar *v,int i)
{ GC_BIND;
  choco_postInstantiateEvt_ChocEngine(pe,GC_OBJECT(InstInt,v->instantiateEvt),i);
  GC_UNBIND;} 


/* The c++ function for: postInstSet(pe:ChocEngine,v:SetVar,i:integer) [] */
void  choco_postInstSet_ChocEngine(ChocEngine *pe,SetVar *v,int i)
{ GC_BIND;
  choco_postInstantiateEvt_ChocEngine(pe,GC_OBJECT(InstSet,v->instantiateEvt),i);
  GC_UNBIND;} 


/* The c++ function for: postRemoveVal(pe:ChocEngine,v:IntVar,x:integer,i:integer) [] */
void  choco_postRemoveVal_ChocEngine(ChocEngine *pe,IntVar *v,int x,int i)
{ GC_BIND;
  ;{ RemovalEventQueue * rq = GC_OBJECT(RemovalEventQueue,pe->removalEvtQueue);
    ValueRemovals * e = GC_OBJECT(ValueRemovals,v->remValEvt);
    int  idxQ = e->idxInQueue;
    if (idxQ == 0)
     { int  idx;
      { int  g0097 = ((rq->qLastEnqueued == rq->qsize) ?
          1 :
          (rq->qLastEnqueued+1) );
        if (g0097 == rq->qLastRead)
         { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,rq->engine));
          idx = -1;
          } 
        else { (rq->qLastEnqueued = g0097);
            idx = g0097;
            } 
          } 
      (pe->nbPendingVarEvent = (pe->nbPendingVarEvent+1));
      (e->idxInQueue = idx);
      (e->many = CFALSE);
      ((*(e->valueStack))[1]=x);
      (e->nbVals = 1);
      ((*(e->causeStack))[1]=i);
      ((*(rq->eventQueue))[idx]=_oid_(e));
      } 
    else { if (idxQ != (-1))
         ;if (e->many == CTRUE)
         { if (idxQ == (-1))
           { int  idx;
            { int  g0098 = ((rq->qLastEnqueued == rq->qsize) ?
                1 :
                (rq->qLastEnqueued+1) );
              if (g0098 == rq->qLastRead)
               { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,rq->engine));
                idx = -1;
                } 
              else { (rq->qLastEnqueued = g0098);
                  idx = g0098;
                  } 
                } 
            (e->idxInQueue = idx);
            (e->nbVals = 1);
            ((*(rq->eventQueue))[idx]=_oid_(e));
            (rq->redundantEvent = CTRUE);
            } 
          if ((*(e->causeStack))[1] != i)
           ((*(e->causeStack))[1]=0);
          } 
        else { int  nbRems = (e->nbVals+1);
            if (nbRems <= e->maxVals)
             { (e->nbVals = (e->nbVals+1));
              ((*(e->valueStack))[nbRems]=x);
              ((*(e->causeStack))[nbRems]=i);
              } 
            else { (e->many = CTRUE);
                (e->nbVals = 1);
                ((*(e->valueStack))[1]=x);
                ((*(e->causeStack))[1]=i);
                (rq->engine->propagationOverflow = CTRUE);
                if (idxQ == (-1))
                 { int  idx;
                  { int  g0099 = ((rq->qLastEnqueued == rq->qsize) ?
                      1 :
                      (rq->qLastEnqueued+1) );
                    if (g0099 == rq->qLastRead)
                     { choco_raiseOverflowWarning_PropagationEngine(GC_OBJECT(PropagationEngine,rq->engine));
                      idx = -1;
                      } 
                    else { (rq->qLastEnqueued = g0099);
                        idx = g0099;
                        } 
                      } 
                  (e->idxInQueue = idx);
                  ((*(rq->eventQueue))[idx]=_oid_(e));
                  (rq->redundantEvent = CTRUE);
                  } 
                { ClaireBoolean * g0101I;
                  { OID  g0102UU;
                    { int  j = 2;
                      int  g0100 = (nbRems-1);
                      { OID gc_local;
                        g0102UU= _oid_(CFALSE);
                        while ((j <= g0100))
                        { // HOHO, GC_LOOP not needed !
                          if ((*(e->causeStack))[j] != i)
                           { g0102UU = Kernel.ctrue;
                            break;} 
                          ++j;
                          } 
                        } 
                      } 
                    g0101I = boolean_I_any(g0102UU);
                    } 
                  
                  if (g0101I == CTRUE) ((*(e->causeStack))[1]=0);
                    } 
                ;} 
              } 
          } 
      } 
  GC_UNBIND;} 


// Before posting an event, we test whether this one is already in the queue.
// If it is, but was pushed in by another constraint, we forget the constraint that pushed it in.
/* The c++ function for: postBoundEvent(pe:ChocEngine,e:BoundUpdate,i:integer) [] */
void  choco_postBoundEvent_ChocEngine(ChocEngine *pe,BoundUpdate *e,int i)
{ ;{ BoundEventQueue * bq = pe->boundEvtQueue;
    int  idxQ = e->idxInQueue;
    if (idxQ <= 0)
     { int  idx;
      { int  g0103 = ((bq->qLastEnqueued == bq->qsize) ?
          1 :
          (bq->qLastEnqueued+1) );
        if (g0103 == bq->qLastRead)
         { close_exception(((general_error *) (*Core._general_error)(_string_("bound event fifo queue is full"),
            _oid_(Kernel.nil))));
          idx = -1;
          } 
        else { (bq->qLastEnqueued = g0103);
            idx = g0103;
            } 
          } 
      (e->cause = i);
      (pe->nbPendingVarEvent = (pe->nbPendingVarEvent+1));
      ((*(bq->eventQueue))[idx]=_oid_(e));
      (e->idxInQueue = idx);
      if (idxQ == (-1))
       (bq->redundantEvent = CTRUE);
      } 
    else { (e->cause = i);
        } 
      } 
  } 


// we are trying to post an event similar to a pending event
// -> if both events have the same cause, then one of the posts is ignored
//    if both events have different causes (i0 and i), the the i-th constraint of v
//    (the cause of the strongest -ie most recent- event) must not be re-waken
//    Therefore, we always keep the the cause of the most recent event (and forget the previous one)
/* The c++ function for: postUpdateInf(pe:ChocEngine,v:IntVar,i:integer) [] */
void  choco_postUpdateInf_ChocEngine(ChocEngine *pe,IntVar *v,int i)
{ GC_BIND;
  choco_postBoundEvent_ChocEngine(pe,GC_OBJECT(IncInf,v->updtInfEvt),i);
  GC_UNBIND;} 


/* The c++ function for: postUpdateSup(pe:ChocEngine,v:IntVar,i:integer) [] */
void  choco_postUpdateSup_ChocEngine(ChocEngine *pe,IntVar *v,int i)
{ GC_BIND;
  choco_postBoundEvent_ChocEngine(pe,GC_OBJECT(DecSup,v->updtSupEvt),i);
  GC_UNBIND;} 


/* The c++ function for: postUpdateKer(pe:ChocEngine,v:SetVar,i:integer) [] */
void  choco_postUpdateKer_ChocEngine(ChocEngine *pe,SetVar *v,int i)
{ GC_BIND;
  choco_postBoundEvent_ChocEngine(pe,GC_OBJECT(IncKer,v->updtKerEvt),i);
  GC_UNBIND;} 


/* The c++ function for: postUpdateEnv(pe:ChocEngine,v:SetVar,i:integer) [] */
void  choco_postUpdateEnv_ChocEngine(ChocEngine *pe,SetVar *v,int i)
{ GC_BIND;
  choco_postBoundEvent_ChocEngine(pe,GC_OBJECT(DecEnv,v->updtEnvEvt),i);
  GC_UNBIND;} 


// v1.0: simple util
/* The c++ function for: getQueue(pe:ChocEngine,evt:ConstAwakeEvent) [] */
ConstAwakeEventQueue * choco_getQueue_ChocEngine(ChocEngine *pe,ConstAwakeEvent *evt)
{ { ConstAwakeEventQueue *Result ;
    { int  prio = evt->priority;
      Result = ((prio == 1) ?
        pe->delayedConst1 :
        ((prio == 2) ?
          pe->delayedConst2 :
          ((prio == 3) ?
            pe->delayedConst3 :
            pe->delayedConst4 ) ) );
      } 
    return (Result);} 
  } 


// v0.9907
/* The c++ function for: registerEvent(pe:ChocEngine,evt:ConstAwakeEvent) [] */
void  choco_registerEvent_ChocEngine(ChocEngine *pe,ConstAwakeEvent *evt)
{ GC_BIND;
  { ConstAwakeEventQueue * q = GC_OBJECT(ConstAwakeEventQueue,choco_getQueue_ChocEngine(pe,evt));
    if (choco_isIn_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition),_oid_(evt)) == CTRUE)
     close_exception(((general_error *) (*Core._general_error)(_string_("event ~S is already attached to engine ~S"),
      _oid_(list::alloc(2,_oid_(evt),_oid_(pe))))));
    else choco_addRight_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition),_oid_(evt));
      } 
  GC_UNBIND;} 


// v0.9907
/* The c++ function for: postConstAwake(pe:ChocEngine,c:AbstractConstraint,init:boolean) [] */
ClaireBoolean * choco_postConstAwake_ChocEngine(ChocEngine *pe,AbstractConstraint *c,ClaireBoolean *init)
{ GC_BIND;
  { ClaireBoolean *Result ;
    { ConstAwakeEvent * evt = GC_OBJECT(ConstAwakeEvent,c->constAwakeEvent);
      ConstAwakeEventQueue * q = GC_OBJECT(ConstAwakeEventQueue,choco_getQueue_ChocEngine(pe,evt));
      if (choco_isLeft_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition),_oid_(evt)) != CTRUE)
       { (evt->initialized = not_any(_oid_(init)));
        choco_moveLeft_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition),_oid_(evt));
        if (init == CTRUE)
         (pe->nbPendingInitConstAwakeEvent = (pe->nbPendingInitConstAwakeEvent+1));
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    GC_UNBIND; return (Result);} 
  } 


// v1.0: used for removing an event from the queue without propagating it
/* The c++ function for: remove(q:ConstAwakeEventQueue,evt:ConstAwakeEvent) [] */
void  choco_remove_ConstAwakeEventQueue(ConstAwakeEventQueue *q,ConstAwakeEvent *evt)
{ GC_BIND;
  { BipartiteSet * ptn = GC_OBJECT(BipartiteSet,q->partition);
    if (choco_isLeft_BipartiteSet(GC_OBJECT(BipartiteSet,q->partition),_oid_(evt)) == CTRUE)
     choco_moveRight_BipartiteSet(ptn,_oid_(evt));
    } 
  GC_UNBIND;} 


// All posts are generated by IntVars, except constevent posts
// API
/* The c++ function for: constAwake(c:AbstractConstraint,init:boolean) [] */
void  choco_constAwake_AbstractConstraint(AbstractConstraint *c,ClaireBoolean *init)
{ GC_BIND;
  ;(*choco.postConstAwake)(GC_OID(_oid_(choco_getProblem_AbstractConstraint(c)->propagationEngine)),
    _oid_(c),
    _oid_(init));
  GC_UNBIND;} 


// ********************************************************************
// *   Part 6: retrieving an event & wakening the constraint          *
// ********************************************************************
// v0.9: this part was fully rewritten
// 1. Most preemptive events: instantiations
// propagate all instantiations
/* The c++ function for: popSomeEvents(q:InstantiationStack) [] */
void  choco_popSomeEvents_InstantiationStack(InstantiationStack *q)
{ GC_BIND;
  { list * evtq = GC_OBJECT(list,q->eventQueue);
    { while ((q->sLastRead != q->sLastPushed))
      { { OID  g0105UU;
          { int  g0104 = (q->sLastRead+1);
            STOREI(q->sLastRead,g0104);
            (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
            g0105UU = (*(q->eventQueue))[g0104];
            } 
          (*choco.propagateEvent)(g0105UU);
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// v0.9907
// v0.9906 propagateInstantiation recast into propagateEvent (and goes from private to choco)
// for all pending INST(v,idx), performs an iteration similar to
//       for k in ((1 .. idx - 1) /+ (idx + 1 .. n))
//             doAwakeOnInst(v.constraints[k], v.indices[k])
// but using the sub-cycle of active constraints from v.constraints, coded by nextConstOnInst
// v0.9906: propagateAllInstantiations recast into popSomeEvents
/* The c++ function for: propagateEvent(e:Instantiation) [] */
void  choco_propagateEvent_Instantiation_choco(Instantiation *e)
{ GC_BIND;
  { AbstractVar * v = e->modifiedVar;
    int  n = v->nbConstraints;
    list * lc = v->constraints;
    list * li = v->indices;
    if (n > 0)
     { list * lnext = e->nextConst;
      int  i1 = e->cause;
      int  prevk = i1;
      int  k = ((i1 == 0) ?
        (*(lnext))[n] :
        (*(lnext))[i1] );
      if ((k > 0) && 
          (k != i1))
       { if (INHERIT(e->isa,choco._InstInt))
         { { while ((k > prevk))
            { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
              prevk= k;
              k= (*(lnext))[k];
              } 
            } 
          prevk= 0;
          { while (((k > prevk) && 
                (k < i1)))
            { _void_(choco.awakeOnInst->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
              prevk= k;
              k= (*(lnext))[k];
              } 
            } 
          } 
        else if (INHERIT(e->isa,choco._InstSet))
         { { while ((k > prevk))
            { (*choco.awakeOnInstSet)((*(lc))[k],
                (*(li))[k]);
              prevk= k;
              k= (*(lnext))[k];
              } 
            } 
          prevk= 0;
          { while (((k > prevk) && 
                (k < i1)))
            { (*choco.awakeOnInstSet)((*(lc))[k],
                (*(li))[k]);
              prevk= k;
              k= (*(lnext))[k];
              } 
            } 
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// V0.9906 propagateRemoval recast into popSomeEvents
// pop value removals one by one
/* The c++ function for: popSomeEvents(q:RemovalEventQueue) [] */
void  choco_popSomeEvents_RemovalEventQueue(RemovalEventQueue *q)
{ { OID  g0107UU;
    { int  g0106 = ((q->qsize <= q->qLastRead) ?
        1 :
        (q->qLastRead+1) );
      (q->qLastRead = g0106);
      (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
      g0107UU = (*(q->eventQueue))[g0106];
      } 
    (*choco.propagateEvent)(g0107UU,
      _oid_(q));
    } 
  } 


// v0.9907
// 2a. Removal events
// for all pending REMOVAL(v,x,idx), performs an iteration similar to
//       for k in ((1 .. idx - 1) /+ (idx + 1 .. n))
//             doAwakeOnRem(v.constraints[k],x,v.indices[k])
// but using the sub-cycle of active constraints from v.constraints, coded by nextConstOnRem
// returns a boolean indicating if there were some events to propagate
// v1.02 <thb> add the ability for a new incoming event to interrupt the loops (in case of redundant MANYREM events)
/* The c++ function for: propagateEvent(e:ValueRemovals,q:RemovalEventQueue) [] */
void  choco_propagateEvent_ValueRemovals_choco(ValueRemovals *e,RemovalEventQueue *q)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    int  n = v->nbConstraints;
    list * lc = GC_OBJECT(list,v->constraints);
    list * li = GC_OBJECT(list,v->indices);
    (e->idxInQueue = -1);
    if (n > 0)
     { list * lnext = GC_OBJECT(list,e->nextConst);
      { while ((e->nbVals > 0))
        { { int  nbv = e->nbVals;
            int  i1 = (*(e->causeStack))[nbv];
            int  prevk = i1;
            int  k = ((i1 == 0) ?
              (*(lnext))[n] :
              (*(lnext))[i1] );
            (e->nbVals = (e->nbVals-1));
            if ((k > 0) && 
                (k != i1))
             { (q->isPopping = CTRUE);
              if (e->many == CTRUE)
               { { while (((k > prevk) && 
                      (q->redundantEvent != CTRUE)))
                  { _void_(choco.awakeOnVar->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                prevk= 0;
                { while (((k > prevk) && 
                      ((k < i1) && 
                        (q->redundantEvent != CTRUE))))
                  { _void_(choco.awakeOnVar->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              else { int  x = (*(e->valueStack))[nbv];
                  { while (((k > prevk) && 
                        (q->redundantEvent != CTRUE)))
                    { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k]),((int) x)));
                      prevk= k;
                      k= (*(lnext))[k];
                      } 
                    } 
                  prevk= 0;
                  { while (((k > prevk) && 
                        ((k < i1) && 
                          (q->redundantEvent != CTRUE))))
                    { _void_(choco.awakeOnRem->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k]),((int) x)));
                      prevk= k;
                      k= (*(lnext))[k];
                      } 
                    } 
                  } 
                (q->isPopping = CFALSE);
              } 
            } 
          } 
        } 
      } 
    if (q->redundantEvent == CTRUE)
     { (q->redundantEvent = CFALSE);
      } 
    else (e->idxInQueue = 0);
      } 
  GC_UNBIND;} 


// in this branch of the "if": v is absent from the queue
// v0.9906 propagateBoundEvent recast into popSomeEvents
/* The c++ function for: popSomeEvents(q:BoundEventQueue) [] */
void  choco_popSomeEvents_BoundEventQueue(BoundEventQueue *q)
{ { OID  g0109UU;
    { int  g0108 = ((q->qsize <= q->qLastRead) ?
        1 :
        (q->qLastRead+1) );
      (q->qLastRead = g0108);
      (CLREAD(ChocEngine,q->engine,nbPendingVarEvent) = (CLREAD(ChocEngine,q->engine,nbPendingVarEvent)-1));
      g0109UU = (*(q->eventQueue))[g0108];
      } 
    (*choco.propagateEvent)(g0109UU,
      _oid_(q));
    } 
  } 


// v0.9907
// 2b. Bound events (INCINF/DECSUP)
// propagate pops triplets from the queue until it is empty
// before popping a quadruplet, the index corresponding to this event is reset to 0
// Note (FL 30.11.98): we avoid doubles between the part of the iteration from the current triplet
// that remains to be done and the following triplets, by raising the PropagationCycle exception.
// returns a boolean indicating if there were some events to propagate
// v0.9906 replaced the propagationCycle exception by the redundantEvent flag 
// 1.330: now contains INC/INF events on integers, but also KER/ENV event on set vars
/* The c++ function for: propagateEvent(e:BoundUpdate,q:BoundEventQueue) [] */
void  choco_propagateEvent_BoundUpdate_choco(BoundUpdate *e,BoundEventQueue *q)
{ GC_BIND;
  { AbstractVar * v = GC_OBJECT(AbstractVar,e->modifiedVar);
    int  n = v->nbConstraints;
    list * lc = GC_OBJECT(list,v->constraints);
    list * li = GC_OBJECT(list,v->indices);
    (q->isPopping = CTRUE);
    if (n == 0)
     (e->idxInQueue = 0);
    else { { list * lnext = GC_OBJECT(list,e->nextConst);
          int  i1 = e->cause;
          int  prevk = i1;
          int  k = ((i1 == 0) ?
            (*(lnext))[n] :
            (*(lnext))[i1] );
          if ((equal(_oid_(lc),Core.nil->value) == CTRUE) || 
              ((k == 0) || 
                (k == i1)))
           (e->idxInQueue = 0);
          else { (e->idxInQueue = -1);
              if (INHERIT(e->isa,choco._IncInf))
               { { while (((k > prevk) && 
                      (q->redundantEvent != CTRUE)))
                  { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                prevk= 0;
                { while (((k > prevk) && 
                      ((k < i1) && 
                        (q->redundantEvent != CTRUE))))
                  { _void_(choco.awakeOnInf->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              else if (INHERIT(e->isa,choco._DecSup))
               { { while (((k > prevk) && 
                      (q->redundantEvent != CTRUE)))
                  { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                prevk= 0;
                { while (((k > prevk) && 
                      ((k < i1) && 
                        (q->redundantEvent != CTRUE))))
                  { _void_(choco.awakeOnSup->fcall(((int) OBJECT(ClaireObject,(*(lc))[k])),((int) (*(li))[k])));
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              else if (INHERIT(e->isa,choco._IncKer))
               { { while (((k > prevk) && 
                      (q->redundantEvent != CTRUE)))
                  { (*choco.awakeOnKer)((*(lc))[k],
                      (*(li))[k]);
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                prevk= 0;
                { while (((k > prevk) && 
                      ((k < i1) && 
                        (q->redundantEvent != CTRUE))))
                  { (*choco.awakeOnKer)((*(lc))[k],
                      (*(li))[k]);
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              else if (INHERIT(e->isa,choco._DecEnv))
               { { while (((k > prevk) && 
                      (q->redundantEvent != CTRUE)))
                  { (*choco.awakeOnEnv)((*(lc))[k],
                      (*(li))[k]);
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                prevk= 0;
                { while (((k > prevk) && 
                      ((k < i1) && 
                        (q->redundantEvent != CTRUE))))
                  { (*choco.awakeOnEnv)((*(lc))[k],
                      (*(li))[k]);
                    prevk= k;
                    k= (*(lnext))[k];
                    } 
                  } 
                } 
              if (q->redundantEvent == CTRUE)
               { (q->redundantEvent = CFALSE);
                } 
              else (e->idxInQueue = 0);
                } 
            } 
        } 
      (q->isPopping = CFALSE);
    } 
  GC_UNBIND;} 


// v0.9906
/* The c++ function for: isEmpty(q:ConstAwakeEventQueue) [] */
ClaireBoolean * choco_isEmpty_ConstAwakeEventQueue(ConstAwakeEventQueue *q)
{ return (equal(q->partition->nbLeft,0));} 


/* The c++ function for: popSomeEvents(q:ConstAwakeEventQueue) [] */
void  choco_popSomeEvents_ConstAwakeEventQueue(ConstAwakeEventQueue *q)
{ GC_BIND;
  { OID  g0113UU;
    { BipartiteSet * g0110 = GC_OBJECT(BipartiteSet,q->partition);
      int  g0111 = g0110->nbLeft;
      ConstAwakeEvent * g0112;
      { { g0112 = OBJECT(ConstAwakeEvent,nth_array(g0110->objs,g0111));
          } 
        GC_OBJECT(ConstAwakeEvent,g0112);} 
      if (g0112->initialized != CTRUE)
       (CLREAD(ChocEngine,q->engine,nbPendingInitConstAwakeEvent) = (CLREAD(ChocEngine,q->engine,nbPendingInitConstAwakeEvent)-1));
      choco_moveRight_BipartiteSet(g0110,_oid_(g0112));
      g0113UU = _oid_(g0112);
      } 
    (*choco.propagateEvent)(g0113UU);
    } 
  GC_UNBIND;} 


// v0.9907
// v0.9907
/* The c++ function for: popNextEvent(q:ConstAwakeEventQueue) [] */
ConstAwakeEvent * choco_popNextEvent_ConstAwakeEventQueue(ConstAwakeEventQueue *q)
{ GC_BIND;
  { ConstAwakeEvent *Result ;
    { BipartiteSet * bp = GC_OBJECT(BipartiteSet,q->partition);
      int  idx = bp->nbLeft;
      ConstAwakeEvent * e;
      { { e = OBJECT(ConstAwakeEvent,nth_array(bp->objs,idx));
          } 
        GC_OBJECT(ConstAwakeEvent,e);} 
      if (e->initialized != CTRUE)
       (CLREAD(ChocEngine,q->engine,nbPendingInitConstAwakeEvent) = (CLREAD(ChocEngine,q->engine,nbPendingInitConstAwakeEvent)-1));
      choco_moveRight_BipartiteSet(bp,_oid_(e));
      Result = e;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: propagateEvent(e:ConstAwakeEvent) [] */
void  choco_propagateEvent_ConstAwakeEvent_choco(ConstAwakeEvent *e)
{ GC_BIND;
  ;if (e->initialized == CTRUE)
   _void_(choco.propagate->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(e->touchedConst))))));
  else (*choco.awake)(GC_OID(_oid_(e->touchedConst)));
    GC_UNBIND;} 


// v1.013: fast dispatch of generic event collection methods
/* The c++ function for: popSomeEvents(q:EventCollection) [] */
void  choco_popSomeEvents_EventCollection(EventCollection *q)
{ close_exception(((general_error *) (*Core._general_error)(_string_("popSomeEvents not defined for abstract class EventCollection"),
    _oid_(Kernel.nil))));
  } 


// v0.9906
/* The c++ function for: getNextActiveEventQueue(pe:PropagationEngine) [] */
OID  choco_getNextActiveEventQueue_PropagationEngine_choco(PropagationEngine *pe)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getNextActiveEventQueue not defined for abstract propagation engine"),
    _oid_(Kernel.nil))));
  return (CNULL);} 


// v1.07: new priority mechanism: ensure that all constraints have been initialized (initial propagation)
// before handling any variable-based event
/* The c++ function for: getNextActiveConstraintEventQueue(pe:ChocEngine) [] */
OID  choco_getNextActiveConstraintEventQueue_ChocEngine(ChocEngine *pe)
{ GC_BIND;
  { OID Result = 0;
    { ConstAwakeEventQueue * cq1 = pe->delayedConst1;
      if (choco_isEmpty_ConstAwakeEventQueue(cq1) != CTRUE)
       Result = _oid_(cq1);
      else { ConstAwakeEventQueue * cq2 = pe->delayedConst2;
          if (choco_isEmpty_ConstAwakeEventQueue(cq2) != CTRUE)
           Result = _oid_(cq2);
          else { ConstAwakeEventQueue * cq3 = pe->delayedConst3;
              if (choco_isEmpty_ConstAwakeEventQueue(cq3) != CTRUE)
               Result = _oid_(cq3);
              else { ConstAwakeEventQueue * cq4 = pe->delayedConst4;
                  if (choco_isEmpty_ConstAwakeEventQueue(cq4) != CTRUE)
                   Result = _oid_(cq4);
                  else Result = CNULL;
                    } 
                } 
            } 
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNextActiveVariableEventQueue(pe:ChocEngine) [] */
OID  choco_getNextActiveVariableEventQueue_ChocEngine(ChocEngine *pe)
{ { OID Result = 0;
    { InstantiationStack * iq = pe->instEvtStack;
      if (iq->sLastRead != iq->sLastPushed)
       Result = _oid_(iq);
      else { BoundEventQueue * bq = pe->boundEvtQueue;
          if (bq->qLastRead != bq->qLastEnqueued)
           Result = _oid_(bq);
          else { RemovalEventQueue * rq = pe->removalEvtQueue;
              if (rq->qLastRead != rq->qLastEnqueued)
               Result = _oid_(rq);
              else Result = CNULL;
                } 
            } 
        } 
    return (Result);} 
  } 


// explicit handling of event priorities: returns an event queue that contains pending
// propagation events (either on variables or on constraints)
// v1.013 the pe.nbPendingVarEvent flag is used as a shortcut to check whether any of
// the VarEventQueues contains pending events
/* The c++ function for: getNextActiveEventQueue(pe:ChocEngine) [] */
OID  choco_getNextActiveEventQueue_ChocEngine_choco(ChocEngine *pe)
{ if (pe->nbPendingInitConstAwakeEvent > 0) 
  { { OID Result = 0;
      { OID  qtest;
        { { ConstAwakeEventQueue * g0114 = pe->delayedConst1;
            if (choco_isEmpty_ConstAwakeEventQueue(g0114) != CTRUE)
             qtest = _oid_(g0114);
            else { ConstAwakeEventQueue * g0115 = pe->delayedConst2;
                if (choco_isEmpty_ConstAwakeEventQueue(g0115) != CTRUE)
                 qtest = _oid_(g0115);
                else { ConstAwakeEventQueue * g0116 = pe->delayedConst3;
                    if (choco_isEmpty_ConstAwakeEventQueue(g0116) != CTRUE)
                     qtest = _oid_(g0116);
                    else { ConstAwakeEventQueue * g0117 = pe->delayedConst4;
                        if (choco_isEmpty_ConstAwakeEventQueue(g0117) != CTRUE)
                         qtest = _oid_(g0117);
                        else qtest = CNULL;
                          } 
                      } 
                  } 
              } 
          GC_OID(qtest);} 
        if (qtest != CNULL)
         { EventCollection * q = OBJECT(EventCollection,qtest);
          Result = _oid_(q);
          } 
        else { close_exception(((general_error *) (*Core._general_error)(_string_("~S pending init events and no active constraint event queue"),
              _oid_(list::alloc(1,pe->nbPendingInitConstAwakeEvent)))));
            Result = CNULL;
            } 
          } 
      return (Result);} 
     } 
  else{ if (pe->nbPendingVarEvent > 0) 
    { { OID Result = 0;
        { OID  qtest;
          { { InstantiationStack * g0118 = pe->instEvtStack;
              if (g0118->sLastRead != g0118->sLastPushed)
               qtest = _oid_(g0118);
              else { BoundEventQueue * g0119 = pe->boundEvtQueue;
                  if (g0119->qLastRead != g0119->qLastEnqueued)
                   qtest = _oid_(g0119);
                  else { RemovalEventQueue * g0120 = pe->removalEvtQueue;
                      if (g0120->qLastRead != g0120->qLastEnqueued)
                       qtest = _oid_(g0120);
                      else qtest = CNULL;
                        } 
                    } 
                } 
            GC_OID(qtest);} 
          if (qtest != CNULL)
           { EventCollection * q = OBJECT(EventCollection,qtest);
            Result = _oid_(q);
            } 
          else { close_exception(((general_error *) (*Core._general_error)(_string_("~S pending var events but none in the queues"),
                _oid_(list::alloc(1,pe->nbPendingVarEvent)))));
              Result = CNULL;
              } 
            } 
        return (Result);} 
       } 
    else{ GC_BIND;
      { OID Result = 0;
        { ConstAwakeEventQueue * g0121 = pe->delayedConst1;
          if (choco_isEmpty_ConstAwakeEventQueue(g0121) != CTRUE)
           Result = _oid_(g0121);
          else { ConstAwakeEventQueue * g0122 = pe->delayedConst2;
              if (choco_isEmpty_ConstAwakeEventQueue(g0122) != CTRUE)
               Result = _oid_(g0122);
              else { ConstAwakeEventQueue * g0123 = pe->delayedConst3;
                  if (choco_isEmpty_ConstAwakeEventQueue(g0123) != CTRUE)
                   Result = _oid_(g0123);
                  else { ConstAwakeEventQueue * g0124 = pe->delayedConst4;
                      if (choco_isEmpty_ConstAwakeEventQueue(g0124) != CTRUE)
                       Result = _oid_(g0124);
                      else Result = CNULL;
                        } 
                    } 
                } 
            } 
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


// v1.013: fast dispatch of generic propagation engine methods