package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTUnaryOperator;
import jct.test.rsc.jct.util.reference.NotNullableReference;
abstract class JCTUnaryOperator
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTUnaryOperator
{
final private jct.test.rsc.jct.util.reference.NotNullableReference operand;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.<init>(aRootNode);
this.operand = this.createInternalReference(operand);
this.backpatchElements(this.operand);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getOperand().getTypeResult();

}

abstract protected java.lang.String getOperator()
{

}

public void setOperand(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.operand.set(operand);

}

public jct.test.rsc.jct.kernel.IJCTExpression getOperand()
{
return this.operand.get();

}


}
