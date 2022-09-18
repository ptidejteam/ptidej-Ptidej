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
package padl.cpp.kernel;

import padl.kernel.IEntity;
import padl.kernel.IFactory;
import padl.kernel.IParameter;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/07/12
 */
public interface ICPPFactoryEclipse extends IFactory {
	ICPPClass createClass(final char[] anID, final char[] aName);
	IDestructor createDestructor(final char[] anID, final char[] aName);
	IEnum createEnum(final char[] anID);
	IEnumValue createEnumValue(final char[] anID);
	ICPPGhost createGhost(final char[] anID, final char[] aName);
	IGlobalField createGlobalField(
		final char[] aID,
		final char[] aName,
		final char[] aType,
		final int aCardinality);
	IGlobalFunction createGlobalFunction(final char[] anID, final char[] aName);
	IGlobalFunctionGhost createGlobalFunctionGhost(
		final char[] anID,
		final char[] aName);
	IMemberStructure createMemberStructure(final char[] anID);
	IParameter createParameter(
		final IEntity parameterEntity,
		final char[] parameterName,
		final char[] parameterQualification,
		final int cardinality);
	IStructure createStructure(final char[] anID);
	IUnion createUnion(final char[] anID);
}
