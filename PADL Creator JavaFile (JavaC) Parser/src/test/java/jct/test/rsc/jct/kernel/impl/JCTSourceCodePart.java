/**
 * @author Mathieu Lemoine
 * @created 2009-03-09 (月)
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



import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Collection;


import jct.test.rsc.jct.kernel.IJCTComment;
import jct.test.rsc.jct.kernel.IJCTCompilationUnit;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
import jct.test.rsc.jct.kernel.JCTKind;


/**
 * This class represents a element which is (indirectly) enclosed in
 * a {@link jct.test.rsc.jct.kernel.IJCTCompilationUnit compilation unit}.
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTSourceCodePart}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTSourceCodePart<Element extends IJCTElement> extends JCTElementContainer<Element> implements IJCTSourceCodePart
{
    /**
     * stored source code offset of this source code part
     */
    private Integer storedSourceCodeOffset = null;
    
    /**
     * stored source code length of this source code part
     */
    private Integer storedSourceCodeLength = null;
    
    /**
     * List of comments of this source code part
     */
    private final List<IJCTComment> comments = new LinkedList<IJCTComment>();
    
    
    /**
     * Identifiable contructor
     */
    JCTSourceCodePart(final IJCTRootNode aRootNode, final String name, final Collection<Element> elements)
    {
        super(aRootNode, name, elements);
    }
    
    /**
     * Non-Identifiable constructor
     */
    JCTSourceCodePart(final IJCTRootNode aRootNode, final Collection<Element> elements)
    {
        super(aRootNode, elements);
    }
    
    /**
     * Identifiable contructor with backpatch enclosed elements
     */
    JCTSourceCodePart(final IJCTRootNode aRootNode, final String name)
    {
        super(aRootNode, name);
    }
    
    /**
     * Non-Identifiable constructor with backpatch enclosed elements
     */
    JCTSourceCodePart(final IJCTRootNode aRootNode)
    {
        super(aRootNode);
    }
    
    /**
     * Adds a "comment" at the index (or move it there)
     */
    @Override
	public void addComment(final int anIndex, final IJCTComment aComment)
    {
        this.comments.add(anIndex, aComment);
    }
    
    /**
     * Adds a "comment" at the end of the list (or move it there)
     */
    @Override
	public void addComment(final IJCTComment aComment)
    {
        this.comments.add(aComment);
    }
    
    /**
     * Removes this comment from the list
     */
    @Override
	public void removeComment(final IJCTComment aComment)
    {
        this.comments.remove(aComment);
    }
    
    /**
     * Remove the comment at the index
     */
    @Override
	public void removeComment(final int anIndex)
    {
        this.comments.remove(anIndex);
    }
    
    /**
     * Returns the list of comments of this source code part
     */
    @Override
	public List<IJCTComment> getComments()
    {
        return Collections.unmodifiableList(this.comments);
    }
    
    /**
     * Returns the offset of the stored code source representation of
     * this element, within its direct enclosing element's.
     *
     * @return null iff not (indirectly) enclosed in a {@link jct.test.rsc.jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
     */
    @Override
	public Integer getStoredSourceCodeOffset()
    {
        return this.getEnclosingCompilationUnit() == null
            || this.getEnclosingCompilationUnit().getStoredSourceCode() == null
            ? null
            : this.getStoredSourceCodeOffset((IJCTSourceCodePart)this.getEnclosingElement());
    }
    
    @Override
	public Integer getStoredSourceCodeOffset(final IJCTSourceCodePart enclosingElement)
    {
        if(null == this.getEnclosingCompilationUnit())
            return null;
    
        try
        {
            final IJCTSourceCodePart realEnclosingElement = null == enclosingElement
                ? (IJCTSourceCodePart)this.getEnclosingElement()
                : enclosingElement;
            
            if(this.getEnclosingElement() == realEnclosingElement)
                return this.storedSourceCodeOffset;
            else
                return this.storedSourceCodeOffset
                    + ((IJCTSourceCodePart)this.getEnclosingElement()).getStoredSourceCodeOffset(realEnclosingElement);
        }
        catch(final ClassCastException e)
        { throw new IllegalArgumentException("enclosingElement must be an Enclosing Element of this", e); }
    }
    
    @Override
	public void setStoredSourceCodeOffset(final Integer offset)
    { this.storedSourceCodeOffset = offset;}
    
    protected void updateStoredSourceCode(final JCTSourceCodePart part, final int offset, final int delta)
    {
        if(offset < this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit()))
            this.setStoredSourceCodeOffset(offset + delta);
        else // offset == this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit())
            if(this != part)
                for(final IJCTElement enclosingPart : this.getEnclosedElements())
                    if(enclosingPart instanceof JCTSourceCodePart
                       && offset <= ((JCTSourceCodePart)enclosingPart).getStoredSourceCodeOffset())
                        ((JCTSourceCodePart)enclosingPart).updateStoredSourceCode((JCTSourceCodePart)enclosingPart,
                                                                                  offset, delta);
    }
    
    /**
     * Returns the length of the stored code source representation of this element.
     *
     * @return null iff not (indirectly) enclosed in a {@link jct.test.rsc.jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
     */
    @Override
	public Integer getStoredSourceCodeLength()
    {
        return this.getEnclosingCompilationUnit() == null
            || this.getEnclosingCompilationUnit().getStoredSourceCode() == null
            ? null
            : (null == this.storedSourceCodeLength ? this.getSourceCode().length() : this.storedSourceCodeLength);
    }
    
    @Override
	public void setStoredSourceCodeLength(final Integer length)
    { this.storedSourceCodeLength = length; }
    
    /**
     * Returns the stored code source representation of this element.
     *
     * @return null iff not (indirectly) enclosed in a {@link jct.test.rsc.jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
     */
    @Override
	public String getStoredSourceCode()
    {
        if(null == this.getEnclosingCompilationUnit())
            return null;
    
        final String cuRepr = this.getEnclosingCompilationUnit().getStoredSourceCode();
        if(null == cuRepr)
            return null;
    
        final int absoluteOffset = this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit());
    
        return cuRepr.substring(absoluteOffset, absoluteOffset + this.storedSourceCodeLength);
    }
    
    @Override
	public void setStoredSourceCode(final String storedSourceCode)
    {
        final String actualStoredSourceCode = null == storedSourceCode
            ? ""
            : storedSourceCode;
    
        ((JCTCompilationUnit)this.getEnclosingCompilationUnit()).updateStoredSourceCode(this, actualStoredSourceCode);
        this.storedSourceCodeLength = actualStoredSourceCode.length();
    }
    
    @Override
	public IJCTCompilationUnit getEnclosingCompilationUnit()
    {
        IJCTElement e = this;
    
        while (null != e
               && JCTKind.COMPILATION_UNIT != e.getKind())
            e = e.getEnclosingElement();
    
        return (IJCTCompilationUnit) e;
    }

}
