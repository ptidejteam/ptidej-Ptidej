package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTBitwiseComplement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTBitwiseComplement
extends jct.test.rsc.jct.kernel.impl.JCTPrefixedUnaryOperator
implements jct.test.rsc.jct.kernel.IJCTBitwiseComplement
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.<init>(aRootNode, operand);

}

protected java.lang.String getOperator()
{
return "~";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.BITWISE_COMPLEMENT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitBitwiseComplement(this, aP);

}


}
