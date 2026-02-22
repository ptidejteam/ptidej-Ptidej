package sad.codesmell.detection.repository.ManyFieldAttributesButNotComplex;

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
import padl.kernel.IGhost;
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
import com.ibm.toad.cfparse.utils.Access;
import util.io.ProxyConsole;

/**
 * This class represents the detection of the code smell <CODESMELL>
 * 
 * @author Auto generated
 *
 */


public class NotComplexDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "NotComplexDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set NotComplexClassesFound = new HashSet();

		final HashMap mapOfNotComplexValues = new HashMap();
		boolean thereIsNotComplex = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				thereIsNotComplex = true;

				
	final double WMC = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("WMC")).compute(anAbstractLevelModel, aClass);
	mapOfNotComplexValues.put(aClass, new Double[] {Double.valueOf(WMC), Double.valueOf(0)});
				//final double WMC = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("WMC")).compute(anAbstractLevelModel, aClass);
				//mapOfNotComplexValues.put(aClass, Double.valueOf(WMC));
			}
		}

		if (thereIsNotComplex == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfNotComplexValues, 10.0);
			setBoxPlot(boxPlot);

			final Map mapOfNotComplexClassesFromBoxPlot = boxPlot.getLowOutliers();
			final Iterator iter3 = mapOfNotComplexClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aNotComplexClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aNotComplexClass);
					
					
	final double WMC = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("WMC")).compute(anAbstractLevelModel, aNotComplexClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("WMC_MinBound", Double.valueOf(boxPlot.getMinBound()));
thresholdMap.put("WMC_LowerQuartile", Double.valueOf(boxPlot.getLowerQuartile()));
					final Double fuzziness = ((Double[])mapOfNotComplexClassesFromBoxPlot.get(aNotComplexClass))[1];
					classProp.addProperty(new MetricProperty("WMC", 
						WMC, 
						thresholdMap, fuzziness.doubleValue()));
					
					NotComplexClassesFound.add(new CodeSmell("NotComplex", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(NotComplexClassesFound);

	}
	
	
}
