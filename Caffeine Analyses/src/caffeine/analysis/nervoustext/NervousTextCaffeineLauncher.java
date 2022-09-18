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
package caffeine.analysis.nervoustext;

import caffeine.Caffeine;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public class NervousTextCaffeineLauncher {
	public static void main(String[] args) {
		Caffeine.getUniqueInstance().start(
			"../Caffeine/Rules/AllEvents.pl",
			"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine",
			"caffeine.analysis.nervoustext.NervousText",
			new String[] { "caffeine.analysis.nervoustext.NervousText" },
			null);
	}
}
