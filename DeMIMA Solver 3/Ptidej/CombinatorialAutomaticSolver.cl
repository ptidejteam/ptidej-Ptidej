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

// ***************************
// * Part 1: BitString class *
// ***************************

BitString <: ephemeral_object(
    numberOfBits:integer,
    value:integer)

[makeBitString(n:integer) : BitString
    ->  BitString(numberOfBits = n, value = 0)
]
[self_print(bitString:BitString) : void
    ->  printf("~S (~A)\n", getString(bitString), bitString.value)
]
[getString(bitString:BitString) : string
    // In Java:
    //    final boolean[] bits = this.decompose(this.value);
    //    final StringBuffer buffer = new StringBuffer(this.numberOfBits);
    //
    //    for (int i = 0; i < this.numberOfBits; i++) {
    //        buffer.append(((bits[i] == true) ? '1' : '0'));
    //    }
    //
    //    return buffer.toString();
    ->  let bits:boolean[] := decompose(bitString),
            text:string := ""
        in (            
            for i in (1 .. bitString.numberOfBits) (
                if (bits[i] = true) (
                    text := text /+ "1"
                )
                else (
                    text := text /+ "0"
                )
            ),
            // [0] ~S (~A) // text, compose(bits),
            text
        )
]
[compose(bits:boolean[]) : integer
    // In Java:
    //    int value = 0;
    //
    //    for (int i = 0; i < this.numberOfBits; i++) {
    //        value
    //            += ((bits[i] == true) ? Math.pow(2, this.numberOfBits - 1 - i) : 0); 
    //    }
    //
    //    return value;
    ->  let value:integer := 0 in (
            for i in (1 .. length(bits)) (
                if (bits[i] = true) (
                    value := value + 2 ^ (length(bits) - i)
                )
            ),
            value
        )
]
[decompose(bitString:BitString) : boolean[]
    // In Java:
    //    final boolean bits[] = new boolean[this.numberOfBits];
    //    int i = this.numberOfBits;
    //
    //    while (value > 0 && i >= 0) {
    //        i--;
    //        if (value % 2 > 0) {
    //            bits[i] = true;
    //        }
    //        else {
    //            bits[i] = false;
    //        }
    //        value = value / 2;
    //    }
    //
    //    return bits;            
    ->  let i:integer      := bitString.numberOfBits,
            value:integer  := bitString.value,
            bits:boolean[] := make_array(bitString.numberOfBits, boolean, false)
        in (
            while (value > 0 & i > 0) (
                if (mod(value, 2) > 0) (
                    bits[i] := true
                )
                else (
                    bits[i] := false
                ),
                i := i - 1,
                value := value / 2
            ),
            bits
        )
]
[inc(bitString:BitString) : void
    ->  bitString.value := bitString.value + 1
]



// *******************************************
// * Part 2: Computation of the combinations *
// *******************************************

