package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTDoWhile;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTDoWhile
extends jct.test.rsc.jct.kernel.impl.JCTDoWhileImpl
implements jct.test.rsc.jct.kernel.IJCTDoWhile
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.<init>(aRootNode, condition);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("do ");
this.getBody().getSourceCode(aWriter).append(" while(");
return this.getCondition().getSourceCode(aWriter).append(");
");

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.DO_WHILE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitDoWhile(this, aP);

}


}
