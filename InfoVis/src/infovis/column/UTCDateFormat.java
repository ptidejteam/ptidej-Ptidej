/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class UTCDateFormat extends SimpleDateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for UTCDateFormat.
	 */
	public UTCDateFormat() {
		super("dd MMM yyyy HH:mm:ss", Locale.US);
	}

	/**
	 * Constructor for UTCDateFormat.
	 * @param pattern
	 */
	public UTCDateFormat(String pattern) {
		super(pattern);
	}

	/**
	 * Constructor for UTCDateFormat.
	 * @param pattern
	 * @param locale
	 */
	public UTCDateFormat(String pattern, Locale locale) {
		super(pattern, locale);
	}

	/**
	 * Constructor for UTCDateFormat.
	 * @param pattern
	 * @param formatSymbols
	 */
	public UTCDateFormat(String pattern, DateFormatSymbols formatSymbols) {
		super(pattern, formatSymbols);
	}

	/**
	 * @see java.text.DateFormat#parseObject(String, ParsePosition)
	 */
	public Object parseObject(String source) {
		try {
			return Long.valueOf(DateFormat
				.getDateInstance()
				.parse(source)
				.getTime());
		}
		catch (ParseException e) {
			e.printStackTrace();
			return Long.valueOf(0L);
		}
	}
}
