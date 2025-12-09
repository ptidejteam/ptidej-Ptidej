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
package util.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.io.NamedInputStream;
import util.io.ProxyConsole;
import util.repository.FileAccessException;
import util.repository.IFileRepository;
import util.repository.IRepository;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/21
 */
class FileFolderRepository implements IFileRepository {
	private static final void storeFiles(final File theCurrentDirectory,
			final List<NamedInputStream> aListOfFiles)
			throws FileAccessException {

		final String[] files = theCurrentDirectory.list();
		if (files == null) {
			throw new FileAccessException(FileFolderRepository.class.getName()
					+ " cannot find any file in: "
					+ theCurrentDirectory.getAbsolutePath());
		}
		for (int i = 0; i < files.length; i++) {
			final File file = new File(theCurrentDirectory.getAbsolutePath()
					+ File.separatorChar + files[i]);
			if (file.isFile()) {
				// Stephane 2008/07/16: Too many FIS open!
				// The following code closes unused FIS
				// to allow analysing large number of files.
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(file);
					aListOfFiles.add(new NamedInputStream(
							file.getCanonicalPath(), fileInputStream));
				}
				catch (final FileNotFoundException fnfe) {
					fnfe.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
				catch (final IOException ioe) {
					ioe.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
				finally {
					if (fileInputStream != null) {
						try {
							fileInputStream.close();
						}
						catch (final IOException ioe) {
							ProxyConsole.getInstance().warningOutput().print(
									FileFolderRepository.class.getName());
							ProxyConsole.getInstance().warningOutput()
									.println(" cannot close a file!");
						}
					}
				}
			}
			else {
				FileFolderRepository.storeFiles(file, aListOfFiles);
			}
		}
	}

	private static final NamedInputStream[] getFiles(
			final Class<? extends IRepository> aClass)
			throws FileAccessException {

		// Yann 2004/07/28: Demo!
		// I must catch the AccessControlException
		// thrown when attempting loading anything
		// from the applet viewer.
		// Yann 2022/09/15: Applet!
		// Applet-related things are slated for removal...
		//	try {
		// TODO Do not use getClassPath() and make code simpler?
		final StringBuffer directory = new StringBuffer();
		directory.append(util.io.Files.getClassPath(aClass));
		directory.append(aClass.getPackage().getName().replace('.', '/'));
		String finalDirectory = directory.toString().replace('\\', '/');

		ProxyConsole.getInstance().warningOutput()
				.print(FileFolderRepository.class.getName());
		ProxyConsole.getInstance().warningOutput()
				.print(" is the current repository on: ");
		ProxyConsole.getInstance().warningOutput().println(finalDirectory);

		final File directoryFile = new File(finalDirectory);
		final List<NamedInputStream> listOfFiles = new ArrayList<NamedInputStream>();
		FileFolderRepository.storeFiles(directoryFile, listOfFiles);
		final NamedInputStream[] arrayOfFiles = new NamedInputStream[listOfFiles
				.size()];
		listOfFiles.toArray(arrayOfFiles);
		return arrayOfFiles;
		//	}
		//	catch (final AccessControlException ace) {
		//		return new NamedInputStream[0];
		//	}
	}

	private NamedInputStream[] fileStreams;
	private final IRepository locator;

	public FileFolderRepository(final IRepository aLocator) {
		this.locator = aLocator;
	}

	public NamedInputStream[] getFiles(final String aPath,
			final String anExtension) throws FileAccessException {

		// TODO Is it still necessary?
		if (this.fileStreams == null) {
			this.fileStreams = FileFolderRepository
					.getFiles(this.locator.getClass());
		}
		return this.fileStreams;
	}

	public String toString() {
		return this.fileStreams.length + " files in repository.";
	}
}
