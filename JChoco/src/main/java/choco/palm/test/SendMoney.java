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

import org.junit.Assert;
import junit.framework.TestCase;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;

/**
 * Created by IntelliJ IDEA.
 * User: rochart
 * Date: Jan 13, 2004
 * Time: 10:22:26 AM
 * To change this template use Options | File Templates.
 */
public class SendMoney extends TestCase {
	public SendMoney(final String name) {
		super(name);
	}

	public void testSendMoneyAll() {
		final PalmProblem myPb = new PalmProblem();

		final IntVar s = myPb.makeEnumIntVar("S", 1, 9);
		final IntVar e = myPb.makeEnumIntVar("E", 0, 9);
		final IntVar n = myPb.makeEnumIntVar("N", 0, 9);
		final IntVar d = myPb.makeEnumIntVar("D", 0, 9);
		final IntVar m = myPb.makeEnumIntVar("M", 1, 9);
		final IntVar o = myPb.makeEnumIntVar("O", 0, 9);
		final IntVar r = myPb.makeEnumIntVar("R", 0, 9);
		final IntVar y = myPb.makeEnumIntVar("Y", 0, 9);

		final IntVar[] letters = new IntVar[] { s, e, n, d, m, o, r, y };

		final IntVar word1 = myPb.makeBoundIntVar("Send", 0, 1000000);
		final IntVar word2 = myPb.makeBoundIntVar("More", 0, 1000000);
		final IntVar word3 = myPb.makeBoundIntVar("Money", 0, 1000000);

		// Alldiff sur les variables
		for (int i = 1; i < 8; i++) {
			for (int j = 0; j < i; j++) {
				myPb.post(myPb.neq(letters[i], letters[j]));
			}
		}

		// Maintien des mots en fonction des lettres
		myPb.post(myPb.eq(
			word1,
			myPb.scalar(new int[] { 1000, 100, 10, 1 }, new IntVar[] { s, e, n,
					d })));
		myPb.post(myPb.eq(
			word2,
			myPb.scalar(new int[] { 1000, 100, 10, 1 }, new IntVar[] { m, o, r,
					e })));
		myPb.post(myPb.eq(
			word3,
			myPb.scalar(new int[] { 10000, 1000, 100, 10, 1 }, new IntVar[] {
					m, o, n, e, y })));

		// Contrainte d'addition
		myPb.post(myPb.eq(word3, myPb.plus(word1, word2)));

		myPb.solve(true);

		PalmSolution[] solutions;
		solutions = myPb.getPalmSolver().getSolutions();
		/*for (int i = 0; i < solutions.length; i++) {
		    PalmSolution solution = solutions[i];
		    System.out.println(solution);
		}  */

		Assert.assertEquals(1, solutions.length);
	}

	public void testSendMoneyOne() {
		final PalmProblem myPb = new PalmProblem();

		final IntVar s = myPb.makeEnumIntVar("S", 1, 9);
		final IntVar e = myPb.makeEnumIntVar("E", 0, 9);
		final IntVar n = myPb.makeEnumIntVar("N", 0, 9);
		final IntVar d = myPb.makeEnumIntVar("D", 0, 9);
		final IntVar m = myPb.makeEnumIntVar("M", 1, 9);
		final IntVar o = myPb.makeEnumIntVar("O", 0, 9);
		final IntVar r = myPb.makeEnumIntVar("R", 0, 9);
		final IntVar y = myPb.makeEnumIntVar("Y", 0, 9);

		final IntVar[] letters = new IntVar[] { s, e, n, d, m, o, r, y };

		final IntVar word1 = myPb.makeBoundIntVar("Send", 0, 1000000);
		final IntVar word2 = myPb.makeBoundIntVar("More", 0, 1000000);
		final IntVar word3 = myPb.makeBoundIntVar("Money", 0, 1000000);

		// Alldiff sur les variables
		for (int i = 1; i < 8; i++) {
			for (int j = 0; j < i; j++) {
				myPb.post(myPb.neq(letters[i], letters[j]));
			}
		}

		// Maintien des mots en fonction des lettres
		myPb.post(myPb.eq(
			word1,
			myPb.scalar(new int[] { 1000, 100, 10, 1 }, new IntVar[] { s, e, n,
					d })));
		myPb.post(myPb.eq(
			word2,
			myPb.scalar(new int[] { 1000, 100, 10, 1 }, new IntVar[] { m, o, r,
					e })));
		myPb.post(myPb.eq(
			word3,
			myPb.scalar(new int[] { 10000, 1000, 100, 10, 1 }, new IntVar[] {
					m, o, n, e, y })));

		// Contrainte d'addition
		myPb.post(myPb.eq(word3, myPb.plus(word1, word2)));

		myPb.solve(false);

		Assert.assertEquals(
			word3.getValue(),
			word2.getValue() + word1.getValue());
		Assert.assertEquals(
			word2.getValue(),
			1000 * m.getValue() + 100 * o.getValue() + 10 * r.getValue()
					+ e.getValue());
		/*System.out.print("Solution : ");
		for (int i = 0; i < letters.length; i++) {
		    IntVar letter = letters[i];
		    System.out.println(letter + "->" + letter.getValue() + " ");
		}*/
	}
}
