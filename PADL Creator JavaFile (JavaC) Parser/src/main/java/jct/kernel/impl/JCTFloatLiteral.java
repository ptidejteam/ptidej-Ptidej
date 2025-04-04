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
import jct.kernel.IJCTFloatLiteral;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.kernel.JCTPrimitiveTypes;

/**
 * This class represents a float literal
 * 
 * Default implementation for {@link jct.kernel.IJCTFloatLiteral}
 *
 * @author Mathieu Lemoine
 */
class JCTFloatLiteral extends JCTSourceCodePart implements IJCTFloatLiteral {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1804514451275470680L;
	/**
	 * value of this float literal
	 */
	private float value;

	JCTFloatLiteral(final IJCTRootNode aRootNode, final float value) {
		super(aRootNode);
		this.value = value;
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		return aWriter.append(String.valueOf(this.value));
	}

	public IJCTType getTypeResult() {
		return this.getRootNode().getType(JCTPrimitiveTypes.FLOAT);
	}

	/**
	 * Modifies the value of this float literal
	 *
	 * @param value the new value
	 */
	public void setValue(final float value) {
		this.value = value;
	}

	/**
	 * Returns the value of this float literal
	 */
	public float getValue() {
		return this.value;
	}

	/**
	 * Returns the kind of this constituent (JCTKind.FLOAT_LITERAL)
	 */
	public JCTKind getKind() {
		return JCTKind.FLOAT_LITERAL;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitFloatLiteral(this, aP);
	}

}
