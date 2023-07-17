//*********************************************************************
//*          Non regression tests for Igloo v1.0                      *
//*********************************************************************

[allIceTestsInterpreted()
 -> verbose() := -1,
    trace(ICETEST_VIEW,"\n ----- Start testing ICE v~A with CHOCO v~A  ----\n",ICE_RELEASE,choco/CHOCO_RELEASE),
    testWCSPSamir1(), testWCSPSamir2(),
    testAllDiff1(5),
    testAllDiff2(5,-999),
    testAllDiff3(3,5,999),
    testPermutation(5),
    testGCC()]

[allIceTests()
 -> verbose() := -1,
    trace(ICETEST_VIEW,"\n ----- Start testing ICE v~A with CHOCO v~A  ----\n",ICE_RELEASE,choco/CHOCO_RELEASE),
    testWCSPSamir1(), testWCSPSamir2(),
    testAllDiff1(5),
    testAllDiff1(7),
    testAllDiff2(5,-999),
    testAllDiff2(7,999),
    testAllDiff3(3,5,999),
    testAllDiff3(5,8,-999),
    testPermutation(5),
    testPermutation(7),
    testGCC()]

