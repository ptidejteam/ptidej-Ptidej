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


import jct.test.rsc.jct.kernel.IJCTCast;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents a cast expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTCast}
 *
 * @author Mathieu Lemoine
 */
class JCTCast extends JCTSourceCodePart<IJCTExpression> implements IJCTCast
{
    /**
     * operand of this cast
     */
    private final NotNullableReference<IJCTExpression> operand;
    
    /**
     * type of this cast
     */
    private IJCTType type;
    
    
    JCTCast(final IJCTRootNode aRootNode, final IJCTType type, final IJCTExpression operand)
    {
        super(aRootNode);
        this.operand = this.createInternalReference(operand);
        super.backpatchElements(this.operand);
        this.setType(type);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        aWriter.append("(");
        this.getType().getSourceCode(aWriter).append(")");
        return this.getOperand().getSourceCode(aWriter);
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return this.type;
    }
    
    /**
     * Modifies the operand of this cast
     *
     * @param operand the new operand
     */
    @Override
	public void setOperand(final IJCTExpression operand)
    {
        this.operand.set(operand);
    }
    
    /**
     * Returns the operand of this cast
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getOperand()
    {
        return this.operand.get();
    }
    
    /**
     * Modifies the type of this cast
     *
     * @param type the new type
     */
    @Override
	public void setType(final IJCTType type)
    {
        this.type = type;
    }
    
    /**
     * Returns the type of this cast
     */
    @Override
	public IJCTType getType()
    {
        return this.type;
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.CAST)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.CAST;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitCast(this, aP);
    }
    

}
