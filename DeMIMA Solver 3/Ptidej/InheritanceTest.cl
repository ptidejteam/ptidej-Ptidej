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

[customProblemForInheritanceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("InheritanceTestPattern Pattern Problem", length(listOfEntities), 90),
            superEntity := makePtidejVar(pb, "SuperEntity", 1, length(listOfEntities), true),
            subEntity := makePtidejVar(pb, "SubEntity", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritanceConstraint(
                    "Sub-entity -|>- Super-entity OR Sub-entity == Super-entity",
                    "SubEntity, SuperEntity |\n\t\t\tjavaXL.XClass c1, javaXL.XClass c2 |\n\t\t\tc1.setSuperclass(c2.getName());",
                    subEntity,
                    superEntity),
                 100),
            pb
        )
]
[ac4ProblemForInheritanceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("InheritanceTestPattern Pattern Problem", length(listOfEntities), 90),
            superEntity := makePtidejVar(pb, "SuperEntity", 1, length(listOfEntities), true),
            subEntity := makePtidejVar(pb, "SubEntity", 1, length(listOfEntities), true) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeInheritanceAC4Constraint(
                    "Sub-entity -|>- Super-entity OR Sub-entity == Super-entity",
                    "SubEntity, SuperEntity |\n\t\t\tjavaXL.XClass c1, javaXL.XClass c2 |\n\t\t\tc1.setSuperclass(c2.getName());",
                    subEntity,
                    superEntity),
                 100),
            pb
        )
]
