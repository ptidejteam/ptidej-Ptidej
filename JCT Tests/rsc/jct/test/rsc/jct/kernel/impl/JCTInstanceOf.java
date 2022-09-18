package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTInstanceOf;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTInstanceOf
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTInstanceOf
{
final private jct.test.rsc.jct.util.reference.NotNullableReference operand;

private jct.test.rsc.jct.kernel.IJCTType type;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression operand, final jct.test.rsc.jct.kernel.IJCTType type)
{
this.<init>(aRootNode);
this.operand = this.createInternalReference(operand);
this.backpatchElements(this.operand);
this.setType(type);

}

public jct.test.rsc.jct.kernel.IJCTType getType()
{
return this.type;

}

public void setType(final jct.test.rsc.jct.kernel.IJCTType aType)
{
if(jct.test.rsc.jct.kernel.JCTKind.PRIMITIVE_TYPE == aType.getKind()) throw new java.lang.IllegalArgumentException("instanceof can be used only with an array type or a class type");
this.type = aType;

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BOOLEAN);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
this.getOperand().getSourceCode(aWriter).append(" instanceof ");
return this.getType().getSourceCode(aWriter);

}

public void setOperand(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.operand.set(operand);

}

public jct.test.rsc.jct.kernel.IJCTExpression getOperand()
{
return this.operand.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.INSTANCE_OF;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitInstanceOf(this, aP);

}


}
