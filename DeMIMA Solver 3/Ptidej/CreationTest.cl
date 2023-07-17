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

[customProblemForCreationTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("CreationTest Pattern Problem", length(listOfEntities), 90),
            creator := makePtidejVar(pb, "Creator", 1, length(listOfEntities), true),
            created := makePtidejVar(pb, "Created", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, list<PtidejVar>(creator, created)),

            post(pb,
                 makeCreationConstraint(
                    "Creator -*--> Created",
                    "throw new RuntimeException(\"Creator ----> Created\");",
                    creator,
                    created),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> Created",
                    "throw new RuntimeException(\"Creator <> Created\");",
                    creator,
                    created),
                 100),
            pb
        )
]
[ac4ProblemForCreationTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("CreationTest Pattern Problem", length(listOfEntities), 90),
            creator := makePtidejVar(pb, "Creator", 1, length(listOfEntities), true),
            created := makePtidejVar(pb, "Created", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, list(creator, created)),

            post(pb,
                 makeCreationAC4Constraint(
                    "Creator -*--> Created",
                    "throw new RuntimeException(\"Creator ----> Created\");",
                    creator,
                    created),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> Created",
                    "throw new RuntimeException(\"Creator <> Created\");",
                    creator,
                    created),
                 100),
            pb
        )
]
