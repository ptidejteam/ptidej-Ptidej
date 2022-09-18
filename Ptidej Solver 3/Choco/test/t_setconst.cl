[testSetCard() : void
 -> //[CHOCOTEST_VIEW] testing the set cardinality constraint,    
    let p := choco/makeProblem("trial problem", 5),
        iv1 := choco/makeBoundIntVar(p,"iv1",-100, 5),
        sv1 := choco/makeSetVar(p,"SV1",1,10,iv1),
        iv2 := choco/makeBoundIntVar(p,"iv1",-100, 15),
        sv2 := choco/makeSetVar(p,"SV1",1,10,iv2) in
      (choco/propagate(p),
       if (choco/getInf(iv1) != 0) error("min card should be 0 instead of ~S",choco/getInf(iv1)),
       choco/addToKernel(sv1,1,0),
       choco/addToKernel(sv1,2,0),
       choco/addToKernel(sv1,3,0),
       choco/propagate(p),
       if (choco/getInf(iv1) != 3) error("min card should be 3 instead of ~S",choco/getInf(iv1)),
       choco/remFromEnveloppe(sv1,7,0),
       choco/remFromEnveloppe(sv1,10,0),
       choco/propagate(p),
       if (choco/getSup(iv1) > 8) error("max card should be at most 8 instead of ~S",choco/getSup(iv1)),
       choco/setMax(iv1,3),
       choco/propagate(p),
       if not(choco/isInstantiated(sv1) & choco/isInstantiatedTo(sv1,list(1,2,3)))
          error("sv1 should be instantiated to 1,2,3"),
       choco/propagate(p),
       if (choco/getInf(iv2) != 0) error("min card should be 0"),
       choco/addToKernel(sv2,1,0),
       choco/addToKernel(sv2,2,0),
       choco/addToKernel(sv2,3,0),
       choco/propagate(p),
       if (choco/getInf(iv2) != 3) error("min card should be 3"),
       choco/remFromEnveloppe(sv2,7,0),
       choco/remFromEnveloppe(sv2,10,0),
       choco/propagate(p),
       if (choco/getSup(iv2) > 8) error("max card should be at most 8 instead of ~S",choco/getSup(iv1)),
       choco/setMin(iv2,8),
       choco/propagate(p),
       if not(choco/isInstantiated(sv2) & choco/isInstantiatedTo(sv2,list(1,2,3,4,5,6,8,9)))
          error("sv2 should be instantiated to 1,2,3,4,5,6,8,9"),       
       choco/discardProblem(p) )]

[testSetMember1() : void
 -> //[CHOCOTEST_VIEW] testing the set memberc constraint,    
    let p := choco/makeProblem("trial problem", 5),
        sv1 := choco/makeSetVar(p,"SV1",1,10),
        x := choco/makeIntVar(p,"x",1,10) in
     (choco/post(p, choco/memberOf(sv1,3)),
      choco/post(p, choco/notMemberOf(sv1,7)),
      choco/propagate(p),
      if (not(choco/isInDomainKernel(sv1,3)) | choco/getDomainKernelSize(sv1) != 1) 
         error("3 should have been set from set variable"),
      if (choco/isInDomainKernel(sv1,7) | choco/getDomainEnveloppeSize(sv1) != 9) 
         error("7 should have been removed from set variable"),
      choco/post(p, (x <= 5) or choco/memberOf(sv1,4)),
      choco/setMin(x,6),
      choco/propagate(p),
      if (not(choco/isInDomainKernel(sv1,4)) | choco/getDomainKernelSize(sv1) != 2) 
         error("4 should have been set in domain of set variable"),
      choco/post(p, choco/ifThen(x == 8, choco/notMemberOf(sv1,5))),
      choco/propagate(p),
      if (choco/getDomainKernelSize(sv1) != 2) 
         error("there should have been no change in domain of set variable"),
      choco/setMin(x,8),
      choco/setMax(x,8),
      choco/propagate(p),
      if choco/isInDomainEnveloppe(sv1,5)
         error("5 should have been removed from domain of set variable"),
      choco/discardProblem(p) )]
      
