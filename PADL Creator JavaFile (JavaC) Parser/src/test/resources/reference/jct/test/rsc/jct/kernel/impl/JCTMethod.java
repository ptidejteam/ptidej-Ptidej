package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTParameter;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTMethod
extends jct.test.rsc.jct.kernel.impl.JCTClassMember
implements jct.test.rsc.jct.kernel.IJCTMethod
{
private jct.test.rsc.jct.kernel.IJCTType returnType;

final private java.util.List parameters = this.createInternalList();

final private jct.test.rsc.jct.util.reference.NotNullableReference body;

final private java.util.Set thrownExceptions = new java.util.HashSet();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name)
{
this.<init>(aRootNode, name);
this.returnType = this.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID);
this.body = this.createInternalReference(this.getRootNode().getFactory().createBlock());
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.parameters, this.body));

}

public void setReturnType(final jct.test.rsc.jct.kernel.IJCTType returnType)
{
this.returnType = returnType;

}

public jct.test.rsc.jct.kernel.IJCTType getReturnType()
{
return this.returnType;

}

public void addParameter(final int anIndex, final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{
this.parameters.add(anIndex, aParameter);

}

public void addParameter(final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{
this.parameters.add(aParameter);

}

public void removeParameter(final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{
this.parameters.remove(aParameter);

}

public void removeParameter(final int anIndex)
{
this.parameters.remove(anIndex);

}

public java.util.List getParameters()
{
return java.util.Collections.unmodifiableList(this.parameters);

}

public void setBody(final jct.test.rsc.jct.kernel.IJCTBlock body)
{
this.body.set(body);

}

public jct.test.rsc.jct.kernel.IJCTBlock getBody()
{
return this.body.get();

}

public void addThrownException(final jct.test.rsc.jct.kernel.IJCTClassType thrownException)
{
this.thrownExceptions.add(thrownException);

}

public void removeThrownException(final jct.test.rsc.jct.kernel.IJCTClassType thrownException)
{
this.thrownExceptions.remove(thrownException);

}

public java.util.Set getThrownExceptions()
{
return java.util.Collections.unmodifiableSet(this.thrownExceptions);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.METHOD;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitMethod(this, aP);

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
final java.lang.StringBuilder str = new java.lang.StringBuilder(this.getName());
str.append(jct.test.rsc.jct.kernel.Constants.METHOD_MARKER);
for(jct.test.rsc.jct.kernel.IJCTParameter p : this.getParameters()) str.append((null == p.getType() ? null : p.getType().getTypeName()) + jct.test.rsc.jct.kernel.Constants.PARAMETER_SEPARATOR);
str.setLength(str.length() - 1);
return this.createPathPart().setData(str.toString());

}

public boolean equals(final java.lang.Object o)
{
if(this == o) return true;
if(! (o instanceof jct.test.rsc.jct.kernel.IJCTMethod)) return false;
final jct.test.rsc.jct.kernel.IJCTMethod m = (jct.test.rsc.jct.kernel.IJCTMethod)o;
if(jct.test.rsc.jct.kernel.Constants.INSTANCE_INITIALIZER_NAME.equals(m.getName()) || jct.test.rsc.jct.kernel.Constants.CLASS_INITIALIZER_NAME.equals(m.getName())) return false;
if(! this.getName().equals(m.getName())) return false;
if(this.getParameters().size() != m.getParameters().size()) return false;
final java.util.Iterator tit = this.getParameters().iterator();
final java.util.Iterator mit = m.getParameters().iterator();
while(mit.hasNext()) 
{
final jct.test.rsc.jct.kernel.IJCTType targtype = tit.next().getType();
final jct.test.rsc.jct.kernel.IJCTType margtype = mit.next().getType();
if(null == margtype || null == targtype || ! margtype.equals(targtype)) return false;

}
return true;

}

public java.lang.String getID()
{
final java.lang.StringBuffer result = new java.lang.StringBuffer();
result.append(this.getName()).append('(');
final java.util.Iterator pit = this.getParameters().iterator();
while(pit.hasNext()) 
{
final java.lang.String var = pit.next().getSourceCode();
result.append(var.substring(0, var.length() - 2));
if(pit.hasNext()) result.append(", ");

}
return result.append(')').append(super.getID()).toString();

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.JCTModifiers m : this.getModifiers()) w.append(m.toString().toLowerCase()).append(' ');
this.getReturnType().getSourceCode(w).append(' ').append(this.getName()).append('(');
final java.util.Iterator pit = this.getParameters().iterator();
while(pit.hasNext()) 
{
final java.lang.String var = pit.next().getSourceCode();
w.append(var.substring(0, var.length() - 2));
if(pit.hasNext()) w.append(", ");

}
w.append(')');
if(this.getThrownExceptions().size() > 0) 
{
w.append(" throws ");
final java.util.SortedSet thrown = new java.util.TreeSet(new java.util.Comparator()
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
thrown.addAll(this.getThrownExceptions());
final java.util.Iterator cit = thrown.iterator();
while(cit.hasNext()) 
{
cit.next().getSourceCode(w);
if(cit.hasNext()) w.append(", ");

}

}
if(null != this.getBody()) this.getBody().getSourceCode(w);
 else w.append(";
");
return w;

}

final private static java.util.Map modifiersIncompatibility = new java.util.HashMap();

final private static void <clinit>()
{
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.FINAL.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.STATIC.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.NATIVE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.STRICTFP.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.SYNCHRONIZED.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.FINAL, jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.NATIVE, jct.test.rsc.jct.kernel.JCTModifiers.STRICTFP.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE, jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.STATIC, jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.STRICTFP, jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.NATIVE.getFlag());
jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.SYNCHRONIZED, jct.test.rsc.jct.kernel.JCTModifiers.ABSTRACT.getFlag());

}

protected boolean hasIncompatibleModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{
final java.lang.Integer incompatibility = jct.test.rsc.jct.kernel.impl.JCTMethod.modifiersIncompatibility.get(m);
if(null == incompatibility) throw new java.lang.IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by methods.");
return (this.getModifierFlags() & incompatibility) != 0;

}


}
