//*****************************************************************************
//* Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
//* All rights reserved. This program and the accompanying materials
//* are made available under the terms of the GNU Public License v2.0
//* which accompanies this distribution, and is available at
//* http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
//* 
//* Contributors:
//*     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
//*****************************************************************************

// Naren 2000/11/03: Solving problems with PaLM
// Augmentation de l'API pour Yann.

// Yann 2001/06/13: Adaptation
// Modified to adapt to Choco and PaLM v1.0

// Naren 2001/07/01: Adaptation (again)!
// Modified to adapt to PaLM v1.0

// Naren 2001/07/13: Nasty!
// Corrected to remove nasty bugs: selectDecisions 
// and solutions different from the one produced
// by the old version of PaLM / Choco

// Yann 2001/11/03: Bug!
// Bug corrected based on Naren's advices.

// Yann 2002/04/03: UI!
// Modified to integrate Ptidej.exe with Ptidej UI (in Java).
// The problem is the path! In Java, there is no way to change the
// current path. Ptidej.exe runs from the root (C:\). It cannot find
// the required resource file because this file stays in:
// "C:\Docume~1\Yann\Work\Ptidej~1\Ptidej\" (For instance).
// I add a global variable PtidejResultFile that is set by the
// Ptidej UI and that is used to locate the appropriate resource file.
// I declare it as unknown here, and it is set properly in the file:
// Instruction.cl given as parameter by Ptidej UI when calling the
// solver.

// Yann 2002/08/12
// Lost of changes to comply with unit tests...

// Yann 2002/08/16: Improvement!
// I remove the constant LOWER_PERCENTAGE_LIMIT to
// make a better use of the problem.maxRelaxLvl value.

// Yann 2002/10/12: Choco API!
// The Choco API to attach GlobalSearchSolver and
// LocalSearchSolver is not consistent:
[choco/attach(newSolver:choco/LocalSearchSolver, pb:PalmProblem) : void;
	->	choco/attachLocalSearchSolver(pb, newSolver)
]



PtidejResultFile:string := unknown



// ********************************************************************
// * Part 0: Temporary declaration of a more general PalmRepair class *
// ********************************************************************

GeneralPalmRepair <: PalmRepair()

[remove(
	repairer:GeneralPalmRepair,
	constraint:AbstractConstraint) : void
    ->  error("Sub-classes of GeneralPalmRepair must override method remove(GeneralPalmRepair, AbstractConstraint).\n")
]
[getRemovedConstraints(
	repairer:GeneralPalmRepair) : list<AbstractConstraint>
    ->  error("Sub-classes of GeneralPalmRepair must override method getRemovedConstraints(GeneralPalmRepair).\n")
]

