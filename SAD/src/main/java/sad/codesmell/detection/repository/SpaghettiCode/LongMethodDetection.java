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

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IElement;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IGetter;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import padl.kernel.ISetter;
import padl.util.Util;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import sad.codesmell.property.impl.FieldProperty;
import sad.codesmell.property.impl.InterfaceProperty;
import sad.codesmell.property.impl.MethodProperty;
import sad.codesmell.property.impl.MetricProperty;
import sad.codesmell.property.impl.SemanticProperty;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.kernel.impl.CodeSmell;
import sad.util.BoxPlot;
import util.lang.Modifier;
import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell <CODESMELL>
 * 
 * @author Auto generated
 *
 */


public class LongMethodDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "LongMethodDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
				final Set LongMethodClassesFound = new HashSet();
		final HashMap mapOfClassesWithValues = new HashMap();
		final HashMap mapClassesWithMethods = new HashMap();

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				IClass classOfLongMethod = null;
				IMethod LongMethod = null;
				Integer longValue = Integer.valueOf(0);
	
				// for each class, we get the LongMethod
				final Iterator iter2 = aClass.getIteratorOnConstituents(IMethod.class);
				while (iter2.hasNext()) {
					final IMethod aMethod = (IMethod) iter2.next();
					if (!aMethod.isAbstract() && (aMethod.getVisibility() & Modifier.NATIVE) == 0) {
						final Integer value = Integer.valueOf(aMethod.getCodeLines().length);
		
						if (!(value == null)) {
							if (value.compareTo(longValue) > 0) {
								longValue = value;
								LongMethod = aMethod;
								classOfLongMethod = aClass;
							}
		
							// we put in a map the class with its LongMethod
							mapClassesWithMethods.put(classOfLongMethod, LongMethod);
		
							// we put in a map the class with the value of its
							// longest method
							mapOfClassesWithValues.put(
								classOfLongMethod,
								new Double[] {Double.valueOf(longValue.doubleValue()), Double.valueOf(0)});
						}
					}
				}// End of iterator of methods
			}
		}// End of iterator of classes

		final BoxPlot boxPlot = new BoxPlot(mapOfClassesWithValues, 0.0);
		setBoxPlot(boxPlot);

		final Map mapOfLongMethodsFromBoxPlot = boxPlot.getHighOutliers();

		final Iterator iter3 = mapOfLongMethodsFromBoxPlot
			.keySet()
			.iterator();
		while (iter3.hasNext()) {
			// we get first the mapMethodsWithClass(aClass, longMethod)
			final IClass aClass = (IClass) iter3.next();
			final IMethod aLongMethod = (IMethod) mapClassesWithMethods
				.get(aClass);
			
			try {
				ClassProperty classProp = new ClassProperty(aClass);
				
				double LOC = ((Double[]) mapOfClassesWithValues.get(aClass))[0].doubleValue();
				MethodProperty mp = new MethodProperty(aLongMethod);
				
				

HashMap thresholdMap = new HashMap();
thresholdMap.put("METHOD_LOC_MaxBound", Double.valueOf(boxPlot.getMaxBound()));
				mp.addProperty(new MetricProperty("LOC", LOC, thresholdMap));
				classProp.addProperty(mp);
				
				LongMethodClassesFound.add(new CodeSmell("LongMethod", "", classProp));
			} catch (Exception e) {
				// Not suppose to append :(
			}
		}

		this.setSetOfSmells(LongMethodClassesFound);

	}
	
	
}
