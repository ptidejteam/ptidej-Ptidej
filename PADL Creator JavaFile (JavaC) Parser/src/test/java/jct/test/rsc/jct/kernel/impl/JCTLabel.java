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


import jct.test.rsc.jct.kernel.IJCTLabel;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents a labeled statement
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTLabel}
 *
 * @author Mathieu Lemoine
 */
class JCTLabel extends JCTSourceCodePart<IJCTStatement> implements IJCTLabel
{
    /**
     * statement of this label
     */
    private final NotNullableReference<IJCTStatement> statement;
    
    
    JCTLabel(final IJCTRootNode aRootNode, final String name, final IJCTStatement statement)
    {
        super(aRootNode, name);
        this.statement = this.createInternalReference(statement);
        super.backpatchElements(this.statement);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        aWriter.append(this.getName()).append(": ");
        return this.getStatement().getSourceCode(aWriter);
    }
    
    /**
     * Modifies the statement of this label
     *
     * @param statement the new statement
     */
    @Override
	public void setStatement(final IJCTStatement statement)
    {
        this.statement.set(statement);
    }
    
    /**
     * Returns the statement of this label
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTStatement getStatement()
    {
        return this.statement.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.LABEL)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.LABEL;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitLabel(this, aP);
    }
    

}
