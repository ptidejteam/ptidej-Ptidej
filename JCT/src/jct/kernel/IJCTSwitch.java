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

import java.util.List;

/**
 * This class represents a switch block
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTSwitch extends IJCTStatement,
		IJCTElementContainer<IJCTElement> {
	/**
	 * Modifies the expression of this switch
	 * 
	 * @param expression
	 *            the new expression
	 */
	public void setExpression(final IJCTExpression expression);

	/**
	 * Returns the expression of this switch
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getExpression();

	/**
	 * Adds a "case" at the index (or move it there)
	 */
	public void addCase(final int anIndex, final IJCTCase aCase);

	/**
	 * Adds a "case" at the end of the list (or move it there)
	 */
	public void addCase(final IJCTCase aCase);

	/**
	 * Removes this case from the list
	 */
	public void removeCase(final IJCTCase aCase);

	/**
	 * Remove the case at the index
	 */
	public void removeCase(final int anIndex);

	/**
	 * Returns the list of cases of this switch
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTCase> getCases();

	/**
	 * Modifies the default case of this switch
	 * 
	 * @param defaultCase
	 *            the new default case, can be {@code null}
	 */
	public void setDefaultCase(final IJCTCase defaultCase);

	/**
	 * Returns the default case of this switch
	 * <em>Included in the enclosed elements.</em>
	 * 
	 * @return null iff there is no default case
	 */
	public IJCTCase getDefaultCase();

}
