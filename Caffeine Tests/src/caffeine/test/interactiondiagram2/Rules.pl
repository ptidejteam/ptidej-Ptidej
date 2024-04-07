query :- detect(during(method(foo), then(method(bar), method(zorg))), []).

detect(method(M), LFE) :-
	write(method(M)), nl,
	nextQualifiedEvent(methodEntry(_, M, _, _, _), LFE),
	nextQualifiedEvent(methodExit(_, M, _, _, _, _), LFE).
	
detect(then(P1, P2), LFE) :-
	write(then(P1, P2)), nl,
	detect(P1, LFE),
	detect(P2, LFE).

detect(during(method(M), P2), LFE) :-
	write(during(method(M))), nl,
	nextQualifiedEvent(methodEntry(_,M, _, _, _), LFE),
	detect(P2, [methodExit(_, M, _, _, _, _) | LFE]),
	nextQualifiedEvent(methodExit(_, M, _, _, _, _), LFE).

nextQualifiedEvent(X, LFE) :-
	nextEvent(Y),
	write(Y), nl,
	checkNextQualifiedEvent(Y, LFE, X).

checkNextQualifiedEvent(Y, LFE, X) :-
	member(Y, LFE),
	nl, write(Y), nl, write(' belongs to '), write(LFE), nl.
checkNextQualifiedEvent(Y, LFE, Y).

main(N, M) :-
	query,
	!,
	N1 is N + 1,
	main(N1, M).
main(N, N).
