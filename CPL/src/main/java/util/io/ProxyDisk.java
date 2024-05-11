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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

public class ProxyDisk {
	private static final String TEMP_DIRECTORY = "tmp/";
	private static ProxyDisk UniqueInstance;

	public static ProxyDisk getInstance() {
		if (ProxyDisk.UniqueInstance == null) {
			ProxyDisk.UniqueInstance = new ProxyDisk();
		}

		return ProxyDisk.UniqueInstance;
	}

	private File tempDirectory;

	private ProxyDisk() {
		try {
			this.tempDirectory = this
					.createDirectories(ProxyDisk.TEMP_DIRECTORY);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	private File createDirectories(final String aPathToADirectory)
			throws IOException {

		final File defaultDirectory = new File(aPathToADirectory);
		if (!defaultDirectory.exists()) {
			try {
				FileUtils.forceMkdir(defaultDirectory);
			}
			catch (final IOException e) {
				ProxyConsole.getInstance().errorOutput().print(
						"FileOutputProxy cannot create the necessary directory: "
								+ aPathToADirectory + " (canonical path: "
								+ defaultDirectory.getCanonicalPath() + ")");
				ProxyConsole.getInstance().errorOutput()
						.print(aPathToADirectory);
				ProxyConsole.getInstance().errorOutput()
						.print("(canonical path: ");
				ProxyConsole.getInstance().errorOutput()
						.print(defaultDirectory.getCanonicalPath());
				ProxyConsole.getInstance().errorOutput().println(
						")\nFalling back on to default temporary directory!");
				return new File(System.getProperty("java.io.tmpdir"));
			}
		}
		else if (defaultDirectory.isFile()) {
			throw new IOException(
					"FileOutputProxy cannot use a file as a directory: "
							+ aPathToADirectory + "(canonical path: "
							+ defaultDirectory.getCanonicalPath() + ")");
		}
		return defaultDirectory;
	}

	private File createFile(final String aFileName, final boolean readMode,
			final boolean shouldAppend) {

		File file;
		int indexOfLastPathSeparator;

		// Yann 2013/05/29: Separators...
		// I make sure to consider any kind of path separators...
		if ((indexOfLastPathSeparator = Math.max(aFileName.lastIndexOf('/'),
				aFileName.lastIndexOf('\\'))) > -1) {

			indexOfLastPathSeparator++;
			final String somePaths = aFileName.substring(0,
					indexOfLastPathSeparator);
			final String fileName = aFileName
					.substring(indexOfLastPathSeparator);
			try {
				file = new File(this.createDirectories(somePaths), fileName);
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				ProxyConsole.getInstance().errorOutput().println(
						"FileOutputProxy will use its default directory");
				file = new File(ProxyDisk.TEMP_DIRECTORY, fileName);
			}
		}
		else {
			file = new File(aFileName);
		}
		if (!readMode && !shouldAppend && file.exists()) {
			ProxyConsole.getInstance().warningOutput()
					.print(this.getClass().getName());
			ProxyConsole.getInstance().warningOutput()
					.print(" reports that \"");
			ProxyConsole.getInstance().warningOutput().print(aFileName);
			ProxyConsole.getInstance().warningOutput()
					.print(" already exists (\"");
			ProxyConsole.getInstance().warningOutput()
					.print(file.getAbsolutePath());
			ProxyConsole.getInstance().warningOutput().println("\")");
		}
		return file;
	}

	public File directoryTempFile() {
		return this.tempDirectory;
	}

	public File directoryTempOutput(final String someSubpaths) {
		String newPath = ProxyDisk.TEMP_DIRECTORY + someSubpaths;
		// Yann 2013/05/29: Separators...
		// I make sure to consider any kind of path separators...
		if (!(someSubpaths.endsWith("/") || someSubpaths.endsWith("\\"))) {
			newPath += '/';
		}
		if (someSubpaths.indexOf("..") > -1) {
			ProxyConsole.getInstance().warningOutput().println(
					"ProxyDisk advises not to \"escape\" from its default directory: "
							+ someSubpaths);
		}
		try {
			final File directory = this.createDirectories(newPath);
			return directory;
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			ProxyConsole.getInstance().errorOutput()
					.println("FileOutputProxy will use its default directory");
			return this.tempDirectory;
		}
	}

	public String directoryTempString() {
		// Yann 2013/05/31: Silly file separator!
		// It use to be:
		//	return this.outputDirectory.getAbsolutePath();
		// but getAbsolutePath() returns a path without
		// a trailing file separator, so silly...
		return ProxyDisk.TEMP_DIRECTORY;
	}

	public FileReader fileAbsoluteInput(final File aFile) {
		return this.fileAbsoluteInput(aFile.getAbsolutePath());
	}

	public FileReader fileAbsoluteInput(final String aFileName) {
		return this.fileInput(aFileName);
	}

	public FileWriter fileAbsoluteOutput(final String aFileName) {
		return this.fileAbsoluteOutput(aFileName, false);
	}

	public FileWriter fileAbsoluteOutput(final String aFileName,
			final boolean shouldAppend) {

		return this.fileOutput(aFileName, shouldAppend);
	}

	private FileReader fileInput(final String aFileName) {
		try {
			final File file = this.createFile(aFileName, true, false);
			// TODO Handle a uniform encoding?
			return new FileReader(file);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			// Fail fast!
			return null;
		}
	}

	private FileWriter fileOutput(final String aFileName,
			final boolean shouldAppend) {

		try {
			final File file = this.createFile(aFileName, false, shouldAppend);
			// TODO Handle a uniform encoding?
			return new FileWriter(file, shouldAppend);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			// Fail fast!
			return null;
		}
	}

	/**
	 * @param aFileName the file that will be used in the temporary directory.
	 * @return Full canonical path to the temporary file named "aFileName" 
	 */
	public File fileTempFile() {
		return new File(this.fileTempString());
	}

	public FileReader fileTempInput(final File aFile) {
		return this.fileTempInput(aFile.getName());
	}

	public FileReader fileTempInput(final String aFileName) {
		return this.fileInput(ProxyDisk.TEMP_DIRECTORY + aFileName);
	}

	public String fileTempInputString(final String aFileName) {
		final File file = this.createFile(aFileName, false, false);
		return file.getAbsolutePath();
	}

	public FileWriter fileTempOutput(final File file,
			final boolean shouldAppend) {
		return this.fileTempOutput(file.getName(), shouldAppend);
	}

	public FileWriter fileTempOutput(final String aFileName) {
		return this.fileTempOutput(aFileName, false);
	}

	public FileWriter fileTempOutput(final String aFileName,
			final boolean shouldAppend) {

		return this.fileOutput(ProxyDisk.TEMP_DIRECTORY + aFileName,
				shouldAppend);
	}

	public String fileTempOutputString(final String aFileName) {
		final File file = this.createFile(ProxyDisk.TEMP_DIRECTORY + aFileName,
				false, false);
		return file.getAbsolutePath();
	}

	/**
	 * @return A unique file name that will be created in the temporary directory. 
	 */
	public String fileTempString() {
		try {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(this.tempDirectory.getCanonicalPath());
			buffer.append(File.separatorChar);
			buffer.append("File.");

			final Calendar calendar = Calendar.getInstance();
			final SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyyMMdd'H'HHmmss");
			buffer.append(sdf.format(calendar.getTime()));
			buffer.append(".temp");

			final File file = this.createFile(buffer.toString(), true, false);
			return file.getCanonicalPath();
		}
		catch (final IOException e) {
			// Not much that we can do if we cannot create a temporary file
			// in the temporary directory, which includes an attempt to use
			// the OS official temporary directory, see createFile(...).
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			System.exit(-1);
			return "";
		}
	}
}
