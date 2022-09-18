package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;
import com.sdmetrics.test.RuleProcedureVerbObject;


public class TestRuleProcedure {

	MetricTestContext mtc;
	ProcedureAttributes procAttrs;
	RuleProcedure procedure;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();
		procedure = new RuleProcedureVerbObject();
		procedure.setRuleEngine(mtc.re);
	}

	@Test
	public void rigging() {
		assertEquals(mtc.re, procedure.getRuleEngine());
		assertEquals(mtc.me, procedure.getMetricsEngine());

		procedure.clear();
		assertNull(procedure.getRuleEngine());
		assertNull(procedure.getMetricsEngine());
	}

	@Test
	public void minCount() throws SDMetricsException {
		ModelElement person = mtc.getElement("Person", "class");
		assertEquals(1, procedure.getMinExpressionValue(person, procAttrs,
				"testmin", null));
		procAttrs.setExpressionNode("testmin", mtc.parse("_var"));
		Variables vars = new Variables(null);
		vars.setVariable("_var", Integer.valueOf(2));
		assertEquals(2, procedure.getMinExpressionValue(person, procAttrs,
				"testmin", vars));
	}

	@Test
	public void ruleValueAccess() throws SDMetricsException {
		ModelElement person = mtc.getElement("Person", "class");
		assertNull(procedure.getRuleValue(person, procAttrs, null));
		procAttrs.setExpressionNode("value", mtc.parse("_var"));
		Variables vars = new Variables(null);
		vars.setVariable("_var", "FSM");
		assertEquals("FSM", procedure.getRuleValue(person, procAttrs, vars));
	}
}
