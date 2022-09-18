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
[choco/makeProblem(s:string, n:integer) : Problem
 -> let pb := Problem(name = copy(s),
                      ;delayedConstraints = list(nil,nil,nil,nil), //v0.30 renamed globalConstraints
                      // v0.9906 removed above
                      globalSearchSolver = GlobalSearchSolver(),
                      localSearchSolver = LocalSearchSolver() )  in
      (let pe := makeChocEngine(n) in attachPropagationEngine(pb,pe),
       let ie := makeConflictCount() in attachInvariantEngine(pb,ie),
       setActiveProblem(pb),
       pb)]

// v1.0
[choco/getProblem(v:IntVar) : Problem -> v.problem]

// v1.02 <thb> add the case when the constraint c involves only constant variables, considered as belonging to CURRENT_PB
[choco/getProblem(c:AbstractConstraint) : Problem
-> let p := CURRENT_PB, n := getNbVars(c) in
      (when i0 := some(i in (1 .. n) | known?(problem,getVar(c,i) as IntVar)) in
            p := (getVar(c,i0) as IntVar).problem,
       p)]

// v1.010 new API methods
[choco/getActiveProblem() : Problem -> CURRENT_PB]
[choco/setActiveProblem(pb:Problem) : void -> CURRENT_PB := pb]
[choco/discardProblem(pb:Problem) : void
 -> when gs := get(globalSearchSolver, pb)  in
       (backtrack(gs.baseWorld),
        CURRENT_PB := DUMMY_PROBLEM)]  // v1.013

// v1.013: accessing the propagation engine
[choco/getPropagationEngine(p:Problem) : PropagationEngine
 -> p.propagationEngine]

// v1.013: a toggle: choosing to delay or no the propagation on linear constraints
[choco/setDelayedLinearConstraintPropagation(pe:ChocEngine,b:boolean) : void
 -> if b pe.delayLinCombThreshold := 0
    else pe.delayLinCombThreshold := 100000]

// ********************************************************************
// *   Part 2: variables                                              *
// ********************************************************************

// public methods for creating variables on the fly and assiging them a name
//
// v0.37 added closeIntVar because of new backtrackable domain bounds
[choco/makeBoundIntVar(p:Problem, s:string, i:integer, j:integer) : IntVar
 -> assert(i <= j),
    let v := IntVar(name = copy(s)) in
       (closeIntVar(v,i,j,1),  // v0.9903: react to removals in one pass
        addIntVar(p,v),
        v)]
[choco/makeBoundIntVar(p:Problem, i:integer, j:integer) : IntVar => makeBoundIntVar(p,"",i,j)]
[choco/makeBoundIntVar(p:Problem, s:string) : IntVar => makeBoundIntVar(p,s,-100,100)]

// v0.37
[choco/makeConstantIntVar(x:integer) : IntVar
 -> let v := IntVar(name = "'" /+ string!(x) /+ "'") in // <thb> v0.97
      (closeIntVar(v,x,x,0), v)]   // v0.9903: there will never be a removal to react to

[choco/makeIntVar(p:Problem, s:string, i:integer, j:integer) : IntVar
 -> assert(i <= j),
;    let v := makeBoundIntVar(p,s,i,j) in
     let v := IntVar(name = copy(s)) in
       (closeIntVar(v,i,j,min(3,j - i - 1)),  // v0.9903: react to (up to 5) removal one by one
        addIntVar(p,v),
        v.bucket := makeLinkedListIntDomain(i,j),
        v)]
[choco/makeIntVar(p:Problem, i:integer, j:integer) : IntVar => makeIntVar(p,"",i,j)]
[choco/makeIntVar(p:Problem, s:string) : IntVar => makeIntVar(p,s,-100,100)]

// v0.22 <fl> documented but not implemented ...
// v0.26 stronger typing of b
[choco/makeIntVar(p:Problem, s:string, b:(list[integer] U set[integer])) : IntVar
 -> let minVal := min(b), // v1.02 min vs. Min
        maxVal := max(b), // v1.02 max vs. Max
        v := makeIntVar(p,s,minVal,maxVal) in
      (for val in list{val2 in (minVal .. maxVal) | not(val2 % b)}
           removeDomainVal(v.bucket, val),
       v)]
// v0.26 stronger typing of b
[choco/makeIntVar(p:Problem, b:(list[integer] U set[integer])) : IntVar -> makeIntVar(p,"",b)]

// v1.330: introducing set variables
[choco/makeSetVar(p:Problem, s:string, i:integer, j:integer) : SetVar
 -> assert(i <= j),
     let v := SetVar(name = copy(s)) in
       (closeSetVar(v,i,j),
        addSetVar(p,v),
        if (j - i + 1 <= 29) 
           v.setBucket := makeBitSetDomain(i,j)
        else v.setBucket := makeBitListSetDomain(i,j),
        v)]
[choco/makeSetVar(p:Problem, i:integer, j:integer) : SetVar => makeSetVar(p,"",i,j)]

[choco/makeSetVar(p:Problem, s:string, i:integer, j:integer, cardVar:IntVar) : SetVar
 -> let sv := makeSetVar(p,s,i,j) in 
     (post(p, setCard(sv,cardVar)),
      sv)]
[choco/makeSetVar(p:Problem, i:integer, j:integer, cardVar:IntVar) : SetVar => makeSetVar(p,"",i,j,cardVar)]

// ********************************************************************
// *   Part 3: Constraints as tuples of values                        *
// ********************************************************************

// ---- binary constraints -----
 
// introducing relations
// creating a relation from a test method
[choco/makeBinTruthTest(m:method[range = boolean]) : TruthTest 
 -> TruthTest(test = m)]
// creating an empty relation table
[choco/makeBinRelation(min1:integer, max1:integer, min2:integer, max2:integer) : TruthTable2D
 -> let n := max1 - min1 + 1, m := max2 - min2 + 1, mat := make2DBoolMatrix(min1,max1,min2,max2,false,false) in 
        TruthTable2D(matrix = mat, offset1 = min1, offset2 = min2, size1 = n, size2 = m)]
// creating a relation from an enumeration of tuples
[choco/makeBinRelation(min1:integer, max1:integer, min2:integer, max2:integer, mytuples:list[tuple(integer,integer)]) : TruthTable2D
 -> let n := max1 - min1 + 1, m := max2 - min2 + 1, mat := make2DBoolMatrix(min1,max1,min2,max2,false,false),
        tt := TruthTable2D(matrix = mat, offset1 = min1, offset2 = min2, size1 = n, size2 = m) in 
     (for t in list{tt in mytuples | (tt[1] >= min1 & tt[1] <= max1 & tt[2] >= min2 & tt[2] <= max2)}
        let i := t[1] - min1 + 1, j := t[2] - min2 + 1 in
          (assert((1 <= i) & (i <= n)), assert((1 <= j) & (j <= m)),
           store(mat,t[1],t[2],true)),
      tt) ]

// generic API for stating binary constraints
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean,ac:integer) : CSPBinConstraint
 -> if (ac = 3) makeAC3BinConstraint(va, vb, feas, feasRel)
    else if (ac = 4) makeAC4BinConstraint(va, vb, feas, feasRel)
    else if (ac = 2001) makeAC2001BinConstraint(va, vb, feas, feasRel)
    else error("algorithm AC~S not implemented yet for binary constraints",ac)]

// AC2001 is the default algorithm
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,feas:boolean) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,feas,2001)]
// by default the positive relation is provided
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation,ac:integer) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,true,ac)]
// Simplest API: by default the positive relation is provided and AC2001 is the algorithm
[choco/binConstraint(va:IntVar,vb:IntVar,feasRel:BinRelation) : CSPBinConstraint
 -> binConstraint(va,vb,feasRel,true,2001)]

// ----- older deprecated API (kept for backward compatibility)
[choco/feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple(integer,integer)], ac:integer) : CSPBinConstraint // port to claire 3 tuple(integer,integer) -> tuple
 -> let feasRel := makeBinRelation(va.inf, va.sup, vb.inf, vb.sup, goodPairs) in binConstraint(va,vb,feasRel,true,ac)]
[choco/infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple(integer,integer)], ac:integer) : CSPBinConstraint // port to claire 3 tuple(integer,integer) -> tuple
 -> let feasRel := makeBinRelation(va.inf, va.sup, vb.inf, vb.sup, badPairs) in binConstraint(va,vb,feasRel,false,ac)]
[choco/feasBinTestConstraint(va:IntVar,vb:IntVar, m:method, ac:integer) : CSPBinConstraint
 -> let feasRel := makeBinTruthTest(m) in binConstraint(va,vb,feasRel,true,ac)]
[choco/infeasBinTestConstraint(va:IntVar,vb:IntVar, m:method, ac:integer) : CSPBinConstraint // new since v0.9907
 -> let feasRel := makeBinTruthTest(m) in binConstraint(va,vb,feasRel,false,ac)]
  
