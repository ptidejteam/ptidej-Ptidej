// benchmark : set N queens on a N*N chessboard

NBQUEENSOL :: list(0,0,0,2,10,4,40,92,352,724,2680,14200,73712)

[queen0(n:integer, all:boolean)
 -> trace(CHOCOBENCH_VIEW,"~A queens [~A] (bin)  : ",stringFormat(n,3),(if all "all" else "one")),
    time_set(),
    let pb := choco/makeProblem("GAUSS: N queens on a N*N chessboard",n),
        // create variables
        queens := list{ choco/makeIntVar(pb,"Q" /+ string!(i), 1, n) | i in (1 .. n) } in
      (// diagonal constraints
       for i in (1 .. n)
         for j in (i + 1 .. n)
            let k := j - i in
              ( choco/post(pb, queens[i] !== queens[j]),
                choco/post(pb, queens[i] !== queens[j] + k),
                choco/post(pb, queens[j] !== queens[i] + k) ),
       solveQueenPb(pb,n,all) ),
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t) ]

[queen1(n:integer, all:boolean)
 -> trace(CHOCOBENCH_VIEW,"~A queens [~A] (n-ary): ",stringFormat(n,3),(if all "all" else "one")),
    time_set(),
    let pb := choco/makeProblem("GAUSS: N queens on a N*N chessboard",3 * n),
        // create variables
        queens := list{ choco/makeIntVar(pb,"Q" /+ string!(i), 1, n) | i in (1 .. n) },
        diag1 := list{ choco/makeIntVar(pb,"DD" /+ string!(i), 2, 2 * n) | i in (1 .. n) },
        diag2 := list{ choco/makeIntVar(pb,"ID" /+ string!(i), -(n - 1), n) | i in (1 .. n) } in
      (for i in (1 .. n) choco/post(pb, diag1[i] == queens[i] + i),
       for i in (1 .. n) choco/post(pb, diag2[i] == queens[i] - i),
       // horizontal constraints
       choco/post(pb, choco/allDifferent(queens)),
       // direct diagonal constraint
       choco/post(pb, choco/allDifferent(diag1)),
       // indirect diagonal constraint
       choco/post(pb, choco/allDifferent(diag2)),
       solveQueenPb(pb,n,all) ),
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t) ]

[solveQueenPb(pb:choco/Problem, n:integer, all:boolean) : void
 -> let algo := choco/makeGlobalSearchSolver(all) in
      (choco/setMaxNbBk(algo,10000000),
       choco/attach(algo,pb),
       choco/run(algo),
       if (all & n <= 13)
         (if (choco/getNbSol(algo) != NBQUEENSOL[n])
             error("queen(~S,true) finds ~S solutions instead of ~S",
                    n,choco/getNbSol(algo),NBQUEENSOL[n])),
       choco/discardProblem(pb)  // v1.010
      )]

