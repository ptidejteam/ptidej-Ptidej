[testSimpleBitSetDomain() : void
 -> //[CHOCOTEST_VIEW] testing simple set domains,   
    let d := choco/makeBitSetDomain(100, 125) in 
     (if (choco/getKernelSize(d) != 0) 
         error("kernel should be empty"),
      if (choco/getEnveloppeSize(d) != 26) 
         error("enveloppe should be full: contains ~S vs 26 elts",choco/getEnveloppeSize(d)),
      if (choco/isInKernel(d,100) | choco/isInKernel(d,113) | choco/isInKernel(d,125)) 
         error("values not in kernel"),
      if not(choco/isInEnveloppe(d,100) & choco/isInEnveloppe(d,113) & choco/isInEnveloppe(d,125)) 
         error("values in enveloppe"),
      choco/updateKernel(d,112),
      choco/updateKernel(d,113),
      if (choco/getKernelInf(d) != 112) error("wrong kernel inf ~S vs 112",choco/getKernelInf(d)),
      if (choco/getKernelSup(d) != 113) error("wrong kernel sup ~S vs 113",choco/getKernelSup(d)),
      if not(choco/isInKernel(d,112) & choco/isInKernel(d,113) & choco/getKernelSize(d) = 2) 
         error("added 112 and 113 to kernel"),
      choco/updateEnveloppe(d,100),
      choco/updateEnveloppe(d,124),
      if (choco/isInEnveloppe(d,100) | choco/isInEnveloppe(d,124) | choco/getEnveloppeSize(d) != 24) 
         error("removed 100 and 124 from enveloppe"),
      choco/updateEnveloppe(d,125),
      choco/updateEnveloppe(d,123),
      choco/updateEnveloppe(d,111),
      if (choco/isInEnveloppe(d,125) | choco/isInEnveloppe(d,123) | choco/isInEnveloppe(d,111) | choco/getEnveloppeSize(d) != 21) 
         error("removed 125, 123, 111 from enveloppe"),
      choco/updateEnveloppe(d,123),
      choco/updateEnveloppe(d,111),
      if (choco/getEnveloppeInf(d) != 101) error("wrong enveloppe inf ~S vs 101",choco/getEnveloppeInf(d)),
      if (choco/getEnveloppeSup(d) != 122) error("wrong enveloppe sup ~S vs 122",choco/getEnveloppeSup(d)),
      if (choco/getEnveloppeSize(d) != 21) 
         error("123 and 111 already removed, size did not change"))]
      
