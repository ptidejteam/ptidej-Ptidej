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
                    "Client -/--> [C@378f7362",
                    "throw new RuntimeException(\"Client -/--> [C@378f7362\");",
                    clientVar,
                    subsystemEntityVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Facade -/--> [C@64eb5975",
                    "throw new RuntimeException(\"Facade -/--> [C@64eb5975\");",
                    facadeVar,
                    clientVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "SubsystemEntity -/--> [C@64eb5975",
                    "throw new RuntimeException(\"SubsystemEntity -/--> [C@64eb5975\");",
                    subsystemEntityVar,
                    clientVar),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "SubsystemEntity -/--> [C@2f3da43e",
                    "throw new RuntimeException(\"SubsystemEntity -/--> [C@2f3da43e\");",
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
