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


import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTInstanceOf;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents an instance of expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTInstanceOf}
 *
 * @author Mathieu Lemoine
 */
class JCTInstanceOf extends JCTSourceCodePart<IJCTExpression> implements IJCTInstanceOf
{
    /**
     * operand of this instance of
     */
    private final NotNullableReference<IJCTExpression> operand;
    
    /**
     * type of this instance of
     */
    private IJCTType type;
    
    
    JCTInstanceOf(final IJCTRootNode aRootNode, final IJCTExpression operand, final IJCTType type)
    {
        super(aRootNode);
        this.operand = this.createInternalReference(operand);
        super.backpatchElements(this.operand);
        this.setType(type);
    }
    
    @Override
	public IJCTType getType()
    {
        return this.type;
    }
    
    @Override
	public void setType(final IJCTType aType)
    {
        if(JCTKind.PRIMITIVE_TYPE == aType.getKind())
            throw new IllegalArgumentException("instanceof can be used only with an array type or a class type");
        
        this.type = aType;
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return this.getRootNode().getType(JCTPrimitiveTypes.BOOLEAN);
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        this.getOperand().getSourceCode(aWriter).append(" instanceof ");
        return this.getType().getSourceCode(aWriter);
    }
    
    /**
     * Modifies the operand of this instance of
     *
     * @param operand the new operand
     */
    @Override
	public void setOperand(final IJCTExpression operand)
    {
        this.operand.set(operand);
    }
    
    /**
     * Returns the operand of this instance of
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getOperand()
    {
        return this.operand.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.INSTANCE_OF)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.INSTANCE_OF;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitInstanceOf(this, aP);
    }
    

}
