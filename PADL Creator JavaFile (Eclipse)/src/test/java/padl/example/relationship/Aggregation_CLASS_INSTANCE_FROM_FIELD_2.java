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

public class Aggregation_CLASS_INSTANCE_FROM_FIELD_2 {
	private static A[] a;
	public static void foo() {
		// We expect CLASS_INSTANCE_FROM_FIELD
		Aggregation_CLASS_INSTANCE_FROM_FIELD_2.a[0].instanceMethod();
	}
}
