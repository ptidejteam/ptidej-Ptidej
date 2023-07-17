// Implémentation d'un système d'explication en claire/choco
// (c) 2000 - Vincent Barichard - EMN 
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM (ici: gestion des domaines en extension)

// ** Summary : Propagating extensive constraints using AC4
// *   Part 1 : Constraint declaration                         
// *   Part 2 : API                                            
// *   Part 3 : Propagation                                    
// *   Part 4 : Data structure management                      


// *************************************************************
// *   Part 1 : Constraint declaration                         *
// *************************************************************

PalmAC4BinConstraint <: PalmBinIntConstraint(
	cachedTuples: boolean = true,
	matrix: BoolMatrix2D,
	feasTest: method[range = boolean],

	supportsOnV1: list<set<integer>>,
	nbSupportsOnV1: list<integer>,

	supportsOnV2: list<set<integer>>,
	nbSupportsOnV2: list<integer>
)

// *************************************************************
// *   Part 2 : API                                            *
// *************************************************************

// pretty printing 
[self_print(c:PalmAC4BinConstraint)
 -> printf("<AC4>"),
    if not(c.cachedTuples) printf("<~S>",c.feasTest.selector),
    printf("(~A,~A)",c.v1.name,c.v2.name)]

// constraint creation
[makeAC4constraint(u:PalmIntVar, v:PalmIntVar, feasible:boolean,
                   mytuples:list<list<integer>>) : PalmAC4BinConstraint
 -> let c := (makePalmBinIntConstraint(PalmAC4BinConstraint,u,v,0) as PalmAC4BinConstraint),
        sizeV1 := (u.bucket as PalmIntDomain).bucketSize,
        sizeV2 := (v.bucket as PalmIntDomain).bucketSize,
		offV1 := (u.bucket as PalmIntDomain).offset,
		offV2 := (v.bucket as PalmIntDomain).offset
	in (
		c.matrix := make2DBoolMatrix(1, sizeV1,1, sizeV2, not(feasible), false),
		c.nbSupportsOnV1 := make_list(sizeV1,integer,0),
		c.nbSupportsOnV2 := make_list(sizeV2,integer,0),
		c.supportsOnV1 := list<set<integer>>{set<integer>() | i in (1 .. sizeV1)},
		c.supportsOnV2 := list<set<integer>>{set<integer>() | i in (1 .. sizeV2)},
		for i in (1 .. sizeV1) c.supportsOnV1[i] := set<integer>(),
		for i in (1 .. sizeV2) c.supportsOnV2[i] := set<integer>(),
        if (not(feasible)) (
			for i in (1 .. sizeV1)
				for j in (1 .. sizeV2) (
					c.supportsOnV1[i] :add j,
					c.nbSupportsOnV1[i] :+ 1,
					c.supportsOnV2[j] :add i,
					c.nbSupportsOnV2[j] :+ 1
				)
		),
		for t in mytuples
			let i := t[1] - offV1, 
				j := t[2] - offV2
			in (
				if (i < 1 | i > c.v1.bucket.bucketSize | j < 1 | j > c.v2.bucket.bucketSize)
					error("**** PaLM error : Can't add tuple (~S, ~S), the values are not in the domain\n", t[1], t[2])
				else (
					store(c.matrix, i,j, feasible),
					if (feasible) (
						c.supportsOnV1[i] :add j,
						c.nbSupportsOnV1[i] :+ 1,
						c.supportsOnV2[j] :add i,
						c.nbSupportsOnV2[j] :+ 1
					)
					else (
						c.supportsOnV1[i] :delete j,
						c.nbSupportsOnV1[i] :- 1,
						c.supportsOnV2[j] :delete i,
						c.nbSupportsOnV2[j] :- 1
					)
				)
			),
		c
	)]


// *************************************************************
// *   Part 3 : Propagation                                    *
// *************************************************************

[testIfSatisfied(c: PalmAC4BinConstraint) : boolean
 -> if (isInstantiated(c.v1) & isInstantiated(c.v2)) (
		if c.cachedTuples read(c.matrix, c.v1.value, c.v2.value)
		else (funcall(c.feasTest,c.v1.value,c.v2.value) as boolean)
	) 
	else ( 
		false 
	)]

[awake(c: PalmAC4BinConstraint) : void
 -> // [0] awake(c: PalmAC4BinConstraint) called,
 	let o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
    in (
		for v in (1 .. c.v1.bucket.bucketSize) (
			c.nbSupportsOnV1[v] := 0,
			for x in c.supportsOnV1[v] (
				if containsValInDomain(c.v2.bucket, x + o2) 
					c.nbSupportsOnV1[v] :+ 1
			)
		),
		for v in (1 .. c.v2.bucket.bucketSize) (
			c.nbSupportsOnV2[v] := 0,
			for x in c.supportsOnV2[v] (
				if containsValInDomain(c.v1.bucket, x + o1) 
					c.nbSupportsOnV2[v] :+ 1
			)
		),
		let expl := Explanation()
		in (
			for x in domainSequence(c.v1.bucket) (
				if (c.nbSupportsOnV1[x - o1] = 0) (
					expl := Explanation(),
					self_explain(c, expl),
					for y in c.supportsOnV1[x  - o1] 
						self_explain(c.v2, VAL, y + o2, expl),
					removeVal(c.v1, x, c.idx1, expl)
				)
			),
			for x in domainSequence(c.v2.bucket)  (
				if (c.nbSupportsOnV2[x - o2] = 0) (
					expl := Explanation(),
					self_explain(c, expl),
					for y in c.supportsOnV2[x  - o2]  (
						self_explain(c.v1, VAL, y + o1, expl)
					),

					removeVal(c.v2, x, c.idx2, expl)
				)
			)		
		)
	),
	// [0] awake(c: PalmAC4BinConstraint) done,
	nil
]

