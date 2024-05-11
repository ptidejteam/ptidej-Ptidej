package jct.test.rsc.jct.kernel.impl;
import java.lang.reflect.Field;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTElementContainer;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.ListOfElements;
import jct.test.rsc.jct.util.collection.ListOfUnique;
import jct.test.rsc.jct.util.reference.NotNullableReference;
import jct.test.rsc.jct.util.reference.NullableReference;
import jct.test.rsc.jct.util.reference.StrongReference;
abstract class JCTElementContainer
extends jct.test.rsc.jct.kernel.impl.JCTElement
implements jct.test.rsc.jct.kernel.IJCTElementContainer
{
final protected java.util.Collection elements;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final java.util.Collection elements)
{
this.<init>(aRootNode, name);
this.elements = elements;

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.util.Collection elements)
{
this.<init>(aRootNode);
this.elements = elements;

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name)
{
this.<init>(aRootNode, name, null);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode, (java.util.Collection)null);

}

public java.util.Collection getEnclosedElements()
{
return java.util.Collections.unmodifiableCollection(null == this.elements ? new java.util.HashSet() : this.elements);

}

protected jct.test.rsc.jct.util.ListOfElements seeNextPathStep(final jct.test.rsc.jct.kernel.JCTKind aKind)
{
final jct.test.rsc.jct.util.ListOfElements result = new jct.test.rsc.jct.util.ListOfElements();
final java.util.Collection enclosedElements = this.getEnclosedElements();
for(jct.test.rsc.jct.kernel.IJCTElement e : enclosedElements) if(null != e && aKind == e.getKind()) result.add(e);
return result;

}

private void discardEnclosedElementsCachedPathPartBuilderIndex(final jct.test.rsc.jct.kernel.impl.JCTElement e)
{
final java.util.Iterator it = this.getEnclosedElements().iterator();
while(it.hasNext() && e != it.next()) 
{

}
while(it.hasNext()) 
{
final jct.test.rsc.jct.kernel.IJCTElement elem = it.next();
if(null != elem && elem instanceof jct.test.rsc.jct.kernel.impl.JCTElement) this.discardCachedPathPartBuilderIndex();

}

}

protected void backpatchElements(final java.util.Collection elements)
{
try
{
final java.lang.reflect.Field rootField = class.getDeclaredField("elements");
rootField.setAccessible(true);
rootField.set(this, elements);
rootField.setAccessible(false);

}
catch(java.lang.NoSuchFieldException ex) 
{
throw new java.lang.LinkageError(ex.toString());

}
catch(java.lang.IllegalAccessException ex) 
{
throw new java.lang.LinkageError(ex.toString());

}

}

protected java.util.List createInternalList()
{
return this.new this.InternalList();

}

protected java.util.List createInternalList(final java.util.Collection c)
{
return this.new this.InternalList(c);

}

protected class InternalList
extends jct.test.rsc.jct.util.collection.ListOfUnique
{
protected void <init>()
{
this.<init>();

}

protected void <init>(final java.util.Collection c)
{
this.<init>(c);

}

public void add(final int i, final jct.test.rsc.jct.kernel.IJCTElement e)
{
final int old_index = this.indexOf(e);
if(i == old_index) return;
if(-1 == old_index) this.updateEnclosingElement(this);
 else 
{
final int max = i > old_index ? i : old_index;
final java.util.ListIterator it = this.listIterator(i < old_index ? i : old_index);
while(it.hasNext() && it.nextIndex() <= max) 
{
final jct.test.rsc.jct.kernel.IJCTElement elem = it.next();
if(elem instanceof jct.test.rsc.jct.kernel.impl.JCTElement) this.discardCachedPathPartBuilderIndex();

}

}
super.add(i, e);
if(-1 == old_index) this.discardEnclosedElementsCachedPathPartBuilderIndex((jct.test.rsc.jct.kernel.impl.JCTElement)e);

}

public jct.test.rsc.jct.kernel.IJCTElement remove(final int i)
{
final jct.test.rsc.jct.kernel.impl.JCTElement e = (jct.test.rsc.jct.kernel.impl.JCTElement)this.get(i);
this.discardEnclosedElementsCachedPathPartBuilderIndex(e);
this.updateEnclosingElement(null);
return super.remove(i);

}

public jct.test.rsc.jct.kernel.IJCTElement set(final int i, final jct.test.rsc.jct.kernel.IJCTElement e)
{
final jct.test.rsc.jct.kernel.IJCTElement old = this.get(i);
if(e != old) 
{
this.updateEnclosingElement(null);
this.updateEnclosingElement(this);
return super.set(i, e);

}
return e;

}

public java.util.Spliterator spliterator()
{
return super.spliterator();

}


}

