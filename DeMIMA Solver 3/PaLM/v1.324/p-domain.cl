// Implémentation d'un système d'explication en claire/choco
// (c) 2000 - Vincent Barichard - EMN 
// (c) 2001 - Narendra Jussien - EMN
// Système PaLM (gestion des domaines en extension)


// ** Summary : handling domains for PaLM
// *   Part 1 : Tools 
// *   Part 2 : API
// *   Part 3 : Interface for propagation



// ********************************************************************
// *   Part 1 : Tools                                                 *
// ********************************************************************

[not!(x:integer) : integer -> (PALM_BITALLONE - x)]
[sum(l:list[integer]) : integer -> let n := 0 in (for y in l n :+ y, n)]


// binary representation => integer
[firstCode29bits(ed:PalmIntDomain, x:integer) : integer
 => let y := (x - ed.offset - 1) in y / 29 + 1]
[secondCode29bits(ed:PalmIntDomain, x:integer) : integer
 => let y := (x - ed.offset - 1) in y mod 29]

// integer => binary representation
[decode29bits(ed:PalmIntDomain, x:integer, i:(1 .. 29)) : integer
 -> (x - 1) * 29 + i + ed.offset]


// ********************************************************************
// *   Part 2 : API                                                   *
// ********************************************************************

// Building an enumerated domain (input: inf and sup)
[makePalmIntDomain(dinf: integer, dsup: integer) : PalmIntDomain
 -> assert(dinf <= dsup),
    assert(dsup <= dinf + PALM_MAXSIZE), // Not too large domains ...
    let n := (dsup - dinf + 1) 
	in 
		PalmIntDomain(
			offset = dinf - 1,	
			bucketSize = n,
			nbElements = n,

			firstSuccPres = 1,
			firstPrecPres = n,
			firstSuccAbs = 0,
			firstPrecAbs = 0,

			booleanVector = add(list<integer>{PALM_BITALLONE | i in (1 .. (n / 29))}, sum(list<integer>{ ^2(j) | j in (0 .. (n mod 29 - 1))})),
			succVector = list<integer>{i | i in 2 .. n} add 0,
			precVector = list<integer>{i | i in 0 .. (n - 1)},

			explanationOnVal = make_list(n, (PalmRemovalExplanation U {unknown}), unknown), 
			instantiationConstraints = make_list(n, AbstractConstraint, unknown),
			negInstantiationConstraints = make_list(n, AbstractConstraint, unknown)
			
		)]

// pretty printing 
[self_print(ed:PalmIntDomain)
 -> let s := domainSet(ed),
		si := size(s)
	in (
		if (si <= 4) printf("~S", list{x | x in s})
		else printf("{~S,~S...~S,~S}",s[1],s[2],s[si - 1],s[si])
	)]



// for compatbilility with 1.07
;[domainSequence(d:PalmIntDomain) : list[integer] => list!(d)]
;[domainSet(d:PalmIntDomain) : set[integer] => set!(d)]


// PalmIntDomain => list[integer]
[domainSequence(ed:PalmIntDomain) : list<integer>
 -> let s := list<integer>(),
        z := ed.firstSuccPres 
	in (
		while (z != 0) (
			s :add (z + ed.offset),
			z := ed.succVector[z],
			//[PDEBUG] Set : Next Succ (z) : ~S // z
		),
		s
	)]

// PalmIntDomain => set[integer]
[domainSet(ed: PalmIntDomain): set<integer>
 -> let s := set<integer>(),
        z := ed.firstSuccPres 
	in (
		while (z != 0) (
			s :add (z + ed.offset),
			z := ed.succVector[z],
			//[PDEBUG] Set : Next Succ (z) : ~S // z
		),
		s
	)]


[iterate(ed: PalmIntDomain, v: Variable, e: any) 
 => let z := ed.firstSuccPres,
		o := ed.offset,
		v := z + o
	in (
		while (z != 0) (
			e,
			z := ed.succVector[z],
			v := z + o
		)
	)]
 

 


// complement(PalmIntDomain) => list[integer]
[removedlist!(ed:PalmIntDomain) : list[integer]
 -> let s := list(),
		z := ed.firstSuccAbs 
	in (
		while (z != 0) (
			s :add (z + ed.offset),
			z := ed.succVector[z],
			//[PDEBUG] Set : Next Succ (z) : ~S // z
		),
		s
	)]

// size of the domain
[getDomainCard(ed:PalmIntDomain) : integer
 => ed.nbElements]


// Testing membership of a given x
[containsValInDomain(ed:PalmIntDomain, x:integer) : boolean
 -> let w := firstCode29bits(ed, x),
        i := secondCode29bits(ed, x) in (x - ed.offset >= 1 & x - ed.offset <= ed.bucketSize & ed.booleanVector[w][i])]

// The first present element (MAXINT if empty domain)
[firstElement(ed: PalmIntDomain) : integer
 -> if (ed.firstSuccPres = 0) MAXINT
    else (ed.firstSuccPres + ed.offset) ]


