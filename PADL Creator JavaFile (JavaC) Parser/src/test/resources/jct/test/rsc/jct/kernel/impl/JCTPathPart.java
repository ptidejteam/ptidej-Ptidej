package jct.test.rsc.jct.kernel.impl;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTElementContainer;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTPathPart;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.IJCTContainer;
public class JCTPathPart
implements jct.test.rsc.jct.kernel.IJCTPathPart
{
final private jct.test.rsc.jct.kernel.JCTKind resultKind;

final private java.lang.String data;

final private java.lang.Integer index;

final private byte[] informativeData;

private jct.test.rsc.jct.kernel.impl.JCTPathPart nextPart = null;

private jct.test.rsc.jct.kernel.impl.JCTPathPart lastPart = null;

public jct.test.rsc.jct.kernel.JCTKind getResultKind()
{
return this.resultKind;

}

public java.lang.String getData()
{
return this.data;

}

public java.lang.Integer getIndex()
{
return this.index;

}

public byte[] getInformativeData()
{
return this.informativeData;

}

protected void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind)
{
this.<init>(resultKind, null);

}

public void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index)
{
this.<init>(resultKind, index, null);

}

public void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index, final java.lang.String data)
{
this.<init>(resultKind, index, data, null);

}

public void <init>(final jct.test.rsc.jct.kernel.JCTKind resultKind, final java.lang.Integer index, final java.lang.String data, final byte[] informativeData)
{
this.<init>();
this.resultKind = resultKind;
this.index = index;
this.data = "null".equals(data) ? null : data;
this.informativeData = informativeData;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPart getNextPart()
{
return this.nextPart;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPart getLastPart()
{
if(null == this.lastPart) if(null != this.nextPart) return this.lastPart = this.getLastPart();
 else return this.lastPart = this;
if(null != this.lastPart.getNextPart()) this.lastPart = this.getLastPart();
return this.lastPart;

}

public jct.test.rsc.jct.kernel.impl.JCTPathPart getPathPartToEnclosing()
{
if(null == this.nextPart) return null;
final jct.test.rsc.jct.kernel.impl.JCTPathPart p = new jct.test.rsc.jct.kernel.impl.JCTPathPart(this.resultKind, this.index, this.data);
this.addPart(this.getPathPartToEnclosing());
return p;

}

public void addPart(final jct.test.rsc.jct.kernel.IJCTPathPart p)
{
final jct.test.rsc.jct.kernel.impl.JCTPathPart part;
if(p instanceof jct.test.rsc.jct.kernel.impl.JCTPathPart) part = (jct.test.rsc.jct.kernel.impl.JCTPathPart)p;
 else 
{
part = new jct.test.rsc.jct.kernel.impl.JCTPathPart(p.getResultKind(), p.getIndex(), p.getData(), p.getInformativeData());
jct.test.rsc.jct.kernel.IJCTPathPart it = p.getNextPart();
jct.test.rsc.jct.kernel.impl.JCTPathPart to = part;
while(null != it) 
{
final jct.test.rsc.jct.kernel.impl.JCTPathPart toAdd = new jct.test.rsc.jct.kernel.impl.JCTPathPart(it.getResultKind(), it.getIndex(), it.getData(), it.getInformativeData());
this.addPart(toAdd);
to = toAdd;
it = it.getNextPart();

}

}
if(null != this.nextPart) this.addPart(part);
 else this.nextPart = part;
this.lastPart = part.getLastPart();

}

public jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTElementContainer e)
{
return this.walk(e, e == null || e.getRootNode().isInitialized());

}

protected jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTElementContainer e, final boolean displayError)
{
if(null == e) 
{
if(displayError) java.lang.System.err.println("NoSuchElement :: " + this + " / " + e);
return null;

}
final jct.test.rsc.jct.kernel.IJCTElement r = this.resolve(e);
return null == this.nextPart ? r : this.walk((jct.test.rsc.jct.kernel.IJCTElementContainer)r, displayError);

}

protected jct.test.rsc.jct.kernel.IJCTElement resolve(final jct.test.rsc.jct.kernel.IJCTElementContainer ee)
{
final java.util.Collection ec = ee instanceof jct.test.rsc.jct.kernel.impl.JCTElementContainer ? this.seeNextPathStep(this.resultKind) : ((jct.test.rsc.jct.util.IJCTContainer)ee).getEnclosedElements();
jct.test.rsc.jct.kernel.IJCTElement e = null;
if(null != this.index) 
{
final java.util.Iterator it = ec.iterator();
for(int i = 0; it.hasNext() && i < this.index; ++ i) it.next();
if(it.hasNext()) e = it.next();
 else return null;

}
if(e == null || (e instanceof jct.test.rsc.jct.kernel.impl.JCTElement && ! this.isDesignatedBy(this.data))) for(jct.test.rsc.jct.kernel.IJCTElement el : ec) if(el instanceof jct.test.rsc.jct.kernel.impl.JCTElement && this.isDesignatedBy(this.data)) 
{
e = el;
break;

}
return e;

}

public jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
return this.walk((jct.test.rsc.jct.kernel.IJCTElementContainer)aRootNode);

}

final public static java.lang.String KIND_INDEX_SEPARATOR = "::";

final public static java.lang.String INDEX_DATA_SEPARATOR = ",";

final public static java.lang.String PART_SEPARATOR = "/";

public java.lang.String toString()
{
final java.lang.StringBuffer result = new java.lang.StringBuffer();
result.append(this.resultKind.toString()).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR).append(null == this.index ? null : this.index.toString()).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append(this.data).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR);
if(null == this.informativeData) result.append("null");
 else for(byte b : this.informativeData) result.append(java.lang.String.format("%02X", b));
return (null == this.getNextPart() ? result : result.append(jct.test.rsc.jct.kernel.impl.JCTPathPart.PART_SEPARATOR).append(this.toString())).toString();

}

public jct.test.rsc.jct.kernel.impl.JCTPathPart clone()
{
try
{
final jct.test.rsc.jct.kernel.impl.JCTPathPart that = (jct.test.rsc.jct.kernel.impl.JCTPathPart)super.clone();
that.nextPart = this.clone();
that.lastPart = null;
return that;

}
catch(java.lang.CloneNotSupportedException ex) 
{
throw new java.lang.IllegalStateException(ex);

}

}

public boolean equals(java.lang.Object that)
{
if(super.equals(that)) return true;
if(! (that instanceof jct.test.rsc.jct.kernel.IJCTPathPart)) return false;
final jct.test.rsc.jct.kernel.IJCTPathPart part = (jct.test.rsc.jct.kernel.IJCTPathPart)that;
return this.getResultKind() == part.getResultKind() && (this.getIndex() == null ? part.getIndex() == null : this.getIndex().equals(part.getIndex())) && (this.getData() == null ? part.getData() == null : this.getData().equals(part.getData())) && (this.getInformativeData() == null ? part.getInformativeData() == null : java.util.Arrays.equals(this.getInformativeData(), part.getInformativeData()));

}


}
