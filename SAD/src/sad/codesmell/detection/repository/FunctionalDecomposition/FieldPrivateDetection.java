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

/**
 * This class represents the detection of the code smell FieldPrivate
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
import padl.kernel.IField;
import sad.codesmell.property.impl.FieldProperty;
import sad.codesmell.property.impl.MethodProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * @author Auto generated
 */

public class FieldPrivateDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {
	private static final int RATIO_PRIVATE_FIELD = 100;
	
	public String getName() {
		return "FieldPrivate";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesFieldPrivate = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final Set setOfFieldPrivate = new HashSet();
				int numField = 0;
				
				final Iterator iter2 = aClass.getIteratorOnConstituents(IField.class);
				while (iter2.hasNext()) {
					numField ++;
					final IField aField = (IField) iter2.next();
					if (aField.isPrivate()) {
						setOfFieldPrivate.add(new FieldProperty(aField));
					}
				}
				
				if (numField > 0 && (setOfFieldPrivate.size()*100/numField) >= FieldPrivateDetection.RATIO_PRIVATE_FIELD) {
					try {
						ClassProperty classProp = new ClassProperty(aClass);
						classProp.addProperties(setOfFieldPrivate);
							
						classesFieldPrivate.add(new CodeSmell("FieldPrivate", "", classProp));
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}
		this.setSetOfSmells(classesFieldPrivate);
	}	
}
