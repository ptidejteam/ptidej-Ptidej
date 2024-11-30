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
package padl.analysis.repository.aacrelationships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IContainerAggregation;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import padl.kernel.ISetter;
import padl.kernel.impl.Factory;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/04
 */
// TODO: Clean up this code further to remove the Map parameters
// and transform them into real instance variables.
final class ContainerRelationshipAnalyzer {
	private static final int IS_GETTER = 1;
	private static final int IS_SETTER = 2;

	private static IMethod createGetterOrSetter(
		final IMethod aMethod,
		final int aType,
		final int aCardinality) {

		switch (aType) {
			case ContainerRelationshipAnalyzer.IS_GETTER :
				final IGetter newGetter =
					Factory.getInstance().createGetter(aMethod);
				newGetter.setCardinality(aCardinality);
				return newGetter;
			case ContainerRelationshipAnalyzer.IS_SETTER :
				final ISetter newSetter =
					Factory.getInstance().createSetter(aMethod);
				newSetter.setCardinality(aCardinality);
				return newSetter;
		}

		return null;
	}
	private static void findPairsMany(
		final IFirstClassEntity anEclosingEntity,
		final Map aMapOfSetters,
		final Map aMapOfGetters) {

		// Yann 2013/07/09: In good order!
		// I used to use HashMap but the test 
		// padl.creator.classfile.test.visitor.Composite1
		// would sometimes fail because of ordering of the
		// getter/setter vs. BCR, so I now use a Linked
		// HashMap and the problem went away :-)
		final Map mapMethodEtter = new LinkedHashMap();

		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			anEclosingEntity.getIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {
			final IMethod element = (IMethod) iterator.next();

			final IMethod currentMethod = (IMethod) element;
			final int visibility = currentMethod.getVisibility();
			final String returnType = currentMethod.getDisplayReturnType();
			final String currentName = currentMethod.getDisplayName();
			final char[][] arguments =
				ContainerRelationshipAnalyzer.getArgumentTypes(currentMethod);

			// Yann 2005/08/07: Getters and Setters!
			if (ContainerRelationshipAnalyzer.isGettersMany(
				aMapOfGetters,
				visibility,
				returnType,
				currentName,
				arguments)) {

				mapMethodEtter.put(currentMethod, ContainerRelationshipAnalyzer
					.createGetterOrSetter(
						currentMethod,
						ContainerRelationshipAnalyzer.IS_GETTER,
						Constants.CARDINALITY_MANY));
			}
			else if (ContainerRelationshipAnalyzer.isSettersMany(
				aMapOfSetters,
				visibility,
				returnType,
				currentName,
				arguments)) {

				mapMethodEtter.put(currentMethod, ContainerRelationshipAnalyzer
					.createGetterOrSetter(
						currentMethod,
						ContainerRelationshipAnalyzer.IS_SETTER,
						Constants.CARDINALITY_MANY));
			}
		}

		ContainerRelationshipAnalyzer.replaceMethodByGetterOrSetter(
			anEclosingEntity,
			mapMethodEtter);
	}
	private static void findPairsOne(
		final IFirstClassEntity anEclosingEntity,
		final Map aMapOfSetters,
		final Map aMapOfAdders) {

		final Map mapMethodEtter = new HashMap();

		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			anEclosingEntity.getIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {
			final IMethod element = (IMethod) iterator.next();

			final IMethod currentMethod = (IMethod) element;
			final int visibility = currentMethod.getVisibility();
			final String returnType = currentMethod.getDisplayReturnType();
			final String currentName = currentMethod.getDisplayName();
			final char[][] arguments =
				ContainerRelationshipAnalyzer.getArgumentTypes(currentMethod);

			// Yann 2005/08/07: Getters and Setters!
			if (ContainerRelationshipAnalyzer.isGettersOne(
				aMapOfAdders,
				visibility,
				returnType,
				currentName,
				arguments)) {

				mapMethodEtter.put(currentMethod, ContainerRelationshipAnalyzer
					.createGetterOrSetter(
						currentMethod,
						ContainerRelationshipAnalyzer.IS_GETTER,
						Constants.CARDINALITY_ONE));
			}
			else if (ContainerRelationshipAnalyzer.isSettersOne(
				aMapOfSetters,
				visibility,
				returnType,
				currentName,
				arguments)) {

				mapMethodEtter.put(currentMethod, ContainerRelationshipAnalyzer
					.createGetterOrSetter(
						currentMethod,
						ContainerRelationshipAnalyzer.IS_SETTER,
						Constants.CARDINALITY_ONE));
			}
		}

		ContainerRelationshipAnalyzer.replaceMethodByGetterOrSetter(
			anEclosingEntity,
			mapMethodEtter);
	}
	private static char[][] getArgumentTypes(final IMethod currentMethod) {
		final List argumentList = new ArrayList();
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			currentMethod.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			argumentList.add(((IParameter) iterator.next()).getTypeName());
		}
		final char[][] arguments = new char[argumentList.size()][];
		argumentList.toArray(arguments);
		return arguments;
	}
	private static boolean isGettersMany(
		final Map aMapOfRemovers,
		final int visibility,
		final String returnType,
		final String currentName,
		final char[][] arguments) {

		// Yann 2005/08/07: Getters and Setters!
		// I return whether or not the current method is a getter or
		// a setter so that I can replace the actual IMethod by an
		// appropriate instances of ISetter or IGetter.
		for (int i = 0; i < Constants.GETTERS_CARDINALITY_MANY.length; i++) {
			if (currentName.startsWith(Constants.GETTERS_CARDINALITY_MANY[i])
					&& arguments.length == Constants.GETTERS_MAX_ARGS_CARDINALITY_MANY[i]) {

				// Yann 2003/11/28: Collection.
				// I now handle the case of:
				//		remove(int)
				if (arguments.length == 0) {
					aMapOfRemovers.put(
						currentName
							.substring(Constants.GETTERS_CARDINALITY_MANY[i]
								.length())
								+ ":void",
						new Integer(visibility));
				}
				else if (Util.isPrimtiveType(arguments[0])) {
					aMapOfRemovers.put(
						currentName
							.substring(Constants.GETTERS_CARDINALITY_MANY[i]
								.length())
								+ ':' + returnType,
						new Integer(visibility));
				}
				else {
					aMapOfRemovers.put(
						currentName
							.substring(Constants.GETTERS_CARDINALITY_MANY[i]
								.length())
								+ ":" + String.valueOf(arguments[0]),
						new Integer(visibility));
				}

				return true;
			}
		}
		return false;
	}
	private static boolean isGettersOne(
		final Map aMapOfRemovers,
		final int visibility,
		final String returnType,
		final String currentName,
		final char[][] arguments) {

		// Yann 2003/11/28: Generalization!
		// Names of the getters and setters are now in the
		// Constants interface.
		// Yann 2005/08/07: Getters and Setters!
		// I return whether or not the current method is a getter or
		// a setter so that I can replace the actual IMethod by an
		// appropriate instances of ISetter or IGetter.
		for (int i = 0; i < Constants.GETTERS_CARDINALITY_ONE.length; i++) {
			if (currentName.startsWith(Constants.GETTERS_CARDINALITY_ONE[i])
					&& arguments.length == Constants.GETTERS_MAX_ARGS_CARDINALITY_ONE[i]) {

				aMapOfRemovers.put(
					currentName.substring(Constants.GETTERS_CARDINALITY_ONE[i]
						.length()) + ':' + returnType,
					new Integer(visibility));

				return true;
			}
		}

		return false;
	}
	private static boolean isSettersMany(
		final Map aMapOfAdders,
		final int visibility,
		final String returnType,
		final String currentName,
		final char[][] arguments) {

		// Yann 2005/08/07: Getters and Setters!
		// I return whether or not the current method is a getter or
		// a setter so that I can replace the actual IMethod by an
		// appropriate instances of ISetter or IGetter.
		for (int i = 0; i < Constants.SETTERS_CARDINALITY_MANY.length; i++) {
			// Yann 2002/08/21: Collection.
			// I now handle the case of:
			//		add(int, <Type>)
			// Yann 2003/11/28: Hashtable.
			// I now handle the case of:
			//		put(<Object key>, <Object value>)
			if (currentName.startsWith(Constants.SETTERS_CARDINALITY_MANY[i])
					&& arguments.length == Constants.SETTERS_MAX_ARGS_CARDINALITY_MANY[i]) {

				if (arguments.length > 0) {
					aMapOfAdders.put(
						currentName
							.substring(Constants.SETTERS_CARDINALITY_MANY[i]
								.length())
								+ ':'
								+ String
									.valueOf(arguments[arguments.length - 1]),
						new Integer(visibility));
				}
				else {
					aMapOfAdders.put(
						currentName
							.substring(Constants.SETTERS_CARDINALITY_MANY[i]
								.length())
								+ ":void",
						new Integer(visibility));
				}

				return true;
			}
		}

		return false;
	}
	private static boolean isSettersOne(
		final Map aMapOfAdders,
		final int visibility,
		final String returnType,
		final String currentName,
		final char[][] arguments) {

		// Yann 2003/11/28: Generalization!
		// Names of the getters and setters are now in the
		// Constants interface.
		// Yann 2005/08/07: Getters and Setters!
		// I return whether or not the current method is a getter or
		// a setter so that I can replace the actual IMethod by an
		// appropriate instances of ISetter or IGetter.
		for (int i = 0; i < Constants.SETTERS_CARDINALITY_ONE.length; i++) {
			if (currentName.startsWith(Constants.SETTERS_CARDINALITY_ONE[i])
					&& arguments.length == Constants.SETTERS_MAX_ARGS_CARDINALITY_ONE[i]) {
				// Yann 2006/02/23: Primitive types.
				// Why bother checking if it is a primitive type?
				//	&& !Util.isPrimtiveType(arguments[0])) {

				aMapOfAdders.put(
					currentName.substring(Constants.SETTERS_CARDINALITY_ONE[i]
						.length()) + ':' + String.valueOf(arguments[0]),
					new Integer(visibility));

				return true;
			}
		}

		return false;
	}
	private static void matchPairs(
		final IFactory aFactory,
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEnclosingEntity,
		final Map aMapOfSetters,
		final Map aMapOfGetters,
		final IAccessorsData someAccessorsData) {

		// Now I look for matching setter + getter pairs.
		final Iterator keys = aMapOfSetters.keySet().iterator();
		while (keys.hasNext()) {
			final String addKey = (String) keys.next();
			// Skip any "add" which doesn't have a matching "remove".

			if (aMapOfGetters.get(addKey) != null) {
				boolean matched = false;

				// Find a private Vector type Field.
				final Iterator iterator =
					anEnclosingEntity.getIteratorOnConstituents(IField.class);
				while (iterator.hasNext() && !matched) {
					final IField element = (IField) iterator.next();

					final IField currentField = (IField) element;
					final String targetName =
						addKey.substring(addKey.indexOf(':') + 1);

					// We admit that an association may be realised
					// if the associated field is either private
					// or default protected.
					// Yann 2006/02/23: Primitive type
					// ... and not a primitive type.
					if (!Util.isPrimtiveType(targetName.toCharArray())
							&& someAccessorsData.matches(
								targetName,
								currentField)) {

						// Matched.
						matched = true;

						// Yann 2002/08/21: Ghost!
						// I must handle a possible ghost now.
						// Yann 2006/02/23: Ghost...
						// There should be no ghost because the target
						// name come from consistent methods/fields...
						final IFirstClassEntity targetEntity =
							(IFirstClassEntity) anAbstractModel
								.getTopLevelEntityFromID(targetName);
						//	if (targetEntity == null) {
						//		try {
						//			targetEntity =
						//				aFactory.createGhost(targetName);
						//			anIdiomLevelModel.addConstituent(targetEntity);
						//		}
						//		catch (final ModelDeclarationException pde) {
						//		}
						//	}

						// Yann 2006/02/25: Consistency...
						// Methods and Fields use String to encode their types.
						// Thus, a Field can have "a$b" for type.
						// This leads to inconsistencies that must be corrected
						// by replacing String with IConstituents.
						// TODO: Fix the inconsistency. The following test should not exist.
						if (targetEntity != null) {
							final IContainerAggregation aggregation =
								aFactory
									.createContainerAggregationRelationship(
										(currentField.getDisplayName() + "_ContainerAggregation")
											.toCharArray(),
										targetEntity,
										someAccessorsData.getCardinality());
							aggregation.setVisibility(((Integer) aMapOfGetters
								.get(addKey)).intValue());
							anEnclosingEntity.addConstituent(aggregation);
						}
					}
				}
			}
		}
	}
	public static void recognizeContainerAggregations(
		final IFirstClassEntity anEclosingEntity,
		final IAbstractModel anAbstractModel) {

		final Map setters = new HashMap();
		final Map getters = new HashMap();

		// Aggregations with cardinality one.
		ContainerRelationshipAnalyzer.findPairsOne(
			anEclosingEntity,
			setters,
			getters);
		ContainerRelationshipAnalyzer.matchPairs(
			anAbstractModel.getFactory(),
			anAbstractModel,
			anEclosingEntity,
			setters,
			getters,
			new CadinalityOneAccessorsData());

		setters.clear();
		getters.clear();

		// Aggregations with cardinality two and more.
		// We look for all suitable "add" and "remove" methods.
		ContainerRelationshipAnalyzer.findPairsMany(
			anEclosingEntity,
			setters,
			getters);
		ContainerRelationshipAnalyzer.matchPairs(
			anAbstractModel.getFactory(),
			anAbstractModel,
			anEclosingEntity,
			setters,
			getters,
			new CardinalityManyAccessorsData(anAbstractModel));
	}
	private static void replaceMethodByGetterOrSetter(
		final IFirstClassEntity anEclosingEntity,
		final Map mapMethodEtter) {

		// Yann 2005/08/07: Getters and Setters!
		// I cannot co-modify the list of elements of an entity
		// while I traverse it, so I take care of replacing
		// methods by appropriate getters and setters now.
		final Iterator iterator = mapMethodEtter.keySet().iterator();
		while (iterator.hasNext()) {
			final IMethod method = (IMethod) iterator.next();
			final IMethod getterOrSetter = (IMethod) mapMethodEtter.get(method);
			if (getterOrSetter != null) {
				anEclosingEntity.removeConstituentFromID(method.getID());
				anEclosingEntity.addConstituent(getterOrSetter);
			}
		}
	}
	private ContainerRelationshipAnalyzer() {
	}
}
