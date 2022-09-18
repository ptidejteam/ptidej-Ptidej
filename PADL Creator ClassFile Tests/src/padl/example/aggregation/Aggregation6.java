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
 */
public class Aggregation6 {
	private A[] a;
	void addA(final int index, final A newA) {
		this.a[index] = newA;
	}
	void removeA(final int index) {
	}
}
