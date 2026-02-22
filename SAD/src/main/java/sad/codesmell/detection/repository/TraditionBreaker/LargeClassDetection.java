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


public class LargeClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "LargeClassDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set LargeClassClassesFound = new HashSet();

		final HashMap mapOfLargeClassValues = new HashMap();
		boolean thereIsLargeClass = false;

		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				thereIsLargeClass = true;

				
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aClass);
	mapOfLargeClassValues.put(aClass, new Double[] {Double.valueOf (NMD + NAD), Double.valueOf(0)});
				//final double NMD_NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD_NAD")).compute(anAbstractLevelModel, aClass);
				//mapOfLargeClassValues.put(aClass, Double.valueOf(NMD_NAD));
			}
		}

		if (thereIsLargeClass == true) {

			BoxPlot boxPlot = new BoxPlot(mapOfLargeClassValues, 10.0);
			setBoxPlot(boxPlot);

			final Map mapOfLargeClassClassesFromBoxPlot = boxPlot.getLowValues();
			final Iterator iter3 = mapOfLargeClassClassesFromBoxPlot
				.keySet()
				.iterator();

			while (iter3.hasNext()) {
				final IClass aLargeClassClass = (IClass) iter3.next();
				try {
					ClassProperty classProp = new ClassProperty(aLargeClassClass);
					
					
	final double NMD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMD")).compute(anAbstractLevelModel, aLargeClassClass);
	final double NAD = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("NAD")).compute(anAbstractLevelModel, aLargeClassClass);

HashMap thresholdMap = new HashMap();
thresholdMap.put("NMD_NAD_LowerQuartile", Double.valueOf(boxPlot.getLowerQuartile()));
thresholdMap.put("NMD_NAD_InterQuartileRange", Double.valueOf(boxPlot.getInterQuartileRange()));
					final Double fuzziness = ((Double[])mapOfLargeClassClassesFromBoxPlot.get(aLargeClassClass))[1];
					classProp.addProperty(new MetricProperty("NMD_NAD", 
						NMD+NAD, 
						thresholdMap, fuzziness.doubleValue()));
					
					LargeClassClassesFound.add(new CodeSmell("LargeClass", "", classProp));
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}

		this.setSetOfSmells(LargeClassClassesFound);

	}
	
	
}