[choco/feasPairConstraint(va:IntVar,vb:IntVar,goodPairs:list[tuple]) : CSPBinConstraint => feasPairConstraint(va,vb,goodPairs,2001)]
[choco/infeasPairConstraint(va:IntVar,vb:IntVar,badPairs:list[tuple]) : CSPBinConstraint => infeasPairConstraint(va,vb,badPairs,2001)]
[choco/feasBinTestConstraint(va:IntVar,vb:IntVar, m:method) : CSPBinConstraint => feasBinTestConstraint(va,vb,m,2001)]
[choco/infeasBinTestConstraint(va:IntVar,vb:IntVar, m:method) : CSPBinConstraint => infeasBinTestConstraint(va,vb,m,2001)]
// ---------------------

// n-ary constraints 
[choco/feasTupleConstraint(lvars:list[IntVar],
                           goodTuples:list[list[integer]]) : CSPLargeConstraint
 -> tupleConstraint(lvars, true, goodTuples)]

[choco/infeasTupleConstraint(lvars:list[IntVar],
                             badTuples:list[list[integer]]) : CSPLargeConstraint
 -> tupleConstraint(lvars, false, badTuples)]

// claire3 port: building a staticly typed vars lisit instead of a simple copy
[choco/feasTestConstraint(lvars:list[IntVar],m:method) : CSPLargeConstraint
 -> let c := CSPLargeConstraint(vars = list<IntVar>{v | v in lvars}, cste = 0) in
     (closeLargeIntConstraint(c),  // v0.34
      c.cachedTuples := false,
      c.feasTest := m,
      c) ]

// ********************************************************************
// *   Part 4: Arithmetic constraints                                 *
// ********************************************************************

// v1.04 the return type of all arithmetic methods is now a subtype of IntConstraint

// Full API for arithmetic comparisons
claire/== :: operation(precedence = precedence(=))
claire/!== :: operation(precedence = precedence(!=))
claire/<> :: !==

// Simple unary operators
[claire/>=(v:IntVar, x:integer) : GreaterOrEqualxc
 -> GreaterOrEqualxc(v1 = v, cste = x)]
[claire/>(v:IntVar, x:integer) : GreaterOrEqualxc
 -> GreaterOrEqualxc(v1 = v,cste = x + 1)]
[claire/<=(v:IntVar, x:integer) : LessOrEqualxc
 -> LessOrEqualxc(v1 = v,cste = x)]
[claire/<(v:IntVar, x:integer) : LessOrEqualxc
 -> LessOrEqualxc(v1 = v,cste = x - 1)]
[claire/==(v:IntVar, x:integer) : Equalxc
 -> Equalxc(v1 = v,cste = x)]
[claire/!==(v:IntVar, x:integer) : NotEqualxc
 -> NotEqualxc(v1 = v,cste = x)]

[claire/>=(x:integer, v:IntVar) : LessOrEqualxc => (v <= x)]
[claire/>(x:integer, v:IntVar) : LessOrEqualxc => (v < x)]
[claire/<=(x:integer, v:IntVar) : GreaterOrEqualxc => (v >= x)]
[claire/<(x:integer, v:IntVar) : GreaterOrEqualxc => (v > x)]
[claire/==(x:integer, v:IntVar) : Equalxc => (v == x)]
[claire/!==(x:integer, v:IntVar) : NotEqualxc => (v !== x)]

// Simple binary operators
[claire/==(u:IntVar, v:IntVar) : Equalxyc
 -> Equalxyc(v1 = u,v2 = v,cste = 0)]
[claire/!==(u:IntVar, v:IntVar) : NotEqualxyc
 -> NotEqualxyc(v1 = u,v2 = v,cste = 0)]
// v0.9907
[claire/<=(u:IntVar, v:IntVar) : GreaterOrEqualxyc
 -> GreaterOrEqualxyc(v1 = v,v2 = u,cste = 0)]
[claire/>=(u:IntVar, v:IntVar) : GreaterOrEqualxyc
 -> GreaterOrEqualxyc(v1 = u,v2 = v,cste = 0)]
[claire/>(u:IntVar, v:IntVar) : GreaterOrEqualxyc
 -> GreaterOrEqualxyc(v1 = u,v2 = v,cste = 1)]
// v0.9907
[claire/<(u:IntVar, v:IntVar) : GreaterOrEqualxyc
 -> GreaterOrEqualxyc(v1 = v,v2 = u,cste = 1)]

// General linear combinations
//  we store linear expressions in temporary data structures: terms
// v1.0 renamed Term (was Term)
choco/Term <: Ephemeral(
    cste:integer = 0)

//  UnTerm: a temporary object representing +/-x +c
choco/UnTerm <: Term(
    v1:IntVar,
    sign1:boolean = true)

[self_print(t:UnTerm)
 -> if not(t.sign1) printf("-"),
    printf("~S ",t.v1),
    if (t.cste < 0) printf("~S",t.cste)
    else if (t.cste > 0) printf("+~S",t.cste)]

//  BinTerm: a temporary object representing +/-x +/-y +c
choco/BinTerm <: Term(
    v1:IntVar,
    v2:IntVar,
    sign1:boolean = true,
    sign2:boolean = true)

[self_print(t:BinTerm)
 -> if (t.sign1) printf("+") else printf("-"),
    printf("~S ",t.v1),
    if (t.sign2) printf("+") else printf("-"),
    printf("~S",t.v2),
    if (t.cste < 0) printf("~S",t.cste)
    else if (t.cste > 0) printf("+~S",t.cste)]

//  LinTerm: a temporary object representing a linear term
choco/LinTerm <: Term(
    lcoeff:list<integer>, // v1.011 <thb> strongly typed lists
    lvars:list<IntVar>)

// v1.02 <franck>
[self_print(t:LinTerm)
 -> let tLength := length(t.lcoeff) in
      (if (tLength > 0) printf("~S.~S",t.lcoeff[1],t.lvars[1]),
       for i in (2 .. tLength) // v0.28: size vs. length
           printf(" + ~S.~S",t.lcoeff[i],t.lvars[i]),
       if (t.cste < 0) printf(" ~S",t.cste)
       else if (t.cste > 0) printf(" +~S",t.cste) )]

// constructing a linear term from a list of variables
claire/scalar :: operation(precedence = precedence(*))
// claire3 port: building a staticly typed vars list instead of a simple copy
// v1.011 strongly typed lists
[scalar(l1:list[integer], l2:list[IntVar]) : LinTerm
 -> LinTerm(lcoeff = list<integer>{i | i in l1}, lvars = list<IntVar>{v | v in l2})] //v0.94: ensure a non-destructive behavior

//v0.36:Constructing a linear term from a list of variables
// claire3 port: avoid make_list, v1.011 strongly typed lists
[choco/sumVars(l:list[IntVar]) : LinTerm
-> LinTerm(lcoeff = make_list(length(l),integer,1),// make_list(length(l),integer,1), // list<integer>{1 | i in (1 .. length(l))}, ;make_list(length(l),1),
           lvars = list<IntVar>{v | v in l})] //v0.94: ensure a non-destructive behavior

[choco/sumTerms(l:list[Term]) : Term
-> let t:Term := l[1] in
    (for i in (2 .. length(l))
         t :+ l[i],
     t)]

// building linear terms
[*(a:integer, x:IntVar) : LinTerm -> LinTerm(lcoeff = list<integer>(a), lvars = list<IntVar>(x))]
[*(x:IntVar, a:integer) : LinTerm -> LinTerm(lcoeff = list<integer>(a), lvars = list<IntVar>(x))]

// v1.018
[*(a:integer, t:LinTerm) : LinTerm
 -> for i in (1 .. length(t.lvars))
        t.lcoeff[i] := t.lcoeff[i] * a,
    t.cste := t.cste * a,
    t]
[*(t:LinTerm, a:integer) : LinTerm -> for i in (1 .. length(t.lvars)) t.lcoeff[i] := t.lcoeff[i] * a, t]

// ----- Addition Operator
[+(u:IntVar, c:integer) : UnTerm -> UnTerm(v1 = u, cste = c)]
[+(c:integer, u:IntVar) : UnTerm => u + c] // v0.36 <thb> wrong return type
[+(u:IntVar, v:IntVar) : BinTerm -> BinTerm(v1 = u, v2 = v)]
[+(t:UnTerm, c:integer) : UnTerm -> t.cste :+ c, t]
[+(c:integer, t:UnTerm) : UnTerm => t + c]

[+(t:UnTerm, x:IntVar) : BinTerm -> BinTerm(v1 = t.v1, sign1 = t.sign1, v2 = x, cste = t.cste)]
[+(x:IntVar, t:UnTerm) : BinTerm => t + x]
[+(t1:UnTerm, t2:UnTerm) : BinTerm -> BinTerm(v1 = t1.v1, sign1 = t1.sign1, v2 = t2.v1, sign2 = t2.sign1, cste = t1.cste + t2.cste)]
[+(t:BinTerm, c:integer) : BinTerm -> t.cste :+ c, t]
[+(c:integer, t:BinTerm) : BinTerm => t + c]

