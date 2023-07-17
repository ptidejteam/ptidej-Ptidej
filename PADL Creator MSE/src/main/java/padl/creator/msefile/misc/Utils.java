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
package padl.creator.msefile.misc;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class Utils {
	public static IFirstClassEntity searchForEntity(
		final IAbstractLevelModel anAbstractModel,
		final String anEntityName) {

		IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) anAbstractModel
				.getConstituentFromName(anEntityName);
		if (firstClassEntity == null) {
			firstClassEntity =
				anAbstractModel.getFactory().createGhost(
					anEntityName.toCharArray(),
					anEntityName.toCharArray());
			anAbstractModel.addConstituent(firstClassEntity);
		}
		return firstClassEntity;
	}
}
