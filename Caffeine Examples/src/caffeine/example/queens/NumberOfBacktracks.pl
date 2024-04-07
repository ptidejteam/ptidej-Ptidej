/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
query(N, M) :- 
	nextMethodEntryStaticEvent(_, removeQueen, _, _, _),
	!,
	N1 is N + 1,
	write('I remove a queen for the '),
	write(N1),
	write(' time(s)'),
	nl,
	query(N1, M).
query(N, N).


main(N1, N2) :-
	query(N1, N2).
