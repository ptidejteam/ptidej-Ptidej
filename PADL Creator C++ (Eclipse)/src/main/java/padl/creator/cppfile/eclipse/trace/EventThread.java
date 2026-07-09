/*
 * @(#)EventThread.java	1.4 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright (c) 1997-2001 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package padl.creator.cppfile.eclipse.trace;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;
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
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.ExceptionRequest;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.ModificationWatchpointRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;

/**
 * This class processes incoming JDI events and displays them
 *
 * @version     @(#) EventThread.java 1.4 03/01/23 16:22:08
 * @author Robert Field
 */
public class EventThread extends Thread {

	/**
	 * This class keeps context on events in one thread.
	 * In this implementation, context is the indentation prefix.
	 */
	class ThreadTrace {
		final ThreadReference thread;
		final String baseIndent;
		static final String threadDelta = "                     ";
		StringBuffer indent;

		ThreadTrace(final ThreadReference thread) {
			this.thread = thread;
			this.baseIndent = EventThread.nextBaseIndent;
			this.indent = new StringBuffer(this.baseIndent);
			EventThread.nextBaseIndent += ThreadTrace.threadDelta;
			this.println("====== " + thread.name() + " ======");
		}

		void exceptionEvent(final ExceptionEvent event) {
			this.println("Exception: " + event.exception() + " catch: "
					+ event.catchLocation());

			// Step to the catch
			final EventRequestManager mgr =
				EventThread.this.vm.eventRequestManager();
			final StepRequest req =
				mgr.createStepRequest(
					this.thread,
					StepRequest.STEP_MIN,
					StepRequest.STEP_INTO);
			req.addCountFilter(1); // next step only
			req.setSuspendPolicy(EventRequest.SUSPEND_ALL);
			req.enable();
		}

		void fieldWatchEvent(final ModificationWatchpointEvent event) {
			final Field field = event.field();
			final Value value = event.valueToBe();
			this.println("    " + field.name() + " = " + value);
		}

		void methodEntryEvent(final MethodEntryEvent event) {
			this.println(event.method().name() + "  --  "
					+ event.method().declaringType().name());
			this.indent.append("| ");
		}

		void methodExitEvent(final MethodExitEvent event) {
			this.indent.setLength(this.indent.length() - 2);
		}

		private void println(final String str) {
			EventThread.this.writer.print(this.indent);
			EventThread.this.writer.println(str);
		}

		// Step to exception catch
		void stepEvent(final StepEvent event) {
			// Adjust call depth
			int cnt = 0;
			this.indent = new StringBuffer(this.baseIndent);
			try {
				cnt = this.thread.frameCount();
			}
			catch (final IncompatibleThreadStateException exc) {
			}
			while (cnt-- > 0) {
				this.indent.append("| ");
			}

			final EventRequestManager mgr =
				EventThread.this.vm.eventRequestManager();
			mgr.deleteEventRequest(event.request());
		}

		void threadDeathEvent(final ThreadDeathEvent event) {
			this.indent = new StringBuffer(this.baseIndent);
			this.println("====== " + this.thread.name() + " end ======");
		}
	}
	private final VirtualMachine vm; // Running VM
	private final String[] excludes; // Packages to exclude

	private final PrintWriter writer; // Where output goes

	static String nextBaseIndent = ""; // Starting indent for next thread
	private boolean connected = true; // Connected to VM

	private boolean vmDied = true; // VMDeath occurred

	// Maps ThreadReference to ThreadTrace instances
	private final Map<ThreadReference, ThreadTrace> traceMap =
		new HashMap<ThreadReference, ThreadTrace>();

	public EventThread(
		final VirtualMachine vm,
		final String[] excludes,
		final PrintWriter writer) {
		super("event-handler");
		this.vm = vm;
		this.excludes = excludes;
		this.writer = writer;
	}

	/**
	 * A new class has been loaded.  
	 * Set watchpoints on each of its fields
	 */
	private void classPrepareEvent(final ClassPrepareEvent event) {
		final EventRequestManager mgr = this.vm.eventRequestManager();
		final List<?> fields = event.referenceType().visibleFields();
		for (final Iterator<?> it = fields.iterator(); it.hasNext();) {
			final Field field = (Field) it.next();
			final ModificationWatchpointRequest req =
				mgr.createModificationWatchpointRequest(field);
			for (int i = 0; i < this.excludes.length; ++i) {
				req.addClassExclusionFilter(this.excludes[i]);
			}
			req.setSuspendPolicy(EventRequest.SUSPEND_NONE);
			req.enable();
		}
	}

	private void exceptionEvent(final ExceptionEvent event) {
		final ThreadTrace trace = this.traceMap.get(event.thread());
		if (trace != null) { // only want threads we care about
			trace.exceptionEvent(event); // Forward event
		}
	}

	// Forward event for thread specific processing
	private void fieldWatchEvent(final ModificationWatchpointEvent event) {
		this.threadTrace(event.thread()).fieldWatchEvent(event);
	}

