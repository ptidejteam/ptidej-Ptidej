package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTErroneousExpression;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTErroneousExpression
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTErroneousExpression
{
final private java.util.List expressions = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return null;

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.IJCTExpression expression : this.getEnclosedElements()) expression.getSourceCode(aWriter);
return aWriter;

}

public void addExpression(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpression aExpression)
{
this.expressions.add(anIndex, aExpression);

}

public void addExpression(final jct.test.rsc.jct.kernel.IJCTExpression aExpression)
{
this.expressions.add(aExpression);

}

public void removeExpression(final jct.test.rsc.jct.kernel.IJCTExpression aExpression)
{
this.expressions.remove(aExpression);

}

public void removeExpression(final int anIndex)
{
this.expressions.remove(anIndex);

}

public java.util.List getExpressions()
{
return java.util.Collections.unmodifiableList(this.expressions);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ERRONEOUS_EXPRESSION;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitErroneousExpression(this, aP);

}


}
