package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSynchronized;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTSynchronized
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTSynchronized
{
final private jct.test.rsc.jct.util.reference.NotNullableReference synchronizedObject;

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression synchronizedObject)
{
this.<init>(aRootNode);
this.synchronizedObject = this.createInternalReference(synchronizedObject);
this.body = this.createInternalReference(this.getRootNode().getFactory().createBlock());
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.synchronizedObject, this.body));

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("synchronized(");
this.getSynchronizedObject().getSourceCode(aWriter).append(')');
return this.getBody().getSourceCode(aWriter);

}

public void setSynchronizedObject(final jct.test.rsc.jct.kernel.IJCTExpression synchronizedObject)
{
this.synchronizedObject.set(synchronizedObject);

}

public jct.test.rsc.jct.kernel.IJCTExpression getSynchronizedObject()
{
return this.synchronizedObject.get();

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
return jct.test.rsc.jct.kernel.JCTKind.SYNCHRONIZED;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitSynchronized(this, aP);

}


}
