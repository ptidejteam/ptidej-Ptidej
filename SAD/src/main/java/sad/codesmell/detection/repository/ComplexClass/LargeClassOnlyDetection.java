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
package sad.codesmell.detection.repository.ComplexClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IEntity;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.AbstractCodeSmellDetection;
import sad.codesmell.property.impl.ClassProperty;
import sad.codesmell.property.impl.MetricProperty;
import sad.kernel.impl.CodeSmell;
import sad.util.BoxPlot;
import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell <CODESMELL>
 * 
 * @author Auto generated
 *
 */

public class LargeClassOnlyDetection extends AbstractCodeSmellDetection
		implements ICodeSmellDetection {

	public String getName() {
		return "LargeClassOnlyDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set LargeClassOnlyClassesFound = new HashSet();

		final HashMap mapOfLargeClassOnlyValues = new HashMap();
		boolean thereIsLargeClassOnly = false;

		final Iterator iter = anAbstractLevelModel
				.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				thereIsLargeClassOnly = true;

				final double NMD = ((IUnaryMetric) MetricsRepository
						.getInstance().getMetric("NMD"))
						.compute(anAbstractLevelModel, aClass);
				final double NAD = ((IUnaryMetric) MetricsRepository
						.getInstance().getMetric("NAD"))
						.compute(anAbstractLevelModel, aClass);
				mapOfLargeClassOnlyValues.put(aClass, new Double[] {
						Double.valueOf(NMD + NAD), Double.valueOf(0) });
				//final double NMD_NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD_NAD")).compute(anAbstractLevelModel, aClass);
				//mapOfLargeClassOnlyValues.put(aClass, Double.valueOf(NMD_NAD));
			}
		}

		if (thereIsLargeClassOnly == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfLargeClassOnlyValues, 0.0);
			setBoxPlot(boxPlot);

			final Map mapOfLargeClassOnlyClassesFromBoxPlot = boxPlot
					.getHighOutliers();
			final Iterator iter3 = mapOfLargeClassOnlyClassesFromBoxPlot
					.keySet().iterator();

			while (iter3.hasNext()) {
				final IClass aLargeClassOnlyClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(
							aLargeClassOnlyClass);

					final double NMD = ((IUnaryMetric) MetricsRepository
							.getInstance().getMetric("NMD")).compute(
									anAbstractLevelModel, aLargeClassOnlyClass);
					final double NAD = ((IUnaryMetric) MetricsRepository
							.getInstance().getMetric("NAD")).compute(
									anAbstractLevelModel, aLargeClassOnlyClass);

					HashMap thresholdMap = new HashMap();
					thresholdMap.put("NMD_NAD_MaxBound",
							Double.valueOf(boxPlot.getMaxBound()));
					final Double fuzziness = ((Double[]) mapOfLargeClassOnlyClassesFromBoxPlot
							.get(aLargeClassOnlyClass))[1];
					classProp.addProperty(new MetricProperty("NMD_NAD",
							NMD + NAD, thresholdMap, fuzziness.doubleValue()));

					LargeClassOnlyClassesFound.add(
							new CodeSmell("LargeClassOnly", "", classProp));
				}
				catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(LargeClassOnlyClassesFound);

	}

}
