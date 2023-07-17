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

[customProblemForRedundantTransitivityPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Redundant Transitivity Pattern Problem", length(listOfEntities), 90),
            entity1 := makePtidejVar(pb, "Entity1", 1, length(listOfEntities)),
            entity2 := makePtidejVar(pb, "Entity2", 1, length(listOfEntities)),
            entity3 := makePtidejVar(pb, "Entity3", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseConstraint(
                    "Entity1 -k--> Entity2",
                    "throw new RuntimeException(\"Entity1 -k--> Entity2\");",
                    entity1,
                    entity2),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Entity2 -k--> Entity3",
                    "throw new RuntimeException(\"Entity2 -k--> Entity3\");",
                    entity2,
                    entity3),
                 100),
            post(pb,
                 makeIgnoranceConstraint(
                    "Entity1 -/--> Entity3",
                    "throw new RuntimeException(\"Entity1 -/--> Entity3\");",
                    entity1,
                    entity3),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity1 <> Entity2",
                    "throw new RuntimeException(\"Entity1 <> Entity2\");",
                    entity1,
                    entity2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity2 <> Entity3",
                    "throw new RuntimeException(\"Entity2 <> Entity3\");",
                    entity2,
                    entity3),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity1 <> Entity3",
                    "throw new RuntimeException(\"Entity1 <> Entity3\");",
                    entity1,
                    entity3),
                 100),
            pb
        )
]
[ac4ProblemForRedundantTransitivityPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Redundant Transitivity Pattern Problem", length(listOfEntities), 90),
            entity1 := makePtidejVar(pb, "Entity1", 1, length(listOfEntities)),
            entity2 := makePtidejVar(pb, "Entity2", 1, length(listOfEntities)),
            entity3 := makePtidejVar(pb, "Entity3", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationAC4Constraint(
                    "Entity1 ----> Entity2",
                    "throw new RuntimeException(\"Entity1 ----> Entity2\");",
                    entity1,
                    entity2),
                 100),
            post(pb,
                 makeAssociationAC4Constraint(
                    "Entity2 ----> Entity3",
                    "throw new RuntimeException(\"Entity2 ----> Entity3\");",
                    entity2,
                    entity3),
                 100),
            post(pb,
                 makeAssociationAC4Constraint(
                    "Entity1 -/--> Entity3",
                    "throw new RuntimeException(\"Entity1 -/--> Entity3\");",
                    entity1,
                    entity3),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity1 <> Entity2",
                    "throw new RuntimeException(\"Entity1 <> Entity2\");",
                    entity1,
                    entity2),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity2 <> Entity3",
                    "throw new RuntimeException(\"Entity2 <> Entity3\");",
                    entity2,
                    entity3),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Entity1 <> Entity3",
                    "throw new RuntimeException(\"Entity1 <> Entity3\");",
                    entity1,
                    entity3),
                 100),
            pb
        )
]
