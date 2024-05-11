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


public class MethodNoParameterDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "MethodNoParameterDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesMethodNoParameter = new HashSet();
		
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (entity instanceof IClass) {
				final IClass aClass = (IClass) entity;
				final double NOParam = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOParam")).compute(anAbstractLevelModel, aClass);
				
				if (NOParam < 5.0) {
					try {
						CodeSmell dc = new CodeSmell(
							"MethodNoParameter", "", 
							new ClassProperty(aClass));
						
						HashMap thresholdMap = new HashMap();
						thresholdMap.put("NOParam", Double.valueOf(5.0));
						
						dc.getClassProperty().addProperty(new MetricProperty("NOParam", 
							((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOParam")).compute(anAbstractLevelModel, aClass), thresholdMap));
						
						classesMethodNoParameter.add(dc);
					}
					catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		this.setSetOfSmells(classesMethodNoParameter);
		// return classesMethodNoParameter;

	}
	
	
}
