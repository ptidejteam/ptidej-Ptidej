/**
 * @author Mathieu Lemoine
 * @created 2008-10-18 (土)
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

package jct.util.reference;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/**
 * This class represents a Strong Reference to an Object...
 *
 * This is the closest representation of a pointer possible.
 * This class implements the List interface for convinience only...
 *
 * @author Mathieu Lemoine
 */
public class StrongReference<T> extends AbstractList<T> implements List<T>,
		RandomAccess, Serializable {
	private static final long serialVersionUID = -2370697996557260864L;

	private T m_element;

	public StrongReference() {
		this.m_element = null;
	}

	public StrongReference(final Collection<? extends T> c) {
		this.m_element = 0 == c.size() ? null : c.iterator().next();
	}

	public StrongReference(final T element) {
		this.m_element = element;
	}

	@Override
	public int size() {
		return 1;
	}

	public T get() {
		return this.m_element;
	}

	@Override
	public T get(final int i) {
		if (0 != i)
			throw new IndexOutOfBoundsException(
				"This list contains only one element");

		return this.get();
	}

	public T set(final T e) {
		final T old = this.m_element;
		this.m_element = e;
		return old;
	}

	@Override
	public T set(final int i, final T e) {
		if (0 != i)
			throw new IndexOutOfBoundsException(
				"This list contains only one element");

		return this.set(e);
	}

	@Override
	public boolean contains(final Object e) {
		return this.m_element.equals(e);
	}
}
