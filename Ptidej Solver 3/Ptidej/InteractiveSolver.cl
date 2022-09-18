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

// The idea is to implement a sub-class of PalmSolver, and a
// sub-class of PalmRepair. These sub-classes allow us to
// implement a new repair() algorithm. The new repair() algorithm
// is able to remove and to post constraints according to the
// selectDecisionToUndo() method. We need this new algorithm to
// allow the removal of the CompositionConstraint and the addition
// of the AggregationConstraint, and the removal of the
// AggregationConstraint and the addition of the AssociationConstraint.
// This is a weakening algorithm.

PtidejInteractiveRepair <: PtidejRepair()

[selectDecisionToUndo(repairer:PtidejInteractiveRepair, e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  let
            ct:AbstractConstraint := (min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint),
            re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>()),
           	cts:list<AbstractConstraint> := list<AbstractConstraint>()
        in (
            if (weight(ct) = 0) (
                add(re[1], ct),
                re
            )
            else  (
                // %% D'abord, nous traitons la relaxation du problème%%
                // %% par rétraction de contraintes~:%%
                for c in e (
                    add(cts, c)
                ),
                cts := sort(isBetterConstraint @ AbstractConstraint, cts) as list<AbstractConstraint>,
	            printf("~A \nThere is no more solution because of the constraint", char!(8)),
                if (length(cts) > 1) (
	                printf("s:\n")
	            )
	            else (
	                printf(":\n")
	           	),
                for i in (1 .. length(cts)) (
                    printf("   ~S. ~S ~S\n      (weight ~S)\n", i, getSymbol(cts[i].thisConstraint), cts[i], weight(cts[i])),
                    if (get(nextConstraint, cts[i]) != unknown
						& get(nextConstraint, cts[i]) != nil
						& cts[i].nextConstraint.isa = method) (

						let
							met:method := cts[i].nextConstraint as method,
				        	args:list<any> := list<any>(),
				        	nnm:string := cts[i].name as string,
				        	j:integer := 0,
				        	nct:AbstractConstraint := cts[i]
						in (
				        	add(args, nnm),
				        	add(args, ct.command),
							for j in (1 .. getNbVars(cts[i])) (
				        		add(args, getVar(cts[i], j))
							),
					        nct := apply(met, args) as AbstractConstraint,
		                    printf("      To be replaced with: ~S\n", getSymbol(nct.thisConstraint))
		            	)
					)
                ),
                printf("Which one do you want to relax? (0 -> none) "),
                let
                	selecC := read(stdin) as integer
                in (
                    if (selecC != 0) (
                        add(re[1], cts[selecC]),
	                    if (get(nextConstraint, cts[selecC]) != unknown
							& get(nextConstraint, cts[selecC]) != nil
							& cts[selecC].nextConstraint.isa = method) (
	
							let
								met:method := cts[selecC].nextConstraint as method,
					        	args:list<any> := list<any>(),
					        	nnm:string := cts[selecC].name as string,
					        	nct:AbstractConstraint := cts[selecC]
							in (
					        	add(args, nnm),
					        	add(args, ct.command),
								for i in (1 .. getNbVars(cts[selecC])) (
					        		add(args, getVar(cts[selecC], i))
								),
						        nct := apply(met, args) as AbstractConstraint,
						        nct.hook.weight := weight(cts[selecC]),
			                    add(re[2], nct)
			            	)
						)
                    )
                ),
                // %% Ensuite, nous traitons l'ajout de contraintes%%
                // %% précédement retirées au problème~:%%
                if (length(repairer.userRelaxedConstraints) > 0) (
                    repairer.userRelaxedConstraints := sort(isBetterConstraint @ AbstractConstraint,
                                                            repairer.userRelaxedConstraints) as list<AbstractConstraint>,
                    let selecC := 1 in (
                        while (selecC != 0 & length(repairer.userRelaxedConstraints) > 0) (
                            printf("\nThe following constraint(s) led to a contradiction:\n"),
                            for i in (1 .. length(repairer.userRelaxedConstraints)) (
                                printf("   ~S. ~S\n      (weight ~S)\n",
                                        i,
                                        repairer.userRelaxedConstraints[i],
                                        weight(repairer.userRelaxedConstraints[i]))
                            ),
                            printf("Which one do you want to put back? (0 -> none) "),
                            selecC := read(stdin) as integer,
                            if (selecC != 0) (
                                add(re[2], repairer.userRelaxedConstraints[selecC]),
                                delete(repairer.userRelaxedConstraints, repairer.userRelaxedConstraints[selecC])
                            )
                        )
                    )
                )
            ),
            // %% La recherche s'arrête si aucune contraintes %%
            // %% n'a été retirée ou ajoutée au problème courant~:%%
            if (length(re[1]) = 0 & length(re[2]) = 0) (
				PalmContradiction()
            ),
			// [0] ~S // re,
            re
        )
]

[initInteractiveSolver(currentProblem:PtidejProblem) : void
    ->  let ps := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0))
        in (
            currentProblem.palmSolver := ps,
            ps.problem := currentProblem,

			// Yann 2003/03/22: Weight!
            // I set the maximum value of relaxation to 101,
            // because the developer decides for herself
            // which constraint to relax or to remove.
			currentProblem.maxRelaxLvl := 101,

            attachPalmState(currentProblem, PalmState(path = Explanation())),
            attachPalmExtend(currentProblem, PalmExtend()),
            attachPalmLearn(currentProblem, PtidejLearn()),
            attachPalmRepair(currentProblem, PtidejInteractiveRepair()),
            attachPalmBranchings(currentProblem, list(PtidejAssignVar(), PtidejInteractiveBranching()))
        )
]

[interactiveSolve(pb:PtidejProblem) : void
    ->  // [0] interactiveSolve(PtidejProblem),
        initInteractiveSolver(pb),
        solve(pb, true)
]
