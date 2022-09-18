package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTNewClass;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTNewClass
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTNewClass
{
private jct.test.rsc.jct.kernel.IJCTClassType classType;

final private jct.test.rsc.jct.util.reference.NullableReference annonymousClass = this.createNullableInternalReference();

final private jct.test.rsc.jct.util.reference.NullableReference selectingExpression = this.createNullableInternalReference();

final private java.util.List arguments = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTClassType classType)
{
this.<init>(aRootNode);
this.classType = classType;
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.selectingExpression, this.annonymousClass, this.arguments));

}

public void setSelectingExpression(final jct.test.rsc.jct.kernel.IJCTExpression selectingExpression)
{
this.selectingExpression.set(selectingExpression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getSelectingExpression()
{
return this.selectingExpression.get();

}

public void addArgument(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.add(anIndex, argument);

}

public void addArgument(final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.add(argument);

}

public void removeArgument(final jct.test.rsc.jct.kernel.IJCTExpression argument)
{
this.arguments.remove(argument);

}

public void removeArgument(final int anIndex)
{
this.arguments.remove(anIndex);

}

public java.util.List getArguments()
{
return java.util.Collections.unmodifiableList(this.arguments);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.NEW_CLASS;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitNewClass(this, aP);

}

public jct.test.rsc.jct.kernel.IJCTClassType getClassType()
{
return this.classType;

}

public void setClassType(final jct.test.rsc.jct.kernel.IJCTClassType classType)
{
if(null != this.getAnnonymousClass() && ! classType.isExtendingOrImplementing(this.getAnnonymousClass().createClassType())) throw new java.lang.IllegalArgumentException("The annonymous declared class must extend or implements the identified class");
if(null == this.getAnnonymousClass() && classType.getSelector().getElement().getIsInterface()) throw new java.lang.IllegalArgumentException("Instanciating an interface is not allewd.");
this.classType = classType;

}

public jct.test.rsc.jct.kernel.IJCTClass getAnnonymousClass()
{
return this.annonymousClass.get();

}

public void setAnnonymousClass(final jct.test.rsc.jct.kernel.IJCTClass annonymousClass)
{
if(null != annonymousClass) 
{
if(! this.getClassType().isExtendingOrImplementing(annonymousClass.createClassType())) throw new java.lang.IllegalArgumentException("The annonymous declared class must extend or implements the identified class");
if(annonymousClass.getIsInterface()) throw new java.lang.IllegalArgumentException("Annonymous interface does not exists");

}
 else if(this.getClassType().getSelector().getElement().getIsInterface()) throw new java.lang.IllegalArgumentException("Instanciating an interface is not allewd.");
this.annonymousClass.set(annonymousClass);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
final jct.test.rsc.jct.kernel.IJCTClass c = this.getAnnonymousClass();
return null != c ? c.createClassType() : this.getClassType();

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
if(null != this.getSelectingExpression()) this.getSelectingExpression().getSourceCode(aWriter).append('.');
aWriter.append("new ");
this.getClassType().getSourceCode(aWriter).append('(');
final java.util.Iterator it = this.getArguments().iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(aWriter);
if(it.hasNext()) aWriter.append(", ");

}
aWriter.append(')');
if(null != this.getAnnonymousClass()) 
{
aWriter.append("
{
");
for(jct.test.rsc.jct.kernel.IJCTClassMember cm : this.getAnnonymousClass().getDeclaredMembers()) cm.getSourceCode(aWriter).append('
');
aWriter.append('}');

}
return aWriter;

}


}
