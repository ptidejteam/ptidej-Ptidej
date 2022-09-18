// ********************************************************************
// * ICEBERG, version 0.0                                             *
// * file: init.cl                                                    *
// *    module definition                                             *
// * Copyright (C) Bouygues, 2001 , see readme.txt                    *
// ********************************************************************

(system.verbose := 0,
 compiler.safety := 5)

// The next line defines a global variable (with range string) that indicates the installation
// directories for Choco and Sugar. You may want to replace this by a global indication of the directories
// in your file system, such as
// ChocoInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Choco"
// IcebergInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Iceberg"
// Note the use of the / operator to concatenate strings while inserting a file separator between them:
// "dir1" / "dir2" return "dir1/dir2" under unix and "dir1\\dir2" under windows

ChocoInstallationDir:string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Choco"
IcebergInstallationDir:string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Iceberg"
Claire3DevDir: string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Claire"

// choco :: module(source = ChocoInstallationDir / "v1.324",  //this version number can be upgraded
//                uses = list(Reader),
//                made_of = list( "model","iprop","const","const2","bool","main","chocapi","compil"))
choco :: module(source = ChocoInstallationDir / "v1.324",
                uses = list(Core, Reader),
                made_of = list( "chocutils","model","dom","prop","var","const",
                                "intconst1","intconst2","boolconst","setconst",
                                "search","chocapi","opapi","compil"))  // remove opapi for the java version

ice :: module(source = IcebergInstallationDir / "v0.95",
              part_of = choco,
              uses = list(choco),
              made_of = list("wcsp", "matching"))

icetest :: module(source = IcebergInstallationDir / "test",
                    uses = list(ice),
                    made_of = list( "test","t_wcsp","t_match"))

icebench :: module(source = IcebergInstallationDir / "bench",
                    uses = list(ice),
                    made_of = list( "bench","b_latin","b_ttemn","b_golomb"))
                    
(compiler.source := IcebergInstallationDir / "obj",
; compiler.inline? := true,
 compiler.libraries_dir := list(getenv("CLAIRE3_HOME") / "bin" / "debug" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env),
 compiler.headers_dir := getenv("CLAIRE3_HOME") / "include")  
(FCALLSTINKS := true)
