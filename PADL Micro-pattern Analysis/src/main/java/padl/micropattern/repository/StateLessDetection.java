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
import padl.micropattern.IMicroPatternDetection;

public final class StateLessDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "StateLessDetection";
	}

	/*
	 *  8. Stateless. If a class has no fields at all (except for fields which
	 *	are both static and final), then it is stateless. The behavior of
	 *	such a class cannot depend on its history. Therefore, the execution
	 *	of each of its methods can only be dictated by the parameters.
	 *	Micro pattern Stateless thus captures classes which are a named
	 *	collection of procedures, and is a representation, in the object-oriented
	 *	world, of a software library in the procedural programming para-
	 *	digm.
	 *	A famous example of the Stateless micro pattern is the Arrays
	 *	class, from package java.util.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Only class can be Stateless
		if (anEntity instanceof IClass) {
			final Iterator iterator =
				anEntity.getIteratorOnConstituents(IField.class);

			while (iterator.hasNext()) {
				final IField aField = (IField) iterator.next();

				// All field must be static and final
				if (!(aField.isStatic() && aField.isFinal())) {
					return false;
				}
			}
			this.addEntities(anEntity);
			return true;
		}
		return false;
	}
}
