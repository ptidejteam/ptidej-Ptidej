package jct.test.rsc.jct.util.reference;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
public class StrongReference
extends java.util.AbstractList
implements java.util.List, java.util.RandomAccess
{
private java.lang.Object m_element;

public void <init>()
{
this.<init>();
this.m_element = null;

}

public void <init>(final java.util.Collection c)
{
this.<init>();
this.m_element = 0 == c.size() ? null : c.iterator().next();

}

public void <init>(final java.lang.Object element)
{
this.<init>();
this.m_element = element;

}

public int size()
{
return 1;

}

public java.lang.Object get()
{
return this.m_element;

}

public java.lang.Object get(final int i)
{
if(0 != i) throw new java.lang.IndexOutOfBoundsException("This list contains only one element");
return this.get();

}

public java.lang.Object set(final java.lang.Object e)
{
final java.lang.Object old = this.m_element;
this.m_element = e;
return old;

}

public java.lang.Object set(final int i, final java.lang.Object e)
{
if(0 != i) throw new java.lang.IndexOutOfBoundsException("This list contains only one element");
return this.set(e);

}

public boolean contains(final java.lang.Object e)
{
return this.m_element.equals(e);

}


}
