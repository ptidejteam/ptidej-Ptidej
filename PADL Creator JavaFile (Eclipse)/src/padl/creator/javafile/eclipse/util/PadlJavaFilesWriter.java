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
package padl.creator.javafile.eclipse.util;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import util.io.ProxyDisk;

public class PadlJavaFilesWriter {
	private static PrintWriter writer = null;

	public static void setWriter(final String fileName) {
		writer =
			new PrintWriter(new BufferedWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(fileName)));
	}
	public static void writeIn(String message) {
		if (writer != null) {
			writer.println(message);
		}

	}
	public static void close() {
		if (writer != null) {
			writer.close();
		}
	}
}
