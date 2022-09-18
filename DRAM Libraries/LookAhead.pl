/*
 * (c) Copyright 2002-2003 Yann-Gaël Guéhéneuc,
 * École des Mines de Nantes and Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
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
	%nextMethodEntryStaticEvent(_, setQueen, _, _, _),	
	%nextPlaceNextQueen,
	%nextMethodEntryStaticEvent(_, removeQueen, _, _, _),
	
	nextConstructorEntryStaticEvent(_, _, _),
	nextMethodEntryStaticEvent(_, _, _, _, _),
	nextMethodExitStaticEvent(_, _, _, _, _, _),
	nextConstructorExitStaticEvent(_, _, _),
	
	%nextFinalizerEntryStaticEvent(_, _, _),
	%nextFinalizerExitStaticEvent(_, _, _),
	
	
	
	
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
