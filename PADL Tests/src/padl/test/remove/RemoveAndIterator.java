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
package padl.test.remove;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.IConstituent;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.Composite;

public final class RemoveAndIterator extends TestCase {
	private IDesignMotifModel compositePattern;
	private IDesignMotifModel clonedCompositePattern1;
	private IDesignMotifModel clonedCompositePattern2;
	public RemoveAndIterator(String name) {
		super(name);
	}
	protected void setUp() throws CloneNotSupportedException {
		this.compositePattern = new Composite();

		this.clonedCompositePattern1 =
			(IDesignMotifModel) this.compositePattern.clone();

		this.clonedCompositePattern2 =
			(IDesignMotifModel) this.compositePattern.clone();
	}
	public void testNotConcurrent() {
		try {
			final Iterator iterator =
				this.clonedCompositePattern1.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final IConstituent constituent = (IConstituent) iterator.next();
				this.clonedCompositePattern1
					.removeConstituentFromID(constituent.getID());
			}
		}
		catch (final ConcurrentModificationException e) {
			return;
		}
		Assert.fail("Modifying concurrently the list of constituents!");
	}
	public void testEmbeddedNotConcurrent1() {
		int count = 0;
		final Iterator iterator1 =
			this.compositePattern.getIteratorOnConstituents();
		while (iterator1.hasNext()) {
			iterator1.next();

			final Iterator iterator2 =
				this.compositePattern.getIteratorOnConstituents();
			while (iterator2.hasNext()) {
				iterator2.next();

				count++;
			}
		}
		Assert.assertEquals(9, count);
	}
	public void testEmbeddedNotConcurrent2() {
		int count = 0;
		final Iterator iterator1 =
			this.compositePattern.getIteratorOnConstituents();
		while (iterator1.hasNext() && count < 5) {
			//	final IConstituent constituent1 = (IConstituent) iterator1.next();

			final Iterator iterator2 =
				this.compositePattern.getIteratorOnConstituents();
			while (iterator2.hasNext() && count < 5) {
				//	final IConstituent constituent2 = (IConstituent) iterator2.next();

				count++;
			}
		}

		count = 0;
		final Iterator iterator =
			this.compositePattern.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		Assert.assertEquals(3, count);
	}
	public void testConcurrent() {
		final Iterator iterator =
			this.clonedCompositePattern2.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			this.clonedCompositePattern2.removeConstituentFromID(constituent
				.getID());
		}
		Assert.assertEquals(
			"Number of entities",
			0,
			this.clonedCompositePattern2.getNumberOfConstituents());
	}
}
