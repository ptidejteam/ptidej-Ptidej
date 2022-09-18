// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.IntLinCombTest.java, last modified by Francois 27 sept. 2003 12:08:59 */
package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.ContradictionException;
import choco.Problem;

public class IntLinCombTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.search.IntLinCombTest");

	private Problem pb;
	private choco.integer.IntVar x1, x2, x3, x4, x5, x6, x7, y1, y2;
	public IntLinCombTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("IntLinComb Testing...");
		this.pb = new Problem();
		this.x1 = this.pb.makeBoundIntVar("X1", 0, 10);
		this.x2 = this.pb.makeBoundIntVar("X2", 0, 10);
		this.x3 = this.pb.makeBoundIntVar("X3", 0, 10);
		this.x4 = this.pb.makeBoundIntVar("X3", 0, 10);
		this.x5 = this.pb.makeBoundIntVar("X3", 0, 10);
		this.x6 = this.pb.makeBoundIntVar("X3", 0, 10);
		this.x7 = this.pb.makeBoundIntVar("X3", 0, 10);
		this.y1 = this.pb.makeBoundIntVar("Y1", 0, 10);
		this.y2 = this.pb.makeBoundIntVar("Y2", 0, 50);
	}

	protected void tearDown() {
		this.x1 = null;
		this.x2 = null;
		this.x3 = null;
		this.x4 = null;
		this.x5 = null;
		this.x6 = null;
		this.x7 = null;
		this.y1 = null;
		this.y2 = null;
		this.pb = null;
	}

	/** Simple test: 5 equations on 4 variables: 1 single search solution that should be found by propagation
	 */
	public void test1() {
		this.logger.finer("test1");
		try {
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 3, 7, 9, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 68));
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 5, 2, 8, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 44));
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 3, 12, 2, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 72));
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 15, 4, 1, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 53));
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 12, 7, 9, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 86));
			// pb.getPropagationEngine().getLogger().setVerbosity(choco.model.ILogger.TALK);
			this.pb.propagate();
			Assert.assertTrue(this.x1.isInstantiated());
			Assert.assertTrue(this.x2.isInstantiated());
			Assert.assertTrue(this.x3.isInstantiated());
			Assert.assertTrue(this.y1.isInstantiated());
			Assert.assertEquals(2, this.x1.getValue());
			Assert.assertEquals(5, this.x2.getValue());
			Assert.assertEquals(3, this.x3.getValue());
			Assert.assertEquals(0, this.y1.getValue());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}

	/** Exact same test as test1, but expressed with binary +/x operators instead of "scalar" operator
	 */
	public void test2() {
		this.logger.finer("test2");
		try {
			this.pb.post(this.pb.eq(
				this.pb.plus(
					this.pb.plus(
						this.pb.plus(
							this.pb.mult(3, this.x1),
							this.pb.mult(7, this.x2)),
						this.pb.mult(9, this.x3)),
					this.pb.mult(-1, this.y1)),
				68));
			this.pb.post(this.pb.eq(
				this.pb.plus(
					this.pb.plus(
						this.pb.plus(
							this.pb.mult(5, this.x1),
							this.pb.mult(2, this.x2)),
						this.pb.mult(8, this.x3)),
					this.pb.mult(-1, this.y1)),
				44));
			this.pb.post(this.pb.eq(
				this.pb.plus(
					this.pb.plus(
						this.pb.plus(
							this.pb.mult(3, this.x1),
							this.pb.mult(12, this.x2)),
						this.pb.mult(2, this.x3)),
					this.pb.mult(-1, this.y1)),
				72));
			this.pb.post(this.pb.eq(
				this.pb.plus(
					this.pb.plus(
						this.pb.plus(
							this.pb.mult(15, this.x1),
							this.pb.mult(4, this.x2)),
						this.pb.mult(1, this.x3)),
					this.pb.mult(-1, this.y1)),
				53));
			this.pb.post(this.pb.eq(
				this.pb.plus(
					this.pb.plus(
						this.pb.plus(
							this.pb.mult(12, this.x1),
							this.pb.mult(7, this.x2)),
						this.pb.mult(9, this.x3)),
					this.pb.mult(-1, this.y1)),
				86));
			// pb.getPropagationEngine().getLogger().setVerbosity(choco.model.ILogger.TALK);
			this.pb.propagate();
			Assert.assertTrue(this.x1.isInstantiated());
			Assert.assertTrue(this.x2.isInstantiated());
			Assert.assertTrue(this.x3.isInstantiated());
			Assert.assertTrue(this.y1.isInstantiated());
			Assert.assertEquals(2, this.x1.getValue());
			Assert.assertEquals(5, this.x2.getValue());
			Assert.assertEquals(3, this.x3.getValue());
			Assert.assertEquals(0, this.y1.getValue());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}

	/** Another test: 5 equations on 4 variables: the solution is found step by step
	 */
	public void test3() {
		this.logger.finer("test3");
		try {
			this.x1.setMin(1);
			this.x2.setMin(1);
			this.x3.setMin(1);
			this.y1.setMin(1);
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 1, 3, 5, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y1 }), 23));
			this.pb.post(this.pb.eq(this.pb
				.scalar(new int[] { 2, 10, 1, -1 }, new choco.integer.IntVar[] {
						this.x1, this.x2, this.x3, this.y2 }), 14));
			this.pb.post(this.pb.eq(
				this.pb.scalar(new int[] { 7, -1 }, new choco.integer.IntVar[] {
						this.y1, this.y2 }),
				0));
			this.pb.propagate();
			Assert.assertEquals(1, this.x1.getInf());
			Assert.assertEquals(10, this.x1.getSup());
			Assert.assertEquals(1, this.x2.getInf());
			Assert.assertEquals(6, this.x2.getSup());
			Assert.assertEquals(1, this.x3.getInf());
			Assert.assertEquals(5, this.x3.getSup());
			Assert.assertEquals(1, this.y1.getInf());
			Assert.assertEquals(7, this.y1.getSup());
			Assert.assertEquals(7, this.y2.getInf());
			Assert.assertEquals(49, this.y2.getSup());
			this.x1.setMin(7);
			this.pb.propagate();
			Assert.assertEquals(7, this.x1.getInf());
			Assert.assertEquals(10, this.x1.getSup());
			Assert.assertEquals(1, this.x2.getInf());
			Assert.assertEquals(4, this.x2.getSup());
			Assert.assertEquals(1, this.x3.getInf());
			Assert.assertEquals(4, this.x3.getSup());
			Assert.assertEquals(2, this.y1.getInf());
			Assert.assertEquals(7, this.y1.getSup());
			Assert.assertEquals(14, this.y2.getInf());
			Assert.assertEquals(49, this.y2.getSup());
			this.x3.setMax(2);
			this.pb.propagate();
			Assert.assertEquals(7, this.x1.getInf());
			Assert.assertEquals(10, this.x1.getSup());
			Assert.assertEquals(2, this.x2.getInf());
			Assert.assertEquals(4, this.x2.getSup());
			Assert.assertEquals(1, this.x3.getInf());
			Assert.assertEquals(2, this.x3.getSup());
			Assert.assertEquals(3, this.y1.getInf());
			Assert.assertEquals(6, this.y1.getSup());
			Assert.assertEquals(21, this.y2.getInf());
			Assert.assertEquals(42, this.y2.getSup());
			this.y2.setMin(30);
			this.pb.propagate();
			Assert.assertEquals(7, this.x1.getInf());
			Assert.assertEquals(10, this.x1.getSup());
			Assert.assertEquals(3, this.x2.getInf());
			Assert.assertEquals(4, this.x2.getSup());
			Assert.assertTrue(this.x3.isInstantiated());
			Assert.assertEquals(2, this.x3.getValue());
			Assert.assertEquals(5, this.y1.getInf());
			Assert.assertEquals(6, this.y1.getSup());
			Assert.assertEquals(35, this.y2.getInf());
			Assert.assertEquals(42, this.y2.getSup());
			this.x2.setMin(4);
			this.pb.propagate();
			/*
			      Logger.getLogger("choco").setLevel(Level.FINEST);
			      Logger.getLogger("choco.var").setLevel(Level.FINEST);
			      Logger.getLogger("choco.search").setLevel(Level.FINEST);
			      Logger.getLogger("choco.search.IntVar").setLevel(Level.FINEST);
			      Logger.getLogger("choco.test").setLevel(Level.FINEST);
			      Logger.getLogger("choco.test.search").setLevel(Level.FINEST);
			      Logger.getLogger("choco.test.search.IntLinCombTest").setLevel(Level.FINEST);
			      pb.propagate();
			*/
			Assert.assertTrue(this.x1.isInstantiated());
			Assert.assertTrue(this.x2.isInstantiated());
			Assert.assertTrue(this.x3.isInstantiated());
			Assert.assertTrue(this.y1.isInstantiated());
			Assert.assertTrue(this.y2.isInstantiated());
			Assert.assertEquals(7, this.x1.getValue());
			Assert.assertEquals(4, this.x2.getValue());
			Assert.assertEquals(2, this.x3.getValue());
			Assert.assertEquals(6, this.y1.getValue());
			Assert.assertEquals(42, this.y2.getValue());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}

	/** Slightly larger test: 10 equations on 7 variables: 1 single search solution that should be found by propagation
	 */
	public void test4() {
		try {
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 98527, 34588, 5872, 59422, 65159, -30704,
							-29649 },
					new choco.integer.IntVar[] { this.x1, this.x2, this.x3,
							this.x5, this.x7, this.x4, this.x6 }), 1547604));
			this.pb
				.post(this.pb.eq(
					this.pb.scalar(new int[] { 98957, 83634, 69966, 62038,
							37164, 85413, -93989 }, new choco.integer.IntVar[] {
							this.x2, this.x3, this.x4, this.x5, this.x6,
							this.x7, this.x1 }),
					1823553));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 10949, 77761, 67052, -80197, -61944, -92964,
							-44550 },
					new choco.integer.IntVar[] { this.x1, this.x2, this.x5,
							this.x3, this.x4, this.x6, this.x7 }), -900032));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 73947, 84391, 81310, -96253, -44247, -70582,
							-33054 },
					new choco.integer.IntVar[] { this.x1, this.x3, this.x5,
							this.x2, this.x4, this.x6, this.x7 }), 1164380));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 13057, 42253, 77527, 96552, -60152, -21103,
							-97932 },
					new choco.integer.IntVar[] { this.x3, this.x4, this.x5,
							this.x7, this.x1, this.x2, this.x6 }), 1185471));
			this.pb.post(this.pb.eq(
				this.pb.scalar(new int[] { 66920, 55679, -64234, -65337,
						-45581, -67707, -98038 }, new choco.integer.IntVar[] {
						this.x1, this.x4, this.x2, this.x3, this.x5, this.x6,
						this.x7 }),
				-1394152));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 68550, 27886, 31716, 73597, 38835, -88963,
							-76391 },
					new choco.integer.IntVar[] { this.x1, this.x2, this.x3,
							this.x4, this.x7, this.x5, this.x6 }), 279091));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 76132, 71860, 22770, 68211, 78587, -48224,
							-82817 },
					new choco.integer.IntVar[] { this.x2, this.x3, this.x4,
							this.x5, this.x6, this.x1, this.x7 }), 480923));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 94198, 87234, 37498, -71583, -25728, -25495,
							-70023 },
					new choco.integer.IntVar[] { this.x2, this.x3, this.x4,
							this.x1, this.x5, this.x6, this.x7 }), -519878));
			this.pb
				.post(this.pb.eq(this.pb.scalar(
					new int[] { 78693, 38592, 38478, -94129, -43188, -82528,
							-69025 },
					new choco.integer.IntVar[] { this.x1, this.x5, this.x6,
							this.x2, this.x3, this.x4, this.x7 }), -361921));
			// pb.getPropagationEngine().getLogger().setVerbosity(choco.model.ILogger.DEBUG);

			this.pb.propagate();
			Assert.assertEquals(0, this.x1.getInf());
			Assert.assertEquals(10, this.x1.getSup());
			Assert.assertEquals(0, this.x2.getInf());
			Assert.assertEquals(10, this.x2.getSup());
			Assert.assertEquals(0, this.x3.getInf());
			Assert.assertEquals(10, this.x3.getSup());
			Assert.assertEquals(0, this.x4.getInf());
			Assert.assertEquals(10, this.x4.getSup());
			Assert.assertEquals(0, this.x5.getInf());
			Assert.assertEquals(10, this.x5.getSup());
			Assert.assertEquals(0, this.x6.getInf());
			Assert.assertEquals(10, this.x6.getSup());
			Assert.assertEquals(0, this.x7.getInf());
			Assert.assertEquals(10, this.x7.getSup());
			this.x1.setMin(6);
			this.pb.propagate();
			Assert.assertEquals(6, this.x1.getInf());
			Assert.assertEquals(1, this.x5.getInf());
			Assert.assertEquals(7, this.x6.getSup());
			Assert.assertEquals(3, this.x7.getInf());
			this.x3.setMin(8);
			this.pb.propagate();
			Assert.assertEquals(6, this.x1.getInf());
			Assert.assertEquals(6, this.x2.getSup());
			Assert.assertEquals(8, this.x3.getInf());
			this.x4.setMin(4);
			this.pb.propagate();
			Assert.assertEquals(5, this.x2.getSup());
			Assert.assertEquals(4, this.x4.getInf());
			Assert.assertEquals(4, this.x5.getInf());
			Assert.assertEquals(4, this.x7.getInf());
			this.x5.setMin(9);
			this.pb.propagate();
			Assert.assertEquals(4, this.x2.getSup());
			Assert.assertEquals(9, this.x5.getInf());
			Assert.assertEquals(6, this.x6.getSup());
			this.x6.setMin(3);
			this.pb.propagate();
			Assert.assertEquals(8, this.x1.getSup());
			Assert.assertEquals(2, this.x2.getSup());
			Assert.assertEquals(7, this.x4.getSup());
			Assert.assertEquals(3, this.x6.getInf());
			Assert.assertEquals(5, this.x6.getSup());
			Assert.assertEquals(8, this.x7.getInf());
			this.x7.setMin(9);
			this.pb.propagate();
			Assert.assertTrue(this.x1.isInstantiated());
			Assert.assertTrue(this.x2.isInstantiated());
			Assert.assertTrue(this.x3.isInstantiated());
			Assert.assertTrue(this.x4.isInstantiated());
			Assert.assertTrue(this.x5.isInstantiated());
			Assert.assertTrue(this.x6.isInstantiated());
			Assert.assertTrue(this.x7.isInstantiated());
			Assert.assertEquals(6, this.x1.getValue());
			Assert.assertEquals(0, this.x2.getValue());
			Assert.assertEquals(8, this.x3.getValue());
			Assert.assertEquals(4, this.x4.getValue());
			Assert.assertEquals(9, this.x5.getValue());
			Assert.assertEquals(3, this.x6.getValue());
			Assert.assertEquals(9, this.x7.getValue());
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
	}
}