[factorial(n:integer) : integer
	// In Java:
    //    int factorial = 1;
    //    for (int i = 1; i <= n; i++) {
    //        factorial *= i;
    //    }
    //    return factorial;
    ->  let factorial:integer := 1 in (
            for i in (1 .. n) (
                factorial := factorial * i
            ),
            factorial
        )
]
[getNumberOfOnes(bitString:BitString) : integer
	// In Java:
    //    int result = 0;
    //    for (int i = 0; i < bitString.length(); i++) {
    //        if (bitString.charAt(i) == '1') {
    //            result++;
    //        }
    //    }
    //    return result;
    ->  let result:integer := 0,
            text:string := getString(bitString)
        in (
            for i in (1 .. length(text)) (
                if (nth(text, i) = '1') (
                    result := result + 1
                )
            ),
            // [0] getNumberOfOnes(~S) = ~A // text, result,
            result
        )
]
[getIndexesOfOnes(bitString:BitString) : list[integer]
	// In Java:
    //    final Vector solution = new Vector();
    //    for (int i = 0; i < bitString.length(); i++) {
    //        if (bitString.charAt(i) == '1') {
    //            solution.add(new Integer(i + 1));
    //        }
    //    }
    //    return solution;
    ->  let solutions:list<integer> := list<integer>(),
            text:string := getString(bitString)
        in (
            for i in (1 .. length(text)) (
                if (nth(text, i) = '1') (
                    add(solutions, i)
                )
            ),
            solutions
        )
]
[C(n:integer, p:integer) : list<list<integer>>
    //    /*
    //     * The number of combinations of p elements within n is equal to:
    //     *        p     n!
    //     *      C  = --------
    //     *        n  p!(n-p)!
    //     */
    //    final int numberOfPermutations = 
    //        factorial(n) / (factorial(p) * factorial(n - p)); 
    //
    //    /*
    //     * (From Canada's SchoolNet website:
    //     *  www.schoolnet.ca/vp-pv/ECOS/index.html)
    //     * The computation of the combination of p elements within n
    //     * is easily done using bitstring.
    //     *
    //     * The idea is that for two elements within four, the results
    //     * are (in bit strings and sub-sets):
    //     *
    //     *     +------+--------+
    //     *     | 0011 | {1, 2} |
    //     *     | 0101 | {1, 3} |
    //     *     | 0110 | {2, 3} |
    //     *     | 1001 | {1, 4} |
    //     *     | 1010 | {2, 4} |
    //     *     | 1100 | {3, 4} |
    //     *     +------+--------+
    //     */
    //
    //    final BitString bitString = new BitString(n);
    //    final Vector solutions = new Vector(numberOfPermutations);
    //
    //    /*
    //     * I compute the solutions by counting the number of
    //     * ones in the bit string: If the number of ones is equal
    //     * to p, then the current value of the bit string represents
    //     * a solution. I obtain a solution from the bit string:
    //     * The indexes of the ones compose the solution.
    //     */
    //    while (solutions.size() != numberOfPermutations) {
    //        if (this.getNumberOfOnes(bitString.getString()) == p) {
    //            solutions.add(0, this.getIndexesOfOnes(bitString.getString()));
    //        }
    //        bitString.inc();
    //    }
    //
    //    return solutions;
    ->  let
    		numberOfPermutations:integer := factorial(n) / (factorial(p) * factorial(n - p)),
            bitString:BitString := makeBitString(n),
            solutions:list<list<integer>> := list<list<integer>>()
        in (
            while (length(solutions) < numberOfPermutations) (
                if (getNumberOfOnes(bitString) = p) (
                    add(solutions, getIndexesOfOnes(bitString))
                ),
                inc(bitString)
            ),
            // [0] C(~A, ~A) -> ~A // n, p, solutions,
            solutions
        )
]



// ********************************************************
// * Part 3: The solve method performing the computations *
// ********************************************************

PtidejCombinatorialAutomaticRepair <: PtidejRepair(
	problem:PtidejProblem
)

