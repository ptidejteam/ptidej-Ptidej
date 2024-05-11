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

public class TestNOC extends TestCase {
	private static MetricsRepository metrics;
	private static ICodeLevelModel model = null;
	private static final String root =
		"../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testNOC/";
	public TestNOC(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		if (model == null) {
			model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			model.create(new CompleteClassFileCreator(new String[] { root }));

			metrics = MetricsRepository.getInstance();
		}
	}
	public void testRefClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNOC.TestSingleClass");

		Assert.assertEquals("", 0d, ((IUnaryMetric) metrics.getMetric("NOC"))
			.compute(model, firstClassEntity), 0d);
	}
	public void testRefInterface() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNOC.TestSingleInterface");

		Assert.assertEquals("", 0d, ((IUnaryMetric) metrics.getMetric("NOC"))
			.compute(model, firstClassEntity), 0d);
	}
	public void test01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNOC.TestAParent00");

		Assert.assertEquals("", 3d, ((IUnaryMetric) metrics.getMetric("NOC"))
			.compute(model, firstClassEntity), 0d);
	}
	public void test02() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNOC.TestAChild01");

		Assert.assertEquals("", 2d, ((IUnaryMetric) metrics.getMetric("NOC"))
			.compute(model, firstClassEntity), 0d);
	}
}