/*
(     choco/post(p, list(98527, 34588, 5872, 59422, 65159, -30704, -29649) scalar list(x1,x2,x3,x5,x7,x4,x6) + 1547604),
      choco/post(p, list(98957, 83634, 69966, 62038, 37164, 85413, -93989) scalar list(x2,x3,x4,x5,x6,x7,x1) ==  1823553),
      choco/post(p, list(10949, 77761, 67052, -80197, -61944, -92964, -44550) scalar list(x1,x2,x5,x3,x4,x6,x7) == -900032),
      choco/post(p, list(73947, 84391, 81310, -96253, -44247, -70582, -33054) scalar list(x1,x3,x5,x2,x4,x6,x7) == 1164380),
      choco/post(p, list(13057, 42253, 77527, 96552, -60152, -21103, -97932) scalar list(x3,x4,x5,x7,x1,x2,x6) == 1185471),
      choco/post(p, list(66920, 55679, -64234, -65337, -45581, -67707, -98038) scalar list(x1,x4,x2,x3,x5,x6,x7) == -1394152),
      choco/post(p, list(68550, 27886, 31716, 73597, 38835, -88963, -76391) scalar list(x1,x2,x3,x4,x7,x5,x6) == 279091),
      choco/post(p, list(76132, 71860, 22770, 68211, 78587, -48224, -82817) scalar list(x2,x3,x4,x5,x6,x1,x7) == 480923),
      choco/post(p, list(94198, 87234, 37498, -71583, -25728, -25495, -70023) scalar list(x2,x3,x4,x1,x5,x6,x7) == -519878),
      choco/post(p, list(78693, 38592, 38478, -94129, -43188, -82528, -69025) scalar list(x1,x5,x6,x2,x3,x4,x7) == -361921),
   */