[selectDecisionToUndo(repairer:PtidejCombinatorialAutomaticRepair, e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  // [0] selectDecisionToUndo(PtidejCombinatorialAutomaticRepair, Explanation),
    	let
    		ct:AbstractConstraint := min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint,
            re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>())
        in (
			// Yann 2002/08/14: Linked list of constraints.
			// If I remove a constraint, I post its less
			// constraining version (if any).

			// [0] ct = ~S // ct,
			// Yann 2002/10/04: Dumb!
			// I cannot write the following debug,
			// command because precisely, the field
			// nextContraint may not be defined.
			//		[0] ct.nextConstraint = ~S // ct.nextConstraint,

			// Yann 2002/11/27: Forgetful?
			if (get(nextConstraint, ct) != unknown
				& get(nextConstraint, ct) != nil
				& ct.nextConstraint.isa = method) (

				// Yann 2002/10/12: Weight!
				// I am using a general repair algorithm.
				// This algorithm checks that the constraint
				// being removed can actually be removed
				// (i.e., its weight must be < to the maximum
				// level of relaxation). I am dealing myself
				// with the constraints, thus I must by bypass
				// this consistency check.
				ct.hook.weight := 1,
				
                // [0] Removing: ~S (~S) // ct, getSymbol(ct.thisConstraint),
                add(re[1], ct),

				let
					met:method := ct.nextConstraint as method,
                    message:string := getMessage(getProblem(repairer).palmSolver.extending.branching.nextBranching),
                    percentage:integer := getPercentage(getProblem(repairer).palmSolver.extending.branching.nextBranching),
                    xCommand := "",
		        	args:list<any> := list<any>(),
		        	nnm:string := ct.name as string,
		        	nct:AbstractConstraint := ct
				in (
		        	add(args, nnm),
		        	add(args, ct.command),
					for i in (1 .. getNbVars(ct)) (
		        		add(args, getVar(ct, i))
					),
			        nct := apply(met, args) as AbstractConstraint,

			        // Yann 2002/08/24: Negate!
			        // I make sure that the constraint weight
			        // never equals 0 as a primitive constraint.
			        // [0] nct = ~S // nct,
                    percentage := /(percentage, 2),
                    if (percentage = 0) (
                    	percentage := 1
                    ),
			        nct.hook.weight := percentage,

					message := message
								/+ "# Solution without constraint:\n#\t"
                    			/+ getSymbol(ct.thisConstraint)
                    			/+ "\t: "
								/+ ct.name
								/+ "\n# Solution with constraint:\n#\t"
                    			/+ getSymbol(nct.thisConstraint)
                    			/+ "\t: "
                    			/+ nnm
                    			/+ "\n",
		            setMessage(getProblem(repairer).palmSolver.extending.branching.nextBranching, message),
		            setPercentage(getProblem(repairer).palmSolver.extending.branching.nextBranching, percentage),

					printf(
						"~A \n~A Trying weaker constraint:\n~A\tSolution without constraint:\n~A\t\t~S\t: ~S\n~A\tSolution with constraint:\n~A\t\t~S\t: ~S\t\t",
						char!(8),
						char!(179),
						char!(179),
						char!(179),
						getSymbol(ct.thisConstraint),
						ct.name,
						char!(179),
						char!(179),
						getSymbol(nct.thisConstraint),
						nnm
					),

                	// [0] Posting:  ~S (~S) // nct, getSymbol(nct.thisConstraint),
                	add(re[2], nct)
            	)
            )
            else (
				// If the constraint is of weight 0, I just relax it.
				if (weight(ct) = 0) (
					add(re[1], ct)
				)
            ),

			// Yann 2002/12/04: Waiting...
			// I am tired of waiting after the solver to
			// solve the problem without knowing what's
			// happening, so I add some display...
			printStatus(),

            // Halt condition.
            if (length(re[1]) = 0 & length(re[2]) = 0) (
				raiseSystemContradiction(repairer.problem.propagationEngine)
            ),

			// [0] re = ~S\n // re,
            re
        )
]

[initCombinatorialAutomaticSolver(currentProblem:PtidejProblem) : void
    ->  let
    		solver   := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0)),
    		repairer := PtidejCombinatorialAutomaticRepair(problem = currentProblem)
        in (
            currentProblem.palmSolver := solver,
            solver.problem := currentProblem,
			setProblem(repairer, currentProblem),

            attachPalmState(currentProblem, PalmState(path = Explanation())),
            attachPalmExtend(currentProblem, PalmExtend()),
            attachPalmLearn(currentProblem, PalmLearn()),
            attachPalmRepair(currentProblem, repairer),
            attachPalmBranchings(currentProblem, list(PtidejAssignVar(), PtidejAutomaticBranching()))
        )
]

