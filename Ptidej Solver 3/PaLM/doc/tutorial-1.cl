// A tutorial file for PaLM : PaLM note "First steps with PaLM" 
// see http://www.e-constraints.net/palm/manual/palm-note-firststeps.html 


// PART 1 : choco vs PaLM

[classical-choco-program()
 -> let pb := choco/makeProblem("demo constraint program", 5), // a problem
        a  := choco/makeBoundIntVar(pb, "a", 1, 3),            // a few variables
        b  := choco/makeBoundIntVar(pb, "b", 1, 3),
        c  := choco/makeBoundIntVar(pb, "c", 1, 3)
    in (
        printf("Variables: ~S\n", list(a,b,c)),  printf("posting a > b \n"),
        choco/post(pb, a > b), choco/propagate(pb),

        printf("Variables: ~S\n", list(a,b,c)),  printf("posting a > c \n"),
        choco/post(pb, a > c), choco/propagate(pb),

        printf("Variables: ~S\n", list(a,b,c)),  printf("posting b > c \n"),
        choco/post(pb, b > c), choco/propagate(pb),

        printf("Variables: ~S\n", list(a,b,c))
    )]


[classical-palm-program()
 -> let pb := makePalmProblem("demo constraint program", 5), // a PaLM problem !!!
        a  := makeBoundIntVar(pb, "a", 1, 3),            // a few variables
        b  := makeBoundIntVar(pb, "b", 1, 3),
        c  := makeBoundIntVar(pb, "c", 1, 3)
    in (
        printf("Variables: ~S\n", list(a,b,c)),  printf("posting a > b \n"),
        post(pb, a > b), propagate(pb),

        printf("Variables: ~S\n", list(a,b,c)),  printf("posting a > c \n"),
        post(pb, a > c), propagate(pb),

        printf("Variables: ~S\n", list(a,b,c)),  printf("posting b > c \n"),
        post(pb, b > c), propagate(pb),

        printf("Variables: ~S\n", list(a,b,c))
    )]




// PART 2 : getting information 

[inconsistent-problem() 
 -> let pb := makePalmProblem("demo explain", 5, 5),
		a  := makeBoundIntVar(pb, "a", 1, 3),
		b  := makeBoundIntVar(pb, "b", 1, 3),
		c  := makeBoundIntVar(pb, "c", 1, 3),
		d  := makeBoundIntVar(pb, "d", 1, 3),
		e  := makeBoundIntVar(pb, "e", 1, 3)
	in (
		printf("\nVariables: ~S\n", list(a,b,c,d,e)),

		post(pb, a > c), propagate(pb),
		post(pb, b > d), propagate(pb),
		post(pb, c > e), propagate(pb),
		post(pb, d > e), propagate(pb),

		printf("Variables: ~S\n", list(a,b,c,d,e)),

		post(pb, e > a),
		propagate(pb)
	)]


// PART 3 : solving over-constrained problems

[over-constrained-problem()
 -> let pb := makePalmProblem("demo over-constrained system", 3, 5),
        a  := makeBoundIntVar(pb, "a", 1, 3),
        b  := makeBoundIntVar(pb, "b", 1, 3),
        c  := makeBoundIntVar(pb, "c", 1, 3),
		ct1 := (a > b),
		ct2 := (b > c),
		ct3 := (c > a)
    in (
        try (
            printf("Variables: ~S\n", list(a,b,c)),

            printf("posting ~S \n", ct1),
            post(pb, ct1, 2), propagate(pb),
            printf("Variables: ~S\n", list(a,b,c)),

            printf("posting ~S \n", ct2),
            post(pb, ct2, 5), propagate(pb),
            printf("Variables: ~S\n", list(a,b,c)),

            printf("posting ~S \n", ct3),
            post(pb, ct3, 3), propagate(pb),
            printf("Variables: ~S\n", list(a,b,c))
        )
        catch PalmContradiction (
            repair(pb),
            printf("Variables: ~S\n", list(a,b,c))
        )
    )]
