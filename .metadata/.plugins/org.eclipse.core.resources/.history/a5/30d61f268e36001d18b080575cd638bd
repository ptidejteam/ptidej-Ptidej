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

public class D {

	C nonStaticField;
	static C staticField;

	static void staticCallingMethod(final C parameter) {
		final C localVariable = new C();
		C localVariable1;
		localVariable1 = new C();
		C.staticMethod();
		localVariable.nonStaticMethod();
		C.staticMethod();
		parameter.nonStaticMethod();
		C.staticMethod();
		D.staticField.nonStaticMethod();
		C.staticMethod();

	}

	void nonstaticCallingMethod(final C parameter) {
		final C localVariable = new C();
		C.staticMethod();
		localVariable.nonStaticMethod();
		C.staticMethod();
		parameter.nonStaticMethod();
		C.staticMethod();
		D.staticField.nonStaticMethod();
		C.staticMethod();
		this.nonStaticField.nonStaticMethod();
		C.staticMethod();
	}
}
