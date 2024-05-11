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
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.IBinaryMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

public class TestCBO extends TestCase {
	private static ICodeLevelModel Model;
	private static MetricsRepository MetricsRepository;

	public TestCBO(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();

		if (Model == null) {
			Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			Model
				.create(new CompleteClassFileCreator(
					new String[] { "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testCBO/" }));
			MetricsRepository = pom.metrics.MetricsRepository.getInstance();
		}
	}
	public void testRefClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestCBO.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleClass");

		Assert.assertEquals("", 0d, ((IUnaryMetric) MetricsRepository
			.getMetric("CBO")).compute(TestCBO.Model, firstClassEntity), 0d);
	}
	public void testRefInterface() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestSingleInterface");

		Assert.assertEquals("", 0d, ((IUnaryMetric) MetricsRepository
			.getMetric("CBO")).compute(TestCBO.Model, firstClassEntity), 0d);
	}
	public void testAboutGetVar() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestAPlaneteRight");

		Assert.assertEquals(0d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBO")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutSetVar() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight");

		Assert.assertEquals(7d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBO")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutSetVarIn() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight");

		Assert.assertEquals(3d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBOin")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutSetVarOut() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestBPlaneteRight");

		Assert.assertEquals(4d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBOout")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutFunct() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight");

		Assert.assertEquals(7d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBO")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutFunctIn() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight");

		Assert.assertEquals(3d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBOin")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
	public void testAboutFunctOut() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteLeft");
		final IFirstClassEntity firstClassEntity2 =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testCBO.TestCPlaneteRight");

		Assert.assertEquals(4d, ((IBinaryMetric) MetricsRepository
			.getMetric("CBOout")).compute(
			TestCBO.Model,
			firstClassEntity,
			firstClassEntity2), 0d);
	}
}
