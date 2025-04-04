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
package sad.codesmell.detection.repository.TraditionBreaker;

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


public class ChildClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "ChildClassDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set ChildClassClassesFound = new HashSet();

		final HashMap mapOfChildClassValues = new HashMap();
		boolean thereIsChildClass = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				thereIsChildClass = true;

				
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
	mapOfChildClassValues.put(aClass, new Double[] {Double.valueOf (NMD + NAD), Double.valueOf(0)});
				//final double NMD_NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD_NAD")).compute(anAbstractLevelModel, aClass);
				//mapOfChildClassValues.put(aClass, Double.valueOf(NMD_NAD));
			}
		}

		if (thereIsChildClass == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfChildClassValues, 10.0);
			setBoxPlot(boxPlot);

			final Map mapOfChildClassClassesFromBoxPlot = boxPlot.getHighValues();
			final Iterator iter3 = mapOfChildClassClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aChildClassClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aChildClassClass);
					
					
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aChildClassClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aChildClassClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("NMD_NAD_UpperQuartile", Double.valueOf(boxPlot.getUpperQuartile()));
thresholdMap.put("NMD_NAD_MaxBound", Double.valueOf(boxPlot.getMaxBound()));
					final Double fuzziness = ((Double[])mapOfChildClassClassesFromBoxPlot.get(aChildClassClass))[1];
					classProp.addProperty(new MetricProperty("NMD_NAD", 
						NMD+NAD, 
						thresholdMap, fuzziness.doubleValue()));
					
					ChildClassClassesFound.add(new CodeSmell("ChildClass", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(ChildClassClassesFound);

	}
	
	
}
