// **************************************************************//
// a crew rostering benchmark for Choco
//   file: b_roster.cl,
//   author: Benoit Rottembourg for the CHIC-2 project
// **************************************************************

// ********************************************************************
// *  Table of Contents                                               *
// *    Part 1: data model                                            *
// *    Part 2: building the model                                    *
// *    Part 3: search algorithms                                     *
// *    Part 4: generating data                                       *
// ********************************************************************


// ********************************************************************
// *    Part 1: data model                                            *
// ********************************************************************

// ------ Dates: a cyclic schedule represented by a bi-dimensional grid
//          accessed by a pairs of integers (week/weekdays)
WEEKDAY :: (1 .. 7)
MAXWEEK :: 24
WEEK :: (1 .. MAXWEEK)

Nweek:integer := 6
allWEEKS:Interval := (1 .. Nweek)

DATE :: tuple(WEEK,WEEKDAY)
FirstMonday :: tuple(1,1)
[nextWD(d:DATE) : DATE
 -> let (w,wd) := d in
      (if (wd != 7) tuple(w,wd + 1)
       else if (w != Nweek) tuple(w + 1, 1)
       else FirstMonday)]

// ------ Activities: coded as integers
DayOff :: 1
Morning :: 2
MidDay :: 3
Evening :: 4
Joker :: 5

ACTIVITY :: (1 .. 5)

// ------ Data model
WorkLoad[a:ACTIVITY,wd:WEEKDAY] : integer := 0  // filled by the data file
Assignment[w:WEEK,wd:WEEKDAY] : any := unknown

// internal storage for the heuristic
CoverageConstraint[a:ACTIVITY,wd:WEEKDAY] : choco/AbstractConstraint := unknown

// ********************************************************************
// *    Part 2: building the model                                    *
// ********************************************************************

[createRosteringModel() : choco/Problem
 -> let p := choco/makeProblem("Ultimate rostering",500) in
      (for w in allWEEKS
         for wd in WEEKDAY
           Assignment[w,wd] := choco/makeIntVar(p,"A" /+ string!(w) /+ "-" /+ string!(wd),1,5),
       p)]

// ------- Algebraic model
[createRosteringConstraints(p:choco/Problem) : void
 -> mustSleep(p),
    isolateDO(p),
    coverage(p),
    mustBreathe(p),
    seeYourBoss(p)]

[mustSleep(p:choco/Problem) : void
 -> //[0] set the must-sleep constraints,
    for w in allWEEKS
     for wd in WEEKDAY
       let (w2:WEEK, wd2:WEEKDAY) := nextWD(tuple(w,wd)) in
          (//[5] set constraint on ~S ~S ~S ~S // w,wd,w2,wd2,
           choco/post(p,(Assignment[w,wd] == Evening) implies (Assignment[w2,wd2] !== Morning) ))]

[coverage(p:choco/Problem) : void
  -> //[0] workload coverage,
     for a in ACTIVITY
       for wd in WEEKDAY
         (CoverageConstraint[a,wd] :=
            (choco/occur(a, list{Assignment[w,wd] | w in allWEEKS}) == WorkLoad[a,wd]),
          choco/post(p,CoverageConstraint[a,wd])) ]

[isolateDO(p:choco/Problem) : void
 -> //[0] no isolated day off,
    let day1 := tuple(1,1), day2 := tuple(1,2), day3 := tuple(1,3) in
       (until (day1 = FirstMonday)
           ( //[5] set constraint on ~S ~S ~S // day1, day2, day3,
             choco/post(p,(Assignment[day2] == DayOff) choco/implies ((Assignment[day1] == DayOff) or
                                                           (Assignment[day3] == DayOff))),
             day1 := day2,
             day2 := day3,
             day3 := nextWD(day3) ))]

[mustBreathe(p:choco/Problem) : void
 -> //[0] must-breathe constraints,
    let window := list<DATE>{tuple(1,i) | i in (1 .. 6)} in
      (until (window[1] = FirstMonday)
           ( choco/post(p,choco/occur(DayOff,list{Assignment[d] | d in window}) >= 1),
             for i in (1 .. 5) window[i] := window[i + 1],
             window[6] := nextWD(window[5]) )) ]

