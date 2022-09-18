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

componentsType :: property()
[problemForStarredCompositePattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Starred Composite Pattern Problem", length(listOfEntities), 90),
            leavesType := makePtidejVar(pb, "LeavesType", 1, length(listOfEntities), false),
            leaf       := makePtidejVar(pb, "Leaf",       1, length(listOfEntities), false),
            composite  := makePtidejVar(pb, "Composite",  1, length(listOfEntities)),
            component  := makePtidejVar(pb, "Component",  1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            printf("Setting constraints for starred Composite pattern...\n"),
            post(pb, makeStrictInheritanceConstraint(
                "Composite -|>- Component",
                "Composite, Component |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                composite, component), 100),
            post(pb, makeStrictInheritanceConstraint(
                "Leaf -|>- LeavesType",
                "Leaf, LeavesType |\\n\\t\\t\\tjavaXL.XClass l1, javaXL.XClass l2 |\\n\\t\\t\\tl1.setSuperclass(l2.getName());",
                leaf, leavesType), 100),
            post(pb, makeInheritanceConstraint(
                "LeavesType ---- Components OR LeavesType -|>- Component",
                "LeavesType, Component |\\n\\t\\t\\tSystem.out.println(\"LeavesType -|>- Component\");",
                leavesType, component), 90),
            post(pb, makeCompositionConstraint(
                "Composite <>-- Leaf",
                "throw new RuntimeException(\"Composite <>-- Leaf\");",
                composite, leaf), 90),
            post(pb, makeListPropertyTypeConstraint(
                "Composite.componentsType contains type ComponentsType",
                "throw new RuntimeException(\"Composite.componentsType contains type ComponentsType\");",
                composite, leavesType, componentsType), 90),
            post(pb,
                 makeNotEqualConstraint(
                    "Composite != Leaf",
                    "throw new RuntimeException(\"Composite != Leaf\");",
                    composite,
                    leaf),
                 100),
            pb
        )
]
