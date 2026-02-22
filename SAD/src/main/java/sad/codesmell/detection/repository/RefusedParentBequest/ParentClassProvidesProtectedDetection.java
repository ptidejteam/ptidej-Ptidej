package sad.codesmell.detection.repository.RefusedParentBequest;

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


public class ParentClassProvidesProtectedDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "ParentClassProvidesProtectedDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final Set classesParentClassProvidesProtected = new HashSet();
		
		final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			// Yann 26/02/20: IGhosts are both IClass and IInterface!
			// I must exclude IGhost when not desirable to be included.
			if (entity instanceof IClass && !(entity instanceof IGhost)) {
				final IClass aClass = (IClass) entity;
				final double USELESS = ((IUnaryMetric) MetricsRepository.getInstance().getMetric("USELESS")).compute(anAbstractLevelModel, aClass);
				
				if (USELESS == 1.0) {
					try {
						CodeSmell dc = new CodeSmell(
							"ParentClassProvidesProtected", "", 
							new ClassProperty(aClass));
						
						HashMap thresholdMap = new HashMap();
						thresholdMap.put("USELESS", Double.valueOf(1.0));
						
						dc.getClassProperty().addProperty(new MetricProperty("USELESS", 
							((IUnaryMetric) MetricsRepository.getInstance().getMetric("USELESS")).compute(anAbstractLevelModel, aClass), thresholdMap));
						
						classesParentClassProvidesProtected.add(dc);
					}
					catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		this.setSetOfSmells(classesParentClassProvidesProtected);
		// return classesParentClassProvidesProtected;

	}
	
	
}
