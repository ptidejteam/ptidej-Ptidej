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
package padl.kernel.cpp.antlr.impl;

import padl.kernel.IFactory;
import padl.kernel.cpp.antlr.ICPPFactoryANTLR;
import padl.kernel.cpp.antlr.IDestructor;
import padl.kernel.cpp.antlr.IEnum;
import padl.kernel.cpp.antlr.IGlobalField;
import padl.kernel.cpp.antlr.IStructure;
import padl.kernel.cpp.antlr.IUnion;
import padl.kernel.impl.Factory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/07/11
 */
public class CPPFactoryANTLR extends Factory implements ICPPFactoryANTLR {
	private static final long serialVersionUID = 3342247491732965777L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (CPPFactoryANTLR.UniqueInstance == null) {
			CPPFactoryANTLR.UniqueInstance = new CPPFactoryANTLR();
		}
		return CPPFactoryANTLR.UniqueInstance;
	}

	private CPPFactoryANTLR() {
		super();
	}
	public IDestructor createDestructor(final char[] anID, final char[] aName) {
		return new Destructor(anID, aName);
	}
	public IEnum createEnum(final char[] aName) {
		return new Enum(aName);
	}
	public IGlobalField createGlobalField(
		final char[] aName,
		final char[] aType,
		final int aCardinality) {

		return new GlobalField(aName, aType, aCardinality);
	}
	public IStructure createStructure(final char[] aName) {
		return new Structure(aName);
	}
	public IUnion createUnion(final char[] aName) {
		return new Union(aName);
	}
}
