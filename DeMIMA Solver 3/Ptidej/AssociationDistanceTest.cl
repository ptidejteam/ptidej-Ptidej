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

[customProblemForAssociationDistanceTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("AssociationDistance Pattern Problem", length(listOfEntities), 90),
            entity := makePtidejVar(pb, "Entity", 1, length(listOfEntities), true),
            associatedEntity := makePtidejVar(pb, "AssociatedEntity", 1, length(listOfEntities), true),
            charge := makePtidejBoundVar(pb, "AssociationDistance", 1, length(listOfEntities), true)
		in (

			setVarsToShow(pb.globalSearchSolver, pb.vars),

            post(pb,
                 makeAssociationDistanceConstraint(
                    "charge(Entity ----> AssociatedEntity)",
                    "throw new RuntimeException(\"charge(Entity ----> AssociatedEntity)\");",
                    entity,
                    associatedEntity,
                    charge),
                 90),
			post(pb,
				 makeGreaterOrEqualPtidejConstraint(
                    "charge(Entity ----> AssociatedEntity)",
                    "throw new RuntimeException(\"charge(Entity ----> AssociatedEntity) >= 2\");",
					charge,
					1),
				90),
            pb
        )
]
