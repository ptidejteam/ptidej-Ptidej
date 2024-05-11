package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTBinaryOperator;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
abstract class JCTBinaryOperator
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTBinaryOperator
{
final private jct.test.rsc.jct.util.reference.NotNullableReference leftOperand;

final private jct.test.rsc.jct.util.reference.NotNullableReference rightOperand;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.<init>(aRootNode);
this.leftOperand = this.createInternalReference(leftOperand);
this.rightOperand = this.createInternalReference(rightOperand);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.leftOperand, this.rightOperand));

}

abstract protected java.lang.String getOperator()
{

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
this.getLeftOperand().getSourceCode(aWriter).append(" " + this.getOperator() + " ");
return this.getRightOperand().getSourceCode(aWriter);

}

public void setLeftOperand(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand)
{
this.leftOperand.set(leftOperand);

}

public jct.test.rsc.jct.kernel.IJCTExpression getLeftOperand()
{
return this.leftOperand.get();

}

public void setRightOperand(final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.rightOperand.set(rightOperand);

}

public jct.test.rsc.jct.kernel.IJCTExpression getRightOperand()
{
return this.rightOperand.get();

}


}
