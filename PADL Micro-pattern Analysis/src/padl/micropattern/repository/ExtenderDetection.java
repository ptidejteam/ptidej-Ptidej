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
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.micropattern.IMicroPatternDetection;

public final class ExtenderDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "ExtenderDetection";
	}

	/*
	 *  27. Extender. An Extender is a class which extends the interface
	 *	inherited from its superclass and super interfaces, but does not over-
	 *	ride any method.
	 *	For example, class Properties (in java.util) extends its
	 *	superclass (Hashtable) by declaring several concrete methods,
	 *	which enrich the functionality provided to the client. None of these
	 *	methods overrides a previously implemented method, thus keeping
	 *	the superclass behavior intact. Note that an Extender may be re-
	 *	garded as an instantiation of a degenerate mixin class [8] over its
	 *	superclass.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;

		// Only Class can be Extender
		if (anEntity instanceof IClass) {

			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IMethod) {
					// Bypass constructor
					if (!((IMethod) anOtherEntity).getDisplayID().startsWith(
						"<init>")) {

						nbMethod++;

						if (!checkInheritedTree(
							anEntity,
							(IMethod) anOtherEntity,
							true)) {
							return false;
						}
					}
				}
			}

			if (nbMethod > 0) {
				this.addEntities(anEntity);
				return true;
			}
		}
		return false;
	}

	private boolean checkInheritedTree(
		final IFirstClassEntity anEntity,
		final IMethod currentMethod,
		final boolean rejectEmpty) {
		// All method must not be declared in the superClass/superInterface

		final Iterator inheritedEntities =
			anEntity.getIteratorOnInheritedEntities();

		if (rejectEmpty && anEntity instanceof IInterface
				&& !inheritedEntities.hasNext()) {
			return false;
		}

		while (inheritedEntities.hasNext()) {
			final IConstituent aSuper = (IConstituent) inheritedEntities.next();

			if (aSuper instanceof IClass || aSuper instanceof IInterface) {
				// Find the method in the superClass/superInterface
				final IMethod superClassMethod =
					(IMethod) ((IFirstClassEntity) aSuper)
						.getConstituentFromID(currentMethod.getID());

				// Must not be declared in the superClass/superInterface
				if (superClassMethod != null) {
					return false;
				}

				if (!checkInheritedTree(
					(IFirstClassEntity) aSuper,
					currentMethod,
					false)) {
					return false;
				}
			}
		}

		if (anEntity instanceof IClass) {
			final Iterator implementedEntities =
				((IClass) anEntity).getIteratorOnImplementedInterfaces();

			if (rejectEmpty && !implementedEntities.hasNext())
				return false;

			while (implementedEntities.hasNext()) {
				final IConstituent aSuper =
					(IConstituent) implementedEntities.next();

				if (aSuper instanceof IClass || aSuper instanceof IInterface) {
					// Find the method in the superClass/superInterface
					final IMethod superClassMethod =
						(IMethod) ((IFirstClassEntity) aSuper)
							.getConstituentFromID(currentMethod.getID());

					// Must not be declared in the superClass/superInterface
					if (superClassMethod != null) {
						return false;
					}

					if (!checkInheritedTree(
						(IFirstClassEntity) aSuper,
						currentMethod,
						false)) {
						return false;
					}
				}
			}
		}

		return true;
	}
}
