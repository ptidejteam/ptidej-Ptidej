/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\intconst1.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:26 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: const.cl                                                   *
// *    propagation of simple constraints (value tuples + linear)     *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 0: Binary relations                                       *
// *   Part 1: Binary constraints as pairs of feasible values         *
// *   Part 2: Constraints as arbitrary lists feasible tuples         *
// *   Part 3: Comparisons as unary constraints                       *
// *   Part 4: Comparisons as binary constraints                      *
// *   Part 5: General linear combinations as global constraints      *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 0: Binary relations                                       *
// ********************************************************************
// Abstract class for encoding relations between values form two domains
//  (maybe, there could be subclasses for dense and sparse matrices)
/* The c++ function for: getTruthValue(tt:BinRelation,x:integer,y:integer) [] */
ClaireBoolean * choco_getTruthValue_BinRelation(BinRelation *tt,int x,int y)
{ close_exception(((general_error *) (*Core._general_error)(_string_("getTruthValue not defined for ~S"),
    _oid_(list::alloc(1,_oid_(tt->isa))))));
  return (CTRUE);} 


// class with a test method
// the predicate defining the constraint
// get
/* The c++ function for: getTruthValue(tt:TruthTest,x:integer,y:integer) [] */
ClaireBoolean * choco_getTruthValue_TruthTest(TruthTest *tt,int x,int y)
{ return (OBJECT(ClaireBoolean,funcall_method2(tt->test,x,y)));} 


// pretty printing 
/* The c++ function for: self_print(tt:TruthTest) [] */
void  claire_self_print_TruthTest_choco(TruthTest *tt)
{ princ_string("<");
  print_any(_oid_(tt->test->selector));
  princ_string(">");
  } 


// class with an explicit table of allowed pairs
// get/set
/* The c++ function for: getTruthValue(dtt:TruthTable2D,x:integer,y:integer) [] */
ClaireBoolean * choco_getTruthValue_TruthTable2D(TruthTable2D *dtt,int x,int y)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = claire_read_BoolMatrix2D(GC_OBJECT(BoolMatrix2D,dtt->matrix),x,y);
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: setTruePair(dtt:TruthTable2D,x:integer,y:integer) [] */
void  choco_setTruePair_TruthTable2D(TruthTable2D *dtt,int x,int y)
{ GC_BIND;
  claire_store_BoolMatrix2D(GC_OBJECT(BoolMatrix2D,dtt->matrix),x,y,CTRUE);
  GC_UNBIND;} 


// pretty printing 
/* The c++ function for: self_print(tt:TruthTable2D) [] */
void  claire_self_print_TruthTable2D_choco(TruthTable2D *tt)
{ princ_string("mat<");
  print_any(tt->size1);
  princ_string("x");
  print_any(tt->size2);
  princ_string(">");
  } 


// ********************************************************************
// *   Part 1: Binary constraints as pairs of feasible values         *
// ********************************************************************
// The abstract class for user-defined constraints and propagated by one of the arc consistency algorithms
// if true: the relation contains feasible pairs, otherwise infeasible ones
/* The c++ function for: self_print(c:CSPBinConstraint) [] */
void  claire_self_print_CSPBinConstraint_choco(CSPBinConstraint *c)
{ GC_BIND;
  print_any(_oid_(c->isa));
  princ_string(((c->feasiblePair == CTRUE) ?
    "+" :
    "-" ));
  princ_string("(");
  print_any(GC_OID(_oid_(c->v1)));
  princ_string(",");
  print_any(GC_OID(_oid_(c->v2)));
  princ_string(")[");
  print_any(GC_OID(_oid_(c->feasRelation)));
  princ_string("]");
  GC_UNBIND;} 


// checking a constraint
/* The c++ function for: testIfSatisfied(c:CSPBinConstraint) [] */
ClaireBoolean * choco_testIfSatisfied_CSPBinConstraint(CSPBinConstraint *c)
{ GC_BIND;
  { ClaireBoolean *Result ;
    Result = ((_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) c->v1->value),((int) c->v2->value))) == _oid_(c->feasiblePair)) ? CTRUE : CFALSE);
    GC_UNBIND; return (Result);} 
  } 


// taking the opposite while sharing the relation object
/* The c++ function for: opposite(c:CSPBinConstraint) [] */
CSPBinConstraint * choco_opposite_CSPBinConstraint(CSPBinConstraint *c)
{ GC_BIND;
  { CSPBinConstraint *Result ;
    { CSPBinConstraint * c2 = GC_OBJECT(CSPBinConstraint,((CSPBinConstraint *) copy_object(c)));
      (c2->feasiblePair = not_any(_oid_(c->feasiblePair)));
      Result = c2;
      } 
    GC_UNBIND; return (Result);} 
  } 


// forward checking propagation: the same code is used for reacting to an instantiation, no matter what AC algorithm is used
/* The c++ function for: awakeOnInst(c:CSPBinConstraint,idx:integer) [] */
void  choco_awakeOnInst_CSPBinConstraint(CSPBinConstraint *c,int idx)
{ GC_BIND;
  { IntVar * var1 = GC_OBJECT(IntVar,c->v1);
    IntVar * var2 = GC_OBJECT(IntVar,c->v2);
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    int  val = ((idx == 1) ?
      c->v1->value :
      c->v2->value );
    if (idx == 1)
     { IntVar * g0177 = var2;
      AbstractIntDomain * g0178 = GC_OBJECT(AbstractIntDomain,g0177->bucket);
      if (g0178 == (NULL))
       { int  y = g0177->inf->latestValue;
        { OID gc_local;
          while ((y <= g0177->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            y= ((g0177->inf->latestValue <= (y+1)) ?
              (y+1) :
              g0177->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g0178->isa,choco._LinkedListIntDomain))
       { int  y = g0177->inf->latestValue;
        { OID gc_local;
          while ((y <= g0177->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            y= choco.getNextValue->fcall(((int) g0178),((int) y));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(y);
          bag *y_support;
          y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0178)))));
          for (START(y_support); NEXT(y);)
          { GC_LOOP;
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            GC_UNLOOP;} 
          } 
        } 
    else { IntVar * g0179 = var1;
        AbstractIntDomain * g0180 = GC_OBJECT(AbstractIntDomain,g0179->bucket);
        if (g0180 == (NULL))
         { int  x = g0179->inf->latestValue;
          { OID gc_local;
            while ((x <= g0179->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              x= ((g0179->inf->latestValue <= (x+1)) ?
                (x+1) :
                g0179->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0180->isa,choco._LinkedListIntDomain))
         { int  x = g0179->inf->latestValue;
          { OID gc_local;
            while ((x <= g0179->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              x= choco.getNextValue->fcall(((int) g0180),((int) x));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(x);
            bag *x_support;
            x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0180)))));
            for (START(x_support); NEXT(x);)
            { GC_LOOP;
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              GC_UNLOOP;} 
            } 
          } 
      choco_setPassive_AbstractConstraint(c);
    } 
  GC_UNBIND;} 


// debug        
/* The c++ function for: see(c:CSPBinConstraint) [] */
void  choco_see_CSPBinConstraint(CSPBinConstraint *c)
{ GC_RESERVE(9);  // v3.0.55 optim !
  princ_string("(");
  print_any(GC_OID(_oid_(c->v1)));
  princ_string(",");
  print_any(GC_OID(_oid_(c->v2)));
  princ_string(") % {");
  { IntVar * g0181 = GC_OBJECT(IntVar,c->v1);
    AbstractIntDomain * g0182 = GC_OBJECT(AbstractIntDomain,g0181->bucket);
    if (g0182 == (NULL))
     { int  x = g0181->inf->latestValue;
      { OID gc_local;
        while ((x <= g0181->sup->latestValue))
        { GC_LOOP;
          { IntVar * g0183 = GC_OBJECT(IntVar,c->v2);
            AbstractIntDomain * g0184 = GC_OBJECT(AbstractIntDomain,g0183->bucket);
            if (g0184 == (NULL))
             { int  y = g0183->inf->latestValue;
              { OID gc_local;
                while ((y <= g0183->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= ((g0183->inf->latestValue <= (y+1)) ?
                    (y+1) :
                    g0183->inf->latestValue );
                  GC_UNLOOP;} 
                } 
              } 
            else if (INHERIT(g0184->isa,choco._LinkedListIntDomain))
             { int  y = g0183->inf->latestValue;
              { OID gc_local;
                while ((y <= g0183->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= choco.getNextValue->fcall(((int) g0184),((int) y));
                  GC_UNLOOP;} 
                } 
              } 
            else { OID gc_local;
                ITERATE(y);
                bag *y_support;
                y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0184)))));
                for (START(y_support); NEXT(y);)
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  GC_UNLOOP;} 
                } 
              } 
          x= ((g0181->inf->latestValue <= (x+1)) ?
            (x+1) :
            g0181->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0182->isa,choco._LinkedListIntDomain))
     { int  x = g0181->inf->latestValue;
      { OID gc_local;
        while ((x <= g0181->sup->latestValue))
        { GC_LOOP;
          { IntVar * g0185 = GC_OBJECT(IntVar,c->v2);
            AbstractIntDomain * g0186 = GC_OBJECT(AbstractIntDomain,g0185->bucket);
            if (g0186 == (NULL))
             { int  y = g0185->inf->latestValue;
              { OID gc_local;
                while ((y <= g0185->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= ((g0185->inf->latestValue <= (y+1)) ?
                    (y+1) :
                    g0185->inf->latestValue );
                  GC_UNLOOP;} 
                } 
              } 
            else if (INHERIT(g0186->isa,choco._LinkedListIntDomain))
             { int  y = g0185->inf->latestValue;
              { OID gc_local;
                while ((y <= g0185->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= choco.getNextValue->fcall(((int) g0186),((int) y));
                  GC_UNLOOP;} 
                } 
              } 
            else { OID gc_local;
                ITERATE(y);
                bag *y_support;
                y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0186)))));
                for (START(y_support); NEXT(y);)
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  GC_UNLOOP;} 
                } 
              } 
          x= choco.getNextValue->fcall(((int) g0182),((int) x));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0182)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          { IntVar * g0187 = GC_OBJECT(IntVar,c->v2);
            AbstractIntDomain * g0188 = GC_OBJECT(AbstractIntDomain,g0187->bucket);
            if (g0188 == (NULL))
             { int  y = g0187->inf->latestValue;
              { OID gc_local;
                while ((y <= g0187->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= ((g0187->inf->latestValue <= (y+1)) ?
                    (y+1) :
                    g0187->inf->latestValue );
                  GC_UNLOOP;} 
                } 
              } 
            else if (INHERIT(g0188->isa,choco._LinkedListIntDomain))
             { int  y = g0187->inf->latestValue;
              { OID gc_local;
                while ((y <= g0187->sup->latestValue))
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  y= choco.getNextValue->fcall(((int) g0188),((int) y));
                  GC_UNLOOP;} 
                } 
              } 
            else { OID gc_local;
                ITERATE(y);
                bag *y_support;
                y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0188)))));
                for (START(y_support); NEXT(y);)
                { GC_LOOP;
                  if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->feasRelation)))),((int) x),((int) y))) == _oid_(c->feasiblePair))
                   { princ_string("(");
                    print_any(x);
                    princ_string(",");
                    print_any(y);
                    princ_string("), ");
                    } 
                  GC_UNLOOP;} 
                } 
              } 
          GC_UNLOOP;} 
        } 
      } 
  princ_string("}");
  GC_UNBIND;} 


