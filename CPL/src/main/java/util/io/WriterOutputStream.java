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
import java.io.OutputStream;
import java.io.Writer;

/**
 * An adapter class between a Writer and an OutputStream
 * 
 * @author	Yann-Gaël Guéhéneuc
 * @version	2004/05/16
 */
public class WriterOutputStream extends OutputStream {
	private final Writer writer;

	/**
	 * Constructor for WriterOutputStream.
	 */
	public WriterOutputStream(final Writer writer) {
		this.writer = writer;
	}

	/**
	 * @see OutputStream#write()
	 */
	public void write(final int b) throws IOException {
		this.writer.write(b);
	}
}
