/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import padl.kernel.IConstituent;
import padl.kernel.IFilter;

/** 
 * Iterator that filters the elements of another iterator using 
 * an IFilter. The responsibility of a filtered iterator is 
 * passed to the FilteredIterator; consequently it should not be
 * reused.
 * 
 * Note: that you CANNOT use the remove operation. While this goes
 * against the API of an iterator, it is acceptable from the standpoint
 * of the iterator pattern.
 * 
 * - Created  on Sep 20, 2005
 * - Modified on Sep 12, 2008 to use standard IFilter interface
 *  
 * @author Stephane Vaucher
 *
 */
public class FilteredIterator implements Iterator {

	private final Iterator iterator;
	private final IFilter filter;

	private IConstituent lookAhead = null;

	/**
	 * @param iterator
	 */
	public FilteredIterator(IFilter filter, Iterator iterator) {
		this.filter = filter;
		this.iterator = iterator;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		if (this.lookAhead != null) {
			throw new IllegalStateException(
				"LookAhead doesn't allow for removes.");
		}
		this.iterator.remove();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		boolean hasNext = true;
		if (this.lookAhead == null) {
			hasNext = findNext();
		}
		return hasNext;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		IConstituent o = null;
		if (this.lookAhead == null) {
			if (!findNext()) {
				throw new NoSuchElementException("Iteration is done.");
			}
		}
		o = this.lookAhead;
		this.lookAhead = null;
		return o;
	}

	/**
	 * Sets the next correct value and stores it in 
	 *
	 */
	private boolean findNext() {
		boolean success = false;
		while (this.iterator.hasNext()) {
			IConstituent element = (IConstituent) this.iterator.next();

			if (!success && this.filter.isFiltered(element)) {
				this.lookAhead = element;
				success = true;
				break;
			}
		}
		return success;
	}

}
