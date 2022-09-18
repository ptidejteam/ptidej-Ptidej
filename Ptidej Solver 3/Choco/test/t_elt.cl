// testing Elt with offset in index
[testEltWithOffset()
-> //[CHOCOTEST_VIEW] testing propagation for Elt with offset in index,
   let pb := choco/makeProblem("pb",10),
       i  := choco/makeBoundIntVar(pb,"I",0,10),
       v  := choco/makeBoundIntVar(pb,"V",0,10) in
           (choco/post(pb, choco/getNth(list(7,5,4,10),i + 1) == v),
            choco/propagate(pb),
            if (choco/getInf(i) > 0) error("i.inf should have remained 0") )]

[testEltInDisj() : void
-> //[CHOCOTEST_VIEW] testing Elt inside a disjunction,
   let pb := choco/makeProblem("pb",10),
       i  := choco/makeBoundIntVar(pb,"I",-20,20),
       v  := choco/makeBoundIntVar(pb,"V",0,10),
       l  := list(12,5,8) in
           (choco/post(pb, (i < 0) or (choco/getNth(l,i) == v)),
            try choco/propagate(pb)
            catch error 
                (self_print(exception!()),
                 error("The above error occured because isEntailed@Elt assumed that domain(i) only contained valid indexes")))]

