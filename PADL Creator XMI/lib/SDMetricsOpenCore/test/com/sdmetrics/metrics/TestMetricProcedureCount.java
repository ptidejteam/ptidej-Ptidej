package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestMetricProcedureCount {

	@Test
	public void counts() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();
		assertEquals(0,
				mtc.getMetricValue("ClientsWithNameAttribute", "Car", "class"),
				0);
		assertEquals(1, mtc.getMetricValue("ClientsWithNameAttribute",
				"Supplier", "class"), 0);
		assertEquals(0, mtc.getMetricValue("ClientsWithNameAttribute",
				"Vehicle", "class"), 0);

		assertEquals(1,
				mtc.getMetricValue("NameAttributes", "Manufacturer", "class"),
				0);
		assertEquals(0,
				mtc.getMetricValue("NameAttributes", "Vehicle", "class"), 0);

	}
}
