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
package common.tools.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import common.tools.constants.Constants;

public enum FileTools {

	Instance;

	private void addFileByExtension(
		final List<String> list,
		final File file,
		final String extension) {
		if (this.isAFileWithAnExtension(file, extension)) {
			list.add(this.getCanonicalPath(file));
		}
	}

	public File createNoExistingFile(final File file) {
		final File newFile = file;
		try {
			if (!newFile.exists()) {
				final File parent = newFile.getParentFile();

				if (!parent.exists()) {
					if (parent.mkdirs() == false) {
						return null;
					}
				}

				if (newFile.createNewFile() == false) {
					return null;
				}
			}

		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return newFile;
	}

	public String getCanonicalPath(final File file) {

		String path = null;
		try {
			path = file.getCanonicalPath();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return path;
	}

	public String getCurrentDirectory() {
		try {
			return new File(Constants.POINT + Constants.SEPARATOR)
				.getCanonicalPath();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return Constants.EMPTY_STR;
	}

	public String[] getElementList(final File dir) {
		final String elements[] = dir.list();
		StringBuffer absPath = null;
		final String[] paths = new String[elements.length];

		for (int i = elements.length - 1; i >= 0; i--) {
			absPath =
				new StringBuffer(dir.getPath())
					.append(Constants.SEPARATOR)
					.append(elements[i]);
			paths[i] = absPath.toString();
		}

		return paths;
	}

	public List<String> getFileListByExtension(
		final File file,
		final String extension) {
		final List<String> fileList = new ArrayList<String>();
		if (file.isDirectory()) {
			final String elements[] = file.list();
			StringBuffer absPath = null;
			for (final String name : elements) {
				absPath =
					new StringBuffer(file.getPath())
						.append(Constants.SEPARATOR)
						.append(name);
				final File element = new File(absPath.toString());

				if (element.isDirectory()) {
					fileList.addAll(this.getFileListByExtension(
						element,
						extension));
				}
				else {
					this.addFileByExtension(fileList, element, extension);
				}
			}

		}
		else {
			this.addFileByExtension(fileList, file, extension);
		}
		return fileList;
	}

	public boolean isAFileWithAnExtension(
		final File file,
		final String extension) {
		return file.getPath().toLowerCase().endsWith(extension);
	}

	public boolean isJarFile(final File file) {
		return this.isAFileWithAnExtension(file, Constants.JAR_FILE_EXT);
	}

	public boolean isJavaFile(final File file) {
		return this.isAFileWithAnExtension(file, Constants.JAVA_FILE_EXT);
	}

	public char[] readFile(final File file) {
		if (file.isDirectory()) {
			return null;
		}

		char[] buffer = null;

		try {
			final FileReader fileReader =
				ProxyDisk.getInstance().fileAbsoluteInput(file);
			final BufferedReader bufferedReader =
				new BufferedReader(fileReader);
			buffer = new char[(int) file.length()];
			bufferedReader.read(buffer);
			bufferedReader.close();
			fileReader.close();
		}
		catch (final Exception e) {
			buffer = null;
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return buffer;
	}
	public char[] readTempFile(final File file) {
		// TODO Unify readTempFile() and readAbsoluteFile()
		if (file.isDirectory()) {
			return null;
		}

		final StringBuffer buffer = new StringBuffer();
		try {
			final FileReader fileReader =
				ProxyDisk.getInstance().fileTempInput(file);
			int c;
			while ((c = fileReader.read()) != -1) {
				buffer.append((char) c);
			}
			fileReader.close();
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return buffer.toString().toCharArray();
	}

	/**
	 * 
	 * @param file
	 * @param text
	 * @param append
	 * @return true if write successfully, otherwise false.
	 */

	public boolean writeTempFile(
		final File file,
		final char[] content,
		final boolean append) {

		if (file.isDirectory()) {
			return false;
		}
		try {
			final Writer fileWriter =
				ProxyDisk.getInstance().fileTempOutput(file, append);
			final BufferedWriter bufferedWriter =
				new BufferedWriter(fileWriter);

			bufferedWriter.write(content);
			bufferedWriter.flush();
			fileWriter.flush();
			bufferedWriter.close();
			fileWriter.close();

			return true;
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return false;
	}
}
