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
 * One container aggregation relationship to A through
 * List listOfAs and the two methods addA(), removeA().
 * 
 * Two aggregation relatiobships to A through the
 * add() and remove() methods of List listOfAs.
 * 
 * Two use relationships to A through the
 * parameters of methods addA() and removeA().
 */
public class Aggregation11 {
	private List listOfAs;
	void addA(final A newA) {
		this.listOfAs.add(newA);
	}
	void removeA(final A newA) {
		this.listOfAs.remove(newA);
	}
}