[selectDecisionToUndo(
	repairer:GeneralPalmRepair,
	e:Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
    ->  error("Sub-classes of GeneralPalmRepair must override method selectDecisionToUndo(GeneralPalmRepair, Explanation).\n")
]


// *******************************************
// * Part 1: Forward class declarations,     *
// *         some methods definitions,       *
// *         definition of the problem class *
// *******************************************

PtidejAssignVar <: PalmAssignVar()
PtidejBranching <: PalmBranching(
	solutionNumber:integer = 0,
	message:string = "",
	percentage:integer = 0,
	xCommand:string = "")
PtidejLearn     <: PalmLearn()
PtidejRepair    <: GeneralPalmRepair(
	problem:PtidejProblem,
	userRelaxedConstraints:list<AbstractConstraint>
)
PtidejSolver    <: PalmSolver()

MinimumPtidejRepair <: PtidejRepair()
[selectDecisionToUndo(
	repairer:MinimumPtidejRepair,
	e: Explanation) : tuple(list<AbstractConstraint>, list<AbstractConstraint>)
	->	let
			ct:AbstractConstraint := min(standard_better_constraint @ AbstractConstraint, set!(e)) as AbstractConstraint,
			re:tuple(list<AbstractConstraint>, list<AbstractConstraint>) := tuple(list<AbstractConstraint>(), list<AbstractConstraint>())
		in (
			add(re[1], ct),
			re
		)
]

[makePtidejProblem(s:string, sizeOfTheDomain:integer, maxRelaxationLevel:integer) : PtidejProblem 
    ->  // [0] name               = ~S // s,
    	// [0] size               = ~S // sizeOfTheDomain,
    	// [0] maxRelaxationLevel = ~S // maxRelaxationLevel,

		// [0] world?() = ~S // world?(),

    	let pb := PtidejProblem(name = copy(s))
		in (
			// Yann 2002/11/22: Simple tests!
			// I copied the simple tests from PaLM and it highlighted
			// some flaws in the creation of Ptidej-specific problem.
			// In particular, I did not initialize all the instances
			// properly...
			// I use the PalmRepair, which provides a sufficient
			// implementation for the tests.
			pb.palmSolver := PtidejSolver(statistics = make_list(length(RuntimeStatistic), integer, 0)),
			pb.palmSolver.problem := pb,
            attachPalmState(pb, PalmState(path = Explanation())),
            attachPalmLearn(pb, PtidejLearn()),
        	attachPalmRepair(pb, MinimumPtidejRepair()),

			choco/attach(choco/GlobalSearchSolver(), pb),
			choco/attach(choco/LocalSearchSolver(), pb),
        	let pe:PalmEngine := makePalmEngine(sizeOfTheDomain + 1) in (
        		attachPropagationEngine(pb, pe)
        	),
			// [0] maxRelaxationLevel = ~S // maxRelaxationLevel,
        	pb.maxRelaxLvl := maxRelaxationLevel,
			// Yann 2002/10/12: Not need anymore!
			// I must use my own init method anyway.
        	//		initPalmSolver(pb),
        	choco/setActiveProblem(pb),
        	pb
    	)
]

[selectBranchingItem(b:PtidejAssignVar) : any
    ->  // [0] selectBranchingItem,
    	let l:list := list{
    		v in b.extender.manager.problem.vars |
    		v.toBeEnumerated}
    	in (
    		// [0] l = ~S // l,
        	getMinDomVar(l)
        )
]

[remove(repairer:PtidejRepair, constraint:AbstractConstraint) : void
    ->	repairer.userRelaxedConstraints :add constraint
]
[getRemovedConstraints(repairer:PtidejRepair) : list<AbstractConstraint>
    ->  repairer.userRelaxedConstraints
]
[setRemovedConstraints(repairer:PtidejRepair, l:list[AbstractConstraint]) : void
    ->  repairer.userRelaxedConstraints := l
]
[getProblemRelaxationLevel(repairer:PtidejRepair) : integer
    ->  repairer.problem.maxRelaxLvl
]
[getProblem(repairer:PtidejRepair) : PtidejProblem
    ->  repairer.problem
]
[setProblem(repairer:PtidejRepair, pb:PtidejProblem) : void
    ->  repairer.problem := pb
]

[setSolutionNumber(b:PtidejBranching, n:integer) : void
    ->  // [0] setSolutionNumber(~S, ~S) // b, n,
    	b.solutionNumber := n
]
[getSolutionNumber(b:PtidejBranching) : integer
    ->  // [0] getSolutionNumber(~S) = ~S // b, b.solutionNumber,
    	b.solutionNumber
]
[setMessage(b:PtidejBranching, m:string) : void
    ->  // [0] setMessage(~S, ~S) // b, m,
    	b.message := m
]
[getMessage(b:PtidejBranching) : string
    ->  // [0] getMessage(~S) = ~S // b, b.message,
    	b.message
]
[setPercentage(b:PtidejBranching, p:integer) : void
    ->  // [0] setPercentage(~S, ~S) // b, p,
    	b.percentage := p
]
[getPercentage(b:PtidejBranching) : integer
    ->  // [0] getPercentage(~S) = ~S // b, b.percentage,
    	b.percentage
]
[setXCommand(b:PtidejBranching, c:string) : void
    ->  // [0] setXCommand(~S, ~S) // b, c,
    	b.xCommand := c
]
[getXCommand(b:PtidejBranching) : string
    ->  // [0] getXCommand(~S) = ~S // b, b.xCommand,
    	b.xCommand
]



// ********************************
// * Part 2: Variable definitions *
// ********************************

[makePtidejVar(p:palm/PalmProblem, s:string, i:integer, j:integer) : PtidejVar
    =>  makePtidejVar(p, s, i, j, true)
]
[makePtidejVar(
	p:palm/PalmProblem,
	s:string,
	i:integer,
	j:integer,
	enumerate:boolean) : PtidejVar
    ->  assert(i <= j),
        let
        	v := PtidejVar(
        			name = copy(s),
        			originalInf = i,
        			originalSup = j,
        			toBeEnumerated = enumerate)
        in (
            closeIntVar(v, i, j, j - i + 2),
            addIntVar(p, v),
            v.bucket := makePalmIntDomain(i, j),
            v.restInfEvt := DecInf(modifiedVar = v),
            v.restSupEvt := IncSup(modifiedVar = v),
            v.explanationOnInf :add makeIncInfExplanation(Explanation(), i, v),
            v.explanationOnSup :add makeDecSupExplanation(Explanation(), j, v),
            v.restValEvt := makeValueRestorations(v, j - i + 2),
            v
        )
]



[makePtidejBoundVar(p:PalmProblem, s:string, i:integer, j:integer) : PtidejBoundVar
    ->  makePtidejBoundVar(p, s, i, j, true)
]
[makePtidejBoundVar(p:PalmProblem, s:string, i:integer, j:integer, enumerate:boolean) : PtidejBoundVar
    ->  assert(i <= j),
        let v := PtidejBoundVar(name = copy(s), originalInf = i, originalSup = j, toBeEnumerated = enumerate)
        in (
	        closeIntVar(v,i,j,1),  
	        addIntVar(p,v),
	        // Initialising PaLM specific slots...
	        v.restInfEvt := DecInf(modifiedVar = v),
	        v.restSupEvt := IncSup(modifiedVar = v),
	        v.explanationOnInf :add makeIncInfExplanation(Explanation(), i, v),
	        v.explanationOnSup :add makeDecSupExplanation(Explanation(), j, v),
	        v.restValEvt := makeValueRestorations(v, 0),
	        v
		)
]

[self_print(v:PtidejVar) : void
	->	// [0] PtidejVar,
		// [0] known?(bucket, v) = ~S // known?(bucket, v),
		if known?(bucket, v) (
			if known?(name, v) (
				printf("~A:",v.name)
			)
			else (
				printf("<PtidejVar>:")
			),
			if known?(value, v) (
				printf("~S", v.value)
			)
			else (
				printf("[~S]~S", size(v.bucket), set!(v.bucket))
			)
		)
		else  (
			if known?(name, v) (
				printf("~A:",v.name)
			)
			else (
				printf("<PtidejBoundVar>:")
			),
			if known?(value, v) (
				printf("~S", v.value)
			)
			else (
				printf("[~S..~S]", v.inf, v.sup)
			)
		),
		printf(" (~S)", v.isa)
]

[userAssignSelectVar(l:list[PtidejVar]) : integer
    ->  // [0] userAssignSelectVar(l:list[PtidejVar]) : integer,
    	let min := car(l).bucket.nbElements,
            i   := 1,
            ret := 1 in (

            for v in l (
                if (v.bucket.nbElements < min) (
                    ret := i,
                    min := v.bucket.nbElements
                ),
                i :+ 1
            ),
            ret
        )
]



// **********************************
// * Part 3: General helper methods *
// **********************************

[isBetterConstraint(c1:AbstractConstraint, c2:AbstractConstraint) : boolean
    -> // [0] c1: ~S (~S) c1: ~S (~S) // c1, isa(c1), c2, isa(c2), 
       (weight(c1) >= weight(c2))
]

[getExplanations(vs:list<choco/IntVar>, expl:Explanation) : void
	->	// [0] getExplanations(vs:list[PalmIntVar], expl:Explanation),
		for v in vs (
			// [0] v = ~S // v,
			self_explain(v, DOM, expl)
		)
]

[clearResultFile()
    ->  try (
	    	let outputPort:port := fopen(PtidejResultFile, "w") in (
	            use_as_output(outputPort),
	            flush(outputPort),
	            use_as_output(stdout)
	        )
        )
        catch any (
	        use_as_output(stdout),
            printf("\nCannot open \"~A\". (Core.cl:2)\n", PtidejResultFile),
            error("~S", exception!())
        )
]

[printOutSolution(
    pb:PtidejProblem,
    solutionMessage:string,
    solutionNumber:integer,
    solutionPercentage:integer,
    xCommand:string) : void
    ->  // [0] printOutSolution,
    	// [0] solutionMessage = ~S // solutionMessage,
    	// [0] solutionPercentage = ~S // solutionPercentage,
    	// [0] globalWeight = ~S // getGlobalWeight(pb),
    	// try (
			let
				outputPort:port := fopen(PtidejResultFile, "a"),
				outputText:string := "",
				percentage:integer := 0
			in (
	            use_as_output(outputPort),
	            flush(outputPort),

				// Yann 2004/08/07: Solution weight.
				// I now use the sum of the weights of the remove
				// constraints to compute the weight of the solution.
				// But this sum might be zero (!) if no constraint
				// has been removed...
				if (getGlobalWeight(pb) = 0) (
					percentage := 100
				)
				else (
					// [0] getGlobalWeight(pb) = ~S // getGlobalWeight(pb),
					// [0] solutionPercentage  = ~S // solutionPercentage,
					percentage := /(*(solutionPercentage, getGlobalWeight(pb)), getGlobalWeight(pb))
					// [0] percentage          = ~S // percentage
				),

	            printf("\n~A\n", solutionMessage),
	            outputText := outputText /+ 
	                        string!(solutionNumber) /+
	                        "." /+
	                        string!(percentage) /+
	                        ".Name = " /+
	                        pb.choco/name /+
	                        "\n",
	
	            outputText := outputText /+ 
	                        string!(solutionNumber) /+
	                        "." /+
	                        string!(percentage) /+
	                        ".XCommand = " /+
	                        xCommand /+
	                        "\n",
	
	            for i in (1 .. length(pb.choco/globalSearchSolver.choco/varsToShow)) (
	            	// [0] \tvarsToShow[~S] = ~S (~S) // i, pb.choco/globalSearchSolver.choco/varsToShow[i], known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i]),
					// [0] i = ~S // i,
					if (pb.choco/globalSearchSolver.choco/varsToShow[i].isa = PtidejBoundVar) (
		                if (known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i])) (
		                    outputText := outputText /+ 
		                                string!(solutionNumber) /+
		                                "." /+
		                                string!(percentage) /+
		                                "." /+
		                                pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
		                                " = " /+ 
		                                string!(pb.choco/globalSearchSolver.choco/varsToShow[i].value) /+
		                                "\n"
		                )
		                else (
		                    let nbROTW: integer := 0 in (
		                        for v in choco/domain(pb.choco/globalSearchSolver.choco/varsToShow[i]) (
		                            nbROTW := nbROTW + 1,
		                            outputText := outputText /+ 
		                                        string!(solutionNumber) /+
		                                        "." /+
		                                        string!(percentage) /+
		                                        "." /+
		                                        pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
		                                        "-" /+
		                                        string!(nbROTW) /+
		                                        " = " /+ 
		                                        string!(v) /+
		                                        "\n"
		                        )
		                    )
		                )
					)
					else (
		                if (known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i]) & pb.choco/globalSearchSolver.choco/varsToShow[i].value > 0) (
		                    outputText := outputText /+ 
		                                string!(solutionNumber) /+
		                                "." /+
		                                string!(percentage) /+
		                                "." /+
		                                pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
		                                " = " /+ 
		                                listOfEntities[pb.choco/globalSearchSolver.choco/varsToShow[i].value].name /+
		                                "\n"
		                )
		                else (
		                	// [0] Variable with unknow value: ~S // pb.choco/globalSearchSolver.choco/varsToShow[i],
							// Yann 2002/11/27: BoundIntVar!
							// I cannot print out the bucket anymore
							// because the variable might be a
							// BoundIntVar, which does not have a bucket...
		                	// [0] \tBucket: ~S // pb.choco/globalSearchSolver.choco/varsToShow[i].bucket.bucketSize,
		                	// [0] \tList: ~S // list!(pb.choco/globalSearchSolver.choco/varsToShow[i].bucket),
		                    let nbROTW: integer := 0 in (
		                        for v in choco/domainSequence(pb.choco/globalSearchSolver.choco/varsToShow[i].bucket) (
		                            nbROTW := nbROTW + 1,
		                            outputText := outputText /+ 
		                                        string!(solutionNumber) /+
		                                        "." /+
		                                        string!(percentage) /+
		                                        "." /+
		                                        pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
		                                        "-" /+
		                                        string!(nbROTW) /+
		                                        " = " /+ 	
		                                        listOfEntities[v].name /+
		                                        "\n"
		                        )
		                    )
		                )
					)
				),

				// [0] printing text out...,
				printf("~A\n", outputText),
	            // I make sure the output is written on disk before continuing.
	            use_as_output(stdout)
			)
        // )
        // catch any (
            // use_as_output(stdout),
            // printf("\n~S", exception!()),
            // printf("\nCannot open \"~A\". (Core.cl:3)\n", PtidejResultFile)
        // )
]