[+(t:BinTerm, x:IntVar) : LinTerm
 -> LinTerm(lcoeff = list<integer>(integer!(t.sign1),integer!(t.sign2),1),
            lvars = list<IntVar>(t.v1,t.v2,x),
            cste = t.cste)]
[+(x:IntVar, t:BinTerm) : LinTerm => t + x]
[+(t1:BinTerm, t2:UnTerm) : LinTerm
 -> LinTerm(lcoeff = list<integer>(integer!(t1.sign1),integer!(t1.sign2),integer!(t2.sign1)),
            lvars = list<IntVar>(t1.v1,t1.v2,t2.v1),
            cste = t1.cste + t2.cste)]
[+(t2:UnTerm, t1:BinTerm) : LinTerm => t1 + t2]

[+(t1:BinTerm, t2:BinTerm) : LinTerm
 -> LinTerm(lcoeff = list<integer>(integer!(t1.sign1),integer!(t1.sign2),integer!(t2.sign1),integer!(t2.sign2)),
            lvars = list<IntVar>(t1.v1,t1.v2,t2.v1,t2.v2),
            cste = t1.cste + t2.cste)]

[+(t:LinTerm, a:integer) : LinTerm -> t.cste :+ a, t]
[+(a:integer, t:LinTerm) : LinTerm => t + a]

[+(t:LinTerm, x:IntVar) : LinTerm -> t.lcoeff :add 1, t.lvars :add x, t]
[+(x:IntVar, t:LinTerm) : LinTerm => t + x]

[+(t1:LinTerm, t2:UnTerm) : LinTerm
 -> t1.lcoeff :add integer!(t2.sign1),
    t1.lvars :add t2.v1,
    t1.cste :+ t2.cste,
    t1]
[+(t2:UnTerm, t1:LinTerm) : LinTerm => (t1 + t2)]

[+(t1:LinTerm, t2:BinTerm) : LinTerm
 -> t1.lcoeff :/+ list<integer>(integer!(t2.sign1),integer!(t2.sign2)),
    t1.lvars :/+ list<IntVar>(t2.v1,t2.v2),
    t1.cste :+ t2.cste,
    t1]
[+(t2:BinTerm, t1:LinTerm) : LinTerm => t1 + t2]

[+(t1:LinTerm, t2:LinTerm) : LinTerm
 -> LinTerm(lcoeff = t1.lcoeff /+ t2.lcoeff,
            lvars = t1.lvars /+ t2.lvars,
            cste = t1.cste + t2.cste)]

// ----- Opposite Operator
[-(x:IntVar) : UnTerm
 -> UnTerm(v1 = x, sign1 = false)]
[-(t:UnTerm) : UnTerm
 -> t.sign1 := not(t.sign1), t.cste := -(t.cste), t]
[-(t:BinTerm) : BinTerm
 -> t.sign1 := not(t.sign1), t.sign2 := not(t.sign2), t.cste := -(t.cste), t]
[-(t:LinTerm) : LinTerm
 -> t.lcoeff := list<integer>{-(a) | a in t.lcoeff}, t.cste := -(t.cste), t]

// ----- Substraction Operator
// many lines for
// [-(t1:(Term U IntVar U integer), t2:(Term U IntVar)) : Term => t1 + -(t2)]
// because of a dump compiler
// v0.18 further expand for static typing
[-(t1:IntVar, t2:integer) : UnTerm -> t1 + -(t2)]
[-(t1:UnTerm, t2:integer) : UnTerm -> t1 + -(t2)]
[-(t1:BinTerm, t2:integer) : BinTerm -> t1 + -(t2)]
[-(t1:LinTerm, t2:integer) : LinTerm -> t1 + -(t2)]
[-(t1:integer, t2:IntVar) : UnTerm -> t1 + -(t2)]
[-(t1:IntVar, t2:IntVar) : BinTerm -> t1 + -(t2)]
[-(t1:UnTerm, t2:IntVar) : BinTerm -> t1 + -(t2)]
[-(t1:BinTerm, t2:IntVar) : LinTerm -> t1 + -(t2)]
[-(t1:LinTerm, t2:IntVar) : LinTerm -> t1 + -(t2)]
[-(t1:integer, t2:UnTerm) : UnTerm -> t1 + -(t2)]
[-(t1:IntVar, t2:UnTerm) : BinTerm -> t1 + -(t2)]
[-(t1:UnTerm, t2:UnTerm) : BinTerm -> t1 + -(t2)]
[-(t1:BinTerm, t2:UnTerm) : LinTerm -> t1 + -(t2)]
[-(t1:LinTerm, t2:UnTerm) : LinTerm -> t1 + -(t2)]
[-(t1:integer, t2:BinTerm) : BinTerm -> t1 + -(t2)]
[-(t1:IntVar, t2:BinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:UnTerm, t2:BinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:BinTerm, t2:BinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:LinTerm, t2:BinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:integer, t2:LinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:IntVar, t2:LinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:UnTerm, t2:LinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:BinTerm, t2:LinTerm) : LinTerm -> t1 + -(t2)]
[-(t1:LinTerm, t2:LinTerm) : LinTerm -> t1 + -(t2)]

// ------- Using terms within comparisons
// v0.18 rewrite for static typing
// [>=(a:integer, t:Term) : IntConstraint => -(t) >= -(a)]
// [>=(x:IntVar, t:Term) : IntConstraint => -(t) >= -(x)]
// [>=(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => -(t2) >= -(t1)]
[>=(a:integer, t:UnTerm) : IntConstraint -> -(t) >= -(a)]
[>=(a:integer, t:BinTerm) : IntConstraint -> -(t) >= -(a)]
[>=(a:integer, t:LinTerm) : IntConstraint -> -(t) >= -(a)]
[>=(x:IntVar, t:UnTerm) : IntConstraint -> -(t) >= -(x)]
[>=(x:IntVar, t:BinTerm) : IntConstraint -> -(t) >= -(x)]
[>=(x:IntVar, t:LinTerm) : IntConstraint -> -(t) >= -(x)]
[>=(t1:UnTerm, t2:BinTerm) : IntConstraint -> -(t2) >= -(t1)]
[>=(t1:UnTerm, t2:LinTerm) : IntConstraint -> -(t2) >= -(t1)]
[>=(t1:BinTerm, t2:LinTerm) : IntConstraint -> -(t2) >= -(t1)]

[>=(t:UnTerm, c:integer) : UnIntConstraint
 -> if t.sign1
       GreaterOrEqualxc(v1 = t.v1, cste = c - t.cste)
     else LessOrEqualxc(v1 = t.v1, cste = t.cste - c)]

[>=(t:UnTerm, x:IntVar) : IntConstraint => -(x) >= -(t)]
[>=(t1:UnTerm, t2:UnTerm) : IntConstraint
 -> if (t1.sign1 = t2.sign1)
       (if t1.sign1
           GreaterOrEqualxyc(v1 = t1.v1,v2 = t2.v1,cste = t2.cste - t1.cste)
        else
           GreaterOrEqualxyc(v1 = t2.v1,v2 = t1.v1,cste = t2.cste - t1.cste))
    else makeLinComb(list<integer>(integer!(t1.sign1),-(integer!(t2.sign1))), list<IntVar>(t1.v1,t2.v1), t1.cste - t2.cste, GEQ)]

[>=(t:BinTerm, c:integer) : IntConstraint
 -> if (t.sign1 != t.sign2)
       (if t.sign1
           GreaterOrEqualxyc(v1 = t.v1,v2 = t.v2,cste = c - t.cste)
        else
           GreaterOrEqualxyc(v1 = t.v2,v2 = t.v1,cste = c + t.cste))
    else makeLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2)), list<IntVar>(t.v1,t.v2), t.cste - c, GEQ) ]

[>=(t:BinTerm, x:IntVar) : LinComb
 -> makeLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2),-1), list<IntVar>(t.v1,t.v2,x),t.cste,GEQ)]

[>=(t1:BinTerm, t2:UnTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1))),
            list<IntVar>(t1.v1,t1.v2,t2.v1),t1.cste - t2.cste,GEQ)]

[>=(t1:BinTerm, t2:BinTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
            list<IntVar>(t1.v1,t1.v2,t2.v1,t2.v2),t1.cste - t2.cste,GEQ)]

[>=(t:LinTerm,a:integer) : LinComb
 -> makeLinComb(t.lcoeff, t.lvars, t.cste - a, GEQ)]

[>=(t:LinTerm,x:IntVar) : LinComb
 -> makeLinComb(t.lcoeff add -1, t.lvars add x, t.cste, GEQ)] //v0.30 add -1 and not +1 !

