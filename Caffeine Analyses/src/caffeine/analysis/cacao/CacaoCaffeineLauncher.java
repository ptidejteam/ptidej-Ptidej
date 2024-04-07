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
package caffeine.analysis.cacao;

import caffeine.Caffeine;

/**
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public class CacaoCaffeineLauncher {
	public static void main(String[] args) {
		Caffeine
			.getUniqueInstance()
			.start(
				"../Caffeine/Rules/AllEvents.pl",
				// "Rules/Composition.pl",
				"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../Cacao",
				"fr.emn.cacao.CacaoSolver",
				new String[] { "fr.emn.cacao.Variable", "fr.emn.cacao.Set" },
				//	"fr.emn.cacao.Pair",
				//	"fr.emn.cacao.Value" },
				//	"fr.emn.cacao.Relation",
				//	"fr.emn.cacao.Variable" },
				new String[][] { new String[] {
				"fr.emn.cacao.Variable",
				"fr.emn.cacao.Set",
				"domain" }
				//	new String[] {
				//		"fr.emn.cacao.Pair",
				//		"fr.emn.cacao.Value",
				//		"fst" },
				//	new String[] {
				//		"fr.emn.cacao.Pair",
				//		"fr.emn.cacao.Value",
				//		"snd" },
				//	new String[] {
				//		"fr.emn.cacao.Relation",
				//		"fr.emn.cacao.Variable",
				//		"var1" },
				//	new String[] {
				//		"fr.emn.cacao.Relation",
				//		"fr.emn.cacao.Variable",
				//		"var2" }
		});
	}
}
