package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.math.ExpressionParser;


public class TestProcedureAttributes {

	ProcedureAttributes procAttr = new ProcedureAttributes();
	ExpressionParser parser = new ExpressionParser();

	@Before
	public void initMetricModel() {
		procAttr.setExpressionNode("visibility", parser.parseExpression("low"));
		procAttr.setExpressionNode("thirdBinomialFormula",
				parser.parseExpression("(a+b)*(a-b)=(a^2-b^2)"));
		procAttr.setExpressionNode("isAbstract", parser.parseExpression("true"));
		procAttr.setExpressionNode("isFinal", parser.parseExpression("false"));
	}

	@Test
	public void optionalStringValues() throws SDMetricsException {
		assertEquals("low", procAttr.getStringValue("visibility"));
		assertEquals(null, procAttr.getStringValue("winds"));

		try {
			procAttr.getStringValue("thirdBinomialFormula");
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Attribute 'thirdBinomialFormula' may not contain operators.",
					ex.getMessage());
		}
	}

	@Test
	public void requiredStringValues() throws SDMetricsException {
		assertEquals("low", procAttr.getRequiredStringValue("visibility"));

		try {
			procAttr.getRequiredStringValue("winds");
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Missing required attribute 'winds'.", ex.getMessage());
		}
	}

	@Test
	public void getBooleanAttribute() throws SDMetricsException {
		assertTrue(procAttr.getBooleanValue("isAbstract", false));
		assertFalse(procAttr.getBooleanValue("isFinal", true));
		assertTrue(procAttr.getBooleanValue("isBoring", true));
		assertFalse(procAttr.getBooleanValue("isBoring", false));
	}

	@Test
	public void optionalExpressions() throws SDMetricsException {
		assertEquals("=", procAttr.getExpression("thirdBinomialFormula")
				.getValue());
		assertEquals(null, procAttr.getExpression("secondBinomialFormula"));
	}

	@Test
	public void requiredExpressions() throws SDMetricsException {
		assertEquals("=", procAttr
				.getRequiredExpression("thirdBinomialFormula").getValue());

		try {
			procAttr.getRequiredExpression("secondBinomialFormula");
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Missing required attribute 'secondBinomialFormula'.",
					ex.getMessage());
		}
	}
}
