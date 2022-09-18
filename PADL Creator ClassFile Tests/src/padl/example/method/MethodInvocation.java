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
package padl.example.method;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/11
 */
@SuppressWarnings("unused")
public class MethodInvocation {
	private int immutable;
	private int mutable;

	public MethodInvocation() {
		this.immutable = 0;
	}
	public void SomeMethod() {
		//this.mutable = CalledMethod(0);
		this.immutable = 0;
		this.mutable = 0;
	}
	public int CalledMethod(int value) {
		return value;
	}
}
