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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.micropattern.IMicroPatternDetection;

public final class CompoundBoxDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "CompoundBoxDetection";
	}

	/*
	 *  15. Compound Box. This is a variant of a Box class with exactly one
	 *	non-primitive instance field, and, additionally, one or more primi-
	 *	tive instance fields. The highly popular Vector class matches the
	 *	Compound Box pattern. 
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbPrimitiveField = 0;
		int nbNonPrimitiveField = 0;

		// Only Class can be Restricted Creation
		if (anEntity instanceof IClass) {

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Check for primitive fields
				if (anOtherEntity instanceof IField) {

					// Type of the field
					String type = ((IField) anOtherEntity).getDisplayTypeName();

					// Check if the field is in the model, if not the type is primitive
					if (this.isPrimitive(type)) {
						nbPrimitiveField++;
					}
					else {
						nbNonPrimitiveField++;
					}
				}
			}

			if ((nbNonPrimitiveField == 1) && (nbPrimitiveField > 0)) {
				this.addEntities(anEntity);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	/*
	 * Verify if a type match one of the primitive type defined by java
	 */
	// TODO: Replace with PADL implementation?
	private boolean isPrimitive(final String type) {
		if ((type.equals("boolean")) || (type.equals("char"))
				|| (type.equals("byte")) || (type.equals("short"))
				|| (type.equals("int")) || (type.equals("long"))
				|| (type.equals("float")) || (type.equals("double"))) {
			return true;
		}
		else {
			return false;
		}
	}
}
