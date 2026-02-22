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
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class RecordDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "RecordDetection";
	}

	/*
	 *  16. Record. JAVA makes it possible to define classes which look
	 *	and feel much like PA S C A L [46] record types. A class matches
	 *	the Record micro pattern if all of its fields are public and if
	 *	has no methods other than constructors and methods inherited from
	 *	Object.
	 *	Perhaps surprisingly, there is a considerable number of examples
	 *	of this pattern in the JAVA standard library. For example, in pack-
	 *	age java.sql we find class DriverPropertyInfo which is
	 *	a record managing a textual property passed to a JDBC driver.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Only Class can be Restricted Immutable
		// Yann 26/02/20: IGhosts are both IClass and IInterface!
		// I must exclude IGhost when not desirable to be included.
		if (anEntity instanceof IClass && !(anEntity instanceof IGhost)) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();
				if (anOtherEntity instanceof IField) {
					// All Fields must be "public"
					final IField currentField = (IField) anOtherEntity;
					if (!currentField.isPublic()) {
						return false;
					}
				}

				if (anOtherEntity instanceof IOperation) {
					// Only method inherited from Object or constructor is allowed
					final IOperation currentMethod = (IOperation) anOtherEntity;

					// The method must be inherited from Object or a constructor
					final String methodName = currentMethod.getDisplayName();
					if (!((methodName.equals("clone"))
							|| (methodName.equals("equals"))
							|| (methodName.equals("finalize"))
							|| (methodName.equals("hashCode"))
							|| (methodName.equals("toString")) || (currentMethod
									.getDisplayID().startsWith("<init>")))) {

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
