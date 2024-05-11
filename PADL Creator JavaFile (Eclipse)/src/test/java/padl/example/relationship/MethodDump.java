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
package padl.example.relationship;

/**
 * @author Farouk ZAIDI - 2004-03-23
 * PADL Tests
 *
 */
public class MethodDump {
	private int counter = 0;
	private float f = 4f;
	private float[] myTab;
	private Integer p;
	private String str;
	public void analyzeDup() {
		this.myTab[this.counter++] = 3f;
		this.myTab[--this.counter] /= 5f;
	}
	public void controlIfWhileForSwitch() {

		final Integer aLocalInt = Integer.valueOf(0);
		for (final int i = 0; i < this.p.intValue();) {
			System.out.println(this.p.floatValue() + ":" + this.p);

			if (this.p != aLocalInt) {
				this.p.byteValue();

				switch (this.p.intValue()) {
					case 1 :
						final int myInt = this.p.intValue();
						final float r = this.p.floatValue() + this.f;
						System.out.println(myInt + r);
						break;
					case 3 :
					case 2 :
						this.p = null;
						break;
					default :

						{
							this.p = Integer.getInteger(this.str, 2);
							while (this.f > 0 || this.p != null) {
								this.f = this.f - 100;
							}
							break;
						}
				}
			}
		}
	}
	@SuppressWarnings("unused")
	public void detectOperators() {
		this.f = this.p.floatValue() + 15f;
		int aLocalInt = this.p.intValue() << 7;
		aLocalInt >>= this.p.intValue() / 100 + (int) this.f;
	}
	public void dynamicMethod(
		final Integer anInteger,
		final String aString,
		final String anotherString,
		final int anInt) {
		this.str.substring(this.p.intValue(), this.str.length());
		this.str.charAt(15);
		final String myLocalStr = "My PADL is very strong!!!";
		myLocalStr.compareTo(this.str);
		this.dynamicMethod(this.p, myLocalStr, this.str, myLocalStr.length());
	}

	public void fooDup() {
		Integer a1 = null, a2 = null, a3 = null;
		Integer.valueOf(this.p.compareTo(a3 =
			a2 = a1 = Integer.valueOf(this.p.intValue())));
		System.out.println(" " + a1 + a2 + a3);
	}
	public void fooNew() {
		this.p = Integer.valueOf(54);
		this.p.compareTo(Integer.valueOf(this.p.intValue()));
		this.p.compareTo(this.p = Integer.valueOf(this.p.intValue()));
	}
	public void instanceOf() {
		final Integer localInteger = null;
		if (this.p instanceof Object) {
			final boolean b = this.str instanceof String;
			final boolean c = localInteger instanceof Number;
			System.out.println(" " + b + c);
		}
	}
	public void loadAndStoreInArray() {
		this.myTab = new float[15];
		this.myTab[5] = this.f;
		final float aLocalFloat = this.myTab[this.p.intValue()] + this.f;
		System.out.println(aLocalFloat);
	}
	public void method3aryOperator(final Integer aLocalParameter) {
		final float lp =
			this.p == aLocalParameter ? aLocalParameter.floatValue()
					: (float) Math.random();
		System.out.println(lp);
	}
	public void methodPrintln(final short s, final Integer aLocalParameter) {
		System.out.println(this.p.floatValue() + ":" + s
				+ aLocalParameter.compareTo((Integer) new Object()));
	}
	public void staticInvocations() {
		final String myLocalStr = "My PADL is stronger!!!";
		Math.random();
		Integer.parseInt("5");
		Integer.getInteger(myLocalStr, this.p);
		System.load(this.str);
	}
	public void storeToField() {
		this.p = Integer.valueOf(this.str);
		this.str = this.p.toString();
		final String myLocalStr = this.str;
		final Integer aLocalInt = this.p = Integer.getInteger(myLocalStr);
		System.out.println(aLocalInt);
	}
}
