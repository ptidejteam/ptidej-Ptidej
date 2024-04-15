/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package pom.test.classfile.specific;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.Files;

public class TestDIT extends TestCase {
	private static MetricsRepository MetricsRepository;
	private static ICodeLevelModel Model = null;
	private static final String root =
		"../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testDIT/";
	public TestDIT(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		if (TestDIT.Model == null) {
			TestDIT.Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			TestDIT.Model.create(new CompleteClassFileCreator(
				new String[] { root }));

			TestDIT.MetricsRepository =
				pom.metrics.MetricsRepository.getInstance();
		}
	}
	public void testRefClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestDIT.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestSingleClass");

		Assert.assertEquals(1d, ((IUnaryMetric) MetricsRepository
			.getMetric("DIT")).compute(TestDIT.Model, firstClassEntity), 0d);
	}
	public void testRefInterface() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestDIT.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestSingleInterface");

		Assert.assertEquals(1.0d, ((IUnaryMetric) MetricsRepository
			.getMetric("DIT")).compute(TestDIT.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestDIT.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestAChild03");

		Assert.assertEquals(4d, ((IUnaryMetric) MetricsRepository
			.getMetric("DIT")).compute(TestDIT.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnInterface01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestDIT.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestBChild03");

		Assert.assertEquals(4d, ((IUnaryMetric) MetricsRepository
			.getMetric("DIT")).compute(TestDIT.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnInterface02() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestDIT.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestBChild11");

		Assert.assertEquals(2d, ((IUnaryMetric) MetricsRepository
			.getMetric("DIT")).compute(TestDIT.Model, firstClassEntity), 0d);
	}
}
