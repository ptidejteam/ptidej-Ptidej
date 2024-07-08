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
package caffeine.analysis.document;

import caffeine.Caffeine;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public class CaffeineLauncher {
	public static void main(String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				"../Caffeine/Rules/Composition.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../Ptidej Examples and Configurations/bin",
				"ptidej.example.composite2.Main",
				new String[] {
					"ptidej.example.composite2.Document",
					"ptidej.example.composite2.Element" },
				new String[][] { new String[] {
					"ptidej.example.composite2.Document",
					"ptidej.example.composite2.Element",
					"elements" }
		});
	}
}
