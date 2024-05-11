package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTPathPartBuilder
{
private jct.test.rsc.jct.kernel.JCTKind kind;

private java.lang.Integer index = null;

private java.lang.String data = null;

private byte[] informativeData = null;

public void <init>(final jct.test.rsc.jct.kernel.JCTKind aKind)
{
this.<init>();
this.kind = aKind;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPart createPathPart()
{
return this.kind == null ? null : new jct.test.rsc.jct.kernel.impl.JCTPathPart(this.kind, this.index, this.data, this.informativeData);

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder setKind(final jct.test.rsc.jct.kernel.JCTKind kind)
{
this.kind = kind;
return this;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return this.kind;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder setIndex(final java.lang.Integer index)
{
this.index = index;
return this;

}

public java.lang.Integer getIndex()
{
return this.index;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder setData(final java.lang.String data)
{
this.data = data;
return this;

}

public java.lang.String getData()
{
return this.data;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder setInformativeData(final byte[] informativeData)
{
this.informativeData = informativeData;
return this;

}

public byte[] getInformativeData()
{
return this.informativeData;

}


}
