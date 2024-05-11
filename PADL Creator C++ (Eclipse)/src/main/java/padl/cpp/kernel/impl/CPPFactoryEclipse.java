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

import padl.cpp.kernel.ICPPClass;
import padl.cpp.kernel.ICPPFactoryEclipse;
import padl.cpp.kernel.ICPPGhost;
import padl.cpp.kernel.ICPPMemberClass;
import padl.cpp.kernel.ICPPMemberGhost;
import padl.cpp.kernel.IDestructor;
import padl.cpp.kernel.IEnum;
import padl.cpp.kernel.IEnumValue;
import padl.cpp.kernel.IGlobalField;
import padl.cpp.kernel.IGlobalFunction;
import padl.cpp.kernel.IGlobalFunctionGhost;
import padl.cpp.kernel.IMemberStructure;
import padl.cpp.kernel.IStructure;
import padl.cpp.kernel.IUnion;
import padl.cpp.kernel.event.CPPEventGenerator;
import padl.kernel.IEntity;
import padl.kernel.IFactory;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

/**
 * @author Sebastien Colladon
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/07/11
 */
public class CPPFactoryEclipse extends Factory implements ICPPFactoryEclipse {
	private static final long serialVersionUID = 3342247491732965777L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (CPPFactoryEclipse.UniqueInstance == null) {
			CPPFactoryEclipse.UniqueInstance = new CPPFactoryEclipse();
		}
		return CPPFactoryEclipse.UniqueInstance;
	}

	private CPPFactoryEclipse() {
	}

	public ICPPClass createClass(final char[] anID, final char[] aName) {
		return new CPPClass(anID, aName);
	}

	public IDestructor createDestructor(final char[] anID, final char[] aName) {

		return new Destructor(anID, aName);
	}

	public IEnum createEnum(final char[] anID) {

		return new Enum(anID);
	}

	public IEnumValue createEnumValue(final char[] anID) {
		final IEnumValue anEnumValue = new EnumValue(anID);
		return anEnumValue;
	}

	public ICPPGhost createGhost(final char[] anID, final char[] aName) {
		return new CPPGhost(anID, aName);
	}

	public IGlobalField createGlobalField(
		final char[] anID,
		final char[] aName,
		final char[] aType,
		final int aCardinality) {

		return new GlobalField(anID, aName, aType, aCardinality);
	}

	public IGlobalFunction createGlobalFunction(
		final char[] anID,
		final char[] aName) {

		return new GlobalFunction(anID, aName);
	}

	public IGlobalFunctionGhost createGlobalFunctionGhost(
		final char[] anID,
		final char[] aName) {

		return new GlobalFunctionGhost(anID, aName);
	}

	public ICPPMemberClass createMemberClass(
		final char[] anID,
		final char[] aName) {

		return new CPPMemberClass(anID, aName);
	}

	public ICPPMemberGhost createMemberGhost(
		final char[] anID,
		final char[] aName) {

		return new CPPMemberGhost(anID, aName);
	}

	public IMemberStructure createMemberStructure(final char[] aName) {
		return new MemberStructure(aName);
	}

	public IParameter createParameter(
		final IEntity anEntity,
		final char[] aName,
		final char[] aQualification,
		final int aCardinality) {

		return new CPPParameter(anEntity, aName, aQualification, aCardinality);
	}

	public IStructure createStructure(final char[] aName) {
		return new Structure(aName);
	}

	public IUnion createUnion(final char[] aName) {
		return new Union(aName);
	}

	protected IWalker getEventGenerator() {
		return CPPEventGenerator.getInstance();
	}
}
