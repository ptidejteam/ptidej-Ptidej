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
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.micropattern.IMicroPatternDetection;

public final class ImplementorDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "ImplementorDetection";
	}

	/*
	 *  25. Implementor. An Implementor is a non-abstract class such that
	 *	all of its the public methods were declared as abstract in its superclass.
	 *	An example is class SimpleFormatter, which is defined in the
	 *	java.util.logging package). This class has single public
	 *	method,
	 *
	 *	format(LogRecord logrecord),
	 *
	 *	which was declared abstract by the superclass, Formatter (of the
	 *	same package).
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		boolean bFound = false;

		// Must be a non-abstract Class
		if (!((IFirstClassEntity) anEntity).isAbstract()) {

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IMethod) {
					if (((IMethod) anOtherEntity).isPublic()
							&& !((IMethod) anOtherEntity)
								.getDisplayID()
								.startsWith("<init>")) {

						// All public method need to be declared abstract in the superclass
						final Iterator inheritedEntities =
							anEntity.getIteratorOnInheritedEntities();

						while (inheritedEntities.hasNext() && !bFound) {
							final IFirstClassEntity aSuperClass =
								(IFirstClassEntity) inheritedEntities.next();

							// Find the method in the superclass
							final IMethod superClassMethod =
								(IMethod) aSuperClass
									.getConstituentFromID(((IMethod) anOtherEntity)
										.getID());
							// Must be declared in the superclass and abstract
							if (superClassMethod != null
									&& superClassMethod.isAbstract()) {
								bFound = true;
							}
						}

						try {
							final Iterator inheritedEntities2 =
								((IClass) anEntity)
									.getIteratorOnImplementedInterfaces();

							while (inheritedEntities2.hasNext() && !bFound) {
								final IFirstClassEntity aSuperClass =
									(IFirstClassEntity) inheritedEntities2
										.next();

								// Find the method in the superinterface
								final IMethod superClassMethod =
									(IMethod) aSuperClass
										.getConstituentFromID(((IMethod) anOtherEntity)
											.getID());

								// Must be declared in the superclass and abstract
								if (superClassMethod != null
										&& superClassMethod.isAbstract()) {
									bFound = true;
								}
							}
						}
						catch (final Exception e) {
							// Nothing to do
						}

						if (!bFound) {
							return false;
						}
					}
				}
			}

			this.addEntities(anEntity);
			return true;
		}
		return false;
	}
}
