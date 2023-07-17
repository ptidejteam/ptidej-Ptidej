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
public class TSE07ExtensibleInheritanceOrNoneApproximations implements
		IApproximations {

	private static final String[] APPROXIMATIONS =
		{
				"ptidej.solver.constraint.repository.StrictInheritanceConstraint",
				"ptidej.solver.constraint.repository.StrictInheritancePathConstraint",
				"ptidej.solver.constraint.repository.NotEqualConstraint" };

	private static TSE07ExtensibleInheritanceOrNoneApproximations UniqueInstance;
	public static TSE07ExtensibleInheritanceOrNoneApproximations getDefaultApproximations() {
		if (TSE07ExtensibleInheritanceOrNoneApproximations.UniqueInstance == null) {
			TSE07ExtensibleInheritanceOrNoneApproximations.UniqueInstance =
				new TSE07ExtensibleInheritanceOrNoneApproximations();
		}
		return TSE07ExtensibleInheritanceOrNoneApproximations.UniqueInstance;
	}

	private TSE07ExtensibleInheritanceOrNoneApproximations() {
	}
	public String[] getApproximations() {
		return TSE07ExtensibleInheritanceOrNoneApproximations.APPROXIMATIONS;
	}
}
