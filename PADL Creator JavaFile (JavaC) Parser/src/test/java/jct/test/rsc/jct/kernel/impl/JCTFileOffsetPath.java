/**
 * @author Mathieu Lemoine
 * @created 2009-03-10
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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



/**
 * This path implementation is used to locate a sub tree by the
 * file it is in and the offset position.
 * If the offset is {@literal null}, then the path points to the Compilation Unit.
 *
 * @author Mathieu Lemoine
 */
public class JCTFileOffsetPath implements IJCTPath
{
    /**
     * informative data of this file offset path
     */
    private final byte[] informativeData;
    
    /**
     * file of this file offset path
     */
    private final File file;
    
    /**
     * offset of this file offset path
     */
    private final Integer offset;
    
    
    /**
     * Simple Constructor
     */
    public JCTFileOffsetPath(final File aFile, final Integer offset)
    {
        this(aFile, offset, null);
    }
    
    /**
     * Complete Constructor
     */
    public JCTFileOffsetPath(final File aFile, final Integer offset, final byte[] informativeData)
    {
        this.file = aFile;
        this.offset = offset;
        this.informativeData = informativeData;
    }
    
    /**
     * Compilation Unit Path Constructor
     */
    public JCTFileOffsetPath(final File aFile)
    {
        this(aFile, null);
    }
    
    /**
     * Always returns null
     */
    @Override
	public IJCTPath getPathToEnclosing()
    {
        return null;
    }
    
    /**
     * Returns the informative data of this file offset path
     */
    @Override
	public byte[] getInformativeData()
    {
        return this.informativeData;
    }
    
    /**
     * Returns the file of this file offset path
     */
    public File getFile()
    {
        return this.file;
    }
    
    /**
     * Returns the offset of this file offset path
     */
    public Integer getOffset()
    {
        return this.offset;
    }
    
    @Override
	public JCTKind getResultKind()
    { throw new UnsupportedOperationException("A File Offset Path can not know the kind of the element it designs."); }
    
    @Override
	public String getData()
    { throw new UnsupportedOperationException("A File Offset Path can not know the data of the element it designs."); }
    
    @Override
	public Integer getIndex()
    { throw new UnsupportedOperationException("A File Offset Path can not know the index of the element it designs."); }
    
    @Override
	public IJCTSourceCodePart walk(final IJCTRootNode aRootNode)
    {
        IJCTCompilationUnit cu = null;
        for(final IJCTCompilationUnit it : ((IJCTContainer<?>)aRootNode).getAllEnclosedElements(JCTKind.COMPILATION_UNIT, IJCTCompilationUnit.class, true))
            if(it.getSourceFile().equals(this.file))
            {
                cu = it;
                break;
            }
    
        if(null == cu
           || null == cu.getStoredSourceCodeLength())
            return null;
    
        if(null == this.getOffset()
           || 0 == this.getOffset())
            return cu;
    
        return this.walkDownThePath(cu, cu);
    }
    
    @SuppressWarnings("unchecked")
	private IJCTSourceCodePart walkDownThePath(final IJCTSourceCodePart aSourceCodePart, final IJCTCompilationUnit topLevel)
    {
        if(this.getOffset() == aSourceCodePart.getStoredSourceCodeOffset(topLevel))
            return aSourceCodePart;
    
        if(aSourceCodePart instanceof IJCTContainer)
            for(final IJCTSourceCodePart scp : ((IJCTContainer<IJCTSourceCodePart>)aSourceCodePart).getEnclosedElements())
            {
                final int absoluteOffset = scp.getStoredSourceCodeOffset(topLevel);
                if(this.offset >= absoluteOffset
                   && this.offset < absoluteOffset + scp.getStoredSourceCodeLength())
                    return this.walkDownThePath(scp, topLevel);
            }
    
        return aSourceCodePart;
    }
    
    @Override
	public void addPart(final IJCTPathPart part)
    { throw new UnsupportedOperationException("A File Offset Path can not be cut in parts."); }
    
    @Override
	public IJCTPathPart getFirstPart()
    { return null; }
    
    @Override
	public IJCTPathPart getLastPart()
    { return null; }
    
    /**
     * Since JCTFileOffsetPath are immutable, no clone implementation is needed.
     *
     * @return {@code this}
     */
    @Override
    public JCTFileOffsetPath clone()
    { return this; }
    
    @Override
    public boolean equals(Object that)
    {
        if(this == that)
            return true;
    
        if(!(that instanceof JCTFileOffsetPath))
            return false;
    
        final JCTFileOffsetPath path = (JCTFileOffsetPath)that;
    
        return this.getFile().equals(path.getFile())
            && (null == this.getOffset() ? null == path.getOffset() : this.getOffset().equals(path.getOffset()))
            && (null == this.getInformativeData() ? null == path.getInformativeData() : Arrays.equals(this.getInformativeData(), path.getInformativeData()));
    }
    
    @Override
	public boolean isEnclosing(IJCTPath that)
    { throw new UnsupportedOperationException("A File Offset Path can not know if another path is enclosing it."); }

}
