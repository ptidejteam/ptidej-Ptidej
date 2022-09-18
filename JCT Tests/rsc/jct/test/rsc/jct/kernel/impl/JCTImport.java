package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTImport;
import jct.test.rsc.jct.kernel.IJCTImportable;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTImport
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTImport
{
private jct.test.rsc.jct.kernel.IJCTImportable importedElement;

private boolean isStatic;

private boolean isOnDemand;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTImportable importedElement, final boolean isStatic, final boolean isOnDemand)
{
this.<init>(aRootNode);
this.importedElement = importedElement;
this.isStatic = isStatic;
this.isOnDemand = isOnDemand;
this.check();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.IMPORT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitImport(this, aP);

}

protected void check() throws java.lang.IllegalStateException
{
if(this.isOnDemand && this.isStatic && (jct.test.rsc.jct.kernel.JCTKind.CLASS != this.importedElement.getKind() || null != ((jct.test.rsc.jct.kernel.IJCTClass)this.importedElement).getDirectEnclosingClass())) throw new java.lang.IllegalStateException("On demand, static import must be on class");
if(this.isOnDemand && ! this.isStatic && jct.test.rsc.jct.kernel.JCTKind.PACKAGE != this.importedElement.getKind()) throw new java.lang.IllegalStateException("On demand, non static import must be on package");
if(! this.isOnDemand && this.isStatic && (jct.test.rsc.jct.kernel.JCTKind.PACKAGE == this.importedElement.getKind() || (jct.test.rsc.jct.kernel.JCTKind.CLASS == this.importedElement.getKind() && null == ((jct.test.rsc.jct.kernel.IJCTClass)this.importedElement).getDirectEnclosingClass()) || ! ((jct.test.rsc.jct.kernel.IJCTClassMember)this.importedElement).isStatic())) throw new java.lang.IllegalStateException("Static import must be on static class members");
if(! this.isOnDemand && ! this.isStatic && jct.test.rsc.jct.kernel.JCTKind.CLASS != this.importedElement.getKind()) throw new java.lang.IllegalStateException("Non static import must be on class");

}

public boolean getIsStatic()
{
return this.isStatic;

}

public void setIsStatic(final boolean s)
{
if(s == this.isStatic) return;
final boolean save = this.isStatic;
this.isStatic = s;
try
{
this.check();

}
catch(java.lang.IllegalStateException e) 
{
this.isStatic = save;
throw new java.lang.IllegalArgumentException(e);

}

}

public boolean getIsOnDemand()
{
return this.isOnDemand;

}

public void setIsOnDemand(final boolean od)
{
if(od == this.isOnDemand) return;
final boolean save = this.isOnDemand;
this.isOnDemand = od;
try
{
this.check();

}
catch(java.lang.IllegalStateException e) 
{
this.isOnDemand = save;
throw new java.lang.IllegalArgumentException(e);

}

}

public jct.test.rsc.jct.kernel.IJCTImportable getImportedElement()
{
return this.importedElement;

}

public void setImportedElement(final jct.test.rsc.jct.kernel.IJCTImportable i)
{
final jct.test.rsc.jct.kernel.IJCTImportable save = this.importedElement;
this.importedElement = i;
try
{
this.check();

}
catch(java.lang.IllegalStateException e) 
{
this.importedElement = save;
throw new java.lang.IllegalArgumentException(e);

}

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
w.append("import ");
if(this.getIsStatic()) w.append("static ");
switch((this.getImportedElement().getKind()))
{
case jct.test.rsc.jct.kernel.JCTKind.PACKAGE:
w.append(this.getImportedElement().getName());
break;
case jct.test.rsc.jct.kernel.JCTKind.CLASS:
w.append(((jct.test.rsc.jct.kernel.IJCTClass)this.getImportedElement()).getFQN());
break;
default:

{
final jct.test.rsc.jct.kernel.IJCTClass c = (jct.test.rsc.jct.kernel.IJCTClass)this.getImportedElement().getEnclosingElement();
w.append(c.getFQN() + "." + this.getImportedElement().getName());

}
}
if(this.getIsOnDemand()) w.append(".*");
return w.append(";
");

}

public jct.test.rsc.jct.kernel.impl.JCTPathPartBuilder createPathPart()
{
final java.lang.StringBuilder str = new java.lang.StringBuilder();
str.append(this.getIsStatic() ? '1' : '0');
str.append(this.getIsOnDemand() ? '1' : '0');
switch((this.getImportedElement().getKind()))
{
case jct.test.rsc.jct.kernel.JCTKind.PACKAGE:
str.append(this.getImportedElement().getName());
break;
case jct.test.rsc.jct.kernel.JCTKind.CLASS:
str.append(((jct.test.rsc.jct.kernel.IJCTClass)this.getImportedElement()).getFQN());
break;
default:

{
final jct.test.rsc.jct.kernel.IJCTClass c = (jct.test.rsc.jct.kernel.IJCTClass)this.getImportedElement().getEnclosingElement();
str.append(c.getFQN()).append('.').append(this.getImportedElement().getName());

}
break;
}
return this.createPathPart().setData(str.toString());

}

public boolean equals(final java.lang.Object o)
{
return this.toString().equals(o.toString());

}

public int hashCode()
{
return this.toString().hashCode();

}

public java.lang.String toString()
{
return this.getSourceCode();

}


}
