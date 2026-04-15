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
public class DefaultAssociationApproximations implements IApproximations {
	private static final String[] APPROXIMATIONS = {
			"ptidej.solver.java.constraint.repository.CompositionConstraint",
			"ptidej.solver.java.constraint.repository.AggregationConstraint",
			"ptidej.solver.java.constraint.repository.AssociationConstraint",
			"ptidej.solver.java.constraint.repository.UseConstraint" };

	private static DefaultAssociationApproximations UniqueInstance;
	public static DefaultAssociationApproximations getDefaultApproximations() {
		if (DefaultAssociationApproximations.UniqueInstance == null) {
			DefaultAssociationApproximations.UniqueInstance =
				new DefaultAssociationApproximations();
		}
		return DefaultAssociationApproximations.UniqueInstance;
	}

	private DefaultAssociationApproximations() {
	}
	public String[] getApproximations() {
		return DefaultAssociationApproximations.APPROXIMATIONS;
	}
}
