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

public class WMC1Test extends TestCase {
	private static MetricsRepository metrics;
	private static ICodeLevelModel model;
	private static final String root = "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testWMC/";

	public WMC1Test(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (WMC1Test.model == null) {
			WMC1Test.model = Factory.getInstance()
					.createCodeLevelModel("Test.TestMetrics");
			WMC1Test.model.create(
					new CompleteClassFileCreator(new String[] { root }));
			WMC1Test.metrics = MetricsRepository.getInstance();
		}
	}

	public void testRefClass() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) model
				.getTopLevelEntityFromID(
						"pom.test.rsc.specific.testWMC.TestSingleClass");

		Assert.assertEquals(2.0d, ((IUnaryMetric) metrics.getMetric("WMC"))
				.compute(model, firstClassEntity), 0d);
	}

	public void testRefInterface() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) model
				.getTopLevelEntityFromID(
						"pom.test.rsc.specific.testWMC.TestSingleInterface");

		Assert.assertEquals(0d, ((IUnaryMetric) metrics.getMetric("WMC"))
				.compute(model, firstClassEntity), 0d);
	}

	/**
	 * To Check with Yann
	 */
	public void test01() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) model
				.getTopLevelEntityFromID(
						"pom.test.rsc.specific.testWMC.TestAClass01");

		Assert.assertEquals(8.0d, ((IUnaryMetric) metrics.getMetric("WMC"))
				.compute(model, firstClassEntity), 0d);
	}

	/**
	 * An empty method has a complexity of 1 !
	 */
	public void test02() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) model
				.getTopLevelEntityFromID(
						"pom.test.rsc.specific.testWMC.TestAClass02");

		Assert.assertEquals(6.0d, ((IUnaryMetric) metrics.getMetric("WMC"))
				.compute(model, firstClassEntity), 0d);
	}
}