// ********************************************************************
// *   Part 1a: Binary constraints propagated by the AC3 algorithm    *
// ********************************************************************
// internal constructor
/* The c++ function for: makeAC3BinConstraint(u:IntVar,v:IntVar,feas:boolean,feasRel:BinRelation) [] */
AC3BinConstraint * choco_makeAC3BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel)
{ GC_BIND;
  { AC3BinConstraint *Result ;
    { AC3BinConstraint * _CL_obj = ((AC3BinConstraint *) GC_OBJECT(AC3BinConstraint,new_object_class(choco._AC3BinConstraint)));
      (_CL_obj->v1 = u);
      (_CL_obj->v2 = v);
      (_CL_obj->cste = 0);
      (_CL_obj->feasiblePair = feas);
      (_CL_obj->feasRelation = feasRel);
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// updates the support for all values in the domain of v2, and remove unsupported values for v2
/* The c++ function for: reviseV2(c:AC3BinConstraint) [] */
void  choco_reviseV2_AC3BinConstraint(AC3BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { IntVar * var1 = c->v1;
    IntVar * var2 = c->v2;
    BinRelation * feasRel = c->feasRelation;
    ClaireBoolean * feas = c->feasiblePair;
    IntVar * g0189 = var2;
    AbstractIntDomain * g0190 = g0189->bucket;
    if (g0190 == (NULL))
     { int  y = g0189->inf->latestValue;
      { OID gc_local;
        while ((y <= g0189->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0197I;
            { OID  g0198UU;
              { IntVar * g0191 = var1;
                AbstractIntDomain * g0192 = g0191->bucket;
                if (g0192 == (NULL))
                 { int  x = g0191->inf->latestValue;
                  { OID gc_local;
                    g0198UU= _oid_(CFALSE);
                    while ((x <= g0191->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0198UU = Kernel.ctrue;
                        break;} 
                      x= ((g0191->inf->latestValue <= (x+1)) ?
                        (x+1) :
                        g0191->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0192->isa,choco._LinkedListIntDomain))
                 { int  x = g0191->inf->latestValue;
                  { OID gc_local;
                    g0198UU= _oid_(CFALSE);
                    while ((x <= g0191->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0198UU = Kernel.ctrue;
                        break;} 
                      x= choco.getNextValue->fcall(((int) g0192),((int) x));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(x);
                    g0198UU= _oid_(CFALSE);
                    bag *x_support;
                    x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0192)))));
                    for (START(x_support); NEXT(x);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0198UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0197I = not_any(g0198UU);
              } 
            
            if (g0197I == CTRUE) (*choco.removeVal)(_oid_(var2),
                y,
                c->idx2);
              } 
          y= ((g0189->inf->latestValue <= (y+1)) ?
            (y+1) :
            g0189->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0190->isa,choco._LinkedListIntDomain))
     { int  y = g0189->inf->latestValue;
      { OID gc_local;
        while ((y <= g0189->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0199I;
            { OID  g0200UU;
              { IntVar * g0193 = var1;
                AbstractIntDomain * g0194 = g0193->bucket;
                if (g0194 == (NULL))
                 { int  x = g0193->inf->latestValue;
                  { OID gc_local;
                    g0200UU= _oid_(CFALSE);
                    while ((x <= g0193->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0200UU = Kernel.ctrue;
                        break;} 
                      x= ((g0193->inf->latestValue <= (x+1)) ?
                        (x+1) :
                        g0193->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0194->isa,choco._LinkedListIntDomain))
                 { int  x = g0193->inf->latestValue;
                  { OID gc_local;
                    g0200UU= _oid_(CFALSE);
                    while ((x <= g0193->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0200UU = Kernel.ctrue;
                        break;} 
                      x= choco.getNextValue->fcall(((int) g0194),((int) x));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(x);
                    g0200UU= _oid_(CFALSE);
                    bag *x_support;
                    x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0194)))));
                    for (START(x_support); NEXT(x);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0200UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0199I = not_any(g0200UU);
              } 
            
            if (g0199I == CTRUE) (*choco.removeVal)(_oid_(var2),
                y,
                c->idx2);
              } 
          y= choco.getNextValue->fcall(((int) g0190),((int) y));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(y);
        bag *y_support;
        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0190)))));
        for (START(y_support); NEXT(y);)
        { GC_LOOP;
          { ClaireBoolean * g0201I;
            { OID  g0202UU;
              { IntVar * g0195 = var1;
                AbstractIntDomain * g0196 = g0195->bucket;
                if (g0196 == (NULL))
                 { int  x = g0195->inf->latestValue;
                  { OID gc_local;
                    g0202UU= _oid_(CFALSE);
                    while ((x <= g0195->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0202UU = Kernel.ctrue;
                        break;} 
                      x= ((g0195->inf->latestValue <= (x+1)) ?
                        (x+1) :
                        g0195->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0196->isa,choco._LinkedListIntDomain))
                 { int  x = g0195->inf->latestValue;
                  { OID gc_local;
                    g0202UU= _oid_(CFALSE);
                    while ((x <= g0195->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0202UU = Kernel.ctrue;
                        break;} 
                      x= choco.getNextValue->fcall(((int) g0196),((int) x));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(x);
                    g0202UU= _oid_(CFALSE);
                    bag *x_support;
                    x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0196)))));
                    for (START(x_support); NEXT(x);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0202UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0201I = not_any(g0202UU);
              } 
            
            if (g0201I == CTRUE) (*choco.removeVal)(_oid_(var2),
                y,
                c->idx2);
              } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// updates the support for all values in the domain of v1, and remove unsupported values for v1
/* The c++ function for: reviseV1(c:AC3BinConstraint) [] */
void  choco_reviseV1_AC3BinConstraint(AC3BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { IntVar * var1 = c->v1;
    IntVar * var2 = c->v2;
    BinRelation * feasRel = c->feasRelation;
    ClaireBoolean * feas = c->feasiblePair;
    IntVar * g0203 = var1;
    AbstractIntDomain * g0204 = g0203->bucket;
    if (g0204 == (NULL))
     { int  x = g0203->inf->latestValue;
      { OID gc_local;
        while ((x <= g0203->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0211I;
            { OID  g0212UU;
              { IntVar * g0205 = var2;
                AbstractIntDomain * g0206 = g0205->bucket;
                if (g0206 == (NULL))
                 { int  y = g0205->inf->latestValue;
                  { OID gc_local;
                    g0212UU= _oid_(CFALSE);
                    while ((y <= g0205->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0212UU = Kernel.ctrue;
                        break;} 
                      y= ((g0205->inf->latestValue <= (y+1)) ?
                        (y+1) :
                        g0205->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0206->isa,choco._LinkedListIntDomain))
                 { int  y = g0205->inf->latestValue;
                  { OID gc_local;
                    g0212UU= _oid_(CFALSE);
                    while ((y <= g0205->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0212UU = Kernel.ctrue;
                        break;} 
                      y= choco.getNextValue->fcall(((int) g0206),((int) y));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(y);
                    g0212UU= _oid_(CFALSE);
                    bag *y_support;
                    y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0206)))));
                    for (START(y_support); NEXT(y);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0212UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0211I = not_any(g0212UU);
              } 
            
            if (g0211I == CTRUE) (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              } 
          x= ((g0203->inf->latestValue <= (x+1)) ?
            (x+1) :
            g0203->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0204->isa,choco._LinkedListIntDomain))
     { int  x = g0203->inf->latestValue;
      { OID gc_local;
        while ((x <= g0203->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0213I;
            { OID  g0214UU;
              { IntVar * g0207 = var2;
                AbstractIntDomain * g0208 = g0207->bucket;
                if (g0208 == (NULL))
                 { int  y = g0207->inf->latestValue;
                  { OID gc_local;
                    g0214UU= _oid_(CFALSE);
                    while ((y <= g0207->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0214UU = Kernel.ctrue;
                        break;} 
                      y= ((g0207->inf->latestValue <= (y+1)) ?
                        (y+1) :
                        g0207->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0208->isa,choco._LinkedListIntDomain))
                 { int  y = g0207->inf->latestValue;
                  { OID gc_local;
                    g0214UU= _oid_(CFALSE);
                    while ((y <= g0207->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0214UU = Kernel.ctrue;
                        break;} 
                      y= choco.getNextValue->fcall(((int) g0208),((int) y));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(y);
                    g0214UU= _oid_(CFALSE);
                    bag *y_support;
                    y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0208)))));
                    for (START(y_support); NEXT(y);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0214UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0213I = not_any(g0214UU);
              } 
            
            if (g0213I == CTRUE) (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              } 
          x= choco.getNextValue->fcall(((int) g0204),((int) x));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0204)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          { ClaireBoolean * g0215I;
            { OID  g0216UU;
              { IntVar * g0209 = var2;
                AbstractIntDomain * g0210 = g0209->bucket;
                if (g0210 == (NULL))
                 { int  y = g0209->inf->latestValue;
                  { OID gc_local;
                    g0216UU= _oid_(CFALSE);
                    while ((y <= g0209->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0216UU = Kernel.ctrue;
                        break;} 
                      y= ((g0209->inf->latestValue <= (y+1)) ?
                        (y+1) :
                        g0209->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0210->isa,choco._LinkedListIntDomain))
                 { int  y = g0209->inf->latestValue;
                  { OID gc_local;
                    g0216UU= _oid_(CFALSE);
                    while ((y <= g0209->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0216UU = Kernel.ctrue;
                        break;} 
                      y= choco.getNextValue->fcall(((int) g0210),((int) y));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(y);
                    g0216UU= _oid_(CFALSE);
                    bag *y_support;
                    y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0210)))));
                    for (START(y_support); NEXT(y);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                       { g0216UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0215I = not_any(g0216UU);
              } 
            
            if (g0215I == CTRUE) (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// Maintaining arc consistency
/* The c++ function for: awakeOnInf(c:AC3BinConstraint,idx:integer) [] */
void  choco_awakeOnInf_AC3BinConstraint(AC3BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0217 = c->v1;
      IntVar * g0218 = c->v2;
      BinRelation * g0219 = c->feasRelation;
      ClaireBoolean * g0220 = c->feasiblePair;
      IntVar * g0223 = g0218;
      AbstractIntDomain * g0224 = g0223->bucket;
      if (g0224 == (NULL))
       { int  g0221 = g0223->inf->latestValue;
        { OID gc_local;
          while ((g0221 <= g0223->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0245I;
              { OID  g0246UU;
                { IntVar * g0225 = g0217;
                  AbstractIntDomain * g0226 = g0225->bucket;
                  if (g0226 == (NULL))
                   { int  g0222 = g0225->inf->latestValue;
                    { OID gc_local;
                      g0246UU= _oid_(CFALSE);
                      while ((g0222 <= g0225->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0246UU = Kernel.ctrue;
                          break;} 
                        g0222= ((g0225->inf->latestValue <= (g0222+1)) ?
                          (g0222+1) :
                          g0225->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0226->isa,choco._LinkedListIntDomain))
                   { int  g0222 = g0225->inf->latestValue;
                    { OID gc_local;
                      g0246UU= _oid_(CFALSE);
                      while ((g0222 <= g0225->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0246UU = Kernel.ctrue;
                          break;} 
                        g0222= choco.getNextValue->fcall(((int) g0226),((int) g0222));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0222);
                      g0246UU= _oid_(CFALSE);
                      bag *g0222_support;
                      g0222_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0226)))));
                      for (START(g0222_support); NEXT(g0222);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0246UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0245I = not_any(g0246UU);
                } 
              
              if (g0245I == CTRUE) (*choco.removeVal)(_oid_(g0218),
                  g0221,
                  c->idx2);
                } 
            g0221= ((g0223->inf->latestValue <= (g0221+1)) ?
              (g0221+1) :
              g0223->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0224->isa,choco._LinkedListIntDomain))
       { int  g0221 = g0223->inf->latestValue;
        { OID gc_local;
          while ((g0221 <= g0223->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0247I;
              { OID  g0248UU;
                { IntVar * g0227 = g0217;
                  AbstractIntDomain * g0228 = g0227->bucket;
                  if (g0228 == (NULL))
                   { int  g0222 = g0227->inf->latestValue;
                    { OID gc_local;
                      g0248UU= _oid_(CFALSE);
                      while ((g0222 <= g0227->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0248UU = Kernel.ctrue;
                          break;} 
                        g0222= ((g0227->inf->latestValue <= (g0222+1)) ?
                          (g0222+1) :
                          g0227->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0228->isa,choco._LinkedListIntDomain))
                   { int  g0222 = g0227->inf->latestValue;
                    { OID gc_local;
                      g0248UU= _oid_(CFALSE);
                      while ((g0222 <= g0227->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0248UU = Kernel.ctrue;
                          break;} 
                        g0222= choco.getNextValue->fcall(((int) g0228),((int) g0222));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0222);
                      g0248UU= _oid_(CFALSE);
                      bag *g0222_support;
                      g0222_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0228)))));
                      for (START(g0222_support); NEXT(g0222);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0248UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0247I = not_any(g0248UU);
                } 
              
              if (g0247I == CTRUE) (*choco.removeVal)(_oid_(g0218),
                  g0221,
                  c->idx2);
                } 
            g0221= choco.getNextValue->fcall(((int) g0224),((int) g0221));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0221);
          bag *g0221_support;
          g0221_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0224)))));
          for (START(g0221_support); NEXT(g0221);)
          { GC_LOOP;
            { ClaireBoolean * g0249I;
              { OID  g0250UU;
                { IntVar * g0229 = g0217;
                  AbstractIntDomain * g0230 = g0229->bucket;
                  if (g0230 == (NULL))
                   { int  g0222 = g0229->inf->latestValue;
                    { OID gc_local;
                      g0250UU= _oid_(CFALSE);
                      while ((g0222 <= g0229->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0250UU = Kernel.ctrue;
                          break;} 
                        g0222= ((g0229->inf->latestValue <= (g0222+1)) ?
                          (g0222+1) :
                          g0229->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0230->isa,choco._LinkedListIntDomain))
                   { int  g0222 = g0229->inf->latestValue;
                    { OID gc_local;
                      g0250UU= _oid_(CFALSE);
                      while ((g0222 <= g0229->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0250UU = Kernel.ctrue;
                          break;} 
                        g0222= choco.getNextValue->fcall(((int) g0230),((int) g0222));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0222);
                      g0250UU= _oid_(CFALSE);
                      bag *g0222_support;
                      g0222_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0230)))));
                      for (START(g0222_support); NEXT(g0222);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0219),((int) g0222),((int) g0221))) == _oid_(g0220))
                         { g0250UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0249I = not_any(g0250UU);
                } 
              
              if (g0249I == CTRUE) (*choco.removeVal)(_oid_(g0218),
                  g0221,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
    { IntVar * g0231 = c->v1;
      IntVar * g0232 = c->v2;
      BinRelation * g0233 = c->feasRelation;
      ClaireBoolean * g0234 = c->feasiblePair;
      IntVar * g0237 = g0231;
      AbstractIntDomain * g0238 = g0237->bucket;
      if (g0238 == (NULL))
       { int  g0235 = g0237->inf->latestValue;
        { OID gc_local;
          while ((g0235 <= g0237->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0251I;
              { OID  g0252UU;
                { IntVar * g0239 = g0232;
                  AbstractIntDomain * g0240 = g0239->bucket;
                  if (g0240 == (NULL))
                   { int  g0236 = g0239->inf->latestValue;
                    { OID gc_local;
                      g0252UU= _oid_(CFALSE);
                      while ((g0236 <= g0239->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0252UU = Kernel.ctrue;
                          break;} 
                        g0236= ((g0239->inf->latestValue <= (g0236+1)) ?
                          (g0236+1) :
                          g0239->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0240->isa,choco._LinkedListIntDomain))
                   { int  g0236 = g0239->inf->latestValue;
                    { OID gc_local;
                      g0252UU= _oid_(CFALSE);
                      while ((g0236 <= g0239->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0252UU = Kernel.ctrue;
                          break;} 
                        g0236= choco.getNextValue->fcall(((int) g0240),((int) g0236));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0236);
                      g0252UU= _oid_(CFALSE);
                      bag *g0236_support;
                      g0236_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0240)))));
                      for (START(g0236_support); NEXT(g0236);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0252UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0251I = not_any(g0252UU);
                } 
              
              if (g0251I == CTRUE) (*choco.removeVal)(_oid_(g0231),
                  g0235,
                  c->idx1);
                } 
            g0235= ((g0237->inf->latestValue <= (g0235+1)) ?
              (g0235+1) :
              g0237->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0238->isa,choco._LinkedListIntDomain))
       { int  g0235 = g0237->inf->latestValue;
        { OID gc_local;
          while ((g0235 <= g0237->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0253I;
              { OID  g0254UU;
                { IntVar * g0241 = g0232;
                  AbstractIntDomain * g0242 = g0241->bucket;
                  if (g0242 == (NULL))
                   { int  g0236 = g0241->inf->latestValue;
                    { OID gc_local;
                      g0254UU= _oid_(CFALSE);
                      while ((g0236 <= g0241->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0254UU = Kernel.ctrue;
                          break;} 
                        g0236= ((g0241->inf->latestValue <= (g0236+1)) ?
                          (g0236+1) :
                          g0241->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0242->isa,choco._LinkedListIntDomain))
                   { int  g0236 = g0241->inf->latestValue;
                    { OID gc_local;
                      g0254UU= _oid_(CFALSE);
                      while ((g0236 <= g0241->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0254UU = Kernel.ctrue;
                          break;} 
                        g0236= choco.getNextValue->fcall(((int) g0242),((int) g0236));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0236);
                      g0254UU= _oid_(CFALSE);
                      bag *g0236_support;
                      g0236_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0242)))));
                      for (START(g0236_support); NEXT(g0236);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0254UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0253I = not_any(g0254UU);
                } 
              
              if (g0253I == CTRUE) (*choco.removeVal)(_oid_(g0231),
                  g0235,
                  c->idx1);
                } 
            g0235= choco.getNextValue->fcall(((int) g0238),((int) g0235));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0235);
          bag *g0235_support;
          g0235_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0238)))));
          for (START(g0235_support); NEXT(g0235);)
          { GC_LOOP;
            { ClaireBoolean * g0255I;
              { OID  g0256UU;
                { IntVar * g0243 = g0232;
                  AbstractIntDomain * g0244 = g0243->bucket;
                  if (g0244 == (NULL))
                   { int  g0236 = g0243->inf->latestValue;
                    { OID gc_local;
                      g0256UU= _oid_(CFALSE);
                      while ((g0236 <= g0243->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0256UU = Kernel.ctrue;
                          break;} 
                        g0236= ((g0243->inf->latestValue <= (g0236+1)) ?
                          (g0236+1) :
                          g0243->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0244->isa,choco._LinkedListIntDomain))
                   { int  g0236 = g0243->inf->latestValue;
                    { OID gc_local;
                      g0256UU= _oid_(CFALSE);
                      while ((g0236 <= g0243->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0256UU = Kernel.ctrue;
                          break;} 
                        g0236= choco.getNextValue->fcall(((int) g0244),((int) g0236));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0236);
                      g0256UU= _oid_(CFALSE);
                      bag *g0236_support;
                      g0236_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0244)))));
                      for (START(g0236_support); NEXT(g0236);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0233),((int) g0235),((int) g0236))) == _oid_(g0234))
                         { g0256UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0255I = not_any(g0256UU);
                } 
              
              if (g0255I == CTRUE) (*choco.removeVal)(_oid_(g0231),
                  g0235,
                  c->idx1);
                } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnSup(c:AC3BinConstraint,idx:integer) [] */
void  choco_awakeOnSup_AC3BinConstraint(AC3BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0257 = c->v1;
      IntVar * g0258 = c->v2;
      BinRelation * g0259 = c->feasRelation;
      ClaireBoolean * g0260 = c->feasiblePair;
      IntVar * g0263 = g0258;
      AbstractIntDomain * g0264 = g0263->bucket;
      if (g0264 == (NULL))
       { int  g0261 = g0263->inf->latestValue;
        { OID gc_local;
          while ((g0261 <= g0263->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0285I;
              { OID  g0286UU;
                { IntVar * g0265 = g0257;
                  AbstractIntDomain * g0266 = g0265->bucket;
                  if (g0266 == (NULL))
                   { int  g0262 = g0265->inf->latestValue;
                    { OID gc_local;
                      g0286UU= _oid_(CFALSE);
                      while ((g0262 <= g0265->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0286UU = Kernel.ctrue;
                          break;} 
                        g0262= ((g0265->inf->latestValue <= (g0262+1)) ?
                          (g0262+1) :
                          g0265->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0266->isa,choco._LinkedListIntDomain))
                   { int  g0262 = g0265->inf->latestValue;
                    { OID gc_local;
                      g0286UU= _oid_(CFALSE);
                      while ((g0262 <= g0265->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0286UU = Kernel.ctrue;
                          break;} 
                        g0262= choco.getNextValue->fcall(((int) g0266),((int) g0262));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0262);
                      g0286UU= _oid_(CFALSE);
                      bag *g0262_support;
                      g0262_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0266)))));
                      for (START(g0262_support); NEXT(g0262);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0286UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0285I = not_any(g0286UU);
                } 
              
              if (g0285I == CTRUE) (*choco.removeVal)(_oid_(g0258),
                  g0261,
                  c->idx2);
                } 
            g0261= ((g0263->inf->latestValue <= (g0261+1)) ?
              (g0261+1) :
              g0263->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0264->isa,choco._LinkedListIntDomain))
       { int  g0261 = g0263->inf->latestValue;
        { OID gc_local;
          while ((g0261 <= g0263->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0287I;
              { OID  g0288UU;
                { IntVar * g0267 = g0257;
                  AbstractIntDomain * g0268 = g0267->bucket;
                  if (g0268 == (NULL))
                   { int  g0262 = g0267->inf->latestValue;
                    { OID gc_local;
                      g0288UU= _oid_(CFALSE);
                      while ((g0262 <= g0267->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0288UU = Kernel.ctrue;
                          break;} 
                        g0262= ((g0267->inf->latestValue <= (g0262+1)) ?
                          (g0262+1) :
                          g0267->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0268->isa,choco._LinkedListIntDomain))
                   { int  g0262 = g0267->inf->latestValue;
                    { OID gc_local;
                      g0288UU= _oid_(CFALSE);
                      while ((g0262 <= g0267->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0288UU = Kernel.ctrue;
                          break;} 
                        g0262= choco.getNextValue->fcall(((int) g0268),((int) g0262));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0262);
                      g0288UU= _oid_(CFALSE);
                      bag *g0262_support;
                      g0262_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0268)))));
                      for (START(g0262_support); NEXT(g0262);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0288UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0287I = not_any(g0288UU);
                } 
              
              if (g0287I == CTRUE) (*choco.removeVal)(_oid_(g0258),
                  g0261,
                  c->idx2);
                } 
            g0261= choco.getNextValue->fcall(((int) g0264),((int) g0261));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0261);
          bag *g0261_support;
          g0261_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0264)))));
          for (START(g0261_support); NEXT(g0261);)
          { GC_LOOP;
            { ClaireBoolean * g0289I;
              { OID  g0290UU;
                { IntVar * g0269 = g0257;
                  AbstractIntDomain * g0270 = g0269->bucket;
                  if (g0270 == (NULL))
                   { int  g0262 = g0269->inf->latestValue;
                    { OID gc_local;
                      g0290UU= _oid_(CFALSE);
                      while ((g0262 <= g0269->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0290UU = Kernel.ctrue;
                          break;} 
                        g0262= ((g0269->inf->latestValue <= (g0262+1)) ?
                          (g0262+1) :
                          g0269->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0270->isa,choco._LinkedListIntDomain))
                   { int  g0262 = g0269->inf->latestValue;
                    { OID gc_local;
                      g0290UU= _oid_(CFALSE);
                      while ((g0262 <= g0269->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0290UU = Kernel.ctrue;
                          break;} 
                        g0262= choco.getNextValue->fcall(((int) g0270),((int) g0262));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0262);
                      g0290UU= _oid_(CFALSE);
                      bag *g0262_support;
                      g0262_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0270)))));
                      for (START(g0262_support); NEXT(g0262);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0259),((int) g0262),((int) g0261))) == _oid_(g0260))
                         { g0290UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0289I = not_any(g0290UU);
                } 
              
              if (g0289I == CTRUE) (*choco.removeVal)(_oid_(g0258),
                  g0261,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
    { IntVar * g0271 = c->v1;
      IntVar * g0272 = c->v2;
      BinRelation * g0273 = c->feasRelation;
      ClaireBoolean * g0274 = c->feasiblePair;
      IntVar * g0277 = g0271;
      AbstractIntDomain * g0278 = g0277->bucket;
      if (g0278 == (NULL))
       { int  g0275 = g0277->inf->latestValue;
        { OID gc_local;
          while ((g0275 <= g0277->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0291I;
              { OID  g0292UU;
                { IntVar * g0279 = g0272;
                  AbstractIntDomain * g0280 = g0279->bucket;
                  if (g0280 == (NULL))
                   { int  g0276 = g0279->inf->latestValue;
                    { OID gc_local;
                      g0292UU= _oid_(CFALSE);
                      while ((g0276 <= g0279->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0292UU = Kernel.ctrue;
                          break;} 
                        g0276= ((g0279->inf->latestValue <= (g0276+1)) ?
                          (g0276+1) :
                          g0279->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0280->isa,choco._LinkedListIntDomain))
                   { int  g0276 = g0279->inf->latestValue;
                    { OID gc_local;
                      g0292UU= _oid_(CFALSE);
                      while ((g0276 <= g0279->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0292UU = Kernel.ctrue;
                          break;} 
                        g0276= choco.getNextValue->fcall(((int) g0280),((int) g0276));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0276);
                      g0292UU= _oid_(CFALSE);
                      bag *g0276_support;
                      g0276_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0280)))));
                      for (START(g0276_support); NEXT(g0276);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0292UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0291I = not_any(g0292UU);
                } 
              
              if (g0291I == CTRUE) (*choco.removeVal)(_oid_(g0271),
                  g0275,
                  c->idx1);
                } 
            g0275= ((g0277->inf->latestValue <= (g0275+1)) ?
              (g0275+1) :
              g0277->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0278->isa,choco._LinkedListIntDomain))
       { int  g0275 = g0277->inf->latestValue;
        { OID gc_local;
          while ((g0275 <= g0277->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0293I;
              { OID  g0294UU;
                { IntVar * g0281 = g0272;
                  AbstractIntDomain * g0282 = g0281->bucket;
                  if (g0282 == (NULL))
                   { int  g0276 = g0281->inf->latestValue;
                    { OID gc_local;
                      g0294UU= _oid_(CFALSE);
                      while ((g0276 <= g0281->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0294UU = Kernel.ctrue;
                          break;} 
                        g0276= ((g0281->inf->latestValue <= (g0276+1)) ?
                          (g0276+1) :
                          g0281->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0282->isa,choco._LinkedListIntDomain))
                   { int  g0276 = g0281->inf->latestValue;
                    { OID gc_local;
                      g0294UU= _oid_(CFALSE);
                      while ((g0276 <= g0281->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0294UU = Kernel.ctrue;
                          break;} 
                        g0276= choco.getNextValue->fcall(((int) g0282),((int) g0276));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0276);
                      g0294UU= _oid_(CFALSE);
                      bag *g0276_support;
                      g0276_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0282)))));
                      for (START(g0276_support); NEXT(g0276);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0294UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0293I = not_any(g0294UU);
                } 
              
              if (g0293I == CTRUE) (*choco.removeVal)(_oid_(g0271),
                  g0275,
                  c->idx1);
                } 
            g0275= choco.getNextValue->fcall(((int) g0278),((int) g0275));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0275);
          bag *g0275_support;
          g0275_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0278)))));
          for (START(g0275_support); NEXT(g0275);)
          { GC_LOOP;
            { ClaireBoolean * g0295I;
              { OID  g0296UU;
                { IntVar * g0283 = g0272;
                  AbstractIntDomain * g0284 = g0283->bucket;
                  if (g0284 == (NULL))
                   { int  g0276 = g0283->inf->latestValue;
                    { OID gc_local;
                      g0296UU= _oid_(CFALSE);
                      while ((g0276 <= g0283->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0296UU = Kernel.ctrue;
                          break;} 
                        g0276= ((g0283->inf->latestValue <= (g0276+1)) ?
                          (g0276+1) :
                          g0283->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0284->isa,choco._LinkedListIntDomain))
                   { int  g0276 = g0283->inf->latestValue;
                    { OID gc_local;
                      g0296UU= _oid_(CFALSE);
                      while ((g0276 <= g0283->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0296UU = Kernel.ctrue;
                          break;} 
                        g0276= choco.getNextValue->fcall(((int) g0284),((int) g0276));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0276);
                      g0296UU= _oid_(CFALSE);
                      bag *g0276_support;
                      g0276_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0284)))));
                      for (START(g0276_support); NEXT(g0276);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0273),((int) g0275),((int) g0276))) == _oid_(g0274))
                         { g0296UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0295I = not_any(g0296UU);
                } 
              
              if (g0295I == CTRUE) (*choco.removeVal)(_oid_(g0271),
                  g0275,
                  c->idx1);
                } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRem(c:AC3BinConstraint,idx:integer,oldval:integer) [] */
void  choco_awakeOnRem_AC3BinConstraint(AC3BinConstraint *c,int idx,int oldval)
{ if (idx == 1) 
  { { IntVar * g0297 = c->v1;
      IntVar * g0298 = c->v2;
      BinRelation * g0299 = c->feasRelation;
      ClaireBoolean * g0300 = c->feasiblePair;
      IntVar * g0303 = g0298;
      AbstractIntDomain * g0304 = g0303->bucket;
      if (g0304 == (NULL))
       { int  g0301 = g0303->inf->latestValue;
        { OID gc_local;
          while ((g0301 <= g0303->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0325I;
              { OID  g0326UU;
                { IntVar * g0305 = g0297;
                  AbstractIntDomain * g0306 = g0305->bucket;
                  if (g0306 == (NULL))
                   { int  g0302 = g0305->inf->latestValue;
                    { OID gc_local;
                      g0326UU= _oid_(CFALSE);
                      while ((g0302 <= g0305->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0326UU = Kernel.ctrue;
                          break;} 
                        g0302= ((g0305->inf->latestValue <= (g0302+1)) ?
                          (g0302+1) :
                          g0305->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0306->isa,choco._LinkedListIntDomain))
                   { int  g0302 = g0305->inf->latestValue;
                    { OID gc_local;
                      g0326UU= _oid_(CFALSE);
                      while ((g0302 <= g0305->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0326UU = Kernel.ctrue;
                          break;} 
                        g0302= choco.getNextValue->fcall(((int) g0306),((int) g0302));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0302);
                      g0326UU= _oid_(CFALSE);
                      bag *g0302_support;
                      g0302_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0306)))));
                      for (START(g0302_support); NEXT(g0302);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0326UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0325I = not_any(g0326UU);
                } 
              
              if (g0325I == CTRUE) (*choco.removeVal)(_oid_(g0298),
                  g0301,
                  c->idx2);
                } 
            g0301= ((g0303->inf->latestValue <= (g0301+1)) ?
              (g0301+1) :
              g0303->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0304->isa,choco._LinkedListIntDomain))
       { int  g0301 = g0303->inf->latestValue;
        { OID gc_local;
          while ((g0301 <= g0303->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0327I;
              { OID  g0328UU;
                { IntVar * g0307 = g0297;
                  AbstractIntDomain * g0308 = g0307->bucket;
                  if (g0308 == (NULL))
                   { int  g0302 = g0307->inf->latestValue;
                    { OID gc_local;
                      g0328UU= _oid_(CFALSE);
                      while ((g0302 <= g0307->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0328UU = Kernel.ctrue;
                          break;} 
                        g0302= ((g0307->inf->latestValue <= (g0302+1)) ?
                          (g0302+1) :
                          g0307->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0308->isa,choco._LinkedListIntDomain))
                   { int  g0302 = g0307->inf->latestValue;
                    { OID gc_local;
                      g0328UU= _oid_(CFALSE);
                      while ((g0302 <= g0307->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0328UU = Kernel.ctrue;
                          break;} 
                        g0302= choco.getNextValue->fcall(((int) g0308),((int) g0302));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0302);
                      g0328UU= _oid_(CFALSE);
                      bag *g0302_support;
                      g0302_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0308)))));
                      for (START(g0302_support); NEXT(g0302);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0328UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0327I = not_any(g0328UU);
                } 
              
              if (g0327I == CTRUE) (*choco.removeVal)(_oid_(g0298),
                  g0301,
                  c->idx2);
                } 
            g0301= choco.getNextValue->fcall(((int) g0304),((int) g0301));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0301);
          bag *g0301_support;
          g0301_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0304)))));
          for (START(g0301_support); NEXT(g0301);)
          { GC_LOOP;
            { ClaireBoolean * g0329I;
              { OID  g0330UU;
                { IntVar * g0309 = g0297;
                  AbstractIntDomain * g0310 = g0309->bucket;
                  if (g0310 == (NULL))
                   { int  g0302 = g0309->inf->latestValue;
                    { OID gc_local;
                      g0330UU= _oid_(CFALSE);
                      while ((g0302 <= g0309->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0330UU = Kernel.ctrue;
                          break;} 
                        g0302= ((g0309->inf->latestValue <= (g0302+1)) ?
                          (g0302+1) :
                          g0309->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0310->isa,choco._LinkedListIntDomain))
                   { int  g0302 = g0309->inf->latestValue;
                    { OID gc_local;
                      g0330UU= _oid_(CFALSE);
                      while ((g0302 <= g0309->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0330UU = Kernel.ctrue;
                          break;} 
                        g0302= choco.getNextValue->fcall(((int) g0310),((int) g0302));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0302);
                      g0330UU= _oid_(CFALSE);
                      bag *g0302_support;
                      g0302_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0310)))));
                      for (START(g0302_support); NEXT(g0302);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0299),((int) g0302),((int) g0301))) == _oid_(g0300))
                         { g0330UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0329I = not_any(g0330UU);
                } 
              
              if (g0329I == CTRUE) (*choco.removeVal)(_oid_(g0298),
                  g0301,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
    { IntVar * g0311 = c->v1;
      IntVar * g0312 = c->v2;
      BinRelation * g0313 = c->feasRelation;
      ClaireBoolean * g0314 = c->feasiblePair;
      IntVar * g0317 = g0311;
      AbstractIntDomain * g0318 = g0317->bucket;
      if (g0318 == (NULL))
       { int  g0315 = g0317->inf->latestValue;
        { OID gc_local;
          while ((g0315 <= g0317->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0331I;
              { OID  g0332UU;
                { IntVar * g0319 = g0312;
                  AbstractIntDomain * g0320 = g0319->bucket;
                  if (g0320 == (NULL))
                   { int  g0316 = g0319->inf->latestValue;
                    { OID gc_local;
                      g0332UU= _oid_(CFALSE);
                      while ((g0316 <= g0319->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0332UU = Kernel.ctrue;
                          break;} 
                        g0316= ((g0319->inf->latestValue <= (g0316+1)) ?
                          (g0316+1) :
                          g0319->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0320->isa,choco._LinkedListIntDomain))
                   { int  g0316 = g0319->inf->latestValue;
                    { OID gc_local;
                      g0332UU= _oid_(CFALSE);
                      while ((g0316 <= g0319->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0332UU = Kernel.ctrue;
                          break;} 
                        g0316= choco.getNextValue->fcall(((int) g0320),((int) g0316));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0316);
                      g0332UU= _oid_(CFALSE);
                      bag *g0316_support;
                      g0316_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0320)))));
                      for (START(g0316_support); NEXT(g0316);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0332UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0331I = not_any(g0332UU);
                } 
              
              if (g0331I == CTRUE) (*choco.removeVal)(_oid_(g0311),
                  g0315,
                  c->idx1);
                } 
            g0315= ((g0317->inf->latestValue <= (g0315+1)) ?
              (g0315+1) :
              g0317->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0318->isa,choco._LinkedListIntDomain))
       { int  g0315 = g0317->inf->latestValue;
        { OID gc_local;
          while ((g0315 <= g0317->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0333I;
              { OID  g0334UU;
                { IntVar * g0321 = g0312;
                  AbstractIntDomain * g0322 = g0321->bucket;
                  if (g0322 == (NULL))
                   { int  g0316 = g0321->inf->latestValue;
                    { OID gc_local;
                      g0334UU= _oid_(CFALSE);
                      while ((g0316 <= g0321->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0334UU = Kernel.ctrue;
                          break;} 
                        g0316= ((g0321->inf->latestValue <= (g0316+1)) ?
                          (g0316+1) :
                          g0321->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0322->isa,choco._LinkedListIntDomain))
                   { int  g0316 = g0321->inf->latestValue;
                    { OID gc_local;
                      g0334UU= _oid_(CFALSE);
                      while ((g0316 <= g0321->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0334UU = Kernel.ctrue;
                          break;} 
                        g0316= choco.getNextValue->fcall(((int) g0322),((int) g0316));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0316);
                      g0334UU= _oid_(CFALSE);
                      bag *g0316_support;
                      g0316_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0322)))));
                      for (START(g0316_support); NEXT(g0316);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0334UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0333I = not_any(g0334UU);
                } 
              
              if (g0333I == CTRUE) (*choco.removeVal)(_oid_(g0311),
                  g0315,
                  c->idx1);
                } 
            g0315= choco.getNextValue->fcall(((int) g0318),((int) g0315));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0315);
          bag *g0315_support;
          g0315_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0318)))));
          for (START(g0315_support); NEXT(g0315);)
          { GC_LOOP;
            { ClaireBoolean * g0335I;
              { OID  g0336UU;
                { IntVar * g0323 = g0312;
                  AbstractIntDomain * g0324 = g0323->bucket;
                  if (g0324 == (NULL))
                   { int  g0316 = g0323->inf->latestValue;
                    { OID gc_local;
                      g0336UU= _oid_(CFALSE);
                      while ((g0316 <= g0323->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0336UU = Kernel.ctrue;
                          break;} 
                        g0316= ((g0323->inf->latestValue <= (g0316+1)) ?
                          (g0316+1) :
                          g0323->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0324->isa,choco._LinkedListIntDomain))
                   { int  g0316 = g0323->inf->latestValue;
                    { OID gc_local;
                      g0336UU= _oid_(CFALSE);
                      while ((g0316 <= g0323->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0336UU = Kernel.ctrue;
                          break;} 
                        g0316= choco.getNextValue->fcall(((int) g0324),((int) g0316));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0316);
                      g0336UU= _oid_(CFALSE);
                      bag *g0316_support;
                      g0316_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0324)))));
                      for (START(g0316_support); NEXT(g0316);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0313),((int) g0315),((int) g0316))) == _oid_(g0314))
                         { g0336UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0335I = not_any(g0336UU);
                } 
              
              if (g0335I == CTRUE) (*choco.removeVal)(_oid_(g0311),
                  g0315,
                  c->idx1);
                } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnVar(c:AC3BinConstraint,idx:integer) [] */
void  choco_awakeOnVar_AC3BinConstraint(AC3BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0337 = c->v1;
      IntVar * g0338 = c->v2;
      BinRelation * g0339 = c->feasRelation;
      ClaireBoolean * g0340 = c->feasiblePair;
      IntVar * g0343 = g0338;
      AbstractIntDomain * g0344 = g0343->bucket;
      if (g0344 == (NULL))
       { int  g0341 = g0343->inf->latestValue;
        { OID gc_local;
          while ((g0341 <= g0343->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0365I;
              { OID  g0366UU;
                { IntVar * g0345 = g0337;
                  AbstractIntDomain * g0346 = g0345->bucket;
                  if (g0346 == (NULL))
                   { int  g0342 = g0345->inf->latestValue;
                    { OID gc_local;
                      g0366UU= _oid_(CFALSE);
                      while ((g0342 <= g0345->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0366UU = Kernel.ctrue;
                          break;} 
                        g0342= ((g0345->inf->latestValue <= (g0342+1)) ?
                          (g0342+1) :
                          g0345->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0346->isa,choco._LinkedListIntDomain))
                   { int  g0342 = g0345->inf->latestValue;
                    { OID gc_local;
                      g0366UU= _oid_(CFALSE);
                      while ((g0342 <= g0345->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0366UU = Kernel.ctrue;
                          break;} 
                        g0342= choco.getNextValue->fcall(((int) g0346),((int) g0342));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0342);
                      g0366UU= _oid_(CFALSE);
                      bag *g0342_support;
                      g0342_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0346)))));
                      for (START(g0342_support); NEXT(g0342);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0366UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0365I = not_any(g0366UU);
                } 
              
              if (g0365I == CTRUE) (*choco.removeVal)(_oid_(g0338),
                  g0341,
                  c->idx2);
                } 
            g0341= ((g0343->inf->latestValue <= (g0341+1)) ?
              (g0341+1) :
              g0343->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0344->isa,choco._LinkedListIntDomain))
       { int  g0341 = g0343->inf->latestValue;
        { OID gc_local;
          while ((g0341 <= g0343->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0367I;
              { OID  g0368UU;
                { IntVar * g0347 = g0337;
                  AbstractIntDomain * g0348 = g0347->bucket;
                  if (g0348 == (NULL))
                   { int  g0342 = g0347->inf->latestValue;
                    { OID gc_local;
                      g0368UU= _oid_(CFALSE);
                      while ((g0342 <= g0347->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0368UU = Kernel.ctrue;
                          break;} 
                        g0342= ((g0347->inf->latestValue <= (g0342+1)) ?
                          (g0342+1) :
                          g0347->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0348->isa,choco._LinkedListIntDomain))
                   { int  g0342 = g0347->inf->latestValue;
                    { OID gc_local;
                      g0368UU= _oid_(CFALSE);
                      while ((g0342 <= g0347->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0368UU = Kernel.ctrue;
                          break;} 
                        g0342= choco.getNextValue->fcall(((int) g0348),((int) g0342));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0342);
                      g0368UU= _oid_(CFALSE);
                      bag *g0342_support;
                      g0342_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0348)))));
                      for (START(g0342_support); NEXT(g0342);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0368UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0367I = not_any(g0368UU);
                } 
              
              if (g0367I == CTRUE) (*choco.removeVal)(_oid_(g0338),
                  g0341,
                  c->idx2);
                } 
            g0341= choco.getNextValue->fcall(((int) g0344),((int) g0341));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0341);
          bag *g0341_support;
          g0341_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0344)))));
          for (START(g0341_support); NEXT(g0341);)
          { GC_LOOP;
            { ClaireBoolean * g0369I;
              { OID  g0370UU;
                { IntVar * g0349 = g0337;
                  AbstractIntDomain * g0350 = g0349->bucket;
                  if (g0350 == (NULL))
                   { int  g0342 = g0349->inf->latestValue;
                    { OID gc_local;
                      g0370UU= _oid_(CFALSE);
                      while ((g0342 <= g0349->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0370UU = Kernel.ctrue;
                          break;} 
                        g0342= ((g0349->inf->latestValue <= (g0342+1)) ?
                          (g0342+1) :
                          g0349->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0350->isa,choco._LinkedListIntDomain))
                   { int  g0342 = g0349->inf->latestValue;
                    { OID gc_local;
                      g0370UU= _oid_(CFALSE);
                      while ((g0342 <= g0349->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0370UU = Kernel.ctrue;
                          break;} 
                        g0342= choco.getNextValue->fcall(((int) g0350),((int) g0342));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0342);
                      g0370UU= _oid_(CFALSE);
                      bag *g0342_support;
                      g0342_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0350)))));
                      for (START(g0342_support); NEXT(g0342);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0339),((int) g0342),((int) g0341))) == _oid_(g0340))
                         { g0370UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0369I = not_any(g0370UU);
                } 
              
              if (g0369I == CTRUE) (*choco.removeVal)(_oid_(g0338),
                  g0341,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
    { IntVar * g0351 = c->v1;
      IntVar * g0352 = c->v2;
      BinRelation * g0353 = c->feasRelation;
      ClaireBoolean * g0354 = c->feasiblePair;
      IntVar * g0357 = g0351;
      AbstractIntDomain * g0358 = g0357->bucket;
      if (g0358 == (NULL))
       { int  g0355 = g0357->inf->latestValue;
        { OID gc_local;
          while ((g0355 <= g0357->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0371I;
              { OID  g0372UU;
                { IntVar * g0359 = g0352;
                  AbstractIntDomain * g0360 = g0359->bucket;
                  if (g0360 == (NULL))
                   { int  g0356 = g0359->inf->latestValue;
                    { OID gc_local;
                      g0372UU= _oid_(CFALSE);
                      while ((g0356 <= g0359->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0372UU = Kernel.ctrue;
                          break;} 
                        g0356= ((g0359->inf->latestValue <= (g0356+1)) ?
                          (g0356+1) :
                          g0359->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0360->isa,choco._LinkedListIntDomain))
                   { int  g0356 = g0359->inf->latestValue;
                    { OID gc_local;
                      g0372UU= _oid_(CFALSE);
                      while ((g0356 <= g0359->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0372UU = Kernel.ctrue;
                          break;} 
                        g0356= choco.getNextValue->fcall(((int) g0360),((int) g0356));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0356);
                      g0372UU= _oid_(CFALSE);
                      bag *g0356_support;
                      g0356_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0360)))));
                      for (START(g0356_support); NEXT(g0356);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0372UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0371I = not_any(g0372UU);
                } 
              
              if (g0371I == CTRUE) (*choco.removeVal)(_oid_(g0351),
                  g0355,
                  c->idx1);
                } 
            g0355= ((g0357->inf->latestValue <= (g0355+1)) ?
              (g0355+1) :
              g0357->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0358->isa,choco._LinkedListIntDomain))
       { int  g0355 = g0357->inf->latestValue;
        { OID gc_local;
          while ((g0355 <= g0357->sup->latestValue))
          { GC_LOOP;
            { ClaireBoolean * g0373I;
              { OID  g0374UU;
                { IntVar * g0361 = g0352;
                  AbstractIntDomain * g0362 = g0361->bucket;
                  if (g0362 == (NULL))
                   { int  g0356 = g0361->inf->latestValue;
                    { OID gc_local;
                      g0374UU= _oid_(CFALSE);
                      while ((g0356 <= g0361->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0374UU = Kernel.ctrue;
                          break;} 
                        g0356= ((g0361->inf->latestValue <= (g0356+1)) ?
                          (g0356+1) :
                          g0361->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0362->isa,choco._LinkedListIntDomain))
                   { int  g0356 = g0361->inf->latestValue;
                    { OID gc_local;
                      g0374UU= _oid_(CFALSE);
                      while ((g0356 <= g0361->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0374UU = Kernel.ctrue;
                          break;} 
                        g0356= choco.getNextValue->fcall(((int) g0362),((int) g0356));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0356);
                      g0374UU= _oid_(CFALSE);
                      bag *g0356_support;
                      g0356_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0362)))));
                      for (START(g0356_support); NEXT(g0356);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0374UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0373I = not_any(g0374UU);
                } 
              
              if (g0373I == CTRUE) (*choco.removeVal)(_oid_(g0351),
                  g0355,
                  c->idx1);
                } 
            g0355= choco.getNextValue->fcall(((int) g0358),((int) g0355));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0355);
          bag *g0355_support;
          g0355_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0358)))));
          for (START(g0355_support); NEXT(g0355);)
          { GC_LOOP;
            { ClaireBoolean * g0375I;
              { OID  g0376UU;
                { IntVar * g0363 = g0352;
                  AbstractIntDomain * g0364 = g0363->bucket;
                  if (g0364 == (NULL))
                   { int  g0356 = g0363->inf->latestValue;
                    { OID gc_local;
                      g0376UU= _oid_(CFALSE);
                      while ((g0356 <= g0363->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0376UU = Kernel.ctrue;
                          break;} 
                        g0356= ((g0363->inf->latestValue <= (g0356+1)) ?
                          (g0356+1) :
                          g0363->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0364->isa,choco._LinkedListIntDomain))
                   { int  g0356 = g0363->inf->latestValue;
                    { OID gc_local;
                      g0376UU= _oid_(CFALSE);
                      while ((g0356 <= g0363->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0376UU = Kernel.ctrue;
                          break;} 
                        g0356= choco.getNextValue->fcall(((int) g0364),((int) g0356));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0356);
                      g0376UU= _oid_(CFALSE);
                      bag *g0356_support;
                      g0356_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0364)))));
                      for (START(g0356_support); NEXT(g0356);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0353),((int) g0355),((int) g0356))) == _oid_(g0354))
                         { g0376UU = Kernel.ctrue;
                          break;} 
                        GC_UNLOOP;} 
                      } 
                    } 
                g0375I = not_any(g0376UU);
                } 
              
              if (g0375I == CTRUE) (*choco.removeVal)(_oid_(g0351),
                  g0355,
                  c->idx1);
                } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: propagate(c:AC3BinConstraint) [] */
void  choco_propagate_AC3BinConstraint(AC3BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { IntVar * g0377 = c->v1;
    IntVar * g0378 = c->v2;
    BinRelation * g0379 = c->feasRelation;
    ClaireBoolean * g0380 = c->feasiblePair;
    IntVar * g0383 = g0378;
    AbstractIntDomain * g0384 = g0383->bucket;
    if (g0384 == (NULL))
     { int  g0381 = g0383->inf->latestValue;
      { OID gc_local;
        while ((g0381 <= g0383->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0405I;
            { OID  g0406UU;
              { IntVar * g0385 = g0377;
                AbstractIntDomain * g0386 = g0385->bucket;
                if (g0386 == (NULL))
                 { int  g0382 = g0385->inf->latestValue;
                  { OID gc_local;
                    g0406UU= _oid_(CFALSE);
                    while ((g0382 <= g0385->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0406UU = Kernel.ctrue;
                        break;} 
                      g0382= ((g0385->inf->latestValue <= (g0382+1)) ?
                        (g0382+1) :
                        g0385->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0386->isa,choco._LinkedListIntDomain))
                 { int  g0382 = g0385->inf->latestValue;
                  { OID gc_local;
                    g0406UU= _oid_(CFALSE);
                    while ((g0382 <= g0385->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0406UU = Kernel.ctrue;
                        break;} 
                      g0382= choco.getNextValue->fcall(((int) g0386),((int) g0382));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0382);
                    g0406UU= _oid_(CFALSE);
                    bag *g0382_support;
                    g0382_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0386)))));
                    for (START(g0382_support); NEXT(g0382);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0406UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0405I = not_any(g0406UU);
              } 
            
            if (g0405I == CTRUE) (*choco.removeVal)(_oid_(g0378),
                g0381,
                c->idx2);
              } 
          g0381= ((g0383->inf->latestValue <= (g0381+1)) ?
            (g0381+1) :
            g0383->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0384->isa,choco._LinkedListIntDomain))
     { int  g0381 = g0383->inf->latestValue;
      { OID gc_local;
        while ((g0381 <= g0383->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0407I;
            { OID  g0408UU;
              { IntVar * g0387 = g0377;
                AbstractIntDomain * g0388 = g0387->bucket;
                if (g0388 == (NULL))
                 { int  g0382 = g0387->inf->latestValue;
                  { OID gc_local;
                    g0408UU= _oid_(CFALSE);
                    while ((g0382 <= g0387->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0408UU = Kernel.ctrue;
                        break;} 
                      g0382= ((g0387->inf->latestValue <= (g0382+1)) ?
                        (g0382+1) :
                        g0387->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0388->isa,choco._LinkedListIntDomain))
                 { int  g0382 = g0387->inf->latestValue;
                  { OID gc_local;
                    g0408UU= _oid_(CFALSE);
                    while ((g0382 <= g0387->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0408UU = Kernel.ctrue;
                        break;} 
                      g0382= choco.getNextValue->fcall(((int) g0388),((int) g0382));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0382);
                    g0408UU= _oid_(CFALSE);
                    bag *g0382_support;
                    g0382_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0388)))));
                    for (START(g0382_support); NEXT(g0382);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0408UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0407I = not_any(g0408UU);
              } 
            
            if (g0407I == CTRUE) (*choco.removeVal)(_oid_(g0378),
                g0381,
                c->idx2);
              } 
          g0381= choco.getNextValue->fcall(((int) g0384),((int) g0381));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(g0381);
        bag *g0381_support;
        g0381_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0384)))));
        for (START(g0381_support); NEXT(g0381);)
        { GC_LOOP;
          { ClaireBoolean * g0409I;
            { OID  g0410UU;
              { IntVar * g0389 = g0377;
                AbstractIntDomain * g0390 = g0389->bucket;
                if (g0390 == (NULL))
                 { int  g0382 = g0389->inf->latestValue;
                  { OID gc_local;
                    g0410UU= _oid_(CFALSE);
                    while ((g0382 <= g0389->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0410UU = Kernel.ctrue;
                        break;} 
                      g0382= ((g0389->inf->latestValue <= (g0382+1)) ?
                        (g0382+1) :
                        g0389->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0390->isa,choco._LinkedListIntDomain))
                 { int  g0382 = g0389->inf->latestValue;
                  { OID gc_local;
                    g0410UU= _oid_(CFALSE);
                    while ((g0382 <= g0389->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0410UU = Kernel.ctrue;
                        break;} 
                      g0382= choco.getNextValue->fcall(((int) g0390),((int) g0382));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0382);
                    g0410UU= _oid_(CFALSE);
                    bag *g0382_support;
                    g0382_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0390)))));
                    for (START(g0382_support); NEXT(g0382);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0379),((int) g0382),((int) g0381))) == _oid_(g0380))
                       { g0410UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0409I = not_any(g0410UU);
              } 
            
            if (g0409I == CTRUE) (*choco.removeVal)(_oid_(g0378),
                g0381,
                c->idx2);
              } 
          GC_UNLOOP;} 
        } 
      } 
  { IntVar * g0391 = c->v1;
    IntVar * g0392 = c->v2;
    BinRelation * g0393 = c->feasRelation;
    ClaireBoolean * g0394 = c->feasiblePair;
    IntVar * g0397 = g0391;
    AbstractIntDomain * g0398 = g0397->bucket;
    if (g0398 == (NULL))
     { int  g0395 = g0397->inf->latestValue;
      { OID gc_local;
        while ((g0395 <= g0397->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0411I;
            { OID  g0412UU;
              { IntVar * g0399 = g0392;
                AbstractIntDomain * g0400 = g0399->bucket;
                if (g0400 == (NULL))
                 { int  g0396 = g0399->inf->latestValue;
                  { OID gc_local;
                    g0412UU= _oid_(CFALSE);
                    while ((g0396 <= g0399->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0412UU = Kernel.ctrue;
                        break;} 
                      g0396= ((g0399->inf->latestValue <= (g0396+1)) ?
                        (g0396+1) :
                        g0399->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0400->isa,choco._LinkedListIntDomain))
                 { int  g0396 = g0399->inf->latestValue;
                  { OID gc_local;
                    g0412UU= _oid_(CFALSE);
                    while ((g0396 <= g0399->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0412UU = Kernel.ctrue;
                        break;} 
                      g0396= choco.getNextValue->fcall(((int) g0400),((int) g0396));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0396);
                    g0412UU= _oid_(CFALSE);
                    bag *g0396_support;
                    g0396_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0400)))));
                    for (START(g0396_support); NEXT(g0396);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0412UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0411I = not_any(g0412UU);
              } 
            
            if (g0411I == CTRUE) (*choco.removeVal)(_oid_(g0391),
                g0395,
                c->idx1);
              } 
          g0395= ((g0397->inf->latestValue <= (g0395+1)) ?
            (g0395+1) :
            g0397->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0398->isa,choco._LinkedListIntDomain))
     { int  g0395 = g0397->inf->latestValue;
      { OID gc_local;
        while ((g0395 <= g0397->sup->latestValue))
        { GC_LOOP;
          { ClaireBoolean * g0413I;
            { OID  g0414UU;
              { IntVar * g0401 = g0392;
                AbstractIntDomain * g0402 = g0401->bucket;
                if (g0402 == (NULL))
                 { int  g0396 = g0401->inf->latestValue;
                  { OID gc_local;
                    g0414UU= _oid_(CFALSE);
                    while ((g0396 <= g0401->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0414UU = Kernel.ctrue;
                        break;} 
                      g0396= ((g0401->inf->latestValue <= (g0396+1)) ?
                        (g0396+1) :
                        g0401->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0402->isa,choco._LinkedListIntDomain))
                 { int  g0396 = g0401->inf->latestValue;
                  { OID gc_local;
                    g0414UU= _oid_(CFALSE);
                    while ((g0396 <= g0401->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0414UU = Kernel.ctrue;
                        break;} 
                      g0396= choco.getNextValue->fcall(((int) g0402),((int) g0396));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0396);
                    g0414UU= _oid_(CFALSE);
                    bag *g0396_support;
                    g0396_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0402)))));
                    for (START(g0396_support); NEXT(g0396);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0414UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0413I = not_any(g0414UU);
              } 
            
            if (g0413I == CTRUE) (*choco.removeVal)(_oid_(g0391),
                g0395,
                c->idx1);
              } 
          g0395= choco.getNextValue->fcall(((int) g0398),((int) g0395));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(g0395);
        bag *g0395_support;
        g0395_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0398)))));
        for (START(g0395_support); NEXT(g0395);)
        { GC_LOOP;
          { ClaireBoolean * g0415I;
            { OID  g0416UU;
              { IntVar * g0403 = g0392;
                AbstractIntDomain * g0404 = g0403->bucket;
                if (g0404 == (NULL))
                 { int  g0396 = g0403->inf->latestValue;
                  { OID gc_local;
                    g0416UU= _oid_(CFALSE);
                    while ((g0396 <= g0403->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0416UU = Kernel.ctrue;
                        break;} 
                      g0396= ((g0403->inf->latestValue <= (g0396+1)) ?
                        (g0396+1) :
                        g0403->inf->latestValue );
                      } 
                    } 
                  } 
                else if (INHERIT(g0404->isa,choco._LinkedListIntDomain))
                 { int  g0396 = g0403->inf->latestValue;
                  { OID gc_local;
                    g0416UU= _oid_(CFALSE);
                    while ((g0396 <= g0403->sup->latestValue))
                    { // HOHO, GC_LOOP not needed !
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0416UU = Kernel.ctrue;
                        break;} 
                      g0396= choco.getNextValue->fcall(((int) g0404),((int) g0396));
                      } 
                    } 
                  } 
                else { OID gc_local;
                    ITERATE(g0396);
                    g0416UU= _oid_(CFALSE);
                    bag *g0396_support;
                    g0396_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0404)))));
                    for (START(g0396_support); NEXT(g0396);)
                    { GC_LOOP;
                      if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) g0393),((int) g0395),((int) g0396))) == _oid_(g0394))
                       { g0416UU = Kernel.ctrue;
                        break;} 
                      GC_UNLOOP;} 
                    } 
                  } 
              g0415I = not_any(g0416UU);
              } 
            
            if (g0415I == CTRUE) (*choco.removeVal)(_oid_(g0391),
                g0395,
                c->idx1);
              } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 1b: Binary constraints propagated by the AC4 algorithm    *
// ********************************************************************
// the AC4 algorithm uses annotation, caching the current number of supports for each value of the domains     
//   a value is removed from this copy only once the constraint did react 
//   to an event removing that value
// internal constructor
//    Note: 0.9907 it is important that after their creation, the support counts of the constraints
//    are meaningful.
//    Indeed, they may be resynchronized with awake (the initial propagation)
//    But some awakeOn... methods may be called before awake.
/* The c++ function for: makeAC4BinConstraint(u:IntVar,v:IntVar,feas:boolean,feasRel:BinRelation) [] */
AC4BinConstraint * choco_makeAC4BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel)
{ GC_RESERVE(14);  // v3.0.55 optim !
  { AC4BinConstraint *Result ;
    { AC4BinConstraint * c;
      { { AC4BinConstraint * _CL_obj = ((AC4BinConstraint *) GC_OBJECT(AC4BinConstraint,new_object_class(choco._AC4BinConstraint)));
          (_CL_obj->v1 = u);
          (_CL_obj->v2 = v);
          (_CL_obj->cste = 0);
          (_CL_obj->feasiblePair = feas);
          (_CL_obj->feasRelation = feasRel);
          c = _CL_obj;
          } 
        GC_OBJECT(AC4BinConstraint,c);} 
      (c->nbSupport1 = choco_makeIntSetIntAnnotation_integer(c->v1->inf->latestValue,c->v1->sup->latestValue,((feas == CTRUE) ?
        0 :
        ((v->sup->latestValue-v->inf->latestValue)+1) )));
      (c->nbSupport2 = choco_makeIntSetIntAnnotation_integer(c->v2->inf->latestValue,c->v2->sup->latestValue,((feas == CTRUE) ?
        0 :
        ((u->sup->latestValue-u->inf->latestValue)+1) )));
      (c->validSupport1 = choco_makeIntSetBoolAnnotation_integer(c->v1->inf->latestValue,c->v1->sup->latestValue,CTRUE));
      (c->validSupport2 = choco_makeIntSetBoolAnnotation_integer(c->v2->inf->latestValue,c->v2->sup->latestValue,CTRUE));
      { int  x = u->inf->latestValue;
        int  g0417 = u->sup->latestValue;
        { OID gc_local;
          while ((x <= g0417))
          { GC_LOOP;
            { int  nbs;
              { int  g0418 = 0;
                { IntVar * g0420 = GC_OBJECT(IntVar,c->v2);
                  AbstractIntDomain * g0421 = GC_OBJECT(AbstractIntDomain,g0420->bucket);
                  if (g0421 == (NULL))
                   { int  g0419 = g0420->inf->latestValue;
                    { OID gc_local;
                      while ((g0419 <= g0420->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0419))) == _oid_(feas))
                         ++g0418;
                        g0419= ((g0420->inf->latestValue <= (g0419+1)) ?
                          (g0419+1) :
                          g0420->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0421->isa,choco._LinkedListIntDomain))
                   { int  g0419 = g0420->inf->latestValue;
                    { OID gc_local;
                      while ((g0419 <= g0420->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0419))) == _oid_(feas))
                         ++g0418;
                        g0419= choco.getNextValue->fcall(((int) g0421),((int) g0419));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0419);
                      bag *g0419_support;
                      g0419_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0421)))));
                      for (START(g0419_support); NEXT(g0419);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0419))) == _oid_(feas))
                         ++g0418;
                        GC_UNLOOP;} 
                      } 
                    } 
                nbs = g0418;
                } 
              STOREI(c->nbSupport1->intValues[(x-c->nbSupport1->offset)],nbs);
              } 
            ++x;
            GC_UNLOOP;} 
          } 
        } 
      { int  y = v->inf->latestValue;
        int  g0422 = v->sup->latestValue;
        { OID gc_local;
          while ((y <= g0422))
          { GC_LOOP;
            { int  nbs;
              { int  g0423 = 0;
                { IntVar * g0425 = GC_OBJECT(IntVar,c->v1);
                  AbstractIntDomain * g0426 = GC_OBJECT(AbstractIntDomain,g0425->bucket);
                  if (g0426 == (NULL))
                   { int  g0424 = g0425->inf->latestValue;
                    { OID gc_local;
                      while ((g0424 <= g0425->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0424),((int) y))) == _oid_(feas))
                         ++g0423;
                        g0424= ((g0425->inf->latestValue <= (g0424+1)) ?
                          (g0424+1) :
                          g0425->inf->latestValue );
                        } 
                      } 
                    } 
                  else if (INHERIT(g0426->isa,choco._LinkedListIntDomain))
                   { int  g0424 = g0425->inf->latestValue;
                    { OID gc_local;
                      while ((g0424 <= g0425->sup->latestValue))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0424),((int) y))) == _oid_(feas))
                         ++g0423;
                        g0424= choco.getNextValue->fcall(((int) g0426),((int) g0424));
                        } 
                      } 
                    } 
                  else { OID gc_local;
                      ITERATE(g0424);
                      bag *g0424_support;
                      g0424_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0426)))));
                      for (START(g0424_support); NEXT(g0424);)
                      { GC_LOOP;
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0424),((int) y))) == _oid_(feas))
                         ++g0423;
                        GC_UNLOOP;} 
                      } 
                    } 
                nbs = g0423;
                } 
              STOREI(c->nbSupport2->intValues[(y-c->nbSupport2->offset)],nbs);
              } 
            ++y;
            GC_UNLOOP;} 
          } 
        } 
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// -- four small utilities --
// decrement the number of values b that support the assignment v1=x
// ie: b such that v1=x, v2=b is a feasible pair
// When this count reaches 0, discard the value x from the domain of v1
/* The c++ function for: decrementNbSupportV1(c:AC4BinConstraint,x:integer) [] */
void  choco_decrementNbSupportV1_AC4BinConstraint(AC4BinConstraint *c,int x)
{ GC_BIND;
  { int  nbs = ((OID *) c->nbSupport1->intValues)[(x-c->nbSupport1->offset)];
    nbs= (nbs-1);
    STOREI(c->nbSupport1->intValues[(x-c->nbSupport1->offset)],nbs);
    if (nbs == 0)
     (*choco.removeVal)(GC_OID(_oid_(c->v1)),
      x,
      c->idx1);
    } 
  GC_UNBIND;} 


// decrement the number of values a that support the assignment v2=y
// ie: a such that v1=a, v2=y is a feasible pair
// When this count reaches 0, discard the value y from the domain of v2
/* The c++ function for: decrementNbSupportV2(c:AC4BinConstraint,y:integer) [] */
void  choco_decrementNbSupportV2_AC4BinConstraint(AC4BinConstraint *c,int y)
{ GC_BIND;
  { int  nbs = ((OID *) c->nbSupport2->intValues)[(y-c->nbSupport2->offset)];
    nbs= (nbs-1);
    STOREI(c->nbSupport2->intValues[(y-c->nbSupport2->offset)],nbs);
    if (nbs == 0)
     (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      y,
      c->idx2);
    } 
  GC_UNBIND;} 


// recompute the number of values b that support the assignment v1=x
// ie: b such that v1=x, v2=b is a feasible pair
// When this count equals 0, discard the value x from the domain of v1
/* The c++ function for: resetNbSupportV1(c:AC4BinConstraint,x:integer) [] */
void  choco_resetNbSupportV1_AC4BinConstraint(AC4BinConstraint *c,int x)
{ GC_BIND;
  { BinRelation * feasRel = c->feasRelation;
    ClaireBoolean * feas = c->feasiblePair;
    int  nbs;
    { int  g0427 = 0;
      { IntVar * g0429 = c->v2;
        AbstractIntDomain * g0430 = g0429->bucket;
        if (g0430 == (NULL))
         { int  g0428 = g0429->inf->latestValue;
          { OID gc_local;
            while ((g0428 <= g0429->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0428))) == _oid_(feas))
               ++g0427;
              g0428= ((g0429->inf->latestValue <= (g0428+1)) ?
                (g0428+1) :
                g0429->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0430->isa,choco._LinkedListIntDomain))
         { int  g0428 = g0429->inf->latestValue;
          { OID gc_local;
            while ((g0428 <= g0429->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0428))) == _oid_(feas))
               ++g0427;
              g0428= choco.getNextValue->fcall(((int) g0430),((int) g0428));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(g0428);
            bag *g0428_support;
            g0428_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0430)))));
            for (START(g0428_support); NEXT(g0428);)
            { GC_LOOP;
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) g0428))) == _oid_(feas))
               ++g0427;
              GC_UNLOOP;} 
            } 
          } 
      nbs = g0427;
      } 
    STOREI(c->nbSupport1->intValues[(x-c->nbSupport1->offset)],nbs);
    if (nbs == 0)
     (*choco.removeVal)(GC_OID(_oid_(c->v1)),
      x,
      c->idx1);
    } 
  GC_UNBIND;} 


