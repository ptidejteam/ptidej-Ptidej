/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/

%-----------------------------------------------------------------------------

findSingletons(S, NS) :-
	constructorsNewClasses([], NC),
	sortOutSingletons(NC, S, NS).

constructorsNewClasses(C, NNC) :-
	nextEvent(
		[generateClassLoadEvent,
		 generateConstructorEntryEvent],
		E),
	isInstantiation(E, C, NC),
	constructorsNewClasses(NC, NNC).
constructorsNewClasses(C, C).

isInstantiation(classLoad(_, CLASSNAME), C, NC) :-
	isInstantiation0(CLASSNAME, C, NC).
isInstantiation(constructorEntry(_, CLASSNAME, _), C, NC) :-
	isInstantiation0(CLASSNAME, C, NC).
isInstantiation(_, C, C).

isInstantiation0(
	N,
	[instantiation(N, NUM) | C],
	[instantiation(N, NNUM) | C]) :-
		NNUM is NUM + 1.
isInstantiation0(N, [CAR | C], [CAR | NC]) :-
	isInstantiation0(N, C, NC).
isInstantiation0(N, [], [instantiation(N, 0)]).

sortOutSingletons(
	[instantiation(N, NUM) | REST],
	[instantiation(N, NUM) | SREST], NSREST) :-
		NUM < 2,
		sortOutSingletons(REST, SREST, NSREST).
sortOutSingletons([C | REST], SREST, [C | NSREST]) :-
	sortOutSingletons(REST, SREST, NSREST).
sortOutSingletons([], [], []).


display([]) :-
	nl.
display([N | CDR]) :-
	write(N), nl,
	display(CDR).
display(TITLE, L) :-
	write('------------------------------'), nl,
	write(TITLE), nl,
	write('------------------------------'), nl,
	display(L).


main(N1, N2) :-
	ms(findSingletons(S, NS), TIME),
	display('List of singletons', S),
	display('List of non-singletons', NS),
	write('Computations running time: '),
	write(TIME),
	write(' ms.'),
	nl.
main(N1, N1).
