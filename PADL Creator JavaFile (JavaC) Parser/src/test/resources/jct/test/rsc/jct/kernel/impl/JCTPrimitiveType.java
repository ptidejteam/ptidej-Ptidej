package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
class JCTPrimitiveType
extends jct.test.rsc.jct.kernel.impl.JCTType
implements jct.test.rsc.jct.kernel.IJCTPrimitiveType
{
final private jct.test.rsc.jct.kernel.JCTPrimitiveTypes type;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{
this.<init>(aRootNode);
this.type = aPrimitiveTypeConstant;

}

public java.lang.String getTypeName()
{
return this.getType().getTypeName();

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return aWriter.append(this.getType().getSourceCode());

}

public jct.test.rsc.jct.kernel.JCTPrimitiveTypes getType()
{
return this.type;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.PRIMITIVE_TYPE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitPrimitiveType(this, aP);

}


}
