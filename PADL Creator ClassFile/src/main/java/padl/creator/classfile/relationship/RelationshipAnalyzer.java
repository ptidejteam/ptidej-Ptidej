/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.classfile.relationship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;
import com.ibm.toad.cfparse.instruction.JVMConstants;
import com.ibm.toad.cfparse.utils.Access;

import padl.creator.classfile.util.ExtendedMethodInfo;
import padl.creator.classfile.util.ExtendedMethodInvocation;
import padl.creator.classfile.util.Utils;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IElement;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.IRelationship;
import padl.kernel.impl.Factory;
import padl.util.Util;
import util.io.ProxyConsole;

/**
 * @author Yann-Gal Guhneuc
 * @since  2004/08/01
 */
public class RelationshipAnalyzer {
	private static final char[] EQUAL_SIGN = "=".toCharArray();
	private static IMethod ASSIGNMENT_METHOD;
	private static Map MapOfIDsEntities;
	private IGhost ghostInSearch;

	private static void addRelationship(final IFirstClassEntity anEntity,
			final IRelationship aRelationship) {

		// Yann 2004/08/07: Duplicates!
		// When adding relationships, I must check for duplicate
		// according to their type (use, association...), their
		// target entity, their visibility, and their multiplicity.
		// However, this check may dramatically decrease performance!
		// TODO: Improve performance by stocking relationships in
		// a hashtable or something...
		final Iterator iterator = anEntity.getIteratorOnConstituents();
		IRelationship duplicateRelationship = null;
		while (iterator.hasNext() && duplicateRelationship == null) {
			final IElement element = (IElement) iterator.next();
			if (element.getClass().equals(aRelationship.getClass())) {
				final IRelationship relationship = (IRelationship) element;
				if (relationship.getTargetEntity()
						.equals(aRelationship.getTargetEntity())
						&& relationship.getCardinality() == aRelationship
								.getCardinality()
						&& relationship.getVisibility() == aRelationship
								.getVisibility()) {

					duplicateRelationship = relationship;
				}
			}
		}

		// Yann 2005/07/11: Duplicate relationships
		// If a relationship is duplicated (existing because of two
		// different piece of code), I concatenate their names.
		if (duplicateRelationship == null) {
			anEntity.addConstituent(aRelationship);
		}
		else {
			final char[] newName = new char[duplicateRelationship
					.getName().length + 1 + aRelationship.getName().length];
			System.arraycopy(duplicateRelationship.getName(), 0, newName, 0,
					duplicateRelationship.getName().length);
			newName[duplicateRelationship.getName().length] = '+';
			System.arraycopy(aRelationship.getName(), 0, newName,
					duplicateRelationship.getName().length + 1,
					aRelationship.getName().length);
			duplicateRelationship.setName(newName);
		}
	}

	private static int computeMethodInvocationTypeDirect(
			final IOperation currentMethod, final IOperation calledMethod) {

		// Yann 2004/07/31: Type.
		// I compute the type and cardinality
		//  of the method invocation.
		if (currentMethod.isStatic()) {
			if (calledMethod.isStatic()) {
				return IMethodInvocation.CLASS_CLASS;
			}
			else {
				return IMethodInvocation.CLASS_INSTANCE;
			}
		}
		else {
			// Yann 2010/01/20: Missing method
			// If there is a discrepancy between the method called
			// on a class and those provided by the class, it is
			// possible that the calledMethod be null... and I
			// assume that it is a instance method, by default.
			if (calledMethod != null && calledMethod.isStatic()) {
				return IMethodInvocation.INSTANCE_CLASS;
			}
			else {
				return IMethodInvocation.INSTANCE_INSTANCE;
			}
		}
	}

	private static int computeMethodInvocationTypeThroughField(
			final IField receivedField, final IOperation calledMethod) {

		// Yann 2004/07/31: Type.
		// I compute the type and cardinality
		//  of the method invocation.
		if (receivedField.isStatic()) {
			if (calledMethod.isStatic()) {
				return IMethodInvocation.CLASS_CLASS_FROM_FIELD;
			}
			else {
				return IMethodInvocation.CLASS_INSTANCE_FROM_FIELD;
			}
		}
		else {
			// Yann 2010/01/20: Missing method
			// If there is a discrepancy between the method called
			// on a class and those provided by the class, it is
			// possible that the calledMethod be null... and I
			// assume that it is a instance method, by default.
			if (calledMethod != null && calledMethod.isStatic()) {
				return IMethodInvocation.INSTANCE_CLASS_FROM_FIELD;
			}
			else {
				return IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD;
			}
		}
	}

	private static char[] computeSignature(final String completeNameMethod) {
		String methodName = completeNameMethod
				.substring(completeNameMethod.indexOf(' ') + 1);
		methodName = methodName.substring(0, methodName.indexOf('.'));
		final String localArgs = completeNameMethod.substring(
				completeNameMethod.indexOf('('),
				completeNameMethod.indexOf(')') + 1);
		return (methodName + localArgs).toCharArray();
	}

	private static String extractFieldName(final String aFieldInfo) {
		final String result = aFieldInfo.substring(aFieldInfo.indexOf(' ') + 1);
		// Yann 2007/01/24: Bug in the givn fieldInfo
		// When analysing the relationships in Java AWT v1.4.2_04
		// at one point, the fieldInfo is actually a method
		// descriptor (erroneously!): 
		//		"getDefaultComponent" "(Ljava/awt/Container;)Ljava/awt/Component;"
		// Thus, the need of the following test:
		if (result.indexOf('.') > -1) {
			return result.substring(0, result.indexOf('.'));
		}
		else {
			return "";
		}
	}

	private static String extractFieldType(final String completeNameField) {
		String fieldType = completeNameField
				.substring(completeNameField.indexOf(" ") + 1);
		fieldType = fieldType.substring(fieldType.indexOf(".") + 1);
		return fieldType;
	}

	private static char[] extractReturnType(final String completeNameMethod) {
		String returnType = completeNameMethod
				.substring(completeNameMethod.indexOf(" ") + 1);
		returnType = returnType.substring(returnType.indexOf(".") + 1,
				returnType.indexOf(" "));
		return returnType.toCharArray();
	}

