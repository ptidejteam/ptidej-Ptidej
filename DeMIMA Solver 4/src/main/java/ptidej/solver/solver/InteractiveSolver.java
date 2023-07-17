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
package ptidej.solver.solver;

import java.util.ArrayList;
import ptidej.solver.AssignVariable;
import ptidej.solver.Problem;
import ptidej.solver.Solver;
import ptidej.solver.branching.InteractiveBranching;
import ptidej.solver.repair.InteractiveRepair;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class InteractiveSolver extends Solver {
	public InteractiveSolver(final Problem pb) {
		super(pb);

		// Attach the repair to solver
		this.attachPalmRepair(new InteractiveRepair(pb));

		final ArrayList list = new ArrayList();
		list.add(new AssignVariable());
		list.add(new InteractiveBranching());

		// Attach branching to solver
		this.attachPalmBranchings(list);
	}

}
