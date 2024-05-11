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
import java.util.Set;

/**
 * This clas represents a method
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTMethod extends IJCTClassMember,
		IJCTElementContainer<IJCTStatement> {
	/**
	 * Modifies the return type of this method
	 * 
	 * @param returnType
	 *            the new return type
	 */
	public void setReturnType(final IJCTType returnType);

	/**
	 * Returns the return type of this method
	 */
	public IJCTType getReturnType();

	/**
	 * Adds a "parameter" at the index (or move it there)
	 */
	public void addParameter(final int anIndex, final IJCTParameter aParameter);

	/**
	 * Adds a "parameter" at the end of the list (or move it there)
	 */
	public void addParameter(final IJCTParameter aParameter);

	/**
	 * Removes this parameter from the list
	 */
	public void removeParameter(final IJCTParameter aParameter);

	/**
	 * Remove the parameter at the index
	 */
	public void removeParameter(final int anIndex);

	/**
	 * Returns the list of parameters of this method
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTParameter> getParameters();

	/**
	 * Modifies the body of this method
	 * 
	 * @param body
	 *            the new body
	 */
	public void setBody(final IJCTBlock body);

	/**
	 * Returns the body of this method
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTBlock getBody();

	/**
	 * Adds a "thrown exception" in the set, if it was not already in it
	 */
	public void addThrownException(final IJCTClassType thrownException);

	/**
	 * Removes this thrown exception from the set
	 */
	public void removeThrownException(final IJCTClassType thrownException);

	/**
	 * Returns the set of thrown exceptions of this method
	 */
	public Set<IJCTClassType> getThrownExceptions();

}
