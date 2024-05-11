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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.creator.classfile.util.AccessorsData;
import padl.creator.classfile.util.CadinalityOneAccessorsData;
import padl.creator.classfile.util.CardinalityManyAccessorsData;
import padl.creator.classfile.util.ExtendedFieldInfo;
import padl.creator.classfile.util.ExtendedMethodInfo;
import padl.creator.classfile.util.Utils;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IContainerAggregation;
import padl.kernel.IFactory;
import padl.kernel.IFirstClassEntity;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/01
 */
public class ContainerRelationshipAnalyzer {
	private static void findPairsMany(
		final List aListOfNotRecognizedConstituents,
		final Map aMapOfAdders,
		final Map aMapOfRemovers) {

		final Iterator iterator = aListOfNotRecognizedConstituents.iterator();
		while (iterator.hasNext()) {
			final Object element = iterator.next();
			if (element instanceof ExtendedMethodInfo) {

				final ExtendedMethodInfo currentMethod =
					(ExtendedMethodInfo) element;
				final int visibility = currentMethod.getVisibility();
				final char[] returnType = currentMethod.getReturnType();
				final char[] currentName = currentMethod.getName();
				final char[][] arguments = currentMethod.getParameters();

				ContainerRelationshipAnalyzer.findPairsMany(
					aMapOfAdders,
					aMapOfRemovers,
					visibility,
					returnType,
					currentName,
					arguments);
			}
		}
	}
	private static void findPairsMany(
		final Map aMapOfAdders,
		final Map aMapOfRemovers,
		final int visibility,
		final char[] returnType,
		final char[] currentName,
		final char[][] arguments) {

		// Yann 2002/08/21: Cumbersome...
		// I remove the following test beacause it is
		// neither really useful nor accurate!
		//	final int modifiers = currentMethod.getAccess();
		// 	final String returnType = currentMethod.getReturnType();
		//	if (Access.isStatic(modifiers)
		//		|| !Access.isPublic(modifiers)
		//		|| (arguments.length > 0 &&
		// !Misc.isPrimtiveType(returnType))
		//		|| (arguments.length == 0
		//			&& Misc.isPrimtiveType(returnType))) {
		//
		//		continue;
		//	}

		// Yann: 01/10/29: Late!
		// Explanation on the following code: We want to make sure the
		// add...() and remove...() methods have the same semantics.
		// We cut their names and compare them. If we have:
		//     addObject()
		// and:
		//		removeObject()
		// This is potentially an association.
		// If we have:
		//		addToSelection()
		// and:
		//		removeFromSelection()
		// This is not an association.

		// Yann: 01/10/29: No! No! No!
		// This code should NOT exist! This code is there only
		// to detect one more aggregation in the JHotDraw framework,
		// in the class StandardDrawingView.

		// Yann 2003/11/28: Generalization!
		// Names of the getters and setters are now in the
		// Constants interface.
		for (int i = 0; i < Constants.GETTERS_CARDINALITY_MANY.length; i++) {
			if (String.valueOf(currentName).startsWith(
				Constants.GETTERS_CARDINALITY_MANY[i])
					&& arguments.length == 1) {

				// Yann 2003/11/28: Collection.
				// I now handle the case of:
				//		remove(int)
				if (Util.isPrimtiveType(arguments[0])) {
					aMapOfRemovers.put(
						String.valueOf(currentName).substring(
							Constants.GETTERS_CARDINALITY_MANY[i].length())
								+ ":" + String.valueOf(returnType),
						Integer.valueOf(visibility));
				}
				else {
					aMapOfRemovers.put(
						String.valueOf(currentName).substring(
							Constants.GETTERS_CARDINALITY_MANY[i].length())
								+ ":" + String.valueOf(arguments[0]),
						Integer.valueOf(visibility));
				}
			}
		}

		for (int i = 0; i < Constants.SETTERS_CARDINALITY_MANY.length; i++) {
			// Yann 2002/08/21: Collection.
			// I now handle the case of:
			//		add(int, <Type>)
			// Yann 2003/11/28: Hashtable.
			// I now handle the case of:
			//		put(<Object key>, <Object value>)
			if (String.valueOf(currentName).startsWith(
				Constants.SETTERS_CARDINALITY_MANY[i])
					&& (arguments.length == 1 || arguments.length == 2)) {

				aMapOfAdders.put(
					String.valueOf(currentName).substring(
						Constants.SETTERS_CARDINALITY_MANY[i].length())
							+ ":"
							+ String.valueOf(arguments[arguments.length - 1]),
					Integer.valueOf(visibility));
			}
		}
	}
	private static void findPairsOne(
		final List aListOfNotRecognizedConstituents,
		final Map aMapOfAdders,
		final Map aMapOfRemovers) {

		final Iterator iterator = aListOfNotRecognizedConstituents.iterator();
		while (iterator.hasNext()) {
			final Object element = iterator.next();
			if (element instanceof ExtendedMethodInfo) {
				final ExtendedMethodInfo currentMethod =
					(ExtendedMethodInfo) element;
				final int visibility = currentMethod.getVisibility();
				final char[] returnType = currentMethod.getReturnType();
				final char[] currentName = currentMethod.getName();
				final char[][] arguments = currentMethod.getParameters();

				ContainerRelationshipAnalyzer.findPairsOne(
					aMapOfAdders,
					aMapOfRemovers,
					visibility,
					returnType,
					currentName,
					arguments);
			}
		}
	}
	private static void findPairsOne(
		final Map aMapOfAdders,
		final Map aMapOfRemovers,
		final int visibility,
		final char[] returnType,
		final char[] currentName,
		final char[][] arguments) {

		// Yann 2002/08/21: Cumbersome...
		// I remove the following test beacause it is
		// neither really useful nor accurate!
		//	final int modifiers = currentMethod.getAccess();
		//	if (Access.isStatic(modifiers)
		//		|| !Access.isPublic(modifiers)
		//		|| arguments.length != 1
		//		|| (arguments.length == 0
		//			&& Misc.isPrimtiveType(returnType))) {
		//
		//		continue;
		//	}

		// Yann 2003/11/28: Generalization!
		// Names of the getters and setters are now in the
		// Constants interface.
		for (int i = 0; i < Constants.GETTERS_CARDINALITY_ONE.length; i++) {
			if (String.valueOf(currentName).startsWith(
				Constants.GETTERS_CARDINALITY_ONE[i])
					&& arguments.length == 0) {

				aMapOfRemovers.put(
					String.valueOf(currentName).substring(
						Constants.GETTERS_CARDINALITY_ONE[i].length())
							+ ":" + String.valueOf(returnType),
					Integer.valueOf(visibility));
			}
		}

		for (int i = 0; i < Constants.SETTERS_CARDINALITY_ONE.length; i++) {
			if (String.valueOf(currentName).startsWith(
				Constants.SETTERS_CARDINALITY_ONE[i])
					&& arguments.length == 1
					&& !Util.isPrimtiveType(arguments[0])) {

				aMapOfAdders.put(
					String.valueOf(currentName).substring(
						Constants.SETTERS_CARDINALITY_ONE[i].length())
							+ ":" + String.valueOf(arguments[0]),
					Integer.valueOf(visibility));
			}
		}
	}
	private static void matchPairs(
		final IFactory aFactory,
		final ICodeLevelModel aCodeLevelModel,
		final List aListOfNotRecognizedConstituents,
		final Map aMapOfAdders,
		final Map aMapOfRemovers,
		final AccessorsData someAccessorsData) {

		// Now I look for matching add + remove pairs.
		final Iterator keys = aMapOfAdders.keySet().iterator();
		while (keys.hasNext()) {
			final String addKey = (String) keys.next();
			// Skip any "add" which doesn't have a matching "remove".

			if (aMapOfRemovers.get(addKey) != null) {
				// Find a private Vector type Field.
				final Iterator iterator =
					aListOfNotRecognizedConstituents.iterator();
				boolean matched = false;

				while (iterator.hasNext() && !matched) {
					final Object element = iterator.next();

					if (element instanceof ExtendedFieldInfo) {
						final ExtendedFieldInfo currentField =
							(ExtendedFieldInfo) element;
						final char[] targetName =
							addKey
								.substring(addKey.indexOf(':') + 1)
								.toCharArray();

						// We admit that an association may be realised
						// if the associated field is either private
						// or default protected.
						// Yann 2006/02/21: Primitive type.
						// I must check if I am not dealing with a primitive type.
						if (someAccessorsData.matches(targetName, currentField)
								&& !Util.isPrimtiveType(targetName)) {

							// Matched.
							matched = true;

							// Yann 2002/08/21: Ghost!
							// I must handle a possible ghost now.
							// Yann 2006/02/21: Ghost...
							// I don't need to deal with Ghost here anymore,
							// because Utils.searchForEntity() takes care of them...
							IFirstClassEntity targetEntity =
								Utils.searchForEntity(
									aCodeLevelModel,
									targetName);
							//	if (targetEntity == null) {
							//		try {
							//			targetEntity =
							//				aFactory.createGhost(targetName);
							//			aCodeLevelModel.addConstituent(targetEntity);
							//		}
							//		catch (final ModelDeclarationException pde) {
							//		}
							//	}

							// Yann 2004/01/25: Dangerous!
							// I remove the removals to make sure
							// I keep as much data as is provided,
							// until everyting is stable again.
							// Make sure to remove unnecessary
							// methods and fields.
							//	aListOfNotRecognizedConstituents.remove(
							//		extendedFieldInfo);
							//	aListOfNotRecognizedConstituents.remove(
							//		setterMethod);
							//	aListOfNotRecognizedConstituents.remove(
							//		getterMethod);
							final IFirstClassEntity firstClassEntity =
								Utils.searchForEntity(
									aCodeLevelModel,
									currentField.getDeclaringClassName());

							// Yann 2003/12/15: Herve!
							// This is legacy code from Herve! Pay it most
							// respect!
							// Meawhile, study if we can safely remove
							// it... :-)
							//	final IContainerAggregation aggregation =
							//		aFactory.createContainerAggregation(
							//			currentField.getName()
							//				+ "_Aggregation_"
							//				+ uniqueID,
							//			target,
							//			pairInformation.getCardinality(),
							//			(IField) entity.getConstituent(
							//				currentField.getName()),
							//			(IMethod) entity.getConstituent(
							//				getterMethod.getName()),
							//			(IMethod) entity.getConstituent(
							//				setterMethod.getName()));

							final IContainerAggregation aggregation =
								aFactory
									.createContainerAggregationRelationship(
										(String.valueOf(currentField.getName()) + "_ContainerAggregation")
											.toCharArray(),
										targetEntity,
										someAccessorsData.getCardinality());
							aggregation.setVisibility(((Integer) aMapOfRemovers
								.get(addKey)).intValue());
							firstClassEntity.addConstituent(aggregation);
						}
					}
				}
			}
		}
	}
	public static List recognizeContainerAggregation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		final Map adds = new HashMap();
		final Map removes = new HashMap();

		// Aggregations with cardinality one.
		ContainerRelationshipAnalyzer.findPairsOne(
			aListOfSubmittedConstituents,
			adds,
			removes);
		ContainerRelationshipAnalyzer.matchPairs(
			aCodeLevelModel.getFactory(),
			aCodeLevelModel,
			aListOfSubmittedConstituents,
			adds,
			removes,
			new CadinalityOneAccessorsData());

		adds.clear();
		removes.clear();

		// Aggregations with cardinality two and more.
		// We look for all suitable "add" and "remove" methods.
		ContainerRelationshipAnalyzer.findPairsMany(
			aListOfSubmittedConstituents,
			adds,
			removes);
		ContainerRelationshipAnalyzer.matchPairs(
			aCodeLevelModel.getFactory(),
			aCodeLevelModel,
			aListOfSubmittedConstituents,
			adds,
			removes,
			new CardinalityManyAccessorsData(aCodeLevelModel));

		return aListOfSubmittedConstituents;
	}
	private ContainerRelationshipAnalyzer() {
	}
}
