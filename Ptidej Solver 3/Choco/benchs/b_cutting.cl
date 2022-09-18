// ********************************************************************
// a wood cutting benchmark for Choco                                 *
//   file: b_cutting.cl,                                              *
//   author: Eric Bourreau and François Xavier Josset                 *
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

// verbosity level
CUTTALK :: 2

// the original shape to be cut
SHAPE <: thing(length:integer,
               width:integer)

// forward
Piece <: object

TypePiece <: object(pieces:list<Piece>, // pieces of this type
                    length:integer,     // pieces length
                    width:integer,     // pieces width
                    cost:integer,     // pieces cost
                    minNb:integer = 0, // minimal nb. of such pieces to be produced
                    maxNb:integer,     // maximal nb. of such pieces to be produced
                    nbPlaced:choco/IntVar) // nb. of such pieces that are produced  a <= n <= b

CuttingInstance <: object(
                   pieceTypes:list<TypePiece>,
                   nbTypes:integer = 0,
                   pieceList:list[Piece],
                   originalShape:SHAPE,
                   problem:choco/Problem,     // the choco model of the cutting problem
                   producedArea:choco/IntVar) // The cost function and the problem

[self_print(t:TypePiece) -> printf("t(~S,~S)",t.length,t.width)]
                
Piece <: object(ofType:TypePiece,   // type de cette piece
                x:choco/IntVar,   // x-coordinate of the lower left angle
                y:choco/IntVar,   // y-coordinate of the lower left angle
                id:integer,       // unique indentifier
                used:choco/IntVar)
(inverse(pieces) := ofType)
[self_print(p:Piece) -> printf("p~S[~S]",p.id,p.ofType)]
[p(i:integer) : (Piece U {unknown}) -> some(p in Piece | p.id = i)]
[moreInteresting(p1:Piece, p2:Piece) : boolean
 -> let t1 := p1.ofType, t2 := p2.ofType in
      (t1.cost * t2.length * t2.width <= t2.cost * t1.length * t1.width)]

[defineType(ci:CuttingInstance,i1:integer,i2:integer,i3:integer,i4:integer) : TypePiece
 -> let tp := TypePiece(length = i1, width = i2, cost = i3, maxNb = i4) in
       (ci.pieceTypes :add tp,
        tp)]

[defineGlobals(i:integer,j:integer,k:integer,l:integer) : CuttingInstance
 -> CuttingInstance(originalShape = SHAPE(length = i, width = j),
                    nbTypes = k)]

// util for pretty printing
[bord?(p:Piece,a:integer,b:integer) : (char U {unknown})
   ->    if ((a = get(value,p.x))         & (b = get(value,p.y))        ) '*'
         else if (((a = get(value,p.x) + p.ofType.length) & (b = get(value,p.y))        ) |
                  ((a = get(value,p.x))         & (b = get(value,p.y) + p.ofType.width)) |
                  ((a = get(value,p.x) + p.ofType.length) & (b = get(value,p.y) + p.ofType.width)))  '+'
         else if (((a = get(value,p.x)) | (a = get(value,p.x) + p.ofType.length)) & (b % (get(value,p.y) .. (get(value,p.y) + p.ofType.width))))       '-'
         else if (((b = get(value,p.y)) | (b = get(value,p.y) + p.ofType.width)) & (a % (get(value,p.x) .. (get(value,p.x) + p.ofType.length))))       '|'
         else unknown ]

// displaying a solution
[displayCuttingSolution(ci:CuttingInstance)
   -> printf("\n"),
      let pp := {p in ci.pieceList | get(value,p.used) = TRUE} in
        (for i in (0 .. ci.originalShape.length )
           (for j in (0 .. ci.originalShape.width )
               (when p := some(z in pp | known?(bord?(z,i,j))) in
                   printf("~A", bord?(p,i,j))
                else printf("."),
                printf(" ")),
           printf("\n"))),
      printf("\n") ]


// ********************************************************************
// *    Part 4: generating data                                       *
// ********************************************************************

// read data from file inputfile
;[readCuttingData(inputfile:string) : CuttingInstance
;  -> //[CUTTALK] read data and allocate first objects //,
;     let p := fopen(inputfile,"r"),
;         ci := defineGlobals(read(p), read(p), read(p), read(p)) in
;       (for i in (1 .. ci.nbTypes)
;            defineType(ci,read(p), read(p), read(p), read(p)),
;        fclose(p),
;        createDataModel(ci),
;        ci) ]

[cuttingData(i:integer) : CuttingInstance
 -> let ci := defineGlobals(10,10,2,4) in
      (defineType(ci,2,3,6,8),
       defineType(ci,3,2,6,11),
       createCuttingDataModel(ci),
       ci)]

// ********************************************************************
// *    Part 2: building the model                                    *
// ********************************************************************

[createCuttingDataModel(ci:CuttingInstance) : void
 -> let cpt := 1 in
       for tp in ci.pieceTypes
          for iter in (1 .. (tp.maxNb - tp.minNb))
             (ci.pieceList :add Piece(ofType = tp, id = cpt),
              cpt :+ 1)]

