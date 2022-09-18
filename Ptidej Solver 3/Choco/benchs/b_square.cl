// magic squares
[square(n:integer, help:boolean, dominance:boolean) : void
 -> trace(CHOCOBENCH_VIEW,"magic squares (~A)     : ",stringFormat(n,3)),
    time_set(),
    let n2 := n ^ 2,
        magic := n * (n2 + 1) / 2,
        p := choco/makeProblem("Magic squares ",n2 + 1),
        x := list{choco/makeIntVar(p,"x" /+ string!(i),1,n2) | i in (1 .. n2)},
        s := choco/makeIntVar(p,1,n2 * (n2 + 1) / 2) in
     (choco/post(p,choco/allDifferent(x)),
      if help choco/setVal(s,magic),
      for i in (1 .. n)
        choco/post(p,choco/sumVars(list{x[(i - 1) * n + j] | j in (1 .. n)}) == s),
      for j in (1 .. n)
        choco/post(p,choco/sumVars(list{x[(i - 1) * n + j] | i in (1 .. n)}) == s),
      if dominance
        for i in (1 .. n - 1)
           choco/post(p,x[(i - 1) * n + i] < x[i * n + i + 1]),
      time_set(),

      let algo := choco/makeGlobalSearchSolver(false) in
      (choco/setMaxNbBk(algo,10000000),
       choco/attach(algo,p),
       choco/run(algo),
      let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t),
       if (system.verbose > 0) displaySquare(p,n),
       choco/discardProblem(p)  // v1.010
        ))]

[displaySquare(p:choco/Problem,n:integer) : void
 -> for i in (1 .. n)
      (princ("\n"),
       for j in (1 .. n)
         let v := choco/getVar(p,(i - 1) * n + j) in
            displayVar(v,3))]

[displayVar(x:choco/IntVar, n:integer) : void
 -> printf("~S",x.value),
    for i in (1 .. n - 2) princ(" "),
    if (x.value <= 9) princ(" ")]

