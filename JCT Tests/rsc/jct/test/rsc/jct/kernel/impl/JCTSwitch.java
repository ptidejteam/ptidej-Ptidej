package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTCase;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSwitch;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTSwitch
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTSwitch
{
final private jct.test.rsc.jct.util.reference.NotNullableReference expression;

final private java.util.List cases = this.createInternalList();

final private jct.test.rsc.jct.util.reference.NullableReference defaultCase = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
this.<init>(aRootNode);
this.expression = this.createInternalReference(expression);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.expression, this.cases, this.defaultCase));

}

public void setExpression(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
this.expression.set(expression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getExpression()
{
return this.expression.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.SWITCH;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitSwitch(this, aP);

}

public jct.test.rsc.jct.kernel.IJCTCase getDefaultCase()
{
return this.defaultCase.get();

}

public void setDefaultCase(final jct.test.rsc.jct.kernel.IJCTCase c)
{
if(! c.isDefaultCase()) throw new java.lang.IllegalArgumentException("Use add/remove/get Case to modify normal cases");
this.defaultCase.set(c);

}

public java.util.List getCases()
{
return java.util.Collections.unmodifiableList(this.cases);

}

public void addCase(final int i, final jct.test.rsc.jct.kernel.IJCTCase c)
{
if(c.isDefaultCase()) throw new java.lang.IllegalArgumentException("Use get/set DefaultCase to modify the default case");
this.cases.add(i, c);

}

public void addCase(final jct.test.rsc.jct.kernel.IJCTCase c)
{
this.cases.add(c);

}

public void removeCase(final jct.test.rsc.jct.kernel.IJCTCase c)
{
this.cases.remove(c);

}

public void removeCase(final int i)
{
this.cases.remove(i);

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
w.append("switch(");
this.getExpression().getSourceCode(w).append(")
{
");
for(jct.test.rsc.jct.kernel.IJCTCase c : this.getCases()) c.getSourceCode(w);
if(null != this.getDefaultCase()) this.getDefaultCase().getSourceCode(w);
return w.append("}
");

}


}
