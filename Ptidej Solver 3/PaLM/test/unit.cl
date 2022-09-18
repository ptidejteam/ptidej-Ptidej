// Unit tests for PaLM 

PALM_TESTS: integer := 0



[testConstraintPropagation() : void
 -> let testOK := false,
 		pb := palm/makePalmProblem("test - propagation", 5),
		a  := palm/makeBoundIntVar(pb, "a", 1, 3),
		b  := palm/makeBoundIntVar(pb, "b", 1, 3),
		c  := palm/makeBoundIntVar(pb, "c", 1, 3),
		ct1 := a > b,
		ct2 := a > c,
		ct3 := b > c
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		testOK := (palm/getInf(a) = 2 & palm/getSup(b) = 2),
		palm/post(pb, ct2), palm/propagate(pb),
		testOK := testOK & (palm/getSup(c) = 2),
		palm/post(pb, ct3), palm/propagate(pb),
		testOK := testOK & (palm/getInf(a) = 3 & palm/getInf(b) = 2 & palm/getSup(c) = 1),
		
		//[PALM_TESTS] Constraint propagation                       => ~S // testOK
	)]


[testConstraintPropagationEnum() : void
 -> let testOK := false,
 		pb := palm/makePalmProblem("test - propagation - enum", 5),
		a  := palm/makeIntVar(pb, "a", 1, 3),
		b  := palm/makeIntVar(pb, "b", 1, 3),
		c  := palm/makeIntVar(pb, "c", 1, 3),
		ct1 := a > b,
		ct2 := a > c,
		ct3 := b > c
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		testOK := (palm/getInf(a) = 2 & palm/getSup(b) = 2),
		palm/post(pb, ct2), palm/propagate(pb),
		testOK := testOK & (palm/getSup(c) = 2),
		palm/post(pb, ct3), palm/propagate(pb),
		testOK := testOK & (palm/getInf(a) = 3 & palm/getInf(b) = 2 & palm/getSup(c) = 1),
		
		//[PALM_TESTS] Constraint propagation enumerated variables  => ~S // testOK
	)]

[testConstraintRemoval() : void
 -> let testOK := false,
		pb := palm/makePalmProblem("test - removal", 5),
		a  := palm/makeBoundIntVar(pb, "a", 1, 3),
		b  := palm/makeBoundIntVar(pb, "b", 1, 3),
		c  := palm/makeBoundIntVar(pb, "c", 1, 3),

		ct1 := a > b,
		ct2 := a > c,
		ct3 := b > c
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		palm/post(pb, ct2), palm/propagate(pb),
		palm/post(pb, ct3), palm/propagate(pb),
		palm/remove(pb, ct1), 
		palm/propagate(pb),
		testOK := (palm/getInf(a) = 2 & palm/getSup(a) = 3
				  & palm/getInf(b) = 2 & palm/getSup(b) = 3
				  & palm/getInf(c) = 1 & palm/getSup(c) = 2),

		//[PALM_TESTS] Constraint removal                           => ~S // testOK
	)]

[testConstraintRemovalEnum() : void
 -> let testOK := false,
		pb := palm/makePalmProblem("test - removal - enum", 5),
		a  := palm/makeIntVar(pb, "a", 1, 3),
		b  := palm/makeIntVar(pb, "b", 1, 3),
		c  := palm/makeIntVar(pb, "c", 1, 3),

		ct1 := a > b,
		ct2 := a > c,
		ct3 := b > c
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		palm/post(pb, ct2), palm/propagate(pb),
		palm/post(pb, ct3), palm/propagate(pb),
		palm/remove(pb, ct1), 
		palm/propagate(pb),
		testOK := (palm/getInf(a) = 2 & palm/getSup(a) = 3
				  & palm/getInf(b) = 2 & palm/getSup(b) = 3
				  & palm/getInf(c) = 1 & palm/getSup(c) = 2),

		//[PALM_TESTS] Constraint removal enumerated variables      => ~S // testOK
	)]

