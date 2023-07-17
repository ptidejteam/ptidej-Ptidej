// ********************************************************************
// * CHOCO, version 0.38 1/12/2000                                   *
// * file: init.cl                                                    *
// *    module definition                                             *
// * Copyright (C) F. Laburthe, 2000, see readme.txt                  *
// ********************************************************************

(system.verbose := 5,
 compiler.safety := 5)

// The next line defines a global variable (with range string) that indicates the installation
// directory for Choco. You may want to replace this by a global indication of the directory
// in your file system, such as
// ChocoInstallationDir:string := "D:" / "GreatProjects" / "Ocre" / "Choco"
// Note the use of the / operator to concatenate strings while inserting a file separator between them:
// "dir1" / "dir2" return "dir1/dir2" under unix and "dir1\\dir2" under windows
ChocoInstallationDir:string := "E:\\Work\\Ptidej-Workspace\\PTD07F~1\\Choco"

choco :: module(source = ChocoInstallationDir / "v1.324",
                uses = list(Core, Reader),
                made_of = list( "chocutils","model","dom","prop","var","const",
                                "intconst1","intconst2","boolconst","setconst",
                                "search","chocapi","opapi","compil"))  // remove opapi for the java version
                
chocotest :: module(source = ChocoInstallationDir / "test",
                uses = list(choco),
                made_of = list( "test","t_delay","t_bool","t_occur","t_elt","t_arithm","t_abs",
                                "t_feas","t_loop","t_domain","t_setdomains","t_setconst"))

chocobench :: module(source = ChocoInstallationDir / "benchs",
                uses = list(choco),
                made_of = list( "bench","b_queens","b_send","b_lineq","b_zebra","b_magic","b_square",
                                "b_photo","b_fcp","b_u2","b_cutting","b_roster","b_indptsets"))

mychocoapp :: module(
                source = ChocoInstallationDir / "apps",
                uses = list(choco),
                made_of = list( "myappfile"))

// jlight :: module( part_of = Generate,
//               uses = list(Generate),
//               source = getenv("CLAIRE3_HOME") / "lib" / "jlight" / "v0.32",
//               made_of = list<string>("jlgen","jlopt"))
;(FCALLSTINKS := true)

(compiler.source := "obj",
 compiler.libraries_dir := list(getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "debug" / compiler.env,
                              getenv("CLAIRE3_HOME") / "bin" / "public" / compiler.env),
 compiler.headers_dir := getenv("CLAIRE3_HOME") / "include")

;(load(jlight))
;(PRODUCER := JAVA)
;(JAVA.jlight/javaRoot := getenv("CLAIRE3_HOME") / "bin" / "java",
; JAVA.jlight/javaHome := getenv("JAVA_HOME") / "bin",
; JAVA.Compile/ignore :add reify,  JAVA.Compile/ignore :add abstract,
; JAVA.Generate/bad_names :add default.name, 
; JAVA.Generate/good_names :add symbol!("Default"),
; Generate/C++PRODUCER.Generate/bad_names :add symbol!("union"), 
; Generate/C++PRODUCER.Generate/good_names :add symbol!("UNION")
;  )
