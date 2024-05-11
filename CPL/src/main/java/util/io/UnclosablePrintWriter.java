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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class UnclosablePrintWriter extends PrintWriter {
	public UnclosablePrintWriter(final Writer out) {
		super(out);
	}
	public UnclosablePrintWriter(final OutputStream out) {
		super(out);
	}
	public UnclosablePrintWriter(final String fileName)
			throws FileNotFoundException {
		super(fileName);
	}
	public UnclosablePrintWriter(final File file) throws FileNotFoundException {
		super(file);
	}
	public UnclosablePrintWriter(final Writer out, final boolean autoFlush) {
		super(out, autoFlush);
	}
	public UnclosablePrintWriter(final OutputStream out, final boolean autoFlush) {
		super(out, autoFlush);
	}
	public UnclosablePrintWriter(final String fileName, final String csn)
			throws FileNotFoundException, UnsupportedEncodingException {

		super(fileName, csn);
	}
	public UnclosablePrintWriter(File file, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {

		super(file, csn);
	}
	public void close() {
		// super.close();
		ProxyConsole.getInstance().warningOutput().print(this.getClass());
		ProxyConsole
			.getInstance()
			.warningOutput()
			.println(" reports that someone is trying to close it!");
	}
}
