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
package sad.codesmell.detection.repository.ManyFieldAttributesButNotComplex;

/**
 * This class represents the detection of the code smell FieldPublic
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

public class FieldPublicDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	private static final int RATIO_PUBLIC_FIELD = 1;
	
	public String getName() {
		return "FieldPublic";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesFieldPublic = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final Set setOfFieldPublic = new HashSet();
				int numField = 0;
				
				final Iterator iter2 = aClass.getIteratorOnConstituents(IField.class);
				while (iter2.hasNext()) {
					numField ++;
					final IField aField = (IField) iter2.next();
				if (aField.isPublic()  && !aField.isFinal()) {
						setOfFieldPublic.add(new FieldProperty(aField));
					}
				}
				
				if (numField > 0 && (setOfFieldPublic.size()*100/numField) >= FieldPublicDetection.RATIO_PUBLIC_FIELD) {
					try {
						ClassProperty classProp = new ClassProperty(aClass);
						classProp.addProperties(setOfFieldPublic);
							
						classesFieldPublic.add(new CodeSmell("FieldPublic", "", classProp));
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}
		this.setSetOfSmells(classesFieldPublic);
	}	
}
