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
package ptidej.solver;

import java.util.ArrayList;
import choco.AbstractConstraint;
import choco.palm.explain.Explanation;
import choco.palm.search.PalmRepair;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public abstract class Repair extends PalmRepair {
	private Problem problem;

	public Repair(final Problem aProblem) {
		this.problem = aProblem;
	}

	public abstract void remove(final AbstractConstraint aConstraint);
	public abstract ArrayList[] ptidejSelectDecisionToUndo(
		final Explanation anExplanation);
	public Problem getProblem() {
		return this.problem;
	}
}