[>=(t:LinTerm,t2:UnTerm) : LinComb
 -> makeLinComb(t.lcoeff add -(integer!(t2.sign1)), t.lvars add t2.v1, t.cste - t2.cste, GEQ)]

[>=(t:LinTerm,t2:BinTerm) : LinComb
 -> makeLinComb(t.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                t.lvars /+ list<IntVar>(t2.v1,t2.v2), t.cste - t2.cste, GEQ)]

[>=(t1:LinTerm,t2:LinTerm) : LinComb
 -> makeLinComb(t1.lcoeff /+ list<integer>{-(a) | a in t2.lcoeff},
                t1.lvars /+ t2.lvars, t1.cste - t2.cste, GEQ)]

// All comparisons can be defined from >=
// rewrite t1 > t2 as t1 >= t2 + 1
// v0.18 expanded for static typing
// [>(t1:Term, t2:Term) : IntConstraint => (t1 >= t2 + 1)]
// [>(t1:(IntVar U integer), t2:Term) : IntConstraint => (t1 >= t2 + 1)]
// [>(t1:Term, t2:(IntVar U integer)) : IntConstraint => (t1 >= t2 + 1)]
[>(t1:integer, t2:UnTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:integer, t2:BinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:integer, t2:LinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:IntVar, t2:UnTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:IntVar, t2:BinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:IntVar, t2:LinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:UnTerm, t2:integer) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:BinTerm, t2:integer) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:LinTerm, t2:integer) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:UnTerm, t2:IntVar) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:BinTerm, t2:IntVar) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:LinTerm, t2:IntVar) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:UnTerm, t2:UnTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:BinTerm, t2:UnTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:LinTerm, t2:UnTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:UnTerm, t2:BinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:BinTerm, t2:BinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:LinTerm, t2:BinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:UnTerm, t2:LinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:BinTerm, t2:LinTerm) : IntConstraint -> (t1 >= t2 + 1)]
[>(t1:LinTerm, t2:LinTerm) : IntConstraint -> (t1 >= t2 + 1)]

// rewrite t1 <= t2 as t2 >= t1

// v0.18 expanded for static typing
// [<=(t1:Term, t2:Term) : IntConstraint -> t2 >= t1]
// [<=(t1:(IntVar U integer), t2:Term) : IntConstraint => t2 >= t1]
// [<=(t1:Term, t2:(IntVar U integer)) : IntConstraint => t2 >= t1]
[<=(t1:integer, t2:UnTerm) : IntConstraint -> t2 >= t1]
[<=(t1:integer, t2:BinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:integer, t2:LinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:IntVar, t2:UnTerm) : IntConstraint -> t2 >= t1]
[<=(t1:IntVar, t2:BinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:IntVar, t2:LinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:UnTerm, t2:integer) : IntConstraint -> t2 >= t1]
[<=(t1:BinTerm, t2:integer) : IntConstraint -> t2 >= t1]
[<=(t1:LinTerm, t2:integer) : IntConstraint -> t2 >= t1]
[<=(t1:UnTerm, t2:IntVar) : IntConstraint -> t2 >= t1]
[<=(t1:BinTerm, t2:IntVar) : IntConstraint -> t2 >= t1]
[<=(t1:LinTerm, t2:IntVar) : IntConstraint -> t2 >= t1]
[<=(t1:UnTerm, t2:UnTerm) : IntConstraint -> t2 >= t1]
[<=(t1:BinTerm, t2:UnTerm) : IntConstraint -> t2 >= t1]
[<=(t1:LinTerm, t2:UnTerm) : IntConstraint -> t2 >= t1]
[<=(t1:UnTerm, t2:BinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:BinTerm, t2:BinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:LinTerm, t2:BinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:UnTerm, t2:LinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:BinTerm, t2:LinTerm) : IntConstraint -> t2 >= t1]
[<=(t1:LinTerm, t2:LinTerm) : IntConstraint -> t2 >= t1]


// rewrite t1 < t2 as t1 <= t2 - 1
// v0.18 expanded for static typing
// [<(t1:Term, t2:Term) : IntConstraint => (t1 <= t2 - 1)]
// [<(t1:(IntVar U integer), t2:Term) : IntConstraint => (t1 <= t2 - 1)]
// [<(t1:Term, t2:(IntVar U integer)) : IntConstraint => (t1 <= t2 - 1)]
[<(t1:integer, t2:UnTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:integer, t2:BinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:integer, t2:LinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:IntVar, t2:UnTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:IntVar, t2:BinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:IntVar, t2:LinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:UnTerm, t2:integer) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:BinTerm, t2:integer) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:LinTerm, t2:integer) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:UnTerm, t2:IntVar) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:BinTerm, t2:IntVar) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:LinTerm, t2:IntVar) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:UnTerm, t2:UnTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:BinTerm, t2:UnTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:LinTerm, t2:UnTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:UnTerm, t2:BinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:BinTerm, t2:BinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:LinTerm, t2:BinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:UnTerm, t2:LinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:BinTerm, t2:LinTerm) : IntConstraint -> (t1 <= t2 - 1)]
[<(t1:LinTerm, t2:LinTerm) : IntConstraint -> (t1 <= t2 - 1)]

// Equality
// v0.18 expanded for static typing
// [==(a:integer, t:Term) : IntConstraint => t == a]
// [==(x:IntVar, t:Term) : IntConstraint => t == x]
// [==(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => t2 == t1]
// [==(t1:BinTerm, t2:LinTerm) : IntConstraint => t2 == t1]
[==(a:integer, t:UnTerm) : IntConstraint => t == a]
[==(a:integer, t:BinTerm) : IntConstraint => t == a]
[==(a:integer, t:LinTerm) : IntConstraint => t == a]
[==(x:IntVar, t:UnTerm) : IntConstraint => t == x]
[==(x:IntVar, t:BinTerm) : IntConstraint => t == x]
[==(x:IntVar, t:LinTerm) : IntConstraint => t == x]
[==(t1:UnTerm, t2:BinTerm) : IntConstraint => t2 == t1]
[==(t1:UnTerm, t2:LinTerm) : IntConstraint => t2 == t1]
[==(t1:BinTerm, t2:LinTerm) : IntConstraint => t2 == t1]



[==(t:UnTerm, c:integer) : UnIntConstraint
  -> Equalxc(v1 = t.v1, cste = (if t.sign1 c - t.cste else t.cste - c))]  // v0.9907
[==(t:UnTerm, x:IntVar) : IntConstraint
  -> if t.sign1
       Equalxyc(v1 = x,v2 = t.v1,cste = t.cste)
     else makeLinComb(list<integer>(1,1),list<IntVar>(t.v1,x),-(t.cste),EQ)]
[==(t1:UnTerm, t2:UnTerm) : BinIntConstraint
  -> Equalxyc(v1 = t1.v1,v2 = t2.v1,cste = t2.cste - t1.cste)]

[==(t:BinTerm, c:integer) : IntConstraint
 -> if (t.sign1 != t.sign2)
       Equalxyc(v1 = t.v1,v2 = t.v2,cste = (if t.sign1 c - t.cste else t.cste - c))   // v0.23
    else makeLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2)),list<IntVar>(t.v1,t.v2),t.cste - c,EQ)]

[==(t:BinTerm, x:IntVar) : LinComb
 -> makeLinComb(list<integer>(integer!(t.sign1),integer!(t.sign2),-1),list<IntVar>(t.v1,t.v2,x),t.cste,EQ)]
[==(t1:BinTerm, t2:UnTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1))),
            list<IntVar>(t1.v1,t1.v2,t2.v1),t1.cste - t2.cste,EQ)]

// claire3 port staticly typed lists
[==(t1:BinTerm, t2:BinTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1),integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
            list<IntVar>(t1.v1,t1.v2,t2.v1,t2.v2),t1.cste - t2.cste,EQ)]

[==(t:LinTerm, c:integer) : LinComb
 -> makeLinComb(t.lcoeff, t.lvars, t.cste - c, EQ)]
[==(t:LinTerm, x:IntVar) : LinComb
 -> makeLinComb(t.lcoeff add -1, t.lvars add x, t.cste, EQ)]
[==(t1:LinTerm, t2:UnTerm) : LinComb
 -> makeLinComb(t1.lcoeff add -(integer!(t2.sign1)),
                t1.lvars add t2.v1, t1.cste - t2.cste, EQ)]

// claire3 port staticly typed lists
[==(t1:LinTerm, t2:BinTerm) : LinComb
 -> makeLinComb(t1.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                t1.lvars /+ list<IntVar>(t2.v1,t2.v2),t1.cste - t2.cste, EQ)]
// claire3 port staticly typed lists
[==(t1:LinTerm, t2:LinTerm) : LinComb
 -> makeLinComb(t1.lcoeff /+ list<integer>{-(a) | a in t2.lcoeff},
                t1.lvars /+ t2.lvars,
                t1.cste - t2.cste, EQ)]

