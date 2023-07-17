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

public class SourceCode extends NamedReader {

	/**
	 * @param name
	 */
	public SourceCode(final String name, final NamedReaderType type) {
		super(name, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.wrapper.input.NamedReader#read()
	 */
	@Override
	public NamedReader[] read() {
		return null;
	}

}
