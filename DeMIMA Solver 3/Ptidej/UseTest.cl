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

[customProblemForUseTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("UseTest Pattern Problem", length(listOfEntities), 90),
            caller := makePtidejVar(pb, "Caller", 1, length(listOfEntities), true),
            callee := makePtidejVar(pb, "Callee", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseConstraint(
                    "Caller ----> Callee",
                    "throw new RuntimeException(\"Caller ----> Callee\");",
                    caller,
                    callee),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Caller <> Callee",
                    "throw new RuntimeException(\"Caller <> Callee\");",
                    caller,
                    callee),
                 100),
            pb
        )
]
[ac4ProblemForUseTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("UseTest Pattern Problem", length(listOfEntities), 90),
            caller := makePtidejVar(pb, "Caller", 1, length(listOfEntities), true),
            callee := makePtidejVar(pb, "Callee", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseAC4Constraint(
                    "Caller ----> Callee",
                    "throw new RuntimeException(\"Composite ----> Component\");",
                    caller,
                    callee),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Caller <> Callee",
                    "throw new RuntimeException(\"Caller <> Callee\");",
                    caller,
                    callee),
                 100),
            pb
        )
]
