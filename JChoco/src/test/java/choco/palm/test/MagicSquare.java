//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.test;

import java.util.ArrayList;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmUnsureExtend;
import choco.palm.search.PalmUnsureRepair;
import choco.palm.search.pathrepair.PathRepairAssignVar;
import choco.palm.search.pathrepair.PathRepairLearn;

public class MagicSquare extends TestCase {
	public MagicSquare(final String name) {
		super(name);
	}

	public void square(final int n, final boolean help, final boolean pathRepair) {
		final int magic = n * (n * n + 1) / 2;

		final PalmProblem myPb = new PalmProblem();
		//myPb.getPalmSolver().attachPalmState(new TraceState(new GenericExplanation(myPb)));

		final IntVar[] vars = new IntVar[n * n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				vars[i * n + j] =
					myPb.makeEnumIntVar("C" + i + "_" + j, 1, n * n);
			}
		}
		final IntVar sum = myPb.makeEnumIntVar("S", 1, n * n * (n * n + 1) / 2);

		try {
			if (help) {
				myPb.post(myPb.eq(sum, magic));
			}

			for (int i = 0; i < n * n; i++) {
				for (int j = 0; j < i; j++) {
					myPb.post(myPb.neq(vars[i], vars[j]));
				}
			}

			final int[] coeffs = new int[n];
			for (int i = 0; i < n; i++) {
				coeffs[i] = 1;
			}

			for (int i = 0; i < n; i++) {
				final IntVar[] col = new IntVar[n];
				final IntVar[] row = new IntVar[n];

				for (int j = 0; j < n; j++) {
					col[j] = vars[i * n + j];
					row[j] = vars[j * n + i];
				}

				myPb.post(myPb.eq(myPb.scalar(coeffs, row), sum));
				myPb.post(myPb.eq(myPb.scalar(coeffs, col), sum));
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
					new PathRepairLearn(10, 10000, myPb.getPalmSolver()));
				myPb.getPalmSolver().attachPalmBranchings(l);
				myPb.solve(false);
			}

			for (int i = 0; i < vars.length; i++) {
				final IntVar var = vars[i];
				System.out.println(var + ": " + var.getValue());
			}

			final int[] col = new int[n];
			final int[] lig = new int[n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					col[i] += vars[j + i * n].getValue();
					lig[i] += vars[j * n + i].getValue();
				}
			}
			for (int i = 0; i < n - 1; i++) {
				Assert.assertEquals(col[i], col[i + 1]); // toutes les lignes sont egales
				Assert.assertEquals(lig[i], lig[i + 1]); // les colonnes
			}
			Assert.assertEquals(col[1], lig[1]); // il suffit d'une ligne egale e une colonne
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i != j) {
						Assert.assertTrue(vars[i].getValue() != vars[j]
							.getValue());
					}
				}
			}

			myPb.getPalmSolver().getSolutions();
			if (pathRepair) {
				System.out.println("DecisionRepair : MAGICSQUARE(" + n
						+ ") Solutions : " + myPb.isFeasible() + " Ext: "
						+ myPb.getGlobalStatistics(PalmProblem.EXT)
						+ "  Relax : "
						+ myPb.getGlobalStatistics(PalmProblem.RLX)
						+ " -- Time : "
						+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
			}
			else {
				System.out.println("MAC-DBT : MAGICSQUARE(" + n
						+ ") Solutions : " + myPb.isFeasible() + " Ext: "
						+ myPb.getGlobalStatistics(PalmProblem.EXT)
						+ "  Relax : "
						+ myPb.getGlobalStatistics(PalmProblem.RLX)
						+ " -- Time : "
						+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	public void testSquare() {
		//square(3, false, false);
		//square(3, false, true);
		//square(4,true,true);
		this.square(5, true, false);
		//square(5, true, true);
		//square(5, true, true);

	}
}
