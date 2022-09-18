[send()
 -> trace(CHOCOBENCH_VIEW,"SEND+MORE=MONEY         : "),
    time_set(),
     let p := choco/makeProblem("CRYPT-ARITHMETIC: send more money",20),
        s := choco/makeIntVar(p,"S",1 ,9),
        e := choco/makeIntVar(p,"E",0 ,9),
        n := choco/makeIntVar(p,"N",0 ,9),
        d := choco/makeIntVar(p,"D",0 ,9),
        m := choco/makeIntVar(p,"M",1 ,9),
        o := choco/makeIntVar(p,"O",0, 9),
        r := choco/makeIntVar(p,"R",0 ,9),
        y := choco/makeIntVar(p,"Y",0 ,9),
        Letters := set(s,e,n,d,m,o,r,y),
        Word1 := choco/makeBoundIntVar(p,"SEND",0,1000000),
        Word2 := choco/makeBoundIntVar(p,"MORE",0,1000000),
        Word3 := choco/makeBoundIntVar(p,"MONEY",0,1000000) in
    (for i in (2 .. 8)
       for j in (1 .. i - 1)
         choco/post(p,Letters[i] !== Letters[j]),
     choco/post(p, list(1000,100,10,1) scalar list(s,e,n,d) == Word1),
     choco/post(p, list(1000,100,10,1) scalar list(m,o,r,e) == Word2),
     choco/post(p, list(10000,1000,100,10,1) scalar list(m,o,n,e,y) == Word3),
     choco/post(p, Word1 + Word2 == Word3),
     choco/solve(p,false),
     choco/discardProblem(p),  // v1.010
     let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t)) ]

[send2()
 -> trace(CHOCOBENCH_VIEW,"SEND+MORE=MONEY         : "),
    time_set(),
     let p := choco/makeProblem("CRYPT-ARITHMETIC: send more money (stronger prop.)",20),
        s := choco/makeIntVar(p,"S",1 ,9),
        e := choco/makeIntVar(p,"E",0 ,9),
        n := choco/makeIntVar(p,"N",0 ,9),
        d := choco/makeIntVar(p,"D",0 ,9),
        m := choco/makeIntVar(p,"M",1 ,9),
        o := choco/makeIntVar(p,"O",0, 9),
        r := choco/makeIntVar(p,"R",0 ,9),
        y := choco/makeIntVar(p,"Y",0 ,9),
        Letters := list(s,e,n,d,m,o,r,y),
        Word1 := choco/makeBoundIntVar(p,"SEND",0,1000000),
        Word2 := choco/makeBoundIntVar(p,"MORE",0,1000000),
        Word3 := choco/makeBoundIntVar(p,"MONEY",0,1000000) in
    (choco/post(p,choco/allDifferent(Letters)),
     choco/post(p, list(1000,100,10,1) scalar list(s,e,n,d) == Word1),
     choco/post(p, list(1000,100,10,1) scalar list(m,o,r,e) == Word2),
     choco/post(p, list(10000,1000,100,10,1) scalar list(m,o,n,e,y) == Word3),
     choco/post(p, list(1,1) scalar list(Word1,Word2) == Word3),
     choco/post(p, list(1000,91,1,10,-90,-1,-9000,-900) scalar list(s,e,d,r,n,y,m,o) == 0),
     choco/solve(p,false),
     choco/discardProblem(p),  // v1.010
     let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t)) ]

[send3()
 -> trace(CHOCOBENCH_VIEW,"SEND+MOST=MONEY [all]   : "),
    time_set(),
    let p := choco/makeProblem("CRYPT-ARITHMETIC: send most money",20),
        s := choco/makeIntVar(p,"S",1 ,9),
        e := choco/makeIntVar(p,"E",0 ,9),
        n := choco/makeIntVar(p,"N",0 ,9),
        d := choco/makeIntVar(p,"D",0 ,9),
        m := choco/makeIntVar(p,"M",1 ,9),
        o := choco/makeIntVar(p,"O",0, 9),
        t := choco/makeIntVar(p,"T",0 ,9),
        y := choco/makeIntVar(p,"Y",0 ,9),
        Letters := list(s,e,n,d,m,o,t,y),
        Word1 := choco/makeBoundIntVar(p,"SEND",0,1000000),
        Word2 := choco/makeBoundIntVar(p,"MOST",0,1000000),
        Word3 := choco/makeBoundIntVar(p,"MONEY",0,1000000) in
    (choco/post(p,choco/allDifferent(Letters)),
     choco/post(p, list(1000,100,10,1) choco/scalar list(s,e,n,d) == Word1),
     choco/post(p, list(1000,100,10,1) choco/scalar list(m,o,s,t) == Word2),
     choco/post(p, list(10000,1000,100,10,1) choco/scalar list(m,o,n,e,y) == Word3),
     choco/post(p, list(1,1) choco/scalar list(Word1,Word2) == Word3),
     choco/post(p, list(1010,90,1,1,-90,-1,-9000,-900) choco/scalar list(s,e,d,t,n,y,m,o) == 0),
     choco/solve(p,true),
     choco/discardProblem(p),  // v1.010
     let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t)) ]


// solution (S,E,N,D,M,O,R,Y) = (9,5,6,7,1,0,8,2)
