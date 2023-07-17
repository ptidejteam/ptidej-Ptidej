// ********************************************************************
// a photo satellite scheduling benchmark for Choco                   *
//   file: b_photo.cl,                                                *
//   author: Michel Lemaitre and Francois Laburthe                    *
// ********************************************************************

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

// A simplified version of a problem for planning the schedule of a satellite
//  the satellite has a pool of pictures to take, and must select & plan
//  a subset of them, at each pass above a strip of the earth territory
//
Point <: thing(
     xcoord:integer,
     ycoord:integer)

PhotoOrder <: Point(
     idx:integer,
     visited:choco/IntVar,
     duration:integer,
     earliestArrival:integer,
     latestDeparture:integer,
     revenue:integer)

MaxNbOrders :: 1000

// Storing the information about a problem instance
PhotoProblemInstance <: object(
     solver:choco/Problem,
     orders:list[PhotoOrder],
     start:Point,
     end:Point,
     nbOrders:integer = 0,
   // ------- domain variables for a sequence-based model -------
     sequence:list[choco/IntVar] = nil,
     duration:list[choco/IntVar] = nil,
     earliestA:list[choco/IntVar] = nil,
     latestD:list[choco/IntVar] = nil,
     visitTime:list[choco/IntVar] = nil,
     travelToNext:list[choco/IntVar] = nil,
     travelFromStart:choco/IntVar = unknown,
     travelToEnd:choco/IntVar = unknown,
     revenue:list[choco/IntVar] = nil,
     globalRevenue:choco/IntVar = unknown,
     globalTravel:choco/IntVar = unknown,
   // ------ parameters for generating a random instance -------
       // discrete time model: time instants are in (0 .. MaxTime)
     maxTime:integer = 1000000,
       // each client (order) has coordinates with (0 .. MaxXCoord,  0 .. MaxYCoord),
       // requires a processing time within (MinDuration .. MaxDuration)
       // and yields a revenue within (MinMoney .. MaxMoney)
     maxXCoord:integer = 100,
     maxYCoord:integer = 1000,
     minDuration:integer = 30,
     maxDuration:integer = 60,
     minMoney:integer = 50,
     maxMoney:integer = 250,
       // The satellite progresses over the geographic zone by increasing y-coordinates.
       // At each instant, it may photograph points that are at a y-coordinate around
       // its own y-coordinate, up to MaxYOffset tolerance
     maxYOffset:integer = 100
     )
CURRENT_PI:PhotoProblemInstance := unknown

// travel time between two customers (the constant function could be replaced by something smarter)
// 1/4 of the euclidean distance (faster than satellite speed around the earth, otherwise,
// possibility for optimization)
[travelTime(o1:Point, o2:Point) : integer
 -> integer!((float!(o1.xcoord - o2.xcoord) ^ 2.0 +
              float!(o2.ycoord - o1.ycoord) ^ 2.0) ^ 0.5) / 4]
TravelTime[i1:(1 .. MaxNbOrders), i2:(1 .. MaxNbOrders)] : integer := 0


// ********************************************************************
// *    Part 2: building the model                                    *
// ********************************************************************

