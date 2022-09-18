between(Low, High, _) :-
    Low > High,
    !,
    fail.
between(Low, High, Low).
between(Low, High, Val) :-
    Now is Low + 1,
    between(Now, High, Val).

distinct([]).
distinct([X|Xs]) :- 
	not(is_member(X, Xs)),
	distinct(Xs).

solution(S, E, N, D, M, O, R, Y) :-
    between(0, 9, D), between(0, 9, E),
    Y is (D + E) mod 10,
    C1 is (D + E) // 10,
    between(0, 9, N), between(0, 9, R),
    E is (N + R + C1) mod 10,
    C2 is (N + R + C1) // 10,
    between(0, 9, O),
    N is (E + O + C2) mod 10,
    C3 is (E + O + C2) // 10,
    between(0, 9, S), between(1, 9, M),
    O is (S + M + C3) mod 10,
    M is (S + M + C3) // 10,
    distinct([S, E, N, D, M, O, R, Y]).
