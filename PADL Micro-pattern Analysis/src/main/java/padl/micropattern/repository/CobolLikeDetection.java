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

public final class CobolLikeDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "CobolLikeDetection";
	}

	/*
	 *  7. Cobol like. Formally, the Cobol like micro pattern is dee?ned by
	 *	the requirement that a class has a single static method, one or more
	 *	static variables, and no instance methods or fields. This particular
	 *	programming style makes a signie?cant deviation from the object
	 *	oriented paradigm. Although the prevalence of this pattern is van-
	 *	ishingly small, instances can be found even in mature libraries.
	 *	Beginner programmers may tend to use Cobol like for their main
	 *	class, i.e., the class with function
	 *
	 *     public static void main(String[] args)
	 *     
	 *	The prevalence of Cobol like is not high, standing at the 0.5% level
	 *	in our corpus. However, we found that it occurs very frequently
	 *	(13.1%) in the sample programs included with the JAVA Tutorial [10]
	 *	guides.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;
		int nbField = 0;

		// Only Class can be Cobol Like
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

						// The Methods must be "Static method"
						if (!currentMethod.isStatic())
							return false;
					}
				}

				if (anOtherEntity instanceof IField) {
					nbField++;

					// The Fields must be "Static field"
					final IField currentField = (IField) anOtherEntity;
					if (!currentField.isStatic())
						return false;
				}
			}

			if ((nbMethod == 1) && (nbField >= 1)) {
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
