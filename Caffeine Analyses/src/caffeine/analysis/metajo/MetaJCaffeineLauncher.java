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
package caffeine.analysis.metajo;

import caffeine.Caffeine;

/**
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public class MetaJCaffeineLauncher {
	public static void main(final String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				// "../Caffeine Analyses/src/caffeine/analysis/metajo/Original.Multiple.trace",
				"Rules/AllEvents.pl",
				// "Rules/Composition.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine;../MetaJ/bin",
				"fr.emn.metaj.Main",
				new String[] {
					"fr.emn.metaj.original.BaseInstance",
					"fr.emn.metaj.original.Instance",
					"fr.emn.metaj.original.BaseClass",
					"fr.emn.metaj.original.MethodList",
					"fr.emn.metaj.original.DataList" },
				new String[][] {
					new String[] {
						"fr.emn.metaj.original.BaseInstance",
						"fr.emn.metaj.original.Instance",
						"referent" },
					new String[] {
						"fr.emn.metaj.original.BaseClass",
						"fr.emn.metaj.original.DataList",
						"dataList" },
					new String[] {
						"fr.emn.metaj.original.BaseClass",
						"fr.emn.metaj.original.MethodList",
						"methodList" }
		});
	}
}
