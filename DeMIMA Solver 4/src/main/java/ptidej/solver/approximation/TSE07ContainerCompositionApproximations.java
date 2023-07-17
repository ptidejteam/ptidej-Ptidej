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
public class TSE07ContainerCompositionApproximations implements IApproximations {
	private static final String[] APPROXIMATIONS =
		{ "ptidej.solver.constraint.repository.ContainerCompositionConstraint",
				"ptidej.solver.constraint.repository.ContainerAggregationConstraint" };

	private static TSE07ContainerCompositionApproximations UniqueInstance;
	public static TSE07ContainerCompositionApproximations getDefaultApproximations() {
		if (TSE07ContainerCompositionApproximations.UniqueInstance == null) {
			TSE07ContainerCompositionApproximations.UniqueInstance =
				new TSE07ContainerCompositionApproximations();
		}
		return TSE07ContainerCompositionApproximations.UniqueInstance;
	}

	private TSE07ContainerCompositionApproximations() {
	}
	public String[] getApproximations() {
		return TSE07ContainerCompositionApproximations.APPROXIMATIONS;
	}
}
