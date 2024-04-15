package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.ListOfElements;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.collection.ListOfUnique;
import jct.test.rsc.jct.util.equiv.Identity;
class JCTRootNode
extends jct.test.rsc.jct.kernel.impl.JCTElementContainer
implements jct.test.rsc.jct.kernel.IJCTRootNode
{
final private java.util.Set packages = this.createInternalSet();

private java.lang.ref.SoftReference factory;

final private java.util.List orphans = jct.test.rsc.jct.util.ListOfElements.decorateList(new this.OrphanList());

private boolean initialized = false;

void <init>(final java.lang.String name)
{
this.<init>(null, name, null);
this.factory = new java.lang.ref.SoftReference(null);
this.backpatchElements(this.packages);

}

public jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTPath aPath)
{
return aPath.walk(this);

}

public jct.test.rsc.jct.kernel.IJCTFactory getFactory()
{
jct.test.rsc.jct.kernel.IJCTFactory f = this.factory.get();
if(null == f) this.factory = new java.lang.ref.SoftReference(f = new jct.test.rsc.jct.kernel.impl.JCTFactory(this));
return f;

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType)
{
return this.registerArrayType(underlyingType, null);

}

public jct.test.rsc.jct.kernel.IJCTPrimitiveType getType(final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{
jct.test.rsc.jct.kernel.IJCTPrimitiveType t = this.primitiveTypes.get(aPrimitiveTypeConstant);
if(null == t) this.primitiveTypes.put(aPrimitiveTypeConstant, t = new jct.test.rsc.jct.kernel.impl.JCTPrimitiveType(this, aPrimitiveTypeConstant));
return t;

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.IJCTPackage p : this.getPackages()) p.getSourceCode(aWriter);
return aWriter;

}

final protected jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
return this.createPathPart().setKind(null);

}

public boolean isInitialized()
{
return this.initialized;

}

public void assumeInitialized()
{
this.initialized = true;

}

public void addPackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{
this.packages.add(aPackage);

}

public void removePackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{
this.packages.remove(aPackage);

}

public java.util.Set getPackages()
{
return java.util.Collections.unmodifiableSet(this.packages);

}

public void addOrphan(final int anIndex, final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.orphans.add(anIndex, orphan);

}

public void addOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.orphans.add(orphan);

}

public void removeOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{
this.orphans.remove(orphan);

}

public void removeOrphan(final int anIndex)
{
this.orphans.remove(anIndex);

}

public java.util.List getOrphans()
{
return java.util.Collections.unmodifiableList(this.orphans);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.ROOT_NODE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitRootNode(this, aP);

}

private class OrphanList
extends jct.test.rsc.jct.util.collection.ListOfUnique
{
public void <init>()
{
this.<init>(new jct.test.rsc.jct.util.equiv.Identity());

}

public void add(final int i, final jct.test.rsc.jct.kernel.IJCTElement e)
{
final int old_index = this.indexOf(e);
if(i == old_index) return;
if(-1 != old_index) 
{
final int max = i > old_index ? i : old_index;
final java.util.ListIterator it = this.listIterator(i < old_index ? i : old_index);
while(it.hasNext() && it.nextIndex() <= max) 
{
final jct.test.rsc.jct.kernel.IJCTElement elem = it.next();
if(elem instanceof jct.test.rsc.jct.kernel.impl.JCTElement) this.discardCachedPathPartBuilderIndex();

}

}
super.add(i, e);
if(-1 == old_index) this.discardOrphansCachedPathPartBuilderIndex((jct.test.rsc.jct.kernel.impl.JCTElement)e);

}

public jct.test.rsc.jct.kernel.IJCTElement remove(final int i)
{
final jct.test.rsc.jct.kernel.impl.JCTElement e = (jct.test.rsc.jct.kernel.impl.JCTElement)this.get(i);
this.discardOrphansCachedPathPartBuilderIndex(e);
return super.remove(i);

}

public java.util.Spliterator spliterator()
{
return super.spliterator();

}


}

private void discardOrphansCachedPathPartBuilderIndex(final jct.test.rsc.jct.kernel.impl.JCTElement e)
{
final java.util.Iterator it = this.getOrphans().iterator();
while(it.hasNext() && e != it.next()) 
{

}
while(it.hasNext()) 
{
final jct.test.rsc.jct.kernel.IJCTElement elem = it.next();
if(null != elem && elem instanceof jct.test.rsc.jct.kernel.impl.JCTElement) this.discardCachedPathPartBuilderIndex();

}

}

