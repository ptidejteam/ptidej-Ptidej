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
package padl.creator.cppfile.eclipse.plugin.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
import org.apache.commons.lang.ArrayUtils;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IBasicType;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IProblemBinding;
import org.eclipse.cdt.core.dom.ast.IProblemType;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import padl.cpp.kernel.ICPPFactoryEclipse;
import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPrimitiveEntity;
import padl.path.Finder;
import padl.path.FormatException;
import padl.path.IConstants;
import util.io.ProxyConsole;

public abstract class SearchHelper {
	private static IContainer findContainerOrCreateGhostInContainer(
		final IContainer aContainer,
		final char[] anID,
		final char[] aName,
		boolean isGlobalFunction,
		boolean isLastPartOfFullyQualifiedName) {

		// Yann 2013/09/28: Name vs. ID!
		// Member entities have for name the simple name of the entity
		// but for ID the ID of their container and "$" and their ID. 
		char[] memberID = ((IConstituent) aContainer).getID();
		memberID = ArrayUtils.add(memberID, IConstants.MEMBER_ENTITY_SYMBOL);
		memberID = ArrayUtils.addAll(memberID, aName);

		IContainer container =
			(IContainer) aContainer.getConstituentFromID(anID);
		if (container == null) {
			container = (IContainer) aContainer.getConstituentFromID(memberID);
		}
		if (container == null) {
			if (isLastPartOfFullyQualifiedName) {
				if (aContainer instanceof IPackage) {
					if (isGlobalFunction) {
						// Yann 2013/07/16: Interesting!
						// Here, I must create a "ghost" global function,
						// I should probably add this type to the meta-model.
						container =
							((ICPPFactoryEclipse) CPPFactoryEclipse
								.getInstance()).createGlobalFunctionGhost(
								anID,
								aName);
					}
					else {
						container =
							((ICPPFactoryEclipse) CPPFactoryEclipse
								.getInstance()).createGhost(anID, aName);
					}
				}
				else if (aContainer instanceof IFirstClassEntity) {
					if (isGlobalFunction) {
						// Yann 2013/07/16: Interesting!
						// Here, I must create a "ghost" global function,
						// I should probably add this type to the meta-model.
						container =
							((ICPPFactoryEclipse) CPPFactoryEclipse
								.getInstance()).createGlobalFunctionGhost(
								anID,
								aName);
					}
					else {
						container =
							((ICPPFactoryEclipse) CPPFactoryEclipse
								.getInstance()).createMemberGhost(
								memberID,
								aName);
					}
				}
				else {
					// Yes, it is legal in C++ to have structure in methods...
					// Certainly classes too, but at this point I don't want
					// to distinguish one from the others...
					container =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createMemberStructure(memberID);
				}
			}
			else {
				if (aContainer instanceof IPackage) {
					container =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createGhost(anID, aName);
				}
				else if (aContainer instanceof IFirstClassEntity) {
					container =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createMemberGhost(memberID, aName);
				}
			}
			aContainer.addConstituent((IConstituent) container);
		}
		return container;
	}
	static private IContainer findContainerOrCreateGhostInModel(
		final ICodeLevelModel aCodeLevelModel,
		final char[] anID,
		final char[] aName,
		boolean isGlobalFunction,
		boolean isLastPartOfFullyQualifiedName) {

		IContainer container =
			(IContainer) aCodeLevelModel.getConstituentFromID(anID);
		// Yes, it is possible that a namespace as the same
		// name as the entity that we are looking for... but
		// this seems very strange indeed...
		//	|| container instanceof IPackage
		if (container == null) {
			// At this point, we only look into the default package,
			// because, if the entity existed into another package,
			// we would have found it through its CPPClassTyep.
			final IPackageDefault packaje =
				(IPackageDefault) aCodeLevelModel
					.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
			container = (IContainer) packaje.getConstituentFromID(anID);
			// Yes, it is possible that a namespace as the same
			// name as the entity that we are looking for... but
			// this seems very strange indeed...
			//	|| container instanceof IPackage
			if (container == null) {
				container = aCodeLevelModel.getTopLevelEntityFromID(anID);
				if (container == null) {
					if (isLastPartOfFullyQualifiedName) {
						if (isGlobalFunction) {
							// Yann 2013/07/16: Interesting!
							// Here, I must create a "ghost" global function,
							// I should probably add this type to the meta-model.
							container =
								((ICPPFactoryEclipse) CPPFactoryEclipse
									.getInstance()).createGlobalFunctionGhost(
									anID,
									aName);
						}
						else {
							container =
								((ICPPFactoryEclipse) CPPFactoryEclipse
									.getInstance()).createGhost(anID, aName);
						}
					}
					else {
						// Impossible to be sure that we should
						// create a package rather than a ghost...
						//	container =
						//		((ICPPFactoryEclipse) CPPFactoryEclipse
						//			.getInstance()).createPackageGhost(anID);
						container =
							((ICPPFactoryEclipse) CPPFactoryEclipse
								.getInstance()).createGhost(anID, aName);
					}
					packaje.addConstituent((IConstituentOfModel) container);
				}
			}
		}
		return container;
	}
	static IContainer findContainerOrCreateGhostInModelRecursively(
		final ICodeLevelModel aCodeLevelModel,
		final char[][] qualifiedName,
		boolean isGlobalFunction) {

		char[] name = qualifiedName[0];
		char[] id = qualifiedName[0];
		IContainer container =
			SearchHelper.findContainerOrCreateGhostInModel(
				aCodeLevelModel,
				id,
				name,
				isGlobalFunction,
				qualifiedName.length == 1);
		for (int i = 1; i < qualifiedName.length; i++) {
			name = qualifiedName[i];
			id =
				ArrayUtils.addAll(
					ArrayUtils.add(id, Utils.SEPARATOR),
					qualifiedName[i]);
			container =
				SearchHelper.findContainerOrCreateGhostInContainer(
					(IContainer) container,
					id,
					name,
					isGlobalFunction,
					i == qualifiedName.length - 1);
		}
		return container;
	}
	static IEntity getExistingContainerOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Accumulator anAccumulator,
		final IType aType) {

		final IEntity entity;
		if (aType instanceof ICPPClassType) {
			final ICPPClassType classType = (ICPPClassType) aType;
			entity =
				SearchHelper.getExistingFirstClassEntityOrCreateGhost(
					aCodeLevelModel,
					anAccumulator,
					(ICPPClassType) classType);
		}
		else if (aType instanceof IBasicType) {
			final String name = ((IBasicType) aType).toString();
			entity =
				SearchHelper.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					null,
					name,
					false);
		}
		else if (aType instanceof IProblemType) {
			entity =
				SearchHelper.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					null,
					Utils.PROBLEM_TYPE,
					false);
		}
		else {
			entity = null;
		}
		return entity;
	}
	static IEntity getExistingContainerOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers,
		final char[] qualifiedName,
		boolean isGlobalFunction) {

		return SearchHelper.getExistingContainerOrCreateGhost(
			aCodeLevelModel,
			someContainers,
			new char[][] { qualifiedName },
			isGlobalFunction);
	}
	static IEntity getExistingContainerOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers,
		final char[][] qualifiedName,
		boolean isGlobalFunction) {

		return SearchHelper.getExistingContainerOrCreateGhost0(
			aCodeLevelModel,
			someContainers,
			qualifiedName,
			isGlobalFunction);
	}
	static IEntity getExistingContainerOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers,
		final String qualifiedName,
		boolean isGlobalFunction) {

		return SearchHelper.getExistingContainerOrCreateGhost(
			aCodeLevelModel,
			someContainers,
			new char[][] { qualifiedName.toCharArray() },
			isGlobalFunction);
	}
	static IEntity getExistingContainerOrCreateGhost0(
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers,
		final char[][] qualifiedName,
		boolean isGlobalFunction) {

		char[] name = qualifiedName[0];
		if (Utils.isPrimitiveName(name)) {
			final IPrimitiveEntity entity;
			if (!aCodeLevelModel.doesContainConstituentWithName(name)) {
				aCodeLevelModel
					.addConstituent(((ICPPFactoryEclipse) CPPFactoryEclipse
						.getInstance()).createPrimitiveEntity(name));
			}
			entity =
				(IPrimitiveEntity) aCodeLevelModel.getConstituentFromID(name);
			return entity;
		}
		else if (someContainers == null) {
			return (IFirstClassEntity) SearchHelper
				.findContainerOrCreateGhostInModelRecursively(
					aCodeLevelModel,
					qualifiedName,
					isGlobalFunction);
		}
		else {
			final IContainer container = someContainers.peek();
			char[] id = qualifiedName[0];
			IContainer entity =
				(IContainer) container.getConstituentFromName(id);
			if (entity == null) {
				return SearchHelper.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					null,
					qualifiedName,
					isGlobalFunction);
			}
			if (entity instanceof IConstructor) {
				return (IFirstClassEntity) container;
			}
			for (int i = 1; i < qualifiedName.length; i++) {
				name = qualifiedName[i];
				id =
					ArrayUtils.addAll(
						ArrayUtils.add(id, Utils.SEPARATOR),
						qualifiedName[i]);
				entity =
					SearchHelper.findContainerOrCreateGhostInContainer(
						(IContainer) entity,
						id,
						name,
						isGlobalFunction,
						i == qualifiedName.length - 1);
			}
			return (IFirstClassEntity) entity;
		}
	}
	static IFirstClassEntity getExistingFirstClassEntityOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Accumulator anAccumulator,
		final ICPPClassType aClassType) {

		final IFirstClassEntity entity =
			anAccumulator.getFirstClassEntity(aClassType);
		if (entity != null) {
			return entity;
		}

		if (aClassType instanceof IProblemBinding) {
			final IProblemBinding problemBinding = (IProblemBinding) aClassType;
			final IASTNode correspondingNode = problemBinding.getASTNode();
			if (correspondingNode instanceof ICPPASTQualifiedName) {
				final char[][] qualifiedName =
					Utils
						.getQualifiedName((ICPPASTQualifiedName) correspondingNode);
				return (IFirstClassEntity) SearchHelper
					.getExistingContainerOrCreateGhost(
						aCodeLevelModel,
						null,
						qualifiedName,
						false);
			}
			else if (correspondingNode instanceof IASTName) {
				final char[][] qualifiedName =
					Utils.getQualifiedName(((IASTName) correspondingNode)
						.toCharArray());
				return (IFirstClassEntity) SearchHelper
					.getExistingContainerOrCreateGhost(
						aCodeLevelModel,
						null,
						qualifiedName,
						false);
			}
			else {
				throw new RuntimeException(new DOMException(problemBinding));
			}
		}
		else {
			final char[] qualifiedName;
			if (aClassType.getKey() == ICPPASTCompositeTypeSpecifier.k_class) {
				try {
					qualifiedName =
						Utils.getQualifiedName(aClassType
							.getQualifiedNameCharArray());
				}
				catch (final DOMException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					throw new RuntimeException(e);
				}
			}
			else {
				// In case of struct or other "nameless" entities,
				// I create an anonymous name to make sure they
				// are visible.
				qualifiedName =
					Utils.createAnonymousName(GeneratorHelper.UniqueID);
			}
			return (IFirstClassEntity) SearchHelper
				.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					null,
					qualifiedName,
					false);
		}
	}
	static IFirstClassEntity getExistingFirstClassEntityOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final ICPPFunction aFunction) {

		try {
			final char[][] qualifiedNameArray =
				aFunction.getQualifiedNameCharArray();
			return (IFirstClassEntity) SearchHelper
				.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					null,
					qualifiedNameArray,
					true);
		}
		catch (final DOMException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
	}
	static IOperation getExistingOperationOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Accumulator anAccumulator,
		final IASTNode aNode) {

		// I need this method to find an IOperation from some statement
		// or expression... I cannot use someContainers because I don't
		// want to deal with the mess of keeping track of enclosing
		// declarations... C++ what a mess!!!

		final ICPPASTFunctionDefinition functionDefinition =
			Utils.getEnclosingFunction(aNode);
		if (functionDefinition == null) {
			// It is possible that the call is "outside" of any function
			// in the case of the initialisation of a variable as in:
			//	static const SkColor kTitleColor = SkColorSetRGB(6, 45, 117);
			// In that case, do nothing.
			return null;
		}

		final IASTName functionName =
			functionDefinition.getDeclarator().getName();
		final char[] functionNameName =
			Utils.convertSeparators(functionName.toCharArray());
		final IBinding function = functionName.resolveBinding();

		if (function instanceof ICPPFunction) {
			return anAccumulator.getOperation((ICPPFunction) function);
		}
		else if (function instanceof ICPPClassType) {
			// Here, I must manage the case of a constructor:
			// it is possible that the enclosing function is not  
			// an function but a class... in this case, I must 
			// look into for its constructor.
			final String pathOfFunction =
				SearchHelper.getExistingOperationOrCreateGhost(
					aCodeLevelModel,
					null,
					Utils.buildGlobalFunctionID(functionNameName));
			try {
				return (IOperation) Finder
					.find(pathOfFunction, aCodeLevelModel);
			}
			catch (final FormatException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		else if (function instanceof IProblemBinding) {
			// If I cannot find a binding for this function,
			// there is not much that I can do, is there?
			// At this point, I know that the famous
			//	functionNameName
			// represents the name of a function, the caller.
			// I take the qualified name, in case the
			//	functionNameName
			// includes some separator, i.e., is qualified.
			// I can ask the ProblemBinding for candidates if any.
			final IBinding[] candidateBindings =
				((IProblemBinding) function).getCandidateBindings();
			for (int i = 0; i < candidateBindings.length; i++) {
				final IBinding candidateBinding = candidateBindings[i];
				if (candidateBinding instanceof ICPPFunction) {
					return anAccumulator
						.getOperation((ICPPFunction) candidateBinding);
				}
			}

			// Finally, it is not possible to find any binding,
			// I guess based on the name of the function.
			final String pathOfFunction =
				SearchHelper.getExistingOperationOrCreateGhost(
					aCodeLevelModel,
					null,
					Utils.buildGlobalFunctionID(functionNameName));
			try {
				return (IOperation) Finder
					.find(pathOfFunction, aCodeLevelModel);
			}
			catch (final FormatException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		else {
			// TODO Treat other cases, including PDOMCPPField?
		}

		Utils.reportUnknownType(
			SearchHelper.class,
			"operation",
			functionNameName,
			function.getClass());
		return null;
	}
	static String getExistingOperationOrCreateGhost(
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers,
		final char[] aFunctionName) {

		final char[][] declaringEntityName =
			Utils.getQualifiedType(aFunctionName);
		final IContainer declaringEntity;
		final IOperation operation;

		if (declaringEntityName.length == 0) {
			declaringEntity =
				(IContainer) aCodeLevelModel
					.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
		}
		else if (declaringEntityName.length == 1) {
			declaringEntity =
				(IContainer) SearchHelper.findContainerOrCreateGhostInModel(
					aCodeLevelModel,
					declaringEntityName[0],
					declaringEntityName[0],
					true,
					false);
		}
		else {
			declaringEntity =
				(IContainer) SearchHelper.getExistingContainerOrCreateGhost(
					aCodeLevelModel,
					someContainers,
					declaringEntityName,
					false);
		}

		final char[] simpleName = Utils.getSimpleName(aFunctionName);
		final IConstituent constituent =
			declaringEntity.getConstituentFromID(simpleName);
		if (constituent instanceof IOperation) {
			operation = (IOperation) constituent;
		}
		else if (constituent instanceof IFirstClassEntity) {
			// Here, I must manage the case of a constructor:
			// it is possible that the constituent is not an 
			// operation but an entity... in this case, I must 
			// look into it for its constructor.
			final IOperation possibleOperation =
				(IOperation) ((IFirstClassEntity) constituent)
					.getConstituentFromID(aFunctionName);
			if (possibleOperation == null) {
				operation =
					CPPFactoryEclipse.getInstance().createConstructor(
						aFunctionName,
						simpleName);

				// Yann 2014/0417: Anti-patterns!
				// I need to set the lines of code of the methods
				// to allow the identification of some anti-patterns
				// like LongMethod and SpaghettiCode. This seems the
				// best place to do so :-)
				// In this case, I just add a null statement!
				Utils.addStatementsToFunction(operation);

				((IFirstClassEntity) constituent).addConstituent(operation);
			}
			else {
				operation = possibleOperation;
			}
		}
		else {
			// Yann 2014/04/17: Parameter!
			// I don't forget that the ID contains the parameter list...
			// Important if I want to find the corresponding function
			// if it exists. BUT, it is not quite possible to build the
			// parameter because there is no bindings at this point.
			// So, it is rather the getExistingOperationOrCreateGhost()
			// method that must be smart: if it finds the method in the
			// declaring entity, then it should choose it; else if it
			// finds a method with the same name, but different parameters,
			// then it should choose it too, else it should look for
			// for (ghost) global functions.
			final Iterator<?> iterator =
				declaringEntity.getIteratorOnConstituents(IOperation.class);
			while (iterator.hasNext()) {
				final IOperation possibleOperation =
					(IOperation) iterator.next();
				final char[] operationName = possibleOperation.getID();
				final char[] operationNameWithoutParameters =
					ArrayUtils.subarray(
						operationName,
						0,
						ArrayUtils.indexOf(operationName, '('));
				final char[] simpleNameWithoutParameters =
					ArrayUtils.subarray(
						simpleName,
						0,
						ArrayUtils.indexOf(simpleName, '('));

				if (Arrays.equals(
					operationNameWithoutParameters,
					simpleNameWithoutParameters)) {

					return possibleOperation.getDisplayPath();
				}
			}

			if (Arrays.equals(
				((IConstituent) declaringEntity).getName(),
				simpleName)) {

				operation =
					CPPFactoryEclipse.getInstance().createConstructor(
						aFunctionName,
						simpleName);
			}
			else {
				if (declaringEntity instanceof IPackage) {
					// Yann 2015/07/16: Weird...
					// Why does the simple name include parentheses???
					final char[] simpleNameWithoutParameters =
						ArrayUtils.subarray(
							simpleName,
							0,
							ArrayUtils.indexOf(simpleName, '('));
					operation =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createGlobalFunctionGhost(
								aFunctionName,
								simpleNameWithoutParameters);
				}
				else {
					operation =
						CPPFactoryEclipse.getInstance().createMethod(
							simpleName,
							simpleName);
				}
			}

			if (!declaringEntity
				.doesContainConstituentWithID(operation.getID())) {

				// Yann 2014/0417: Anti-patterns!
				// I need to set the lines of code of the methods
				// to allow the identification of some anti-patterns
				// like LongMethod and SpaghettiCode. This seems the
				// best place to do so :-)
				// In this case, I just add a null statement!
				Utils.addStatementsToFunction(operation);

				declaringEntity.addConstituent(operation);
			}
			else {
				// I must return the real constituent in the entity,
				// I cannot use the ones build above because they 
				// don't belong to any model yet.
				return declaringEntity
					.getConstituentFromID(operation.getID())
					.getDisplayPath();
			}
		}
		return operation.getDisplayPath();
	}
}
