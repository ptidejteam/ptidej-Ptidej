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
package modec.tool.instrumentation;

import java.io.BufferedWriter;
import util.io.ProxyDisk;

/**
 * Classe appelee pour logger un 
 * evenement dans la trace d'execution.
 *
 * @author Janice Ka-Yee Ng
 * @author Yann
 * @since  2007/03/01
 **/
public class LogToFile {
	private volatile static StringBuffer Buffer = new StringBuffer();
	private volatile static BufferedWriter BufferedWriter = null;
	private static final int NUMBER_OF_LINES_ON_EXIT = 10000101;
	private static final int NUMBER_OF_LINES_ON_WRITE = 100;
	private volatile static int NumberOfLines = 0;
	private volatile static int TotalNumberOfLines = 0;

	public synchronized static void allLoopsExit(final String aFileName) {
		LogToFile.Buffer.append("ALL LOOPs exit\n");
		LogToFile.NumberOfLines++;
		LogToFile.TotalNumberOfLines++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void calleeID(
		final String aFileName,
		final Object callee,
		final boolean isMain,
		final boolean isStatic) {

		if (isMain) {
			LogToFile.Buffer.append(callee);
			LogToFile.Buffer.append(" -1\n");
		}
		else if (!isMain && isStatic) {
			LogToFile.Buffer.append(callee);
			LogToFile.Buffer.append(" -2\n");
		}
		else {
			LogToFile.Buffer.append(callee.getClass().getName());
			LogToFile.Buffer.append(" ");
			LogToFile.Buffer.append(System.identityHashCode(callee));
			LogToFile.Buffer.append("\n");
		}
		LogToFile.NumberOfLines++;
		LogToFile.TotalNumberOfLines++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void constructorEntry(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("CONSTRUCTOR entry ");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append(" CALLEE ");
		// LogToFile.Counter++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void constructorExit(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("CONSTRUCTOR exit ");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append(" CALLEE ");
		// LogToFile.Counter++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void loopEntry(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("LOOP entry #");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append('\n');
		LogToFile.NumberOfLines++;
		LogToFile.TotalNumberOfLines++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void loopExit(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("LOOP exit #");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append('\n');
		LogToFile.NumberOfLines++;
		LogToFile.TotalNumberOfLines++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void methodEntry(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("METHOD entry ");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append(" CALLEE ");
		// LogToFile.Counter++;

		LogToFile.write0(aFileName);
	}
	public synchronized static void methodExit(
		final String aFileName,
		final String log) {

		LogToFile.Buffer.append("METHOD exit ");
		LogToFile.Buffer.append(log);
		LogToFile.Buffer.append(" CALLEE ");
		// LogToFile.Counter++;

		LogToFile.write0(aFileName);
	}
	//	public synchronized static void write(final String aFileName, final String log) {
	//		LogToFile.Buffer.append(log);
	//		LogToFile.LineCounter++;
	//
	//		LogToFile.write0(aFileName);
	//	}
	private synchronized static void write0(final String aFileName) {
		if (LogToFile.NumberOfLines > LogToFile.NUMBER_OF_LINES_ON_WRITE) {
			try {
				if (LogToFile.BufferedWriter == null) {
					final java.io.Writer fileWriter =
						ProxyDisk.getInstance().fileAbsoluteOutput(
							aFileName,
							true);
					LogToFile.BufferedWriter = new BufferedWriter(fileWriter);
				}
				LogToFile.BufferedWriter.write(LogToFile.Buffer.toString());
				LogToFile.BufferedWriter.flush();

				LogToFile.Buffer.setLength(0);
				LogToFile.NumberOfLines = 0;
			}
			catch (final StackOverflowError e) {
				// Catch exception if any
				System.err
					.println("Error in LogToFile.write: StackOverflowError\n"
							+ e.getMessage());
				System.exit(-20);
			}
			catch (final Exception e) {
				// Catch exception if any
				System.err.println("Error in LogToFile.write: "
						+ e.getMessage());
				System.exit(-30);
			}
		}
		if (LogToFile.TotalNumberOfLines > LogToFile.NUMBER_OF_LINES_ON_EXIT) {
			// Yann 2007/12/05: Safety!
			// We don't want to fill up the hard drive
			// in case of "infinite" loop or something!
			System.exit(-1);
		}
	}
}
