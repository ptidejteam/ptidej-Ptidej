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

import padl.kernel.IClass;
import sad.codesmell.property.ICodeSmellProperty;

/**
 * 
 */

public class ClassProperty extends PropertyContainer
	implements
		ICodeSmellProperty {

	final private IClass iClass;

	public ClassProperty(final IClass iclass) {
		this.iClass = iclass;
	}

	public IClass getIClass() {
		return this.iClass;
	}

	public String getIDClass() {
		return this.iClass.getDisplayID();
	}

	public String toString(final int count, final int propertyCount, final String codesmellName) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\n" + count + ".100." + codesmellName + "-" + propertyCount + " = ");
		buffer.append(this.getIDClass());

		// Add properties informations
		buffer.append(super.toString(count, codesmellName + "-" + propertyCount));

		return buffer.toString();
	}

}
