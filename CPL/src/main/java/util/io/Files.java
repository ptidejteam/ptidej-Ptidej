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
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/21
 */
public class Files {
	public static String getClassPath(final Class<?> aClass) {
		// Yann 2003/10/26: Relativity!
		// A user found a major drawback of this class:
		// Everything is hardcoded and thus prevent install
		// in different directory than the one specified.
		// I now use the client class as base for the lookup
		// of files. Using the client class should give (much)
		// more flexibility and allow (also) to load project
		// without specifying the full path.
		try {
			// First, I get the client class name and I replace
			// any '.' by a '/' to get a filesystem-style name.
			final String clientClassName = aClass.getName().replace('.',
					Files.getSeparatorChar());

			// Then, I built a buffer that shall contain the full
			// path of the client class.
			final StringBuffer clientClassBuffer = new StringBuffer(
					clientClassName.length() + 6);
			clientClassBuffer.append(clientClassName);
			clientClassBuffer.append(".class");

			// Then, I do many things:
			// - I get the client class as a resource using itself
			//   (of course, it shall always exist! :-);
			// - I decode the corresponding URL to get a filesystem
			//   file name;
			// - I get the absolute path of the client class file.
			try {
				final File clientClassFile = new File(
						URLDecoder.decode(aClass.getClassLoader()
								.getResource(clientClassBuffer.toString())
								.getFile(), "UTF-8"));

				// Yann 24/04/27: Weird behaviour of getAbsolutePath() 
				// with a URL, when running in Maven instead of JUnit.
				final String clientClassAbsolutePath;
				if (clientClassFile.isFile()) {
					clientClassAbsolutePath = clientClassFile.getAbsolutePath();
				}
				else {
					clientClassAbsolutePath = clientClassFile.getPath();
				}
				// Finally, I remove from the client class file its name
				// itself to get the path to the client class, from which
				// I can as usuall load other resources.
				return clientClassAbsolutePath.substring(0,
						clientClassAbsolutePath.length()
								- clientClassBuffer.length());
			}
			catch (final NullPointerException npe) {
				return "";
			}
		}
		catch (final UnsupportedEncodingException uee) {
			uee.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return "";
		}
	}

	public static List<String> getRecursivelyFilenamesFromDirectory(
			final String aPath, final FilenameFilter aFilenameFilter) {

		final List<String> listOfFiles = new ArrayList<String>();
		Files.getRecursivelyFilenamesFromDirectory(aPath, listOfFiles,
				aFilenameFilter);
		return listOfFiles;
	}

	private static void getRecursivelyFilenamesFromDirectory(final String aPath,
			final List<String> aListOfIles,
			final FilenameFilter aFilenameFilter) {

		final File pathFile = new File(aPath);
		final String[] subPaths = pathFile.list();
		if (subPaths != null) {
			for (int i = 0; i < subPaths.length; i++) {
				final String fileName = aPath + '/' + subPaths[i];
				final File file = new File(fileName);
				if (file.isDirectory()) {
					Files.getRecursivelyFilenamesFromDirectory(fileName,
							aListOfIles, aFilenameFilter);
				}
				else {
					if (aFilenameFilter.accept(new File(aPath), subPaths[i])) {
						aListOfIles.add(fileName);
					}
				}
			}
		}
		else {
			throw new RuntimeException(
					"No subdirectories with expected files in " + aPath);
		}
	}

	/*
		public static String getRunningPath(final Class aClass) {
			// Yann 2013/07/06: Crapy piece of code because of Sun!
			// See http://stackoverflow.com/questions/4114702/how-to-get-name-of-jar-which-is-a-desktop-application-run-from
			String path = aClass.getProtectionDomain().getCodeSource().getLocation()
					.getPath();
			final File tentativePath = new File(path);
			if (!tentativePath.isDirectory()) {
				try {
					path = URLDecoder.decode(path, "UTF-8").substring(1);
					// Remove spurious "/" at the beginning of the decoded string
				}
				catch (final UnsupportedEncodingException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			return path;
		}
	
		public static String getRunningRootPath(final Class aClass) {
			// Yann 24/04/10: I know that getRunningPath() will include CPL (!!!).
			final String path = Files.getRunningPath(aClass) + "../../../";
			return path;
		}
	*/

	public static char getSeparatorChar() {
		return '/';
	}

	public static String normalizePath(final String aPath) {
		return aPath.replace('\\', Files.getSeparatorChar());
	}

	public static List<String> getJARFiles(final String aPath) {
		final List<String> jarFiles = new ArrayList<String>();
		Files.getJARFiles0(aPath, jarFiles);
		return jarFiles;
	}

	private static void getJARFiles0(final String aPath,
			final List<String> someJARFiles) {
		final File path = new File(aPath);
		final String[] files = path.list();
		for (int i = 0; i < files.length; i++) {
			final String file = aPath + '/' + files[i];
			if (new File(file).isDirectory()) {
				Files.getJARFiles0(file, someJARFiles);
			}
			else if (file.endsWith(".jar")) {
				someJARFiles.add(file);
			}
		}
	}
}
