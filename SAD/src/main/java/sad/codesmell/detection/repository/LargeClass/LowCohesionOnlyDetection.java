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
package sad.codesmell.detection.repository.LargeClass;

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
import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell <CODESMELL>
 * 
 * @author Auto generated
 *
 */


public class LowCohesionOnlyDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "LowCohesionOnlyDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set LowCohesionOnlyClassesFound = new HashSet();

		final HashMap mapOfLowCohesionOnlyValues = new HashMap();
		boolean thereIsLowCohesionOnly = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				thereIsLowCohesionOnly = true;

				
	final double LCOM5 = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5")).compute(anAbstractLevelModel, aClass);
	mapOfLowCohesionOnlyValues.put(aClass, new Double[] {Double.valueOf(LCOM5), Double.valueOf(0)});
				//final double LCOM5 = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5")).compute(anAbstractLevelModel, aClass);
				//mapOfLowCohesionOnlyValues.put(aClass, Double.valueOf(LCOM5));
			}
		}

		if (thereIsLowCohesionOnly == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfLowCohesionOnlyValues, 20.0);
			setBoxPlot(boxPlot);

			final Map mapOfLowCohesionOnlyClassesFromBoxPlot = boxPlot.getHighOutliers();
			final Iterator iter3 = mapOfLowCohesionOnlyClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aLowCohesionOnlyClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aLowCohesionOnlyClass);
					
					
	final double LCOM5 = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5")).compute(anAbstractLevelModel, aLowCohesionOnlyClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("LCOM5_MaxBound", Double.valueOf(boxPlot.getMaxBound()));
					final Double fuzziness = ((Double[])mapOfLowCohesionOnlyClassesFromBoxPlot.get(aLowCohesionOnlyClass))[1];
					classProp.addProperty(new MetricProperty("LCOM5", 
						LCOM5, 
						thresholdMap, fuzziness.doubleValue()));
					
					LowCohesionOnlyClassesFound.add(new CodeSmell("LowCohesionOnly", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(LowCohesionOnlyClassesFound);

	}
	
	
}
