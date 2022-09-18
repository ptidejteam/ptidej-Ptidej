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
package caffeine.logic;

import java.util.Iterator;

import JIP.engine.JIPErrorEvent;
import JIP.engine.JIPErrorListener;
import JIP.engine.JIPEvent;
import JIP.engine.JIPEventListener;
import JIP.engine.JIPTraceEvent;
import JIP.engine.JIPTraceListener;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.1
 */
public final class PrologListener
	implements JIPErrorListener, JIPEventListener, JIPTraceListener {

	private boolean keepOn = true;
	private JIPTraceEvent traceEvent;

	public void callNotified(final JIPTraceEvent traceEvent) {
		// System.out.println("callNotified");
		this.keepOn(traceEvent);
	}
	public void closeNotified(final JIPEvent jipEvent) {
		// System.out.println("closeNotified");
	}
	private void display(final JIPTraceEvent traceEvent) {
		System.err.println(traceEvent.getTerm());
		System.err.println(traceEvent.getHeight());
	}
	public void endNotified(final JIPEvent jipEvent) {
		// System.out.println("endNotified");
	}
	public void errorNotified(final JIPErrorEvent errorEvent) {
		System.err.println("An error occured: " + errorEvent);
		errorEvent.consume();
	}
	public void exitNotified(final JIPTraceEvent traceEvent) {
		// System.out.println("exitNotified");
		this.keepOn(traceEvent);
	}
	public void failNotified(final JIPTraceEvent traceEvent) {
		// System.out.println("failNotified");
		this.keepOn(traceEvent);
	}
	public void foundNotified(final JIPTraceEvent traceEvent) {
		// System.out.println("foundNotified");
		this.display(traceEvent);
		this.keepOn(traceEvent);
	}
	private void keepOn(final JIPTraceEvent traceEvent) {
		if (this.keepOn) {
			traceEvent.step();
		}
		else {
			this.traceEvent = traceEvent;
		}
	}
	public void moreNotified(final JIPEvent jipEvent) {
		// System.out.println("moreNotified");
	}
	public void openNotified(final JIPEvent jipEvent) {
		// System.out.println("openNotified");
	}
	public void redoNotified(final JIPTraceEvent traceEvent) {
		// System.out.println("redoNotified");
		this.keepOn(traceEvent);
	}
	public boolean resume() {
		if (this.traceEvent != null) {
			this.keepOn = true;
			this.traceEvent.step();
			return true;
		}
		return false;
	}
	public void solutionNotified(final JIPEvent jipEvent) {
		final StringBuffer buffer = new StringBuffer(25);

		buffer.append("Solution: ");
		buffer.append(jipEvent.getTerm());
		System.out.println(buffer.toString());
		buffer.setLength(0);

		final long time =
			EventManager.getVMDeathTimeStamp()
				- EventManager.getVMStartTimeStamp();
		final long steps = EventManager.getNumberOfSteps();
		buffer.append("JVM running time: ");
		buffer.append(time);
		buffer.append(" ms.");
		if (steps > 0) {
			buffer.append('(');
			buffer.append(time / steps);
			buffer.append(" ms. per step for the ");
			buffer.append(steps);
			buffer.append(" steps)");
		}
		System.out.println(buffer.toString());

		// Yann 2003/07/31: Listener.
		// I notify all the listerners that a new event
		// has been built and is sent to the Prolog engine.
		final Engine engine = (Engine) jipEvent.getSource();
		final Iterator iterator = engine.getCaffeineListeners().iterator();
		while (iterator.hasNext()) {
			((EngineListener) iterator.next()).engineTerminated(
				jipEvent.getTerm(),
				engine,
				time,
				steps);
		}
	}
	public boolean suspend() {
		if (this.traceEvent != null) {
			this.keepOn = false;
			return true;
		}
		return false;
	}
	public void termNotified(final JIPEvent jipEvent) {
		// System.out.print("termNotified");
	}
}
