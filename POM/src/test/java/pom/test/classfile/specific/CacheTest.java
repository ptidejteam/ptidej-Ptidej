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

public class CacheTest extends TestCase {
	private static ICodeLevelModel Model;
	private static MetricsRepository MetricsRepository;
	private static IBinaryMetric CBOMetric;
	private static IBinaryMetric CBOinMetric;
	private static IBinaryMetric CBOoutMetric;

	public CacheTest(final String aName) {
		super(aName);
	}
	protected void setUp() throws Exception {
		super.setUp();

		if (CacheTest.Model == null) {
			CacheTest.Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			CacheTest.Model
				.create(new CompleteClassFileCreator(
					new String[] { "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testCBO/" }));
			CacheTest.MetricsRepository =
				pom.metrics.MetricsRepository.getInstance();

			CacheTest.CBOMetric =
				(IBinaryMetric) CacheTest.MetricsRepository.getMetric("CBO");
			CacheTest.CBOinMetric =
				(IBinaryMetric) CacheTest.MetricsRepository.getMetric("CBOin");
			CacheTest.CBOoutMetric =
				(IBinaryMetric) CacheTest.MetricsRepository.getMetric("CBOout");

			// Pre-compute some metric values.
			((IUnaryMetric) CacheTest.CBOMetric)
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass"));

			((IUnaryMetric) CacheTest.CBOMetric)
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface"));

			CacheTest.CBOMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"));

			CacheTest.CBOMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"));

			CacheTest.CBOinMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));

			CacheTest.CBOoutMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));
		}
	}
	public void testCachingOfCBOValues() {
		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfCBOinValues() {
		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));
		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOinMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfCBOoutValues() {
		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass")));

		Assert
			.assertTrue(
				"Value should be in cache",
				CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight")));
		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight")));
		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isUnaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft")));

		Assert
			.assertTrue(
				"Value should NOT be in cache",
				!CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOoutMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteLeft"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestDPlaneteRight")));
	}
	public void testCachingOfReversedCBOValues() {
		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft")));

		Assert
			.assertTrue(
				"Is value in cache?",
				CacheManager
					.getInstance(CacheTest.Model)
					.isBinaryMetricValueInCache(
						CacheTest.CBOMetric,
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight"),
						CacheTest.Model
							.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft")));
	}
	public void testCBOinAndOut() {
		final double leftRightCBO =
			CacheTest.CBOinMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"))
					+ CacheTest.CBOoutMetric
						.compute(
							CacheTest.Model,
							CacheTest.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"),
							CacheTest.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"));
		final double rightLeftCBO =
			CacheTest.CBOinMetric
				.compute(
					CacheTest.Model,
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
					CacheTest.Model
						.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"))
					+ CacheTest.CBOoutMetric
						.compute(
							CacheTest.Model,
							CacheTest.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight"),
							CacheTest.Model
								.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft"));
		Assert.assertEquals(leftRightCBO, rightLeftCBO, 0d);
	}
}
