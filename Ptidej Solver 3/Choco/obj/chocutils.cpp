/***** CLAIRE Compilation of file C:\Docume~1\Yann\Work\Ptidej~2\Choco\v1.324\chocutils.cl 
         [version 3.2.52 / safety 5] Thu Feb 13 21:07:24 2003 *****/

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>

// ********************************************************************
// * CHOCO, version 1.330 sept. 9th 2002                              *
// * file: utils.cl                                                   *
// *    common utilities & data structures                            *
// * Copyright (C) F. Laburthe, 1999-2002, see readme.txt             *
// ********************************************************************
// ------------------  File Overview  ---------------------------------
// *   Part 1: Global parameters                                      *
// *   Part 2: simple utilities (min,max,etc.)                        *
// *   Part 3: data structure utilities                               *
// *   Part 3: The generic object hierarchy                           *
// *   Part 4: Variables                                              *
// *   Part 5: Propagation events                                     *
// *   Part 6: Constraints (objects, creation, generic methods)       *
// *   Part 7: compiler optimization                                  *
// *   Part 8: Problems & constraint networks                         *
// --------------------------------------------------------------------
// ********************************************************************
// *   Part 1: simple utilities (min,max,etc.)                        *
// ********************************************************************
// utils: integer divisions (rounded down for div- and up for div+)
// we suppose that b is always > 0
// (too bad: the integer division of C (and so of Claire) is rounded
//  up for negative integers and down for positive ones ....)
//
/* The c++ function for: div-(a:integer,b:integer) [] */
int  choco_div_dash_integer(int a,int b)
{ ;{ int Result = 0;
    if (b < 0)
     Result = choco_div_dash_integer((-a),(-b));
    else { int  r = (a/b);
        Result = (((0 <= a) || 
            ((r*b) == a)) ?
          r :
          (r-1) );
        } 
      return (Result);} 
  } 


/* The c++ function for: div+(a:integer,b:integer) [] */
int  choco_div_plus_integer(int a,int b)
{ ;{ int Result = 0;
    if (b < 0)
     Result = choco_div_plus_integer((-a),(-b));
    else { int  r = (a/b);
        Result = (((a <= 0) || 
            ((r*b) == a)) ?
          r :
          (r+1) );
        } 
      return (Result);} 
  } 


// the largest bound for integers are [-1073741823, 1073741823],
// but we use a slightly restricted range, with easier to recognize values
// 0.34: MAXINT - MININT no longer produces an overflow
// utils specific for claire3 port
/* The c++ function for: max(x:integer,y:integer) [] */
int  claire_max_integer2(int x,int y)
{ { int Result = 0;
    Result = ((y <= x) ?
      x :
      y );
    return (Result);} 
  } 


/* The c++ function for: min(x:integer,y:integer) [] */
int  claire_min_integer2(int x,int y)
{ { int Result = 0;
    Result = ((y <= x) ?
      y :
      x );
    return (Result);} 
  } 


// Utils : maximum and minimum of a collection of integers
// v1.02 lowercase those function names
/* The c++ function for: max(x:(list[integer] U set[integer])) [] */
int  claire_max_bag(bag *x)
{ { int Result = 0;
    { int  s = -99999999;
      { ITERATE(y);
        for (START(x); NEXT(y);)
        s= ((y <= s) ?
          s :
          y );
        } 
      Result = s;
      } 
    return (Result);} 
  } 


/* The c++ function for: min(x:(list[integer] U set[integer])) [] */
int  claire_min_bag(bag *x)
{ { int Result = 0;
    { int  s = 99999999;
      { ITERATE(y);
        for (START(x); NEXT(y);)
        s= ((y <= s) ?
          y :
          s );
        } 
      Result = s;
      } 
    return (Result);} 
  } 


/* The c++ function for: sum(x:(list[integer] U set[integer])) [] */
int  claire_sum_bag(bag *x)
{ { int Result = 0;
    { int  s = 0;
      { ITERATE(y);
        for (START(x); NEXT(y);)
        s= (s+y);
        } 
      Result = s;
      } 
    return (Result);} 
  } 


/* The c++ function for: product(x:(list[integer] U set[integer])) [] */
int  claire_product_bag(bag *x)
{ { int Result = 0;
    { int  p = 1;
      { ITERATE(y);
        for (START(x); NEXT(y);)
        p= (p*y);
        } 
      Result = p;
      } 
    return (Result);} 
  } 


