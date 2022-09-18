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
import padl.kernel.IGetter;
import padl.kernel.IMethod;
import padl.kernel.ISetter;
import padl.micropattern.IMicroPatternDetection;

public final class DataManagerDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "DataManagerDetection";
	}

	/*
	 *  17. Data Manager. Experienced object-oriented programmers will
	 *	encapsulate all ï¬?elds of a Record and use setter and getter methods
	 *	to access these.
	 *	We say that a class is a Data Manager if all of its methods (including
	 *	inherited ones) are either setters or getters5 .
	 *	Recall that Data Manager micro pattern (just as the previously de-
	 *	scribed Record) also belong to the Degenerate Behavior category.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Only Class can be Data Manager
		if (anEntity instanceof IClass) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IMethod) {
					final IMethod currentMethod = (IMethod) anOtherEntity;

					// The method must be Getter of Setter
					if (!((currentMethod instanceof IGetter) || (currentMethod instanceof ISetter))) {
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
