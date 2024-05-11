/**
 * @author Mathieu Lemoine
 * @created 2008-10-27 (月)
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
 * Interface representing a selector (possibly qualified identifier which can be
 * totally resolved just by knowing the element referenced) or a simple
 * identifier (if the selector is used for a class member not resolvable by
 * itself and as the second part of a {@link IJCTMemberSelector}). E.g.: a
 * simple selector is an expression like:
 * <ul>
 * <li>[*.]this.foo (TODO : remove this one)</li>
 * <li>A reference to a static member of a (raw) class</li>
 * <li>A reference to a (raw) class or a package</li>
 * <li>A reference to a local variable</li>
 * <li>... <em>this list is not exhaustive</em> ...</li>
 * </ul>
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTSimpleSelector<Identifiable extends IJCTIdentifiable>
		extends IJCTSelector<Identifiable> {
	/**
	 * Modifies the element of this simple selector
	 * 
	 * @param element
	 *            the new element
	 */
	public void setElement(final Identifiable element);

	/**
	 * Returns the element of this simple selector
	 */
	public Identifiable getElement();

}
