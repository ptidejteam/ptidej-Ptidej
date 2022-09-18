/*
 * T_StoredInt.java
 *
 */

package choco.test.mem;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.mem.Environment;
import choco.mem.IStateInt;

/** a class implementing tests for backtrackable search */
public class StoredIntTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.mem.StoredIntTest");

	private Environment env;
	private IStateInt x1;
	public StoredIntTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("StoredInt Testing...");
		this.env = new Environment();
		this.x1 = this.env.makeInt(0);
	}

	protected void tearDown() {
		this.x1 = null;
		this.env = null;
	}

	/**
	 * testing one backtrack
	 */
	public void test1() {
		this.logger.finer("test1");
		Assert.assertTrue(this.env.getWorldIndex() == 0);
		Assert.assertTrue(this.env.getTrailSize() == 0);
		this.env.worldPush();
		this.x1.set(1);
		Assert.assertTrue(this.x1.get() == 1);
		Assert.assertTrue(this.env.getWorldIndex() == 1);
		Assert.assertTrue(this.env.getTrailSize() == 1);
		this.env.worldPop();
		Assert.assertTrue(this.x1.get() == 0);
		Assert.assertTrue(this.env.getWorldIndex() == 0);
		Assert.assertTrue(this.env.getTrailSize() == 0);
	}

	/**
	 * testing a bunch of backtracks
	 */
	public void test2() {
		this.logger.finer("test2");
		Assert.assertTrue(this.env.getWorldIndex() == 0);
		Assert.assertTrue(this.env.getTrailSize() == 0);
		for (int i = 1; i <= 100; i++) {
			this.env.worldPush();
			this.x1.set(i);
			this.x1.set(2 * i);
			this.x1.set(3 * i);
			this.x1.set(i);
			Assert.assertTrue(this.env.getWorldIndex() == i);
			Assert.assertTrue(this.env.getTrailSize() == i);
			Assert.assertTrue(this.x1.get() == i);
		}
		for (int i = 100; i >= 1; i--) {
			this.env.worldPop();
			Assert.assertTrue(this.env.getWorldIndex() == i - 1);
			Assert.assertTrue(this.env.getTrailSize() == i - 1);
			Assert.assertTrue(this.x1.get() == i - 1);
		}
	}

}