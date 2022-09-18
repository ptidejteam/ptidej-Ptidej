package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTCast;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTCast
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTCast
{
final private jct.test.rsc.jct.util.reference.NotNullableReference operand;

private jct.test.rsc.jct.kernel.IJCTType type;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTType type, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.<init>(aRootNode);
this.operand = this.createInternalReference(operand);
this.backpatchElements(this.operand);
this.setType(type);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("(");
this.getType().getSourceCode(aWriter).append(")");
return this.getOperand().getSourceCode(aWriter);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.type;

}

public void setOperand(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.operand.set(operand);

}

public jct.test.rsc.jct.kernel.IJCTExpression getOperand()
{
return this.operand.get();

}

public void setType(final jct.test.rsc.jct.kernel.IJCTType type)
{
this.type = type;

}

public jct.test.rsc.jct.kernel.IJCTType getType()
{
return this.type;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CAST;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitCast(this, aP);

}


}
