package com.sdmetrics.metrics;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;


public class TestRuleProcedureSet {

	MetricTestContext mtc;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
	}

	@Test
	public void valueset() throws SDMetricsException {
		mtc.assertViolation("ClassG", "OpOverriding", "class", "operation1");

		mtc.assertViolation("Manufacturer", "HasAttributes", "class", "name");
		mtc.assertCompliance("Vehicle", "HasAttributes", "class");
	}

	@Test
	public void projection() throws SDMetricsException {
		mtc.assertViolation("Train", "LeavesWithOps", "class", "derail");
		mtc.assertCompliance("ClassG", "LeavesWithOps", "class");
	}

	@Test
	public void compare() throws SDMetricsException {
		mtc.assertViolation("Vehicle", "ChildHasMoreOps", "class", "Train",
				"Cruiser");
		mtc.assertViolation("Motorcycle", "ChildHasMoreOps", "class", "Cruiser");
	}
}
