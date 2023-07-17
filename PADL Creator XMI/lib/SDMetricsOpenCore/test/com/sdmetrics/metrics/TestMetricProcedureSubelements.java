package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestMetricProcedureSubelements {

	@Test
	public void values() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();

		assertEquals(3,
				mtc.getMetricValue("RecClassCount", "package1_1", "package"), 0);
		assertEquals(0,
				mtc.getMetricValue("RecClassCount", "package2", "package"), 0);
	}
}
