package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTParameter;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTVariableImpl
extends jct.test.rsc.jct.kernel.impl.JCTClassMember
implements jct.test.rsc.jct.kernel.IJCTField, jct.test.rsc.jct.kernel.IJCTParameter, jct.test.rsc.jct.kernel.IJCTVariable
{
final private jct.test.rsc.jct.kernel.JCTKind defaultKind;

private jct.test.rsc.jct.kernel.IJCTType type;

final private jct.test.rsc.jct.util.reference.NullableReference initialValue = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type, final jct.test.rsc.jct.kernel.JCTKind defaultKind)
{
this.<init>(aRootNode, name);
this.defaultKind = defaultKind;
this.type = type;
this.backpatchElements(this.initialValue);

}

public void setType(final jct.test.rsc.jct.kernel.IJCTType type)
{
this.type = type;

}

public jct.test.rsc.jct.kernel.IJCTType getType()
{
return this.type;

}

public void setInitialValue(final jct.test.rsc.jct.kernel.IJCTExpression initialValue)
{
this.initialValue.set(initialValue);

}

public jct.test.rsc.jct.kernel.IJCTExpression getInitialValue()
{
return this.initialValue.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
if(null == this.getEnclosingElement()) return this.defaultKind;
final jct.test.rsc.jct.kernel.JCTKind enclosingKind = this.getEnclosingElement().getKind();
if(jct.test.rsc.jct.kernel.JCTKind.CLASS == enclosingKind) return jct.test.rsc.jct.kernel.JCTKind.FIELD;
if(jct.test.rsc.jct.kernel.JCTKind.METHOD == enclosingKind) return jct.test.rsc.jct.kernel.JCTKind.PARAMETER;
return jct.test.rsc.jct.kernel.JCTKind.VARIABLE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
final jct.test.rsc.jct.kernel.JCTKind kind = this.getKind();
if(jct.test.rsc.jct.kernel.JCTKind.FIELD == kind) return visitor.visitField(this, aP);
if(jct.test.rsc.jct.kernel.JCTKind.PARAMETER == kind) return visitor.visitParameter(this, aP);
return visitor.visitVariable(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.JCTModifiers m : this.getModifiers()) w.append(m.toString().toLowerCase()).append(' ');
if(null != this.getType()) this.getType().getSourceCode(w).append(' ');
w.append(this.getName());
if(null != this.getInitialValue()) this.getInitialValue().getSourceCode(w.append(" = "));
return w.append(";
");

}

final private static java.util.Map modifiersIncompatibility = new java.util.HashMap();

final private static void <clinit>()
{
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.FINAL, jct.test.rsc.jct.kernel.JCTModifiers.VOLATILE.getFlag());
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE, jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag());
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC.getFlag());
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC, jct.test.rsc.jct.kernel.JCTModifiers.PRIVATE.getFlag() | jct.test.rsc.jct.kernel.JCTModifiers.PROTECTED.getFlag());
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.STATIC, 0);
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.TRANSIENT, 0);
jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.put(jct.test.rsc.jct.kernel.JCTModifiers.VOLATILE, jct.test.rsc.jct.kernel.JCTModifiers.FINAL.getFlag());

}

protected boolean hasIncompatibleModifier(final jct.test.rsc.jct.kernel.JCTModifiers m)
{
final java.lang.Integer incompatibility = jct.test.rsc.jct.kernel.impl.JCTVariableImpl.modifiersIncompatibility.get(m);
if(null == incompatibility) throw new java.lang.IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by variables and fields.");
return (this.getModifierFlags() & incompatibility) != 0;

}

public java.lang.String getID()
{
return this.getName() + super.getID();

}


}
