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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/15
 */
public class ProxyConsole {
	private static ProxyConsole UniqueInstance;
	public static ProxyConsole getInstance() {
		if (ProxyConsole.UniqueInstance == null) {
			ProxyConsole.UniqueInstance = new ProxyConsole();
		}

		return ProxyConsole.UniqueInstance;
	}

	private PrintWriter debugOutput;
	private PrintWriter errorOutput;
	private PrintWriter normalOutput;
	private PrintWriter warningOutput;

	private ProxyConsole() {
		// this.setDebugOutput(new NullWriter());
		this.setDebugOutput(
			new AutoFlushPrintWriter(new OutputStreamWriter(System.err)));
		this.setErrorOutput(
			new AutoFlushPrintWriter(new OutputStreamWriter(System.err)));
		this.setNormalOutput(
			new AutoFlushPrintWriter(new OutputStreamWriter(System.out)));
	}
	public PrintWriter debugOutput() {
		return this.debugOutput;
	}
	public PrintWriter errorOutput() {
		return this.errorOutput;
	}
	public PrintWriter normalOutput() {
		return this.normalOutput;
	}
	public void printSetContent(final Writer writer, final Set<?> aSet) {
		final Iterator<?> iterator = aSet.iterator();
		while (iterator.hasNext()) {
			final Object object = iterator.next();
			try {
				writer.write(object.toString());
				writer.write('\n');
				writer.flush();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void setDebugOutput(final PrintWriter messageWriter) {
		this.debugOutput = messageWriter;
	}
	public void setDebugOutput(final Writer messageWriter) {
		this.setDebugOutput(new UnclosablePrintWriter(messageWriter));
	}
	private void setErrorOutput(final PrintWriter messageWriter) {
		this.errorOutput = messageWriter;
	}
	public void setErrorOutput(final Writer messageWriter) {
		// Yann 2014/06/22: Eclipse...
		// For some unknown reason, Eclipse decided to close the writer
		// given to it at startup, through its Adaptor (see for example
		// padl.creator.cppfile.eclipse.misc.EclipseCPPParserAdaptor).
		// It closes the writer at
		//	EclipseLogWriter.setOutput(File, Writer, boolean) line: 331
		// So, I make sure that it cannot close MY writers using my own
		// UnclosablePrintWriter.
		this.setErrorOutput(new UnclosablePrintWriter(messageWriter));
	}
	private void setNormalOutput(final PrintWriter messageWriter) {
		this.normalOutput = messageWriter;
		// this.warningOutput = new WarningPrintWriter(this.normalOutput);
		this.warningOutput = this.normalOutput;
	}
	public void setNormalOutput(final Writer messageWriter) {
		// Yann 2014/06/22: Eclipse...
		// For some unknown reason, Eclipse decided to close the writer
		// given to it at startup, through its Adaptor (see for example
		// padl.creator.cppfile.eclipse.misc.EclipseCPPParserAdaptor).
		// It closes the writer at
		//	EclipseLogWriter.setOutput(File, Writer, boolean) line: 331
		// So, I make sure that it cannot close MY writers using my own
		// UnclosablePrintWriter.
		this.setNormalOutput(new UnclosablePrintWriter(messageWriter));
	}
	public PrintWriter warningOutput() {
		return this.warningOutput;
	}
}
