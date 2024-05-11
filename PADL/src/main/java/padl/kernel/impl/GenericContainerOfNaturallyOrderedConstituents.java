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
package padl.kernel.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import padl.kernel.IConstituent;
import padl.kernel.INavigable;
import padl.util.CharArrayComparator;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/04/09
 */
public class GenericContainerOfNaturallyOrderedConstituents extends
		AbstractGenericContainerOfConstituents {

	private static class NaturalOrderComparator implements Comparator {
		private static NaturalOrderComparator UniqueInstance;
		private static NaturalOrderComparator getInstance() {
			if (NaturalOrderComparator.UniqueInstance == null) {
				NaturalOrderComparator.UniqueInstance =
					new NaturalOrderComparator();
			}
			return NaturalOrderComparator.UniqueInstance;
		}

		public int compare(final Object o1, final Object o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			return CharArrayComparator.getInstance().compare(
				((IConstituent) o1).getID(),
				((IConstituent) o2).getID());
		}
	}
	private static final long serialVersionUID = -3180931385964182545L;

	private boolean dirty;
	public GenericContainerOfNaturallyOrderedConstituents(
		final INavigable aContainerConstituent) {

		super(aContainerConstituent);
	}
	public GenericContainerOfNaturallyOrderedConstituents(
		final INavigable aContainerConstituent,
		final int anInitialCapacity) {

		super(aContainerConstituent, anInitialCapacity);
	}
	protected void directlyAddConstituentExtra(final IConstituent aConstituent) {
		this.dirty = true;
	}
	public Iterator getIteratorOnConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		if (this.dirty) {
			Arrays
				.sort(this.constituents, NaturalOrderComparator.getInstance());
			this.dirty = false;
		}
		//	this.constituentIterator.reset();
		//	return this.constituentIterator;
		return super.getIteratorOnConstituents();
	}
}
