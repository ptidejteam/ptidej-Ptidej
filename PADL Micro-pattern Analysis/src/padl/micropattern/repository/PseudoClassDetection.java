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
import padl.kernel.IMethod;
import padl.micropattern.IMicroPatternDetection;

public final class PseudoClassDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "PseudoClassDetection";
	}

	/*
	 *  24. Pseudo Class. A Pseudo Class is an abstract class, with no instance
	 *	fields, and such that all of its instance methods are abstract;
	 *	static data members and methods are permitted. A Pseudo Class
	 *	could be mechanically rewritten as an interface. For instance, class
	 *	Dictionary, the abstract parent of any class which maps keys to
	 *	values, could be rewritten as an interface.
	 *	Pseudo Class is an “anti-pattern” and is not so common; its prevalence
	 *	is only 0.4%.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Must be an Abstract Class
		if (anEntity instanceof IClass && anEntity.isAbstract()) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Need at least one method
				if (anOtherEntity instanceof IMethod) {
					// All instance method must be abstract
					if (!((IMethod) anOtherEntity).isStatic()
							&& !((IMethod) anOtherEntity).isAbstract()) {
						return false;
					}
				}

				// All field must be static
				if (anOtherEntity instanceof IField
						&& !((IField) anOtherEntity).isStatic()) {
					return false;
				}
			}

			this.addEntities(anEntity);
			return true;
		}
		return false;
	}
}
