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
package padl.kernel.impl;

import padl.kernel.Constants;
import padl.kernel.IElementMarker;
import padl.kernel.IField;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;
import com.ibm.toad.cfparse.utils.Access;

// Sebastien Colladon 21/04/2012 : Change the visibility to public in order to allow other project to extend from this class in the particular case of eclipse bundle loader (avoid IllegalAccessError).
public class Field extends Element implements IElementMarker, IField {
	private static final long serialVersionUID = -2269333542392931119L;
	private int cardinality;
	private char[] fieldType;

	public Field(final String anID) {
		this(anID.toCharArray());
	}

	public Field(final char[] anID) {
		this(anID, anID, null, 1);
	}

	public Field(
		final char[] anID,
		final char[] aName,
		final char[] aFieldType,
		final int aCardinality) {

		super(anID);
		this.setType(aFieldType);
		this.setName(aName);
		this.setCardinality(aCardinality);
	}

	public int getCardinality() {
		return this.cardinality;
	}

	public String getDisplayTypeName() {
		return String.valueOf(this.getType());
	}

	public char[] getType() {
		return this.fieldType;
	}

	public void setCardinality(int cardinality) {
		if (cardinality < 1) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"CARDINALITY",
				IField.class,
				new Object[] { Integer.valueOf(cardinality) }));
		}

		this.cardinality = cardinality;
	}

	public void setType(final char[] fieldType) {
		this.fieldType = fieldType;
	}

	public void setVisibility(final int visibility) {
		super.setVisibility(visibility & ~Access.ACC_ABSTRACT);
	}

	public String toString() {
		if (Constants.DEBUG) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("// Field.toString()");
		}
		return toString(0);
	}

	public String toString(final int tab) {
		if (Constants.DEBUG) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("// Field.toString(int)");
		}
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString(tab));
		if (this.getVisibility() != 0) {
			codeEq.append(' ');
		}
		codeEq.append(this.getType());
		codeEq.append(' ');
		codeEq.append(this.getName());
		final String[] codeLines = this.getCodeLines();
		// Yann: Can be null. The case "empty array" is dealt with in the loop. 
		if (codeLines != null) {
			codeEq.append(" = ");
			for (int i = 0; i < codeLines.length; i++) {
				codeEq.append('\n');
				Util.addTabs(tab + 1, codeEq);
				codeEq.append(codeLines[i]);
			}
		}
		codeEq.append(';');
		return codeEq.toString();
	}
}
