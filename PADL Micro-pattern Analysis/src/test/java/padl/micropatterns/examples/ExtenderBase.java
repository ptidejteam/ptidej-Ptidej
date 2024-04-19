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

public class ExtenderBase {
	public String toString() {
		// Just not to be Extender
		return super.toString();
	}
	//private static int aField = 0;
	public int TestMethod1() {return 0;};
	public int TestMethod2() {return 0;};
	public int TestMethod3() {return 0;};
	public int TestMethod4() {return 0;};
}
