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

public final class CanopyDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "CanopyDetection";
	}

	/*
	 *  14. Canopy. A Canopy is a class with exactly one instance field
	 *	which can only changed by the constructors of this cass.
	 *	     The name Canopy draws from the visual association of a trans-
	 *	     parent enclosure set over a precious object; an enclosure which
	 *	     makes it possible to see, but not touch, the protected item.
	 *	Class Integer, which boxes an immutable int field, is a famous
	 *	example of Canopy.
	 *	As explained above, since the Canopy pattern captures immutable
	 *	classes, it also belongs in the Degenerate State category.
	 */

	private List modifiedField = new ArrayList();

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbInstanceField = 0;

		// Only Class can be Restricted Immutable
		if (anEntity instanceof IClass) {
			this.buildMethodModifiedFields(anEntity);

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext() && nbInstanceField <= 1) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IField) {
					if (!((IField) anOtherEntity).isStatic()) {
						nbInstanceField++;

						if (this.modifiedField.contains(anOtherEntity)) {
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
			final Object constituent = iter.next();
			if (constituent instanceof IMethod) {
				if (!((IMethod) constituent)
					.getDisplayID()
					.startsWith("<init>"))
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
				final IMethodInvocation aMethodInvocation =
					(IMethodInvocation) entity;
				final IOperation aCalledMethod =
					aMethodInvocation.getCalledMethod();
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
