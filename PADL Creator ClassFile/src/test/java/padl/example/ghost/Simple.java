/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.example.ghost;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 * @since		2002/10/10
 */
@SuppressWarnings("unused")
public class Simple {
	private A anA;
	private A[] listOfAs;
	private int anInt;
	private int[] listOfInts;

	public static void main(final String[] args) {
		final Simple simple = new Simple();
		simple.foo(new B());
		simple.foo(new B[0]);
		simple.foo(0);
		simple.foo(new float[0]);
		simple.foo1();
		simple.foo2();
		simple.foo3();
		simple.foo4();
	}
	private void foo(final B anA) {
		this.anA = null;
		this.listOfAs = null;
		this.anInt = 0;
		this.listOfInts = null;
	}
	private void foo(final B[] aListOfBs) {
	}
	private void foo(final float anInt) {
	}
	private void foo(final float[] aListOfInts) {
	}
	private void foo1() {
		final C c = new C();
		c.getClass();
	}
	private void foo2() {
		final C[] aListOfCs = new C[0];
		aListOfCs.getClass();
	}
	private void foo3() {
		final double aDouble;
		aDouble = 0;
		System.out.println(aDouble);
	}
	private void foo4() {
		final double[] aListOfDoubles = new double[0];
		aListOfDoubles.getClass();
	}
}
