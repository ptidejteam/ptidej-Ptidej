/**
 * This package contains several XML parsers.<br>
 * <br>
 * If you want to parse a RevML file, take a look to
 * {@link jct.test.rsc.snpsht.parser.revml.RevMLCvsCompleteBuilder} in jct.test.rsc.snpsht.parser.revml package. For the
 * moment, only RevML CVS can be parsed, but it will be easy to implement an SVN
 * jct.test.rsc.snpsht.parser.<br>
 * Here is a simple code to parse a RevML file :<br> {@code VerFsManager manager =
 * (new RevMLCvsCompleteBuilder()).parse(<path2RevMLFile>);}<br>
 * <br>
 * If you want to build a new SAX jct.test.rsc.snpsht.parser, jct.test.rsc.snpsht.parser.sax package provide a standard
 * mechanism to build a SAX jct.test.rsc.snpsht.parser finite states machine.<br>
 * If you don't understand how to use it, you can find two examples in
 * jct.test.rsc.snpsht.parser.revml.sax package.<br>
 * <br>
 * 
 */

package jct.test.rsc.snpsht.parser;

