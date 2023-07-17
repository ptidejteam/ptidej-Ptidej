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

[ac4ProblemForVisitorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Visitor Pattern Problem", length(listOfEntities), 90),
            nodeHierarchyRoot := makePtidejVar(pb, "NodeHierarchyRoot", 1, length(listOfEntities)),
            visitorHierarchyRoot := makePtidejVar(pb, "VisitorHierarchyRoot", 1, length(listOfEntities)),
            node := makePtidejVar(pb, "Node", 1, length(listOfEntities)),
            nodeVisitor := makePtidejVar(pb, "NodeVisitor", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationAC4Constraint(
                    "NodeHierarchyRoot ----> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeHierarchyRoot should ----> VisitorHierarchyRoot\");",
                    nodeHierarchyRoot,
                    visitorHierarchyRoot),
                 100),
            post(pb,
                 makeAssociationAC4Constraint(
                    "VisitorHierarchyRoot ----> NodeHierarchyRoot",
                    "throw new RuntimeException(\"VisitorHierarchyRoot should ----> NodeHierarchyRoot\");",
                    visitorHierarchyRoot,
                    nodeHierarchyRoot),
                 100),
            post(pb,
                 makeAssociationAC4Constraint(
                    "Node ----> NodeVisitor",
                    "throw new RuntimeException(\"Node should ----> NodeVisitor\");",
                    node,
                    nodeVisitor),
                 100),
            post(pb,
                 makeAssociationAC4Constraint(
                    "NodeVisitor ----> Node",
                    "throw new RuntimeException(\"NodeVisitor should ----> Node\");",
                    nodeVisitor,
                    node),
                 100),
            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "Node -|>- NodeHierarchyRoot",
                    "Node, NodeHierarchyRoot | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    node,
                    nodeHierarchyRoot),
                 50),
            post(pb,
                 makeStrictInheritanceAC4Constraint(
                    "NodeVisitor -|>- VisitorHierarchyRoot",
                    "NodeVisitor, VisitorHierarchyRoot | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    nodeVisitor,
                    visitorHierarchyRoot),
                 50),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "NodeHierarchyRoot -/--> Node",
                    "throw new RuntimeException(\"NodeHierarchyRoot -/--> Node\");",
                    nodeHierarchyRoot,
                    node),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "NodeHierarchyRoot -/--> NodeVisitor",
                    "throw new RuntimeException(\"NodeHierarchyRoot -/--> NodeVisitor\");",
                    nodeHierarchyRoot,
                    nodeVisitor),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "VisitorHierarchyRoot -/--> Node",
                    "throw new RuntimeException(\"VisitorHierarchyRoot -/--> Node\");",
                    visitorHierarchyRoot,
                    node),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "VisitorHierarchyRoot -/--> NodeVisitor",
                    "throw new RuntimeException(\"VisitorHierarchyRoot -/--> NodeVisitor\");",
                    visitorHierarchyRoot,
                    nodeVisitor),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Node -/--> NodeHierarchyRoot",
                    "throw new RuntimeException(\"Node -/--> NodeHierarchyRoot\");",
                    node,
                    nodeHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Node -/--> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"Node -/--> VisitorHierarchyRoot\");",
                    node,
                    visitorHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "NodeVisitor -/--> NodeHierarchyRoot",
                    "throw new RuntimeException(\"NodeVisitor -/--> NodeHierarchyRoot\");",
                    nodeVisitor,
                    nodeHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "NodeVisitor -/--> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeVisitor -/--> VisitorHierarchyRoot\");",
                    nodeVisitor,
                    visitorHierarchyRoot),
                 75),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> VisitorHierarchyRoot\");",
                    nodeHierarchyRoot,
                    visitorHierarchyRoot),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> Node",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> Node\");",
                    nodeHierarchyRoot,
                    node),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> NodeVisitor",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> NodeVisitor\");",
                    nodeHierarchyRoot,
                    nodeVisitor),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "VisitorHierarchyRoot <> Node",
                    "throw new RuntimeException(\"VisitorHierarchyRoot <> Node\");",
                    visitorHierarchyRoot,
                    node),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "VisitorHierarchyRoot <> NodeVisitor",
                    "throw new RuntimeException(\"VisitorHierarchyRoot <> NodeVisitor\");",
                    visitorHierarchyRoot,
                    nodeVisitor),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Node <> NodeVisitor",
                    "throw new RuntimeException(\"Node <> NodeVisitor\");",
                    node,
                    nodeVisitor),
                 100),
            pb
        )
]

[customProblemForVisitorPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Visitor Pattern Problem", length(listOfEntities), 90),
            nodeHierarchyRoot := makePtidejVar(pb, "NodeHierarchyRoot", 1, length(listOfEntities)),
            visitorHierarchyRoot := makePtidejVar(pb, "VisitorHierarchyRoot", 1, length(listOfEntities)),
            node := makePtidejVar(pb, "Node", 1, length(listOfEntities)),
            nodeVisitor := makePtidejVar(pb, "NodeVisitor", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationConstraint(
                    "NodeHierarchyRoot ----> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeHierarchyRoot should ----> VisitorHierarchyRoot\");",
                    nodeHierarchyRoot,
                    visitorHierarchyRoot),
                 100),
            post(pb,
                 makeAssociationConstraint(
                    "VisitorHierarchyRoot ----> NodeHierarchyRoot",
                    "throw new RuntimeException(\"VisitorHierarchyRoot should ----> NodeHierarchyRoot\");",
                    visitorHierarchyRoot,
                    nodeHierarchyRoot),
                 100),
            post(pb,
                 makeAssociationConstraint(
                    "Node ----> NodeVisitor",
                    "throw new RuntimeException(\"Node should ----> NodeVisitor\");",
                    node,
                    nodeVisitor),
                 100),
            post(pb,
                 makeAssociationConstraint(
                    "NodeVisitor ----> Node",
                    "throw new RuntimeException(\"NodeVisitor should ----> Node\");",
                    nodeVisitor,
                    node),
                 100),
            post(pb,
                 makeStrictInheritanceConstraint(
                    "Node -|>- NodeHierarchyRoot",
                    "Node, NodeHierarchyRoot | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    node,
                    nodeHierarchyRoot),
                 50),
            post(pb,
                 makeStrictInheritanceConstraint(
                    "NodeVisitor -|>- VisitorHierarchyRoot",
                    "NodeVisitor, VisitorHierarchyRoot | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    nodeVisitor,
                    visitorHierarchyRoot),
                 50),
            post(pb,
                 makeIgnoranceConstraint(
                    "NodeHierarchyRoot -/--> Node",
                    "throw new RuntimeException(\"NodeHierarchyRoot -/--> Node\");",
                    nodeHierarchyRoot,
                    node),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "NodeHierarchyRoot -/--> NodeVisitor",
                    "throw new RuntimeException(\"NodeHierarchyRoot -/--> NodeVisitor\");",
                    nodeHierarchyRoot,
                    nodeVisitor),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "VisitorHierarchyRoot -/--> Node",
                    "throw new RuntimeException(\"VisitorHierarchyRoot -/--> Node\");",
                    visitorHierarchyRoot,
                    node),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "VisitorHierarchyRoot -/--> NodeVisitor",
                    "throw new RuntimeException(\"VisitorHierarchyRoot -/--> NodeVisitor\");",
                    visitorHierarchyRoot,
                    nodeVisitor),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Node -/--> NodeHierarchyRoot",
                    "throw new RuntimeException(\"Node -/--> NodeHierarchyRoot\");",
                    node,
                    nodeHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Node -/--> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"Node -/--> VisitorHierarchyRoot\");",
                    node,
                    visitorHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "NodeVisitor -/--> NodeHierarchyRoot",
                    "throw new RuntimeException(\"NodeVisitor -/--> NodeHierarchyRoot\");",
                    nodeVisitor,
                    nodeHierarchyRoot),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "NodeVisitor -/--> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeVisitor -/--> VisitorHierarchyRoot\");",
                    nodeVisitor,
                    visitorHierarchyRoot),
                 75),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> VisitorHierarchyRoot",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> VisitorHierarchyRoot\");",
                    nodeHierarchyRoot,
                    visitorHierarchyRoot),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> Node",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> Node\");",
                    nodeHierarchyRoot,
                    node),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "NodeHierarchyRoot <> NodeVisitor",
                    "throw new RuntimeException(\"NodeHierarchyRoot <> NodeVisitor\");",
                    nodeHierarchyRoot,
                    nodeVisitor),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "VisitorHierarchyRoot <> Node",
                    "throw new RuntimeException(\"VisitorHierarchyRoot <> Node\");",
                    visitorHierarchyRoot,
                    node),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "VisitorHierarchyRoot <> NodeVisitor",
                    "throw new RuntimeException(\"VisitorHierarchyRoot <> NodeVisitor\");",
                    visitorHierarchyRoot,
                    nodeVisitor),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Node <> NodeVisitor",
                    "throw new RuntimeException(\"Node <> NodeVisitor\");",
                    node,
                    nodeVisitor),
                 100),
            pb
        )
]
