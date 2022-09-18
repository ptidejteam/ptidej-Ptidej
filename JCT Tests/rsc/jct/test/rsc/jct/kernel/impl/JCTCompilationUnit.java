package jct.test.rsc.jct.kernel.impl;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTComment;
import jct.test.rsc.jct.kernel.IJCTCompilationUnit;
import jct.test.rsc.jct.kernel.IJCTImport;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
class JCTCompilationUnit
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTCompilationUnit
{
private java.io.File sourceFile;

final private java.util.Set importeds = this.createInternalSet();

final private java.util.Set clazzs = this.createInternalSet();

private java.lang.StringBuffer storedSourceCode = null;

final private java.util.List comments = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.io.File sourceFile)
{
this.<init>(aRootNode, sourceFile.getAbsolutePath());
this.sourceFile = sourceFile;
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.importeds, this.clazzs, this.comments));

}

public boolean isPackageDeclaration()
{
return this.sourceFile.getName().equals(jct.test.rsc.jct.kernel.Constants.PACKAGE_DECLARATION_FILENAME);

}

public void addImported(final jct.test.rsc.jct.kernel.IJCTImport imported)
{
this.importeds.add(imported);

}

public void removeImported(final jct.test.rsc.jct.kernel.IJCTImport imported)
{
this.importeds.remove(imported);

}

public java.util.Set getImporteds()
{
return java.util.Collections.unmodifiableSet(this.importeds);

}

public void addClazz(final jct.test.rsc.jct.kernel.IJCTClass clazz)
{
this.clazzs.add(clazz);

}

public void removeClazz(final jct.test.rsc.jct.kernel.IJCTClass clazz)
{
this.clazzs.remove(clazz);

}

public java.util.Set getClazzs()
{
return java.util.Collections.unmodifiableSet(this.clazzs);

}

public void addComment(final int anIndex, final jct.test.rsc.jct.kernel.IJCTComment aComment)
{
this.comments.add(anIndex, aComment);

}

public void addComment(final jct.test.rsc.jct.kernel.IJCTComment aComment)
{
this.comments.add(aComment);

}

public void removeComment(final jct.test.rsc.jct.kernel.IJCTComment aComment)
{
this.comments.remove(aComment);

}

public void removeComment(final int anIndex)
{
this.comments.remove(anIndex);

}

public java.util.List getComments()
{
return java.util.Collections.unmodifiableList(this.comments);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.COMPILATION_UNIT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitCompilationUnit(this, aP);

}

public java.lang.String getSourceCode()
{
try
{
final java.io.StringWriter w = new java.io.StringWriter().append(this.getSourceFile().getPath()).append(new java.lang.String(java.lang.Character.toChars(0)));
return this.getSourceCode(w).toString();

}
catch(java.io.IOException e) 
{
throw new java.lang.RuntimeException(e);

}

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
final jct.test.rsc.jct.kernel.IJCTPackage p = (jct.test.rsc.jct.kernel.IJCTPackage)this.getEnclosingElement();
if(null != p && ! p.isUnnamed()) aWriter.append("package " + p.getName() + ";
");
final java.util.SortedSet is = new java.util.TreeSet(new java.util.Comparator()
{
void <init>()
{
this.<init>();

}

public int compare(final jct.test.rsc.jct.kernel.IJCTImport o1, final jct.test.rsc.jct.kernel.IJCTImport o2)
{
return o1.toString().compareTo(o2.toString());

}

});
is.addAll(this.getImporteds());
for(jct.test.rsc.jct.kernel.IJCTImport i : is) i.getSourceCode(aWriter);
for(jct.test.rsc.jct.kernel.IJCTClass c : this.getClazzs()) c.getSourceCode(aWriter);
return aWriter;

}

public void setStoredSourceCodeLength(final java.lang.Integer length)
{
throw new java.lang.UnsupportedOperationException("A compilation unit is not pre-calculated");

}

public java.lang.Integer getStoredSourceCodeLength()
{
if(null != this.storedSourceCode) this.setStoredSourceCodeLength(this.storedSourceCode.length());
return this.getStoredSourceCodeLength();

}

public void setStoredSourceCodeOffset(final java.lang.Integer offset)
{
throw new java.lang.UnsupportedOperationException("A compilation unit is always at offset 0");

}

public java.lang.Integer getStoredSourceCodeOffset()
{
this.setStoredSourceCodeOffset(0);
return null == this.getStoredSourceCode() ? null : 0;

}

public java.lang.Integer getStoredSourceCodeOffset(final jct.test.rsc.jct.kernel.IJCTSourceCodePart enclosingElement)
{
if(this != enclosingElement) throw new java.lang.IllegalArgumentException("enclosingElement must be an Enclosing Element of this");
return this.getStoredSourceCodeOffset();

}

public java.lang.String getStoredSourceCode()
{
return null == this.storedSourceCode ? null : this.storedSourceCode.toString();

}

public void setStoredSourceCode(final java.lang.String storedSourceCode)
{
this.storedSourceCode = new java.lang.StringBuffer(storedSourceCode);

}

protected void updateStoredSourceCode(final jct.test.rsc.jct.kernel.impl.JCTSourceCodePart part, final java.lang.String storedSourceCode)
{
if(null == this.storedSourceCode) throw new java.lang.IllegalStateException("Impossible to replace a part of Stored Source Code if there is no SSC.");
final int absoluteOffset = this.getStoredSourceCodeOffset(this);
this.storedSourceCode.replace(absoluteOffset, this.getStoredSourceCodeLength(), storedSourceCode);
final int delta = storedSourceCode.length() - this.getStoredSourceCodeLength();
if(delta > 0) for(jct.test.rsc.jct.kernel.IJCTSourceCodePart enclosingPart : this.getEnclosedElements()) if(enclosingPart instanceof jct.test.rsc.jct.kernel.impl.JCTSourceCodePart && absoluteOffset <= enclosingPart.getStoredSourceCodeOffset()) this.updateStoredSourceCode(part, absoluteOffset, delta);

}

public java.io.File getSourceFile()
{
return this.sourceFile;

}

public void setSourceFile(final java.io.File sourceFile)
{
this.sourceFile = sourceFile;
this.setName(sourceFile.getAbsolutePath());

}


}
