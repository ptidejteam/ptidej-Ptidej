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

import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTEnhancedFor;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents an enhanced for loop
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTEnhancedFor}
 *
 * @author Mathieu Lemoine
 */
class JCTEnhancedFor extends JCTSourceCodePart<IJCTElement> implements IJCTEnhancedFor
{
    /**
     * variable of this enhanced for
     */
    private final NotNullableReference<IJCTVariable> variable;
    
    /**
     * iterable of this enhanced for
     */
    private final NotNullableReference<IJCTExpression> iterable;
    
    /**
     * body of this enhanced for
     */
    private final NotNullableReference<IJCTStatement> body;
    
    
    JCTEnhancedFor(final IJCTRootNode aRootNode, final IJCTVariable variable, final IJCTExpression iterable, final IJCTStatement body)
    {
        super(aRootNode);
        this.variable = this.createInternalReference(variable);
        this.iterable = this.createInternalReference(iterable);
        this.body = this.createInternalReference(body);
        super.backpatchElements(new IndirectCollection<IJCTElement>(this.variable, this.iterable, this.body));
    }
    
    /**
     * Modifies the variable of this enhanced for
     *
     * @param variable the new variable
     */
    @Override
	public void setVariable(final IJCTVariable variable)
    {
        this.variable.set(variable);
    }
    
    /**
     * Returns the variable of this enhanced for
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTVariable getVariable()
    {
        return this.variable.get();
    }
    
    /**
     * Modifies the iterable of this enhanced for
     *
     * @param iterable the new iterable
     */
    @Override
	public void setIterable(final IJCTExpression iterable)
    {
        this.iterable.set(iterable);
    }
    
    /**
     * Returns the iterable of this enhanced for
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getIterable()
    {
        return this.iterable.get();
    }
    
    /**
     * Modifies the body of this enhanced for
     *
     * @param body the new body
     */
    @Override
	public void setBody(final IJCTStatement body)
    {
        this.body.set(body);
    }
    
    /**
     * Returns the body of this enhanced for
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTStatement getBody()
    {
        return this.body.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.ENHANCED_FOR)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.ENHANCED_FOR;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitEnhancedFor(this, aP);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        aWriter.append("for(");
    
        final IJCTVariable v = this.getVariable();
        if(null != v.getType())
            v.getType().getSourceCode(aWriter)
                .append(' ');
    
        aWriter.append(v.getName())
            .append(" : ");
    
        this.getIterable().getSourceCode(aWriter).append(") ");
    
        return this.getBody().getSourceCode(aWriter);
    }

}
