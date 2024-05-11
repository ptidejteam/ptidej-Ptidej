package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTVariable
extends jct.test.rsc.jct.kernel.impl.JCTVariableImpl
implements jct.test.rsc.jct.kernel.IJCTVariable
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{
this.<init>(aRootNode, name, type, jct.test.rsc.jct.kernel.JCTKind.VARIABLE);

}


}
