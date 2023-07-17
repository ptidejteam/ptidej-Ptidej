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
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class OutlineDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "OutlineDetection";
	}

	/*
	 *  19. Outline. An Outline is an abstract class where two or more
	 *	declared methods invoke at least one abstract methods of the current
	 *	(“this”) object.
	 *	For example, the methods of java.io.Reader rely on the abstract
	 *	method
	 *	read(char ac[], int i, int j)
	 *	Obviously, Outline is related to the TEMPLATE METHOD design
	 *	pattern.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethodCallingAbstractMethod = 0;

		// Must be an abstract Class
		if (anEntity instanceof IClass && anEntity.isAbstract()) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext() && nbMethodCallingAbstractMethod < 2) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IOperation) {
					final IOperation currentMethod = (IOperation) anOtherEntity;

					// Check if this Method call other abstract 
					// Method of the current ("this") object.
					final Iterator invocation =
						currentMethod.getIteratorOnConstituents();
					while (invocation.hasNext()
							&& nbMethodCallingAbstractMethod < 2) {

						final Object currentItem = invocation.next();
						if (currentItem instanceof IMethodInvocation) {

							final IMethodInvocation currentInvocation =
								(IMethodInvocation) currentItem;
							if ((currentInvocation.getCalledMethod() != null)
									&& (!currentInvocation
										.getCalledMethod()
										.getDisplayName()
										.equals("="))
									&& (currentInvocation.getCalledMethod()
										.isAbstract())) {

								// Check if the abstract method belong to the current class.
								final IConstituent aConstituent =
									anEntity
										.getConstituentFromID(currentInvocation
											.getCalledMethod()
											.getID());
								if (aConstituent != null) {
									nbMethodCallingAbstractMethod++;
								}
							}
						}
					}
				}
			}

			if (nbMethodCallingAbstractMethod >= 2) {
				this.addEntities(anEntity);
				return true;
			}
			return false;
		}
		return false;
	}
}
