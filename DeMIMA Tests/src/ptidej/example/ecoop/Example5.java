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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public class Example5 {
	public static void main(final String[] args) {
		final Example5 example5 = new Example5();
		example5.listOfAs.put(Integer.valueOf(0), new A());
		// ...
	}

	private final Map listOfAs = new HashMap();
}
