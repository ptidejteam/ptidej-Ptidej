/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package common.tools.constants;

public class Constants {
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final String CURRENT_DIR = "." + Constants.SEPARATOR;
	public static final String SEMICOLON = ";";
	public static final String NEW_LINE = "\n";
	public static final String CR_LF = "\r\n";
	public static final String TAB = "\t";
	public static final String POINT = ".";
	public static final String LEFT_BRACE = "{";
	public static final String RIGHT_BRACE = "}";
	public static final String LEFT_BRACKET = "[";
	public static final String RIGHT_BRACKET = "]";
	public static final String SPACE = " ";
	public static final String EMPTY_STR = "";
	public static final String COMMA = ",";
	public static final String LEFT_PARANTHESES = "(";
	public static final String RIGHT_PARANTHESES = ")";
	public static final String LESS_THAN = "<";
	public static final String GREATER_THAN = ">";
	public static final String UNDER_SCORE = "_";
	public static final String COLON = ":";
	public static final String QUOTATION = "\"";
	public static final String SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String EQUAL = "=";
	public static final String DASH = "-";
	public static final String ASTERISK = "*";
	public static final String POND = "#";
	public static final String ZERO = "0";
	public static final String ONE = "1";

	public static final String PUBLIC = "public ";
	public static final String CLASS = " class ";

	public static final String PACKAGE = "package ";
	public static final String IMPORT = "import ";
	public static final String EXTENDS = " extends ";
	public static final String IMPLEMENTS = " implements ";
	public static final String DEPRECATED = "@deprecated";

	public static final String ANONYMOUS_CLASS_NAME = "$AnonymousClass_";

	public static final String JAVA_LANG_OBJ = "java.lang.Object";

	public static final String ZIP_FILE = ".zip";
	public static final String JAR_FILE_EXT = ".jar";
	public static final String XML_FILE_EXT = ".xml";
	public static final String TXT_FILE_EXT = ".txt";
	public static final String JAVA_FILE_EXT = ".java";
	public static final String CLASS_FILE_EX = ".class";

	public static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

	public static final long HOUR_IN_MS = 1000 * 60 * 60;
	public static final long MINUTE_IN_MS = 1000 * 60;
}
