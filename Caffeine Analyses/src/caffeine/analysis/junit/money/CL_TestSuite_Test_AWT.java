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
public final class CL_TestSuite_Test_AWT {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				// "../Caffeine Analyses/src/caffeine/analysis/junit/money/TestSuite-Test_AWT.trace",
				"Rules/AllEvents.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine;../JUnit",
				"junit.samples.money.MoneyTest",
				new String[] {
					"junit.framework.TestSuite",
					"junit.framework.Test",
					"junit.awtui.TestRunner" },
				new String[][] {
					new String[] {
						"junit.framework.TestSuite",
						"java.util.Vector",
						"fTests" },
					new String[] {
						"junit.awtui.TestRunner",
						"java.util.Vector",
						"fExceptions" },
					new String[] {
						"junit.awtui.TestRunner",
						"java.util.Vector",
						"fFailedTests" }
		});
	}
}
