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

[customProblemForProxyPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Proxy Pattern Problem", length(listOfEntities), 90),
            subject := makePtidejVar(pb, "Subject", 1, length(listOfEntities)),
            realSubject := makePtidejVar(pb, "RealSubject", 1, length(listOfEntities)),
            proxy := makePtidejVar(pb, "Proxy", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritanceConstraint(
                    "RealSubject -|>- Subject",
                    "RealSubject, Subject | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    realSubject,
                    subject),
                 50),
            post(pb,
                 makeStrictInheritanceConstraint(
                    "Proxy -|>- Subject",
                    "Proxy, Subject | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    proxy,
                    subject),
                 50),
            post(pb,
                 makeUseConstraint(
                    "Proxy ----> RealSubject",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    proxy,
                    realSubject),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> RealSubject",
                    "throw new RuntimeException(\"Subject <> RealSubject\");",
                    subject,
                    realSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> Proxy",
                    "throw new RuntimeException(\"Subject <> Proxy\");",
                    subject,
                    proxy),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "RealSubject <> Subject",
                    "throw new RuntimeException(\"RealSubject <> Subject\");",
                    realSubject,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "RealSubject <> Proxy",
                    "throw new RuntimeException(\"RealSubject <> Proxy\");",
                    realSubject,
                    proxy),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Proxy <> Subject",
                    "throw new RuntimeException(\"Proxy <> Subject\");",
                    proxy,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Proxy <> RealSubject",
                    "throw new RuntimeException(\"Proxy <> RealSubject\");",
                    proxy,
                    realSubject),
                 100),
            pb
        )
]
[ac4ProblemForProxyPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Proxy Pattern Problem", length(listOfEntities), 90),
            subject := makePtidejVar(pb, "Subject", 1, length(listOfEntities)),
            realSubject := makePtidejVar(pb, "RealSubject", 1, length(listOfEntities)),
            proxy := makePtidejVar(pb, "Proxy", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "RealSubject -|>- Subject",
                    "RealSubject, Subject | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    realSubject,
                    subject),
                 50),
            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "Proxy -|>- Subject",
                    "Proxy, Subject | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    proxy,
                    subject),
                 50),
            post(pb,
                 makeUseAC4Constraint(
                    "Proxy ----> RealSubject",
                    "throw new RuntimeException(\"Composite <>--> Component\");",
                    proxy,
                    realSubject),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> RealSubject",
                    "throw new RuntimeException(\"Subject <> RealSubject\");",
                    subject,
                    realSubject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Subject <> Proxy",
                    "throw new RuntimeException(\"Subject <> Proxy\");",
                    subject,
                    proxy),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "RealSubject <> Subject",
                    "throw new RuntimeException(\"RealSubject <> Subject\");",
                    realSubject,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "RealSubject <> Proxy",
                    "throw new RuntimeException(\"RealSubject <> Proxy\");",
                    realSubject,
                    proxy),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Proxy <> Subject",
                    "throw new RuntimeException(\"Proxy <> Subject\");",
                    proxy,
                    subject),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Proxy <> RealSubject",
                    "throw new RuntimeException(\"Proxy <> RealSubject\");",
                    proxy,
                    realSubject),
                 100),
            pb
        )
]
