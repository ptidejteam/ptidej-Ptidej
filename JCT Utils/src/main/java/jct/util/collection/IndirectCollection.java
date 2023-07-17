/**
 * @author Mathieu Lemoine
 * @created 2008-09-28 (日)
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

package jct.util.collection;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class allow the creation of a collection of collections whose iterator
 * iterate over the elements of the member collections, and not on the
 * collection themselves. The iterator also support transparent remove support.
 *
 * @author Mathieu Lemoine
 */
public class IndirectCollection<T> extends AbstractCollection<T> implements
		Serializable {
	protected class InternalIterator implements Iterator<T> {
		// TODO: This code is unnecessarily complex
		// and create many objects whose usefulness
		// is doubtful: simplify or remove.
		private int m_collection_index = 0;
		private Iterator<T> m_it;
		private SoftReference<Iterator<T>> m_next_it =
			new SoftReference<Iterator<T>>(null);

		InternalIterator(final Iterator<T> it) {
			this.m_it = it;
		}

		public boolean hasNext() {
			final Iterator<T> it = this.p_getNextValidIterator();

			if (null == it)
				return false;
			else {
				this.m_next_it = new SoftReference<Iterator<T>>(it);
				return true;
			}
		}

		public T next() {
			Iterator<T> it = this.m_next_it.get();

			if (null == it) {
				it = this.p_getNextValidIterator();
				if (null == it)
					throw new NoSuchElementException();
			}
			else
				this.m_next_it = new SoftReference<Iterator<T>>(null);

			return (this.m_it = it).next();
		}

		private Iterator<T> p_getNextValidIterator() {
			if (this.m_it.hasNext())
				return this.m_it;

			while (this.m_collection_index < IndirectCollection.this.m_collections.length - 1) {
				final Iterator<T> next_collection_iterator =
					IndirectCollection.this.m_collections[++this.m_collection_index]
						.iterator();
				if (next_collection_iterator.hasNext())
					return next_collection_iterator;
			}

			return null;
		}

		public void remove() {
			this.m_it.remove();
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3758316801632055213L;

	protected final Collection<T>[] m_collections;

	public IndirectCollection(final Collection<? extends T>... collections) {
		this.m_collections = (Collection<T>[]) collections;
	}

	@Override
	public Iterator<T> iterator() {
		return this.new InternalIterator(this.m_collections[0].iterator());
	}

	@Override
	public int size() {
		int size = 0;
		for (final Collection<T> l : this.m_collections)
			size += l.size();
		return size;
	}
}
