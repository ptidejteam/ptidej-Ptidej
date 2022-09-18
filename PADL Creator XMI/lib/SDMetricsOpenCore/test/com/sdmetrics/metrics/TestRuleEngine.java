package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestRuleEngine {

	MetricTestContext mtc;
	RuleEngine re;

	@Before
	public void loadFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		re = mtc.re;
	}

	@Test
	public void ruleExemption() throws SDMetricsException {
		Collection<String> exemptions = re.collectExemptedRules(mtc.getElement(
				"firstName", "attribute"));
		assertTrue(exemptions.isEmpty());

		exemptions = re
				.collectExemptedRules(mtc.getElement("Cruiser", "class"));
		assertTrue(exemptions.isEmpty());

		exemptions = re.collectExemptedRules(mtc.getElement("Motorcycle",
				"class"));
		assertEquals(3, exemptions.size());
		assertTrue(exemptions.contains("NameTooLong"));
		assertTrue(exemptions.contains("RightHandRule"));
		assertTrue(exemptions.contains("CommonSense"));
	}

	@Test
	public void ruleCheck() throws SDMetricsException {
		mtc.assertViolation("Person", "HasAttributes", "class", "firstName",
				"lastName");
		mtc.assertCompliance("Person", "NameTooLong", "class");
	}

	@Test
	public void errorHandling() {
		Rule rule = mtc.getRule("NameTooLong", "class");
		rule.getAttributes()
				.setExpressionNode("condition", mtc.parse("IQ>125"));

		ModelElement person = mtc.getElement("Person", "class");
		try {
			mtc.re.checkRule(person, rule);
			fail("SDMetrics Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"No metric or single-valued attribute 'IQ' for elements of type 'class'.",
					ex.getMessage());
			assertSame(rule, ex.getMetricEntry());
			assertSame(person, ex.getElement());
		}
	}

	@Test
	public void illegalRule() {
		Rule rule = mtc.getRule("NameTooLong", "class");
		rule.setAttributes(null);
		ModelElement person = mtc.getElement("Person", "class");
		try {
			mtc.re.checkRule(person, rule);
			fail("SDMetrics Exception expected");
		} catch (SDMetricsException ex) {
			assertTrue(ex.getMessage().startsWith(
					"Internal metrics engine failure"));
			assertSame(rule, ex.getMetricEntry());
			assertSame(person, ex.getElement());
			assertEquals(NullPointerException.class, ex.getCause().getClass());
		}
	}

	@Test
	public void cacheAccess() {
		re.getValuesCache().put("property tax", "five grand");
		re.clearValuesCache();
		assertFalse(re.getValuesCache().containsKey("property tax"));
	}
}
