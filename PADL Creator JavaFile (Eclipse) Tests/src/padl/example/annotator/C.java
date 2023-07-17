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
package padl.example.annotator;

public class C {
	public static void staticMethod() {
		System.out.println();

	}
	String s;

	public C() {
		super();
		this.s = new String("ttttt");
	}

	public void nonStaticMethod() {
		C.staticMethod();
		C.staticMethod();
		this.s.toString();
		super.toString();
		this.s.toString();
		this.s = "";
	}

}
