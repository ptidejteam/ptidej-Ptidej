package jct.test.rsc.jct.util;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.JCTKind;
abstract public class AbstractJCTContainer
implements jct.test.rsc.jct.util.IJCTContainer
{
public void <init>()
{
this.<init>();

}

public java.util.Collection getEnclosedElements(final java.lang.Class c)
{
final java.util.Collection result = new java.util.LinkedList();
for(jct.test.rsc.jct.kernel.IJCTElement e : this.getEnclosedElements()) if(c.isInstance(e)) result.add(c.cast(e));
return java.util.Collections.unmodifiableCollection(result);

}

public java.util.Collection getAllEnclosedElements()
{
return this.getAllEnclosedElements(null, class, false);

}

public java.util.Collection getAllEnclosedElements(final jct.test.rsc.jct.kernel.JCTKind K, final java.lang.Class c, final boolean first_layer_only)
{
final java.util.Set result = new java.util.HashSet();
final java.util.Set checked = new java.util.HashSet();
final java.util.Set not_ok_yet = new java.util.HashSet();
if(this instanceof jct.test.rsc.jct.kernel.IJCTElement) not_ok_yet.add((jct.test.rsc.jct.kernel.IJCTElement)this);
 else for(jct.test.rsc.jct.kernel.IJCTElement e : this.getEnclosedElements()) if(null != e) not_ok_yet.add(e);
while(! not_ok_yet.isEmpty()) 
{
final java.util.Iterator it = not_ok_yet.iterator();
final jct.test.rsc.jct.kernel.IJCTElement T = it.next();
assert null != T"T should not be null, test below";
checked.add(T);
it.remove();
if(null == K || K == T.getKind()) 
{
result.add(c.cast(T));
if(first_layer_only) continue;

}
for(jct.test.rsc.jct.kernel.IJCTElement t : ((jct.test.rsc.jct.util.IJCTContainer)T).getEnclosedElements()) 
{
if(null == t) continue;
if(! checked.contains(t)) 
{
if(t instanceof jct.test.rsc.jct.util.IJCTContainer) not_ok_yet.add(t);
 else if(null == K || K == t.getKind()) 
{
result.add(c.cast(t));
checked.add(t);

}

}

}

}
return java.util.Collections.unmodifiableCollection(result);

}


}
