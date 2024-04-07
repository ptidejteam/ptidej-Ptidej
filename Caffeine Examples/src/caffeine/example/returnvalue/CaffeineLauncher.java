/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.returnvalue;

import caffeine.Caffeine;
import caffeine.Constants;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.1
 */
public final class CaffeineLauncher {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				"../Caffeine/Rules/AllDynamicEvents.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../Caffeine Examples/bin",
				"caffeine.example.returnvalue.Return",
				new String[] { "caffeine.example.returnvalue.*" },
				Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT,
				new String[][] { new String[] {
					"caffeine.example.returnvalue.A",
					"caffeine.example.returnvalue.B",
					"b" }
		});
	}
}