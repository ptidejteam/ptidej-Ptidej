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
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.3
 */


/*
 * A predicate to simplify the event from the remote program.
 */
interpretEvent(
	fieldModification(EID, _, IID, VID, ICLASSNAME, VCLASSNAME),
	assignation(EID, ICLASSNAME, IID, VCLASSNAME, VID)).
interpretEvent(
	finalizerExit(EID, ICLASSNAME, IID),
	finalization(EID, ICLASSNAME, IID)).
interpretEvent(
	programEnd(EID, _),
	programEnd(EID)).
interpretEvent(E, _) :- fail.



/*
 * A predicate that checks the aggregation/composition relationships
 * for all the fields being monitored in the remote program:
 *		IMS(Whole, Part) = {field, array field, collection}, 
 *		IMS(Part, Whole) = {no}, 
 *		MU(Whole) = [1, 1], 
 *		MU(Part) = [1, n], n in N, 
 *		EX(Whole, Part) = true, 
 *		EX(Part, Whole) = false, 
 *		LTd(Whole) >= LTd(Part).
 *
 * IMS(Whole, Part) = {field, array field, collection} is implicitely
 * checked because the other properties are checked against any field
 * modifications.
 *
 * IMS(Part, Whole) is checked manually (so far).
 *
 * I left aside MU(Whole) and MU(Part) because they may disappear if
 * found redundant with EX(Whole, Part) and EX(Part, Whole).
 *
 * EX(Whole, Part) = true and EX(Part, Whole) = false are checked with
 * predicate checkEXProperty/3.
 *
 * LTd(Whole) >= LTd(Part) is checked with predicate checkLTProperty/3.
 */
instanceLevelCompositions(LIST) :-
	checkProperties([], LEXP, [], LLTP),
	checkComposition(LEXP, LLTP, LIST).

checkProperties(LEXP, NNLEXP, LLTP, NNLLTP) :-
	nextEvent(
		[generateConstructorEntryEvent,
		 generateFieldModificationEvent,
		 generateFinalizerExitEvent,
		 generateProgramEndEvent],
		 E),
	interpretEvent(E, IE),
	checkEXProperty(IE, LEXP, NLEXP),
	checkLTProperty(IE, LLTP, NLLTP),
	!,
	(
			(IE = programEnd,
			 NNLEXP = NLEXP,
			 NNLLTP = NLLTP)
		;
			checkProperties(NLEXP, NNLEXP, NLLTP, NNLLTP)
	).
checkProperties(LEXP, LEXP, LLTP, LLTP).


checkComposition([], [], []).
checkComposition([], _, []) :-
	write('Problem! Lists of life-time properties and of exclusivity properties have different size!'), nl, false.
checkComposition(_, [], []) :-
	write('Problem! Lists of exclusivity properties and of life-time properties have different size!'), nl, false.
checkComposition(
	[exclusivityProperty(A, AID, B, BID, OKAY) | EXPCDR],
	[lifetimeProperty(A, AID, B, BID, OKAY) | LTPCDR],
	[composition(A, AID, B, BID, OKAY) | CDR]) :-
		checkComposition(EXPCDR, LTPCDR, CDR).
checkComposition(
	[exclusivityProperty(A, AID, B, BID, _) | EXPCDR],
	[lifetimeProperty(A, AID, B, BID, _) | LTPCDR],
	[composition(A, AID, B, BID, false) | NCDR]) :-
		checkComposition(EXPCDR, LTPCDR, NCDR).



/*
 * A predicate that builds three lists:
 * 		- A list of the pending assignations.
 *		- A list of the closed assignations
 *		  (assignation, then finalization of the component, then finalization of the composite).
 *		- A list of the crossed assignations
 *		  (assignation, then finalization of the composite, then finalization of the component).
 */
checkLTProperty(
	assignation(_, A, AID, B, BID),
	LIST,
	NLIST) :-
		append(LIST, [pendingAssignation(A, AID, B, BID, [])], NLIST).
checkLTProperty(
	finalization(EID, A, AID),
	LIST,
	NLIST) :-
		doesLTPropertyHold(finalization(EID, A, AID), LIST, NLIST).
checkLTProperty(
	programEnd(_),
	LIST,
	NLIST) :-
		convertPendingAssignations(LIST, NLIST).
checkLTProperty(_, LIST, LIST).



doesLTPropertyHold(finalization(_, _, _), [], []).

doesLTPropertyHold(
	finalization(_, B, BID),
	[pendingAssignation(A, AID, B, BID, []) | CDR],
	[pendingAssignation(A, AID, B, BID, [finalization(B, BID)]) | NCDR]) :-
		doesLTPropertyHold(finalization(_, B, BID), CDR, NCDR).

doesLTPropertyHold(
	finalization(_, A, AID),
	[pendingAssignation(A, AID, B, BID, [finalization(B, BID)]) | CDR], 
	[lifetimeProperty(A, AID, B, BID, true) | CDR]).

