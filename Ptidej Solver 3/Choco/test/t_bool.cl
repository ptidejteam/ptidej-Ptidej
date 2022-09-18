// simple test on disjunction (testing new disjunctions with their opposite constraints)
[testDisjunction()
 -> //[CHOCOTEST_VIEW] testing the disjunction,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeIntVar(pb,"X",3,7),
        y := choco/makeIntVar(pb,"Y",4,10),
	z := choco/makeIntVar(pb,"Z",2,5) in
     (choco/post(pb,( ((x >= 6) and (z <= 4)) and ((y > 7) ifOnlyIf (y < 4))) ),
      choco/propagate(pb),
      if (choco/getInf(x) != 6) error("min(x) should be 6 (not ~S)", choco/getInf(x)),
      if (choco/getSup(z) != 4) error("max(z) should be 4 (not ~S)", choco/getSup(z)),
      if (choco/getSup(y) != 7) error("max(y) should be 7 (not ~S)", choco/getSup(y))
      )]

[testDisjSubStatus()
 -> //[CHOCOTEST_VIEW] testing the use of sub-statuses in disjunction,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeIntVar(pb,"X",1,3),
        y := choco/makeIntVar(pb,"Y",1,3),
	z := choco/makeIntVar(pb,"Z",1,3) in
     (choco/post(pb, ifThen((x >= 2), (y < 0) or (z < 0) )),
      choco/propagate(pb),
      try (choco/setMin(x, 2),
           choco/propagate(pb),
           error("there should be a contradiction !"))
      catch contradiction
          nil )]

[testDisjSubStatus2()
 -> //[CHOCOTEST_VIEW] testing the use of sub-statuses in disjunction,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeIntVar(pb,"X",1,3),
        y := choco/makeIntVar(pb,"Y",1,3),
	z := choco/makeIntVar(pb,"Z",1,3) in
     (choco/post(pb, implies((x >= 2), (y < 0) or (z < 0) )),
      choco/propagate(pb),
      error("stop"),
      try (choco/setMin(x, 2),
           choco/propagate(pb),
           error("there should be a contradiction !"))
      catch contradiction
          nil )]

[testGuardedOccur()
-> //[CHOCOTEST_VIEW] testing whether an occur is correclty propagated in a implies,
   let pb := choco/makeProblem("pb",10),
       x := choco/makeBoundIntVar(pb,"X",0,5),
       y := choco/makeBoundIntVar(pb,"Y",0,5),
       z := choco/makeBoundIntVar(pb,"Z",0,5),
       n := choco/makeBoundIntVar(pb,"N",0,5),
       lv := list(x,y) in
    (choco/post(pb,(z == 0) implies (choco/occur(0,lv) == n)),
     choco/post(pb, x == 3),
     choco/post(pb, y == 0),
     choco/propagate(pb),
     if ((choco/getInf(n) != 0) | (choco/getSup(n) != 5))
        error("N should not be restricted while the status of the condition is unknown"),
     choco/post(pb, z == 0),
     choco/propagate(pb),
     if (get(value,n) != 1) error("N should be instantiated to 1")  )]

// Test for opposite constraint indexation and propagation
// test whether the cardinality constraint correcly handles the opposite constraints
[testOpposite()
 -> //[CHOCOTEST_VIEW] testing whether the cardinality constraint correcly handles the opposite constraints,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5),
        n := choco/makeBoundIntVar(pb,"N",0,5) in
     (choco/post(pb,choco/card(list((x == 0),(y <= 3 * z + 1)),n)),
      choco/propagate(pb),
      if ( (choco/getInf(n) != 0) | (choco/getSup(n) != 2)) error("N should be restricted to [0.2]"),
      choco/setVal(x,0),
      choco/propagate(pb),
      if ( (choco/getInf(n) != 1) | (choco/getSup(n) != 2)) error("N should be restricted to [1.2]"),
      choco/setMax(n,1),
      choco/propagate(pb),
      if ( (choco/getInf(y) != 2)) error("Y.inf should be increased to 2 (propagation of opposite(y<=3z+1))")
      )]

// Tests complex Boolean combinations
[testComplexBool1()
 -> //[CHOCOTEST_VIEW] testing super bool combinations: ifOnlyif(and,or):1,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5) in
     (choco/post(pb, ((x == 1) and (y == 3)) ifOnlyIf ((z == 2) or (y == 5)) ),
      choco/setVal(z,2),
      choco/propagate(pb),
      if ( not(choco/isInstantiatedTo(x,1)) | not(choco/isInstantiatedTo(y,3)) )
         error("x,y should be isntantiated to (1,3)")
      )]

