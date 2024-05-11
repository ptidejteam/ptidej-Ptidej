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
package padl.creator.aolfile.misc;

import org.apache.commons.lang3.ArrayUtils;
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
				.getConstituentFromName(anEntityName.toCharArray());

		if (firstClassEntity == null) {
			firstClassEntity =
				anAbstractModel.getFactory().createGhost(
					anEntityName.toCharArray(),
					anEntityName.toCharArray());
			anAbstractModel.addConstituent(firstClassEntity);
		}

		return firstClassEntity;
	}
	public static char[] cleanCppEntityName(char[] anEntityName) {
		final int lastCharIndex = anEntityName.length - 1;
		if (anEntityName[lastCharIndex] == '&'
				|| anEntityName[lastCharIndex] == '*') {

			return ArrayUtils.subarray(anEntityName, 0, lastCharIndex);
		}
		return anEntityName;
	}
}
