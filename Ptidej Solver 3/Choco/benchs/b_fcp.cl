// ********************************************************************
// the four corner problem benchmark for Choco                        *
//   file: b_fcp.cl,                                                  *
//   author: Michel Lemaitre                                          *
// ********************************************************************

// ********************************************************************
// *  Table of Contents                                               *
// *    Part 1: standard model                                        *
// *    Part 2: additional heuristics                                 *
// *    Part 3: search algorithms                                     *
// ********************************************************************

// The `four corners problem' (contribution by Mihel Lemaître)
// 
// Given a n x n board (in the standard problem, n=10) and three
// colours, the goal is to assign each place a colour such that, for any
// rectangle or square sub-board, the four corner places are not assigned
// the same colour; in other words, for any quadruple of places that
// forms a rectangle or a square, let a, b, c, and d be the colours of
// these four places, we must have : not(a=b=c=d). This problem can be
// easily cast as a CSP, with n^2 variables and (n x (n-1)/2)^2
// constraints of arity 4.

// Note: this problem originates from the "problème des 4 coins",
// published in "Pour la Science" no 250 Aout 1998, page 24.
// The article provides the reader with a solution for n=10
 
FCPInstance <: object(length:integer,
                      width:integer,
                      nbColors:integer,
                      places:list[list[choco/IntVar]],
                      problem:choco/Problem)

// ********************************************************************
// *    Part 1: standard model                                        *
// ********************************************************************

[printSolution(fcpi:FCPInstance) : void
 -> printf("\n a solution for n = ~S, m = ~S : \n",fcpi.length, fcpi.width),
    for r in (1 .. fcpi.length)
     (for c in (1 .. fcpi.width)
         printf(" ~S", fcpi.places[r][c].value),
      printf("\n") )]

// First, the simplest way to express the problem
[makeFourCornerProblemModel(n:integer, m:integer, nbc:integer) : FCPInstance
 ->     // Create the object storing the problem instance
    let fcpi := FCPInstance(length = n, width = m, nbColors = nbc),
      // Create problem.
      pb := choco/makeProblem("Four Corners Problem", n * m),
      // Create variables.
      place := list{ list{ choco/makeBoundIntVar(pb, "place" /+ "_" /+ string!(i) /+ "_" /+ string!(j), 1, nbc) |
		           j in (1 .. m) } | // second index
                     i in (1 .. n) }  // first index 
   in (
      // Post the inequality constraints for each sub-rectangle, as before.
      for r1 in (1 .. n - 1)
      for r2 in (r1 + 1 .. n)
         for c1 in (1 .. m - 1)
         for c2 in (c1 + 1 .. m) (
             choco/post(pb, (place[r1][c1] !== place[r1][c2]) or
                      (place[r1][c2] !== place[r2][c1]) or
                      (place[r2][c1] !== place[r2][c2]) )
            ),

      fcpi.problem := pb,
      fcpi.places := place,
      fcpi)]

// Then a program posting additionnal heuristic constraints
// in order to guide the search towards a solution (useful for n >= 7).
// The heuristic is based on the assumption that in a solution, colors
// will be more or less equally distributed over the board.

[addFourCornerDominanceConstraint(fcpi:FCPInstance) : void
 -> let pb := fcpi.problem,
        m := fcpi.width, n := fcpi.length,
        place := fcpi.places in

     (// Additional dominance constraints :
      // the first row and the first column must be sorted by increasing order
      // solutions remain valid under permutations of rows and columns

      for c in (1 .. m - 1)
          choco/post(pb, place[1][c] <= place[1][c + 1]),
      for r in (1 .. n - 1)
          choco/post(pb, place[r][1] <= place[r + 1][1])
     )]

// ********************************************************************
// *    Part 2: additional heuristics                                 *
// ********************************************************************

