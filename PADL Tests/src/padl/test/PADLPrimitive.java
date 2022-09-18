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
package padl.test;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2003/12/09
 */
public abstract class PADLPrimitive extends TestCase {
	public static void assertAssigable(
		final String aMessage,
		final Class anInterface,
		final Class aClass) {

		if (!anInterface.isAssignableFrom(aClass)) {
			Assert.fail(aMessage);
		}
	}
	public static IFactory getFactory() {
		return Factory.getInstance();
	}
	public PADLPrimitive(final String aName) {
		super(aName);
	}
}