// What is the smallest element present in the domain ?
[getInf(ed:PalmIntDomain) : integer
 -> let l := ed.booleanVector,
		w := 1, 
		i := 0 
	in (
		while (w <= length(l) & l[w] = 0) 
			w :+ 1,
		if (w <= length(l)) (
			while (not(l[w][i])) 
				i :+ 1,
			decode29bits(ed, w, (i + 1))
		)
		else MAXINT
	)]

// What is the greatest element present in the domain ?
[getSup(ed:PalmIntDomain) : integer
 -> let l := ed.booleanVector,
		w := length(l), 
			i := 28 
	in (
		while (w > 0 & l[w] = 0) 
			w :- 1,
		if (w > 0) (
			while (not(l[w][i])) 
				i :- 1,
			decode29bits(ed, w, (i + 1))
		)
		else MININT
	)]

// ********************************************************************
// *   Part 3 : Interface for propagation                             *
// ********************************************************************


// adding a given value (z) to the domain
[addDomainVal(ed:PalmIntDomain, z:integer) : void
 -> let x := z - ed.offset,
		w := firstCode29bits(ed, z),
        i := secondCode29bits(ed, z) 
	in (
		//[PDEBUG] +++ Av Pres ~S ~S Abs ~S ~S Tab ~S ~S--> ~S + ~S// ed.firstSuccPres, ed.firstPrecPres, ed.firstSuccAbs, ed.firstPrecAbs, ed.succVector, ed.precVector, domainSet(ed),z,
		if (x >= 1 & x <= ed.bucketSize & not(ed.booleanVector[w][i])) (
			store(ed.booleanVector,w,ed.booleanVector[w] + ^2(i), true),
			let succAbsX := ed.succVector[x],
				precAbsX := ed.precVector[x] 
			in (
				if (precAbsX = 0) (
					ed.firstSuccAbs := succAbsX, 
					if (succAbsX = 0) 
						ed.firstPrecAbs := 0 
					else store(ed.precVector, succAbsX, 0, true)
				)
				else (
					store(ed.succVector, precAbsX, succAbsX, true),
					if (succAbsX = 0) 
						ed.firstPrecAbs := precAbsX 
					else 
						store(ed.precVector, succAbsX, precAbsX, true)
				)
			)
		),
        //[PDEBUG] +++ Mi Pres ~S ~S Abs ~S ~S Tab ~S ~S--> ~S + ~S// ed.firstSuccPres, ed.firstPrecPres,ed.firstSuccAbs, ed.firstPrecAbs,  ed.succVector, ed.precVector, domainSet(ed),z,
		if (ed.firstSuccPres = 0) 
			ed.firstSuccPres := x
        else 
			store(ed.succVector, ed.firstPrecPres, x, true),
        store(ed.succVector, x, 0, true),
        store(ed.precVector, x, ed.firstPrecPres, true),
		ed.firstPrecPres := x,
		//[PDEBUG] +++ Ap Pres ~S ~S Abs ~S ~S Tab ~S ~S--> ~S + ~S// ed.firstSuccPres, ed.firstPrecPres,ed.firstSuccAbs, ed.firstPrecAbs,  ed.succVector, ed.precVector, domainSet(ed),z,
		ed.nbElements :+ 1
	)]

// removing a given value (z) from the domain
[removeDomainVal(ed:PalmIntDomain, z:integer) : boolean
 -> let x := z - ed.offset,
        w := firstCode29bits(ed, z),
        i := secondCode29bits(ed, z)
	in (
		//[PDEBUG] Av ~S ~S Tab ~S ~S--> ~S - ~S// ed.firstSuccPres, ed.firstPrecPres, ed.succVector, ed.precVector, domainSet(ed),z,
		if (x >= 1 & x <= ed.bucketSize & ed.booleanVector[w][i]) ( 
			store(ed.booleanVector,w,ed.booleanVector[w] - ^2(i), true),
			let succX := ed.succVector[x],
				precX := ed.precVector[x] 
			in (
				if (precX = 0)(
					ed.firstSuccPres := succX, 
					if (succX = 0) 
						ed.firstPrecPres := 0 
					else store(ed.precVector, succX, 0, true)
				)
				else (
					store(ed.succVector, precX, succX, true),
					if (succX = 0) 
						ed.firstPrecPres := precX 
					else 
						store(ed.precVector, succX, precX, true)
				),
				//[PDEBUG] Mi ~S ~S Tab ~S ~S--> ~S - ~S// ed.firstSuccPres, ed.firstPrecPres, ed.succVector, ed.precVector, domainSet(ed),z,
	
				if (ed.firstSuccAbs = 0) 
					ed.firstSuccAbs := x
				else 
					store(ed.succVector, ed.firstPrecAbs, x, true),
				store(ed.succVector, x, 0, true),
				store(ed.precVector, x, ed.firstPrecAbs, true),
				ed.firstPrecAbs := x,
				ed.nbElements :- 1,
			
				//[PDEBUG] Ap ~S ~S Tab ~S ~S--> ~S - ~S// ed.firstSuccPres, ed.firstPrecPres, ed.succVector, ed.precVector, domainSet(ed),z,
	
				true
			)
		) 
		else 
			false
	)]

