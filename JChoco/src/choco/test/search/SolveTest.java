// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.SolveTest.java, last modified by Francois 2 déc. 2003 23:49:19 */
package choco.test.search;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Problem;

public class SolveTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.SolveTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	public SolveTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("StoredInt Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 0, 5);
		this.y = this.pb.makeBoundIntVar("Y", 0, 1);
	}

	protected void tearDown() {
		this.x = null;
		this.y = null;
		this.pb = null;
	}

	/**
	 * test the solution count for an infeasible problem
	 */
	public void test1() {
		this.logger.finer("test1");
		this.pb.post(this.pb.eq(this.x, 2));
		this.pb.post(this.pb.eq(this.x, 3));
		this.pb.solve(false);
		Assert.assertEquals(this.pb.getSolver().getNbSolutions(), 0);
		Assert.assertEquals(this.pb.isFeasible(), Boolean.FALSE);
	}

	/**
	 * test the solution count for instantiated problem
	 */
	public void test2() {
		this.logger.finer("test2");
		this.pb.post(this.pb.eq(this.x, 2));
		this.pb.post(this.pb.eq(this.y, 1));
		this.pb.solve(true);
		Assert.assertEquals(this.pb.isFeasible(), Boolean.TRUE);
		Assert.assertEquals(this.pb.getSolver().getNbSolutions(), 1);
	}

	/**
	 * test the solution count for a simple one-variable problem
	 */
	public void test3() {
		this.logger.finer("test3");
		this.pb.post(this.pb.eq(this.y, 1));
		this.pb.solve(true);
		Assert.assertEquals(this.pb.isFeasible(), Boolean.TRUE);
		Assert.assertEquals(this.pb.getSolver().getNbSolutions(), 6);
	}

	/**
	 * test the solution count for a simple two-variable problem
	 */
	public void test4() {
		this.logger.finer("test4");
		this.pb.solve(true);
		Assert.assertEquals(this.pb.isFeasible(), Boolean.TRUE);
		Assert.assertEquals(this.pb.getSolver().getNbSolutions(), 12);
	}
}
