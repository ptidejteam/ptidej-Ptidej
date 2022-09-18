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

// **************************************************************************************
// * The new repair algorithm, a generalization of the algorithm provided in PaLM v1.07 *
// **************************************************************************************

[repair(pb:PtidejProblem): void
	->	repair(pb.palmSolver)
]

[repair(algo:PtidejSolver) : void
    ->  // [0] repair(PtidejSolver),
        let pb       := algo.problem,
            pe       := pb.propagationEngine,
            state    := algo.state, 
            learner  := algo.learning,
            repairer := algo.repairing
        in (
			printStatus(),
            // [0] algo.finished = ~S // algo.finished,
            // [0] pe.contradictory = ~S // pe.contradictory,
            if (pe.contradictory) (
                let
                    e  := Explanation(),    
                    fv := pe.contradictionCause
                in (
                    self_explain(fv, DOM, e),
                    assert(valid?(e)),
                    pe.contradictory := false,
                    learnFromContradiction(learner, e),
                    // [0] e = ~S // e,
                    if (empty?(e)) (
                        raiseSystemContradiction(pe)
                    )
                    else (
                        when
                        	re := selectDecisionToUndo(repairer, e)
                        in (
                            // [0] re = ~S // re, 
                            // First, I deal with the removal of a constraint.
                            let hasAnyConstraintBeenRemoved:boolean := false in (
                                for ct:AbstractConstraint in re[1] (
	                            	printStatus(),
                                    // [0] Willing to remove: ~S // ct,
                                    // [0] weight(ct) < pb.maxRelaxLvl = ~S < ~S = ~S // weight(ct), pb.maxRelaxLvl, weight(ct) < pb.maxRelaxLvl,
                                    if (weight(ct) < pb.maxRelaxLvl) (
                                    	// the constraint is relaxable
                                        incRuntimeStatistic(algo, RLX, 1),
                                        if (weight(ct) > 0) (
                                            // Not an enumeration constraint.
                                            // [0] Removing constraint ~S (w:~S) // ct, weight(ct),
                                            nil
                                        ) 
                                        else (
                                            // I need to maintain the current search state .
                                            removeDecision(state, ct)
                                        ),
                                        // [0] Removing: ~S // ct,
                                        remove(pb, ct),
                                        remove(repairer, ct),
                                        hasAnyConstraintBeenRemoved := true
                                    )
                                ),
                                // Yann 01/11/06: Naren says!
                                // If no constraint has been removed, this means
                                // that there is no solution to the problem:
                                // Whatever we do, we will not get a solution.
                                // Then, I need to raise a system contradiction
                                // to stop any further computations.
                                // [0] hasAnyConstraintBeenRemoved = ~S // hasAnyConstraintBeenRemoved,
                                if (not(hasAnyConstraintBeenRemoved)) (
                                    raiseSystemContradiction(pe)
                                )
                            ),
						    try (
						    	// [0] Propagate,
						    	// Yann 2003/03/12: Over-propagated!
						    	// We need to propagate now in case the
						    	// problem is still over-constrained
						    	// despite the removed contraints.
						    	printStatus(),
						        propagate(pb)
						    )
						    catch PalmContradiction (
						        repair(algo)
						    ),
                            for ct:AbstractConstraint in re[1] (
                                // [0] Willing to negate: ~S // ct,
                                let temporaryExplanation:Explanation := e in (
                                    if (weight(ct) = 0) (
                                        // An enumeration constraint.
                                        // [0] Trying to negate the constraint: ~S // ct,
                                        temporaryExplanation := copy(e),
                                        temporaryExplanation :delete ct,
    
                                        when negct := negate(ct) in (
                                            if valid?(temporaryExplanation) (
                                                clean(temporaryExplanation),
                                                // [0] Posting: ~S // negct, 
                                                post(pb, negct, temporaryExplanation)
                                            )
                                        )
                                    )
                                )
                            ),
					    	// Yann 2003/03/12: Over-propagated!
					    	// We can merge the following two propagation
					    	// because we only post constraints there
					    	// (decision constraints and previously-removed
					    	// constraints).
							// 	try (
							//		printStatus(),
							//		propagate(pb)
							// 	)
							// 	catch PalmContradiction (
							// 		repair(algo)
							// 	),
                            // Second, I deal with the addition of constraints.
                            // [0] Posting back relaxed constraints: ~S // re[2],
                            for ct in re[2] (
                                // [0] Putting back: ~S of weight: ~S // ct, weight(ct),
                                post(pb, ct, weight(ct))
                            ),
                            try (
                            	printStatus(),
                                propagate(pb)
                            )
                            catch PalmContradiction (
                                repair(algo)
                            )
                        )
                        else (
                            raiseSystemContradiction(pe)
                        )
                    )
                )
            )
        )
]
