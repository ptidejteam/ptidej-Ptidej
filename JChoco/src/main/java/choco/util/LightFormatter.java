// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.util.LightFormatter.java, last modified by Francois 28 sept. 2003 16:03:25 */
package choco.util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A class for formatting trace messages in the lightest possible mode
 */
public class LightFormatter extends Formatter {
	// Line separator string.  This is the value of the line.separator
	// property at the moment that the SimpleFormatter was created.
	// private String lineSeparator = "\n";
	private final String lineSeparator = System.getProperty("line.separator");

	/**
	 * Format the given LogRecord.
	 * @param record the log record to be formatted.
	 * @return a formatted log record
	 */
	public synchronized String format(final LogRecord record) {
		final StringBuffer sb = new StringBuffer();
		record.getLevel();
		// sb.append(record.getLoggerName());
		//for (int i=0; i<lvli; i++) {
		//  sb.append("  ");
		//}
		if (record.getLoggerName() == "choco") {
			sb.append("===");
		}
		if (record.getLoggerName().startsWith("choco.search")) {
			sb.append("-- ");
		}
		sb.append(record.getMessage());
		//sb.append(formatMessage(record));
		sb.append(this.lineSeparator);
		return sb.toString();
	}
}