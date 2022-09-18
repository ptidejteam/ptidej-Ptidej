# #!/bin/sh
# inclure jdk-1.5
java -Xmx8072M -classpath "../CPL/bin":"../POM/bin":"../Linguistic Ptidej Solver/lib/lucene-core-2.3.2.jar":"../foutsekh-program-Integratik/bin":"../CPL/cfparse.jar":"../CPL/bcel-5.2.jar":"../JChoco/bin":"../JUnit/bin":"../PADL/bin":"../PADL Analyses/bin":"../PADL Creator ClassFile/bin":"../foutsekh-program-BugDefects/bin" sad.detection.MetricsGenerator $1 $2  "3.4.b"
