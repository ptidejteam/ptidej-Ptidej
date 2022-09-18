package jct.test.rsc.jct.kernel.impl;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
abstract class JCTClassMember
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTClassMember
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final java.util.Collection elements)
{
this.<init>(aRootNode, name, elements);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name)
{
this.<init>(aRootNode, name, null);

}

private int modifiers = 0;

public java.util.Set getModifiers()
{
final java.util.Set result = new java.util.TreeSet();
for(jct.test.rsc.jct.kernel.JCTModifiers m : <NULL>.values()) if((this.modifiers & m.getFlag()) != 0) result.add(m);
return java.util.Collections.unmodifiableSet(result);

}

protected int getModifierFlags()
{
return this.modifiers;

}

abstract protected boolean hasIncompatibleModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{

}

public void addModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{
if(this.hasIncompatibleModifier(m)) throw new java.lang.IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") has an uncompatibility with one of the modifiers of this class member (0x" + java.lang.Integer.toString(this.getModifierFlags(), 16) + ")");
this.modifiers |= m.getFlag();

}

public void removeModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{
this.modifiers &= ~ m.getFlag();

}

public boolean isMemberOf(final jct.test.rsc.jct.kernel.IJCTNonPrimitiveType c)
{
if(null == this.isStatic()) return false;
return c.getAllSuperClasses().contains(this.getEnclosingElement().getKind() == jct.test.rsc.jct.kernel.JCTKind.ARRAY_TYPE ? (jct.test.rsc.jct.kernel.IJCTArrayType)this.getEnclosingElement() : ((jct.test.rsc.jct.kernel.IJCTClass)this.getEnclosingElement()).createClassType());

}

public java.lang.Boolean isStatic()
{
if(null == this.getEnclosingElement() || (jct.test.rsc.jct.kernel.JCTKind.ARRAY_TYPE != this.getEnclosingElement().getKind() && jct.test.rsc.jct.kernel.JCTKind.CLASS != this.getEnclosingElement().getKind())) return null;
return this.getModifiers().contains(jct.test.rsc.jct.kernel.JCTModifiers.STATIC);

}

public jct.test.rsc.jct.kernel.IJCTClass getDirectEnclosingClass()
{
jct.test.rsc.jct.kernel.IJCTElement e = this.getEnclosingElement();
while(null != e && jct.test.rsc.jct.kernel.JCTKind.CLASS != e.getKind() && jct.test.rsc.jct.kernel.JCTKind.ARRAY_TYPE != e.getKind()) e = e.getEnclosingElement();
return (jct.test.rsc.jct.kernel.IJCTClass)e;

}

public jct.test.rsc.jct.kernel.IJCTClass getTopLevelEnclosingClass()
{
jct.test.rsc.jct.kernel.IJCTClass c = this.getDirectEnclosingClass();
if(null == c) return null;
jct.test.rsc.jct.kernel.IJCTClass ce = c.getDirectEnclosingClass();
while(null != ce) 
{
c = ce;
ce = c.getDirectEnclosingClass();

}
return c;

}


}
