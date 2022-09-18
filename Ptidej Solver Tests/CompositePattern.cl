[ac4ProblemForCompositeModel() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Composite Model Problem", length(listOfEntities), 90),
            componentVar := makePtidejVar(pb, "Component", 1, length(listOfEntities)),
            compositeVar := makePtidejVar(pb, "Composite", 1, length(listOfEntities)),
            leafVar := makePtidejVar(pb, "Leaf", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeContainerCompositionAC4Constraint(
                    "Composite <#>-> Component",
                    "throw new RuntimeException(\"Composite should <#>-> Component\");",
                    compositeVar,
                    componentVar),
                 100),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "Composite -|>- ... -|>- Component",
                    "Composite, Component | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    compositeVar,
                    componentVar),
                 50),
            post(pb,
                 makeInheritancePathAC4Constraint(
                    "Leaf -|>- ... -|>- Component",
                    "Leaf, Component | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    leafVar,
                    componentVar),
                 50),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Component -/--> [C@2b08bc5a",
                    "throw new RuntimeException(\"Component -/--> [C@2b08bc5a\");",
                    componentVar,
                    compositeVar),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Component -/--> [C@4a85cec8",
                    "throw new RuntimeException(\"Component -/--> [C@4a85cec8\");",
                    componentVar,
                    leafVar),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Composite -/--> [C@4a85cec8",
                    "throw new RuntimeException(\"Composite -/--> [C@4a85cec8\");",
                    compositeVar,
                    leafVar),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Leaf -/--> [C@3dc087a2",
                    "throw new RuntimeException(\"Leaf -/--> [C@3dc087a2\");",
                    leafVar,
                    componentVar),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Leaf -/--> [C@2b08bc5a",
                    "throw new RuntimeException(\"Leaf -/--> [C@2b08bc5a\");",
                    leafVar,
                    compositeVar),
                 75),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Composite",
                    "throw new RuntimeException(\"Component <> Composite\");",
                    componentVar,
                    compositeVar),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Component <> Leaf",
                    "throw new RuntimeException(\"Component <> Leaf\");",
                    componentVar,
                    leafVar),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Composite <> Leaf",
                    "throw new RuntimeException(\"Composite <> Leaf\");",
                    compositeVar,
                    leafVar),
                 100),
            pb
        )
]
