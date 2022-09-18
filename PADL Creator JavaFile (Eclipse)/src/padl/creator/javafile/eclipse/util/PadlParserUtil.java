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
package padl.creator.javafile.eclipse.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import padl.kernel.Constants;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IEntity;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;

public class PadlParserUtil {

	private static final String DEFAULT_PACKAGE_NAME_STRING =
		"(Default Package)";
	private static final char[] DEFAULT_PACKAGE_NAME =
		PadlParserUtil.DEFAULT_PACKAGE_NAME_STRING.toCharArray();

	/**
	 * 
	 */
	public static char[] computeMethodNodeSignature(
		final MethodDeclaration node,
		final ICodeLevelModel model,
		final IPackage packag) {
		final String name = node.getName().toString();
		String returnTypeName = null;
		final int modifier = node.getModifiers();
		final List<IParameter> listOfParameters =
			PadlParserUtil
				.getListOfParameters(node.parameters(), packag, model);
		// compute method's signature
		if (!node.isConstructor()) {
			if (node.getReturnType2() != null) {
				final ITypeBinding typeBinding =
					node.getReturnType2().resolveBinding();
				if (typeBinding != null) {
					returnTypeName =
						PadlParserUtil.getTypeName(typeBinding, false);
				}
			}
		}

		char[] operationID = null;
		if (node.isConstructor()) {
			operationID =
				PadlParserUtil.computeSignature(
					"<init>",
					listOfParameters,
					returnTypeName,
					modifier);
		}
		else {
			operationID =
				PadlParserUtil.computeSignature(
					name,
					listOfParameters,
					returnTypeName,
					modifier);
		}

		return operationID;

	}

	/**
	 * Compute method or constructor's signature
	 * 
	 * @param methodName
	 * @param parametersList
	 * @param returnType
	 * @param modifier
	 * @return
	 */
	public static char[] computeSignature(
		final String methodName,
		final List<IParameter> parametersList,
		final String returnType,
		final int modifier) {
		final StringBuffer methodSignature = new StringBuffer();

		// signature= methodName(param1, param2,...,paramn)
		methodSignature.append(methodName);
		methodSignature.append("(");

		Iterator<IParameter> iter;
		if (!parametersList.isEmpty()) {
			iter = parametersList.iterator();
			IParameter parameter = iter.next();
			methodSignature.append(parameter.getDisplayTypeName());
			// to take into account multiple dimensions arrays as I saw in padl
			// .class
			int arrayDim = Integer.parseInt(parameter.getComment());
			for (int i = 1; i < arrayDim; i++) {
				methodSignature.append("[]");
			}
			// clear this field
			parameter.setComment("");
			while (iter.hasNext()) {
				methodSignature.append(", ");
				parameter = iter.next();
				methodSignature.append(parameter.getDisplayTypeName());
				arrayDim = Integer.parseInt(parameter.getComment());
				for (int i = 1; i < arrayDim; i++) {
					methodSignature.append("[]");
				}
				parameter.setComment("");

			}
		}
		methodSignature.append(")");

		return methodSignature.toString().toCharArray();
	}

	/**
	 * Compute a simple name (the term after the last .) from a qualified name
	 * 
	 * @param aFullyQualifiedName
	 * @return
	 */
	public static char[] computeSimpleName(final char[] aFullyQualifiedName) {
		//	final String fullyQualifiedName =
		//		ArrayUtils.toString(aFullyQualifiedName);
		final int index = ArrayUtils.lastIndexOf(aFullyQualifiedName, '.');

		final char[] name =
			ArrayUtils.subarray(
				aFullyQualifiedName,
				index + 1,
				aFullyQualifiedName.length);

		return name;
	}

