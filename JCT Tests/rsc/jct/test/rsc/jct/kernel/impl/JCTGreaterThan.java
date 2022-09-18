package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTGreaterThan;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTGreaterThan
extends jct.test.rsc.jct.kernel.impl.JCTLogicalBinaryOperator
implements jct.test.rsc.jct.kernel.IJCTGreaterThan
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.<init>(aRootNode, leftOperand, rightOperand);

}

protected java.lang.String getOperator()
{
return ">";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.GREATER_THAN;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitGreaterThan(this, aP);

}


}
