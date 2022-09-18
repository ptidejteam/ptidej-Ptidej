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

import padl.kernel.IConstituent;
import padl.kernel.IFilter;

/**
 * @author Yann
 * @since  2014/03/19
 */
public class NotFilter implements IFilter {
	private final IFilter filterToNegate;
	public NotFilter(final IFilter aFilter) {
		this.filterToNegate = aFilter;
	}
	public boolean isFiltered(IConstituent aConstituent) {
		return !this.filterToNegate.isFiltered(aConstituent);
	}
}
