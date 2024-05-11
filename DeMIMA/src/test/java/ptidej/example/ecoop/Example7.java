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
package ptidej.example.ecoop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public class Example7 {
	public static void main(final String[] args) {
		final Example7 example7 = new Example7();
		final A a = new A();
		example7.addA(a);
		// ...
	}

	private final List listOfAs = new ArrayList();
	public void addA(final A a) {
		this.listOfAs.add(a);
	}
	public A getA(final int index) {
		return (A) this.listOfAs.remove(index);
	}
	public void removeA(final A a) {
		this.listOfAs.remove(a);
	}
}
