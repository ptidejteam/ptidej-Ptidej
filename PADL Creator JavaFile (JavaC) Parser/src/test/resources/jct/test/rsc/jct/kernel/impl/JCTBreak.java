package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTBreak;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTBreak
extends jct.test.rsc.jct.kernel.impl.JCTBreakContinue
implements jct.test.rsc.jct.kernel.IJCTBreak
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

protected java.lang.String getKeyword()
{
return "break";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.BREAK;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitBreak(this, aP);

}


}
