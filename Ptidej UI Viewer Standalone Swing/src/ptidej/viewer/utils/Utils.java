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
package ptidej.viewer.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ptidej.viewer.ui.DesktopFrame;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

public class Utils {
	public static Map MapOfNameIcon = new HashMap();
	public static Icon getImageIcon(final String aKey, final Class aClass) {
		return new ImageIcon(
			Utils.getImage(aKey.concat(Resources.ICON), aClass));
	}
	public static Image getImage(final String aKey, final Class aClass) {
		try {
			final URL url = Utils.getImagePath(aKey, aClass);
			return Toolkit.getDefaultToolkit().getImage(url);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	private static URL getImagePath(final String aKey, final Class aClass) {
		//	try {
		//		return new File(util.io.Files.getClassPath(Desktop.class)
		//				+ Utils.IMAGES_PATH
		//				+ MultilingualManager.getString(aKey, aClass))
		//			.toURI()
		//			.toURL();
		//	}
		//	catch (final MalformedURLException e) {
		//		e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		//		return null;
		//	}
		final URL url =
			aClass.getResource("/images/"
					+ MultilingualManager.getString(aKey, aClass));
		ProxyConsole.getInstance().debugOutput().print(Utils.class.getName());
		ProxyConsole.getInstance().debugOutput().print(" uses the image URL: ");
		ProxyConsole.getInstance().debugOutput().print(url);
		ProxyConsole.getInstance().debugOutput().print(" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(MultilingualManager.getString(aKey, aClass));
		ProxyConsole.getInstance().debugOutput().println(')');
		return url;
	}
	public static File loadDirectory(
		final JFrame owner,
		final boolean multiSelectionEnabled,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		// Yann 2013/07/28: Preferences!
		// Enough of selecting folders after folders
		// to test the user-interface, let's use some
		// nice preferences, nice for the users too!
		// Having the Utils.loadFile() and Utils.loadDirectory()
		// methods makes it easy to implement.
		final Preferences prefs = Preferences.userRoot();
		final String srcPath =
			prefs.get(
				"PTIDEJ_LOADING_SAVING_PATH",
				"../Ptidej Tests/ptidej/Examples/");
		final File file =
			Utils.loadDirectory(
				owner,
				srcPath,
				multiSelectionEnabled,
				strDialogTitle,
				strFilterName,
				strFilterDesc);
		if (file != null) {
			prefs.put("PTIDEJ_LOADING_SAVING_PATH", file.getPath());
		}
		return file;
	}
	private static File loadDirectory(
		final JFrame owner,
		final String strPath,
		final boolean multiSelectionEnabled,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		return Utils.loadFile(
			owner,
			strPath,
			JFileChooser.DIRECTORIES_ONLY,
			multiSelectionEnabled,
			strDialogTitle,
			strFilterName,
			strFilterDesc);
	}
	public static File loadFile(
		final JFrame owner,
		final boolean multiSelectionEnabled,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		// Yann 2013/07/28: Preferences!
		// Enough of selecting folders after folders
		// to test the user-interface, let's use some
		// nice preferences, nice for the users too!
		// Having the Utils.loadFile() and Utils.loadDirectory()
		// methods makes it easy to implement.
		final Preferences prefs = Preferences.userRoot();
		final String srcPath =
			prefs.get(
				"PTIDEJ_LOADING_SAVING_PATH",
				"../Ptidej Tests/ptidej/Examples/");
		final File file =
			Utils.loadFile(
				owner,
				srcPath,
				multiSelectionEnabled,
				strDialogTitle,
				strFilterName,
				strFilterDesc);
		if (file != null) {
			prefs.put("PTIDEJ_LOADING_SAVING_PATH", file.getPath());
		}
		return file;
	}
	private static File loadFile(
		final JFrame owner,
		final String strPath,
		final boolean multiSelectionEnabled,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		return Utils.loadFile(
			owner,
			strPath,
			JFileChooser.FILES_ONLY,
			multiSelectionEnabled,
			strDialogTitle,
			strFilterName,
			strFilterDesc);
	}
	private static File loadFile(
		final JFrame owner,
		final String strPath,
		final int selectionMode,
		final boolean multiSelectionEnabled,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		final JFileChooser fileChooser = new JFileChooser(strPath);
		final File file;

		fileChooser.setDialogTitle(strDialogTitle);
		fileChooser.setFileFilter(new FileFilter(strFilterName, strFilterDesc));
		fileChooser.setMultiSelectionEnabled(multiSelectionEnabled);
		fileChooser.setFileSelectionMode(selectionMode);

		if (fileChooser.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION) {

			file = fileChooser.getSelectedFile();
		}
		else {
			file = null;
		}

		return file;
	}
	public static File saveFile(
		final JFrame owner,
		final String strPath,
		final String strDialogTitle,
		final String strFilterName,
		final String strFilterDesc) {

		final JFileChooser fileChooser = new JFileChooser(strPath);
		final File file;

		fileChooser.setDialogTitle(strDialogTitle);
		fileChooser.addChoosableFileFilter(new FileFilter(
			strFilterName,
			strFilterDesc));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (fileChooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {

			file = fileChooser.getSelectedFile();
		}
		else {
			file = null;
		}

		return file;
	}
	private Utils() {
	}
	public static boolean warnAboutLongOperation() {
		final Object[] options = { "Yes, continue", "Okay, no" };

		final int n =
			JOptionPane.showOptionDialog(
				DesktopFrame.getInstance(),
				"The requested operation may take a long time, continue?",
				"Long Operation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, // do not use a custom Icon
				options, // Button texts
				options[0]); // Default button text

		return n == 0;
	}
}
