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
package util.help;

import java.io.IOException;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

/**
 * @author Steven Spencer
 * @link   http://www.javaworld.com/javaworld/javatips/jw-javatip66.html  
 *
 * A simple, static class to display a URL in the system browser.
 *
 * Under Unix, the system browser is hard-coded to be 'netscape'.
 * Netscape must be in your PATH for this to work.  This has been
 * tested with the following platforms: AIX, HP-UX and Solaris.
 *
 * Under Windows, this will bring up the default browser under windows,
 * usually either Netscape or Microsoft IE.  The default browser is
 * determined by the OS.  This has been tested under Windows 95/98/NT.
 *
 * Examples:
 * BrowserControl.displayURL("http://www.javaworld.com")
 * BrowserControl.displayURL("file://c:\\docs\\index.html")
 * BrowserContorl.displayURL("file:///user/joe/index.html");
 * 
 * Note - you must include the url type -- either "http://"
 * or "file://".
 */
public class Browser {
	// The flag to display a url.
	private static final String UNIX_FLAG = "-remote openURL";
	// The default browser under unix.
	private static final String UNIX_PATH = "firefox";
	// The flag to display a url.
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
	// Used to identify the windows platform.
	private static final String WIN_ID = "Windows";
	// The default system browser under windows.
	private static final String WIN_PATH = "rundll32";

	/**
	 * Display a file in the system browser.  If you want to display a
	 * file, you must include the absolute path name.
	 *
	 * @param url the file's url (the url must start with either "http://"
	 * or "file://").
	 */
	public static void displayURL(final String url) {

		final String os = System.getProperty("os.name");
		// Modification Done by Mohamed Kahla
		// 12-08-2006-H1013
		System.out.println("Os : " + os);

		// TODO : optimization could be done 

		final boolean windows = Browser.isWindowsPlatform();
		String cmd = null;
		try {
			if (windows) {
				// cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
				cmd = Browser.WIN_PATH + " " + Browser.WIN_FLAG + " " + url;
				Runtime.getRuntime().exec(cmd);
			}

			// Under Mac Os X we run the url whith the default Browser
			else if (os.equals("Mac OS X")) {
				System.out.println("Os detected : " + os);

				// we invoke the command string : "open -a /Applications/Safari.app [url]"
				cmd = "open -a /Applications/Safari.app" + " " + url;
				Runtime.getRuntime().exec(cmd);
			}

			// TODO : i did not deleted the debug messages
			// TODO : the else suppose that ther is no other operating System 
			// but the code is specific for Unix or maybe Linux 
			// so in my openion we should test by the os name as i do for MAc OS X
			// Mohamed kahla
			// 12-08-2006- H1032

			else {
				// Under Unix, Netscape has to be running for the "-remote"
				// command to work.  So, we try sending the command and
				// check for an exit value.  If the exit command is 0,
				// it worked, otherwise we need to start the browser.
				// cmd = 'netscape -remote openURL(http://www.javaworld.com)'
				cmd =
					Browser.UNIX_PATH + " " + Browser.UNIX_FLAG + "(" + url
							+ ")";
				final Process p = Runtime.getRuntime().exec(cmd);
				try {
					// wait for exit code -- if it's 0, command worked,
					// otherwise we need to start the browser up.
					final int exitCode = p.waitFor();
					if (exitCode != 0) {
						// Command failed, start up the browser
						// cmd = 'netscape http://www.javaworld.com'
						cmd = Browser.UNIX_PATH + " " + url;
						Runtime.getRuntime().exec(cmd);
					}
				}
				catch (final InterruptedException x) {
					ProxyConsole
						.getInstance()
						.errorOutput()
						.print(
							MultilingualManager.getString(
								"Err_BRINGING_BROWSER",
								Browser.class));
					ProxyConsole.getInstance().errorOutput().println(cmd);
					x.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}
		catch (final IOException x) {
			// couldn't exec browser
			ProxyConsole
				.getInstance()
				.errorOutput()
				.print(
					MultilingualManager.getString(
						"Err_INVOKE_BROWSER",
						Browser.class));
			ProxyConsole.getInstance().errorOutput().println(cmd);
			x.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	/**
	 * Try to determine whether this application is running under Windows
	 * or some other platform by examing the "os.name" property.
	 *
	 * @return true if this application is running under a Windows OS
	 */
	public static boolean isWindowsPlatform() {
		final String os = System.getProperty("os.name");

		// Modification Done by Mohamed Kahla
		// 12-08-2006-H1013

		if (os != null && os.startsWith(Browser.WIN_ID)) {
			return true;
		}
		return false;
	}

	/**
	 * Simple example.
	 */
	public static void main(final String[] args) {
		Browser.displayURL(MultilingualManager.getString(
			"BROWSER_URL",
			Browser.class));
	}
}