	public static void initialise(final Map aMapOfIDsEntities) {
		RelationshipAnalyzer.MapOfIDsEntities = aMapOfIDsEntities;
	}

	public static List recogniseRelationship(
			final List aListOfSubmittedConstituents,
			final ICodeLevelModel aCodeLevelModel,
			final boolean storeMethodInvocation) {

		final RelationshipAnalyzer analyzer = new RelationshipAnalyzer(
				aListOfSubmittedConstituents, aCodeLevelModel,
				storeMethodInvocation);

		// Yann 2004/08/02: Explanations.
		// First, I build the method invocations for all methods
		// in the idiom-level model.
		analyzer.addMethodInvocations();

		// Then, I create relationships according to the
		// method invocations created.
		// Yann 2007/10/30: AACRelationshipAnalyse!
		// The building of the relationships is now
		// externalised in a real analysis.
		if (Utils.ENABLE_BUILT_IN_AAC_ANALYSIS) {
			analyzer.createRelationships();
		}

		return aListOfSubmittedConstituents;
	}

	// TODO: This list should be a set.
	private final ICodeLevelModel codeLevelModel;
	private final LightMethodInvocationAnalyzer lightMethodInvocationAnalyzer;
	private final List listOfMethodInvocations;
	private final List listOfSubmittedConstituents;
	private final boolean storeMethodInvocation;

	private RelationshipAnalyzer(final List aListOfSubmittedConstituents,
			final ICodeLevelModel aCodeLevelModel,
			final boolean storeMethodInvocation) {

		this.listOfSubmittedConstituents = aListOfSubmittedConstituents;
		this.codeLevelModel = aCodeLevelModel;
		this.storeMethodInvocation = storeMethodInvocation;

		this.lightMethodInvocationAnalyzer = new LightMethodInvocationAnalyzer(
				this.codeLevelModel.getFactory(), this.codeLevelModel);

		this.listOfMethodInvocations = new ArrayList();
	}

	private void addMethodInvocations() {
		// Yann 2004/06/02: Diry hack!
		// See the construction of method invocations...
		if (RelationshipAnalyzer.ASSIGNMENT_METHOD == null) {
			RelationshipAnalyzer.ASSIGNMENT_METHOD = this.codeLevelModel
					.getFactory().createMethod(RelationshipAnalyzer.EQUAL_SIGN,
							RelationshipAnalyzer.EQUAL_SIGN);
			// Yann 2007/11/13: Clone!
			// I should not forget to prepare a possible clone
			// of this "method" in case I clone a model in
			// which it is used... BAD BAD UGLY CODE!!!
			RelationshipAnalyzer.ASSIGNMENT_METHOD.startCloneSession();
			RelationshipAnalyzer.ASSIGNMENT_METHOD.performCloneSession();
		}

		// A method submitted is a Use if inside the
		// method body there are some methods calls or some fields
		// access (at least one) or some instance creation.
		// A method is also a Use when it returns an
		// object, not a primitive type.
		// Well, all methods (almost) are PLinkingMethods.
		final Iterator iterator = this.listOfSubmittedConstituents.iterator();
		while (iterator.hasNext()) {
			final Object member = iterator.next();
			if (member instanceof ExtendedMethodInfo) {
				final ExtendedMethodInfo methodInfo = (ExtendedMethodInfo) member;
				final IFirstClassEntity currentEntity = Utils
						.getEntityOrCreateGhost(this.codeLevelModel,
								methodInfo.getDeclaringClassName(),
								RelationshipAnalyzer.MapOfIDsEntities);

				// final long startTime = System.currentTimeMillis();
				this.listOfMethodInvocations.addAll(
						this.analyseLightMethodInvocations(currentEntity,
								methodInfo));
				// OutputManager.getCurrentOutputManager().getOutput().println(System.currentTimeMillis() - startTime);

				// TODO: This is not the best place to call computeMethodInvocations(),
				// this method should be called within the recognizeMethod() method.

				// Yann 2004/03/25: Hack!
				// There seems to be some bugs in the computation
				// of method invocations. I surround the method call
				// with a catch block to prevent the failure of tests.
				// Even though, some tests still fail and also an
				// infinite loop occurs in
				// MethodInvocationAnalyer.createCouples(...).
				// Also, the name of method added to Ghost should not
				// include parentheses:
				//     public void <init>()()
				// ->
				//     public void <init>()
				// Yann 2004/08/02: Comment.
				// The previous comment is no longer valid.

				// Yann 2004/07/31: Message types.
				// I know add the method invocations created during the
				// analysis of method bodies to create more relationships
				// if appropriate.
				// Yann 2004/07/30: Object-orientation.
				// The flag "storeMethodInvocation" is *not* object-oriented
				// programming. However, it helps in increasing performances
				// by avoiding to recompute many variables to compute then
				// method invocations.
				this.listOfMethodInvocations
						.addAll(this.analyseDeepMethodInvocations(currentEntity,
								methodInfo));
			}
		}
	}

