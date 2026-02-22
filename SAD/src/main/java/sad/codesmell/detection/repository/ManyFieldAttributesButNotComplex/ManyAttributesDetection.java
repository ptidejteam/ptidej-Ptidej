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


public class ManyAttributesDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "ManyAttributesDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set ManyAttributesClassesFound = new HashSet();

		final HashMap mapOfManyAttributesValues = new HashMap();
		boolean thereIsManyAttributes = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				thereIsManyAttributes = true;

				
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
	mapOfManyAttributesValues.put(aClass, new Double[] {Double.valueOf(NAD), Double.valueOf(0)});
				//final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
				//mapOfManyAttributesValues.put(aClass, Double.valueOf(NAD));
			}
		}

		if (thereIsManyAttributes == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfManyAttributesValues, 10.0);
			setBoxPlot(boxPlot);

			final Map mapOfManyAttributesClassesFromBoxPlot = boxPlot.getHighOutliers();
			final Iterator iter3 = mapOfManyAttributesClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aManyAttributesClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aManyAttributesClass);
					
					
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aManyAttributesClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("NAD_MaxBound", Double.valueOf(boxPlot.getMaxBound()));
					final Double fuzziness = ((Double[])mapOfManyAttributesClassesFromBoxPlot.get(aManyAttributesClass))[1];
					classProp.addProperty(new MetricProperty("NAD", 
						NAD, 
						thresholdMap, fuzziness.doubleValue()));
					
					ManyAttributesClassesFound.add(new CodeSmell("ManyAttributes", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(ManyAttributesClassesFound);

	}
	
	
}
