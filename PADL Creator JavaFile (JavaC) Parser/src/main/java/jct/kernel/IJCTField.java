/**
 * @author Mathieu Lemoine
 * @created 2009-06-05 (金)
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

import java.util.Set;

/**
 * This interface represents a class' field
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTField extends IJCTVariable, IJCTClassMember {
	/**
	 * Adds a "modifier" in the set, if it was not already in it
	 */
	public void addModifier(final JCTModifiers modifier);

	/**
	 * Removes this modifier from the set
	 */
	public void removeModifier(final JCTModifiers modifier);

	/**
	 * Returns the set of modifiers of this field
	 */
	public Set<JCTModifiers> getModifiers();

	/**
	 * Modifies the type of this field
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(final IJCTType type);

	/**
	 * Returns the type of this field
	 */
	public IJCTType getType();

	/**
	 * Modifies the name of this field
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(final String name);

	/**
	 * Returns the name of this field
	 */
	public String getName();

	/**
	 * Modifies the initial value of this field
	 * 
	 * @param initialValue
	 *            the new initial value, can be {@code null}
	 */
	public void setInitialValue(final IJCTExpression initialValue);

	/**
	 * Returns the initial value of this field
	 * <em>Included in the enclosed elements.</em>
	 * 
	 * @return null iff there is no initial value
	 */
	public IJCTExpression getInitialValue();

}
