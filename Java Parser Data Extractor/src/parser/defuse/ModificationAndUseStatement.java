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
package parser.defuse;

public class ModificationAndUseStatement {

	private final char[] lineNumber;
	private final char[] statement;
	private char[] location;

	public ModificationAndUseStatement(
		final char[] statement,
		final char[] lineNumber) {
		this.statement = statement;
		this.lineNumber = lineNumber;

	}

	public char[] getLineNumber() {
		return this.lineNumber;
	}

	public char[] getLocation() {
		return this.location;
	}

	public char[] getStatement() {
		return this.statement;
	}

	public String toString() {
		final String result =
			new String(this.getLineNumber()) + ";"
					+ new String(this.getStatement());
		//				+";"+ new String(this.getLocation());

		return result;
	}

}