	private List analyseDeepMethodInvocations(
			final IFirstClassEntity currentEntity,
			final ExtendedMethodInfo methodInfo) {

		final IOperation currentMethod = (IOperation) currentEntity
				.getConstituentFromID(Utils.computeSignature(methodInfo));
		final DeepMethodInvocationAnalyzer analyzer = DeepMethodInvocationAnalyzer
				.getUniqueInstance();
		final List couplesOfUsedAttributes = analyzer.analyzeMethod(methodInfo);

		final List methodInvocations = new ArrayList();
		boolean foundArrayStaticOrNot = false;
		Vector foundFieldStaticOrNot = null;
		IFirstClassEntity foundEntityDeclaringField = null;
		boolean foundArrayLoad = false;
		final Iterator iterAttributes = couplesOfUsedAttributes.iterator();
		while (iterAttributes.hasNext()) {
			final String[] nextCouple = (String[]) iterAttributes.next();
			final boolean isFieldEmpty = Utils
					.isMemberNameEmptyOrEqual(nextCouple[0]);
			final boolean isMethodEmpty = Utils
					.isMemberNameEmpty(nextCouple[1]);

			final IFirstClassEntity entityDeclaringField;
			final IFirstClassEntity entityDeclaringMethod;
			final Vector callingFields;
			final IOperation calledMethod;
			ExtendedMethodInvocation methodInvocation = null;

			// Yann 2012/02/02: Dealing with static arrays!
			// The idea is to remember if a static array is
			// used just before an instance method invocation,
			// then a dedicated IMetodInvocation is created.
			if (nextCouple[1] != null
					&& nextCouple[1].endsWith(":" + JVMConstants.AALOAD)) {

				foundArrayLoad = true;
			}

			if (!isFieldEmpty && !isMethodEmpty
					&& !Utils.isAnonymousOrLocalEntity(nextCouple[0]
							.substring(0, nextCouple[0].indexOf(" "))
							.toCharArray())) {

				callingFields = this.createFieldsFromFieldInfos(nextCouple[0]);

				// Yann 2004/06/02: Field assignment!
				// I want to know when a field is assigned to
				// distinguish data type from ordanary class.
				if (nextCouple[1].equals(
						String.valueOf(RelationshipAnalyzer.EQUAL_SIGN))) {

					calledMethod = RelationshipAnalyzer.ASSIGNMENT_METHOD;
				}
				else {
					calledMethod = this.createOperation(nextCouple[1]);
				}
				entityDeclaringField = Utils.getEntityOrCreateGhost(
						this.codeLevelModel,
						nextCouple[0].substring(0, nextCouple[0].indexOf(" "))
								.toCharArray(),
						RelationshipAnalyzer.MapOfIDsEntities);
				if (nextCouple[1].equals(
						String.valueOf(RelationshipAnalyzer.EQUAL_SIGN))) {

					entityDeclaringMethod = null;
				}
				else {
					final String fieldNameAndType = nextCouple[0]
							.substring(nextCouple[0].indexOf(" ") + 1);

					String fieldType = fieldNameAndType
							.substring(fieldNameAndType.indexOf('.') + 1);
					final int hashCharPos = fieldType.indexOf('#');
					if (hashCharPos > 0) {
						fieldType = fieldType.substring(0, hashCharPos);
					}

					entityDeclaringMethod = Utils.getEntityOrCreateGhost(
							this.codeLevelModel, fieldType.toCharArray(),
							RelationshipAnalyzer.MapOfIDsEntities);
				}
				methodInvocation = new ExtendedMethodInvocation(
						this.codeLevelModel.getFactory(), currentEntity,
						currentMethod,
						// Yann 2012/02/02: Not sure!
						// I believe that we should use the commented code
						// instead of the uncommented code. However, the
						// commented code leads to the creation of more
						// aggregation relationships than expected, see
						// for example the test
						//	padl.creator.test.classfile.example.CreationLink_INSTANCE_CREATION_3
						RelationshipAnalyzer
								.computeMethodInvocationTypeThroughField(
										(IField) callingFields.lastElement(),
										calledMethod),
						((IField) callingFields.lastElement()).getCardinality(),
						entityDeclaringMethod, entityDeclaringField);
				methodInvocation.setCallingField(callingFields);
				methodInvocation.setCalledMethod(calledMethod);
				methodInvocations.add(methodInvocation);
			}
			else if (!isFieldEmpty && isMethodEmpty
					&& !Utils.isAnonymousOrLocalEntity(nextCouple[0]
							.substring(0, nextCouple[0].indexOf(" "))
							.toCharArray())) {

				callingFields = this.createFieldsFromFieldInfos(nextCouple[0]);
				// Yann 2007/01/24: Unneeded test
				// The test of the callingFields being null
				// is only needed because of the bug documented
				// in method extractFieldName()!
				if (callingFields.get(0) == null) {
					break;
				}
				entityDeclaringField = Utils.getEntityOrCreateGhost(
						this.codeLevelModel,
						nextCouple[0].substring(0, nextCouple[0].indexOf(" "))
								.toCharArray(),
						RelationshipAnalyzer.MapOfIDsEntities);
				// Yann 2012/02/02: Null Object!
				// The use of the "null" value below is really ugly
				// and suggest misadapted interface: at least a Null
				// Object should be used in this code.
				// TODO: Remove the use of "null"
				methodInvocation = new ExtendedMethodInvocation(
						this.codeLevelModel.getFactory(), currentEntity,
						currentMethod, IMethodInvocation.OTHER,
						((IField) callingFields.lastElement()).getCardinality(),
						null, entityDeclaringField);
				methodInvocation.setCallingField(callingFields);
				methodInvocations.add(methodInvocation);

				// Yann 2012/02/02: Dealing with static arrays!
				// The idea is to remember if a static array is
				// used just before an instance method invocation,
				// then a dedicated IMetodInvocation is created.
				if (((IField) callingFields.lastElement())
						.getCardinality() == Constants.CARDINALITY_MANY) {
					foundArrayStaticOrNot = true;
					foundFieldStaticOrNot = callingFields;
					foundEntityDeclaringField = entityDeclaringField;
				}
			}
			else if (isFieldEmpty && !isMethodEmpty
					&& !Utils.isAnonymousOrLocalEntity(nextCouple[1]
							.substring(0, nextCouple[1].indexOf(" "))
							.toCharArray())) {

				calledMethod = this.createOperation(nextCouple[1]);
				// Yann 24/12/07: Missing called method
				// It's possible that the called method is missing
				if (calledMethod == null) {
					// TODO Log the missing called method or make
					// sure that they exist or create them now...
					continue;
				}
				entityDeclaringMethod = Utils.getEntityOrCreateGhost(
						this.codeLevelModel,
						nextCouple[1].substring(0, nextCouple[1].indexOf(" "))
								.toCharArray(),
						RelationshipAnalyzer.MapOfIDsEntities);

				// Yann 2012/03/23: New case!
				// Segla found out that in Lucene Core v3.0.3, the callee is
				// actually "long[]", which leads to entityDeclaringMethod being
				// null and thus break the code below...
				if (entityDeclaringMethod != null) {
					// Yann 2012/02/02: Dealing with static arrays!
					// The idea is to remember if a static array is
					// used just before an instance method invocation,
					// then a dedicated IMetodInvocation is created.
					if (foundArrayStaticOrNot && foundArrayLoad
							&& foundFieldStaticOrNot != null) {

						methodInvocation = new ExtendedMethodInvocation(
								this.codeLevelModel.getFactory(), currentEntity,
								currentMethod,
								RelationshipAnalyzer
										.computeMethodInvocationTypeThroughField(
												(IField) foundFieldStaticOrNot
														.lastElement(),
												calledMethod),
								((IField) foundFieldStaticOrNot.lastElement())
										.getCardinality(),
								entityDeclaringMethod,
								foundEntityDeclaringField);
						methodInvocation.setCallingField(foundFieldStaticOrNot);
						methodInvocation.setCalledMethod(calledMethod);
						methodInvocations.add(methodInvocation);

						foundArrayStaticOrNot = false;
						foundFieldStaticOrNot = null;
						foundEntityDeclaringField = null;
						foundArrayLoad = false;
					}
					else {
						// Yann 2012/02/21: Cardinality
						// Thanks to Aminata, we have now clear test about
						// method invocations and we unify the created MIs
						// between the JavaFile Creator and the ClassFile 
						// Creator. From now on, cardinality also matters
						// in the following...

						final int cardinality;
						// I must use getID because I need the package name, not just the simple name.
						if (Util.isArrayOrCollection(
								entityDeclaringMethod.getID())
								|| foundArrayLoad) {

							cardinality = Constants.CARDINALITY_MANY;
						}
						else {
							cardinality = Constants.CARDINALITY_ONE;
						}

						methodInvocation = new ExtendedMethodInvocation(
								this.codeLevelModel.getFactory(), currentEntity,
								currentMethod,
								RelationshipAnalyzer
										.computeMethodInvocationTypeDirect(
												currentMethod, calledMethod),
								cardinality, entityDeclaringMethod);
						methodInvocation.setCalledMethod(calledMethod);
						methodInvocations.add(methodInvocation);
					}
				}
			}

			if (this.storeMethodInvocation && methodInvocation != null) {
				currentMethod
						.addConstituent(methodInvocation.getMethodInvocation());
			}
		}

		return methodInvocations;
	}