[testSetMember2() : void
 -> //[CHOCOTEST_VIEW] testing the set memberx constraint,    
    let p := choco/makeProblem("trial problem", 5),
        iv1 := choco/makeBoundIntVar(p,"iv1",0,3),
        sv1 := choco/makeSetVar(p,"SV1",1,10,iv1),
        x := choco/makeIntVar(p,"x",1,10),
        y := choco/makeIntVar(p,"y",list(3,5,9)) in
     (choco/post(p, choco/memberOf(sv1,y)),
      choco/post(p, choco/notMemberOf(sv1,x)),
      choco/post(p, choco/memberOf(sv1,7) choco/ifOnlyIf choco/memberOf(sv1,9)),
      choco/propagate(p),             
      choco/setIn(sv1,1),
      choco/setIn(sv1,2),
      choco/propagate(p),             
      if not(choco/getInf(iv1) = 2)
         error("set card should be at least 2"),
      choco/setVal(x,7),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv1,9)
         error("9 should have been removed from enveloppe"),
      choco/setOut(sv1,5),
      choco/propagate(p),             
      if not(choco/isInstantiated(sv1) & choco/isInstantiated(iv1) & choco/isInstantiated(y) &
             choco/isInstantiatedTo(y,3))
         error("incomplete propagation y=3, sv1={1,2,3} vs y:~S, sv1:~S",y,sv1),
      choco/discardProblem(p) )]
      
[testIntersection() : void 
 -> //[CHOCOTEST_VIEW] testing the set intersection constraint,    
    let p := choco/makeProblem("trial problem", 5),
        sv1 := choco/makeSetVar(p,"SV1",1,10),
        sv2 := choco/makeSetVar(p,"SV2",1,10),
        iv3 := choco/makeBoundIntVar(p,"iv3",3,3),
        sv3 := choco/makeSetVar(p,"SV3",1,10,iv3) in
     (choco/post(p, choco/setintersection(sv1,sv2,sv3)),
      choco/setIn(sv1,1),
      choco/setIn(sv1,2),
      choco/setIn(sv2,1),
      choco/propagate(p),             
      if not(choco/isInDomainKernel(sv3,1))
         error("3 should be in the intersection"),
      choco/setOut(sv3,2),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv2,2)
         error("2 should not be in the second set"),
      choco/setIn(sv3,7),
      choco/propagate(p),             
      if (not(choco/isInDomainKernel(sv1,7)) | not(choco/isInDomainKernel(sv2,7)))
         error("7 should be in both sets"),
      choco/setOut(sv3,5),
      choco/propagate(p),             
      choco/setIn(sv1,5),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv2,5)
         error("5 should not be in the second set"),
      choco/setOut(sv1,10),
      choco/setOut(sv1,9),
      choco/setOut(sv1,8),
      choco/setOut(sv1,6),
      choco/setOut(sv1,3),
      choco/propagate(p),             
      if (choco/isInDomainEnveloppe(sv3,3))
         error("3 not possible for intersection"),
      if not(choco/isInDomainKernel(sv3,4))
         error("the cardinal constraints forces 4 to be in the intersection ~S, thus in v1 & v2",sv3)
     )]    
[testUnion() : void 
 -> //[CHOCOTEST_VIEW] testing the set union constraint,    
    let p := choco/makeProblem("trial problem", 6),
        iv1 := choco/makeBoundIntVar(p,"iv2",0,4),
        sv1 := choco/makeSetVar(p,"SV1",1,10,iv1),
        iv2 := choco/makeBoundIntVar(p,"iv2",0,4),
        sv2 := choco/makeSetVar(p,"SV2",1,10,iv2),
        iv3 := choco/makeBoundIntVar(p,"iv2",8,10),
        sv3 := choco/makeSetVar(p,"SV3",1,10,iv3) in
     (choco/post(p, choco/setunion(sv1,sv2,sv3)),
      choco/setIn(sv1,1),
      choco/setIn(sv1,2),
      choco/propagate(p),             
      if (not(choco/isInDomainKernel(sv3,1)) | not(choco/isInDomainKernel(sv3,2)))
         error("1&2 should be in the union"),
      choco/setOut(sv3,3),
      choco/propagate(p),             
      if (choco/isInDomainEnveloppe(sv1,3) | choco/isInDomainEnveloppe(sv2,3))
         error("3 should neither be in sv1 nor sv2"),
      choco/setOut(sv3,10),
      choco/propagate(p),             
      if (choco/getDomainKernelSize(sv3) != 8 | not(choco/isInstantiated(sv3)))
         error("all values from 1 to 9 but 3 should be in kernel of sv3:~S",sv3),
      if (choco/isInDomainEnveloppe(sv1,10) | choco/isInDomainEnveloppe(sv2,10))
         error("10 should  beforbidden for sv1 and sv2"),
      choco/setOut(sv2,4),
      choco/setOut(sv2,5),
      choco/propagate(p),             
      if (not(choco/isInstantiated(sv1)) | not(choco/isInstantiated(sv2)))
         error("s1 and s2 should be instantiated (1,2,4,5) and (6,7,8,9): ~S,~S",sv1,sv2)
     )]    
