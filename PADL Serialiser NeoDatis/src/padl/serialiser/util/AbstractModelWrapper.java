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
package padl.serialiser.util;

import padl.kernel.IAbstractModel;

/**
 * A simple wrapper to easily retrieve an abstract model
 * from the database, whatever it is really (a DesignMotif,
 * a ICodeLevelModel, and so on)
 * 
 * @author Yann
 * 2009/02/23
 */
public class AbstractModelWrapper {
	private final IAbstractModel abstractModel;
	public AbstractModelWrapper(final IAbstractModel anAbstractModel) {
		this.abstractModel = anAbstractModel;
	}
	public IAbstractModel getAbstractModel() {
		return this.abstractModel;
	}
}
