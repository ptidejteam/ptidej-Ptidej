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
package padl.kernel.cpp.antlr;

import padl.kernel.IFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/07/12
 */
public interface ICPPFactoryANTLR extends IFactory {
	IDestructor createDestructor(final char[] anID, final char[] aName);
	IEnum createEnum(final char[] aName);
	IGlobalField createGlobalField(
		final char[] aName,
		final char[] aType,
		final int aCardinality);
	IStructure createStructure(final char[] aName);
	IUnion createUnion(final char[] aName);
}