[testConstraintRelaxation() : void
 -> let testOK := false,
		pb := palm/makePalmProblem("test - relaxation", 5, 5),
		a  := palm/makeBoundIntVar(pb, "a", 1, 3),
		b  := palm/makeBoundIntVar(pb, "b", 1, 3),
		c  := palm/makeBoundIntVar(pb, "c", 1, 3),

		ct1 := a > b,
		ct2 := b > c,
		ct3 := c > a
	in (
		try (
			palm/post(pb, ct1, 2), palm/propagate(pb),
			palm/post(pb, ct2, 5), palm/propagate(pb),
			testOK := (a.value = 3 & b.value = 2 & c.value = 1),
			palm/post(pb, ct3, 3), palm/propagate(pb)
		)
		catch palm/PalmContradiction (
			palm/repair(pb)
		),
		testOK := testOK & (a.value = 1 & b.value = 3 & c.value = 2),

		//[PALM_TESTS] Constraint relaxation                        => ~S // testOK
	)]


[testConstraintRelaxationEnum() : void
 -> let testOK := false,
		pb := palm/makePalmProblem("test - relaxation - enum", 5, 5),
		a  := palm/makeIntVar(pb, "a", 1, 3),
		b  := palm/makeIntVar(pb, "b", 1, 3),
		c  := palm/makeIntVar(pb, "c", 1, 3),

		ct1 := a > b,
		ct2 := b > c,
		ct3 := c > a
	in (
		try (
			palm/post(pb, ct1, 2), palm/propagate(pb),
			palm/post(pb, ct2, 5), palm/propagate(pb),
			testOK := (a.value = 3 & b.value = 2 & c.value = 1),
			palm/post(pb, ct3, 3), palm/propagate(pb)
		)
		catch palm/PalmContradiction (
			palm/repair(pb)
		),
		testOK := testOK & (a.value = 1 & b.value = 3 & c.value = 2),

		//[PALM_TESTS] Constraint relaxation enumerated variables   => ~S // testOK
	)]

[testConstraintRelaxationDelay() : void
 -> let testOK := false,
		pb := palm/makePalmProblem("test - relaxation - delay", 5, 5),
		a  := palm/makeBoundIntVar(pb, "a", 1, 3),
		b  := palm/makeBoundIntVar(pb, "b", 1, 3),
		c  := palm/makeBoundIntVar(pb, "c", 1, 3),

		ct1 := palm/delay(a > b, 2),
		ct2 := palm/delay(b > c, 2),
		ct3 := palm/delay(c > a, 2)
	in (
		try (
			palm/post(pb, ct1, 2), palm/propagate(pb),
			palm/post(pb, ct2, 5), palm/propagate(pb),
			testOK := (a.value = 3 & b.value = 2 & c.value = 1),
			palm/post(pb, ct3, 3), palm/propagate(pb)
		)
		catch palm/PalmContradiction (
			palm/repair(pb)
		),
		testOK := testOK & (a.value = 1 & b.value = 3 & c.value = 2),

		//[PALM_TESTS] Constraint relaxation w/ delayed constraint  => ~S // testOK
	)]

[testLinComb() : void
 -> let pb := palm/makePalmProblem("test - lincomb", 3),
		h12   := palm/makeIntVar(pb, "h12", 0, 1),
		v12   := palm/makeIntVar(pb, "v12", 0, 1),
		beta  := palm/makeBoundIntVar(pb, "beta", -1000, 1000),
		ct1 := beta >= -1000 * h12 + -1000 * v12 + 649,
		testOK := false
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		// nothing should have changed
		testOK := (palm/getInf(h12) = 0 & palm/getInf(v12) = 0 & 
				   palm/getSup(h12) = 1 & palm/getSup(h12) = 1 &	
				   palm/getInf(beta) = -1000),
			
		palm/post(pb, h12 == 0), palm/propagate(pb),
		testOK := testOK & (palm/getInf(beta) = -351),

		palm/post(pb, v12 == 0), palm/propagate(pb),
		testOK := testOK & (palm/getInf(beta) = 649),
		//[PALM_TESTS] Linear combination of variables              => ~S // testOK
	)]

