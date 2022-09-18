package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.constraints.GreaterOrEqualXYC;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.GreaterOrEqualXYCTest.java, last modified by Francois 11 sept. 2003 00:36:20 */

public class GreaterOrEqualXYCTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.GreaterOrEqualXYCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private choco.integer.IntVar z;
	private Constraint c1;
	private Constraint c2;
	public GreaterOrEqualXYCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("GreaterOrEqualXYCTest Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.z = this.pb.makeBoundIntVar("Y", 1, 5);
		this.c1 = new GreaterOrEqualXYC(this.x, this.y, 1);
		this.c2 = new GreaterOrEqualXYC(this.y, this.z, 2);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.x = null;
		this.y = null;
		this.z = null;
		this.pb = null;
	}

	public void test1() {
		this.logger.finer("test2");
		try {
			this.pb.post(this.c1);
			this.pb.post(this.c2);
			this.pb.propagate();
			Assert.assertTrue(!this.x.isInstantiated());
			Assert.assertTrue(!this.y.isInstantiated());
			Assert.assertEquals(4, this.x.getInf());
			Assert.assertEquals(3, this.y.getInf());
			Assert.assertEquals(1, this.z.getInf());
			Assert.assertEquals(5, this.x.getSup());
			Assert.assertEquals(4, this.y.getSup());
			Assert.assertEquals(2, this.z.getSup());
			this.logger.finest("domains OK after first propagate");
			this.z.setVal(2);
			this.pb.propagate();
			Assert.assertTrue(this.x.isInstantiated());
			Assert.assertTrue(this.y.isInstantiated());
			Assert.assertTrue(this.z.isInstantiated());
			Assert.assertEquals(5, this.x.getValue());
			Assert.assertEquals(4, this.y.getValue());
			Assert.assertEquals(2, this.z.getValue());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}
}