	//	private static int computeMethodInvocationType(
	//		final IField lastCallingField,
	//		final IOperation calledMethod) {
	//	
	//		// Aminata and Yann 2011/09/06: Type.
	//		// I compute the type and cardinality
	//		// of the method invocation.
	//		if (lastCallingField.isStatic()) {
	//			if (calledMethod.isStatic()) {
	//				return IMethodInvocation.CLASS_CLASS_FROM_FIELD;
	//			}
	//			else {
	//				return IMethodInvocation.INSTANCE_CLASS_FROM_FIELD;
	//			}
	//		}
	//		else {
	//			if (calledMethod != null && calledMethod.isStatic()) {
	//				return IMethodInvocation.CLASS_INSTANCE_FROM_FIELD;
	//			}
	//			else {
	//				return IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD;
	//			}
	//		}
	//	}
	private List analyseLightMethodInvocations(final IFirstClassEntity anEntity,
			final ExtendedMethodInfo aMethodInfo) {

		final List methodInvocations = new ArrayList();
		// Yann 2004/04/04: Context!
		// The RelationshipAnalyzer builds its 
		// initial context itself.
		//	final Context context =
		//		new Context(
		//			anEntity,
		//			anAbstractLevelModel,
		//			aMethodInfo,
		//			aConstantPool,
		//			messageTypes);
		final AttrInfoList attributeInfoList = aMethodInfo.getAttributes();
		final CodeAttrInfo codeAttributeInfo = (CodeAttrInfo) attributeInfoList
				.get("Code");

		if (codeAttributeInfo != null) {
			final byte[] rawCode = codeAttributeInfo.getCode();
			final ImmutableCodeSegment immutableCodeSegment = new ImmutableCodeSegment(
					rawCode);

			// For each instruction found into the class file.
			//		AbstractState state = DefaultState.getCurrentDefaultState();
			this.lightMethodInvocationAnalyzer.initialise(anEntity,
					aMethodInfo);
			for (int j = 0; j < immutableCodeSegment
					.getNumInstructions(); j++) {
				final int offset = immutableCodeSegment.getOffset(j);
				this.lightMethodInvocationAnalyzer.handleRequest(offset);
				// state.handleRequest(context, offset);
				// state = state.getNextState();
			}
			methodInvocations.addAll(this.lightMethodInvocationAnalyzer
					.listOfMethodInvocations());
		}

		// Yann 2004/08/02: Question!
		// How could the enclosing method be null?
		// In the case aMethodInfo.getName().equals("<init>"), of course!
		// final String methodName = aMethodInfo.getName();
		final IOperation method;
		// Yann 2008/09/05: Is this code necessary?
		// It generates a bug in case a member class is named
		// as its enclosing class because getEntityFromName() will
		// return the member class instead of the constructor!
		// Thanks to Alban for discovering this bug!
		//	if (Utils.isSpecialMethod(methodName)) {
		//		method =
		//			(IAbstractMethod) anEntity.getEntityFromName(
		//				Util.computeSimpleName(anEntity.getName()));
		//	}
		//	else {
		method = (IOperation) anEntity
				.getConstituentFromID(Utils.computeSignature(aMethodInfo));
		//	}
		methodInvocations.addAll(this.analyseParameters(anEntity, method));
		methodInvocations.addAll(this.analyseReturnType(anEntity, method));

		// Yann 2005/08/05: Method invocations!
		// I must not forget to add to the current method
		// its appropriate method invocations, else how could
		// I reproduce the AAC algorithm elsewhere???
		if (this.storeMethodInvocation) {
			final IOperation currentMethod = (IOperation) anEntity
					.getConstituentFromID(Utils.computeSignature(aMethodInfo));

			final Iterator iterator = methodInvocations.iterator();
			while (iterator.hasNext()) {
				final ExtendedMethodInvocation methodInvocation = (ExtendedMethodInvocation) iterator
						.next();

				currentMethod
						.addConstituent(methodInvocation.getMethodInvocation());
			}
		}

		return methodInvocations;
	}

