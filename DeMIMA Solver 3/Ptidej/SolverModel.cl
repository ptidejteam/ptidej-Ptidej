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

// *********************************************
// * Part 0: Problem definitions and accessors *
// *********************************************

PtidejProblem <: PalmProblem(
	private/globalWeight:integer = 0)

[getGlobalWeight(p:PtidejProblem) : integer
	->	p.globalWeight
]
[setGlobalWeight(p:PtidejProblem, gw:integer) : void
	->	// [0] gw = ~S // gw,
		p.globalWeight := gw
]


// ************************************************
// * Part 1: Constraint definitions and accessors *
// ************************************************

PtidejBinConstraint <: PalmBinIntConstraint(
	private/name:string,
	private/command:string,
	private/thisConstraint:any = unknown,
	private/nextConstraint:any = unknown)

[getName(c:PtidejBinConstraint) : string
	->	c.name
]
[getXCommand(c:PtidejBinConstraint) : string
	->	c.command
]
[getThisConstraint(c:PtidejBinConstraint) : any
	->	c.thisConstraint
]
[getNextConstraint(c:PtidejBinConstraint) : any
	->	c.nextConstraint
]
[weight(c:PtidejBinConstraint) : integer
	->	c.hook.weight
]



PtidejLargeConstraint <: PalmLargeIntConstraint(
	private/name:string,
	private/command:string,
	private/thisConstraint:any = unknown,
	private/nextConstraint:any = unknown)

[getName(c:PtidejLargeConstraint) : string
	->	c.name
]
[getXCommand(c:PtidejLargeConstraint) : string
	->	c.command
]
[getThisConstraint(c:PtidejLargeConstraint) : any
	->	c.thisConstraint
]
[getNextConstraint(c:PtidejLargeConstraint) : any
	->	c.nextConstraint
]
[weight(c:PtidejLargeConstraint) : integer
	->	c.hook.weight
]



PtidejAC4Constraint <: PalmAC4BinConstraint(
	private/name:string,
	private/command:string,
	private/thisConstraint:any = unknown,
	private/nextConstraint:any = unknown)

[getName(c:PtidejAC4Constraint) : string
	->	c.name
]
[getXCommand(c:PtidejAC4Constraint) : string
	->	c.command
]
[getThisConstraint(c:PtidejAC4Constraint) : any
	->	c.thisConstraint
]
[getNextConstraint(c:PtidejAC4Constraint) : any
	->	c.nextConstraint
]
[weight(c:PtidejAC4Constraint) : integer
	->	c.hook.weight
]



// ********************************
// * Part 2: Variable definitions *
// ********************************

// Yann 2001/07/24: Improvements
// The class PtidejVar now contains a new attribute
// toBeEnumerated. This is a boolean value, that is at
// true to indicate that the corresponding variable is
// to be enumerated and at false when the corresponding
// variable should not be enumerated (list of PEntities).
// See also the declaration of the class PtidejAssignVar
// and of the method selectBranchingObject(b:PtidejAssignVar).

PtidejVar <: PalmIntVar(toBeEnumerated:boolean = true)
PtidejBoundVar <: PtidejVar()

[setVarsToShow(
	globalSearchSolver:GlobalSearchSolver, 
	vars:list<choco/PtidejVar>) : void
	->	globalSearchSolver.choco/varsToShow :=
			set!(list<choco/IntVar>{(vars[i] as choco/IntVar) | i in 1 .. length(vars)})
]
[setVarsToShow(
	globalSearchSolver:GlobalSearchSolver, 
	vars:list<choco/IntVar>) : void
	->	globalSearchSolver.choco/varsToShow := set!(copy(vars))
]
