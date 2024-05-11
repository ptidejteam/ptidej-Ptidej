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
 * This class represents an Array Type
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTArrayType extends IJCTNonPrimitiveType {
	/**
	 * Modifies the underlying type, set also the registered type name.
	 */
	public void setUnderlyingType(
		final IJCTType underlyingType,
		final String registeredUnderlyingTypeName);

	/**
	 * Returns the (registered) underlying type's name.
	 */
	public String getUnderlyingTypeName();

	/**
	 * Clears the registered underlying type name. Post-condition: {@code
	 * this.getUnderlyingTypeName().equals(this.getUnderlyingType().getTypeName(
	 * ))} returns true;
	 */
	public void clearRegisteredTypeName();

	/**
	 * Modifies the underlying type of this array type
	 * 
	 * @param underlyingType
	 *            the new underlying type
	 */
	public void setUnderlyingType(final IJCTType underlyingType);

	/**
	 * Returns the underlying type of this array type
	 */
	public IJCTType getUnderlyingType();

	/**
	 * Returns the clone method of this array type
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTMethod getCloneMethod();

	/**
	 * Returns the length field of this array type
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTField getLengthField();

}
