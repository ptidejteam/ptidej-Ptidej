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
package modec.test.visitor.example;

import java.io.BufferedWriter;
import java.io.Writer;
import util.io.ProxyDisk;

/**
 * Classe appelee pour logger un 
 * evenement dans la trace d'execution.
 *
 * @author Janice Ka-Yee Ng
 * @since  2007/03/01
 **/
public class LogToFile {
	public static void methodEntry(final String filename, final String log) {
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			out.write("METHOD entry " + log.toString() + " CALLEE ");
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void methodExit(final String filename, final String log) {
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			out.write("METHOD exit " + log.toString() + " CALLEE ");
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void constructorEntry(final String filename, final String log) {
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			out.write("CONSTRUCTOR entry " + log.toString() + " CALLEE ");
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void constructorExit(final String filename, final String log) {
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			out.write("CONSTRUCTOR exit " + log.toString() + " CALLEE ");
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void calleeID(
		final String filename,
		final Object callee,
		final boolean isMain,
		final boolean isStatic) {

		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			if (isMain) {
				out.write(callee.getClass().getName() + " -1\n");
			}
			else if (!isMain && isStatic) {
				out.write(callee.getClass().getName() + " -2\n");
			}
			else {
				out.write(callee.getClass().getName() + " "
						+ System.identityHashCode(callee) + "\n");
			}
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void write(final String filename, final Object log) {
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(writer);
			//			if (log instanceof java.lang.StackTraceElement) {
			//				out.write(log.toString() + "\n");
			//			} else {
			out.write(log.toString());
			//			}
			out.close();
		}
		catch (final StackOverflowError e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: StackOverflowError\n"
					+ e.getMessage());
		}
		catch (final Exception e) { //Catch exception if any
			System.err.println("Error in LogToFile.write: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		calleeID("filename", new Object(), true, true);

	}
}
