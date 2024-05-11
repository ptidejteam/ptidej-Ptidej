package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTMethodInvocation;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTMethodInvocation
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTMethodInvocation
{
final private jct.test.rsc.jct.util.reference.NotNullableReference methodSelector;

final private java.util.List arguments = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTSelector methodSelector)
{
this.<init>(aRootNode);
this.methodSelector = this.createInternalReference(methodSelector);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.methodSelector, this.arguments));

}

public void setMethodSelector(final jct.test.rsc.jct.kernel.IJCTSelector methodSelector)
{
this.methodSelector.set(methodSelector);

}

public jct.test.rsc.jct.kernel.IJCTSelector getMethodSelector()
{
return this.methodSelector.get();

}

public void addArgument(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.add(anIndex, argument);

}

public void addArgument(final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.add(argument);

}

public void removeArgument(final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.remove(argument);

}

public void removeArgument(final int anIndex)
{
this.arguments.remove(anIndex);

}

public java.util.List getArguments()
{
return java.util.Collections.unmodifiableList(this.arguments);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.METHOD_INVOCATION;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitMethodInvocation(this, aP);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
final jct.test.rsc.jct.kernel.IJCTMethod m = this.getMethodSelector().getElement();
if(null == m) return null;
return m.getReturnType();

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
this.getMethodSelector().getSourceCode(w).append('(');
final java.util.Iterator it = this.getArguments().iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(w);
if(it.hasNext()) w.append(", ");

}
return w.append(')');

}


}
