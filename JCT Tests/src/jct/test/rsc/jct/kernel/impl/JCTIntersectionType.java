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

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.List;

import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;


/**
 * This class represents an intersection of types
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTIntersectionType}
 *
 * @author Mathieu Lemoine
 */
class JCTIntersectionType extends JCTType implements IJCTIntersectionType
{
    /**
     * Set of types of this intersection type
     */
    private final Set<IJCTType> types;
    
    
    JCTIntersectionType(final IJCTRootNode aRootNode, final IJCTType... types)
    {
        super(aRootNode);
        if(types.length <= 1)
            throw new IllegalArgumentException("To create the intersection of 0 types, use JCTPrimitiveTypes.VOID, and to create the intersection type of a sole type, use the type itself.");
        this.types = new HashSet<IJCTType>();
        
        final List<IJCTType> lTypes = Arrays.asList(types);
        final ListIterator<IJCTType> it = lTypes.listIterator();
        while(it.hasNext())
        {
            final IJCTType type = it.next();
            if(type instanceof IJCTIntersectionType)
            {
                it.remove();
                for(final IJCTType t : ((IJCTIntersectionType)type).getTypes())
                {
                    it.add(t);
                    it.previous();
                }
            }
        }
    }
    
    /**
     * Returns the set of types composing this intersection types
     */
    @Override
	public Set<IJCTType> getTypes()
    {
        return Collections.unmodifiableSet(this.types);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        throw new IllegalStateException("An Intersection Type should never appear in source code.");
    }
    
    @Override
	public String getTypeName()
    {
        final StringBuilder typeName = new StringBuilder(Constants.INTERSECTION_MARKER);
        for(final IJCTType type : this.getTypes())
            typeName.append(type.getTypeName()).append(Constants.INTERSECTION_SEPARATOR);
        
        return typeName.toString();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.INTERSECTION_TYPE)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.INTERSECTION_TYPE;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitIntersectionType(this, aP);
    }
    

}
