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
package ptidej.solver.constraint.repository;

import ptidej.solver.Variable;
import ptidej.solver.constraint.BinaryCounterConstraint;
import choco.palm.integer.PalmIntVar;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class InheritanceTreeDepthConstraint extends BinaryCounterConstraint {
	public InheritanceTreeDepthConstraint(
		final int n,
		final String name,
		final String command,
		final Variable v0,
		final Variable v1,
		final PalmIntVar counter,
		final int weight) {

		super(n, name, command, v0, v1, counter, weight);
		this.setFieldName("superEntities");
		this.setSymbol("-|>- or = depth");
	}
}
