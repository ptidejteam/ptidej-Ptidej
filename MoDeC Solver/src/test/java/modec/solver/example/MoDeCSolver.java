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
package modec.solver.example;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.PalmProblem;

/**
 * @author Yann-Gaël Guéhéneuc
 * @author Janice Ka-yee Ng
 * @since  2007/04/05
 */
public class MoDeCSolver {
	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final IntVar variable1 = problem.makeBoundIntVar("v1", 0, 2);
		final IntVar variable2 = problem.makeBoundIntVar("v2", 0, 2);
		final Constraint constraint = problem.neq(variable1, variable2);

		// Yann 2013/08/12: Needed?
		//	problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {
			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					// System.out.println(variable1.isInstantiated());
					// System.out.println(variable2.isInstantiated());

					System.out.print('[');
					System.out.print(variable1.getDomain().getInf());
					System.out.print(',');
					System.out.print(variable1.getDomain().getSup());
					System.out.println(']');

					System.out.print('[');
					System.out.print(variable2.getDomain().getInf());
					System.out.print(',');
					System.out.print(variable2.getDomain().getSup());
					System.out.println(']');
				}
			}
		});
		problem.post(constraint);
		problem.solve(true);
		System.out.println(problem.getPalmSolver().solutions);
	}
}
