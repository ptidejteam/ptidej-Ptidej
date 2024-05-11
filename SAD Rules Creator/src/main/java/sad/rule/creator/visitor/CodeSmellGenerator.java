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
package sad.rule.creator.visitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.Iterator;
import sad.rule.creator.model.IAggregation;
import sad.rule.creator.model.IAssociation;
import sad.rule.creator.model.IComposition;
import sad.rule.creator.model.IInheritance;
import sad.rule.creator.model.IMetric;
import sad.rule.creator.model.IOperator;
import sad.rule.creator.model.IRule;
import sad.rule.creator.model.IRuleCard;
import sad.rule.creator.model.ISemantic;
import sad.rule.creator.model.IStruct;
import sad.rule.creator.model.IVisitor;
import sad.rule.creator.utils.Constants;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/* 
 * This visitor generate all the source code
 * needed to detect anti-pattern and CodeSmell. 
 * 
 * We put block of code together to generate
 * the right code for a RuleCard detection.
 */

public class CodeSmellGenerator implements IVisitor {
	private IRule currentRule;

	// TODO: Very good idea to use a template!
	// TODO: Rather than putting all the logic here, and thus use lost of
	// if-then-else, couldn't we use polymorphism and ask each instance to
	// "print" out itself appropriately? Maybe it is not a good idea?...
	// TODO: Separate the templates from the rule cards better...

	// Tag to be replaced in the template files
	final private String className = "<CLASSNAME>";
	final private String codeSmellTAG = "<CODESMELL>";
	final private String detectMethodTAG = "<DETECT_METHOD>";
	final private String utilsMethodTAG = "<UTILS_METHOD>";
	final private String headerTAG = "<HEADER>";
	final private String innerClass1TAG = "<INNERCLASS1>";
	final private String innerClass2TAG = "<INNERCLASS2>";
	final private String operationTAG = "<OPERATION>";
	final private String localVars = "<LOCAL_VARS>";
	final private String metricName = "<METRIC>";
	final private String metricComputing = "<METRIC_COMPUTING>";
	final private String metricOrdinalValue = "<ORDINAL_VALUE>";
	final private String metricThreshold = "<THRESHOLD>";
	final private String metricNumValue = "<NUMERIC_VALUE>";
	final private String metricValue = "<METRIC_VALUE>";
	final private String metricFuzziness = "<FUZZINESS>";
	final private String metricComparisonOperator = "<COMPARISON_OPERATOR>";

	final private String structNumInterface = "<NB_INTERFACE>";
	final private String structRatioAccessor = "<RATIO_ACCESSOR>";
	final private String structRatioPrivateField = "<RATIO_PRIVATE_FIELD>";
	final private String structRatioPublicField = "<RATIO_PUBLIC_FIELD>";
	final private String structGlobalVariable = "<BOOLEAN_GLOBAL_VARIABLE>";
	final private String structClass = "<BOOLEAN_CLASS>";
	final private String structClassMethod = "<METHOD_CLASS>";

	// CodeSmell base templates
	final private String header =
		"../SAD Rules Creator/rsc/templates/CodeSmellHeader.txt";
	final private String singleFile =
		"../SAD Rules Creator/rsc/templates/CodeSmellDetector.txt";
	final private String combinedFile =
		"../SAD Rules Creator/rsc/templates/CodeSmellCombinedDetector.txt";

	// Where the generated files will be written
	final private String outputFileName =
		"../SAD/src/sad/codesmell/detection/repository/";
	final private String AntiPatternName;

	// Pre-defined code from structural elemets
	final private String MethodAccessor =
		"../SAD Rules Creator/rsc/templates/struct/MethodAccessor.txt";
	final private String MethodNoParam =
		"../SAD Rules Creator/rsc/templates/struct/MethodNoParam.txt";
	final private String GlobalVariable =
		"../SAD Rules Creator/rsc/templates/struct/GlobalVariable.txt";
	final private String DifferentParameter =
		"../SAD Rules Creator/rsc/templates/struct/DifferentParameter.txt";
	final private String OneMethod =
		"../SAD Rules Creator/rsc/templates/struct/OneMethod.txt";
	final private String PrivateField =
		"../SAD Rules Creator/rsc/templates/struct/PrivateField.txt";
	final private String PublicField =
		"../SAD Rules Creator/rsc/templates/struct/PublicField.txt";
	final private String MultipleInterface =
		"../SAD Rules Creator/rsc/templates/struct/MultipleInterface.txt";

