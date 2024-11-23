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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarFile;

import util.io.NamedInputStream;
import util.io.ProxyConsole;
import util.repository.IFileRepository;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since 2009/03/03
 */
class JarFileRepository implements IFileRepository {
	private static String JarFile;
	private static JarFileRepository UniqueInstance;

	// TODO Should not be a false Singleton!
	public static JarFileRepository getInstance(final String aJARFile) {
		if (JarFileRepository.UniqueInstance == null
				|| JarFileRepository.JarFile != aJARFile) {

			JarFileRepository.JarFile = aJARFile;
			JarFileRepository.UniqueInstance = new JarFileRepository(
					JarFileRepository.JarFile);
		}
		return JarFileRepository.UniqueInstance;
	}

	final List<String> listOfStreamsNames = new ArrayList<String>();
	final List<NamedInputStream> listOfStreams = new ArrayList<NamedInputStream>();

	private JarFileRepository(final String aJARFile) {
		ProxyConsole.getInstance().warningOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().warningOutput()
				.print(" is the current repository on: ");
		ProxyConsole.getInstance().warningOutput().println(aJARFile);

		try {
			final JarFile jarFile = new JarFile(JarFileRepository.JarFile);
			final Enumeration<?> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry entry = (JarEntry) entries.nextElement();
				final String entryName = entry.getName();

				if (!this.listOfStreamsNames.contains(entryName)) {
					this.listOfStreamsNames.add(entryName);
					this.listOfStreams.add(new NamedInputStream(entryName,
							jarFile.getInputStream(entry)));
				}
			}
			jarFile.close();
		}
		catch (final JarException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public NamedInputStream[] getFiles(final String aPath,
			final String anExtension) {

		return this.listOfStreams.toArray(new NamedInputStream[0]);
	}

	public String toString() {
		return this.listOfStreams.size() + " files in repository.";
	}
}
