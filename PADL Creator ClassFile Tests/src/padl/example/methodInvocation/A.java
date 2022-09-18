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
package padl.example.methodInvocation;

import padl.example.methodInvocation1.B;
import padl.example.methodInvocation1.C;
import padl.example.methodInvocation1.D;

public abstract class A extends B implements
		C, D {
	public boolean isEmpty() {
		size();
		return false;
	}

}
