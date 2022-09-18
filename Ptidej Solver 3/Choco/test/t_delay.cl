// test whether linear combination are propagated when included in a CompositeConstraint
[testDelay1()
 -> //[CHOCOTEST_VIEW] testing whether linear const. are propagated when included in a composite const,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5),
        n := choco/makeBoundIntVar(pb,"N",0,5) in
     (choco/post(pb,(x == 0) implies (y == 3 * z)),
      choco/propagate(pb),
      choco/setVal(x,0),
      ;error("stop"),
      choco/propagate(pb),
      if (choco/getSup(z) > 1) error("z should be decreased to [0,1]"),
      choco/setMin(z,1),
      choco/propagate(pb),
      if (get(value,y) != 3) error("y should be instantiated to 3") )]

[testDelay2()
 -> //[CHOCOTEST_VIEW] testing whether linear const. are propagated when encapsulated in a Delayer,
    let pb := choco/makeProblem("pb",10),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5),
        n := choco/makeBoundIntVar(pb,"N",0,5) in (
        	choco/post(pb,y == 3 * z), // encapsulate the LinComb in a Delayer
      		choco/propagate(pb),
      		if (choco/getSup(z) > 1) error("z should be decreased to [0,1]"),
      		choco/setMin(z,1),
      		choco/propagate(pb),
      		if (get(value,y) != 3) error("y should be instantiated to 3") )]


[testDelay3()
 -> //[CHOCOTEST_VIEW] testing whether any constraint (even the most simple) can be delayed,
    let pb := choco/makeProblem("pb",10),
    	x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,3) in (
            choco/post(pb,choco/delay(x == y,3)),
            choco/propagate(pb),
            if (choco/getSup(x) > 3) error("x should be decreased to [0,3]"),
            choco/setVal(y,2),
            choco/propagate(pb),
            if (get(value,x) != 2) error("x should be instantiated to 2") )]

// test whether abstractDecMax is always called by updateSup@Delayer,
// even if needToUpdate has already been set to true.
[testNeedToUpdate()
->  let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,34),
        y := choco/makeBoundIntVar(pb,"Y",0,50),
        z := choco/makeBoundIntVar(pb,"Z",0,2) in
           (choco/post(pb,2 * x + y + z == 69),
            choco/propagate(pb),
            choco/setMin(z,1),   // will cause needToUpdate := true and improvedLowerBound := true
            choco/setMax(y,5),   // should cause needToUpdate := true AND improvedUpperBound := true
            choco/propagate(pb),
            if (choco/getInf(x) < 31) error("X.inf should have been increased to 31:\nThe second event has probably been dropped.\n") )]


