[fact(n:integer) : integer
 -> if (n <= 1) 1 else n * fact(n - 1)]

[testAllDiff1(n:integer)
 -> //[ICETEST_VIEW] testing alldiff to generate permutations of (1 .. n)[~S] // n,
    let pb := choco/makeProblem("test all diff on (1 .. n)",n),
        x := list{choco/makeIntVar(pb,"x" /+ string!(i),1,n) | i in (1 .. n)} in
      (choco/post(pb,choco/completeAllDiff(x)),
       let algo := choco/makeGlobalSearchSolver(true) in
         (choco/attach(algo,pb),
          choco/run(algo),
          if (choco/getNbSol(algo) != fact(n))
             error("wrong number of solutions:~S instead of ~S",
                   choco/getNbSol(algo),fact(n)) ))]

[testAllDiff2(n:integer, a:integer)
 -> //[ICETEST_VIEW] testing alldiff to generate permutations of (a + 1 .. a + n)[~S,~S] // n,a,
    let pb := choco/makeProblem("test all diff on any sequence of values",n),
        x := list{choco/makeIntVar(pb,"x" /+ string!(i),a + 1, a + n) | i in (1 .. n)} in
      (choco/post(pb,choco/completeAllDiff(x)),
       let algo := choco/makeGlobalSearchSolver(true) in
         (choco/attach(algo,pb),
          choco/run(algo),
          if (choco/getNbSol(algo) != fact(n))
             error("wrong number of solutions:~S instead of ~S",
                   choco/getNbSol(algo),fact(n)) ))]

[testAllDiff3(n:integer,m:integer,a:integer)
 -> //[ICETEST_VIEW] testing alldiff to generate combinations of ~S values among ~S // n,m,
    let pb := choco/makeProblem("test all diff with too many values",n),
        x := list{choco/makeIntVar(pb,"x" /+ string!(i),a + 1, a + m) | i in (1 .. n)} in
      (choco/post(pb,choco/completeAllDiff(x)),
       let algo := choco/makeGlobalSearchSolver(true),
           nbcomb := fact(m) / fact(m - n) in
         (choco/attach(algo,pb),
          choco/run(algo),
          if (choco/getNbSol(algo) != nbcomb)
             error("wrong number of solutions:~S instead of ~S",
                   choco/getNbSol(algo),nbcomb) ))]

[testPermutation(n:integer)
 -> //[ICETEST_VIEW] testing permutation to generate permutations of (1 .. n)[~S] // n,
    let pb := choco/makeProblem("test all diff",2 * n),
        x := list{choco/makeIntVar(pb,"x" /+ string!(i),1,n) | i in (1 .. n)},
        y := list{choco/makeIntVar(pb,"y" /+ string!(i),1,n) | i in (1 .. n)} in
      (choco/post(pb,choco/permutation(x,y)),
       let algo := choco/makeGlobalSearchSolver(true) in
         (choco/attach(algo,pb),
          choco/run(algo),
          if (choco/getNbSol(algo) != fact(n))
             error("wrong number of solutions:~S instead of ~S",
                   choco/getNbSol(algo),fact(n)) ))]

[testGCC()
 -> //[ICETEST_VIEW] testing simple example of GCC from Regin// ,
    let pb := choco/makeProblem("Work assignment",12),
        persons := list(choco/makeIntVar(pb,"peter",{1,2}),
                        choco/makeIntVar(pb,"paul",{1,2}),
                        choco/makeIntVar(pb,"mary",{1,2}),
                        choco/makeIntVar(pb,"john",{1,2}),
                        choco/makeIntVar(pb,"bob",{1,2,3}),
                        choco/makeIntVar(pb,"mike",{2,3,4,5}),
                        choco/makeIntVar(pb,"julia",{3,5}) ),
        capacities := list((1 .. 2), (1 .. 2), (1 .. 1), (0 .. 2), (0 .. 2)) in
      (choco/post(pb,choco/gcc(persons,capacities)),
       let algo := choco/makeGlobalSearchSolver(true) in
         (choco/attach(algo,pb),
          choco/run(algo),
          if (choco/getNbSol(algo) != 12)
             error("wrong number of solutions:~S instead of 12",
                   choco/getNbSol(algo)) ))]

