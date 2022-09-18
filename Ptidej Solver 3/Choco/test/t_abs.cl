// testing simple models of absolute values

[absEqual(l:list[integer]) : boolean
 -> (if (l[2] < l[3]) (l[1] = l[3] - l[2])
     else (l[1] = l[2] - l[3]))]

[testAbsByAuxFunction()
-> //[CHOCOTEST_VIEW] testing a model for absolute value through auxiliary functions,
   let pb := choco/makeProblem("pb",10),
       x  := choco/makeIntVar(pb,"x",0,10),
       y  := choco/makeIntVar(pb,"y",0,10),
       z  := choco/makeIntVar(pb,"z",0,10),
       u  := choco/makeBoundIntVar(pb,"u",0,10),
       v  := choco/makeBoundIntVar(pb,"v",0,10) in
    (choco/post(pb,choco/feasTestConstraint(list(u,x,y),absEqual @ list)),
     choco/post(pb,choco/feasTestConstraint(list(u,y,z),absEqual @ list)),
     choco/post(pb,choco/feasTestConstraint(list(v,x,z),absEqual @ list)),
     choco/setVal(x,1),
     choco/setVal(y,5),
     choco/propagate(pb),
     if (get(value,u) != 4) error("u should be instantiated to 4"),
     choco/setVal(v,8),
     choco/propagate(pb),
     if (get(value,z) != 9) error("the solution should be fully instantiated")
    )]

[testAbsByDisjunction()
-> //[CHOCOTEST_VIEW] testing a model for absolute value through disjunctions of equalities,
   let pb := choco/makeProblem("pb",10),
       x  := choco/makeIntVar(pb,"x",0,10),
       y  := choco/makeIntVar(pb,"y",0,10),
       z  := choco/makeIntVar(pb,"z",0,10),
       u  := choco/makeBoundIntVar(pb,"u",0,10),
       v  := choco/makeBoundIntVar(pb,"v",0,10) in
    (choco/post(pb,(u == x - y) or (u == y - x)),
     choco/post(pb,(u == z - y) or (u == y - z)),
     choco/post(pb,(v == x - z) or (v == z - x)),
     choco/setMin(u,3),
     choco/setVal(y,5),
     choco/setMax(x,4),
     choco/propagate(pb),
     if (choco/getSup(x) != 2) error("max(x) should have been reduced to 2"),
     choco/setMin(v,3),
     choco/propagate(pb),
     if (choco/getInf(z) != 8) error("min(z) should have been reduced to 8"),
     choco/setVal(z,9),
     choco/propagate(pb),
     if (get(value,x) != 1 | get(value,u) != 4 | get(value,v) != 8)
        error("the solution should be fully instantiated")
    )]

