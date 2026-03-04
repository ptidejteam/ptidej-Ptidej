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
package padl.kernel;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2003/12/05
 */
public interface IFactory {
	IAggregation createAggregationRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	IAssociation createAssociationRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	IClass createClass(final char[] anID, final char[] aName);
	ICodeLevelModel createCodeLevelModel(final char[] aName);
	ICodeLevelModel createCodeLevelModel(final String aName);
	IComposition createCompositionRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	IComposition createCompositionRelationship(final IAssociation anAssociation);
	IConstructor createConstructor(final char[] anID, final char[] aName);
	IContainerAggregation createContainerAggregationRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	IContainerComposition createContainerCompositionRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	IContainerComposition createContainerCompositionRelationship(
		final IAssociation anAssociation);
	ICreation createCreationRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
	//	IDelegatingMethod createDelegatingMethod(
	//		final char[] aName,
	//		final IAssociation aTargetAssociation) ;
	IDelegatingMethod createDelegatingMethod(
		final char[] aName,
		final IAssociation aTargetAssociation,
		final IMethod aSupportMethod);
	IField createField(
		final char[] anID,
		final char[] aName,
		final char[] aType,
		final int aCardinality);
	IFieldAccess createFieldAccess(
		final int cardinality,
		final int visibility,
		final IField field,
		final IFirstClassEntity entityDeclaringField);
	IGetter createGetter(final char[] anID, final char[] aName);
	IGetter createGetter(final IMethod aMethod);
	IGhost createGhost(final char[] anID, final char[] aName);
	public IFirstClassEntity createHierarchyRoot();
	IIdiomLevelModel createIdiomLevelModel(final char[] aName);
	IInterface createInterface(final char[] anID, final char[] aName);
	IMemberClass createMemberClass(final char[] anID, final char[] aName);
	IMemberGhost createMemberGhost(final char[] anID, final char[] aName);
	IMemberInterface createMemberInterface(final char[] anID, final char[] aName);
	IMethod createMethod(final char[] anID, final char[] aName);
	IMethodInvocation createMethodInvocation(
		final int type,
		final int cardinality,
		final int visibility,
		final IFirstClassEntity targetEntity);
	IMethodInvocation createMethodInvocation(
		final int type,
		final int cardinality,
		final int visibility,
		final IFirstClassEntity targetEntity,
		final IFirstClassEntity entityDeclaringField);
	IPackage createPackage(final char[] aName);
	IPackageDefault createPackageDefault();
	IPackageGhost createPackageGhost(final char[] aName);
	IParameter createParameter(
		final IEntity aType,
		final char[] aName,
		final int aCardinality);
	IParameter createParameter(final IEntity aType, final int aCardinality);
	IParameter createParameter(
		final IEntity aType,
		final char [] aName,
		final int aCardinality,
		final int aDimension);
	IPrimitiveEntity createPrimitiveEntity(final char[] aPrimitiveEntityName);
	ISetter createSetter(final char[] anID, final char[] aName);
	ISetter createSetter(final IMethod aMethod);
	IUseRelationship createUseRelationship(
		final char[] aName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality);
}
