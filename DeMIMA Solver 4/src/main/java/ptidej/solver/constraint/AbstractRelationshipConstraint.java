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
package ptidej.solver.constraint;

import ptidej.solver.Variable;
import ptidej.solver.approximation.IApproximations;

/**
 * @author Yann-Gaël Guéhéneuc
 * @snce   2007/02/24
 */
public abstract class AbstractRelationshipConstraint extends BinaryConstraint {
	public AbstractRelationshipConstraint(
		final String name,
		final String commande,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		super(name, commande, v0, v1, weight, approximations);
	}
}
