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
import jct.kernel.IJCTBlock;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSynchronized;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This class represents a synchronized statement
 * 
 * Default implementation for {@link jct.kernel.IJCTSynchronized}
 *
 * @author Mathieu Lemoine
 */
class JCTSynchronized extends JCTSourceCodePart<IJCTElement> implements
		IJCTSynchronized {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6542025261699559008L;

	/**
	 * synchronized object of this synchronized
	 */
	private final NotNullableReference<IJCTExpression> synchronizedObject;

	/**
	 * body of this synchronized
	 */
	private final NotNullableReference<IJCTBlock> body;

	JCTSynchronized(
		final IJCTRootNode aRootNode,
		final IJCTExpression synchronizedObject) {
		super(aRootNode);
		this.synchronizedObject =
			this.createInternalReference(synchronizedObject);
		this.body =
			this.createInternalReference(this
				.getRootNode()
				.getFactory()
				.createBlock());
		super.backpatchElements(new IndirectCollection<IJCTElement>(
			this.synchronizedObject,
			this.body));
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("synchronized(");
		this.getSynchronizedObject().getSourceCode(aWriter).append(')');
		return this.getBody().getSourceCode(aWriter);
	}

	/**
	 * Modifies the synchronized object of this synchronized
	 *
	 * @param synchronizedObject the new synchronized object
	 */
	public void setSynchronizedObject(final IJCTExpression synchronizedObject) {
		this.synchronizedObject.set(synchronizedObject);
	}

	/**
	 * Returns the synchronized object of this synchronized
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getSynchronizedObject() {
		return this.synchronizedObject.get();
	}

	/**
	 * Modifies the body of this synchronized
	 *
	 * @param body the new body
	 */
	public void setBody(final IJCTBlock body) {
		this.body.set(body);
	}

	/**
	 * Returns the body of this synchronized
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTBlock getBody() {
		return this.body.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.SYNCHRONIZED)
	 */
	public JCTKind getKind() {
		return JCTKind.SYNCHRONIZED;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitSynchronized(this, aP);
	}

}
