package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTUnsignedRightShift;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTUnsignedRightShift
extends jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator
implements jct.test.rsc.jct.kernel.IJCTUnsignedRightShift
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.<init>(aRootNode, leftOperand, rightOperand);

}

protected java.lang.String getOperator()
{
return ">>>";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.UNSIGNED_RIGHT_SHIFT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitUnsignedRightShift(this, aP);

}


}
