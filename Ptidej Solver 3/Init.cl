// ********************************************************************
// * PaLM version 1.08 - Sep 12, 2001                                 *
// * file: init.cl                                                    *
// *    module definition                                             *
// * Copyright (C) N. Jussien, 2001, see readme.txt                   *
// ********************************************************************
//
// (c) Copyright 2000-2003 Yann-Gaël Guéhéneuc,
// Ecole des Mines de Nantes and Object Technology International, Inc.
// 
// Use and copying of this software and preparation of derivative works
// based upon this software are permitted. Any copy of this software or
// of any derivative work must include the above copyright notice of
// Yann-Gaël Guéhéneuc, this paragraph and the one after it.
// 
// This software is made available AS IS, and THE AUTHOR DISCLAIMS
// ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY
// LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
// EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
// NEGLIGENCE) OR STRICT LIABILITY, EVEN IF YANN-GAEL GUEHENEUC IS ADVISED
// OF THE POSSIBILITY OF SUCH DAMAGES.
// 
// All Rights Reserved.



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

ChocoInstallationDir:string   :: "Choco"
IcebergInstallationDir:string :: "Iceberg"
PalmInstallationDir:string    :: "PaLM"
PtidejInstallationDir:string  :: "Ptidej"

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
                        "UseDistanceTest",
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
