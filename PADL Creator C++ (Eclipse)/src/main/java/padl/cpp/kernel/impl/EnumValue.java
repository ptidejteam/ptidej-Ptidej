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
package padl.cpp.kernel.impl;

import padl.cpp.kernel.IEnumValue;
import padl.kernel.Constants;
import padl.kernel.impl.Field;

public class EnumValue extends Field implements IEnumValue {
	private static final long serialVersionUID = 1L;

	public EnumValue(final char[] anID) {
		super(anID, anID, "EnumValue".toCharArray(), Constants.CARDINALITY_ONE);
	}
	private EnumValue(
		final char[] anID,
		final char[] aName,
		final char[] aFieldType,
		final int aCardinality) {

		super(anID, aName, aFieldType, aCardinality);
	}
}
