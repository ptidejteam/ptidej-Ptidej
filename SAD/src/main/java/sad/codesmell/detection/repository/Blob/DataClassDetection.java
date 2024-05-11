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
package sad.codesmell.detection.repository.Blob;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IElement;
import padl.kernel.IEntity;
import padl.kernel.IGetter;
import padl.kernel.IMethod;
import padl.kernel.ISetter;
import padl.util.Util;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import sad.codesmell.property.impl.MetricProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;

/**
 * This class represents the detection of the code smell DataClass
 * 
 * @author Auto generated
 *
 */
 
public class DataClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	private static final int RATIO_ACCESSOR = 90; 

	public String getName() {
		return "DataClass";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set DataClassFound = new HashSet();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next(); 
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				if (this.isDataClass(aClass)) {
					final HashMap tableOfValueMetrics = new HashMap();
					
					ClassProperty classProp = new ClassProperty(aClass);
					try {
						double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
						double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass); 
								
						classProp.addProperty(new MetricProperty("NMD+NAD", NMD+NAD, new HashMap()));
						DataClassFound.add(new CodeSmell("DataClass", "", classProp));
					} catch (Exception e) {
						// Not suppose to append :(
					}
				}
			}
		}
		this.setSetOfSmells(DataClassFound);
	}
	private boolean isDataClass(final IClass aClass) {
		boolean isDataClass = true;
		if (!Util.isObjectModelRoot(aClass.getName())
			&& (isDataClass = this.checkForMehods(aClass))) {

			final Iterator iterator =
				aClass.getIteratorOnInheritedEntities();
			while (iterator.hasNext() && isDataClass) {
				final IEntity superEntity = (IEntity) iterator.next();
				if (superEntity instanceof IClass) {
					isDataClass |= this.isDataClass((IClass) superEntity);
				}
			}
		}
		return isDataClass;
	}
	private boolean checkForMehods(final IClass aClass) {
		boolean isDataClass = true;
		int numAccessor = 0;
		int numMethod = 0;
		
		// Check if all the methods of the class 
		// are accessors or constructor
		final Iterator iter2 = aClass.getIteratorOnConstituents();
		while (iter2.hasNext() && isDataClass == true) {
			final IElement element = (IElement) iter2.next();

			// We analyze only the element instance of IAttribute
			if (element instanceof IMethod && !element.isPrivate()) {
				numMethod ++;
				if (Util.isMethodFromObject(element.getDisplayName())
					|| element instanceof IGetter
					|| element instanceof ISetter) {

					numAccessor ++;
				}
			}
		}
		if (numMethod > 0) {
			if ((numAccessor*100/numMethod) >= DataClassDetection.RATIO_ACCESSOR)
				return true;
			else
				return false;
		} else {
			// TODO: Is a class with no method should be considered as a "Only Accessor"
			return true;
		}
	}
}
