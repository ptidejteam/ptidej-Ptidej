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

[customProblemForFacadePattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Facade Pattern Problem", length(listOfEntities), 90),
            facade := makePtidejVar(pb, "Facade", 1, length(listOfEntities)),
            subsystemEntity := makePtidejVar(pb, "SubsystemEntity", 1, length(listOfEntities), false),
            client := makePtidejVar(pb, "Client", 1, length(listOfEntities), false) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationConstraint(
                    "Facade ----> SubsystemEntity",
                    "throw new RuntimeException(\"Composite ----> Component\");",
                    facade,
                    subsystemEntity),
                 90),
            post(pb,
                 makeAssociationConstraint(
                    "Client ----> Facade",
                    "throw new RuntimeException(\"Composite ----> Component\");",
                    client,
                    facade),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Facade <> SubsystemEntity",
                    "throw new RuntimeException(\"Facade <> SubsystemEntity\");",
                    facade,
                    subsystemEntity),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Facade <> Client",
                    "throw new RuntimeException(\"Facade <> Client\");",
                    facade,
                    client),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "SubsystemEntity <> Facade",
                    "throw new RuntimeException(\"SubsystemEntity <> Facade\");",
                    subsystemEntity,
                    facade),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "SubsystemEntity <> Client",
                    "throw new RuntimeException(\"SubsystemEntity <> Client\");",
                    subsystemEntity,
                    client),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> Facade",
                    "throw new RuntimeException(\"Client <> Facade\");",
                    client,
                    facade),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> SubsystemEntity",
                    "throw new RuntimeException(\"Client <> SubsystemEntity\");",
                    client,
                    subsystemEntity),
                 100),
            pb
        )
]
[ac4ProblemForFacadePattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Facade Pattern Problem", length(listOfEntities), 90),
            facade := makePtidejVar(pb, "Facade", 1, length(listOfEntities)),
            subsystemEntity := makePtidejVar(pb, "SubsystemEntity", 1, length(listOfEntities), false),
            client := makePtidejVar(pb, "Client", 1, length(listOfEntities), false) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationAC4Constraint(
                    "Facade ----> SubsystemEntity",
                    "throw new RuntimeException(\"Composite ----> Component\");",
                    facade,
                    subsystemEntity),
                 90),
            post(pb,
                 makeAssociationAC4Constraint(
                    "Client ----> Facade",
                    "throw new RuntimeException(\"Composite ----> Component\");",
                    client,
                    facade),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Facade <> SubsystemEntity",
                    "throw new RuntimeException(\"Facade <> SubsystemEntity\");",
                    facade,
                    subsystemEntity),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Facade <> Client",
                    "throw new RuntimeException(\"Facade <> Client\");",
                    facade,
                    client),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "SubsystemEntity <> Facade",
                    "throw new RuntimeException(\"SubsystemEntity <> Facade\");",
                    subsystemEntity,
                    facade),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "SubsystemEntity <> Client",
                    "throw new RuntimeException(\"SubsystemEntity <> Client\");",
                    subsystemEntity,
                    client),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> Facade",
                    "throw new RuntimeException(\"Client <> Facade\");",
                    client,
                    facade),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> SubsystemEntity",
                    "throw new RuntimeException(\"Client <> SubsystemEntity\");",
                    client,
                    subsystemEntity),
                 100),
            pb
        )
]
