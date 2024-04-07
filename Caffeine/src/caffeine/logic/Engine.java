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

import java.io.Writer;
import java.util.List;

import JIP.engine.JIPEngine;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;

/**
 * This class represents the main and only access point
 * to Caffeine. It is a singleton, because the communication
 * between the program under analysis and the underlying Prolog
 * engine is performed through static methods.
 * 
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.3
 */
public final class Engine extends JIPEngine {
	private static Engine UNIQUE_INSTANCE;

	public static Engine getUniqueInstance(
		final Writer traceWriter,
		final String[] classNameFilter,
		final int requiredEventMask,
		final String[][] fieldAccesses,
		final VirtualMachine jvm,
		final List caffeineListeners) {

		if (Engine.UNIQUE_INSTANCE == null) {
			Engine.UNIQUE_INSTANCE =
				new Engine(
					traceWriter,
					classNameFilter,
					requiredEventMask,
					fieldAccesses,
					jvm,
					caffeineListeners);
		}
		else {
			System.err.println(
				"Warning! One and only one engine can exist at a give time!");
			throw new RuntimeException("Reset the current engine first, using the resetUniqueInstance() static method");
		}
		return Engine.UNIQUE_INSTANCE;
	}
	public static void resetUniqueInstance() {
		if (Engine.UNIQUE_INSTANCE != null
			&& Engine.UNIQUE_INSTANCE.jvm != null) {

			try {
				Engine.UNIQUE_INSTANCE.jvm.exit(0);
			}
			catch (final VMDisconnectedException vmde) {
			}
		}
		EventManager.reset();
		Engine.UNIQUE_INSTANCE = null;
	}

	// I add these fields to "my" JIPEngine to pass
	// them along to the EventManager.
	private final List caffeineListeners;
	private final String[] classNameFilter;
	private final String[][] fieldAccesses;
	private final VirtualMachine jvm;
	private final int requiredEventMask;
	private final Writer traceWriter;

	private Engine(
		final Writer traceWriter,
		final String[] classNameFilter,
		final int requiredEventMask,
		final String[][] fieldAccesses,
		final VirtualMachine jvm,
		final List caffeineListeners) {

		this.traceWriter = traceWriter;
		this.classNameFilter = classNameFilter;
		this.requiredEventMask = requiredEventMask;
		this.fieldAccesses = fieldAccesses;
		this.jvm = jvm;
		this.caffeineListeners = caffeineListeners;
	}

	public List getCaffeineListeners() {
		return this.caffeineListeners;
	}
	public String[] getClassNameFilter() {
		return this.classNameFilter;
	}
	public String[][] getFieldAccesses() {
		return this.fieldAccesses;
	}
	public int getRequiredEventMask() {
		return this.requiredEventMask;
	}
	public Writer getTraceWriter() {
		return this.traceWriter;
	}
	public VirtualMachine getVirtualMachine() {
		return this.jvm;
	}
}
