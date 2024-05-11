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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import util.multilingual.MultilingualManager;

public final class LogoBytesArrayBuilder {
	public static String createBytesArrayBuilder(final String fileName) {
		// System.out.println(LogoBytesArrayBuilder.createBytesArrayBuilder("E:/Work/JPtidej/Ptidej.gif"));

		try {
			final File file = new File(fileName);
			final FileInputStream fis = new FileInputStream(file);
			final byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes, 0, bytes.length);
			fis.close();

			final StringBuffer buffer = new StringBuffer();
			buffer
				.append("\tprivate static final byte[] imageBytes = new byte[] {");
			for (int i = 0; i < bytes.length; i++) {
				if (i % 10 == 0) {
					buffer.append("\n\t\t");
				}
				buffer.append(bytes[i]);
				if (i < bytes.length - 1) {
					buffer.append(", ");
				}
			}
			buffer.append("};\n");

			return buffer.toString();
		}
		catch (final FileNotFoundException fnfe) {
			fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return MultilingualManager.getString(
			"Err_BUILDING_BYTES",
			LogoBytesArrayBuilder.class);
	}
}
