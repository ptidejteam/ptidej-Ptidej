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

import jct.kernel.IJCTExpression;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTUnsignedRightShiftAssignment;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * This class represents an unsigned right shift assignment expression
 * 
 * Default implementation for {@link jct.kernel.IJCTUnsignedRightShiftAssignment}
 *
 * @author Mathieu Lemoine
 */
class JCTUnsignedRightShiftAssignment extends JCTAssignment implements
		IJCTUnsignedRightShiftAssignment {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3260283995377649107L;

	JCTUnsignedRightShiftAssignment(
		final IJCTRootNode aRootNode,
		final IJCTExpression variable,
		final IJCTExpression value) {
		super(aRootNode, variable, value);
	}

	@Override
	protected String getCompoundOperator() {
		return ">>>";
	}

	/**
	 * Returns the kind of this constituent (JCTKind.UNSIGNED_RIGHT_SHIFT_ASSIGNMENT)
	 */
	@Override
	public JCTKind getKind() {
		return JCTKind.UNSIGNED_RIGHT_SHIFT_ASSIGNMENT;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	@Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitUnsignedRightShiftAssignment(this, aP);
	}

}
