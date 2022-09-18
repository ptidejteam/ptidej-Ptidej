// full test: 10 equations with 7 variables
// solution: (x1,x2,x3,x4,x5,x6,x7) = (6,0,8,4,9,3,9)
//
[eq()
 -> time_set(),
    trace(CHOCOBENCH_VIEW,"10 linear equations    "),
    let p := choco/makeProblem("Linear",7),
        x1 := choco/makeBoundIntVar(p,"X1",0 ,10),
        x2 := choco/makeBoundIntVar(p,"X2",0 ,10),
        x3 := choco/makeBoundIntVar(p,"X3",0 ,10),
        x4 := choco/makeBoundIntVar(p,"X4",0 ,10),
        x5 := choco/makeBoundIntVar(p,"X5",0 ,10),
        x6 := choco/makeBoundIntVar(p,"X6",0, 10),
        x7 := choco/makeBoundIntVar(p,"X7",0 ,10) in
   (choco/post(p, list(98527, 34588, 5872, 59422, 65159) scalar list(x1,x2,x3,x5,x7) == list(30704, 29649) scalar list(x4,x6) + 1547604),
    choco/post(p, list(98957, 83634, 69966, 62038, 37164, 85413) scalar list(x2,x3,x4,x5,x6,x7) == 93989 * x1 + 1823553),
    choco/post(p, list(10949, 77761, 67052) scalar list(x1,x2,x5) == list(80197, 61944,92964, 44550) scalar list(x3,x4,x6,x7) - 900032),
    choco/post(p, list(73947, 84391, 81310) scalar list(x1,x3,x5) == list(96253, 44247,70582, 33054) scalar list(x2,x4,x6,x7) + 1164380),
    choco/post(p, list(13057, 42253, 77527,96552) scalar list(x3,x4,x5,x7) == list(60152, 21103, 97932) scalar list(x1,x2,x6) + 1185471),
    choco/post(p, list(66920, 55679) scalar list(x1,x4) == list(64234, 65337, 45581, 67707, 98038) scalar list(x2,x3,x5,x6,x7) - 1394152),
    choco/post(p, list(68550, 27886, 31716, 73597, 38835) scalar list(x1,x2,x3,x4,x7) == list(88963, 76391) scalar list(x5,x6) + 279091),
    choco/post(p, list(76132, 71860, 22770, 68211, 78587) scalar list(x2,x3,x4,x5,x6) == list(48224, 82817) scalar list(x1,x7) + 480923),
    choco/post(p, list(94198, 87234, 37498) scalar list(x2,x3,x4) == list(71583, 25728, 25495, 70023) scalar list(x1,x5,x6,x7) - 519878),
    choco/post(p, list(78693, 38592, 38478) scalar list(x1,x5,x6) == list(94129, 43188, 82528, 69025) scalar list(x2,x3,x4,x7) - 361921),
    choco/solve(p,false),
    choco/discardProblem(p),  // v1.010
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,": ~S ms.\n",t)) ]


// test2 x1=7, x2=4, x3=2, y1=6
[test1()
 -> time_set(),
    trace(CHOCOBENCH_VIEW,"4 linear equations    "),
    let p := choco/makeProblem("Linear 4",4),
        x1 := choco/makeBoundIntVar(p,"X1",0, 10),
        x2 := choco/makeBoundIntVar(p,"X2", 0, 10),
        x3 := choco/makeBoundIntVar(p,"X3", 0, 10),
        y1 := choco/makeBoundIntVar(p,"Y1", 0, 100000000),
        y2 := choco/makeBoundIntVar(p,"Y2", 0, 100000000) in
   (for x in set(x1,x2,x3,y1) (choco/setMin(x,1), choco/setMax(x,10)),
    choco/setMin(y2,0), choco/setMax(y2,50),
    choco/post(p, list(1,3,5) scalar list(x1,x2,x3) == y1 + 23),
    choco/post(p, list(2,10,1) scalar list(x1,x2,x3) == y2 + 14),
    choco/post(p, 7 * y1 == y2),
    choco/propagate(p),
    choco/discardProblem(p),  // v1.010
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,": ~S ms.\n",t)) ]

// test with x1=2, x2=5, x3=3, y1=0
[test2()
 -> time_set(),
    trace(CHOCOBENCH_VIEW,"5 linear equations    "),
    let p := choco/makeProblem("Linear 5",5),
        x1 := choco/makeBoundIntVar(p,"X1",0, 10),
        x2 := choco/makeBoundIntVar(p,"X2", 0, 10),
        x3 := choco/makeBoundIntVar(p,"X3", 0, 10),
        y1 := choco/makeBoundIntVar(p,"Y1", 0, 100000000) in
   (for x in set(x1,x2,x3,y1) (choco/setMin(x,0), choco/setMax(x,10)),
    choco/post(p, list(3,7,9) scalar list(x1,x2,x3) == y1 + 68),
    choco/post(p, list(5,2,8) scalar list(x1,x2,x3) == y1 + 44),
    choco/post(p, list(3,12,2) scalar list(x1,x2,x3) == y1 + 72),
    choco/post(p, list(15,4,1) scalar list(x1,x2,x3) == y1 + 53),
    choco/post(p, list(12,7,9) scalar list(x1,x2,x3) == y1 + 86),
    choco/propagate(p),
    choco/discardProblem(p),  // v1.010
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,": ~S ms.\n",t)) ]

[test22()
 -> time_set(),
    trace(CHOCOBENCH_VIEW,"5 linear equations     "),
    let p := choco/makeProblem("T",5),
        x1 := choco/makeBoundIntVar(p,"X1",0, 10),
        x2 := choco/makeBoundIntVar(p,"X2", 0, 10),
        x3 := choco/makeBoundIntVar(p,"X3", 0, 10),
        y1 := choco/makeBoundIntVar(p,"Y1", 0, 100000000) in
   (for x in set(x1,x2,x3,y1) (choco/setMin(x,0), choco/setMax(x,10)),
    choco/post(p, 3 * x1 + 7 * x2 + 9 * x3 - 68 == y1),
    choco/post(p, 5 * x1 + 2 * x2 + 8 * x3 - 44 == y1),
    choco/post(p, 3 * x1 + 12 * x2 + 2 * x3 - 72 == y1),
    choco/post(p, 15 * x1 + 4 * x2 + x3 == y1 + 53),
    choco/post(p, 12 * x1 + 7 * x2 + 9 * x3 == y1 + 86),
    choco/propagate(p),
    choco/discardProblem(p),  // v1.010
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,": ~S ms.\n",t)) ]

