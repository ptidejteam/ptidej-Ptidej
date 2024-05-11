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
import java.io.Reader;

/**
 * An adapter class between a Reader and an InputStream
 * 
 * @author  Yann-Gaël Guéhéneuc
 * @version	0.1
 */
public class ReaderInputStream extends InputStream {
	private final Reader reader;

	/**
	 * Constructor for ReaderInputStream.
	 */
	public ReaderInputStream(final Reader reader) {
		this.reader = reader;
	}

	//	/**
	//	 * @see InputStream#available()
	//	 */
	//	public int available() throws IOException {
	//		return Integer.MAX_VALUE;
	//	}

	//	/**
	//	 * @see InputStream#close()
	//	 */
	//	public void close() throws IOException {
	//		this.reader.close();
	//	}

	/**
	 * @see InputStream#read()
	 */
	public int read() throws IOException {
		return this.reader.read();
	}

	//	/**
	//	 * @see InputStream#skip()
	//	 */
	//	public long skip(long n) throws IOException {
	//		return this.reader.skip(n);
	//	}
}
