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
import jct.kernel.IJCTAssignment;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This class represents a simple assignment expression
 * 
 * Default implementation for {@link jct.kernel.IJCTAssignment}
 *
 * @author Mathieu Lemoine
 */
class JCTAssignment extends JCTSourceCodePart<IJCTExpression> implements
		IJCTAssignment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5110451116994577846L;

	/**
	 * variable of this assignment
	 */
	private final NotNullableReference<IJCTExpression> variable;

	/**
	 * value of this assignment
	 */
	private final NotNullableReference<IJCTExpression> value;

	JCTAssignment(
		final IJCTRootNode aRootNode,
		final IJCTExpression variable,
		final IJCTExpression value) {
		super(aRootNode);
		this.variable = this.createInternalReference(variable);
		this.value = this.createInternalReference(value);
		super.backpatchElements(new IndirectCollection<IJCTExpression>(
			this.variable,
			this.value));
	}

	/**
	 * Returns the operator to compose with the "=" operator.
	 */
	protected String getCompoundOperator() {
		return "";
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		this.getVariable().getSourceCode(aWriter).append(
			' ' + this.getCompoundOperator() + "= ");
		return this.getValue().getSourceCode(aWriter);
	}

	public IJCTType getTypeResult() {
		return this.getVariable().getTypeResult();
	}

	/**
	 * Modifies the variable of this assignment
	 *
	 * @param variable the new variable
	 */
	public void setVariable(final IJCTExpression variable) {
		this.variable.set(variable);
	}

	/**
	 * Returns the variable of this assignment
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getVariable() {
		return this.variable.get();
	}

	/**
	 * Modifies the value of this assignment
	 *
	 * @param value the new value
	 */
	public void setValue(final IJCTExpression value) {
		this.value.set(value);
	}

	/**
	 * Returns the value of this assignment
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getValue() {
		return this.value.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.ASSIGNMENT)
	 */
	public JCTKind getKind() {
		return JCTKind.ASSIGNMENT;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitAssignment(this, aP);
	}

}
