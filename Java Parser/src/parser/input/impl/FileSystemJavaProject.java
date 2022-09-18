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
package parser.input.impl;

import java.util.ArrayList;
import java.util.List;
import parser.input.SourceInputsHolder;
import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import parser.reader.impl.filesystem.FileNamedReaderFactory;

public class FileSystemJavaProject extends SourceInputsHolder {
	public FileSystemJavaProject(
		final List<String> classPaths,
		final List<String> sourceFilesAndDirectories) throws Exception {

		this.buildClasspathEntries(classPaths);
		this.setSourcepathEntries(this
			.buildSourcepathEntries(sourceFilesAndDirectories));
	}

	protected void buildClasspathEntries(final List<String> classPaths) {
		final List<NamedReader> jars = new ArrayList<NamedReader>();

		if (classPaths != null) {
			for (final String classpath : classPaths) {
				final NamedReader reader =
					FileNamedReaderFactory.Instance
						.createNamedReaderFromFile(classpath);
				if (reader != null) {
					jars.addAll(this.findReadersByType(
						reader,
						NamedReaderType.JarFile));
				}
			}
		}

		this.setClasspathEntries(jars.toArray(new NamedReader[0]));
	}

	protected NamedReader[] buildCompilationUnitList() {
		final List<NamedReader> compilationUnits = new ArrayList<NamedReader>();

		for (final NamedReader src : this.getSourcepathEntries()) {
			compilationUnits.addAll(this.findReadersByType(
				src,
				NamedReaderType.JavaFile));
		}
		return compilationUnits.toArray(new NamedReader[0]);
	}

	protected NamedReader[] buildSourcepathEntries(
		final List<String> sourceFilesAndDirectories) {
		final List<NamedReader> readers = new ArrayList<NamedReader>();

		if (sourceFilesAndDirectories != null) {
			NamedReader reader = null;
			for (final String sourceFileOrDirectory : sourceFilesAndDirectories) {
				reader =
					FileNamedReaderFactory.Instance
						.createNamedReaderFromFile(sourceFileOrDirectory);
				if (reader != null) {
					readers.add(reader);
				}
			}
		}

		return readers.toArray(new NamedReader[0]);
	}

	protected List<NamedReader> findReadersByType(
		final NamedReader parent,
		final NamedReaderType type) {

		final List<NamedReader> readersFound = new ArrayList<NamedReader>();

		if (parent.getType() == NamedReaderType.JarFile) {
			readersFound.add(parent);
		}
		else if (parent.getType() == NamedReaderType.Directory) {
			final NamedReader[] elements = parent.read();
			if (elements != null) {
				for (final NamedReader element : elements) {
					if (element != null) {
						if (element.getType() == NamedReaderType.Directory) {
							readersFound.addAll(this.findReadersByType(
								element,
								type));
						}
						else if (element.getType() == type) {
							readersFound.add(element);
						}
					}
				}
			}
		}
		return readersFound;
	}
}
