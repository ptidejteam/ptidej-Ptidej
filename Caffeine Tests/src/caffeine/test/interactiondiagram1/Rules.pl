query :- detect(during(method(foo),then(method(bar),method(zorg)))).

detect(method(M)) :- 
	nextMethodEntryEvent(_, M, _, _, _),
	write('Method '), write(M), nl,
	nextMethodExitEvent(_, M, _, _, _, _).
detect(then(P1, P2)) :- 
	detect(P1),
	detect(P2).
detect(during(method(M), P2)) :-
	nextMethodEntryEvent(_, M, _, _, _),
	write('During '), write(M), nl,
	detect(P2),
	nextMethodExitEvent(_, M, _, _, _, _).

main(N, M) :-
	query,
	!,
	N1 is N + 1,
	main(N1, M).
main(N, N).