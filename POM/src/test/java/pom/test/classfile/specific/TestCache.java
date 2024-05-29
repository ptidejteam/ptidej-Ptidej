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

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import pom.metrics.IBinaryMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import pom.util.CacheManager;

public class TestCache extends TestCase {
	private static ICodeLevelModel Model;
	private static MetricsRepository MetricsRepository;
	private static IBinaryMetric CBOMetric;
	private static IBinaryMetric CBOinMetric;
	private static IBinaryMetric CBOoutMetric;

	public TestCache(final String aName) {
		super(aName);
	}
	protected void setUp() throws Exception {
		super.setUp();

		if (TestCache.Model == null) {
			TestCache.Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			TestCache.Model
				.create(new CompleteClassFileCreator(
					new String[] { "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testCBO/" }));
			TestCache.MetricsRepository =
				pom.metrics.MetricsRepository.getInstance();

			TestCache.CBOMetric =
				(IBinaryMetric) TestCache.MetricsRepository.getMetric("CBO");
			TestCache.CBOinMetric =
				(IBinaryMetric) TestCache.MetricsRepository.getMetric("CBOin");
			TestCache.CBOoutMetric =
				(IBinaryMetric) TestCache.MetricsRepository.getMetric("CBOout");

			// Pre-compute some metric values.
			((IUnaryMetric) TestCache.CBOMetric)
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass"));

			((IUnaryMetric) TestCache.CBOMetric)
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface"));

			TestCache.CBOMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"));

			TestCache.CBOMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"));

			TestCache.CBOinMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));

			TestCache.CBOoutMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));
		}
	}
	public void testCachingOfCBOValues() {
		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfCBOinValues() {
		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));
		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOinMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfCBOoutValues() {
		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));
		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isUnaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOoutMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfReversedCBOValues() {
		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(TestCache.Model)
					.isBinaryMetricValueInCache(
						TestCache.CBOMetric,
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						TestCache.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));
	}
	public void testCBOinAndOut() {
		final double leftRightCBO =
			TestCache.CBOinMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"))
					+ TestCache.CBOoutMetric
						.compute(
							TestCache.Model,
							TestCache.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
							TestCache.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));
		final double rightLeftCBO =
			TestCache.CBOinMetric
				.compute(
					TestCache.Model,
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
					TestCache.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"))
					+ TestCache.CBOoutMetric
						.compute(
							TestCache.Model,
							TestCache.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
							TestCache.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"));
		Assert.assertEquals(leftRightCBO, rightLeftCBO, 0d);
	}
}
