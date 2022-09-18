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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.micropattern.IMicroPatternDetection;

public final class PureTypeDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "PureTypeDetection";
	}

	/*
	 *  22. Pure Type. A class that has absolutely no implementation details
	 *	is a Pure Type. Specifically, the requirements are that the class
	 *	is abstract, has no static members, at least one method, all of its
	 *	methods are abstract, and that it has no instance fields. In particular,
	 *	any interface which has at least one method, but no static definitions
	 *	is a Pure Type.
	 *	An example is class BufferStrategy, which is found in package
	 *	java.awt.image.BufferStrategy. As the documentation
	 *	of this class states, it ï¿½represents the mechanism with which to
	 *	organize complex memory . . . ï¿½. The concrete implementation can
	 *	only be fixed in a subclass, since, ï¿½Hardware and software limitations
	 *	determine whether and how a particular buffer strategy can
	 *	be implemented.ï¿½. Indeed, this class has nothing more than four
	 *	abstract methods which concrete subclasses must override.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;

		// Must be an interface or an Abstract Class
		if (anEntity.isAbstract()) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Need at least one method
				if (anOtherEntity instanceof IMethod) {
					// All method need to be abstract
					if (((IMethod) anOtherEntity).isAbstract()) {
						nbMethod++;
					}
					else {
						return false;
					}
				}

				if (anEntity instanceof IInterface) {
					if (anOtherEntity instanceof IField
							&& ((IField) anOtherEntity).isStatic()) {
						return false;
					}
				}
				else {
					// All field must be static
					if (anOtherEntity instanceof IField
							&& !((IField) anOtherEntity).isStatic()) {
						return false;
					}
				}
			}

			if (nbMethod > 0) {
				this.addEntities(anEntity);
				return true;
			}
			return false;
		}
		return false;
	}
}
