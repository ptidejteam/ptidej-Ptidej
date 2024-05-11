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
import java.util.Collections;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.constraints.PalmAssignment;
import choco.palm.integer.constraints.PalmElt;
import choco.palm.integer.constraints.PalmElt2D;
import choco.palm.integer.constraints.PalmGreaterOrEqualXYC;
import choco.palm.prop.PalmEngine;
import choco.palm.search.PalmSolution;

public class PalmSolveTest extends TestCase {
	public PalmSolveTest(final String name) {
		super(name);
	}

	public void testArithmetic() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeEnumIntVar("X", 1, 5);
		final IntVar y = myPb.makeBoundIntVar("Y", 1, 5);
		final IntVar z = myPb.makeEnumIntVar("Z", 1, 5);
		final Constraint A = myPb.geq(x, myPb.plus(y, 1)); //new PalmGreaterOrEqualXYC(x,y,1);
		final Constraint B = myPb.geq(y, myPb.plus(z, 1)); //new PalmGreaterOrEqualXYC(y,z,1);
		myPb.post(A);
		myPb.post(B);

		A.setPassive();
		A.setActive();

		try {
			myPb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		//System.out.println("X : " + x.getInf() + " - > "  + x.getSup());
		//System.out.println("Y : " + y.getInf() + " - > "  + y.getSup());
		//System.out.println("Z : " + z.getInf() + " - > "  + z.getSup());
		Assert.assertEquals(3, x.getInf());
		Assert.assertEquals(5, x.getSup());
		Assert.assertEquals(2, y.getInf());
		Assert.assertEquals(4, y.getSup());
		Assert.assertEquals(1, z.getInf());
		Assert.assertEquals(3, z.getSup());

		final Explanation expl = myPb.makeExplanation();
		((PalmIntVar) x).self_explain(PalmIntDomain.INF, expl);
		//System.out.println("Expl de X.inf : " + expl);
		Assert.assertTrue(expl.contains(A));
		Assert.assertTrue(expl.contains(B));

		((PalmEngine) myPb.getPropagationEngine()).remove(A);

		try {
			myPb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}

		//System.out.println("X : " + x.getInf() + " - > "  + x.getSup());
		//System.out.println("Y : " + y.getInf() + " - > "  + y.getSup());
		//System.out.println("Z : " + z.getInf() + " - > "  + z.getSup());
		Assert.assertEquals(1, x.getInf());
		Assert.assertEquals(5, x.getSup());
		Assert.assertEquals(2, y.getInf());
		Assert.assertEquals(5, y.getSup());
		Assert.assertEquals(1, z.getInf());
		Assert.assertEquals(4, z.getSup());
	}

	public void testArithmetic2() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeBoundIntVar("X", 1, 5);
		final IntVar y = myPb.makeBoundIntVar("Y", 1, 5);
		final IntVar z = myPb.makeBoundIntVar("Z", 1, 5);
		final Constraint A = myPb.geq(x, myPb.plus(y, 1)); //new PalmGreaterOrEqualXYC(x,y,1);
		final Constraint B = myPb.geq(y, myPb.plus(z, 1)); //new PalmGreaterOrEqualXYC(y,z,1);
		myPb.post(A);
		myPb.post(B);

		A.setPassive();

		try {
			myPb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		//System.out.println("X : " + x.getInf() + " - > "  + x.getSup());
		Assert.assertEquals(1, x.getInf());
		Assert.assertEquals(5, x.getSup());
		//System.out.println("Y : " + y.getInf() + " - > "  + y.getSup());
		Assert.assertEquals(2, y.getInf());
		Assert.assertEquals(5, y.getSup());
		//System.out.println("Z : " + z.getInf() + " - > "  + z.getSup());
		Assert.assertEquals(1, z.getInf());
		Assert.assertEquals(4, z.getSup());
	}

	public void testBranchAndBoundMaximize() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeEnumIntVar("X", 1, 10);
		final IntVar y = myPb.makeEnumIntVar("Y", 1, 10);
		final IntVar z = myPb.makeEnumIntVar("Z", 1, 10);
		final IntVar obj = myPb.makeEnumIntVar("OBJ", 1, 100);
		myPb.post(myPb.geq(x, z));
		myPb.post(myPb.neq(x, z));

		myPb.post(myPb.geq(z, y));
		myPb.post(myPb.neq(z, y));

