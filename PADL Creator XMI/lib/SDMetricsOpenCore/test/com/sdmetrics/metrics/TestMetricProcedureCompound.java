package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureCompound {

	MetricTestContext mtc;
	ProcedureAttributes procAttrs;
	Metric metric;
	MetricProcedureCompound procedure;
	ModelElement person;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();
		metric = new Metric("test", mtc.getType("class"), null);
		metric.setAttributes(procAttrs);
		procedure = new MetricProcedureCompound();
		procedure.setMetricsEngine(mtc.me);
		person = mtc.getElement("Person", "class");
	}

	@Test
	public void unconditionalTerm() throws SDMetricsException {
		procAttrs.setExpressionNode("term", mtc.parse("NumAttr"));
		assertEquals(Integer.valueOf(2), procedure.calculate(person, metric));

		procAttrs.setExpressionNode("term", mtc.parse("ln(0)"));
		assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY),
				procedure.calculate(person, metric));

		procAttrs.setExpressionNode("term", mtc.parse("0/0"));
		procAttrs.setExpressionNode("fallback", mtc.parse("3"));
		assertEquals(Float.valueOf(3), procedure.calculate(person, metric));
	}

	@Test
	public void conditionalMetric() throws SDMetricsException {
		procAttrs.setExpressionNode("condition", mtc.parse("NumOps=0"));
		assertEquals(Integer.valueOf(0), procedure.calculate(person, metric));

		procAttrs.setExpressionNode("condition", mtc.parse("NumOps=1"));
		assertEquals(Integer.valueOf(1), procedure.calculate(person, metric));

		procAttrs.setExpressionNode("term", mtc.parse("length('hi')"));
		assertEquals(Integer.valueOf(2), procedure.calculate(person, metric));

		procAttrs.setExpressionNode("condition", mtc.parse("NumOps=0"));
		procAttrs.setExpressionNode("alt", mtc.parse("NumAttr"));
		assertEquals(Integer.valueOf(2), procedure.calculate(person, metric));
	}
}
