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

// These tests are inspired from the PaLM tutorial-1.cl file!
// Thanks to Narendra Jussien.

[classicalPtidejProblem() : void
	->	let 
			// A Ptidej problem...
			pb := makePtidejProblem("Demo constraints", 5, 100),
			// A few variables...
	        a  := makePtidejBoundVar(pb, "a", 1, 3),
	        b  := makePtidejBoundVar(pb, "b", 1, 3),
	        c  := makePtidejBoundVar(pb, "c", 1, 3)
		in (
		    printf("Variables: ~S\n", list(a,b,c)),

		    printf("posting a > b \n"),
		    post(pb, a > b), propagate(pb),
		    printf("Variables: ~S\n", list(a,b,c)),

		    printf("posting a > c \n"),
		    post(pb, a > c), propagate(pb),
		    printf("Variables: ~S\n", list(a,b,c)),

		    printf("posting b > c \n"),
		    post(pb, b > c), propagate(pb),
		    printf("Variables: ~S\n", list(a,b,c))
		)
]

[inconsistentPtidejProblem() : void
	->	let pb := makePtidejProblem("Demo explanations", 5, 100),
			a  := makePtidejBoundVar(pb, "a", 1, 3),
			b  := makePtidejBoundVar(pb, "b", 1, 3),
			c  := makePtidejBoundVar(pb, "c", 1, 3),
			d  := makePtidejBoundVar(pb, "d", 1, 3),
			e  := makePtidejBoundVar(pb, "e", 1, 3)
		in (
			printf("\nVariables: ~S\n", list(a,b,c,d,e)),
	
			post(pb, a > c),
			post(pb, b > d),
			post(pb, c > e),
			post(pb, d > e),
	
			propagate(pb),
			printf("Variables: ~S\n", list(a,b,c,d,e)),
	
			post(pb, e > a),
			propagate(pb)
		)
]

[overConstrainedPtidejProblem() : void
	->	let pb := makePtidejProblem("Demo over-constrained problem", 3, 5),
	        a  := makePtidejBoundVar(pb, "a", 1, 3),
	        b  := makePtidejBoundVar(pb, "b", 1, 3),
	        c  := makePtidejBoundVar(pb, "c", 1, 3),
			ct1 := (a > b),
			ct2 := (b > c),
			ct3 := (c > a)
	    in (
	        try (
	            printf("Variables: ~S\n", list(a,b,c)),
	
	            printf("posting ~S \n", ct1),
	            post(pb, ct1, 2),
	            propagate(pb),
	            printf("Variables: ~S\n", list(a,b,c)),
	
	            printf("posting ~S \n", ct2),
	            post(pb, ct2, 5),
	            propagate(pb),
	            printf("Variables: ~S\n", list(a,b,c)),
	
	            printf("posting ~S \n", ct3),
	            post(pb, ct3, 3),
	            propagate(pb),
	            printf("Variables: ~S\n", list(a,b,c))
	        )
	        catch PalmContradiction (
	            repair(pb),
	            printf("Variables: ~S\n", list(a,b,c))
	        )
	    )
]