	/**
	 * Create a ghost (this can lead the creation of ghost packages)
	 * 
	 * @param anAbstractLevelModel
	 * @param anEntityName
	 * @return
	 */
	public static IFirstClassEntity createGhost(
		final IAbstractLevelModel anAbstractLevelModel,
		final char[] aFullyQualifiedName) {

		final IPackage enclosingPackage =
			PadlParserUtil.searchForPackage(
				anAbstractLevelModel,
				aFullyQualifiedName,
				new PackageCreator() {
					public IPackage create(final char[] aName) {
						return Factory.getInstance().createPackageGhost(aName);
					}
				});

		final IFirstClassEntity firstClassEntity;
		if (enclosingPackage.doesContainConstituentWithID(aFullyQualifiedName)) {

			firstClassEntity =
				(IFirstClassEntity) enclosingPackage
					.getConstituentFromID(aFullyQualifiedName);
		}
		else {
			firstClassEntity =
				anAbstractLevelModel.getFactory().createGhost(
					aFullyQualifiedName,
					PadlParserUtil.computeSimpleName(aFullyQualifiedName));
			enclosingPackage.addConstituent(firstClassEntity);
		}
		return firstClassEntity;
	}

	/**
	 * Create a top level package
	 * 
	 * @param aPackageName
	 * @param aCodeLevelModel
	 * @return
	 */
	private static IPackage createPackage(
		final char[] aPackageName,
		final ICodeLevelModel aCodeLevelModel) {

		IPackage packaje =
			(IPackage) aCodeLevelModel.getConstituentFromName(aPackageName);

		if (packaje == null) {
			packaje = aCodeLevelModel.getFactory().createPackage(aPackageName);
			aCodeLevelModel.addConstituent(packaje);
		}

		return packaje;
	}

	/**
	 * Create a sub package
	 * 
	 * @param aPackageName
	 * @param aPackage
	 * @param aCodeLevelModel
	 * @return
	 */
	private static IPackage createPackage(
		final char[] aPackageName,
		final IPackage aPackage,
		final ICodeLevelModel aCodeLevelModel) {

		// Fix for eclipse_12-15-2009 (fixed)
		// This is for the case where the package contains another constituent
		// (class or interface) with the same name
		IConstituent packaje = aPackage.getConstituentFromName(aPackageName);

		if (packaje == null || !(packaje instanceof IPackage)) {
			packaje = aCodeLevelModel.getFactory().createPackage(aPackageName);
			aPackage.addConstituent((IPackage) packaje);
		}

		return (IPackage) packaje;

	}

	/**
	 * extract the name of a package via the name of an entity
	 * 
	 * @param aJVMClassName
	 * @return
	 */
	public static char[] extractPackageName(final char[] aJVMClassName) {
		if (ArrayUtils.indexOf(aJVMClassName, '.') == -1) {
			return PadlParserUtil.DEFAULT_PACKAGE_NAME;
		}
		else {
			return ArrayUtils.subarray(
				aJVMClassName,
				0,
				ArrayUtils.lastIndexOf(aJVMClassName, '.'));
		}
	}

	/**
	 * Return the dimension of a type
	 * 
	 * @param aTypeBinding
	 * @return
	 */
	public static int getDim(final ITypeBinding aTypeBinding) {
		if (aTypeBinding.isArray()) {
			return Constants.CARDINALITY_MANY;
		}

		ITypeBinding typeBinding = aTypeBinding;
		if (aTypeBinding.isParameterizedType()) {
			typeBinding = aTypeBinding.getErasure();
		}
		if (padl.util.Util.isArrayOrCollection(typeBinding
			.getQualifiedName()
			.toCharArray())) {
			return Constants.CARDINALITY_MANY;
		}
		else {
			return Constants.CARDINALITY_ONE;
		}
	}

	/**
	 * get an existing entity or get or create a ghost
	 * 
	 * @param anEntityTypeBinding
	 * @return
	 */
	public static IEntity getEntityOrGetOrCreateGhost(
		final boolean isDealingWithExtend,
		final ITypeBinding anEntityTypeBinding,
		final ICodeLevelModel aPadlModel,
		final String aCurrentPackageName) {

		ITypeBinding entityBinding = anEntityTypeBinding;

		if (entityBinding.isArray()) {
			entityBinding = entityBinding.getElementType();

		}

		if (entityBinding.isParameterizedType()) {
			entityBinding = entityBinding.getErasure();
		}

		if (entityBinding.isPrimitive()) {
			return aPadlModel.getFactory().createPrimitiveEntity(
				entityBinding.getName().toCharArray());
		}

		if (entityBinding.isFromSource()
				&& !entityBinding.isTypeVariable()// case of templates
				&& (entityBinding.isClass() || entityBinding.isInterface() || entityBinding
					.isMember())) {
			// It is not a ghost and it is a class or an interface or a member
			// class or interface and it is not a isTypeVariable
			// get it from the existing padl model or create a ghost if it is
			// not there
			return PadlParserUtil.getExistingEntity(
				isDealingWithExtend,
				entityBinding,
				aPadlModel,
				aCurrentPackageName);
		}
		else {
			// It is a ghost --- see perhaps there are others considerations
			// to take into account; for the moment generic types (templates)
			// are created as ghosts
			return PadlParserUtil.getOrCreateGhostEntity(
				isDealingWithExtend,
				entityBinding,
				aPadlModel,
				aCurrentPackageName);
		}
	}

