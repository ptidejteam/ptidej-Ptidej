package jct.test.rsc.jct.kernel.impl;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTComment;
import jct.test.rsc.jct.kernel.IJCTCompilationUnit;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.JCTKind;
abstract class JCTSourceCodePart
extends jct.test.rsc.jct.kernel.impl.JCTElementContainer
implements jct.test.rsc.jct.kernel.IJCTSourceCodePart
{
private java.lang.Integer storedSourceCodeOffset = null;

private java.lang.Integer storedSourceCodeLength = null;

final private java.util.List comments = new java.util.LinkedList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final java.util.Collection elements)
{
this.<init>(aRootNode, name, elements);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.util.Collection elements)
{
this.<init>(aRootNode, elements);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name)
{
this.<init>(aRootNode, name);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

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

public java.lang.Integer getStoredSourceCodeOffset()
{
return this.getEnclosingCompilationUnit() == null || this.getEnclosingCompilationUnit().getStoredSourceCode() == null ? null : this.getStoredSourceCodeOffset((jct.test.rsc.jct.kernel.IJCTSourceCodePart)this.getEnclosingElement());

}

public java.lang.Integer getStoredSourceCodeOffset(final jct.test.rsc.jct.kernel.IJCTSourceCodePart enclosingElement)
{
if(null == this.getEnclosingCompilationUnit()) return null;
try
{
final jct.test.rsc.jct.kernel.IJCTSourceCodePart realEnclosingElement = null == enclosingElement ? (jct.test.rsc.jct.kernel.IJCTSourceCodePart)this.getEnclosingElement() : enclosingElement;
if(this.getEnclosingElement() == realEnclosingElement) return this.storedSourceCodeOffset;
 else return this.storedSourceCodeOffset + ((jct.test.rsc.jct.kernel.IJCTSourceCodePart)this.getEnclosingElement()).getStoredSourceCodeOffset(realEnclosingElement);

}
catch(java.lang.ClassCastException e) 
{
throw new java.lang.IllegalArgumentException("enclosingElement must be an Enclosing Element of this", e);

}

}

public void setStoredSourceCodeOffset(final java.lang.Integer offset)
{
this.storedSourceCodeOffset = offset;

}

protected void updateStoredSourceCode(final jct.test.rsc.jct.kernel.impl.JCTSourceCodePart part, final int offset, final int delta)
{
if(offset < this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit())) this.setStoredSourceCodeOffset(offset + delta);
 else if(this != part) for(jct.test.rsc.jct.kernel.IJCTElement enclosingPart : this.getEnclosedElements()) if(enclosingPart instanceof jct.test.rsc.jct.kernel.impl.JCTSourceCodePart && offset <= this.getStoredSourceCodeOffset()) this.updateStoredSourceCode((jct.test.rsc.jct.kernel.impl.JCTSourceCodePart)enclosingPart, offset, delta);

}

public java.lang.Integer getStoredSourceCodeLength()
{
return this.getEnclosingCompilationUnit() == null || this.getEnclosingCompilationUnit().getStoredSourceCode() == null ? null : (null == this.storedSourceCodeLength ? this.getSourceCode().length() : this.storedSourceCodeLength);

}

public void setStoredSourceCodeLength(final java.lang.Integer length)
{
this.storedSourceCodeLength = length;

}

public java.lang.String getStoredSourceCode()
{
if(null == this.getEnclosingCompilationUnit()) return null;
final java.lang.String cuRepr = this.getEnclosingCompilationUnit().getStoredSourceCode();
if(null == cuRepr) return null;
final int absoluteOffset = this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit());
return cuRepr.substring(absoluteOffset, absoluteOffset + this.storedSourceCodeLength);

}

public void setStoredSourceCode(final java.lang.String storedSourceCode)
{
final java.lang.String actualStoredSourceCode = null == storedSourceCode ? "" : storedSourceCode;
((jct.test.rsc.jct.kernel.impl.JCTCompilationUnit)this.getEnclosingCompilationUnit()).updateStoredSourceCode(this, actualStoredSourceCode);
this.storedSourceCodeLength = actualStoredSourceCode.length();

}

public jct.test.rsc.jct.kernel.IJCTCompilationUnit getEnclosingCompilationUnit()
{
jct.test.rsc.jct.kernel.IJCTElement e = this;
while(null != e && jct.test.rsc.jct.kernel.JCTKind.COMPILATION_UNIT != e.getKind()) e = e.getEnclosingElement();
return (jct.test.rsc.jct.kernel.IJCTCompilationUnit)e;

}


}
