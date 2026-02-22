package sad.codesmell.detection.repository.LongParameterList;

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


public class LongParameterListClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "LongParameterListClassDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set LongParameterListClassClassesFound = new HashSet();

		final HashMap mapOfLongParameterListClassValues = new HashMap();
		boolean thereIsLongParameterListClass = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				thereIsLongParameterListClass = true;

				
	final double NOParam = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOParam")).compute(anAbstractLevelModel, aClass);
	mapOfLongParameterListClassValues.put(aClass, new Double[] {Double.valueOf(NOParam), Double.valueOf(0)});
				//final double NOParam = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOParam")).compute(anAbstractLevelModel, aClass);
				//mapOfLongParameterListClassValues.put(aClass, Double.valueOf(NOParam));
			}
		}

		if (thereIsLongParameterListClass == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfLongParameterListClassValues, 20.0);
			setBoxPlot(boxPlot);

			final Map mapOfLongParameterListClassClassesFromBoxPlot = boxPlot.getHighOutliers();
			final Iterator iter3 = mapOfLongParameterListClassClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aLongParameterListClassClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aLongParameterListClassClass);
					
					
	final double NOParam = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOParam")).compute(anAbstractLevelModel, aLongParameterListClassClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("NOParam_MaxBound", Double.valueOf(boxPlot.getMaxBound()));
					final Double fuzziness = ((Double[])mapOfLongParameterListClassClassesFromBoxPlot.get(aLongParameterListClassClass))[1];
					classProp.addProperty(new MetricProperty("NOParam", 
						NOParam, 
						thresholdMap, fuzziness.doubleValue()));
					
					LongParameterListClassClassesFound.add(new CodeSmell("LongParameterListClass", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(LongParameterListClassClassesFound);

	}
	
	
}
