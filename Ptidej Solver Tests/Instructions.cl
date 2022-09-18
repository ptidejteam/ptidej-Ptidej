[solvePtidejProblem() : void
	->	PtidejResultFile := "../Ptidej Solver Data/ConstraintResults.ini",
		printHeader(),
		printf("~A Loading domain file	", char!(179)),
		load("Domain.cl"),
		searchConcretePatterns(
			automaticSolve @ PtidejProblem,
			ac4ProblemForStrictInheritanceTestPattern()),
		exit(-1)
]

(solvePtidejProblem())