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
package pom.primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.util.CacheManager;
import pom.util.NoSuchValueInCacheException;

/**
 * @author Farouk Zaidi
 * @since  2004/02/05
 * 
 * @author Yann
 */
public class MethodPrimitives extends Primitives {
	private static MethodPrimitives UniqueInstance;
	public static MethodPrimitives getInstance() {
		if (MethodPrimitives.UniqueInstance == null) {
			MethodPrimitives.UniqueInstance = new MethodPrimitives();
		}
		return MethodPrimitives.UniqueInstance;
	}
	private final CacheManager cacheManger;

	private MethodPrimitives() {
		super();
		this.cacheManger = CacheManager.getInstance();
	}
	public int numberOfUsesByFieldsOrMethods(
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		try {
			return ((Integer) this.cacheManger.retrievePrimitiveResult(
				"countNumberOfUsesByFieldsOrMethods",
				anEntity,
				anotherEntity)).intValue();
		}
		catch (final NoSuchValueInCacheException e) {
			final List invokedMethod = new ArrayList();
			final List usedFields = new ArrayList();

			final Iterator iteratorOnAbstractMethods =
				anEntity.getIteratorOnConstituents(IOperation.class);
			while (iteratorOnAbstractMethods.hasNext()) {
				final IOperation method =
					(IOperation) iteratorOnAbstractMethods.next();
				invokedMethod.addAll(this.listOfSisterMethodCalledByMethod(
					anotherEntity,
					method));
				usedFields.addAll(this.listOfFieldsUsedByMethod(
					anotherEntity,
					method));
			}

			int result = invokedMethod.size() + usedFields.size();
			this.cacheManger.cachePrimitiveResult(
				"countNumberOfUsesByFieldsOrMethods",
				anEntity,
				anotherEntity,
				Integer.valueOf(result));
			return result;
		}
	}
	/*
	 * Farouk 2004/04/02, 14h52 
	 * Modified to consider multiple access to fields.
	 */
	public List listOfFieldsUsedByMethod(
		final IFirstClassEntity anEntity,
		final IOperation aMethod) {

		try {
			return (List) this.cacheManger.retrievePrimitiveResult(
				"listFieldsUsedByMethod",
				anEntity,
				aMethod);
		}
		catch (final NoSuchValueInCacheException e) {
			final ClassPrimitives classPrimitives =
				ClassPrimitives.getInstance();
			final List usedFields = new ArrayList();
			final List entityFields =
				classPrimitives.listOfImplementedFields(anEntity);

			for (final Iterator iteratorOnConstituents =
				aMethod.getIteratorOnConstituents(); iteratorOnConstituents
				.hasNext();) {
				final IConstituentOfOperation element =
					(IConstituentOfOperation) iteratorOnConstituents.next();
				if (element instanceof IMethodInvocation) {
					final IMethodInvocation mi = (IMethodInvocation) element;

					//				mi.getFieldDeclaringEntity()
					//				mi.getFirstCallingField()

					final Iterator iterCallingField =
						mi.getIteratorOnCallingFields();
					while (iterCallingField.hasNext()) {
						final IField aField = (IField) iterCallingField.next();
						if (aField != null) {
							if (entityFields.contains(aField)) {
								usedFields.add(aField);
							}
						}
					}
				}
			}

			this.cacheManger.cachePrimitiveResult(
				"listFieldsUsedByMethod",
				anEntity,
				aMethod,
				usedFields);
			return usedFields;
		}
	}
	public List listOfSisterMethodCalledByMethod(
		final IFirstClassEntity anEntity,
		final IOperation aMethod) {

		try {
			return (List) this.cacheManger.retrievePrimitiveResult(
				"listOfSisterMethodCalledByMethod",
				anEntity,
				aMethod);
		}
		catch (final NoSuchValueInCacheException e) {
			final List invokedMethods = new ArrayList();
			final Collection concreteMethods =
				ClassPrimitives
					.getInstance()
					.listOfOverriddenAndConcreteMethods(anEntity);

			final Iterator iteratorOnMethodInvocations =
				aMethod.getIteratorOnConstituents(IMethodInvocation.class);
			while (iteratorOnMethodInvocations.hasNext()) {
				final IMethodInvocation mi =
					(IMethodInvocation) iteratorOnMethodInvocations.next();

				if (mi.getTargetEntity() != null
						&& mi.getTargetEntity().equals(anEntity)) {

					if (concreteMethods.contains(mi.getCalledMethod())) {
						invokedMethods.add(mi.getCalledMethod());
					}
				}
			}

			this.cacheManger.cachePrimitiveResult(
				"listOfSisterMethodCalledByMethod",
				anEntity,
				aMethod,
				invokedMethods);
			return invokedMethods;
		}
	}
}
