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
package ptidej.solver.test.data.composite;

import java.util.Vector;

public class B implements A {

	private final Vector children = new Vector();

	public void Add(final A anA) {
		this.children.add(anA);
	}

	public void Operation() {
		for (int i = 0; i < this.children.size(); i++) {
			((A) this.children.get(i)).Operation();
		}
	}

	public void Remove(final A anA) {
		this.children.remove(anA);
	}
}

class BB extends B {
	public void foo() {

	}
}
