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

package jct.kernel.impl;

import java.io.IOException;
import java.io.Writer;
import jct.kernel.IJCTBinaryOperator;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This class represents a binary operator
 * 
 * Default implementation for {@link jct.kernel.IJCTBinaryOperator}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTBinaryOperator extends JCTSourceCodePart<IJCTExpression>
		implements IJCTBinaryOperator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8678367014661119270L;

	/**
	 * left operand of this binary operator
	 */
	private final NotNullableReference<IJCTExpression> leftOperand;

	/**
	 * right operand of this binary operator
	 */
	private final NotNullableReference<IJCTExpression> rightOperand;

	JCTBinaryOperator(
		final IJCTRootNode aRootNode,
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		super(aRootNode);
		this.leftOperand = this.createInternalReference(leftOperand);
		this.rightOperand = this.createInternalReference(rightOperand);
		super.backpatchElements(new IndirectCollection<IJCTExpression>(
			this.leftOperand,
			this.rightOperand));
	}

	/**
	 * Returns the operator
	 */
	protected abstract String getOperator();

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		this.getLeftOperand().getSourceCode(aWriter).append(
			" " + this.getOperator() + " ");
		return this.getRightOperand().getSourceCode(aWriter);
	}

	/**
	 * Modifies the left operand of this binary operator
	 *
	 * @param leftOperand the new left operand
	 */
	public void setLeftOperand(final IJCTExpression leftOperand) {
		this.leftOperand.set(leftOperand);
	}

	/**
	 * Returns the left operand of this binary operator
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getLeftOperand() {
		return this.leftOperand.get();
	}

	/**
	 * Modifies the right operand of this binary operator
	 *
	 * @param rightOperand the new right operand
	 */
	public void setRightOperand(final IJCTExpression rightOperand) {
		this.rightOperand.set(rightOperand);
	}

	/**
	 * Returns the right operand of this binary operator
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getRightOperand() {
		return this.rightOperand.get();
	}

}
