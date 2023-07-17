/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.test.util.BipartiteSetTest.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco.test.util;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.util.BipartiteSet;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: Jul 4, 2003
 * Time: 10:07:46 AM
 * To change this template use Options | File Templates.
 */
public class BipartiteSetTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.util.BipartiteSetTest");

	public BipartiteSetTest(final String name) {
		super(name);
	}

	public void setUp() {
		this.logger.fine("BipartiteSet Testing...");
	}

	protected void tearDown() {
	}

	public void test1() {
		this.logger.finer("test1");
		final BipartiteSet set = new BipartiteSet();
		final Object obj1 = new Object();
		final Object obj2 = new Object();
		final Object obj3 = new Object();

		set.addLeft(obj1);
		set.addLeft(obj2);
		set.addRight(obj3);

		Assert.assertEquals(2, set.getNbLeft());
		Assert.assertEquals(1, set.getNbRight());
		Assert.assertTrue(set.isLeft(obj1));
		this.logger.finest("First Step passed");

		set.moveRight(obj1);

		Assert.assertEquals(1, set.getNbLeft());
		Assert.assertEquals(2, set.getNbRight());
		Assert.assertTrue(set.isLeft(obj2));
		Assert.assertTrue(!set.isLeft(obj1));
		Assert.assertTrue(!set.isLeft(obj3));
		this.logger.finest("Second Step passed");

		set.moveAllLeft();

		Assert.assertEquals(3, set.getNbLeft());
		Assert.assertEquals(0, set.getNbRight());
		Assert.assertTrue(set.isLeft(obj1));
		Assert.assertTrue(set.isLeft(obj2));
		Assert.assertTrue(set.isLeft(obj3));
		this.logger.finest("Third Step passed");
	}
}