	/**
	 * get concrete entity : Only get because, now we create all the entities in
	 * the first parse so no need to create them before discovering them in the
	 * code
	 * 
	 * @param anEntityTypeBinding
	 * @return
	 */
	private static IFirstClassEntity getExistingEntity(
		final boolean isDealingWithExtend,
		final ITypeBinding anEntityTypeBinding,
		final ICodeLevelModel aPadlModel,
		final String aCurrentPackageName) {

		final String entityQualifiedName =
			anEntityTypeBinding.getQualifiedName();

		final String packagePath;

		// if (anEntityTypeBinding.getPackage() == null: Fix for
		// eclipse_12-15-2009 (fixed)
		// it is leaded by typeVariable binding which are now stopped at
		// previous level
		//TODO rename this variable packageId and current package name is package current path (to be checked)
		packagePath = anEntityTypeBinding.getPackage().getName();

		StringTokenizer tokenizer;

		final String searchedEntityID =
			PadlParserUtil.renameWith$(entityQualifiedName, packagePath);

		int nbTokens;

		tokenizer = new StringTokenizer(searchedEntityID, "$");

		nbTokens = tokenizer.countTokens();

		IPackage searchedPackage;

		IFirstClassEntity currentEntity = null;
		String currentEntityID;
		//	String currentEntityName;

		IFirstClassEntity currentMemberEntity;
		String currentMemberEntityID;
		String currentMemberEntityName;

		// Get the package

		if (packagePath.length() == 0) {// default package
			searchedPackage =
				(IPackage) aPadlModel
					.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
		}
		else {
			searchedPackage =
				PadlParserUtil.getPackage(packagePath, aPadlModel);
		}
		if (searchedPackage == null) {
			// Source class but not existing in the current model
			return PadlParserUtil.getOrCreateGhostEntity(
				isDealingWithExtend,
				anEntityTypeBinding,
				aPadlModel,
				aCurrentPackageName);
		}

		if (nbTokens > 0) {
			// Hierarchy... 1st entity (top entity), 1st token
			currentEntityID = tokenizer.nextToken();
			//	currentEntityName =
			//		currentEntityID.substring(currentEntityID.lastIndexOf('.') + 1);

			// get entity
			currentEntity =
				(IFirstClassEntity) searchedPackage
					.getConstituentFromID(currentEntityID.toCharArray());
			if (currentEntity == null) {
				// Source class but not existing in the current model
				return PadlParserUtil.getOrCreateGhostEntity(
					false,
					anEntityTypeBinding,
					aPadlModel,
					aCurrentPackageName);
			}
			// The members classes which don't exist in their classes are not
			// deal in this part;
			// this kind of ITypeBinding is like not from source

			// Get member entity
			// I assume that all member entities are created because when a
			// class member does not exist, exemple, A exists but
			// A.B does not exist, the system does not recognize A.B as from
			// source so we will go directly in the ghost handler
			// see the code before 04/11 to see the recursive creation of member
			// entities (I delete this part here because no need to create them)
			// If this member is not a class but an enum, we are in the trouble,
			// it will return null so we put a check for that; in this case we
			// will send the request to the ghost handler

			while (tokenizer.hasMoreTokens()) {
				currentMemberEntityName = tokenizer.nextToken();
				currentMemberEntityID =
					currentEntity.getDisplayID() + '$'
							+ currentMemberEntityName;

				currentMemberEntity =
					(IFirstClassEntity) currentEntity
						.getConstituentFromID(currentMemberEntityID
							.toCharArray());

				currentEntity = currentMemberEntity;
				// case of subclasses declared in enum or in a class not visited
				// perhaps because another class same id has been parsed first
				// This is when we search parameters types
				// we will create in this case a ghost

				if (currentEntity == null) {
					return PadlParserUtil.getOrCreateGhostEntity(
						isDealingWithExtend,
						anEntityTypeBinding,
						aPadlModel,
						aCurrentPackageName);
				}

			}
			// The last entity got in the padl model is which
			// is searched for
		}

		return currentEntity;
	}

