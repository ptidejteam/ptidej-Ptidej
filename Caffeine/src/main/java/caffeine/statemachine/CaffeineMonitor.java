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
package caffeine.statemachine;

import java.util.Iterator;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;

public final class CaffeineMonitor implements Runnable {
	private static final int UNHAPPY = 0;
	private static final int SET_QUEEN = 1;
	private static final int PLACE_NEXT_QUEEN = 2;

	private EventQueue eventQueue;

	public CaffeineMonitor(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}
	public void run() {
		System.out.println("Monitor started.");
		try {
			final StringBuffer buffer = new StringBuffer();
			int removeQueenCounter = 0;
			int state = CaffeineMonitor.UNHAPPY;

			while (true) {

				final VirtualMachine jvm = this.eventQueue.virtualMachine();
				final EventSet eventSet = this.eventQueue.remove(1000);
				if (eventSet != null) {

					final Iterator iterator = eventSet.iterator();
					while (iterator.hasNext()) {

						Event event = (Event) iterator.next();
						if (event instanceof LocatableEvent) {

							final LocatableEvent locatableEvent = (LocatableEvent) event;
							if (locatableEvent instanceof MethodEntryEvent) {
								final String methodName = ((MethodEntryEvent) locatableEvent).method().name();

								/*****************************************
								 * Beginning of the request on the trace *
								 *****************************************
								 * We want to know the number of times,  *
								 * the algorithm calls the removeQueen() *
								 * method "just after" a call to the     *
								 * setQueen() method.                    *
								 * "Just after" means that we do not     *
								 * care about methods other than:        *
								 *     - setQueen()                      *
								 *     - placeNextQueen()                *
								 *     - removeQueen()                   *
								 *****************************************/

								if (methodName.equals("setQueen")) {
									state = CaffeineMonitor.SET_QUEEN;
								}
								else if (methodName.equals("placeNextQueen")) {
									if (state == CaffeineMonitor.SET_QUEEN) {
										state = CaffeineMonitor.PLACE_NEXT_QUEEN;
									}
									else {
										state = CaffeineMonitor.UNHAPPY;
									}
								}
								else if (methodName.equals("removeQueen")) {
									if (state == CaffeineMonitor.PLACE_NEXT_QUEEN) {
										state = CaffeineMonitor.UNHAPPY;

										removeQueenCounter++;
										/*
										buffer.append("Entering method: ");
										buffer.append(methodName);
										buffer.append(" (");
										buffer.append(removeQueenCounter);
										buffer.append(')');
										buffer.append('\n');
										*/
										buffer.append("I remove just after having placed her for the ");
										buffer.append(removeQueenCounter);
										buffer.append(" time(s)\n");

									}
									else {
										state = CaffeineMonitor.UNHAPPY;
									}
								}

								/***********************************
								 * End of the request on the trace *
								 ***********************************/

							}

							System.out.print(buffer);
							buffer.setLength(0);
						}
					}

					// I make sure the JVM keeps on running after it stops to
					// put the events in the event queue.
					jvm.resume();
				}
			}
		}
		catch (VMDisconnectedException vmde) {
		}
		catch (InterruptedException ie) {
		}
	}
}
