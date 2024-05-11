package jct.test.rsc.jct.kernel.impl;
import java.io.File;
import java.util.Arrays;
import jct.test.rsc.jct.kernel.IJCTCompilationUnit;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPathPart;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.IJCTContainer;
public class JCTFileOffsetPath
implements jct.test.rsc.jct.kernel.IJCTPath
{
final private byte[] informativeData;

final private java.io.File file;

final private java.lang.Integer offset;

public void <init>(final java.io.File aFile, final java.lang.Integer offset)
{
this.<init>(aFile, offset, null);

}

public void <init>(final java.io.File aFile, final java.lang.Integer offset, final byte[] informativeData)
{
this.<init>();
this.file = aFile;
this.offset = offset;
this.informativeData = informativeData;

}

public void <init>(final java.io.File aFile)
{
this.<init>(aFile, null);

}

public jct.test.rsc.jct.kernel.IJCTPath getPathToEnclosing()
{
return null;

}

public byte[] getInformativeData()
{
return this.informativeData;

}

public java.io.File getFile()
{
return this.file;

}

public java.lang.Integer getOffset()
{
return this.offset;

}

public jct.test.rsc.jct.kernel.JCTKind getResultKind()
{
throw new java.lang.UnsupportedOperationException("A File Offset Path can not know the kind of the element it designs.");

}

public java.lang.String getData()
{
throw new java.lang.UnsupportedOperationException("A File Offset Path can not know the data of the element it designs.");

}

public java.lang.Integer getIndex()
{
throw new java.lang.UnsupportedOperationException("A File Offset Path can not know the index of the element it designs.");

}

public jct.test.rsc.jct.kernel.IJCTSourceCodePart walk(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
jct.test.rsc.jct.kernel.IJCTCompilationUnit cu = null;
for(jct.test.rsc.jct.kernel.IJCTCompilationUnit it : ((jct.test.rsc.jct.util.IJCTContainer)aRootNode).getAllEnclosedElements(jct.test.rsc.jct.kernel.JCTKind.COMPILATION_UNIT, class, true)) if(it.getSourceFile().equals(this.file)) 
{
cu = it;
break;

}
if(null == cu || null == cu.getStoredSourceCodeLength()) return null;
if(null == this.getOffset() || 0 == this.getOffset()) return cu;
return this.walkDownThePath(cu);

}

private jct.test.rsc.jct.kernel.IJCTSourceCodePart walkDownThePath(final jct.test.rsc.jct.kernel.IJCTSourceCodePart aSourceCodePart, final jct.test.rsc.jct.kernel.IJCTCompilationUnit topLevel)
{
if(this.getOffset() == aSourceCodePart.getStoredSourceCodeOffset(topLevel)) return aSourceCodePart;
if(aSourceCodePart instanceof jct.test.rsc.jct.util.IJCTContainer) for(jct.test.rsc.jct.kernel.IJCTSourceCodePart scp : ((jct.test.rsc.jct.util.IJCTContainer)aSourceCodePart).getEnclosedElements()) 
{
final int absoluteOffset = scp.getStoredSourceCodeOffset(topLevel);
if(this.offset >= absoluteOffset && this.offset < absoluteOffset + scp.getStoredSourceCodeLength()) return this.walkDownThePath(scp, topLevel);

}
return aSourceCodePart;

}

public void addPart(final jct.test.rsc.jct.kernel.IJCTPathPart part)
{
throw new java.lang.UnsupportedOperationException("A File Offset Path can not be cut in parts.");

}

public jct.test.rsc.jct.kernel.IJCTPathPart getFirstPart()
{
return null;

}

public jct.test.rsc.jct.kernel.IJCTPathPart getLastPart()
{
return null;

}

public jct.test.rsc.jct.kernel.impl.JCTFileOffsetPath clone()
{
return this;

}

public boolean equals(java.lang.Object that)
{
if(this == that) return true;
if(! (that instanceof jct.test.rsc.jct.kernel.impl.JCTFileOffsetPath)) return false;
final jct.test.rsc.jct.kernel.impl.JCTFileOffsetPath path = (jct.test.rsc.jct.kernel.impl.JCTFileOffsetPath)that;
return this.getFile().equals(path.getFile()) && (null == this.getOffset() ? null == path.getOffset() : this.getOffset().equals(path.getOffset())) && (null == this.getInformativeData() ? null == path.getInformativeData() : java.util.Arrays.equals(this.getInformativeData(), path.getInformativeData()));

}

public boolean isEnclosing(jct.test.rsc.jct.kernel.IJCTPath that)
{
throw new java.lang.UnsupportedOperationException("A File Offset Path can not know if another path is enclosing it.");

}


}
