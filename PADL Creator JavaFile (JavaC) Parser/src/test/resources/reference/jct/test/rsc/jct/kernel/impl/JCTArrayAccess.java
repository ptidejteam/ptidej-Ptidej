package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTArrayAccess;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTArrayAccess
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTArrayAccess
{
final private jct.test.rsc.jct.util.reference.NotNullableReference array;

final private jct.test.rsc.jct.util.reference.NotNullableReference index;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression array, final jct.test.rsc.jct.kernel.IJCTExpression index)
{
this.<init>(aRootNode);
this.array = this.createInternalReference(array);
this.index = this.createInternalReference(index);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.array, this.index));

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
this.getArray().getSourceCode(aWriter).append('[');
return this.getIndex().getSourceCode(aWriter).append(']');

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return ((jct.test.rsc.jct.kernel.IJCTArrayType)this.getArray().getTypeResult()).getUnderlyingType();

}

public void setArray(final jct.test.rsc.jct.kernel.IJCTExpression array)
{
this.array.set(array);

}

public jct.test.rsc.jct.kernel.IJCTExpression getArray()
{
return this.array.get();

}

public void setIndex(final jct.test.rsc.jct.kernel.IJCTExpression index)
{
this.index.set(index);

}

public jct.test.rsc.jct.kernel.IJCTExpression getIndex()
{
return this.index.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ARRAY_ACCESS;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitArrayAccess(this, aP);

}


}
