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
import jct.kernel.IJCTStatement;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NullableReference;

/**
 * This class represents a case
 * 
 * Default implementation for {@link jct.kernel.IJCTCase}
 *
 * @author Mathieu Lemoine
 */
class JCTCase extends JCTSourceCodePart<IJCTElement> implements IJCTCase {
	/**
	 * label of this case
	 */
	private final NullableReference<IJCTExpression> label =
		this.createNullableInternalReference();

	/**
	 * List of statements of this case
	 */
	private final List<IJCTStatement> statements = this.createInternalList();

	JCTCase(final IJCTRootNode aRootNode) {
		super(aRootNode);
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.label,
			this.statements));
	}

	/**
	 * Returns wheter this case is a default or not.
	 */
	public boolean isDefaultCase() {
		return null == this.getLabel();
	}

	/**
	 * Modifies the label of this case
	 *
	 * @param label the new label, can be {@code null}
	 */
	public void setLabel(final IJCTExpression label) {
		this.label.set(label);
	}

	/**
	 * Returns the label of this case
	 * <em>Included in the enclosed elements.</em>
	 *
	 * @return null iff there is no label
	 */
	public IJCTExpression getLabel() {
		return this.label.get();
	}

	/**
	 * Adds a "statement" at the index (or move it there)
	 */
	public void addStatement(final int anIndex, final IJCTStatement aStatement) {
		this.statements.add(anIndex, aStatement);
	}

	/**
	 * Adds a "statement" at the end of the list (or move it there)
	 */
	public void addStatement(final IJCTStatement aStatement) {
		this.statements.add(aStatement);
	}

	/**
	 * Removes this statement from the list
	 */
	public void removeStatement(final IJCTStatement aStatement) {
		this.statements.remove(aStatement);
	}

	/**
	 * Remove the statement at the index
	 */
	public void removeStatement(final int anIndex) {
		this.statements.remove(anIndex);
	}

	/**
	 * Returns the list of statements of this case
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTStatement> getStatements() {
		return Collections.unmodifiableList(this.statements);
	}

	/**
	 * Returns the kind of this constituent (JCTKind.CASE)
	 */
	public JCTKind getKind() {
		return JCTKind.CASE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitCase(this, aP);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		if (!this.isDefaultCase())
			this.getLabel().getSourceCode(aWriter.append("case "));
		else
			aWriter.append("default");

		aWriter.append(":\n");

		for (final IJCTStatement s : this.getStatements())
			s.getSourceCode(aWriter);

		return aWriter;
	}

	private static final long serialVersionUID = 2954700554419386991L;

}
