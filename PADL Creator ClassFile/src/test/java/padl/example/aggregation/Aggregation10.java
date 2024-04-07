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

import java.util.List;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 * 
 * This is a counter-example of aggregation relationship.
 * Method addA(A) and removeA(int) do not implement an
 * aggregation relationship.
 */
public class Aggregation10 {
	private List listOfAs;
	void addA(final A newA) {
		this.listOfAs.add(11, newA);
	}
	void removeA(final int index) {
		this.listOfAs.remove(index);
	}
}
