package jct.test.rsc.jct.kernel.impl;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
abstract class JCTNonPrimitiveType
extends jct.test.rsc.jct.kernel.impl.JCTType
implements jct.test.rsc.jct.kernel.IJCTNonPrimitiveType
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public boolean isExtendingOrImplementing(final jct.test.rsc.jct.kernel.IJCTNonPrimitiveType aNonPrimitiveType)
{
return this == aNonPrimitiveType || aNonPrimitiveType.getAllSuperClasses().contains(this);

}

public java.util.Set getAllSuperClasses()
{
final java.util.Set not_ok_yet = new java.util.HashSet();
final java.util.Set result = new java.util.HashSet();
result.add(this);
not_ok_yet.addAll(this.getDirectSuperClasses());
while(! not_ok_yet.isEmpty()) 
{
final jct.test.rsc.jct.kernel.IJCTNonPrimitiveType superClass = not_ok_yet.iterator().next();
if(! result.contains(superClass)) 
{
result.add(superClass);
not_ok_yet.addAll(superClass.getAllSuperClasses());

}
not_ok_yet.remove(superClass);

}
return java.util.Collections.unmodifiableSet(result);

}


}
