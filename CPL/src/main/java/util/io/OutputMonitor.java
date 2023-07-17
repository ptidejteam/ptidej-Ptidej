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
package util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author 	Yann-Gaël Guéhéneuc
 * @version 0.3
 */
public class OutputMonitor extends Thread {
	private final String header;
	private final InputStream inputStream;
	private final PrintStream printStream;
	private boolean isVerbose;

	public OutputMonitor(
		final String threadName,
		final String header,
		final InputStream inputStream,
		final PrintStream printStream) {

		this.setName(threadName);
		this.setPriority(Thread.MAX_PRIORITY);
		this.header = header;
		this.inputStream = inputStream;
		this.printStream = printStream;
		this.isVerbose = true;
	}
	public OutputMonitor(
		final String threadName,
		final String header,
		final InputStream inputStream,
		final PrintWriter printWriter) {

		this.setName(threadName);
		this.setPriority(Thread.MAX_PRIORITY);
		this.header = header;
		this.inputStream = inputStream;
		this.printStream = new PrintStream(new WriterOutputStream(printWriter));
		this.isVerbose = true;
	}

	public boolean isVerbose() {
		return this.isVerbose;
	}
	public void run() {
		try {
			int value = 0;
			byte[] bytes;
			char lastWrittenChar;
			// Despite what the JDK documentation says, the
			// read(byte, int, int) method does not block
			// until data is available! I must use a read
			// (which blocks until data is available) and then
			// use the read(byte, int, int) method for the
			// rest of the available data.
			while ((value = this.inputStream.read()) > 0) {
				synchronized (System.err) {
					synchronized (System.out) {
						// Yann 2003/07/30: Carriage return management!
						// I don't want to print 'normally' a carriage
						// return, because it prints an extra header with
						// a dummy blank line. Also, when I encounter a
						// '\r' or a '\n', I just print a new line in the
						// output flow and remove this caracter from the
						// input flow.
						if (value != 13 && value != 10) {
							lastWrittenChar = (char) value;
							if (this.isVerbose) {
								this.printStream.print(this.header);
								this.printStream.print(' ');
								this.printStream.print(lastWrittenChar);
								while (value > 0) {
									final int available =
										this.inputStream.available();
									value =
										this.inputStream.read(bytes =
											new byte[available], 0, available);
									for (int i = 0; i < value; i++) {
										lastWrittenChar = (char) bytes[i];
										this.printStream.print(lastWrittenChar);
									}
								}
								this.printStream.flush();
							}
						}
						else {
							this.printStream.println();
							this.printStream.flush();
							this.inputStream.read();
						}
					}
				}
			}
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void setVerbose(final boolean isVerbose) {
		this.isVerbose = isVerbose;
	}
}
