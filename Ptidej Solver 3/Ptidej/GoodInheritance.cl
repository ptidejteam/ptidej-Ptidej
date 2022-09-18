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

[customProblemForGoodInheritancePattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Good Inheritance Pattern Problem", length(listOfEntities), 90),
            superEntity := makePtidejVar(pb, "SuperEntity", 1, length(listOfEntities)),
            subEntity   := makePtidejVar(pb, "SubEntity", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritancePathConstraint(
                    "Sub-entity -|>- ... -|>- Super-entity",
                    "throw new RuntimeException(\"Sub-entity -|>- ... -|>- Super-entity\");",
                    subEntity,
                    superEntity),
                 100),
            post(pb,
                 makeIgnoranceConstraint(
                    "Super-entity -/--> Sub-entity",
                    "throw new RuntimeException(\"Super-entity -/--> Sub-entity\");",
                    superEntity,
                    subEntity),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Super-entity <> Sub-entity",
                    "throw new RuntimeException(\"Super-entity <> Sub-entity\");",
                    superEntity,
                    subEntity),
                 100),
            pb
        )
]
[ac4ProblemForGoodInheritancePattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Good Inheritance Pattern Problem", length(listOfEntities), 90),
            superEntity := makePtidejVar(pb, "SuperEntity", 1, length(listOfEntities)),
            subEntity   := makePtidejVar(pb, "SubEntity", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritancePathAC4Constraint(
                    "Sub-entity -|>- ... -|>- Super-entity",
                    "throw new RuntimeException(\"Sub-entity -|>- ... -|>- Super-entity\");",
                    subEntity,
                    superEntity),
                 100),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Super-entity -/--> Sub-entity",
                    "throw new RuntimeException(\"Super-entity -/--> Sub-entity\");",
                    superEntity,
                    subEntity),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Super-entity <> Sub-entity",
                    "throw new RuntimeException(\"Super-entity <> Sub-entity\");",
                    superEntity,
                    subEntity),
                 100),
            pb
        )
]
