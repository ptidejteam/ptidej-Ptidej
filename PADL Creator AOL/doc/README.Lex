This directory contains a minimal example for using JLex and CUP
together.  This example requires CUP version 0.10k or greater and
JLex version 1.2.5 or greater.

To compile and build:

 java JLex.Main minimal.lex
 mv minimal.lex.java Yylex.java
 java java_cup.Main minimal.cup
 javac -d . parser.java sym.java Yylex.java

The example is a simple calculator.  Only addition and multiplation
are supported.  Input is taken from stdin.  To run:
 java -classpath .:$CLASSPATH Example.parser

Try inputs such as:
 1+2;
 1+2*5;
 5*1+2;
 5*(1+2);

Enjoy!

-- C. Scott Ananian <cananian@alumni.princeton.edu>, 27-Jul-1999
