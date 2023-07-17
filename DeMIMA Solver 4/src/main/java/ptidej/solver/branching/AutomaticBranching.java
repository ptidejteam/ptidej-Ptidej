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
public class AutomaticBranching extends Branching {
	public Object selectBranchingObject() {
		final Problem pb = (Problem) this.extender.getManager().getProblem();
		this.setSolutionNumber(this.getSolutionNumber() + 1);
		this.printOutSolution(
			pb,
			this.getMessage(),
			this.getSolutionNumber(),
			this.getWeight(),
			this.getXCommand());
		final Explanation expl = pb.makeExplanation();

		// Yann 2007/03/30: Variables...
		// I make sure there is at least one variable.
		// In case the user is trying to call the solver
		// on an empty problem.
		if (pb.getVars().length > 0) {
			final IntVar last = pb.getIntVar(pb.getVars().length - 1);

			for (int i = 0; i < pb.getVars().length; i++) {
				((Variable) pb.getIntVar(i)).self_explain(
					PalmIntDomain.DOM,
					expl);
			}

			final IntDomain domain = last.getDomain();
			if (domain.getSize() != 0) {
				final IntIterator iterator = domain.getIterator();
				while (iterator.hasNext()) {
					final int value = iterator.next();
					pb.post(pb.neq(last, value), expl);
				}
			}
			else {
				pb.post(pb.neq(last, last.getValue()), expl);
			}
		}

		try {
			pb.propagate();
		}
		catch (final ContradictionException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// TODO: What is this return for?
		return this.getNextBranching();
	}
}
