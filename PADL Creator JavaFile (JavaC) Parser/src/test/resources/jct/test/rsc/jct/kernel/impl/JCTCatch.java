package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTCatch;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTCatch
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTCatch
{
final private jct.test.rsc.jct.util.reference.NotNullableReference variable;

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTVariable variable)
{
this.<init>(aRootNode);
this.body = this.createInternalReference(this.getRootNode().getFactory().createBlock());
this.variable = this.createInternalReference(variable);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.variable, this.body));

}

public void setVariable(final jct.test.rsc.jct.kernel.IJCTVariable variable)
{
this.variable.set(variable);

}

public jct.test.rsc.jct.kernel.IJCTVariable getVariable()
{
return this.variable.get();

}

public void setBody(final jct.test.rsc.jct.kernel.IJCTBlock body)
{
this.body.set(body);

}

public jct.test.rsc.jct.kernel.IJCTBlock getBody()
{
return this.body.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CATCH;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitCatch(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("catch(");
final jct.test.rsc.jct.kernel.IJCTVariable v = this.getVariable();
if(null != v.getType()) v.getType().getSourceCode(aWriter).append(' ');
aWriter.append(v.getName()).append(") ");
return this.getBody().getSourceCode(aWriter);

}


}
