java -Xmx1024M -jar ../lib/antlr-3.1.3.jar -Xconversiontimeout 100000 -o ../tmp ../rsc/CSharp.g3
COPY ..\tmp\CSharpLexer.java ..\src\padl\creator\parser\
java -classpath ../bin padl.creator.util.Splitter ../tmp/CSharpParser.java ../src/padl/creator/parser/
PAUSE