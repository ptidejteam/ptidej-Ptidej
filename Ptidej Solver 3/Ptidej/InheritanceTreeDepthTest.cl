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

[customProblemForInheritanceTreeDepthTestPattern() : PtidejProblem
    ->  verbose() := 0,
        let pb := makePtidejProblem("InheritanceTreeDepthTest Pattern Problem", length(listOfEntities), 90),
            subEntity := makePtidejVar(pb, "SubEntity", 1, length(listOfEntities), true),
            superEntity := makePtidejVar(pb, "SuperEntity", 1, length(listOfEntities), true),
            depth := makePtidejBoundVar(pb, "Depth", 1, length(listOfEntities), true)
		in (

            setVarsToShow(pb.globalSearchSolver, list(subEntity, superEntity, depth)),

            post(pb,
                 makeInheritanceTreeDepthConstraint(
                    "depth(Sub-entity -|>- Super-entity)",
                    "throw new RuntimeException(\"depth(Sub-entity -|>- Super-entity)\");",
                    subEntity,
                    superEntity,
                    depth),
                 90),
			post(pb,
				 makeGreaterOrEqualPtidejConstraint(
                    "depth(Sub-entity -|>- Super-entity) >= 3",
                    "throw new RuntimeException(\"depth(Sub-entity -|>- Super-entity) >= 3\");",
					depth,
					3),
				90),
            pb
        )
]
