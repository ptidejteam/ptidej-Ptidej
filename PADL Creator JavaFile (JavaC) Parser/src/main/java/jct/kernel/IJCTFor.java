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
 * This class represents a for loop
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTFor extends IJCTStatement,
		IJCTElementContainer<IJCTElement> {
	/**
	 * Adds a "initializer" at the index (or move it there)
	 */
	public void addInitializer(
		final int anIndex,
		final IJCTStatement initializer);

	/**
	 * Adds a "initializer" at the end of the list (or move it there)
	 */
	public void addInitializer(final IJCTStatement initializer);

	/**
	 * Removes this initializer from the list
	 */
	public void removeInitializer(final IJCTStatement initializer);

	/**
	 * Remove the initializer at the index
	 */
	public void removeInitializer(final int anIndex);

	/**
	 * Returns the list of initializers of this for
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTStatement> getInitializers();

	/**
	 * Modifies the condition of this for
	 * 
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(final IJCTExpression condition);

	/**
	 * Returns the condition of this for
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTExpression getCondition();

	/**
	 * Adds a "updater" at the index (or move it there)
	 */
	public void addUpdater(
		final int anIndex,
		final IJCTExpressionStatement updater);

	/**
	 * Adds a "updater" at the end of the list (or move it there)
	 */
	public void addUpdater(final IJCTExpressionStatement updater);

	/**
	 * Removes this updater from the list
	 */
	public void removeUpdater(final IJCTExpressionStatement updater);

	/**
	 * Remove the updater at the index
	 */
	public void removeUpdater(final int anIndex);

	/**
	 * Returns the list of updaters of this for
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpressionStatement> getUpdaters();

	/**
	 * Modifies the body of this for
	 * 
	 * @param body
	 *            the new body
	 */
	public void setBody(final IJCTStatement body);

	/**
	 * Returns the body of this for <em>Included in the enclosed elements.</em>
	 */
	public IJCTStatement getBody();

}