LCAS :: list(1,3,6,10,15,21,28,36,45) 

[testLinCombAllSolutions(v: integer) 
 -> let testOK := false,
		pb := palm/makePalmProblem("demo - lincomb", v),
		vars := list<palm/PalmIntVar>()
	in (
		for i in (1 .. v) vars :add palm/makeBoundIntVar(pb, "x" /+ string!(i), 1, 3),
		let ct := make_list(v, integer, 1) palm/e-scalar vars == v + 2 
		in (
			palm/post(pb, ct)
		),		
		palm/propagate(pb),
		palm/setSolutionVars(pb, vars), 
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = LCAS[v]),

		//[PALM_TESTS] Linear combination of variables (exp ~S all)  => ~S // v, testOK
	)]

[testAC4Constraints() 
 -> let testOK := false,
		pb := palm/makePalmProblem("demo ac4", 4),
		x  := palm/makeIntVar(pb, "x", 0, 3),
		y  := palm/makeIntVar(pb, "y", 0, 3),
		ct := palm/makeAC4constraint(x, y, true, list<list<integer>>(list<integer>(1,3),list<integer>(2,3),list<integer>(1,2)))
	in (
		palm/post(pb, ct), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 3),
	
		//[PALM_TESTS] AC4 like constraints                         => ~S // testOK
	)]


[testOR() 
 -> let testOK := false
	in (
		let pb := palm/makePalmProblem("test-eor", 4),
			x := palm/makeBoundIntVar(pb, "x", 1, 3),
			y := palm/makeBoundIntVar(pb, "y", 1, 3),
			z := palm/makeBoundIntVar(pb, "z", 1, 3),
			ct1 := (x < y) e-or (y < x),
			ct2 := (y < z) e-or (z < y)
		in (
			palm/post(pb, ct1), palm/propagate(pb),
			palm/post(pb, ct2), palm/propagate(pb),
			palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
			palm/solve(pb, true),
			testOK := (length(palm/solutions(pb)) = 12)
		),
		//[PALM_TESTS] boolean constraint (OR)                      => ~S // testOK,
		testOK := false,
		let pb := palm/makePalmProblem("test-eor-enum", 4),
			x := palm/makeIntVar(pb, "x", 1, 3),
			y := palm/makeIntVar(pb, "y", 1, 3),
			z := palm/makeIntVar(pb, "z", 1, 3),
			ct1 := (x < y) e-or (y < x),
			ct2 := (y < z) e-or (z < y)
		in (
			palm/post(pb, ct1), palm/propagate(pb),
			palm/post(pb, ct2), palm/propagate(pb),
			palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
			palm/solve(pb, true),
			testOK := (length(palm/solutions(pb)) = 12)
		),
		//[PALM_TESTS] boolean constraint (OR enumerated vars)      => ~S // testOK
	)]

[testGuard() 
 -> let testOK := false,
		pb := palm/makePalmProblem("test-guard", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 2),
		y := palm/makeBoundIntVar(pb, "y", 1, 2),
		z := palm/makeBoundIntVar(pb, "z", 1, 2),
		ct := palm/e-ifThen(x <= y, x <= z) // (x <= y) palm/e-implies (x <= z) // palm/e-ifThen(x <= y, x <= z)
	in (
		palm/post(pb, ct), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 7), 
		//[PALM_TESTS] boolean constraint (GUARD)                   => ~S // testOK
	)]

[testImplies() 
 -> let testOK := false,
		pb := palm/makePalmProblem("test - implies", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 2),
		y := palm/makeBoundIntVar(pb, "y", 1, 2),
		z := palm/makeBoundIntVar(pb, "z", 1, 2),
		ct := (x <= y) palm/e-implies (x <= z) 
	in (
		palm/post(pb, ct), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 7), 
		//[PALM_TESTS] boolean constraint (IMPLIES)                 => ~S // testOK
	)]

