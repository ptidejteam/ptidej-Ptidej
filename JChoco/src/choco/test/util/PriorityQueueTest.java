/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.test.util.PriorityQueueTest.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco.test.util;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.util.IPrioritizable;
import choco.util.PriorityQueue;

public class PriorityQueueTest extends TestCase {
	private class Entity implements IPrioritizable {
		public String name;
		public int priority;

		public Entity(final String name, final int prio) {
			this.name = name;
			this.priority = prio;
		}

		public int getPriority() {
			return this.priority;
		}

		public String toString() {
			return this.name + " " + this.priority;
		}
	}

	private final Logger logger = Logger.getLogger("choco.test.util.BitSet");

	public PriorityQueueTest(final String name) {
		super(name);
	}

	public void setUp() {
		this.logger.fine("PriorityQueue Testing...");
	}

	protected void tearDown() {
	}

	public void test1() {
		this.logger.finer("test1");

		final Entity obj1 = new Entity("Objet 1", 2);
		final Entity obj2 = new Entity("Objet 2", 0);
		final Entity obj3 = new Entity("Objet 3", 3);
		final Entity obj4 = new Entity("Objet 4", 1);

		final PriorityQueue queue = new PriorityQueue(4);

		Object[] ret;

		this.logger.finest("Step 1");
		queue.add(obj1);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 1);
		Assert.assertEquals(ret[0], obj1);

		this.logger.finest("Step 2");
		queue.add(obj2);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 2);
		Assert.assertEquals(ret[0], obj2);
		Assert.assertEquals(ret[1], obj1);

		this.logger.finest("Step 3");
		queue.add(obj3);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 3);
		Assert.assertEquals(ret[0], obj2);
		Assert.assertEquals(ret[1], obj1);
		Assert.assertEquals(ret[2], obj3);

		this.logger.finest("Step 4");
		queue.add(obj4);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 4);
		Assert.assertEquals(ret[0], obj2);
		Assert.assertEquals(ret[1], obj4);
		Assert.assertEquals(ret[2], obj1);
		Assert.assertEquals(ret[3], obj3);

		this.logger.finest("Step 5");
		obj3.priority = 1;
		queue.updatePriority(obj3);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 4);
		Assert.assertEquals(ret[0], obj2);
		Assert.assertEquals(ret[1], obj4);
		Assert.assertEquals(ret[2], obj3);
		Assert.assertEquals(ret[3], obj1);

		this.logger.finest("Step 6");
		obj2.priority = 3;
		queue.updatePriority(obj2);

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 4);
		Assert.assertEquals(ret[0], obj4);
		Assert.assertEquals(ret[1], obj3);
		Assert.assertEquals(ret[2], obj1);
		Assert.assertEquals(ret[3], obj2);

		this.logger.finest("Step 7");
		final Object obj = queue.popFirst();

		ret = queue.toArray();
		Assert.assertEquals(ret.length, 3);
		Assert.assertEquals(obj, obj4);
		Assert.assertEquals(ret[0], obj3);
		Assert.assertEquals(ret[1], obj1);
		Assert.assertEquals(ret[2], obj2);
	}
}
