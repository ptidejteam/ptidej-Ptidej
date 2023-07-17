package com.sdmetrics.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.metrics.Metric;
import com.sdmetrics.metrics.MetricStore;
import com.sdmetrics.metrics.MetricsEngine;
import com.sdmetrics.metrics.PrivilegedMetricsAccess;
import com.sdmetrics.metrics.Rule;
import com.sdmetrics.metrics.RuleEngine;
import com.sdmetrics.metrics.RuleViolation;
import com.sdmetrics.metrics.SDMetricsException;
import com.sdmetrics.metrics.Set;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

/**
 * Convenience class to read a test fixture consisting of metamodel, metric
 * definitions, XMI transformations, and XMI file. Provides access to the model
 * and metrics.
 */
public class MetricTestContext {

	public MetricsEngine me;
	public MetaModel mm;
	public Model model;
	public MetricStore ms;
	public RuleEngine re;
	private ExpressionParser expressionParser = new ExpressionParser();

	public static MetricTestContext getStandardContext04() throws Exception {
		return getStandardContext(Utils.TEST_METRICS_DIR + "testMetrics04.xml");
	}

	public static MetricTestContext getStandardContext(String metrics)
			throws Exception {
		String dirBase = Utils.TEST_METRICS_DIR;
		return new MetricTestContext(dirBase + "testMetaModel04.xml", metrics,
				dirBase + "testXMITransformations04.xml", dirBase
						+ "testModel04.xmi");
	}

	public MetricTestContext(String metaModel, String metrics, String xmiTrans,
			String xmi) throws Exception {
		mm = new MetaModel();
		XMLParser parser = new XMLParser();
		parser.parse(metaModel, mm.getSAXParserHandler());

		ms = new MetricStore(mm);
		parser.parse(metrics, ms.getSAXParserHandler());
		expressionParser = PrivilegedMetricsAccess.getExpressionParser(ms);

		XMITransformations trans = new XMITransformations(mm);
		parser.parse(xmiTrans, trans.getSAXParserHandler());

		model = new Model(mm);
		XMIReader xmir = new XMIReader(trans, model);
		parser.parse(xmi, xmir);

		me = new MetricsEngine(ms, model);
		re = new RuleEngine(me);
	}

	public MetaModelElement getType(String typeName) {
		MetaModelElement type = mm.getType(typeName);
		if (type == null)
			fail("No element type " + typeName);
		return type;
	}

	public ModelElement getElement(String elementName, String typeName) {
		List<ModelElement> candidates = model.getElements(getType(typeName));
		for (ModelElement candidate : candidates)
			if (elementName.equals(candidate.getName()))
				return candidate;
		fail("No element " + elementName + " for type " + typeName);
		return null;
	}

	public ModelElement getElement(int index, String typeName) {
		List<ModelElement> candidates = model.getElements(getType(typeName));
		if (index >= candidates.size())
			fail("No element index " + index + " for type " + typeName
					+ " (max. " + candidates.size() + ")");

		return candidates.get(index);
	}

	public ExpressionNode parse(String expression) {
		ExpressionNode result = expressionParser.parseExpression(expression);
		assertNotNull(expressionParser.getErrorInfo(), result);
		return result;
	}

	public Metric getMetric(String metricName, String typeName) {
		Metric result = ms.getMetric(getType(typeName), metricName);
		if (result == null)
			fail("No metric " + metricName + " for type " + typeName);
		return result;
	}

	public Set getSet(String setName, String typeName) {
		Set result = ms.getSet(getType(typeName), setName);
		if (result == null)
			fail("No set " + setName + " for type " + typeName);
		return result;
	}

	public Rule getRule(String ruleName, String typeName) {
		Collection<Rule> rules = ms.getRules(getType(typeName));
		for (Rule r : rules)
			if (r.getName().equals(ruleName))
				return r;
		fail("No rule " + ruleName + " for type " + typeName);
		return null;
	}

	public float getMetricValue(String metricName, String elementName,
			String typeName) throws SDMetricsException {
		return ((Number) me.getMetricValue(getElement(elementName, typeName),
				getMetric(metricName, typeName))).floatValue();
	}

	public String getMetricStrValue(String metricName, String elementName,
			String typeName) throws SDMetricsException {
		return me.getMetricValue(getElement(elementName, typeName),
				getMetric(metricName, typeName)).toString();
	}

	public Collection<?> getSetValue(String setName, String elementName,
			String typeName) throws SDMetricsException {
		return me.getSet(getElement(elementName, typeName),
				getSet(setName, typeName));
	}

	public void assertViolation(String elemName, String ruleName,
			String typeName, Object... values) throws SDMetricsException {
		ModelElement element = getElement(elemName, typeName);
		Rule rule = getRule(ruleName, typeName);
		List<RuleViolation> violations = re.checkRule(element, rule);

		assertTrue(violations.size() == values.length);

		for (RuleViolation violation : violations) {
			assertSame(element, violation.getModelElement());
			assertSame(rule, violation.getRule());
			boolean admissibleValue = false;
			for (Object value : values) {
				if ((value == null && violation.getValue() == null)
						|| (value != null && value.equals(violation.getValue())))
					admissibleValue = true;
			}

			assertTrue("Violation with value: " + violation.getValue(),
					admissibleValue);
		}
	}

	public void assertCompliance(String elemName, String ruleName,
			String typeName) throws SDMetricsException {
		ModelElement element = getElement(elemName, typeName);
		Rule rule = getRule(ruleName, typeName);
		List<RuleViolation> violations = re.checkRule(element, rule);

		assertTrue("Model element " + elemName + " of type " + typeName
				+ " violates rule " + ruleName, violations.isEmpty());
	}
}
