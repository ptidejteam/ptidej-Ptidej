package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTUnaryOperator;
abstract class JCTPostfixedUnaryOperator
extends jct.test.rsc.jct.kernel.impl.JCTUnaryOperator
implements jct.test.rsc.jct.kernel.IJCTUnaryOperator
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
this.<init>(aRootNode, operand);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return this.getOperand().getSourceCode(aWriter).append(' ' + this.getOperator());

}


}
