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
package parser.reader.impl.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import util.io.ProxyConsole;
import common.tools.file.FileTools;

public class DirectoryNamedReader extends NamedReader {

	/**
	 * @param name
	 */
	public DirectoryNamedReader(final String name, final NamedReaderType type) {
		super(name, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.wrapper.reader.NamedReader#read()
	 */
	@Override
	public NamedReader[] read() {
		final File dir = new File(this.getName());

		if (!dir.isDirectory()) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println(this.getName() + " is not a directory!");
			return null;
		}

		final String[] paths = FileTools.Instance.getElementList(dir);
		final List<NamedReader> readers = new ArrayList<NamedReader>();

		if (paths != null) {
			NamedReader reader = null;
			for (int i = paths.length - 1; i >= 0; i--) {

				reader =
					FileNamedReaderFactory.Instance
						.createNamedReaderFromFile(paths[i]);
				if (reader != null) {
					readers.add(reader);
				}
			}
		}
		return readers.toArray(new NamedReader[0]);
	}
}
