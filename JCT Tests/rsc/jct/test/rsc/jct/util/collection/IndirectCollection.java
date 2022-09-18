package jct.test.rsc.jct.util.collection;
import java.lang.ref.SoftReference;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class IndirectCollection
extends java.util.AbstractCollection
{
final protected java.util.Collection[] m_collections;

public void <init>(final java.util.Collection[] collections)
{
this.<init>();
this.m_collections = (java.util.Collection[])collections;

}

public int size()
{
int size = 0;
for(java.util.Collection l : this.m_collections) size += l.size();
return size;

}

public java.util.Iterator iterator()
{
return this.new this.InternalIterator(this.m_collections[0].iterator());

}

protected class InternalIterator
implements java.util.Iterator
{
private int m_collection_index = 0;

private java.util.Iterator m_it;

private java.lang.ref.SoftReference m_next_it = new java.lang.ref.SoftReference(null);

private java.util.Iterator p_getNextValidIterator()
{
if(this.m_it.hasNext()) return this.m_it;
while(this.m_collection_index < this.m_collections.length - 1) 
{
final java.util.Iterator next_collection_iterator = this.m_collections[++ this.m_collection_index].iterator();
if(next_collection_iterator.hasNext()) return next_collection_iterator;

}
return null;

}

void <init>(final java.util.Iterator it)
{
this.<init>();
this.m_it = it;

}

public boolean hasNext()
{
final java.util.Iterator it = this.p_getNextValidIterator();
if(null == it) return false;
 else 
{
this.m_next_it = new java.lang.ref.SoftReference(it);
return true;

}

}

public java.lang.Object next()
{
java.util.Iterator it = this.m_next_it.get();
if(null == it) 
{
it = this.p_getNextValidIterator();
if(null == it) throw new java.util.NoSuchElementException();

}
 else this.m_next_it = new java.lang.ref.SoftReference(null);
return (this.m_it = it).next();

}

public void remove()
{
this.m_it.remove();

}


}


}