// recompute the number of values a that support the assignment v2=y
// ie: a such that v1=a, v2=y is a feasible pair
// When this count equals 0, discard the value y from the domain of v2
/* The c++ function for: resetNbSupportV2(c:AC4BinConstraint,y:integer) [] */
void  choco_resetNbSupportV2_AC4BinConstraint(AC4BinConstraint *c,int y)
{ GC_BIND;
  { BinRelation * feasRel = c->feasRelation;
    ClaireBoolean * feas = c->feasiblePair;
    int  nbs;
    { int  g0431 = 0;
      { IntVar * g0433 = c->v1;
        AbstractIntDomain * g0434 = g0433->bucket;
        if (g0434 == (NULL))
         { int  g0432 = g0433->inf->latestValue;
          { OID gc_local;
            while ((g0432 <= g0433->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0432),((int) y))) == _oid_(feas))
               ++g0431;
              g0432= ((g0433->inf->latestValue <= (g0432+1)) ?
                (g0432+1) :
                g0433->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0434->isa,choco._LinkedListIntDomain))
         { int  g0432 = g0433->inf->latestValue;
          { OID gc_local;
            while ((g0432 <= g0433->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0432),((int) y))) == _oid_(feas))
               ++g0431;
              g0432= choco.getNextValue->fcall(((int) g0434),((int) g0432));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(g0432);
            bag *g0432_support;
            g0432_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0434)))));
            for (START(g0432_support); NEXT(g0432);)
            { GC_LOOP;
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) g0432),((int) y))) == _oid_(feas))
               ++g0431;
              GC_UNLOOP;} 
            } 
          } 
      nbs = g0431;
      } 
    STOREI(c->nbSupport2->intValues[(y-c->nbSupport2->offset)],nbs);
    if (nbs == 0)
     (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      y,
      c->idx2);
    } 
  GC_UNBIND;} 


