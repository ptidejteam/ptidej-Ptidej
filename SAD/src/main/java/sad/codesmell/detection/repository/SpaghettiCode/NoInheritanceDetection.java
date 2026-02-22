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


public class NoInheritanceDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "NoInheritanceDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesNoInheritance = new HashSet();
		
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				final double DIT = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("DIT")).compute(anAbstractLevelModel, aClass);
				
				if (DIT <= 2.0) {
					try {
						CodeSmell dc = new CodeSmell(
							"NoInheritance", "", 
							new ClassProperty(aClass));
						
						HashMap thresholdMap = new HashMap();
						thresholdMap.put("DIT", Double.valueOf(2.0));
						
						dc.getClassProperty().addProperty(new MetricProperty("DIT", 
							((IUnaryMetric) MetricsRepository.getInstance().getMetric("DIT")).compute(anAbstractLevelModel, aClass), thresholdMap));
						
						classesNoInheritance.add(dc);
					}
					catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		this.setSetOfSmells(classesNoInheritance);
		// return classesNoInheritance;

	}
	
	
}
