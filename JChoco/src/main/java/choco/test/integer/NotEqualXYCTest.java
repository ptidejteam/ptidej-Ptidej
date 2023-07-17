// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.NotEqualXYCTest.java, last modified by Francois 21 sept. 2003 10:59:44 */

package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.constraints.NotEqualXYC;

public class NotEqualXYCTest extends TestCase {
	private static Logger logger = Logger
		.getLogger("choco.test.search.NotEqualXCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private choco.integer.IntVar z;
	private Constraint c1;
	private Constraint c2;
	public NotEqualXYCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		NotEqualXYCTest.logger.fine("NotEqualXYC Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeEnumIntVar("X", 1, 5);
		this.y = this.pb.makeEnumIntVar("Y", 1, 5);
		this.z = this.pb.makeEnumIntVar("Z", 1, 5);
		this.c1 = new NotEqualXYC(this.x, this.y, -2);
		this.c2 = new NotEqualXYC(this.y, this.z, 1);
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
		NotEqualXYCTest.logger.finer("test1");
		try {
			this.pb.post(this.c1);
			this.x.setMax(2);
			this.y.setVal(3);
			this.pb.propagate();
			Assert.assertTrue(!this.x.canBeInstantiatedTo(1));
			Assert.assertTrue(this.x.isInstantiatedTo(2));
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}

	public void test2() {
		NotEqualXYCTest.logger.finer("test2");
		try {
			this.pb.post(this.c1);
			this.pb.post(this.c2);
			this.y.setVal(3);
			this.pb.propagate();
			Assert.assertTrue(!this.x.canBeInstantiatedTo(1));
			Assert.assertTrue(!this.z.canBeInstantiatedTo(2));
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}

}