// v0.18 expanded for static typing
// [!==(a:integer, t:Term) : IntConstraint => t !== a]
// [!==(x:IntVar, t:Term) : IntConstraint => t !== x]
// [!==(t1:UnTerm, t2:(BinTerm U LinTerm)) : IntConstraint => t2 !== t1]
// [!==(t1:BinTerm, t2:LinTerm) : IntConstraint => t2 !== t1]

[!==(a:integer, t:UnTerm) : IntConstraint -> t !== a]
[!==(a:integer, t:BinTerm) : IntConstraint -> t !== a]
[!==(a:integer, t:LinTerm) -> t !== a]
[!==(x:IntVar, t:UnTerm) : IntConstraint -> t !== x]
[!==(x:IntVar, t:BinTerm) -> t !== x]
[!==(x:IntVar, t:LinTerm) -> t !== x]
[!==(t1:UnTerm, t2:BinTerm) -> t2 !== t1]
[!==(t1:UnTerm, t2:LinTerm) -> t2 !== t1]
[!==(t1:BinTerm, t2:LinTerm) -> t2 !== t1]
// v1.010 introduce general linear disequalities
[!==(t:UnTerm, c:integer) : UnIntConstraint
 -> NotEqualxc(v1 = t.v1,cste = (if t.sign1 c - t.cste else t.cste - c))]
[!==(t:UnTerm, x:IntVar) : IntConstraint
 -> if not(t.sign1) makeLinComb(list<integer>(1,1), list<IntVar>(x, t.v1), -(t.cste), NEQ)
    else NotEqualxyc(v1 = x,v2 = t.v1,cste = t.cste)]
[!==(t1:UnTerm, t2:UnTerm) : IntConstraint
 -> let newcste := (if (t1.sign1) t2.cste - t1.cste else t1.cste - t2.cste) in
      (if (t1.sign1 != t2.sign1) 
          makeLinComb(list<integer>(1,1), list<IntVar>(t1.v1, t2.v1), -(newcste), NEQ)
       else NotEqualxyc(v1 = t1.v1,v2 = t2.v1,cste = newcste))]
[!==(t1:BinTerm, c:integer) : IntConstraint
 -> let newcste := (if (t1.sign1) c - t1.cste else t1.cste - c) in 
      (if (t1.sign1 != t1.sign2)
          NotEqualxyc(v1 = t1.v1,v2 = t1.v2,cste = newcste)
       else makeLinComb(list<integer>(1,1), list<IntVar>(t1.v1, t1.v2), -(newcste), NEQ))]
[!==(t1:BinTerm, x:IntVar) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-1),
                list<IntVar>(t1.v1, t1.v2, x), t1.cste, NEQ)]
[!==(t1:BinTerm, t2:UnTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-(integer!(t2.sign1))),
                list<IntVar>(t1.v1, t1.v2, t2.v1), t1.cste - t2.cste, NEQ)]
[!==(t1:BinTerm, t2:BinTerm) : LinComb
 -> makeLinComb(list<integer>(integer!(t1.sign1), integer!(t1.sign2),-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                list<IntVar>(t1.v1, t1.v2, t2.v1, t2.v2), t1.cste - t2.cste, NEQ)]
[!==(t1:LinTerm, c:integer) : LinComb
 -> makeLinComb(t1.lcoeff, t1.lvars, t1.cste - c, NEQ)]
[!==(t1:LinTerm, x:IntVar) : LinComb
 -> makeLinComb(t1.lcoeff add -1, t1.lvars add x, t1.cste, NEQ)]
[!==(t1:LinTerm, t2:UnTerm) : LinComb
 -> makeLinComb(t1.lcoeff add -(integer!(t2.sign1)), t1.lvars add t2.v1, t1.cste - t2.cste, NEQ)]
[!==(t1:LinTerm, t2:BinTerm) : LinComb
 -> makeLinComb(t1.lcoeff /+ list<integer>(-(integer!(t2.sign1)),-(integer!(t2.sign2))),
                t1.lvars /+ list<IntVar>(t2.v1, t2.v2), t1.cste - t2.cste, NEQ)]
[!==(t1:LinTerm, t2:LinTerm) : LinComb
 -> makeLinComb(t1.lcoeff /+ list<integer>{-(cf) | cf in t2.lcoeff},
                t1.lvars /+ t2.lvars, t1.cste - t2.cste, NEQ)]

// ********************************************************************
// *   Part 5: Boolean connectors                                     *
// ********************************************************************

// ----- Disjunctions -----------

// 1. Associative rules for building disjunctions from two disjunctions
[or(c1:LargeDisjunction, c2:LargeDisjunction) : LargeDisjunction
 -> let c := LargeDisjunction(lconst = c1.lconst /+ c2.lconst) in  // v0.93 typo
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
        c)]
// claire3 port: strongly typed lists
[or(c1:Disjunction, c2: Disjunction) : LargeDisjunction // v1.0: missing API functions, v1.02 typos
 -> let c := LargeDisjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2.const1,c2.const2)) in
       (closeBoolConstraint(c), // v1.05
        c)]
[or(c1:LargeDisjunction, c2:Disjunction) : LargeDisjunction
 => c1.lconst :add c2.const1, c1.lconst :add c2.const2,
    closeBoolConstraint(c1), // fill the offset, nbConst, status slots
    c1]
[or(c1:Disjunction, c2:LargeDisjunction) : LargeDisjunction => c2 or c1] 

// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v0.21 <fl> initialize lstatus -> v1.02 done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
// claire3 port: strongly typed lists
[or(c1:Disjunction, 
    c2:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Conjunction U LargeConjunction)) : LargeDisjunction
;    c2:(IntConstraint U CompositeConstraint)) : LargeDisjunction  // caused undue warnings...
 -> let c := LargeDisjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2)) in
      (closeBoolConstraint(c), // v0.37 fill the internal offset slot + status slot (v1.02)
       c)]
[or(c1:LargeDisjunction, 
    c2:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Conjunction U LargeConjunction)) : LargeDisjunction // v0.30 more precise signature
;    c2:(IntConstraint U CompositeConstraint)) : LargeDisjunction // caused undue warnings...
 -> c1.lconst :add c2,
    closeBoolConstraint(c1), // v0.37 fill the internal offset slot
    c1]
// v1.0: the "or" operator is commutative 
;[or(c1:(IntConstraint U CompositeConstraint), 
[or(c1:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Conjunction U LargeConjunction),
    c2:Disjunction) : LargeDisjunction => c2 or c1] 
;[or(c1:(IntConstraint U CompositeConstraint), 
[or(c1:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Conjunction U LargeConjunction),
    c2:LargeDisjunction) : LargeDisjunction => c2 or c1] 

// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
[or(c1:(IntConstraint U SetConstraint U CompositeConstraint), c2:(IntConstraint U SetConstraint U CompositeConstraint)) : Disjunction // v0.30 more precise signature
 -> let d := Disjunction(const1 = c1, const2 = c2) in
       (closeBoolConstraint(d), // v1.05
        d)]

// 4. additional (non binary) method definition (new in 0.9901)
[or(l:list[AbstractConstraint]) : LargeDisjunction
 -> if (length(l) = 0) error("empty disjunction is not a valid constraint") // v1.011 <thb>
    else let c := LargeDisjunction(lconst = list<AbstractConstraint>{subc | subc in l}) in // v1.011 <thb> strongly typed list
       (closeBoolConstraint(c),
        c)]

// ----- Conjunctions -----------

// 1. Associative rules for building conjunctions from two conjunctions
// claire3 port: strongly typed lists
[and(c1:Conjunction, c2: Conjunction) : LargeConjunction
 -> let c := LargeConjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2.const1,c2.const2)) in  // v1.02
       (closeBoolConstraint(c),
        c)]
[and(c1:LargeConjunction, c2:LargeConjunction) : LargeConjunction
 -> let c := LargeConjunction(lconst = c1.lconst /+ c2.lconst) in
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
        c)]
[and(c1:LargeConjunction, c2:Conjunction) : LargeConjunction
 => c1.lconst :add c2.const1, c1.lconst :add c2.const2,
    closeBoolConstraint(c1), // fill the internal offset slot
    c1]
[and(c1:Conjunction, c2:LargeConjunction) : LargeConjunction => c2 and c1] 

// 2. Associative rules for building disjunctions from one disjunction and a non disjunctive constraint
//   v1.02 status initialization is done through a call to closeBoolConstraint
//   v1.02 more precise signature (c2:IntConstraint U CompositeConstraint)
//   in order to avoid a few undue warnings at load time (a slight Claire weakness)
// claire3 port: strongly typed lists
[and(c1:Conjunction, 
     c2:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Disjunction U LargeDisjunction)) : LargeConjunction
