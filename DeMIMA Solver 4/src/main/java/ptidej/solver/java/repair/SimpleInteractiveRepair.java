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
package ptidej.solver.java.repair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;
import choco.AbstractConstraint;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.search.PalmContradiction;
import ptidej.solver.java.Constraint;
import ptidej.solver.java.Problem;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public class SimpleInteractiveRepair extends NoMemoryRepair {
	public SimpleInteractiveRepair(final Problem problem) {
		super(problem);
	}

	public ArrayList[] ptidejSelectDecisionToUndo(final Explanation expl)
			throws PalmContradiction {
		//		we save the withdrawn and the added constraint
		ArrayList[] re = { new ArrayList(), new ArrayList() };

		AbstractConstraint ct =
			(AbstractConstraint) Collections.min(
				expl.toSet(),
				new choco.palm.explain.BetterConstraintComparator());
		PalmConstraintPlugin plug1 = (PalmConstraintPlugin) ct.getPlugIn();
		if (plug1.getWeight() == 0) {
			re[0].add(ct);
			return re;
			//return (Constraint) ct;
		}
		else {
			// we treat the relaxation of the problem 
			// by constraints retraction
			ArrayList cts = new ArrayList();
			Iterator iterator = (expl.toSet()).iterator();
			while (iterator.hasNext()) {
				cts.add((AbstractConstraint) iterator.next());
			}
			Collections.sort(
				cts,
				new choco.palm.explain.BetterConstraintComparator());
			System.out.println(MultilingualManager.getString(
				"NO_MORE_SOL",
				SimpleInteractiveRepair.class));

			//we print the listing of the constarints to be removed 
			for (int i = 0; i < cts.size(); i++) {
				PalmConstraintPlugin plug2 =
					(PalmConstraintPlugin) ((AbstractConstraint) cts.get(i))
						.getPlugIn();
				System.out.println(i + "."
						+ ((Constraint) cts.get(i)).getName() + ' ' + "weight "
						+ plug2.getWeight());
			}
			System.out.println(MultilingualManager.getString(
				"WHICH_TO_RELAX",
				SimpleInteractiveRepair.class));
			BufferedReader keyBoard =
				new BufferedReader(new InputStreamReader(System.in));
			int selectC = -1;
			try {
				String choix = keyBoard.readLine();
				selectC = Integer.parseInt(choix);
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			// The research stops itself if no constraints
			// was chosen to be removed from the current problem
			if (selectC == -1) {
				throw new PalmContradiction();
			}
			else {
				re[0].add((AbstractConstraint) cts.get(selectC));
				return re;
			}
		}

	}

}
