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
package modec.tool.instrumentation;

import java.io.BufferedWriter;
import util.io.ProxyDisk;

/**
 * @author Janice Ng
 */
public class Writer {
	public static void write(final String filename, final Object log) {
		try {
			final java.io.Writer fstream =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(fstream);
			if (log instanceof java.lang.StackTraceElement) {
				out.write(log.toString() + "\n");
			}
			else {
				out.write(log.toString());
			}
			out.close();
		}
		catch (final Exception e) {
			// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
