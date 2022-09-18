/**
 * @author Mathieu Lemoine
 * @created 2009-03-03
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

package jct.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import jct.kernel.IJCTElement;

/**
 * General-purpose List of JCT subtrees.
 * Implemented as a Decorator.
 * Use a {@link java.util.LinkedList} if not used as a Decorator.
 *
 * @author Mathieu Lemoine
 */
public class ListOfElements<Element extends IJCTElement> extends
		AbstractJCTContainer<Element> implements List<Element>, Serializable {

	private static final long serialVersionUID = -8273479858727535500L;

	public static <Element extends IJCTElement> ListOfElements<Element> decorateList(
		final List<Element> list) {
		return new ListOfElements<Element>(list);
	}

	private final List<Element> list;

	public ListOfElements() {
		this(new LinkedList<Element>());
	}

	public ListOfElements(final Collection<? extends Element> c) {
		this();
		for (final Element e : c) {
			this.add(e);
		}
	}

	protected ListOfElements(final List<Element> list) {
		this.list = list;
	}

	@Override
	public boolean add(final Element e) {
		return this.list.add(e);
	}

	@Override
	public void add(final int index, final Element element) {
		this.list.add(index, element);
	}

	@Override
	public boolean addAll(final Collection<? extends Element> c) {
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Element> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return this.list.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public Element get(final int index) {
		return this.list.get(index);
	}

	public List<Element> getDecoratedList() {
		return this.list;
	}

	@Override
	public Collection<Element> getEnclosedElements() {
		return this;
	}

	@Override
	public int indexOf(final Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public Iterator<Element> iterator() {
		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(final Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Element> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<Element> listIterator(final int index) {
		return this.list.listIterator();
	}

	@Override
	public Element remove(final int index) {
		return this.list.remove(index);
	}

	@Override
	public boolean remove(final Object o) {
		return this.list.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public Element set(final int index, final Element element) {
		return this.list.set(index, element);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public List<Element> subList(final int fromIndex, final int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return this.list.toArray(a);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("[");
		for (final Element e : this) {
			sb.append(e.getKind());
			if (e instanceof jct.kernel.IJCTIdentifiable)
				sb.append(" : ").append(
					((jct.kernel.IJCTIdentifiable) e).getName());
			sb.append(",\n");

		}

		return sb.append("]").toString();
	}
}