/* The c++ function for: count(S:any) [] */
int  claire_count_any(OID S)
{ GC_BIND;
  { int Result = 0;
    { int  s = 0;
      { OID gc_local;
        ITERATE(_Zx);
        bag *_Zx_support;
        _Zx_support = GC_OBJECT(bag,enumerate_any(S));
        for (START(_Zx_support); NEXT(_Zx);)
        { GC_LOOP;
          ++s;
          GC_UNLOOP;} 
        } 
      Result = s;
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: abs(v:integer) [] */
int  claire_abs_integer(int v)
{ { int Result = 0;
    Result = ((v < 0) ?
      (-v) :
      v );
    return (Result);} 
  } 


/* The c++ function for: abs(g0000:any) [] */
OID  claire_abs_float_(OID g0000)
{ return _float_(claire_abs_float(float_v(g0000)));} 


/* The c++ function for: abs(v:float) [] */
double  claire_abs_float(double v)
{ { double Result =0.0;
    Result = ((v < 0.0) ?
      _dash_float2(v) :
      v );
    return (Result);} 
  } 


/* The c++ function for: stringFormat(n:integer,k:integer) [] */
char * claire_stringFormat_integer(int n,int k)
{ if (k <= 0) 
  { { char *Result ;
      Result = "";
      return (Result);} 
     } 
  else{ if (n < 0) 
    { { char *Result ;
        Result = append_string("-",GC_STRING(claire_stringFormat_integer((-n),(k-1))));
        return (Result);} 
       } 
    else{ if (n == 0) 
      { { char *Result ;
          Result = make_string_integer(((0 <= k) ?
            k :
            0 ),_char_(' '));
          return (Result);} 
         } 
      else{ if (n <= 9) 
        { { char *Result ;
            Result = append_string(GC_STRING(string_I_integer (n)),GC_STRING(claire_stringFormat_integer(0,(k-1))));
            return (Result);} 
           } 
        else{ if (n <= 99) 
          { { char *Result ;
              Result = append_string(GC_STRING(string_I_integer (n)),GC_STRING(claire_stringFormat_integer(0,(k-2))));
              return (Result);} 
             } 
          else{ if (n <= 999) 
            { { char *Result ;
                Result = append_string(GC_STRING(string_I_integer (n)),GC_STRING(claire_stringFormat_integer(0,(k-3))));
                return (Result);} 
               } 
            else{ GC_BIND;
              { char *Result ;
                Result = append_string(GC_STRING(string_I_integer (n)),GC_STRING(claire_stringFormat_integer(0,(k-4))));
                GC_UNBIND; return (Result);} 
              } 
            } 
          } 
        } 
      } 
    } 
  } 


/* The c++ function for: stringFormat(s:string,k:integer) [] */
char * claire_stringFormat_string(char *s,int k)
{ if (k <= strlen(s)) 
  { { char *Result ;
      Result = substring_string(s,1,k);
      return (Result);} 
     } 
  else{ GC_BIND;
    { char *Result ;
      Result = append_string(s,GC_STRING(make_string_integer((k-strlen(s)),_char_(' '))));
      GC_UNBIND; return (Result);} 
    } 
  } 


/* The c++ function for: random(l:list) [] */
OID  claire_random_list(list *l)
{ { OID Result = 0;
    { int  n = l->length;
      Result = (*(l))[(1+random_integer(n))];
      } 
    return (Result);} 
  } 


// 0.26 casts for improved compilation
// claire3 port: module System has disappeared
/* The c++ function for: random(I:Interval) [] */
int  claire_random_Interval(Interval *I)
{ return ((I->arg1+random_integer(((1+I->arg2)-I->arg1))));} 


/* The c++ function for: random(a:integer,b:integer) [] */
int  claire_random_integer2(int a,int b)
{ ;return ((a+random_integer(((b-a)+1))));} 


// used in interpreted mode (the optimized iterator is already in Claire)
/* The c++ function for: /+(x:Interval,y:Interval) [] */
list * claire__7_plus_Interval1(Interval *x,Interval *y)
{ GC_BIND;
  { list *Result ;
    Result = append_list(GC_OBJECT(list,list_I_set(GC_OBJECT(set,set_I_Interval(x)))),GC_OBJECT(list,list_I_set(GC_OBJECT(set,set_I_Interval(y)))));
    GC_UNBIND; return (Result);} 
  } 


// v0.35
/* The c++ function for: /+(x:Interval,y:set) [] */
list * claire__7_plus_Interval2(Interval *x,set *y)
{ GC_BIND;
  { list *Result ;
    Result = append_list(GC_OBJECT(list,list_I_set(GC_OBJECT(set,set_I_Interval(x)))),GC_OBJECT(list,list_I_set(y)));
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: /+(x:set,y:Interval) [] */
list * claire__7_plus_set2(set *x,Interval *y)
{ GC_BIND;
  { list *Result ;
    Result = append_list(GC_OBJECT(list,list_I_set(x)),GC_OBJECT(list,list_I_set(GC_OBJECT(set,set_I_Interval(y)))));
    GC_UNBIND; return (Result);} 
  } 


// mapping booleans to -1/+1 coefficients
/* The c++ function for: integer!(b:boolean) [] */
int  claire_integer_I_boolean(ClaireBoolean *b)
{ { int Result = 0;
    Result = ((b == CTRUE) ?
      1 :
      -1 );
    return (Result);} 
  } 


// 0.26 type integer result
/* The c++ function for: knownInt(x:integer) [] */
ClaireBoolean * choco_knownInt_integer(int x)
{ return (_I_equal_any(x,-100000000));} 


// ********************************************************************
// *   Part 2: backtrackable integers                                 *
// ********************************************************************
// root class for all generic data structure utilities
// utility: storing a backtrackable integer with a time stamp, so that
// only one update is recorded per world
// (cf Claire documentation, end of Part 2, page 15)
// new in v0.37
// index of the latest world in which the StoredInt was modified
/* The c++ function for: self_print(x:StoredInt) [] */
void  claire_self_print_StoredInt_choco(StoredInt *x)
{ princ_integer(x->latestValue);
  } 


/* The c++ function for: write(x:StoredInt,y:integer) [] */
void  claire_write_StoredInt(StoredInt *x,int y)
{ if (y != x->latestValue)
   { int  currentWorld = world_number();
    if (currentWorld > x->latestUpdate)
     { STOREI(x->latestValue,y);
      STOREI(x->latestUpdate,currentWorld);
      } 
    else (x->latestValue = y);
      } 
  } 


/* The c++ function for: read(x:StoredInt) [] */
int  claire_read_StoredInt(StoredInt *x)
{ return (x->latestValue);} 


// ********************************************************************
// *   Part 3: Matrices                                               *
// ********************************************************************
// creating unnamed arrays
// creates a bi-dimensional matrix storing values
// for pairs of indices in (a1 .. a2, b1 .. b2)
/* The c++ function for: make2DBoolMatrix(a1:integer,a2:integer,b1:integer,b2:integer,default:boolean,stored:boolean) [] */
BoolMatrix2D * choco_make2DBoolMatrix_integer(int a1,int a2,int b1,int b2,ClaireBoolean *DEFAULT,ClaireBoolean *stored)
{ GC_BIND;
  { BoolMatrix2D *Result ;
    { int  s1 = ((a2-a1)+1);
      int  s2 = ((b2-b1)+1);
      { BoolMatrix2D * _CL_obj = ((BoolMatrix2D *) GC_OBJECT(BoolMatrix2D,new_object_class(choco._BoolMatrix2D)));
        (_CL_obj->contents = make_array_integer((s1*s2),Kernel._boolean,_oid_(DEFAULT)));
        (_CL_obj->backtrackable = stored);
        (_CL_obj->size1 = s1);
        (_CL_obj->size2 = s2);
        (_CL_obj->offset1 = a1);
        (_CL_obj->offset2 = b1);
        Result = _CL_obj;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


/* The c++ function for: make2DIntMatrix(a1:integer,a2:integer,b1:integer,b2:integer,default:integer,stored:boolean) [] */
IntMatrix2D * choco_make2DIntMatrix_integer(int a1,int a2,int b1,int b2,int DEFAULT,ClaireBoolean *stored)
{ GC_BIND;
  { IntMatrix2D *Result ;
    { int  s1 = ((a2-a1)+1);
      int  s2 = ((b2-b1)+1);
      { IntMatrix2D * _CL_obj = ((IntMatrix2D *) GC_OBJECT(IntMatrix2D,new_object_class(choco._IntMatrix2D)));
        (_CL_obj->contents = make_array_integer((s1*s2),Kernel._integer,DEFAULT));
        (_CL_obj->backtrackable = stored);
        (_CL_obj->size1 = s1);
        (_CL_obj->size2 = s2);
        (_CL_obj->offset1 = a1);
        (_CL_obj->offset2 = a2);
        Result = _CL_obj;
        } 
      } 
    GC_UNBIND; return (Result);} 
  } 


// claire3 port: module System has disappeared, type arrays
/* The c++ function for: makeNDBoolMatrix(l:list[Interval],default:boolean,stored:boolean) [] */
BoolMatrixND * choco_makeNDBoolMatrix_list(list *l,ClaireBoolean *DEFAULT,ClaireBoolean *stored)
{ GC_BIND;
  { BoolMatrixND *Result ;
    { int  n = l->length;
      list * ls;
      { { bag *v_list; OID v_val;
          OID itv,CLcount;
          v_list = l;
           ls = v_list->clone(Kernel._integer);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { itv = (*(v_list))[CLcount];
            v_val = ((OBJECT(Interval,itv)->arg2-OBJECT(Interval,itv)->arg1)+1);
            
            (*((list *) ls))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,ls);} 
      BoolMatrixND * _CL_obj = ((BoolMatrixND *) GC_OBJECT(BoolMatrixND,new_object_class(choco._BoolMatrixND)));
      { BoolMatrixND * g0003; 
        OID * g0004;
        g0003 = _CL_obj;
        { int  g0005UU;
          { int  g0001 = 1;
            { ITERATE(g0002);
              for (START(ls); NEXT(g0002);)
              g0001= (g0001*g0002);
              } 
            g0005UU = g0001;
            } 
          g0004 = make_array_integer(g0005UU,Kernel._boolean,_oid_(DEFAULT));
          } 
        (g0003->contents = g0004);} 
      (_CL_obj->backtrackable = stored);
      (_CL_obj->dim = n);
      (_CL_obj->lsizes = array_I_list(ls));
      { MatrixND * g0006; 
        OID * g0007;
        g0006 = _CL_obj;
        { list * g0008UU;
          { { bag *v_list; OID v_val;
              OID itv,CLcount;
              v_list = l;
               g0008UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { itv = (*(v_list))[CLcount];
                v_val = OBJECT(Interval,itv)->arg1;
                
                (*((list *) g0008UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0008UU);} 
          g0007 = array_I_list(g0008UU);
          } 
        (g0006->offsetArray = g0007);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// claire3 port: module System has disappeared, type arrays
/* The c++ function for: makeNDIntMatrix(l:list[Interval],default:integer,stored:boolean) [] */
IntMatrixND * choco_makeNDIntMatrix_list(list *l,int DEFAULT,ClaireBoolean *stored)
{ GC_BIND;
  { IntMatrixND *Result ;
    { int  n = l->length;
      list * ls;
      { { bag *v_list; OID v_val;
          OID itv,CLcount;
          v_list = l;
           ls = v_list->clone(Kernel._integer);
          for (CLcount= 1; CLcount <= v_list->length; CLcount++)
          { itv = (*(v_list))[CLcount];
            v_val = ((OBJECT(Interval,itv)->arg2-OBJECT(Interval,itv)->arg1)+1);
            
            (*((list *) ls))[CLcount] = v_val;} 
          } 
        GC_OBJECT(list,ls);} 
      IntMatrixND * _CL_obj = ((IntMatrixND *) GC_OBJECT(IntMatrixND,new_object_class(choco._IntMatrixND)));
      { IntMatrixND * g0011; 
        OID * g0012;
        g0011 = _CL_obj;
        { int  g0013UU;
          { int  g0009 = 1;
            { ITERATE(g0010);
              for (START(ls); NEXT(g0010);)
              g0009= (g0009*g0010);
              } 
            g0013UU = g0009;
            } 
          g0012 = make_array_integer(g0013UU,Kernel._integer,DEFAULT);
          } 
        (g0011->contents = g0012);} 
      (_CL_obj->backtrackable = stored);
      (_CL_obj->dim = n);
      (_CL_obj->lsizes = array_I_list(ls));
      { MatrixND * g0014; 
        OID * g0015;
        g0014 = _CL_obj;
        { list * g0016UU;
          { { bag *v_list; OID v_val;
              OID itv,CLcount;
              v_list = l;
               g0016UU = v_list->clone(Kernel._integer);
              for (CLcount= 1; CLcount <= v_list->length; CLcount++)
              { itv = (*(v_list))[CLcount];
                v_val = OBJECT(Interval,itv)->arg1;
                
                (*((list *) g0016UU))[CLcount] = v_val;} 
              } 
            GC_OBJECT(list,g0016UU);} 
          g0015 = array_I_list(g0016UU);
          } 
        (g0014->offsetArray = g0015);} 
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// matrices of booleans or integer in dimension 2
/* The c++ function for: flatIndex(m:Matrix2D,i:integer,j:integer) [] */
int  choco_flatIndex_Matrix2D(Matrix2D *m,int i,int j)
{ ;;;;return ((((m->size1*(j-m->offset2))+(i-m->offset1))+1));} 


/* The c++ function for: read(m:BoolMatrix2D,i:integer,j:integer) [] */
ClaireBoolean * claire_read_BoolMatrix2D(BoolMatrix2D *m,int i,int j)
{ return (OBJECT(ClaireBoolean,((OID *) m->contents)[choco_flatIndex_Matrix2D(m,i,j)]));} 


/* The c++ function for: store(m:BoolMatrix2D,i:integer,j:integer,x:boolean) [] */
void  claire_store_BoolMatrix2D(BoolMatrix2D *m,int i,int j,ClaireBoolean *x)
{ store_array(m->contents,choco_flatIndex_Matrix2D(m,i,j),_oid_(x),m->backtrackable);
  } 


/* The c++ function for: read(m:IntMatrix2D,i:integer,j:integer) [] */
int  claire_read_IntMatrix2D(IntMatrix2D *m,int i,int j)
{ return (((OID *) m->contents)[choco_flatIndex_Matrix2D(m,i,j)]);} 


/* The c++ function for: store(m:IntMatrix2D,i:integer,j:integer,x:integer) [] */
void  claire_store_IntMatrix2D(IntMatrix2D *m,int i,int j,int x)
{ store_array(m->contents,choco_flatIndex_Matrix2D(m,i,j),x,m->backtrackable);
  } 


// matrices of booleans or integer in arbitrary dimensions
/* The c++ function for: flatIndex(m:MatrixND,l:list[integer]) [] */
int  choco_flatIndex_MatrixND(MatrixND *m,list *l)
{ ;;{ int Result = 0;
    { int  n = l->length;
      OID * loff = m->offsetArray;
      int  s = (((*(l))[n])-(((OID *) loff)[n]));
      { int  i = 1;
        int  g0017 = (n-1);
        { while ((i <= g0017))
          { { int  ithCoordinate = (((*(l))[(n-i)])-(((OID *) loff)[(n-i)]));
              s= ((s*(((OID *) m->lsizes)[(n-i)]))+ithCoordinate);
              } 
            ++i;
            } 
          } 
        } 
      Result = (s+1);
      } 
    return (Result);} 
  } 


/* The c++ function for: read(m:BoolMatrixND,l:list[integer]) [] */
ClaireBoolean * claire_read_BoolMatrixND(BoolMatrixND *m,list *l)
{ return (OBJECT(ClaireBoolean,((OID *) m->contents)[choco_flatIndex_MatrixND(m,l)]));} 


/* The c++ function for: store(m:BoolMatrixND,l:list[integer],x:boolean) [] */
void  claire_store_BoolMatrixND(BoolMatrixND *m,list *l,ClaireBoolean *x)
{ store_array(m->contents,choco_flatIndex_MatrixND(m,l),_oid_(x),m->backtrackable);
  } 


/* The c++ function for: read(m:IntMatrixND,l:list[integer]) [] */
int  claire_read_IntMatrixND(IntMatrixND *m,list *l)
{ return (((OID *) m->contents)[choco_flatIndex_MatrixND(m,l)]);} 


/* The c++ function for: store(m:IntMatrixND,l:list[integer],x:integer) [] */
void  claire_store_IntMatrixND(IntMatrixND *m,list *l,int x)
{ store_array(m->contents,choco_flatIndex_MatrixND(m,l),x,m->backtrackable);
  } 


// ********************************************************************
// *   Part 4: associations of keys to values from a set              *
// ********************************************************************
// utilities for associating annotations to each value for a set
//  an abstract class for associating a (backtrackable) annotation to each value from a set of integers
// size of the nbSupport vectors
/* The c++ function for: getOriginalMin(annot:IntSetAnnotation) [] */
int  choco_getOriginalMin_IntSetAnnotation(IntSetAnnotation *annot)
{ return (annot->offset);} 


/* The c++ function for: getOriginalMax(annot:IntSetAnnotation) [] */
int  choco_getOriginalMax_IntSetAnnotation(IntSetAnnotation *annot)
{ return (((annot->offset+annot->asize)-1));} 


// subclass for integer annotations   
// get/set methods
/* The c++ function for: getIntAnnotation(ida:IntSetIntAnnotation,val:integer) [] */
int  choco_getIntAnnotation_IntSetIntAnnotation(IntSetIntAnnotation *ida,int val)
{ return (((OID *) ida->intValues)[(val-ida->offset)]);} 


/* The c++ function for: setIntAnnotation(ida:IntSetIntAnnotation,val:integer,annot:integer) [] */
void  choco_setIntAnnotation_IntSetIntAnnotation(IntSetIntAnnotation *ida,int val,int annot)
{ STOREI(ida->intValues[(val-ida->offset)],annot);
  } 


// constructor 
/* The c++ function for: makeIntSetIntAnnotation(min:integer,max:integer,def:integer) [] */
IntSetIntAnnotation * choco_makeIntSetIntAnnotation_integer(int min,int max,int def)
{ GC_BIND;
  { IntSetIntAnnotation *Result ;
    { int  n = ((max-min)+1);
      IntSetIntAnnotation * _CL_obj = ((IntSetIntAnnotation *) GC_OBJECT(IntSetIntAnnotation,new_object_class(choco._IntSetIntAnnotation)));
      (_CL_obj->offset = (min-1));
      (_CL_obj->asize = n);
      (_CL_obj->intValues = make_array_integer(n,Kernel._integer,def));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// subclass for boolean annotations   
// get/set methods
/* The c++ function for: getBoolAnnotation(ida:IntSetBoolAnnotation,val:integer) [] */
ClaireBoolean * choco_getBoolAnnotation_IntSetBoolAnnotation(IntSetBoolAnnotation *ida,int val)
{ return (OBJECT(ClaireBoolean,((OID *) ida->boolValues)[(val-ida->offset)]));} 


/* The c++ function for: setBoolAnnotation(ida:IntSetBoolAnnotation,val:integer,annot:boolean) [] */
void  choco_setBoolAnnotation_IntSetBoolAnnotation(IntSetBoolAnnotation *ida,int val,ClaireBoolean *annot)
{ STOREI(ida->boolValues[(val-ida->offset)],_oid_(annot));
  } 


// constructor 
/* The c++ function for: makeIntSetBoolAnnotation(min:integer,max:integer,default:boolean) [] */
IntSetBoolAnnotation * choco_makeIntSetBoolAnnotation_integer(int min,int max,ClaireBoolean *DEFAULT)
{ GC_BIND;
  { IntSetBoolAnnotation *Result ;
    { int  n = ((max-min)+1);
      IntSetBoolAnnotation * _CL_obj = ((IntSetBoolAnnotation *) GC_OBJECT(IntSetBoolAnnotation,new_object_class(choco._IntSetBoolAnnotation)));
      (_CL_obj->offset = (min-1));
      (_CL_obj->asize = n);
      (_CL_obj->boolValues = make_array_integer(n,Kernel._boolean,_oid_(DEFAULT)));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// ********************************************************************
// *   Part 5: Bipartite sets                                         *
// ********************************************************************
//  <thierry Benoist> v0.9906
// This data structure contains a set of values divided into two parts (left and right).
// A loop over one part takes a time proportionnal to the size of this part (an not to the total size of the set).
// Moving one element from one part to the other takes the time of getting this element in the set through a hastable.
//hashtable: indices[objs[i]] = i 
// util: build a table with:
//       - domain: 'memberType'
//       - range: integer 
//       - defaultValue:0
/* The c++ function for: makeIndexesTable(memberType:type) [] */
table * choco_makeIndexesTable_type(ClaireType *memberType)
{ { table *Result ;
    { table * t = ((table *) new_object_class(Kernel._table));
      (t->range = Kernel._integer);
      (t->domain = memberType);
      (t->DEFAULT = 0);
      (t->graph = make_list_integer(29,CNULL));
      Result = t;
      } 
    return (Result);} 
  } 


/* The c++ function for: makeBipartiteSet(memberType:type,default:any) [] */
BipartiteSet * choco_makeBipartiteSet_type(ClaireType *memberType,OID DEFAULT)
{ GC_BIND;
  { BipartiteSet *Result ;
    { BipartiteSet * _CL_obj = ((BipartiteSet *) GC_OBJECT(BipartiteSet,new_object_class(choco._BipartiteSet)));
      (_CL_obj->defaultValue = DEFAULT);
      (_CL_obj->objs = make_array_integer(0,memberType,DEFAULT));
      (_CL_obj->nbLeft = 0);
      (_CL_obj->indices = choco_makeIndexesTable_type(memberType));
      Result = _CL_obj;
      } 
    GC_UNBIND; return (Result);} 
  } 


// Create an empty typed BipartiteSet
// v1.06: add a parameter
// Create a BipartiteSet from a left part and a right part
// v1.06: add a parameter
// Exchange elements at indices idx1 and idx2.
/* The c++ function for: swap(b:BipartiteSet,idx1:integer,idx2:integer) [] */
void  choco_swap_BipartiteSet(BipartiteSet *b,int idx1,int idx2)
{ if (idx1 != idx2) 
  { { OID  obj1 = GC_OID(nth_array(b->objs,idx1));
      OID  obj2 = GC_OID(nth_array(b->objs,idx2));
      nth_put_array(b->objs,idx1,obj2);
      nth_put_array(b->objs,idx2,obj1);
      nth_equal_table1(b->indices,obj1,idx2);
      nth_equal_table1(b->indices,obj2,idx1);
      } 
     } 
  else{ GC_BIND;
    ;GC_UNBIND;} 
  } 


// Move an object to the left part of the set.
// Throws an error if the bipartite set does not contain this object.
// claire 3 port: there seems to a bug with second order types
/* The c++ function for: moveLeft(b:BipartiteSet,obj:any) [] */
void  choco_moveLeft_BipartiteSet(BipartiteSet *b,OID obj)
{ { int  idx = nth_table1(b->indices,obj);
    if (idx == 0)
     close_exception(((general_error *) (*Core._general_error)(_string_("~S does not contain ~S"),
      _oid_(list::alloc(2,_oid_(b),obj)))));
    else if (idx > b->nbLeft)
     { choco_swap_BipartiteSet(b,idx,(b->nbLeft+1));
      (b->nbLeft = (b->nbLeft+1));
      } 
    } 
  } 


// Move an object to the right part of the set.
// Throws an error if the bipartite set does not contain this object.   
/* The c++ function for: moveRight(b:BipartiteSet,obj:any) [] */
void  choco_moveRight_BipartiteSet(BipartiteSet *b,OID obj)
{ { int  idx = nth_table1(b->indices,obj);
    if (idx == 0)
     close_exception(((general_error *) (*Core._general_error)(_string_("~S does not contain ~S"),
      _oid_(list::alloc(2,_oid_(b),obj)))));
    else if (idx <= b->nbLeft)
     { choco_swap_BipartiteSet(b,idx,b->nbLeft);
      (b->nbLeft = (b->nbLeft-1));
      } 
    } 
  } 


// Move all element to the left part of the set.   
/* The c++ function for: moveAllLeft(b:BipartiteSet) [] */
void  choco_moveAllLeft_BipartiteSet(BipartiteSet *b)
{ (b->nbLeft = b->objs[0]);
  } 


// Move all element to the right part of the set.   
/* The c++ function for: moveAllRight(b:BipartiteSet) [] */
void  choco_moveAllRight_BipartiteSet(BipartiteSet *b)
{ (b->nbLeft = 0);
  } 


// adding a new object to the set
/* The c++ function for: addRight(b:BipartiteSet,obj:any) [] */
void  choco_addRight_BipartiteSet(BipartiteSet *b,OID obj)
{ GC_BIND;
  { OID * oldobjs = GC_ARRAY(b->objs);
    (b->objs = make_array_integer((oldobjs[0]+1),Kernel._object,GC_OID(b->defaultValue)));
    { int  i = 1;
      int  g0018 = oldobjs[0];
      { OID gc_local;
        while ((i <= g0018))
        { // HOHO, GC_LOOP not needed !
          nth_put_array(b->objs,i,nth_array(oldobjs,i));
          ++i;
          } 
        } 
      } 
    } 
  nth_put_array(b->objs,b->objs[0],obj);
  nth_equal_table1(b->indices,obj,b->objs[0]);
  GC_UNBIND;} 


/* The c++ function for: addLeft(b:BipartiteSet,obj:any) [] */
void  choco_addLeft_BipartiteSet(BipartiteSet *b,OID obj)
{ choco_addRight_BipartiteSet(b,obj);
  choco_moveLeft_BipartiteSet(b,obj);
  } 


// Returns true if the specified object belongs to the left part of the set.
// Returns false if the specified object belongs to the right part of the set.
// Throws an error if the bipartite set does not contain this object.   
/* The c++ function for: isLeft(b:BipartiteSet,obj:any) [] */
ClaireBoolean * choco_isLeft_BipartiteSet(BipartiteSet *b,OID obj)
{ { ClaireBoolean *Result ;
    { ClaireObject *V_CC ;
      { int  idx = nth_table1(b->indices,obj);
        if (idx == 0)
         close_exception(((general_error *) (*Core._general_error)(_string_("~S does not contain ~S"),
          _oid_(list::alloc(2,_oid_(b),obj)))));
        else V_CC = ((idx <= b->nbLeft) ? CTRUE : CFALSE);
          } 
      Result= (ClaireBoolean *) V_CC;} 
    return (Result);} 
  } 


// v0.9907
// returns true if the object is present in the bipartition
/* The c++ function for: isIn(b:BipartiteSet,obj:any) [] */
ClaireBoolean * choco_isIn_BipartiteSet(BipartiteSet *b,OID obj)
{ return (_I_equal_any(nth_table1(b->indices,obj),0));} 


// Returns the number of elements in the left part of the set
/* The c++ function for: getNbLeft(b:BipartiteSet) [] */
int  choco_getNbLeft_BipartiteSet(BipartiteSet *b)
{ return (b->nbLeft);} 


// Returns the number of elements in the right part of the set
/* The c++ function for: getNbRight(b:BipartiteSet) [] */
int  choco_getNbRight_BipartiteSet(BipartiteSet *b)
{ return ((b->objs[0]-b->nbLeft));} 


// Returns the overall number of elements in the bipartite set
/* The c++ function for: getNbObjects(b:BipartiteSet) [] */
int  choco_getNbObjects_BipartiteSet(BipartiteSet *b)
{ return (b->objs[0]);} 


// indexed access to an object
/* The c++ function for: getObject(b:BipartiteSet,i:integer) [] */
OID  choco_getObject_BipartiteSet(BipartiteSet *b,int i)
{ ;return (nth_array(b->objs,i));} 


// Returns the left part of the set
/* The c++ function for: leftPart(b:BipartiteSet) [] */
set * choco_leftPart_BipartiteSet(BipartiteSet *b)
{ GC_BIND;
  { set *Result ;
    { set * i_bag = set::empty(Kernel.emptySet);
      { int  i = 1;
        int  g0019 = b->nbLeft;
        { OID gc_local;
          while ((i <= g0019))
          { GC_LOOP;
            i_bag->addFast(nth_array(b->objs,i));
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = GC_OBJECT(set,i_bag);
      } 
    GC_UNBIND; return (Result);} 
  } 


// Returns the right part of the set
/* The c++ function for: rightPart(b:BipartiteSet) [] */
set * choco_rightPart_BipartiteSet(BipartiteSet *b)
{ GC_BIND;
  { set *Result ;
    { set * i_bag = set::empty(Kernel.emptySet);
      { int  i = (b->nbLeft+1);
        int  g0020 = b->objs[0];
        { OID gc_local;
          while ((i <= g0020))
          { GC_LOOP;
            i_bag->addFast(nth_array(b->objs,i));
            ++i;
            GC_UNLOOP;} 
          } 
        } 
      Result = GC_OBJECT(set,i_bag);
      } 
    GC_UNBIND; return (Result);} 
  } 


// Optimized iteration on the left part of the set (without allocation)
// Optimized iteration on the left part of the set (without allocation)
// ********************************************************************
// *   Part 6: Integers as bitvectors                                 *
// ********************************************************************
/* The c++ function for: getBitCount(bv:integer) [] */
int  choco_getBitCount_integer(int bv)
{ { int Result = 0;
    { int  cnt = 0;
      { int  i = 0;
        int  g0021 = 29;
        { while ((i <= g0021))
          { if (BCONTAIN(bv,i))
             ++cnt;
            ++i;
            } 
          } 
        } 
      Result = cnt;
      } 
    return (Result);} 
  } 


// those two methods return integers between 0 and 29
/* The c++ function for: getMinBitIndex(bv:integer) [] */
int  choco_getMinBitIndex_integer(int bv)
{ ;{ int Result = 0;
    { int  i = 0;
      { while ((!BCONTAIN(bv,i)))
        { ++i;
          } 
        } 
      Result = i;
      } 
    return (Result);} 
  } 


/* The c++ function for: getMaxBitIndex(bv:integer) [] */
int  choco_getMaxBitIndex_integer(int bv)
{ ;{ int Result = 0;
    { int  i = 29;
      { while ((!BCONTAIN(bv,i)))
        { i= (i-1);
          } 
        } 
      Result = i;
      } 
    return (Result);} 
  } 


// ********************************************************************
// *   Part 6: Bitvectors                                             *
// ********************************************************************
// An encoding of sets with a list of integers taken as bitvectors 
// bitvetor[word][idx] tells if (minValue + 30(word-1) + idx) is in the set
//   this is valid for 0<=word<=length(bitvector), 0<=idx<=29
//        x = minv + 30(w-1) + idx
//        idx = (x - minv) mod 30
//        w   = (x - minv) / 30 + 1 
// utility: creates a bitvector with n "ones": 11111... (from position 0 to n - 1)
/* The c++ function for: makeAllOnesBitVector(n:integer) [] */
int  choco_makeAllOnesBitVector_integer(int n)
{ ;return (((exp2_integer((n-1))-1)+exp2_integer((n-1))));} 


/* The c++ function for: getBitVectorList(minv:integer,l:list<integer>) [] */
list * choco_getBitVectorList_integer(int minv,list *l)
{ { list *Result ;
    { list * res = list::empty(Kernel._integer);
      { int  w = 1;
        int  g0022 = l->length;
        { OID gc_local;
          while ((w <= g0022))
          { // HOHO, GC_LOOP not needed !
            { int  bv = (*(l))[w];
              int  i = 0;
              int  g0023 = 29;
              { OID gc_local;
                while ((i <= g0023))
                { // HOHO, GC_LOOP not needed !
                  if (BCONTAIN(bv,i))
                   res->addFast(((minv+((w-1)*30))+i));
                  ++i;
                  } 
                } 
              } 
            ++w;
            } 
          } 
        } 
      Result = res;
      } 
    return (Result);} 
  } 


/* The c++ function for: isInBitVectorList(idx:integer,l:list<integer>) [] */
ClaireBoolean * choco_isInBitVectorList_integer(int idx,list *l)
{ { ClaireBoolean *Result ;
    { int  w = ((idx/30)+1);
      int  i = mod_integer(idx,30);
      Result = nth_integer((*(l))[w],i);
      } 
    return (Result);} 
  } 


// backtrackable addition to a bit vector. Returns a boolean indicating whether the addition was really performed
// or whether the element was already present
/* The c++ function for: addToBitVectorList(idx:integer,l:list<integer>) [] */
ClaireBoolean * choco_addToBitVectorList_integer(int idx,list *l)
{ { ClaireBoolean *Result ;
    { int  w = ((idx/30)+1);
      int  i = mod_integer(idx,30);
      int  bv = (*(l))[w];
      if (!BCONTAIN(bv,i))
       { STOREI((*l)[w],(bv+exp2_integer(i)));
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 


/* The c++ function for: remFromBitVectorList(idx:integer,l:list<integer>) [] */
ClaireBoolean * choco_remFromBitVectorList_integer(int idx,list *l)
{ { ClaireBoolean *Result ;
    { int  w = ((idx/30)+1);
      int  i = mod_integer(idx,30);
      int  bv = (*(l))[w];
      if (BCONTAIN(bv,i))
       { STOREI((*l)[w],(bv-exp2_integer(i)));
        Result = CTRUE;
        } 
      else Result = CFALSE;
        } 
    return (Result);} 
  } 


/* The c++ function for: getBitVectorListCount(l:list<integer>) [] */
int  choco_getBitVectorListCount_list(list *l)
{ { int Result = 0;
    { int  n = 0;
      { int  w = 1;
        int  g0024 = l->length;
        { while ((w <= g0024))
          { { int  bv = (*(l))[w];
              int  i = 0;
              int  g0025 = 29;
              { while ((i <= g0025))
                { if (BCONTAIN(bv,i))
                   ++n;
                  ++i;
                  } 
                } 
              } 
            ++w;
            } 
          } 
        } 
      Result = n;
      } 
    return (Result);} 
  } 


/* The c++ function for: getBitVectorInf(l:list<integer>) [] */
int  choco_getBitVectorInf_list(list *l)
{ { int Result = 0;
    { int  bvi = 1;
      int  n = l->length;
      int  res = 0;
      ClaireBoolean * found = CFALSE;
      { while (((found != CTRUE) && 
            (bvi <= n)))
        { { int  bv = (*(l))[bvi];
            if (bv == 0)
             res= (res+30);
            else { found= CTRUE;
                res= (res+choco_getMinBitIndex_integer(bv));
                } 
              ++bvi;
            } 
          } 
        } 
      Result = ((found == CTRUE) ?
        res :
        99999999 );
      } 
    return (Result);} 
  } 


/* The c++ function for: getBitVectorSup(l:list<integer>) [] */
int  choco_getBitVectorSup_list(list *l)
{ { int Result = 0;
    { int  n = l->length;
      int  bvi = n;
      int  res = (30*(n-1));
      ClaireBoolean * found = CFALSE;
      { while (((found != CTRUE) && 
            (0 <= bvi)))
        { { int  bv = (*(l))[bvi];
            if (bv == 0)
             res= (res-30);
            else { found= CTRUE;
                res= (res+choco_getMaxBitIndex_integer(bv));
                } 
              bvi= (bvi-1);
            } 
          } 
        } 
      Result = ((found == CTRUE) ?
        res :
        -99999999 );
      } 
    return (Result);} 
  } 

