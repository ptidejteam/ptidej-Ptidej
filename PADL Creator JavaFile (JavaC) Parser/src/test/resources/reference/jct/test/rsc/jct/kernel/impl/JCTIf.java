package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTIf;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTIf
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTIf
{
final private jct.test.rsc.jct.util.reference.NotNullableReference condition;

final private jct.test.rsc.jct.util.reference.NotNullableReference thenStatement;

final private jct.test.rsc.jct.util.reference.NullableReference elseStatement = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement thenStatement)
{
this.<init>(aRootNode);
this.condition = this.createInternalReference(condition);
this.thenStatement = this.createInternalReference(thenStatement);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.condition, this.thenStatement, this.elseStatement));

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("if(");
this.getCondition().getSourceCode(aWriter).append(") ");
this.getThenStatement().getSourceCode(aWriter);
if(null != this.getElseStatement()) this.getElseStatement().getSourceCode(aWriter.append(" else "));
return aWriter;

}

public void setCondition(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.condition.set(condition);

}

public jct.test.rsc.jct.kernel.IJCTExpression getCondition()
{
return this.condition.get();

}

public void setThenStatement(final jct.test.rsc.jct.kernel.IJCTStatement thenStatement)
{
this.thenStatement.set(thenStatement);

}

public jct.test.rsc.jct.kernel.IJCTStatement getThenStatement()
{
return this.thenStatement.get();

}

public void setElseStatement(final jct.test.rsc.jct.kernel.IJCTStatement elseStatement)
{
this.elseStatement.set(elseStatement);

}

public jct.test.rsc.jct.kernel.IJCTStatement getElseStatement()
{
return this.elseStatement.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.IF;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitIf(this, aP);

}


}
