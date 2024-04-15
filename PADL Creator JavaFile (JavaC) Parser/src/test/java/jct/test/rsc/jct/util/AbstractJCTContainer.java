/**
 * @author Mathieu Lemoine
 * @created 2008-12-17 (水)
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

package jct.test.rsc.jct.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.JCTKind;

/**
 * Default implementation helper for {@link IJCTContainer}
 *
 * @author Mathieu Lemoine
 */
public abstract class AbstractJCTContainer<Element extends IJCTElement>
		implements IJCTContainer<Element> {
	/**
	 * Returns an unmodifiable list of Enclosed Elements of type T
	 */
	@Override
	public <T extends Element> Collection<T> getEnclosedElements(
			final Class<T> c) {
		final Collection<T> result = new LinkedList<T>();
		for (final Element e : this.getEnclosedElements())
			if (c.isInstance(e))
				result.add(c.cast(e));
		return Collections.unmodifiableCollection(result);
	}

	@Override
	public Collection<? extends IJCTElement> getAllEnclosedElements() {
		return this.getAllEnclosedElements(null, IJCTElement.class, false);
	}

	/**
	 * Returns an unmodifiable Set on indirectly Enclosed Elements of kind K.
	 *
	 * T must be compatible with K
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IJCTElement> Collection<T> getAllEnclosedElements(
			final JCTKind K, final Class<T> c, final boolean first_layer_only) {
		final Set<T> result = new HashSet<T>();
		final Set<IJCTElement> checked = new HashSet<IJCTElement>();
		final Set<IJCTElement> not_ok_yet = new HashSet<IJCTElement>();

		if (this instanceof IJCTElement)
			not_ok_yet.add((IJCTElement) this);
		else
			for (final Element e : this.getEnclosedElements())
				if (null != e)
					not_ok_yet.add(e);

		while (!not_ok_yet.isEmpty()) {
			final Iterator<IJCTElement> it = not_ok_yet.iterator();
			final IJCTElement T = it.next();

			assert null != T : "T should not be null, test below";

			checked.add(T);
			it.remove();

			if (null == K || K == T.getKind()) {
				result.add(c.cast(T));
				if (first_layer_only)
					continue;
			}

			for (final IJCTElement t : ((IJCTContainer<IJCTElement>) T)
					.getEnclosedElements()) {
				if (null == t)
					continue;

				if (!checked.contains(t)) {
					if (t instanceof IJCTContainer)
						not_ok_yet.add(t);
					else if (null == K || K == t.getKind()) {
						result.add(c.cast(t));
						checked.add(t);
					}
				}
			}
		}

		return Collections.unmodifiableCollection(result);
	}
}
