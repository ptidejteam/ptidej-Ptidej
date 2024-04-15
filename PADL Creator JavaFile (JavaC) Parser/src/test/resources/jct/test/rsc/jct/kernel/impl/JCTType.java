package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
abstract class JCTType
extends jct.test.rsc.jct.kernel.impl.JCTElementContainer
implements jct.test.rsc.jct.kernel.IJCTType
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
return this.createPathPart().setData(this.getTypeName());

}

public boolean equals(final java.lang.Object o)
{
if(! (o instanceof jct.test.rsc.jct.kernel.IJCTType)) return false;
return this.getTypeName().equals(((jct.test.rsc.jct.kernel.IJCTType)o).getTypeName());

}


}
