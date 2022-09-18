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

[customProblemForCompositionTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("CompositionTest Pattern Problem", length(listOfEntities), 90),
            aggregate := makePtidejVar(pb, "Aggregate", 1, length(listOfEntities), true),
            aggregated := makePtidejVar(pb, "Aggregated", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeCompositionConstraint(
                    "Aggregate <>--> Aggregated",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    aggregate,
                    aggregated),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> Aggregated",
                    "throw new RuntimeException(\"Aggregate <> Aggregated\");",
                    aggregate,
                    aggregated),
                 100),
            pb
        )
]

[ac4ProblemForCompositionTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("CompositionTest Pattern Problem", length(listOfEntities), 90),
            aggregate := makePtidejVar(pb, "Aggregate", 1, length(listOfEntities), true),
            aggregated := makePtidejVar(pb, "Aggregated", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeCompositionAC4Constraint(
                    "Aggregate <>--> Aggregated",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    aggregate,
                    aggregated),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> Aggregated",
                    "throw new RuntimeException(\"Aggregate <> Aggregated\");",
                    aggregate,
                    aggregated),
                 100),
            pb
        )
]
