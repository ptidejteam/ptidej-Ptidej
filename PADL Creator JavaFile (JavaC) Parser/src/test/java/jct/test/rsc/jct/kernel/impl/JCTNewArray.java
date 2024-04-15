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



import java.util.List;
import java.util.Collections;


import java.io.Writer;
import java.io.IOException;

import java.util.Iterator;

import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTNewArray;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;


/**
 * This class represents a new array expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTNewArray}
 *
 * @author Mathieu Lemoine
 */
class JCTNewArray extends JCTSourceCodePart<IJCTExpression> implements IJCTNewArray
{
    /**
     * element type of this new array
     */
    private IJCTType elementType;
    
    /**
     * List of dimensions of this new array
     */
    private final List<IJCTExpression> dimensions = this.createInternalList();
    
    /**
     * unspecified dimensions of this new array
     */
    private int unspecifiedDimensions;
    
    /**
     * List of initializers of this new array
     */
    private final List<IJCTExpression> initializers = this.createInternalList();
    
    
    JCTNewArray(final IJCTRootNode aRootNode, final IJCTType elementType)
    {
        super(aRootNode);
        this.elementType = elementType;
        super.backpatchElements(new IndirectCollection<IJCTExpression>(this.dimensions, this.initializers));
    }
    
    /**
     * Modifies the element type of this new array
     *
     * @param elementType the new element type
     */
    @Override
	public void setElementType(final IJCTType elementType)
    {
        this.elementType = elementType;
    }
    
    /**
     * Returns the element type of this new array
     */
    @Override
	public IJCTType getElementType()
    {
        return this.elementType;
    }
    
    /**
     * Adds a "dimension" at the index (or move it there)
     */
    @Override
	public void addDimension(final int anIndex, final IJCTExpression dimension)
    {
        this.dimensions.add(anIndex, dimension);
    }
    
    /**
     * Adds a "dimension" at the end of the list (or move it there)
     */
    @Override
	public void addDimension(final IJCTExpression dimension)
    {
        this.dimensions.add(dimension);
    }
    
    /**
     * Removes this dimension from the list
     */
    @Override
	public void removeDimension(final IJCTExpression dimension)
    {
        this.dimensions.remove(dimension);
    }
    
    /**
     * Remove the dimension at the index
     */
    @Override
	public void removeDimension(final int anIndex)
    {
        this.dimensions.remove(anIndex);
    }
    
    /**
     * Returns the list of dimensions of this new array
     * <em>Part of the enclosed elements.</em>
     */
    @Override
	public List<IJCTExpression> getDimensions()
    {
        return Collections.unmodifiableList(this.dimensions);
    }
    
    /**
     * Modifies the unspecified dimensions of this new array
     *
     * @param unspecifiedDimensions the new unspecified dimensions
     */
    @Override
	public void setUnspecifiedDimensions(final int unspecifiedDimensions)
    {
        this.unspecifiedDimensions = unspecifiedDimensions;
    }
    
    /**
     * Returns the unspecified dimensions of this new array
     */
    @Override
	public int getUnspecifiedDimensions()
    {
        return this.unspecifiedDimensions;
    }
    
    /**
     * Adds a "initializer" at the index (or move it there)
     */
    @Override
	public void addInitializer(final int anIndex, final IJCTExpression initializer)
    {
        this.initializers.add(anIndex, initializer);
    }
    
    /**
     * Adds a "initializer" at the end of the list (or move it there)
     */
    @Override
	public void addInitializer(final IJCTExpression initializer)
    {
        this.initializers.add(initializer);
    }
    
    /**
     * Removes this initializer from the list
     */
    @Override
	public void removeInitializer(final IJCTExpression initializer)
    {
        this.initializers.remove(initializer);
    }
    
    /**
     * Remove the initializer at the index
     */
    @Override
	public void removeInitializer(final int anIndex)
    {
        this.initializers.remove(anIndex);
    }
    
    /**
     * Returns the list of initializers of this new array
     * <em>Part of the enclosed elements.</em>
     */
    @Override
	public List<IJCTExpression> getInitializers()
    {
        return Collections.unmodifiableList(this.initializers);
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.NEW_ARRAY)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.NEW_ARRAY;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitNewArray(this, aP);
    }
    
    @Override
	public IJCTArrayType getTypeResult()
    {
        final StringBuilder str = new StringBuilder();
        final int dims = this.getDimensions().size() + this.getUnspecifiedDimensions();
        for(int i = 0; i < dims; ++i)
            str.append(Constants.ARRAY_MARKER);
        return this.getRootNode().getType(str.toString() + this.getElementType().getTypeName(),
                                          IJCTArrayType.class);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        if(null != this.getElementType())
        {
            aWriter.append("new ");
            this.getElementType().getSourceCode(aWriter);
        }
    
        for(final IJCTExpression d : this.getDimensions())
        {
            aWriter.append('[');
            d.getSourceCode(aWriter).append(']');
        }
    
        for(int i = 0; i < this.getUnspecifiedDimensions(); ++i)
            aWriter.append("[]");
    
        if(this.getInitializers().size() > 0)
        {
            aWriter.append(" { ");
            final Iterator<IJCTExpression> it = this.getInitializers().iterator();
            while(it.hasNext())
            {
                it.next().getSourceCode(aWriter);
                if(it.hasNext())
                    aWriter.append(", ");
            }
            aWriter.append(" }");
        }
    
        return aWriter;
    }

}