final private java.util.Map arrayTypes = new java.util.HashMap();

final private java.util.Map intersectionTypes = new java.util.HashMap();

final private java.util.Map primitiveTypes = new java.util.HashMap();

final private java.util.Set classesTypes = new java.util.HashSet();

private jct.test.rsc.jct.kernel.IJCTArrayType getCachedArrayType(final java.lang.String name)
{
final java.lang.ref.WeakReference ref = this.arrayTypes.get(name);
if(null == ref) return null;
if(null == ref.get()) 
{
this.arrayTypes.remove(name);
return null;

}
return ref.get();

}

private jct.test.rsc.jct.kernel.IJCTIntersectionType getCachedIntersectionType(final java.lang.String name)
{
final java.lang.ref.WeakReference ref = this.intersectionTypes.get(name);
if(null == ref) return null;
if(null == ref.get()) 
{
this.intersectionTypes.remove(name);
return null;

}
return ref.get();

}

public jct.test.rsc.jct.kernel.IJCTType getType(final java.lang.String path, final java.lang.Class type)
{
if(1 == path.length()) return type.cast(this.getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.resolveType(path)));
if(jct.test.rsc.jct.kernel.Constants.ARRAY_MARKER == path.charAt(0)) 
{
jct.test.rsc.jct.kernel.IJCTArrayType t = this.getCachedArrayType(path);
if(null == t) this.arrayTypes.put(path, new java.lang.ref.WeakReference(t = new jct.test.rsc.jct.kernel.impl.JCTArrayType(this, this.getType(path.substring(1), class))));
return type.cast(t);

}
if(jct.test.rsc.jct.kernel.Constants.INTERSECTION_MARKER == path.charAt(0)) 
{
final java.lang.String[] names = jct.test.rsc.jct.kernel.Constants.INTERSECTION_SPLITTER_PATTERN.split(path.substring(1));
if(1 == names.length) return this.getType(path.substring(1), type);
jct.test.rsc.jct.kernel.IJCTIntersectionType t = this.getCachedIntersectionType(path);
if(null == t) 
{
final jct.test.rsc.jct.kernel.IJCTType[] types = new jct.test.rsc.jct.kernel.IJCTType[names.length];
for(int i = 0; i < names.length; ++ i) types[i] = this.getType(names[i], class);
this.intersectionTypes.put(path, new java.lang.ref.WeakReference(t = new jct.test.rsc.jct.kernel.impl.JCTIntersectionType(this, types)));

}
return type.cast(t);

}
if(jct.test.rsc.jct.kernel.Constants.CLASS_MARKER == path.charAt(0)) 
{
final jct.test.rsc.jct.kernel.impl.JCTPath p = this.getPath();
final int dotIndex = path.lastIndexOf(jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR);
java.lang.String[] classes;
if(-1 != dotIndex) 
{
classes = jct.test.rsc.jct.kernel.Constants.DOLLAR_SPLITTER_PATTERN.split(path.substring(dotIndex + 1));

}
 else classes = jct.test.rsc.jct.kernel.Constants.DOLLAR_SPLITTER_PATTERN.split(path.substring(1));
for(java.lang.String s : classes) p.addPart(new jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder(jct.test.rsc.jct.kernel.JCTKind.CLASS).setData(s).createPathPart());
final jct.test.rsc.jct.kernel.IJCTClass c = (jct.test.rsc.jct.kernel.IJCTClass)p.walk(this);
return type.cast(null == c ? null : c.createClassType());

}
throw new java.lang.IllegalArgumentException("A Type path must be a primitive type name (length = 1) or start by '" + jct.test.rsc.jct.kernel.Constants.ARRAY_MARKER + "', '" + jct.test.rsc.jct.kernel.Constants.CLASS_MARKER + "' or '" + jct.test.rsc.jct.kernel.Constants.INTERSECTION_MARKER + "'.
" + path);

}

