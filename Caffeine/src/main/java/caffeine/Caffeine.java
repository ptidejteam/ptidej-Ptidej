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
package caffeine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import JIP.engine.JIPSyntaxErrorException;
import JIP.engine.JIPTerm;
import caffeine.logic.Engine;
import caffeine.logic.EngineListener;
import caffeine.logic.PrologListener;
import caffeine.remote.WrapperMain;
import util.io.NullWriter;
import util.io.OutputMonitor;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @version 0.4
 * @author Yann-Gaël Guéhéneuc
 */
public final class Caffeine {
	private static Caffeine UNIQUE_INSTANCE;

	private static String getStreamContent(final InputStream stream) {
		final StringBuffer buffer = new StringBuffer();
		try {
			int i;
			while ((i = stream.read()) > 0) {
				if (Constants.DEBUG) {
					System.out.print((char) i);
					System.out.flush();
				}
				buffer.append((char) i);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		} catch (NullPointerException npe) {
			System.err.println("Is the rule available?");
			npe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return buffer.toString();
	}

	public synchronized static Caffeine getUniqueInstance() {
		if (Caffeine.UNIQUE_INSTANCE == null) {
			Caffeine.UNIQUE_INSTANCE = new Caffeine();
		}
		return Caffeine.UNIQUE_INSTANCE;
	}

	private final List caffeineListerners = new ArrayList();
	private Engine engine;

	private Caffeine() {
	}

	public void addCaffeineListerner(final EngineListener eventListener) {
		this.caffeineListerners.add(eventListener);
	}

	private void initialize(final String traceOutputFileName, final String ruleFileName, final String[] classNameFiler,
			final int requiredEventMask, final String[][] fieldAccesses, final VirtualMachine jvm,
			final PrologListener prologListener) throws ClassNotFoundException {

		Writer traceWriter;
		try {
			traceWriter = ProxyDisk.getInstance().fileTempOutput(traceOutputFileName);
		} catch (final Exception e) {
			traceWriter = new NullWriter();
		}

		this.engine = Engine.getUniqueInstance(traceWriter, classNameFiler, requiredEventMask, fieldAccesses, jvm,
				this.caffeineListerners);
		this.engine.addErrorListener(prologListener);
		this.engine.addEventListener(prologListener);
		this.engine.addTraceListener(prologListener);
		// this.jipEngine.setTrace(true);

		try {
			final StringBuffer predicates = new StringBuffer();
			predicates.append(Caffeine.getStreamContent(Engine.class.getResourceAsStream("Rules.pl")));
			predicates.append(Caffeine.getStreamContent(new FileInputStream(ruleFileName)));
			this.engine.consultStream(new ReaderInputStream(new StringReader(predicates.toString())), "Predicates.pl");
		} catch (final FileNotFoundException fnfe) {
			System.err.println("Is the rule available?");
			fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		} catch (final JIPSyntaxErrorException jipsee) {
			System.err.println("Error while adding the clauses in the engine.");
			jipsee.printStackTrace(ProxyConsole.getInstance().errorOutput());
		} catch (final IOException ioe) {
			System.err.println("Error while adding the clauses in the engine.");
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Yann 2003/07/31: Listener.
		// I notify all the listeners that a new
		// engine has been initialized.
		final Iterator iterator = this.caffeineListerners.iterator();
		while (iterator.hasNext()) {
			((EngineListener) iterator.next()).engineInitialized(this.engine);
		}

		System.out.println("Caffeine initialized");
	}

	public void removeCaffeineListerner(final EngineListener eventListener) {
		this.caffeineListerners.remove(eventListener);
	}

	private void start() {
		try {
			final StringBuffer query = new StringBuffer();
			query.append(Caffeine.getStreamContent(Engine.class.getResourceAsStream("Query.pl")));
			this.engine.openQuery(JIPTerm.parseQuery(query.toString()));
			System.out.println(query.toString());
			
		} catch (final JIPSyntaxErrorException jipsee) {
			System.err.println("Error while querying the engine.");
			jipsee.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Yann 2003/07/31: Listener.
		// I notify all the listeners that a new
		// engine has been started.
		final Iterator iterator = this.caffeineListerners.iterator();
		while (iterator.hasNext()) {
			((EngineListener) iterator.next()).engineStarted(this.engine);
		}

		System.out.println("Caffeine started");
	}

	public void start(final String traceOutputFileName, final String ruleFileName, final String classPath,
			final String mainClassName, final String[] classNameFilter, final int requiredEventMask) {

		this.start(traceOutputFileName, ruleFileName, classPath, mainClassName, classNameFilter, requiredEventMask,
				null);
	}

	public void start(final String traceOutputFileName, final String ruleFileName, final String classPath,
			final String mainClassName, final String[] classNameFilter, final int requiredEventMask,
			final String[][] fieldAccesses) {

		try {
			// Yann 2002/07/09: Composition relationships management!
			for (int i = 0; fieldAccesses != null && i < fieldAccesses.length; i++) {
				for (int j = 0; j < fieldAccesses[i].length; j++) {
					if (fieldAccesses[i][j].equals("java.util.Vector")) {
						fieldAccesses[i][j] = "caffeine.remote.Vector";
					} else if (fieldAccesses[i][j].equals("java.util.Hashtable")) {
						fieldAccesses[i][j] = "caffeine.remote.Hashtable";
					}
				}
			}

			// I create a remote Java Virtual Machine.
			final VirtualMachineManager jvmManager = Bootstrap.virtualMachineManager();
			final LaunchingConnector launchingConnector = jvmManager.defaultConnector();
			final Map arguments = launchingConnector.defaultArguments();
			// TODO: Replace the iteration by a direct access using
			// get("options") and get("main").
			final Iterator iterator = arguments.values().iterator();
			while (iterator.hasNext()) {
				final Connector.Argument argument = (Connector.Argument) iterator.next();
				if (argument.name().equals("options")) {
					argument.setValue("-classpath \"" + classPath + ";.\"");
				} else if (argument.name().equals("home")) {
					argument.setValue("");
				} else if (argument.name().equals("main")) {
					final StringBuffer newArgument = new StringBuffer();
					newArgument.append(WrapperMain.class.getName());
					newArgument.append(' ');
					newArgument.append(mainClassName);

					newArgument.append(' ');
					newArgument.append((requiredEventMask
							& Constants.GENERATE_COLLECTION_EVENT) == Constants.GENERATE_COLLECTION_EVENT);

					newArgument.append(' ');
					newArgument.append((requiredEventMask
							& Constants.GENERATE_FINALIZER_ENTRY_EVENT) == Constants.GENERATE_FINALIZER_ENTRY_EVENT
							|| (requiredEventMask
									& Constants.GENERATE_FINALIZER_EXIT_EVENT) == Constants.GENERATE_FINALIZER_EXIT_EVENT);

					newArgument.append(' ');
					newArgument.append((requiredEventMask
							& Constants.GENERATE_PROGRAM_END_EVENT) == Constants.GENERATE_PROGRAM_END_EVENT);

					newArgument.append(' ');
					newArgument.append((requiredEventMask
							& Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT) == Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT);

					for (int i = 0; classNameFilter != null && i < classNameFilter.length; i++) {

						newArgument.append(' ');
						newArgument.append(classNameFilter[i].substring(0, classNameFilter[i].lastIndexOf('.')));
					}

					argument.setValue(newArgument.toString());
				}
			}
			final VirtualMachine jvm = launchingConnector.launch(arguments);

			// A thread to catch and to display the output of the remote JVM.
			final OutputMonitor streamOutputMonitor = new OutputMonitor("Caffeine Remote JVM Ouput Stream Monitor",
					"(Remote JVM)", jvm.process().getInputStream(), System.out);
			streamOutputMonitor.start();

			// A thread to catch and to display the output of the remote JVM.
			final OutputMonitor streamErrorMonitor = new OutputMonitor("Caffeine Remote JVM Error Stream Monitor",
					"(Remote JVM)", jvm.process().getErrorStream(), System.err);
			streamErrorMonitor.start();

			// The Caffeine engine... at last!
			this.initialize(traceOutputFileName, ruleFileName, classNameFilter, requiredEventMask, fieldAccesses, jvm,
					new PrologListener());
			this.start();
		} catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
	}

	public void start(final String ruleFileName, final String classPath, final String mainClassName,
			final String[] classNameFilter, final int requiredEventMask) {

		this.start("Caffeine.trace", ruleFileName, classPath, mainClassName, classNameFilter, requiredEventMask, null);
	}

	public void start(final String ruleFileName, final String classPath, final String mainClassName,
			final String[] classNameFilter, final int requiredEventMask, final String[][] fieldAccesses) {

		this.start("Caffeine.trace", ruleFileName, classPath, mainClassName, classNameFilter, requiredEventMask,
				fieldAccesses);
	}

	public void start(final String ruleFileName, final String classPath, final String mainClassName,
			final String[] classNameFilter, final String[][] fieldAccesses) {

		this.start("Caffeine.trace", ruleFileName, classPath, mainClassName, classNameFilter, 0, fieldAccesses);
	}

	public void stop() {
		// Yann 2003/08/05: Reset!
		// In case of multiple calls, I reset the Engine
		// (and thus the EventManager) between two calls.
		Engine.resetUniqueInstance();
	}
}