	private List analyseParameters(final IFirstClassEntity anOriginEntity,
			final IOperation aMethod) {

		// Parameters.
		final List methodInvocations = new ArrayList();
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator = aMethod
				.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			if (!padl.util.Util.isPrimtiveType(parameter.getTypeName())) {
				final IEntity targetEntity = parameter.getType();
				if (targetEntity instanceof IFirstClassEntity) {

					// Yann 2006/02/08: Members!
					// The getEntityFromID() method takes care of members and ghosts...
					//	// Yann 2002/08/01: Ghost!
					//	// From now on, if an entity cannot be found in the
					//	// list of entities, I create a ghost for it...
					//	if (targetEntity == null) {
					//		try {
					//			targetEntity =
					//				aFactory.createGhost(parameter.getType());
					//			anAbstractLevelModel.addEntity(targetEntity);
					//		}
					//		catch (final ModelDeclarationException mde) {
					//		}
					//	}

					final ExtendedMethodInvocation methodInvocation;
					if (Access.isStatic(aMethod.getVisibility())) {
						methodInvocation = new ExtendedMethodInvocation(
								this.codeLevelModel.getFactory(),
								anOriginEntity, aMethod,
								IMethodInvocation.CLASS_CLASS,
								parameter.getCardinality(),
								(IFirstClassEntity) targetEntity);
					}
					else {
						methodInvocation = new ExtendedMethodInvocation(
								this.codeLevelModel.getFactory(),
								anOriginEntity, aMethod,
								IMethodInvocation.INSTANCE_CLASS,
								parameter.getCardinality(),
								(IFirstClassEntity) targetEntity);
					}

					// Yann 2004/04/04: Duplicate!
					// Now that we record method invocations,
					// is the duplication test valid still?
					if (!methodInvocations.contains(methodInvocation)) {
						methodInvocations.add(methodInvocation);
					}
				}
			}
		}

		// Yann 2007/11/14: Method invocations!
		// I should NOT forget to add the newly
		// created method invocations to the
		// current method... Well, actually, this
		// increases the number of UseRelationships
		// possibly unduly... and it fails many tests!
		//	iterator = methodInvocations.iterator();
		//	while (iterator.hasNext()) {
		//		final ExtendedMethodInvocation methodInvocation =
		//			(ExtendedMethodInvocation) iterator.next();
		//		final IMethodInvocation mi =
		//			methodInvocation.getMethodInvocation();
		//		try {
		//			if (!aMethod.doesContainConstituentWithID(mi.getID())) {
		//				aMethod.addConstituent(mi);
		//			}
		//
		//		}
		//		catch (final ModelDeclarationException e) {
		//			e.printStackTrace();
		//		}
		//	}

