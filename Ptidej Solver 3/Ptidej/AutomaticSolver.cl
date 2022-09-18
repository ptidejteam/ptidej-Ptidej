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

// *******************************************************
// * New versions of an interactive combinatorial solver *
// *******************************************************

// Yann 2002/08/12
// The PtidejCombinatorialAutomaticRepair now holds the list of all removable
// constraints. This list is used in the selectDecisionToUndo method.

PtidejAutomaticRepair <: PtidejRepair(
	problem:PtidejProblem,
	ptidejBranching:PtidejBranching,
	removableConstraints:list<AbstractConstraint> = list<AbstractConstraint>(),
	areAllConstraintsRelaxed:boolean = false
)

[selectDecisionToUndo(repairer:PtidejAutomaticRepair, e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  // [0] \nselectDecisionToUndo(PtidejAutomaticRepair, Explanation),
        let
            ct:AbstractConstraint := (min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint),
            re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>()),
           	cts:list<AbstractConstraint> := list<AbstractConstraint>(),
            message:string := "# Solution without constraint",
            percentage:integer := 100,
            xCommand := "",
        	removedConstraints:list<AbstractConstraint> := getRemovedConstraints(repairer)
        in (
        	// [0] ct = ~S // ct,
        	// [0] cts = ~S // cts,
        	// [0] e = ~S // e,
            for c in e (
            	// [0] Because of ~S (~S) (~S) // c, weight(c), c.isa,
                add(cts, c)
            ),
            cts := sort(isBetterConstraint @ AbstractConstraint, cts) as list<AbstractConstraint>,

			// If the constraint is of weight 0, I just relax it.
            if (weight(ct) = 0) (
            	// [0] weight(ct) = 0,
                add(re[1], ct)
            )
            else (
            	// [0] repairer.areAllConstraintsRelaxed = ~S // repairer.areAllConstraintsRelaxed,
				if (not(repairer.areAllConstraintsRelaxed)) (
	                // I deal with the removal of a constraint.
                    // [0] There is no more solution because of:\n~S // cts ,
            		if (length(removedConstraints) > 1) (
                        message := message /+ "s:\n",
						printf("\n~A Solution without constraints\t\t\t\t\t\t", char!(179))
                    )
                    else (
                        message := message /+ ":\n",
						printf("\n~A Solution without constraint:\t\t\t\t\t\t", char!(179))
                    ),
                    // I add to the current message the message of the
                    // previously removed constraints.
                    for i in (1 .. length(removedConstraints)) (
                    	if (weight(removedConstraints[i]) > 0) (
							if (get(nextConstraint, ct) != unknown
								& get(nextConstraint, ct) != nil
								& ct.nextConstraint.isa = method) (

	                            message := message 
	                            			/+ "# "
	                            			/+ getSymbol(removedConstraints[i].thisConstraint)
	                            			/+ " : "
	                            			/+ removedConstraints[i].name
	                            			/+ "\n",

								printf(
									"~A\t~S\t: ~S\n",
									char!(179),
									getSymbol(removedConstraints[i].thisConstraint),
									removedConstraints[i].name
								)
							)
							else (
	                            message := message 
	                            			/+ "# "
	                            			/+ removedConstraints[i].name
	                            			/+ "\n",

								printf(
									"~A\t~S\n",
									char!(179),
									removedConstraints[i].name
								)
							),
                            percentage := /(*(percentage, weight(removedConstraints[i])), 100)
                        )
                    ),
                    let
                    	i:integer := length(cts)
                    in (
						// Yann 2002/08/12
				    	// I only relaxed those constraints
				    	// that are user defined
				    	// (weight < getProblemRelaxationLevel(repairer)).
				    	// [0] weight(cts[i]) < getProblemRelaxationLevel(repairer) = ~S // weight(cts[i]) < getProblemRelaxationLevel(repairer),
				    	// [0] ~S < ~S = ~S // weight(cts[i]), getProblemRelaxationLevel(repairer), weight(cts[i]) < getProblemRelaxationLevel(repairer),
				    	if (weight(cts[i]) < getProblemRelaxationLevel(repairer)) (
	                        // [0]   ~S. ~S\n      (weight ~S) // i, cts[i], weight(cts[i])),
	                        add(re[1], cts[i]),

							if (get(nextConstraint, ct) != unknown
								& get(nextConstraint, ct) != nil
								& ct.nextConstraint.isa = method) (

	                            message := message
	                            			/+ "# "
	                            			/+ getSymbol(cts[i].thisConstraint)
	                            			/+ " : "
	                            			/+ cts[i].name
	                            			/+ "\n",

								printf(
									"~A\t~S\t: ~S\n",
									char!(179),
									getSymbol(cts[i].thisConstraint),
									cts[i].name
								)
							)
							else (
	                            message := message
	                            			/+ "# "
	                            			/+ cts[i].name
	                            			/+ "\n",

								printf(
									"~A\t~S\n",
									char!(179),
									cts[i].name
								)
							),
                            percentage := /(*(percentage, weight(cts[i])), 100),
                            xCommand := xCommand /+ cts[i].command
	                    )
	                    else (
							// Yann 2002/08/13
							// I finished relaxing all possible constraints.
							// Now, I put them back one after the other.
			                repairer.areAllConstraintsRelaxed := true
					    )
                    ),
                    setMessage(repairer.ptidejBranching, message),
                    setPercentage(repairer.ptidejBranching, percentage),
                    setXCommand(repairer.ptidejBranching, xCommand)
                )
            ),

        	// [0] repairer.areAllConstraintsRelaxed = ~S // repairer.areAllConstraintsRelaxed,
        	// [0] weight(~S) = ~S // ct.name, weight(ct),
			if (repairer.areAllConstraintsRelaxed) (
                // I deal with the (re)addition of constraints.
				let 
					removedConstraints:list<AbstractConstraint> := getRemovedConstraints(repairer)
				in (
		            // [0] removedConstraints := ~S // removedConstraints,
		            if (length(removedConstraints) > 0) (
		                removedConstraints := sort(isBetterConstraint @ AbstractConstraint, removedConstraints) as list<AbstractConstraint>,
		                // [0] Constraints led to contradictions.,
						// Yann 2002/08/12
				    	// I only add to the user-relaxed constraint list those constraints
				    	// that are defined by the user (weight > 0).
				    	// [0] ~S (~S) // removedConstraints[1], weight(removedConstraints[1]),
				    	if (weight(removedConstraints[1]) > 0) (
		                    add(re[2], removedConstraints[1]),
		                    removedConstraints :delete removedConstraints[1]
		            	)
		            )
		    	)
            ),

			// Yann 2002/12/04: Waiting...
			// I am tried of waiting after the solver to
			// solve the problem without knowing what's
			// happening, so I add some display...
			printStatus(),

            // Halt condition.
            if (length(re[1]) = 0 & length(re[2]) = 0) (
            	// [0] Halt condition met.,
            	raiseSystemContradiction(repairer.problem.propagationEngine)
            ),

            // [0] Re = ~S // re,
            re
        )
]