[combinatorialAutomaticSolve(currentProblem:PtidejProblem) : void
    ->  // Yann 2002/10/12: ASCII Art?
    	// I improved the display in the console...
		printf("\n~A Computing solutions (combinatorial automatic)\n", char!(179)),
		printFooter(),

		// verbose() := 2,
        // [0] Solve initial problem...\n------------------------,
        // initCombinatorialAutomaticSolver(currentProblem),
		// time_set(),
        // solve(currentProblem, true),
        // [0] Solve terminated in ~S ms. // time_get(),

        let
            numberOfRemovableConstraints:integer := 0,
            removableConstraints:list<AbstractConstraint> := list<AbstractConstraint>(),
            nonRemovableConstraints:list<AbstractConstraint> := list<AbstractConstraint>(),
            solutionNumber:integer := 0
        in (
            // It is important that I sort the constraint now, because I will use
            // the first removable constraint (with weight between 0 and 90) as
            // reference for the computation of the total weight of the solution.
            for constraint in currentProblem.constraints (
                if (weight(constraint) < currentProblem.maxRelaxLvl) (
                    // [0] Removable constraint: ~S of weight: ~S (~S) // constraint, weight(constraint), currentProblem.maxRelaxLvl,

					// Yann 2004/05/13: Score!
					// I want to change the computation of the score
					// associated with each solutions. I need the global
					// weight of the original constraints.
					setGlobalWeight(currentProblem, +(getGlobalWeight(currentProblem), weight(constraint))),
                    // [0] getGlobalWeight(currentProblem) = ~S // getGlobalWeight(currentProblem),

                    numberOfRemovableConstraints := numberOfRemovableConstraints + 1,
                    add(removableConstraints, constraint)
                )
                else (
                    add(nonRemovableConstraints, constraint)
                )
            ),

            // I compute all the solutions with the constraints removed according to their permutations.
            for p in (0 .. numberOfRemovableConstraints) (
            	// [0] p = ~S // p,
            	
            	// [0] currentProblem.name = ~S // currentProblem.name,
            	// [0] currentProblem.propagationEngine.maxSize = ~S // currentProblem.propagationEngine.maxSize - 1,
            	// [0] currentProblem.maxRelaxLvl = ~S // currentProblem.maxRelaxLvl,
                let
					currentNumberOfDynamicProblem:integer := 1,
                	setsOfRemovableConstraints:list := C(numberOfRemovableConstraints, p),
                	dynamicProblem:PtidejProblem := currentProblem,
	                message:string := "",
	                percentage:integer := 0,
	                xCommand := ""
                in (
                    // [0] setsOfRemovableConstraints = ~S // setsOfRemovableConstraints,
                    for setOfRemovableConstraints in setsOfRemovableConstraints (
                    	// [0] setOfRemovableConstraints = ~S // setOfRemovableConstraints,

						printHeader(),
						printf(
							"~A Soft constraints: ~S/~S, combination: ~S/~S\n",
							char!(179),
							(p + 1),
							(numberOfRemovableConstraints + 1),
							currentNumberOfDynamicProblem,
							length(setsOfRemovableConstraints)),
						currentNumberOfDynamicProblem := currentNumberOfDynamicProblem + 1,

						// I create (dynamically!) a new problem.
						// I override the maximum level of relaxation: currentProblem.maxRelaxLvl.
						dynamicProblem := makePtidejProblem(
							currentProblem.name,
							currentProblem.propagationEngine.maxSize - 1,
							currentProblem.maxRelaxLvl),
						setGlobalWeight(dynamicProblem, getGlobalWeight(currentProblem)),
						initCombinatorialAutomaticSolver(dynamicProblem),
						setSolutionNumber(dynamicProblem.palmSolver.extending.branching.nextBranching, solutionNumber),

						// I compute and display the appropriate message.
						if (length(setOfRemovableConstraints) = 0) (
				            setMessage(dynamicProblem.palmSolver.extending.branching.nextBranching, "# Solution with all constraints\n"),
				            setPercentage(dynamicProblem.palmSolver.extending.branching.nextBranching, 100),
				            setXCommand(dynamicProblem.palmSolver.extending.branching.nextBranching, "System.out.println(\"No transformation required.\");"),

							printf("~A Solutions with all constraints\t\t", char!(179))
						)
						else (
							message := "# Solution without constraint",
							percentage := 0,
							xCommand := "",
	                        if (length(setOfRemovableConstraints) > 1) (
		                        message := message /+ "s:\n",
								printf("~A Solution without constraints\t\t", char!(179))
		                    )
		                    else (
		                        message := message /+ ":\n",
								printf("~A Solution without constraint:\t\t", char!(179))
		                    ),

	                        for constraint in setOfRemovableConstraints (
	                        	// [0] \t~S // removableConstraints[constraint],
	                            message := message
	                            			/+ "#\t"
	                            			/+ getSymbol(removableConstraints[constraint].thisConstraint)
	                            			/+ "\t: "
	                            			/+ removableConstraints[constraint].name
	                            			/+ "\n",
	                            // percentage := /(*(percentage, weight(removableConstraints[constraint])), 100),
	                            percentage := +(percentage, weight(removableConstraints[constraint])),
	                            xCommand := xCommand /+ removableConstraints[constraint].command,

								printf(
									"~A\n~A\t~S\t: ~S\t\t",
									char!(8),
									char!(179),
									getSymbol(removableConstraints[constraint].thisConstraint),
									removableConstraints[constraint].name
								)
	                        ),
	                        setMessage(dynamicProblem.palmSolver.extending.branching.nextBranching, message),
	                        setPercentage(dynamicProblem.palmSolver.extending.branching.nextBranching, percentage),
	                        setXCommand(dynamicProblem.palmSolver.extending.branching.nextBranching, xCommand)
						),
                        
                        // I dynamically create a new problem with (and only with)
                        // the required constraints.
                        // [0] Variables,
                        for var in currentProblem.vars (
                        	// [0] ~S // var,
                        	// [0] ~S // var.isa,
                        	if (var.isa = PtidejVar) (
                        		// [0] \tPtidejVar //,
								makePtidejVar(
									dynamicProblem,
									var.name,
									var.originalInf,
									var.originalSup,
									var.toBeEnumerated)
							)
							else (
                        		// [0] \tPtidejBoundVar //,
								makePtidejBoundVar(
									dynamicProblem,
									var.name,
									var.originalInf,
									var.originalSup,
									var.toBeEnumerated)
							)
                        ),
                        // [0] Non-removable constraints,
                        for constraint in nonRemovableConstraints (
                        	// [0] \t~S // constraint,
                        	dynamicPost(
                        		dynamicProblem,
                        		constraint)
                        ),
                        // [0] Not-removed removable constraints,
                        for i in (1 .. numberOfRemovableConstraints) (
                        	if (not(i % setOfRemovableConstraints)) (
                        		// [0] \t~S // removableConstraints[i],
                        		dynamicPost(
                        			dynamicProblem,
                        			removableConstraints[i])
	                        )
                        ),

						// Yann 2002/12/04: Show me your vars!
						// I should show only the variable defined
						// by the user in the original problem,
						// not all of them...
						setVarsToShow(dynamicProblem.globalSearchSolver, dynamicProblem.vars),
						time_set(),
						// [0] solve(dynamicProblem, true),
                        solve(dynamicProblem, true),
                        // [0] done!,
						printf(
							"~A \n~A Computed in ~S ms.\n",
							char!(8),
							char!(179),
							time_get()),
						printFooter(),
                        solutionNumber := getSolutionNumber(dynamicProblem.palmSolver.extending.branching.nextBranching)
                    )
                )
            )
        )
]

