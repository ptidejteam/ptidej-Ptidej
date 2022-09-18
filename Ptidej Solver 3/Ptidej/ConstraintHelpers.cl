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

[getSymbol(m:method) : string
	->	let
			symbol:string := "unknown"
		in (
			// [0] m = ~S // m,
			if (m = makeAggregationAC4Constraint @ string
				| m = makeAggregationConstraint @ string) (
				symbol := "[]-->"
			),
			if (m = makeAssociationAC4Constraint @ string
				| m = makeAssociationConstraint @ string) (
				symbol := "---->"
			),
			if (m = makeCompositionAC4Constraint @ string
				| m = makeCompositionConstraint @ string) (
				symbol := "[#]->"
			),
			if (m = makeContainerAggregationAC4Constraint @ string
				| m = makeContainerAggregationConstraint @ string) (
				symbol := "<>-->"
			),
			if (m = makeContainerCompositionAC4Constraint @ string
				| m = makeContainerCompositionConstraint @ string) (
				symbol := "<#>->"
			),
			if (m = makeCreationAC4Constraint @ string
				| m = makeCreationConstraint @ string) (
				symbol := "-*-->"
			),
			if (m = makeIgnoranceAC4Constraint @ string
				| m = makeIgnoranceConstraint @ string) (
				symbol := "-/-->"
			),
			if (m = makeInheritanceAC4Constraint @ string
				| m = makeInheritanceConstraint @ string) (
				symbol := "-|>- or ="
			),
			if (m = makeInheritancePathAC4Constraint @ string
				| m = makeInheritancePathConstraint @ string) (
				symbol := "-|>-...-|>- or ="
			),
			if (m = makeStrictInheritancePathConstraint @ string) (
				symbol := "-|>-...-|>-"
			),
			if (m = makeUseAC4Constraint @ string
				| m = makeUseConstraint @ string) (
				symbol := "-k-->"
			),
			if (m = makeCreationAC4Constraint @ string
				| m = makeCreationConstraint @ string) (
				symbol := "-*-->"
			),
			if (m = makeStrictInheritanceAC4Constraint @ string
				| m = makeStrictInheritanceConstraint @ string) (
				symbol := "-|>-"
			),
			symbol
		)
]