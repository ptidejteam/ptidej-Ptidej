package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTCharacterLiteral;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
class JCTCharacterLiteral
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTCharacterLiteral
{
private char value;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final char value)
{
this.<init>(aRootNode);
this.value = value;

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return aWriter.append("'" + this.value + "'");

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.CHARACTER);

}

public void setValue(final char value)
{
this.value = value;

}

public char getValue()
{
return this.value;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CHARACTER_LITERAL;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitCharacterLiteral(this, aP);

}


}
