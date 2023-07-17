//*****************************************************************************
//* Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
//* All rights reserved. This program and the accompanying materials
//* are made available under the terms of the GNU Public License v2.0
//* which accompanies this distribution, and is available at
//* http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
//* 
//* Contributors:
//*     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
//*****************************************************************************

// Yann 2002/08/08: Constructor.
// It seems to me that there is no chaining of constructors in Claire?!
// Thus, I must copy and modify the constructor declaration from entity
// PalmAC4BinConstraint (cf. makeAC4constraint()).
[makePtidejAC4Constraint(
	nm:string,
	co:string,
	u:PtidejVar,
	v:PtidejVar,
	feasible:boolean,
	mytuples:list<tuple(integer,integer)>,
	thisMet:any,
	nextMet:any) : PtidejAC4Constraint
	->	// [0] Replacement method: ~S // repmet,
		let c:PtidejAC4Constraint := makePalmBinIntConstraint(
											PtidejAC4Constraint,
											u,
											v,
											0) as PtidejAC4Constraint,
        	sizeV1 := u.bucket.bucketSize,
        	sizeV2 := v.bucket.bucketSize,
        	offV1  := u.bucket.offset,
        	offV2  := v.bucket.offset
		in (
			c.name           := nm,
			c.command        := co,
	        c.matrix         := make2DBoolMatrix(
									1,
									sizeV1,
									1,
									sizeV2,
									not(feasible),
									false),
	        c.nbSupportsOnV1 := make_list(sizeV1, integer, 0),
	        c.nbSupportsOnV2 := make_list(sizeV2, integer, 0),
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
	        for t in mytuples (
	            let i := t[1] - offV1, 
	                j := t[2] - offV2
	            in (
	                if (i < 1 | i > c.v1.bucket.bucketSize | j < 1 | j > c.v2.bucket.bucketSize)
	                    error("**** Ptidej error: Cannot add tuple (~S, ~S), the values are not in the domain\n", t[1], t[2])
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
	            )
	        ),
	        
			c.thisConstraint := thisMet,
			c.nextConstraint := nextMet,
	        c
		)
]
[self_print(c:PtidejAC4Constraint) : void
	->	printf("~S", c.name, c.v1.name, c.v2.name)
]



// ********************
// * AC-4 constraints *
// ********************

[makeAggregationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeAggregationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeAggregationAC4Constraint))

[makeAssociationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeAssociationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeAssociationAC4Constraint))

[makeAwarenessAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeAwarenessAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeAwarenessAC4Constraint))

[makeCompositionAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeCompositionAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeCompositionAC4Constraint))

[makeContainerAggregationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeContainerAggregationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeContainerAggregationAC4Constraint))

[makeContainerCompositionAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeContainerCompositionAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeContainerCompositionAC4Constraint))

[makeCreationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeCreationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeCreationAC4Constraint))

// [makeEqualAC4Constraint(
// 	n:string,
// 	co:string,
// 	c1:PtidejVar,
// 	c2:PtidejVar) : PtidejAC4Constraint
// 	->	error("Ptidej error: method makeEqualAC4Constraint() must be implemented"),
// 		new(PtidejAC4Constraint)
// ]
// (abstract(makeEqualAC4Constraint))

// [makeNotEqualAC4Constraint(
// 	n:string,
// 	co:string,
// 	c1:PtidejVar,
// 	c2:PtidejVar) : PtidejAC4Constraint
// 	->	error("Ptidej error: method makeNotEqualAC4Constraint() must be implemented"),
// 		new(PtidejAC4Constraint)
// ]
// (abstract(makeNotEqualAC4Constraint))

[makeIgnoranceAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeIgnoranceAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeIgnoranceAC4Constraint))

[makeInheritanceAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeInheritanceAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeInheritanceAC4Constraint))

[makeInheritancePathAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeInheritancePathAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeInheritancePathAC4Constraint))

[makeUseAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeUseAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeUseAC4Constraint))

[makeNoAssociationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeNoAssociationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeNoAssociationAC4Constraint))

[makeNoAggregationAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeNoAggregationAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeNoAggregationAC4Constraint))

[makeNoCompositionAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeNoCompositionAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeNoCompositionAC4Constraint))

[makeStrictInheritanceAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeStrictInheritanceAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeStrictInheritanceAC4Constraint))

[makeStrictInheritancePathAC4Constraint(
	n:string,
	co:string,
	c1:PtidejVar,
	c2:PtidejVar) : PtidejAC4Constraint
	->	error("Ptidej error: method makeStrictInheritancePathAC4Constraint() must be implemented"),
		new(PtidejAC4Constraint)
]
(abstract(makeStrictInheritancePathAC4Constraint))