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
package caffeine.remote;
/**
 * @version 	0.1
 * @author		Yann-Gaël Guéhéneuc
 */
public final class CaffeineWrapper {
	public static double caffeineReturnedValueWrapper(final double value) {
		return value;
	}
	public static float caffeineReturnedValueWrapper(final float value) {
		return value;
	}
	public static int caffeineReturnedValueWrapper(final int value) {
		return value;
	}
	public static long caffeineReturnedValueWrapper(final long value) {
		return value;
	}
	public static Object caffeineReturnedValueWrapper(final Object value) {
		return value;
	}
	public static void caffeineUniqueExit(final int code) {
		System.exit(code);
	}
}
