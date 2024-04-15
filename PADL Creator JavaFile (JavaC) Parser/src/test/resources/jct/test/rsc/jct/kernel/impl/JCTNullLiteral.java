package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTNullLiteral;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
class JCTNullLiteral
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTNullLiteral
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return aWriter.append("null");

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.NULL_LITERAL;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitNullLiteral(this, aP);

}


}
