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

import java.util.Iterator;
import padl.kernel.IClass;
import padl.kernel.IConstructor;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.micropattern.IMicroPatternDetection;

public final class SamplerDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "SamplerDetection";
	}

	/*
	 *  12. Sampler. The Sampler matches classes class with at least one
	 *	public constructor, and at least one static field whose type is the
	 *	same as that of the class. These classes allow client code to create
	 *	new instances, but they also provide several predefined instances.
	 *	An example is class Color (in package java.awt) with fields
	 *	such as red, green and blue.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Only Class can be Restricted Creation
		if (anEntity instanceof IClass) {

			final Iterator iterator = anEntity.getIteratorOnConstituents();

			int nbPublicConstructor = 0;
			boolean foundAttribute = false;
			final String className =
				((IFirstClassEntity) anEntity).getDisplayID();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Need at least one public constructor
				if (anOtherEntity instanceof IConstructor) {

					// BIG HACK - WAZZ UP
					// TODO: Remove the hack...
					final IConstructor currentMethod =
						(IConstructor) anOtherEntity;

					if (currentMethod.getDisplayID().startsWith("<init>")) {
						if (currentMethod.isPublic()) {
							nbPublicConstructor++;
						}
					}
					// Need at least one static field of the same type of the class
				}
				else if ((anOtherEntity instanceof IField)
						&& (((IField) anOtherEntity).isStatic())
						&& (((IField) anOtherEntity).getDisplayTypeName()
							.equals(className))) {

					foundAttribute = true;
				}
			}

			if (foundAttribute && nbPublicConstructor > 0) {
				this.addEntities(anEntity);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
}