[testSubset() : void 
 -> //[CHOCOTEST_VIEW] testing the subset constraint,    
    let p := choco/makeProblem("trial problem", 6),
        iv1 := choco/makeBoundIntVar(p,"iv2",0,2),
        sv1 := choco/makeSetVar(p,"SV1",1,10,iv1),
        iv2 := choco/makeBoundIntVar(p,"iv2",4,4),
        sv2 := choco/makeSetVar(p,"SV2",1,10,iv2) in
     (choco/post(p, choco/subset(sv1,sv2)),
      choco/setIn(sv1,1),
      choco/propagate(p),             
      if not(choco/isInDomainKernel(sv2,1))
         error("1 should be in the superset"),
      choco/setOut(sv2,3),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv1,3)
         error("3 should not be in subset"),
      choco/setIn(sv2,2),
      choco/setIn(sv2,4),
      choco/setIn(sv2,5),
      choco/propagate(p),             
      if choco/isInDomainKernel(sv1,2)
         error("2 should not be in subset kernel"),
      choco/setIn(sv1,4),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv1,2)
         error("2 should not be in subset"),
      if not(choco/isInstantiated(sv1))
         error("subset should be instantiated")
     )]    
[testDisjointSets() : void 
 -> //[CHOCOTEST_VIEW] testing the disjoint sets constraint,    
    let p := choco/makeProblem("trial problem", 6),
        sv1 := choco/makeSetVar(p,"SV1",1,10),
        sv2 := choco/makeSetVar(p,"SV2",1,10),
        sv3 := choco/makeSetVar(p,"SV3",1,4) in
     (choco/post(p, choco/setunion(sv1,sv2,sv3)),
      choco/post(p, choco/disjoint(sv1,sv2)),
      choco/setIn(sv3,1),
      choco/setIn(sv1,1),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv2,1)
         error("1 should not be in sv2"),
      choco/setIn(sv2,2),
      choco/setIn(sv1,3),
      choco/propagate(p),             
      if choco/isInDomainEnveloppe(sv1,2)
         error("2 should not be in sv1"),
      if not(choco/isInDomainKernel(sv3,3))
         error("3 should be in the union"),
      choco/setIn(sv3,4),
      choco/setOut(sv1,4),
      choco/propagate(p),             
      if not(choco/isInDomainKernel(sv2,4))
         error("4 should be in sv2"),
      if not(choco/isInstantiated(sv1) & choco/isInstantiated(sv2) & choco/isInstantiated(sv3))
         error("all should be instantiated")
     )]         
[testOverlapSets() : void 
 -> //[CHOCOTEST_VIEW] testing the overlapping sets constraint,    
    let p := choco/makeProblem("trial problem", 6),
        sv1 := choco/makeSetVar(p,"SV1",1,10),
        sv2 := choco/makeSetVar(p,"SV2",1,10),
        sv3 := choco/makeSetVar(p,"SV3",1,5) in
     (choco/post(p, choco/setunion(sv1,sv2,sv3)),
      choco/post(p, choco/overlap(sv1,sv2)),
      for i in (1 .. 5)
          choco/setIn(sv3,i),
      choco/setOut(sv1,1),
      choco/setOut(sv1,2),
      choco/setOut(sv2,5),
      choco/setOut(sv2,4),
      choco/propagate(p),             
      if not(choco/isInDomainKernel(sv2,3))
         error("3 should be in sv2"),
      if not(choco/isInstantiated(sv1) & choco/isInstantiated(sv2) & choco/isInstantiated(sv3))
         error("all should be instantiated")
     )]
