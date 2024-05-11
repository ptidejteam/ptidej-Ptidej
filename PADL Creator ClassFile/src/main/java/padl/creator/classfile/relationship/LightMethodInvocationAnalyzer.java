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
package padl.creator.classfile.relationship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import padl.creator.classfile.util.ExtendedMethodInfo;
import padl.creator.classfile.util.ExtendedMethodInvocation;
import padl.creator.classfile.util.Utils;
import padl.kernel.Constants;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFactory;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.util.Util;
import com.ibm.toad.cfparse.instruction.JVMConstants;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.cfparse.utils.ByteArray;

/**
 * @version 0.1
 * @author Yann-Gaël Guéhéneuc
 */
// TODO: This class should implement the Singleton desing pattern.
public final class LightMethodInvocationAnalyzer {
	private final IAbstractLevelModel abstractLevelModel;
	private List contexes;
	private final IFactory factory;
	private List methodInvocations;
	private final Context primaryContext;

	public LightMethodInvocationAnalyzer(
		final IFactory aFactory,
		final IAbstractLevelModel anAbstractLevelModel) {

		this.abstractLevelModel = anAbstractLevelModel;
		this.factory = aFactory;
		this.primaryContext = new Context(this.abstractLevelModel);
	}
	private Context getCurrentContext() {
		return (Context) this.contexes.get(0);
	}
	private void handleALoad(final Context context, final int offset) {
	}
	private void handleGetField(final Context context, final int offset) {
		String invocationSiteName =
			context
				.getConstantPool()
				.get(
					ByteArray.getShortAtOffset(context.getRawCode(), offset + 1))
				.getAsJava();

		// Typically, an invocationSiteName looks like:
		//	boolean java.awt.AWTEvent.consumed
		// where:
		//	- "boolean" is the type of the field;
		//	- "java.awt.AWTEvent" is the class declaring this field;
		//	- "consumed" is the name of the field.

		int index = invocationSiteName.indexOf(' ');
		final String fieldDeclaringClassName =
			invocationSiteName.substring(
				index + 1,
				invocationSiteName.lastIndexOf('.'));
		final IFirstClassEntity fieldDeclaringEntity =
			context.getEntityFromID(fieldDeclaringClassName.toCharArray());

		// Yann 2002/08/21: Cardinality.
		// I now check the cardinality according to the
		// type of the invocation site.
		//	invocationSiteName = invocationSiteName.substring(0, index);
		//	int cardinality = 2;
		//	if ((index = invocationSiteName.indexOf('[')) == -1) {
		//		cardinality = 1;
		//		index = invocationSiteName.length();
		//	}
		//	invocationSiteName = invocationSiteName.substring(0, index);
		invocationSiteName = invocationSiteName.substring(0, index);
		if (Util.isArrayOrCollection(invocationSiteName.toCharArray())
				|| (fieldDeclaringEntity != null && Util
					.isArrayOrCollection(fieldDeclaringEntity))) {

			context.setCardinality(Constants.CARDINALITY_MANY);
		}
		else {
			context.setCardinality(Constants.CARDINALITY_ONE);
		}
		if ((index = invocationSiteName.indexOf('[')) == -1) {
			index = invocationSiteName.length();
		}
		invocationSiteName = invocationSiteName.substring(0, index);
		context.setInvocationSiteName(invocationSiteName.toCharArray());
		context.setInvocationSiteStatic(false);
	}
	private void handleGetStatic(final Context context, final int offset) {
		String invocationSiteName =
			context
				.getConstantPool()
				.get(
					ByteArray.getShortAtOffset(context.getRawCode(), offset + 1))
				.getAsJava();

		// Typically, an invocationSiteName looks like:
		//	boolean java.awt.AWTEvent.consumed
		// where:
		//	- "boolean" is the type of the field;
		//	- "java.awt.AWTEvent" is the class declaring this field;
		//	- "consumed" is the name of the field.

		int index = invocationSiteName.indexOf(' ');
		final String fieldDeclaringClassName =
			invocationSiteName.substring(
				index + 1,
				invocationSiteName.lastIndexOf('.'));
		final IFirstClassEntity fieldDeclaringEntity =
			context.getEntityFromID(fieldDeclaringClassName.toCharArray());

		// Yann 2002/08/20: Use
		// I add a message type to create a use
		// relationship corresponding to the class 
		// declaring the static field.
		// Yann 2006/02/08: Members!
		// The getEntityFromID() method takes care of members and ghosts...
		//	// Yann 2002/08/01: Ghost!
		//	// From now on, if an entity cannot be found in the
		//	// list of entities, I create a ghost for it...
		//	if (pTargetEntity == null) {
		//		context.addGhost(fieldDeclaratingClassName);
		//		pTargetEntity = context.getEntityFromID(fieldDeclaratingClassName);
		//	}
		// TODO: Should this piece of code be there at all?
		context.addMethodInvocation(new ExtendedMethodInvocation(
			this.factory,
			context.getCurrentEntity(),
			context.getCurrentMethod(),
			IMethodInvocation.CLASS_CLASS,
			Constants.CARDINALITY_ONE,
			fieldDeclaringEntity));

		// Yann 2002/08/21: Cardinality.
		// I now check the cardinality according to the
		// type of the invocation site.
		//	invocationSiteName = invocationSiteName.substring(0, index);
		//	int cardinality = 2;
		//	if ((index = invocationSiteName.indexOf('[')) == -1) {
		//		cardinality = 1;
		//		index = invocationSiteName.length();
		//	}
		//	invocationSiteName = invocationSiteName.substring(0, index);
		invocationSiteName = invocationSiteName.substring(0, index);
		if (Util.isArrayOrCollection(invocationSiteName.toCharArray())
				|| (fieldDeclaringEntity != null && Util
					.isArrayOrCollection(fieldDeclaringEntity))) {

			context.setCardinality(Constants.CARDINALITY_MANY);
		}
		else {
			context.setCardinality(Constants.CARDINALITY_ONE);
		}
		if ((index = invocationSiteName.indexOf('[')) == -1) {
			index = invocationSiteName.length();
			context.setInvocationSiteStatic(true);
		}
		else {
			// Aminata and Yann 2012/02/02: Static arrays!
			// If the invocation site is an array declared as
			// a static field, then the invocation site is
			// NOT static because the content of an array is
			// an instance, hence NOT static by definition.
			context.setInvocationSiteStatic(false);
		}
		invocationSiteName = invocationSiteName.substring(0, index);
		context.setInvocationSiteName(invocationSiteName.toCharArray());

		// Yann 2002/08/20: Careful!
		// If the static field comes from the current class,
		// then it is an aggregation relationships, else
		// it is a use relationship.
		// Yann 2012/02/02: Convinced!
		// This piece of code is needed to distinguish the
		// following two cases:
		//	class A
		//		private static B b;
		//		void foo() {
		//			b.bar();
		// and:
		//	class A
		//		void foo() {
		//			System.out.println(...)
		if (fieldDeclaringClassName.equals(context
			.getCurrentEntity()
			.getDisplayID())) {

			context.setInternalFieldDeclaration(true);
		}
		else {
			context.setInternalFieldDeclaration(false);
		}
	}
	private void handleInstanceOf(final Context context, final int offset) {
		String invocationSiteName =
			context.getConstantPool().getAsString(
				context
					.getConstantPool()
					.get(
						ByteArray.getShortAtOffset(
							context.getRawCode(),
							offset + 1))
					.getIndices()[0]);
		invocationSiteName = invocationSiteName.replace('/', '.');

		context.setCardinality(Constants.CARDINALITY_ONE);

		if (!Util.isPrimtiveType(invocationSiteName.toCharArray())
				&& !Utils.isAnonymousOrLocalEntity(invocationSiteName
					.toCharArray())
				&& !Utils.isSpecialMethod(context.getMethodName())) {

			final IFirstClassEntity pTargetEntity =
				context.getEntityFromID(invocationSiteName.toCharArray());
			// Yann 2006/02/08: Members!
			// The getEntityFromID() method takes care of members and ghosts...
			//	// Yann 2002/08/01: Ghost!
			//	// From now on, if an entity cannot be found in the
			//	// list of entities, I create a ghost for it...
			//	if (pTargetEntity == null) {
			//		context.addGhost(invocationSiteName);
			//		pTargetEntity = context.getEntityFromID(invocationSiteName);
			//	}

			final ExtendedMethodInvocation methodInvocation;
			if (Access.isStatic(context.getMethodAccess())) {
				methodInvocation =
					new ExtendedMethodInvocation(
						this.factory,
						context.getCurrentEntity(),
						context.getCurrentMethod(),
						IMethodInvocation.CLASS_CLASS,
						context.getCardinality(),
						pTargetEntity);
			}
			else {
				methodInvocation =
					new ExtendedMethodInvocation(
						this.factory,
						context.getCurrentEntity(),
						context.getCurrentMethod(),
						IMethodInvocation.INSTANCE_CLASS,
						context.getCardinality(),
						pTargetEntity);
			}

			context.addMethodInvocation(methodInvocation);
		}
	}
	private void handleInvokeStatic(final Context context, final int offset) {
		String invocationSiteName =
			context.getConstantPool().getAsString(
				context
					.getConstantPool()
					.get(
						ByteArray.getShortAtOffset(
							context.getRawCode(),
							offset + 1))
					.getIndices()[0]);
		invocationSiteName = invocationSiteName.replace('/', '.');

		// Yann 2012/02/21: Method invocation with Java parser!
		// Thanks to Aminata, we now have tests to compare the consistency
		// of the PADL models from the JavaFile and ClassFile creators...
		String invokedMethodName =
			context.getConstantPool().getAsString(
				context
					.getConstantPool()
					.get(
						ByteArray.getShortAtOffset(
							context.getRawCode(),
							offset + 1))
					.getIndices()[1]);
		invokedMethodName =
			invokedMethodName.substring(0, invokedMethodName.indexOf(' '))
					+ "()";

		final IFirstClassEntity invokedMethodDeclaringEntity =
			context.getEntityFromID(invocationSiteName.toCharArray());
		final IMethod invokedMethod =
			(IMethod) invokedMethodDeclaringEntity
				.getConstituentFromID(invokedMethodName.toCharArray());

		if (!Util.isPrimtiveType(invocationSiteName.toCharArray())
				&& !Utils.isAnonymousOrLocalEntity(invocationSiteName
					.toCharArray())
				&& !Utils.isSpecialMethod(context.getMethodName())) {

			final IFirstClassEntity targetEntity =
				context.getEntityFromID(invocationSiteName.toCharArray());
			// Yann 2006/02/08: Members!
			// The getEntityFromID() method takes care of members and ghosts...
			//	// Yann 2002/08/01: Ghost!
			//	// From now on, if an entity cannot be found in the
			//	// list of entities, I create a ghost for it...
			//	if (pTargetEntity == null) {
			//		context.addGhost(invocationSiteName);
			//		pTargetEntity = context.getEntityFromID(invocationSiteName);
			//	}

			// Yann 2013/08/13: Cardinality of the instantiated class
			// I check if the class is a collection rather than asking
			// the context of the cardinality of the creation... but
			// ask the context of the class is not a collection, just
			// in case...
			final int cardinality;
			if (Util.isArrayOrCollection(invocationSiteName.toCharArray())) {
				cardinality = Constants.CARDINALITY_MANY;
			}
			else {
				cardinality = context.getCardinality();
			}

			final ExtendedMethodInvocation methodInvocation;
			if (Access.isStatic(context.getMethodAccess())) {
				methodInvocation =
					new ExtendedMethodInvocation(
						this.factory,
						context.getCurrentEntity(),
						context.getCurrentMethod(),
						IMethodInvocation.CLASS_CLASS,
						cardinality,
						targetEntity);
			}
			else {
				methodInvocation =
					new ExtendedMethodInvocation(
						this.factory,
						context.getCurrentEntity(),
						context.getCurrentMethod(),
						IMethodInvocation.INSTANCE_CLASS,
						cardinality,
						targetEntity);
			}
			methodInvocation.setCalledMethod(invokedMethod);

			context.addMethodInvocation(methodInvocation);
		}
	}
	private void handleInvokeVirtual(
		final Context givenContext,
		final int offset) {

		// Yann 2002/08/21: Consumption.
		// I must remove the contexes that corresponds
		// to the arguments of the methods.
		Context context = givenContext;
		final String methodName =
			context.getConstantPool().getAsJava(
				context
					.getConstantPool()
					.get(
						ByteArray.getShortAtOffset(
							context.getRawCode(),
							offset + 1))
					.getIndices()[1]);
		//	final int numberOfArguments = Misc.getNumberOfArguments(methodName);
		//	for (int i = 0; i < numberOfArguments; i++) {
		//		context = (Context) this.contexes.remove(0);
		//	}

		if (context.getInvocationSiteName() == null) {
			String invocationSiteName =
				context.getConstantPool().getAsString(
					context
						.getConstantPool()
						.get(
							ByteArray.getShortAtOffset(
								context.getRawCode(),
								offset + 1))
						.getIndices()[0]);
			invocationSiteName = invocationSiteName.replace('/', '.');
			context.setInvocationSiteName(invocationSiteName.toCharArray());
		}

		if (!Utils.isSpecialMethod(methodName.toCharArray())) {
			// Yann 2003/11/28: IContainer type.
			// If the method name corresponds to a getter or a setter,
			// I use this information to explicit the target end of
			// association, aggregation, composition.
			// TODO: This code should be replaced by a real bytecodes
			// analysis to infer the (static) type of the argument, not
			// only its declaring type.
			// Yann 2003/11/29: (Almost) duplicated code.
			// TODO: This code has pretty much the same semantics as the code
			// in the ContainerAggregation.findPairsOne() and
			// ContainerAggregation.findPairsMany() methods...
			// I should merge the two code!
			// TODO: Reverse the loops and the tests to avoid going into
			// the loops if the methodName does not correspond to any of
			// the looked after method names.
			for (int i = 0; i < Constants.GETTERS_CARDINALITY_ONE.length; i++) {
				if (methodName.startsWith(Constants.GETTERS_CARDINALITY_ONE[i])
						&& methodName.indexOf('(') + 1 == methodName
							.indexOf(')')) {

					// Yann 2003/11/29: Return type.
					// I use the return type for getter with cardinality one.
					// Yann 2004/08/15: Collections...
					// If the parameter type is java.lang.Object, then I am
					// probably dealing with a standard collection API, so
					// I keep the previously found invocation site.
					final String parameterType =
						methodName.substring(
							methodName.indexOf('.') + 1,
							methodName.indexOf(' '));
					if (!Util.isObjectModelRoot(parameterType.toCharArray())) {
						context.setInvocationSiteName(parameterType
							.toCharArray());
					}
					context.setCardinality(Constants.CARDINALITY_ONE);
				}
			}
			for (int i = 0; i < Constants.SETTERS_CARDINALITY_ONE.length; i++) {
				if (methodName.startsWith(Constants.SETTERS_CARDINALITY_ONE[i])
						&& methodName.indexOf(',') == -1) {

					// Yann 2004/08/15: Collections...
					// If the parameter type is java.lang.Object, then I am
					// probably dealing with a standard collection API, so
					// I keep the previously found invocation site.
					final String parameterType =
						methodName.substring(
							methodName.indexOf('(') + 1,
							methodName.indexOf(')'));
					if (!Util.isObjectModelRoot(parameterType.toCharArray())) {
						context.setInvocationSiteName(parameterType
							.toCharArray());
					}
					context.setCardinality(Constants.CARDINALITY_ONE);
				}
			}
			for (int i = 0; i < Constants.GETTERS_CARDINALITY_MANY.length; i++) {
				if (methodName
					.startsWith(Constants.GETTERS_CARDINALITY_MANY[i])
						&& methodName.indexOf(',') == -1
						&& methodName.indexOf('(') + 1 < methodName
							.indexOf(')')) {

					// Yann 2004/08/15: Collections...
					// If the parameter type is java.lang.Object, then I am
					// probably dealing with a standard collection API, so
					// I keep the previously found invocation site.
					final String parameterType =
						methodName.substring(
							methodName.indexOf('(') + 1,
							methodName.indexOf(')'));
					if (!Util.isObjectModelRoot(parameterType.toCharArray())) {
						context.setInvocationSiteName(parameterType
							.toCharArray());
						context.setCardinality(Constants.CARDINALITY_MANY);
					}
					else {
						context.setCardinality(Constants.CARDINALITY_ONE);
					}
				}
			}
			for (int i = 0; i < Constants.SETTERS_CARDINALITY_MANY.length; i++) {
				// Yann 2003/11/29: Maximum two arguments!
				// I make sure the setter has only one or two arguments.
				if (methodName
					.startsWith(Constants.SETTERS_CARDINALITY_MANY[i])
						&& (methodName.indexOf(',') == -1 || methodName
							.indexOf(',') == methodName.lastIndexOf(','))) {

					// Yann 2003/11/29: Hashtable.
					// I must take care to handle the case:
					//		put(<Object key>, <Object value>)
					// Yann 2004/08/15: Collections...
					// If the parameter type is java.lang.Object, then I am
					// probably dealing with a standard collection API, so
					// I keep the previously found invocation site.
					final String parameterType =
						methodName.substring(
							Math.max(
								methodName.indexOf('(') + 1,
								methodName.indexOf(',') + 2),
							methodName.indexOf(')'));
					if (!Util.isObjectModelRoot(parameterType.toCharArray())) {
						context.setInvocationSiteName(parameterType
							.toCharArray());
						context.setCardinality(Constants.CARDINALITY_MANY);
					}
					else {
						context.setCardinality(Constants.CARDINALITY_ONE);
					}
				}
			}

			// Yann 2006/07/23: Length
			// I do not remember why I should check
			// the lenght of the name of the invocation
			// site... So I comment it for now...
			//	if (context.getInvocationSiteName().length() > 0
			//		&& !Util.isPrimtiveType(context.getInvocationSiteName())) {

			final char[] invocationSiteName = context.getInvocationSiteName();
			if (!Util.isPrimtiveType(invocationSiteName)
					&& !Utils.isAnonymousOrLocalEntity(invocationSiteName)
					&& !Utils.isSpecialMethod(context.getMethodName())) {

				// Yann 2007/03/02: Noname
				// For some unknown reasons, when analysing 
				// CH.ifa.draw.applet.DrawApplet, an invocation
				// site named "" is created.
				// TODO: Fixed the bug leading to "" invocationSiteName
				final IFirstClassEntity targetEntity;
				if (Arrays.equals(invocationSiteName, new char[0])) {
					targetEntity = context.getCurrentEntity();
				}
				else {
					targetEntity = context.getEntityFromID(invocationSiteName);
				}

				// Yann 2006/02/08: Members!
				// The getEntityFromID() method takes care of members and ghosts...
				//	// Yann 2002/08/01: Ghost!
				//	// From now on, if an entity cannot be found in the
				//	// list of entities, I create a ghost for it...
				//	if (pTargetEntity == null) {
				//		context.addGhost(invocationSiteName);
				//		pTargetEntity =
				//			context.getEntityFromID(invocationSiteName);
				//	}

				final ExtendedMethodInvocation methodInvocation;
				if (context.isInternalFieldDeclaration()) {
					// Aminata and Yann 2011/09/06: Missing case
					// We just realised that for all these years, we were
					// missing the case CLASS_INSTANCE_FROM_FIELD and that
					// the case INSTANCE_CLASS_FROM_FIELD was probably wrong...
					// Luckily, it should not impact to much the AAC analyser.
					// Now, the following code is a clone of the case
					// where the method is called not on a field (see below).
					if (Access.isStatic(context.getMethodAccess())
							&& context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.CLASS_CLASS_FROM_FIELD,
								context.getCardinality(),
								targetEntity);
					}
					else if (!Access.isStatic(context.getMethodAccess())
							&& context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.INSTANCE_CLASS_FROM_FIELD,
								context.getCardinality(),
								targetEntity);
					}
					else if (Access.isStatic(context.getMethodAccess())
							&& !context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.CLASS_INSTANCE_FROM_FIELD,
								context.getCardinality(),
								targetEntity);
					}
					else {
						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD,
								context.getCardinality(),
								targetEntity);
					}

