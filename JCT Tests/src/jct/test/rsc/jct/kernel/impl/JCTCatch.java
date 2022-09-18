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

import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTCatch;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents a catch block
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTCatch}
 *
 * @author Mathieu Lemoine
 */
class JCTCatch extends JCTSourceCodePart<IJCTStatement> implements IJCTCatch
{
    /**
     * variable of this catch
     */
    private final NotNullableReference<IJCTVariable> variable;
    
    /**
     * body of this catch
     */
    private final NotNullableReference<IJCTBlock> body;
    
    
    JCTCatch(final IJCTRootNode aRootNode, final IJCTVariable variable)
    {
        super(aRootNode);
        this.body = this.createInternalReference(this.getRootNode().getFactory().createBlock());
        this.variable = this.createInternalReference(variable);
        super.backpatchElements(new IndirectCollection<IJCTStatement>(this.variable, this.body));
    }
    
    /**
     * Modifies the variable of this catch
     *
     * @param variable the new variable
     */
    @Override
	public void setVariable(final IJCTVariable variable)
    {
        this.variable.set(variable);
    }
    
    /**
     * Returns the variable of this catch
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTVariable getVariable()
    {
        return this.variable.get();
    }
    
    /**
     * Modifies the body of this catch
     *
     * @param body the new body
     */
    @Override
	public void setBody(final IJCTBlock body)
    {
        this.body.set(body);
    }
    
    /**
     * Returns the body of this catch
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTBlock getBody()
    {
        return this.body.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.CATCH)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.CATCH;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitCatch(this, aP);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        aWriter.append("catch(");
    
        final IJCTVariable v = this.getVariable();
        if(null != v.getType())
            v.getType().getSourceCode(aWriter)
                .append(' ');
    
        aWriter.append(v.getName())
            .append(") ");
    
        return this.getBody().getSourceCode(aWriter);
    }

}
