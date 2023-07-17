#!/usr/bin/env sh

java -Xmx256M -cp "\
../Caffeine/bin":"\
../Caffeine/*":"\
../Caffeine Analyses/bin":"\
../CLAP/lib/*":"\
../Copyright Management/bin":"\
../CPL/target/CPL-1.0.0.jar":"\
../DOT/bin":"\
../DRAM Libraries/bin":"\
../DRAM Libraries/lib/*":"\
../DRAM OADymPPaC/bin":"\
../DRAM OADymPPaC/libraries/*":"\
../DRAM Robot/bin":"\
../EPI/bin":"\
../InfoVis/bin":"\
../InfoVis/lib/*":"\
../Java Parser/bin":"\
../Java Parser Data Extractor/bin":"\
../JChoco/bin":"\
../JCT/bin":"\
../JCT Impl/bin":"\
../JCT Tools/bin":"\
../JCT Tools/lib/*":"\
../JCT Utils/bin":"\
../Log Metamodel/bin":"\
../Mendel/bin":"\
../MoDeC Bytecode Instrumentation/bin":"\
../MoDeC Invoker/bin":"\
../MoDeC Metamodel/bin":"\
../MoDeC Solver/bin":"\
../Metrical Ptidej Solver/bin":"\
../PADL/target/PADL-1.0.0.jar":"\
../PADL Analyses/bin":"\
../PADL Code-Diagram Comparison/bin":"\
../PADL Creator AOL/bin":"\
../PADL Creator AspectJ/bin":"\
../PADL Creator AspectJ/lib/*":"\
../PADL Creator C# v1/bin":"\
../PADL Creator C# v1/lib/*":"\
../PADL Creator C# v2/bin":"\
../PADL Creator C# v2/libs/*":"\
../PADL Creator C++/bin":"\
../PADL Creator C++/lib/*":"\
../PADL Creator ClassFile/bin":"\
../PADL Creator CSharp v1/bin":"\
../PADL Creator CSharp v1/lib/*":"\
../PADL Creator CSharp v2/bin":"\
../PADL Creator CSharp v2/libs/*":"\
../PADL Creator JavaFile (JavaC)/bin":"\
../PADL Creator MSE/bin":"\
../PADL Creator XMI/bin":"\
../PADL Design Motifs/bin":"\
../PADL Generator/bin":"\
../PADL Generator PageRank/bin":"\
../PADL JNI/bin":"\
../PADL Micro-pattern Analysis/bin":"\
../PADL Refactorings/bin":"\
../PADL Serialiser DB4O/bin":"\
../PADL Serialiser DB4O/lib/*":"\
../PADL Serialiser JOS/bin":"\
../PADL Serialiser NeoDatis/bin":"\
../PADL Serialiser NeoDatis/libs/*":"\
../PADL Statements/bin":"\
../PADL Statements Creator AOL/bin":"\
../PADL Statements Creator ClassFile/bin":"\
../POM/bin":"\
../Ptidej/bin":"\
../Ptidej/lib/*":"\
../Ptidej Reporting/bin":"\
../Ptidej Reporting/lib/*":"\
../Ptidej Solver Metrics/bin":"\
../Ptidej Solver 4/bin":"\
../Ptidej UI/bin":"\
../Ptidej UI Analyses/bin":"\
../Ptidej UI AspectJ/bin":"\
../Ptidej UI C++/bin":"\
../Ptidej UI Layouts/bin":"\
../Ptidej UI Primitives AWT/bin":"\
../Ptidej UI Primitives SWT/bin":"\
../Ptidej UI Viewer/bin":"\
../Ptidej UI Viewer AWT/bin":"\
../Ptidej UI Viewer Extensions/bin":"\
../SAD/bin":"\
../SAD Rules Creator/bin":"\
../SQUAD/bin":"\
../SQUAD/lib/jdom.jar":"\
../SQUAD/lib/lucene-core-2.3.2":"\
./Tools v1.4.1_02.jar":"\
./bin":"\
./lib/*":"\
" ptidej.viewer.ProjectViewer
