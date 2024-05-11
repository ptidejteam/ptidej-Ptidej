/**
 * @author Mathieu Lemoine
 * @created 2008-08-18 (月)
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
import java.util.Collections;
import java.util.List;
import jct.kernel.IJCTErroneousExpression;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * Interface representing an erroneous expression
 * 
 * Default implementation for {@link jct.kernel.IJCTErroneousExpression}
 *
 * @author Mathieu Lemoine
 */
class JCTErroneousExpression extends JCTSourceCodePart<IJCTExpression>
		implements IJCTErroneousExpression {
	/**
	 * 
	 */
	private static final long serialVersionUID = 638165398186271724L;
	/**
	 * List of expressions of this erroneous expression
	 */
	private final List<IJCTExpression> expressions = this.createInternalList();

	JCTErroneousExpression(final IJCTRootNode aRootNode) {
		super(aRootNode);
	}

	public IJCTType getTypeResult() {
		return null;
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		for (final IJCTExpression expression : this.getEnclosedElements())
			expression.getSourceCode(aWriter);

		return aWriter;
	}

	/**
	 * Adds a "expression" at the index (or move it there)
	 */
	public void addExpression(
		final int anIndex,
		final IJCTExpression aExpression) {
		this.expressions.add(anIndex, aExpression);
	}

	/**
	 * Adds a "expression" at the end of the list (or move it there)
	 */
	public void addExpression(final IJCTExpression aExpression) {
		this.expressions.add(aExpression);
	}

	/**
	 * Removes this expression from the list
	 */
	public void removeExpression(final IJCTExpression aExpression) {
		this.expressions.remove(aExpression);
	}

	/**
	 * Remove the expression at the index
	 */
	public void removeExpression(final int anIndex) {
		this.expressions.remove(anIndex);
	}

	/**
	 * Returns the list of expressions of this erroneous expression
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpression> getExpressions() {
		return Collections.unmodifiableList(this.expressions);
	}

	/**
	 * Returns the kind of this constituent (JCTKind.ERRONEOUS_EXPRESSION)
	 */
	public JCTKind getKind() {
		return JCTKind.ERRONEOUS_EXPRESSION;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitErroneousExpression(this, aP);
	}

}
