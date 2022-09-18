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
import padl.kernel.IMethodInvocation;
import padl.micropattern.IMicroPatternDetection;

public final class SinkDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "SinkDetection";
	}

	/*
	 *  18. Sink. A class where its declared methods do not call neither
	 *	instance methods nor static methods is a Sink.
	 *	Class JarEntry of package java.util.jar.JarEntry is
	 *	an example of Sink.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		// Only Class can be Sink
		if (anEntity instanceof IClass) {
			final Iterator iterator =
				anEntity.getIteratorOnConstituents(IMethod.class);
			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();
				final IMethod currentMethod = (IMethod) anOtherEntity;

				final Iterator invocation =
					currentMethod.getIteratorOnConstituents();
				while (invocation.hasNext()) {

					final Object currentItem = invocation.next();
					if (currentItem instanceof IMethodInvocation) {

						final IMethodInvocation currentInvocation =
							(IMethodInvocation) currentItem;
						if ((currentInvocation.getCalledMethod() != null)
								&& (!currentInvocation
									.getCalledMethod()
									.getDisplayName()
									.equals("="))) {

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