[addFourCornerHeuristics(fcpi:FCPInstance) : void
 -> let pb := fcpi.problem,
        n := fcpi.length,
        place := fcpi.places in

      // Additional (non-redondant) constraints :
      // in each row, the number or places of a given color
      // is at least N/3 and at most N/3 + 1,
      // and the same holds for columns.

      for color in (1 .. fcpi.nbColors) (
         for r in (1 .. n) (
            choco/post(pb, choco/occur(color, list{ place[r][c] | c in (1 .. n)}) <= n / fcpi.nbColors + 1),
            choco/post(pb, choco/occur(color, list{ place[r][c] | c in (1 .. n)}) >= n / fcpi.nbColors)
         ),
         for c in (1 .. n) (
            choco/post(pb, choco/occur(color, list{ place[r][c] | r in (1 .. n)}) <= n / fcpi.nbColors + 1),
            choco/post(pb, choco/occur(color, list{ place[r][c] | r in (1 .. n)}) >= n / fcpi.nbColors)
         )
  )
]

// For comparison : the corresponding OPL models :
//
// The first one
// (can only find solutions up to N=6)
//
// int N = 6 ;
// 
// range Rows [1..N];
// range Columns [1..N];
// range Colors [1..3];
//
// var Colors p[Rows, Columns]; 
// 
// solve {
//    forall (r1, r2 in Rows : r1 < r2) 
//       forall (c1, c2 in Columns : c1 < c2) 
//          not (p[r1, c1] = p[r1, c2] = p[r2, c1] = p[r2, c2])
// };
//

// The second one with additional constraints
// Can only find solutions up to N=9 (in less than 1 night).
//
//int N = 9;
//
//range Rows [1..N];
//range Columns [1..N];
//range Colors [1..3];
//
//var Colors p[Rows, Columns]; 
//
//solve {
//   
//   // Basic Constraints
//   forall (r1, r2 in Rows : r1 < r2) 
//      forall (c1, c2 in Columns : c1 < c2) 
//         not (p[r1, c1] = p[r1, c2] = p[r2, c1] = p[r2, c2]);
//         
//   // In each row, the number of places of a given color
//   // is at least N/3 and at most N/3 + 1,
//   // And the same holds for columns.
//   
//   forall (color in Colors) {
//      forall (r in Rows)
//         (N/3) <= (sum (c in Columns) (p[r, c] = color)) <= (N/3 + 1) ;
//      forall (c in Columns)
//         (N/3) <= (sum (r in Rows) (p[r, c] = color)) <= (N/3 + 1) ;         
//   }
//};

// ********************************************************************
// *    Part 3: search algorithms                                     *
// ********************************************************************

[solveColoringProblem(fcpi:FCPInstance) : void
 -> let pb := fcpi.problem,
        algo := choco/makeGlobalSearchSolver(false) in
       (choco/setMaxNbBk(algo,10000000),
        choco/attach(algo,pb),
        choco/run(algo),
        if choco/getFeasibility(algo)
          (if (system.verbose >= 0)
              printSolution(fcpi)),
        choco/discardProblem(pb)  // v1.010
        )]

[fcp(n:integer) : void ->
   trace(CHOCOBENCH_VIEW,"4corners (coloring) (~A) : ",stringFormat(n,1)),
   time_set(),
   solveColoringProblem(makeFourCornerProblemModel(n,n,3)),
   let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]


[fcp2(n:integer) : void ->
   trace(CHOCOBENCH_VIEW,"4corners (coloring) (~A) : ",stringFormat(n,1)),
   time_set(),
   let fcpi := makeFourCornerProblemModel(n,n,3) in
     (addFourCornerDominanceConstraint(fcpi),
      addFourCornerHeuristics(fcpi),
      solveColoringProblem(fcpi)),
   let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]

// Bicolored
[fcp3(n:integer,m:integer) : void ->
   trace(CHOCOBENCH_VIEW,"4corners (coloring) (~A) : ",stringFormat(n,1)),
   time_set(),
   let fcpi := makeFourCornerProblemModel(n,m,2) in
     (addFourCornerDominanceConstraint(fcpi),
      addFourCornerHeuristics(fcpi),
      solveColoringProblem(fcpi)),
   let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]
 