[propagate(c: PalmAC4BinConstraint) : void
 -> let expl := Explanation(),
        o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
	in (
		for x in domainSequence(c.v1.bucket) (
			if (c.nbSupportsOnV1[x - o1] = 0) (
				expl := Explanation(),
				self_explain(c, expl),
				for y in c.supportsOnV1[x - o1] 
					self_explain(c.v2, VAL, y + o2, expl),
				removeVal(c.v1, x, c.idx1, expl)
			)
		),
		for x in domainSequence(c.v2.bucket) (
			if (c.nbSupportsOnV2[x - o2] = 0) (
				expl := Explanation(),
				self_explain(c, expl),
				for y in c.supportsOnV2[x - o2] 
					self_explain(c.v1, VAL, y + o1, expl),
				removeVal(c.v2, x, c.idx2, expl)
			)
        )
	)]


[awakeOnRem(c: PalmAC4BinConstraint, idx: integer, v: integer) : void
 -> let expl := Explanation(), 
        o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
	in (
		if (idx = 1) (
			for x in c.supportsOnV1[v - o1] (
  				if (containsValInDomain(c.v2.bucket, x + o2) & c.nbSupportsOnV2[x] = 0) (
					expl := Explanation(),
					self_explain(c, expl),
					for y in c.supportsOnV2[x] 
						self_explain(c.v1, VAL, y + o1, expl),
					removeVal(c.v2, x + o2, c.idx2, expl)
				)

			)
		)
		else (
			for x in c.supportsOnV2[v - o2] (
				if (containsValInDomain(c.v1.bucket, x + o1) & c.nbSupportsOnV1[x] = 0) (
					expl := Explanation(),
					self_explain(c, expl),
					for y in c.supportsOnV1[x] 
						self_explain(c.v2, VAL, y + o2, expl),
					removeVal(c.v1, x + o1, c.idx1, expl)
				)
			)
        )
	)]



[awakeOnRestoreVal(c: PalmAC4BinConstraint, idx: integer, v: integer) : void
 -> let expl := Explanation(),
        o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
	in (
		if (idx = 1) (
			if (c.nbSupportsOnV1[v - o1] = 0) (
				self_explain(c, expl),	
				for y in c.supportsOnV1[v - o2] 
					self_explain(c.v2, VAL, y + o2, expl),
				removeVal(c.v1, v, c.idx1, expl)
			)
		) 
		else (
			if (c.nbSupportsOnV2[v - o2] = 0) (
				self_explain(c, expl),
				for y in c.supportsOnV2[v - o2] 
					self_explain(c.v1, VAL, y + o2, expl),
				removeVal(c.v2, v, c.idx2, expl)
			)
		)
	)]

// *************************************************************
// *   Part 4 : Data structure management                      *
// *************************************************************

[updateDataStructuresOnConstraint(c: PalmAC4BinConstraint, idx: integer, way: SELECT, v: integer, unused: integer) : void
 -> let o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
	in (
		if (idx = 1)
			for x in c.supportsOnV1[v - o1] 
				c.nbSupportsOnV2[x] :- 1
		else
			for x in c.supportsOnV2[v - o2] 
				c.nbSupportsOnV1[x] :- 1
	)]


[updateDataStructuresOnRestoreConstraint(c: PalmAC4BinConstraint, idx: integer, way: SELECT, v: integer, unused: integer) : void
 -> let o1 := c.v1.bucket.offset,
        o2 := c.v2.bucket.offset
    in (
		if (idx = 1)
			for x in c.supportsOnV1[v - o1] 
				c.nbSupportsOnV2[x] :+ 1
		else
			for x in c.supportsOnV2[v - o2] 
				c.nbSupportsOnV1[x] :+ 1
	)]


; needed for compilation
[awakeOnInf(c:PalmAC4BinConstraint, i:integer) : void -> nil] 
[awakeOnSup(c:PalmAC4BinConstraint, i:integer) : void -> nil]
[awakeOnInst(c: PalmAC4BinConstraint, idx: integer): void -> nil ]
[askIfEntailed(c: PalmAC4BinConstraint): (boolean U {unknown}) -> unknown]
[testIfCompletelyInstantiated(c: PalmAC4BinConstraint): boolean -> false]




[checkPalm(ct: PalmAC4BinConstraint) : boolean -> true]

// registering the constraint within choco mechanims
// claire3 port register no longer used
;;(#if (compiler.active? & compiler.loading?) register(PalmAC4BinConstraint) )
