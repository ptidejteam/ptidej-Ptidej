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
package ptidej.solver.branching;

import ptidej.solver.Branching;
import ptidej.solver.Problem;
import ptidej.solver.Variable;
import util.io.ProxyConsole;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.integer.var.IntDomain;
import choco.palm.explain.Explanation;
import choco.palm.integer.PalmIntDomain;
import choco.util.IntIterator;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class SaveAllSolutions extends Branching {
	public SaveAllSolutions() {
	}

	public Object selectBranchingObject() {
		final Problem pb =
			(Problem) this.getExtender().getManager().getProblem();

		this.setSolutionNumber(this.getSolutionNumber() + 1);
		this.printOutSolution(
			pb,
			this.getMessage(),
			this.getSolutionNumber(),
			this.getWeight(),
			this.getXCommand());

		final Explanation explanation = pb.makeExplanation();
		final IntVar last = pb.getIntVar(pb.getVars().length - 1);
		for (int i = 0; i < pb.getVars().length; i++) {
			((Variable) pb.getIntVar(i)).self_explain(
				PalmIntDomain.DOM,
				explanation);

		}

		final IntDomain domain = last.getDomain();
		final IntIterator iterator = domain.getIterator();
		while (iterator.hasNext()) {
			pb.post(pb.neq(last, iterator.next()), explanation);
		}

		try {
			pb.propagate();
		}
		catch (final ContradictionException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return new Object();
	}
}