doesLTPropertyHold(
	finalization(_, A, AID),
	[pendingAssignation(A, AID, B, BID, []) | CDR],
	[lifetimeProperty(A, AID, B, BID, false) | CDR]).

doesLTPropertyHold(
	finalization(EID, A, AID),
	[CAR | CDR],
	[CAR | NCDR]) :-
		doesLTPropertyHold(finalization(EID, A, AID), CDR, NCDR).



convertPendingAssignations([], []).
convertPendingAssignations(
	[pendingAssignation(A, AID, B, BID, [_]) | CDR],
	[lifetimeProperty(A, AID, B, BID, true) | NCDR]) :-
		convertPendingAssignations(CDR, NCDR).
convertPendingAssignations(
	[pendingAssignation(A, AID, B, BID, []) | CDR],
	[lifetimeProperty(A, AID, B, BID, true) | NCDR]) :-
		convertPendingAssignations(CDR, NCDR).
convertPendingAssignations(
	[CAR | CDR],
	[CAR | NCDR]) :-
		convertPendingAssignations(CDR, NCDR).



/*
 * The checkEXProperty clause is only partially valid: It should test
 * that an aggregate is not used in no other whole AND no other 'extern'
 * method access or variable assignation!
 * Actually, if we make the hypothesis that all possible uses of the
 * component instances are being monitored, then this predicate is valid.
 */
checkEXProperty(
	assignation(_, A, AID, B, BID),
	LIST,
	NLIST) :-
		updateEXProperties(A, AID, B, BID, LIST, NLIST, true).
checkEXProperty(_, LIST, LIST).



updateEXProperties(
	A, AID, B, BID,
	[],
	[exclusivityProperty(A, AID, B, BID, true)],
	true).
updateEXProperties(
	A, AID, B, BID,
	[],
	[exclusivityProperty(A, AID, B, BID, false)],
	false).
updateEXProperties(
	A, AID, B, BID,
	[exclusivityProperty(A0, AID0, B, BID, true) | CDR],
	[exclusivityProperty(A0, AID0, B, BID, false) | NCDR],
	EX) :-
		updateEXProperties(A, AID, B, BID, CDR, NCDR, false).
updateEXProperties(
	A, AID, B, BID,
	[exclusivityProperty(A0, AID0, B0, BID0, true) | CDR],
	[exclusivityProperty(A0, AID0, B0, BID0, true) | NCDR],
	EX) :-
		updateEXProperties(A, AID, B, BID, CDR, NCDR, EX).
updateEXProperties(
	A, AID, B, BID,
	[CAR | CDR],
	[CAR | NCDR],
	EX) :-
		updateEXProperties(A, AID, B, BID, CDR, NCDR, false).



/*
 * This predicate returns a list of class-level compositions,
 * from a list of instance-level compositions.
 */
classLevelCompositions(LIST, NLIST) :-
	classLevelCompositions(LIST, [], NLIST).

classLevelCompositions([], LIST, LIST).
classLevelCompositions([CAR | CDR], LIST, NNLIST) :-
	classLevelCompositions0(CAR, LIST, NLIST),
	classLevelCompositions(CDR, NLIST, NNLIST).

classLevelCompositions0(
	composition(A, AID, B, BID, ISCOMPO),
	LIST,
	NLIST) :-
		updateClassLevelCompositions(
			composition(A, AID, B, BID, ISCOMPO),
			LIST,
			NLIST).
classLevelCompositions0(_, LIST, LIST).

updateClassLevelCompositions(
	composition(A, _, B, _, ISCOMPO),
	[],
	[composition(A, B, ISCOMPO)]).
updateClassLevelCompositions(
	composition(A, AID, B, BID, ISCOMPO),
	[composition(A, B, ISCOMPO) | CDR],
	NCDR) :-
		updateClassLevelCompositions(
			composition(A, AID, B, BID, ISCOMPO),
			CDR,
			NCDR).
updateClassLevelCompositions(
	composition(A, AID, B, BID, ISCOMPO1),
	[composition(A, B, ISCOMPO2) | CDR],
	NCDR) :-
		updateClassLevelCompositions(
			composition(A, AID, B, BID, false),
			CDR,
			NCDR).
updateClassLevelCompositions(
	composition(A, AID, B, BID, ISCOMPO),
	[composition(A0, B0, ISCOMPO0) | CDR],
	[composition(A0, B0, ISCOMPO0) | NCDR]) :-
		updateClassLevelCompositions(
			composition(A, AID, B, BID, ISCOMPO),
			CDR,
			NCDR).



/*
 * A simple pretty-printer predicate.
 */
display([]).
display([CAR | CDR]) :-
	write('    '), 
	write(CAR), nl,
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
main(N1, NLIST) :-
	ms(instanceLevelCompositions(LIST), TIME),
	display('List of instance-level compositions', LIST),
	classLevelCompositions(LIST, NLIST),
	display('List of class-level compositions', NLIST),
	write('In '),
	write(TIME),
	write(' ms.'),
	nl.
main(N1, N1).
