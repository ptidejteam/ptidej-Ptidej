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
import jct.kernel.IJCTAssert;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;
import jct.util.reference.NullableReference;

/**
 * This class reprensents an assert statement
 * 
 * Default implementation for {@link jct.kernel.IJCTAssert}
 *
 * @author Mathieu Lemoine
 */
class JCTAssert extends JCTSourceCodePart<IJCTExpression> implements IJCTAssert {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4034767218710480369L;

	/**
	 * condition of this assert
	 */
	private final NotNullableReference<IJCTExpression> condition;

	/**
	 * detail of this assert
	 */
	private final NullableReference<IJCTExpression> detail =
		this.createNullableInternalReference();

	JCTAssert(final IJCTRootNode aRootNode, final IJCTExpression condition) {
		super(aRootNode);
		this.condition = this.createInternalReference(condition);
		super.backpatchElements(new IndirectCollection<IJCTExpression>(
			this.condition,
			this.detail));
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("assert ");
		this.getCondition().getSourceCode(aWriter);

		if (null != this.getDetail())
			this.getDetail().getSourceCode(aWriter);

		return aWriter.append(";\n");
	}

	/**
	 * Modifies the condition of this assert
	 *
	 * @param condition the new condition
	 */
	public void setCondition(final IJCTExpression condition) {
		this.condition.set(condition);
	}

	/**
	 * Returns the condition of this assert
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getCondition() {
		return this.condition.get();
	}

	/**
	 * Modifies the detail of this assert
	 *
	 * @param detail the new detail, can be {@code null}
	 */
	public void setDetail(final IJCTExpression detail) {
		this.detail.set(detail);
	}

	/**
	 * Returns the detail of this assert
	 * <em>Included in the enclosed elements.</em>
	 *
	 * @return null iff there is no detail
	 */
	public IJCTExpression getDetail() {
		return this.detail.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.ASSERT)
	 */
	public JCTKind getKind() {
		return JCTKind.ASSERT;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitAssert(this, aP);
	}

}