[definePhotoProblem(pi:PhotoProblemInstance, sequenceSize:integer)
 -> for o1 in pi.orders
      for o2 in pi.orders
        TravelTime[o1.idx, o2.idx] := travelTime(o1,o2),
    let pb := choco/makeProblem("Pb CNES",7 * sequenceSize + pi.nbOrders + 4) in
      (pi.solver := pb,
       for o in pi.orders
         o.visited := choco/makeBoundIntVar(pb,"ok" /+ string!(o.idx),0,1),
       pi.sequence := list{choco/makeIntVar(pb,"seq" /+ string!(i),1,pi.nbOrders) | i in (1 .. sequenceSize)},
       pi.duration := list{choco/makeBoundIntVar(pb,"d" /+ string!(i),0,pi.maxTime) | i in (1 .. sequenceSize)},
       pi.earliestA := list{choco/makeBoundIntVar(pb,"est" /+ string!(i),0,pi.maxTime) | i in (1 .. sequenceSize)},
       pi.latestD := list{choco/makeBoundIntVar(pb,"let" /+ string!(i),0,pi.maxTime) | i in (1 .. sequenceSize)},
       pi.visitTime := list{choco/makeBoundIntVar(pb,"t" /+ string!(i),0,pi.maxTime) | i in (1 .. sequenceSize)},
       pi.travelToNext := list{choco/makeBoundIntVar(pb,"tvl" /+ string!(i),0,pi.maxTime) | i in (1 .. sequenceSize - 1)},
       pi.travelFromStart := choco/makeBoundIntVar(pb,"tvlA",0,pi.maxTime),
       pi.travelToEnd := choco/makeBoundIntVar(pb,"tvlZ",0,pi.maxTime),

       pi.revenue := list{choco/makeBoundIntVar(pb,"rev" /+ string!(i),0,pi.maxMoney) | i in (1 .. sequenceSize)},

       pi.globalRevenue := choco/makeBoundIntVar(pb,"gRevenue", 0, pi.maxMoney * sequenceSize),
       pi.globalTravel := choco/makeBoundIntVar(pb,"gTravel", 0, pi.maxTime),

            // global structure constraint
       choco/post(pb, choco/allDifferent(pi.sequence)),

           // useless from a propagation point of view, but yields a redundant model
           // this codes a simplified version of the following constraint
;      for i in (1 .. sequenceSize)
;            choco/post(pb,choco/getNth(list{o.visited | o in pi.orders}, pi.sequence[i]) == 1),
       for o in pi.orders
          for i in (1 .. sequenceSize)
            choco/post(pb, (pi.sequence[i] !== o.idx) or (o.visited == 1)),
           // the disjunction propagates more information than the implication
;           choco/post(pb, (pi.sequence[i] == o.idx) implies (o.visited == 1)),

           // a useful redundant constraint
       choco/post(pb,choco/occur(1,list{o.visited |o in pi.orders}) == sequenceSize),

       //[1] sequence model set // ,
            // time window constraints
       for i in (1 .. sequenceSize)
         (choco/post(pb, choco/getNth(list{o.duration | o in pi.orders}, pi.sequence[i]) == pi.duration[i]),
          choco/post(pb, choco/getNth(list{o.earliestArrival | o in pi.orders}, pi.sequence[i]) == pi.earliestA[i]),
;         let lEarliest := list{o.earliestArrival | o in pi.orders} in
;             choco/post(pb,choco/getNth(lEarliest,pi.sequence[i]) == pi.earliestA[i]),
          choco/post(pb, choco/getNth(list{o.latestDeparture | o in pi.orders}, pi.sequence[i]) == pi.latestD[i]),
          choco/post(pb, pi.visitTime[i] >= pi.earliestA[i]),
          choco/post(pb, pi.visitTime[i] <= pi.latestD[i] - pi.duration[i])
         ),
       //[1] ith duration & TW modeled // ,
             // Travel time
       for i in (1 .. sequenceSize - 1)
          (choco/post(pb, choco/makeElt2Term(TravelTime, pi.nbOrders, pi.nbOrders, pi.sequence[i], pi.sequence[i + 1]) == pi.travelToNext[i]),
           choco/post(pb, pi.visitTime[i + 1] >= pi.visitTime[i] + pi.duration[i] + pi.travelToNext[i])
          ),
       //[1] travel constraint set // ,
       choco/post(pb, choco/getNth( list{travelTime(pi.start,o) | o in pi.orders},pi.sequence[1]) == pi.travelFromStart),
       choco/post(pb, choco/getNth( list{travelTime(o,pi.end) | o in pi.orders},pi.sequence[sequenceSize]) == pi.travelToEnd), // v0.24 fix
       choco/post(pb, choco/sumVars(pi.travelToNext)
                      + pi.travelFromStart + pi.travelToEnd                 == pi.globalTravel),
       //[1] global travel constraint set // ,
            // objective function
       for i in (1 .. sequenceSize)
          choco/post(pb, choco/getNth(list{o.revenue | o in pi.orders}, pi.sequence[i]) == pi.revenue[i]),
       choco/post(pb, choco/sumVars(pi.revenue) == pi.globalRevenue),
       //[1] revenue constraints set // ,
       pb)]

// ********************************************************************
// *    Part 3: search algorithms                                     *
// ********************************************************************

[photo(i:integer,j:integer) : void
 ->  trace(CHOCOBENCH_VIEW,"satellite planning (~A) : ",stringFormat(i,2)),
     let pi := photoData(i),
         pb := (time_set(),definePhotoProblem(pi,j)) in
      (choco/SVIEW := 1,
       // new in v1.02: do not branch on disjunctions
       let algo := choco/makeGlobalSearchMaximizer(pi.globalRevenue,false,
                                                   list<choco/AbstractBranching>(
                                                     choco/makeSplitDomBranching(),
                                                     choco/makeAssignVarBranching() )) in
         (choco/setMaxNbBk(algo,10000000),
          choco/setMaxSolutionStorage(algo,1),
          choco/attach(algo,pb),
          choco/run(algo),
;       choco/maximize(pb,pi.globalRevenue,false),
          choco/discardProblem(pb)),  // v1.010
       let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t))]

// ********************************************************************
// *    Part 4: generating data                                       *
// ********************************************************************

//  a function for generating random problem instances
[makePhotoPoint(pi:PhotoProblemInstance,x:integer,y:integer) : Point
 -> Point(xcoord = x, ycoord = y)]

[makePhotoOrder(pi:PhotoProblemInstance,x:integer,y:integer) : PhotoOrder
 -> pi.nbOrders :+ 1,
    let po := PhotoOrder(idx = pi.nbOrders, xcoord = x, ycoord = y) in
       (pi.orders :add po,
        po)]

[makePhotoInstance() : PhotoProblemInstance
 -> let pi := PhotoProblemInstance() in
       (pi.nbOrders := 0,
        pi.start := makePhotoPoint(pi,pi.maxXCoord / 2, 0),
        pi.end := makePhotoPoint(pi,pi.maxXCoord / 2, pi.maxYCoord),
        CURRENT_PI := pi,
        pi ) ]

[photoData(n:integer) : PhotoProblemInstance
 -> let pi := makePhotoInstance() in
       (for i in (1 .. n)
          let po := makePhotoOrder(pi, random(pi.maxXCoord + 1), random(pi.maxYCoord + 1)) in
             (po.duration := pi.minDuration + random(pi.maxDuration - pi.minDuration + 1),
              po.earliestArrival := po.ycoord - pi.maxYOffset,
              po.latestDeparture := po.ycoord + pi.maxYOffset,
              po.revenue := pi.minMoney + random(pi.maxMoney - pi.minMoney + 1)),
        pi) ]