[seeYourBoss(p:choco/Problem) : void
 -> //[0] don't forget the boss constraints,
    let window := list<DATE>{tuple(1,i) | i in (1 .. 4)} in
      (until (window[1] = FirstMonday)
           ( choco/post(p,choco/occur(DayOff,list{Assignment[d] | d in window}) <= 3),
             for i in (1 .. 3) window[i] := window[i + 1],
             window[4] := nextWD(window[3]) )) ]

// ********************************************************************
// *    Part 3: search algorithms                                     *
// ********************************************************************

// dedicated branching scheme: focus on highest DO requirements first
DayOffPlacement <: choco/LargeBranching()

[choco/selectBranchingObject(b:DayOffPlacement) : (WEEKDAY U {unknown})
 -> let bestWD:(WEEKDAY U {unknown}) := unknown, greatestDOreq := 2 in
       (for wd in WEEKDAY
          let req :=  WorkLoad[DayOff,wd] - CoverageConstraint[DayOff,wd].choco/nbSure in
             (if (req >= greatestDOreq) (bestWD := wd, greatestDOreq := req)),
        bestWD)]
        
[choco/finishedBranching(b:DayOffPlacement, wd:any, previousw:integer) : boolean
 -> case wd
     (integer forall(w in allWEEKS | (choco/isInstantiated(Assignment[w,wd]) |
                                      not(choco/canBeInstantiatedTo(Assignment[w,wd],DayOff)))),
      any error("ill typed parameter to ~S",b))]

[choco/getFirstBranch(b:DayOffPlacement, wd:any) : integer
 -> case wd
     (integer 
         let bestw := -1, leastA := 10000 in
          (for w in {w in allWEEKS | not(choco/isInstantiated(Assignment[w,wd])) &
                        choco/canBeInstantiatedTo(Assignment[w,wd],DayOff)}
             let numAssigned := count({wd2 in (WEEKDAY but wd) | 
                                            choco/isInstantiatedTo(Assignment[w,wd2],DayOff)}) in
               (if (numAssigned < leastA) (leastA := numAssigned, bestw := w)),
           //[5] for wd:~S, bestw:~S // wd,bestw,
           if (bestw = -1) error("should never happen"),
           bestw),
      any error("wd should be a day"))]

[choco/getNextBranch(b:DayOffPlacement, wd:any, previousw:integer) : integer
 -> case wd
     (integer 
         let bestw := -1, leastA := 10000 in
          (for w in {w in allWEEKS | not(choco/isInstantiated(Assignment[w,wd])) &
                        choco/canBeInstantiatedTo(Assignment[w,wd],DayOff)}
             let numAssigned := count({wd2 in (WEEKDAY but wd) | 
                                            choco/isInstantiatedTo(Assignment[w,wd2],DayOff)}) in
               (if (numAssigned < leastA) (leastA := numAssigned, bestw := w)),
           //[5] for wd:~S, bestw:~S // wd,bestw,
           if (bestw = -1) error("should never happen"),
           bestw),
      any error("wd should be a day"))]
           
[choco/goDownBranch(b:DayOffPlacement, wd:any, w:integer) : void
 -> choco/setVal(Assignment[w,wd], DayOff),
    choco/propagate(choco/getProblem(b))]

[choco/traceDownBranch(b:DayOffPlacement, wd:any, w:integer) : void
 -> //[SVIEW] ~S: day off for wd:~S, try to place at ~S // world?(),wd,w
]
                         
[choco/goUpBranch(b:DayOffPlacement, wd:any, w:integer) : void
 -> choco/remVal(Assignment[w,wd], DayOff),
    choco/propagate(choco/getProblem(b))]

[choco/traceUpBranch(b:DayOffPlacement, wd:any, w:integer) : void
 -> //[SVIEW] ~S: day off for wd:~S, try to forbid place at ~S // world?(),wd,w
]

