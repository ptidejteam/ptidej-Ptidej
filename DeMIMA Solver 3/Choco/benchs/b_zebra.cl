// ********************************************************************
// a historical CSP benchmark for Choco: the zebra problem            *
//   file: b_zebra.cl,                                                *
// ********************************************************************

// ********************************************************************
// *  Table of Contents                                               *
// *    Part 1: model with aritmetic constraints                      *
// *    Part 2: model with functional constraints                     *
// *    Part 3: model with tuple constraints                          *
// *    Part 4: various solving methodsaints                          *
// ********************************************************************

// The zebra problem

// left[Yellow]: Norwegian is a Diplomat, owns a Fox and drinks Water
// midleft[Blue]: Italian is a Doctor, owns a Horse and drinks Tea
// middle[Red]: Englishman is a Sculptor, owns a Snail and drinks Milk
// midright[Ivory]: Spaniard is a Violinist owns a Dog and drinks Wine
// right[Green]: Japanese is a Painter owns a Zebra and drinks Coffee

// ********************************************************************
// *    Part 1: model with aritmetic constraints                      *
// ********************************************************************

// first model: constraint with semantics (arithmetic)
[zebraSemanticModel() : choco/Problem
 -> let p := choco/makeProblem("ZEBRA CSP",25),
        green := choco/makeIntVar(p,"green",1 ,5),
        blue := choco/makeIntVar(p,"blue",1 ,5),
        yellow := choco/makeIntVar(p,"yellow",1 ,5),
        ivory := choco/makeIntVar(p,"ivory",1 ,5),
        red := choco/makeIntVar(p,"red",1 ,5),
        diplomat := choco/makeIntVar(p,"diplomat",1 ,5),
        painter := choco/makeIntVar(p,"painter",1 ,5),
        sculptor := choco/makeIntVar(p,"sculptor",1 ,5),
        doctor := choco/makeIntVar(p,"doctor",1 ,5),
        violinist := choco/makeIntVar(p,"violinist",1 ,5),
        norwegian := choco/makeIntVar(p,"norwegian",1 ,5),
        english := choco/makeIntVar(p,"english",1 ,5),
        japanese := choco/makeIntVar(p,"japanese",1 ,5),
        spanish := choco/makeIntVar(p,"spanish",1 ,5),
        italian := choco/makeIntVar(p,"italian",1 ,5),
        wine := choco/makeIntVar(p,"wine",1 ,5),
        milk := choco/makeIntVar(p,"milk",1 ,5),
        coffee := choco/makeIntVar(p,"coffee",1 ,5),
        water := choco/makeIntVar(p,"water",1 ,5),
        tea := choco/makeIntVar(p,"tea",1 ,5),
        fox := choco/makeIntVar(p,"fox",1 ,5),
        snail := choco/makeIntVar(p,"snail",1 ,5),
        horse := choco/makeIntVar(p,"horse",1 ,5),
        dog := choco/makeIntVar(p,"dog",1 ,5),
        zebra := choco/makeIntVar(p,"zebra",1 ,5),
        colors := list(green, blue, yellow, ivory, red),
        trades := list(diplomat, painter, sculptor, doctor, violinist),
        nationalities := list(norwegian, english, japanese, spanish, italian),
        drinks := list(wine, milk, coffee, water, tea),
        pets := list(fox, snail, horse, dog, zebra) in
     (choco/post(p, choco/allDifferent(colors)),
      choco/post(p, choco/allDifferent(trades)),
      choco/post(p, choco/allDifferent(nationalities)),
      choco/post(p, choco/allDifferent(drinks)),
      choco/post(p, choco/allDifferent(pets)),
      choco/post(p, english == red),
      choco/post(p, spanish == dog),
      choco/post(p, coffee == green),
      choco/post(p, italian == tea),
      choco/post(p, sculptor == snail),
      choco/post(p, diplomat == yellow),
      choco/post(p, green == ivory + 1),
      choco/post(p, milk == 3),
      choco/post(p, norwegian == 1),
      choco/post(p, ((doctor - fox == 1) or (doctor - fox == -1))),
      choco/post(p, violinist == wine),
      choco/post(p, japanese == painter),
      choco/post(p, ((diplomat - horse == 1) or (diplomat - horse == -1))),
      choco/post(p, ((norwegian - blue == 1) or (norwegian - blue == -1))),
      p)]

