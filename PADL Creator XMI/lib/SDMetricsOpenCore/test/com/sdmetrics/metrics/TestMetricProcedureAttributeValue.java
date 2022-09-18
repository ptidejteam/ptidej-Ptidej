package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureAttributeValue {

	MetricTestContext mtc;
	ProcedureAttributes procAttr;
	Metric metric;
	MetricProcedureAttributeValue procedure;
	ModelElement supplier;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttr = new ProcedureAttributes();
		metric = new Metric("test", mtc.getType("class"), null);
		metric.setAttributes(procAttr);
		procedure = new MetricProcedureAttributeValue();
		procedure.setMetricsEngine(mtc.me);
		supplier = mtc.getElement("Supplier", "class");
	}

	@Test
	public void plainAttribute() throws SDMetricsException {
		procAttr.setExpressionNode("attr", mtc.parse("name"));
		assertEquals("Supplier", procedure.calculate(supplier, metric));

		procAttr.setExpressionNode("attr", mtc.parse("account"));
		try {
			procedure.calculate(supplier, metric);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Unknown attribute 'account' for elements of type 'class'.",
					ex.getMessage());
		}

		procAttr.setExpressionNode("attr", mtc.parse("stereotypes"));
		try {
			procedure.calculate(supplier, metric);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Attribute 'stereotypes' is not a single-valued attribute for elements of type 'class'.",
					ex.getMessage());
		}
	}

	@Test
	public void refAttribute() throws SDMetricsException {
		ModelElement buildParts = mtc.getElement("buildParts", "dependency");
		procAttr.setExpressionNode("attr", mtc.parse("depsupplier"));
		assertSame(supplier, procedure.calculate(buildParts, metric));

		procAttr.setExpressionNode("element", mtc.parse("depsupplier"));
		procAttr.setExpressionNode("attr", mtc.parse("name"));
		assertEquals("Supplier", procedure.calculate(buildParts, metric));

		procAttr.setExpressionNode("eltype", mtc.parse("interface"));
		assertEquals("", procedure.calculate(buildParts, metric));
	}

	@Test
	public void emptyReferences() throws SDMetricsException {
		ModelElement steers = mtc.getElement("steers", "dependency");
		procAttr.setExpressionNode("attr", mtc.parse("depclient"));
		assertEquals("", procedure.calculate(steers, metric));

		procAttr.setExpressionNode("element", mtc.parse("depclient"));
		procAttr.setExpressionNode("attr", mtc.parse("name"));
		assertEquals("", procedure.calculate(steers, metric));
	}
}
