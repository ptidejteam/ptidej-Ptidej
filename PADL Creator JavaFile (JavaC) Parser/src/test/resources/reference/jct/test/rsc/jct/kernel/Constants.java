package jct.test.rsc.jct.kernel;
import java.util.regex.Pattern;
final public class Constants
{
private void <init>()
{
this.<init>();

}

final public static char DOT_SEPARATOR = '.';

final public static char DOLLAR_SEPARATOR = '$';

final public static char METHOD_MARKER = '(';

final public static char PARAMETER_SEPARATOR = ',';

final public static java.util.regex.Pattern PARAMETER_SPLITTER_PATTERN = java.util.regex.Pattern.compile("\" + jct.test.rsc.jct.kernel.Constants.PARAMETER_SEPARATOR);

final public static char ARRAY_MARKER = '[';

final public static char CLASS_MARKER = 'L';

final public static char INTERSECTION_MARKER = '&';

final public static char INTERSECTION_SEPARATOR = '|';

final public static java.util.regex.Pattern INTERSECTION_SPLITTER_PATTERN = java.util.regex.Pattern.compile("\" + jct.test.rsc.jct.kernel.Constants.INTERSECTION_SEPARATOR);

final public static java.util.regex.Pattern DOLLAR_SPLITTER_PATTERN = java.util.regex.Pattern.compile("\" + jct.test.rsc.jct.kernel.Constants.DOLLAR_SEPARATOR);

final public static java.lang.String ARRAY_TYPE = "$$ARRAY_TYPE$$";

final public static java.lang.String PACKAGE_DECLARATION_FILENAME = "package-info.java";

final public static java.lang.String CONSTRUCTOR_NAME = "<init>";

final public static java.lang.String INSTANCE_INITIALIZER_NAME = ">init<";

final public static java.lang.String CLASS_INITIALIZER_NAME = "<clinit>";

final public static java.lang.String PACKAGE_JAVA_LANG = "java.lang";

final public static java.lang.String PATH_TO_PACKAGE_JAVA_LANG = "java/lang/";

final public static java.lang.String CLASSFILE_EXTENSION = ".class";

final public static java.lang.String THIS_NAME = "this";

final public static java.lang.String SUPER_NAME = "super";

final public static java.lang.String CLASS_NAME = "class";

final public static java.lang.String LENGTH_NAME = "length";

final public static java.lang.String CLONE_NAME = "clone";

final public static java.lang.String CLASSNAME_OBJECT = "Object";

final public static java.lang.String CLASSNAME_CLASS = "Class";

final public static java.lang.String CLASSNAME_VOID = "Void";

final public static java.lang.String CLASSNAME_DOUBLE = "Double";

final public static java.lang.String CLASSNAME_FLOAT = "Float";

final public static java.lang.String CLASSNAME_LONG = "Long";

final public static java.lang.String CLASSNAME_INT = "Integer";

final public static java.lang.String CLASSNAME_SHORT = "Short";

final public static java.lang.String CLASSNAME_BYTE = "Byte";

final public static java.lang.String CLASSNAME_BOOLEAN = "Boolean";

final public static java.lang.String CLASSNAME_CHAR = "Character";

final public static java.lang.String CLASSNAME_STRING = "String";

final public static java.lang.String CLASSPATH_OBJECT = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_OBJECT;

final public static java.lang.String CLASSPATH_CLASS = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_CLASS;

final public static java.lang.String CLASSPATH_VOID = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_VOID;

final public static java.lang.String CLASSPATH_DOUBLE = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_DOUBLE;

final public static java.lang.String CLASSPATH_FLOAT = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_FLOAT;

final public static java.lang.String CLASSPATH_LONG = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_LONG;

final public static java.lang.String CLASSPATH_INT = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_INT;

final public static java.lang.String CLASSPATH_SHORT = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_SHORT;

final public static java.lang.String CLASSPATH_BYTE = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_BYTE;

final public static java.lang.String CLASSPATH_BOOLEAN = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_BOOLEAN;

final public static java.lang.String CLASSPATH_CHAR = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_CHAR;

final public static java.lang.String CLASSPATH_STRING = jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + jct.test.rsc.jct.kernel.Constants.CLASSNAME_STRING;

final public static java.lang.String CLASS_BINARYNAME_OBJECT = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_OBJECT;

final public static java.lang.String CLASS_BINARYNAME_CLASS = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_CLASS;

final public static java.lang.String CLASS_BINARYNAME_VOID = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_VOID;

final public static java.lang.String CLASS_BINARYNAME_DOUBLE = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_DOUBLE;

final public static java.lang.String CLASS_BINARYNAME_FLOAT = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_FLOAT;

final public static java.lang.String CLASS_BINARYNAME_LONG = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_LONG;

final public static java.lang.String CLASS_BINARYNAME_INT = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_INT;

final public static java.lang.String CLASS_BINARYNAME_SHORT = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_SHORT;

final public static java.lang.String CLASS_BINARYNAME_BYTE = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_BYTE;

final public static java.lang.String CLASS_BINARYNAME_BOOLEAN = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_BOOLEAN;

final public static java.lang.String CLASS_BINARYNAME_CHAR = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_CHAR;

final public static java.lang.String CLASS_BINARYNAME_STRING = jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + jct.test.rsc.jct.kernel.Constants.CLASSPATH_STRING;


}
