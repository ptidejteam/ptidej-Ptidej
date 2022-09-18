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
package parser.defuse.entities;

import parser.defuse.Entity;

public class FieldEntity extends Entity {

	public FieldEntity(
		final char[] name,
		final char[] signature,
		final char[] type,
		final char[] lineNumber,
		final char[] parent,
		final char[] key) {
		super(name, signature, type, lineNumber, parent, key);
		// TODO Auto-generated constructor stub
	}

}
