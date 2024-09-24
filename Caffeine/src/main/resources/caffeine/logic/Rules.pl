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

/*
 * The list of all possible JVM-generated events follows.
 * (See Caffeine class.)
 */
generateNoEvents.
	% Tells the JVM to generate no event.
generateClassLoadEvent.
	% Tells the JVM to generate an event for each loaded class.
generateClassUnloadEvent.
	% Tells the JVM to generate an event for each unloaded class.
generateCollectionEvent.
	% Tells the JVM to generate an event when collection are accessed.
generateConstructorEntryEvent.
	% Tells the JVM to generate an event for each entered constructor.
generateConstructorExitEvent.
	% Tells the JVM to generate an event for each exited constructor.
generateFieldAccessEvent.
	% Tells the JVM to generate an event each time a field
	% (specified as parameter of the Caffeine.run() method)
	% is accessed.
generateFieldModificationEvent.
	% Tells the JVM to generate an event each time a field
	% (specified as parameter of the Caffeine.run() method)
	% is modified.
generateFinalizerEntryEvent.
	% Tells the JVM to generate an event for each entered finalizer.
generateFinalizerExitEvent.
	% Tells the JVM to generate an event for each exited finalizer.
generateMethodEntryEvent.
	% Tells the JVM to generate an event for each entered method.
generateMethodExitEvent.
	% Tells the JVM to generate an event for each exited method.
generateMethodReturnedValueEvent.
	% Tells the JVM to compute the returned value of the methods.
generateProgramEndEvent.
	% Tells the JVM to generate an event when the program ends.

%-----------------------------------------------------------------------------