protected java.util.Set createInternalSet()
{
return this.new this.InternalSet();

}

protected java.util.Set createInternalSet(final java.util.Collection c)
{
return this.new this.InternalSet(c);

}

protected class InternalSet
extends java.util.AbstractSet
{
final private java.util.Set set;

private void preRemoveClean(final jct.test.rsc.jct.kernel.IJCTElement e)
{
this.discardEnclosedElementsCachedPathPartBuilderIndex((jct.test.rsc.jct.kernel.impl.JCTElement)e);
this.updateEnclosingElement(null);

}

protected void <init>()
{
this.<init>();
this.set = new java.util.HashSet();

}

protected void <init>(final java.util.Collection c)
{
this.<init>();
this.set = new java.util.HashSet(c);

}

public int size()
{
return this.set.size();

}

public java.util.Iterator iterator()
{
return this.new this.InternalIterator(this.set.iterator());

}

public boolean add(final jct.test.rsc.jct.kernel.IJCTElement e)
{
if(this.contains(e)) return false;
this.updateEnclosingElement(this);
return this.set.add(e);

}

public boolean remove(final jct.test.rsc.jct.kernel.IJCTElement e)
{
if(! this.contains(e)) return false;
this.preRemoveClean(e);
return this.set.remove(e);

}

public boolean contains(final jct.test.rsc.jct.kernel.IJCTElement e)
{
return this.set.contains(e);

}

protected class InternalIterator
implements java.util.Iterator
{
final private java.util.Iterator it;

private jct.test.rsc.jct.kernel.IJCTElement last;

protected jct.test.rsc.jct.kernel.IJCTElement getLast()
{
return this.last;

}

public void <init>(final java.util.Iterator it)
{
this.<init>();
this.it = it;

}

public boolean hasNext()
{
return this.it.hasNext();

}

public jct.test.rsc.jct.kernel.IJCTElement next()
{
return this.last = this.it.next();

}

public void remove()
{
this.preRemoveClean(this.last);
this.it.remove();

}


}


}

protected jct.test.rsc.jct.util.reference.NullableReference createNullableInternalReference()
{
return this.new this.NullableInternalReference();

}

protected jct.test.rsc.jct.util.reference.NullableReference createNullableInternalReference(final java.util.Collection c)
{
return this.new this.NullableInternalReference(c);

}

protected jct.test.rsc.jct.util.reference.NullableReference createNullableInternalReference(final jct.test.rsc.jct.kernel.IJCTElement element)
{
return this.new this.NullableInternalReference(element);

}

protected class NullableInternalReference
extends jct.test.rsc.jct.util.reference.StrongReference
implements jct.test.rsc.jct.util.reference.NullableReference
{
protected void <init>()
{
this.<init>();

}

protected void <init>(final java.util.Collection c)
{
this.<init>(c);

}

protected void <init>(final jct.test.rsc.jct.kernel.IJCTElement element)
{
this.<init>(element);

}

public jct.test.rsc.jct.kernel.IJCTElement set(final jct.test.rsc.jct.kernel.IJCTElement e)
{
if(this.get() == e) return e;
final jct.test.rsc.jct.kernel.IJCTElement old = super.set(e);
try
{
if(null != e) this.updateEnclosingElement(this);
if(null != old) this.updateEnclosingElement(null);

}
catch(java.lang.RuntimeException ex) 
{
super.set(old);
throw ex;

}
return old;

}


}

protected jct.test.rsc.jct.util.reference.NotNullableReference createInternalReference(final jct.test.rsc.jct.kernel.IJCTElement element)
{
return this.new this.InternalReference(element);

}

protected class InternalReference
extends jct.test.rsc.jct.util.reference.StrongReference
implements jct.test.rsc.jct.util.reference.NotNullableReference
{
protected void <init>(final jct.test.rsc.jct.kernel.IJCTElement element)
{
this.<init>(element);
this.updateEnclosingElement(this);

}

public jct.test.rsc.jct.kernel.IJCTElement set(final jct.test.rsc.jct.kernel.IJCTElement e)
{
if(this.get() == e) return e;
final jct.test.rsc.jct.kernel.IJCTElement old = super.set(e);
try
{
this.updateEnclosingElement(this);
this.updateEnclosingElement(null);

}
catch(java.lang.RuntimeException ex) 
{
super.set(old);
throw ex;

}
return super.set(e);

}


}


}
