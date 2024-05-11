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
package caffeine.analysis.junit.money;

import caffeine.Caffeine;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public final class CL_Money_String {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				// "../Caffeine Analyses/src/caffeine/analysis/junit/money/Money-String.trace",
				"Rules/AllEvents.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine;../JUnit",
				"junit.samples.money.MoneyTest",
				new String[] {
					"junit.samples.money.Money",
					"java.lang.String" },
				new String[][] { new String[] {
					"junit.samples.money.Money",
					"java.lang.String",
					"fCurrency" }
		});
	}
}
