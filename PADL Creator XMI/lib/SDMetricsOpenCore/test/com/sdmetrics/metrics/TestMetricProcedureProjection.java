package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestMetricProcedureProjection {

	MetricTestContext mtc;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
	}

	@Test
	public void simpleProjection() throws Exception {
		assertEquals(0,
				mtc.getMetricValue("ChildrenWithoutOps", "Cruiser", "class"), 0);
		assertEquals(
				1,
				mtc.getMetricValue("ChildrenWithoutOps", "Motorcycle", "class"),
				0);
		assertEquals(2,
				mtc.getMetricValue("ChildrenWithoutOps", "Vehicle", "class"), 0);
	}

	@Test
	public void nestingProjection() throws Exception {
		assertEquals(1, mtc.getMetricValue("DepLevel", "Vehicle", "class"), 0);
		assertEquals(0,
				mtc.getMetricValue("DepLevel", "Manufacturer", "class"), 0);
		assertEquals(1, mtc.getMetricValue("DepLevel", "Supplier", "class"), 0);
	}

	@Test
	public void recurseProjection() throws Exception {
		assertEquals(4,
				mtc.getMetricValue("RecDepLevel", "Manufacturer", "class"), 0);
		assertEquals(2, mtc.getMetricValue("RecDepLevel", "Supplier", "class"),
				0);
	}

	@Test
	public void recurseProjectionWithInheritance() throws Exception {
		assertEquals(6, mtc.getMetricValue("Classes_tc", "scope", "package"), 0);
		int classCount = mtc.model.getElements(mtc.getType("class")).size();
		assertEquals(classCount,
				mtc.getMetricValue("Classes_tc", "testModel04", "model"), 0);
		assertEquals(1,
				mtc.getMetricValue("Classes_tc2", "testModel04", "model"), 0);

	}

	@Test
	public void principal() throws Exception {
		assertEquals(0, mtc.getMetricValue("DescWithLongerNames", "Motorcycle",
				"class"), 0);
		assertEquals(1,
				mtc.getMetricValue("DescWithLongerNames", "Vehicle", "class"),
				0);

	}
}