;     c2:(IntConstraint U CompositeConstraint)) : LargeConjunction // v0.30 more precise signature
 -> let c := LargeConjunction(lconst = list<AbstractConstraint>(c1.const1,c1.const2,c2)) in
       (closeBoolConstraint(c), // v0.37 fill the internal offset slot
        c)]
[and(c1:LargeConjunction, 
     c2:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Disjunction U LargeDisjunction)) : LargeConjunction
;     c2:(IntConstraint U CompositeConstraint)) : LargeConjunction // v0.30 more precise signature
 -> c1.lconst :add c2,
    closeBoolConstraint(c1), // v0.37 fill the internal offset slot
    c1]
// v1.0: the "and" operator is commutative 
[and(c1:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Disjunction U LargeDisjunction), 
     c2:Conjunction) : LargeConjunction => c2 and c1] 
[and(c1:(IntConstraint U SetConstraint U Cardinality U Guard U Equiv U Disjunction U LargeDisjunction), 
     c2:LargeConjunction) : LargeConjunction => c2 and c1] 

// 3. Basic rule for building a simple disjunction from two non disjunctive constraints
[and(c1:(IntConstraint U SetConstraint U CompositeConstraint), c2:(IntConstraint U SetConstraint U CompositeConstraint)) : Conjunction // v0.30 more precise signature
 -> let c := Conjunction() in
      (write(const1,c,c1),
       write(const2,c,c2),
       closeBoolConstraint(c), // v1.05
       c)]

// 4. additional (non binary) method definition (new in 0.9901)
[and(l:list[AbstractConstraint]) : LargeConjunction
 -> if (length(l) = 0) error("empty conjunction is not a valid constraint") // v1.011 <thb>
    else let c := LargeConjunction(lconst = list<AbstractConstraint>{subc | subc in l}) in // v1.011 <thb> strongly typed list
       (closeBoolConstraint(c),
        c)]

// ----- Complete and lightweight guards + equivalence -----------

// v0.97: the "implies" guard now achieves both ways consistency (back propagating guard onto the triggerring condition)
claire/implies :: operation()
[claire/implies(c1:(IntConstraint U SetConstraint U CompositeConstraint), c2:(IntConstraint U SetConstraint U CompositeConstraint)) : (Disjunction U LargeDisjunction)
 -> opposite(c1) or c2]

// this is the old implies: a lightweight implementation (incomplete propagation)
[claire/ifThen(c1:(IntConstraint U SetConstraint U CompositeConstraint), c2:(IntConstraint U SetConstraint U CompositeConstraint)) : Guard // v0.30 more precise signature
 -> let g := Guard() in
      (write(const1,g,c1),
       write(const2,g,c2),
       closeBoolConstraint(g), // v1.05
       g)]

// v0.29: there is now an explicit correspondance between
// the indices of variables in e.const1 and in e.oppositeConst1
// most of the time, computeVarIdxInOpposite computes the same index, but not for linear constraint
claire/ifOnlyIf :: operation(precedence = precedence(&))
[claire/ifOnlyIf(c1:(IntConstraint U SetConstraint U CompositeConstraint), c2:(IntConstraint U SetConstraint U CompositeConstraint)) : Equiv // v0.30 more precise signature
 -> let e := Equiv() in
      (write(const1,e,c1),
       write(const2,e,c2),
       closeBoolConstraintWCounterOpp(e), // v0.37 fill the internal offset slot
       e)]

// ----- Cardinality constraints -----------

// v0.9904 
// claire3 port: strongly typed lists
[choco/makeCardinalityConstraint(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:IntVar,
      atleast:boolean, atmost:boolean) : Cardinality // v0.30 more precise signature
 -> assert(forall(c in l | getNbVars(opposite(c)) = getNbVars(c))),
    let n := length(l),
        c := Cardinality(lconst = list<AbstractConstraint>{c | c in l}, 
                         nbConst = n,
                         additionalVars = list<IntVar>(v),  // v1.02 new data structure
                         constrainOnInfNumber = atleast,
                         constrainOnSupNumber = atmost
                         ) in
      (closeBoolConstraintWCounterOpp(c),  // v1.02
       c)]

// v0.9904: extended API
[choco/card(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:IntVar) : Cardinality // v0.30 more precise signature
 -> makeCardinalityConstraint(l,v,true,true)] 
[choco/atleast(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:IntVar) : Cardinality // v0.30 more precise signature
 -> makeCardinalityConstraint(l,v,true,false)]
[choco/atmost(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:IntVar) : Cardinality // v0.30 more precise signature
 -> makeCardinalityConstraint(l,v,false,true)]

// <thb> v0.93: allows an integer as second parameter
[choco/card(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:integer) : Cardinality
 -> card(l,makeConstantIntVar(v))]
[choco/atleast(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:integer) : Cardinality
 -> atleast(l,makeConstantIntVar(v))]
[choco/atmost(l:list[(IntConstraint U SetConstraint U CompositeConstraint)], v:integer) : Cardinality
 -> atmost(l,makeConstantIntVar(v))]

// ********************************************************************
// *   Part 6: global constraints                                     *
// ********************************************************************

// changed v0.28
// claire3 port: building a staticly typed vars lisit instead of a simple copy
[choco/allDifferent(l:list[IntVar]) : AllDiff
 -> let c := AllDiff(vars = list<IntVar>{v | v in l}, cste = 0) in
       (closeLargeIntConstraint(c),
        c)]

//  OccurTerm: a temporary object representing occur(t,l)
// v0.9907 inherit from Term
choco/OccurTerm <: Term(
    target:integer,
    lvars:list[IntVar])

// changed v0.28, v0.32
// claire3 port: strongly typed lists
[private/makeOccurConstraint(ot:OccurTerm,v:IntVar,
                             atleast:boolean,atmost:boolean) : Occurrence
 -> let l := ot.lvars, n := length(l), nb1 := 0, nb2 := 0,
        isPos := make_array(n,boolean,true),  // v1.011 isPossible is now an array
        isSur := make_array(n,boolean,false),
        tgt := ot.target, c := Occurrence(vars = list<IntVar>{vv | vv in l} add v, cste = tgt) in
     (closeLargeIntConstraint(c),   // v0.34
      for j in (1 .. n)
         let v := (l[j] as IntVar) in
            (if (v canBeInstantiatedTo tgt)
                (nb1 :+ 1,
                 if (v isInstantiatedTo tgt)
                    (isSur[j] := true, nb2 :+ 1))
             else isPos[j] := false),
      c.isPossible := isPos,
      c.isSure := isSur,
      c.nbPossible := nb1,
      c.nbSure := nb2,
      c.constrainOnInfNumber := atleast,      // v0.9907
      c.constrainOnSupNumber := atmost,
      c)]

[choco/occur(tgt:integer, l:list[IntVar]) : OccurTerm
 -> OccurTerm(target = tgt, lvars = copy(l)) ]

// Occurrence constraints are always stated as
//   occur(t,l) (==/>=/<=) x/v
// watchout: arithmetic is not complete.
[==(ot:OccurTerm,x:IntVar) : Occurrence
 -> makeOccurConstraint(ot,x,true,true)]
[==(x:IntVar,ot:OccurTerm) : Occurrence -> (ot == x)]
[==(ot:OccurTerm,x:integer) : Occurrence
 -> makeOccurConstraint(ot,makeConstantIntVar(x),true,true)]
[==(x:integer,ot:OccurTerm) : Occurrence -> (ot == x)]
[>=(ot:OccurTerm,x:IntVar) : Occurrence
 -> makeOccurConstraint(ot,x,true,false)]
[>=(ot:OccurTerm,x:integer) : Occurrence
 -> makeOccurConstraint(ot,makeConstantIntVar(x),true,false)]
[<=(ot:OccurTerm,x:IntVar) : Occurrence
 -> makeOccurConstraint(ot,x,false,true)]
[<=(ot:OccurTerm,x:integer) : Occurrence
 -> makeOccurConstraint(ot,makeConstantIntVar(x),false,true)]

// v0.24 Symmetrical API's (a request from Michel Lemaitre)
[>=(x:IntVar,ot:OccurTerm) : Occurrence -> (ot <= x)]
[>=(x:integer,ot:OccurTerm) : Occurrence -> (ot <= x)]
[<=(x:IntVar,ot:OccurTerm) : Occurrence -> (ot >= x)]
[<=(x:integer,ot:OccurTerm) : Occurrence -> (ot >= x)]

//  for Eric: EltTerm: a temporary object representing l[I]
choco/EltTerm <: Term(
    lvalues:list[integer],
    indexVar:IntVar)

// 1.020: the nth method is replaced by getNth
// v0.34: allows positive UnTerm as indexes (I + c)
[choco/getNth(l:list[integer], i:UnTerm) : EltTerm
 -> if not(i.sign1) error("Negative indexes (-I + c) are not allowed in an element constraint"),
    EltTerm(lvalues = l, indexVar = i.v1, cste = i.cste)]
