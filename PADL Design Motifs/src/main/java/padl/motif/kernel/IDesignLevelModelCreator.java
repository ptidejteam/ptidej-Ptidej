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
package padl.motif.kernel;

import padl.kernel.exception.CreationException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/05
 */
public interface IDesignLevelModelCreator {
	void create(final IDesignLevelModel aDesignLevelModel)
			throws CreationException;
}
