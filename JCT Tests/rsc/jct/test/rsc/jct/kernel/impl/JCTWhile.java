package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.IJCTWhile;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTWhile
extends jct.test.rsc.jct.kernel.impl.JCTDoWhileImpl
implements jct.test.rsc.jct.kernel.IJCTWhile
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.<init>(aRootNode, condition);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("while(");
this.getCondition().getSourceCode(aWriter).append(") ");
return this.getBody().getSourceCode(aWriter);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.WHILE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitWhile(this, aP);

}


}
