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

/* File choco.test.search.NotEqualXCTest.java, last modified by Francois 14 sept. 2003 16:00:54 */

public class NotEqualXCTest extends TestCase {
	private static Logger logger = Logger
		.getLogger("choco.test.search.NotEqualXCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private Constraint c1;
	private Constraint c2;
	public NotEqualXCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		NotEqualXCTest.logger.fine("NotEqualXC Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeEnumIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.c1 = this.pb.neq(this.x, 3);
		this.c2 = this.pb.neq(this.y, 3);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.x = null;
		this.y = null;
		this.pb = null;
	}

	public void test1() {
		NotEqualXCTest.logger.finer("test1");
		try {
			this.pb.post(this.c1);
			this.pb.post(this.c2);
			this.pb.propagate();
			Assert.assertTrue(!this.x.canBeInstantiatedTo(3));
			Assert.assertTrue(this.y.canBeInstantiatedTo(3));
			this.x.remVal(2);
			this.x.remVal(1);
			this.pb.propagate();
			Assert.assertEquals(this.x.getInf(), 4);
			this.y.setMin(3);
			this.pb.propagate();
			Assert.assertEquals(this.y.getInf(), 4);
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}
}
