package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.LessOrEqualXC.java, last modified by Francois 11 sept. 2003 00:46:25 */

public class LessOrEqualXCTest extends TestCase {
	private static Logger logger = Logger
		.getLogger("choco.test.search.LessOrEqualXCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private Constraint c1;
	private Constraint c2;
	public LessOrEqualXCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		LessOrEqualXCTest.logger.finer("LessOrEqualXC Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.c1 = this.pb.leq(this.x, 1);
		this.c2 = this.pb.leq(this.y, 2);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.x = null;
		this.y = null;
		this.pb = null;
	}

	public void test1() {
		LessOrEqualXCTest.logger.finer("test1");
		try {
			this.pb.post(this.c1);
			this.pb.post(this.c2);
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		Assert.assertTrue(this.x.isInstantiated());
		Assert.assertTrue(!this.y.isInstantiated());
		Assert.assertEquals(1, this.x.getSup());
		Assert.assertEquals(2, this.y.getSup());
		LessOrEqualXCTest.logger.finest("domains OK after first propagate");
		Assert.assertTrue(this.c1.isSatisfied());
		Assert.assertEquals(this.c2.isEntailed(), Boolean.TRUE);
	}
}
