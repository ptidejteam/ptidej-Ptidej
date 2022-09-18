package jct.test.rsc.snpsht.parser.verfilesystem;
public class VerFsParserException
extends java.lang.Exception
{
final private static long serialVersionUID = 8347620660800917034;

final public static short MARKER_NOT_FOUND = 0;

final public static short ATTRIBUTE_NOT_FOUND = 1;

final public static short SOURCE_NOT_FOUND = 2;

final public static short PARSING_FAILED = 3;

final public static short UNEXPECTED_NODE_VALUE = 4;

final public static short ILLEGAL_MANAGER_TYPE = 5;

final public static short MISFORMED_ELEMENT = 6;

final public static short BAD_XPATH = 7;

private short type;

public void <init>(short type, java.lang.String message)
{
this.<init>(message);
this.type = type;

}

public void <init>(short type, java.lang.Throwable e)
{
this.<init>(e);
this.type = type;

}

public short getType()
{
return this.type;

}


}
