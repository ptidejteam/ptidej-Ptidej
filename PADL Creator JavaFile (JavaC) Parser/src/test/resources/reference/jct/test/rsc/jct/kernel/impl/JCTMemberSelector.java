package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTMemberSelector;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTSimpleSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTMemberSelector
extends jct.test.rsc.jct.kernel.impl.JCTSelector
implements jct.test.rsc.jct.kernel.IJCTMemberSelector
{
final private jct.test.rsc.jct.util.reference.NotNullableReference qualifyingExpression;

final private jct.test.rsc.jct.util.reference.NotNullableReference memberSelector;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression qualifyingExpression, final jct.test.rsc.jct.kernel.IJCTClassMember member)
{
this.<init>(aRootNode);
this.qualifyingExpression = this.createInternalReference(qualifyingExpression);
this.memberSelector = this.createInternalReference(this.getRootNode().getFactory().createSimpleSelector(member));
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.qualifyingExpression, this.memberSelector));

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitMemberSelector(this, aP);

}

private void check()
{
if(jct.test.rsc.jct.kernel.JCTKind.CLASS != this.getElement().getEnclosingElement().getKind()) throw new java.lang.IllegalStateException("the element must be a class member.");
jct.test.rsc.jct.kernel.IJCTClassType c;
if((jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getQualifyingExpression().getKind() || jct.test.rsc.jct.kernel.JCTKind.SIMPLE_SELECTOR == this.getQualifyingExpression().getKind()) && jct.test.rsc.jct.kernel.JCTKind.CLASS == ((jct.test.rsc.jct.kernel.IJCTSelector)this.getQualifyingExpression()).getElement().getKind()) c = ((jct.test.rsc.jct.kernel.IJCTSelector)this.getQualifyingExpression()).getElement().createClassType();
 else 
{
final jct.test.rsc.jct.kernel.IJCTType t = this.getQualifyingExpression().getTypeResult();
if(jct.test.rsc.jct.kernel.JCTKind.CLASS_TYPE == t.getKind()) c = (jct.test.rsc.jct.kernel.IJCTClassType)t;
 else c = null;

}
if(! this.getElement().isMemberOf(c)) throw new java.lang.IllegalStateException("the element must be a class member of the type specified by the qualifying expression.");

}

public jct.test.rsc.jct.kernel.IJCTClassMember getElement()
{
return this.memberSelector.get().getElement();

}

public void setElement(final jct.test.rsc.jct.kernel.IJCTClassMember e)
{
final jct.test.rsc.jct.kernel.IJCTClassMember old = this.getElement();
this.memberSelector.get().setElement(e);
try
{
this.check();

}
catch(java.lang.IllegalStateException ex) 
{
this.memberSelector.get().setElement(old);
throw new java.lang.IllegalArgumentException(ex);

}

}

public jct.test.rsc.jct.kernel.IJCTExpression getQualifyingExpression()
{
return this.qualifyingExpression.get();

}

public void setQualifyingExpression(final jct.test.rsc.jct.kernel.IJCTExpression e)
{
final jct.test.rsc.jct.kernel.IJCTExpression old = this.getQualifyingExpression();
this.qualifyingExpression.set(e);
try
{
this.check();

}
catch(java.lang.IllegalStateException ex) 
{
this.qualifyingExpression.set(old);
throw new java.lang.IllegalArgumentException(ex);

}

}

public jct.test.rsc.jct.kernel.IJCTSimpleSelector getMemberSelector()
{
return this.memberSelector.get();

}

public void setMemberSelector(final jct.test.rsc.jct.kernel.IJCTSimpleSelector e)
{
final jct.test.rsc.jct.kernel.IJCTSimpleSelector old = this.getMemberSelector();
this.memberSelector.set(e);
try
{
this.check();

}
catch(java.lang.IllegalStateException ex) 
{
this.memberSelector.set(old);
throw new java.lang.IllegalArgumentException(ex);

}

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
return this.getQualifyingExpression().getSourceCode(w).append('.').append(this.getElement().getName());

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getMemberSelector().getTypeResult();

}

public java.lang.String toString()
{
return this.getSourceCode();

}


}
