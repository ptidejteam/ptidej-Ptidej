[sillyCSP() : void
 -> let pb := choco/makeProblem("Silly CSP",3),
        x := choco/makeIntVar(pb, "x", 1, 3),
        y := choco/makeIntVar(pb, "y", 1, 3),
        z := choco/makeIntVar(pb, "z", 1, 3) in 
      (choco/post(pb, x + y == z),
       choco/post(pb, x > y),
       choco/propagate(pb),
       printf("~S ~S ~S\n",x,y,z) )]
