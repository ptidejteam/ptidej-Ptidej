package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.IJCTContainer;
class JCTRenamedRootNode
implements jct.test.rsc.jct.kernel.IJCTRootNode, jct.test.rsc.jct.util.IJCTContainer
{
final private jct.test.rsc.jct.kernel.IJCTRootNode original;

final private java.lang.String name;

private java.lang.ref.SoftReference factory;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode originalRootNode, final java.lang.String name)
{
this.<init>();
this.original = originalRootNode.getRootNode();
this.name = name;
this.factory = new java.lang.ref.SoftReference(null);

}

public jct.test.rsc.jct.kernel.IJCTFactory getFactory()
{
jct.test.rsc.jct.kernel.IJCTFactory f = this.factory.get();
if(null == f) this.factory = new java.lang.ref.SoftReference(f = new jct.test.rsc.jct.kernel.impl.JCTFactory(this));
return f;

}

public java.lang.String getName()
{
return this.name;

}

public boolean equals(final java.lang.Object o)
{
return (o instanceof jct.test.rsc.jct.kernel.IJCTRootNode) && ((jct.test.rsc.jct.kernel.IJCTRootNode)o).getRootNode().equals(this.getRootNode());

}

public jct.test.rsc.jct.kernel.IJCTPrimitiveType getType(final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{
return this.original.getType(aPrimitiveTypeConstant);

}

public jct.test.rsc.jct.kernel.IJCTType getType(final jct.test.rsc.jct.kernel.IJCTType[] types)
{
return this.original.getType(types);

}

public jct.test.rsc.jct.kernel.IJCTType getType(final java.lang.String path, final java.lang.Class typeClass)
{
return this.original.getType(path, typeClass);

}

public jct.test.rsc.jct.kernel.IJCTElement getEnclosingElement()
{
return this.original.getEnclosingElement();

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor aVisitor, final java.lang.Object p)
{
return this.original.accept(aVisitor, p);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return this.original.getKind();

}

public jct.test.rsc.jct.kernel.IJCTRootNode getRootNode()
{
return this.original.getRootNode();

}

public boolean isInitialized()
{
return this.original.isInitialized();

}

public void assumeInitialized()
{
this.original.assumeInitialized();

}

public void addPackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{
this.original.addPackage(aPackage);

}

public void removePackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{
this.original.removePackage(aPackage);

}

public java.util.Set getPackages()
{
return this.original.getPackages();

}

public void addOrphan(final int anIndex, final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.original.addOrphan(anIndex, orphan);

}

public void addOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.original.addOrphan(orphan);

}

public void removeOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.original.removeOrphan(orphan);

}

public void removeOrphan(final int anIndex)
{
this.original.removeOrphan(anIndex);

}

public java.util.List getOrphans()
{
return this.original.getOrphans();

}

public jct.test.rsc.jct.kernel.IJCTPath getPath()
{
return this.original.getPath();

}

public java.lang.String getID()
{
return this.original.getID();

}

public jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTPath aPath)
{
return this.original.walk(aPath);

}

public int hashCode()
{
return this.original.hashCode();

}

public java.lang.String getSourceCode()
{
return this.original.getSourceCode();

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return this.original.getSourceCode(aWriter);

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType)
{
return this.original.registerArrayType(underlyingType);

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType, final java.lang.String underlyingTypeName)
{
return this.original.registerArrayType(underlyingType, underlyingTypeName);

}

public java.util.Collection getEnclosedElements()
{
return ((jct.test.rsc.jct.util.IJCTContainer)this.original).getEnclosedElements();

}

public java.util.Collection getEnclosedElements(final java.lang.Class elementType)
{
return ((jct.test.rsc.jct.util.IJCTContainer)this.original).getEnclosedElements(elementType);

}

public java.util.Collection getAllEnclosedElements()
{
return ((jct.test.rsc.jct.util.IJCTContainer)this.original).getAllEnclosedElements();

}

public java.util.Collection getAllEnclosedElements(final jct.test.rsc.jct.kernel.JCTKind aKind, final java.lang.Class elementType, final boolean first_layer_only)
{
return ((jct.test.rsc.jct.util.IJCTContainer)this.original).getAllEnclosedElements(aKind, elementType, first_layer_only);

}


}