	final private String metricLOC =
		"../SAD Rules Creator/rsc/templates/MetricLOC.txt";
	final private String metricNumericValue =
		"../SAD Rules Creator/rsc/templates/MetricNumericValue.txt";
	final private String metricBase =
		"../SAD Rules Creator/rsc/templates/MetricBase.txt";
	final private String classBase =
		"../SAD Rules Creator/rsc/templates/ClassBase.txt";

	public CodeSmellGenerator(final String Antipattern) {
		super();

		this.AntiPatternName = Antipattern;
	}

	/*
	 * Generate the threshold code for a metric 
	 */
	private StringBuffer calculateThreshold(
		final IMetric aMetric,
		final String metricName) {
		final StringBuffer threshold = new StringBuffer();

		threshold.append("\n\nHashMap thresholdMap = new HashMap();");

		switch (aMetric.getOrdinalValue()) {
			case Constants.VERY_HIGH :
				threshold.append("\nthresholdMap.put(\"" + metricName
						+ "_MaxBound\", Double.valueOf(boxPlot.getMaxBound()));");
				break;

			case Constants.HIGH : //
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_UpperQuartile\", Double.valueOf(boxPlot.getUpperQuartile()));");
				threshold.append("\nthresholdMap.put(\"" + metricName
						+ "_MaxBound\", Double.valueOf(boxPlot.getMaxBound()));");
				break;

			case Constants.MEDIUM :
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_InterQuartileRange\", Double.valueOf(boxPlot.getInterQuartileRange()));");
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_UpperQuartile\", Double.valueOf(boxPlot.getUpperQuartile()));");
				break;
			case Constants.LOW :
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_LowerQuartile\", Double.valueOf(boxPlot.getLowerQuartile()));");
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_InterQuartileRange\", Double.valueOf(boxPlot.getInterQuartileRange()));");
				break;

			case Constants.VERY_LOW :
				threshold.append("\nthresholdMap.put(\"" + metricName
						+ "_MinBound\", Double.valueOf(boxPlot.getMinBound()));");
				threshold
					.append("\nthresholdMap.put(\""
							+ metricName
							+ "_LowerQuartile\", Double.valueOf(boxPlot.getLowerQuartile()));");
				break;

			case Constants.NONE :
				// Dont know what to do
				threshold.append("\nthresholdMap.put(\"" + metricName
						+ "_None\", Double.valueOf(0));");
				break;

			default :
				break;
		}

		return threshold;
	}

	public void close(final IRule aRule) {
		// Nothing to do
	}

	public void close(final IRuleCard aRuleCard) {
		// Nothing to do
	}

	/*
	 * Generate the code to detect metric attribute.
	 * 
	 *  There's a lot of exception, a review should be made
	 *  to generalize the generation if possible.
	 */
	private StringBuffer generateMetricClass(
		final IMetric aMetric,
		final String aCodeSmellName) {

		final StringBuffer tempGeneratedCode = this.readFile(this.singleFile);
		this.replaceTAG(tempGeneratedCode, this.codeSmellTAG, aCodeSmellName
				+ "Detection");

		final StringBuffer detectMethod = new StringBuffer();

		final String metricName;
		if (aMetric.getOperator() == Constants.PLUS) {
			metricName =
				aMetric.getMetric1().getID() + "_"
						+ aMetric.getMetric2().getID();
		}
		else {
			metricName = aMetric.getID();
		}

		if (aMetric.getNumericValue() != 0) {
			detectMethod.append(this.readFile(this.metricNumericValue));
			this.replaceTAG(detectMethod, this.codeSmellTAG, aCodeSmellName);
			this.replaceTAG(detectMethod, this.metricName, metricName);

			this.replaceTAG(detectMethod, this.metricNumValue, Double.valueOf(
				aMetric.getNumericValue()).toString());

			final int compOp = aMetric.getComparisonOperator();
			if (compOp == Constants.INF) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					"<");
			}
			else if (compOp == Constants.INF_EQ) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					"<=");
			}
			else if (compOp == Constants.EQ) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					"==");
			}
			else if (compOp == Constants.SUP) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					">");
			}
			else if (compOp == Constants.SUP_EQ) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					">=");
			}
			else if (compOp == Constants.NOT_EQ) {
				this.replaceTAG(
					detectMethod,
					this.metricComparisonOperator,
					"!=");
			}

		}
		else {
			// Detect METHOD_LOC metric
			if (metricName.equals("METHOD_LOC")) {
				detectMethod.append(this.readFile(this.metricLOC));
				this
					.replaceTAG(detectMethod, this.codeSmellTAG, aCodeSmellName);
				this.replaceTAG(detectMethod, this.metricFuzziness, Double.valueOf(
					aMetric.getFuzziness()).toString());
				this.replaceTAG(detectMethod, this.metricThreshold, this
					.calculateThreshold(aMetric, metricName)
					.toString());

				this.replaceTAG(
					detectMethod,
					this.metricOrdinalValue,
					this.getOrdinalValue(aMetric.getOrdinalValue()));

			}
			else {
				detectMethod.append(this.readFile(this.metricBase));
				this
					.replaceTAG(detectMethod, this.codeSmellTAG, aCodeSmellName);
				this.replaceTAG(detectMethod, this.metricFuzziness, Double.valueOf(
					aMetric.getFuzziness()).toString());
				this.replaceTAG(detectMethod, this.metricName, metricName);

				StringBuffer temp = new StringBuffer();

				// To be changed in the Metric class
				if (aMetric.getOperator() == Constants.PLUS) {
					// Add the fisrt metric
					final String name1 = aMetric.getMetric1().getID();
					temp.append("\n	final double ");
					temp
						.append(name1
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ name1
								+ "\")).compute(anAbstractLevelModel, aClass);");

					// Add the second metric
					final String name2 = aMetric.getMetric2().getID();
					temp.append("\n	final double ");
					temp
						.append(name2
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ name2
								+ "\")).compute(anAbstractLevelModel, aClass);");

					temp.append("\n	mapOf" + aCodeSmellName
							+ "Values.put(aClass, new Double[] {new Double (");
					temp.append(name1 + " + " + name2 + "), Double.valueOf(0)});");
				}
				else {
					temp.append("\n	final double ");
					temp
						.append(metricName
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ metricName
								+ "\")).compute(anAbstractLevelModel, aClass);");

					temp.append("\n	mapOf" + aCodeSmellName
							+ "Values.put(aClass, new Double[] {Double.valueOf(");
					temp.append(metricName + "), Double.valueOf(0)});");
				}
				this.replaceTAG(
					detectMethod,
					this.metricComputing,
					temp.toString());
				this.replaceTAG(
					detectMethod,
					this.metricOrdinalValue,
					this.getOrdinalValue(aMetric.getOrdinalValue()));

				temp = new StringBuffer();

				//	To be changed in the Metric class
				if (aMetric.getOperator() == Constants.PLUS) {
					final String name1 = aMetric.getMetric1().getID();
					final String name2 = aMetric.getMetric2().getID();

					temp.append("\n	final double ");
					temp
						.append(name1
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ name1
								+ "\")).compute(anAbstractLevelModel, a"
								+ aCodeSmellName + "Class);");

					temp.append("\n	final double ");
					temp
						.append(name2
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ name2
								+ "\")).compute(anAbstractLevelModel, a"
								+ aCodeSmellName + "Class);");

					this.replaceTAG(detectMethod, this.metricValue, name1 + "+"
							+ name2);
				}
				else {
					temp.append("\n	final double ");
					temp
						.append(metricName
								+ " = ((IUnaryMetric) MetricsRepository.getInstance().getMetric(\""
								+ metricName
								+ "\")).compute(anAbstractLevelModel, a"
								+ aCodeSmellName + "Class);");

					this.replaceTAG(detectMethod, this.metricValue, metricName);
				}
				temp.append(this.calculateThreshold(aMetric, metricName));
				this.replaceTAG(
					detectMethod,
					this.metricThreshold,
					temp.toString());

			}
		}
		this.replaceTAG(
			tempGeneratedCode,
			this.detectMethodTAG,
			detectMethod.toString());
		this.replaceTAG(tempGeneratedCode, this.utilsMethodTAG, "");
		this.replaceTAG(tempGeneratedCode, this.localVars, "");
		return tempGeneratedCode;
	}

	/*
	 * Code to generate Semantic detection code.  
	 * 
	 */
	private StringBuffer generateSemantic(
		final ISemantic aSemantic,
		final String aCodeSmellName) {

		final StringBuffer tempGeneratedCode = this.readFile(this.singleFile);
		this.replaceTAG(tempGeneratedCode, this.codeSmellTAG, aCodeSmellName
				+ "Detection");

		final StringBuffer detectMethod = new StringBuffer();

		final String className = this.currentRule.getID();

		// Generate the List of string to check
		final StringBuffer wordToCheck = new StringBuffer();
		final Iterator it = aSemantic.getSemanticValues().iterator();
		while (it.hasNext()) {
			wordToCheck.append("\"" + it.next() + "\"");
			if (it.hasNext()) {
				wordToCheck.append(",");
			}
		}
		detectMethod.append("\nString[] CTRL_NAME = new String[]{"
				+ wordToCheck + "};");

		detectMethod.append("\nfinal Set " + className
				+ "sFound = new HashSet();");
		detectMethod
			.append("\nfinal Iterator iter = anAbstractLevelModel.getIteratorOnTopLevelEntities();");
		detectMethod.append("\nwhile (iter.hasNext()) {");
		detectMethod.append("\n	final IEntity entity = (IEntity) iter.next();");
		detectMethod.append("\n	if (entity instanceof IClass) {");
		detectMethod.append("\n	final IClass aClass = (IClass) entity;");
		detectMethod.append("\n	boolean is" + className + " = false;");

		final StringBuffer properties = new StringBuffer();

		switch (aSemantic.getSemanticType()) {
			case Constants.CLASS_NAME :
				detectMethod.append("\n	// we check the names of classes");
				detectMethod.append("\n\nString detectedKeyword = \"\";");

				detectMethod.append("\n	for (int i = 0; i < CTRL_NAME.length");
				detectMethod.append("\n			&& !is" + className + "; i++) {");
				detectMethod
					.append("\n		if (aClass.getDisplayName().indexOf(CTRL_NAME[i]) > -1) {");
				detectMethod.append("\n		is" + className + " = true;");
				detectMethod.append("\n		detectedKeyword = CTRL_NAME[i];");
				detectMethod.append("\n		}");
				detectMethod.append("\n	}");

				properties
					.append("\nClassProperty classProp = new ClassProperty(aClass);");
				properties.append("\ntry {");
				properties
					.append("\nclassProp.addProperty(new SemanticProperty(");
				properties.append("\ndetectedKeyword));");
				properties.append("\n}");
				properties.append("\ncatch (Exception e) {");
				properties.append("\n// TODO: Auto generated");
				properties.append("\n}");

				properties.append("\n" + className
						+ "sFound.add(new CodeSmell(\"" + className
						+ "\", \"\", classProp));");
				break;

			case Constants.METHOD_NAME :
				detectMethod.append("\n// we check the names of methods");
				detectMethod.append("\n\nString detectedKeyword = \"\";");
				detectMethod.append("\nIMethod detectedMethod = null;\n");

				detectMethod
					.append("\n\nfinal Iterator iteratorMethods = aClass");
				detectMethod
					.append("\n		.getIteratorOnConstituents(IMethod.class);");
				detectMethod.append("\nwhile (iteratorMethods.hasNext() && !is"
						+ className + ") {");
				detectMethod
					.append("\n		final IMethod method = (IMethod) iteratorMethods.next();");

				detectMethod.append("\n		for (int i = 0; i < CTRL_NAME.length");
				detectMethod.append("\n			&& !is" + className + "; i++) {");
				detectMethod
					.append("\n			if (method.getDisplayName().startsWith(CTRL_NAME[i])) {");
				detectMethod.append("\n				is" + className + " = true;");
				detectMethod.append("\n				detectedKeyword = CTRL_NAME[i];");
				detectMethod.append("\n				detectedMethod = method;");
				detectMethod.append("\n			}");
				detectMethod.append("\n		}");
				detectMethod.append("\n}");

				properties
					.append("\nClassProperty classProp = new ClassProperty(aClass);");
				properties.append("\ntry {");
				properties.append("\nMethodProperty mp = new MethodProperty(");
				properties.append("\ndetectedMethod);");
				properties.append("\nmp.addProperty(new SemanticProperty(");
				properties.append("\ndetectedKeyword));");
				properties.append("\nclassProp.addProperty(mp);");
				properties.append("\n}");
				properties.append("\ncatch (Exception e) {");
				properties.append("\n// TODO: Auto generated");
				properties.append("\n}");

				properties.append("\n" + className
						+ "sFound.add(new CodeSmell(\"" + className
						+ "\", \"\", classProp));");

				break;
			default :
				break;
		}

		detectMethod.append("\n	if (is" + className + ") {");
		detectMethod.append("\n\n" + properties + "\n");
		detectMethod.append("\n	}");
		detectMethod.append("\n	}");
		detectMethod.append("\n}");
		detectMethod.append("\nthis.setSetOfSmells(" + className + "sFound);");
		// detectMethod.append("\nreturn " + className + "sFound;");

		this.replaceTAG(
			tempGeneratedCode,
			this.detectMethodTAG,
			detectMethod.toString());
		this.replaceTAG(tempGeneratedCode, this.utilsMethodTAG, "");
		this.replaceTAG(tempGeneratedCode, this.localVars, "");

		return tempGeneratedCode;
	}
	/* 
	 * Generate the code to detect a structural element.
	 * 
	 * Right now the BNF grammar is too "loose" and the 
	 * correctness of the generated code depend on the 
	 * argument specified on the RulesCard.  
	 * 
	 */
	private StringBuffer generateStruct(
		final IStruct aStruct,
		final String aCodeSmellName) {

		StringBuffer GeneratedCode = new StringBuffer();

		switch (aStruct.getType()) {
			case Constants.METHOD_ACCESSOR :
				GeneratedCode = this.readFile(this.MethodAccessor);
				this.replaceTAG(
					GeneratedCode,
					this.structRatioAccessor,
					Integer.toString(aStruct.getValue()));
				break;
			case Constants.METHOD_NO_PARAM :
				GeneratedCode = this.readFile(this.MethodNoParam);
				break;
			case Constants.GLOBAL_VARIABLE :
				GeneratedCode = this.readFile(this.GlobalVariable);
				this.replaceTAG(
					GeneratedCode,
					this.structGlobalVariable,
					Integer.toString(aStruct.getValue()));

				break;
			case Constants.IS_ABSTRACT :
				GeneratedCode = this.readFile(this.classBase);
				this.replaceTAG(
					GeneratedCode,
					this.structClass,
					Integer.toString(aStruct.getValue()));
				this.replaceTAG(
					GeneratedCode,
					this.structClassMethod,
					"isAbstract()");

				break;
			case Constants.DIFFERENT_PARAMETER :
				GeneratedCode = this.readFile(this.DifferentParameter);
				break;
			case Constants.ONE_METHOD :
				GeneratedCode = this.readFile(this.OneMethod);
				break;
			case Constants.PRIVATE_FIELD :
				GeneratedCode = this.readFile(this.PrivateField);
				this.replaceTAG(
					GeneratedCode,
					this.structRatioPrivateField,
					Integer.toString(aStruct.getValue()));
				break;
			case Constants.PUBLIC_FIELD :
				GeneratedCode = this.readFile(this.PublicField);
				this.replaceTAG(
					GeneratedCode,
					this.structRatioPublicField,
					Integer.toString(aStruct.getValue()));
				break;
			case Constants.MULTIPLE_INTERFACE :
				GeneratedCode = this.readFile(this.MultipleInterface);
				this.replaceTAG(
					GeneratedCode,
					this.structNumInterface,
					Integer.toString(aStruct.getValue()));
				break;
		}

		this.replaceTAG(GeneratedCode, this.codeSmellTAG, aCodeSmellName);
		this.replaceTAG(GeneratedCode, this.className, this.AntiPatternName);

		return GeneratedCode;
	}

	private String getOrdinalValue(final int value) {
		switch (value) {
			case Constants.VERY_HIGH :
				return "getHighOutliers";

			case Constants.HIGH :
				return "getHighValues";

			case Constants.MEDIUM :
				return "getNormalValues";

			case Constants.LOW :
				return "getLowValues";

			case Constants.VERY_LOW :
				return "getLowOutliers";

			case Constants.NONE :
				// Dont know what to do
				return "getLowOutliers";

			default :
				return "";
		}
	}

	public Object getResult() {
		return null;
	}

	public void open(final IRule aRule) {
		this.currentRule = aRule;
	}

	public void open(final IRuleCard aRuleCard) {
		// Nothing to do
	}

	private void println(final String typeElement, final Object o) {
		System.out.println(typeElement + " : " + o);
	}

	/*
	 * Utility method to read a file from the disk 
	 */
	private StringBuffer readFile(final String fileName) {

		final StringBuffer buffer = new StringBuffer();

		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					fileName)));

			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return buffer;
	}

	/*
	 * Utility method to replace all occurance 
	 * of a specified TAG into a StringBuffer 
	 */
	private void replaceTAG(
		final StringBuffer source,
		final String TAG,
		final String replaceWith) {
		while (source.indexOf(TAG) >= 0) {
			source.replace(
				source.indexOf(TAG),
				source.indexOf(TAG) + TAG.length(),
				replaceWith);
		}
	}

	public void setSourceFile(final String fileName) {
	}

	public void visit(final IAggregation anAggregation) {
		this.visit((IAssociation) anAggregation);
	}

	public void visit(final IAssociation anAssociation) {
		// Nothing to do
	}

	public void visit(final IComposition aComposition) {
		this.visit((IAssociation) aComposition);
	}

	public void visit(final IInheritance anInheritance) {
		// Nothing to do
	}

	public void visit(final IMetric aMetric) {
		// This method is called when the Metric constituent is used as 
		// terminal rule. So we can generate and write the detector.
		final StringBuffer classGeneratedCode =
			this.generateMetricClass(aMetric, this.currentRule.getID());
		this.replaceTAG(
			classGeneratedCode,
			this.headerTAG,
			this.readFile(this.header).toString());

		this.replaceTAG(
			classGeneratedCode,
			this.className,
			this.AntiPatternName);

		try {
			// Ensure the directory is created
			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	(new File(this.outputFileName + this.AntiPatternName)).mkdirs();

			final Writer fw =
				ProxyDisk.getInstance().fileAbsoluteOutput(
					this.outputFileName + this.AntiPatternName + "/"
							+ this.currentRule.getID() + "Detection.java",
					false);
			fw.write(classGeneratedCode.toString());
			fw.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void visit(final IOperator anOperator) {
		this.println("visit IOperator :", anOperator);
		if (!(anOperator.getOperand1() instanceof IRule)) {
			StringBuffer innerClass1 = new StringBuffer();
			StringBuffer innerClass2 = new StringBuffer();

			if (anOperator.getOperand1() instanceof IMetric) {
				innerClass1 =
					this.generateMetricClass(
						(IMetric) anOperator.getOperand1(),
						"OPERAND1");
				this.replaceTAG(innerClass1, this.headerTAG, "");
			}
			else if (anOperator.getOperand1() instanceof IStruct) {
				innerClass1 =
					this.generateStruct(
						(IStruct) anOperator.getOperand1(),
						"OPERAND1");
				this.replaceTAG(innerClass1, this.headerTAG, "");
			}
			else if (anOperator.getOperand1() instanceof ISemantic) {
				innerClass1 =
					this.generateSemantic(
						(ISemantic) anOperator.getOperand1(),
						"OPERAND1");
				this.replaceTAG(innerClass1, this.headerTAG, "");
			}

			if (anOperator.getOperand2() instanceof IMetric) {
				innerClass2 =
					this.generateMetricClass(
						(IMetric) anOperator.getOperand2(),
						"OPERAND2");
				this.replaceTAG(innerClass2, this.headerTAG, "");
			}
			else if (anOperator.getOperand2() instanceof IStruct) {
				innerClass2 =
					this.generateStruct(
						(IStruct) anOperator.getOperand2(),
						"OPERAND2");
				this.replaceTAG(innerClass2, this.headerTAG, "");
			}
			else if (anOperator.getOperand2() instanceof ISemantic) {
				innerClass2 =
					this.generateSemantic(
						(ISemantic) anOperator.getOperand2(),
						"OPERAND2");
				this.replaceTAG(innerClass2, this.headerTAG, "");
			}

			// Here we have build our two innerclass,
			final StringBuffer tempGeneratedCode =
				this.readFile(this.combinedFile);
			this.replaceTAG(
				tempGeneratedCode,
				this.headerTAG,
				this.readFile(this.header).toString());
			this.replaceTAG(
				tempGeneratedCode,
				this.className,
				this.AntiPatternName);

			this.replaceTAG(
				tempGeneratedCode,
				this.codeSmellTAG,
				this.currentRule.getID() + "Detection");

			this.replaceTAG(
				tempGeneratedCode,
				this.innerClass1TAG,
				innerClass1.toString());
			this.replaceTAG(
				tempGeneratedCode,
				this.innerClass2TAG,
				innerClass2.toString());

			String operation = "";
			switch (anOperator.getOperatorType()) {
				case Constants.OPERATOR_UNION :
					operation = "union";
					break;
				case Constants.OPERATOR_INTER :
					operation = "intersection";
					break;
				default :
					break;
			}
			this.replaceTAG(tempGeneratedCode, this.operationTAG, operation);

			try {
				// Ensure the directory is created
				// Yann 2013/05/30
				// Not necessary thanks to ProxyDisk
				//	(new File(this.outputFileName + this.AntiPatternName)).mkdirs();

				final Writer fw =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						this.outputFileName + this.AntiPatternName + "/"
								+ this.currentRule.getID() + "Detection.java",
						false);
				fw.write(tempGeneratedCode.toString());
				fw.close();
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		else {
			this.println("UNHANDLED CASE!", anOperator);
		}
	}

	public void visit(final ISemantic aSemantic) {
		// This method is called when the Semantic constituent is used as 
		// terminal rule.  So we can generate and write the détector.
		final StringBuffer classGeneratedCode =
			this.generateSemantic(aSemantic, this.currentRule.getID());
		this.replaceTAG(
			classGeneratedCode,
			this.headerTAG,
			this.readFile(this.header).toString());
		this.replaceTAG(
			classGeneratedCode,
			this.className,
			this.AntiPatternName);

		try {
			// Ensure the directory is created
			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	(new File(this.outputFileName + this.AntiPatternName)).mkdirs();

			final Writer fw =
				ProxyDisk.getInstance().fileAbsoluteOutput(
					this.outputFileName + this.AntiPatternName + "/"
							+ this.currentRule.getID() + "Detection.java",
					false);
			fw.write(classGeneratedCode.toString());
			fw.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void visit(final IStruct aStruct) {
		// This method is called when the Structural constituent is used as 
		// terminal rule.  So we can generate and write the détector.
		final StringBuffer classGeneratedCode =
			this.generateStruct(aStruct, this.currentRule.getID());
		this.replaceTAG(
			classGeneratedCode,
			this.headerTAG,
			this.readFile(this.header).toString());
		this.replaceTAG(
			classGeneratedCode,
			this.className,
			this.AntiPatternName);

		try {
			// Ensure the directory is created
			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	(new File(this.outputFileName + this.AntiPatternName)).mkdirs();

			final Writer fw =
				ProxyDisk.getInstance().fileAbsoluteOutput(
					this.outputFileName + this.AntiPatternName + "/"
							+ this.currentRule.getID() + "Detection.java",
					false);
			fw.write(classGeneratedCode.toString());
			fw.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