[printOutTime(t:integer) : void
    ->  // [0] printOutTime,
    	try (
		    let
		    	outputPort:port := fopen(PtidejResultFile, "a")
		    in (
		        use_as_output(outputPort),
		        flush(outputPort),
		        printf("# Solved in ~Smin (~Sms).\n",  /(t, 60000), t),
		        use_as_output(stdout)
		    )
        )
        catch any (
	        use_as_output(stdout),
            printf("\nCannot open \"~A\". (Core.cl:4)\n", PtidejResultFile),
            error("~S", exception!())
        )
]


[printHeader() : void
	->	printf("~A", char!(218)),
		for i in (1 .. 78) (
			printf("~A", char!(196))
		),
		printf("\n")
]

[printFooter() : void
	->	printf("~A", char!(192)),
		for i in (1 .. 78) (
			printf("~A", char!(196))
		),
		printf("\n")
]

StatusCounter:integer := 0
[printStatus() : void
	->	case StatusCounter (
			{0} (
				printf("~A|", char!(8)),
				StatusCounter := 1
			),
			{1} (
				printf("~A/", char!(8)),
				StatusCounter := 2
			),
			{2} (
				printf("~A-", char!(8)),
				StatusCounter := 3
			),
			{3} (
				printf("~A\\", char!(8)),
				StatusCounter := 0
			)
		)
]


