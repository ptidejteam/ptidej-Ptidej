package choco.test;

import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.constraints.GreaterOrEqualXYC;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: Jul 20, 2003
 * Time: 5:23:39 PM
 * To change this template use Options | File Templates.
 */
public class ChocoSolveTest extends TestCase {
	private Problem pb;

	private choco.integer.IntVar x;
	private choco.integer.IntVar y;
	private choco.integer.IntVar z;
	private Constraint A;
	private Constraint B;
	public ChocoSolveTest(final String name) {
		super(name);
	}

	public void setUp() {
		this.pb = new Problem();
		this.x = this.pb.makeBoundIntVar("X", 1, 5);
		this.y = this.pb.makeBoundIntVar("Y", 1, 5);
		this.z = this.pb.makeBoundIntVar("Z", 1, 5);
		this.A = new GreaterOrEqualXYC(this.x, this.y, 1);
		this.B = new GreaterOrEqualXYC(this.y, this.z, 1);
	}

	public void tearDown() {
		this.A = null;
		this.B = null;
		this.x = null;
		this.y = null;
		this.z = null;
		this.pb = null;
	}

	public void testArithmetic() {

		this.pb.post(this.A);
		this.pb.post(this.B);
		this.A.setPassive();
		this.A.setActive();
		try {
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		System.out
			.println("X : " + this.x.getInf() + " - > " + this.x.getSup());
		System.out
			.println("Y : " + this.y.getInf() + " - > " + this.y.getSup());
		System.out
			.println("Z : " + this.z.getInf() + " - > " + this.z.getSup());
		Assert.assertEquals(3, this.x.getInf());
		Assert.assertEquals(5, this.x.getSup());
		Assert.assertEquals(2, this.y.getInf());
		Assert.assertEquals(4, this.y.getSup());
		Assert.assertEquals(1, this.z.getInf());
		Assert.assertEquals(3, this.z.getSup());

		try {
			this.z.setVal(2);
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		System.out
			.println("X : " + this.x.getInf() + " - > " + this.x.getSup());
		Assert.assertEquals(4, this.x.getInf());
		Assert.assertEquals(5, this.x.getSup());
		System.out
			.println("Y : " + this.y.getInf() + " - > " + this.y.getSup());
		Assert.assertEquals(3, this.y.getInf());
		Assert.assertEquals(4, this.y.getSup());
		System.out
			.println("Z : " + this.z.getInf() + " - > " + this.z.getSup());
		Assert.assertEquals(2, this.z.getInf());
		Assert.assertEquals(2, this.z.getSup());

		try {
			this.x.setMax(4);
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		System.out
			.println("X : " + this.x.getInf() + " - > " + this.x.getSup());
		Assert.assertEquals(4, this.x.getInf());
		Assert.assertEquals(4, this.x.getSup());
		System.out
			.println("Y : " + this.y.getInf() + " - > " + this.y.getSup());
		Assert.assertEquals(3, this.y.getInf());
		Assert.assertEquals(3, this.y.getSup());
		System.out
			.println("Z : " + this.z.getInf() + " - > " + this.z.getSup());
		Assert.assertEquals(2, this.z.getInf());
		Assert.assertEquals(2, this.z.getSup());
	}

	public void testArithmetic2() {
		this.pb.post(this.A);
		this.pb.post(this.B);
		this.A.setPassive();
		try {
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		System.out
			.println("X : " + this.x.getInf() + " - > " + this.x.getSup());
		Assert.assertEquals(1, this.x.getInf());
		Assert.assertEquals(5, this.x.getSup());
		System.out
			.println("Y : " + this.y.getInf() + " - > " + this.y.getSup());
		Assert.assertEquals(2, this.y.getInf());
		Assert.assertEquals(5, this.y.getSup());
		System.out
			.println("Z : " + this.z.getInf() + " - > " + this.z.getSup());
		Assert.assertEquals(1, this.z.getInf());
		Assert.assertEquals(4, this.z.getSup());
	}
}
