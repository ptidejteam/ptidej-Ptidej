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
import padl.kernel.IConstructor;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IOperation;
import padl.micropattern.IMicroPatternDetection;

public final class TaxonomyDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "TaxonomyDetection";
	}

	/*
	 * 	2. Taxonomy. Even if the deï¬?nition of an interface is empty it may
	 * 	still extend another, potentially non-empty, interface.
	 * 	Consider for example interface DocAttribute (deï¬?ned in pack-
	 * 	age javax.print.attribute). This interface extends inter-
	 * 	face Attribute in the same package without adding any further
	 * 	declarations. Interface DocAttribute is used, similarly to the
	 * 	Designator micro pattern, for tagging purposesâ€”speciï¬?cally that
	 * 	the attribute at hand is specialized for what is known as â€œDocâ€? in
	 * 	the JRE.
	 * 	An empty interface which extends a single interface is called a Tax-
	 * 	onomy, since it is included, in the subtyping sense, in its parent, but
	 * 	otherwise identical to it.
	 * 	There are also classes which are Taxonomy. Such a class must sim-
	 * 	ilarly be empty, i.e., add no ï¬?elds nor methods to its parent. Since
	 * 	constructors are not inherited, an empty class may contain construc-
	 * 	tors. A Taxonomy class may not implement any interfaces.
	 * 	This micro pattern is very common in the hierarchy of JAVAâ€™s ex-
	 * 	ception classes, such as: EOFException which extends IOEx-
	 * 	ception. The reason is that selection of a catch clause is de-
	 * 	termined by the runtime type of the thrown exception, and not by
	 * 	its state.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int numberOfInterface = 0;

		// Interface and Class can be Taxonormy
		if ((anEntity instanceof IClass) || (anEntity instanceof IInterface)) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				if (anOtherEntity instanceof IOperation) {
					// An Interface must not contain any method declaration
					if (anEntity instanceof IInterface) {
						return false;
					}
					else { // Class
							// A class can contain only Constructor declaration
						if (anOtherEntity instanceof IConstructor) {
							// BIG HACK - WAZZ UP
							// TODO: Remove the hack...
							final IConstructor currentMethod =
								(IConstructor) anOtherEntity;

							if (!currentMethod.getDisplayID().startsWith(
								"<init>")) {

								return false;
							}
						}
					}

					// No field declaration allowed
					if (anOtherEntity instanceof IField) {
						return false;
					}
				}
			}

			// Looks good so far :)
			// Count the number of implemented interface
			final Iterator myIterator =
				anEntity.getIteratorOnInheritedEntities();
			while (myIterator.hasNext()) {
				final Object anOtherEntity = myIterator.next();
				if (anOtherEntity instanceof IInterface) {
					numberOfInterface++;
				}
			}
			if (anEntity instanceof IClass) {
				// A class cannot implement any Interface
				if (numberOfInterface == 0) {
					this.addEntities(anEntity);
					return true;
				}
			}
			else {
				// An Interface can implement only one interface 
				if (numberOfInterface == 1) {
					this.addEntities(anEntity);
					return true;
				}
			}
		}
		return false;
	}
}
