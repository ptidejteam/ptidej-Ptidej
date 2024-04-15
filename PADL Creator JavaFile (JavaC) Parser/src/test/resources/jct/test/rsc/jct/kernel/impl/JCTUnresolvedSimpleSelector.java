package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTIdentifiable;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSimpleSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTUnresolvedSimpleSelector
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTSimpleSelector
{
final private java.lang.String identifier;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String identifier)
{
this.<init>(aRootNode);
this.identifier = identifier;

}

public jct.test.rsc.jct.kernel.IJCTIdentifiable getElement()
{
return null;

}

public void setElement(final jct.test.rsc.jct.kernel.IJCTIdentifiable anIdentifiable)
{
throw new java.lang.UnsupportedOperationException("to resolve an unresolved selector, you must use the method resolveSelector(IJCTSelector).");

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return aWriter.append(this.identifier);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ERRONEOUS_SELECTOR;

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return null;

}

public java.lang.String toString()
{
return this.getSourceCode();

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor v, final java.lang.Object p)
{
return this.getEnclosingElement().accept(v, p);

}

public java.lang.String getIdentifier()
{
return this.identifier;

}


}
