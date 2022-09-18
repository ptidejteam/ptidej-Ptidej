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

public final class PoolDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "PoolDetection";
	}

	/*
	 * 	4. Pool. The most degenerate classes are those which have neither
	 *	state nor behavior. Such a class is distinguished by the requirement
	 *	that it declares no instance ï¬?elds. Moreover, all of its declared static
	 *	ï¬?elds must be ï¬?nal . Another requirement is that the class has no
	 *	methods (other than those inherited from Object, or automatically
	 *	generated constructors).
	 *	A Pool is a class deï¬?ned by these requirements. It serves a the
	 *	purpose of grouping together a set of named constants.
	 *	Programmers often use interfaces for the Pool micro pattern.
	 *	For example, package javax.swing includes interface Swing-
	 *	Constants which deï¬?nes constants used in positioning and ori-
	 *	enting screen components.
	 *	The pattern, also called â€œconstant interface anti-patternâ€? [7], makes
	 *	it possible to incorporate a name space of deï¬?nitions into a class by
	 *	adding an implements clause to that class.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		if ((anEntity instanceof IClass) || (anEntity instanceof IInterface)) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Check for method behavior
				if (anOtherEntity instanceof IOperation) {
					final IOperation currentMethod = (IOperation) anOtherEntity;

					// Detect static attribute initialization
					if (!currentMethod.getDisplayName().equals("<clinit>")
							&& (!currentMethod.getDisplayID().startsWith(
								"<init>"))) {

						// The method must be inherited from Object
						final String methodName =
							currentMethod.getDisplayName();
						if (!((methodName.equals("clone"))
								|| (methodName.equals("equals"))
								|| (methodName.equals("finalize"))
								|| (methodName.equals("hashCode")) || (methodName
							.equals("toString")))) {

							return false;
						}
					}
				}

				// All Fields must be "static final"
				if (anOtherEntity instanceof IField) {
					final IField currentField = (IField) anOtherEntity;
					if ((!currentField.isStatic()) || (!currentField.isFinal())) {

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
