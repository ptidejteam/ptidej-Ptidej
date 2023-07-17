// ********************************************************************
// * PaLM version 1.324 - Dec 1, 2002                                 *
// * file: init.cl                                                    *
// *    module definition                                             *
// * Copyright (C) N. Jussien, 2001-2002, see readme.txt              *
// ********************************************************************


(system.verbose := 0, compiler.safety := 5)


// The next lines define two global variables (with range string) that indicate the 
// installation directories for Choco, Ice and PaLM. You want to replace this by a 
// global indication of the directory in your file system, such as:

// ChocoInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Choco"
// IcInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Ice"
// PalmInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "PaLM"

// Note the use of the / operator to concatenate strings while inserting a file 
// separator between them: "dir1" / "dir2" return "dir1/dir2" under unix and 
// "dir1\\dir2" under windows


ChocoInstallationDir:string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Choco"
IceInstallationDir:string   := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Iceberg"
PalmInstallationDir: string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\PaLM"

choco :: module(source = ChocoInstallationDir / "v1.324",
                uses = list(Core),
				made_of = list( "chocutils","model","dom","prop","var","const",
                                "intconst1","intconst2","boolconst","setconst",
                                "search","chocapi","opapi","compil"))  // remove opapi for the java version

ice :: module(source = IceInstallationDir / "v0.95",
              part_of = choco,
              uses = list(choco),
              made_of = list("matching"))


palm :: module( source = PalmInstallationDir / "v1.324", 
               uses = list(ice),
               made_of = list( "p-namespace", "p-model" , "p-domain", "p-variables", 
                               "p-constraints", "p-symbolic", "p-ac4", 
                               "p-explain", "p-solve", "p-bool", "palmapi"))

palmtest :: module( source = PalmInstallationDir / "test",
				uses = list(palm), 
				made_of = list("unit"))


(compiler.source := palm.source / "obj",
 compiler.libraries_dir := list(getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "debug" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env),
 compiler.headers_dir := getenv("CLAIRE3_HOME") / "include")
