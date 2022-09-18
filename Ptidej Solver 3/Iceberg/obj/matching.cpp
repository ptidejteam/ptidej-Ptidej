/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Iceberg\v0.95\matching.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:08:22 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>
#include <ice.h>

// ********************************************************************
// * ICE: global constraints for OCRE, version 1.001 03/09/2001       *
// *        requires Claire v2.5, CHOCO v1.08                         *
// * file: match.cl                                                   *
// *    assignment constraints (bipartite matching and flow)          *
// * Copyright (C) Bouygues, 2001, see readme.txt                     *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: utils                                                  *
// *    ------ graphs --------                                        *
// *   Part 2: bipartite graph management                             *
// *   Part 3: flow and residual graph management                     *
// *   Part 4: specific matching (vs. flow) graphs                    *
// *   Part 5: computing a reference flow                             *
// *   Part 6: computing the st. conn. comp. of the residual graph    *
// *    ------ propagation --------                                   *
// *   Part 7: achieving generalized arc consistency (Regin 94/96)    *
// *   Part 8: standard alldiff constraint with generalized AC        *
// *   Part 9: dual models for permutation constraints                *
// *   Part 10:global cardinality constraint (gcc, Regin 96)          *
// --------------------------------------------------------------------
/* The c++ function for: showIglooLicense(_CL_obj:void) [] */
OID  claire_showIglooLicense_void()
{ princ_string("Iceberg version ");
  princ_float(0.8);
  return (_void_(princ_string(", Copyright (C) 2000-02 Bouygues e-lab\n")));} 


// ********************************************************************
// *   Part 1: utils                                                  *
// ********************************************************************
/* The c++ function for: getSize(ic:IntCollection) [] */
int  ice_getSize_IntCollection(IntCollection *ic)
{ return (ic->nbElts);} 


// ----- utils: stack operations --------
/* The c++ function for: close(s:IntStack) [] */
IntStack * claire_close_IntStack(IntStack *s)
{ (s->contents = make_list_integer2(s->maxsize,Kernel._integer,0));
  return (s);} 


/* The c++ function for: set!(s:IntStack) [] */
OID  claire_set_I_IntStack(IntStack *s)
{ GC_BIND;
  { OID Result = 0;
    { list * V_CL0083;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0082 = s->nbElts;
          { OID gc_local;
            while ((i <= g0082))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(s->contents))[i]);
              ++i;
              } 
            } 
          } 
        V_CL0083 = GC_OBJECT(list,i_bag);
        } 
      
      Result=_oid_(V_CL0083);} 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: clear(s:IntStack) [] */
void  ice_clear_IntStack(IntStack *s)
{ (s->nbElts = 0);
  } 


/* The c++ function for: pop(s:IntStack) [] */
int  ice_pop_IntStack(IntStack *s)
{ (s->nbElts = (s->nbElts-1));
  return ((*(s->contents))[(s->nbElts+1)]);} 


/* The c++ function for: push(s:IntStack,x:integer) [] */
void  ice_push_IntStack(IntStack *s,int x)
{ (s->nbElts = (s->nbElts+1));
  ((*(s->contents))[s->nbElts]=x);
  } 


// ----- utils: queue operations --------
/* The c++ function for: close(q:IntQueue) [] */
IntQueue * claire_close_IntQueue(IntQueue *q)
{ (q->contents = make_list_integer2(q->maxsize,Kernel._integer,0));
  (q->onceinqueue = make_list_integer2(q->maxsize,Kernel._boolean,Kernel.cfalse));
  return (q);} 


/* The c++ function for: push(q:IntQueue,x:integer) [] */
void  ice_push_IntQueue(IntQueue *q,int x)
{ ;((*(q->onceinqueue))[x]=Kernel.ctrue);
  if ((*(q->contents))[x] == 0)
   { if (q->nbElts == 0)
     ((*(q->contents))[x]=x);
    else { ((*(q->contents))[x]=(*(q->contents))[q->last]);
        ((*(q->contents))[q->last]=x);
        } 
      (q->last = x);
    (q->nbElts = (q->nbElts+1));
    } 
  } 


/* The c++ function for: pop(q:IntQueue) [] */
int  ice_pop_IntQueue(IntQueue *q)
{ { int Result = 0;
    { int  x = (*(q->contents))[q->last];
      (q->nbElts = (q->nbElts-1));
      ((*(q->contents))[q->last]=(*(q->contents))[x]);
      ((*(q->contents))[x]=0);
      Result = x;
      } 
    return (Result);} 
  } 


/* The c++ function for: init(q:IntQueue) [] */
void  ice_init_IntQueue(IntQueue *q)
{ (q->nbElts = 0);
  { int  i = 1;
    int  g0084 = q->maxsize;
    { while ((i <= g0084))
      { ((*(q->onceinqueue))[i]=Kernel.cfalse);
        ((*(q->contents))[i]=0);
        ++i;
        } 
      } 
    } 
  } 


// ********************************************************************
// *   Part 2: bipartite graph management                             *
// ********************************************************************
// An abstract class encoding assignment graphs
//  (matching each left vertex with one single right vertex)
// componentOrder[i,j]=true <=> there exists an edge in the SCC graph from component i to component j
// in addition, updates to refMatch are also backtrackably stored
// debug methods
/* The c++ function for: showRefAssignment(c:AbstractBipartiteGraph) [] */
void  ice_showRefAssignment_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ GC_BIND;
  princ_string("F&F matching:");
  print_any(_oid_(c->isa));
  princ_string(" ");
  { OID  g0086UU;
    { list * V_CL0087;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0085 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0085))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast(_oid_(list::alloc(2,i,((OID *) c->refMatch)[i])));
              ++i;
              } 
            } 
          } 
        V_CL0087 = GC_OBJECT(list,i_bag);
        } 
      
      g0086UU=_oid_(V_CL0087);} 
    print_any(g0086UU);
    } 
  princ_string("\n");
  GC_UNBIND;} 


/* The c++ function for: showSCCDecomposition(c:AbstractBipartiteGraph) [] */
void  ice_showSCCDecomposition_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ GC_BIND;
  { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    int  nbComp = c->currentComponent;
    princ_string("Component graph with ");
    print_any(nbComp);
    princ_string(" components");
    { int  comp = 1;
      int  g0088 = nbComp;
      { OID gc_local;
        while ((comp <= g0088))
        { GC_LOOP;
          princ_string("NODE ");
          print_any(comp);
          princ_string(": left:");
          { OID  g0091UU;
            { list * V_CL0092;{ list * i_out = list::empty(Kernel.emptySet);
                { int  i = 1;
                  int  g0089 = n1;
                  { OID gc_local;
                    while ((i <= g0089))
                    { // HOHO, GC_LOOP not needed !
                      if (((OID *) c->component)[i] == comp)
                       i_out->addFast(i);
                      ++i;
                      } 
                    } 
                  } 
                V_CL0092 = GC_OBJECT(list,i_out);
                } 
              
              g0091UU=_oid_(V_CL0092);} 
            print_any(g0091UU);
            } 
          princ_string(", right:");
          { OID  g0093UU;
            { { list * V_CL0094;{ bag *v_list; OID v_val;
                  OID j,CLcount;
                  { list * j_out = list::empty(Kernel.emptySet);
                    { int  j = 1;
                      int  g0090 = n2;
                      { OID gc_local;
                        while ((j <= g0090))
                        { // HOHO, GC_LOOP not needed !
                          if (((OID *) c->component)[(j+n1)] == comp)
                           j_out->addFast(j);
                          ++j;
                          } 
                        } 
                      } 
                    v_list = GC_OBJECT(list,j_out);
                    } 
                   V_CL0094 = v_list->clone();
                  for (CLcount= 1; CLcount <= v_list->length; CLcount++)
                  { j = (*(v_list))[CLcount];
                    v_val = ((j+c->minValue)-1);
                    
                    (*((list *) V_CL0094))[CLcount] = v_val;} 
                  } 
                
                g0093UU=_oid_(V_CL0094);} 
              GC_OID(g0093UU);} 
            print_any(g0093UU);
            } 
          princ_string("\n");
          princ_string("  predecessors: ");
          print_any(comp);
          princ_string("\n");
          ++comp;
          GC_UNLOOP;} 
        } 
      } 
    } 
  GC_UNBIND;} 


// we consider a flow in the graph by adding a source linked to all right vertices
// and a sink linked to all left vertices
//
// accessing the edges of the bipartite graph from the left vertices
//    access from the left vertex set: reading domains of modeling variables
/* The c++ function for: mayMatch(c:AbstractBipartiteGraph,i:integer) [] */
list * ice_mayMatch_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i)
{ GC_BIND;
  { list *Result ;
    { list * j_bag = list::empty(Kernel._integer);
      { IntVar * g0095 = OBJECT(IntVar,(*(c->vars))[i]);
        AbstractIntDomain * g0096 = g0095->bucket;
        if (g0096 == (NULL))
         { int  j = g0095->inf->latestValue;
          { OID gc_local;
            while ((j <= g0095->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              j_bag->addFast(((j-c->minValue)+1));
              j= ((g0095->inf->latestValue <= (j+1)) ?
                (j+1) :
                g0095->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0096->isa,choco._LinkedListIntDomain))
         { int  j = g0095->inf->latestValue;
          { OID gc_local;
            while ((j <= g0095->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              j_bag->addFast(((j-c->minValue)+1));
              j= choco.getNextValue->fcall(((int) g0096),((int) j));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(j);
            bag *j_support;
            j_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0096)))));
            for (START(j_support); NEXT(j);)
            { GC_LOOP;
              j_bag->addFast(((j-c->minValue)+1));
              GC_UNLOOP;} 
            } 
          } 
      Result = GC_OBJECT(list,j_bag);
      } 
    GC_UNBIND; return (Result);} 
  } 


// reverse: accessing the edges of the bipartite graph from the left vertices
//      iterating over the variables (left vertex set) and reading their domains
//  note: this could be improved for Permutation: choco/domain(c.vars[j + c.nbLeftVertices])
//        but we do not want to have multiple versions of this function to choose from 
/* The c++ function for: mayInverseMatch(c:AbstractBipartiteGraph,j:integer) [] */
list * ice_mayInverseMatch_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j)
{ GC_BIND;
  { list *Result ;
    { list * i_out = list::empty(Kernel._integer);
      { int  i = 1;
        int  g0097 = c->nbLeftVertices;
        { OID gc_local;
          while ((i <= g0097))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[i],
              ((j+c->minValue)-1)))) == CTRUE)
             i_out->addFast(i);
            ++i;
            } 
          } 
        } 
      Result = GC_OBJECT(list,i_out);
      } 
    GC_UNBIND; return (Result);} 
  } 


// accessing the right vertex matched to i
/* The c++ function for: match(c:AbstractBipartiteGraph,i:integer) [] */
int  ice_match_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i)
{ return (((OID *) c->refMatch)[i]);} 


// integrity check: checking that the flow is indeed maximal (yielding an assignment)
/* The c++ function for: choco/checkFlow(c:AbstractBipartiteGraph) [] */
void  choco_checkFlow_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    (ice.CHK->value= _oid_(c));
    ;} 
  } 


// ********************************************************************
// *   Part 3: flow and residual graph management                     *
// ********************************************************************
// a flow is built in the bipartite graph from the reference matching
//          match(i) = j <=> flow(j to i) = 1
//    from a source linked to all right vertices and a sink linked to all left vertices
//    Yes, this IS counter-intuitive, the flow goes from left to right
//    but this makes the job much easier for gcc in order to compute compatible flows
//    (with lower bounds on the edges from the source to the right vertices)
// whether the flow from i (a left vertex) to the sink may be increased
/* The c++ function for: mayGrowFlowToSink(c:AbstractBipartiteGraph,i:integer) [] */
ClaireBoolean * ice_mayGrowFlowToSink_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int i)
{ return (equal(((OID *) c->refMatch)[i],0));} 


// whether the flow from j (a right vertex) to i (a left vertex) may be increased
// (the additional flow is able to arrive to j, we don't care yet whether it will be able to leave i)
/* The c++ function for: mayGrowFlowBetween(c:AbstractBipartiteGraph,j:integer,i:integer) [] */
ClaireBoolean * ice_mayGrowFlowBetween_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j,int i)
{ return (_I_equal_any(((OID *) c->refMatch)[i],j));} 


