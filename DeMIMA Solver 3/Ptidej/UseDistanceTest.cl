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

[customProblemForUseDistanceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("UseDistance Pattern Problem", length(listOfEntities), 90),
            entity := makePtidejVar(pb, "Entity", 1, length(listOfEntities), true),
            knownEntity := makePtidejVar(pb, "KnownEntity", 1, length(listOfEntities), true),
            charge := makePtidejBoundVar(pb, "UseDistance", 1, length(listOfEntities), true)
		in (

            setVarsToShow(pb.globalSearchSolver, list<PtidejVar>(entity, knownEntity, charge)),

            post(pb,
                 makeUseDistanceConstraint(
                    "charge(Entity -k--> KnownEntity)",
                    "throw new RuntimeException(\"charge(Entity -k--> KnownEntity)\");",
                    entity,
                    knownEntity,
                    charge),
                 90),
			post(pb,
				 makeGreaterOrEqualPtidejConstraint(
                    "charge(Entity ----> KnownEntity)",
                    "throw new RuntimeException(\"charge(Entity -k--> KnownEntity) >= 2\");",
					charge,
					2),
				90),
            pb
        )
]
