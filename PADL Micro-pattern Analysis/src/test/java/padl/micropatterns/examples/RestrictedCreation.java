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

public class RestrictedCreation {
	private static RestrictedCreation uniqueInstance;
	private int intOne;
	
	// private constructor
	private RestrictedCreation() {
	}
	
	public void methodTwo () {		
		this.intOne = 0;
	}
}
