package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTNewArray;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
class JCTNewArray
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTNewArray
{
private jct.test.rsc.jct.kernel.IJCTType elementType;

final private java.util.List dimensions = this.createInternalList();

private int unspecifiedDimensions;

final private java.util.List initializers = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTType elementType)
{
this.<init>(aRootNode);
this.elementType = elementType;
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.dimensions, this.initializers));

}

public void setElementType(final jct.test.rsc.jct.kernel.IJCTType elementType)
{
this.elementType = elementType;

}

public jct.test.rsc.jct.kernel.IJCTType getElementType()
{
return this.elementType;

}

public void addDimension(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpression dimension)
{
this.dimensions.add(anIndex, dimension);

}

public void addDimension(final jct.test.rsc.jct.kernel.IJCTExpression dimension)
{
this.dimensions.add(dimension);

}

public void removeDimension(final jct.test.rsc.jct.kernel.IJCTExpression dimension)
{
this.dimensions.remove(dimension);

}

public void removeDimension(final int anIndex)
{
this.dimensions.remove(anIndex);

}

public java.util.List getDimensions()
{
return java.util.Collections.unmodifiableList(this.dimensions);

}

public void setUnspecifiedDimensions(final int unspecifiedDimensions)
{
this.unspecifiedDimensions = unspecifiedDimensions;

}

public int getUnspecifiedDimensions()
{
return this.unspecifiedDimensions;

}

public void addInitializer(final int anIndex, final jct.test.rsc.jct.kernel.IJCTExpression initializer)
{
this.initializers.add(anIndex, initializer);

}

public void addInitializer(final jct.test.rsc.jct.kernel.IJCTExpression initializer)
{
this.initializers.add(initializer);

}

public void removeInitializer(final jct.test.rsc.jct.kernel.IJCTExpression initializer)
{
this.initializers.remove(initializer);

}

public void removeInitializer(final int anIndex)
{
this.initializers.remove(anIndex);

}

public java.util.List getInitializers()
{
return java.util.Collections.unmodifiableList(this.initializers);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.NEW_ARRAY;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitNewArray(this, aP);

}

public jct.test.rsc.jct.kernel.IJCTArrayType getTypeResult()
{
final java.lang.StringBuilder str = new java.lang.StringBuilder();
final int dims = this.getDimensions().size() + this.getUnspecifiedDimensions();
for(int i = 0; i < dims; ++ i) str.append(jct.test.rsc.jct.kernel.Constants.ARRAY_MARKER);
return this.getRootNode().getType(str.toString() + this.getElementType().getTypeName(), class);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
if(null != this.getElementType()) 
{
aWriter.append("new ");
this.getElementType().getSourceCode(aWriter);

}
for(jct.test.rsc.jct.kernel.IJCTExpression d : this.getDimensions()) 
{
aWriter.append('[');
d.getSourceCode(aWriter).append(']');

}
for(int i = 0; i < this.getUnspecifiedDimensions(); ++ i) aWriter.append("[]");
if(this.getInitializers().size() > 0) 
{
aWriter.append(" { ");
final java.util.Iterator it = this.getInitializers().iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(aWriter);
if(it.hasNext()) aWriter.append(", ");

}
aWriter.append(" }");

}
return aWriter;

}


}
