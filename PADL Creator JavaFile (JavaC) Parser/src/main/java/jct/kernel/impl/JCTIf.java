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
import jct.kernel.IJCTElement;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTIf;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTStatement;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;
import jct.util.reference.NullableReference;

/**
 * This class represents an if statement
 * 
 * Default implementation for {@link jct.kernel.IJCTIf}
 *
 * @author Mathieu Lemoine
 */
class JCTIf extends JCTSourceCodePart<IJCTElement> implements IJCTIf {
	/**
	 * 
	 */
	private static final long serialVersionUID = -527935361255817904L;

	/**
	 * condition of this if
	 */
	private final NotNullableReference<IJCTExpression> condition;

	/**
	 * then statement of this if
	 */
	private final NotNullableReference<IJCTStatement> thenStatement;

	/**
	 * else statement of this if
	 */
	private final NullableReference<IJCTStatement> elseStatement =
		this.createNullableInternalReference();

	JCTIf(
		final IJCTRootNode aRootNode,
		final IJCTExpression condition,
		final IJCTStatement thenStatement) {
		super(aRootNode);
		this.condition = this.createInternalReference(condition);
		this.thenStatement = this.createInternalReference(thenStatement);
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.condition,
			this.thenStatement,
			this.elseStatement));
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("if(");
		this.getCondition().getSourceCode(aWriter).append(") ");
		this.getThenStatement().getSourceCode(aWriter);

		if (null != this.getElseStatement())
			this.getElseStatement().getSourceCode(aWriter.append(" else "));

		return aWriter;
	}

	/**
	 * Modifies the condition of this if
	 *
	 * @param condition the new condition
	 */
	public void setCondition(final IJCTExpression condition) {
		this.condition.set(condition);
	}

	/**
	 * Returns the condition of this if
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getCondition() {
		return this.condition.get();
	}

	/**
	 * Modifies the then statement of this if
	 *
	 * @param thenStatement the new then statement
	 */
	public void setThenStatement(final IJCTStatement thenStatement) {
		this.thenStatement.set(thenStatement);
	}

	/**
	 * Returns the then statement of this if
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTStatement getThenStatement() {
		return this.thenStatement.get();
	}

	/**
	 * Modifies the else statement of this if
	 *
	 * @param elseStatement the new else statement, can be {@code null}
	 */
	public void setElseStatement(final IJCTStatement elseStatement) {
		this.elseStatement.set(elseStatement);
	}

	/**
	 * Returns the else statement of this if
	 * <em>Included in the enclosed elements.</em>
	 *
	 * @return null iff there is no else statement
	 */
	public IJCTStatement getElseStatement() {
		return this.elseStatement.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.IF)
	 */
	public JCTKind getKind() {
		return JCTKind.IF;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitIf(this, aP);
	}

}
