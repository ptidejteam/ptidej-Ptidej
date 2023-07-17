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

import padl.kernel.IFirstClassEntity;
import sad.codesmell.property.ICodeSmellProperty;

/**
 * Hold information about an interface
 *
 * @author Pierre Leduc
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @since  2006/05/29
 */
public class InterfaceProperty extends PropertyContainer implements ICodeSmellProperty {
	private final IFirstClassEntity interfaze;

	public InterfaceProperty(final IFirstClassEntity anInterface) {
		this.interfaze = anInterface;
	}
	public IFirstClassEntity getInterface() {
		return this.interfaze;
	}
	public String getIDInterface() {
		return this.interfaze.getDisplayID();
	}
	public String toString(
		int count,
		final int propertyCount,
		final String codesmellName) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append('\n');
		buffer.append(count);
		buffer.append(".100.");
		buffer.append(codesmellName);
		buffer.append(".InterfaceName-");
		buffer.append(propertyCount);
		buffer.append(" = ");
		buffer.append(this.getIDInterface());
		return buffer.toString();
	}
}
