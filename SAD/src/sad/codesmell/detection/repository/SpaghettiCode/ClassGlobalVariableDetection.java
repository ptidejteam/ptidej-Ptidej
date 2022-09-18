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
package sad.codesmell.detection.repository.SpaghettiCode;
   
/**
 * This class represents the detection of the code smell ClassGlobalVariable
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
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

import util.io.ProxyConsole;

/**
 * @author Auto generated
 */

public class ClassGlobalVariableDetection  extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	// David and Yann 2013/10/24: Useless?
	// This variable and its accessor should probably removed. 
	private static final int bool = 1;

	public boolean getValueBoolean() {
		return ClassGlobalVariableDetection.bool == 1;
	}
	 
	public String getName() {
		return "ClassGlobalVariable";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		Set classesClassGlobalVariable = new HashSet();
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next(); 
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final Set setOfVariables = new HashSet();
	   			final Iterator iter2 = aClass.getIteratorOnConstituents(IField.class);
				   while (iter2.hasNext()) {
					   final IField aField = (IField) iter2.next();
					   if (aField.isStatic()  && !(aField.isFinal()) && !(aField.isPrivate())) {
						   setOfVariables.add(new FieldProperty(aField));
					   }
				   }
	   
				   
				boolean presence = getValueBoolean();
	
				if (presence == true) {
					if (!(setOfVariables.isEmpty())) {
						classesClassGlobalVariable =
							createCodeSmell(
								classesClassGlobalVariable,
								aClass,
								setOfVariables);
					}
				} else {
					if (setOfVariables.isEmpty()) {
						classesClassGlobalVariable =
							createCodeSmell(
								classesClassGlobalVariable,
								aClass,
								setOfVariables);
					}
				}
			}
		}

		this.setSetOfSmells(classesClassGlobalVariable);
	}
	
	private Set createCodeSmell(
		Set classesClassGlobalVariable,
		final IClass aClass,
		final Set setOfVariables) {
		try {
			CodeSmell dc =
				new CodeSmell(
					"ClassGlobalVariable",
					"",
					new ClassProperty(aClass));
			dc.getClassProperty().addProperties(setOfVariables);

			classesClassGlobalVariable.add(dc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return classesClassGlobalVariable;
	}
}