// ********************************************************************
// *    Part 2: model with functional constraints                     *
// ********************************************************************

// second model: constraint expressed with functions for feasibility tests.

[different(i:integer, j:integer) : boolean
 -> assert((1 <= i) & (i <= 5) & (1 <= j) & (j <= 5)),
    i != j]
[neighbors(i:integer, j:integer) : boolean
 -> assert((1 <= i) & (i <= 5) & (1 <= j) & (j <= 5)),
    (i - j = 1) | (j - i = 1)]
[same(i:integer, j:integer) : boolean
 -> assert((1 <= i) & (i <= 5) & (1 <= j) & (j <= 5)),
    (i = j)]
[oneMore(i:integer, j:integer) : boolean
 -> assert((1 <= i) & (i <= 5) & (1 <= j) & (j <= 5)),
    (i = j + 1)]

[postAllDifferencesByFeasTest(p:choco/Problem, l:list[choco/IntVar])
 -> let n := size(l), diffTest := choco/makeBinTruthTest(different @ integer) in 
       for i in (1 .. n - 1)
           for j in (i + 1 .. n)
               choco/post(p,choco/binConstraint(l[i],l[j],diffTest))]

[zebraFunctionModel() : choco/Problem
 -> let p := choco/makeProblem("ZEBRA CSP",25),
        green := choco/makeIntVar(p,"green",1 ,5),
        blue := choco/makeIntVar(p,"blue",1 ,5),
        yellow := choco/makeIntVar(p,"yellow",1 ,5),
        ivory := choco/makeIntVar(p,"ivory",1 ,5),
        red := choco/makeIntVar(p,"red",1 ,5),
        diplomat := choco/makeIntVar(p,"diplomat",1 ,5),
        painter := choco/makeIntVar(p,"painter",1 ,5),
        sculptor := choco/makeIntVar(p,"sculptor",1 ,5),
        doctor := choco/makeIntVar(p,"doctor",1 ,5),
        violinist := choco/makeIntVar(p,"violinist",1 ,5),
        norwegian := choco/makeIntVar(p,"norwegian",1 ,5),
        english := choco/makeIntVar(p,"english",1 ,5),
        japanese := choco/makeIntVar(p,"japanese",1 ,5),
        spanish := choco/makeIntVar(p,"spanish",1 ,5),
        italian := choco/makeIntVar(p,"italian",1 ,5),
        wine := choco/makeIntVar(p,"wine",1 ,5),
        milk := choco/makeIntVar(p,"milk",1 ,5),
        coffee := choco/makeIntVar(p,"coffee",1 ,5),
        water := choco/makeIntVar(p,"water",1 ,5),
        tea := choco/makeIntVar(p,"tea",1 ,5),
        fox := choco/makeIntVar(p,"fox",1 ,5),
        snail := choco/makeIntVar(p,"snail",1 ,5),
        horse := choco/makeIntVar(p,"horse",1 ,5),
        dog := choco/makeIntVar(p,"dog",1 ,5),
        zebra := choco/makeIntVar(p,"zebra",1 ,5),
        colors := list(green, blue, yellow, ivory, red),
        trades := list(diplomat, painter, sculptor, doctor, violinist),
        nationalities := list(norwegian, english, japanese, spanish, italian),
        drinks := list(wine, milk, coffee, water, tea),
        pets := list(fox, snail, horse, dog, zebra),
        sameTest := choco/makeBinTruthTest(same @ integer),
        oneMoreTest := choco/makeBinTruthTest(oneMore @ integer),
        neighborsTest := choco/makeBinTruthTest(neighbors @ integer) in
     (postAllDifferencesByFeasTest(p,colors),
      postAllDifferencesByFeasTest(p,trades),
      postAllDifferencesByFeasTest(p,nationalities),
      postAllDifferencesByFeasTest(p,drinks),
      postAllDifferencesByFeasTest(p,pets),
      choco/post(p, choco/binConstraint(english,red,sameTest)),
      choco/post(p, choco/binConstraint(spanish,dog,sameTest)),
      choco/post(p, choco/binConstraint(coffee,green,sameTest)),
      choco/post(p, choco/binConstraint(italian,tea,sameTest)),
      choco/post(p, choco/binConstraint(sculptor,snail,sameTest)),
      choco/post(p, choco/binConstraint(diplomat,yellow,sameTest)),
      choco/post(p, choco/binConstraint(green,ivory,oneMoreTest)),
      choco/post(p, milk == 3),
      choco/post(p, norwegian == 1),
      choco/post(p, choco/binConstraint(doctor,fox,neighborsTest)),
      choco/post(p, choco/binConstraint(violinist,wine,sameTest)),
      choco/post(p, choco/binConstraint(japanese,painter,sameTest)),
      choco/post(p, choco/binConstraint(diplomat,horse,neighborsTest)),
      choco/post(p, choco/binConstraint(norwegian,blue,neighborsTest)),
      p)]

