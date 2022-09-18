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
 * One container aggregation relationship with A through
 * methods addToA(), getA(), and removeFromA().
 * 
 * Three use relationships with A through parameters
 * and return types of methods addToA(), getA(), and removeFromA().
 */
public class Aggregation9 {
	private A[] a;
	void addToA(final int index, final A newA) {
		this.a[index] = newA;
	}
	A getA(final int index) {
		return this.a[index];
	}
	void removeFromA(final A newA) {
	}
}
