/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.test.util.BitSetTest.java
 * last modified by Francois 28 aoet 2003:14:59:09
 */
package choco.test.util;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.util.BitSet;

/** a class implementing tests for bit sets management */
public class BitSetTest extends TestCase {
	private final Logger logger = Logger.getLogger("choco.test.util.BitSet");

	public BitSetTest(final String name) {
		super(name);
	}

	public void setUp() {
		this.logger.fine("BitSet Testing...");
	}

	protected void tearDown() {
	}

	public void test1() {
		this.logger.finer("test1");
		Assert.assertTrue(BitSet.getBit(3, 0));
		Assert.assertTrue(BitSet.getBit(3, 1));
		for (int i = 3; i < 31; i++) {
			Assert.assertTrue(!BitSet.getBit(3, i));
		}
	}

	public void test2() {
		this.logger.finer("test2");
		Assert.assertTrue(BitSet.getBit(21, 0));
		Assert.assertTrue(!BitSet.getBit(21, 1));
		Assert.assertTrue(BitSet.getBit(21, 2));
		Assert.assertTrue(!BitSet.getBit(21, 3));
		Assert.assertTrue(BitSet.getBit(21, 4));
		for (int i = 5; i < 31; i++) {
			Assert.assertTrue(!BitSet.getBit(21, i));
		}
	}

	public void test3() {
		this.logger.finer("test3");
		Assert.assertTrue(BitSet.setBit(3, 0) == 3);
		Assert.assertTrue(BitSet.setBit(3, 1) == 3);
		Assert.assertTrue(BitSet.setBit(3, 2) == 7);
	}

	public void test4() {
		this.logger.finer("test4");
		Assert.assertTrue(BitSet.setBit(21, 0) == 21);
		Assert.assertTrue(BitSet.setBit(21, 1) == 23);
		Assert.assertTrue(BitSet.setBit(21, 2) == 21);
	}

}