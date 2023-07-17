// ********************************************************************
// * CHOCO, version 0.99 feb. 25th 2001                               *
// * file: opapi.cl                                                   *
// *    API (public methods) using operators for stating constraints  *
// * Copyright (C) F. Laburthe, 1999-2000, see readme.txt             *
// ********************************************************************

// ------------------  File Overview  ---------------------------------
// *   Part 4: Arithmetic constraints                                 *
// *   Part 5: Boolean connectors                                     *
// --------------------------------------------------------------------
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
 -> let oppc1 := opposite(c1) in 
       (oppc1 or c2)]

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

