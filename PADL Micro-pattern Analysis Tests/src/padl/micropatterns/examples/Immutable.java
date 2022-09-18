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
package padl.micropatterns.examples;

@SuppressWarnings("unused")
public class Immutable {
	// Not modified outside of the constructor
	private int anImmutableAttribute;
	private static int aMutableAttribute;
	
	public Immutable() {
		this.anImmutableAttribute = 0;
	}
	
	public void methodOne () {
		Immutable.aMutableAttribute = 0;
	}
	
	private void methodTwo () {
	}
}
