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

import padl.kernel.INavigable;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/04/09
 */
public class GenericContainerOfInsertionOrderedConstituents extends
		AbstractGenericContainerOfConstituents {

	private static final long serialVersionUID = -3180931385964182545L;

	public GenericContainerOfInsertionOrderedConstituents(
		final INavigable aContainerConstituent) {

		super(aContainerConstituent);
	}
	public GenericContainerOfInsertionOrderedConstituents(
		final INavigable aContainerConstituent,
		final int anInitialCapacity) {

		super(aContainerConstituent, anInitialCapacity);
	}
}