[choco/getNth(l:list[integer], i:IntVar) : EltTerm
 -> EltTerm(lvalues = l, indexVar = i, cste = 0)]
 
[claire/==(t:EltTerm,x:IntVar) : Elt // v0.9907 add return type
 -> makeEltConstraint(t.lvalues,t.indexVar,x,t.cste)]  //v0.34 added the offset t.cste
//v0.93: added the reverse syntax
[claire/==(x:IntVar,t:EltTerm) : Elt // v0.9907 add return type
 -> makeEltConstraint(t.lvalues,t.indexVar,x,t.cste)]

//v0.93: allows equality with an integer
[claire/==(t:EltTerm,x:integer) : Elt // v0.9907 add return type
 -> makeEltConstraint(t.lvalues,t.indexVar,makeConstantIntVar(x),t.cste)]  //v0.34 added the offset t.cste
[claire/==(x:integer,t:EltTerm) : Elt // v0.9907 add return type
 -> makeEltConstraint(t.lvalues,t.indexVar,makeConstantIntVar(x),t.cste)]

choco/Elt2Term <: Term(
    valueTable:table[range = integer],
    dim1:integer = 0,
    dim2:integer = 0,
    index1Var:IntVar,
    index2Var:IntVar)
// v0.9907 elt with 5 arguments becomes makeElt2Term
[choco/makeElt2Term(l:table[range = integer], n1:integer,n2:integer,i:IntVar, j:IntVar) : Elt2Term
 -> Elt2Term(valueTable = l,index1Var = i, index2Var = j, cste = 0,dim1 = n1, dim2 = n2)]
// 1.020 renamed nth into getNth
[choco/getNth(l:table[range = integer], i:IntVar, j:IntVar) : Elt2Term
// claire3 port: module System disappeared
 -> makeElt2Term(l,size(domain(l).arg[1]),size(domain(l).arg[2]),i,j)]
[claire/==(t:Elt2Term,x:IntVar) : Elt2 // v0.9907 add return type
 -> makeElt2Constraint(t.valueTable,t.dim1,t.dim2,t.index1Var,t.index2Var,x)]
[claire/==(x:IntVar,t:Elt2Term) : Elt2 // v0.9907 add return type
 -> makeElt2Constraint(t.valueTable,t.dim1,t.dim2,t.index1Var,t.index2Var,x)]
[claire/==(t:Elt2Term,x:integer) : Elt2 // v0.9907 add return type
 -> makeElt2Constraint(t.valueTable,t.dim1,t.dim2,t.index1Var,t.index2Var,makeConstantIntVar(x))]
[claire/==(x:integer,t:Elt2Term) : Elt2 // v0.9907 add return type
 -> makeElt2Constraint(t.valueTable,t.dim1,t.dim2,t.index1Var,t.index2Var,makeConstantIntVar(x))]

// ********************************************************************
// *   Part 7: adding information                                     *
// ********************************************************************

// v0.9906: rewritten
// v0.9907: merged the definitions from IntConstraint and DelayedConstraint into AbstractConstraint
// v1.010: one single restriction for post@Problem
[choco/post(p:Problem, c0:AbstractConstraint) : void //v0.30 replaced GlobalConstraint by DelayedConstraint
 -> let c := c0 in
      (case c0 (LinComb
                  (if (getNbVars(c0) >= p.propagationEngine.delayLinCombThreshold)
                      c := delay(c0,2)), // 0.30: delay propagation for large linear constraints
                Delayer (if (c0.priority <= 0)
                            c := c0.target)),// v1.012 undelaying constraints that are posted as delay(c,0/-1)
       p.constraints :add c,
       connect(c),
       let pe := p.propagationEngine, prio := getPriority(c),
           e := ConstAwakeEvent(touchedConst = c, initialized = false, priority = prio) in
        (c.constAwakeEvent := e,
         registerEvent(pe,e),
         postConstAwake(pe,c,true)))]

// further constraining a domain: adding information to the current state
// (restricting by hand the domain of a variable)
[choco/setMin(v:IntVar, x:integer) : void => updateInf(v,x,0)]
[choco/setMax(v:IntVar, x:integer) : void => updateSup(v,x,0)]
[choco/setVal(v:IntVar, x:integer) : void => instantiate(v,x,0)]
[choco/remVal(v:IntVar, x:integer) : void => removeVal(v,x,0)]

// v1.02 using the targetStatus slots
// v1.103: same signatures for setBranch
[choco/setBranch(d:Disjunction, i:integer, b:boolean) : void
 -> setTargetStatus(d,i,b),
    if b
      (if (i = 1) doAwake(d.const1)
       else doAwake(d.const2))]
[choco/setBranch(d:LargeDisjunction, i:integer, b:boolean) : void
-> setTargetStatus(d,i,b),
   if b doAwake(d.lconst[i]) ]  // v1.02: no propagation of counterpart for largedisjunctions (see cardinality for this)

[choco/setBranch(c:Cardinality, i:integer, b:boolean) : void
 -> if not(knownStatus(c,i))
       (setStatus(c, i, b),
        if b (c.nbTrueStatus :+ 1, doAwake(c.lconst[i]), awakeOnNbTrue(c))
        else (c.nbFalseStatus :+ 1, doAwake(c.loppositeConst[i]), awakeOnNbFalse(c)))
    else if (getStatus(c,i) != b) raiseContradiction(c) ]

// v0.9906
[choco/propagate(p:Problem) : void
 -> let pe := p.propagationEngine, someEvents := true in
       (while someEvents
           (when q := getNextActiveEventQueue(pe) in 
                 popSomeEvents(q)
            else someEvents := false))]

// v1.0: controlling the amount of propagation (useful for dynamic constraint posts...)
[choco/setActive(c:AbstractConstraint) : void
 -> let rootConstraint := getConstraint(getVar(c,1),getConstraintIdx(c,1)) in
       (if (rootConstraint = c) 
           (if not(isActive(c))
              (reconnect(c),
               constAwake(c,true)))
        else case rootConstraint 
           (Delayer (if (rootConstraint.target = c) setActive(rootConstraint))) )]

[choco/setPassive(c:AbstractConstraint) : void
 -> let rootConstraint := getConstraint(getVar(c,1),getConstraintIdx(c,1)) in
       (if (rootConstraint = c) 
           (if  isActive(c)
              (disconnect(c),
               let pb := getProblem(c),
                   evt := c.constAwakeEvent,
                   q := getQueue(pb.propagationEngine, evt) in
                 remove(q,evt)))
        else case rootConstraint 
           (Delayer (if (rootConstraint.target = c) setPassive(rootConstraint))) )]

// ********************************************************************
// *   Part 8: searching for solutions / optimization                 *
// ********************************************************************

[choco/getProblem(algo:Solver) : Problem -> algo.problem]
[choco/getSearchManager(b:AbstractBranching) : GlobalSearchSolver -> b.manager]

// v1.016: new branching heuristic objects
[choco/makeMinDomVarHeuristic() -> MinDomain()]
[choco/makeDomDegVarHeuristic() -> DomDeg()]
[choco/makeMaxDegVarHeuristic() -> MaxDeg()]
[choco/makeStaticVarHeuristic(l:list[IntVar])
 -> StaticVarOrder(vars = list<IntVar>{v | v in l})]

// new API methods
[choco/makeMinDomVarHeuristic(l:list[IntVar]) : MinDomain
 -> MinDomain(vars = list<IntVar>{v | v in l})]
[choco/makeDomDegVarHeuristic(l:list[IntVar]) : DomDeg
 -> DomDeg(vars = list<IntVar>{v | v in l})]
[choco/makeMaxDegVarHeuristic(l:list[IntVar]) : MaxDeg
 -> MaxDeg(vars = list<IntVar>{v | v in l})]

[choco/makeIncValIterator() -> IncreasingDomain()]
[choco/makeDecValIterator() -> DecreasingDomain()]

[choco/makeMinValSelector() -> MinValHeuristic()]
[choco/makeMaxValSelector() -> MaxValHeuristic()]
[choco/makeMidValSelector() -> MidValHeuristic()]

[choco/makeAssignVarBranching(varh:VarSelector, valh:ValIterator) : AssignVar
 -> let av := AssignVar(varHeuristic = varh, valHeuristic = valh) in
      (varh.branching := av,
       valh.branching := av,
       av)]
[choco/makeAssignVarBranching(varh:VarSelector) : AssignVar
 -> makeAssignVarBranching(varh, makeIncValIterator())]
[choco/makeAssignVarBranching() : AssignVar
 -> makeAssignVarBranching(makeMinDomVarHeuristic(), makeIncValIterator())]

[choco/makeSplitDomBranching(varh:VarSelector, valh:ValSelector, threshold:integer) : SplitDomain
 -> let sd := SplitDomain(varHeuristic = varh, valHeuristic = valh, dichotomyThreshold = threshold) in
      (varh.branching := sd,
       valh.branching := sd,
       sd)]
