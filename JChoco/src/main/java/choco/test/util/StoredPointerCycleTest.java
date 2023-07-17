package choco.test.util;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.mem.Environment;
import choco.util.IntIterator;
import choco.util.StoredPointerCycle;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredPointerCycleTest.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2007/09/01 00:05:05 $
// Last Contributor:   $Author: guehene $

public class StoredPointerCycleTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.util.StoredPointerCycle");

	private Environment env;
	private StoredPointerCycle pcyc1;
	private StoredPointerCycle pcyc2;
	private IntIterator it;
	public StoredPointerCycleTest(final String name) {
		super(name);
	}

	public void setUp() {
		this.logger.fine("StoredPointerCycle Testing...");
		this.env = new Environment();
		this.pcyc1 = new StoredPointerCycle(this.env);
		for (int i = 0; i < 5; i++) {
			this.pcyc1.add(i, true);
		}
		this.pcyc1.setOutOfCycle(1);
		this.pcyc1.setOutOfCycle(3);

		this.pcyc2 = new StoredPointerCycle(this.env);
		for (int i = 0; i < 5; i++) {
			this.pcyc2.add(i, true);
		}
		for (int i = 0; i < 5; i++) {
			this.pcyc2.setOutOfCycle(i);
		}
		this.pcyc2.setInCycle(3);
	}

	protected void tearDown() {
		this.it = null;
		this.pcyc1 = null;
		this.pcyc2 = null;
		this.env = null;
	}

	/**
	 * A simple test of full cycle iteration with no removals during the iteration
	 */
	public void test1() {
		this.logger.finer("test1");
		this.it = this.pcyc1.getCycleButIterator(-1);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(0, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test where the cycle becomes empty after the first element is considered
	 */
	public void test10() {
		this.logger.finer("test10");
		this.it = this.pcyc1.getCycleButIterator(-1);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(0, this.it.next());
		this.pcyc1.setOutOfCycle(2);
		this.pcyc1.setOutOfCycle(4);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test with a singleton cycle, starting before it
	 */
	public void test11() {
		this.logger.finer("test11");
		this.it = this.pcyc2.getCycleButIterator(2);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(3, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test with a singleton cycle, starting after it and immediately destroying it
	 */
	public void test12() {
		this.logger.finer("test12");
		this.it = this.pcyc2.getCycleButIterator(4);
		Assert.assertTrue(this.it.hasNext());
		this.pcyc2.setOutOfCycle(3);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test with a singleton cycle, starting on it
	 */
	public void test13() {
		this.logger.finer("test13");
		this.it = this.pcyc2.getCycleButIterator(3);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, starting from a value within the cycle with no removals during the iteration
	 */
	public void test2() {
		this.logger.finer("test2");
		this.it = this.pcyc1.getCycleButIterator(2);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(0, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, starting from a value outside the cycle with no removals during the iteration
	 */
	public void test3() {
		this.logger.finer("test3");
		this.it = this.pcyc1.getCycleButIterator(3);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(0, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, starting from the first value in the cycle with no removals during the iteration
	 */
	public void test4() {
		this.logger.finer("test4");
		this.it = this.pcyc1.getCycleButIterator(0);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, starting from the last value in the cycle with no removals during the iteration
	 */
	public void test5() {
		this.logger.finer("test5");
		this.it = this.pcyc1.getCycleButIterator(4);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(0, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, destroying the end of cycle while iterating
	 */
	public void test6() {
		this.logger.finer("test6");
		this.it = this.pcyc1.getCycleButIterator(2);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		this.pcyc1.setOutOfCycle(0);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, destroying several elements while iterating
	 */
	public void test7() {
		this.logger.finer("test7");
		this.it = this.pcyc1.getCycleButIterator(1);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		this.pcyc1.setOutOfCycle(2);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(4, this.it.next());
		Assert.assertTrue(this.it.hasNext());
		this.pcyc1.setOutOfCycle(0);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, destroying several elements while iterating
	 */
	public void test8() {
		this.logger.finer("test8");
		this.it = this.pcyc1.getCycleButIterator(1);
		Assert.assertTrue(this.it.hasNext());
		Assert.assertEquals(2, this.it.next());
		this.pcyc1.setOutOfCycle(4);
		this.pcyc1.setOutOfCycle(0);
		Assert.assertTrue(!this.it.hasNext());
	}

	/**
	 * A simple test of a cycle iteration, destroying several elements while iterating
	 */
	public void test9() {
		this.logger.finer("test9");
		this.it = this.pcyc1.getCycleButIterator(-1);
		this.pcyc1.setOutOfCycle(2);
		this.pcyc1.setOutOfCycle(4);
		this.pcyc1.setOutOfCycle(0);
		Assert.assertTrue(!this.it.hasNext());
	}

}
