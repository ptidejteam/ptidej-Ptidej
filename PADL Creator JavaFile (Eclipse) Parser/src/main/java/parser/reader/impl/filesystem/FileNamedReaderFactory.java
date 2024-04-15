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
import parser.reader.NamedReader;
import parser.reader.NamedReaderType;
import parser.reader.impl.NamedReaderFactory;
import common.tools.file.FileTools;

public enum FileNamedReaderFactory {

	Instance;

	public NamedReader createNamedReaderFromFile(
		final String sourceFileOrDirectory) {
		final File file = new File(sourceFileOrDirectory);
		NamedReaderType type = null;

		if (file.isDirectory()) {
			type = NamedReaderType.Directory;
		}
		else if (file.isFile()) {
			if (FileTools.Instance.isJarFile(file)) {
				type = NamedReaderType.JarFile;
			}
			else if (FileTools.Instance.isJavaFile(file)) {
				type = NamedReaderType.JavaFile;
			}
			else {
				type = NamedReaderType.Other;
			}
		}
		else {
			type = NamedReaderType.Other;
		}

		return NamedReaderFactory.Instance.createNamedReader(
			FileTools.Instance.getCanonicalPath(file),
			type);
	}

}