// -- main propagation methods --
// standard filtering algorithm initializing all support counts
/* The c++ function for: propagate(c:AC4BinConstraint) [] */
void  choco_propagate_AC4BinConstraint(AC4BinConstraint *c)
{ GC_BIND;
  { IntVar * g0435 = GC_OBJECT(IntVar,c->v1);
    AbstractIntDomain * g0436 = GC_OBJECT(AbstractIntDomain,g0435->bucket);
    if (g0436 == (NULL))
     { int  x = g0435->inf->latestValue;
      { OID gc_local;
        while ((x <= g0435->sup->latestValue))
        { // HOHO, GC_LOOP not needed !
          choco_resetNbSupportV1_AC4BinConstraint(c,x);
          x= ((g0435->inf->latestValue <= (x+1)) ?
            (x+1) :
            g0435->inf->latestValue );
          } 
        } 
      } 
    else if (INHERIT(g0436->isa,choco._LinkedListIntDomain))
     { int  x = g0435->inf->latestValue;
      { OID gc_local;
        while ((x <= g0435->sup->latestValue))
        { // HOHO, GC_LOOP not needed !
          choco_resetNbSupportV1_AC4BinConstraint(c,x);
          x= choco.getNextValue->fcall(((int) g0436),((int) x));
          } 
        } 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0436)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          choco_resetNbSupportV1_AC4BinConstraint(c,x);
          GC_UNLOOP;} 
        } 
      } 
  { IntVar * g0437 = GC_OBJECT(IntVar,c->v2);
    AbstractIntDomain * g0438 = GC_OBJECT(AbstractIntDomain,g0437->bucket);
    if (g0438 == (NULL))
     { int  y = g0437->inf->latestValue;
      { OID gc_local;
        while ((y <= g0437->sup->latestValue))
        { // HOHO, GC_LOOP not needed !
          choco_resetNbSupportV2_AC4BinConstraint(c,y);
          y= ((g0437->inf->latestValue <= (y+1)) ?
            (y+1) :
            g0437->inf->latestValue );
          } 
        } 
      } 
    else if (INHERIT(g0438->isa,choco._LinkedListIntDomain))
     { int  y = g0437->inf->latestValue;
      { OID gc_local;
        while ((y <= g0437->sup->latestValue))
        { // HOHO, GC_LOOP not needed !
          choco_resetNbSupportV2_AC4BinConstraint(c,y);
          y= choco.getNextValue->fcall(((int) g0438),((int) y));
          } 
        } 
      } 
    else { OID gc_local;
        ITERATE(y);
        bag *y_support;
        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0438)))));
        for (START(y_support); NEXT(y);)
        { GC_LOOP;
          choco_resetNbSupportV2_AC4BinConstraint(c,y);
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// Maintaining arc consistency with the standard AC4 procedure:
// upon the removal of (v1=a) the number of support of (v2=b) is decremented for all values b
// such that (a,b) is a feasible pair
// v0.9903: introduced c.inversedFeasTest flag
// v0.9907: introduced the c.validSupport1/2 slots
// v1.321: rewrite
/* The c++ function for: awakeOnRem(c:AC4BinConstraint,idx:integer,oldval:integer) [] */
void  choco_awakeOnRem_AC4BinConstraint(AC4BinConstraint *c,int idx,int oldval)
{ GC_BIND;
  { ClaireBoolean * newEvt = CTRUE;
    if (idx == 1)
     { if ((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(oldval-c->validSupport1->offset)])) == CTRUE)
       STOREI(c->validSupport1->boolValues[(oldval-c->validSupport1->offset)],Kernel.cfalse);
      else newEvt= CFALSE;
        } 
    else if ((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(oldval-c->validSupport2->offset)])) == CTRUE)
     STOREI(c->validSupport2->boolValues[(oldval-c->validSupport2->offset)],Kernel.cfalse);
    else newEvt= CFALSE;
      if (newEvt == CTRUE)
     { BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
      ClaireBoolean * feas = c->feasiblePair;
      if (idx == 1)
       { int  min2 = choco_getOriginalMin_IntSetAnnotation(c->validSupport2);
        int  max2 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport2));
        int  oldx = oldval;
        int  y = min2;
        int  g0439 = max2;
        { OID gc_local;
          while ((y <= g0439))
          { // HOHO, GC_LOOP not needed !
            if (((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(y-c->validSupport2->offset)])) == CTRUE) && 
                (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) oldx),((int) y))) == _oid_(feas)))
             choco_decrementNbSupportV2_AC4BinConstraint(c,y);
            ++y;
            } 
          } 
        } 
      else { int  min1 = choco_getOriginalMin_IntSetAnnotation(c->validSupport1);
          int  max1 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport1));
          int  oldy = oldval;
          int  x = min1;
          int  g0440 = max1;
          { OID gc_local;
            while ((x <= g0440))
            { // HOHO, GC_LOOP not needed !
              if (((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(x-c->validSupport1->offset)])) == CTRUE) && 
                  (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) oldy))) == _oid_(feas)))
               choco_decrementNbSupportV1_AC4BinConstraint(c,x);
              ++x;
              } 
            } 
          } 
        } 
    } 
  GC_UNBIND;} 


// forward checking propagation
/* The c++ function for: awakeOnInst(c:AC4BinConstraint,idx:integer) [] */
void  choco_awakeOnInst_AC4BinConstraint(AC4BinConstraint *c,int idx)
{ GC_BIND;
  { IntVar * var1 = GC_OBJECT(IntVar,c->v1);
    IntVar * var2 = GC_OBJECT(IntVar,c->v2);
    int  val = ((idx == 1) ?
      var1->value :
      var2->value );
    int  min1 = choco_getOriginalMin_IntSetAnnotation(c->validSupport1);
    int  max1 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport1));
    int  min2 = choco_getOriginalMin_IntSetAnnotation(c->validSupport2);
    int  max2 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport2));
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    if (idx == 1)
     { { int  x = min1;
        int  g0441 = max1;
        { OID gc_local;
          while ((x <= g0441))
          { // HOHO, GC_LOOP not needed !
            if (x != val)
             STOREI(c->validSupport1->boolValues[(x-c->validSupport1->offset)],Kernel.cfalse);
            ++x;
            } 
          } 
        } 
      { int  y = min2;
        int  g0442 = max2;
        { OID gc_local;
          while ((y <= g0442))
          { // HOHO, GC_LOOP not needed !
            if (((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(y-c->validSupport2->offset)])) == CTRUE) && 
                (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas)))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            ++y;
            } 
          } 
        } 
      } 
    else { { int  y = min2;
          int  g0443 = max2;
          { OID gc_local;
            while ((y <= g0443))
            { // HOHO, GC_LOOP not needed !
              if (y != val)
               STOREI(c->validSupport2->boolValues[(y-c->validSupport2->offset)],Kernel.cfalse);
              ++y;
              } 
            } 
          } 
        { int  x = min1;
          int  g0444 = max1;
          { OID gc_local;
            while ((x <= g0444))
            { // HOHO, GC_LOOP not needed !
              if (((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(x-c->validSupport1->offset)])) == CTRUE) && 
                  (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas)))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              ++x;
              } 
            } 
          } 
        } 
      choco_setPassive_AbstractConstraint(c);
    } 
  GC_UNBIND;} 


// Note: these methods could be improved by considering for each value, the minimal and maximal support considered into the count
/* The c++ function for: awakeOnInf(c:AC4BinConstraint,idx:integer) [] */
void  choco_awakeOnInf_AC4BinConstraint(AC4BinConstraint *c,int idx)
{ GC_BIND;
  { int  vinf = ((idx == 1) ?
      (c->v1->inf->latestValue-1) :
      (c->v2->inf->latestValue-1) );
    int  min1 = choco_getOriginalMin_IntSetAnnotation(c->validSupport1);
    int  max1 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport1));
    int  min2 = choco_getOriginalMin_IntSetAnnotation(c->validSupport2);
    int  max2 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport2));
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    if (idx == 1)
     { { int  x = min1;
        int  g0445 = vinf;
        { OID gc_local;
          while ((x <= g0445))
          { // HOHO, GC_LOOP not needed !
            STOREI(c->validSupport1->boolValues[(x-c->validSupport1->offset)],Kernel.cfalse);
            ++x;
            } 
          } 
        } 
      { int  y = min2;
        int  g0446 = max2;
        { OID gc_local;
          while ((y <= g0446))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(y-c->validSupport2->offset)])) == CTRUE)
             { { ClaireBoolean * g0451I;
                { OID  g0452UU;
                  { int  x = min1;
                    int  g0447 = vinf;
                    { OID gc_local;
                      g0452UU= _oid_(CFALSE);
                      while ((x <= g0447))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                         { g0452UU = Kernel.ctrue;
                          break;} 
                        ++x;
                        } 
                      } 
                    } 
                  g0451I = boolean_I_any(g0452UU);
                  } 
                
                if (g0451I == CTRUE) choco_resetNbSupportV2_AC4BinConstraint(c,y);
                  } 
              } 
            ++y;
            } 
          } 
        } 
      } 
    else { { int  y = min2;
          int  g0448 = vinf;
          { OID gc_local;
            while ((y <= g0448))
            { // HOHO, GC_LOOP not needed !
              STOREI(c->validSupport2->boolValues[(y-c->validSupport2->offset)],Kernel.cfalse);
              ++y;
              } 
            } 
          } 
        { int  x = min1;
          int  g0449 = max1;
          { OID gc_local;
            while ((x <= g0449))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(x-c->validSupport1->offset)])) == CTRUE)
               { { ClaireBoolean * g0453I;
                  { OID  g0454UU;
                    { int  y = min2;
                      int  g0450 = vinf;
                      { OID gc_local;
                        g0454UU= _oid_(CFALSE);
                        while ((y <= g0450))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { g0454UU = Kernel.ctrue;
                            break;} 
                          ++y;
                          } 
                        } 
                      } 
                    g0453I = boolean_I_any(g0454UU);
                    } 
                  
                  if (g0453I == CTRUE) choco_resetNbSupportV1_AC4BinConstraint(c,x);
                    } 
                } 
              ++x;
              } 
            } 
          } 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:AC4BinConstraint,idx:integer) [] */
