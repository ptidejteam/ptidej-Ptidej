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

[customProblemForIteratorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb                := makePtidejProblem("Iterator Pattern Problem", length(listOfEntities), 90),
            iterator          := makePtidejVar(pb, "Iterator",          1, length(listOfEntities)),
            concreteIterator  := makePtidejVar(pb, "ConcreteIterator",  1, length(listOfEntities), false),
            aggregate         := makePtidejVar(pb, "Aggregate",         1, length(listOfEntities)),
            concreteAggregate := makePtidejVar(pb, "ConcreteAggregate", 1, length(listOfEntities), false) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteIterator -|>- ... -|>- Iterator",
                    "throw new RuntimeException(\"ConcreteIterator -|>- ... -|>- Iterator\");",
                    concreteIterator,
                    iterator),
                 90),
            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteAggregate -|>- ... -|>- Aggregate",
                    "throw new RuntimeException(\"ConcreteAggregate -|>- ... -|>- Aggregate\");",
                    concreteAggregate,
                    aggregate),
                 90),
            post(pb,
                 makeCreationConstraint(
                    "ConcreteAggregate -*--> ConcreteIterator",
                    "throw new RuntimeException(\"ConcreteAggregate -*--> ConcreteIterator\");",
                    concreteAggregate,
                    concreteIterator),
                 90),
            post(pb,
                 makeUseConstraint(
                    "ConcreteIterator ----> ConcreteAggregate",
                    "throw new RuntimeException(\"ConcreteIterator ----> ConcreteAggregate\");",
                    concreteIterator,
                    concreteAggregate),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteCreator",
                    "throw new RuntimeException(\"Creator <> ConcreteCreator\");",
                    iterator,
                    concreteIterator),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> ConcreteAggregate",
                    "throw new RuntimeException(\"Aggregate <> ConcreteAggregate\");",
                    aggregate,
                    concreteAggregate),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Iterator <> Aggregate",
                    "throw new RuntimeException(\"Iterator <> Aggregate\");",
                    iterator,
                    aggregate),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Iterator <> ConcreteAggregate",
                    "throw new RuntimeException(\"Iterator <> ConcreteAggregate\");",
                    iterator,
                    concreteAggregate),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> ConcreteAggregate",
                    "throw new RuntimeException(\"Aggregate <> ConcreteAggregate\");",
                    aggregate,
                    concreteIterator),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteIterator <> ConcreteAggregate",
                    "throw new RuntimeException(\"ConcreteIterator <> ConcreteAggregate\");",
                    concreteIterator,
                    concreteAggregate),
                 100),
            pb
        )
]
[ac4ProblemForIteratorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb                := makePtidejProblem("Iterator Pattern Problem", length(listOfEntities), 90),
            iterator          := makePtidejVar(pb, "Iterator",          1, length(listOfEntities)),
            concreteIterator  := makePtidejVar(pb, "ConcreteIterator",  1, length(listOfEntities), false),
            aggregate         := makePtidejVar(pb, "Aggregate",         1, length(listOfEntities)),
            concreteAggregate := makePtidejVar(pb, "ConcreteAggregate", 1, length(listOfEntities), false) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteIterator -|>- ... -|>- Iterator",
                    "throw new RuntimeException(\"ConcreteIterator -|>- ... -|>- Iterator\");",
                    concreteIterator,
                    iterator),
                 90),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteAggregate -|>- ... -|>- Aggregate",
                    "throw new RuntimeException(\"ConcreteAggregate -|>- ... -|>- Aggregate\");",
                    concreteAggregate,
                    aggregate),
                 90),
            post(pb,
                 makeCreationAC4Constraint(
                    "ConcreteAggregate -*--> ConcreteIterator",
                    "throw new RuntimeException(\"ConcreteAggregate -*--> ConcreteIterator\");",
                    concreteAggregate,
                    concreteIterator),
                 90),
            post(pb,
                 makeUseAC4Constraint(
                    "ConcreteIterator ----> ConcreteAggregate",
                    "throw new RuntimeException(\"ConcreteIterator ----> ConcreteAggregate\");",
                    concreteIterator,
                    concreteAggregate),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Creator <> ConcreteCreator",
                    "throw new RuntimeException(\"Creator <> ConcreteCreator\");",
                    iterator,
                    concreteIterator),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> ConcreteAggregate",
                    "throw new RuntimeException(\"Aggregate <> ConcreteAggregate\");",
                    aggregate,
                    concreteAggregate),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Iterator <> Aggregate",
                    "throw new RuntimeException(\"Iterator <> Aggregate\");",
                    iterator,
                    aggregate),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Iterator <> ConcreteAggregate",
                    "throw new RuntimeException(\"Iterator <> ConcreteAggregate\");",
                    iterator,
                    concreteAggregate),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Aggregate <> ConcreteAggregate",
                    "throw new RuntimeException(\"Aggregate <> ConcreteAggregate\");",
                    aggregate,
                    concreteIterator),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteIterator <> ConcreteAggregate",
                    "throw new RuntimeException(\"ConcreteIterator <> ConcreteAggregate\");",
                    concreteIterator,
                    concreteAggregate),
                 100),
            pb
        )
]
