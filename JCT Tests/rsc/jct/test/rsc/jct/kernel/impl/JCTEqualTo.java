package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTEqualTo;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTEqualTo
extends jct.test.rsc.jct.kernel.impl.JCTLogicalBinaryOperator
implements jct.test.rsc.jct.kernel.IJCTEqualTo
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.<init>(aRootNode, leftOperand, rightOperand);

}

protected java.lang.String getOperator()
{
return "==";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.EQUAL_TO;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitEqualTo(this, aP);

}


}
