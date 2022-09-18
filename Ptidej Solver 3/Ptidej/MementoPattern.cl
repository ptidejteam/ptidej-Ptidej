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

[customProblemForMementoPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Memento Pattern Problem", length(listOfEntities), 90),
            originator   := makePtidejVar(pb, "Originator",   1, length(listOfEntities)),
            memento      := makePtidejVar(pb, "Memento",      1, length(listOfEntities)),
            caretaker    := makePtidejVar(pb, "Caretaker",    1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseConstraint(
                    "Originator ----> Memento",
                    "System.out.println(\"Not implemented yet.\");",
                    originator,
                    memento),
                 75),
            post(pb,
                 makeCompositionConstraint(
                    "Caretaker <>--> Memento",
                    "throw new RuntimeException(\"Caretaker <>--> Memento\");",
                    caretaker,
                    memento),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Originator <> Memento",
                    "throw new RuntimeException(\"Originator <> Memento\");",
                    originator,
                    memento),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Originator <> Caretaker",
                    "throw new RuntimeException(\"Originator <> Caretaker\");",
                    originator,
                    caretaker),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Memento <> Caretaker",
                    "throw new RuntimeException(\"Memento <> Caretaker\");",
                    memento,
                    caretaker),
                 100),
            pb
        )
]
[ac4ProblemForMementoPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Memento Pattern Problem", length(listOfEntities), 90),
            originator   := makePtidejVar(pb, "Originator",   1, length(listOfEntities)),
            memento      := makePtidejVar(pb, "Memento",      1, length(listOfEntities)),
            caretaker    := makePtidejVar(pb, "Caretaker",    1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseAC4Constraint(
                    "Originator ----> Memento",
                    "System.out.println(\"Not implemented yet.\");",
                    originator,
                    memento),
                 75),
            post(pb,
                 makeCompositionAC4Constraint(
                    "Caretaker <>--> Memento",
                    "throw new RuntimeException(\"Caretaker <>--> Memento\");",
                    caretaker,
                    memento),
                 50),
            post(pb,
                 makeNotEqualConstraint(
                    "Originator <> Memento",
                    "throw new RuntimeException(\"Originator <> Memento\");",
                    originator,
                    memento),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Originator <> Caretaker",
                    "throw new RuntimeException(\"Originator <> Caretaker\");",
                    originator,
                    caretaker),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Memento <> Caretaker",
                    "throw new RuntimeException(\"Memento <> Caretaker\");",
                    memento,
                    caretaker),
                 100),
            pb
        )
]