	public static List<IParameter> getListOfParameters(
		final List<?> aListOfVars,
		final IPackage packag,
		final ICodeLevelModel model) {
		// get the list of parameters

		final List<IParameter> listOfParameters = new ArrayList<IParameter>();
		final Iterator<?> iterator = aListOfVars.iterator();
		while (iterator.hasNext()) {
			final SingleVariableDeclaration var =
				(SingleVariableDeclaration) iterator.next();
			if (var.resolveBinding() != null) {
				final ITypeBinding type = var.resolveBinding().getType();
				final IEntity entity =
					PadlParserUtil.getEntityOrGetOrCreateGhost(
						false,
						type,
						model,
						packag.getDisplayPath());

				// Yann: May be null when parsing Foutse's Eclipse data!
				// TODO: Understand why it could be null...
				if (entity != null) {
					// Yann 2015/04/15: Dimensions!
					// I don't forget to add +1 because
					//	int 	has for cardinality 1
					//	int[]	has for cardinality 2
					//	int[][]	has for cardniality 3
					//	...
					final int dim = PadlParserUtil.getDim(type) + 1;
					final IParameter parameter =
						model.getFactory().createParameter(
							entity,
							var.getName().toString().toCharArray(),
							dim);

					parameter.setVisibility(var.getModifiers());
					// I will use the field comment of param to register the
					// dimension of this param if it is an array
					// After using this information during method signature
					// computation, I will delete it
					// This for having the same information with padl .class
					// Aminata 05/05/11
					parameter.setComment(Integer.toString(dim));
					listOfParameters.add(parameter);
				}
			}
		}
		return listOfParameters;
	}

