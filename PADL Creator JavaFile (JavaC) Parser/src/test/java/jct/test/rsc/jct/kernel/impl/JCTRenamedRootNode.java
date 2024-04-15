/**
 * @author Mathieu Lemoine
 * @created 2008-10-15 (水)
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
import java.lang.ref.SoftReference;

import java.util.Collection;
import java.util.Set;
import java.util.List;

import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.IJCTContainer;



/**
 * Implementation of a renamed {@link jct.test.rsc.jct.kernel.IJCTRootNode}
 * 
 * Implemented as a Decorator.
 *
 * @author Mathieu Lemoine
 */
class JCTRenamedRootNode implements IJCTRootNode, IJCTContainer<IJCTPackage>
{
    /**
     * original of this renamed root node
     */
    private final IJCTRootNode original;
    
    /**
     * name of this renamed root node
     */
    private final String name;
    
    /**
     * factory of this renamed root node
     */
    private SoftReference<IJCTFactory> factory;
    
    
    JCTRenamedRootNode(final IJCTRootNode originalRootNode, final String name)
    {
        this.original = originalRootNode.getRootNode();
        this.name = name;
        this.factory = new SoftReference<IJCTFactory>(null);
    }
    
    /**
     * Returns the Factory associated to this JCT
     */
    @Override
	public IJCTFactory getFactory()
    {
        IJCTFactory f = this.factory.get();
        
        if(null == f)
            this.factory = new SoftReference<IJCTFactory>(f = new JCTFactory(this));
        return f;
    }
    
    /**
     * Returns the name of the JCT.
     * This data is purely informative
     */
    @Override
	public String getName()
    {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o)
    {
        return (o instanceof IJCTRootNode)
               && ((IJCTRootNode)o).getRootNode().equals(this.getRootNode());
    }
    
    @Override
	public IJCTPrimitiveType getType(final JCTPrimitiveTypes aPrimitiveTypeConstant)
    {
        return this.original.getType(aPrimitiveTypeConstant);
    }
    
    @Override
	public IJCTType getType(final IJCTType... types)
    {
        return this.original.getType(types);
    }
    
    @Override
	public <T extends IJCTType> T getType(final String path, final Class<T> typeClass)
    {
        return this.original.getType(path, typeClass);
    }
    
    @Override
	public IJCTElement getEnclosingElement()
    {
        return this.original.getEnclosingElement();
    }
    
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> aVisitor, final P p)
    {
        return this.original.accept(aVisitor, p);
    }
    
    @Override
	public JCTKind getKind()
    {
        return this.original.getKind();
    }
    
    @Override
	public IJCTRootNode getRootNode()
    {
        return this.original.getRootNode();
    }
    
    @Override
	public boolean isInitialized()
    {
        return this.original.isInitialized();
    }
    
    @Override
	public void assumeInitialized()
    {
        this.original.assumeInitialized();
    }
    
    @Override
	public void addPackage(final IJCTPackage aPackage)
    {
        this.original.addPackage(aPackage);
    }
    
    @Override
	public void removePackage(final IJCTPackage aPackage)
    {
        this.original.removePackage(aPackage);
    }
    
    @Override
	public Set<IJCTPackage> getPackages()
    {
        return this.original.getPackages();
    }
    
    @Override
	public void addOrphan(final int anIndex, final IJCTElement orphan)
    {
        this.original.addOrphan(anIndex, orphan);
    }
    
    @Override
	public void addOrphan(final IJCTElement orphan)
    {
        this.original.addOrphan(orphan);
    }
    
    @Override
	public void removeOrphan(final IJCTElement orphan)
    {
        this.original.removeOrphan(orphan);
    }
    
    @Override
	public void removeOrphan(final int anIndex)
    {
        this.original.removeOrphan(anIndex);
    }
    
    @Override
	public List<IJCTElement> getOrphans()
    {
        return this.original.getOrphans();
    }
    
    @Override
	public IJCTPath getPath()
    {
        return this.original.getPath();
    }
    
    @Override
	public String getID()
    {
        return this.original.getID();
    }
    
    @Override
	public IJCTElement walk(final IJCTPath aPath)
    {
        return this.original.walk(aPath);
    }
    
    @Override
    public int hashCode()
    {
        return this.original.hashCode();
    }
    
    @Override
	public String getSourceCode()
    {
        return this.original.getSourceCode();
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        return this.original.getSourceCode(aWriter);
    }
    
    @Override
	public IJCTArrayType registerArrayType(final IJCTType underlyingType)
    {
        return this.original.registerArrayType(underlyingType);
    }
    
    @Override
	public IJCTArrayType registerArrayType(final IJCTType underlyingType, final String underlyingTypeName)
    {
        return this.original.registerArrayType(underlyingType, underlyingTypeName);
    }
    
    @Override
	public Collection<IJCTPackage> getEnclosedElements()
    {
        return ((IJCTContainer)this.original).getEnclosedElements();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public <T extends IJCTPackage> Collection<T> getEnclosedElements(final Class<T> elementType)
    {
        return ((IJCTContainer)this.original).getEnclosedElements(elementType);
    }
    
    @Override
	public Collection<? extends IJCTPackage> getAllEnclosedElements()
    {
        return ((IJCTContainer)this.original).getAllEnclosedElements();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public <T extends IJCTElement> Collection<T> getAllEnclosedElements(final JCTKind aKind, final Class<T> elementType, final boolean first_layer_only)
    {
        return ((IJCTContainer)this.original).getAllEnclosedElements(aKind, elementType, first_layer_only);
    }
    

}
