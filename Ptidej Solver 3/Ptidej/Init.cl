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

// Yann 2001/07/25: Hicham!
// Chami pointed out that I could remove the "CD ..." call in the
// bat file, if I directly put Claire and its initialisation
// file in the same directory, that could save the problem of
// the 8.3-limitation of B2C.exe and C2E.exe.
// Meanwhile, Chami works on a Perl version of the conversion
// command!
//
// Yann 2002/04/03: Integration with Ptidej UI.
// I use the PtidejInstallationDir global variable to hold the directory
// where Ptidej Solver can locate the required resource file.

(system.verbose := 0, compiler.safety := 2)

// The next lines defin two global variables (with range string) that indicate the 
// installation directories for Choco and PaLM. You want to replace this by a global
// indication of the directory in your file system, such as:

// ChocoInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Choco"
// PalmInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "PaLM"

// Note the use of the / operator to concatenate strings while inserting a file 
// separator between them: "dir1" / "dir2" return "dir1/dir2" under unix and 
// "dir1\\dir2" under windows

ChocoInstallationDir:string   :: "..\\Choco"
IcebergInstallationDir:string :: "..\\Iceberg"
PalmInstallationDir:string    :: "..\\PaLM"
PtidejInstallationDir:string  :: "..\\Ptidej"

choco :: module(source = ChocoInstallationDir / "v1.324",
                uses = list(Core),
				made_of = list(
						"chocutils",
						"model",
						"dom",
						"prop",
						"var",
						"const",
						"intconst1",
						"intconst2",
						"boolconst",
						"setconst",
						"search",
						"chocapi",
						"opapi",	// Remove opapi for the Java version!
						"compil"))  

ice :: module(source = IcebergInstallationDir / "v0.95",
              part_of = choco,
              uses = list(choco),
              made_of = list("matching"))


palm :: module( source = PalmInstallationDir / "v1.324", 
               uses = list(ice),
               made_of = list(
               			"p-namespace",
               			"p-model" ,
               			"p-domain",
               			"p-variables", 
						"p-constraints",
						"p-symbolic",
						"p-ac4", 
						"p-explain",
						"p-solve",
						"p-bool",
						"palmapi"))

// Pattern Trace Identification, Detection and Enhancement for Java.
ptidej :: module(
			source = PtidejInstallationDir,
			uses = list(Reader, choco, palm),
			part_of = palm,
			made_of = list(
						"Release",
						"DomainModel",
						"SolverModel",
						"SolverCore",
						"GeneralRepairAlgorithm",
                        "AC4Constraints",
                        "CustomConstraints",
						"SimpleAutomaticSolver",
                        "ConstraintHelpers",
                        "SimpleInteractiveSolver",
                        "AutomaticSolver",
                        "InteractiveSolver",
                        "CombinatorialAutomaticSolver",

						// Simple tests.
						"SimpleTests",

						// Test patterns.
						"AssociationDistanceTest",
                        "CompositionTest",
                        "CreationTest",
                        "IgnoranceTest",
                        "InheritancePathTest",
                        "InheritanceTest",
                        "InheritanceTreeDepthTest",
                        "useDistanceTest",
                        "UseTest",
                        "StrictInheritanceTest",

						// Design patterns.
						"AbstractFactoryPattern",
                        "ChainOfResponsibilityPattern",
                        "CompositePattern",
                        "ExtendedCompositePattern",
                        "FacadePattern",
                        "FactoryMethodPattern",
                        "IteratorPattern",
                        "MediatorPattern",
                        "MementoPattern",
                        "ObserverPattern",
                        "ProxyPattern",
                        "VisitorPattern",

						// Design defect (defect pattern?).
                        "BadCompositionTest",
                        "GoodInheritance",
                        "RedundantTransitivity"
					)
)


(
	compiler.source := ptidej.source / "Obj",
	compiler.libraries_dir := list(getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env,
									getenv("CLAIRE3_HOME") / "bin" / "debug" / compiler.env,
									getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env),
	compiler.headers_dir := getenv("CLAIRE3_HOME") / "include"
)
