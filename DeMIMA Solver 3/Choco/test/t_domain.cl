[testNextdomainValue()
 -> //[CHOCOTEST_VIEW] testing the getNextDomainValue method,
    let pb := choco/makeProblem("pb",10),
        y := choco/makeIntVar(pb,"Y",2,7) in
     (choco/post(pb, y !== 3),
      choco/post(pb, y !== 4),
      choco/propagate(pb),
      if (choco/getNextDomainValue(y,1) != 2)
         error("value after 1 in the domain(Y) should be 2"),
      if (choco/getPrevDomainValue(y,4) != 2)
         error("value before 4 in domain(Y) should be 2"),
      let a := choco/getInf(y), b := choco/getSup(y), n1 := 1, n2 := 1 in
        (while (a < choco/getSup(y))
	   (a := choco/getNextDomainValue(y,a),
            n1 :+ 1),
         while (b > choco/getInf(y))
	   (b := choco/getPrevDomainValue(y,b),
            n2 :+ 1),
         if ((n1 != n2) | (n1 != 4))
           error("the enumeration of domain(Y) yields ~S or ~S values, should be 4",n1,n2) ))]
