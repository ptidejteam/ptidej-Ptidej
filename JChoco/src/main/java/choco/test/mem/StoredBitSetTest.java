package choco.test.mem;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.mem.Environment;
import choco.mem.StoredBitSet;

/**
 * Created by IntelliJ IDEA.
 * User: hcambaza
 * Date: 8 déc. 2003
 * Time: 15:46:11
 * To change this template use Options | File Templates.
 */
public class StoredBitSetTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.mem.StoredBitSetTest");

	private Environment env;
	private StoredBitSet bSetA;
	private StoredBitSet bSetB;
	public StoredBitSetTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("StoredBitSetTest Testing...");

		this.env = new Environment();
		this.bSetA = new StoredBitSet(this.env, 5);
		this.bSetB = new StoredBitSet(this.env, 33);
	}

	protected void tearDown() {
		this.bSetA = null;
		this.bSetB = null;
		this.env = null;
	}

	//	/**
	//	 * testing the empty constructor with a few backtracks, additions, and updates
	//	 */
	//	public void test1() {
	//		logger.finer("test1");
	//		assertEquals(0, env.getWorldIndex());
	//		assertEquals(0, env.getTrailSize());
	//		logger.finest("bsetA" + bSetA.representedBy.get(0));
	//
	//		for (int i = 0; i < 5; i++) {
	//			assertTrue(!bSetA.get(i));
	//		}
	//		logger.finest("bSetA OK in root world 0");
	//
	//		env.worldPush();
	//		assertEquals(0, env.getTrailSize());
	//		assertEquals(1, env.getWorldIndex());
	//		bSetA.set(0);
	//		bSetA.set(1);
	//		bSetA.set(2);
	//		bSetA.set(3);
	//		bSetA.set(4);
	//		for (int i = 0; i < 5; i++) {
	//			assertTrue(bSetA.get(i));
	//		}
	//		assertEquals(1, env.getTrailSize());
	//		bSetA.set(2);
	//		bSetA.set(3);
	//		assertEquals(1, env.getTrailSize());
	//		for (int i = 0; i < 5; i++) {
	//			assertTrue(bSetA.get(i));
	//		}
	//		env.worldPush();
	//		bSetA.set(2);
	//		assertEquals(1, env.getTrailSize());
	//		assertTrue(bSetA.get(2));
	//		env.worldPop();
	//
	//		bSetA.clear(2);
	//		assertTrue(!bSetA.get(2));
	//		assertEquals(1, env.getTrailSize());
	//		env.worldPop();
	//		assertEquals(0, env.getTrailSize());
	//		assertEquals(0, env.getWorldIndex());
	//		for (int i = 0; i < 5; i++) {
	//			assertTrue(!bSetA.get(i));
	//		}
	//
	//		logger.finest("bSetA OK in world 0");
	//	}

	//	public void test2() {
	//		logger.finer("test2");
	//		assertEquals(0, env.getWorldIndex());
	//		assertEquals(0, env.getTrailSize());
	//		logger.finest("bsetB" + bSetB.representedBy.get(0));
	//
	//		for (int i = 0; i < 33; i++) {
	//			assertTrue(!bSetB.get(i));
	//		}
	//		logger.finest("bSetB OK in root world 0");
	//
	//		env.worldPush();
	//		assertEquals(0, env.getTrailSize());
	//		assertEquals(1, env.getWorldIndex());
	//		bSetB.set(0);
	//		bSetB.set(1);
	//		bSetB.set(2);
	//		bSetB.set(3);
	//		bSetB.set(4);
	//		for (int i = 0; i < 5; i++) {
	//			assertTrue(bSetB.get(i));
	//		}
	//		assertEquals(1, env.getTrailSize());
	//		bSetB.set(32);
	//		assertEquals(2, env.getTrailSize());
	//
	//		assertTrue(bSetB.get(32));
	//		assertTrue(!bSetB.get(31));
	//
	//		bSetB.clear(32);
	//		assertTrue(!bSetB.get(32));
	//
	//		env.worldPop();
	//		assertEquals(0, env.getTrailSize());
	//		assertEquals(0, env.getWorldIndex());
	//		for (int i = 0; i < 33; i++) {
	//			assertTrue(!bSetB.get(i));
	//		}
	//		logger.finest("bSetB OK in world 0");
	//	}

	//	/**
	//	 * A test for methods trailingZeroCnt, startingZeroCnt
	//	 */
	//	public void test3() {
	//		logger.finer("test3");
	//		// 10 = 2^3 + 2^1
	//		assertEquals(1, StoredBitSet.trailingZeroCnt(10));
	//		assertEquals(28, StoredBitSet.startingZeroCnt(10));
	//		// 16 = 2^4
	//		assertEquals(4, StoredBitSet.trailingZeroCnt(16));
	//		assertEquals(27, StoredBitSet.startingZeroCnt(16));
	//		for (int i = 0; i < 31; i++) {
	//			assertEquals(i, StoredBitSet.trailingZeroCnt(1 << i));
	//			assertEquals(31 - i, StoredBitSet.startingZeroCnt(1 << i));
	//		}
	//	}

	/**
	 * A test for methods nextSetBit, prevSetBit
	 */
	public void test4() {
		this.logger.finer("test4");
		Assert.assertEquals(-1, this.bSetA.prevSetBit(4));
		Assert.assertEquals(-1, this.bSetA.nextSetBit(0));
		this.bSetA.set(2);
		this.bSetA.set(4);
		Assert.assertEquals(4, this.bSetA.prevSetBit(4));
		Assert.assertEquals(2, this.bSetA.prevSetBit(3));
		Assert.assertEquals(2, this.bSetA.prevSetBit(2));
		Assert.assertEquals(-1, this.bSetA.prevSetBit(1));
		Assert.assertEquals(2, this.bSetA.nextSetBit(0));
		Assert.assertEquals(2, this.bSetA.nextSetBit(2));
		Assert.assertEquals(4, this.bSetA.nextSetBit(3));
		Assert.assertEquals(4, this.bSetA.nextSetBit(4));

		Assert.assertEquals(-1, this.bSetB.prevSetBit(33));
		Assert.assertEquals(-1, this.bSetB.nextSetBit(0));
		this.bSetB.set(2);
		this.bSetB.set(17);
		this.bSetB.set(32);
		Assert.assertEquals(32, this.bSetB.prevSetBit(32));
		Assert.assertEquals(17, this.bSetB.prevSetBit(31));
		Assert.assertEquals(17, this.bSetB.prevSetBit(17));
		Assert.assertEquals(2, this.bSetB.prevSetBit(16));
		Assert.assertEquals(2, this.bSetB.prevSetBit(2));
		Assert.assertEquals(-1, this.bSetB.prevSetBit(1));
		Assert.assertEquals(2, this.bSetB.nextSetBit(0));
		Assert.assertEquals(2, this.bSetB.nextSetBit(2));
		Assert.assertEquals(17, this.bSetB.nextSetBit(3));
		Assert.assertEquals(17, this.bSetB.nextSetBit(17));
		Assert.assertEquals(32, this.bSetB.nextSetBit(18));
		Assert.assertEquals(32, this.bSetB.nextSetBit(32));
	}

	public void test5() {
		this.logger.finer("test5");
		this.bSetB.set(0);
		this.bSetB.set(2);
		Assert.assertEquals(this.bSetB.prevSetBit(7), 2);
	}
}
