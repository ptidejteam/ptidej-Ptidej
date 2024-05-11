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

package jct.kernel;

import java.util.regex.Pattern;

/**
 * Constants list for JCT interfaces
 */
public interface Constants {
	// Path separators
	char DOT_SEPARATOR = '.';
	char DOLLAR_SEPARATOR = '$';

	char METHOD_MARKER = '(';
	char PARAMETER_SEPARATOR = ',';

	Pattern PARAMETER_SPLITTER_PATTERN = Pattern
			.compile("\\" + Constants.PARAMETER_SEPARATOR);

	// Type markers, separators and patterns
	char ARRAY_MARKER = '[';
	char CLASS_MARKER = 'L';

	char INTERSECTION_MARKER = '&';
	char INTERSECTION_SEPARATOR = '|';

	Pattern INTERSECTION_SPLITTER_PATTERN = Pattern
			.compile("\\" + Constants.INTERSECTION_SEPARATOR);
	Pattern DOLLAR_SPLITTER_PATTERN = Pattern
			.compile("\\" + Constants.DOLLAR_SEPARATOR);

	// Magic Strings
	String ARRAY_TYPE = "$$ARRAY_TYPE$$";
	String PACKAGE_DECLARATION_FILENAME = "package-info.java";

	String CONSTRUCTOR_NAME = "<init>";
	String INSTANCE_INITIALIZER_NAME = ">init<";
	String CLASS_INITIALIZER_NAME = "<clinit>";

	// Gloabl Java API names
	String PACKAGE_JAVA_LANG = "java.lang";

	String PATH_TO_PACKAGE_JAVA_LANG = "java/lang/";
	String CLASSFILE_EXTENSION = ".class";

	String THIS_NAME = "this";
	String SUPER_NAME = "super";
	String CLASS_NAME = "class";

	String LENGTH_NAME = "length";
	String CLONE_NAME = "clone";

	String CLASSNAME_OBJECT = "Object";
	String CLASSNAME_CLASS = "Class";
	String CLASSNAME_VOID = "Void";

	String CLASSNAME_DOUBLE = "Double";
	String CLASSNAME_FLOAT = "Float";
	String CLASSNAME_LONG = "Long";
	String CLASSNAME_INT = "Integer";
	String CLASSNAME_SHORT = "Short";
	String CLASSNAME_BYTE = "Byte";
	String CLASSNAME_BOOLEAN = "Boolean";
	String CLASSNAME_CHAR = "Character";
	String CLASSNAME_STRING = "String";

	String CLASSPATH_OBJECT = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_OBJECT;
	String CLASSPATH_CLASS = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_CLASS;
	String CLASSPATH_VOID = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_VOID;

	String CLASSPATH_DOUBLE = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_DOUBLE;
	String CLASSPATH_FLOAT = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_FLOAT;
	String CLASSPATH_LONG = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_LONG;
	String CLASSPATH_INT = Constants.PACKAGE_JAVA_LANG + Constants.DOT_SEPARATOR
			+ Constants.CLASSNAME_INT;
	String CLASSPATH_SHORT = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_SHORT;
	String CLASSPATH_BYTE = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_BYTE;
	String CLASSPATH_BOOLEAN = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_BOOLEAN;
	String CLASSPATH_CHAR = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_CHAR;
	String CLASSPATH_STRING = Constants.PACKAGE_JAVA_LANG
			+ Constants.DOT_SEPARATOR + Constants.CLASSNAME_STRING;

	String CLASS_BINARYNAME_OBJECT = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_OBJECT;
	String CLASS_BINARYNAME_CLASS = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_CLASS;
	String CLASS_BINARYNAME_VOID = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_VOID;

	String CLASS_BINARYNAME_DOUBLE = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_DOUBLE;
	String CLASS_BINARYNAME_FLOAT = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_FLOAT;
	String CLASS_BINARYNAME_LONG = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_LONG;
	String CLASS_BINARYNAME_INT = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_INT;
	String CLASS_BINARYNAME_SHORT = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_SHORT;
	String CLASS_BINARYNAME_BYTE = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_BYTE;
	String CLASS_BINARYNAME_BOOLEAN = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_BOOLEAN;
	String CLASS_BINARYNAME_CHAR = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_CHAR;
	String CLASS_BINARYNAME_STRING = Constants.CLASS_MARKER
			+ Constants.CLASSPATH_STRING;
}
