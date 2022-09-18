//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.test;

import java.util.ArrayList;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;
import choco.palm.search.PalmUnsureExtend;
import choco.palm.search.PalmUnsureRepair;
import choco.palm.search.pathrepair.PathRepairAssignVar;
import choco.palm.search.pathrepair.PathRepairLearn;

/**
 * Created by IntelliJ IDEA.
 * User: rochart
 * Date: Jan 13, 2004
 * Time: 2:19:59 PM
 * To change this template use Options | File Templates.
 */
public class NQueens extends TestCase {
	public NQueens(final String name) {
		super(name);
	}

	public void solveNQueensOne(final boolean pathRepair) {
		final int NBQueen = 10;

		final PalmProblem myPb = new PalmProblem();

		final IntVar[] vars = new IntVar[NBQueen];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = myPb.makeEnumIntVar("C" + (i + 1), 1, NBQueen);
		}

		for (int i = 0; i < NBQueen; i++) {
			for (int j = i + 1; j < NBQueen; j++) {
				final int k = j - i;
				myPb.post(myPb.neq(vars[i], vars[j]));
				myPb.post(myPb.neq(vars[i], myPb.plus(vars[j], k)));
				myPb.post(myPb.neq(vars[j], myPb.plus(vars[i], k)));
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
				new PathRepairLearn(7, 1500, myPb.getPalmSolver()));
			myPb.getPalmSolver().attachPalmBranchings(l);
			myPb.solve(false);
		}

		if (myPb.isFeasible() == Boolean.TRUE) {
			for (int x = 0; x < NBQueen; x++) {
				for (int y = x + 1; y < NBQueen; y++) {
					final int k = y - x;
					Assert.assertTrue(vars[x].getValue() != vars[y].getValue());
					Assert.assertTrue(vars[x].getValue() != vars[y].getValue()
							+ k);
					Assert.assertTrue(vars[y].getValue() != vars[x].getValue()
							+ k);
				}
			}
		}
		if (pathRepair) {
			System.out.println("DecisionRepair : NQueens : " + " "
					+ myPb.isFeasible() + " EXT : "
					+ myPb.getGlobalStatistics(PalmProblem.EXT) + " RLX : "
					+ myPb.getGlobalStatistics(PalmProblem.RLX) + " -- Time : "
					+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
		}
		else {
			System.out.println("MAC-DBT : NQueens : " + " " + myPb.isFeasible()
					+ " EXT : " + myPb.getGlobalStatistics(PalmProblem.EXT)
					+ " RLX : " + myPb.getGlobalStatistics(PalmProblem.RLX)
					+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
					+ " ms.");
		}
	}

	public void testNQueensAll() {
		// A priori fonctionne jusqu'a au moins 13
		// - 13 : plus de 4000 sec (plus d'une heure)
		// - 12 : 1013 sec
		// - 11 : 180 sec
		// - 10 : 38 sec
		// - 9 : 5 sec
		final int NBQueen = 8;
		final int[] NBSols =
			new int[] { 0, 0, 0, 2, 10, 4, 40, 92, 352, 724, 2680, 14200, 73712 };

		final PalmProblem myPb = new PalmProblem();

		final IntVar[] vars = new IntVar[NBQueen];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = myPb.makeEnumIntVar("C" + (i + 1), 1, NBQueen);
		}

		for (int i = 0; i < NBQueen; i++) {
			for (int j = i + 1; j < NBQueen; j++) {
				final int k = j - i;
				myPb.post(myPb.neq(vars[i], vars[j]));
				myPb.post(myPb.neq(vars[i], myPb.plus(vars[j], k)));
				myPb.post(myPb.neq(vars[j], myPb.plus(vars[i], k)));
			}
		}

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		int falseSoluce = 0;

		/*PrintWriter pw;
		try {
		    File solu = new File("Solutions.txt");
		    FileOutputStream stream = new FileOutputStream(solu);
		    pw = new PrintWriter(stream);
		} catch(Exception e) {
		    System.err.println("File opening problem");
		    pw = null;
		} */

		for (int i = 0; i < solutions.length; i++) {
			final PalmSolution solution = solutions[i];
			for (int x = 0; x < NBQueen; x++) {
				for (int y = x + 1; y < NBQueen; y++) {
					final int k = y - x;
					if (solution.getValue(x) == solution.getValue(y)
							|| solution.getValue(x) == solution.getValue(y) + k
							|| solution.getValue(y) == solution.getValue(x) + k) {
						System.out.println("Fausse solution : ");
						System.out.println(solution);
						falseSoluce++;
					}
				}
				//pw.println(solution);
			}
		}
		//pw.close();;

		System.out.println(falseSoluce + " fausses solutions");
		System.out.println("NBQueen(" + NBQueen + ") Solutions : "
				+ solutions.length + "  Relax : "
				+ myPb.getGlobalStatistics(PalmProblem.RLX) + " -- Time : "
				+ myPb.getGlobalStatistics(PalmProblem.CPU) + " ms.");
		Assert.assertEquals(NBSols[NBQueen - 1], solutions.length);
	}

	public void testNQueensOneDR() {
		this.solveNQueensOne(true);
	}

	public void testNQueensOneMACDBT() {
		this.solveNQueensOne(false);
	}

}
