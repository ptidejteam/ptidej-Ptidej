/*
 * @(#)Trace.java	1.4 03/01/23
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.io.ProxyConsole;
import util.io.WriterOutputStream;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

/**
 * This program traces the execution of another program.
 * See "java Trace -help".
 * It is a simple example of the use of the Java Debug Interface.
 *
 * @version     @(#) Trace.java 1.4 03/01/23 16:22:08
 * @author Robert Field
 */
public class Trace {

	/**
	 * main
	 */
	public static void main(final String[] args) {
		new Trace(args);
	}

	// Running remote VM
	private final VirtualMachine vm;

	// Thread transferring remote error stream to our error stream
	private Thread errThread = null;

	// Thread transferring remote output stream to our output stream
	private Thread outThread = null;

	// Mode for tracing the Trace program (default= 0 off)
	private int debugTraceMode = 0;

	//  Do we want to watch assignments to fields
	private boolean watchFields = false;

	// Class patterns for which we don't want events
	private String[] excludes = { "java.*", "javax.*", "sun.*", "com.sun.*" };

	/**
	 * Parse the command line arguments.  
	 * Launch target VM.
	 * Generate the trace.
	 */
	Trace(final String[] args) {
		PrintWriter writer = ProxyConsole.getInstance().debugOutput();
		int inx;
		for (inx = 0; inx < args.length; ++inx) {
			final String arg = args[inx];
			if (arg.charAt(0) != '-') {
				break;
			}
			if (arg.equals("-output")) {
				try {
					writer = new PrintWriter(new FileWriter(args[++inx]));
				}
				catch (final IOException exc) {
					ProxyConsole
						.getInstance()
						.errorOutput()
						.println(
							"Cannot open output file: " + args[inx] + " - "
									+ exc);
					System.exit(1);
				}
			}
			else if (arg.equals("-all")) {
				this.excludes = new String[0];
			}
			else if (arg.equals("-fields")) {
				this.watchFields = true;
			}
			else if (arg.equals("-dbgtrace")) {
				this.debugTraceMode = Integer.parseInt(args[++inx]);
			}
			else if (arg.equals("-help")) {
				this.usage();
				System.exit(0);
			}
			else {
				ProxyConsole
					.getInstance()
					.errorOutput()
					.println("No option: " + arg);
				this.usage();
				System.exit(1);
			}
		}
		if (inx >= args.length) {
			ProxyConsole.getInstance().errorOutput().println("<class> missing");
			this.usage();
			System.exit(1);
		}
		final StringBuffer sb = new StringBuffer();
		sb.append(args[inx]);
		for (++inx; inx < args.length; ++inx) {
			sb.append(' ');
			sb.append(args[inx]);
		}
		this.vm = this.launchTarget(sb.toString());
		this.generateTrace(writer);
	}

	/**
	 * Return the launching connector's arguments.
	 */
	Map<String, Argument> connectorArguments(
		final LaunchingConnector connector,
		final String mainArgs) {

		final Map<String, Argument> arguments = connector.defaultArguments();
		final Connector.Argument mainArg =
			(Connector.Argument) arguments.get("main");
		if (mainArg == null) {
			throw new Error("Bad launching connector");
		}
		mainArg.setValue(mainArgs);

		if (this.watchFields) {
			// We need a VM that supports watchpoints
			final Connector.Argument optionArg =
				(Connector.Argument) arguments.get("options");
			if (optionArg == null) {
				throw new Error("Bad launching connector");
			}
			optionArg.setValue("-classic");
		}
		return arguments;
	}

	/**
	 * Find a com.sun.jdi.CommandLineLaunch connector
	 */
	LaunchingConnector findLaunchingConnector() {
		final List<?> connectors =
			Bootstrap.virtualMachineManager().allConnectors();
		final Iterator<?> iter = connectors.iterator();
		while (iter.hasNext()) {
			final Connector connector = (Connector) iter.next();
			if (connector.name().equals("com.sun.jdi.CommandLineLaunch")) {
				return (LaunchingConnector) connector;
			}
		}
		throw new Error("No launching connector");
	}

	/**
	 * Generate the trace.
	 * Enable events, start thread to display events, 
	 * start threads to forward remote error and output streams,
	 * resume the remote VM, wait for the final event, and shutdown.
	 */
	void generateTrace(final PrintWriter writer) {
		this.vm.setDebugTraceMode(this.debugTraceMode);
		final EventThread eventThread =
			new EventThread(this.vm, this.excludes, writer);
		eventThread.setEventRequests(this.watchFields);
		eventThread.start();
		this.redirectOutput();
		this.vm.resume();

		// Shutdown begins when event thread terminates
		try {
			eventThread.join();
			this.errThread.join(); // Make sure output is forwarded 
			this.outThread.join(); // before we exit
		}
		catch (final InterruptedException exc) {
			// we don't interrupt
		}
		writer.close();
	}

	/**
	 * Launch target VM.
	 * Forward target's output and error.
	 */
	VirtualMachine launchTarget(final String mainArgs) {
		final LaunchingConnector connector = this.findLaunchingConnector();
		final Map<String, Argument> arguments =
			this.connectorArguments(connector, mainArgs);
		try {
			return connector.launch(arguments);
		}
		catch (final IOException exc) {
			throw new Error("Unable to launch target VM: " + exc);
		}
		catch (final IllegalConnectorArgumentsException exc) {
			throw new Error("Internal error: " + exc);
		}
		catch (final VMStartException exc) {
			throw new Error("Target VM failed to initialize: "
					+ exc.getMessage());
		}
	}

	void redirectOutput() {
		final Process process = this.vm.process();

		// Copy target's output and error to our output and error.
		this.errThread =
			new StreamRedirectThread(
				"error reader",
				process.getErrorStream(),
				new PrintStream(new WriterOutputStream(ProxyConsole
					.getInstance()
					.errorOutput())));
		this.outThread =
			new StreamRedirectThread(
				"output reader",
				process.getInputStream(),
				new PrintStream(new WriterOutputStream(ProxyConsole
					.getInstance()
					.debugOutput())));
		this.errThread.start();
		this.outThread.start();
	}

	/**
	 * Print command line usage help
	 */
	void usage() {
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("Usage: java Trace <options> <class> <args>");
		ProxyConsole.getInstance().errorOutput().println("<options> are:");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("  -output <filename>   Output trace to <filename>");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("  -all                 Include system classes in output");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("  -help                Print this help message");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("<class> is the program to trace");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.println("<args> are the arguments to <class>");
	}
}
