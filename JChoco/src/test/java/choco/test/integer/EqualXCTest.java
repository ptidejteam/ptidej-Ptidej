/*
 * EqualXCTest.java
 *
 */

package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;

/** a class implementing tests for backtrackable search */
public class EqualXCTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.EqualXCTest");

	private Problem pb;
	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private Constraint c1;
	private Constraint c2;
	private Constraint c3;
	public EqualXCTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("EqualXC Testing...");
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.c1 = this.pb.eq(this.x, 1);
		this.c2 = this.pb.eq(this.y, 2);
		this.c3 = this.pb.eq(this.y, 3);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.c3 = null;
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
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		Assert.assertTrue(this.x.isInstantiated());
		Assert.assertTrue(this.y.isInstantiated());
		Assert.assertEquals(1, this.x.getValue());
		Assert.assertEquals(2, this.y.getValue());
		this.logger.finest("domains OK after first propagate");
		Assert.assertTrue(!this.c3.isSatisfied());
	}
}
