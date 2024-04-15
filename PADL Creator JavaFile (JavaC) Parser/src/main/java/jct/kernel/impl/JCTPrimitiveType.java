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
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.kernel.JCTPrimitiveTypes;

/**
 * This class represents a primitive type
 * 
 * Default implementation for {@link jct.kernel.IJCTPrimitiveType}
 *
 * @author Mathieu Lemoine
 */
class JCTPrimitiveType extends JCTType implements IJCTPrimitiveType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8913932699983149121L;
	/**
	 * type of this primitive type
	 */
	private final JCTPrimitiveTypes type;

	JCTPrimitiveType(
		final IJCTRootNode aRootNode,
		final JCTPrimitiveTypes aPrimitiveTypeConstant) {
		super(aRootNode);
		this.type = aPrimitiveTypeConstant;
	}

	public String getTypeName() {
		return this.getType().getTypeName();
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		return aWriter.append(this.getType().getSourceCode());
	}

	/**
	 * Returns the type of this primitive type
	 */
	public JCTPrimitiveTypes getType() {
		return this.type;
	}

	/**
	 * Returns the kind of this constituent (JCTKind.PRIMITIVE_TYPE)
	 */
	public JCTKind getKind() {
		return JCTKind.PRIMITIVE_TYPE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitPrimitiveType(this, aP);
	}

}
