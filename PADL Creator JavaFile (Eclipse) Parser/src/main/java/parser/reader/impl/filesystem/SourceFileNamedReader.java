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
import common.tools.file.FileTools;
import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import parser.reader.impl.NamedReaderFactory;
import util.io.ProxyConsole;

/**
 * Source file is not necessary to be ended with .java
 * 
 * @author Wei Wu
 * 
 */
public class SourceFileNamedReader extends NamedReader {

	/**
	 * @param name
	 * @param type
	 */
	public SourceFileNamedReader(
		final String name,
		final NamedReaderType type) {
		super(name, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see parser.reader.NamedReader#read()
	 */
	@Override
	public NamedReader[] read() {
		final File file = new File(this.getName());

		if (!file.isFile()) {
			ProxyConsole.getInstance().errorOutput().println(
				this.getName() + " is not a file!");
			throw new RuntimeException(this.getName() + " is not a file!");
		}

		return new NamedReader[] {
				NamedReaderFactory.Instance.createNamedReader(
					new String(FileTools.Instance.readFile(file)),
					NamedReaderType.SourceCode) };
	}

}
