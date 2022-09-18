//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Fran?ois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.test;

import java.util.ArrayList;
import java.util.BitSet;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;
import choco.palm.search.PalmUnsureExtend;
import choco.palm.search.PalmUnsureRepair;
import choco.palm.search.pathrepair.PathRepairAssignVar;
import choco.palm.search.pathrepair.PathRepairLearn;

public class LatinSquare extends TestCase {
	public LatinSquare(final String name) {
		super(name);
	}

	public int[][] affect(final int n, final int toComplete) {
		final int[][] ret = new int[toComplete][2];

		final BitSet init = new BitSet();
		for (int i = 0; i < n; i++) {
			init.set(i);
		}

		final BitSet[][] vars = new BitSet[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				vars[x][y] = new BitSet();
				vars[x][y].or(init);
			}
		}

		int affected = 0;
		while (affected < toComplete) {
			final int x = (int) Math.floor(Math.random() * n);
			final int y = (int) Math.floor(Math.random() * n);
			final int l = vars[x][y].cardinality();
			if (l > 1) {
				final int pos = (int) Math.floor(Math.random() * l);
				int val = -1;
				for (int i = 0; i <= pos; i++) {
					val = vars[x][y].nextSetBit(val + 1);
				}

				vars[x][y].clear();
				vars[x][y].set(val);
				for (int i = 0; i < n; i++) {
					if (i != y) {
						vars[x][i].clear(val);
					}
					if (i != x) {
						vars[i][y].clear(val);
					}
				}

				ret[affected][0] = x * n + y;
				ret[affected][1] = val + 1;
				affected++;
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (vars[x][y].cardinality() == 0) {
					return null;
				}
			}
		}

		return ret;
	}

	public void solveCompleteLatinSquare(final boolean pathRepair) {
		final int n = 11;

		int[][] preAffect = null;
		final int preAffected = n * n * 42 / 100;

		while (preAffect == null) {
			preAffect = this.affect(n, preAffected);
		}

		// Problem
		final PalmProblem myPb = new PalmProblem();

		// Variables
		final IntVar[] vars = new IntVar[n * n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				vars[i * n + j] = myPb.makeEnumIntVar("C" + i + "_" + j, 1, n);
			}
		}

		for (int i = 0; i < preAffected; i++) {
			//System.out.println("Affect " + vars[preAffect[i][0]] + "  to  " + preAffect[i][1]);
			myPb.post(myPb.eq(vars[preAffect[i][0]], preAffect[i][1]));
		}

		// Constraints
		for (int i = 0; i < n; i++) {
			for (int x = 0; x < n; x++) {
				for (int y = 0; y < x; y++) {
					myPb.post(myPb.neq(vars[i * n + y], vars[i * n + x]));
					myPb.post(myPb.neq(vars[y * n + i], vars[x * n + i]));
				}
			}
		}

		if (!pathRepair) {
			myPb.solve(false);
		}
		else {
			final ArrayList l = new ArrayList();
			l.add(new PathRepairAssignVar());
			myPb.getPalmSolver().attachPalmExtend(new PalmUnsureExtend());
			myPb.getPalmSolver().attachPalmRepair(new PalmUnsureRepair());
			myPb.getPalmSolver().attachPalmLearn(
				new PathRepairLearn(10, 100000, myPb.getPalmSolver()));
			myPb.getPalmSolver().attachPalmBranchings(l);
			myPb.solve(false);
		}

		if (myPb.isFeasible() == Boolean.TRUE) {
			for (int i = 0; i < n; i++) {
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < x; y++) {
						Assert.assertTrue(vars[i * n + y].getValue() != vars[i
								* n + x].getValue());
						Assert.assertTrue(vars[y * n + i].getValue() != vars[x
								* n + i].getValue());
					}
				}
			}
			//for (int i = 0; i < vars.length; i++) {
			//    IntVar var = vars[i];
			//    System.out.println(var + ": " + var.getValue());
			//  }
		}

		if (pathRepair) {
			System.out
				.println("DecisionRepair : LatinSquare Completion Relax : "
						+ " " + myPb.isFeasible() + " EXT : "
						+ myPb.getGlobalStatistics(PalmProblem.EXT) + " RLX : "
						+ myPb.getGlobalStatistics(PalmProblem.RLX)
						+ " -- Time : "
						+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
		}
		else {
			System.out.println("MAC-DBT : LatinSquare Completion Relax : "
					+ " " + myPb.isFeasible() + " EXT : "
					+ myPb.getGlobalStatistics(PalmProblem.EXT) + " RLX : "
					+ myPb.getGlobalStatistics(PalmProblem.RLX) + " -- Time : "
					+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
		}
	}

	public void testLatinSquare() {
		// Toutes les solutions de n=5 en 90 sec  (161280 solutions)
		final int n = 4;
		final int[] soluces = new int[] { 1, 2, 12, 576, 161280 };

		// Problem
		final PalmProblem myPb = new PalmProblem();

		// Variables
		final IntVar[] vars = new IntVar[n * n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				vars[i * n + j] = myPb.makeEnumIntVar("C" + i + "_" + j, 1, n);
			}
		}

		// Constraints
		for (int i = 0; i < n; i++) {
			for (int x = 0; x < n; x++) {
				for (int y = 0; y < x; y++) {
					myPb.post(myPb.neq(vars[i * n + y], vars[i * n + x]));
					myPb.post(myPb.neq(vars[y * n + i], vars[x * n + i]));
				}
			}
		}

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		Assert.assertEquals(soluces[n - 1], solutions.length);
		System.out.println("LatinSquare Solutions : " + solutions.length
				+ "  Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
	}

	public void ttestCompleteLatinSquareDR() { // Non active par defaut, car peut prendre du temps !
		this.solveCompleteLatinSquare(true);
	}

	public void ttestCompleteLatinSquareMACDBT() { // Non active par defaut, car peut prendre du temps !
		this.solveCompleteLatinSquare(false);
	}

}