[testComplexBool2()
 -> //[CHOCOTEST_VIEW] testing super bool combinations: ifOnlyif(and,or):2,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5) in
     (choco/post(pb, ((x == 1) and (y == 3)) ifOnlyIf ((z == 2) or (y <= 5)) ),
      choco/propagate(pb),
      if ( not(choco/isInstantiatedTo(x,1)) | not(choco/isInstantiatedTo(y,3)) )
         error("x,y should be isntantiated to (1,3)")
      )]

[testComplexBool3()
 -> //[CHOCOTEST_VIEW] testing super bool combinations: ifOnlyif(and,or):3,
    let pb := choco/makeProblem("pb",10),
        x := choco/makeBoundIntVar(pb,"X",0,5),
        y := choco/makeBoundIntVar(pb,"Y",0,5),
        z := choco/makeBoundIntVar(pb,"Z",0,5) in
     (choco/post(pb, ((2 * x + 2 * y - z <= 3) and (3 * z - 2 * y >= 10)) ifOnlyIf ((x + 4 * y >= 2) or (y <= 3)) ),
      choco/setMax(y,2),
      choco/propagate(pb),
      if ( (choco/getSup(x) != 4) | (choco/getInf(z) != 4))
        error("x should be <= 4 and z >= 4")
      )]

[testCardOnPairs()
 -> //[CHOCOTEST_VIEW] testing cardinality constraint on CSPBinConstraints,
    let pb := choco/makeProblem("pb", 4),
        x := choco/makeIntVar(pb, "x", 1, 2),
        y := choco/makeIntVar(pb, "y", 1, 2),
        z := choco/makeIntVar(pb, "z", 1, 2),
        n := choco/makeIntVar(pb, "n", 0, 5),
        myTable := choco/makeBinRelation(1,2,1,2,list(tuple(2,1))),
        c1 := choco/binConstraint(x, y, myTable),
        c2 := choco/binConstraint(y, z, myTable),
        cc := choco/card(list(c1, c2), n) in
  (choco/post(pb,cc),
   let algo := choco/makeGlobalSearchMaximizer(n,false) in
     (choco/attach(algo,pb),
      choco/setMaxSolutionStorage(algo,1),
      choco/run(algo),
      if (choco/getBestObjectiveValue(algo) != 1)
        error("there should be at most 1 valid subconstraint"))
  ) ]

// a bug from Philippe Baptiste
[testTrivialAtleast1() : void
 -> //[CHOCOTEST_VIEW] simple testing of the atleast constraint,
    let pb := choco/makeProblem("testing atleast constraint", 10),
        x := choco/makeIntVar(pb, "x", 0, 1),
        c := choco/makeIntVar(pb, "count", 0, 1) in
      (choco/post(pb,choco/atleast(list(list(1) scalar list(x) >= 1, x <= 0, x >= 1),1)),
       try (choco/propagate(pb),
            choco/post(pb, x == 1),
            choco/propagate(pb))
       catch any
           error("there should not be any contradiction") )]

// a bug from Franck Mornet
[testTrivialAtleast2() : void
 -> //[CHOCOTEST_VIEW] testing the cardinality constraint,
    let pb := choco/makeProblem("pb",10),
        y1 := choco/makeIntVar(pb,"Y1",1,1),
        y2 := choco/makeIntVar(pb,"Y2",1,1),
        y3 := choco/makeIntVar(pb,"Y3",1,1),
        y4 := choco/makeIntVar(pb,"Y4",1,1) in
    (choco/post(pb,choco/atleast(list((y1 == y2),(y2 == y3)),1)),
     if not(choco/solve(pb,true))
        error("there should exist a feasible solution") )]

// a new test by franck
[testCard()
 -> //[CHOCOTEST_VIEW] testing the cardinality constraint,
    let pb := choco/makeProblem("pb",10),
        y1 := choco/makeIntVar(pb,"Y1",1,1),
        y2 := choco/makeIntVar(pb,"Y2",1,1),
        y3 := choco/makeIntVar(pb,"Y3",1,1),
        y4 := choco/makeIntVar(pb,"Y4",0,1) in
     (choco/post(pb,choco/atleast(list((y1 == y2),or((y2 == y3),(y2 == 0))),2)),
      choco/solve(pb,true)
      )]
