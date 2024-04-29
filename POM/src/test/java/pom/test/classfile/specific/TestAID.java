/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
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

public class TestAID extends TestCase {
	private static MetricsRepository metrics;
	private static ICodeLevelModel model = null;
	private static final String root =
		"../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testAID/";
	public TestAID(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		if (TestAID.model == null) {
			TestAID.model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			TestAID.model.create(new CompleteClassFileCreator(
				new String[] { root }));

			TestAID.metrics = MetricsRepository.getInstance();
		}
	}
	public void testRef() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testAID.TestChild00");

		Assert.assertEquals(1d, ((IUnaryMetric) metrics.getMetric("AID"))
			.compute(model, firstClassEntity), 0d);
	}
	public void testAncestorExtend01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestAID.model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testAID.TestAChild00");

		Assert.assertEquals(5d, ((IUnaryMetric) metrics.getMetric("AID"))
			.compute(model, firstClassEntity), 0d);
	}
	public void testAncestorImplement01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestAID.model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testAID.TestBChild00");

		Assert.assertEquals(1.9375d, ((IUnaryMetric) metrics.getMetric("AID"))
			.compute(model, firstClassEntity), 0d);
	}
	public void testAncestorExtendImplement01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestAID.model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testAID.TestCChild00");

		Assert.assertEquals(2.9375d, ((IUnaryMetric) metrics.getMetric("AID"))
			.compute(model, firstClassEntity), 0d);
	}
}
