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

/**
 * @version 	0.1
 * @author		Yann-Gaël Guéhéneuc
 */



/*
 * Acronyms:
 *		- LA: Waiting list, list of the yet-un-matched events.
 *		- LT: Okay list, list of the events showing a composition relationship.
 *		- LD: Error list, list of the events not corresponding to a composition relationship.
 */
interpretEvent(
	fieldModification(_, _, IID, VID, _, _),
	assignation(IID, VID)).
interpretEvent(
	constructorEntry(_, CLASSNAME, IID),
	instance(CLASSNAME, IID)) :-
		assert(instance(CLASSNAME, IID)).
interpretEvent(
	finalizerExit(_, _, IID),
	finalization(IID)).
interpretEvent(
	programEnd(_, _),
	programEnd).
interpretEvent(_, _) :- fail.

checkLt(LA, LT, LD) :-
	checkLt0([], [], [], LA, LT, LD).

checkLt0(LA, LT, LD, NNLA, NNLT, NNLD) :-
	nextEvent(
		[generateConstructorEntryEvent,
		 generateFieldModificationEvent,
		 generateFinalizerExitEvent,
		 generateProgramEndEvent],
		E),
	interpretEvent(E, IE),
	% write(IE), nl,
	checkLt1(IE, LA, LT, LD, NLA, NLT, NLD),
	!,
	(
			(IE = programEnd,
			 NNLA = NLA,
			 NNLT = NLT,
			 NNLD = NLD)
		;
			checkLt0(NLA, NLT, NLD, NNLA, NNLT, NNLD)
	).

checkLt0(LA, LT, LD, LA, LT, LD).

checkLt1(assignation(X, Y), LA, LT, LD, [opened(assignation(X, Y), []) | LA], LT, LD).
checkLt1(finalization(X), LA, LT, LD, NLA, NLT, NLD) :-
	isOkay(finalization(X), LA, NLA, LT, NLT, LD, NLD).
checkLt1(programEnd, LA, LT, LD, [], NLT, LD) :-
	convertOpened(LA, LT, NLT).
checkLt1(_, LA, LT, LD, LA, LT, LD).

isOkay(
	finalization(X),
	[], [],
	LT, LT,
	LD, LD).

isOkay(
	finalization(Y),
	[opened(assignation(X, Y), []) | LA], [opened(assignation(X, Y), [finalization(Y)]) | NLA],
	LT, NLT,
	LD, NLD) :-
		isOkay((Y), LA, NLA, LT, NLT, LD, NLD).

isOkay(
	finalization(X),
	[opened(assignation(X, Y), [finalization(Y)]) | LA], LA,
	LT, [composition(X, Y) | LT],
	LD, LD).

isOkay(
	finalization(X),
	[opened(assignation(X, Y), []) | LA], LA,
	LT, LT,
	LD, [aggregation(X, Y) | LD]).

isOkay(
	finalization(X),
	[CARLA | LA], [CARLA | NLA],
	LT, NLT,
	LD, NLD) :-
		isOkay(finalization(X), LA, NLA, LT, NLT, LD, NLD).

convertOpened([], LT, LT).
convertOpened([opened(assignation(X, Y), [_]) | REST], LT, NLT) :-
	convertOpened(REST, [composition(X, Y) | LT], NLT).
convertOpened([opened(assignation(X, Y), []) | REST], LT, NLT) :-
	convertOpened(REST, [composition(X, Y) | LT], NLT).



/*
 * This clause checkEx is only partially valid: It shouls test that
 * an aggregate is not used in no other whole AND no other 'extern'
 * method access or variable assignation!
 * Actually, if we make the hypothesis that all possible uses of the
 * component are being monitored, then this predicate is valid.
 */
checkEx(LPC, LA, NNLA, NLC) :-
	buildSharedList(LPC, [], LSC),
	removeSharedComponents(LPC, LSC, [], NLA, [], NLC),
	append(LA, NLA, NNLA).

buildSharedList([PC | RPC], LD, NNLD) :-
	buildSharedList0(PC, RPC, LD, NLD),
	buildSharedList(RPC, NLD, NNLD).
buildSharedList([], LD, LD).

buildSharedList0(composition(_, Y), RPC, LD, NLD) :-
	isShared(Y, RPC, R),
	append(R, LD, NLD).
buildSharedList0(_, [], LD, LD).

isShared(Y, [composition(_, Y) | RPC], [Y]).
isShared(Y, [composition(_, _) | RPC], R) :-
	isShared(Y, RPC, R).
isShared(Y, [], []).

removeSharedComponents([PC | RPC], LSC, LA, NNLA, LC, NNLC) :-
	removeSharedComponents0(PC, LSC, LA, NLA, LC, NLC),
	removeSharedComponents(RPC, LSC, NLA, NNLA, NLC, NNLC).
removeSharedComponents([], _, LA, LA, LC, LC).

removeSharedComponents0(
	composition(X, Y),
	LSC,
	LA,
	[aggregation(X, Y) | LA],
	LC,
	LC) :-
		member(Y, LSC).
removeSharedComponents0(
	composition(X, Y),
	LSC,
	LA,
	LA,
	LC,
	[composition(X, Y) | LC]).



/*
 * A simple pretty-printer predicate.
 */
display([]).
display([aggregation(X, Y) | CDR]) :-
	instance(N1, X),
	instance(N2, Y),
	write('    '), 
	write(aggregation(N1, N2)), nl,
	display(CDR).
display([composition(X, Y) | CDR]) :-
	instance(N1, X),
	instance(N2, Y),
	write('    '), 
	write(composition(N1, N2)), nl,
	display(CDR).
display([opened(assignation(X, Y), [finalization(Y)]) | CDR]) :-
	instance(N1, X),
	instance(N2, Y),
	write('    '), 
	write(unknown(N1, N2)), nl,
	write('        '), write(X), write(', '), write(Y), nl,
	display(CDR).
display(TITLE, []) :-
	write(TITLE),
	write(': '), write('None.'), nl.
display(TITLE, L) :-
	write(TITLE), write(':'), nl,
	display(L).



/*
 * The main query.
 */
main(N1, N2) :-
	ms((checkLt(LA, LC, LD), checkEx(LC, LA, NLA, NLC)), TIME),

	write('Lifetime property'), nl,
	write('-----------------'), nl,
	display('List of remaining assignations', LD),
	display('List of compositions', LC),
	display('List of aggregations', LA),

	nl,

	write('Exclusivity property'), nl,
	write('--------------------'), nl,
	display('List of remaining assignations', ND),
	display('List of compositions', NLC),
	display('List of aggregations', NLA),

	nl,
	write('in '),
	write(TIME),
	write(' ms.'),
	nl.
main(N1, N1).
