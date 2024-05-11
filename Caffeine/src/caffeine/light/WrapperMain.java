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
package caffeine.light;

import java.lang.reflect.Method;

import util.io.ProxyConsole;

/**
 * @version 	0.1
 * @author		Yann-Gaël Guéhéneuc
 */
public final class WrapperMain {
	public static void main(String[] args) {
		try {
			Class toBeRun = Class.forName(args[0]);
			Method mainMethod =
				toBeRun.getMethod("main", new Class[] { String[].class });
			final long startTime = System.currentTimeMillis();
			mainMethod.invoke(null, new Object[] { new String[0] });
			final long endTime = System.currentTimeMillis();
			System.out.println();
			System.out.println(endTime - startTime);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
