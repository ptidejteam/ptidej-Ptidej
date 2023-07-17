[ac4ProblemForMediatorModel() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("Mediator Model Problem", length(listOfEntities), 90),
            client1Var := makePtidejVar(pb, "Client1", 1, length(listOfEntities)),
            client2Var := makePtidejVar(pb, "Client2", 1, length(listOfEntities)),
            mediatorVar := makePtidejVar(pb, "Mediator", 1, length(listOfEntities)) in (

            setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeUseAC4Constraint(
                    "Client1 -k--> Mediator",
                    "throw new RuntimeException(\"Client1 should -k--> Mediator\");",
                    client1Var,
                    mediatorVar),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Client2 -k--> Mediator",
                    "throw new RuntimeException(\"Client2 should -k--> Mediator\");",
                    client2Var,
                    mediatorVar),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Mediator -k--> Client1",
                    "throw new RuntimeException(\"Mediator should -k--> Client1\");",
                    mediatorVar,
                    client1Var),
                 100),
            post(pb,
                 makeUseAC4Constraint(
                    "Mediator -k--> Client2",
                    "throw new RuntimeException(\"Mediator should -k--> Client2\");",
                    mediatorVar,
                    client2Var),
                 100),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Client1 -/--> [C@1f3ea022",
                    "throw new RuntimeException(\"Client1 -/--> [C@1f3ea022\");",
                    client1Var,
                    client2Var),
                 75),
            post(pb,
                 makeIgnoranceAC4Constraint(
                    "Client2 -/--> [C@46154850",
                    "throw new RuntimeException(\"Client2 -/--> [C@46154850\");",
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
