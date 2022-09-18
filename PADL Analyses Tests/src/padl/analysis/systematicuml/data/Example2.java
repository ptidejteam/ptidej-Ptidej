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
package padl.analysis.systematicuml.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/24
 */
@SuppressWarnings("rawtypes")
public class Example2 {
	private List listOfAs = new ArrayList();

	@SuppressWarnings("unchecked")
	public void addA(final A a) {
		this.listOfAs.add(a);
	}
	public A getA(final int index) {
		return (A) this.listOfAs.remove(index);
	}
	public void removeA(final A a) {
		this.listOfAs.remove(a);
	}
	public static void main(final String[] args) {
		final Example2 example2 = new Example2();
		example2.addA(new A());
		// ...
	}
}
