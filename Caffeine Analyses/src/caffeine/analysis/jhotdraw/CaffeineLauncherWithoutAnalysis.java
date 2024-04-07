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
package caffeine.analysis.jhotdraw;

import caffeine.Caffeine;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public class CaffeineLauncherWithoutAnalysis {
	public static void main(final String[] args) {
		Caffeine.getUniqueInstance().start(
			"../Caffeine/Rules/NoAnalysis.pl",
			"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../JHotDraw v5.1/bin/",
			"CH.ifa.draw.samples.javadraw.JavaDrawApp",
			null,
			null);
	}
}
