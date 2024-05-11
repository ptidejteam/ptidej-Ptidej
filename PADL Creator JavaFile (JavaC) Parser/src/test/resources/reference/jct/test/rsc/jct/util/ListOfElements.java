package jct.test.rsc.jct.util;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import jct.test.rsc.jct.kernel.IJCTElement;
public class ListOfElements
extends jct.test.rsc.jct.util.AbstractJCTContainer
implements java.util.List
{
final private java.util.List list;

public static jct.test.rsc.jct.util.ListOfElements decorateList(final java.util.List list)
{
return new jct.test.rsc.jct.util.ListOfElements(list);

}

protected void <init>(final java.util.List list)
{
this.<init>();
this.list = list;

}

public void <init>()
{
this.<init>(new java.util.LinkedList());

}

public void <init>(final java.util.Collection c)
{
this.<init>();
for(jct.test.rsc.jct.kernel.IJCTElement e : c) this.add(e);

}

public java.util.List getDecoratedList()
{
return this.list;

}

public java.util.Collection getEnclosedElements()
{
return this;

}

public boolean add(final jct.test.rsc.jct.kernel.IJCTElement e)
{
return this.list.add(e);

}

public void add(final int index, final jct.test.rsc.jct.kernel.IJCTElement element)
{
this.list.add(index, element);

}

public boolean addAll(final java.util.Collection c)
{
return this.list.addAll(c);

}

public boolean addAll(final int index, final java.util.Collection c)
{
return this.list.addAll(index, c);

}

public void clear()
{
this.list.clear();

}

public boolean contains(final java.lang.Object o)
{
return this.list.contains(o);

}

public boolean containsAll(final java.util.Collection c)
{
return this.list.containsAll(c);

}

public jct.test.rsc.jct.kernel.IJCTElement get(final int index)
{
return this.list.get(index);

}

public int indexOf(final java.lang.Object o)
{
return this.list.indexOf(o);

}

public boolean isEmpty()
{
return this.list.isEmpty();

}

public java.util.Iterator iterator()
{
return this.list.iterator();

}

public int lastIndexOf(final java.lang.Object o)
{
return this.list.lastIndexOf(o);

}

public java.util.ListIterator listIterator()
{
return this.list.listIterator();

}

public java.util.ListIterator listIterator(final int index)
{
return this.list.listIterator();

}

public boolean remove(final java.lang.Object o)
{
return this.list.remove(o);

}

public jct.test.rsc.jct.kernel.IJCTElement remove(final int index)
{
return this.list.remove(index);

}

public boolean removeAll(final java.util.Collection c)
{
return this.list.removeAll(c);

}

public boolean retainAll(final java.util.Collection c)
{
return this.list.retainAll(c);

}

public jct.test.rsc.jct.kernel.IJCTElement set(final int index, final jct.test.rsc.jct.kernel.IJCTElement element)
{
return this.list.set(index, element);

}

public int size()
{
return this.list.size();

}

public java.util.List subList(final int fromIndex, final int toIndex)
{
return this.list.subList(fromIndex, toIndex);

}

public java.lang.Object[] toArray()
{
return this.list.toArray();

}

public java.lang.Object[] toArray(final java.lang.Object[] a)
{
return this.list.toArray(a);

}

public java.lang.String toString()
{
final java.lang.StringBuffer sb = new java.lang.StringBuffer("[");
for(jct.test.rsc.jct.kernel.IJCTElement e : this) 
{
sb.append(e.getKind());
if(e instanceof jct.test.rsc.jct.kernel.IJCTIdentifiable) sb.append(" : ").append(((jct.test.rsc.jct.kernel.IJCTIdentifiable)e).getName());
sb.append(",
");

}
return sb.append("]").toString();

}


}
