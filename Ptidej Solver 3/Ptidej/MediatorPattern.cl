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

// +----------------------------------------------+
// | Hypothesis:                                  |
// |     There is (at least) one Mediator pattern |
// +----------------------------------------------+

[customProblemForMediatorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb       := makePtidejProblem("Mediator Pattern Problem", length(listOfEntities), 90),
            mediator := makePtidejVar(pb, "Mediator", 1, length(listOfEntities)),
            client1  := makePtidejVar(pb, "Client1", 1, length(listOfEntities)),
            client2  := makePtidejVar(pb, "Client2", 1, length(listOfEntities))
        in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseConstraint(
                    "Client1 ----> Mediator",
                    "throw new RuntimeException(\"Client1 ----> Mediator\");",
                    client1,
                    mediator),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Mediator ----> Client1",
                    "throw new RuntimeException(\"Mediator ----> Client1\");",
                    mediator,
                    client1),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Mediator <> Client1",
                    "throw new RuntimeException(\"Mediator <> Client1\");",
                    mediator,
                    client1),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Client2 ----> Mediator",
                    "throw new RuntimeException(\"Client2 ----> Mediator\");",
                    client2,
                    mediator),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Mediator ----> Client2",
                    "throw new RuntimeException(\"Mediator ----> Client2\");",
                    mediator,
                    client2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Mediator <> Client2",
                    "throw new RuntimeException(\"Mediator <> Client2\");",
                    mediator,
                    client2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client1 <> Client2",
                    "throw new RuntimeException(\"Client1 <> Client2\");",
                    client1,
                    client2),
                 100),
            post(pb,
                 makeIgnoranceConstraint(
                    "Client1 -/--> Client2",
                    "throw new RuntimeException(\"Client1 -/--> Client2\");",
                    client1,
                    client2),
                 50),
            post(pb,
                 makeIgnoranceConstraint(
                    "Client2 -/--> Client1",
                    "throw new RuntimeException(\"Client1 -/--> Client2\");",
                    client2,
                    client1),
                 50),
            pb
        )
]
[ac4ProblemForMediatorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb       := makePtidejProblem("Mediator Pattern Problem", length(listOfEntities), 90),
            mediator := makePtidejVar(pb, "Mediator", 1, length(listOfEntities)),
            client1  := makePtidejVar(pb, "Client1", 1, length(listOfEntities)),
            client2  := makePtidejVar(pb, "Client2", 1, length(listOfEntities))
        in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseAC4Constraint(
                    "Client1 ----> Mediator",
                    "throw new RuntimeException(\"Client1 ----> Mediator\");",
                    client1,
                    mediator),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Mediator ----> Client1",
                    "throw new RuntimeException(\"Mediator ----> Client1\");",
                    mediator,
                    client1),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Mediator <> Client1",
                    "throw new RuntimeException(\"Mediator <> Client1\");",
                    mediator,
                    client1),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Client2 ----> Mediator",
                    "throw new RuntimeException(\"Client2 ----> Mediator\");",
                    client2,
                    mediator),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Mediator ----> Client2",
                    "throw new RuntimeException(\"Mediator ----> Client2\");",
                    mediator,
                    client2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Mediator <> Client2",
                    "throw new RuntimeException(\"Mediator <> Client2\");",
                    mediator,
                    client2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client1 <> Client2",
                    "throw new RuntimeException(\"Client1 <> Client2\");",
                    client1,
                    client2),
                 100),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Client1 -/--> Client2",
                    "throw new RuntimeException(\"Client1 -/--> Client2\");",
                    client1,
                    client2),
                 50),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Client2 -/--> Client1",
                    "throw new RuntimeException(\"Client1 -/--> Client2\");",
                    client2,
                    client1),
                 50),
            pb
        )
]
