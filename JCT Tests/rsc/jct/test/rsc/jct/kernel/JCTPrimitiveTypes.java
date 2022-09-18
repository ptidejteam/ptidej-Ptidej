package jct.test.rsc.jct.kernel;
public class JCTPrimitiveTypes
{
final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes VOID = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("V", "void");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes CHARACTER = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("C", "char");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes BOOLEAN = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("Z", "boolean");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes BYTE = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("B", "byte");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes SHORT = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("S", "short");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes INTEGER = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("I", "int");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes LONG = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("L", "long");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes FLOAT = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("F", "float");

final public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes DOUBLE = new jct.test.rsc.jct.kernel.JCTPrimitiveTypes("D", "double");

final private java.lang.String name;

final private java.lang.String sourceCode;

void <init>(final java.lang.String name, final java.lang.String sourceCode)
{
this.<init>();
this.name = name;
this.sourceCode = sourceCode;

}

public java.lang.String getTypeName()
{
return this.name;

}

public java.lang.String getSourceCode()
{
return this.sourceCode;

}

public static jct.test.rsc.jct.kernel.JCTPrimitiveTypes resolveType(final java.lang.String n)
{
for(jct.test.rsc.jct.kernel.JCTPrimitiveTypes t : <NULL>.values()) if(n.equals(t.getTypeName())) return t;
throw new java.lang.IllegalArgumentException("Invalid Primitive Type Name");

}


}
