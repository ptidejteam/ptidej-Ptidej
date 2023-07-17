package com.sdmetrics.metrics;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;


public class TestRuleProcedureViolation {

	@Test
	public void violation() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();

		mtc.assertViolation("Manufacturer", "NameTooLong", "class",
				"Manufacturer");
		mtc.assertCompliance("Car", "NameTooLong", "class");
	}
}
