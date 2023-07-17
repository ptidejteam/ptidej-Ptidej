// N :: 300
//
// exemple a 5: 2,1,2,0,0 (21 b. -> 8b.)
// exemple a 10: 6,2,1,0,0,0,1,0,0,0 (819 b. -> 90b.)
//
[magic(n:integer)
 -> trace(CHOCOBENCH_VIEW,"magic series (~A)      : ",stringFormat(n,3)),
    time_set(),
    let p := choco/makeProblem("Magic series " /+ string!(n),n + 1),
        serie := list{choco/makeIntVar(p,"x" /+ string!(i),0,n) | i in (1 .. n)},
        serie2 := list{serie[i] | i in (2 .. n)},
        serie3 := list{serie[i] | i in (3 .. n)} in
     (for v in serie
         (choco/setMin(v,0), choco/setMax(v,n)),
      for i in (1 .. n) choco/post(p,choco/occur(i - 1,serie) == serie[i]),      // problem definition
;      choco/post(p, make_list(n,1) choco/scalar serie == n),                    // simple redundent
      choco/post(p, list{1 | i in (1 .. n)} choco/scalar serie == n),            // simple redundent
      choco/post(p, list{i | i in (1 .. n - 1)} choco/scalar serie2 == n),       // killer redundent
      choco/post(p, list{i | i in (1 .. n - 2)} choco/scalar serie3 == serie[1]),// mega killer redundent
      choco/propagate(p),
      choco/solve(p,false),
      choco/discardProblem(p),  // v1.010
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t)) ]

