[solvePtidejProblem() : void
	->	PtidejResultFile := "../Temp/ConstraintResults.ini",
		printHeader(),
		printf("~A Loading domain file	", char!(179)),
		load("../Temp/Domain.cl"),
		searchConcretePatterns(
			automaticSolve @ PtidejProblem,
			ac4ProblemForStrictInheritanceTestPattern()),
		exit(-1)
]

(solvePtidejProblem())