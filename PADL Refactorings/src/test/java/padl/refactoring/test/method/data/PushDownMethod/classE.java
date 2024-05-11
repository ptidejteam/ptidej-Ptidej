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
package padl.refactoring.test.method.data.PushDownMethod;

public class classE extends classD {
	void fofo() {
		System.out.println("je suis fofo ");
	}
	void fo(int entier) {
		System.out.println("le double du nombre " + entier + " est "
			+ entier * 2);
	}
}
