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
public class DefaultInheritanceApproximations implements IApproximations {
	private static final String[] APPROXIMATIONS =
		{
				"ptidej.solver.java.constraint.repository.StrictInheritanceConstraint",
				"ptidej.solver.java.constraint.repository.InheritanceConstraint",
				"ptidej.solver.java.constraint.repository.StrictInheritancePathConstraint",
				"ptidej.solver.java.constraint.repository.InheritancePathConstraint",
				"ptidej.solver.java.constraint.repository.NotEqualConstraint" };

	private static DefaultInheritanceApproximations UniqueInstance;
	public static DefaultInheritanceApproximations getDefaultApproximations() {
		if (DefaultInheritanceApproximations.UniqueInstance == null) {
			DefaultInheritanceApproximations.UniqueInstance =
				new DefaultInheritanceApproximations();
		}
		return DefaultInheritanceApproximations.UniqueInstance;
	}

	private DefaultInheritanceApproximations() {
	}
	public String[] getApproximations() {
		return DefaultInheritanceApproximations.APPROXIMATIONS;
	}
}
