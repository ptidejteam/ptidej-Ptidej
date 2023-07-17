package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureCompare {

	MetricTestContext mtc;
	ProcedureAttributes procAttrs;
	Metric metric;
	MetricProcedureCompare procedure;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();
		procAttrs.setExpressionNode("set", mtc.parse("Descendents"));
		procAttrs.setExpressionNode("comp",
				mtc.parse("term.NumOps<with.NumOps"));

		metric = new Metric("test", mtc.getType("class"), null);
		metric.setAttributes(procAttrs);
		procedure = new MetricProcedureCompare();
		procedure.setMetricsEngine(mtc.me);
	}

	@Test
	public void count() throws SDMetricsException {
		assertEquals(Integer.valueOf(2),
				procedure.calculate(mtc.getElement("Vehicle", "class"), metric));
	}

	@Test
	public void element() throws SDMetricsException {
		procAttrs.setExpressionNode("return_element", mtc.parse("true"));
		assertEquals(mtc.getElement("Cruiser", "class"), procedure.calculate(
				mtc.getElement("Motorcycle", "class"), metric));

		assertEquals("",
				procedure.calculate(mtc.getElement("Cruiser", "class"), metric));
	}
}