[createCuttingProblem(ci:CuttingInstance) : choco/Problem
 -> let pb := choco/makeProblem("cutting ", size(TypePiece) + size(Piece) * 5 + 1),
        obj := choco/makeBoundIntVar(pb, "cost", 0, ci.originalShape.length * ci.originalShape.width) in
     (ci.producedArea := obj,
      ci.problem := pb,
      pb)]

// create/fill variables
[createCuttingVars(ci:CuttingInstance) : void
  -> //[CUTTALK] create variables //,
     let pb := ci.problem in
       (for p in Piece
         (p.x := choco/makeIntVar(pb, "p" /+ string!(p.id) /+ "x", 0, ci.originalShape.length - p.ofType.length),
          p.y := choco/makeIntVar(pb, "p" /+ string!(p.id) /+ "y", 0, ci.originalShape.width - p.ofType.width),
          p.used := choco/makeBoundIntVar(pb, "p" /+ string!(p.id) /+ "used", 0, 1)),
        for tp in TypePiece
         tp.nbPlaced := choco/makeBoundIntVar(pb,"nb" /+ string!(tp.length) /+ "-" /+ string!(tp.width),tp.minNb,tp.maxNb))]

TRUE :: 1
FALSE :: 0

[createCuttingConstraints(ci:CuttingInstance, k1:integer,k2:integer) : void
 -> let pb := ci.problem, area := ci.producedArea in
     (//[CUTTALK] post constraints for no overlapping //,
      for pi in ci.pieceList
        (choco/post(pb, choco/ifThen((pi.used == FALSE),((pi.x == 0) and (pi.y == 0)))),
         for pj in {p in ci.pieceList | p.id > pi.id}
           choco/post(pb, ((pi.used == FALSE) or (pj.used == FALSE) or (pi.x !== pj.x) or (pi.y !== pj.y)))),
      // contraintes de guillotine
      for pi in ci.pieceList
        for pj in (ci.pieceList but pi)
           choco/post(pb, ((pi.used == FALSE) or (pj.used == FALSE)) or
                                (  ((pi.x >= pj.x) or (pi.x + pi.ofType.length <= pj.x)) and
                                   ((pi.y >= pj.y) or (pi.y + pi.ofType.width <= pj.y)) and
                                   ((pi.x >= pj.x + pj.ofType.length) or (pi.x + pi.ofType.length <= pj.x + pj.ofType.length)) and
                                   ((pi.y >= pj.y + pj.ofType.width) or (pi.y + pi.ofType.width <= pj.y + pj.ofType.width)))),

      // contrainte de dominance : ordre lexicographique spatial sur les pieces equivalentes
      for tp in ci.pieceList
        let similarPieces := list{p in ci.pieceList | p.ofType = tp}, n := length(similarPieces) in
           (for i in (1 .. n - 1)
                choco/post(pb, similarPieces[i].used >= similarPieces[i + 1].used),           
            for pi in similarPieces
               for pj in list{p in similarPieces | p.id > pi.id}
                 (choco/post(pb, choco/ifThen((pi.used == TRUE) and (pj.used == TRUE),pi.x <= pj.x)),
                  choco/post(pb, choco/ifThen((pi.used == TRUE) and (pj.used == TRUE),
                                    (pi.x + pi.ofType.length <= pj.x) or ((pi.x == pj.x) and (pi.y + pi.ofType.width <= pj.y)) ))  )),

     // contrainte objective
     for tp in ci.pieceTypes
        choco/post(pb, choco/occur(TRUE,list{p.used | p in tp.pieces}) == tp.nbPlaced),

     choco/post(pb, (list{tp.cost | tp in TypePiece} scalar list{tp.nbPlaced | tp in TypePiece}) == area),

     choco/setMin(area, k1),
     choco/setMax(area, k2) )]

// ********************************************************************
// *    Part 3: search algorithms                                     *
// ********************************************************************

[createCuttingVarList() : list<choco/IntVar>
 -> let l := list<choco/IntVar>() in
     (for p in Piece 
         (l :add p.used, l :add p.x, l :add p.y),
      //[0] static order: ~S // l,
      l)]

[go(ci:CuttingInstance, k1:integer, k2:integer) : void
  -> let pb := createCuttingProblem(ci) in
       (createCuttingVars(ci),
        createCuttingConstraints(ci,k1,k2),
        //[CUTTALK] solve pb //,
        let staticVarList := createCuttingVarList(),
            staticStrategy := choco/makeAssignVarBranching(
                      choco/makeStaticVarHeuristic(staticVarList)),
            algo := choco/makeGlobalSearchMaximizer(ci.producedArea,false,list(staticStrategy)) in
          (SVIEW := 0, STALK := 0,
           choco/setMaxPrintDepth(algo,7),
           choco/setMaxSolutionStorage(algo,1),
           choco/attach(algo,pb),
           choco/run(algo),
           if (system.verbose >= 2) displayCuttingSolution(ci),
           choco/discardProblem(pb)  // v1.010
           ))]

// main function 
[cutting(i:integer)
 -> trace(CHOCOBENCH_VIEW,"cutting shapes [~S] : ",i),
    time_set(),
    let ci := cuttingData(i) in go(ci,1,66),
    let t := time_get() in trace(CHOCOBENCH_VIEW,"~S ms.\n",t)]

