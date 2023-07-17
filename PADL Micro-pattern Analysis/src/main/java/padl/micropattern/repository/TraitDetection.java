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
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class TraitDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "TraitDetection";
	}

	/*
	 *  20. Trait. The Trait pattern captures abstract classes which have no
	 *	state. Specifically, a Trait class must have no instance fields, and at
	 *	least one abstract method.
	 *	
	 *	The term Trait follows the traits modules of Sch¨arli, Ducasse,
	 *	Nierstrasz and Black [38]. A trait module, found in e.g., the
	 *	SCALA [35] programming language, is a collection of implemented
	 *	methods, but with no underlying state.
	 *	
	 *	For instance, class Number (of package java.lang) provides an
	 *	implementation for two methods: shortValue() and for method
	 *	byteValue(). Other than this implementation, class Number
	 *	expects its subclass to provide the full state and complement the
	 *	implementation as necessary.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbAbstractMethod = 0;

		// Must be an abstract Class
		if (anEntity instanceof IClass && anEntity.isAbstract()) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// No instance field allowed
				if (anOtherEntity instanceof IField
						&& !((IField) anOtherEntity).isStatic()) {
					return false;
				}

				// At least one abstract method
				if (anOtherEntity instanceof IOperation
						&& ((IOperation) anOtherEntity).isAbstract()) {
					nbAbstractMethod++;
				}
			}
			if (nbAbstractMethod >= 1) {
				this.addEntities(anEntity);
				return true;
			}
			return false;
		}
		return false;
	}
}
