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
import jct.kernel.IJCTLabel;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSourceCodePart;

/**
 * This class represents a break or a continue statement
 *
 * @author Mathieu Lemoine
 */
abstract class JCTBreakContinue extends JCTSourceCodePart implements
		IJCTSourceCodePart {
	/**
	 * 
	 */
	private static final long serialVersionUID = -917689053854605069L;
	/**
	 * label of this break continue
	 */
	private IJCTLabel label = null;

	JCTBreakContinue(final IJCTRootNode aRootNode) {
		super(aRootNode);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		aWriter.append(this.getKeyword());

		if (null != this.getLabel())
			aWriter.append(' ').append(this.getLabel().getName());

		return aWriter.append(";\n");
	}

	/**
	 * Returns either "break" or "continue"
	 * depending on the actual statement represented by this instance.
	 */
	protected abstract String getKeyword();

	/**
	 * Modifies the label of this break continue
	 *
	 * @param label the new label, can be {@code null}
	 */
	public void setLabel(final IJCTLabel label) {
		this.label = label;
	}

	/**
	 * Returns the label of this break continue
	 *
	 * @return null iff there is no label
	 */
	public IJCTLabel getLabel() {
		return this.label;
	}

}
