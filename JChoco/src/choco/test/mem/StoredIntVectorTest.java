package choco.test.mem;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.mem.Environment;
import choco.mem.IStateIntVector;

public class StoredIntVectorTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.mem.StoredIntVectorTest");

	private Environment env;
	private IStateIntVector iVectA;
	private IStateIntVector iVectB;
	private IStateIntVector vector;
	public StoredIntVectorTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("StoredIntVector Testing...");

		this.env = new Environment();
		this.iVectA = this.env.makeIntVector();
		this.iVectB = this.env.makeIntVector(10, 1000);
		this.vector = this.env.makeIntVector();
	}

	protected void tearDown() {
		this.vector = null;
		this.iVectA = null;
		this.iVectB = null;
		this.env = null;
	}
	/**
	 * testing the empty constructor with a few backtracks, additions, and updates
	 */
	public void test1() {
		this.logger.finer("test1");
		Assert.assertEquals(0, this.env.getWorldIndex());
		Assert.assertEquals(0, this.env.getTrailSize());
		Assert.assertTrue(this.iVectA.isEmpty());
		this.logger.finest("iVectA OK in root world 0");

		this.env.worldPush();
		Assert.assertEquals(1, this.env.getWorldIndex());
		this.iVectA.add(0);
		this.iVectA.add(1);
		this.iVectA.add(2);
		this.iVectA.add(0);
		this.iVectA.set(3, 3);
		Assert.assertEquals(0, this.iVectA.get(0));
		Assert.assertEquals(1, this.iVectA.get(1));
		Assert.assertEquals(2, this.iVectA.get(2));
		Assert.assertEquals(3, this.iVectA.get(3));
		Assert.assertEquals(4, this.iVectA.size());
		Assert.assertEquals(1, this.env.getTrailSize());
		this.logger.finest("iVectA OK in updated world 1");

		this.env.worldPush();
		for (int i = 0; i < 4; i++) {
			this.iVectA.set(i, 50 + i);
		}
		for (int i = 0; i < 4; i++) {
			Assert.assertTrue(this.iVectA.get(i) == 50 + i);
		}
		Assert.assertEquals(5, this.env.getTrailSize());
		Assert.assertEquals(2, this.env.getWorldIndex());
		this.logger.finest("iVectA OK in updated world 2");

		this.env.worldPop();
		Assert.assertEquals(0, this.iVectA.get(0));
		Assert.assertEquals(1, this.iVectA.get(1));
		Assert.assertEquals(2, this.iVectA.get(2));
		Assert.assertEquals(3, this.iVectA.get(3));
		Assert.assertEquals(4, this.iVectA.size());
		Assert.assertEquals(1, this.env.getTrailSize());
		Assert.assertEquals(1, this.env.getWorldIndex());
		this.logger.finest("iVectA OK in restored world 1");

		this.env.worldPop();
		Assert.assertEquals(0, this.env.getWorldIndex());
		Assert.assertEquals(0, this.env.getTrailSize());
		Assert.assertTrue(this.iVectA.isEmpty());
		this.logger.finest("iVectA OK in world 0");
	}

	/**
	 * testing the two constructors with a few backtrack, additions, updates and deletions
	 */
	public void test2() {
		Assert.assertEquals(0, this.env.getWorldIndex());
		Assert.assertEquals(0, this.env.getTrailSize());
		Assert.assertTrue(!this.iVectB.isEmpty());
		Assert.assertEquals(10, this.iVectB.size());
		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(1000, this.iVectB.get(i));
		}
		this.logger.finest("iVectB OK in root world 0");

		this.env.worldPush();
		Assert.assertEquals(1, this.env.getWorldIndex());
		for (int i = 0; i < 10; i++) {
			this.iVectB.set(i, 2000 + i);
			this.iVectB.set(i, 3000 + i);
		}
		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(3000 + i, this.iVectB.get(i));
		}
		Assert.assertEquals(10, this.env.getTrailSize()); // 10 entries
		this.logger.finest("iVectB OK in updated world 1");

		this.env.worldPush();
		Assert.assertEquals(2, this.env.getWorldIndex());
		for (int i = 10; i < 20; i++) {
			this.iVectB.add(4000 + i);
		}
		Assert.assertEquals(20, this.iVectB.size());
		for (int i = 10; i < 20; i++) {
			Assert.assertEquals(4000 + i, this.iVectB.get(i));
		}
		Assert.assertEquals(11, this.env.getTrailSize());
		// only the size is pushed on the trail, not the additions
		for (int i = 10; i < 20; i++) {
			this.iVectB.set(i, 5000 + i);
		}
		Assert.assertEquals(11, this.env.getTrailSize());
		// 10 modified entries, but in same world -> nothing trailed
		this.logger.finest("iVectB OK in updated world 2");

		this.logger.finest("OK before worldPush");
		this.env.worldPush();
		Assert.assertEquals(3, this.env.getWorldIndex());
		for (int i = 20; i > 10; i--) {
			this.iVectB.removeLast();
		}
		Assert.assertEquals(10, this.iVectB.size());
		Assert.assertEquals(12, this.env.getTrailSize()); // modified the size
		this.logger.finest("iVectB OK in updated world 3");

		this.logger.finest("OK before worldPop");
		this.env.worldPop();
		Assert.assertEquals(2, this.env.getWorldIndex());
		Assert.assertEquals(11, this.env.getTrailSize());
		Assert.assertTrue(this.iVectB.size() == 20);
		for (int i = 10; i < 20; i++) {
			Assert.assertTrue(this.iVectB.get(i) == 5000 + i);
		}
		this.logger.finest("iVectB OK in restored world 2");

		this.logger.finest("OK before worldPop");
		this.env.worldPop();
		Assert.assertEquals(1, this.env.getWorldIndex());
		Assert.assertEquals(10, this.iVectB.size());
		Assert.assertEquals(10, this.env.getTrailSize());
		for (int i = 0; i < 10; i++) {
			Assert.assertTrue(this.iVectB.get(i) == 3000 + i);
		}
		this.logger.finest("iVectB OK in restored world 1");

		this.logger.finest("OK before worldPop");
		this.env.worldPop();
		Assert.assertEquals(0, this.env.getWorldIndex());
		Assert.assertTrue(this.iVectB.size() == 10);
		Assert.assertEquals(0, this.env.getTrailSize());
		this.logger.finest("iVectB OK in root world 0");
	}

	/**
	 * another small test
	 */
	public void test3() {
		this.logger.finer("test3");
		this.env.worldPush();
		this.vector.add(1);
		this.vector.add(2);
		this.env.worldPush();
		this.vector.set(0, 2);
		this.vector.add(3);
		this.env.worldPush();
		Assert.assertEquals(this.vector.size(), 3);
		Assert.assertEquals(this.vector.get(0), 2);
		Assert.assertEquals(this.vector.get(1), 2);
		Assert.assertEquals(this.vector.get(2), 3);

		this.env.worldPop();
		this.env.worldPop();
		Assert.assertEquals(this.vector.size(), 2);
		Assert.assertEquals(this.vector.get(0), 1);
		Assert.assertEquals(this.vector.get(1), 2);

		this.env.worldPop();
		Assert.assertEquals(this.vector.size(), 0);
	}
}
