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

import caffeine.simulator.Caffeine;

/**
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public class CacaoSimulator {
	public static void main(final String[] args) {
		Caffeine.getUniqueInstance().run("Rules/Composition.pl",
		// "Caffeine/Analysis/Cacao/AllFields.trace");
		// "Caffeine/Analysis/Cacao/Relation-Variable.trace");
		// "Caffeine/Analysis/Cacao/Pair-Value.trace");
		"Caffeine/Analysis/Cacao/Variable-Domain.trace");
	}
}
