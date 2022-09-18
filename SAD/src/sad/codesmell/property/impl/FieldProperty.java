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

import padl.kernel.IField;
import sad.codesmell.property.ICodeSmellProperty;

/**
 * Hold information about a field
 *
 * @author Pierre Leduc
 * @version 1.0
 * @since 2006/05/29
 */
public class FieldProperty implements ICodeSmellProperty {

	final private IField iField;

	public FieldProperty(IField field) {
		this.iField = field;
	}

	public IField getIField() {
		return this.iField;
	}

	public String getIDField() {
		return this.iField.getDisplayID();
	}

	public String toString(final int count, final int propertyCount, final String codesmellName) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\n" + count + ".100." + codesmellName + ".FieldName-" + propertyCount + " = ");
		buffer.append(this.getIDField());

		return buffer.toString();
	}

}