// **********************************************************************************
// * Part 4: A general branching object asking the user if she wants more solutions *
// **********************************************************************************

PtidejInteractiveBranching <: PtidejBranching()

[selectBranchingItem(b:PtidejInteractiveBranching) : any
    ->  // [0] selectBranchingObject 2,
        let
        	pb:Problem := b.extender.manager.problem,
			outputText:string := ""
        in ( 
        	setSolutionNumber(b, getSolutionNumber(b) + 1),

			// Yann 2003/03/22: Printing solutions out...
			// I do somewhat like in printOutSolution() method.
            printf("\nSolution ~S:\n", getSolutionNumber(b)),
            for i in (1 .. length(pb.choco/globalSearchSolver.choco/varsToShow)) (
            	// [0] \tvarsToShow[~S] = ~S (~S) // i, pb.choco/globalSearchSolver.choco/varsToShow[i], known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i]),
				// [0] i = ~S // i,
				if (pb.choco/globalSearchSolver.choco/varsToShow[i].isa = PtidejBoundVar) (
	                if (known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i])) (
	                    outputText := outputText /+ 
	                                "\t" /+ 
	                                pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
	                                " = " /+ 
	                                string!(pb.choco/globalSearchSolver.choco/varsToShow[i].value) /+
	                                "\n"
	                )
	                else (
	                    let nbROTW: integer := 0 in (
	                        for v in choco/domain(pb.choco/globalSearchSolver.choco/varsToShow[i]) (
	                            nbROTW := nbROTW + 1,
	                            outputText := outputText /+ 
			                                "\t" /+ 
	                                        pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
	                                        "-" /+
	                                        string!(nbROTW) /+
	                                        " = " /+ 
	                                        string!(v) /+
	                                        "\n"
	                        )
	                    )
	                )
				)
				else (
	                if (known?(value, pb.choco/globalSearchSolver.choco/varsToShow[i]) & pb.choco/globalSearchSolver.choco/varsToShow[i].value > 0) (
	                    outputText := outputText /+ 
	                                "\t" /+ 
	                                pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
	                                " = " /+ 
	                                listOfEntities[pb.choco/globalSearchSolver.choco/varsToShow[i].value].name /+
	                                "\n"
	                )
	                else (
	                	// [0] Variable with unknow value: ~S // pb.choco/globalSearchSolver.choco/varsToShow[i],
						// Yann 2002/11/27: BoundIntVar!
						// I cannot print out the bucket anymore
						// because the variable might be a
						// BoundIntVar, which does not have a bucket...
	                	// [0] \tBucket: ~S // pb.choco/globalSearchSolver.choco/varsToShow[i].bucket.bucketSize,
	                	// [0] \tList: ~S // pb.choco/globalSearchSolver.choco/varsToShow[i].bucket,
	                    let nbROTW: integer := 0 in (
	                        for v in choco/domainSequence(pb.choco/globalSearchSolver.choco/varsToShow[i].bucket) (
	                            nbROTW := nbROTW + 1,
	                            outputText := outputText /+ 
			                                "\t" /+ 
	                                        pb.choco/globalSearchSolver.choco/varsToShow[i].name /+
	                                        "-" /+
	                                        string!(nbROTW) /+
	                                        " = " /+ 	
	                                        listOfEntities[v].name /+
	                                        "\n"
	                        )
	                    )
	                )
				)
			),
			// [0] printing text out...,
			printf("~A\n", outputText),

            printf("Do you want another solution? (y/n) "),
            let rep := getc(stdin) in (
                getc(stdin),
                if (rep = 'n') (
					raiseSystemContradiction(pb.propagationEngine),
					// Yann 2003/04/06: Wrong sort!
					// I must add "unknown" to make sure the compiler
					// doesn't comlain about this method being "void"
					// instead of "any".
					unknown
                )
                else (
                    let
                        expl := Explanation(),
                        last := last(pb.vars)
                    in (
                        getExplanations(pb.vars, expl),
                        // Yann 2001/10/24: Bug corrected thanks to Naren!
                        // I must enumerate on all the values of the
                        // domain of the variable, not just on the
                        // first element, in case of non-enumerated
                        // variables (typically, Leaf in the Composite
                        // pattern).
                        // [0] last = ~S // last,
                        // Yann 2003/03/22: Bucket!
                        // I must check whether the last
                        // constraint is bound or not...
						if (known?(bucket, last)) (
			                for i in domainSequence(last.bucket) (
			                    post(pb, last !== i, expl)
			                )
			            )
			            else (
							post(pb, last !== last.value, expl)
			            ),
                        propagate(pb),
                        true
                    )
                )
            )
        )
]