[dynamicPost(
	problem:PtidejProblem,
	constraint:AbstractConstraint) : void
	->	// [0] \tconstraint = ~S\n\t(~S) (~S) // constraint, weight(constraint), constraint.isa,
		// [0] \tget(thisConstraint, constraint) = ~S // get(thisConstraint, constraint),
		// [0] \tconstraint.thisConstraint.isa = ~S // constraint.thisConstraint.isa,
		if (get(thisConstraint, constraint) != unknown
			& get(thisConstraint, constraint) != nil
			& constraint.thisConstraint.isa = method) (

			let
	        	args:list<any> := list<any>(),
	        	c:AbstractConstraint := constraint
	        in (
	        	add(args, constraint.name),
	        	add(args, constraint.command),
	        	// Yann 2002/11/27: BoundIntVar!
	        	// I must distinguish between PtidejVar
	        	// and PtidejBoundVar because they don't
	        	// have the same underlying representation
	        	// [0] constraint: ~S // constraint,
	        	// [0] getNbVars(constraint) = ~S // getNbVars(constraint),
	        	if (getNbVars(constraint) = 1) (
		        	add(args, findNewVariable(problem, getVar(constraint, 1))),
		        	add(args, getConstant(constraint))
	        	)
	        	else (
		        	for i in (1 .. getNbVars(constraint)) (
		        		add(args, findNewVariable(problem, getVar(constraint, i)))
		        	)
	        	),
		        c := apply(constraint.thisConstraint, args) as AbstractConstraint,
		        // [0] c = ~S (~S) // c, weight(constraint),
	        	post(
	        		problem,
	        		c,
	        		weight(constraint))
		    )
		)
		else (
			error("CombinatorialAutomaticSolver cannot add ~S", constraint)
		)
]

[findNewVariable(
	problem:PtidejProblem,
	variable:IntVar) : IntVar
	->	let
			newVar:IntVar := variable
		in (
			// [0] name = ~S // variable.name,
			for var in problem.vars (
				// [0] \tvar.name = ~S // var.name,
				if (var.name = variable.name) (
					// [0] \tFound.,
					newVar := var
				)
			),
			// [0] Done.,
			if (newVar = variable) (
				error("findNewVariable() in CombinatorialAutomaticSolver failed!")
			),
			newVar
		)
]
