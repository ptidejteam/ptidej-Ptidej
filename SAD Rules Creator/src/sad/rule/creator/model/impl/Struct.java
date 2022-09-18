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
package sad.rule.creator.model.impl;

import sad.rule.creator.model.IStruct;

/**
 * @author Pierre Leduc
 */
public class Struct extends Attribute implements IStruct {
	private final int type;
	private final int value;

	public Struct(final String aName, final int aType, final int value) {
		super(aName);
		this.type = aType;
		this.value = value;
	}

	public int getType() {
		return this.type;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName());
		buffer.append("\nName: ");
		buffer.append(this.getID());
		buffer.append("\nType: ");
		buffer.append(this.getType());

		if (this.value != -1) {
			buffer.append("\nType: ");
			buffer.append(this.getType());
		}

		return buffer.toString();
	}
}