// whether the flow from j (a right vertex) to i (a left vertex) may be increased
/* The c++ function for: mayDiminishFlowBetween(c:AbstractBipartiteGraph,j:integer,i:integer) [] */
ClaireBoolean * ice_mayDiminishFlowBetween_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int j,int i)
{ return (equal(((OID *) c->refMatch)[i],j));} 


// ********************************************************************
// *   Part 4: specific matching (vs. flow) graphs                    *
// ********************************************************************
// several classes of AbstractBipartiteGraph will be defined:
//   they must all implement:
//     inlined methods:
//        increaseMatchingSize, decreaseMatchingSize,
//        mayDiminishFlowFromSource, mayGrowFlowFromSource, mustGrowFlowFromSource
//        deleteMatch, setMatch, 
//     std methods:
//        setMatch
// a subclass that implements matchings (and not flows)
// the reverse assignment is stored
// updates to refInverseMatch are also backtrackably stored
// accessing the left vertex matched to j
/* The c++ function for: inverseMatch(c:AbstractBipartiteMatching,j:integer) [] */
int  ice_inverseMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ return (((OID *) c->refInverseMatch)[j]);} 


// updates the matching size when one more left vertex is matched with j
/* The c++ function for: increaseMatchingSize(c:AbstractBipartiteMatching,j:integer) [] */
void  ice_increaseMatchingSize_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ STOREI(c->matchingSize,(c->matchingSize+1));
  } 


// updates the matching size when one more left vertex is de-matched with j
/* The c++ function for: decreaseMatchingSize(c:AbstractBipartiteMatching,j:integer) [] */
void  ice_decreaseMatchingSize_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ STOREI(c->matchingSize,(c->matchingSize-1));
  } 


// removing the arc i-j from the reference matching & update matchingSize
// note (v0.6): this function may be called twice without damage
/* The c++ function for: deleteMatch(c:AbstractBipartiteMatching,i:integer,j:integer) [] */
void  ice_deleteMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j)
{ if (j == ((OID *) c->refMatch)[i])
   { STOREI(c->refMatch[i],0);
    STOREI(c->refInverseMatch[j],0);
    STOREI(c->matchingSize,(c->matchingSize-1));
    } 
  } 


// adding the arc i-j in the reference matching without any updates
/* The c++ function for: putRefMatch(c:AbstractBipartiteMatching,i:integer,j:integer) [] */
void  ice_putRefMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j)
{ STOREI(c->refMatch[i],j);
  STOREI(c->refInverseMatch[j],i);
  } 


// adding the arc i-j in the reference matching & update matchingSize
/* The c++ function for: setMatch(c:AbstractBipartiteMatching,i:integer,j:integer) [] */
void  ice_setMatch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i,int j)
{ { int  j0 = ((OID *) c->refMatch)[i];
    int  i0 = ((OID *) c->refInverseMatch)[j];
    if (j0 != j)
     { if (j0 > 0)
       { STOREI(c->refInverseMatch[j0],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        } 
      if (i0 > 0)
       { STOREI(c->refMatch[i0],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        } 
      STOREI(c->refMatch[i],j);
      STOREI(c->refInverseMatch[j],i);
      STOREI(c->matchingSize,(c->matchingSize+1));
      } 
    } 
  } 


// whether the flow from the source to j (a right vertex) may be decreased
/* The c++ function for: mayDiminishFlowFromSource(c:AbstractBipartiteMatching,j:integer) [] */
ClaireBoolean * ice_mayDiminishFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ return (_I_equal_any(((OID *) c->refInverseMatch)[j],0));} 


// whether the flow from the source to j (a right vertex) may be increased
/* The c++ function for: mayGrowFlowFromSource(c:AbstractBipartiteMatching,j:integer) [] */
ClaireBoolean * ice_mayGrowFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ return (equal(((OID *) c->refInverseMatch)[j],0));} 


// whether the flow from the source to j (a right vertex) must be increased in order
// to get a maximal (sink/left vertex set saturating) flow
/* The c++ function for: mustGrowFlowFromSource(c:AbstractBipartiteMatching,j:integer) [] */
ClaireBoolean * ice_mustGrowFlowFromSource_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int j)
{ return (CFALSE);} 


// safety check: the matching is indeed a perfect matching
// yet another even more specific kind of graph: balanced bipartite matching
//   (such that nbLeftVertices = nbRightVertices).
// a general assignment constraint with constraints on the flow bounds
// a flag used when searching for augmenting paths
// updates to flow are backtrackably stored
// adding the arc i-j in the reference matching & update matchingSize
// noet: in case this method makes the flow incompatible (going below minflow), we do not
// set the edge in the matching, so that the flow may be repaired into a compatible one while
// being augmented
/* The c++ function for: setMatch(c:AbstractBipartiteFlow,i:integer,j:integer) [] */
void  ice_setMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j)
{ { int  j0 = ((OID *) c->refMatch)[i];
    if (j0 != j)
     { if (j0 > 0)
       { STOREI(c->refMatch[i],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        STOREI(c->flow[j0],((((OID *) c->flow)[j0])-1));
        } 
      if ((((OID *) c->flow)[j] < ((OID *) c->maxFlow)[j]) && 
          ((j0 == 0) || 
              (((OID *) c->minFlow)[j0] <= ((OID *) c->flow)[j0])))
       { STOREI(c->refMatch[i],j);
        STOREI(c->matchingSize,(c->matchingSize+1));
        STOREI(c->flow[j],((((OID *) c->flow)[j])+1));
        } 
      } 
    } 
  } 


// removing the arc i-j from the reference matching & update matchingSize
// note (v0.6): this function may be called twice without damage
/* The c++ function for: deleteMatch(c:AbstractBipartiteFlow,i:integer,j:integer) [] */
void  ice_deleteMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j)
{ if (j == ((OID *) c->refMatch)[i])
   { STOREI(c->refMatch[i],0);
    STOREI(c->matchingSize,(c->matchingSize-1));
    STOREI(c->flow[j],((((OID *) c->flow)[j])-1));
    } 
  } 


// adding the arc i-j in the reference matching without any updates
/* The c++ function for: putRefMatch(c:AbstractBipartiteFlow,i:integer,j:integer) [] */
void  ice_putRefMatch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i,int j)
{ STOREI(c->refMatch[i],j);
  } 


/* The c++ function for: mayDiminishFlowFromSource(c:AbstractBipartiteFlow,j:integer) [] */
ClaireBoolean * ice_mayDiminishFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j)
{ return (_sup_integer(((OID *) c->flow)[j],((OID *) c->minFlow)[j]));} 


/* The c++ function for: mayGrowFlowFromSource(c:AbstractBipartiteFlow,j:integer) [] */
ClaireBoolean * ice_mayGrowFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j)
{ return (_inf_integer(((OID *) c->flow)[j],((OID *) c->maxFlow)[j]));} 


/* The c++ function for: mustGrowFlowFromSource(c:AbstractBipartiteFlow,j:integer) [] */
ClaireBoolean * ice_mustGrowFlowFromSource_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j)
{ return (_inf_integer(((OID *) c->flow)[j],((OID *) c->minFlow)[j]));} 


// updates the matching size when one more left vertex is matched with j
/* The c++ function for: increaseMatchingSize(c:AbstractBipartiteFlow,j:integer) [] */
void  ice_increaseMatchingSize_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j)
{ STOREI(c->matchingSize,(c->matchingSize+1));
  STOREI(c->flow[j],((((OID *) c->flow)[j])+1));
  } 


/* The c++ function for: decreaseMatchingSize(c:AbstractBipartiteFlow,j:integer) [] */
void  ice_decreaseMatchingSize_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int j)
{ STOREI(c->matchingSize,(c->matchingSize-1));
  STOREI(c->flow[j],((((OID *) c->flow)[j])-1));
  } 


// ********************************************************************
// *   Part 5: computing a reference flow                             *
// ********************************************************************
// The exact same code is copied twice (for flows and matchings), but will be specialized differently thanks
// to inline expansions.
// First pass: use Ford & Fulkerson algorithm to compute a reference flow (assignment)
//    finds an augmenting path using a fifo queue (returns 0 if none found, otherwise the end of the path)
/* The c++ function for: findAlternatingPath(c:AbstractBipartiteMatching) [] */
int  ice_findAlternatingPath_AbstractBipartiteMatching(AbstractBipartiteMatching *c)
{ GC_BIND;
  ;{ int Result = 0;
    { int  eopath = 0;
      IntQueue * q = GC_OBJECT(IntQueue,c->queue);
      int  n = c->nbLeftVertices;
      ice_init_IntQueue(q);
      { int  j = 1;
        int  g0098 = c->nbRightVertices;
        { OID gc_local;
          while ((j <= g0098))
          { // HOHO, GC_LOOP not needed !
            if (CFALSE == CTRUE)
             ice_push_IntQueue(q,(j+n));
            ++j;
            } 
          } 
        } 
      if (ice_getSize_IntCollection(q) == 0)
       { int  j = 1;
        int  g0099 = c->nbRightVertices;
        { OID gc_local;
          while ((j <= g0099))
          { // HOHO, GC_LOOP not needed !
            if (((OID *) c->refInverseMatch)[j] == 0)
             ice_push_IntQueue(q,(j+n));
            ++j;
            } 
          } 
        } 
      { OID gc_local;
        while ((ice_getSize_IntCollection(q) > 0))
        { // HOHO, GC_LOOP not needed !
          { int  x = ice_pop_IntQueue(q);
            if (x > n)
             { x= (x-n);
              { ClaireBoolean * g0102I;
                { OID  g0103UU;
                  { int  y = 1;
                    int  g0101 = c->nbLeftVertices;
                    { OID gc_local;
                      g0103UU= _oid_(CFALSE);
                      while ((y <= g0101))
                      { // HOHO, GC_LOOP not needed !
                        if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[y],
                          ((x+c->minValue)-1)))) == CTRUE)
                         { if ((((OID *) c->refMatch)[y] != x) && 
                              (not_any((*(q->onceinqueue))[y]) == CTRUE))
                           { (((OID *) c->left2rightArc)[y] = x);
                            if (((OID *) c->refMatch)[y] == 0)
                             { eopath= y;
                              { g0103UU = Kernel.ctrue;
                                break;} 
                              } 
                            else ice_push_IntQueue(q,y);
                              } 
                          ;} 
                        ++y;
                        } 
                      } 
                    } 
                  g0102I = boolean_I_any(g0103UU);
                  } 
                
                if (g0102I == CTRUE) { ;break;} 
                  } 
              } 
            else { { int  y = ((OID *) c->refMatch)[x];
                  if (not_any((*(q->onceinqueue))[(y+n)]) == CTRUE)
                   { (((OID *) c->right2leftArc)[y] = x);
                    ice_push_IntQueue(q,(y+n));
                    } 
                  } 
                } 
              } 
          } 
        } 
      Result = eopath;
      } 
    GC_UNBIND; return (Result);} 
  } 


// augment the matching along one alternating path
// note: throughout the following code, we assume
//    (1 <= x <= c.nbLeftVertices), (1 <= y <= c.nbRightVertices)
/* The c++ function for: choco/augment(c:AbstractBipartiteMatching,x:integer) [] */
void  choco_augment_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int x)
{ { int  y = ((OID *) c->left2rightArc)[x];
    { while ((((OID *) c->refInverseMatch)[y] != 0))
      { STOREI(c->refMatch[x],y);
        STOREI(c->refInverseMatch[y],x);
        x= ((OID *) c->right2leftArc)[y];
        y= ((OID *) c->left2rightArc)[x];
        ;} 
      } 
    STOREI(c->refMatch[x],y);
    STOREI(c->refInverseMatch[y],x);
    STOREI(c->matchingSize,(c->matchingSize+1));
    } 
  } 