					// Aminata and Yann 2011/09/06: Missing information
					// Because we are lightly analysing the bytecode,
					// we do not do a stack analysis to know the field(s)
					// on which the method is being called.
					// methodInvocation.setCallingField(...)
				}
				else {
					if (Access.isStatic(context.getMethodAccess())
							&& context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.CLASS_CLASS,
								context.getCardinality(),
								targetEntity);
					}
					else if (!Access.isStatic(context.getMethodAccess())
							&& context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.INSTANCE_CLASS,
								context.getCardinality(),
								targetEntity);
					}
					else if (Access.isStatic(context.getMethodAccess())
							&& !context.isInvocationSiteStatic()) {

						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.CLASS_INSTANCE,
								context.getCardinality(),
								targetEntity);
					}
					else {
						methodInvocation =
							new ExtendedMethodInvocation(
								this.factory,
								context.getCurrentEntity(),
								context.getCurrentMethod(),
								IMethodInvocation.INSTANCE_INSTANCE,
								context.getCardinality(),
								targetEntity);
					}
				}

				context.addMethodInvocation(methodInvocation);
			}
		}
	}
	private void handleNew(final Context context, final int offset) {
		final String invocationSiteDisplayName =
			context
				.getConstantPool()
				.get(
					ByteArray.getShortAtOffset(context.getRawCode(), offset + 1))
				.getAsJava();
		final char[] invocationSiteName =
			invocationSiteDisplayName.toCharArray();

		if (!Util.isPrimtiveType(invocationSiteName)
				&& !Utils.isAnonymousOrLocalEntity(invocationSiteName)
				&& !Utils.isSpecialMethod(context.getMethodName())) {

			final IFirstClassEntity pTargetEntity =
				context.getEntityFromID(invocationSiteName);
			// Yann 2006/02/08: Members!
			// The getEntityFromID() method takes care of members and ghosts...
			//	// Yann 2002/08/01: Ghost!
			//	// From now on, if an entity cannot be found in the
			//	// list of entities, I create a ghost for it...
			//	if (pTargetEntity == null) {
			//		context.addGhost(invocationSiteName);
			//		pTargetEntity = context.getEntityFromID(invocationSiteName);
			//	}

			// Yann 2013/08/13: Cardinality of the instantiated class
			// I check if the class is a collection rather than asking
			// the context of the cardinality of the creation... but
			// ask the context of the class is not a collection, just
			// in case...
			final int cardinality;
			if (Util.isArrayOrCollection(pTargetEntity)) {
				cardinality = Constants.CARDINALITY_MANY;
			}
			else {
				cardinality = context.getCardinality();
			}
			
			context.addMethodInvocation(new ExtendedMethodInvocation(
				this.factory,
				context.getCurrentEntity(),
				context.getCurrentMethod(),
				IMethodInvocation.INSTANCE_CREATION,
				cardinality,
				pTargetEntity));
		}
	}
	public void handleRequest(final int offset) {
		final int opCode =
			ByteArray.getByteAtOffset(
				this.getCurrentContext().getRawCode(),
				offset);

		switch (opCode) {
		/*
		 * The case of instantiation.
		 */
			case JVMConstants.ANEWARRAY :
			case JVMConstants.ANEWARRAY_QUICK :
				this.getCurrentContext().setCardinality(
					Constants.CARDINALITY_MANY);
				this.handleNew(this.getCurrentContext(), offset);
				break;

			case JVMConstants.NEW :
			case JVMConstants.NEW_QUICK :
				this.getCurrentContext().setCardinality(
					Constants.CARDINALITY_ONE);
				this.handleNew(this.getCurrentContext(), offset);
				break;

			/*
			 * The case of the instanceof keyword.
			 */
			case JVMConstants.INSTANCEOF :
			case JVMConstants.INSTANCEOF_QUICK :
				this.handleInstanceOf(this.getCurrentContext(), offset);
				break;

			/*
			 * Invocation of a static method.
			 */
			case JVMConstants.INVOKESTATIC :
				this.handleInvokeStatic(this.getCurrentContext(), offset);
				break;

			/*
			 * A local variable is put on the stack.
			 */
			case JVMConstants.ALOAD_0 :
			case JVMConstants.ALOAD :
			case JVMConstants.ALOAD_1 :
			case JVMConstants.ALOAD_2 :
			case JVMConstants.ALOAD_3 :
				this.handleALoad(this.getCurrentContext(), offset);
				break;

			//		/*
			//		 * Something is put on the stack.
			//		 */
			//	case JVMConstants.ACONST_NULL :
			//	case JVMConstants.ICONST_M1 :
			//	case JVMConstants.ICONST_0 :
			//	case JVMConstants.ICONST_1 :
			//	case JVMConstants.ICONST_2 :
			//	case JVMConstants.ICONST_3 :
			//	case JVMConstants.ICONST_4 :
			//	case JVMConstants.ICONST_5 :
			//	case JVMConstants.LCONST_0 :
			//	case JVMConstants.LCONST_1 :
			//	case JVMConstants.FCONST_0 :
			//	case JVMConstants.FCONST_1 :
			//	case JVMConstants.DCONST_0 :
			//	case JVMConstants.DCONST_1 :
			//	case JVMConstants.BIPUSH :
			//	case JVMConstants.SIPUSH :
			//		{
			//			final Context newContext =
			//				new Context(this.getCurrentContext());
			//			newContext.setCardinality(1);
			//			newContext.setInvocationSiteStatic(false);
			//			this.contexes.add(0, newContext);
			//		}
			//		break;

			case JVMConstants.IALOAD :
			case JVMConstants.LALOAD :
			case JVMConstants.FALOAD :
			case JVMConstants.DALOAD :
			case JVMConstants.AALOAD :
			case JVMConstants.BALOAD :
			case JVMConstants.CALOAD :
			case JVMConstants.SALOAD :
				this.getCurrentContext().setCardinality(
					Constants.CARDINALITY_MANY);
				break;

			/*
			 * An instance field is put on the stack.
			 */
			case JVMConstants.GETFIELD :
			case JVMConstants.GETFIELD_QUICK :
			case JVMConstants.GETFIELD_QUICK_W :
				{
					final Context newContext =
						new Context(this.getCurrentContext());
					this.contexes.add(0, newContext);
					this.handleGetField(this.getCurrentContext(), offset);
				}
				break;

			/*
			 * A static field is put on the stack.
			 */
			case JVMConstants.GETSTATIC :
			case JVMConstants.GETSTATIC_QUICK :
				{
					final Context newContext =
						new Context(this.getCurrentContext());
					this.contexes.add(0, newContext);
					this.handleGetStatic(newContext, offset);
				}
				break;

			/*
			 * Invocation of an instance method.
			 */
			case JVMConstants.INVOKEINTERFACE :
			case JVMConstants.INVOKEVIRTUAL :
				this.handleInvokeVirtual(this.popCurrentContext(), offset);
				break;

			default :
				break;
		}
	}
	public List listOfMethodInvocations() {
		return this.methodInvocations;
	}
	private Context popCurrentContext() {
		// Yann 2012/02/02
		// Very strange: a "pop" method that includes an "add"
		// and that pops the first element, not the last...
		// TODO: Revise!
		if (this.contexes.size() == 1) {
			this.contexes.add(new Context(this.getCurrentContext()));
		}
		//	return (Context) this.contexes.remove(this.contexes.size() - 1);
		return (Context) this.contexes.remove(0);
	}
	public void initialise(
		final IFirstClassEntity anEntity,
		final ExtendedMethodInfo aMethodInfo) {

		this.contexes = new ArrayList();
		this.methodInvocations = new ArrayList();

		this.primaryContext.initialise(
			anEntity,
			aMethodInfo,
			this.methodInvocations);
		this.contexes.add(this.primaryContext);
	}
}
