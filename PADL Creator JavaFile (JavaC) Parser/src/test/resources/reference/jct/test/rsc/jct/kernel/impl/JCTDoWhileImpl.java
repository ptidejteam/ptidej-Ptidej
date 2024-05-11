package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTElementContainer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
abstract class JCTDoWhileImpl
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTElementContainer, jct.test.rsc.jct.kernel.IJCTSourceCodePart
{
final private jct.test.rsc.jct.util.reference.NotNullableReference condition;

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.<init>(aRootNode);
this.condition = this.createInternalReference(condition);
this.body = this.createInternalReference(((jct.test.rsc.jct.kernel.IJCTStatement)this.getRootNode().getFactory().createEmptyStatement()));
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.condition, this.body));

}

public void setCondition(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.condition.set(condition);

}

public jct.test.rsc.jct.kernel.IJCTExpression getCondition()
{
return this.condition.get();

}

public void setBody(final jct.test.rsc.jct.kernel.IJCTStatement body)
{
this.body.set(body);

}

public jct.test.rsc.jct.kernel.IJCTStatement getBody()
{
return this.body.get();

}


}
