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
package parser.reader;

public abstract class NamedReader implements Comparable<NamedReader> {
	private final String name;

	private final NamedReaderType type;

	public NamedReader(final String name, final NamedReaderType type) {
		this.name = name;
		this.type = type;
	}

	public int compareTo(final NamedReader arg0) {
		return this.getName().compareTo(arg0.getName());
	}

	public String getName() {
		return this.name;
	}

	public NamedReaderType getType() {
		return this.type;
	}

	abstract public NamedReader[] read();
}
