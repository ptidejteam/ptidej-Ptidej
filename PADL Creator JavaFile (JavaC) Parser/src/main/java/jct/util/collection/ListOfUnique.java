/**
 * @author Mathieu Lemoine
 * @created 2008-10-17 (金)
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;

import jct.util.equiv.Equivalence;
import jct.util.equiv.NaturalEquivalence;

/**
 * This class implements a List that does not allow any of its elements to be equal.
 * The elements must be such that they could be used in a HashSet.
 * Implemented as a Decorator.
 * Use an {@link java.util.ArrayList} if not used as a Decorator.
 *
 * If used as a decorator, the decorated list must be used via the decorated version.
 *
 * The methods add and set do not throw exception but move the element if it is
 * already present in the list.
 *
 * This class implements the methods size, get, set, add and remove of
 * {@link java.util.AbstractList}, and override contains.
 */
public class ListOfUnique<T> extends AbstractList<T> implements List<T>,
		Set<T>, Serializable {

	private static final long serialVersionUID = 4131860927985341538L;

	private final class TWithEquivalence implements Serializable {
		private static final long serialVersionUID = -6530417188478563487L;
		private final Equivalence<T> equalsp = ListOfUnique.this.equalsp;
		private final T object;

		public TWithEquivalence(T object) {
			this.object = object;
		}

		@Override
		public boolean equals(Object o) {
			return (o instanceof ListOfUnique<?>.TWithEquivalence)
					&& this.equalsp.areEquivalent(
						this.object,
						((TWithEquivalence) o).object);
		}

		public int hashCode() {
			return this.object.hashCode();
		}
	}

	private final Equivalence<T> equalsp;
	private final List<T> list;
	// TODO: Why elements *must* be a set and a crash occurs when replaced by a List?
	private final Set<TWithEquivalence> elements =
		new HashSet<TWithEquivalence>();

	private ListOfUnique(
		final List<T> list,
		final Equivalence<T> equalsp,
		final boolean removeDuplicatedElements) {

		this.list = list;
		this.equalsp = equalsp;

		for (final T e : list)
			if (!this.elements.add(new TWithEquivalence(e))
					&& !removeDuplicatedElements)
				throw new IllegalArgumentException(
					"To decorate a List as a List of Unique, the list must not contains duplicated elements.");
	}

	private ListOfUnique(
		final List<T> list,
		final boolean removeDuplicatedElements) {
		this(list, new NaturalEquivalence<T>(), removeDuplicatedElements);
	}

	public static <T> ListOfUnique<T> decorateList(
		final List<T> list,
		final Equivalence<T> equalsp,
		final boolean removeDuplicatedElements) {
		return new ListOfUnique<T>(list, equalsp, removeDuplicatedElements);
	}

	public static <T> ListOfUnique<T> decorateList(
		final List<T> list,
		final boolean removeDuplicatedElements) {
		return new ListOfUnique<T>(list, removeDuplicatedElements);
	}

	public ListOfUnique() {
		this(new ArrayList<T>(), false);
	}

	public ListOfUnique(Equivalence<T> equalsp) {
		this(new ArrayList<T>(), equalsp, false);
	}

	public ListOfUnique(final Collection<? extends T> c) {
		this();
		for (final T e : c)
			this.add(e);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public T get(final int i) {
		return this.list.get(i);
	}

	@Override
	public T set(final int i, final T e) {
		final T old = this.get(i);
		this.elements.remove(old);

		if (this.contains(e)) {
			final int old_index = this.indexOf(e);
			this.list.set(i, e);
			this.list.remove(old_index);
		}
		else {
			this.list.set(i, e);
			this.elements.add(new TWithEquivalence(e));
		}

		return old;
	}

	@Override
	public void add(final int i, final T e) {
		if (this.contains(e)) {
			final int old_index = this.indexOf(e);
			this.list.add(i, e);
			this.list.remove(old_index);
		}
		else {
			this.elements.add(new TWithEquivalence(e));
			this.list.add(e);
		}

		++super.modCount;
	}

	@Override
	public T remove(final int i) {
		final T e = this.list.remove(i);
		this.elements.remove(new TWithEquivalence(e));
		++super.modCount;
		return e;
	}

	/**
	 * {@inheritDoc}
	 * @param e musts be of type T, otherwise, result is undefined.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(final Object e) {
		return this.elements.contains(new TWithEquivalence((T) e));
	}

	/**
	 * {@inheritDoc}
	 * @param e musts be of type T, otherwise, result is undefined.
	 */
	@Override
	public int indexOf(final Object e) {
		// Yann 2010/06/21: Performance!
		// The code used to read like the following:
		//	if (!this.contains(e)) {
		//		return -1;
		//	}
		//
		//	final ListIterator<T> it = this.listIterator();
		//	while (it.hasNext()) {
		//		if (this.equalsp.areEquivalent(it.next(), (T) e)) {
		//			return it.previousIndex();
		//		}
		//	}
		//
		//	return -1;
		// and to use object equalsp of type Equivalence, 
		// which is an interface whose purpose is to unify
		// and abstract the comparison of objects. This class
		// has several implementation but, after several test,
		// it appears that only its Identity<T> and 
		// NaturalEquivalence<T> implementations were used. 
		// These two implementations basically abstract the
		// == and equals() comparisons, which are alreay
		// abstracted by equals() (overloaded if necessary).
		// Therefore, a simpler, cleaner, and faster 
		// implementation is as follows:

		int oldIndex = this.indexOf0(e);
		// TODO: Replace indexOf0() by indexOf1()...
		//	int newIndex = this.indexOf1(e);
		//	if (oldIndex != newIndex) {
		//		System.err.println("Different! (" + oldIndex + " vs. " + newIndex
		//				+ ')');
		//	}

		return oldIndex;
	}
	@SuppressWarnings("unchecked")
	private int indexOf0(final Object e) {
		if (!this.contains(e)) {
			return -1;
		}

		final ListIterator<T> it = this.listIterator();
		while (it.hasNext()) {
			if (this.equalsp.areEquivalent(it.next(), (T) e)) {
				return it.previousIndex();
			}
		}

		return -1;
	}
	//	private int indexOf1(final Object e) {
	//		if (!this.contains(e)) {
	//			return -1;
	//		}
	//
	//		final ListIterator<T> it = this.listIterator();
	//		while (it.hasNext()) {
	//			if (it.next().equals(e)) {
	//				return it.previousIndex();
	//			}
	//		}
	//
	//		return -1;
	//	}

	/**
	 * {@inheritDoc}
	 * @param e musts be of type T, otherwise, result is undefined.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int lastIndexOf(final Object e) {
		if (!this.contains(e))
			return -1;

		final ListIterator<T> it = this.listIterator(this.size());
		while (it.hasPrevious())
			if (this.equalsp.areEquivalent(it.previous(), (T) e))
				return it.nextIndex();

		return -1;
	}

	/**
	 * {@inheritDoc}
	 * @param o musts be a {@code List<T>} when it's a List, otherwise, result is undefined.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof List<?>))
			return false;

		final List<T> that = (List<T>) o;

		if (this.size() != that.size())
			return false;

		final Iterator<T> it = that.iterator();

		for (T e : this)
			if (!this.equalsp.areEquivalent(e, it.next()))
				return false;

		return true;
	}

	/**
	 * {@inheritDoc}
	 * @param o musts be of type T, otherwise, result is undefined.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (!this.contains(o)) {
			return false;
		}

		final Iterator<T> it = this.iterator();
		while (it.hasNext()) {
			if (this.equalsp.areEquivalent(it.next(), (T) o)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

	@Override
	public String toString() {
		return this.list.toString();
	}

	@Override
	public Spliterator<T> spliterator() {
		return super.spliterator();
	}
}
