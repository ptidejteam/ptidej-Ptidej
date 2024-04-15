package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.collection.IndirectCollection;
class JCTClass
extends jct.test.rsc.jct.kernel.impl.JCTClassMember
implements jct.test.rsc.jct.kernel.IJCTClass
{
private jct.test.rsc.jct.kernel.IJCTClassType directSuperClass;

private boolean isInterface;

final private java.util.List declaredMembers = this.createInternalList();

final private jct.test.rsc.jct.kernel.IJCTField[] specialMembers;

private boolean isGhost;

final private java.util.Set directlyImplementedInterfaces = new java.util.HashSet();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final boolean isInterface, final boolean isGhost)
{
this.<init>(aRootNode, name);
this.setIsInterface(isInterface);
this.setIsGhost(isGhost);
this.specialMembers = this.createSpecialMembers();
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(java.util.Collections.unmodifiableCollection(java.util.Arrays.asList(this.specialMembers)), this.declaredMembers));

}

public jct.test.rsc.jct.kernel.IJCTClassType createClassType()
{
final jct.test.rsc.jct.kernel.IJCTFactory f = this.getRootNode().getFactory();
return f.createClassType(f.createSimpleSelector(((jct.test.rsc.jct.kernel.IJCTClass)this)));

}

public jct.test.rsc.jct.kernel.IJCTField getThisField()
{
return this.specialMembers[0];

}

public jct.test.rsc.jct.kernel.IJCTField getSuperField()
{
return this.specialMembers[1];

}

public jct.test.rsc.jct.kernel.IJCTField getClassField()
{
return this.specialMembers[2];

}

public java.lang.String getID()
{
return (null == this.isStatic() ? this.getFQN() : this.getName()) + super.getID();

}

public void addDeclaredMember(final int anIndex, final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{
this.declaredMembers.add(anIndex, declaredMember);

}

public void addDeclaredMember(final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{
this.declaredMembers.add(declaredMember);

}

public void removeDeclaredMember(final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{
this.declaredMembers.remove(declaredMember);

}

public void removeDeclaredMember(final int anIndex)
{
this.declaredMembers.remove(anIndex);

}

public java.util.List getDeclaredMembers()
{
return java.util.Collections.unmodifiableList(this.declaredMembers);

}

public void setIsGhost(final boolean isGhost)
{
this.isGhost = isGhost;

}

public boolean getIsGhost()
{
return this.isGhost;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CLASS;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitClass(this, aP);

}

protected jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
final jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder p = this.createPathPart();
if(null == p.getInformativeData()) 
{
byte informativeData = 0;
if(this.getIsGhost()) informativeData |= 1;
if(this.getIsInterface()) informativeData |= 2;
p.setInformativeData(new byte[] { informativeData });

}
return p;

}

void updateEnclosingElement(final jct.test.rsc.jct.kernel.impl.JCTElementContainer e)
{
jct.test.rsc.jct.kernel.IJCTElement t = e;
while(null != t) 
{
if(this == t) throw new java.lang.IllegalArgumentException("Auto-Enclosing class" + this + " : " + e);
t = t.getEnclosingElement();

}
this.updateEnclosingElement(e);

}

private jct.test.rsc.jct.kernel.IJCTField[] createSpecialMembers()
{
final jct.test.rsc.jct.kernel.IJCTRootNode r = this.getRootNode();
final jct.test.rsc.jct.kernel.IJCTFactory f = r.getFactory();
final jct.test.rsc.jct.kernel.IJCTField vThis = f.createField(jct.test.rsc.jct.kernel.Constants.THIS_NAME, this.createClassType());
vThis.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE);
vThis.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.FINAL);
this.updateEnclosingElement(this);
final jct.test.rsc.jct.kernel.IJCTField vSuper = f.createField(jct.test.rsc.jct.kernel.Constants.SUPER_NAME, this.getDirectSuperClass() != null ? this.getDirectSuperClass() : this.createClassType());
vSuper.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE);
vSuper.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.FINAL);
this.updateEnclosingElement(this);
final jct.test.rsc.jct.kernel.IJCTClassType tClass = r.getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_CLASS, class);
final jct.test.rsc.jct.kernel.IJCTField vClass = f.createField(jct.test.rsc.jct.kernel.Constants.CLASS_NAME, tClass != null ? tClass : this.createClassType());
vClass.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC);
vClass.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.FINAL);
vClass.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.STATIC);
this.updateEnclosingElement(this);
return new jct.test.rsc.jct.kernel.IJCTField[] { vThis, vSuper, vClass };

}

public boolean getIsInterface()
{
return this.isInterface;

}

public void setIsInterface(final boolean i)
{
this.isInterface = i;
if(! this.getIsInterface() && null == this.getDirectSuperClass()) 
{
final jct.test.rsc.jct.kernel.IJCTClassType c = this.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_OBJECT, class);
if(c != null) this.setDirectSuperClass(c);

}

}

public java.lang.String getFQN()
{
if(null == this.getEnclosingElement()) return this.getName();
if(jct.test.rsc.jct.kernel.JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind()) 
{
final jct.test.rsc.jct.kernel.IJCTPackage p = (jct.test.rsc.jct.kernel.IJCTPackage)this.getEnclosingElement().getEnclosingElement();
return null == p || p.isUnnamed() ? this.getName() : p.getName() + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + this.getName();

}
 else return this.getDirectEnclosingClass().getFQN() + jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR + this.getName();

}

