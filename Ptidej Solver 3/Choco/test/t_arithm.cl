// regressionTest for arc consistency (with holes) in awake@Equalxyc

[testACEqualxyc()
-> //[CHOCOTEST_VIEW] testing arc consistency (with holes) in awake@Equalxyc,
   let pb := choco/makeProblem("pb",10),
       x  := choco/makeIntVar(pb,"X",list<integer>(0,1,2,3,4,5)),
       y  := choco/makeIntVar(pb,"Y",list<integer>(1,3,4,6,8,9,10)) in
           (choco/post(pb, y == x + 3),
            choco/propagate(pb),
            if (choco/domain(x) != list<integer>(0,1,3,5))
               error("The domain of x should have been reduced to (0,1,3,5)") )]