// utility for debugging: viewing current domains
[viewRosterDoms() : void
 -> princ("\n"),
    for w in allWEEKS
      (for wd in WEEKDAY
         let v := Assignment[w,wd] in
            (if choco/canBeInstantiatedTo(v,Morning) princ("M") else princ("."),
             if choco/canBeInstantiatedTo(v,MidDay) princ("D") else princ("."),
             if choco/canBeInstantiatedTo(v,Evening) princ("E") else princ("."),
             if choco/canBeInstantiatedTo(v,Joker) princ("J") else princ("."),
             if choco/canBeInstantiatedTo(v,DayOff) princ("0  ") else princ(".  ") ),
       princ("\n"))]

// viewing solutions
[viewRosterSol() : void
 -> princ("\n"),
    for w in allWEEKS
      (for wd in WEEKDAY
          case Assignment[w,wd].value
             ({Morning} princ("M "),
              {MidDay}  princ("D "),
              {Evening} princ("E "),
              {Joker}   princ("J "),
              {DayOff}  princ("O ") ),
        princ("\n"))]


// ------- Solving
[solveRoster() : void
 -> let p := createRosteringModel() in
     (createRosteringConstraints(p),
      choco/propagate(p),
      //[0] start solving //,
      let algo := choco/makeGlobalSearchSolver(false,
                     list(DayOffPlacement(), choco/makeAssignVarBranching())) in
        (choco/attach(algo,p),
         choco/setMaxPrintDepth(algo,100),
         choco/run(algo),
         if (choco/getFeasibility(algo) & system.verbose > 0) viewRosterSol(),
         choco/discardProblem(p)  // v1.010
         ))]

// The main function
[roster(i:integer) : void
 -> trace(CHOCOBENCH_VIEW,"rostering     (~A)  : ",stringFormat(i,2)),
    time_set(),
    if (i = 3)       (loadRosterData3(), solveRoster())
    else if (i = 6)  (loadRosterData6(), solveRoster())
    else if (i = 12) (loadRosterData12(), solveRoster())
    else if (i = 24) (loadRosterData24(), solveRoster())
    else error("no data for roster(~S)",i),
    let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]

// ********************************************************************
// *    Part 4: generating data                                       *
// ********************************************************************

[rosteringData(l:list[list[integer]])
 -> let nbcells := sum(list{sum(la) | la in l}) in
      (assert(nbcells mod 7 = 0),
       Nweek := nbcells / 7,
       allWEEKS := (1 .. Nweek)),
    for wd in WEEKDAY
      (WorkLoad[Morning,wd] := l[1][wd],
       WorkLoad[MidDay,wd] := l[2][wd],
       WorkLoad[Evening,wd] := l[3][wd],
       WorkLoad[Joker,wd] := l[4][wd],
       WorkLoad[DayOff,wd] := l[5][wd])]

[loadRosterData3() // infeasible !!
 -> rosteringData(list(list(1,0,0,1,0,1,0),  // Morning
              list(0,0,0,0,0,0,1),  // MidDay
              list(1,0,0,0,0,0,0),  // Evening
              list(0,0,1,0,1,0,0),  // Joker
              list(1,3,2,2,2,2,2)) )]  // DayOff
[loadRosterData6()
 -> rosteringData(list(list(2,1,1,1,1,1,1),  // Morning
              list(0,1,0,0,1,1,0),  // MidDay
              list(1,1,2,0,0,0,1),  // Evening
              list(1,0,1,2,1,1,1),  // Joker
              list(2,3,2,3,3,3,3)) )]  // DayOff
[loadRosterData12()
 -> rosteringData(list(list(3,2,2,3,2,4,2),  // Morning
              list(0,1,1,0,1,0,1),  // MidDay
              list(4,2,2,1,1,0,1),  // Evening
              list(1,1,2,2,2,2,2),  // Joker
              list(4,6,5,6,6,6,6)) )]  // DayOff
[loadRosterData24()
 -> rosteringData(list(list(4,3,4,3,2,0,3),  // Morning
              list(2,3,2,3,4,5,8),  // MidDay
              list(6,5,8,7,5,7,5),  // Evening
              list(4,4,5,4,4,4,5),  // Joker
              list(8,9,5,7,9,8,3)) )]  // DayOff


