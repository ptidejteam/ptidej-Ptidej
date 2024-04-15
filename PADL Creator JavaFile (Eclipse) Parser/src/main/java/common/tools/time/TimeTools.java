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
package common.tools.time;

import java.util.Date;
import common.tools.constants.Constants;

public enum TimeTools {

	Instance;

	public String convertDateToFileName(final Date date) {
		return new StringBuilder(date
			.toString()
			.replace(Constants.SPACE, Constants.DASH)
			.replace(Constants.COLON, Constants.DASH))
			.append(Constants.DASH)
			.toString();
	}
}
