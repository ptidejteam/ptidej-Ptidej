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
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class DesignatorDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "DesignatorDetection";
	}

	/*
	 *	1. Designator. The most trivial interface is an empty one. Interest-
	 *	ingly, vacuous interfaces are employed in a powerful programming
	 *	technique, of tagging classes in such a way that these tags can be
	 *	examined at runtime.
	 *	 
	 *	For example, a class that implements the empty interface Clone-
	 *	able indicates (at run time) that it is legal to make a field-for-field
	 *	copy of instances of that class.
	 *	Thus, a Designator micro pattern is an interface which does not de-
	 *	clare any methods, does not define any static fields or methods, and
	 *	does not inherit such members from any of its superinterfaces.
	 *	A class can also be Designator if its definition, as well as the defin-
	 *	itions of all of its ancestors (other than Object), are empty.
	 *	Pattern Designator is the rarest, with only 0.2% prevalence in our
	 *	software corpus. It was included in the catalog because it presents
	 *	an important JAVA technique, which is also easily discernible.
	 */

	// Yann 26/02/20: IGhosts are both IClass and IInterface!
	// I must exclude IGhost when not desirable to be included.
	public boolean detect(final IFirstClassEntity anEntity) {
		if (((anEntity instanceof IClass) || (anEntity instanceof IInterface))
				&& !(anEntity instanceof IGhost)) {

			if (!this.isEmpty(anEntity)) {
				return false;
			}

			if (!isInheritedEntitiesEmpty(anEntity)) {
				return false;
			}

			this.addEntities(anEntity);
			return true;
		}
		return false;
	}

	private boolean isInheritedEntitiesEmpty(final IFirstClassEntity anEntity) {
		// Looks good so far  :)
		final Iterator myIterator = anEntity.getIteratorOnInheritedEntities();
		while (myIterator.hasNext()) {
			final Object anOtherEntity = myIterator.next();
			if (!(anOtherEntity instanceof IGhost)
					&& !((IFirstClassEntity) anOtherEntity).getDisplayName()
							.equals("java.lang.Object")) {
				if (!this.isEmpty((IFirstClassEntity) anOtherEntity)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isEmpty(final IFirstClassEntity anEntity) {
		final Iterator iterator = anEntity.getIteratorOnConstituents();

		while (iterator.hasNext()) {
			final Object anOtherEntity = iterator.next();
			if (anOtherEntity instanceof IOperation) {
				final IOperation currentMethod = (IOperation) anOtherEntity;

				// Detect static attribute initialization
				if (!currentMethod.getDisplayName().equals("<clinit>")) {
					return false;
				}
			}
			if (anOtherEntity instanceof IField) {
				return false;
			}
		}
		return true;
	}
}
