// test whether, once the max number of occurences has been reached,
// the occurence constraint behave (even for BoundIntVar) like a <>
// (ie for x in the list, the event x <= target causes the event x <= target -1)
[testBoundOccur()
-> //[CHOCOTEST_VIEW] testing whether max-saturated occur constraints behave like <> constraints,
   let pb := choco/makeProblem("pb",10),
       x := choco/makeBoundIntVar(pb,"X",0,2),
       y := choco/makeBoundIntVar(pb,"Y",0,2),
       n := choco/makeBoundIntVar(pb,"N",0,5),
       m := choco/makeBoundIntVar(pb,"M",0,5) in
   (choco/post(pb,n == choco/occur(1,list(x,y))),
    choco/post(pb,m == choco/occur(2,list(x,y))),
    choco/propagate(pb),
    choco/setVal(n,0),
    choco/setMax(x,1),
    choco/propagate(pb),
    if (get(value,x) != 0) error("x should be instantiated to 0 ! (since 1 is forbidden by the occur)") )]

// test whether an instantiation causing the awake of an occur
// is not counted twice in the nbSure (once in the awake(occur) and once in the awakeOnInst(occur))
[testNbSure1()
-> //[CHOCOTEST_VIEW] testing whether instantiations causing occur to awake is not counted twice in nbSure,
   let pb := choco/makeProblem("pb",10),
       x := choco/makeBoundIntVar(pb,"X",0,5),
       y := choco/makeBoundIntVar(pb,"Y",0,5),
       z := choco/makeBoundIntVar(pb,"Z",0,5),
       n := choco/makeBoundIntVar(pb,"N",0,5),
       lv := list(x,y) in
    (choco/post(pb, z == x - 1),
     let occ := (choco/occur(1,lv) == n) in
         (choco/post(pb,(z == 0) implies occ),
          choco/setVal(x,1),
          choco/propagate(pb),
     // There is an assertion in const2.cl, but we add a duplicate test
     // in case the test is run in compiled mode with safety > 2 (ie assertion disabled)
          if (occ.choco/nbSure > 1) error("Instantiation of X was counted twice!"))) ]

// test whether occur works with an intial domain reduced to one value
[testNbSure2()
-> //[CHOCOTEST_VIEW] testing whether occur works with an intial domain reduced to one value,
   let pb := choco/makeProblem("pb",10),
       x := choco/makeBoundIntVar(pb,"X",1,1),
       y := choco/makeBoundIntVar(pb,"Y",0,5),
       n := choco/makeBoundIntVar(pb,"N",0,5),
       lv := list(x,y) in
    (choco/post(pb,choco/occur(1,lv) == n),
     choco/propagate(pb),
     if (choco/getInf(n) < 1) error("N.inf should be increased to 1 since domain(X) is reduced to the target value of the occur"))]

// testing askIfEntailed@Occurrence
[testOccurEntailment()
-> //[CHOCOTEST_VIEW] testing entailements of occur,
   let pb := choco/makeProblem("pb",10),
       x := choco/makeBoundIntVar(pb,"X",0,5),
       y := choco/makeBoundIntVar(pb,"Y",0,5),
       z := choco/makeBoundIntVar(pb,"Z",0,5),
       n := choco/makeBoundIntVar(pb,"N",0,5),
       lv := list(x,y) in
    (choco/post(pb,(choco/occur(1,lv) == n)  or (z >= 3)),    
     choco/setMin(x,3),
     choco/setMin(y,2),
     choco/setMin(n,1),
     choco/propagate(pb),
     if (choco/getInf(z) < 3) error("Z.inf should be increased to 3") )]