	/**
	 * Get or create a ghost entity
	 * 
	 * @param isDealingWithExtend
	 * @param anEntityTypeBinding
	 * @param aPadlModel
	 * @param aCurrentPackagePath
	 * @return
	 */
	private static IFirstClassEntity getOrCreateGhostEntity(
		final boolean isDealingWithExtend,
		final ITypeBinding anEntityTypeBinding,
		final ICodeLevelModel aPadlModel,
		final String aCurrentPackagePath) {

		final StringBuffer tmpStringBuffer = new StringBuffer();
		String ghostName = null;
		String ghostPackagePath = null;
		String fullyQualifiedName = null;
		final String entityBindingKey = anEntityTypeBinding.getKey();
		final String prefix = "Recovered#currentType";

		if (entityBindingKey.startsWith(prefix)) {
			// ghosts in existing entities
			tmpStringBuffer.append(entityBindingKey.substring(prefix.length()));
			tmpStringBuffer.deleteCharAt(tmpStringBuffer.length() - 1);

			// Yann 2015/03/16: Symbols collision with IConstants!
			// I make sure that the name of the entity does not contain
			// any symbol used in the models, see IConstants, because
			// the entityBindingKey will be of the form:
			//	org.hibernate.envers.entities.mapper.relation.lazy.initializor.Initializor<List<U>>0<Ljava/util/List<Lorg/hibernate/envers/entities/mapper/relation/lazy/proxy/C:\Data\Java Programs\hibernate-orm-3.6.4.Final\hibernate-envers\src\main\java\org\hibernate\envers\entities\mapper\relation\lazy\proxy\ListProxy~ListProxy;:TU;>;>
			if (entityBindingKey.indexOf('/') > -1
					|| entityBindingKey.indexOf('\\') > -1) {

				ghostName =
					tmpStringBuffer.substring(0, tmpStringBuffer.indexOf("0"));
			}
			else {
				ghostName = tmpStringBuffer.toString();
			}
			ghostPackagePath = anEntityTypeBinding.getPackage().getName();
		}
		else if (isDealingWithExtend
				&& entityBindingKey.startsWith("Recovered#typeBinding")) {

			ghostName = anEntityTypeBinding.getQualifiedName();
			ghostPackagePath = anEntityTypeBinding.getPackage().getName();
		}
		else {
			// Pure ghosts
			// Fix for eclipse_12-15-2009 (fixed) case of generic types
			// (templates) (fixed)
			final String[] packageComponents;
			if (anEntityTypeBinding.getPackage() == null) {// case of templates
				packageComponents = new String[0];
				// should be deleted (only for debug purpose)
				/*
				 * Output .getInstance() .normalOutput() .println(
				 * "PadlParserUtil in getOrCreateGhostEntity anEntityTypeBinding.getPackage() == null "
				 * + anEntityTypeBinding .getQualifiedName());
				 */
			}
			else {
				packageComponents =
					anEntityTypeBinding.getPackage().getNameComponents();
			}

			final int numberOfNames = packageComponents.length;

			char firstLetter;
			int i = 0;
			boolean checkPackage = true;

			if (numberOfNames > 0) {
				firstLetter = packageComponents[0].toCharArray()[0];
				if (Character.isLowerCase(firstLetter)) {
					tmpStringBuffer.append(packageComponents[0]);
					i++;
				}
				else {
					checkPackage = false;
				}

				while (i < numberOfNames && checkPackage) {
					firstLetter = packageComponents[i].toCharArray()[0];
					if (Character.isLowerCase(firstLetter)) {
						tmpStringBuffer.append('.');
						tmpStringBuffer.append(packageComponents[i]);
						i++;
					}
					else {
						checkPackage = false;
					}
				}
			}

			ghostPackagePath = tmpStringBuffer.toString();
			// why? aCurrentPackagePath=display path, is it more than the
			// package???
			String packagePath = aCurrentPackagePath;
			if (aCurrentPackagePath.length() > 2) {
				/*
				 * Output .getInstance() .normalOutput() .println(
				 * "PadlParserUtil in getOrCreateGhostEntity aCurrentPackagePath.length() > 2 "
				 * + aCurrentPackagePath.length());
				 */
				packagePath =
					aCurrentPackagePath.substring(
						aCurrentPackagePath.indexOf('|') + 1).replace("|", ".");
			}

			// if package ghost is the same with the current package and this
			// ghost is not from source
			// this means generally that there is a probleme of resolving so we
			// put this ghost in a specific package
			// also when this ghost is a generic type, we do the same
			// These conditions are not always true but permit to handle a lot
			// of cases
			if ((ghostPackagePath.equals(packagePath) || ghostPackagePath
				.length() == 0
					&& ArrayUtils.isEquals(
						packagePath.toCharArray(),
						Constants.DEFAULT_PACKAGE_ID))
					&& !anEntityTypeBinding.isFromSource()
					|| ghostPackagePath.length() == 0
					&& anEntityTypeBinding.isFromSource()
					&& anEntityTypeBinding.isTypeVariable()) {
				ghostPackagePath = "unknown.ghost.packag";
			}

			tmpStringBuffer.setLength(0);

			while (i < numberOfNames) {
				tmpStringBuffer.append(packageComponents[i]);
				tmpStringBuffer.append('.');

				i++;
			}

			tmpStringBuffer.append(anEntityTypeBinding.getName());
			ghostName = tmpStringBuffer.toString();

		}

		tmpStringBuffer.setLength(0);
		tmpStringBuffer.append(ghostPackagePath);
		if (tmpStringBuffer.length() != 0) {
			tmpStringBuffer.append('.');
		}
		tmpStringBuffer.append(ghostName);
		fullyQualifiedName = tmpStringBuffer.toString();

		final int indexOfDot = ghostName.lastIndexOf('.');

		if (indexOfDot < 0) {
			// Case of a ghost
			IFirstClassEntity ghost =
				aPadlModel.getTopLevelEntityFromID(fullyQualifiedName
					.toCharArray());
			if (ghost == null) {
				ghost =
					PadlParserUtil.createGhost(
						aPadlModel,
						fullyQualifiedName.toCharArray());
			}
			return ghost;
		}
		else {
			// case of a ghost member
			// get or create a ghost based on the package name and the ghostName
			final String searchedGhostID =
				PadlParserUtil
					.renameWith$(fullyQualifiedName, ghostPackagePath);

			StringTokenizer tokenizer;

			//	int nbTokens = tokenizer.countTokens(); // number of entities in the
			// hierarchy=class>memberClass1>MemberClass2

			tokenizer = new StringTokenizer(searchedGhostID, "$");

			IPackage searchedPackage = null;

			IFirstClassEntity currentEntity = null;
			final String currentEntityID = tokenizer.nextToken();

			searchedPackage =
				PadlParserUtil.getPackage(ghostPackagePath, aPadlModel);

			if (searchedPackage != null) {
				currentEntity =
					aPadlModel.getTopLevelEntityFromID(currentEntityID
						.toCharArray());

				if (currentEntity == null) {
					currentEntity =
						PadlParserUtil.createGhost(
							aPadlModel,
							currentEntityID.toCharArray());
				}
			}
			else {
				currentEntity =
					PadlParserUtil.createGhost(
						aPadlModel,
						currentEntityID.toCharArray());
			}

			// create the member entities
			IFirstClassEntity currentMemberEntity;
			String currentMemberEntityID;
			String currentMemberEntityName;

			while (tokenizer.hasMoreTokens()) {
				currentMemberEntityName = tokenizer.nextToken();
				currentMemberEntityID =
					currentEntity.getDisplayID() + '$'
							+ currentMemberEntityName;

				currentMemberEntity =
					(IFirstClassEntity) currentEntity
						.getConstituentFromID(currentMemberEntityID
							.toCharArray());

				if (currentMemberEntity == null) {
					currentMemberEntity =
						aPadlModel.getFactory().createMemberGhost(
							currentMemberEntityID.toCharArray(),
							currentMemberEntityName.toCharArray());
					currentEntity
						.addConstituent((IConstituentOfEntity) currentMemberEntity);
				}
				currentEntity = currentMemberEntity;
			}
			// The last entity created or got in the padl model is which
			// is searched for
			return currentEntity;
		}
	}

