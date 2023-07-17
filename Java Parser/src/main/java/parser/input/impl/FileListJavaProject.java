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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import parser.reader.impl.NamedReaderFactory;
import common.tools.constants.Constants;
import common.tools.file.FileTools;

public class FileListJavaProject extends FileSystemJavaProject {

	private final String[] files;

	public FileListJavaProject(
		final List<String> classPaths,
		final List<String> sourceDirectories,
		final String listFile) throws Exception {

		super(classPaths, sourceDirectories);

		final String list =
			new String(FileTools.Instance.readFile(new File(listFile)));
		this.files = Pattern.compile(Constants.NEW_LINE).split(list);
	}

	protected NamedReader[] buildCompilationUnitList() {

		final List<NamedReader> compilationUnitList =
			new ArrayList<NamedReader>();

		for (final String filePath : this.files) {
			final NamedReader reader =
				NamedReaderFactory.Instance.createNamedReader(
					filePath.trim(),
					NamedReaderType.SourceFile);
			if (reader != null) {
				compilationUnitList.add(reader);
			}
		}

		return compilationUnitList.toArray(new NamedReader[0]);
	}

	public String[] getFiles() {
		return this.files;
	}
}