void  choco_awakeOnSup_AC4BinConstraint(AC4BinConstraint *c,int idx)
{ GC_BIND;
  { int  vsup = ((idx == 1) ?
      (c->v1->sup->latestValue+1) :
      (c->v2->sup->latestValue+1) );
    int  min1 = choco_getOriginalMin_IntSetAnnotation(c->validSupport1);
    int  max1 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport1));
    int  min2 = choco_getOriginalMin_IntSetAnnotation(c->validSupport2);
    int  max2 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport2));
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    if (idx == 1)
     { { int  x = vsup;
        int  g0455 = max1;
        { OID gc_local;
          while ((x <= g0455))
          { // HOHO, GC_LOOP not needed !
            STOREI(c->validSupport1->boolValues[(x-c->validSupport1->offset)],Kernel.cfalse);
            ++x;
            } 
          } 
        } 
      { int  y = min2;
        int  g0456 = max2;
        { OID gc_local;
          while ((y <= g0456))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(y-c->validSupport2->offset)])) == CTRUE)
             { { ClaireBoolean * g0461I;
                { OID  g0462UU;
                  { int  x = vsup;
                    int  g0457 = max1;
                    { OID gc_local;
                      g0462UU= _oid_(CFALSE);
                      while ((x <= g0457))
                      { // HOHO, GC_LOOP not needed !
                        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                         { g0462UU = Kernel.ctrue;
                          break;} 
                        ++x;
                        } 
                      } 
                    } 
                  g0461I = boolean_I_any(g0462UU);
                  } 
                
                if (g0461I == CTRUE) choco_resetNbSupportV2_AC4BinConstraint(c,y);
                  } 
              } 
            ++y;
            } 
          } 
        } 
      } 
    else { { int  y = vsup;
          int  g0458 = max2;
          { OID gc_local;
            while ((y <= g0458))
            { // HOHO, GC_LOOP not needed !
              STOREI(c->validSupport2->boolValues[(y-c->validSupport2->offset)],Kernel.cfalse);
              ++y;
              } 
            } 
          } 
        { int  x = min1;
          int  g0459 = max1;
          { OID gc_local;
            while ((x <= g0459))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(x-c->validSupport1->offset)])) == CTRUE)
               { { ClaireBoolean * g0463I;
                  { OID  g0464UU;
                    { int  y = vsup;
                      int  g0460 = max2;
                      { OID gc_local;
                        g0464UU= _oid_(CFALSE);
                        while ((y <= g0460))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { g0464UU = Kernel.ctrue;
                            break;} 
                          ++y;
                          } 
                        } 
                      } 
                    g0463I = boolean_I_any(g0464UU);
                    } 
                  
                  if (g0463I == CTRUE) choco_resetNbSupportV1_AC4BinConstraint(c,x);
                    } 
                } 
              ++x;
              } 
            } 
          } 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnVar(c:AC4BinConstraint,idx:integer) [] */
void  choco_awakeOnVar_AC4BinConstraint(AC4BinConstraint *c,int idx)
{ GC_BIND;
  { int  vinf = ((idx == 1) ?
      (c->v1->inf->latestValue-1) :
      (c->v2->inf->latestValue-1) );
    int  min1 = choco_getOriginalMin_IntSetAnnotation(c->validSupport1);
    int  max1 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport1));
    int  min2 = choco_getOriginalMin_IntSetAnnotation(c->validSupport2);
    int  max2 = choco_getOriginalMax_IntSetAnnotation(GC_OBJECT(IntSetBoolAnnotation,c->validSupport2));
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    if (idx == 1)
     { { int  x = min1;
        int  g0465 = max1;
        { OID gc_local;
          while ((x <= g0465))
          { GC_LOOP;
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              x)) == CTRUE)
             STOREI(c->validSupport1->boolValues[(x-c->validSupport1->offset)],Kernel.cfalse);
            ++x;
            GC_UNLOOP;} 
          } 
        } 
      { int  y = min2;
        int  g0466 = max2;
        { OID gc_local;
          while ((y <= g0466))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,((OID *) c->validSupport2->boolValues)[(y-c->validSupport2->offset)])) == CTRUE)
             choco_resetNbSupportV2_AC4BinConstraint(c,y);
            ++y;
            } 
          } 
        } 
      } 
    else { { int  y = min2;
          int  g0467 = max2;
          { OID gc_local;
            while ((y <= g0467))
            { GC_LOOP;
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                y)) == CTRUE)
               STOREI(c->validSupport2->boolValues[(y-c->validSupport2->offset)],Kernel.cfalse);
              ++y;
              GC_UNLOOP;} 
            } 
          } 
        { int  x = min1;
          int  g0468 = max1;
          { OID gc_local;
            while ((x <= g0468))
            { // HOHO, GC_LOOP not needed !
              if ((OBJECT(ClaireBoolean,((OID *) c->validSupport1->boolValues)[(x-c->validSupport1->offset)])) == CTRUE)
               choco_resetNbSupportV1_AC4BinConstraint(c,x);
              ++x;
              } 
            } 
          } 
        } 
      } 
  GC_UNBIND;} 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 1c: Binary constraints propagated by the AC2001 algorithm *
// ********************************************************************
// the AC2001 algorithm uses annotation, caching a current support for each value of the domains     
// current support for each value of v2 (each entry is a value for v1)
// internal constructor
/* The c++ function for: makeAC2001BinConstraint(u:IntVar,v:IntVar,feas:boolean,feasRel:BinRelation) [] */
AC2001BinConstraint * choco_makeAC2001BinConstraint_IntVar(IntVar *u,IntVar *v,ClaireBoolean *feas,BinRelation *feasRel)
{ GC_BIND;
  { AC2001BinConstraint *Result ;
    { AC2001BinConstraint * c;
      { { AC2001BinConstraint * _CL_obj = ((AC2001BinConstraint *) GC_OBJECT(AC2001BinConstraint,new_object_class(choco._AC2001BinConstraint)));
          (_CL_obj->v1 = u);
          (_CL_obj->v2 = v);
          (_CL_obj->cste = 0);
          (_CL_obj->feasiblePair = feas);
          (_CL_obj->feasRelation = feasRel);
          c = _CL_obj;
          } 
        GC_OBJECT(AC2001BinConstraint,c);} 
      (c->currentSupport1 = choco_makeIntSetIntAnnotation_integer(c->v1->inf->latestValue,c->v1->sup->latestValue,-99999));
      (c->currentSupport2 = choco_makeIntSetIntAnnotation_integer(c->v2->inf->latestValue,c->v2->sup->latestValue,-99999));
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


// updates the support for all values in the domain of v2, and remove unsupported values for v2
/* The c++ function for: reviseV2(c:AC2001BinConstraint) [] */
void  choco_reviseV2_AC2001BinConstraint(AC2001BinConstraint *c)
{ GC_BIND;
  { IntVar * g0469 = GC_OBJECT(IntVar,c->v2);
    AbstractIntDomain * g0470 = GC_OBJECT(AbstractIntDomain,g0469->bucket);
    if (g0470 == (NULL))
     { int  y = g0469->inf->latestValue;
      { OID gc_local;
        while ((y <= g0469->sup->latestValue))
        { GC_LOOP;
          { int  x0 = ((OID *) c->currentSupport2->intValues)[(y-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              x0)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,y);
            } 
          y= ((g0469->inf->latestValue <= (y+1)) ?
            (y+1) :
            g0469->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0470->isa,choco._LinkedListIntDomain))
     { int  y = g0469->inf->latestValue;
      { OID gc_local;
        while ((y <= g0469->sup->latestValue))
        { GC_LOOP;
          { int  x0 = ((OID *) c->currentSupport2->intValues)[(y-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              x0)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,y);
            } 
          y= choco.getNextValue->fcall(((int) g0470),((int) y));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(y);
        bag *y_support;
        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0470)))));
        for (START(y_support); NEXT(y);)
        { GC_LOOP;
          { int  x0 = ((OID *) c->currentSupport2->intValues)[(y-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              x0)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,y);
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// updates the support for all values in the domain of v1, and remove unsupported values for v1
/* The c++ function for: reviseV1(c:AC2001BinConstraint) [] */
void  choco_reviseV1_AC2001BinConstraint(AC2001BinConstraint *c)
{ GC_BIND;
  { IntVar * g0471 = GC_OBJECT(IntVar,c->v1);
    AbstractIntDomain * g0472 = GC_OBJECT(AbstractIntDomain,g0471->bucket);
    if (g0472 == (NULL))
     { int  x = g0471->inf->latestValue;
      { OID gc_local;
        while ((x <= g0471->sup->latestValue))
        { GC_LOOP;
          { int  y0 = ((OID *) c->currentSupport1->intValues)[(x-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              y0)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,x);
            } 
          x= ((g0471->inf->latestValue <= (x+1)) ?
            (x+1) :
            g0471->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0472->isa,choco._LinkedListIntDomain))
     { int  x = g0471->inf->latestValue;
      { OID gc_local;
        while ((x <= g0471->sup->latestValue))
        { GC_LOOP;
          { int  y0 = ((OID *) c->currentSupport1->intValues)[(x-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              y0)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,x);
            } 
          x= choco.getNextValue->fcall(((int) g0472),((int) x));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(x);
        bag *x_support;
        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0472)))));
        for (START(x_support); NEXT(x);)
        { GC_LOOP;
          { int  y0 = ((OID *) c->currentSupport1->intValues)[(x-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              y0)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,x);
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// the smart trick of AC2001:
//  the current support for value x of v1 is no longer valid:
//  look for a new one, without restarting the iteration form scratch
/* The c++ function for: updateSupportVal1(c:AC2001BinConstraint,x:integer) [] */
void  choco_updateSupportVal1_AC2001BinConstraint(AC2001BinConstraint *c,int x)
{ GC_BIND;
  { IntVar * var2 = GC_OBJECT(IntVar,c->v2);
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    int  max2 = choco.getSup->fcall(((int) var2));
    int  y = ((OID *) c->currentSupport1->intValues)[(x-c->currentSupport1->offset)];
    ClaireBoolean * found = CFALSE;
    { while (((found != CTRUE) && 
          (y < max2)))
      { y= choco_getNextDomainValue_IntVar(var2,y);
        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
         found= CTRUE;
        } 
      } 
    if (found == CTRUE)
     STOREI(c->currentSupport1->intValues[(x-c->currentSupport1->offset)],y);
    else (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        x,
        c->idx1);
      } 
  GC_UNBIND;} 


//  the current support for value y of v2 is no longer valid:
//  look for a new one, without restarting the iteration form scratch
/* The c++ function for: updateSupportVal2(c:AC2001BinConstraint,y:integer) [] */
void  choco_updateSupportVal2_AC2001BinConstraint(AC2001BinConstraint *c,int y)
{ GC_BIND;
  { IntVar * var1 = GC_OBJECT(IntVar,c->v1);
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    int  max1 = choco.getSup->fcall(((int) var1));
    int  x = ((OID *) c->currentSupport2->intValues)[(y-c->currentSupport2->offset)];
    ClaireBoolean * found = CFALSE;
    { while (((found != CTRUE) && 
          (x < max1)))
      { x= choco_getNextDomainValue_IntVar(var1,x);
        if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
         found= CTRUE;
        } 
      } 
    if (found == CTRUE)
     STOREI(c->currentSupport2->intValues[(y-c->currentSupport2->offset)],x);
    else (*choco.removeVal)(GC_OID(_oid_(c->v2)),
        y,
        c->idx2);
      } 
  GC_UNBIND;} 


/* The c++ function for: awakeOnInf(c:AC2001BinConstraint,idx:integer) [] */
void  choco_awakeOnInf_AC2001BinConstraint(AC2001BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0475 = GC_OBJECT(IntVar,c->v2);
      AbstractIntDomain * g0476 = GC_OBJECT(AbstractIntDomain,g0475->bucket);
      if (g0476 == (NULL))
       { int  g0473 = g0475->inf->latestValue;
        { OID gc_local;
          while ((g0473 <= g0475->sup->latestValue))
          { GC_LOOP;
            { int  g0474 = ((OID *) c->currentSupport2->intValues)[(g0473-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0474)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0473);
              } 
            g0473= ((g0475->inf->latestValue <= (g0473+1)) ?
              (g0473+1) :
              g0475->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0476->isa,choco._LinkedListIntDomain))
       { int  g0473 = g0475->inf->latestValue;
        { OID gc_local;
          while ((g0473 <= g0475->sup->latestValue))
          { GC_LOOP;
            { int  g0474 = ((OID *) c->currentSupport2->intValues)[(g0473-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0474)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0473);
              } 
            g0473= choco.getNextValue->fcall(((int) g0476),((int) g0473));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0473);
          bag *g0473_support;
          g0473_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0476)))));
          for (START(g0473_support); NEXT(g0473);)
          { GC_LOOP;
            { int  g0474 = ((OID *) c->currentSupport2->intValues)[(g0473-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0474)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0473);
              } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_BIND;
    { IntVar * g0479 = GC_OBJECT(IntVar,c->v1);
      AbstractIntDomain * g0480 = GC_OBJECT(AbstractIntDomain,g0479->bucket);
      if (g0480 == (NULL))
       { int  g0477 = g0479->inf->latestValue;
        { OID gc_local;
          while ((g0477 <= g0479->sup->latestValue))
          { GC_LOOP;
            { int  g0478 = ((OID *) c->currentSupport1->intValues)[(g0477-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0478)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0477);
              } 
            g0477= ((g0479->inf->latestValue <= (g0477+1)) ?
              (g0477+1) :
              g0479->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0480->isa,choco._LinkedListIntDomain))
       { int  g0477 = g0479->inf->latestValue;
        { OID gc_local;
          while ((g0477 <= g0479->sup->latestValue))
          { GC_LOOP;
            { int  g0478 = ((OID *) c->currentSupport1->intValues)[(g0477-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0478)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0477);
              } 
            g0477= choco.getNextValue->fcall(((int) g0480),((int) g0477));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0477);
          bag *g0477_support;
          g0477_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0480)))));
          for (START(g0477_support); NEXT(g0477);)
          { GC_LOOP;
            { int  g0478 = ((OID *) c->currentSupport1->intValues)[(g0477-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0478)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0477);
              } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnSup(c:AC2001BinConstraint,idx:integer) [] */
void  choco_awakeOnSup_AC2001BinConstraint(AC2001BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0483 = GC_OBJECT(IntVar,c->v2);
      AbstractIntDomain * g0484 = GC_OBJECT(AbstractIntDomain,g0483->bucket);
      if (g0484 == (NULL))
       { int  g0481 = g0483->inf->latestValue;
        { OID gc_local;
          while ((g0481 <= g0483->sup->latestValue))
          { GC_LOOP;
            { int  g0482 = ((OID *) c->currentSupport2->intValues)[(g0481-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0482)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0481);
              } 
            g0481= ((g0483->inf->latestValue <= (g0481+1)) ?
              (g0481+1) :
              g0483->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0484->isa,choco._LinkedListIntDomain))
       { int  g0481 = g0483->inf->latestValue;
        { OID gc_local;
          while ((g0481 <= g0483->sup->latestValue))
          { GC_LOOP;
            { int  g0482 = ((OID *) c->currentSupport2->intValues)[(g0481-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0482)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0481);
              } 
            g0481= choco.getNextValue->fcall(((int) g0484),((int) g0481));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0481);
          bag *g0481_support;
          g0481_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0484)))));
          for (START(g0481_support); NEXT(g0481);)
          { GC_LOOP;
            { int  g0482 = ((OID *) c->currentSupport2->intValues)[(g0481-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0482)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0481);
              } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_BIND;
    { IntVar * g0487 = GC_OBJECT(IntVar,c->v1);
      AbstractIntDomain * g0488 = GC_OBJECT(AbstractIntDomain,g0487->bucket);
      if (g0488 == (NULL))
       { int  g0485 = g0487->inf->latestValue;
        { OID gc_local;
          while ((g0485 <= g0487->sup->latestValue))
          { GC_LOOP;
            { int  g0486 = ((OID *) c->currentSupport1->intValues)[(g0485-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0486)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0485);
              } 
            g0485= ((g0487->inf->latestValue <= (g0485+1)) ?
              (g0485+1) :
              g0487->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0488->isa,choco._LinkedListIntDomain))
       { int  g0485 = g0487->inf->latestValue;
        { OID gc_local;
          while ((g0485 <= g0487->sup->latestValue))
          { GC_LOOP;
            { int  g0486 = ((OID *) c->currentSupport1->intValues)[(g0485-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0486)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0485);
              } 
            g0485= choco.getNextValue->fcall(((int) g0488),((int) g0485));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0485);
          bag *g0485_support;
          g0485_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0488)))));
          for (START(g0485_support); NEXT(g0485);)
          { GC_LOOP;
            { int  g0486 = ((OID *) c->currentSupport1->intValues)[(g0485-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0486)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0485);
              } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


// forward checking propagation
/* The c++ function for: awakeOnInst(c:AC2001BinConstraint,idx:integer) [] */
void  choco_awakeOnInst_AC2001BinConstraint(AC2001BinConstraint *c,int idx)
{ GC_BIND;
  { IntVar * var1 = GC_OBJECT(IntVar,c->v1);
    IntVar * var2 = GC_OBJECT(IntVar,c->v2);
    BinRelation * feasRel = GC_OBJECT(BinRelation,c->feasRelation);
    ClaireBoolean * feas = c->feasiblePair;
    int  val = ((idx == 1) ?
      var1->value :
      var2->value );
    if (idx == 1)
     { IntVar * g0489 = var2;
      AbstractIntDomain * g0490 = GC_OBJECT(AbstractIntDomain,g0489->bucket);
      if (g0490 == (NULL))
       { int  y = g0489->inf->latestValue;
        { OID gc_local;
          while ((y <= g0489->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            y= ((g0489->inf->latestValue <= (y+1)) ?
              (y+1) :
              g0489->inf->latestValue );
            } 
          } 
        } 
      else if (INHERIT(g0490->isa,choco._LinkedListIntDomain))
       { int  y = g0489->inf->latestValue;
        { OID gc_local;
          while ((y <= g0489->sup->latestValue))
          { // HOHO, GC_LOOP not needed !
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            y= choco.getNextValue->fcall(((int) g0490),((int) y));
            } 
          } 
        } 
      else { OID gc_local;
          ITERATE(y);
          bag *y_support;
          y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0490)))));
          for (START(y_support); NEXT(y);)
          { GC_LOOP;
            if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) val),((int) y))) != _oid_(feas))
             (*choco.removeVal)(_oid_(var2),
              y,
              c->idx2);
            GC_UNLOOP;} 
          } 
        } 
    else { IntVar * g0491 = var1;
        AbstractIntDomain * g0492 = GC_OBJECT(AbstractIntDomain,g0491->bucket);
        if (g0492 == (NULL))
         { int  x = g0491->inf->latestValue;
          { OID gc_local;
            while ((x <= g0491->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              x= ((g0491->inf->latestValue <= (x+1)) ?
                (x+1) :
                g0491->inf->latestValue );
              } 
            } 
          } 
        else if (INHERIT(g0492->isa,choco._LinkedListIntDomain))
         { int  x = g0491->inf->latestValue;
          { OID gc_local;
            while ((x <= g0491->sup->latestValue))
            { // HOHO, GC_LOOP not needed !
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              x= choco.getNextValue->fcall(((int) g0492),((int) x));
              } 
            } 
          } 
        else { OID gc_local;
            ITERATE(x);
            bag *x_support;
            x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0492)))));
            for (START(x_support); NEXT(x);)
            { GC_LOOP;
              if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) val))) != _oid_(feas))
               (*choco.removeVal)(_oid_(var1),
                x,
                c->idx1);
              GC_UNLOOP;} 
            } 
          } 
      choco_setPassive_AbstractConstraint(c);
    } 
  GC_UNBIND;} 


