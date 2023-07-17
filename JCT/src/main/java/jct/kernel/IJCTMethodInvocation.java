/**
 * @author Mathieu Lemoine
 * @created 2008-08-20 (水)
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

package jct.kernel;

import java.util.List;

/**
 * This class represents a method invocation
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTMethodInvocation extends IJCTExpression,
		IJCTElementContainer<IJCTExpression> {
	/**
	 * Modifies the method selector of this method invocation
	 * 
	 * @param methodSelector
	 *            the new method selector
	 */
	public void setMethodSelector(final IJCTSelector<IJCTMethod> methodSelector);

	/**
	 * Returns the method selector of this method invocation
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTSelector<IJCTMethod> getMethodSelector();

	/**
	 * Adds a "argument" at the index (or move it there)
	 */
	public void addArgument(final int anIndex, final IJCTExpression argument);

	/**
	 * Adds a "argument" at the end of the list (or move it there)
	 */
	public void addArgument(final IJCTExpression argument);

	/**
	 * Removes this argument from the list
	 */
	public void removeArgument(final IJCTExpression argument);

	/**
	 * Remove the argument at the index
	 */
	public void removeArgument(final int anIndex);

	/**
	 * Returns the list of arguments of this method invocation
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpression> getArguments();

}
