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
package sad.codesmell.detection.repository.LazyClass;

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


public class FewMethodsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "FewMethodsDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set FewMethodsClassesFound = new HashSet();

		final HashMap mapOfFewMethodsValues = new HashMap();
		boolean thereIsFewMethods = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				thereIsFewMethods = true;

				
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
	mapOfFewMethodsValues.put(aClass, new Double[] {new Double (NMD + NAD), Double.valueOf(0)});
				//final double NMD_NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD_NAD")).compute(anAbstractLevelModel, aClass);
				//mapOfFewMethodsValues.put(aClass, Double.valueOf(NMD_NAD));
			}
		}

		if (thereIsFewMethods == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfFewMethodsValues, 5.0);
			setBoxPlot(boxPlot);

			final Map mapOfFewMethodsClassesFromBoxPlot = boxPlot.getLowOutliers();
			final Iterator iter3 = mapOfFewMethodsClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aFewMethodsClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aFewMethodsClass);
					
					
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aFewMethodsClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aFewMethodsClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("NMD_NAD_MinBound", Double.valueOf(boxPlot.getMinBound()));
thresholdMap.put("NMD_NAD_LowerQuartile", Double.valueOf(boxPlot.getLowerQuartile()));
					final Double fuzziness = ((Double[])mapOfFewMethodsClassesFromBoxPlot.get(aFewMethodsClass))[1];
					classProp.addProperty(new MetricProperty("NMD_NAD", 
						NMD+NAD, 
						thresholdMap, fuzziness.doubleValue()));
					
					FewMethodsClassesFound.add(new CodeSmell("FewMethods", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(FewMethodsClassesFound);

	}
	
	
}
