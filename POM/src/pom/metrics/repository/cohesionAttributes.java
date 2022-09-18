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
package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class cohesionAttributes extends AbstractMetric implements IMetric,
		IUnaryMetric {

	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final List methods =
			this.classPrimitives.listOfDeclaredMethods(anEntity);
		final List fields =
			this.classPrimitives.listOfInheritedAndImplantedFields(anEntity);

		if (fields.size() == 0) {
			return 0;
		}

		double result = 0;

		final Iterator iterMethod = methods.iterator();
		while (iterMethod.hasNext()) {
			final IOperation aMethod = (IOperation) iterMethod.next();

			result +=
				this.listUsedInheritedAndImplantedFieldsByMethod(
					anEntity,
					aMethod).size();
		}

		return result / (methods.size() * fields.size());
	}
	public String getDefinition() {
		return "Cohesion of the attributes.";
	}
	private List listUsedInheritedAndImplantedFieldsByMethod(
		final IFirstClassEntity anEntity,
		final IOperation aMethod) {

		final List usedFields = new ArrayList();
		final List entityFields =
			this.classPrimitives.listOfInheritedAndImplantedFields(anEntity);

		final Iterator iteratorOnMethodInvocations =
			aMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iteratorOnMethodInvocations.hasNext()) {
			final IMethodInvocation methodInvocation =
				(IMethodInvocation) iteratorOnMethodInvocations.next();

			final Iterator iteratorOnCallingFields =
				methodInvocation.getIteratorOnCallingFields();
			while (iteratorOnCallingFields.hasNext()) {
				final IField aField = (IField) iteratorOnCallingFields.next();
				if (aField != null && entityFields.contains(aField)) {
					usedFields.add(aField);
				}
			}
		}
		return usedFields;
	}
}
