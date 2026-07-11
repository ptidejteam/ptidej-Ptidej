/*
 * EqualXYCTest.java
 *
 */

package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.constraints.EqualXYC;

/** a class implementing tests for backtrackable search */
public class EqualXYCTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.EqualXYCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private choco.integer.IntVar z;
	private Constraint c1;
	private Constraint c2;
	public EqualXYCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("EqualXYC Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.z = this.pb.makeBoundIntVar("Z", 1, 5);
		this.c1 = new EqualXYC(this.x, this.y, 2);
		this.c2 = new EqualXYC(this.y, this.z, 1);
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
		this.logger.finer("test1");
		this.pb.post(this.c1);
		this.pb.post(this.c2);

		try {
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		this.logger
			.finest("X : " + this.x.getInf() + " - > " + this.x.getSup());
		this.logger
			.finest("Y : " + this.y.getInf() + " - > " + this.y.getSup());
		this.logger
			.finest("Z : " + this.z.getInf() + " - > " + this.z.getSup());
		Assert.assertEquals(4, this.x.getInf());
		Assert.assertEquals(5, this.x.getSup());
		Assert.assertEquals(2, this.y.getInf());
		Assert.assertEquals(3, this.y.getSup());
		Assert.assertEquals(1, this.z.getInf());
		Assert.assertEquals(2, this.z.getSup());
		this.logger.finest("domains OK after first propagate");

		try {
			this.z.setMin(2);
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}

		Assert.assertTrue(this.x.isInstantiated());
		Assert.assertTrue(this.y.isInstantiated());
		Assert.assertTrue(this.z.isInstantiated());
		Assert.assertEquals(5, this.x.getValue());
		Assert.assertEquals(3, this.y.getValue());
		Assert.assertEquals(2, this.z.getValue());
	}

}