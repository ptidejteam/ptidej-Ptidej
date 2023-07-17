// A small suite of benchmarks for CHOCO
//  up to date for version 0.26, july 2000

[allChocoBenchs() : void
 -> system.verbose := -1,
    trace(CHOCOBENCH_VIEW,"\n   CHOCO benchmark suite, v~A ...\n===================================\n",choco/CHOCO_RELEASE),
    // test with the n-queens (Gauss) problem
    queen0(25,false),queen0(50,false),;queen0(100,false),
    queen0(8,true),queen0(10,true),queen0(12,true),
    queen1(25,false),queen1(50,false),queen1(100,false),
    queen1(8,true),queen1(10,true),queen1(12,true),
    // crypt-arithmetic problems
    send(),send2(),send3(),
    // solving systems of linear inequalities
    eq(),
    // the famous zebra problem
    zebras(),zebraf(),zebrap(),zebral(),
    // magic series
    magic(30),magic(100),magic(300),
    // magic squares
    square(4,false,false), square(6,true,false), square(7,true,false),
    //  A simple instance of satellite photo planning
    photo(5,3), photo(8,4), photo(10,5),
    //  The four corner problem: colouring corners of a rectangles a rectangle is never monochromatic
    fcp2(7),fcp2(8),fcp2(9),
    solveU2(),
    // test with a cutting problem
    cutting(1),
    // test with a rostering problem
    roster(12),
    indptSets("g25-01",1),
    indptSets("g25-01",3),
    maxIndptSets("g25-01",1),
    maxIndptSets("g25-01",3),
    indptSets("g25-02",1),
    indptSets("g25-02",3),
    maxIndptSets("g25-02",1),
    maxIndptSets("g25-02",3)]


[allChocoBenchsInterpreted() : void

 -> system.verbose := -1,
    trace(CHOCOBENCH_VIEW,"\n   CHOCO benchmark suite, v~A ...\n===================================\n",choco/CHOCO_RELEASE),
    // test with the n-queens (Gauss) problem
    queen0(10,false),queen0(20,false),queen0(30,false),
    queen0(7,true),queen0(8,true),queen0(9,true),
    queen1(10,false),queen1(20,false),queen1(30,false),queen1(50,false),
    queen1(7,true),queen1(8,true),queen1(9,true),
    // crypt-arithmetic problems
    send(),send2(),send3(),
    // solving systems of linear inequalities
    eq(),
    // the famous zebra problem
    zebras(),zebraf(),zebrap(),zebral(),
    // magic series
    magic(10),magic(30),magic(50),
    // magic squares
    square(3,false,false), square(4,true,false), square(5,true,false),
    //  A simple instance of satellite photo planning
    photo(5,3), photo(8,4), photo(10,5),
    //  The four corner problem: colouring corners of a rectangles a rectangle is never monochromatic
    fcp2(6),fcp2(7),fcp2(8),
    //  A planning problem (have u2 cross a bridge)
    solveU2(),
    // test with a cutting problem
    cutting(1),
    // test with a rostering problem
    roster(6),
    indptSets("gtest",1),
    indptSets("gtest",3),
    maxIndptSets("gtest",1),
    maxIndptSets("gtest",3)
]

[oldbug() : void
 -> system.verbose := -1,
    trace(CHOCOBENCH_VIEW,"\n   CHOCO benchmark suite, v~A ...\n===================================\n",choco/CHOCO_RELEASE),
    // the famous zebra problem
    zebraf(),
    //  A simple instance of satellite photo planning
    photo(5,3)
]