% The following list present the signatures of the JVM-generated events.
% (See Caffeine class.)
% 		classLoad0(
%			<Event unique ID>,
%			<Class name>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
% 		classUnload0(
%			<Event unique ID>,
%			<Class name>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
% 		fieldAccess0(
%			<Event unique ID>,
%			<Field name>,
%			<Unique ID of the instance possessing this field>,
%			<Fully qualified name of the class of the possessing instance>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
% 		fieldModification0(
%			<Event unique ID>,
%			<Field name>,
%			<Unique ID of the instance possessing this field>,
%			<Unique ID of the new object assigned to this field>,
%			<Fully qualified name of the class of the possessing instance>,
%			<Fully qualified name of the class of the assigned object>,
%			<Source name>,
%			<Line number>).
%		constructorEntry0(
%			<Event unique ID>,
%			<Declaring class name>,
%			<Unique ID of the newly created instance>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
%		finalizeEntry0(
%			<Event unique ID>,
%			<Declaring class name>,
%			<Unique ID of the being-finalized instance>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
%		methodEntry0(
%			<Event unique ID>,
%			<Method name>,
%			<Unique ID of the caller instance>,
%			<Unique ID of the receiver instance>,
%			<Fully-qualified name of the class declaring this method>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
%		constructorExit0(
%			<Event unique ID>,
%			<Declaring class name>,
%			<Unique ID of the newly created instance>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
%		finalizeExit0(
%			<Event unique ID>,
%			<Declaring class name>,
%			<Unique ID of the being-finalized instance>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Source name>,
%			<Line number>).
%		methodExit0(
%			<Event unique ID>,
%			<Method name>,
%			<Unique ID of the caller instance>,
%			<Unique ID of the receiver instance>,
%			<Fully-qualified name of the class declaring this method>,
%			<The value returned by this method>,
%			<Source name>,
%			<Line number>).
%		programEnd0(
%			<Event unique ID>,
%			<Unused>,
%			<Unused>,
%			<Unused>,
%			<Name of the exiting thread>,
%			<Unused>,
%			<Unused>,
%			<Unused>).

% nextEvent0/2: nexEvent0(<List of desired event types>, <JVM-generated event>).
nextEvent0(LEE, E) :-
	xcall("caffeine.logic.EventManager", LEE, E),
	% write(E), nl,
	true.

% nextEvent/1: nexEvent(<JVM-generated event>).
nextEvent(TE) :-
	nextEvent0([1], E),
	translate(E, TE).


% nextEvent/2: nexEvent(<List of desired event types>, <JVM-generated event>).
nextEvent(LEE, TE) :-
	nextEvent0([2 | LEE], E),
	translate(E, TE).

% nextEvent/3:
%	nexEvent(
%		<List of desired positive filters>,
%		<List of desired event types>,
%		<JVM-generated event>).
% WARNING: The EventManager assumes that the list of
% positive filters contains a unique (1) class name!
nextEvent(LPF, LEE, TE) :-
	nextEvent0([3, LPF, LEE], E),
	translate(E, TE).

translate(
	fieldAccess0(EID, FIELDNAME, IID, _, ICLASSNAME, _, _, _),
	fieldAccess(EID, FIELDNAME, IID, ICLASSNAME)) :- !.
translate(
	fieldModification0(EID, FIELDNAME, IID, VID, ICLASSNAME, VCLASSNAME, _, _),
	fieldModification(EID, FIELDNAME, IID, VID, ICLASSNAME, VCLASSNAME)) :- !.
translate(
	classLoad0(EID, CLASSNAME, _, _, _, _, _, _),
	classLoad(EID, CLASSNAME)) :- !.
translate(
	classUnload0(EID, CLASSNAME, _, _, _, _, _, _),
	classUnload(EID, CLASSNAME)) :- !.
translate(
	constructorEntry0(EID, CLASSNAME, IID, _, _, _, _, _),
	constructorEntry(EID, CLASSNAME, IID)) :- !.
translate(
	constructorExit0(EID, CLASSNAME, IID, _, _, _, _, _),
	constructorExit(EID, CLASSNAME, IID)) :- !.
translate(
	finalizerEntry0(EID, CLASSNAME, IID, _, _, _, _, _),
	finalizerEntry(EID, CLASSNAME, IID)) :- !.
translate(
	finalizerExit0(EID, CLASSNAME, IID, _, _, _, _, _),
	finalizerExit(EID, CLASSNAME, IID)) :- !.
translate(
	methodEntry0(EID, METHODNAME, CID, RID, CLASSNAME, _, _, _),
	methodEntry(EID, METHODNAME, CID, RID, CLASSNAME)) :- !.
translate(
	methodExit0(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE, _, _),
	methodExit(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE)) :- !.
translate(
	programEnd0(EID, _, _, _, CLASSNAME, _, _, _),
	programEnd(EID, CLASSNAME)) :- !.
translate(E, E).

%-----------------------------------------------------------------------------

% The following clauses are helpers to simplify the Prolog files.
nextConstructorEntryEvent(EID, CLASSNAME, IID) :-
	nextEvent([generateConstructorEntryEvent], E),
	E = constructorEntry(EID, CLASSNAME, IID).

nextConstructorExitEvent(EID, CLASSNAME, IID) :-
	nextEvent([generateConstructorExitEvent], E),
	E = constructorExit(EID, CLASSNAME, IID).

nextFinalizerEntryEvent(EID, CLASSNAME, IID) :-
	nextEvent([generateFinalizerEntryEvent], E),
	E = finalizerEntry(EID, CLASSNAME, IID).

nextFinalizerExitEvent(EID, CLASSNAME, IID) :-
	nextEvent([generateFinalizerExitEvent], E),
	E = finalizerExit(EID, CLASSNAME, IID).

nextMethodEntryEvent(EID, METHODNAME, CID, RID, CLASSNAME) :-
	nextEvent([generateMethodEntryEvent], E),
	E = methodEntry(EID, METHODNAME, CID, RID, CLASSNAME).

nextMethodExitEvent(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE) :-
	nextEvent([generateMethodExitEvent], E),
	E = methodExit(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE).

nextConstructorEntryEvent(FILTER, EID, CLASSNAME, IID) :-
	nextEvent(FILTER, [generateConstructorEntryEvent], E),
	E = constructorEntry(EID, CLASSNAME, IID).

nextConstructorExitEvent(FILTER, EID, CLASSNAME, IID) :-
	nextEvent(FILTER, [generateConstructorExitEvent], E),
	E = constructorExit(EID, CLASSNAME, IID).

nextFinalizerEntryEvent(FILTER, EID, CLASSNAME, IID) :-
	nextEvent(FILTER, [generateFinalizerEntryEvent], E),
	E = finalizerEntry(EID, CLASSNAME, IID).

nextFinalizerExitEvent(FILTER, EID, CLASSNAME, IID) :-
	nextEvent(FILTER, [generateFinalizerExitEvent], E),
	E = finalizerExit(EID, CLASSNAME, IID).

nextMethodEntryEvent(FILTER, EID, METHODNAME, CID, RID, CLASSNAME) :-
	nextEvent(FILTER, [generateMethodEntryEvent], E),
	E = methodEntry(EID, METHODNAME, CID, RID, CLASSNAME).

nextMethodExitEvent(FILTER, EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE) :-
	nextEvent(FILTER, [generateMethodExitEvent], E),
	E = methodExit(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE).

%-----------------------------------------------------------------------------

nextConstructorEntryStaticEvent(EID, CLASSNAME, IID) :-
	nextEvent(E),
	E = constructorEntry(EID, CLASSNAME, IID).

nextConstructorExitStaticEvent(EID, CLASSNAME, IID) :-
	nextEvent(E),
	E = constructorExit(EID, CLASSNAME, IID).

nextFinalizerEntryStaticEvent(EID, CLASSNAME, IID) :-
	nextEvent(E),
	E = finalizerEntry(EID, CLASSNAME, IID).

nextFinalizerExitStaticEvent(EID, CLASSNAME, IID) :-
	nextEvent(E),
	E = finalizerExit(EID, CLASSNAME, IID).

nextMethodEntryStaticEvent(EID, METHODNAME, CID, RID, CLASSNAME) :-
	nextEvent(E),
	E = methodEntry(EID, METHODNAME, CID, RID, CLASSNAME).

nextMethodExitStaticEvent(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE) :-
	nextEvent(E),
	E = methodExit(EID, METHODNAME, CID, RID, CLASSNAME, RETURNEDVALUE).

%-----------------------------------------------------------------------------

% This is a helper clause, to build a list without redundancy.
addIfNew(OBJ, [], [OBJ]).
addIfNew(OBJ, [OBJ | REST], [OBJ | REST]).
addIfNew(OBJ, [ANY | REST], [ANY | NREST]) :- addIfNew(OBJ, REST, NREST).

% This is a helper clause, to remove a given element from a given list.
removeElement(E, L, NL) :-
	removeElement0(E, L, [], NL).

removeElement0(_, [], NLE, NLE).
removeElement0(E, [E | REST], HEAD, NNLE) :-
	removeElement0(E, REST, HEAD, NNLE).
removeElement0(E, [O | REST], HEAD, NNLE) :-
	removeElement0(E, REST, [O | HEAD], NNLE).

%-----------------------------------------------------------------------------

/*
 * Such file should be used together with the package JIPxerr.zip
 * Defined custom predicates:
 *  	catch/2
 *  	catch/3
 *  	throw/1
 *  	throw/2
 * Author:  Ugo Chirico
 * version: 12/02/02
 */

catch(Error, Goal):-
	xcall("JIP.extensions.errors.JIPCatch2", Goal, Error).

catch(Goal, Mask, RecoverGoal):-
	xcall("JIP.extensions.errors.JIPCatch3", [Goal, Mask, RecoverGoal], _).

throw(Error, Goal):-
	xcall("JIP.extensions.errors.JIPThrow2", [Error, Goal], _).

throw(Goal):-
	xcall("JIP.extensions.errors.JIPThrow1", Goal, _).
