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
public class DefaultContainerAssociationApproximations implements
		IApproximations {
	private static final String[] APPROXIMATIONS =
		{
				"ptidej.solver.constraint.repository.ContainerCompositionConstraint",
				"ptidej.solver.constraint.repository.ContainerAggregationConstraint",
				"ptidej.solver.constraint.repository.CompositionConstraint",
				"ptidej.solver.constraint.repository.AggregationConstraint",
				"ptidej.solver.constraint.repository.AssociationConstraint",
				"ptidej.solver.constraint.repository.UseConstraint" };

	private static DefaultContainerAssociationApproximations UniqueInstance;
	public static DefaultContainerAssociationApproximations getDefaultApproximations() {
		if (DefaultContainerAssociationApproximations.UniqueInstance == null) {
			DefaultContainerAssociationApproximations.UniqueInstance =
				new DefaultContainerAssociationApproximations();
		}
		return DefaultContainerAssociationApproximations.UniqueInstance;
	}

	private DefaultContainerAssociationApproximations() {
	}
	public String[] getApproximations() {
		return DefaultContainerAssociationApproximations.APPROXIMATIONS;
	}
}
