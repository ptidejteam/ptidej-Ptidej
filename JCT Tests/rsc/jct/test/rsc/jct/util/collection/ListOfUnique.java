package jct.test.rsc.jct.util.collection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import jct.test.rsc.jct.util.equiv.Equivalence;
import jct.test.rsc.jct.util.equiv.NaturalEquivalence;
public class ListOfUnique
extends java.util.AbstractList
implements java.util.List, java.util.Set
{
final private class TWithEquivalence
{
final private java.lang.Object object;

public void <init>(java.lang.Object object)
{
this.<init>();
this.object = object;

}

public boolean equals(java.lang.Object o)
{
return (o instanceof this.TWithEquivalence) && this.equalsp.areEquivalent(((this.TWithEquivalence)o).object);

}


}

final private java.util.List list;

final private java.util.Set elements = new java.util.HashSet();

final private jct.test.rsc.jct.util.equiv.Equivalence equalsp;

private void <init>(final java.util.List list, final jct.test.rsc.jct.util.equiv.Equivalence equalsp, final boolean removeDuplicatedElements)
{
this.<init>();
this.list = list;
this.equalsp = equalsp;
for(java.lang.Object e : list) if(! this.elements.add(new this.TWithEquivalence(e)) && ! removeDuplicatedElements) throw new java.lang.IllegalArgumentException("To decorate a List as a List of Unique, the list must not contains duplicated elements.");

}

private void <init>(final java.util.List list, final boolean removeDuplicatedElements)
{
this.<init>(list, new jct.test.rsc.jct.util.equiv.NaturalEquivalence(), removeDuplicatedElements);

}

public static jct.test.rsc.jct.util.collection.ListOfUnique decorateList(final java.util.List list, final jct.test.rsc.jct.util.equiv.Equivalence equalsp, final boolean removeDuplicatedElements)
{
return new jct.test.rsc.jct.util.collection.ListOfUnique(list, equalsp, removeDuplicatedElements);

}

public static jct.test.rsc.jct.util.collection.ListOfUnique decorateList(final java.util.List list, final boolean removeDuplicatedElements)
{
return new jct.test.rsc.jct.util.collection.ListOfUnique(list, removeDuplicatedElements);

}

public void <init>()
{
this.<init>(new java.util.ArrayList(), false);

}

public void <init>(jct.test.rsc.jct.util.equiv.Equivalence equalsp)
{
this.<init>(new java.util.ArrayList(), equalsp, false);

}

public void <init>(final java.util.Collection c)
{
this.<init>();
for(java.lang.Object e : c) this.add(e);

}

public int size()
{
return this.list.size();

}

public java.lang.Object get(final int i)
{
return this.list.get(i);

}

public java.lang.Object set(final int i, final java.lang.Object e)
{
final java.lang.Object old = this.get(i);
this.elements.remove(old);
if(this.contains(e)) 
{
final int old_index = this.indexOf(e);
this.list.set(i, e);
this.list.remove(old_index);

}
 else 
{
this.list.set(i, e);
this.elements.add(new this.TWithEquivalence(e));

}
return old;

}

public void add(final int i, final java.lang.Object e)
{
if(this.contains(e)) 
{
final int old_index = this.indexOf(e);
this.list.add(i, e);
this.list.remove(old_index);

}
 else 
{
this.elements.add(new this.TWithEquivalence(e));
this.list.add(e);

}
++ super.modCount;

}

public java.lang.Object remove(final int i)
{
final java.lang.Object e = this.list.remove(i);
this.elements.remove(new this.TWithEquivalence(e));
++ super.modCount;
return e;

}

public boolean contains(final java.lang.Object e)
{
return this.elements.contains(new this.TWithEquivalence((java.lang.Object)e));

}

public int indexOf(final java.lang.Object e)
{
if(! this.contains(e)) return -1;
final java.util.ListIterator it = this.listIterator();
while(it.hasNext()) if(this.equalsp.areEquivalent(it.next(), (java.lang.Object)e)) return it.previousIndex();
return -1;

}

public int lastIndexOf(final java.lang.Object e)
{
if(! this.contains(e)) return -1;
final java.util.ListIterator it = this.listIterator(this.size());
while(it.hasPrevious()) if(this.equalsp.areEquivalent(it.previous(), (java.lang.Object)e)) return it.nextIndex();
return -1;

}

public boolean equals(final java.lang.Object o)
{
if(this == o) return true;
if(! (o instanceof java.util.List)) return false;
final java.util.List that = (java.util.List)o;
if(this.size() != that.size()) return false;
final java.util.Iterator it = that.iterator();
for(java.lang.Object e : this) if(! this.equalsp.areEquivalent(e, it.next())) return false;
return true;

}

public boolean remove(java.lang.Object o)
{
if(! this.contains(o)) return false;
final java.util.Iterator it = this.iterator();
while(it.hasNext()) if(this.equalsp.areEquivalent((java.lang.Object)o, it.next())) 
{
it.remove();
return true;

}
return false;

}

public int hashCode()
{
return this.list.hashCode();

}

public java.lang.String toString()
{
return this.list.toString();

}


}
