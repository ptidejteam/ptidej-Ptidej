[customProblemForMediatorModel() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Mediator Model Problem", length(listOfEntities), 90),
            client1Var := makePtidejVar(pb, "Client1", 1, length(listOfEntities)),
            client2Var := makePtidejVar(pb, "Client2", 1, length(listOfEntities)),
            mediatorVar := makePtidejVar(pb, "Mediator", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseConstraint(
                    "Client1 -k--> Mediator",
                    "throw new RuntimeException(\"Client1 should -k--> Mediator\");",
                    client1Var,
                    mediatorVar),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Client2 -k--> Mediator",
                    "throw new RuntimeException(\"Client2 should -k--> Mediator\");",
                    client2Var,
                    mediatorVar),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Mediator -k--> Client1",
                    "throw new RuntimeException(\"Mediator should -k--> Client1\");",
                    mediatorVar,
                    client1Var),
                 100),
            post(pb,
                 makeUseConstraint(
                    "Mediator -k--> Client2",
                    "throw new RuntimeException(\"Mediator should -k--> Client2\");",
                    mediatorVar,
                    client2Var),
                 100),
            post(pb,
                 makeIgnoranceConstraint(
                    "Client1 -/--> [C@5ad89a0e",
                    "throw new RuntimeException(\"Client1 -/--> [C@5ad89a0e\");",
                    client1Var,
                    client2Var),
                 75),
            post(pb,
                 makeIgnoranceConstraint(
                    "Client2 -/--> [C@306445af",
                    "throw new RuntimeException(\"Client2 -/--> [C@306445af\");",
                    client2Var,
                    client1Var),
                 75),
            post(pb,
                 makeNotEqualConstraint(
                    "Client1 <> Client2",
                    "throw new RuntimeException(\"Client1 <> Client2\");",
                    client1Var,
                    client2Var),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client1 <> Mediator",
                    "throw new RuntimeException(\"Client1 <> Mediator\");",
                    client1Var,
                    mediatorVar),
                 100),
            post(pb,
                 makeNotEqualConstraint(
                    "Client2 <> Mediator",
                    "throw new RuntimeException(\"Client2 <> Mediator\");",
                    client2Var,
                    mediatorVar),
                 100),
            pb
        )
]
