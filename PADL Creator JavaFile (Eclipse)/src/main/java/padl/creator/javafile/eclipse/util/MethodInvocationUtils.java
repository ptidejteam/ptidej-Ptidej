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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMemberGhost;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IParameter;
import util.io.ProxyConsole;

public class MethodInvocationUtils {

	//	private static final int INEXITANT_TYPE = -1;
	//	private static final int CALLED_METHOD_CREATION = 1;
	//	private static final int CALLED_METHOD_STATIC_FROM_FIELD = 2;
	//	private static final int CALLED_METHOD_INSTANCE_FROM_FIELD = 3;
	//	private static final int CALLED_METHOD_STATIC = 4;// local variable,
	//	// parameter, class,
	//	// field not declared in
	//	// the current class
	//	private static final int CALLED_METHOD_INSTANCE = 5;// local variable,

	// parameter, field not
	// declared in the
	// current class

	/**
	 * 
	 * @param anEntity
	 * @param aMethodBinding
	 * @param aPadlModel
	 * @param aCurrentPackageName
	 * @return
	 */
	public static IMethod getCalledMethod(
		final IMethodBinding aMethodBinding,
		final ICodeLevelModel aPadlModel,
		final String aCurrentPackageName) {

		IMethod calledMethod = null;
		final String methodName = aMethodBinding.getName();

		final IFirstClassEntity methodDeclaringEntity =
			(IFirstClassEntity) PadlParserUtil.getEntityOrGetOrCreateGhost(
				false,
				aMethodBinding.getDeclaringClass(),
				aPadlModel,
				aCurrentPackageName);

		String methodDeclaringEntityPackagePath =
			methodDeclaringEntity.getDisplayPath().substring(
				0,
				methodDeclaringEntity.getDisplayPath().lastIndexOf("|"));
		// compute method signature and search it in the declaring class, if it
		// is not there, add it
		final List<IParameter> parametersList = new ArrayList<IParameter>();

		final ITypeBinding parametersTypes[] =
			aMethodBinding.getParameterTypes();
		// create the parameters
		//for the parameters, use the package path of the declaring entity to discriminate between existing classes in the package and ghosts
		for (final ITypeBinding typeBinding : parametersTypes) {
			final IEntity paramEntity =
				PadlParserUtil.getEntityOrGetOrCreateGhost(
					false,
					typeBinding,
					aPadlModel,
					methodDeclaringEntityPackagePath);

			// Yann: May be null when parsing Foutse's Eclipse data!
			// Yann: May use a Ghost with no name (empty string) when parsing Foutse's "colossus" data!
			// TODO: Understand why it could be null...
			if (paramEntity != null && !typeBinding.isCapture()) {
				final int paramDim = PadlParserUtil.getCardinality(typeBinding);
				final IParameter param =
					aPadlModel.getFactory().createParameter(
						paramEntity,
						paramDim);

				// see comment PaldParserUtil.getListOfParameters
				// Aminata 14/09/11
				// Yann 2015/03/16: Rationale?
				// It just does not make sense to use comments to store information,
				// remember never to use String when other data-types are available!
				// Also, the old code:
				//	param.setComment(Integer.toString(typeBinding.getDimensions()));
				// is not the same as:
				param.setComment(Integer.toString(paramDim));

				parametersList.add(param);
			}
		}

		// get the return type
		final String returnType =
			PadlParserUtil.getTypeName(aMethodBinding.getReturnType(), false);

		final char[] methodId =
			PadlParserUtil.computeSignature(
				methodName,
				parametersList,
				returnType,
				aMethodBinding.getModifiers());

		calledMethod =
			(IMethod) methodDeclaringEntity.getConstituentFromID(methodId);
		if (calledMethod == null) {
			//if methodDeclaringEntity is a ghost add the method, 
			//TODO else
			//we have to search in its superclasses and add it in the first superclass which is ghost
			//otherwise, don't add it...
			if (methodDeclaringEntity instanceof IGhost
					|| methodDeclaringEntity instanceof IMemberGhost) {

				calledMethod =
					aPadlModel.getFactory().createMethod(
						methodId,
						methodName.toCharArray());

				for (final IParameter param : parametersList) {
					calledMethod.addConstituent(param);
					calledMethod.setVisibility(aMethodBinding.getModifiers());
				}

				methodDeclaringEntity.addConstituent(calledMethod);
			}
			else {
				//TODO else
				//we have to search in its superclasses and add it in the first superclass which is ghost
				//otherwise, don't add it... see also the case of fields...
			}
		}
		return calledMethod;
	}
	/**
	 * return the type binding of an expression which could be: an arrayaccess,
	 * a field access, a qualified name, a simple name, ...
	 * 
	 * @param anExpression
	 * @return
	 */
	private static IBinding getExpressionTypeBinding(
		final Expression anExpression) {

		IBinding binding = null;

		if (anExpression instanceof ArrayAccess) {
			final ArrayAccess arrayAccess = (ArrayAccess) anExpression;
			return MethodInvocationUtils.getExpressionTypeBinding(arrayAccess
				.getArray());

		}

		if (anExpression instanceof FieldAccess) {
			final FieldAccess fieldAccess = (FieldAccess) anExpression;
			return MethodInvocationUtils.getExpressionTypeBinding(fieldAccess
				.getName());
		}

		if (anExpression instanceof ParenthesizedExpression) {
			final ParenthesizedExpression parenthesizedExpression =
				(ParenthesizedExpression) anExpression;

			return MethodInvocationUtils
				.getExpressionTypeBinding(parenthesizedExpression
					.getExpression());
		}

		if (anExpression instanceof CastExpression) {
			final CastExpression castExpression = (CastExpression) anExpression;

			return MethodInvocationUtils
				.getExpressionTypeBinding(castExpression.getExpression());
		}

		if (anExpression instanceof QualifiedName
				|| anExpression instanceof SimpleName) {

			if (anExpression instanceof QualifiedName) {
				final QualifiedName qualifiedName =
					(QualifiedName) anExpression;
				binding = qualifiedName.resolveBinding();
			}
			if (anExpression instanceof SimpleName) {
				final SimpleName simpleName = (SimpleName) anExpression;
				binding = simpleName.resolveBinding();
			}
		}
		return binding;
	}

