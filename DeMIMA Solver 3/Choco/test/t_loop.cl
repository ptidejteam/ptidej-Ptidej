// Non regression test on propagation loop for instantiations

// make sure that a deconnected var can still propagate events when at the end of the list
// (in this case x <= y deconnects itself just after sending its event INST(x,0))
[testLoop1() ->
 let pb := choco/makeProblem("bug", 10),
      x := choco/makeIntVar(pb, "x", 0, 50),
      y := choco/makeIntVar(pb, "y", 0, 50),
      z := choco/makeIntVar(pb, "z", -100, 100) in 
          (//[CHOCOTEST_VIEW] testing the propagation loop of an INST[cause=2] when nextConst=(1,1), 
           choco/post(pb, (x == 0) implies (z == 0)),
           choco/post(pb, x <= y),
           choco/propagate(pb),
           choco/setVal(y,0),
           choco/propagate(pb),
           if (get(choco/value,z) != 0) error("Z should be instantiated to 0"))]

[testLoop2() ->
 let pb := choco/makeProblem("bug", 10),
      x := choco/makeIntVar(pb, "x", 0, 10),
      y := choco/makeIntVar(pb, "y", 0, 20),
      z := choco/makeIntVar(pb, "z", 0, 20) in
          (//[CHOCOTEST_VIEW] testing propagation loop of an INF[cause=2] when nextConst=(2,3,1) then (3,3,1),
           choco/post(pb, (y <= 12) or (x == 3)),
           choco/post(pb, y >= z),
           choco/post(pb, x <= y),
           choco/propagate(pb),
           choco/setVal(z,15),
           choco/propagate(pb),
           if (get(choco/value,x) != 3) error("X should be instantiated to 3"))]
           