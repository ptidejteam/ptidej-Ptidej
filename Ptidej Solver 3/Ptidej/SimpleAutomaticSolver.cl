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

SolutionNumber:integer       := 0
MaximumNumberOfSolutions:integer := 10
SolutionMessage:string       := "# Solution with all constraints."
Percentage:integer           := 100
XCommand:string              := "XCommand = System.out.println(\"No source transformation needed.\");"

// ****************************************************************
// * A general branching object printing out all the solutions    *
// * in the file specified in the resource file                   *
// * (entries "CPtidej.resultDirectory" and "CPtidej.resultFile") *
// ****************************************************************

PtidejSaveAllSolutions <: PtidejBranching()

[selectBranchingObject(b:PtidejSaveAllSolutions) : any
    ->  // [0] selectBranchingObject(b:PtidejSaveAllSolutions),
        let pb     := b.extender.manager.problem,
            p:port := fopen(PtidejResultFile, "a")
        in (
            SolutionNumber := SolutionNumber + 1,
            printOutSolution(pb, SolutionMessage, SolutionNumber, Percentage, XCommand),
            MaximumNumberOfSolutions := 10 + length(listOfEntities) * 2,
    
            while (SolutionNumber <= MaximumNumberOfSolutions) (
                let expl := Explanation(),
                    last := last(pb.vars) in (
                        getExplanations(pb.vars, expl),
                        // Yann 01/10/24: Bug corrected thanks to Naren!
                        // I must enumerate on all the values of the
                        // domain of the variable, not just on the
                        // first element, in case of non-enumerated
                        // variables (typically, Leaf in the Composite
                        // pattern).
                        for i in last (
                            post(pb, last !== i, expl)
                        ),
                        propagate(pb)
                )
            ),
            fclose(p),
            unknown
        )
]

// *****************************************
// * Simple version of an automatic solver *
// *****************************************

PtidejSimpleAutomaticRepair <: PtidejRepair()

[selectDecisionToUndo(rep:PtidejSimpleAutomaticRepair, e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  let
            ct:AbstractConstraint := (min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint),
            re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>())
        in (
            if (weight(ct) = 0) (
                add(re[1], ct),
                re
            )
            else  (
                let
                	cts:list<AbstractConstraint> := list<AbstractConstraint>()
                in (
                    for c in e (
                    	cts :add c
                    ),
                    cts := sort(isBetterConstraint @ AbstractConstraint, cts) as list<AbstractConstraint>,
                    let lightestConstraint:integer := length(cts) in (
                        if (weight(cts[lightestConstraint]) < getProblemRelaxationLevel(rep)) (
                            Percentage := 100 - weight(cts[lightestConstraint]),
                            if (length(getXCommand(cts[lightestConstraint])) > 0) (
                                XCommand := "XCommand = " /+ getXCommand(cts[lightestConstraint])
                            )
                            else (
                                XCommand := "XCommand = System.out.println(\"No source transformation needed.\");"
                            ),
                            SolutionMessage := "# More solution without constraint "
                                            /+ string!(lightestConstraint)
                                            /+ ": "
                                            /+ cts[lightestConstraint].name
                                            /+ " of weight "
                                            /+ string!(weight(cts[lightestConstraint])),
                            add(re[1], cts[length(cts)]),
                            re
                        )
                        else (
                            PalmContradiction()
                        )
                    )
                )
            )
        )
]

[initSimpleAutomaticSolver(currentProblem:PtidejProblem) : void
    ->  let
    		solver   := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0)),
    		repairer := PtidejSimpleAutomaticRepair()
        in (
            currentProblem.palmSolver := solver,
            solver.problem := currentProblem,
            setProblem(repairer, currentProblem),

            attachPalmState(currentProblem, PalmState(path = Explanation())),
            attachPalmExtend(currentProblem, PalmExtend()),
            attachPalmLearn(currentProblem, PtidejLearn()),
            attachPalmRepair(currentProblem, repairer),
            attachPalmBranchings(currentProblem, list(PtidejAssignVar(), PtidejSaveAllSolutions()))
        )
]

[simpleAutomaticSolve(currentProblem:PtidejProblem) : void
    ->  printf("\n~A Computing solutions (simple automatic)\n", char!(179)),
		printFooter(),

		printHeader(),
    	initSimpleAutomaticSolver(currentProblem),
        let p: port := fopen(PtidejResultFile, "w") in (
            use_as_output(p),
            printf("[~A]\n", currentProblem.name),
            fclose(p),
            use_as_output(stdout)
        ),
        solve(currentProblem, false),
		printFooter()
]
