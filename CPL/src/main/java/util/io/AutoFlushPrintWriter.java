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

import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public class AutoFlushPrintWriter extends PrintWriter {
	public AutoFlushPrintWriter(final Writer writer) {
		super(writer, true);
	}
	public void write(final char[] buf, final int pos, final int len) {
		super.write(buf, pos, len);
		this.flush();
	}
}
