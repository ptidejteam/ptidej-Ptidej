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

package jct.kernel;

/**
 * This class represents an if statement
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTIf extends IJCTStatement,
		IJCTElementContainer<IJCTElement> {
	/**
	 * Modifies the condition of this if
	 * 
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(final IJCTExpression condition);

	/**
	 * Returns the condition of this if
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getCondition();

	/**
	 * Modifies the then statement of this if
	 * 
	 * @param thenStatement
	 *            the new then statement
	 */
	public void setThenStatement(final IJCTStatement thenStatement);

	/**
	 * Returns the then statement of this if
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTStatement getThenStatement();

	/**
	 * Modifies the else statement of this if
	 * 
	 * @param elseStatement
	 *            the new else statement, can be {@code null}
	 */
	public void setElseStatement(final IJCTStatement elseStatement);

	/**
	 * Returns the else statement of this if
	 * <em>Included in the enclosed elements.</em>
	 * 
	 * @return null iff there is no else statement
	 */
	public IJCTStatement getElseStatement();

}
