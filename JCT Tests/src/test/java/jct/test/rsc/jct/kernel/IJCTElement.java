/**
 * @author Mathieu Lemoine
 * @created 2008-08-16 (土)
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

package jct.test.rsc.jct.kernel;


import java.io.Writer;
import java.io.IOException;


/**
 * Basic interface representing an element of a JCT (JavaC bound abstract syntax Tree)
 *
 * @author Mathieu Lemoine
 */
public interface IJCTElement 
{
    /**
     * Returns the kind of the element
     */
    public JCTKind getKind();
    
    /**
     * Accepts a visitor (calls the appropriate visit* Method)
     */
    public <R, P> R accept(final IJCTVisitor<R, P> v, final P p);
    
    /**
     * Returns the enclosing element
     */
    public IJCTElement getEnclosingElement();
    
    /**
     * Returns the RootNode of the model this element is refering to.
     * Invariant: {@code this.getRootNode() == this.getRootNode().getRootNode()}
     */
    public IJCTRootNode getRootNode();
    
    /**
     * Returns a unique ID for this element.
     */
    public String getID();
    
    /**
     * Returns the textual representation of the expression as if it was within the source code
     */
    public String getSourceCode();
    
    /**
     * Writes the textual representation in the stream.
     */
    public Writer getSourceCode(final Writer w) throws IOException;
    
    /**
     * Returns string representation of the path to this constituent
     */
    @Override
	public String toString();
    
    /**
     * Returns the path to this element
     */
    public IJCTPath getPath();
    

}
