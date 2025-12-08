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

import org.junit.Assert;

import junit.framework.TestCase;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.Composite;

public final class RemoveTest extends TestCase {
	private IDesignMotifModel compositePattern;
	private IDesignMotifModel clonedCompositePattern2;

	public RemoveTest(String name) {
		super(name);
	}

	protected void setUp() throws CloneNotSupportedException {
		this.compositePattern = new Composite();
		this.clonedCompositePattern2 = (IDesignMotifModel) this.compositePattern
				.clone();
		this.clonedCompositePattern2
				.removeConstituentFromID("Composite".toCharArray());
	}

	public void test2() {
		Assert.assertEquals("Number of entities", 2,
				this.clonedCompositePattern2.getNumberOfConstituents());
	}
}
