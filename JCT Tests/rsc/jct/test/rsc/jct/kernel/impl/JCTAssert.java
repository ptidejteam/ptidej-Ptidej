package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTAssert;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTAssert
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTAssert
{
final private jct.test.rsc.jct.util.reference.NotNullableReference condition;

final private jct.test.rsc.jct.util.reference.NullableReference detail = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.<init>(aRootNode);
this.condition = this.createInternalReference(condition);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.condition, this.detail));

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("assert ");
this.getCondition().getSourceCode(aWriter);
if(null != this.getDetail()) this.getDetail().getSourceCode(aWriter);
return aWriter.append(";
");

}

public void setCondition(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.condition.set(condition);

}

public jct.test.rsc.jct.kernel.IJCTExpression getCondition()
{
return this.condition.get();

}

public void setDetail(final jct.test.rsc.jct.kernel.IJCTExpression detail)
{
this.detail.set(detail);

}

public jct.test.rsc.jct.kernel.IJCTExpression getDetail()
{
return this.detail.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ASSERT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitAssert(this, aP);

}


}
