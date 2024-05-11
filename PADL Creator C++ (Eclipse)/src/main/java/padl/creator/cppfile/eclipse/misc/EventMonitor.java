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
package padl.creator.cppfile.eclipse.misc;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;

public class EventMonitor extends Thread {
	private boolean connected = true;
	private final VirtualMachine vm;

	public EventMonitor(final VirtualMachine aVirtualMachine) {
		this.vm = aVirtualMachine;
	}
	public void run() {
		final EventQueue queue = this.vm.eventQueue();
		while (this.connected) {
			try {
				final EventSet eventSet = queue.remove();
				final EventIterator it = eventSet.eventIterator();
				while (it.hasNext()) {
					this.handleEvent(it.nextEvent());
				}
				eventSet.resume();
			}
			catch (final InterruptedException exc) {
				// Ignore
			}
			catch (final VMDisconnectedException discExc) {
				this.handleDisconnectedException();
				break;
			}
		}
	}
	/***
	 * A VMDisconnectedException has happened while dealing with
	 * another event. We need to flush the event queue, dealing only
	 * with exit events (VMDeath, VMDisconnect) so that we terminate
	 * correctly.
	 */
	private synchronized void handleDisconnectedException() {
		final EventQueue queue = this.vm.eventQueue();
		while (this.connected) {
			try {
				final EventSet eventSet = queue.remove();
				final EventIterator iter = eventSet.eventIterator();
				while (iter.hasNext()) {
					final Event event = iter.nextEvent();
					if (event instanceof VMDeathEvent) {
						// Nothing to do.
					}
					else if (event instanceof VMDisconnectEvent) {
						this.connected = false;
					}
				}
				eventSet.resume();
			}
			catch (final InterruptedException exc) {
				// ignore
			}
		}
	}
	/**
	 * Dispatch incoming events
	 */
	private void handleEvent(final Event event) {
		if (event instanceof ExceptionEvent) {
		}
		else if (event instanceof ModificationWatchpointEvent) {
		}
		else if (event instanceof MethodEntryEvent) {
		}
		else if (event instanceof MethodExitEvent) {
		}
		else if (event instanceof StepEvent) {
		}
		else if (event instanceof ThreadDeathEvent) {
		}
		else if (event instanceof ClassPrepareEvent) {
		}
		else if (event instanceof VMStartEvent) {
		}
		else if (event instanceof VMDeathEvent) {
			// Nothing to do.
		}
		else if (event instanceof VMDisconnectEvent) {
			this.connected = false;
		}
		else {
			throw new Error("Unexpected event type");
		}
	}
}
