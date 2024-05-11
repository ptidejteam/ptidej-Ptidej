// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.GreaterOrEqualXCTest.java, last modified by Francois 23 aoet 2003:17:40:29 */
package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;

public class GreaterOrEqualXCTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.GreaterOrEqualXCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private Constraint c1;
	private Constraint c2;
	public GreaterOrEqualXCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("GreaterOrEqualXCTest Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.c1 = this.pb.geq(this.x, 1);
		this.c2 = this.pb.geq(this.y, 2);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.x = null;
		this.y = null;
		this.pb = null;
	}

	public void test1() {
		this.logger.finer("test1");
		try {
			this.pb.post(this.c1);
			this.pb.post(this.c2);
			this.pb.propagate();
			Assert.assertTrue(!this.x.isInstantiated());
			Assert.assertTrue(!this.y.isInstantiated());
			Assert.assertEquals(1, this.x.getInf());
			Assert.assertEquals(2, this.y.getInf());
			this.logger.finest("domains OK after first propagate");
			Assert.assertTrue(this.c1.isSatisfied());
			Assert.assertTrue(this.c2.isSatisfied());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}
}
