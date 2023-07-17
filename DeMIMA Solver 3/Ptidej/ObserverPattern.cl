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

[customProblemForObserverPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Observer Pattern Problem", length(listOfEntities), 90),
            observer := makePtidejVar(pb, "Observer", 1, length(listOfEntities)),
            subject := makePtidejVar(pb, "Subject", 1, length(listOfEntities)),
            concreteSubject := makePtidejVar(pb, "ConcreteSubject", 1, length(listOfEntities)),
            concreteObserver := makePtidejVar(pb, "ConcreteObserver", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeCompositionConstraint(
                    "Subject <>--> Observer",
                    "throw new RuntimeException(\"Subject <>--> Observer\");",
                    subject,
                    observer),
                 50),
            post(pb,
                 makeUseConstraint(
                    "Subject ----> Observer",
                    "throw new RuntimeException(\"Subject <>--> Observer\");",
                    subject,
                    observer),
                 90),
            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteSubject -|>- Subject",
                    "ConcreteSubject, Subject |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    concreteSubject,
                    subject),
                 90),
            post(pb,
                 makeInheritancePathConstraint(
                    "ConcreteObserver -|>- Observer",
                    "ConcreteObserver, Observer |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    concreteObserver,
                    observer),
                 90),
            post(pb,
                 makeUseConstraint(
                    "ConcreteObserver ----> ConcreteSubject",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    concreteObserver,
                    concreteSubject),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> Subject",
                    "throw new RuntimeException(\"Observer <> Subject\");",
                    observer,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> ConcreteSubject",
                    "throw new RuntimeException(\"Observer <> ConcreteSubject\");",
                    observer,
                    concreteSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> ConcreteObserver",
                    "throw new RuntimeException(\"Observer <> ConcreteObserver\");",
                    observer,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> Observer",
                    "throw new RuntimeException(\"Subject <> Observer\");",
                    subject,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> ConcreteSubject",
                    "throw new RuntimeException(\"Subject <> ConcreteSubject\");",
                    subject,
                    concreteSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> ConcreteObserver",
                    "throw new RuntimeException(\"Subject <> ConcreteObserver\");",
                    subject,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> Observer",
                    "throw new RuntimeException(\"ConcreteSubject <> Observer\");",
                    concreteSubject,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> Subject",
                    "throw new RuntimeException(\"ConcreteSubject <> Subject\");",
                    concreteSubject,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> ConcreteObserver",
                    "throw new RuntimeException(\"ConcreteSubject <> ConcreteObserver\");",
                    concreteSubject,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> Observer",
                    "throw new RuntimeException(\"ConcreteObserver <> Observer\");",
                    concreteObserver,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> Subject",
                    "throw new RuntimeException(\"ConcreteObserver <> Subject\");",
                    concreteObserver,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> ConcreteSubject",
                    "throw new RuntimeException(\"ConcreteObserver <> ConcreteSubject\");",
                    concreteObserver,
                    concreteSubject),
                 100),
            pb
        )
]
[ac4ProblemForObserverPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Observer Pattern Problem", length(listOfEntities), 90),
            observer := makePtidejVar(pb, "Observer", 1, length(listOfEntities)),
            subject := makePtidejVar(pb, "Subject", 1, length(listOfEntities)),
            concreteSubject := makePtidejVar(pb, "ConcreteSubject", 1, length(listOfEntities)),
            concreteObserver := makePtidejVar(pb, "ConcreteObserver", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeCompositionAC4Constraint(
                    "Subject <>--> Observer",
                    "throw new RuntimeException(\"Subject <>--> Observer\");",
                    subject,
                    observer),
                 50),
            post(pb,
                 makeUseAC4Constraint(
                    "Subject ----> Observer",
                    "throw new RuntimeException(\"Subject <>--> Observer\");",
                    subject,
                    observer),
                 90),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteSubject -|>- Subject",
                    "ConcreteSubject, Subject |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    concreteSubject,
                    subject),
                 90),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "ConcreteObserver -|>- Observer",
                    "ConcreteObserver, Observer |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    concreteObserver,
                    observer),
                 90),
            post(pb,
                 makeUseAC4Constraint(
                    "ConcreteObserver ----> ConcreteSubject",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    concreteObserver,
                    concreteSubject),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> Subject",
                    "throw new RuntimeException(\"Observer <> Subject\");",
                    observer,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> ConcreteSubject",
                    "throw new RuntimeException(\"Observer <> ConcreteSubject\");",
                    observer,
                    concreteSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Observer <> ConcreteObserver",
                    "throw new RuntimeException(\"Observer <> ConcreteObserver\");",
                    observer,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> Observer",
                    "throw new RuntimeException(\"Subject <> Observer\");",
                    subject,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> ConcreteSubject",
                    "throw new RuntimeException(\"Subject <> ConcreteSubject\");",
                    subject,
                    concreteSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> ConcreteObserver",
                    "throw new RuntimeException(\"Subject <> ConcreteObserver\");",
                    subject,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> Observer",
                    "throw new RuntimeException(\"ConcreteSubject <> Observer\");",
                    concreteSubject,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> Subject",
                    "throw new RuntimeException(\"ConcreteSubject <> Subject\");",
                    concreteSubject,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteSubject <> ConcreteObserver",
                    "throw new RuntimeException(\"ConcreteSubject <> ConcreteObserver\");",
                    concreteSubject,
                    concreteObserver),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> Observer",
                    "throw new RuntimeException(\"ConcreteObserver <> Observer\");",
                    concreteObserver,
                    observer),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> Subject",
                    "throw new RuntimeException(\"ConcreteObserver <> Subject\");",
                    concreteObserver,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "ConcreteObserver <> ConcreteSubject",
                    "throw new RuntimeException(\"ConcreteObserver <> ConcreteSubject\");",
                    concreteObserver,
                    concreteSubject),
                 100),
            pb
        )
]