// keeps augmenting the flow until a maximal flow is reached
/* The c++ function for: augmentFlow(c:AbstractBipartiteMatching) [] */
void  ice_augmentFlow_AbstractBipartiteMatching_ice(AbstractBipartiteMatching *c)
{ { int  eopath = ice_findAlternatingPath_AbstractBipartiteMatching(c);
    int  n1 = c->nbLeftVertices;
    if (c->matchingSize < n1)
     ;{ OID gc_local;
      while ((eopath > 0))
      { // HOHO, GC_LOOP not needed !
        choco_augment_AbstractBipartiteMatching(c,eopath);
        eopath= ice_findAlternatingPath_AbstractBipartiteMatching(c);
        } 
      } 
    if (c->matchingSize < n1)
     { choco_raiseContradiction_AbstractConstraint(c);
      } 
    else { { int  i = 1;
          int  g0104 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0104))
            { // HOHO, GC_LOOP not needed !
              ++i;
              } 
            } 
          } 
        { int  g0105 = c->nbLeftVertices;
          int  g0106 = c->nbRightVertices;
          (ice.CHK->value= _oid_(c));
          ;} 
        } 
      } 
  } 


// --------------------------------------------------------------------------
//    EXACT SAME CODE FOR FLOWS (GCC)
// --------------------------------------------------------------------------
/* The c++ function for: findAlternatingPath(c:AbstractBipartiteFlow) [] */
int  ice_findAlternatingPath_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ GC_BIND;
  ;{ int Result = 0;
    { int  eopath = 0;
      IntQueue * q = GC_OBJECT(IntQueue,c->queue);
      int  n = c->nbLeftVertices;
      ice_init_IntQueue(q);
      { int  j = 1;
        int  g0109 = c->nbRightVertices;
        { OID gc_local;
          while ((j <= g0109))
          { // HOHO, GC_LOOP not needed !
            if (((OID *) c->flow)[j] < ((OID *) c->minFlow)[j])
             ice_push_IntQueue(q,(j+n));
            ++j;
            } 
          } 
        } 
      if (ice_getSize_IntCollection(q) == 0)
       { (c->compatibleFlow = CTRUE);
        { int  j = 1;
          int  g0110 = c->nbRightVertices;
          { OID gc_local;
            while ((j <= g0110))
            { // HOHO, GC_LOOP not needed !
              if (((OID *) c->flow)[j] < ((OID *) c->maxFlow)[j])
               ice_push_IntQueue(q,(j+n));
              ++j;
              } 
            } 
          } 
        } 
      else (c->compatibleFlow = CFALSE);
        { OID gc_local;
        while ((ice_getSize_IntCollection(q) > 0))
        { // HOHO, GC_LOOP not needed !
          { int  x = ice_pop_IntQueue(q);
            if (x > n)
             { x= (x-n);
              { ClaireBoolean * g0113I;
                { OID  g0114UU;
                  { int  y = 1;
                    int  g0112 = c->nbLeftVertices;
                    { OID gc_local;
                      g0114UU= _oid_(CFALSE);
                      while ((y <= g0112))
                      { // HOHO, GC_LOOP not needed !
                        if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[y],
                          ((x+c->minValue)-1)))) == CTRUE)
                         { if ((((OID *) c->refMatch)[y] != x) && 
                              (not_any((*(q->onceinqueue))[y]) == CTRUE))
                           { (((OID *) c->left2rightArc)[y] = x);
                            if (((OID *) c->refMatch)[y] == 0)
                             { eopath= y;
                              { g0114UU = Kernel.ctrue;
                                break;} 
                              } 
                            else ice_push_IntQueue(q,y);
                              } 
                          ;} 
                        ++y;
                        } 
                      } 
                    } 
                  g0113I = boolean_I_any(g0114UU);
                  } 
                
                if (g0113I == CTRUE) { ;break;} 
                  } 
              } 
            else { { int  y = ((OID *) c->refMatch)[x];
                  if (not_any((*(q->onceinqueue))[(y+n)]) == CTRUE)
                   { (((OID *) c->right2leftArc)[y] = x);
                    ice_push_IntQueue(q,(y+n));
                    } 
                  } 
                } 
              } 
          } 
        } 
      Result = eopath;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: choco/augment(c:AbstractBipartiteFlow,x:integer) [] */
void  choco_augment_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int x)
{ { int  y = ((OID *) c->left2rightArc)[x];
    if (c->compatibleFlow == CTRUE)
     { while ((((OID *) c->flow)[y] >= ((OID *) c->maxFlow)[y]))
      { STOREI(c->refMatch[x],y);
        x= ((OID *) c->right2leftArc)[y];
        y= ((OID *) c->left2rightArc)[x];
        ;} 
      } 
    else { while ((((OID *) c->flow)[y] >= ((OID *) c->minFlow)[y]))
        { STOREI(c->refMatch[x],y);
          x= ((OID *) c->right2leftArc)[y];
          y= ((OID *) c->left2rightArc)[x];
          ;} 
        } 
      STOREI(c->refMatch[x],y);
    STOREI(c->matchingSize,(c->matchingSize+1));
    STOREI(c->flow[y],((((OID *) c->flow)[y])+1));
    } 
  } 


// keeps augmenting the flow until a maximal flow is reached
/* The c++ function for: augmentFlow(c:AbstractBipartiteFlow) [] */
void  ice_augmentFlow_AbstractBipartiteFlow_ice(AbstractBipartiteFlow *c)
{ { int  eopath = ice_findAlternatingPath_AbstractBipartiteFlow(c);
    int  n1 = c->nbLeftVertices;
    if (c->matchingSize < n1)
     ;{ OID gc_local;
      while ((eopath > 0))
      { // HOHO, GC_LOOP not needed !
        choco_augment_AbstractBipartiteFlow(c,eopath);
        eopath= ice_findAlternatingPath_AbstractBipartiteFlow(c);
        } 
      } 
    if (c->matchingSize < n1)
     { choco_raiseContradiction_AbstractConstraint(c);
      } 
    else { { int  i = 1;
          int  g0115 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0115))
            { // HOHO, GC_LOOP not needed !
              ++i;
              } 
            } 
          } 
        { int  g0116 = c->nbLeftVertices;
          int  g0117 = c->nbRightVertices;
          (ice.CHK->value= _oid_(c));
          ;} 
        } 
      } 
  } 


/* The c++ function for: turnIntoCompatibleFlow(c:AbstractBipartiteFlow) [] */
void  ice_turnIntoCompatibleFlow_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ { int  eopath = ice_findAlternatingCycle_AbstractBipartiteFlow(c);
    { OID gc_local;
      while ((eopath > 0))
      { // HOHO, GC_LOOP not needed !
        ice_circulate_AbstractBipartiteFlow(c,eopath);
        eopath= ice_findAlternatingCycle_AbstractBipartiteFlow(c);
        } 
      } 
    { ClaireBoolean * g0125I;
      { OID  g0126UU;
        { int  j = 1;
          int  g0120 = c->nbRightVertices;
          { OID gc_local;
            g0126UU= _oid_(CFALSE);
            while ((j <= g0120))
            { // HOHO, GC_LOOP not needed !
              if (((OID *) c->flow)[j] < ((OID *) c->minFlow)[j])
               { g0126UU = Kernel.ctrue;
                break;} 
              ++j;
              } 
            } 
          } 
        g0125I = boolean_I_any(g0126UU);
        } 
      
      if (g0125I == CTRUE) { choco_raiseContradiction_AbstractConstraint(c);
          } 
        else { { int  g0121 = c->nbLeftVertices;
          int  g0122 = c->nbRightVertices;
          (ice.CHK->value= _oid_(c));
          ;} 
        } 
      } 
    } 
  } 


// circulates the flow back along the alternate cycle 
//    this does not increase the matching size, but turns an incompatible flow 
//     -ie violating minflow requirements- into a compatible one
/* The c++ function for: circulate(c:AbstractBipartiteFlow,y:integer) [] */
void  ice_circulate_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int y)
{ STOREI(c->matchingSize,(c->matchingSize-1));
  STOREI(c->flow[y],((((OID *) c->flow)[y])-1));
  { while ((((OID *) c->flow)[y] >= ((OID *) c->maxFlow)[y]))
    { { int  x = ((OID *) c->right2leftArc)[y];
        y= ((OID *) c->left2rightArc)[x];
        STOREI(c->refMatch[x],y);
        ;} 
      } 
    } 
  { STOREI(c->matchingSize,(c->matchingSize+1));
    STOREI(c->flow[y],((((OID *) c->flow)[y])+1));
    } 
  } 


/* The c++ function for: findAlternatingCycle(c:AbstractBipartiteFlow) [] */
int  ice_findAlternatingCycle_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ GC_BIND;
  ;{ int Result = 0;
    { int  eopath = 0;
      IntQueue * q = GC_OBJECT(IntQueue,c->queue);
      int  n = c->nbLeftVertices;
      ice_init_IntQueue(q);
      { int  j = 1;
        int  g0127 = c->nbRightVertices;
        { OID gc_local;
          while ((j <= g0127))
          { // HOHO, GC_LOOP not needed !
            if (((OID *) c->flow)[j] < ((OID *) c->minFlow)[j])
             ice_push_IntQueue(q,(j+n));
            ++j;
            } 
          } 
        } 
      { OID gc_local;
        while ((ice_getSize_IntCollection(q) > 0))
        { // HOHO, GC_LOOP not needed !
          { int  x = ice_pop_IntQueue(q);
            if (x > n)
             { x= (x-n);
              { int  y = 1;
                int  g0129 = c->nbLeftVertices;
                { OID gc_local;
                  while ((y <= g0129))
                  { // HOHO, GC_LOOP not needed !
                    if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[y],
                      ((x+c->minValue)-1)))) == CTRUE)
                     { if ((((OID *) c->refMatch)[y] != x) && 
                          (not_any((*(q->onceinqueue))[y]) == CTRUE))
                       { (((OID *) c->left2rightArc)[y] = x);
                        ice_push_IntQueue(q,y);
                        } 
                      } 
                    ++y;
                    } 
                  } 
                } 
              } 
            else { { int  y = ((OID *) c->refMatch)[x];
                  if (not_any((*(q->onceinqueue))[(y+n)]) == CTRUE)
                   { (((OID *) c->right2leftArc)[y] = x);
                    if (boolean_I_any((*ice.mayDimishFlowFromSource)(_oid_(c),
                      y)) == CTRUE)
                     { eopath= y;
                      { ;break;} 
                      } 
                    else ice_push_IntQueue(q,(y+n));
                      } 
                  } 
                } 
              } 
          } 
        } 
      Result = eopath;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 6: computing the st. conn. comp. of the residual graph    *
// ********************************************************************
// computing the strongly connected components of the residual graph,
// then remove arcs connecting two different strongly connected components
//
// Computing the strongly connected components is done by an algorithm
// of Aho, Hopcroft, Ullman using depth first search (Cormen, Leiserson, p. 478, p. 489)
// initialize the graph data structure storing the SCC decomposition
/* The c++ function for: initSCCGraph(c:AbstractBipartiteGraph) [] */
void  ice_initSCCGraph_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ GC_BIND;
  { BoolMatrix2D * compOrder = GC_OBJECT(BoolMatrix2D,c->componentOrder);
    int  cci = 1;
    int  g0130 = c->currentComponent;
    { OID gc_local;
      while ((cci <= g0130))
      { // HOHO, GC_LOOP not needed !
        { int  ccj = 1;
          int  g0131 = c->currentComponent;
          { OID gc_local;
            while ((ccj <= g0131))
            { // HOHO, GC_LOOP not needed !
              if (ccj != cci)
               claire_store_BoolMatrix2D(compOrder,cci,ccj,CFALSE);
              ++ccj;
              } 
            } 
          } 
        ++cci;
        } 
      } 
    } 
  { int  i = 1;
    int  g0132 = c->nbVertices;
    { OID gc_local;
      while ((i <= g0132))
      { // HOHO, GC_LOOP not needed !
        (((OID *) c->component)[i] = 0);
        ++i;
        } 
      } 
    } 
  (c->currentComponent = 0);
  GC_UNBIND;} 


// adds a new vertex to the component graph (= a component = a set of s. connected vertices in the original graph) 
/* The c++ function for: addComponentVertex(c:AbstractBipartiteGraph) [] */
void  ice_addComponentVertex_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ (c->currentComponent = (c->currentComponent+1));
  } 


