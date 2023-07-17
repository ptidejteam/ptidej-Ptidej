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

public final class FunctionObjectDetection extends
		AbstractMicroPatternDetection implements IMicroPatternDetection {

	public String getName() {
		return "FunctionObjectDetection";
	}

	/*
	 * 	6. Function Object. The Function Object micro pattern is similar
	 *	to the Function Pointer micro pattern. The only difference is that
	 *	Function Object has instance ï¬?elds (which are often set by the class
	 *	constructor). Thus, an instance of Function Object class can store
	 *	parameters to the main method of the class.
	 *	The Function Object pattern matches many anonymous classes in
	 *	the JRE which implement an interface with a single method. These
	 *	are mostly event handlers, passed as callback hooks in GUI libraries
	 *	(AWT and Swing). Hence, such classes often realize the C O M -
	 *	M A N D design pattern.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;

		// Only a Class can be a Function Object
		if (anEntity instanceof IClass) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();
				if (anOtherEntity instanceof IOperation) {
					final IOperation currentMethod = (IOperation) anOtherEntity;

					// Detect static attribute initialization
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

				// The Fields must be "instance field"
				if (anOtherEntity instanceof IField) {
					final IField currentField = (IField) anOtherEntity;
					if (currentField.isStatic()) {
						return false;
					}
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
