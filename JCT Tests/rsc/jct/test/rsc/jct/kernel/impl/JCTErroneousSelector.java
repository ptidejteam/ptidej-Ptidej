package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTErroneousSelector;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTIdentifiable;
import jct.test.rsc.jct.kernel.IJCTMemberSelector;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTSimpleSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTErroneousSelector
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTErroneousSelector
{
final private jct.test.rsc.jct.util.reference.NotNullableReference identifier;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String anIdentifier)
{
this.<init>(aRootNode);
this.identifier = this.createInternalReference(new jct.test.rsc.jct.kernel.impl.JCTUnresolvedSimpleSelector(aRootNode, anIdentifier));
this.backpatchElements(this.identifier);

}

public void resolveSelector(final jct.test.rsc.jct.kernel.IJCTSelector s)
{
if(jct.test.rsc.jct.kernel.JCTKind.ERRONEOUS_SELECTOR != this.getIdentifier().getKind()) throw new java.lang.IllegalStateException("Selector already resolved");
this.identifier.set(s);

}

public jct.test.rsc.jct.kernel.IJCTSelector getIdentifier()
{
return this.identifier.get();

}

public jct.test.rsc.jct.kernel.IJCTClassMember getElement()
{
final jct.test.rsc.jct.kernel.IJCTIdentifiable element = this.getIdentifier().getElement();
if(null != element && ! (element instanceof jct.test.rsc.jct.kernel.IJCTClassMember)) throw new java.lang.IllegalStateException("Erroneous Simple Selector, please use this.getSelector().getMember() instead.");
return (jct.test.rsc.jct.kernel.IJCTClassMember)element;

}

public void setElement(final jct.test.rsc.jct.kernel.IJCTIdentifiable e)
{
this.getIdentifier().setElement(e);

}

public jct.test.rsc.jct.kernel.IJCTExpression getQualifyingExpression()
{
if(jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind()) return ((jct.test.rsc.jct.kernel.IJCTMemberSelector)this.getIdentifier()).getQualifyingExpression();
 else throw new java.lang.UnsupportedOperationException("getQualifyingExpression is implemented only by MEMBER_SELECTOR");

}

public void setQualifyingExpression(final jct.test.rsc.jct.kernel.IJCTExpression e)
{
if(jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind()) ((jct.test.rsc.jct.kernel.IJCTMemberSelector)this.getIdentifier()).setQualifyingExpression(e);
 else throw new java.lang.UnsupportedOperationException("setQualifyingExpression is implemented only by MEMBER_SELECTOR");

}

public jct.test.rsc.jct.kernel.IJCTSimpleSelector getMemberSelector()
{
if(jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind()) return ((jct.test.rsc.jct.kernel.IJCTMemberSelector)this.getIdentifier()).getMemberSelector();
 else throw new java.lang.UnsupportedOperationException("getMemberSelector is implemented only by MEMBER_SELECTOR");

}

public void setMemberSelector(final jct.test.rsc.jct.kernel.IJCTSimpleSelector e)
{
if(jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind()) ((jct.test.rsc.jct.kernel.IJCTMemberSelector)this.getIdentifier()).setMemberSelector(e);
 else throw new java.lang.UnsupportedOperationException("setMemberSelector is implemented only by MEMBER_SELECTOR");

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
return this.getIdentifier().getSourceCode(w);

}

public boolean equals(final java.lang.Object o)
{
return this.getIdentifier().equals(o);

}

public int hashCode()
{
return this.getIdentifier().hashCode();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return this.getIdentifier().getKind();

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return this.getIdentifier().getTypeResult();

}

public java.lang.String toString()
{
return this.getIdentifier().toString();

}

public java.util.Collection getEnclosedElements()
{
if(jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind()) return this.getEnclosedElements();
 else throw new java.lang.UnsupportedOperationException("getEnclosedElements is implemented only by MEMBER_SELECTOR");

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor v, final java.lang.Object p)
{
if(this.getIdentifier() instanceof jct.test.rsc.jct.kernel.impl.JCTUnresolvedSimpleSelector) return v.visitErroneousSelector(this, p);
 else return this.getIdentifier().accept(v, p);

}

public void setElement(final jct.test.rsc.jct.kernel.IJCTClassMember e)
{
this.setElement((jct.test.rsc.jct.kernel.IJCTIdentifiable)e);

}


}
