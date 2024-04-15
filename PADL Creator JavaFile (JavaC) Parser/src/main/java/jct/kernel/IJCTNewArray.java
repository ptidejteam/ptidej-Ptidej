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
 * This class represents a new array expression
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTNewArray extends IJCTExpression,
		IJCTElementContainer<IJCTExpression> {
	/**
	 * Modifies the element type of this new array
	 * 
	 * @param elementType
	 *            the new element type
	 */
	public void setElementType(final IJCTType elementType);

	/**
	 * Returns the element type of this new array
	 */
	public IJCTType getElementType();

	/**
	 * Adds a "dimension" at the index (or move it there)
	 */
	public void addDimension(final int anIndex, final IJCTExpression dimension);

	/**
	 * Adds a "dimension" at the end of the list (or move it there)
	 */
	public void addDimension(final IJCTExpression dimension);

	/**
	 * Removes this dimension from the list
	 */
	public void removeDimension(final IJCTExpression dimension);

	/**
	 * Remove the dimension at the index
	 */
	public void removeDimension(final int anIndex);

	/**
	 * Returns the list of dimensions of this new array
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpression> getDimensions();

	/**
	 * Modifies the unspecified dimensions of this new array
	 * 
	 * @param unspecifiedDimensions
	 *            the new unspecified dimensions
	 */
	public void setUnspecifiedDimensions(final int unspecifiedDimensions);

	/**
	 * Returns the unspecified dimensions of this new array
	 */
	public int getUnspecifiedDimensions();

	/**
	 * Adds a "initializer" at the index (or move it there)
	 */
	public void addInitializer(
		final int anIndex,
		final IJCTExpression initializer);

	/**
	 * Adds a "initializer" at the end of the list (or move it there)
	 */
	public void addInitializer(final IJCTExpression initializer);

	/**
	 * Removes this initializer from the list
	 */
	public void removeInitializer(final IJCTExpression initializer);

	/**
	 * Remove the initializer at the index
	 */
	public void removeInitializer(final int anIndex);

	/**
	 * Returns the list of initializers of this new array
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTExpression> getInitializers();

}
