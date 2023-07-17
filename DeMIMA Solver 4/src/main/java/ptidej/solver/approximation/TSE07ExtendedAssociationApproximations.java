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
public class TSE07ExtendedAssociationApproximations implements IApproximations {
	private static final String[] APPROXIMATIONS = {
			"ptidej.solver.constraint.repository.AssociationConstraint",
			"ptidej.solver.constraint.repository.UseConstraint",
			"ptidej.solver.constraint.repository.NotEqualConstraint" };

	private static TSE07ExtendedAssociationApproximations UniqueInstance;
	public static TSE07ExtendedAssociationApproximations getDefaultApproximations() {
		if (TSE07ExtendedAssociationApproximations.UniqueInstance == null) {
			TSE07ExtendedAssociationApproximations.UniqueInstance =
				new TSE07ExtendedAssociationApproximations();
		}
		return TSE07ExtendedAssociationApproximations.UniqueInstance;
	}

	private TSE07ExtendedAssociationApproximations() {
	}
	public String[] getApproximations() {
		return TSE07ExtendedAssociationApproximations.APPROXIMATIONS;
	}
}
