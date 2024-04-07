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
nextPlaceNextQueen :-
	nextMethodEntryStaticEvent(_, N, _, _, _),
	isPlaceNextQueen(N).

isPlaceNextQueen(placeNextQueen) :- !.
isPlaceNextQueen(removeQueen)    :- !, fail.
isPlaceNextQueen(setQueen)       :- !, fail.
isPlaceNextQueen(_)              :- nextPlaceNextQueen. 


query(N, M) :- 
	% I go to the next
	%	setQueen(...)
	%	placeNextQueen(...)
	%	removeQueen(...)
	% triplet of events:
	nextMethodEntryStaticEvent(_, setQueen, _, _, _),
	nextPlaceNextQueen,
	nextMethodEntryStaticEvent(_, removeQueen, _, _, _),
	% I count the number of time I execute the query:
	!,
	N1 is N + 1,
	write('I remove a queen just after having placed her for the '),
	write(N1),
	write(' time(s)'),
	nl,
	query(N1, M).
query(N, N).


main(N1, N2) :-
	query(N1, N2).
