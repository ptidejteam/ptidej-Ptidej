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
package ptidej.reporting.typed.occurrence;

/**
 * 
 * @author Yann
 * @since  2008/10/03
 *
 */
public class DesignSmellCause {
	private final String codeSmellName;
	private final String codeSmellValue;

	public DesignSmellCause(
		final String aCodeSmellName,
		final String aCodeSmellValue) {

		this.codeSmellName = aCodeSmellName;
		this.codeSmellValue = aCodeSmellValue;
	}
	public String getName() {
		return this.codeSmellName;
	}
	public String getValue() {
		return this.codeSmellValue;
	}
	public String toString() {
		return this.codeSmellName + " = " + this.codeSmellValue;
	}
}
