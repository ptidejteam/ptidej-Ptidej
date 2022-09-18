package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTReturn;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTReturn
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTReturn
{
final private jct.test.rsc.jct.util.reference.NullableReference returnedExpression = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);
this.backpatchElements(this.returnedExpression);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("return");
if(null != this.getReturnedExpression()) this.getReturnedExpression().getSourceCode(aWriter.append(" "));
return aWriter.append(";
");

}

public void setReturnedExpression(final jct.test.rsc.jct.kernel.IJCTExpression returnedExpression)
{
this.returnedExpression.set(returnedExpression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getReturnedExpression()
{
return this.returnedExpression.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.RETURN;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitReturn(this, aP);

}


}
