/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.returnvalue;

public class B {
	private boolean booleanField = false;
	private long longField = 1l;
	private String stringField = "Hello World!";
	private Object objectField = new Object();

	public boolean booleanOperation(int i) {
		return !this.booleanField;
	}
	public long longOperation() {
		return this.longField;
	}
	public String stringOperation() {
		return this.stringField;
	}
	public Object objectOperation() {
		return this.objectField;
	}
}