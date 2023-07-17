package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.math.HashMultiSet;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestSetProcedureProjection {

	MetricTestContext mtc;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
	}

	@Test
	public void emptyProjections() throws Exception {
		// Empty result with set/multiset
		assertEquals(new HashSet<Object>(),
				mtc.getSetValue("DependentElements", "Cruiser", "class"));
		assertEquals(new HashMultiSet<Object>(1),
				mtc.getSetValue("DependingClasses", "Cruiser", "class"));
	}

	@Test
	public void simpleProjection() throws Exception {
		// Iteration with null/invalid
		HashSet<ModelElement> result = new HashSet<ModelElement>();
		result.add(mtc.getElement("Resource", "class"));
		result.add(mtc.getElement("Logistics", "interface"));
		assertEquals(result,
				mtc.getSetValue("DependentElements", "Supplier", "class"));

		result.add(mtc.getElement("Supplier", "class"));
		assertEquals(result,
				mtc.getSetValue("DependentElementsTC", "Manufacturer", "class"));

		assertEquals(new HashMultiSet<Object>(1),
				mtc.getSetValue("DependingClasses", "Vehicle", "class"));
	}

	@Test
	public void relationReuse() throws Exception {
		// Test optimization: reuse of relation set when nothing is filtered
		SetProcedureProjection procedure = new SetProcedureProjection();
		procedure.setMetricsEngine(mtc.me);

		Collection<?> dependencies = procedure.getRelationOrSet(
				mtc.getElement("Supplier", "class"),
				mtc.getMetric("RecDepLevel", "class").getAttributes(), null);
		assertSame(dependencies,
				mtc.getSetValue("OutgoingDependencies", "Supplier", "class"));
	}
}
