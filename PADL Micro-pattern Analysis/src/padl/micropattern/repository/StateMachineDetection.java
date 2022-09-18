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
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.micropattern.IMicroPatternDetection;

public final class StateMachineDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "StateMachineDetection";
	}

	/*
	 *  21. State Machine. It is not uncommon for an interface to define
	 *	only parameterless methods. Such an interface allows client code to
	 *	either query the state of the object, or, request the object to change
	 *	its state in some predefined manner. Since no parameters are passed,
	 *	the way the object changes is determined entirely by the object’s
	 *	dynamic type.
	 *	This sort of interface, captured by the State Machine pattern, is typical
	 *	for state machine classes.
	 *	For example, the interface java.util.Iterator describes the
	 *	protocol of the standard JAVA iterator, which is actually a state machine
	 *	that has two possible transitions: next() and remove().
	 *	The third method, hasNext() is a query that tests whether the
	 *	iteration is complete. In the state machine analogy, this query is
	 *	equivalent for checking if the machine’s final state was reached.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Must be an interface
		if (anEntity instanceof IInterface) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Only method without parameter is allowed
				if (anOtherEntity instanceof IOperation) {
					final Iterator iter =
						((IOperation) anOtherEntity)
							.getIteratorOnConstituents(IParameter.class);
					if (iter.hasNext()) {
						return false;
					}
				}
			}
			this.addEntities(anEntity);
			return true;
		}
		return false;
	}
}