		myPb.post(myPb.neq(x, y));
		myPb.post(myPb.eq(
			myPb.scalar(new IntVar[] { x, y, z }, new int[] { 1, 1, -1 }),
			obj));

		myPb.maximize(obj);
		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		for (int i = 0; i < solutions.length; i++) {
			final PalmSolution solution = solutions[i];
			System.out.println(solution);
		}

		System.out.println("nb Sol " + solutions.length);
		/*System.out.println("x " + x.getValue() + " y " + y.getValue() + " z " + z.getValue());
		*/
		Assert.assertEquals(9, solutions[solutions.length - 1].getValue(3));
	}

	public void testComparator() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeBoundIntVar("X", 1, 5);
		final IntVar y = myPb.makeBoundIntVar("Y", 1, 5);
		final IntVar z = myPb.makeBoundIntVar("Z", 1, 5);
		final Constraint A = new PalmGreaterOrEqualXYC(x, y, 1);
		final Constraint B = new PalmGreaterOrEqualXYC(y, z, 1);
		final Constraint C = new PalmAssignment(x, 2);
		myPb.post(A);
		myPb.post(B);
		myPb.post(C, 0);

		final ArrayList list = new ArrayList();
		list.add(B);
		list.add(C);
		list.add(A);

		Assert.assertEquals(C, Collections.min(
			list,
			new choco.palm.explain.BetterConstraintComparator()));
		Assert.assertEquals(A, Collections.max(
			list,
			new choco.palm.explain.BetterConstraintComparator()));
	}

	public void testElt() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar index = myPb.makeEnumIntVar("IndexVar", 0, 4);
		final IntVar value = myPb.makeEnumIntVar("ValueVar", 1, 10);
		final Constraint A =
			new PalmElt(index, value, 0, new int[] { 0, 2, 4, 5, 12 }); //TODO : API pour poser la contrainte
		myPb.post(A);

		try {
			myPb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		System.out.println("indexVar : " + index.getInf() + " - "
				+ index.getSup());
		System.out.println("valueVar : " + value.getInf() + " - "
				+ value.getSup());
		Assert.assertEquals(1, index.getInf());
		Assert.assertEquals(3, index.getSup());
		Assert.assertEquals(5, value.getSup());
		Assert.assertEquals(2, value.getInf());
		Assert.assertTrue(!value.canBeInstantiatedTo(3));

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		for (int i = 0; i < solutions.length; i++) {
			final PalmSolution solution = solutions[i];
			System.out.println(solution);
		}
		System.out.println("Element Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(3, solutions.length);
	}

	public void testElt2() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar index = myPb.makeEnumIntVar("IndexVar", 0, 3);
		final IntVar value1 = myPb.makeEnumIntVar("ValueVar1", 0, 1);
		final IntVar value2 = myPb.makeEnumIntVar("ValueVar2", 0, 1);
		final IntVar value3 = myPb.makeEnumIntVar("ValueVar3", 0, 1);
		final IntVar value4 = myPb.makeEnumIntVar("ValueVar4", 0, 1);
		final Constraint A =
			new PalmElt(index, value1, 0, new int[] { 1, 0, 0, 0 });
		final Constraint B =
			new PalmElt(index, value2, 0, new int[] { 0, 1, 0, 0 });
		final Constraint C =
			new PalmElt(index, value3, 0, new int[] { 0, 0, 1, 0 });
		final Constraint D =
			new PalmElt(index, value4, 0, new int[] { 0, 0, 0, 1 });
		myPb.post(A);
		myPb.post(B);
		myPb.post(C);
		myPb.post(D);

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		for (int i = 0; i < solutions.length; i++) {
			final PalmSolution solution = solutions[i];
			System.out.println(solution);
		}

		System.out.println("Element Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(4, solutions.length);
	}

	public void testElt2D() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar index1 = myPb.makeEnumIntVar("IndexVar1", 0, 2);
		final IntVar index2 = myPb.makeEnumIntVar("IndexVar2", 0, 3);
		final IntVar value = myPb.makeEnumIntVar("ValueVar", 0, 10);
		final Constraint A =
			new PalmElt2D(index1, index2, value, new int[][] { { 1, 2, 0 },
					{ 12, 1, 5 }, { 0, 6, 1 } }, 3, 3); //TODO : API pour poser la contrainte
		myPb.post(A);

		try {
			myPb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}

		//assertEquals(index2.getSup(),2);
		System.out.println("sup : " + value.getSup());
		Assert.assertEquals(6, value.getSup());
		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		for (int i = 0; i < solutions.length; i++) {
			final PalmSolution solution = solutions[i];
			System.out.println(solution);
		}
		System.out.println("Element2D Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(8, solutions.length);
	}

	public void testLinComb() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar w = myPb.makeEnumIntVar("W", 1, 5);
		final IntVar x = myPb.makeBoundIntVar("X", 1, 5);
		final IntVar y = myPb.makeEnumIntVar("Y", 1, 5);
		final IntVar z = myPb.makeBoundIntVar("Z", 1, 5);
		final Constraint A =
			myPb.eq(
				myPb.scalar(new int[] { 1, 1, -1, -1 }, new IntVar[] { w, x, y,
						z }),
				1);
		myPb.post(A);

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		} */

		System.out.println("Lin Comb Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(80, solutions.length);
	}

	public void testSolveArithmetic() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeBoundIntVar("X", 1, 5);
		final IntVar y = myPb.makeBoundIntVar("Y", 1, 5);
		final IntVar z = myPb.makeBoundIntVar("Z", 1, 5);
		final Constraint A = myPb.geq(x, myPb.plus(y, 1)); //new PalmGreaterOrEqualXYC(x,y,1);
		final Constraint B = myPb.geq(y, myPb.plus(z, 1)); //new PalmGreaterOrEqualXYC(y,z,1);
		myPb.post(A);
		myPb.post(B);

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		}*/

		System.out.println("Solve Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(10, solutions.length);
	}

	public void testSolveArithmetic2() {
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeEnumIntVar("X", 1, 5);
		final IntVar y = myPb.makeBoundIntVar("Y", 1, 5);
		final IntVar z = myPb.makeEnumIntVar("Z", 1, 5);
		final Constraint A = myPb.eq(x, y); //new PalmEqualXYC(x,y,0);
		final Constraint B = myPb.eq(y, z); //new PalmEqualXYC(y,z,0);
		myPb.post(A);
		myPb.post(B);

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		} */

		System.out.println("Solve2 Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(5, solutions.length);
	}

	public void testSolveArithmetic3() {
		//System.out.println("NOUS");
		final PalmProblem myPb = new PalmProblem();
		final IntVar x = myPb.makeEnumIntVar("X", 1, 5);
		final IntVar y = myPb.makeEnumIntVar("Y", 1, 5);
		final IntVar z = myPb.makeBoundIntVar("Z", 1, 5);
		final Constraint A = myPb.neq(x, y);
		final Constraint B = myPb.neq(y, z);//new PalmNotEqualXYC(y,z,0);
		final Constraint C = myPb.neq(x, z);//new PalmNotEqualXYC(x,z,0);
		myPb.post(A);
		myPb.post(B);
		myPb.post(C);

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		} */

		System.out.println("Solve3 Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
		Assert.assertEquals(60, solutions.length);
	}

	public void testSolveArithmeticBench() {
		System.out.println("Test Arithm Enum/Bound");
		final PalmProblem myPb = new PalmProblem();
		final int n = 5;
		final IntVar[] vars = new IntVar[n];
		for (int i = 0; i < n; i++) {
			//IIntVar var = vars[i];
			if (i % 2 == 0) {
				vars[i] = myPb.makeBoundIntVar("X" + i, 1, n + 3);
			}
			else {
				vars[i] = myPb.makeEnumIntVar("X" + i, 1, n + 3);
			}
		}
		for (int i = 0; i < n - 1; i++) {
			final IntVar var1 = vars[i];
			final IntVar var2 = vars[i + 1];
			myPb.post(myPb.geq(var1, myPb.plus(var2, 1))); //new PalmGreaterOrEqualXYC(var1, var2, 1));
		}

		myPb.solve(true);
		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		}*/
		Assert.assertEquals(56, solutions.length);
		System.out.println("Arith Bench -- Solutions : " + solutions.length
				+ " Relax : " + myPb.getGlobalStatistics(PalmProblem.RLX)
				+ " -- Time : " + myPb.getGlobalStatistics(PalmProblem.CPU)
				+ " ms.");
	}

}