	/**
	 * Create possibly recursively packages
	 * 
	 * @param aPackageName
	 * @param aCodeLevelModel
	 * @return
	 */
	public static IPackage getOrCreatePackage(
		final char[] aPackageName,
		final ICodeLevelModel aCodeLevelModel) {

		IPackage currentPackage = null;
		int startIndexInclusive = 0;
		do {
			int endIndexExclusive =
				ArrayUtils.indexOf(aPackageName, '.', startIndexInclusive);
			if (endIndexExclusive == -1) {
				endIndexExclusive = aPackageName.length;
			}
			final char[] tempName =
				ArrayUtils.subarray(
					aPackageName,
					startIndexInclusive,
					endIndexExclusive);
			if (currentPackage == null) {
				currentPackage =
					PadlParserUtil.createPackage(tempName, aCodeLevelModel);
			}
			else {
				currentPackage =
					PadlParserUtil.createPackage(
						tempName,
						currentPackage,
						aCodeLevelModel);
			}
			startIndexInclusive = endIndexExclusive + 1;
		}
		while (startIndexInclusive < aPackageName.length);

		return currentPackage;
	}

	/**
	 * return the package from its path
	 * 
	 * @param aPackagePath
	 * @param aModel
	 * @return
	 */
	public static IPackage getPackage(
		final String aPackagePath,
		final ICodeLevelModel aModel) {

		if (aPackagePath.length() == 0) {
			// default package
			return (IPackage) aModel
				.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);

		}

		final StringTokenizer tokenizer =
			new StringTokenizer(aPackagePath, ".");

