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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class UnclosablePrintStream extends PrintStream {
	public UnclosablePrintStream(final OutputStream out) {
		super(out);
	}
	public UnclosablePrintStream(final String fileName)
			throws FileNotFoundException {
		super(fileName);
	}
	public UnclosablePrintStream(final File file) throws FileNotFoundException {
		super(file);
	}

	public UnclosablePrintStream(final OutputStream out, final boolean autoFlush) {
		super(out, autoFlush);
	}
	public UnclosablePrintStream(final String fileName, final String csn)
			throws FileNotFoundException, UnsupportedEncodingException {

		super(fileName, csn);
	}
	public UnclosablePrintStream(final File file, final String csn)
			throws FileNotFoundException, UnsupportedEncodingException {

		super(file, csn);
	}
	public UnclosablePrintStream(
		final OutputStream out,
		final boolean autoFlush,
		final String encoding) throws UnsupportedEncodingException {

		super(out, autoFlush, encoding);
	}
	public void close() {
		// super.close();
		ProxyConsole.getInstance().warningOutput().print("");
	}
}
