package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTCompilationUnit;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.ListOfElements;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTPackage
extends jct.test.rsc.jct.kernel.impl.JCTElementContainer
implements jct.test.rsc.jct.kernel.IJCTPackage
{
final private jct.test.rsc.jct.util.reference.NullableReference packageDeclaration = this.createNullableInternalReference();

final private java.util.List compilationUnits = this.createInternalList();

private boolean isGhost;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final boolean isGhost)
{
this.<init>(aRootNode, name, null, isGhost);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTCompilationUnit packageDeclaration, final boolean isGhost)
{
this.<init>(aRootNode, name);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.packageDeclaration, this.compilationUnits));
this.isGhost = isGhost;
this.packageDeclaration.set(packageDeclaration);

}

public jct.test.rsc.jct.kernel.IJCTCompilationUnit getPackageDeclaration()
{
return this.packageDeclaration.get();

}

public void setPackageDeclaration(final jct.test.rsc.jct.kernel.IJCTCompilationUnit packageDeclaration)
{
if(null != packageDeclaration && ! packageDeclaration.isPackageDeclaration()) throw new java.lang.IllegalArgumentException("Compilation Unit must be a package declaration");
this.packageDeclaration.set(packageDeclaration);

}

public java.lang.String getID()
{
return this.getName() + super.getID();

}

public boolean isUnnamed()
{
return null == this.getName();

}

public void addCompilationUnit(final int anIndex, final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{
this.compilationUnits.add(anIndex, aCompilationUnit);

}

public void addCompilationUnit(final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{
this.compilationUnits.add(aCompilationUnit);

}

public void removeCompilationUnit(final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{
this.compilationUnits.remove(aCompilationUnit);

}

public void removeCompilationUnit(final int anIndex)
{
this.compilationUnits.remove(anIndex);

}

public java.util.List getCompilationUnits()
{
return java.util.Collections.unmodifiableList(this.compilationUnits);

}

public void setIsGhost(final boolean isGhost)
{
this.isGhost = isGhost;

}

public boolean getIsGhost()
{
return this.isGhost;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.PACKAGE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitPackage(this, aP);

}

protected jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
final jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder p = this.createPathPart();
if(null == p.getInformativeData()) 
{
byte informativeData = 0;
if(this.getIsGhost()) informativeData |= 1;
p.setInformativeData(new byte[] { informativeData });

}
return p;

}

public java.lang.String getSourceCode()
{
try
{
final java.lang.String fileSeparator = new java.lang.String(java.lang.Character.toChars(28));
final java.io.StringWriter w = new java.io.StringWriter();
final java.util.Iterator it = this.getEnclosedElements().iterator();
while(it.hasNext()) 
{
it.next().getSourceCode(w);
if(it.hasNext()) w.append(fileSeparator);

}
return w.toString();

}
catch(java.io.IOException e) 
{
throw new java.lang.RuntimeException(e);

}

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
for(jct.test.rsc.jct.kernel.IJCTCompilationUnit cu : this.getEnclosedElements()) if(cu != null) cu.getSourceCode(w);
return w;

}

protected jct.test.rsc.jct.util.ListOfElements seeNextPathStep(final jct.test.rsc.jct.kernel.JCTKind aKind)
{
if(jct.test.rsc.jct.kernel.JCTKind.CLASS == aKind) return new jct.test.rsc.jct.util.ListOfElements((java.util.Collection)this.getAllEnclosedElements(jct.test.rsc.jct.kernel.JCTKind.CLASS, class, true));
final jct.test.rsc.jct.util.ListOfElements result = new jct.test.rsc.jct.util.ListOfElements();
for(jct.test.rsc.jct.kernel.IJCTCompilationUnit e : this.getEnclosedElements()) if(null != e && aKind == e.getKind()) result.add((jct.test.rsc.jct.kernel.IJCTElement)e);
return result;

}


}
