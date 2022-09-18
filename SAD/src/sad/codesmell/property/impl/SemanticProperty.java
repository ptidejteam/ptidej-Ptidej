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
package sad.codesmell.property.impl;

import sad.codesmell.property.ICodeSmellProperty;

/**
 * Hold information about a semantic property
 *
 * @author Pierre Leduc
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @since  2006/05/29
 */
public class SemanticProperty implements ICodeSmellProperty {
	final private String detectedKeyword;

	public SemanticProperty(final String keyword) {
		this.detectedKeyword = keyword;
	}
	public String getKeyword() {
		return this.detectedKeyword;
	}
	public String toString(
		final int count,
		final int propertyCount,
		final String codesmellName) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append('\n');
		buffer.append(count);
		buffer.append(".100.");
		buffer.append(codesmellName);
		buffer.append(".DetectedKeyword-");
		buffer.append(propertyCount);
		buffer.append(" = ");
		buffer.append(this.detectedKeyword);
		return buffer.toString();
	}
}
