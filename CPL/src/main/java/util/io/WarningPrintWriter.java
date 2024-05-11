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
 * @since  2009/05/22
 */
public class WarningPrintWriter extends PrintWriter {
	private static final String PREFIX = "## ";
	private boolean withinSomeCallsToPrintWithouLN;
	private boolean withinSomeCallsToPrintLN;
	public WarningPrintWriter(final Writer writer) {
		super(writer, true);
		this.withinSomeCallsToPrintWithouLN = false;
		this.withinSomeCallsToPrintLN = false;
	}
	public void print(final char aChar) {
		if (!this.withinSomeCallsToPrintWithouLN
				&& !this.withinSomeCallsToPrintLN) {

			super.print(WarningPrintWriter.PREFIX);
			this.withinSomeCallsToPrintWithouLN = true;
		}
		super.print(aChar);
	}
	public void print(final String aString) {
		if (!this.withinSomeCallsToPrintWithouLN
				&& !this.withinSomeCallsToPrintLN) {

			super.print(WarningPrintWriter.PREFIX);
			this.withinSomeCallsToPrintWithouLN = true;
		}
		super.print(aString);
	}
	public void println(final char aChar) {
		if (!this.withinSomeCallsToPrintWithouLN) {
			super.print(WarningPrintWriter.PREFIX);
		}
		this.withinSomeCallsToPrintWithouLN = false;
		this.withinSomeCallsToPrintLN = true;
		super.println(aChar);
		this.withinSomeCallsToPrintLN = false;
	}
	public void println(final String aString) {
		if (!this.withinSomeCallsToPrintWithouLN) {
			super.print(WarningPrintWriter.PREFIX);
		}
		this.withinSomeCallsToPrintWithouLN = false;
		this.withinSomeCallsToPrintLN = true;
		super.println(aString);
		this.withinSomeCallsToPrintLN = false;
	}
}
