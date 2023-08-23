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



import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTUnaryOperator;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents an unary operator
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTUnaryOperator}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTUnaryOperator extends JCTSourceCodePart<IJCTExpression> implements IJCTUnaryOperator
{
    /**
     * operand of this unary operator
     */
    private final NotNullableReference<IJCTExpression> operand;
    
    
    JCTUnaryOperator(final IJCTRootNode aRootNode, final IJCTExpression operand)
    {
        super(aRootNode);
        this.operand = this.createInternalReference(operand);
        super.backpatchElements(this.operand);
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return this.getOperand().getTypeResult();
    }
    
    /**
     * Returns the operator
     */
    protected abstract String getOperator();
    
    /**
     * Modifies the operand of this unary operator
     *
     * @param operand the new operand
     */
    @Override
	public void setOperand(final IJCTExpression operand)
    {
        this.operand.set(operand);
    }
    
    /**
     * Returns the operand of this unary operator
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getOperand()
    {
        return this.operand.get();
    }
    

}
