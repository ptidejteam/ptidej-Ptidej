/**
 * @author Mathieu Lemoine
 * @created 2009-03-04 (水)
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTNonPrimitiveType;
import jct.kernel.IJCTRootNode;

/**
 * This class represents a non primitive type
 * 
 * Default implementation for {@link jct.kernel.IJCTNonPrimitiveType}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTNonPrimitiveType<Component extends IJCTElement> extends
		JCTType<Component> implements IJCTNonPrimitiveType {
	JCTNonPrimitiveType(final IJCTRootNode aRootNode) {
		super(aRootNode);
	}

	/**
	 * Checks whether or not this class extends or implements type.
	 * If type is not a class, return false
	 */
	public boolean isExtendingOrImplementing(
		final IJCTNonPrimitiveType aNonPrimitiveType) {
		return this == aNonPrimitiveType
				|| aNonPrimitiveType.getAllSuperClasses().contains(this);
	}

	/**
	 * Returns the set of all super classes or interfaces
	 */
	public Set<IJCTNonPrimitiveType> getAllSuperClasses() {
		final Set<IJCTNonPrimitiveType> not_ok_yet =
			new HashSet<IJCTNonPrimitiveType>();
		final Set<IJCTNonPrimitiveType> result =
			new HashSet<IJCTNonPrimitiveType>();
		result.add(this);

		not_ok_yet.addAll(this.getDirectSuperClasses());

		while (!not_ok_yet.isEmpty()) {
			final IJCTNonPrimitiveType superClass =
				not_ok_yet.iterator().next();
			if (!result.contains(superClass)) {
				result.add(superClass);
				not_ok_yet.addAll(superClass.getAllSuperClasses());
			}
			not_ok_yet.remove(superClass);
		}

		return Collections.unmodifiableSet(result);
	}

	private static final long serialVersionUID = 4213112898470303666L;

}
