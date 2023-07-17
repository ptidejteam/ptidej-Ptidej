package com.sdmetrics.metrics;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;


public class TestRuleProcedureCycle {

	MetricTestContext mtc;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
	}

	@Test
	public void simpleCycle() throws SDMetricsException {
		mtc.assertViolation("ClassA", "DepCycle", "class", "cyc# 1 (3 nodes)");
		mtc.assertViolation("ClassB", "DepCycle", "class", "cyc# 1 (3 nodes)");
		mtc.assertViolation("ClassC", "DepCycle", "class", "cyc# 1 (3 nodes)");
		mtc.assertViolation("ClassF", "DepCycle", "class", "cyc# 2 (1 node)");
	}

	@Test
	public void minnodes() throws SDMetricsException {
		mtc.assertViolation("ClassA", "DepCycle2", "class", "cyc# 1 (3 nodes)");
		mtc.assertCompliance("ClassF", "DepCycle2", "class");
	}
}
