package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPathPart;
import jct.test.rsc.jct.kernel.JCTKind;
public class JCTPath
extends jct.test.rsc.jct.kernel.impl.JCTPathPart
implements jct.test.rsc.jct.kernel.IJCTPath
{
public void <init>()
{
this.<init>(jct.test.rsc.jct.kernel.JCTKind.ROOT_NODE, null);

}

void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index)
{
this.<init>(resultKind, index);

}

void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index, final java.lang.String data)
{
this.<init>(resultKind, index, data);

}

void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index, final java.lang.String data, final byte[] informativeData)
{
this.<init>(resultKind, index, data, informativeData);

}

public jct.test.rsc.jct.kernel.IJCTPathPart getFirstPart()
{
return super.getNextPart();

}

public jct.test.rsc.jct.kernel.IJCTPath getPathToEnclosing()
{
final jct.test.rsc.jct.kernel.IJCTPath result = new jct.test.rsc.jct.kernel.impl.JCTPath();
result.addPart(null == this.getFirstPart() ? null : ((jct.test.rsc.jct.kernel.impl.JCTPathPart)this.getFirstPart()).getPathPartToEnclosing());
return result;

}

public jct.test.rsc.jct.kernel.JCTKind getResultKind()
{
return this == this.getLastPart() ? super.getResultKind() : this.getLastPart().getResultKind();

}

public java.lang.String getData()
{
return this == this.getLastPart() ? super.getData() : this.getLastPart().getData();

}

public java.lang.Integer getIndex()
{
return this == this.getLastPart() ? super.getIndex() : this.getLastPart().getIndex();

}

public byte[] getInformativeData()
{
return this == this.getLastPart() ? super.getInformativeData() : this.getLastPart().getInformativeData();

}

public jct.test.rsc.jct.kernel.impl.JCTPath clone()
{
return (jct.test.rsc.jct.kernel.impl.JCTPath)super.clone();

}

public boolean equals(final java.lang.Object that)
{
if(this == that) return true;
if(! (that instanceof jct.test.rsc.jct.kernel.IJCTPath)) return false;
jct.test.rsc.jct.kernel.IJCTPathPart thisPart = this.getFirstPart();
jct.test.rsc.jct.kernel.IJCTPathPart thatPart = ((jct.test.rsc.jct.kernel.IJCTPath)that).getFirstPart();
while(null != thisPart) if(! thisPart.equals(thatPart)) return false;
 else 
{
thisPart = thisPart.getNextPart();
thatPart = thatPart.getNextPart();

}
return null == thatPart;

}

public boolean isEnclosing(final jct.test.rsc.jct.kernel.IJCTPath that)
{
jct.test.rsc.jct.kernel.IJCTPathPart thisPart = this.getFirstPart();
jct.test.rsc.jct.kernel.IJCTPathPart thatPart = that.getFirstPart();
while(null != thisPart) if(! thisPart.equals(thatPart)) return false;
 else 
{
thisPart = thisPart.getNextPart();
thatPart = thatPart.getNextPart();

}
return true;

}


}
