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
package sad.codesmell.detection.repository.FunctionalDecomposition;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IEntity;
import padl.kernel.IMethod;
import sad.codesmell.property.impl.MethodProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell ClassOneMethod
 * @author Auto generated
 */

public class ClassOneMethodDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {
	
	public String getName() {
		return "ClassOneMethod";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classClassOneMethod = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;

				/*
				 * Why it does not work ? int i =
				 * aClass.getNumberOfConstituents(IMethod.class);
				 * System.out.println("------> " + i + " <---------");
				 */
	
				// We count first the number of methods
				int i = 0;
				final Iterator iterBis = aClass.getIteratorOnConstituents(IMethod.class);
				while (iterBis.hasNext()) {
					final IMethod aMethod = (IMethod) iterBis.next();
					if (!(aMethod.getDisplayName().matches("main"))) {
						i++;
					}
				}
	
				// if there is only one method, we add it as a code smell
				// ClassOneMethod
				if (i == 1) {
					final Iterator iter2 = aClass
							.getIteratorOnConstituents(IMethod.class);
					while (iter2.hasNext()) {
						final IMethod aMethod = (IMethod) iter2.next();
						if (!(aMethod.getDisplayName().matches("main"))) {
							try {
								CodeSmell dc = new CodeSmell(
									"ClassOneMethod", "", 
									new ClassProperty(aClass));
								
								dc.getClassProperty().addProperty(new MethodProperty(aMethod));
								classClassOneMethod.add(dc);
							}
							catch (final Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace(ProxyConsole.getInstance().errorOutput());
							}
							
							
						}
					}
				}
			}
		}
		this.setSetOfSmells(classClassOneMethod);
	}
}
