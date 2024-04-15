package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.util.AbstractJCTContainer;
abstract class JCTElement
extends jct.test.rsc.jct.util.AbstractJCTContainer
implements jct.test.rsc.jct.kernel.IJCTElement
{
private jct.test.rsc.jct.kernel.impl.JCTElementContainer enclosingElement;

final private jct.test.rsc.jct.kernel.IJCTRootNode rootNode;

private java.lang.String name = null;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode, null);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name)
{
this.<init>();
this.rootNode = null == aRootNode && this instanceof jct.test.rsc.jct.kernel.IJCTRootNode ? (jct.test.rsc.jct.kernel.IJCTRootNode)this : aRootNode;
this.name = name;

}

public jct.test.rsc.jct.kernel.IJCTElement getEnclosingElement()
{
return this.enclosingElement;

}

public jct.test.rsc.jct.kernel.IJCTRootNode getRootNode()
{
return this.rootNode;

}

public java.lang.String getID()
{
return super.toString();

}

public java.lang.String getSourceCode()
{
try
{
return this.getSourceCode(new java.io.StringWriter()).toString();

}
catch(java.io.IOException e) 
{
throw new java.lang.RuntimeException(e);

}

}

public java.util.Collection getEnclosedElements()
{
throw new java.lang.UnsupportedOperationException("It is impossible to extract enclosed elements from a leaf.");

}

public void setName(final java.lang.String name)
{
this.name = name;

}

public java.lang.String getName()
{
return this.name;

}

private java.lang.ref.SoftReference cachedPathPartBuilder = new java.lang.ref.SoftReference(null);

void updateEnclosingElement(final jct.test.rsc.jct.kernel.impl.JCTElementContainer e)
{
if(null == this.enclosingElement) ((jct.test.rsc.jct.kernel.impl.JCTRootNode)this.getRootNode()).removeOrphan(this);
 else if(null != this.elements) this.elements.remove(this);
if(e == null) ((jct.test.rsc.jct.kernel.impl.JCTRootNode)this.getRootNode()).addOrphan(this);
 else if(! e.getRootNode().equals(this.getRootNode())) throw new java.lang.IllegalArgumentException("An element's Root Node and its enclosing element's Root Node must be equals");
this.enclosingElement = e;
this.discardCachedPathPartBuilderIndex();

}

final protected void discardCachedPathPartBuilderIndex()
{
if(null != this.cachedPathPartBuilder.get()) this.cachedPathPartBuilder.get().setIndex(null);

}

final public jct.test.rsc.jct.kernel.impl.JCTPath getPath()
{
if(this instanceof jct.test.rsc.jct.kernel.IJCTRootNode) return new jct.test.rsc.jct.kernel.impl.JCTPath();
final jct.test.rsc.jct.kernel.impl.JCTPath p = this.getPath();
p.addPart(this.createPathPart().createPathPart());
return p;

}

protected jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder p = this.cachedPathPartBuilder.get();
if(null == p) 
{
p = new jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder(this.getKind());
this.cachedPathPartBuilder = new java.lang.ref.SoftReference(p);

}
if(null == this.getIndex()) 
{
int i = 0;
final java.util.Iterator it = this.seeNextPathStep(this.getKind()).iterator();
while(it.hasNext()) if(this == it.next()) 
{
this.setIndex(i);
break;

}
 else ++ i;
if(null == this.getIndex()) throw new java.lang.AssertionError("lists: for(List<JCTElementContainer> l : this.seePreviousPathStep())
{
    for(IJCTElement e : l.seeNextPathStep(this.getKind))
        if(this == e)
            continue lists;
    return false;
}
return true;
must always return true. 

This is not valid for :
- enclosing 0 : " + this.seePreviousPathStep().get(0).getClass() + "
- enclosed : " + this.getClass() + "/" + this.getName() + "
- enclosed Kind : " + this.getKind() + "
- enclosed Name : " + this.getName());

}
return this.setData(this.getName());

}

protected java.util.List seePreviousPathStep()
{
return new java.util.LinkedList()
{
void <init>()
{
this.<init>();

}

final private static long serialVersionUID = -3500896847858214798;

final private void >init<()
{
this.add((jct.test.rsc.jct.kernel.impl.JCTElementContainer)(null == this.enclosingElement ? this.getRootNode() : this.enclosingElement));

}

};

}

protected boolean isDesignatedBy(final java.lang.String designator)
{
return designator == null || designator.equals(this.getData());

}

public java.lang.String toString()
{
try
{
return this.getPath().toString();

}
catch(java.lang.Throwable t) 
{
t.printStackTrace();
return this.getClass() + " : " + this.getName();

}

}


}
