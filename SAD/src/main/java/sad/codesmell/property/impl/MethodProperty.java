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

import padl.kernel.IOperation;
import sad.codesmell.property.ICodeSmellProperty;

/**
 * Hold information about a method
 *
 * @author Pierre Leduc
 * @version 1.0
 * @since 2006/05/29
 */
public class MethodProperty extends PropertyContainer
	implements
		ICodeSmellProperty {

	final private IOperation iMethod;

	public MethodProperty(final IOperation method) {
		this.iMethod = method;
	}

	public IOperation getIMethod() {
		return this.iMethod;
	}

	public String getIDMethod() {
		return this.iMethod.getDisplayID();
	}

	public String toString(int count, final int propertyCount, final String codesmellName) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\n" + count + ".100." + codesmellName + ".MethodName-" + propertyCount + " = ");
		buffer.append(this.getIDMethod());

		// Add properties informations
		buffer.append(super.toString(count, codesmellName));

		return buffer.toString();
	}

}
