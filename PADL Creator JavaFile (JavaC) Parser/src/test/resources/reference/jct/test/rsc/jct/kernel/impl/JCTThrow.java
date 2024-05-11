package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTThrow;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTThrow
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTThrow
{
final private jct.test.rsc.jct.util.reference.NotNullableReference thrownException;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression thrownException)
{
this.<init>(aRootNode);
this.thrownException = this.createInternalReference(thrownException);
this.backpatchElements(this.thrownException);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("throw ");
return this.getThrownException().getSourceCode(aWriter).append(";
");

}

public void setThrownException(final jct.test.rsc.jct.kernel.IJCTExpression thrownException)
{
this.thrownException.set(thrownException);

}

public jct.test.rsc.jct.kernel.IJCTExpression getThrownException()
{
return this.thrownException.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.THROW;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitThrow(this, aP);

}


}