// Maintaining arc consistency
/* The c++ function for: awakeOnRem(c:AC2001BinConstraint,idx:integer,oldval:integer) [] */
void  choco_awakeOnRem_AC2001BinConstraint(AC2001BinConstraint *c,int idx,int oldval)
{ if (idx == 1) 
  { { IntVar * g0495 = GC_OBJECT(IntVar,c->v2);
      AbstractIntDomain * g0496 = GC_OBJECT(AbstractIntDomain,g0495->bucket);
      if (g0496 == (NULL))
       { int  g0493 = g0495->inf->latestValue;
        { OID gc_local;
          while ((g0493 <= g0495->sup->latestValue))
          { GC_LOOP;
            { int  g0494 = ((OID *) c->currentSupport2->intValues)[(g0493-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0494)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0493);
              } 
            g0493= ((g0495->inf->latestValue <= (g0493+1)) ?
              (g0493+1) :
              g0495->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0496->isa,choco._LinkedListIntDomain))
       { int  g0493 = g0495->inf->latestValue;
        { OID gc_local;
          while ((g0493 <= g0495->sup->latestValue))
          { GC_LOOP;
            { int  g0494 = ((OID *) c->currentSupport2->intValues)[(g0493-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0494)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0493);
              } 
            g0493= choco.getNextValue->fcall(((int) g0496),((int) g0493));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0493);
          bag *g0493_support;
          g0493_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0496)))));
          for (START(g0493_support); NEXT(g0493);)
          { GC_LOOP;
            { int  g0494 = ((OID *) c->currentSupport2->intValues)[(g0493-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0494)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0493);
              } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_BIND;
    { IntVar * g0499 = GC_OBJECT(IntVar,c->v1);
      AbstractIntDomain * g0500 = GC_OBJECT(AbstractIntDomain,g0499->bucket);
      if (g0500 == (NULL))
       { int  g0497 = g0499->inf->latestValue;
        { OID gc_local;
          while ((g0497 <= g0499->sup->latestValue))
          { GC_LOOP;
            { int  g0498 = ((OID *) c->currentSupport1->intValues)[(g0497-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0498)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0497);
              } 
            g0497= ((g0499->inf->latestValue <= (g0497+1)) ?
              (g0497+1) :
              g0499->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0500->isa,choco._LinkedListIntDomain))
       { int  g0497 = g0499->inf->latestValue;
        { OID gc_local;
          while ((g0497 <= g0499->sup->latestValue))
          { GC_LOOP;
            { int  g0498 = ((OID *) c->currentSupport1->intValues)[(g0497-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0498)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0497);
              } 
            g0497= choco.getNextValue->fcall(((int) g0500),((int) g0497));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0497);
          bag *g0497_support;
          g0497_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0500)))));
          for (START(g0497_support); NEXT(g0497);)
          { GC_LOOP;
            { int  g0498 = ((OID *) c->currentSupport1->intValues)[(g0497-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0498)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0497);
              } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


// v1.322
/* The c++ function for: awakeOnVar(c:AC2001BinConstraint,idx:integer) [] */
void  choco_awakeOnVar_AC2001BinConstraint(AC2001BinConstraint *c,int idx)
{ if (idx == 1) 
  { { IntVar * g0503 = GC_OBJECT(IntVar,c->v2);
      AbstractIntDomain * g0504 = GC_OBJECT(AbstractIntDomain,g0503->bucket);
      if (g0504 == (NULL))
       { int  g0501 = g0503->inf->latestValue;
        { OID gc_local;
          while ((g0501 <= g0503->sup->latestValue))
          { GC_LOOP;
            { int  g0502 = ((OID *) c->currentSupport2->intValues)[(g0501-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0502)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0501);
              } 
            g0501= ((g0503->inf->latestValue <= (g0501+1)) ?
              (g0501+1) :
              g0503->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0504->isa,choco._LinkedListIntDomain))
       { int  g0501 = g0503->inf->latestValue;
        { OID gc_local;
          while ((g0501 <= g0503->sup->latestValue))
          { GC_LOOP;
            { int  g0502 = ((OID *) c->currentSupport2->intValues)[(g0501-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0502)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0501);
              } 
            g0501= choco.getNextValue->fcall(((int) g0504),((int) g0501));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0501);
          bag *g0501_support;
          g0501_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0504)))));
          for (START(g0501_support); NEXT(g0501);)
          { GC_LOOP;
            { int  g0502 = ((OID *) c->currentSupport2->intValues)[(g0501-c->currentSupport2->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
                g0502)) == CTRUE)
               choco_updateSupportVal2_AC2001BinConstraint(c,g0501);
              } 
            GC_UNLOOP;} 
          } 
        } 
     } 
  else{ GC_BIND;
    { IntVar * g0507 = GC_OBJECT(IntVar,c->v1);
      AbstractIntDomain * g0508 = GC_OBJECT(AbstractIntDomain,g0507->bucket);
      if (g0508 == (NULL))
       { int  g0505 = g0507->inf->latestValue;
        { OID gc_local;
          while ((g0505 <= g0507->sup->latestValue))
          { GC_LOOP;
            { int  g0506 = ((OID *) c->currentSupport1->intValues)[(g0505-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0506)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0505);
              } 
            g0505= ((g0507->inf->latestValue <= (g0505+1)) ?
              (g0505+1) :
              g0507->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0508->isa,choco._LinkedListIntDomain))
       { int  g0505 = g0507->inf->latestValue;
        { OID gc_local;
          while ((g0505 <= g0507->sup->latestValue))
          { GC_LOOP;
            { int  g0506 = ((OID *) c->currentSupport1->intValues)[(g0505-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0506)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0505);
              } 
            g0505= choco.getNextValue->fcall(((int) g0508),((int) g0505));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(g0505);
          bag *g0505_support;
          g0505_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0508)))));
          for (START(g0505_support); NEXT(g0505);)
          { GC_LOOP;
            { int  g0506 = ((OID *) c->currentSupport1->intValues)[(g0505-c->currentSupport1->offset)];
              if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
                g0506)) == CTRUE)
               choco_updateSupportVal1_AC2001BinConstraint(c,g0505);
              } 
            GC_UNLOOP;} 
          } 
        } 
    GC_UNBIND;} 
  } 


// propagation: incrementally maintain the supports that are no longer valid
/* The c++ function for: propagate(c:AC2001BinConstraint) [] */
void  choco_propagate_AC2001BinConstraint(AC2001BinConstraint *c)
{ GC_BIND;
  { IntVar * g0511 = GC_OBJECT(IntVar,c->v2);
    AbstractIntDomain * g0512 = GC_OBJECT(AbstractIntDomain,g0511->bucket);
    if (g0512 == (NULL))
     { int  g0509 = g0511->inf->latestValue;
      { OID gc_local;
        while ((g0509 <= g0511->sup->latestValue))
        { GC_LOOP;
          { int  g0510 = ((OID *) c->currentSupport2->intValues)[(g0509-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              g0510)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,g0509);
            } 
          g0509= ((g0511->inf->latestValue <= (g0509+1)) ?
            (g0509+1) :
            g0511->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0512->isa,choco._LinkedListIntDomain))
     { int  g0509 = g0511->inf->latestValue;
      { OID gc_local;
        while ((g0509 <= g0511->sup->latestValue))
        { GC_LOOP;
          { int  g0510 = ((OID *) c->currentSupport2->intValues)[(g0509-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              g0510)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,g0509);
            } 
          g0509= choco.getNextValue->fcall(((int) g0512),((int) g0509));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(g0509);
        bag *g0509_support;
        g0509_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0512)))));
        for (START(g0509_support); NEXT(g0509);)
        { GC_LOOP;
          { int  g0510 = ((OID *) c->currentSupport2->intValues)[(g0509-c->currentSupport2->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
              g0510)) == CTRUE)
             choco_updateSupportVal2_AC2001BinConstraint(c,g0509);
            } 
          GC_UNLOOP;} 
        } 
      } 
  { IntVar * g0515 = GC_OBJECT(IntVar,c->v1);
    AbstractIntDomain * g0516 = GC_OBJECT(AbstractIntDomain,g0515->bucket);
    if (g0516 == (NULL))
     { int  g0513 = g0515->inf->latestValue;
      { OID gc_local;
        while ((g0513 <= g0515->sup->latestValue))
        { GC_LOOP;
          { int  g0514 = ((OID *) c->currentSupport1->intValues)[(g0513-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              g0514)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,g0513);
            } 
          g0513= ((g0515->inf->latestValue <= (g0513+1)) ?
            (g0513+1) :
            g0515->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0516->isa,choco._LinkedListIntDomain))
     { int  g0513 = g0515->inf->latestValue;
      { OID gc_local;
        while ((g0513 <= g0515->sup->latestValue))
        { GC_LOOP;
          { int  g0514 = ((OID *) c->currentSupport1->intValues)[(g0513-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              g0514)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,g0513);
            } 
          g0513= choco.getNextValue->fcall(((int) g0516),((int) g0513));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(g0513);
        bag *g0513_support;
        g0513_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0516)))));
        for (START(g0513_support); NEXT(g0513);)
        { GC_LOOP;
          { int  g0514 = ((OID *) c->currentSupport1->intValues)[(g0513-c->currentSupport1->offset)];
            if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
              g0514)) == CTRUE)
             choco_updateSupportVal1_AC2001BinConstraint(c,g0513);
            } 
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


// initialization: initialize supports
/* The c++ function for: awake(c:AC2001BinConstraint) [] */
void  choco_awake_AC2001BinConstraint_choco(AC2001BinConstraint *c)
{ GC_RESERVE(1);  // HOHO v3.0.55 optim !
  { IntVar * var1 = c->v1;
    IntVar * var2 = c->v2;
    BinRelation * feasRel = c->feasRelation;
    ClaireBoolean * feas = c->feasiblePair;
    { IntVar * g0517 = var1;
      AbstractIntDomain * g0518 = g0517->bucket;
      if (g0518 == (NULL))
       { int  x = g0517->inf->latestValue;
        { OID gc_local;
          while ((x <= g0517->sup->latestValue))
          { GC_LOOP;
            { OID  y0test;
              { { OID  y_some = CNULL;
                  { IntVar * g0519 = var2;
                    AbstractIntDomain * g0520 = g0519->bucket;
                    if (g0520 == (NULL))
                     { int  y = g0519->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0519->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= ((g0519->inf->latestValue <= (y+1)) ?
                            (y+1) :
                            g0519->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0520->isa,choco._LinkedListIntDomain))
                     { int  y = g0519->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0519->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= choco.getNextValue->fcall(((int) g0520),((int) y));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(y);
                        bag *y_support;
                        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0520)))));
                        for (START(y_support); NEXT(y);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  y0test = y_some;
                  } 
                GC_OID(y0test);} 
              if (y0test != CNULL)
               { int  y0 = y0test;
                STOREI(c->currentSupport1->intValues[(x-c->currentSupport1->offset)],y0);
                } 
              else (*choco.removeVal)(_oid_(var1),
                  x,
                  c->idx1);
                } 
            x= ((g0517->inf->latestValue <= (x+1)) ?
              (x+1) :
              g0517->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0518->isa,choco._LinkedListIntDomain))
       { int  x = g0517->inf->latestValue;
        { OID gc_local;
          while ((x <= g0517->sup->latestValue))
          { GC_LOOP;
            { OID  y0test;
              { { OID  y_some = CNULL;
                  { IntVar * g0521 = var2;
                    AbstractIntDomain * g0522 = g0521->bucket;
                    if (g0522 == (NULL))
                     { int  y = g0521->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0521->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= ((g0521->inf->latestValue <= (y+1)) ?
                            (y+1) :
                            g0521->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0522->isa,choco._LinkedListIntDomain))
                     { int  y = g0521->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0521->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= choco.getNextValue->fcall(((int) g0522),((int) y));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(y);
                        bag *y_support;
                        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0522)))));
                        for (START(y_support); NEXT(y);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  y0test = y_some;
                  } 
                GC_OID(y0test);} 
              if (y0test != CNULL)
               { int  y0 = y0test;
                STOREI(c->currentSupport1->intValues[(x-c->currentSupport1->offset)],y0);
                } 
              else (*choco.removeVal)(_oid_(var1),
                  x,
                  c->idx1);
                } 
            x= choco.getNextValue->fcall(((int) g0518),((int) x));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(x);
          bag *x_support;
          x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0518)))));
          for (START(x_support); NEXT(x);)
          { GC_LOOP;
            { OID  y0test;
              { { OID  y_some = CNULL;
                  { IntVar * g0523 = var2;
                    AbstractIntDomain * g0524 = g0523->bucket;
                    if (g0524 == (NULL))
                     { int  y = g0523->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0523->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= ((g0523->inf->latestValue <= (y+1)) ?
                            (y+1) :
                            g0523->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0524->isa,choco._LinkedListIntDomain))
                     { int  y = g0523->inf->latestValue;
                      { OID gc_local;
                        while ((y <= g0523->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          y= choco.getNextValue->fcall(((int) g0524),((int) y));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(y);
                        bag *y_support;
                        y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0524)))));
                        for (START(y_support); NEXT(y);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { y_some= y;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  y0test = y_some;
                  } 
                GC_OID(y0test);} 
              if (y0test != CNULL)
               { int  y0 = y0test;
                STOREI(c->currentSupport1->intValues[(x-c->currentSupport1->offset)],y0);
                } 
              else (*choco.removeVal)(_oid_(var1),
                  x,
                  c->idx1);
                } 
            GC_UNLOOP;} 
          } 
        } 
    { IntVar * g0525 = var2;
      AbstractIntDomain * g0526 = g0525->bucket;
      if (g0526 == (NULL))
       { int  y = g0525->inf->latestValue;
        { OID gc_local;
          while ((y <= g0525->sup->latestValue))
          { GC_LOOP;
            { OID  x0test;
              { { OID  x_some = CNULL;
                  { IntVar * g0527 = var1;
                    AbstractIntDomain * g0528 = g0527->bucket;
                    if (g0528 == (NULL))
                     { int  x = g0527->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0527->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= ((g0527->inf->latestValue <= (x+1)) ?
                            (x+1) :
                            g0527->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0528->isa,choco._LinkedListIntDomain))
                     { int  x = g0527->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0527->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= choco.getNextValue->fcall(((int) g0528),((int) x));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(x);
                        bag *x_support;
                        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0528)))));
                        for (START(x_support); NEXT(x);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  x0test = x_some;
                  } 
                GC_OID(x0test);} 
              if (x0test != CNULL)
               { int  x0 = x0test;
                STOREI(c->currentSupport2->intValues[(y-c->currentSupport2->offset)],x0);
                } 
              else (*choco.removeVal)(_oid_(var2),
                  y,
                  c->idx2);
                } 
            y= ((g0525->inf->latestValue <= (y+1)) ?
              (y+1) :
              g0525->inf->latestValue );
            GC_UNLOOP;} 
          } 
        } 
      else if (INHERIT(g0526->isa,choco._LinkedListIntDomain))
       { int  y = g0525->inf->latestValue;
        { OID gc_local;
          while ((y <= g0525->sup->latestValue))
          { GC_LOOP;
            { OID  x0test;
              { { OID  x_some = CNULL;
                  { IntVar * g0529 = var1;
                    AbstractIntDomain * g0530 = g0529->bucket;
                    if (g0530 == (NULL))
                     { int  x = g0529->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0529->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= ((g0529->inf->latestValue <= (x+1)) ?
                            (x+1) :
                            g0529->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0530->isa,choco._LinkedListIntDomain))
                     { int  x = g0529->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0529->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= choco.getNextValue->fcall(((int) g0530),((int) x));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(x);
                        bag *x_support;
                        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0530)))));
                        for (START(x_support); NEXT(x);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  x0test = x_some;
                  } 
                GC_OID(x0test);} 
              if (x0test != CNULL)
               { int  x0 = x0test;
                STOREI(c->currentSupport2->intValues[(y-c->currentSupport2->offset)],x0);
                } 
              else (*choco.removeVal)(_oid_(var2),
                  y,
                  c->idx2);
                } 
            y= choco.getNextValue->fcall(((int) g0526),((int) y));
            GC_UNLOOP;} 
          } 
        } 
      else { OID gc_local;
          ITERATE(y);
          bag *y_support;
          y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0526)))));
          for (START(y_support); NEXT(y);)
          { GC_LOOP;
            { OID  x0test;
              { { OID  x_some = CNULL;
                  { IntVar * g0531 = var1;
                    AbstractIntDomain * g0532 = g0531->bucket;
                    if (g0532 == (NULL))
                     { int  x = g0531->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0531->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= ((g0531->inf->latestValue <= (x+1)) ?
                            (x+1) :
                            g0531->inf->latestValue );
                          } 
                        } 
                      } 
                    else if (INHERIT(g0532->isa,choco._LinkedListIntDomain))
                     { int  x = g0531->inf->latestValue;
                      { OID gc_local;
                        while ((x <= g0531->sup->latestValue))
                        { // HOHO, GC_LOOP not needed !
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          x= choco.getNextValue->fcall(((int) g0532),((int) x));
                          } 
                        } 
                      } 
                    else { OID gc_local;
                        ITERATE(x);
                        bag *x_support;
                        x_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0532)))));
                        for (START(x_support); NEXT(x);)
                        { GC_LOOP;
                          if (_oid_((ClaireObject *) choco.getTruthValue->fcall(((int) feasRel),((int) x),((int) y))) == _oid_(feas))
                           { x_some= x;
                            break;} 
                          GC_UNLOOP;} 
                        } 
                      } 
                  x0test = x_some;
                  } 
                GC_OID(x0test);} 
              if (x0test != CNULL)
               { int  x0 = x0test;
                STOREI(c->currentSupport2->intValues[(y-c->currentSupport2->offset)],x0);
                } 
              else (*choco.removeVal)(_oid_(var2),
                  y,
                  c->idx2);
                } 
            GC_UNLOOP;} 
          } 
        } 
    } 
  GC_UNBIND;} 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 2: Constraints as arbitrary lists feasible tuples         *
// ********************************************************************
// v0.15
// otherwise: the test method
/* The c++ function for: tupleConstraint(lvars:list[IntVar],feasible:boolean,mytuples:list[list[integer]]) [] */
CSPLargeConstraint * choco_tupleConstraint_list(list *lvars,ClaireBoolean *feasible,list *mytuples)
{ GC_BIND;
  { CSPLargeConstraint *Result ;
    { CSPLargeConstraint * c;
      { { CSPLargeConstraint * _CL_obj = ((CSPLargeConstraint *) GC_OBJECT(CSPLargeConstraint,new_object_class(choco._CSPLargeConstraint)));
          (_CL_obj->vars = ((list *) copy_bag(lvars)));
          c = _CL_obj;
          } 
        GC_OBJECT(CSPLargeConstraint,c);} 
      BoolMatrixND * mat;
      { { list * g0533UU;
          { { bag *v_list; OID v_val;
              OID v,CLcount;
              v_list = lvars;
               g0533UU = v_list->clone();
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { v = (*(v_list))[CLcount];
                v_val = _oid_(_dot_dot_integer(OBJECT(IntVar,v)->inf->latestValue,OBJECT(IntVar,v)->sup->latestValue));
                
                (*((list *) g0533UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0533UU);} 
          mat = choco_makeNDBoolMatrix_list(g0533UU,not_any(_oid_(feasible)),CFALSE);
          } 
        GC_OBJECT(BoolMatrixND,mat);} 
      (c->matrix = mat);
      { ITERATE(t);
        for (START(mytuples); NEXT(t);)
        claire_store_BoolMatrixND(mat,OBJECT(list,t),feasible);
        } 
      (c->nbVars = c->vars->length);
      (c->indices = make_list_integer2(c->nbVars,Kernel._integer,0));
      Result = c;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: awakeOnInf(c:CSPLargeConstraint,idx:integer) [] */
void  choco_awakeOnInf_CSPLargeConstraint(CSPLargeConstraint *c,int idx)
{ choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// v1.008 delay the propagation to the constraint-based loop
/* The c++ function for: awakeOnSup(c:CSPLargeConstraint,idx:integer) [] */
void  choco_awakeOnSup_CSPLargeConstraint(CSPLargeConstraint *c,int idx)
{ choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// v1.008
/* The c++ function for: awakeOnRem(c:CSPLargeConstraint,idx:integer,oldval:integer) [] */
void  choco_awakeOnRem_CSPLargeConstraint(CSPLargeConstraint *c,int idx,int oldval)
{ choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// v1.008
/* The c++ function for: awakeOnInst(c:CSPLargeConstraint,idx:integer) [] */
void  choco_awakeOnInst_CSPLargeConstraint(CSPLargeConstraint *c,int idx)
{ choco_constAwake_AbstractConstraint(c,CFALSE);
  } 


// v1.008
// forward checking propagation
/* The c++ function for: propagate(c:CSPLargeConstraint) [] */
void  choco_propagate_CSPLargeConstraint(CSPLargeConstraint *c)
{ GC_BIND;
  { int  n = c->vars->length;
    int  nbUnAssigned = 0;
    int  i0 = 0;
    { int  i = 1;
      int  g0534 = n;
      { OID gc_local;
        while ((i <= g0534))
        { // HOHO, GC_LOOP not needed !
          if (not_any(_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,(*(c->vars))[i]))))) == CTRUE)
           { ++nbUnAssigned;
            i0= i;
            } 
          ++i;
          } 
        } 
      } 
    if (nbUnAssigned == 1)
     { list * l1;
      { { list * i_bag = list::empty(Kernel.emptySet);
          { int  i = 1;
            int  g0535 = (i0-1);
            { OID gc_local;
              while ((i <= g0535))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast(OBJECT(IntVar,(*(c->vars))[i])->value);
                ++i;
                } 
              } 
            } 
          l1 = GC_OBJECT(list,i_bag);
          } 
        GC_OBJECT(list,l1);} 
      list * l2;
      { { list * i_bag = list::empty(Kernel.emptySet);
          { int  i = (i0+1);
            int  g0536 = n;
            { OID gc_local;
              while ((i <= g0536))
              { // HOHO, GC_LOOP not needed !
                i_bag->addFast(OBJECT(IntVar,(*(c->vars))[i])->value);
                ++i;
                } 
              } 
            } 
          l2 = GC_OBJECT(list,i_bag);
          } 
        GC_OBJECT(list,l2);} 
      if (c->cachedTuples == CTRUE)
       { IntVar * g0537 = OBJECT(IntVar,(*(c->vars))[i0]);
        AbstractIntDomain * g0538 = GC_OBJECT(AbstractIntDomain,g0537->bucket);
        if (g0538 == (NULL))
         { int  y = g0537->inf->latestValue;
          { OID gc_local;
            while ((y <= g0537->sup->latestValue))
            { GC_LOOP;
              if (claire_read_BoolMatrixND(GC_OBJECT(BoolMatrixND,c->matrix),append_list(append_list(l1,list::alloc(1,y)),l2)) != CTRUE)
               (*choco.removeVal)((*(c->vars))[i0],
                y,
                0);
              y= ((g0537->inf->latestValue <= (y+1)) ?
                (y+1) :
                g0537->inf->latestValue );
              GC_UNLOOP;} 
            } 
          } 
        else if (INHERIT(g0538->isa,choco._LinkedListIntDomain))
         { int  y = g0537->inf->latestValue;
          { OID gc_local;
            while ((y <= g0537->sup->latestValue))
            { GC_LOOP;
              if (claire_read_BoolMatrixND(GC_OBJECT(BoolMatrixND,c->matrix),append_list(append_list(l1,list::alloc(1,y)),l2)) != CTRUE)
               (*choco.removeVal)((*(c->vars))[i0],
                y,
                0);
              y= choco.getNextValue->fcall(((int) g0538),((int) y));
              GC_UNLOOP;} 
            } 
          } 
        else { OID gc_local;
            ITERATE(y);
            bag *y_support;
            y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0538)))));
            for (START(y_support); NEXT(y);)
            { GC_LOOP;
              if (claire_read_BoolMatrixND(GC_OBJECT(BoolMatrixND,c->matrix),append_list(append_list(l1,list::alloc(1,y)),l2)) != CTRUE)
               (*choco.removeVal)((*(c->vars))[i0],
                y,
                0);
              GC_UNLOOP;} 
            } 
          } 
      else { IntVar * g0539 = OBJECT(IntVar,(*(c->vars))[i0]);
          AbstractIntDomain * g0540 = GC_OBJECT(AbstractIntDomain,g0539->bucket);
          if (g0540 == (NULL))
           { int  y = g0539->inf->latestValue;
            { OID gc_local;
              while ((y <= g0539->sup->latestValue))
              { // HOHO, GC_LOOP not needed !
                if (boolean_I_any(funcall_method1(c->feasTest,_oid_(append_list(append_list(l1,list::alloc(1,y)),l2)))) != CTRUE)
                 (*choco.removeVal)((*(c->vars))[i0],
                  y,
                  0);
                y= ((g0539->inf->latestValue <= (y+1)) ?
                  (y+1) :
                  g0539->inf->latestValue );
                } 
              } 
            } 
          else if (INHERIT(g0540->isa,choco._LinkedListIntDomain))
           { int  y = g0539->inf->latestValue;
            { OID gc_local;
              while ((y <= g0539->sup->latestValue))
              { // HOHO, GC_LOOP not needed !
                if (boolean_I_any(funcall_method1(c->feasTest,_oid_(append_list(append_list(l1,list::alloc(1,y)),l2)))) != CTRUE)
                 (*choco.removeVal)((*(c->vars))[i0],
                  y,
                  0);
                y= choco.getNextValue->fcall(((int) g0540),((int) y));
                } 
              } 
            } 
          else { OID gc_local;
              ITERATE(y);
              bag *y_support;
              y_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0540)))));
              for (START(y_support); NEXT(y);)
              { GC_LOOP;
                if (boolean_I_any(funcall_method1(c->feasTest,_oid_(append_list(append_list(l1,list::alloc(1,y)),l2)))) != CTRUE)
                 (*choco.removeVal)((*(c->vars))[i0],
                  y,
                  0);
                GC_UNLOOP;} 
              } 
            } 
        } 
    else if (nbUnAssigned == 0)
     { { ClaireBoolean * g0543I;
        { ClaireBoolean *v_or;
          { { ClaireBoolean *v_and;
              { v_and = c->cachedTuples;
                if (v_and == CFALSE) v_or =CFALSE; 
                else { { OID  g0544UU;
                    { ClaireBoolean * V_CL0545;{ list * g0546UU;
                        { list * i_bag = list::empty(Kernel.emptySet);
                          { int  i = 1;
                            int  g0541 = n;
                            { OID gc_local;
                              while ((i <= g0541))
                              { // HOHO, GC_LOOP not needed !
                                i_bag->addFast(OBJECT(IntVar,(*(c->vars))[i])->value);
                                ++i;
                                } 
                              } 
                            } 
                          g0546UU = GC_OBJECT(list,i_bag);
                          } 
                        V_CL0545 = claire_read_BoolMatrixND(GC_OBJECT(BoolMatrixND,c->matrix),g0546UU);
                        } 
                      
                      g0544UU=_oid_(V_CL0545);} 
                    v_and = not_any(g0544UU);
                    } 
                  if (v_and == CFALSE) v_or =CFALSE; 
                  else v_or = CTRUE;} 
                } 
              } 
            if (v_or == CTRUE) g0543I =CTRUE; 
            else { { ClaireBoolean *v_and;
                { v_and = not_any(_oid_(c->cachedTuples));
                  if (v_and == CFALSE) v_or =CFALSE; 
                  else { { OID  g0547UU;
                      { ClaireBoolean * V_CL0548;{ OID  g0549UU;
                          { OID  g0550UU;
                            { list * V_CL0551;{ list * i_bag = list::empty(Kernel.emptySet);
                                { int  i = 1;
                                  int  g0542 = n;
                                  { OID gc_local;
                                    while ((i <= g0542))
                                    { // HOHO, GC_LOOP not needed !
                                      i_bag->addFast(OBJECT(IntVar,(*(c->vars))[i])->value);
                                      ++i;
                                      } 
                                    } 
                                  } 
                                V_CL0551 = GC_OBJECT(list,i_bag);
                                } 
                              
                              g0550UU=_oid_(V_CL0551);} 
                            g0549UU = funcall_method1(c->feasTest,g0550UU);
                            } 
                          V_CL0548 = boolean_I_any(g0549UU);
                          } 
                        
                        g0547UU=_oid_(V_CL0548);} 
                      v_and = ((g0547UU != Kernel.ctrue) ? CTRUE : CFALSE);
                      } 
                    if (v_and == CFALSE) v_or =CFALSE; 
                    else v_or = CTRUE;} 
                  } 
                } 
              if (v_or == CTRUE) g0543I =CTRUE; 
              else g0543I = CFALSE;} 
            } 
          } 
        
        if (g0543I == CTRUE) choco_raiseContradiction_AbstractConstraint(c);
          } 
      } 
    } 
  GC_UNBIND;} 