// add an edge in the component graph between compi nd compj: 
//   componentOrder stores the transitive closure of that graph
/* The c++ function for: addComponentEdge(c:AbstractBipartiteGraph,compi:integer,compj:integer) [] */
void  ice_addComponentEdge_AbstractBipartiteGraph(AbstractBipartiteGraph *c,int compi,int compj)
{ GC_BIND;
  { BoolMatrix2D * compOrder = GC_OBJECT(BoolMatrix2D,c->componentOrder);
    if (claire_read_BoolMatrix2D(compOrder,compi,compj) != CTRUE)
     { claire_store_BoolMatrix2D(compOrder,compi,compj,CTRUE);
      { int  compj2 = 1;
        int  g0133 = (compj-1);
        { OID gc_local;
          while ((compj2 <= g0133))
          { // HOHO, GC_LOOP not needed !
            if (claire_read_BoolMatrix2D(compOrder,compj,compj2) == CTRUE)
             claire_store_BoolMatrix2D(compOrder,compi,compj2,CTRUE);
            ++compj2;
            } 
          } 
        } 
      } 
    } 
  GC_UNBIND;} 


// seen[i] = false <=> color[i] = white (in book)
//         = true               % {gray, black}
/* The c++ function for: firstPassDFS(c:AbstractBipartiteMatching) [] */
void  ice_firstPassDFS_AbstractBipartiteMatching(AbstractBipartiteMatching *c)
{ { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    { int  i = 1;
      int  g0134 = c->nbVertices;
      { OID gc_local;
        while ((i <= g0134))
        { // HOHO, GC_LOOP not needed !
          (((OID *) c->finishDate)[i] = 0);
          (((OID *) c->seen)[i] = Kernel.cfalse);
          ++i;
          } 
        } 
      } 
    (c->time = 0);
    { int  i = 1;
      int  g0135 = c->nbVertices;
      { OID gc_local;
        while ((i <= g0135))
        { // HOHO, GC_LOOP not needed !
          ice_firstDFSearch_AbstractBipartiteMatching(c,i);
          ++i;
          } 
        } 
      } 
    } 
  } 


// the first search explores (DFS) the reduced graph
/* The c++ function for: firstDFSearch(c:AbstractBipartiteMatching,i:integer) [] */
void  ice_firstDFSearch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i)
{ if (not_any(((OID *) c->seen)[i]) == CTRUE)
   { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    (c->time = (c->time+1));
    (((OID *) c->seen)[i] = Kernel.ctrue);
    if (i <= n1)
     { ice_firstDFSearch_AbstractBipartiteMatching(c,((((OID *) c->refMatch)[i])+n1));
      } 
    else if (i < c->source)
     { { int  j = 1;
        int  g0137 = c->nbLeftVertices;
        { OID gc_local;
          while ((j <= g0137))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[j],
              (((i-n1)+c->minValue)-1)))) == CTRUE)
             { if (((OID *) c->refMatch)[j] != (i-n1))
               ice_firstDFSearch_AbstractBipartiteMatching(c,j);
              } 
            ++j;
            } 
          } 
        } 
      if (((OID *) c->refInverseMatch)[(i-n1)] != 0)
       ice_firstDFSearch_AbstractBipartiteMatching(c,c->source);
      } 
    else { int  j = 1;
        int  g0138 = n2;
        { OID gc_local;
          while ((j <= g0138))
          { // HOHO, GC_LOOP not needed !
            if (((OID *) c->refInverseMatch)[j] == 0)
             ice_firstDFSearch_AbstractBipartiteMatching(c,(j+n1));
            ++j;
            } 
          } 
        } 
      (c->time = (c->time+1));
    (((OID *) c->finishDate)[i] = c->time);
    } 
  } 


/* The c++ function for: secondPassDFS(c:AbstractBipartiteMatching) [] */
void  ice_secondPassDFS_AbstractBipartiteMatching(AbstractBipartiteMatching *c)
{ ;ice_initSCCGraph_AbstractBipartiteGraph(c);
  { OID gc_local;
    while ((CTRUE == CTRUE))
    { // HOHO, GC_LOOP not needed !
      { int  maxf = 0;
        int  rootOfComp = 0;
        { int  i = 1;
          int  g0139 = c->nbVertices;
          { OID gc_local;
            while ((i <= g0139))
            { // HOHO, GC_LOOP not needed !
              if ((((OID *) c->component)[i] == 0) && 
                  (((OID *) c->finishDate)[i] > maxf))
               { maxf= ((OID *) c->finishDate)[i];
                rootOfComp= i;
                } 
              ++i;
              } 
            } 
          } 
        if (maxf > 0)
         { (c->currentComponent = (c->currentComponent+1));
          ice_secondDFSearch_AbstractBipartiteMatching(c,rootOfComp);
          } 
        else { ;break;} 
          } 
      } 
    } 
  } 


// the second search explores (DFS) the inverse of the reduced graph
/* The c++ function for: secondDFSearch(c:AbstractBipartiteMatching,i:integer) [] */
void  ice_secondDFSearch_AbstractBipartiteMatching(AbstractBipartiteMatching *c,int i)
{ GC_BIND;
  { int  compi = ((OID *) c->component)[i];
    int  curComp = c->currentComponent;
    if (compi == 0)
     { int  n1 = c->nbLeftVertices;
      int  n2 = c->nbRightVertices;
      (((OID *) c->component)[i] = curComp);
      (c->currentNode = i);
      if (i <= n1)
       { IntVar * g0142 = OBJECT(IntVar,(*(c->vars))[i]);
        AbstractIntDomain * g0143 = GC_OBJECT(AbstractIntDomain,g0142->bucket);
        if (g0143 == (NULL))
         { int  g0141 = g0142->inf->latestValue;
          { OID gc_local;
            while ((g0141 <= g0142->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  j = ((g0141-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteMatching(c,(j+n1));
                } 
              g0141= ((g0142->inf->latestValue <= (g0141+1)) ?
                (g0141+1) :
                g0142->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0143->isa,choco._LinkedListIntDomain))
         { int  g0141 = g0142->inf->latestValue;
          { OID gc_local;
            while ((g0141 <= g0142->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  j = ((g0141-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteMatching(c,(j+n1));
                } 
              g0141= choco.getNextValue->fcall(((int) g0143),((int) g0141));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(g0141);
            bag *g0141_support;
            g0141_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0143)))));
            for (START(g0141_support); NEXT(g0141);)
            { GC_LOOP;
              { int  j = ((g0141-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteMatching(c,(j+n1));
                } 
              GC_UNLOOP;} 
            } 
          } 
      else if (i < c->source)
       { { int  j = 1;
          int  g0145 = c->nbLeftVertices;
          { OID gc_local;
            while ((j <= g0145))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[j],
                (((i-n1)+c->minValue)-1)))) == CTRUE)
               { if (((OID *) c->refMatch)[j] == (i-n1))
                 ice_secondDFSearch_AbstractBipartiteMatching(c,j);
                } 
              ++j;
              } 
            } 
          } 
        if (((OID *) c->refInverseMatch)[(i-n1)] == 0)
         ice_secondDFSearch_AbstractBipartiteMatching(c,c->source);
        } 
      else { int  j = 1;
          int  g0146 = n2;
          { OID gc_local;
            while ((j <= g0146))
            { // HOHO, GC_LOOP not needed !
              if (((OID *) c->refInverseMatch)[j] != 0)
               ice_secondDFSearch_AbstractBipartiteMatching(c,(j+n1));
              ++j;
              } 
            } 
          } 
        ;} 
    else if (compi < curComp)
     ice_addComponentEdge_AbstractBipartiteGraph(c,curComp,compi);
    else if (compi > curComp)
     close_exception(((general_error *) (*Core._general_error)(_string_("unexpected strong connection component of higher index ~S[cur:~S]"),
      _oid_(list::alloc(2,compi,curComp)))));
    } 
  GC_UNBIND;} 


// --------------------------------------------------------------------------
//    EXACT SAME CODE FOR FLOWS (GCC)
// --------------------------------------------------------------------------
/* The c++ function for: firstPassDFS(c:AbstractBipartiteFlow) [] */
void  ice_firstPassDFS_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    { int  i = 1;
      int  g0147 = c->nbVertices;
      { OID gc_local;
        while ((i <= g0147))
        { // HOHO, GC_LOOP not needed !
          (((OID *) c->finishDate)[i] = 0);
          (((OID *) c->seen)[i] = Kernel.cfalse);
          ++i;
          } 
        } 
      } 
    (c->time = 0);
    { int  i = 1;
      int  g0148 = c->nbVertices;
      { OID gc_local;
        while ((i <= g0148))
        { // HOHO, GC_LOOP not needed !
          ice_firstDFSearch_AbstractBipartiteFlow(c,i);
          ++i;
          } 
        } 
      } 
    } 
  } 


/* The c++ function for: firstDFSearch(c:AbstractBipartiteFlow,i:integer) [] */
void  ice_firstDFSearch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i)
{ if (not_any(((OID *) c->seen)[i]) == CTRUE)
   { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    (c->time = (c->time+1));
    (((OID *) c->seen)[i] = Kernel.ctrue);
    if (i <= n1)
     { ice_firstDFSearch_AbstractBipartiteFlow(c,((((OID *) c->refMatch)[i])+n1));
      } 
    else if (i < c->source)
     { { int  j = 1;
        int  g0150 = c->nbLeftVertices;
        { OID gc_local;
          while ((j <= g0150))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[j],
              (((i-n1)+c->minValue)-1)))) == CTRUE)
             { if (((OID *) c->refMatch)[j] != (i-n1))
               ice_firstDFSearch_AbstractBipartiteFlow(c,j);
              } 
            ++j;
            } 
          } 
        } 
      if (((OID *) c->flow)[(i-n1)] > ((OID *) c->minFlow)[(i-n1)])
       ice_firstDFSearch_AbstractBipartiteFlow(c,c->source);
      } 
    else { int  j = 1;
        int  g0151 = n2;
        { OID gc_local;
          while ((j <= g0151))
          { // HOHO, GC_LOOP not needed !
            if (((OID *) c->flow)[j] < ((OID *) c->maxFlow)[j])
             ice_firstDFSearch_AbstractBipartiteFlow(c,(j+n1));
            ++j;
            } 
          } 
        } 
      (c->time = (c->time+1));
    (((OID *) c->finishDate)[i] = c->time);
    } 
  } 


/* The c++ function for: secondPassDFS(c:AbstractBipartiteFlow) [] */
void  ice_secondPassDFS_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ ;ice_initSCCGraph_AbstractBipartiteGraph(c);
  { OID gc_local;
    while ((CTRUE == CTRUE))
    { // HOHO, GC_LOOP not needed !
      { int  maxf = 0;
        int  rootOfComp = 0;
        { int  i = 1;
          int  g0152 = c->nbVertices;
          { OID gc_local;
            while ((i <= g0152))
            { // HOHO, GC_LOOP not needed !
              if ((((OID *) c->component)[i] == 0) && 
                  (((OID *) c->finishDate)[i] > maxf))
               { maxf= ((OID *) c->finishDate)[i];
                rootOfComp= i;
                } 
              ++i;
              } 
            } 
          } 
        if (maxf > 0)
         { (c->currentComponent = (c->currentComponent+1));
          ice_secondDFSearch_AbstractBipartiteFlow(c,rootOfComp);
          } 
        else { ;break;} 
          } 
      } 
    } 
  } 


