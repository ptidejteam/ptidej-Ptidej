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
package padl.kernel.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import padl.event.EventGenerator;
import padl.kernel.Constants;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IComposition;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IEntity;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFieldAccess;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public class Factory implements IFactory, Serializable {
	private static IPackageDefault DefaultPackage;
	private static final IFirstClassEntity HIERARCHY_ROOT_ENTITY = new Ghost(
		Constants.DEFAULT_HIERARCHY_ROOT_ID,
		Constants.DEFAULT_HIERARCHY_ROOT_NAME);
	private static final Map PrimitiveEntities = new HashMap();
	private static final long serialVersionUID = -4969943969597847522L;

	// Sebastien Colladon 19/04/2012 : Change with the abstract type for more flexibility in the legacy
	// TODO Make field and constructor private
	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (Factory.UniqueInstance == null) {
			Factory.UniqueInstance = new Factory();
		}
		return Factory.UniqueInstance;
	}
	protected Factory() {
	}

	public IAggregation createAggregationRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new Aggregation(aName, aTargetEntity, aCardinality);
	}
	public IAssociation createAssociationRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new Association(anID, aTargetEntity, aCardinality);
	}
	public IClass createClass(final char[] anID, final char[] aName) {
		return new Class(anID, aName);
	}
	public ICodeLevelModel createCodeLevelModel(final char[] aName) {
		final ICodeLevelModel codeLevelModel = new CodeLevelModel(aName);
		((CodeLevelModel) codeLevelModel).setFactory(this);
		((CodeLevelModel) codeLevelModel).setEventGenerator(this
			.getEventGenerator());
		return codeLevelModel;
	}
	public ICodeLevelModel createCodeLevelModel(final String aName) {
		return this.createCodeLevelModel(aName.toCharArray());
	}
	public IComposition createCompositionRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new Composition(anID, aTargetEntity, aCardinality);
	}
	public IComposition createCompositionRelationship(
		final IAssociation anAssociation) {

		return new Composition(anAssociation);
	}
	public IConstructor createConstructor(final char[] anID, final char[] aName) {
		final IConstructor constructor = new Constructor(anID);
		constructor.setName(aName);

		return constructor;
	}
	public IContainerAggregation createContainerAggregationRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new ContainerAggregation(anID, aTargetEntity, aCardinality);
	}
	public IContainerComposition createContainerCompositionRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new ContainerComposition(anID, aTargetEntity, aCardinality);
	}
	public IContainerComposition createContainerCompositionRelationship(
		final IAssociation anAssociation) {

		return new ContainerComposition(anAssociation);
	}
	public ICreation createCreationRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new Creation(anID, aTargetEntity, aCardinality);
	}
	//	public IDelegatingMethod createDelegatingMethod(
	//		final char[] aName,
	//		final IAssociation aTargetAssociation)  {
	//
	//		return new DelegatingMethod(aName, aTargetAssociation);
	//	}
	public IDelegatingMethod createDelegatingMethod(
		final char[] aName,
		final IAssociation aTargetAssociation,
		final IMethod aSupportMethod) {

		return new DelegatingMethod(aName, aTargetAssociation, aSupportMethod);
	}
	public IField createField(
		final char[] anID,
		final char[] aName,
		final char[] aType,
		final int aCardinality) {

		return new Field(anID, aName, aType, aCardinality);
	}
	public IFieldAccess createFieldAccess(
		int cardinality,
		int visibility,
		IField field,
		IFirstClassEntity entityDeclaringField) {

		return new FieldAccess(
			cardinality,
			visibility,
			field,
			entityDeclaringField);
	}
	public IGetter createGetter(final char[] anID, final char[] aName) {
		final IGetter getter = new Getter(anID);
		getter.setName(aName);

		return getter;
	}
	public IGetter createGetter(final IMethod aMethod) {
		return new Getter(aMethod);
	}
	public IGhost createGhost(final char[] anID, final char[] aName) {
		// Yann 2009/05/03: Useless?
		// Is the following check really necessary now?
		// TODO: Remove spurious code.
		String id = String.valueOf(anID);
		// Yann 2004/01/23: Ghost, arrays, and primitive types.
		// I make sure before creating a ghost that it is not
		// an array or a primitive type.
		final int bracketIndex = id.indexOf('[');
		if (bracketIndex > -1) {
			id = id.substring(0, bracketIndex);
		}
		/*if (Util.isPrimtiveType(id.toCharArray())) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ADD",
				Factory.class));
		}*/

		return new Ghost(id.toCharArray(), aName);
	}
	public IFirstClassEntity createHierarchyRoot() {
		return Factory.HIERARCHY_ROOT_ENTITY;
	}
	public IIdiomLevelModel createIdiomLevelModel(final char[] aName) {
		final IIdiomLevelModel idiomLevelModel = new IdiomLevelModel(aName);
		((IdiomLevelModel) idiomLevelModel).setFactory(this);
		((IdiomLevelModel) idiomLevelModel).setEventGenerator(this
			.getEventGenerator());
		return idiomLevelModel;
	}
	public IInterface createInterface(final char[] anID, final char[] aName) {
		return new Interface(anID, aName);
	}
	public IMemberClass createMemberClass(final char[] anID, final char[] aName) {
		return new MemberClass(anID, aName);
	}
	public IMemberGhost createMemberGhost(final char[] anID, final char[] aName) {
		return new MemberGhost(anID, aName);
	}
	public IMemberInterface createMemberInterface(
		final char[] anID,
		final char[] aName) {

		return new MemberInterface(anID, aName);
	}
	public IMethod createMethod(final char[] anID, final char[] aName) {
		final IMethod method = new Method(anID);
		method.setName(aName);

		return method;
	}
	public IMethodInvocation createMethodInvocation(
		final int type,
		final int cardinality,
		final int visibility,
		final IFirstClassEntity targetEntity) {

		return new MethodInvocation(type, cardinality, visibility, targetEntity);
	}
	public IMethodInvocation createMethodInvocation(
		final int type,
		final int cardinality,
		final int visibility,
		final IFirstClassEntity targetEntity,
		final IFirstClassEntity entityDeclaringField) {

		return new MethodInvocation(
			type,
			cardinality,
			visibility,
			targetEntity,
			entityDeclaringField);
	}
	public IPackage createPackage(final char[] aName) {
		return new Package(aName);
	}
	public IPackageDefault createPackageDefault() {
		// TODO: Remove static variable!
		if (Factory.DefaultPackage == null) {
			Factory.DefaultPackage = new PackageDefault();
		}
		return new PackageDefault();
	}
	public IPackageGhost createPackageGhost(final char[] aName) {
		return new PackageGhost(aName);
	}
	public IParameter createParameter(
		final IEntity aType,
		final char[] aName,
		final int aCardinality) {

		return new Parameter(aType, aName, aCardinality);
	}
	public IParameter createParameter(
		final IEntity aType,
		final int aCardinality) {

		return new Parameter(aType, aCardinality);
	}
	public IPrimitiveEntity createPrimitiveEntity(
		final char[] aPrimitiveEntityName) {

		//if (Util.isPrimtiveType(aPrimitiveEntityName)) {
		IPrimitiveEntity primitiveEntity =
			(IPrimitiveEntity) Factory.PrimitiveEntities
				.get(aPrimitiveEntityName);
		if (primitiveEntity == null) {
			primitiveEntity = new PrimitiveEntity(aPrimitiveEntityName);
			Factory.PrimitiveEntities
				.put(aPrimitiveEntityName, primitiveEntity);
		}
		return primitiveEntity;
		/*}
		else {
			throw new ModelDeclarationException(
				"Cannot create a primitive entity from a non-primtive name!");
		}*/
	}
	public ISetter createSetter(final char[] anID, final char[] aName) {
		final ISetter setter = new Setter(anID);
		setter.setName(aName);

		return setter;
	}
	public ISetter createSetter(final IMethod aMethod) {
		return new Setter(aMethod);
	}
	//	public IParameter createParameter(
	//		final int aPosition,
	//		final char[] aName,
	//		final char[] aType)
	//		 {
	//
	//		return new Parameter(aPosition, aName, aType);
	//	}
	public IUseRelationship createUseRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		return new UseRelationship(anID, aTargetEntity, aCardinality);
	}
	protected IWalker getEventGenerator() {
		return EventGenerator.getInstance();
	}
}
