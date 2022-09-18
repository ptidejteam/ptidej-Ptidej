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

[customProblemForCompositePattern() : PalmProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Composite design pattern", length(listOfEntities), 90),
            leaf       := makePtidejVar(pb, "Leaf",       1, length(listOfEntities), false),
            composite  := makePtidejVar(pb, "Composite",  1, length(listOfEntities), true),
            componente := makePtidejVar(pb, "Component",  1, length(listOfEntities), true)
		in (

			setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritanceConstraint(
                    "Composite -|>- Component",
                    "Composite, Component |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    composite,
                    componente),
                 50),
            post(pb,
                 makeStrictInheritancePathConstraint(
                    "Leaf -|>- Component",
                    "Leaf, Component |\\n\\t\\t\\tjavaXL.XClass l1, javaXL.XClass l2 |\\n\\t\\t\\tl1.setSuperclass(l2.getName());",
                    leaf,
                    componente),
                 90),
            post(pb,
                 makeContainerCompositionConstraint(
                    "Composite <>-- Component",
                    "throw new RuntimeException(\"Composite <>-- Component\");",
                    composite,
                    componente),
                 50),
            post(pb,
                 makeIgnoranceConstraint(
                    "Component -/--> Leaf",
                    "throw new RuntimeException(\"Component should not ----> Leaf\");",
                    componente,
                    leaf),
                 10),
            post(pb,
                 makeIgnoranceConstraint(
                    "Leaf -/--> Composite",
                    "throw new RuntimeException(\"Leaf should not ----> Composite\");",
                    leaf,
                    composite),
                 30),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Composite",
                    "throw new RuntimeException(\"Component <> Composite\");",
                    componente,
                    composite),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Leaf",
                    "throw new RuntimeException(\"Component <> Leaf\");",
                    componente,
                    leaf),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Composite <> Leaf",
                    "throw new RuntimeException(\"Composite <> Leaf\");",
                    composite,
                    leaf),
                 100),
            pb
        )
]
[ac4ProblemForCompositePattern() : PalmProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Composite Pattern Problem", length(listOfEntities), 90),
            leaf       := makePtidejVar(pb, "Leaf",       1, length(listOfEntities), false),
            composite  := makePtidejVar(pb, "Composite",  1, length(listOfEntities), true),
            componente := makePtidejVar(pb, "Component",  1, length(listOfEntities), true)
		in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "Composite -|>- Component",
                    "Composite, Component |\\n\\t\\t\\tjavaXL.XClass c1, javaXL.XClass c2 |\\n\\t\\t\\tc1.setSuperclass(c2.getName());",
                    composite,
                    componente),
                 50),
            post(pb,
                 makeStrictInheritancePathAC4Constraint(
                    "Leaf -|>- Component",
                    "Leaf, Component |\\n\\t\\t\\tjavaXL.XClass l1, javaXL.XClass l2 |\\n\\t\\t\\tl1.setSuperclass(l2.getName());",
                    leaf,
                    componente),
                 90),
            post(pb,
                 makeContainerCompositionAC4Constraint(
                    "Composite <>-- Component",
                    "throw new RuntimeException(\"Composite <>-- Component\");",
                    composite,
                    componente),
                 90),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Component -/--> Leaf",
                    "throw new RuntimeException(\"Component should not ----> Leaf\");",
                    componente,
                    leaf),
                 10),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Leaf -/--> Composite",
                    "throw new RuntimeException(\"Leaf should not ----> Composite\");",
                    leaf,
                    composite),
                 30),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Composite",
                    "throw new RuntimeException(\"Component <> Composite\");",
                    componente,
                    composite),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Leaf",
                    "throw new RuntimeException(\"Component <> Leaf\");",
                    componente,
                    leaf),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Composite <> Leaf",
                    "throw new RuntimeException(\"Composite <> Leaf\");",
                    composite,
                    leaf),
                 100),
            pb
        )
]
