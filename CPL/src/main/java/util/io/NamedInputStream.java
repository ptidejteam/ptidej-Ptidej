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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/21
 */
public class NamedInputStream {
	private static final int BYTE_ARRAY_SIZE = 15000;
	private static final int BYTE_ARRAY_INCREMENT = 2;

	// Yann 2004/07/21: InputStreams.
	// It is *never* a good idea to store instances of InputStream
	// because once read, an input stream cannot be easily reset,
	// reread, and because it may be closed unexpectedly. It is
	// *far better* to store the data (bytes) and to produce a new
	// input stream on-demand.
	private final byte[] bytes;
	private final String fileName;

	public NamedInputStream(final String aFileName, final InputStream aStream) {

		this.fileName = Files.normalizePath(aFileName);
		byte[] array1 = new byte[NamedInputStream.BYTE_ARRAY_SIZE];
		int b = 0;
		int i = 0;

		try {
			// Yann 2004/12/04: Availability...
			// The available() method cannot be trusted because
			// some stream just cannot tell how much data is
			// available. So, I grow little by little the array
			// until it reaches the appropriate size.
			//	this.bytes = new byte[aStream.available()];
			while ((b = aStream.read()) != -1) {
				array1[i++] = (byte) b;
				if (i == array1.length) {
					final byte[] array2 =
						new byte[i * NamedInputStream.BYTE_ARRAY_INCREMENT];
					System.arraycopy(array1, 0, array2, 0, i);
					array1 = array2;
				}
			}
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.bytes = new byte[i];
		System.arraycopy(array1, 0, this.bytes, 0, i);
	}
	public String getName() {
		return this.fileName;
	}
	public InputStream getStream() {
		return new ByteArrayInputStream(this.bytes);
	}
	public String toString() {
		return this.getName();
	}
}
