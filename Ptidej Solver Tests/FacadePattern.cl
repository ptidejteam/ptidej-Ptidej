[customProblemForFacadeModel() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Facade Model Problem", length(listOfEntities), 90),
            clientVar := makePtidejVar(pb, "Client", 1, length(listOfEntities)),
            facadeVar := makePtidejVar(pb, "Facade", 1, length(listOfEntities)),
            subsystemEntityVar := makePtidejVar(pb, "SubsystemEntity", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationConstraint(
                    "Client ----> Facade",
                    "throw new RuntimeException(\"Client should ----> Facade\");",
                    clientVar,
                    facadeVar),
                 100),
            post(pb,
                 makeAssociationConstraint(
                    "Facade ----> SubsystemEntity",
                    "throw new RuntimeException(\"Facade should ----> SubsystemEntity\");",
                    facadeVar,
                    subsystemEntityVar),
                 100),
            post(pb,
                 makeIgnoranceConstraint(
                    "Client -/--> [C@6f3f154b",
                    "throw new RuntimeException(\"Client -/--> [C@6f3f154b\");",
                    clientVar,
                    subsystemEntityVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Facade -/--> [C@1ee2a54d",
                    "throw new RuntimeException(\"Facade -/--> [C@1ee2a54d\");",
                    facadeVar,
                    clientVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "SubsystemEntity -/--> [C@1ee2a54d",
                    "throw new RuntimeException(\"SubsystemEntity -/--> [C@1ee2a54d\");",
                    subsystemEntityVar,
                    clientVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "SubsystemEntity -/--> [C@31d27212",
                    "throw new RuntimeException(\"SubsystemEntity -/--> [C@31d27212\");",
                    subsystemEntityVar,
                    facadeVar),
                 75),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> Facade",
                    "throw new RuntimeException(\"Client <> Facade\");",
                    clientVar,
                    facadeVar),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client <> SubsystemEntity",
                    "throw new RuntimeException(\"Client <> SubsystemEntity\");",
                    clientVar,
                    subsystemEntityVar),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Facade <> SubsystemEntity",
                    "throw new RuntimeException(\"Facade <> SubsystemEntity\");",
                    facadeVar,
                    subsystemEntityVar),
                 100),
            pb
        )
]
