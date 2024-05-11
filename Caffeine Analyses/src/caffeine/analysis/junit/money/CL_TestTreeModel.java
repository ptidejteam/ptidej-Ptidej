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
import caffeine.Constants;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public final class CL_TestTreeModel {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				"Rules/AllStaticEvents.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine;../JUnit",
				"junit.samples.money.MoneyTest",
				new String[] { "junit.swingui.TestTreeModel" },
				Constants.GENERATE_METHOD_ENTRY_EVENT
					| Constants.GENERATE_METHOD_EXIT_EVENT
					| Constants.GENERATE_FIELD_ACCESS_EVENT
					| Constants.GENERATE_FIELD_MODIFICATION_EVENT,
				new String[][] {
					new String[] {
						"junit.swingui.TestTreeModel",
						"java.util.Hashtable",
						"fFailures" },
					new String[] {
						"junit.swingui.TestTreeModel",
						"java.util.Hashtable",
						"fErrors" },
					new String[] {
						"junit.swingui.TestTreeModel",
						"java.util.Hashtable",
						"fRunTests" }
		});
	}
}
