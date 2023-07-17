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
import padl.kernel.IInterface;
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class JoinerDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "JoinerDetection";
	}

	/*
	 *	3. Joiner. An empty interface which extends more than one inter-
	 *	face is called a Joiner, since in effect, it joins together the sets of
	 *	members of its parents.
	 *	For example, the interface MouseInputListener joins together
	 *	two other interfaces: interface MouseMotionListener and in-
	 *	terface MouseListener.
	 *	An empty class which implements one or more interfaces is also
	 *	a Joiner. For example, class LinkedHashSet marries together
	 *	class HashSet and three interfaces Cloneable, Serializ-
	 *	able and Set.
	 *
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int numberOfInterface = 0;

		// Class and Interface can be a Joiner
		if ((anEntity instanceof IClass) || (anEntity instanceof IInterface)) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				// No method allowed
				final Object anOtherEntity = iterator.next();
				if (anOtherEntity instanceof IOperation) {
					final IOperation currentMethod = (IOperation) anOtherEntity;
					// Detect static attribute initialization and constructor
					if (!currentMethod.getDisplayName().startsWith("<clinit>")
							&& (!currentMethod
								.getDisplayID()
								.startsWith("<init>"))) {
						return false;
					}
				}
				// No attribute allowed
				if (anOtherEntity instanceof IField) {
					return false;
				}
			}

			// Looks good so far  :)
			// Count the number of implemented interface
			final Iterator myIterator =
				anEntity.getIteratorOnInheritedEntities();
			while (myIterator.hasNext()) {
				final Object anOtherEntity = myIterator.next();
				if (anOtherEntity instanceof IInterface) {
					numberOfInterface++;
				}
			}

			if (anEntity instanceof IClass) {
				final Iterator mIterator =
					((IClass) anEntity).getIteratorOnImplementedInterfaces();
				while (mIterator.hasNext()) {
					final Object anOtherEntity = mIterator.next();
					if (anOtherEntity instanceof IInterface) {
						numberOfInterface++;
					}
				}
			}

			if (numberOfInterface >= 2) {
				this.addEntities(anEntity);
				return true;
			}
		}
		return false;
	}
}
