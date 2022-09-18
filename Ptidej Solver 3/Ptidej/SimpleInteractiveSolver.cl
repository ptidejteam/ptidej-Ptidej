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

// ************************************************
// * Standard interactive solver written by Naren *
// ************************************************

PtidejSimpleInteractiveRepair <: PtidejRepair()

[selectDecisionToUndo(rep:PtidejSimpleInteractiveRepair, e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  // [0] selectDecisionToUndo(SimpleInteractiveRepair, Explanation),
        let
            ct:AbstractConstraint := (min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint),
            re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>())
        in (
            if (weight(ct) = 0) (
                add(re[1], ct),
                re
            )
            else  (
                let cts := nil in (
                    for c in e cts :add c,
                    cts := sort(isBetterConstraint @ AbstractConstraint, cts) as list<AbstractConstraint>,
                    printf("\nThere is no more solution because of the constraints:\n"),
                    for i in (1 .. length(cts)) (
                        printf("   ~S. ~S\n      (weight ~S)\n", i, cts[i], weight(cts[i]))
                    ),
                    printf("Which one do you want to relax? (0 -> none) "),
                    let
                    	selecC := read(stdin) as integer
                    in (
                        if (selecC = 0) (
                            PalmContradiction()
                        ) 
                        else (
                            add(re[1], cts[selecC]),
                            re
                        )
                    )
                )
            )
        )
]

[initSimpleInteractiveSolver(pb:PtidejProblem) : void
    ->  let ps := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0))
        in (
            pb.palmSolver := ps,

            attachPalmState(pb, PalmState(path = Explanation())),
            attachPalmExtend(pb, PalmExtend()),
            attachPalmLearn(pb, PtidejLearn()),
            attachPalmRepair(pb, PtidejSimpleInteractiveRepair()),
            attachPalmBranchings(pb, list(PtidejAssignVar(), PtidejInteractiveBranching())),

            ps.problem := pb
        )
]

[simpleInteractiveSolve(pb:PtidejProblem) : void
    ->  // [0] simpleInteractiveSolve(PtidejProblem),
		printf("\n~A Computing solutions (simple interactive)\n", char!(179)),
		printFooter(),

		printHeader(),
    	initSimpleInteractiveSolver(pb),
        solve(pb, false),
		printFooter()
]
