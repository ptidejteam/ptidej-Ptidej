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
package ptidej.viewer.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import ptidej.viewer.ui.IWindow;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/20
 */
public class SingletonBag {
	private static Map Windows = new HashMap();
	public static IWindow getInstance(final Class aWindow) {
		if (!SingletonBag.Windows.containsKey(aWindow)) {
			try {
				final IWindow window =
					(IWindow) aWindow
						.getDeclaredConstructor(new Class[0])
						.newInstance(new Object[0]);
				SingletonBag.Windows.put(aWindow, window);
			}
			catch (final IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (final SecurityException e) {
				e.printStackTrace();
			}
			catch (final InstantiationException e) {
				e.printStackTrace();
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
			catch (final NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return (IWindow) SingletonBag.Windows.get(aWindow);
	}
}
