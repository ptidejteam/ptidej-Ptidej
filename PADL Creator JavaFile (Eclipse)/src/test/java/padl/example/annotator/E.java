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
package padl.example.annotator;

public class E {

	public static void main(final String args[]) {
		final E a = new E("ma");
		a.print();
		a.setM("mo");
		a.print();
		if (a instanceof E) {
			System.out.println();
		}
		else {
			System.out.println();
			for (int i = 0; i < 10; i++) {
				a.print();
				System.out.println(i);
			}
		}
	}

	String m;

	public E(final String _m) {

		this.setM(_m);
		this.setM(_m);
		this.setM(_m);
		this.setM(_m);

	}

	public void method1(final int k, final int j) {
		if (k == 4) {
			switch (j) {
				case 1 :
					System.out.println(j);
				case 2 :
					System.out.println(j);
				case 3 :
					System.out.println(j);
				case 4 :
					System.out.println(j);
				case 5 :
					System.out.println(j);
				default :
					System.out.println(j);
			}
		}
		else {
			switch (j) {
				case 1 :
					System.out.println(j);
				case 2 :
					System.out.println(j);
				case 3 :
					System.out.println(j);
				default :
					System.out.println(j);
			}
		}
	}

	public void print() {
		System.out.println(this.m);
	}

	public void setM(final String _m) {
		this.m = _m;
	}

}
