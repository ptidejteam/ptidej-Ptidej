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

public final class CommonStateDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "CommonStateDetection";
	}

	/*
	 *  9. Common State. At the next level of complexity, stand classes
	 *	that maintain state, but this state is shared by all of their instances.
	 *	Specifically, a class that has no instance fields, but at least one static
	 *	field is a Common State.
	 *	For example, the class System manages (among other things) the
	 *	global input, output, and error streams.
	 *	A Common State with no instance methods is in fact an incarnation
	 *	of the modular programming paradigm in the JAVA world.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbStaticField = 0;

		// Only Class can be Commen State
		if (anEntity instanceof IClass) {
			final Iterator iterator =
				anEntity.getIteratorOnConstituents(IField.class);

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// All field must be static
				if (anOtherEntity instanceof IField) {
					if (!((IField) anOtherEntity).isStatic()) {
						return false;
					}
					else {
						nbStaticField++;
					}
				}
			}

			// Must have at least one static field
			if (nbStaticField >= 1) {
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
