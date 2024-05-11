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
import java.util.Set;
import java.util.Stack;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IEnumerator;
import org.eclipse.cdt.core.dom.ast.IProblemBinding;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFieldReference;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTLinkageSpecification;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassTemplate;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPConstructor;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPEnumeration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMember;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethodSpecialization;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPNamespace;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPVariable;
import padl.cpp.kernel.ICPPFactoryEclipse;
import padl.cpp.kernel.IEnum;
import padl.cpp.kernel.IEnumValue;
import padl.cpp.kernel.IGlobalField;
import padl.cpp.kernel.IGlobalFunction;
import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.exception.ModelDeclarationException;
import padl.path.Finder;
import padl.path.FormatException;
import padl.path.IConstants;
import util.io.ProxyConsole;

/**
 * PADL Generator for C++ source code.
 * 
 * @author Seb
 * @author Yann
 */
class GeneratorHelper {
	static int UniqueID;

	private final Set<IASTTranslationUnit> astTranslationUnits;
	private final ICodeLevelModel codeLevelModel;

	GeneratorHelper(
		final Set<IASTTranslationUnit> someASTTranslationUnits,
		final ICodeLevelModel aCodeLevelModel) {

		this.astTranslationUnits = someASTTranslationUnits;
		this.codeLevelModel = aCodeLevelModel;
	}
	private void addEntityToModel(
		final Accumulator anAccumulator,
		final ICPPClassType aCPPEntity,
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers) throws DOMException {

		this.addEntityToModelOrMemberToClass(
			anAccumulator,
			aCPPEntity,
			someContainers);
	}
	private void addEntityToModelOrMemberToClass(
		final Accumulator anAccumulator,
		final ICPPClassType aCPPEntity,
		final Stack<IContainer> someContainers) {

		final IContainer container = someContainers.peek();

		char[] id;
		try {
			id = Utils.getQualifiedName(aCPPEntity.getQualifiedNameCharArray());
		}
		catch (final DOMException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
		char[] name = aCPPEntity.getNameCharArray();
		// It is possible for the name to be empty, 
		// for enumeration for example
		if (id.length == 0 || name.length == 0) {
			// id.length could be 1 in case of union... 
			id = Utils.createAnonymousName(GeneratorHelper.UniqueID++);
			name = id;
		}

		// Yann 2013/09/28: Name vs. ID!
		// Member entities have for name the simple name of the entity
		// but for ID the ID of their container and "$" and their ID.
		char[] memberID = ((IConstituent) container).getID();
		memberID = ArrayUtils.add(memberID, IConstants.MEMBER_ENTITY_SYMBOL);
		memberID = ArrayUtils.addAll(memberID, name);

		// It is possible that the entity already exists in the case of
		// In one file:
		//	class DebuggerWrapper;
		// In another file:
		//	class DebuggerWrapper {
		//		...
		if (container.doesContainConstituentWithID(id)
				|| container.doesContainConstituentWithID(memberID)) {

			return;
		}

		final IFirstClassEntity entity;
		final int type = aCPPEntity.getKey();
		if (type == ICPPASTCompositeTypeSpecifier.k_class) {
			if (container instanceof IFirstClassEntity) {
				if (Utils.isInterface(aCPPEntity)) {
					entity =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createMemberInterface(memberID, name);
				}
				else {
					entity =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createMemberClass(memberID, name);
					entity.setAbstract(Utils.isAbstract(aCPPEntity));
				}
			}
			else {
				if (Utils.isInterface(aCPPEntity)) {
					entity =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createInterface(id, name);
				}
				else {
					entity =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createClass(id, name);
					entity.setAbstract(Utils.isAbstract(aCPPEntity));
					entity.equals(entity);
				}
			}
		}
		else if (type == IASTCompositeTypeSpecifier.k_struct) {
			if (container instanceof IFirstClassEntity) {
				entity = ((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createMemberStructure(memberID);
			}
			else {
				entity = ((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createStructure(id);
			}
		}
		else if (type == IASTCompositeTypeSpecifier.k_union) {
			entity = ((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
				.createUnion(id);
		}
		else {
			entity = null;
			// Should never happen! Would fail fast!
		}

		container.addConstituent(entity);
		anAccumulator.addClassTypes(aCPPEntity, entity);
	}
	private void addEnumerationToModel(
		final ICPPEnumeration aCPPEnumeration,
		final Stack<IContainer> someContainers) throws DOMException {

		final IContainer container = someContainers.peek();

		char[] id =
			Utils.getQualifiedName(aCPPEnumeration.getQualifiedNameCharArray());
		// It is possible for the name to be empty, 
		// for enumeration for example
		if (id.length == 0) {
			id = Utils.createAnonymousName(GeneratorHelper.UniqueID++);
		}

		if (!container.doesContainConstituentWithID(id)) {
			final IEnum enumeration =
				((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createEnum(id);

			for (final IEnumerator enumerator : aCPPEnumeration
				.getEnumerators()) {
				final IEnumValue anEnumValue =
					((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
						.createEnumValue(enumerator.getName().toCharArray());
				enumeration.addConstituent(anEnumValue);
			}

			container.addConstituent(enumeration);
		}
	}
	void addFieldToClass(
		final ICPPVariable aCPPVariable,
		final IFirstClassEntity aFirstClassEntity) {

		final Stack<IContainer> temporaryStack = new Stack<IContainer>();
		temporaryStack.push(aFirstClassEntity);
		this.addVariableToModelOrFieldToClass(aCPPVariable, temporaryStack);
	}
	private void addFunctionToModel(
		final Accumulator anAccumulator,
		final ICPPFunction aCPPFunction,
		final IASTStatement aBodyStatement,
		final ICodeLevelModel aCodeLevelModel,
		final Stack<IContainer> someContainers) throws DOMException {

		this.addFunctionToModelOrMethodToClass(
			anAccumulator,
			aCPPFunction,
			aBodyStatement,
			someContainers);
	}
	private void addFunctionToModelOrMethodToClass(
		final Accumulator anAccumulator,
		final ICPPFunction aCPPFunction,
		final IASTStatement aBodyStatement,
		final Stack<IContainer> someContainers) {

		final IContainer container = someContainers.peek();

		// Yann 2014/04/17: Const-ness
		// It is annoying to manage const-ness so, right now,
		// I remove any trace of it... Could do better?
		// Similarly, I cannot deal with ellipses currently.
		// For some reasons, sometimes CDT adds the string
		// CPPMETHOD at the end of a function signature,
		// when the aCPPFunction is a PDOMCPPMethod.
		final String stringID =
			String.valueOf(Utils.computeSignature(aCPPFunction));
		final char[] id = stringID
			.replaceAll("const ", "")
			.replaceAll(", \\.\\.\\.", "")
			.replaceAll(" CPPMETHOD", "")
			.replaceAll("/", "US")
			.replaceAll("#", "NUM")
			.toCharArray();

		final char[] name;
		try {
			name =
				Utils.getSimpleName(aCPPFunction.getQualifiedNameCharArray());
		}
		catch (final DOMException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}

		// Yann 2013/07/19: Duplication in case of problem
		// It is possible to have duplicate constituents in the model
		// in case of problematic parameter types, for example:
		//	operator <<(? &, const ? &)
		// is the ID of both
		//	operator <<(A &, const B &)
		// and
		//	operator <<(C &, const D &)
		// if A, B, C, and D are all problematic! So, yes, here it is
		// okay to check for duplication because there would be no
		// way to distinguish one from the other, is there?
		// Although I don't want to create twice the "same"
		// operation, I must add it to the accumulator for
		// later proper retrieval of the operation associated
		// with the ICPPFunction.
		if (container.doesContainConstituentWithID(id)) {
			// Ugly duplication with similar code at the
			// end of this method... TODO Remove duplication
			final IOperation padlFunction =
				(IOperation) container.getConstituentFromID(id);
			if (container instanceof IFirstClassEntity) {
				anAccumulator.addFunction(
					aCPPFunction,
					(IFirstClassEntity) container,
					padlFunction);
			}
			else {
				anAccumulator.addFunction(aCPPFunction, null, padlFunction);
			}
			return;
		}

		final char[] returnTypeName = Utils
			.getInterestingTypeName(aCPPFunction.getType().getReturnType())
			.toCharArray();

		final IOperation padlFunction;
		// Yann 2013/06/27: Method vs. Function
		// Right now, I only build methods (constructor, destructor, and method)
		// when the enclosing first-class entity exists, else I built a function.
		if (aCPPFunction instanceof ICPPConstructor) {
			padlFunction =
				((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createConstructor(id, name);
			Utils.setVisibility(padlFunction, (ICPPMember) aCPPFunction);
		}
		else if (aCPPFunction instanceof ICPPMethod
				&& ((ICPPMethod) aCPPFunction).isDestructor()) {

			padlFunction =
				((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createDestructor(id, name);
			Utils.setVisibility(padlFunction, (ICPPMember) aCPPFunction);
		}
		else if (aCPPFunction instanceof ICPPMethod) {
			padlFunction =
				((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createMethod(id, name);
			// Create the return type if it does not exist.
			final IEntity returnTypeEntity =
				SearchHelper.getExistingContainerOrCreateGhost(
					this.codeLevelModel,
					someContainers,
					returnTypeName,
					false);
			((IMethod) padlFunction).setReturnType(returnTypeEntity.getName());
			Utils.setVisibility(padlFunction, (ICPPMember) aCPPFunction);
		}
		else if (aCPPFunction instanceof ICPPFunction) {
			// Global function
			padlFunction =
				((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
					.createGlobalFunction(id, name);
			((IGlobalFunction) padlFunction).setReturnType(returnTypeName);
		}
		else {
			padlFunction = null;
			Utils.reportUnknownType(
				GeneratorHelper.class,
				"operation",
				id,
				aCPPFunction.getClass());
		}

		padlFunction.setStatic(aCPPFunction.isStatic());

		if (container instanceof IFirstClassEntity) {
			anAccumulator.addFunction(
				aCPPFunction,
				(IFirstClassEntity) container,
				padlFunction);
		}
		else {
			anAccumulator.addFunction(aCPPFunction, null, padlFunction);
		}

		// Yann 2014/0417: Anti-patterns!
		// I need to set the lines of code of the methods
		// to allow the identification of some anti-patterns
		// like LongMethod and SpaghettiCode. This seems the
		// best place to do so :-)
		Utils.addStatementsToFunction(aBodyStatement, padlFunction);

		container.addConstituent(padlFunction);
	}
	void addMemberToClass(
		final Accumulator anAccumulator,
		final ICPPClassType aCPPClassType,
		final IFirstClassEntity aFirstClassEntity) {

		final Stack<IContainer> temporaryStack = new Stack<IContainer>();
		temporaryStack.push(aFirstClassEntity);
		this.addEntityToModelOrMemberToClass(
			anAccumulator,
			aCPPClassType,
			temporaryStack);
	}
	void addMethodToClass(
		final Accumulator anAccumulator,
		final ICPPMethod aMethod,
		final IASTStatement aBodyStatement,
		final IFirstClassEntity aFirstClassEntity) {

		final Stack<IContainer> temporaryStack = new Stack<IContainer>();
		temporaryStack.push(aFirstClassEntity);
		this.addFunctionToModelOrMethodToClass(
			anAccumulator,
			aMethod,
			aBodyStatement,
			temporaryStack);
	}
	void addParameterToOperation(
		final Accumulator anAccumulator,
		final ICPPParameter aCPPParameter,
		final IOperation aPADLOperation) {

		try {
			final IType parameterType = aCPPParameter.getType();
			final IType realParameterType =
				Utils.getInterestingType(parameterType);
			final IEntity parameterEntity =
				SearchHelper.getExistingContainerOrCreateGhost(
					this.codeLevelModel,
					anAccumulator,
					realParameterType);

			if (parameterEntity != null) {
				final String parameterTypeName =
					parameterType.toString().replaceAll("const ", "");
				final char[] parameterName = aCPPParameter.getNameCharArray();
				final int cardinality = Utils.getCardinality(aCPPParameter);

				final IParameter padlParameter;
				final int indexOfSpace;
				if ((indexOfSpace = parameterTypeName.indexOf(' ')) > -1) {
					final char[] parameterQualification =
						parameterTypeName.substring(indexOfSpace).toCharArray();
					padlParameter =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createParameter(
								parameterEntity,
								parameterName,
								parameterQualification,
								cardinality);
				}
				else {
					padlParameter =
						CPPFactoryEclipse.getInstance().createParameter(
							parameterEntity,
							parameterName,
							cardinality);
				}

				aPADLOperation.addConstituent(padlParameter);
			}
			else {
				// Do nothing?
			}
		}
		catch (final ModelDeclarationException e) {
			// If the type of the parameter cannot be found 
			// and I cannot get its qualified name, then 
			// there is nothing that I can do to add things
			// to its methods.
		}
		catch (final NullPointerException e) {
			// If the type of the parameter is null, there is
			// nothing to be done even though this is a weird
			// case indeed!
		}
	}
	private void addVariableToModel(
		final ICPPVariable aCPPVariable,
		final ICodeLevelModel aCodeLevelModel,
		Stack<IContainer> someContainers) throws DOMException {

		this.addVariableToModelOrFieldToClass(aCPPVariable, someContainers);
	}
	private void addVariableToModelOrFieldToClass(
		final ICPPVariable aCPPVariable,
		final Stack<IContainer> someContainers) {

		final IContainer container = someContainers.peek();

		final String[] qualifiedNameRow;
		try {
			qualifiedNameRow = aCPPVariable.getQualifiedName();
		}
		catch (final DOMException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
		final String fieldTypeName =
			Utils.getInterestingTypeName(aCPPVariable.getType());
		final String fieldName = qualifiedNameRow[qualifiedNameRow.length - 1];
		final String id = fieldTypeName + '.' + fieldName;
		final IEntity fieldTypeEntity =
			SearchHelper.getExistingContainerOrCreateGhost(
				this.codeLevelModel,
				someContainers,
				fieldTypeName,
				false);

		if (Arrays.equals(
			((IConstituent) container).getName(),
			fieldName.toCharArray())) {

			// It is possible but really I don't understand why:
			// see Chrome/browser/render_widget_host_hwnd.h:class RenderWidgetHostHWND
			return;
		}

		final int cardinality = Utils.getCardinality(aCPPVariable);

		IField field = null;
		if (aCPPVariable instanceof ICPPField
				&& !(container instanceof IPackage)) {

			field = ((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
				.createField(
					id.toCharArray(),
					fieldName.toCharArray(),
					fieldTypeEntity.getName(),
					cardinality);
			Utils.setVisibility(field, (ICPPMember) aCPPVariable);
		}
		else if (aCPPVariable instanceof ICPPVariable
				|| container instanceof IPackage) {

			field = ((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
				.createGlobalField(
					id.toCharArray(),
					fieldName.toCharArray(),
					fieldTypeEntity.getName(),
					cardinality);
		}

		Utils.setConst(field, aCPPVariable);
		container.addConstituent(field);
	}
	void convertDeclaration(
		final Accumulator anAccumulator,
		final IASTDeclSpecifier aDeclaration,
		final Stack<IContainer> someContainers) throws DOMException {

		final IBinding binding;

		// Get the binding
		if (aDeclaration instanceof ICPPASTCompositeTypeSpecifier) {
			final ICPPASTCompositeTypeSpecifier compositeTypeSpecifier =
				(ICPPASTCompositeTypeSpecifier) aDeclaration;
			binding = compositeTypeSpecifier.getName().resolveBinding();
		}
		else if (aDeclaration instanceof ICPPASTEnumerationSpecifier) {
			binding = ((ICPPASTEnumerationSpecifier) aDeclaration)
				.getName()
				.resolveBinding();
		}
		else if (aDeclaration instanceof ICPPASTNamedTypeSpecifier) {
			// This seems to the the case of "friend class VCFilter;"
			// that we treat in a subsequent phase: do nothing here!
			//	class BuildsMetaMakefileGenerator : public MetaMakefileGenerator
			//	{
			//	    bool init_flag;
			//	private:
			//	    struct Build {
			//	        QString name, build;
			//	        MakefileGenerator *makefile;
			//			^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//	binding =
			//		((ICPPASTNamedTypeSpecifier) declSpecififer)
			//			.getName()
			//			.resolveBinding();
			binding = null;
		}
		else if (aDeclaration instanceof ICPPASTElaboratedTypeSpecifier) {
			// This seems to the the case of "friend class VCFilter;"
			// that we treat in a subsequent phase: do nothing here!
			// It also includes the case of
			//	void baz(enum Foo foo);
			// in which we repeat that Foo is an enum.
			binding = null;
		}
		else if (aDeclaration instanceof ICPPASTSimpleDeclSpecifier) {
			// Represents a built-in type in C++
			binding = null;
		}
		else {
			Utils.reportUnknownType(
				GeneratorHelper.class,
				"specifier",
				aDeclaration.getRawSignature(),
				aDeclaration.getClass());
			binding = null;
		}

		// To something with the binding.
		if (binding instanceof ICPPClassType) {
			if (binding instanceof ICPPClassTemplate) {
				// Do nothing.
			}
			else {
				final ICPPClassType classType = (ICPPClassType) binding;
				this.addEntityToModel(
					anAccumulator,
					classType,
					this.codeLevelModel,
					someContainers);
			}
		}
		else if (binding instanceof ICPPFunction) {
			// TODO This is from a declaration, not a definition, necessary?
			// http://stackoverflow.com/a/1410632/2256758
			final ICPPFunction function = (ICPPFunction) binding;
			final IASTStatement body =
				Utils.getBody(this.astTranslationUnits, function);
			this.addFunctionToModel(
				anAccumulator,
				function,
				body,
				this.codeLevelModel,
				someContainers);
		}
		else if (binding instanceof ICPPField) {
			// Do nothing.
			this.addVariableToModel(
				(ICPPVariable) binding,
				this.codeLevelModel,
				someContainers);
		}
		else if (binding instanceof ITypedef) {
			// Do nothing.
		}
		else if (binding instanceof ICPPVariable) {
			// Do nothing.
		}
		else if (binding instanceof ICPPEnumeration) {
			this.addEnumerationToModel(
				(ICPPEnumeration) binding,
				someContainers);
		}
		else if (binding instanceof IProblemBinding) {
			// Do nothing.
		}
		else if (binding != null) {
			Utils.reportUnknownType(
				GeneratorHelper.class,
				"binding",
				aDeclaration.getRawSignature(),
				aDeclaration.getClass());
		}
	}
	void convertDeclaration(
		final Accumulator anAccumulator,
		final IASTFunctionCallExpression aDeclaration,
		final Stack<IContainer> someContainers) throws DOMException {

		// First, deal with the calling operation.
		final IOperation callingOperation =
			SearchHelper.getExistingOperationOrCreateGhost(
				this.codeLevelModel,
				anAccumulator,
				aDeclaration);
		if (callingOperation == null) {
			// It is possible that the call is "outside" of any function
			// in the case of the initialisation of a variable as in:
			//	static const SkColor kTitleColor = SkColorSetRGB(6, 45, 117);
			// In that case, do nothing.
			return;
		}

		// Second, deal with the called operation and the 
		// target entity, defining the called operation.
		final IOperation calledOperation;
		final IFirstClassEntity targetEntity;
		{
			final IASTExpression calledFunctionNameExpression =
				aDeclaration.getFunctionNameExpression();
			final IASTName calledFunctionName;
			if (calledFunctionNameExpression instanceof ICPPASTFieldReference) {
				calledFunctionName =
					((ICPPASTFieldReference) calledFunctionNameExpression)
						.getFieldName();
			}
			else if (calledFunctionNameExpression instanceof IASTIdExpression) {
				calledFunctionName =
					((IASTIdExpression) calledFunctionNameExpression).getName();
			}
			else {
				Utils.reportUnknownType(
					GeneratorHelper.class,
					"function-name expression",
					calledFunctionNameExpression.getRawSignature(),
					calledFunctionNameExpression.getClass());
				return;
			}

			final char[] calledFunctionNameName;
			final IBinding calledFunction = calledFunctionName.getBinding();
			if (calledFunction instanceof ICPPMethodSpecialization) {
				// Do nothing. We do not handle template right now.
				return;
			}
			else if (calledFunction instanceof ICPPFunction) {
				calledFunctionNameName =
					((ICPPFunction) calledFunction).getNameCharArray();
				calledOperation =
					anAccumulator.getOperation((ICPPFunction) calledFunction);
				targetEntity = anAccumulator
					.getFirstClassEntity((ICPPFunction) calledFunction);
			}
			else if (calledFunction instanceof ICPPNamespace) {
				// Yes, it can happen! For some reason, 
				// the parser sometimes says that the
				// called function is actually a namespace,
				// what should be done?
				return;
			}
			else {
				if (calledFunctionName instanceof ICPPASTQualifiedName) {
					calledFunctionNameName = Utils.getQualifiedName(
						Utils.getQualifiedName(
							(ICPPASTQualifiedName) calledFunctionName));
				}
				else {
					calledFunctionNameName = calledFunctionName.toCharArray();
				}

				final String pathOfCalledFunction =
					SearchHelper.getExistingOperationOrCreateGhost(
						this.codeLevelModel,
						someContainers,
						Utils.buildGlobalFunctionID(calledFunctionNameName));
				try {
					calledOperation = (IOperation) Finder
						.find(pathOfCalledFunction, this.codeLevelModel);
					if (calledOperation instanceof IGlobalFunction) {
						targetEntity = (IFirstClassEntity) calledOperation;
					}
					else {
						targetEntity = (IFirstClassEntity) Finder.findContainer(
							pathOfCalledFunction,
							this.codeLevelModel);
					}
				}
				catch (final FormatException e) {
					// TODO Do something when the operation is missing!
					// e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					return;
				}
			}
			if (calledOperation == null) {
				Utils.reportUnknownType(
					GeneratorHelper.class,
					"operation",
					calledFunctionNameName,
					calledFunction.getClass());
				return;
			}
		}

		// TODO Handle calls from fields. 
		//	final IFirstClassEntity entityDeclaringField;
		final boolean isFromField = false;

		final int visibility = callingOperation.getVisibility();
		final int cardinality = Constants.CARDINALITY_ONE;
		final int type = Utils.getMethodInvocationType(
			callingOperation,
			calledOperation,
			isFromField);

		final IMethodInvocation methodInvocation =
			((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
				.createMethodInvocation(
					type,
					cardinality,
					visibility,
					targetEntity);
		methodInvocation.setCalledMethod(calledOperation);
		callingOperation.addConstituent(methodInvocation);
	}
	void convertDeclaration(
		final Accumulator anAccumulator,
		final IASTSimpleDeclaration aDeclaration,
		final Stack<IContainer> someContainers) throws DOMException {

		// Management of global variables only.
		// Here variables must be declared directly
		// in a translation unit or inside a namespace.
		if (!(aDeclaration.getParent() instanceof ICPPASTNamespaceDefinition
				|| aDeclaration
					.getParent() instanceof ICPPASTTranslationUnit)) {

			return;
		}

		final String signature = aDeclaration.getRawSignature();
		if (signature.startsWith("typedef ")) {
			// I don't want to deal with typedef, which
			// complicate things like:
			//	typedef LoggerImpl::SharedLoggerImplPtr SharedLoggerImplPtr;
			return;
		}

		final IASTDeclarator[] declarators = aDeclaration.getDeclarators();
		final IASTDeclSpecifier specifier = aDeclaration.getDeclSpecifier();
		for (int i = 0; i < declarators.length; i++) {
			final IASTDeclarator declarator = declarators[i];
			final IASTName declaratorName = declarator.getName();
			final IBinding declaratorBinding = declaratorName.getBinding();
			if (declaratorBinding instanceof ICPPVariable) {
				// Here, I am sure that I am dealing with a variable
				// that must be added to the code-level model, because
				// I "PROCESS_SKIP" the visit of IASTDeclSpecifier in
				// VisitorTopLevelDeclarations.visit(IASTDeclSpecifier).
				try {
					this.addVariableToModel(
						(ICPPVariable) declaratorBinding,
						this.codeLevelModel,
						someContainers);
				}
				catch (final ModelDeclarationException e) {
					// Do nothing: we are facing a variable already added
					// during the first phase... How possible?
					// TODO How come is a variable added in the first phase?
				}
			}
			else {
				final int cardinality;
				if (declarator instanceof ICPPASTArrayDeclarator) {
					cardinality = Constants.CARDINALITY_MANY;
				}
				else if (declarator.getPointerOperators().length > 0) {
					cardinality = Constants.CARDINALITY_MANY;
				}
				else {
					cardinality = Constants.CARDINALITY_ONE;
				}
				final char[] fieldName =
					Utils.convertSeparators(declaratorName.toCharArray());
				final char[] fieldTypeName;
				if (specifier instanceof ICPPASTCompositeTypeSpecifier) {
					final IBinding binding =
						((ICPPASTCompositeTypeSpecifier) specifier)
							.getName()
							.getBinding();
					if (binding instanceof ICPPClassType) {
						// Yann 2013/09/11: Strange code...
						// I am not comfortable with that piece of code! Even
						// more so sinceI forgot to check first if an entity
						// exists before creating it...
						//	this.addEntityToModel(
						//		anAccumulator,
						//		(ICPPClassType) binding,
						//		this.codeLevelModel,
						//		someContainers);
						//	final IFirstClassEntity entity =
						//		anAccumulator
						//			.getFirstClassEntity((ICPPClassType) binding);
						final IFirstClassEntity entity =
							(IFirstClassEntity) SearchHelper
								.getExistingContainerOrCreateGhost(
									this.codeLevelModel,
									anAccumulator,
									(ICPPClassType) binding);
						fieldTypeName = entity.getID();
					}
					else {
						Utils.reportUnknownType(
							GeneratorHelper.class,
							"binding",
							binding.toString(),
							binding.getClass());
						fieldTypeName = new char[0];
					}
				}
				else if (specifier instanceof ICPPASTNamedTypeSpecifier) {
					final IASTName fieldType =
						((ICPPASTNamedTypeSpecifier) specifier).getName();
					// At this point, I cannot ask the name for its binding,
					// it would be null, so I know that I must find the
					// corresponding entity or create a ghost.
					//	final IBinding binding = fieldTypeName.getBinding();
					fieldTypeName = fieldType.toCharArray();
					// I actually do nothing here because qualified name
					// and type will be taken care of below, when I will
					// concretely create the global field.
					if (fieldType instanceof ICPPASTQualifiedName) {
						// Do nothing.
					}
					else if (fieldType instanceof IASTName) {
						// Do nothing.
					}
					else if (fieldType instanceof ICPPASTTemplateId) {
						// Do nothing.
					}
					else {
						Utils.reportUnknownType(
							GeneratorHelper.class,
							"name",
							fieldTypeName,
							fieldType.getClass());
					}
				}
				else if (specifier instanceof ICPPASTElaboratedTypeSpecifier) {
					// The binding is null at this point, nothing much that I can do.
					fieldTypeName = "struct".toCharArray();
				}
				else if (specifier instanceof ICPPASTEnumerationSpecifier) {
					// The binding is null at this point, nothing much that I can do.
					fieldTypeName = "enum".toCharArray();
				}
				else if (specifier instanceof ICPPASTSimpleDeclSpecifier) {
					// Basic type.
					fieldTypeName = ((IASTSimpleDeclSpecifier) specifier)
						.getRawSignature()
						.toCharArray();
				}
				else if (specifier instanceof ICPPASTLinkageSpecification) {
					fieldTypeName = Utils.PROBLEM_TYPE.toCharArray();
				}
				else {
					Utils.reportUnknownType(
						GeneratorHelper.class,
						"specifier",
						specifier.getRawSignature(),
						specifier.getClass());
					fieldTypeName = Utils.PROBLEM_TYPE.toCharArray();
				}

				// I remove any qualifier from the type name,
				// such as static or const.
				char[] cleanedFieldTypeName = Utils
					.convertSeparators(Utils.removeQualifiers(fieldTypeName));

				if (Utils.isQualifiedName(cleanedFieldTypeName)) {
					final char[][] qualifiedNameOfFieldType =
						Utils.getQualifiedName(cleanedFieldTypeName);
					final IEntity fieldTypeEntity =
						SearchHelper.getExistingContainerOrCreateGhost(
							this.codeLevelModel,
							someContainers,
							qualifiedNameOfFieldType,
							false);
					fieldTypeEntity
						.setStatic(Utils.isStatic(cleanedFieldTypeName));
					cleanedFieldTypeName = fieldTypeEntity.getID();
				}

				final IContainer container;
				if (Utils.isQualifiedName(fieldName)) {
					final char[][] qualifiedNameOfEnclosingContainer =
						Utils.getQualifiedType(fieldName);
					// Yann 2014/04/21: Global variable!
					// Here, I know that I am dealing with a gobal variable,
					// declared either in a translation unit or in a namespace.
					// Therefore, I cannot use the following piece of code 
					// because it assumes that it will return an IEntity. An
					// IPackage is NOT an IEntity... by definition!
					//	final IEntity enclosingEntity =
					//		SearchHelper.getExistingContainerOrCreateGhost(
					//			this.codeLevelModel,
					//			someContainers,
					//			qualifiedNameOfEnclosingType,
					//			false);
					container = SearchHelper
						.findContainerOrCreateGhostInModelRecursively(
							this.codeLevelModel,
							qualifiedNameOfEnclosingContainer,
							false);
				}
				else {
					container = (IContainer) this.codeLevelModel
						.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
				}

				final char[] id =
					Utils.buildID(cleanedFieldTypeName, fieldName);
				if (!container.doesContainConstituentWithID(id)) {
					// It is possible that the constituent already exists
					// if its binding existed in the previous phase.
					final IGlobalField field =
						((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
							.createGlobalField(
								id,
								fieldName,
								fieldTypeName,
								cardinality);
					container.addConstituent(field);
				}

				// TODO Is this condition necessary???
				//	if (declarator instanceof IASTFunctionDeclarator) {
				//		//	final char[] id =
				//		//		Utils.buildGlobalFunctionID(Utils.buildID(
				//		//			cleanedFieldTypeName,
				//		//			fieldName));
				//		if (!container.doesContainConstituentWithID(fieldName)) {
				//			// It is possible that the constituent already exists
				//			// if its binding existed in the previous phase.
				//			final IGlobalFunction function =
				//				((ICPPFactoryEclipse) CPPFactoryEclipse
				//					.getInstance()).createGlobalFunctionGhost(
				//				// TODO ? See also the if just above...
				//				// id,
				//					fieldName,
				//					fieldName);
				//			container.addConstituent(function);
				//		}
				//	}
				//	else {
				//		final char[] id =
				//			Utils.buildID(cleanedFieldTypeName, fieldName);
				//		if (!container.doesContainConstituentWithID(id)) {
				//			// It is possible that the constituent already exists
				//			// if its binding existed in the previous phase.
				//			final IGlobalField field =
				//				((ICPPFactoryEclipse) CPPFactoryEclipse
				//					.getInstance()).createGlobalField(
				//					id,
				//					fieldName,
				//					fieldTypeName,
				//					cardinality);
				//			container.addConstituent(field);
				//		}
				//	}
			}
		}
	}
	void convertDeclaration(
		final Accumulator anAccumulator,
		final ICPPASTFunctionDefinition aDefinition,
		final Stack<IContainer> someContainers) throws DOMException {

		final ICPPASTFunctionDefinition functionDefinition =
			(ICPPASTFunctionDefinition) aDefinition;
		final IBinding binding =
			functionDefinition.getDeclarator().getName().resolveBinding();
		if (binding instanceof ICPPFunction) {
			final IBinding owner = binding.getOwner();
			if (owner instanceof ICPPClassType) {
				// We handle this case after creating all the top-level
				// entities, in the method handleCassMethods().
				// We must do that because the parser may parse the
				// files in any order and thus "see" a method 
				// *definition* before its enclosing class is actually
				// defined... Ah... C++...
			}
			else if (owner instanceof IProblemBinding) {
				// Do nothing because we would not know what to 
				// do with the build PADL equivalent anyways...
			}
			else {
				this.addFunctionToModel(
					anAccumulator,
					(ICPPFunction) binding,
					aDefinition.getBody(),
					this.codeLevelModel,
					someContainers);
			}
		}
		else {
			Utils.reportUnknownType(
				GeneratorHelper.class,
				"function definition",
				binding.getName(),
				binding.getClass());
		}
	}
	void convertDeclaration(
		final Accumulator anAccumulator,
		final ICPPASTNamespaceDefinition aDeclaration,
		final Stack<IContainer> someContainers) {

		IContainer container = someContainers.peek();
		// If the current container is the default package,
		// I add the new package (if one must be created)
		// into the code-level model.
		if (container.equals(
			this.codeLevelModel
				.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID))) {

			container = this.codeLevelModel;
		}

		final IBinding binding = ((ICPPASTNamespaceDefinition) aDeclaration)
			.getName()
			.resolveBinding();
		final String namespaceName = binding.getName();
		final char[] id;
		if (StringUtils.EMPTY.equals(namespaceName)) {
			id = Utils.createAnonymousName(GeneratorHelper.UniqueID++);
		}
		else {
			id = namespaceName.toCharArray();
		}

		// In C++, it is legitimate to have the same space
		// declared in multiple files, a way to share its
		// content and its declarations... So, yes, if it
		// already exists, do nothing, just reuse the one
		// created the first time.
		if (container.doesContainConstituentWithID(id)) {
			final IContainer existingPackage =
				(IContainer) container.getConstituentFromID(id);
			someContainers.push(existingPackage);
			return;
		}

		final IPackage newPackage =
			((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
				.createPackage(id);
		container.addConstituent(newPackage);
		someContainers.push(newPackage);
	}
	void convertDeclaration(
		final Accumulator accumulator,
		final ICPPASTUsingDeclaration aDeclaration,
		final Stack<IContainer> someContainers) {

		// Examples: using ::f;  
		//           using A::g;
		// Because C++ is really whatever as a programming
		// language, we cannot do really anything because
		// anything goes:
		//	- namespace::type
		//	- namespace:type::function or attribute
		//	- namespace::type:innertype::...
		// WTF is C++?
	}
	void convertDeclaration(
		final Accumulator accumulator,
		final ICPPASTUsingDirective aDeclaration,
		final Stack<IContainer> someContainers) {

		// Example: using namespace std;
		// Here, I am sure that the name (even
		// qualified), is the name of a package.
		IContainer container = this.codeLevelModel;
		final char[][] names = Utils
			.getQualifiedName(aDeclaration.getQualifiedName().toCharArray());
		for (int i = 0; i < names.length; i++) {
			final char[] name = names[i];
			char[] id = name;
			if (i > 0) {
				id = ArrayUtils
					.addAll(ArrayUtils.add(id, Utils.SEPARATOR), name);
			}
			if (!container.doesContainConstituentWithID(id)) {
				container.addConstituent(
					((ICPPFactoryEclipse) CPPFactoryEclipse.getInstance())
						.createPackageGhost(id));
			}
			container = (IContainer) container.getConstituentFromName(name);
		}
	}
}
