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

public final class OverriderDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "OverriderDetection";
	}

	/*
	 *  26. Overrider. A class where each of its declared public methods
	 *	overrides a non-abstract method inherited from its superclass. Such
	 *	a class changes the behavior of its superclass while retaining its protocol.
	 *	A typical Overrider class is the BufferedOutputStream
	 *	class.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Must be a Class
		if (anEntity instanceof IClass) {

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IMethod) {
					if (((IMethod) anOtherEntity).isPublic()
							&& !((IMethod) anOtherEntity)
								.getDisplayID()
								.startsWith("<init>")) {

						// All public method must override a non-abstract method of the superclass
						final Iterator inheritedEntities =
							anEntity.getIteratorOnInheritedEntities();
						while (inheritedEntities.hasNext()) {
							final IFirstClassEntity aSuperClass =
								(IFirstClassEntity) inheritedEntities.next();

							// Find the method in the superclass
							final IMethod superClassMethod =
								(IMethod) aSuperClass
									.getConstituentFromID(((IMethod) anOtherEntity)
										.getDisplayName() + "()");

							// Must be declared in the superclass and abstract
							if (superClassMethod == null
									|| superClassMethod.isAbstract()) {
								return false;
							}
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
