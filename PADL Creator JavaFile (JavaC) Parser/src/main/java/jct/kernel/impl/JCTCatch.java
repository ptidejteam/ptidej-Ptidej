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
import jct.kernel.IJCTCatch;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTStatement;
import jct.kernel.IJCTVariable;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This class represents a catch block
 * 
 * Default implementation for {@link jct.kernel.IJCTCatch}
 *
 * @author Mathieu Lemoine
 */
class JCTCatch extends JCTSourceCodePart<IJCTStatement> implements IJCTCatch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5667396777004805046L;

	/**
	 * variable of this catch
	 */
	private final NotNullableReference<IJCTVariable> variable;

	/**
	 * body of this catch
	 */
	private final NotNullableReference<IJCTBlock> body;

	JCTCatch(final IJCTRootNode aRootNode, final IJCTVariable variable) {
		super(aRootNode);
		this.body =
			this.createInternalReference(this
				.getRootNode()
				.getFactory()
				.createBlock());
		this.variable = this.createInternalReference(variable);
		super.backpatchElements(new IndirectCollection<IJCTStatement>(
			this.variable,
			this.body));
	}

	/**
	 * Modifies the variable of this catch
	 *
	 * @param variable the new variable
	 */
	public void setVariable(final IJCTVariable variable) {
		this.variable.set(variable);
	}

	/**
	 * Returns the variable of this catch
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTVariable getVariable() {
		return this.variable.get();
	}

	/**
	 * Modifies the body of this catch
	 *
	 * @param body the new body
	 */
	public void setBody(final IJCTBlock body) {
		this.body.set(body);
	}

	/**
	 * Returns the body of this catch
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTBlock getBody() {
		return this.body.get();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.CATCH)
	 */
	public JCTKind getKind() {
		return JCTKind.CATCH;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitCatch(this, aP);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append("catch(");

		final IJCTVariable v = this.getVariable();
		if (null != v.getType())
			v.getType().getSourceCode(aWriter).append(' ');

		aWriter.append(v.getName()).append(") ");

		return this.getBody().getSourceCode(aWriter);
	}

}
