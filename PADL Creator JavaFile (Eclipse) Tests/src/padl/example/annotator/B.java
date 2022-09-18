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

public class B {
	A a;
	public B() {
		this.a = new A("m");
	}

	public void m() {
		this.a.print();
		this.a.setM("b");

	}

	public void m1(final A _a) {
		_a.print();
		_a.setM("k");

	}

}