// ********************************************************************
// *    Part 3: model with tuple constraints                          *
// ********************************************************************

// third model: constraints passed as pairs of feasible values
[postAllDifferencesByFeasPair(p:choco/Problem, l:list[choco/IntVar])
 -> let equalPairs := choco/makeBinRelation(1,5,1,5,list(tuple(1,1),tuple(2,2),tuple(3,3),tuple(4,4),tuple(5,5))), 
        n := size(l) in
      (for i in (1 .. n - 1)
           for j in (i + 1 .. n)
               choco/post(p,choco/binConstraint(l[i],l[j],equalPairs,false)))]

[zebraTupleModel() : choco/Problem
 -> let p := choco/makeProblem("ZEBRA CSP",25),
        green := choco/makeIntVar(p,"green",1 ,5),
        blue := choco/makeIntVar(p,"blue",1 ,5),
        yellow := choco/makeIntVar(p,"yellow",1 ,5),
        ivory := choco/makeIntVar(p,"ivory",1 ,5),
        red := choco/makeIntVar(p,"red",1 ,5),
        diplomat := choco/makeIntVar(p,"diplomat",1 ,5),
        painter := choco/makeIntVar(p,"painter",1 ,5),
        sculptor := choco/makeIntVar(p,"sculptor",1 ,5),
        doctor := choco/makeIntVar(p,"doctor",1 ,5),
        violinist := choco/makeIntVar(p,"violinist",1 ,5),
        norwegian := choco/makeIntVar(p,"norwegian",1 ,5),
        english := choco/makeIntVar(p,"english",1 ,5),
        japanese := choco/makeIntVar(p,"japanese",1 ,5),
        spanish := choco/makeIntVar(p,"spanish",1 ,5),
        italian := choco/makeIntVar(p,"italian",1 ,5),
        wine := choco/makeIntVar(p,"wine",1 ,5),
        milk := choco/makeIntVar(p,"milk",1 ,5),
        coffee := choco/makeIntVar(p,"coffee",1 ,5),
        water := choco/makeIntVar(p,"water",1 ,5),
        tea := choco/makeIntVar(p,"tea",1 ,5),
        fox := choco/makeIntVar(p,"fox",1 ,5),
        snail := choco/makeIntVar(p,"snail",1 ,5),
        horse := choco/makeIntVar(p,"horse",1 ,5),
        dog := choco/makeIntVar(p,"dog",1 ,5),
        zebra := choco/makeIntVar(p,"zebra",1 ,5),
        colors := list(green, blue, yellow, ivory, red),
        trades := list(diplomat, painter, sculptor, doctor, violinist),
        nationalities := list(norwegian, english, japanese, spanish, italian),
        drinks := list(wine, milk, coffee, water, tea),
        pets := list(fox, snail, horse, dog, zebra),
        equalPairs := choco/makeBinRelation(1,5,1,5,list(tuple(1,1),tuple(2,2),tuple(3,3),tuple(4,4),tuple(5,5))), 
        neighborPairs := choco/makeBinRelation(1,5,1,5,list( tuple(1,2), tuple(2,1), tuple(2,3), tuple(3,2),tuple(3,4), tuple(4,3), tuple(4,5), tuple(5,4))),
        add1Pairs := choco/makeBinRelation(1,5,1,5,list(tuple(2,1),tuple(3,2),tuple(4,3),tuple(5,4)))         
         in
     (postAllDifferencesByFeasPair(p,colors),
      postAllDifferencesByFeasPair(p,trades),
      postAllDifferencesByFeasPair(p,nationalities),
      postAllDifferencesByFeasPair(p,drinks),
      postAllDifferencesByFeasPair(p,pets),
      choco/post(p, choco/binConstraint(english,red,equalPairs)),
      choco/post(p, choco/binConstraint(spanish,dog,equalPairs)),
      choco/post(p, choco/binConstraint(coffee,green,equalPairs)),
      choco/post(p, choco/binConstraint(italian,tea,equalPairs)),
      choco/post(p, choco/binConstraint(sculptor,snail,equalPairs)),
      choco/post(p, choco/binConstraint(diplomat,yellow,equalPairs)),
      choco/post(p, choco/binConstraint(green,ivory,add1Pairs)),
      choco/post(p, milk == 3),
      choco/post(p, norwegian == 1),
      choco/post(p, choco/binConstraint(doctor,fox,neighborPairs)),
      choco/post(p, choco/binConstraint(violinist,wine,equalPairs)),
      choco/post(p, choco/binConstraint(japanese,painter,equalPairs)),
      choco/post(p, choco/binConstraint(diplomat,horse,neighborPairs)),
      choco/post(p, choco/binConstraint(norwegian,blue,neighborPairs)),
      p)]

