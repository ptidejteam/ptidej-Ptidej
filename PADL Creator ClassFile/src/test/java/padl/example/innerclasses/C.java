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
package padl.example.innerclasses;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/03
 */
public class C implements IA.IAMember, B.BMember{
	public B.BMember bMember;
	
	public static void main(final String[] args) {
		B.BMember b = (B.BMember) new Object();
		 System.out.println(b);
	}
	public void foo(final B.BMember aBMember) {
		 System.out.println(aBMember);
	}
}
