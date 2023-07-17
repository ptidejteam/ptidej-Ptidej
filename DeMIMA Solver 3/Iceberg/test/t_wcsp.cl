// a first example from Samir Loudni:
//   with 6 binary constraints stated as infeasible pairs, and uniform penalties
//   the optimal cost is 2
[testWCSPSamir1() : void
 -> //[ICETEST_VIEW] small test #1 of WCSP from Samir Loudni,
    let pb := choco/makeProblem("Samir 1",5),
        x0 := choco/makeIntVar(pb,"x0",0,2),
        x1 := choco/makeIntVar(pb,"x1",0,2),
        x2 := choco/makeIntVar(pb,"x2",0,2),
        x3 := choco/makeIntVar(pb,"x3",0,2),
        cost := choco/makeBoundIntVar(pb,"cost",-100, 100),
        c1 := choco/infeasPairConstraint(x0,x2,list(tuple(1,2),tuple(2,2),tuple(0,0),tuple(2,1),tuple(2,0),tuple(1,0)) ),
        c2 := choco/infeasPairConstraint(x2,x3,list(tuple(1,1),tuple(2,0),tuple(0,1),tuple(2,1),tuple(0,0),tuple(1,0)) ),
        c3 := choco/infeasPairConstraint(x1,x3,list(tuple(2,1),tuple(0,0),tuple(0,2),tuple(1,0),tuple(1,2),tuple(2,2)) ),
        c4 := choco/infeasPairConstraint(x1,x2,list(tuple(0,1),tuple(2,1),tuple(1,2),tuple(1,0),tuple(0,0),tuple(1,1)) ),
        c5 := choco/infeasPairConstraint(x0,x1,list(tuple(2,1),tuple(0,0),tuple(0,1),tuple(2,0),tuple(1,1),tuple(2,2)) ),
        c6 := choco/infeasPairConstraint(x0,x3,list(tuple(0,2),tuple(2,1),tuple(0,0),tuple(2,2),tuple(1,0),tuple(1,1)) ) in
    (choco/post(pb, ice/additiveWCSP(list(c1,c2,c3,c4,c5,c6),list(1,1,1,1,1,1), cost)),
     let algo := choco/makeGlobalSearchMinimizer(cost, false, list(choco/makeAssignVarBranching())) in
       (// uncomment this line if you want to trace the search tree
        ; choco/setMaxPrintDepth(algo,10), SVIEW := 0,  STALK := 0,
        choco/attach(algo,pb),
        choco/run(algo),
        if (choco/getBestObjectiveValue(algo) != 2) error("optimal cost should be 2"))
     )]

// a second example from Samir Loudni:
//   with 10 symbolic constraints stated as arithmetic expressions
//   the optimal cost is 1
[testWCSPSamir2() : void
 -> //[ICETEST_VIEW] small test #2 of WCSP from Samir Loudni,
    let pb := choco/makeProblem("Samir 2",5),
        Ma := choco/makeBoundIntVar(pb,"Ma",1,4),
        Am := choco/makeBoundIntVar(pb,"Am",1,4),
        Mp := choco/makeBoundIntVar(pb,"Mp",1,4),
        Pm := choco/makeBoundIntVar(pb,"Pm",1,4),
        cost := choco/makeBoundIntVar(pb,"cost",-100, 100),
        ct1 := (Am !== Pm),// weight = 100
        ct2 := (Ma > Am), // weight = 3
        ct3 := (Ma > Pm), // weight = 3
        ct4 := (Mp > Am), // weight = 3
        ct5 := (Mp > Pm), // weight = 3
        ct6 := (Ma !== Mp),// weight = 2
        ct7 := (Ma !== 4), // weight = 1
        ct8 := (Mp !== 4), // weight = 1
        ct9 := (Pm !== 4), // weight = 1
        ct10 := (Am !== 4) in // weight = 1
    (choco/post(pb, ice/additiveWCSP(list(ct1,ct2,ct3,ct4,ct5,ct6,ct7,ct8,ct9,ct10),
                                       list(100,3,3,3,3,2,1,1,1,1),
                                       cost) ),
     let algo := choco/makeGlobalSearchMinimizer(cost, false, list(choco/makeAssignVarBranching())) in
       (// uncomment this line if you want to trace the search tree
        ; choco/setMaxPrintDepth(algo,10), SVIEW := 0,  STALK := 0,
        choco/attach(algo,pb),
        choco/run(algo),
        if (choco/getBestObjectiveValue(algo) != 1) error("optimal cost should be 1"))
    )]