		return methodInvocations;
	}

	private List analyseReturnType(final IFirstClassEntity anEntity,
			final IOperation aMethod) {

		final List methodInvocations = new ArrayList();
		if (aMethod instanceof IMethod) {
			// Return type.
			char[] invocationSiteName = ((IMethod) aMethod).getReturnType();
			String invocationSiteDisplayName = String
					.valueOf(invocationSiteName);
			int cardinality = Constants.CARDINALITY_MANY;
			int index;
			if ((index = invocationSiteDisplayName.indexOf('[')) == -1) {
				cardinality = Constants.CARDINALITY_ONE;
				index = invocationSiteDisplayName.length();
			}
			invocationSiteDisplayName = invocationSiteDisplayName.substring(0,
					index);
			invocationSiteName = invocationSiteDisplayName.toCharArray();

			if (!Util.isPrimtiveType(invocationSiteName)) {
				final IFirstClassEntity targetEntity = Utils
						.getEntityOrCreateGhost(this.codeLevelModel,
								invocationSiteName,
								RelationshipAnalyzer.MapOfIDsEntities);
				// Yann 2006/02/08: Members!
				// The getConstituentFromID() method takes care of members and ghosts...
				//	// Yann 2002/08/01: Ghost!
				//	// From now on, if an entity cannot be found in the
				//	// list of entities, I create a ghost for it...
				//	if (targetEntity == null) {
				//		try {
				//			targetEntity =
				//				aFactory.createGhost(invocationSiteName);
				//			anAbstractLevelModel.addConstituent(targetEntity);
				//		}
				//		catch (final ModelDeclarationException mde) {
				//		}
				//	}

				final ExtendedMethodInvocation methodInvocation;
				if (Access.isStatic(aMethod.getVisibility())) {
					methodInvocation = new ExtendedMethodInvocation(
							this.codeLevelModel.getFactory(), anEntity, aMethod,
							IMethodInvocation.CLASS_INSTANCE, cardinality,
							targetEntity);
				}
				else {
					methodInvocation = new ExtendedMethodInvocation(
							this.codeLevelModel.getFactory(), anEntity, aMethod,
							IMethodInvocation.INSTANCE_INSTANCE, cardinality,
							targetEntity);
				}

				// Yann 2004/04/04: Duplicate!
				// Now that we record method invocations,
				// is the duplication test valid still?
				// (See also method Context.addMessageType().)
				if (!methodInvocations.contains(methodInvocation)) {
					methodInvocations.add(methodInvocation);
				}
			}
		}

		// Yann 2007/11/14: Method invocations!
		// I should NOT forget to add the newly
		// created method invocations to the
		// current method... Well, actually, this
		// increases the number of UseRelationships
		// possibly unduly... and it fails many tests!
		//	final Iterator iterator = methodInvocations.iterator();
		//	while (iterator.hasNext()) {
		//		final ExtendedMethodInvocation methodInvocation =
		//			(ExtendedMethodInvocation) iterator.next();
		//		final IMethodInvocation mi =
		//			methodInvocation.getMethodInvocation();
		//		try {
		//			if (!aMethod.doesContainConsituentWithID(mi.getID())) {
		//				aMethod.addConstituent(mi);
		//			}
		//
		//		}
		//		catch (final ModelDeclarationException e) {
		//			e.printStackTrace();
		//		}
		//	}

		return methodInvocations;
	}

	private IOperation createConstructor(final IFirstClassEntity anOriginEntity,
			final char[] anID) {

		final IConstructor constructor = this.codeLevelModel.getFactory()
				.createConstructor(anID,
						Util.computeSimpleName(anOriginEntity.getName()));

		// Yann 2004/08/02: Analysis of parameters.
		// I don't really need to analyse parameters of newly created
		// methods (in ghosts) because these don't have any parameters.
		this.listOfMethodInvocations
				.addAll(this.analyseParameters(anOriginEntity, constructor));

		return constructor;
	}

	private IOperation createOperation(final String methodData) {
		// Get the method signature, return type, and declaring class name.
		final char[] usedMethodName = RelationshipAnalyzer
				.computeSignature(methodData);
		final char[] methodClassName = methodData
				.substring(0, methodData.indexOf(" ")).toCharArray();
		final char[] returnType = RelationshipAnalyzer
				.extractReturnType(methodData);

		// Get the declaring entity from the abstract level model.

		// Yann 2008/12/15: Arrays!
		// There is the case of the clone() method
		// defined in a java.lang.String[] and
		// returning as expected a java.lang.Object.
		// I handle this case by giving back to Ceasar,
		// what belongs to Ceasar...
		final IFirstClassEntity entityDeclaringMethod;
		if (Util.isArray(methodClassName)) {
			entityDeclaringMethod = Factory.getInstance().createHierarchyRoot();
			RelationshipAnalyzer.MapOfIDsEntities.put(methodClassName,
					entityDeclaringMethod);
			//	Utils.getEntityOrCreateGhost(
			//		this.codeLevelModel,
			//		RelationshipAnalyzer.JAVA_LANG_OBJECT,
			//		RelationshipAnalyzer.MapOfIDsEntities);
		}
		else {
			entityDeclaringMethod = Utils.getEntityOrCreateGhost(
					this.codeLevelModel, methodClassName,
					RelationshipAnalyzer.MapOfIDsEntities);
		}

		// Yann 2006/02/08: Members!
		// The searchForEntity() method takes care of members and ghosts...
		//	IEntity entityDeclaringMethod =
		//		(IEntity) (anAbstractLevelModel.getConstituentFromID(methodClassName));
		//	if (entityDeclaringMethod == null) {
		//		try {
		//			// I create a ghost because the entity is null.
		//			entityDeclaringMethod = aFactory.createGhost(methodClassName);
		//
		//			// The ghost is added to the abstract level model.
		//			anAbstractLevelModel.addConstituent(entityDeclaringMethod);
		//		}
		//		catch (final ModelDeclarationException e) {
		//			e.printStackTrace(
		//				OutputManager.getCurrentOutputManager().getErrorOutput());
		//		}
		//	}

		final IOperation calledMethod = this.findOrCreateMethod(usedMethodName,
				returnType, entityDeclaringMethod);

		if (calledMethod == null) {
			ProxyConsole.getInstance().errorOutput()
					.println("Cannot find called method!");
			ProxyConsole.getInstance().errorOutput().print('\t');
			ProxyConsole.getInstance().errorOutput().print("Method name   :");
			ProxyConsole.getInstance().errorOutput().println(usedMethodName);
			ProxyConsole.getInstance().errorOutput().print('\t');
			ProxyConsole.getInstance().errorOutput().print("Return type   :");
			ProxyConsole.getInstance().errorOutput().println(returnType);
			ProxyConsole.getInstance().errorOutput().print('\t');
			ProxyConsole.getInstance().errorOutput().print("Declaring type: ");
			ProxyConsole.getInstance().errorOutput()
					.println(entityDeclaringMethod.getDisplayName());
			ProxyConsole.getInstance().errorOutput().println(
					"Missing called method can happen if there is a compile error: a method is called but undefined in the class of the instance on which it is called.");
		}

		return calledMethod;
	}

	private Vector createFieldsFromFieldInfos(final String fieldInfos) {
		final Vector callingFields = new Vector();
		final StringTokenizer st = new StringTokenizer(fieldInfos, "#");
		IFirstClassEntity entityDeclaringField = null;
		IField aCallingField = null;
		while (st.hasMoreElements()) {

			final String oneFieldInfo = st.nextToken();
			//Extracts the field name
			final String usedFieldName = RelationshipAnalyzer
					.extractFieldName(oneFieldInfo);

			try {
				//Extracts the entity name that declares the field
				final String entityNameDeclaringField = oneFieldInfo
						.substring(0, oneFieldInfo.indexOf(" "));

				// Gets from the abstract level model the entity within its name
				entityDeclaringField = Utils.getEntityOrCreateGhost(
						this.codeLevelModel,
						entityNameDeclaringField.toCharArray(),
						RelationshipAnalyzer.MapOfIDsEntities);
				// Yann 2006/02/08: Members!
				// The getConstituentFromID() method takes care of members and ghosts...
				//	// Tests if the entity exists. Otherwise, I create a ghost. 
				//	if (entityDeclaringField == null) {
				//		try {
				//			// Extracts the field type
				//			String fieldType =
				//				RelationshipAnalyzer.extractFieldType(
				//					oneFieldInfo);
				//			final int cardinality =
				//				padl.util.Util.isArrayOrCollection(fieldType)
				//					? Constants.CARDINALITY_MANY
				//					: Constants.CARDINALITY_ONE;
				//
				//			// Creates a new Ghost and a field by the Factory
				//			entityDeclaringField =
				//				aFactory.createGhost(entityNameDeclaringField);
				//			aCallingField =
				//				aFactory.createField(
				//					usedFieldName,
				//					fieldType,
				//					cardinality);
				//
				//			// The field is added to the new ghost
				//			entityDeclaringField.addConstituent(aCallingField);
				//			anAbstractLevelModel.addConstituent(entityDeclaringField);
				//
				//		}
				//		catch (final ModelDeclarationException e) {
				//			e.printStackTrace(
				//				OutputManager
				//					.getCurrentOutputManager()
				//					.getErrorOutput());
				//		}
				//	}
				//	else {
				aCallingField = (IField) entityDeclaringField
						.getConstituentFromID(usedFieldName);
				// Yann 2007/01/24: Unneeded test
				// The test of the usedFieldName being empty
				// is only needed because of the bug documented
				// in method extractFieldName()!
				if (aCallingField == null && !usedFieldName.equals("")) {
					final String fieldType = RelationshipAnalyzer
							.extractFieldType(oneFieldInfo);
					final int cardinality = padl.util.Util
							.isArrayOrCollection(fieldType.toCharArray())
									? Constants.CARDINALITY_MANY
									: Constants.CARDINALITY_ONE;

					aCallingField = this.codeLevelModel.getFactory()
							.createField(usedFieldName.toCharArray(),
									usedFieldName.toCharArray(),
									fieldType.toCharArray(), cardinality);
					aCallingField.setVisibility(Access.ACC_PUBLIC);
					entityDeclaringField.addConstituent(aCallingField);
				}
				callingFields.add(aCallingField);
			}
			catch (final Exception e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		return callingFields;
	}

	private IOperation createMethod(final IFirstClassEntity anOriginEntity,
			final char[] anID, final char[] aReturnType, final char[] aName) {

		final IMethod method = this.codeLevelModel.getFactory()
				.createMethod(anID, aName);
		method.setReturnType(aReturnType);

		this.listOfMethodInvocations
				.addAll(this.analyseReturnType(anOriginEntity, method));
		// Yann 2004/08/02: Analysis of parameters.
		// I don't really need to analyse parameters of newly created
		// methods (in ghosts) because these don't have any parameters.
		this.listOfMethodInvocations
				.addAll(this.analyseParameters(anOriginEntity, method));

		return method;
	}

	private void createRelationships() {
		IRelationship relationship = null;
		final Iterator e = this.listOfMethodInvocations.iterator();

		while (e.hasNext()) {
			// This is a Use because it has object references.
			final ExtendedMethodInvocation methodInvocation = (ExtendedMethodInvocation) e
					.next();
			final char[] name = methodInvocation.getEnclosingMethod().getName();

			//	System.err.print(
			//		methodInvocation.getType() == IMethodInvocation.CLASS_CLASS
			//			|| methodInvocation.getType()
			//				== IMethodInvocation.INSTANCE_CLASS);
			//	System.err.print(" ");
			//	System.err.println(methodInvocation.getTargetEntity() != null);

			// Yann 2004/08/02: Assignment.
			// I record assignments as method invocations which
			// name is "=" and target entity is null. I don't
			// create relationships for such method invocations.
			if (methodInvocation.getTargetEntity() != null) {
				switch (methodInvocation.getType()) {
				case IMethodInvocation.CLASS_CLASS:
				case IMethodInvocation.INSTANCE_CLASS:
					// Yann 2002/12/15: Distinction!
					// If the current entity is an interface
					// then we have an agregation relationship,
					// not a use relationship: If an
					// interface returns an object, the concrete
					// implementation must store this object
					// somewhere! So... aggregation!
					// Yann 2003/06/12: Spurious?
					// Well, the preceding comment and its
					// implementation lead to hard-to-understand
					// relationships... So, removed!
					//	if (currentEntity.isAbstract()) {
					//		element =
					//			new Aggregation(
					//				actorName,
					//				messageType.getTargetEntity(),
					//				messageType.getCardinality());
					//	}
					//	else {
					relationship = this.codeLevelModel.getFactory()
							.createUseRelationship(name,
									methodInvocation.getTargetEntity(),
									methodInvocation.getCardinality());
					relationship
							.setVisibility(methodInvocation.getVisibility());
					// Yann 2004/07/31: Origin entity.
					// The current entity is not the origin entity,
					// the origin entity is stored in the message
					// type from now on.
					//	currentEntity.addConstituent(element);
					RelationshipAnalyzer.addRelationship(
							methodInvocation.getOriginEntity(), relationship);
					break;

				case IMethodInvocation.CLASS_INSTANCE:
				case IMethodInvocation.INSTANCE_INSTANCE:
					relationship = this.codeLevelModel.getFactory()
							.createAssociationRelationship(name,
									methodInvocation.getTargetEntity(),
									methodInvocation.getCardinality());
					relationship
							.setVisibility(methodInvocation.getVisibility());
					// Yann 2004/07/31: Origin entity.
					// The current entity is not the origin entity,
					// the origin entity is stored in the message
					// type from now on.
					//	currentEntity.addConstituent(element);
					RelationshipAnalyzer.addRelationship(
							methodInvocation.getOriginEntity(), relationship);
					break;

				case IMethodInvocation.CLASS_CLASS_FROM_FIELD:
				case IMethodInvocation.INSTANCE_CLASS_FROM_FIELD:
				case IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD:
					relationship = this.codeLevelModel.getFactory()
							.createAggregationRelationship(name,
									methodInvocation.getTargetEntity(),
									methodInvocation.getCardinality());
					relationship
							.setVisibility(methodInvocation.getVisibility());
					// Yann 2004/07/31: Origin entity.
					// The current entity is not the origin entity,
					// the origin entity is stored in the message
					// type from now on.
					//	currentEntity.addConstituent(element);
					RelationshipAnalyzer.addRelationship(
							methodInvocation.getOriginEntity(), relationship);
					break;

				case IMethodInvocation.INSTANCE_CREATION:
					relationship = this.codeLevelModel.getFactory()
							.createCreationRelationship(name,
									methodInvocation.getTargetEntity(),
									methodInvocation.getCardinality());
					relationship
							.setVisibility(methodInvocation.getVisibility());
					// Yann 2004/07/31: Origin entity.
					// The current entity is not the origin entity,
					// the origin entity is stored in the message
					// type from now on.
					//	currentEntity.addConstituent(element);
					RelationshipAnalyzer.addRelationship(
							methodInvocation.getOriginEntity(), relationship);
					break;

				case IMethodInvocation.OTHER:
					break;

				default:
					throw new RuntimeException(
							"An instance of MethodInvocation must have a recognized type value (was "
									+ methodInvocation.getType() + ')');
				}
			}
		}
	}

	private IOperation findOrCreateMethod(final char[] usedMethodName,
			final char[] returnType,
			final IFirstClassEntity entityDeclaringMethod) {
		
		// Luca 2026/01/23:
		// Separation between searching and creating a method.
		// Before, we would sometimes create a method too early and interrupt the search
		// which leads to the creation of method where they shouldn't be
		//
		// When searching, we will encounter the ghost where we could create the method if it is not found
		// I keep this ghost in memory so we don't have to do a second search when it comes to
		// actually creating the method (is there a cleaner solution?)
		this.ghostInSearch = null;
		
		IOperation calledMethod = this.searchForMethod(usedMethodName, returnType, entityDeclaringMethod);
		
		if (calledMethod == null && this.ghostInSearch != null) {
			calledMethod = this.createInGhost(usedMethodName, returnType, this.ghostInSearch);
		}
		
		return calledMethod;
	}
	
	private IOperation searchForMethod(final char[] usedMethodName,
			final char[] returnType,
			final IFirstClassEntity entityDeclaringMethod) {

		// Yann 2006/07/29: Compatiblity!
		// There is a compatibility issue between classfiles v1.1-
		// and v1.2+, i.e., compiled with a 1.3- compiler and with
		// a 1.4+ compiler: the methodref pointed by the index in
		// a invokevirtual bytecode has changed. In the case of two
		// classes A and B such as A -<|- B, A defines foo(), B
		// defines bar() that calls this.foo(), the 1.1- bytcode 
		// looks like this (from BCEL):
		//
		//	[Code(max_stack = 1, max_locals = 1, code_length = 5)
		//	0:    aload_0
		//	1:    invokevirtual	padl.test.method.A.foo ()V (17)
		//	4:    return
		//	
		//	Attribute(s) = 
		//	LineNumber(0, 5), LineNumber(4, 6)
		//	LocalVariable(start_pc = 0, length = 5, index = 0:padl.test.method.B this)
		//	]
		//
		// while the 1.2+ looks like that:
		//	[Code(max_stack = 1, max_locals = 1, code_length = 5)
		//	0:    aload_0
		//	1:    invokevirtual	padl.test.method.B.foo ()V (17)
		//	4:    return
		//	
		//	Attribute(s) = 
		//	LineNumber(0, 5), LineNumber(4, 6)
		//	LocalVariable(start_pc = 0, length = 5, index = 0:padl.test.method.B this)
		//	]
		//
		// Note the change in the methodref: with 1.1- it is:
		//		padl.test.method.A.foo
		// while with 1.2+ it is:
		//		padl.test.method.B.foo
		// and that actually makes a hell of a difference,  because we 
		// were assuming that if the method did not exist we should add 
		// it, regardless if this was to a IGhost or a IClass.
		//
		// Thus, with 1.2+ bytecode, class B would define an additional
		// method foo() because of "padl.test.method.B.foo": indeed,
		// method foo() does not exist in B and thus it would be added!
		// I modified the test below to add the "instanceof", which
		// fixes nicely the problem.
		if (this.ghostInSearch == null
				&& !entityDeclaringMethod.doesContainConstituentWithID(usedMethodName)
				&& entityDeclaringMethod instanceof IGhost) {
			this.ghostInSearch = (IGhost) entityDeclaringMethod;
		}
		
		IOperation calledMethod = (IOperation) entityDeclaringMethod
				.getConstituentFromID(usedMethodName);;

		// Yann 2006/08/03: Compatibility!
		// Now that I don't add a method in silly cases, I must
		// look for this method in the hierarchy of the current 
		// entity, because it can be defined higher up in the
		// hierarchy...
		// We are in Java, only one super-class is allowed.
		// Yann 2007/03/16: Interfaces...
		// But we could be dealing with an interface,
		// with no super-interfaces...
		// TODO: Anyhow, it is strange to deal with interfaces here...
		Iterator iterator = entityDeclaringMethod
				.getIteratorOnInheritedEntities();
		if (calledMethod == null && iterator.hasNext()) {
			/*
			 * Alban : 2008/09/30
			 * Iterate on all parents to find method
			 */
			while (iterator.hasNext() && calledMethod == null) {
				calledMethod = this.searchForMethod(usedMethodName, returnType,
						(IFirstClassEntity) iterator.next());
			}
		}

		if (entityDeclaringMethod instanceof IClass) {
			iterator = ((IClass) entityDeclaringMethod)
					.getIteratorOnImplementedInterfaces();
			if (calledMethod == null && iterator.hasNext()) {
				/*
				* Aminata & Venera : 2011/08/19 
				* Iterate on all interfaces to find method
				*/
				while (iterator.hasNext() && calledMethod == null) {
					calledMethod = this.searchForMethod(usedMethodName,
							returnType, (IFirstClassEntity) iterator.next());
				}
			}
		}

		return calledMethod;
	}
	
	private IOperation createInGhost(final char[] usedMethodName,
			final char[] returnType,
			final IGhost aGhost) {
		
		IOperation calledMethod = null;

		// Yann 2004/04/03: Constituent ID!
		// When creating a new method, we give it a unique actorID.
		// We must also give it a name without extra parentheses.
		// TODO: Refactor to remove the call to substring().
		// Yann 2004/04/09: Modifiers!
		// When creating a new method, we must also set its modifier:
		// Test case padl.test.example.Composite1 fails because the
		// print() method built should be set abstract.
		// Yann 2004/05/20: Constructors.
		// If the method is a constructor, its name is
		// its declaring class name.
		final char[] name = ArrayUtils.subarray(usedMethodName, 0,
				ArrayUtils.indexOf(usedMethodName, '('));
		if (Utils.isSpecialMethod(name)) {
			// Creates a new constructor that will be added to the ghost
			calledMethod = this.createConstructor(aGhost,
					usedMethodName);
		}
		else {
			// Creates a new method that will be added to the ghost
			calledMethod = this.createMethod(aGhost,
					usedMethodName, returnType, name);
		}
		aGhost.addConstituent(calledMethod);
		return calledMethod;
	}
}
