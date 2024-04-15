package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTIntersectionType
extends jct.test.rsc.jct.kernel.impl.JCTType
implements jct.test.rsc.jct.kernel.IJCTIntersectionType
{
final private java.util.Set types;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTType[] types)
{
this.<init>(aRootNode);
if(types.length <= 1) throw new java.lang.IllegalArgumentException("To create the intersection of 0 types, use JCTPrimitiveTypes.VOID, and to create the intersection type of a sole type, use the type itself.");
this.types = new java.util.HashSet();
final java.util.List lTypes = java.util.Arrays.asList(types);
final java.util.ListIterator it = lTypes.listIterator();
while(it.hasNext()) 
{
final jct.test.rsc.jct.kernel.IJCTType type = it.next();
if(type instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) 
{
it.remove();
for(jct.test.rsc.jct.kernel.IJCTType t : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)type).getTypes()) 
{
it.add(t);
it.previous();

}

}

}

}

public java.util.Set getTypes()
{
return java.util.Collections.unmodifiableSet(this.types);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
throw new java.lang.IllegalStateException("An Intersection Type should never appear in source code.");

}

public java.lang.String getTypeName()
{
final java.lang.StringBuilder typeName = new java.lang.StringBuilder(jct.test.rsc.jct.kernel.Constants.INTERSECTION_MARKER);
for(jct.test.rsc.jct.kernel.IJCTType type : this.getTypes()) typeName.append(type.getTypeName()).append(jct.test.rsc.jct.kernel.Constants.INTERSECTION_SEPARATOR);
return typeName.toString();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.INTERSECTION_TYPE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitIntersectionType(this, aP);

}


}