	/***
	 * A VMDisconnectedException has happened while dealing with
	 * another event. We need to flush the event queue, dealing only
	 * with exit events (VMDeath, VMDisconnect) so that we terminate
	 * correctly.
	 */
	synchronized void handleDisconnectedException() {
		final EventQueue queue = this.vm.eventQueue();
		while (this.connected) {
			try {
				final EventSet eventSet = queue.remove();
				final EventIterator iter = eventSet.eventIterator();
				while (iter.hasNext()) {
					final Event event = iter.nextEvent();
					if (event instanceof VMDeathEvent) {
						this.vmDeathEvent((VMDeathEvent) event);
					}
					else if (event instanceof VMDisconnectEvent) {
						this.vmDisconnectEvent((VMDisconnectEvent) event);
					}
				}
				eventSet.resume(); // Resume the VM
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
			this.exceptionEvent((ExceptionEvent) event);
		}
		else if (event instanceof ModificationWatchpointEvent) {
			this.fieldWatchEvent((ModificationWatchpointEvent) event);
		}
		else if (event instanceof MethodEntryEvent) {
			this.methodEntryEvent((MethodEntryEvent) event);
		}
		else if (event instanceof MethodExitEvent) {
			this.methodExitEvent((MethodExitEvent) event);
		}
		else if (event instanceof StepEvent) {
			this.stepEvent((StepEvent) event);
		}
		else if (event instanceof ThreadDeathEvent) {
			this.threadDeathEvent((ThreadDeathEvent) event);
		}
		else if (event instanceof ClassPrepareEvent) {
			this.classPrepareEvent((ClassPrepareEvent) event);
		}
		else if (event instanceof VMStartEvent) {
			this.vmStartEvent((VMStartEvent) event);
		}
		else if (event instanceof VMDeathEvent) {
			this.vmDeathEvent((VMDeathEvent) event);
		}
		else if (event instanceof VMDisconnectEvent) {
			this.vmDisconnectEvent((VMDisconnectEvent) event);
		}
		else {
			throw new Error("Unexpected event type");
		}
	}

	// Forward event for thread specific processing
	private void methodEntryEvent(final MethodEntryEvent event) {
		this.threadTrace(event.thread()).methodEntryEvent(event);
	}

	// Forward event for thread specific processing
	private void methodExitEvent(final MethodExitEvent event) {
		this.threadTrace(event.thread()).methodExitEvent(event);
	}

	/**
	 * Run the event handling thread.  
	 * As long as we are connected, get event sets off 
	 * the queue and dispatch the events within them.
	 */
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

	/**
	 * Create the desired event requests, and enable 
	 * them so that we will get events.
	 * @param excludes     Class patterns for which we don't want events
	 * @param watchFields  Do we want to watch assignments to fields
	 */
	public void setEventRequests(final boolean watchFields) {
		final EventRequestManager mgr = this.vm.eventRequestManager();

		// want all exceptions
		final ExceptionRequest excReq =
			mgr.createExceptionRequest(null, true, true);
		// suspend so we can step
		excReq.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		excReq.enable();

		final MethodEntryRequest menr = mgr.createMethodEntryRequest();
		for (int i = 0; i < this.excludes.length; ++i) {
			menr.addClassExclusionFilter(this.excludes[i]);
		}
		menr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		menr.enable();

		final MethodExitRequest mexr = mgr.createMethodExitRequest();
		for (int i = 0; i < this.excludes.length; ++i) {
			mexr.addClassExclusionFilter(this.excludes[i]);
		}
		mexr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		mexr.enable();

		final ThreadDeathRequest tdr = mgr.createThreadDeathRequest();
		// Make sure we sync on thread death
		tdr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		tdr.enable();

		if (watchFields) {
			final ClassPrepareRequest cpr = mgr.createClassPrepareRequest();
			for (int i = 0; i < this.excludes.length; ++i) {
				cpr.addClassExclusionFilter(this.excludes[i]);
			}
			cpr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
			cpr.enable();
		}
	}

	// Forward event for thread specific processing
	private void stepEvent(final StepEvent event) {
		this.threadTrace(event.thread()).stepEvent(event);
	}

	void threadDeathEvent(final ThreadDeathEvent event) {
		final ThreadTrace trace =
			(ThreadTrace) this.traceMap.get(event.thread());
		if (trace != null) { // only want threads we care about
			trace.threadDeathEvent(event); // Forward event
		}
	}

	/**
	 * Returns the ThreadTrace instance for the specified thread,
	 * creating one if needed.
	 */
	ThreadTrace threadTrace(final ThreadReference thread) {
		ThreadTrace trace = this.traceMap.get(thread);
		if (trace == null) {
			trace = new ThreadTrace(thread);
			this.traceMap.put(thread, trace);
		}
		return trace;
	}

	public void vmDeathEvent(final VMDeathEvent event) {
		this.vmDied = true;
		this.writer.println("-- The application exited --");
	}

	public void vmDisconnectEvent(final VMDisconnectEvent event) {
		this.connected = false;
		if (!this.vmDied) {
			this.writer.println("-- The application has been disconnected --");
		}
	}

	private void vmStartEvent(final VMStartEvent event) {
		this.writer.println("-- VM Started --");
	}
}
