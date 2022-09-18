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
package epi.solver.problem;

import padl.kernel.ICodeLevelModel;
import ptidej.solver.fingerprint.Rule;

/**
 * @author OlivierK
 */
public class AbstractFactory extends Problem {
	public AbstractFactory(final ICodeLevelModel aCodeLevelModel) {
		super(aCodeLevelModel);

		//this.domain.put("leaf", Manager.build(rdg.computeReducedDomain(Rule.C_LEAF_ROLE_1)));
		this.domain.put(
			"abstractfactory",
			this.getSimpleConstituentList(this.rdg
				.computeReducedDomain(Rule.AF_AF)));
		this.domain.put(
			"abstractproduct",
			this.getSimpleConstituentList(this.rdg
				.computeReducedDomain(Rule.AF_AP)));
		this.domain
			.put("concretefactory", this.getSimpleConstituentList(this.rdg
				.computeReducedDomain(Rule.AF_C)));
	}
}
