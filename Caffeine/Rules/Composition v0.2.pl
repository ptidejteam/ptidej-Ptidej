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
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */


/*
 * A predicate to simplify the event from the remote program.
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



/*
 * A predicate that checks the aggregation/composition relationships
 * for all the fields being monitored in the remote program:
 *		IMS(Whole, Part) = {field, array field}, 
 *		IMS(Part, Whole) = {no}, 
 *		MU(Whole) = [1, 1], 
 *		MU(Part) = [1, n], n in N, 
 *		EX(Whole, Part) = true, 
 *		EX(Part, Whole) = false, 
 *		LTd(Whole) >= LTd(Part).
 *
 * IMS(Whole, Part) = {field, array field} is implicitely checked
 * because the other properties are checked against field/array field
 * modifications.
 *
 * IMS(Part, Whole) is checked manually (so far).
 *
 * I left aside MU(Whole) and MU(Part) because they may disappear if
 * found redundant with EX(Whole, Part) and EX(Part, Whole).
 *
 * EX(Whole, Part) = true and EX(Part, Whole) = false are checked with
 * predicate checkEx/7.
 *
 * LTd(Whole) >= LTd(Part) is checked with predicate checkLt/7.
 */
composition(LA, LCMP, LO) :-
	composition0([], [], [], LO, LT, LD, [], [], [], LE, LS, LC),
	convertCrossed(LD, [], NLA1),
	convertShared(LS, [], NLA2),
	checkLtandEx(LT, LE, [], NLA3, [], LCMP),
	append(NLA1, NLA2, NNLA),
	append(NLA3, NNLA, LA).

composition0(LO, LT, LD, NNLO, NNLT, NNLD, LE, LS, LC, NNLE, NNLS, NNLC) :-
	nextEvent(
		[generateConstructorEntryEvent,
		 generateFieldModificationEvent,
		 generateFinalizerExitEvent,
		 generateProgramEndEvent],
		E),
	interpretEvent(E, IE),
	% write(IE), nl,

	checkLt(IE, LO, LT, LD, NLO, NLT, NLD),
	checkEx(IE, LE, LS, LC, NLE, NLS, NLC),

	!,
	(
			(IE = programEnd,
			 NNLO = NLO,
			 NNLT = NLT,
			 NNLD = NLD,
			 NNLE = NLE,
			 NNLS = NLS,
			 NNLC = NLC)
		;
			composition0(NLO, NLT, NLD, NNLO, NNLT, NNLD, NLE, NLS, NLC, NNLE, NNLS, NNLC)
	).
composition0(LO, LT, LD, LO, LT, LD, LE, LS, LC, LE, LS, LC).



/*
 * THIS COULD BE SUBJECT OF A HIGHER-LEVEL LANGUAGE!
 * Combine the results from the checkLt and checkEx predicates
 * to build the list of aggregation and composition relationships.
 */
checkLtandEx([closed(X, Y) | REST], LE, LAGR, NLAGR, LCMP, NLCMP) :-
    member(exclusive(X, Y), LE),
    checkLtandEx(REST, LE, LAGR, NLAGR, [composition(X, Y) | LCMP], NLCMP).
checkLtandEx([closed(X, Y) | REST], LE, LAGR, [aggregation(X, Y) | NLAGR], LCMP, NLCMP) :-
    checkLtandEx(REST, LE, LAGR, NLAGR, LCMP, NLCMP).
checkLtandEx([], _, LAGR, LAGR, LCMP, LCMP).
checkLtandEx(_, [], LAGR, LAGR, LCMP, LCMP).

convertCrossed([], LT, LT).
convertCrossed([crossed(X, Y) | REST], LT, NLT) :-
	convertCrossed(REST, [aggregation(X, Y) | LT], NLT).

convertShared([], LT, LT).
convertShared([shared(X, Y) | REST], LT, [aggregation(X, Y) | NLT]) :-
	convertShared(REST, LT, NLT).



/*
 * THIS COULD BE SUBJECT OF A HIGHER-LEVEL LANGUAGE!
 * A predicate that builds three lists:
 * 		- A list of the opened assignations.
 *		- A list of the closed assignations
 *		  (assignation, then finalization of the component, then finalization of the composite).
 *		- A list of the crossed assignations
 *		  (assignation, then finalization of the composite, then finalization of the component).
 */
checkLt(assignation(X, Y), LA, LT, LD, [opened(assignation(X, Y), []) | LA], LT, LD).
checkLt(finalization(X), LA, LT, LD, NLA, NLT, NLD) :-
	isOkay(finalization(X), LA, NLA, LT, NLT, LD, NLD).
checkLt(programEnd, LA, LT, LD, [], NLT, LD) :-
	convertOpened(LA, LT, NLT).
checkLt(_, LA, LT, LD, LA, LT, LD).

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
	LT, [closed(X, Y) | LT],
	LD, LD).

isOkay(
	finalization(X),
	[opened(assignation(X, Y), []) | LA], LA,
	LT, LT,
	LD, [crossed(X, Y) | LD]).

isOkay(
	finalization(X),
	[CARLA | LA], [CARLA | NLA],
	LT, NLT,
	LD, NLD) :-
		isOkay(finalization(X), LA, NLA, LT, NLT, LD, NLD).

convertOpened([], LT, LT).
convertOpened([opened(assignation(X, Y), [_]) | REST], LT, NLT) :-
	convertOpened(REST, [closed(X, Y) | LT], NLT).
convertOpened([opened(assignation(X, Y), []) | REST], LT, NLT) :-
	convertOpened(REST, [closed(X, Y) | LT], NLT).



/*
 * This clause checkEx is only partially valid: It shouls test that
 * an aggregate is not used in no other whole AND no other 'extern'
 * method access or variable assignation!
 * Actually, if we make the hypothesis that all possible uses of the
 * component are being monitored, then this predicate is valid.
 */
checkEx(assignation(X, Y), LE, LS, LC, [exclusive(X, Y) | LE], LS, [Y | LC]) :-
	not(member(Y, LC)).
checkEx(assignation(X, Y), LE, LS, LC, NLE, [shared(X, Y), shared(X1, Y) | LS], LC) :-
	member(Y, LC),
	member(exclusive(X1, Y), LE),
	removeElement(exclusive(X1, Y), LE, NLE).
checkEx(assignation(X, Y), LE, LS, LC, LE, [shared(X, Y) | LS], LC) :-
	member(Y, LC).
checkEx(_, LE, LS, LC, LE, LS, LC).



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
	ms(composition(LA, LC, LD), TIME),

	% write(LA), nl,
	% write(LC), nl,
	% write(LD), nl,

	display('List of remaining assignations', LD),
	display('List of compositions', LC),
	display('List of aggregations', LA),

	nl,
	write('in '),
	write(TIME),
	write(' ms.'),
	nl.
main(N1, N1).
