/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.codesmell.detection.repository.SpeculativeGenerality;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IEntity;
import padl.kernel.IGhost;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

/**
 * @author Auto generated
 */

public class AbstractClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	// David and Yann 2013/10/24: Useless?
	// This variable and its accessor should probably removed. 
	private static final int bool = 1;

	public boolean getValueBoolean() {
		return AbstractClassDetection.bool == 1;
	}
	
	public String getName() {
		return "AbstractClass";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		Set classesAbstractClass = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				boolean presence = getValueBoolean();
		
				if (presence == true) {
					if (aClass.isAbstract()) {
						classesAbstractClass =
							createCodeSmell(
								classesAbstractClass,
								aClass);
					}
				} else {
					if (!aClass.isAbstract()) {
						classesAbstractClass =
							createCodeSmell(
								classesAbstractClass,
								aClass);
					}
				}
			}
		}
		this.setSetOfSmells(classesAbstractClass);
	}
	
	private Set createCodeSmell(
		Set classesAbstractClass,
		final IClass aClass) {
		try {
			CodeSmell dc =
				new CodeSmell(
					"AbstractClass",
					"",
					new ClassProperty(aClass));

			classesAbstractClass.add(dc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return classesAbstractClass;
	}
}
