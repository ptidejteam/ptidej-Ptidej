package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTAssignment;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTAssignment
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTAssignment
{
final private jct.test.rsc.jct.util.reference.NotNullableReference variable;

final private jct.test.rsc.jct.util.reference.NotNullableReference value;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
this.<init>(aRootNode);
this.variable = this.createInternalReference(variable);
this.value = this.createInternalReference(value);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.variable, this.value));

}

protected java.lang.String getCompoundOperator()
{
return "";

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
this.getVariable().getSourceCode(aWriter).append(' ' + this.getCompoundOperator() + "= ");
return this.getValue().getSourceCode(aWriter);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getVariable().getTypeResult();

}

public void setVariable(final jct.test.rsc.jct.kernel.IJCTExpression variable)
{
this.variable.set(variable);

}

public jct.test.rsc.jct.kernel.IJCTExpression getVariable()
{
return this.variable.get();

}

public void setValue(final jct.test.rsc.jct.kernel.IJCTExpression value)
{
this.value.set(value);

}

public jct.test.rsc.jct.kernel.IJCTExpression getValue()
{
return this.value.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ASSIGNMENT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitAssignment(this, aP);

}


}
