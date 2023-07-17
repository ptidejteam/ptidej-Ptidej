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
package sad.codesmell.detection.repository.SwissArmyKnife;

/**
 * This class represents the detection of the code smell MultipleInterface
 * 
 * @author Auto generated
 *
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IEntity;
import padl.kernel.IInterface;
import sad.codesmell.property.impl.InterfaceProperty;
import sad.codesmell.property.impl.MethodProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * @author Auto generated
 */

public class MultipleInterfaceDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {
	private static final int NB_INT = 3;

	public String getName() {
		return "MultipleInterface";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesMultipleInterface = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final Set setOfInterfaces = new HashSet();
	
				final Iterator iter2 = aClass.getIteratorOnImplementedInterfaces();
				while (iter2.hasNext()) {
					final Object obj = iter2.next();
	
					if (obj instanceof IInterface) {
						final IInterface iInterface = (IInterface) obj;
						setOfInterfaces.add(new InterfaceProperty(iInterface));
					}
				}
	
				if (setOfInterfaces.size() >= MultipleInterfaceDetection.NB_INT) {
					try {
						CodeSmell dc = new CodeSmell(
							"MultipleInterface", "", 
							new ClassProperty(aClass));
						
						dc.getClassProperty().addProperties(setOfInterfaces);
						classesMultipleInterface.add(dc);
					}
					catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}
		this.setSetOfSmells(classesMultipleInterface);
	}
}
