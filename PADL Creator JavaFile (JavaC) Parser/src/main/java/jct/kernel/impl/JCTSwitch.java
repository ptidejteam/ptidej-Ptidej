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
import java.util.Collections;
import java.util.List;
import jct.kernel.IJCTCase;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSwitch;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;
import jct.util.reference.NullableReference;

/**
 * This class represents a switch block
 * 
 * Default implementation for {@link jct.kernel.IJCTSwitch}
 *
 * @author Mathieu Lemoine
 */
class JCTSwitch extends JCTSourceCodePart<IJCTElement> implements IJCTSwitch {
	/**
	 * expression of this switch
	 */
	private final NotNullableReference<IJCTExpression> expression;

	/**
	 * List of cases of this switch
	 */
	private final List<IJCTCase> cases = this.createInternalList();

	/**
	 * default case of this switch
	 */
	private final NullableReference<IJCTCase> defaultCase =
		this.createNullableInternalReference();

	JCTSwitch(final IJCTRootNode aRootNode, final IJCTExpression expression) {
		super(aRootNode);
		this.expression = this.createInternalReference(expression);
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.expression,
			this.cases,
			this.defaultCase));
	}

	/**
	 * Modifies the expression of this switch
	 *
	 * @param expression the new expression
	 */
	public void setExpression(final IJCTExpression expression) {
		this.expression.set(expression);
	}

	/**
	 * Returns the expression of this switch
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getExpression() {
		return this.expression.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.SWITCH)
	 */
	public JCTKind getKind() {
		return JCTKind.SWITCH;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitSwitch(this, aP);
	}

	public IJCTCase getDefaultCase() {
		return this.defaultCase.get();
	}

	public void setDefaultCase(final IJCTCase c) {
		if (!c.isDefaultCase())
			throw new IllegalArgumentException(
				"Use add/remove/get Case to modify normal cases");

		this.defaultCase.set(c);
	}

	public List<IJCTCase> getCases() {
		return Collections.unmodifiableList(this.cases);
	}

	public void addCase(final int i, final IJCTCase c) {
		if (c.isDefaultCase())
			throw new IllegalArgumentException(
				"Use get/set DefaultCase to modify the default case");

		this.cases.add(i, c);
	}

	public void addCase(final IJCTCase c) {
		this.cases.add(c);
	}

	public void removeCase(final IJCTCase c) {
		this.cases.remove(c);
	}

	public void removeCase(final int i) {
		this.cases.remove(i);
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		w.append("switch(");
		this.getExpression().getSourceCode(w).append(")\n{\n");

		for (final IJCTCase c : this.getCases())
			c.getSourceCode(w);

		if (null != this.getDefaultCase())
			this.getDefaultCase().getSourceCode(w);

		return w.append("}\n");
	}

	private static final long serialVersionUID = 6727922564471740879L;

}
