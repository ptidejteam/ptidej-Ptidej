// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.BitSetIntDomainTest.java, last modified by Francois 21 sept. 2003 10:59:44 */

package choco.test.integer;

import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Problem;
import choco.integer.var.BitSetIntDomain;
import choco.util.IntIterator;

/** a class implementing tests for backtrackable search */
public class BitSetIntDomainTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.integer.BitSetIntDomainTest");

	private Problem pb;
	private choco.integer.IntVar y;
	BitSetIntDomain yDom;
	public BitSetIntDomainTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("BitSetIntDomain Testing...");
		this.pb = new Problem();
		this.y = this.pb.makeEnumIntVar("Y", 1, 15);
		this.yDom = (BitSetIntDomain) this.y.getDomain();
	}

	protected void tearDown() {
		this.yDom = null;
		this.y = null;
		this.pb = null;
	}

	public void test1() {
		this.logger.finer("test1");

		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(15, this.yDom.getSize());
		this.logger.finest("First step passed");

		this.pb.getEnvironment().worldPush();
		this.yDom.remove(2);
		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(14, this.yDom.getSize());
		this.logger.finest("Second step passed");

		this.yDom.remove(1);
		Assert.assertEquals(3, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(13, this.yDom.getSize());
		this.logger.finest("Third step passed");

		this.pb.getEnvironment().worldPop();
		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(15, this.yDom.getSize());
		this.logger.finest("Fourth step passed");
	}

	public void test2() {
		this.logger.finer("test2");

		this.yDom.remove(10);
		this.yDom.remove(12);
		this.yDom.remove(14);
		this.yDom.remove(13);
		this.yDom.updateSup(14);
		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(11, this.yDom.getSup());
		Assert.assertEquals(10, this.yDom.getSize());
		this.logger.finest("First step passed");

		this.yDom.updateInf(8);
		Assert.assertEquals(8, this.yDom.getInf());
		Assert.assertEquals(11, this.yDom.getSup());
		Assert.assertEquals(3, this.yDom.getSize());
		this.logger.finest("Second step passed");

		this.yDom.remove(11);
		Assert.assertEquals(8, this.yDom.getInf());
		Assert.assertEquals(9, this.yDom.getSup());
		Assert.assertEquals(2, this.yDom.getSize());
		this.logger.finest("Third step passed");
	}

	/**
	 * testing delta domain management
	 */
	public void test3() {
		this.logger.finer("test3");
		final Set expectedSet357 = new TreeSet();
		expectedSet357.add(Integer.valueOf(3));
		expectedSet357.add(Integer.valueOf(5));
		expectedSet357.add(Integer.valueOf(7));
		final Set expectedSet9 = new TreeSet();
		expectedSet9.add(Integer.valueOf(9));

		{
			this.yDom.freezeDeltaDomain();
			final IntIterator it = this.yDom.getDeltaIterator();
			Assert.assertTrue(!it.hasNext());
			Assert.assertTrue(this.yDom.releaseDeltaDomain());
		}
		this.yDom.remove(3);
		this.yDom.remove(5);
		this.yDom.remove(7);
		final Set tmp357 = new TreeSet();
		this.yDom.freezeDeltaDomain();
		this.yDom.remove(9);
		for (final IntIterator it = this.yDom.getDeltaIterator(); it.hasNext();) {
			final int val = it.next();
			tmp357.add(Integer.valueOf(val));
		}
		Assert.assertEquals(expectedSet357, tmp357);
		Assert.assertTrue(!this.yDom.releaseDeltaDomain());
		this.yDom.freezeDeltaDomain();
		final Set tmp9 = new TreeSet();
		for (final IntIterator it = this.yDom.getDeltaIterator(); it.hasNext();) {
			final int val = it.next();
			tmp9.add(Integer.valueOf(val));
		}
		Assert.assertEquals(expectedSet9, tmp9);
		Assert.assertTrue(this.yDom.releaseDeltaDomain());
	}

	/**
	 * test the restrict method
	 */
	public void test4() {
		this.logger.finer("test2");

		this.yDom.remove(10);
		this.yDom.remove(12);
		this.yDom.remove(14);
		this.yDom.remove(13);
		this.yDom.updateSup(14);
		this.yDom.restrict(7);
		Assert.assertEquals(7, this.yDom.getInf());
		Assert.assertEquals(7, this.yDom.getSup());
		Assert.assertEquals(1, this.yDom.getSize());
		final IntIterator it = this.yDom.getIterator();
		Assert.assertTrue(it.hasNext());
		Assert.assertEquals(7, it.next());
		Assert.assertTrue(!it.hasNext());
	}

}