[testSimpleBitListSetDomain()
 -> //[CHOCOTEST_VIEW] testing large set domains,   
    let d := choco/makeBitListSetDomain(1000, 1224) in 
     (if (choco/getKernelSize(d) != 0) 
         error("kernel should be empty"),
      if (choco/getEnveloppeSize(d) != 225) 
         error("enveloppe should be full: contains ~S vs 225 elts",choco/getEnveloppeSize(d)),
      if (choco/isInKernel(d,1000) | choco/isInKernel(d,1131) | choco/isInKernel(d,1215)) 
         error("values not in kernel"),
      if not(choco/isInEnveloppe(d,1000) & choco/isInEnveloppe(d,1131) & choco/isInEnveloppe(d,1215)) 
         error("values in enveloppe"),
      choco/updateKernel(d,1120),
      choco/updateKernel(d,1130),
      if not(choco/isInKernel(d,1120) & choco/isInKernel(d,1130) & choco/getKernelSize(d) = 2) 
         error("added 1120 and 1130 to kernel"),
      if (choco/getKernelInf(d) != 1120) error("wrong kernel inf ~S vs 1120",choco/getKernelInf(d)),
      if (choco/getKernelSup(d) != 1130) error("wrong kernel sup ~S vs 1130",choco/getKernelSup(d)),
      choco/updateEnveloppe(d,1000),
      choco/updateEnveloppe(d,1224),
      if (choco/isInEnveloppe(d,1000) | choco/isInEnveloppe(d,1224) | choco/getEnveloppeSize(d) != 223) 
         error("removed 1000 and 1224 from enveloppe"),
      choco/updateEnveloppe(d,1221),
      if (choco/getEnveloppeSize(d) != 222) 
         error("enveloppe should be full: contains ~S vs 222 elts",choco/getEnveloppeSize(d)),
      choco/updateEnveloppe(d,1223),
      if (choco/getEnveloppeSize(d) != 221) 
         error("enveloppe should be full: contains ~S vs 221elts",choco/getEnveloppeSize(d)),
      choco/updateEnveloppe(d,1111),
      if choco/isInEnveloppe(d,1221) 
         error("removed 1221 from enveloppe"),
      if choco/isInEnveloppe(d,1223) 
         error("removed 1223 from enveloppe"),
      if choco/isInEnveloppe(d,1111) 
         error("removed 1111 from enveloppe"),
      if (choco/getEnveloppeSize(d) != 220) 
         error("wrong enveloppe size ~S vs. 220",choco/getEnveloppeSize(d)),
      choco/updateEnveloppe(d,1223),
      choco/updateEnveloppe(d,1111),
      if (choco/getEnveloppeInf(d) != 1001) error("wrong enveloppe inf ~S vs 1001",choco/getEnveloppeInf(d)),
      if (choco/getEnveloppeSup(d) != 1222) error("wrong enveloppe sup ~S vs 1222",choco/getEnveloppeSup(d)),
      if (choco/getEnveloppeSize(d) != 220) 
         error("1223 and 1111 already removed, size did not change"))]

[testSetVar() : void
 -> //[CHOCOTEST_VIEW] testing set variables operations,    
    let p := choco/makeProblem("trial problem", 2),
        sv1 := choco/makeSetVar(p,"SV1",1,10),
        sv2 := choco/makeSetVar(p,"SV2",2,8) in
      (choco/addToKernel(sv1,2,0),
       choco/addToKernel(sv1,5,0),
       choco/addToKernel(sv1,8,0),
       choco/remFromEnveloppe(sv1,9,0),
       choco/remFromEnveloppe(sv1,10,0),
       choco/remFromEnveloppe(sv1,1,0),
       if (choco/getDomainKernel(sv1) != list<integer>(2,5,8)) 
          error("kernel:~S instead of (2,5,8)",choco/getDomainKernel(sv1)),
       if (choco/getDomainEnveloppeSize(sv1) != 7) 
          error("enveloppe size ~S instead of 7",choco/getDomainEnveloppeSize(sv1)),
       if not(choco/canBeInstantiatedTo(sv1,list(2,3,5,7,8))) 
          error("~S can be instantiated to 2,3,5,7,8",sv1),
       if choco/canBeInstantiatedTo(sv1,list(2,3,5,7)) 
          error("~S cannot be instantiated to 2,3,5,7",sv1),
       if not(choco/canBeEqualTo(sv1,sv2))
          error("~S and ~S can be  equal!!",sv1,sv2),
       choco/addToKernel(sv2,4,0),
       choco/remFromEnveloppe(sv1,4,0),
       if choco/canBeEqualTo(sv1,sv2)
          error("~S and ~S can no longer be  equal!!",sv1,sv2),
       try (choco/remFromEnveloppe(sv1,8,0),
            error("a contradiction should have been raised"))
       catch contradiction nil,
       choco/addToKernel(sv2,2,0),
       choco/remFromEnveloppe(sv2,3,0),
       choco/addToKernel(sv2,5,0),
       choco/remFromEnveloppe(sv2,6,0),
       choco/addToKernel(sv2,7,0),
       if choco/isInstantiated(sv2) 
          error("~S is not instantiated yet (don t know for for value 8)",sv2),
       choco/addToKernel(sv2,8,0),
       if not(choco/isInstantiated(sv2))
          error("~S is now instantiated",sv2)
        )]
