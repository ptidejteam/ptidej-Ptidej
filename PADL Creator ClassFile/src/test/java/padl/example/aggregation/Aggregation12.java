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
package padl.example.aggregation;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 * 
 * One container aggregation with A through field A[] a
 * and methods addA(), getA(), and removeA().
 * 
 * Two use relationships with A through return type
 * and parameters of methods addA() and getA().
 */
public class Aggregation12 {
	private A[] a;
	void addA(final int index, final A newA) {
		this.a[index] = newA;
	}
	A getA(final int index) {
		return this.a[index];
	}
	void removeA(final int index) {
	}
}