// v1.008
// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 3: Comparisons as unary constraints                       *
// ********************************************************************
// Comparisons to a constant (unary Constraints)
//
/* The c++ function for: self_print(c:GreaterOrEqualxc) [] */
void  claire_self_print_GreaterOrEqualxc_choco(GreaterOrEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" >= ");
  print_any(c->cste);
  princ_string("");
  } 


/* The c++ function for: self_print(c:LessOrEqualxc) [] */
void  claire_self_print_LessOrEqualxc_choco(LessOrEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" <= ");
  print_any(c->cste);
  princ_string("");
  } 


/* The c++ function for: self_print(c:Equalxc) [] */
void  claire_self_print_Equalxc_choco(Equalxc *c)
{ princ_string(c->v1->name);
  princ_string(" = ");
  print_any(c->cste);
  princ_string("");
  } 


/* The c++ function for: self_print(c:NotEqualxc) [] */
void  claire_self_print_NotEqualxc_choco(NotEqualxc *c)
{ princ_string(c->v1->name);
  princ_string(" <> ");
  print_any(c->cste);
  princ_string("");
  } 


// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
/* The c++ function for: awakeOnInf(c:Equalxc,i:integer) [] */
void  choco_awakeOnInf_Equalxc(Equalxc *c,int i)
{ GC_BIND;
  choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:Equalxc,i:integer) [] */
void  choco_awakeOnSup_Equalxc(Equalxc *c,int i)
{ GC_BIND;
  choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:Equalxc,i:integer) [] */
void  choco_awakeOnInst_Equalxc(Equalxc *c,int i)
{ GC_BIND;
  choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: propagate(c:Equalxc) [] */
void  choco_propagate_Equalxc(Equalxc *c)
{ GC_BIND;
  choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v1),c->cste,c->idx1);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:Equalxc) [] */
OID  choco_askIfEntailed_Equalxc(Equalxc *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * v = c->v1;
      int  x = c->cste;
      if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
        x))) == CTRUE)
       Result = Kernel.ctrue;
      else if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
        x)) == CTRUE)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:Equalxc) [] */
ClaireBoolean * choco_testIfSatisfied_Equalxc(Equalxc *c)
{ ;return (equal(c->v1->value,c->cste));} 


// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
/* The c++ function for: awakeOnInf(c:GreaterOrEqualxc,i:integer) [] */
void  choco_awakeOnInf_GreaterOrEqualxc(GreaterOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:GreaterOrEqualxc,i:integer) [] */
void  choco_awakeOnSup_GreaterOrEqualxc(GreaterOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:GreaterOrEqualxc,i:integer) [] */
void  choco_awakeOnInst_GreaterOrEqualxc(GreaterOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  GC_UNBIND;} 


/* The c++ function for: propagate(c:GreaterOrEqualxc) [] */
void  choco_propagate_GreaterOrEqualxc(GreaterOrEqualxc *c)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:GreaterOrEqualxc) [] */
OID  choco_askIfEntailed_GreaterOrEqualxc(GreaterOrEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * v = c->v1;
      int  x = c->cste;
      if (x <= choco.getInf->fcall(((int) v)))
       Result = Kernel.ctrue;
      else if (choco.getSup->fcall(((int) v)) < x)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:GreaterOrEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_GreaterOrEqualxc(GreaterOrEqualxc *c)
{ ;return (_inf_equal_integer(c->cste,c->v1->value));} 


// v1.0: for such constraints that are not subconstraints of combinations, 
// disconnect them from the network after one single propagation
/* The c++ function for: awakeOnInf(c:LessOrEqualxc,i:integer) [] */
void  choco_awakeOnInf_LessOrEqualxc(LessOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateSup)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:LessOrEqualxc,i:integer) [] */
void  choco_awakeOnSup_LessOrEqualxc(LessOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateSup)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:LessOrEqualxc,i:integer) [] */
void  choco_awakeOnInst_LessOrEqualxc(LessOrEqualxc *c,int i)
{ GC_BIND;
  (*choco.updateSup)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  GC_UNBIND;} 


/* The c++ function for: propagate(c:LessOrEqualxc) [] */
void  choco_propagate_LessOrEqualxc(LessOrEqualxc *c)
{ GC_BIND;
  (*choco.updateSup)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:LessOrEqualxc) [] */
