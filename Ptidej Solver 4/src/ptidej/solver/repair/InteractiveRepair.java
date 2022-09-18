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
package ptidej.solver.repair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import ptidej.solver.Branching;
import ptidej.solver.Constraint;
import ptidej.solver.Problem;
import ptidej.solver.Solver;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;
import choco.AbstractConstraint;
import choco.integer.IntVar;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.search.PalmContradiction;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class InteractiveRepair extends MemoryRepair {
	public InteractiveRepair(final Problem problem) {
		super(problem);
	}

	public ArrayList[] ptidejSelectDecisionToUndo(Explanation expl) {
		// we save the withdrawn constraints in the first arrayList
		// and the added constraint on the second.
		ArrayList[] re = { new ArrayList(), new ArrayList() };

		AbstractConstraint ct =
			(AbstractConstraint) Collections.min(
				expl.toSet(),
				new choco.palm.explain.BetterConstraintComparator());
		PalmConstraintPlugin plug1 = (PalmConstraintPlugin) ct.getPlugIn();

		// If the weight of the constraint is 0, we just relax it.
		if (plug1.getWeight() == 0) {
			re[0].add(ct);
			return re;
			//return (Constraint) ct;
		}
		else {
			// we treat the relaxation of the problem 
			// by constraints rétraction
			ArrayList cts = new ArrayList();
			Iterator iterator = (expl.toSet()).iterator();
			while (iterator.hasNext()) {
				cts.add((AbstractConstraint) iterator.next());
			}

			Collections.sort(
				cts,
				new choco.palm.explain.BetterConstraintComparator());
			System.out.print(MultilingualManager.getString(
				"NO_MORE_SOL",
				InteractiveRepair.class));
			if (cts.size() > 1)
				System.out.println("s : ");
			else
				System.out.println(" : ");

			//we print the listing of the constarints to be removed 
			for (int i = 0; i < cts.size(); i++) {
				PalmConstraintPlugin plug2 =
					(PalmConstraintPlugin) ((AbstractConstraint) cts.get(i))
						.getPlugIn();
				System.out.println(i + "  "
						+ ((Constraint) cts.get(i)).getSymbol() + ":"
						+ ((Constraint) cts.get(i)).getName() + ' ' + "weight "
						+ plug2.getWeight());
				String nextConstraint =
					((Constraint) cts.get(i)).getNextConstraint();

				// print the next constraint(if exist) of each constraint of the above listing 
				if (nextConstraint != null) {
					System.out.println(MultilingualManager.getString(
						"TO_BE_REPLACED",
						InteractiveRepair.class,
						new Object[] { nextConstraint.substring(25) }));
				}
			}
			System.out.println(MultilingualManager.getString(
				"WHICH_TO_RELAX",
				InteractiveRepair.class));

			BufferedReader keyBoard =
				new BufferedReader(new InputStreamReader(System.in));
			int selectC = -1;
			try {
				String choix = keyBoard.readLine();
				selectC = Integer.parseInt(choix);
			}
			catch (IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			if (selectC != -1) {
				Constraint constraint = (Constraint) (cts.get(selectC));

				re[0].add(constraint);

				String nextConstraint = constraint.getNextConstraint();
				Branching nextBranching =
					((Solver) this.getManager()).getNextBranching();
				int percentage = nextBranching.getWeight();
				String commande = ((Constraint) constraint).getXCommand();
				String nom = ((Constraint) constraint).getName();
				ArrayList args = new ArrayList();
				args.add(nom);
				args.add(commande);
				for (int i = 0; i < ct.getNbVars(); ++i) {
					args.add((IntVar) constraint.getVar(i));
				}
				args.add(Integer.valueOf(percentage));
				args.add(constraint.getApproximations());

				if (nextConstraint != null) {
					//Instantiation of the next constraint by reflection
					Class nClass =
						((Constraint) constraint)
							.getNextConstraintConstructor(nextConstraint);
					Constructor[] constructeurs = nClass.getConstructors();
					Constructor constr = constructeurs[0];
					AbstractConstraint nextConstraintInst = null;
					try {
						nextConstraintInst =
							(AbstractConstraint) constr.newInstance(args
								.toArray());
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
					catch (InstantiationException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
					catch (IllegalAccessException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
					catch (InvocationTargetException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
					PalmConstraintPlugin plugX =
						(PalmConstraintPlugin) constraint.getPlugIn();
					PalmConstraintPlugin plugY =
						(PalmConstraintPlugin) nextConstraintInst.getPlugIn();
					plugY.setWeight(plugX.getWeight());
					re[1].add(nextConstraintInst);
				}

			}

			//Next, we treat the added constraints 
			//précédement withdrawn to the problem

			if (this.getRemovedConstraints().size() > 0) {
				Collections.sort(
					this.getRemovedConstraints(),
					new choco.palm.explain.BetterConstraintComparator());
				int selectC2 = 0;
				while (selectC2 != -1
						&& this.getRemovedConstraints().size() > 0) {
					System.out.println(MultilingualManager.getString(
						"CONST_CONTRADICTION",
						InteractiveRepair.class));
					for (int i = 0; i < this.getRemovedConstraints().size(); i++) {
						PalmConstraintPlugin plug3 =
							(PalmConstraintPlugin) ((AbstractConstraint) this
								.getRemovedConstraints()
								.get(i)).getPlugIn();
						System.out.println(i
								+ ":"
								+ ((Constraint) this
									.getRemovedConstraints()
									.get(i)).getSymbol()
								+ ((Constraint) this
									.getRemovedConstraints()
									.get(i)).getName() + ' ' + "weight "
								+ plug3.getWeight());
					}

					System.out.println(MultilingualManager.getString(
						"WHICH_TO_PUT_BACK",
						InteractiveRepair.class));

					try {
						String choix2 = keyBoard.readLine();
						selectC2 = Integer.parseInt(choix2);
					}
					catch (IOException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
					if (selectC2 != -1) {
						Constraint addedConstraint =
							(Constraint) this.getRemovedConstraints().get(
								selectC2);
						re[1].add(addedConstraint);
						this.getRemovedConstraints().remove(selectC2);
					}
				}
			}

		}

		//The research stops itself if no constraints
		// was withdrawn or added to the current problem
		if (re[0].size() == 0 && re[1].size() == 0) {
			throw new PalmContradiction();
		}
		return re;
	}
}
