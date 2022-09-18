//*********************************************************************
//*          Non regression tests for Choco v.0.34                    *
//*********************************************************************

[allChocoTests() : void
 -> trace(CHOCOTEST_VIEW,"\n ----- Start testing CHOCO v~A  ----\n",choco/CHOCO_RELEASE),
    testDelay1(),testDelay2(),testDelay3(),
    testDisjunction(),
    testGuardedOccur(),
    testOpposite(),
    testComplexBool1(),testComplexBool2(),testComplexBool3(),
    testCardOnPairs(),testTrivialAtleast1(),testTrivialAtleast2(),
    testBoundOccur(),
    testNbSure1(),
    testNbSure2(),
    testOccurEntailment(),
    testEltWithOffset(),
    testEltInDisj(),
    testACEqualxyc(),
    testAbsByAuxFunction(),
    testAbsByDisjunction(),
    testFeas(),
    testLoop1(),testLoop2(),
    testNextdomainValue(),
    testSimpleBitSetDomain(),
    testSimpleBitListSetDomain(),
    testSetVar(),
    testSetCard(),
    testSetMember1(),
    testSetMember2(),
    testIntersection(),
    testUnion(),
    testSubset(),
    testDisjointSets(),
    testOverlapSets()
   ]
