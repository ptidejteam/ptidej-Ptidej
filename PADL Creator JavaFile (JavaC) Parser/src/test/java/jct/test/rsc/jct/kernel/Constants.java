/**
 * @author Mathieu Lemoine
 * @created 2008-12-15 (月)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package jct.test.rsc.jct.kernel;

import java.util.regex.Pattern;

/**
 * Constants list for JCT interfaces
 */
public final class Constants
{
	private Constants()
	{ }

	// Path separators
    public final static char DOT_SEPARATOR = '.';
    public final static char DOLLAR_SEPARATOR = '$';

    public final static char METHOD_MARKER = '(';
    public final static char PARAMETER_SEPARATOR = ',';

    public final static Pattern PARAMETER_SPLITTER_PATTERN = Pattern.compile("\\" + Constants.PARAMETER_SEPARATOR);

    // Type markers, separators and patterns
    public final static char ARRAY_MARKER = '[';
    public final static char CLASS_MARKER = 'L';

    public final static char INTERSECTION_MARKER = '&';
    public final static char INTERSECTION_SEPARATOR = '|';

    public final static Pattern INTERSECTION_SPLITTER_PATTERN = Pattern.compile("\\" + Constants.INTERSECTION_SEPARATOR);
    public final static Pattern DOLLAR_SPLITTER_PATTERN = Pattern.compile("\\" + Constants.DOLLAR_SEPARATOR);

    // Magic Strings
    public final static String ARRAY_TYPE = "$$ARRAY_TYPE$$";
    public final static String PACKAGE_DECLARATION_FILENAME = "package-info.java";

    public final static String CONSTRUCTOR_NAME = "<init>";
    public final static String INSTANCE_INITIALIZER_NAME = ">init<";
    public final static String CLASS_INITIALIZER_NAME = "<clinit>";

    // Gloabl Java API names
    public final static String PACKAGE_JAVA_LANG = "java.lang";

    public final static String PATH_TO_PACKAGE_JAVA_LANG = "java/lang/";
    public final static String CLASSFILE_EXTENSION = ".class";

    public final static String THIS_NAME  = "this";
    public final static String SUPER_NAME = "super";
    public final static String CLASS_NAME = "class";

    public final static String LENGTH_NAME = "length";
    public final static String CLONE_NAME  = "clone";

    public final static String CLASSNAME_OBJECT = "Object";
    public final static String CLASSNAME_CLASS  = "Class";
    public final static String CLASSNAME_VOID   = "Void";

    public final static String CLASSNAME_DOUBLE  = "Double";
    public final static String CLASSNAME_FLOAT   = "Float";
    public final static String CLASSNAME_LONG    = "Long";
    public final static String CLASSNAME_INT     = "Integer";
    public final static String CLASSNAME_SHORT   = "Short";
    public final static String CLASSNAME_BYTE    = "Byte";
    public final static String CLASSNAME_BOOLEAN = "Boolean";
    public final static String CLASSNAME_CHAR    = "Character";
    public final static String CLASSNAME_STRING  = "String";


    public final static String CLASSPATH_OBJECT = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_OBJECT;
    public final static String CLASSPATH_CLASS  = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_CLASS;
    public final static String CLASSPATH_VOID   = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_VOID;

    public final static String CLASSPATH_DOUBLE  = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_DOUBLE;
    public final static String CLASSPATH_FLOAT   = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_FLOAT;
    public final static String CLASSPATH_LONG    = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_LONG;
    public final static String CLASSPATH_INT     = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_INT;
    public final static String CLASSPATH_SHORT   = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_SHORT;
    public final static String CLASSPATH_BYTE    = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_BYTE;
    public final static String CLASSPATH_BOOLEAN = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_BOOLEAN;
    public final static String CLASSPATH_CHAR    = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_CHAR;
    public final static String CLASSPATH_STRING  = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR + Constants.CLASSNAME_STRING;


    public final static String CLASS_BINARYNAME_OBJECT = Constants.CLASS_MARKER + Constants.CLASSPATH_OBJECT;
    public final static String CLASS_BINARYNAME_CLASS  = Constants.CLASS_MARKER + Constants.CLASSPATH_CLASS;
    public final static String CLASS_BINARYNAME_VOID   = Constants.CLASS_MARKER + Constants.CLASSPATH_VOID;

    public final static String CLASS_BINARYNAME_DOUBLE  = Constants.CLASS_MARKER + Constants.CLASSPATH_DOUBLE;
    public final static String CLASS_BINARYNAME_FLOAT   = Constants.CLASS_MARKER + Constants.CLASSPATH_FLOAT;
    public final static String CLASS_BINARYNAME_LONG    = Constants.CLASS_MARKER + Constants.CLASSPATH_LONG;
    public final static String CLASS_BINARYNAME_INT     = Constants.CLASS_MARKER + Constants.CLASSPATH_INT;
    public final static String CLASS_BINARYNAME_SHORT   = Constants.CLASS_MARKER + Constants.CLASSPATH_SHORT;
    public final static String CLASS_BINARYNAME_BYTE    = Constants.CLASS_MARKER + Constants.CLASSPATH_BYTE;
    public final static String CLASS_BINARYNAME_BOOLEAN = Constants.CLASS_MARKER + Constants.CLASSPATH_BOOLEAN;
    public final static String CLASS_BINARYNAME_CHAR    = Constants.CLASS_MARKER + Constants.CLASSPATH_CHAR;
    public final static String CLASS_BINARYNAME_STRING  = Constants.CLASS_MARKER + Constants.CLASSPATH_STRING;
}
