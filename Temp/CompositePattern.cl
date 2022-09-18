[customProblemForCompositeModel() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Composite Model Problem", length(listOfEntities), 90),
            componentVar := makePtidejVar(pb, "Component", 1, length(listOfEntities)),
            compositeVar := makePtidejVar(pb, "Composite", 1, length(listOfEntities)),
            leafVar := makePtidejVar(pb, "Leaf", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeContainerCompositionConstraint(
                    "Composite <#>-> Component",
                    "throw new RuntimeException(\"Composite should <#>-> Component\");",
                    compositeVar,
                    componentVar),
                 100),
            post(pb,
                 makeInheritancePathConstraint(
                    "Composite -|>- ... -|>- Component",
                    "Composite, Component | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    compositeVar,
                    componentVar),
                 50),
            post(pb,
                 makeInheritancePathConstraint(
                    "Leaf -|>- ... -|>- Component",
                    "Leaf, Component | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());",
                    leafVar,
                    componentVar),
                 50),
            post(pb,
                 makeIgnoranceConstraint(
                    "Component -/--> [C@47d081d2",
                    "throw new RuntimeException(\"Component -/--> [C@47d081d2\");",
                    componentVar,
                    compositeVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Component -/--> [C@2dfa1667",
                    "throw new RuntimeException(\"Component -/--> [C@2dfa1667\");",
                    componentVar,
                    leafVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Composite -/--> [C@2dfa1667",
                    "throw new RuntimeException(\"Composite -/--> [C@2dfa1667\");",
                    compositeVar,
                    leafVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Leaf -/--> [C@501a2821",
                    "throw new RuntimeException(\"Leaf -/--> [C@501a2821\");",
                    leafVar,
                    componentVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Leaf -/--> [C@47d081d2",
                    "throw new RuntimeException(\"Leaf -/--> [C@47d081d2\");",
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