		IPackage currentPackage =
			(IPackage) aModel.getConstituentFromID(tokenizer.nextToken());
		if (currentPackage != null) {

			while (tokenizer.hasMoreTokens()) {
				final IPackage packaje =
					(IPackage) currentPackage.getConstituentFromID(tokenizer
						.nextToken());
				if (packaje == null) {
					return packaje;
				}
				else {
					currentPackage = packaje;
				}
			}

		}
		return currentPackage;
	}

	/**
	 * return the name of a type
	 * 
	 * @param type
	 * @return
	 */
	public static String getTypeName(
		final ITypeBinding aTypeBinding,
		final boolean isField) {

		String typeName = null;
		ITypeBinding typeBinding = aTypeBinding;

		if (aTypeBinding.isArray()) {

			typeBinding = typeBinding.getElementType();

		}
		if (typeBinding.isParameterizedType()) {
			typeBinding = typeBinding.getErasure();
		}

		if (aTypeBinding.isPrimitive()) {
			typeName = aTypeBinding.getName();

		}
		else if (typeBinding.isFromSource() && !typeBinding.isTypeVariable()) {
			// previous condition - Fix for Eclipse JDT v3.4(
			// case of templates) (fixed)
			if (typeBinding.getPackage() != null) {
				typeName =
					PadlParserUtil.renameWith$(
						typeBinding.getQualifiedName(),
						typeBinding.getPackage().getName());
			}
			else {
				typeName = typeBinding.getQualifiedName();
			}
		}
		else {
			typeName = typeBinding.getQualifiedName();
			// here, it can be wrong if the resolving is not good, and it can
			// be with the current package name!!!
		}

		if (!isField) {
			for (int i = 0; i < aTypeBinding.getDimensions(); i++) {

				typeName = typeName.concat("[]");
			}
		}
		return typeName;
	}

	/**
	 * Rewrite the IDs of members by replacing . by $
	 * 
	 * @param entityQualifiedName
	 * @param packageName
	 * @return
	 */
	public static String renameWith$(
		final String entityQualifiedName,
		final String packageName) {

		// default package
		String partToRename = entityQualifiedName;

		final StringBuffer tmpNewName = new StringBuffer();

		if (entityQualifiedName.length() <= packageName.length()) {
			// which case?
			/*
			 * Output .getInstance() .normalOutput() .println(
			 * "PadlParserUtil in renameWith$ entityQualifiedName.length() <= packageName.length() "
			 * + entityQualifiedName + " " + packageName.length());
			 */
		}
		// not default package
		if (packageName.length() != 0
		// TODO: Fix for eclipse_12-15-2009
				&& entityQualifiedName.length() > packageName.length()) {

			partToRename =
				entityQualifiedName.substring(packageName.length() + 1);
			tmpNewName.append(packageName);
			tmpNewName.append('.');
		}
		// Replace . by $ in the string part to rename

		partToRename = partToRename.replace('.', '$');

		tmpNewName.append(partToRename);

		return tmpNewName.toString();
	}

	/**
	 * 
	 * @param anAbstractLevelModel
	 * @param anEntityName
	 * @param aPackageCreator
	 * @return
	 */

	public static IPackage searchForPackage(
		final IAbstractLevelModel anAbstractLevelModel,
		final char[] anEntityName,
		final PackageCreator aPackageCreator) {

		// Yann 2008/11/04: Packages can be ghost, too!
		// Now that I handle packages, I must also create
		// appropriate ghost packages...
		final char[] packageName =
			PadlParserUtil.extractPackageName(anEntityName);
		final StringTokenizer packageNameTokenizer =
			new StringTokenizer(String.valueOf(packageName), ".");
		IPackage enclosingPackage = null;

		if (!packageNameTokenizer.hasMoreTokens()) {
			enclosingPackage =
				aPackageCreator.create(PadlParserUtil.DEFAULT_PACKAGE_NAME);
		}

		while (packageNameTokenizer.hasMoreTokens()) {
			final String packagePartDisplayName =
				packageNameTokenizer.nextToken();
			final char[] packagePartName = packagePartDisplayName.toCharArray();
			if (enclosingPackage == null) {
				enclosingPackage =
					(IPackage) anAbstractLevelModel
						.getConstituentFromID(packagePartName);

				if (enclosingPackage == null) {
					enclosingPackage = aPackageCreator.create(packagePartName);
					anAbstractLevelModel.addConstituent(enclosingPackage);
				}

			}

			else {
				IPackage newEnclosingPackage =
					(IPackage) enclosingPackage
						.getConstituentFromID(packagePartName);

				if (newEnclosingPackage == null) {
					newEnclosingPackage =
						aPackageCreator.create(packagePartName);
					enclosingPackage.addConstituent(newEnclosingPackage);
				}
				enclosingPackage = newEnclosingPackage;
			}
		}
		return enclosingPackage;
	}
}
