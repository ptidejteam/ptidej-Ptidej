package choco.test.search;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Problem;
import choco.Solver;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.QueensTest.java, last modified by flaburthe 12 janv. 2004 18:03:29 */

/**
 * A test placing n-queens on a chessboard, so that no two attack each other
 */
public class QueensTest extends TestCase {
	public static int nbQueensSolution[] = { 0, 0, 0, 0, 2, 10, 4, 40, 92, 352,
			724, 2680, 14200, 73712 };

	private final Logger logger = Logger
		.getLogger("choco.test.integer.BitSetIntDomainTest");
	private Problem pb;
	private choco.integer.IntVar[] queens;
	public QueensTest(final String name) {
		super(name);
	}

	private void queen0(final int n) {
		this.logger.finer("n queens, binary model, n=" + n);
		// create variables
		this.queens = new choco.integer.IntVar[n];
		for (int i = 0; i < n; i++) {
			this.queens[i] = this.pb.makeEnumIntVar("Q" + i, 1, n);
		}
		// diagonal constraints
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				final int k = j - i;
				this.pb.post(this.pb.neq(this.queens[i], this.queens[j]));
				this.pb.post(this.pb.neq(
					this.queens[i],
					this.pb.plus(this.queens[j], k)));
				this.pb.post(this.pb.neq(
					this.queens[i],
					this.pb.minus(this.queens[j], k)));
			}
		}
		final Solver s = this.pb.getSolver();
		this.pb.solve(true);
		if (n >= 4) {
			if (n <= 13) {
				Assert.assertEquals(Boolean.TRUE, this.pb.isFeasible());
				Assert.assertEquals(
					QueensTest.nbQueensSolution[n],
					s.getNbSolutions());
			}
		}
		else {
			Assert.assertEquals(Boolean.FALSE, this.pb.isFeasible());
		}
	}

	protected void setUp() {
		this.logger.fine("Queens Testing...");
		this.pb = new Problem();
	}

	protected void tearDown() {
		this.pb = null;
	}

	public void test0() {
		this.queen0(4);
	}
	public void test1() {
		this.queen0(5);
	}
	public void test2() {
		this.queen0(6);
	}
	public void test3() {
		this.queen0(7);
	}
	public void test4() {
		this.queen0(8);
	}
	public void test5() {
		this.queen0(9);
	}
	public void test6() {
		this.queen0(10);
	}
	//  public void test7() { queen0(11); }

}