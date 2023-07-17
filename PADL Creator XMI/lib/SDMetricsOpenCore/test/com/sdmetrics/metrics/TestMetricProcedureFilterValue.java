package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestMetricProcedureFilterValue {

	@Test
	public void values() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();

		assertEquals("",
				mtc.getMetricStrValue("ClientName", "Vehicle", "class"));
		assertEquals("Manufacturer",
				mtc.getMetricStrValue("ClientName", "Supplier", "class"));
		assertEquals(mtc.getElement("Manufacturer", "class").getXMIID(),
				mtc.getMetricStrValue("ClientElement", "Supplier", "class"));
		assertEquals("",
				mtc.getMetricStrValue("ClientName", "Manufacturer", "class"));
	}
}
