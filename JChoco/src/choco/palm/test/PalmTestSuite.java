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

import junit.framework.Test;
import junit.framework.TestSuite;

public class PalmTestSuite extends TestSuite {
	public static Test suite() {
		final TestSuite test = new TestSuite();

		test.addTestSuite(LatinSquare.class);
		test.addTestSuite(MagicSquare.class);
		test.addTestSuite(NQueens.class);
		test.addTestSuite(PalmSolveTest.class);
		test.addTestSuite(SendMoney.class);

		return test;
	}
}