[testIff()
 -> let testOK := false,
		pb := palm/makePalmProblem("test - iff", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 3),
		y := palm/makeBoundIntVar(pb, "y", 1, 3),
		z := palm/makeBoundIntVar(pb, "z", 1, 3),
		ct1 := (x < y) e-iff (y < z)
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 11),
		//[PALM_TESTS] boolean constraint (IFF)                     => ~S // testOK
	)]

[testAnd()
 -> let testOK := false,
		pb := palm/makePalmProblem("test - and", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 3),
		y := palm/makeBoundIntVar(pb, "y", 1, 3),
		z := palm/makeBoundIntVar(pb, "z", 1, 3),
		ct1 := (x < 2) e-implies ((x < y) e-and (y < z))
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 19),
		//[PALM_TESTS] boolean constraint (AND)                     => ~S // testOK
	)]



[testLargeOr()
 -> let testOK := false,
		pb := palm/makePalmProblem("demo leor", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 2),
		y := palm/makeBoundIntVar(pb, "y", 1, 2),
		z := palm/makeBoundIntVar(pb, "z", 1, 2),
		ct1 := (x < y) e-or (x < z) e-or (y < z)
	in (
		palm/post(pb, ct1), palm/propagate(pb),
		palm/setSolutionVars(pb, list<palm/PalmIntVar>(x,y,z)),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 4),
		//[PALM_TESTS] boolean constraint (LARGE OR)                => ~S // testOK
	)]



[testElement()
 -> let testOK := false
	in (
		let pb := palm/makePalmProblem("test - element - 1",10),
			i  := palm/makeBoundIntVar(pb,"I",0,10),
			v  := palm/makeBoundIntVar(pb,"V",0,10) 
		in (
			palm/setSolutionVars(pb, list<palm/PalmIntVar>(i,v)),
			palm/post(pb, palm/getNth(list(7,5,4,10),i + 1) == v),
			palm/solve(pb,true),
			testOK := (length(palm/solutions(pb)) = 4),
			//[PALM_TESTS] Element with offset in index                 => ~S // testOK
		),
		let pb := palm/makePalmProblem("test - element - 2",10),
			i  := palm/makeBoundIntVar(pb,"I",-20,20),
			v  := palm/makeBoundIntVar(pb,"V",0,10),
			l  := list(12,5,8) 
		in (
			palm/post(pb, (i < 0) e-or (v == palm/getNth(l,i))), palm/propagate(pb),
			palm/setSolutionVars(pb, list<palm/PalmIntVar>(i,v)),
			palm/solve(pb,true),
			testOK := (length(palm/solutions(pb)) = 222),
			//[PALM_TESTS] Element inside a disjunction                 => ~S // testOK
		),	
		let pb := palm/makePalmProblem("test - element - 3",10),
			i  := palm/makeIntVar(pb,"I",-20,20),
			v  := palm/makeIntVar(pb,"V",0,10),
			l  := list(6,5,8) 
		in (
			palm/post(pb,v == palm/getNth(l,i)), // propagate(pb), 
			palm/setSolutionVars(pb, list<palm/PalmIntVar>(i,v)),
			palm/solve(pb,true),
			testOK := (length(palm/solutions(pb)) = 3),
			//[PALM_TESTS] Element with enumerated variables            => ~S // testOK
		)	
	)]

[testOccur()
 -> let testOK := false,	
		pb := palm/makePalmProblem("test - occur", 4),
		x := palm/makeBoundIntVar(pb, "x", 1, 4),
		y := palm/makeBoundIntVar(pb, "y", 1, 4),
		z := palm/makeBoundIntVar(pb, "z", 1, 4),
		t := palm/makeBoundIntVar(pb, "t", 1, 4),
		ct := palm/occur(2, list(x,y,z)) == t
	in (
		palm/post(pb, ct), palm/propagate(pb),
		palm/solve(pb, true),
		testOK := (length(palm/solutions(pb)) = 37),
		//[PALM_TESTS] Occurence constraint                         => ~S // testOK
	)]

