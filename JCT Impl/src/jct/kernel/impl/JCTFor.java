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
import java.util.Iterator;
import java.util.List;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTExpressionStatement;
import jct.kernel.IJCTFor;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTStatement;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This class represents a for loop
 * 
 * Default implementation for {@link jct.kernel.IJCTFor}
 *
 * @author Mathieu Lemoine
 */
class JCTFor extends JCTSourceCodePart<IJCTElement> implements IJCTFor {
	/**
	 * List of initializers of this for
	 */
	private final List<IJCTStatement> initializers = this.createInternalList();

	/**
	 * condition of this for
	 */
	private final NotNullableReference<IJCTExpression> condition;

	/**
	 * List of updaters of this for
	 */
	private final List<IJCTExpressionStatement> updaters =
		this.createInternalList();

	/**
	 * body of this for
	 */
	private final NotNullableReference<IJCTStatement> body;

	JCTFor(
		final IJCTRootNode aRootNode,
		final IJCTExpression condition,
		final IJCTStatement body) {
		super(aRootNode);
		this.body = this.createInternalReference(body);
		this.condition = this.createInternalReference(condition);
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.initializers,
			this.condition,
			this.updaters,
			this.body));
	}

	/**
	 * Adds a "initializer" at the index (or move it there)
	 */
	public void addInitializer(
		final int anIndex,
		final IJCTStatement initializer) {
		this.initializers.add(anIndex, initializer);
	}

	/**
	 * Adds a "initializer" at the end of the list (or move it there)
	 */
	public void addInitializer(final IJCTStatement initializer) {
		this.initializers.add(initializer);
	}

	/**
	 * Removes this initializer from the list
	 */
	public void removeInitializer(final IJCTStatement initializer) {
		this.initializers.remove(initializer);
	}

	/**
	 * Remove the initializer at the index
	 */
	public void removeInitializer(final int anIndex) {
		this.initializers.remove(anIndex);
	}

	/**
	 * Returns the list of initializers of this for
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTStatement> getInitializers() {
		return Collections.unmodifiableList(this.initializers);
	}

	/**
	 * Modifies the condition of this for
	 *
	 * @param condition the new condition
	 */
	public void setCondition(final IJCTExpression condition) {
		this.condition.set(condition);
	}

	/**
	 * Returns the condition of this for
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getCondition() {
		return this.condition.get();
	}

	/**
	 * Adds a "updater" at the index (or move it there)
	 */
	public void addUpdater(
		final int anIndex,
		final IJCTExpressionStatement updater) {
		this.updaters.add(anIndex, updater);
	}

	/**
	 * Adds a "updater" at the end of the list (or move it there)
	 */
	public void addUpdater(final IJCTExpressionStatement updater) {
		this.updaters.add(updater);
	}

	/**
	 * Removes this updater from the list
	 */
	public void removeUpdater(final IJCTExpressionStatement updater) {
		this.updaters.remove(updater);
	}

	/**
	 * Remove the updater at the index
	 */
	public void removeUpdater(final int anIndex) {
		this.updaters.remove(anIndex);
	}

	/**
	 * Returns the list of updaters of this for
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpressionStatement> getUpdaters() {
		return Collections.unmodifiableList(this.updaters);
	}

	/**
	 * Modifies the body of this for
	 *
	 * @param body the new body
	 */
	public void setBody(final IJCTStatement body) {
		this.body.set(body);
	}

	/**
	 * Returns the body of this for
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTStatement getBody() {
		return this.body.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.FOR)
	 */
	public JCTKind getKind() {
		return JCTKind.FOR;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitFor(this, aP);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("for(");

		final Iterator<IJCTStatement> sit = this.getInitializers().iterator();
		while (sit.hasNext()) {
			final String var = sit.next().getSourceCode();
			aWriter.append(var.substring(0, var.length() - 2));
			if (sit.hasNext())
				aWriter.append(", ");
		}

		aWriter.append("; ");
		this.getCondition().getSourceCode(aWriter).append("; ");

		final Iterator<IJCTExpressionStatement> eit =
			this.getUpdaters().iterator();
		while (eit.hasNext()) {
			final String update = eit.next().getSourceCode();
			aWriter.append(update.substring(0, update.length() - 2));
			if (eit.hasNext())
				aWriter.append(", ");
		}

		aWriter.append(") ");

		return this.getBody().getSourceCode(aWriter);
	}

	private static final long serialVersionUID = -5853852778113348131L;

}
