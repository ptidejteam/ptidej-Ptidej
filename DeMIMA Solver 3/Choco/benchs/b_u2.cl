// benchmark proposed by Michel Lemaitre.

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//           ============  The U2 problem =============
//
// # "U2" has a Christmas concert that starts in 17 minutes and they must
// # all cross a bridge to get there.  All four men begin on the same side
// # of the bridge.  You must help them across to the other side. It is
// # night. There is one flashlight.  A maximum of two people can cross at
// # one time.  Any party who crosses, either 1 or 2 people, must have the
// # flashlight with them.  The flashlight must be walked back and forth,
// # it cannot be thrown, etc.  Each band member walks at a different
// # speed.  A pair must walk together at the rate of the slower man's
// # pace:
// # 
// #            Bono:- 1 minute to cross
// #            Edge:- 2 minutes to cross
// #            Adam:- 5 minutes to cross
// #            Larry:-10 minutes to cross
// # 
// # For example: if Bono and Larry walk across first, 10 minutes have
// # elapsed when they get to the other side of the bridge.  If Larry then
// # returns with the flashlight, a total of 20 minutes have passed and you
// # have failed the mission.  Notes: There is no trick behind this.  It is
// # the simple movement of resources in the appropriate order.  There are
// # two known answers to this problem.  This is based on a question
// # Microsoft gives to all prospective employees.  Note: Microsoft expects
// # you to answer this question in under 5 minutes!  Good Luck!  REMEMBER
// # - all the parameters you need to solve the problem are given to you!
// # That means all 4 guys ARE AT THE OTHER SIDE in 17 minutes. 2 guys at a
// # time MAX on the bridge at any time AND they HAVE to have the ONE
// # flashlight with them.


// [Thanks to Choco, you get the answer in a few seconds.]

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 
u2member <: thing(thename:string, rate:integer)

U2 :: list( u2member(thename = "Larry", rate = 10),
            u2member(thename = "Adam", rate = 5),
            u2member(thename = "Edge", rate = 2), 
            u2member(thename = "Bono", rate = 1),
            u2member(thename = "Flashlight", rate = 0)  )
nbU :: 5
uFlashlight :: 5

maxDurationU2 :: 17

[fDefDuration(xs:list[integer]) : boolean  
-> let d := xs[1], ms := cdr(xs), rs := list{ u.rate | u in U2 } in 
      d = choco/max(list{ (rs[i] * ms[i]) | i in (1 .. nbU) }) ]

// A constraint satisfied if and only if d is the max 
// of the rates of moving people. 
[cDefDuration(d:choco/IntVar, moves:list[choco/IntVar]) : choco/AbstractConstraint 
;-> choco/feasTestConstraint(cons(d, moves), fDefDuration @ list)]
-> choco/feasTestConstraint(list(d) /+ moves, fDefDuration @ list)]

[makeList2Var(p:choco/Problem, ident:string, n:integer, vinf:integer, vsup:integer) 
-> list{ list{ choco/makeIntVar(p, ident /+ "_" /+ string!(s) /+ "_" /+ u.thename, vinf, vsup)
               | u in U2 }     // second index : U2 member
         | s in (1 .. n) } ]   // first index : step or state

[u2(nbSteps:integer) 
-> let nbStates := (nbSteps + 1),
       pb := choco/makeProblem("The U2 problem", 200),
       move := makeList2Var(pb, "move", nbSteps, 0, 1),
       position := makeList2Var(pb, "position", nbStates, 0, 1),
       outward := makeList2Var(pb, "outward", nbSteps, 0, 1),
       backward := makeList2Var(pb, "backward", nbSteps, 0, 1),
       duration := list{ choco/makeIntVar(pb, "duration_" /+ string!(s), 1, maxDurationU2)
                          | s in (1 .. nbSteps) },
       w := world?() in
     (for u in (1 .. nbU)
        (//  Starting and ending positions. 0 is before the bridge, 1 is after crossing.
         choco/post(pb, position[1][u] == 0),          // starting positions
         choco/post(pb, position[nbStates][u] == 1),   // ending positions
 
         for s in (1 .. nbSteps)             
           (//  Define movements from positions.
            choco/post(pb, (outward[s][u] == 1)  ifOnlyIf (position[s][u] < position[s + 1][u])),
            choco/post(pb, (backward[s][u] == 1) ifOnlyIf (position[s + 1][u] < position[s][u])),
            choco/post(pb, move[s][u] == outward[s][u] + backward[s][u]),

            // a redundant constraint that could be useful if we were to instantiate move variables instead of position
;            choco/post(pb, (move[s][u] == 0) ifOnlyIf (position[s + 1][u] == position[s][u])),

            
            //redundant constraint (because the cDefDuration one only propagates once all but one are instantiated)         
            choco/post(pb, (move[s][u] == 1) implies (duration[s] >= U2[u].rate)),
            choco/post(pb, (duration[s] < U2[u].rate) implies (move[s][u] == 0)), //counter-opposite
                           
            //  If somebody crosses, the flashlight also crosses in the same direction.
            choco/post(pb, outward[s][u] <= outward[s][uFlashlight]),
            choco/post(pb, backward[s][u] <= backward[s][uFlashlight]) )),
   
      for s in (1 .. nbSteps) 
        (choco/setVal(move[s][uFlashlight],1), //the flashlight always moves          
         choco/post(pb, cDefDuration(duration[s], move[s])),    //  Connect durations and moves.
         choco/post(pb, choco/sumVars(move[s]) >= 2), // At each step, at least 2 (including the flashlight) are moving.
         choco/post(pb, choco/sumVars(move[s]) <= 3)), //  At each step, at most 3 (including the flashlight) are moving.
         
      choco/post(pb, choco/sumVars(duration) <= maxDurationU2),// The constraint over the total duration.

      // Try to solve the problem
      let algo := choco/makeGlobalSearchSolver(false) in
        (choco/setMaxSolutionStorage(algo,1),
         choco/attach(algo,pb),
         choco/run(algo),
         if choco/getFeasibility(algo)
           (if (verbose() >= 1) displaySolution(pb, position, duration, nbSteps),
            choco/setWorld(pb,w),
            choco/discardProblem(pb),  // v1.010
            true)
         else (choco/discardProblem(pb),  // v1.010
               false)))]
  

[displaySolution(pb:choco/Problem, position:list, duration:list, nbSteps:integer) 
-> if pb.choco/feasible 
     (printf("Found a solution in ~S steps :\n\n", nbSteps),
      for s in (1 .. nbSteps + 1)
        (printf("State ~S ->  ", s),
         for u in (1 .. nbU) (
            printf(" ~S:~S",U2[u], position[s][u].value)),
         if (s != nbSteps + 1) 
           (printf("\n\n        step~S -> ", s),
            printf(" duration=~S\n", duration[s].value)),
         printf("\n")))
   else (printf("No solution found in ~S steps\n\n", nbSteps) )]


// Try more and more steps until a solution is found.
[solveU2() 
-> trace(CHOCOBENCH_VIEW,"U2 planning problem     : "),
   time_set(),
   let i := 1 in (until ( (i > 7) | u2(i)) i :+ 2), //adds 2 because even number of step is impossible.           
   let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)  ]
   