[choco/makeSplitDomBranching(varh:VarSelector, threshold:integer) : SplitDomain
 -> makeSplitDomBranching(varh, makeMidValSelector(), threshold)]
[choco/makeSplitDomBranching(varh:VarSelector) : SplitDomain
 -> makeSplitDomBranching(varh, makeMidValSelector(), 5)]
[choco/makeSplitDomBranching() : SplitDomain
 -> makeSplitDomBranching(makeMinDomVarHeuristic(), makeMidValSelector(), 5)]

[choco/makeAssignOrForbidBranching(varh:VarSelector, valh:ValSelector) : AssignOrForbid
 -> let af := AssignOrForbid(varHeuristic = varh, valHeuristic = valh) in
      (varh.branching := af,
       valh.branching := af,
       af)]

// v1.010 building branching objects
[choco/makeDisjunctionBranching(pb:Problem) : SettleBinDisjunction
 -> let ldisj := list<Disjunction>() in
     (for c in pb.constraints
        (case c (Disjunction ldisj :add c)),
      SettleBinDisjunction(disjunctions = ldisj))]

//  the default standard procedure in Choco:
//  first settle suspended disjunctions, then split domains, then instantiate
[choco/makeDefaultBranchingList(pb:Problem) : list[AbstractBranching]
 => list<AbstractBranching>(
             makeDisjunctionBranching(pb),
             makeSplitDomBranching(makeMinDomVarHeuristic(), makeMidValSelector(), 5),
             makeAssignVarBranching(makeMinDomVarHeuristic(), makeIncValIterator())
             )]

// v1.0 new API methods
[choco/makeGlobalSearchSolver(allSolutions:boolean, l:list[AbstractBranching]) : Solve
 -> composeGlobalSearch(Solve(stopAtFirstSol = not(allSolutions)),l)]

// v1.02 initialize the stopAtFirstSol slot
[choco/makeGlobalSearchMaximizer(obj:IntVar, restartSearch:boolean, l:list[AbstractBranching]) : AbstractOptimize
 -> if restartSearch 
       composeGlobalSearch(OptimizeWithRestarts(objective = obj, doMaximize = true, stopAtFirstSol = true),l)
    else composeGlobalSearch(BranchAndBound(objective = obj, doMaximize = true, stopAtFirstSol = false),l)]

// v1.02 initialize the stopAtFirstSol slot
[choco/makeGlobalSearchMinimizer(obj:IntVar, restartSearch:boolean, l:list[AbstractBranching]) : AbstractOptimize
 -> if restartSearch 
       composeGlobalSearch(OptimizeWithRestarts(objective = obj, doMaximize = false, stopAtFirstSol = true),l)
    else composeGlobalSearch(BranchAndBound(objective = obj, doMaximize = false, stopAtFirstSol = false),l)]

// default methods
// v1.010: add the Problem argument
[choco/makeGlobalSearchSolver(allSolutions:boolean) : Solve
 -> makeGlobalSearchSolver(allSolutions,makeDefaultBranchingList(getActiveProblem()))]

[choco/makeGlobalSearchMaximizer(obj:IntVar, restartSearch:boolean) : AbstractOptimize
 -> makeGlobalSearchMaximizer(obj, restartSearch,makeDefaultBranchingList(getActiveProblem()))]

[choco/makeGlobalSearchMinimizer(obj:IntVar, restartSearch:boolean) : AbstractOptimize
 -> makeGlobalSearchMinimizer(obj, restartSearch, makeDefaultBranchingList(getActiveProblem()))]

[choco/makeBacktrackLimit(nb:integer) : NodeLimit -> NodeLimit(maxNb = nb)]
[choco/makeNodeLimit(nb:integer) : BacktrackLimit -> BacktrackLimit(maxNb =  nb)] 
[choco/setSearchLimit(algo:GlobalSearchSolver, lim:GlobalSearchLimit) : void 
 -> algo.limits :add lim]
 
// -- start: deprecated API functions
// API functions for the default search procedure
[choco/solve(pb:Problem,all:boolean) : boolean
 -> solve(pb, makeDefaultBranchingList(pb), all)]
[choco/minimize(pb:Problem,obj:IntVar,restart:boolean) : integer
 -> minimize(pb, obj, makeDefaultBranchingList(pb), restart)]
[choco/maximize(pb:Problem,obj:IntVar,restart:boolean) : integer
 -> maximize(pb, obj, makeDefaultBranchingList(pb), restart)]

// New API functions
// v1.08 attachGlobalSearchSolver -> attach
[choco/solve(pb:Problem,l:list[AbstractBranching],all:boolean) : boolean
 -> let algo := makeGlobalSearchSolver(all, l) in
       (attach(algo,pb), run(algo), getFeasibility(algo))]  // v1.02: explicit access to feasibility

// v1.05 <thb> getBestObjectiveValue
[choco/minimize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) : integer
 -> let algo := makeGlobalSearchMinimizer(obj, restart, l) in 
       (attach(algo,pb), run(algo), getBestObjectiveValue(algo))]  // v1.02: explicit access to obj. value

[choco/maximize(pb:Problem,obj:IntVar,l:list[AbstractBranching],restart:boolean) : integer
 -> let algo := makeGlobalSearchMaximizer(obj, restart, l) in 
       (attach(algo,pb), run(algo), getBestObjectiveValue(algo))]  // v1.02: explicit access to obj. value
// -- end: deprecated API functions

// controlling the search
// v1.08 these functions no longer apply to Problem, but to GlobalSearchSolver
[choco/setMaxPrintDepth(algo:GlobalSearchSolver, n:integer) : void
 -> algo.maxPrintDepth := n]

// v1.013 new API function
[choco/setMaxSolutionStorage(algo:GlobalSearchSolver, nb:integer) : void
 -> algo.maxSolutionStorage := nb]

[choco/setVarsToShow(algo:GlobalSearchSolver, l:list[IntVar]) : void
 -> algo.varsToShow := copy(l)]

[choco/getNbSol(algo:GlobalSearchSolver) : integer
 -> algo.nbSol]

[choco/getProblem(b:AbstractBranching) : Problem
 -> b.manager.problem]

// v1.322 new API method
[choco/getGlobalSearchSolver(p:Problem) : GlobalSearchSolver
 -> p.globalSearchSolver]
  
// -------- new from v1.020: introducing limit objects

[choco/setNodeLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add NodeLimit(maxNb = n)]
[choco/setTimeLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add TimeLimit(maxMsec = n)]
[choco/setBacktrackLimit(algo:GlobalSearchSolver, n:integer) : void
 -> algo.limits :add BacktrackLimit(maxNb = n)]
// deprecated API: mentionned for upward compatibility 
[choco/setMaxNbBk(algo:GlobalSearchSolver, n:integer) : void
 -> setBacktrackLimit(algo,n)]
 
// ********************************************************************
// *   Part 9: searching for solutions / (assign & repair) local opt. *
// ********************************************************************

// API functions -> delegate work to the "invariant maintainer"
// Three new events:
[choco/assignVal(v:IntVar, a:integer) : void
 -> //[IDEBUG] assign ~S: ~S // v,a,
    when val := get(value,v) in
        (if (val != a) error("wrong event -assign- for a change of value assignment on ~S",v)
         // otherwise do nothing (no change)
        )
    else (v.value := a,
          postAssignVal(v.problem.invariantEngine,v,a))]

[choco/unassignVal(v:IntVar) : void
 -> //[IDEBUG] unassign ~S // v,
    if known?(value,v)
      (postUnAssignVal(v.problem.invariantEngine,v),
       erase(value,v))]

[choco/changeVal(v:IntVar,b:integer) : void
 -> when a := get(value,v) in
      (if (b != a)
          (postChangeVal(v.problem.invariantEngine,v,a,b),
           v.value := b)
       // otherwise, not a real change, do nothing
       )
    else error("can not change the value of an unassigned variable :~S",v)]

private/DEFAULT_BUILD :: AssignmentHeuristic()
private/DEFAULT_MOVE :: FlipNeighborhood()

// controlling the search
[choco/setMaxNbLocalSearch(pb:Problem, n:integer) : void
 -> pb.localSearchSolver.maxNbLocalSearch := n]

[choco/setMaxNbLocalMoves(pb:Problem, n:integer) : void
 -> pb.localSearchSolver.maxNbLocalMoves := n]

[choco/move(pb:Problem) : integer
 -> move(pb, DEFAULT_BUILD, DEFAULT_MOVE)]

[choco/move(pb:Problem, b:ConstructiveHeuristic, m:MoveNeighborhood) : integer
 -> let algo := MultipleDescent(buildAssignment = b,changeAssignment = m) in
     (m.manager := algo,
      b.manager := algo,
      attachLocalSearchSolver(pb,algo),
      run(algo),
      getObjectiveValue(pb.invariantEngine))]