[initAutomaticSolver(pb:PtidejProblem) : void
    ->  let
    		solver    := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0)),
    		repairer  := PtidejAutomaticRepair(problem = pb)
        in (
            pb.palmSolver := solver,
            solver.problem := pb,

            attachPalmState(pb, PalmState(path = Explanation())),
            attachPalmExtend(pb, PalmExtend()),
            attachPalmLearn(pb, PtidejLearn()),
            attachPalmRepair(pb, repairer),
            attachPalmBranchings(pb, list(PtidejAssignVar(), PtidejAutomaticBranching())),

            repairer.ptidejBranching := pb.palmSolver.extending.branching.nextBranching,
			setProblem(repairer, pb),
			setMessage(repairer.ptidejBranching, "# Solutions with all constraints\n"),
			setPercentage(repairer.ptidejBranching, 100),
			setXCommand(repairer.ptidejBranching, "System.out.println(\"No transformation required.\");"),

			printf("~A Solutions with all constraints\t\t\t\t\t", char!(179)),

            // It is important that I sort the constraint now, because I will use
            // the first removable constraint (with weight between 0 and 90) as
            // reference for the computation of the total weight of the solution.
            let
            	csts:list[AbstractConstraint] := list()
            in (
                csts := sort(isBetterConstraint @ AbstractConstraint, pb.constraints) as list<AbstractConstraint>,
                for c in csts (
                    if (weight(c) < getProblemRelaxationLevel(repairer)) (
	                    // [0] Removable constraint: ~S of weight: ~S // constraint, weight(constraint),
                        repairer.removableConstraints :add c,

						// Yann 2004/05/13: Score!
						// I want to change the computation of the score
						// associated with each solutions. I need the global
						// weight of the original constraints.
						setGlobalWeight(pb, +(getGlobalWeight(pb), weight(c)))
                    )
                )
            )
        )
]

[automaticSolve(pb:PtidejProblem) : void
    ->  // [0] automaticSolve(PtidejProblem),
		// Yann 2002/10/12: ASCII Art?
    	// I improved the display in the console...
		printf("\n~A Computing solutions (automatic)\n", char!(179)),
		printFooter(),

		printHeader(),
        initAutomaticSolver(pb),
		time_set(),
        solve(pb, true),
		printf(
			"\n~A Solve terminated in ~S ms.\n",
			char!(179),
			time_get()),
		printFooter()
]
