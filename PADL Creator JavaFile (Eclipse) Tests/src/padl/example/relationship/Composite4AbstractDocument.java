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
package padl.example.relationship;

/**
 * @author  Yann-Gaël Guéhéneuc
 * @version 0.1
 * 
 * One use relationship with System through the
 * use of the field out of type System.
 * 
 * One association relationship with PrintStream
 * through the call to the method println() of
 * field Printstream out of type System.
 */
public abstract class Composite4AbstractDocument {
	public void print() {
		System.out.println(this.getClass());
	}
}
