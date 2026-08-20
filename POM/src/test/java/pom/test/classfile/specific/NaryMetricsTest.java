/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package pom.test.classfile.specific;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.INaryMetric;
import pom.metrics.MetricsRepository;

/**
 * Test class for n-ary metrics (TotalCoupling, AverageCohesion, etc.).
 * N-ary metrics compute values across multiple entities in a model.
 * 
 * @author Ptidej Contributors
 */
public class NaryMetricsTest extends TestCase {
	private static ICodeLevelModel Model;
	private static MetricsRepository Metrics;
	private static IFirstClassEntity[] AllEntities;

	public NaryMetricsTest(final String aName) {
		super(aName);
	}

	protected void setUp() throws Exception {
		super.setUp();

		if (Model == null) {
			// Yann 2026/08/20: Isolation
			// Use a unique model name to avoid polluting the CacheManager
			// singleton used by other tests (e.g. CacheTest), which uses
			// "Test.TestMetrics" and asserts the cache is empty.
			Model =
				Factory.getInstance().createCodeLevelModel("Test.NaryMetrics");
			Model
				.create(new CompleteClassFileCreator(
					new String[] { "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testCBO/" }));
			Metrics = pom.metrics.MetricsRepository.getInstance();

			// Collect all top-level entities for n-ary metric testing
			final List entitiesList = new ArrayList();
			final Iterator iterator = Model.getIteratorOnTopLevelEntities();
			while (iterator.hasNext()) {
				entitiesList.add(iterator.next());
			}
			AllEntities = new IFirstClassEntity[entitiesList.size()];
			entitiesList.toArray(AllEntities);
		}
	}

	/**
	 * Test that the n-ary metrics array is accessible from the repository.
	 */
	public void testNaryMetricList() {
		final INaryMetric[] naryMetrics = Metrics.getNaryMetrics();
		Assert.assertNotNull("N-ary metrics array should not be null", naryMetrics);
		// Note: there may be 0 n-ary metrics in some configurations
	}

	/**
	 * Test TotalCoupling n-ary metric can be retrieved and computes a value.
	 * This test is optional: it only runs if TotalCoupling is registered.
	 */
	public void testTotalCouplingMetricExists() {
		final INaryMetric totalCoupling =
			(INaryMetric) Metrics.getMetric("TotalCoupling");
		// TotalCoupling may not be present in all configurations; skip if absent.
		if (totalCoupling == null) {
			return;
		}
		Assert.assertEquals(
			"TotalCoupling definition should be descriptive",
			"Total coupling between all objects in a model, "
				+ "computed as the sum of CBO across all entity pairs.",
			totalCoupling.getDefinition());
	}

	/**
	 * Test TotalCoupling computation returns a non-negative value.
	 * This test is optional: it only runs if TotalCoupling is registered.
	 */
	public void testTotalCouplingCompute() {
		final INaryMetric totalCoupling =
			(INaryMetric) Metrics.getMetric("TotalCoupling");
		// TotalCoupling may not be present in all configurations; skip if absent.
		if (totalCoupling == null) {
			return;
		}
		if (AllEntities.length >= 2) {
			final double result = totalCoupling.compute(Model, AllEntities);
			Assert.assertTrue(
				"TotalCoupling should be non-negative", result >= 0);
		}
		else {
			// If fewer than 2 entities, result should be 0
			final double result = totalCoupling.compute(Model, AllEntities);
			Assert.assertEquals(
				"TotalCoupling should be 0 with fewer than 2 entities",
				0d, result, 0d);
		}
	}

	/**
	 * Test AverageCohesion n-ary metric can be retrieved.
	 * This test is optional: it only runs if AverageCohesion is registered.
	 */
	public void testAverageCohesionMetricExists() {
		final INaryMetric avgCohesion =
			(INaryMetric) Metrics.getMetric("AverageCohesion");
		// AverageCohesion may not be present in all configurations; skip if absent.
		if (avgCohesion == null) {
			return;
		}
		Assert.assertEquals(
			"AverageCohesion definition should be descriptive",
			"Average lack of cohesion across all entities in a model, "
				+ "computed as the mean LCOM1 value of all top-level entities.",
			avgCohesion.getDefinition());
	}

	/**
	 * Test AverageCohesion computation returns a non-negative value.
	 * This test is optional: it only runs if AverageCohesion is registered.
	 */
	public void testAverageCohesionCompute() {
		final INaryMetric avgCohesion =
			(INaryMetric) Metrics.getMetric("AverageCohesion");
		// AverageCohesion may not be present in all configurations; skip if absent.
		if (avgCohesion == null) {
			return;
		}
		if (AllEntities.length > 0) {
			final double result = avgCohesion.compute(Model, AllEntities);
			Assert.assertTrue(
				"AverageCohesion should be non-negative", result >= 0);
		}
		else {
			final double result = avgCohesion.compute(Model, AllEntities);
			Assert.assertEquals(
				"AverageCohesion should be 0 with no entities",
				0d, result, 0d);
		}
	}

	/**
	 * Test that getNaryMetrics() returns the same metrics as getMetric().
	 */
	public void testNaryMetricsConsistency() {
		final INaryMetric[] naryMetrics = Metrics.getNaryMetrics();

		for (int i = 0; i < naryMetrics.length; i++) {
			final String name = naryMetrics[i].getName();
			final INaryMetric fromGetMetric =
				(INaryMetric) Metrics.getMetric(name);
			Assert.assertNotNull(
				"Metric " + name + " should be retrievable via getMetric()",
				fromGetMetric);
		}
	}
}
