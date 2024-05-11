package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTEnhancedFor;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTEnhancedFor
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTEnhancedFor
{
final private jct.test.rsc.jct.util.reference.NotNullableReference variable;

final private jct.test.rsc.jct.util.reference.NotNullableReference iterable;

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTVariable variable, final jct.test.rsc.jct.kernel.IJCTExpression iterable, final jct.test.rsc.jct.kernel.IJCTStatement body)
{
this.<init>(aRootNode);
this.variable = this.createInternalReference(variable);
this.iterable = this.createInternalReference(iterable);
this.body = this.createInternalReference(body);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.variable, this.iterable, this.body));

}

public void setVariable(final jct.test.rsc.jct.kernel.IJCTVariable variable)
{
this.variable.set(variable);

}

public jct.test.rsc.jct.kernel.IJCTVariable getVariable()
{
return this.variable.get();

}

public void setIterable(final jct.test.rsc.jct.kernel.IJCTExpression iterable)
{
this.iterable.set(iterable);

}

public jct.test.rsc.jct.kernel.IJCTExpression getIterable()
{
return this.iterable.get();

}

public void setBody(final jct.test.rsc.jct.kernel.IJCTStatement body)
{
this.body.set(body);

}

public jct.test.rsc.jct.kernel.IJCTStatement getBody()
{
return this.body.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ENHANCED_FOR;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitEnhancedFor(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("for(");
final jct.test.rsc.jct.kernel.IJCTVariable v = this.getVariable();
if(null != v.getType()) v.getType().getSourceCode(aWriter).append(' ');
aWriter.append(v.getName()).append(" : ");
this.getIterable().getSourceCode(aWriter).append(") ");
return this.getBody().getSourceCode(aWriter);

}


}
