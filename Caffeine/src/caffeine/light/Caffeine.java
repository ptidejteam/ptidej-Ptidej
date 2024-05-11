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

import java.util.Iterator;
import java.util.Map;

import util.io.OutputMonitor;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.request.MethodEntryRequest;

/**
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */
public final class Caffeine {
	//	private static final String MainClassName = "caffeine.example.queens.NQueens";
	//	private static final String[] ClassNameFilters =
	//		new String[] { "caffeine.example.queens.Board" };
	private static final String MainClassName =
		"junit.samples.money.MoneyTest";
	private static final String[] ClassNameFilters =
		new String[] {
			"junit.samples.money.MoneyTest",
			"junit.samples.money.MoneyBag" };

	public static void main(final String[] args) throws Exception {
		// I build a remote JVM.
		final VirtualMachineManager jvmManager =
			Bootstrap.virtualMachineManager();
		final LaunchingConnector launchingConnector =
			jvmManager.defaultConnector();
		final Map arguments = launchingConnector.defaultArguments();
		final Iterator iterator = arguments.values().iterator();
		while (iterator.hasNext()) {
			final Argument argument = (Argument) iterator.next();
			if (argument.name().equals("options")) {
				argument.setValue("-classpath \";../JUnit;.\"");
			}
			if (argument.name().equals("main")) {
				argument.setValue(
					WrapperMain.class.getName()
						+ ' '
						+ Caffeine.MainClassName);
			}
		}
		final VirtualMachine jvm = launchingConnector.launch(arguments);

		// I request method entry events.
		for (int i = 0; i < Caffeine.ClassNameFilters.length; i++) {
			final MethodEntryRequest methodEntryRequest =
				jvm.eventRequestManager().createMethodEntryRequest();
			methodEntryRequest.addClassFilter(Caffeine.ClassNameFilters[i]);
			methodEntryRequest.enable();
		}

		// A thread to catch and to display the output of the remote JVM.
		final OutputMonitor streamOutputMonitor =
			new OutputMonitor(
				"Remote JVM Ouput Stream Monitor",
				"(Remote JVM)",
				jvm.process().getInputStream(),
				System.out);
		streamOutputMonitor.start();

		// A thread to catch and to display the output of the remote JVM.
		final OutputMonitor streamErrorMonitor =
			new OutputMonitor(
				"Remote JVM Error Stream Monitor",
				"(Remote JVM)",
				jvm.process().getErrorStream(),
				System.err);
		streamErrorMonitor.start();

		// I get all the events from the remote JVM.
		try {
			while (jvm.process() != null) {
				jvm.resume();
				//	final EventSet eventSet = jvm.eventQueue().remove(1000);
				//	if (eventSet != null) {
				//		final Iterator events = eventSet.iterator();
				//		while (events.hasNext()) {
				//			System.out.println(events.next());
				//		}
				//	}
			}
		}
		catch (final VMDisconnectedException vmde) {
		}
	}
}
