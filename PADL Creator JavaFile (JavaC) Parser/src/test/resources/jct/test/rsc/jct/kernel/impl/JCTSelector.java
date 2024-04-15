package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTIdentifiable;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
abstract class JCTSelector
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTSelector
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public boolean equals(final java.lang.Object o)
{
if(! (o instanceof jct.test.rsc.jct.kernel.IJCTSelector)) return false;
if(null == this.getElement() && null == ((jct.test.rsc.jct.kernel.IJCTSelector)o).getElement()) return true;
if(null == this.getElement() || null == ((jct.test.rsc.jct.kernel.IJCTSelector)o).getElement()) return false;
return ((jct.test.rsc.jct.kernel.IJCTSelector)o).getElement().equals(this.getElement());

}

public int hashCode()
{
return this.getElement().hashCode();

}


}