// *******************************************************************
// * Part 5: A general branching object displaying all the solutions *
// *******************************************************************

PtidejAutomaticBranching <: PtidejBranching()

[selectBranchingItem(b:PtidejAutomaticBranching) : any
    ->  // [0] selectBranchingObject(b:PtidejAutomaticBranching),
    	// Yann 2002/08/12: Copy.
    	// This branching object is now similar to the one
    	// for PtidejInteractiveBranching.
        let
        	pb := b.extender.manager.problem
        in ( 
        	setSolutionNumber(b, getSolutionNumber(b) + 1),
			printOutSolution(
				pb,
				getMessage(b),
				getSolutionNumber(b),
				getPercentage(b),
				getXCommand(b)),
			// [0] Done printing,
            let
                expl := Explanation(),
                last := last(pb.vars)
            in (
            	// [0] getExplanations(pb.vars, expl),
            	// [0] getExplanations(~S, ~S) // pb.vars, expl,
                getExplanations(pb.vars, expl),
                // Yann 2001/10/24: Bug corrected thanks to Naren!
                // I must enumerate on all the values of the
                // domain of the variable, not just on the
                // first element, in case of non-enumerated
                // variables (typically, Leaf in the Composite
                // pattern).
				// [0] Posting diff constraint,
				// Yann 2002/11/26: Bound variables
				// I must check whether I am working with a bound
				// variable or not. If the last variable is NOT a
				// bound variable, then I post constraints that
				// prevent its remaining values.
				// If I am working with a bound variable, I post
				// a constraint that prevent its bound value (?).
				// I need to talk with Naren to see if this is
				// enough...
				if (known?(bucket, last)) (
	                for i in domainSequence(last.bucket) (
	                    post(pb, last !== i, expl)
	                )
	            )
	            else (
					post(pb, last !== last.value, expl)
	            ),
				// [0] Propagating,
                propagate(pb),
                true
            )
        )
]



// *******************************************
// * Part 6: A general helper method to call *
// * a constraint solver on a problem        *
// *******************************************

// automaticSolve(ac4ProblemForCompositePattern()),
// interactiveSolve(ac4ProblemForCompositePattern()),
// combinatorialAutomaticSolve(ac4ProblemForCompositePattern()),
[searchConcretePatterns(solver:method, problem:PtidejProblem) : void
    ->	printf("\n~A Clearing result file", char!(179)),
    	clearResultFile(),
		printf("\n~A Calling solver\t", char!(179)),
    	time_set(),
		let
        	args:list<any> := list<any>()
        in (
        	args :add problem,
	        apply(solver, args)
	    ),
		printOutTime(time_get()),
        nil
]
