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
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell NoPolymorphism
 * @author Auto generated
 */

public class NoPolymorphismDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "NoPolymorphism";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {	
		final Set classesNoPolymorphism = new HashSet(); 
		
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final Set methodsOfClass = new HashSet();
				boolean usePoly = false;
				
				final Iterator iter2 = aClass.getIteratorOnConstituents(IMethod.class);
				while (iter2.hasNext() && !usePoly) {
					final IMethod aMethod = (IMethod) iter2.next();
					
					// We store the name of all methods in a class
					// If we find that the name of a method was already stored,
					// it means that the class is used polymorphism
					String methName = aMethod.getDisplayName();
					
					if(methodsOfClass.contains(methName)) {
						usePoly = true;
					}
					else {
						methodsOfClass.add(methName);
					}
				}
				
				if (usePoly == false) {
					try {
						CodeSmell dc = new CodeSmell(
							"NoPolymorphism", "", 
							new ClassProperty(aClass));
						
						classesNoPolymorphism.add(dc);
					}
					catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		this.setSetOfSmells(classesNoPolymorphism);
	}
}
