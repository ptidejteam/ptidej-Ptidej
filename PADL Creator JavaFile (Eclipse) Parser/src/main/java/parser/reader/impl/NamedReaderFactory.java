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
package parser.reader.impl;

import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import parser.reader.impl.filesystem.DirectoryNamedReader;
import parser.reader.impl.filesystem.JarFileNamedReader;
import parser.reader.impl.filesystem.JavaFileNamedReader;
import parser.reader.impl.filesystem.SourceFileNamedReader;

public enum NamedReaderFactory {

	Instance;

	public NamedReader createNamedReader(
		final String name,
		final NamedReaderType type) {
		NamedReader reader = null;

		switch (type) {
			case SourceCode :
				reader = new SourceCode(name, type);
				break;
			case JarFile :
				reader = new JarFileNamedReader(name, type);
				break;
			case JavaFile :
				reader = new JavaFileNamedReader(name, type);
				break;
			case SourceFile :
				reader = new SourceFileNamedReader(name, type);
				break;
			case Directory :
				reader = new DirectoryNamedReader(name, type);
				break;
			default :
				break;
		}

		return reader;
	}

}