protected java.util.List seePreviousPathStep()
{
final jct.test.rsc.jct.kernel.impl.JCTClass enclosingClass = (jct.test.rsc.jct.kernel.impl.JCTClass)this.getDirectEnclosingClass();
final java.util.List list = this.seePreviousPathStep();
if(null != enclosingClass) list.add(enclosingClass);
 else if(null != this.getEnclosingElement() && jct.test.rsc.jct.kernel.JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind()) if(null != this.getEnclosingElement().getEnclosingElement()) list.add((jct.test.rsc.jct.kernel.impl.JCTElementContainer)this.getEnclosingElement().getEnclosingElement());
 else list.add((jct.test.rsc.jct.kernel.impl.JCTElementContainer)this.getEnclosingElement());
return list;

}

public jct.test.rsc.jct.kernel.IJCTClassType getDirectSuperClass()
{
return this.getIsInterface() ? null : this.directSuperClass;

}

public void setDirectSuperClass(final jct.test.rsc.jct.kernel.IJCTClassType aJCTClassType)
{
jct.test.rsc.jct.kernel.IJCTClassType c = aJCTClassType;
if(! this.getIsInterface() && null == c) c = this.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_OBJECT, class);
if(c.getSelector().getElement().getIsInterface()) throw new java.lang.IllegalArgumentException("An interface can not be extended");
this.directSuperClass = c;
if(null != this.specialMembers) this.getSuperField().setType(c);

}

public java.util.Set getDirectlyImplementedInterfaces()
{
return java.util.Collections.unmodifiableSet(this.directlyImplementedInterfaces);

}

public void addDirectlyImplementedInterface(final jct.test.rsc.jct.kernel.IJCTClassType c)
{
if(! c.getSelector().getElement().getIsInterface()) throw new java.lang.IllegalArgumentException("A class cannot be put in the implements list");
this.directlyImplementedInterfaces.add(c);

}

public void removeDirectlyImplementedInterface(final jct.test.rsc.jct.kernel.IJCTClassType c)
{
this.directlyImplementedInterfaces.remove(c);

}

public java.util.Collection getNestedClasses(final java.lang.Boolean staticOnly)
{
final java.util.Collection result = new java.util.LinkedList();
for(jct.test.rsc.jct.kernel.IJCTClassMember cm : this.getEnclosedElements()) if(cm instanceof jct.test.rsc.jct.kernel.IJCTClass && (null == staticOnly || staticOnly.equals(cm.isStatic()))) result.add((jct.test.rsc.jct.kernel.IJCTClass)cm);
return java.util.Collections.unmodifiableCollection(result);

}

public java.util.Collection getFields(final java.lang.Boolean staticOnly, final boolean includeSpecials)
{
final java.util.Collection result = new java.util.LinkedList();
for(jct.test.rsc.jct.kernel.IJCTClassMember cm : includeSpecials ? this.getEnclosedElements() : this.getDeclaredMembers()) if(cm instanceof jct.test.rsc.jct.kernel.IJCTField && (null == staticOnly || staticOnly.equals(cm.isStatic()))) result.add((jct.test.rsc.jct.kernel.IJCTField)cm);
return java.util.Collections.unmodifiableCollection(result);

}

public java.util.Collection getMethods(final java.lang.Boolean staticOnly)
{
final java.util.Collection result = new java.util.LinkedList();
for(jct.test.rsc.jct.kernel.IJCTClassMember cm : this.getEnclosedElements()) if(cm instanceof jct.test.rsc.jct.kernel.IJCTMethod && (null == staticOnly || staticOnly.equals(cm.isStatic()))) result.add((jct.test.rsc.jct.kernel.IJCTMethod)cm);
return java.util.Collections.unmodifiableCollection(result);

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.JCTModifiers m : this.getModifiers()) w.append(m.toString().toLowerCase()).append(' ');
w.append(this.getIsInterface() ? "interface" : "class").append(' ').append(this.getName());
if(null != this.getDirectSuperClass() && ! "Ljava.lang.Object".equals(this.getDirectSuperClass().getTypeName())) 
{
w.append("
extends ");
this.getDirectSuperClass().getSourceCode(w);

}
if(this.getDirectlyImplementedInterfaces().size() > 0) 
{
w.append('
').append(this.getIsInterface() ? "extends" : "implements").append(' ');
final java.util.SortedSet implemented = new java.util.TreeSet(new java.util.Comparator()
{
void <init>()
{
this.<init>();

}

public int compare(final jct.test.rsc.jct.kernel.IJCTClassType o1, final jct.test.rsc.jct.kernel.IJCTClassType o2)
{
return o1.getSelector().getElement().getFQN().compareTo(o2.getSelector().getElement().getFQN());

}

});
implemented.addAll(this.getDirectlyImplementedInterfaces());
final java.util.Iterator it = implemented.iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(w);
if(it.hasNext()) w.append(", ");

}

}
w.append("
{
");
for(jct.test.rsc.jct.kernel.IJCTClassMember cm : this.getDeclaredMembers()) cm.getSourceCode(w).append('
');
return w.append("
}
");

}

final private static java.util.Map modifiersIncompatibility = new java.util.HashMap();

final private static void <clinit>()
{
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT, jct.test.rsc.jct.kernel.JCTModifiers.FINAL.getFlag());
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.FINAL, jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE, jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag());
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag());
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag());
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.STATIC, 0);
jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.STRICTFP, 0);

}

protected boolean hasIncompatibleModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{
final java.lang.Integer incompatibility = jct.test.rsc.jct.kernel.impl.JCTClass.modifiersIncompatibility.get(m);
if(null == incompatibility) throw new java.lang.IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by classes.");
return (this.getModifierFlags() & incompatibility) != 0;

}


}
