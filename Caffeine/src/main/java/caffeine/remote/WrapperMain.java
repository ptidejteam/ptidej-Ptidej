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

import java.lang.reflect.Method;

import util.io.ProxyConsole;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.2
 */
public final class WrapperMain {
	public static void main(final String[] args) {
		// These threads seem to replace well a call to
		// 		System.runFinalizersOnExit(true);
		// However, this is not true because the thread
		// added to the shutdown hook runs *after* all
		// other threads are terminated, including the
		// Finalizer thread.
		//		Runtime.getRuntime().addShutdownHook(new Thread() {
		//			public void run() {
		//				final long time = System.currentTimeMillis();
		//				while (System.currentTimeMillis() - time < 1000) {
		//					System.gc();
		//					System.runFinalization();
		//				}
		//			}
		//		});

		if (args[2].equals("true")) {
			// Yann 2002/06/14: Question?
			// It seems (according to caffeine.test.composition5.Composition)
			// that this thread is not very efficient and should be replaced
			// with call to the GC at the end of each methods.
			// Yann 2002/06/14: Answer!
			// Example caffeine.test.composition7.Composition shows that we
			// need both the VacuumCleaner (for finalizer events *after* the
			// the program end) and GC calls at the end of each methods.
			new VacuumCleaner(
				"Caffeine Vacuum Cleaner",
				Thread.currentThread())
				.start();
			// TODO
			// Yann 2022/09/14: Hook
			// I should use some JVM hook to ensure that finalizers are called...
			//	System.runFinalizersOnExit(true);
		}
		try {
			final Loader classLoader = new Loader(args);
			// final ClassLoader classLoader = WrapperMain.class.getClassLoader();
			final Class classToBeRun = classLoader.loadClass(args[0]);
			final Method mainMethod =
				classToBeRun.getMethod(
					"main",
					new Class[] { String[].class });

			final long startTime = System.currentTimeMillis();
			mainMethod.invoke(null, new Object[] { new String[0] });
			final long endTime = System.currentTimeMillis();
			final StringBuffer buffer = new StringBuffer(25);
			buffer.append("Duration of main() method: ");
			buffer.append(endTime - startTime);
			buffer.append(" ms.");
			System.out.println(buffer.toString());
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