public jct.test.rsc.jct.kernel.IJCTType getType(final jct.test.rsc.jct.kernel.IJCTType[] types)
{
if(0 == types.length) return this.getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID);
if(1 == types.length) return types[0];
final java.lang.String[] names = new java.lang.String[types.length];
for(int i = 0; i < types.length; ++ i) names[i] = types[i].getTypeName();
final java.lang.StringBuilder typeName = new java.lang.StringBuilder(jct.test.rsc.jct.kernel.Constants.INTERSECTION_MARKER);
for(java.lang.String name : names) typeName.append(name).append(jct.test.rsc.jct.kernel.Constants.INTERSECTION_SEPARATOR);
typeName.setLength(typeName.length() - 1);
jct.test.rsc.jct.kernel.IJCTIntersectionType t = this.getCachedIntersectionType(typeName.toString());
if(null == t) this.intersectionTypes.put(typeName.toString(), new java.lang.ref.WeakReference(t = new jct.test.rsc.jct.kernel.impl.JCTIntersectionType(this, types)));
return t;

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType, final java.lang.String underlyingTypeName)
{
final java.lang.String arrayTypePath = jct.test.rsc.jct.kernel.Constants.ARRAY_MARKER + (null == underlyingTypeName ? underlyingType.getTypeName() : underlyingTypeName);
jct.test.rsc.jct.kernel.IJCTArrayType t = this.getCachedArrayType(arrayTypePath);
if(null == t) this.arrayTypes.put(arrayTypePath, new java.lang.ref.WeakReference(t = new jct.test.rsc.jct.kernel.impl.JCTArrayType(this, underlyingType, underlyingTypeName)));
 else if(! t.getUnderlyingType().getTypeName().equals(underlyingType.getTypeName())) throw new java.lang.IllegalStateException("An array type is registered with this name (" + underlyingTypeName + "), but does not use the same underlying type.");
return t;

}

protected void registerClassType(final jct.test.rsc.jct.kernel.IJCTClassType aClassType)
{
this.classesTypes.add(new java.lang.ref.WeakReference(aClassType));

}

private jct.test.rsc.jct.util.ListOfElements getAllClassTypes()
{
final jct.test.rsc.jct.util.ListOfElements result = new jct.test.rsc.jct.util.ListOfElements();
final java.util.Iterator it = this.classesTypes.iterator();
while(it.hasNext()) 
{
final java.lang.ref.WeakReference wct = it.next();
if(null == wct.get()) it.remove();
 else result.add(wct.get());

}
return result;

}

public java.lang.String getSourceCode()
{
try
{
final java.io.StringWriter w = new java.io.StringWriter();
final java.lang.String packageSeparator = new java.lang.String(java.lang.Character.toChars(29));
final java.util.Iterator it = this.getPackages().iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(w);
if(it.hasNext()) w.append(packageSeparator);

}
return w.toString();

}
catch(java.io.IOException e) 
{
throw new java.lang.RuntimeException(e);

}

}

protected jct.test.rsc.jct.util.ListOfElements seeNextPathStep(final jct.test.rsc.jct.kernel.JCTKind aKind)
{
final jct.test.rsc.jct.util.ListOfElements result = new jct.test.rsc.jct.util.ListOfElements();
switch((aKind))
{
case jct.test.rsc.jct.kernel.JCTKind.INTERSECTION_TYPE:

{
final java.util.Iterator it = this.intersectionTypes.entrySet().iterator();
while(it.hasNext()) 
{
final java.util.Map.Entry entry = it.next();
final jct.test.rsc.jct.kernel.IJCTIntersectionType type = entry.getValue().get();
if(null == type) it.remove();
 else result.add(type);

}

}
break;
case jct.test.rsc.jct.kernel.JCTKind.ARRAY_TYPE:

{
final java.util.Iterator it = this.arrayTypes.entrySet().iterator();
while(it.hasNext()) 
{
final java.util.Map.Entry entry = it.next();
final jct.test.rsc.jct.kernel.IJCTArrayType type = entry.getValue().get();
if(null == type) it.remove();
 else result.add(type);

}

}
break;
case jct.test.rsc.jct.kernel.JCTKind.PRIMITIVE_TYPE:
return new jct.test.rsc.jct.util.ListOfElements(this.primitiveTypes.values());
case jct.test.rsc.jct.kernel.JCTKind.CLASS_TYPE:
return this.getAllClassTypes();
case jct.test.rsc.jct.kernel.JCTKind.ROOT_NODE:
result.add(this);
break;
case jct.test.rsc.jct.kernel.JCTKind.CLASS:
result.addAll(this.getAllEnclosedElements(jct.test.rsc.jct.kernel.JCTKind.CLASS, class, true));
default:
for(jct.test.rsc.jct.kernel.IJCTElement e : new jct.test.rsc.jct.util.collection.IndirectCollection(this.getEnclosedElements(), this.getOrphans())) if(aKind == e.getKind()) result.add(e);
}
return result;

}


}
