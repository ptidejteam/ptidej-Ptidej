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
package caffeine.analysis.metajr;

import caffeine.Caffeine;

/**
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public class MetaJRCaffeineLauncher {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				//"../Caffeine Analyses/src/caffeine/analysis/metajr/Multiple.trace",
				"Rules/AllEvents.pl",
				// "Rules/Composition.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine;../MetaJ-R/bin",
				"fr.emn.metaj.reflective.Main",
				new String[] {
					"fr.emn.metaj.reflective.BaseInstance",
					"fr.emn.metaj.reflective.Instance",
					"fr.emn.metaj.reflective.BaseClass",
					"fr.emn.metaj.reflective.MethodList",
					"fr.emn.metaj.reflective.DataList" },
				new String[][] {
					new String[] {
						"fr.emn.metaj.reflective.BaseInstance",
						"fr.emn.metaj.reflective.Instance",
						"referent" },
					new String[] {
						"fr.emn.metaj.reflective.BaseClass",
						"fr.emn.metaj.reflective.DataList",
						"dataList" },
					new String[] {
						"fr.emn.metaj.reflective.BaseClass",
						"fr.emn.metaj.reflective.MethodList",
						"methodList" }
		});
	}
}
