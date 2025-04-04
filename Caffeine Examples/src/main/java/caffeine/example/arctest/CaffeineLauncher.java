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
package caffeine.example.arctest;

import caffeine.Caffeine;
import caffeine.Constants;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.2
 */
public final class CaffeineLauncher {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				"../Caffeine/Rules/OnlyStaticEvents.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../Caffeine Examples/bin",
				"caffeine.example.arctest.ArcTest",
				new String[] { "caffeine.example.arctest.*", },
				Constants.GENERATE_METHOD_ENTRY_EVENT
					| Constants.GENERATE_PROGRAM_END_EVENT,
				new String[][] {
					new String[] {
						"caffeine.example.arctest.ArcTest",
						"caffeine.example.arctest.ArcControls",
						"controls" },
					new String[] {
						"caffeine.example.arctest.ArcTest",
						"caffeine.example.arctest.ArcCanvas",
						"canvas" }
		});
	}
}