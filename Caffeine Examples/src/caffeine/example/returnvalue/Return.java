/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.returnvalue;

public class Return {
	public static void main(final String[] args) {
		final A a = new A();
		a.attach(new B());
		a.objectOperation();
		a.booleanOperation(1);
		a.longOperation();
		a.stringOperation();
		a.objectOperation();
		B b = new B();
		b.booleanOperation(10);
	}
}