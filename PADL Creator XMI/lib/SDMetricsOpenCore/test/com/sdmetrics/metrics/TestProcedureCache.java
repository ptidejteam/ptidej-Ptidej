package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;

public class TestProcedureCache {

	ProcedureCache<MetricProcedure> cache;

	@Before
	public void initTestCache() throws SDMetricsException {
		cache = new ProcedureCache<MetricProcedure>("testprocedure") {
			@Override
			protected Class<? extends MetricProcedure> loadClass(
					String className) throws ClassNotFoundException {
				return Class.forName(className).asSubclass(
						MetricProcedure.class);
			}

		};
		cache.addProcedureClass("projection",
				MetricProcedureProjection.class.getName());
		cache.addProcedureClass("compoundmetric",
				MetricProcedureCompound.class.getName());
	}

	@Test
	public void retrieveProcedures() throws SDMetricsException {
		ArrayList<MetricProcedure> procedures = new ArrayList<MetricProcedure>();
		// get five procedure instances and check their types
		for (int i = 0; i < 5; i++) {
			MetricProcedure proc = cache.getProcedure("projection");
			assertTrue(proc.getClass().equals(MetricProcedureProjection.class));
			assertEquals("projection", proc.getName());
			assertFalse("unique instances", procedures.contains(proc));
			procedures.add(cache.getProcedure("projection"));
		}

		// Return the procedures to the store
		for (MetricProcedure proc : procedures)
			cache.returnProcedure(proc);

		// Retrieving the procedures yields the same instances
		// in the order in which they were returned
		for (int i = 4; i >= 0; i--)
			assertSame(procedures.get(i), cache.getProcedure("projection"));
	}

	@Test
	public void procedureExists() {
		assertTrue(cache.hasProcedure("projection"));
		assertFalse(cache.hasProcedure("dividebyzero"));

		try {
			cache.getProcedure("dividebyzero");
			fail("Exception expected.");
		} catch (SDMetricsException ex) {
			assertEquals("Unknown testprocedure 'dividebyzero'.",
					ex.getMessage());
		}
	}

	@Test(expected = SDMetricsException.class)
	public void addNonexistingClass() throws SDMetricsException {
		cache.addProcedureClass("test", "sdmetrics.test.BoringMetric");
	}

	@Test(expected = SDMetricsException.class)
	public void addWrongClass() throws SDMetricsException {
		cache.addProcedureClass("violation",
				RuleProcedureViolation.class.getName());
	}

	@Test(expected = SDMetricsException.class)
	public void addUninstantiableClass() throws SDMetricsException {
		cache.addProcedureClass("test", NoPublicConstructor.class.getName());
	}

	static class NoPublicConstructor extends MetricProcedure {

		private NoPublicConstructor() {
			super();
		}

		@Override
		public Object calculate(ModelElement e, Metric m) {
			return null;
		}
	}
}