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

[customProblemForChainOfResponsibilityPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Chain Of Responsibility Pattern Problem", length(listOfEntities), 90),
            client          := makePtidejVar(pb, "Client",          1, length(listOfEntities)),
            handler         := makePtidejVar(pb, "Handler",         1, length(listOfEntities)),
            concreteHandler := makePtidejVar(pb, "ConcreteHandler", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            printf("Setting constraints for Chain Of Responsibility pattern...\n"),
            post(pb,
                 makeStrictInheritanceConstraint(
                    "ConcreteHandler -|>- Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    concreteHandler,
                    handler),
                 50),
            post(pb,
                 makeUseConstraint(
                    "Handler ---> Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    handler,
                    handler),
                 90),
            post(pb,
                 makeUseConstraint(
                    "Client ---> Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    client,
                    handler),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Client != Handler",
                    "throw new RuntimeException(\"Client != Handler\");",
                    client,
                    handler),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Handler != ConcreteHandler",
                    "throw new RuntimeException(\"Handler != ConcreteHandler\");",
                    handler,
                    concreteHandler),
                 100),
            pb
        )
]
[ac4ProblemForChainOfResponsibilityPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Chain Of Responsibility Pattern Problem", length(listOfEntities), 90),
            client          := makePtidejVar(pb, "Client",          1, length(listOfEntities)),
            handler         := makePtidejVar(pb, "Handler",         1, length(listOfEntities)),
            concreteHandler := makePtidejVar(pb, "ConcreteHandler", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "ConcreteHandler -|>- Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    concreteHandler,
                    handler),
                 50),
            post(pb,
                 makeUseAC4Constraint(
                    "Handler ---> Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    handler,
                    handler),
                 90),
            post(pb,
                 makeUseAC4Constraint(
                    "Client ---> Handler",
                    "System.out.println(\"Not implemented yet.\");",
                    client,
                    handler),
                 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Client != Handler",
                    "throw new RuntimeException(\"Client != Handler\");",
                    client,
                    handler),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Handler != ConcreteHandler",
                    "throw new RuntimeException(\"Handler != ConcreteHandler\");",
                    handler,
                    concreteHandler),
                 100),
            pb
        )
]
