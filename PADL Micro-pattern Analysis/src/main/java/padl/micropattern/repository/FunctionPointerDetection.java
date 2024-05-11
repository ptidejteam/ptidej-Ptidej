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

public final class FunctionPointerDetection extends
		AbstractMicroPatternDetection implements IMicroPatternDetection {

	public String getName() {
		return "FunctionPointerDetection";
	}

	/*
	 * 	5. Function Pointer. Very peculiar are those classes which have no
	 *	fields at all, and only a single public instance method.
	 *	An example is class LdapNameParser (which is defined in pack-
	 *	age com.sun.jndi.ldap.LdapNameParser). This class has
	 *	a single parse method, with (as expected) a string parameter.
	 *	Instances of Function Pointer classes represent the equivalent of a
	 *	function pointer (or a pointer to procedure) in the procedural pro-
	 *	gramming paradigm, or of a function value in the functional pro-
	 *	gramming paradigm. Such an instance can then be used to make
	 *	an indirect polymorphic call to this function. The task of function
	 *	composition (as in the functional programming paradigm), can be
	 *	achieved by using two such instances.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;

		// Only a Class can be a Function Pointer
		if (anEntity instanceof IClass) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();
				if (anOtherEntity instanceof IOperation) {
					final IOperation currentMethod = (IOperation) anOtherEntity;

					// Detect static attribute initialization and constructor
					if (!currentMethod.getDisplayName().equals("<clinit>")
							&& (!currentMethod.getDisplayID().startsWith(
								"<init>"))) {

						nbMethod++;

						// The Methods must be "instance method" and public
						if ((currentMethod.isStatic())
								|| (!currentMethod.isPublic())) {
							return false;
						}
					}
				}
				// No field allowed
				if (anOtherEntity instanceof IField) {
					return false;
				}
			}

			if (nbMethod == 1) {
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
