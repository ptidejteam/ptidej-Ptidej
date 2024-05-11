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

public class A {

	public static void main() {
		final A a = new A("ma");
		a.print();
		a.setM("mo");
		a.print();
	}

	String m;

	public A(final String _m) {

		this.setM(_m);
	}

	public void print() {
		System.out.println(this.m);
	}

	public void setM(final String _m) {
		this.m = _m;
	}
}