// ********************************************************************
// *    Part 4: various solving methodsaints                          *
// ********************************************************************

// returns the solving time
[solveZebraModel(pb:choco/Problem) : integer
 -> time_set(),
    let algo := choco/makeGlobalSearchSolver(true) in
      (;choco/setMaxPrintDepth(algo,100),
       choco/attach(algo,pb),
       choco/run(algo),
       choco/discardProblem(pb),  // v1.010
       if (choco/getNbSol(algo) != 1)
          error("zebraf finds ~S solutions instead of 1",choco/getNbSol(algo)),
       time_get() )]

[zebras()
 -> trace(CHOCOBENCH_VIEW,"zebra (arithm)          : "),
    let t := solveZebraModel(zebraSemanticModel()) in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t) ]

[zebraf()
 -> trace(CHOCOBENCH_VIEW,"zebra (CSP functions)   : "),
    let t := solveZebraModel(zebraFunctionModel()) in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t) ]

// note: this may cause a bug
[zebrap()
 -> trace(CHOCOBENCH_VIEW,"zebra (tuples)          : "),
    let t := solveZebraModel(zebraTupleModel()) in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]

// fourth approach: same model as second approach (with feasibility tests) but with local moves

[zebral()
 -> trace(CHOCOBENCH_VIEW,"zebra (min conflicts)   : "),
    time_set(),
    let p := zebraFunctionModel() in
     (choco/setMaxNbLocalMoves(p,30),
      choco/setMaxNbLocalSearch(p,100),
      choco/propagate(p),
      choco/move(p) ),
    let t := time_get() in
        trace(CHOCOBENCH_VIEW,"~S ms.\n",t) ]
