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
package util.multilingual;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import util.io.ProxyConsole;

/**
 * Since we will not supply any Locale when getting the Bundle,
 * the program will then use the default Locale, and for our case,
 * the bundle will be "PtidejResourceBundle".
 * If you want to hardcode a specific "Locale" (i.e, Japanese),
 * you can do so by
 * <code>ResourceBundle res = ResourceBundle.getBundle("util.multilanguage.PtidejResourceBundle", new Locale("ja","JP"));</code>
 * Also, don't forget to import the java.util.Locale class.
 * <code>import java.util.Locale</code>
 * 
 * @author Mehdi Lahlou
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/07/07
 */
public class MultilingualManager {
	private static final String BUNDLE_NAME =
		"util.multilingual.PtidejResourceBundle";
	private static ResourceBundle Bundle;
	static {
		try {
			MultilingualManager.Bundle =
				ResourceBundle.getBundle(MultilingualManager.BUNDLE_NAME);
		}
		catch (final MissingResourceException mre) {
			mre.printStackTrace(ProxyConsole.getInstance().errorOutput());
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("Use default english locale");
			MultilingualManager.Bundle =
				ResourceBundle.getBundle(
					MultilingualManager.BUNDLE_NAME,
					Locale.ENGLISH);
		}
	}
	public static String getString(final String aKey) {
		try {
			return MultilingualManager.Bundle.getString(aKey);
		}
		catch (final MissingResourceException mre) {
			mre.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return "<MISSING>";
		}
	}
	public static String getString(final String aKey, final Class<?> aClass) {
		try {
			return MultilingualManager.Bundle.getString(aClass.getName() + "::"
					+ aKey);
		}
		catch (final MissingResourceException mre) {
			mre.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return aKey;
		}
	}
	public static String getString(
		final String aKey,
		final Class<?> aClass,
		final Object[] someArguments) {

		try {
			final MessageFormat formatter =
				new MessageFormat(MultilingualManager.getString(aKey, aClass));
			return formatter.format(someArguments);
		}
		catch (final MissingResourceException mre) {
			mre.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return aKey;
		}
	}

	private MultilingualManager() {
	}
}
