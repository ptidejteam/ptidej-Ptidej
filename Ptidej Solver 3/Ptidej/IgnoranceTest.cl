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

[customProblemForIgnoranceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("IgnoranceTest Pattern Problem", length(listOfEntities), 90),
            a := makePtidejVar(pb, "A", 1, length(listOfEntities), true),
            b := makePtidejVar(pb, "B", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeIgnoranceConstraint(
                    "A -/--> B",
                    "throw new RuntimeException(\"A -/--> B\");",
                    a,
                    b),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "A <> B",
                    "throw new RuntimeException(\"A <> B\");",
                    a,
                    b),
                 100),
            pb
        )
]
[ac4ProblemForIgnoranceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("IgnoranceTest Pattern Problem", length(listOfEntities), 90),
            a := makePtidejVar(pb, "A", 1, length(listOfEntities), true),
            b := makePtidejVar(pb, "B", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeIgnoranceAC4Constraint(
                    "A -/--> B",
                    "throw new RuntimeException(\"A -/--> B\");",
                    a,
                    b),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "A <> B",
                    "throw new RuntimeException(\"A <> B\");",
                    a,
                    b),
                 100),
            pb
        )
]
