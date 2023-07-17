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

[ptidejVersion() : string
	->	"1.0.1"
]
[ptidejReleaseDate() : string
	->	"July 11, 2005"
]
[ptidejInfo() : string
	->	"++ Ptidej: Pattern Trace Identification Detection and Enhancement for Java\n++ Constraint Programming for Design Patterns and Design Defects Identification"
]

[showPtidejLicense() : void
    ->  printf(
    		"~A \n++ Ptidej Solver v~A (~A), Copyright (c) 2001-2004 Y.-G. Gu‚h‚neuc\n",
    		ptidejInfo(),
    		ptidejVersion(),
    		ptidejReleaseDate())
]

(showPtidejLicense())
