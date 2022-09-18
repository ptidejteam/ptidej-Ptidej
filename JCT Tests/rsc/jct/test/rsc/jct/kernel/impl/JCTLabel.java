package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTLabel;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTLabel
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTLabel
{
final private jct.test.rsc.jct.util.reference.NotNullableReference statement;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTStatement statement)
{
this.<init>(aRootNode, name);
this.statement = this.createInternalReference(statement);
this.backpatchElements(this.statement);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append(this.getName()).append(": ");
return this.getStatement().getSourceCode(aWriter);

}

public void setStatement(final jct.test.rsc.jct.kernel.IJCTStatement statement)
{
this.statement.set(statement);

}

public jct.test.rsc.jct.kernel.IJCTStatement getStatement()
{
return this.statement.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.LABEL;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitLabel(this, aP);

}


}
