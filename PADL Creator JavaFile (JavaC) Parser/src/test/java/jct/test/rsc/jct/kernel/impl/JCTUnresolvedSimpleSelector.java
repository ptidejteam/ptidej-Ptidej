/**
 * @author Mathieu Lemoine
 * @created 2008-10-29 (水)
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

import jct.test.rsc.jct.kernel.IJCTIdentifiable;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSimpleSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;


/**
 * Class representing an unresolved simple selector.
 *
 * @author Mathieu Lemoine
 */
class JCTUnresolvedSimpleSelector<Identifiable extends IJCTIdentifiable> extends JCTSourceCodePart<Identifiable> implements IJCTSimpleSelector<Identifiable>
{
    /**
     * identifier of this unresolved simple selector
     */
    private final String identifier;
    
    
    JCTUnresolvedSimpleSelector(final IJCTRootNode aRootNode, final String identifier)
    {
        super(aRootNode);
        this.identifier = identifier;
    }
    
    @Override
	public Identifiable getElement()
    {
        return null;
    }
    
    @Override
	public void setElement(final Identifiable anIdentifiable)
    {
        throw new UnsupportedOperationException("to resolve an unresolved selector, you must use the method resolveSelector(IJCTSelector).");
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        return aWriter.append(this.identifier);
    }
    
    @Override
	public JCTKind getKind()
    {
        return JCTKind.ERRONEOUS_SELECTOR;
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return this.getSourceCode();
    }
    
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> v, final P p)
    {
        return this.getEnclosingElement().accept(v, p);
    }
    
    /**
     * Returns the identifier of this unresolved simple selector
     */
    public String getIdentifier()
    {
        return this.identifier;
    }
    

}
