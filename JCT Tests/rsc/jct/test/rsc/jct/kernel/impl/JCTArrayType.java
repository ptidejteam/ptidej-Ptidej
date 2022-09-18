package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTArrayType
extends jct.test.rsc.jct.kernel.impl.JCTNonPrimitiveType
implements jct.test.rsc.jct.kernel.IJCTArrayType
{
private jct.test.rsc.jct.kernel.IJCTType underlyingType;

final private jct.test.rsc.jct.util.reference.NotNullableReference cloneMethod;

final private jct.test.rsc.jct.util.reference.NotNullableReference lengthField;

private java.lang.String underlyingTypeName = null;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTType underlyingType, final java.lang.String registeredUnderlyingTypeName)
{
this.<init>(aRootNode);
this.underlyingTypeName = registeredUnderlyingTypeName;
this.underlyingType = underlyingType;
this.lengthField = this.createInternalReference(jct.test.rsc.jct.kernel.impl.JCTArrayType.createLength(this));
this.cloneMethod = this.createInternalReference(jct.test.rsc.jct.kernel.impl.JCTArrayType.createClone(this));
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.lengthField, this.cloneMethod));

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTType underlyingType)
{
this.<init>(aRootNode, underlyingType, null);

}

public java.lang.String getTypeName()
{
return jct.test.rsc.jct.kernel.Constants.ARRAY_MARKER + this.getUnderlyingTypeName();

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return this.getUnderlyingType().getSourceCode(aWriter).append("[]");

}

public java.util.Set getDirectSuperClasses()
{
final java.util.Set result = new java.util.HashSet();
final jct.test.rsc.jct.kernel.IJCTClassType object = this.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASSPATH_OBJECT, class);
if(null != object.getSelector().getElement()) result.add(object);
return result;

}

public void setUnderlyingType(final jct.test.rsc.jct.kernel.IJCTType underlyingType, final java.lang.String registeredUnderlyingTypeName)
{
this.setUnderlyingType(underlyingType);
this.underlyingTypeName = registeredUnderlyingTypeName;

}

public java.lang.String getUnderlyingTypeName()
{
return null == this.underlyingTypeName ? this.underlyingType.getTypeName() : this.underlyingTypeName;

}

public void clearRegisteredTypeName()
{
this.underlyingTypeName = null;

}

public void setUnderlyingType(final jct.test.rsc.jct.kernel.IJCTType underlyingType)
{
this.underlyingType = underlyingType;

}

public jct.test.rsc.jct.kernel.IJCTType getUnderlyingType()
{
return this.underlyingType;

}

public jct.test.rsc.jct.kernel.IJCTMethod getCloneMethod()
{
return this.cloneMethod.get();

}

public jct.test.rsc.jct.kernel.IJCTField getLengthField()
{
return this.lengthField.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ARRAY_TYPE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitArrayType(this, aP);

}

private static jct.test.rsc.jct.kernel.IJCTField createLength(final jct.test.rsc.jct.kernel.impl.JCTArrayType anArrayType)
{
final jct.test.rsc.jct.kernel.IJCTFactory f = anArrayType.getRootNode().getFactory();
final jct.test.rsc.jct.kernel.IJCTField vLength = f.createField(jct.test.rsc.jct.kernel.Constants.LENGTH_NAME, anArrayType);
vLength.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC);
vLength.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.FINAL);
this.updateEnclosingElement(anArrayType);
return vLength;

}

private static jct.test.rsc.jct.kernel.IJCTMethod createClone(final jct.test.rsc.jct.kernel.impl.JCTArrayType anArrayType)
{
final jct.test.rsc.jct.kernel.IJCTFactory f = anArrayType.getRootNode().getFactory();
final jct.test.rsc.jct.kernel.IJCTMethod mClone = f.createMethod(jct.test.rsc.jct.kernel.Constants.CLONE_NAME);
mClone.setReturnType(anArrayType);
mClone.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.PUBLIC);
mClone.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.FINAL);
mClone.addModifier(jct.test.rsc.jct.kernel.JCTModifiers.NATIVE);
this.updateEnclosingElement(anArrayType);
return mClone;

}

public boolean equals(final java.lang.Object o)
{
if(! (o instanceof jct.test.rsc.jct.kernel.IJCTArrayType)) return false;
final jct.test.rsc.jct.kernel.IJCTArrayType at = (jct.test.rsc.jct.kernel.IJCTArrayType)o;
if(null != this.getUnderlyingType() && null != at.getUnderlyingType()) return this.getUnderlyingType().equals(at.getUnderlyingType());
 else 
{
final java.lang.String underlyingTypeName = this.getUnderlyingTypeName();
return null == underlyingTypeName ? null == at.getUnderlyingTypeName() : underlyingTypeName.equals(at.getUnderlyingTypeName());

}

}


}
