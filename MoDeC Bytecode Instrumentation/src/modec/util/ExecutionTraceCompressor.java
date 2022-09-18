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
package modec.util;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;

public class ExecutionTraceCompressor {
	// Yann 2010/03/29: RLE
	// A simple implementation of the RLE
	// see http://en.wikipedia.org/wiki/Run_length_encoding
	public static void main(final String[] args) {
	}
	public void compress(final Reader aReader, final Writer aWriter) {
		try {
			final LineNumberReader reader = new LineNumberReader(aReader);
			String line = null;
			String previousLine = null;
			while ((line = reader.readLine()) != null) {
				if (previousLine == null) {
					previousLine = line;
					aWriter.write(line);
				}
				else {
					if (line.equals(previousLine)) {
						aWriter.write(line);
					}
					else {
						previousLine = line;
						aWriter.write(line);
					}
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
