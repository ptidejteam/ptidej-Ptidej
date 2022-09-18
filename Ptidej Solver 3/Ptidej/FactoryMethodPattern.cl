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

[customProblemForFactoryMethodPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb              := makePtidejProblem("FactoryMethod Pattern Problem", length(listOfEntities), 90),
            creator         := makePtidejVar(pb, "Creator",         1, length(listOfEntities)),
            concreteCreator := makePtidejVar(pb, "ConcreteCreator", 1, length(listOfEntities)),
            product         := makePtidejVar(pb, "Product",         1, length(listOfEntities)),
            concreteProduct := makePtidejVar(pb, "ConcreteProduct", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteCreator -|>- ... -|>- Creator",
                    "throw new RuntimeException(\"ConcreteCreator -|>- ... -|>- Creator\");",
                    concreteCreator,
                    creator),
                 90),
            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteProduct -|>- ... -|>- Product",
                    "throw new RuntimeException(\"ConcreteProduct -|>- ... -|>- Product\");",
                    concreteProduct,
                    product),
                 90),
            post(pb,
                 makeAssociationConstraint(
                    "Creator ----> Product",
                    "throw new RuntimeException(\"Creator ----> Product\");",
                    creator,
                    product),
                 30),
            post(pb,
                 makeCreationConstraint(
                    "ConcreteCreator -*--> ConcreteProduct",
                    "throw new RuntimeException(\"ConcreteCreator -*--> ConcreteProduct\");",
                    concreteCreator,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> Product",
                    "throw new RuntimeException(\"Creator <> Product\");",
                    creator,
                    product),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteCreator",
                    "throw new RuntimeException(\"Creator <> ConcreteCreator\");",
                    creator,
                    concreteCreator),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteProduct",
                    "throw new RuntimeException(\"Creator <> ConcreteProduct\");",
                    creator,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Product <> ConcreteProduct",
                    "throw new RuntimeException(\"Product <> ConcreteProduct\");",
                    product,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteCreator <> ConcreteProduct",
                    "throw new RuntimeException(\"ConcreteCreator <> ConcreteProduct\");",
                    concreteCreator,
                    concreteProduct),
                 90),
            pb
        )
]
[ac4ProblemForFactoryMethodPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb              := makePtidejProblem("FactoryMethod Pattern Problem", length(listOfEntities), 90),
            creator         := makePtidejVar(pb, "Creator",         1, length(listOfEntities)),
            concreteCreator := makePtidejVar(pb, "ConcreteCreator", 1, length(listOfEntities)),
            product         := makePtidejVar(pb, "Product",         1, length(listOfEntities)),
            concreteProduct := makePtidejVar(pb, "ConcreteProduct", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteCreator -|>- ... -|>- Creator",
                    "throw new RuntimeException(\"ConcreteCreator -|>- ... -|>- Creator\");",
                    concreteCreator,
                    creator),
                 90),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteProduct -|>- ... -|>- Product",
                    "throw new RuntimeException(\"ConcreteProduct -|>- ... -|>- Product\");",
                    concreteProduct,
                    product),
                 90),
            post(pb,
                 makeUseAC4Constraint(
                    "Creator ----> Product",
                    "throw new RuntimeException(\"Creator ----> Product\");",
                    creator,
                    product),
                 30),
            post(pb,
                 makeCreationAC4Constraint(
                    "ConcreteCreator -*--> ConcreteProduct",
                    "throw new RuntimeException(\"ConcreteCreator -*--> ConcreteProduct\");",
                    concreteCreator,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> Product",
                    "throw new RuntimeException(\"Creator <> Product\");",
                    creator,
                    product),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteCreator",
                    "throw new RuntimeException(\"Creator <> ConcreteCreator\");",
                    creator,
                    concreteCreator),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteProduct",
                    "throw new RuntimeException(\"Creator <> ConcreteProduct\");",
                    creator,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Product <> ConcreteProduct",
                    "throw new RuntimeException(\"Product <> ConcreteProduct\");",
                    product,
                    concreteProduct),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteCreator <> ConcreteProduct",
                    "throw new RuntimeException(\"ConcreteCreator <> ConcreteProduct\");",
                    concreteCreator,
                    concreteProduct),
                 90),
            pb
        )
]