/* The c++ function for: secondDFSearch(c:AbstractBipartiteFlow,i:integer) [] */
void  ice_secondDFSearch_AbstractBipartiteFlow(AbstractBipartiteFlow *c,int i)
{ GC_BIND;
  { int  compi = ((OID *) c->component)[i];
    int  curComp = c->currentComponent;
    if (compi == 0)
     { int  n1 = c->nbLeftVertices;
      int  n2 = c->nbRightVertices;
      (((OID *) c->component)[i] = curComp);
      (c->currentNode = i);
      if (i <= n1)
       { IntVar * g0155 = OBJECT(IntVar,(*(c->vars))[i]);
        AbstractIntDomain * g0156 = GC_OBJECT(AbstractIntDomain,g0155->bucket);
        if (g0156 == (NULL))
         { int  g0154 = g0155->inf->latestValue;
          { OID gc_local;
            while ((g0154 <= g0155->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  j = ((g0154-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteFlow(c,(j+n1));
                } 
              g0154= ((g0155->inf->latestValue <= (g0154+1)) ?
                (g0154+1) :
                g0155->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0156->isa,choco._LinkedListIntDomain))
         { int  g0154 = g0155->inf->latestValue;
          { OID gc_local;
            while ((g0154 <= g0155->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              { int  j = ((g0154-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteFlow(c,(j+n1));
                } 
              g0154= choco.getNextValue->fcall(((int) g0156),((int) g0154));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(g0154);
            bag *g0154_support;
            g0154_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0156)))));
            for (START(g0154_support); NEXT(g0154);)
            { GC_LOOP;
              { int  j = ((g0154-c->minValue)+1);
                if (((OID *) c->refMatch)[i] != j)
                 ice_secondDFSearch_AbstractBipartiteFlow(c,(j+n1));
                } 
              GC_UNLOOP;} 
            } 
          } 
      else if (i < c->source)
       { { int  j = 1;
          int  g0158 = c->nbLeftVertices;
          { OID gc_local;
            while ((j <= g0158))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[j],
                (((i-n1)+c->minValue)-1)))) == CTRUE)
               { if (((OID *) c->refMatch)[j] == (i-n1))
                 ice_secondDFSearch_AbstractBipartiteFlow(c,j);
                } 
              ++j;
              } 
            } 
          } 
        if (((OID *) c->flow)[(i-n1)] < ((OID *) c->maxFlow)[(i-n1)])
         ice_secondDFSearch_AbstractBipartiteFlow(c,c->source);
        } 
      else { int  j = 1;
          int  g0159 = n2;
          { OID gc_local;
            while ((j <= g0159))
            { // HOHO, GC_LOOP not needed !
              if (((OID *) c->flow)[j] > ((OID *) c->minFlow)[j])
               ice_secondDFSearch_AbstractBipartiteFlow(c,(j+n1));
              ++j;
              } 
            } 
          } 
        ;} 
    else if (compi < curComp)
     ice_addComponentEdge_AbstractBipartiteGraph(c,curComp,compi);
    else if (compi > curComp)
     close_exception(((general_error *) (*Core._general_error)(_string_("unexpected strong connection component of higher index ~S[cur:~S]"),
      _oid_(list::alloc(2,compi,curComp)))));
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 7: achieving generalized arc consistency (Regin 94/96)    *
// ********************************************************************
// two methods used for detecting that an edge should be removed
// from the bipartite assignment graph
//    deleteMatch          -> removes it from the graph data strutures
//    deleteEdgeAndPublish -> same + publishes the information outside the constraint
// the event generated by the flow algorithm:
// discovering that an edge is no longer valid, and posting this event
// to the constraint solver: since we are already achieving GAC consistency
// in one single loop, there is no need to post a constAwake
// remove arcs connecting two different strongly connected components
/* The c++ function for: removeUselessEdges(c:AbstractBipartiteMatching) [] */
void  ice_removeUselessEdges_AbstractBipartiteMatching(AbstractBipartiteMatching *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  ;if (c->matchingSize < c->nbLeftVertices)
   { (*ice.augmentFlow)(_oid_(c));
    } 
  { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    ice_firstPassDFS_AbstractBipartiteMatching(c);
    ice_secondPassDFS_AbstractBipartiteMatching(c);
    { int  i = 1;
      int  g0160 = n1;
      { OID gc_local;
        while ((i <= g0160))
        { // HOHO, GC_LOOP not needed !
          ++i;
          } 
        } 
      } 
    { int  j = 1;
      int  g0161 = n2;
      { OID gc_local;
        while ((j <= g0161))
        { // HOHO, GC_LOOP not needed !
          ++j;
          } 
        } 
      } 
    { int  nkept = 0;
      int  ndiscard = 0;
      { int  i = 1;
        int  g0162 = c->nbLeftVertices;
        { OID gc_local;
          while ((i <= g0162))
          { GC_LOOP;
            { IntVar * g0165 = OBJECT(IntVar,(*(c->vars))[i]);
              AbstractIntDomain * g0166 = GC_OBJECT(AbstractIntDomain,g0165->bucket);
              if (g0166 == (NULL))
               { int  g0164 = g0165->inf->latestValue;
                { OID gc_local;
                  while ((g0164 <= g0165->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  j = ((g0164-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          (*ice.deleteEdgeAndPublish)(_oid_(c),
                            i,
                            j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    g0164= ((g0165->inf->latestValue <= (g0164+1)) ?
                      (g0164+1) :
                      g0165->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0166->isa,choco._LinkedListIntDomain))
               { int  g0164 = g0165->inf->latestValue;
                { OID gc_local;
                  while ((g0164 <= g0165->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  j = ((g0164-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          (*ice.deleteEdgeAndPublish)(_oid_(c),
                            i,
                            j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    g0164= choco.getNextValue->fcall(((int) g0166),((int) g0164));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(g0164);
                  bag *g0164_support;
                  g0164_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0166)))));
                  for (START(g0164_support); NEXT(g0164);)
                  { GC_LOOP;
                    { int  j = ((g0164-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          (*ice.deleteEdgeAndPublish)(_oid_(c),
                            i,
                            j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    GC_UNLOOP;} 
                  } 
                } 
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      ;} 
    } 
  GC_UNBIND;} 


// remove arcs connecting two different strongly connected components
/* The c++ function for: removeUselessEdges(c:AbstractBipartiteFlow) [] */
void  ice_removeUselessEdges_AbstractBipartiteFlow(AbstractBipartiteFlow *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  ;if (c->matchingSize < c->nbLeftVertices)
   { (*ice.augmentFlow)(_oid_(c));
    } 
  { int  g0167 = c->nbLeftVertices;
    int  g0168 = c->nbRightVertices;
    (ice.CHK->value= _oid_(c));
    ;} 
  { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    ice_firstPassDFS_AbstractBipartiteFlow(c);
    ice_secondPassDFS_AbstractBipartiteFlow(c);
    { int  i = 1;
      int  g0171 = n1;
      { OID gc_local;
        while ((i <= g0171))
        { // HOHO, GC_LOOP not needed !
          ++i;
          } 
        } 
      } 
    { int  j = 1;
      int  g0172 = n2;
      { OID gc_local;
        while ((j <= g0172))
        { // HOHO, GC_LOOP not needed !
          ++j;
          } 
        } 
      } 
    { int  nkept = 0;
      int  ndiscard = 0;
      { int  i = 1;
        int  g0173 = c->nbLeftVertices;
        { OID gc_local;
          while ((i <= g0173))
          { GC_LOOP;
            { IntVar * g0176 = OBJECT(IntVar,(*(c->vars))[i]);
              AbstractIntDomain * g0177 = GC_OBJECT(AbstractIntDomain,g0176->bucket);
              if (g0177 == (NULL))
               { int  g0175 = g0176->inf->latestValue;
                { OID gc_local;
                  while ((g0175 <= g0176->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  j = ((g0175-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          choco_deleteEdgeAndPublish_GlobalCardinality(((GlobalCardinality *) c),i,j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    g0175= ((g0176->inf->latestValue <= (g0175+1)) ?
                      (g0175+1) :
                      g0176->inf->latestValue );
                    } 
                  } 
                } 
              else if (INHERIT(g0177->isa,choco._LinkedListIntDomain))
               { int  g0175 = g0176->inf->latestValue;
                { OID gc_local;
                  while ((g0175 <= g0176->sup->latestValue))
                  { // HOHO, GC_LOOP not needed !
                    { int  j = ((g0175-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          choco_deleteEdgeAndPublish_GlobalCardinality(((GlobalCardinality *) c),i,j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    g0175= choco.getNextValue->fcall(((int) g0177),((int) g0175));
                    } 
                  } 
                } 
              else { OID gc_local;
                  ITERATE(g0175);
                  bag *g0175_support;
                  g0175_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0177)))));
                  for (START(g0175_support); NEXT(g0175);)
                  { GC_LOOP;
                    { int  j = ((g0175-c->minValue)+1);
                      if (j != ((OID *) c->refMatch)[i])
                       { if (((OID *) c->component)[i] != ((OID *) c->component)[(j+n1)])
                         { ++ndiscard;
                          choco_deleteEdgeAndPublish_GlobalCardinality(((GlobalCardinality *) c),i,j);
                          } 
                        else ++nkept;
                          } 
                      } 
                    GC_UNLOOP;} 
                  } 
                } 
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      ;} 
    } 
  GC_UNBIND;} 


// Achieves generalized arc consistency in one call
/* The c++ function for: choco/propagate(c:AbstractBipartiteGraph) [] */
void  choco_propagate_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ ;(*ice.removeUselessEdges)(_oid_(c));
  } 


/* The c++ function for: choco/getPriority(c:AbstractBipartiteGraph) [] */
int  choco_getPriority_AbstractBipartiteGraph_ice(AbstractBipartiteGraph *c)
{ return (2);} 


// generic constraint initialization
//    assumes that the slots nbLeftVertices/nbRightVertices are filled properly
// initialize the structure for strong connection decomposition (with an order matrix filled with false, but on the diagonal)
/* The c++ function for: choco/closeAssignmentConstraint(c:AbstractBipartiteGraph) [] */
void  choco_closeAssignmentConstraint_AbstractBipartiteGraph(AbstractBipartiteGraph *c)
{ GC_BIND;
  { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    int  nb = (n1+n2);
    (c->refMatch = make_array_integer(n1,Kernel._integer,0));
    STOREI(c->matchingSize,0);
    { AbstractBipartiteGraph * g0179; 
      IntQueue * g0180;
      g0179 = c;
      { IntQueue * _CL_obj = ((IntQueue *) GC_OBJECT(IntQueue,new_object_class(ice._IntQueue)));
        (_CL_obj->maxsize = nb);
        g0180 = claire_close_IntQueue(_CL_obj);
        } 
      (g0179->queue = g0180);} 
    (c->left2rightArc = make_array_integer(n1,Kernel._integer,0));
    (c->right2leftArc = make_array_integer(n2,Kernel._integer,0));
    if (INHERIT(c->isa,ice._AbstractBipartiteMatching))
     (CLREAD(AbstractBipartiteMatching,c,refInverseMatch) = make_array_integer(n2,Kernel._integer,0));
    (c->source = (nb+1));
    (c->nbVertices = (nb+1));
    (c->finishDate = make_array_integer((nb+1),Kernel._integer,0));
    (c->seen = make_array_integer((nb+1),Kernel._boolean,Kernel.cfalse));
    (c->component = make_array_integer((nb+1),Kernel._integer,0));
    { BoolMatrix2D * ord = GC_OBJECT(BoolMatrix2D,choco_make2DBoolMatrix_integer(1,
        (nb+1),
        1,
        (nb+1),
        CFALSE,
        CFALSE));
      { int  i = 1;
        int  g0178 = (nb+1);
        { OID gc_local;
          while ((i <= g0178))
          { // HOHO, GC_LOOP not needed !
            claire_store_BoolMatrix2D(ord,i,i,CTRUE);
            ++i;
            } 
          } 
        } 
      (c->componentOrder = ord);
      } 
    } 
  GC_UNBIND;} 


// ********************************************************************
// *   Part 8: standard alldiff constraint with generalized AC        *
// ********************************************************************
// integer valued variables are used only for the left vertex set
// no explicit variables are used for the right vertex set
//   the right vertex set is the interval (minValue .. maxValue)
/* The c++ function for: self_print(c:CompleteAllDiff) [] */
OID  claire_self_print_CompleteAllDiff_ice(CompleteAllDiff *c)
{ GC_BIND;
  princ_string("CompleteAllDiff(");
  { OID  g0182UU;
    { list * V_CL0183;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0181 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0181))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(c->vars))[i]);
              ++i;
              } 
            } 
          } 
        V_CL0183 = GC_OBJECT(list,i_bag);
        } 
      
      g0182UU=_oid_(V_CL0183);} 
    print_any(g0182UU);
    } 
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


// API entry point: creating the constraint (before posting it)
/* The c++ function for: choco/completeAllDiff(l1:list[choco/IntVar]) [] */
CompleteAllDiff * choco_completeAllDiff_list(list *l1)
{ GC_BIND;
  { CompleteAllDiff *Result ;
    { int  n = l1->length;
      CompleteAllDiff * c;
      { { CompleteAllDiff * _CL_obj = ((CompleteAllDiff *) GC_OBJECT(CompleteAllDiff,new_object_class(ice._CompleteAllDiff)));
          c = _CL_obj;
          } 
        GC_OBJECT(CompleteAllDiff,c);} 
      { ITERATE(v);
        for (START(l1); NEXT(v);)
        { (c->minValue = claire_min_integer2(c->minValue,choco.getInf->fcall(((int) OBJECT(ClaireObject,v)))));
          (c->maxValue = claire_max_integer2(c->maxValue,choco.getSup->fcall(((int) OBJECT(ClaireObject,v)))));
          } 
        } 
      { LargeIntConstraint * g0184; 
        list * g0185;
        g0184 = c;
        { bag *v_list; OID v_val;
          OID c,CLcount;
          v_list = l1;
           g0185 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { c = (*(v_list))[CLcount];
            v_val = c;
            
            (*((list *) g0185))[CLcount] = v_val;} 
          } 
        (g0184->vars = g0185);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      (c->nbLeftVertices = n);
      (c->nbRightVertices = ((c->maxValue-c->minValue)+1));
      choco_closeAssignmentConstraint_AbstractBipartiteGraph(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// The next two functions implement the main two events:
//   1. when an edge is definitely chosen in the bipartite assignment graph.
/* The c++ function for: setEdgeAndPublish(c:CompleteAllDiff,i:integer,j:integer) [] */
void  ice_setEdgeAndPublish_CompleteAllDiff(CompleteAllDiff *c,int i,int j)
{ ;{ int  g0186 = ((OID *) c->refMatch)[i];
    int  g0187 = ((OID *) c->refInverseMatch)[j];
    if (g0186 != j)
     { if (g0186 > 0)
       { STOREI(c->refInverseMatch[g0186],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        } 
      if (g0187 > 0)
       { STOREI(c->refMatch[g0187],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        } 
      STOREI(c->refMatch[i],j);
      STOREI(c->refInverseMatch[j],i);
      STOREI(c->matchingSize,(c->matchingSize+1));
      } 
    } 
  ;{ int  i2 = 1;
    int  g0188 = c->nbLeftVertices;
    { OID gc_local;
      while ((i2 <= g0188))
      { // HOHO, GC_LOOP not needed !
        if (i2 != i)
         (*choco.removeVal)((*(c->vars))[i2],
          ((j+c->minValue)-1),
          (*(c->indices))[i2]);
        ++i2;
        } 
      } 
    } 
  } 


//   2. when an edge is definitely removed from the bipartite assignment graph.
/* The c++ function for: choco/deleteEdgeAndPublish(c:CompleteAllDiff,i:integer,j:integer) [] */
void  choco_deleteEdgeAndPublish_CompleteAllDiff(CompleteAllDiff *c,int i,int j)
{ ;if (j == ((OID *) c->refMatch)[i])
   { STOREI(c->refMatch[i],0);
    STOREI(c->refInverseMatch[j],0);
    STOREI(c->matchingSize,(c->matchingSize-1));
    } 
  (*choco.removeVal)((*(c->vars))[i],
    ((j+c->minValue)-1),
    (*(c->indices))[i]);
  } 


// propagation functions: reacting to events
/* The c++ function for: choco/awakeOnRem(c:CompleteAllDiff,idx:integer,val:integer) [] */
void  choco_awakeOnRem_CompleteAllDiff(CompleteAllDiff *c,int idx,int val)
{ ;if (((val-c->minValue)+1) == ((OID *) c->refMatch)[idx])
   { STOREI(c->refMatch[idx],0);
    STOREI(c->refInverseMatch[((val-c->minValue)+1)],0);
    STOREI(c->matchingSize,(c->matchingSize-1));
    } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
/* The c++ function for: choco/awakeOnVar(c:CompleteAllDiff,idx:integer) [] */
void  choco_awakeOnVar_CompleteAllDiff(CompleteAllDiff *c,int idx)
{ GC_BIND;
  ;{ AbstractVar * v = GC_OBJECT(AbstractVar,OBJECT(AbstractVar,(*choco.getVar)(_oid_(c),
      idx)));
    int  val = c->minValue;
    int  g0189 = c->maxValue;
    { OID gc_local;
      while ((val <= g0189))
      { // HOHO, GC_LOOP not needed !
        if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
          val)) == CTRUE)
         { if (((val-c->minValue)+1) == ((OID *) c->refMatch)[idx])
           { STOREI(c->refMatch[idx],0);
            STOREI(c->refInverseMatch[((val-c->minValue)+1)],0);
            STOREI(c->matchingSize,(c->matchingSize-1));
            } 
          } 
        ++val;
        } 
      } 
    } 
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnInf(c:CompleteAllDiff,idx:integer) [] */
void  choco_awakeOnInf_CompleteAllDiff(CompleteAllDiff *c,int idx)
{ { int  j = c->minValue;
    int  g0190 = (OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue-1);
    { OID gc_local;
      while ((j <= g0190))
      { // HOHO, GC_LOOP not needed !
        if (((j-c->minValue)+1) == ((OID *) c->refMatch)[idx])
         { STOREI(c->refMatch[idx],0);
          STOREI(c->refInverseMatch[((j-c->minValue)+1)],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          } 
        ++j;
        } 
      } 
    } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


/* The c++ function for: choco/awakeOnSup(c:CompleteAllDiff,idx:integer) [] */
void  choco_awakeOnSup_CompleteAllDiff(CompleteAllDiff *c,int idx)
{ { int  j = (OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue+1);
    int  g0191 = c->maxValue;
    { OID gc_local;
      while ((j <= g0191))
      { // HOHO, GC_LOOP not needed !
        if (((j-c->minValue)+1) == ((OID *) c->refMatch)[idx])
         { STOREI(c->refMatch[idx],0);
          STOREI(c->refInverseMatch[((j-c->minValue)+1)],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          } 
        ++j;
        } 
      } 
    } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


/* The c++ function for: choco/awakeOnInst(c:CompleteAllDiff,idx:integer) [] */
void  choco_awakeOnInst_CompleteAllDiff(CompleteAllDiff *c,int idx)
{ ice_setEdgeAndPublish_CompleteAllDiff(c,idx,((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1));
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// no specific initial propagation (awake does the same job as propagate)
/* The c++ function for: choco/awake(c:CompleteAllDiff) [] */
void  choco_awake_CompleteAllDiff_ice(CompleteAllDiff *c)
{ ;choco_propagate_AbstractBipartiteGraph(c);
  } 


// ********************************************************************
// *   Part 9: dual models for permutation constraints                *
// ********************************************************************
// the constraint uses two lists of integer valued variables and models
// a permutation (and the inverse permutation) over (1 .. n)
/* The c++ function for: self_print(c:Permutation) [] */
OID  claire_self_print_Permutation_ice(Permutation *c)
{ GC_BIND;
  princ_string("Permutation(");
  { OID  g0194UU;
    { list * V_CL0195;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0192 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0192))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(c->vars))[i]);
              ++i;
              } 
            } 
          } 
        V_CL0195 = GC_OBJECT(list,i_bag);
        } 
      
      g0194UU=_oid_(V_CL0195);} 
    print_any(g0194UU);
    } 
  princ_string(",");
  { OID  g0196UU;
    { list * V_CL0197;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0193 = c->nbRightVertices;
          { OID gc_local;
            while ((i <= g0193))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(c->vars))[(i+c->nbLeftVertices)]);
              ++i;
              } 
            } 
          } 
        V_CL0197 = GC_OBJECT(list,i_bag);
        } 
      
      g0196UU=_oid_(V_CL0197);} 
    print_any(g0196UU);
    } 
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


// main two events: when an edge is removed or selected in the assignment graph.
//  1. the internal state of the constraint (ref. match.) is update,
//  2. Propagate the integrity constraint of the symmetrical model
//         (v1[i] == j <=> V2[j] == i)
//  3. Propagate the binary difference constraints
//         (V1[i1] <> V1[i2], V2[j1] <> V2[j2])
//      if fromLeft = true,  inst(V1[i],j) is the caller, thus inst(V2[j],i) is called
//      if fromLeft = false, inst(V2[j],i) is the caller, thus inst(V1[i],j) is called
//  4. If the ref. matching is no longer maximal (of size n), it will be completed afterwards
//     (const event loop) and the strong connection component analysis will be performed
// An edge is definitely chosen in the assignement.
/* The c++ function for: setEdgeAndPublish(c:Permutation,i:integer,j:integer,fromLeft:boolean) [] */
void  ice_setEdgeAndPublish_Permutation(Permutation *c,int i,int j,ClaireBoolean *fromLeft)
{ { int  n = c->nbLeftVertices;
    { int  g0198 = ((OID *) c->refMatch)[i];
      int  g0199 = ((OID *) c->refInverseMatch)[j];
      if (g0198 != j)
       { if (g0198 > 0)
         { STOREI(c->refInverseMatch[g0198],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          } 
        if (g0199 > 0)
         { STOREI(c->refMatch[g0199],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          } 
        STOREI(c->refMatch[i],j);
        STOREI(c->refInverseMatch[j],i);
        STOREI(c->matchingSize,(c->matchingSize+1));
        } 
      } 
    if (fromLeft == CTRUE)
     choco_instantiate_IntVar2(OBJECT(IntVar,(*(c->vars))[(j+n)]),i,(*(c->indices))[(j+n)]);
    else choco_instantiate_IntVar2(OBJECT(IntVar,(*(c->vars))[i]),j,(*(c->indices))[i]);
      { int  j2 = 1;
      int  g0200 = n;
      { OID gc_local;
        while ((j2 <= g0200))
        { // HOHO, GC_LOOP not needed !
          if (j2 != j)
           (*choco.removeVal)((*(c->vars))[(j2+n)],
            i,
            (*(c->indices))[(j2+n)]);
          ++j2;
          } 
        } 
      } 
    { int  i2 = 1;
      int  g0201 = n;
      { OID gc_local;
        while ((i2 <= g0201))
        { // HOHO, GC_LOOP not needed !
          if (i2 != i)
           (*choco.removeVal)((*(c->vars))[i2],
            j,
            (*(c->indices))[i2]);
          ++i2;
          } 
        } 
      } 
    } 
  } 


// the last parameter, fromLeft, indicates whether the event comes from the leftToRight model
// (c.leftVars, also denoted v1) or the roghtToLeft model (c.rightVars, also denoted v2)
/* The c++ function for: choco/deleteEdgeAndPublish(c:Permutation,i:integer,j:integer,fromLeft:boolean) [] */
void  choco_deleteEdgeAndPublish_Permutation1(Permutation *c,int i,int j,ClaireBoolean *fromLeft)
{ { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    if (j == ((OID *) c->refMatch)[i])
     { STOREI(c->refMatch[i],0);
      STOREI(c->refInverseMatch[j],0);
      STOREI(c->matchingSize,(c->matchingSize-1));
      } 
    if (fromLeft == CTRUE)
     (*choco.removeVal)((*(c->vars))[(j+n1)],
      i,
      (*(c->indices))[(j+n1)]);
    else (*choco.removeVal)((*(c->vars))[i],
        j,
        (*(c->indices))[i]);
      } 
  } 


// the event generated by the flow algorithm
/* The c++ function for: choco/deleteEdgeAndPublish(c:Permutation,i:integer,j:integer) [] */
void  choco_deleteEdgeAndPublish_Permutation2(Permutation *c,int i,int j)
{ { int  n1 = c->nbLeftVertices;
    int  n2 = c->nbRightVertices;
    if (j == ((OID *) c->refMatch)[i])
     { STOREI(c->refMatch[i],0);
      STOREI(c->refInverseMatch[j],0);
      STOREI(c->matchingSize,(c->matchingSize-1));
      } 
    (*choco.removeVal)((*(c->vars))[i],
      j,
      (*(c->indices))[i]);
    (*choco.removeVal)((*(c->vars))[(n1+j)],
      i,
      (*(c->indices))[(n1+j)]);
    } 
  } 


// propagation functions: reacting to events
/* The c++ function for: choco/awakeOnRem(c:Permutation,idx:integer,val:integer) [] */
void  choco_awakeOnRem_Permutation(Permutation *c,int idx,int val)
{ { int  n = c->nbLeftVertices;
    if (idx <= c->nbLeftVertices)
     choco_deleteEdgeAndPublish_Permutation1(c,idx,val,CTRUE);
    else choco_deleteEdgeAndPublish_Permutation1(c,val,(idx-n),CFALSE);
      } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
/* The c++ function for: choco/awakeOnVar(c:Permutation,idx:integer) [] */
void  choco_awakeOnVar_Permutation(Permutation *c,int idx)
{ GC_BIND;
  { int  n = c->nbLeftVertices;
    AbstractVar * v = GC_OBJECT(AbstractVar,OBJECT(AbstractVar,(*choco.getVar)(_oid_(c),
      idx)));
    if (idx <= n)
     { int  j = 1;
      int  g0202 = n;
      { OID gc_local;
        while ((j <= g0202))
        { // HOHO, GC_LOOP not needed !
          if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
            j)) == CTRUE)
           choco_deleteEdgeAndPublish_Permutation1(c,idx,j,CTRUE);
          ++j;
          } 
        } 
      } 
    else { int  j = 1;
        int  g0203 = n;
        { OID gc_local;
          while ((j <= g0203))
          { // HOHO, GC_LOOP not needed !
            if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
              j)) == CTRUE)
             choco_deleteEdgeAndPublish_Permutation1(c,j,(idx-n),CFALSE);
            ++j;
            } 
          } 
        } 
      choco_constAwake_AbstractConstraint(c,CFALSE);
    } 
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnInf(c:Permutation,idx:integer) [] */
void  choco_awakeOnInf_Permutation(Permutation *c,int idx)
{ { int  n = c->nbLeftVertices;
    if (idx <= n)
     { int  j = 1;
      int  g0204 = (OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue-1);
      { OID gc_local;
        while ((j <= g0204))
        { // HOHO, GC_LOOP not needed !
          choco_deleteEdgeAndPublish_Permutation1(c,idx,j,CTRUE);
          ++j;
          } 
        } 
      } 
    else { int  j = 1;
        int  g0205 = (OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue-1);
        { OID gc_local;
          while ((j <= g0205))
          { // HOHO, GC_LOOP not needed !
            choco_deleteEdgeAndPublish_Permutation1(c,j,(idx-n),CFALSE);
            ++j;
            } 
          } 
        } 
      } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


/* The c++ function for: choco/awakeOnSup(c:Permutation,idx:integer) [] */
void  choco_awakeOnSup_Permutation(Permutation *c,int idx)
{ { int  n = c->nbLeftVertices;
    if (idx <= n)
     { int  j = (OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue+1);
      int  g0206 = n;
      { OID gc_local;
        while ((j <= g0206))
        { // HOHO, GC_LOOP not needed !
          choco_deleteEdgeAndPublish_Permutation1(c,idx,j,CTRUE);
          ++j;
          } 
        } 
      } 
    else { int  j = (OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue+1);
        int  g0207 = n;
        { OID gc_local;
          while ((j <= g0207))
          { // HOHO, GC_LOOP not needed !
            choco_deleteEdgeAndPublish_Permutation1(c,j,(idx-n),CFALSE);
            ++j;
            } 
          } 
        } 
      } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// Note: this works even if the assigned value is not an edge of the reference matching
/* The c++ function for: choco/awakeOnInst(c:Permutation,idx:integer) [] */
void  choco_awakeOnInst_Permutation(Permutation *c,int idx)
{ { int  n = c->nbLeftVertices;
    if (idx <= n)
     ice_setEdgeAndPublish_Permutation(c,idx,OBJECT(IntVar,(*(c->vars))[idx])->value,CTRUE);
    else ice_setEdgeAndPublish_Permutation(c,OBJECT(IntVar,(*(c->vars))[idx])->value,(idx-n),CFALSE);
      } 
  choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// performing the initial propagation
/* The c++ function for: choco/awake(c:Permutation) [] */
void  choco_awake_Permutation_ice(Permutation *c)
{ { int  n = c->nbLeftVertices;
    { int  i = 1;
      int  g0208 = n;
      { OID gc_local;
        while ((i <= g0208))
        { // HOHO, GC_LOOP not needed !
          (*choco.updateInf)((*(c->vars))[i],
            1,
            (*(c->indices))[i]);
          (*choco.updateSup)((*(c->vars))[i],
            n,
            (*(c->indices))[i]);
          ++i;
          } 
        } 
      } 
    { int  j = 1;
      int  g0209 = n;
      { OID gc_local;
        while ((j <= g0209))
        { // HOHO, GC_LOOP not needed !
          (*choco.updateInf)((*(c->vars))[(j+n)],
            1,
            (*(c->indices))[(j+n)]);
          (*choco.updateSup)((*(c->vars))[(j+n)],
            n,
            (*(c->indices))[(j+n)]);
          ++j;
          } 
        } 
      } 
    { int  i = 1;
      int  g0210 = n;
      { OID gc_local;
        while ((i <= g0210))
        { // HOHO, GC_LOOP not needed !
          { int  j = 1;
            int  g0211 = n;
            { OID gc_local;
              while ((j <= g0211))
              { // HOHO, GC_LOOP not needed !
                if (not_any((*choco.canBeInstantiatedTo)((*(c->vars))[i],
                  j)) == CTRUE)
                 (*choco.removeVal)((*(c->vars))[(j+n)],
                  i,
                  (*(c->indices))[(j+n)]);
                if (not_any((*choco.canBeInstantiatedTo)((*(c->vars))[(j+n)],
                  i)) == CTRUE)
                 (*choco.removeVal)((*(c->vars))[i],
                  j,
                  (*(c->indices))[i]);
                ++j;
                } 
              } 
            } 
          ++i;
          } 
        } 
      } 
    ice_removeUselessEdges_AbstractBipartiteMatching(c);
    } 
  } 


// API entry point: creating the constraint (before posting it)
/* The c++ function for: choco/permutation(l1:list[choco/IntVar],l2:list[choco/IntVar]) [] */
Permutation * choco_permutation_list(list *l1,list *l2)
{ GC_BIND;
  { Permutation *Result ;
    { Permutation * c;
      { { Permutation * _CL_obj = ((Permutation *) GC_OBJECT(Permutation,new_object_class(ice._Permutation)));
          c = _CL_obj;
          } 
        GC_OBJECT(Permutation,c);} 
      int  n = l1->length;
      { LargeIntConstraint * g0212; 
        list * g0213;
        g0212 = c;
        { list * g0214UU;
          { { bag *v_list; OID v_val;
              OID v,CLcount;
              v_list = l1;
               g0214UU = v_list->clone(choco._IntVar);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v = (*(v_list))[CLcount];
                v_val = v;
                
                (*((list *) g0214UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0214UU);} 
          list * g0215UU;
          { { bag *v_list; OID v_val;
              OID v,CLcount;
              v_list = l2;
               g0215UU = v_list->clone(choco._IntVar);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v = (*(v_list))[CLcount];
                v_val = v;
                
                (*((list *) g0215UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0215UU);} 
          g0213 = append_list(g0214UU,g0215UU);
          } 
        (g0212->vars = g0213);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      (c->nbLeftVertices = n);
      (c->nbRightVertices = n);
      (c->minValue = 1);
      (c->maxValue = n);
      choco_closeAssignmentConstraint_AbstractBipartiteGraph(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 10: global cardinality constraint (gcc, Regin 96)          *
// ********************************************************************
// a very simple version of the cardinality constraint where the values
//   the set of values whose occurrences are counted is the interval (minValue .. maxValue)
/* The c++ function for: self_print(c:GlobalCardinality) [] */
OID  claire_self_print_GlobalCardinality_ice(GlobalCardinality *c)
{ GC_BIND;
  princ_string("GCC(");
  { OID  g0217UU;
    { list * V_CL0218;{ list * i_bag = list::empty(Kernel.emptySet);
        { int  i = 1;
          int  g0216 = c->nbLeftVertices;
          { OID gc_local;
            while ((i <= g0216))
            { // HOHO, GC_LOOP not needed !
              i_bag->addFast((*(c->vars))[i]);
              ++i;
              } 
            } 
          } 
        V_CL0218 = GC_OBJECT(list,i_bag);
        } 
      
      g0217UU=_oid_(V_CL0218);} 
    print_any(g0217UU);
    } 
  { OID Result = 0;
    princ_string(")");
    GC_UNBIND; return (Result);} 
  } 


// The next two functions implement the main event:
// when an edge is definitely removed from the bipartite assignment graph.
/* The c++ function for: choco/deleteEdgeAndPublish(c:GlobalCardinality,i:integer,j:integer) [] */
void  choco_deleteEdgeAndPublish_GlobalCardinality(GlobalCardinality *c,int i,int j)
{ ;if (j == ((OID *) c->refMatch)[i])
   { STOREI(c->refMatch[i],0);
    STOREI(c->matchingSize,(c->matchingSize-1));
    STOREI(c->flow[j],((((OID *) c->flow)[j])-1));
    } 
  (*choco.removeVal)((*(c->vars))[i],
    ((j+c->minValue)-1),
    (*(c->indices))[i]);
  } 


/* The c++ function for: setEdgeAndPublish(c:GlobalCardinality,i:integer,j:integer) [] */
void  ice_setEdgeAndPublish_GlobalCardinality(GlobalCardinality *c,int i,int j)
{ ;{ int  g0219 = ((OID *) c->refMatch)[i];
    if (g0219 != j)
     { if (g0219 > 0)
       { STOREI(c->refMatch[i],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        STOREI(c->flow[g0219],((((OID *) c->flow)[g0219])-1));
        } 
      if ((((OID *) c->flow)[j] < ((OID *) c->maxFlow)[j]) && 
          ((g0219 == 0) || 
              (((OID *) c->minFlow)[g0219] <= ((OID *) c->flow)[g0219])))
       { STOREI(c->refMatch[i],j);
        STOREI(c->matchingSize,(c->matchingSize+1));
        STOREI(c->flow[j],((((OID *) c->flow)[j])+1));
        } 
      } 
    } 
  choco_instantiate_IntVar2(OBJECT(IntVar,(*(c->vars))[i]),((j+c->minValue)-1),(*(c->indices))[i]);
  } 


// propagation functions: reacting to events
/* The c++ function for: choco/awakeOnRem(c:GlobalCardinality,idx:integer,val:integer) [] */
void  choco_awakeOnRem_GlobalCardinality(GlobalCardinality *c,int idx,int val)
{ ;choco_deleteEdgeAndPublish_GlobalCardinality(c,idx,((val-c->minValue)+1));
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// new in v0.6: this function needs be defined, otherwise the default awakeOnVar, calling propagate is called
// and thus the reference matching is not updated before redoing the strongly connected components analysis.    
/* The c++ function for: choco/awakeOnVar(c:GlobalCardinality,idx:integer) [] */
void  choco_awakeOnVar_GlobalCardinality(GlobalCardinality *c,int idx)
{ GC_BIND;
  ;{ AbstractVar * v = GC_OBJECT(AbstractVar,OBJECT(AbstractVar,(*choco.getVar)(_oid_(c),
      idx)));
    int  val = c->minValue;
    int  g0220 = c->maxValue;
    { OID gc_local;
      while ((val <= g0220))
      { // HOHO, GC_LOOP not needed !
        if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
          val)) == CTRUE)
         { if (((val-c->minValue)+1) == ((OID *) c->refMatch)[idx])
           { STOREI(c->refMatch[idx],0);
            STOREI(c->matchingSize,(c->matchingSize-1));
            STOREI(c->flow[((val-c->minValue)+1)],((((OID *) c->flow)[((val-c->minValue)+1)])-1));
            } 
          } 
        ++val;
        } 
      } 
    } 
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  GC_UNBIND;} 


/* The c++ function for: choco/awakeOnInf(c:GlobalCardinality,idx:integer) [] */
void  choco_awakeOnInf_GlobalCardinality(GlobalCardinality *c,int idx)
{ ;{ int  j = 1;
    int  g0221 = (OBJECT(IntVar,(*(c->vars))[idx])->inf->latestValue-1);
    { OID gc_local;
      while ((j <= g0221))
      { // HOHO, GC_LOOP not needed !
        if (((j-c->minValue)+1) == ((OID *) c->refMatch)[idx])
         { STOREI(c->refMatch[idx],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          STOREI(c->flow[((j-c->minValue)+1)],((((OID *) c->flow)[((j-c->minValue)+1)])-1));
          } 
        ++j;
        } 
      } 
    } 
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


/* The c++ function for: choco/awakeOnSup(c:GlobalCardinality,idx:integer) [] */
void  choco_awakeOnSup_GlobalCardinality(GlobalCardinality *c,int idx)
{ ;{ int  j = (OBJECT(IntVar,(*(c->vars))[idx])->sup->latestValue+1);
    int  g0222 = c->maxValue;
    { OID gc_local;
      while ((j <= g0222))
      { // HOHO, GC_LOOP not needed !
        if (((j-c->minValue)+1) == ((OID *) c->refMatch)[idx])
         { STOREI(c->refMatch[idx],0);
          STOREI(c->matchingSize,(c->matchingSize-1));
          STOREI(c->flow[((j-c->minValue)+1)],((((OID *) c->flow)[((j-c->minValue)+1)])-1));
          } 
        ++j;
        } 
      } 
    } 
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// Note: this works even if the assigned value is not an edge of the reference matching
/* The c++ function for: choco/awakeOnInst(c:GlobalCardinality,idx:integer) [] */
void  choco_awakeOnInst_GlobalCardinality(GlobalCardinality *c,int idx)
{ ;{ int  g0223 = ((OID *) c->refMatch)[idx];
    if (g0223 != ((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1))
     { if (g0223 > 0)
       { STOREI(c->refMatch[idx],0);
        STOREI(c->matchingSize,(c->matchingSize-1));
        STOREI(c->flow[g0223],((((OID *) c->flow)[g0223])-1));
        } 
      if ((((OID *) c->flow)[((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1)] < ((OID *) c->maxFlow)[((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1)]) && 
          ((g0223 == 0) || 
              (((OID *) c->minFlow)[g0223] <= ((OID *) c->flow)[g0223])))
       { STOREI(c->refMatch[idx],((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1));
        STOREI(c->matchingSize,(c->matchingSize+1));
        STOREI(c->flow[((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1)],((((OID *) c->flow)[((OBJECT(IntVar,(*(c->vars))[idx])->value-c->minValue)+1)])+1));
        } 
      } 
    } 
  ;choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// performing the initial propagation
/* The c++ function for: choco/awake(c:GlobalCardinality) [] */
void  choco_awake_GlobalCardinality_ice(GlobalCardinality *c)
{ { int  i = 1;
    int  g0224 = c->nbLeftVertices;
    { OID gc_local;
      while ((i <= g0224))
      { // HOHO, GC_LOOP not needed !
        (*choco.updateInf)((*(c->vars))[i],
          c->minValue,
          (*(c->indices))[i]);
        (*choco.updateSup)((*(c->vars))[i],
          c->maxValue,
          (*(c->indices))[i]);
        ++i;
        } 
      } 
    } 
  choco_propagate_AbstractBipartiteGraph(c);
  } 


// API entry point: creating the constraint (before posting it)
/* The c++ function for: choco/gcc(l1:list[choco/IntVar],l2:list[Interval]) [] */
GlobalCardinality * choco_gcc_list1(list *l1,list *l2)
{ return (choco_gcc_list2(l1,1,l2->length,l2));} 


/* The c++ function for: choco/gcc(l1:list[choco/IntVar],val1:integer,val2:integer,l2:list[Interval]) [] */
GlobalCardinality * choco_gcc_list2(list *l1,int val1,int val2,list *l2)
{ GC_BIND;
  { GlobalCardinality *Result ;
    { GlobalCardinality * c;
      { { GlobalCardinality * _CL_obj = ((GlobalCardinality *) GC_OBJECT(GlobalCardinality,new_object_class(ice._GlobalCardinality)));
          c = _CL_obj;
          } 
        GC_OBJECT(GlobalCardinality,c);} 
      { LargeIntConstraint * g0226; 
        list * g0227;
        g0226 = c;
        { bag *v_list; OID v_val;
          OID v,CLcount;
          v_list = l1;
           g0227 = v_list->clone(choco._IntVar);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { v = (*(v_list))[CLcount];
            v_val = v;
            
            (*((list *) g0227))[CLcount] = v_val;} 
          } 
        (g0226->vars = g0227);} 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      (c->minValue = val1);
      (c->maxValue = val2);
      (c->nbRightVertices = ((val2-val1)+1));
      (c->nbLeftVertices = l1->length);
      (c->minFlow = make_array_integer(l2->length,Kernel._integer,0));
      (c->maxFlow = make_array_integer(l2->length,Kernel._integer,0));
      (c->flow = make_array_integer(l2->length,Kernel._integer,0));
      { int  i = 1;
        int  g0225 = l2->length;
        { OID gc_local;
          while ((i <= g0225))
          { // HOHO, GC_LOOP not needed !
            (((OID *) c->minFlow)[i] = OBJECT(Interval,(*(l2))[i])->arg1);
            (((OID *) c->maxFlow)[i] = OBJECT(Interval,(*(l2))[i])->arg2);
            ++i;
            } 
          } 
        } 
      choco_closeAssignmentConstraint_AbstractBipartiteGraph(c);
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: checkComponentOrder(c:AbstractBipartiteMatching) [] */
void  ice_checkComponentOrder_AbstractBipartiteMatching(AbstractBipartiteMatching *c)
{ GC_RESERVE(17);  // v3.0.55 optim !
  { int  i = 1;
    int  g0228 = c->currentComponent;
    { OID gc_local;
      while ((i <= g0228))
      { GC_LOOP;
        { set * l = set::alloc(Kernel._integer,1,i);
          set * l2;
          { { set * j_out = set::empty(Kernel._integer);
              { int  j = 1;
                int  g0229 = c->currentComponent;
                { OID gc_local;
                  while ((j <= g0229))
                  { GC_LOOP;
                    if (claire_read_BoolMatrix2D(GC_OBJECT(BoolMatrix2D,c->componentOrder),i,j) == CTRUE)
                     j_out->addFast(j);
                    ++j;
                    GC_UNLOOP;} 
                  } 
                } 
              l2 = GC_OBJECT(set,j_out);
              } 
            GC_OBJECT(set,l2);} 
          int  n1 = c->nbLeftVertices;
          int  n2 = c->nbRightVertices;
          ClaireBoolean * stable = CFALSE;
          ClaireBoolean * ok = CTRUE;
          { OID gc_local;
            while ((stable != CTRUE))
            { GC_LOOP;
              stable= CTRUE;
              { int  vtx = 1;
                int  g0230 = n1;
                { OID gc_local;
                  while ((vtx <= g0230))
                  { GC_LOOP;
                    { IntVar * g0233 = OBJECT(IntVar,(*(c->vars))[vtx]);
                      AbstractIntDomain * g0234 = GC_OBJECT(AbstractIntDomain,g0233->bucket);
                      if (g0234 == (NULL))
                       { int  g0232 = g0233->inf->latestValue;
                        { OID gc_local;
                          while ((g0232 <= g0233->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            { int  vtv = ((g0232-c->minValue)+1);
                              if (vtv != ((OID *) c->refMatch)[vtx])
                               { if ((contain_ask_set(l,((OID *) c->component)[vtx]) == CTRUE) && 
                                    (contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) != CTRUE))
                                 { stable= CFALSE;
                                  l= l->addFast(((OID *) c->component)[(vtv+n1)]);
                                  } 
                                } 
                              } 
                            g0232= ((g0233->inf->latestValue <= (g0232+1)) ?
                              (g0232+1) :
                              g0233->inf->latestValue );
                            } 
                          } 
                        } 
                      else if (INHERIT(g0234->isa,choco._LinkedListIntDomain))
                       { int  g0232 = g0233->inf->latestValue;
                        { OID gc_local;
                          while ((g0232 <= g0233->sup->latestValue))
                          { // HOHO, GC_LOOP not needed !
                            { int  vtv = ((g0232-c->minValue)+1);
                              if (vtv != ((OID *) c->refMatch)[vtx])
                               { if ((contain_ask_set(l,((OID *) c->component)[vtx]) == CTRUE) && 
                                    (contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) != CTRUE))
                                 { stable= CFALSE;
                                  l= l->addFast(((OID *) c->component)[(vtv+n1)]);
                                  } 
                                } 
                              } 
                            g0232= choco.getNextValue->fcall(((int) g0234),((int) g0232));
                            } 
                          } 
                        } 
                      else { OID gc_local;
                          ITERATE(g0232);
                          bag *g0232_support;
                          g0232_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0234)))));
                          for (START(g0232_support); NEXT(g0232);)
                          { GC_LOOP;
                            { int  vtv = ((g0232-c->minValue)+1);
                              if (vtv != ((OID *) c->refMatch)[vtx])
                               { if ((contain_ask_set(l,((OID *) c->component)[vtx]) == CTRUE) && 
                                    (contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) != CTRUE))
                                 { stable= CFALSE;
                                  l= l->addFast(((OID *) c->component)[(vtv+n1)]);
                                  } 
                                } 
                              } 
                            GC_UNLOOP;} 
                          } 
                        } 
                    ++vtx;
                    GC_UNLOOP;} 
                  } 
                } 
              { int  vtv = 1;
                int  g0235 = n2;
                { OID gc_local;
                  while ((vtv <= g0235))
                  { // HOHO, GC_LOOP not needed !
                    { int  vtx = 1;
                      int  g0237 = c->nbLeftVertices;
                      { OID gc_local;
                        while ((vtx <= g0237))
                        { // HOHO, GC_LOOP not needed !
                          if ((OBJECT(ClaireBoolean,(*choco.canBeInstantiatedTo)((*(c->vars))[vtx],
                            ((vtv+c->minValue)-1)))) == CTRUE)
                           { if (((OID *) c->refMatch)[vtx] == vtv)
                             { if ((contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) == CTRUE) && 
                                  (contain_ask_set(l,((OID *) c->component)[vtx]) != CTRUE))
                               { stable= CFALSE;
                                l= l->addFast(((OID *) c->component)[vtx]);
                                } 
                              } 
                            } 
                          ++vtx;
                          } 
                        } 
                      } 
                    if (((OID *) c->refInverseMatch)[vtv] == 0)
                     { if ((contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) == CTRUE) && 
                          (contain_ask_set(l,((OID *) c->component)[c->source]) != CTRUE))
                       { stable= CFALSE;
                        l= l->addFast(((OID *) c->component)[c->source]);
                        } 
                      } 
                    if (((OID *) c->refInverseMatch)[vtv] != 0)
                     { if ((contain_ask_set(l,((OID *) c->component)[c->source]) == CTRUE) && 
                          (contain_ask_set(l,((OID *) c->component)[(vtv+n1)]) != CTRUE))
                       { stable= CFALSE;
                        l= l->addFast(((OID *) c->component)[(vtv+n1)]);
                        } 
                      } 
                    ;++vtv;
                    } 
                  } 
                } 
              GC_UNLOOP;} 
            } 
          if (equal(_oid_(l),_oid_(l2)) != CTRUE)
           { princ_string("Wrong successors of ");
            print_any(i);
            princ_string(": ");
            print_any(_oid_(l));
            princ_string(" instead of ");
            print_any(_oid_(l2));
            princ_string(" \n");
            (ice.CHK->value= _oid_(c));
            ok= CFALSE;
            } 
          if (ok != CTRUE)
           close_exception(((general_error *) (*Core._general_error)(_string_("STOP"),
            _oid_(Kernel.nil))));
          } 
        ++i;
        GC_UNLOOP;} 
      } 
    } 
  GC_UNBIND;} 

