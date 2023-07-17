package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureSetOperation {

	@Test
	public void setOperation() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();
		ProcedureAttributes procAttr = new ProcedureAttributes();
		Metric metric = new Metric("test", mtc.getType("class"), null);
		metric.setAttributes(procAttr);

		MetricProcedureSetOperation setOperation = new MetricProcedureSetOperation();
		setOperation.setMetricsEngine(mtc.me);
		procAttr.setExpressionNode("set", mtc.parse("IncomingDependencies"));
		procAttr.setExpressionNode("element", mtc.parse("depclient"));
		procAttr.setExpressionNode("eltype", mtc.parse("class"));

		assertEquals(Integer.valueOf(0), setOperation.calculate(
				mtc.getElement("Vehicle", "class"), metric));
		assertEquals(Integer.valueOf(1), setOperation.calculate(
				mtc.getElement("Supplier", "class"), metric));
		assertEquals(Integer.valueOf(0),
				setOperation.calculate(mtc.getElement("Car", "class"), metric));
	}
}
