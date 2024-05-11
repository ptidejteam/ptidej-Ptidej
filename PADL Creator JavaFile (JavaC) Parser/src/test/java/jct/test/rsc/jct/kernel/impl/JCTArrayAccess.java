/**
 * @author Mathieu Lemoine
 * @created 2009-01-08 (木)
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


import java.io.Writer;
import java.io.IOException;



import jct.test.rsc.jct.kernel.IJCTArrayAccess;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents an Array Access Expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTArrayAccess}
 *
 * @author Mathieu Lemoine
 */
class JCTArrayAccess extends JCTSourceCodePart<IJCTExpression> implements IJCTArrayAccess
{
    /**
     * array of this array access
     */
    private final NotNullableReference<IJCTExpression> array;
    
    /**
     * index of this array access
     */
    private final NotNullableReference<IJCTExpression> index;
    
    
    JCTArrayAccess(final IJCTRootNode aRootNode, final IJCTExpression array, final IJCTExpression index)
    {
        super(aRootNode);
        this.array = this.createInternalReference(array);
        this.index = this.createInternalReference(index);
        super.backpatchElements(new IndirectCollection<IJCTExpression>(this.array, this.index));
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        this.getArray().getSourceCode(aWriter).append('[');
        return this.getIndex().getSourceCode(aWriter).append(']');
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return ((IJCTArrayType)this.getArray().getTypeResult()).getUnderlyingType();
    }
    
    /**
     * Modifies the array of this array access
     *
     * @param array the new array
     */
    @Override
	public void setArray(final IJCTExpression array)
    {
        this.array.set(array);
    }
    
    /**
     * Returns the array of this array access
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getArray()
    {
        return this.array.get();
    }
    
    /**
     * Modifies the index of this array access
     *
     * @param index the new index
     */
    @Override
	public void setIndex(final IJCTExpression index)
    {
        this.index.set(index);
    }
    
    /**
     * Returns the index of this array access
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getIndex()
    {
        return this.index.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.ARRAY_ACCESS)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.ARRAY_ACCESS;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitArrayAccess(this, aP);
    }
    

}