[fact(n:integer) : integer
 -> if (n <= 1) 1 else n * fact(n - 1)]


[testAllDifferent1(n:integer)
 -> let testOK := false,
		pb := palm/makePalmProblem("test - alldiff - 1",n),
		x := list{palm/makeIntVar(pb,"x" /+ string!(i),1,n) | i in (1 .. n)} 
	in (
		palm/post(pb,palm/e-allDifferent(x)),
		palm/solve(pb, true),
		testOK := (length(list{s in palm/solutions(pb) |s.palm/feasible}) = fact(n)),
		//[PALM_TESTS] AllDifferent - permutations of (1 .. n)[~S]   => ~S // n, testOK
	)]



[testAllDifferent2(n:integer, a:integer)
 -> let testOK := false,
		pb := palm/makePalmProblem("test - alldiff - 2",n),
        x := list{palm/makeIntVar(pb,"x" /+ string!(i),a + 1, a + n) | i in (1 .. n)} 
	in (
		palm/post(pb,palm/e-allDifferent(x)),
		palm/solve(pb, true),
		testOK := (length(list{s in palm/solutions(pb) |s.palm/feasible}) = fact(n)),
		//[PALM_TESTS] AllDifferent - perm. of (a + 1, a + n)[~S,~S]  => ~S // n, a, testOK
	)]




[testAllDifferent3(n:integer,m:integer,a:integer)
 -> let testOK := false,
		pb := palm/makePalmProblem("test - alldiff - 3",n),
        x := list{palm/makeIntVar(pb,"x" /+ string!(i),a + 1, a + m) | i in (1 .. n)} 
	in (
		palm/post(pb,e-allDifferent(x)),
		palm/solve(pb, true),
		testOK := (length(list{s in palm/solutions(pb) |s.palm/feasible}) = fact(m) / fact(m - n)),
		//[PALM_TESTS] AllDifferent - comb. of ~S values among ~S     => ~S // n, m, testOK
	)]
 

[testPermutation(n:integer)
 -> let testOK := false,
		pb := palm/makePalmProblem("test all diff",2 * n),
        x := list{palm/makeIntVar(pb,"x" /+ string!(i),1,n) | i in (1 .. n)},
        y := list{palm/makeIntVar(pb,"y" /+ string!(i),1,n) | i in (1 .. n)} 
	in (
		palm/post(pb,e-permutation(x,y)),
		palm/setSolutionVars(pb, x /+ y),
		palm/solve(pb, true), 
		testOK := (length(list{s in palm/solutions(pb) |s.palm/feasible}) = fact(n)),
		//[PALM_TESTS] Permutation - perm. of (1 .. n)[~S]           => ~S // n, testOK
     )]


[allPalmTests() : void
 -> //[PALM_TESTS] Testing PaLM basic functionnalities // ,
	PalmVIEW := 4, 
	testConstraintPropagation(),
	testConstraintPropagationEnum(),
	testConstraintRemoval(),
	testConstraintRemovalEnum(),
	testConstraintRelaxation(),
	testConstraintRelaxationEnum(),
	testConstraintRelaxationDelay(),
	testLinComb(),
	testLinCombAllSolutions(1),
	testLinCombAllSolutions(5),
	testLinCombAllSolutions(9),
	testAC4Constraints(),
	testOR(),
	testGuard(),
	testImplies(), 
	testIff(),
	testAnd(),
	testLargeOr(),
	testElement(),
	testOccur(), 
	testAllDifferent1(2),
	testAllDifferent1(5),
	testAllDifferent2(4, 3),
	testAllDifferent3(4,6,2),
	testPermutation(2),
	testPermutation(5)
	]

