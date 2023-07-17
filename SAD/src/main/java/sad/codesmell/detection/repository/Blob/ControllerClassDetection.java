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
package sad.codesmell.detection.repository.Blob;

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
 * This class represents the detection of the code smell ControllerClassDetection
 * 
 * @author Auto generated
 *
 */

import sad.util.OperatorsCodeSmells;

public class ControllerClassDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	private final OperatorsCodeSmells operators;

	public ControllerClassDetection() {
		super();
		this.operators = OperatorsCodeSmells.getInstance();
	}

	public String getName() {
		return "ControllerClassDetection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		final OPERAND1Detection op1 = new OPERAND1Detection();
		op1.detect(anAbstractLevelModel);
		final Set setOperand1 = op1.getCodeSmells();

		final OPERAND2Detection op2 = new OPERAND2Detection();
		op2.detect(anAbstractLevelModel);
		final Set setOperand2 = op2.getCodeSmells();

		final Set setOperation = this.operators.union(setOperand1, setOperand2);
		this.setSetOfSmells(setOperation);
	}

	

public class OPERAND1Detection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "OPERAND1Detection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		
String[] CTRL_NAME = new String[]{"Drive","Manage","UI","Proc","Cmd","Command","Ctrl","Control","Process"};
final Set ControllerClasssFound = new HashSet();
final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
while (iter.hasNext()) {
	final IEntity entity = (IEntity) iter.next();
	if (entity instanceof IClass) {
	final IClass aClass = (IClass) entity;
	boolean isControllerClass = false;
// we check the names of methods

String detectedKeyword = "";
IMethod detectedMethod = null;


final Iterator iteratorMethods = aClass
		.getIteratorOnConstituents(IMethod.class);
while (iteratorMethods.hasNext() && !isControllerClass) {
		final IMethod method = (IMethod) iteratorMethods.next();
		for (int i = 0; i < CTRL_NAME.length
			&& !isControllerClass; i++) {
			if (method.getDisplayName().startsWith(CTRL_NAME[i])) {
				isControllerClass = true;
				detectedKeyword = CTRL_NAME[i];
				detectedMethod = method;
			}
		}
}
	if (isControllerClass) {


ClassProperty classProp = new ClassProperty(aClass);
try {
MethodProperty mp = new MethodProperty(
detectedMethod);
mp.addProperty(new SemanticProperty(
detectedKeyword));
classProp.addProperty(mp);
}
catch (Exception e) {
// TODO: Auto generated
}
ControllerClasssFound.add(new CodeSmell("ControllerClass", "", classProp));

	}
	}
}
this.setSetOfSmells(ControllerClasssFound);
	}
	
	
}

	
	

public class OPERAND2Detection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	
	
	public String getName() {
		return "OPERAND2Detection";
	}

	public void detect(final IAbstractLevelModel anAbstractLevelModel) {
		
String[] CTRL_NAME = new String[]{"Subsystem","System","Drive","Manage","UI","Proc","Cmd","Command","Ctrl","Control","Process"};
final Set ControllerClasssFound = new HashSet();
final Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();
while (iter.hasNext()) {
	final IEntity entity = (IEntity) iter.next();
	if (entity instanceof IClass) {
	final IClass aClass = (IClass) entity;
	boolean isControllerClass = false;
	// we check the names of classes

String detectedKeyword = "";
	for (int i = 0; i < CTRL_NAME.length
			&& !isControllerClass; i++) {
		if (aClass.getDisplayName().indexOf(CTRL_NAME[i]) > -1) {
		isControllerClass = true;
		detectedKeyword = CTRL_NAME[i];
		}
	}
	if (isControllerClass) {


ClassProperty classProp = new ClassProperty(aClass);
try {
classProp.addProperty(new SemanticProperty(
detectedKeyword));
}
catch (Exception e) {
// TODO: Auto generated
}
ControllerClasssFound.add(new CodeSmell("ControllerClass", "", classProp));

	}
	}
}
this.setSetOfSmells(ControllerClasssFound);
	}
	
	
}


}