	/**
	 * Return the entity that declares the field by which the invocation is
	 * performed
	 * 
	 * @param aPadlModel
	 * @param anExpression
	 * @param anInvokedMethodType
	 */
	public static IFirstClassEntity getFieldDeclaringEntity(
		final ICodeLevelModel aPadlModel,
		final Expression anExpression,
		final IFirstClassEntity currentEntity,
		final String aCurrentPackageName) {

		final IVariableBinding variableBinding =
			(IVariableBinding) MethodInvocationUtils
				.getExpressionTypeBinding(anExpression);

		if (variableBinding == null) {
			return null;
		}

		return (IFirstClassEntity) PadlParserUtil.getEntityOrGetOrCreateGhost(
			false,
			variableBinding.getDeclaringClass(),
			aPadlModel,
			aCurrentPackageName);
	}

	/**
	 * Return the field by which the invocation is performed
	 * 
	 * @param aPadlModel
	 * @param anExpression
	 * @param anEntity
	 * @return
	 */
	public static IField getFieldInvocation(
		final ICodeLevelModel aPadlModel,
		final Expression anExpression,
		final IFirstClassEntity anEntity) {

		IField field = null;

		final IVariableBinding variableBinding =
			(IVariableBinding) MethodInvocationUtils
				.getExpressionTypeBinding(anExpression);

		final String fieldName = variableBinding.getName();
		// search a field in an entity; I assume that 2 fields can't have the
		// same name so I don't check the type

		try {
			final Iterator<?> iter =
				anEntity.getIteratorOnConstituents(Class
					.forName("padl.kernel.impl.Field"));

			IField tmpField;

			while (iter.hasNext()) {
				tmpField = (IField) iter.next();

				if (fieldName.equals(tmpField.getDisplayName())) {
					field = tmpField;
					break;
				}
			}
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Should I add this field to the ghost --- and if it is an array or
		// list
		// type
		// should I add it ; I think it will create like ghosts for those
		// classes - what should I do?
		// if yes, the code for that is below

		if (field == null) {

			final String fieldID = fieldName;
			final ITypeBinding fieldTypeBinding = variableBinding.getType();
			final String fieldType =
				PadlParserUtil.getTypeName(fieldTypeBinding, true);
			final int cardinality = PadlParserUtil.getCardinality(fieldTypeBinding);
			field =
				aPadlModel.getFactory().createField(
					fieldID.toCharArray(),
					fieldName.toCharArray(),
					fieldType.toCharArray(),
					cardinality);
			anEntity.addConstituent(field);
		}

		return field;
	}

	/**
	 * 
	 * @param callingMethodVisibility
	 * @param calledMethodVisibility
	 * @param invocationThroughField
	 * @return
	 */
	public static int getMethodInvocationType(
		final int callingMethodVisibility,
		final int calledMethodVisibility,
		final boolean invocationThroughField,
		final boolean invocationThroughStaticField) {
		int type;

		if (invocationThroughField) {
			// Yann 2013/08/13: CLASS_INSTANCE...
			// I don't forget to check if the field is static or not...
			if (Modifier.isStatic(callingMethodVisibility)
					|| invocationThroughStaticField) {
				if (Modifier.isStatic(calledMethodVisibility)) {
					//type = IMethodInvocation.CLASS_CLASS_FROM_FIELD;
					//02012012- Aminata S
					//Changed by CLASS_CLASS because when a static method is called, 
					//it is in the bytecode a static invocation (INVOKE STATIC) and no matter if it is from a fiedl or not
					type = IMethodInvocation.CLASS_CLASS;
				}
				else {
					type = IMethodInvocation.CLASS_INSTANCE_FROM_FIELD;
				}
			}
			else {
				if (Modifier.isStatic(calledMethodVisibility)) {
					//type = IMethodInvocation.INSTANCE_CLASS_FROM_FIELD;
					//02012012- Aminata S
					//Changed by CLASS_CLASS because when a static method is called, 
					//it is in the bytecode a static invocation (INVOKE STATIC) and no matter if it is from a fiedl or not
					type = IMethodInvocation.INSTANCE_CLASS;
				}
				else {
					type = IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD;
				}
			}

		}
		else {

			if (Modifier.isStatic(callingMethodVisibility)) {
				if (Modifier.isStatic(calledMethodVisibility)) {
					type = IMethodInvocation.CLASS_CLASS;
				}
				else {
					type = IMethodInvocation.CLASS_INSTANCE;
				}
			}
			else {
				if (Modifier.isStatic(calledMethodVisibility)) {
					type = IMethodInvocation.INSTANCE_CLASS;
				}
				else {
					type = IMethodInvocation.INSTANCE_INSTANCE;
				}
			}

		}

		return type;

	}

	public static boolean isInvocationThroughField(final Expression anExpression) {
		boolean throughField = false;
		if (anExpression == null) {
			return throughField;
		}
		final IBinding binding =
			MethodInvocationUtils.getExpressionTypeBinding(anExpression);

		if (binding instanceof IVariableBinding) {
			final IVariableBinding varBinding = (IVariableBinding) binding;

			if (varBinding.isField()) {
				throughField = true;
			}

		}
		return throughField;
	}
	public static boolean isInvocationThroughStaticField(
		final Expression anExpression) {
		boolean throughStaticField = false;
		if (anExpression == null) {
			return throughStaticField;
		}
		final IBinding binding =
			MethodInvocationUtils.getExpressionTypeBinding(anExpression);

		if (binding instanceof IVariableBinding) {
			final IVariableBinding varBinding = (IVariableBinding) binding;

			if (varBinding.isField()) {
				throughStaticField =
					Modifier.isStatic(varBinding.getModifiers());
			}

		}
		return throughStaticField;
	}
}
