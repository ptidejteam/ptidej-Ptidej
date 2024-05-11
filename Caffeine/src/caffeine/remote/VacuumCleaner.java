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

import util.io.ProxyConsole;
import caffeine.Constants;

/**
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */
public final class VacuumCleaner extends Thread {
	public VacuumCleaner(final String name, final Thread mainThread) {
		super(mainThread.getThreadGroup().getParent(), name);
		this.setPriority(Thread.MIN_PRIORITY);
		// This thread should not be a daemon, because we want
		// it to keep running event after the program thread(s)
		// terminated, to get all finalizer calls and class
		// unload events.
	}
	public void run() {
		try {
			boolean isAnyUserThreadAlive = true;

			while (isAnyUserThreadAlive) {
				VacuumCleaner.clean();

				final int numberOfActiveThreads = Thread.activeCount();
				final Thread[] activeThreads =
					new Thread[numberOfActiveThreads];
				Thread.enumerate(activeThreads);
				isAnyUserThreadAlive = false;
				for (int i = 0; i < numberOfActiveThreads
						&& !isAnyUserThreadAlive; i++) {
					for (int j = 0; j < Constants.NUMBER_OF_USER_SYSTEM_THREAD_NAMES; j++) {
						if (activeThreads[i] != null
								&& activeThreads[i].getName().equals(
									Constants.USER_SYSTEM_THREAD_NAMES[j])) {
							isAnyUserThreadAlive = true;
						}
					}
				}
			}
			//  if (Caffeine.Debug) {
			//  	System.err.println("Vacuum cleaner terminated.");
			//	}
			for (int i = 0; i < Constants.VACUUM_CLEANER_LATENT_PERIOD; i++) {

				VacuumCleaner.clean();
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private static void clean() throws InterruptedException {
		Thread.yield();
		Thread.sleep(Constants.VACUUM_CLEANER_WAKE_UP_TIME);
		System.gc();
		System.runFinalization();
	}
}