OID  choco_askIfEntailed_LessOrEqualxc(LessOrEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * v = c->v1;
      int  x = c->cste;
      if (choco.getSup->fcall(((int) v)) <= x)
       Result = Kernel.ctrue;
      else if (choco.getInf->fcall(((int) v)) > x)
       Result = Kernel.cfalse;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:LessOrEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_LessOrEqualxc(LessOrEqualxc *c)
{ ;return (_inf_equal_integer(c->v1->value,c->cste));} 


// v1.05 the calls to setPassive are valid only when c.v1 is not a BoundIntVar
// Moreover, they seem to be a bit heavy (too long to do perform given the computation that they later save)
/* The c++ function for: awakeOnInf(c:NotEqualxc,idx:integer) [] */
void  choco_awakeOnInf_NotEqualxc(NotEqualxc *c,int idx)
{ GC_BIND;
  (*choco.removeVal)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awakeOnSup(c:NotEqualxc,idx:integer) [] */
void  choco_awakeOnSup_NotEqualxc(NotEqualxc *c,int idx)
{ GC_BIND;
  (*choco.removeVal)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  GC_UNBIND;} 


/* The c++ function for: awakeOnInst(c:NotEqualxc,idx:integer) [] */
void  choco_awakeOnInst_NotEqualxc(NotEqualxc *c,int idx)
{ if (c->v1->inf->latestValue == c->cste)
   choco_raiseContradiction_AbstractConstraint(c);
  } 


// v1.010
/* The c++ function for: propagate(c:NotEqualxc) [] */
void  choco_propagate_NotEqualxc(NotEqualxc *c)
{ GC_BIND;
  (*choco.removeVal)(GC_OID(_oid_(c->v1)),
    c->cste,
    c->idx1);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:NotEqualxc) [] */
OID  choco_askIfEntailed_NotEqualxc(NotEqualxc *c)
{ GC_BIND;
  { OID Result = 0;
    { IntVar * v = c->v1;
      int  x = c->cste;
      if ((OBJECT(ClaireBoolean,(*choco.isInstantiatedTo)(_oid_(v),
        x))) == CTRUE)
       Result = Kernel.cfalse;
      else if (not_any((*choco.canBeInstantiatedTo)(_oid_(v),
        x)) == CTRUE)
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:NotEqualxc) [] */
ClaireBoolean * choco_testIfSatisfied_NotEqualxc(NotEqualxc *c)
{ ;return (_I_equal_any(c->v1->value,c->cste));} 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 4: Comparisons as binary constraints                      *
// ********************************************************************
/* The c++ function for: self_print(c:Equalxyc) [] */
void  claire_self_print_Equalxyc_choco(Equalxyc *c)
{ princ_string(c->v1->name);
  princ_string(" = ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  princ_string("");
  } 


/* The c++ function for: self_print(c:NotEqualxyc) [] */
void  claire_self_print_NotEqualxyc_choco(NotEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" <> ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  princ_string("");
  } 


/* The c++ function for: self_print(c:GreaterOrEqualxyc) [] */
void  claire_self_print_GreaterOrEqualxyc_choco(GreaterOrEqualxyc *c)
{ princ_string(c->v1->name);
  princ_string(" >= ");
  princ_string(c->v2->name);
  princ_string(" + ");
  print_any(c->cste);
  princ_string("");
  } 


// ----- Constraint:  U == V + c ------
/* The c++ function for: propagate(c:Equalxyc) [] */
void  choco_propagate_Equalxyc(Equalxyc *c)
{ GC_BIND;
  (*choco.updateInf)(GC_OID(_oid_(c->v1)),
    (c->v2->inf->latestValue+c->cste),
    c->idx1);
  (*choco.updateSup)(GC_OID(_oid_(c->v1)),
    (c->v2->sup->latestValue+c->cste),
    c->idx1);
  (*choco.updateInf)(GC_OID(_oid_(c->v2)),
    (c->v1->inf->latestValue-c->cste),
    c->idx2);
  (*choco.updateSup)(GC_OID(_oid_(c->v2)),
    (c->v1->sup->latestValue-c->cste),
    c->idx2);
  if (c->v2->bucket != (NULL))
   { IntVar * g0552 = c->v1;
    AbstractIntDomain * g0553 = g0552->bucket;
    if (g0553 == (NULL))
     { int  val = g0552->inf->latestValue;
      { OID gc_local;
        while ((val <= g0552->sup->latestValue))
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
            (val-c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v1)),
            val,
            c->idx1);
          val= ((g0552->inf->latestValue <= (val+1)) ?
            (val+1) :
            g0552->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0553->isa,choco._LinkedListIntDomain))
     { int  val = g0552->inf->latestValue;
      { OID gc_local;
        while ((val <= g0552->sup->latestValue))
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
            (val-c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v1)),
            val,
            c->idx1);
          val= choco.getNextValue->fcall(((int) g0553),((int) val));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0553)))));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v2)),
            (val-c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v1)),
            val,
            c->idx1);
          GC_UNLOOP;} 
        } 
      } 
  if (c->v1->bucket != (NULL))
   { IntVar * g0554 = c->v2;
    AbstractIntDomain * g0555 = g0554->bucket;
    if (g0555 == (NULL))
     { int  val = g0554->inf->latestValue;
      { OID gc_local;
        while ((val <= g0554->sup->latestValue))
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
            (val+c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            val,
            c->idx2);
          val= ((g0554->inf->latestValue <= (val+1)) ?
            (val+1) :
            g0554->inf->latestValue );
          GC_UNLOOP;} 
        } 
      } 
    else if (INHERIT(g0555->isa,choco._LinkedListIntDomain))
     { int  val = g0554->inf->latestValue;
      { OID gc_local;
        while ((val <= g0554->sup->latestValue))
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
            (val+c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            val,
            c->idx2);
          val= choco.getNextValue->fcall(((int) g0555),((int) val));
          GC_UNLOOP;} 
        } 
      } 
    else { OID gc_local;
        ITERATE(val);
        bag *val_support;
        val_support = GC_OBJECT(list,OBJECT(bag,_oid_((ClaireObject *) choco.domainSequence->fcall(((int) g0555)))));
        for (START(val_support); NEXT(val);)
        { GC_LOOP;
          if (not_any((*choco.canBeInstantiatedTo)(GC_OID(_oid_(c->v1)),
            (val+c->cste))) == CTRUE)
           (*choco.removeVal)(GC_OID(_oid_(c->v2)),
            val,
            c->idx2);
          GC_UNLOOP;} 
        } 
      } 
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:Equalxyc) [] */
OID  choco_askIfEntailed_Equalxyc(Equalxyc *c)
{ { OID Result = 0;
    if ((c->v1->sup->latestValue < (c->v2->inf->latestValue+c->cste)) || 
        (c->v1->inf->latestValue > (c->v2->sup->latestValue+c->cste)))
     Result = Kernel.cfalse;
    else if ((c->v1->inf->latestValue == c->v1->sup->latestValue) && 
        ((c->v2->inf->latestValue == c->v2->sup->latestValue) && 
          (c->v1->inf->latestValue == (c->v2->inf->latestValue+c->cste))))
     Result = Kernel.ctrue;
    else Result = CNULL;
      return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:Equalxyc) [] */
ClaireBoolean * choco_testIfSatisfied_Equalxyc(Equalxyc *c)
{ ;;return (equal(c->v1->value,(c->v2->value+c->cste)));} 


/* The c++ function for: awakeOnInf(c:Equalxyc,idx:integer) [] */
void  choco_awakeOnInf_Equalxyc(Equalxyc *c,int idx)
{ if (idx == 1) 
  { (*choco.updateInf)(GC_OID(_oid_(c->v2)),
      (c->v1->inf->latestValue-c->cste),
      c->idx2);
     } 
  else{ GC_BIND;
    (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      (c->v2->inf->latestValue+c->cste),
      c->idx1);
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnSup(c:Equalxyc,idx:integer) [] */
void  choco_awakeOnSup_Equalxyc(Equalxyc *c,int idx)
{ if (idx == 1) 
  { (*choco.updateSup)(GC_OID(_oid_(c->v2)),
      (c->v1->sup->latestValue-c->cste),
      c->idx2);
     } 
  else{ GC_BIND;
    (*choco.updateSup)(GC_OID(_oid_(c->v1)),
      (c->v2->sup->latestValue+c->cste),
      c->idx1);
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnInst(c:Equalxyc,idx:integer) [] */
void  choco_awakeOnInst_Equalxyc(Equalxyc *c,int idx)
{ if (idx == 1) 
  { choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v2),(c->v1->value-c->cste),c->idx2);
     } 
  else{ GC_BIND;
    choco_instantiate_IntVar2(GC_OBJECT(IntVar,c->v1),(c->v2->value+c->cste),c->idx1);
    GC_UNBIND;} 
  } 


/* The c++ function for: awakeOnRem(c:Equalxyc,idx:integer,x:integer) [] */
void  choco_awakeOnRem_Equalxyc(Equalxyc *c,int idx,int x)
{ if (idx == 1) 
  { (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      (x-c->cste),
      c->idx2);
     } 
  else{ GC_BIND;
    (*choco.removeVal)(GC_OID(_oid_(c->v1)),
      (x+c->cste),
      c->idx1);
    GC_UNBIND;} 
  } 


// ----- Constraint:  U <> V + c ------
/* The c++ function for: propagate(cont:NotEqualxyc) [] */
void  choco_propagate_NotEqualxyc(NotEqualxyc *cont)
{ choco_awakeOnInf_NotEqualxyc(cont,1);
  choco_awakeOnInf_NotEqualxyc(cont,2);
  } 


/* The c++ function for: awakeOnInf(c:NotEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_NotEqualxyc(NotEqualxyc *c,int idx)
{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) 
  { (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      (c->v1->value-c->cste),
      c->idx2);
     } 
  else{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) 
    { (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        (c->v2->value+c->cste),
        c->idx1);
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnSup(c:NotEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_NotEqualxyc(NotEqualxyc *c,int idx)
{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) 
  { (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      (c->v1->value-c->cste),
      c->idx2);
     } 
  else{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) 
    { (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        (c->v2->value+c->cste),
        c->idx1);
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


// v1.0: after one instantiation, the constraint is necessarily always satisfied
/* The c++ function for: awakeOnInst(c:NotEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_NotEqualxyc(NotEqualxyc *c,int idx)
{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v1)))))))) == CTRUE) 
  { (*choco.removeVal)(GC_OID(_oid_(c->v2)),
      (c->v1->value-c->cste),
      c->idx2);
     } 
  else{ if ((OBJECT(ClaireBoolean,_oid_((ClaireObject *) choco.isInstantiated->fcall(((int) OBJECT(ClaireObject,GC_OID(_oid_(c->v2)))))))) == CTRUE) 
    { (*choco.removeVal)(GC_OID(_oid_(c->v1)),
        (c->v2->value+c->cste),
        c->idx1);
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: askIfEntailed(c:NotEqualxyc) [] */
OID  choco_askIfEntailed_NotEqualxyc(NotEqualxyc *c)
{ { OID Result = 0;
    if ((c->v1->sup->latestValue < (c->v2->inf->latestValue+c->cste)) || 
        (c->v2->sup->latestValue < (c->v1->inf->latestValue-c->cste)))
     Result = Kernel.ctrue;
    else if ((c->v1->inf->latestValue == c->v1->sup->latestValue) && 
        ((c->v2->inf->latestValue == c->v2->sup->latestValue) && 
          (c->v1->inf->latestValue == (c->v2->inf->latestValue+c->cste))))
     Result = Kernel.cfalse;
    else Result = CNULL;
      return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:NotEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_NotEqualxyc(NotEqualxyc *c)
{ ;;return (_I_equal_any(c->v1->value,(c->v2->value+c->cste)));} 


// ----- Constraint:  U >= V + c ------
/* The c++ function for: propagate(cont:GreaterOrEqualxyc) [] */
void  choco_propagate_GreaterOrEqualxyc(GreaterOrEqualxyc *cont)
{ choco_awakeOnInf_GreaterOrEqualxyc(cont,2);
  choco_awakeOnSup_GreaterOrEqualxyc(cont,1);
  } 


/* The c++ function for: awakeOnInf(c:GreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInf_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx)
{ if (idx == 2) 
  { (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      (c->v2->inf->latestValue+c->cste),
      c->idx1);
     } 
  else{ if ((c->v2->sup->latestValue+c->cste) <= c->v1->inf->latestValue) 
    { choco_setPassive_AbstractConstraint(c);
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnSup(c:GreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnSup_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx)
{ if (idx == 1) 
  { (*choco.updateSup)(GC_OID(_oid_(c->v2)),
      (c->v1->sup->latestValue-c->cste),
      c->idx2);
     } 
  else{ if ((c->v2->sup->latestValue+c->cste) <= c->v1->inf->latestValue) 
    { choco_setPassive_AbstractConstraint(c);
       } 
    else{ GC_BIND;
      ;GC_UNBIND;} 
    } 
  } 


/* The c++ function for: awakeOnInst(c:GreaterOrEqualxyc,idx:integer) [] */
void  choco_awakeOnInst_GreaterOrEqualxyc(GreaterOrEqualxyc *c,int idx)
{ GC_BIND;
  if (idx == 1)
   (*choco.updateSup)(GC_OID(_oid_(c->v2)),
    (c->v1->value-c->cste),
    c->idx2);
  else (*choco.updateInf)(GC_OID(_oid_(c->v1)),
      (c->v2->value+c->cste),
      c->idx1);
    if ((c->v2->sup->latestValue+c->cste) <= c->v1->inf->latestValue)
   choco_setPassive_AbstractConstraint(c);
  GC_UNBIND;} 


/* The c++ function for: askIfEntailed(c:GreaterOrEqualxyc) [] */
OID  choco_askIfEntailed_GreaterOrEqualxyc(GreaterOrEqualxyc *c)
{ { OID Result = 0;
    if (c->v1->sup->latestValue < (c->v2->inf->latestValue+c->cste))
     Result = Kernel.cfalse;
    else if ((c->v2->sup->latestValue+c->cste) <= c->v1->inf->latestValue)
     Result = Kernel.ctrue;
    else Result = CNULL;
      return (Result);} 
  } 


/* The c++ function for: testIfSatisfied(c:GreaterOrEqualxyc) [] */
ClaireBoolean * choco_testIfSatisfied_GreaterOrEqualxyc(GreaterOrEqualxyc *c)
{ ;;return (_inf_equal_integer((c->v2->value+c->cste),c->v1->value));} 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)
// ********************************************************************
// *   Part 5: General linear combinations as global constraints      *
// ********************************************************************
// ----- Constraint:  sum(ai Xi) + c >= 0 (or == 0) ------
// v0.32: This constraint has the following data structures
//   coefs[i] is the coefficient of vars[i]
//   nbPosVars is the number of >0 coefficient.
//   vars and coeffs are stored such that all positive coefficients come first
//   (from position 1 to nbPosVars) and negative coefficients last
//   (from position nbPosVars + 1 to nbVars)
// v1.010: introduce op: 1: eqality constrain (== 0)
//                       2: inequalitity constraint (>= 0)
//                       3: disequality constraint (<> 0)
// make the initial propagation
// TODO: changed to StoredBool
//v0.37: must be stored in order to remain true in worlds
//       were the constraint has not been awoken yet (when in a bool. const.)
// v1.02
/* The c++ function for: self_print(self:LinComb) [] */
void  claire_self_print_LinComb_choco(LinComb *self)
{ { int  n = self->vars->length;
    int  i = 1;
    int  g0556 = ((n <= 5) ?
      n :
      5 );
    { OID gc_local;
      while ((i <= g0556))
      { // HOHO, GC_LOOP not needed !
        if ((i != 1) && 
            (((OID *) self->coefs)[i] > 0))
         princ_string("+");
        print_any(((OID *) self->coefs)[i]);
        princ_string(".");
        princ_string(OBJECT(AbstractVar,(*(self->vars))[i])->name);
        princ_string(" ");
        ++i;
        } 
      } 
    } 
  if (self->cste > 0)
   { princ_string("+");
    print_any(self->cste);
    princ_string("");
    } 
  else if (self->cste < 0)
   { print_any(self->cste);
    princ_string("");
    } 
  if (self->op == 1)
   princ_string("== 0");
  else if (self->op == 2)
   princ_string(">= 0");
  else princ_string("<> 0");
    } 


// 0.32: new data structures
/* The c++ function for: makeLinComb(l1:list[integer],l2:list[IntVar],c:integer,opn:integer) [] */
LinComb * choco_makeLinComb_list(list *l1,list *l2,int c,int opn)
{ GC_BIND;
  ;;{ LinComb *Result ;
    { list * posCoefs = list::empty(Kernel._integer);
      list * negCoefs = list::empty(Kernel._integer);
      list * posVars = list::empty(choco._IntVar);
      list * negVars = list::empty(choco._IntVar);
      { int  j = 1;
        int  g0557 = l2->length;
        { OID gc_local;
          while ((j <= g0557))
          { // HOHO, GC_LOOP not needed !
            if ((*(l1))[j] > 0)
             { posCoefs= posCoefs->addFast((*(l1))[j]);
              posVars= posVars->addFast((*(l2))[j]);
              } 
            else if ((*(l1))[j] < 0)
             { negCoefs= negCoefs->addFast((*(l1))[j]);
              negVars= negVars->addFast((*(l2))[j]);
              } 
            ++j;
            } 
          } 
        } 
      { LinComb * cont;
        { { LinComb * _CL_obj = ((LinComb *) GC_OBJECT(LinComb,new_object_class(choco._LinComb)));
            (_CL_obj->vars = append_list(posVars,negVars));
            (_CL_obj->cste = c);
            cont = _CL_obj;
            } 
          GC_OBJECT(LinComb,cont);} 
        (cont->nbVars = cont->vars->length);
        (cont->indices = make_list_integer2(cont->nbVars,Kernel._integer,0));
        (cont->op = opn);
        (cont->nbPosVars = posCoefs->length);
        (cont->coefs = array_I_list(append_list(posCoefs,negCoefs)));
        Result = cont;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: getNbVars(c:LinComb) [] */
int  choco_getNbVars_LinComb(LinComb *c)
{ return (c->nbVars);} 


// v0.29
// v0.30: linear combinations can now be used within Boolean constraints or within a Delayer ("UnCompositeConstraint")
/* The c++ function for: assignIndices(c:LinComb,root:(CompositeConstraint U Delayer),i:integer) [] */
int  choco_assignIndices_LinComb(LinComb *c,AbstractConstraint *root,int i)
{ { int Result = 0;
    { int  j = i;
      { int  k = 1;
        int  g0558 = c->nbPosVars;
        { OID gc_local;
          while ((k <= g0558))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint2(root,
              OBJECT(IntVar,(*(c->vars))[k]),
              j,
              CTRUE,
              _I_equal_any(c->op,2),
              CTRUE,
              CFALSE);
            ((*(c->indices))[k]=OBJECT(AbstractVar,(*(c->vars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      { int  k = (c->nbPosVars+1);
        int  g0559 = c->nbVars;
        { OID gc_local;
          while ((k <= g0559))
          { // HOHO, GC_LOOP not needed !
            ++j;
            choco_connectIntVar_AbstractConstraint2(root,
              OBJECT(IntVar,(*(c->vars))[k]),
              j,
              CTRUE,
              CTRUE,
              _I_equal_any(c->op,2),
              CFALSE);
            ((*(c->indices))[k]=OBJECT(AbstractVar,(*(c->vars))[k])->constraints->length);
            ++k;
            } 
          } 
        } 
      Result = j;
      } 
    return (Result);} 
  } 


// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: opposite(c:LinComb) [] */
LinComb * choco_opposite_LinComb(LinComb *c)
{ if (c->op == 1) 
  { { LinComb *Result ;
      { list * g0569UU;
        { list * i_bag = list::empty(Kernel.emptySet);
          { int  g0560 = 1;
            OID * g0561 = GC_ARRAY(c->coefs);
            int  g0562 = g0561[0];
            { OID gc_local;
              while ((g0560 <= g0562))
              { // HOHO, GC_LOOP not needed !
                { int  i = ((OID *) g0561)[g0560];
                  i_bag->addFast(i);
                  ++g0560;
                  } 
                } 
              } 
            } 
          g0569UU = GC_OBJECT(list,i_bag);
          } 
        Result = choco_makeLinComb_list(g0569UU,GC_OBJECT(list,c->vars),c->cste,3);
        } 
      return (Result);} 
     } 
  else{ if (c->op == 2) 
    { { LinComb *Result ;
        { list * g0570UU;
          { list * i_bag = list::empty(Kernel.emptySet);
            { int  g0563 = 1;
              OID * g0564 = GC_ARRAY(c->coefs);
              int  g0565 = g0564[0];
              { OID gc_local;
                while ((g0563 <= g0565))
                { // HOHO, GC_LOOP not needed !
                  { int  i = ((OID *) g0564)[g0563];
                    i_bag->addFast((-i));
                    ++g0563;
                    } 
                  } 
                } 
              } 
            g0570UU = GC_OBJECT(list,i_bag);
            } 
          Result = choco_makeLinComb_list(g0570UU,GC_OBJECT(list,c->vars),(-(c->cste+1)),2);
          } 
        return (Result);} 
       } 
    else{ GC_BIND;
      ;{ LinComb *Result ;
        { list * g0571UU;
          { list * i_bag = list::empty(Kernel.emptySet);
            { int  g0566 = 1;
              OID * g0567 = GC_ARRAY(c->coefs);
              int  g0568 = g0567[0];
              { OID gc_local;
                while ((g0566 <= g0568))
                { // HOHO, GC_LOOP not needed !
                  { int  i = ((OID *) g0567)[g0566];
                    i_bag->addFast(i);
                    ++g0566;
                    } 
                  } 
                } 
              } 
            g0571UU = GC_OBJECT(list,i_bag);
            } 
          Result = choco_makeLinComb_list(g0571UU,GC_OBJECT(list,c->vars),c->cste,1);
          } 
        GC_UNBIND; return (Result);} 
      } 
    } 
  } 


// v1.011 coefs is an array
// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: computeVarIdxInOpposite(c:LinComb,i:integer) [] */
int  choco_computeVarIdxInOpposite_LinComb(LinComb *c,int i)
{ { int Result = 0;
    Result = ((c->op != 2) ?
      i :
      ((i <= c->nbPosVars) ?
        ((i+c->nbVars)-c->nbPosVars) :
        (i-c->nbPosVars) ) );
    return (Result);} 
  } 


// Upper bound estimate of a linear combination of variables
//
// v1.05 added a few casts
/* The c++ function for: computeUpperBound(c:LinComb) [] */
int  choco_computeUpperBound_LinComb_choco(LinComb *c)
{ { int Result = 0;
    { int  s = 0;
      { int  i = 1;
        int  g0572 = c->nbPosVars;
        { while ((i <= g0572))
          { s= (s+(OBJECT(IntVar,(*(c->vars))[i])->sup->latestValue*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0573 = c->nbVars;
        { while ((i <= g0573))
          { s= (s+(OBJECT(IntVar,(*(c->vars))[i])->inf->latestValue*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      Result = (s+c->cste);
      } 
    return (Result);} 
  } 


// lower bound estimate of a linear combination of variables
//
// v1.05 added a few casts
/* The c++ function for: computeLowerBound(c:LinComb) [] */
int  choco_computeLowerBound_LinComb_choco(LinComb *c)
{ { int Result = 0;
    { int  s = 0;
      { int  i = 1;
        int  g0574 = c->nbPosVars;
        { while ((i <= g0574))
          { s= (s+(OBJECT(IntVar,(*(c->vars))[i])->inf->latestValue*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0575 = c->nbVars;
        { while ((i <= g0575))
          { s= (s+(OBJECT(IntVar,(*(c->vars))[i])->sup->latestValue*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      Result = (s+c->cste);
      } 
    return (Result);} 
  } 


// v1.010: new internal methods
// v0.9907: this does not reach saturation (fix point), but returns a Boolean indicating 
// whether it infered new information or not.
// v1.05 added a few casts
// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
/* The c++ function for: propagateNewLowerBound(c:LinComb,mylb:integer) [] */
ClaireBoolean * choco_propagateNewLowerBound_LinComb_choco(LinComb *c,int mylb)
{ { ClaireBoolean *Result ;
    { ClaireBoolean * anyChange = CFALSE;
      if (mylb > 0)
       choco_raiseContradiction_AbstractConstraint(c);
      { int  i = 1;
        int  g0576 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0576))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.updateSup)((*(c->vars))[i],
              (choco_div_dash_integer((-mylb),((OID *) c->coefs)[i])+OBJECT(IntVar,(*(c->vars))[i])->inf->latestValue),
              (*(c->indices))[i]))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0577 = c->nbVars;
        { OID gc_local;
          while ((i <= g0577))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.updateInf)((*(c->vars))[i],
              (choco_div_plus_integer(mylb,(-((OID *) c->coefs)[i]))+OBJECT(IntVar,(*(c->vars))[i])->sup->latestValue),
              (*(c->indices))[i]))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            } 
          } 
        } 
      Result = anyChange;
      } 
    return (Result);} 
  } 


// propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c 
/* The c++ function for: propagateNewUpperBound(c:LinComb,myub:integer) [] */
ClaireBoolean * choco_propagateNewUpperBound_LinComb_choco(LinComb *c,int myub)
{ { ClaireBoolean *Result ;
    { ClaireBoolean * anyChange = CFALSE;
      if (myub < 0)
       choco_raiseContradiction_AbstractConstraint(c);
      { int  i = 1;
        int  g0578 = c->nbPosVars;
        { OID gc_local;
          while ((i <= g0578))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.updateInf)((*(c->vars))[i],
              (choco_div_plus_integer((-myub),((OID *) c->coefs)[i])+OBJECT(IntVar,(*(c->vars))[i])->sup->latestValue),
              (*(c->indices))[i]))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            } 
          } 
        } 
      { int  i = (c->nbPosVars+1);
        int  g0579 = c->nbVars;
        { OID gc_local;
          while ((i <= g0579))
          { // HOHO, GC_LOOP not needed !
            if ((OBJECT(ClaireBoolean,(*choco.updateSup)((*(c->vars))[i],
              (choco_div_dash_integer(myub,(-((OID *) c->coefs)[i]))+OBJECT(IntVar,(*(c->vars))[i])->inf->latestValue),
              (*(c->indices))[i]))) == CTRUE)
             anyChange= CTRUE;
            ++i;
            } 
          } 
        } 
      Result = anyChange;
      } 
    return (Result);} 
  } 


// when the lower bound changes
// Note: this must be called as a constraint check everytime the lower bound is improved
//       but there is some propagation to perform only in case the linear constraint
//       is a linear equality
// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: filterOnImprovedLowerBound(c:LinComb) [] */
ClaireBoolean * choco_filterOnImprovedLowerBound_LinComb(LinComb *c)
{ if (c->op == 1) 
  { { ClaireBoolean *Result ;
      Result = OBJECT(ClaireBoolean,(*choco.propagateNewLowerBound)(_oid_(c),
        GC_OID((*choco.computeLowerBound)(_oid_(c)))));
      return (Result);} 
     } 
  else{ GC_BIND;
    { ClaireBoolean *Result ;
      if (c->op == 2)
       Result = CFALSE;
      else { { int  mylb = (*choco.computeLowerBound)(_oid_(c));
            ClaireBoolean * anyChange = CFALSE;
            Result = ((mylb == 0) ?
              OBJECT(ClaireBoolean,(*choco.propagateNewUpperBound)(_oid_(c),
                (((*choco.computeUpperBound)(_oid_(c)))-1))) :
              CFALSE );
            } 
          } 
        GC_UNBIND; return (Result);} 
    } 
  } 


// when the upper bound changes
// v1.05 added a few casts
/* The c++ function for: filterOnImprovedUpperBound(c:LinComb) [] */
ClaireBoolean * choco_filterOnImprovedUpperBound_LinComb(LinComb *c)
{ { ClaireBoolean *Result ;
    { int  myub = (*choco.computeUpperBound)(_oid_(c));
      if (c->op == 1)
       Result = OBJECT(ClaireBoolean,(*choco.propagateNewUpperBound)(_oid_(c),
        myub));
      else if (c->op == 2)
       Result = OBJECT(ClaireBoolean,(*choco.propagateNewUpperBound)(_oid_(c),
        myub));
      else { Result = ((myub == 0) ?
            OBJECT(ClaireBoolean,(*choco.propagateNewLowerBound)(_oid_(c),
              (((*choco.computeLowerBound)(_oid_(c)))+1))) :
            CFALSE );
          } 
        } 
    return (Result);} 
  } 


// v1.0
// Note: additional propagation pass are sometimes useful:
// For instance : 3*X[0.3] + Y[1.10] = 10
//                Y >= 2 causes X < 3 -> updateSup(X,2)
//                and this very event (the new sup of X) generates causes (Y >= 4).
//                this induced event (Y>=4) could not be infered at first (with only Y>=2)
//
// a strategy for chaotic iteration with two rules (LB and UB propagation)
// the fix point is reached individually for each rule in one function call
// but this call may break the stability condition for the other rule (in which case
// the second rule infers new information from the fresh inferences made by the first rule).
// The algorithm oscilates between both rules until a global fix point is reached.
// Conjecture: the maximum number of oscillations is two.
// parameters: startWithLB -> whether LB must be the first rule applied
//             MinNbRules  -> minimum number of rules required to reach fiux point.
/* The c++ function for: filter(c:LinComb,startWithLB:boolean,minNbRules:integer) [] */
void  choco_filter_LinComb(LinComb *c,ClaireBoolean *startWithLB,int minNbRules)
{ { ClaireBoolean * lastRuleEffective = CTRUE;
    int  nbr = 0;
    ClaireBoolean * nextRuleIsLB = startWithLB;
    { while (((lastRuleEffective == CTRUE) || 
          (nbr < minNbRules)))
      { if (nextRuleIsLB == CTRUE)
         { if (choco.GPTALK->value <= ClEnv->verbose)
           tformat_string("  -- LB propagation for ~S \n",choco.GPTALK->value,list::alloc(1,_oid_(c)));
          else ;lastRuleEffective= choco_filterOnImprovedLowerBound_LinComb(c);
          } 
        else { if (choco.GPTALK->value <= ClEnv->verbose)
             tformat_string("  -- UB propagation for ~S \n",choco.GPTALK->value,list::alloc(1,_oid_(c)));
            else ;lastRuleEffective= choco_filterOnImprovedUpperBound_LinComb(c);
            } 
          nextRuleIsLB= not_any(_oid_(nextRuleIsLB));
        ++nbr;
        } 
      } 
    STOREO(c->improvedLowerBound,CFALSE);
    STOREO(c->improvedUpperBound,CFALSE);
    } 
  } 


// v1.015: all right these methods are abstract for Palm, but they are now fast dispatched
// v0.33 <thb>: this same function is used for propagating immediately or delayed
/* The c++ function for: propagate(c:LinComb) [] */
void  choco_propagate_LinComb(LinComb *c)
{ if ((c->improvedLowerBound == CTRUE) && 
      (c->improvedUpperBound == CTRUE))
   choco_filter_LinComb(c,CTRUE,2);
  else if (c->improvedLowerBound == CTRUE)
   choco_filter_LinComb(c,CTRUE,1);
  else if (c->improvedUpperBound == CTRUE)
   choco_filter_LinComb(c,CFALSE,1);
  } 


// apply UB rule at least once and ocsillate if this produces new inferences
// start with the LB rule, continue with UB and oscillate until no more information can be drawn
/* The c++ function for: awake(c:LinComb) [] */
void  choco_awake_LinComb_choco(LinComb *c)
{ choco_filter_LinComb(c,CTRUE,2);
  } 


// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: askIfEntailed(c:LinComb) [] */
OID  choco_askIfEntailed_LinComb(LinComb *c)
{ { OID Result = 0;
    if (c->op == 1)
     { int  a = (*choco.computeLowerBound)(_oid_(c));
      int  b = (*choco.computeUpperBound)(_oid_(c));
      if ((b < 0) || 
          (a > 0))
       Result = Kernel.cfalse;
      else if ((a == 0) && 
          (b == 0))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    else if (c->op == 2)
     { if ((*choco.computeUpperBound)(_oid_(c)) < 0)
       Result = Kernel.cfalse;
      else if (0 <= (*choco.computeLowerBound)(_oid_(c)))
       Result = Kernel.ctrue;
      else Result = CNULL;
        } 
    else { { int  a = (*choco.computeLowerBound)(_oid_(c));
          if (a > 0)
           Result = Kernel.ctrue;
          else { int  b = (*choco.computeUpperBound)(_oid_(c));
              if (b < 0)
               Result = Kernel.ctrue;
              else if ((b == 0) && 
                  (a == 0))
               Result = Kernel.cfalse;
              else Result = Kernel.ctrue;
                } 
            } 
        } 
      return (Result);} 
  } 


// v1.010: introduce disequalities (NEQ: sigma(ai xi) + c !== 0)
/* The c++ function for: testIfSatisfied(c:LinComb) [] */
ClaireBoolean * choco_testIfSatisfied_LinComb(LinComb *c)
{ ;{ ClaireBoolean *Result ;
    { int  s = 0;
      { int  i = 1;
        int  g0580 = c->nbVars;
        { while ((i <= g0580))
          { s= (s+(OBJECT(IntVar,(*(c->vars))[i])->value*(((OID *) c->coefs)[i])));
            ++i;
            } 
          } 
        } 
      s= (s+c->cste);
      if (c->op == 1)
       Result = ((s == 0) ? CTRUE : CFALSE);
      else if (c->op == 2)
       Result = ((0 <= s) ? CTRUE : CFALSE);
      else { Result = ((s != 0) ? CTRUE : CFALSE);
          } 
        } 
    return (Result);} 
  } 


// TODO: when the lists nextConstOnInf & nextConstOnSUp will be merged into a single nextConstOnBound,
// the optimization from assignIndices (wake linear Inequalities only on half the bound events)
// should be moved here, on abstractXXXX and awakeOnXXXX methods
// v0.30 explicit event abstractions required by the Delayer object
// v0.39 <thb> explicitly returns a boolean (a := b no longer returns b)
/* The c++ function for: abstractIncInf(c:LinComb,i:integer) [] */
ClaireBoolean * choco_abstractIncInf_LinComb(LinComb *c,int i)
{ if (i <= c->nbPosVars)
   STOREO(c->improvedLowerBound,CTRUE);
  else STOREO(c->improvedUpperBound,CTRUE);
    return (CTRUE);} 


/* The c++ function for: abstractDecSup(c:LinComb,i:integer) [] */
ClaireBoolean * choco_abstractDecSup_LinComb(LinComb *c,int i)
{ if (i <= c->nbPosVars)
   STOREO(c->improvedUpperBound,CTRUE);
  else STOREO(c->improvedLowerBound,CTRUE);
    return (CTRUE);} 


/* The c++ function for: abstractInstantiate(c:LinComb,i:integer) [] */
ClaireBoolean * choco_abstractInstantiate_LinComb(LinComb *c,int i)
{ STOREO(c->improvedLowerBound,CTRUE);
  STOREO(c->improvedUpperBound,CTRUE);
  return (CTRUE);} 


/* The c++ function for: abstractRemoveVal(c:LinComb,i:integer,v:integer) [] */
ClaireBoolean * choco_abstractRemoveVal_LinComb(LinComb *c,int i,int v)
{ if (i <= c->nbPosVars)
   STOREO(c->improvedLowerBound,CTRUE);
  else STOREO(c->improvedUpperBound,CTRUE);
    return (CTRUE);} 


/* The c++ function for: awakeOnInf(c:LinComb,idx:integer) [] */
void  choco_awakeOnInf_LinComb(LinComb *c,int idx)
{ if (idx <= c->nbPosVars)
   choco_filter_LinComb(c,CTRUE,1);
  else choco_filter_LinComb(c,CFALSE,1);
    } 


/* The c++ function for: awakeOnSup(c:LinComb,idx:integer) [] */
void  choco_awakeOnSup_LinComb(LinComb *c,int idx)
{ if (idx <= c->nbPosVars)
   choco_filter_LinComb(c,CFALSE,1);
  else choco_filter_LinComb(c,CTRUE,1);
    } 


/* The c++ function for: awakeOnInst(c:LinComb,idx:integer) [] */
void  choco_awakeOnInst_LinComb(LinComb *c,int idx)
{ (*choco.awake)(_oid_(c));
  } 


// claire3 port: remove that ugly ptach for fast dispatch of the main methods (no more register)