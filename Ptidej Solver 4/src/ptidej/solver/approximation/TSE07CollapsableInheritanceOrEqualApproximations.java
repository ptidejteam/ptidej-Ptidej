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
package ptidej.solver.approximation;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/08/16
 */
public class TSE07CollapsableInheritanceOrEqualApproximations implements
		IApproximations {

	private static final String[] APPROXIMATIONS = {
			"ptidej.solver.constraint.repository.StrictInheritanceConstraint",
			"ptidej.solver.constraint.repository.InheritanceConstraint",
			"ptidej.solver.constraint.repository.EqualConstraint" };

	private static TSE07CollapsableInheritanceOrEqualApproximations UniqueInstance;
	public static TSE07CollapsableInheritanceOrEqualApproximations getDefaultApproximations() {
		if (TSE07CollapsableInheritanceOrEqualApproximations.UniqueInstance == null) {
			TSE07CollapsableInheritanceOrEqualApproximations.UniqueInstance =
				new TSE07CollapsableInheritanceOrEqualApproximations();
		}
		return TSE07CollapsableInheritanceOrEqualApproximations.UniqueInstance;
	}

	private TSE07CollapsableInheritanceOrEqualApproximations() {
	}
	public String[] getApproximations() {
		return TSE07CollapsableInheritanceOrEqualApproximations.APPROXIMATIONS;
	}
}
