package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTExpressionStatement;
import jct.test.rsc.jct.kernel.IJCTFor;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTFor
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTFor
{
final private java.util.List initializers = this.createInternalList();

final private jct.test.rsc.jct.util.reference.NotNullableReference condition;

final private java.util.List updaters = this.createInternalList();

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement body)
{
this.<init>(aRootNode);
this.body = this.createInternalReference(body);
this.condition = this.createInternalReference(condition);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.initializers, this.condition, this.updaters, this.body));

}

public void addInitializer(final int anIndex, final jct.test.rsc.jct.kernel.IJCTStatement initializer)
{
this.initializers.add(anIndex, initializer);

}

public void addInitializer(final jct.test.rsc.jct.kernel.IJCTStatement initializer)
{
this.initializers.add(initializer);

}

public void removeInitializer(final jct.test.rsc.jct.kernel.IJCTStatement initializer)
{
this.initializers.remove(initializer);

}

public void removeInitializer(final int anIndex)
{
this.initializers.remove(anIndex);

}

public java.util.List getInitializers()
{
return java.util.Collections.unmodifiableList(this.initializers);

}

public void setCondition(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.condition.set(condition);

}

public jct.test.rsc.jct.kernel.IJCTExpression getCondition()
{
return this.condition.get();

}

public void addUpdater(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpressionStatement updater)
{
this.updaters.add(anIndex, updater);

}

public void addUpdater(final jct.test.rsc.jct.kernel.IJCTExpressionStatement updater)
{
this.updaters.add(updater);

}

public void removeUpdater(final jct.test.rsc.jct.kernel.IJCTExpressionStatement updater)
{
this.updaters.remove(updater);

}

public void removeUpdater(final int anIndex)
{
this.updaters.remove(anIndex);

}

public java.util.List getUpdaters()
{
return java.util.Collections.unmodifiableList(this.updaters);

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
return jct.test.rsc.jct.kernel.JCTKind.FOR;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitFor(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("for(");
final java.util.Iterator sit = this.getInitializers().iterator();
while(sit.hasNext()) 
{
final java.lang.String var = sit.next().getSourceCode();
aWriter.append(var.substring(0, var.length() - 2));
if(sit.hasNext()) aWriter.append(", ");

}
aWriter.append("; ");
this.getCondition().getSourceCode(aWriter).append("; ");
final java.util.Iterator eit = this.getUpdaters().iterator();
while(eit.hasNext()) 
{
final java.lang.String update = eit.next().getSourceCode();
aWriter.append(update.substring(0, update.length() - 2));
if(eit.hasNext()) aWriter.append(", ");

}
aWriter.append(") ");
return this.getBody().getSourceCode(aWriter);

}


}
