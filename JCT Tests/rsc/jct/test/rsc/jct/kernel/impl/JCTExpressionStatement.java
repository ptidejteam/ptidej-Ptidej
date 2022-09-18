package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTExpressionStatement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTExpressionStatement
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTExpressionStatement
{
final private jct.test.rsc.jct.util.reference.NotNullableReference expression;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
this.<init>(aRootNode);
this.expression = this.createInternalReference(expression);
this.backpatchElements(this.expression);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return this.getExpression().getSourceCode(aWriter).append(";
");

}

public void setExpression(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
this.expression.set(expression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getExpression()
{
return this.expression.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.EXPRESSION_STATEMENT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitExpressionStatement(this, aP);

}


}
