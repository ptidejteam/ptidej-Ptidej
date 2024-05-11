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

import org.apache.commons.lang3.ArrayUtils;

import padl.kernel.IEntity;
import padl.kernel.IParameter;
import padl.kernel.impl.Parameter;

public class CPPParameter extends Parameter implements IParameter {
	private static final long serialVersionUID = -1979306638669159781L;

	private final char[] qualitification;

	public CPPParameter(final IEntity anEntity, final char[] aName, final char[] aQualification,
			final int aCardinality) {

		super(anEntity, aName, aCardinality);
		this.qualitification = aQualification;
	}

	public char[] getTypeName() {
		final char[] nakedName = super.getTypeName();
		return ArrayUtils.addAll(nakedName, this.qualitification);
	}
}
