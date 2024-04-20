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
package padl.micropattern.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IClass;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class BoxDetection extends AbstractMicroPatternDetection implements
		IMicroPatternDetection {

	public String getName() {
		return "BoxDetection";
	}

	/*
	 * 	13. Box. A Box is class with exactly one instance field. This in-
	 *	stance field is mutated by at least one of the methods, or one of the
	 *	static methods, of the class.
	 *	Class CRC32 (in the java.util.crc package) is an example of
	 *	this micro pattern. Its entire state is represented by a single field
	 *	(int crc), which is mutated by method
	 *	     update(int i)
	 */

	private List modifiedField = new ArrayList();

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbInstanceField = 0;

		// Only Class can be Restricted Creation
		if (anEntity instanceof IClass) {

			this.buildMethodModifiedFields(anEntity);

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext() && nbInstanceField <= 1) {
				final Object anOtherEntity = iterator.next();

				// Only onw instance field allowed
				if (anOtherEntity instanceof IField) {
					if (!((IField) anOtherEntity).isStatic()) {
						nbInstanceField++;

						// This field must modified in one the method of the class
						if (!this.modifiedField.contains(anOtherEntity)) {
							return false;
						}
					}
				}
			}

			if (nbInstanceField == 1) {
				this.addEntities(anEntity);
				return true;
			}
			else
				return false;
		}
		return false;
	}

	public void buildMethodModifiedFields(final IFirstClassEntity aClass) {
		this.modifiedField.clear();

		// Build list of modified field
		final Iterator iter = aClass.getIteratorOnConstituents();
		while (iter.hasNext()) {
			Object constituent = iter.next();
			if (constituent instanceof IMethod) {
				this.scanMethod((IMethod) constituent);
			}
		}
	}

	private void scanMethod(final IMethod aMethod) {
		// Check if the field is assign in this method
		final Iterator iterator = aMethod.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final Object entity = iterator.next();

			// Seach for MethodInvocation
			if (entity instanceof IMethodInvocation) {
				IMethodInvocation aMethodInvocation =
					(IMethodInvocation) entity;
				IOperation aCalledMethod = aMethodInvocation.getCalledMethod();
				if (aCalledMethod != null) {

					// Seach for variable assignation
					if (aCalledMethod.getDisplayName().equals("=")) {
						this.modifiedField.add(aMethodInvocation
							.getFirstCallingField());
					}
				}
			}
		}
	}

}
