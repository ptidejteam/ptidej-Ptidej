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
package ptidej.solver.java.approximation;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/08/16
 */
public class TSE07CollapsableInheritanceApproximations implements
		IApproximations {

	private static final String[] APPROXIMATIONS = {
			"ptidej.solver.java.constraint.repository.StrictInheritanceConstraint",
			"ptidej.solver.java.constraint.repository.InheritanceConstraint" };

	private static TSE07CollapsableInheritanceApproximations UniqueInstance;
	public static TSE07CollapsableInheritanceApproximations getDefaultApproximations() {
		if (TSE07CollapsableInheritanceApproximations.UniqueInstance == null) {
			TSE07CollapsableInheritanceApproximations.UniqueInstance =
				new TSE07CollapsableInheritanceApproximations();
		}
		return TSE07CollapsableInheritanceApproximations.UniqueInstance;
	}

	private TSE07CollapsableInheritanceApproximations() {
	}
	public String[] getApproximations() {
		return TSE07CollapsableInheritanceApproximations.APPROXIMATIONS;
	}
}
