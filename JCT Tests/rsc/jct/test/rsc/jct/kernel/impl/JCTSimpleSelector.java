package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTIdentifiable;
import jct.test.rsc.jct.kernel.IJCTMemberSelector;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTSimpleSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTSimpleSelector
extends jct.test.rsc.jct.kernel.impl.JCTSelector
implements jct.test.rsc.jct.kernel.IJCTSimpleSelector
{
private jct.test.rsc.jct.kernel.IJCTIdentifiable element;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTIdentifiable anIdentifiable)
{
this.<init>(aRootNode);
this.element = anIdentifiable;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitSimpleSelector(this, aP);

}

public jct.test.rsc.jct.kernel.IJCTIdentifiable getElement()
{
return this.element;

}

public void setElement(final jct.test.rsc.jct.kernel.IJCTIdentifiable e)
{
if(e == null) throw new java.lang.NullPointerException("e must not be null");
this.element = e;

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
if(jct.test.rsc.jct.kernel.JCTKind.SIMPLE_IDENTIFIER == this.getKind()) w.append(this.getElement().getName());
 else 
{
final jct.test.rsc.jct.kernel.IJCTIdentifiable e = this.getElement();
switch((e.getKind()))
{
case jct.test.rsc.jct.kernel.JCTKind.PACKAGE:
w.append(e.getName());
break;
case jct.test.rsc.jct.kernel.JCTKind.PRIMITIVE_TYPE:
w.append(((jct.test.rsc.jct.kernel.IJCTPrimitiveType)e).getType().toString().toLowerCase());
break;
case jct.test.rsc.jct.kernel.JCTKind.CLASS:
if(null == ((jct.test.rsc.jct.kernel.IJCTClassMember)e).isStatic()) 
{
w.append(((jct.test.rsc.jct.kernel.IJCTClass)e).getFQN());
break;

}
case jct.test.rsc.jct.kernel.JCTKind.VARIABLE:
case jct.test.rsc.jct.kernel.JCTKind.FIELD:
case jct.test.rsc.jct.kernel.JCTKind.PARAMETER:
if(null == ((jct.test.rsc.jct.kernel.IJCTClassMember)e).isStatic()) 
{
w.append(e.getName());
break;

}
case jct.test.rsc.jct.kernel.JCTKind.METHOD:

{
final jct.test.rsc.jct.kernel.IJCTClassMember m = (jct.test.rsc.jct.kernel.IJCTClassMember)e;
final java.lang.Boolean isStatic = m.isStatic();
if(null != isStatic && isStatic) w.append(((jct.test.rsc.jct.kernel.IJCTClass)m.getEnclosingElement()).getFQN()).append('.').append(m.getName());
 else w.append(null == isStatic ? "<NULL>." : "this.").append(m.getName());

}
break;
default:
throw new java.lang.AssertionError("the element selected by a selector must be an identifiable element (i.e. either a package, a primitive type or a class member).");
}

}
return w;

}

public boolean equals(final java.lang.Object o)
{
if(o instanceof jct.test.rsc.jct.kernel.IJCTSelector) return this.getElement().equals(((jct.test.rsc.jct.kernel.IJCTSelector)o).getElement());
 else return false;

}

public int hashCode()
{
return this.getElement().hashCode();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
if(null != this.getEnclosingElement() && jct.test.rsc.jct.kernel.JCTKind.MEMBER_SELECTOR == this.getEnclosingElement().getKind() && this == ((jct.test.rsc.jct.kernel.IJCTMemberSelector)this.getEnclosingElement()).getMemberSelector()) if(! (this.getElement() instanceof jct.test.rsc.jct.kernel.IJCTClassMember) || null == ((jct.test.rsc.jct.kernel.IJCTClassMember)this.getElement()).getDirectEnclosingClass()) throw new java.lang.AssertionError("This element (" + this.getElement().getPath() + ") can not be selected as the right part of a member selector");
 else return jct.test.rsc.jct.kernel.JCTKind.SIMPLE_IDENTIFIER;
 else return jct.test.rsc.jct.kernel.JCTKind.SIMPLE_SELECTOR;

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
if(jct.test.rsc.jct.kernel.JCTKind.VARIABLE != this.getElement().getKind()) throw new java.lang.UnsupportedOperationException("Can not get the type of an identifiable other than a variable.");
return ((jct.test.rsc.jct.kernel.IJCTVariable)this.getElement()).getType();

}


}
